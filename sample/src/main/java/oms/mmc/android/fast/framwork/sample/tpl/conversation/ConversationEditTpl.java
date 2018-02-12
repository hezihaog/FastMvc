package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.content.DialogInterface;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import oms.mmc.android.fast.framwork.base.BaseStickyTpl;
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

public class ConversationEditTpl extends BaseStickyTpl<BaseItemData> implements View.OnClickListener {
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
    protected void onRender(BaseItemData itemData) {
        boolean isNormalMode = getListAdapter().isNormalMode();
        if (isNormalMode) {
            editTv.setText(R.string.main_tool_bar_edit_mode_text);
        } else {
            editTv.setText(R.string.main_tool_bar_complete_mode_text);
        }
    }

    @Override
    public void onClick(View v) {
        final List<Integer> checkedItemPositions = getListAdapter().getCheckedItemPositions();
        boolean isEditMode = getListAdapter().isEditMode();
        if (!isEditMode) {
            new ConversationEditStateChangeBroadcast().setEditMode().send(getActivity());
            editTv.setText(R.string.main_tool_bar_complete_mode_text);
        } else {
            if (checkedItemPositions.size() == 0) {
                new ConversationEditStateChangeBroadcast().setNomalMode().send(getActivity());
                editTv.setText(R.string.main_tool_bar_edit_mode_text);
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示");
            builder.setMessage("是否删除这" + checkedItemPositions.size() + "条数据");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new ConversationEditStateChangeBroadcast().setNomalMode().send(getActivity());
                    editTv.setText(R.string.main_tool_bar_edit_mode_text);
                    //不是编辑模式，并且勾选了条目，则删除这些条目
                    if (checkedItemPositions.size() > 0) {
                        for (Integer itemPosition : checkedItemPositions) {
                            getListViewData().remove(itemPosition.intValue());
                        }
                        //清除完条目后，记得将保存选择的位置的集合清空
                        checkedItemPositions.clear();
                        //listViewAdapter.notifyDataSetChanged();
                    }
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    @Override
    public void onAttachSticky() {
        ViewCompat.setElevation(getRoot(), 10);
    }

    @Override
    public void onDetachedSticky() {
        ViewCompat.setElevation(getRoot(), 0);
    }
}