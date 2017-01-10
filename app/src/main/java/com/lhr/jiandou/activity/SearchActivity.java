package com.lhr.jiandou.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.SpinnerAdapter;
import com.lhr.jiandou.model.bean.SpinnerBean;
import com.lhr.jiandou.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
               if (i==0){
                   SEARCH_TAG = SEARCH_MOVIE;
               }else if(i==1){
                   SEARCH_TAG = SEARCH_BOOK;
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initView() {
        SearchManager mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));
        //开启输入文字的清除与查询按钮
        search.setSubmitButtonEnabled(true);
        //设置默认打开
        search.onActionViewExpanded();
        List<SpinnerBean> list = new ArrayList<>();
        list.add(new SpinnerBean("电影", R.drawable.search_movie));
        list.add(new SpinnerBean("图书", R.drawable.search_book));

        SpinnerAdapter madapter = new SpinnerAdapter(this, list);
        spinner.setAdapter(madapter);
    }

    private void query(String query) {
        search.setQuery("",false);

        if (SEARCH_TAG==0){
            ToastUtils.show(this, "搜索电影："+query);
        }else if (SEARCH_TAG==1){
            ToastUtils.show(this, "搜索图书："+query);
        }

    }

}
