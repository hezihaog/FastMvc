package oms.mmc.android.fast.framwork.sample.tpl.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.sample
 * FileName: MainSampleTextTpl
 * Date: on 2018/3/22  上午12:08
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ShoppingCartSampleTextTpl extends BaseTpl<ItemDataWrapper> {
    private TextView mItemTextTv;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.tpl_sample_shopping_cart_text, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mItemTextTv = finder.get(R.id.itemTextTv);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        setViewText((String) itemData.getDatas().get(0), mItemTextTv);
    }
}