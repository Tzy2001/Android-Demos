package com.example.myapplication


import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import com.gingersoft.gsa.cloud.base.BaseViewModel
import com.gingersoft.gsa.cloud.base.DataBindingConfig
import com.gingersoft.gsa.cloud.base.KtBaseActivity
import com.gingersoft.gsa.cloud.common.logan.codeLog
import com.gingersoft.gsa.cloud.common.utils.dp
import com.gingersoft.gsa.cloud.common.utils.getResColor
import com.gingersoft.gsa.cloud.dialog.SingleTextBottomListDialog
import com.gingersoft.gsa.cloud.manager.BR
import com.gingersoft.gsa.cloud.manager.R
import com.gingersoft.gsa.cloud.manager.databinding.ActivityStaffEditBinding
import com.gingersoft.gsa.cloud.manager.mvp.model.bean.StaffsBean
import com.gingersoft.gsa.cloud.manager.viewmodel.StaffEditViewModel
import com.gingersoft.gsa.cloud.ui.databinding.ItemSingleTextBinding
import com.gingersoft.gsa.cloud.ui.widget.dialog.TipDialog
import com.lxj.xpopup.core.BottomPopupView

class StaffEditActivity : KtBaseActivity() {

    private val binding by lazy { getDataBinding() as ActivityStaffEditBinding }

    var data: StaffsBean? = null
    private val typeMap =
        linkedMapOf(
            Pair(0, "老闆組"),
            Pair(1, "外賣員"),
            Pair(2, "收銀員"),
            Pair(3, "服務員")
        )

    private val viewModel: StaffEditViewModel by lazy {
        getActivityViewModel(StaffEditViewModel::class.java)
    }

    override fun initViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.activity_staff_edit, BR.vm, viewModel).addBindingParam(
            BR.click,
            ClickProxy()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val inflate = ActivityStaffEditBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.includeBtnDelete.btnSave.let {
            it.setText("刪除")
            it.background = GradientDrawable().apply {
                cornerRadius = 8.dp.toFloat()
                setColor(R.color.white.getResColor())
                setStroke(1.dp, R.color.theme_color.getResColor())
            }
        }


    }

    override fun initData() {
        super.initData()
        data = intent.getSerializableExtra("data") as StaffsBean?
        viewModel.isEdit = data != null
        viewModel.title.set(
            if (viewModel.isEdit) {
                "編輯員工"
            } else {
                "新增員工"
            }
        )
        if (viewModel.isEdit) {
            "編輯員工".codeLog(TAG)
            viewModel.staffInfoLiveData.value = data
            val type = data?.type
//            if (type !=null && typeMap.cpmtainsKey(type)){
//                viewModel.typeName.set(typeMap[type]?.second)
//            }
            if (typeMap.containsKey(data!!.type)) {
                viewModel.roleIdList.set(typeMap[type])
//                val s = typeMap[data!!.type]
//                s
            }
        } else {
            "新增員工".codeLog(TAG)
            val firstType = typeMap.toList().first()
            viewModel.roleIdList.set(firstType.second)
        }
    }

    inner class ClickProxy {
        /*
        選擇類型
        */
        fun showTypeListDialog() {
            "選擇員工類型彈窗".codeLog(TAG)
            SingleTextBottomListDialog(mContext,
                typeMap.toList(),
                "請選擇職位",
                onBindingListener = { itemSingleTextBinding: ItemSingleTextBinding, pair: Pair<Int, String>, _: Int ->

                    itemSingleTextBinding.tvText.text = pair.second
                },
                onItemClickListener = { i, pair: Pair<Int, String>, bottomPopupView: BottomPopupView ->
                    var x: StaffsBean? =
                        viewModel.staffInfoLiveData.value?.apply {
                            type = pair.first
                            employeeName = "dshakjdfbnk"
                            roleIdList = "京東方撒離開"
                        }
//                    viewModel.staffInfoLiveData.value?.name="dshakjdfbnk"
//                    viewModel.staffInfoLiveData.value?.position="京東方撒離開"

                    viewModel.roleIdList.set(pair.second)
                    "選擇員工類型彈窗,選擇${pair.second}".codeLog(TAG)
                    bottomPopupView.dismiss()

                }).showDialog()
        }

        /*
        刪除點擊
        */
        fun clickDelete() {
            "刪除點擊".codeLog(TAG)
            TipDialog.Builder(mContext).setTipType(TipDialog.TipType.DANGER)
                .setTitle("是否確認刪除？")
                .setPositiveListener(object : TipDialog.Callback {
                    override fun doAction() {
                        "刪除確認".codeLog(TAG)
                        viewModel.delete()
                    }


                }).build().show()
        }


    }
}