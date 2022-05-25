/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Organization {
	int idStabilimento ;
	int orgId;
	int idApiario;
	int altId;
	int siteId ;
	private String name ="";
	int tipologia ;
	int codiceAcqua;
	int idControllo;
	
	ArrayList<String> concessionari = new ArrayList<String>();
	String specieMolluschi = "";
	String OperatoreUrl;
	String ragione_sociale;
	String data_referto;
	String data_chiusura_controllo;
	String annoReferto;
	String meseReferto;
	String giornoReferto;
	String latitudini = "";
	String longitudini = "";
	String componente_nucleo;
	String componente_nucleo_due;
	String componente_nucleo_tre;
	String componente_nucleo_quattro;
	String componente_nucleo_cinque;
	String componente_nucleo_sei;
	String componente_nucleo_sette;
	String componente_nucleo_otto;
	String componente_nucleo_nove;
	String componente_nucleo_dieci;
	String nome_rappresentante;
	String cognome_rappresentante;
	String data_nascita_rappresentante;
	String legale_rapp;
	String luogo_nascita_rappresentante; 
	String citta_legale_rapp; 
	String indirizzo_legale_rapp; 
	String civico_indirizzo_legale_rapp; 
	String provincia_legale_rapp; 
	String domicilio_digitale_legale_rapp;
	String data_fine_controllo;
	String giorno_chiusura;
	String mese_chiusura;
	String anno_chiusura;
	String approval_number;
	String n_reg;
	String comune;
	String domicilio_digitale;
	String indirizzo;
	String civico_indirizzo;
	String codice_fiscale;
	String codice_specie;
	String sede_legale;
	String indirizzo_legale;
	String civico_indirizzo_legale;
	String tipologia_att; //Corrisponde alla tipologia
	String tipologia_struttura;
	String punteggio_ispezione;
	String punteggio_significativo;
	String punteggio_formale;
	String punteggio_grave;
	String descrizione_ncf;
	String descrizione_ncs;
	String descrizione_ncg;
	String valutazione_formale;
	String valutazione_significativa;
	String valutazione_grave;
	String followupF;
	String followupS;
	String followupG;
	String processiVerbali;
	String listaProvNC;
	private String accountNumber = "" ;
	String classe="";
	String prov;
	String cap;
	String asl;
	String problem;
	String comune_operativo ;
	String indirizzo_operativo ;
	String cun;
	String action;
	String codice_tipo_allevamento;
	
	private String nominativo_proprietario;
	private String nominativo_detentore;

	private int num_specie1 = -1;
	private int num_specie2 = -1;
	private int num_specie3 = -1;
	private int num_specie4 = -1;
	private int num_specie5 = -1;
	private int num_specie6 = -1;
	private int num_specie7 = -1;
	private int num_specie8 = -1;
	private int num_specie9 = -1;
	private int num_specie10 = -1;
	private int num_specie11 = -1;
	private int num_specie12 = -1;
	private int num_specie13 = -1;
	private int num_specie14 = -1;
	private int num_specie15  = -1;
	private int num_specie22  = -1;
	private int num_specie23  = -1;
	private int num_specie24  = -1;
	private int num_specie25  = -1;
	private int num_specie26  = -1;
	
	private String container = "";
	private String prefissoAction = "";

	public void setPrefissoAction(String action) {
		this.action = action;
	}
	
	public String getPrefissoAction(String string) {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}
	
	public String getCun() {
		return cun;
	}

	public void setCun(String cun) {
		this.cun = cun;
	}

	public int getIdApiario() {
		return idApiario;
	}

	public void setIdApiario(int idApiario) {
		this.idApiario = idApiario;
	}

	public int getNum_specie1() {
		return num_specie1;
	}

	public void setNum_specie1(int num_specie1) {
		this.num_specie1 = num_specie1;
	}

	public int getNum_specie2() {
		return num_specie2;
	}

	public void setNum_specie2(int num_specie2) {
		this.num_specie2 = num_specie2;
	}

	public int getNum_specie3() {
		return num_specie3;
	}

	public void setNum_specie3(int num_specie3) {
		this.num_specie3 = num_specie3;
	}

	public int getNum_specie4() {
		return num_specie4;
	}

	public void setNum_specie4(int num_specie4) {
		this.num_specie4 = num_specie4;
	}

	public int getNum_specie5() {
		return num_specie5;
	}

	public void setNum_specie5(int num_specie5) {
		this.num_specie5 = num_specie5;
	}

	public int getNum_specie6() {
		return num_specie6;
	}

	public void setNum_specie6(int num_specie6) {
		this.num_specie6 = num_specie6;
	}

	public int getNum_specie7() {
		return num_specie7;
	}

	public void setNum_specie7(int num_specie7) {
		this.num_specie7 = num_specie7;
	}

	public int getNum_specie8() {
		return num_specie8;
	}

	public void setNum_specie8(int num_specie8) {
		this.num_specie8 = num_specie8;
	}

	public int getNum_specie9() {
		return num_specie9;
	}

	public void setNum_specie9(int num_specie9) {
		this.num_specie9 = num_specie9;
	}

	public int getNum_specie10() {
		return num_specie10;
	}

	public void setNum_specie10(int num_specie10) {
		this.num_specie10 = num_specie10;
	}

	public int getNum_specie11() {
		return num_specie11;
	}

	public void setNum_specie11(int num_specie11) {
		this.num_specie11 = num_specie11;
	}

	public int getNum_specie12() {
		return num_specie12;
	}

	public void setNum_specie12(int num_specie12) {
		this.num_specie12 = num_specie12;
	}

	public int getNum_specie13() {
		return num_specie13;
	}

	public void setNum_specie13(int num_specie13) {
		this.num_specie13 = num_specie13;
	}

	public int getNum_specie14() {
		return num_specie14;
	}

	public void setNum_specie14(int num_specie14) {
		this.num_specie14 = num_specie14;
	}

	public int getNum_specie15() {
		return num_specie15;
	}

	public void setNum_specie15(int num_specie15) {
		this.num_specie15 = num_specie15;
	}

	public int getNum_specie22() {
		return num_specie22;
	}

	public void setNum_specie22(int num_specie22) {
		this.num_specie22 = num_specie22;
	}

	public int getNum_specie23() {
		return num_specie23;
	}

	public void setNum_specie23(int num_specie23) {
		this.num_specie23 = num_specie23;
	}

	public int getNum_specie24() {
		return num_specie24;
	}

	public void setNum_specie24(int num_specie24) {
		this.num_specie24 = num_specie24;
	}

	public int getNum_specie25() {
		return num_specie25;
	}

	public void setNum_specie25(int num_specie25) {
		this.num_specie25 = num_specie25;
	}

	public int getNum_specie26() {
		return num_specie26;
	}

	public void setNum_specie26(int num_specie26) {
		this.num_specie26 = num_specie26;
	}


	
	HashMap<Integer,String> tipoCondizionalita = new HashMap<Integer,String>();
	
	
	
	
	public HashMap<Integer, String> getTipoCondizionalita() {
		return tipoCondizionalita;
	}

	public void setTipoCondizionalita(HashMap<Integer, String> tipoCondizionalita) {
		this.tipoCondizionalita = tipoCondizionalita;
	}

	
	public String getComune_operativo() {
		return comune_operativo;
	}

	public void setComune_operativo(String comune_operativo) {
		this.comune_operativo = comune_operativo;
	}

	public String getIndirizzo_operativo() {
		return indirizzo_operativo;
	}

	public void setIndirizzo_operativo(String indirizzo_operativo) {
		this.indirizzo_operativo = indirizzo_operativo;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}

	public String getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}

	String specie_allev;
	String data_inizio_attivita;
	String prop;
	String cf_prop;
	String det;
	String cf_det;
	
	String latitudine = "";
	String longitudine = "";
	
	public int getIdControllo() {
		return idControllo;
	}
	
	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}

	public void setIdControllo(int idControllo) {
		this.idControllo = idControllo;
	}
	
	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getAsl() {
		return asl;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}

	public String getSpecie_allev() {
		return specie_allev;
	}

	public void setSpecie_allev(String specie_allev) {
		this.specie_allev = specie_allev;
	}

	public String getData_inizio_attivita() {
		return data_inizio_attivita;
	}

	public void setData_inizio_attivita(String data_inizio_attivita) {
		this.data_inizio_attivita = data_inizio_attivita;
	}
	
	public String getData_chiusura_controllo() {
		return data_chiusura_controllo;
	}

	public void setData_chiusura_controllo(String data) {
		this.data_chiusura_controllo = data;
	}
	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public String getCf_prop() {
		return cf_prop;
	}

	public void setCf_prop(String cf_prop) {
		this.cf_prop = cf_prop;
	}

	public String getDet() {
		return det;
	}

	public void setDet(String det) {
		this.det = det;
	}

	public String getCf_det() {
		return cf_det;
	}

	public String getCodice_specie() {
		return codice_specie;
	}

	public void setCodice_specie(String codice_specie) {
		this.codice_specie = codice_specie;
	}

	public void setCf_det(String cf_det) {
		this.cf_det = cf_det;
	}

	boolean ncgCheck1 = false;
	boolean ncgCheck2 = false;
	boolean ncgCheck3 = false;
	boolean ncgCheck4 = false;
	boolean ncgCheck5 = false;
	boolean ncgCheck6 = false;
	boolean ncgCheck7 = false;
	
	public boolean isNcgCheck7() {
		return ncgCheck7;
	}

	public void setNcgCheck7(boolean ncgCheck7) {
		this.ncgCheck7 = ncgCheck7;
	}

	public boolean isNcgCheck1() {
		return ncgCheck1;
	}
	
	public void setNcgCheck1(boolean ncgCheck1) {
		this.ncgCheck1 = ncgCheck1;
	}
	
	public boolean isNcgCheck2() {
		return ncgCheck2;
	}
	
	public void setNcgCheck2(boolean ncgCheck2) {
		this.ncgCheck2 = ncgCheck2;
	}
	
	public boolean isNcgCheck3() {
		return ncgCheck3;
	}
	
	public void setNcgCheck3(boolean ncgCheck3) {
		this.ncgCheck3 = ncgCheck3;
	}
	
	public boolean isNcgCheck4() {
		return ncgCheck4;
	}
	
	public void setNcgCheck4(boolean ncgCheck4) {
		this.ncgCheck4 = ncgCheck4;
	}

	public boolean isNcgCheck5() {
		return ncgCheck5;
	}
	
	public void setNcgCheck5(boolean ncgCheck5) {
		this.ncgCheck5 = ncgCheck5;
	}
	
	public boolean isNcgCheck6() {
		return ncgCheck6;
	}
	
	public void setNcgCheck6(boolean ncgCheck6) {
		this.ncgCheck6 = ncgCheck6;
	}
	public String getListProvNC() {
		return listaProvNC;
	}
	public void setListProvNC(String list) {
		this.listaProvNC = list;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public void setClasse(String c) {
		this.classe = c;
	}
	
	public String getProcessiVerbali() {
		return processiVerbali;
	}
	public void setProcessiVerbali(String listSanzioni) {
		this.processiVerbali = listSanzioni;
	}
	public String getOperatoreUrl() {
		return OperatoreUrl;
	}
	public void setOperatoreUrl(String operatoreUrl) {
		OperatoreUrl = operatoreUrl;
	}
	
	public String getDomicilioDigitale() {
		return domicilio_digitale;
	}
	public void setDomicilioDigitale(String dg) {
		this.domicilio_digitale = dg;
	}
	
	public String getFollowUpFormale() {
		return followupF;
	}
	public void setFollowUpFormale(String followup) {
		this.followupF = followup;
	}
	
	public String getFollowUpSign() {
		return followupS;
	}
	public void setFollowUpSign(String followup) {
		this.followupS = followup;
	}
	
	public String getFollowUpGravi() {
		return followupG;
	}
	public void setFollowUpGravi(String followup) {
		this.followupG = followup;
	}
	public ArrayList<String> getConcessionari() {
		return concessionari;
	}

	public void setConcessionari(ArrayList<String> lista) {
		this.concessionari = lista;
	}

	public String getSpecieMolluschi() {
		return specieMolluschi;
	}

	public void setSpecieMolluschi(String specieMolluschi) {
		this.specieMolluschi = specieMolluschi;
	}

	public String getNome_rappresentante() {
		return nome_rappresentante;
	}

	public void setNome_rappresentante(String nome_rappresentante) {
		this.nome_rappresentante = nome_rappresentante;
	}

	public String getCognome_rappresentante() {
		return cognome_rappresentante;
	}

	public void setCognome_rappresentante(String cognome_rappresentante) {
		this.cognome_rappresentante = cognome_rappresentante;
	}

	public String getDomicilio_digitale() {
		return domicilio_digitale;
	}

	public void setDomicilio_digitale(String domicilio_digitale) {
		this.domicilio_digitale = domicilio_digitale;
	}

	public String getFollowupF() {
		return followupF;
	}

	public void setFollowupF(String followupF) {
		this.followupF = followupF;
	}

	public String getFollowupS() {
		return followupS;
	}

	public void setFollowupS(String followupS) {
		this.followupS = followupS;
	}

	public String getFollowupG() {
		return followupG;
	}

	public void setFollowupG(String followupG) {
		this.followupG = followupG;
	}

	public String getListaProvNC() {
		return listaProvNC;
	}

	public void setListaProvNC(String listaProvNC) {
		this.listaProvNC = listaProvNC;
	}

	public String getGiorno_nascita() {
		return giorno_nascita;
	}

	public void setGiorno_nascita(String giorno_nascita) {
		this.giorno_nascita = giorno_nascita;
	}

	public String getMese_nascita() {
		return mese_nascita;
	}

	public void setMese_nascita(String mese_nascita) {
		this.mese_nascita = mese_nascita;
	}

	public String getAnno_nascita() {
		return anno_nascita;
	}

	public void setAnno_nascita(String anno_nascita) {
		this.anno_nascita = anno_nascita;
	}
	public String getValutazione_formale() {
		return valutazione_formale;
	}
	public void setValutazione_formale(String valutazione_formale) {
		this.valutazione_formale = valutazione_formale;
	}
	public String getValutazione_significativa() {
		return valutazione_significativa;
	}
	public void setValutazione_significativa(String valutazione_significativa) {
		this.valutazione_significativa = valutazione_significativa;
	}
	public String getDescrizione_ncf() {
		return descrizione_ncf;
	}
	public void setDescrizione_ncf(String descrizione_ncf) {
		this.descrizione_ncf = descrizione_ncf;
	}
	public String getDescrizione_ncs() {
		return descrizione_ncs;
	}
	public void setDescrizione_ncs(String descrizione_ncs) {
		this.descrizione_ncs = descrizione_ncs;
	}
	public String getDescrizione_ncg() {
		return descrizione_ncg;
	}
	public void setDescrizione_ncg(String descrizione_ncg) {
		this.descrizione_ncg = descrizione_ncg;
	}
	public String getPunteggio_ispezione() {
		return punteggio_ispezione;
	}
	public void setPunteggio_ispezione(String punteggio_ispezione) {
		this.punteggio_ispezione = punteggio_ispezione;
	}
	public String getPunteggio_significativo() {
		return punteggio_significativo;
	}
	public void setPunteggio_significativo(String punteggio_significativo) {
		this.punteggio_significativo = punteggio_significativo;
	}
	public String getPunteggio_formale() {
		return punteggio_formale;
	}
	public void setPunteggio_formale(String punteggio_formale) {
		this.punteggio_formale = punteggio_formale;
	}
	public String getPunteggio_grave() {
		return punteggio_grave;
	}
	public void setPunteggio_grave(String punteggio_grave) {
		this.punteggio_grave = punteggio_grave;
	}
	
	public String getRagione_sociale() {
		return ragione_sociale;
	}
	public void setRagione_sociale(String ragione_sociale) {
		this.ragione_sociale = ragione_sociale;
	}
	public String getNome_Rappresentante() {
		return nome_rappresentante;
	}
	public void setNome_Rappresentante(String nome_rappresentante) {
		this.nome_rappresentante = nome_rappresentante;
	}
	public String getCognome_Rappresentante() {
		return cognome_rappresentante;
	}
	public void setCognome_Rappresentante(String cognome_rappresentante) {
		this.cognome_rappresentante = cognome_rappresentante;
	}
	public String getData_referto() {
		return data_referto;
	}
	public void setData_referto(String data_referto) {
		this.data_referto = data_referto;
	}
	public String getComponente_nucleo() {
		return !"n.d".equalsIgnoreCase(componente_nucleo) ? componente_nucleo : "" ;
	}
	public void setComponente_nucleo(String componente_nucleo) {
		this.componente_nucleo = componente_nucleo;
	}
	public String getComponente_nucleo_due() {
		return !"n.d".equalsIgnoreCase(componente_nucleo_due) ? componente_nucleo_due : "" ;
	}
	public void setComponente_nucleo_due(String componente_nucleo_due) {
		this.componente_nucleo_due = componente_nucleo_due;
	}
	public String getComponente_nucleo_tre() {
		return !"n.d".equalsIgnoreCase(componente_nucleo_tre) ? componente_nucleo_tre : "" ;

	}
	public void setComponente_nucleo_tre(String componente_nucleo_tre) {
		this.componente_nucleo_tre = componente_nucleo_tre;
	}
	public String getComponente_nucleo_quattro() {
		return componente_nucleo_quattro;
	}
	public void setComponente_nucleo_quattro(String componente_nucleo_quattro) {
		this.componente_nucleo_quattro = componente_nucleo_quattro;
	}
	public String getComponente_nucleo_cinque() {
		return componente_nucleo_cinque;
	}
	public void setComponente_nucleo_cinque(String componente_nucleo_cinque) {
		this.componente_nucleo_cinque = componente_nucleo_cinque;
	}
	public String getComponente_nucleo_sei() {
		return componente_nucleo_sei;
	}
	public void setComponente_nucleo_sei(String componente_nucleo_sei) {
		this.componente_nucleo_sei = componente_nucleo_sei;
	}
	public String getComponente_nucleo_sette() {
		return componente_nucleo_sette;
	}
	public void setComponente_nucleo_sette(String componente_nucleo_sette) {
		this.componente_nucleo_sette = componente_nucleo_sette;
	}
	public String getComponente_nucleo_otto() {
		return componente_nucleo_otto;
	}
	public void setComponente_nucleo_otto(String componente_nucleo_otto) {
		this.componente_nucleo_otto = componente_nucleo_otto;
	}
	public String getComponente_nucleo_nove() {
		return componente_nucleo_nove;
	}
	public void setComponente_nucleo_nove(String componente_nucleo_nove) {
		this.componente_nucleo_nove = componente_nucleo_nove;
	}
	public String getComponente_nucleo_dieci() {
		return componente_nucleo_dieci;
	}
	public void setComponente_nucleo_dieci(String componente_nucleo_dieci) {
		this.componente_nucleo_dieci = componente_nucleo_dieci;
	}
	public String getApproval_number() {
		return approval_number;
	}
	public void setApproval_number(String approval_number) {
		this.approval_number = approval_number;
	}
	public String getN_reg() {
		return n_reg;
	}
	public void setN_reg(String n_reg) {
		this.n_reg = n_reg;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}
	public String getSede_legale() {
		return sede_legale;
	}
	public void setSede_legale(String sede_legale) {
		this.sede_legale = sede_legale;
	}
	public String getIndirizzo_legale() {
		return indirizzo_legale;
	}
	public void setIndirizzo_legale(String indirizzo_legale) {
		this.indirizzo_legale = indirizzo_legale;
	}
	
	public void setLegale_rapp(String legale_rapp) {
		this.legale_rapp = legale_rapp;
	}
	public String getLegale_rapp() {
		return legale_rapp;
	}
	public String getTipologia_att() {
		return tipologia_att;
	}
	public void setTipologia_att(String tipologia_att) {
		this.tipologia_att = tipologia_att;
	}
	public int getTipologia() {
		return tipologia;
	}
	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public int getAltId() {
		return altId;
	}
	public void setAltId(int altId) {
		this.altId = altId;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	public int getCodiceAcqua() {
		return codiceAcqua;
	}
	public void setCodiceAcqua(int c) {
		this.codiceAcqua = c;
	}
	
	public String getData_nascita_rappresentante() {
		return data_nascita_rappresentante;
	}
	public void setData_nascita_rappresentante(String data_nascita_rappresentante) {
		this.data_nascita_rappresentante = data_nascita_rappresentante;
	}
	public String getLuogo_nascita_rappresentante() {
		return luogo_nascita_rappresentante;
	}
	public void setLuogo_nascita_rappresentante(String luogo_nascita_rappresentante) {
		this.luogo_nascita_rappresentante = luogo_nascita_rappresentante;
	}
	public String getCitta_legale_rapp() {
		return citta_legale_rapp;
	}
	public void setCitta_legale_rapp(String citta_legale_rapp) {
		this.citta_legale_rapp = citta_legale_rapp;
	}
	public String getIndirizzo_legale_rapp() {
		return indirizzo_legale_rapp;
	}
	public void setIndirizzo_legale_rapp(String indirizzo_legale_rapp) {
		this.indirizzo_legale_rapp = indirizzo_legale_rapp;
	}
	public String getProvincia_legale_rapp() {
		return provincia_legale_rapp;
	}
	public void setProvincia_legale_rapp(String provincia_legale_rapp) {
		this.provincia_legale_rapp = provincia_legale_rapp;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAnnoReferto() {
		return annoReferto;
	}
	public void setAnnoReferto(String annoReferto) {
		this.annoReferto = annoReferto;
	}
	public String getMeseReferto() {
		return meseReferto;
	}
	public void setMeseReferto(String meseReferto) {
		this.meseReferto = meseReferto;
	}
	public String getGiornoReferto() {
		return giornoReferto;
	}
	public void setGiornoReferto(String giornoReferto) {
		this.giornoReferto = giornoReferto;
	}
	public String getData_fine_controllo() {
		return data_fine_controllo;
	}
	public void setData_fine_controllo(String data_fine_controllo) {
		this.data_fine_controllo = data_fine_controllo;
	}
	public String getGiorno_chiusura() {
		return giorno_chiusura;
	}
	public void setGiorno_chiusura(String giorno_chiusura) {
		this.giorno_chiusura = giorno_chiusura;
	}
	public String getMese_chiusura() {
		return mese_chiusura;
	}
	public void setMese_chiusura(String mese_chiusura) {
		this.mese_chiusura = mese_chiusura;
	}
	public String getAnno_chiusura() {
		return anno_chiusura;
	}
	public void setAnno_chiusura(String anno_chiusura) {
		this.anno_chiusura = anno_chiusura;
	}
	
	String giorno_nascita;
	String mese_nascita;
	String anno_nascita;
	
	public String getGiornoNascita() {
		return giorno_nascita;
	}
	public void setGiornoNascita(String giorno) {
		this.giorno_nascita = giorno;
	}
	public String getTipologia_struttura() {
		return tipologia_struttura;
	}
	public void setTipologia_struttura(String tipo) {
		this.tipologia_struttura = tipo;
	}
	public String getMeseNascita() {
		return mese_nascita;
	}
	public void setMeseNascita(String mese) {
		this.mese_nascita = mese;
	}
	public String getAnnoNascita() {
		return anno_nascita;
	}
	public void setAnnoNascita(String anno) {
		this.anno_nascita = anno;
	}
	

	public String getValutazione_grave() {
		return valutazione_grave;
	}

	public void setValutazione_grave(String valutazione_grave) {
		this.valutazione_grave = valutazione_grave;
	}

	public String getLatitudini() {
		return latitudini;
	}

	public void setLatitudini(String lat) {
		this.latitudini = lat;
	}

	public String getLongitudini() {
		return longitudini;
	}

	public void setLongitudini(String lon) {
		this.longitudini = lon;
	}
	
	public Organization(){}
	
		
	public Organization (ResultSet rs) throws SQLException{
		
		if(rs.next()) {
			
			ragione_sociale = rs.getString("ragione_sociale");
			data_referto = rs.getString("data_referto");
			componente_nucleo = rs.getString("componente_nucleo");
			componente_nucleo_due = rs.getString("componente_nucleo_due");
			componente_nucleo_tre = rs.getString("componente_nucleo_tre");
			componente_nucleo_quattro = rs.getString("componente_nucleo_quattro");
			componente_nucleo_cinque = rs.getString("componente_nucleo_cinque");
			componente_nucleo_sei = rs.getString("componente_nucleo_sei");
			componente_nucleo_sette = rs.getString("componente_nucleo_sette");
			componente_nucleo_otto = rs.getString("componente_nucleo_otto");
			componente_nucleo_nove = rs.getString("componente_nucleo_nove");
			componente_nucleo_dieci = rs.getString("componente_nucleo_dieci");
		
					
			if (componente_nucleo_due != null
					&& !componente_nucleo_due.equals("")) {
				String componenti = componente_nucleo_due + "-";
				if (componente_nucleo_tre!= null
						&& !componente_nucleo_tre.equals("")) {
					componenti += componente_nucleo_tre + "-";
				}
				if (componente_nucleo_quattro!= null
						&& !componente_nucleo_quattro.equals("")) {
					componenti += componente_nucleo_quattro + "-";
				}
				if (componente_nucleo_cinque != null
						&& !componente_nucleo_cinque.equals("")) {
					componenti += componente_nucleo_cinque + "-";
				}
				if (componente_nucleo_sei != null
						&& !componente_nucleo_sei.equals("")) {
					componenti += componente_nucleo_sei + "-";
				}
				if (componente_nucleo_sette != null
						&& !componente_nucleo_sette.equals("")) {
					componenti += componente_nucleo_sette + "-";
				}
				if (componente_nucleo_otto != null
						&& !componente_nucleo_otto.equals("")) {
					componenti += componente_nucleo_otto + "-";
				}
				if (componente_nucleo_nove != null
						&& !componente_nucleo_nove.equals("")) {
					componenti += componente_nucleo_nove + "-";
				}
				if (componente_nucleo_dieci != null
						&& !componente_nucleo_dieci.equals("")) {
					componenti += componente_nucleo_dieci + "";
				}
				componente_nucleo_due = componenti;
			} else {
				componente_nucleo_due = "";
			}
			
			//R.M Aggiunto il riferimento alla colonna tipologia_operatore perche' negli 853 non veniva settata
			//la linea di attivita' sottoposta a controllo corretta (Mod.5). 
			// Nel metodo getFieldsMod5 non entrava mai nell'if(org.getTipologia() == 3){...}
			try {
				rs.findColumn("tipologia_operatore");
				tipologia = rs.getInt("tipologia_operatore");
			} catch (SQLException sqlex) {
				// System.out.println("not found");
			}
			
			try {
				rs.findColumn("cun");
				cun = rs.getString("cun");
			} catch (SQLException sqlex) {
				// System.out.println("not found");
			}
			
			try {
				rs.findColumn("descr_asl");
				this.asl = rs.getString("descr_asl");
			} catch (SQLException sqlex) {
				// System.out.println("not found");
			}
			
			try {
				rs.findColumn("problem");
				this.problem = rs.getString("problem");
			} catch (SQLException sqlex) {
				// System.out.println("not found");
			}
			
			tipologia_att = rs.getString("tipologia");
			approval_number = rs.getString("approval_number");
			n_reg = rs.getString("n_reg");
    		comune = rs.getString("comune");
    		domicilio_digitale = rs.getString("domicilio_digitale");
    		indirizzo = rs.getString("indirizzo");
    		codice_fiscale = rs.getString("codice_fiscale");
    		sede_legale = rs.getString("sede_legale");
    		indirizzo_legale = rs.getString("indirizzo_legale");  		
    		nome_rappresentante = rs.getString("nome_rappresentante") ;
    		if (nome_rappresentante != null && ! "".equals(nome_rappresentante))
    			legale_rapp = nome_rappresentante ;
    		cognome_rappresentante = rs.getString("cognome_rappresentante") ;
    		if (cognome_rappresentante != null && ! "".equals(cognome_rappresentante))
    			legale_rapp += " "+ cognome_rappresentante ;
    		
    		data_nascita_rappresentante = rs.getString("data_nascita_rappresentante") ;
    		luogo_nascita_rappresentante = rs.getString("luogo_nascita_rappresentante") ;
    		citta_legale_rapp = rs.getString("citta_legale_rapp") ;
    		indirizzo_legale_rapp = rs.getString("indirizzo_legale_rapp") ;
    		provincia_legale_rapp = rs.getString("provincia_legale_rapp") ;
    		data_fine_controllo = rs.getString("data_fine_controllo") ;
    		
    		try {
				rs.findColumn("civico_indirizzo_legale_rapp");
				this.civico_indirizzo_legale_rapp = rs.getString("civico_indirizzo_legale_rapp");
			} catch (SQLException sqlex) {
				// System.out.println("not found");
			}
    		try {
				rs.findColumn("civico_indirizzo_legale");
				this.civico_indirizzo_legale = rs.getString("civico_indirizzo_legale");
			} catch (SQLException sqlex) {
				// System.out.println("not found");
			}
    		try {
				rs.findColumn("civico_indirizzo");
				this.civico_indirizzo = rs.getString("civico_indirizzo");
			} catch (SQLException sqlex) {
				// System.out.println("not found");
			}
    		try {
				rs.findColumn("domicilio_digitale_legale_rapp");
				this.domicilio_digitale_legale_rapp = rs.getString("domicilio_digitale_legale_rapp");
			} catch (SQLException sqlex) {
				// System.out.println("not found");
			}
    		
    		
	
		}
		
	}
	
	
	public Organization (Connection db , int idControllo) 
	{
		try
		{
			PreparedStatement pst = db.prepareStatement("select o.account_number,o.tipologia, coalesce(o.name,op.ragione_sociale, ai.ragione_sociale,ricop.ragione_sociale, sinop.ragione_sociale, anim.ragione_sociale) as name, t.org_id , t.id_stabilimento, t.alt_id, t.id_apiario, o.site_id"
					+ " from ticket t left join opu_stabilimento st on (st.id=t.id_stabilimento or st.alt_id = t.alt_id) left join apicoltura_apiari aa on aa.id =t.id_apiario"
					+ " left join opu_operatore op on op.id=st.id_operatore left join apicoltura_imprese ai on ai.id = aa.id_operatore left join organization o on (o.org_id = t.org_id)"
					+ " left join suap_ric_scia_stabilimento ricst on ricst.alt_id=t.alt_id left join suap_ric_scia_operatore ricop on ricop.id=ricst.id_operatore " 
					+ " left join sintesis_stabilimento sinst on sinst.alt_id=t.alt_id left join sintesis_operatore sinop on sinop.id=sinst.id_operatore "
					+ " left join anagrafica.stabilimenti anst on anst.alt_id = t.alt_id  left join anagrafica.rel_imprese_stabilimenti anrelis on anrelis.id_stabilimento = anst.id and anrelis.data_scadenza is null and anrelis.data_cancellazione is null left join anagrafica.imprese anim on anim.id = anrelis.id_impresa "
					+ " where t.ticketid = ? and t.tipologia = 3");
			pst.setInt(1, idControllo);
			ResultSet rs = pst.executeQuery() ;
			if (rs.next())
			{
				orgId = rs.getInt("org_id");
				idStabilimento = rs.getInt("id_stabilimento");
				altId = rs.getInt("alt_id");
				idApiario = rs.getInt("id_apiario");
				siteId = rs.getInt("site_id");
				name = rs.getString("name");
				tipologia = rs.getInt("tipologia");
				accountNumber = rs.getString("account_number");
				if (tipologia==14)//acque di rete
					name = rs.getString("account_number");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void queryRecord (Connection db , int orgId,int tipologiaOp) 
	{
		try
		{PreparedStatement pst = db.prepareStatement("select distinct o.tipologia_operatore as tipologia, o.ragione_sociale,  o.asl_rif ,o.riferimento_id as org_id "
				+ "from  ricerche_anagrafiche_old_materializzata o where o.riferimento_id =? and o.tipologia_operatore = ? ");
			pst.setInt(1, orgId);
			pst.setInt(2, tipologiaOp);

			ResultSet rs = pst.executeQuery() ;
			if (rs.next())
			{
				this.orgId = rs.getInt("org_id");
				siteId = rs.getInt("asl_rif");
				name = rs.getString("ragione_sociale");
				tipologia = rs.getInt("tipologia");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void queryRecord (Connection db , int orgId) 
	{
		try
		{
//			PreparedStatement pst = db.prepareStatement("select o.tipologia, o.ragione_sociale, "
//					+ " o.asl_rif ,o.org_id from  view_globale_trashed_no_trashed o where org_id = ? ");
//			pst.setInt(1, orgId);

			PreparedStatement pst = db.prepareStatement("select o.tipologia_operatore as tipologia, o.ragione_sociale,  o.asl_rif ,o.riferimento_id as org_id "
					+ "from  ricerche_anagrafiche_old_materializzata o where o.riferimento_id =? and o.riferimento_id_nome_col ='org_id'");
	pst.setInt(1, orgId);
			

			ResultSet rs = pst.executeQuery() ;
			if (rs.next())
			{
				this.orgId = rs.getInt("org_id");
				siteId = rs.getInt("asl_rif");
				name = rs.getString("ragione_sociale");
				tipologia = rs.getInt("tipologia");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings("deprecation")
	public Organization (ResultSet rs, String allevamento) throws SQLException
	{
		if(allevamento.equals("allevamento")){
			while (rs.next())
			{
				try {codice_tipo_allevamento = rs.getString("codice_tipo_allevamento");} catch (Exception e) {}
				try {data_referto = rs.getString("data_controllo");} catch (Exception e) {}
				try {n_reg = rs.getString("codice_azienda");} catch (Exception e) {}
				try {name = rs.getString("denominazione");} catch (Exception e) {}
				try {comune = rs.getString("comune_azienda");} catch (Exception e) {}
				try {indirizzo = rs.getString("indirizzo_azienda");} catch (Exception e) {}
				try {indirizzo_legale = rs.getString("indirizzo_legale");} catch (Exception e) {}
	    		try {prov = rs.getString("prov_sede_azienda");} catch (Exception e) {}
	    		try {cap = rs.getString("cap_azienda");} catch (Exception e) {}
	    		try {asl = rs.getString("asl");} catch (Exception e) {}
	    		try {tipologia_att = rs.getString("orientamento_produttivo");} catch (Exception e) {}
	    		try {tipologia_struttura = rs.getString("tipologia_struttura");} catch (Exception e) {}
	    		try {specie_allev = rs.getString("specie_allevata");} catch (Exception e) {}
	    		try {codice_specie = rs.getString("codice_specie");} catch (Exception e) {}
	    		try {data_inizio_attivita =  rs.getString("data_inizio_attivita");} catch (Exception e) {}
	    		try {codice_fiscale = rs.getString("partita_iva_codice_fiscale");} catch (Exception e) {}
	    		try {prop = rs.getString("proprietario");} catch (Exception e) {}
	    		try {cf_prop = rs.getString("cf_proprietario");} catch (Exception e) {}
	    		try {det = rs.getString("detentore");} catch (Exception e) {}
	    		try {cf_det = rs.getString("cf_detentore");		} catch (Exception e) {}	
	    		try {orgId = rs.getInt("org_id");} catch (Exception e) {}
	    		try {idStabilimento = rs.getInt("id_stabilimento");} catch(SQLException e){}
	    		try {idApiario = rs.getInt("id_apiario");} catch(SQLException e){}
	    		try {altId = rs.getInt("alt_id");} catch(SQLException e){}
	    		try {idStabilimento = rs.getInt("id_stabilimento");} catch(SQLException e){}
	    		try {idStabilimento = rs.getInt("id_stabilimento");} catch(SQLException e){}
	    		try {data_chiusura_controllo = rs.getString("data_chiusura_controllo");} catch (Exception e) {}
	    		try {idControllo = rs.getInt("id_controllo_ufficiale");} catch (Exception e) {}
	    		try {comune_operativo = rs.getString("comune_operativo");} catch (Exception e) {}
	    		try {indirizzo_operativo = rs.getString("indirizzo_operativo");} catch (Exception e) {}
	    		try {latitudine = rs.getString("latitudine");} catch (Exception e) {}
	    		try {longitudine = rs.getString("longitudine");} catch (Exception e) {}
	    		try {nominativo_proprietario = rs.getString("nome_proprietario");} catch (Exception e) {}
	    		try {nominativo_detentore = rs.getString("nome_detentore");} catch (Exception e) {}

	    		
			}

		}
		else {
			while (rs.next())
			{
				ragione_sociale = rs.getString("ragione_sociale");
				if (approval_number != null && !approval_number.equals("")) {
					approval_number = rs.getString("approval_number");
				} else{
					approval_number = "";
				}
				
				n_reg = rs.getString("n_reg");
	    		comune = rs.getString("comune");
	    		indirizzo = rs.getString("indirizzo");
	    		codice_fiscale = rs.getString("codice_fiscale");
	    		sede_legale = rs.getString("sede_legale");
	    		indirizzo_legale = rs.getString("indirizzo_legale");  	
	    		try {
					rs.findColumn("descr_asl");
					this.asl = rs.getString("descr_asl");
				} catch (SQLException sqlex) {
					// System.out.println("not found");
				}
			}
		}
		
	}
	
	
	public Organization(Connection db, int riferimento_id, String tipo) {
		// TODO Auto-generated constructor stub
		try
		{
			PreparedStatement pst = db.prepareStatement("select * from anagrafica.anagrafica_cerca_anagrafica_per_cu(?)");
			pst.setInt(1, riferimento_id);
			ResultSet rs = pst.executeQuery() ;
			if (rs.next()) 
			{
				altId = rs.getInt("riferimento_id");
				siteId = rs.getInt("id_asl");
				name = rs.getString("ragione_sociale");
				if(tipo.equalsIgnoreCase("")) //da continuare per nuova anagrafica. Bisogna cambiare il parametro nella chiamata per non fargli settare 13
					tipologia = rs.getInt("tipologia");
				else 
				{
					tipologia = 13;
					action="Operatoriprivati";
					container = "operatoriprivati";
				}
				accountNumber = rs.getString("n_reg");
				if (tipologia==14)//acque di rete
					name = rs.getString("account_number");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}

	public boolean isChecked(int code) {
		
		boolean checked = false;
		int key = this.getCodiceAcqua();
		
		if(key == code){
			checked = true;
		}						
		
		return checked;	
		
			
	}
	
	public void buildListSpecieMolluschi(Connection db) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement(" select m.nome as specie_molluschi, * from tipo_molluschi t left join organization o on t.id_molluschi = o.org_id left join matrici m on m.matrice_id = t.id_matrice where t.id_molluschi = ?");
		pst.setInt(1, orgId);
		ResultSet rs = pst.executeQuery() ;
		while (rs.next())
		{
			specieMolluschi += rs.getString("specie_molluschi")+" - ";
		}
		
		this.setSpecieMolluschi(specieMolluschi);
			
	}
	
	public int listConcessionari(Connection db) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement(" SELECT distinct o.*, oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode,oa.county "+ 
		" as o_county,oa.addrline1,oa.latitude,oa.longitude,c_associati.* " +  
		" FROM organization o " + 
		" LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " + 
		" left join concessionari_associati_zone_in_concessione c_associati on (c_associati.id_concessionario = o.org_id) where o.tipologia = 211 "+   
		" and c_associati.id_zona = ? " +  
		" and c_associati.enabled = true  and c_associati.trashed_date is null order by data_scadenza ");
		
		pst.setInt(1, orgId);
		ResultSet rs = pst.executeQuery() ;
		ArrayList<String> lista = new ArrayList<String>();
		while (rs.next())
		{
			
			lista.add(rs.getString("latitude"));
			lista.add(rs.getString("longitude"));
			lista.add(rs.getString("name"));
		}
		
		this.setConcessionari(lista);
		return lista.size();		
		
	}
	public String getMeseFromData(String data_referto){
		String mese = data_referto.substring(5,7);
		
		switch (Integer.parseInt(mese)) {
			case 01 : mese = "Gennaio"    ;  break;
			case 02 : mese = "Febbraio"   ;  break;
			case 03 : mese = "Marzo"      ;  break;
			case 04 : mese = "Aprile"     ;  break;
			case 05 : mese = "Maggio"     ;  break;
			case 06 : mese = "Giugno"     ;  break;
			case 07 : mese = "Luglio"     ;  break;
			//case 08 : mese = "Agosto"     ;  break;
			//case 09 : mese = "Settembre"  ;  break;
			case 10 : mese = "Ottobre"    ;  break;
			case 11 : mese = "Novembre"   ;  break;
			case 12 : mese = "Dicembre"   ;  break;
		}
		if (mese.equals("08")){
			mese = "Agosto"; 
		}
		if (mese.equals("09")){
			mese = "Settembre";
		}
		
		return mese;
	}
	
	public void getOnlyRagioneSociale (Connection db , int orgId) 
	{
		try
		{
			PreparedStatement pst = db.prepareStatement("select account_number, name from organization where org_id = ?");
			pst.setInt(1, orgId);
			ResultSet rs = pst.executeQuery() ;
			if (rs.next())
			{
				this.name = rs.getString("name");
				this.accountNumber =  rs.getString("account_number");
				this.orgId=orgId;
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	//aggiunti per compatibilita

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public int getIdAsl() {
		return siteId;
	}

	public String getCodice_tipo_allevamento() {
		return codice_tipo_allevamento;
	}

	public void setCodice_tipo_allevamento(String codice_tipo_allevamento) {
		this.codice_tipo_allevamento = codice_tipo_allevamento;
	}

	public String getCivico_indirizzo_legale_rapp() {
		return civico_indirizzo_legale_rapp;
	}

	public void setCivico_indirizzo_legale_rapp(String civico_indirizzo_legale_rapp) {
		this.civico_indirizzo_legale_rapp = civico_indirizzo_legale_rapp;
	}

	public String getCivico_indirizzo() {
		return civico_indirizzo;
	}

	public void setCivico_indirizzo(String civico_indirizzo) {
		this.civico_indirizzo = civico_indirizzo;
	}

	public String getCivico_indirizzo_legale() {
		return civico_indirizzo_legale;
	}

	public void setCivico_indirizzo_legale(String civico_indirizzo_legale) {
		this.civico_indirizzo_legale = civico_indirizzo_legale;
	}

	public String getDomicilio_digitale_legale_rapp() { 
		return domicilio_digitale_legale_rapp;
	}

	public void setDomicilio_digitale_legale_rapp(String domicilio_digitale_legale_rapp) {
		this.domicilio_digitale_legale_rapp = domicilio_digitale_legale_rapp;
	}

	public String getNominativo_proprietario() {
		return nominativo_proprietario;
	}

	public void setNominativo_proprietario(String nominativo_proprietario) {
		this.nominativo_proprietario = nominativo_proprietario;
	}

	public String getNominativo_detentore() {
		return nominativo_detentore;
	}

	public void setNominativo_detentore(String nominativo_detentore) {
		this.nominativo_detentore = nominativo_detentore;
	}
}
