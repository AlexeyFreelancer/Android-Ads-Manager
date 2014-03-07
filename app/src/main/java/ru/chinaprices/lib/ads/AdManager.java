package ru.chinaprices.lib.ads;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class AdManager {
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

        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = gravity;

        int adViewId = 237843;

        View adView = activity.getWindow().findViewById(adViewId);

        if (adView != null) {
            ((FrameLayout) adView.getParent()).removeView(adView);
        }

        adView = ad.getView();
        adView.setId(adViewId);
        activity.addContentView(adLayout, params);
        adLayout.addView(adView);
    }

    public void hide() {
        adLayout.setVisibility(View.GONE);
    }

    public void showInView(final String viewId) throws NullPointerException {
        LinearLayout layout = ((LinearLayout) activity.findViewById(
                activity.getResources().getIdentifier(viewId, "id", activity.getPackageName())));

        layout.addView(ad.getView());
    }
}
