package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.HashMap;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ConversationEditableTpl
 * Date: on 2018/2/13  下午8:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class ConversationEditableTpl extends BaseTpl<ItemDataWrapper> implements CompoundButton.OnCheckedChangeListener {
    private CheckBox checkBox;

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        checkBox = finder.get(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        if (getAssistHelper().isEditMode()) {
            getViewFinder().setVisible(R.id.checkBox);
        } else {
            getViewFinder().setGone(R.id.checkBox);
        }
        if (getAssistHelper().getCheckedItemPositions().containsKey(getPosition())) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        HashMap<Integer, Object> checkedItemPositions = getAssistHelper().getCheckedItemPositions();
        Integer position = getPosition();
        if (isChecked) {
            if (!checkedItemPositions.containsKey(position)) {
                getAssistHelper().getCheckedItemPositions().put(position, getItemDataBean());
            }
        } else {
            if (checkedItemPositions.containsKey(position)) {
                checkedItemPositions.remove(position);
            }
        }
    }
}