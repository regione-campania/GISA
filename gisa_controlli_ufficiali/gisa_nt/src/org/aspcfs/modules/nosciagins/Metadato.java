/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nosciagins;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aspcfs.utils.ConvertRsToHash;
import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.extend.LoginRequiredException;

public class Metadato implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//inserire campi configuratore template
	private Integer id;
	
	//campi relativi alla linea
	private String codice_univoco_ml;
	private String desc_linea;
	
	//campi relativi al gruppo
	private String html_label_sezione;
	private String ftl_name;
	
	//campi del metadato
	private Integer id_configuratore;
	private String html_label;
	private boolean html_enabled;
	private String sql_campo;
	private String sql_origine;
	private String sql_condizione;
	private String html_ordine;
	private String html_type;
	private String sql_campo_lookup;
	private String sql_origine_lookup;
	private String sql_condizione_lookup;
	private String html_name;
	private String mapping_field;
	private Integer oid_parent;
	private String html_style;
	private String html_event;	
	private List<Object> listaLookup = new ArrayList<>();
	
	public Metadato(){}
	
	public void getItems(Connection conn) {
		
		String queryLookup = "";
		String query = "";
	
		 if (this.html_type.equalsIgnoreCase("select") || this.html_type.equalsIgnoreCase("multiple"))
		 {
			 //System.out.println("costrutto per select o altri tipi lista");
			 queryLookup = generaQueryLookup(this.sql_campo_lookup , this.sql_origine_lookup , this.sql_condizione_lookup);
			 try {
				 //conn = GestoreConnessioni.getConnection();
				 PreparedStatement st = conn.prepareStatement(queryLookup);
				 //System.out.println(st);
		         ResultSet rs = st.executeQuery();
		         this.listaLookup = ConvertRsToHash.resultSetToHashMapLookup(rs); 
			 }catch(LoginRequiredException e)
				{
					throw e;
				}catch(Exception e)
				{
					e.printStackTrace();		
				}
				finally
				{
					//GestoreConnessioni.freeConnection(conn);
				}
			 
		 }
		
		if (this.html_type.equalsIgnoreCase("gruppo_calcolato") ||  this.html_type.equalsIgnoreCase("indirizzo") || 
				this.html_type.equalsIgnoreCase("coordinate") || this.html_type.equalsIgnoreCase("fiscale")){
			
			//System.out.println("gruppo calcolato, riga con piu elementi");
			try{
				//conn = GestoreConnessioni.getConnection();
				String sql = "select * from public.configuratore_template_no_scia where oid_parent=? order by html_ordine";
				PreparedStatement st = conn.prepareStatement(sql);
	            st.setInt(1, this.id_configuratore);
	            ResultSet rs = st.executeQuery();
	            
	            this.listaLookup = ConvertRsToHash.resultSetToHashMapLookup(rs);
			}catch(LoginRequiredException e)
			{
				throw e;
			}catch(Exception e)
			{
				e.printStackTrace();		
			}
			finally
			{
				//GestoreConnessioni.freeConnection(conn);
			}
		}
		
		if (this.html_type.equalsIgnoreCase("dati_linea_attivita")) {
			 //System.out.println("costrutto per macroarea aggregazione attivita");
			 query = "";
			 query = "SELECT " + this.sql_campo_lookup + " FROM ml8_linee_attivita_nuove_materializzata WHERE codice = ? limit 1";
			 try{
				 //conn = GestoreConnessioni.getConnection();
				 PreparedStatement st = conn.prepareStatement(query);
				 st.setString(1, this.codice_univoco_ml);
		         ResultSet rs = st.executeQuery();
		         this.listaLookup = ConvertRsToHash.resultSetToHashMapLookup(rs);
			 }catch(LoginRequiredException e)
				{
					throw e;
				}catch(Exception e)
				{
					e.printStackTrace();		
				}
				finally
				{
					//GestoreConnessioni.freeConnection(conn);
				}
		}
		
		
	}
	
	private String generaQueryLookup (String campo, String origine, String condizione)
    {
        String queryLookup = "";
        queryLookup = "SELECT "+campo+" FROM "+origine+" WHERE "+condizione;
        return queryLookup;
    }	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodice_univoco_ml() {
		return codice_univoco_ml;
	}

	public void setCodice_univoco_ml(String codice_univoco_ml) {
		this.codice_univoco_ml = codice_univoco_ml;
	}

	public String getDesc_linea() {
		return desc_linea;
	}

	public void setDesc_linea(String desc_linea) {
		this.desc_linea = desc_linea;
	}

	public String getHtml_label_sezione() {
		return html_label_sezione;
	}

	public void setHtml_label_sezione(String html_label_sezione) {
		this.html_label_sezione = html_label_sezione;
	}

	public String getFtl_name() {
		return ftl_name;
	}

	public void setFtl_name(String ftl_name) {
		this.ftl_name = ftl_name;
	}


	public String getHtml_label() {
		return html_label;
	}

	public void setHtml_label(String html_label) {
		this.html_label = html_label;
	}

	public boolean isHtml_enabled() {
		return html_enabled;
	}

	public void setHtml_enabled(boolean html_enabled) {
		this.html_enabled = html_enabled;
	}

	public String getSql_campo() {
		return sql_campo;
	}

	public void setSql_campo(String sql_campo) {
		this.sql_campo = sql_campo;
	}

	public String getSql_origine() {
		return sql_origine;
	}

	public void setSql_origine(String sql_origine) {
		this.sql_origine = sql_origine;
	}

	public String getSql_condizione() {
		return sql_condizione;
	}

	public void setSql_condizione(String sql_condizione) {
		this.sql_condizione = sql_condizione;
	}

	public String getHtml_ordine() {
		return html_ordine;
	}

	public void setHtml_ordine(String html_ordine) {
		this.html_ordine = html_ordine;
	}

	public String getHtml_type() {
		return html_type;
	}

	public void setHtml_type(String html_type) {
		this.html_type = html_type;
	}

	public String getSql_campo_lookup() {
		return sql_campo_lookup;
	}

	public void setSql_campo_lookup(String sql_campo_lookup) {
		this.sql_campo_lookup = sql_campo_lookup;
	}

	public String getSql_origine_lookup() {
		return sql_origine_lookup;
	}

	public void setSql_origine_lookup(String sql_origine_lookup) {
		this.sql_origine_lookup = sql_origine_lookup;
	}

	public String getSql_condizione_lookup() {
		return sql_condizione_lookup;
	}

	public void setSql_condizione_lookup(String sql_condizione_lookup) {
		this.sql_condizione_lookup = sql_condizione_lookup;
	}

	public String getHtml_name() {
		return html_name;
	}

	public void setHtml_name(String html_name) {
		this.html_name = html_name;
	}

	public String getMapping_field() {
		return mapping_field;
	}

	public void setMapping_field(String mapping_field) {
		this.mapping_field = mapping_field;
	}

	public Integer getOid_parent() {
		return oid_parent;
	}

	public void setOid_parent(Integer oid_parent) {
		this.oid_parent = oid_parent;
	}

	public String getHtml_style() {
		return html_style;
	}

	public void setHtml_style(String html_style) {
		this.html_style = html_style;
	}

	public String getHtml_event() {
		return html_event;
	}

	public void setHtml_event(String html_event) {
		this.html_event = html_event;
	}

	public List<Object> getListaLookup() {
		return listaLookup;
	}

	public void setListaLookup(List<Object> listaLookup) {
		this.listaLookup = listaLookup;
	}

	public Integer getId_configuratore() {
		return id_configuratore;
	}

	public void setId_configuratore(Integer id_configuratore) {
		this.id_configuratore = id_configuratore;
	}
}
