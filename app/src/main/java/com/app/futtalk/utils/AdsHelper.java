package com.app.futtalk.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.app.futtalk.R;
import com.app.futtalk.activties.MainActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class AdsHelper {
    public interface AdWatchListener {
        void onAdWatched();
    }
    public static boolean isPremiumUser = true;
    private static InterstitialAd mInterstitialAd;
    private static Context mContext;
    private static AdsHelper adsHelper;

    private RewardedInterstitialAd rewardedInterstitialAd;

    private AdsHelper(Context context) {
        mContext = context;
        loadInterstitialAd(context);
        loadRewardedInterstitialAd(context);
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

    private void loadRewardedInterstitialAd(Context context) {
        RewardedInterstitialAd.load(context, "ca-app-pub-3940256099942544/5354046379",
                new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        rewardedInterstitialAd = ad;
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        rewardedInterstitialAd = null;
                    }
                });
    }

    public void showRewardedInterstitialAd(Activity activity, AdWatchListener adWatchListener) {
        if (!isPremiumUser) {
            if (rewardedInterstitialAd != null) {
                rewardedInterstitialAd.show(activity, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        adWatchListener.onAdWatched();
                    }
                });
                loadRewardedInterstitialAd(mContext);
            } else {
                loadRewardedInterstitialAd(mContext);
                adWatchListener.onAdWatched();
            }
        } else {
            adWatchListener.onAdWatched();
        }
    }


    public void showInterstitialAd(Activity activity) {
        if (!isPremiumUser) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(activity);
                loadInterstitialAd(mContext);
            } else {
                loadInterstitialAd(mContext);
            }
        }
    }


    public void showBannerAd(Context context, View parent) {
        if (!isPremiumUser) {
            LinearLayout linearLayout = parent.findViewById(R.id.banner_ad_container);
            AdView adView = new AdView(context);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(context.getResources().getString(R.string.banner_ad_unit_id));
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    linearLayout.addView(adView);
                }
            });
        }
    }


}
