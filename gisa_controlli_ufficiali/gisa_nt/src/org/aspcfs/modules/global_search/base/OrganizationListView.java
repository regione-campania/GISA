/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.global_search.base;

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
public class OrganizationListView extends Vector<OrganizationView> implements SyncableList {
	
	Logger logger = Logger.getLogger("MainLogger");

  private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.OrganizationList.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
  protected String HtmlJsEvent = "";
  public final static int TRUE = 1;
  public final static int FALSE = 0;
  private boolean directBill = false;
  private static final long serialVersionUID = 2268314721560915731L;
  private String tipoDest = null;
  protected PagedListInfo pagedListInfo = null;
  private Integer addressType=-1;
  private String nomeRappresentante = null;
  private String cognomeRappresentante = null;
  protected int tipologia = -1;
  protected String name = null;
  protected int orgId = -1;
  
  
  protected String accountNumber = null;
  protected int orgSiteId = -1;
  private String city = null;
  private String state = null;
  private String country = null;
  private String partitaIva =null;
  private String stato = null;
  private String stato_allevamento = null;
  private String stato_impresa = null;
  private String num_aut = null;
  private String titolare = null;
  private String tipologia_operatore = null;
  
  private String num_verbale = null;
  private String esito = null;

  private String soggetto = null;
  private String identificativo = null;
  private int controllo_ufficiale = -1;
  private int tipologiaAttivita = -1;
  private String tipoRicerca = null;
  private int categoriaRischio = -1;
  private int numero = -1;
  private int qualificatore;
  protected java.sql.Timestamp inizio = null;
  protected java.sql.Timestamp fine = null;
 
  private String codiceFiscale =null;
  private String opCancellati = null;
  private String attCancellati = null;
  protected Boolean minerOnly = null;
  protected boolean includeOrganizationWithoutSite = false;
  public final static String tableName = "vista_operatori_globale";
  protected int includeEnabled = TRUE;
  public final static String uniqueField = "org_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected String n_reg;
  protected String partita_iva ;
  protected String targa ;
  private ArrayList<String> controlli_sanitari = new ArrayList<String>();
  
  private boolean ignoraImportOpu= false;
  
  
  public ArrayList<String> getControlli_sanitari() {
	return controlli_sanitari;
}

  public String getTarga() {
		return targa;
	}



	public void setTarga(String targa) {
		this.targa = targa;
	}
  
public String getPartita_iva() {
	return partita_iva;
}



public void setPartita_iva(String partita_iva) {
	this.partita_iva = partita_iva;
}



public void setControlli_sanitari(ArrayList<String> controlli_sanitari) {
	this.controlli_sanitari = controlli_sanitari;
}

public void setMinerOnly(boolean tmp) {
	    this.minerOnly = new Boolean(tmp);
   }
  
  /**
   *  Sets the includeOrganizationWithoutSite attribute of the OrganizationList
   *  object
   *
   * @param  tmp  The new includeOrganizationWithoutSite value
   */
  public void setIncludeOrganizationWithoutSite(boolean tmp) {
    this.includeOrganizationWithoutSite = tmp;
  }


  /**
   *  Sets the includeEnabled attribute of the OrganizationList object
   *
   * @param  includeEnabled  The new includeEnabled value
   */
  public void setIncludeEnabled(int includeEnabled) {
    this.includeEnabled = includeEnabled;
  }

  
  /**
   *  Sets the includeOrganizationWithoutSite attribute of the OrganizationList
   *  object
   *
   * @param  tmp  The new includeOrganizationWithoutSite value
   */
  public void setIncludeOrganizationWithoutSite(String tmp) {
    this.includeOrganizationWithoutSite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the includeOrganizationWithoutSite attribute of the OrganizationList
   *  object
   *
   * @return    The includeOrganizationWithoutSite value
   */
  public boolean getIncludeOrganizationWithoutSite() {
    return includeOrganizationWithoutSite;
  }

  
   //Metodi Get e Set
  	public Integer getAddressType() {
		return addressType;
	}

	public void setDirectBill(boolean tmp) {
	    this.directBill = tmp;
	}
	
	public void setDirectBill(String tmp) {
	    this.directBill = DatabaseUtils.parseBoolean(tmp);
	  }
	
	public boolean getDirectBill() {
	    return directBill;
	  }
	
	public void setAddressType(String addressType) {
	    this.addressType = Integer.parseInt(addressType);
	  }
	
	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	public String getStato_allevamento() {
		return stato_allevamento;
	}

	public void setStato_allevamento(String stato_allevamento) {
		this.stato_allevamento = stato_allevamento;
	}

	public String getStato_impresa() {
		return stato_impresa;
	}

	public void setStato_impresa(String stato_impresa) {
		this.stato_impresa = stato_impresa;
	}

	public String getTipologia_operatore() {
		return tipologia_operatore;
	}

	public void setTipologia_operatore(String tipologia_operatore) {
		this.tipologia_operatore = tipologia_operatore;
	}
	
	public java.sql.Timestamp getInizio() {
			return inizio;
	}
	
	public void setInizio(java.sql.Timestamp inizio) {
			this.inizio = inizio;
	}
	
	public void setInizio(String tmp) {
	    this.inizio = DatabaseUtils.parseDateToTimestamp(tmp);
	}
	
	public java.sql.Timestamp getFine() {
		return fine;
	}

	public void setFine(java.sql.Timestamp fine) {
		this.fine = fine;
	}
	
	public void setFine(String tmp) {
	    this.fine = DatabaseUtils.parseDateToTimestamp(tmp);
	}
	
	public void setTipoRicerca(String ricerca) {
		this.tipoRicerca = ricerca;
	}
	
	public String getNum_aut() {
		return num_aut;
	}

	public void setNum_aut(String num_aut) {
		this.num_aut = num_aut;
	}

	public String getN_reg() {
		return n_reg;
	}

	public void setN_reg(String n_reg) {
		this.n_reg = n_reg;
	}

	public String getTitolare() {
		return titolare;
	}

	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}


	public void setTipologia(String tipo) {
	    this.tipologia = Integer.parseInt(tipo);
	}
	
	public void setTipologiaAttivita(String tipologia) {
	    this.tipologiaAttivita = Integer.parseInt(tipologia);
	}
	
	
	public void setNumero(String num) {
	    this.numero = Integer.parseInt(num);
	}
	
	public void setNumero(int num) {
	    this.numero = num;
	}
	
	public int getNumero(){
		return numero;
	}
	
	public void setQualificatore(String qual) {
	    this.qualificatore = Integer.parseInt(qual);
	}
	
	public void setQualificatore(int qualificatore) {
	    this.qualificatore = qualificatore;
	}
	
	public int getQualifcatore(){
		return qualificatore;
	}
	
	public String getTipoDest() {
		return tipoDest;
	}

	public void setTipoDest(String tipoDest) {
		this.tipoDest = tipoDest;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
  
   public void setPartitaIva (String partitaIva) {
	  this.partitaIva=partitaIva;
   }
  
   public String getCodiceFiscale() {
	  return codiceFiscale;
   }
  
  public void setCodiceFiscale (String codiceFiscale) {
	  this.codiceFiscale=codiceFiscale;
  }
  
  public String getNomeRappresentante() {
		return nomeRappresentante;
	}
  
  public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}

	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}
 
	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public int getTipologiaAttivita() {
		return tipologiaAttivita;
	}

	public void setTipologiaAttivita(int tipologiaAttivita) {
		this.tipologiaAttivita = tipologiaAttivita;
	}
	
	public void setCategoriaRischio(String cat) {
	    this.categoriaRischio = Integer.parseInt(cat);
	}
	
	public void setCategoriaRischio(int categoria) {
		this.categoriaRischio = categoria;
	}
	
	public int getCategoriaRischio() {
		return categoriaRischio;
	}	
	
	public String getAccountNumVerbale() {
		  return num_verbale;
	}
	  
	public void setAccountNumVerbale (String num_verbale) {
		  this.num_verbale=num_verbale;
	}
	
	public String getAccountIdentificativo() {
		  return identificativo;
	}
	  
	public void setAccountIdentificativo (String  identificativo) {
		  this.identificativo = identificativo;
	}
	
	
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}


	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setOrgSiteId(int orgSiteId) {
		this.orgSiteId = orgSiteId;
	}


	public void setOrgSiteId(String orgSiteId) {
		this.orgSiteId = Integer.parseInt(orgSiteId);
	}


	public int getOrgSiteId() {
		return orgSiteId;
	}
	
	public void setName(String tmp) {
		this.name = tmp;
	}

	public void setAccountName(String tmp) {
		this.name = tmp;
	}

	public String getAccountName() {
	    return name;
	}

	public void setOrgId(int tmp) {
		this.orgId = tmp;
	}

	public void setOrgId(String tmp) {
		this.orgId = Integer.parseInt(tmp);
	}

	public int getOrgId() {
		return orgId;
	}

	public String getCity() {
		return city;
	}

  
	public void setCity(String tmp) {
		this.city = tmp;
	}

	public String getAccountCity() {
		return city;
	}

	public void setAccountCity(String tmp) {
		this.city = tmp;
	}

	public String getState() {
		return state;
	}

	public void setState(String tmp) {
		this.state = tmp;
	}

	public String getAccountState() {
		return state;
	}

	public void setAccountOtherState(String tmp) {
		this.state = tmp;
	}

	public String getCountry() {
		return country;
	}

	
	public void setCountry(String tmp) {
		this.country = tmp;
	}
	
	public void setOpCancellati(String opCancellati) {
		// TODO Auto-generated method stub
		this.opCancellati= opCancellati;
	}
	
	public void setAttCancellati(String attCancellati) {
		// TODO Auto-generated method stub
		this.attCancellati = attCancellati;
	}


  /**
   *  Gets the accountCountry attribute of the OrganizationList object
   *
   * @return    The accountCountry value
   */
  public String getAccountCountry() {
    return country;
  }

  /**
   *  Sets the accountCountry attribute of the OrganizationList object
   *
   * @param  tmp  The new accountCountry value
   */
  	public void setAccountCountry(String tmp) {
  		this.country = tmp;
  	}

  	public void setSearchText(String tmp) {
  		this.name = tmp;
  	}


  	
  	public String getTableName() {
  		// TODO Auto-generated method stub
  		return tableName;
  	}

  	public String getUniqueField() {
  		// TODO Auto-generated method stub
  		return null;
  	}

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
   *  Gets the searchText(name) attribute of the OrganizationList object
   *
   * @return    The searchText(name) value
   */
  public String getSearchText() {
    return name;
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

  public String getHtmlSelect(String selectName, int defaultKey) {
	    HtmlSelect orgListSelect = new HtmlSelect();

	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      OrganizationView thisOrg = (OrganizationView) i.next();
	      orgListSelect.addItem(
	          thisOrg.getOrgId(),
	          thisOrg.getName());
	    }

	    if (!(this.getHtmlJsEvent().equals(""))) {
	      orgListSelect.setJsEvent(this.getHtmlJsEvent());
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
      OrganizationView thisOrg = (OrganizationView) i.next();
      orgListSelect.addItem(
          thisOrg.getOrgId(),
          thisOrg.getName());
    }

    if (!(this.getHtmlJsEvent().equals(""))) {
      orgListSelect.setJsEvent(this.getHtmlJsEvent());
    }

    return orgListSelect.getHtml(selectName, defaultKey);
  }
  
  /**
   *  Gets the HtmlJsEvent attribute of the OrganizationList object
   *
   * @return    The HtmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return HtmlJsEvent;
  }
  

  public void buildListView(Connection db, String tipoRicerca, boolean reportExcelRichiesto) throws SQLException  {
	
	  PreparedStatement pst = null;
	  ResultSet rs = null;
	  StringBuffer sqlSelect = new StringBuffer();
	  StringBuffer sqlFilter = new StringBuffer();
	  StringBuffer sqlCount = new StringBuffer();
	  StringBuffer sqlOrder = new StringBuffer();
	  
	  if(tipoRicerca.equals("op")){
		  sqlCount.append(" select distinct org_id from view_globale_trashed_no_trashed WHERE TRUE ");
	  }
	  else{
		  sqlCount.append(" select count(*) as recordcount from view_globale_trashed_no_trashed WHERE TRUE ");
	  }
	  
	  
	  //Gestire i filtri
	  createFilterView(db, sqlFilter, reportExcelRichiesto);
	  
	  if (pagedListInfo != null) {
	      //Get the total number of records matching filter
		  if(tipoRicerca.equals("op")){
			  pst = db.prepareStatement( "select count(*) as recordcount from ( " + sqlCount.toString() + sqlFilter.toString() + "  ) v");
		  }
		  else{
			  pst = db.prepareStatement( sqlCount.toString() + sqlFilter.toString());
		  }
	      
	     
	      prepareFilterView(pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords <= 1000 ? maxRecords : 1000);
	      }
	      rs.close();
	      pst.close();
	     
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	     
	      
	      
	      /**
	       *	LISTA DELLE MALATTIE PER I CONTROLLI SANITARI 
	       */
	      int index = 0 ;
	      while(!controlli_sanitari.isEmpty())
	      {
	    	  controlli_sanitari.remove(index);
	    	  index++ ;
	      }
	      ResultSet rs_controlli_sanitari = db.prepareStatement("SELECT description FROM codici_malattie_allevamenti").executeQuery();
	      while(rs_controlli_sanitari.next())
	      {
	    	  controlli_sanitari.add(rs_controlli_sanitari.getString("description"));
	      }
	      
	      
	  }
	 
	  if(tipoRicerca.equals("op")){
		  sqlSelect.append(
				  /*"select inf.risultati_esiti as stato_sanitario,* from view_globale_trashed_no_trashed left join view_globale_inf_sanitarie inf on(n_reg = inf.azienda)" +
			        " WHERE TRUE ");*/
			       " SELECT distinct on (org_id) org_id, ragione_sociale,tipologia, asl, tipologia_operatore,tipologia_acque, " +
			        " titolare, comune, provincia, codice_fiscale, categoria_rischio, "+
			        " partita_iva, codice_fiscale_rappresentante, stato_impresa, descrizione, "+
			        " stato_allevamento, stato, num_aut, n_reg, num_verbale, tipo_attivita, " +
			        " identificativo, tipologia_campioni, ticketid, id_nc, id_cu, data_inizio_controllo, " +
			        " nome_correntista,indirizzo_proprietario,nome_detentore,cf_detentore," +
			        " indirizzo_detentore,specie_allevata,tipologia_struttura,orientamento_produttivo,id_allevamento," +
			     //   " numero_totale_capi,num_capi_mag_anno,num_capi_sei_mesi ,inf.risultati_esiti as stato_sanitario "+
			    //    " FROM view_globale_trashed_no_trashed left join view_globale_inf_sanitarie inf on(n_reg = inf.azienda)" +
			    " numero_totale_capi,num_capi_mag_anno,num_capi_sei_mesi  "+
			     " FROM view_globale_trashed_no_trashed " +
			        " WHERE TRUE "
			    ); 
	  }
	  else{
		  sqlSelect.append(
				  /*	"select inf.risultati_esiti as stato_sanitario,* from view_globale_trashed_no_trashed left join view_globale_inf_sanitarie inf on(n_reg = inf.azienda)" +
			        " WHERE TRUE ");*/
			        " SELECT ragione_sociale, org_id, asl,tipologia, tipologia_operatore, motivazione_campione, esito, matrice, analita, tipologia_acque, " +
			        " titolare, comune, provincia, codice_fiscale, categoria_rischio, "+
			        " partita_iva, codice_fiscale_rappresentante, stato_impresa, descrizione, "+
			        " stato_allevamento, stato, num_aut, n_reg, num_verbale, tipo_attivita, " +
			        " identificativo, tipologia_campioni, ticketid, id_nc, id_cu, data_inizio_controllo, " +
			        " nome_correntista ,indirizzo_proprietario,nome_detentore,cf_detentore," +
			        " indirizzo_detentore,specie_allevata,tipologia_struttura,orientamento_produttivo,id_allevamento," +
			        //   " numero_totale_capi,num_capi_mag_anno,num_capi_sei_mesi ,inf.risultati_esiti as stato_sanitario "+
				    //    " FROM view_globale_trashed_no_trashed left join view_globale_inf_sanitarie inf on(n_reg = inf.azienda)" +
				    " numero_totale_capi,num_capi_mag_anno,num_capi_sei_mesi  "+
				     " FROM view_globale_trashed_no_trashed " +
			        " WHERE TRUE "
			    ); 
	  }
	  
	  if(reportExcelRichiesto)
		  sqlOrder =new StringBuffer(sqlOrder.toString().replaceAll(" LIMIT 1000", ""));
	   
	   if(tipoRicerca.equals("op")){
		   pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());   
	   } else {
		   pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + " ORDER BY data_inizio_controllo desc " + sqlOrder.toString());    
	   }
	    
	    prepareFilterView(pst); 
	    
	    
	    rs = pst.executeQuery();
	 
	    while (rs.next()) {
	      OrganizationView thisOrg = new OrganizationView();
	      thisOrg.setOrgId(rs.getInt("org_id"));
	      thisOrg.setName(rs.getString("ragione_sociale"));
	      thisOrg.setAsl(rs.getString("asl"));
	      thisOrg.setTipologia_operatore(rs.getString("tipologia_operatore"));
	      thisOrg.setTipologiaAttivita(rs.getInt("tipo_attivita"));
	      thisOrg.setTitolare(rs.getString("titolare"));
	      thisOrg.setTipologia(rs.getInt("tipologia"));
	      thisOrg.setCodiceFiscale(rs.getString("codice_fiscale"));
	      thisOrg.setCodiceFiscaleRappresentante(rs.getString("codice_fiscale_rappresentante"));
	      thisOrg.setCity(rs.getString("comune"));
	      thisOrg.setState(rs.getString("provincia"));
	      thisOrg.setN_reg(rs.getString("n_reg"));
	      thisOrg.setTarga(rs.getString("nome_correntista"));
	      thisOrg.setNum_aut(rs.getString("num_aut"));
	      thisOrg.setPartitaIva(rs.getString("partita_iva"));
	      thisOrg.setStato_impresa(rs.getString("stato_impresa"));
	      thisOrg.setStato(rs.getString("stato"));
	      thisOrg.setAlertText(rs.getString("descrizione"));
	      thisOrg.setStato_allevamento(rs.getString("stato_allevamento"));
	      thisOrg.setTipologia_acque(rs.getInt("tipologia_acque"));
	      thisOrg.setNome_detentore(rs.getString("nome_detentore"));
	      thisOrg.setCf_detentore(rs.getString("cf_detentore"));
	      thisOrg.setTipologia_struttura(rs.getString("tipologia_struttura"));
	      thisOrg.setOrientamento_produttivo(rs.getString("orientamento_produttivo"));
	      thisOrg.setSpecie_allevata(rs.getString("specie_allevata"));
	      if(!rs.getString("tipologia_campioni").equals("Altro")){
	    	  thisOrg.setTicketId(rs.getString("ticketid"));
	      }
	      
	      thisOrg.setIdNonConformita(rs.getString("id_nc"));
	      thisOrg.setIdControllo(rs.getString("id_cu"));
	      //thisOrg.setTipologiaAttivita(rs.getString("tipologia_campioni"));
	      thisOrg.setNum_verbale(rs.getString("num_verbale"));
	      thisOrg.setIdC(rs.getString("identificativo"));
	      thisOrg.setTipologia_campioni(rs.getString("tipologia_campioni"));
	      if(tipoRicerca.equals("cam")){
	    	  thisOrg.setMotivazioneCampione(rs.getString("motivazione_campione"));
	    	  thisOrg.setAnalita(rs.getString("analita"));
		      thisOrg.setMatrice(rs.getString("matrice"));
		      thisOrg.setEsito(rs.getString("esito"));
	      }
	     
	      thisOrg.setTipologia(rs.getInt("tipologia"));
	      thisOrg.setDataControllo(rs.getString("data_inizio_controllo"));
	      thisOrg.setCategoriaRischio(rs.getString("categoria_rischio"));

	      
	      //Settare le due date
	      this.add(thisOrg);
	    }
	    rs.close();
	    pst.close();
	    
	  
	    
	  }
	  	
  private void prepareFilterView(PreparedStatement pst) throws SQLException {
	 int i = 0;
	 
	 if(orgSiteId!=-1 && orgSiteId!=-2){
		 pst.setInt(++i, orgSiteId);
	 }
	 
	 //Come prendere gli apici?
	 if (name != null && !"".equals(name)) {
	      pst.setString(++i, name.toLowerCase());
	 }
	 
	 if (tipologia > -1) {
		 if(tipologia == 4){
			 pst.setInt(++i, 3);
		 }
		 else{
			 pst.setInt(++i, tipologia);
		 }
	 }
	 
	 if (city != null && !"".equals(city.trim()) && !"%-1%".equals(city.trim())) {
	      pst.setString(++i, city.toLowerCase());
	 }
	 
	 if ( state != null && !"".equals(state) && state != "%-1%" && !"%-1%".equals(state) ) {
		 if( state.substring(1,2).equals("1")){
			 pst.setString(++i, "%AV%".toLowerCase());
		 }else if( state.substring(1,2).equals("2")){
			 pst.setString(++i, "%BN%".toLowerCase());
		 }
		 else if(state.substring(1,2).equals("3")){
			 pst.setString(++i, "%CE%".toLowerCase());
		 }else if(state.substring(1,2).equals("4")){
			 pst.setString(++i, "%NA%".toLowerCase());
		 } else if (state.substring(1,2).equals("5")){
			 pst.setString(++i, "%SA%".toLowerCase());
		 } 
	 }
	 
	
	 
	 if(tipologiaAttivita > -1){
		 if(qualificatore != 4 || tipologiaAttivita != 3){
			 pst.setInt(++i, tipologiaAttivita);
		 }
		 
	 }
	 
	 if (num_verbale != null && !"".equals(num_verbale)) {
	      pst.setString(++i, num_verbale);
	 }
	 
	 if (identificativo != null && !"".equals(identificativo)) {
	      pst.setString(++i, identificativo);
	 }
	 
	 
	 if(categoriaRischio > -1){
		 pst.setInt(++i, categoriaRischio);
	 }
	 
	 if(codiceFiscale!=null && !codiceFiscale.equals("") ){
		 pst.setString(++i, codiceFiscale);
	 }

	 //Verificare---
	 if (esito != null && !"".equals(esito)) {
		 if (esito.indexOf("%") >= 0 && esito.contains("ok_")) {
			 pst.setString(++i,  "%respinto%".toLowerCase()); //prevedere il caso in cui il campione e' respinto anche quando e' senza esito per tot.tempo...
		 }else {
			 pst.setString(++i,  "%N.D%".toLowerCase());
		 }
	 }
	 
	
	 	

	 if(tipoRicerca.equals("det")){
		 if(numero > -1){
			 pst.setInt(++i, numero);
		 }
	 }
	 
	  if (n_reg != null && ! "".equals(n_reg))
	    {
	    	pst.setString(++i, "%"+n_reg+"%");
	    }
	    
	    if (partita_iva != null && ! "".equals(partita_iva))
	    {
	    	pst.setString(++i,  "%"+partita_iva+"%");
	    }
	    if (partitaIva != null && ! "".equals(partitaIva))
	    {
	    	pst.setString(++i,  "%"+partitaIva+"%");
	    }
	    if (targa != null && ! "".equals(targa))
	    {
	    	pst.setString(++i,  "%"+targa+"%");
	    }
  }

  private void createFilterView(Connection db, StringBuffer sqlFilter, boolean reportExcelRichiesto) {
	
	  if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }
	  
	   //Filtro per Asl
	    if(orgSiteId!=-1 && orgSiteId !=-2){
	    	sqlFilter.append("AND asl_rif = ? ");
	    }
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
	    	if(tipologia == 4){
	    		sqlFilter.append(" AND tipologia= ? AND tipologia_operatore = 'Mercati Ittici' ");
	    	}
	    	else{
	    		sqlFilter.append(" AND tipologia= ? ");
	    	}
	    }
	    
	   
		 if (city != null && !"".equals(city.trim()) && !"%-1%".equals(city.trim())) {
	        if (city.indexOf("%") >= 0) {
	          sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(comune) ILIKE ? ");       
	        } else {
	        	sqlFilter.append(
	  	              "AND " + DatabaseUtils.toLowerCase(db) + "(comune) = ? ");
	        }
	      }
 
	    if (state != null && !"".equals(state)  && state != "%-1%" && !"%-1%".equals(state) ) {
	      if (state.indexOf("%") >= 0) {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) ILIKE ? ");
	      } else {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) = ? ");
	        }
	    }  
	    
	    //Filtro sul tipo di attivita'
	    if(tipologiaAttivita > -1){
	    	sqlFilter.append(" AND tipo_attivita = ? ");
	    }
	      
	    //Filtro sul tipo di attivita'
	    if(num_verbale != null && !(num_verbale.equals(""))){
	    	 if (num_verbale.indexOf("%") >= 0) {
		          sqlFilter.append(
		              "AND " + DatabaseUtils.toLowerCase(db) + "(num_verbale) ILIKE ? ");
		        } else {
		          sqlFilter.append(
		              "AND " + DatabaseUtils.toLowerCase(db) + "(num_verbale) = ? ");
		        }
	    	
	    	// sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(num_verbale) ILIKE ? ");
	    }
	   /* MODIFICA PER TICKET 003249
	    if (identificativo != null && !(identificativo.equals(""))) {
	        if (identificativo.indexOf("%") >= 0) {
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(identificativo) ILIKE ? ");
	        } else 
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(identificativo) ILIKE ? ");
	        //}
	     }
	    */
	    if (identificativo != null && !(identificativo.equals(""))) {
	        if (identificativo.indexOf("%") >= 0) {
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(id_cu) ILIKE ? ");
	        } else 
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(id_cu) ILIKE ? ");
	        //}
	     }
	    
	    
	    //Aggiungere 
	    if(categoriaRischio > -1){
	    	sqlFilter.append(" AND categoria_rischio = ? ");
	    }
	    
	    if (codiceFiscale != null && !(codiceFiscale.equals(""))) {
	     
	          sqlFilter.append(
	              "AND " + "(codice_fiscale) ILIKE ? ");
	    
	     }
	    
	    //Aggiunta controllo sul trashed_date
	    if (opCancellati != null && !(opCancellati.equals(""))) {
	        if (opCancellati.indexOf("%") >= 0 && opCancellati.contains("no_")) {
	          sqlFilter.append(
	              "AND (data_cancellazione_operatore) IS NULL ");
	        }else if(opCancellati.indexOf("%") >= 0 && opCancellati.contains("trashed")) {
	        	sqlFilter.append(
	              "AND (data_cancellazione_operatore) IS NOT NULL ");
	        }
	        else 
	          sqlFilter.append(
	              "AND (data_concellazione_operatore) IS NULL ");
	        //}
	     }
	    
	    //Campione respinto...o no?
	    if(esito != null && !(esito.equals(""))){
	    	 if (esito.indexOf("%") >= 0) {
		          sqlFilter.append(
		              "AND " + DatabaseUtils.toLowerCase(db) + "(esito) ILIKE ? ");
		        } else {
		          sqlFilter.append(
		              "AND " + DatabaseUtils.toLowerCase(db) + "(esito) = ? ");
		        }
	    	
	    }
	    
	    //Solo se selezioni una attivita' ha senso il controllo sulla data di cancellazione ticket
	    if(tipologiaAttivita != -1){
	    	if (attCancellati != null && !(attCancellati.equals(""))) {
		        if (attCancellati.indexOf("%") >= 0 && attCancellati.contains("no_")) {
		          sqlFilter.append(
		              "AND (data_cancellazione_attivita) IS NULL ");
		        }else if(attCancellati.indexOf("%") >= 0 && attCancellati.contains("trashed")) {
		        	sqlFilter.append(
		              "AND (data_cancellazione_attivita) IS NOT NULL ");
		        }else 
		          sqlFilter.append(
		              "AND (data_concellazione_attivita) IS NULL ");
		        //}
		     }
		    
	    }
	    
	    /*gestire intervallo date*/
	    if(inizio != null && !(inizio.equals(""))){   //CAST('2009-01-08 00:01:00' AS TIMESTAMP) 
	    	 if(fine != null && !(fine.equals(""))){
	    		 sqlFilter.append(" AND data_inizio_controllo "+
	    				 "between CAST('"+inizio+"' AS TIMESTAMP) and CAST('"+fine+"' AS TIMESTAMP)");
	    	 }
	    	 else{
	    		 sqlFilter.append(" AND data_inizio_controllo >= CAST('"+inizio+"' AS TIMESTAMP) "); 
	    	 }
	    }
	    else { //data_inizio non e' valoizzata
	    	if(fine != null && !(fine.equals(""))){
	    		 sqlFilter.append(" AND data_inizio_controllo <= CAST('"+fine+"' AS TIMESTAMP) "); 
		    }
	    }
	    if (n_reg != null && ! "".equals(n_reg))
	    {
	    	sqlFilter.append(" AND n_reg ilike ? "); 
	    }
	    
	    if (partita_iva != null && ! "".equals(partita_iva))
	    {
	    	sqlFilter.append(" AND partita_iva ilike ? "); 
	    }
	    if (partitaIva != null && ! "".equals(partitaIva))
	    {
	    	sqlFilter.append(" AND partita_iva ilike ? "); 
	    }
	    
	    if (targa != null && ! "".equals(targa))
	    {
	    	sqlFilter.append(" AND nome_correntista ilike ? "); 
	    }
	    
	    if (ignoraImportOpu)
	    {
	    	sqlFilter.append(" AND import_opu is not true "); 
	    	sqlFilter.append(" AND tipologia NOT IN (");
	    	List<Integer> tipiDaNonImportare = Stabilimento.getTipologieDaNonImportare();
	    	String tipiString = tipiDaNonImportare.toString();
	    	tipiString = tipiString.replace("[","").replace("]", "");
	    	sqlFilter.append(tipiString);
	    	sqlFilter.append(") ");
	    }
	    
	    
	   /* if (qualificatore != -1 && numero!= -1) {
			if (qualificatore == 1){
				sqlFilter.append(" HAVING count(*) >= ? ");
			}
			else if (qualificatore == 2){
				sqlFilter.append(" HAVING count(*) <= ? ");
			}
			else if(qualificatore == 3){
				sqlFilter.append(" HAVING count(*) = ? ");
			}
			else{
				//select+=" HAVING count(*) >= ? ";
			}
		 }*/
	    
	    /*if(!reportExcelRichiesto){
	    	sqlFilter.append(" limit 1000 ");
	    }*/
	    
  }
  
  private void createFilterViewCount(Connection db, StringBuffer sqlFilter, boolean reportExcelRichiesto) {
		
	  if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }
	  
	  
	   //Filtro per Asl
	    if(orgSiteId!=-1 && orgSiteId !=-2){
	    	sqlFilter.append("AND asl_rif = ? ");
	    }
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
	    
	   
		 if (city != null && !"".equals(city.trim()) && !"%-1%".equals(city.trim())) {
	        if (city.indexOf("%") >= 0) {
	          sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(comune) ILIKE ? ");       
	        } else {
	        	sqlFilter.append(
	  	              "AND " + DatabaseUtils.toLowerCase(db) + "(comune) = ? ");
	        }
	      }
 
	    if (state != null && !"".equals(state)  && state != "%-1%" && !"%-1%".equals(state) ) {
	      if (state.indexOf("%") >= 0) {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) ILIKE ? ");
	      } else {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) = ? ");
	        }
	    }  
	    
	    //Filtro sul tipo di attivita'
	    if(tipologiaAttivita > -1){
	    	if(qualificatore !=4 ){
	    		sqlFilter.append(" AND tipo_attivita = ? ");
	    	}
	    	else{
	    		if(tipologiaAttivita == 3){
	    			sqlFilter.append(" AND (tipo_attivita NOT IN (1,2,3,4,6,7,8,9,15,700) OR tipo_attivita IS NULL)  ");
	    		}
	    		else{
	    			sqlFilter.append(" AND NOT tipo_attivita = ? ");
	    		}
	    		
	    	}
	    	
	    }
	      
	    //Filtro sul tipo di attivita'
	    if(num_verbale != null && !(num_verbale.equals(""))){
	    	 if (num_verbale.indexOf("%") >= 0) {
		          sqlFilter.append(
		              "AND " + DatabaseUtils.toLowerCase(db) + "(num_verbale) ILIKE ? ");
		        } else {
		          sqlFilter.append(
		              "AND " + DatabaseUtils.toLowerCase(db) + "(num_verbale) = ? ");
		        }
	    	
	    	// sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(num_verbale) ILIKE ? ");
	    }
	    
	    if (identificativo != null && !(identificativo.equals(""))) {
	        if (identificativo.indexOf("%") >= 0) {
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(identificativo) ILIKE ? ");
	        } else 
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(identificativo) ILIKE ? ");
	        //}
	     }
	    
	    //Aggiungere 
	    if(categoriaRischio > -1){
	    	sqlFilter.append(" AND categoria_rischio = ? ");
	    }
	    
	    if (codiceFiscale != null && !(codiceFiscale.equals(""))) {
		     
	          sqlFilter.append(
	              "AND " + "(codice_fiscale) ILIKE ? ");
	    
	     }
	    
	    //Aggiunta controllo sul trashed_date
	    if (opCancellati != null && !(opCancellati.equals(""))) {
	        if (opCancellati.indexOf("%") >= 0 && opCancellati.contains("no_")) {
	          sqlFilter.append(
	              "AND (data_cancellazione_operatore) IS NULL ");
	        }else if(opCancellati.indexOf("%") >= 0 && opCancellati.contains("trashed")) {
	        	sqlFilter.append(
	              "AND (data_cancellazione_operatore) IS NOT NULL ");
	        }
	        else 
	          sqlFilter.append(
	              "AND (data_concellazione_operatore) IS NULL ");
	        //}
	     }
	    
	    //Solo se selezioni una attivita' ha senso il controllo sulla data di cancellazione ticket
	    if(tipologiaAttivita != -1){
	    	if (attCancellati != null && !(attCancellati.equals(""))) {
		        if (attCancellati.indexOf("%") >= 0 && attCancellati.contains("no_")) {
		          sqlFilter.append(
		              "AND (data_cancellazione_attivita) IS NULL ");
		        }else if(attCancellati.indexOf("%") >= 0 && attCancellati.contains("trashed")) {
		        	sqlFilter.append(
		              "AND (data_cancellazione_attivita) IS NOT NULL ");
		        }else 
		          sqlFilter.append(
		              "AND (data_concellazione_attivita) IS NULL ");
		        //}
		     }
		    
	    }
	    
	    /*gestire intervallo date*/
	    if(inizio != null && !(inizio.equals(""))){   //CAST('2009-01-08 00:01:00' AS TIMESTAMP) 
	    	 if(fine != null && !(fine.equals(""))){
	    		 sqlFilter.append(" AND data_inizio_controllo "+
	    				 "between CAST('"+inizio+"' AS TIMESTAMP) and CAST('"+fine+"' AS TIMESTAMP)");
	    	 }
	    	 else{
	    		 sqlFilter.append(" AND data_inizio_controllo >= CAST('"+inizio+"' AS TIMESTAMP) "); 
	    	 }
	    }
	    else { //data_inizio non e' valoizzata
	    	if(fine != null && !(fine.equals(""))){
	    		 sqlFilter.append(" AND data_inizio_controllo <= CAST('"+fine+"' AS TIMESTAMP) "); 
		    }
	    }
	    
	    if (n_reg != null && ! "".equals("n_Reg"))
	    {
	    	sqlFilter.append(" AND n_reg ilike ? "); 
	    }
	    
	    if (partita_iva != null && ! "".equals("partita_iva"))
	    {
	    	sqlFilter.append(" AND partita_iva ilike ? "); 
	    }
	    
	    if (targa != null && ! "".equals("targa"))
	    {
	    	sqlFilter.append(" AND nome_correntista ilike ? "); 
	    }
	    
	    sqlFilter.append(" GROUP BY ragione_sociale, org_id, asl, tipologia_operatore, " +
	        " categoria_rischio, tipo_attivita, " +
	        " tipologia_campioni  " );
	    
	    if (qualificatore != -1 && numero!= -1) {
			if (qualificatore == 1){
				sqlFilter.append(" HAVING count(*) >= ? " );
			}
			else if (qualificatore == 2){
				sqlFilter.append(" HAVING count(*) <= ? ");
			}
			else if(qualificatore == 3){
				sqlFilter.append(" HAVING count(*) =  ? ");
			}
			else{
				//select+=" HAVING count(*) >= (-)? ";
			}
		 }
	    
	    
	       
//	    if(!reportExcelRichiesto){
//	    	sqlFilter.append(" limit 1000 ");
//	    }
	    
	    sqlFilter.append(") prova ");
	    
  }
  
  public void buildListViewCount (Connection db, boolean reportExcelRichiesto) throws SQLException  {
		
	  PreparedStatement pst = null;
	  PreparedStatement pst_conta = null;
	  ResultSet rs = null;
	  StringBuffer sqlSelect = new StringBuffer();
	  StringBuffer sqlFilter = new StringBuffer();
	  StringBuffer sqlCount = new StringBuffer();
	  StringBuffer sqlOrder = new StringBuffer();
	  

	  sqlCount.append(" SELECT count(count) from (" + " SELECT count(*) as count, ragione_sociale, org_id, asl, tipologia_operatore, " +
      " categoria_rischio, tipo_attivita, " +
      " tipologia_campioni" +
      " FROM view_globale_trashed_no_trashed " +
      " WHERE TRUE ");
	 
	  //Gestire i filtri
	  createFilterViewCount(db, sqlFilter, reportExcelRichiesto);
	  
	  if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      
	      prepareFilterView(pst);
	      
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("count");
	        pagedListInfo.setMaxRecords(maxRecords <= 1000 ? maxRecords : 1000);
	      }
	      rs.close();
	      pst.close();
	 
	      //pagedListInfo.appendSqlTailViewCount(db, sqlOrder, qualificatore, numero);
	      pagedListInfo.appendSqlTail(db, sqlOrder);

	  }
	 
	  sqlSelect.append(
	        " SELECT count(*) as count, ragione_sociale, org_id, asl, tipologia_operatore, " +
	        " categoria_rischio, tipo_attivita, " +
	        " tipologia_campioni" +
	        " FROM view_globale_trashed_no_trashed " +
	        " WHERE TRUE "
	    ); 
	    
	 
	  	String filter = sqlFilter.toString().substring(0,sqlFilter.toString().length()-9);
	    if(reportExcelRichiesto)
			  sqlOrder =new StringBuffer(sqlOrder.toString().replaceAll("LIMIT 1000", ""));
	    
	 	pst = db.prepareStatement(sqlSelect.toString() + filter +sqlOrder.toString());
	    prepareFilterView(pst);
	    
	    				
	     
	    rs = pst.executeQuery();
	    while (rs.next()) {
	      OrganizationView thisOrg = new OrganizationView();
	      thisOrg.setOrgId(rs.getInt("org_id"));
	      thisOrg.setName(rs.getString("ragione_sociale"));
	      thisOrg.setAsl(rs.getString("asl"));
	      thisOrg.setTipologia_operatore(rs.getString("tipologia_operatore"));
	      thisOrg.setTipologiaAttivita(rs.getInt("tipo_attivita"));
	      thisOrg.setTipologia(rs.getInt("tipologia"));
	      thisOrg.setTipologia_campioni(rs.getString("tipologia_campioni"));
	      //thisOrg.setDataControllo(rs.getString("data_inizio_controllo"));
	      thisOrg.setCategoriaRischio(rs.getString("categoria_rischio"));
	      thisOrg.setCount(rs.getInt("count"));
	      //Settare le due date
	      this.add(thisOrg);
	    }
	    
	    rs.close();
	    pst.close();
	    
	    
	  }

  public void buildListSIN (Connection db) throws SQLException  {
		
	  PreparedStatement pst = null;
	  ResultSet rs = null;
	  StringBuffer sqlSelect = new StringBuffer();
	  
	  sqlSelect.append(" SELECT * from view_operatori_sin where (tipologia = 5 or tipologia_operatore = 'Mercati Ittici') and asl_rif > 0 order by asl ");
	  pst = db.prepareStatement(sqlSelect.toString());	    				
	     
	  rs = pst.executeQuery();
	  while (rs.next()) {
	      OrganizationView thisOrg = new OrganizationView();
	      thisOrg.setOrgId(rs.getInt("org_id"));
	      thisOrg.setName(rs.getString("ragione_sociale"));
	      thisOrg.setAsl(rs.getString("asl"));
	      thisOrg.setTipologia_operatore(rs.getString("tipologia_operatore"));
	      if(rs.getString("punti_sb_wm")!= null && !rs.getString("punti_sb_wm").equals("null")){
	    	  thisOrg.setAlertText(rs.getString("punti_sb_wm"));  
	      }else {
	    	  thisOrg.setAlertText("N.D");
	      }
	      
	      this.add(thisOrg);
	 }
	    
	  rs.close();
	  pst.close();
	    
	    
	  }

@Override
public void setSyncType(String arg0) {
	// TODO Auto-generated method stub
	
}

public boolean isIgnoraImportOpu() {
	return ignoraImportOpu;
}

public void setIgnoraImportOpu(boolean ignoraImportOpu) {
	this.ignoraImportOpu = ignoraImportOpu;
}
  
  


  	
  }
  


  
  
  
  


