package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ConversationNewsTpl
 * Date: on 2018/1/25  上午11:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ConversationChatTpl extends BaseTpl<ItemDataWrapper> {
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.msgContent)
    TextView msgContent;

    @Override
    public int onLayoutId() {
        return R.layout.item_conversation_chat_msg;
    }

    @Override
    public void render() {
        String avatarUrl = (String) getBean().getDatas().get(0);
        String nameText = (String) getBean().getDatas().get(1);
        String content = (java.lang.String) getBean().getDatas().get(2);
        Glide.with(getActivity()).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(avatar);
        name.setText(nameText);
        msgContent.setText(content);
    }
}