package oms.mmc.android.fast.framwork.imageloader;

import android.graphics.Bitmap;

/**
 * Create Date: 2017/12/6 17:04
 * Author:jingtian
 * Description:加载图片bitmap回调
 **/

public interface LoadImageCallback {
    void onSuccess(Bitmap bitmap);

    void onFail();
}