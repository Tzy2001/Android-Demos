package com.example.test.progressBar;

/**
 * @ClassName ProgressBarView
 * @Author TZY
 * @Date 2023/11/15 14:25
 **/

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import com.example.test.R;


public class ProgressBarView extends View {

    private Paint mPaintBack;
    private Paint mPaint;
    private Paint mPaintText;
    private float process;
    private int strokeWidth = 35;
    private int textSize = 20;
    private long duration = 3500;

    public ProgressBarView(Context context) {
        super(context);
        init();
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintBack = new Paint();
        mPaintBack.setColor(getResources().getColor(R.color.back));
        mPaintBack.setStyle(Paint.Style.STROKE);
        mPaintBack.setAntiAlias(true);
        mPaintBack.setStrokeCap(Paint.Cap.ROUND);
        mPaintBack.setStrokeWidth(strokeWidth);

        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.inner));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(strokeWidth);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor(getResources().getColor(R.color.back));
        mPaintBack.setStrokeCap(Paint.Cap.ROUND);
        mPaintText.setTextSize(sp2px(textSize));
    }

    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景矩形
        drawBack(canvas);
        //绘制进度
        drawProgress(canvas, process);
        //绘制文字
        drawText(canvas);
    }

    /**
     * 设置动画效果
     */
    public void start() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            process = (float) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.start();
    }

    //背景矩形
    private void drawBack(Canvas canvas) {
        //创建圆环矩形
        RectF rectF = new RectF(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth);
        //画出灰色进度条作为背景
        canvas.drawArc(rectF, 0, 360, false, mPaintBack);
    }

    //创建圆环矩形
    private void drawProgress(Canvas canvas, float process) {
        RectF rectF = new RectF(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth);
        canvas.drawArc(rectF, 0, process, false, mPaint);
    }

    //绘制文字
    private void drawText(Canvas canvas) {
        int percent = (int) (process / 360 * 100);
        int mTxtWidth = getTextWidth(percent);
        int mTxtHeight = getTextHeight();
        int x = getWidth() / 2 - mTxtWidth / 2;
        int y = getHeight() / 2 + mTxtHeight / 4;
        if (percent < 100) {
            canvas.drawText(percent + "%", x, y, mPaintText);
        } else {
            canvas.drawText(getResources().getString(R.string.finished), x, y, mPaintText);
        }
    }

    private int getTextWidth(int percent) {
        int mTxtWidth;
        String text = getResources().getString(R.string.finished);
        String defaultText = getResources().getString(R.string.defaultText);
        String defaultText2 = getResources().getString(R.string.defaultText2);
        if (percent == 0) {
            mTxtWidth = (int) mPaintText.measureText(defaultText, 0, defaultText.length());
        } else if (percent > 0 && percent < 100) {
            mTxtWidth = (int) mPaintText.measureText(defaultText2, 0, defaultText2.length());
        } else {
            mTxtWidth = (int) mPaintText.measureText(text, 0, text.length());
        }
        return mTxtWidth;
    }

    private int getTextHeight() {
        Paint.FontMetrics fm = mPaintText.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }
}
