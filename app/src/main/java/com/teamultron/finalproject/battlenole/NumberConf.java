package com.teamultron.finalproject.battlenole;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
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
                smsManager.sendTextMessage(myNum, null, "#StartGame", null, null);
                bS.setClickable(false);
                tV.setVisibility(View.VISIBLE);
                pB.setVisibility(View.VISIBLE);
            }
        });
        /*BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Retrieves a map of extended data from the intent.
                final Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        final Object[] pdusObj = (Object[]) bundle.get("pdus");

                        for (int i = 0; i < pdusObj.length; i++) {
                            SmsMessage messageContent = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                            String senderNumber = messageContent.getDisplayOriginatingAddress();
                            String message = messageContent.getDisplayMessageBody();
                            //There might be some issues here with confirmation on both ends.
                            if (senderNumber.equals(myNum))
                                if (message.equals("#StartGame")) {
                                    final Intent gameStart = new Intent(NumberConf.this, GameActivity.class);
                                    startActivity(gameStart);
                                }
                        }
                    }
            }
        };
        IntentFilter theFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        theFilter.addAction("android.provider.Telephony.WAP_PUSH_DELIVER");
        theFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(br, theFilter);*/
    }
        //TODO SET UP MESSAGE RECEPTION TO START GAME
        //ON Receive, do the following---
        //if message is from same number and has confirmation key
        //(message should be the same as above, #StartGame,
        //then
        //final Intent i = new Intent(this, GameActivity.class);
        //startactivity(i);

}
