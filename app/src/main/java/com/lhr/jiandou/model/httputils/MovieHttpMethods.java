package com.lhr.jiandou.model.httputils;

import com.lhr.jiandou.doubanservice.DouBanService;
import com.lhr.jiandou.model.bean.ActorDetailsBean;
import com.lhr.jiandou.model.bean.MovieDetailsBean;
import com.lhr.jiandou.model.bean.MovieHttpResult;
import com.lhr.jiandou.model.bean.SubjectsBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ChinaLHR on 2016/12/15.
 * Email:13435500980@163.com
 */

public class MovieHttpMethods {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit_movie;
    private DouBanService mDouBanService;

    private MovieHttpMethods() {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit_movie = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClientBuilder.build())
                .build();
        mDouBanService = retrofit_movie.create(DouBanService.class);


    }

    private static class Holder {
        private static final MovieHttpMethods INSTANCE = new MovieHttpMethods();
    }

    public static final MovieHttpMethods getInstance() {
        return Holder.INSTANCE;
    }


    public void getMovieByTag(Subscriber<List<SubjectsBean>> subscriber, String tag, int start, int count) {
        mDouBanService.getMovieByTag(tag, start, count)
                .map(new HttpResultFunc<List<SubjectsBean>>())
                .onErrorReturn(new Func1<Throwable, List<SubjectsBean>>() {
                    @Override
                    public List<SubjectsBean> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void getMovieById(Subscriber<MovieDetailsBean> subscriber, String id){
        mDouBanService.getMovieDetails(id)
                .onErrorReturn(new Func1<Throwable, MovieDetailsBean>() {
                    @Override
                    public MovieDetailsBean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getActorById(Subscriber<ActorDetailsBean> subscriber,String id){
        mDouBanService.getActorDetails(id)
                .onErrorReturn(new Func1<Throwable, ActorDetailsBean>() {
                    @Override
                    public ActorDetailsBean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getMovieHot(Subscriber<List<SubjectsBean>> subscriber,int start, int count){
        mDouBanService.getMovieByHot(start,count)
                .map(new HttpResultFunc<List<SubjectsBean>>())
                .onErrorReturn(new Func1<Throwable, List<SubjectsBean>>() {
                    @Override
                    public List<SubjectsBean> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    public void getMovieNew(Subscriber<List<SubjectsBean>> subscriber,int start, int count){
        mDouBanService.getMovieByNew(start,count)
                .map(new HttpResultFunc<List<SubjectsBean>>())
                .onErrorReturn(new Func1<Throwable, List<SubjectsBean>>() {
                    @Override
                    public List<SubjectsBean> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    public void getMovieTop250(Subscriber<List<SubjectsBean>> subscriber,int start,int count){
        mDouBanService.getMovieByTop(start,count)
                .map(new HttpResultFunc<List<SubjectsBean>>())
                .onErrorReturn(new Func1<Throwable, List<SubjectsBean>>() {
                    @Override
                    public List<SubjectsBean> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getMovieByQ(Subscriber<List<SubjectsBean>> subscriber, String q, int start, int count) {
        mDouBanService.getMovieByQ(q, start, count)
                .map(new HttpResultFunc<List<SubjectsBean>>())
                .onErrorReturn(new Func1<Throwable, List<SubjectsBean>>() {
                    @Override
                    public List<SubjectsBean> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    private class HttpResultFunc<T> implements Func1<MovieHttpResult<T>, T> {
        @Override
        public T call(MovieHttpResult<T> httpResult) {
            if (httpResult != null) {
                return httpResult.getSubjects();
            } else {
                throw new RuntimeException("出错了");
            }
        }
    }




}
