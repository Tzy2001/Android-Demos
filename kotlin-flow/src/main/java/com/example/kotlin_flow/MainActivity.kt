package com.example.kotlin_flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    //充當水龍頭的角色，出來從水源來的數據，冷流只有在有接收端的時候才會執行
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text_view)
        val button = findViewById<Button>(R.id.button)
        textView.textSize = 20F
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.stateFlow.collect { time ->
                    textView.text = time.toString()
                }
            }
        }
//        button.setOnClickListener {
//            //collect是一個掛起函數需要藉助lifecycleScope啟動一個協程作用域
//            //當代碼中有多個flow需要collect時，需要創建字協程，要不然下一個collect一直不會執行，會陷入死循環
//            lifecycleScope.launch {//這樣會有問題，如果應用推出顯示前臺，那麽計數器仍然會保持計算，
////                lifecycleScope.launchWhenCreated {  }//這裏使用launchWhenCreated可以避免這種錯誤，但是儅重新進入應用時會接著上次的數據往下計數，造成了内存的浪費（數據量大的時候影響更加嚴重），數據失去了時效性
////                repeatOnLifecycle(Lifecycle.State.STARTED)//Google推薦用法，表示只有在Activity处于Started状态的情况下，协程中的代码才会执行
//                //這裡可以使用collectLatest函數，只接收最新的flow，如果正在處理前一個數據，那麼會將前一個數據的邏輯全部取消
////                mainViewModel.timeFlow.collectLatest { time ->
////                    textView.text = time.toString()
////                    delay(3000)//處理水源的速度，需要用背壓策略，flow也有這種策略。
////                }
//                repeatOnLifecycle(Lifecycle.State.STARTED){
//                    mainViewModel.timeFlow.collect{time->
//                        textView.text = time.toString()
//                        Log.d("FlowTest", "Update time $time in UI.")
//                    }
//                }
//            }
//        }
//        button.setOnClickListener {
//            mainViewModel.startTimer()
//        }
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {//這裏有點問題，如果應用推出顯示前臺，那麽計數器仍然會保持計算，應該是Timer開啓了一個綫程會一直執行下去，就把stateFlow儅liveData使用
//                mainViewModel.stateFlow.collect { time ->
//                    textView.text = time.toString()
//                    Log.d("FlowTest", "Update time $time in UI.")
//                }
//            }
//        }

        button.setOnClickListener {
            mainViewModel.startLogin()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.loginFlow.collect {
                    if (it.isNotEmpty()) {
                        Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}