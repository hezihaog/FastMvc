package oms.mmc.android.fast.framwork.sample.tpl.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.sample
 * FileName: BaseListImageTplSample
 * Date: on 2018/2/27  下午4:33
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BaseListImageTplSample extends BaseTpl<ItemDataWrapper> {
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_base_list_image_sample, container, false);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
    }
}
