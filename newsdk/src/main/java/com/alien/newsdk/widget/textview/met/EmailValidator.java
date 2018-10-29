package com.alien.newsdk.widget.textview.met;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述:
 * <p>
 * Created by Alien on 2018/4/24.
 */

public class EmailValidator extends METValidator {

    public static String isMailPattern = "^[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    public static boolean isMail(String mail){
        String pattern = isMailPattern;
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(mail);
        return matcher.matches();
    }


    public EmailValidator(String errorString) {
        super(errorString);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return isMail(text.toString());
    }
}

