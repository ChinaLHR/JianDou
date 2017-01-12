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
        /**
         *  手动创建一个OkHttpClient并设置超时时间
         */

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

    /**
     * 根据tag,start与count获取豆瓣电影
     */
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

    /**
     * 根据Id获取电影详情
     */
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
    /**
     * 根据Id获取影人信息
     */
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

    /**
     * 获取正在热映
     * @param subscriber
     * @param start
     * @param count
     */
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

    /**
     * 即将上映
     * @param subscriber
     * @param start
     * @param count
     */
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

    /**
     * Top250
     * @param subscriber
     * @param start
     * @param count
     */
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

    /**
     * 相同格式的Http请求数据统一进行预处理，将HttpResult的Data部分剥离出来给subseriber
     * T为真正需要的类型，也就是Data部分
     */
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
