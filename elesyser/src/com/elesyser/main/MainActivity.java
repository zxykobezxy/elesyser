package com.elesyser.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elesyser.R;
import com.elesyser.util.CurriculumManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	
	ListView menu;
	CurriculumManager cm;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		try {
			FileInputStream fi = openFileInput("test");
			try {
				ObjectInputStream oop = new ObjectInputStream(fi);
				cm = new CurriculumManager(oop);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("exception", "Persist failed");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        menu = (ListView) findViewById(R.id.mainmenu);
        
        menu.setAdapter(new SimpleAdapter(this,getData(),R.layout.mainlistitem,new String[]{"tv_mainlistitem"},new int[]{R.id.tv_mainlistitem}));
        menu.setOnItemClickListener(contentClickListener);
    }
   

	@Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(Menu.NONE,Menu.FIRST + 1,Menu.NONE,"test");
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();      
        System.out.println(itemId);
        return super.onOptionsItemSelected(item);
    }
    
    private List<Map<String, String>> getData(){
    	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    	Map<String,String> item = new HashMap<String,String>();
    	Map<String,String> item2 = new HashMap<String,String>();
    	Map<String,String> item3 = new HashMap<String,String>();
    	item.put("tv_mainlistitem", "课程表");
    	item2.put("tv_mainlistitem", "考试安排");
    	item3.put("tv_mainlistitem", "查询空闲教室");
    	data.add(item);
    	data.add(item2);
    	data.add(item3);
    	return data;
    }
    
    protected OnItemClickListener contentClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg2 == 0){
				Intent intent = new Intent(MainActivity.this, CurriculumActivity.class);
				startActivity(intent);
			}
			if(arg2 == 1){
				Intent intent = new Intent(MainActivity.this, ExamActivity.class);
				startActivity(intent);
			}
			if(arg2 == 2){
				Intent intent = new Intent(MainActivity.this, ClassroomActivity.class);
				startActivity(intent);
			}
		}
    };
}
