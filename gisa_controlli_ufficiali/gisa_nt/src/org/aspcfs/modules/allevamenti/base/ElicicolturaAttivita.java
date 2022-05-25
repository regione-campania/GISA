/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allevamenti.base;

import org.json.JSONObject;

public class ElicicolturaAttivita {
	
	 private String dtUltimaModifica;
     private String eliattId;
     private String denominazione; 
     private String aziendaId;
     private String aziendaCodice; 
     private String aslId;
     private String aslCodice; 
     private String aslDenominazione; 
     private String comId;
     private String comIstat; 
     private String comDescrizione; 
     private String comProSigla; 
     private String regId;
     private String regCodice; 
     private String regDescrizione; 
     private String operId;
     private String operIdFiscale; 
     private String operCognNome; 
     private String tipattId;
     private String tipattCodice; 
     private String tipattDescrizione; 
     private String superficie;
     private String elidelId;
     private String elidelDelegatoId;
     private String elidelDtAssegnazioneDelega;
     private String elidelDtRevoca;
     private String elidelTipoDelegato;
     private String elidelCodice;
     private String elidelDenominazione;
     private String dtInizioAttivita; 
     private String dtFineAttivita;
     private String note; 
     private String statoDelega; 
     private String distrettoCodice;
     private String specieCodice;
     private String oriproCodice;
	public ElicicolturaAttivita(JSONObject explrObject) {
		
		 this.dtUltimaModifica = explrObject.getString("dtUltimaModifica");
	     this.eliattId = explrObject.getString("eliattId");
	     this.denominazione = explrObject.getString("denominazione"); 
	     this.aziendaId = explrObject.getString("aziendaId");
	     this.aziendaCodice = explrObject.getString("aziendaCodice"); 
	     this.aslId = explrObject.getString("aslId");
	     this.aslCodice = explrObject.getString("aslCodice"); 
	     this.aslDenominazione = explrObject.getString("aslDenominazione"); 
	     this.comId = explrObject.getString("comId");
	     this.comIstat = explrObject.getString("comIstat"); 
	     this.comDescrizione = explrObject.getString("comDescrizione"); 
	     this.comProSigla = explrObject.getString("comProSigla"); 
	     this.regId = explrObject.getString("regId");
	     this.regCodice = explrObject.getString("regCodice"); 
	     this.regDescrizione = explrObject.getString("regDescrizione"); 
	     this.operId = explrObject.getString("operId");
	     this.operIdFiscale = explrObject.getString("operIdFiscale"); 
	     this.operCognNome = explrObject.getString("operCognNome"); 
	     this.tipattId = explrObject.getString("tipattId");
	     this.tipattCodice = explrObject.getString("tipattCodice"); 
	     this.tipattDescrizione = explrObject.getString("tipattDescrizione"); 
	     this.superficie = explrObject.getString("superficie");
	     this.elidelId = explrObject.getString("elidelId");
	     this.elidelDelegatoId = explrObject.getString("elidelDelegatoId");
	     this.elidelDtAssegnazioneDelega = explrObject.getString("elidelDtAssegnazioneDelega");
	     this.elidelDtRevoca = explrObject.getString("elidelDtRevoca");
	     this.elidelTipoDelegato = explrObject.getString("elidelTipoDelegato");
	     this.elidelCodice = explrObject.getString("elidelCodice");
	     this.elidelDenominazione = explrObject.getString("elidelDenominazione");
	     this.dtInizioAttivita = explrObject.getString("dtInizioAttivita"); 
	     this.dtFineAttivita = explrObject.getString("dtFineAttivita");
	     this.note = explrObject.getString("note"); 
	     this.statoDelega = explrObject.getString("statoDelega"); 
	     this.distrettoCodice = explrObject.getString("distrettoCodice");
	     this.specieCodice = explrObject.getString("specieCodice");
	     this.oriproCodice = explrObject.getString("oriproCodice");	
	     }
	
	public String getDtUltimaModifica() {
		return dtUltimaModifica;
	}
	public void setDtUltimaModifica(String dtUltimaModifica) {
		this.dtUltimaModifica = dtUltimaModifica;
	}
	public String getEliattId() {
		return eliattId;
	}
	public void setEliattId(String eliattId) {
		this.eliattId = eliattId;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getAziendaId() {
		return aziendaId;
	}
	public void setAziendaId(String aziendaId) {
		this.aziendaId = aziendaId;
	}
	public String getAziendaCodice() {
		return aziendaCodice;
	}
	public void setAziendaCodice(String aziendaCodice) {
		this.aziendaCodice = aziendaCodice;
	}
	public String getAslId() {
		return aslId;
	}
	public void setAslId(String aslId) {
		this.aslId = aslId;
	}
	public String getAslCodice() {
		return aslCodice;
	}
	public void setAslCodice(String aslCodice) {
		this.aslCodice = aslCodice;
	}
	public String getAslDenominazione() {
		return aslDenominazione;
	}
	public void setAslDenominazione(String aslDenominazione) {
		this.aslDenominazione = aslDenominazione;
	}
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getComIstat() {
		return comIstat;
	}
	public void setComIstat(String comIstat) {
		this.comIstat = comIstat;
	}
	public String getComDescrizione() {
		return comDescrizione;
	}
	public void setComDescrizione(String comDescrizione) {
		this.comDescrizione = comDescrizione;
	}
	public String getComProSigla() {
		return comProSigla;
	}
	public void setComProSigla(String comProSigla) {
		this.comProSigla = comProSigla;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegCodice() {
		return regCodice;
	}
	public void setRegCodice(String regCodice) {
		this.regCodice = regCodice;
	}
	public String getRegDescrizione() {
		return regDescrizione;
	}
	public void setRegDescrizione(String regDescrizione) {
		this.regDescrizione = regDescrizione;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getOperIdFiscale() {
		return operIdFiscale;
	}
	public void setOperIdFiscale(String operIdFiscale) {
		this.operIdFiscale = operIdFiscale;
	}
	public String getOperCognNome() {
		return operCognNome;
	}
	public void setOperCognNome(String operCognNome) {
		this.operCognNome = operCognNome;
	}
	public String getTipattId() {
		return tipattId;
	}
	public void setTipattId(String tipattId) {
		this.tipattId = tipattId;
	}
	public String getTipattCodice() {
		return tipattCodice;
	}
	public void setTipattCodice(String tipattCodice) {
		this.tipattCodice = tipattCodice;
	}
	public String getTipattDescrizione() {
		return tipattDescrizione;
	}
	public void setTipattDescrizione(String tipattDescrizione) {
		this.tipattDescrizione = tipattDescrizione;
	}
	public String getSuperficie() {
		return superficie;
	}
	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}
	public String getElidelId() {
		return elidelId;
	}
	public void setElidelId(String elidelId) {
		this.elidelId = elidelId;
	}
	public String getElidelDelegatoId() {
		return elidelDelegatoId;
	}
	public void setElidelDelegatoId(String elidelDelegatoId) {
		this.elidelDelegatoId = elidelDelegatoId;
	}
	public String getElidelDtAssegnazioneDelega() {
		return elidelDtAssegnazioneDelega;
	}
	public void setElidelDtAssegnazioneDelega(String elidelDtAssegnazioneDelega) {
		this.elidelDtAssegnazioneDelega = elidelDtAssegnazioneDelega;
	}
	public String getElidelDtRevoca() {
		return elidelDtRevoca;
	}
	public void setElidelDtRevoca(String elidelDtRevoca) {
		this.elidelDtRevoca = elidelDtRevoca;
	}
	public String getElidelTipoDelegato() {
		return elidelTipoDelegato;
	}
	public void setElidelTipoDelegato(String elidelTipoDelegato) {
		this.elidelTipoDelegato = elidelTipoDelegato;
	}
	public String getElidelCodice() {
		return elidelCodice;
	}
	public void setElidelCodice(String elidelCodice) {
		this.elidelCodice = elidelCodice;
	}
	public String getElidelDenominazione() {
		return elidelDenominazione;
	}
	public void setElidelDenominazione(String elidelDenominazione) {
		this.elidelDenominazione = elidelDenominazione;
	}
	public String getDtInizioAttivita() {
		return dtInizioAttivita;
	}
	public void setDtInizioAttivita(String dtInizioAttivita) {
		this.dtInizioAttivita = dtInizioAttivita;
	}
	public String getDtFineAttivita() {
		return dtFineAttivita;
	}
	public void setDtFineAttivita(String dtFineAttivita) {
		this.dtFineAttivita = dtFineAttivita;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatoDelega() {
		return statoDelega;
	}
	public void setStatoDelega(String statoDelega) {
		this.statoDelega = statoDelega;
	}
	public String getDistrettoCodice() {
		return distrettoCodice;
	}
	public void setDistrettoCodice(String distrettoCodice) {
		this.distrettoCodice = distrettoCodice;
	}
	public String getSpecieCodice() {
		return specieCodice;
	}
	public void setSpecieCodice(String specieCodice) {
		this.specieCodice = specieCodice;
	}
	public String getOriproCodice() {
		return oriproCodice;
	}
	public void setOriproCodice(String oriproCodice) {
		this.oriproCodice = oriproCodice;
	}

}
