/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.riproduzioneanimale.base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.log4j.Logger;

import org.aspcfs.modules.molluschibivalvi.base.Concessionario;
import org.aspcfs.modules.molluschibivalvi.base.OrganizationAddressList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;


public class OrganizationStazioniList extends Vector{

	private static Logger log = Logger.getLogger(org.aspcfs.modules.riproduzioneanimale.base.OrganizationStazioniList.class);

	private int id ;
	private String provv_aut ;
	private String codice_legge_30;
	private String codice_azienda;
	private String tipo_attivita;
	private String sede;
	private String razza;
	private String scadenza_aut;		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvv_aut() {
		return provv_aut;
	}

	public void setProvv_aut(String provv_aut) {
		this.provv_aut = provv_aut;
	}

	public String getCodice_legge_30() {
		return codice_legge_30;
	}

	public void setCodice_legge_30(String codice_legge_30) {
		this.codice_legge_30 = codice_legge_30;
	}

	public String getCodice_azienda() {
		return codice_azienda;
	}

	public void setCodice_azienda(String codice_azienda) {
		this.codice_azienda = codice_azienda;
	}

	public String getTipo_attivita() {
		return tipo_attivita;
	}

	public void setTipo_attivita(String tipo_attivita) {
		this.tipo_attivita = tipo_attivita;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getRazza() {
		return razza;
	}

	public void setRazza(String razza) {
		this.razza = razza;
	}

	public String getScadenza_aut() {
		return scadenza_aut;
	}

	public void setScadenza_aut(String scadenza_aut) {
		this.scadenza_aut = scadenza_aut;
	}

	public int getOrg_id() {
		return org_id;
	}

	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}

	public boolean isMonta_equina_attive() {
		return monta_equina_attive;
	}

	public void setMonta_equina_attive(boolean monta_equina_attive) {
		this.monta_equina_attive = monta_equina_attive;
	}

	public boolean isStazione_inseminazione_equine() {
		return stazione_inseminazione_equine;
	}

	public void setStazione_inseminazione_equine(
			boolean stazione_inseminazione_equinde) {
		this.stazione_inseminazione_equine = stazione_inseminazione_equinde;
	}

	public boolean isMonta_bovine_attive() {
		return monta_bovina_attive;
	}

	public void setMonta_bovine_attive(boolean monta_bovine_attive) {
		this.monta_bovina_attive = monta_bovine_attive;
	}

	public boolean isCentro_produzione_sperma() {
		return centro_produzione_sperma;
	}

	public void setCentro_produzione_sperma(boolean centro_produzione_sperma) {
		this.centro_produzione_sperma = centro_produzione_sperma;
	}

	public boolean isCentro_produzione_embrioni() {
		return centro_produzione_embrioni;
	}

	public void setCentro_produzione_embrioni(boolean centro_produzione_embrioni) {
		this.centro_produzione_embrioni = centro_produzione_embrioni;
	}

	public boolean isGruppo_raccolta_embrioni() {
		return gruppo_raccolta_embrioni;
	}

	public void setGruppo_raccolta_embrioni(boolean gruppo_raccolta_embrioni) {
		this.gruppo_raccolta_embrioni = gruppo_raccolta_embrioni;
	}

	public boolean isRecapiti_autorizzati() {
		return recapiti_autorizzati;
	}

	public void setRecapiti_autorizzati(boolean recapiti_autorizzati) {
		this.recapiti_autorizzati = recapiti_autorizzati;
	}


	private int org_id;
	
	private boolean monta_equina_attive;
	private boolean stazione_inseminazione_equine;
	private boolean monta_bovina_attive;
	private boolean centro_produzione_sperma;
	private boolean centro_produzione_embrioni;
	private boolean gruppo_raccolta_embrioni;
	private boolean recapiti_autorizzati;
	
	
	protected PagedListInfo pagedListInfo = null;
	
	public Stazione getObject(ResultSet rs) throws SQLException {
		Stazione thisOrganization = new Stazione(rs);
		return thisOrganization;
	}
	
	
	public void buildList(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) 
	    {
	    	Stazione thisOrganization = this.getObject(rs);
	        this.add(thisOrganization);
	     
	    }
	 
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	   
	  }
	
	
	
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		sqlCount.append("SELECT  COUNT(distinct c.*) AS recordcount " +
						"FROM centri_riproduzione_animale c " +
						"left join organization o on (o.org_id = c.org_id) " +
						" WHERE o.org_id = ? and o.tipologia = 8 and o.trashed_date is null");
		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString());
			pst.setInt(1, org_id);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(sqlCount.toString());
				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
				rs = pst.executeQuery();
				if (rs.next()) 
				{
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("o.name");
			pagedListInfo.appendSqlTail(db, sqlOrder);
		} else 
		{
			sqlOrder.append("ORDER BY o.name ");
		}

		if (pagedListInfo != null) 
		{
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else 
		{
			sqlSelect.append("SELECT ");
		}
		sqlSelect.append(
		        "distinct c.* " +
		        "FROM centri_riproduzione_animale c "  + 
		        "LEFT JOIN organization o ON (o.org_id = c.org_id) " +
				"where tipologia = 8 and o.trashed_date is null and o.org_id = ?" );


		pst = db.prepareStatement(sqlSelect.toString());
		pst.setInt(1, org_id);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}
		rs = DatabaseUtils.executeQuery(db, pst, log);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}
	
			
			
}
