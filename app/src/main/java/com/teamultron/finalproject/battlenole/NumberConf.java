package com.teamultron.finalproject.battlenole;

import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


public class NumberConf extends ActionBarActivity {

    Button bS;
    EditText phone;
    ProgressBar pB;
    TextView tV;
    SmsManager smsManager;
    public static String myNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_conf);

        smsManager = SmsManager.getDefault();

        phone = (EditText) findViewById(R.id.editText);
        pB = (ProgressBar) findViewById(R.id.progressBar);
        tV = (TextView) findViewById(R.id.textView);
        bS = (Button) findViewById(R.id.bSubmit);

        pB.setVisibility(View.INVISIBLE);
        tV.setVisibility(View.INVISIBLE);

        bS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNum = phone.getText().toString();
                smsManager.sendTextMessage(myNum, null, "Welcome to Battleship", null, null);

                tV.setVisibility(View.VISIBLE);
                pB.setVisibility(View.VISIBLE);
            }
        });

        //TODO SET UP MESSAGE RECEPTION TO START GAME

    }
}
