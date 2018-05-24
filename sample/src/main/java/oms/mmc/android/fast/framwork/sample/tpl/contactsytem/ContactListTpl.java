package oms.mmc.android.fast.framwork.sample.tpl.contactsytem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.bean.ContactList;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.contactsytem
 * FileName: ContactListTpl
 * Date: on 2018/5/24  上午7:54
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ContactListTpl extends BaseTpl<ItemDataWrapper> {
    private TextView mNameTv;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_contact_list_tpl, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mNameTv = finder.get(R.id.name_tv);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        ContactList.Contact contact = (ContactList.Contact) itemData.getDatas().get(0);
        setViewText(contact.getName(), mNameTv);
    }
}
