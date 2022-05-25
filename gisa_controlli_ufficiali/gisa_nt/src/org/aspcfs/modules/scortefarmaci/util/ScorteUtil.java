/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.scortefarmaci.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScorteUtil {
	
	public static String estraiDaPattern(String inizio, String fine, String stringa){
		if (stringa==null)
			return "";
		Pattern pattern;
		Matcher matcher; 
		pattern = Pattern.compile(inizio+"(.+?)"+fine, Pattern.DOTALL);
		matcher = pattern.matcher(stringa);
		matcher.find();
		try {return matcher.group(1);} catch (Exception e) {}
		return "";
	}
	
	public static String fixXmlResponse(String stringa){
		
		String output = "";
		
		if (stringa==null)
			return "";
		
		Matcher m = Pattern.compile("<(\\w+)(?:[>]|$)").matcher(stringa);
		while (m.find()) {
			String inizio = "<"+m.group(1)+">";
			String fine = "</"+m.group(1)+">";
			
			Pattern p = Pattern.compile(inizio+"(.+?)"+fine, Pattern.DOTALL);
			
			Matcher m2 = p.matcher(stringa);
			m2.find();
			
			String match = "";
			
			try {match = m2.group(1);} catch (Exception e) {}

			output = output + inizio + match + fine + "\n";
		}
		return output;
	}

}
