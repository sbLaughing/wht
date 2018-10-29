package com.alien.ksdk.view.textview.met;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alien.ksdk.R;

/**
 * 描述:
 * <p>
 * Created by Alien on 2018/1/8.
 */

public class PhoneValidator extends METValidator {

    public PhoneValidator(Context context) {
        super(context.getString(R.string.phone_length_notright));
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return !isEmpty&&text.length()==11;
    }
}
