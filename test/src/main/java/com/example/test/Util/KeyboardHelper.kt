package com.gingersoft.gsa.cloud.common.utils.keyboard

import android.content.Context
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 鍵盤輔助類
 * 可設置鍵盤高度監聽
 * @author DZG
 * @time 2022/5/24 12:20
 */
object KeyboardHelper {

    /**隱藏輸入法*/
    @JvmStatic
    fun hideSoftInput(view: View?) {
        if (view?.context == null) return
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**顯示輸入法*/
    fun showSoftInput(view: View?) {
        if (view?.context == null) return
        view.requestFocus()
        view.postDelayed({
            (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(
                view,
                InputMethodManager.SHOW_FORCED
            )
        }, 200L)
    }

    /**監聽鍵盤高度*/
    fun observeKeyboardHeight(
        activity: FragmentActivity,
        callback: (height: Int, orientation: Int) -> Unit
    ) {
        var keyboardHeightProvider: KeyboardHeightProvider? = null

        activity.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                keyboardHeightProvider = KeyboardHeightProvider(activity)
                Looper.myQueue().addIdleHandler {
                    keyboardHeightProvider?.start()
                    false
                }
                keyboardHeightProvider?.setKeyboardHeightObserver(object :
                    KeyboardHeightProvider.KeyboardHeightObserver {
                    override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
                        callback.invoke(height, orientation)
                    }
                })
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                keyboardHeightProvider?.setKeyboardHeightObserver(null)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                keyboardHeightProvider?.close()
                activity.lifecycle.removeObserver(this)
            }
        })
    }
}