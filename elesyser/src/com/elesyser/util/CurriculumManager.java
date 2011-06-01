package com.elesyser.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.elesyser.parser.CurriculumTableParser;

public class CurriculumManager {
	
	String date;
	String getHtml;
	String html;
	String Url = "http://electsys0.sjtu.edu.cn/edu/student/elect/viewLessonTbl.aspx";
	
	public CurriculumManager(String d){
		this.date = d;
	}
	
	public List<CourseInfo> getCourses(){
		if (html == null){
			getHtml();
		}
		CurriculumTableParser ctp = new CurriculumTableParser(html);
		
		/*int pos = html.indexOf("<body");
		int end = html.indexOf("</body>");
		html = html.substring(pos,end + 6);
		System.out.println(html);
		SAXParserFactory sf = SAXParserFactory.newInstance();
		SAXParser sp;
		XMLReader xr;
		try {
			sp = sf.newSAXParser();
			xr = sp.getXMLReader();
			CurriculumSaxHandler cs = new CurriculumSaxHandler();
			xr.setContentHandler(cs);
			xr.parse(html);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return ctp.getCurriculums(1);
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
