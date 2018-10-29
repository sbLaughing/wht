package com.alien.newsdk.widget.textview.met;

import android.content.Context;
import android.support.annotation.NonNull;


/**
 * 描述:
 * <p>
 * Created by Alien on 2018/1/8.
 */

public class PhoneValidator extends METValidator {

    public PhoneValidator(Context context) {
        super("手机号码长度不正确");
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return !isEmpty&&text.length()==11;
    }
}
