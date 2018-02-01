package oms.mmc.android.fast.framwork.basiclib.lazy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import oms.mmc.android.fast.framwork.basiclib.lazy.inter.FragmentOperationCallback;


/**
 * Package: com.hzh.lazy.fragment
 * FileName: ExtendLazyFragment
 * Date: on 2017/11/24  上午10:44
 * Auther: zihe
 * Descirbe: 继承方式的懒加载Fragment基类
 * Email: hezihao@linghit.com
 */

public abstract class ExtendLazyFragment extends PagerVisibleFragment implements FragmentOperationCallback {
    //Fragment容器
    protected FrameLayout rootContainer;
    private LayoutInflater inflater;
    private Bundle savedInstanceState;
    private boolean isLazyViewCreated = false;
    private boolean isVisible = false;

    /**
     * 已加final，该方法不要重写，请重写{@link #onInflaterRootView(LayoutInflater, ViewGroup, Bundle)} 来返回视图
     *
     * @param inflater           填充器
     * @param container          父容器
     * @param savedInstanceState bundle状态保存map
     * @return
     */
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.savedInstanceState = savedInstanceState;
        rootContainer = new FrameLayout(getContext().getApplicationContext());
        rootContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return rootContainer;
    }

    /**
     * 已加final，该方法不要重写，请重写{@link #onLazyViewCreated(View, Bundle)} ()}来回调已经懒加载完毕
     *
     * @param view               Fragment的视图View
     * @param savedInstanceState bundle状态保存map
     */
    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getUserVisibleHint() && !isLazyViewCreated) {
            startLazy();
        }
    }

    /**
     * 显示，并且是第一次显示，就开始调用{@link #onLazyViewCreated(View, Bundle)}
     *
     * @param isVisibleToUser 是否可见，ture为可见，false为不可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        if (isVisibleToUser && !isLazyViewCreated && inflater != null) {
            startLazy();
        }
        if (getUserVisibleHint()) {
            onFragmentVisible();
        } else {
            onFragmentInvisible();
        }
    }

    /**
     * 开始懒加载
     */
    private void startLazy() {
        //开始懒加载，并且将Fragment的View视图添加到容器
        View view = onInflaterRootView(inflater, rootContainer, savedInstanceState);
        rootContainer.addView(view);
        isLazyViewCreated = true;
        //懒加载完毕
        onLazyViewCreated(rootContainer, savedInstanceState);
        //可以开始查找控件了
        onFindViews(rootContainer);
        //开始控件绑定
        onBindContent();
    }

    /**
     * 是否已经初始化懒加载过
     *
     * @return true为已经懒加载初始化，false为未初始化
     */
    @SuppressWarnings("unused")
    public boolean isLazyViewCreated() {
        return isLazyViewCreated;
    }

    /**
     * fragment销毁，将懒加载标志位归位
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isVisible = false;
        isLazyViewCreated = false;
    }

    /**
     * 用来代替Fragment的onViewCreated，在真正获得用户焦点并且{@link #onLazyViewCreated(View, Bundle)}
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void onLazyViewCreated(View view, @Nullable Bundle savedInstanceState);

    /**
     * Fragment可见时回调，子类按需重写
     */
    protected void onFragmentVisible() {
    }

    /**
     * Fragment不可见时回调，子类按需重写
     */
    protected void onFragmentInvisible() {
    }

    /**
     * 获取Fragment是否可见
     *
     * @return true为可见，false为不可见
     */
    public boolean getFragmentIsVisible() {
        return isVisible;
    }

    @Override
    public void onFindViews(View mRootView) {

    }

    @Override
    public void onBindContent() {

    }
}
