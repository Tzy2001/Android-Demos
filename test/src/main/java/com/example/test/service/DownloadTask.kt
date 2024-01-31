package com.example.test.service

import android.os.AsyncTask
import android.widget.Toast

//class DownloadTask :AsyncTask<Unit,Int,Boolean>() {
//頁面的初始化操作
//    override fun onPreExecute() {
//        progressDialog.show()//顯示進度對話框
//    }
//

//該方法的所有代碼都會在子線程中執行
//    override fun doInBackground(vararg params: Unit?)=try {
//        while (true){
//            val downloadPercent =doDownLoad()//一個虛構的方法
//            publishProgress(downloadPercent)
//            if (downloadPercent>=100){
//                break
//            }
//        }
//        true
//    }catch (e :Exception){
//        false
//    }
//

//更新ui的方法，裡面的參數由doInBackground提供
//    override fun onProgressUpdate(vararg values: Int?) {
////        在這裡更新下載進度
//        progressDialog.setMessages("Download ${values[0]}%")
//    }
//
//


//對結果的操作
//    override fun onPostExecute(result: Boolean?) {
////        關閉進度對話框
//        progressDialog.dismiss()
//        if (result){
//            Toast.makeText(context,"Download succeeded ",Toast.LENGTH_SHORT).show()
//        }else{
//            Toast.makeText(context,"Download failed ",Toast.LENGTH_SHORT).show()
//        }
//    }

//}