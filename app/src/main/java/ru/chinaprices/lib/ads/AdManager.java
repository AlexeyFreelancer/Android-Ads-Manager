package ru.chinaprices.lib.ads;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import ru.chinaprices.lib.ads.adapter.AdListener;

public class AdManager {
    private AdInterface ad;
    private Activity activity;
    private FrameLayout adLayout;
    private AdListener adListener;


    public AdManager(AdInterface ad) {
        this.ad = ad;
        activity = ad.getActivity();
    }

    public void setAdListener(AdListener adListener) {
        this.adListener = adListener;
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

        if (adListener != null) {
            adView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    adListener.onClick(ad);
                    return false;
                }
            });
        }

        adView.setId(adViewId);
        activity.addContentView(adLayout, params);
        adLayout.addView(adView);
    }

    public void hide() {
        adLayout.setVisibility(View.GONE);
    }

    public void showInView(final String viewId) throws NullPointerException {
        View adView = ad.getView();

        LinearLayout layout = ((LinearLayout) activity.findViewById(
                activity.getResources().getIdentifier(viewId, "id", activity.getPackageName())));

        if (adListener != null) {
            adView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    adListener.onClick(ad);
                    return false;
                }
            });
        }

        layout.addView(adView);
    }
}
