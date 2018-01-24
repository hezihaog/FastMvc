package oms.mmc.android.fast.framwork.sample.tpl;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl
 * FileName: HeaderTpl
 * Date: on 2018/1/24  下午2:08
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class CommentTpl extends BaseTpl<ItemDataWrapper> {
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.avatar)
    ImageView avatar;

    private String data;

    @Override
    public int onLayoutId() {
        return R.layout.item_text;
    }

    @Override
    public void render() {
        String avatarUrl = (String) getBean().getDatas().get(0);
        data = (String) getBean().getDatas().get(1);
        Glide.with(_activity).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(avatar);
        text.setText(data);
    }
}
