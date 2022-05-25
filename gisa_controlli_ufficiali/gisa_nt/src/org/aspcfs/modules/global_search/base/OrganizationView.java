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
package org.aspcfs.modules.global_search.base;


import com.darkhorseventures.framework.beans.GenericBean;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;
import org.omg.CORBA.DATA_CONVERSION;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 * Exp $
 */
public class OrganizationView extends GenericBean {
										 	
	//static final long serialVersionUID =-6915867402685037717L;

  /**
	 * 
	 */
	private static final long serialVersionUID = -566967668465298257L;
	private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
    }
  }
  
  protected double YTD = 0;
  private String errorMessage = "";
  private int orgId = -1;
  private int ticketId = -1;
 

private String name = "";
  private String url = "";
  
  private boolean directBill = false;
 
  private String tipoDest = null;
 
  private int siteId = -1;
  private int stageId = -1;
  private int tipologia_acque = -1 ;
  
  private boolean minerOnly = false;
  private int enteredBy = -1;
  private Vector comuni=new Vector();
 
  private String accountNumber = "";
  private String city = null;
  private String state = null;
  private String county = null;
  private String stato = null;
  private String stato_allevamento = null;
  private String stato_impresa = null;
  private String tipologia_operatore = null;
  private String num_verbale = null;
  private String idC = null;
  private String idNonConformita = null;
  private String idControllo = null;
  private String soggetto = null;
  private String num_aut = null;
  private String n_reg = null;
  private String targa = null;
  private String titolare = null;
  private String asl = null;
  private int tipologiaAttivita = -1;
  private String motivazione = null;
  private String analita = null;
  private String matrice =  null;
  private String esito = null;
  private String data_controllo = null;
  private String categoria_rischio = null; 
  private LookupList types = new LookupList();
  private ArrayList typeList = null;
  String partitaIva = null;
  private String codiceFiscale = null;
  private String codiceFiscaleRappresentante = null;
  private String nomeRappresentante = null;
  private String cognomeRappresentante = null;
  private String tipologia_campioni = null;
  private String alertText = "";
  private Timestamp trashedDate = null;
  private Timestamp dataAudit = null;
  private int source = -1;
  private int count  = -1;
  private String nome_detentore;
  private String cf_detentore;
  private String tipologia_struttura;
  private String orientamento_produttivo;
  private String specie_allevata;
  private String num_capi;
  private String num_capi_sei_settimane;
  private String num_capi_un_anno;
  private String stato_sanitario ;
  private int tipologia ;
  
  
  
  public int getTipologia() {
	return tipologia;
}

public void setTipologia(int tipologia) {
	this.tipologia = tipologia;
}

public String getNum_capi_sei_settimane() {
	return num_capi_sei_settimane;
}

public void setNum_capi_sei_settimane(String num_capi_sei_settimane) {
	this.num_capi_sei_settimane = num_capi_sei_settimane;
}

public String getNum_capi_un_anno() {
	return num_capi_un_anno;
}

public void setNum_capi_un_anno(String num_capi_un_anno) {
	this.num_capi_un_anno = num_capi_un_anno;
}

public String getStato_sanitario() {
	return stato_sanitario;
}

public void setStato_sanitario(String stato_sanitario) {
	this.stato_sanitario = stato_sanitario;
}

public String getNum_capi() {
	return num_capi;
}

public void setNum_capi(String num_capi) {
	this.num_capi = num_capi;
}

public String getNome_detentore() {
	return nome_detentore;
}

public void setNome_detentore(String nome_detentore) {
	this.nome_detentore = nome_detentore;
}

public String getCf_detentore() {
	return cf_detentore;
}

public void setCf_detentore(String cf_detentore) {
	this.cf_detentore = cf_detentore;
}

public String getTipologia_struttura() {
	return tipologia_struttura;
}

public void setTipologia_struttura(String tipologia_struttura) {
	this.tipologia_struttura = tipologia_struttura;
}

public String getOrientamento_produttivo() {
	return orientamento_produttivo;
}

public void setOrientamento_produttivo(String orientamento_produttivo) {
	this.orientamento_produttivo = orientamento_produttivo;
}

public String getSpecie_allevata() {
	return specie_allevata;
}

public void setSpecie_allevata(String specie_allevata) {
	this.specie_allevata = specie_allevata;
}

public static Logger getLog() {
	return log;
  }
  
  public static void setLog(Logger log) {
	OrganizationView.log = log;
  }

  public String getTicketId() {
	return Integer.toString(ticketId);
  }
  
  public void setTicketId(int ticketId) {
	this.ticketId = ticketId;
  }

  public void setTicketId (String tic){
	  	this.ticketId = Integer.parseInt(tic);
  }
  
  public int getTipologia_acque() {
	return tipologia_acque;
  }

  public void setTipologia_acque(int tipologia_acque) {
	this.tipologia_acque = tipologia_acque;
  }

  public int getTipologiaAttivita() {
		return tipologiaAttivita;
  }
 
  public void setTipologiaAttivita(int tipologia) {
		this.tipologiaAttivita = tipologia;
  }
  
  public void setDataControllo(String dataC) {
		this.data_controllo = dataC;
  }
  
  public String getDataControllo() {
		return data_controllo; 
 }
  
  public void setCategoriaRischio(String cat) {
		this.categoria_rischio = cat;
  }

  public String getCategoriaRischio() {
		return categoria_rischio; 
  }
  
  public void setCount(int c){
	  	this.count = c;
  }
  
  public int getCount(){
	  	return count;
  }
  
  public boolean isDirectBill() {
	return directBill;
  }

  public void setDirectBill(boolean directBill) {
	this.directBill = directBill;
  }

  public Vector getComuni() {
	return comuni;
  }

  public void setComuni(Vector comuni) {
	this.comuni = comuni;
  }

  public String getCounty() {
	return county;
  }

  public void setCounty(String county) {
	this.county = county;
  }

  public static long getSerialVersionUID() {
	return serialVersionUID;
  }

  public boolean isMinerOnly() {
	return minerOnly;
  }

  public String getAccountNumber() {
	return accountNumber;
  }

  public String getCity() {
	return city;
  }

  public String getState() {
	return state;
  }

  
 
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

	public String getTipologia_campioni() {
		return tipologia_campioni;
	}

	public void setTipologia_campioni(String tipo_campioni) {
		this.tipologia_campioni = tipo_campioni;
	}
	
	
	public void setMotivazioneCampione(String motivazione) {
		// TODO Auto-generated method stub
		this.motivazione = motivazione;
	}

	public String getMotivazioneCampione() {
		return motivazione;
	}
	
	public void setAnalita(String analita) {
		// TODO Auto-generated method stub
		this.analita = analita;
	}

	public String getAnalita() {
		return analita;
	}
	
	public void setMatrice(String matrice) {
		// TODO Auto-generated method stub
		this.matrice = matrice;
	}

	public String getMatrice() {
		return matrice;
	}
	
	public void setEsito(String esito) {
		// TODO Auto-generated method stub
		this.esito = esito;
	}
	
	public String getEsito() {
		return esito;
	}
	
	public String getNum_verbale() {
		return num_verbale;
	}

	public void setNum_verbale(String num_verbale) {
		this.num_verbale = num_verbale;
	}

	public String getIdC() {
		return idC;
	}


	public void setIdC(String idC) {
		this.idC = idC;
	}

	public String getIdNonConformita() {
		return idNonConformita;
	}

	public void setIdNonConformita(String idNonConformita) {
		this.idNonConformita = idNonConformita;
	}

	//Id controllo ufficiale
	public String getIdControllo(){
		return idControllo; 
	}
	
	public void setIdControllo(String idControllo){
		this.idControllo = idControllo;
	}
	
	public String getSoggetto() {
			return soggetto;
	}
	
	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
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

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}
	
	public String getTitolare() {
		return titolare;
	}

	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}
  
	private ArrayList<Distrubutore> listaDistributori=new ArrayList<Distrubutore>();

	public ArrayList<Distrubutore> getListaDistributori() {
		return listaDistributori;
	}

	public void setListaDistributori(ArrayList<Distrubutore> listaDistributori) {
		this.listaDistributori = listaDistributori;
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
	
	public Timestamp getDataAudit() {
		return dataAudit;
	}

	public void setDataAudit(Timestamp tmp) {
		this.dataAudit = tmp;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	/**
	   *  Gets the alertText attribute of the Organization object
	   *
	   * @return    The alertText value
	   */
	  public String getAlertText() {
	    return alertText;
	 }
	
  public OrganizationView() { }



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
   *  Gets the source attribute of the Organization object
   *
   * @return    The source value
   */
  public int getSource() {
    return source;
  }

  /**
   *  Gets the trashedDate attribute of the Organization object
   *
   * @return    The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }

  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }

  public void setTypes(LookupList types) {
    this.types = types;
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
   *  Sets the AccountNumber attribute of the Organization obA  9
   *
   * @param  accountNumber  The new AccountNumber value
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }


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
   *  Sets the alertText attribute of the Organization object
   *
   * @param  tmp  The new alertText value
   */
  public void setAlertText(String tmp) {
    this.alertText = tmp;
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
   *  Gets the EnteredBy attribute of the Organization object
   *
   * @return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


	public void setOrgId(int tmp) {
	    this.orgId = tmp;
	 }


	public void setOrgId(String tmp) {
	    this.orgId = Integer.parseInt(tmp);
	}

	public void setCity(String tmp) {
			this.city = tmp;
	}

	public void setState(String tmp) {
		this.state = tmp;
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
    return this.name;
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

  public String getUrlString() {
    if (url != null) {
      if (url.indexOf("://") == -1) {
        return "http://" + url;
      }
    }
    return url;
  }



  
  


}

