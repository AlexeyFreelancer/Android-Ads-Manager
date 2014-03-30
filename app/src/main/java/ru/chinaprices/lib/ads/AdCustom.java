package ru.chinaprices.lib.ads;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AdCustom extends Ad {

    private String html;

    public AdCustom(Activity activity, String html) {
        super(activity);
        this.html = html;
    }

    @Override
    public View getView() {
        final WebView adView = new WebView(activity);
        final AdCustom ad = this;

        adView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
        adView.setHorizontalScrollBarEnabled(false);
        adView.setVerticalScrollBarEnabled(false);
        adView.setFocusable(false);
        adView.setFocusableInTouchMode(false);

        final WebSettings webSettings = adView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        adView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

        adView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                activity.startActivity(intent);

                if (adListener != null) {
                    adListener.onClick(ad);
                }
                return true;
            }
        });


        return adView;
    }
}
