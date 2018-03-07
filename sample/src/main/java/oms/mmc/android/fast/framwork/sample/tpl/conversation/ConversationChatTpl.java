package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ConversationNewsTpl
 * Date: on 2018/1/25  上午11:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ConversationChatTpl extends ConversationEditableTpl {
    private ImageView avatar;
    private TextView name;
    private TextView msgContent;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_conversation_chat_msg, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        avatar = finder.get(R.id.avatar);
        name = finder.get(R.id.name);
        msgContent = finder.get(R.id.msgContent);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        super.onRender(itemData);
        String avatarUrl = (String) itemData.getDatas().get(0);
        String nameText = (String) itemData.getDatas().get(1);
        String content = (java.lang.String) itemData.getDatas().get(2);
        loadUrlImage(avatarUrl, avatar, -1);
        setViewText(nameText, name);
        msgContent.setText(content);
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        super.onItemLongClick(view, position);
    }
}