package com.sabuj.messmanagement.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sabuj.messmanagement.R;
import com.sabuj.messmanagement.storage.Save;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(new Save().admin_loadData(getApplicationContext())){
            startActivity(new Intent(SplashActivity.this,AdminActivity.class));
            finish();
        }else if(new Save().member_loadData(getApplicationContext())) {
            startActivity(new Intent(SplashActivity.this,MemberActivity.class));
            finish();
        }else {
            startActivity(new Intent(SplashActivity.this,adminLoginActivity.class));
            finish();
        }


        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
