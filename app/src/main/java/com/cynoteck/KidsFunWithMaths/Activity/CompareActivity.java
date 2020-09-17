package com.cynoteck.KidsFunWithMaths.Activity;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.cynoteck.KidsFunWithMaths.Adapter.LeftSideAdapter;
import com.cynoteck.KidsFunWithMaths.Adapter.RightSideAdpter;
import com.cynoteck.KidsFunWithMaths.BackgroundSound;
import com.cynoteck.KidsFunWithMaths.Models.LeftSideModel;
import com.cynoteck.KidsFunWithMaths.Models.RightSideModel;
import com.cynoteck.KidsFunWithMaths.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;
import java.util.Random;

public class CompareActivity extends FragmentActivity implements View.OnClickListener {


    RecyclerView leftSideRV, rightSideRV;
    ImageView menuIV;
    LeftSideModel[] leftSideData;
    RightSideModel[] rightSideData;
    int leftSideSize, rightSideSize;
    public int selectedAns = 0;
    public int total = 0;
    Button greaterBT, smallerBT, equalBT;
    InterstitialAd mInterstitialAd;
    int click=0;
    MediaPlayer mediaPlayer;
    Button ansBT;
    AdView adView;
   TextToSpeech textToSpeech;

    String warning[] ={
            "Try Again",
            "No",
            "Its Wrong",
            "Be carefull"

    };
    String coorectAns[] ={
            "awesome",
            "Wonderfull",
            "brilliant",
            "Perfect",
            "Very Good",
            "bravo",
            "excellent"

    };

    MediaPlayer  noMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_compare);
        MobileAds.initialize(this, String.valueOf(R.string.app_id));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        noMediaPlayer = MediaPlayer.create(this, R.raw.no);
        startService(new Intent(this, BackgroundSound.class));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ads_unit_Id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }

            }
        });
        init();

        bannerAdd();

        leftSideData = new LeftSideModel[]{
                new LeftSideModel("basketball",R.drawable.basketball),
                new LeftSideModel("banana",R.drawable.banana),
                new LeftSideModel("brookle",R.drawable.brookle),
                new LeftSideModel("icecream", R.drawable.icecream),
                new LeftSideModel("brookle", R.drawable.brookle),
                new LeftSideModel("football",R.drawable.football),
                new LeftSideModel("orange", R.drawable.orange),
                new LeftSideModel("pineapple",R.drawable.pineapple),
                new LeftSideModel("tamatoo", R.drawable.tamatoo),
                new LeftSideModel("cherry",R.drawable.cherry),
                new LeftSideModel("car",R.drawable.car),
                new LeftSideModel("corn", R.drawable.corn),
                new LeftSideModel("bringl", R.drawable.bringl),
        };
        rightSideData = new RightSideModel[]{
                new RightSideModel("basketball",R.drawable.basketball),
                new RightSideModel("banana",R.drawable.banana),
                new RightSideModel("brookle",R.drawable.brookle),
                new RightSideModel("icecream", R.drawable.icecream),
                new RightSideModel("brookle", R.drawable.brookle),
                new RightSideModel("football",R.drawable.football),
                new RightSideModel("orange", R.drawable.orange),
                new RightSideModel("pineapple",R.drawable.pineapple),
                new RightSideModel("tamatoo", R.drawable.tamatoo),
                new RightSideModel("cherry",R.drawable.cherry),
                new RightSideModel("car",R.drawable.car),
                new RightSideModel("corn", R.drawable.corn),
                new RightSideModel("bringl", R.drawable.bringl),
        };

        takeTimeToNextQuestion();

    }



    private void bannerAdd() {
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(String.valueOf(AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE))
                .build();
        adView.loadAd(adRequest);
    }

    private void init() {

        leftSideRV = findViewById(R.id.leftSideRV);
        rightSideRV = findViewById(R.id.rightSideRV);
        greaterBT = findViewById(R.id.greaterBT);
        smallerBT = findViewById(R.id.smallerBT);
        equalBT = findViewById(R.id.equalBT);
        ansBT = findViewById(R.id.ansBT);
        menuIV = findViewById(R.id.menuIV);

        menuIV.setOnClickListener(this);
        greaterBT.setOnClickListener(this);
        smallerBT.setOnClickListener(this);
        equalBT.setOnClickListener(this);
    }

    private void genrateQuestion() {
        ansBT.setText("");
        leftSideList();
        rightSideList();
    }

    private void rightSideList() {
        Random one = new Random();
        int rightRandomSize = one.nextInt(9);
        rightSideSize = (rightRandomSize+1);
        Log.e("random1", String.valueOf(rightSideSize));

        String rightSideItems[] ={
                "banana",
                "brookle",
                "icecream",
                "basketball",
                "football",
                "orange",
                "pineapple",
                "tamatoo",
                "cherry",
                "car",
                "corn",
                "bringl",
        };


        int rightSideRandomItems = new Random().nextInt(rightSideItems.length);
        String rightSideItemsName= rightSideItems[rightSideRandomItems];
        Log.e("fruits",rightSideItemsName);

        RightSideAdpter adapter = new RightSideAdpter(rightSideData,rightSideSize,rightSideItemsName);
        rightSideRV.setHasFixedSize(true);
        rightSideRV.setLayoutManager(new GridLayoutManager(this, 3));
        rightSideRV.setAdapter(adapter);


    }

    private void leftSideList() {
        Random one = new Random();
        int leftRandomSize = one.nextInt(9);
        leftSideSize = (leftRandomSize+1);
        Log.e("random1", String.valueOf(leftSideSize));

        String leftSideItems[] ={
                "banana",
                "brookle",
                "icecream",
                "basketball",
                "football",
                "orange",
                "pineapple",
                "tamatoo",
                "cherry",
                "car",
                "corn",
                "bringl",
        };


        int leftSideRandomItems = new Random().nextInt(leftSideItems.length);
        String leftSideItemsName= leftSideItems[leftSideRandomItems];
        Log.e("fruits",leftSideItemsName);

        LeftSideAdapter adapter = new LeftSideAdapter(leftSideData,leftSideSize,leftSideItemsName);
        leftSideRV.setHasFixedSize(true);
        leftSideRV.setLayoutManager(new GridLayoutManager(this, 3));
        leftSideRV.setAdapter(adapter);




    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.greaterBT:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }
                mediaPlayer.start();

                if (rightSideSize>leftSideSize){
                    ansBT.setText("<");
                    if(click == 4){
                        if (mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                            click = 0;
                            mInterstitialAd.setAdListener(new AdListener() {
                                public void onAdLoaded() {

                                }
                                @Override
                                public void onAdClosed() {
                                    afterAdd();
                                }
                            });

                        }  else if (!mInterstitialAd.isLoaded()) {
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            afterAdd();
                        }
                    }else{
                        click++ ;
                        afterAdd();
                    }

                }else {
                    errorMsg();
                }
                break;

            case R.id.smallerBT:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }
                mediaPlayer.start();
                if (rightSideSize<leftSideSize){
                    ansBT.setText(">");
                    if(click == 4){
                        if (mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                            click = 0;
                            mInterstitialAd.setAdListener(new AdListener() {
                                public void onAdLoaded() {

                                }
                                @Override
                                public void onAdClosed() {
                                    afterAdd();
                                }
                            });

                        }  else if (!mInterstitialAd.isLoaded()) {
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            afterAdd();
                        }
                    }else{
                        click++ ;
                        afterAdd();
                    }
                }else {
                    errorMsg();
                }
                break;

            case R.id.equalBT:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }
                mediaPlayer.start();
                if (rightSideSize==leftSideSize){
                    ansBT.setText("=");
                    if(click == 4){
                        if (mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                            click = 0;
                            mInterstitialAd.setAdListener(new AdListener() {
                                public void onAdLoaded() {

                                }
                                @Override
                                public void onAdClosed() {
                                    afterAdd();
                                }
                            });

                        }  else if (!mInterstitialAd.isLoaded()) {
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            afterAdd();
                        }
                    }else{
                        click++ ;
                        afterAdd();
                    }
                }else {
                    errorMsg();


                }
                break;

            case R.id.menuIV:
                mediaPlayer.start();
                onBackPressed();
                break;

        }

    }

    private void afterAdd() {
        int randomCorrect = new Random().nextInt(coorectAns.length);
        String correctName= coorectAns[randomCorrect];
        Log.e("fruits",correctName);
        textToSpeech.setSpeechRate(0.5f);
        textToSpeech.setPitch(0.9f);
        textToSpeech.speak(correctName, TextToSpeech.QUEUE_FLUSH, null);

        takeTimeToNextQuestion();
    }

    private void errorMsg() {
        noMediaPlayer.start();
//        int randomWarning = new Random().nextInt(warning.length);
//        String warningName= warning[randomWarning];
//        Log.e("fruits",warningName);
//        textToSpeech.setPitch(0.9f);
//        textToSpeech.setSpeechRate(0.5f);
//        textToSpeech.speak(warningName, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void takeTimeToNextQuestion() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                genrateQuestion();

            }
        },2000);
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
