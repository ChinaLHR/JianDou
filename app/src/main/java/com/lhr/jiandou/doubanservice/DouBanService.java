package com.lhr.jiandou.doubanservice;

import com.lhr.jiandou.model.bean.MovieHttpResult;
import com.lhr.jiandou.model.bean.SubjectsBean;

import java.util.List;

import retrofit2.http.GET;
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
     * @return
     */
    @GET("search")
    Observable<MovieHttpResult<List<SubjectsBean>>> getMovieByTag
    (@Query("tag") String tag, @Query("start") int start, @Query("count") int count);
}
