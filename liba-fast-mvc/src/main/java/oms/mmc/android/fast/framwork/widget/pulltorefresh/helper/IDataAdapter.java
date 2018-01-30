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

import android.support.v7.widget.RecyclerView;

public interface IDataAdapter<T> {
    /**
     * 设置数据集
     *
     * @param data
     * @param isRefresh
     * @param isReverse
     */
    void setListViewData(T data, boolean isRefresh, boolean isReverse);

    /**
     * 获取数据集
     */
    T getListViewData();

    /**
     * 刷新数据集
     */
    void notifyDataSetChanged();

    /**
     * adapter中数据是否为空
     */
    boolean isEmpty();

    /**
     * 获取rv的adapter，其实就是自身
     */
    RecyclerView.Adapter getAdapter();
}