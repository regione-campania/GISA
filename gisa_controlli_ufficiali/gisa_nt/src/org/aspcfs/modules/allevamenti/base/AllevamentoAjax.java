/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allevamenti.base;
/**
 * @author Daniele
 */
public class AllevamentoAjax
{
	private int asl;
	private String comune;
	private String descComune;
	private String cap;
	private String denominazione;
	private String codice_fiscale;
	private String codice_azienda;
	private String indirizzo;
	private String note;
	private String flag_Carne_Latte;
	private String data_inizio_attivita;
	private String data_fine_attivita;
	private int specie_allevata;
	private String descrizione_specie;
	private String cfProprietario  = "" ; ;
	private String cfDetentore = "" ;
	private int numero_capi;
	private boolean inBDN;
	private int errore;
	private String codiceTipoAllevamento;
	private String orientamento_prod;
	private String tipologia_strutt;
	
	
	public String getCfProprietario() {
		return cfProprietario;
	}
	public void setCfProprietario(String cfProprietario) {
		this.cfProprietario = cfProprietario;
	}
	public String getCfDetentore() {
		return cfDetentore;
	}
	public void setCfDetentore(String cfDetentore) {
		this.cfDetentore = cfDetentore;
	}
	public String getDescrizione_specie() {
		return descrizione_specie;
	}
	public void setDescrizione_specie(String descrizione_specie) {
		this.descrizione_specie = descrizione_specie;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCodice_azienda() {
		return codice_azienda;
	}
	public void setCodice_azienda(String codice_azienda) {
		this.codice_azienda = codice_azienda;
	}
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codiceFiscale) {
		this.codice_fiscale = codiceFiscale;
	}
	public int getAsl() {
		return asl;
	}
	public void setAsl(int asl) {
		this.asl = asl;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getNumero_capi() {
		return numero_capi;
	}
	public void setNumero_capi(int numero_capi) {
		this.numero_capi = numero_capi;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public String getData_inizio_attivita() {
		return data_inizio_attivita;
	}
	public void setData_inizio_attivita(String dataInizioAttivita) {
		this.data_inizio_attivita = dataInizioAttivita;
	}
	public String getData_fine_attivita() {
		return data_fine_attivita;
	}
	public void setData_fine_attivita(String dataFineAttivita) {
		this.data_fine_attivita = dataFineAttivita;
	}	
	public int getSpecie_allevata() {
		return specie_allevata;
	}
	public void setSpecie_allevata(int specieAllevata) {
		this.specie_allevata = specieAllevata;
	}	
	public int getErrore() {
		return errore;
	}
	public void setErrore(int errore) {
		this.errore = errore;
	}
	public boolean getInBDN() {
		return inBDN;
	}
	public void setInBDN(boolean inBDN) {
		this.inBDN = inBDN;
	}
	public String getCodiceTipoAllevamento() {
		return codiceTipoAllevamento;
	}
	public void setCodiceTipoAllevamento(String codiceTipoAllevamento) {
		this.codiceTipoAllevamento = codiceTipoAllevamento;
	}
	public String getOrientamento_prod() {
		return orientamento_prod;
	}
	public void setOrientamento_prod(String orientamento_prod) {
		this.orientamento_prod = orientamento_prod;
	}
	
	public String getTipologia_strutt() {
		return tipologia_strutt;
	}
	public void setTipologia_strutt(String tipologia_strutt) {
		this.tipologia_strutt = tipologia_strutt;
	}
	
	public String getFlag_Carne_Latte() {
		return flag_Carne_Latte;
	}
	public void setFlag_Carne_Latte(String flag_Carne_Latte) {
		this.flag_Carne_Latte = flag_Carne_Latte;
	}
		
}