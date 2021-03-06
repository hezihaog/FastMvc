package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl
 * FileName: ConversationTeamTpl
 * Date: on 2018/1/25  上午11:41
 * Auther: zihe
 * Descirbe:会话-订阅号条目
 * Email: hezihao@linghit.com
 */

public class ConversationSubscriptionMsgTpl extends ConversationEditableTpl {

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_conversation_subscription_msg, container, false);
    }
}
