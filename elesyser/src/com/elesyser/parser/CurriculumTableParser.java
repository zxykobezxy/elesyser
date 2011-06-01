package com.elesyser.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.CoreConnectionPNames;

import com.elesyser.util.CourseInfo;

public class CurriculumTableParser {
	//String source;
	ArrayList<ArrayList<CourseInfo>> source = new ArrayList<ArrayList<CourseInfo>>();
	
	public CurriculumTableParser(String source){
		int pos = source.indexOf("id=\"LessonTbl1_spanContent\"");
		pos = source.indexOf("alltab",pos);
		pos = source.indexOf("<tr>",pos);
		int endpos = source.indexOf("</table>",pos);
		String courseString = source.substring(pos, endpos);
		
		pos = 0;
		endpos = 0;
		while( pos != -1){
			endpos = courseString.indexOf("</tr>",pos);
			String tempString = courseString.substring(pos + 4,endpos);
			int tdpos = tempString.indexOf("<td");
			int tdendpos = 0;
			ArrayList<CourseInfo> tempList = new ArrayList<CourseInfo>();
			while(tdpos != -1){
				int temppos = tempString.indexOf(">",tdpos);
				tdendpos = tempString.indexOf("</td>",tdpos);
				String courseinfoString = tempString.substring(temppos + 1,tdendpos);
				CourseInfo c = new CourseInfo();
				c.setCourseName(courseinfoString);
				tempList.add(c);
				tdpos = tempString.indexOf("<td",tdendpos);
			}
			this.source.add(tempList);
			pos = courseString.indexOf("<tr>", pos + 1);
		}
	}
	
	public List<CourseInfo> getCurriculums(int day){
		List<CourseInfo> ret = new ArrayList<CourseInfo>();
		for(int i = 0; i < source.size(); ++i){
			ret.add(source.get(i).get(day-1));
		}
		return ret;
	}
}
