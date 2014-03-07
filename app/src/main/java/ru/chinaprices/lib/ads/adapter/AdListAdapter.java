package ru.chinaprices.lib.ads.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;

import com.google.android.gms.ads.AdView;

import ru.chinaprices.lib.ads.AdInterface;

public class AdListAdapter extends BaseAdapter {
    private final BaseAdapter delegate;
    private int step;
    private AdInterface ad;

    public AdListAdapter(BaseAdapter delegate, int step, AdInterface ad) {
        this(delegate, step);
        this.ad = ad;
    }

    public AdListAdapter(BaseAdapter delegate, int step) {
        this(delegate);
        this.step = step;
    }

    public AdListAdapter(BaseAdapter delegate) {
        this.delegate = delegate;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setAd(AdInterface ad) {
        this.ad = ad;
    }

    @Override
    public int getCount() {
        if (!isActive()) {
            return delegate.getCount();
        }

        return delegate.getCount() + (int) Math.floor(delegate.getCount() / step) + 1;
    }

    /**
     * Return null if an item is an ad.  Otherwise return the delegate item.
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        if (!isActive()) {
            return delegate.getItem(position);
        }

        if (isItemAnAd(position)) {
            return null;
        }

        return delegate.getItem(getOffsetPosition(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!isActive()) {
            return delegate.getView(position, convertView, parent);
        }

        if (!isItemAnAd(position)) {
            if (isAdView(convertView)) {
                convertView = null;
            }
            return delegate.getView(getOffsetPosition(position), convertView, parent);
        }

        if (convertView instanceof AdView) {
            return convertView;
        }

        return ad.getView();
    }

    @Override
    public int getViewTypeCount() {
        if (!isActive()) {
            return delegate.getViewTypeCount();
        }

        return delegate.getViewTypeCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (!isActive()) {
            return delegate.getItemViewType(position);
        }

        if (isItemAnAd(position)) {
            return delegate.getViewTypeCount();
        }

        return delegate.getItemViewType(getOffsetPosition(position));
    }

    @Override
    public boolean areAllItemsEnabled() {
        return !isActive();
    }

    @Override
    public boolean isEnabled(int position) {
        if (!isActive()) {
            return true;
        }

        return (!isItemAnAd(position)) && delegate.isEnabled(getOffsetPosition(position));
    }

    private boolean isActive() {
        if (step < 2) {
            // TODO Log
            return false;
        }

        if (ad == null) {
            // TODO Log
            return false;
        }

        return true;
    }

    private boolean isItemAnAd(int position) {
        return (position % step == 0);
    }

    private int getOffsetPosition(int position) {
        return position - (int) Math.ceil(position / step) - 1;
    }

    private boolean isAdView(View view) {
        // FIXME
        return (view instanceof AdView || view instanceof WebView);
    }
}
