package com.gingersoft.gsa.cloud.common.utils.keyboard

import android.R
import android.app.Activity
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import java.lang.ref.WeakReference

/**
 * 輸入法高度監聽者
 *
 *
 * 全屏的輸入法高度 = 狀态欄高度 + height
 * 使用方法@Link https://www.cnblogs.com/asche/p/10104459.html
 */
class KeyboardHeightProvider(
    /**
     * The root activity that uses this KeyboardHeightProvider
     */
    private val activity: Activity
) : PopupWindow(
    activity
) {
    /**
     * The keyboard height observer
     */
    private var observer: KeyboardHeightObserver? = null

    /**
     * The cached landscape height of the keyboard
     */
    private var keyboardLandscapeHeight = 0

    /**
     * The cached portrait height of the keyboard
     */
    private var keyboardPortraitHeight = 0

    /**
     * The view that is used to calculate the keyboard height
     */
    private val popupView: View?

    /**
     * The parent view
     */
    private val parentView: View

    /**
     * Construct a new KeyboardHeightProvider
     *
     * @param activity The parent activity
     */
    init {

//        LayoutInflater inflator = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        this.popupView = inflator.inflate(R.layout.popupwindow, null, false);
        val linearLayout = LinearLayout(activity)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.background = null
        popupView = linearLayout
        contentView = popupView
        softInputMode =
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        inputMethodMode = INPUT_METHOD_NEEDED
        parentView = activity.findViewById(R.id.content)
        width = 0
        height = WindowManager.LayoutParams.MATCH_PARENT
        popupView.getViewTreeObserver().addOnGlobalLayoutListener {
            if (popupView != null) {
                handleOnGlobalLayout()
            }
        }
    }

    /**
     * Start the KeyboardHeightProvider, this must be called after the onResume of the Activity.
     * PopupWindows are not allowed to be registered before the onResume has finished
     * of the Activity.
     */
    fun start() {
        if (!activity.isFinishing && !isShowing && null != parentView.windowToken) {
            setBackgroundDrawable(ColorDrawable(0))
            showAtLocation(parentView, Gravity.NO_GRAVITY, 0, 0)
        }
    }

    /**
     * Close the keyboard height provider,
     * this provider will not be used anymore.
     */
    fun close() {
        observer = null
        dismiss()
    }

    /**
     * Set the keyboard height observer to this provider. The
     * observer will be notified when the keyboard height has changed.
     * For example when the keyboard is opened or closed.
     *
     * @param observer The observer to be added to this provider.
     */
    fun setKeyboardHeightObserver(observer: KeyboardHeightObserver?) {
        this.observer = observer
    }

    /**
     * Get the screen orientation
     *
     * @return the screen orientation
     */
    private val screenOrientation: Int
        private get() = activity.resources.configuration.orientation

    /**
     * Popup window itself is as big as the window of the Activity.
     * The keyboard can then be calculated by extracting the popup view bottom
     * from the activity window height.
     */
    private fun handleOnGlobalLayout() {
        val screenSize = Point()
        activity.windowManager.defaultDisplay.getSize(screenSize)
        val rect = Rect()
        popupView!!.getWindowVisibleDisplayFrame(rect)

        // REMIND, you may like to change this using the fullscreen size of the phone
        // and also using the status bar and navigation bar heights of the phone to calculate
        // the keyboard height. But this worked fine on a Nexus.
        val orientation = screenOrientation
        val keyboardHeight = screenSize.y - rect.bottom
        if (keyboardHeight == 0) {
            notifyKeyboardHeightChanged(0, orientation)
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            keyboardPortraitHeight = keyboardHeight
            notifyKeyboardHeightChanged(keyboardPortraitHeight, orientation)
        } else {
            keyboardLandscapeHeight = keyboardHeight
            notifyKeyboardHeightChanged(keyboardLandscapeHeight, orientation)
        }
    }

    private fun notifyKeyboardHeightChanged(height: Int, orientation: Int) {
        if (observer != null) {
            observer!!.onKeyboardHeightChanged(height, orientation)
        }
    }

    interface KeyboardHeightObserver {
        /**
         * Called when the keyboard height has changed, 0 means keyboard is closed,
         * >= 1 means keyboard is opened.
         *
         * @param height      The height of the keyboard in pixels
         * @param orientation The orientation either: Configuration.ORIENTATION_PORTRAIT or
         * Configuration.ORIENTATION_LANDSCAPE
         */
        fun onKeyboardHeightChanged(height: Int, orientation: Int)
    }
}