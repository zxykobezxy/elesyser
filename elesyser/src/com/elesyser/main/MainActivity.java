package com.elesyser.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elesyser.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	
	ListView menu;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        menu = (ListView) findViewById(R.id.mainmenu);
        
        menu.setAdapter(new SimpleAdapter(this,getData(),R.layout.mainlistitem,new String[]{"tv_mainlistitem"},new int[]{R.id.tv_mainlistitem}));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(Menu.NONE,Menu.FIRST + 1,Menu.NONE,"����");
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
    	item.put("tv_mainlistitem", "�γ̱�");
    	item2.put("tv_mainlistitem", "���԰���");
    	data.add(item);
    	data.add(item2);
    	return data;
    }
}
