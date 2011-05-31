package com.elesyser.main;

import java.util.ArrayList;

import com.elesyser.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends Activity {
    /** Called when the activity is first created. */
	
	long delay = 200;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Handler handler = new Handler();
        handler.postDelayed(new starthandler(), delay);
    }
    
	class starthandler implements Runnable
	{
		@Override
		public void run() {
			//SharedPreferences sharedPreferences  = getSharedPreferences("config", MODE_PRIVATE);
//			Utli.userName = sharedPreferences.getString("username",null);
//			Utli.password = sharedPreferences.getString("password",null);
//			Utli.remember = sharedPreferences.getBoolean("remember", false);
//			Utli.auto = sharedPreferences.getBoolean("auto", false);
//			Utli.topicmode = sharedPreferences.getBoolean("topicmode", true);
//			Utli.favlist = new ArrayList<String>();
//			FavListEdit favListEdit = new FavListEdit(yssy.this);
//			try {
//				favListEdit.read();
//			} catch (Exception e) {
//				Toast.makeText(yssy.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//			}
			Intent intent = new Intent();
//			if(Utli.auto == false) {
//			     intent.setClass(yssy.this,LoginActivity.class);
//			}else {
//				intent.setClass(yssy.this,LoginingActivity.class);
//			}
			intent.setClass(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			SplashActivity.this.finish();
		}
	}
}