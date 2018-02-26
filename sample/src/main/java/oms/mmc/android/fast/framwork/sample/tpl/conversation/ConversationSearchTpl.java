package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ConversationTeamTpl
 * Date: on 2018/1/25  上午11:53
 * Auther: zihe
 * Descirbe:会话-搜索
 * Email: hezihao@linghit.com
 */

public class ConversationSearchTpl extends BaseTpl<BaseItemData> {

    @Override
    protected void onRender(BaseItemData itemData) {

    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_conversation_search, container, false);
    }
}