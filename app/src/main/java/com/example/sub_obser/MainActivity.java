package com.example.sub_obser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {
    public final static String TAG="观察者";
    public final static String tag="观察者";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //建立连接
      //  observable.subscribe(observer);
        test();
    }

    ////创建观察者 Observer第一种方式
    Observer<Integer> observer = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "订阅成功");
        }

        @Override
        public void onNext(Integer value) {
            Log.d(TAG, "测试看见的数值+" + value);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "出错");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "结束");
        }
    };
    //创建观察者 Observer第二种方式
    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onSubscribe(Subscription s) {
            Log.d(tag, "onSubscribe!");
        }

        @Override
        public void onNext(String s) {
            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onComplete() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }

    };


    // 被观察者 Observable：
    Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        }
    });


public  void test(){

    Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();

        }
    }).subscribe(new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "成功");
        }

        @Override
        public void onNext(Integer value) {
            Log.d(TAG, "数值+++" + value);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "出错");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "完成");
        }
    });
}


}
