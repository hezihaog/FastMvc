package oms.mmc.helper.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import oms.mmc.helper.widget.ScrollableGridView;
import oms.mmc.helper.widget.ScrollableListView;
import oms.mmc.helper.widget.ScrollableNestedScrollView;
import oms.mmc.helper.widget.ScrollableRecyclerView;
import oms.mmc.helper.widget.ScrollableScrollView;

/**
 * Package: oms.mmc.android.fast.framwork.adapter
 * FileName: ScrollableLayoutFactory
 * Date: on 2018/2/11  下午6:43
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ScrollableViewFactory implements IScrollableReplaceFactory {
    private Activity mActivity;
    private IScrollableReplaceAdapter mReplaceAdapter;

    private ScrollableViewFactory(Activity activity, IScrollableReplaceAdapter replaceAdapter) {
        mActivity = activity;
        mReplaceAdapter = replaceAdapter;
    }

    /**
     * 创建工厂实例
     */
    public static ScrollableViewFactory create(Activity activity, IScrollableReplaceAdapter replaceAdapter) {
        return new ScrollableViewFactory(activity, replaceAdapter);
    }

    /**
     * 安装工厂，狸猫换太子，如果app原来没有其他替换，可以直接用该方法
     * 注意创建完实例后，调用该方法才能生效
     */
    @Override
    public void install() {
        LayoutInflaterFactory factory = LayoutInflaterCompat.getFactory(LayoutInflater.from(mActivity));
        if (factory == null) {
            LayoutInflaterCompat.setFactory(LayoutInflater.from(mActivity), new LayoutInflaterFactory() {
                @Override
                public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                    return performReplace(parent, name, context, attrs);
                }
            });
        }
    }

    @Override
    public View performReplace(View parent, String name, Context context, AttributeSet attrs) {
        return mReplaceAdapter.onReplace(mActivity, parent, name, context, attrs);
    }

    /**
     * 替换支持的控件为实现了Scrollable接口的类
     */
    public View replace(View parent, String name, Context context, AttributeSet attrs) {
        if ("android.support.v7.widget.RecyclerView".equals(name)) {
            return new ScrollableRecyclerView(context, attrs) {
            };
        } else if ("android.support.v4.widget.NestedScrollView".equals(name)) {
            return new ScrollableNestedScrollView(context, attrs) {
            };
        }
        return null;
    }

    public static class Adapter implements IScrollableReplaceAdapter {

        @Override
        public View onReplace(Activity activity, View parent, String name, Context context, AttributeSet attrs) {
            if ("ListView".equals(name)) {
                return new ScrollableListView(context, attrs) {
                };
            } else if ("GridView".equals(name)) {
                return new ScrollableGridView(context, attrs) {
                };
            } else if ("ScrollView".equals(name)) {
                return new ScrollableScrollView(context, attrs) {
                };
            }
            if ("android.support.v7.widget.RecyclerView".equals(name)) {
                return new ScrollableRecyclerView(context, attrs) {
                };
            } else if ("android.support.v4.widget.NestedScrollView".equals(name)) {
                return new ScrollableNestedScrollView(context, attrs) {
                };
            }
            return null;
        }
    }
}
