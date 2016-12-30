package com.lhr.jiandou.doubanservice;

import com.lhr.jiandou.model.bean.ActorDetailsBean;
import com.lhr.jiandou.model.bean.BookDetailsBean;
import com.lhr.jiandou.model.bean.BookHttpResult;
import com.lhr.jiandou.model.bean.BooksBean;
import com.lhr.jiandou.model.bean.MovieDetailsBean;
import com.lhr.jiandou.model.bean.MovieHttpResult;
import com.lhr.jiandou.model.bean.SubjectsBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ChinaLHR on 2016/12/15.
 * Email:13435500980@163.com
 */

public interface DouBanService {
    /**
     * 根据tag获取电影
     * https://api.douban.com/v2/movie/search?tag=喜剧&start=20
     *
     * @return
     */

    @GET("search")
    Observable<MovieHttpResult<List<SubjectsBean>>> getMovieByTag
    (@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    /**
     * 获取正在热映的电影
     * https://api.douban.com/v2/movie/in_theaters
     */
    @GET("in_theaters")
    Observable<MovieHttpResult<List<SubjectsBean>>> getMovieByHot
    (@Query("start") int start, @Query("count") int count);

    /**
     * 即将上映的电影
     * https://api.douban.com/v2/movie/coming_soon
     */
    @GET("coming_soon")
    Observable<MovieHttpResult<List<SubjectsBean>>> getMovieByNew
    (@Query("start") int start, @Query("count") int count);

    /**
     * 电影top250
     * https://api.douban.com/v2/movie/top250?start=20&count=60
     */
    @GET("top250")
    Observable<MovieHttpResult<List<SubjectsBean>>> getMovieByTop
    (@Query("start") int start, @Query("count") int count);

    /**
     * 根据tag获取图书
     * https://api.douban.com/v2/book/search?tag=日本文学&start=20&count=60
     *
     * @return
     */
    @GET("search")
    Observable<BookHttpResult<List<BooksBean>>> getBooksByTag
    (@Query("tag") String tag, @Query("start") int start, @Query("count") int count);


    @GET("subject/{MovieId}")
    Observable<MovieDetailsBean> getMovieDetails
            (@Path("MovieId") String MovieId);

    @GET("celebrity/{actorId}")
    Observable<ActorDetailsBean> getActorDetails
            (@Path("actorId") String actorId);

    @GET("{BookId}")
    Observable<BookDetailsBean> getBookDetails
            (@Path("BookId") String BookId);
}
