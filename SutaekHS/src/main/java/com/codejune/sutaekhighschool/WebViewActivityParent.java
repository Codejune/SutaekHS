package com.codejune.sutaekhighschool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivityParent extends ActionBarActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWebView = (WebView) findViewById(R.id.webView);
        String ViewURL = getIntent().getStringExtra("URL");
        mWebSettings = mWebView.getSettings();
        mWebSettings.setSaveFormData(false);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new webViewChrome());
        mWebView.setWebViewClient(new webViewClient());
        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        mWebView.loadUrl(ViewURL);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class webViewChrome extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url,
                                        String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }

    public class webViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView mView, String url) {
            mView.loadUrl(url);

            return super.shouldOverrideUrlLoading(mView, url);
        }
    }

}
