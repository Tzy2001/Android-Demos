package com.example.shoppingcart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.shoppingcart.R;
import com.example.shoppingcart.bean.CartResponse;
import com.example.shoppingcart.util.GoodCallback;

import java.util.List;

/**
 * @ClassName StoreAdapter
 * @Author TZY
 * @Date 2024/2/1 15:46
 **/
public class StoreAdapter extends BaseQuickAdapter<CartResponse.OrderDataBean, BaseViewHolder> {


    private Context mContext;
    private RecyclerView rvGood;

    //商品回调
    private GoodCallback goodCallback;

    //店鋪對象
    private List<CartResponse.OrderDataBean> storeBean;

    public StoreAdapter(int layoutId, @Nullable List<CartResponse.OrderDataBean> data, Context context, GoodCallback goodCallback) {
        super(layoutId, data);
        this.mContext = context;
        this.goodCallback = goodCallback;
        this.storeBean = data;
        addChildClickViewIds(R.id.iv_checked_store);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, CartResponse.OrderDataBean item) {
        rvGood = helper.getView(R.id.rv_goods);
        helper.setText(R.id.tv_store_name, item.getShopName());

        ImageView checkedStore = helper.getView(R.id.iv_checked_store);
        if (item.isChecked()) {
            checkedStore.setImageDrawable(mContext.getDrawable(R.drawable.ic_checked));
        } else {
            checkedStore.setImageDrawable(mContext.getDrawable(R.drawable.ic_check));
        }
        final GoodAdapter goodAdapter = new GoodAdapter(R.layout.item_good, item.getCartlist(), mContext);

        rvGood.setLayoutManager(new LinearLayoutManager(mContext));

        rvGood.setAdapter(goodAdapter);

        goodAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                CartResponse.OrderDataBean.CartListBean goodsBean = item.getCartlist().get(position);
                switch (view.getId()) {
                    case R.id.iv_checked_goods:
                        //選中按鈕的切換
                        goodsBean.setChecked(!goodsBean.isChecked());
                        //刷新適配器
                        goodAdapter.notifyDataSetChanged();
                        //控制店鋪是否選中
                        controlStore(item);

//                        商品控製價格
                        goodCallback.calculationPrice();
                        break;

                    case R.id.tv_increase_goods_num:
                        updateGoodsNum(goodsBean,goodAdapter,true);
                        break;
                    case R.id.tv_reduce_goods_num:
                        updateGoodsNum(goodsBean,goodAdapter,false);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    /**
     * 功能描述：控制商品是否选中
     *
     * @param shopId
     * @param state
     * @author Tzy
     * @date 2024/02/01 17:23
     */

    public void controlGoods(int shopId, boolean state) {
        //根據店鋪id選中該店鋪下所有商品
        for (CartResponse.OrderDataBean orderDataBean : storeBean) {
            //店铺id等于传递过来的店铺id,则选中该店铺下所有商品
            if (orderDataBean.getShopId() == shopId) {
                for (CartResponse.OrderDataBean.CartListBean cartListBean : orderDataBean.getCartlist()) {
                    cartListBean.setChecked(state);
                    notifyDataSetChanged();
                }
            }
        }
    }


    /**
     * 功能描述：选中店铺
     *
     * @param item
     * @author Tzy
     * @date 2024/02/01 17:24
     */

    private void controlStore(CartResponse.OrderDataBean item) {
        int num = 0;
        for (CartResponse.OrderDataBean.CartListBean bean : item.getCartlist()) {
            if (bean.isChecked()) {
                ++num;
            }
        }
        if (num == item.getCartlist().size()) {
            //全选中 传递需要选中的店铺的id过去
            goodCallback.checkedStore(item.getShopId(), true);
        } else {
            goodCallback.checkedStore(item.getShopId(), false);
        }
    }

    private void updateGoodsNum(CartResponse.OrderDataBean.CartListBean goodsBean, GoodAdapter goodsAdapter, boolean state) {
        //其实商品应该还有一个库存值或者其他的限定值，我这里写一个假的库存值为10
        int inventory = 10;
        int count = goodsBean.getCount();
        if (state) {
            if (count >= inventory) {
                Toast.makeText(mContext, "商品數量不可超過庫存值~", Toast.LENGTH_SHORT).show();
                return;
            }
            count++;
        } else {
            if (count <= 1) {
                Toast.makeText(mContext, "已經是最低商品數量", Toast.LENGTH_SHORT).show();
                return;
            }
            count--;
        }
        goodsBean.setCount(count);//設置商品數量
        //刷新適配器
        goodsAdapter.notifyDataSetChanged();
        //計算商品價格
        goodCallback.calculationPrice();
    }
}
