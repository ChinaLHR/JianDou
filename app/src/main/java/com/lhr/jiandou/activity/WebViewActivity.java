package com.lhr.jiandou.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lhr.jiandou.R;

import static com.lhr.jiandou.R.id.atv_web_container;

/**
 * Created by ChinaLHR on 2016/12/31.
 * Email:13435500980@163.com
 */

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String WEB_URL = "web_url";
    private static final String WEB_TITLE = "web_title";
    private String mUrl;
    private String mTitle;
    private WebView mwebview;

    private android.support.v7.widget.Toolbar atvwebtoolbar;
    private android.widget.ProgressBar atvwebpb;
    private android.widget.ImageView webviewbtnclose;
    private android.widget.ImageView webviewbtngo;
    private android.widget.ImageView webviewbtnrefresh;
    private android.widget.ImageView webviewbtnto;

    public void toWebActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEB_URL, url);
        intent.putExtra(WEB_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mUrl = getIntent().getStringExtra(WEB_URL);
        mTitle = getIntent().getStringExtra(WEB_TITLE);
        initView();
        initListener();

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        atvwebtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 初始化V
     */
    private void initView() {
        mwebview = (WebView) findViewById(atv_web_container);
        this.atvwebpb = (ProgressBar) findViewById(R.id.atv_web_pb);
        this.atvwebtoolbar = (Toolbar) findViewById(R.id.atv_web_toolbar);
        this.webviewbtnto = (ImageView) findViewById(R.id.webview_btn_to);
        this.webviewbtnrefresh = (ImageView) findViewById(R.id.webview_btn_refresh);
        this.webviewbtngo = (ImageView) findViewById(R.id.webview_btn_go);
        this.webviewbtnclose = (ImageView) findViewById(R.id.webview_btn_close);
        webviewbtnclose.setOnClickListener(this);
        webviewbtngo.setOnClickListener(this);
        webviewbtnrefresh.setOnClickListener(this);
        webviewbtnto.setOnClickListener(this);

        atvwebtoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        atvwebtoolbar.setTitle(mTitle);
        WebSettings settings = mwebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);//设置适应屏幕
        settings.setAppCacheEnabled(true);//设置应用缓存
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置底层布局算法
        settings.setDomStorageEnabled(true);//启动DOM存储api，防止加载一半
        mwebview.setWebChromeClient(new ChromeClient());
        mwebview.setWebViewClient(new ViewClient());
        mwebview.loadUrl(mUrl);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.webview_btn_close:
                mwebview.goBack();
                break;
            case R.id.webview_btn_go:
                mwebview.goForward();
                break;
            case R.id.webview_btn_refresh:
                mwebview.reload();
                break;
            case R.id.webview_btn_to:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(mUrl);
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
                break;
        }
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            atvwebpb.setProgress(newProgress);
            if (newProgress == 100) {
                atvwebpb.setVisibility(View.GONE);
            } else {
                atvwebpb.setVisibility(View.VISIBLE);
            }
        }
    }

    private class ViewClient extends WebViewClient {
        //当一个新的URL即将加载到当前的WebView中时，给主机应用程序一个接管控制的机会。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl()!=null){
                view.loadUrl(String.valueOf(request.getUrl()));
            }
            return true;
        }
        //接收证书
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mwebview.canGoBack())
                        mwebview.goBack();
                    else
                        finish();
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }




}
