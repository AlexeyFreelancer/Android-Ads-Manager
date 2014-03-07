package ru.chinaprices.lib.ads;

import android.app.Activity;
import android.view.View;

public interface AdInterface {
    public boolean isActive();

    public View getView();

    public Activity getActivity();
}