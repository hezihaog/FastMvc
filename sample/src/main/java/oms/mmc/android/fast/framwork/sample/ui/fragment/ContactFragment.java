package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.github.promeg.pinyinhelper.Pinyin;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseListFragment;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactRecommendTpl;
import oms.mmc.android.fast.framwork.widget.view.ListScrollHelper;
import oms.mmc.android.fast.framwork.widget.view.wrapper.ScrollableRecyclerViewWrapper;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.android.fast.framwork.sample.loadview.MyContactLoadViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactGroupChatTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactLabelTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactLetterTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactOfficialAccountsTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactSumCountTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.NewFriendTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.ILoadViewFactory;
import oms.mmc.android.fast.framwork.widget.view.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: HomeFragment
 * Date: on 2018/1/25  上午11:03
 * Auther: zihe
 * Descirbe:联系人fragment
 * Email: hezihao@linghit.com
 */

public class ContactFragment extends BaseListFragment {
    //新的好友条目
    public static final int TPL_NEW_FRIEND = 0;
    //群聊条目
    public static final int TPL_GROUP_CHAT = 1;
    //标签条目
    public static final int TPL_LABLE = 2;
    //公众号条目
    public static final int TPL_OFFICIAL_ACCOUNTS = 3;
    //悬浮的字母类型
    public static final int TPL_STICKY_LETTER = 4;
    //我的联系人条目
    public static final int TPL_CONTACT = 5;
    //联系人总数条目
    public static final int TPL_SUM_CONTACT_COUNT = 6;
    //推荐卡片
    public static final int TPL_RECOMMENT = 7;

    @Override
    public void onFindView(ViewFinder finder) {
    }

    @Override
    public IDataSource onListDataSourceReady() {
        return new BaseListDataSource(getActivity()) {
            @Override
            protected ArrayList load(int page) throws Exception {
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
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_NEW_FRIEND, NewFriendTpl.class);
        tpls.put(TPL_GROUP_CHAT, ContactGroupChatTpl.class);
        tpls.put(TPL_LABLE, ContactLabelTpl.class);
        tpls.put(TPL_OFFICIAL_ACCOUNTS, ContactOfficialAccountsTpl.class);
        tpls.put(TPL_STICKY_LETTER, ContactLetterTpl.class);
        tpls.put(TPL_CONTACT, ContactTpl.class);
        tpls.put(TPL_SUM_CONTACT_COUNT, ContactSumCountTpl.class);
        tpls.put(TPL_RECOMMENT, ContactRecommendTpl.class);
        return tpls;
    }

    @Override
    public int onGetStickyTplViewType() {
        return TPL_STICKY_LETTER;
    }

    @Override
    public RecyclerView.LayoutManager getListLayoutManager() {
        return new StickyHeadersLinearLayoutManager<BaseListAdapter>(getActivity());
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new MyContactLoadViewFactory();
    }

    @Override
    public ListScrollHelper onGetScrollHelper() {
        return new ListScrollHelper(new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) mRecyclerView));
    }

    @Override
    public void onListReady() {
        super.onListReady();
        mRecyclerViewHelper.setCanPullToRefresh(false);
    }
}