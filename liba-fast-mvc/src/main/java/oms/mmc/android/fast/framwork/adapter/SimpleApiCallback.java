package oms.mmc.android.fast.framwork.adapter;


import oms.mmc.android.fast.framwork.base.ApiCallback;
import oms.mmc.android.fast.framwork.bean.IResult;
import oms.mmc.android.fast.framwork.logger.L;

/**
 * 网络请求回调接口适配器
 */
public class SimpleApiCallback implements ApiCallback {
    @Override
    public void onApiStart(String tag) {
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {
    }

    @Override
    public void onApiSuccess(IResult res, String tag) {
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        L.e("网络请求错误");
        onApiError(tag);
    }

    @Override
    public void onParseError(String tag) {
        L.e("数据解析错误");
        onApiError(tag);
    }

    protected void onApiError(String tag) {
    }
}
