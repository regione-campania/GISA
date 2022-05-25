/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcf.modules.controlliufficiali.base.ModCampioni;
import org.aspcf.modules.report.util.ApplicationProperties;
import org.aspcfs.modules.campioni.base.SpecieAnimali;
import org.aspcfs.modules.campioni.base.Ticket;
import org.aspcfs.utils.DateUtils;

public class ModuloCampione {
	
	private int idCampione = -1;
	private int tipoModulo = -1;

	private String sede_dipartimento="";
	private String mail_dipartimento="";
	private String ora_controllo="";
	private String comune_residenza_rapp_legale="";
	private String indirizzo_residenza_rapp_legale="";
	private String num_indirizzo_residenza_rapp_legale="";
	private String domicilio_digitale_rapp_legale="";
	private String nome_presente_ispezione="";
	private String luogo_nascita_presente_ispezione="";
	private String data_nascita_presente_ispezione="";
	private String num_indirizzo_residenza_presente_ispezione="";
	private String indirizzo_residenza_presente_ispezione="";
	private String comune_residenza_presente_ispezione="";
	private String doc_ident_presente_ispezione="";
	private String ausilio="";
	private String campione_prelevato="";
	private String aliquote="";
	private String peso_aliquote="";
	private String num_uc="";
	private String lettera="";
	private String dicitura="";
	private String proveniente="";
	private String corrente_proveniente ="";
	private String temperatura_aria="";
	private String temperatura_acqua="";
	private String temperatura_acqua_10m="";
	private String data_mareggiata="";
	private String data_pioggia="";
	private String salinita="";
	private String sezione="";
	private String ricerca="";

	
	
	public int getIdCampione() {
		return idCampione;
	}

	public void setIdCampione(int idCampione) {
		this.idCampione = idCampione;
	}

	public int getTipoModulo() {
		return tipoModulo;
	}

	public void setTipoModulo(int tipoModulo) {
		this.tipoModulo = tipoModulo;
	}
	
	public void setTipoModulo(String tipoModulo) {
		if (tipoModulo!=null && !tipoModulo.equals("null"))
			this.tipoModulo = Integer.parseInt(tipoModulo);
	}

	public String getSede_dipartimento() {
		return sede_dipartimento;
	}

	public void setSede_dipartimento(String sede_dipartimento) {
		this.sede_dipartimento = sede_dipartimento;
	}

	public String getMail_dipartimento() {
		return mail_dipartimento;
	}

	public void setMail_dipartimento(String mail_dipartimento) {
		this.mail_dipartimento = mail_dipartimento;
	}

	public String getOra_controllo() {
		return ora_controllo;
	}

	public void setOra_controllo(String ora_controllo) {
		this.ora_controllo = ora_controllo;
	}

	public String getComune_residenza_rapp_legale() {
		return comune_residenza_rapp_legale;
	}

	public void setComune_residenza_rapp_legale(String comune_residenza_rapp_legale) {
		this.comune_residenza_rapp_legale = comune_residenza_rapp_legale;
	}

	public String getIndirizzo_residenza_rapp_legale() {
		return indirizzo_residenza_rapp_legale;
	}

	public void setIndirizzo_residenza_rapp_legale(String indirizzo_residenza_rapp_legale) {
		this.indirizzo_residenza_rapp_legale = indirizzo_residenza_rapp_legale;
	}

	public String getNum_indirizzo_residenza_rapp_legale() {
		return num_indirizzo_residenza_rapp_legale;
	}

	public void setNum_indirizzo_residenza_rapp_legale(String num_indirizzo_residenza_rapp_legale) {
		this.num_indirizzo_residenza_rapp_legale = num_indirizzo_residenza_rapp_legale;
	}

	public String getDomicilio_digitale_rapp_legale() {
		return domicilio_digitale_rapp_legale;
	}

	public void setDomicilio_digitale_rapp_legale(String domicilio_digitale_rapp_legale) {
		this.domicilio_digitale_rapp_legale = domicilio_digitale_rapp_legale;
	}

	public String getNome_presente_ispezione() {
		return nome_presente_ispezione;
	}

	public void setNome_presente_ispezione(String nome_presente_ispezione) {
		this.nome_presente_ispezione = nome_presente_ispezione;
	}

	public String getLuogo_nascita_presente_ispezione() {
		return luogo_nascita_presente_ispezione;
	}

	public void setLuogo_nascita_presente_ispezione(String luogo_nascita_presente_ispezione) {
		this.luogo_nascita_presente_ispezione = luogo_nascita_presente_ispezione;
	}

	public String getData_nascita_presente_ispezione() {
		return data_nascita_presente_ispezione;
	}

	public void setData_nascita_presente_ispezione(String data_nascita_presente_ispezione) {
		this.data_nascita_presente_ispezione = data_nascita_presente_ispezione;
	}

	public String getNum_indirizzo_residenza_presente_ispezione() {
		return num_indirizzo_residenza_presente_ispezione;
	}

	public void setNum_indirizzo_residenza_presente_ispezione(String num_indirizzo_residenza_presente_ispezione) {
		this.num_indirizzo_residenza_presente_ispezione = num_indirizzo_residenza_presente_ispezione;
	}

	public String getIndirizzo_residenza_presente_ispezione() {
		return indirizzo_residenza_presente_ispezione;
	}

	public void setIndirizzo_residenza_presente_ispezione(String indirizzo_residenza_presente_ispezione) {
		this.indirizzo_residenza_presente_ispezione = indirizzo_residenza_presente_ispezione;
	}

	public String getDoc_ident_presente_ispezione() {
		return doc_ident_presente_ispezione;
	}

	public void setDoc_ident_presente_ispezione(String doc_ident_presente_ispezione) {
		this.doc_ident_presente_ispezione = doc_ident_presente_ispezione;
	}

	public String getAusilio() {
		return ausilio;
	}

	public void setAusilio(String ausilio) {
		this.ausilio = ausilio;
	}

	public String getCampione_prelevato() {
		return campione_prelevato;
	}

	public void setCampione_prelevato(String campione_prelevato) {
		this.campione_prelevato = campione_prelevato;
	}

	public String getAliquote() {
		return aliquote;
	}

	public void setAliquote(String aliquote) {
		this.aliquote = aliquote;
	}

	public String getPeso_aliquote() {
		return peso_aliquote;
	}

	public void setPeso_aliquote(String peso_aliquote) {
		this.peso_aliquote = peso_aliquote;
	}

	public String getNum_uc() {
		return num_uc;
	}

	public void setNum_uc(String num_uc) {
		this.num_uc = num_uc;
	}

	public String getLettera() {
		return lettera;
	}

	public void setLettera(String lettera) {
		this.lettera = lettera;
	}

	public String getDicitura() {
		return dicitura;
	}

	public void setDicitura(String dicitura) {
		this.dicitura = dicitura;
	}

	public String getProveniente() {
		return proveniente;
	}

	public void setProveniente(String proveniente) {
		this.proveniente = proveniente;
	}

	public String getTemperatura_aria() {
		return temperatura_aria;
	}

	public void setTemperatura_aria(String temperatura_aria) {
		this.temperatura_aria = temperatura_aria;
	}

	public String getTemperatura_acqua() {
		return temperatura_acqua;
	}

	public void setTemperatura_acqua(String temperatura_acqua) {
		this.temperatura_acqua = temperatura_acqua;
	}

	public String getTemperatura_acqua_10m() {
		return temperatura_acqua_10m;
	}

	public void setTemperatura_acqua_10m(String temperatura_acqua_10m) {
		this.temperatura_acqua_10m = temperatura_acqua_10m;
	}

	public String getData_mareggiata() {
		return data_mareggiata;
	}

	public void setData_mareggiata(String data_mareggiata) {
		this.data_mareggiata = data_mareggiata;
	}

	public String getData_pioggia() {
		return data_pioggia;
	}

	public void setData_pioggia(String data_pioggia) {
		this.data_pioggia = data_pioggia;
	}

	public String getSalinita() {
		return salinita;
	}

	public void setSalinita(String salinita) {
		this.salinita = salinita;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getRicerca() {
		return ricerca;
	}

	public void setRicerca(String ricerca) {
		this.ricerca = ricerca;
	}

	public String getComune_residenza_presente_ispezione() {
		return comune_residenza_presente_ispezione;
	}

	public void setComune_residenza_presente_ispezione(String comune_residenza_presente_ispezione) {
		this.comune_residenza_presente_ispezione = comune_residenza_presente_ispezione;
	}

	public String getCorrente_proveniente() {
		return corrente_proveniente;
	}

	public void setCorrente_proveniente(String corrente_proveniente) {
		this.corrente_proveniente = corrente_proveniente;
	}

	//kiave = nomeCampo
	// value = hashmap<String ,String>
	//kiave = valorecampo
	HashMap<String,HashMap<String ,String>> listaCampiModulo = new HashMap<String, HashMap<String ,String>>();
	
	
	public HashMap<String, HashMap<String,String>> getListaCampiModulo() {
		return listaCampiModulo;
	}

	public void setListaCampiModulo(HashMap<String, HashMap<String,String>> listaCampiModulo) {
		this.listaCampiModulo = listaCampiModulo;
	}

	

	public ModuloCampione() throws SQLException {
	}

	

	
	

	
}