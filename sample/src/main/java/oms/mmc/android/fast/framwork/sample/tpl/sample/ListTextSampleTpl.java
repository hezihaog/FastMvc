package oms.mmc.android.fast.framwork.sample.tpl.sample;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.BaseFastApplication;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseStickyTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.sample
 * FileName: BaseListTplSample
 * Date: on 2018/2/27  下午3:32
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ListTextSampleTpl extends BaseStickyTpl<ItemDataWrapper> {
    private TextView mSampleTextTv;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_base_list_text_sample, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        mSampleTextTv = finder.get(R.id.sampleText);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        //获取条目数据中的数据
        String data = (String) itemData.getDatas().get(0);
        setViewText(data, mSampleTextTv);
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        showWaitDialog();
        toast("tpl -- onItemClick");
        BaseFastApplication.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideWaitDialog();
            }
        }, 1500);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        super.onItemLongClick(view, position);
        toast("tpl -- onItemLongClick");
        getListScrollHelper().smoothMoveToTop();
    }

    @Override
    public void onAttachSticky() {
        ViewCompat.setElevation(getRoot(), dip2px(getActivity(), 15f));
    }

    @Override
    public void onDetachedSticky() {
        ViewCompat.setElevation(getRoot(), 0f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
}