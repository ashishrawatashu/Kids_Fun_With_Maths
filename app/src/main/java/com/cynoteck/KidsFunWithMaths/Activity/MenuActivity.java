package com.cynoteck.KidsFunWithMaths.Activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cynoteck.KidsFunWithMaths.BackgroundSound;
import com.cynoteck.KidsFunWithMaths.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MenuActivity extends FragmentActivity implements View.OnClickListener {
    RelativeLayout countBT, comapreBT, addBT, subBT, arrangeBT;
    AdView adView;
    MediaPlayer mediaPlayer;
    InterstitialAd mInterstitialAd;
    ImageView homeIV;
    BackgroundSound backgroundSound = new BackgroundSound();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        MobileAds.initialize(this, String.valueOf(R.string.app_id));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        interStitialAd();

        startService(new Intent(MenuActivity.this, BackgroundSound.class));


        init();

        bannerAdd();


    }

    private void interStitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ads_unit_Id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    private void init() {

        homeIV = findViewById(R.id.homeIV);
        adView = findViewById(R.id.adView);
        countBT = findViewById(R.id.countBT);
        comapreBT = findViewById(R.id.compareBT);
        addBT = findViewById(R.id.addBT);
        subBT = findViewById(R.id.subBT);
        arrangeBT = findViewById(R.id.arrangeBT);
        arrangeBT.setOnClickListener(this);
        countBT.setOnClickListener(this);
        comapreBT.setOnClickListener(this);
        addBT.setOnClickListener(this);
        subBT.setOnClickListener(this);
        homeIV.setOnClickListener(this);

    }

    private void bannerAdd() {

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(String.valueOf(AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE))
                .build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.countBT:
                mediaPlayer.start();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {

                        }
                        @Override
                        public void onAdClosed() {

                            Intent intent = new Intent(MenuActivity.this, CountActivity.class);
                            startActivity(intent);

                        }
                    });
                }else {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    Intent intent = new Intent(MenuActivity.this, CountActivity.class);
                    startActivity(intent);
                }



                break;

            case R.id.compareBT:

                mediaPlayer.start();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {

                        }
                        @Override
                        public void onAdClosed() {

                            Intent intent1 = new Intent(MenuActivity.this, CompareActivity.class);
                            startActivity(intent1);

                        }
                    });
                }else {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    Intent intent1 = new Intent(MenuActivity.this, CompareActivity.class);
                    startActivity(intent1);

                }



                break;

            case R.id.addBT:
                mediaPlayer.start();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {

                        }
                        @Override
                        public void onAdClosed() {

                            Intent add = new Intent(MenuActivity.this, AdditionActivity.class);
                            startActivity(add);

                        }
                    });

                }else{
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    Intent add = new Intent(MenuActivity.this, AdditionActivity.class);
                    startActivity(add);

                }

                break;

            case R.id.arrangeBT:
                mediaPlayer.start();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {

                        }
                        @Override
                        public void onAdClosed() {

                            Intent arrange = new Intent(MenuActivity.this, ArrangementActivity.class);
                            startActivity(arrange);

                        }
                    });
                }else{
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    Intent arrange = new Intent(MenuActivity.this, ArrangementActivity.class);
                    startActivity(arrange);
                }


                break;

            case R.id.subBT:
                mediaPlayer.start();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {

                        }
                        @Override
                        public void onAdClosed() {

                            Intent sub  = new Intent(MenuActivity.this, SubtractionActivity.class);
                            startActivity(sub);
                        }
                    });
                }else {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    Intent sub  = new Intent(MenuActivity.this, SubtractionActivity.class);
                    startActivity(sub);
                }


                break;

            case R.id.homeIV:
                mediaPlayer.start();
                onBackPressed();
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, BackgroundSound.class));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, BackgroundSound.class));
    }

}
