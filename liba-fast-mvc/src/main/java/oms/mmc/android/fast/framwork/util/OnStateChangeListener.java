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
package oms.mmc.android.fast.framwork.util;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;

/**
 * 界面加载切换的的状态监听器，开始刷新、结束刷新、开始加载更多、结束更多
 */
public interface OnStateChangeListener<T> {
    /**
     * 开始刷新
     *
     * @param adapter 列表适配器
     * @param isFirst 是否是第一次刷新
     */
    void onStartRefresh(ICommonListAdapter<T> adapter, boolean isFirst, boolean isReverse);

    /**
     * 结束刷新
     *
     * @param adapter 列表适配器
     * @param result  结果集
     * @param isFirst 是否是第一次刷新
     */
    void onEndRefresh(ICommonListAdapter<T> adapter, ArrayList<T> result, boolean isFirst, boolean isReverse);

    /**
     * 开始加载更多
     *
     * @param adapter 列表适配器
     * @param isFirst 是否是第一次加载更多
     */
    void onStartLoadMore(ICommonListAdapter<T> adapter, boolean isFirst, boolean isReverse);

    /**
     * 结束加载更多
     *
     * @param adapter 列表适配器
     * @param result  结果集
     * @param isFirst 是否是第一次加载更多
     */
    void onEndLoadMore(ICommonListAdapter<T> adapter, ArrayList<T> result, boolean isFirst, boolean isReverse);
}