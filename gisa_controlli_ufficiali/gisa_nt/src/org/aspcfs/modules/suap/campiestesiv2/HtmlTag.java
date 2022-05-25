/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.campiestesiv2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


public abstract class HtmlTag {

	public int ordine;
	protected ArrayList<Prop> props = new ArrayList<Prop>();
	protected ArrayList<Attr> attrs = new ArrayList<Attr>();
	public String name;
	protected String label;
	protected String type;
	protected String valoreAssunto = ""; /*quello della valorizzazione */
	public int idIstanzaValore ; 
	protected ArrayList<HtmlTag> childs = new ArrayList<HtmlTag>(); /*se volessimo innestarne*/
	 
	
	
	/*to implement*/
	public abstract String apriTag();
	public abstract String chiudiTag();
	public String wrapIn(String inWhat, String toWrap)
	{
		String closingInWhat = "</"+inWhat.substring(1);
		return inWhat+toWrap+closingInWhat;
	}
	
	protected void searchAndSetValue(Connection db, int idIstanzaLinea, int idIstanzaValore ) throws Exception
	{
		/*dato id istanza ritrova id linea master list, e data questa ritrova il template che e' unico. A questo punto
		 * usa i nomi dei campi per fare query sulla tabella dei values con quel nome campo e quell'id rel
		 * PER HP unica entry per quell'id rel stab lp, array di oggetti json, oggi oggetto json ha nome campo 
		 * PER HP ci puo' essere un nome campo che si ripete all'interno dell'array, nel caso di copie scadute e aggiornate
		 * QUESTO METODO PRENDE QUELLI NON SCADUTI
		 */
		
		/* per hp una istanza di linea ha un solo array di valori associati (poiche' un solo template). Quindi uso questa
		 * per ritrovare l'array. Uso il nome di questo campo per trovare, nell'array, quello che x l'ultimo valore valido (l'unico non scaduto) che per hp sara' sempre 1
		 * 
		 */
		String query = "select vals_linearizzati.value from campi_estesi_valori_v2 array_valori_per_template, json_array_elements(array_valori_per_template.valori_json) vals_linearizzati "+
						"where lower(vals_linearizzati->>'name') = ? and vals_linearizzati->>'data_scadenza' is  NULL "+
						"and array_valori_per_template.id_rel_stab_lp = ? and id = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			pst = db.prepareStatement(query);
			pst.setString(1,this.name.toLowerCase());
			pst.setInt(2, idIstanzaLinea);
			pst.setInt(3, idIstanzaValore);
			rs = pst.executeQuery();
			if(!rs.next())
				return;
			
			String ts = rs.getString(1); /*questo x il template come stringa json */
			
			this.idIstanzaValore = idIstanzaValore;
			
			JSONObject joCampo =new JSONObject(ts);
			this.valoreAssunto = (String) getSafe(joCampo,"value"); 
			this.valoreAssunto = this.valoreAssunto == null || this.valoreAssunto.equalsIgnoreCase("null") ? "" : this.valoreAssunto;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		
	}

	
	public static HtmlTag build (JSONObject jo, int idRelStabLp,  int idIstanzaValore, Connection db) throws Exception /*to override, se idRelStabLp e' != -1 allora cerca di agganciargli anche il valore con id uguale a quello dato*/
	{
		return null;
	}
	
 
	
	
	public String generaHtml()
	{

		String toRet = this.label != null ? this.label+"&nbsp;" : "";
		toRet += apriTag();
		for(HtmlTag tagFiglio : childs)
		{
			toRet += tagFiglio.generaHtml();
		}
		toRet += chiudiTag();
		
		toRet = wrapIn("<div>", toRet);
		
		return toRet;
		
	}
	
	
	/*perche' da eccezione se cerchiamo di estrarre quando non esiste la chiave, non ritorna neanche null */
	public static Object getSafe(JSONObject from, String key)
	{
		Object toRet = null;
		try
		{
			
			 toRet = from.get(key);
			
		}
		catch(Exception ex)
		{
			
		}
		return toRet;
	}
	
	 
	
}
