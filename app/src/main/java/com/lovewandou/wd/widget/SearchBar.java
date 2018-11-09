package com.lovewandou.wd.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lovewandou.wd.R;

/**
 * 描述:
 * <p>
 * Created by and on 2018/11/7.
 */
public class SearchBar extends LinearLayout {

    private AppCompatEditText mEditText;
    private View clearView;
    public SearchBar(Context context) {
        super(context);
        init();
    }

    public SearchBar(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchBar(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundResource(R.drawable.search_bg);

        LayoutInflater.from(getContext()).inflate(R.layout.layout_searchbar,this);

        mEditText = findViewById(R.id.edit_view);
        clearView = findViewById(R.id.clear_view);

        clearView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateClearBtn();
            }
        });
        mEditText.setEnabled(isEnabled());
        validateClearBtn();
    }

    public AppCompatEditText getEditText() {
        return mEditText;
    }

    public String getSearchContent(){
        return mEditText.getText().toString();
    }


    @Override
    public void setOnClickListener(View.OnClickListener l) {
        super.setOnClickListener(l);
        mEditText.setOnClickListener(l);
    }

    public void setEditEnabled(boolean enabled) {
        mEditText.setFocusable(enabled);
        if (!enabled){
            mEditText.clearFocus();
        }
        validateClearBtn();
    }

    private void validateClearBtn(){
        if (!mEditText.isEnabled()||TextUtils.isEmpty(mEditText.getText().toString().trim())){
            clearView.setVisibility(View.INVISIBLE);
        }else {
            clearView.setVisibility(View.VISIBLE);
        }
    }
}
