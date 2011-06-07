package com.elesyser.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.R.integer;
import android.util.Log;

import com.elesyser.R.string;
import com.elesyser.parser.CurriculumTableParser;

public class CurriculumManager {
	
	String academicYear;
	String semester;
	String getHtml;
	String html;
	CurriculumTableParser ctp;
	String Url = "http://electsys0.sjtu.edu.cn/edu/student/elect/viewLessonTbl.aspx";
	
	public CurriculumManager(String d,String s){
		this.academicYear = d;
		this.semester = s;
	}
	
	public CurriculumManager(ObjectInputStream ois){
		ctp = new CurriculumTableParser(ois);
	}
	
	public List<CourseInfo> getCourses( int day){
		if (html == null){
			getHtml();
		}
		if(ctp == null){
			ctp = new CurriculumTableParser(html);
		}
		return ctp.getCurriculums(day);
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
		params.add(new BasicNameValuePair("dpXn",academicYear) );
		params.add(new BasicNameValuePair("dpXq",semester));
		params.add(new BasicNameValuePair("btnOk","ȷ ��"));
		
		try {
			html = http.post(Url, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Persist( ObjectOutputStream oop){
		try {
			ctp.Persist(oop);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("exception", "Persist failed");
		}
	}

}
