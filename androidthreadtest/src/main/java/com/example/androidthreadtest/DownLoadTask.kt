package com.example.androidthreadtest

import android.os.AsyncTask
import android.widget.Toast

class DownLoadTask:AsyncTask<Unit,Int,Boolean>() {
    override fun onPreExecute() {
//    progressDialog.show()
    }
    override fun doInBackground(vararg p0: Unit?): Boolean? {
//        while (true) {
//            val downloadPercent = doDownload()//這是一個虛構的方法
//            publishProgress(downloadPercent)
//            if (downloadPercent >= 100) {
//                break
//            }
//        }
//        true
        return true
    }
//    catch(e:Exception){
//        false
//    }

    override fun onProgressUpdate(vararg values: Int?) {
//    progressDialog.setMessage("Downloaded ${values[0]}%")
    }

    override fun onPostExecute(result: Boolean) {
//    progressDialog.dismiss()
//        if (result){
//            Toast.makeText(this,"Download succeeded",Toast.LENGTH_SHORT).show()
//        }
    }

}