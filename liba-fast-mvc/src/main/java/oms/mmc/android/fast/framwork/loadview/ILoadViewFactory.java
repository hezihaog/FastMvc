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
package oms.mmc.android.fast.framwork.loadview;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 布局切换工厂
 */
public interface ILoadViewFactory {
    /**
     * 构建一个界面切换加载器
     */
    ILoadView madeLoadView();

    /**
     * 切换加载中，加载失败等布局
     */
    interface ILoadView {
        /**
         * 初始化
         */
        void init(View view, OnClickListener onClickRefreshListener);

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
         * 没有数据的时候，toast提示失败
         */
        void tipFail();

        /**
         * 显示原先的布局
         */
        void restore();
    }
}