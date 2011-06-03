package com.elesyser.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elesyser.R;
import com.elesyser.util.ExamManager;
import com.elesyser.util.ExamInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ExamActivity extends Activity{
	
	ExamManager ex;
	ListView examlist;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.examtable);
    	examlist = (ListView) findViewById(R.id.lv_exam);
    	new getExam().execute("2010-2011");
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
				ret.put("tv_coursename2", result.get(i).getCourseName()+result.get(i).getTime()+"_"+result.get(i).getLocation()+"_"+result.get(i).getTeacher());
				data.add(ret);
			}
			examlist.setAdapter(new SimpleAdapter(ExamActivity.this,data,R.layout.examitem,new String[]{"tv_coursename2"}, new int[]{R.id.tv_coursename2}));
		}

		@Override
		protected List<ExamInfo> doInBackground(String... params) {
			// TODO check the userinfo via the Internet
			ex = new ExamManager( params[0]);
			List<ExamInfo> ret = ex.getExams();
			return ret;
		}
    }

}
