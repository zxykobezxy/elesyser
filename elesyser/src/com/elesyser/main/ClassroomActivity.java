package com.elesyser.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elesyser.R;
import com.elesyser.util.ClassroomManager;
import com.elesyser.util.RoomInfo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class ClassroomActivity extends Activity {
	
	ClassroomManager room;
	private ListView roomlist;
	private Spinner spinner1, spinner2;
	private ArrayAdapter adapter1, adapter2;
	private Button search;
	private RadioGroup rg;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.classroom);
    	//找到Xml中定义的下拉列表
        spinner1 = (Spinner)findViewById(R.id.timestart);
        spinner2 = (Spinner)findViewById(R.id.timelength);
        //准备一个数组适配器
        adapter1 = ArrayAdapter.createFromResource(this, R.array.timestart, android.R.layout.simple_spinner_item);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.timelength, android.R.layout.simple_spinner_item);
        //设置下拉样式
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //为下拉列表设置适配器
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        
        rg = (RadioGroup) findViewById(R.id.building);
        search = (Button) findViewById(R.id.bt_search);
        search.setOnClickListener(SearchListener);
    }
	
	public OnClickListener SearchListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			new getRoom().execute(spinner1.getSelectedItem().toString(), spinner2.getSelectedItem().toString(), getCheckedValue());
		}
	};
	
	public String getCheckedValue(){
		int n = rg.getChildCount();
		for(int i=0;i<n;i++){
			RadioButton radio = (RadioButton)rg.getChildAt(i);
		    if(radio.isChecked()){
		    	return (String) radio.getText();
		    }
		}
		return null;
	}
	
	private class getRoom extends AsyncTask<String,Integer,List<RoomInfo>>{
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected void onPostExecute(List<RoomInfo> result){
			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for(int i = 0; i < result.size(); ++i){
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("tv_name", result.get(i).getName()+"编号：  "+result.get(i).getID()+"        座位数：  "+result.get(i).getNum());
				data.add(ret);
			}
			setContentView(R.layout.roomlist);
			roomlist = (ListView) findViewById(R.id.lv_classroom);
			roomlist.setAdapter(new SimpleAdapter(ClassroomActivity.this,data,R.layout.examitem,new String[]{"tv_name"}, new int[]{R.id.tv_name}));
		}

		@Override
		protected List<RoomInfo> doInBackground(String... params) {
			room = new ClassroomManager( params[0], params[1],  params[2]);
			List<RoomInfo> ret = room.getRooms();
			return ret;
		}
    }
	
}
