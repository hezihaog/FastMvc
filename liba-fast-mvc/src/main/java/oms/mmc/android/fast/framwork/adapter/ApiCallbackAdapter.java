package oms.mmc.android.fast.framwork.adapter;


import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.base.ApiCallback;
import oms.mmc.android.fast.framwork.util.ToastUtil;
import oms.mmc.android.fast.framwork.bean.IResult;

/**
 * 网络请求回调接口适配器
 */
public class ApiCallbackAdapter implements ApiCallback {
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
        ToastUtil.showToast(BaseMMCFastApplication.context(), R.string.net_tip_net_request_error);
        t.printStackTrace();
        onApiError(tag);
    }

    @Override
    public void onParseError(String tag) {
        ToastUtil.showToast(BaseMMCFastApplication.context(), R.string.net_tip_net_parse_data_error);
        onApiError(tag);
    }

    protected void onApiError(String tag) {
    }
}
