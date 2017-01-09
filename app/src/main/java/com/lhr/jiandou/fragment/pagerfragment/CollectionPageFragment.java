package com.lhr.jiandou.fragment.pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;
import com.lhr.jiandou.activity.ActorDetailsActivity;
import com.lhr.jiandou.activity.BookDetailsActivity;
import com.lhr.jiandou.activity.MovieDetailsActivity;
import com.lhr.jiandou.adapter.CollectionActorAdapter;
import com.lhr.jiandou.adapter.CollectionBookAdapter;
import com.lhr.jiandou.adapter.CollectionMovieAdapter;
import com.lhr.jiandou.adapter.base.BaseCollectionAdapter;
import com.lhr.jiandou.model.db.Actor_db;
import com.lhr.jiandou.model.db.Book_db;
import com.lhr.jiandou.model.db.GreenDaoUtils;
import com.lhr.jiandou.model.db.Movie_db;
import com.lhr.jiandou.model.dbinterface.DbObservrt;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.ToastUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ChinaLHR on 2017/1/7.
 * Email:13435500980@163.com
 */

public class CollectionPageFragment extends Fragment implements DbObservrt {
    private Subscriber mSubscriber;
    private String title;
    private GreenDaoUtils utils;
    private RecyclerView page_collection_rv;

    private CollectionMovieAdapter movieadapter;
    private CollectionBookAdapter mBookAdapter;
    private CollectionActorAdapter mActorAdapter;


    private List<Movie_db> moviedata;
    private List<Book_db> bookdata;
    private List<Actor_db> actordata;

    private Subscriber<List<Movie_db>> mMovieSubscriber;
    private Subscriber<List<Book_db>> mBookSubscriber;
    private Subscriber<List<Actor_db>> mActorSubscriber;

    private RecyclerView.LayoutManager mLayoutManager;

    public CollectionPageFragment(Observable<String> observable) {
        mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                title = s;
            }

        };
        observable.subscribe(mSubscriber);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = MyApplication.getDbUtils();
        utils.attach(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagefragment_collection, container, false);
        page_collection_rv = (RecyclerView) view.findViewById(R.id.page_collection_rv);
        initData();
        return view;


    }

    private void initData() {


        if (title.equals(Constants.COLLECTION_TYPE[0])) {
            getMovieByDatabase();
        } else if (title.equals(Constants.COLLECTION_TYPE[1])) {
            getBookByDatabase();
        } else if (title.equals(Constants.COLLECTION_TYPE[2])) {
            getActorByDatabase();
        }


    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (title.equals(Constants.COLLECTION_TYPE[0])) {
            movieadapter = new CollectionMovieAdapter(getActivity(), moviedata);
            page_collection_rv.setLayoutManager(mLayoutManager);
            page_collection_rv.setAdapter(movieadapter);
            movieadapter.setOnItemClickListener(new BaseCollectionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String id, String url) {
                    MovieDetailsActivity.toActivity(getActivity(), id, url);
                }
            });
        } else if (title.equals(Constants.COLLECTION_TYPE[1])) {
            mBookAdapter = new CollectionBookAdapter(getActivity(),bookdata);
            page_collection_rv.setLayoutManager(mLayoutManager);
            page_collection_rv.setAdapter(mBookAdapter);
            mBookAdapter.setOnItemClickListener(new BaseCollectionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String id, String url) {
                    BookDetailsActivity.toActivity(getActivity(),id,url);
                }
            });
        }else if (title.equals(Constants.COLLECTION_TYPE[2])){
            mActorAdapter = new CollectionActorAdapter(getActivity(),actordata);
            page_collection_rv.setLayoutManager(mLayoutManager);
            page_collection_rv.setAdapter(mActorAdapter);
            mActorAdapter.setOnItemClickListener(new BaseCollectionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String id, String url) {
                    ActorDetailsActivity.toActivity(getActivity(),id,url);
                }
            });
        }


    }

    /**
     * 异步读取movie
     */
    public void getMovieByDatabase() {
        mMovieSubscriber = new Subscriber<List<Movie_db>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show(getActivity(), "读取数据失败");
            }

            @Override
            public void onNext(List<Movie_db> list) {
                if (!list.isEmpty()) {
                    moviedata = list;
                    initView();
                }
            }
        };

        Observable.just(utils.queryAllMovie())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mMovieSubscriber);


    }

    /**
     * 异步读取book
     */
    public void getBookByDatabase() {
        mBookSubscriber = new Subscriber<List<Book_db>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show(getActivity(), "读取数据失败");
            }

            @Override
            public void onNext(List<Book_db> book_dbs) {
                if (!book_dbs.isEmpty()) {
                    bookdata = book_dbs;
                    initView();
                }
            }
        };
        Observable.just(utils.queryAllBook())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mBookSubscriber);
    }

    /**
     * 异步读取Actor
     */
    public void getActorByDatabase() {
        mActorSubscriber = new Subscriber<List<Actor_db>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show(getActivity(), "读取数据失败");
            }

            @Override
            public void onNext(List<Actor_db> actor_dbs) {
                if (!actor_dbs.isEmpty()) {
                    actordata = actor_dbs;
                    initView();
                }
            }
        };
        Observable.just(utils.queryAllActor())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mActorSubscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscriber != null) {
            mSubscriber.unsubscribe();
        }
        if (mMovieSubscriber != null) {
            mMovieSubscriber.unsubscribe();
        }
        if (mBookSubscriber != null) {
            mBookSubscriber.unsubscribe();
        }
        if (mActorSubscriber != null) {
            mActorSubscriber.unsubscribe();
        }
        utils.detach(this);
    }


    @Override
    public void updateMovie() {
        if (title.equals(Constants.COLLECTION_TYPE[0])) {
            getMovieByDatabase();
        }
    }

    @Override
    public void updateBook() {
        if (title.equals(Constants.COLLECTION_TYPE[1])) {
            getBookByDatabase();
        }
    }

    @Override
    public void updateActor() {
        if (title.equals(Constants.COLLECTION_TYPE[2])) {
            getActorByDatabase();
        }
    }
}
