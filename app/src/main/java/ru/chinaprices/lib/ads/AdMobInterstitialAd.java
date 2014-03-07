package ru.chinaprices.lib.ads;

import android.app.Activity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import java.util.List;

public class AdMobInterstitialAd extends InterstitialAd {

    private String id;

    private List<String> keywords = new ArrayList<String>();

    public AdMobInterstitialAd(Activity activity, String id) {
        super(activity);
        this.id = id;
    }

    public void addKeyword(String keyword) {
        this.keywords.add(keyword);
    }


    @Override
    public void show() {
        final com.google.android.gms.ads.InterstitialAd interstitial = new com.google.android.gms.ads.InterstitialAd(activity);

        interstitial.setAdUnitId(id);

        AdRequest adRequest = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                interstitial.show();
            }
        });
    }
}
