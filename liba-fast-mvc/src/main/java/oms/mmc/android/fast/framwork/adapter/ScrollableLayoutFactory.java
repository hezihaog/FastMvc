package oms.mmc.android.fast.framwork.adapter;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
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
    private Context mContext;

    private ScrollableLayoutFactory(Context context) {
        mContext = context;
    }

    public static ScrollableLayoutFactory create(Context context) {
        return new ScrollableLayoutFactory(context);
    }

    /**
     * 安装工厂，狸猫换太子
     */
    public void install() {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(mContext), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                //L.d("name ::: " + name);
                if (name.equals("android.support.v7.widget.RecyclerView")) {
                    return new ScrollableRecyclerView(mContext, attrs) {
                    };
                } else if (name.equals("android.support.v4.widget.NestedScrollView")) {
                    return new ScrollableNestedScrollView(mContext, attrs) {
                    };
                } else if (name.equals("ListView")) {
                    return new ScrollableListView(mContext, attrs) {
                    };
                } else if (name.equals("GridView")) {
                    return new ScrollableGridView(mContext, attrs) {
                    };
                } else if (name.equals("ScrollView")) {
                    return new ScrollableScrollView(mContext, attrs) {
                    };
                }
                return null;
            }
        });
    }
}
