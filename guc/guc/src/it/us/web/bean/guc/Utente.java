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
package it.us.web.bean.guc;

import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.util.guc.GUCEndpoint;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


public class Utente implements Serializable {

  private static final long serialVersionUID = 1L;
	
  private String error;
//Info Utente
  private int id;
  private String username;
  private String password;
  private String passwordEncrypted;
  private Date expires;
  private boolean enabled = true;
  private String luogo ;
  private String luogoVam ;
  private String piva ;
  
  private int gestore ;
  private int comuneGestore ;
  
  private String tipoAttivitaApicoltore;
  private int comuneApicoltore;
  private String indirizzoApicoltore;
  private String capIndirizzoApicoltore;
  
  private int comuneTrasportatore;
  private String indirizzoTrasportatore;
  private String capIndirizzoTrasportatore;
  
  //CAMPI PER LP VAM
  private int id_provincia_iscrizione_albo_vet_privato_vam;
  private String nr_iscrione_albo_vet_privato_vam;
  
  
  //Info Contatto
  private String nome;
  private String cognome;
  private String codiceFiscale;
  private String email;
  private String note;
  private String numAutorizzazione ;
  private String telefono;
  
  //Info Asl
  private Asl asl;
  
  //Info portale importatori / prelievo DNA
  private Integer id_importatore = -1 ;
  private String importatoriDescription = "" ;
  
  //Info Clinica
  private Integer clinicaId = -1;
  private String clinicaDescription = "";
  
  //Info Canile
  private Integer canileId = -1;
  private String canileDescription = ""; 
  //Info Canile bdu
  private Integer canilebduId = -1;
  private String canilebduDescription = "";  
  
  //Info Struttura gisa GISA NON e'' ANCORA SUPPORTATO
  private Integer strutturagisaId = -1;
  private String strutturagisaDescription = ""; 
  
  
  
  //Info Ruoli
  private Set<Ruolo> ruoli = new HashSet<Ruolo>(0);
  private TreeMap<String, Ruolo> hashRuoli = new TreeMap<String, Ruolo>();
  
  //Info Sistema
  private int enteredBy = -1;
  private String enteredByUsername = null;
  private int modifiedBy = -1;
  private Date entered = null;
  private Date modified = null;
  
  //Info di utilita''
  private String oldUsername = null;
  private Integer ruoloId = -1;
  private boolean newPassword;
  
  //CAMPI PER LP ED UNINA
  private int id_provincia_iscrizione_albo_vet_privato;
  private String nr_iscrione_albo_vet_privato;
  
  private ArrayList<Clinica> clinicheVam = new ArrayList<Clinica>();
  private ArrayList<Struttura> struttureGisa = new ArrayList<Struttura>();
  private ArrayList<Canile> caniliBdu = new ArrayList<Canile>();
  private ArrayList<Importatori> importatori = new ArrayList<Importatori>();
  
  private HashMap<String,HashMap<String,String>> extOption = new HashMap<String, HashMap<String,String>>();
  
  private Date dataScadenza ;
  
  private boolean cessato;
  
  private HashMap<String,String> query= new HashMap<String, String>();
  
  
  
  /*INFO ACCREDITAMENTO SUAP*/
  private String comuneSuap ;
  private String ipSuap ;
  private String pecSuap ;
  private String callbackSuap ;
  private String sharedKeySuap ;
  private String callbackSuap_ko ;
  //private String telefonoSuap ;
  private String descrizioneLivelloAccreditamento ;
  private int livelloAccreditamentoSuap;
  
  private String numRegStab ;
  
  

//public String getTelefonoSuap() {
//	return telefonoSuap;
//}
//public void setTelefonoSuap(String telefonoSuap) {
//	this.telefonoSuap = telefonoSuap;
//}
  
public String getDescrizioneLivelloAccreditamento() {
	return descrizioneLivelloAccreditamento;
}
public void setDescrizioneLivelloAccreditamento(String descrizioneLivelloAccreditamento) {
	this.descrizioneLivelloAccreditamento = descrizioneLivelloAccreditamento;
}
public int getLivelloAccreditamentoSuap() {
	return livelloAccreditamentoSuap;
}
public void setLivelloAccreditamentoSuap(int livelloAccreditamentoSuap) {
	this.livelloAccreditamentoSuap = livelloAccreditamentoSuap;
}

public void setLivelloAccreditamentoSuap(String livelloAccreditamentoSuap) {
	if (livelloAccreditamentoSuap!=null && !"".equals(livelloAccreditamentoSuap))
		this.livelloAccreditamentoSuap = Integer.parseInt(livelloAccreditamentoSuap);
}

public String getError() {
	return error;
}
public void setError(String error) {
	this.error = error;
}
public String getCallbackSuap_ko() {
	return callbackSuap_ko;
}
public void setCallbackSuap_ko(String callbackSuap_ko) {
	this.callbackSuap_ko = callbackSuap_ko;
}
public String getComuneSuap() {
	return comuneSuap;
}
public void setComuneSuap(String comuneSuap) {
	this.comuneSuap = comuneSuap;
}

public String getNumRegStab() {
	return numRegStab;
}
public void setNumRegStab(String numRegStab) {
	if(numRegStab==null)
		this.numRegStab = numRegStab;
	else
		this.numRegStab = numRegStab.trim();
}
public String getIpSuap() {
	if(ipSuap!= null)
	return ipSuap;
	return "" ;
}
public void setIpSuap(String ipSuap) {
	this.ipSuap = ipSuap;
}
public String getPecSuap() {
	if(pecSuap!= null)
	return pecSuap;
	return "" ;
}
public void setPecSuap(String pecSuap) {
	this.pecSuap = pecSuap;
}
public String getCallbackSuap() {
	if(callbackSuap!= null)
	return callbackSuap;
	return "" ;
}
public void setCallbackSuap(String callbackSuap) {
	this.callbackSuap = callbackSuap;
}
public String getSharedKeySuap() {
	if (sharedKeySuap!=null)
	
	return sharedKeySuap;
	return "" ;
}
public void setSharedKeySuap(String sharedKeySuap) {
	this.sharedKeySuap = sharedKeySuap;
}
public boolean isCessato() {
	return cessato;
}
public void setCessato(boolean cessato) {
	this.cessato = cessato;
}
public Date getDataScadenza() {
	return dataScadenza;
}
public void setDataScadenza(Date dataScadenza) {
	
	this.dataScadenza = dataScadenza;
}

public void setDataScadenza(Connection conn) throws ParseException {
	try {
		PreparedStatement	stat	= conn.prepareStatement("select data_scadenza from guc_utenti_ where id = "+this.getId());
		ResultSet			rs		= stat.executeQuery();
		if(rs.next())
			this.dataScadenza=rs.getDate("data_scadenza");
		else
			this.dataScadenza = null;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		this.dataScadenza = null;
	}
	
	
	
}


public void setDataScadenza(String dataScadenza) throws ParseException {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	this.dataScadenza= sdf.parse(dataScadenza);
}

//SET e GET  
public String getImportatoriDescription() {
	return importatoriDescription;
}
public void setImportatoriDescription(String importatoriDescription) {
	this.importatoriDescription = importatoriDescription;
}


public Integer getCanilebduId() {
	return canilebduId;
}
public void setCanilebduId(Integer canilebduId) {
	this.canilebduId = canilebduId;
}


public String getCanilebduDescription() {
	return canilebduDescription;
}
public void setCanilebduDescription(String canilebduDescription) {
	this.canilebduDescription = canilebduDescription;
}

	public Integer getId_importatore() {
	return id_importatore;
}
public void setId_importatore(Integer id_importatore) {
	this.id_importatore = id_importatore;
}
	public String getNumAutorizzazione() {
	return numAutorizzazione;
}
public void setNumAutorizzazione(String numAutorizzazione) {
	this.numAutorizzazione = numAutorizzazione;
}
	public String getLuogo() {
	return luogo;
}
public void setLuogo(String luogo) {
	this.luogo = luogo;
}

public int getGestore() {
	return gestore;
}
public void setGestore(int gestore) {
	this.gestore = gestore;
}

public int getComuneGestore() {
	return comuneGestore;
}
public void setComuneGestore(int comuneGestore) {
	this.comuneGestore = comuneGestore;
}

public String getLuogoVam() {
	return luogoVam;
}
public void setLuogoVam(String luogoVam) {
	this.luogoVam = luogoVam;
}

public String getPiva() {
	return piva;
}
public void setPiva(String piva) {
	this.piva = piva;
}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordEncrypted() {
		return passwordEncrypted;
	}
	public void setPasswordEncrypted(String passwordEncrypted) {
		this.passwordEncrypted = passwordEncrypted;
	}
	
	public Date getExpires() {
		return expires;
	}
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getNominativo() {
		return (getCognome() != null ? getCognome() : "") + " , " + (getNome() != null ? getNome() : "");
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public Asl getAsl() {
		return asl;
	}
	public void setAsl(Asl asl) {
		this.asl = asl;
	}
	
	public Integer getClinicaId() {
		return clinicaId;
	}
	public void setClinicaId(Integer clinicaId) {
		this.clinicaId = clinicaId;
	}
	
	public String getClinicaDescription() {
		return clinicaDescription;
	}
	public void setClinicaDescription(String clinicaDescription) {
		this.clinicaDescription = clinicaDescription;
	}
	
	
	public Integer getCanileId() {
		return canileId;
	}
	public void setCanileId(Integer canileId) {
		this.canileId = canileId;
	}
	
	public String getCanileDescription() {
		return canileDescription;
	}
	public void setCanileDescription(String canileDescription) {
		this.canileDescription = canileDescription;
	}
	
	public Set<Ruolo> getRuoli() {
		return ruoli;
	}
	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
		for(GUCEndpoint e : GUCEndpoint.values()){
			hashRuoli.put(e.toString(), new Ruolo());
		}
		for(Ruolo r : ruoli){
			hashRuoli.put(r.getEndpoint(), r);
		}
	}
	
	public boolean containsEndPoint(String nomeEndPoint)
	{
		Iterator<Ruolo> it =  ruoli.iterator();
		while (it.hasNext())
		{
			if (it.next().getEndpoint().equalsIgnoreCase(nomeEndPoint))
				return true ;
		}
		return false;
	}
	public TreeMap<String, Ruolo> getHashRuoli() {
		return hashRuoli;
	}
	
	public int getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public Date getEntered() {
		return entered;
	}
	public void setEntered(Date entered) {
		this.entered = entered;
	}
	
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	public String getOldUsername() {
		return oldUsername;
	}
	public void setOldUsername(String oldUsername) {
		this.oldUsername = oldUsername;
	}
	
	public Integer getRuoloId() {
		return ruoloId;
	}
	public void setRuoloId(Integer ruoloId) {
		this.ruoloId = ruoloId;
	}
	
	public boolean isNewPassword() {
		return newPassword;
	}
	public void setNewPassword(boolean newPassword) {
		this.newPassword = newPassword;
	}
	public ArrayList<Clinica> getClinicheVam() {
		return clinicheVam;
	}
	public void setClinicheVam(ArrayList<Clinica> clinicheVam) {
		this.clinicheVam = clinicheVam;
	}
	public ArrayList<Canile> getCaniliBdu() {
		return caniliBdu;
	}
	public void setCaniliBdu(ArrayList<Canile> caniliBdu) {
		this.caniliBdu = caniliBdu;
	}
	public ArrayList<Struttura> getStruttureGisa() {
		return struttureGisa;
	}
	public void setStruttureGisa(ArrayList<Struttura> struttureGisa) {
		this.struttureGisa = struttureGisa;
	}
	public Integer getStrutturagisaId() {
		return strutturagisaId;
	}
	public void setStrutturagisaId(Integer strutturagisaId) {
		this.strutturagisaId = strutturagisaId;
	}
	public String getStrutturagisaDescription() {
		return strutturagisaDescription;
	}
	public void setStrutturagisaDescription(String strutturagisaDescription) {
		this.strutturagisaDescription = strutturagisaDescription;
	}
	public ArrayList<Importatori> getImportatori() {
		return importatori;
	}
	public void setImportatori(ArrayList<Importatori> importatori) {
		this.importatori = importatori;
	}
	public int getId_provincia_iscrizione_albo_vet_privato() {
		return id_provincia_iscrizione_albo_vet_privato;
	}
	public void setId_provincia_iscrizione_albo_vet_privato(
			int id_provincia_iscrizione_albo_vet_privato) {
		this.id_provincia_iscrizione_albo_vet_privato = id_provincia_iscrizione_albo_vet_privato;
	}
	public String getNr_iscrione_albo_vet_privato() {
		return nr_iscrione_albo_vet_privato;
	}
	public void setNr_iscrione_albo_vet_privato(
			String nr_iscrione_albo_vet_privato) {
		this.nr_iscrione_albo_vet_privato = nr_iscrione_albo_vet_privato;
	}
	
	public int getId_provincia_iscrizione_albo_vet_privato_vam() {
		return id_provincia_iscrizione_albo_vet_privato_vam;
	}
	public void setId_provincia_iscrizione_albo_vet_privato_vam(
			int id_provincia_iscrizione_albo_vet_privato_vam) {
		this.id_provincia_iscrizione_albo_vet_privato_vam = id_provincia_iscrizione_albo_vet_privato_vam;
	}
	public String getNr_iscrione_albo_vet_privato_vam() {
		return nr_iscrione_albo_vet_privato_vam;
	}
	public void setNr_iscrione_albo_vet_privato_vam(
			String nr_iscrione_albo_vet_privato_vam) {
		this.nr_iscrione_albo_vet_privato_vam = nr_iscrione_albo_vet_privato_vam;
	}
	
	
	public HashMap<String,HashMap<String,String>> getExtOption() {
		return extOption;
	}
	public void setExtOption(HashMap<String,HashMap<String,String>> extOption) {
		this.extOption = extOption;
	}
	public void setQuery(HashMap<String, String> qQQ) {
		// TODO Auto-generated method stub
		this.query=qQQ;
	}
	
	public HashMap<String, String> getQuery() {
		// TODO Auto-generated method stub
		return this.query;
	}
	
	
	public boolean confrontaUtente(Utente preModifica,int endPoint)
	{
		
		switch(endPoint)
		{
		case EndPoint.GISA :
		{
			
			Ruolo ruoloPreModifica = preModifica.getHashRuoli().get(GUCEndpoint.Gisa.toString());
			Ruolo thisRuolo = this.getHashRuoli().get(GUCEndpoint.Gisa.toString());
			
			HashMap<String, String> extOpt =  this.getExtOption().get(GUCEndpoint.Gisa.toString());
			HashMap<String, String> extOptPreModifica =  preModifica.getExtOption().get(GUCEndpoint.Gisa.toString());
			Iterator<String> itPreMod = extOptPreModifica.keySet().iterator();
			boolean differenze = false ;
			
			if(extOptPreModifica.size()!=extOpt.size())
			{
				differenze=true;
			}
			else
			while (itPreMod.hasNext())
			{
				String key = itPreMod.next();
				if(extOpt.containsKey(key))
				{
					if(!extOptPreModifica.get(key).equalsIgnoreCase(extOpt.get(key)))
					{
						differenze = true ;
						break ;
					}
					
				}
				else
				{
					differenze=true;
					break ;
				}
				
			}
			
			
			if(ruoloPreModifica.getRuoloInteger()!=thisRuolo.getRuoloInteger() ||differenze)
			{
				return true ;
			}
			return false;

		}
		
		case EndPoint.GISA_EXT :
		{
			
			
			Ruolo ruoloPreModifica = preModifica.getHashRuoli().get(GUCEndpoint.Gisa_ext.toString());
			Ruolo thisRuolo = this.getHashRuoli().get(GUCEndpoint.Gisa_ext.toString());
			
			HashMap<String, String> extOpt =  this.getExtOption().get(GUCEndpoint.Gisa_ext.toString());
			HashMap<String, String> extOptPreModifica =  preModifica.getExtOption().get(GUCEndpoint.Gisa_ext.toString());
			Iterator<String> itPreMod = extOptPreModifica.keySet().iterator();
			boolean differenze = false ;
			
			
			
			
			while (itPreMod.hasNext())
			{
				String key = itPreMod.next();
				if(extOpt.containsKey(key))
				{
					if(!extOptPreModifica.get(key).equalsIgnoreCase(extOpt.get(key)))
					{
						differenze = true ;
						break ;
					}
					
				}
				else
				{
					differenze=true;
					break ;
				}
				
			}
			
			
		
			
			
			if(
					(this.getLivelloAccreditamentoSuap()!=preModifica.getLivelloAccreditamentoSuap()) ||
					(this.getCallbackSuap() !=null && preModifica.getCallbackSuap() != null && ! this.getCallbackSuap().equalsIgnoreCase(preModifica.getCallbackSuap())) ||
					(this.getComuneSuap() !=null && preModifica.getComuneSuap() != null && ! this.getComuneSuap().equalsIgnoreCase(preModifica.getComuneSuap())) ||
					(this.getSharedKeySuap() !=null && preModifica.getSharedKeySuap() != null && ! this.getSharedKeySuap().equalsIgnoreCase(preModifica.getSharedKeySuap())) ||
					//(this.getTelefonoSuap() !=null && preModifica.getTelefonoSuap() != null && ! this.getTelefonoSuap().equalsIgnoreCase(preModifica.getTelefonoSuap())) ||
					(this.getIpSuap() !=null && preModifica.getIpSuap() != null && ! this.getIpSuap().equalsIgnoreCase(preModifica.getIpSuap())) ||
					(this.getPecSuap() !=null && preModifica.getPecSuap() != null && ! this.getPecSuap().equalsIgnoreCase(preModifica.getPecSuap())) ||
					
					ruoloPreModifica.getRuoloInteger()!=thisRuolo.getRuoloInteger() ||differenze || (this.getNumRegStab()!=null && preModifica.getNumRegStab()!=null && !this.getNumRegStab().equals(preModifica.getNumRegStab())))
			{
				return true ;
			}
			
			return false;

			
		}
		
		case EndPoint.BDU :
		{
			
			Ruolo ruoloPreModifica = preModifica.getHashRuoli().get(GUCEndpoint.bdu.toString());
			Ruolo thisRuolo = this.getHashRuoli().get(GUCEndpoint.bdu.toString());
			
			
			ArrayList<Canile> listaCanili = this.getCaniliBdu();
			ArrayList<Canile> listaCaniliPreModifica = preModifica.getCaniliBdu();
			
			boolean diff = false;
			boolean trovato = false ;
			for (Canile canileDaAggiornare : listaCanili)
			{
				trovato = false ;
				for ( Canile canilePresente : listaCaniliPreModifica)
				{
					if(canileDaAggiornare.getDescrizioneCanile().equalsIgnoreCase(canilePresente.getDescrizioneCanile()))
					{
						trovato = true ;
					}
				}
				
				if (trovato==false)
					diff = true ;
			}
			
			
			if(ruoloPreModifica.getRuoloInteger()!=thisRuolo.getRuoloInteger() ||
					(this.getGestore()>0 && preModifica.getGestore()>0 && this.getGestore()!=preModifica.getGestore()  ) ||
					(this.getComuneGestore()>0 && preModifica.getComuneGestore()>0 && this.getComuneGestore()!=preModifica.getComuneGestore()  ) ||
					(this.getPiva()!=null && preModifica.getPiva()!=null && this.getPiva()!=preModifica.getPiva()  ) ||
					(this.getLuogo()    != null && preModifica.getLuogo()!=null && !this.getLuogo().equalsIgnoreCase(preModifica.getLuogo())  ) ||
					(this.getLuogoVam() != null && preModifica.getLuogoVam()!=null && !this.getLuogoVam().equalsIgnoreCase(preModifica.getLuogoVam())  ) ||
					
					(this.getId_provincia_iscrizione_albo_vet_privato()!=preModifica.getId_provincia_iscrizione_albo_vet_privato() )|| 
					(this.getId_provincia_iscrizione_albo_vet_privato_vam()!=preModifica.getId_provincia_iscrizione_albo_vet_privato_vam() )|| 
					
					(this.getNumAutorizzazione() != null && preModifica.getNumAutorizzazione()!=null && !this.getNumAutorizzazione().equalsIgnoreCase(preModifica.getNumAutorizzazione()) )
					|| diff==true
					
					)
			{
				return true ;
			}
			
			
			return false;
		}
		
		case EndPoint.VAM :
		{
			
			
			ArrayList<Clinica> listaCanili = this.getClinicheVam();
			ArrayList<Clinica> listaCaniliPreModifica = preModifica.getClinicheVam();
			
			boolean diff = false;
			boolean trovato = false ;
			for (Clinica canileDaAggiornare : listaCanili)
			{
				trovato = false ;
				for ( Clinica canilePresente : listaCaniliPreModifica)
				{
					if(canileDaAggiornare.getDescrizioneClinica().equalsIgnoreCase(canilePresente.getDescrizioneClinica()))
					{
						trovato = true ;
					}
				}
				
				if (trovato==false)
					diff = true ;
			}
			
			for (Clinica canileDaAggiornare : listaCaniliPreModifica)
			{
				trovato = false ;
				for ( Clinica canilePresente : listaCanili)
				{
					if(canileDaAggiornare.getDescrizioneClinica().equalsIgnoreCase(canilePresente.getDescrizioneClinica()))
					{
						trovato = true ;
					}
				}
				
				if (trovato==false)
					diff = true ;
			}
			
			Ruolo ruoloPreModifica = preModifica.getHashRuoli().get(GUCEndpoint.Vam.toString());
			Ruolo thisRuolo = this.getHashRuoli().get(GUCEndpoint.Vam.toString());
			
			if(ruoloPreModifica.getRuoloInteger()!=thisRuolo.getRuoloInteger() || diff ==true)
			{
				return true ;
			}
			return false;
			
			
		}
		case EndPoint.DIGEMON :
		{
			Ruolo ruoloPreModifica = preModifica.getHashRuoli().get(GUCEndpoint.Digemon.toString());
			Ruolo thisRuolo = this.getHashRuoli().get(GUCEndpoint.Digemon.toString());
			
			if(ruoloPreModifica.getRuoloInteger()!=thisRuolo.getRuoloInteger() )
			{
				return true ;
			}
			return false;
		}
	
		
		}
		
		
		
		return false ;
		
		
	}
	public String getEnteredByUsername() {
		return enteredByUsername;
	}
	public void setEnteredByUsername(String enteredByUsername) {
		this.enteredByUsername = enteredByUsername;
	}
	public int getComuneApicoltore() {
		return comuneApicoltore;
	}
	public void setComuneApicoltore(int comuneApicoltore) {
		this.comuneApicoltore = comuneApicoltore;
	}
	public String getIndirizzoApicoltore() {
		return indirizzoApicoltore;
	}
	public void setIndirizzoApicoltore(String indirizzoApicoltore) {
		this.indirizzoApicoltore = indirizzoApicoltore;
	}
	public String getCapIndirizzoApicoltore() {
		return capIndirizzoApicoltore;
	}
	public void setCapIndirizzoApicoltore(String capIndirizzoApicoltore) {
		this.capIndirizzoApicoltore = capIndirizzoApicoltore;
	}
	public int getComuneTrasportatore() {
		return comuneTrasportatore;
	}
	public void setComuneTrasportatore(int comuneTrasportatore) {
		this.comuneTrasportatore = comuneTrasportatore;
	}
	public String getIndirizzoTrasportatore() {
		return indirizzoTrasportatore;
	}
	public void setIndirizzoTrasportatore(String indirizzoTrasportatore) {
		this.indirizzoTrasportatore = indirizzoTrasportatore;
	}
	public String getCapIndirizzoTrasportatore() {
		return capIndirizzoTrasportatore;
	}
	public void setCapIndirizzoTrasportatore(String capIndirizzoTrasportatore) {
		this.capIndirizzoTrasportatore = capIndirizzoTrasportatore;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTipoAttivitaApicoltore() {
		return tipoAttivitaApicoltore;
	}
	public void setTipoAttivitaApicoltore(String tipoAttivitaApicoltore) {
		this.tipoAttivitaApicoltore = tipoAttivitaApicoltore;
	}

}
