package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ConversationTeamTpl
 * Date: on 2018/1/25  上午11:53
 * Auther: zihe
 * Descirbe:微信团队消息
 * Email: hezihao@linghit.com
 */

public class ConversationWeChatTeamChatMsgTpl extends BaseTpl<BaseItemData> implements CompoundButton.OnCheckedChangeListener {
    private CheckBox checkBox;

    @Override
    public int onLayoutId() {
        return R.layout.item_conversation_we_chat_team_msg;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        checkBox = finder.get(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onRender(BaseItemData itemData) {
        if (getListAdapter().isEditMode()) {
            getViewFinder().setVisibility(R.id.checkBox);
        } else {
            getViewFinder().setGone(R.id.checkBox);
        }
        if (getListAdapter().getCheckedItemPositions().contains(getPosition())) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        List<Integer> checkedItemPositions = getListAdapter().getCheckedItemPositions();
        if (isChecked) {
            if (!checkedItemPositions.contains(getPosition())) {
                getListAdapter().getCheckedItemPositions().add(getPosition());
            }
        } else {
            if (checkedItemPositions.contains(getPosition())) {
                checkedItemPositions.remove(getPosition());
            }
        }
    }
}