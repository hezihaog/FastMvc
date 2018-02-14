package oms.mmc.android.fast.framwork.sample.tpl.chat;

import android.widget.TextView;

import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.basiclib.util.ViewUtil;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.chat
 * FileName: ChatDateTpl
 * Date: on 2018/2/11  上午10:59
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ChatDateTpl extends BaseTpl<ItemDataWrapper> {
    private TextView dateTV;

    @Override
    public int onLayoutId() {
        return R.layout.item_chat_date;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        dateTV = finder.get(R.id.date);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        String date = (String) itemData.getDatas().get(0);
        ViewUtil.setText(date, dateTV);
    }
}
