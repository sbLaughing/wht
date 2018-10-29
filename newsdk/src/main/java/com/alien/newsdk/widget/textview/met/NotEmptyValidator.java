package com.alien.ksdk.view.textview.met;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alien.ksdk.R;

/**
 * 描述:
 * <p>
 * Created by Alien on 2018/1/8.
 */

public class NotEmptyValidator extends METValidator {
    public NotEmptyValidator(Context context) {
        super(context.getString(R.string.please_input_here));
    }

    public NotEmptyValidator(String errorString) {
        super(errorString);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return !isEmpty;
    }
}
