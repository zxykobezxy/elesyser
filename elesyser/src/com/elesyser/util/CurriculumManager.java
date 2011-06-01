package com.elesyser.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class CurriculumManager {
	
	String date;
	String html;
	String Url = "http://electsys0.sjtu.edu.cn/edu/student/elect/viewLessonTbl.aspx";
	
	public CurriculumManager(String d){
		this.date = d;
	}
	
	public List<CourseInfo> getCourses(){
		if (html == null){
			getHtml();
		}
		return null;
	}
	
	private void getHtml() {
		HttpAccess http = HttpAccess.getInstance();
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE", UserInfo.__VIEWSTATE));
		params.add(new BasicNameValuePair("__EVENTVALIDATION",UserInfo.__EVENTVALIDATION));
		params.add(new BasicNameValuePair("dpXn",date) );
		params.add(new BasicNameValuePair("dpXq","1"));
		params.add(new BasicNameValuePair("btnOk","È· ¶¨"));
		
		try {
			html = http.post(Url, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
