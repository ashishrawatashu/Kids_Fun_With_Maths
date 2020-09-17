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
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AdditionActivity extends FragmentActivity implements View.OnClickListener {

    ImageView menuIV;
    RecyclerView leftSideRV, rightSideRV;
    LeftSideModel[] leftSideData;
    RightSideModel[] rightSideData;
    int leftSideSize, rightSideSize;
    public int selectedAns = 0;
    public int total = 0;
    Random rnum1;
    Random rnum2;
    Random roption1;
    Random roption2;
    int min1 = 1;
    int max1 = 5;
    int min2 = 5;
    int max2 = 10;
    Button option1, option2, option3, option4;
    int num1, num2, option1val, rval1, rval2;
    int option2val;
    int option3val;
    int option4val;
    List<Integer> objects = new ArrayList<Integer>();
    public int rightAns = 0;
    public int wrongAns = 0;
    AdView adView;
    TextToSpeech textToSpeech;
    TextView firstNumberTV, ansNumberTV, secondNumberTV, leftsideTV, rightSideTV;
    int click=0;
    String coorectAns[] ={
            "awesome",
            "Wonderfull",
            "brilliant",
            "Perfect",
            "Very Good",
            "bravo",
            "excellent"

    };
    MediaPlayer mediaPlayer, noMediaPlayer;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_additionactivity);
        MobileAds.initialize(this, String.valueOf(R.string.app_id));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        noMediaPlayer= MediaPlayer.create(this,R.raw.no);
        startService(new Intent(this, BackgroundSound.class));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ads_unit_Id));

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        textToSpeeech();
        init();
        bannerAdd();

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
        leftsideTV = findViewById(R.id.leftsideTV);
        rightSideTV = findViewById(R.id.rightSideTV);
        firstNumberTV = findViewById(R.id.firstNumberTV);
        ansNumberTV = findViewById(R.id.ansNumberTV);
        secondNumberTV =findViewById(R.id.secondNumberTV);
        leftSideRV = findViewById(R.id.leftSideRV);
        rightSideRV = findViewById(R.id.rightSideRV);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        menuIV = findViewById(R.id.menuIV);
        menuIV.setOnClickListener(this);
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

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

    }

    private void textToSpeeech() {
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
    }

    private void genrateQuestion() {
        ansNumberTV.setVisibility(View.INVISIBLE);
        leftSideList();
        rightSideList();
        setOption();
        textToSpeech.setPitch(0.9f);
        textToSpeech.setSpeechRate(0.7f);
        textToSpeech.speak((leftSideSize)+" "+"+"+" "+rightSideSize, TextToSpeech.QUEUE_FLUSH, null);
    }


    private void rightSideList() {
        if (selectedAns<5){
            Random one = new Random();
            int rightRandomSize = one.nextInt(5);
            rightSideSize = (rightRandomSize+1);
        }else if (selectedAns>5){
            Random one = new Random();
            int rightRandomSize = one.nextInt(9);
            rightSideSize = (rightRandomSize+1);
        }

        Log.e("random1", String.valueOf(rightSideSize));
        secondNumberTV.setText(String.valueOf(rightSideSize));
        rightSideTV.setText((String.valueOf(rightSideSize)));

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
        leftSideRV.setNestedScrollingEnabled(true);
        rightSideRV.setLayoutManager(new GridLayoutManager(this, 3));
        rightSideRV.setAdapter(adapter);


    }
    private void leftSideList() {

        if (selectedAns<5){
            Random one = new Random();
            int leftRandomSize = one.nextInt(5);
            leftSideSize = (leftRandomSize+1);
        }else if (selectedAns>5){
            Random one = new Random();
            int leftRandomSize = one.nextInt(9);
            leftSideSize = (leftRandomSize+1);
        }

        Log.e("random1", String.valueOf(leftSideSize));
        firstNumberTV.setText(String.valueOf(leftSideSize));
        leftsideTV.setText(String.valueOf(leftSideSize));
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
        leftSideRV.setNestedScrollingEnabled(false);

        leftSideRV.setLayoutManager(new GridLayoutManager(this, 3));
        leftSideRV.setAdapter(adapter);

    }


    private void setOption() {
        rnum1 = new Random();
        rnum2 = new Random();
        roption1 = new Random();
        roption2 = new Random();
        rval1 = roption1.nextInt((max1 - min1) + 1) + min1;
        rval2 = roption2.nextInt((max2 - min2) + 1) + min2;

        option1val = leftSideSize + rightSideSize;
        option2val = option1val + rval1;
        option3val = option1val + 2;
        option4val = option1val + rval2;

        ansNumberTV.setText(String.valueOf(option1val));

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

        switch (v.getId()){

            case R.id.option1:

                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }
                if (option1.getText().equals(Integer.toString(option1val))) {
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


                } else {
                    errorMsg();
                    wrongAns++;
                }


                break;

            case R.id.option2:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }
                if (option2.getText().equals(Integer.toString(option1val))) {

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
                        afterAdd();
                        click++ ;

                    }

                } else {
                    errorMsg();

                    wrongAns++;
                }

                break;


            case R.id.option3:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }
                if (option3.getText().equals(Integer.toString(option1val))) {
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

                        }else if (!mInterstitialAd.isLoaded()) {
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            afterAdd();
                        }
                    }else{
                        click++ ;
                        afterAdd();
                    }
                } else {
                    errorMsg();

                    wrongAns++;
                }

                break;

            case R.id.option4:
                selectedAns++;
                if (selectedAns == 1) {
                    total = 1;
                }
                if (option4.getText().equals(Integer.toString(option1val))) {
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

                        }else if (!mInterstitialAd.isLoaded()) {
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            afterAdd();
                        }
                    }else{
                        click++ ;
                        afterAdd();
                    }
                } else {
                  errorMsg();
                    wrongAns++;
                }

                break;

            case R.id.menuIV:
                mediaPlayer.start();
                onBackPressed();
                break;

        }



    }

    private void errorMsg() {
        noMediaPlayer.start();
//        int randomWarning = new Random().nextInt(warning.length);
//        String warningName= warning[randomWarning];
//        Log.e("fruits",warningName);
//
//        textToSpeech.setPitch(0.9f);
//        textToSpeech.setSpeechRate(0.7f);
//        textToSpeech.speak(option4.getText()+"  "+warningName, TextToSpeech.QUEUE_FLUSH, null);
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

    public void afterAdd(){
        ansNumberTV.setVisibility(View.VISIBLE);
        mediaPlayer.start();
        int randomCorrect = new Random().nextInt(coorectAns.length);
        String correctName= coorectAns[randomCorrect];
        Log.e("fruits",correctName);
        textToSpeech.setSpeechRate(0.7f);
        textToSpeech.setPitch(0.9f);
        textToSpeech.speak(String.valueOf(option1val)+" "+correctName, TextToSpeech.QUEUE_FLUSH, null);
        takeTimeToNextQuestion();
        rightAns++;
    }
}
