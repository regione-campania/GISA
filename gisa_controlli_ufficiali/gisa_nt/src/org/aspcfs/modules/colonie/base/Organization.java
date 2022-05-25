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
package org.aspcfs.modules.colonie.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.communications.base.RecipientList;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.mycfs.base.OrganizationEventList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.utils.ProjectUtils;
import com.zeroio.webdav.utils.ICalendar;
/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 * Exp $
 */
public class Organization extends GenericBean {
										 	
	//static final long serialVersionUID =-6915867402685037717L;

  /**
	 * 
	 */
	private static final long serialVersionUID = 4320567602597719160L;
	java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MainLogger");
/**
	 * 
	 */
private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
  protected double YTD = 0;
  private String errorMessage = "";
  private int orgId = -1;
  private int orgIdC = -1;
  //private Connection db=null;
  private String name = "";
  private String url = "";
  private String lastModified = "";
  private String notes = "";
  private int industry = 0;
  private int accountSize = -1;
  private boolean directBill = false;
  private String accountSizeName = null;
  private int listSalutation = -1;
  private String tipoDest = null;
  private boolean cessazione = false;
  private boolean voltura = false;
  private int segmentList = -1;
  private int siteId = -1;
  private int stageId = -1;
  private String stageName = null; 
  private String siteClient = null;
  public int segmentId = -1;
  private int subSegmentId = -1;
  private String industryName = null;
  private int tipologia = -1;
  private boolean minerOnly = false;
  private int enteredBy = -1;
	private java.sql.Timestamp dataPresentazione = null;

  private int cessato = 0;
  public int getTipologia() {
	return tipologia;
}

public void setTipologia(int tipologia) {
	this.tipologia = tipologia;
}


private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp contractEndDate = null;
  private java.sql.Timestamp date1 = null;
  private java.sql.Timestamp date2 = null;
  private int categoriaRischio=-1;
  private int categoria_precedente=-1;
  private java.sql.Timestamp dateI = null;
  private java.sql.Timestamp dateF = null;
  
  private Vector comuni=new Vector();
  
  private java.sql.Timestamp alertDate = null;
  private String alertText = "";

  protected int modifiedBy = -1;
  private boolean enabled = true;
  private int employees = 0;
  private double revenue = 0;
  private String ticker = "";
  private String accountNumber = "";
  private int owner = -1;
  private int duplicateId = -1;
  private int importId = -1;
  private int statusId = -1;
  private Timestamp trashedDate = null;
  private String alertDateTimeZone = null;
  private String contractEndDateTimeZone = null;
  private int source = -1;
  private int rating = -1;
  private double potential = 0;
  private String city = null;
  private String state = null;
  private String postalCode = null;
  private String county = null;
  
  //Aggiunta
  
  private String stato = null;
  private String stato_allevamento = null;
  private String stato_impresa = null;
  private String tipologia_operatore = null;
  private String num_aut = null;
  private String n_reg = null;
  private String titolare = null;
  private String asl = null;
  
  private boolean abusivo = false;
  private Timestamp dataChiusuraCanile = null;
  private String autorizzazione = null;
  
  private int TipoStruttura = -1;
  private int TipoLocale = -1;
  private int TipoLocale2 = -1;
  private int TipoLocale3 = -1;
  private java.util.Date prossimoControllo= null;
  private OrganizationAddressList addressList = new OrganizationAddressList();
  private OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
  protected OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
  private String ownerName = "";
  private String enteredByName = "";
  private String modifiedByName = "";
  protected int codiceImpresaGeneratoDa = -1;
  protected int cambiatoInOsaDa = -1;
  private String city_legale_rapp ;
  private String address_legale_rapp ;
  private String prov_legale_rapp;
  private String ip_entered;
  private String ip_modified ;
  private int numeroCUaperti;
  private int numeroCUchiusi;
	
	public int getNumeroCUaperti() {
		return numeroCUaperti;
	}
	
	public void setNumeroCUaperti(int numeroCUaperti) {
		this.numeroCUaperti = numeroCUaperti;
	}
	
	public int getNumeroCUchiusi() {
		return numeroCUchiusi;
	}
	
	public void setNumeroCUchiusi(int numeroCUchiusi) {
		this.numeroCUchiusi = numeroCUchiusi;
	}
	
	public int getOrgIdC() {
		return orgIdC;
	}

	public void setOrgIdC(int orgIdC) {
		this.orgIdC = orgIdC;
	}

	public String getAutorizzazione() {
		return autorizzazione;
	}

	public void setAutorizzazione(String autorizzazione) {
		this.autorizzazione = autorizzazione;
	}

	public Timestamp getDataChiusuraCanile() {
		return dataChiusuraCanile;
	}

	public void setDataChiusuraCanile(Timestamp dataChiusuraCanile) {
		this.dataChiusuraCanile = dataChiusuraCanile;
	}

	public boolean isAbusivo() {
		return abusivo;
	}

	public void setAbusivo(boolean abusivo) {
		this.abusivo = abusivo;
	}

	public String getCity_legale_rapp() {
		return city_legale_rapp;
	}

	public void setCity_legale_rapp(String city_legale_rapp) {
		this.city_legale_rapp = city_legale_rapp;
	}

	public String getAddress_legale_rapp() {
		return address_legale_rapp;
	}

	public void setAddress_legale_rapp(String address_legale_rapp) {
		this.address_legale_rapp = address_legale_rapp;
	}

	public String getProv_legale_rapp() {
		return prov_legale_rapp;
	}

	public void setProv_legale_rapp(String prov_legale_rapp) {
		this.prov_legale_rapp = prov_legale_rapp;
	}

	public String getIp_entered() {
		return ip_entered;
	}

	public void setIp_entered(String ip_entered) {
		this.ip_entered = ip_entered;
	}

	public String getIp_modified() {
		return ip_modified;
	}
	public void setIp_modified(String ip_modified) {
		this.ip_modified = ip_modified;
	}

  
  private Timestamp dataProssimoControllo;
  
  
  public String getAsl() {
		return asl;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}
  
  
  public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
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
  
  

  public int getCambiatoInOsaDa() {
	return cambiatoInOsaDa;
}

public int getCodiceImpresaGeneratoDa() {
	return codiceImpresaGeneratoDa;
}
public Integer getCategoriaRischio() {
	return categoriaRischio;
}

public void setCategoriaRischio(String categoriaRischio) {
    this.categoriaRischio = Integer.parseInt(categoriaRischio);
  }
public void setCategoriaRischio(Integer categoriaRischio) {
	this.categoriaRischio = categoriaRischio;
}

public Integer getCategoriaPrecedente() {
	return categoria_precedente;
}

public void setCategoriaPrecedente(String categoriaRischio) {
    this.categoria_precedente = Integer.parseInt(categoriaRischio);
  }
public void setCategoriaPrecedente(Integer categoriaRischio) {
	this.categoria_precedente = categoriaRischio;
}

private ArrayList<Distrubutore> listaDistributori=new ArrayList<Distrubutore>();

public ArrayList<Distrubutore> getListaDistributori() {
	return listaDistributori;
}

public void setListaDistributori(ArrayList<Distrubutore> listaDistributori) {
	this.listaDistributori = listaDistributori;
}
public java.sql.Timestamp getDataPresentazione() {
	return dataPresentazione;
}
public void setDataPresentazione(String tmp) {
    this.dataPresentazione = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
  }

public void setDataPresentazione(java.sql.Timestamp tmp) {
	this.dataPresentazione = tmp;
}

public void updateDistributori(Connection db) throws SQLException{
	
	
	PreparedStatement pst=db.prepareStatement("delete  from distributori_automatici where org_id="+orgId);
	pst.execute();
	
	for(Distrubutore distributore : listaDistributori){
		
		distributore.insert(db, orgId);
		
	
	}
	
	
	
}

public void updateCategoriaRischio(Connection db, int categoriaR, int orgid) throws SQLException{
	
	
	PreparedStatement pst=db.prepareStatement("UPDATE organization set categoria_rischio = ? where org_id = ?");
	pst.setInt(1, categoriaR);
	pst.setInt(2, orgid);
	pst.execute();
	
}

public void updateCategoriaPrecedente(Connection db, int categoriaR, int orgid) throws SQLException{
	
	
	PreparedStatement pst=db.prepareStatement("UPDATE organization set categoria_precedente = ? where org_id = ?");
	pst.setInt(1, categoriaR);
	pst.setInt(2, orgid);
	pst.execute();
	
}

public int updateVoltura(Connection db, int org_id) throws SQLException{
	
	int result = 0;
	PreparedStatement pst=db.prepareStatement("UPDATE organization set name = ?, banca = ?, partita_iva = ?, codice_fiscale_rappresentante = ?, nome_rappresentante = ?, cognome_rappresentante = ?, email_rappresentante = ?, telefono_rappresentante = ?, data_nascita_rappresentante =?, luogo_nascita_rappresentante = ?, fax = ? where org_id = ?");
	pst.setString(1, this.getName());
	pst.setString(2, this.getBanca());
	pst.setString(3, this.getPartitaIva());
	pst.setString(4, this.getCodiceFiscaleRappresentante());
	pst.setString(5, this.getNomeRappresentante());
	pst.setString(6, this.getCognomeRappresentante());
	pst.setString(7, this.getEmailRappresentante());
	pst.setString(8, this.getTelefonoRappresentante());
	DatabaseUtils.setTimestamp(pst, 9, this.getDataNascitaRappresentante());
	pst.setString(10, this.getLuogoNascitaRappresentante());
	pst.setString(11, this.getFax());
	pst.setInt(12, org_id);
	result = pst.executeUpdate();
	return result;
}

public void insertDistributori(Connection db) throws SQLException{
	
	for(Distrubutore distributore : listaDistributori){
		
		distributore.insert(db, orgId);
		
	
	}
	
	
	
}


public ArrayList<Distrubutore> getDistributori(Connection db,int org_id){
	
	ArrayList<Distrubutore> lista=new ArrayList<Distrubutore>();
	try{
		
		LookupList tipoDitributore = new LookupList(db, "lookup_tipo_distributore");
	     
		
		String sql="select * from distributori_automatici,lookup_tipo_distributore where code=alimenti_distribuiti and org_id="+org_id;
		java.sql.PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			
			String matricola=rs.getString("matricola");
			String comune=rs.getString("comune");
			String provincia=rs.getString("provincia");
			String latitudine=rs.getString("latitudine");
			String longitudine=rs.getString("longitudine");
			String cap=rs.getString("cap");
			String note=rs.getString("note");
			String indirizzo=rs.getString("indirizzo");
			Date data=rs.getDate("data");
			String description=rs.getString("description");
			int alimentiDstribuiti=rs.getInt("alimenti_distribuiti");
			String ubicazione=rs.getString("ubicazione");
			
			Distrubutore dist=new Distrubutore(matricola,comune,indirizzo,cap,provincia,latitudine,longitudine,note,data,alimentiDstribuiti,ubicazione);
			dist.setDescrizioneTipoAlimenti(description);
			dist.setOrg_id(org_id);
			lista.add(dist);
			
		}
		
		
		
		
		
		
		
		
	}catch(Exception e){
		
		e.printStackTrace();
	}
	return lista;
	
	
}

public String getIdVoltura(Connection db,int org_id){
	
	String idVoltura= null;
	try{
		
		String sql="select max(ticketid) as ticketid from ticket where org_id=? and tipologia = 4";
		java.sql.PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, org_id);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			
			idVoltura = rs.getString("ticketid");
			
		}
		pst.close();
		rs.close();
	}catch(Exception e){
		
		e.printStackTrace();
	}
	return idVoltura;
	
	
}

private LookupList types = new LookupList();
  private ArrayList typeList = null;

  private boolean contactDelete = false;
  private boolean revenueDelete = false;
  private boolean documentDelete = false;

  //By default, insert a primary contact record if contact information is available.
  //Account contact import disables this automatic insertion when importing

  private boolean hasEnabledOwnerAccount = true;

  //contact as account stuff
  private String nameSalutation = null;
  private String nameFirst = null;
  private String nameMiddle = null;
  private String nameLast = null;
  private String nameSuffix = null;

  private boolean hasOpportunities = false;
  private boolean hasPortalUsers = false;
  private boolean isIndividual = false;

  private boolean forceDelete = false;

  private String dunsType = null;
  private String dunsNumber = null;
  private String businessNameTwo = null;
  private int sicCode = -1;
  private int yearStarted = -1;
  private String sicDescription = null;
  
  	// campi nuovi - progetto STI
	private String partitaIva = null;
	private String codiceFiscale = null;
	private String abi = null;
	private String cab = null;
	private String cin = null;
	private String banca = null;
	private String contoCorrente = null;
	private String nomeCorrentista = null;
	private String codiceFiscaleCorrentista = null;
	
//		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//	private String codice1 = null;
//	private String codice2 = null;
//	private String codice3 = null;
//	private String codice4 = null;
//	private String codice5 = null;
//	private String codice6 = null;
//	private String codice7 = null;
//	private String codice8 = null;
//	private String codice9 = null;
//	private String codice10 = null;
	
	private String codiceCont = null;
	// fine campi nuovi - progetto STI
	
	
	//livello di rischio provvisorio e finale: campo ricavato da audit
	private int livelloRischio = -1;
	
	/*dati aggiunti da d.dauria per gestire il riferimento */
	private String codiceFiscaleRappresentante = null;
	private String nomeRappresentante = null;
	private String cognomeRappresentante = null;
	private String emailRappresentante = null;
	private int    titoloRappresentante = -1;
	private String telefonoRappresentante = null;
	private java.sql.Timestamp dataNascitaRappresentante = null;
	private String luogoNascitaRappresentante = null;
	private String fax = null;
	

	private String codiceImpresaInterno = "";
	
	
	
	public String getCodiceImpresaInterno() {
		return codiceImpresaInterno;
	}

	public void setCodiceImpresaInterno(String codiceImpresaInterno) {
		this.codiceImpresaInterno = codiceImpresaInterno;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	

	public String getTelefonoRappresentante() {
		return telefonoRappresentante;
	}

	public void setTelefonoRappresentante(String telefonoRappresentante) {
		this.telefonoRappresentante = telefonoRappresentante;
	}
	
	public String getLuogoNascitaRappresentante() {
		return luogoNascitaRappresentante;
	}

	public void setLuogoNascitaRappresentante(String luogoRappresentante) {
		this.luogoNascitaRappresentante = luogoRappresentante;
	}

	public void setTitoloRappresentante(String tmp) {
	    this.titoloRappresentante = Integer.parseInt(tmp);
	  }

	public int getTitoloRappresentante() {
		return titoloRappresentante;
	}

	public void setTitoloRappresentante(int titoloRappresentante) {
		this.titoloRappresentante = titoloRappresentante;
	}

	public String getCodiceFiscaleRappresentante() {
		return codiceFiscaleRappresentante;
	}

	public void setCodiceFiscaleRappresentante(String codiceFiscaleRappresentante) {
		this.codiceFiscaleRappresentante = codiceFiscaleRappresentante;
	}

	public String getNomeRappresentante() {
		return nomeRappresentante;
	}
	
	public void setCessazione(boolean tmp) {
	    this.cessazione = tmp;
	  }
	
	public void setCessazione(String tmp) {
	    this.cessazione = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
	        tmp));
	  }

	public boolean getCessazione() {
	    return cessazione;
	  }
	
	public void setVoltura(boolean tmp) {
	    this.voltura = tmp;
	  }
	
	public void setVoltura(String tmp) {
	    this.voltura = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
	        tmp));
	  }

	public boolean getVoltura() {
	    return voltura;
	  }

	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}
	
	public String getTipoDest() {
		return tipoDest;
	}

	public void setTipoDest(String tipoDest) {
		this.tipoDest = tipoDest;
			}

	public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}

	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}

	public String getEmailRappresentante() {
		return emailRappresentante;
	}

	public void setEmailRappresentante(String emailRappresentante) {
		this.emailRappresentante = emailRappresentante;
	}

	public int getLivelloRischio() {
		return livelloRischio;
	}
	
//		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//	public String getCodice1() {
//		return codice1;
//	}
//
//	public void setCodice1(String codice1) {
//		this.codice1 = codice1;
//			}
//	
//	public String getCodice2() {
//		return codice2;
//	}
//
//	public void setCodice2(String codice2) {
//		this.codice2 = codice2;
//	}
//	
//	public String getCodice3() {
//		return codice3;
//	}
//
//	public void setCodice3(String codice3) {
//		this.codice3 = codice3;
//	}
//	
//	public String getCodice4() {
//		return codice4;
//	}
//
//	public void setCodice4(String codice4) {
//		this.codice4 = codice4;
//	}
//	
//	public String getCodice5() {
//		return codice5;
//	}
//
//	public void setCodice5(String codice5) {
//		this.codice5 = codice5;
//	}
//	
//	public String getCodice6() {
//		return codice6;
//	}
//
//	public void setCodice6(String codice6) {
//		this.codice6 = codice6;
//	}
//	
//	public String getCodice7() {
//		return codice7;
//	}
//
//	public void setCodice7(String codice7) {
//		this.codice7 = codice7;
//	}
//	
//	public String getCodice8() {
//		return codice8;
//	}
//
//	public void setCodice8(String codice8) {
//		this.codice8 = codice8;
//	}
//	
//	public String getCodice9() {
//		return codice9;
//	}
//
//	public void setCodice9(String codice9) {
//		this.codice9 = codice9;
//	}
//	
//	public String getCodice10() {
//		return codice10;
//	}
//
//	public void setCodice10(String codice10) {
//		this.codice10 = codice10;
//	}
	
	public String getCodiceCont() {
		return codiceCont;
	}

	public void setCodiceCont(String codiceCont) {
		this.codiceCont = codiceCont;
			}
	
	
	public void setLivelloRischio(int tmp) {
		this.livelloRischio = tmp;
	}
	
	private Timestamp dataAudit = null;
	public Timestamp getDataAudit() {
		return dataAudit;
	}

	public void setDataAudit(Timestamp tmp) {
		this.dataAudit = tmp;
	}
	public java.sql.Timestamp getDataNascitaRappresentante() {
		return dataNascitaRappresentante;
	}
	public void setDataNascitaRappresentante(String tmp) {
	    this.dataNascitaRappresentante = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	  }

	public void setDataNascitaRappresentante(java.sql.Timestamp tmp) {
		this.dataNascitaRappresentante = tmp;
	}
	
	private int livelloRischioFinale = -1;
	public int getLivelloRischioFinale() {
		return livelloRischioFinale;
	}

	public void setLivelloRischioFinale(int tmp) {
		this.livelloRischioFinale = tmp;
	}
	
	
  	// campi nuovi - metodi get e set
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodiceFiscaleCorrentista() {
		return codiceFiscaleCorrentista;
	}

	public void setCodiceFiscaleCorrentista(String codiceFiscaleCorrentista) {
		this.codiceFiscaleCorrentista = codiceFiscaleCorrentista;
	}

	public String getAbi() {
		return abi;
	}

	public void setAbi(String abi) {
		this.abi = abi;
	}

	public String getCab() {
		return cab;
	}

	public void setCab(String cab) {
		this.cab = cab;
	}
	
	
	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getBanca() {
		return banca;
	}

	public void setBanca(String banca) {
		this.banca = banca;
	}

	public String getNomeCorrentista() {
		return nomeCorrentista;
	}

	public void setNomeCorrentista(String nomeCorrentista) {
		this.nomeCorrentista = nomeCorrentista;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getContoCorrente() {
		return contoCorrente;
	}

	public void setContoCorrente(String contoCorrente) {
		this.contoCorrente = contoCorrente;
	}
	
	public void setTipoStruttura(int TipoStruttura) {
	    this.TipoStruttura = TipoStruttura;
	  }


	  /**
	   *  Sets the siteId attribute of the Organization object
	   *
	   * @param  tmp  The new siteId value
	   */
	  public void setTipoStruttura(String TipoStruttura) {
	    this.TipoStruttura = Integer.parseInt(TipoStruttura);
	  }
	  
	  /**
	   *  Gets the siteId attribute of the Organization object
	   *
	   * @return    The siteId value
	   */
	  public int getTipoStruttura() {
	    return TipoStruttura;
	  }
	  
	  
	  public void setTipoLocale(int TipoLocale) {
		    this.TipoLocale = TipoLocale;
		  }


		  /**
		   *  Sets the siteId attribute of the Organization object
		   *
		   * @param  tmp  The new siteId value
		   */
		  public void setTipoLocale(String TipoLocale) {
		    this.TipoLocale = Integer.parseInt(TipoLocale);
		  }
		  
		  /**
		   *  Gets the siteId attribute of the Organization object
		   *
		   * @return    The siteId value
		   */
		  public int getTipoLocale() {
		    return TipoLocale;
		  }
		  
	//fine campi nuovi - metodi get e set

	
  /**
   *  Constructor for the Organization object, creates an empty Organization
   *
   * @since    1.0
   */
  public Organization() { }


  /**
   *  Constructor for the Organization object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Organization(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  org_id            Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
 public java.util.Date getProssimoControllo(){
	 return this.prossimoControllo;
	 	  
 }
 
  
  
  public Organization(Connection db, int org_id) throws SQLException {
	
	  
    if (org_id == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
        "SELECT o.*, " +
        "ct_owner.namelast AS o_namelast, ct_owner.namefirst AS o_namefirst, " +
        "ct_eb.namelast AS eb_namelast, ct_eb.namefirst AS eb_namefirst, " +
        "ct_mb.namelast AS mb_namelast, ct_mb.namefirst AS mb_namefirst, " +
        "'' AS industry_name, '' AS account_size_name, " +
        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode, oa.county as o_county, " +
        "'' as stage_name "+
        "FROM organization o " +
        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " +
        "WHERE o.org_id = ? " +
        " AND (oa.address_id IS NULL OR oa.address_id IN ( "
		+ "SELECT ora.address_id FROM organization_address ora WHERE ora.org_id = o.org_id AND ora.primary_address = ?) "
		+ "OR oa.address_id IN (SELECT MIN(ctodd.address_id) FROM organization_address ctodd WHERE ctodd.org_id = o.org_id AND "
		+ " ctodd.org_id NOT IN (SELECT org_id FROM organization_address WHERE organization_address.primary_address = ?)))");
    pst.setInt(1, org_id);
    pst.setBoolean(2, true);
    pst.setBoolean(3, true);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (orgId == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    
    //if this is an individual account, populate the primary contact record
   
    listaDistributori= this.getDistributori(db, this.getOrgId());
    phoneNumberList.setOrgId(this.getOrgId());
    phoneNumberList.buildList(db);
    addressList.setOrgId(this.getOrgId());
    addressList.buildList(db);
    emailAddressList.setOrgId(this.getOrgId());
    emailAddressList.buildList(db);
    
    this.calcolaNumeroCU(db);
    
    //setto livello di rischio
    //this.livelloRischio = getLivelloRischio(db);
    
    this.livelloRischioFinale = getLivelloRischioFinale(db);
    this.dataAudit = getDataAudit(db);
//    if( (livelloRischioFinale > -1) && (dataAudit != null) )
//    {
//	    ControlliOffset cO		= new ControlliOffset( db, livelloRischioFinale );
//	    //this.dataAudit			= getDataAudit( db );
//	    this.prossimoControllo	= cO.getProssimoControllo( dataAudit );
//    }
  }
  
  
  private void calcolaNumeroCU(Connection db)
	throws SQLException
{
	  PreparedStatement stat=null;
	  ResultSet res=null;
	  try{
	  String select = "select cu_aperti, cu_chiusi " + 
	  				  "from ((select count(*) as cu_aperti from ticket where org_id = ? and tipologia = 3 and closed is null and trashed_date is null)) aperti, " +
	  				  "(select count(*) as cu_chiusi from ticket where org_id = ? and tipologia = 3 and closed is not null and trashed_date is null) chiusi";
	  stat = db.prepareStatement( select );
	  stat.setInt( 1, orgId );
	  stat.setInt( 2, orgId );
	  res = stat.executeQuery();
	  if( res.next() ){
		  numeroCUaperti = res.getInt( "cu_aperti" );
		  numeroCUchiusi = res.getInt( "cu_chiusi" );
	  }
	  	
	  }
	  finally {
		  try{
			  res.close();
			  stat.close();
		  }
		  catch (Exception e) {
			  logger.severe("Eccezione nella chiusura del ResultSet o del PreparedStatement.");
		}
	 
	  }
}


  /**
   *  Sets the typeList attribute of the Organization object
   *
   * @param  typeList  The new typeList value
   */
  public void setTypeList(ArrayList typeList) {
    this.typeList = typeList;
  }


  /**
   *  Sets the typeList attribute of the Organization object
   *
   * @param  criteriaString  The new typeList value
   */
  public void setTypeList(String[] criteriaString) {
    if (criteriaString != null) {
      String[] params = criteriaString;
      typeList = new ArrayList(Arrays.asList(params));
    } else {
      typeList = new ArrayList();
    }
  }


  /**
   *  Sets the ContactDelete attribute of the Organization object
   *
   * @param  tmp  The new ContactDelete value
   */
  public void setContactDelete(boolean tmp) {
    this.contactDelete = tmp;
  }


  /**
   *  Gets the approved attribute of the Contact object
   *
   * @return    The approved value
   */
  public boolean isApproved() {
    return (statusId == Import.PROCESSED_UNAPPROVED ? false : true);
  }

  /**
   *  Sets the RevenueDelete attribute of the Organization object
   *
   * @param  tmp  The new RevenueDelete value
   */
  public void setRevenueDelete(boolean tmp) {
    this.revenueDelete = tmp;
  }


  /**
   *  Sets the DocumentDelete attribute of the Organization object
   *
   * @param  tmp  The new DocumentDelete value
   */
  public void setDocumentDelete(boolean tmp) {
    this.documentDelete = tmp;
  }


  /**
   *  Sets the EnteredByName attribute of the Organization object
   *
   * @param  enteredByName  The new EnteredByName value
   */
  public void setEnteredByName(String enteredByName) {
    this.enteredByName = enteredByName;
  }


  /**
   *  Sets the ModifiedByName attribute of the Organization object
   *
   * @param  modifiedByName  The new ModifiedByName value
   */
  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }


  /**
   *  Sets the hasEnabledOwnerAccount attribute of the Organization object
   *
   * @param  hasEnabledOwnerAccount  The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   *  Sets the trashedDate attribute of the Organization object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the Organization object
   *
   * @param  tmp  The new trashedDate value
   */
 


  /**
   *  Sets the statusId attribute of the Organization object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the Organization object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the alertDateTimeZone attribute of the Organization object
   *
   * @param  tmp  The new alertDateTimeZone value
   */
  public void setAlertDateTimeZone(String tmp) {
    this.alertDateTimeZone = tmp;
  }


  /**
   *  Sets the contractEndDateTimeZone attribute of the Organization object
   *
   * @param  tmp  The new contractEndDateTimeZone value
   */
  public void setContractEndDateTimeZone(String tmp) {
    this.contractEndDateTimeZone = tmp;
  }


  /**
   *  Sets the source attribute of the Organization object
   *
   * @param  tmp  The new source value
   */
  public void setSource(int tmp) {
    this.source = tmp;
  }


  /**
   *  Sets the source attribute of the Organization object
   *
   * @param  tmp  The new source value
   */
  public void setSource(String tmp) {
    this.source = Integer.parseInt(tmp);
  }


  /**
   *  Sets the rating attribute of the Organization object
   *
   * @param  tmp  The new rating value
   */
  public void setRating(int tmp) {
    this.rating = tmp;
  }


  /**
   *  Sets the rating attribute of the Organization object
   *
   * @param  tmp  The new rating value
   */
  public void setRating(String tmp) {
    this.rating = Integer.parseInt(tmp);
  }


  /**
   *  Sets the potential attribute of the Organization object
   *
   * @param  tmp  The new potential value
   */
  public void setPotential(double tmp) {
    this.potential = tmp;
  }


  /**
   *  Gets the alertDateTimeZone attribute of the Organization object
   *
   * @return    The alertDateTimeZone value
   */
  public String getAlertDateTimeZone() {
    return alertDateTimeZone;
  }


  /**
   *  Gets the contractEndDateTimeZone attribute of the Organization object
   *
   * @return    The contractEndDateTimeZone value
   */
  public String getContractEndDateTimeZone() {
    return contractEndDateTimeZone;
  }


  /**
   *  Gets the source attribute of the Organization object
   *
   * @return    The source value
   */
  public int getSource() {
    return source;
  }


  /**
   *  Gets the rating attribute of the Organization object
   *
   * @return    The rating value
   */
  public int getRating() {
    return rating;
  }


  /**
   *  Gets the potential attribute of the Organization object
   *
   * @return    The potential value
   */
  public double getPotential() {
    return potential;
  }


  /**
   *  Gets the trashedDate attribute of the Organization object
   *
   * @return    The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Gets the statusId attribute of the Organization object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the hasEnabledOwnerAccount attribute of the Organization object
   *
   * @return    The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   *  Sets the OwnerName attribute of the Organization object
   *
   * @param  ownerName  The new OwnerName value
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }


 


  /**
   *  Sets the ErrorMessage attribute of the Organization object
   *
   * @param  tmp  The new ErrorMessage value
   */
  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }


  /**
   *  Sets the types attribute of the Organization object
   *
   * @param  types  The new types value
   */
  public void setTypes(LookupList types) {
    this.types = types;
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   * @param  owner  The new Owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the importId attribute of the Organization object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }


  /**
   *  Sets the importId attribute of the Organization object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the importId attribute of the Organization object
   *
   * @return    The importId value
   */
  public int getImportId() {
    return importId;
  }



  /**
   *  Sets the OwnerId attribute of the Organization object
   *
   * @param  owner  The new OwnerId value
   */
  public void setOwnerId(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the Entered attribute of the Organization object
   *
   * @param  tmp  The new Entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the alertDate attribute of the Organization object
   *
   * @param  tmp  The new alertDate value
   */
  public void setAlertDate(java.sql.Timestamp tmp) {
    this.alertDate = tmp;
  }


  /**
   *  Sets the alertText attribute of the Organization object
   *
   * @param  tmp  The new alertText value
   */
  public void setAlertText(String tmp) {
    this.alertText = tmp;
  }


  /**
   *  Sets the Modified attribute of the Organization object
   *
   * @param  tmp  The new Modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Organization object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }

  public void setTrashedDate(String tmp) {
	    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
	  }
  /**
   *  Sets the modified attribute of the Organization object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   * @param  owner  The new Owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the OwnerId attribute of the Organization object
   *
   * @param  owner  The new OwnerId value
   */
  public void setOwnerId(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   * @param  contractEndDate  The new ContractEndDate value
   */
  public void setContractEndDate(java.sql.Timestamp contractEndDate) {
    this.contractEndDate = contractEndDate;
  }
  public void setDate1(java.sql.Timestamp val) {
	    this.date1 = val;
  }
  public void setDate2(java.sql.Timestamp val) {
	    this.date2 = val;
  }

  /**
   *  Sets the YTD attribute of the Organization object
   *
   * @param  YTD  The new YTD value
   */
  public void setYTD(double YTD) {
    this.YTD = YTD;
  }


  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   * @param  tmp  The new ContractEndDate value
   */
  public void setContractEndDate(String tmp) {
    this.contractEndDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }
  public void setDate1(String tmp) {
	    this.date1 = DatabaseUtils.parseDateToTimestamp(tmp);
  }
  public void setDate2(String tmp) {
	    this.date2 = DatabaseUtils.parseDateToTimestamp(tmp);
}

  /**
   *  Sets the alertDate attribute of the Organization object
   *
   * @param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    this.alertDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the Employees attribute of the Organization object
   *
   * @param  employees  The new Employees value
   */
  public void setEmployees(String employees) {
    try {
      this.employees = Integer.parseInt(employees);
    } catch (Exception e) {
      this.employees = 0;
    }
  }


  /**
   *  Sets the DuplicateId attribute of the Organization object
   *
   * @param  duplicateId  The new DuplicateId value
   */
  public void setDuplicateId(int duplicateId) {
    this.duplicateId = duplicateId;
  }


  /**
   *  Sets the orgId attribute of the Organization object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
    addressList.setOrgId(tmp);
    phoneNumberList.setOrgId(tmp);
    emailAddressList.setOrgId(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the Organization object
   *
   * @param  modifiedBy  The new ModifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   *  Sets the ModifiedBy attribute of the Organization object
   *
   * @param  modifiedBy  The new ModifiedBy value
   */
  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = Integer.parseInt(modifiedBy);
  }


  /**
   *  Sets the AccountNumber attribute of the Organization obA  9
   *
   * @param  accountNumber  The new AccountNumber value
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }


  /**
   *  Sets the OrgId attribute of the Organization object
   *
   * @param  tmp  The new OrgId value
   */
  public void setOrgId(String tmp) {
    this.setOrgId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the Revenue attribute of the Organization object
   *
   * @param  revenue  The new Revenue value
   */
  public void setRevenue(String revenue) {
    this.revenue = Double.parseDouble(revenue);
  }


  /**
   *  Sets the revenue attribute of the Organization object
   *
   * @param  tmp  The new revenue value
   */
  public void setRevenue(double tmp) {
    this.revenue = tmp;
  }


  /**
   *  Sets the Name attribute of the Organization object
   *
   * @param  tmp  The new Name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Url attribute of the Organization object
   *
   * @param  tmp  The new Url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the Ticker attribute of the Organization object
   *
   * @param  ticker  The new Ticker value
   */
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }


  /**
   *  Sets the LastModified attribute of the Organization object
   *
   * @param  tmp  The new LastModified value
   */
  public void setLastModified(String tmp) {
    this.lastModified = tmp;
  }


  /**
   *  Sets the Notes attribute of the Organization object
   *
   * @param  tmp  The new Notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the Industry attribute of the Organization object
   *
   * @param  tmp  The new Industry value
   */
  public void setIndustry(String tmp) {
    this.industry = Integer.parseInt(tmp);
  }


  /**
   *  Sets the account size attribute of the Organization object
   *
   * @param  tmp  The new account size value
   */
  public void setAccountSize(String tmp) {
    this.accountSize = Integer.parseInt(tmp);
  }


  /**
   *  Sets the listSalutation attribute of the Organization object
   *
   * @param  tmp  The new listSalutation value
   */
  public void setListSalutation(int tmp) {
    this.listSalutation = tmp;
  }


  /**
   *  Sets the listSalutation attribute of the Organization object
   *
   * @param  tmp  The new listSalutation value
   */
  public void setListSalutation(String tmp) {
    this.listSalutation = Integer.parseInt(tmp);
  }


  /**
   *  Sets the segmentList attribute of the Organization object
   *
   * @param  segmentList  The new segmentList value
   */
  public void setSegmentList(int segmentList) {
    this.segmentList = segmentList;
  }


  /**
   *  Sets the siteId attribute of the Organization object
   *
   * @param  siteId  The new siteId value
   */
  public void setSiteId(int siteId) {
    this.siteId = siteId;
  }


  /**
   *  Sets the siteId attribute of the Organization object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }
  
  /**
   *  Gets the siteId attribute of the Organization object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }
  
  /**
   *  Sets the stageId attribute of the Organization object
   *
   * @param  stageId  The new siteId value
   */
  public void setStageId(int stageId) {
    this.stageId = stageId;
  }


  /**
   *  Sets the stageId attribute of the Organization object
   *
   * @param  tmp  The new stageId value
   */
  public void setStageId(String tmp) {
    this.stageId = Integer.parseInt(tmp);
  }

  /**
   *  Gets the stageId attribute of the Organization object
   *
   * @return    The stageId value
   */
  
  public int getStageId() {
    return stageId;
  }  

  /**
   *  Gets the stageName attribute of the Organization object
   *
   * @return    The stageName value
   */
  
  public String getStageName() {
    return stageName;
  }

  /**
   *  Sets the segmentId attribute of the Organization object
   *
   * @param  tmp  The new segmentId value
   */
  public void setSegmentList(String tmp) {
    this.segmentList = Integer.parseInt(tmp);
  }


  /**
   *  Sets the siteClient attribute of the Organization object
   *
   * @param  siteClient  The new siteClient value
   */
  public void setSiteClient(String siteClient) {
    this.siteClient = siteClient;
  }


  /**
   *  Gets the siteClient attribute of the Organization object
   *
   * @return    The siteClient value
   */
  public String getSiteClient() {
    return siteClient;
  }


  /**
   *  Sets the segmentId attribute of the Organization object
   *
   * @param  segmentId  The new segmentId value
   */
  public void setSegmentId(int segmentId) {
    this.segmentId = segmentId;
  }


  /**
   *  Gets the segmentId attribute of the Organization object
   *
   * @return    The segmentId value
   */
  public int getSegmentId() {
    return segmentId;
  }


  /**
   *  Sets the segmentId attribute of the Organization object
   *
   * @param  tmp  The new segmentId value
   */
  public void setSegmentId(String tmp) {
    this.segmentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the subSegmentId attribute of the Organization object
   *
   * @param  tmp  The new subSegmentId value
   */
  public void setSubSegmentId(String tmp) {
    this.subSegmentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the subSegmentId attribute of the Organization object
   *
   * @param  SubSegmentId  The new subSegmentId value
   */
  public void setSubSegmentId(int SubSegmentId) {
    this.subSegmentId = SubSegmentId;
  }


  /**
   *  Gets the subSegmentId attribute of the Organization object
   *
   * @return    The subSegmentId value
   */
  public int getSubSegmentId() {
    return subSegmentId;
  }


  /**
   *  Gets the segmentList attribute of the Organization object
   *
   * @return    The segmentList value
   */
  public int getSegmentList() {
    return segmentList;
  }


  /**
   *  Gets the listSalutation attribute of the Organization object
   *
   * @return    The listSalutation value
   */
  public int getListSalutation() {
    return listSalutation;
  }


  /**
   *  Sets the industry attribute of the Organization object
   *
   * @param  tmp  The new industry value
   */
  public void setIndustry(int tmp) {
    industry = tmp;
  }


  /**
   *  Sets the directBill attribute of the Organization object
   *
   * @param  tmp  The new directBill value
   */
  public void setDirectBill(boolean tmp) {
    this.directBill = tmp;
  }
  
  public void setCessato(int tmp) {
	    this.cessato = tmp;
	  }

  public void setCessato(String tmp) {
	    this.cessato = Integer.parseInt(tmp);
	  }

  /**
   *  Sets the directBill attribute of the Organization object
   *
   * @param  tmp  The new directBill value
   */
  public void setDirectBill(String tmp) {
    this.directBill = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the Miner_only attribute of the Organization object
   *
   * @param  tmp  The new Miner_only value
   */
  public void setMiner_only(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
        tmp));
  }


  /**
   *  Sets the MinerOnly attribute of the Organization object
   *
   * @param  tmp  The new MinerOnly value
   */
  public void setMinerOnly(boolean tmp) {
    this.minerOnly = tmp;
  }


  /**
   *  Sets the MinerOnly attribute of the Organization object
   *
   * @param  tmp  The new MinerOnly value
   */
  public void setMinerOnly(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
        tmp));
  }


  /**
   *  Sets the AddressList attribute of the Organization object
   *
   * @param  tmp  The new AddressList value
   */
  public void setAddressList(OrganizationAddressList tmp) {
    this.addressList = tmp;
  }


  /**
   *  Sets the PhoneNumberList attribute of the Organization object
   *
   * @param  tmp  The new PhoneNumberList value
   */
  public void setPhoneNumberList(OrganizationPhoneNumberList tmp) {
    this.phoneNumberList = tmp;
  }


  /**
   *  Sets the EmailAddressList attribute of the Organization object
   *
   * @param  tmp  The new EmailAddressList value
   */
  public void setEmailAddressList(OrganizationEmailAddressList tmp) {
    this.emailAddressList = tmp;
  }


  /**
   *  Sets the Enteredby attribute of the Organization object
   *
   * @param  tmp  The new Enteredby value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the Organization object
   *
   * @param  tmp  The new EnteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(String tmp) {
    enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Since dynamic fields cannot be auto-populated, passing the request to this
   *  method will populate the indicated fields.
   *
   * @param  context  The new requestItems value
   * @since           1.15
   */
  public void setRequestItems(ActionContext context) {
    phoneNumberList = new OrganizationPhoneNumberList(context);
    addressList = new OrganizationAddressList(context.getRequest());
    emailAddressList = new OrganizationEmailAddressList(context.getRequest());
  }


  /**
   *  Gets the ContactDelete attribute of the Organization object
   *
   * @return    The ContactDelete value
   */
  public boolean getContactDelete() {
    return contactDelete;
  }


  /**
   *  Gets the RevenueDelete attribute of the Organization object
   *
   * @return    The RevenueDelete value
   */
  public boolean getRevenueDelete() {
    return revenueDelete;
  }


  /**
   *  Gets the hasOpportunities attribute of the Organization object
   *
   * @return    The hasOpportunities value
   */
  public boolean getHasOpportunities() {
    return hasOpportunities;
  }


  /**
   *  Sets the hasOpportunities attribute of the Organization object
   *
   * @param  hasOpportunities  The new hasOpportunities value
   */
  public void setHasOpportunities(boolean hasOpportunities) {
    this.hasOpportunities = hasOpportunities;
  }


  /**
   *  Sets the hasPortalUsers attribute of the Organization object
   *
   * @param  tmp  The new hasPortalUsers value
   */
  public void setHasPortalUsers(boolean tmp) {
    this.hasPortalUsers = tmp;
  }


  /**
   *  Gets the hasPortalUsers attribute of the Organization object
   *
   * @return    The hasPortalUsers value
   */
  public boolean getHasPortalUsers() {
    return hasPortalUsers;
  }


  /**
   *  Gets the DocumentDelete attribute of the Organization object
   *
   * @return    The DocumentDelete value
   */
  public boolean getDocumentDelete() {
    return documentDelete;
  }


  /**
   *  Gets the YTD attribute of the Organization object
   *
   * @return    The YTD value
   */
  public double getYTD() {
    return YTD;
  }


  /**
   *  Gets the YTDValue attribute of the Organization object
   *
   * @return    The YTDValue value
   */
  public String getYTDValue() {
    double value_2dp = (double) Math.round(YTD * 100.0) / 100.0;
    String toReturn = String.valueOf(value_2dp);
    if (toReturn.endsWith(".0")) {
      toReturn = toReturn.substring(0, toReturn.length() - 2);
    }

    if (Integer.parseInt(toReturn) == 0) {
      toReturn = "";
    }

    return toReturn;
  }


  /**
   *  Gets the Enabled attribute of the Organization object
   *
   * @return    The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the typeList attribute of the Organization object
   *
   * @return    The typeList value
   */
  public ArrayList getTypeList() {
    return typeList;
  }


  /**
   *  Gets the types attribute of the Organization object
   *
   * @return    The types value
   */
  public LookupList getTypes() {
    return types;
  }


  /**
   *  Gets the alertDate attribute of the Organization object
   *
   * @return    The alertDate value
   */
  public java.sql.Timestamp getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the alertText attribute of the Organization object
   *
   * @return    The alertText value
   */
  public String getAlertText() {
    return alertText;
  }


  /**
   *  Gets the EnteredBy attribute of the Organization object
   *
   * @return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Entered attribute of the Organization object
   *
   * @return    The Entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the Modified attribute of the Organization object
   *
   * @return    The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedString attribute of the Organization object
   *
   * @return    The ModifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the EnteredString attribute of the Organization object
   *
   * @return    The EnteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the nameSalutation attribute of the Organization object
   *
   * @return    The nameSalutation value
   */
  public String getNameSalutation() {
    return nameSalutation;
  }


  /**
   *  Gets the nameFirst attribute of the Organization object
   *
   * @return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameMiddle attribute of the Organization object
   *
   * @return    The nameMiddle value
   */
  public String getNameMiddle() {
    return nameMiddle;
  }


  /**
   *  Gets the nameLast attribute of the Organization object
   *
   * @return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the nameSuffix attribute of the Organization object
   *
   * @return    The nameSuffix value
   */
  public String getNameSuffix() {
    return nameSuffix;
  }


  /**
   *  Sets the nameSalutation attribute of the Organization object
   *
   * @param  tmp  The new nameSalutation value
   */
  public void setNameSalutation(String tmp) {
    this.nameSalutation = tmp;
  }


  /**
   *  Sets the nameFirst attribute of the Organization object
   *
   * @param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameMiddle attribute of the Organization object
   *
   * @param  tmp  The new nameMiddle value
   */
  public void setNameMiddle(String tmp) {
    this.nameMiddle = tmp;
  }


  /**
   *  Sets the nameLast attribute of the Organization object
   *
   * @param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the nameSuffix attribute of the Organization object
   *
   * @param  tmp  The new nameSuffix value
   */
  public void setNameSuffix(String tmp) {
    this.nameSuffix = tmp;
  }


  /**
   *  Gets the ContractEndDate attribute of the Organization object
   *
   * @return    The ContractEndDate value
   */
  public java.sql.Timestamp getContractEndDate() {
    return contractEndDate;
  }
  public java.sql.Timestamp getDate1() {
	    return date1;
  }
  public java.sql.Timestamp getDate2() {
	    return date2;
}
  /**
   *  Gets the ContractEndDateString attribute of the Organization object
   *
   * @return    The ContractEndDateString value
   */
  public String getContractEndDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(contractEndDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  public String getDate1String() {
	    String tmp = "";
	    try {
	      return DateFormat.getDateInstance(3).format(date1);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
  }
  public String getDate2String() {
	    String tmp = "";
	    try {
	      return DateFormat.getDateInstance(3).format(date2);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
}

  /**
   *  Gets the contractEndDateBufferedString attribute of the Organization
   *  object
   *
   * @return    The contractEndDateBufferedString value
   */
  public String getContractEndDateBufferedString() {
    String tmp = "None";
    try {
      return DateFormat.getDateInstance(3).format(contractEndDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  public String getDate1BufferedString() {
	    String tmp = "None";
	    try {
	      return DateFormat.getDateInstance(3).format(date1);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
	  }
  public String getDate2BufferedString() {
	    String tmp = "None";
	    try {
	      return DateFormat.getDateInstance(3).format(date2);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
	  }

  /**
   *  Gets the asValuesArray attribute of the Organization object
   *
   * @return    The asValuesArray value
   */
  public String[] getAsValuesArray() {
    String[] temp = new String[5];
    temp[0] = this.getName();
    temp[1] = this.getUrl();
    temp[2] = this.getOwnerName();
    temp[3] = this.getEnteredByName();
    temp[4] = this.getModifiedByName();

    return temp;
  }


  /**
   *  Gets the alertDateString attribute of the Organization object
   *
   * @return    The alertDateString value
   */
  public String getAlertDateString() {
    String tmp = "";

    try {
      return DateFormat.getDateInstance(3).format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the alertDetails attribute of the Organization object
   *
   * @return    The alertDetails value
   */
  public String getAlertDetails() {
    StringBuffer out = new StringBuffer();

    if (alertDate == null) {
      return out.toString();
    } else {
      out.append(getAlertText() + " - " + getAlertDateString());
      return out.toString().trim();
    }
  }


  /**
   *  Gets the DuplicateId attribute of the Organization object
   *
   * @return    The DuplicateId value
   */
  public int getDuplicateId() {
    return duplicateId;
  }


  /**
   *  Gets the OwnerName attribute of the Organization object
   *
   * @return    The OwnerName value
   */
  public String getOwnerName() {
    return ownerName;
  }


  /**
   *  Gets the EnteredByName attribute of the Organization object
   *
   * @return    The EnteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the ModifiedByName attribute of the Organization object
   *
   * @return    The ModifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the Owner attribute of the Organization object
   *
   * @return    The Owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the OwnerId attribute of the Organization object
   *
   * @return    The OwnerId value
   */
  public int getOwnerId() {
    return owner;
  }


  /**
   *  Gets the AccountNumber attribute of the Organization object
   *
   * @return    The AccountNumber value
   */
  public String getAccountNumber() {
	
	  return accountNumber;
  }


  /**
   *  Gets the Ticker attribute of the Organization object
   *
   * @return    The Ticker value
   */
  public String getTicker() {
    return ticker;
  }


  /**
   *  Gets the Revenue attribute of the Organization object
   *
   * @return    The Revenue value
   */
  public double getRevenue() {
    return revenue;
  }


  /**
   *  Gets the Employees attribute of the Organization object
   *
   * @return    The Employees value
   */
  public int getEmployees() {
    return employees;
  }


  /**
   *  Gets the ErrorMessage attribute of the Organization object
   *
   * @return    The ErrorMessage value
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the orgId attribute of the Organization object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the id attribute of the Organization object
   *
   * @return    The id value
   */
  public int getId() {
    return orgId;
  }


  /**
   *  Gets the Name attribute of the Organization object
   *
   * @return    The Name value
   */
  public String getName() {
    if (name != null && name.trim().length() > 0) {
      return name;
    }
    return this.getNameLastFirstMiddle();
  }


  /**
   *  Gets the accountNameOnly attribute of the Organization object
   *
   * @return    The accountNameOnly value
   */
  public String getAccountNameOnly() {
    return name;
  }


  /**
   *  Gets the Url attribute of the Organization object
   *
   * @return    The Url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the urlString attribute of the Organization object when a url or link
   *  needs to be displayed
   *
   * @return    The urlString value
   */
  public String getUrlString() {
    if (url != null) {
      if (url.indexOf("://") == -1) {
        return "http://" + url;
      }
    }
    return url;
  }


  /**
   *  Gets the LastModified attribute of the Organization object
   *
   * @return    The LastModified value
   */
  public String getLastModified() {
    return lastModified;
  }


  /**
   *  Gets the Notes attribute of the Organization object
   *
   * @return    The Notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the Industry attribute of the Organization object
   *
   * @return    The Industry value
   */
  public int getIndustry() {
    return industry;
  }


  /**
   *  Gets the AccountSize attribute of the Organization object adding account
   *  size to the ad account form
   *
   * @return    The Account size value
   */
  public int getAccountSize() {
    return accountSize;
  }


  /**
   *  Gets the IndustryName attribute of the Organization object
   *
   * @return    The IndustryName value
   */
  public String getIndustryName() {
    return industryName;
  }


  /**
   *  Gets the AccountSize attribute of the Organization object
   *
   * @return    The IndustryName value
   */
  public String getAccountSizeName() {
    return accountSizeName;
  }


  /**
   *  Gets the phone number attribute of the Organization object
   *
   * @param  thisType  Description of Parameter
   * @return           The PhoneNumber value
   */
  public String getPhoneNumber(String thisType) {
    return phoneNumberList.getPhoneNumber(thisType);
  }


  /**
   *  Gets the EmailAddress attribute of the Organization object
   *
   * @param  thisType  Description of Parameter
   * @return           The EmailAddress value
   */
  public String getEmailAddress(String thisType) {
    return emailAddressList.getEmailAddress(thisType);
  }


  /**
   *  Gets the Address attribute of the Organization object
   *
   * @param  thisType  Description of Parameter
   * @return           The Address value
   */
  public Address getAddress(String thisType) {
    return getAddressList().getAddress(thisType);
  }


  /**
   *  Gets the primaryEmailAddress attribute of the Organization object
   *
   * @return    The primaryEmailAddress value
   */
  public String getPrimaryEmailAddress() {
    return emailAddressList.getPrimaryEmailAddress();
  }


  /**
   *  Gets the primaryPhoneNumber attribute of the Organization object
   *
   * @return    The primaryPhoneNumber value
   */
  public String getPrimaryPhoneNumber() {
    return phoneNumberList.getPrimaryPhoneNumber();
  }


  /**
   *  Gets the primaryAddress attribute of the Organization object
   *
   * @return    The primaryAddress value
   */
  public Address getPrimaryAddress() {
    return getAddressList().getPrimaryAddress();
  }


  /**
   *  Gets the Enteredby attribute of the Organization object
   *
   * @return    The Enteredby value
   */
  public int getEnteredby() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Organization object
   *
   * @return    The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the Miner_only attribute of the Organization object
   *
   * @return    The Miner_only value
   */
  public boolean getMiner_only() {
    return minerOnly;
  }


  /**
   *  Gets the MinerOnly attribute of the Organization object
   *
   * @return    The MinerOnly value
   */
  public boolean getMinerOnly() {
    return minerOnly;
  }


  /**
   *  Gets the DirectBill attribute of the Organization object
   *
   * @return    The DirectBill value
   */
  public boolean getDirectBill() {
    return directBill;
  }

  public int getCessato() {
	    return cessato;
	  }


  /**
   *  Sets the forceDelete attribute of the Organization object
   *
   * @param  tmp  The new forceDelete value
   */
  public void setForceDelete(boolean tmp) {
    this.forceDelete = tmp;
  }


  /**
   *  Sets the forceDelete attribute of the Organization object
   *
   * @param  tmp  The new forceDelete value
   */
  public void setForceDelete(String tmp) {
    this.forceDelete = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the forceDelete attribute of the Organization object
   *
   * @return    The forceDelete value
   */
  public boolean getForceDelete() {
    return forceDelete;
  }



  /**
   * Gets the city attribute of the Organization object
   *
   * @return The city value
   */
  public String getCity() {
    return city;
  }


  /**
   * Sets the city attribute of the Organization object
   *
   * @param tmp The new city value
   */
  public void setCity(String tmp) {
    this.city = tmp;
  }
 
  /**
   * Sets the state attribute of the Organization object
   *
   * @param tmp The new state value
   */
	 public void setState(String tmp) {
	 	this.state = tmp;
	 }


	/**
   * Sets the postalCode attribute of the Organization object
   *
   * @param tmp The new postalCode value
   */
	 public void setPostalCode(String tmp) {
	 	this.postalCode = tmp;
	 }
	 
	 
  /**
   * Gets the postalCode attribute of the Organization object
   *
   * @return The postalCode value
   */
	 public String getPostalCode() {
	 	return postalCode;
	 }


  /**
   * Sets the county attribute of the Organization object
   *
   * @param tmp The new county value
   */
	 public void setCounty(String tmp) {
	 	this.county = tmp;
	 }


  /**
   * Gets the state attribute of the Organization object
   *
   * @return The state value
   */
	 public String getState() {
	 	return state;
	 }


  /**
   * Gets the county attribute of the Organization object
   *
   * @return The county value
   */
	 public String getCounty() {
	 	return county;
	 }


  /**
   *  Gets the trashed attribute of the Organization object
   *
   * @return    The trashed value
   */
  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   *  Gets the AddressList attribute of the Organization object
   *
   * @return    The AddressList value
   */
  public OrganizationAddressList getAddressList() {
    return addressList;
  }


  /**
   *  Gets the PhoneNumberList attribute of the Organization object
   *
   * @return    The PhoneNumberList value
   */
  public OrganizationPhoneNumberList getPhoneNumberList() {
    return phoneNumberList;
  }


  /**
   *  Gets the EmailAddressList attribute of the Organization object
   *
   * @return    The EmailAddressList value
   */
  public OrganizationEmailAddressList getEmailAddressList() {
    return emailAddressList;
  }


  /**
   *  Gets the isIndividual attribute of the Organization object
   *
   * @return    The isIndividual value
   */
  public boolean getIsIndividual() {
    return isIndividual;
  }


  /**
   *  Sets the isIndividual attribute of the Organization object
   *
   * @param  tmp  The new isIndividual value
   */
  public void setIsIndividual(boolean tmp) {
    this.isIndividual = tmp;
  }


  /**
   *  Sets the isIndividual attribute of the Organization object
   *
   * @param  tmp  The new isIndividual value
   */
  public void setIsIndividual(String tmp) {
    this.isIndividual = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * @return the businessNameTwo
   */
  public String getBusinessNameTwo() {
    return businessNameTwo;
  }


  /**
   * @return the dunsNumber
   */
  public String getDunsNumber() {
    return dunsNumber;
  }


  /**
   * @return the dunsType
   */
  public String getDunsType() {
    return dunsType;
  }


  /**
   * @return the sicCode
   */
  public int getSicCode() {
    return sicCode;
  }

	public String getSicDescription() {
		return sicDescription;
	}

  /**
   * @return the yearStarted
   */
  public int getYearStarted() {
    return yearStarted;
  }


  /**
   * @param businessNameTwo the businessNameTwo to set
   */
  public void setBusinessNameTwo(String businessNameTwo) {
    this.businessNameTwo = businessNameTwo;
  }


  /**
   * @param dunsNumber the dunsNumber to set
   */
  public void setDunsNumber(String dunsNumber) {
    this.dunsNumber = dunsNumber;
  }


  /**
   * @param dunsType the dunsType to set
   */
  public void setDunsType(String dunsType) {
    this.dunsType = dunsType;
  }


  /**
   * @param employees the employees to set
   */
  public void setEmployees(int employees) {
    this.employees = employees;
  }


  /**
   * @param sicCode the sicCode to set
   */
  public void setSicCode(int sicCode) {
    this.sicCode = sicCode;
  }


  /**
   * @param sicCode the sicCode to set
   */
  public void setSicCode(String sicCode) {
    this.sicCode = Integer.parseInt(sicCode);
  }

	public void setSicDescription(String tmp) {
		this.sicDescription = tmp;
	}

  /**
   * @param yearStarted the yearStarted to set
   */
  public void setYearStarted(int yearStarted) {
    this.yearStarted = yearStarted;
  }


  /**
   * @param yearStarted the yearStarted to set
   */
  public void setYearStarted(String yearStarted) {
    this.yearStarted = Integer.parseInt(yearStarted);
  }



  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  year           Description of Parameter
   * @param  type           Description of Parameter
   * @param  ownerId        Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void buildRevenueYTD(Connection db, int year, int type, int ownerId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT SUM(rv.amount) AS s " +
        "FROM revenue rv " +
        "WHERE rv.org_id = ? AND rv.year = ? AND rv.owner = ? ");
    if (type > 0) {
      sql.append("AND rv.type = ? ");
    }

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, orgId);
    pst.setInt(++i, year);
    pst.setInt(++i, ownerId);
    if (type > 0) {
      pst.setInt(++i, type);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setYTD(rs.getDouble("s"));
    }
    rs.close();
    pst.close();
  }


  /**
   *  sets the items in the type list to the the lookup list 'types'
   *  Organization object
   *
   * @param  db             The new typeListToTypes value
   * @throws  SQLException  Description of the Exception
   */
  public void setTypeListToTypes(Connection db) throws SQLException {
    Iterator itr = typeList.iterator();
    while (itr.hasNext()) {
      String tmpId = (String) itr.next();
      types.add(
          new LookupElement(
          db, Integer.parseInt(tmpId), "lookup_account_types"));
    }
  }


 

  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean disable(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE organization set enabled = ? " +
        "WHERE org_id = ? ");

    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, false);
    pst.setInt(++i, orgId);

    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }

    int resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 1) {
      success = true;
    }

    return success;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean enable(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE organization SET enabled = ? " +
        "WHERE org_id = ? ");
    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, true);
    pst.setInt(++i, orgId);
    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }
    int resultCount = pst.executeUpdate();
    pst.close();
    if (resultCount == 1) {
      success = true;
    }
    return success;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  checkName      Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean checkIfExists(Connection db, String checkName) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sqlSelect =
        "SELECT name, org_id " +
        "FROM organization " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(organization.name) = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, checkName.toLowerCase());
    rs = pst.executeQuery();
    if (rs.next()) {
      this.setDuplicateId(rs.getInt("org_id"));
      rs.close();
      pst.close();
      return true;
    } else {
      rs.close();
      pst.close();
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  lookupName     Description of the Parameter
   * @param  importId       Description of the Parameter
   * @param  siteId         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int lookupAccount(Connection db, String lookupName, int importId, int siteId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int lookupId = -1;
    String sqlSelect =
        "SELECT org_id " +
        "FROM organization o " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(o.name) = ? " +
        "AND (o.status_id IS NULL OR o.status_id = ? OR (o.status_id = ? AND o.import_id = ?) ) " +
        "AND " + (siteId > -1 ? "o.site_id = ? " : "o.site_id IS NULL") + " " +
        "AND o.trashed_date IS NULL " +
        "AND o.enabled = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, lookupName.toLowerCase());
    pst.setInt(++i, Import.PROCESSED_APPROVED);
    pst.setInt(++i, Import.PROCESSED_UNAPPROVED);
    pst.setInt(++i, importId);
    if (siteId > -1) {
      pst.setInt(++i, siteId);
    }
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    if (rs.next()) {
      lookupId = rs.getInt("org_id");
    }
    rs.close();
    pst.close();
    return lookupId;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  first             Description of the Parameter
   * @param  middle            Description of the Parameter
   * @param  last              Description of the Parameter
   * @param  importId          Description of the Parameter
   * @param  siteId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static int lookupAccount(Connection db, String first, String middle, String last, int importId, int siteId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int lookupId = -1;
    String sqlSelect =
        "SELECT org_id " +
        "FROM organization o " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(o.namefirst) = ? " +
        "AND " + DatabaseUtils.toLowerCase(db) + "(o.namemiddle) = ? " +
        "AND " + DatabaseUtils.toLowerCase(db) + "(o.namelast) = ? " +
        "AND (o.status_id IS NULL OR o.status_id = ? OR (o.status_id = ? AND o.import_id = ?) ) " +
        "AND " + (siteId > -1 ? "o.site_id = ? " : "o.site_id IS NULL") + " " +
        "AND o.trashed_date IS NULL " +
        "AND o.enabled = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, (first != null ? first.toLowerCase() : (String) null));
    pst.setString(++i, (middle != null ? middle.toLowerCase() : (String) null));
    pst.setString(++i, (last != null ? last.toLowerCase() : (String) null));
    pst.setInt(++i, Import.PROCESSED_APPROVED);
    pst.setInt(++i, Import.PROCESSED_UNAPPROVED);
    pst.setInt(++i, importId);
    if (siteId > -1) {
      pst.setInt(++i, siteId);
    }
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    if (rs.next()) {
      lookupId = rs.getInt("org_id");
    }
    rs.close();
    pst.close();
    return lookupId;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  tmpOrgId       Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int getOrganizationSiteId(Connection db, int tmpOrgId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int organizationSiteId = -1;
    String sqlSelect =
        "SELECT site_id " +
        "FROM organization " +
        "WHERE org_id = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setInt(++i, tmpOrgId);
    rs = pst.executeQuery();
    if (rs.next()) {
      organizationSiteId = DatabaseUtils.getInt(rs, "site_id");
    }
    rs.close();
    pst.close();
    return organizationSiteId;
  }


  public boolean checkEsistenzaImpresa(ActionContext context,Connection db,String ragioneSociale , String partitaIva , String citta, String indirizzo,String codFiscale, int type)
  {
	  boolean toReturn = false;
	  
	  String sql = "select o.name , o.partita_iva , oa.city, oa.addrline1 , o.codice_fiscale as cf from organization o, organization_address oa where o.org_id = oa.org_id and oa.address_type = "+type+" and o.tipologia =1" ;
	  		/*" and ((o.name = ? and o.partita_iva = ? and oa.city= ?) " +
	  		"or ( o.name=? and o.partita_iva and oa.addrline1 = ? )" +
	  		"or ())";*/
	  String ragioneSocialeE = "";
	  String partitaIvaE = "";
	  String cittaE = "" ;
	  String indirizzoE = "";
	  String cfE = "";
	  
	  try {
		PreparedStatement pst = db.prepareStatement(sql);
		/*pst.setString(1, ragioneSociale);
		pst.setString(2, partitaIva);
		pst.setString(3, citta);
		pst.setString(4, indirizzo);*/
		
		ResultSet rs = pst.executeQuery();
		int count = 0;
		while(rs.next() )
		{
			if(count<3)
			{
			
			
			String ragioneS = rs.getString("name");
			String piva = rs.getString("partita_iva");
			String city = rs.getString("city");
			String ind= rs.getString("addrline1");
			String codfisc = rs.getString("cf");
			
			if(ragioneS!=null && ragioneSociale.equalsIgnoreCase(ragioneS.trim()))
			{
				count = count+1;
				ragioneSocialeE = ragioneS;
			}
			if(piva!=null && partitaIva.equalsIgnoreCase(piva.trim()))
			{
				count = count+1;
				partitaIvaE = piva;
			}
			if(city!=null && citta.equalsIgnoreCase(city.trim()))
			{
				count = count+1;
				cittaE = city;
			}
			if(ind!=null && indirizzo.equalsIgnoreCase(ind.trim()))
			{
				count = count+1;
				indirizzoE = ind;
			}
			if(codfisc!=null && codFiscale.equalsIgnoreCase(codfisc.trim()))
			{
				count = count+1;
				cfE = codfisc;
			}
			if(count >=3)
			{
				break;
			}
			else
			{
				count=0;
				ragioneSocialeE = "";
				partitaIvaE = "";
				cittaE = "" ;
				indirizzoE = "";
				cfE = "";
			}
				
			}
		
			
			
		}
		if(!partitaIvaE.equals(""))
		{
			context.getRequest().setAttribute("piva", partitaIvaE);
		}
		if(!cittaE.equals(""))
		{
			context.getRequest().setAttribute("citta", cittaE);
		}
		if(!cfE.equals(""))
		{
			context.getRequest().setAttribute("cf", cfE);
		}
		if(!ragioneSocialeE.equals(""))
		{
			context.getRequest().setAttribute("rs", ragioneSocialeE);
		}
		
		if(!indirizzoE.equals(""))
		{
			context.getRequest().setAttribute("indirizzo", indirizzoE);
		}
		
		if(count>=3)
			return true;
		return false;
			
		
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  return toReturn;
	  
	  
  }
  
  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db,ActionContext context) throws SQLException {
    StringBuffer sql = new StringBuffer();
    boolean doCommit = false;
    try {
      modifiedBy = enteredBy;
      
      if (stageId==-1){
    	  LookupList stageList = new LookupList();
    	  stageList.tableName = "lookup_account_stage";
    	  stageList.setShowDisabledFlag(false);
    	  stageList.buildList(db);
    	  if (stageList.getFirstEnabledElement()>0){
    	   stageId = stageList.getFirstEnabledElement();
    	  }
      }
      
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      orgId = DatabaseUtils.getNextSeq(db,context, "organization","org_id");

      sql.append(
          "INSERT INTO organization (name, industry_temp_code, url, " +
          "miner_only, owner, duplicate_id, notes, employees, revenue, " +
          "ticker_symbol, account_number, codice_impresa_interno, namesalutation, namefirst, namelast, " +
          "namemiddle, trashed_date, segment_id,  direct_bill, account_size,  " +
          "sub_segment_id, site_id, source, rating, potential, " +
          "duns_number, business_name_two, year_started, sic_code, sic_description,ip_entered,ip_modified,  ");
      if (orgId > -1) {
        sql.append("org_id, ");
      }
      sql.append("entered, ");
      
      if (statusId > -1) {
        sql.append("status_id, ");
      }
      if (stageId > -1) {
          sql.append("stage_id, ");
        }
      if (importId > -1) {
        sql.append("import_id, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      
      //campi nuovi
      if (city_legale_rapp != null) {
          sql.append("city_legale_rapp, ");
      }
      if (address_legale_rapp != null) {
          sql.append("address_legale_rapp, ");
      }
      if (prov_legale_rapp != null) {
          sql.append("prov_legale_rapp, ");
      }
      
      
      if (partitaIva != null) {
          sql.append("partita_iva, ");
      }
      if (codiceFiscale != null) {
    	  sql.append("codice_fiscale, ");
      }
      if (abi != null) {
          sql.append("abi, ");
      }
      if (cab != null) {
    	  sql.append("cab, ");
      }
      if (cin != null) {
    	  sql.append("cin, ");
      }
      if (banca != null) {
    	  sql.append("banca, ");
      }
      if (contoCorrente != null) {
    	  sql.append("conto_corrente, ");
      }
      if (nomeCorrentista != null) {
    	  sql.append("nome_correntista, ");
      }
      if (codiceFiscaleCorrentista != null) {
    	  sql.append("cf_correntista, ");
      }
      
    
      // fine campi nuovi
      
      sql.append("enteredBy, modifiedBy");
      
//		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//      if ( codice1 != null && !"".equals( codice1 ) ) {
//    	  sql.append(", codice1 ");
//      }
//      if ( codice2 != null && !"".equals( codice2 ) ) {
//    	  sql.append(", codice2 ");
//      }
//      if ( codice3 != null && !"".equals( codice3 ) ) {
//    	  sql.append(", codice3 ");
//      }
//      if ( codice4 != null && !"".equals( codice4 ) ) {
//    	  sql.append(", codice4 ");
//      }
//      if ( codice5 != null && !"".equals( codice5 ) ) {
//    	  sql.append(", codice5 ");
//      }
//      if ( codice6 != null && !"".equals( codice6 ) ) {
//    	  sql.append(", codice6 ");
//      }
//      if ( codice7 != null && !"".equals( codice7 ) ) {
//    	  sql.append(", codice7 ");
//      }
//      if ( codice8 != null && !"".equals( codice8 ) ) {
//    	  sql.append(", codice8 ");
//      }
//      if ( codice9 != null && !"".equals( codice9 ) ) {
//    	  sql.append(", codice9 ");
//      }
//      if ( codice10 != null && !"".equals( codice10 ) ) {
//    	  sql.append(", codice10 ");
//      }
    
      if ( codiceCont != null && !"".equals( codiceCont ) ) {
    	  sql.append(", codice_cont ");
      }
      sql.append(", tipo_dest, tipo_struttura, tipo_locale,tipo_locale2,tipo_locale3, data_in_carattere, data_fine_carattere, cessazione");
      if (titoloRappresentante > -1 ) {
    	  sql.append(", titolo_rappresentante ");
      }
      if ( ( codiceFiscaleRappresentante != null) && !"".equals( codiceFiscaleRappresentante ) ) {
    	  sql.append(", codice_fiscale_rappresentante ");
      }
      if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
    	  sql.append(", nome_rappresentante ");
      }
      if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
    	  sql.append(", cognome_rappresentante ");
      }
      if ( ( emailRappresentante != null) && !"".equals( emailRappresentante ) ) {
    	  sql.append(", email_rappresentante ");
      }
      if ( ( telefonoRappresentante != null) && !"".equals( telefonoRappresentante ) ) {
    	  sql.append(", telefono_rappresentante ");
      }
      if ((dataNascitaRappresentante != null)&&(!dataNascitaRappresentante.equals(""))) {
    	  sql.append(", data_nascita_rappresentante ");
      }
      if ((dataPresentazione != null)&&(!dataPresentazione.equals(""))) {
    	  sql.append(", datapresentazione ");
      }
      if ( ( luogoNascitaRappresentante != null) && !"".equals( luogoNascitaRappresentante )) {
    	  sql.append(", luogo_nascita_rappresentante ");
      }
      if ( ( fax != null) && !"".equals( fax ) ) {
    	  sql.append(", fax ");
      }
      sql.append(", cessato, categoria_rischio");
      sql.append(")");
      sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
      sql.append("?,?,?,?,?,?,?,");
      if (orgId > -1) {
        sql.append("?,");
      }
      //if (entered != null) {
        sql.append("?, ");
      //}
      if (statusId > -1) {
        sql.append("?, ");
      }
      if (stageId > -1) {
          sql.append("?, ");
        }
      if (importId > -1) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      
      //campi nuovi
      if (city_legale_rapp != null) {
          sql.append("?, ");
      }
      if (address_legale_rapp != null) {
          sql.append("?, ");
      }
      if (prov_legale_rapp != null) {
          sql.append("?, ");
      }
      
      if (partitaIva != null) {
          sql.append("?, ");
      }
      if (codiceFiscale != null) {
          sql.append("?, ");
      }
      if (abi != null) {
          sql.append("?, ");
      }
      if (cab != null) {
          sql.append("?, ");
      }
      if (cin != null) {
          sql.append("?, ");
      }
      if (banca != null) {
          sql.append("?, ");
      }
      if (contoCorrente != null) {
          sql.append("?, ");
      }
      if (nomeCorrentista != null) {
          sql.append("?, ");
      }
      if (codiceFiscaleCorrentista != null) {
          sql.append("?, ");
      }
      
     
      //fine campi nuovi
      
      sql.append("?, ? ");
      
//		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//      if ( codice1 != null && !"".equals( codice1 ) ) {
//          sql.append(", ? ");
//      }
//      if ( codice2 != null && !"".equals( codice2 ) ) {
//          sql.append(", ? ");
//      }
//      if ( codice3 != null && !"".equals( codice3 ) ) {
//          sql.append(", ? ");
//      }
//      if ( codice4 != null && !"".equals( codice4 ) ) {
//          sql.append(", ? ");
//      }
//      if ( codice5 != null && !"".equals( codice5 ) ) {
//          sql.append(", ? ");
//      }
//      if ( codice6 != null && !"".equals( codice6 ) ) {
//          sql.append(", ? ");
//      }
//      if ( codice7 != null && !"".equals( codice7 ) ) {
//          sql.append(", ? ");
//      }
//      if ( codice8 != null && !"".equals( codice8 ) ) {
//          sql.append(", ? ");
//      }
//      if ( codice9 != null && !"".equals( codice9 ) ) {
//          sql.append(", ? ");
//      }
//      if ( codice10 != null && !"".equals( codice10 ) ) {
//          sql.append(", ? ");
//      }
    
      if ( codiceCont != null && !"".equals( codiceCont ) ) {
          sql.append(", ? ");
      }
      sql.append(", ?, ?, ?, ?,?,?");
      //aggiunti da d.dauria
      if (titoloRappresentante > -1) {
          sql.append(", ? ");
        }
      if ( ( codiceFiscaleRappresentante != null) && !"".equals( codiceFiscaleRappresentante) ) {
          sql.append(", ? ");
      }
      if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
          sql.append(", ? ");
      }
      if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
          sql.append(", ? ");
      }
      if ( ( emailRappresentante != null) && !"".equals( emailRappresentante ) ) {
          sql.append(", ? ");
      }
      if ( ( telefonoRappresentante != null) && !"".equals( telefonoRappresentante ) ) {
          sql.append(", ? ");
      }
      if ((dataNascitaRappresentante != null)&&(!dataNascitaRappresentante.equals(""))) {
    	  sql.append(", ? ");
      }
      if ((dataPresentazione != null)&&(!dataPresentazione.equals(""))) {
    	  sql.append(", ? ");
      }
      if ( ( luogoNascitaRappresentante != null) && !"".equals( luogoNascitaRappresentante ) ) {
    	  sql.append(", ? ");
      }
      if ( ( fax != null) && !"".equals( fax ) ) {
          sql.append(", ? ");
      }
      sql.append(", ?, (select categoria_default from lookup_codistat where description = ? )");
      sql.append(")");
      int i = 0;
      Timestamp dataCorrente = new Timestamp((new java.sql.Date(System.currentTimeMillis())).getTime());
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());
      pst.setInt(++i, this.getIndustry());
      pst.setString(++i, this.getUrl());
      pst.setBoolean(++i, this.getMinerOnly());
      DatabaseUtils.setInt(pst, ++i, this.getOwner());
      pst.setInt(++i, this.getDuplicateId());
      pst.setString(++i, this.getNotes());
      DatabaseUtils.setInt(pst, ++i, this.getEmployees());
      pst.setDouble(++i, this.getRevenue());
      pst.setString(++i, this.getTicker());
      pst.setString(++i, this.getAccountNumber());
      pst.setString(++i, this.getCodiceImpresaInterno());
      pst.setString(++i, this.getNameSalutation());
      pst.setString(++i, this.getNameFirst());
      pst.setString(++i, this.getNameLast());
      pst.setString(++i, this.getNameMiddle());
      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
      DatabaseUtils.setInt(pst, ++i, this.getSegmentId());
      pst.setBoolean(++i, this.getDirectBill());
      DatabaseUtils.setInt(pst, ++i, this.getAccountSize());
      DatabaseUtils.setInt(pst, ++i, this.getSubSegmentId());
      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
      DatabaseUtils.setInt(pst, ++i, this.getSource());
      DatabaseUtils.setInt(pst, ++i, this.getRating());
      pst.setDouble(++i, this.getPotential());
      pst.setString(++i, this.getDunsNumber());
      pst.setString(++i, this.getBusinessNameTwo());
      DatabaseUtils.setInt(pst, ++i, this.getYearStarted());
      DatabaseUtils.setInt(pst, ++i, this.getSicCode());
      pst.setString(++i, this.getSicDescription());
      pst.setString(++i, ip_entered);
      pst.setString(++i, ip_modified);
      
      if (orgId > -1) {
        pst.setInt(++i, orgId);
      }
      /*if (entered != null) {
        pst.setTimestamp(++i, entered);
      }*/
      
      DatabaseUtils.setTimestamp(
              pst, ++i, new Timestamp(System.currentTimeMillis()));
      
      //DatabaseUtils.setTimestamp(pst, ++i,dataCorrente);
     
      if (statusId > -1) {
        pst.setInt(++i, this.getStatusId());
      }
      if (stageId > -1) {
          pst.setInt(++i, this.getStageId());
      }
      if (importId > -1) {
        pst.setInt(++i, this.getImportId());
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      
      //campi nuovi
      if (city_legale_rapp != null) {
    	  pst.setString(++i, this.getCity_legale_rapp());
      }
      if (address_legale_rapp != null) {
    	  pst.setString(++i, this.getAddress_legale_rapp());
      }
      if (prov_legale_rapp != null) {
    	  pst.setString(++i, this.getProv_legale_rapp());
      }
      
      if (partitaIva != null) {
    	  pst.setString(++i, this.getPartitaIva());
      }
      if (codiceFiscale != null) {
    	  pst.setString(++i, this.getCodiceFiscale());
      }
      if (abi != null) {
    	  pst.setString(++i, this.getAbi());
      }
      if (cab != null) {
    	  pst.setString(++i, this.getCab());
      }
      if (cin != null) {
    	  pst.setString(++i, this.getCin());
      }
      if (banca != null) {
    	  pst.setString(++i, this.getBanca());
      }
      if (contoCorrente != null) {
    	  pst.setString(++i, this.getContoCorrente());
      }
      if (nomeCorrentista != null) {
    	  pst.setString(++i, this.getNomeCorrentista());
      }
      if (codiceFiscaleCorrentista != null) {
    	  pst.setString(++i, this.getCodiceFiscaleCorrentista());
      }
      
      //fine campi nuovi
      
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getModifiedBy());
      
//		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//      if ( codice1 != null && !"".equals( codice1 ) ) {
//    	  pst.setString(++i, this.getCodice1());
//      }
//      if ( codice2 != null && !"".equals( codice2 ) ) {
//    	  pst.setString(++i, this.getCodice2());
//      }
//      if ( codice3 != null && !"".equals( codice3 ) ) {
//    	  pst.setString(++i, this.getCodice3());
//      }
//      if ( codice4 != null && !"".equals( codice4 ) ) {
//    	  pst.setString(++i, this.getCodice4());
//      }
//      if ( codice5 != null && !"".equals( codice5 ) ) {
//    	  pst.setString(++i, this.getCodice5());
//      }
//      if ( codice6 != null && !"".equals( codice6 ) ) {
//    	  pst.setString(++i, this.getCodice6());
//      }
//      if ( codice7 != null && !"".equals( codice7 ) ) {
//    	  pst.setString(++i, this.getCodice7());
//      }
//      if ( codice8 != null && !"".equals( codice8 ) ) {
//    	  pst.setString(++i, this.getCodice8());
//      }
//      if ( codice9 != null && !"".equals( codice9 ) ) {
//    	  pst.setString(++i, this.getCodice9());
//      }
//      if ( codice10 != null && !"".equals( codice10 ) ) {
//    	  pst.setString(++i, this.getCodice10());
//      }
      
      if ( codiceCont != null && !"".equals( codiceCont ) ) {
    	  pst.setString(++i, this.getCodiceCont());
      }
      pst.setString(++i, this.getTipoDest());
      pst.setInt(++i, this.getTipoStruttura());
      pst.setInt(++i, this.getTipoLocale());
      pst.setInt(++i, this.getTipoLocale2());
      pst.setInt(++i, this.getTipoLocale3());
      pst.setTimestamp(++i, this.getDateI());
      pst.setTimestamp(++i, this.getDateF());
      pst.setBoolean(++i, this.getCessazione()); 
      if (titoloRappresentante > -1) {
          pst.setInt(++i, this.getTitoloRappresentante());
        }
      if ( ( codiceFiscaleRappresentante != null) && !"".equals( codiceFiscaleRappresentante ) ) {
    	  pst.setString(++i, this.getCodiceFiscaleRappresentante());
      }
      if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
    	  pst.setString(++i, this.getNomeRappresentante());
      }
      if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
    	  pst.setString(++i, this.getCognomeRappresentante());
      }
      if ( ( emailRappresentante != null) && !"".equals( emailRappresentante ) ) {
    	  pst.setString(++i, this.getEmailRappresentante());
      }
      if ( ( telefonoRappresentante != null) && !"".equals( telefonoRappresentante ) ) {
    	  pst.setString(++i, this.getTelefonoRappresentante());
      }
      if ((dataNascitaRappresentante != null)&&(!dataNascitaRappresentante.equals(""))) {
    	  pst.setTimestamp(++i, this.getDataNascitaRappresentante());
      }
      if ((dataPresentazione != null)&&(!dataPresentazione.equals(""))) {
    	  pst.setTimestamp(++i, this.getDataPresentazione());
      }
      if ( ( luogoNascitaRappresentante != null) && !"".equals( luogoNascitaRappresentante ) ) {
    	  pst.setString(++i, this.getLuogoNascitaRappresentante());
      }
      if ( ( fax != null) && !"".equals( fax ) ) {
    	  pst.setString(++i, this.getFax());
      }
      pst.setInt(++i, 0);
      pst.setString(++i, this.getCodiceFiscaleCorrentista());
      pst.execute();
      pst.close();

      

      //Insert the addresses if there are any
      Iterator iaddress = getAddressList().iterator();
      while (iaddress.hasNext()) {
    	  
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
        
        
        if((thisAddress.getCity()!=null) && !(thisAddress.getCity().equals(""))) {
            thisAddress.process(context,
                db, orgId, this.getEnteredBy(), this.getModifiedBy());
            }
      }

      this.update(db, true);
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  public int getTipoLocale2() {
	return TipoLocale2;
}

public void setTipoLocale2(int tipoLocale2) {
	TipoLocale2 = tipoLocale2;
}

public int getTipoLocale3() {
	return TipoLocale3;
}

public void setTipoLocale3(int tipoLocale3) {
	TipoLocale3 = tipoLocale3;
}

/**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int update(Connection db,ActionContext context) throws SQLException {
    int i = -1;
    boolean doCommit = false;
    try {
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      i = this.update(db, false);

      //Insert the addresses if there are any
      Iterator iaddress = getAddressList().iterator();
      while (iaddress.hasNext()) {
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
        
        if((thisAddress.getCity()!=null) && !(thisAddress.getCity().equals(""))) {
            thisAddress.process(context,
                db, orgId, this.getEnteredBy(), this.getModifiedBy());
            }
       
      }
  
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  newOwner       Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean reassign(Connection db, int newOwner) throws SQLException {
    int result = -1;
    this.setOwner(newOwner);
   

    if (result == -1) {
      return false;
    }

    return true;
  }





  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  override       Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE organization " +
        "SET name = ?, industry_temp_code = ?, " +
        "url = ?, notes= ?, ");

    if (!override) {
      sql.append("modified = ?, ");
    }
    
     sql.append(
        "modifiedby = ?, " +
        "employees = ?, revenue = ?, ticker_symbol = ?, account_number = ?, codice_impresa_interno = ?,ip_modified=?, ");

    if (owner > -1) {
      sql.append("owner = ?, ");
    }
   
   
    sql.append(
        "duplicate_id = ?, contract_end = ?, date1 = ?, date2 = ?, contract_end_timezone = ?, " +
        "alertdate = ?, alertdate_timezone=?, alert = ?, namesalutation = ?, namefirst = ?, " +
        "namemiddle = ?, namelast = ?, trashed_date = ?, segment_id = ?, " +
        "direct_bill = ?, account_size = ?, site_id = ?, sub_segment_id = ?, " +
        "source = ?, rating = ?, potential = ?, " +
        " duns_number = ?, business_name_two = ?, year_started = ?, sic_code = ?, sic_description = ?, ");
    sql.append("stage_id = ? ");
    
    //campi nuovi
	sql.append(", partita_iva = ?, " +
					"codice_fiscale = ?, " +
					"abi = ?, " +
					"cab = ?, " +
					"cin = ?, " +
					"banca = ?, " +
					"conto_corrente = ?,  " +
					"cf_correntista = ?, " +
//			 		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//					"codice1 = ?, " +
//					"codice2 = ?, " +
//					"codice3 = ?, " +
//					"codice4 = ?, " +
//					"codice5 = ?, " +
//					"codice6 = ?, " +
//					"codice7 = ?, " +
//					"codice8 = ?, " +
//					"codice9 = ?, " +
//					"codice10 = ?, " +
					"codice_cont = ?, " +
					"tipo_dest = ?, tipo_struttura = ?, tipo_locale = ?, tipo_locale2 = ?, tipo_locale3 = ?, data_in_carattere = ?,data_fine_carattere = ?, cessazione = ? ,titolo_rappresentante = ?, codice_fiscale_rappresentante = ?, nome_rappresentante = ?, cognome_rappresentante = ?, email_rappresentante = ?, telefono_rappresentante = ?, data_nascita_rappresentante =?, datapresentazione = ?, luogo_nascita_rappresentante = ?, fax = ?, cessato = ?, voltura=? ");
	
	if(nomeCorrentista!= null && ! "".equals(nomeCorrentista))
	{
		sql.append(" , nome_correntista = ? ");
	}
	
	if (city_legale_rapp != null) {
		sql.append(" , city_legale_rapp = ? ");
	     }
	     if (address_legale_rapp != null) {
	    	 sql.append(" , address_legale_rapp = ? ");
	     }
	     if (prov_legale_rapp != null) {
	    	 sql.append(" , prov_legale_rapp = ? ");
	     }
	
	 
	
	//fine campi nuovi

    sql.append("WHERE org_id = ? ");
   /* if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }
*/
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, name);
    pst.setInt(++i, industry);
    pst.setString(++i, url);
    pst.setString(++i, notes);
    if (!override) {
    	DatabaseUtils.setTimestamp(
                pst, ++i, new Timestamp(System.currentTimeMillis()));
       }
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, employees);
    pst.setDouble(++i, revenue);
    pst.setString(++i, ticker);
    pst.setString(++i, accountNumber);
    pst.setString(++i, codiceImpresaInterno);
    pst.setString(++i, ip_modified);
    if (owner > -1) {
      pst.setInt(++i, this.getOwner());
    }
    pst.setInt(++i, this.getDuplicateId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getContractEndDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDate1());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDate2());
    pst.setString(++i, this.getContractEndDateTimeZone());
    DatabaseUtils.setTimestamp(pst, ++i, this.getAlertDate());
    pst.setString(++i, this.getAlertDateTimeZone());
    pst.setString(++i, alertText);
    pst.setString(++i, this.getNameSalutation());
    pst.setString(++i, nameFirst);
    pst.setString(++i, nameMiddle);
    pst.setString(++i, nameLast);
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    DatabaseUtils.setInt(pst, ++i, segmentId);
    pst.setBoolean(++i, directBill);
    DatabaseUtils.setInt(pst, ++i, accountSize);
    DatabaseUtils.setInt(pst, ++i, siteId);
    DatabaseUtils.setInt(pst, ++i, subSegmentId);
    DatabaseUtils.setInt(pst, ++i, this.getSource());
    DatabaseUtils.setInt(pst, ++i, this.getRating());
    pst.setDouble(++i, this.getPotential());
    pst.setString(++i, this.getDunsNumber());
    pst.setString(++i, this.getBusinessNameTwo());
    DatabaseUtils.setInt(pst, ++i, this.getYearStarted());
    DatabaseUtils.setInt(pst, ++i, this.getSicCode());
    pst.setString(++i, this.getSicDescription());
    DatabaseUtils.setInt(pst, ++i, stageId);
    
    //campi nuovi
	pst.setString(++i, this.getPartitaIva());
    pst.setString(++i, this.getCodiceFiscale());
  	pst.setString(++i, this.getAbi());
  	pst.setString(++i, this.getCab());
  	pst.setString(++i, this.getCin());
  	pst.setString(++i, this.getBanca());
  	pst.setString(++i, this.getContoCorrente());  
  	
  
  	
  	pst.setString(++i, this.getCodiceFiscaleCorrentista()); 
//		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//  	pst.setString(++i, this.getCodice1()); 
//  	pst.setString(++i, this.getCodice2()); 
//  	pst.setString(++i, this.getCodice3()); 
//  	pst.setString(++i, this.getCodice4()); 
//  	pst.setString(++i, this.getCodice5()); 
//  	pst.setString(++i, this.getCodice6()); 
//  	pst.setString(++i, this.getCodice7()); 
//  	pst.setString(++i, this.getCodice8()); 
//  	pst.setString(++i, this.getCodice9()); 
//  	pst.setString(++i, this.getCodice10()); 
  	
  	pst.setString(++i, this.getCodiceCont()); 
  	pst.setString(++i, this.getTipoDest());
  	pst.setInt(++i, this.getTipoStruttura());
  	pst.setInt(++i, this.getTipoLocale());
  	pst.setInt(++i, this.getTipoLocale2());
  	pst.setInt(++i, this.getTipoLocale3());
  	DatabaseUtils.setTimestamp(pst, ++i, this.getDateI());
  	DatabaseUtils.setTimestamp(pst, ++i, this.getDateF());
  	pst.setBoolean(++i, this.getCessazione());
  	DatabaseUtils.setInt(pst, ++i, this.getTitoloRappresentante());
  	pst.setString(++i, this.getCodiceFiscaleRappresentante()); 
  	pst.setString(++i, this.getNomeRappresentante());
  	pst.setString(++i, this.getCognomeRappresentante());
  	pst.setString(++i, this.getEmailRappresentante()); 
	pst.setString(++i, this.getTelefonoRappresentante());
	DatabaseUtils.setTimestamp(pst, ++i, this.getDataNascitaRappresentante());
	DatabaseUtils.setTimestamp(pst, ++i, this.getDataPresentazione());
	pst.setString(++i, this.getLuogoNascitaRappresentante()); 
	pst.setString(++i, this.getFax());
	pst.setInt(++i, this.getCessato());
	pst.setBoolean(++i, this.getVoltura());
	if(nomeCorrentista!= null && ! "".equals(nomeCorrentista))
	{
  		pst.setString(++i, this.getNomeCorrentista());   
	}
		if (city_legale_rapp != null) {
	   	  pst.setString(++i, this.getCity_legale_rapp());
	     }
	     if (address_legale_rapp != null) {
	   	  pst.setString(++i, this.getAddress_legale_rapp());
	     }
	     if (prov_legale_rapp != null) {
	   	  pst.setString(++i, this.getProv_legale_rapp());
	     }
    //fine campi nuovi    
    
    pst.setInt(++i, orgId);
   /* if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }*/
 	  
    resultCount = pst.executeUpdate();
    pst.close();

    // When an account name gets updated,
    // the stored org_name in contact needs to be updated
    pst = db.prepareStatement(
        "UPDATE contact " +
        "SET org_name = ? " +
        "WHERE org_id = ? " +
        "AND org_name NOT LIKE ? ");
    pst.setString(1, name);
    pst.setInt(2, orgId);
    pst.setString(3, name);
    pst.executeUpdate();
    pst.close();

   
    return resultCount;
  }

  public int updateConVoltura(Connection db, boolean override) throws SQLException {
	    int resultCount = 0;
	    PreparedStatement pst = null;
	    StringBuffer sql = new StringBuffer();
	    sql.append(
	        "UPDATE organization " +
	        "SET industry_temp_code = ?, " +
	        "url = ?, notes= ?, ");

	    if (!override) {
	      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
	    }

	    sql.append(
	        "modifiedby = ?, " +
	        "employees = ?, revenue = ?, ticker_symbol = ?, account_number = ?, codice_impresa_interno = ?, ");

	    if (owner > -1) {
	      sql.append("owner = ?, ");
	    }
	   
	    sql.append(
	        "duplicate_id = ?, contract_end = ?, date1 = ?, date2 = ?, contract_end_timezone = ?, " +
	        "alertdate = ?, alertdate_timezone=?, alert = ?, namesalutation = ?, namefirst = ?, " +
	        "namemiddle = ?, namelast = ?, trashed_date = ?, segment_id = ?, " +
	        "direct_bill = ?, account_size = ?, site_id = ?, sub_segment_id = ?, " +
	        "source = ?, rating = ?, potential = ?, " +
	        "duns_number = ?, business_name_two = ?, year_started = ?, sic_code = ?, sic_description = ?, ");
	    sql.append("stage_id = ? ");
	    
	    //campi nuovi
		sql.append(", partita_iva = ?, " +
						"codice_fiscale = ?, " +
						"abi = ?, " +
						"cab = ?, " +
						"cin = ?, " +
						"conto_corrente = ?, " +
						"nome_correntista = ?, " +
						"cf_correntista = ?, " +
//				 		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//						"codice1 = ?, " +
//						"codice2 = ?, " +
//						"codice3 = ?, " +
//						"codice4 = ?, " +
//						"codice5 = ?, " +
//						"codice6 = ?, " +
//						"codice7 = ?, " +
//						"codice8 = ?, " +
//						"codice9 = ?, " +
//						"codice10 = ?, " +
						"codice_cont = ?,tipo_dest = ?, tipo_struttura = ?, tipo_locale = ?, tipo_locale2 = ?, tipo_locale3 = ?, data_in_carattere = ?,data_fine_carattere = ?, cessazione = ?, datapresentazione = ?,  cessato = ?, voltura=? ");
		//fine campi nuovi

	    sql.append("WHERE org_id = ? ");
	    if (!override) {
	      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
	    }

	    int i = 0;
	    pst = db.prepareStatement(sql.toString());
	    pst.setInt(++i, industry);
	    pst.setString(++i, url);
	    pst.setString(++i, notes);
	    pst.setInt(++i, this.getModifiedBy());
	    DatabaseUtils.setInt(pst, ++i, employees);
	    pst.setDouble(++i, revenue);
	    pst.setString(++i, ticker);
	    pst.setString(++i, accountNumber);
	    pst.setString(++i, codiceImpresaInterno);
	    if (owner > -1) {
	      pst.setInt(++i, this.getOwner());
	    }
	    pst.setInt(++i, this.getDuplicateId());
	    DatabaseUtils.setTimestamp(pst, ++i, this.getContractEndDate());
	    DatabaseUtils.setTimestamp(pst, ++i, this.getDate1());
	    DatabaseUtils.setTimestamp(pst, ++i, this.getDate2());
	    pst.setString(++i, this.getContractEndDateTimeZone());
	    DatabaseUtils.setTimestamp(pst, ++i, this.getAlertDate());
	    pst.setString(++i, this.getAlertDateTimeZone());
	    pst.setString(++i, alertText);
	    pst.setString(++i, this.getNameSalutation());
	    pst.setString(++i, nameFirst);
	    pst.setString(++i, nameMiddle);
	    pst.setString(++i, nameLast);
	    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
	    DatabaseUtils.setInt(pst, ++i, segmentId);
	    pst.setBoolean(++i, directBill);
	    DatabaseUtils.setInt(pst, ++i, accountSize);
	    DatabaseUtils.setInt(pst, ++i, siteId);
	    DatabaseUtils.setInt(pst, ++i, subSegmentId);
	    DatabaseUtils.setInt(pst, ++i, this.getSource());
	    DatabaseUtils.setInt(pst, ++i, this.getRating());
	    pst.setDouble(++i, this.getPotential());
	    pst.setString(++i, this.getDunsNumber());
	    pst.setString(++i, this.getBusinessNameTwo());
	    DatabaseUtils.setInt(pst, ++i, this.getYearStarted());
	    DatabaseUtils.setInt(pst, ++i, this.getSicCode());
	    pst.setString(++i, this.getSicDescription());
	    DatabaseUtils.setInt(pst, ++i, stageId);
	    
	    //campi nuovi
		pst.setString(++i, this.getPartitaIva());
	    pst.setString(++i, this.getCodiceFiscale());
	  	pst.setString(++i, this.getAbi());
	  	pst.setString(++i, this.getCab());
	  	pst.setString(++i, this.getCin());
	  	pst.setString(++i, this.getContoCorrente());  
	  	pst.setString(++i, this.getNomeCorrentista());   
	  	pst.setString(++i, this.getCodiceFiscaleCorrentista());
	  	
// 		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//	  	pst.setString(++i, this.getCodice1()); 
//	  	pst.setString(++i, this.getCodice2()); 
//	  	pst.setString(++i, this.getCodice3()); 
//	  	pst.setString(++i, this.getCodice4()); 
//	  	pst.setString(++i, this.getCodice5()); 
//	  	pst.setString(++i, this.getCodice6()); 
//	  	pst.setString(++i, this.getCodice7()); 
//	  	pst.setString(++i, this.getCodice8()); 
//	  	pst.setString(++i, this.getCodice9()); 
//	  	pst.setString(++i, this.getCodice10()); 
	  	
	  	pst.setString(++i, this.getCodiceCont()); 
	  	pst.setString(++i, this.getTipoDest());
	  	pst.setInt(++i, this.getTipoStruttura());
	  	pst.setInt(++i, this.getTipoLocale());
	  	pst.setInt(++i, this.getTipoLocale2());
	  	pst.setInt(++i, this.getTipoLocale3());
	  	DatabaseUtils.setTimestamp(pst, ++i, this.getDateI());
	  	DatabaseUtils.setTimestamp(pst, ++i, this.getDateF());
	  	pst.setBoolean(++i, this.getCessazione());
	  	DatabaseUtils.setTimestamp(pst, ++i, this.getDataPresentazione());
		pst.setInt(++i, this.getCessato());
		pst.setBoolean(++i, this.getVoltura());
	    //fine campi nuovi    
	    
	    pst.setInt(++i, orgId);
	    if (!override && this.getModified() != null) {
	      pst.setTimestamp(++i, this.getModified());
	    }
	 	  
	    resultCount = pst.executeUpdate();
	    pst.close();

	    // When an account name gets updated,
	    // the stored org_name in contact needs to be updated
	    pst = db.prepareStatement(
	        "UPDATE contact " +
	        "SET org_name = ? " +
	        "WHERE org_id = ? " +
	        "AND org_name NOT LIKE ? ");
	    pst.setString(1, name);
	    pst.setInt(2, orgId);
	    pst.setString(3, name);
	    pst.executeUpdate();
	    pst.close();

	   
	    return resultCount;
	  }


  /**
   *  Renames the organization running this database
   *
   * @param  db             Description of the Parameter
   * @param  newName        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public static void renameMyCompany(Connection db, String newName) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE organization " +
        "SET name = ? " +
        "WHERE org_id = 0 ");
    pst.setString(1, newName);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  baseFilePath   Description of Parameter
   * @param  context        Description of the Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean delete(Connection db, ActionContext context, String baseFilePath) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified.");
    }
    boolean commit = db.getAutoCommit();
    try { 
      if (commit) {
        db.setAutoCommit(false);
      }
 
      Statement st = db.createStatement();
      st.executeUpdate(
          "update organization set trashed_date = current_date WHERE org_id = " + this.getOrgId());
     
      st.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void deleteMinerOnly(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("The Organization could not be found.");
    }
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      
      Statement st = db.createStatement();
      st.executeUpdate("DELETE FROM news WHERE org_id = " + this.getOrgId());
      st.close();
      
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM organization " +
          "WHERE org_id = ? " +
          "AND miner_only = ? ");
      pst.setInt(1, this.getOrgId());
      pst.setBoolean(2, true);
      pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   *  Approves all records for a specific import
   *
   * @param  db             Description of the Parameter
   * @param  importId       Description of the Parameter
   * @param  status         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int updateImportStatus(Connection db, int importId, int status) throws SQLException {
    int count = 0;
    boolean commit = true;

    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      String sql = "UPDATE organization " +
          "SET status_id = ? " +
          "WHERE import_id = ? ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, status);
      pst.setInt(++i, importId);
      count = pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return count;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  toTrash        Description of the Parameter
   * @param  tmpUserId      Description of the Parameter
   * @param  context        Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean updateStatus(Connection db, ActionContext context, boolean toTrash, int tmpUserId) throws SQLException {
	    int count = 0;

	    boolean commit = true;

	    try {
	      commit = db.getAutoCommit();
	      if (commit) {
	        db.setAutoCommit(false);
	      }
	      StringBuffer sql = new StringBuffer();
	      sql.append(
	          "UPDATE organization " +
	          "SET trashed_date = ?, " +
	          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
	          "modifiedby = ? " +
	          "WHERE org_id = ? ");
	      int i = 0;
	      PreparedStatement pst = db.prepareStatement(sql.toString());
	      if (toTrash) {
	        DatabaseUtils.setTimestamp(
	            pst, ++i, new Timestamp(System.currentTimeMillis()));
	      } else {
	        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
	      }
	      pst.setInt(++i, tmpUserId);
	      pst.setInt(++i, this.getId());
	      count = pst.executeUpdate();
	      pst.close();

	      if (commit) {
	        db.commit();
	      }
	    } catch (SQLException e) {
	      e.printStackTrace();
	      if (commit) {
	        db.rollback();
	      }
	      throw new SQLException(e.getMessage());
	    } finally {
	      if (commit) {
	        db.setAutoCommit(true);
	      }
	    }
	    return true;
	  }



  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  thisImportId   Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static boolean deleteImportedRecords(Connection db, int thisImportId) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM organization_emailaddress " +
          "WHERE org_id IN (SELECT org_id from organization o where import_id = ? AND o.org_id = organization_emailaddress.org_id) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM organization_phone " +
          "WHERE org_id IN (SELECT org_id from organization o where import_id = ? AND o.org_id = organization_phone.org_id)");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM organization_address " +
          "WHERE org_id IN (SELECT org_id from organization o where import_id = ? AND o.org_id = organization_address.org_id) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM organization " +
          "WHERE import_id = ?");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Checks to see if the this account has any associated contacts
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean hasContacts(Connection db) throws SQLException {
    int recordCount = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) as recordcount " +
        "FROM contact " +
        "WHERE org_id = ? ");
    pst.setInt(1, this.getOrgId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = rs.getInt("recordCount");
    }
    rs.close();
    pst.close();
    if (recordCount > 0) {
      return true;
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Returned Value
   */
  public String toString() {
    StringBuffer out = new StringBuffer();
    out.append("=================================================\r\n");
    out.append("Organization Name: " + this.getName() + "\r\n");
    out.append("ID: " + this.getOrgId() + "\r\n");
    return out.toString();
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //organization table
	 city_legale_rapp = rs.getString("city_legale_rapp");
	 address_legale_rapp =rs.getString("address_legale_rapp");
	 prov_legale_rapp = rs.getString("prov_legale_rapp");
	 prossimoControllo = rs.getTimestamp("prossimo_controllo");
	categoriaRischio=rs.getInt("categoria_rischio");
	categoria_precedente=rs.getInt("categoria_precedente");
    this.setOrgId(rs.getInt("org_id"));
    orgIdC = rs.getInt("org_id_c");
    name = rs.getString("name");
    accountNumber = rs.getString("account_number");
    codiceImpresaInterno = rs.getString( "codice_impresa_interno");
    url = rs.getString("url");
    revenue = rs.getDouble("revenue");
    employees = DatabaseUtils.getInt(rs, "employees");
    notes = rs.getString("notes");
    ticker = rs.getString("ticker_symbol");
    //taxId = rs.getString("taxid");
    minerOnly = rs.getBoolean("miner_only");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    industry = rs.getInt("industry_temp_code");
    owner = DatabaseUtils.getInt(rs, "owner");
    stageId = DatabaseUtils.getInt(rs, "stage_id");
    duplicateId = rs.getInt("duplicate_id");
    contractEndDate = rs.getTimestamp("contract_end");
    date1 = rs.getTimestamp("date1");
    date2 = rs.getTimestamp("date2");
    alertDate = rs.getTimestamp("alertdate");
    alertText = rs.getString("alert");
    nameSalutation = rs.getString("namesalutation");
    nameLast = rs.getString("namelast");
    nameFirst = rs.getString("namefirst");
    nameMiddle = rs.getString("namemiddle");
    nameSuffix = rs.getString("namesuffix");
    importId = DatabaseUtils.getInt(rs, "import_id");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    alertDateTimeZone = rs.getString("alertdate_timezone");
    contractEndDateTimeZone = rs.getString("contract_end_timezone");
    trashedDate = rs.getTimestamp("trashed_date");
    source = DatabaseUtils.getInt(rs, "source");
    rating = DatabaseUtils.getInt(rs, "rating");
    potential = rs.getDouble("potential");
    segmentId = DatabaseUtils.getInt(rs, "segment_id");
    directBill = rs.getBoolean("direct_bill");
    accountSize = DatabaseUtils.getInt(rs, "account_size");
    subSegmentId = DatabaseUtils.getInt(rs, "sub_segment_id");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    dunsType = rs.getString("duns_type");
    dunsNumber = rs.getString("duns_number");
    businessNameTwo = rs.getString("business_name_two");
    sicCode = DatabaseUtils.getInt(rs, "sic_code");
    yearStarted = DatabaseUtils.getInt(rs, "year_started");
    sicDescription = rs.getString("sic_description");
    codiceImpresaGeneratoDa = rs.getInt("codice_impresa_generato_da");
    cambiatoInOsaDa = rs.getInt("cambiato_in_osa_da");
    tipologia = rs.getInt("tipologia");
    //contact table
  
    //industry table
    industryName = rs.getString("industry_name");

    //account size table
    accountSizeName = rs.getString("account_size_name");

	//organization address table
    city=rs.getString("o_city");
    
    //di prova
 
    
    state=rs.getString("o_state");
    postalCode=rs.getString("o_postalcode");
    county=rs.getString("o_county");
//    stageName=rs.getString("stage_name");
    
    //campi nuovi
	partitaIva = rs.getString("partita_iva");
	codiceFiscale = rs.getString("codice_fiscale");
	abi = rs.getString("abi");
	cab = rs.getString("cab");
	cin = rs.getString("cin");
	banca = rs.getString("banca");
	contoCorrente = rs.getString("conto_corrente");
	nomeCorrentista = rs.getString("nome_correntista");
	codiceFiscaleCorrentista = rs.getString("cf_correntista");
	
//		------  COMMENTATO PERCH� CAMBIATA LA GESTIONE DEI CODICI ATECO ------ NON CANCELLARE
//	codice1 = rs.getString("codice1");
//	codice2 = rs.getString("codice2");
//	codice3 = rs.getString("codice3");
//	codice4 = rs.getString("codice4");
//	codice5 = rs.getString("codice5");
//	codice6 = rs.getString("codice6");
//	codice7 = rs.getString("codice7");
//	codice8 = rs.getString("codice8");
//	codice9 = rs.getString("codice9");
//	codice10 = rs.getString("codice10");
	
	codiceCont = rs.getString("codice_cont");
	tipoDest = rs.getString("tipo_dest");
	TipoStruttura = rs.getInt("tipo_struttura");
	TipoLocale = rs.getInt("tipo_locale");
	TipoLocale2 = rs.getInt("tipo_locale2");
	TipoLocale3 = rs.getInt("tipo_locale3");
	dateI = rs.getTimestamp("data_in_carattere");
	dateF = rs.getTimestamp("data_fine_carattere");
	dataPresentazione = rs.getTimestamp("datapresentazione");
	cessazione = rs.getBoolean("cessazione");
    //fine campi nuovi
	titoloRappresentante = rs.getInt("titolo_rappresentante");
	codiceFiscaleRappresentante = rs.getString("codice_fiscale_rappresentante");
	nomeRappresentante = rs.getString("nome_rappresentante");
	cognomeRappresentante = rs.getString("cognome_rappresentante");
	nomeRappresentante = rs.getString("nome_rappresentante");
	emailRappresentante = rs.getString("email_rappresentante");
	telefonoRappresentante = rs.getString("telefono_rappresentante");
	dataNascitaRappresentante = rs.getTimestamp("data_nascita_rappresentante");
	dataNascitaRappresentante = rs.getTimestamp("data_nascita_rappresentante");
	luogoNascitaRappresentante = rs.getString("luogo_nascita_rappresentante");
    fax = rs.getString("fax");
    cessato = rs.getInt("cessato");
    voltura = rs.getBoolean("voltura");
    abusivo = rs.getBoolean("abusivo");
    dataChiusuraCanile = rs.getTimestamp("data_chiusura_canile");
    autorizzazione = rs.getString("autorizzazione");
  }


  /**
   *  Gets the nameLastFirstMiddle attribute of the Organization object
   *
   * @return    The nameLastFirstMiddle value
   */
  public String getNameLastFirstMiddle() {
    StringBuffer out = new StringBuffer();
    if (nameLast != null && nameLast.trim().length() > 0) {
      out.append(nameLast);
    }
    if (nameFirst != null && nameFirst.trim().length() > 0) {
      if (nameLast.length() > 0) {
        out.append(", ");
      }
      out.append(nameFirst);
    }
    if (nameMiddle != null && nameMiddle.trim().length() > 0) {
      if (nameMiddle.length() > 0) {
        out.append(" ");
      }
      out.append(nameMiddle);
    }
    if (out.toString().length() == 0) {
      return null;
    }
    return out.toString().trim();
  }


  /**
   *  Gets the properties that are TimeZone sensitive for a Call
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("alertDate");
    thisList.add("contractEndDate");
    return thisList;
  }


  /**
   *  Gets the numberParams attribute of the Organization class
   *
   * @return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("revenue");
    thisList.add("potential");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @param  tz         Description of the Parameter
   * @param  created    Description of the Parameter
   * @param  alertDate  Description of the Parameter
   * @param  user       Description of the Parameter
   * @param  category   Description of the Parameter
   * @param  type       Description of the Parameter
   * @return            Description of the Return Value
   */
  public String generateWebcalEvent(TimeZone tz, Timestamp created, Timestamp alertDate,
      User user, String category, int type) {

    StringBuffer webcal = new StringBuffer();
    String CRLF = System.getProperty("line.separator");

    String description = "";
    if (name != null) {
      description += "Account: " + name;
    }

    if (user != null && user.getContact() != null) {
      description += "\\nOwner: " + user.getContact().getNameFirstLast();
    }

    //write the event
    webcal.append("BEGIN:VEVENT" + CRLF);

    if (type == OrganizationEventList.ACCOUNT_EVENT_ALERT) {
      webcal.append(
          "UID:www.centriccrm.com-accounts-alerts-" + this.getOrgId() + CRLF);
    } else if (type == OrganizationEventList.ACCOUNT_CONTRACT_ALERT) {
      webcal.append(
          "UID:www.centriccrm.com-accounts-contract-alerts-" + this.getOrgId() + CRLF);
    }

    if (created != null) {
      webcal.append("DTSTAMP:" + ICalendar.getDateTimeUTC(created) + CRLF);
    }
    if (entered != null) {
      webcal.append("CREATED:" + ICalendar.getDateTimeUTC(entered) + CRLF);
    }
    if (alertDate != null) {
      webcal.append("DTSTART;TZID=" + tz.getID() + ":" + ICalendar.getDateTime(tz, alertDate) + CRLF);
    }
    if (type == OrganizationEventList.ACCOUNT_EVENT_ALERT) {
      if (alertText != null) {
        webcal.append(ICalendar.foldLine("SUMMARY:" + ICalendar.parseNewLine(alertText)) + CRLF);
      }
    } else if (type == OrganizationEventList.ACCOUNT_CONTRACT_ALERT) {
      webcal.append("SUMMARY:Account Contract Expiry!" + CRLF);
    }

    if (description != null) {
      webcal.append(ICalendar.foldLine("DESCRIPTION:" + ICalendar.parseNewLine(description)) + CRLF);
    }
    if (category != null) {
      webcal.append("CATEGORIES:" + category + CRLF);
    }

    webcal.append("END:VEVENT" + CRLF);

    return webcal.toString();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int countEmployees(Connection db, int id) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement("SELECT employees FROM organization WHERE org_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = rs.getInt("employees");
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @param  employeeCount     Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void updateEmployeeCount(Connection db, int id, int employeeCount) throws SQLException {
    StringBuffer sql = new StringBuffer();
    if (employeeCount != -1) {
      sql.append("UPDATE organization SET employees = ? WHERE org_id = ? AND employees <> ? ");
    } else {
      sql.append("UPDATE organization SET employees = ? WHERE org_id = ? AND employees IS NOT NULL ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, 1, employeeCount);
    pst.setInt(2, id);
    if (employeeCount != -1) {
      pst.setInt(3, employeeCount);
    }
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  typeId  Description of the Parameter
   * @return         Description of the Return Value
   */
  public boolean hasType(int typeId) {
    if (types != null) {
      Iterator i = types.iterator();
      while (i.hasNext()) {
        LookupElement element = (LookupElement) i.next();
        if (element.getCode() == typeId) {
          return true;
        }
      }
    }
    return false;
  }


  
  
  public int getLivelloRischio(Connection db) throws SQLException {
	    int livelloRischio = -1;
	    PreparedStatement pst = db.prepareStatement("SELECT livello_rischio FROM audit WHERE org_id = ? ORDER BY data_1 DESC");
	    pst.setInt(1, this.getOrgId());
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	      livelloRischio = rs.getInt("livello_rischio");
	    }
	    rs.close();
	    pst.close();
	    return livelloRischio;
  }
  public int getLivelloRischioFinale(Connection db) throws SQLException {
	    int livelloRischio = -1;
	    PreparedStatement pst = db.prepareStatement
	    	("SELECT livello_rischio_finale FROM audit WHERE org_id = ? ORDER BY data_2 IS NULL, data_2 DESC");
	    pst.setInt(1, this.getOrgId());
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	      livelloRischio = rs.getInt("livello_rischio_finale");
	    }
	    rs.close();
	    pst.close();
	    return livelloRischio;
}
  
  public Timestamp getDataAudit(Connection db) throws SQLException {
	  	Timestamp data = null;
	    PreparedStatement pst = db.prepareStatement
	    	("SELECT data_2 FROM audit WHERE org_id = ? ORDER BY data_2 IS NULL, data_2 DESC");
	    pst.setInt(1, this.getOrgId());
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	      data = rs.getTimestamp("data_2");
	    }
	    rs.close();
	    pst.close();
	    return data;
}

public java.sql.Timestamp getDateI() {
	return dateI;
}

public void setDateI(java.sql.Timestamp dateI) {
	this.dateI = dateI;
}


public java.sql.Timestamp getDateF() {
	return dateF;
}
public String getDateFString() {
	String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(dateF);
    } catch (NullPointerException e) {
    }
    return tmp;
}
public String getDateIString() {
	String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(dateI);
    } catch (NullPointerException e) {
    }
    return tmp;
}
public void setDateF(java.sql.Timestamp dateF) {
	this.dateF = dateF;
}

public void setDateF(String tmp) {
    this.dateF = DatabaseUtils.parseDateToTimestamp(tmp);
}
public void setDateI(String tmp) {
    this.dateI = DatabaseUtils.parseDateToTimestamp(tmp);
}

public void setComuni (Connection db, int codeUser) throws SQLException {
	
  	Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    
    //sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= (select site_id from organization where org_id="+ this.getOrgId() + "))");
    if(codeUser==-1){
        sql.append("select comune from comuni order by comune;");
    }else{
    sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= "+ codeUser + ")");
    //sql.append("select comune from comuni");
    }
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    
    while (rs.next()) {
      comuni.add(rs.getString("comune"));
    }
    rs.close();
    st.close();
  
}

public ResultSet osaReportAnalisi (Connection db, String urlQueryReport, String tipo, String provincia, String comune, String categoria, String dataDa, String dataA) throws SQLException {
	
  	Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    PreparedStatement pst = null;
    
    if(tipo.equals("osa")){
    String selectOSA = "SELECT OSA.* FROM (SELECT o.*, oa.addrline1, oa.city, oa.state, oa.country, oa.postalcode, oa.latitude, oa.longitude, oa.address_type, asl.description AS asl, audit.livello_rischio_finale"+
								" FROM organization o"+
								 " JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 5 AND o.tipologia = 1"+
								 " JOIN lookup_site_id asl ON o.site_id = asl.code"+
								 " LEFT JOIN audit audit ON o.org_id = audit.org_id"+ 
								 " WHERE oa.latitude > 0 AND oa.longitude > 0) as OSA";
    if(comune.equals("TUTTI") && provincia.equals("tutte") && categoria.equals("tutte") ){
    	selectOSA += ")";
		}else{
			selectOSA += " WHERE ";
		  if(!provincia.equals("tutte")){
			  selectOSA += "OSA.state = '" + provincia + "' ";
		  }
		  if(!comune.equals("TUTTI")){
		  	if(!provincia.equals("tutte")){
		  		selectOSA += " AND ";}
		  	selectOSA += " OSA.city = '" + comune + "' ";
		  }
		}
		 if(!categoria.equals("tutte")){
		 	if((!provincia.equals("tutte")) || (!comune.equals("TUTTI"))){
		 		selectOSA += " AND ";}
		 	selectOSA += " OSA.cf_correntista in (" + categoria + ")";
			}
		
		 
    sql.append(selectOSA);
    }else if(tipo.equals("sorveglianza")){
    	String selectVigilanza = "SELECT VIGILANZA.* FROM (SELECT c.*, o.alert, o.name, o.account_number, oa.addrline1, oa.city, oa.state, oa.country, oa.postalcode, oa.latitude, oa.longitude, oa.address_type, asl.description AS asl, esito.description as esito"+
    									   " FROM ticket c"+
    									   " JOIN organization o ON o.org_id = c.org_id" +
    									   " JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1 AND o.tipologia = 3 OR oa.org_id = o.org_id AND oa.address_type = 5 AND o.tipologia = 1 OR oa.org_id = o.org_id AND oa.address_type = 1 AND o.tipologia = 2" +
    									   " JOIN lookup_site_id asl ON o.site_id = asl.code " +
    									   " LEFT JOIN lookup_esito_campione esito ON c.sanzioni_amministrative = esito.code"+
    									   " WHERE oa.latitude > 0 AND oa.longitude > 0 AND c.tipologia = 3) as VIGILANZA";
		
  		if(provincia.equals("tutte")){
  			
  		}else {
  			selectVigilanza += " where VIGILANZA.state = '" + provincia + "'";
  			} 
    	sql.append(selectVigilanza);
    }else if(tipo.equals("campioni")){
    	String selectCampioni = "SELECT CAMPIONI.* FROM (SELECT c.*, o.alert, o.name, o.account_number, oa.addrline1, oa.city, oa.state, oa.country, oa.postalcode, oa.latitude, oa.longitude, oa.address_type, asl.description AS asl, esito.description as esito" +
    									  " FROM ticket c JOIN organization o ON o.org_id = c.org_id" +
    									  " JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1 AND o.tipologia = 3 OR oa.org_id = o.org_id AND oa.address_type = 5 AND o.tipologia = 1 OR oa.org_id = o.org_id AND oa.address_type = 1 AND o.tipologia = 2 JOIN lookup_site_id asl ON o.site_id = asl.code" +
    									  " LEFT JOIN lookup_esito_campione esito ON c.sanzioni_amministrative = esito.code"+
    									  " WHERE oa.latitude > 0 AND oa.longitude > 0 AND c.tipologia = 2) as CAMPIONI WHERE CAMPIONI.assigned_date <= '" + dataA + "' and CAMPIONI.assigned_date >= '" + dataDa + "'";
   	    sql.append(selectCampioni);
    }
    
    pst = db.prepareStatement(sql.toString());
    rs = pst.executeQuery();
    //pst.close();
    return rs;
    
}


public Vector getComuni () throws SQLException {
  /*
  Enumeration e=comuni.elements();
  
  while(e.hasMoreElements()) {
  }*/
 
  return comuni;
 
}

public String comuneOperativo(Connection db, int orgId) throws SQLException {
  int count = 0;
  String citta = "";
  
  StringBuffer sql = new StringBuffer();
  sql.append("SELECT o.city as citta FROM organization_address o WHERE o.org_id = ? AND o.address_type = 5");
  PreparedStatement pst = db.prepareStatement(sql.toString());
  pst.setInt(1, orgId);
  ResultSet rs = pst.executeQuery();
  if (rs.next()) {
    citta = rs.getString("citta");
  }
  rs.close();
  pst.close();
  return citta;
}


  //inserito da Francesco
  public String verificaAccountNumber(Connection db) throws SQLException {
    int count = 0;
    String msg = "";
    
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT COUNT(*) as itemcount FROM organization o WHERE account_number = ? AND trashed_date IS NULL ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setString(1, this.getAccountNumber());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    
    if (count > 0){
    	msg = "Organizzazione con codice " + this.getAccountNumber() + " gi� presente nel database";
    }
    
    rs.close();
    pst.close();
    return msg;
  }
  
  public String getCity2(Connection db, int orgid) throws SQLException {
		  	PreparedStatement st = null;
		    ResultSet rs = null;
		    String ret = null;
		    
		    StringBuffer sql = new StringBuffer();
		    sql.append("SELECT oa.city FROM organization_address oa " +
		    		"INNER JOIN organization o on (o.org_id = oa.org_id)" +
		    		" WHERE oa.address_type = 5 and oa.org_id = "+orgid+"");
		    st = db.prepareStatement(sql.toString());
		   // st.setString( 1, comune );
		    rs = st.executeQuery();
		    
		    if (rs.next()) {
		      ret = rs.getString("city");
		    }
		    rs.close();
		    st.close();
	  return ret;
	}
	
	public String getCity3(Connection db, int orgid) throws SQLException {
		  	PreparedStatement st = null;
		    ResultSet rs = null;
		    String ret = null;
		    
		    StringBuffer sql = new StringBuffer();
		    sql.append("SELECT oa.city FROM organization_address oa " +
		    		"INNER JOIN organization o on (o.org_id = oa.org_id)" +
		    		" WHERE oa.address_type = 7 and oa.org_id = "+orgid+"");
		    st = db.prepareStatement(sql.toString());
		   // st.setString( 1, comune );
		    rs = st.executeQuery();
		    
		    if (rs.next()) {
		      ret = rs.getString("city");
		    }
		    rs.close();
		    st.close();
	return ret;
	}
	
	  public boolean generaCodice (Connection db, String codice, boolean natocomedia) throws SQLException
	  {
		  //Controllo sull'orgId
		  if (this.getOrgId() == -1) {
		      throw new SQLException("Organization ID not specified");
		    }
		     boolean codEs = false;
		  	//Buffer di creazione query
		  	StringBuffer sql = new StringBuffer();
		  	 ResultSet rs=null;
		  	/*Il codicee'formato dalla parte relativa al comune,
		  	 * al codice dell'attivit�
		  	 * dal progressivo dell'ASL, e dal FLAG che rappresenta
		  	 * la D.I.A.*/
		    
		  	String codiceOsa=codice+this.getCodAttivita(db)+this.getAslProgressive(db)+this.getFlagDia(db);
		  	
		  	Timestamp dataCodice=new Timestamp(System.currentTimeMillis());
		  	
		  	StringBuffer sq = new StringBuffer();

		  	//Creazione della query per ottenere la ASL di appartenenza
		   sq.append(
				   "SELECT account_number " +
		      		"FROM organization " +
		      		"WHERE account_number = '"+ codiceOsa + "'" );  
		   		    
		      Statement ps = db.createStatement();
		      rs =ps.executeQuery(sq.toString());
		      if (rs.next()) {
		          codEs = true;
		          ps.close();
		          return codEs;
		        }else{
		    
		    /* Viene chiamato il metodo per ottenere il progressivo 
		     * della specifica ASL*/
		   		  
		    
		  	
		    /* Query che inserisce il codice osa nella tabella organization*/	    
		  	sql.append(
			        "UPDATE organization SET account_number = '"+codiceOsa+"', codice_impresa_generato_da = ? " +
			        ", data_attribuzione_codice= ?, nato_come_dia= ? "+
			        "WHERE org_id = "+ this.getOrgId() + "" );  
		     
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      pst.setInt( 1, this.modifiedBy );
		      pst.setTimestamp(2, dataCodice);
		      pst.setBoolean(3, natocomedia);
		      pst.executeUpdate();
		      ps.close();
		      pst.close();
		        }
		      return codEs;
	  }
	  
	  protected String getCodAttivita(Connection db) throws SQLException
	  {
		  //Result Set
		  ResultSet rs=null;
	      
		  //Codice attivit�
		  String codiceAttivita=null;
	      if (this.getOrgId() == -1) {
		      throw new SQLException("Organization ID not specified");
		    }
		  
		  	StringBuffer sql = new StringBuffer();
		     
		   //Creazione della query (cf_correntista rappresenta il cod. attivita')
		   sql.append("SELECT cf_correntista FROM organization WHERE org_id = "+ this.getOrgId() + " " );  
		     
		      Statement pst = db.createStatement();
		      rs =pst.executeQuery(sql.toString());
		      if (rs.next()) {
		    	  codiceAttivita = rs.getString(1);
		        }
		        pst.close();
		        
		 return codiceAttivita;
	  }
	  
	  /*Metodo che ritorna il progressivo asl recuperando
	   * prima l'asl di riferimento e poi invocando il metodo
	   * privato getProgressive*/
	  protected String getAslProgressive (Connection db) throws SQLException
	  {
		  //ResultSet
		  ResultSet rs=null;
	      
		  //NOME ASL
		  String asl=null;
		  int id_asl = -1;
	      
	      if (this.getOrgId() == -1) {
		      throw new SQLException("Organization ID not specified");
		    }
		  
		  	StringBuffer sql = new StringBuffer();

		  	//Creazione della query per ottenere la ASL di appartenenza
		   sql.append(
				   "SELECT code, description " +
		      		"FROM lookup_site_id " +
		      		"WHERE code = (SELECT site_id FROM organization WHERE org_id = "+ this.getOrgId() + ") " );  
		   		    
		      Statement pst = db.createStatement();
		      rs =pst.executeQuery(sql.toString());
		      if (rs.next()) {
		          asl = rs.getString("description");
		          id_asl = rs.getInt("code");
		        }
		    
		    /* Viene chiamato il metodo per ottenere il progressivo 
		     * della specifica ASL*/
		    Integer progressive=this.getProgressive(db, id_asl);  
		    
		  
		    pst.close();
		    
		    String result=null;
		    
		    //Il progressivo deve essere a 6 caratteri
		    result=org.aspcfs.utils.StringUtils.zeroPad(progressive,6);	    	
		  
		 
		  //Il progressivo per quella ASL viene ritornato       
		    //return progressive.toString();
		  return result;
	  }
	  
	  protected int getProgressive (Connection db, int asl) throws SQLException
	  {
		  ResultSet rs=null;
	      int progressive=0;
		  StringBuffer sql = new StringBuffer();
		  
		  switch (asl) {
		case 1:
			sql.append("SELECT nextval('avellino1_seq') " );  
			break;
		case 2:
			sql.append("SELECT nextval('avellino2_seq') " );
		break;
		case 3:
  			sql.append("SELECT nextval('benevento1_seq') " );
		break;
		case 4:
			sql.append("SELECT nextval('caserta1_seq') " );  
		break;
		case 5:
			sql.append("SELECT nextval('caserta2_seq') " );  
		break;
		case 6:
			sql.append("SELECT nextval('napoli1_seq') " );  
		break;
		case 7:
		sql.append("SELECT nextval('napoli2_seq') " );  
		break;
		case 8:
		sql.append("SELECT nextval('napoli3_seq') " );  
		break;
		case 9:
		sql.append("SELECT nextval('napoli4_seq') " );  
		break;
		case 10:
			sql.append("SELECT nextval('napoli5_seq') " );  
		break;
		case 11:
			sql.append("SELECT nextval('salerno1_seq') " );  
		break;
		case 12:
			sql.append("SELECT nextval('salerno2_seq') " );  
		break;
		case 13:
			sql.append("SELECT nextval('salerno3_seq') " );  
		break;
		}
		  
//		  	if (asl.equals("Benevento 1")) 
//		  		sql.append("SELECT nextval('benevento1_seq') " );
//		  	if (asl.equals("Avellino 1")) 
//		  		sql.append("SELECT nextval('avellino1_seq') " );  
//		  	if (asl.equals("Avellino 2")) 
//		  		sql.append("SELECT nextval('avellino2_seq') " );  
//		  	if (asl.equals("Caserta 1")) 
//		  		sql.append("SELECT nextval('caserta1_seq') " );  
//		  	if (asl.equals("Caserta 2")) 
//		  		sql.append("SELECT nextval('caserta2_seq') " );  
//		  	if (asl.equals("Caserta 3")) 
//		  		sql.append("SELECT nextval('caserta3_seq') " );  
//		  	if (asl.equals("Napoli 1")) 
//		  		sql.append("SELECT nextval('napoli1_seq') " );  
//		  	if (asl.equals("Napoli 2")) 
//		  		sql.append("SELECT nextval('napoli2_seq') " );  
//		  	if (asl.equals("Napoli 3")) 
//		  		sql.append("SELECT nextval('napoli3_seq') " );  
//		  	if (asl.equals("Napoli 4")) 
//		  		sql.append("SELECT nextval('napoli4_seq') " );  
//		  	if (asl.equals("Napoli 5")) 
//		  		sql.append("SELECT nextval('napoli5_seq') " );  
//		  	if (asl.equals("Salerno 1")) 
//		  		sql.append("SELECT nextval('salerno1_seq') " );  
//		  	if (asl.equals("Salerno 2")) 
//		  		sql.append("SELECT nextval('salerno2_seq') " );  
//		  	if (asl.equals("Salerno 3")) 
//		  		sql.append("SELECT nextval('salerno3_seq') " );  
		   		    
		      Statement pst = db.createStatement();
		      rs =pst.executeQuery(sql.toString());
		     
		      if (rs.next()) {
		    	  //Progressivo per quell'ASL
		    	  progressive= rs.getInt(1);
		        }
		       
		        pst.close();
		        
		 //Il codice del progressivo ASL viene ritornato      
		 return progressive;
		  
	  }
	  
	  protected String getFlagDia (Connection db) throws SQLException
	  {
 	      
	      ResultSet rs=null;
	      String flag=null;
	      if (this.getOrgId() == -1) {
		      throw new SQLException("Organization ID not specified");
		    }
		  
		  	StringBuffer sql = new StringBuffer();
		   sql.append(
				   "SELECT description " +
		      		"FROM lookup_requestor_stage " +
		      		"WHERE code = (SELECT stage_id FROM organization WHERE org_id = "+ this.getOrgId() + ") " );  
		     
		      Statement pst = db.createStatement();
		      rs =pst.executeQuery(sql.toString());
		     
		      if (rs.next()) {
		          flag = rs.getString(1);
		        }
		       
		        pst.close();

		        return flag.substring(0, 1);
  }
	  
	  public boolean checkDiaByPartitaIva(String partitaIva, Connection db) throws SQLException
	  {
		  boolean trovato = false;
		  PreparedStatement pst = db.prepareStatement("select * from organization where (tipologia = 1 or tipologia=0) and partita_iva = ? and trashed_date is null");
		  pst.setString(1, partitaIva);
		  ResultSet rs = pst.executeQuery();
		  if(rs.next())
		  {
			  trovato =true;
		  }
		  
		  return trovato ;
		  
	  }
	  

}

