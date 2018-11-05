package com.alien.newsdk.widget.toolbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.TextView;

import com.alien.newsdk.R;

import java.lang.reflect.Field;


public class MToolbar extends Toolbar {

    private TextView mTitleTextView;
    private CharSequence mTitleText;
    private @ColorInt
    int mTitleTextColor;
    private int mCustomTitleTextAppearance;
    private boolean isTitleCenter;
    /**
     * 默认非浅色
     */
    public boolean isToolbarLight = true;

    public MToolbar(Context context) {
        super(context);
        resolveAttribute(context, null, R.attr.toolbarStyle);
    }
    public MToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        resolveAttribute(context,attrs,R.attr.toolbarStyle);
    }
    public MToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        resolveAttribute(context,attrs,defStyleAttr);
    }
    private void resolveAttribute(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
// Need to use getContext() here so that we use the themed context
        context = getContext();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Toolbar,defStyleAttr,0);
        mCustomTitleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance,0);
        if(mCustomTitleTextAppearance !=0) {
            setTitleTextAppearance(context,mCustomTitleTextAppearance);
        }
        a.recycle();
//        a = context.obtainStyledAttributes(attrs,ATTR);
//        if (a.hasValue(0)){
//            isToolbarLight = a.getBoolean(0,isToolbarLight);
//        }


        a = context.obtainStyledAttributes(attrs,R.styleable.MToolbar,R.attr.MToolbarDefaultStyle,0);
        isTitleCenter = a.getBoolean(R.styleable.MToolbar_mt_title_center,isTitleCenter);
        final CharSequence title = a.getText(R.styleable.MToolbar_mt_title);
        if (!TextUtils.isEmpty(title)){
            setTitle(title);
        }
        a.recycle();

    }



    @Override
    public void setNavigationIcon(@Nullable Drawable icon) {
        super.setNavigationIcon(icon);
        setGravityCenter();
        if (null!=icon && getContext() instanceof Activity){
            setNavigationOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) getContext()).onBackPressed();
                }
            });
        }
    }




    @Override
    public CharSequence getTitle() {
        return mTitleText;
    }

    @Override
    public void setTitle(CharSequence title) {
        if (isTitleCenter) {
            if (!TextUtils.isEmpty(title)) {
                if (mTitleTextView == null) {
                    final Context context = getContext();
                    mTitleTextView = new TextView(context);
                    mTitleTextView.setTextSize(16);
                    mTitleTextView.setSingleLine();
                    mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                    if (mCustomTitleTextAppearance != 0) {
                        mTitleTextView.setTextAppearance(context, mCustomTitleTextAppearance);
                    }
                    if (mTitleTextColor != 0) {
                        mTitleTextView.setTextColor(mTitleTextColor);
                    }
                }
                if (mTitleTextView.getParent() != this) {
                    addCenterView(mTitleTextView);
                }
            } else if (mTitleTextView != null && mTitleTextView.getParent() == this) {// 当title为空时，remove
                removeView(mTitleTextView);
            }
            if (mTitleTextView != null) {
                mTitleTextView.setText(title);
            }
        }
        else super.setTitle(title);
        mTitleText = title;
    }

    private void addCenterView(View v) {
        final ViewGroup.LayoutParams vlp = v.getLayoutParams();
        final LayoutParams lp;
        if(vlp ==null) {
            lp = generateDefaultLayoutParams();
        }else if(!checkLayoutParams(vlp)) {
            lp = generateLayoutParams(vlp);
        }else{
            lp = (LayoutParams) vlp;
        }
        addView(v,lp);
    }
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        LayoutParams lp =new LayoutParams(getContext(),attrs);
        lp.gravity= Gravity.CENTER;
        return lp;
    }
    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        LayoutParams lp;
        if(p instanceof LayoutParams) {
            lp =new LayoutParams((LayoutParams) p);
        }else if(p instanceof ActionBar.LayoutParams) {
            lp =new LayoutParams((ActionBar.LayoutParams) p);
        }else if(p instanceof MarginLayoutParams) {
            lp =new LayoutParams((MarginLayoutParams) p);
        }else{
            lp =new LayoutParams(p);
        }
        lp.gravity= Gravity.CENTER;
        return lp;
    }
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams lp =new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity= Gravity.CENTER;
        return lp;
    }
    @Override
    public void setTitleTextAppearance(Context context, int resId) {
        mCustomTitleTextAppearance= resId;
        if (isTitleCenter) {
            if (mTitleTextView != null) {
                mTitleTextView.setTextAppearance(context, resId);
            }
        }else super.setTitleTextAppearance(context,resId);
    }
    @Override
    public void setTitleTextColor(@ColorInt int color) {
        mTitleTextColor= color;
        if (isTitleCenter){
            if(mTitleTextView!=null) {
                mTitleTextView.setTextColor(color);
            }
        }
        else super.setTitleTextColor(color);
    }


    public void setGravityCenter() {
        post(new Runnable() {
            @Override
            public void run() {
                setCenter("mNavButtonView");
                setCenter("mMenuView");
            }

        });
    }
    private void setCenter(String fieldName) {
        try{
            Field field = getClass().getSuperclass().getDeclaredField(fieldName);//反射得到父类Field
            field.setAccessible(true);
            Object obj = field.get(this);//拿到对应的Object
            if(obj ==null)return;
            if(obj instanceof View) {
                View view = (View) obj;
                ViewGroup.LayoutParams lp = view.getLayoutParams();//拿到LayoutParams
                if(lp instanceof ActionBar.LayoutParams) {
                    ActionBar.LayoutParams params = (ActionBar.LayoutParams) lp;
                    params.gravity= Gravity.CENTER;//设置居中
                    view.setLayoutParams(lp);
                }
            }
        }catch(NoSuchFieldException e) {
            e.printStackTrace();
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, insets.getSystemWindowInsetTop(), 0, 0));
    }

}
