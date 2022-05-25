/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.masterlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.anagrafica_noscia.prototype.base_beans.LookupPair;
import com.google.gson.JsonObject;

public class Attivita {
	
	public Integer getIdLineaAttivita() {
		return idLineaAttivita;
	}
	public void setIdLineaAttivita(Integer idLineaAttivita) {
		this.idLineaAttivita = idLineaAttivita;
	}
	public Integer getIdAggregazione() {
		return idAggregazione;
	}
	public void setIdAggregazione(Integer idAggregazione) {
		this.idAggregazione = idAggregazione;
	}
	public String getCodiceProdottoSpecieLineaAttivita() {
		return codiceProdottoSpecieLineaAttivita;
	}
	public void setCodiceProdottoSpecieLineaAttivita(String codiceProdottoSpecieLineaAttivita) {
		this.codiceProdottoSpecieLineaAttivita = codiceProdottoSpecieLineaAttivita;
	}
	public String getLineaAttivita() {
		return lineaAttivita;
	}
	public void setLineaAttivita(String lineaAttivita) {
		this.lineaAttivita = lineaAttivita;
	}
	public String getTipoLineaAttivita() {
		return tipoLineaAttivita;
	}
	public void setTipoLineaAttivita(String tipoLineaAttivita) {
		this.tipoLineaAttivita = tipoLineaAttivita;
	}
	public String getSchedaSupplementareLineaAttivita() {
		return schedaSupplementareLineaAttivita;
	}
	public void setSchedaSupplementareLineaAttivita(String schedaSupplementareLineaAttivita) {
		this.schedaSupplementareLineaAttivita = schedaSupplementareLineaAttivita;
	}
	public String getNoteLineaAttivita() {
		return noteLineaAttivita;
	}
	public void setNoteLineaAttivita(String noteLineaAttivita) {
		this.noteLineaAttivita = noteLineaAttivita;
	}
	public String getMappingAtecoLineaAttivita() {
		return mappingAtecoLineaAttivita;
	}
	public void setMappingAtecoLineaAttivita(String mappingAtecoLineaAttivita) {
		this.mappingAtecoLineaAttivita = mappingAtecoLineaAttivita;
	}
	public String getCodiceUnivocoLineaAttivita() {
		return codiceUnivocoLineaAttivita;
	}
	public void setCodiceUnivocoLineaAttivita(String codiceUnivocoLineaAttivita) {
		this.codiceUnivocoLineaAttivita = codiceUnivocoLineaAttivita;
	}
	public String getCodiceNazionaleRichiestoLineaAttivita() {
		return codiceNazionaleRichiestoLineaAttivita;
	}
	public void setCodiceNazionaleRichiestoLineaAttivita(String codiceNazionaleRichiestoLineaAttivita) {
		this.codiceNazionaleRichiestoLineaAttivita = codiceNazionaleRichiestoLineaAttivita;
	}
	public String getChiInseriscePraticaLineaAttivita() {
		return chiInseriscePraticaLineaAttivita;
	}
	public void setChiInseriscePraticaLineaAttivita(String chiInseriscePraticaLineaAttivita) {
		this.chiInseriscePraticaLineaAttivita = chiInseriscePraticaLineaAttivita;
	}
	
	public Integer getIdLookupTipoAttivita() {
		return idLookupTipoAttivita;
	}
	public void setIdLookupTipoAttivita(Integer idLookupTipoAttivita) {
		this.idLookupTipoAttivita = idLookupTipoAttivita;
	}
	public void setDescTipoAttivita(String desc){this.descTipoAttivita = desc;}
	public String getDescTipoAttivita(){return this.getDescTipoAttivita();}
	
	private Integer idLineaAttivita;
	private String codiceProdottoSpecieLineaAttivita;
	private String lineaAttivita;
	private String tipoLineaAttivita;
	private String schedaSupplementareLineaAttivita;
	private String noteLineaAttivita;
	private String mappingAtecoLineaAttivita;
	private String codiceUnivocoLineaAttivita;
	private String codiceNazionaleRichiestoLineaAttivita;
	private String chiInseriscePraticaLineaAttivita;
	private Integer idAggregazione;
	private Integer idLookupTipoAttivita;
	private String descTipoAttivita; /*da id lookup tipo attivita */
	
	
	
	public Attivita(){}
	
	public Attivita(ResultSet rs, Connection conn) throws SQLException
	{
		setIdLineaAttivita(rs.getInt("id_linea_attivita"));
		setCodiceProdottoSpecieLineaAttivita(rs.getString("codice_prodotto_specie_linea_attivita"));
		setLineaAttivita(rs.getString("linea_attivita"));
		setTipoLineaAttivita(rs.getString("tipo_linea_attivita"));
		setSchedaSupplementareLineaAttivita(rs.getString("scheda_supplementare_linea_attivita"));
		setNoteLineaAttivita(rs.getString("note_linea_attivita"));
		setMappingAtecoLineaAttivita(rs.getString("mapping_ateco_linea_attivita"));
		setCodiceUnivocoLineaAttivita(rs.getString("codice_univoco_linea_attivita"));
		setCodiceNazionaleRichiestoLineaAttivita(rs.getString("codice_nazionale_richiesto_linea_attivita"));
		setChiInseriscePraticaLineaAttivita(rs.getString("chi_inserisce_pratica_linea_attivita"));
		setIdAggregazione(rs.getInt("id_aggregazione"));
		setIdLookupTipoAttivita(rs.getInt("id_lookup_tipo_attivita"));
		/*provo a risolvere desc tipo attivita */
		try
		{
			String descTipoAttivita = LookupPair.buildByCode(conn, "lookup_tipo_attivita", "code", "description", getIdLookupTipoAttivita(), "enabled = true").getDesc();
			setDescTipoAttivita(descTipoAttivita);
			
		}
		catch(Exception ex){System.out.println("-----Impossibile ottenere desc tipo attivita");}
	}
	
	public static Attivita getByOid(Integer oid, Connection conn) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		Attivita toRet = null;
		
		try
		{
			pst = conn.prepareStatement("select * from  MASTER_LIST_DENORM where id_linea_attivita = ?");
			pst.setInt(1, oid);
			rs = pst.executeQuery();
			rs.next();
			toRet = new Attivita(rs,conn);
			return toRet;
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
	
	public JSONObject asJSONOb()
	{

		JSONObject ob = new JSONObject();
		
		
		ob.put("desc", getLineaAttivita());
		ob.put("id", getIdLineaAttivita()+"");
		ob.put("figli", new JSONObject()); /*no figli oltre terzo livello per ora */
		ob.put("campi_estesi", new JSONObject()); /*non e' necessario popolarli qui */
		ob.put("id_lookup_tipo_attivita", getIdLookupTipoAttivita()+"");
		
		
		return ob;
	
	}
}
