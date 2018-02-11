package oms.mmc.android.fast.framwork.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import oms.mmc.android.fast.framwork.widget.view.ScrollableGridView;
import oms.mmc.android.fast.framwork.widget.view.ScrollableListView;
import oms.mmc.android.fast.framwork.widget.view.ScrollableNestedScrollView;
import oms.mmc.android.fast.framwork.widget.view.ScrollableRecyclerView;
import oms.mmc.android.fast.framwork.widget.view.ScrollableScrollView;

/**
 * Package: oms.mmc.android.fast.framwork.adapter
 * FileName: ScrollableLayoutFactory
 * Date: on 2018/2/11  下午6:43
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ScrollableLayoutFactory {
    private Context mActivity;

    private ScrollableLayoutFactory(Activity activity) {
        mActivity = activity;
    }

    public static ScrollableLayoutFactory create(Activity activity) {
        return new ScrollableLayoutFactory(activity);
    }

    /**
     * 安装工厂，狸猫换太子，如果app原来没有其他替换，可以直接用该方法
     */
    public void install() {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(mActivity), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                //L.d("name ::: " + name);
                View replaceView = replace(parent, name, context, attrs);
                if (replaceView == null) {
                    if (mActivity instanceof AppCompatActivity) {
                        AppCompatDelegate delegate = ((AppCompatActivity) mActivity).getDelegate();
                        return delegate.createView(parent, name, context, attrs);
                    } else {
                        return null;
                    }
                } else {
                    return replaceView;
                }
            }
        });
    }

    /**
     * 替换支持的控件为实现了Scrollable接口的类
     */
    public View replace(View parent, String name, Context context, AttributeSet attrs) {
        if (name.equals("android.support.v7.widget.RecyclerView")) {
            return new ScrollableRecyclerView(context, attrs) {
            };
        } else if (name.equals("android.support.v4.widget.NestedScrollView")) {
            return new ScrollableNestedScrollView(context, attrs) {
            };
        } else if (name.equals("ListView")) {
            return new ScrollableListView(context, attrs) {
            };
        } else if (name.equals("GridView")) {
            return new ScrollableGridView(context, attrs) {
            };
        } else if (name.equals("ScrollView")) {
            return new ScrollableScrollView(context, attrs) {
            };
        }
        return null;
    }
}
