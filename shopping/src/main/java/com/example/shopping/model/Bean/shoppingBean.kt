package com.example.shopping.model.Bean


open class ProductItemParent {
    //記錄當前類所選的商品數量
    var typeSelectCount: Int = 0
}

data class ProductItem(
    var productList: List<Product>,
    //類別ID
    var typeId: String,
    var typeName: String,
) : ProductItemParent()


data class Product(
    //所屬類別ID
    var parentId: String,
//產品Id
    var productId: String,
//產品圖片
    var productImg: Int,
//產品價格
    var productMoney: Double,
//月銷
    var productMonthSale: Int,
//產品名稱
    var productName: String,


    )