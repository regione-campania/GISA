/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nosciagins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.extend.LoginRequiredException;

public class LineaAttivita {
	 
	private String desc_linea;
	private String codice_univoco_ml;
	private List<Metadato> metadati = new ArrayList<Metadato>();
	
	public LineaAttivita(String desc_linea, String codice_univoco_ml){
		this.setDesc_linea(desc_linea);
		this.setCodice_univoco_ml(codice_univoco_ml);
	}
	
	public void cercaMetadati(Connection db){
		try{
			
			//db = GestoreConnessioni.getConnection();
			String sql = "select * from public.cerca_metadato(?)";
	    	PreparedStatement st = db.prepareStatement(sql);
	        st.setString(1, this.codice_univoco_ml);
	        ResultSet rs = st.executeQuery();
	        
	        Metadato metadatoTemp1 = new Metadato();
	        metadatoTemp1.setDesc_linea(this.desc_linea);
	        metadatoTemp1.setCodice_univoco_ml(this.codice_univoco_ml);
	        metadati.add(metadatoTemp1);
	        
	        while(rs.next()){
	        	Metadato metadatoTemp = new Metadato();
	        	metadatoTemp.setId(rs.getInt("id"));
	        	metadatoTemp.setId_configuratore(rs.getInt("id_configuratore"));
	        	metadatoTemp.setCodice_univoco_ml(this.codice_univoco_ml);
	        	metadatoTemp.setDesc_linea("");
	        	metadatoTemp.setHtml_label_sezione(rs.getString("html_label_sezione"));
	        	metadatoTemp.setFtl_name(rs.getString("ftl_name"));
	        	metadatoTemp.setHtml_label(rs.getString("html_label"));
	        	metadatoTemp.setHtml_enabled(rs.getBoolean("enabled"));
	        	metadatoTemp.setSql_campo(rs.getString("sql_campo"));
	        	metadatoTemp.setSql_origine(rs.getString("sql_origine"));
	        	metadatoTemp.setSql_condizione(rs.getString("sql_condizione"));
	        	metadatoTemp.setHtml_ordine(rs.getString("html_ordine"));
	        	metadatoTemp.setHtml_type(rs.getString("html_type"));
	        	metadatoTemp.setSql_campo_lookup(rs.getString("sql_campo_lookup"));
	        	metadatoTemp.setSql_origine_lookup(rs.getString("sql_origine_lookup"));
	        	metadatoTemp.setSql_condizione_lookup(rs.getString("sql_condizione_lookup"));
	        	metadatoTemp.setHtml_name(rs.getString("html_name"));
	        	metadatoTemp.setMapping_field(rs.getString("mapping_field"));
	        	metadatoTemp.setOid_parent(rs.getInt("oid_parent"));
	        	metadatoTemp.setHtml_style(rs.getString("html_style"));
	        	metadatoTemp.setHtml_event(rs.getString("html_event"));
	        	metadatoTemp.getItems(db);
	        	metadati.add(metadatoTemp);
        }
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		finally
		{
			//GestoreConnessioni.freeConnection(db);
		}
	}

	public String getDesc_linea() {
		return desc_linea;
	}

	public void setDesc_linea(String desc_linea) {
		this.desc_linea = desc_linea;
	}

	public String getCodice_univoco_ml() {
		return codice_univoco_ml;
	}

	public void setCodice_univoco_ml(String codice_univoco_ml) {
		this.codice_univoco_ml = codice_univoco_ml;
	}

	public List<Metadato> getMetadati() {
		return metadati;
	}

	public void setMetadati(List<Metadato> metadati) {
		this.metadati = metadati;
	}

}
