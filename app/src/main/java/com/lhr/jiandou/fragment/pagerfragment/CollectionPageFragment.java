package com.lhr.jiandou.fragment.pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lhr.jiandou.R;
import com.lhr.jiandou.utils.LogUtils;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ChinaLHR on 2017/1/7.
 * Email:13435500980@163.com
 */

public class CollectionPageFragment extends Fragment {
    private Subscriber mSubscriber;
    private String title;


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
                LogUtils.e(title);
            }

        };
        observable.subscribe(mSubscriber);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagefragment_collection, container, false);
        TextView page_collection_tv = (TextView) view.findViewById(R.id.page_collection_tv);
        if (title!=null) {
            page_collection_tv.setText(title);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscriber != null) {
            mSubscriber.unsubscribe();
        }
    }
}
