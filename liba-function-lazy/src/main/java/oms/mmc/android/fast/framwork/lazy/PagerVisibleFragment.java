package oms.mmc.android.fast.framwork.lazy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import oms.mmc.android.lifecycle.dispatch.base.LifecycleFragment;

/**
 * 切换fragment可见回调的fragment
 */
public abstract class PagerVisibleFragment extends LifecycleFragment {
    private Set<OnFragmentVisibleChangeCallback> visibleCallbacks = Collections.newSetFromMap(new HashMap<OnFragmentVisibleChangeCallback, Boolean>());
    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    protected View rootView;

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeAllVisibleCallbacks();
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见 false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        for (OnFragmentVisibleChangeCallback callback : getSnapshot(visibleCallbacks)) {
            callback.onFragmentVisibleChange(getClass().getName(), isVisible);
        }
    }


    public interface OnFragmentVisibleChangeCallback {
        void onFragmentVisibleChange(String name, boolean isVisible);
    }

    public void addVisibleChangeCallback(OnFragmentVisibleChangeCallback callback) {
        visibleCallbacks.add(callback);
    }

    public void removeVisibleChangeCallback(OnFragmentVisibleChangeCallback callback) {
        visibleCallbacks.remove(callback);
    }

    private void removeAllVisibleCallbacks() {
        visibleCallbacks.clear();
    }

    public static <T> List<T> getSnapshot(Collection<T> other) {
        // toArray creates a new ArrayList internally and this way we can guarantee entries will not
        // be null. See #322.
        List<T> result = new ArrayList<T>(other.size());
        for (T item : other) {
            result.add(item);
        }
        return result;
    }
}