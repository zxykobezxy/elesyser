package com.elesyser.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elesyser.R;
import com.elesyser.alarm.AlarmReceiver;
import com.elesyser.util.CourseInfo;
import com.elesyser.util.CurriculumManager;
import com.elesyser.util.LoginHelper;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CurriculumActivity extends Activity {
	
	CurriculumManager cu;
	ListView curriculumlist;
	RelativeLayout progressLayout;
	TextView dayofweek;
	DigitalClock clock;
	
	Integer Today;
	Integer Current;
	String[] weekdays;
	String[] begintimes;
	String[] endtimes;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.curriculumtable);
    	curriculumlist = (ListView) findViewById(R.id.lv_curriculum);
    	progressLayout = (RelativeLayout) findViewById(R.id.progresslayout);
    	dayofweek = (TextView) findViewById(R.id.dayofweek);
    	clock = (DigitalClock) findViewById(R.id.digitalClock1);
    	
    	Time time = new Time();
    	time.setToNow();
    	Integer year = time.year;
    	int month = time.month;
    	
    	String semestor = "";
    	String academicYear = "";
    	if(month < 9){
    		academicYear = Integer.toString(year-1) +"-" +year.toString();
    	}
    	else{
    		academicYear = year.toString() + "-" + Integer.toString(year + 1);
    	}
    	
    	if(month <2 || month >= 9){
    		semestor = "1";
    	}
    	else {
			semestor = "2";
		}
    	dayofweek.setText(year.toString());
    	Today = time.weekDay == 0 ? 7 : time.weekDay ;
    	
    	Current = Today;
    	
    	Resources res = getResources();
    	weekdays = res.getStringArray(R.array.weekday);
    	begintimes = res.getStringArray(R.array.classstarttime);
    	//endtimes = res.getStringArray(R.array.classendtime);
    	new getCurriculum().execute(academicYear,semestor);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(Menu.NONE,Menu.FIRST + 1,Menu.NONE,"上一天");
    	menu.add(Menu.NONE,Menu.FIRST + 2,Menu.NONE,"下一天");
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case Menu.FIRST + 1:
			ChangeDay(--Current);
			break;
		case Menu.FIRST + 2:
			ChangeDay(++Current);
			break;
		default:
			break;
		}
    	return true;
    }
    
    private void ChangeDay(int day) {
    	List<CourseInfo> Courses = cu.getCourses(day);
//		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
//		for(int i = 0; i < Courses.size(); ++i){
//			Map<String, Object> ret = new HashMap<String, Object>();
//			ret.put("tv_coursename", Courses.get(i).getCourseName());
//			data.add(ret);
//		}
//		curriculumlist.setAdapter(new SimpleAdapter(CurriculumActivity.this,data,R.layout.curriculumitem,new String[]{"tv_coursename"}, new int[]{R.id.tv_coursename}));
//		progressLayout.setVisibility(View.GONE);
//		dayofweek.setText(Today.toString());
//		dayofweek.setText(weekdays[day-1]);
    	setCurriculumTable(Courses);
    }
    
    private void setCurriculumTable(List<CourseInfo> result){
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < result.size(); ++i){
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("tv_coursename", result.get(i).getCourseName());
			ret.put("tv_classroom", result.get(i).getLocation());
			ret.put("tv_begintime", begintimes[result.get(i).getBegin()-1]);
			//ret.put("tv_endtime", endtimes[result.get(i).getEnd()]);
			ret.put("tv_endtime", "tst");
			data.add(ret);
		}
		curriculumlist.setAdapter(new SimpleAdapter(CurriculumActivity.this,data,R.layout.curriculumitem,new String[]{"tv_coursename","tv_classroom","tv_begintime","tv_endtime"}, new int[]{R.id.tv_coursename,R.id.tv_classroom,R.id.tv_begintime,R.id.tv_endtime}));
		
		dayofweek.setText(weekdays[Current-1]);
    }
    
    private class getCurriculum extends AsyncTask<String,Integer,List<CourseInfo>>{
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected void onPostExecute(List<CourseInfo> result){
			progressLayout.setVisibility(View.GONE);
			clock.setVisibility(View.VISIBLE);
			setCurriculumTable(result);
			Intent intent = new Intent(CurriculumActivity.this,AlarmReceiver.class); 
			intent.putExtra("message", "test");
			PendingIntent pi = PendingIntent.getBroadcast(CurriculumActivity.this, 0, intent, 0);
			AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)+5);
			am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
			
			try {
				FileOutputStream fo = openFileOutput("test", MODE_PRIVATE);
				try {
					ObjectOutputStream oop = new ObjectOutputStream(fo);
					cu.Persist(oop);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d("exception", "Persist failed");
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected List<CourseInfo> doInBackground(String... params) {
			// TODO check the userinfo via the Internet
			cu = new CurriculumManager( params[0],params[1]);
			List<CourseInfo> ret = cu.getCourses(Today);
			return ret;
		}
    }
}
