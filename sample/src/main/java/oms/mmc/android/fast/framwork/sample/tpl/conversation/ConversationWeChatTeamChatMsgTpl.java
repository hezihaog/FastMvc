package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ConversationTeamTpl
 * Date: on 2018/1/25  上午11:53
 * Auther: zihe
 * Descirbe:微信团队消息
 * Email: hezihao@linghit.com
 */

public class ConversationWeChatTeamChatMsgTpl extends ConversationEditableTpl {

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_conversation_we_chat_team_msg, container, false);
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        getListScrollHelper().smoothMoveToTop();
    }
}