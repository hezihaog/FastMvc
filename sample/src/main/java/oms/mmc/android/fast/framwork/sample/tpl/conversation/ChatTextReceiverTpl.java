package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.basiclib.util.ViewUtil;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ChatTextReceiverTpl
 * Date: on 2018/2/11  上午10:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ChatTextReceiverTpl extends BaseTpl<ItemDataWrapper> {
    private ImageView avatarIv;
    private TextView nameTv;
    private TextView contentTv;

    @Override
    public int onLayoutId() {
        return R.layout.item_chat_text_receiver;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        avatarIv = finder.get(R.id.avatar);
        nameTv = finder.get(R.id.name);
        contentTv = finder.get(R.id.content);
    }

    @Override
    public void render() {
        ItemDataWrapper bean = getBean();
        String avatarUrl = (String) bean.getDatas().get(0);
        String name = (String) bean.getDatas().get(1);
        String content = (String) bean.getDatas().get(2);
        Glide.with(mActivity).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(avatarIv);
        ViewUtil.setText(name, nameTv);
        ViewUtil.setText(content, contentTv);
    }
}
