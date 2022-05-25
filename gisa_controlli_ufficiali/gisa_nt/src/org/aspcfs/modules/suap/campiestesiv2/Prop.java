/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.campiestesiv2;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Prop {
	private String prop;
	private Boolean value;
	
	public Prop(String prop, Boolean value)
	{
		this.prop = prop;
		this.value = value;
	}
	
	public String getHtml()
	{
		if(value == true)
		{
			return this.prop+" ";
		}
		return " ";
	}
	
	public static  ArrayList<Prop> buildList(JSONArray ja)
	{
		ArrayList<Prop> toRet = new ArrayList<Prop>();
		
		if(ja != null)
		{
			for(int i = 0; i< ja.length();i++)
			{
				JSONObject jo = (JSONObject) ja.get(i);
				/* per hp deve essere un {attr name : attrvalue} */
				String nomeAttr = (String)jo.keys().next();
				Boolean valueAttr = Boolean.parseBoolean((String)jo.getString(nomeAttr));
			
				toRet.add(new Prop(nomeAttr,valueAttr));
			}
		}
		return toRet;
	}
}
