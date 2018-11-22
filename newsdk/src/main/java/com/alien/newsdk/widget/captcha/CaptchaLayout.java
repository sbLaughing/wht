package com.alien.newsdk.widget.captcha;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.alien.newsdk.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


/**
 * Created by Alien on 2017/8/8.
 */

public class CaptchaLayout extends android.support.v7.widget.AppCompatTextView {


    public interface ICaptcha {
        void onStart();
    }

    String initText;
    int maxTime;
    String stringFormat;
    ICaptcha mICaptcha;

    Disposable disposable;
    public void setICaptcha(ICaptcha i) {
        mICaptcha = i;
    }

    public CaptchaLayout(Context context) {
        this(context, null);
    }

    public CaptchaLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CaptchaLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CaptchaLayout, 0, 0);
        initText = a.getString(R.styleable.CaptchaLayout_init_text);
        maxTime = a.getInt(R.styleable.CaptchaLayout_max_time, 60);
        stringFormat = a.getString(R.styleable.CaptchaLayout_stringFormat);
        a.recycle();
        setText(initText);
    }

    public void start() {
        setEnabled(false);
        if (mICaptcha != null)
            mICaptcha.onStart();
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {


                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (maxTime <= aLong) {
                            setText(initText);
                            setEnabled(true);
                            disposable.dispose();
                        } else {
                            if (TextUtils.isEmpty(stringFormat))
                                setText(String.valueOf(maxTime - aLong) + "s");
                            else
                                setText(String.format(stringFormat, maxTime - aLong));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (disposable!=null && !disposable.isDisposed()) disposable.dispose();
    }
}

