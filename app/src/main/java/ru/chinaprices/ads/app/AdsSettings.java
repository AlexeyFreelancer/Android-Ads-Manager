package ru.chinaprices.ads.app;

import com.google.gson.annotations.SerializedName;

public class AdsSettings {

    @SerializedName("list")
    private boolean list;

    @SerializedName("top")
    private boolean top;

    @SerializedName("bottom")
    private boolean bottom;

    @SerializedName("smart")
    private boolean smart;

    @SerializedName("step")
    private int step;

    @SerializedName("html")
    private String html;

    @SerializedName("admobid")
    private String admobId;

    @SerializedName("admobInterstitialAdId")
    private String admobInterstitialAdId;

    @SerializedName("startadid")
    private String startAdId;

    @SerializedName("viewid")
    private String viewId;

    public boolean isList() {
        return list;
    }

    public boolean isTop() {
        return top;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getAdmobId() {
        return admobId;
    }

    public void setAdmobId(String admobId) {
        this.admobId = admobId;
    }

    public boolean isSmart() {
        return smart;
    }

    public void setSmart(boolean smart) {
        this.smart = smart;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getStartAdId() {
        return startAdId;
    }

    public String getViewId() {
        return viewId;
    }

    public void setStartAdId(String startAdId) {
        this.startAdId = startAdId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getAdmobInterstitialAdId() {
        return admobInterstitialAdId;
    }

    public void setAdmobInterstitialAdId(String admobInterstitialAdId) {
        this.admobInterstitialAdId = admobInterstitialAdId;
    }
}
