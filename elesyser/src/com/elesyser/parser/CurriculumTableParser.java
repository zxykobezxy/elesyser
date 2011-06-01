package com.elesyser.parser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.CoreConnectionPNames;

import android.R.integer;

import com.elesyser.util.CourseInfo;

public class CurriculumTableParser {
	//String source;
	ArrayList<ArrayList<CourseInfo>> source = new ArrayList<ArrayList<CourseInfo>>();
	
	public CurriculumTableParser(String source){
		int pos = source.indexOf("id=\"LessonTbl1_spanContent\"");
		pos = source.indexOf("alltab",pos);
		pos = source.indexOf("<tr>",pos);
		pos = source.indexOf("<tr>",pos  + 1);
		int endpos = source.indexOf("</table>",pos);
		String courseString = source.substring(pos, endpos);
		
		pos = 0;
		endpos = 0;
		int cnt = 1;
		while( pos != -1){
			endpos = courseString.indexOf("</tr>",pos);
			String tempString = courseString.substring(pos + 4,endpos);
			int tdpos = tempString.indexOf("<td");
			tdpos = tempString.indexOf("<td",tdpos + 1);
			int tdendpos = 0;
			ArrayList<CourseInfo> tempList = new ArrayList<CourseInfo>();
			
			while(tdpos != -1){
				int temppos = tempString.indexOf(">",tdpos);
				String attr = tempString.substring(tdpos,temppos);
				int sr = getrowspan(attr);
				tdendpos = tempString.indexOf("</td>",tdpos);
				String courseinfoString = tempString.substring(temppos + 1,tdendpos);
				if(courseinfoString.equals("&nbsp;")){
					tdpos = tempString.indexOf("<td",tdendpos);
					continue;
				}					
				CourseInfo c = ConvertToCourse(courseinfoString);
				c.setBegin(cnt);
				c.setEnd(cnt+sr-1);
				//c.setCourseName(courseinfoString);
				tempList.add(c);
				tdpos = tempString.indexOf("<td",tdendpos + 1);
			}
			this.source.add(tempList);
			pos = courseString.indexOf("<tr>", pos + 1);
			cnt++;
		}
	}
	
	public List<CourseInfo> getCurriculums(int day){
		List<CourseInfo> ret = new ArrayList<CourseInfo>();
		for(int i = 0; i < source.size(); ++i){
			if(source.get(i).size()<day)
				continue;
			ret.add(source.get(i).get(day-1));
		}
		return ret;
	}
	
	private CourseInfo ConvertToCourse(String s){
		CourseInfo courseInfo = new CourseInfo();
		String ret = s;
		int pos = s.indexOf("<a");
		if(pos != -1){
			pos = ret.indexOf(">",pos);
			int endpos = ret.indexOf("</a>");
			ret = ret.substring(pos+1,endpos);
		}
		courseInfo.setCourseName(ret);
		return courseInfo;
	}
	
	private int getrowspan(String s){
		int pos = s.indexOf("rowspan");
		if(pos != -1){
			pos = s.indexOf("=", pos);
			return Integer.parseInt(s.substring(pos + 1));
		}
		else {
			return 1;
		}
	}
}
