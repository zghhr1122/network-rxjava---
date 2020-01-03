package com.example.sub_obser;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiUrl {
    //get请求
    @GET(Constans.retrofit)
    Call<Bean> getRetrofit();

    @GET(Constans.retrofit)
    Observable<Bean> getRetrofit1();
}
