Android Ads Manager
===================
# Описание
Управление рекламой в вашем android приложении. Вы можете менять позиции отображения рекламы и ее тип.

Демо версия [Ads Manager](https://play.google.com/store/apps/details?id=ru.chinaprices.ads.app)

Используется в проекте [Цены в Китае](https://play.google.com/store/apps/details?id=ru.chinaprices)

Доступны следующие позиции:
- Снизу
- Сверху
- В списке (с заданным шагом)
- Внутри указанного элемента (указываем id элемента)
- Межстраничная реклама (на весь экран)

Поддерживаемые типы рекламы:
- AdMob
- StartAd.mobi
- Свой (в виде html)

Min sdk version - 8 (android 2.2)

Так как приходится постоянно экспериментировать, добавлена возможость хранить настройки на стороне сервера. Это очень удобно, можно поменять позицию/тип рекламы не заставляя пользователей обновлять приложение.

# Примеры использования

## AdMob снизу
    AdMob ad = new AdMob(activity, adMobId);
    AdManager manager = new AdManager(ad);
    manager.show(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

<img src="http://chinaprices.ru/images/adsmanager/1.png" width="300px" />

## AdMob smart
Если нужно растянуть на всю ширину экрана

    adMob.setAdSize(AdSize.SMART_BANNER);

<img src="http://chinaprices.ru/images/adsmanager/2.png" width="300px" />

## Свой баннер сверху
    AdCustom ad = new AdCustom(activity, html);
    AdManager manager = new AdManager(ad);
    manager.show(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

<img src="http://chinaprices.ru/images/adsmanager/3.png" width="300px" />

## В списке с шагом 15
    AdListAdapter adapter = new AdListAdapter(oldAdapter);

    AdMob ad = new AdMob(activity, adMobId);
    adapter.setStep(15);
    adapter.setAd(ad);
    adapter.notifyDataSetChanged();

<img src="http://chinaprices.ru/images/adsmanager/4.png" width="300px" />

## В конкретном месте
    <LinearLayout
        android:id="@+id/inline_banner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical" />

    AdStartAd ad = new AdStartAd(activity, startadId);
    AdManager manager = new AdManager(ad);
    manager.showInView("inline_banner");

<img src="http://chinaprices.ru/images/adsmanager/5.png" width="300px" />

## Межстраничный баннер
    AdMobInterstitialAd ad = new AdMobInterstitialAd(activity, admobId);
    ad.show();

<img src="http://chinaprices.ru/images/adsmanager/6.png" width="300px" />

## Настройки на стороне сервера
    new AdsLoadTask(getActivity(), sectionNumber, adapter, "android").execute();

 - http://chinaprices.ru/api/test_ads_settings.php?apikey=testads&section=1
 - http://chinaprices.ru/api/test_ads_settings.php?apikey=testads&section=2
 - http://chinaprices.ru/api/test_ads_settings.php?apikey=testads&section=3
 - http://chinaprices.ru/api/test_ads_settings.php?apikey=testads&section=4
 - http://chinaprices.ru/api/test_ads_settings.php?apikey=testads&section=5
 - http://chinaprices.ru/api/test_ads_settings.php?apikey=testads&section=6

# Подключение к проекту
На текущий момент это не библиотека, а обычное приложение. Если Вы хотите использовать это в своем приложении нужно сделать следующее:
 - перенести библиотеки из папки libs
 На текущий момент там только одна - StartADLib-1.0.1.jar

 - обновить зависимости в build.gradle
    ```gradle
    compile 'com.google.android.gms:play-services:4.0.30'
    compile files('libs/StartADLib-1.0.1.jar')
    compile 'com.google.code.gson:gson:2.2.4@jar'

 - изменить AndroidManifest.xml
```xml
<meta-data android:name="email" android:value="you@email" />
<meta-data android:name="trackingId" android:value="yourid" />
<meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version" />
<activity android:name="com.google.android.gms.ads.AdActivity" android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize" />


