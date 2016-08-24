package com.example.mytext.api;

import com.example.mytext.mode.SelectGoodEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by CTXD-24 on 2016/8/18.
 */

public interface Api {

    @GET("product/searchPruduct.do")
    Call<SelectGoodEntity>getShopInfo(@Query("sortBy")String sortBy,@Query("pageNum") int pageNum,@Query("supplierId") int supplierId);
}
