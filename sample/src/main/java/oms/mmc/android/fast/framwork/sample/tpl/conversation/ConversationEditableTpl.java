package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;

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
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        checkBox = finder.get(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
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
        Integer position = Integer.valueOf(getPosition());
        if (isChecked) {
            if (!checkedItemPositions.contains(position)) {
                getListAdapter().getCheckedItemPositions().add(position);
            }
        } else {
            if (checkedItemPositions.contains(position)) {
                checkedItemPositions.remove(position);
            }
        }
    }
}