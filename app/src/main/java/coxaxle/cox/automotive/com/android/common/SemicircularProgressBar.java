package coxaxle.cox.automotive.com.android.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import coxaxle.cox.automotive.com.android.R;

/**
 * Created by Lakshmana on 26-09-2016.
 */
public class SemicircularProgressBar extends View{
    private int mProgress;
    private RectF mOval;
    private RectF mOvalInner;
    private Paint mPaintProgress;
    private Paint mPaintClip;
    private float ovalsDiff;
    private Path clipPath;

    public SemicircularProgressBar(Context context) {
        super(context);
        init();
    }

    public SemicircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SemicircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mProgress = 0;
        ovalsDiff = 20;
        mOval = new RectF();
        mOvalInner = new RectF();
        clipPath = new Path();
        mPaintProgress = new Paint();
        mPaintProgress.setColor(getResources().getColor(R.color.colorPumpkin));
        mPaintProgress.setAntiAlias(true);
        mPaintClip = new Paint();
        mPaintClip.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaintClip.setAlpha(0);
        mPaintClip.setAntiAlias(true);
    }


    // call this from the code to change the progress displayed
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    // sets the width of the progress arc
    public void setProgressBarWidth(float width) {
        this.ovalsDiff = width;
        invalidate();
    }

    // sets the color of the bar (#FF00FF00 - Green by default)
    public void setProgressBarColor(int color){
        this.mPaintProgress.setColor(color);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        mOval.set(0, 0, this.getWidth(), this.getHeight()*2);
        mOvalInner.set(0+ovalsDiff, 0+ovalsDiff, this.getWidth()-ovalsDiff, this.getHeight()*2);
        clipPath.addArc(mOvalInner, 180, 180);
        c.clipPath(clipPath, Region.Op.DIFFERENCE);
        c.drawArc(mOval, 180, 180f * ((float) mProgress / 100), true, mPaintProgress);
    }

    // Setting the view to be always a rectangle with height equal to half of its width
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth/2, parentHeight);
        ViewGroup.LayoutParams params = this.getLayoutParams();
        params.width = parentWidth;
        params.height = parentWidth/2;
        this.setLayoutParams(params);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
