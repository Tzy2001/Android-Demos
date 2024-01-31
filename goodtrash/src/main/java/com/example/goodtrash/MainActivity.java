package com.example.goodtrash;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodtrash.adapter.SearchGoodsAdapter;
import com.example.goodtrash.contract.MainContract;
import com.example.goodtrash.model.TrashResponse;
import com.example.goodtrash.util.Constant;
import com.llw.mvplibrary.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.MainView {

    private static final String TAG = "MainActivity";
    private EditText etGoods;
    private ImageView ivClear;
    private RecyclerView rvResult;
    private List<TrashResponse.NewsListBean> newsListBeanList = new ArrayList<>();
    private SearchGoodsAdapter searchGoodsAdapter;


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }


    private void initView() {//页面初始化
        etGoods = findViewById(R.id.et_goods);
        ivClear = findViewById(R.id.iv_clear);
        rvResult = findViewById(R.id.rv_result);

        //配置适配器 设置布局和数据源
        searchGoodsAdapter = new SearchGoodsAdapter(R.layout.item_search_rv, newsListBeanList);

        //设置列表的布局管理器
        rvResult.setLayoutManager(new LinearLayoutManager(this));
        //列表item的点击事件
        searchGoodsAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
            showMsg("点击了" + newsListBeanList.get(position).getName());
        }));

        rvResult.setAdapter(searchGoodsAdapter);
        //设置输入监听
        etGoods.addTextChangedListener(new TextWatcher() {
         @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() < 1) {
                    ivClear.setVisibility(View.INVISIBLE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //设置动作监听
        etGoods.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String word = etGoods.getText().toString().trim();
                if (word.isEmpty()) {
                    showMsg("请输入物品名");
                } else {
                    //显示加载弹窗
                    showLoadingDialog();
                    //控制输入法
                    controlInputMethod();
                    //请求接口
                    mPresenter.searchGoods(word);
                }
                return true;
            }
            return false;
        });

        ivClear.setOnClickListener(v -> {
            controlInputMethod();
            etGoods.setText("");
        });
    }

    /**
     * 控制输入法，当输入法打开时关闭，关闭时弹出
     */
    private void controlInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void getSearchResponse(TrashResponse response) {//搜索物品返回數據
//隱藏加載彈窗
        hideLoadingDialog();
        if (response.getCode() == Constant.SUCCESS_CODE) {
            //請求成功,進行數據渲染
            if (response.getNewsList() != null && response.getNewsList().size() > 0) {
                newsListBeanList.clear();
                newsListBeanList.addAll(response.getNewsList());
                //刷新適配器
//                searchGoodsAdapter.notifyDataSetChanged();
            } else {
                showMsg("觸及到了知識盲區");
            }
        } else {
            //顯示請求接口失敗的原因
            showMsg(response.getMsg());
        }
    }

    @Override
    public void getSearchResponseFailed(Throwable throwable) {//搜索物品失敗返回
        hideLoadingDialog();
        Log.e(TAG, throwable.toString());
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainContract.MainPresenter();
    }
}