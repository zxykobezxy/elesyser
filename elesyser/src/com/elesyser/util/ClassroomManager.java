package com.elesyser.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.elesyser.parser.ClassroomTableParser;
import com.elesyser.parser.ExamTableParser;

public class ClassroomManager {
	
	String Url = "http://classroom.jwc.sjtu.edu.cn/wap/query";
	String timestart;
	String timelength;
	String building;
	String html;
	
	public ClassroomManager(String s, String l, String b){
		if(s.equals("当前时间")) this.timestart = "0";
		else if(s.equals("5分钟后")) this.timestart = "5";
		else if(s.equals("15分钟后")) this.timestart = "15";
		else if(s.equals("30分钟后")) this.timestart = "30";
		else if(s.equals("60分钟后")) this.timestart = "60";
		
		if(l.equals("半小时")) this.timelength = "30";
		else if(l.equals("一小时")) this.timelength = "60";
		else if(l.equals("两小时")) this.timelength = "120";
		else if(l.equals("四小时")) this.timelength = "240";
		else if(l.equals("六小时")) this.timelength = "360";
		
		if(b.equals("西区教学楼")) this.building = "w";
		else if(b.equals("上院")) this.building = "1";
		else if(b.equals("中院")) this.building = "2";
		else if(b.equals("下院")) this.building = "3";
		else if(b.equals("东区教学楼")) this.building = "e";
		else if(b.equals("东上院")) this.building = "4";
		else if(b.equals("东中院")) this.building = "5";
		else if(b.equals("东下院")) this.building = "6";
	}
	
	public List<RoomInfo> getRooms(){
		if (html == null){
			getHtml();
		}
		ClassroomTableParser ctp = new ClassroomTableParser(html);
		return ctp.getRooms();
	}

	private void getHtml() {
		HttpAccess http = HttpAccess.getInstance();
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("timestart",timestart) );
		params.add(new BasicNameValuePair("timelength",timelength));
		params.add(new BasicNameValuePair("building",building));
		params.add(new BasicNameValuePair("submit","查询"));
		
		try {
			html = http.post(Url, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
