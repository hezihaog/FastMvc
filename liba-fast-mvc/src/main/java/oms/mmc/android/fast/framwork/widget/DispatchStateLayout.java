package oms.mmc.android.fast.framwork.widget;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Package: oms.mmc.android.fast.framwork.widget
 * FileName: DispatchStateLayout
 * Date: on 2018/3/19  下午11:10
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class DispatchStateLayout extends FrameLayout {
    public DispatchStateLayout(@NonNull Context context) {
        super(context);
    }

    public DispatchStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }
}
