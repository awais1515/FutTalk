package com.app.futtalk.utils;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.app.futtalk.R;
import com.app.futtalk.activties.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdsHelper {
    private static InterstitialAd mInterstitialAd;
    private static Context mContext;
    private static AdsHelper adsHelper;

    private AdsHelper(Context context) {
        mContext = context;
        loadInterstitialAd(context);
    }

    public static void initAdsHelper(Context context) {
        adsHelper = new AdsHelper(context);
    }

    public static AdsHelper getInstance() {
        return adsHelper;
    }

    private void loadInterstitialAd(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context,context.getResources().getString(R.string.interstitial_ad_unit_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
    }


    public void showInterstitialAd(Activity activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
            loadInterstitialAd(mContext);
        } else {
            loadInterstitialAd(mContext);
        }
    }
}
