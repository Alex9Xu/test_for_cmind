package com.alex9xu.mytest.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alex9xu.mytest.R;
import com.alex9xu.mytest.base.TestApp;
import com.alex9xu.mytest.utils.LogHelper;
import com.alex9xu.mytest.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.os.StrictMode.setThreadPolicy;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String SPECIAL_WEB = "yahoo.com";

    // About ImageView
    private ImageView mImgShow;
    private TextView mTvwGuide;
    private List<String> mImgUrlList;

    // About WebView
    private WebView mWebView;
    private ProgressBar mProgressbar;
    private String mWebViewUrl;
    private Button mBtnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Alex9Xu@hotmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTvwGuide = (TextView) findViewById(R.id.activity_main_tvw_guide);
        mImgShow = (ImageView) findViewById(R.id.activity_main_img_show);
        mImgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAndShowImage();
            }
        });

        mWebView = (WebView) findViewById(R.id.activity_main_web_view);
        mProgressbar = (ProgressBar) findViewById(R.id.activity_main_progressBar);
        mBtnRefresh = (Button) findViewById(R.id.activity_main_btn_refresh);
    }

    private void initData() {
        mImgUrlList = new ArrayList<>();
        mImgUrlList.add("https://www.baidu.com/img/bd_logo1.png");
        mImgUrlList.add("https://www.sogou.com/index/images/logo_440x140.png");
        mImgUrlList.add("http://dl.pinyin.sogou.com/index/header/logo.png");

        mWebViewUrl = "http://www.bing.com";
        initWebView();
    }

    private void loadAndShowImage() {
        int randomNum = (int) (Math.random() * mImgUrlList.size());
        String randomUrl = mImgUrlList.get(randomNum);
        Picasso.with(TestApp.getAppContext()).load(randomUrl).fit().into(mImgShow);

        mTvwGuide.setVisibility(View.GONE);
        mImgShow.setBackground(null);
    }
    
    private void initWebView() {
        LogHelper.d("MainActivity", "loadWebView, Start");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);

        WebSettings webViewSettings = mWebView.getSettings();
        webViewSettings.setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            webViewSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            webViewSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        webViewSettings.setDatabaseEnabled(true);
        webViewSettings.setAllowFileAccess(true);
        webViewSettings.setDomStorageEnabled(true);
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebChromeClient(new CustomChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());

        // Security
        webViewSettings.setSavePassword(false);
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        loadWebView(mWebViewUrl);
    }

    private void loadWebView(final String webUrl) {
        if(! StringUtils.isStringEmpty(webUrl)) {
            if(TestApp.getInstance().isHaveNetWork()) {
                LogHelper.d("MainActivity", "loadWebView, p1");
                mWebView.loadUrl(mWebViewUrl);
            } else {
                LogHelper.d("MainActivity", "loadWebView, p2");
                mBtnRefresh.setVisibility(View.VISIBLE);
                Toast.makeText(this, getString(R.string.tip_network), Toast.LENGTH_SHORT).show();
                mBtnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBtnRefresh.setVisibility(View.GONE);
                        loadWebView(webUrl);
                    }
                });
            }
        } else {
            LogHelper.d("MainActivity", "loadWebView, mWebViewUrl = null");
        }
    }

    class CustomChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressbar.setVisibility(View.GONE);
            } else {
                if (mProgressbar.getVisibility() == View.GONE)
                    mProgressbar.setVisibility(View.VISIBLE);
                mProgressbar.setProgress(newProgress + 20);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(SPECIAL_WEB)) {
                Intent intent = new Intent(MainActivity.this, NumListActivity.class);
                startActivity(intent);
                return true;
            } else {
                view.loadUrl(url);
                return true;
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.loadUrl("file:///android_asset/app_error.html");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // JavaScriptEnabled here, to save power
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // JavaScriptEnabled false, to release resource
        mWebView.getSettings().setJavaScriptEnabled(false);
    }

}
