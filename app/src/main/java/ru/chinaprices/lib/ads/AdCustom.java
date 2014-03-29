package ru.chinaprices.lib.ads;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

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

        final WebSettings webSettings = adView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        adView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

        if (adListener != null) {
            adView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    adListener.onClick(ad);
                    return false;
                }
            });
        }

        return adView;
    }
}
