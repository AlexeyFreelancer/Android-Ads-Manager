package ru.chinaprices.lib.ads;

import android.app.Activity;
import android.view.View;

import com.startad.lib.SADView;

import java.util.Locale;

/**
 * URL: http://startad.mobi/
 */
public class AdStartAd extends Ad {

    private final String id;

    public AdStartAd(Activity activity, String id) {
        super(activity);
        this.id = id;
    }

    @Override
    public View getView() {
        SADView sadView = new SADView(activity, id);

        Locale locale = activity.getResources().getConfiguration().locale;
        if (locale.getLanguage().equals("ru")
                || locale.getLanguage().equals("uk")
                || locale.getLanguage().equals("bg")) {

            sadView.loadAd(SADView.LANGUAGE_RU);
        } else {
            sadView.loadAd(SADView.LANGUAGE_EN);
        }

        return sadView;
    }
}
