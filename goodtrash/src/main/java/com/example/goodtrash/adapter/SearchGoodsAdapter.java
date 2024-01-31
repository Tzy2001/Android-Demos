package com.example.goodtrash.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.goodtrash.R;
import com.example.goodtrash.model.TrashResponse;

import java.util.List;

/**
 * @ClassName SearchGoodsAdapter  搜索物品结果列表适配器
 * @Author TZY
 * @Date 2024/1/25 17:40
 **/
public class SearchGoodsAdapter extends BaseQuickAdapter<TrashResponse.NewsListBean, BaseViewHolder> {

    public SearchGoodsAdapter(int layoutResId, @Nullable List<TrashResponse.NewsListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrashResponse.NewsListBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_explain, item.getExplain())
                .addOnClickListener(R.id.item_search_goods);

        TextView tvType = helper.getView(R.id.tv_type);
        switch (item.getType()) {
            case 0:
                tvType.setText("可回收垃圾");
                break;
            case 1:
                tvType.setText("有害垃圾");
                break;
            case 2:
                tvType.setText("厨余垃圾");
                break;
            case 3:
                tvType.setText("干垃圾");
                break;
            default:
                break;
        }
    }


}
