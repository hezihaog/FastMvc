package oms.mmc.android.fast.framwork.sample.tpl.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Set;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.event.MultipleCheckEvent;
import oms.mmc.android.fast.framwork.sample.util.EventBusUtil;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.sample
 * FileName: ListSingleCheckSampleTpl
 * Date: on 2018/2/27  下午5:01
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ListMultipleCheckSampleTpl extends BaseTpl<BaseItemData> {
    private TextView mTextView;
    private ImageView mMultipleCheck;

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
        return inflater.inflate(R.layout.item_list_multiple_check_sample, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mTextView = finder.get(R.id.textTv);
        mMultipleCheck = finder.get(R.id.multipleCheck);
    }

    @Override
    protected void onRender(BaseItemData itemData) {
        setViewText("item position " + getPosition(), mTextView);
        HashMap<Integer, Object> checkedItemPositions = getAssistHelper().getCheckedItemPositions();
        if (checkedItemPositions.containsKey(getPosition())) {
            toggleCheckImage(true);
        } else {
            toggleCheckImage(false);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        //不是编辑模式直接返回
        if (!getAssistHelper().isEditMode()) {
            return;
        }
        boolean isCheck;
        HashMap<Integer, Object> checkedItemPositions = getAssistHelper().getCheckedItemPositions();
        //已经选中，取消选中
        if (checkedItemPositions.containsKey(getPosition())) {
            getAssistHelper().getCheckedItemPositions().remove(getPosition());
            isCheck = false;
        } else {
            //未选中，选中
            getAssistHelper().getCheckedItemPositions().put(getPosition(), getItemDataBean());
            isCheck = true;
        }
        EventBusUtil.sendEvent(new MultipleCheckEvent(getPosition(), isCheck));
        Set<Integer> integers = getAssistHelper().getCheckedItemPositions().keySet();
        toast("当前选中的position是 ::: " + integers.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MultipleCheckEvent event) {
        //当是自己的时候，才根据状态去切换
        if (event.getPosition() == getPosition()) {
            toggleCheckImage(event.isCheck());
        }
    }

    private void toggleCheckImage(boolean isCheck) {
        if (isCheck) {
            loadDrawableResId(mMultipleCheck, R.drawable.ic_checkbox_checked);
        } else {
            loadDrawableResId(mMultipleCheck, R.drawable.ic_checkbox_normal);
        }
    }
}