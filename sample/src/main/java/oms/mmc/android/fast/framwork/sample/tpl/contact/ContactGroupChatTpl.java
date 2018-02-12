package oms.mmc.android.fast.framwork.sample.tpl.contact;

import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.sample.R;

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
    public int onLayoutId() {
        return R.layout.item_contact_group_chat;
    }

    @Override
    public void onFindView(ViewFinder finder) {
    }

    @Override
    protected void onRender(BaseItemData itemData) {

    }
}