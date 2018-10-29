package com.alien.ksdk.view.textview.met;

import android.support.annotation.IntDef;

import static com.alien.ksdk.view.textview.met.InputType.captcha;
import static com.alien.ksdk.view.textview.met.InputType.other;
import static com.alien.ksdk.view.textview.met.InputType.password;
import static com.alien.ksdk.view.textview.met.InputType.phone;

/**
 * 描述:
 * <p>
 * Created by Alien on 2018/1/9.
 */

@IntDef({phone,captcha,password,other})
public @interface InputType {
    int phone=0x00;
    int captcha = 0x01;
    int password = 0x02;
    int other = 99;
}
