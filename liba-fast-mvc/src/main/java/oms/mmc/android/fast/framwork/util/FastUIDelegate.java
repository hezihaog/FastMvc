package oms.mmc.android.fast.framwork.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import oms.mmc.android.fast.framwork.base.IFastUIDelegate;
import oms.mmc.android.fast.framwork.base.IFastUIInterface;
import oms.mmc.android.fast.framwork.base.IStatusBarHost;
import oms.mmc.android.fast.framwork.widget.lv.ScrollablePinnedSectionListView;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout;
import oms.mmc.factory.wait.WaitDialogController;
import oms.mmc.factory.wait.inter.IWaitViewController;
import oms.mmc.helper.base.ScrollableViewFactory;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: IFastUIDelegate
 * Date: on 2018/3/20  下午5:20
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class FastUIDelegate implements IFastUIDelegate {
    private IFastUIInterface mUiIml;
    private Activity mActivity;
    private ViewFinder mViewFinder;
    private Handler mUIHandler;
    private WaitDialogController mWaitDialogController;

    private static class FastUIReplaceAdapter extends AppCompatScrollableReplaceAdapter {
        @Override
        public View onReplace(Activity activity, View parent, String name, Context context, AttributeSet attrs) {
            View view = null;
            //为了兼容旧版框架中没有封装下拉刷新而做的批量替换掉原生的下拉刷新控件为我们封装的对应的刷新控件
            if ("android.support.v4.widget.SwipeRefreshLayout".equals(name)) {
                view = new SwipePullRefreshLayout(activity, attrs);
            } else if ("ListView".equals(name)) {
                //替换原生ListView为支持粘性条目的ListView
                view = new ScrollablePinnedSectionListView(activity, attrs);
            }
            if (view == null) {
                return super.onReplace(activity, parent, name, context, attrs);
            }
            return view;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mUiIml != null) {
            //只在Activity时安装，Fragment无需调用
            if (mUiIml instanceof Activity) {
                ScrollableViewFactory.create(getActivity(), new FastUIReplaceAdapter()).install();
            }
        }
    }

    @Override
    public void attachUIIml(IFastUIInterface uiIml) {
        mUiIml = uiIml;
        if (uiIml instanceof Activity) {
            mActivity = (Activity) mUiIml;
        } else if (uiIml instanceof Fragment) {
            mActivity = ((Fragment) uiIml).getActivity();
        } else if (uiIml instanceof android.support.v4.app.Fragment) {
            mActivity = ((android.support.v4.app.Fragment) uiIml).getActivity();
        }
    }

    @Override
    public void performLayoutBefore() {
        mUiIml.onLayoutBefore();
    }

    @Override
    public void performLayoutView(ViewGroup container) {
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(),
                    mUiIml.onLayoutView(LayoutInflater.from(getActivity()), container));
        } else {
            mViewFinder.setActivity(getActivity());
            mViewFinder.setRootView(getContentView());
        }
        mWaitDialogController = mUiIml.onWaitDialogFactoryReady().madeWaitDialogController(getActivity());
    }

    @Override
    public void performFindView() {
        mUiIml.onFindView(mViewFinder);
    }

    @Override
    public void performLayoutAfter() {
        mUiIml.onLayoutAfter();
    }

    @Override
    public void onDestroy() {
        TDevice.hideSoftKeyboard(getContentView());
        if (mViewFinder != null) {
            mViewFinder.recycle();
        }
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public View getContentView() {
        return ((ViewGroup) getWindow().findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    public ViewFinder getViewFinder() {
        return mViewFinder;
    }

    @Override
    public IWaitViewController getWaitViewController() {
        if (mWaitDialogController == null) {
            mWaitDialogController = mUiIml.onWaitDialogFactoryReady().madeWaitDialogController(getActivity());
        }
        return mWaitDialogController;
    }

    @Override
    public View getRootView() {
        return mViewFinder.getRootView();
    }

    @Override
    public Window getWindow() {
        return mActivity.getWindow();
    }

    @Override
    public void configStatusBar() {
        if (mUiIml instanceof IStatusBarHost) {
            IStatusBarHost host = (IStatusBarHost) mUiIml;
            if (host.hasTranslucentStatusBar()) {
                setTranslucentStatusBar();
                setBlackStatusBar();
            }
        }
    }

    @Override
    public Handler initUiHandler() {
        return new Handler(getActivity().getMainLooper());
    }

    @Override
    public Handler getUiHandler() {
        if (mUIHandler == null) {
            return initUiHandler();
        }
        return mUIHandler;
    }

    @Override
    public void post(Runnable runnable) {
        if (mUIHandler == null) {
            mUIHandler = initUiHandler();
        }
        mUIHandler.post(runnable);
    }

    @Override
    public void postDelayed(Runnable runnable, long duration) {
        if (mUIHandler == null) {
            mUIHandler = initUiHandler();
        }
        mUIHandler.postDelayed(runnable, duration);
    }

    @Override
    public void removeUiHandlerMessage(Runnable runnable) {
        getUiHandler().removeCallbacks(runnable);
    }

    @Override
    public void removeUiHandlerAllMessage() {
        getUiHandler().removeCallbacksAndMessages(null);
    }

    /**
     * 设置透明状态栏
     */
    @Override
    public void setTranslucentStatusBar() {
        TDevice.setTranslucentStatusBar(getActivity());
    }

    /**
     * 设置状态栏文字黑色，暂只支持小米、魅族
     */
    @Override
    public void setBlackStatusBar() {
        TDevice.setStatusBarMode(getActivity(), true);
    }

    @Override
    public void hideStatusBar() {
        TDevice.hideStatusBar(getActivity());
    }

    @Override
    public void showStatusBar() {
        TDevice.showStatusBar(getActivity());
    }
}
