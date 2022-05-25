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
public class RicercaList extends Vector implements SyncableList {
	
	Logger logger = Logger.getLogger("MainLogger");

  private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.OrganizationList.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
  private boolean ricercaUnicaSpostamentoCu;
  
  
  private String numeroRiconoscimento;
  private String comuneSedeProduttiva = null;
  private String partitaIva =null;
  private String codiceFiscale =null;
  private String ragioneSociale = null;
  private String nomeSoggettoFisico =null;
  private String codiceFiscaleSoggettoFisico =null;
  private String indirizzoSedeProduttiva =null;
  private int idAsl = -1;
  private int idStato = -1;
  private String numeroRegistrazione = null;
  private String attivita = null;
  private int idAttivita = -1;
  
  private int tipoRicerca = -1;
  private int tipoAttivita = -1;
  private String riferimentoIdnome ;
  private int riferimentoId ;
  private List<Integer> riferimentoList = new ArrayList<Integer>();
  
  private int tipologiaOperatore ;
  private String riferimentoIdnomeCol ;
  private String riferimentoIdnomeTab ;
  private int idTipoLineaProduttiva ;
  private int idNorma ;
  private String targa;
  private String matricola;

  
  private int categoriaRischio ;



private ArrayList<Integer> tipologiaAnagraficheVersoCuiConvergere = new ArrayList<Integer>();



  
  public ArrayList<Integer> getTipologiaAnagraficheVersoCuiConvergere() {
	return tipologiaAnagraficheVersoCuiConvergere;
}


public void setTipologiaAnagraficheVersoCuiConvergere(ArrayList<Integer> tipologiaAnagraficheVersoCuiConvergere) {
	this.tipologiaAnagraficheVersoCuiConvergere = tipologiaAnagraficheVersoCuiConvergere;
}

  public String getTarga() {
  return targa;
  }
  
  

  	
  	public int getIdNorma() {
	return idNorma;
}

  	



public int getTipologiaOperatore() {
		return tipologiaOperatore;
	}




	public void setTipologiaOperatore(int tipologiaOperatore) {
		this.tipologiaOperatore = tipologiaOperatore;
	}



	public void setTipologiaOperatore(String tipologiaOperatore) {
		if(tipologiaOperatore!=null && !"".equals(tipologiaOperatore))
		this.tipologiaOperatore = Integer.parseInt(tipologiaOperatore);
	}



public boolean isRicercaUnicaSpostamentoCu() {
		return ricercaUnicaSpostamentoCu;
	}




	public void setRicercaUnicaSpostamentoCu(boolean ricercaUnicaSpostamentoCu) {
		this.ricercaUnicaSpostamentoCu = ricercaUnicaSpostamentoCu;
	}




public void setIdNorma(int idNorma) {
	this.idNorma = idNorma;
}


public void setIdNorma(String idNorma) {
	
	if(idNorma!=null && !"".equals(idNorma))
	this.idNorma = Integer.parseInt(idNorma);
}




	public void setTarga(String targa) {
		if(targa!=null)
			this.targa=targa.trim();
  }

  
  private String codiceAllerta = "";
  private String statoCu ;
  
  public String getStatoCu() {
		return statoCu;
	}

	public void setStatoCu(String statoCu) {
		this.statoCu = statoCu;
	}
	
  public String getCodiceAllerta() {
	return codiceAllerta;
}

public void setCodiceAllerta(String codiceAllerta) {
	this.codiceAllerta = codiceAllerta;
}
  
  public int getIdTipoLineaProduttiva() {
	return idTipoLineaProduttiva;
}
  public void setIdTipoLineaProduttiva(String idTipoLineaProduttiva) {
	  if(idTipoLineaProduttiva!=null && !"".equals(idTipoLineaProduttiva))
		this.idTipoLineaProduttiva = Integer.parseInt(idTipoLineaProduttiva);
	}

public void setIdTipoLineaProduttiva(int idTipoLineaProduttiva) {
	this.idTipoLineaProduttiva = idTipoLineaProduttiva;
}


public String getNumeroRiconoscimento() {
	return numeroRiconoscimento;
}


public void setNumeroRiconoscimento(String numeroRiconoscimento) {
	if(numeroRiconoscimento!=null)
		this.numeroRiconoscimento = numeroRiconoscimento.trim();
}


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
	if(comuneSedeProduttiva!=null)
	this.comuneSedeProduttiva = comuneSedeProduttiva.trim();
}

public String getPartitaIva() {
	return partitaIva;
}

public void setPartitaIva(String partitaIva) {
	if(partitaIva!=null)
	this.partitaIva = partitaIva.trim();
}

public String getRagioneSociale() {
	return ragioneSociale;
}

public void setRagioneSociale(String ragioneSociale) {
	if(ragioneSociale!=null)
	this.ragioneSociale = ragioneSociale.trim();
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
		this.add(thisopu);
//		if (!this.riferimentoList.contains(thisopu.getRiferimentoId())){
//			this.add(thisopu);
//			this.riferimentoList.add(thisopu.getRiferimentoId());
//		}

	    
	    
	}

	rs.close();
	if (pst != null) {
		pst.close();
	}
}

public void buildListNoScia(Connection db) throws SQLException, IndirizzoNotFoundException {
	PreparedStatement pst = null;

	ResultSet rs = queryListNoScia(db, pst);
	while (rs.next()) {

		RicercaOpu thisopu = this.getObject(rs);
		thisopu.setTipoRicerca(tipoRicerca);//?
		//Nuovo controllo: distinct in java
		this.add(thisopu);
//		if (!this.riferimentoList.contains(thisopu.getRiferimentoId())){
//			this.add(thisopu);
//			this.riferimentoList.add(thisopu.getRiferimentoId());
//		}

	    
	    
	}

	rs.close();
	if (pst != null) {
		pst.close();
	}
}

public void buildListAllevamenti(Connection db) throws SQLException, IndirizzoNotFoundException {
	PreparedStatement pst = null;

	ResultSet rs = queryListAllevamenti(db, pst);
	while (rs.next()) {

		RicercaOpu thisopu = this.getObject(rs);
		thisopu.setTipoRicerca(tipoRicerca);
		//Nuovo controllo: distinct in java
		this.add(thisopu);
//		if (!this.riferimentoList.contains(thisopu.getRiferimentoId())){
//			this.add(thisopu);
//			this.riferimentoList.add(thisopu.getRiferimentoId());
//		}

	    
	    
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
	
	st.setIdNorma(rs.getInt("id_norma"));
	st.setRagionseSociale(rs.getString("ragione_sociale"));
	st.setPartitaIva(rs.getString("partita_iva"));
	st.setCodiceFiscale (rs.getString("codice_fiscale"));
	st.setAsl(rs.getString("asl"));
	st.setTipologia(rs.getInt("tipologia_operatore"));
	st.setRiferimentoId(rs.getInt("riferimento_id"));
	st.setRiferimentoIdNome(rs.getString("riferimento_id_nome"));
	st.setNorma(rs.getString("norma"));
	st.setNumeroRegistrazione(rs.getString("n_reg"));
	st.setNumAut(rs.getString("num_riconoscimento"));
	st.setN_linea(rs.getString("n_linea"));
	st.setStato(rs.getString("stato"));
//	st.setStatoImpresa(rs.getString("stato_impresa"));
	st.setAttivita(rs.getString("attivita"));
	
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
        st.setN_Regold(rs.getString("n_reg_old"));
    }
    catch(Exception e)
    {
    	
    }
    try
    {
        st.setIdAsl(rs.getInt("asl_rif"));
    }
    catch(Exception e)
    {
    	
    }
    
    
    try
    {
        st.setComuneSedeProduttiva(rs.getString("comune"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
        st.setComuneSedeLegale(rs.getString("comune_leg"));
    }
    catch(Exception e)
    {
    	
    }
	
    
    
    try
    {
    	st.setMacroarea(rs.getString("macroarea"));
    }
    catch(Exception e)
    {
    	
    }
    
    
    try
    {
    	st.setMacroarea(rs.getString("aggregazione"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	st.setMacroarea(rs.getString("attivita"));
    }
    catch(Exception e)
    {
    	
    }
    try
    {
    	st.setId_tipo_attivita(rs.getInt("tipo_attivita"));
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
    
    try
    {
    	st.setTipoAttivita(rs.getString("tipo_attivita_descrizione"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	st.setTarga(rs.getString("targa"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	st.setMatricola(rs.getString("matricola"));
    }
    catch(Exception e)
    {
    	
    }
    
    
    try
    {
    	st.setTipoRicerca(rs.getInt("tipo_ricerca_anagrafica"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	st.setColor(rs.getString("color"));
    }
    catch(Exception e)
    {
    	
    }
    
    try
    {
    	st.setCodiceFiscaleSoggettoFisico(rs.getString("codice_fiscale_rappresentante"));
    }
    catch(Exception e)
    {
    	
    }
    
    
    
    
	return st;
  }





public ResultSet queryListAllevamenti(Connection db, PreparedStatement pst) throws SQLException {
	ResultSet rs = null;
	int items = -1;
	
	StringBuffer sqlSelect = new StringBuffer();
	StringBuffer sqlCount = new StringBuffer();
	StringBuffer sqlFilter = new StringBuffer();
	StringBuffer sqlOrder = new StringBuffer();
	
	
	
	String origine = "ricerche_anagrafiche_old_materializzata_allevamenti";
	
	
	
	
	
	
	
	//sqlCount.append("select count (distinct riferimento_id) as recordcount from "+origine+" o");
	sqlCount.append("select count( o.*) as recordcount from "+origine+" o");
	
	if(codiceAllerta != null){
    	if(!codiceAllerta.equals("")){
    		sqlCount.append(" join ticket cufficiali on o.riferimento_id = cufficiali.id_stabilimento and cufficiali.codice_allerta ilike '"+codiceAllerta+"' and cufficiali.tipologia=3"); 
    		
    	}
    	
    }
	
	sqlCount.append(" where 1=1  ");
	createFilter(db, sqlFilter);

	if (pagedListInfo != null) {
		//Get the total number of records matching filter
		pst = db.prepareStatement(
				sqlCount.toString() +
				sqlFilter.toString());
		// UnionAudit(sqlFilter,db);
		items = prepareFilter(pst);
   
		
//		if (idAsl!=-1 && tipoAttivita==-1 && tipoRicerca!=0){
			// Se la ricerca e' per tutti (tipoAttivita==-1) e l'utente ha l'asl bloccata (idAsl!=-1), si fa la union con 
			// tutte le attivita' mobili anche NON appartenenti all'asl dell'utente
//			pst=db.prepareStatement("select sum(t.recordcount) as recordcount from ( "+pst.toString()+" union "+
//			pst.toString().replace("asl_rif =", "asl_rif !=")+" and tipo_attivita=2 ) as t");
//		}

		rs = pst.executeQuery(); 
		if (rs.next()) {
			int maxRecords = rs.getInt("recordcount");
			pagedListInfo.setMaxRecords(maxRecords);
		}
		rs.close();
		pst.close();

		//Determine the offset, based on the filter, for the first record to show
		

	      //Determine column to sort by
	      pagedListInfo.setColumnToSortBy("id_norma,asl_rif,ragione_sociale");
	      pagedListInfo.appendSqlTail(db, sqlOrder);
		
		//Optimize SQL Server Paging
		//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	} else {
		sqlOrder.append(" order by id_norma,asl_rif,ragione_sociale  ");
	}

	//Need to build a base SQL statement for returning records


	if(ricercaUnicaSpostamentoCu==true)
		sqlSelect.append("select distinct on (riferimento_id) riferimento_id as rif_id,* from "+origine+"  where 1=1 ");
	else
		sqlSelect.append("select * from "+origine+"  where 1=1 ");
	

	
	if(ricercaUnicaSpostamentoCu==true)
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() );
	else
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	
		
		items = prepareFilter(pst);


	
	
	rs = pst.executeQuery();
	if (pagedListInfo != null) { 	 	
		pagedListInfo.doManualOffset(db, rs);
	}
	return rs;
}


public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
	ResultSet rs = null;
	int items = -1;
	
	StringBuffer sqlSelect = new StringBuffer();
	StringBuffer sqlCount = new StringBuffer();
	StringBuffer sqlFilter = new StringBuffer();
	StringBuffer sqlOrder = new StringBuffer();
	
	
	
	String origine = "ricerche_anagrafiche_old_materializzata";
	
	
	
	
	
	
	
	//sqlCount.append("select count (distinct riferimento_id) as recordcount from "+origine+" o");
	sqlCount.append("select count( o.*) as recordcount from "+origine+" o");
	
	if(codiceAllerta != null){
    	if(!codiceAllerta.equals("")){
    		sqlCount.append(" join ticket cufficiali on o.riferimento_id = cufficiali.id_stabilimento and cufficiali.codice_allerta ilike '"+codiceAllerta+"' and cufficiali.tipologia=3"); 
    		
    	}
    	
    }
	
	sqlCount.append(" where 1=1  ");
	createFilter(db, sqlFilter);

	if (pagedListInfo != null) {
		//Get the total number of records matching filter
		pst = db.prepareStatement(
				sqlCount.toString() +
				sqlFilter.toString());
		// UnionAudit(sqlFilter,db);
		items = prepareFilter(pst);
   
		
//		if (idAsl!=-1 && tipoAttivita==-1 && tipoRicerca!=0){
			// Se la ricerca e' per tutti (tipoAttivita==-1) e l'utente ha l'asl bloccata (idAsl!=-1), si fa la union con 
			// tutte le attivita' mobili anche NON appartenenti all'asl dell'utente
//			pst=db.prepareStatement("select sum(t.recordcount) as recordcount from ( "+pst.toString()+" union "+
//			pst.toString().replace("asl_rif =", "asl_rif !=")+" and tipo_attivita=2 ) as t");
//		}

		rs = pst.executeQuery(); 
		if (rs.next()) {
			int maxRecords = rs.getInt("recordcount");
			pagedListInfo.setMaxRecords(maxRecords);
		}
		rs.close();
		pst.close();

		//Determine the offset, based on the filter, for the first record to show
		

	      //Determine column to sort by
	      pagedListInfo.setColumnToSortBy(" tipo_attivita, asl_rif,ragione_sociale, id_norma "); 
	      pagedListInfo.appendSqlTail(db, sqlOrder);
		
		//Optimize SQL Server Paging
		//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	} else {
		sqlOrder.append(" order by tipo_attivita, asl_rif,ragione_sociale, id_norma ");
	}

	//Need to build a base SQL statement for returning records


	if(ricercaUnicaSpostamentoCu==true)
		sqlSelect.append("select distinct on (riferimento_id) riferimento_id as rif_id,* from "+origine+" o  where 1=1 ");
	else
		sqlSelect.append("select * from "+origine+" o where 1=1 ");
	

	
	if(ricercaUnicaSpostamentoCu==true)
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() );
	else
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	
		
		items = prepareFilter(pst);


	
	log.info("QUERY RICERCA : "+pst.toString());
	rs = pst.executeQuery();
	if (pagedListInfo != null) { 	 	
		pagedListInfo.doManualOffset(db, rs);
	}
	return rs;
}


public ResultSet queryListNoScia(Connection db, PreparedStatement pst) throws SQLException {
	ResultSet rs = null;
	int items = -1;
	
	StringBuffer sqlSelect = new StringBuffer();
	StringBuffer sqlCount = new StringBuffer();
	StringBuffer sqlFilter = new StringBuffer();
	StringBuffer sqlOrder = new StringBuffer();
	
	
	
	String origine = "ricerche_anagrafiche_old_materializzata";
	sqlCount.append("select count( o.*) as recordcount from "+origine+" o");
	
	
	
	sqlCount.append(" where 1=1 and id_attivita in"
			+ " (select id_linea from master_list_flag_linee_attivita  where no_scia and codice_univoco <> 'OPR-OPR-X')");

	createFilter(db, sqlFilter,true);

	if (pagedListInfo != null) {
		//Get the total number of records matching filter
		pst = db.prepareStatement(
				sqlCount.toString() +
				sqlFilter.toString());
		// UnionAudit(sqlFilter,db);
		items = prepareFilter(pst,true);
   

		rs = pst.executeQuery(); 
		if (rs.next()) {
			int maxRecords = rs.getInt("recordcount");
			pagedListInfo.setMaxRecords(maxRecords);
		}
		rs.close();
		pst.close();

		//Determine the offset, based on the filter, for the first record to show
		

	      //Determine column to sort by
	      pagedListInfo.setColumnToSortBy(" tipo_attivita, asl_rif,ragione_sociale, id_norma "); 
	      pagedListInfo.appendSqlTail(db, sqlOrder);
		
		//Optimize SQL Server Paging
		//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	} else {
		sqlOrder.append(" order by tipo_attivita, asl_rif,ragione_sociale, id_norma ");
	}

	//Need to build a base SQL statement for returning records

	sqlSelect.append("select * from "+origine+" o where 1=1 and id_attivita in"
			+ " (select id_linea from master_list_flag_linee_attivita  where no_scia and codice_univoco <> 'OPR-OPR-X') ");
 
	pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	items = prepareFilter(pst,true);

	rs = pst.executeQuery();
	if (pagedListInfo != null) { 	 	
		pagedListInfo.doManualOffset(db, rs);
	}
	return rs;
}


protected void createFilter(Connection db, StringBuffer sqlFilter) 
{
	createFilter(db, sqlFilter,false) ;
}

protected void createFilter(Connection db, StringBuffer sqlFilter,boolean ricercaNoScia) 
{
	//andAudit( sqlFilter );
	if (sqlFilter == null) 
	{
		sqlFilter = new StringBuffer();
	}
	
	
	if(tipologiaAnagraficheVersoCuiConvergere.size()>0)
	{
		String valoriAmmessi = "" ;
		
		for(Object tipologiaDestinazione:tipologiaAnagraficheVersoCuiConvergere)
		{
			valoriAmmessi+=tipologiaDestinazione+",";
		}
		valoriAmmessi+="-1";
		
		
		sqlFilter.append(" and tipologia_operatore in ("+valoriAmmessi+") ");
	}
	else
	
	if(tipologiaOperatore>0)
	{
		sqlFilter.append(" and tipologia_operatore= "+tipologiaOperatore);

	}
	if(tipoRicerca>=0)
	{
		sqlFilter.append(" and tipo_ricerca_anagrafica= "+tipoRicerca);
	}
	if(targa!= null && !"".equals(targa))
	{
		sqlFilter.append(" and targa::text ilike ? ");
	}
	if(matricola!= null && !"".equals(matricola))
	{
		sqlFilter.append(" and matricola::text ilike ? ");
	}
	if (idNorma>0)
	{
		sqlFilter.append(" and id_norma=  "+idNorma);
	}
	
	if (categoriaRischio>0)
	{
		sqlFilter.append(" and o.categoria_rischio=  "+categoriaRischio);
	}
	if (idTipoLineaProduttiva>0)
	{
		sqlFilter.append(" and id_tipo_linea_reg_ric=  "+idTipoLineaProduttiva);
	}
	if(numeroRiconoscimento!= null && !"".equals(numeroRiconoscimento))
	{
		sqlFilter.append(" and (num_riconoscimento::text ilike ? or n_linea::text ilike ?) ");
	}
	
	if(riferimentoIdnomeCol!=null && ! riferimentoIdnomeCol.equals("") && riferimentoId>0)
	{
		sqlFilter.append(" and riferimento_id_nome_col::text ilike ? and riferimento_id =?");
	}
	  if (ragioneSociale != null && !ragioneSociale.equals(""))
	    {
	    	sqlFilter.append(" and ragione_sociale ilike ? ");
	    }
	  
	  if (partitaIva != null && !partitaIva.equals(""))
	    {
	    	sqlFilter.append(" and partita_iva  ilike ? ");
	    }
	  if (codiceFiscale != null && !codiceFiscale.equals(""))
	    {
	    	sqlFilter.append(" and codice_fiscale  ilike ? ");
	    }
		
	  if (idAsl>0 && ricercaUnicaSpostamentoCu==false && tipoRicerca!=3)
		{
		    /*modifica 12/07/2017 Davide : gli apicoltori hanno tipo_attivita = -1 quando vengono da ricerche_anagrafiche_old_materializzata */
//			sqlFilter.append(" and  (( asl_rif = ? and (tipo_attivita is null or tipo_attivita=1)) or  tipo_attivita=2 or (id_norma=27 ) ) ");
		    if(idNorma != 17)
		    	sqlFilter.append(" and  ((( asl_rif = ? or asl_rif =-1) and (tipo_attivita is null or tipo_attivita = -1 or tipo_attivita=1)) or  (tipo_attivita=2) or tipo_attivita=4 ) ");
		    else /*per apicoltura*/
		    	sqlFilter.append(" and  asl_rif = ? ");
		}
	  else
	  {
		  if (idAsl>0)
		  sqlFilter.append(" and  asl_rif = ? ");
	  }
	  if(tipoAttivita>0)
	  {
		  sqlFilter.append(" and  tipo_attivita = ? ");
		  
	  }
	  if (idStato>-1)
		{
			sqlFilter.append(" and (case when riferimento_id_nome_tab = 'sintesis_stabilimento' then id_stato in (select id_lookup_stato_attivita_sintesis from mapping_lookup_stato_lab_stato_attivita_sintesis where id_lookup_stato_lab = ?) else id_stato = ? end) ");
		}
	  if (tipoAttivita>0)
		{
			sqlFilter.append(" and tipo_attivita = ? ");
		}
	  if (comuneSedeProduttiva != null && !comuneSedeProduttiva.equals(""))
	    {
	    	sqlFilter.append(" and comune ilike ? ");
	    }
   
	  if (indirizzoSedeProduttiva != null && !indirizzoSedeProduttiva.equals(""))
	    {
		 
	    	sqlFilter.append(" and  (levenshtein(lower(coalesce(indirizzo,'')),lower(?))<=5 or indirizzo ilike ?) ");
	    }
	  
	  if (numeroRegistrazione != null && !numeroRegistrazione.equals(""))
	    {
	    	sqlFilter.append(" and (n_reg ilike ? or n_reg_old ilike ?) ");
	    }
	  
	  if (nomeSoggettoFisico != null && !nomeSoggettoFisico.equals(""))
	    {
	    	sqlFilter.append(" and nominativo_rappresentante ilike ?");
	    }
	  
	 
	  if (codiceFiscaleSoggettoFisico != null && !codiceFiscaleSoggettoFisico.equals(""))
	    {
	    	sqlFilter.append(" and codice_fiscale_rappresentante ilike ? ");
	    }
   
	    if (attivita != null && !attivita.equals(""))
	    {
	    	if(ricercaNoScia)
	    		sqlFilter.append(" and (attivita ilike ? or attivita similar to (select path_descrizione from ml8_linee_attivita_nuove_materializzata  where codice ilike ? limit 1)) ");
	    	else
	    		sqlFilter.append(" and attivita ilike ? ");

	    }
	    
	    if (idAttivita >0)
	    {
	    	sqlFilter.append(" and (o.codice_macroarea, o.codice_aggregazione, o.codice_attivita) in  (select codice_macroarea, codice_aggregazione, codice_attivita from ml8_linee_attivita_nuove_materializzata where id_nuova_linea_attivita = ?)  ");
	    }
     
	    if (codiceAllerta != null && ! "".equals(codiceAllerta))
	    {
	    	
	    	if (codiceAllerta.equalsIgnoreCase("%tutte%"))
	    	{
	    		if ("%aperti%".equals(statoCu))
	    		{
	    			sqlFilter.append("and riferimento_id in (select id_stabilimento from ticket where tipologia = 3 and closed is null and  codice_allerta in (select id_allerta from ticket where tipologia = 700 and trashed_date is null ) and trashed_Date is null)");

	    		}
	    		else
	    		{
	    			sqlFilter.append("and riferimento_id in (select id_stabilimento from ticket where tipologia = 3 and closed is not null and  codice_allerta in (select id_allerta from ticket where tipologia = 700 and trashed_date is null ) and trashed_Date is null)");

	    		}
	    		
	    		
	    }
	    else
	    {
	    	if ("%aperti%".equals(statoCu))
			{
				sqlFilter.append("and riferimento_id in (select id_stabilimento from ticket where tipologia = 3 and closed is null and  codice_allerta ilike ? and trashed_Date is null)");
	        	
			}
	    	else
	    	{
	    		sqlFilter.append("and riferimento_id in (select id_stabilimento from ticket where tipologia = 3 and closed is not null and  codice_allerta ilike ? and trashed_Date is null)");
	        	
	    	}
	    		
	    }
	    	
	    }
    
   
}


protected int prepareFilter(PreparedStatement pst) throws SQLException 
{
	return prepareFilter(pst,false);
}

protected int prepareFilter(PreparedStatement pst,boolean ricercaNoScia) throws SQLException 
{
	int i = 0;
	if(targa!= null && !"".equals(targa))
	{
		pst.setString(++i, targa);
	}
	if(matricola!= null && !"".equals(matricola))
	{
		pst.setString(++i, matricola);
	}
	if(numeroRiconoscimento!= null && !"".equals(numeroRiconoscimento))
	{
		pst.setString(++i, numeroRiconoscimento);
		pst.setString(++i, numeroRiconoscimento);
	}
	
	if(riferimentoIdnomeCol!=null && ! riferimentoIdnomeCol.equals("") && riferimentoId>0)
	{
		pst.setString(++i, riferimentoIdnomeCol);
		pst.setInt(++i, riferimentoId);
		
	
	}
	
	  if (ragioneSociale != null && !ragioneSociale.equals(""))
	    {
	    	pst.setString(++i, ragioneSociale);
	    }
	  
	  if (partitaIva != null && !partitaIva.equals(""))
	    {
		   	pst.setString(++i, partitaIva);
	    }
	  if (codiceFiscale != null && !codiceFiscale.equals(""))
	    {
		   	pst.setString(++i, codiceFiscale);
	    }
		
	  if (idAsl>0 && ricercaUnicaSpostamentoCu==false )
		{
			pst.setInt(++i, idAsl) ;
		}
	  else
	  {
		  if (idAsl>0 )
		  {
		  pst.setInt(++i, idAsl) ;
		 
		  }
	  }
	  if(tipoAttivita>0)
	  {
		  pst.setInt(++i, tipoAttivita);
	  }
	  
	  if (idStato>-1 )
		{
			pst.setInt(++i, idStato) ;
			pst.setInt(++i, idStato) ;

		}
	  if (tipoAttivita>0)
		{
			pst.setInt(++i, tipoAttivita) ;
		}
	
	  if (comuneSedeProduttiva != null && !comuneSedeProduttiva.equals(""))
	    {
		  pst.setString(++i, comuneSedeProduttiva);
	    }
   
	  if (indirizzoSedeProduttiva != null && !indirizzoSedeProduttiva.equals(""))
	    {
		  pst.setString(++i, indirizzoSedeProduttiva);
		  pst.setString(++i, indirizzoSedeProduttiva);
	    }
	  
	  if (numeroRegistrazione != null && !numeroRegistrazione.equals(""))
	    {
			 pst.setString(++i, numeroRegistrazione);
			 pst.setString(++i, numeroRegistrazione);
	    }
	  
	  if (nomeSoggettoFisico != null && !nomeSoggettoFisico.equals(""))
	    {
			 pst.setString(++i, nomeSoggettoFisico);
	    }
	  
	 
	  if (codiceFiscaleSoggettoFisico != null && !codiceFiscaleSoggettoFisico.equals(""))
	    {
			 pst.setString(++i, codiceFiscaleSoggettoFisico);
	    }
   
	    if (attivita != null && !attivita.equals(""))
	    {
			// pst.setString(++i, attivita.trim().replaceAll(" ", "%"));
	    	 pst.setString(++i, attivita.trim());
	    	 if(ricercaNoScia)
	    		 pst.setString(++i, attivita.trim());
	    }
     
	    if (idAttivita > 0)
	    {
			 pst.setInt(++i, idAttivita);
	    }
	    
	    if (codiceAllerta != null && ! "".equals(codiceAllerta))
	    {
	    	
	    	if (!codiceAllerta.equalsIgnoreCase("%tutte%"))
	    	{
	    		pst.setString(++i, codiceAllerta);
	    		
	    	}
	    	
	    }
	       
	return i;
}


public int cessazioneAutomaticaAttivitaTemporane(Connection db)
{
	
	int numRecordAggiornati = 0 ;
	try
	{
		String sql = "select * from public_functions.suap_aggiorna_stato_attivita_temporanee() ";
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			numRecordAggiornati = rs.getInt(1);
		
	}
	catch(SQLException e)
	{
		return numRecordAggiornati ;
	}
	return numRecordAggiornati;
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
	if(numeroRegistrazione!=null)
		this.numeroRegistrazione = numeroRegistrazione.trim();
}


public String getNomeSoggettoFisico() {
	return nomeSoggettoFisico;
}


public void setNomeSoggettoFisico(String nomeSoggettoFisico) {
	this.nomeSoggettoFisico = nomeSoggettoFisico;
}

public int getIdAttivita() {
	return idAttivita;
}


public void setIdAttivita(int idAttivita) {
		this.idAttivita = idAttivita;
}

public void setIdAttivita(String idAttivita) {
	try {this.idAttivita = Integer.parseInt(idAttivita);}
	catch (Exception e){}
}


public String getAttivita() {
	return attivita;
}


public void setAttivita(String attivita) {
	if(attivita!=null)
	this.attivita = attivita.trim();
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


public int getCategoriaRischio() {
	return categoriaRischio;
}


public void setCategoriaRischio(int categoriaRischio) {
	this.categoriaRischio = categoriaRischio;
}
public void setCategoriaRischio(String categoriaRischio) {
	try {this.categoriaRischio = Integer.parseInt(categoriaRischio);} catch (Exception e) {}
}


public String getCodiceFiscale() {
	return codiceFiscale;
}


public void setCodiceFiscale(String codiceFiscale) {
	this.codiceFiscale = codiceFiscale;
}


public String getMatricola() {
	return matricola;
}


public void setMatricola(String matricola) {
	this.matricola = matricola;
}

}

  
  
  
  


