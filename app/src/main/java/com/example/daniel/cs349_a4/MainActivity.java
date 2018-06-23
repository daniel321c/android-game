package com.example.daniel.cs349_a4;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Handler handler;
    TextView score;
    TextView message;
    Button back;
    int btn_num;
    int level;

    ArrayList<Button> btns = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn = (Button)findViewById(R.id.button);
        score = (TextView)findViewById(R.id.score);
        message = (TextView)findViewById(R.id.message);
        back = (Button)findViewById(R.id.back);

        btn_num = Integer.parseInt(getIntent().getExtras().getString("BTN_NUMBER"));
        level = Integer.parseInt(getIntent().getExtras().getString("LEVEL"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Welcome.class);

                Bundle bdl = new Bundle();
                bdl.putString("BTN_NUMBER", Integer.toString(btn_num-1));
                bdl.putString("LEVEL", Integer.toString(level-1));
                intent.putExtras(bdl);

                startActivity(intent);
                MainActivity.this.finish();
            }
        });



        RelativeLayout relative = (RelativeLayout) findViewById(R.id.btnlayout);


        final Simon simon = new Simon(btn_num, true);

        final Button new_round_btn = (Button)findViewById(R.id.button);

        new_round_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message.setText("WATCH WHAT I DO ...");
                Thread thread = new Thread(new MyThread(simon, message,score));
                thread.start();

            }
        });

            for (int i = 0; i < btn_num; i++) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(150, 150);
                int width = relative.getLayoutParams().width / btn_num;

                int padding = (width - params.width)/2;
                params.topMargin = 30;
                params.leftMargin = (width * i) + padding;
                Button btn = new Button(this);
                btn.setId(i);
                btn.setText( Integer.toString(i+1) );
                btn.setBackgroundResource(R.drawable.circle_btn_normal);
                relative.addView(btn, params);
                btns.add(btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                        Btn_Bounce interpolator = new Btn_Bounce(0.2, 20);
                        myAnim.setInterpolator(interpolator);
                        view.startAnimation(myAnim);

                        simon.verifyButton(view.getId());
                        if(simon.getState()==Simon.State.LOSE){
                            message.setText("YOU LOSE. PRESS START");
                            new_round_btn.setText("START");

                        }else if(simon.getState()==Simon.State.WIN){
                            message.setText("YOU WON!");
                            score.setText("SCORE: "+simon.getScore());

                            //need time to start new game
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Thread thread = new Thread(new MyThread(simon, message, score));
                                    thread.start();
                                }
                            }, 2000);

                        }

                    }
                });
            }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                message.setText("WATCH WHAT I DO ...");
                Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                Btn_Bounce interpolator = new Btn_Bounce(0.2, 20);
                myAnim.setInterpolator(interpolator);
//                btn.startAnimation(myAnim);
                btns.get(msg.arg1).startAnimation(myAnim);
                System.out.println(msg.arg1);
                if(simon.getState()==Simon.State.HUMAN){
                    message.setText("NOW IT IS YOUR TURN");
                }
            }
        };
    }


    class MyThread implements Runnable{

        Simon simon;
        MyThread(Simon simon, TextView message, TextView score){
            this.simon = simon;
        }
        public void run (){
            try{
                Thread.sleep(1000/(level==3? 10:level ==2? 5:2));
            }catch (Exception e){
                e.printStackTrace();
            }
            simon.newRound();
            while(simon.getState() == Simon.State.COMPUTER) {
                Message msg = Message.obtain();
                msg.arg1 = simon.nextButton();
                handler.sendMessage(msg);
                try {
                    Thread.sleep(2000/(level==3? 10:level ==2? 5:2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
