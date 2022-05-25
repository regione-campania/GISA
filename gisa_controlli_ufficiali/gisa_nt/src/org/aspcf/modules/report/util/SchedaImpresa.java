/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.report.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.darkhorseventures.framework.beans.GenericBean;

public class SchedaImpresa extends GenericBean {

	private String ragioneSociale;
	private String numeroRegistrazione;
	private String partitaIva;
	private String operatore;
	private String asl;
	private String codiceFiscale;
	private String atecoPrincCodice;
	private String atecoPrincDescrizione;
	private String targa;
	private String tipoAttivita;
	private String carattere;
	private Timestamp dataPresentazioneDIA;
	private int categoriaRischio;
	private Timestamp prossimoControllo;
	private String statoImpresa;
	private Timestamp dataInizioAttivita;
	private String codiceFiscaleRappresentante;
	private String nomeRappresentante;
	private String cognomeRappresentante;
	private String comuneNascitaRappresentante;
	private Timestamp dataNascitaRappresentante;
	private String mailRappresentante;
	private String telefonoRappresentante;
	private String faxRappresentante;
	private String sedeLegale;
	private String sedeOperativa;
	private String locale1;
	private String locale2;
	private String locale3;
	private Timestamp dataCompletamentoDIA;
	private String esito;
	private String comuneSindaco;
	private String denominazione;
	private String nRegistrazione;
	private String telefono;
	private String  email;
	private Timestamp  dataPresentazione;
	private String viaSede;
	private String comuneSede;
	private String viaSedeOp;
	private String comuneSedeOp;
	private String viaLocale1;
	private String comuneLocale1;
	private String viaLocale2;
	private String comuneLocale2;
	private String viaLocale3;
	private String comuneLocale3;
	private String tipoStruttura;
	private String localeCollegato1;
	private String localeCollegato2;
	private String localeCollegato3;
	private String indirizzoMobile;
	private String capMobile;
	private String cittaMobile;
	private String provinciaMobile;
	private String indViaLocale1;
	private String indViaLocale2;
	private String indViaLocale3;
	private String domicilioDigitale;
	private String nomeImbarcazione;
	private String codiceInterno;
	private String tipoPesca;
	private String sistemaPesca;
	private String flagSelezione;
	private String note;
	private String autorizzazione;
	private String fuoriRegione;
	private String decreto;
	private String zonaDiProduzione;
	private String classificazione;
	private Timestamp  dataClassificazione;
	private Timestamp  dataFineClassificazione;
	private Timestamp data2;
	private Timestamp data1;
	
	public String getIndViaLocale1() {
		return indViaLocale1;
	}
	public void setIndViaLocale1(String indViaLocale1) {
		this.indViaLocale1 = indViaLocale1;
	}
	public String getIndViaLocale2() {
		return indViaLocale2;
	}
	public void setIndViaLocale2(String indViaLocale2) {
		this.indViaLocale2 = indViaLocale2;
	}
	public String getIndViaLocale3() {
		return indViaLocale3;
	}
	public void setIndViaLocale3(String indViaLocale3) {
		this.indViaLocale3 = indViaLocale3;
	}
	public String getTipoStruttura() {
		return tipoStruttura;
	}
	public void setTipoStruttura(String tipoStruttura) {
		this.tipoStruttura = tipoStruttura;
	}
	public String getLocaleCollegato1() {
		return localeCollegato1;
	}
	public void setLocaleCollegato1(String localeCollegato1) {
		this.localeCollegato1 = localeCollegato1;
	}
	public String getLocaleCollegato2() {
		return localeCollegato2;
	}
	public void setLocaleCollegato2(String localeCollegato2) {
		this.localeCollegato2 = localeCollegato2;
	}
	public String getLocaleCollegato3() {
		return localeCollegato3;
	}
	public void setLocaleCollegato3(String localeCollegato3) {
		this.localeCollegato3 = localeCollegato3;
	}
	public String getIndirizzoMobile() {
		return indirizzoMobile;
	}
	public void setIndirizzoMobile(String indirizzoMobile) {
		this.indirizzoMobile = indirizzoMobile;
	}
	public String getCapMobile() {
		return capMobile;
	}
	public void setCapMobile(String capMobile) {
		this.capMobile = capMobile;
	}
	public String getCittaMobile() {
		return cittaMobile;
	}
	public void setCittaMobile(String cittaMobile) {
		this.cittaMobile = cittaMobile;
	}
	public String getProvinciaMobile() {
		return provinciaMobile;
	}
	public void setProvinciaMobile(String provinciaMobile) {
		this.provinciaMobile = provinciaMobile;
	}
	public String getnRegistrazione() {
		return nRegistrazione;
	}
	public void setnRegistrazione(String nRegistrazione) {
		this.nRegistrazione = nRegistrazione;
	}
	public Timestamp getDataPresentazione() {
		return dataPresentazione;
	}
	public void setDataPresentazione(Timestamp dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}
	public String getViaSede() {
		return viaSede;
	}
	public void setViaSede(String viaSede) {
		this.viaSede = viaSede;
	}
	public String getComuneSede() {
		return comuneSede;
	}
	public void setComuneSede(String comuneSede) {
		this.comuneSede = comuneSede;
	}
	public String getViaSedeOp() {
		return viaSedeOp;
	}
	public void setViaSedeOp(String viaSedeOp) {
		this.viaSedeOp = viaSedeOp;
	}
	public String getComuneSedeOp() {
		return comuneSedeOp;
	}
	public void setComuneSedeOp(String comuneSedeOp) {
		this.comuneSedeOp = comuneSedeOp;
	}
	public String getViaLocale1() {
		return viaLocale1;
	}
	public void setViaLocale1(String viaLocale1) {
		this.viaLocale1 = viaLocale1;
	}
	public String getComuneLocale1() {
		return comuneLocale1;
	}
	public void setComuneLocale1(String comuneLocale1) {
		this.comuneLocale1 = comuneLocale1;
	}
	public String getViaLocale2() {
		return viaLocale2;
	}
	public void setViaLocale2(String viaLocale2) {
		this.viaLocale2 = viaLocale2;
	}
	public String getComuneLocale2() {
		return comuneLocale2;
	}
	public void setComuneLocale2(String comuneLocale2) {
		this.comuneLocale2 = comuneLocale2;
	}
	public String getViaLocale3() {
		return viaLocale3;
	}
	public void setViaLocale3(String viaLocale3) {
		this.viaLocale3 = viaLocale3;
	}
	public String getComuneLocale3() {
		return comuneLocale3;
	}
	public void setComuneLocale3(String comuneLocale3) {
		this.comuneLocale3 = comuneLocale3;
	}
	public String getComuneSindaco() {
		return comuneSindaco;
	}
	public void setComuneSindaco(String comuneSindaco) {
		this.comuneSindaco = comuneSindaco;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getNRegistrazione() {
		return nRegistrazione;
	}
	public void setNRegistrazione(String n_registrazione) {
		this.nRegistrazione = n_registrazione;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}
	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getAtecoPrincCodice() {
		return atecoPrincCodice;
	}
	public void setAtecoPrincCodice(String atecoPrincCodice) {
		this.atecoPrincCodice = atecoPrincCodice;
	}
	public String getAtecoPrincDescrizione() {
		return atecoPrincDescrizione;
	}
	public void setAtecoPrincDescrizione(String atecoPrincDescrizione) {
		this.atecoPrincDescrizione = atecoPrincDescrizione;
	}
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	public String getTipoAttivita() {
		return tipoAttivita;
	}
	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}
	public String getCarattere() {
		return carattere;
	}
	public void setCarattere(String carattere) {
		this.carattere = carattere;
	}
	public Timestamp getDataPresentazioneDIA() {
		return dataPresentazioneDIA;
	}
	public void setDataPresentazioneDIA(Timestamp dataPresentazioneDIA) {
		this.dataPresentazioneDIA = dataPresentazioneDIA;
	}
	public int getCategoriaRischio() {
		if (categoriaRischio<0)
			return 3; //VALORE DI DEFAULT
		return categoriaRischio;
	}
	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}
	public Timestamp getProssimoControllo() {
		return prossimoControllo;
	}
	public void setProssimoControllo(Timestamp prossimoControllo) {
		this.prossimoControllo = prossimoControllo;
	}
	public String getStatoImpresa() {
		return statoImpresa;
	}
	public void setStatoImpresa(String statoImpresa) {
		this.statoImpresa = statoImpresa;
	}
	public Timestamp getDataInizioAttivita() {
		return dataInizioAttivita;
	}
	public void setDataInizioAttivita(Timestamp dataInizioAttivita) {
		this.dataInizioAttivita = dataInizioAttivita;
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
	public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}
	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}
	public String getComuneNascitaRappresentante() {
		return comuneNascitaRappresentante;
	}
	public void setComuneNascitaRappresentante(String comuneNascitaRappresentante) {
		this.comuneNascitaRappresentante = comuneNascitaRappresentante;
	}
	public Timestamp getDataNascitaRappresentante() {
		return dataNascitaRappresentante;
	}
	public void setDataNascitaRappresentante(Timestamp dataNascitaRappresentante) {
		this.dataNascitaRappresentante = dataNascitaRappresentante;
	}
	public String getMailRappresentante() {
		return mailRappresentante;
	}
	public void setMailRappresentante(String mailRappresentante) {
		this.mailRappresentante = mailRappresentante;
	}
	public String getTelefonoRappresentante() {
		return telefonoRappresentante;
	}
	public void setTelefonoRappresentante(String telefonoRappresentante) {
		this.telefonoRappresentante = telefonoRappresentante;
	}
	public String getFaxRappresentante() {
		return faxRappresentante;
	}
	public void setFaxRappresentante(String faxRappresentante) {
		this.faxRappresentante = faxRappresentante;
	}
	public String getSedeLegale() {
		return sedeLegale;
	}
	public void setSedeLegale(String sedeLegale) {
		this.sedeLegale = sedeLegale;
	}
	public String getSedeOperativa() {
		return sedeOperativa;
	}
	public void setSedeOperativa(String sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}
	public String getLocale1() {
		return locale1;
	}
	public void setLocale1(String locale1) {
		this.locale1 = locale1;
	}
	public String getLocale2() {
		return locale2;
	}
	public void setLocale2(String locale2) {
		this.locale2 = locale2;
	}
	public String getLocale3() {
		return locale3;
	}
	public void setLocale3(String locale3) {
		this.locale3 = locale3;
	}
	public Timestamp getDataCompletamentoDIA() {
		return dataCompletamentoDIA;
	}
	public void setDataCompletamentoDIA(Timestamp dataCompletamentoDIA) {
		this.dataCompletamentoDIA = dataCompletamentoDIA;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public SchedaImpresa()  {
			
	}
	
	public SchedaImpresa(ResultSet rs) throws SQLException {
		this.buildRecord(rs);
		
	}
	
	void buildRecord(ResultSet rs) throws SQLException {

		try {
			rs.findColumn("ragione_sociale");
			this.ragioneSociale = rs.getString("ragione_sociale");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		
		try {
			rs.findColumn("numero_registrazione");
			this.numeroRegistrazione = rs.getString("numero_registrazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("partita_iva");
			this.partitaIva = rs.getString("partita_iva");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("operatore");
			this.operatore = rs.getString("operatore");
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
			rs.findColumn("codice_fiscale");
			this.codiceFiscale = rs.getString("codice_fiscale");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
	
		try {
			rs.findColumn("cod_att_princ");
			this.atecoPrincCodice = rs.getString("cod_att_princ");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("cod_attivita_principale");
			this.atecoPrincCodice = rs.getString("cod_attivita_principale");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("descrizione_cod_att_princ");
			this.atecoPrincDescrizione = rs.getString("descrizione_cod_att_princ");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
	
		try {
			rs.findColumn("targa");
			this.targa = rs.getString("targa");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("tipo_attivita");
			this.tipoAttivita = rs.getString("tipo_attivita");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("carattere");
			this.carattere = rs.getString("carattere");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("data_dia");
			this.dataPresentazioneDIA = rs.getTimestamp("data_dia");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("data_completamento_dia");
			this.dataCompletamentoDIA = rs.getTimestamp("data_completamento_dia");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("esito");
			this.esito = rs.getString("esito");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("data_inizio");
			this.dataInizioAttivita = rs.getTimestamp("data_inizio");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("cat_rischio");
			this.categoriaRischio = rs.getInt("cat_rischio");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
			
		try {
			rs.findColumn("prossimo_controllo");
			this.prossimoControllo = rs.getTimestamp("prossimo_controllo");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
			
		try {
			rs.findColumn("codice_fiscale_titolare");
			this.codiceFiscaleRappresentante = rs.getString("codice_fiscale_titolare");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("nome_titolare");
			this.nomeRappresentante = rs.getString("nome_titolare");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("nome_rappresentante");
			this.nomeRappresentante = rs.getString("nome_rappresentante");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("cognome_titolare");
			this.cognomeRappresentante = rs.getString("cognome_titolare");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("data_nascita_titolare");
			this.dataNascitaRappresentante = rs.getTimestamp("data_nascita_titolare");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("luogo_nascita_titolare");
			this.comuneNascitaRappresentante = rs.getString("luogo_nascita_titolare");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("telefono_titolare");
			this.telefonoRappresentante = rs.getString("telefono_titolare");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("fax_titolare");
			this.faxRappresentante = rs.getString("fax_titolare");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("email_titolare");
			this.mailRappresentante = rs.getString("email_titolare");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("stato_impresa");
			this.statoImpresa = rs.getString("stato_impresa");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("locale_1");
			this.locale1 = rs.getString("locale_1");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("locale_2");
			this.locale2 = rs.getString("locale_2");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("locale_3");
			this.locale3 = rs.getString("locale_3");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("ind_sede_legale");
			this.sedeLegale = rs.getString("ind_sede_legale");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("ind_sede_operativa");
			this.sedeOperativa = rs.getString("ind_sede_operativa");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
	
		try {
			rs.findColumn("comune_sindaco");
			this.comuneSindaco = rs.getString("comune_sindaco");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("denominazione");
			this.denominazione = rs.getString("denominazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("n_registrazione");
			this.nRegistrazione = rs.getString("n_registrazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("telefono");
			this.telefono = rs.getString("telefono");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("email");
			this.email = rs.getString("email");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("data_presentazione");
			this.dataPresentazione = rs.getTimestamp("data_presentazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("data_2");
			this.setData2(rs.getTimestamp("data_2"));
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("data_1");
			this.setData1(rs.getTimestamp("data_1"));
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("via_sede");
			this.viaSede = rs.getString("via_sede");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("comune_sede");
			this.comuneSede = rs.getString("comune_sede");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("via_sede_op");
			this.viaSedeOp = rs.getString("via_sede_op");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("comune_sede_op");
			this.comuneSedeOp = rs.getString("comune_sede_op");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("via_locale_1");
			this.viaLocale1 = rs.getString("via_locale_1");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("comune_locale_1");
			this.comuneLocale1 = rs.getString("comune_locale_1");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("via_locale_2");
			this.viaLocale2 = rs.getString("via_locale_2");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("comune_locale_2");
			this.comuneLocale2 = rs.getString("comune_locale_2");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}try {
			rs.findColumn("via_locale_3");
			this.viaLocale3 = rs.getString("via_locale_3");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("comune_locale_3");
			this.comuneLocale3 = rs.getString("comune_locale_3");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("tipo_struttura");
			this.tipoStruttura = rs.getString("tipo_struttura");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("locale_collegato_1");
			this.localeCollegato1 = rs.getString("locale_collegato_1");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("locale_collegato_2");
			this.localeCollegato2 = rs.getString("locale_collegato_2");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("locale_collegato_3");
			this.localeCollegato3 = rs.getString("locale_collegato_3");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("indirizzomobile");
			this.indirizzoMobile = rs.getString("indirizzomobile");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		
		try {
			rs.findColumn("capmobile");
			this.capMobile = rs.getString("capmobile");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("cittamobile");
			this.cittaMobile = rs.getString("cittamobile");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("provinciamobile");
			this.provinciaMobile = rs.getString("provinciamobile");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("ind_locale_1");
			this.indViaLocale1 = rs.getString("ind_locale_1");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("ind_locale_2");
			this.indViaLocale2 = rs.getString("ind_locale_2");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("ind_locale_3");
			this.indViaLocale3 = rs.getString("ind_locale_3");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		

		try {
			rs.findColumn("domicilio_digitale");
			this.domicilioDigitale = rs.getString("domicilio_digitale");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("nome_imbarcazione");
			this.nomeImbarcazione = rs.getString("nome_imbarcazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("codice_interno");
			this.codiceInterno =  rs.getString("codice_interno");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("tipo_pesca");
			this.tipoPesca = rs.getString("tipo_pesca");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("sistema_pesca");
			this.sistemaPesca = rs.getString("sistema_pesca");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("flag_selezione");
			this.flagSelezione = rs.getString("flag_selezione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("note");
			this.note = rs.getString("note");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("autorizzazione");
			this.autorizzazione = rs.getString("autorizzazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("fuori_regione");
			this.fuoriRegione = rs.getString("fuori_regione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("decreto");
			this.decreto = rs.getString("decreto");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("zona_di_produzione");
			this.zonaDiProduzione = rs.getString("zona_di_produzione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("classificazione");
			this.classificazione = rs.getString("classificazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("data_classificazione");
			this.dataClassificazione = rs.getTimestamp("data_classificazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("data_fine_classificazione");
			this.dataFineClassificazione = rs.getTimestamp("data_fine_classificazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
	}
	public String getOperatore() {
		return operatore;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}
	public String getAsl() {
		return asl;
	}
	public void setAsl(String asl) {
		this.asl = asl;
	}
	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}
	public void setDomicilioDigitale(String domicilioDigitale) {
		this.domicilioDigitale = domicilioDigitale;
	}
	public String getNomeImbarcazione() {
		return nomeImbarcazione;
	}
	public void setNomeImbarcazione(String nomeImbarcazione) {
		this.nomeImbarcazione = nomeImbarcazione;
	}
	public String getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(String codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	public String getTipoPesca() {
		return tipoPesca;
	}
	public void setTipoPesca(String tipoPesca) {
		this.tipoPesca = tipoPesca;
	}
	public String getSistemaPesca() {
		return sistemaPesca;
	}
	public void setSistemaPesca(String sistemaPesca) {
		this.sistemaPesca = sistemaPesca;
	}
	public String getFlagSelezione() {
		return flagSelezione;
	}
	public void setFlagSelezione(String flagSelezione) {
		this.flagSelezione = flagSelezione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAutorizzazione() {
		return autorizzazione;
	}
	public void setAutorizzazione(String autorizzazione) {
		this.autorizzazione = autorizzazione;
	}
	public String getFuoriRegione() {
		return fuoriRegione;
	}
	public void setFuoriRegione(String fuoriRegione) {
		this.fuoriRegione = fuoriRegione;
	}
	public String getDecreto() {
		return decreto;
	}
	public void setDecreto(String decreto) {
		this.decreto = decreto;
	}
	public String getZonaDiProduzione() {
		return zonaDiProduzione;
	}
	public void setZonaDiProduzione(String zonaDiProduzione) {
		this.zonaDiProduzione = zonaDiProduzione;
	}
	public String getClassificazione() {
		return classificazione;
	}
	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}
	public Timestamp getDataClassificazione() {
		return dataClassificazione;
	}
	public void setDataClassificazione(Timestamp dataClassificazione) {
		this.dataClassificazione = dataClassificazione;
	}
	public Timestamp getDataFineClassificazione() {
		return dataFineClassificazione;
	}
	public void setDataFineClassificazione(Timestamp dataFineClassificazione) {
		this.dataFineClassificazione = dataFineClassificazione;
	}
	public Timestamp getData2() {
		return data2;
	}
	public void setData2(Timestamp data2) {
		this.data2 = data2;
	}
	public Timestamp getData1() {
		return data1;
	}
	public void setData1(Timestamp data1) {
		this.data1 = data1;
	}
	
}
