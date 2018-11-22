package com.alien.newsdk.widget.web;


import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by Alien on 2017/8/10.
 *
 */
class MWebViewClient extends WebViewClient {

    private ProgressWebView.IWebOutter listener;

    public MWebViewClient(ProgressWebView.IWebOutter listener) {
        this.listener = listener;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (listener!=null) listener.onPageFinished();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        if (listener!=null) return listener.shouldOverrideUrlLoading(webView,s);
        else return super.shouldOverrideUrlLoading(webView,s);
    }



    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        super.onReceivedError(webView, i, s, s1);
        if (listener!=null) listener.onError(i,s);
        String data = "Page NO FOUND！";
        webView.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
    }

}
