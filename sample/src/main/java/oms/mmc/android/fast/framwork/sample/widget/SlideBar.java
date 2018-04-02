package oms.mmc.android.fast.framwork.sample.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: SlideBar
 * Date: on 2018/4/2  上午11:30
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class SlideBar extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private String[] mLetter = new String[]{"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Rect mTextRect;
    private boolean mTouched = false;
    private OnSelectItemListener mListener;
    private int mSelectTextColor;
    private int mUnSelectTextColor;
    private int mCellHeight;

    public SlideBar(Context context) {
        super(context);
        init();
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //选中时文字的颜色
        mSelectTextColor = Color.parseColor("#FFFFFF");
        //未选中时文字的颜色
        mUnSelectTextColor = Color.parseColor("#202020");

        mPaint = new Paint();
        mPaint.setTextSize(sp2px(getContext(), 11f));
        if (mTouched) {
            mPaint.setColor(mSelectTextColor);
        } else {
            mPaint.setColor(mUnSelectTextColor);
        }
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mTextRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        //每行的高度，计算平均分每个字母占的高度
        mCellHeight = mHeight / mLetter.length;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), heightMeasureSpec);
    }

    /**
     * 测量本身的大小，这里只是测量宽度
     *
     * @param widthMeaSpec 传入父View的测量标准
     * @return 测量的宽度
     */
    private int measureWidth(int widthMeaSpec) {
        /*定义view的宽度*/
        int width;
        /*获取当前 View的测量模式*/
        int mode = MeasureSpec.getMode(widthMeaSpec);
        /*
         * 获取当前View的测量值，这里得到的只是初步的值，
         * 我们还需根据测量模式来确定我们期望的大小
         * */
        int size = MeasureSpec.getSize(widthMeaSpec);
        /*
         * 如果，模式为精确模式
         * 当前View的宽度，就是我们
         * 的size ；
         * */
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            /*否则的话我们就需要结合padding的值来确定*/
            int desire = size + getPaddingLeft() + getPaddingRight();
            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(desire, size);
            } else {
                width = desire;
            }
        }
        return width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //触摸时改变背景颜色
        if (mTouched) {
            canvas.drawColor(Color.parseColor("#66202020"));
            mPaint.setColor(mSelectTextColor);
        } else {
            canvas.drawColor(Color.parseColor("#00000000"));
            mPaint.setColor(mUnSelectTextColor);
        }
        for (int i = 0; i < mLetter.length; i++) {
            String text = mLetter[i];
            //测量文字宽高
            mPaint.getTextBounds(text, 0, text.length(), mTextRect);
            int textWidth = mTextRect.width();
            int textHeight = mTextRect.height();

            //文字一半的宽度
            float textHalfWidth = textWidth / 2.0f;
            //字母文字的起点X坐标，控件的宽度的一半再减去文字的一半
            float x = (mWidth / 2.0f) - textHalfWidth;
            //起点文字的Y坐标
            float y = (mCellHeight / 2.0f + textHeight / 2.0f + mCellHeight * i);
            //画文字
            canvas.drawText(mLetter[i], x, y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        //计算出当前触摸的字母
        int index = (int) (y / mCellHeight);
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            mTouched = true;
            if (index >= 0 && index < mLetter.length) {
                if (mListener != null) {
                    mListener.onItemSelect(index, mLetter[index]);
                }
            } else {
                return super.onTouchEvent(event);
            }
        } else {
            mTouched = false;
            if (mListener != null) {
                mListener.onItemUnSelect();
            }
        }
        invalidate();
        return true;
    }

    public interface OnSelectItemListener {
        /**
         * 选择时回调
         *
         * @param position     选中的位置
         * @param selectLetter 选中的字母
         */
        void onItemSelect(int position, String selectLetter);

        /**
         * 松手取消选中时回调
         */
        void onItemUnSelect();
    }

    public void setOnSelectItemListener(OnSelectItemListener listener) {
        mListener = listener;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
}