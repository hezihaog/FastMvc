package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.ToastUtil;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ConversationNewsTpl
 * Date: on 2018/1/25  上午11:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ConversationChatTpl extends BaseTpl<ItemDataWrapper> implements CompoundButton.OnCheckedChangeListener {
    ImageView avatar;
    TextView name;
    TextView msgContent;
    private CheckBox checkBox;

    @Override
    public int onLayoutId() {
        return R.layout.item_conversation_chat_msg;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        avatar = finder.get(R.id.avatar);
        name = finder.get(R.id.name);
        msgContent = finder.get(R.id.msgContent);
        checkBox = finder.get(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void render() {
        String avatarUrl = (String) getBean().getDatas().get(0);
        String nameText = (String) getBean().getDatas().get(1);
        String content = (java.lang.String) getBean().getDatas().get(2);
        Glide.with(getActivity()).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(avatar);
        name.setText(nameText);
        msgContent.setText(content);

        if (listViewAdapter.isEditMode()) {
            getViewFinder().setVisibility(R.id.checkBox);
        } else {
            getViewFinder().setGone(R.id.checkBox);
        }
        if (listViewAdapter.getCheckedItemPositions().contains(position)) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    @Override
    protected void onItemClick(View view) {
        super.onItemClick(view);
        ToastUtil.showToast(getActivity(), "onItemClick");
    }

    @Override
    protected void onItemLongClick(View view) {
        super.onItemLongClick(view);
        ToastUtil.showToast(getActivity(), "onItemLongClick");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            listViewAdapter.getCheckedItemPositions().add(position);
        } else {
            listViewAdapter.getCheckedItemPositions().remove(position);
        }
    }
}