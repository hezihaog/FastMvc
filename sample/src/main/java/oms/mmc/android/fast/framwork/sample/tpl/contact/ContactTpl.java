package oms.mmc.android.fast.framwork.sample.tpl.contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl
 * FileName: HeaderTpl
 * Date: on 2018/1/24  下午2:08
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ContactTpl extends BaseTpl<ItemDataWrapper> {
    TextView contactName;
    ImageView avatar;

    private String name;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_contact, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        contactName = finder.get(R.id.contactName);
        avatar = finder.get(R.id.avatar);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        String avatarUrl = (String) itemData.getDatas().get(0);
        name = (String) itemData.getDatas().get(1);
        loadUrlImage(avatarUrl, avatar, -1);
        contactName.setText(name);
    }
}
