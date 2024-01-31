package com.example.goodtrash.api;

import static com.example.goodtrash.util.Constant.KEY;

import com.example.goodtrash.model.TrashResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @ClassName ApiService  Api接口
 * @Author TZY
 * @Date 2024/1/25 17:25
 **/
public interface ApiService {

    @GET("/txapi/lajifenlei/index?key=" + KEY)
    Observable<TrashResponse> searchGoods(@Query("word") String word);

}
