package com.elesyser.main;

import com.elesyser.util.CurriculumManager;
import com.elesyser.util.LoginHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class CurriculumActivity extends Activity {
	
	CurriculumManager cu;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	new getCurriculum().execute("2010-2011");
    }
    
    
    private class getCurriculum extends AsyncTask<String,Integer,Integer>{
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected void onPostExecute(Integer result){
			Intent intent = new Intent(CurriculumActivity.this, MainActivity.class);
			startActivity(intent);
			CurriculumActivity.this.finish();
		}

		@Override
		protected Integer doInBackground(String... params) {
			// TODO check the userinfo via the Internet
			cu = new CurriculumManager( params[0]);
			cu.getCourses();
			return 1;
		}
    }
}
