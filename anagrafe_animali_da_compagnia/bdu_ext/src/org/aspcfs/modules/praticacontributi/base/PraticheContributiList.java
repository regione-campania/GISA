/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.praticacontributi.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *  Description of the Class
 *
 * @author     kbhoopal
 * @created    January 8, 2004
 * @version    $Id: AssetList.java,v 1.1.6.1.2.3 2004/02/04 19:38:31 mrajkowski
 *      Exp $
 */
public class PraticheContributiList extends ArrayList<Pratica> {
	
	private static final long serialVersionUID = 1L;
  
	public static int TRUE = 1;
	public static int FALSE = 0;
	protected int includeEnabled = TRUE;
	private PagedListInfo pagedListInfo = null; 
	private boolean buildCompleteHierarchy = false;
 
	//Inizio filtri per le pratiche dei contributi
	private int asl = -1;
	private String descrizione = "";
	private int numero_decreto = -1;
	private Timestamp data_decreto;
	private Timestamp data_inizio_sterilizzazione;
	private Timestamp data_fine_sterilizzazione;
	private int totale_cani_catturati = 0;
	private int totale_cani_padronali = 0;
	//Fine filtri per le pratiche dei contributi
  
 
	//Inizio Costruttori
	public PraticheContributiList() { }

	public PraticheContributiList(Connection db) throws SQLException { }
  	//Fine Costruttori
  
  
	//Inizio metodi di SET e GET
	public int getIncludeEnabled() {
		return includeEnabled;
	}

	public void setIncludeEnabled(int includeEnabled) {
		this.includeEnabled = includeEnabled;
	}
  
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}

	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public boolean getBuildCompleteHierarchy() {
		return buildCompleteHierarchy;
	}

	public void setBuildCompleteHierarchy(boolean tmp) {
		this.buildCompleteHierarchy = tmp;
	}

	public void setBuildCompleteHierarchy(String tmp) {
		this.buildCompleteHierarchy = DatabaseUtils.parseBoolean(tmp);
	}
  

	public int getAsl() {
		return asl;
	}
	
	public void setAsl(int asl) {
		this.asl = asl;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public int getNumero_decreto() {
		return numero_decreto;
	}
	
	public void setNumero_decreto(int numero_decreto) {
		this.numero_decreto = numero_decreto;
	}
	
	public Timestamp getData_decreto() {
		return data_decreto;
	}
	
	public void setData_decreto(Timestamp data_decreto) {
		this.data_decreto = data_decreto;
	}
	
	public Timestamp getData_inizio_sterilizzazione() {
		return data_inizio_sterilizzazione;
	}
	
	public void setData_inizio_sterilizzazione(Timestamp data_inizio_sterilizzazione) {
		this.data_inizio_sterilizzazione = data_inizio_sterilizzazione;
	}
	
	public Timestamp getData_fine_sterilizzazione() {
		return data_fine_sterilizzazione;
	}
	
	public void setData_fine_sterilizzazione(Timestamp data_fine_sterilizzazione) {
		this.data_fine_sterilizzazione = data_fine_sterilizzazione;
	}

	public int getTotale_cani_catturati() {
		return totale_cani_catturati;
	}

	public void setTotale_cani_catturati(int totale_cani_catturati) {
		this.totale_cani_catturati = totale_cani_catturati;
	}

	public int getTotale_cani_padronali() {
		return totale_cani_padronali;
	}

	public void setTotale_cani_padronali(int totale_cani_padronali) {
		this.totale_cani_padronali = totale_cani_padronali;
	}

	public void buildListView(Connection db) throws SQLException  {

	  PreparedStatement pst = null;
	  ResultSet rs = null;
	  StringBuffer sqlSelect = new StringBuffer();
	  StringBuffer sqlFilter = new StringBuffer();
	  StringBuffer sqlCount = new StringBuffer();
	  StringBuffer sqlOrder = new StringBuffer();
	  
	  sqlCount.append(" select count(*) as recordcount from view_pratiche_contributi_ricerca WHERE TRUE ");
	  
	  //Gestire i filtri
	  createFilterView(db, sqlFilter);
	  
	  if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	     
	      prepareFilterView(pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	 
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	  }
	 
	  sqlSelect.append("SELECT * FROM view_pratiche_contributi_ricerca WHERE TRUE ");  
	    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    prepareFilterView(pst);
	    rs = pst.executeQuery();
	    Pratica pratica = null;
	    while (rs.next()) {
	      pratica = new Pratica();
	      pratica.setId(rs.getInt("id"));
	      pratica.setIdAslPratica(rs.getInt("asl"));
	      pratica.setDescrizioneAslPratica(rs.getString("descrizione"));
	      pratica.setNumeroDecretoPratica(rs.getInt("numero_decreto"));
	      pratica.setDataDecreto(rs.getTimestamp("data_decreto"));
	      pratica.setDataInizioSterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
	      pratica.setDataFineSterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));
	      pratica.setTotaleCaniCatturati(rs.getInt("numero_totale_cani_catturati"));
	      pratica.setTotaleGattiCatturati(rs.getInt("numero_totale_gatti_catturati"));
	      pratica.setTotaleCaniPadronali(rs.getInt("numero_totale_cani_padronali"));
	      pratica.setTotaleGattiPadronali(rs.getInt("numero_totale_gatti_padronali"));
	      pratica.setCaniRestantiCatturati(rs.getInt("numero_restante_cani_catturati"));
	      pratica.setGattiRestantiCatturati(rs.getInt("numero_restante_gatti_catturati")); 
	      pratica.setCaniRestantiPadronali(rs.getInt("numero_restante_cani_padronali"));
	      pratica.setGattiRestantiPadronali(rs.getInt("numero_restante_gatti_padronali"));
	      this.add(pratica);
	    }
	    rs.close();
	    pst.close();
	    
	  }

  
	  	
  private void prepareFilterView(PreparedStatement pst) throws SQLException {
	  	
	  	int i = 0;
	 
	 	//Filtro per Asl
	    if(asl != -1){
	    	pst.setInt(++i, asl);
	    }
	    
	    //Filtro per numero decreto
	    if(numero_decreto != -1){
	    	pst.setInt(++i, numero_decreto);
	    }
	    
	    //Filtro per data decreto
	    if(data_decreto != null){
	    	pst.setTimestamp(++i, data_decreto);
	    }
	    
	    //Filtro per data inizio sterilizzazione
	    if(data_inizio_sterilizzazione != null){
	    	pst.setTimestamp(++i, data_inizio_sterilizzazione);
	    }
	    
	    //Filtro per data fine sterilizzazione
	    if(data_fine_sterilizzazione != null){
	    	pst.setTimestamp(++i, data_fine_sterilizzazione);
	    }
	    
	    //Filtro per totale cani catturati
	    if(totale_cani_catturati > 0){
	    	pst.setInt(++i, totale_cani_catturati);
	    }
	    
	    //Filtro per totale cani padronali
	    if(totale_cani_padronali > 0){
	    	pst.setInt(++i, totale_cani_padronali);
	    }
	 	
	
  }

  private void createFilterView(Connection db, StringBuffer sqlFilter) {
	
	  	if (sqlFilter == null) {
	  		sqlFilter = new StringBuffer();
	    }

	    //Filtro per Asl
	    if(asl != -1){
	    	sqlFilter.append("AND asl = ? ");
	    }
	    
	    //Filtro per numero decreto
	    if(numero_decreto != -1){
	    	sqlFilter.append("AND numero_decreto ilike ? ");
	    }
	    
	    //Filtro per data decreto
	    if(data_decreto != null){
	    	sqlFilter.append("AND data_decreto >= ? ");
	    }
	    
	    //Filtro per data inizio sterilizzazione
	    if(data_inizio_sterilizzazione != null){
	    	sqlFilter.append("AND data_inizio_sterilizzazione >= ? ");
	    }
	    
	    //Filtro per data fine sterilizzazione
	    if(data_fine_sterilizzazione != null){
	    	sqlFilter.append("AND data_fine_sterilizzazione <= ? ");
	    }
	    
	    //Filtro per totale cani catturati
	    if(totale_cani_catturati > 0){
	    	sqlFilter.append("AND numero_totale_cani_catturati = ? ");
	    }
	    
	    //Filtro per totale cani padronali
	    if(totale_cani_padronali > 0){
	    	sqlFilter.append("AND numero_totale_cani_padronali = ? ");
	    }
  }

  
}

