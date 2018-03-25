package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.promeg.pinyinhelper.Pinyin;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import oms.mmc.android.fast.framwork.base.BaseFastListFragment;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.loadview.MyContactLoadViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactGroupChatTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactLabelTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactLetterTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactOfficialAccountsTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactRecommendTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactSumCountTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.NewFriendTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshLayout;
import oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.factory.load.factory.ILoadViewFactory;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: HomeFragment
 * Date: on 2018/1/25  上午11:03
 * Auther: zihe
 * Descirbe:联系人fragment
 * Email: hezihao@linghit.com
 */

public class ContactListFragment extends BaseFastListFragment<SmartPullRefreshLayout> {
    //推荐卡片
    public static final int TPL_RECOMMENT = 1;
    //新的好友条目
    public static final int TPL_NEW_FRIEND = 2;
    //群聊条目
    public static final int TPL_GROUP_CHAT = 3;
    //标签条目
    public static final int TPL_LABLE = 4;
    //公众号条目
    public static final int TPL_OFFICIAL_ACCOUNTS = 5;
    //悬浮的字母类型
    public static final int TPL_STICKY_LETTER = 6;
    //我的联系人条目
    public static final int TPL_CONTACT = 7;
    //联系人总数条目
    public static final int TPL_SUM_CONTACT_COUNT = 8;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                //模拟后台数据
                Thread.sleep(1000);
                //拼装需要的数据集
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                models.add(new ItemDataWrapper(TPL_RECOMMENT));
                //插入固定数据在顶部
                models.add(new BaseItemData(TPL_NEW_FRIEND));
                models.add(new BaseItemData(TPL_GROUP_CHAT));
                models.add(new BaseItemData(TPL_LABLE));
                models.add(new BaseItemData(TPL_OFFICIAL_ACCOUNTS));

                ArrayList<String> datas = new ArrayList<String>();
                for (int i = 0; i < 25; i++) {
                    datas.add(FakeUtil.getRandomName(i));
                }
                //按字母排序
                Collections.sort(datas, Collator.getInstance(Locale.CHINA));
                char letter = 0;
                for (int i = 0; i < datas.size(); i++) {
                    //首次肯定有一个字母
                    if (i == 0) {
                        letter = Character.toUpperCase(Pinyin.toPinyin(datas.get(i).charAt(0)).charAt(0));
                        models.add(new ItemDataWrapper(TPL_STICKY_LETTER, String.valueOf(letter)));
                    } else {
                        //如果下一个条目的首字母和上一个的不一样，则插入一条新的悬浮字母条目
                        char nextLetter = Character.toUpperCase(Pinyin.toPinyin(datas.get(i).charAt(0)).charAt(0));
                        if (!(nextLetter == letter)) {
                            letter = nextLetter;
                            models.add(new ItemDataWrapper(TPL_STICKY_LETTER, String.valueOf(letter)));
                        }
                    }
                    models.add(new ItemDataWrapper(TPL_CONTACT, FakeUtil.getRandomAvatar(i), datas.get(i)));
                }
                //最后插入一条联系人总数
                models.add(new ItemDataWrapper(TPL_SUM_CONTACT_COUNT, String.valueOf(datas.size())));
                return models;
            }
        };
    }

    @Override
    public IPullRefreshWrapper<SmartPullRefreshLayout> onInitPullRefreshWrapper(SmartPullRefreshLayout pullRefreshAbleView) {
        return new SmartPullRefreshWrapper(pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<SmartPullRefreshLayout> refreshWrapper, SmartPullRefreshLayout pullRefreshAbleView) {
        super.onPullRefreshWrapperReady(refreshWrapper, pullRefreshAbleView);
        pullRefreshAbleView.setEnableLoadmore(false);
    }

    /**
     * 修改懒加载进场布局，用于替换懒加载在显示之前的空白瞬间
     */
    @Override
    protected View onGetLazyLoadingView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_ziwei_load_loading, null);
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_RECOMMENT, ContactRecommendTpl.class);
        tpls.put(TPL_NEW_FRIEND, NewFriendTpl.class);
        tpls.put(TPL_GROUP_CHAT, ContactGroupChatTpl.class);
        tpls.put(TPL_LABLE, ContactLabelTpl.class);
        tpls.put(TPL_OFFICIAL_ACCOUNTS, ContactOfficialAccountsTpl.class);
        tpls.put(TPL_STICKY_LETTER, ContactLetterTpl.class);
        tpls.put(TPL_CONTACT, ContactTpl.class);
        tpls.put(TPL_SUM_CONTACT_COUNT, ContactSumCountTpl.class);
        return tpls;
    }

    @Override
    public int onStickyTplViewTypeReady() {
        return TPL_STICKY_LETTER;
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new MyContactLoadViewFactory();
    }

    @Override
    public void onListReady() {
        super.onListReady();
        getRefreshLayoutWrapper().setRefreshEnable();
//        getRecyclerViewHelper().setCanPullToRefresh(false);
    }

    @Override
    public void onListReadyAfter() {
        super.onListReadyAfter();
        //进入后马上刷新一次
        getRefreshWrapper().startRefreshWithAnimation();
    }
}