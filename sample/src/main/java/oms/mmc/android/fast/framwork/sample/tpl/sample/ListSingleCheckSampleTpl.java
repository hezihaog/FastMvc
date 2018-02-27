package oms.mmc.android.fast.framwork.sample.tpl.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.event.SingleCheckEvent;
import oms.mmc.android.fast.framwork.sample.util.EventBusUtil;
import oms.mmc.android.fast.framwork.util.ToastUtil;
import oms.mmc.android.fast.framwork.util.ViewFinder;
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

public class ListSingleCheckSampleTpl extends BaseTpl<BaseItemData> {
    private TextView mTextView;
    private ImageView mSingleCheck;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        EventBusUtil.register(this);
    }

    @Override
    public void onRecyclerViewDetachedFromWindow(View view) {
        super.onRecyclerViewDetachedFromWindow(view);
        EventBusUtil.unregister(this);
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_list_single_check_sample, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        mTextView = finder.get(R.id.textTv);
        mSingleCheck = finder.get(R.id.singleCheck);
    }

    @Override
    protected void onRender(BaseItemData itemData) {
        mTextView.setText("item position " + getPosition());
        int checkedItemPosition = getListAdapter().getCheckedItemPosition();
        if (checkedItemPosition == getPosition()) {
            toggleCheckImage(true);
        } else {
            toggleCheckImage(false);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        boolean isCheck;
        int checkedItemPosition = getListAdapter().getCheckedItemPosition();
        //已经选中，取消选中
        if (checkedItemPosition == getPosition()) {
            getListAdapter().clearCheckedItemPosition();
            isCheck = false;
        } else {
            //未选中，选中
            getListAdapter().setCheckedItemPosition(getPosition());
            isCheck = true;
        }
        EventBusUtil.sendEvent(new SingleCheckEvent(getPosition(), isCheck));
        ToastUtil.showToast(getActivity(), "当前选中的position是 ::: " + getListAdapter().getCheckedItemPosition());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SingleCheckEvent event) {
        //当时自己的时候，才根据状态去切换
        if (event.getPosition() == getPosition()) {
            toggleCheckImage(event.isCheck());
        } else {
            //如果不是自己的，统一都去除
            toggleCheckImage(false);
        }
    }

    private void toggleCheckImage(boolean isCheck) {
        if (isCheck) {
            mSingleCheck.setImageResource(R.drawable.ic_checkbox_checked);
        } else {
            mSingleCheck.setImageResource(R.drawable.ic_checkbox_normal);
        }
    }
}