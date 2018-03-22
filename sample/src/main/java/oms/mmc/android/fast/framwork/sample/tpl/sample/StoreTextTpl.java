package oms.mmc.android.fast.framwork.sample.tpl.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.sample
 * FileName: StoreTextTpl
 * Date: on 2018/3/22  上午11:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class StoreTextTpl extends BaseTpl<ItemDataWrapper> {
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.tpl_sample_store_text, container, false);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        String data = (String) itemData.getDatas().get(0);
        TextView textView = getViewFinder().get(R.id.itemTextTv);
        setViewText(data, textView);
    }
}