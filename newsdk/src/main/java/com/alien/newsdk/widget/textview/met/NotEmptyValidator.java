package com.alien.newsdk.widget.textview.met;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 描述:
 * <p>
 * Created by Alien on 2018/1/8.
 */

public class NotEmptyValidator extends METValidator {
    public NotEmptyValidator(Context context) {
        super("请填写此项");
    }

    public NotEmptyValidator(String errorString) {
        super(errorString);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return !isEmpty;
    }
}
