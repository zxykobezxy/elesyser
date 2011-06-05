package com.elesyser.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elesyser.R;
import com.elesyser.util.ExamManager;
import com.elesyser.util.ExamInfo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ExamActivity extends Activity{
	
	ExamManager ex;
	ListView examlist;
	private int Year;
	private int Month;
	final Calendar c = Calendar.getInstance();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.examtable);
    	examlist = (ListView) findViewById(R.id.lv_exam);
    	Year = c.get(Calendar.YEAR); //获取当前年份 
        Month = c.get(Calendar.MONTH);//获取当前月份
        String temp;
        String temp2;
        if(Month>=9){
        	temp=Year+"-"+(Year+1);
        	temp2="1";
        }
        else{
        	temp=(Year-1)+"-"+Year;
        	temp2="2";
        }
    	new getExam().execute(temp,temp2);
    }
	
	private class getExam extends AsyncTask<String,Integer,List<ExamInfo>>{
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected void onPostExecute(List<ExamInfo> result){
			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for(int i = 0; i < result.size(); ++i){
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("tv_name", result.get(i).getCourseName()+result.get(i).getTime()+"   "+result.get(i).getLocation()+"   "+result.get(i).getTeacher());
				data.add(ret);
			}
			examlist.setAdapter(new SimpleAdapter(ExamActivity.this,data,R.layout.examitem,new String[]{"tv_name"}, new int[]{R.id.tv_name}));
		}

		@Override
		protected List<ExamInfo> doInBackground(String... params) {
			ex = new ExamManager( params[0], params[1]);
			List<ExamInfo> ret = ex.getExams();
			return ret;
		}
    }

}
