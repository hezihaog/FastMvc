package oms.mmc.android.fast.framwork.base;


import oms.mmc.android.fast.framwork.bean.IResult;

/**
 * 网络请求回调接口
 */
public interface ApiCallback {
    /**
     * 接口执行前回调
     *
     * @param tag 请求tag，用于区分请求那个接口
     */
    void onApiStart(String tag);

    /**
     * 接口请求中回调
     *
     * @param count   请求数据总大小
     * @param current 当前请求到的数据大小
     * @param tag     请求tag，用于区分请求那个接口
     */
    void onApiLoading(long count, long current, String tag);

    /**
     * 接口请求成功时回调
     *
     * @param res 接口返回的结果封装对象
     * @param tag 请求tag，用于区分请求那个接口
     */
    void onApiSuccess(IResult res, String tag);

    /**
     * 接口请求网络出错时回调
     *
     * @param t       错误
     * @param errorNo 错误号
     * @param strMsg  错误信息
     * @param tag     请求tag，用于区分请求那个接口
     */
    void onApiFailure(Throwable t, int errorNo, String strMsg, String tag);

    /**
     * 接口返回的结构解析为对象出错时回调
     *
     * @param tag 请求tag，用于区分请求那个接口
     */
    void onParseError(String tag);
}
