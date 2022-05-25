/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.campiestesiv2;

import java.sql.Connection;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

import bsh.This;

public class TextField extends HtmlTag {

	
	
	
	@Override
	public String apriTag() {

		StringBuffer toRet = new StringBuffer("<input ");
		toRet.append("type = \""+this.type+"\" ");
		toRet.append("name = \""+this.name+"\" ");
		toRet.append("value = \""+this.valoreAssunto+"\" ");

		toRet.append("class = \"ordine-"+this.ordine+"\" ");
		
		for(Attr attr : attrs)
		{
			toRet.append(attr.getHtml());
		}
		for(Prop prop : props)
		{
			toRet.append(prop.getHtml());
		}
		toRet.append(" >");
		return toRet.toString();
	}

	
	@Override
	public String chiudiTag() {
		
		return "</input>";
	}
	
	
	public static TextField build(JSONObject jo, int idRelStabLp, int idIstanzaValore, Connection db ) throws Exception /*se idRelStabLp e' != -1 cerca di agganciargli il valore */
	{
		 TextField tf = new TextField();
		 tf.ordine = Integer.parseInt(getSafe(jo,"ordine") != null ? (String)getSafe(jo,"ordine") : "0");
		 tf.type = (String) getSafe(jo,"type");
		 tf.name = (String) getSafe(jo,"name");
		 tf.label = (String) getSafe(jo,"label");
//		 tf.injectHtml = (String) getSafe(jo,"label");
		 JSONArray arrayAttrs = (JSONArray) getSafe(jo,"attrs");
		 JSONArray arrayProps = (JSONArray) getSafe(jo,"props");
		 tf.attrs = Attr.buildList(arrayAttrs);
		 tf.props = Prop.buildList(arrayProps);
		 if(idRelStabLp > 0 && idIstanzaValore > 0)
		 {
			 tf.searchAndSetValue(db, idRelStabLp,idIstanzaValore);
		 }
		 
		 return tf;
		
	}
	 
	

}
