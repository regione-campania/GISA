/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.campiestesiv2;

import java.sql.Connection;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class RadioGroup extends HtmlTag{

	
	HashMap<String,String> scelte = new HashMap<String,String>(); /*[valore->label] */
	private String defaultValue;
	
	/*generazione particolare poiche' devo stampare tutte le possibili scelte, mettendo checked quella che ha come valore uno uguale a quello trovato sul db (se c'e' ) */
	@Override
	public String generaHtml() {
		 StringBuffer toRet = new StringBuffer("");
		 for(String valoreDistinto : scelte.keySet())
		 {
			 String labelScelta = scelte.get(valoreDistinto);
			 
			 /*e' il selected se non esiste un valore settato nel db e il valore in question ee' uguale a quello che sul db NEL TEMPLATe e' indicato come default
			  * oppure se il valore sul db e' settato ed e' uguale a quello che stiamo generando
			  */
			 boolean isSelected = ((this.valoreAssunto == null || this.valoreAssunto.trim().length() == 0) && (this.defaultValue != null && this.defaultValue.equals(valoreDistinto)))
					 				||
					 			valoreDistinto.equalsIgnoreCase(this.valoreAssunto);
			 
			 toRet.append( ( apriTag(valoreDistinto, isSelected) )+"&nbsp;"+labelScelta+"&nbsp;"+chiudiTag()  +"</br>" );
		 }
		 
		 return wrapIn("<div>",toRet.toString());
	}
	
	@Override
	public String apriTag()
	{
		return null;
	}
	
	public String apriTag(String valSpecifico, boolean checked) {

		StringBuffer toRet = new StringBuffer("<input ");
		toRet.append("type = \""+this.type+"\" ");
		toRet.append("name = \""+this.name+"\" ");
		toRet.append("value = \""+valSpecifico+"\" ");

		toRet.append("class = \"ordine-"+this.ordine+"\" ");
		toRet.append(checked ? " checked " : "" );
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
	
	
	public static RadioGroup build(JSONObject jo, int idRelStabLp, int idIstanzaValore,Connection db ) throws Exception /*se idRelStabLp e' != -1 cerca di agganciargli il valore */
	{
		RadioGroup tf = new RadioGroup();
		 tf.ordine = Integer.parseInt(getSafe(jo,"ordine") != null ? (String)getSafe(jo,"ordine") : "0");
		 tf.type = (String) getSafe(jo,"type");
		 tf.name = (String) getSafe(jo,"name");
		 JSONArray arrayAttrs = (JSONArray) getSafe(jo,"attrs");
		 JSONArray arrayProps = (JSONArray) getSafe(jo,"props");
		 tf.attrs = Attr.buildList(arrayAttrs);
		 tf.props = Prop.buildList(arrayProps);
		 
		 JSONArray scelteDb = jo.getJSONArray("scelte");
		 for(int i = 0; i< scelteDb.length(); i++)
		 {
			 JSONObject obScelta = scelteDb.getJSONObject(i);
			 String valore = obScelta.getString("valore");
			 String label = obScelta.getString("label");
			 if(getSafe(obScelta, "default") != null && getSafe(obScelta,"default") .equals( "true" ))
			 {
				 tf.defaultValue  = valore;
			 }
			 tf.scelte.put(valore,label);
		 }
		 
		 if(idRelStabLp > 0 && idIstanzaValore > 0)
		 {
			 tf.searchAndSetValue(db, idRelStabLp, idIstanzaValore);
		 }
		 
		 return tf;
		
	}
	 
	

}
