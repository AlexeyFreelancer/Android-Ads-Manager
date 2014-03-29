package ru.chinaprices.lib.ads;

import android.app.Activity;

abstract public class InterstitialAd implements InterstitialAdInterface {
    protected Activity activity;
    protected InterstitialAdListener adListener;

    public InterstitialAd(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setAdListener(InterstitialAdListener adListener) {
        this.adListener = adListener;
    }
}
