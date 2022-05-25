/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/


package org.aspcf.modules.controlliufficiali.base;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;





import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.soa.base.Organization;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;



public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6062647703225443222L;

	public int getAnomaliIspezionat() {
		return anomaliIspezionat;
	}
	private int idMacchinetta=-1;

	public void setAnomaliIspezionat(int anomaliIspezionat) {
		this.anomaliIspezionat = anomaliIspezionat;
	}
	
	public int getAuditTipo() {
		return auditTipo;
	}

	public void setAuditTipo(int auditTipo) {
		this.auditTipo = auditTipo;
	}
	private int auditTipo=-1;
	
	private double latitude = 0.0;
	private double longitude = 0.0;
	private String latitude2 = null;
	private String longitude2 = null;
	
	private String descrizioneControllo;
	private String descrizioneAnimali=null;
	private int numeroMezzi=-1;
	private int categoriaTrasportata=-1;
	private int specieA=-1;
	private int categoriaRischio ;
	
	private int distribuzionePartita;
	private int destinazioneDistribuzione;
	private boolean comunicazioneRischio;
	private String noteRischio;
	private int proceduraRitiro=-1;
	private int proceduraRichiamo;
	private String motivoProceduraRichiamo;
	private String allertaNotes;
	private int esitoControllo;
	private Timestamp dataddt;
	private int numDDt;
	private String quantitaPartita;
	private String quantitaBloccata;
	private HashMap<Integer, String> azioniAdottate = new HashMap<Integer, String>();
	private HashMap<Integer, String> listaAnimali_trasportati = new HashMap<Integer, String>();
	private int azioneArticolo;
	private Integer idFileAllegato;
	
	
	private boolean listaDistribuzioneAllegata=false;
	private String subjectFileAllegato="";
	private String unitaMisura="";
	
	
	
	
	
	
	public String getUnitaMisura() {
		return unitaMisura;
	}

	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	public boolean isComunicazioneRischio() {
		return comunicazioneRischio;
	}

	public void setComunicazioneRischio(boolean comunicazioneRischio) {
		this.comunicazioneRischio = comunicazioneRischio;
	}

	public String getNoteRischio() {
		return noteRischio;
	}

	public void setNoteRischio(String noteRischio) {
		this.noteRischio = noteRischio;
	}

	public int getProceduraRitiro() {
		return proceduraRitiro;
	}

	public void setProceduraRitiro(int proceduraRitiro) {
		this.proceduraRitiro = proceduraRitiro;
	}

	public void setProceduraRitiro(String proceduraRitiro) {
		if (proceduraRitiro!=null && !proceduraRitiro.equals("null") && !proceduraRitiro.equals(""))
		this.proceduraRitiro = Integer.parseInt(proceduraRitiro);
	}
	
	public int getProceduraRichiamo() {
		return proceduraRichiamo;
	}

	public void setProceduraRichiamo(int proceduraRichiamo) {
		this.proceduraRichiamo = proceduraRichiamo;
	}

	public String getMotivoProceduraRichiamo() {
		return motivoProceduraRichiamo;
	}

	public void setMotivoProceduraRichiamo(String motivoProceduraRichiamo) {
		this.motivoProceduraRichiamo = motivoProceduraRichiamo;
	}
	
	public int getEsitoControllo() {
		return esitoControllo;
	}

	public void setEsitoControllo(int esitoControllo) {
		this.esitoControllo = esitoControllo;
	}

	public Timestamp getDataDdt() {
		return dataddt;
	}

	public void setDataDdt(Timestamp dataDDt) {
		this.dataddt = dataDDt;
	}

	public int getNumDDt() {
		return numDDt;
	}

	public void setNumDDt(int numDDt) {
		this.numDDt = numDDt;
	}

	public String getQuantitaPartita() {
		return quantitaPartita;
	}

	public void setQuantitaPartita(String quantitaPartita) {
		this.quantitaPartita = quantitaPartita;
	}

	public String getQuantitaBloccata() {
		return quantitaBloccata;
	}

	public void setQuantitaBloccata(String quantitaBloccata) {
		this.quantitaBloccata = quantitaBloccata;
	}

	public HashMap<Integer, String> getAzioniAdottate() {
		return azioniAdottate;
	}

	public void setAzioniAdottate(HashMap<Integer, String> azioniAdottate) {
		this.azioniAdottate = azioniAdottate;
	}

	public HashMap<Integer, String> getListaAnimaliTrasportati() {
		return listaAnimali_trasportati;
	}

	public void setListaAnimaliTrasportati(HashMap<Integer, String> animaliTrasp) {
		this.listaAnimali_trasportati = animaliTrasp;
	}
	
	public int getAzioneArticolo() {
		return azioneArticolo;
	}

	public void setAzioneArticolo(int azioneArticolo) {
		this.azioneArticolo = azioneArticolo;
	}

	public int getIdFileAllegato() {
		return idFileAllegato;
	}

	public void setIdFileAllegato(int idFileAllegato) {
		this.idFileAllegato = idFileAllegato;
	}

	public String getSubjectFileAllegato() {
		return subjectFileAllegato;
	}

	public void setSubjectFileAllegato(String subjectFileAllegato) {
		this.subjectFileAllegato = subjectFileAllegato;
	}

	public boolean isListaDistribuzioneAllegata() {
		return listaDistribuzioneAllegata;
	}

	public void setListaDistribuzioneAllegata(boolean listaDistribuzioneAllegata) {
		this.listaDistribuzioneAllegata = listaDistribuzioneAllegata;
	}

	public int getDistribuzionePartita() {
		return distribuzionePartita;
	}

	public void setDistribuzionePartita(int distribuzionePartita) {
		this.distribuzionePartita = distribuzionePartita;
	}

	public int getDestinazioneDistribuzione() {
		return destinazioneDistribuzione;
	}

	public void setDestinazioneDistribuzione(int destinazioneDistribuzione) {
		this.destinazioneDistribuzione = destinazioneDistribuzione;
	}

	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}

	public String getDescrizioneControllo() {
		return descrizioneControllo;
	}

	public void setDescrizioneControllo(String descrizioneControllo) {
		this.descrizioneControllo = descrizioneControllo;
	}
	

	public String getDescrizioneAnimali() {
		return descrizioneAnimali;
	}

	public void setDescrizioneAnimali(String descrizioneAnimali) {
		this.descrizioneAnimali = descrizioneAnimali;
	}
	
	public int getNumeroMezzi() {
		return numeroMezzi;
	}

	public void setNumeroMezzi(int numeroMezzi) {
		this.numeroMezzi = numeroMezzi;
	}

	public int getCategoriaTrasportata() {
		return categoriaTrasportata;
	}

	public void setCategoriaTrasportata(int categoriaTrasportata) {
		this.categoriaTrasportata = categoriaTrasportata;
	}
	
	public int getSpecieA() {
		return specieA;
	}

	public void setSpecieA(int specieA) {
		this.specieA = specieA;
	}
	
	public int getIdMacchinetta() {
		return idMacchinetta;
	}

	public void setIdMacchinetta(int idMacchinetta) {
		this.idMacchinetta = idMacchinetta;
	}
	private int anomaliIspezionat=-1;
	/*private String luogoControllo;
	private int mezziIspezionati=-1;

	/*private String luogoControllo;
	private int mezziIspezionati=-1;*/


	/*public String getLuogoControllo() {
		return luogoControllo;
	}

	public void setLuogoControllo(String luogoControllo) {
		this.luogoControllo = luogoControllo;
	}

	public int getMezziIspezionati() {
		return mezziIspezionati;
	}

	public void setMezziIspezionati(int mezziIspezionati) {
		this.mezziIspezionati = mezziIspezionati;
	}*/

/*	public int getAnomaliIspezionati() {
		return anomaliIspezionat;
	}

	public void setAnomaliIspezionati(int animaliIspezionati) {
		this.anomaliIspezionat = animaliIspezionati;
	}
	
	public int getAnomaliIspezionati() {
		return anomaliIspezionat;
	}*/
	
	private String noteAltrodiSistema="";

	public String getNoteAltrodiSistema() {
		return noteAltrodiSistema;
	}

	public void setNoteAltrodiSistema(String noteAltrodiSistema) {
		this.noteAltrodiSistema = noteAltrodiSistema;
	}

	public void setAnomaliIspezionati(int animaliIspezionati) {
		this.anomaliIspezionat = animaliIspezionati;}
	private boolean ncrilevate=false;
	
	public Ticket()
	{
		
	}
	
	public String getCodiceAllerta() {
		return codiceAllerta;
	}
	public boolean isNcrilevate() {
		return ncrilevate;
	}

	public void setNcrilevate(boolean ncrilevate) {
		this.ncrilevate = ncrilevate;
	}

	public void setCodiceAllerta(String codiceAllerta) {
		this.codiceAllerta = codiceAllerta;
	}
	 private String nonConformitaFormali="";
	    private String nonConformitaGravi="";
	    private String nonConformitaSignificative="";
	    private String puntiFormali=null;
	    private String puntiSignificativi=null;
	    private String puntiGravi=null;
	    private String luogoControllo=null;
	    private int mezziIspezionati=-1;
	    private int animaliIspezionati=-1;
	 
	public String getNonConformitaFormali() {
			return nonConformitaFormali;
		}

		public void setNonConformitaFormali(String nonConformitaFormali) {
			this.nonConformitaFormali = nonConformitaFormali;
		}

		public String getNonConformitaGravi() {
			return nonConformitaGravi;
		}

		public void setNonConformitaGravi(String nonConformitaGravi) {
			this.nonConformitaGravi = nonConformitaGravi;
		}

		public String getNonConformitaSignificative() {
			return nonConformitaSignificative;
		}

		public void setNonConformitaSignificative(String nonConformitaSignificative) {
			this.nonConformitaSignificative = nonConformitaSignificative;
		}

		public String getPuntiFormali() {
			return puntiFormali;
		}
		public void setPuntiFormali(String puntiFormali) {
			this.puntiFormali = puntiFormali;
		}
		public String getLuogoControllo() {
			return luogoControllo;
		}

		public void setLuogoControllo(String luogoControllo) {
			this.luogoControllo = luogoControllo;
		}
		public int getMezziIspezionati() {
			return mezziIspezionati;
		}

		public void setMezziIspezionati(int mezziIspezionati) {
			this.mezziIspezionati = mezziIspezionati;
		}
		public void setMezziIspezionati(String tmp) {
			this.mezziIspezionati = Integer.parseInt(tmp);
		}
		public int getAnimaliIspezionati() {
			return animaliIspezionati;
		}

		public void setAnimaliIspezionati(int animaliIspezionati) {
			this.animaliIspezionati = animaliIspezionati;
		}
		public void setAnimaliIspezionati(String tmp) {
			this.animaliIspezionati = Integer.parseInt(tmp);
		}
		public void setCategoriaTrasportata(String tmp) {
			this.categoriaTrasportata = Integer.parseInt(tmp);
		}
		public void setSpecieA(String tmp) {
			this.specieA = Integer.parseInt(tmp);
		}
		public String getPuntiSignificativi() {
			return puntiSignificativi;
		}

		public void setPuntiSignificativi(String puntiSignificativi) {
			this.puntiSignificativi = puntiSignificativi;
		}

		public String getPuntiGravi() {
			return puntiGravi;
		}

		public void setPuntiGravi(String puntiGravi) {
			this.puntiGravi = puntiGravi;
		}
	protected String codiceAllerta="";
	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	protected String pippo = "";
	public double getContributi() {
		return contributi;
	}

	public void setContributi(double contributi) {
		this.contributi = contributi;
	}
	protected int tipoCampione = -1;
	protected int tipoAudit = -1;
	protected int esitoCampione = -1;
	protected int destinatarioCampione = -1;
	protected Timestamp dataAccettazione = null;
	protected String dataAccettazioneTimeZone = null;
	private int nucleoIspettivo = -1;
	private int nucleoIspettivoDue = -1;
	private int nucleoIspettivoTre = -1;
	//aggiunto da d.dauria
	private String componenteNucleo = null;
	private String componenteNucleoDue = null;
	private String componenteNucleoTre = null;
	private String testoAppoggio = "";
	private int nucleoIspettivoQuattro = -1;
	private int nucleoIspettivoCinque = -1;
	private int nucleoIspettivoSei = -1;
	private int nucleoIspettivoSette = -1;
	private int nucleoIspettivoOtto = -1;
	private int nucleoIspettivoNove = -1;
	private int nucleoIspettivoDieci = -1;
	private String componenteNucleoQuattro = null;
	private String componenteNucleoCinque = null;
	private String componenteNucleoSei = null;
	private String componenteNucleoSette = null;
	private String componenteNucleoOtto = null;
	private String componenteNucleoNove = null;
	private String componenteNucleoDieci = null;
	private double contributi=0;
	private boolean inserisciContinua = false;
	private String idControlloUfficiale = null;
	private ArrayList<Object> listaRiferimenti = new ArrayList<Object>();
	private int punteggio = 0;
	private int tipoIspezione=-1;
	private int pianoMonitoraggio=-1;
	private int ispezione=1;
	private String bpi="-1";
	private int haccp=-1;
	private String descriptionTipoAudit="";
	private String descriptionTipoIspezione="";
	private String followUp = null;
	private HashMap<Integer,String> lisaElementibpiOhaccp=new HashMap<Integer, String>();
	private HashMap<Integer, String> lisaElementipianoMonitoraggio_ispezioni=new HashMap<Integer, String>();
	


	
	
	
	public int getIspezione() {
		return ispezione;
	}
	public void setIspezione(int ispezione) {
		this.ispezione = ispezione;
	}
	public String getBpi() {
		return bpi;
	}
	public void setBpi(String bpi) {
		this.bpi = bpi;
	}
	public int getHaccp() {
		return haccp;
	}
	public void setHaccp(int haccp) {
		this.haccp = haccp;
	}
	public int getPianoMonitoraggio() {
		return pianoMonitoraggio;
	}
	public void setPianoMonitoraggio(int pianoMonitoraggio) {
		this.pianoMonitoraggio = pianoMonitoraggio;
	}
	public int getTipoIspezione() {
		return tipoIspezione;
	}
	public void setTipoIspezione(int tipoIspezione) {
		this.tipoIspezione = tipoIspezione;
	}

	private Timestamp dataFineControllo = null;
	private Timestamp dataProssimoControllo = null;
	
	public void setPunteggio(String temp)
	{
		this.punteggio = Integer.parseInt(temp);
	}
	public void setPunteggio(int temp)
	{
		this.punteggio = temp;
	}
	public int getPunteggio() {
		return punteggio;
	}
	public String getIdControlloUfficiale() {
		return idControlloUfficiale;
	}

	public void setIdControlloUfficiale(String idControlloUfficiale) {
		this.idControlloUfficiale = idControlloUfficiale;
	}
	
	
	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}
	public boolean getInserisciContinua() {
	    return inserisciContinua;
	  }

	  public void setInserisciContinua(boolean tmp) {
	    this.inserisciContinua = tmp;
	  }


	  public void setInserisciContinua(String tmp) {
	    this.inserisciContinua = DatabaseUtils.parseBoolean(tmp);
	  }
	
	public void setNucleoIspettivoQuattro(String temp)
	{
		this.nucleoIspettivoQuattro = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoCinque(String temp)
	{
		this.nucleoIspettivoCinque = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoSei(String temp)
	{
		this.nucleoIspettivoSei = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoSette(String temp)
	{
		this.nucleoIspettivoSette = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoOtto(String temp)
	{
		this.nucleoIspettivoOtto = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoNove(String temp)
	{
		this.nucleoIspettivoNove = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoDieci(String temp)
	{
		this.nucleoIspettivoDieci = Integer.parseInt(temp);
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

	public String getTestoAppoggio() {
		return testoAppoggio;
	}

	public void setTestoAppoggio(String testoAppoggio) {
		this.testoAppoggio = testoAppoggio;
	}

	public void setNucleoIspettivo(String temp)
	{
		this.nucleoIspettivo = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoDue(String temp)
	{
		this.nucleoIspettivoDue = Integer.parseInt(temp);
	}
	
	public void setNucleoIspettivoTre(String temp)
	{
		this.nucleoIspettivoTre = Integer.parseInt(temp);
	}
	
	public int getNucleoIspettivo() {
		return nucleoIspettivo;
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


		  /**
		   *  Sets the assignedDate attribute of the Ticket object
		   *
		   * @param  tmp  The new assignedDate value
		   */
		  public void setDataAccettazione(String tmp) {
		    this.dataAccettazione = DatabaseUtils.parseDateToTimestamp(tmp);
		  }
	 
	public java.sql.Timestamp getDataAccettazione() {
			    return dataAccettazione;
			  }

	 public void setDataFineControllo(java.sql.Timestamp tmp) {
		    this.dataFineControllo = tmp;
		  }
	 public void setDataProssimoControllo(java.sql.Timestamp tmp) {
		    this.dataProssimoControllo = tmp;
		  }


	  
	 public void setDataInizioControllo(java.sql.Timestamp tmp) {
		    this.assignedDate = tmp;
		  }

		  /**
		   *  Sets the assignedDate attribute of the Ticket object
		   *
		   * @param  tmp  The new assignedDate value
		   */
		  public void setDataFineControllo(String tmp) {
		    this.dataFineControllo = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
		  }
		  
		  public void setDataProssimoControllo(String tmp) {
			    this.dataProssimoControllo = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
			  }
		  /*public void setDataFineControllo(String tmp) {
			    this.dataFineControllo = DatabaseUtils.parseDateToTimestamp(tmp);
			  }*/
		
		  public void setDataInizioControllo(String tmp) {
			    this.assignedDate = DatabaseUtils.parseDateToTimestamp(tmp);
			  }
		  
		/*  public void setDataInizioControllo(String tmp) {
			    this.assignedDate = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
			  }
		 */
	public java.sql.Timestamp getDataFineControllo() {
			    return dataFineControllo;
			  }
	
	public java.sql.Timestamp getDataProssimoControllo() {
	    return dataProssimoControllo;
	  }
	public java.sql.Timestamp getDataInizioControllo() {
	    return assignedDate;
	  }

		  
	public int getTipoCampione() {
		return tipoCampione;
	}
	
	public String getDescriptionTipoIspezione() {
		return descriptionTipoIspezione;
	}
	public void setDescriptionTipoIspezione(String descriptionTipoIspezione) {
		this.descriptionTipoIspezione = descriptionTipoIspezione;
	}
	public HashMap<Integer, String> getLisaElementipianoMonitoraggio_ispezioni() {
		return lisaElementipianoMonitoraggio_ispezioni;
	}
	public void setLisaElementipianoMonitoraggio_ispezioni(
			HashMap<Integer, String> lisaElementipianoMonitoraggio_ispezioni) {
		this.lisaElementipianoMonitoraggio_ispezioni = lisaElementipianoMonitoraggio_ispezioni;
	}
	public int getTipoAudit() {
		return tipoAudit;
	}
	
	public void setTipoCampione(String tipoCampione) {
	    try {
	      this.tipoCampione = Integer.parseInt(tipoCampione);
	    } catch (Exception e) {
	      this.tipoCampione = -1;
	    }
	  }
	public void setTipoAudit(String tipoAudit) {
	    try {
	      this.tipoAudit = Integer.parseInt(tipoAudit);
	    } catch (Exception e) {
	      this.tipoAudit = -1;
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

	
	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}

	  public Ticket(ResultSet rs) throws SQLException {
	    buildRecord(rs);
	  }

	  
	  
	  public Ticket(Connection db,ResultSet rs) throws SQLException {
		  buildRecord(rs);


		  if(tipoCampione==3){// se si tratta di un controllo di tipo audit
			  this.getTipoAuditSelezionato(db, this.id); // ritorna la descrizione del tipo di audit selezionato
			  if(tipoAudit==2){ // e' un audit di tipo bpi
				  this.setBpiSelezionati(db, this.id);

			  }else{

				  if(tipoAudit==3){// se e' un controllo di tipo haccp
					  this.setHaccpSelezionati(db, this.id);

				  }
			  }

		  }
		  else{

			  if(tipoCampione==4){//se si tratta di un controllo ispezione
				  this.getTipoIspezioneSelezionato(db, this.id);
				  if(tipoIspezione==2){// controllo ispettivo in monitoraggio
					  this.setPianoMonitoraggiSelezionato(db, this.id);
				  }


				  else{

					  if(tipoIspezione==4 || tipoIspezione==6){// se e' un controllo ispettivo non in monitoraggio

						  this.setPianoispezioniSelezionate(db,this.id);
						  



					  }
					  else{
						  if(tipoIspezione==3){
							  
							  org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
				    	      int AuditOrgId = this.getOrgId();
				    	      String idT = this.getPaddedId();
				    	      audit.setOrgId(AuditOrgId);
				    	     
				    	      audit.buildListControlli(db, AuditOrgId, idT);
				    	      
				    	     
				      	      Iterator<Audit> itera=audit.iterator();
				      	      int punteggioChecklist=0;
				      	      while(itera.hasNext()){
				      	    	  Audit temp=itera.next();
				      	    	  punteggioChecklist+=temp.getLivelloRischio();
				      	    	  
				      	      }
							  
							  punteggio=punteggioChecklist;
							  
						  }

						  this.getTipoIspezioneSelezionato(db, tipoIspezione);

					  }

				  }


			  } 

		  }

		  }
	  
	  
	  private boolean categoriaisAggiornata=false;
	  
	  
	  public boolean isCategoriaisAggiornata() {
		return categoriaisAggiornata;
	}

	public void setCategoriaisAggiornata(boolean categoriaisAggiornata) {
		this.categoriaisAggiornata = categoriaisAggiornata;
	}

	public Ticket(Connection db, int id) throws SQLException {
	    queryRecord(db, id);
	  }
	  
	


	
	  
	  
	  protected HashMap<Integer, String> non_conformita_formali=new HashMap<Integer, String>();
		protected HashMap<Integer, String> non_conformita_significative=new HashMap<Integer, String>();
		public HashMap<Integer, String> getNon_conformita_formali() {
			return non_conformita_formali;
		}


		public void set_NonConformitaFormali(Connection db) throws SQLException {
			String sql="select nc_formali,description from salvataggio_nonconformita , lookup_provvedimenti where salvataggio_nonconformita.nc_formali=lookup_provvedimenti.code and idticket=?";
			PreparedStatement pst=db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rst=pst.executeQuery();
			
			while(rst.next()){
				
				int code=rst.getInt("nc_formali");
				String value=rst.getString("description");
				non_conformita_formali.put(code, value);
				
			}
			
			
			
			
			
		
		}

		public HashMap<Integer, String> getNon_conformita_significative() {
			return non_conformita_significative;
		}

		public void setNon_conformita_significative(Connection db) throws SQLException {
					String sql="select nc_significative,description from salvataggio_nonconformita , lookup_provvedimenti where salvataggio_nonconformita.nc_significative=lookup_provvedimenti.code and idticket=?";
					PreparedStatement pst=db.prepareStatement(sql);
					pst.setInt(1, id);
					ResultSet rst=pst.executeQuery();
					
					while(rst.next()){
						
						int code=rst.getInt("nc_significative");
						String value=rst.getString("description");
						non_conformita_significative.put(code, value);
						
					}
					
					
				
				}

		public HashMap<Integer, String> getNon_conformita_gravi() {
			return non_conformita_gravi;
		}

		public void setNon_conformita_gravi(Connection db) throws SQLException {
				String sql="select nc_gravi,description from salvataggio_nonconformita , lookup_provvedimenti where salvataggio_nonconformita.nc_gravi=lookup_provvedimenti.code and idticket=?";
				PreparedStatement pst=db.prepareStatement(sql);
				pst.setInt(1, id);
				ResultSet rst=pst.executeQuery();
				
				while(rst.next()){
					
					int code=rst.getInt("nc_gravi");
					String value=rst.getString("description");
					non_conformita_gravi.put(code, value);
					
				
				
				
				
				
				
			
			}
		}

		protected HashMap<Integer, String> non_conformita_gravi=new HashMap<Integer, String>();

		private int numeroAudit=0;
		
		
	  public int getNumeroAudit() {
			return numeroAudit;
		}

		public void setNumeroAudit(Connection db) {
			
			String sql="select count(id) from audit where id_controllo=? and trashed_date is null";
			try {
				PreparedStatement pst=db.prepareStatement(sql);
				pst.setString(1, this.getPaddedId());
				ResultSet rs=pst.executeQuery();
				if(rs.next()){
					numeroAudit=rs.getInt(1);
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			this.numeroAudit = numeroAudit;
		}

		
		public void buildAzioniAdottate(Connection db){
			
		
				try{
					PreparedStatement pst = db.prepareStatement("select * from salvataggio_azioni_adottate join lookup_azioni_adottate on (code = id_azione_adottata) where id_controllo_ufficiale = ?");
					pst.setInt(1, id);
					ResultSet rs = pst.executeQuery();
					while(rs.next())
					{
						int idAzione = rs.getInt("id_azione_adottata");
						String descr = rs.getString("description");
						azioniAdottate.put(idAzione, descr);
					}
					
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				
				
			
		}
		
public void salvaAzioniAdottate(Connection db, String [] azioniAdottate){
			
			if(tipoIspezione == 7)
			{
				try{
					PreparedStatement pst = db.prepareStatement("insert into salvataggio_azioni_adottate (id_controllo_ufficiale,id_azione_adottata) values (?,?)");
					PreparedStatement pst1 = db.prepareStatement("delete from salvataggio_azioni_adottate where id_controllo_ufficiale = "+id);
					pst1.execute();
					for(String idAzione : azioniAdottate)
					{
						pst.setInt(1, id);
						pst.setInt(2, Integer.parseInt(idAzione));
						pst.execute();
					}
					
					
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				
				
			}
		}

public void deleteAzioniAdottate(Connection db){
	
	if(tipoIspezione == 7)
	{
		try{
			PreparedStatement pst1 = db.prepareStatement("delete from salvataggio_azioni_adottate where id_controllo_ufficiale = "+id);
			pst1.execute();
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
}

//Salva specie animali trasportati

public void buildSpecieTrasportata(Connection db){
	
	
	try{
		PreparedStatement pst = db.prepareStatement("select * from salvataggio_specie_trasportata join lookup_specie_trasportata on (code = id_specie_trasportata) where id_controllo_ufficiale = ?");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			int idSpecie = rs.getInt("id_specie_trasportata");
			String descr = rs.getString("description");
			listaAnimali_trasportati.put(idSpecie, descr);
		}
		
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}

}


public void salvaSpecieTrasportata(Connection db, String [] specieTrasportata){

	try{
		PreparedStatement pst = db.prepareStatement("insert into salvataggio_specie_trasportata (id_controllo_ufficiale,id_specie_trasportata) values (?,?)");
		PreparedStatement pst1 = db.prepareStatement("delete from salvataggio_specie_trasportata where id_controllo_ufficiale = "+id);
		pst1.execute();
		for(String idSpecie : specieTrasportata)
		{
			pst.setInt(1, id);
			pst.setInt(2, Integer.parseInt(idSpecie));
			pst.execute();
		}
		
		
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}

}

	public void deleteSpecieTrasportata(Connection db){
	
	
		try{
			PreparedStatement pst1 = db.prepareStatement("delete from salvataggio_specie_trasportata where id_controllo_ufficiale = "+id);
			pst1.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	
	}


		
	public void queryRecord(Connection db, int id) throws SQLException {
		    if (id == -1) {
		      throw new SQLException("Invalid Ticket Number");
		    }
		    PreparedStatement pst = db.prepareStatement(
		        "SELECT t.*, " +
		        "o.name AS orgname, " +
		        "o.enabled AS orgenabled, " +
		        "o.site_id AS orgsiteid, " +
//		        
		        "a.serial_number AS serialnumber, " +
		        "a.manufacturer_code AS assetmanufacturercode, " +
		        "a.vendor_code AS assetvendorcode, " +
		        "a.model_version AS modelversion, " +
		        "a.location AS assetlocation, " +
		        "a.onsite_service_model AS assetonsiteservicemodel, " +
		        "lu_te.description AS escalationlevelname, tcu.tipo_audit, tcu.tipoispezione ,t.latitudine,t.longitudine " +
		        "FROM ticket t " +
		        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +

		        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +

		        "LEFT JOIN tipocontrolloufficialeimprese tcu ON (t.ticketid = tcu.idcontrollo)" +
		        "WHERE t.ticketid = ? AND t.tipologia = 3 ");
		    
		    
		    
		    pst.setInt(1, id);
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		      setNumeroAudit(db);
		      buildAzioniAdottate(db);
		      
		      buildSpecieTrasportata(db);
		    //aggiunto da giuseppe per prelevae il tipo di controllo
			    
		      if(tipoCampione==3){// se si tratta di un controllo di tipo audit
		    	  this.getTipoAuditSelezionato(db, this.id); // ritorna la descrizione del tipo di audit selezionato
		    	  if(tipoAudit==2){ // e' un audit di tipo bpi
		    		  this.setBpiSelezionati(db, this.id);
		    		  
		    	  }else{
		    		  
		    		  if(tipoAudit==3){// se e' un controllo di tipo haccp
		    			  this.setHaccpSelezionati(db, this.id);
		    			  
		    		  }
		    	  }
		    
		      }
		      else{
		    	  
		    	  if(tipoCampione==4){//se si tratta di un controllo ispezione
		    		  this.getTipoIspezioneSelezionato(db, this.id);
		    		  if(tipoIspezione==2){// controllo ispettivo in monitoraggio
		    			  this.setPianoMonitoraggiSelezionato(db, this.id);
		    		  }
		    			  
		    			  
		    		  else{
		    			  
		    			  if(tipoIspezione==4 || tipoIspezione==6 || tipoIspezione==9 || tipoIspezione==11){// se e' un controllo ispettivo non in monitoraggio
		    				 
		    				  this.setPianoispezioniSelezionate(db,this.id);
		    				  
		    		  
		    		  
		    	  }
		    			  else{
		    				
		    				  this.getTipoIspezioneSelezionato(db, tipoIspezione);
		    				  
		    			  }
		    	  
		      }
			   
			   
		    	  } 
			    
		      }
		      
		    
		    
		      this.set_NonConformitaFormali(db);
		      this.setNon_conformita_gravi(db);
		      this.setNon_conformita_significative(db);
		    
		    }
			    
			    //fine blocco aggiunto da giuseppe 
			    
		   
		   listaRiferimenti = this.getRiferimentiSoa(db, id);
		      
		     
		    
		    
		    rs.close();
		    pst.close();
		    if (this.id == -1) {
		      throw new SQLException(Constants.NOT_FOUND_ERROR);
		    }
		   
		   
		   
		      this.buildFiles(db);
		   
		 
		  }
	  
	  
	  
	  
	  
	  
	  
	  
 public ArrayList<Object> getListaRiferimenti() {
		return listaRiferimenti;
	}

	public void setListaRiferimenti(ArrayList<Object> listaRiferimenti) {
		this.listaRiferimenti = listaRiferimenti;
	}

public String getDescriptionTipoAudit() {
		return descriptionTipoAudit;
	}
	public void setDescriptionTipoAudit(String descriptionTipoAudit) {
		this.descriptionTipoAudit = descriptionTipoAudit;
	}
	public HashMap<Integer,String> getLisaElementibpiOhaccp() {
		return lisaElementibpiOhaccp;
	}
	public void setLisaElementibpiOhaccp(HashMap<Integer,String> lisaElementibpiOhaccp) {
		this.lisaElementibpiOhaccp = lisaElementibpiOhaccp;
	}

	/*public void insertTipocontrolloIspezioneTrasporti(Connection db,int tipoControllo,String[] s, int tipologia) throws SQLException{

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tipocontrolloufficialeimprese("+
				"idcontrollo, tipo_audit, bpi, haccp, tipoispezione, pianomonitoraggio,"+ 
		"ispezione)  VALUES (?, ?, ?, ?, ?, ?, ?)");
		PreparedStatement pst=db.prepareStatement(sql.toString());
		pst.setInt(1, id);
		

			pst.setInt(2, -1);
			pst.setInt(3, -1);
			pst.setInt(4, -1);
			pst.setInt(5, tipoIspezione);
			pst.setInt(6,-1);
			pst.setInt(7, -1);
			pst.execute();



		

	}*/

public void insertTipocontrollo(Connection db,int tipoControllo,String[] s, int tipologia) throws SQLException{
		  
		  StringBuffer sql = new StringBuffer();
		  sql.append("INSERT INTO tipocontrolloufficialeimprese("+
		            "idcontrollo, tipo_audit, bpi, haccp, tipoispezione, pianomonitoraggio,"+ 
		            "ispezione,audit_tipo)  VALUES (?,?, ?, ?, ?, ?, ?, ?)");
		  PreparedStatement pst=db.prepareStatement(sql.toString());
		  pst.setInt(1, id);
		 if(tipologia != 9){
		  if(tipoControllo==3){// se si sta eseguendo un controllo di tipo audit
				pst.setInt(2, tipoAudit);
				pst.setInt(8, auditTipo);
			 
				if(auditTipo==1){
				if(tipoAudit==2){// se il tipo audit e' bpi
				  
			
				
				  
				    for(int i=0;i<s.length;i++){
				    	int tipo_bpi=Integer.parseInt(s[i]);
				pst.setInt(3, tipo_bpi);
				pst.setInt(4, -1);
				pst.setInt(5, -1);
				pst.setInt(6, -1);
				pst.setInt(7, -1);
				pst.execute();
				}
				  
				  
			  }
			  else{
				  if(tipoAudit==3){// se tipo audit e' haccp
					  
					    for(int i=0;i<s.length;i++){
					    	int tipo_haccp=Integer.parseInt(s[i]);
					pst.setInt(3,-1);
					pst.setInt(4, tipo_haccp);
					pst.setInt(5, -1);
					pst.setInt(6, -1);
					pst.setInt(7, -1);
					pst.execute();
					}  
		  
				  }
				  else
				 {
					  
					  	pst.setInt(3,-1);
						pst.setInt(4, -1);
						pst.setInt(5, -1);
						pst.setInt(6, -1);
						pst.setInt(7, -1);
						pst.execute();
					  
					  
					  
				  }
				  
			  }}else{
				  
					pst.setInt(3,-1);
					pst.setInt(4, -1);
					pst.setInt(5, -1);
					pst.setInt(6, -1);
					pst.setInt(7, -1);
					pst.execute();
			  }
			  
			  
		  }
		  else{
			  if(tipoControllo==4){// se si sta eseguendo un tipo controllo ispezione
				 
				 
				  pst.setInt(8, -1);
				  pst.setInt(2, -1);
				  pst.setInt(3, -1);
				  pst.setInt(4, -1);
				  pst.setInt(5, tipoIspezione);
				  if(tipoIspezione==2){ // tipo ispe<ione scelta e' in monitoraggio
					
					 if(s[0]!=null)
					 {
					pst.setInt(6,Integer.parseInt(s[0]));
					pst.setInt(7, -1);
					pst.execute();  
					 }
					
					  
					  
				  }
				  else
				  {
					  if(tipoIspezione==4 || tipoIspezione==6 || tipoIspezione==9 || tipoIspezione==11){ // se il tipo ispezione e' non in monitoraggioo
						
						  if(s!=null){
						  for(int i=0;i<s.length;i++){
							  if(s[i]!=null)
							  {
							  int idispezione=Integer.parseInt(s[i]);
							pst.setInt(6,-1);
							pst.setInt(7, idispezione);
							  pst.execute();
							  }
						  }
						  }
						  
						  
					  }else{
						  
						 
							  pst.setInt(6,-1);
								pst.setInt(7, -1);
								  pst.execute();
							  
							  
						  
						  
					  }
					  
					  
					  
				  }
				  
				  
				  
			  }
		  }
		  
		 }else{
			  pst.setInt(2, -1);
			  pst.setInt(3, -1);
			  pst.setInt(4, -1);
			  pst.setInt(5, tipoIspezione);
			  
				pst.setInt(6,-1);
				pst.setInt(7, -1);
				pst.setInt(8, -1);
				pst.execute();  
				
		 }
		  
	  }
//public int getTipoPiano(Connection db,int idPiano) throws SQLException{
//		int livello = -1;
// 	
//		String sql="select distinct level from lookup_piano_monitoraggio where code = ?";
//		PreparedStatement pst=db.prepareStatement(sql);
//		pst.setInt(1, idPiano);
//		ResultSet rs=pst.executeQuery();
//		if(rs.next()){
//			
//			livello=rs.getInt("level");
//			
//		}
//	return livello;	
//	
//	
//}
 
 
 	public void getTipoAuditSelezionato(Connection db,int idControllo) throws SQLException{
 		
 	
 			String sql="select distinct code,description from lookup_tipo_audit where code in (select distinct tipo_audit from tipocontrolloufficialeimprese where idcontrollo=? )";
 	 		PreparedStatement pst=db.prepareStatement(sql);
 	 		pst.setInt(1, idControllo);
 			ResultSet rs=pst.executeQuery();
 			if(rs.next()){
 				
 				this.descriptionTipoAudit=rs.getString("description");
 				this.tipoAudit=rs.getInt("code");
 				
 			}
 			
 			
 			
 			String sql1="(select distinct audit_tipo from tipocontrolloufficialeimprese where idcontrollo=? )";
 	 		PreparedStatement pst1=db.prepareStatement(sql1);
 	 		pst1.setInt(1, idControllo);
 			ResultSet rs1=pst1.executeQuery();
 			if(rs1.next()){
 				
 				
 				this.auditTipo=rs1.getInt("audit_tipo");
 				
 			}
 			
 		
 		
 	}
 	public void updateNonConformita(Connection db,String[] nc_formali,String[] nc_significative,String[] nc_gravi ) throws SQLException{
		String sql="delete from salvataggio_nonconformita where idticket="+id;
		PreparedStatement pst=db.prepareStatement(sql);
		pst.execute();
		this.insertNonConformita(db, nc_formali, nc_significative, nc_gravi);
		
		
	}
 	
 	public void deleteTipiControlli(Connection db,int idControllo)throws SQLException{
 		
 		
 		String sql="delete from tipocontrolloufficialeimprese where idcontrollo=?";
 		PreparedStatement pst=db.prepareStatement(sql);
 		pst.setInt(1, idControllo);
 		pst.execute();
 		
 	}
 	
 	
 	public int update(Connection db,String[] s, int tipologia) throws SQLException{
 		int res=0;
 		int i=0;
 		String update="update ticket set contributi= ? ,ncrilevate= ?, codice_allerta= ? ";
 		
 		String updateAllarmeRabido = "" ;
 		
 			updateAllarmeRabido += ",distribuzione_partita = "+distribuzionePartita+" ,destinazione_distribuzione = "+destinazioneDistribuzione+",id_file_allegato = "+idFileAllegato;
 		
 		
 		updateAllarmeRabido += ",comunicazione_rischio = "+comunicazioneRischio+", procedura_richiamo = "+proceduraRichiamo+",procedura_ritiro = "+proceduraRitiro;
 		
 		updateAllarmeRabido += ",motivo_procedura_richiamo = ?";
 		updateAllarmeRabido += ",allerta_notes = ?";
 		
 		if(comunicazioneRischio==true)
	      {
 			updateAllarmeRabido += ",note_rischio = ?";
	      }
	     
	      
	      if(esitoControllo!=-1)
	      {
	    	  updateAllarmeRabido += ",esito_controllo = "+esitoControllo;
	      }
	      
	      if(esitoControllo==7)
	      {
	    	  
	    	  updateAllarmeRabido += ",data_ddt="+dataddt+",num_ddt="+numDDt;
	    	 
	      }
	      if(esitoControllo==8)
	      {
	    	  
	    	  updateAllarmeRabido += ",quantita_partita='"+quantitaPartita+"',unita_misura_text='"+unitaMisura+"'";
	    	 
	      }
	      if(esitoControllo==10 || esitoControllo==11)
	      {
	    	  
	    	  updateAllarmeRabido += ",quantita_partita_bloccata='"+quantitaBloccata+"',unita_misura_text='"+unitaMisura+"'";
		    	 
	    	 
	      }
	      
	      
	      if(azioneArticolo!=-1)
	      {
	    	  updateAllarmeRabido += ",articolo_azioni="+azioneArticolo;
	    	 
	      }
 		
	      update = update+updateAllarmeRabido+" where ticketid="+id;
 		
 		PreparedStatement pst=db.prepareStatement(update);
 		pst.setDouble(++i, contributi);
 		pst.setBoolean(++i, ncrilevate);
 		pst.setString(++i, codiceAllerta);
 		pst.setString(++i, motivoProceduraRichiamo);
 		pst.setString(++i, allertaNotes);
 		if(comunicazioneRischio==true)
	      {
 		pst.setString(++i, noteRischio);
	      }
 		pst.execute();
 		
 		res=super.update(db);
 		this.deleteTipiControlli(db, this.id);

 		this.insertTipocontrollo(db, this.tipoCampione, s, tipologia);
 		return res;
 		
 	}
 	

 	public void getTipoIspezioneSelezionato(Connection db,int idControllo) throws SQLException{
 		
 	
 			String sql="select distinct code,description from lookup_tipo_ispezione where code in (select distinct tipoispezione from tipocontrolloufficialeimprese where idcontrollo=? )";
 	 		PreparedStatement pst=db.prepareStatement(sql);
 	 		pst.setInt(1, idControllo);
 			ResultSet rs=pst.executeQuery();
 			if(rs.next()){
 				
 				this.descriptionTipoIspezione=rs.getString("description");
 				this.tipoIspezione=rs.getInt("code");
 				
 			}
 			
 		
 		
 	}
 	
 	
 	public void getTipoIspezioneSelezionato2(Connection db,int idIspezione) throws SQLException{
 		
 	 	
			String sql="select distinct description from lookup_tipo_ispezione where code ="+idIspezione;
	 		PreparedStatement pst=db.prepareStatement(sql);
	 		
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				
				this.descriptionTipoIspezione=rs.getString("description");
				
				
			}
			
		
		
	}
 	
	public void setPianoMonitoraggiSelezionato(Connection db,int idControllo) throws SQLException{
 		
 		
 		String sql ="select code, description from lookup_piano_monitoraggio where code in (select pianomonitoraggio from tipocontrolloufficialeimprese where idcontrollo=?)";
 		PreparedStatement pst=db.prepareStatement(sql);
 		pst.setInt(1, idControllo);
 		ResultSet rst=pst.executeQuery();
 		while(rst.next()){
 			
 			this.lisaElementipianoMonitoraggio_ispezioni.put(rst.getInt(1),rst.getString(2));
 			
 			pianoMonitoraggio=rst.getInt(1);
 			
 		}
 		
 		
 		
 		
 	}
	
	
public void setPianoispezioniSelezionate(Connection db,int idControllo) throws SQLException{
 		
 		
 		String sql ="select code, description from lookup_ispezione where code in (select ispezione from tipocontrolloufficialeimprese where idcontrollo=?)";
 		PreparedStatement pst=db.prepareStatement(sql);
 		pst.setInt(1, idControllo);
 		ResultSet rst=pst.executeQuery();
 		while(rst.next()){
 			
 			this.lisaElementipianoMonitoraggio_ispezioni.put(rst.getInt(1),rst.getString(2));
 			
 		}
 		
 		
 		
 		
 	}
 	
 
 
 	public void setBpiSelezionati(Connection db,int idControllo) throws SQLException{
 		
 		
 		String sql ="select code, description from lookup_bpi where code in (select bpi from tipocontrolloufficialeimprese where idcontrollo=?)";
 		PreparedStatement pst=db.prepareStatement(sql);
 		pst.setInt(1, idControllo);
 		ResultSet rst=pst.executeQuery();
 		while(rst.next()){
 			
 			this.lisaElementibpiOhaccp.put(rst.getInt(1),rst.getString(2));
 			
 		}
 		
 		
 		
 		
 	}
 	

 	
 	
 	
public void setHaccpSelezionati(Connection db,int idControllo) throws SQLException{
 		
 		
 		String sql ="select code,description from lookup_haccp where code in (select haccp from tipocontrolloufficialeimprese where idcontrollo=?)";
 		PreparedStatement pst=db.prepareStatement(sql);
 		pst.setInt(1, idControllo);
 		ResultSet rst=pst.executeQuery();
 		while(rst.next()){
 			
 			this.lisaElementibpiOhaccp.put(rst.getInt(1) , rst.getString(2));
 			
 		}
 				
 	}
 

public boolean insertNonConformita(Connection db,String[] ncFormali,String[] ncSignificative,String[] ncGravi ) throws SQLException {
	  String insert="insert into salvataggio_nonconformita (idticket,nc_formali,nc_significative,nc_gravi) values (?,?,?,?)";
	  PreparedStatement pst=db.prepareStatement(insert);
	  pst.setInt(1, id);
	  
	  if(ncFormali!=null){
	  
	  for(int i=0;i<ncFormali.length;i++){
		  int nc_id=Integer.parseInt(ncFormali[i]);
		  pst.setInt(2, nc_id);
		  pst.setInt(3, -1);
		  pst.setInt(4, -1);
		  pst.execute();
	  }}
	  if(ncSignificative!=null){
	  for(int i=0;i<ncSignificative.length;i++){
		  int nc_id=Integer.parseInt(ncSignificative[i]);
		  pst.setInt(2, -1);
		  pst.setInt(3, nc_id);
		  pst.setInt(4, -1);
		  pst.execute();
	  }}
	  if(ncGravi!=null){
	  for(int i=0;i<ncGravi.length;i++){
		  int nc_id=Integer.parseInt(ncGravi[i]);
		  pst.setInt(2, -1);
		  pst.setInt(3, -1);
		  pst.setInt(4, nc_id);
		  pst.execute();
	  }
}

	  return true;
}


public boolean updateCategoria(Connection db) throws SQLException{
	
	PreparedStatement pst=db.prepareStatement("update ticket set categoria_rischio = ? , punteggio = ?,data_prossimo_controllo =?, isAggiornata_categoria=true where ticketid=? ");
	pst.setInt(1, categoriaRischio);
	pst.setInt(2, punteggio);
	pst.setTimestamp(3, dataProssimoControllo);
	pst.setInt(4, id);
	pst.execute();
	
	return true;
	
}

	  public boolean insert(Connection db,String[] s, int tipologia,ActionContext context) throws SQLException {

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
			
			
			
			sql.append(
		          "INSERT INTO ticket (contact_id, problem, pri_code, " +
		          "department_code, cat_code, scode, org_id, link_contract_id, " +
		          "link_asset_id, expectation, product_id, customer_product_id, " +
		          "key_count, status_id, trashed_date, user_group_id, cause_id, " +
		          "resolution_id, defect_id, escalation_level, resolvable, " +
		          "resolvedby, resolvedby_department_code, state_id, site_id, ");
		      
		      sql.append("note_altro, ");
		      sql.append("isAggiornata_categoria, ");
		      
		      
		      if(distribuzionePartita!=-1)
		      {
		    	  sql.append("distribuzione_partita,");
		      }
		      if(destinazioneDistribuzione!=-1)
		      {
		    	  sql.append("destinazione_distribuzione,");
		      }
		      
		      sql.append("comunicazione_rischio,");
		      
		      if(comunicazioneRischio==true)
		      {
		    	  sql.append("note_rischio,");
		      }
		      sql.append("procedura_ritiro,");
		      
		      sql.append("procedura_richiamo,");
		      sql.append("motivo_procedura_richiamo,");
		      sql.append("allerta_notes,");
		      
		      if(esitoControllo!=-1)
		      {
		    	  sql.append("esito_controllo,");
		      }
		      
		      if(esitoControllo==7)
		      {
		    	  sql.append("data_ddt,num_ddt,");
		      }
		      if(esitoControllo==8)
		      {
		    	  sql.append("quantita_partita,unita_misura_text,");
		      }
		      if(esitoControllo==10 || esitoControllo==11)
		      {
		    	  sql.append("quantita_partita_bloccata,unita_misura_text,");
		      }
		      
		      if(idFileAllegato!=null)
		      {
		    	  sql.append("id_file_allegato,");
		      }
		      if(azioneArticolo!=-1)
		      {
		    	  sql.append("articolo_azioni,");
		      }
		      
		      
		      
		      
		      
		        sql.append("ticketid, ");
		      
		      if (entered != null) {
		        sql.append("entered, ");
		      }
		      if (modified != null) {
		        sql.append("modified, ");
		      }
		      sql.append("tipo_richiesta, custom_data, enteredBy, modifiedBy, " +
		      		"tipologia, provvedimenti_prescrittivi, sanzioni_amministrative, sanzioni_penali ");
		      if (dataAccettazione != null) {
			        sql.append(", data_accettazione ");
			      }
		      if (assignedDate != null) {
			        sql.append(", assigned_date ");
		      }
		      if (dataFineControllo != null) {
			        sql.append(", data_fine_controllo ");
			      }
		      if (dataProssimoControllo != null) {
			        sql.append(", data_prossimo_controllo ");
			      }
		      if (dataAccettazioneTimeZone != null) {
			        sql.append(",data_accettazione_timezone ");
			      }
		      if ( nucleoIspettivo > -1) {
			        sql.append(", nucleo_ispettivo");
			      }
		      if ( nucleoIspettivoDue > -1) {
			        sql.append(", nucleo_ispettivo_due");
			      }
		      if ( nucleoIspettivoTre > -1) {
			        sql.append(", nucleo_ispettivo_tre");
			      }
		      if (componenteNucleo != null) {
			        sql.append(", componente_nucleo");
			      }
		      if (componenteNucleoDue != null) {
			        sql.append(", componente_nucleo_due");
			      }
		      if (componenteNucleoTre != null) {
			        sql.append(", componente_nucleo_tre");
			      }
		      if ( nucleoIspettivoQuattro > -1) {
			        sql.append(", nucleo_ispettivo_quattro");
			      }
		      if ( nucleoIspettivoCinque > -1) {
			        sql.append(", nucleo_ispettivo_cinque");
			      }
		      if ( nucleoIspettivoSei > -1) {
			        sql.append(", nucleo_ispettivo_sei");
			      }
		      if ( nucleoIspettivoSette > -1) {
			        sql.append(", nucleo_ispettivo_sette");
			      }
		      if ( nucleoIspettivoOtto > -1) {
			        sql.append(", nucleo_ispettivo_otto");
			      }
		      if ( nucleoIspettivoNove > -1) {
			        sql.append(", nucleo_ispettivo_nove");
			      }
		      if ( nucleoIspettivoDieci > -1) {
			        sql.append(", nucleo_ispettivo_dieci");
			      }
		      if (componenteNucleoQuattro != null) {
			        sql.append(", componente_nucleo_quattro");
			      }
		      if (componenteNucleoCinque != null) {
			        sql.append(", componente_nucleo_cinque");
			      }
		      if (componenteNucleoSei != null) {
			        sql.append(", componente_nucleo_sei");
			      }
		      if (componenteNucleoSette != null) {
			        sql.append(", componente_nucleo_sette");
			      }
		      if (componenteNucleoOtto != null) {
			        sql.append(", componente_nucleo_otto");
			      }
		      if (componenteNucleoNove != null) {
			        sql.append(", componente_nucleo_nove");
			      }
		      if (componenteNucleoDieci != null) {
			        sql.append(", componente_nucleo_dieci");
			      }
		      if (followUp != null) {
			        sql.append(", follow_up");
			      }
		      
		      if (codiceAllerta != null) {
			        sql.append(", codice_allerta");
			      }
		      
		      if (nonConformitaFormali != null) {
			        sql.append(",nonconformitaformali");
			      }
		      
		      
		      if (nonConformitaSignificative != null) {
			        sql.append(",nonconformitasignificative");
			      }
		      
		      
		      if (nonConformitaGravi != null) {
			        sql.append(" ,nonconformitagravi");
			      }
		      
		      
		      if (puntiFormali != null) {
			        sql.append(", puntiformali");
			      }
		      
		      
		      if (puntiSignificativi != null) {
			        sql.append(", puntisignificativi");
			      }
		      
		      
		      if (puntiGravi != null) {
			        sql.append(", puntigravi");
			      }
		      
		      if (followupDate != null) {
			        sql.append(", followupdate");
			      }
		      
		      if (followupDateTimeZone != null) {
			        sql.append(", followupdate_timezone");
			      }
		      
		      
		      
		      if (descrizioneControllo != null) {
			        sql.append(", descrizione");
			      }
		      if (descrizioneAnimali != null) {
			        sql.append(", descrizioneanimali");
			      }
		      
		      if (luogoControllo != null) {
			        sql.append(", luogocontrollo");
			      }
		      
		      if (numeroMezzi != -1) {
			        sql.append(", numeromezzi");
			      }
		      
		      if (mezziIspezionati != -1) {
			        sql.append(", mezziispezionati");
			      }
		      
		      if (anomaliIspezionat != -1) {
			        sql.append(", animali");
			      }
		      if (idMacchinetta != -1) {
			        sql.append(", id_macchinetta");
			      }
		      
		      if (categoriaTrasportata != -1) {
			        sql.append(", specietrasportata");
			      }
		      if (specieA != -1) {
			        sql.append(", animalitrasportati");
			      }
		    	 
		    	 sql.append(", contributi");
		     
		    	 sql.append(", ncrilevate");
		      
		      sql.append(", inserisci_continua");
		      sql.append(", id_controllo_ufficiale");
		      if(latitude!=0){
		    	  sql.append(", latitudine");
		      }
		      if(longitude!=0){
		    	  sql.append(", longitudine");
		      }

		      sql.append(")");
		      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		      sql.append("?, ?, ?, ");
		      sql.append("?,");
		      sql.append("?,");
		     // sql.append("?,? , ");
		      
		      if(distribuzionePartita!=-1)
		      {
		    	  sql.append("?,");
		      }
		      if(destinazioneDistribuzione!=-1)
		      {
		    	  sql.append("?,");
		      }
		      
		      sql.append("?,");
		      
		      if(comunicazioneRischio==true)
		      {
		    	  sql.append("?,");
		      }
		      sql.append("?,?");
		      
		      sql.append("?,? ");
		      
		      if(esitoControllo!=-1)
		      {
		    	  sql.append("?,");
		      }
		      
		      if(esitoControllo==7)
		      {
		    	  sql.append("?,?,");
		      }
		      if(esitoControllo==8)
		      {
		    	  sql.append("?,?,");
		      }
		      if(esitoControllo==10 || esitoControllo==11)
		      {
		    	  sql.append("?,?,");
		      }
		      
		      if(idFileAllegato!=null)
		      {
		    	  sql.append("?,");
		      }
		      if(azioneArticolo!=-1)
		      {
		    	  sql.append("?,");
		      }
		      
		      
		      
		        sql.append( DatabaseUtils.getNextIntSql(  "ticket","ticketid",livello) +",");
		      if (entered != null) {
		        sql.append("?, ");
		      }
		      if (modified != null) {
		        sql.append("?, ");
		      }
		      sql.append("?, ?, ?, ?, " +
		      		     "3, ?, ?, ? ");
		      if (dataAccettazione != null) {
			        sql.append(", ? ");
			      }
		      
		      if (assignedDate != null) {
			        sql.append(", ? ");
		      }
		      if (dataFineControllo != null) {
			        sql.append(", ? ");
			      }
		      if (dataProssimoControllo != null) {
			        sql.append(", ? ");
			      }
			  if (dataAccettazioneTimeZone != null) {
			        sql.append(", ? ");
			      }
			  if (nucleoIspettivo > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoDue > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoTre > -1) {
			        sql.append(", ?");
			      }
		      if (componenteNucleo != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoDue != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoTre != null) {
			        sql.append(", ? ");
			      }
		      if (nucleoIspettivoQuattro > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoCinque > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoSei > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoSette > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoOtto > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoNove > -1) {
			        sql.append(", ?");
			      }
		      if (nucleoIspettivoDieci > -1) {
			        sql.append(", ?");
			      }
		      if (componenteNucleoQuattro != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoCinque != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoSei != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoSette != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoOtto != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoNove != null) {
			        sql.append(", ? ");
			      }
		      if (componenteNucleoDieci != null) {
			        sql.append(", ? ");
			      }
		      if (followUp != null) {
			        sql.append(", ? ");
			      }
		      if (codiceAllerta != null) {
		    	  sql.append(", ? ");
			      }
		      
		      
		      if (nonConformitaFormali != null) {
			        sql.append(",?");
			      }
		      
		      
		      if (nonConformitaSignificative != null) {
			        sql.append(" ,?");
			      }
		      
		      
		      if (nonConformitaGravi != null) {
			        sql.append(" ,?");
			      }
		      
		      
		      if (puntiFormali != null) {
			        sql.append(",? ");
			      }
		      
		      
		      if (puntiSignificativi != null) {
			        sql.append(" ,?");
			      }
		      
		      
		      if (puntiGravi != null) {
			        sql.append(", ?");
			      }
		      
		      if (followupDate != null) {
		    	 
		    	  sql.append(", ?");
				     
			      }
		      
		      
		      if (followupDateTimeZone != null) {
		    	  sql.append(", ?");
			      }
		      
		      if (descrizioneControllo != null) {
		    	  sql.append(", ?");
			      }
		      if (descrizioneAnimali != null) {
		    	  sql.append(", ?");
			      }
		      
		      if (luogoControllo != null) {
		    	  sql.append(", ?");
			      }
		      
		      if (numeroMezzi != -1) {
		    	  sql.append(", ?");
			      }
		      
		      if (mezziIspezionati != -1) {
		    	  sql.append(", ?");
			      }
		      
		      if (anomaliIspezionat != -1) {
		    	  sql.append(", ?");
			      }
		      if (idMacchinetta != -1) {
			        sql.append(",?");
			      }
		      if (categoriaTrasportata != -1) {
			        sql.append(",?");
			      }
		      if (specieA != -1) {
			        sql.append(",?");
			      }
		      sql.append(", ?");
		      sql.append(", ?");
		      
		      if(inserisciContinua){
		           sql.append(", true");
		      }else{
		    	  sql.append(", false");
		      }
		      sql.append(", trim(to_char( "+id+", '"+DatabaseUtils.getPaddedFromId(id)+"' ))");
		      if(latitude!=0){
		    	  sql.append(", ?");
		      }
		      if(longitude!=0){
		    	  sql.append(", ?");
		      }
	  
			  sql.append(") RETURNING ticketid");
			
			  
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      DatabaseUtils.setInt(pst, ++i, this.getContactId());
		      pst.setString(++i, this.getProblem());
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);

		      DatabaseUtils.setInt(pst, ++i, orgId);
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
		      pst.setString(++i, noteAltrodiSistema);
		      pst.setBoolean(++i, false);
		      if(distribuzionePartita!=-1)
		      {
		    	  pst.setInt(++i, distribuzionePartita);
		      }
		      if(destinazioneDistribuzione!=-1)
		      {
		    	  pst.setInt(++i, destinazioneDistribuzione);
		      }
		      
		      pst.setBoolean(++i, comunicazioneRischio);
		      
		      if(comunicazioneRischio==true)
		      {
		    	  pst.setString(++i, noteRischio);
		      }
		      pst.setInt(++i, proceduraRitiro);
		      
		      pst.setInt(++i, proceduraRichiamo);
		      pst.setString(++i, motivoProceduraRichiamo);
		      pst.setString(++i, allertaNotes);
		      
		      if(esitoControllo!=-1)
		      {
		    	  pst.setInt(++i, esitoControllo);
		      }
		      
		      if(esitoControllo==7)
		      {
		    	  pst.setTimestamp(++i, dataddt);
		    	  pst.setInt(++i,numDDt);
		      }
		      if(esitoControllo==8)
		      {
		    	  pst.setString(++i, quantitaPartita);
		    	  pst.setString(++i, unitaMisura);
		      }
		      if(esitoControllo==10 || esitoControllo==11)
		      {
		    	  pst.setString(++i, quantitaBloccata);
		    	  pst.setString(++i, unitaMisura);
		      }
		      
		      if(idFileAllegato!=null)
		      {
		    	  pst.setInt(++i, idFileAllegato);
		      }
		      if(azioneArticolo!=-1)
		      {
		    	  pst.setInt(++i, azioneArticolo);
		      }
		      
		     
		      if (entered != null) {
		        pst.setTimestamp(++i, entered);
		      }
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      } 
		      
		      /**
		       * 
		       */
		      pst.setString( ++i, this.getTipo_richiesta() );
		     
		      pst.setString( ++i, this.getPippo() );
		      
		      pst.setInt(++i, this.getEnteredBy());
		      pst.setInt(++i, this.getModifiedBy());
		      DatabaseUtils.setInt(pst, ++i, tipoCampione);
		      DatabaseUtils.setInt(pst, ++i, esitoCampione);
		      DatabaseUtils.setInt(pst, ++i, destinatarioCampione);
		      if (dataAccettazione != null) {
			        pst.setTimestamp(++i, dataAccettazione);
			      }
		      if (assignedDate != null) {
		          pst.setTimestamp(++i, assignedDate);
		      }
		      if (dataFineControllo != null) {
			        pst.setTimestamp(++i, dataFineControllo);
			      }
		      if (dataProssimoControllo != null) {
			        pst.setTimestamp(++i, dataProssimoControllo);
			      }
		      if (dataAccettazioneTimeZone != null) {
			        pst.setString(++i, dataAccettazioneTimeZone);
			      }
		      if(nucleoIspettivo > -1)
			      pst.setInt(++i , nucleoIspettivo);
	         if(nucleoIspettivoDue > -1)
			      pst.setInt(++i , nucleoIspettivoDue);
	         if(nucleoIspettivoTre > -1)
			       pst.setInt(++i , nucleoIspettivoTre);
	         if (componenteNucleo != null) {
		         pst.setString(++i, componenteNucleo);
		       }
	        if (componenteNucleoDue != null) {
		        pst.setString(++i, componenteNucleoDue);
		      }
	        if (componenteNucleoTre != null) {
		        pst.setString(++i, componenteNucleoTre);
		      }
	        if(nucleoIspettivoQuattro > -1)
			      pst.setInt(++i , nucleoIspettivoQuattro);
	        if(nucleoIspettivoCinque > -1)
			      pst.setInt(++i , nucleoIspettivoCinque);     
	        if(nucleoIspettivoSei > -1)
			      pst.setInt(++i , nucleoIspettivoSei);
	        if(nucleoIspettivoSette > -1)
			      pst.setInt(++i , nucleoIspettivoSette);
	        if(nucleoIspettivoOtto > -1)
			      pst.setInt(++i , nucleoIspettivoOtto);
	        if(nucleoIspettivoNove > -1)
			      pst.setInt(++i , nucleoIspettivoNove);
	        if(nucleoIspettivoDieci > -1)
			      pst.setInt(++i , nucleoIspettivoDieci);
	        if (componenteNucleoQuattro != null) {
		        pst.setString(++i, componenteNucleoQuattro);
		      }
	        if (componenteNucleoCinque != null) {
		        pst.setString(++i, componenteNucleoCinque);
		      }
	        if (componenteNucleoSei != null) {
		        pst.setString(++i, componenteNucleoSei);
		      }
	        if (componenteNucleoSette != null) {
		        pst.setString(++i, componenteNucleoSette);
		      }
	        if (componenteNucleoOtto != null) {
		        pst.setString(++i, componenteNucleoOtto);
		      }
	        if (componenteNucleoNove != null) {
		        pst.setString(++i, componenteNucleoNove);
		      }
	        if (componenteNucleoDieci != null) {
		        pst.setString(++i, componenteNucleoDieci);
		      }
	        if (followUp != null) {
	        	pst.setString(++i, followUp);
		      }
	        if (codiceAllerta != null) {
	        	pst.setString(++i, codiceAllerta);
			      }
	        if (nonConformitaFormali != null) {
	        	pst.setString(++i, nonConformitaFormali);
		      }
	      
	      
	      if (nonConformitaSignificative != null) {
	    	  pst.setString(++i,nonConformitaSignificative);
		      }
	      
	      
	      if (nonConformitaGravi != null) {
	    	  pst.setString(++i, nonConformitaGravi);
		      }
	      
	      
	      if (puntiFormali != null) {
	    	  pst.setString(++i, puntiFormali);
		      }
	      
	      
	      if (puntiSignificativi != null) {
	    	  pst.setString(++i, puntiSignificativi);
		      }
	      
	      
	      if (puntiGravi != null) {
	    	  pst.setString(++i, puntiGravi);
		      }
	      
	      if (followupDate != null) {
	    	  DatabaseUtils.setTimestamp(pst, ++i, followupDate);
	    	  
	    	  
	      }
	      if (followupDateTimeZone != null) {
	    	  pst.setString(++i, followupDateTimeZone);
	    	  
	    	  
	      }
	      
	      if (descrizioneControllo != null) {
	    	  pst.setString(++i, descrizioneControllo);
		      }
	      if (descrizioneAnimali != null) {
	    	  pst.setString(++i, descrizioneAnimali);
		      }
	      
	      if (luogoControllo != null) {
	    	  pst.setString(++i, luogoControllo);
		      }
	      
	      if (numeroMezzi != -1) {
	    	  pst.setInt(++i, numeroMezzi);
	    	  }
	      
	      if (mezziIspezionati != -1) {
	    	  pst.setInt(++i, mezziIspezionati);
		      }
	      
	      if (anomaliIspezionat != -1) {
	    	 pst.setInt(++i, anomaliIspezionat);
		      }
	      
	      if (idMacchinetta != -1) {
	    	  pst.setInt(++i, idMacchinetta);
		      }
	      if (categoriaTrasportata != -1) {
	    	  pst.setInt(++i, categoriaTrasportata);
		      }
	      if (specieA != -1) {
	    	  pst.setInt(++i, specieA);
		      }
	      pst.setDouble(++i, contributi);
	      pst.setBoolean(++i, ncrilevate);
	      
	      if(latitude!=0)
	      {
	    	  pst.setDouble(++i,latitude);
	    
	      }
	      if(longitude != 0)
	      {
	    		 pst.setDouble(++i,longitude);
	      }	
	      
	      //pst.setString(++i, this.getPaddedId());
		      ResultSet r = pst.executeQuery();
		      if ( r.next())
		    	  this.id = r.getInt("ticketid");
		      pst.close();
		      
		      
		      
		      
		      //Update the rest of the fields
		     // this.update(db, true);
		      this.insertTipocontrollo(db, tipoCampione, s, tipologia);
		      
		    
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

	  protected void buildRecord(ResultSet rs) throws SQLException {
		    //ticket table
		    this.setId(rs.getInt("ticketid"));
		    
		    
		    categoriaTrasportata=rs.getInt("specietrasportata");
		    specieA=rs.getInt("animalitrasportati");
		    idMacchinetta=rs.getInt("id_macchinetta");
		    nonConformitaFormali = rs.getString("nonconformitaformali");
		    nonConformitaSignificative=rs.getString("nonconformitasignificative");
		    nonConformitaGravi=rs.getString("nonconformitagravi");
		    puntiFormali=rs.getString("puntiformali");
		    puntiSignificativi=rs.getString("puntisignificativi");
		    puntiGravi=rs.getString("puntigravi");
		   
		    punteggio=rs.getInt("punteggio");
		    categoriaRischio = rs.getInt("categoria_rischio");
		    contributi=rs.getDouble("contributi");
		    noteAltrodiSistema=rs.getString("note_altro");
		    followupDate=rs.getTimestamp("followupdate");
		    
		    followupDateTimeZone=rs.getString("followupdate_timezone");
		    categoriaisAggiornata=rs.getBoolean("isAggiornata_categoria");
		    
		    ncrilevate=rs.getBoolean("ncrilevate");
		    descrizioneControllo=rs.getString("descrizione");
		    descrizioneAnimali=rs.getString("descrizioneanimali");
		    luogoControllo=rs.getString("luogocontrollo");
		    numeroMezzi=rs.getInt("numeromezzi");
		    mezziIspezionati=rs.getInt("mezziispezionati");
		    anomaliIspezionat=rs.getInt("animali");
		    
		    orgId = DatabaseUtils.getInt(rs, "org_id");
		    contactId = DatabaseUtils.getInt(rs, "contact_id");
		    problem = rs.getString("problem");
		    entered = rs.getTimestamp("entered");
		    enteredBy = rs.getInt("enteredby");
		    modified = rs.getTimestamp("modified");
		    modifiedBy = rs.getInt("modifiedby");
		    closed = rs.getTimestamp("closed");
		   
		    codiceAllerta=rs.getString("codice_allerta");
		    
		    
		    location = rs.getString("location");
		    assignedDate = rs.getTimestamp("assigned_date");
		    //aggiungere
		    //assignedDate = rs.getTimestamp("data_fine_controllo");
		   // dataFineControllo=rs.getTimestamp("data_fine_controllo");
		    estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		    resolutionDate = rs.getTimestamp("resolution_date");
		    cause = rs.getString("cause");
		    assetId = DatabaseUtils.getInt(rs, "link_asset_id");
		    estimatedResolutionDateTimeZone = rs.getString(
		        "est_resolution_date_timezone");
		    assignedDateTimeZone = rs.getString("assigned_date_timezone");
		    followupDateTimeZone=rs.getString("followupdate_timezone");
		    
		    resolutionDateTimeZone = rs.getString("resolution_date_timezone");
		    statusId = DatabaseUtils.getInt(rs, "status_id");
		    trashedDate = rs.getTimestamp("trashed_date");

		    causeId = DatabaseUtils.getInt(rs, "cause_id");
		    resolutionId = DatabaseUtils.getInt(rs, "resolution_id");
		   
		    stateId = DatabaseUtils.getInt(rs, "state_id");
		    siteId = DatabaseUtils.getInt(rs, "site_id");
		    tipo_richiesta = rs.getString( "tipo_richiesta" );
		    pippo = rs.getString( "custom_data" );
		    tipologia = rs.getInt( "tipologia" );
		    tipoCampione = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		    esitoCampione = DatabaseUtils.getInt(rs, "sanzioni_amministrative");
		    destinatarioCampione = DatabaseUtils.getInt(rs, "sanzioni_penali");
		    dataAccettazione = rs.getTimestamp( "data_accettazione" );
		    dataFineControllo = rs.getTimestamp("data_fine_controllo");
		    dataProssimoControllo = rs.getTimestamp("data_prossimo_controllo");
		    		   
		    dataAccettazioneTimeZone = rs.getString("data_accettazione_timezone");
		    //organization table
		    companyName = rs.getString("orgname");
		    orgSiteId = DatabaseUtils.getInt(rs, "orgsiteid");
 
	
		    //asset table
		    assetSerialNumber = rs.getString("serialnumber");
		    assetManufacturerCode = DatabaseUtils.getInt(rs, "assetmanufacturercode");
		    assetVendorCode = DatabaseUtils.getInt(rs, "assetvendorcode");
		    assetModelVersion = rs.getString("modelversion");
		    assetLocation = rs.getString("assetlocation");
		    assetOnsiteResponseModel = DatabaseUtils.getInt(
		        rs, "assetonsiteservicemodel");

		
		    
		    nucleoIspettivo = DatabaseUtils.getInt(rs, "nucleo_ispettivo");
		    nucleoIspettivoDue = DatabaseUtils.getInt(rs, "nucleo_ispettivo_due");
		    nucleoIspettivoTre = DatabaseUtils.getInt(rs, "nucleo_ispettivo_tre");
		    componenteNucleo = rs.getString("componente_nucleo");
		    componenteNucleoDue = rs.getString("componente_nucleo_due");
		    componenteNucleoTre = rs.getString("componente_nucleo_tre");
		    nucleoIspettivoQuattro = DatabaseUtils.getInt(rs, "nucleo_ispettivo_quattro");
		    nucleoIspettivoCinque = DatabaseUtils.getInt(rs, "nucleo_ispettivo_cinque");
		    nucleoIspettivoSei = DatabaseUtils.getInt(rs, "nucleo_ispettivo_sei");
		    nucleoIspettivoSette = DatabaseUtils.getInt(rs, "nucleo_ispettivo_sette");
		    nucleoIspettivoOtto = DatabaseUtils.getInt(rs, "nucleo_ispettivo_otto");
		    nucleoIspettivoNove = DatabaseUtils.getInt(rs, "nucleo_ispettivo_nove");
		    nucleoIspettivoDieci = DatabaseUtils.getInt(rs, "nucleo_ispettivo_dieci");
		    componenteNucleoQuattro = rs.getString("componente_nucleo_quattro");
		    componenteNucleoCinque = rs.getString("componente_nucleo_cinque");
		    componenteNucleoSei = rs.getString("componente_nucleo_sei");
		    componenteNucleoSette = rs.getString("componente_nucleo_sette");
		    componenteNucleoOtto = rs.getString("componente_nucleo_otto");
		    componenteNucleoNove = rs.getString("componente_nucleo_nove");
		    componenteNucleoDieci = rs.getString("componente_nucleo_dieci");
		    inserisciContinua = rs.getBoolean("inserisci_continua");
		    idControlloUfficiale = rs.getString("id_controllo_ufficiale");
		    followUp = rs.getString("follow_up");
		    //tipoAudit = rs.getInt("tipo_audit");
		   // tipoIspezione = rs.getInt("tipoispezione");
		    latitude=rs.getDouble("latitudine");
		    longitude=rs.getDouble("longitudine");
		    distribuzionePartita = rs.getInt("distribuzione_partita");
		    destinazioneDistribuzione = rs.getInt("destinazione_distribuzione");
		   comunicazioneRischio =  rs.getBoolean("comunicazione_rischio");
		      
		      if(comunicazioneRischio==true)
		      {
		    	  noteRischio = rs.getString("note_rischio");
		      }
		      proceduraRitiro= rs.getInt("procedura_ritiro");
		      
		      proceduraRichiamo = rs.getInt("procedura_richiamo");
		      motivoProceduraRichiamo = rs.getString("motivo_procedura_richiamo");
		      allertaNotes = rs.getString("allerta_notes");
		    	  esitoControllo= rs.getInt("esito_controllo");
		      
		      
		      
		      if(esitoControllo==7)
		      {
		    	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    	  dataddt = rs.getTimestamp("data_ddt");
		    	  if(dataddt!=null)
		    	  {
		    		  numDDt = rs.getInt("num_ddt");
		    	  }
		      }
		      if(esitoControllo==8)
		      {
		    	  quantitaPartita = rs.getString("quantita_partita");
		    	  unitaMisura = rs.getString("unita_misura_text");
		      }
		      if(esitoControllo==10 || esitoControllo == 11)
		      {
		    	  quantitaBloccata = rs.getString("quantita_partita_bloccata");
		    	  unitaMisura = rs.getString("unita_misura_text");
		    	  
		      }
		      
		     
		    	 idFileAllegato = rs.getInt("id_file_allegato");
		    	 
		    	 if(idFileAllegato!=-1)
		    	 {
		    		 listaDistribuzioneAllegata = true ;
		    	 }
		      
		    	 azioneArticolo = rs.getInt("articolo_azioni");
		    
		    
		
		  }
	  
	 
	  
	  public int update(Connection db, boolean override) throws SQLException {
		    int resultCount = 0;
		    PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer();
		    sql.append(
		        "UPDATE ticket " +
		        "SET link_contract_id = ?, link_asset_id = ?, department_code = ?, " +
		        "pri_code = ?, scode = ?, " +
		        "cat_code = ?, assigned_to = ?, " +
		        "subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, " +
		        "source_code = ?, contact_id = ?, problem = ?, " +
		        "status_id = ?, trashed_date = ?, site_id = ? , ");
		    if (!override) {
		      sql.append(
		          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
		    }
		
		    
		    sql.append(" note_altro = ?, ");
		    sql.append(" isAggiornata_categoria = ?, ");
		    if (orgId != -1) {
		      sql.append(" org_id = ?, ");
		    }
		    
		    if(followupDate!=null){
		    	sql.append(" followupdate = ?, ");
		    	
		    }
		    if(followupDateTimeZone!=null){
		    	sql.append(" followupdate_timezone = ?, ");
		    	
		    }
		    
		    
		    if (nonConformitaFormali != null) {
		        sql.append(" nonconformitaformali= ?, ");
		      }
	      
	      
	      if (nonConformitaSignificative != null) {
		        sql.append(" nonconformitasignificative= ?, ");
		      }
	      
	      
	      if (nonConformitaGravi != null) {
		        sql.append(" nonconformitagravi= ?, ");
		      }
	      
	      
	      if (puntiFormali != null) {
		        sql.append(" puntiformali= ?, ");
		      }
	      
	      
	      if (puntiSignificativi != null) {
		        sql.append(" puntisignificativi= ?, ");
		      }
	      
	      
	      if (puntiGravi != null) {
		        sql.append(" puntigravi= ?, ");
		      }
	      
	      if (descrizioneControllo != null) {
		        sql.append("descrizione=? ,");
		      }
	      if (descrizioneAnimali != null) {
		        sql.append("descrizioneanimali=? ,");
		      }
	      if (luogoControllo != null) {
		        sql.append(" luogocontrollo =? ,");
		      }
	      
	      if (numeroMezzi != -1) {
		        sql.append("numeromezzi=? ,");
		      }
	      
	      if (mezziIspezionati != -1) {
		        sql.append(" mezziispezionati =? ,");
		      }
	      
	      if (anomaliIspezionat != -1) {
		        sql.append(" animali=? ,");
		      }
	      
	      if (categoriaTrasportata != -1) {
		        sql.append(" specietrasportata=? ,");
		      }
	      if (specieA != -1) {
		        sql.append(" animalitrasportati=? ,");
		      }
		    sql.append(
		        "solution = ?, custom_data = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
		        "est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
		        "cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
		        "user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +
		        "escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, data_accettazione = ?,data_fine_controllo = ? , data_prossimo_controllo = ? , data_accettazione_timezone = ?" +
		        ", nucleo_ispettivo = ?, nucleo_ispettivo_due = ?, nucleo_ispettivo_tre = ?, componente_nucleo = ?, componente_nucleo_due = ?, componente_nucleo_tre = ?, nucleo_ispettivo_quattro = ?, nucleo_ispettivo_cinque = ?, nucleo_ispettivo_sei = ?, nucleo_ispettivo_sette = ?, nucleo_ispettivo_otto = ?, nucleo_ispettivo_nove = ?, nucleo_ispettivo_dieci = ?,componente_nucleo_quattro = ?, componente_nucleo_cinque = ?, componente_nucleo_sei = ?, componente_nucleo_sette = ?, componente_nucleo_otto = ?, componente_nucleo_nove = ?, componente_nucleo_dieci = ?, punteggio = ?, follow_up = ? "+
		        "WHERE ticketid = ? AND tipologia = 3");
		   /* if (!override) {
		      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
		    }*/
		    int i = 0;
		    pst = db.prepareStatement(sql.toString());
	        pst.setNull(++i, java.sql.Types.INTEGER);
		    DatabaseUtils.setInt(pst, ++i, this.getAssetId());
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
		    if (!override) {
		      pst.setInt(++i, this.getModifiedBy());
		    }
		    
		    pst.setString(++i, noteAltrodiSistema);
		    pst.setBoolean(++i, categoriaisAggiornata);
		    if (orgId != -1) {
		      DatabaseUtils.setInt(pst, ++i, orgId);
		    }
		    
		    if(followupDate!=null){
		    	 pst.setTimestamp(++i, followupDate);
		    	
		    }
		    
		    if(followupDateTimeZone!=null){
		    	 pst.setString(++i,followupDateTimeZone);
		    	
		    }
		    
		    if (nonConformitaFormali != null) {
	        	pst.setString(++i, nonConformitaFormali);
		      }
	      
	      
	      if (nonConformitaSignificative != null) {
	    	  pst.setString(++i,nonConformitaSignificative);
		      }
	      
	      
	      if (nonConformitaGravi != null) {
	    	  pst.setString(++i, nonConformitaGravi);
		      }
	      
	      
	      if (puntiFormali != null) {
	    	  pst.setString(++i, puntiFormali);
		      }
	      
	      
	      if (puntiSignificativi != null) {
	    	  pst.setString(++i, puntiSignificativi);
		      }
	      
	      
	      if (puntiGravi != null) {
	    	  pst.setString(++i, puntiGravi);
		      }
	      
	      
	      if (descrizioneControllo != null) {
	    	  pst.setString(++i, descrizioneControllo);
		      }
	      if (descrizioneAnimali != null) {
	    	  pst.setString(++i, descrizioneAnimali);
		      }
	      if (luogoControllo != null) {
	    	  pst.setString(++i, luogoControllo);
		      }
	      
	      if (numeroMezzi != -1) {
	    	  pst.setInt(++i, numeroMezzi);
		      }
	      
	      if (mezziIspezionati != -1) {
	    	  pst.setInt(++i, mezziIspezionati);
		      }
	      
	      if (anomaliIspezionat != -1) {
	    	  pst.setInt(++i, anomaliIspezionat);
		      }
	      if (categoriaTrasportata != -1) {
	    	  pst.setInt(++i, categoriaTrasportata);
		      }
	      if (specieA != -1) {
	    	  pst.setInt(++i, specieA);
		      }
		    
		    pst.setString(++i, this.getSolution());
		    if( pippo != null )
		    {
		    	pst.setString( ++i, pippo );
		    }
		    else
		    {
		    	pst.setNull( i++, Types.VARCHAR );
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

		    DatabaseUtils.setInt(pst, ++i, tipoCampione);
		    DatabaseUtils.setInt(pst, ++i, esitoCampione);
		    DatabaseUtils.setInt(pst, ++i, destinatarioCampione);
		    pst.setTimestamp(++i, dataAccettazione );
		    pst.setTimestamp(++i, dataFineControllo );
		    pst.setTimestamp(++i, dataProssimoControllo );
		    pst.setString(++i, dataAccettazioneTimeZone );
		    pst.setInt(++i, nucleoIspettivo);
		    pst.setInt(++i, nucleoIspettivoDue);
		    pst.setInt(++i, nucleoIspettivoTre);
		    pst.setString(++i, componenteNucleo);
		    pst.setString(++i, componenteNucleoDue);
		    pst.setString(++i, componenteNucleoTre);
		    pst.setInt(++i, nucleoIspettivoQuattro);
	    	pst.setInt(++i, nucleoIspettivoCinque);
	    	pst.setInt(++i, nucleoIspettivoSei);
	    	pst.setInt(++i, nucleoIspettivoSette);
	    	pst.setInt(++i, nucleoIspettivoOtto);
	    	pst.setInt(++i, nucleoIspettivoNove);
	    	pst.setInt(++i, nucleoIspettivoDieci);
	    	pst.setString(++i, componenteNucleoQuattro);
	    	pst.setString(++i, componenteNucleoCinque);
	    	pst.setString(++i, componenteNucleoSei);
	    	pst.setString(++i, componenteNucleoSette);
	    	pst.setString(++i, componenteNucleoOtto);
	    	pst.setString(++i, componenteNucleoNove);
	    	pst.setString(++i, componenteNucleoDieci);
	    	pst.setInt(++i, punteggio);
	    	pst.setString(++i, followUp);
	    	pst.setInt(++i, id);
		    /*if (!override && this.getModified() != null) {
		      pst.setTimestamp(++i, this.getModified());
		    }*/
		    resultCount = pst.executeUpdate();
		    pst.close();
		
		   
		    return resultCount;
		  }
	  
	  public void aggiornaPunteggioControlloUfficiale(Connection db)
	  {
		  try
		  {
			  PreparedStatement pst = db.prepareStatement("update ticket set punteggio = ? where ticketid = ?");
			  pst.setInt(1, punteggio);
			  pst.setInt(2, id);
			  pst.execute();
			  
		  }
		  catch(SQLException e)
		  {
			  e.printStackTrace();
		  }
		  
	  }
	  
	  public int updateIdControllo(Connection db, boolean override, String idControllo, int idTicket) throws SQLException {
		    int resultCount = 0;
		    PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer();
		    sql.append(
		        "UPDATE ticket " +
		        "SET id_controllo_ufficiale = "+idControllo+" ");
		    
		    sql.append(
		        "WHERE ticketid = "+idTicket+" AND tipologia = 3");
		   
		    int i = 0;
		    pst = db.prepareStatement(sql.toString());
		    
		    resultCount = pst.executeUpdate();
		    pst.close();
		    
		    return resultCount;
		  }
	  
	  
	  public int chiudi(Connection db) throws SQLException {
		    int resultCount = 0;
		    try {
		      db.setAutoCommit(false);
		      PreparedStatement pst = null;
		      String sql =
		          "UPDATE ticket " +
		          "SET closed = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
		          db) + ", modifiedby = ? " +
		          "WHERE ticketid = ? ";
		      int i = 0;
		      pst = db.prepareStatement(sql);
		      pst.setTimestamp( ++i, new Timestamp( System.currentTimeMillis() ) );
		      pst.setInt(++i, this.getModifiedBy());
		      pst.setInt(++i, this.getId());
		      resultCount = pst.executeUpdate();
		      pst.close();
		      this.setClosed((java.sql.Timestamp) null);
		      //Update the ticket log
		    
		      db.commit();
		    } catch (SQLException e) {
		      db.rollback();
		      throw new SQLException(e.getMessage());
		    } finally {
		      db.setAutoCommit(true);
		    }
		    return resultCount;
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
		
		this.deleteAzioniAdottate(db);
		
		this.deleteSpecieTrasportata(db);
		// delete any related action list items
		ActionItemLog.deleteLink(db, this.getId(), Constants.TICKET_OBJECT);
		
		PreparedStatement pst = null;
		// Delete the ticket
		pst = db.prepareStatement("UPDATE ticket SET trashed_date = now(), modified = now(), modifiedby = ? WHERE ticketid = ? OR id_controllo_ufficiale = ?");
		pst.setInt(1, this.getModifiedBy());
		pst.setInt(2, this.getId());
		pst.setString(3, this.getPaddedId());
		
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
		

		/**
		   * Sets the latitude attribute of the Address object
		   *
		   * @param latitude the latitude to set
		   */
		  public void setLatitude(double latitude) {
		    this.latitude = latitude;
		  }


		  /**
		   * Sets the longitude attribute of the Address object
		   *
		   * @param longitude the longitude to set
		   */
		  public void setLongitude(double longitude) {
		    this.longitude = longitude;
		  }


		  /**
		   * Sets the latitude attribute of the Address object
		   *
		   * @param latitude the latitude to set
		   */
		  public void setLatitude(String latitude) {
		    try {
		      this.latitude = Double.parseDouble(latitude.replace(',', '.'));
		    } catch (Exception e) {
		      this.latitude = 0;
		    }
		  }


		  /**
		   * Sets the longitude attribute of the Address object
		   *
		   * @param longitude the longitude to set
		   */
		  public void setLongitude(String longitude) {
		    try {
		      this.longitude = Double.parseDouble(longitude.replace(',', '.'));
		    } catch (Exception e) {
		      this.longitude = 0;
		    }
		  }
		  
		  
		    public String getLatitude() {
		    	return String.valueOf(this.latitude).toString();
		       // return this.latitude2;
			  }


			  public String getLongitude() {
			   return String.valueOf(this.longitude).toString();
			  }

		public boolean inserimentoRiferimentoSoa(Connection db,int idControlloUfficiale,String[] ragioneSociale, String [] indirizzi, String[] orgId)
		{
			boolean esito = false ;
			try
			{
			String delete = "delete from cu_riferimenti_soa where id_cu = "+idControlloUfficiale;
			String insert = "insert into cu_riferimenti_soa ( id_cu ,ragione_sociale, indirizzo, org_id ) values (?,?,?,?)";
			PreparedStatement pst = db.prepareStatement(delete);
			pst.execute();
			
			PreparedStatement pstInsert =db.prepareStatement(insert);
			
			if(ragioneSociale!=null)
			{
			for(int i = 0; i < ragioneSociale.length; i++)
			{
				if(! orgId[i].equals(""))
				{
				pstInsert.setInt(1, idControlloUfficiale);
				pstInsert.setString(2, ragioneSociale[i]);
				pstInsert.setString(3, indirizzi[i]);
				pstInsert.setInt(4, Integer.parseInt(orgId[i]));
				pstInsert.execute();
			}
			}
			}
			
			esito = true;
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			return esito;
		}
		

		public ArrayList<Object> getRiferimentiSoa(Connection db , int idControllo)
		{
			
			ArrayList<Object> toReturn = new ArrayList<Object>();
			
			try
			{
			
				String select = "select * from cu_riferimenti_soa where id_cu ="+idControllo;
				PreparedStatement pst = db.prepareStatement(select);
				ResultSet rs = pst.executeQuery();
				while(rs.next())
				{
					int orgId = rs.getInt("org_id");
					
					if(orgId==-1)
					{
						org.aspcfs.modules.soa.base.Organization soa = new Organization();
						org.aspcfs.modules.soa.base.OrganizationAddress add=	new org.aspcfs.modules.soa.base.OrganizationAddress();
						add.setCity(rs.getString("indirizzo"));
						add.setType(5);
						add.setType("5");
					
						soa.setName(rs.getString("ragione_sociale"));
						org.aspcfs.modules.soa.base.OrganizationAddressList addresslist = new 	org.aspcfs.modules.soa.base.OrganizationAddressList ();
						addresslist.add(add);
						soa.setAddressList(addresslist);
						toReturn.add(soa);
					
					}
					else
					{
					org.aspcfs.modules.soa.base.Organization soa = new Organization(db,orgId);
					toReturn.add(soa);
					}
					
					
					
				}
				
				
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			return toReturn;
		}
		
		public String getAllertaNotes() {
			return allertaNotes;
		}

		public void setAllertaNotes(String allertaNotes) {
			this.allertaNotes = allertaNotes;
		}

		public enum TipoOperatori {
			Account,Allevamenti,Stabilimenti,OperatoriMercatoIttico,OperatoriFuoriRegione,
			Operatoriprivati,Farmacie,Abusivismi,Soa,Osm,OsmRegistrati,AziendeAgricole,RiproduzioneAnimale,AcqueRete,
			Trasporti,Canili, CaniPadronali,LabHaccp,OSAnimali,Parafarmacie,OpnonAltrove,PuntiSbarco,MolluschiBivalvi,OperatoriCommerciali,Colonie,
			Imbarcazioni,ZoneControllo,Opu,AziendeAgricoleOpuStab,ApicolturaApiari, OpuStab, GisaSuapStab, StabilimentoSintesisAction, GestioneAnagrafica
		} 
		
		
}

