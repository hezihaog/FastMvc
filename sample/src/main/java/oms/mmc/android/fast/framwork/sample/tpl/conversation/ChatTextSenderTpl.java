package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.util.ViewUtil;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ChatTextSenderTpl
 * Date: on 2018/2/11  上午10:51
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ChatTextSenderTpl extends BaseTpl<ItemDataWrapper> {
    private ImageView avatarIv;
    private TextView nameTv;
    private TextView contentTv;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_chat_text_sender, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        avatarIv = finder.get(R.id.avatar);
        nameTv = finder.get(R.id.name);
        contentTv = finder.get(R.id.content);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        String avatarUrl = (String) itemData.getDatas().get(0);
        String name = (String) itemData.getDatas().get(1);
        String content = (String) itemData.getDatas().get(2);
        loadUrlImage(avatarUrl, avatarIv, -1);
        Glide.with(getActivity()).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(avatarIv);
        ViewUtil.setText(name, nameTv);
        ViewUtil.setText(content, contentTv);
    }
}
