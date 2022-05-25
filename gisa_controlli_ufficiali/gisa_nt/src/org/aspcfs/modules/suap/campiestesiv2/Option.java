/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.campiestesiv2;

import java.sql.Connection;

import org.json.JSONArray;
import org.json.JSONObject;

public class Option extends HtmlTag {
	
	public boolean isDefault;
	 
	
	public static Option build(JSONObject jo, int idRelStabLp, int idIstanzaValore, Connection conn) /*non mi servono in realtx idrelstablp e idistanza valore in questo caso */
	{
		Option op = new Option();
		 op.ordine = Integer.parseInt(getSafe(jo,"ordine") != null ? (String)getSafe(jo,"ordine") : "0");
		 op.isDefault = Boolean.parseBoolean(  getSafe(jo,"default") != null ? (String) getSafe(jo,"default") : "false");
		 op.valoreAssunto = (String) getSafe(jo,"valore");
		 op.label = (String)getSafe(jo,"label");
		 op.type = "option";
		 JSONArray arrayAttrs =  (JSONArray) getSafe(jo,"attrs");
		 JSONArray arrayProps =(JSONArray) getSafe(jo,"props");
		 op.attrs = Attr.buildList(arrayAttrs);
		 op.props = Prop.buildList(arrayProps);
		 
		 return op;
	}
	
	
	@Override
	public String apriTag() {

		StringBuffer toRet = new StringBuffer("<option ");
		 
		toRet.append("value = "+this.valoreAssunto);
		for(Attr attr : attrs)
		{
			toRet.append(attr.getHtml());
		}
		for(Prop prop : props)
		{
			toRet.append(prop.getHtml());
		}
		if(isDefault)
		{
			toRet.append(" selected");
		}
		toRet.append(" >");
		return toRet.toString();
	}

	@Override
	public String chiudiTag() {
		 
		return "</option>";
	}

	
	@Override
	public String generaHtml() {
		 

		String toRet = apriTag();
		toRet += (this.label);
		toRet += chiudiTag();
		return toRet;
	}
	
}
