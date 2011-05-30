package com.elesyser.main;

import com.elesyser.R;
import com.elesyser.util.UserInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private EditText account;
	private EditText pwd;
	private Button login;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        account = (EditText) findViewById(R.id.Username);
        pwd = (EditText) findViewById(R.id.Password);
        login = (Button) findViewById(R.id.bt_login);
        login.setOnClickListener(LoginListener);
    }
    
    public OnClickListener LoginListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(LoginActivity.this, LoginingActivity.class);
			UserInfo.Username = account.getText().toString();
			UserInfo.Password = pwd.getText().toString();
			startActivity(intent);
		}
    };
}
