package com.example.myapplication

import android.widget.CompoundButton
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.gingersoft.gsa.cloud.base.BaseViewModel
import com.gingersoft.gsa.cloud.common.aop.click.ClickLimit
import com.gingersoft.gsa.cloud.common.core.restaurant.RestaurantInfoManager
import com.gingersoft.gsa.cloud.common.logan.codeLog
import com.gingersoft.gsa.cloud.common.utils.showToast
import com.gingersoft.gsa.cloud.manager.mvp.model.bean.StaffsBean
import com.gingersoft.gsa.cloud.manager.mvp.model.repository.StaffsManageRepository

class StaffEditViewModel :BaseViewModel() {
    val repository= StaffsManageRepository()

    val staffInfoLiveData=
        MutableLiveData(StaffsBean())
    //    狀態check
    val statusListener = CompoundButton.OnCheckedChangeListener{ _, isChecked ->
        "狀態check,$isChecked".codeLog(TAG)
        staffInfoLiveData.value?.restaurantStatus=if (isChecked)1 else 0

    }
    //    標題
    val title= ObservableField("")
    //    員工職位類型
    val roleIdList = ObservableField("")

    //    是否為編輯模式
    var isEdit =false
    /*
       保存
       */

    @ClickLimit(limit = 600)
    fun save(){
        staffInfoLiveData.value?.let{
            if(it.employeeName.isNullOrBlank()){
                "請輸入員工姓名".showToast()
                return
            }
            it.restaurantId= RestaurantInfoManager.newInstance().restaurantId
            it.beginTime=null
            it.endTime=null
        }
        "請求新增/編輯員工保存".codeLog(TAG)
        request({
            if (isEdit) repository.updateRestaurantStaff(getJsonRequestBody(staffInfoLiveData.value!!))
            else repository.addRestaurantStaff(getJsonRequestBody(staffInfoLiveData.value!!))
        },{
            onSuccess={
                "請求新增/編輯員工保存成功".codeLog(TAG)
                "員工信息保存成功".showToast()
                finishActivity(true)
            }
        })
    }

    /*
     刪除請求
 */

    @ClickLimit(limit = 600)
    fun delete(){
        "刪除折扣".codeLog(TAG)
        staffInfoLiveData.value!!.employeeName?.let{

            request({repository.deleteRestaurantStaff(it.toInt())},{
                onSuccess={
                    "刪除員工成功".codeLog(TAG)
                    finishActivity(true)
                }
            })
        }
    }
}