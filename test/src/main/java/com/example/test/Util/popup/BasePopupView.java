package com.example.test.Util.popup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.test.Util.popup.PopupInfo;

/**
 * @ClassName BasePopupView
 * @Author TZY
 * @Date 2023/10/27 10:42
 **/
public abstract class BasePopupView extends FrameLayout implements LifecycleObserver, LifecycleOwner,
        ViewCompat.OnUnhandledKeyEventListenerCompat {
    public PopupInfo popupInfo;

    public BasePopupView(@NonNull Context context) {
        super(context);
    }

    public BasePopupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePopupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BasePopupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 如果你自己繼承BasePopupView來做，這個不用實現
     *
     * @return
     */
    protected int getImplLayoutId() {
        return -1;
    }
    /**
     * 彈窗的高度，用來動态設定當前彈窗的高度，受getMaxHeight()限制
     * 返回0表示不設置，默認為0
     *
     * @return
     */
    protected int getPopupHeight() {
        return popupInfo.popupHeight;
    }

    /**
     * 請使用onCreate，主要給彈窗内部用，不要去重寫。
     */
    protected void initPopupContent() {
    }
}
