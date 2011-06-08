package com.elesyser.parser;

import java.util.ArrayList;
import java.util.List;

import com.elesyser.util.RoomInfo;

public class ClassroomTableParser {

	ArrayList<RoomInfo> List = new ArrayList<RoomInfo>();
	
	public ClassroomTableParser(String html) {
		// TODO Auto-generated constructor stub
		int pos = html.indexOf("</tr>");
		int endpos = html.indexOf("</table>",pos);
		String roomString = html.substring(pos+5, endpos);
		pos = 0;
		endpos = 0;
		while( pos != -1){
			endpos = roomString.indexOf("</tr>",pos);
			String tempString = roomString.substring(pos,endpos);
			RoomInfo room = new RoomInfo();
			int temppos = tempString.indexOf("<td>")+5;
			int tempendpos = tempString.indexOf("</td>",temppos);
			String name = tempString.substring(temppos,tempendpos-1);
			room.setName(name);
			temppos = tempendpos+11;
			tempendpos = tempString.indexOf("</td>",temppos);
			String id = tempString.substring(temppos,tempendpos);
			room.setID(id);
			temppos = tempendpos+11;
			tempendpos = tempString.indexOf("</td>",temppos);
			String num = tempString.substring(temppos,tempendpos);
			room.setNum(num);
			List.add(room);
			pos = roomString.indexOf("<tr>",endpos);
		}
	}

	public List<RoomInfo> getRooms() {
		// TODO Auto-generated method stub
		return List;
	}

}
