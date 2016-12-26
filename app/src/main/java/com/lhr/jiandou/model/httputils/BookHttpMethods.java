package com.lhr.jiandou.model.httputils;

import com.lhr.jiandou.doubanservice.DouBanService;
import com.lhr.jiandou.model.bean.BookDetailsBean;
import com.lhr.jiandou.model.bean.BookHttpResult;
import com.lhr.jiandou.model.bean.BooksBean;

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
 * Created by ChinaLHR on 2016/12/24.
 * Email:13435500980@163.com
 */

public class BookHttpMethods {
    public static final String BASE_URL_BOOK = "https://api.douban.com/v2/book/";
    private DouBanService mBookService;
    private Retrofit retrofit_book;

    private static final int DEFAULT_TIMEOUT = 5;

    private BookHttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit_book = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL_BOOK)
                .client(httpClientBuilder.build())
                .build();
        mBookService = retrofit_book.create(DouBanService.class);
    }

    private static class Holder {
        private static final BookHttpMethods INSTANCE = new BookHttpMethods();
    }

    public static final BookHttpMethods getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 根据tag，start与count获取豆瓣图书
     */
    public void getBookByTag(Subscriber<List<BooksBean>> subscriber, String tag, int start, int count) {
        mBookService.getBooksByTag(tag, start, count)
                .map(new HttpResultFunc<List<BooksBean>>())
                .onErrorReturn(new Func1<Throwable, List<BooksBean>>() {
                    @Override
                    public List<BooksBean> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据id获取豆瓣图书
     */
    public void getBookById(Subscriber<BookDetailsBean> subscriber,String id){
        mBookService.getBookDetails(id)
                .onErrorReturn(new Func1<Throwable, BookDetailsBean>() {
                    @Override
                    public BookDetailsBean call(Throwable throwable) {
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
    private class HttpResultFunc<T> implements Func1<BookHttpResult<T>, T> {
        @Override
        public T call(BookHttpResult<T> tBookHttpResult) {
            if (tBookHttpResult != null) {
                return tBookHttpResult.getBooks();
            } else {
                throw new RuntimeException("出错了");
            }
        }
    }
}
