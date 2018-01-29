/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package oms.mmc.android.fast.framwork.widget.pulltorefresh.helper;

import android.view.View.OnClickListener;
import android.widget.AbsListView;

/**
 * 布局切换工厂
 */
public interface ILoadViewFactory {
    ILoadMoreView madeLoadMoreView();

    ILoadView madeLoadView();

    /**
     * 切换加载中，加载失败等布局
     */
    interface ILoadView {
        /**
         * 初始化
         */
        void init(AbsListView mListView, OnClickListener onClickRefreshListener);

        /**
         * 显示加载中
         */
        void showLoading();

        /**
         * 显示无网络加载失败
         */
        void showFail();

        /**
         * 显示空数据布局
         */
        void showEmpty();

        /**
         * 有数据的时候，toast提示失败
         */
        void tipFail();

        /**
         * 显示原先的布局
         */
        void restore();
    }

    /**
     * ListView底部加载更多的布局切换
     */
    interface ILoadMoreView {
        /**
         * 初始化
         *
         * @param mListView
         * @param onClickLoadMoreListener 加载更多的点击事件，需要点击调用加载更多的按钮都可以设置这个监听
         */
        void init(AbsListView mListView, OnClickListener onClickLoadMoreListener);

        /**
         * 显示普通保布局
         */
        void showNormal();

        /**
         * 显示已经加载完成，没有更多数据的布局
         */
        void showNomore();

        /**
         * 显示正在加载中的布局
         */
        void showLoading();

        /**
         * 显示加载失败的布局
         */
        void showFail();
    }
}
