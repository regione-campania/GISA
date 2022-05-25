/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.util.StringTokenizer;

public class Utils {
	public static String[] tokenizerToArray(String stringToTokenize, String delimitator){
		StringTokenizer st = new StringTokenizer(stringToTokenize, delimitator);
		String[] array = new String[st.countTokens()]; 
		
		int i = 0;
		while(st.hasMoreTokens()){
			  String s=st.nextToken();
			  array[i] = s;
			  i++;
		}
		return array;
	} 
}
