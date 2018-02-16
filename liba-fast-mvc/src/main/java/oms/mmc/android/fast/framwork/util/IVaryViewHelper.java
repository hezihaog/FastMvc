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

import android.content.Context;
import android.view.View;

/**
 * 布局切换帮助类接口
 */
public interface IVaryViewHelper {
    /**
     * 获取当前布局
     */
    View getCurrentLayout();

    /**
     * 恢复原来的View
     */
    void restoreView();

    /**
     * 用View的形式,开始展示覆盖布局（例如loading布局，错误布局等）
     */
    void showLayout(View view);

    /**
     * 用布局id的形式,开始展示布局（例如loading布局，错误布局等）
     */
    void showLayout(int layoutId);

    /**
     * 填充布局
     */
    View inflate(int layoutId);

    /**
     * 获取上下文
     */
    Context getContext();

    /**
     * 获取要替换的View
     */
    View getView();
}