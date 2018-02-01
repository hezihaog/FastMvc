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

/**
 * 列表控件的adapter接口
 */
public interface IDataAdapter<T> {
    /**
     * 设置数据集
     */
    void setListViewData(T data, boolean isRefresh);

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
     * 是否添加了加载更多的尾部条目
     */
    boolean isAddLoaderMoreItem();
}