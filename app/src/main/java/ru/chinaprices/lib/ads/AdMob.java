package ru.chinaprices.lib.ads;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class AdMob extends Ad {
    private String id;
    private AdSize adSize;

    private List<String> keywords = new ArrayList<String>();

    public AdMob(Activity activity, String id, AdSize adSize) {
        super(activity);
        this.id = id;
        this.adSize = adSize;
    }

    public AdMob(Activity activity, String id) {
        this(activity, id, AdSize.BANNER);
    }

    public void setAdSize(AdSize adSize) {
        this.adSize = adSize;
    }

    public void addKeyword(String keyword) {
        this.keywords.add(keyword);
    }

    @Override
    public View getView() {
        final AdView mAdView = new AdView(activity);
        final AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        final AdMob ad = this;

        mAdView.setAdUnitId(id);
        mAdView.setAdSize(adSize);

        for (String keyword : keywords) {
            adRequestBuilder.addKeyword(keyword);
        }

        // TODO setGender setBirthday AdListener
        // https://developers.google.com/mobile-ads-sdk/docs/admob/intermediate

        // TODO add more
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

        if (adListener != null) {
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLeftApplication() {
                    adListener.onClick(ad);
                }
            });
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdView.loadAd(adRequestBuilder.build());
            }
        });

        return mAdView;
    }
}
