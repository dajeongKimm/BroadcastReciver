package com.dajeong.android.broadcastreciver;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        }else{
            init();
        }


    }
    private static final int REQ_PER = 99;
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        if(checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            String perm[] = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS};
            requestPermissions(perm, REQ_PER);
        }else{
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PER){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                init();
            }else{
                Toast.makeText(this,"허용하지 않으면 앱을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void init(){
        System.out.println("SMS");
    }
}
