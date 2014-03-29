package ru.chinaprices.lib.ads;

import android.app.Activity;

public abstract class Ad implements AdInterface {
    protected Activity activity;
    protected AdListener adListener;


    public void setAdListener(AdListener adListener) {
        this.adListener = adListener;
    }

    public Ad(Activity activity) {
        this.activity = activity;
    }

    public boolean isActive() {
        return true;
    }

    public Activity getActivity() {
        return activity;
    }
}
