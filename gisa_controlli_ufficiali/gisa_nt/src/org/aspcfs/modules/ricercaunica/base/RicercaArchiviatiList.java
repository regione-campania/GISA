/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.ricercaunica.base;

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

import com.darkhorseventures.framework.actions.ActionContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.global_search.base.OrganizationView;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;


/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @created    August 30, 2001
 * @version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class RicercaArchiviatiList extends Vector implements SyncableList {
	
	Logger logger = Logger.getLogger("MainLogger");

  private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.OrganizationList.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
  private String comuneSedeProduttiva = null;
  private String partitaIva =null;
  private String ragioneSociale = null;
  private String nomeSoggettoFisico =null;
  private String codiceFiscaleSoggettoFisico =null;
  private String indirizzoSedeProduttiva =null;
  private int idAsl = -1;
  private int idStato = -1;
  private String numeroRegistrazione = null;
  private String attivita = null;
  
  private int tipoRicerca = -1;
  private int tipoAttivita = -1;
  private String riferimentoIdnome ;
  private int riferimentoId ;
  private List<Integer> riferimentoList = new ArrayList<Integer>();

  private String riferimentoIdnomeCol ;
  private String riferimentoIdnomeTab ;
  
  
  public String getRiferimentoIdnomeCol() {
	return riferimentoIdnomeCol;
}


public void setRiferimentoIdnomeCol(String riferimentoIdnomeCol) {
	this.riferimentoIdnomeCol = riferimentoIdnomeCol;
}


public String getRiferimentoIdnomeTab() {
	return riferimentoIdnomeTab;
}


public void setRiferimentoIdnomeTab(String riferimentoIdnomeTab) {
	this.riferimentoIdnomeTab = riferimentoIdnomeTab;
}


public String getRiferimentoIdnome() {
	return riferimentoIdnome;
}


public void setRiferimentoIdnome(String riferimentoIdnome) {
	this.riferimentoIdnome = riferimentoIdnome;
}


public int getRiferimentoId() {
	return riferimentoId;
}


public void setRiferimentoId(int riferimentoId) {
	this.riferimentoId = riferimentoId;
}
protected PagedListInfo pagedListInfo = null;
  
  public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}


public String getComuneSedeProduttiva() {
	return comuneSedeProduttiva;
}

public void setComuneSedeProduttiva(String comuneSedeProduttiva) {
	this.comuneSedeProduttiva = comuneSedeProduttiva;
}

public String getPartitaIva() {
	return partitaIva;
}

public void setPartitaIva(String partitaIva) {
	this.partitaIva = partitaIva;
}

public String getRagioneSociale() {
	return ragioneSociale;
}

public void setRagioneSociale(String ragioneSociale) {
	this.ragioneSociale = ragioneSociale;
}

public String getCodiceFiscaleSoggettoFisico() {
	return codiceFiscaleSoggettoFisico;
}

public void setCodiceFiscaleSoggettoFisico(String codiceFiscaleSoggettoFisico) {
	this.codiceFiscaleSoggettoFisico = codiceFiscaleSoggettoFisico;
}

public String getIndirizzoSedeProduttiva() {
	return indirizzoSedeProduttiva;
}

public void setIndirizzoSedeProduttiva(String indirizzoSedeProduttiva) {
	this.indirizzoSedeProduttiva = indirizzoSedeProduttiva;
}

public int getIdAsl() {
	return idAsl;
}

public void setIdAsl(int idAsl) {
	this.idAsl = idAsl;
}

public void setIdAsl(String idAsl) {
	if (idAsl!=null && !idAsl.equals(""))
		this.idAsl = Integer.parseInt(idAsl);
}

public void buildList(Connection db) throws SQLException, IndirizzoNotFoundException {
	PreparedStatement pst = null;

	ResultSet rs = queryList(db, pst);
	while (rs.next()) {

		RicercaOpu thisopu = this.getObject(rs);
		thisopu.setTipoRicerca(tipoRicerca);
		//Nuovo controllo: distinct in java
		if (!this.riferimentoList.contains(thisopu.getRiferimentoId())){
			this.add(thisopu);
			this.riferimentoList.add(thisopu.getRiferimentoId());
		}

	    
	    
	}

	rs.close();
	if (pst != null) {
		pst.close();
	}
}

public void setPagedListInfo(PagedListInfo pagedListInfo) {
	this.pagedListInfo = pagedListInfo;
}


public RicercaOpu getObject(ResultSet rs) throws SQLException {
	  
	RicercaOpu st = new RicercaOpu() ;
	
	
	st.setRagionseSociale(rs.getString("ragione_sociale"));
	st.setPartitaIva(rs.getString("partita_iva"));
	st.setAsl(rs.getString("asl"));
	//st.setTipologia(rs.getInt("tipologia"));
	//st.setRiferimentoId(rs.getInt("org_id"));
	st.setRiferimentoId(rs.getInt("riferimento_id"));
	//st.setRiferimentoIdNome(rs.getString("riferimento_id_nome"));
	st.setNumeroRegistrazione(rs.getString("n_reg"));
	//st.setNumAut(rs.getString("num_aut"));
	st.setStato(rs.getString("stato"));
	//st.setStatoImpresa(rs.getString("stato_impresa"));
	//st.setAttivita(rs.getString("attivita"));
	
	st.setIndirizzoSedeProduttiva(rs.getString("indirizzo") + ", "+rs.getString("comune")  + ", "+rs.getString("provincia_stab"));
    try
    {
    	st.setCategoriaRischio(rs.getInt("categoria_rischio"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	st.setDataProssimoControllo(rs.getTimestamp("prossimo_controllo"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	st.setRiferimentoIdNomeCol(rs.getString("riferimento_id_nome_col"));
    }
    catch(Exception e)
    {
    	
    }
    try
    {
    	st.setRiferimentoIdNomeTab(rs.getString("riferimento_id_nome_tab"));
    }
    catch(Exception e)
    {
    	
    }
    
    
	return st;
  }

public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
	ResultSet rs = null;
	int items = -1;

	StringBuffer sqlSelect = new StringBuffer();
	StringBuffer sqlCount = new StringBuffer();
	StringBuffer sqlFilter = new StringBuffer();
	StringBuffer sqlOrder = new StringBuffer();
	
	String origine = "global_arch_view";

	sqlCount.append("select count (*) as recordcount "
			+ "from "+origine+" where tipo_ricerca_anagrafica = 3 ");
	createFilter(db, sqlFilter);

	if (pagedListInfo != null) {
		//Get the total number of records matching filter
		pst = db.prepareStatement(
				sqlCount.toString() +
				sqlFilter.toString());
		// UnionAudit(sqlFilter,db);
		items = prepareFilter(pst);


		rs = pst.executeQuery(); 
		if (rs.next()) {
			int maxRecords = rs.getInt("recordcount");
			pagedListInfo.setMaxRecords(maxRecords);
		}
		rs.close();
		pst.close();

		//Determine the offset, based on the filter, for the first record to show
		if (!pagedListInfo.getCurrentLetter().equals("")) {
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString() +
					"AND " + DatabaseUtils.toLowerCase(db) + "(ragione_sociale) < ? ");
			items = prepareFilter(pst);
			pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
			rs = pst.executeQuery();
			if (rs.next()) {
				int offsetCount = rs.getInt("recordcount");
				pagedListInfo.setCurrentOffset(offsetCount);
			}
			rs.close();
			pst.close();
		}

		 //sqlOrder.append(" order by ragione_sociale ASC ");
	      //Determine column to sort by
	      pagedListInfo.setColumnToSortBy(" ragione_sociale");
	      pagedListInfo.appendSqlTail(db, sqlOrder);
		
		//Optimize SQL Server Paging
		//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	} else {
		sqlOrder.append("");
	}

	//Need to build a base SQL statement for returning records


	sqlSelect.append("select * from "+origine+"  where tipo_ricerca_anagrafica = 3 ");
	pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	items = prepareFilter(pst);
	
	rs = pst.executeQuery();
	if (pagedListInfo != null) { 	 	
		pagedListInfo.doManualOffset(db, rs);
	}
	return rs;
}

protected void createFilter(Connection db, StringBuffer sqlFilter) 
{
	//andAudit( sqlFilter );
	if (sqlFilter == null) 
	{
		sqlFilter = new StringBuffer();
	}
	
	  if (ragioneSociale != null && !ragioneSociale.equals(""))
	    {
	    	sqlFilter.append(" and ragione_sociale ilike ? ");
	    }
	  
	  if (partitaIva != null && !partitaIva.equals(""))
	    {
	    	sqlFilter.append(" and partita_iva  ilike ? ");
	    }
		
	  if (idAsl>0 && tipoAttivita!=2)
		{
			sqlFilter.append(" and asl_rif = ? ");
		}

	  if (comuneSedeProduttiva != null && !comuneSedeProduttiva.equals(""))
	    {
	    	sqlFilter.append(" and comune ilike ? ");
	    }
   
	  if (indirizzoSedeProduttiva != null && !indirizzoSedeProduttiva.equals(""))
	    {
	    	sqlFilter.append(" and indirizzo ilike ? ");
	    }
	  
	  if (numeroRegistrazione != null && !numeroRegistrazione.equals(""))
	    {
	    	sqlFilter.append(" and n_reg ilike ? ");
	    }
	  
//	  if (codiceFiscaleSoggettoFisico != null && !codiceFiscaleSoggettoFisico.equals(""))
//	    {
//	    	sqlFilter.append(" and codice_fiscale_rappresentante ilike ? ");
//	    }
   
   
}
protected int prepareFilter(PreparedStatement pst) throws SQLException 
{
	int i = 0;
	
	  if (ragioneSociale != null && !ragioneSociale.equals(""))
	    {
	    	pst.setString(++i, ragioneSociale);
	    }
	  
	  if (partitaIva != null && !partitaIva.equals(""))
	    {
		   	pst.setString(++i, partitaIva);
	    }
		
	  if (idAsl>0 && tipoAttivita!=2)
		{
			pst.setInt(++i, idAsl) ;
		}
	  
	
	  
	  if (comuneSedeProduttiva != null && !comuneSedeProduttiva.equals(""))
	    {
		  pst.setString(++i, comuneSedeProduttiva);
	    }
	  
	  if (indirizzoSedeProduttiva != null && !indirizzoSedeProduttiva.equals(""))
	  {
		  pst.setString(++i, indirizzoSedeProduttiva) ;
	  }
	  
	  if (numeroRegistrazione != null && !numeroRegistrazione.equals(""))
	    {
			 pst.setString(++i, numeroRegistrazione);
	    }
//	 
//	  if (codiceFiscaleSoggettoFisico != null && !codiceFiscaleSoggettoFisico.equals(""))
//	    {
//			 pst.setString(++i, codiceFiscaleSoggettoFisico);
//	    }
   
	return i;
}


@Override
public String getTableName() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public String getUniqueField() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public void setLastAnchor(Timestamp arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void setLastAnchor(String arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void setNextAnchor(Timestamp arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void setNextAnchor(String arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void setSyncType(int arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void setSyncType(String arg0) {
	// TODO Auto-generated method stub
	
}


public String getNumeroRegistrazione() {
	return numeroRegistrazione;
}


public void setNumeroRegistrazione(String numeroRegistrazione) {
	this.numeroRegistrazione = numeroRegistrazione;
}


public String getNomeSoggettoFisico() {
	return nomeSoggettoFisico;
}


public void setNomeSoggettoFisico(String nomeSoggettoFisico) {
	this.nomeSoggettoFisico = nomeSoggettoFisico;
}


public String getAttivita() {
	return attivita;
}


public void setAttivita(String attivita) {
	this.attivita = attivita;
}


public int getTipoRicerca() {
	return tipoRicerca;
}


public void setTipoRicerca(int tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
}

public void setTipoRicerca(String tipoRicerca) {
	try {this.tipoRicerca = Integer.parseInt(tipoRicerca);}
	catch (Exception e){}
}


public int getTipoAttivita() {
	return tipoAttivita;
}


public void setTipoAttivita(int tipoAttivita) {
	this.tipoAttivita = tipoAttivita;
}

public void setTipoAttivita(String tipoAttivita) {
	try {this.tipoAttivita = Integer.parseInt(tipoAttivita);}
	catch (Exception e){}
}


public int getIdStato() {
	return idStato;
}

public void setIdStato(int idStato) {
	this.idStato = idStato;
}
public void setIdStato(String idStato) {
	try {
	this.idStato = Integer.parseInt(idStato);}
	catch (Exception e){}
}

}

  
  
  
  


