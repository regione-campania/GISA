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

import it.us.web.util.guc.GUCEndpoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


public class Utente implements Serializable {

  private static final long serialVersionUID = 1L;
	
//Info Utente
  private int id;
  private String username;
  private String password;
  private String passwordEncrypted;
  private Date expires;
  private boolean enabled = true;
  private String luogo ;
  //Info Contatto
  private String nome;
  private String cognome;
  private String codiceFiscale;
  private String email;
  private String note;
  private String numAutorizzazione ;
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
  
  //Info Struttura gisa GISA NON e ANCORA SUPPORTATO
  private Integer strutturagisaId = -1;
  private String strutturagisaDescription = ""; 
  
  
  //Info Ruoli
  private Set<Ruolo> ruoli = new HashSet<Ruolo>(0);
  private TreeMap<String, Ruolo> hashRuoli = new TreeMap<String, Ruolo>();
  
  //Info Sistema
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private Date entered = null;
  private Date modified = null;
  
  //Info di utilita
  private String oldUsername = null;
  private Integer ruoloId = -1;
  private boolean newPassword;
  
  //CAMPI PER LP ED UNINA
  private int id_provincia_iscrizione_albo_vet_privato;
  private String nr_iscrione_albo_vet_privato;
  
  private ArrayList<Clinica> clinicheVam = new ArrayList<Clinica>();
  private ArrayList<Struttura> struttureGisa = new ArrayList<Struttura>();
  private ArrayList<Importatori> importatori = new ArrayList<Importatori>();
  
  
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

}
