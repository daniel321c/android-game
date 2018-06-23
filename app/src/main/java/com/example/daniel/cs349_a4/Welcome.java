package com.example.daniel.cs349_a4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    Button enter;
    Button exit;
    Button btn_instruction;
    SeekBar level;
    SeekBar btn_num;
    TextView difficulty;
    TextView btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        enter = (Button)findViewById(R.id.enter);
        exit = (Button)findViewById(R.id.exit);

        level = (SeekBar)findViewById(R.id.level);
        btn_num = (SeekBar)findViewById(R.id.number);

        difficulty = (TextView)findViewById(R.id.difficulty);
        btn =(TextView)findViewById(R.id.btn_number);

        final AlertDialog  dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("INSTRUCTION");
        dialog.setMessage(
                "(1) PLAYER NEEDS TO FOLLOW THE CLICK THE BUTTON IN THE SAME SEQUENCE AS HOW THE COMPUTER PLAYED.\n" +
                "(2) PLAYER CAN CHANGE THE DIFFICULTY AND BUTTON NUMBER BELOW");

        btn_instruction = (Button) findViewById(R.id.instruction);
        btn_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
        }});

        if(getIntent().getExtras()!=null) {
            btn_num.setProgress(Integer.parseInt(getIntent().getExtras().getString("BTN_NUMBER")));
            level.setProgress(Integer.parseInt(getIntent().getExtras().getString("LEVEL")));
            switch (Integer.parseInt(getIntent().getExtras().getString("LEVEL"))) {
                case (0):
                    difficulty.setText("EASY");
                    break;
                case (1):
                    difficulty.setText("NORMAL");
                    break;
                case (2):
                    difficulty.setText("HARD");
                    break;
                default:
                    break;
            }
            btn.setText(Integer.toString(Integer.parseInt(getIntent().getExtras().getString("BTN_NUMBER"))+1));
        }
        
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Welcome.this, MainActivity.class);
                Bundle bdl = new Bundle();
                bdl.putString("BTN_NUMBER", Integer.toString(btn_num.getProgress()+1));
                bdl.putString("LEVEL", Integer.toString(level.getProgress()+1));
                intent.putExtras(bdl);

                startActivity(intent);
                //Welcome.this.finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        level.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case (0):
                        difficulty.setText("EASY");
                        break;
                    case (1):                                             
                        difficulty.setText("NORMAL");
                        break;
                    case (2):
                        difficulty.setText("HARD");
                        break;
                    default:
                        break;
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btn_num.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                btn.setText(Integer.toString(i+1));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

}
