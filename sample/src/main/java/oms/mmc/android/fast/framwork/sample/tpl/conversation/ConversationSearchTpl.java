package oms.mmc.android.fast.framwork.sample.tpl.conversation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.conversation
 * FileName: ConversationTeamTpl
 * Date: on 2018/1/25  上午11:53
 * Auther: zihe
 * Descirbe:会话-搜索
 * Email: hezihao@linghit.com
 */

public class ConversationSearchTpl extends BaseTpl<BaseItemData> {
    private static final String KEY_EDIT = "key_edit_text_content";

    private EditText mSearchEdit;

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mSearchEdit = finder.get(R.id.searchEdit);
    }

    @Override
    public void onSaveState(Bundle stateBundle) {
        super.onSaveState(stateBundle);
        mSearchEdit = getViewFinder().get(R.id.searchEdit);
        stateBundle.putString(KEY_EDIT, String.valueOf(getViewText(mSearchEdit)));
    }

    @Override
    public void onRestoreState(Bundle stateBundle) {
        super.onRestoreState(stateBundle);
        mSearchEdit = getViewFinder().get(R.id.searchEdit);
        String content = stateBundle.getString(KEY_EDIT);
        setViewText(content, mSearchEdit);
    }

    @Override
    protected void onRender(BaseItemData itemData) {
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_conversation_search, container, false);
    }
}