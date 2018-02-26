package oms.mmc.android.fast.framwork.sample.tpl.contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.contact
 * FileName: ContactSumCountTpl
 * Date: on 2018/1/25  下午7:36
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ContactSumCountTpl extends BaseTpl<ItemDataWrapper> {
    TextView count;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_contact_sum_count, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        count = finder.get(R.id.count);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        String sumCount = (String) itemData.getDatas().get(0);
        count.setText(sumCount + "位联系人");
    }
}