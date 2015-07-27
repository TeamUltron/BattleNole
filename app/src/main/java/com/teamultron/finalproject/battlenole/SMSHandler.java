package com.teamultron.finalproject.battlenole;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by davidperez on 7/21/2015.
 */
public class SMSHandler  extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
      //  if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
        Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
        if (bundle != null) {
              //  Toast.makeText(context, "In bundle", Toast.LENGTH_SHORT).show();
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                // large message might be broken into many
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String sender = messages[0].getOriginatingAddress();
                String message = sb.toString();
               // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                // prevent any other broadcast receivers from receiving broadcast
                // abortBroadcast();
            }
       // }
    }
    /*// Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage messageContent = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = messageContent.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = messageContent.getDisplayMessageBody();
                    //TODO: Need to figure out how to pass this message that is created to the respective activities.
                    parseMessage(message);
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }*/
    //}

    //Sends a formatted message using the SMS manager in Android.
    public void sendMessage(BattleShipMessage b) {
        String message = "BattleShip ";
        message += b.getColumn();
        message += " ";
        message += b.getRow();
        //sms.sendTextMessage(,,message,,);
    }

    //Making some assumptions on the content of the message.
    public BattleShipMessage parseMessage(String message){
        if(message == null)
            return null;
        BattleShipMessage ret;
        String chunks[] = message.split("\\s+");
        if(chunks[0].equals("BattleShip") && chunks.length == 3) {
            int row = Integer.parseInt(chunks[1]);
            ret = new BattleShipMessage(row,chunks[2]);
            return ret;
        }
        ret = new BattleShipMessage();
        return ret;
    }
}
