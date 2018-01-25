package oms.mmc.android.fast.framwork.sample.ui.fragment;

import com.github.promeg.pinyinhelper.Pinyin;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseStickyListFragment;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.loadview.TextLoadViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactGroupChatTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactLabelTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactLetterTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactOfficialAccountsTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactSumCountTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.NewFriendTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ILoadViewFactory;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: HomeFragment
 * Date: on 2018/1/25  上午11:03
 * Auther: zihe
 * Descirbe:联系人fragment
 * Email: hezihao@linghit.com
 */

public class ContactFragment extends BaseStickyListFragment {
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

    @Override
    public int onLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    public IDataSource onListViewDataSourceReady() {
        return new BaseListDataSource(getActivity()) {
            @Override
            protected ArrayList load(int page) throws Exception {
                //模拟后台数据
                Thread.sleep(1000);
                //拼装需要的数据集
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                //插入固定数据在顶部
                models.add(new BaseItemData(TPL_NEW_FRIEND));
                models.add(new BaseItemData(TPL_GROUP_CHAT));
                models.add(new BaseItemData(TPL_LABLE));
                models.add(new BaseItemData(TPL_OFFICIAL_ACCOUNTS));
                ArrayList<String> datas = new ArrayList<String>();
                for (int i = 0; i < 15; i++) {
                    datas.add(FakeUtil.getRandomName());
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
                    models.add(new ItemDataWrapper(TPL_CONTACT, FakeUtil.getRandomAvatar(), datas.get(i)));
                }
                //最后插入一条联系人总数
                models.add(TPL_SUM_CONTACT_COUNT, new ItemDataWrapper(TPL_SUM_CONTACT_COUNT, String.valueOf(datas.size())));
                //分页，需要和后台协商，一页返回大于多少条时可以有下一页
//                this.page = page;
//                this.hasMore = datas.size() >= Const.Config.pageSize;
                return models;
            }
        };
    }

    @Override
    public ArrayList<Class> onListViewTypeClassesReady() {
        ArrayList<Class> tpls = new ArrayList<Class>();
        tpls.add(TPL_NEW_FRIEND, NewFriendTpl.class);
        tpls.add(TPL_GROUP_CHAT, ContactGroupChatTpl.class);
        tpls.add(TPL_LABLE, ContactLabelTpl.class);
        tpls.add(TPL_OFFICIAL_ACCOUNTS, ContactOfficialAccountsTpl.class);
        tpls.add(TPL_STICKY_LETTER, ContactLetterTpl.class);
        tpls.add(TPL_CONTACT, ContactTpl.class);
        tpls.add(TPL_SUM_CONTACT_COUNT, ContactSumCountTpl.class);
        return tpls;
    }

    @Override
    protected int onGetStickyTemplateViewType() {
        return TPL_STICKY_LETTER;
    }

    @Override
    public void onListViewReady() {
        super.onListViewReady();
        listView.setDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setDividerHeight(0);
        pullToRefreshListView.setPullRefreshEnabled(false);
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new TextLoadViewFactory();
    }
}