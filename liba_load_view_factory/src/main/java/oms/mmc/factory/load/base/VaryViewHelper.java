package oms.mmc.factory.load.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Package: oms.mmc.factory.load
 * FileName: VaryViewHelper
 * Date: on 2018/2/23  下午8:56
 * Auther: zihe
 * Descirbe:用于切换布局,用一个新的布局替换掉原先的布局
 * Email: hezihao@linghit.com
 */

public class VaryViewHelper implements IVaryViewHelper {
    private View view;
    private ViewGroup parentView;
    private int viewIndex;
    private ViewGroup.LayoutParams params;
    private View currentView;

    public VaryViewHelper(View view) {
        super();
        this.view = view;
    }

    private void init() {
        //保存原来的要替换的View布局参数
        params = view.getLayoutParams();
        if (view.getParent() != null) {
            parentView = (ViewGroup) view.getParent();
        } else {
            parentView = (ViewGroup) view.getRootView().findViewById(android.R.id.content);
        }
        //找出要替换的View的父View上的全部子View
        int count = parentView.getChildCount();
        //找到要替换的View在父View上的位置
        for (int i = 0; i < count; i++) {
            if (view == parentView.getChildAt(i)) {
                viewIndex = i;
                break;
            }
        }
        //保存要替换的View
        currentView = view;
    }

    @Override
    public View getCurrentLayout() {
        return currentView;
    }

    @Override
    public void restoreView() {
        showLayout(view);
    }

    @Override
    public void showLayout(final View view) {
        if (parentView == null) {
            init();
        }
        //将当前View切换
        this.currentView = view;
        //如果已经回去显示原来的view了，那就不需要再进行替换操作了
        if (parentView.getChildAt(viewIndex) != view) {
            final ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            //将要覆盖的View移除
            parentView.removeViewAt(viewIndex);
            //添加覆盖View
            parentView.addView(view, viewIndex, params);
        }
    }

    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(view.getContext()).inflate(layoutId, null);
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
