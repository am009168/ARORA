package com.example.aorora;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.network.NetworkCalls;

public class SurveyPage extends AppCompatActivity implements OnClickListener {
    /*LinearLayout mood_desc_ll;
    LinearLayout mood_desc_stress;*/
    Context surveyPage;
    Animation move_to_animation;
    Animation move_from_animation;
    Button red_mood_button;
    Button darkorange_mood_button;
    Button orange_mood_button;
    Button  yellow_mood_button;
    Button green_mood_button;
    TextView survey_question_tv;
    Button exitButton;
    String[] questions = {"What is your mood today?","What is your stress level?"};
    final int questions_array_size = 2;
    int question_order_count;
    Intent navigatedFrom;
    Boolean exitVisible;
    int from;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page);

        //Initialization
        red_mood_button = findViewById(R.id.red_mood_button);
        darkorange_mood_button = findViewById(R.id.darkorange_mood_button);
        orange_mood_button = findViewById(R.id.orange_mood_button);
        yellow_mood_button = findViewById(R.id.yellow_mood_button);
        green_mood_button = findViewById(R.id.green_mood_button);
        survey_question_tv = findViewById(R.id.survey_question_tv);
        question_order_count = 0;
        survey_question_tv.setText(questions[question_order_count]);
        surveyPage = this;
        //mood_desc_ll = findViewById(R.id.mood_desc_ll);
        //mood_desc_stress = findViewById(R.id.mood_desc_stress);
        exitButton =  findViewById(R.id.exit_button_survey);

//        mood_desc_ll.setVisibility(View.INVISIBLE);
//        mood_desc_stress.setVisibility(View.INVISIBLE);
        survey_question_tv.setVisibility(View.INVISIBLE);
        red_mood_button.setVisibility(View.INVISIBLE);
        darkorange_mood_button.setVisibility(View.INVISIBLE);
        orange_mood_button.setVisibility(View.INVISIBLE);
        yellow_mood_button.setVisibility(View.INVISIBLE);
        green_mood_button.setVisibility(View.INVISIBLE);

        exitVisible = false;

        navigatedFrom = getIntent();
        if(navigatedFrom.hasExtra("NavigatedFrom"))
        {
            from = navigatedFrom.getIntExtra("NavigatedFrom", 0);
            if(from == -1 || from == -2 || from == -3)
            {
                exitButton.setVisibility(View.VISIBLE);
                exitVisible = true;
            }
        }

        //Delay for survey buttons
        new CountDownTimer(1500, 100) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished < 800 )
                {
                    survey_question_tv.setVisibility(View.VISIBLE);
                }

            }

            public void onFinish() {

                red_mood_button.setVisibility(View.VISIBLE);
                darkorange_mood_button.setVisibility(View.VISIBLE);
                orange_mood_button.setVisibility(View.VISIBLE);
                yellow_mood_button.setVisibility(View.VISIBLE);
                green_mood_button.setVisibility(View.VISIBLE);
//                mood_desc_ll.setVisibility(View.VISIBLE);
               // mood_desc_stress.setVisibility(View.VISIBLE);


            }
        }.start();

        red_mood_button.setOnClickListener(this);
        darkorange_mood_button.setOnClickListener(this);
        orange_mood_button.setOnClickListener(this);
        yellow_mood_button.setOnClickListener(this);
        green_mood_button.setOnClickListener(this);
        exitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_navigate;
                switch (from){
                    case -1:
                        to_navigate = new Intent(surveyPage, MindfullnessBreathing.class);
                        startActivity(to_navigate);
                        break;
                    case -2:
                        to_navigate = new Intent(surveyPage, MindfullnessMeditation.class);
                        startActivity(to_navigate);
                         break;
                    case -3:
                        to_navigate = new Intent(surveyPage, MindfullnessWalking.class);
                        startActivity(to_navigate);
                        break;
                }
            }
        });

        move_to_animation =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.blink);
        move_from_animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.movefromnegative);

        move_from_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                red_mood_button.setVisibility(View.VISIBLE);
                darkorange_mood_button.setVisibility(View.VISIBLE);
                orange_mood_button.setVisibility(View.VISIBLE);
                yellow_mood_button.setVisibility(View.VISIBLE);
                green_mood_button.setVisibility(View.VISIBLE);

                red_mood_button.setClickable(true);
                darkorange_mood_button.setClickable(true);
                orange_mood_button.setClickable(true);
                yellow_mood_button.setClickable(true);
                green_mood_button.setClickable(true);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        move_to_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                red_mood_button.setClickable(false);
                darkorange_mood_button.setClickable(false);
                orange_mood_button.setClickable(false);
                yellow_mood_button.setClickable(false);
                green_mood_button.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                survey_question_tv.setText(questions[question_order_count]);
               /* if(question_order_count == 0)
                {
                    mood_desc_ll.setVisibility(View.VISIBLE);
                }
                else if(question_order_count == 1)
                {
                    mood_desc_ll.setVisibility(View.INVISIBLE);
                    mood_desc_stress.setVisibility(View.VISIBLE);
                }*/
                survey_question_tv.startAnimation(move_from_animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        // result will be considered on a scale of 1-5
        int q1_response = 0;
        int q2_response = 0;
        int result = 0;
        //v_id is the id of the view that is passed as a parameter
        int v_id = v.getId();

        if(exitVisible)
        {
            exitButton.setVisibility(View.INVISIBLE);
            exitVisible = false;
        }
        if(v_id == red_mood_button.getId())
        {
            red_mood_button.setVisibility(View.INVISIBLE);
            result = 1;
        }
        else if(v_id == darkorange_mood_button.getId())
        {
            darkorange_mood_button.setVisibility(View.INVISIBLE);
            result = 2;
        }
        else if(v_id == orange_mood_button.getId())
        {
            orange_mood_button.setVisibility(View.INVISIBLE);
            result =3;
        }
        else if(v_id == yellow_mood_button.getId())
        {
            yellow_mood_button.setVisibility(View.INVISIBLE);
            result = 4;
        }
        else if(v_id == green_mood_button.getId())
        {
            green_mood_button.setVisibility(View.INVISIBLE);
            result = 5;
        }

        if(question_order_count == 0)
        {
            q1_response = result;
        }
        else
        {
            q2_response = result;
        }

        question_order_count++;

        if(question_order_count < questions_array_size) {
            survey_question_tv.startAnimation(move_to_animation);

        }
        else
        {
            NetworkCalls.createMoodReport(MainActivity.user_info.getUser_id(), q1_response,q2_response, surveyPage);

            Intent navigated_from = getIntent();
            Intent to_navigate;
            int mindfullness = 0;
            if(navigated_from.hasExtra("NavigatedFrom"))
            {
                mindfullness = navigated_from.getIntExtra("NavigatedFrom", 0);
                if(mindfullness == -1)
                {
                    int timer = navigated_from.getIntExtra("TimerValue", 1);
                    to_navigate = new Intent(surveyPage, MindfullnessBreathingGame.class);
                    to_navigate.putExtra("TimerValue", timer);
                    startActivity(to_navigate);
                }
                else if(mindfullness == 1)
                {
                    to_navigate = new Intent(surveyPage, MindfullnessBreathing.class);
                    startActivity(to_navigate);
                }
                else if(mindfullness == -2 )
                {
                    to_navigate = new Intent(surveyPage, MindfullnessFeatherSelection.class);
                    startActivity(to_navigate);
                }
                else if(mindfullness == 2)
                {
                    to_navigate = new Intent(surveyPage, MindfullnessMeditation.class);
                    startActivity(to_navigate);
                }
                else if(mindfullness == -3)
                {
                    to_navigate = new Intent(surveyPage, MindfullnessWalkingGame.class);
                    startActivity(to_navigate);
                }
                else if(mindfullness == 3)
                {
                    to_navigate = new Intent(surveyPage, MindfullnessWalking.class);
                    startActivity(to_navigate);
                }
            }
            else{
                to_navigate = new Intent(surveyPage, HomeScreen.class);
                startActivity(to_navigate);
                overridePendingTransition(R.anim.blink_reverse,R.anim.blink);
            }

        }


    }



}
