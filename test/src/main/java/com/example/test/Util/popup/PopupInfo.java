package com.example.test.Util.popup;

import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import java.util.ArrayList;

/**
 * Description: Popup的屬性封裝
 * Create by dance, at 2018/12/8
 */
public class PopupInfo {
    public Boolean isDismissOnBackPressed = true;  //按返回鍵是否消失
    public Boolean isDismissOnTouchOutside = true; //點擊外部消失
    public Boolean autoDismiss = true; //操作完畢後是否自動關閉
    public Boolean hasShadowBg = true; // 是否有半透明的背景
    public Boolean hasBlurBg = false; // 是否有高斯模糊背景
    public View atView = null; // 依附于那個View
    // 動畫執行器，如果不指定，則會根據窗體類型popupType字段生成默認合適的動畫執行器
    public PointF touchPoint = null; // 觸摸的點
    public int maxWidth; // 最大寬度
    public int maxHeight; // 最大高度
    public int popupWidth, popupHeight; // 指定彈窗的寬高，受max的寬高限制
    public float borderRadius = 15; // 圓角
    public Boolean autoOpenSoftInput = false;//是否自動打開輸入法

    public Boolean isMoveUpToKeyboard = true; //是否移動到軟鍵盤上面，默認彈窗會移到軟鍵盤上面
    public PopupPosition popupPosition = null; //彈窗出現在目標的什麼位置
    public Boolean hasStatusBarShadow = false; //是否顯示狀态欄陰影
    public Boolean hasStatusBar = true; //是否顯示狀态欄
    public Boolean hasNavigationBar = true; //是否顯示導航欄
    public int navigationBarColor = 0; //是否顯示導航欄
    public int isLightNavigationBar = 0; //是否是亮色導航欄，>0為true，<0為false
    public int isLightStatusBar = 0; //是否是亮色狀态欄，>0為true，<0為false
    public int offsetX, offsetY;//x，y方向的偏移量
    public Boolean enableDrag = true;//是否啟用拖拽
    public boolean isCenterHorizontal = false;//是否水平居中
    public boolean isRequestFocus = true; //彈窗是否強制搶占焦點
    public boolean autoFocusEditText = true; //是否讓輸入框自動獲取焦點
    public boolean isClickThrough = false;//是否點擊透傳，默認彈背景點擊是攔截的
    public boolean isTouchThrough = false;//是否觸摸透傳，默認是不支持的
    public boolean isDarkTheme = false; //是否是暗色調主題
    public boolean enableShowWhenAppBackground = false; //是否允許應用在後台的時候也能彈出彈窗
    public boolean isThreeDrag = false; //是否開啟三階拖拽
    public boolean isDestroyOnDismiss = false; //是否關閉後進行資源釋放
    public boolean positionByWindowCenter = false; //是否已屏幕中心進行定位，默認根據Material範式進行定位
    public boolean isViewMode = false; //是否是View實現，默認是Dialog實現
    public boolean keepScreenOn = false; //是否保持屏幕常亮
    public int shadowBgColor = 0; //陰影背景的顏色
    public int animationDuration = -1; //動畫的時長
    public int statusBarBgColor = 0; //狀态欄陰影顏色，對Drawer彈窗和全屏彈窗有效
    public ArrayList<Rect> notDismissWhenTouchInArea; //當觸摸在這個區域時，不消失
    public Lifecycle hostLifecycle; //自定義的宿主生命周期

    public Rect getAtViewRect(){
        int[] locations = new int[2];
//        atView.getLocationInWindow(locations);
        atView.getLocationOnScreen(locations);
        return new Rect(locations[0], locations[1], locations[0] + atView.getMeasuredWidth(),
                locations[1] + atView.getMeasuredHeight());
    }
}
