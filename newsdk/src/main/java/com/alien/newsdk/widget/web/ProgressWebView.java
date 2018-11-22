package com.alien.newsdk.widget.web;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.alien.newsdk.R;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * Created by Laughing on 2016/10/19.
 * 带加载进度的webview
 */
public class ProgressWebView extends FrameLayout {


    MWebViewClient mWebViewClient;
    WebChromeClient webChromeClient;

    public interface IWebOutter {
        void onTitleGet(String title);
        void onError(int code, String message);
        void onPageFinished();
        boolean shouldOverrideUrlLoading(WebView webView, String url);
        Map<String,String> getHeads(String url);
        boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult);
        boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult);
        boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult);
    }


    ProgressBar progressBar;
    com.tencent.smtt.sdk.WebView webView;
    IWebOutter iWebOutter;


    public ProgressWebView(Context context) {
        super(context);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void onDestory(){
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearMatches();
        webView.clearSslPreferences();
        webView.clearDisappearingChildren();
        webView.clearHistory();
        webView.clearAnimation();
        webView.loadUrl("about:blank");
        webView.removeAllViews();
        webView.freeMemory();
        webView.removeAllViews();
        webView.destroy();

    }

    public void hideProgress(){
        if (progressBar!=null)
            progressBar.setVisibility(GONE);
    }

    public void setIWebOutter(boolean withWebClient,@NonNull final IWebOutter iWebOutter) {
        this.iWebOutter = iWebOutter;
        if (withWebClient) {
            mWebViewClient = new MWebViewClient(iWebOutter);
        }
        webView.setWebViewClient(mWebViewClient);

        webChromeClient = new WebChromeClient(){

            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                iWebOutter.onJsAlert(webView,s,s1,jsResult);
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
                iWebOutter.onJsConfirm(webView,s,s1,jsResult);
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
                iWebOutter.onJsPrompt(webView,s,s1,s2,jsPromptResult);
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                iWebOutter.onTitleGet(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar==null||progressBar.getVisibility() == View.GONE)return;
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }, 350);

                } else if (progressBar.getVisibility() == View.INVISIBLE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        };
        webView.setWebChromeClient(webChromeClient);
    }
    public void setIWebOutter(@NonNull final IWebOutter iWebOutter) {
        setIWebOutter(true,iWebOutter);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_progress_webview, this);

        progressBar = view.findViewById(R.id.progressbar);
        webView = view.findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setSupportZoom(true);
        settings.setAllowFileAccess(false);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setNeedInitialFocus(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLoadWithOverviewMode(true);
        extensionSettings(settings);

        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.requestFocus(FOCUS_DOWN);
        webView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });
//        webView.addJavascriptInterface(new JavaScriptObject(mContext), "xx");
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        webView.setOnTouchListener(l);
    }


    protected void extensionSettings(WebSettings settings){

    }
    public void loadUrl(String url) {
        checkClient();
        if (iWebOutter!=null) webView.loadUrl(url, iWebOutter.getHeads(url));
    }


    private void checkClient(){
//        if (mWebViewClient ==null||webChromeClient==null)
//            throw new IllegalStateException("Please set mWebViewClient&webChromeClient at first");
        if (webChromeClient==null)
            throw new IllegalStateException("Please set webChromeClient at first");
    }

    public void loadContent(String htmlContent) {
        checkClient();
        progressBar.setVisibility(GONE);
        webView.loadDataWithBaseURL(null, htmlContent, null, "utf-8", null);
    }

    public boolean onBack(){
        if (webView.canGoBack()){
            webView.goBack();
            return true;
        }else return false;
    }

    public com.tencent.smtt.sdk.WebView getWebView() {
        return webView;
    }
}