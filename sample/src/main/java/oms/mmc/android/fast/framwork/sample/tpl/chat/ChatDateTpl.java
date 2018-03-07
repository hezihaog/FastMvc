package oms.mmc.android.fast.framwork.sample.tpl.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

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
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_chat_date, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        dateTV = finder.get(R.id.date);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        String date = (String) itemData.getDatas().get(0);
        setViewText(date, dateTV);
    }
}
