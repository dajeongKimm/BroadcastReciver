package com.dajeong.android.broadcastreciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())){
            String msg = "";

            //버전 킷캣이상은 더 간편하게 할수 있슴.
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                //1.SmsMessage배열을 함수로 꺼낸다.
                SmsMessage smss[] = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                for(SmsMessage sms : smss){
                    msg+= sms.getMessageBody();
                }


            }else { //킷캣 버전이하

                //1.intent를 통해 넘어온 데이터를 꺼낸다.
                Bundle bundle = intent.getExtras();
                Object object[] = (Object[]) bundle.get("pdus");
                //2.꺼낸 object형의 데이터를 SmsMessage객체로 변환.
                SmsMessage smss[] = new SmsMessage[object.length];
                //3. SmsMessage를 반복문을 통해 꺼내고 담아준다.
                for (int i = 0; i < object.length; i++) {
                    byte temp[] = (byte[]) object[i];
                    smss[i] = SmsMessage.createFromPdu(temp);
                    //위 두줄과 같음.
                    //smss[i] = SmsMessage.createFromPdu((byte[])object[i]);
                    msg += smss[i].getMessageBody();
                }


                System.out.println("SMS:msg = " + msg);
            }
        }
    }
}
