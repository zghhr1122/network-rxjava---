package com.example.sub_obser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(Constans.DEFAULT_TIME,TimeUnit.SECONDS)//设置写入超时时间
               .addInterceptor(new LogInterceptor())//添加打印拦截器
               .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
               .build();



        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constans.BaseUrl)
                //添加GSON解析：返回数据转换成GSON类型
                .addConverterFactory(GsonConverterFactory.create())
                //添加Rxjava支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
      //  ApiUrl api = retrofit.create(ApiUrl.class);

        retrofit.create(ApiUrl.class)
                .getRetrofit1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //绑定生命周期
              //  .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("111", "onSubscribe: " );
                    }
                    @Override
                    public void onNext(Bean demo) {
                        Log.e("222", "onNext: " +demo.toString());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("333", "Throwable: " + e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        Log.e("444", "onComplete: " );
                    }
                });
        //retrofit原生的访问框架
       /* Call<Bean> demo = api.getRetrofit();
        demo.enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                Log.e("SecondActivity", "请求成功信息: "+response.body().toString());
            }
            @Override
            public void onFailure(Call<Bean> call, Throwable t) {
                Log.e("SecondActivity", "请求失败信息: " +t.getMessage());
            }
        });*/

    }
}
