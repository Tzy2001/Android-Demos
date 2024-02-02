package com.example.shoppingcart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.shoppingcart.adapter.StoreAdapter;
import com.example.shoppingcart.bean.CartResponse;
import com.example.shoppingcart.util.Constant;
import com.example.shoppingcart.util.GoodCallback;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoodCallback, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView rvStore;

    private List<CartResponse.OrderDataBean> mList = new ArrayList<>();

    private StoreAdapter storeAdapter;

    private List<Integer> shopIdList = new ArrayList<>();//店鋪列表

    private double totalPrice = 0.00;//商品總價
    private int totalCount = 0;//商品總數
    private TextView tvEdit;//编辑
    private ImageView ivCheckedAll;//全选
    private TextView tvTotal;//合计价格
    private TextView tvSettlement;//结算
    private LinearLayout layEdit;//编辑底部布局
    private TextView tvShareGoods;//分享商品
    private TextView tvCollectGoods;//收藏商品
    private TextView tvDeleteGoods;//删除商品

    private boolean isEdit = false;//是否编辑

    private boolean isAllChecked = false;//是否全選

    private AlertDialog dialog;//彈窗
    private boolean isHaveGoods = false;//購物車是否有商品

    private SmartRefreshLayout refresh;//刷新佈局
    private LinearLayout layEmpty;//空佈局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void isSelectAllStore(boolean state) {
        //修改背景
        ivCheckedAll.setImageDrawable(getDrawable(state ? R.drawable.ic_checked : R.drawable.ic_check));

        for (CartResponse.OrderDataBean orderDataBean : mList) {
            //商品是否選中
            storeAdapter.controlGoods(orderDataBean.getShopId(), state);
            //店鋪是否選中
            checkedStore(orderDataBean.getShopId(), state);
        }
        isAllChecked = state;
    }

    private void initView() {
        //在Android11中棄用了
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        rvStore = findViewById(R.id.rv_store);
        tvEdit = findViewById(R.id.tv_edit);
        ivCheckedAll = findViewById(R.id.iv_checked_all);
        tvTotal = findViewById(R.id.tv_total);
        tvSettlement = findViewById(R.id.tv_settlement);
        layEdit = findViewById(R.id.lay_edit);
        tvShareGoods = findViewById(R.id.tv_share_goods);
        tvCollectGoods = findViewById(R.id.tv_collect_goods);
        tvDeleteGoods = findViewById(R.id.tv_delete_goods);
        refresh = findViewById(R.id.smart_refresh);
        layEmpty = findViewById(R.id.lay_empty);


        //禁用下拉刷新和上啦加載更多
        refresh.setEnableLoadMore(false);
        refresh.setEnableLoadMore(false);
        //下拉刷新監聽
        refresh.setOnRefreshListener(refreshLayout -> initView());

        tvEdit.setOnClickListener(this);
        ivCheckedAll.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        tvShareGoods.setOnClickListener(this);
        tvCollectGoods.setOnClickListener(this);
        tvDeleteGoods.setOnClickListener(this);
        CartResponse cartResponse = new Gson().fromJson(Constant.CAR_JSON, CartResponse.class);
        mList.addAll(cartResponse.getOrderData());
        storeAdapter = new StoreAdapter(R.layout.item_store, mList, MainActivity.this, MainActivity.this);
        rvStore.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvStore.setAdapter(storeAdapter);


        //店铺点击
        storeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                CartResponse.OrderDataBean storeBean = mList.get(position);
                if (view.getId() == R.id.iv_checked_store) {
                    storeBean.setChecked(!storeBean.isChecked());
                    storeAdapter.notifyDataSetChanged();
                    if (storeBean.isChecked()) {
                        //全選商品
                        storeAdapter.controlGoods(storeBean.getShopId(), true);
                        if (!shopIdList.contains(storeBean.getShopId())) {
                            //如果列表中没有这个店铺id且当前店铺为选中状态
                            shopIdList.add(storeBean.getShopId());
                        }
                    } else {
                        //清楚全選商品
                        storeAdapter.controlGoods(storeBean.getShopId(), false);
                        if (shopIdList.contains(storeBean.getShopId())) {
                            shopIdList.remove((Integer) storeBean.getShopId());
                        }
                    }
                    controlAllChecked();
                }
            }
        });
        isHaveGoods = true;
        //下拉加載數據完成後，關閉下拉刷新動畫
        refresh.finishRefresh();
//        隱藏佈局
        layEmpty.setVisibility(View.GONE);
    }

    /**
     * 功能描述：選中店鋪
     *
     * @param shopId
     * @param state
     * @author Tzy
     * @date 2024/02/01 17:15
     */

    @Override
    public void checkedStore(int shopId, boolean state) {
        for (CartResponse.OrderDataBean bean : mList) {
            if (shopId == bean.getShopId()) {
                bean.setChecked(state);
                storeAdapter.notifyDataSetChanged();
                //記錄選中店鋪的shopid，添加到一個列表中
                if (!shopIdList.contains(bean.getShopId()) && state) {
                    //如果列表中没有这个店铺id且当前店铺为选中状态
                    shopIdList.add(bean.getShopId());
                } else {
                    if (shopIdList.contains(bean.getShopId())) {
                        shopIdList.remove((Integer) bean.getShopId());
                    }
                }
            }
        }
        controlAllChecked();
    }

    /**
     * 功能描述：商品價格
     *
     * @author Tzy
     * @date 2024/02/01 18:16
     */

    @Override
    public void calculationPrice() {
//每次計算之前先清0
        totalPrice = 0.00;
        totalCount = 0;
        //循環購物車中的店鋪列表
        for (int i = 0; i < mList.size(); i++) {
            CartResponse.OrderDataBean orderDataBean = mList.get(i);
            //循環店鋪中的商品列表
            for (int j = 0; j < orderDataBean.getCartlist().size(); j++) {
                CartResponse.OrderDataBean.CartListBean cartListBean = orderDataBean.getCartlist().get(j);
                //當有選中商品時計算數量和價格
                if (cartListBean.isChecked()) {
                    totalCount++;
                    totalPrice += cartListBean.getPrice() * cartListBean.getCount();
                }
            }
        }
        tvTotal.setText("$" + totalPrice);
        tvSettlement.setText(totalCount == 0 ? "結算" : "結算(" + totalCount + ")");
    }

    private void controlAllChecked() {
        if (shopIdList.size() == mList.size() && mList.size() != 0) {
            ivCheckedAll.setImageDrawable(getDrawable(R.drawable.ic_checked));
            isAllChecked = true;
        } else {
            ivCheckedAll.setImageDrawable(getDrawable(R.drawable.ic_check));
            isAllChecked = false;
        }
        //計算價格
        calculationPrice();
    }

    private void deleteGoods() {
        //店铺列表
        List<CartResponse.OrderDataBean> storeList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            CartResponse.OrderDataBean store = mList.get(i);
            if (store.isChecked()) {
                //店铺如果选择则添加到此列表中
                storeList.add(store);
            }
            //商品列表
            List<CartResponse.OrderDataBean.CartListBean> goodsList = new ArrayList<>();
            List<CartResponse.OrderDataBean.CartListBean> goods = store.getCartlist();
            //循环店铺中的商品列表
            for (int j = 0; j < goods.size(); j++) {
                CartResponse.OrderDataBean.CartListBean cartListBean = goods.get(j);
                //当有选中商品时添加到列表中
                if (cartListBean.isChecked()) {
                    goodsList.add(cartListBean);
                }
            }
            //删除商品
            goods.removeAll(goodsList);
        }
        //删除店铺
        mList.removeAll(storeList);
        shopIdList.clear();//删除了选中商品，清空已选择的标识
        controlAllChecked();//控制去全选
        //改变UI界面
        tvEdit.setText("编辑");
        layEdit.setVisibility(View.GONE);
        isEdit = false;
        //刷新数据
        storeAdapter.notifyDataSetChanged();
        if (mList.size() <= 0) {
            //無商品
            isHaveGoods = false;
//            啟用下拉刷新
            refresh.setEnableLoadMore(true);
//            顯示空佈局
            layEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit://编辑
                if (!isHaveGoods) {
                    showMsg("當前購物車空空如也");
                    return;
                }
                if (isEdit) {
                    tvEdit.setText("编辑");
                    layEdit.setVisibility(View.GONE);
                    isEdit = false;
                } else {
                    tvEdit.setText("完成");
                    layEdit.setVisibility(View.VISIBLE);
                    isEdit = true;
                }
                break;
            case R.id.iv_checked_all://全选
                if (!isHaveGoods) {
                    showMsg("當前購物車空空如也");
                    return;
                }
                if (isAllChecked) {
                    //取消全選
                    isSelectAllStore(false);
                } else {
                    isSelectAllStore(true);
                }
                break;
            case R.id.tv_settlement://结算
                if (!isHaveGoods) {
                    showMsg("當前購物車空空如也");
                    return;
                }
                if (totalCount == 0) {
                    showMsg("请选择要结算的商品");
                }
                //弹窗
                dialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("总计：" + totalCount + "种商品" + totalPrice + "元")
                        .setPositiveButton("确定", (dialog1, which) -> deleteGoods())
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
                break;
            case R.id.tv_delete_goods://删除
                if (totalCount == 0) {
                    showMsg("請選擇要刪除的商品");
                }
                //彈窗
                dialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("確定要刪除所選商品碼？")
                        .setPositiveButton("確定", (dialog1, which) -> deleteGoods())
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
                break;
            case R.id.tv_collect_goods://收藏
                if (totalCount == 0) {
                    showMsg("请选择要收藏的商品");
                    return;
                }
                showMsg("收藏成功");
                break;
            case R.id.tv_share_goods://分享
                if (totalCount == 0) {
                    showMsg("请选择要分享的商品");
                    return;
                }
                showMsg("分享成功");
                break;
            default:
                break;
        }
    }
}