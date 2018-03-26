package oms.mmc.android.fast.framwork.sample.tpl.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.event.ToggleModeEvent;
import oms.mmc.android.fast.framwork.sample.util.EventBusUtil;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.sample
 * FileName: ListModeSampleTpl
 * Date: on 2018/2/27  下午6:36
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ListModeSampleTpl extends BaseTpl<ItemDataWrapper> {
    private TextView mTextTv;
    private ImageView mCheckIv;

    @Override
    protected void onCreate() {
        super.onCreate();
        EventBusUtil.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_list_mode, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mTextTv = finder.get(R.id.textTv);
        mCheckIv = finder.get(R.id.checkIv);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        setViewText((String) itemData.getDatas().get(0), mTextTv);
        boolean isEditMode = getAssistHelper().isEditMode();
        if (!isEditMode) {
            setGone(mCheckIv);
            toggleCheck(false);
        } else {
            setVisible(mCheckIv);
            HashMap<Integer, Object> checkedItemPositions = getAssistHelper().getCheckedItemPositions();
            if (checkedItemPositions.containsKey(getPosition())) {
                toggleCheck(true);
            } else {
                toggleCheck(false);
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        //不是编辑模式直接返回
        if (!getAssistHelper().isEditMode()) {
            return;
        }
        HashMap<Integer, Object> checkedItemPositions = getAssistHelper().getCheckedItemPositions();
        boolean isCheck;
        //选中了，反选
        if (checkedItemPositions.containsKey(getPosition())) {
            getAssistHelper().getCheckedItemPositions().remove(Integer.valueOf(getPosition()));
            isCheck = false;
        } else {
            //没有选中，选中
            getAssistHelper().getCheckedItemPositions().put(getPosition(), getItemDataBean());
            isCheck = true;
        }
        toggleCheck(isCheck);
    }

    private void toggleCheck(boolean isCheck) {
        if (!isCheck) {
            loadDrawableResId(mCheckIv, R.drawable.ic_checkbox_normal);
        } else {
            loadDrawableResId(mCheckIv, R.drawable.ic_checkbox_checked);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ToggleModeEvent event) {
        boolean isEditMode = event.isEdit();
        if (isEditMode) {
            mCheckIv.setVisibility(View.VISIBLE);
        } else {
            mCheckIv.setVisibility(View.GONE);
        }
    }
}