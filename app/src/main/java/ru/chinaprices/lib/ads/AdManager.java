package ru.chinaprices.lib.ads;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class AdManager {
    public static final int AD_VIEW_ID = 237843;
    private AdInterface ad;
    private Activity activity;
    private FrameLayout adLayout;

    public AdManager(AdInterface ad) {
        this.ad = ad;
        activity = ad.getActivity();
    }

    public void show(final int gravity) {
        if (!ad.isActive()) {
            return;
        }

        if (adLayout != null) {
            adLayout.setVisibility(View.VISIBLE);
            return;
        }

        adLayout = new FrameLayout(activity);
        adLayout.setVisibility(View.VISIBLE);

        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = gravity;

        View adView = activity.getWindow().findViewById(AD_VIEW_ID);

        if (adView != null) {
            ((FrameLayout) adView.getParent()).removeView(adView);
        }

        adView = ad.getView();

        adView.setId(AD_VIEW_ID);
        activity.addContentView(adLayout, params);
        adLayout.addView(adView);
    }

    public void hide() {
        View adView = activity.getWindow().findViewById(AD_VIEW_ID);
        if (adView != null) {
            ((FrameLayout) adView.getParent()).removeView(adView);
        }
    }

    public void showInView(final String viewId) throws NullPointerException {
        View adView = ad.getView();

        LinearLayout layout = ((LinearLayout) activity.findViewById(
                activity.getResources().getIdentifier(viewId, "id", activity.getPackageName())));

        layout.setVisibility(View.VISIBLE);
        layout.addView(adView);
    }
}
