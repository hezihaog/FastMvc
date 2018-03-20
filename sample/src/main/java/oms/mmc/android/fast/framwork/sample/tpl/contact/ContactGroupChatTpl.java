package oms.mmc.android.fast.framwork.sample.tpl.contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.contact
 * FileName: NewFriendTpl
 * Date: on 2018/1/25  下午7:44
 * Auther: zihe
 * Descirbe:群聊
 * Email: hezihao@linghit.com
 */

public class ContactGroupChatTpl extends BaseTpl<BaseItemData> {

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_contact_group_chat, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
    }

    @Override
    protected void onRender(BaseItemData itemData) {

    }
}