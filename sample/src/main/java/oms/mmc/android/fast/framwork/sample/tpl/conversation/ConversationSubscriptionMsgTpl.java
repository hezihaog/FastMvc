package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl
 * FileName: ConversationTeamTpl
 * Date: on 2018/1/25  上午11:41
 * Auther: zihe
 * Descirbe:会话-订阅号条目
 * Email: hezihao@linghit.com
 */

public class ConversationSubscriptionMsgTpl extends BaseTpl<BaseItemData> implements CompoundButton.OnCheckedChangeListener {
    private CheckBox checkBox;

    @Override
    public int onLayoutId() {
        return R.layout.item_conversation_subscription_msg;
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
        if (isChecked) {
            getListAdapter().getCheckedItemPositions().add(getPosition());
        } else {
            getListAdapter().getCheckedItemPositions().remove(getPosition());
        }
    }
}
