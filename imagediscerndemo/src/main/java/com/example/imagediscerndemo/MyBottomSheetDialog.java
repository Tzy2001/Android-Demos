package com.example.imagediscerndemo;


import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.imagediscerndemo.utils.ScreenUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * @ClassName MyBottomSheetDialog
 * @Author TZY
 * @Date 2024/1/31 10:37
 **/
public class MyBottomSheetDialog extends BottomSheetDialog {

    public MyBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for transparent
        int screenHeight = ScreenUtil.getScreenHeight(getContext());
        int statusBarHeight = ScreenUtil.getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        Window window = getWindow();
        if(null != window) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // for landscape mode
        BottomSheetBehavior behavior = getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
