/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.acquedirete.base.InfoPdP;
import org.aspcfs.modules.acquedirete.base.Organization;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.molluschibivalvi.base.CoordinateMolluschiBivalvi;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;

public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket {	

	private static final long serialVersionUID = 748096277979828012L;

	public static final int PIANO_BENESSERE_ANIMALE = 452 ;
	private static Logger log = Logger.getLogger(org.aspcfs.modules.campioni.base.Ticket.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}

	private String txt_desc_non_accettato = "";
	private String motivazione = "";
	private String noteMotivazione = "";
	private boolean mangimi = false;
	private int specieAlimentoZootecnico = -1;
	private int tipologiaAlimentoZootecnico = -1;
	private String sanzione = "";
	private int motivazione_campione;
	private int motivazione_piano_campione ;
	private int idMatrice ;
	private String camminoMatrice ;
	private int analitiId ;
	private String camminoAnaliti ;
	private String numBoxCanile ;
	private ArrayList<CoordinateMolluschiBivalvi> listaCoordinateCampione = new ArrayList<CoordinateMolluschiBivalvi>();

	private String check_circuito_ogm ;
	private boolean check_specie_mangimi = false ;
	private ArrayList<SpecieAnimali> listaSpecieAnimali = new ArrayList<SpecieAnimali>();
	private ArrayList<String> listaProdottiPnaa = new ArrayList<String>();
	HashMap<String,HashMap<String ,String>> listaCampiAggiuntivi = new HashMap<String, HashMap<String ,String>>();

	InfoPdP pdp = null;
	
	private String codiceInternoPiano ;
	private String codiceInternoIspezione ;
	
	private String esitoNoteEsame;
    private Timestamp dataRisultato;
    private boolean esitoCampioneChiuso = false ;
    private boolean informazioniLaboratorioChiuso = false ;
    
   public String getEsitoNoteEsame(){
	   return esitoNoteEsame;
   }
   
   public void setEsitoNoteEsame(String esitoNoteEsame){
	   this.esitoNoteEsame = esitoNoteEsame;
   }
   
   public Timestamp getDataRisultato(){
	   return dataRisultato;
   }
   
   
   public String getCodiceInternoIspezione() {
	return codiceInternoIspezione;
}

public void setCodiceInternoIspezione(String codiceInternoIspezione) {
	this.codiceInternoIspezione = codiceInternoIspezione;
}

public void setDataRisultato(String dataRisultato){
	   this.dataRisultato = DatabaseUtils.parseDateToTimestamp(dataRisultato);
   }
   	
	public String getCodiceInternPiano() {
		return codiceInternoPiano;
	}


	public void setCodiceInternoPiano(String codiceInterno) {
		this.codiceInternoPiano = codiceInterno;
	}


	public HashMap<String, HashMap<String, String>> getListaCampiAggiuntivi() {
		return listaCampiAggiuntivi;
	}


	public void setListaCampiAggiuntivi(
			HashMap<String, HashMap<String, String>> listaCampiAggiuntivi) {
		this.listaCampiAggiuntivi = listaCampiAggiuntivi;
	}


	public InfoPdP getPdp() {
		return pdp;
	}


	public void setPdp(InfoPdP pdp) {
		this.pdp = pdp;
	}


	public ArrayList<String> getListaProdottiPnaa() {
		return listaProdottiPnaa;
	}


	public void setListaProdottiPnaa(ArrayList<String> listaprodotti) {
		// TODO Auto-generated method stub
		this.listaProdottiPnaa = listaprodotti;
	}
	
	
	public String getCheck_circuito_ogm() {
		return check_circuito_ogm;
	}

	public void setCheck_circuito_ogm(String check_circuito_ogm) {
		this.check_circuito_ogm = check_circuito_ogm;
	}

	public boolean isCheck_specie_mangimi() {
		return check_specie_mangimi;
	}

	public void setCheck_specie_mangimi(boolean check_specie_mangimi) {
		this.check_specie_mangimi = check_specie_mangimi;
	}

	public ArrayList<SpecieAnimali> getListaSpecieAnimali() {
		return listaSpecieAnimali;
	}

	public void setListaSpecieAnimali(ArrayList<SpecieAnimali> listaSpecieAnimali) {
		this.listaSpecieAnimali = listaSpecieAnimali;
	}

	public String getNumBoxCanile() {
		return numBoxCanile;
	}

	public void setNumBoxCanile(String numBoxCanile) {
		this.numBoxCanile = numBoxCanile;
	}

	public int getAnalitiId() {
		return analitiId;

	}

	public void setAnalitiId(int analitiId) {
		this.analitiId = analitiId;
	}

	public String getCamminoAnaliti() {
		return camminoAnaliti;
	}

	public void setCamminoAnaliti(String camminoAnaliti) {
		this.camminoAnaliti = camminoAnaliti;
	}

	public String getCamminoMatrice() {
		return camminoMatrice;
	}

	public void setCamminoMatrice(String camminoMatrice) {
		this.camminoMatrice = camminoMatrice;
	}

	public int getIdMatrice() {
		return idMatrice;
	}

	public void setIdMatrice(int idMatrice) {
		this.idMatrice = idMatrice;
	}

	public int getMotivazione_campione() {
		return motivazione_campione;
	}

	public void setMotivazione_campione(int motivazione_campione) {
		this.motivazione_campione = motivazione_campione;
	}

	public int getMotivazione_piano_campione() {
		return motivazione_piano_campione;
	}

	public void setMotivazione_piano_campione(int motivazione_piano_campione) {
		this.motivazione_piano_campione = motivazione_piano_campione;
	}

	public String getSanzione() {
		return sanzione;
	}

	public void setSanzione(String sanzione) {
		this.sanzione = sanzione;
	}

	private boolean soa = false;

	public boolean isSoa() {
		return soa;
	}

	public void setSoa(boolean soa) {
		this.soa = soa;
	}

	public int getTipoAlimento() {
		return tipoAlimento;
	}

	public void setTipoAlimento(int tipoAlimento) {
		this.tipoAlimento = tipoAlimento;
	}

	public boolean isMangimi() {
		return mangimi;
	}

	public void setMangimi(boolean mangimi) {
		this.mangimi = mangimi;
	}

	public int getSpecieAlimentoZootecnico() {
		return specieAlimentoZootecnico;
	}

	public void setSpecieAlimentoZootecnico(int specieAlimentoZootecnico) {
		this.specieAlimentoZootecnico = specieAlimentoZootecnico;
	}

	public int getTipologiaAlimentoZootecnico() {
		return tipologiaAlimentoZootecnico;
	}

	public void setTipologiaAlimentoZootecnico(int tipologiaAlimentoZootecnico) {
		this.tipologiaAlimentoZootecnico = tipologiaAlimentoZootecnico;
	}

	protected int tipoAlimento = -1;

	public String getTxt_desc_non_accettato() {
		return txt_desc_non_accettato;
	}

	public void setTxt_desc_non_accettato(String txtDescNonAccettato) {
		txt_desc_non_accettato = txtDescNonAccettato;
	}

	public void setTipoAlimento(String tipoAlimento) {
		try {
			this.tipoAlimento = Integer.parseInt(tipoAlimento);
		} catch (Exception e) {
			this.tipoAlimento = -1;
		}
	}


	
	public Ticket() {

	}

	protected ArrayList<Analita> tipiCampioni = new ArrayList<Analita>();
	protected HashMap<Integer, String> matrici = new HashMap<Integer, String>();


	public HashMap<Integer, String> getMatrici() {
		return matrici;
	}

	public void setMatrici(HashMap<Integer, String> matrici) {
		this.matrici = matrici;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public String getNoteMotivazione() {
		return noteMotivazione;
	}

	public void setNoteMotivazione(String noteMotivazione) {
		this.noteMotivazione = noteMotivazione;
	}

	protected HashMap<Integer, String> tipiChimiciSelezionati = new HashMap<Integer, String>();

	protected HashMap<Integer, String> tipiAlimentiVegetali = new HashMap<Integer, String>();

	public HashMap<Integer, String> getTipiChimiciSelezionati() {
		return tipiChimiciSelezionati;
	}

	public void setTipiChimiciSelezionati(
			HashMap<Integer, String> tipiChimiciSelezionati) {
		this.tipiChimiciSelezionati = tipiChimiciSelezionati;
	}

	protected int tipSpecie_latte = -1;
	protected int tipSpecie_uova = -1;
	protected String tipoSpecieLatte_descrizione = "";
	protected String tipoSpecieUova_descrizione = "";

	public int getTipSpecie_latte() {
		return tipSpecie_latte;
	}

	private int isvegetaletrasformato = -1;

	public int getIsvegetaletrasformato() {
		return isvegetaletrasformato;
	}

	public void setIsvegetaletrasformato(int isvegetaletrasformato) {
		this.isvegetaletrasformato = isvegetaletrasformato;
	}

	public void setTipSpecie_latte(int tipSpecie_latte) {
		this.tipSpecie_latte = tipSpecie_latte;
	}

	public int getTipSpecie_uova() {
		return tipSpecie_uova;
	}

	public void setTipSpecie_uova(int tipSpecie_uova) {
		this.tipSpecie_uova = tipSpecie_uova;
	}

	public String getTipoSpecieLatte_descrizione() {
		return tipoSpecieLatte_descrizione;
	}

	public void setTipoSpecieLatte_descrizione(
			String tipoSpecieLatte_descrizione) {
		this.tipoSpecieLatte_descrizione = tipoSpecieLatte_descrizione;
	}

	public String getTipoSpecieUova_descrizione() {
		return tipoSpecieUova_descrizione;
	}

	public void setTipoSpecieUova_descrizione(String tipoSpecieUova_descrizione) {
		this.tipoSpecieUova_descrizione = tipoSpecieUova_descrizione;
	}

	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	// protected String dati_extra = "";
	protected String pippo = "";
	protected int tipoCampione = -1;
	protected int esitoCampione = -1;
	protected int destinatarioCampione = -1;
	protected Timestamp dataAccettazione = null;
	protected String dataAccettazioneTimeZone = null;
	private int tipoAcque = -1;

	private String descrizionetipoAcqua = "";

	public void setDescrizioneTipoMatriciCanili(Connection db)
	throws SQLException {
		try {

			String sql = "select description from lookup_matrici_canili where code="
				+ tipoMatriciCanili;

			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				descrizioneTipoMatriciCanili = rs.getString(1);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public String getDescrizionetipoAcqua() {
		return descrizionetipoAcqua;
	}

	public void setDescrizionetipoAcqua(Connection db) throws SQLException {
		try {

			String sql = "select description from lookup_acqua where code="
				+ tipoAcque;

			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				descrizionetipoAcqua = rs.getString(1);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public int getTipoAcque() {
		return tipoAcque;
	}

	public void setTipoAcque(int tipoAcque) {
		this.tipoAcque = tipoAcque;
	}

	private String noteAlimenti = "";
	private String noteAnalisi = "";

	/* aggiunti da d.dauria */
	private boolean alimentiOrigineAnimale = false;
	private boolean alimentiOrigineVegetale = false;
	private boolean alimentiComposti = false;
	private int alimentiOrigineAnimaleNonTrasformati = -1;
	private int alimentiOrigineAnimaleTrasformati = -1;
	private int alimentiOrigineVegetaleValori = -1;
	private int alimentiOrigineAnimaleNonTrasformatiValori = -1;
	private boolean allerta = false;
	private boolean segnalazione = false;
	private boolean nonConformita = false;
	private int conseguenzePositivita = -1;
	private int responsabilitaPositivita = -1;
	private String noteEsito = null;
	private int nucleoIspettivo = -1;
	private int nucleoIspettivoDue = -1;
	private int nucleoIspettivoTre = -1;
	private String componenteNucleo = "";
	private String componenteNucleoDue = "";
	private String componenteNucleoTre = "";
	private String testoAppoggio = "";
	private String testoAlimentoComposto = null;
	private int nucleoIspettivoQuattro = -1;
	private int nucleoIspettivoCinque = -1;
	private int nucleoIspettivoSei = -1;
	private int nucleoIspettivoSette = -1;
	private int nucleoIspettivoOtto = -1;
	private int nucleoIspettivoNove = -1;
	private int nucleoIspettivoDieci = -1;
	private int punteggio = 0;
	private String componenteNucleoQuattro = "";
	private String componenteNucleoCinque = "";
	private String componenteNucleoSei = "";
	private String componenteNucleoSette = "";
	private String componenteNucleoOtto = "";
	private String componenteNucleoNove = "";
	private String componenteNucleoDieci = "";
	private String idControlloUfficiale = null;
	private String identificativo = null;
	private boolean altriAlimenti = false;

	public boolean isAltriAlimenti() {
		return altriAlimenti;
	}

	public void setAltriAlimenti(boolean altriAlimenti) {
		this.altriAlimenti = altriAlimenti;
	}

	private boolean alimentiAcqua = false;
	private boolean alimentiBevande = false;
	private boolean alimentiAdditivi = false;
	private boolean materialiAlimenti = false;

	// fine campi

	public void setNucleoIspettivoQuattro(String temp) {
		this.nucleoIspettivoQuattro = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoCinque(String temp) {
		this.nucleoIspettivoCinque = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoSei(String temp) {
		this.nucleoIspettivoSei = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoSette(String temp) {
		this.nucleoIspettivoSette = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoOtto(String temp) {
		this.nucleoIspettivoOtto = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoNove(String temp) {
		this.nucleoIspettivoNove = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoDieci(String temp) {
		this.nucleoIspettivoDieci = Integer.parseInt(temp);
	}

	public void setNucleoIspettivo(String temp) {
		this.nucleoIspettivo = Integer.parseInt(temp);
	}

	public void setPunteggio(String temp) {
		this.punteggio = Integer.parseInt(temp);
	}

	public void setPunteggio(int temp) {
		this.punteggio = temp;
	}

	public void setNucleoIspettivoDue(String temp) {
		this.nucleoIspettivoDue = Integer.parseInt(temp);
	}

	public void setNucleoIspettivoTre(String temp) {
		this.nucleoIspettivoTre = Integer.parseInt(temp);
	}

	public int getNucleoIspettivo() {
		return nucleoIspettivo;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public void setNucleoIspettivo(int nucleoIspettivo) {
		this.nucleoIspettivo = nucleoIspettivo;
	}

	public int getNucleoIspettivoDue() {
		return nucleoIspettivoDue;
	}

	public void setNucleoIspettivoDue(int nucleoIspettivoDue) {
		this.nucleoIspettivoDue = nucleoIspettivoDue;
	}

	public int getNucleoIspettivoTre() {
		return nucleoIspettivoTre;
	}

	public void setNucleoIspettivoTre(int nucleoIspettivoTre) {
		this.nucleoIspettivoTre = nucleoIspettivoTre;
	}

	public String getComponenteNucleo() {
		return componenteNucleo;
	}

	public void setComponenteNucleo(String componenteNucleo) {
		this.componenteNucleo = componenteNucleo;
	}

	public String getComponenteNucleoDue() {
		return componenteNucleoDue;
	}

	public void setComponenteNucleoDue(String componenteNucleoDue) {
		this.componenteNucleoDue = componenteNucleoDue;
	}

	public String getComponenteNucleoTre() {
		return componenteNucleoTre;
	}

	public void setComponenteNucleoTre(String componenteNucleoTre) {
		this.componenteNucleoTre = componenteNucleoTre;
	}

	public String getNoteEsito() {
		return noteEsito;
	}

	public void setNoteEsito(String noteEsito) {
		this.noteEsito = noteEsito;
	}

	public String getNoteAnalisi() {
		return noteAnalisi;
	}

	public void setNoteAnalisi(String noteAnalisi) {
		this.noteAnalisi = noteAnalisi;
	}

	public void setConseguenzePositivita(String temp) {
		this.conseguenzePositivita = Integer.parseInt(temp);
	}

	public void setNonConformita(String tmp) {
		this.nonConformita = DatabaseUtils.parseBoolean(tmp);
	}

	public void setAllerta(String tmp) {
		this.allerta = DatabaseUtils.parseBoolean(tmp);
	}

	public void setResponsabilitaPositivita(String temp) {
		this.responsabilitaPositivita = Integer.parseInt(temp);
	}

	public boolean getAllerta() {
		return allerta;
	}

	public boolean getNonConformita() {
		return nonConformita;
	}

	public boolean isAllerta() {
		return allerta;
	}

	public void setAllerta(boolean allerta) {
		this.allerta = allerta;
	}

	public boolean isNonConformita() {
		return nonConformita;
	}

	public void setNonConformita(boolean nonConformita) {
		this.nonConformita = nonConformita;
	}

	public int getConseguenzePositivita() {
		return conseguenzePositivita;
	}

	public void setConseguenzePositivita(int conseguenzePositivita) {
		this.conseguenzePositivita = conseguenzePositivita;
	}

	public int getResponsabilitaPositivita() {
		return responsabilitaPositivita;
	}

	public void setResponsabilitaPositivita(int responsabilitaPositivita) {
		this.responsabilitaPositivita = responsabilitaPositivita;
	}

	public int getAlimentiOrigineAnimaleNonTrasformatiValori() {
		return alimentiOrigineAnimaleNonTrasformatiValori;
	}

	public void setAlimentiOrigineAnimaleNonTrasformatiValori(
			int alimentiOrigineAnimaleNonTrasformatiValori) {
		this.alimentiOrigineAnimaleNonTrasformatiValori = alimentiOrigineAnimaleNonTrasformatiValori;
	}

	public void setAlimentiOrigineAnimaleNonTrasformati(String temp) {
		this.alimentiOrigineAnimaleNonTrasformati = Integer.parseInt(temp);
	}

	public void setAlimentiOrigineAnimaleTrasformati(String temp) {
		this.alimentiOrigineAnimaleTrasformati = Integer.parseInt(temp);
	}

	public void setAlimentiOrigineVegetaleValori(String temp) {
		this.alimentiOrigineVegetaleValori = Integer.parseInt(temp);
	}

	public void setAlimentiOrigineAnimaleNonTrasformatiValori(String temp) {
		this.alimentiOrigineAnimaleNonTrasformatiValori = Integer
		.parseInt(temp);
	}

	public void setAlimentiOrigineAnimale(String tmp) {
		this.alimentiOrigineAnimale = DatabaseUtils.parseBoolean(tmp);
	}

	public void setAlimentiComposti(String tmp) {
		this.alimentiComposti = DatabaseUtils.parseBoolean(tmp);
	}

	public void setAlimentiOrigineVegetale(String tmp) {
		this.alimentiOrigineVegetale = DatabaseUtils.parseBoolean(tmp);
	}

	public boolean isAlimentiOrigineAnimale() {
		return alimentiOrigineAnimale;
	}

	public boolean isAlimentiAcqua() {
		return alimentiAcqua;
	}

	public boolean getAlimentiAcqua() {
		return alimentiAcqua;
	}

	public void setAlimentiAcqua(boolean alimentiAcqua) {
		this.alimentiAcqua = alimentiAcqua;
	}

	public boolean isAlimentiBevande() {
		return alimentiBevande;
	}

	public boolean getAlimentiBevande() {
		return alimentiBevande;
	}

	public void setAlimentiBevande(boolean alimentiBevande) {
		this.alimentiBevande = alimentiBevande;
	}

	public boolean isAlimentiAdditivi() {
		return alimentiAdditivi;
	}

	public boolean getAlimentiAdditivi() {
		return alimentiAdditivi;
	}

	public void setAlimentiAdditivi(boolean alimentiAdditivi) {
		this.alimentiAdditivi = alimentiAdditivi;
	}

	public boolean isMaterialiAlimenti() {
		return materialiAlimenti;
	}

	public boolean getMaterialiAlimenti() {
		return materialiAlimenti;
	}

	private boolean dolciumi = false;

	public boolean getDolciumi() {
		return dolciumi;
	}

	public void setDolciumi(boolean flag) {
		dolciumi = flag;

	}

	private boolean gelati = false;

	public boolean getGelati() {
		return gelati;
	}

	public void setGelati(boolean flag) {
		gelati = flag;

	}

	public void setMaterialiAlimenti(boolean materialiAlimenti) {
		this.materialiAlimenti = materialiAlimenti;
	}

	public boolean getAlimentiOrigineAnimale() {
		return alimentiOrigineAnimale;
	}

	public void setAlimentiOrigineAnimale(boolean alimentiOrigineAnimale) {
		this.alimentiOrigineAnimale = alimentiOrigineAnimale;
	}

	public boolean isAlimentiOrigineVegetale() {
		return alimentiOrigineVegetale;
	}

	public boolean getAlimentiOrigineVegetale() {
		return alimentiOrigineVegetale;
	}

	public void setAlimentiOrigineVegetale(boolean alimentiOrigineVegetale) {
		this.alimentiOrigineVegetale = alimentiOrigineVegetale;
	}

	public boolean isAlimentiComposti() {
		return alimentiComposti;
	}

	public boolean getAlimentiComposti() {
		return alimentiComposti;
	}

	public void setAlimentiComposti(boolean alimentiComposti) {
		this.alimentiComposti = alimentiComposti;
	}

	public int getAlimentiOrigineAnimaleNonTrasformati() {
		return alimentiOrigineAnimaleNonTrasformati;
	}

	public void setAlimentiOrigineAnimaleNonTrasformati(
			int alimentiOrigineAnimaleNonTrasformati) {
		this.alimentiOrigineAnimaleNonTrasformati = alimentiOrigineAnimaleNonTrasformati;
	}

	public int getAlimentiOrigineAnimaleTrasformati() {
		return alimentiOrigineAnimaleTrasformati;
	}

	public void setAlimentiOrigineAnimaleTrasformati(
			int alimentiOrigineAnimaleTrasformati) {
		this.alimentiOrigineAnimaleTrasformati = alimentiOrigineAnimaleTrasformati;
	}

	public int getAlimentiOrigineVegetaleValori() {
		return alimentiOrigineVegetaleValori;
	}

	public void setAlimentiOrigineVegetaleValori(
			int alimentiOrigineVegetaleValori) {
		this.alimentiOrigineVegetaleValori = alimentiOrigineVegetaleValori;
	}

	/* fine modifiche apportate da d.dauria */

	public String getPippo() {
		return pippo;
	}

	public void setPippo(String pippo) {
		this.pippo = pippo;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}

	public void setDataAccettazioneTimeZone(String tmp) {
		this.dataAccettazioneTimeZone = tmp;
	}

	public String getDataAccettazioneTimeZone() {
		return dataAccettazioneTimeZone;
	}

	public void setDataAccettazione(java.sql.Timestamp tmp) {
		this.dataAccettazione = tmp;
	}

	public void setDataAccettazione(String tmp) {
		this.dataAccettazione = DatabaseUtils.parseDateToTimestamp(tmp);
	}

	public java.sql.Timestamp getDataAccettazione() {
		return dataAccettazione;
	}

	public int getTipoCampione() {
		return tipoCampione;
	}

	public void setTipoCampione(String tipoCampione) {
		try {
			this.tipoCampione = Integer.parseInt(tipoCampione);
		} catch (Exception e) {
			this.tipoCampione = -1;
		}
	}

	public int getDestinatarioCampione() {
		return destinatarioCampione;
	}

	public void setDestinatarioCampione(String destinatarioCampione) {
		try {
			this.destinatarioCampione = Integer.parseInt(destinatarioCampione);
		} catch (Exception e) {
			this.destinatarioCampione = -1;
		}
	}

	public void setEsitoCampione(String esitoCampione) {
		try {
			this.esitoCampione = Integer.parseInt(esitoCampione);
		} catch (Exception e) {
			this.esitoCampione = -1;
		}
	}

	public void setTipoCampione(int tipoCampione) {
		this.tipoCampione = tipoCampione;
	}

	public void setDestinatarioCampione(int destinatarioCampione) {
		this.destinatarioCampione = destinatarioCampione;
	}

	public int getEsitoCampione() {
		return esitoCampione;
	}

	public void setEsitoCampione(int esitoCampione) {
		this.esitoCampione = esitoCampione;
	}

	public String getIdControlloUfficiale() {
		return idControlloUfficiale;
	}

	public void setIdControlloUfficiale(String idControlloUfficiale) {
		this.idControlloUfficiale = idControlloUfficiale;
	}

	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}

	public Ticket(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	public Ticket(Connection db, int id) throws SQLException {
		queryRecord(db, id);

	}

	public Ticket(Connection db, int id, boolean soa) throws SQLException {
		this.soa = soa;
		queryRecord(db, id);

	}

	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}
		PreparedStatement pst = db
		.prepareStatement("SELECT t.*,"
				+ "o.name AS orgname, "
				+ "o.enabled AS orgenabled, "
				+ "o.site_id AS orgsiteid,o.tipologia as tipologia_operatore, "
				+ "a.serial_number AS serialnumber, "
				+ "a.manufacturer_code AS assetmanufacturercode, "
				+ "a.vendor_code AS assetvendorcode, "
				+ "a.model_version AS modelversion, "
				+ "a.location AS assetlocation, "
				+ "a.onsite_service_model AS assetonsiteservicemodel "
				+ "FROM ticket t " 
				+ "LEFT JOIN organization o ON (t.org_id = o.org_id) "
				+ "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) "
				+ "WHERE t.ticketid = ? AND t.tipologia = 2 ");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
			//this.setDescrizionetipoAcqua(db);
			//this.setDescrizioneTipoMatriciCanili(db);
			this.codiceInternoPiano=ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio", this.motivazione_piano_campione).getCodiceInterno();
			this.codiceInternoIspezione=ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", this.motivazione_campione).getCodiceInterno();

			buildCoordinateMolluschi(db);
			buildListSpecieAnimali_Pnaa_Mangime(db);
			buildListProdottiPnaa(db);
			getTipoCampioneSelezionatoNuovaGestione(db);
			getMatriceCampioneSelezionatoNuovaGestione( db);
			//this.getTipoAlimentiVegetali(db);
			pdp =  InfoPdP.InfoPdPCampione(db,this.getId());
			if (pdp != null)
				pdp.setOrgdDetails(new Organization(db,pdp.getOrg_id_pdp()));
		} 
 
		//Verifica blocco
		super.controlloBloccoCu(db, Integer.parseInt(this.idControlloUfficiale));

		
		rs.close();
		pst.close(); 
		if (this.id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}


	}

	public HashMap<Integer, String> getTipiAlimentiVegetali() {
		return tipiAlimentiVegetali;
	}

	public void setTipiAlimentiVegetali(
			HashMap<Integer, String> tipiAlimentiVegetali) {
		this.tipiAlimentiVegetali = tipiAlimentiVegetali;
	}


	public ArrayList<CoordinateMolluschiBivalvi> getListaCoordinateCampione() {
		return listaCoordinateCampione;
	}

	public void setListaCoordinateCampione(
			ArrayList<CoordinateMolluschiBivalvi> listaCoordinateCampione) {
		this.listaCoordinateCampione = listaCoordinateCampione;
	}
	
	public void insertCoordinateMolluschi(Connection db) throws SQLException
	{

		PreparedStatement pst1 = db.prepareStatement("delete from coordinate_molluschi where id_campione = ?");
		pst1.setInt(1, this.id);
		pst1.execute();

		PreparedStatement pst = db.prepareStatement("insert into coordinate_molluschi (id_campione,identificativo, latitudine,longitudine) values (?,?, ?,?)"); 
		for(CoordinateMolluschiBivalvi c : listaCoordinateCampione)
		{
			pst.setInt(1, this.id);
			pst.setString(2, c.getIdentificativo());
			pst.setDouble(3, c.getLatitude());
			pst.setDouble(4,c.getLongitude());
			pst.execute();
		}

	}
	
	
	public void inserSpecieAnimali_Pnaa_Mangime(Connection db) throws SQLException
	{

		PreparedStatement pst1 = db.prepareStatement("delete from campione_specie_animali_pnaa_mangime where id_campione = ?");
		pst1.setInt(1, this.id);
		pst1.execute();

		PreparedStatement pst = db.prepareStatement("insert into campione_specie_animali_pnaa_mangime (id_campione,id_specie,id_categoria) values (?,?,?)"); 
		for(SpecieAnimali c : listaSpecieAnimali)
		{
			pst.setInt(1, this.id);
			pst.setDouble(2, c.getIdSpecie());
			pst.setDouble(3,c.getIdCategoria());
			pst.execute();
		}

	}
	

	public void insertProdottiPnaa(Connection db) throws SQLException
	{

		PreparedStatement pst1 = db.prepareStatement("delete from campione_prodotti_pnaa where id_campione = ?");
		pst1.setInt(1, this.id);
		pst1.execute();

		PreparedStatement pst = db.prepareStatement("insert into campione_prodotti_pnaa (id_campione,id_prodotto) values (?,?)"); 
		for(String c : listaProdottiPnaa)
		{
			pst.setInt(1, this.id);
			pst.setInt(2, Integer.parseInt(c));
			pst.execute();
		}

	}

	public void buildListSpecieAnimali_Pnaa_Mangime(Connection db) throws SQLException
	{

		/*PreparedStatement pst1 = db.prepareStatement("select specie.description as specie_descrizione ,specie.code as id_specie , " +
				"lcsa.code as categoria_id ,lcsa.description as categoria_descrizione "+
				"from campione_specie_animali_pnaa_mangime csa "+
				"join lookup_categorie_specie_animali lcsa on csa.id_categoria = lcsa.code "+  
				"join lookup_specie_pnaa specie on specie.code = csa.id_specie  "+
		"where id_campione = ?" );*/
		PreparedStatement pst1 = db.prepareStatement(" select specie.description as specie_descrizione ,specie.code as id_specie " +
						" from campione_specie_animali_pnaa_mangime csa " +  
						" join lookup_specie_pnaa specie on specie.code = csa.id_specie " +  
						" where id_campione = ?" );
		
		
		pst1.setInt(1, this.id);
		ResultSet rs = pst1.executeQuery();

		 while (rs.next())
		{
			int idSpecie = rs.getInt("id_specie");
			String descrSpecie = rs.getString("specie_descrizione");
			//int idCategoria = rs.getInt("categoria_id");
			//String descrCategoria = rs.getString("categoria_descrizione");	
			SpecieAnimali specie = new SpecieAnimali();
			//specie.setIdCategoria(idCategoria);
			specie.setIdSpecie(idSpecie);
			//specie.setDescrizioneCategoria(descrCategoria);
			specie.setDescrizioneSpecie(descrSpecie);
			listaSpecieAnimali.add(specie);
				
		}

	}

	public void buildListProdottiPnaa(Connection db) throws SQLException
	{
		PreparedStatement pst1 = db.prepareStatement(" select p.description as prodotto_descrizione ,p.code as id_prodotto " +
						" from campione_prodotti_pnaa cpa " +  
						" join lookup_prodotti_pnaa p on p.code = cpa.id_prodotto " +  
						" where id_campione = ?" );
		
		
		pst1.setInt(1, this.id);
		ResultSet rs = pst1.executeQuery();

		 while (rs.next())
		{
			int idProd = rs.getInt("id_prodotto");
			String descrProdotto = rs.getString("prodotto_descrizione");	
			listaProdottiPnaa.add(descrProdotto);
				
		}

	}



	public void buildCoordinateMolluschi(Connection db) throws SQLException
	{

		PreparedStatement pst = db.prepareStatement("select * from coordinate_molluschi where id_campione = ?");
		pst.setInt(1, this.id);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			CoordinateMolluschiBivalvi c = new CoordinateMolluschiBivalvi ();
			c.setLatitude(rs.getDouble("latitudine"));
			c.setLongitude(rs.getDouble("longitudine"));
			c.setIdentificativo(rs.getString("identificativo"));
			listaCoordinateCampione.add(c);
		}


	}
	public synchronized boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}

			UserBean user = (UserBean)context.getSession().getAttribute("User");
			int livello=1 ;
			if (user.getUserRecord().getGruppo_ruolo()==2)
				livello=2;
//			id = DatabaseUtils.getNextInt( db, "ticket","ticketid",livello);
			
			sql.append("INSERT INTO ticket (contact_id, problem, pri_code, "
					+ "department_code, cat_code, scode, org_id,id_stabilimento,id_apiario,alt_id, link_contract_id, "
					+ "link_asset_id, expectation, product_id, customer_product_id, "
					+ "key_count, status_id, trashed_date, user_group_id, cause_id, "
					+ "resolution_id, defect_id, escalation_level, resolvable, "
					+ "resolvedby, resolvedby_department_code, state_id, site_id,ip_entered,ip_modified, ");
			
				sql.append("ticketid, ");
			
			if (entered != null) {
				sql.append("entered, ");
			}
			if(numBoxCanile!= null && ! "".equalsIgnoreCase(numBoxCanile))
			{
				sql.append("box, ");
			}

			if (motivazione_campione>0)
			{
				sql.append("motivazione_campione, ");
			}
			if (motivazione_piano_campione>0)
			{
				sql.append("motivazione_piano_campione, ");
			}


			sql.append("note_motivazione, ");


			if (sanzione != null && !sanzione.equals("")) {
				sql.append("sanzione_campioni, ");
			}

			if (modified != null) {
				sql.append("modified, ");
			}


			sql.append("tipo_richiesta, custom_data, enteredBy, modifiedBy, "
					+ "tipologia, provvedimenti_prescrittivi, sanzioni_amministrative, sanzioni_penali ");
			if (dataAccettazione != null) {
				sql.append(", data_accettazione ");
			}
			if (dataAccettazioneTimeZone != null) {
				sql.append(",data_accettazione_timezone ");
			}


			sql.append(", allerta");
			sql.append(", segnalazione");
			sql.append(", non_conformita");
			if (conseguenzePositivita > -1) {
				sql.append(", conseguenze_positivita");
			}
			if (responsabilitaPositivita > -1) {
				sql.append(", responsabilita_positivita");
			}
			if (noteEsito != null) {
				sql.append(", note_esito");
			}

			sql.append(", id_controllo_ufficiale");
			if (punteggio != -1) {
				sql.append(", punteggio");
			}


			if (noteAnalisi != "") {
				sql.append(", note_analisi");
			}
			
			if (noteAlimenti != "") {
				sql.append(", note_altro");
			}
			
			if (microchip != null && !microchip.equals("")) {
				sql.append(", microchip");
			}
			
			

			sql.append(",check_specie_mangimi,check_circuito_ogm)");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?, ");
			sql.append("?, ?, ?, ");
			
				sql.append( DatabaseUtils.getNextIntSql("ticket", "ticketid", livello)+",");
			
			if (entered != null) {
				sql.append("?, ");
			}
			if(numBoxCanile!= null && ! "".equalsIgnoreCase(numBoxCanile))
			{
				sql.append("?, ");
			}
			if (motivazione_campione>0)
			{
				sql.append("?, ");
			}
			if (motivazione_piano_campione>0)
			{
				sql.append("?, ");
			}
			sql.append("?, ");

			if (sanzione != null && !sanzione.equals("")) {
				sql.append("?, ");

			}

			if (modified != null) {
				sql.append("?, ");
			}

			sql.append("?, ?, ?, ?, " + "2, ?, ?, ? ");
			if (dataAccettazione != null) {
				sql.append(", ? ");
			}
			if (dataAccettazioneTimeZone != null) {
				sql.append(", ? ");
			}



			sql.append(", ?"); // questo e' per allerta
			sql.append(", ?"); // questo e' per segnalazione
			sql.append(", ?"); // questo e' per nonconformita
			if (conseguenzePositivita > -1) {
				sql.append(", ?");
			}
			if (responsabilitaPositivita > -1) {
				sql.append(", ?");
			}
			if (noteEsito != null) {
				sql.append(", ?");
			}

			String asl = null;
			if (siteId == 201) {
				asl = "AV";
			} else if (siteId == 202) {
				asl = "BN";
			} else if (siteId == 203) {
				asl = "CE";
			} else if (siteId == 204) {
				asl = "NA1";
			} else if (siteId == 205) {
				asl = "NA2Nord";
			} else if (siteId == 206) {
				asl = "NA3Sud";
			} else if (siteId == 207) {
				asl = "SA";
			} else {
				if (siteId == 16) {
					asl = "FuoriRegione";
				}

			}
			sql.append(", ?");
			if (punteggio != -1) {
				sql.append(", ? ");
			}


			if (noteAnalisi != "") {
				sql.append(", ?");
			}
			
			if (noteAlimenti != "") {
				sql.append(", ?");
			}
			
			if (microchip != null && !microchip.equals("")) {
				sql.append(", ?");
			}
			sql.append(",?,?) RETURNING ticketid");
			int i = 0;
			
			
			PreparedStatement pst = db.prepareStatement(sql.toString());
			DatabaseUtils.setInt(pst, ++i, this.getContactId());
			pst.setString(++i, this.getProblem());
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, orgId);
			DatabaseUtils.setInt(pst, ++i, idStabilimento);
			DatabaseUtils.setInt(pst, ++i, idApiario);
			DatabaseUtils.setInt(pst, ++i, altId);
			
			pst.setNull(++i, java.sql.Types.INTEGER);
			DatabaseUtils.setInt(pst, ++i, assetId);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, statusId);
			DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
			pst.setNull(++i, java.sql.Types.INTEGER);
			DatabaseUtils.setInt(pst, ++i, causeId);
			DatabaseUtils.setInt(pst, ++i, resolutionId);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.BOOLEAN);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, this.getStateId());
			DatabaseUtils.setInt(pst, ++i, this.getSiteId());
			pst.setString(++i, super.getIpEntered());
			pst.setString(++i, super.getIpModified());
			
			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}
			if(numBoxCanile!= null && ! "".equalsIgnoreCase(numBoxCanile))
			{
				pst.setString(++i, numBoxCanile);
			}
			if (motivazione_campione>0)
			{
				pst.setInt(++i, motivazione_campione);
			}
			if (motivazione_piano_campione>0)
			{
				pst.setInt(++i, motivazione_piano_campione);
			}

			pst.setString(++i, noteMotivazione);


			if (sanzione != null && !sanzione.equals("")) {
				pst.setString(++i, this.getSanzione());

			}
			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}


			/**
			 * 
			 */
			pst.setString(++i, this.getTipo_richiesta());

			pst.setString(++i, this.getPippo());

			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());

			DatabaseUtils.setInt(pst, ++i, tipoCampione);
			DatabaseUtils.setInt(pst, ++i, esitoCampione);
			DatabaseUtils.setInt(pst, ++i, destinatarioCampione);
			if (dataAccettazione != null) {
				pst.setTimestamp(++i, dataAccettazione);
			}
			if (dataAccettazioneTimeZone != null) {
				pst.setString(++i, dataAccettazioneTimeZone);
			}

			// aggiunto da d.dauria


			pst.setBoolean(++i, allerta);
			pst.setBoolean(++i, segnalazione);
			pst.setBoolean(++i, nonConformita);
			if (conseguenzePositivita > -1)
				pst.setInt(++i, conseguenzePositivita);
			if (responsabilitaPositivita > -1) {
				pst.setInt(++i, responsabilitaPositivita);
			}
			if (noteEsito != null) {
				pst.setString(++i, noteEsito);
			}


			pst.setString(++i, idControlloUfficiale);
			if (punteggio != -1) {
				pst.setInt(++i, punteggio);
			}


			if (noteAnalisi != "") {
				pst.setString(++i, noteAnalisi);
			}
			
			if (noteAlimenti != "") {
				pst.setString(++i, noteAlimenti);
			}
			
			if (microchip != null && !microchip.equals("")) {
				pst.setString(++i, microchip);
			}
			pst.setBoolean(++i, check_specie_mangimi);
			pst.setString(++i, check_circuito_ogm);

			
			
			ResultSet rs = pst.executeQuery();
			if ( rs.next())
				this.id = rs.getInt("ticketid");
			pst.close();


			db.prepareStatement("UPDATE TICKET set identificativo = '"+asl+idControlloUfficiale+"' || trim(to_char( "+id+", '"+DatabaseUtils.getPaddedFromId(id)+"' )) where ticketid ="+this.getId()).execute();

			insertCoordinateMolluschi(db);
			
			
			LookupList pianiList = new LookupList(db,"lookup_piano_monitoraggio");
			
			Iterator<Integer> itKiaviMatrici = matrici.keySet().iterator();
		
			
			if (pianiList.getSelectedValue(motivazione_piano_campione) != null 
					&& pianiList.getSelectedValue(motivazione_piano_campione).startsWith("19 ")					
			)
			{
				inserSpecieAnimali_Pnaa_Mangime(db);
				insertProdottiPnaa(db);
			}
			



			// Update the rest of the fields
			this.update(db);

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

	public void insertAnaliti(Connection db,int id,String path) throws SQLException
	{
		PreparedStatement pst=db.prepareStatement("insert into analiti_campioni (id_campione , analiti_id,cammino,note) values (?,?,?,?)");
		pst.setInt(1, this.id);
		pst.setInt(2, id);
		pst.setString(3, path);
		pst.setString(4, "");
		pst.execute();
	}

	public void insertMatrici(Connection db,int id,String path) throws SQLException
	{
		PreparedStatement pst=db.prepareStatement("insert into matrici_campioni (id_campione , id_matrice,cammino,note) values (?,?,?,?)");
		pst.setInt(1, this.id);
		pst.setInt(2, id);
		pst.setString(3, path);
		pst.setString(4, "");
		pst.execute();
	}

	public String getNoteAlimenti() {
		return noteAlimenti;
	}

	public void setNoteAlimenti(String noteAlimenti) {
		this.noteAlimenti = noteAlimenti;
	}

	public void setDescrizioneSpecieLatte(Connection db) throws SQLException {

		String sql = "select description from lookup_alimenti_origine_animale_non_trasformati_specielatte where code="
			+ tipSpecie_latte;
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			tipoSpecieLatte_descrizione = rs.getString(1);

		}

	}

	public void setDescrizioneSpecieUova(Connection db) throws SQLException {

		String sql = "select description from lookup_alimenti_origine_animale_non_trasformati_specieuova where code="
			+ tipSpecie_uova;
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			tipoSpecieUova_descrizione = rs.getString(1);

		}

	}

	public void setTipoAcqueValue(int value) {
		this.tipoAcque = value;

	}

	protected void buildRecord(ResultSet rs) throws SQLException {
		// ticket table
		this.setId(rs.getInt("ticketid"));
		
		noteAlimenti = rs.getString("note_altro");

		orgId = DatabaseUtils.getInt(rs, "org_id");
		idStabilimento = DatabaseUtils.getInt(rs, "id_stabilimento");
		idApiario = DatabaseUtils.getInt(rs, "id_apiario");
		altId = rs.getInt("alt_id");
		problem = rs.getString("problem");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		motivazione_campione = rs.getInt("motivazione_campione");
		motivazione_piano_campione=rs.getInt("motivazione_piano_campione");
		closed = rs.getTimestamp("closed");
		check_specie_mangimi = rs.getBoolean("check_specie_mangimi");
		check_circuito_ogm = rs.getString("check_circuito_ogm");
		numBoxCanile = rs.getString("box");
		sanzione = rs.getString("sanzione_campioni");
		motivazione = rs.getString("motivazione");
		noteMotivazione = rs.getString("note_motivazione");
		solution = rs.getString("solution");
		microchip = rs.getString("microchip");
		super.tipologia_operatore = rs.getInt("tipologia_operatore");
		 
		   if (idStabilimento>0)
			   tipologia_operatore = Ticket.TIPO_OPU;
		   if (idApiario>0)
			   tipologia_operatore = Ticket.TIPO_API;
		   
		   if (altId>0 && altId >= 60000000 && altId <= 79999999 )
				tipologia_operatore = Ticket.TIPO_OPU_RICHIESTE;
			if (altId>0 && altId >= 100000000 && altId <= 119999999 )
				tipologia_operatore = Ticket.TIPO_SINTESIS;
			
		txt_desc_non_accettato = rs.getString("txt_desc_non_accettato"); // aggiunto
		// da
		// alberto
		location = rs.getString("location");
		location_new = rs.getString("location_new");
		assignedDate = rs.getTimestamp("assigned_date");
		estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		resolutionDate = rs.getTimestamp("resolution_date");
		cause = rs.getString("cause");
		assetId = DatabaseUtils.getInt(rs, "link_asset_id");
		estimatedResolutionDateTimeZone = rs
		.getString("est_resolution_date_timezone");
		assignedDateTimeZone = rs.getString("assigned_date_timezone");
		resolutionDateTimeZone = rs.getString("resolution_date_timezone");
		trashedDate = rs.getTimestamp("trashed_date");
		resolutionId = DatabaseUtils.getInt(rs, "resolution_id");
		stateId = DatabaseUtils.getInt(rs, "state_id");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		tipo_richiesta = rs.getString("tipo_richiesta");
		tipologia = rs.getInt("tipologia");
		tipoCampione = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		esitoCampione = DatabaseUtils.getInt(rs, "sanzioni_amministrative");
		destinatarioCampione = DatabaseUtils.getInt(rs, "sanzioni_penali");
		dataAccettazione = rs.getTimestamp("data_accettazione");
		dataAccettazioneTimeZone = rs.getString("data_accettazione_timezone");
		// organization table
		

		// asset table

		allerta = rs.getBoolean("allerta");
		segnalazione = rs.getBoolean("segnalazione");
		nonConformita = rs.getBoolean("non_conformita");
		conseguenzePositivita = DatabaseUtils.getInt(rs,
		"conseguenze_positivita");
		responsabilitaPositivita = DatabaseUtils.getInt(rs,
		"responsabilita_positivita");
		noteEsito = rs.getString("note_esito");
		idControlloUfficiale = rs.getString("id_controllo_ufficiale");
		identificativo = rs.getString("identificativo");
		punteggio = rs.getInt("punteggio");
		noteAnalisi = rs.getString("note_analisi");
		// Calculations
		esitoNoteEsame = rs.getString("note_esito_esame");
		dataRisultato = rs.getTimestamp("data_risultato");
		esitoCampioneChiuso = rs.getBoolean("esito_campione_chiuso");
		informazioniLaboratorioChiuso = rs.getBoolean("esito_informazioni_laboratorio_chiuso");
		
		setPermission();

	}

	public int updateEsito(Connection db, boolean override) throws SQLException {
		int resultCount = 0;
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ticket ");

		sql.append(" set punteggio=?, solution = ?, txt_desc_non_accettato = ?, "
				+ "est_resolution_date = ?, est_resolution_date_timezone = ?,sanzioni_amministrative = ? , responsabilita_positivita = ? , segnalazione = ? , allerta = ?"
				+

		" WHERE ticketid = ? AND tipologia = 2");
		/*
		 * if (!override) { sql.append("AND modified " + ((this.getModified() ==
		 * null)?"IS NULL ":"= ? ")); }
		 */
		int i = 0;
		pst = db.prepareStatement(sql.toString());

		pst.setInt(++i, punteggio);
		pst.setString(++i, solution);

		pst.setString(++i, txt_desc_non_accettato); // Aggiunto da alberto

		DatabaseUtils.setTimestamp(pst, ++i, estimatedResolutionDate);
		pst.setString(++i, estimatedResolutionDateTimeZone);
		DatabaseUtils.setInt(pst, ++i, esitoCampione);
		pst.setInt(++i, responsabilitaPositivita);
		pst.setBoolean(++i, segnalazione);
		pst.setBoolean(++i, allerta);
		pst.setInt(++i, id);

		/*
		 * if (!override && this.getModified() != null) { pst.setTimestamp(++i,
		 * this.getModified()); }
		 */
		resultCount = pst.executeUpdate();
		pst.close();

		return resultCount;
	}

	
	public int updateLocation(Connection db) throws SQLException {
		int resultCount = 0;
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ticket ");

		sql.append(" set location=?"
				+

		" WHERE ticketid = ?");
		/*
		 * if (!override) { sql.append("AND modified " + ((this.getModified() ==
		 * null)?"IS NULL ":"= ? ")); }
		 */
		int i = 0;
		pst = db.prepareStatement(sql.toString());

		pst.setString(++i, location);
		pst.setInt(++i, id);

		/*
		 * if (!override && this.getModified() != null) { pst.setTimestamp(++i,
		 * this.getModified()); }
		 */
		resultCount = pst.executeUpdate();
		pst.close();

		return resultCount;
	}
	public int update(Connection db, boolean override) throws SQLException {
		int resultCount = 0;
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ticket "
				+ "SET link_contract_id = ?, link_asset_id = ?, department_code = ?, "
				+ "pri_code = ?, scode = ?, "
				+ "cat_code = ?, assigned_to = ?, "
				+ "subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, "
				+ "source_code = ?, contact_id = ?, problem = ?, "
				+ "status_id = ?, trashed_date = ?, site_id = ? , ip_modified='"
				+ super.getIpModified() + "', ");

		sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db)
				+ ", modifiedby = ?, ");


		if (orgId != -1) {
			sql.append(" org_id = ?, ");
		}

		String asl = null;
		if (siteId == 201) {
			asl = "AV";
		} else if (siteId == 202) {
			asl = "BN";
		} else if (siteId == 203) {
			asl = "CE";
		} else if (siteId == 204) {
			asl = "NA1";
		} else if (siteId == 205) {
			asl = "NA2Nord";
		} else if (siteId == 206) {
			asl = "NA3Sud";
		} else if (siteId == 207) {
			asl = "SA";
		} else {
			if (siteId == 16) {
				asl = "FuoriRegione";
			}

		}
		if (location_new!=null && !location_new.equals(""))
			sql.append("location_new = ?, ");
		
		sql.append("solution = ?, txt_desc_non_accettato = ?, custom_data = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, "
				+ "est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, "
				+ "cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, "
				+ "user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, "
				+ "escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, "
				+ "sanzioni_amministrative = ?, "
				+ " allerta = ?,segnalazione=?, non_conformita = ?, conseguenze_positivita = ?, responsabilita_positivita = ?, note_esito = ? , "
				+ "punteggio = ?, microchip = ? "
				+ "WHERE ticketid = ? AND tipologia = 2");

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);

		DatabaseUtils.setInt(pst, ++i, this.getContactId());
		pst.setString(++i, this.getProblem());
		DatabaseUtils.setInt(pst, ++i, this.getStatusId());
		DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
		DatabaseUtils.setInt(pst, ++i, this.getSiteId());

		pst.setInt(++i, this.getModifiedBy());


		if (orgId != -1) {
			DatabaseUtils.setInt(pst, ++i, orgId);
		}
		
		if (location_new!=null && !location_new.equals(""))
			pst.setString(++i, location_new);

		pst.setString(++i, this.getSolution());

		pst.setString(++i, this.getTxt_desc_non_accettato());

		if (pippo != null) {
			pst.setString(++i, pippo);
		} else {
			pst.setNull(i++, Types.VARCHAR);
		}
		pst.setString(++i, location);
		DatabaseUtils.setTimestamp(pst, ++i, assignedDate);
		pst.setString(++i, this.assignedDateTimeZone);
		DatabaseUtils.setTimestamp(pst, ++i, estimatedResolutionDate);
		pst.setString(++i, estimatedResolutionDateTimeZone);
		DatabaseUtils.setTimestamp(pst, ++i, resolutionDate);
		pst.setString(++i, this.resolutionDateTimeZone);
		pst.setString(++i, cause);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);

		DatabaseUtils.setInt(pst, ++i, causeId);
		DatabaseUtils.setInt(pst, ++i, resolutionId);
		pst.setNull(++i, java.sql.Types.INTEGER);
		DatabaseUtils.setInt(pst, ++i, this.getStateId());
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.BOOLEAN);
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);

		DatabaseUtils.setInt(pst, ++i, esitoCampione);
		pst.setBoolean(++i, allerta);
		pst.setBoolean(++i, segnalazione);
		pst.setBoolean(++i, nonConformita);
		pst.setInt(++i, conseguenzePositivita);
		pst.setInt(++i, responsabilitaPositivita);
		pst.setString(++i, noteEsito);
		pst.setInt(++i, punteggio);
		pst.setString(++i, microchip);
		pst.setInt(++i, id);

		resultCount = pst.executeUpdate();
		pst.close();

		return resultCount;
	}

	public boolean isSegnalazione() {
		return segnalazione;
	}

	public void setSegnalazione(boolean segnalazione) {
		this.segnalazione = segnalazione;
	}

	public void setSegnalazione(String segnalazione) {
		this.segnalazione = DatabaseUtils.parseBoolean(segnalazione);
	}

	public boolean getSegnalazione() {
		return segnalazione;
	}

	public int chiudi(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;

			

			MacroareaValutazioneComportamentale scheda = new MacroareaValutazioneComportamentale(db,this.id);
			boolean schedaMacroareaInserita = false ;
			if (scheda.getId()>0)
				schedaMacroareaInserita = true ;

			if (!isCampioneChiudibilePnaa(db))
				resultCount = -2 ;
			
			for (Analita analita : this.getTipiCampioni())
			{
				
				if (analita.getEsito_data()==null || analita.getEsito_id()<=0)
				{
					resultCount = -2 ;
				}
			}
			if (resultCount != -2) {


				if (tipologia_operatore == Ticket.TIPO_CANILI || tipologia_operatore == Ticket.TIPO_CANIPADRONALI || tipologia_operatore == Ticket.TIPO_OPERATORI_COMMERCIALI)
				{
					if(this.getMotivazione_piano_campione()!=210 || (schedaMacroareaInserita && this.getMotivazione_piano_campione()==210 ))
					{
						String sql = "UPDATE ticket " + "SET closed = ?, modified = "
						+ DatabaseUtils.getCurrentTimestamp(db)
						+ ", modifiedby = ? " + "WHERE ticketid = ? ";

						int i = 0;
						pst = db.prepareStatement(sql);
						pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
						pst.setInt(++i, this.getModifiedBy());
						pst.setInt(++i, this.getId());
						resultCount = pst.executeUpdate();
						pst.close();
					}
					else
					{
						resultCount = -3 ;
					}


				}
				else
				{
					String sql = "UPDATE ticket " + "SET closed = ?, modified = "
					+ DatabaseUtils.getCurrentTimestamp(db)
					+ ", modifiedby = ? " + "WHERE ticketid = ? ";

					int i = 0;
					pst = db.prepareStatement(sql);
					pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
					pst.setInt(++i, this.getModifiedBy());
					pst.setInt(++i, this.getId());
					resultCount = pst.executeUpdate();
					pst.close();
				}
			
		}

			this.setClosed((java.sql.Timestamp) null);

			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}




	public void getTipoCampioneSelezionatoNuovaGestione(Connection db) throws SQLException {

		String sql = "select * from analiti_campioni where id_campione = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			int code = rs.getInt("analiti_id");
			String value = rs.getString("cammino");
			Analita a = new Analita();
			a.setIdAnalita(code);
			a.setDescrizione(value);
			
			a.setEsito_allarme_rapido(rs.getBoolean("esito_allarme_rapido"));
			a.setEsito_data(rs.getTimestamp("esito_data"));
			a.setEsito_id(rs.getInt("esito_id"));
			a.setEsito_motivazione_respingimento(rs.getString("esito_motivazione_respingimento"));
			a.setEsito_note_esame(rs.getString("esito_note_esame"));
			a.setEsito_punteggio(rs.getInt("esito_punteggio"));
			a.setEsito_responsabilita_positivita(rs.getInt("esito_responsabilita_positivita"));
			a.setEsito_segnalazione_informazioni(rs.getBoolean("esito_segnalazione_informazioni"));
			a.setEsito_nonconforme_nonc(rs.getString("esito_nonconforme_nonc"));
			tipiCampioni.add(a);
		}
	}

	public void getMatriceCampioneSelezionatoNuovaGestione(Connection db) throws SQLException {

		String sql = "select * from matrici_campioni where id_campione = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			int code = rs.getInt("id_matrice");
			String value = rs.getString("cammino");
			matrici.put(code, value);
		}
	}

	public void getTipoCampioneSelezionato(Connection db) throws SQLException {

		String sql = "";

		if (tipoCampione == 5) {
			sql = "select idcampionetipo,description from tipicampioni,lookup_tipo_campione_chimicio where code=idcampionetipo and idticket=? ";

			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			int code = 0;
			if (rs.next()) {
				code = rs.getInt("idcampionetipo");
				String value = rs.getString("description");
				Analita a = new Analita();
				a.setIdAnalita(code);
				a.setDescrizione(value);
				tipiCampioni.add(a);
			}
			String sql2 = "";


			switch (code) {
			case 1:
				sql2 = "select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio where code=idesamechimicotipo and idticket=? ";
				break;
			case 2:
				sql2 = "select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio2 where code=idesamechimicotipo and idticket=? ";
				break;
			case 3:
				sql2 = "select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio3 where code=idesamechimicotipo and idticket=? ";
				break;
			case 4:
				sql2 = "select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio4 where code=idesamechimicotipo and idticket=? ";
				break;
			case 5: 								
				sql2 = "select idesamechimicotipo,description from tipocampionechimico,lookup_tipo_campione_sottochimicio5 where code=idesamechimicotipo and idticket=? ";
				break;
			}

			if (!sql2.equals("")) {
				PreparedStatement pst1 = db.prepareStatement(sql2);
				pst1.setInt(1, id);
				ResultSet rs1 = pst1.executeQuery();
				while (rs1.next()) {
					int code1 = rs1.getInt("idesamechimicotipo");
					String value1 = rs1.getString("description");
					tipiChimiciSelezionati.put(code1, value1);
				}
			}
		}

		else{
			if (tipoCampione == 1 || tipoCampione == 2 || tipoCampione == 4
					|| tipoCampione == 26 || tipoCampione == 27) {

				switch (tipoCampione) {
				case 1:
					sql = "select idcampionetipo,description from tipicampioni,lookup_tipo_campione_batteri where code=idcampionetipo and idticket=? ";
					break;
				case 2:
					sql = "select idcampionetipo,description from tipicampioni,lookup_tipo_campione_virus where code=idcampionetipo and idticket=? ";
					break;
				case 4:
					sql = "select idcampionetipo,description from tipicampioni,lookup_tipo_campione_parassiti where code=idcampionetipo and idticket=? ";
					break;
				case 26:
					sql = "select idcampionetipo,description from tipicampioni,lookup_tipo_campione_istologico where code=idcampionetipo and idticket=? ";
					break;
				case 27:
					sql = "select idcampionetipo,description from tipicampioni,lookup_tipo_campione_sierologico where code=idcampionetipo and idticket=? ";
					break;

				default:
					break;
				}

				PreparedStatement pst = db.prepareStatement(sql);
				pst.setInt(1, id);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					int code = rs.getInt("idcampionetipo");
					String value = rs.getString("description");
					Analita a = new Analita();
					a.setIdAnalita(code);
					a.setDescrizione(value);
					tipiCampioni.add(a);
				}
			}
		}

	}

	public ArrayList<Analita> getTipiCampioni() {
		return tipiCampioni;
	}

	public void setTipiCampioni(ArrayList<Analita> tipiCampioni) {
		this.tipiCampioni = tipiCampioni;
	}

	public void aggiornatipiCampioni(Connection db, String[] tipi,
			String[] tipiChimici) throws SQLException {
		String remove = "delete from tipicampioni where idticket=" + id;
		PreparedStatement ps = db.prepareStatement(remove);
		ps.execute();
		this.insertTipoCampione(db, tipi);

		if (tipiChimici != null) {
			String remove1 = "delete from tipocampionechimico where idticket="
				+ id;
			PreparedStatement ps1 = db.prepareStatement(remove1);
			ps1.execute();

			this.insertTipoChimico(db, tipiChimici);
		}

	}

	public void insertTipoCampione(Connection db, String[] tipi)
	throws SQLException {
		if (tipi != null) {
			String sql = "insert into tipicampioni (idticket,idcampionetipo) values (?,?) ";
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			for (int i = 0; i < tipi.length; i++) {
				int idCamp = Integer.parseInt(tipi[i]);
				pst.setInt(2, idCamp);
				pst.execute();
			}
		}
	}

	private int altrialimenti = -1;

	public int getAltrialimenti() {
		return altrialimenti;
	}

	public void setAltrialimenti(int altrialimenti) {
		this.altrialimenti = altrialimenti;
	}

	public void getTipoAlimentiVegetali(Connection db) throws SQLException {

		String sql = "";

		sql = "select code,description from tipoalimentioriginevegetale,lookup_alimenti_origine_vegetale_valori where code=idalimento and idticket=? ";

		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		int code = 0;
		while (rs.next()) {
			code = rs.getInt("code");
			String value = rs.getString("description");
			tipiAlimentiVegetali.put(code, value);
		}
	}

	public void insertAlimentiOrigineVegetale(Connection db, String[] tipi)
	throws SQLException {

		String sql = "insert into tipoalimentioriginevegetale(idticket,idalimento) values (?,?) ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		if (tipi != null) {
			for (int i = 0; i < tipi.length; i++) {
				int idCamp = Integer.parseInt(tipi[i]);
				pst.setInt(2, idCamp);
				pst.execute();
			}
		}
	}

	public void aggiornaAlimentiOrigineVegetale(Connection db, String[] tipi)
	throws SQLException {

		String sql = "delete from tipoalimentioriginevegetale where idticket=? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		pst.execute();
		insertAlimentiOrigineVegetale(db, tipi);

	}

	public void insertTipoChimico(Connection db, String[] tipi)
	throws SQLException {

		String sql = "insert into tipocampionechimico (idticket,idesamechimicotipo) values (?,?) ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		if (tipi != null) {
			for (int i = 0; i < tipi.length; i++) {
				int idCamp = Integer.parseInt(tipi[i]);
				pst.setInt(2, idCamp);
				pst.execute();
			}
		}
	}



	public boolean delete(Connection db, String baseFilePath)
	throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("Ticket ID not specified.");
		}
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}
			// delete any related action list items

			// Delete the ticket tasks

			// delete all history data
			PreparedStatement pst = null ;
			pst = db.prepareStatement("UPDATE ticket SET trashed_date = ? , modified=now() , modifiedby = ? WHERE ticketid = ?");
			pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pst.setInt(2, this.getModifiedBy());
			pst.setInt(3, this.getId());
			pst.execute();
			pst.close();
			
			// delete barcode_osa
			pst = db.prepareStatement("DELETE from barcode_osa where id_campione = ?::text");
			pst.setInt(1, this.getId());
			pst.execute();
			pst.close();

			/* Ricalcolo punteggio controllo ufficiale */
			pst = db.prepareStatement("select id_controllo_ufficiale from ticket where ticketid = ?");
			pst.setInt(1, this.getId());
			ResultSet rs = pst.executeQuery();

			int seleziona_id_controllo_ufficiale = -1;

			if (rs.next())
				seleziona_id_controllo_ufficiale = Integer.parseInt(rs
						.getString("id_controllo_ufficiale"));

			pst = db.prepareStatement("UPDATE ticket SET punteggio = (select sum(punteggio) "
					+ "from ticket where trashed_date is null and tipologia in (2,8,7) and id_controllo_ufficiale = "
					+ "(select id_controllo_ufficiale from ticket where ticketid = ?)) "
					+ "WHERE ticketid = ("
					+ seleziona_id_controllo_ufficiale
					+ ");");

			pst.setInt(1, this.getId());


			pst.execute();
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

	public boolean logicDelete(Connection db, String baseFilePath)
	throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("Ticket ID not specified.");
		}
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}

			// delete all history data
			/*
			 * PreparedStatement pst = db.prepareStatement(
			 * "UPDATE ticket SET trashed_date = ? WHERE ticketid = ?");
			 * pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			 * pst.setInt(2, this.getId()); pst.execute(); pst.close();
			 */
			PreparedStatement pst = null;
			pst = db.prepareStatement("UPDATE ticket SET trashed_date = ? , modified=now() , modifiedby = ? WHERE ticketid = ?");
			pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pst.setInt(2, this.getModifiedBy());
			pst.setInt(3, this.getId());
			pst.execute();
			pst.close();

			/* Ricalcolo punteggio controllo ufficiale */
			pst = db.prepareStatement("select id_controllo_ufficiale from ticket where ticketid = ?");
			pst.setInt(1, this.getId());
			ResultSet rs = pst.executeQuery();

			int seleziona_id_controllo_ufficiale = -1;

			if (rs.next())
				seleziona_id_controllo_ufficiale = Integer.parseInt(rs
						.getString("id_controllo_ufficiale"));

			pst = db.prepareStatement("UPDATE ticket SET punteggio = (select sum(punteggio) "
					+ "from ticket where trashed_date is null and tipologia in (2,8,7) and id_controllo_ufficiale = "
					+ "(select id_controllo_ufficiale from ticket where ticketid = ?)) "
					+ "WHERE ticketid = ("
					+ seleziona_id_controllo_ufficiale
					+ ");");

			pst.setInt(1, this.getId());


			pst.execute();
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

	public void aggiornaEsito(Connection db) {

	}

	public int reopen(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql = "UPDATE ticket " + "SET closed = ?, modified = "
			+ DatabaseUtils.getCurrentTimestamp(db)
			+ ", modifiedby = ? " + "WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setNull(++i, java.sql.Types.TIMESTAMP);
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			this.setClosed((java.sql.Timestamp) null);
			// Update the ticket log
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}

	public String getTestoAppoggio() {
		return testoAppoggio;
	}

	public void setTestoAppoggio(String testoAppoggio) {
		this.testoAppoggio = testoAppoggio;
	}

	public String getTestoAlimentoComposto() {
		return testoAlimentoComposto;
	}

	public void setTestoAlimentoComposto(String testoAlimentoComposto) {
		this.testoAlimentoComposto = testoAlimentoComposto;
	}

	public int getNucleoIspettivoQuattro() {
		return nucleoIspettivoQuattro;
	}

	public void setNucleoIspettivoQuattro(int nucleoIspettivoQuattro) {
		this.nucleoIspettivoQuattro = nucleoIspettivoQuattro;
	}

	public int getNucleoIspettivoCinque() {
		return nucleoIspettivoCinque;
	}

	public void setNucleoIspettivoCinque(int nucleoIspettivoCinque) {
		this.nucleoIspettivoCinque = nucleoIspettivoCinque;
	}

	public int getNucleoIspettivoSei() {
		return nucleoIspettivoSei;
	}

	public void setNucleoIspettivoSei(int nucleoIspettivoSei) {
		this.nucleoIspettivoSei = nucleoIspettivoSei;
	}

	public int getNucleoIspettivoSette() {
		return nucleoIspettivoSette;
	}

	public void setNucleoIspettivoSette(int nucleoIspettivoSette) {
		this.nucleoIspettivoSette = nucleoIspettivoSette;
	}

	public int getNucleoIspettivoOtto() {
		return nucleoIspettivoOtto;
	}

	public void setNucleoIspettivoOtto(int nucleoIspettivoOtto) {
		this.nucleoIspettivoOtto = nucleoIspettivoOtto;
	}

	public int getNucleoIspettivoNove() {
		return nucleoIspettivoNove;
	}

	public void setNucleoIspettivoNove(int nucleoIspettivoNove) {
		this.nucleoIspettivoNove = nucleoIspettivoNove;
	}

	public int getNucleoIspettivoDieci() {
		return nucleoIspettivoDieci;
	}

	public void setNucleoIspettivoDieci(int nucleoIspettivoDieci) {
		this.nucleoIspettivoDieci = nucleoIspettivoDieci;
	}

	public String getComponenteNucleoQuattro() {
		return componenteNucleoQuattro;
	}

	public void setComponenteNucleoQuattro(String componenteNucleoQuattro) {
		this.componenteNucleoQuattro = componenteNucleoQuattro;
	}

	public String getComponenteNucleoCinque() {
		return componenteNucleoCinque;
	}

	public void setComponenteNucleoCinque(String componenteNucleoCinque) {
		this.componenteNucleoCinque = componenteNucleoCinque;
	}

	public String getComponenteNucleoSei() {
		return componenteNucleoSei;
	}

	public void setComponenteNucleoSei(String componenteNucleoSei) {
		this.componenteNucleoSei = componenteNucleoSei;
	}

	public String getComponenteNucleoSette() {
		return componenteNucleoSette;
	}

	public void setComponenteNucleoSette(String componenteNucleoSette) {
		this.componenteNucleoSette = componenteNucleoSette;
	}

	public String getComponenteNucleoOtto() {
		return componenteNucleoOtto;
	}

	public void setComponenteNucleoOtto(String componenteNucleoOtto) {
		this.componenteNucleoOtto = componenteNucleoOtto;
	}

	public String getComponenteNucleoNove() {
		return componenteNucleoNove;
	}

	public void setComponenteNucleoNove(String componenteNucleoNove) {
		this.componenteNucleoNove = componenteNucleoNove;
	}

	public String getComponenteNucleoDieci() {
		return componenteNucleoDieci;
	}

	public void setComponenteNucleoDieci(String componenteNucleoDieci) {
		this.componenteNucleoDieci = componenteNucleoDieci;
	}

	public boolean isEsitoCampioneChiuso() {
		return esitoCampioneChiuso;
	}

	public void setEsitoCampioneChiuso(boolean esitoCampioneChiuso) {
		this.esitoCampioneChiuso = esitoCampioneChiuso;
	}

	public boolean isInformazioniLaboratorioChiuso() {
		return informazioniLaboratorioChiuso;
	}

	public void setInformazioniLaboratorioChiuso(boolean informazioniLaboratorioChiuso) {
		this.informazioniLaboratorioChiuso = informazioniLaboratorioChiuso;
	}

	public String getCampioneCancellabile(Connection db) {
		// TODO Auto-generated method stub
		String cancellabile = "";
		String sqlCheck = "select * from is_campione_cancellabile(?)";
		PreparedStatement pstCheck;
		try {
			pstCheck = db.prepareStatement(sqlCheck);
			pstCheck.setInt(1, this.getId());
			ResultSet rsCheck = pstCheck.executeQuery();
			System.out.println("VERIFICO POSSIBILITA CANCELLAZIONE CAMPIONE : "+pstCheck.toString());
			if (rsCheck.next()){
				cancellabile = rsCheck.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cancellabile;
		}

	public String getCodiceInternoPiano() {
		return codiceInternoPiano;
	}

	public boolean isCampioneChiudibilePnaa(Connection db) throws SQLException{
		
		String messaggio = "";
		String sql="select * from is_campione_chiudibile_pnaa(?)";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rst=pst.executeQuery();

		while(rst.next()){
			messaggio=rst.getString(1);
		}
		if (messaggio!=null && !messaggio.equals(""))
			return false;
		return true;
	}

}
