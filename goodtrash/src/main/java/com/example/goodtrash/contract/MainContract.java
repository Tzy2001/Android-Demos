package com.example.goodtrash.contract;

import android.annotation.SuppressLint;

import com.example.goodtrash.api.ApiService;
import com.example.goodtrash.model.TrashResponse;
import com.llw.mvplibrary.base.BasePresenter;
import com.llw.mvplibrary.base.BaseView;
import com.llw.mvplibrary.network.NetworkApi;
import com.llw.mvplibrary.network.observer.BaseObserver;

/**
 * @ClassName MainContract
 * @Author TZY
 * @Date 2024/1/25 17:33
 **/
public class MainContract {

    public static class MainPresenter extends BasePresenter<MainView> {
        //搜索物品
        @SuppressLint("CheckResult")
        public void searchGoods(String word) {
            ApiService service = NetworkApi.createService(ApiService.class);
            service.searchGoods(word).compose(NetworkApi.applySchedulers(new BaseObserver<TrashResponse>() {
                @Override
                public void onSuccess(TrashResponse trashResponse) {
                    if (getView() != null) {
                        getView().getSearchResponse(trashResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getSearchResponseFailed(e);
                    }
                }
            }));
        }
    }

    public interface MainView extends BaseView {
        //搜索物品返回
        void getSearchResponse(TrashResponse response);


        //搜索物品异常返回
        void getSearchResponseFailed(Throwable throwable);
    }
}
