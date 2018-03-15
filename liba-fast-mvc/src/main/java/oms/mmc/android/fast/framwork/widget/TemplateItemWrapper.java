package oms.mmc.android.fast.framwork.widget;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Package: oms.mmc.android.fast.framwork.widget
 * FileName: TemplateItemWrapper
 * Date: on 2018/3/14  下午10:48
 * Auther: zihe
 * Descirbe:条目类包裹布局
 * Email: hezihao@linghit.com
 */

public class TemplateItemWrapper extends FrameLayout {
    public TemplateItemWrapper(@NonNull Context context) {
        super(context);
    }

    public TemplateItemWrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TemplateItemWrapper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
