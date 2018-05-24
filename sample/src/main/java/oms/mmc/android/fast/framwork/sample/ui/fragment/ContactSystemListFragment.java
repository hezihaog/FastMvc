package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastRecyclerViewListFragment;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.bean.ContactList;
import oms.mmc.android.fast.framwork.sample.bean.Result;
import oms.mmc.android.fast.framwork.sample.constant.Const;
import oms.mmc.android.fast.framwork.sample.tpl.contactsytem.ContactListTpl;
import oms.mmc.android.fast.framwork.sample.util.RequestManager;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: ContactSystemListFragment
 * Date: on 2018/5/23  下午11:02
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ContactSystemListFragment extends BaseFastRecyclerViewListFragment<SwipePullRefreshLayout, ScrollableRecyclerView> {
    private static final int TPL_CONTACT = 0;

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                Result result = RequestManager.getAllContact(page + "", Const.Config.pageSize + "");
                if (result.isOk()) {
                    ContactList bean = (ContactList) result;
                    ArrayList<ContactList.Contact> allContactList = bean.getContent();
                    for (ContactList.Contact contact : allContactList) {
                        models.add(new ItemDataWrapper(TPL_CONTACT, contact));
                    }
                    this.page = page;
                    this.hasMore = allContactList.size() >= Const.Config.pageSize;
                }
                return models;
            }
        };
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_CONTACT, ContactListTpl.class);
        return tpls;
    }

    @Override
    public void onListReady() {
        super.onListReady();
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        getScrollableView().addItemDecoration(decoration);
    }
}
