package oms.mmc.android.fast.framwork.sample.tpl;

import android.widget.TextView;

import butterknife.Bind;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl
 * FileName: HeaderTpl
 * Date: on 2018/1/24  下午2:08
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class HeaderTpl extends BaseTpl<ItemDataWrapper> {
    @Bind(R.id.header)
    TextView header;

    private String data;

    @Override
    public int onLayoutId() {
        return R.layout.item_header;
    }

    @Override
    public void render() {
        data = (String) bean.getDatas().get(0);
        header.setText(data);
    }
}