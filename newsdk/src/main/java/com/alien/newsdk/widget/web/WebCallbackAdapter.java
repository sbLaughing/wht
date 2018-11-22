package com.alien.newsdk.widget.web;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * <p>
 * Created by Alien on 2018/3/22.
 */

public class WebCallbackAdapter implements ProgressWebView.IWebOutter {
    @Override
    public void onTitleGet(String title) {
    }

    @Override
    public void onError(int code, String message) {

    }

    @Override
    public void onPageFinished() {

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        webView.loadUrl(url);
        return true;
    }

    @Override
    public Map<String, String> getHeads(String url) {
        return new HashMap<>();
    }

    @Override
    public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
        return false;
    }

    @Override
    public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
        return false;
    }

    @Override
    public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
        return false;
    }
}
