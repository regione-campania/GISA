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
package org.aspcfs.modules.trasportoanimali.base;

import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.returnsclause;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.utils.ProjectUtils;
import com.zeroio.webdav.utils.ICalendar;

import org.aspcf.modules.report.util.ApplicationProperties;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.modules.trasportoanimali.base.OrganizationAddress;
import org.aspcfs.modules.trasportoanimali.base.OrganizationEmailAddress;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.RecipientList;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.mycfs.base.OrganizationEventList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.trasportoanimali.base.OrganizationPhoneNumber;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.TipoAnimaliRendicontazione;




import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;
import java.util.logging.*;
/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 *      Exp $
 */

public class Organization extends org.aspcfs.modules.accounts.base.Organization implements Serializable {
	
	
	transient Logger logger = Logger.getLogger("MainLogger");
	/**
	 * 
	 */
	private static final long serialVersionUID = 2588567327513331333L;

	//private static final long serialVersionUID = 1L;
	
	
	private String ip_entered;
	private String ip_modified ;


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
	protected double YTD = 0;
	
	/*Id azienda sanitaria*/
	private int siteId = -1;
	public void setSiteId(String tmp) {
		this.siteId = Integer.parseInt(tmp);
	}
	public int getSiteId() {
		return siteId;
	}
	
	/*Data richiesta -  date1*/
	private java.sql.Timestamp date1 = null;
	public java.sql.Timestamp getDate1() {
		return date1;
	}
	public void setDate1(java.sql.Timestamp val) {
	    this.date1 = val;
	}
	public void setDate1(String tmp) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (tmp!= null && !tmp.equals(""));
			this.date1 = new Timestamp(sdf.parse(tmp).getTime());
	}
	private String stato = "Attivo";
	public void setStato(String tmp) {
	    this.stato = tmp;
	  }
	public String getStato() {
	    return stato;
	  }
	
	/*Tipolgia dell'autorizzazione*/
	private String dunsType = null;
	public void setDunsType(String dunsType) {
		this.dunsType = dunsType;
	}
	public String getDunsType() {
		return dunsType;
	}
	
	
	/*Rappresentate*/
  	private int titoloRappresentante = -1;
  	public int getTitoloRappresentante() {
		return titoloRappresentante;
	}
	public void setTitoloRappresentante(int titoloRappresentante) {
		this.titoloRappresentante = titoloRappresentante;
	}
	
	private String codiceFiscaleRappresentante = null;
	public String getCodiceFiscaleRappresentante() {
		return codiceFiscaleRappresentante;
	}
	public void setCodiceFiscaleRappresentante(String codiceFiscaleRappresentante) {
		this.codiceFiscaleRappresentante = codiceFiscaleRappresentante;
	}
	private int specieA = -1;
	public void setSpecieA(String specie) {
	    try {
	      this.specieA = Integer.parseInt(specie);
	    } catch (Exception e) {
	      this.specieA = -1;
	    }
	  }
	private int anno = -1;
	public void setAnno(String anno) {
	    try {
	      this.anno = Integer.parseInt(anno);
	    } catch (Exception e) {
	      this.anno = -1;
	    }
	  }
	private int categoriaTrasportata = -1;
	public void setCategoriaTrasportata(String categoriaTrasportata) {
	    try {
	      this.categoriaTrasportata = Integer.parseInt(categoriaTrasportata);
	    } catch (Exception e) {
	      this.categoriaTrasportata = -1;
	    }
	  }
	int animaliPropri = -1;
	public void setAnimaliPropri(String specie) {
	    try {
	      this.animaliPropri = Integer.parseInt(specie);
	    } catch (Exception e) {
	      this.animaliPropri = -1;
	    }
	  }
	public int getSpecieA() {
		return specieA;
	}
	public int getAnno() {
		return anno;
	}
	public int getCategoriaTrasportata() {
		return categoriaTrasportata;
	}
	public int getAnimaliPropri() {
		return animaliPropri;
	}
	private String nomeRappresentante = null;
	public String getNomeRappresentante() {
		return nomeRappresentante;
	}
	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}
	
	private String cognomeRappresentante = null;
	public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}
	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}
	
	private java.sql.Timestamp dataNascitaRappresentante = null;
	public java.sql.Timestamp getDataNascitaRappresentante() {
		return dataNascitaRappresentante;
	}
	public void setDataNascitaRappresentante(String tmp) {
	    this.dataNascitaRappresentante = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	}
	public void setDataNascitaRappresentante(java.sql.Timestamp tmp) {
		this.dataNascitaRappresentante = tmp;
	}
	
	private String luogoNascitaRappresentante = null;
	public String getLuogoNascitaRappresentante() {
		return luogoNascitaRappresentante;
	}
	public void setLuogoNascitaRappresentante(String luogoRappresentante) {
		this.luogoNascitaRappresentante = luogoRappresentante;
	}
	
	private String emailRappresentante = null;
	public String getEmailRappresentante() {
		return emailRappresentante;
	}
	public void setEmailRappresentante(String emailRappresentante) {
		this.emailRappresentante = emailRappresentante;
	}
	
	private String telefonoRappresentante = null;
	public String getTelefonoRappresentante() {
		return telefonoRappresentante;
	}
	public void setTelefonoRappresentante(String telefonoRappresentante) {
		this.telefonoRappresentante = telefonoRappresentante;
	}
	private int statoLab = -1;
	public void setStatoLab(int tmp) {
	    this.statoLab = tmp;
	  }
	public void setStatoLab(String tmp) {
	    this.statoLab = Integer.parseInt(tmp);
	  }
	 public int getStatoLab() {
		    return statoLab;
		  }
	private String fax = null;
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax= fax;
	}
	
	private String targa;
	public String getTarga(){
		return this.targa;
	}
	public void setTarga(String targa){
		this.targa=targa;
	}
	
	
	private String statoImpresa="Attivo";
	public String getStatoImpresa(){
		return this.statoImpresa;
	}
	public void setStatoImpresa(String statoImpresa){
		this.statoImpresa=statoImpresa;
	}
	
	private Timestamp data_cambio_stato = null;
	private int utente_cambio_stato = -1;
	
	public Timestamp getData_cambio_stato() {
		return data_cambio_stato;
	}

	public void setData_cambio_stato(Timestamp data_cambio_stato) {
		this.data_cambio_stato = data_cambio_stato;
	}

	
	
	public int getUtente_cambio_stato() {
		return utente_cambio_stato;
	}

	public void setUtente_cambio_stato(int utente_cambio_stato) {
		this.utente_cambio_stato = utente_cambio_stato;
	}
	private String cod1="";
  public String getCod1() {
	return cod1;
}

public void setCod1(String cod1) {
	this.cod1 = cod1;
}

public String getCod2() {
	return cod2;
}

public void setCod2(String cod2) {
	this.cod2 = cod2;
}

public String getCod3() {
	return cod3;
}

public void setCod3(String cod3) {
	this.cod3 = cod3;
}

public String getCod4() {
	return cod4;
}

public void setCod4(String cod4) {
	this.cod4 = cod4;
}

public String getCod5() {
	return cod5;
}

public void setCod5(String cod5) {
	this.cod5 = cod5;
}

public String getCod6() {
	return cod6;
}

public void setCod6(String cod6) {
	this.cod6 = cod6;
}

public String getCod7() {
	return cod7;
}

public void setCod7(String cod7) {
	this.cod7 = cod7;
}

public String getCod8() {
	return cod8;
}

public void setCod8(String cod8) {
	this.cod8 = cod8;
}

public String getCod9() {
	return cod9;
}

public void setCod9(String cod9) {
	this.cod9 = cod9;
}

public String getCod10() {
	return cod10;
}

public void setCod10(String cod10) {
	this.cod10 = cod10;
}


private String cod2="";
  private String cod3="";
  private String cod4="";
  private String cod5="";
  private String cod6="";
  private String cod7="";
  private String cod8="";
  private String cod9="";
  private String cod10="";
  private String errorMessage = "";
  private int orgId = -1;
  private String name = "";
  private String url = "";
  private String lastModified = "";
  private String notes = "";
  private int industry = 0;
  private int accountSize = -1;
  private boolean directBill = false;
  private String accountSizeName = null;
  private int listSalutation = -1;
  private int segmentList = -1;
  
  private int stageId = -1;
  private String stageName = null; 
  private String siteClient = null;
  public int segmentId = -1;
  private int subSegmentId = -1;
  private String industryName = null;
  private boolean minerOnly = false;
  private int enteredBy = -1;
  private int TipoStruttura = -1;
  private int TipoLocale = -1;
  private String numAut = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp contractEndDate = null;
  
  private java.sql.Timestamp date2 = null;
  
  private java.sql.Timestamp date3 = null;
  private java.sql.Timestamp date4 = null;
  private java.sql.Timestamp dateI = null;
  private java.sql.Timestamp dateF = null;
  
  private java.sql.Timestamp alertDate = null;
  private String alertText = "";

  //private int modifiedBy = -1;
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
  private String city2 = null;
  private String city3 = null;
  private String state = null;
  private String postalCode = null;
  private String county = null;
  private HashMap<Integer,String> listaAnimali=new HashMap<Integer, String>();
  public HashMap<Integer,String> getListaAnimali() {
		return listaAnimali;
	}
	public void setListaAnimali(HashMap<Integer,String> listaAnimali) {
		this.listaAnimali = listaAnimali;
	}
	
	private HashMap<Integer,String> listaCategoria=new HashMap<Integer, String>();
	  public HashMap<Integer,String> getListaCategoria() {
			return listaCategoria;
		}
  public void setListaCategoria(HashMap<Integer,String> listaCategoria) {
			this.listaCategoria = listaCategoria;
		}
  private OrganizationAddressList addressList = new OrganizationAddressList();
  //private OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
  //private OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
  private String ownerName = "";
  private String enteredByName = "";
  private String modifiedByName = "";

  private LookupList types = new LookupList();
  private ArrayList typeList = null;

  private boolean contactDelete = false;
  private boolean revenueDelete = false;
  private boolean documentDelete = false;
  
  private boolean cessazione = false;

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
	private int richiedenti=0;
	private String codice1 = null;
	private String codice2 = null;
	private String codice3 = null;
	private String codice4 = null;
	private String codice5 = null;
	private String codice6 = null;
	private String codice7 = null;
	private String codice8 = null;
	private String codice9 = null;
	private String codice10 = "";
	private String codiceCont = null;
	private String tipoDest = null;

	// fine campi nuovi - progetto STI
	
	//Vettore dei comuni
	private Vector comuni=new Vector();
	
  	// campi nuovi - metodi get e set
	
	
	
	
	
	//Campi Trasporto animali - Salvatore
	


	

	

	

	public String getCodice1() {
		return codice1;
	}

	public void setCodice1(String codice1) {
		this.codice1 = codice1;
			}
	
	public String getCodiceCont() {
		return codiceCont;
	}

	public String getNumAut() {
		return numAut;
	}
	
	public void setNumAut(String numAut) {
		this.numAut = numAut;
	}
	
	public void setCodiceCont(String codiceCont) {
		this.codiceCont = codiceCont;
			}
	
	public String getTipoDest() {
		return tipoDest;
	}

	public void setTipoDest(String tipoDest) {
		this.tipoDest = tipoDest;
			}
	
	public String getCodice2() {
		return codice2;
	}

	public void setCodice2(String codice2) {
		this.codice2 = codice2;
	}
	
	public String getCodice3() {
		return codice3;
	}

	public void setCodice3(String codice3) {
		this.codice3 = codice3;
	}
	
	public String getCodice4() {
		return codice4;
	}

	public void setCodice4(String codice4) {
		this.codice4 = codice4;
	}
	
	public String getCodice5() {
		return codice5;
	}

	public void setCodice5(String codice5) {
		this.codice5 = codice5;
	}
	
	public String getCodice6() {
		return codice6;
	}

	public void setCodice6(String codice6) {
		this.codice6 = codice6;
	}
	
	public String getCodice7() {
		return codice7;
	}

	public void setCodice7(String codice7) {
		this.codice7 = codice7;
	}
	
	public String getCodice8() {
		return codice8;
	}

	public void setCodice8(String codice8) {
		this.codice8 = codice8;
	}
	
	public String getCodice9() {
		return codice9;
	}

	public void setCodice9(String codice9) {
		this.codice9 = codice9;
	}
	
	public String getCodice10() {
		return codice10;
	}

	public void setCodice10(String codice10) {
		this.codice10 = codice10;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public int getRichiedenti() {
		return richiedenti;
	}

	public void setRichiedenti(int richiedenti) {
		this.richiedenti = richiedenti;
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
	//fine campi nuovi - metodi get e set

	
  /**
   *  Constructor for the Organization object, creates an empty Organization
   *
   * @since    1.0
   */
  public Organization() { }
  
  private ArrayList<Veicolo> listaV=new ArrayList<Veicolo>();

  public ArrayList<Veicolo> getListaV(ResultSet rs) throws SQLException {
	  while(rs.next()){
		  int org_id=rs.getInt("org_id");
		  String targa=rs.getString("targa");
		  String descrizione=rs.getString("descrizione");
		  boolean lunghi_viaggi = rs.getBoolean("lunghi_viaggi");
		  boolean accepted = rs.getBoolean("accepted");

		 
		 Veicolo v=new Veicolo(org_id,descrizione,targa,lunghi_viaggi,accepted);

		 v.setOrg_id(org_id);
		 listaV.add(v);
		  
		  
		  
	  }
	  
	return listaV;
}

public void setListaV(ArrayList<Veicolo> listaV) {
	this.listaV = listaV;
}

private ArrayList<Sede> listaS=new ArrayList<Sede>();




public ArrayList<Personale> getListaPersonale(ResultSet rs) throws SQLException {
	  while(rs.next()){
		  String cf=rs.getString("cf");
			String nome=rs.getString("nome");
			String cognome=rs.getString("cognome");
			String mansione=rs.getString("mansione");
		
			Personale p=new Personale(cf,nome,cognome,mansione);
			
			listaP.add(p); 
		  
	  }
	  
	return listaP;
}



private boolean sediCaricate=false;




public boolean isSediCaricate() {
	return sediCaricate;
}
public void setSediCaricate(boolean sediCaricate) {
	this.sediCaricate = sediCaricate;
}
public ArrayList<Sede> getListaS(ResultSet rs) throws SQLException {
	  while(rs.next()){
		  this.setSediCaricate(true);
		  int id=rs.getInt("id");
			String comune=rs.getString("comune");
			String indirizzo=rs.getString("indirizzo");
			String provincia=rs.getString("provincia");
			String latitudine=rs.getString("latitudine");
			String longitudine=rs.getString("longitudine");
			String cap=rs.getString("cap");
			String stato=rs.getString("stato");
			String elimina="elimina";
			Sede dist=new Sede(comune,indirizzo,provincia,cap,stato);
			dist.setId(id);
			dist.setOrg_id(rs.getInt("org_id"));
			dist.setElimina(elimina);
			listaS.add(dist); 
		  
	  }
	  
	return listaS;
}

public void setListaS(ArrayList<Sede> listaS) {
	this.listaS = listaS;
}

private ArrayList<Personale> listaP=new ArrayList<Personale>();

public ArrayList<Personale> getListaP(ResultSet rs) throws SQLException {
	  while(rs.next()){
		  	String cf=rs.getString("cf");
			String comune=rs.getString("nome");
			String indirizzo=rs.getString("cognome");
			String provincia=rs.getString("mansione");
					
			Personale dist=new Personale(cf,comune,indirizzo.replaceAll("_", "'"),provincia);
			dist.setOrg_id(rs.getInt("org_id"));
			listaP.add(dist); 
		  
	  }
	  
	return listaP;
}

public void setListaP(ArrayList<Personale> listaP) {
	this.listaP = listaP;
}

public void updateSede(Connection db) throws SQLException{
	
	
	PreparedStatement pst=db.prepareStatement("delete  from organization_sediveicoli where org_id="+orgId);
	pst.execute();
	
	for(Sede sede : listaS){
		
		sede.insert(db, orgId);
		
	
	}	
}

public void updatePersonale(Connection db) throws SQLException{
	
	
	PreparedStatement pst=db.prepareStatement("delete  from organization_personale where org_id="+orgId);
	pst.execute();
	
	for(Personale personale : listaP){
		
		personale.insert(db, orgId);
		
	
	}
	
	
	
}

public void updateVeicoli(Connection db) throws SQLException{
	
	
	PreparedStatement pst=db.prepareStatement("delete  from organization_autoveicoli where org_id="+orgId);
	pst.execute();
	
	for(Veicolo veicolo : listaV){
		
		veicolo.insert(db, orgId);
		
	
	}
	
	
	
}

  public void insertVeicolo(Connection db) throws SQLException{
		
		for(Veicolo veicoli : listaV){
			
			veicoli.insert(db, orgId);
				
		}

	}

  public void insertSede(Connection db) throws SQLException{
		
		for(Sede sedi : listaS){
			
			sedi.insert(db, orgId);
				
		}

	}
  
 public ArrayList<Veicolo> getVeicoli(Connection db,int org_id){
		
		ArrayList<Veicolo> lista=new ArrayList<Veicolo>();
		try{
			
			//LookupList tipoDitributore = new LookupList(db, "lookup_tipo_distributore");
		     
			
			String sql="select * from organization_autoveicoli where org_id="+org_id;
			java.sql.PreparedStatement pst=db.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){

				String targa=rs.getString("targa");
				String descrizione=rs.getString("descrizione");
				boolean lunghi_viaggi = rs.getBoolean("lunghi_viaggi");
				boolean accepted = rs.getBoolean("accepted");
				Veicolo dist=new Veicolo(org_id,targa,descrizione,lunghi_viaggi,accepted);
				//dist.setDescrizioneTipoAlimenti(description);
				lista.add(dist);
				
			}
			
			
			
			
			
			
			
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return lista;
		
		
	}
	
public ArrayList<Sede> getSede(Connection db,int org_id){
		
		ArrayList<Sede> lista=new ArrayList<Sede>();
		try{
			
			//LookupList tipoDitributore = new LookupList(db, "lookup_tipo_distributore");
		     
			
			String sql="select * from organization_sediveicoli where org_id="+org_id;
			java.sql.PreparedStatement pst=db.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				
				Integer id=rs.getInt("id");
				String comune=rs.getString("comune");
				String indirizzo=rs.getString("indirizzo");
				String provincia=rs.getString("provincia");
				String latitudine=rs.getString("latitudine");
				String longitudine=rs.getString("longitudine");
				String cap=rs.getString("cap");
				String stato=rs.getString("stato");
				
				Sede dist=new Sede(comune, indirizzo, provincia, cap, stato);
				//dist.setDescrizioneTipoAlimenti(description);
				lista.add(dist);
				
			}
			
			
			
			
			
			
			
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return lista;
		
		
	}

public ArrayList<Personale> getPersonale(Connection db,int org_id){
	
	ArrayList<Personale> lista=new ArrayList<Personale>();
	try{
		
		//LookupList tipoDitributore = new LookupList(db, "lookup_tipo_distributore");
	     
		
		String sql="select * from organization_personale where org_id="+org_id;
		java.sql.PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			
			String cf=rs.getString("cf");
			String nome=rs.getString("nome");
			String cognome=rs.getString("cognome");
			String mansione=rs.getString("mansione");
						
			Personale dist=new Personale(cf,nome, cognome, mansione);
			//dist.setDescrizioneTipoAlimenti(description);
			lista.add(dist);
			
		}
		
		
		
		
		
		
		
		
	}catch(Exception e){
		
		e.printStackTrace();
	}
	return lista;
	
	
}

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
  private HashMap<Integer,String> lista=new HashMap<Integer, String>();
  
  private HashMap<TipoAnimaliRendicontazione, HashMap<Integer,Integer>> hash_animali_trasportati = new HashMap<TipoAnimaliRendicontazione, HashMap<Integer,Integer>>();
  
  
  public HashMap<TipoAnimaliRendicontazione, HashMap<Integer, Integer>> getHash_animali_trasportati() {
	return hash_animali_trasportati;
  }

  public void setHash_animali_trasportati(
		HashMap<TipoAnimaliRendicontazione, HashMap<Integer, Integer>> hash_animali_trasportati) {
	this.hash_animali_trasportati = hash_animali_trasportati;
  }

public HashMap<Integer,String> getLista() {
		return lista;
	}
	public void setLista(HashMap<Integer,String> lista) {
		this.lista = lista;
	}
	
	
	
	
	public void insertVeicoli(Connection db) throws SQLException{
		
		for(Veicolo veicoli : listaV){
			
			veicoli.insert(db, orgId);
			
		
		}
		
		
		
	}
public void insertPersonale(Connection db) throws SQLException{
		
		for(Personale personale : listaP){
			
			personale.insert(db, orgId);
			
		
		}
		
		
		
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
        "'' as stage_name, oauto.targa as targa, atras.tipo_animale as animali, ctras.categoria_animale as categoria  "+
        "FROM organization o " +
        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " +
        "LEFT JOIN organization_autoveicoli oauto ON (o.org_id = oauto.org_id) " +
        "LEFT JOIN animalitrasportati atras ON (o.org_id = atras.org_id) " +
        "LEFT JOIN categoriatrasportati ctras ON (o.org_id = ctras.org_id) " +
        "WHERE o.org_id = ?" +
        " AND (oa.address_id IS NULL OR oa.address_id IN ( "
		+ "SELECT ora.address_id FROM organization_address ora WHERE ora.org_id = o.org_id AND ora.primary_address = ?) "
		+ "OR oa.address_id IN (SELECT MIN(ctodd.address_id) FROM organization_address ctodd WHERE ctodd.org_id = o.org_id AND "
		+ " ctodd.org_id NOT IN (SELECT org_id FROM organization_address WHERE organization_address.primary_address = ?)))");
    pst.setInt(1, org_id);
    pst.setBoolean(2, true);
    pst.setBoolean(3, true);
    ResultSet rs =pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
      if(rs.getString("duns_type").equals("tipo4")){
    	  this.setAnimaliSelezionatiTipo4(db, this.orgId);
      }else{
    	  this.setAnimaliSelezionati(db, this.orgId);
      }
      this.setCategoriaSelezionati(db, this.orgId);
      
    }
    
    pst = db.prepareStatement("select * from stato_impresa where org_id = ? and entered = (select max(entered) from stato_impresa where org_id = ?)");
    pst.setInt(1, org_id);
    pst.setInt(2, org_id);
    rs = pst.executeQuery();
    if(rs.next()){
    	//data_cambio_stato = rs.getTimestamp("entered");
    	
    	utente_cambio_stato = rs.getInt("enteredby");
    }
    
    rs.close();
    pst.close();
    if (orgId == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    
    //if this is an individual account, populate the primary contact record
  
   // phoneNumberList.setOrgId(this.getOrgId());
   // phoneNumberList.buildList(db);
    addressList.setOrgId(this.getOrgId());
    addressList.buildList(db);
//    emailAddressList.setOrgId(this.getOrgId());
//    emailAddressList.buildList(db);
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


  
  
  public int update(Connection db) throws SQLException {
	    int i = -1;
	    boolean doCommit = false;
	    try {
	      if (doCommit = db.getAutoCommit()) {
	        db.setAutoCommit(false);
	      }
	     
	      i = this.update(db, false);
	      //Process the phone numbers if there are any
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

  
  public void setAnimaliSelezionati(Connection db,int Orgid) throws SQLException{
		
		
		String sql ="select code, description from lookup_specie_trasportata where code in (select tipo_animale from animalitrasportati where org_id=?)";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, Orgid);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){
			
			this.listaAnimali.put(rst.getInt(1),rst.getString(2));
			
		}
		
		
		
		
	}
  
  public void setAnimaliSelezionatiTipo4(Connection db,int Orgid) throws SQLException{
		
		
		String sql ="select code, description from lookup_specie_trasportata_tipo4 where code in (select tipo_animale from animalitrasportati where org_id=?)";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, Orgid);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){
			
			this.listaAnimali.put(rst.getInt(1),rst.getString(2));
			
		}
		
		
		
		
	}
  
  public void setCategoriaSelezionati(Connection db,int Orgid) throws SQLException{
		
		
		String sql ="select code, description from lookup_categoria_trasportata where code in (select categoria_animale from categoriatrasportati where org_id=?)";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, Orgid);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){
			
			this.listaCategoria.put(rst.getInt(1),rst.getString(2));
			
		}
		
		
		
		
	}

  public void setAnimaliSelezionatiPropri(Connection db,int Orgid) throws SQLException{
		
		
		String sql ="select code, description from lookup_animali_propri where code in (select tipo_animale from animalitrasportati where org_id=?)";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, Orgid);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){
			
			this.listaAnimali.put(rst.getInt(1),rst.getString(2));
			
		}
		
		
		
		
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
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


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
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Organization object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
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
  
  public void setDate2(java.sql.Timestamp val) {
	    this.date2 = val;
  }
 public void setDate3(java.sql.Timestamp val) {
	    this.date3 = val;
}
public void setDate4(java.sql.Timestamp val) {
	    this.date4 = val;
}

public void setDateI(java.sql.Timestamp val) {
    this.dateI = val;
}

public void setDateF(java.sql.Timestamp val) {
    this.dateF = val;
}

public void setCessazione(boolean tmp) {
    this.cessazione = tmp;
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
  
  public void setDate2(String tmp) {
	    this.date2 = DatabaseUtils.parseDateToTimestamp(tmp);
}
  public void setDate3(String tmp) {
	    this.date3 = DatabaseUtils.parseDateToTimestamp(tmp);
}
public void setDate4(String tmp) {
	    this.date4 = DatabaseUtils.parseDateToTimestamp(tmp);
}
public void setDateI(String tmp) {
    this.dateI = DatabaseUtils.parseDateToTimestamp(tmp);
}

public void setDateF(String tmp) {
    this.dateF = DatabaseUtils.parseDateToTimestamp(tmp);
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
//    phoneNumberList.setOrgId(tmp);
//    emailAddressList.setOrgId(tmp);
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
   *  Sets the AccountNumber attribute of the Organization obA
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
//  public void setPhoneNumberList(OrganizationPhoneNumberList tmp) {
//    this.phoneNumberList = tmp;
//  }


  /**
   *  Sets the EmailAddressList attribute of the Organization object
   *
   * @param  tmp  The new EmailAddressList value
   */
//  public void setEmailAddressList(OrganizationEmailAddressList tmp) {
//    this.emailAddressList = tmp;
//  }


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
   // phoneNumberList = new OrganizationPhoneNumberList(context);
    addressList = new OrganizationAddressList(context.getRequest());
    //emailAddressList = new OrganizationEmailAddressList(context.getRequest());
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
  public void setCessazione(String tmp) {
	    this.cessazione = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
	        tmp));
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
  
  public java.sql.Timestamp getDate2() {
	    return date2;
}
  public java.sql.Timestamp getDate3() {
	    return date3;
}
public java.sql.Timestamp getDate4() {
	    return date4;
}

public java.sql.Timestamp getDateI() {
    return dateI;
}

public java.sql.Timestamp getDateF() {
    return dateF;
}

public boolean getCessazione() {
    return cessazione;
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
  public String getDate3String() {
	    String tmp = "";
	    try {
	      return DateFormat.getDateInstance(3).format(date3);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
}
public String getDate4String() {
	    String tmp = "";
	    try {
	      return DateFormat.getDateInstance(3).format(date4);
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

public String getDateFString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(dateF);
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
  public String getDate3BufferedString() {
	    String tmp = "None";
	    try {
	      return DateFormat.getDateInstance(3).format(date3);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
	  }
public String getDate4BufferedString() {
	    String tmp = "None";
	    try {
	      return DateFormat.getDateInstance(3).format(date4);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
	  }

public String getDateIBufferedString() {
    String tmp = "None";
    try {
      return DateFormat.getDateInstance(3).format(dateI);
    } catch (NullPointerException e) {
    }
    return tmp;
  }

public String getDateFBufferedString() {
    String tmp = "None";
    try {
      return DateFormat.getDateInstance(3).format(dateF);
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
//  public String getPhoneNumber(String thisType) {
//    return phoneNumberList.getPhoneNumber(thisType);
//  }


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
//  public String getPrimaryPhoneNumber() {
//    return phoneNumberList.getPrimaryPhoneNumber();
//  }


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
   * Gets the city attribute of the Organization object
   *
   * @return The city value
 * @throws SQLException 
   */
  public String getCity2(Connection db, int orgid) throws SQLException {
//	  	PreparedStatement st = null;
//	    ResultSet rs = null;
//	    
//	    StringBuffer sql = new StringBuffer();
//	    sql.append("SELECT oa.city FROM organization_address oa " +
//	    		"INNER JOIN organization o on (o.org_id = oa.org_id)" +
//	    		" WHERE oa.address_type = 5 and oa.org_id = "+orgid+"");
//	    st = db.prepareStatement(sql.toString());
//	   // st.setString( 1, comune );
//	    rs = st.executeQuery();
//	    
//	    if (rs.next()) {
//	      city2 = rs.getString("city");
//	    }
//	    rs.close();
//	    st.close();
    return super.getCity2(db, orgid);
  }
  
  public String getCity3(Connection db, int orgid) throws SQLException {
//	  	PreparedStatement st = null;
//	    ResultSet rs = null;
//	    
//	    StringBuffer sql = new StringBuffer();
//	    sql.append("SELECT oa.city FROM organization_address oa " +
//	    		"INNER JOIN organization o on (o.org_id = oa.org_id)" +
//	    		" WHERE oa.address_type = 7 and oa.org_id = "+orgid+"");
//	    st = db.prepareStatement(sql.toString());
//	   // st.setString( 1, comune );
//	    rs = st.executeQuery();
//	    
//	    if (rs.next()) {
//	      city3 = rs.getString("city");
//	    }
//	    rs.close();
//	    st.close();
  return super.getCity3(db, orgid);
}


  /**
   * Sets the city attribute of the Organization object
   *
   * @param tmp The new city value
   */
  public void setCity2(String tmp) {
    this.city2 = tmp;
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
//  public OrganizationPhoneNumberList getPhoneNumberList() {
//    return phoneNumberList;
//  }


  /**
   *  Gets the EmailAddressList attribute of the Organization object
   *
   * @return    The EmailAddressList value
   */
//  public OrganizationEmailAddressList getEmailAddressList() {
//    return emailAddressList;
//  }


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
          db, Integer.parseInt(tmpId), "lookup_requestor_types"));
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


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db, String[] s, String[] c,ActionContext context) throws SQLException {
	  /*Inserisco il campo di prova*/
	  StringBuffer sql = new StringBuffer();
	  StringBuffer sql_1 = new StringBuffer();
	  StringBuffer sql_2 = new StringBuffer();
	  boolean doCommit = false;
	  try {
		  modifiedBy = enteredBy;
	       
	      if (doCommit = db.getAutoCommit()) {
	        db.setAutoCommit(false);
	      }
	      
	      
	      orgId = DatabaseUtils.getNextSeq(db,context, "organization","org_id");
	      
	      /*-------------------------------------------------Composizione della query - Prima parte*/
	      sql_1.append("INSERT INTO organization (stato_impresa,enteredby,modifiedby,name,site_id,owner,account_number");
	      sql_2.append(" VALUES (?,?,?,?,?,?,?");
	      /*Composizione della query - Parte intermedia*/
	      
	      //[start] ditta
	      sql_1.append(", tipologia");
	      sql_2.append(", ?");
	      if (orgId>-1){
	    	  sql_1.append(",org_id");
	    	  sql_2.append(",?");
	      }
	      if (this.date1!=null){
	    	  sql_1.append(",date1");
		      sql_2.append(",?");
	      }
	      if (this.banca!=""){
	    	  sql_1.append(",banca");
		      sql_2.append(",?");
	      }
	      
	      if (this.partitaIva!=""){
	    	  sql_1.append(",partita_iva");
		      sql_2.append(",?");
	      }
	      
	      if (this.codiceFiscale!=""){
	    	  sql_1.append(",codice_fiscale");
		      sql_2.append(",?");
	      }	           
	      
	      //[end] ditta
	      //[start] rappresentante
	      if (this.titoloRappresentante>-1){
	    	  sql_1.append(",titolo_rappresentante");
	    	  sql_2.append(",?");
	      }
	      
	    	  sql_1.append(",stato_lab");
	    	  sql_2.append(",?");
	      
	      if (this.codiceFiscaleRappresentante!=""){
	    	  sql_1.append(",codice_fiscale_rappresentante");
	    	  sql_2.append(",?");
	      }
	      if (this.nomeRappresentante!=""){
	    	  sql_1.append(",nome_rappresentante");
	    	  sql_2.append(",?");
	      }
	      if (this.cognomeRappresentante!=""){
	    	  sql_1.append(",cognome_rappresentante");
	    	  sql_2.append(",?");
	      }
	      if ((dataNascitaRappresentante != null)&&(!dataNascitaRappresentante.equals(""))) {
	    	  sql_1.append(",data_nascita_rappresentante");
	    	  sql_2.append(",?");
	      }
	      if (this.luogoNascitaRappresentante!=""){
	    	  sql_1.append(",luogo_nascita_rappresentante");
	    	  sql_2.append(",?");
	      }
	      if (this.emailRappresentante!=""){
	    	  sql_1.append(",email_rappresentante");
	    	  sql_2.append(",?");
	      }
	      if (this.telefonoRappresentante!=""){
	    	  sql_1.append(",telefono_rappresentante");
	    	  sql_2.append(",?");
	      }
	      if (this.fax!=""){
	    	  sql_1.append(",fax");
	    	  sql_2.append(",?");
	      }

	      //[end] rappresentante
	      if (this.nomeCorrentista!=""){
	    	  sql_1.append(",nome_correntista");
	    	  sql_2.append(",?");
	      }
	      if (this.codiceFiscaleCorrentista!=""){
	    	  sql_1.append(",cf_correntista");
	    	  sql_2.append(",?");
	      }
	      
	    	  sql_1.append(",duns_type");
	    	  sql_2.append(",?");
	      
	    	  if (this.notes!=""){
		    	  sql_1.append(",notes");
		    	  sql_2.append(",?");
		      }
	    	  if (this.contoCorrente!=""){
		    	  sql_1.append(",conto_corrente");
		    	  sql_2.append(",?");
		      }
	      /*-------------------------------------------------Composizione della query - Parte finale*/
	    	  if (this.codice10!=""){
		    	  sql_1.append(",codice10");
		    	  sql_2.append(",regexp_replace(?, '''|\n|\r', ' ', 'g')");
		      }
	    	  
	    	  if (this.numAut != "") {
	        	  sql_1.append(", numaut");
	        	  sql_2.append(",?");
	          }
	      sql_1.append(")");
	      sql_2.append(")");
	      sql.append(sql_1);
	      sql.append(sql_2);
	            
	      /*-------------------------------------------------Parametri della query*/
	      int i = 0;
	      PreparedStatement pst = db.prepareStatement(sql.toString());
	      /*Se il tipoe'4 allora nome= cognome nome del rappresentante*/
	      pst.setString(++i, "Attivo");   
	      pst.setInt(++i, this.getEnteredBy());
	      pst.setInt(++i, this.getModifiedBy());
	      pst.setString(++i, this.getName());
	      pst.setInt(++i, this.getSiteId());
	      pst.setInt(++i, this.getOwner());
	      pst.setString(++i, this.getAccountNumber());
	      
	      //[start] ditta
	      int val=9;
	      pst.setInt(++i, val);
	      if (orgId>-1){
	    	 pst.setInt(++i, orgId);
	      }	
	      if (this.date1!=null){
	    	  pst.setTimestamp(++i, this.date1);
	      }
	      if (this.banca!=""){
	    	  pst.setString(++i, this.banca);
	      }	      
	      if (this.partitaIva!=""){
	    	  pst.setString(++i, this.partitaIva);
	      }	      
	      if (this.codiceFiscale!=""){
	    	  pst.setString(++i, this.codiceFiscale);
	      }
	      //[end] ditta
	      //[start] rappresentante
	      if (this.titoloRappresentante>-1){
	    	  pst.setInt(++i, this.titoloRappresentante);
	      }
	      
	    	  pst.setInt(++i, 1);
	      
	      if (this.codiceFiscaleRappresentante!=""){
	    	  pst.setString(++i, this.codiceFiscaleRappresentante);
	      }
	      if (this.nomeRappresentante!=""){
	    	  pst.setString(++i, this.nomeRappresentante);
	      }
	      if (this.cognomeRappresentante!=""){
	    	  pst.setString(++i, this.cognomeRappresentante);
	      }
	      if ((dataNascitaRappresentante != null)&&(!dataNascitaRappresentante.equals(""))) {
	    	  pst.setTimestamp(++i, this.getDataNascitaRappresentante());
	      }
	      if (this.luogoNascitaRappresentante!=""){
	    	  pst.setString(++i, this.luogoNascitaRappresentante);
	      }
	      if (this.emailRappresentante!=""){
	    	  pst.setString(++i, this.emailRappresentante);
	      }
	      if (this.telefonoRappresentante!=""){
	    	  pst.setString(++i, this.telefonoRappresentante);
	      }
	      if (this.fax!=""){
	    	  pst.setString(++i, this.fax);
	      }
	      
	      //[end] rappresentante
	      if (this.nomeCorrentista!=""){
	    	  pst.setString(++i, this.nomeCorrentista);
	      }
	      if (this.codiceFiscaleCorrentista!=""){
	    	  pst.setString(++i, this.codiceFiscaleCorrentista);
	      }
	      pst.setString(++i, this.dunsType);
	      if (this.notes!=""){
	    	  pst.setString(++i, this.notes);
	      }
	      if (this.contoCorrente!=""){
	    	  pst.setString(++i, this.contoCorrente);
	      }
	      if (this.codice10!=""){
	    	  pst.setString(++i, this.codice10);
	      }
	      
	      if (this.numAut!=""){
	    	  pst.setString(++i, this.numAut);
	      }
	      /*Eseguo la query*/
	      
	      pst.execute();
	      pst.close();

	      
	      //eliminazione apici dal campo descrizione animali
	      /*StringBuffer update_descrizione = new StringBuffer();
	      update_descrizione.append("update organization set codice10 = regexp_replace(codice10, '''|\n|\r', ' ', 'g') where org_id = ?;");
	      PreparedStatement update_desc = db.prepareStatement(update_descrizione.toString());
	      update_desc.setInt(1, orgId);
	      update_desc.execute();
	      update_desc.close();*/
	      //this.update(db, true);
	      this.insertTipoAnimale(db, s);
	      this.insertCategoriaAnimale(db, c);
	     
	        //Insert the phone numbers if there are any
	        /*Iterator iphone = phoneNumberList.iterator();
	        while (iphone.hasNext()) {
	          OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber) iphone.next();
	          //thisPhoneNumber.insert(db, this.getOrgId(), this.getEnteredBy());
	          thisPhoneNumber.process(
	              db, orgId, this.getEnteredBy(), this.getModifiedBy());
	        */

	        //Insert the addresses if there are any
	        Iterator iaddress = getAddressList().iterator();
	        while (iaddress.hasNext()) {
	      	  org.aspcfs.modules.trasportoanimali.base.OrganizationAddress thisAddress = (org.aspcfs.modules.trasportoanimali.base.OrganizationAddress) iaddress.next();
	          
	          
	          //Solo se la provincia viene selezionata allora avviene il salvataggio       
	          if(thisAddress.getCity()!=null &&  !"".equals(thisAddress.getCity()) &&  !"-1".equals(thisAddress.getCity())) {
	        	  thisAddress.insert(db, this.getOrgId(), this.getEnteredBy(),context);
	          thisAddress.process(
	              db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
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
	      return true;
  }

public void updateTipoAnimale(Connection db, String[] s) throws SQLException{
		
		
		PreparedStatement pst=db.prepareStatement("delete from animalitrasportati where org_id= ?");
		pst.setInt(1, orgId);
		pst.execute();
		
		insertTipoAnimale(db, s);   

		
	}
  
  public void updateCategoriaAnimale(Connection db, String[] s) throws SQLException{
		
		
		PreparedStatement pst=db.prepareStatement("delete from categoriatrasportati where org_id= ?");
		pst.setInt(1, orgId);
		pst.execute();
		
		insertCategoriaAnimale(db, s);   

		
	}
  
public int updateStato(Connection db, String stato, String dataStato, int org_id) throws SQLException{
	  int i = 0;
	  StringBuffer sql = new StringBuffer();
	  sql.append("update organization set stato_impresa = ?, data_cambio_stato = ?  where org_id = ?");
	 
	  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	  Date parsedDate;
	  Timestamp timestamp; 
	  
	  try {
		
		parsedDate = dateFormat.parse(dataStato);
		timestamp = new java.sql.Timestamp(parsedDate.getTime()); 
		PreparedStatement pst=db.prepareStatement(sql.toString());
		pst.setString(1, stato);
		pst.setTimestamp(2, timestamp);
		pst.setInt(3, org_id); 

		i=	pst.executeUpdate();    
			pst.close();
			
			
		
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  return i;
	 
	
	   
}
  
  public void insertTipoAnimale(Connection db, String[] s) throws SQLException{
	  
	  StringBuffer sql = new StringBuffer();
	  sql.append("INSERT INTO animalitrasportati("+
	            "org_id, tipo_animale)  VALUES (?, ?)");
	  if(s!=null && s.length>0){
	  PreparedStatement pst=db.prepareStatement(sql.toString());
	  
	  pst.setInt(1, orgId);
	for(int i = 0; i< s.length; i++ ){
			pst.setInt(2, Integer.parseInt(s[i]));
	
			pst.execute();    
	} 
	  }	  
}
public void insertCategoriaAnimale(Connection db, String[] s) throws SQLException{
	  
	  StringBuffer sql = new StringBuffer();
	  sql.append("INSERT INTO categoriatrasportati("+
	            "org_id, categoria_animale)  VALUES (?, ?)");
	  if(s!=null && s.length>0){
	  PreparedStatement pst=db.prepareStatement(sql.toString());
	  
	  pst.setInt(1, orgId);
	for(int i = 0; i< s.length; i++ ){
			pst.setInt(2, Integer.parseInt(s[i]));
	
			pst.execute();    
	} 
	  }	  
}
 

  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int update(Connection db, String[] s,String[] c,ActionContext context) throws SQLException {
    int i = -1;
    boolean doCommit = false;
    try {
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      i = this.update(db, false,s,c);
      //Process the phone numbers if there are any
//      Iterator iphone = phoneNumberList.iterator();
//      while (iphone.hasNext()) {
//        OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber) iphone.next();
//        thisPhoneNumber.process(
//            db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy());
//      }
      
      
      
      
      //Insert the addresses if there are any
      Iterator iaddress = getAddressList().iterator();
      while (iaddress.hasNext()) {
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
        
        //Solo se la provincia viene selezionata allora avviene il salvataggio       
       // if(thisAddress.getCity()!=null && !thisAddress.getCity().equalsIgnoreCase("-1")) {
        thisAddress.process(
            db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
      //  }
        
      }
      
      
      

      //Insert the addresses if there are any
      //Iterator iaddress = getAddressList().iterator();
      while (iaddress.hasNext()) {
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        thisAddress.process(
            db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy(),context);
      }
      
      
      

//      //Insert the email addresses if there are any
//      Iterator iemail = emailAddressList.iterator();
//      while (iemail.hasNext()) {
//        OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress) iemail.next();
//        thisEmailAddress.process(
//            db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy());
//      }
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
    result = this.update(db);

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
  public int update(Connection db, boolean override, String[] s,String[] c) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE organization " +
        "SET account_number = ? ,name = ?, industry_temp_code = ?, " +
        "url = ?, notes= ?, ");

    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append(
        "modifiedby = ?, " +
        "employees = ?, revenue = ?, ticker_symbol = ?, ");

    if (owner > -1) {
      sql.append("owner = ?, ");
    }
    if (codice10 != null) {
        sql.append("codice10 = ?, ");
      }

 
    
    sql.append(
        "duplicate_id = ?, contract_end = ?, date1 = ?, date2 = ?, date3 = ?, date4 = ?,contract_end_timezone = ?, " +
        "alertdate = ?, alertdate_timezone=?, alert = ?, namesalutation = ?, namefirst = ?, " +
        "namemiddle = ?, namelast = ?, trashed_date = ?, segment_id = ?, " +
        "direct_bill = ?, account_size = ?, site_id = ?, sub_segment_id = ?, " +
        "source = ?, rating = ?, potential = ?, " +
        "duns_number = ?, business_name_two = ?, sic_code = ?, sic_description = ?, ");
    sql.append("stage_id = ? ");
    
    //campi nuovi
	sql.append(", partita_iva = ?, codice_fiscale = ?, abi = ?, cab = ?, cin = ?, banca = ?, conto_corrente = ?, nome_correntista = ?, cf_correntista = ?, codice1 = ?, codice2 = ?, codice3 = ?, codice4 = ?, codice5 = ?, codice6 = ?, codice7 = ?, codice8 = ?, codice9 = ?, codice_cont = ?, tipo_dest = ?, tipo_struttura = ?, tipo_locale = ?, data_in_carattere = ?,data_fine_carattere = ?, cessazione = ? ,titolo_rappresentante = ?,stato_lab= ?, codice_fiscale_rappresentante = ?, nome_rappresentante = ?, cognome_rappresentante = ?, email_rappresentante = ?, telefono_rappresentante = ?, data_nascita_rappresentante =?, luogo_nascita_rappresentante = ?, fax = ? , numaut = ?, stato_impresa = ? ");
	//fine campi nuovi

    sql.append(",ip_modified = ? WHERE org_id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, accountNumber);
    pst.setString(++i, name);
    pst.setInt(++i, industry);
    pst.setString(++i, url);
    pst.setString(++i, notes);
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, employees);
    pst.setDouble(++i, revenue);
    pst.setString(++i, ticker);
   if (owner > -1) {
      pst.setInt(++i, this.getOwner());
    }
    if (codice10 != null) {
    	pst.setString(++i, this.getCodice10());
    }
   
    pst.setInt(++i, this.getDuplicateId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getContractEndDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDate1());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDate2());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDate3());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDate4());
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
    //pst.setString(++i, this.getDunsType());
    pst.setString(++i, this.getDunsNumber());
    pst.setString(++i, this.getBusinessNameTwo());
    //pst.setInt(++i, this.getCategoriaTrasportata());
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
  	pst.setString(++i, this.getNomeCorrentista());   
  	pst.setString(++i, this.getCodiceFiscaleCorrentista()); 
  	pst.setString(++i, this.getCodice1()); 
  	pst.setString(++i, this.getCodice2()); 
  	pst.setString(++i, this.getCodice3()); 
  	pst.setString(++i, this.getCodice4()); 
  	pst.setString(++i, this.getCodice5()); 
  	pst.setString(++i, this.getCodice6()); 
  	pst.setString(++i, this.getCodice7()); 
  	pst.setString(++i, this.getCodice8()); 
  	pst.setString(++i, this.getCodice9()); 
  	pst.setString(++i, this.getCodiceCont()); 
  	pst.setString(++i, this.getTipoDest());
  	pst.setInt(++i, this.getTipoStruttura());
  	pst.setInt(++i, this.getTipoLocale());
  	DatabaseUtils.setTimestamp(pst, ++i, this.getDateI());
  	DatabaseUtils.setTimestamp(pst, ++i, this.getDateF());
  	pst.setBoolean(++i, this.getCessazione());
  	DatabaseUtils.setInt(pst, ++i, this.getTitoloRappresentante());
  	DatabaseUtils.setInt(pst, ++i, this.getStatoLab());
  	pst.setString(++i, this.getCodiceFiscaleRappresentante()); 
  	pst.setString(++i, this.getNomeRappresentante());
  	pst.setString(++i, this.getCognomeRappresentante());
  	pst.setString(++i, this.getEmailRappresentante()); 
	pst.setString(++i, this.getTelefonoRappresentante());
	DatabaseUtils.setTimestamp(pst, ++i, this.getDataNascitaRappresentante());
	pst.setString(++i, this.getLuogoNascitaRappresentante()); 
	pst.setString(++i, this.getFax());
	
	pst.setString(++i, this.getNumAut());
	pst.setString(++i, this.getStatoImpresa());

    //fine campi nuovi    
	pst.setString(++i, ip_modified);
    pst.setInt(++i, orgId);
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
 	  
    resultCount = pst.executeUpdate();
    this.updateTipoAnimale(db, s);
    this.updateCategoriaAnimale(db, c);
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
    this.setOrgId(rs.getInt("org_id"));
    name = rs.getString("name");
    statoImpresa=rs.getString("stato_impresa");
    accountNumber = rs.getString("account_number");
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
    date3 = rs.getTimestamp("date3");
    date4 = rs.getTimestamp("date4");
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
    data_cambio_stato = rs.getTimestamp("data_cambio_stato");
    //contact table


    //industry table
    industryName = rs.getString("industry_name");

    //account size table
    accountSizeName = rs.getString("account_size_name");

	//organization address table
    city=rs.getString("o_city");
    state=rs.getString("o_state");
    postalCode=rs.getString("o_postalcode");
    county=rs.getString("o_county");
    stageName=rs.getString("stage_name");
    
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
	codice1 = rs.getString("codice1");
	codice2 = rs.getString("codice2");
	codice3 = rs.getString("codice3");
	codice4 = rs.getString("codice4");
	codice5 = rs.getString("codice5");
	codice6 = rs.getString("codice6");
	codice7 = rs.getString("codice7");
	codice8 = rs.getString("codice8");
	codice9 = rs.getString("codice9");
	codice10 = rs.getString("codice10");
	codiceCont = rs.getString("codice_cont");
	tipoDest = rs.getString("tipo_dest");
	TipoStruttura = rs.getInt("tipo_struttura");
	TipoLocale = rs.getInt("tipo_locale");
	dateI = rs.getTimestamp("data_in_carattere");
	dateF = rs.getTimestamp("data_fine_carattere");
	cessazione = rs.getBoolean("cessazione");
    //fine campi nuovi
	titoloRappresentante = rs.getInt("titolo_rappresentante");
	statoLab = rs.getInt("stato_lab");
	codiceFiscaleRappresentante = rs.getString("codice_fiscale_rappresentante");
	nomeRappresentante = rs.getString("nome_rappresentante");
	cognomeRappresentante = rs.getString("cognome_rappresentante");
	nomeRappresentante = rs.getString("nome_rappresentante");
	emailRappresentante = rs.getString("email_rappresentante");
	telefonoRappresentante = rs.getString("telefono_rappresentante");
	dataNascitaRappresentante = rs.getTimestamp("data_nascita_rappresentante");
	luogoNascitaRappresentante = rs.getString("luogo_nascita_rappresentante");
    fax = rs.getString("fax");
    targa = rs.getString("targa");
    //categoriaTrasportata = rs.getInt("year_started");
	numAut = rs.getString("numaut");
	stato = rs.getString("stato_impresa");

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


  
  
  
  
  
  
  /* Metodo che serve a convertire una richiesta di D.I.A.
   * in un OSA. Il metodo si limita a fare un'update sul
   * campo tipologia il cui valore 0 rappresenta la D.I.A.,
   * mentre il valore 1 rappresenta un OSA*/
  public boolean cambiaInOsa(Connection db) throws SQLException {
	  //Controllo sullo orgId  
	  if (this.getOrgId() == -1) {
	      throw new SQLException("Organization ID not specified");
	    }
	    
	  	//Prepare statement della query
	    PreparedStatement pst = null;
	    
	    //String buffer per la creazione della query
	    StringBuffer sql = new StringBuffer();
	    
	    boolean success = false;	   
	    
	    //Query che rappresenta l'aggiornamento
	    sql.append(
	        "UPDATE organization SET tipologia = 9, cessato = 0, cambiato_in_osa_da = ? " +
	        "WHERE org_id = "+ this.getOrgId() + "" );
	   
	    int i = 0;
	    pst = db.prepareStatement(sql.toString());
	   pst.setInt( 1, this.modifiedBy );
	   // pst.setBoolean(++i, true);
	   // pst.setInt(++i, orgId);
	    
	   
	    int resultCount = pst.executeUpdate();
	  
	    pst.close();
	    if (resultCount == 1) {
	      success = true;
	    }
	    return success;
	  }
  
  /* Metodo, privato, che serve ad ottenere il flag
   * del tipo D.I.A. a partire dall'orgId di riferimento*/  
  protected String getFlagDia (Connection db) throws SQLException {
	 	      
//	      ResultSet rs=null;
//	      String flag=null;
//	      
//	      if (this.getOrgId() == -1) {
//		      throw new SQLException("Organization ID not specified");
//		    }
//		  
//		  	StringBuffer sql = new StringBuffer();
//		     
//		  
//		   //Creazione della query
//		   sql.append(
//				   "SELECT description " +
//		      		"FROM lookup_requestor_stage " +
//		      		"WHERE code = (SELECT stage_id FROM organization WHERE org_id = "+ this.getOrgId() + ") " );  
//		     
//		   		    
//		      //Creazione dello statement
//		      Statement pst = db.createStatement();
//		      
//		      //Esecuzione della query
//		      rs =pst.executeQuery(sql.toString());
//		     
//		      if (rs.next()) {
//		          flag = rs.getString(1);
//		        }
//		       
//		        pst.close();
//		    
//		 
//		  
//		  //Il flag viene ritornato....ma solo la prima lettera       
//		 return flag.substring(0, 1);
	  
	  return super.getFlagDia(db);
	  
  }
  
  
   //Metodo che serve a prelevare il progressivo di una asl
  protected int getProgressive (Connection db, int asl) throws SQLException {
//	  
//	  //ResultSet
//	  ResultSet rs=null;
//	  
//	  //Inizializzazione del progressivo
//      int progressive=0;
//      
//     
//	  //Buffer di SQL
//	  StringBuffer sql = new StringBuffer();
//	     
//	  
//	  	if (asl.equals("Benevento 1")) 
//	  		sql.append("SELECT nextval('benevento1_seq') " );
//	  	if (asl.equals("Avellino 1")) 
//	  		sql.append("SELECT nextval('avellino1_seq') " );  
//	  	if (asl.equals("Avellino 2")) 
//	  		sql.append("SELECT nextval('avellino2_seq') " );  
//	  	if (asl.equals("Caserta 1")) 
//	  		sql.append("SELECT nextval('caserta1_seq') " );  
//	  	if (asl.equals("Caserta 2")) 
//	  		sql.append("SELECT nextval('caserta2_seq') " );  
//	  	if (asl.equals("Caserta 3")) 
//	  		sql.append("SELECT nextval('caserta3_seq') " );  
//	  	if (asl.equals("Napoli 1")) 
//	  		sql.append("SELECT nextval('napoli1_seq') " );  
//	  	if (asl.equals("Napoli 2")) 
//	  		sql.append("SELECT nextval('napoli2_seq') " );  
//	  	if (asl.equals("Napoli 3")) 
//	  		sql.append("SELECT nextval('napoli3_seq') " );  
//	  	if (asl.equals("Napoli 4")) 
//	  		sql.append("SELECT nextval('napoli4_seq') " );  
//	  	if (asl.equals("Napoli 5")) 
//	  		sql.append("SELECT nextval('napoli5_seq') " );  
//	  	if (asl.equals("Salerno 1")) 
//	  		sql.append("SELECT nextval('salerno1_seq') " );  
//	  	if (asl.equals("Salerno 2")) 
//	  		sql.append("SELECT nextval('salerno2_seq') " );  
//	  	if (asl.equals("Salerno 3")) 
//	  		sql.append("SELECT nextval('salerno3_seq') " );  
//	   		    
//	      //Creazione dello statement
//	      Statement pst = db.createStatement();
//	      
//	      //Esecuzione della query
//	      rs =pst.executeQuery(sql.toString());
//	     
//	      if (rs.next()) {
//	    	  //Progressivo per quell'ASL
//	    	  progressive= rs.getInt(1);
//	        }
//	       
//	        pst.close();
//	        
//	 //Il codice del progressivo ASL viene ritornato      
//	 return progressive;
	  
	  return super.getProgressive(db, asl);
	  
  }
  
  /*Metodo che ritorna il progressivo asl recuperando
   * prima l'asl di riferimento e poi invocando il metodo
   * privato getProgressive*/
  protected String getAslProgressive (Connection db) throws SQLException {
//	  
//	  //ResultSet
//	  ResultSet rs=null;
//      
//	  //NOME ASL
//	  String asl=null;
//      
//      if (this.getOrgId() == -1) {
//	      throw new SQLException("Organization ID not specified");
//	    }
//	  
//      //Buffer di SQL
//	  	StringBuffer sql = new StringBuffer();
//	     
//	  
//	   //Creazione della query per ottenere la ASL di appartenenza
//	   sql.append(
//			   "SELECT description " +
//	      		"FROM lookup_site_id " +
//	      		"WHERE code = (SELECT site_id FROM organization WHERE org_id = "+ this.getOrgId() + ") " );  
//	     
//	   		    
//	      //Creazione dello statement
//	      Statement pst = db.createStatement();
//	      
//	      //Esecuzione della query
//	      rs =pst.executeQuery(sql.toString());
//	     
//	      if (rs.next()) {
//	          asl = rs.getString(1);
//	        }
//	    
//	    /* Viene chiamato il metodo per ottenere il progressivo 
//	     * della specifica ASL*/
//	    Integer progressive=this.getProgressive(db, asl);  
//	    
//	  
//	    pst.close();
//	    
//	    String result=null;
//	    
//	    //Il progressivo deve essere a 6 caratteri
//	    result=org.aspcfs.utils.StringUtils.zeroPad(progressive,6);	    	
//	  
//	 
//	  //Il progressivo per quella ASL viene ritornato       
//	    //return progressive.toString();
//	  return result;
	  
	  return super.getAslProgressive(db);
	 
  }
  
  /* Metodo utilizzato, in fase di costruzione del codice OSA, per
   * ottenere il codice dell'attivit�*/
  protected String getCodAttivita(Connection db) throws SQLException {
//	  
//	  //Result Set
//	  ResultSet rs=null;
//      
//	  //Codice attivit�
//	  String codiceAttivita=null;
//      
//      if (this.getOrgId() == -1) {
//	      throw new SQLException("Organization ID not specified");
//	    }
//	  
//      	//Buffer di creazione query
//	  	StringBuffer sql = new StringBuffer();
//	     
//	  
//	   //Creazione della query (cf_correntista rappresenta il cod. attivita')
//	   sql.append("SELECT cf_correntista FROM organization WHERE org_id = "+ this.getOrgId() + " " );  
//	     
//	   		    
//	      //Creazione dello statement
//	      Statement pst = db.createStatement();
//	      
//	      //Esecuzione della query
//	      rs =pst.executeQuery(sql.toString());
//	     
//	      if (rs.next()) {
//	    	  codiceAttivita = rs.getString(1);
//	        }
//	       
//	        pst.close();
//	        
//	 //Il codice dell'attivit� viene ritornato       
//	 return codiceAttivita;
	  
	  return super.getCodAttivita(db);
	  
  }
  
  public String getPaddedId(int sequence) {
	    String padded = (String.valueOf(sequence));
	    while (padded.length() < 6) {
	      padded = "0" + padded;
	    }
	    return padded;
	  }
  /*Metodo utilizzato per generare il codice OSA da assegnare*/
  public void generaCodice (Connection db) throws SQLException {
	  
	  //Controllo sull'orgId
	  if (this.getOrgId() == -1) {
	      throw new SQLException("Organization ID not specified");
	    }
	  
	  	//Buffer di creazione query
	  	StringBuffer sql = new StringBuffer();
	    
	  	int sequence;
	  	
	  	if(this.getDunsType().equals("tipo1")){
	  		sequence = DatabaseUtils.getNextSeqTipo(db, "organization_tipo1_id_seq");
	  	}else{
	  		sequence = DatabaseUtils.getNextSeqTipo(db, "organization_tipo2_id_seq");
	  	}
	  
	   
	    	  	  	
	  	/*Il codicee'formato dalla parte relativa al comune,
	  	 * al codice dell'attivit�
	  	 * dal progressivo dell'ASL, e dal FLAG che rappresenta
	  	 * la D.I.A.*/
	    
	  	String codiceOsa="CE IT T"+((this.getDunsType().equals("tipo1"))?("S"):("L"))+"150"+this.getPaddedId(sequence);
	  	
	   
	    /* Query che inserisce il codice osa nella tabella organization*/	    
	  	sql.append(
		        "UPDATE organization SET account_number = '"+codiceOsa+"' " +
		        "WHERE org_id = "+ this.getOrgId() + "" );  
	     
	
	   	  //Statement    
	      PreparedStatement pst = db.prepareStatement(sql.toString());
	      
	      //Esecuzione dell'update
	      pst.executeUpdate();
	     
	      pst.close();
	         
	 // super.generaCodice(db);
	  
  }
  
  
  
  public void setComuni (Connection db, int codeUser) throws SQLException {
	
	  	Statement st = null;
	    ResultSet rs = null;
	    StringBuffer sql = new StringBuffer();
	    
	    //sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= (select site_id from organization where org_id="+ this.getOrgId() + "))");
	    
	    if (codeUser != -1)
	    {
	    sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= "+ codeUser + ")ORDER BY comune");
	    //sql.append("select comune from comuni");
	    }
	    else
	    {
		    sql.append("select comune from comuni ORDER BY comune");

	    }
	    st = db.createStatement();
	    rs = st.executeQuery(sql.toString());
	    
	    while (rs.next()) {
	      comuni.add(rs.getString("comune"));
	    }
	    rs.close();
	    st.close();
	  
  }
  
  
  public Vector getComuni () throws SQLException {
	  /*
	  Enumeration e=comuni.elements();
	  
	  while(e.hasMoreElements()) {
	  }*/
	 
	  return comuni;
	 
  }
  
  
  public ResultSet queryRecord_allegatoH( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_ALLEGATO_H");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 pst.setInt(2, orgId);
			 pst.setInt(3, orgId);

			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
  }
  
  public ResultSet queryRecord_allegatoG( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_ALLEGATO_G");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
  }
  
  public ResultSet queryRecord_allegatoE( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_ALLEGATO_E");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
  }
  
  public ResultSet queryRecord_allegatoB( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_ALLEGATO_B");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
 }
  
  public ResultSet queryRecord_allegatoC( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_ALLEGATO_C_OP");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
  }
  
  public ResultSet queryRecord_specie( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_ALLEGATO_SP");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
}
  
  public ResultSet queryRecord_rendicontazione_animali( Connection db, Double anno_vero )throws SQLException {
	    
		ResultSet rs = null;
		
		try{
			 String qry = ApplicationProperties.getProperty("GET_RENDICONTAZIONE_ANNUALE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, anno_vero.intValue());
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
  }
  
  public ResultSet queryRecord_rendicontazione_ispezioni( Connection db, Double anno_vero )throws SQLException {
	    
		ResultSet rs = null;
		
		try{
			 String qry = ApplicationProperties.getProperty("GET_RENDICONTAZIONE_ANNUALE_ISPEZIONI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, anno_vero.intValue());
			
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
}
  
  public ResultSet queryRecord_rendicontazione_violazioni( Connection db, Double anno_vero )throws SQLException {
	    
		ResultSet rs = null;
		
		try{
			 String qry = ApplicationProperties.getProperty("GET_RENDICONTAZIONE_ANNUALE_VIOLAZIONI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, anno_vero.intValue());
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
  }
  
  public ResultSet queryRecord_rendicontazione_mezzi(Connection db, Double anno_vero) throws SQLException {
	  ResultSet rs = null;
		
		try{
			 String qry = ApplicationProperties.getProperty("GET_RENDICONTAZIONE_ANNUALE_MEZZI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, anno_vero.intValue());
			
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		 }
		return rs;
	}
  
  public ResultSet queryRecord_rendicontazione_mezzi_violazioni(Connection db, Double anno_vero) throws SQLException {
	  ResultSet rs = null;
		
		try{
			 String qry = ApplicationProperties.getProperty("GET_RENDICONTAZIONE_ANNUALE_MEZZI_VIOLAZIONI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, anno_vero.intValue());
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		 }
		return rs;
	}
  
  
  public ResultSet queryRecord_rendicontazione_asl( Connection db, Double anno_vero, int idAsl )throws SQLException {
	    
		ResultSet rs = null;
		
		try{
			 String qry = ApplicationProperties.getProperty("GET_RENDICONTAZIONE_ASL");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, anno_vero.intValue());
			 pst.setInt(2, idAsl);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
}
  
  
  public ResultSet queryRecord_allegatoD( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_ALLEGATO_D");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
  }
  
  public void setFields(ResultSet rs, ResultSet rs1, AcroFields form, String tipo) throws IOException, DocumentException, SQLException{
	  
	  if(tipo.equals("D") || tipo.equals("C")){
		  String specifiche = "";
		  if (rs.next())
		  {
			  form.setField("name", rs.getString("name"));
			  form.setField("num_aut", rs.getString("account_number"));
			  form.setField("indirizzo", rs.getString("addrline1") );
			  form.setField("comune", rs.getString("city")+ "   "+ "("+rs.getString("state")+")");
			  form.setField("codice_postale", rs.getString("postalcode"));
			  form.setField("stato_membro",rs.getString("country"));
			  form.setField("telefono",rs.getString("telefono_rappresentante"));
			  form.setField("fax",rs.getString("fax"));
			  form.setField("mail",rs.getString("email_rappresentante"));
			  form.setField("data_rev", rs.getString("data_rev"));
	 		  String specie = setFieldsSpecie(rs1, form);
			  
			  
			  String other = rs.getString("codice10") ;
			if (other!= null &&  ! other.equals(""))
			{
				specie += " Descrizione: "+other;
			}
			  
			    form.setField("specificare", specie);

		  }
		  
	  }
	  
	  if(tipo.equals("G")) {
		  String categorie = " ";
		  if (rs.next())
		  {
			 form.setField("name", rs.getString("name"));
			 form.setField("codice_azienda", rs.getString("account_number"));
			 form.setField("indirizzo_residenza", rs.getString("addrline1") );
			 form.setField("comune", rs.getString("city")+ "   "+ "("+rs.getString("state")+")");
			 form.setField("data_rev", rs.getString("data_rev"));

			 /*if(rs.getString("d1")!= null){
				 categorie += rs.getString("d1");
			 }
			 if(rs.getString("d2")!= null){
				 categorie += " - "+rs.getString("d2");
			 }
			 if(rs.getString("d3")!= null){
				 categorie += " - "+rs.getString("d3");
			 }
			 if(rs.getString("d4")!= null){
				 categorie += " - "+rs.getString("d4");
			 }
			 if(rs.getString("d5")!= null){
				 categorie += " - "+rs.getString("d5");
			 }
			 if(rs.getString("d6")!= null){
				 categorie += " - "+rs.getString("d6");
			 }
			 if(rs.getString("d10")!= null){
				 categorie += " - "+rs.getString("d10");
			 }*/
			 
			 String specie = setFieldsSpecie(rs1, form);
			 	 
			 form.setField("categorie", specie);
			 form.setField("marca",rs.getString("mezzo"));
			 form.setField("targa",rs.getString("targa"));
			 form.setField("asl",rs.getString("asl"));
			 form.setField("data",this.getDateDDMMYYYY(rs.getString("date1")));
		}
		
	  }else if(tipo.equals("H")){
		  //Caso Allegato H
		  if (rs.next())
		  {
			  if (rs.getString("name") != null)
				  form.setField("name", rs.getString("name").toUpperCase());
			  if (rs.getString("codice_fiscale_rappresentante") != null)
				  form.setField("cf", rs.getString("codice_fiscale_rappresentante").toUpperCase());
			 
			  if (rs.getString("city") != null)
				  form.setField("luogo_residenza", rs.getString("city").toUpperCase() );
			
			  if (rs.getString("addrline1") != null)
				  form.setField("indirizzo_residenza", rs.getString("addrline1").toUpperCase() );
			
			  if (rs.getString("city") != null)
				  form.setField("comune", rs.getString("city").toUpperCase()+ "   "+ "("+rs.getString("state")+")");
			 
			  if (rs.getString("banca") != null)
				  form.setField("abitazione_allevamento", rs.getString("banca").toUpperCase());
			 
			  if (rs.getString("sede") != null)
				  form.setField("indirizzo_abitazione_allevamento", rs.getString("sede").toUpperCase() );
		 	 
			  if (rs.getString("cittasede") != null && rs.getString("statesede") != null)
				  form.setField("comune_abitazione_allevamento", rs.getString("cittasede").toUpperCase()+ "   "+ "("+rs.getString("statesede").toUpperCase()+")" );
			  else
				  if (rs.getString("cittasede") != null )
					  form.setField("comune_abitazione_allevamento", rs.getString("cittasede").toUpperCase() );
				 
				  
			  if (rs.getString("mezzo") != null)
				  form.setField("marca_tipo",rs.getString("mezzo").toUpperCase());
			 
			  if (rs.getString("targa") != null)
				  form.setField("targa",rs.getString("targa").toUpperCase());
			 
			  if (rs.getString("asl") != null)
				  form.setField("asl",rs.getString("asl").toUpperCase());
			  if (rs.getString("date1") != null)
			 form.setField("data",this.getDateDDMMYYYY(rs.getString("date1")));
			  if (rs.getString("data_rev") != null)
			 form.setField("data_rev", rs.getString("data_rev"));

			 if(rs.getString("d1")!= null){
				 form.setField("d1", "Yes");
			 }
			 if(rs.getString("d2")!= null){
				 form.setField("d2","Yes");
			 }
			 if(rs.getString("d3")!= null){
				 form.setField("d3", "Yes");

			 }
		}
	  }
	  
	  
  }
  
public String setFieldsSpecie(ResultSet rs, AcroFields form) throws IOException, DocumentException, SQLException{
	  
	 
		  String specifiche = "";
		  String desc = "";
		  while (rs.next())
		  {
			    specifiche += rs.getString("d1")+" - ";
			    desc= rs.getString("codice10");
		  }
		  if(desc!=null && ! "".equals(desc))
		 	specifiche += desc;
		  
	return specifiche;
}	  
	  
  
   public String getDateDDMMYYYY(String Sdate){
		
		String anno = "-"+Sdate.substring(0,4);
		String mese = "-"+Sdate.substring(5,7);
		String giorno = Sdate.substring(8,10);
		
		return giorno+mese+anno; 
	}


   
}

