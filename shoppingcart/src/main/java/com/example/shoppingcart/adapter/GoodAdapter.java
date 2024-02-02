package com.example.shoppingcart.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.shoppingcart.R;
import com.example.shoppingcart.bean.CartResponse;

import java.util.List;

/**
 * @ClassName GoodAdapter
 * @Author TZY
 * @Date 2024/2/1 15:50
 **/
public class GoodAdapter extends BaseQuickAdapter<CartResponse.OrderDataBean.CartListBean, BaseViewHolder> {

    private Context mContext;

    public GoodAdapter(int layoutId, @Nullable List<CartResponse.OrderDataBean.CartListBean> data, Context context) {
        super(layoutId, data);
        this.mContext = context;
        addChildClickViewIds(R.id.iv_checked_goods);//选中商品
        addChildClickViewIds(R.id.tv_increase_goods_num);//增加商品
        addChildClickViewIds(R.id.tv_reduce_goods_num);//减少商品
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CartResponse.OrderDataBean.CartListBean item) {
        helper.setText(R.id.tv_good_name, item.getProductName())
                .setText(R.id.tv_good_color, item.getColor())
                .setText(R.id.tv_good_size, item.getSize())
                .setText(R.id.tv_goods_price, item.getPrice() + "")
                .setText(R.id.tv_goods_num, item.getCount() + "");
        ImageView goodImg = helper.getView(R.id.iv_goods);

        Glide.with(mContext).load(item.getDefaultPic()).into(goodImg);
        ImageView checkedGoods = helper.getView(R.id.iv_checked_goods);
        if (item.isChecked()){
            checkedGoods.setImageDrawable(mContext.getDrawable(R.drawable.ic_checked));
        }else {
            checkedGoods.setImageDrawable(mContext.getDrawable(R.drawable.ic_check));
        }


    }
}
