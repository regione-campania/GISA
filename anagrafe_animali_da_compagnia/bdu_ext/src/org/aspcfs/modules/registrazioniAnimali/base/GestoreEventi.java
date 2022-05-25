/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

public class GestoreEventi {
	
	private static EventoList listaEventi;

	/**
	 * @return the listaEventi
	 */
	public static EventoList getListaEventi() {
		return listaEventi;
	}

	/**
	 * @param listaEventi the listaEventi to set
	 */
	public static void setListaEventi(EventoList listaEventiToAdd) {
		listaEventi = listaEventiToAdd;
	}
	
	public static void aggiungiEventi(Connection db){
		for (Object object : listaEventi){
			Evento e = (Evento) object;
			try {
				e.insert(db);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	
	public static ArrayList getFieldsByEventoId(Connection db, int idTipologia, int idSpecie, boolean includeHD, int idAsl, int idAnimale) throws SQLException{
		ArrayList fields = new ArrayList();
		StringBuffer sqlSelect = new StringBuffer();
		PreparedStatement pst = null;
		HashMap fields1 = new HashMap();
		String html = "";
		sqlSelect.append("SELECT * from evento_html_fields where id_tipologia_evento = ? ");
		
//		if (!includeHD){
//		
//			sqlSelect.append(" and (only_hd = null or only_hd = false)");
//		}
		
		sqlSelect.append(" order by ordine_campo");
		pst = db.prepareStatement(
		        sqlSelect.toString());
		
		pst.setInt(1, idTipologia);
		System.out.println("QUERY GET EVENTO HTML FIELDS: "+pst.toString());
		ResultSet rs = pst.executeQuery();
		
	      while (rs.next()) {
	    	  

	    	  	//CONTROLLO SE E' UN CAMPO SOLO PER UNA PARTICOLARE SPECIE ANIMALE E SE TALE SPECIE COINCIDE CON QUELLA PER CUI HO CHIAMATO
	    	  if ( !(rs.getInt("id_specie_only") > 0 ) || (rs.getInt("id_specie_only") > 0 && rs.getInt("id_specie_only") == idSpecie ) ){
	    		  if ((includeHD && rs.getInt("only_hd") != 2 ) || (!includeHD && rs.getInt("only_hd") != 1) ){
				 fields1 = new HashMap();
				 html = "";
				 fields1.put("name", rs.getString("nome_campo"));
				 fields1.put("type", rs.getString("tipo_campo"));
				 fields1.put("label", rs.getString("label_campo"));
				 fields1.put("controlli", rs.getString("tipo_controlli_date"));
				 fields1.put("label_data", rs.getString("label_campo_controlli_date"));
				 fields1.put("label_link", rs.getString("label_link"));
				 if (rs.getInt("maxlength") > 0){
					 fields1.put("size", rs.getInt("maxlength"));
				 }else{
					 fields1.put("size", 500);
				 }
				 
				 if (rs.getString("javascript_function") != null && rs.getString("javascript_function") != "" ){
					 fields1.put("javascript", rs.getString("javascript_function"));
				 }else{
					 fields1.put("javascript", "");
				 }
		    	  	if ("select".equals(rs.getString("tipo_campo"))){
		    	  		if ("comuni".equals(rs.getString("tabella_lookup")))
		    	  		{
		    	  				ComuniAnagrafica c = new ComuniAnagrafica();
		    					ArrayList<ComuniAnagrafica> listaComuni =  c.buildList(db, -1, -1);
		    					LookupList comuniList = new LookupList(listaComuni, -1);
		    					comuniList.addItem(-1, "--Seleziona--");
		    					if (rs.getString("javascript_function") != null && !("").equals(rs.getString("javascript_function")))
		    						comuniList.setJsEvent(rs.getString("javascript_function"));
		    					html = 	comuniList.getHtmlSelect(rs.getString("nome_campo"), -1);
		    	  		}else{
		    	  			LookupList lookuplist = new LookupList();
		    	  			 if (("elenco_veterinari_chippatori_with_asl_select_grouping").equals(rs.getString("tabella_lookup")))
		    	  				lookuplist.setIdAsl(idAsl);
		    	  			 
		    	  			if(idTipologia == EventoRientroFuoriRegione.idTipologiaDB && idAsl>0)
		    	  				lookuplist.setIdAslFiltro(idAsl);
		    	  			
		    	  			if((idTipologia == EventoMorsicatura.idTipologiaDB || idTipologia == EventoAggressione.idTipologiaDB) && idAsl>0 && ("elenco_veterinari_chippatori_with_asl_select_grouping").equals(rs.getString("tabella_lookup")))
		    	  				lookuplist.setIdAslFiltro(idAsl);
		    	  			
		    	  			lookuplist.setMultiple(rs.getBoolean("select_multiple"));
		    	  			lookuplist.setSelectSize(rs.getInt("select_size"));
		    	  			lookuplist.setTable(rs.getString("tabella_lookup"));
		    	  			if(rs.getBoolean("no_order"))
		    	  				lookuplist.buildList(db,false);
		    	  			else
		    	  				lookuplist.buildList(db);
		    				if((idTipologia != EventoRientroFuoriRegione.idTipologiaDB || idAsl<1) && !lookuplist.getMultiple())
		    	  			{
		    					lookuplist.addItem(-1, "--Seleziona--");
		    	  			}
		    				if((idTipologia == EventoMorsicatura.idTipologiaDB || idTipologia == EventoAggressione.idTipologiaDB) && rs.getString("tabella_lookup").equalsIgnoreCase("elenco_veterinari_chippatori"))
		    	  			{
		    					lookuplist.addItem(-1, "-- Selezionare un veterinario --");
		    	  			}
		    				if (rs.getString("javascript_function") != null && !("").equals(rs.getString("javascript_function")))
		    					lookuplist.setJsEvent(rs.getString("javascript_function"));
		    				if (("lookup_asl_rif").equals(rs.getString("tabella_lookup")) && !(idTipologia == EventoRientroFuoriRegione.idTipologiaDB) && !(idTipologia == EventoAdozioneFuoriAsl.idTipologiaDB)){
		    					lookuplist.remove(1); //Tolgo asl f. regione
		    				}
		    				if (("lookup_province").equals(rs.getString("tabella_lookup"))){
		    					lookuplist.removeElementByLevel(1); //Tolgo province non campane (level 1)
		    				}
		    			
		    				html = 	lookuplist.getHtmlSelect(rs.getString("nome_campo"), -1);
		    				
		    	  		}
		    	  		
		    	  	}
		    	  	else if("radio".equals(rs.getString("tipo_campo")))
		    	  	{
		    	  		Animale animale = new Animale(db,idAnimale);
		    	  		PreparedStatement pstRadio = db.prepareStatement(rs.getString("tabella_lookup"));
		    	  		pstRadio.setString(1, animale.getMicrochip());
		    	  		pstRadio.setString(2, animale.getTatuaggio());
		    	  		pstRadio.setString(3, animale.getMicrochip());
		    	  		pstRadio.setString(4, animale.getTatuaggio());
		    	  		pstRadio.setString(5, animale.getMicrochip());
		    	  		pstRadio.setString(6, animale.getTatuaggio());
			    		ResultSet rsRadio = pstRadio.executeQuery();
		    	  		while(rsRadio.next())
		    	  		{
		    	  			String htmlTemp = "<input type=\"radio\" name=\"" +  fields1.get("name")  + "\" id=\"" + fields1.get("name") + "\" value=\"" + rsRadio.getString("id") + "\">" + rsRadio.getString("description") + "</input><br/>";
		    	  			html +=htmlTemp;
		    	  		}
			    		
			    		pstRadio.close();
			    		rsRadio.close();
		    	  	}
		    	  	else if ("link".equals(rs.getString("tipo_campo"))){
		    	  		if (rs.getString("link_value") == null || "".equals(rs.getString("link_value")) ){
		    	  			html = "<a id=\""+rs.getString("nome_campo")+"\" href=\"javascript:"+rs.getString("javascript_function").replace("&idAnimale=", "&idAnimale="+idAnimale)+"\" >" +rs.getString("label_campo") + "</a>";
		    	  		}else{
		    	  			html = "<a id=\""+rs.getString("nome_campo")+"\" href=\""+rs.getString("link_value")+"\" >" +rs.getString("label_campo") + "</a>";
		    	  		}
		    	  		
		    	  	}
		    	  	else if ("jsp".equals(rs.getString("tipo_campo"))){
		    	  		
		    	  		html = "<%@ include file=\"" + rs.getString("valore_campo") + "\" %>";
		    	  		
		    	  		
		    	  	}
		    	 fields1.put("html", html);
		    	 fields1.put("value", rs.getString("valore_campo"));
				 fields.add(fields1);
	      }
	      }
	      }
	      rs.close();
	      pst.close();
		return fields;
	}

}
