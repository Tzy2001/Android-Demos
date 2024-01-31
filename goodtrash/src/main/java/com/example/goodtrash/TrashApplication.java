package com.example.goodtrash;

import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.network.NetworkApi;

/**
 * @ClassName TrashApplication 自定义Application
 * @Author TZY
 * @Date 2024/1/25 17:18
 **/
public class TrashApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络框架
        NetworkApi.init(new NetworkRequiredInfo(this));
    }
}
