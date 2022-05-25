/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @created    August 30, 2001
 * @version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class OperatoreList extends Vector implements SyncableList {

  private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.OperatoreList.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    } 
  }

  

  public final static String tableName = "organization";
  public final static String uniqueField = "org_id";
  protected Boolean minerOnly = null;
  protected int typeId = 0;
  protected int stageId = -1;
  boolean sindacoFR = false;
  
  private int tipologiaSoggetto = 0;
  
  
  public void setTipologiaSoggetto(int tipologiaSoggetto) {
		// TODO Auto-generated method stub
		this.tipologiaSoggetto=tipologiaSoggetto;
	}

	public int getTipologiaSoggetto() {
		// TODO Auto-generated method stub
		return this.tipologiaSoggetto;
	}
  
  
  public int getStageId() {
	return stageId;
}

public void setStageId(int stageId) {
	this.stageId = stageId;
}

public void setStageId(String tmp) {
	  if (tmp!=null){
  this.stageId = Integer.parseInt(tmp);
	  }else{this.stageId = -1;}
}

public Boolean getMinerOnly() {
	return minerOnly;
}

public void setMinerOnly(Boolean minerOnly) {
	this.minerOnly = minerOnly;
}



public int getTypeId() {
	return typeId;
}

public void setTypeId(int typeId) {
	this.typeId = typeId;
}



 protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected PagedListInfo pagedListInfo = null;
  private String ragioneSociale;
  private String codiceFiscale;
  private Boolean uos;
  private String partIva;
  private int idAsl = -1;
  private int idAslToExclude = -1;
  /*campi utili per la ricerca su stabilimenti*/
  
  private Integer[] idLineaProduttiva ;
  private String[] descrizioneLineaProduttiva ;
  private String nrProtocollo;
  
  private String microchipPosseduto = null;
  
  private Boolean flagCanileAbusivo = null;
  private Boolean flagChiuso =  null;
	
  public String getNrProtocollo() {
	return nrProtocollo;
}

public void setNrProtocollo(String nrProtocollo) {
	this.nrProtocollo = nrProtocollo;
}



public boolean isFlagCanileAbusivo() {
	return flagCanileAbusivo;
}

public void setFlagCanileAbusivo(boolean flagCanileAbusivo) {
	this.flagCanileAbusivo = flagCanileAbusivo;
}




public Boolean getFlagCanileAbusivo() {
	return flagCanileAbusivo;
}

public void setFlagCanileAbusivo(Boolean flagCanileAbusivo) {
	this.flagCanileAbusivo = flagCanileAbusivo;
}

public Boolean getFlagChiuso() {
	return flagChiuso;
}

public void setFlagChiuso(Boolean flagChiuso) {
	this.flagChiuso = flagChiuso;
}

public void setIdAslToExclude(int idAslToExclude) {
	this.idAslToExclude = idAslToExclude;
}



private int idOperatore;
	private int siteId;
	
	
 private int idRelazioneStabilimentoLineaProduttiva = -1; //Se settato recupero solo una linea produttiva 
	
	
	
	public int getIdRelazioneStabilimentoLineaProduttiva() {
	return idRelazioneStabilimentoLineaProduttiva;
}

public void setIdRelazioneStabilimentoLineaProduttiva(
		int idRelazioneStabilimentoLineaProduttiva) {
	this.idRelazioneStabilimentoLineaProduttiva = idRelazioneStabilimentoLineaProduttiva;
}

	public String[] getDescrizioneLineaProduttiva() {
		return descrizioneLineaProduttiva;
	}

	public void setDescrizioneLineaProduttiva(String[] descrizioneLineaProduttiva) {
		this.descrizioneLineaProduttiva = descrizioneLineaProduttiva;
	}

	public Integer[] getIdLineaProduttiva() {
		return idLineaProduttiva;
	}

	public void setIdLineaProduttiva(Integer[] idLineaProduttiva) {
		this.idLineaProduttiva = idLineaProduttiva;
	}
	
	public void setIdLineaProduttiva(String[] idLineaProduttiva) {
		
		this.idLineaProduttiva = new Integer[idLineaProduttiva.length];
		for(int i = 0 ; i<idLineaProduttiva.length; i++)
		{
			this.idLineaProduttiva[i] = Integer.parseInt(idLineaProduttiva[i]) ;
		}
	}

	
	


	public String getMicrochipPosseduto() {
		return microchipPosseduto;
	}

	public void setMicrochipPosseduto(String microchipPosseduto) {
		this.microchipPosseduto = microchipPosseduto;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public void setIdAsl(String idAsl) {
		this.idAsl = new Integer(idAsl).intValue();
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}
	
	
	public void setIdOperatore(String idOperatore) {
		this.idOperatore = new Integer(idOperatore).intValue();
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



	private String telefono1;
	private String telefono2;
	private int owner;
	private int enteredBy;
	private String email;
	private String fax;
	private String note;
	private int modifiedBy;
	private int idComuneStabilimento ;
	
	
  
  
  public int getIdComuneStabilimento() {
		return idComuneStabilimento;
	}

	public void setIdComuneStabilimento(int idComuneStabilimento) {
		this.idComuneStabilimento = idComuneStabilimento;
	}
	
	public void setIdComuneStabilimento(String idComuneStabilimento) {
		if(idComuneStabilimento!=null && !"".equals(idComuneStabilimento))
		this.idComuneStabilimento = Integer.parseInt(idComuneStabilimento);
	}
	
	
	


public static Logger getLog() {
	return log;
}

public static void setLog(Logger log) {
	OperatoreList.log = log;
}

public String getRagioneSociale() {
	return ragioneSociale;
}

public void setRagioneSociale(String ragioneSociale) {
	this.ragioneSociale = ragioneSociale;
}

public String getPartIva() {
	return partIva;
}

public void setPartIva(String partIva) {
	this.partIva = partIva;
}

public Boolean getUos() {
	return uos;
}

public void setUos(Boolean uos) {
	this.uos = uos;
}

public boolean isHasExpireDate() {
	return hasExpireDate;
}

public void setHasExpireDate(boolean hasExpireDate) {
	this.hasExpireDate = hasExpireDate;
}

public java.sql.Timestamp getLastAnchor() {
	return lastAnchor;
}

public java.sql.Timestamp getNextAnchor() {
	return nextAnchor;
}

public int getSyncType() {
	return syncType;
}

public PagedListInfo getPagedListInfo() {
	return pagedListInfo;
}

protected boolean hasAlertDate = false;
  protected boolean hasExpireDate = false;
  
  
  

  
  public boolean isHasAlertDate() {
	return hasAlertDate;
}

public void setHasAlertDate(boolean hasAlertDate) {
	this.hasAlertDate = hasAlertDate;
}

public String getCodiceFiscale() {
	  return codiceFiscale;
  }
  
  public void setCodiceFiscale (String codiceFiscale) {
	  this.codiceFiscale=codiceFiscale;
  }
  
  /**
   *  Constructor for the OrganizationList object, creates an empty list. After
   *  setting parameters, call the build method.
   *
   * @since    1.1
   */
  public OperatoreList() { }



  /**
   *  Sets the lastAnchor attribute of the OrganizationList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the OrganizationList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the OrganizationList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the OrganizationList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the OrganizationList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  


  /**
   *  Sets the PagedListInfo attribute of the OrganizationList object. <p>
   *
   *  <p/>
   *
   *  The query results will be constrained to the PagedListInfo parameters.
   *
   * @param  tmp  The new PagedListInfo value
   * @since       1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }





  
  

  /**
   *  Gets the HtmlSelect attribute of the ContactList object
   *
   * @param  selectName  Description of Parameter
   * @return             The HtmlSelect value
   * @since              1.8
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the HtmlSelect attribute of the ContactList object
   *
   * @param  selectName  Description of Parameter
   * @param  defaultKey  Description of Parameter
   * @return             The HtmlSelect value
   * @since              1.8
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect orgListSelect = new HtmlSelect();

    Iterator i = this.iterator();
    while (i.hasNext()) {
      Operatore thisOp = (Operatore) i.next();
      orgListSelect.addItem(
    		  thisOp.getIdOperatore(),
    		  thisOp.getRagioneSociale());
    }

    return orgListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the HtmlSelectDefaultNone attribute of the OrganizationList object
   *
   * @param  selectName  Description of Parameter
   * @param  thisSystem  Description of the Parameter
   * @return             The HtmlSelectDefaultNone value
   */
  public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName) {
    return getHtmlSelectDefaultNone(thisSystem, selectName, -1);
  }


  /**
   *  Gets the htmlSelectDefaultNone attribute of the OrganizationList object
   *
   * @param  selectName  Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @param  thisSystem  Description of the Parameter
   * @return             The htmlSelectDefaultNone value
   */
  public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName, int defaultKey) {
    HtmlSelect orgListSelect = new HtmlSelect();
    orgListSelect.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));

    Iterator i = this.iterator();
    while (i.hasNext()) {
        Operatore thisOp = (Operatore) i.next();
        orgListSelect.addItem(
      		  thisOp.getIdOperatore(),
      		  thisOp.getRagioneSociale());
      }

/*    if (!(this.getHtmlJsEvent().equals(""))) {
      orgListSelect.setJsEvent(this.getHtmlJsEvent());
    }*/

    return orgListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
 * @throws IndirizzoNotFoundException 
   */
  public void select(Connection db) throws SQLException, IndirizzoNotFoundException {
    buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  timeZone       Description of the Parameter
   * @param  events         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
 /* public HashMap queryRecordCount(Connection db, TimeZone timeZone, HashMap events) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();

    String sqlDate = ((hasAlertDate ? "alertdate" : "") + (hasExpireDate ? "contract_end" : ""));

    createFilter(db, sqlFilter);

    sqlSelect.append(
        "SELECT " + sqlDate + " AS " + DatabaseUtils.addQuotes(db, "date")+ ", count(*) AS nocols " +
        "FROM organization o " +
        "WHERE o.org_id >= 0 ");
    sqlTail.append("GROUP BY " + sqlDate);
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertDate = DateUtils.getServerToUserDateString(
          timeZone, DateFormat.SHORT, rs.getTimestamp("date"));
      int thisCount = rs.getInt("nocols");
      if (events.containsKey(alertDate)) {
        int tmpCount = ((Integer) events.get(alertDate)).intValue();
        thisCount += tmpCount;
      }
      events.put(alertDate, new Integer(thisCount));
    }
    rs.close();
    pst.close();
    return events;
  }*/


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildShortList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();

    createFilter(db, sqlFilter);

    sqlSelect.append(
        "SELECT o.id, o.ragione_sociale, " +
        (hasAlertDate ? "o.alertdate, " : "") +
        (hasExpireDate ? "o.contract_end, " : "") +
        " FROM opu_operatore o " +
        "WHERE o.id >= 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Operatore thisOp = new Operatore();
      thisOp.setIdOperatore(rs.getInt("id"));
      thisOp.setRagioneSociale(rs.getString("ragione_sociale"));
      if (hasAlertDate) {
     //   thisOrg.setAlertDate(rs.getTimestamp("alertdate"));
      }
      if (hasExpireDate) {
       // thisOrg.setContractEndDate(rs.getTimestamp("contract_end"));
      }
/*      thisOrg.setAlertText(rs.getString("alert"));
      thisOrg.setEntered(rs.getTimestamp("entered"));
      thisOrg.setEnteredBy(rs.getInt("enteredby"));
      thisOrg.setOwner(rs.getInt("owner"));*/
      this.add(thisOp);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Queries the database, using any of the filters, to retrieve a list of
   *  organizations. The organizations are appended, so build can be run any
   *  number of times to generate a larger list for a report.
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
 * @throws IndirizzoNotFoundException 
   * @since                 1.1
   */

  
  public void buildList(Connection db) throws SQLException, IndirizzoNotFoundException {
	    PreparedStatement pst = null;

	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) {
	      Operatore thisOperatore = this.getObject(rs);
	      
	      Integer[] lineaProduttiva = new Integer[1];
	      
	      lineaProduttiva[0] = rs.getInt("id_linea_produttiva");
	      
	      
	      thisOperatore.getListaStabilimenti().setIdOperatore(thisOperatore.getIdOperatore());
	      thisOperatore.getListaStabilimenti().setIdLineaProduttiva(lineaProduttiva);
	      thisOperatore.getListaStabilimenti().setIdRelazioneStabilimentoLineaProduttiva(idRelazioneStabilimentoLineaProduttiva);
	      
	      Stabilimento st = new Stabilimento();
	      st.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_rel_stab_lp"));
	      st.setMq_disponibili(thisOperatore.getMq_disponibili());
	      st.setOccupazioneAttuale(thisOperatore.getOccupazioneAttuale());
	      st.setNumeroCaniVivi(thisOperatore.getNumeroCaniVivi());
	      st.setId_linea_produttiva(thisOperatore.getId_linea_produttiva());
//	      LineaProduttivaList lpList = new LineaProduttivaList();
//		  lpList.setIdStabilimento(st.getIdStabilimento());
//		  lpList.setId(rs.getInt("id_rel_stab_lp"));
//		  lpList.buildListStabilimento(db);
//		  st.setListaLineeProduttive(lpList);
		
	        this.add(st);
	     
	    }
	 
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	    buildResources(db);
	  }
  
  
  public HashMap<Integer, String> buildListUos(Connection db) throws SQLException, IndirizzoNotFoundException {

	  PreparedStatement pst = null;
	  HashMap<Integer, String> lista = new HashMap<Integer, String>();
	  
	    ResultSet rs = queryListUos(db, pst);
	    while (rs.next()) 
	    {
	    	String asl = "";
	    	if(rs.getString("id_asl")!=null)
	    		asl = rs.getString("id_asl");
	    	lista.put(rs.getInt("id"), rs.getString("ragione_sociale") + ";" + asl);
	    }
	    
	 
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	    return lista;
	  }
  
  
/*
  
  public void buildListView(Connection db) throws SQLException  {

	  PreparedStatement pst = null;
	  ResultSet rs = null;
	  StringBuffer sqlSelect = new StringBuffer();
	  StringBuffer sqlFilter = new StringBuffer();
	  StringBuffer sqlCount = new StringBuffer();
	  StringBuffer sqlOrder = new StringBuffer();
	  
	  sqlCount.append(" select count(*) as recordcount from vista_operatori_globale WHERE TRUE ");
	  
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
	 
	      pagedListInfo.appendSqlTailView(db, sqlOrder);
	  }
	 
	  sqlSelect.append(
	        " SELECT distinct org_id, asl, tipologia_operatore, " +
	        " ragione_sociale, titolare, comune, provincia, codice_fiscale, "+
	        " partita_iva, codice_fiscale_rappresentante, stato_impresa, descrizione, "+
	        " stato_allevamento, stato, num_aut, n_reg "+
	        " FROM vista_operatori_globale " +
	        " WHERE TRUE ");  
	    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    prepareFilterView(pst);
	    rs = pst.executeQuery();
	    while (rs.next()) {
	      Organization thisOrg = new Organization();
	      thisOrg.setOrgId(rs.getInt("org_id"));
	      thisOrg.setName(rs.getString("ragione_sociale"));
	      thisOrg.setAsl(rs.getString("asl"));
	      thisOrg.setTipologia_operatore(rs.getString("tipologia_operatore"));
	      thisOrg.setTitolare(rs.getString("titolare"));
	      thisOrg.setCodiceFiscale(rs.getString("codice_fiscale"));
	      thisOrg.setCodiceFiscaleRappresentante(rs.getString("codice_fiscale_rappresentante"));
	      thisOrg.setCity(rs.getString("comune"));
	      thisOrg.setState(rs.getString("provincia"));
	      thisOrg.setN_reg(rs.getString("n_reg"));
	      thisOrg.setNum_aut(rs.getString("num_aut"));
	      thisOrg.setPartitaIva(rs.getString("partita_iva"));
	      thisOrg.setStato_impresa(rs.getString("stato_impresa"));
	      thisOrg.setStato(rs.getString("stato"));
	      thisOrg.setAlertText(rs.getString("descrizione"));
	      thisOrg.setStato_allevamento(rs.getString("stato_allevamento"));
	      this.add(thisOrg);
	    }
	    rs.close();
	    pst.close();
	    
	  
	    
	  }
	  	
  private void prepareFilterView(PreparedStatement pst) throws SQLException {
	 int i = 0;
	 
	 if(orgSiteId!=-1){
		 pst.setInt(++i, orgSiteId);
	 }
	 
	 //Come prendere gli apici?
	 if (name != null && !"".equals(name)) {
	      pst.setString(++i, name.toLowerCase());
	 }
	 
	 if (tipologia > -1) {
		 pst.setInt(++i, tipologia);
	 }
	 
	 if (city != null && !"".equals(city)) {
	      pst.setString(++i, city.toLowerCase());
	 }
	 
	 if (state != null && !"".equals(state)) {
	     pst.setString(++i, state.toLowerCase());
	 }
	 	
	
  }

private void createFilterView(Connection db, StringBuffer sqlFilter) {
	
	  if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }

	   //Filtro per Asl
	    if(orgSiteId!=-1){
	    	sqlFilter.append("AND asl_rif = ? ");
	    }
	    
	    //Filtro per nome
	    if (name != null && !(name.equals(""))) {
	        if (name.indexOf("%") >= 0) {
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(ragione_sociale) ILIKE ? ");
	        } else {
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(ragione_sociale) = ? ");
	        }
	     }
	    
	    //Aggiungere filtri anche sullo stato
	    if(tipologia != -1){
	    	sqlFilter.append(" AND tipologia= ?");
	    }
	    
	    
	    if (city != null && !"".equals(city)) {
	        if (city.indexOf("%") >= 0) {
	          sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(comune) ILIKE ? ");       
	        } else {
	        	sqlFilter.append(
	  	              "AND " + DatabaseUtils.toLowerCase(db) + "(comune) = ? ");
	        }
	      }

	    if (state != null && !"".equals(state)) {
	      if (state.indexOf("%") >= 0) {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) ILIKE ? ");
	      } else {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) = ? ");
	        }
	    }  
	    
  }*/

/**
   *  Gets the object attribute of the OrganizationList object
   *
   * @param  rs             Description of the Parameter
   * @return                The object value
   * @throws  SQLException  Description of the Exception
   */
  public Operatore getObject(ResultSet rs) throws SQLException {
	  Operatore thisOperatore = new Operatore(rs);
    return thisOperatore;
  }


  /**
   *  This method is required for synchronization, it allows for the resultset
   *  to be streamed with lower overhead
   *
   * @param  db             Description of the Parameter
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  
  /*public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records

    sqlCount.append(
    		"Select COUNT( * ) AS recordcount " +
    				"from opu_operatore o " +
    				"left join opu_rel_operatore_soggetto_fisico oss on (o.id = oss.id_operatore) " +
					"left join opu_soggetto_fisico f on (oss.id_soggetto_fisico = f.id) " +
	"left join opu_stabilimento s on s.id_operatore = o.id " +
	"left join opu_indirizzo i on i.id = s.id_indirizzo " );

    
	

		sqlCount.append(" left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_stabilimento = s.id " +
				"left join opu_informazioni_colonia info_colonia on info_colonia.id_relazione_stabilimento_linea_produttiva = rslp.id ");

    	
    //}
    			
       
           sqlCount.append(" WHERE o.id >= 0 ");
          // sqlCount.append("and oss.stato_ruolo = 1 ");

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
            "AND " + DatabaseUtils.toLowerCase(db) + "(o.ragione_sociale) < ? ");
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

      //Determine column to sort by
      pagedListInfo.setColumnToSortBy("o.ragione_sociale");
      pagedListInfo.appendSqlTail(db, sqlOrder);
            
      //Optimize SQL Server Paging
      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
    } else {
      sqlOrder.append("ORDER BY o.ragione_sociale ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    
    sqlSelect.append(
    		" distinct o.*, o.id as idOperatore, f.*,  f.id as id_soggetto, rslp.id_linea_produttiva as id_linea_produttiva  " +
    				"from opu_operatore o " +
    				"left join opu_rel_operatore_soggetto_fisico oss on (o.id = oss.id_operatore) " +
    				"left join opu_soggetto_fisico f on (oss.id_soggetto_fisico = f.id) " +
    				"left join opu_stabilimento s on s.id_operatore = o.id " +
    				"left join opu_indirizzo i on i.id = s.id_indirizzo" );
    
	sqlSelect.append(" left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_stabilimento = s.id " +
			"left join opu_informazioni_colonia info_colonia on info_colonia.id_relazione_stabilimento_linea_produttiva = rslp.id ");

  
    	 	sqlSelect.append("WHERE o.id >= 0 ");
    	 	//sqlSelect.append("and oss.stato_ruolo = 1 ");
    	 	
  
      
  
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    
    
   
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    
    
    rs = pst.executeQuery() ;
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    return rs;
  }*/
  
  
  
  public ResultSet queryListUos(Connection db, PreparedStatement pst) throws SQLException 
  {
	    ResultSet rs = null;

	    pst = db.prepareStatement("SELECT * from uos where  ? is null or ? <=0 or id_asl = ? order by id_asl, ragione_sociale " );
	    pst.setInt(1, idAsl);
	    pst.setInt(2, idAsl);
	    pst.setInt(3, idAsl);
	    
	    
	    
	    rs = pst.executeQuery() ;
	    return rs;
	  }
  
  
  
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records

	    if(this.getTipologiaSoggetto()==8){
	    	sqlCount.append("select COUNT( * ) AS recordcount from " +
	    				" (select * from opu_operatori_denormalizzati union select * from opu_operatori_denormalizzati_origine) o " );
	    }else{
	    	sqlCount.append("select COUNT( * ) AS recordcount from " +
    				"opu_operatori_denormalizzati o " );
    	
	    }

	    
		
			sqlCount.append("left join opu_informazioni_colonia info_colonia on info_colonia.id_relazione_stabilimento_linea_produttiva = o.id_rel_stab_lp ");
			sqlCount.append("left join opu_informazioni_canile info_canile on info_canile.id_relazione_stabilimento_linea_produttiva = o.id_rel_stab_lp ");
	    	
	    //}
	    			
	       
	           sqlCount.append(" WHERE o.id_opu_operatore >= 0 ");
	          // sqlCount.append("and oss.stato_ruolo = 1 ");

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
	            "AND " + DatabaseUtils.toLowerCase(db) + "(o.ragione_sociale) < ? ");
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

	      //Determine column to sort by
	      pagedListInfo.setColumnToSortBy("o.ragione_sociale");
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	            
	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append("ORDER BY o.ragione_sociale ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	     
	    ArrayList<Integer> temp = new ArrayList(Arrays.asList(idLineaProduttiva)); 
	    
	    sqlSelect.append(" distinct o.*, o.id_opu_operatore as idOperatore " );
	    
	    
	    if (temp.contains(LineaProduttiva.idAggregazioneCanile))
		{
	    	sqlSelect.append(" ,info_canile_2.indice, info_canile_2.mq_disponibili, info_canile_2.sum as occupazione_attuale, info_canile_2.numero_cani_vivi "
	    			+", bl.bloccato as canile_bloccato, bl.data_sospensione, bl.data_riattivazione "
	    			);
		}
	    
		   // sqlSelect.append(" from opu_operatori_denormalizzati o ");
	    if(this.getTipologiaSoggetto()==8){
	    	sqlSelect.append(" from  (select * from opu_operatori_denormalizzati union select * from opu_operatori_denormalizzati_origine) o " );
	    }else{
	     	sqlSelect.append(" from opu_operatori_denormalizzati o " ); 	
	    }
	    
		sqlSelect.append("left join opu_informazioni_colonia info_colonia on info_colonia.id_relazione_stabilimento_linea_produttiva = o.id_rel_stab_lp ");
		sqlSelect.append("left join opu_informazioni_canile info_canile on info_canile.id_relazione_stabilimento_linea_produttiva = o.id_rel_stab_lp ");
		
		
		if (temp.contains(LineaProduttiva.idAggregazioneCanile))
		{
			sqlSelect.append("left join canili_occupazione info_canile_2 on info_canile_2.id_rel_stab_lp = o.id_rel_stab_lp ");
			sqlSelect.append("left join blocco_sblocco_canile bl on (bl.id_canile = o.id_rel_stab_lp and bl.trashed = false and bl.trashed_date is null) ");
		}
		
	  
	    	 	sqlSelect.append("WHERE o.id_opu_operatore >= 0 ");
	    	 	//sqlSelect.append("and oss.stato_ruolo = 1 ");
	    	 	
	  
	      
	  
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    items = prepareFilter(pst);
	    
	    
	   
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    
	    
	    rs = pst.executeQuery() ;
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
  



  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   * @param  sqlFilter  Description of Parameter
   * @param  db         Description of the Parameter
   * @since             1.2
   */
  
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
  //andAudit( sqlFilter );
if (sqlFilter == null) {
  sqlFilter = new StringBuffer();
}



if (ragioneSociale != null && ! "".equals(ragioneSociale))
{
	if (ragioneSociale.indexOf(",")>0)
	{
		sqlFilter.append("and o.cognome ILIKE ? and o.nome ILIKE ? ");  
	}
	else
	{
		sqlFilter.append(" and case when o.id_linea_produttiva=1 then o.cognome ilike ? else o.ragione_sociale ilike ? end ");  
	}
		
}
if (codiceFiscale != null && !"".equals(codiceFiscale)){
	sqlFilter.append("and o.codice_fiscale_impresa ilike ? " );
}


if (partIva != null && !"".equals(partIva)){
	sqlFilter.append("and o.partita_iva ilike ? " );
}






if (idComuneStabilimento > 0)
{
	sqlFilter.append("AND  o.comune = ? ");
	
}



if (idLineaProduttiva != null && idLineaProduttiva.length>0)
{
	
	ArrayList<Integer> temp = new ArrayList(Arrays.asList(idLineaProduttiva)); 
	
	if (temp.contains(LineaProduttiva.IdAggregazioneOperatoreCommerciale)){
		temp.add(temp.size()-1, LineaProduttiva.idAggregazioneImportatore); //Importatore
		
	}
	
	
if (temp.contains(7)){
	sindacoFR = true;
}

	sqlFilter.append("AND  o.id_linea_produttiva in ( ");
	for (int i = 0 ; i<temp.size()-1 ; i++)
	{
		if (temp.get(i) == 7) //SindacoFR, non mi serve asl giu
			sindacoFR = true;
		
		if(! temp.get(i).equals("-1"))
			sqlFilter.append(temp.get(i)+",");
	}
	if(! temp.get(temp.size()-1).equals("-1"))
		sqlFilter.append(idLineaProduttiva[idLineaProduttiva.length-1]+") ");
	else
		sqlFilter.append(") ");
	
	if (temp.contains(LineaProduttiva.idAggregazioneColonia)  && nrProtocollo != null && !("").equals(nrProtocollo)){
		sqlFilter.append("AND info_colonia.nr_protocollo = ?");
		
	}
	
	if (temp.contains(LineaProduttiva.idAggregazioneCanile)  ){
		if (flagCanileAbusivo != null)
			sqlFilter.append(" AND info_canile.abusivo = "+ flagCanileAbusivo);
		
		
	}
	
	if (flagChiuso != null && flagChiuso ) //true voglio solo aperti
		sqlFilter.append(" AND o.data_fine is null ");
	
	
	//METTO DISTINCT PERCHé C'è UN PROBLEMA NELL'IMPORT DEI GATTI CHE VENGONO MESSI DUE VOLTE FORSE, DA VERIFICARE
	if (microchipPosseduto != null && !("").equals(microchipPosseduto)){
		sqlFilter.append("and ( id_opu_operatore IN ("+
						"(select distinct(id_proprietario) from animale where animale.microchip = ?  and animale.data_cancellazione is null),"+
						"(select distinct(a.id_detentore) from animale a left join cane on a.id = cane.id_animale where a.microchip = ?  and a.data_cancellazione is null),"+
						"(select distinct(a.id_detentore) from animale a left join gatto on a.id = gatto.id_animale where a.microchip = ?  and a.data_cancellazione is null)"+
							" ))");
	}
	
	
}




if (idAsl > -1 && !sindacoFR){
	sqlFilter.append("AND o.id_asl = ?");
}

if (idAslToExclude > -1 ){
	sqlFilter.append("AND o.id_asl != ?");
}

if (idRelazioneStabilimentoLineaProduttiva > 0){
	sqlFilter.append("AND o.id_rel_stab_lp = ?");
}


}
  
  
  /**
   *  Convenience method to get a list of phone numbers for each contact
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   * @since                 1.5
   */
  protected void buildResources(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Operatore thisOperatore = (Operatore) i.next();
   //   thisOperatore.getPhoneNumberList().buildList(db);
      //thisOrganization.getAddressList().buildList(db); AGGIUNGERE BUILD LIST A OPERATOREADDRESSLIST
  //    thisOperatore.getEmailAddressList().buildList(db);
      thisOperatore.getListaSediOperatore().buildListOperatore(db);
      //if this is an individual account, populate the primary contact record
/*      if (thisOrganization.getNameLast() != null) {
        thisOrganization.populatePrimaryContact(db);
      }*/
    //  thisOrganization.buildTypes(db);
    }
  }




  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   * @param  pst            Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   * @since                 1.2
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    
    
    
    if (ragioneSociale != null && !"".equals(ragioneSociale))
    {
    	if (ragioneSociale.indexOf(",")>0)
    	{
    		if(ragioneSociale.split(",")[0]!=null)
    			pst.setString(++i, ragioneSociale.split(",")[0].trim());
    		else
    			pst.setString(++i, "");
    		if(ragioneSociale.split(",")[1]!=null)
    			pst.setString(++i, ragioneSociale.split(",")[1].trim());
    		else
    			pst.setString(++i, "");
    	}
    	else
    	{
    		pst.setString(++i, ragioneSociale.trim());
    		pst.setString(++i, ragioneSociale.trim());
    	}
    		
    }
    if (codiceFiscale != null && !"".equals(codiceFiscale)){
    	pst.setString(++i, codiceFiscale);	
    }
    
    
    if (partIva != null && !"".equals(partIva)){
    	pst.setString(++i, partIva);	
    }
    
    

    if (idComuneStabilimento > 0)
    {
    	pst.setInt(++i, idComuneStabilimento);
    	
    }
    
    ArrayList<Integer> temp = new ArrayList(Arrays.asList(idLineaProduttiva));
    
     if (temp.contains(LineaProduttiva.idAggregazioneColonia) && nrProtocollo != null && !("").equals(nrProtocollo)){
    	 pst.setString(++i, nrProtocollo);
     }
     
 	if (microchipPosseduto != null && !("").equals(microchipPosseduto)){
 		pst.setString(++i, microchipPosseduto);
 		pst.setString(++i, microchipPosseduto);
 		pst.setString(++i, microchipPosseduto);
	}
    
    if (idAsl > -1 && !sindacoFR){
    	pst.setInt(++i, idAsl);
    }
    
    if (idAslToExclude > -1 ){
    	pst.setInt(++i, idAslToExclude);
    }
    
    if (idRelazioneStabilimentoLineaProduttiva > 0 ){
    	pst.setInt(++i, idRelazioneStabilimentoLineaProduttiva);
    }
    
    return i;
  }
  
  

  
  
  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  baseFilePath   Description of the Parameter
   * @param  forceDelete    Description of the Parameter
   * @param  context        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
/*  public void delete(Connection db, ActionContext context, String baseFilePath, boolean forceDelete) throws SQLException {
    Iterator organizationIterator = this.iterator();
    while (organizationIterator.hasNext()) {
      Organization thisOrganization = (Organization) organizationIterator.next();
      thisOrganization.setContactDelete(true);
      thisOrganization.setRevenueDelete(true);
      thisOrganization.setDocumentDelete(true);
      thisOrganization.setForceDelete(forceDelete);
      thisOrganization.delete(db, context, baseFilePath);
    }
  }
*/

  /**
   *  Gets the parent and leaf Accounts in the application Parent accounts are
   *  the list of root nodes in the organization hierarchies Leaf accounts are
   *  the list of leaf nodes in the organization hierarchies
   *
   * @param  db                Description of the Parameter
   * @param  typeId            Description of the Parameter
   * @param  reciprocal        Description of the Parameter
   * @return                   The parentAccounts value
   * @exception  SQLException  Description of the Exception
   */
  public static HashMap getParentAndLeafAccounts(Connection db, int typeId, boolean reciprocal) throws SQLException {
    HashMap allAccounts = new HashMap();
    HashMap leafAccounts = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT org_id FROM organization WHERE trashed_date IS NULL ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      Integer accountId = new Integer(rs.getInt("org_id"));
      allAccounts.put(accountId, accountId);
      leafAccounts.put(accountId, accountId);
    }
    rs.close();
    pst.close();
    RelationshipList thisList = new RelationshipList();
    thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
    thisList.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);
    thisList.setTypeId(typeId);
    thisList.buildList(db);
    Iterator iter = (Iterator) thisList.keySet().iterator();
    while (iter.hasNext()) {
      String relType = (String) iter.next();
      ArrayList tmpList = (ArrayList) thisList.get(relType);
      Iterator j = tmpList.iterator();
      while (j.hasNext()) {
        Relationship rel = (Relationship) j.next();
        //check for parent accounts to be in the child category
        if (allAccounts.get(new Integer(rel.getObjectIdMapsTo())) != null && reciprocal) {
          allAccounts.remove(new Integer(rel.getObjectIdMapsTo()));
        } else if (allAccounts.get(new Integer(rel.getObjectIdMapsFrom())) != null && !reciprocal) {
          allAccounts.remove(new Integer(rel.getObjectIdMapsFrom()));
        }
        //check for child accounts to be in the parent category
        if (leafAccounts.get(new Integer(rel.getObjectIdMapsFrom())) != null && reciprocal) {
          leafAccounts.remove(new Integer(rel.getObjectIdMapsFrom()));
        } else if (leafAccounts.get(new Integer(rel.getObjectIdMapsTo())) != null && !reciprocal) {
          leafAccounts.remove(new Integer(rel.getObjectIdMapsTo()));
        }
      }
    }
    HashMap combinedAccounts = new HashMap();
    combinedAccounts.put("parentNodes", allAccounts);
    combinedAccounts.put("leafNodes", leafAccounts);
    return combinedAccounts;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  childId           Description of the Parameter
   * @param  skipName          Description of the Parameter
   * @param  existingAccounts  Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
/*  public static String buildParentNameHierarchy(Connection db, int childId, boolean skipName, HashMap existingAccounts) throws SQLException {
    if (existingAccounts == null) {
      existingAccounts = new HashMap();
    }
    StringBuffer parentName = new StringBuffer();
    RelationshipList thisList = new RelationshipList();
    thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
    thisList.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);
    thisList.setObjectIdMapsFrom(childId);
    existingAccounts.put(String.valueOf(childId), new Integer(childId));
    thisList.setTypeId(1);
    thisList.buildList(db);
    Iterator iter = (Iterator) thisList.keySet().iterator();
    if (iter.hasNext()) {
      String relType = (String) iter.next();
      ArrayList tmpList = (ArrayList) thisList.get(relType);
      Iterator j = tmpList.iterator();
      if (j.hasNext()) {
        Relationship rel = (Relationship) j.next();
        if (existingAccounts.get(String.valueOf(rel.getObjectIdMapsTo())) == null) {
          parentName.append(buildParentNameHierarchy(db, rel.getObjectIdMapsTo(), false, existingAccounts));
          if (!skipName) {
            parentName.append(", ");
          }
        }
      }
    }
    Organization org = new Organization(db, childId);
    if (!skipName) {
      parentName.append(org.getName());
    }
    return parentName.toString();
  }*/


  /**
   *  Gets the orgById attribute of the OrganizationList object This method
   *  assumes that the value of id is > 0
   *
   * @param  id  The value of id is always greater than 0.
   * @return     returns the matched organization or returns null
   */
  public Operatore getOrgById(int id) {
	  Operatore result = null;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
    	Operatore org = (Operatore) iter.next();
      if (org.getIdOperatore() == id) {
        result = org;
        break;
      }
    }
    return result;
  }

public String getTableName() {
	// TODO Auto-generated method stub
	return null;
}

public String getUniqueField() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setSyncType(String tmp) {
	// TODO Auto-generated method stub
	
}





}

