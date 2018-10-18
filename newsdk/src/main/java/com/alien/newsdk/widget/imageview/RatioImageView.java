package com.alien.newsdk.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.alien.newsdk.R;


/**
 * Created by Alien on 2017/8/11.
 */

public class RatioImageView extends android.support.v7.widget.AppCompatImageView {

    public static final int[] ATTR = new int[]{R.attr.ratio};
    float mRatio;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    public void init(Context context, @Nullable AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, ATTR, 0,0);
        mRatio = typedArray.getFloat(0,1.0f);
        typedArray.recycle();
    }

    public void setmRatio(float mRatio) {
        this.mRatio = mRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(
                        (int) (MeasureSpec.getSize(widthMeasureSpec) * mRatio),
                        MeasureSpec.getMode(widthMeasureSpec)
                ));
    }
}
