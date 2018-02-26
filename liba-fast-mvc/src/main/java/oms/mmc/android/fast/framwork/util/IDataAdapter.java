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

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 列表控件的adapter接口
 */
public interface IDataAdapter<T, VH extends RecyclerView.ViewHolder> {
    /**
     * 设置下拉刷新数据集
     */
    void setRefreshListViewData(T data, boolean isReverse, boolean isFirst);

    /**
     * 设置加载更多数据集
     */
    void setLoadMoreListViewData(T data, boolean isReverse, boolean isFirst);

    /**
     * 设置数据集
     */
    void setListViewData(T data);

    /**
     * 刷新数据集
     */
    void notifyDataSetChanged();

    /**
     * adapter中数据是否为空
     */
    boolean isEmpty();

    int getItemCount();

    void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer);

    void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer);

    long getItemId(int position);

    int getItemViewType(int position);

    VH onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(VH holder, int position);
}