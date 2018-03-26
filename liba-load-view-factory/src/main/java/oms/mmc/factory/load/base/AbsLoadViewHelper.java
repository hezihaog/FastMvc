package oms.mmc.factory.load.base;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import oms.mmc.factory.load.R;
import oms.mmc.factory.load.factory.ILoadViewFactory;

/**
 * Package: oms.mmc.factory.load.base
 * FileName: AbsLoadViewHelper
 * Date: on 2018/2/28  下午5:31
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class AbsLoadViewHelper implements ILoadViewFactory.ILoadView {
    protected VaryViewHelper helper;
    protected View.OnClickListener onClickRefreshListener;
    protected Context context;

    @Override
    public void init(View view, View.OnClickListener onClickRefreshListener) {
        this.helper = new VaryViewHelper(view);
        this.context = view.getContext().getApplicationContext();
        this.onClickRefreshListener = onClickRefreshListener;
    }

    @Override
    public void showLoading() {
        helper.showLayout(onInflateLoadingLayout(helper, onClickRefreshListener));
    }

    @Override
    public void tipFail() {
        Toast.makeText(context, R.string.net_tip_net_load_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        helper.showLayout(onInflateErrorLayout(helper, onClickRefreshListener));
    }

    @Override
    public void showEmpty() {
        helper.showLayout(onInflateEmptyLayout(helper, onClickRefreshListener));
    }

    @Override
    public void restore() {
        helper.restoreView();
    }

    /**
     * 子类返回加载视图
     * @param helper
     * @param onClickRefreshListener
     */
    protected abstract View onInflateLoadingLayout(VaryViewHelper helper, View.OnClickListener onClickRefreshListener);

    /**
     * 子类返回错误视图
     */
    protected abstract View onInflateErrorLayout(VaryViewHelper helper, View.OnClickListener onClickRefreshListener);

    /**
     * 子类返回数据为空视图
     */
    protected abstract View onInflateEmptyLayout(VaryViewHelper helper, View.OnClickListener onClickRefreshListener);

    public VaryViewHelper getHelper() {
        return helper;
    }

    public Context getContext() {
        return context;
    }

    public View.OnClickListener getOnClickRefreshListener() {
        return onClickRefreshListener;
    }
}
