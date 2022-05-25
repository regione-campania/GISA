/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

public class DetectBrowser implements Serializable {


	private HttpServletRequest 		request 	= null;
	private String 					useragent   = null;
	private boolean 				netEnabled 	= false;
	private boolean 				oldIe 		= false;
	private boolean 				newIe 		= false;
	private boolean 				ns6 		= false;
	private boolean 				ns4 		= false;
	private boolean 				chrome 		= false;


	public void setRequest(HttpServletRequest req) {

	request = req;
	
	useragent = request.getHeader("User-Agent");
	
	String user = useragent.toLowerCase();
	
	if(user.indexOf("msie 6") != -1 || user.indexOf("msie 7") != -1 || user.indexOf("msie 8") != -1) {		
		oldIe = true;	
	} 
	
	else if(user.indexOf("msie 9") != -1 || user.indexOf("msie 1") != -1) {		
		newIe = true;	
	} 
	else if(user.indexOf("netscape6") != -1) {	
		ns6 = true;	
	}
	else if(user.indexOf("mozilla") != -1) {	
		ns4 = true;	
	}	
	else if(user.indexOf(".net clr") != -1)	{
		netEnabled = true;	
	}
	else if (user.indexOf("chrome") != -1) {
		chrome = true;
	}
	}
	
	public String getUseragent() {	
		return useragent;
	}
	
	public boolean isNetEnabled() {	
		return netEnabled;	
	}
	
	public boolean isOldIE() {
		return oldIe;	
	}
	
	public boolean isNewIE() {
		return newIe;	
	}
	
	public boolean isNS6() {	
		return ns6;	
	}
	
	public boolean isNS4() {	
		return ns4;	
	}

	public boolean isChrome() {
		return chrome;
	}
	
	

}
