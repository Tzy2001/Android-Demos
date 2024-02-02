package com.example.shoppingcart.util;

/**
 * 类功能描述：商品回调接口
 *
 * @author Tzy
 * @date 2024/02/01 17:07
 */

public interface GoodCallback {

    /**
     * 功能描述：选中店铺
     *
     * @param shopId
     * @param state
     * @author Tzy
     * @date 2024/02/01 17:08
     */

    void checkedStore(int shopId,boolean state);


    /**
     * 功能描述：計算價格
     *
     * @author Tzy
     * @date 2024/02/01 18:13
     */

    void calculationPrice();
}
