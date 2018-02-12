package oms.mmc.android.fast.framwork.sample.tpl.contact;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;

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
    public int onLayoutId() {
        return R.layout.item_contact;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        contactName = finder.get(R.id.contactName);
        avatar = finder.get(R.id.avatar);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        String avatarUrl = (String) itemData.getDatas().get(0);
        name = (String) itemData.getDatas().get(1);
        Glide.with(mActivity).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(avatar);
        contactName.setText(name);
    }
}
