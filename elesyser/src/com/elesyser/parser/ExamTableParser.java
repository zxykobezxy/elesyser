package com.elesyser.parser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.CoreConnectionPNames;

import android.R.integer;

import com.elesyser.util.ExamInfo;

public class ExamTableParser {
	
ArrayList<ExamInfo> List = new ArrayList<ExamInfo>();
	
	public ExamTableParser(String source){
		int pos = source.indexOf("id=\"gridMain\"");
		pos = source.indexOf("<tr",pos);
		pos = source.indexOf("<tr",pos  + 1);
		int endpos = source.indexOf("</table>",pos);
		String examString = source.substring(pos, endpos);
		int i=1;
		pos = 0;
		endpos = 0;
		while( pos != -1){
			endpos = examString.indexOf("</tr>",pos);
			String tempString = examString.substring(pos,endpos);
			ExamInfo exam = new ExamInfo();
			int temppos = tempString.indexOf("<td");
			temppos = tempString.indexOf(">",temppos) + 1;
			int tempendpos = tempString.indexOf("</td>",temppos);
			String name = tempString.substring(temppos,tempendpos);
			exam.setCourseName(name);
			temppos = tempString.indexOf("<td",tempendpos);
			temppos = tempString.indexOf(">",temppos) + 1;
			tempendpos = tempString.indexOf("</td>",temppos);
			temppos = tempString.indexOf("<td",tempendpos);
			temppos = tempString.indexOf(">",temppos) + 1;
			tempendpos = tempString.indexOf("</td>",temppos);
			String time = tempString.substring(temppos,tempendpos);
			exam.setTime(time);
			temppos = tempString.indexOf("<td",tempendpos);
			temppos = tempString.indexOf(">",temppos) + 1;
			tempendpos = tempString.indexOf("</td>",temppos);
			String location = tempString.substring(temppos,tempendpos);
			exam.setLocation(location);
			temppos = tempString.indexOf("<td",tempendpos);
			temppos = tempString.indexOf(">",temppos) + 1;
			tempendpos = tempString.indexOf("</td>",temppos);
			String teacher = tempString.substring(temppos,tempendpos);
			exam.setTeacher(teacher);
			List.add(exam);
			pos = examString.indexOf("<tr",endpos);
		}
	}
	
	public List<ExamInfo> getExams(){
		return List;
	}
	
}
