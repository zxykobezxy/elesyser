package com.elesyser.parser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.CoreConnectionPNames;

import android.R.integer;

import com.elesyser.util.CourseInfo;

public class CurriculumTableParser {
	//String source;
	ArrayList<ArrayList<CourseInfo>> source = new ArrayList<ArrayList<CourseInfo>>();
	
	public CurriculumTableParser(ObjectInputStream oip){
		try {
			source = (ArrayList<ArrayList<CourseInfo>>)oip.readObject();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
			int dayweek = 1;
			while(tdpos != -1){
				int temppos = tempString.indexOf(">",tdpos);
				String attr = tempString.substring(tdpos,temppos);
				int sr = getrowspan(attr);
				tdendpos = tempString.indexOf("</td>",tdpos);
				String courseinfoString = tempString.substring(temppos + 1,tdendpos);
				if(courseinfoString.equals("&nbsp;")){
					tdpos = tempString.indexOf("<td",tdendpos);
					++dayweek;
					continue;
				}					
				CourseInfo c = ConvertToCourse(courseinfoString);
				c.setBegin(cnt);
				c.setEnd(cnt+sr-1);
				c.setWeekday(dayweek);
				//c.setCourseName(courseinfoString);
				tempList.add(c);
				tdpos = tempString.indexOf("<td",tdendpos + 1);
				++dayweek;
			}
			this.source.add(tempList);
			pos = courseString.indexOf("<tr>", pos + 1);
			cnt++;
		}
	}
	
	
	public List<CourseInfo> getCurriculums(int day){
		List<CourseInfo> ret = new ArrayList<CourseInfo>();
		CourseInfo tempcourse;
		for(int i = 0; i < source.size(); ++i){
			ArrayList<CourseInfo> tempArrayList = source.get(i);
			for(int j = 0; j < tempArrayList.size(); ++j){
				if(tempArrayList.get(j).getWeekday() == day)
					ret.add(tempArrayList.get(j));
			}
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
		
		pos = ret.lastIndexOf("[");
		if(pos != -1){
			courseInfo.setLocation(ret.substring(pos + 1, ret.length()-1));
			ret = ret.substring(0, pos);
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
	
	public void Persist(ObjectOutputStream oop) throws IOException{
		oop.writeObject(source);
	}
	
	
}
