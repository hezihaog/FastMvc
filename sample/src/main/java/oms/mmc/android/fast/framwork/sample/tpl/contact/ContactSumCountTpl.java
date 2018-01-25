package oms.mmc.android.fast.framwork.sample.tpl.contact;

import android.widget.TextView;

import butterknife.Bind;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
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
    @Bind(R.id.count)
    TextView count;

    @Override
    public int onLayoutId() {
        return R.layout.item_contact_sum_count;
    }

    @Override
    public void render() {
        String sumCount = (String) getBean().getDatas().get(0);
        count.setText(sumCount + "位联系人");
    }
}