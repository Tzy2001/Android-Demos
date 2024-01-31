package com.example.test.Util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.test.Test;
import com.google.android.material.internal.ViewUtils;

/**
 * @ClassName HideTextWatcher
 * @Author TZY
 * @Date 2023/11/7 15:35
 **/
public class HideTextWatcher implements TextWatcher {

    private EditText mView;
    private int mMaxLength;
    private Context mContext;

    public HideTextWatcher(EditText v, int maxLength, Context context) {
        super();
        mView = v;
        mMaxLength = maxLength;
        mContext = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString();
        if ((str.length() == 11 && mMaxLength == 11) || (str.length() == 6 && mMaxLength == 6)) {
            hideKeyboard();
        }
    }

    private void hideKeyboard() {
        InputMethodManager systemService = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        systemService.hideSoftInputFromWindow(mView.getWindowToken(), 0);
    }
}
