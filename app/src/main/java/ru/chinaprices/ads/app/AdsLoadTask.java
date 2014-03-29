package ru.chinaprices.ads.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.chinaprices.lib.ads.Ad;
import ru.chinaprices.lib.ads.AdCustom;
import ru.chinaprices.lib.ads.AdInterface;
import ru.chinaprices.lib.ads.AdManager;
import ru.chinaprices.lib.ads.AdMob;
import ru.chinaprices.lib.ads.AdMobInterstitialAd;
import ru.chinaprices.lib.ads.AdPosition;
import ru.chinaprices.lib.ads.AdStartAd;
import ru.chinaprices.lib.ads.InterstitialAd;
import ru.chinaprices.lib.ads.InterstitialAdInterface;
import ru.chinaprices.lib.ads.InterstitialAdListener;
import ru.chinaprices.lib.ads.adapter.AdListAdapter;
import ru.chinaprices.lib.ads.AdListener;

public class AdsLoadTask extends AsyncTask<String, Integer, AdsSettings> {

    private static final String LOG_TAG = "AdsLoadTask";
    private static final String ADS_SETTINGS_URL = "http://chinaprices.ru/api/test_ads_settings.php";

    private Activity activity;
    private String section;
    private AdListAdapter listAdapter;
    private String query;

    public AdsLoadTask(Activity activity, String section, AdListAdapter listAdapter, String query) {
        this.activity = activity;
        this.section = section;
        this.listAdapter = listAdapter;
        this.query = query;
    }

    @Override
    protected AdsSettings doInBackground(String[] params) {
        AdsSettings adsSettings;

        try {
            adsSettings = getAdsSettings(activity, section);
        } catch (IOException e) {
            Log.e(LOG_TAG, "getAdsSettings(): " + e.getMessage());
            return null;
        } catch (JsonIOException e) {
            Log.e(LOG_TAG, "getAdsSettings(): " + e.getMessage());
            return null;
        } catch (JsonSyntaxException e) {
            Log.e(LOG_TAG, "getAdsSettings(): " + e.getMessage());
            return null;
        }

        return adsSettings;
    }

    @Override
    protected void onPostExecute(final AdsSettings adsSettings) {

        if (adsSettings == null) {
            return;
        }

        if (activity == null) {
            return;
        }

        final AdPosition adPosition;
        try {
            adPosition = getAdPosition(adsSettings);
        } catch (IOException e) {
            Log.e(LOG_TAG, "getAdPosition(): " + e.getMessage());
            return;
        }

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (adPosition.equals(adPosition.INTERSTITIAL)) {
                    InterstitialAd interstitialAd;
                    try {
                        interstitialAd = getInterstitialAd(activity, adsSettings, query);
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "getInterstitialAd(): " + e.getMessage());
                        return;
                    }

                    interstitialAd.setAdListener(new InterstitialAdListener() {
                        @Override
                        public void onClick(InterstitialAdInterface ad) {
                            Log.v(LOG_TAG, "Click on ad: " + ad.getClass().getSimpleName());
                            Toast.makeText(activity, "Click on ad:" + ad.getClass().getSimpleName(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                    interstitialAd.show();
                }

                Ad ad;
                try {
                    ad = getAd(activity, adsSettings, query);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "getAd(): " + e.getMessage());
                    return;
                }

                ad.setAdListener(new AdListener() {
                    @Override
                    public void onClick(AdInterface ad) {
                        Log.v(LOG_TAG, "Click on ad: " + ad.getClass().getSimpleName());
                        Toast.makeText(activity, "Click on ad:" + ad.getClass().getSimpleName(),
                                Toast.LENGTH_LONG).show();
                    }
                });

                AdManager adManager = new AdManager(ad);



                switch (adPosition) {
                    case LIST:
                        listAdapter.setStep(adsSettings.getStep());
                        listAdapter.setAd(ad);
                        listAdapter.notifyDataSetChanged();
                        break;
                    case TOP:
                        adManager.show(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                        break;
                    case BOTTOM:
                        adManager.show(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                        break;
                    case INLINE:
                        try {
                            adManager.showInView(adsSettings.getViewId());
                        } catch (NullPointerException e) {
                            Log.e(LOG_TAG, "showInView(): " + e.getMessage());
                        }
                        break;
                }
            }
        });
    }

    private static AdsSettings getAdsSettings(Activity activity, String section)
            throws IOException, JsonSyntaxException, JsonIOException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("apikey", "testads"));
        params.add(new BasicNameValuePair("section", section));

        try {
            params.add(new BasicNameValuePair("lang",
                    activity.getResources().getConfiguration().locale.getLanguage()));
        } catch (Exception e) {
            Log.e(LOG_TAG, "Get language: " + e.getMessage());
        }

        String url = ADS_SETTINGS_URL + "?" + URLEncodedUtils.format(params, "utf-8");

        Log.v(LOG_TAG, url);

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();

        InputStream is = conn.getInputStream();
        Reader reader = new InputStreamReader(is);

        return new Gson().fromJson(reader, AdsSettings.class);
    }

    private static AdPosition getAdPosition(AdsSettings adsSettings) throws IOException {
        if (adsSettings.getViewId() != null && !adsSettings.getViewId().equals("")) {
            return AdPosition.INLINE;
        } else if (adsSettings.isBottom()) {
            return AdPosition.BOTTOM;
        } else if (adsSettings.isTop()) {
            return AdPosition.TOP;
        } else if (adsSettings.isList()) {
            return AdPosition.LIST;
        } else if (adsSettings.getAdmobInterstitialAdId() != null && !adsSettings.getAdmobInterstitialAdId().equals("")) {
            return AdPosition.INTERSTITIAL;
        } else {
            throw new IOException("Position can not be found");
        }
    }

    private static Ad getAd(Activity activity, AdsSettings adsSettings, String query) throws IOException {

        // AdMob
        if (adsSettings.getAdmobId() != null && !adsSettings.getAdmobId().equals("")) {

            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
            if (status != ConnectionResult.SUCCESS) {
                throw new IOException("AdMob isGooglePlayServicesAvailable() status: " + status);
            }

            AdMob adMob = new AdMob(activity, adsSettings.getAdmobId());
            if (query != null && !query.equals("")) {
                adMob.addKeyword(query);
            }
            if (adsSettings.isSmart()) {
                adMob.setAdSize(AdSize.SMART_BANNER);
            }
            return adMob;
        }
        // StartAd
        else if (adsSettings.getStartAdId() != null && !adsSettings.getStartAdId().equals("")) {
            AdStartAd adStartAd = new AdStartAd(activity, adsSettings.getStartAdId());
            return adStartAd;
        }
        // Custom
        else if (adsSettings.getHtml() != null && !adsSettings.getHtml().trim().equals("")) {
            AdCustom adCustom = new AdCustom(activity, adsSettings.getHtml());
            return adCustom;
        }

        throw new IOException("Ad can not be created");
    }

    private static InterstitialAd getInterstitialAd(Activity activity, AdsSettings adsSettings, String query) throws IOException {

        // AdMob
        if (adsSettings.getAdmobInterstitialAdId() != null && !adsSettings.getAdmobInterstitialAdId().equals("")) {

            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
            if (status != ConnectionResult.SUCCESS) {
                throw new IOException("AdmobInterstitialAd isGooglePlayServicesAvailable() status: " + status);
            }

            AdMobInterstitialAd adMobInterstitialAd = new AdMobInterstitialAd(activity, adsSettings.getAdmobInterstitialAdId());
            if (query != null && !query.equals("")) {
                adMobInterstitialAd.addKeyword(query);
            }

            return adMobInterstitialAd;
        }

        throw new IOException("Ad can not be created");
    }
}