package com.elesyser.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.elesyser.parser.ExamTableParser;

public class ExamManager {
	
	String year;
	String term;
	String getHtml;
	String html;
	String Url = "http://electsys0.sjtu.edu.cn/edu/student/examArrange/examArrange.aspx";
	
	public ExamManager(String y, String t){
		this.year = y;
		this.term = t;
	}
	
	public List<ExamInfo> getExams(){
		if (html == null){
			getHtml();
		}
		ExamTableParser etp = new ExamTableParser(html);
		return etp.getExams();
	}
	
	private void getHtml() {
		HttpAccess http = HttpAccess.getInstance();
		try {
			getHtml = http.get(Url);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int first = getHtml.indexOf("id=\"__VIEWSTATE\"");
		first = getHtml.indexOf("=",first + 4);
		int last = getHtml.indexOf("/>", first);
		String __VIEWSTATE = getHtml.substring(first + 2, last-2);
		
		first = getHtml.indexOf("id=\"__EVENTVALIDATION\"");
		first = getHtml.indexOf("=",first + 4);
		last = getHtml.indexOf("/>", first);
		String __EVENTVALIDATION = getHtml.substring(first + 2, last-2);
		
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE",__VIEWSTATE));
		params.add(new BasicNameValuePair("__EVENTVALIDATION",__EVENTVALIDATION));
		params.add(new BasicNameValuePair("dpXn",year) );
		params.add(new BasicNameValuePair("dpXq",term));
		params.add(new BasicNameValuePair("btnQuery","查 询"));
		
		try {
			html = http.post(Url, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
