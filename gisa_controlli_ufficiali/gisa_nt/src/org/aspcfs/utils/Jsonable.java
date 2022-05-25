/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Jsonable {
	public abstract JSONObject toJsonObject();
	public abstract String toJsonString();

	@Override
	public String toString() {
		return toJsonString();
	}
	
	public static String getListAsJsonArrayString(List toTransform)
	{
		JSONArray toRet = new JSONArray();
		for(Object o : toTransform)
		{
			toRet.put(((Jsonable)o).toJsonObject());
		}
		return toRet.toString();
	}
	
	public static String sanityString(String s)
	{
		if(s != null )
			return s.replace("'", " ");
		
		return s;
	}
}
