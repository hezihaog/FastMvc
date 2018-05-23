package oms.mmc.android.fast.framwork.imageloader;

import android.app.Activity;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Create Date: 2017/12/4 12:09
 * Author:jingtian
 * Description:
 **/

public interface ImageLoader extends Serializable {

    /**
     * 加载网络图片
     */
    void loadUrlImage(Activity activity, ImageView imageView, String url, int defaultImage);

    /**
     * 加载网络圆形图片
     */
    void loadUrlImageToRound(Activity activity, ImageView imageView, String url, int defaultImage);

    /**
     * 加载网络圆角图片
     */
    void loadUrlImageToCorner(Activity activity, ImageView imageView, String url, int defaultImage);

    /**
     * 加载内存卡图片
     */
    void loadFileImage(Activity activity, ImageView imageView, String filePath, int defaultImage);

    /**
     * 加载资源图片
     */
    void loadDrawableResId(Activity activity, ImageView imageView, int resId);

    /**
     * 加载图片bitmap
     */
    void loadImageToBitmap(Activity activity, String url, LoadImageCallback loadImageCallback);


    /**
     * 清除图片缓存
     */
    void clearMemoryCache(Activity activity);

}
