package com.elesyser.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elesyser.R;
import com.elesyser.util.CourseInfo;
import com.elesyser.util.CurriculumManager;
import com.elesyser.util.LoginHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CurriculumActivity extends Activity {
	
	CurriculumManager cu;
	ListView curriculumlist;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.curriculumtable);
    	curriculumlist = (ListView) findViewById(R.id.lv_curriculum);
    	new getCurriculum().execute("2010-2011");
    }
    
    
    private class getCurriculum extends AsyncTask<String,Integer,List<CourseInfo>>{
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected void onPostExecute(List<CourseInfo> result){
			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for(int i = 0; i < result.size(); ++i){
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("tv_coursename", result.get(i).getCourseName());
				data.add(ret);
			}
			curriculumlist.setAdapter(new SimpleAdapter(CurriculumActivity.this,data,R.layout.curriculumitem,new String[]{"tv_coursename"}, new int[]{R.id.tv_coursename}));
		}

		@Override
		protected List<CourseInfo> doInBackground(String... params) {
			// TODO check the userinfo via the Internet
			cu = new CurriculumManager( params[0]);
			List<CourseInfo> ret = cu.getCourses();
			return ret;
		}
    }
}
