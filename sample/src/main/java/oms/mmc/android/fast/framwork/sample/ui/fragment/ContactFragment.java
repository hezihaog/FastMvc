package oms.mmc.android.fast.framwork.sample.ui.fragment;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseStickyListFragment;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.constant.Const;
import oms.mmc.android.fast.framwork.sample.loadview.TextLoadViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactLetterTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactTpl;
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
    //悬浮的字母类型
    public static final int TPL_STICKY_LETTER = 0;
    //新的好友条目
    public static final int TPL_NEW_FRIEND = 1;
    //群聊条目
    public static final int TPL_GROUP_CHAT = 2;
    //标签条目
    public static final int TPL_LABLE = 3;
    //公众号条目
    public static final int TPL_OFFICIAL_ACCOUNTS = 4;
    //我的联系人条目
    public static final int TPL_CONTACT = 1;

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
                ArrayList<String> datas = new ArrayList<String>();
                for (int i = 0; i < 15; i++) {
                    datas.add(FakeUtil.getRandomName());
                }
                //拼装需要的数据集
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
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
                //和后台协商，一页返回大于多少条时可以有下一页
                this.page = page;
                this.hasMore = datas.size() >= Const.Config.pageSize;
                return models;
            }
        };
    }

    @Override
    public ArrayList<Class> onListViewTypeClassesReady() {
        ArrayList<Class> tpls = new ArrayList<Class>();
        tpls.add(TPL_STICKY_LETTER, ContactLetterTpl.class);
        tpls.add(TPL_CONTACT, ContactTpl.class);
        return tpls;
    }

    @Override
    public void onListViewReady() {
        super.onListViewReady();
        listView.setDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setDividerHeight(0);
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new TextLoadViewFactory();
    }
}