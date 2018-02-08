package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.broadcast.ConversationEditStateChangeBroadcast;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ConversationEditTpl
 * Date: on 2018/2/8  下午6:48
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ConversationEditTpl extends BaseTpl<BaseItemData> implements View.OnClickListener {
    private TextView editTv;

    @Override
    public int onLayoutId() {
        return R.layout.item_conversation_edit;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        editTv = finder.get(R.id.editTv);
        editTv.setOnClickListener(this);
    }

    @Override
    public void render() {

    }

    @Override
    public void onClick(View v) {
        ArrayList<Integer> checkedItemPositions = listViewAdapter.getCheckedItemPositions();
        boolean isEditMode = listViewAdapter.isEditMode();
        if (!isEditMode) {
            new ConversationEditStateChangeBroadcast().setEditMode().send(getActivity());
            editTv.setText(R.string.main_tool_bar_complete_mode_text);
        } else {
            new ConversationEditStateChangeBroadcast().setNomalMode().send(getActivity());
            editTv.setText(R.string.main_tool_bar_edit_mode_text);
            //不是编辑模式，并且勾选了条目，则删除这些条目
            if (checkedItemPositions.size() > 0) {
                for (Integer itemPosition : checkedItemPositions) {
                    listViewData.remove(itemPosition.intValue());
                }
                //清除完条目后，记得将保存选择的位置的集合清空
                checkedItemPositions.clear();
                listViewAdapter.notifyDataSetChanged();
            }
        }
    }
}