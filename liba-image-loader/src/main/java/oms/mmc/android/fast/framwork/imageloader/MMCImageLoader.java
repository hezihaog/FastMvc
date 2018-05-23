package oms.mmc.android.fast.framwork.imageloader;

import android.app.Activity;
import android.widget.ImageView;

/**
 * 基本功能：图片加载工具
 */
public class MMCImageLoader {

    private ImageLoader mImageLoader;

    /**
     * 如果没有设置ImageLoader，就返回一个默认的GlideImageLoader
     */
    public ImageLoader getmImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new GlideImageLoader();
            return mImageLoader;
        } else {
            return mImageLoader;
        }
    }

    /**
     * 设置app端指定的ImageLoader
     */
    public MMCImageLoader setmImageLoader(ImageLoader mImageLoader) {
        this.mImageLoader = mImageLoader;
        return this;
    }

    private static class ImageLoaderHolder {
        private static final MMCImageLoader INSTANCE = new MMCImageLoader();
    }

    private MMCImageLoader() {
    }

    public static final MMCImageLoader getInstance() {
        return ImageLoaderHolder.INSTANCE;
    }


    /**
     * 加载网络图片
     */
    public void loadUrlImage(Activity activity, String url, ImageView imageView, int defaultImage) {
        getmImageLoader().loadUrlImage(activity, imageView, url, defaultImage);
    }

    /**
     * 加载网络圆形图片
     */
    public void loadUrlImageToRound(Activity activity, String url, ImageView imageView, int defaultImage) {
        getmImageLoader().loadUrlImageToRound(activity, imageView, url, defaultImage);
    }

    /**
     * 加载网络圆角图片
     */
    public void loadUrlImageToCorner(Activity activity, String url, ImageView imageView, int defaultImage) {
        getmImageLoader().loadUrlImageToCorner(activity, imageView, url, defaultImage);
    }

    /**
     * 加载内存卡图片
     */
    public void loadFileImage(Activity activity, String filePath, ImageView imageView, int defaultImage) {
        getmImageLoader().loadFileImage(activity, imageView, filePath, defaultImage);
    }

    /**
     * 加载图片bitmap
     */
    public void loadImageToBitmap(Activity activity, String url, LoadImageCallback loadImageCallback) {
        getmImageLoader().loadImageToBitmap(activity, url, loadImageCallback);
    }

    /**
     * 清除图片缓存
     */
    public void clearMemoryCache(Activity activity) {
        getmImageLoader().clearMemoryCache(activity);
    }


}
