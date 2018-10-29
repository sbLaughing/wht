package com.alien.ksdk.view.textview.met;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alien.ksdk.R;

/**
 * 描述:
 * <p>
 * Created by Alien on 2018/1/9.
 */

public class LengthValidator extends METValidator {
    int len =0;
    public LengthValidator(Context context,int minlen) {
        super(context.getString(R.string.please_input_here));
        this.len = minlen;
    }

    public LengthValidator(String errorString,int minlen) {
        super(errorString);
        this.len = minlen;
    }


    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return !isEmpty && text.length()>=len;
    }
}
