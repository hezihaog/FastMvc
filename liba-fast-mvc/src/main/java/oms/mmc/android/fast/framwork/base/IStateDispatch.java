package oms.mmc.android.fast.framwork.base;

/**
 * Created by Hezihao on 2017/7/26.
 * Activity实例重建回调统一接口
 */

public interface IStateDispatch {
    /**
     * 添加状态保存、恢复监听
     */
    void addStateListener(InstanceStateCallback callback);

    /**
     * 移除状态保存、恢复监听
     */
    void removeStateListener(InstanceStateCallback callback);
}