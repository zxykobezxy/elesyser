package com.elesyser.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


public class LoginHelper {
	
	String Url="http://electsys0.sjtu.edu.cn/edu/index.aspx";
	String Url2="http://electsys0.sjtu.edu.cn/edu/student/elect/viewLessonTbl.aspx";
	String Url3="http://electsys0.sjtu.edu.cn/edu/login.aspx";
	String Url4 = "https://jaccount.sjtu.edu.cn/jaccount/ulogin";
	String __VIEWSTATE;
	String __EVENTVALIDATION;
	
	public boolean Valid(String Username, String Password){
		HttpAccess http = HttpAccess.getInstance();
		String mainhtml = "";
		
		try {
			mainhtml = http.get(Url);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int first = mainhtml.indexOf("id=\"__VIEWSTATE\"");
		first = mainhtml.indexOf("=",first + 4);
		int last = mainhtml.indexOf("/>", first);
		__VIEWSTATE = mainhtml.substring(first + 2, last-2);
		UserInfo.__VIEWSTATE = __VIEWSTATE;
		
		first = mainhtml.indexOf("id=\"__EVENTVALIDATION\"");
		first = mainhtml.indexOf("=",first + 4);
		last = mainhtml.indexOf("/>", first);
		__EVENTVALIDATION = mainhtml.substring(first + 2, last-2);
		UserInfo.__EVENTVALIDATION = __EVENTVALIDATION;
		
		List <NameValuePair> params = new ArrayList <NameValuePair>(); 
		params.add(new BasicNameValuePair("__VIEWSTATE",__VIEWSTATE));
		params.add(new BasicNameValuePair("__EVENTVALIDATION",__EVENTVALIDATION));
		params.add(new BasicNameValuePair("txtUserName", Username));
		params.add(new BasicNameValuePair("txtPwd",Password));
		params.add(new BasicNameValuePair("rbtnLst","1"));
		params.add(new BasicNameValuePair("Button1","µÇÂ¼"));
		try {
			String temp = http.post(Url, params);
			//String temp2 = http.get(Url2);
			//int cnt = temp.indexOf("__VIEWSTATE");
			//String temp2 = new String(temp.getBytes(),"gbk");
			//System.out.print(temp);
		} catch (Exception e) {
			// TODO Auto-generatedfd catch block
			e.printStackTrace();
		}
		return true;
	}
//	public boolean Valid(String Username, String Password){
//		HttpAccess http = HttpAccess.getInstance();
//		String mainhtml = "";
//		try {
//			mainhtml = http.get(Url3);
//			int first = mainhtml.indexOf("name=\"sid\"");
//			first = mainhtml.indexOf("=",first + 5);
//			int last = mainhtml.indexOf("'>", first);
//			String sid = mainhtml.substring(first + 2, last);
//			
//			first = mainhtml.indexOf("name=\"se\"");
//			first = mainhtml.indexOf("=",first + 5);
//			last = mainhtml.indexOf("'>", first);
//			String  se = mainhtml.substring(first + 2, last);
//			
//			first = mainhtml.indexOf("name=\"returl\"");
//			first = mainhtml.indexOf("=",first + 5);
//			last = mainhtml.indexOf("'>", first);
//			String  returl = mainhtml.substring(first + 2, last);
//			
//			List <NameValuePair> params = new ArrayList <NameValuePair>(); 
//			params.add(new BasicNameValuePair("sid", sid));
//			params.add(new BasicNameValuePair("se",se));
//			params.add(new BasicNameValuePair("returl",returl));
//			params.add(new BasicNameValuePair("user","zxykobezxy"));
//			params.add(new BasicNameValuePair("pass","132566899zxy"));
//			
//			String temp = http.post(Url4, params);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return true;
//	}
}
