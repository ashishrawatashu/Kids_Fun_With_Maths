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

import com.cynoteck.KidsFunWithMaths.Adapter.CountAdapter;
import com.cynoteck.KidsFunWithMaths.BackgroundSound;
import com.cynoteck.KidsFunWithMaths.Models.CountModel;
import com.cynoteck.KidsFunWithMaths.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CountActivity extends FragmentActivity implements View.OnClickListener {
    AdView adView;
    TextToSpeech textToSpeech;
    MediaPlayer mediaPlayer, noMediaPlayer;
    ImageView menuIV;
    InterstitialAd mInterstitialAd;
    int click=0;
    Button option1, option2, option3,option4;
    RecyclerView itemsListRV;
    Random rnum1;
    Random rnum2;
    Random roption1;
    Random roption2;
    int num1, num2, rval1, rval2;
    int min1 = 1;
    int max1 = 5;
    int min2 = 5;
    int max2 = 10;
    List<Integer> objects = new ArrayList<Integer>();
    int option1val;
    int option2val;
    int option3val;
    int option4val;
    int totalSize;
    public int selectedAns = 0;
    public int total = 0;
    CountModel[] myListData;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        MobileAds.initialize(this, String.valueOf(R.string.app_id));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        noMediaPlayer= MediaPlayer.create(this,R.raw.no);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ads_unit_Id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        startService(new Intent(this, BackgroundSound.class));

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



        myListData = new CountModel[]{

                new CountModel("basketball",R.drawable.basketball),
                new CountModel("banana",R.drawable.banana),
                new CountModel("brookle",R.drawable.brookle),
                new CountModel("icecream", R.drawable.icecream),
                new CountModel("brookle", R.drawable.brookle),
                new CountModel("football",R.drawable.football),
                new CountModel("orange", R.drawable.orange),
                new CountModel("pineapple",R.drawable.pineapple),
                new CountModel("tamatoo", R.drawable.tamatoo),
                new CountModel("cherry",R.drawable.cherry),
                new CountModel("car",R.drawable.car),
                new CountModel("corn", R.drawable.corn),
                new CountModel("bringl", R.drawable.bringl),

        };

        genrateQuestion();






    }

    private void bannerAdd() {
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(String.valueOf(AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE))
                .build();
        adView.loadAd(adRequest);
    }

    private void init() {

        itemsListRV = findViewById(R.id.itemsListRV);
        option1= findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        menuIV = findViewById(R.id.menuIV);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
        menuIV.setOnClickListener(this);
    }

    private void genrateQuestion() {

        Random one = new Random();
        int randomSize = one.nextInt(12-6)+6;
        totalSize = (randomSize+1);
        Log.e("random1", String.valueOf(totalSize));

        String fruits[] ={
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

        int randomItems = new Random().nextInt(fruits.length);
        String itemsName= fruits[randomItems];
        Log.e("fruits",itemsName);

        if (totalSize>14){
            CountAdapter adapter = new CountAdapter(myListData,totalSize,itemsName);
            itemsListRV.setHasFixedSize(true);
            itemsListRV.setLayoutManager(new GridLayoutManager(this, 4));
            itemsListRV.setAdapter(adapter);

        }else if (totalSize<16){
            CountAdapter adapter = new CountAdapter(myListData,totalSize,itemsName);
            itemsListRV.setHasFixedSize(true);
            itemsListRV.setLayoutManager(new GridLayoutManager(this, 4));
            itemsListRV.setAdapter(adapter);
        }else if (totalSize<4){
            CountAdapter adapter = new CountAdapter(myListData,totalSize,itemsName);
            itemsListRV.setHasFixedSize(true);
            itemsListRV.setLayoutManager(new GridLayoutManager(this, 3));
            itemsListRV.setAdapter(adapter);
        }else if (totalSize==1){
            CountAdapter adapter = new CountAdapter(myListData,totalSize,itemsName);
            itemsListRV.setHasFixedSize(true);
            itemsListRV.setLayoutManager(new GridLayoutManager(this, 1));
            itemsListRV.setAdapter(adapter);
        }else if (totalSize==2){
            CountAdapter adapter = new CountAdapter(myListData,totalSize,itemsName);
            itemsListRV.setHasFixedSize(true);
            itemsListRV.setLayoutManager(new GridLayoutManager(this, 2));
            itemsListRV.setAdapter(adapter);
        }else if (totalSize==4){
            CountAdapter adapter = new CountAdapter(myListData,totalSize,itemsName);
            itemsListRV.setHasFixedSize(true);
            itemsListRV.setLayoutManager(new GridLayoutManager(this, 2));
            itemsListRV.setAdapter(adapter);
        }else {
            CountAdapter adapter = new CountAdapter(myListData,totalSize,itemsName);
            itemsListRV.setHasFixedSize(true);
            itemsListRV.setLayoutManager(new GridLayoutManager(this, 4));
            itemsListRV.setAdapter(adapter);
        }


        rnum1 = new Random();
        rnum2 = new Random();
        roption1 = new Random();
        roption2 = new Random();
        rval1 = roption1.nextInt((max1 - min1) + 1) + min1;
        rval2 = roption2.nextInt((max2 - min2) + 1) + min2;

        option1val = totalSize;
        option2val = option1val + rval1;
        option3val = option1val + 2;
        option4val = option1val + rval2;

        objects.clear();
        objects.add(option1val);
        objects.add(option2val);
        objects.add(option3val);
        objects.add(option4val);
        Collections.shuffle(objects);

        option1.setText(Integer.toString(objects.get(0)));
        option2.setText(Integer.toString(objects.get(1)));
        option3.setText(Integer.toString(objects.get(2)));
        option4.setText(Integer.toString(objects.get(3)));


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option1:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }

                if (option1.getText().equals(Integer.toString(option1val))) {
                    mediaPlayer.start();
                    if (click == 4) {
                        if (mInterstitialAd.isLoaded()) {
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

                        } else if (!mInterstitialAd.isLoaded()) {
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            afterAdd();
                        }
                    } else {
                        click++;
                        afterAdd();
                    }
                } else {
                    errorMsg();
//                    int randomWarning = new Random().nextInt(warning.length);
//                    String warningName= warning[randomWarning];
//                    Log.e("fruits",warningName);
//                    textToSpeech.setPitch(0.9f);
//                    textToSpeech.setSpeechRate(0.5f);
//                    textToSpeech.speak(option1.getText()+"  "+warningName, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;

            case R.id.option2:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }
                if (option2.getText().equals(Integer.toString(option1val))) {
                    mediaPlayer.start();
                    if (click == 4) {
                        if (mInterstitialAd.isLoaded()) {
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

                        } else if (!mInterstitialAd.isLoaded()) {
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            afterAdd();
                        }
                    } else {
                        click++;
                        afterAdd();
                    }
                } else {
                    errorMsg();
//                    int randomWarning = new Random().nextInt(warning.length);
//                    String warningName= warning[randomWarning];
//                    Log.e("fruits",warningName);
//                    textToSpeech.setPitch(0.9f);
//                    textToSpeech.setSpeechRate(0.5f);
//                    textToSpeech.speak(option2.getText()+"  "+warningName, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;

            case R.id.option3:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }

                if (option3.getText().equals(Integer.toString(option1val))) {

                    mediaPlayer.start();

                    if (click == 4) {
                        if (mInterstitialAd.isLoaded()) {
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

                        } else if (!mInterstitialAd.isLoaded()) {
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            afterAdd();
                        }
                    } else {
                        click++;
                        afterAdd();
                    }
                }else {
                    errorMsg();
//                    int randomWarning = new Random().nextInt(warning.length);
//                    String warningName= warning[randomWarning];
//                    Log.e("fruits",warningName);
//                    textToSpeech.setPitch(0.9f);
//                    textToSpeech.setSpeechRate(0.5f);
//                    textToSpeech.speak(option3.getText()+"  "+warningName, TextToSpeech.QUEUE_FLUSH, null);
                    }
                break;

            case  R.id.option4:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }

                if (option4.getText().equals(Integer.toString(option1val))) {
                    mediaPlayer.start();
                    if (click == 4) {
                        if (mInterstitialAd.isLoaded()) {
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

                        } else if (!mInterstitialAd.isLoaded()) {
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            afterAdd();
                        }
                    } else {
                        click++;
                        afterAdd();
                    }
                }else {
                    errorMsg();
//                    int randomWarning = new Random().nextInt(warning.length);
//                    String warningName= warning[randomWarning];
//                    Log.e("fruits",warningName);
//                    textToSpeech.setPitch(0.9f);
//                    textToSpeech.setSpeechRate(0.5f);
//                    textToSpeech.speak(option4.getText()+"  "+warningName, TextToSpeech.QUEUE_FLUSH, null);
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
        textToSpeech.setPitch(0.9f);
        textToSpeech.setSpeechRate(0.5f);
        textToSpeech.speak(String.valueOf(option1val)+" "+correctName, TextToSpeech.QUEUE_FLUSH, null);
        takeTimeToNextQuestion();
    }

    private void errorMsg() {
        noMediaPlayer.start();
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
