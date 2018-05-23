package oms.mmc.android.fast.framwork.imageloader;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

/**
 * Create Date: 2017/12/4 11:48
 * Author:jingtian
 * Description:glide加载
 **/

public class GlideImageLoader implements ImageLoader {

    @Override
    public void loadUrlImage(Activity activity, ImageView imageView, String url, int defaultImage) {
        Glide.with(activity)
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                .crossFade()
//                .thumbnail(0.7f)
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void loadUrlImageToRound(Activity activity, ImageView imageView, String url, int defaultImage) {
        Glide.with(activity)
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                //.centerCrop()//网友反馈，设置此属性可能不起作用,在有些设备上可能会不能显示为圆形。
//                .thumbnail(0.7f)
                .transform(new GlideCircleTransform(activity))
                .crossFade()
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void loadUrlImageToCorner(Activity activity, ImageView imageView, String url, int defaultImage) {
        Glide.with(activity)
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                //.centerCrop()
//                .thumbnail(0.7f)
                .transform(new GlideRoundTransform(activity))
                .crossFade()
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void loadFileImage(Activity activity, ImageView imageView, String filePath, int defaultImage) {
        Glide.with(activity)
                .load(new File(filePath))
                .placeholder(defaultImage)
                .error(defaultImage)
                .crossFade()
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void loadDrawableResId(Activity activity, ImageView imageView, int resId) {
        Glide.with(activity).load(resId).into(imageView);
    }

    @Override
    public void loadImageToBitmap(Activity activity, String url, final LoadImageCallback loadImageCallback) {
        Glide.with(activity)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (loadImageCallback != null) {
                            loadImageCallback.onSuccess(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (loadImageCallback != null) {
                            loadImageCallback.onFail();
                        }
                    }
                });
    }

    @Override
    public void clearMemoryCache(Activity activity) {
        Glide.get(activity).clearDiskCache();
    }


    //基本功能：Glide显示为圆形图片
    public class GlideCircleTransform extends BitmapTransformation {

        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    //基本功能：Glide显示为圆角图片
    public class GlideRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }
}
