package com.cynoteck.KidsFunWithMaths.Activity;
import androidx.fragment.app.FragmentActivity;

import android.content.ClipData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cynoteck.KidsFunWithMaths.BackgroundSound;
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

public class ArrangementActivity extends FragmentActivity implements  View.OnTouchListener, View.OnClickListener {

    MediaPlayer mediaPlayer, nomediaPlayer;
    ImageView menuIV;
    List<Integer> arrange = new ArrayList<Integer>();
    List<Integer> optionArrange = new ArrayList<Integer>();
    List<Integer> ansarray = new ArrayList<Integer>();

    public int selectedAns = 0;
    public int total = 0;
    TextView option1, option2, option3, option4;
    TextView option1TV, option2TV, option3TV, option4TV, orderTV;
    int num1, num2, num3, num4;
    int click=0;

    LinearLayout target1, target2, target3, target4;
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
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_arrangement);
        MobileAds.initialize(this, String.valueOf(R.string.app_id));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mInterstitialAd = new InterstitialAd(this);
        startService(new Intent(this, BackgroundSound.class));
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ads_unit_Id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        nomediaPlayer = MediaPlayer.create(this, R.raw.no);

        textToSpeeech();

        init();

        bannerAdd();


        takeTimeToNextQuestion();

        target1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DROP:
                        String clipData = event.getClipDescription().getLabel().toString();
                        Log.e("clipdata", clipData);
                        if (option1TV.getText().equals(clipData)) {
                            correctAns();
                            option1TV.setVisibility(View.VISIBLE);
                            ansarray.add(Integer.valueOf(clipData));
                            if (ansarray.size()==4){
                                if(click == 4){
                                    if (mInterstitialAd.isLoaded()){
                                        mInterstitialAd.show();
                                        click = 0;
                                        mInterstitialAd.setAdListener(new AdListener() {
                                            public void onAdLoaded() {

                                            }
                                            @Override
                                            public void onAdClosed() {
                                                takeTimeToNextQuestion();

                                            }
                                        });

                                    }else if (!mInterstitialAd.isLoaded()) {
                                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    }
                                }else{
                                    takeTimeToNextQuestion();
                                    click++ ;

                                }
                            }


                        } else {

                            errotMsg();

                        }

                        return (true);
                    case DragEvent.ACTION_DRAG_ENDED: {

                        return (true);

                    }

                    default:
                        break;
                }
                return true;
            }
        });
        target2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();

                switch (action) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;

                    case DragEvent.ACTION_DROP:
                        String clipData1 = event.getClipDescription().getLabel().toString();
                        Log.e("clipdata", clipData1);
                        if (option2TV.getText().equals(clipData1)) {
                            correctAns();
                            option2TV.setVisibility(View.VISIBLE);
                            ansarray.add(Integer.valueOf(clipData1));
                            if (ansarray.size()==4){
                                if(click == 4){
                                    if (mInterstitialAd.isLoaded()){
                                        mInterstitialAd.show();
                                        click = 0;
                                        mInterstitialAd.setAdListener(new AdListener() {
                                            public void onAdLoaded() {

                                            }
                                            @Override
                                            public void onAdClosed() {
                                                takeTimeToNextQuestion();

                                            }
                                        });

                                    }
                                    else if (!mInterstitialAd.isLoaded()) {
                                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    }
                                }else{
                                    takeTimeToNextQuestion();
                                    click++ ;

                                }

                            }

                        } else {
                            errotMsg();
                        }

                        return (true);
                    case DragEvent.ACTION_DRAG_ENDED: {

                        return (true);

                    }

                    default:
                        break;
                }
                return true;
            }
        });
        target3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();
                switch (action) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;

                    case DragEvent.ACTION_DROP:
                        String clipData2 = event.getClipDescription().getLabel().toString();
                        Log.e("clipdata", clipData2);
                        if (option3TV.getText().equals(clipData2)) {
                            correctAns();
                            option3TV.setVisibility(View.VISIBLE);
                            ansarray.add(Integer.valueOf(clipData2));
                            if (ansarray.size()==4){
                                if(click == 4){
                                    if (mInterstitialAd.isLoaded()){
                                        mInterstitialAd.show();
                                        click = 0;
                                        mInterstitialAd.setAdListener(new AdListener() {
                                            public void onAdLoaded() {

                                            }
                                            @Override
                                            public void onAdClosed() {
                                                takeTimeToNextQuestion();

                                            }
                                        });

                                    }else if (!mInterstitialAd.isLoaded()) {
                                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    }
                                }else{
                                    takeTimeToNextQuestion();
                                    click++ ;

                                }
                            }


                        } else {
                            errotMsg();
                        }

                        return (true);
                    case DragEvent.ACTION_DRAG_ENDED: {

                        return (true);

                    }

                    default:
                        break;
                }
                return true;
            }
        });
        target4.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();


                switch (action) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;

                    case DragEvent.ACTION_DROP:
                        String clipData3 = event.getClipDescription().getLabel().toString();
                        Log.e("clipdata", clipData3);
                        if (option4TV.getText().equals(clipData3)) {
                            correctAns();
                            option4TV.setVisibility(View.VISIBLE);
                            ansarray.add(Integer.valueOf(clipData3));
                            if (ansarray.size()==4){
                                if(click == 4){
                                    if (mInterstitialAd.isLoaded()){
                                        mInterstitialAd.show();
                                        click = 0;
                                        mInterstitialAd.setAdListener(new AdListener() {
                                            public void onAdLoaded() {

                                            }
                                            @Override
                                            public void onAdClosed() {
                                                takeTimeToNextQuestion();

                                            }
                                        });

                                    }else if (!mInterstitialAd.isLoaded()) {
                                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    }
                                }else{
                                    takeTimeToNextQuestion();
                                    click++ ;

                                }
                            }

                        } else {
                            errotMsg();
                        }


                        return (true);
                    case DragEvent.ACTION_DRAG_ENDED: {

                        return (true);

                    }

                    default:
                        break;
                }
                return true;
            }
        });

    }

    private void afterAdd() {

    }

    private void correctAns() {
        mediaPlayer.start();
        int randomCorrect = new Random().nextInt(coorectAns.length);
        String correctName= coorectAns[randomCorrect];
        Log.e("fruits",correctName);
        textToSpeech.setSpeechRate(0.7f);
        textToSpeech.setPitch(0.9f);
        textToSpeech.speak(correctName, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void errotMsg() {
        nomediaPlayer.start();
//        int randomWarning = new Random().nextInt(warning.length);
//        String warningName= warning[randomWarning];
//        Log.e("fruits",warningName);
//        textToSpeech.setPitch(0.9f);
//        textToSpeech.setSpeechRate(0.7f);
//        textToSpeech.speak(warningName, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void init() {
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        option1TV = findViewById(R.id.option1TV);
        option2TV = findViewById(R.id.option2TV);
        option3TV = findViewById(R.id.option3TV);
        option4TV = findViewById(R.id.option4TV);
        target1 = findViewById(R.id.target1);
        target2 = findViewById(R.id.target2);
        target3 = findViewById(R.id.target3);
        target4 = findViewById(R.id.target4);
        orderTV= findViewById(R.id.order);
        menuIV = findViewById(R.id.menuIV);

        menuIV.setOnClickListener(this);
        option1.setOnTouchListener(this);
        option2.setOnTouchListener(this);
        option3.setOnTouchListener(this);
        option4.setOnTouchListener(this);


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

    private void bannerAdd() {
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(String.valueOf(AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE))
                .build();
        adView.loadAd(adRequest);
    }

    private void genrateQuestion() {

        String order[] ={"ASCENDING","DESCENDING","ASCENDING","DESCENDING","ASCENDING","DESCENDING","ASCENDING","DESCENDING","ASCENDING","DESCENDING"};
        textToSpeech.setPitch(0.9f);
        textToSpeech.setSpeechRate(0.7f);
        int randomItems = new Random().nextInt(order.length);
        String orderItemsName= order[randomItems];
        Log.e("order",orderItemsName);

        textToSpeech.speak("Arrange in"+orderItemsName+" "+"Order", TextToSpeech.QUEUE_FLUSH, null);

        ansarray.clear();
        optionArrange.clear();
        arrange.clear();

        Random rone = new Random();
        int onenumber = rone.nextInt(15);
        num1 = (onenumber);

        Random rtwo = new Random();
        int twoNumber = rtwo.nextInt(15);
        num2 = (twoNumber);

        Random rthree = new Random();
        int threenumber = rthree.nextInt(15);
        num3 = (threenumber);

        Random rfour = new Random();
        int fournumber = rfour.nextInt(15);
        num4 = (fournumber);


        optionArrange.add(num1);
        optionArrange.add(num2);
        optionArrange.add(num3);
        optionArrange.add(num4);

        arrange.add(num1);
        arrange.add(num2);
        arrange.add(num3);
        arrange.add(num4);

        Collections.shuffle(arrange);
        Collections.shuffle(optionArrange);
        Log.e("Optionarrange", String.valueOf(optionArrange));


        option1.setText(Integer.toString(optionArrange.get(0)));
        option2.setText(Integer.toString(optionArrange.get(1)));
        option3.setText(Integer.toString(optionArrange.get(2)));
        option4.setText(Integer.toString(optionArrange.get(3)));


        for (int i = 0; i < 4 - 1; i++) {
            for (int j = i + 1; j < arrange.size(); j++) {
                if (arrange.get(i) > arrange.get(j)) {
                    int temp = arrange.get(i);
                    arrange.set(i, arrange.get(j));
                    arrange.set(j, temp);
                }
            }
        }
        Log.e("arrange", String.valueOf(arrange));

        if (orderItemsName.equals("ASCENDING")){

            orderTV.setText("Arrange in Ascending");

            option1TV.setText(Integer.toString(arrange.get(0)));
            option2TV.setText(Integer.toString(arrange.get(1)));
            option3TV.setText(Integer.toString(arrange.get(2)));
            option4TV.setText(Integer.toString(arrange.get(3)));

            option1TV.setVisibility(View.INVISIBLE);
            option2TV.setVisibility(View.INVISIBLE);
            option3TV.setVisibility(View.INVISIBLE);
            option4TV.setVisibility(View.INVISIBLE);

        }if (orderItemsName.equals("DESCENDING")){

            orderTV.setText("Arrange in Descending");

            option1TV.setText(Integer.toString(arrange.get(3)));
            option2TV.setText(Integer.toString(arrange.get(2)));
            option3TV.setText(Integer.toString(arrange.get(1)));
            option4TV.setText(Integer.toString(arrange.get(0)));

            option1TV.setVisibility(View.INVISIBLE);
            option2TV.setVisibility(View.INVISIBLE);
            option3TV.setVisibility(View.INVISIBLE);
            option4TV.setVisibility(View.INVISIBLE);


        }






    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        View.DragShadowBuilder mShadow = new View.DragShadowBuilder(v);
        ClipData data1 = ClipData.newPlainText(Integer.toString(optionArrange.get(0)), option1.getText().toString());
        ClipData data2 = ClipData.newPlainText(Integer.toString(optionArrange.get(1)), option2.getText().toString());
        ClipData data3 = ClipData.newPlainText(Integer.toString(optionArrange.get(2)), option3.getText().toString());
        ClipData data4 = ClipData.newPlainText(Integer.toString(optionArrange.get(3)), option4.getText().toString());
        switch (v.getId()) {
            case R.id.option1:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data1, mShadow, null, 0);
                } else {
                    v.startDrag(data1, mShadow, null, 0);
                }

                break;
            case R.id.option2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data2, mShadow, null, 0);
                } else {
                    v.startDrag(data2, mShadow, null, 0);
                }
                break;

            case R.id.option3:


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data3, mShadow, null, 0);
                } else {
                    v.startDrag(data3, mShadow, null, 0);
                }

                break;
            case R.id.option4:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data4, mShadow, null, 0);
                } else {
                    v.startDrag(data4, mShadow, null, 0);
                }
                break;

        }

        return true;
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
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.menuIV:
                onBackPressed();
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, BackgroundSound.class));

    }
}
