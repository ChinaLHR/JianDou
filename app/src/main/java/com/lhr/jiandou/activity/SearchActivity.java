package com.lhr.jiandou.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.SearchBookAdapter;
import com.lhr.jiandou.adapter.SearchMovieAdapter;
import com.lhr.jiandou.adapter.SpinnerAdapter;
import com.lhr.jiandou.adapter.base.BaseSearchAdapter;
import com.lhr.jiandou.model.bean.BooksBean;
import com.lhr.jiandou.model.bean.SpinnerBean;
import com.lhr.jiandou.model.bean.SubjectsBean;
import com.lhr.jiandou.model.httputils.BookHttpMethods;
import com.lhr.jiandou.model.httputils.MovieHttpMethods;
import com.lhr.jiandou.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class SearchActivity extends AppCompatActivity {
    private android.support.v7.widget.SearchView search;
    private android.widget.Spinner spinner;
    private int SEARCH_MOVIE = 0;
    private int SEARCH_BOOK = 1;
    private int SEARCH_TAG = 0;
    private final int COUNT = 10;
    private int mStart = 0;
    private String q;

    private SearchMovieAdapter mMovieAdapter;
    private SearchBookAdapter mBookAdapter;
    private android.support.v7.widget.RecyclerView searchrv;

    private Subscriber<List<SubjectsBean>> moviesub;
    private Subscriber<List<BooksBean>> booksub;
    private List<SubjectsBean> mMovieBean;
    private List<BooksBean> mBookBean;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mFootView;
    private android.widget.ProgressBar searchpb;
    private android.support.design.widget.FloatingActionButton searchfab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.searchfab = (FloatingActionButton) findViewById(R.id.search_fab);
        this.searchpb = (ProgressBar) findViewById(R.id.search_pb);
        this.searchrv = (RecyclerView) findViewById(R.id.search_rv);
        this.spinner = (Spinner) findViewById(R.id.spinner);
        this.search = (SearchView) findViewById(R.id.search);
        initView();
        initListener();
    }

    private void initListener() {
        //查询监听
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击查询按钮
            @Override
            public boolean onQueryTextSubmit(String query) {
                q = query;
                query(query);
                return true;
            }

            //查询框文字发送发生变化
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    SEARCH_TAG = SEARCH_MOVIE;
                } else if (i == 1) {
                    SEARCH_TAG = SEARCH_BOOK;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchrv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1) && MyApplication.isNetworkAvailable(SearchActivity.this)) {
                    if (SEARCH_TAG == SEARCH_MOVIE) {
                        updateMovie();
                        mFootView.setVisibility(View.VISIBLE);
                        searchrv.scrollToPosition(mMovieAdapter.getItemCount() - 1);
                    } else if (SEARCH_TAG == SEARCH_BOOK) {
                        updateBook();
                        mFootView.setVisibility(View.VISIBLE);
                        searchrv.scrollToPosition(mBookAdapter.getItemCount() - 1);
                    }

                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        searchfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchrv.scrollToPosition(0);
            }
        });
    }

    private void updateBook() {
        if (mBookAdapter.getStart() == mStart) return;
        mStart = mBookAdapter.getStart();
        booksub = new Subscriber<List<BooksBean>>() {
            @Override
            public void onCompleted() {
                mFootView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                mFootView.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<BooksBean> list) {
                if (!list.isEmpty()) {
                    mBookAdapter.addData(list);
                }
            }
        };

        BookHttpMethods.getInstance().getBookByQ(booksub, q, mStart, COUNT);
    }

    private void updateMovie() {
        if (mMovieAdapter.getStart() == mStart) return;
        mStart = mMovieAdapter.getStart();
        moviesub = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {
                mFootView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                mFootView.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<SubjectsBean> list) {
                if (!list.isEmpty()) {
                    mMovieAdapter.addData(list);
                }
            }
        };
        MovieHttpMethods.getInstance().getMovieByQ(moviesub, q, mStart, COUNT);
    }


    private void initView() {
        SearchManager mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));
        //开启输入文字的清除与查询按钮
        search.setSubmitButtonEnabled(true);
        List<SpinnerBean> list = new ArrayList<>();
        list.add(new SpinnerBean("电影", R.drawable.search_movie));
        list.add(new SpinnerBean("图书", R.drawable.search_book));

        SpinnerAdapter madapter = new SpinnerAdapter(this, list);
        spinner.setAdapter(madapter);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchrv.setLayoutManager(mLayoutManager);
        mFootView = LayoutInflater.from(this).inflate(R.layout.item_footer, searchrv, false);

    }

    private void query(String query) {


        if (SEARCH_TAG == 0) {
            SearchMovie(query);
        } else if (SEARCH_TAG == 1) {
            SearchBook(query);
        }
        search.setQuery("", false);
        search.onActionViewCollapsed();

    }

    private void SearchBook(String query) {
        showProgressbar();

        booksub = new Subscriber<List<BooksBean>>() {
            @Override
            public void onCompleted() {
                closeProgressbar();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressbar();
                ToastUtils.show(SearchActivity.this, "没有搜索到结果");
            }

            @Override
            public void onNext(List<BooksBean> booksBeen) {
                if (!booksBeen.isEmpty()) {
                    mBookBean = booksBeen;
                    initRecyclerView();
                } else {
                    ToastUtils.show(SearchActivity.this, "没有搜索到结果");
                }

            }
        };

        BookHttpMethods.getInstance().getBookByQ(booksub, query, 0, COUNT);
    }

    private void SearchMovie(String query) {
        showProgressbar();
        moviesub = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {
                closeProgressbar();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressbar();
                ToastUtils.show(SearchActivity.this, "没有搜索到结果");
            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {
                if (!subjectsBeen.isEmpty()) {
                    mMovieBean = subjectsBeen;
                    initRecyclerView();
                } else {
                    ToastUtils.show(SearchActivity.this, "没有搜索到结果");
                }

            }
        };

        MovieHttpMethods.getInstance().getMovieByQ(moviesub, query, 0, COUNT);
    }

    private void initRecyclerView() {
        searchfab.setVisibility(View.VISIBLE);
        if (SEARCH_TAG == SEARCH_MOVIE) {
            mMovieAdapter = new SearchMovieAdapter(this, mMovieBean);
            mMovieAdapter.setFooterView(mFootView);
            searchrv.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(new BaseSearchAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String id, String url) {
                    MovieDetailsActivity.toActivity(SearchActivity.this, id, url);
                }
            });
        } else if (SEARCH_TAG == SEARCH_BOOK) {
            mBookAdapter = new SearchBookAdapter(this, mBookBean);
            mBookAdapter.setFooterView(mFootView);
            searchrv.setAdapter(mBookAdapter);
            mBookAdapter.setOnItemClickListener(new BaseSearchAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String id, String url) {
                    BookDetailsActivity.toActivity(SearchActivity.this, id, url);
                }
            });
        }

    }

    private void showProgressbar() {
        searchpb.setVisibility(View.VISIBLE);
    }

    private void closeProgressbar() {
        searchpb.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        if (booksub!=null) booksub.unsubscribe();
        if (moviesub!=null)moviesub.unsubscribe();
        super.onDestroy();
    }
}
