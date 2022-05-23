/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.validazione;

import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;



public class Richiesta {
	
	public final static int TIPO_CREAZIONE = 1;
	public final static int TIPO_MODIFICA = 2;
	public final static int TIPO_ELIMINAZIONE = 3;
	
	public final static int STATO_DA_VALIDARE = 0;
	public final static int STATO_COMPLETATA = 1;
	public final static int STATO_RIFIUTATA = 2;

	private static final long serialVersionUID = 2L;
	
	private int id;
	
	private int idTipologiaUtente;
	private String tipologiaUtente;
	private int idTipoRichiesta;
	private String tipoRichiesta;
	
	private String cognome;
	private String nome;
	private String codiceFiscale;
	private String email;
	private String telefono;
	
	private int idRuoloGisa = -1;
	private String ruoloGisa;

	private int idRuoloBdu = -1;
	private String ruoloBdu;
	
	private int idRuoloVam = -1;
	private String ruoloVam;
	
	private int idRuoloGisaExt = -1;
	private String ruoloGisaExt;
	
	private int idRuoloDigemon = -1;
	private String ruoloDigemon;

	private ArrayList<Integer> idClinicaVam = new ArrayList<Integer>();
	private ArrayList<String> clinicaVam = new ArrayList<String>();
	
	private String identificativoEnte;
	private String pivaNumRegistrazione;
	private String comune;
	private String istatComune;

	private String nominativoReferente;
	private String ruoloReferente;
	private String emailReferente;
	private String telefonoReferente;
	
	private Timestamp dataRichiesta;
	private String codiceGisa;
	private String indirizzo;
	
	private int idGestoreAcque;
	private String gestoreAcque;
	
	private String cap;
	private String pec;
	
	private String numeroRichiesta;
	
	private String provinciaOrdineVet;
	private String numeroOrdineVet;
	private int idAsl;
	private String asl;
	
	private String numeroDecretoPrefettizio;
	private String scadenzaDecretoPrefettizio;
	private String numeroAutorizzazioneRegionaleVet;
	private int idTipologiaTraspDist;
	
	private int stato;
	private Boolean esitoGuc;
	private Boolean esitoGisa;
	private Boolean esitoGisaExt;
	private Boolean esitoBdu;
	private Boolean esitoVam;
	private Boolean esitoDigemon;
	private Timestamp dataEsito;
	private String esito;
	
	private int idGucRuoli;
	private String endpointGucRuoli;
	private String ruoloGucRuoli;
	
	private Boolean inNucleo;
	private Boolean inDpat;
	
	public Richiesta() {
	} 
	
	public Richiesta(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	 
	
	public Richiesta(Connection db, String numeroRichiesta) {
		String sql = "select * from spid.get_lista_richieste(?)"; 
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setString(1, numeroRichiesta);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				buildRecord(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public String processa(Connection db, int userId) {
		String esito = "";
		String sql = "select * from spid.processa_richiesta(?, ?)"; 
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setString(1, numeroRichiesta);
			pst.setInt(2, userId);
			
			salvaTentativoLog(db, pst.toString(), userId);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				esito = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return esito;
	}
	
	public String rifiuta(Connection db, int userId) {
		String esito = "";
		String sql = "select * from spid.rifiuta_richiesta(?, ?)"; 
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setString(1, numeroRichiesta);
			pst.setInt(2, userId);
			
			salvaTentativoLog(db, pst.toString(), userId);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				esito = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return esito;
	}

	private void salvaTentativoLog(Connection db, String sql, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into spid.log_query_generate (enteredby, query, db, output) values (?, ?, ?, ?)");
		pst.setInt(1, userId);
		pst.setString(2, sql);
		pst.setString(3, "guc");
		pst.setString(4, "### TENTATIVO DI CHIAMATA ###");
		pst.execute();
	}

	public String aggiornaFlag(Connection db, Boolean inNucleo, Boolean inDpat) {
		String esito = "";
		String sql = "select * from spid.aggiorna_flag(?, ?, ?)"; 
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setString(1, numeroRichiesta);
			pst.setBoolean(2, inNucleo);
			pst.setBoolean(3, inDpat);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				esito = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return esito;
	}
	
	private void buildRecord(ResultSet rs) throws SQLException {
		
		id = rs.getInt("id");
		
		idTipologiaUtente = rs.getInt("id_tipologia_utente");
		tipologiaUtente = rs.getString("tipologia_utente");

		idTipoRichiesta = rs.getInt("id_tipo_richiesta");
		tipoRichiesta = rs.getString("tipo_richiesta");
		
		cognome = rs.getString("cognome");
		nome = rs.getString("nome");
		codiceFiscale = rs.getString("codice_fiscale");
		email = rs.getString("email");
		telefono = rs.getString("telefono");
		
		idRuoloGisa = rs.getInt("id_ruolo_gisa");
		ruoloGisa = rs.getString("ruolo_gisa");
		
		idRuoloBdu = rs.getInt("id_ruolo_bdu");
		ruoloBdu = rs.getString("ruolo_bdu");
		
		idRuoloVam = rs.getInt("id_ruolo_vam");
		ruoloVam = rs.getString("ruolo_vam");
		
		idRuoloGisaExt = rs.getInt("id_ruolo_gisa_ext");
		ruoloGisaExt = rs.getString("ruolo_gisa_ext");
		
		idRuoloDigemon = rs.getInt("id_ruolo_digemon");
		ruoloDigemon = rs.getString("ruolo_digemon");

		setIdClinicaVam(rs.getArray("id_clinica_vam")); 
		setClinicaVam(rs.getArray("clinica_vam"));
		
		identificativoEnte = rs.getString("identificativo_ente");
		pivaNumRegistrazione = rs.getString("piva_numregistrazione");
		comune = rs.getString("comune");
		istatComune = rs.getString("istat_comune");

		nominativoReferente = rs.getString("nominativo_referente");
		ruoloReferente = rs.getString("ruolo_referente");
		emailReferente = rs.getString("email_referente");
		telefonoReferente = rs.getString("telefono_referente");
		
		dataRichiesta = rs.getTimestamp("data_richiesta");
		codiceGisa = rs.getString("codice_gisa");
		indirizzo = rs.getString("indirizzo");
		
		idGestoreAcque = rs.getInt("id_gestore_acque");
		gestoreAcque = rs.getString("gestore_acque");
		
		cap = rs.getString("cap");
		pec = rs.getString("pec");
		
		provinciaOrdineVet = rs.getString("provincia_ordine_vet");
		numeroOrdineVet = rs.getString("numero_ordine_vet");

		idAsl = rs.getInt("id_asl");
		asl = rs.getString("asl");
				
		numeroRichiesta = rs.getString("numero_richiesta");
		numeroDecretoPrefettizio = rs.getString("numero_decreto_prefettizio");
		scadenzaDecretoPrefettizio = rs.getString("scadenza_decreto_prefettizio");
		numeroAutorizzazioneRegionaleVet = rs.getString("numero_autorizzazione_regionale_vet");
		
		idTipologiaTraspDist = rs.getInt("id_tipologia_trasp_dist");
		
		stato = rs.getInt("stato");
		esitoGuc = rs.getBoolean("esito_guc");
		esitoGisa = rs.getBoolean("esito_gisa");
		esitoGisaExt = rs.getBoolean("esito_gisa_ext");
		esitoBdu = rs.getBoolean("esito_bdu");
		esitoVam = rs.getBoolean("esito_vam");
		esitoDigemon = rs.getBoolean("esito_digemon");	
		esito = rs.getString("esito");	

		dataEsito = rs.getTimestamp("data_esito");	
		
		idGucRuoli = rs.getInt("id_guc_ruoli");	
		endpointGucRuoli = rs.getString("endpoint_guc_ruoli");	
		ruoloGucRuoli = rs.getString("ruolo_guc_ruoli");	
		
		inNucleo = (Boolean) rs.getObject("in_nucleo");	
		inDpat = (Boolean) rs.getObject("in_dpat");	


	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdTipologiaUtente() {
		return idTipologiaUtente;
	}

	public void setIdTipologiaUtente(int idTipologiaUtente) {
		this.idTipologiaUtente = idTipologiaUtente;
	}

	public String getTipologiaUtente() {
		return tipologiaUtente;
	}

	public void setTipologiaUtente(String tipologiaUtente) {
		this.tipologiaUtente = tipologiaUtente;
	}

	public int getIdTipoRichiesta() {
		return idTipoRichiesta;
	}

	public void setIdTipoRichiesta(int idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}

	public String getTipoRichiesta() {
		return tipoRichiesta;
	}

	public void setTipoRichiesta(String tipoRichiesta) {
		this.tipoRichiesta = tipoRichiesta;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getIdRuoloGisa() {
		return idRuoloGisa;
	}

	public void setIdRuoloGisa(int idRuoloGisa) {
		this.idRuoloGisa = idRuoloGisa;
	}

	public String getRuoloGisa() {
		return ruoloGisa;
	}

	public void setRuoloGisa(String ruoloGisa) {
		this.ruoloGisa = ruoloGisa;
	}

	public int getIdRuoloBdu() {
		return idRuoloBdu;
	}

	public void setIdRuoloBdu(int idRuoloBdu) {
		this.idRuoloBdu = idRuoloBdu;
	}

	public String getRuoloBdu() {
		return ruoloBdu;
	}

	public void setRuoloBdu(String ruoloBdu) {
		this.ruoloBdu = ruoloBdu;
	}

	public int getIdRuoloVam() {
		return idRuoloVam;
	}

	public void setIdRuoloVam(int idRuoloVam) {
		this.idRuoloVam = idRuoloVam;
	}

	public String getRuoloVam() {
		return ruoloVam;
	}

	public void setRuoloVam(String ruoloVam) {
		this.ruoloVam = ruoloVam;
	}

	public int getIdRuoloGisaExt() {
		return idRuoloGisaExt;
	}

	public void setIdRuoloGisaExt(int idRuoloGisaExt) {
		this.idRuoloGisaExt = idRuoloGisaExt;
	}

	public String getRuoloGisaExt() {
		return ruoloGisaExt;
	}

	public void setRuoloGisaExt(String ruoloGisaExt) {
		this.ruoloGisaExt = ruoloGisaExt;
	}

	public int getIdRuoloDigemon() {
		return idRuoloDigemon;
	}

	public void setIdRuoloDigemon(int idRuoloDigemon) {
		this.idRuoloDigemon = idRuoloDigemon;
	}

	public String getRuoloDigemon() {
		return ruoloDigemon;
	}

	public void setRuoloDigemon(String ruoloDigemon) {
		this.ruoloDigemon = ruoloDigemon;
	}

	public ArrayList<Integer> getIdClinicaVam() {
		return idClinicaVam;
	}

	public void setIdClinicaVam(ArrayList<Integer> idClinicaVam) {
		this.idClinicaVam = idClinicaVam;
	}
	
	public void setIdClinicaVam(Array idClinicaVam) { 
		int[] arr;
		try {
			arr = (int[]) idClinicaVam.getArray();
			for (int i = 0; i < arr.length; i++)
				this.idClinicaVam.add(arr[i]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	public ArrayList<String> getClinicaVam() { 
		return clinicaVam;
	}

	public void setClinicaVam(ArrayList<String> clinicaVam) {
		this.clinicaVam = clinicaVam;
	}

	public void setClinicaVam(Array clinicaVam) throws SQLException {
		String[] arr;
		try {
			arr = (String[]) clinicaVam.getArray();
			for (int i = 0; i < arr.length; i++)
				this.clinicaVam.add(arr[i]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public String getIdentificativoEnte() {
		return identificativoEnte;
	}

	public void setIdentificativoEnte(String identificativoEnte) {
		this.identificativoEnte = identificativoEnte;
	}

	public String getPivaNumRegistrazione() {
		return pivaNumRegistrazione;
	}

	public void setPivaNumRegistrazione(String pivaNumRegistrazione) {
		this.pivaNumRegistrazione = pivaNumRegistrazione;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getNominativoReferente() {
		return nominativoReferente;
	}

	public void setNominativoReferente(String nominativoReferente) {
		this.nominativoReferente = nominativoReferente;
	}

	public String getRuoloReferente() {
		return ruoloReferente;
	}

	public void setRuoloReferente(String ruoloReferente) {
		this.ruoloReferente = ruoloReferente;
	}

	public String getEmailReferente() {
		return emailReferente;
	}

	public void setEmailReferente(String emailReferente) {
		this.emailReferente = emailReferente;
	}

	public String getTelefonoReferente() {
		return telefonoReferente;
	}

	public void setTelefonoReferente(String telefonoReferente) {
		this.telefonoReferente = telefonoReferente;
	}

	public Timestamp getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Timestamp dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public String getCodiceGisa() {
		return codiceGisa;
	}

	public void setCodiceGisa(String codiceGisa) {
		this.codiceGisa = codiceGisa;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getIdGestoreAcque() {
		return idGestoreAcque;
	}

	public void setIdGestoreAcque(int idGestoreAcque) {
		this.idGestoreAcque = idGestoreAcque;
	}

	public String getGestoreAcque() {
		return gestoreAcque;
	}

	public void setGestoreAcque(String gestoreAcque) {
		this.gestoreAcque = gestoreAcque;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getNumeroRichiesta() {
		return numeroRichiesta;
	}

	public void setNumeroRichiesta(String numeroRichiesta) {
		this.numeroRichiesta = numeroRichiesta;
	}

	public String getProvinciaOrdineVet() {
		return provinciaOrdineVet;
	}

	public void setProvinciaOrdineVet(String provinciaOrdineVet) {
		this.provinciaOrdineVet = provinciaOrdineVet;
	}

	public String getNumeroOrdineVet() {
		return numeroOrdineVet;
	}

	public void setNumeroOrdineVet(String numeroOrdineVet) {
		this.numeroOrdineVet = numeroOrdineVet;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public String getAsl() {
		return asl;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}

	public String getNumeroDecretoPrefettizio() {
		return numeroDecretoPrefettizio;
	}

	public void setNumeroDecretoPrefettizio(String numeroDecretoPrefettizio) {
		this.numeroDecretoPrefettizio = numeroDecretoPrefettizio;
	}

	public String getScadenzaDecretoPrefettizio() {
		return scadenzaDecretoPrefettizio;
	}

	public void setScadenzaDecretoPrefettizio(String scadenzaDecretoPrefettizio) {
		this.scadenzaDecretoPrefettizio = scadenzaDecretoPrefettizio;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public Boolean getEsitoGuc() {
		return esitoGuc;
	}

	public void setEsitoGuc(Boolean esitoGuc) {
		this.esitoGuc = esitoGuc;
	}

	public Boolean getEsitoGisa() {
		return esitoGisa;
	}

	public void setEsitoGisa(Boolean esitoGisa) {
		this.esitoGisa = esitoGisa;
	}

	public Boolean getEsitoGisaExt() {
		return esitoGisaExt;
	}

	public void setEsitoGisaExt(Boolean esitoGisaExt) {
		this.esitoGisaExt = esitoGisaExt;
	}

	public Boolean getEsitoBdu() {
		return esitoBdu;
	}

	public void setEsitoBdu(Boolean esitoBdu) {
		this.esitoBdu = esitoBdu;
	}

	public Boolean getEsitoVam() {
		return esitoVam;
	}

	public void setEsitoVam(Boolean esitoVam) {
		this.esitoVam = esitoVam;
	}

	public Boolean getEsitoDigemon() {
		return esitoDigemon;
	}

	public void setEsitoDigemon(Boolean esitoDigemon) {
		this.esitoDigemon = esitoDigemon;
	}

	public Timestamp getDataEsito() {
		return dataEsito;
	}

	public void setDataEsito(Timestamp dataEsito) {
		this.dataEsito = dataEsito;
	}

	public String getNumeroAutorizzazioneRegionaleVet() {
		return numeroAutorizzazioneRegionaleVet;
	}

	public void setNumeroAutorizzazioneRegionaleVet(String numeroAutorizzazioneRegionaleVet) {
		this.numeroAutorizzazioneRegionaleVet = numeroAutorizzazioneRegionaleVet;
	}

	public int getIdTipologiaTraspDist() {
		return idTipologiaTraspDist;
	}

	public void setIdTipologiaTraspDist(int idTipologiaTraspDist) {
		this.idTipologiaTraspDist = idTipologiaTraspDist;
	}

	public String getIstatComune() {
		return istatComune;
	}

	public void setIstatComune(String istatComune) {
		this.istatComune = istatComune;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public int getIdGucRuoli() {
		return idGucRuoli;
	}

	public void setIdGucRuoli(int idGucRuoli) {
		this.idGucRuoli = idGucRuoli;
	}

	public String getEndpointGucRuoli() {
		return endpointGucRuoli;
	}

	public void setEndpointGucRuoli(String endpointGucRuoli) {
		this.endpointGucRuoli = endpointGucRuoli;
	}

	public String getRuoloGucRuoli() {
		return ruoloGucRuoli;
	}

	public void setRuoloGucRuoli(String ruoloGucRuoli) {
		this.ruoloGucRuoli = ruoloGucRuoli;
	}

	public Boolean getInNucleo() {
		return inNucleo;
	}

	public void setInNucleo(Boolean inNucleo) {
		this.inNucleo = inNucleo;
	}

	public Boolean getInDpat() {
		return inDpat;
	}

	public void setInDpat(Boolean inDpat) {
		this.inDpat = inDpat;
	}

	

	

	



	
	
	
}

