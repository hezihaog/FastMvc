package oms.mmc.android.fast.framwork.sample.tpl;

import android.widget.TextView;

import butterknife.Bind;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ObjectWrapper;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl
 * FileName: HeaderTpl
 * Date: on 2018/1/24  下午2:08
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class FooterTpl extends BaseTpl<ObjectWrapper> {
    @Bind(R.id.footer)
    TextView footer;

    @Override
    public int onLayoutId() {
        return R.layout.item_footer;
    }

    @Override
    public void render() {
        footer.setText("footer" + position);
    }
}
