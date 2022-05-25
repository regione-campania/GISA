/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/



package org.aspcfs.modules.vigilanza.base;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.aspcf.modules.controlliufficiali.base.CuHtmlFields;
import org.aspcf.modules.controlliufficiali.base.Piano;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.bloccocu.base.EventoBloccoCu;
import org.aspcfs.modules.canipadronali.base.OperazioniCaniDAO;
import org.aspcfs.modules.canipadronali.base.Proprietario;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.opu.base.DatiMobile;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.sintesis.base.SintesisAutomezzo;
import org.aspcfs.modules.sintesis.base.SintesisOperatoreMercato;
import org.aspcfs.modules.soa.base.Organization;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.RispostaDwrCodicePiano;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
//import com.itextpdf.text.log.Logger;
//import com.itextpdf.text.log.SysoLogger;



public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{
	
	public static final int TIPO_CONDIZIONALISTA_ATTO_B11 = 5;
	
	private HashMap<Integer, OiaNodo> listaStruttureControllareAutoritaCompetenti = new HashMap<Integer, OiaNodo>();

	public static final int TIPO_CONTROLLO_SORVEGLIANZA = 5;
	public static final int TIPO_CONTROLLO_AUDIT = 3;
	public static final int TIPO_CONTROLLO_AUDIT_INTERNO = 7;
	public static final int TIPO_CONTROLLO_AUDIT_FOLLOWUP = 23;
	public static final int TIPO_CONTROLLO_ISPEZIONE = 4;
	public static final int TIPO_CONTROLLO_SIMULAZIONE = 22;
	public static final int TIPO_CONTROLLO_SANZIONE_POSTICIPATA = 2;
    public static final int TIPO_CONTROLLO_ISPEZIONE_MACELLO = 26;

	ArrayList<String>  listaLineeProduttive = new ArrayList<String>();
	
	
	
	public static final int STATO_APERTO = 1;
	public static final int STATO_CHIUSO = 2;
	public static final int STATO_RIAPERTO = 3;
	public static final int STATO_ANNULLATO = 4;
	
	
	private boolean farmacosorveglianza=false ;
	private LookupList bpi_default ;
	private LookupList haccp_default ;
	private LookupList tipiispezioni_default ;
	private LookupList audit_default;
	private LookupList azioni_adottate_def ;

	private boolean vincoloRegistro = true;
	private boolean vincoloChecklistMacelli = false;


	private String flag_preavviso;
	private Timestamp data_preavviso_ba ;

	private String flag_checklist;
	private String misureFormative;
	private String misureRiabilitative;
	private String misureRestrittive;
	private String comuneControllo ;
	private String luogoControlloTarga ;
	private String checklistMacelli ;
	private boolean checklistLibera ;
	private boolean AMR ;
	private boolean acquacoltura;
	
	private String apiariSelezionati;
	private String apiariSelezionatiMotivo;
	private String apiariSelezionatiMotivoAltro;
	private int apiariSelezionatiAlveariControllati;
	private String apiariSelezionatiEsito;
	private String apiariSelezionatiEsitoNote;
	
	/**
	 * 
	 */
	
	private int quantitativo ;
	public String getComuneControllo() {
		return comuneControllo;
	}
	public void setComuneControllo(String comuneControllo) {
		this.comuneControllo = comuneControllo;
	}
	public String getLuogoControlloTarga() {
		return luogoControlloTarga;
	}
	public void setLuogoControlloTarga(String luogoControlloTarga) {
		this.luogoControlloTarga = luogoControlloTarga;
	}
	public String getFlag_checklist() {
		return flag_checklist;
	}
	public void setFlag_checklist(String flag_checklist) {
		this.flag_checklist = flag_checklist;
	}
	
	public String getMisureFormative() {
		return misureFormative;
	}
	public void setMisureFormative(String misureFormative) {
		this.misureFormative = misureFormative;
	}
	public String getMisureRiabilitative() {
		return misureRiabilitative;
	}
	public void setMisureRiabilitative(String misureRiabilitative) {
		this.misureRiabilitative = misureRiabilitative;
	}
	public String getMisureRestrittive() {
		return misureRestrittive;
	}
	public void setMisureRestrittive(String misureRestrittive) {
		this.misureRestrittive = misureRestrittive;
	}
	
	private double quintali ;
	private static final long serialVersionUID = 6062647703225443222L;
	private ArrayList<OiaNodo> lista_uo_nucleo = new ArrayList<OiaNodo>();
	private static final int TIPOLOGIA_CAMPIONI 		= 2 ;
	private static final int TIPOLOGIA_TAMPONI 			= 7 ;
	private static final int TIPOLOGIA_NONCONFORMITA	= 8 ;
	private static final int TIPOLOGIA_SEQUESTRI 		= 9 ;
	private static final int TIPOLOGIA_SANZIONI 		= 1 ;
	private static final int TIPOLOGIA_REATI 			= 6 ;
	private static final int TIPOLOGIA_FOLLOWUP 		= 15 ;

	private String data_nascita_conducente ;
	private String luogo_nascita_conducente ;
	private String citta_conducente ;
	private String via_connducente ;
	private String prov_conducente ;
	private String cap_conducente ;
	private ArrayList<OiaNodo> lista_unita_operative = new ArrayList<OiaNodo>();
	private Integer tipologiaOperatore ;
	private String tipologiaOperatoreDescr ;
	private String ragioneSociale;
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
	private int livelloRischio ;	
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
	private String numDDt;
	private String quantitaPartita;
	private String quantitaBloccata;
	private HashMap<Integer, String> azioniAdottate = new HashMap<Integer, String>();
	private int azioneArticolo;
	private Integer idFileAllegato = -1;
	private boolean listaDistribuzioneAllegata=false;
	private String subjectFileAllegato="";
	private String unitaMisura="";
	private String codiceAteco="";
	private boolean ncrilevate=false;
	private int anomaliIspezionat=-1;
	private int numeoAttivitainCU=0;
	private String noteAltrodiSistema="";
	private String ispezioneAltro="";
	private String nonConformitaFormali="";
	private String nonConformitaGravi="";
	private String nonConformitaSignificative="";
	private String puntiFormali=null;
	private String puntiSignificativi=null;
	private String puntiGravi=null;
	private String luogoControllo=null;
	private int mezziIspezionati=-1;
	private int animaliIspezionati=-1;
	protected int tipoCampione = -1;
	protected int esitoCampione = -1;
	protected int destinatarioCampione = -1;
	protected Timestamp dataAccettazione = null;
	protected String dataAccettazioneTimeZone = null;
	private int nucleoIspettivo = -1;
	private int nucleoIspettivoDue = -1;
	private int nucleoIspettivoTre = -1;
	//aggiunto da d.dauria
	private int supervisionato_da ;
	private boolean supervisione_flag_congruo = true ;
	private String supervisione_note  ;
	private Timestamp supervisionato_in_data ;
	private String testoAppoggio = "";
	private int nucleoIspettivoQuattro = -1;
	private int nucleoIspettivoCinque = -1;
	private int nucleoIspettivoSei = -1;
	private int nucleoIspettivoSette = -1;
	private int nucleoIspettivoOtto = -1;
	private int nucleoIspettivoNove = -1;
	private int nucleoIspettivoDieci = -1;
	private HashMap<Integer, OiaNodo> lista_uo_ispezione = new HashMap<Integer, OiaNodo>();
	private HashMap<Integer, OiaNodo> lista_nucleo_ispettivo = new HashMap<Integer, OiaNodo>();


	private String componenteNucleo = null;
	private String componenteNucleoDue = null;
	private String componenteNucleoTre = null;
	private String componenteNucleoQuattro = null;
	private String componenteNucleoCinque = null;
	private String componenteNucleoSei = null;
	private String componenteNucleoSette = null;
	private String componenteNucleoOtto = null;
	private String componenteNucleoNove = null;
	private String componenteNucleoDieci = null;
	private String tipoSospetto = null;
	private String codiceBuffer =  null;


	private int componentenucleoid_uno ;
	private int componentenucleoid_due  ;
	private int componentenucleoid_tre  ;
	private int componentenucleoid_quattro  ;
	private int componentenucleoid_cinque  ;
	private int componentenucleoid_sei  ;
	private int componentenucleoid_sette  ;
	private int componentenucleoid_otto  ;
	private int componentenucleoid_nove  ;
	private int componentenucleoid_dieci  ;

	/*CAMPI PER TENERE TRACCIA DELLA RISPOSTA DEI SERVIZI CHIAMATI PER L'INVIO ALLA BDN NEL CASO IN CUI
	 * IL CONTROLLO e' BENESSERE ANIMALE */
	private String esito_import;
	private String descrizione_errore ;
	private Timestamp data_import ;
	private int idBdn ;
	
	/*CAMPI PER TENERE TRACCIA DELLA RISPOSTA DEI SERVIZI CHIAMATI PER L'INVIO ALLA BDN NEL CASO IN CUI
	 * IL CONTROLLO e' CONDIZIONALITA */
	private String esito_import_b11;
	private String descrizione_errore_b11 ;
	private Timestamp data_import_b11 ;

	private String headerAllegatoDocumentale=null;
	private HashMap<Integer, String> tipo_ispezione_condizionalita = new HashMap<Integer, String>();

	private boolean closed_nolista = false;
	private Boolean auditDiFollowUp = false;

	private int idTarga=-1;
	
	public ArrayList<String> getListaLineeProduttive() {
		return listaLineeProduttive;
	}
	public void setListaLineeProduttive(ArrayList<String> listaLineeProduttive) {
		this.listaLineeProduttive = listaLineeProduttive;
	}
	public HashMap<Integer, OiaNodo> getListaStruttureControllareAutoritaCompetenti() {
		return listaStruttureControllareAutoritaCompetenti;
	}
	public void setListaStruttureControllareAutoritaCompetenti(
			HashMap<Integer, OiaNodo> listaStruttureControllareAutoritaCompetenti) {
		this.listaStruttureControllareAutoritaCompetenti = listaStruttureControllareAutoritaCompetenti;
	}
	public HashMap<Integer, OiaNodo> getLista_nucleo_ispettivo() {
		return lista_nucleo_ispettivo;
	}
	public String getFlag_preavviso() {
		return flag_preavviso;
	}
	public void setFlag_preavviso(String flag_preavviso) {
		this.flag_preavviso = flag_preavviso;
	}
	public Timestamp getData_preavviso_ba() {
		return data_preavviso_ba;
	}
	public void setData_preavviso_ba(Timestamp data_preavviso_ba) {
		this.data_preavviso_ba = data_preavviso_ba;
	}
	public void setLista_nucleo_ispettivo(
			HashMap<Integer, OiaNodo> lista_nucleo_ispettivo) {
		this.lista_nucleo_ispettivo = lista_nucleo_ispettivo;
	}
	public HashMap<Integer, String> getTipo_ispezione_condizionalita() {
		return tipo_ispezione_condizionalita;
	}
	public void setTipo_ispezione_condizionalita(
			HashMap<Integer, String> tipo_ispezione_condizionalita) {
		this.tipo_ispezione_condizionalita = tipo_ispezione_condizionalita;
	}

	public String getEsito_import() {
		return esito_import;
	}
	public void setEsito_import(String esito_import) {
		this.esito_import = esito_import;
	}
	public String getDescrizione_errore() {
		return descrizione_errore;
	}
	public void setDescrizione_errore(String descrizione_errore) {
		this.descrizione_errore = descrizione_errore;
	}
	public Timestamp getData_import() {
		return data_import;
	}
	public void setData_import(Timestamp data_import) {
		this.data_import = data_import;
	}
	public int getIdBdn() {
		return idBdn;
	}
	public void setIdBdn(int idBdn) {
		this.idBdn = idBdn;
	}
	public Ticket () {



	}

	
	public int getComponentenucleoid_uno() {
		return componentenucleoid_uno;
	}


	public void setComponentenucleoid_uno(int componentenucleoid_uno) {
		this.componentenucleoid_uno = componentenucleoid_uno;
	}


	public int getComponentenucleoid_due() {
		return componentenucleoid_due;
	}


	public void setComponentenucleoid_due(int componentenucleoid_due) {
		this.componentenucleoid_due = componentenucleoid_due;
	}


	public int getComponentenucleoid_tre() {
		return componentenucleoid_tre;
	}


	public void setComponentenucleoid_tre(int componentenucleoid_tre) {
		this.componentenucleoid_tre = componentenucleoid_tre;
	}
	public String getDataddtTimeZone() {
		return dataddtTimeZone;
	}


	public void setDataddtTimeZone(String dataddtTimeZone) {
		this.dataddtTimeZone = dataddtTimeZone;
	}

	public void setDataDdtTimeZone(String tmp) {
		this.dataddtTimeZone = tmp;
	}
	public String getDataDdtTimeZone() {
		return dataddtTimeZone;
	}

	public int getComponentenucleoid_quattro() {
		return componentenucleoid_quattro;
	}


	public void setComponentenucleoid_quattro(int componentenucleoid_quattro) {
		this.componentenucleoid_quattro = componentenucleoid_quattro;
	}


	public int getComponentenucleoid_cinque() {
		return componentenucleoid_cinque;
	}


	public void setComponentenucleoid_cinque(int componentenucleoid_cinque) {
		this.componentenucleoid_cinque = componentenucleoid_cinque;
	}


	public int getComponentenucleoid_sei() {
		return componentenucleoid_sei;
	}


	public void setComponentenucleoid_sei(int componentenucleoid_sei) {
		this.componentenucleoid_sei = componentenucleoid_sei;
	}


	public int getComponentenucleoid_sette() {
		return componentenucleoid_sette;
	}


	public void setComponentenucleoid_sette(int componentenucleoid_sette) {
		this.componentenucleoid_sette = componentenucleoid_sette;
	}


	public int getComponentenucleoid_otto() {
		return componentenucleoid_otto;
	}


	public void setComponentenucleoid_otto(int componentenucleoid_otto) {
		this.componentenucleoid_otto = componentenucleoid_otto;
	}


	public int getComponentenucleoid_nove() {
		return componentenucleoid_nove;
	}


	public void setComponentenucleoid_nove(int componentenucleoid_nove) {
		this.componentenucleoid_nove = componentenucleoid_nove;
	}


	public int getComponentenucleoid_dieci() {
		return componentenucleoid_dieci;
	}


	public void setComponentenucleoid_dieci(int componentenucleoid_dieci) {
		this.componentenucleoid_dieci = componentenucleoid_dieci;
	}


	public boolean isSupervisione_flag_congruo() {
		return supervisione_flag_congruo;
	}


	public void setSupervisione_flag_congruo(boolean supervisione_flag_congruo) {
		this.supervisione_flag_congruo = supervisione_flag_congruo;
	}


	public String getSupervisione_note() {
		return supervisione_note;
	}


	public void setSupervisione_note(String supervisione_note) {
		this.supervisione_note = supervisione_note;
	}


	public static int getTIPOLOGIA_SEQUESTRI() {
		return TIPOLOGIA_SEQUESTRI;
	}


	public String getCodiceBuffer() {
		return codiceBuffer;
	}


	public void setCodiceBuffer(String codiceBuffer) {
		this.codiceBuffer = codiceBuffer;
	}


	public String getTipoSospetto() {
		return tipoSospetto;
	}


	public void setTipoSospetto(String tipoSospetto) {
		this.tipoSospetto = tipoSospetto;
	}


	private EventoBloccoCu bloccoCU ;
	private String [] uo ;

	private double contributi_seguito_campioni_tamponi=0;
	private double contributi_importazione_scambio=0;

	private double contributi_allarme_rapido=0;
	private double contributi_rilascio_certificazione =0;
	private double contributi_verifica_risoluzione_nc=0;
	private double contributi_macellazione=0;
	private double contributi_macellazione_urgenza=0;

	private Timestamp data_preavviso ;
	private String protocollo_preavviso ;
	private String tipologia_sottoprodotto ;
	private double peso ;
	private Timestamp data_comunicazione_svincolo;
	private String protocollo_svincolo ;
	private ArrayList<OiaNodo> lista_struttura_asl = new ArrayList<OiaNodo>();
	private Boolean intera_asl = false ;

	private Timestamp data_chiusura_ufficio_prevista ;




	public int getSupervisionato_da() {
		return supervisionato_da;
	}


	public void setSupervisionato_da(int supervisionato_da) {
		this.supervisionato_da = supervisionato_da;
	}


	public String getSupervisionato_in_data_string() {

		String s = "" ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(supervisionato_in_data!=null)
		{
			s = sdf.format(new Date(supervisionato_in_data.getTime()));
		}
		return s ;

	}




	public Timestamp getSupervisionato_in_data() {
		return supervisionato_in_data;
	}


	public void setSupervisionato_in_data(Timestamp supervisionato_in_data) {
		this.supervisionato_in_data = supervisionato_in_data;
	}


	public int getQuantitativo() {
		return quantitativo;
	}


	public void setQuantitativo(int quantitativo) {
		this.quantitativo = quantitativo;
	}


	public double getQuintali() {
		return quintali;
	}

	public void setQuantitativo(String quantitativo) {

		if (quantitativo != null && !quantitativo.equals(""))
			this.quantitativo = Integer.parseInt(quantitativo);
	}


	public void setQuintali(String quintali) {
		if (quintali != null && !quintali.equals(""))
			this.quintali = Integer.parseInt(quintali);	
	}


	public void setQuintali(double quintali) {
		this.quintali = quintali;
	}


	public String [] getUo() {
		return uo;
	}


	public void setUo(String uo[]) {
		this.uo = uo;
	}


	public ArrayList<OiaNodo> getLista_uo_nucleo() {
		return lista_uo_nucleo;
	}


	public void setLista_uo_nucleo(ArrayList<OiaNodo> lista_uo_nucleo) {
		this.lista_uo_nucleo = lista_uo_nucleo;
	}




	public void setSupervisione(Connection db) throws SQLException
	{

		String sql = "update ticket set supervisionato_da = ? ,supervisionato_in_data = ?,supervisione_flag_congruo = ? ,supervisione_note = ? where ticketid = ?" ;
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, supervisionato_da);
		pst.setTimestamp(2, supervisionato_in_data);
		pst.setBoolean(3, supervisione_flag_congruo);
		pst.setString(4, supervisione_note);
		pst.setInt(5, id);
		pst.execute();
	}

	public Timestamp getData_chiusura_ufficio_prevista() {


		if (this.entered!=null)
		{
			data_chiusura_ufficio_prevista = new Timestamp (entered.getTime());
			data_chiusura_ufficio_prevista.setDate(data_chiusura_ufficio_prevista.getDate()+30);
		}
		return data_chiusura_ufficio_prevista;
	}


	public void setData_chiusura_ufficio_prevista(
			Timestamp data_chiusura_ufficio_prevista) {
		this.data_chiusura_ufficio_prevista = data_chiusura_ufficio_prevista;
	}


	public double getContributi_importazione_scambio() {
		return contributi_importazione_scambio;
	}


	public void setContributi_importazione_scambio(
			double contributi_importazione_scambio) {
		this.contributi_importazione_scambio = contributi_importazione_scambio;
	}


	public ArrayList<OiaNodo> getLista_struttura_asl() {
		return lista_struttura_asl;
	}


	public void setLista_struttura_asl(ArrayList<OiaNodo> lista_struttura_asl) {
		this.lista_struttura_asl = lista_struttura_asl;
	}


	public boolean isIntera_asl() {
		return intera_asl;
	}


	public void setIntera_asl(boolean intera_asl) {
		this.intera_asl = intera_asl;
	}


	public Timestamp getData_preavviso() {
		return data_preavviso;
	}


	public String getData_preavvisoasString() {

		if (data_preavviso != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(new Date(data_preavviso.getTime()));


		}
		return "";
	}


	public void setData_preavviso_ba(String data_preavviso_ba) throws ParseException {
		if (data_preavviso_ba != null && ! "".equals(data_preavviso_ba))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_preavviso_ba = new Timestamp(sdf.parse(data_preavviso_ba).getTime());


		}
	}


	public void setData_preavviso(String data_preavviso) throws ParseException {
		if (data_preavviso != null && ! "".equals(data_preavviso))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_preavviso = new Timestamp(sdf.parse(data_preavviso).getTime());


		}
	}


	public String getProtocollo_preavviso() {
		return protocollo_preavviso;
	}


	public void setProtocollo_preavviso(String protocollo_preavviso) {
		this.protocollo_preavviso = protocollo_preavviso;
	}


	public String getTipologia_sottoprodotto() {
		return tipologia_sottoprodotto;
	}


	public void setTipologia_sottoprodotto(String tipologia_sottoprodotto) {
		this.tipologia_sottoprodotto = tipologia_sottoprodotto;
	}


	public double getPeso() {
		return peso;
	}


	public void setPeso(double peso) {
		this.peso = peso;
	}


	public Timestamp getData_comunicazione_svincolo() {
		return data_comunicazione_svincolo;
	}

	public String getcomunicazione_svincoloasString() {

		if (data_comunicazione_svincolo != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(new Date(data_comunicazione_svincolo.getTime()));


		}
		return "";
	}
	public void setData_comunicazione_svincolo(String data_comunicazione_svincolo) throws ParseException {
		if (data_comunicazione_svincolo != null && ! "".equals(data_comunicazione_svincolo))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_comunicazione_svincolo = new Timestamp(sdf.parse(data_comunicazione_svincolo).getTime());


		}
	}


	public String getProtocollo_svincolo() {
		return protocollo_svincolo;
	}


	public void setProtocollo_svincolo(String protocollo_svincolo) {
		this.protocollo_svincolo = protocollo_svincolo;
	}


	public double getContributi_allarme_rapido() {
		return contributi_allarme_rapido;
	}


	public void setContributi_allarme_rapido(double contributi_allarme_rapido) {
		this.contributi_allarme_rapido = contributi_allarme_rapido;
	}


	public double getContributi_rilascio_certificazione() {
		return contributi_rilascio_certificazione;
	}


	public void setContributi_rilascio_certificazione(
			double contributi_rilascio_certificazione) {
		this.contributi_rilascio_certificazione = contributi_rilascio_certificazione;
	}


	public double getContributi_verifica_risoluzione_nc() {
		return contributi_verifica_risoluzione_nc;
	}


	public void setContributi_verifica_risoluzione_nc(
			double contributi_verifica_risoluzione_nc) {
		this.contributi_verifica_risoluzione_nc = contributi_verifica_risoluzione_nc;
	}


	public double getContributi_macellazione() {
		return contributi_macellazione;
	}


	public void setContributi_macellazione(double contributi_macellazione) {
		this.contributi_macellazione = contributi_macellazione;
	}

	public double getContributi_macellazione_urgenza() {
		return contributi_macellazione_urgenza;
	}


	public void setContributi_macellazione_urgenza(
			double contributi_macellazione_urgenza) {
		this.contributi_macellazione_urgenza = contributi_macellazione_urgenza;
	}

	private boolean inserisciContinua = false;
	private String idControlloUfficiale = null;
	private ArrayList<Object> listaRiferimenti = new ArrayList<Object>();
	private int punteggio = 0;

	private int ispezione=1;
	private String bpi="-1";
	private int haccp=-1;
	private String descriptionTipoAudit="";
	private String descriptionTipoIspezione="";
	private String followUp = null;

	protected HashMap<Integer, String> tipoAudit = new HashMap<Integer, String>();
	protected HashMap<Integer, String> oggettoAudit = new HashMap<Integer, String>();
	private HashMap<Integer,String> lisaElementibpi=new HashMap<Integer, String>();
	private HashMap<Integer,String> lisaElementihaccp=new HashMap<Integer, String>();
	private HashMap<Integer, String> tipoIspezione= new HashMap<Integer, String>();

	private ArrayList<String> tipoIspezioneCodiceInterno= new ArrayList<String>();
	private ArrayList<String> tipoIspezioneCodiceInternoUnivoco= new ArrayList<String>();

	private ArrayList<Piano> pianoMonitoraggio=new ArrayList<Piano>();


	private HashMap<Integer, String> lisaElementipianoMonitoraggio_ispezioni=new HashMap<Integer, String>();
	private HashMap<Integer, HashMap<Integer, String> > lisaElementi_ispezioni=new HashMap<Integer, HashMap<Integer, String> >();
	//Trasporti
	private HashMap<Integer, String> listaAnimali_ispezioni=new HashMap<Integer, String> ();

	private TicketList elenco_lab_in_regione_controllati = new TicketList();
	private TicketList elenco_lab_non_in_regione_controllati = new TicketList();



	private int numCampioni 	= 0 ;
	private int numTamponi 		= 0 ;
	private int numSequestri	= 0 ;
	private int numSanzioni		= 0 ;
	private int numFollowup		= 0 ;
	private int numReati		= 0 ;
	private int idFarmacia 		= -1;
	private String descrizioneCodiceAteco;
	private String esitoDeclassamento;
	private int declassamento ;

	private String ricoverati ;
	private String soggettiCoinvolti;
	private boolean azione;
	private boolean azioneIsNull;
	private String azione_descrizione ;
	private String alimentiSospetti ;

	private String ispezioni_desc1;
	private String ispezioni_desc2;
	private String ispezioni_desc3;
	private String ispezioni_desc4;
	private String ispezioni_desc5;
	private String ispezioni_desc6;
	private String ispezioni_desc7;
	private String ispezioni_desc8;

	private int num_specie1 = 0;
	private int num_specie2 = 0;
	private int num_specie3 = 0;
	private int num_specie4 = 0;
	private int num_specie5 = 0;
	private int num_specie6 = 0;
	private int num_specie7 = 0;
	private int num_specie8 = 0;
	private int num_specie9 = 0;
	private int num_specie10 = 0;
	private int num_specie11 = 0;
	private int num_specie12 = 0;
	private int num_specie13 = 0;
	private int num_specie14 = 0;
	private int num_specie15  = 0;
	private int num_specie22  = 0;
	private int num_specie23  = 0;
	private int num_specie24  = 0;
	private int num_specie25  = 0;
	private int num_specie26  = 0;
	
	private String num_documento_accompagnamento = null;


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


	private int idConcessionario ;
	private int id_imprese_linee_attivita ;
	private String tipoControllo;
	private String nome_conducente ;
	private String cognome_conducente ;
	private String documento_conducente ;
	private int tipologia_controllo_cani ;
	private int assetId ;
	private boolean categoriaAggiornabile = false ;

	private Proprietario proprietario 					;
	private String codice_azienda ;

	private String ragione_sociale_allevamento ;
	private int id_allevamento ;

	//private int categoriaQuantitativa;

	public boolean tipoIspezioneCodiceInternoContainsIgnoreCase(String str) {
		return containsIgnoreCase(str, tipoIspezioneCodiceInterno);
	}

	public ArrayList<String> getTipoIspezioneCodiceInterno() {
		return tipoIspezioneCodiceInterno;
	}
	public void setTipoIspezioneCodiceInterno(ArrayList<String> tipoIspezioneCodiceInterno) {
		this.tipoIspezioneCodiceInterno = tipoIspezioneCodiceInterno;
	}
	public HashMap<Integer, String> getOggettoAudit() {
		return oggettoAudit;
	}


	public ArrayList<String> getTipoIspezioneCodiceInternoUnivoco() {
		return tipoIspezioneCodiceInternoUnivoco;
	}
	public void setTipoIspezioneCodiceInternoUnivoco(ArrayList<String> tipoIspezioneCodiceInternoUnivoco) {
		this.tipoIspezioneCodiceInternoUnivoco = tipoIspezioneCodiceInternoUnivoco;
	}
	public void setOggettoAudit(HashMap<Integer, String> oggettoAudit) {
		this.oggettoAudit = oggettoAudit;
	}


	public LookupList getAzioni_adottate_def() {
		return azioni_adottate_def;
	}


	public void setAzioni_adottate_def(LookupList azioni_adottate_def) {
		this.azioni_adottate_def = azioni_adottate_def;
	}


	public LookupList getBpi_default() {
		return bpi_default;
	}


	public void setBpi_default(LookupList bpi_default) {
		this.bpi_default = bpi_default;
	}


	public LookupList getHaccp_default() {
		return haccp_default;
	}


	public void setHaccp_default(LookupList haccp_default) {
		this.haccp_default = haccp_default;
	}


	public LookupList getTipiispezioni_default() {
		return tipiispezioni_default;
	}


	public void setTipiispezioni_default(LookupList tipiispezioni_default) {
		this.tipiispezioni_default = tipiispezioni_default;
	}


	public LookupList getAudit_default() {
		return audit_default;
	}


	public void setAudit_default(LookupList audit_default) {
		this.audit_default = audit_default;
	}


	public String getData_nascita_conducente() {
		return data_nascita_conducente;
	}


	public void setData_nascita_conducente(String data_nascita_conducente) {
		this.data_nascita_conducente = data_nascita_conducente;
	}


	public String getLuogo_nascita_conducente() {
		return luogo_nascita_conducente;
	}


	public void setLuogo_nascita_conducente(String luogo_nascita_conducente) {
		this.luogo_nascita_conducente = luogo_nascita_conducente;
	}


	public String getCitta_conducente() {
		return citta_conducente;
	}


	public void setCitta_conducente(String citta_conducente) {
		this.citta_conducente = citta_conducente;
	}


	public String getVia_connducente() {
		return via_connducente;
	}


	public void setVia_connducente(String via_connducente) {
		this.via_connducente = via_connducente;
	}


	public String getProv_conducente() {
		return prov_conducente;
	}


	public void setProv_conducente(String prov_conducente) {
		this.prov_conducente = prov_conducente;
	}


	public String getCap_conducente() {
		return cap_conducente;
	}


	public void setCap_conducente(String cap_conducente) {
		this.cap_conducente = cap_conducente;
	}


	public String getRagione_sociale_allevamento() {
		return ragione_sociale_allevamento;
	}


	public void setRagione_sociale_allevamento(String ragione_sociale_allevamento) {
		this.ragione_sociale_allevamento = ragione_sociale_allevamento;
	}


	public int getId_allevamento() {
		return id_allevamento;
	}


	public void setId_allevamento(int id_allevamento) {
		this.id_allevamento = id_allevamento;
	}


	public String getCodice_azienda() {
		return codice_azienda;
	}


	public void setCodice_azienda(String codice_azienda) {
		this.codice_azienda = codice_azienda;
	}


	public Proprietario getProprietario() {
		return proprietario;
	}


	public void setCane(Proprietario proprietario) {
		this.proprietario = proprietario;
	}


	public boolean isCategoriaAggiornabile() {
		return categoriaAggiornabile;
	}


	public void setCategoriaAggiornabile(boolean categoriaAggiornabile) {
		this.categoriaAggiornabile = categoriaAggiornabile;
	}


	public int getAssetId() {
		return assetId;
	}


	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}


	public String getNome_conducente() {
		return nome_conducente;
	}


	public void setNome_conducente(String nome_conducente) {
		this.nome_conducente = nome_conducente;
	}


	public String getCognome_conducente() {
		return cognome_conducente;
	}


	public void setCognome_conducente(String cognome_conducente) {
		this.cognome_conducente = cognome_conducente;
	}


	public String getDocumento_conducente() {
		return documento_conducente;
	}


	public void setDocumento_conducente(String documento_conducente) {
		this.documento_conducente = documento_conducente;
	}


	public int getTipologia_controllo_cani() {
		return tipologia_controllo_cani;
	}


	public void setTipologia_controllo_cani(int tipologia_controllo_cani) {
		this.tipologia_controllo_cani = tipologia_controllo_cani;
	}


	public String getTipoControllo() {
		return tipoControllo;
	}


	public void setTipoControllo(String tipoControllo) {
		this.tipoControllo = tipoControllo;
	}


	public int getId_imprese_linee_attivita() {
		return id_imprese_linee_attivita;
	}


	public void setId_imprese_linee_attivita(String idImpreseLineeAttivita) {
		if (idImpreseLineeAttivita!=null){
			setId_imprese_linee_attivita(Integer.parseInt(idImpreseLineeAttivita));
		}

	}

	public void setId_imprese_linee_attivita(int idImpreseLineeAttivita) {
		id_imprese_linee_attivita = idImpreseLineeAttivita;
	}


	public int getIdConcessionario() {
		return idConcessionario;
	}


	public void setIdConcessionario(int idConcessionario) {
		this.idConcessionario = idConcessionario;
	}


	public String getEsitoDeclassamento() {
		return esitoDeclassamento;
	}


	public void setEsitoDeclassamento(String esitoDeclassamento) {
		this.esitoDeclassamento = esitoDeclassamento;
	}


	public int getDeclassamento() {
		return declassamento;
	}


	public void setDeclassamento(int declassamento) {
		this.declassamento = declassamento;
	}


	public String getAlimentiSospetti() {
		return alimentiSospetti;
	}


	public void setAlimentiSospetti(String alimentiSospetti) {
		this.alimentiSospetti = alimentiSospetti;
	}


	public String getIspezioni_desc1() {
		return ispezioni_desc1;
	}


	public void setIspezioni_desc1(String ispezioni_desc1) {
		this.ispezioni_desc1 = ispezioni_desc1;
	}


	public String getIspezioni_desc2() {
		return ispezioni_desc2;
	}


	public void setIspezioni_desc2(String ispezioni_desc2) {
		this.ispezioni_desc2 = ispezioni_desc2;
	}


	public String getIspezioni_desc3() {
		return ispezioni_desc3;
	}


	public void setIspezioni_desc3(String ispezioni_desc3) {
		this.ispezioni_desc3 = ispezioni_desc3;
	}


	public String getIspezioni_desc4() {
		return ispezioni_desc4;
	}


	public void setIspezioni_desc4(String ispezioni_desc4) {
		this.ispezioni_desc4 = ispezioni_desc4;
	}


	public String getIspezioni_desc5() {
		return ispezioni_desc5;
	}


	public void setIspezioni_desc5(String ispezioni_desc5) {
		this.ispezioni_desc5 = ispezioni_desc5;
	}


	public String getIspezioni_desc6() {
		return ispezioni_desc6;
	}


	public void setIspezioni_desc6(String ispezioni_desc6) {
		this.ispezioni_desc6 = ispezioni_desc6;
	}


	public String getIspezioni_desc7() {
		return ispezioni_desc7;
	}


	public void setIspezioni_desc7(String ispezioni_desc7) {
		this.ispezioni_desc7 = ispezioni_desc7;
	}

	public String getIspezioni_desc8() {
		return ispezioni_desc8;
	}


	public void setIspezioni_desc8(String ispezioni_desc8) {
		this.ispezioni_desc8 = ispezioni_desc8;
	}

	public boolean isAzione() {
		return azione;
	}
	public boolean getAzioneIsNull() {
		return azioneIsNull;
	}


	public void setAzione(boolean azione) {
		this.azione = azione;
	}


	public String getAzione_descrizione() {
		return azione_descrizione;
	}


	public void setAzione_descrizione(String azione_descrizione) {
		this.azione_descrizione = azione_descrizione;
	}


	public String getRicoverati() {
		return ricoverati;
	}


	public void setRicoverati(String ricoverati) {
		this.ricoverati = ricoverati;
	}


	public String getSoggettiCoinvolti() {
		return soggettiCoinvolti;
	}


	public void setSoggettiCoinvolti(String soggettiCoinvolti) {
		this.soggettiCoinvolti = soggettiCoinvolti;
	}


	public int getNumeoAttivitainCU() {
		return numeoAttivitainCU;
	}





	public void setNumeoAttivitainCU(int numeoAttivitainCU) {
		this.numeoAttivitainCU = numeoAttivitainCU;
	}


	public int getTipologiaOperatore() {
		return tipologiaOperatore;
	}


	public void setTipologiaOperatore(int tipologiaOperatore) {
		this.tipologiaOperatore = tipologiaOperatore;
	}


	public String getRagioneSociale() {
		return ragioneSociale;
	}


	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}


	public String getDescrizioneCodiceAteco() {
		return descrizioneCodiceAteco;
	}


	public void setDescrizioneCodiceAteco(String descrizioneCodiceAteco) {
		this.descrizioneCodiceAteco = descrizioneCodiceAteco;
	}


	public int getNumCampioni() {
		return numCampioni;
	}


	public int getIdFarmacia() {
		return idFarmacia;
	}


	public void setIdFarmacia(int idFarmacia) {
		this.idFarmacia = idFarmacia;
	}


	public void setNumCampioni(int numCampioni) {
		this.numCampioni = numCampioni;
	}

	public int getNumTamponi() {
		return numTamponi;
	}

	public void setNumTamponi(int numTamponi) {
		this.numTamponi = numTamponi;
	}

	public int getNumSequestri() {
		return numSequestri;
	}

	public void setNumSequestri(int numSequestri) {
		this.numSequestri = numSequestri;
	}

	public int getNumSanzioni() {
		return numSanzioni;
	}

	public void setNumSanzioni(int numSanzioni) {
		this.numSanzioni = numSanzioni;
	}

	public int getNumFollowup() {
		return numFollowup;
	}

	public void setNumFollowup(int numFollowup) {
		this.numFollowup = numFollowup;
	}

	public int getNumReati() {
		return numReati;
	}

	public void setNumReati(int numReati) {
		this.numReati = numReati;
	}

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

	public String getCodiceAteco() {
		return codiceAteco;
	}

	public void setCodiceAteco(String codiceAteco) {
		this.codiceAteco = codiceAteco;
	}

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

	public String getNumDDt() {
		return numDDt;
	}

	public void setNumDDt(String numDDt) {
		this.numDDt = numDDt;
	}

	public String getQuantitaPartita() {
		return quantitaPartita;
	}

	public void setQuantitaPartita(String quantitaPartita) {
		if(quantitaPartita!=null)
			this.quantitaPartita = quantitaPartita.trim();
	}

	public String getQuantitaBloccata() {
		return quantitaBloccata;
	}

	public void setQuantitaBloccata(String quantitaBloccata) {
		if(quantitaBloccata!=null)
			this.quantitaBloccata = quantitaBloccata.trim();
	}

	public HashMap<Integer, String> getAzioniAdottate() {
		return azioniAdottate;
	}

	public void setAzioniAdottate(HashMap<Integer, String> azioniAdottate) {
		this.azioniAdottate = azioniAdottate;
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
	
	public int getLivelloRischio() {
		return livelloRischio;
	}

	public void setLivelloRischio(int livelloRischio) {
		this.livelloRischio = livelloRischio;
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


	public String getNoteAltrodiSistema() {
		return noteAltrodiSistema;
	}

	public void setNoteAltrodiSistema(String noteAltrodiSistema) {
		this.noteAltrodiSistema = noteAltrodiSistema;
	}

	public String getIspezioneAltro() {
		return ispezioneAltro;
	}

	public void setIspezioneAltro(String ispezioneAltro) {
		this.ispezioneAltro = ispezioneAltro;
	}

	public void setAnomaliIspezionati(int animaliIspezionati) {
		this.anomaliIspezionat = animaliIspezionati;}



	public String getCodiceAllerta() {
		return codiceAllerta;
	}
	
	public int getIdListaDistribuzione() {
		return idLdd;
	}
	public void setIdListaDistribuzione(int idLdd) {
		this.idLdd = idLdd;
	}
	public void setIdListaDistribuzione(String idLdd) {
		if (idLdd!=null && !idLdd.equals("null") && !idLdd.equals(""))
			this.idLdd = Integer.parseInt(idLdd);
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

	public void setNum_specie1(int animaliIspezionati) {
		this.num_specie1 = animaliIspezionati;
	}

	public void setNum_specie2(int animaliIspezionati) {
		this.num_specie2 = animaliIspezionati;
	}

	public void setNum_specie3(int animaliIspezionati) {
		this.num_specie3 = animaliIspezionati;
	}

	public void setNum_specie4(int animaliIspezionati) {
		this.num_specie4 = animaliIspezionati;
	}

	public void setNum_specie5(int animaliIspezionati) {
		this.num_specie5 = animaliIspezionati;
	}

	public void setNum_specie6(int animaliIspezionati) {
		this.num_specie6 = animaliIspezionati;
	}

	public void setNum_specie7(int animaliIspezionati) {
		this.num_specie7 = animaliIspezionati;
	}

	public void setNum_specie8(int animaliIspezionati) {
		this.num_specie8 = animaliIspezionati;
	}

	public void setNum_specie9(int animaliIspezionati) {
		this.num_specie9 = animaliIspezionati;
	}

	public void setNum_specie10(int animaliIspezionati) {
		this.num_specie10 = animaliIspezionati;
	}

	public void setNum_specie11(int animaliIspezionati) {
		this.num_specie11 = animaliIspezionati;
	}

	public void setNum_specie12(int animaliIspezionati) {
		this.num_specie12 = animaliIspezionati;
	}

	public void setNum_specie13(int animaliIspezionati) {
		this.num_specie13 = animaliIspezionati;
	}

	public void setNum_specie14(int animaliIspezionati) {
		this.num_specie14 = animaliIspezionati;
	}

	public void setNum_specie15(int animaliIspezionati) {
		this.num_specie15 = animaliIspezionati;
	}

	public int getNum_specie1() {
		return num_specie1;
	}

	public int getNum_specie2() {
		return num_specie2;
	}

	public int getNum_specie3() {
		return num_specie3;
	}

	public int getNum_specie4() {
		return num_specie4;
	}

	public int getNum_specie5() {
		return num_specie5;
	}

	public int getNum_specie6() {
		return num_specie6;
	}

	public int getNum_specie7() {
		return num_specie7;
	}

	public int getNum_specie8() {
		return num_specie8;
	}

	public int getNum_specie9() {
		return num_specie9;
	}

	public int getNum_specie10() {
		return num_specie10;
	}

	public int getNum_specie11() {
		return num_specie11;
	}

	public int getNum_specie12() {
		return num_specie12;
	}

	public int getNum_specie13() {
		return num_specie13;
	}

	public int getNum_specie14() {
		return num_specie14;
	}

	public int getNum_specie15() {
		return num_specie15;
	}


	public void setNum_specie1(String tmp) {
		this.num_specie1 = Integer.parseInt(tmp);
	}


	public void setNum_specie2(String tmp) {
		this.num_specie2 = Integer.parseInt(tmp);
	}

	public void setNum_specie3(String tmp) {
		this.num_specie3 = Integer.parseInt(tmp);
	}

	public void setNum_specie4(String tmp) {
		this.num_specie4 = Integer.parseInt(tmp);
	}

	public void setNum_specie5(String tmp) {
		this.num_specie5 = Integer.parseInt(tmp);
	}

	public void setNum_specie6(String tmp) {
		this.num_specie6 = Integer.parseInt(tmp);
	}

	public void setNum_specie7(String tmp) {
		this.num_specie7 = Integer.parseInt(tmp);
	}

	public void setNum_specie8(String tmp) {
		this.num_specie8 = Integer.parseInt(tmp);
	}

	public void setNum_specie9(String tmp) {
		this.num_specie9 = Integer.parseInt(tmp);
	}

	public void setNum_specie10(String tmp) {
		this.num_specie10 = Integer.parseInt(tmp);
	}

	public void setNum_specie11(String tmp) {
		this.num_specie11 = Integer.parseInt(tmp);
	}

	public void setNum_specie12(String tmp) {
		this.num_specie12 = Integer.parseInt(tmp);
	}

	public void setNum_specie13(String tmp) {
		this.num_specie13 = Integer.parseInt(tmp);
	}

	public void setNum_specie14(String tmp) {
		this.num_specie14 = Integer.parseInt(tmp);
	}

	public void setNum_specie15(String tmp) {
		this.num_specie15 = Integer.parseInt(tmp);
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


	public boolean isFarmacosorveglianza() {
		return farmacosorveglianza;
	}

	public void setFarmacosorveglianza(boolean farmacosorveglianza) {
		this.farmacosorveglianza = farmacosorveglianza;
	}


	public TicketList getLabInRegioneControllatiList() {
		return elenco_lab_in_regione_controllati;
	}

	public void setElencoLabInRegioneControllati(TicketList elenco_lab_in_regione_controllati) {
		this.elenco_lab_in_regione_controllati = elenco_lab_in_regione_controllati;
	}	

	public TicketList getLabNonInRegioneControllatiList() {
		return elenco_lab_non_in_regione_controllati;
	}

	public void setElencoLabNonInRegioneControllati(TicketList elenco_lab_no_in_regione_controllati) {
		this.elenco_lab_non_in_regione_controllati = elenco_lab_no_in_regione_controllati;
	}	



	protected String codiceAllerta="";
	protected int idLdd = -1;
	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	protected String pippo = "";


	private DatiMobile datiMobile = null;
	private SintesisAutomezzo sintesisAutomezzo = null;
	
	private ArrayList<SintesisOperatoreMercato> listaOperatoriMercato = new ArrayList<SintesisOperatoreMercato>();
	
	private Indirizzo indirizzoLuogoControllo = new Indirizzo();
	private int idIndirizzoLuogoControllo = -1;
	
	public double getContributi_seguito_campioni_tamponi() {
		return contributi_seguito_campioni_tamponi;
	}


	public void setContributi_seguito_campioni_tamponi(
			double contributi_seguito_campioni_tamponi) {
		this.contributi_seguito_campioni_tamponi = contributi_seguito_campioni_tamponi;
	}



	
	
	
	
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
	public ArrayList<Piano> getPianoMonitoraggio() {
		return pianoMonitoraggio;
	}
	public void setPianoMonitoraggio(ArrayList<Piano> pianoMonitoraggio) {
		this.pianoMonitoraggio = pianoMonitoraggio;
	}
	public HashMap<Integer, String> getTipoIspezione() {
		return tipoIspezione;
	}
	public void setTipoIspezione(HashMap<Integer, String> tipoIspezione) {
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

	public void setDataSintomi(String tmp)
	{
		if (!"".equals(tmp))
		{
			dataSintomi = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
			if(dataSintomi!=null)
				dataSintomiTimeZone = dataSintomi.toString();
		}
	}

	public void setDataPasto(String tmp)
	{
		if (!"".equals(tmp))
		{
			dataPasto = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
			if(dataPasto!=null)
				dataPastoTimeZone = dataPasto.toString();
		}
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
	public int getAnnoDataInizioControllo() {
		return 1900+assignedDate.getYear();
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

	public HashMap<Integer, HashMap<Integer, String>> getLisaElementi_Ispezioni() {
		return lisaElementi_ispezioni;
	}
	public void setLisaElementi_Ispezioni(
			HashMap<Integer, HashMap<Integer, String>> lisaElementi_ispezioni) {
		this.lisaElementi_ispezioni = lisaElementi_ispezioni;
	}

	public HashMap<Integer, String> getListaAnimali_Ispezioni() {
		return listaAnimali_ispezioni;
	}

	public void setListaAnimali_Ispezioni(HashMap<Integer, String> listaAnimali_Ispezioni) {
		this.listaAnimali_ispezioni = listaAnimali_Ispezioni;
	}



	public HashMap<Integer, String> getTipoAudit() {
		return tipoAudit;
	}

	public void setTipoCampione(String tipoCampione) {
		try {
			this.tipoCampione = Integer.parseInt(tipoCampione);
		} catch (Exception e) {
			this.tipoCampione = -1;
		}
	}
	public void setTipoAudit(HashMap<Integer, String> tipoAudit) {
		try {
			this.tipoAudit = tipoAudit;
		} catch (Exception e) {

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
		if ( tipologiaOperatore==255)
		{

			OperazioniCaniDAO op = new OperazioniCaniDAO();
			op.setDb(db);
			proprietario =  op.dettaglioProprietario(orgId, this.id);

		}



		/* if(tipoCampione==3){// se si tratta di un controllo di tipo audit
			  this.getTipoAuditSelezionato(db, this.id); // ritorna la descrizione del tipo di audit selezionato
			  if(tipoAudit==2){ // e' un audit di tipo bpi
				  this.setBpiSelezionati(db, this.id);

			  }else{

				  if(tipoAudit==3){// se e' un controllo di tipo haccp
					  this.setHaccpSelezionati(db, this.id);

				  }
			  }

		/*  if(tipoCampione==3)
		  {// se si tratta di un controllo di tipo audit
			  this.getTipoAuditSelezionato(db, this.id); // ritorna la descrizione del tipo di audit selezionato e bpi haccp

		  }
		  else{
			  if(tipoCampione==4)
			  {
				  //se si tratta di un controllo ispezione
				  this.setPianoispezioniSelezionate(db,this.id);
				  this.getTipoIspezioneSelezionato(db, this.id);

			  } 
			  else
			  {
				  if(tipoCampione==5) // ispezioni con la tecnica della sorveglianza
				  {

						  org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
			    	      int AuditOrgId = this.getOrgId();
			    	      String idT = this.getPaddedId();
			    	      audit.setOrgId(AuditOrgId);
			    	      audit.buildListControlli(db, AuditOrgId, idT);
			    	      Iterator<Audit> itera=audit.iterator();
			      	      int punteggioChecklist=0;
			      	      while(itera.hasNext())
			      	      {
			      	    	  Audit temp=itera.next();
			      	    	  punteggioChecklist+=temp.getLivelloRischio();

			      	      }

						  punteggio=punteggioChecklist;


				  }


			  }


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

		  }*/
		//calcoaAttivitaCU ( db );
		// numCampioni	=	calcoaAttivitaCU (db, TIPOLOGIA_CAMPIONI)	;
		//numTamponi	=	calcoaAttivitaCU (db, TIPOLOGIA_TAMPONI)	;
		//numSequestri	=	calcoaAttivitaCU (db, TIPOLOGIA_SEQUESTRI)	;
		//numSanzioni	=	calcoaAttivitaCU (db, TIPOLOGIA_SANZIONI)	;
		//numFollowup	=	calcoaAttivitaCU (db, TIPOLOGIA_FOLLOWUP)	;
		//numReati		= 	calcoaAttivitaCU (db, TIPOLOGIA_REATI)		;

		// }


	}



	private boolean categoriaisAggiornata=false;


	public boolean isCategoriaisAggiornata() {
		return categoriaisAggiornata;
	}

	public void setCategoriaisAggiornata(boolean categoriaisAggiornata) {
		this.categoriaisAggiornata = categoriaisAggiornata;
	}

	//
	public Ticket(Connection db, int id) throws SQLException {
		queryRecord(db, id);
		if (id_imprese_linee_attivita==0)
			queryRecordIdLineaAttivitaNew(db, id);
		//setStrutturaAsl(db);
		getNucleoasList();
	}
/*
	private void setCategoriaQuantitativa(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from get_categoria_rischio_quantitativa(-1, null, ?)");
		pst.setInt(1, this.id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			this.categoriaQuantitativa = rs.getInt(1);
	}
*/	
	public Ticket(Connection db, int id, String soa) throws SQLException {
		getNucleoasList();
	}

	
	public boolean isControlloChiudibileBaSa(Connection db, boolean isCondizionalita) throws SQLException{
		
		
		boolean checkScheda = true;
		boolean hasEvento = false;
		
		for(Piano p :getPianoMonitoraggio()) {
			if (PopolaCombo.hasEventoMotivoCU((isCondizionalita ? "isCondizionalita" : "isBenessereAnimale"), p.getId(), -1))
				hasEvento = true;
		}
		
		if(hasEvento == false){
			//do nothing
		} else {
			
			if(hasEvento){
		
				checkScheda = PopolaCombo.verificaEsistenzaIstanzaBenessere(getId(),isCondizionalita);
			}
		}
	
		return checkScheda;
		
	}
	
public boolean isControlloChiudibileFarmacosorveglianza(Connection db) throws SQLException{
		
		
		boolean checkScheda = true;
		boolean hasEvento = false;
		
		for(Piano p :getPianoMonitoraggio()) {
			if (PopolaCombo.hasEventoMotivoCU("isFarmacosorveglianza", p.getId(), -1))
				hasEvento = true;
		}
		
		if(hasEvento == false){
			//do nothing
		} else {
			
			if(hasEvento){
		
				checkScheda = PopolaCombo.verificaEsistenzaIstanzaFarmacosorveglianza(getId());
			}
		}
	
		return checkScheda;
		
	}
	
	
//public String isControlloChiudibilePagoPa(Connection db) throws SQLException{
//		
//		String messaggio = "";
//		String sql="select * from is_controllo_chiudibile_pagopa(?)";
//		PreparedStatement pst=db.prepareStatement(sql);
//		pst.setInt(1, id);
//		ResultSet rst=pst.executeQuery();
//
//		while(rst.next()){
//			messaggio=rst.getString(1);
//		}
//		return messaggio;
//	}


public String isControlloChiudibileAllegatiSanzione(Connection db) throws SQLException{
	
	String messaggio = "";
	String sql="select * from is_controllo_chiudibile_allegati_sanzione(?)";
	PreparedStatement pst=db.prepareStatement(sql);
	pst.setInt(1, id);
	ResultSet rst=pst.executeQuery();

	while(rst.next()){
		messaggio=rst.getString(1);
	}
	return messaggio;
}

public String isControlloChiudibilePnaa(Connection db) throws SQLException{
	
	String messaggio = "";
	String sql="select * from is_controllo_chiudibile_pnaa(?)";
	PreparedStatement pst=db.prepareStatement(sql);
	pst.setInt(1, id);
	ResultSet rst=pst.executeQuery();

	while(rst.next()){
		messaggio=rst.getString(1);
	}
	return messaggio;
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

		if(tipoIspezioneCodiceInterno.contains("7a"))
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

		if(tipoIspezioneCodiceInterno.contains("7a"))
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
				listaAnimali_ispezioni.put(idSpecie, descr);
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

	}


	public void salvaSpecieTrasportata(Connection db, String [] specieTrasportata){

		try{
			if(specieTrasportata != null) {

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
		} catch(SQLException e){
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

	public void salvaOperatoriMercato(Connection db, String [] operatoriMercato){
	
		try{
			
			PreparedStatement pst1 = db.prepareStatement("update controlli_ufficiali_operatori_mercato set trashed_date = now() where id_controllo_ufficiale = "+id+ "and trashed_date is null");
			pst1.execute();
			
			if(operatoriMercato != null) {

				PreparedStatement pst = db.prepareStatement("insert into controlli_ufficiali_operatori_mercato (id_controllo_ufficiale,id_operatore_mercato) values (?,?)");
				for(String idOperatore : operatoriMercato)
				{
					pst.setInt(1, id);
					pst.setInt(2, Integer.parseInt(idOperatore));
					pst.execute();
				}

			}
		} catch(SQLException e){
			e.printStackTrace();
		}

	}
	
	public void salvaIndirizzoLuogoControllo(Connection db, String idToponimoLuogoControllo, String viaLuogoControllo, String civicoLuogoControllo, String capLuogoControllo, String idComuneLuogoControllo, String idProvinciaLuogoControllo){
		
		try{
			
			// inserisco in opu indirizzo
				PreparedStatement pst = null;
				ResultSet rs = null;
				
				pst = db.prepareStatement("select * from insert_opu_noscia_indirizzo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");  
				int i = 0;    
				pst.setInt(++i, 106);
				pst.setString(++i, idProvinciaLuogoControllo);
				pst.setInt(++i, Integer.parseInt(idComuneLuogoControllo));
				pst.setString(++i, "");
				pst.setInt(++i, Integer.parseInt(idToponimoLuogoControllo));
				pst.setString(++i, viaLuogoControllo);
				pst.setString(++i, capLuogoControllo);
				pst.setString(++i, civicoLuogoControllo);
				pst.setDouble(++i, 0.0);
				pst.setDouble(++i, 0.0);
				pst.setString(++i, "");
				rs = pst.executeQuery();
				
				if (rs.next())
					this.idIndirizzoLuogoControllo = rs.getInt(1);
			
			//aggiorno ticket
				
				if (this.idIndirizzoLuogoControllo > 0) {
					pst = db.prepareStatement("update ticket set id_indirizzo_luogo_controllo = ? where ticketid = ?");  
					i = 0;    
					pst.setInt(++i, this.idIndirizzoLuogoControllo);
					pst.setInt(++i, this.id);
					pst.executeUpdate();
				}
				
		} catch(SQLException e){
			e.printStackTrace();
		}

	}


	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			this.id=-1 ;
		}
		else
		{
			PreparedStatement pst = db.prepareStatement(
					"SELECT t.*, " +
							"coalesce(opu.ragione_sociale,o.ragione_sociale, anag.ragione_sociale) AS name, " +
							"o.tipologia_operatore as tipologia_operatore,"+
							"true AS orgenabled, " +
							"coalesce(opu.asl_rif,o.asl_rif, anag.asl_rif) AS orgsiteid, " +
							"a.serial_number AS serialnumber, " +
							"a.manufacturer_code AS assetmanufacturercode, " +
							"a.vendor_code AS assetvendorcode, " +
							"a.model_version AS modelversion, " +
							"a.location AS assetlocation, " +
							"a.onsite_service_model AS assetonsiteservicemodel, " +
							"tcu.tipo_audit, tcu.tipoispezione ,t.latitudine,t.longitudine, tcu.audit_di_followup, o.n_linea " +
							"FROM ticket t " +
							"left join ricerche_anagrafiche_old_materializzata opu on t.id_stabilimento = opu.riferimento_id and opu.riferimento_id_nome_col='id_stabilimento' " + 
							"left join ricerche_anagrafiche_old_materializzata o on t.org_id = o.riferimento_id and o.riferimento_id_nome_col='org_id' "	+
							"left join ricerche_anagrafiche_old_materializzata anag on t.alt_id = anag.riferimento_id and anag.riferimento_id_nome_col='alt_id' " + 

				"LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +

				"LEFT JOIN tipocontrolloufficialeimprese tcu ON (t.ticketid = tcu.idcontrollo) and tcu.enabled=true " +
					"WHERE t.ticketid = ? AND t.tipologia = 3 ");

			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRecord(rs);
				buildNucleoIspettivo(db);
				setVincoloRegistro(checkVincoloRegistroTrasgressori(db));
				setVincoloChecklistMacelli(db);
				setVincoloChecklistLibera(db);
				setVincoloAMR(db);
				setVincoloAcquacoltura(db);
				setDatiMobile(db);
				setSintesisAutomezzo(db);
				setListaOperatoriMercato(db);
				setIndirizzoLuogoControllo(db);
				//setCategoriaQuantitativa(db);
				switch(tipoCampione)
				{

				case  TIPO_CONTROLLO_SORVEGLIANZA :
				{

					setNumeroAudit(db);
					buildUnitaOperative(db);

					break ;
				}
				
				case  TIPO_CONTROLLO_SANZIONE_POSTICIPATA :
				{

					buildUnitaOperative(db);
					
					break ;
				}
				case  TIPO_CONTROLLO_SIMULAZIONE :
				{

					buildUnitaOperative(db);
					getStruttureControllareAurotiraCompetenti(db);
					break ;
				}
				case  TIPO_CONTROLLO_AUDIT :
				{

					if (assignedDate.before(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_MOTIVO_ISPEZIONE_AUDIT")))){
						buildUnitaOperative(db);
						this.getTipoAuditSelezionato(db, this.id); // ritorna la descrizione del tipo di audit selezionato
						getStruttureControllareAurotiraCompetenti(db);
					} else {
							this.getTipoIspezioneSelezionato(db, this.id);
							buildAzioniAdottate(db);
					}
					break ;
				}
				
				case  TIPO_CONTROLLO_AUDIT_INTERNO :
				{

					buildUnitaOperative(db);
					this.getTipoAuditSelezionato(db, this.id); // ritorna la descrizione del tipo di audit selezionato
					getStruttureControllareAurotiraCompetenti(db);
					break ;
				}
				case  TIPO_CONTROLLO_AUDIT_FOLLOWUP :
				{

					buildUnitaOperative(db);
					this.getTipoAuditSelezionato(db, this.id); // ritorna la descrizione del tipo di audit selezionato
					getStruttureControllareAurotiraCompetenti(db);
					break ;
				}
				case  TIPO_CONTROLLO_ISPEZIONE :
				{


					this.getTipoIspezioneSelezionato(db, this.id);
					buildAzioniAdottate(db);

					break ;
				}
				case TIPO_CONTROLLO_ISPEZIONE_MACELLO:
				{
                    buildUnitaOperative(db);
                    
                    break;
				}

				}


				super.controlloBloccoCu(db, this.id);
			}

			listaRiferimenti = this.getRiferimentiSoa(db, id);
			this.setPianoispezioniSelezionate(db,this.id);  
			this.calcoloTipoControllo(db);

			rs.close();
			pst.close();
			if (this.id == -1) {
				throw new SQLException(Constants.NOT_FOUND_ERROR);
			}
		}

	}

	private void setVincoloChecklistLibera(Connection db) throws SQLException {
		boolean esito = false;
		PreparedStatement pst = db.prepareStatement("select * from get_cu_richiede_checklist_libera(?)");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			esito = rs.getBoolean(1);
		checklistLibera = esito;
	}
	private void setVincoloChecklistMacelli(Connection db) throws SQLException { 
		boolean esito = false;
		PreparedStatement pst = db.prepareStatement("select * from get_cu_richiede_checklist_macelli(?)");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			esito = rs.getBoolean(1);
		vincoloChecklistMacelli = esito;
		
	}
	
	private void setVincoloAMR(Connection db) throws SQLException {
		boolean esito = false;
		PreparedStatement pst = db.prepareStatement("select * from is_controllo_amr(?)");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			esito = rs.getBoolean(1);
		AMR = esito;
	}
		
	private void setVincoloAcquacoltura(Connection db) throws SQLException {
		boolean esito = false;
		PreparedStatement pst = db.prepareStatement("select * from is_controllo_acquacoltura(?)");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			esito = rs.getBoolean(1);
		acquacoltura = esito;
	}
	
	public void queryRecordIdLineaAttivitaNew(Connection db, int id) throws SQLException {
		if (id == -1) {
			this.id=-1 ;
		}
		else
		{
			String sql = "";
			//Es. get_linee_attivita(812549, 'organization', false, 1087175) 
			sql = "select id from public.get_linee_attivita(?,?,false,?)";
			PreparedStatement pst = db.prepareStatement(sql);
			
			if (idStabilimento > 0){
				pst.setInt(1, idStabilimento);
				pst.setString(2, "opu_stabilimento");
				
			} else if(altId>0 &&  DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_OPU_RICHIESTE)
			{
				pst.setInt(1, altId);
				pst.setString(2, "suap_ric_scia_stabilimento");
			}
			else if(altId>0 &&  DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_SINTESIS)
			{
				pst.setInt(1, altId);
				pst.setString(2, "sintesis_stabilimento");
			}
			else if(altId>0 &&  DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_ANAGRAFICA_STABILIMENTI)
			{
				pst.setInt(1, altId);
				pst.setString(2, "anagrafica.stabilimenti");
			}
			else if(idApiario>0)
			{
				pst.setInt(1, idApiario);
				pst.setString(2, "apicoltura_imprese");
			}
			else {

				pst.setInt(1, orgId);
				pst.setString(2, "organization");
			}
			
			pst.setInt(3, this.getId());
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
					id_imprese_linee_attivita = rs.getInt(1);
					rs.close();
					pst.close();
			}
		}
				
		if (this.id == -1) {
				throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
	}
	
	public ArrayList<String> getCFList(ActionContext ctx, String idComponenti, int lenPrelev, Connection db) throws SQLException
	{
		ArrayList<String>  cfs = new  ArrayList<String> ();

		//Ricerco prima per id...
		String cf = "" ;
		ApplicationPrefs prefs = (ApplicationPrefs) ctx.getServletContext().getAttribute("applicationPrefs");
		String ceDriver = prefs.get("GATEKEEPER.DRIVER");
		String ceHost = prefs.get("GATEKEEPER.URL");
		String ceUser = prefs.get("GATEKEEPER.USER");
		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

		ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
		SystemStatus thisSystem = null; 
		HashMap sessions = null;
		thisSystem = (SystemStatus) ((Hashtable) ctx.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
		
		for(String idp : idComponenti.split(",")) {
			cf = "" ;
			
			

			
				cf = thisSystem.getUser(Integer.parseInt(idp)).getContact().getCodiceFiscale() ;

			

			cfs.add(cf+",");
		}


		

		return cfs;

	}


	
	public void getStruttureControllareAurotiraCompetenti(Connection db)
	{
		String sql = "select * from strutture_controllate_autorita_competenti where id_controllo = ? and enabled ";
		
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, this.getId());
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				OiaNodo n = new OiaNodo(db, rs.getInt("id_struttura"),true);
				listaStruttureControllareAutoritaCompetenti.put(rs.getInt("id_struttura"), n);
			}
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
		}
		
		
	}
	
	public void insertStruttureControllareAurotiraCompetenti(Connection db)
	{
		String sql = "insert into strutture_controllate_autorita_competenti ( id_controllo ,id_struttura,entered) values (?,?,current_timestamp)";
		PreparedStatement pst = null ;
		
		try
		{
			pst = db.prepareStatement("update strutture_controllate_autorita_competenti set modified = current_timestamp , enabled=false where id_controllo =?");
			pst.setInt(1, this.getId());
			pst.execute();
			
			pst = db.prepareCall(sql);
			Iterator<Integer> it = listaStruttureControllareAutoritaCompetenti.keySet().iterator();
			while (it.hasNext())
			{
				int codiceInternoStruttura = it.next();
				pst.setInt(1, this.getId());
				pst.setInt(2, codiceInternoStruttura);
				pst.execute();
			}
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
		}
		
		
	}

	public ArrayList<NucleoIspettivo> getNucleoasList()
	{
		ArrayList<NucleoIspettivo>  nucleo = new  ArrayList<NucleoIspettivo> ();
		int i = 1 ;
		if ( (Integer)this.nucleoIspettivo != null && this.nucleoIspettivo != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivo,this.componenteNucleo,this.componentenucleoid_uno));

		}
		if ( (Integer)this.nucleoIspettivoDue != null && this.nucleoIspettivoDue != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivoDue,this.componenteNucleoDue,this.componentenucleoid_due));
		}
		if ( (Integer)this.nucleoIspettivoTre != null && this.nucleoIspettivoTre != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivoTre,this.componenteNucleoTre,this.componentenucleoid_tre));
		}
		if ( (Integer)this.nucleoIspettivoQuattro != null && this.nucleoIspettivoQuattro != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivoQuattro,this.componenteNucleoQuattro,this.componentenucleoid_quattro));
		}
		if ( (Integer)this.nucleoIspettivoCinque != null && this.nucleoIspettivoCinque != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivoCinque,this.componenteNucleoCinque,this.componentenucleoid_cinque));;
		}
		if ( (Integer)this.nucleoIspettivoSei != null && this.nucleoIspettivoSei != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivoSei,this.componenteNucleoSei,this.componentenucleoid_sei));
		}
		if ( (Integer)this.nucleoIspettivoSette != null && this.nucleoIspettivoSette != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivoSette,this.componenteNucleoSette,this.componentenucleoid_sette));
		}
		if ( (Integer)this.nucleoIspettivoOtto != null && this.nucleoIspettivoOtto != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivoOtto,this.componenteNucleoOtto,this.componentenucleoid_otto));
		}
		if ( (Integer)this.nucleoIspettivoNove != null && this.nucleoIspettivoNove != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivoNove,this.componenteNucleoNove,this.componentenucleoid_nove));
		}
		if ( (Integer)this.nucleoIspettivoDieci != null && this.nucleoIspettivoDieci != -1)
		{
			nucleo.add(new NucleoIspettivo(this.nucleoIspettivoDieci,this.componenteNucleoDieci,this.componentenucleoid_dieci));
		}


		return nucleo;
	}
	/**
	 * RITORNA L'ID ASL ASSOCIATA AL LOGO DA VISUALIZZARE NEL VERBALE 
	 * MODELLO CINQUE. SE ESISTE ALMENO UN NURECU CHE FA PARTE
	 * DEL NUCLEO RITORNO -1
	 * ALTRIMENTI RITORNA L'ASL DEL CONTROLLO UFFICIALE
	 * @return
	 */
	public int getIdAslVerbale()
	{
		int idAsl = -1 ;
		ArrayList<NucleoIspettivo> listaNucleo = getNucleoasList();
		for (NucleoIspettivo nucleo : listaNucleo)
		{
			if (nucleo.getNucleo() == 21 )
			{
				return idAsl;

			}
		}
		return siteId;
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



	public HashMap<Integer, String> getLisaElementibpi() {
		return lisaElementibpi;
	}


	public void setLisaElementibpi(HashMap<Integer, String> lisaElementibpi) {
		this.lisaElementibpi = lisaElementibpi;
	}


	public HashMap<Integer, String> getLisaElementihaccp() {
		return lisaElementihaccp;
	}


	public void setLisaElementihaccp(HashMap<Integer, String> lisaElementihaccp) {
		this.lisaElementihaccp = lisaElementihaccp;
	}


	public HashMap<Integer, OiaNodo> getLista_uo_ispezione() {
		return lista_uo_ispezione;
	}


	public void setLista_uo_ispezione(HashMap<Integer, OiaNodo> lista_uo_ispezione) {
		this.lista_uo_ispezione = lista_uo_ispezione;
	}


	public void insertOggettoControllo(Connection db , String s[]) throws SQLException
	{


		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tipocontrolloufficialeimprese("+
				"idcontrollo, tipo_audit, bpi, haccp, tipoispezione, pianomonitoraggio,"+ 
				"ispezione,audit_tipo)  VALUES (?,?, ?, ?, ?, ?, ?, ?)");
		PreparedStatement pst1=db.prepareStatement("delete from tipocontrolloufficialeimprese where idcontrollo =? and ispezione >0");
		pst1.setInt(1, id);
		pst1.execute();
		PreparedStatement pst=db.prepareStatement(sql.toString());

		pst.setInt(1, id);

		if(s!=null){
			for(int i=0;i<s.length;i++){
				if(s[i]!=null)
				{
					int idispezione=Integer.parseInt(s[i]);

					pst.setInt(2, -1);
					pst.setInt(3, -1);
					pst.setInt(4, -1);
					pst.setInt(5, -1);

					pst.setInt(6,-1);
					pst.setInt(7, idispezione);
					pst.setInt(8, -1);
					pst.execute();  

				}
			}
		}

	}


	public void insertUnitaOperative(Connection db,int tipoControllo) throws SQLException
	{
		PreparedStatement pst = null ;
		StringBuffer sql = new StringBuffer();
		pst = db.prepareStatement("delete from unita_operative_controllo where id_controllo="+id);
		pst.execute();
		if(uo!=null && uo.length>0 && tipoControllo!=4)
		{

			try {


				sql.append("INSERT INTO unita_operative_controllo(id_controllo,id_unita_operativa) values (?,?)");



				for(int i = 0 ; i <uo.length; i++)
				{
					if(!uo[i].equals("-1"))
					{
						pst = db.prepareStatement(sql.toString());

						pst.setInt(1, id);
						pst.setInt(2, Integer.parseInt(uo[i]));
						pst.execute();
					}

				}


			} catch (SQLException e) {
				db.rollback();

			}
		}

	}



	public ArrayList<OiaNodo> getLista_unita_operative() {
		return lista_unita_operative;
	}


	public void setLista_unita_operative(ArrayList<OiaNodo> lista_unita_operative) {
		this.lista_unita_operative = lista_unita_operative;
	}


	public void buildUnitaOperative(Connection db)
	{

		ArrayList<String> listauo = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id_unita_operativa,n.pathdes  as descrizione_lunga from unita_operative_controllo "
				+ "join dpat_strutture_asl n on unita_operative_controllo.id_unita_operativa = n.id  "
				+ "where id_controllo = ? ");
		
		
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try {

			pst = db.prepareStatement(sql.toString());
			pst.setInt(1, id);
			//pst.setInt(2, id);
			rs = pst.executeQuery();
			while (rs.next())
			{

				OiaNodo nn = new OiaNodo();
				nn.setId(rs.getInt("id_unita_operativa"));
				nn.setDescrizione_lunga(rs.getString("descrizione_lunga"));

				listauo.add(""+rs.getInt("id_unita_operativa"));
				//lista_unita_operative.add(OiaNodo.load_nodo(""+rs.getInt("id_unita_operativa"), db));
				lista_unita_operative.add(nn);
			}

			uo = new String[listauo.size()];
			int indice = 0 ;
			for (String s : listauo)
			{
				uo[indice] = s ;
				indice ++ ;
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	public void buildUnitaOperativeScadenz(Connection db)
	{

		ArrayList<String> listauo = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id_unita_operativa,descrizione_lunga from unita_operative_controllo join oia_nodo n on n.id = id_unita_operativa where id_controllo = ? ");
		PreparedStatement pst = null ;
		String descrizione_lunga = "" ;
		ResultSet rs = null ;
		try {

			pst = db.prepareStatement(sql.toString());
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next())
			{
				listauo.add(""+rs.getInt("id_unita_operativa"));

				descrizione_lunga = rs.getString("descrizione_lunga");
				OiaNodo n = new OiaNodo();
				n.setDescrizione_lunga(descrizione_lunga);
				lista_unita_operative.add(n);

			}

			uo = new String[listauo.size()];
			int indice = 0 ;
			for (String s : listauo)
			{
				uo[indice] = s ;
				indice ++ ;
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	public void updateTipocontrollo(Connection db,int tipoControllo,ActionContext context) throws SQLException{


		PreparedStatement pst = db.prepareStatement("select tipoispezione,pianomonitoraggio,id from  tipocontrolloufficialeimprese where idcontrollo = ? and enabled and tipoispezione>0");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		int i =1 ;
		while (rs.next())
		{
			int tipoIsp = rs.getInt("tipoispezione");
			int piano = rs.getInt("pianomonitoraggio");
			int id = rs.getInt("id");

			if (piano>0)
			{
				PreparedStatement pst2 = db.prepareStatement("update tipocontrolloufficialeimprese set pianomonitoraggio = ?, id_unita_operativa = ? where id = ? ");
				if (context.getRequest().getParameter("uo"+i)!= null && !"".equals(context.getRequest().getParameter("uo"+i)))
				{
					pst2.setInt(1, Integer.parseInt(context.getRequest().getParameter("piano_monitoraggio"+i)));
					pst2.setInt(2, Integer.parseInt(context.getRequest().getParameter("uo"+i)));
					pst2.setInt(3, id);
					pst2.execute();
					i++;
				}
			}
			else
			{
				PreparedStatement pst2 = db.prepareStatement("update tipocontrolloufficialeimprese set id_unita_operativa = ? where id = ?");

				if (context.getRequest().getParameter("per_condo_di"+tipoIsp)!= null && !"".equals(context.getRequest().getParameter("per_conto_di"+tipoIsp)))
				{
					pst2.setInt(1, Integer.parseInt(context.getRequest().getParameter("per_condo_di"+tipoIsp)));
					pst2.setInt(2, id);
					pst2.execute();
				}

			}

		}

	}
	public void insertTipocontrollo(Connection db,int tipoControllo,ActionContext context) throws SQLException{

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tipocontrolloufficialeimprese("+
				"idcontrollo, tipo_audit, bpi, haccp, tipoispezione, pianomonitoraggio,"+ 
				"ispezione,audit_tipo,id_unita_operativa,oggetto_audit,enabled,modified,modifiedby,id_lookup_condizionalita, audit_di_followup)  VALUES (?,?, ?, ?, ?, ?, ?, ?,?,?,true,current_timestamp,?,?, ?)");
		PreparedStatement pst=db.prepareStatement(sql.toString());
		pst.setInt(1, id);

		if(tipoControllo==23 ||tipoControllo==7){// se si sta eseguendo un controllo di tipo audit
			pst.setInt(8, auditTipo);

			if(auditTipo==1){
				Iterator<Integer> itTipoAudit = tipoAudit.keySet().iterator();

				while (itTipoAudit.hasNext())
				{
					int tipoAudit = itTipoAudit.next();
					pst.setInt(2, tipoAudit) ;
					if(tipoAudit==2)
					{// se il tipo audit e' bpi

						Iterator<Integer> itBpi = lisaElementibpi.keySet().iterator();
						while (itBpi.hasNext())
						{
							int tipo_bpi = itBpi.next();
							pst.setInt(3, tipo_bpi);
							pst.setInt(4, -1);
							pst.setInt(5, -1);
							pst.setInt(6, -1);
							pst.setInt(7, -1);
							pst.setInt(9, -1);
							pst.setInt(10, -1);
							pst.setInt(11, ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId());
							pst.setInt(12, -1);
							pst.setObject(13, null);



							pst.execute();
						}
					}
					else{
						if(tipoAudit==3)
						{// se tipo audit e' haccp
							Iterator<Integer> itHaccp = lisaElementihaccp.keySet().iterator();
							while (itHaccp.hasNext())
							{
								int tipo_haccp=itHaccp.next();
								pst.setInt(3,-1);
								pst.setInt(4, tipo_haccp);
								pst.setInt(5, -1);
								pst.setInt(6, -1);
								pst.setInt(7, -1);
								pst.setInt(9, -1);
								pst.setInt(10, -1);
								pst.setInt(11, ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId());
								pst.setInt(12, -1);
								pst.setObject(13, null);
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
							pst.setInt(9, -1);
							pst.setInt(10, -1);
							pst.setInt(11, ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId());
							pst.setInt(12, -1);
							pst.setObject(13, null);
							pst.execute();
						}
					}


				}

			}
			else
			{
				if(auditTipo==2 || auditTipo==3 || auditTipo==101 || auditTipo==102 || auditTipo==103 ){

					pst.setInt(2, -1);
					pst.setInt(3, -1);
					pst.setInt(4, -1);
					pst.setInt(5, -1);
					pst.setInt(6, -1);
					pst.setInt(7, -1);
					pst.setInt(9, -1);
					pst.setInt(12, -1);
					Iterator<Integer> itOggettoAudit = oggettoAudit.keySet().iterator();
					while (itOggettoAudit.hasNext())
					{
						int idOggettoAudit=itOggettoAudit.next();
						if (idOggettoAudit>0)
						{
							pst.setInt(10, idOggettoAudit);
							pst.setInt(11, ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId());
							pst.setBoolean(13, isAuditDiFollowUp());
							pst.execute();
						}
					}


				}
				else
				{
					pst.setInt(2, -1);
					pst.setInt(3, -1);
					pst.setInt(4, -1);
					pst.setInt(5, -1);
					pst.setInt(6, -1);
					pst.setInt(7, -1);
					pst.setInt(9, -1);
					pst.setInt(10, -1);
					pst.setInt(11, ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId());

					pst.setInt(12, -1);
					pst.setObject(13, null);

					pst.execute();
				}
			}

		}
		else{
			if(tipoControllo==4 || tipoControllo==3 ){// se si sta eseguendo un tipo controllo ispezione o audit


				pst.setInt(8, -1);
				pst.setInt(2, -1);
				pst.setInt(3, -1);
				pst.setInt(4, -1);

				Iterator<Integer> itTipoIspezione = tipoIspezione.keySet().iterator();
				while (itTipoIspezione.hasNext())
				{
					int tipo_ispezione = itTipoIspezione.next();
					pst.setInt(5, tipo_ispezione);
					RispostaDwrCodicePiano codiceInterno = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", tipo_ispezione);
					if("2a".equalsIgnoreCase(codiceInterno.getCodiceInterno()))// tipo ispe<ione scelta e' in monitoraggio
					{


						int id = 0 ;
						boolean isCondiz = false;
						for(Piano piano : pianoMonitoraggio)
						{

							RispostaDwrCodicePiano codiceInternopiano = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio",piano.getId());
							//if("982".equalsIgnoreCase(codiceInternopiano.getCodiceInterno()) && "true".equals(codiceInternopiano.getFlagCondizionalita()))// tipo ispe<ione scelta e' Condizionalita
							
							//La condizionalita' ora deve essere gestita anche sul piano C38
							if(("1483".equalsIgnoreCase(codiceInternopiano.getCodiceInterno()) || ("982".equalsIgnoreCase(codiceInternopiano.getCodiceInterno())) && "true".equals(codiceInternopiano.getFlagCondizionalita()) || "983".equalsIgnoreCase(codiceInternopiano.getCodiceInterno())) && "true".equals(codiceInternopiano.getFlagCondizionalita()))// tipo ispe<ione scelta e' Condizionalita
							{ 

								isCondiz = true ;

								

							}
							

								pst.setInt(6,piano.getId());
								pst.setInt(7, -1);
								pst.setInt(9, piano.getId_uo());
								pst.setInt(10, -1);
								pst.setInt(11, ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId());
								pst.setInt(12, -1);
								pst.setObject(13, null);
								if (piano.getId()>0)
								{
									pst.execute();  

								}
								

							
							id ++ ;
						}
						
						if (isCondiz==true)
						{
							Iterator <Integer > itKeyCond = tipo_ispezione_condizionalita.keySet().iterator();
							while (itKeyCond.hasNext())
							{
								int idLookupCond = itKeyCond.next();

								
								pst.setInt(5, -1);
								pst.setInt(6,-1);
								pst.setInt(7, -1);
								pst.setInt(9, -1);
								pst.setInt(10, -1);
								pst.setInt(11, ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId());
								pst.setInt(12,idLookupCond);
								pst.setObject(13, null);
								
									pst.execute();  

								
								
							

							}
						}

					}
					else
					{



						if (tipo_ispezione>0)
						{
							pst.setInt(5, tipo_ispezione);
							pst.setInt(6,-1);
							pst.setInt(7, -1);
							pst.setInt(9, Integer.parseInt(context.getParameter("per_condo_di"+tipo_ispezione)));
							pst.setInt(10, -1);
							pst.setInt(11, ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId());
							pst.setInt(12, -1);
							pst.setObject(13, null);
							pst.execute();
						}


					}

				}


			}
		}


	}




	//	public int getTipoPiano(Connection db,int idPiano) throws SQLException{
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
	//		return livello;	
	//
	//
	//	}


	public void getTipoAuditSelezionato(Connection db,int idControllo) throws SQLException{


		String sql="select distinct code,description from lookup_tipo_audit where code in (select distinct tipo_audit from tipocontrolloufficialeimprese where idcontrollo=? and enabled = true and tipo_audit > 0 )";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){

			String descriptionTipoAudit=rs.getString("description");
			int tipoAudit=rs.getInt("code");
			this.tipoAudit.put(tipoAudit, descriptionTipoAudit);

			if (tipoAudit == 2) // bpi
			{
				this.setBpiSelezionati(db, idControllo);

			}
			if (tipoAudit == 3) // haccp
			{
				this.setHaccpSelezionati(db, idControllo);

			}

			

		}



		String sql1="(select distinct audit_tipo, audit_di_followup from tipocontrolloufficialeimprese where idcontrollo=? and enabled = true and audit_tipo >0 )";
		PreparedStatement pst1=db.prepareStatement(sql1);
		pst1.setInt(1, idControllo);
		ResultSet rs1=pst1.executeQuery();
		if(rs1.next()){
			this.auditTipo=rs1.getInt("audit_tipo");
			if (this.auditTipo == 2 || this.auditTipo == 3 || this.auditTipo == 101 || this.auditTipo == 102 || this.auditTipo == 103)
			{
				setAuditDiFollowUp(rs1.getObject("audit_di_followup"));
				sql1="(select distinct ogg.* from tipocontrolloufficialeimprese t join lookup_oggetto_audit ogg on ogg.code = t.oggetto_audit where idcontrollo=? and t.enabled=true )";
				pst1=db.prepareStatement(sql1);
				pst1.setInt(1, idControllo);
				rs1=pst1.executeQuery();
				while (rs1.next())
				{
					oggettoAudit.put(rs1.getInt("code"), rs1.getString("description"));

				}
			}

		}



	}




	/*public void updateNonConformita(Connection db,String[] nc_formali,String[] nc_significative,String[] nc_gravi ) throws SQLException{
		String sql="delete from salvataggio_nonconformita where idticket="+id;
		PreparedStatement pst=db.prepareStatement(sql);
		pst.execute();
		//this.insertNonConformita(db, nc_formali, nc_significative, nc_gravi);


	}*/

	public void deleteTipiControlli(Connection db,int idControllo)throws SQLException{


		String sql="update tipocontrolloufficialeimprese set enabled=false where idcontrollo=?";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		pst.execute();

	}


	public int update(Connection db,String[] oggetto_controllo,ActionContext context) throws SQLException{
		int res=0;

		Date d = new Date(System.currentTimeMillis());
		String update="update ticket set " +
				"id_imprese_linee_attivita= ?," +
				"contributi_campioni_tamponi= ?," +
				"ncrilevate=?, " +
				"codice_allerta=?," +
				"id_ldd=?," +
				"codice_buffer=?," +
				"ip_modified=?," +
				"modifiedby=? ," +
				"modified=? ";

		update += ",contributi_importazione_scambio=?," +
				"contributi_allarme_rapido =? ," +
				"contributi_verifica_risoluzione_nc = ?," +
				"contributi_macellazione = ? ," +
				"contributi_macellazione_urgenza = ? ," +
				"contributi_rilascio_certificazione = ? " ;



		update +=		",distribuzione_partita = ?," +
				"destinazione_distribuzione = ?," +
				"id_file_allegato = ?";


		update +=  		",comunicazione_rischio = ?, " +
				"procedura_richiamo = ? ," +
				"motivo_procedura_richiamo = ? ," +
				"allerta_notes = ? ," +
				"procedura_ritiro = ?";
		if (esitoDeclassamento != null && !esitoDeclassamento.equals(""))
		{

			update += ",esito_declassamento=?,declassamento=?";
		}
		if(esitoControllo!=-1)
		{
			update += ",esito_controllo = ?";
		}
		if(esitoControllo==7)
		{

			update += ",data_ddt=?,num_ddt=?,quantita_partita=?,unita_misura_text= ?";

		}
		if(esitoControllo==4 || esitoControllo==5 || esitoControllo==6 || esitoControllo==8)
		{

			update +=",quantita_partita= ?,unita_misura_text= ? ";

		}
		if(esitoControllo==10 || esitoControllo==11 || esitoControllo==14)
		{

			update += ",quantita_partita_bloccata= ?,unita_misura_text= ? ";


		}


		if(azioneArticolo!=-1)
		{
			update += ",articolo_azioni= ? ";

		}

		if(quantitativo>0)
		{
			update += ",quantitativo= ? ";

		}
		if(quintali>0)
		{
			update += ",quintali= ? ";

		}

		if(comunicazioneRischio==true)

		{
			update += ",note_rischio = regexp_replace(?, '''|\n|\r', ' ', 'g') ";
		}

		if (flag_preavviso !=null)
			update += ",flag_preavviso=?";


		update += ",data_preavviso_ba=?";


		if (flag_checklist !=null)
			update += ",flag_checklist=?";

		//commento al 214
		//if (misureFormative !=null)
		//update += ",misure_formative=?";
		
		//if (misureRestrittive !=null)
		//update += ",misure_restrittive=?";
		
		//if (misureRiabilitative !=null)
		//update += ",misure_riabilitative=?";
		
		//commento al 247
		if (apiariSelezionati!=null)
		update += ",apiari_selezionati=?";
		if (apiariSelezionatiMotivo!=null)
			update += ",apiari_selezionati_motivo=?";
		if (apiariSelezionatiMotivoAltro!=null)
			update += ",apiari_selezionati_motivo_altro=?";
		if (apiariSelezionatiAlveariControllati>-1)
			update += ",apiari_selezionati_alveari_controllati=?";
		if (apiariSelezionatiEsito!=null)
			update += ",apiari_selezionati_esito=?";
		if (apiariSelezionatiEsitoNote!=null)
			update += ",apiari_selezionati_esito_note=?";


		update = update+" where ticketid="+id;
		int i = 0 ;
		PreparedStatement pst=db.prepareStatement(update);



		pst.setInt(++i, id_imprese_linee_attivita);
		pst.setDouble(++i, contributi_seguito_campioni_tamponi);
		pst.setBoolean(++i, ncrilevate);
		pst.setString(++i, codiceAllerta);
		pst.setInt(++i, idLdd);
		pst.setString(++i, codiceBuffer);
		pst.setString(++i, super.getIpModified());
		pst.setInt(++i, modifiedBy);
		pst.setDate(++i, d);
		pst.setDouble(++i, contributi_importazione_scambio);
		pst.setDouble(++i, contributi_allarme_rapido);
		pst.setDouble(++i, contributi_verifica_risoluzione_nc);
		pst.setDouble(++i, contributi_macellazione);
		pst.setDouble(++i, contributi_macellazione_urgenza);

		pst.setDouble(++i, contributi_rilascio_certificazione);
		pst.setInt(++i, distribuzionePartita);
		pst.setInt(++i, destinazioneDistribuzione);
		pst.setInt(++i, idFileAllegato);
		pst.setBoolean(++i, comunicazioneRischio);
		pst.setInt(++i, proceduraRichiamo);
		pst.setString(++i, motivoProceduraRichiamo);
		pst.setString(++i, allertaNotes);
		pst.setInt(++i, proceduraRitiro);
		if (esitoDeclassamento != null && !esitoDeclassamento.equals(""))
		{
			pst.setString(++i, esitoDeclassamento);
			pst.setInt(++i, declassamento);
		}
		if(esitoControllo!=-1)
		{
			pst.setInt(++i, esitoControllo);
		}
		if(esitoControllo==7)
		{

			pst.setTimestamp(++i, dataddt);
			pst.setString(++i, numDDt);
			pst.setString(++i, quantitaPartita);
			pst.setString(++i, unitaMisura);

		}
		if(esitoControllo==4 || esitoControllo==5 || esitoControllo==6 || esitoControllo==8)
		{

			pst.setString(++i, quantitaPartita);
			pst.setString(++i, unitaMisura);



		}
		if(esitoControllo==10 || esitoControllo==11 || esitoControllo==14)
		{

			pst.setString(++i, quantitaBloccata);
			pst.setString(++i, unitaMisura);



		}


		if(azioneArticolo!=-1)
		{
			pst.setInt(++i, azioneArticolo);

		}
		if(quantitativo>0)
		{
			pst.setInt(++i, quantitativo);

		}
		if(quintali>0)
		{
			pst.setDouble(++i, quintali);

		}

		if(comunicazioneRischio==true)
		{
			pst.setString(++i, noteRischio);
		}

		if (flag_preavviso !=null)
			pst.setString(++i, flag_preavviso);


		pst.setTimestamp(++i, data_preavviso_ba);

		if (flag_checklist !=null)
			pst.setString(++i, flag_checklist);


		//commento al 214
		//if (misureFormative !=null)
		//pst.setString(++i, misureFormative);
		
		//if (misureRestrittive !=null)
		//pst.setString(++i, misureRestrittive);
		
		//if (misureRiabilitative !=null)
		//pst.setString(++i, misureRiabilitative);
		
		//commento al 247
		if (apiariSelezionati!=null)
			pst.setString(++i, apiariSelezionati);
		if (apiariSelezionatiMotivo!=null)
			pst.setString(++i, apiariSelezionatiMotivo);
		if (apiariSelezionatiMotivoAltro!=null)
			pst.setString(++i, apiariSelezionatiMotivoAltro);
		if (apiariSelezionatiAlveariControllati>-1)
			pst.setInt(++i, apiariSelezionatiAlveariControllati);
		if (apiariSelezionatiEsito!=null)
			pst.setString(++i, apiariSelezionatiEsito);
		if (apiariSelezionatiEsitoNote!=null)
			pst.setString(++i, apiariSelezionatiEsitoNote);

		pst.execute();

		res=this.update(db,true);
		super.update(db);
		if(modificabile.equals("si"))
		{
			this.deleteTipiControlli(db, this.id);
			insertTipocontrollo(db,this.tipoCampione,context);


		}
		else
		{
			updateTipocontrollo(db,this.tipoCampione,context);
		}

		insertUnitaOperative(db, this.tipoCampione);
		this.insertOggettoControllo(db, oggetto_controllo);

		this.calcoloTipoControllo(db);
		this.updateTipoControllo(db);

		return res;








	}

	private String modificabile ;

	public String getModificabile()
	{
		return modificabile ;
	}

	public void setModificabile(String mod)
	{
		if(mod == null || "null".equals(modificabile))
			modificabile = "no";
		else

			modificabile = mod;
	}


	public void getTipoIspezioneSelezionato(Connection db,int idControllo) throws SQLException{

		this.tipoIspezioneCodiceInterno = new ArrayList<String>();
		this.tipoIspezioneCodiceInternoUnivoco = new ArrayList<String>();

		LookupList tipoIspezioneLookup = new LookupList();
		tipoIspezioneLookup.setTable("lookup_tipo_ispezione");
		tipoIspezioneLookup.buildListWithEnabled(db);

		this.setPianoMonitoraggiSelezionato(db, idControllo);
		this.setCondizionalita(db, idControllo);
		String sql="select distinct tc.id,code,description,id_unita_operativa,n.pathdes as descrizione_lunga from lookup_tipo_ispezione t join tipocontrolloufficialeimprese tc on tc.tipoispezione =t.code join dpat_Strutture_asl n on n.id = tc.id_unita_operativa  where idcontrollo=? and tc.enabled=true ";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rs=pst.executeQuery();
		boolean isPianoMonitoraggio = false ;
		while(rs.next()){

			String descriptionTipoIspezione=rs.getString("description");
			int tipoIspezione=rs.getInt("code");
			int idtipocontrolloufficialeimprese = rs.getInt("id");

			this.tipoIspezione.put(tipoIspezione, descriptionTipoIspezione);

			System.out.println("TIPO ISPEZIONE : "+tipoIspezione + " ----- CODICE INTERNO : "+tipoIspezioneLookup.getElementfromValue(tipoIspezione).getCodiceInterno().toLowerCase());
			if (!this.tipoIspezioneCodiceInterno.contains(tipoIspezioneLookup.getElementfromValue(tipoIspezione).getCodiceInterno().toLowerCase()))
			{

				this.tipoIspezioneCodiceInterno.add(tipoIspezioneLookup.getElementfromValue(tipoIspezione).getCodiceInterno().toLowerCase());
			
				
			}
			
			if (!this.tipoIspezioneCodiceInternoUnivoco.contains(tipoIspezioneLookup.getElementfromValue(tipoIspezione).getCodiceInternoUnivoco().toLowerCase()))
			{

				this.tipoIspezioneCodiceInternoUnivoco.add(tipoIspezioneLookup.getElementfromValue(tipoIspezione).getCodiceInternoUnivoco().toLowerCase());
			
				
			}
			

//			if(tipoIspezioneLookup.getElementfromValue(tipoIspezione).getCodiceInterno().toLowerCase().equals("2a")) // piano di monitoraggio
//			{
				//OiaNodo nodo = OiaNodo.load_nodo(rs.getInt("id_unita_operativa")+"", db);
				OiaNodo nodo = new OiaNodo();
				nodo.setId(rs.getInt("id_unita_operativa"));
				nodo.setDescrizione_lunga(rs.getString("descrizione_lunga"));

				nodo.getListaComponentiNucleoIspettivo().setId_tipo_controllo_uff_imprese(idtipocontrolloufficialeimprese);

				this.lista_uo_ispezione.put(tipoIspezione,nodo );


//			}





		}
		System.out.println("SIZECODICI  INTERNI  "+this.tipoIspezioneCodiceInterno.size());


	}
	
	
	
	
	
	
	
	
	public ArrayList<ArrayList<String>> getTipoIspezioneSelezionatoString(Connection db,int idControllo) throws SQLException
	{
		ArrayList<ArrayList<String>> motivi = new ArrayList<ArrayList<String>>();
		  String sql=" select distinct case " + 
		  		"                     when  tc.pianomonitoraggio >0 then tc.pianomonitoraggio " + 
		  		"                     when tc.tipoispezione <>89 and tc.tipoispezione > 0 then tc.tipoispezione " + 
		  		"                     else -1 " + 
		  		"                end as code, " + 
		  		" ((((upper(a.descrizione) || ' '::text) || ' - '::text) ||       " + 
		  		" CASE            " + 
		  		" WHEN piano.alias_indicatore IS NOT NULL THEN piano.alias_indicatore      " + 
		  		" ELSE ''::text         " + 
		  		" END) || " + 
		  		"' >> '::text) || " + 
		  		"concat(piano.descrizione,CASE            " + 
		  		"			   WHEN piano.tipo_item_dpat<>3 THEN ' - MOTIVO NON VALIDO'         " + 
		  		"			   ELSE ''::text         " + 
		  		"			 END) " + 
		  		"			 as description, " + 
		  		" n.pathdes as descrizione_lunga  " + 
		  		" from ticket t " + 
		  		" join tipocontrolloufficialeimprese tc on tc.idcontrollo = t.ticketid and tc.enabled=true  and (tc.pianomonitoraggio > 0 or tc.tipoispezione > 0) and t.tipologia=3 and t.trashed_date is null and t.provvedimenti_prescrittivi = 4" + 
		  		" join dpat_Strutture_asl n on n.id = tc.id_unita_operativa  " + 
		  		" left join dpat_indicatore_new  piano on (piano.id = tc.pianomonitoraggio and tc.tipoispezione = 89) or (piano.id = tc.tipoispezione and tc.tipoispezione <> 89)  " + 
		  		" left join dpat_piano_attivita_new a on a.id = piano.id_piano_attivita " + 
		  		"where t.ticketid = ? ";
		  
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rs=pst.executeQuery();
		while(rs.next())
		{
			ArrayList<String> temp = new ArrayList<String>();
			String descriptionTipoIspezione = rs.getString("description");
			String descrizioneLungaPerContoDi = (rs.getString("descrizione_lunga")!=null)?(rs.getString("descrizione_lunga")):("");
			temp.add(descriptionTipoIspezione);
			temp.add(descrizioneLungaPerContoDi);
			motivi.add(temp);
		}
		return motivi;

	}
	
	
	

	public void getTipoIspezioneSelezionatoScadenz(Connection db,int idControllo) throws SQLException{


		this.setPianoMonitoraggiSelezionato(db, idControllo);

		String sql="select distinct code,description,id_unita_operativa,n.descrizione_lunga from lookup_tipo_ispezione t join tipocontrolloufficialeimprese tc on tc.tipoispezione =t.code join oia_nodo n on n.id = tc.id_unita_operativa where idcontrollo=? and tc.enabled=true ";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		System.out.println("QUERY INTERNA "+pst.toString());
		ResultSet rs=pst.executeQuery();
		boolean isPianoMonitoraggio = false ;
		while(rs.next()){

			String descriptionTipoIspezione=rs.getString("description");
			int tipoIspezione=rs.getInt("code");

			this.tipoIspezione.put(tipoIspezione, descriptionTipoIspezione);
			if(tipoIspezione!=2) // piano di monitoraggio
			{
				String uo = rs.getString("descrizione_lunga");
				OiaNodo n = new OiaNodo();
				n.setDescrizione_lunga(uo);


				this.lista_uo_ispezione.put(tipoIspezione, n);
			}





		}



	}

	public void setPianoMonitoraggiSelezionato(Connection db,int idControllo) throws SQLException{


		String sql ="select distinct tipi.id,p.code, p.description ,o.id,o.pathdes as descrizione_lunga,tipi.id_unita_operativa from lookup_piano_monitoraggio p  " +
				"join tipocontrolloufficialeimprese tipi on tipi.pianomonitoraggio = p.code " +
				"join dpat_strutture_asl o on o.id = tipi.id_unita_operativa where idcontrollo= ? and tipi.enabled = true ";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){

			Piano p = new Piano();
			int idtipocontrolloufficialeimprese = rst.getInt("id");
			p.setId(rst.getInt("code"));
			
			p.setDescrizione(rst.getString("description"));
			p.setId_uo(rst.getInt("id_unita_operativa"));
			p.setCodice_interno(ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio", p.getId()).getCodiceInterno());
			p.setFlagCondizionalita(Boolean.parseBoolean(ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio", p.getId()).getFlagCondizionalita()));
			p.setDesc_uo(rst.getString("descrizione_lunga")) ;
			p.getListaComponentiNucleoIspettivo().setId_tipo_controllo_uff_imprese(idtipocontrolloufficialeimprese);
			p.getListaComponentiNucleoIspettivo().buildList(db);
			pianoMonitoraggio.add(p);


		}

	}




	public void setCondizionalita(Connection db,int idControllo) throws SQLException{


		String sql ="select lc.code,lc.description from lookup_condizionalita lc  " +
				"join tipocontrolloufficialeimprese tipi on tipi.id_lookup_condizionalita = lc.code " +
				" where idcontrollo= ? and tipi.enabled = true ";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){

			this.tipo_ispezione_condizionalita.put(rst.getInt(1), rst.getString(2));


		}

	}






	public void setPianoispezioniSelezionate(Connection db,int idControllo) throws SQLException{


		String sql ="select code, description , level from lookup_ispezione where code in (select ispezione from tipocontrolloufficialeimprese where idcontrollo=?)";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){

			int level = rst.getInt("level");
			int code = rst.getInt("code");
			String desc = rst.getString("description");

			if (lisaElementi_ispezioni.get(level)==null)
			{
				lisaElementi_ispezioni.put(level, new HashMap<Integer, String > ());
			}

			HashMap<Integer, String > l = lisaElementi_ispezioni.get(level) ;
			l.put(code,desc);
			lisaElementi_ispezioni.put(level,l);



		}




	}



	public void setBpiSelezionati(Connection db,int idControllo) throws SQLException{


		String sql ="select code, description from lookup_bpi where code in (select bpi from tipocontrolloufficialeimprese where idcontrollo=? and enabled=true  and tipo_audit > 0)";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){

			this.lisaElementibpi.put(rst.getInt(1),rst.getString(2));

		}




	}





	public void setHaccpSelezionati(Connection db,int idControllo) throws SQLException{


		String sql ="select code,description from lookup_haccp where code in (select haccp from tipocontrolloufficialeimprese where idcontrollo=? and enabled = true and tipo_audit > 0)";
		PreparedStatement pst=db.prepareStatement(sql);
		pst.setInt(1, idControllo);
		ResultSet rst=pst.executeQuery();
		while(rst.next()){

			this.lisaElementihaccp.put(rst.getInt(1) , rst.getString(2));

		}

	}

	/*
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
}*/


	public boolean updateCategoria(Connection db) throws SQLException
	{

		PreparedStatement pst=db.prepareStatement("update ticket set categoria_rischio = ? , punteggio = ?,data_prossimo_controllo =?, isAggiornata_categoria=true where ticketid=? ");
		pst.setInt(1, categoriaRischio);
		pst.setInt(2, punteggio);
		pst.setTimestamp(3, dataProssimoControllo);
		pst.setInt(4, id);
		pst.execute();

		return true;

	}
	
	public boolean updateLivello(Connection db) throws SQLException
	{

		PreparedStatement pst=db.prepareStatement("update ticket set livello_rischio = ? , punteggio = ?,data_prossimo_controllo =null, isAggiornata_categoria=true where ticketid=? ");
		pst.setInt(1, livelloRischio);
		pst.setInt(2, punteggio);
		pst.setInt(3, id);
		pst.execute();

		return true;

	}

	public synchronized boolean insert(Connection db, String[] ispezioni,ActionContext context) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			db.setAutoCommit(false);

			UserBean user = (UserBean)context.getSession().getAttribute("User");
			int livello=1 ;
			if (user.getUserRecord().getGruppo_ruolo()==2)
				livello=2;
			
			
		
			sql.append(
					"INSERT INTO ticket (id_imprese_linee_attivita, contact_id, problem, pri_code, " +
							"department_code, cat_code, scode, link_contract_id, " +
							"expectation, product_id, customer_product_id, " +
							"key_count, status_id, trashed_date, user_group_id, cause_id, " +
							"resolution_id, defect_id, escalation_level, resolvable, " +
					"resolvedby, resolvedby_department_code, state_id, site_id,ip_entered,ip_modified,comune_controllato,luogo_controllato_targa,");

			sql.append("note_altro, ");
			sql.append("ispezione_altro, ");
			sql.append("isAggiornata_categoria, ");

			if (intera_asl!=null)
			{
				sql.append("intera_asl, ");
			}

			if (containsIgnoreCase("16a", tipoIspezioneCodiceInterno)) //tossinfezione
			{


				if (alimentiSospetti != null && !"".equals(alimentiSospetti))
				{
					sql.append("alimenti_sospetti,");
				}
				if (ricoverati != null && !"".equals(ricoverati))
				{
					sql.append("ricoverati,");
				}
				if (soggettiCoinvolti != null && !"".equals(soggettiCoinvolti))
				{
					sql.append("soggetti_coinvolti,");
				}
				if (dataPasto != null )
				{
					sql.append("data_pasto,data_pasto_timezone,");
				}
				if (dataSintomi != null )
				{
					sql.append("data_sintomi,data_sintomi_timezone,");
				}
			}
			sql.append("data_nascita_conducente,luogo_nascita_conducente,citta_conducente,via_conducente,cap_conducente,prov_conducente,");

			if (codice_azienda!= null)
			{
				sql.append("codice_azienda,");
			}

			if (id_allevamento >0)
			{
				sql.append("id_allevamento,");
			}

			if (ragione_sociale_allevamento!= null)
			{
				sql.append("ragione_sociale_allevamento,");
			}

			if (containsIgnoreCase("8a", tipoIspezioneCodiceInterno))	//	verifica followup non conformita
			{
				sql.append("azione_followup,azione_followup_descrizione ,");
			}
			if (esitoDeclassamento != null && !esitoDeclassamento.equals(""))
			{

				sql.append("esito_declassamento,declassamento,");
			}

			sql.append("ispezioni_desc1,ispezioni_desc2,ispezioni_desc3,ispezioni_desc4,ispezioni_desc5,ispezioni_desc6,ispezioni_desc7,ispezioni_desc8,");

			//Per i trasporti
			sql.append("num_specie1,num_specie2,num_specie3,num_specie4,num_specie5,num_specie6,num_specie7,num_specie8,");
			sql.append("num_specie9,num_specie10,num_specie11,num_specie12,num_specie13,num_specie14,num_specie15,"
					+ "num_specie22, num_specie23,num_specie24,num_specie25,num_specie26, num_documento_accompagnamento, ");

			if (descrizioneCodiceAteco!=null && !descrizioneCodiceAteco.equals(""))
			{
				sql.append("descrizione_codice, ");
			}

			if (altId>0)
				sql.append("alt_id, ");
			
			if (farmacosorveglianza == true )
			{
				sql.append("id_farmacia, ");
			}
			else
			{
			
				if (orgId>0)
					sql.append("org_id, ");
				else if(idStabilimento > 0 && idApiario < 0){
					sql.append("id_stabilimento, ");
				} else {
					sql.append("id_apiario, ");
				}
					
			}

			if(!"".equals(codiceAteco))
			{
				sql.append("codiceAteco,");
			}

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


			if (data_preavviso != null)
			{
				sql.append("data_preavviso,");
			}
			if (protocollo_preavviso != null)
			{
				sql.append("protocollo_preavviso,");
			}
			if (protocollo_svincolo != null)
			{
				sql.append("protocollo_svincolo,");
			}
			if (tipologia_sottoprodotto != null)
			{
				sql.append("tipologia_sottoprodotto,");
			}

			if (data_comunicazione_svincolo != null)
			{
				sql.append("data_comunicazione_svincolo,");
			}
			sql.append("peso,"); 

			if(esitoControllo==7)
			{
				sql.append("data_ddt,num_ddt,quantita_partita,unita_misura_text,");
			}
			if(esitoControllo==4 || esitoControllo==5 || esitoControllo==6 || esitoControllo==8)
			{
				sql.append("quantita_partita,unita_misura_text,");
			}
			if(esitoControllo==10 || esitoControllo==11 || esitoControllo==14)
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
			

			if (quantitativo > 0) {
				sql.append("quantitativo, ");
			}

			if (quintali > 0) {
				sql.append("quintali, ");
			}


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
//			if ( nucleoIspettivo > -1) {
//				sql.append(", nucleo_ispettivo");
//			}
//			if ( nucleoIspettivoDue > -1) {
//				sql.append(", nucleo_ispettivo_due");
//			}
//			if ( nucleoIspettivoTre > -1) {
//				sql.append(", nucleo_ispettivo_tre");
//			}
//			if (componenteNucleo != null) {
//				sql.append(", componente_nucleo , componentenucleoid_uno ");
//			}
//			if (componenteNucleoDue != null) {
//				sql.append(", componente_nucleo_due , componentenucleoid_due");
//			}
//			if (componenteNucleoTre != null) {
//				sql.append(", componente_nucleo_tre , componentenucleoid_tre");
//			}
//			if ( nucleoIspettivoQuattro > -1) {
//				sql.append(", nucleo_ispettivo_quattro");
//			}
//			if ( nucleoIspettivoCinque > -1) {
//				sql.append(", nucleo_ispettivo_cinque");
//			}
//			if ( nucleoIspettivoSei > -1) {
//				sql.append(", nucleo_ispettivo_sei");
//			}
//			if ( nucleoIspettivoSette > -1) {
//				sql.append(", nucleo_ispettivo_sette");
//			}
//			if ( nucleoIspettivoOtto > -1) {
//				sql.append(", nucleo_ispettivo_otto");
//			}
//			if ( nucleoIspettivoNove > -1) {
//				sql.append(", nucleo_ispettivo_nove");
//			}
//			if ( nucleoIspettivoDieci > -1) {
//				sql.append(", nucleo_ispettivo_dieci");
//			}
//			if (componenteNucleoQuattro != null) {
//				sql.append(", componente_nucleo_quattro , componentenucleoid_quattro ");
//			}
//			if (componenteNucleoCinque != null) {
//				sql.append(", componente_nucleo_cinque , componentenucleoid_cinque");
//			}
//			if (componenteNucleoSei != null) {
//				sql.append(", componente_nucleo_sei ,componentenucleoid_sei ");
//			}
//			if (componenteNucleoSette != null) {
//				sql.append(", componente_nucleo_sette , componentenucleoid_sette ");
//			}
//			if (componenteNucleoOtto != null) {
//				sql.append(", componente_nucleo_otto ,componentenucleoid_otto ");
//			}
//			if (componenteNucleoNove != null) {
//				sql.append(", componente_nucleo_nove , componentenucleoid_nove ");
//			}
//			if (componenteNucleoDieci != null) {
//				sql.append(", componente_nucleo_dieci ,componentenucleoid_dieci");
//			}



			if (followUp != null) {
				sql.append(", follow_up");
			}

			if (codiceAllerta != null) {
				sql.append(", codice_allerta");
			}
			
			if (idLdd >0 ) {
				sql.append(", id_ldd");
			}

			if (codiceBuffer != null) {
				sql.append(", codice_buffer");
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
			if (idConcessionario != -1) {
				sql.append(", id_concessionario");
			}

			if (categoriaTrasportata != -1) {
				sql.append(", specietrasportata");
			}
			if (specieA != -1) {
				sql.append(", animalitrasportati");
			}


			if(nome_conducente != null && ! "".equals(nome_conducente))
			{
				sql.append(", nome_conducente");
			}
			if(cognome_conducente != null && ! "".equals(cognome_conducente))
			{
				sql.append(", cognome_conducente");
			}
			if(documento_conducente != null && ! "".equals(documento_conducente))
			{
				sql.append(", documento_conducente");
			}
			if(tipologia_controllo_cani>0)
			{
				sql.append(", tipologia_controllo_cani");
			}
			if(assetId>0)
			{
				sql.append(", link_asset_id");
			}

			sql.append(", contributi_allarme_rapido");
			sql.append(", contributi_verifica_risoluzione_nc");
			sql.append(", contributi_macellazione");
			sql.append(", contributi_macellazione_urgenza");
			sql.append(", contributi_rilascio_certificazione");
			sql.append(", tipo_sospetto");
			sql.append(", contributi_campioni_tamponi");
			sql.append(", contributi_importazione_scambio");



			sql.append(", ncrilevate");

			sql.append(", inserisci_continua");
			if(latitude!=0){
				sql.append(", latitudine");
			}
			if(longitude!=0){
				sql.append(", longitudine");
			}

			sql.append(", flag_mod5");

			if(headerAllegatoDocumentale!=null){
				sql.append(", header_allegato_documentale");
			}
			if (flag_preavviso!=null)
				sql.append(",flag_preavviso");

			if (data_preavviso_ba!=null)
				sql.append(",data_preavviso_ba");

			if (flag_checklist !=null)
				sql.append(",flag_checklist");
			
			//commento al 214
			
			//if (misureFormative !=null)
			//sql.append(",misure_formative");
			
			//if (misureRestrittive !=null)
			//sql.append(",misure_restrittive");
			
			//if (misureRiabilitative !=null)
			//sql.append(",misure_riabilitative");
			
			//commento al 247
			if (apiariSelezionati!=null)
				sql.append(",apiari_selezionati");
			if (apiariSelezionatiMotivo!=null)
				sql.append(",apiari_selezionati_motivo");
			if (apiariSelezionatiMotivoAltro!=null)
				sql.append(",apiari_selezionati_motivo_altro");
			if (apiariSelezionatiAlveariControllati>-1)
				sql.append(",apiari_selezionati_alveari_controllati");
			if (apiariSelezionatiEsito!=null)
				sql.append(",apiari_selezionati_esito");
			if (apiariSelezionatiEsitoNote!=null)
				sql.append(",apiari_selezionati_esito_note");
			
			if (idTarga >0)
				sql.append(",id_targa");
			
			sql.append(")");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
			sql.append("?, ?, ?,?,?,?,?, ");

			sql.append("?,");
			sql.append("?,");
			sql.append("?,");

			if (intera_asl!=null)
			{
				sql.append("?,");
			}
			if (containsIgnoreCase("16a", tipoIspezioneCodiceInterno)) //tossinfezione
			{

				if (alimentiSospetti != null && !"".equals(alimentiSospetti))
				{
					sql.append("?,");
				}
				if (ricoverati != null && !"".equals(ricoverati))
				{
					sql.append("?, ");
				}
				if (soggettiCoinvolti != null && !"".equals(soggettiCoinvolti))
				{
					sql.append("?, ");
				}
				if (dataPasto != null )
				{
					sql.append("?, ?,");
				}
				if (dataSintomi != null )
				{
					sql.append("?, ?,");
				}

			}
			sql.append("?,?,?,?,?,?,");
			if (codice_azienda!= null)
			{
				sql.append("?,");
			}
			if (id_allevamento >0)
			{
				sql.append("?,");
			}

			if (ragione_sociale_allevamento!= null)
			{
				sql.append("?,");
			}


			if (containsIgnoreCase("8a", tipoIspezioneCodiceInterno))	//	verifica followup non conformita
			{
				sql.append("?, ?,");
			}
			if (esitoDeclassamento != null && !esitoDeclassamento.equals(""))
			{

				sql.append("?,?,");
			}


			sql.append("?, ?,?,?,?,?,?,?,");
			sql.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ");

			if (descrizioneCodiceAteco!=null && !descrizioneCodiceAteco.equals(""))
			{
				sql.append("?, ");
			}

			if (altId>0)
				sql.append("?,");
			
			if (farmacosorveglianza == true )
			{
				sql.append("?,");
			}
			else
			{
				sql.append("?,");
			}
			if(!"".equals(codiceAteco))
			{
				sql.append("?,");
			}

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
			sql.append("?,");

			sql.append("?,?, ?, ");

			if(esitoControllo!=-1)
			{
				sql.append("?,");
			}
			if (data_preavviso != null)
			{
				sql.append("?,");
			}
			if (protocollo_preavviso != null)
			{
				sql.append("?,");
			}
			if (protocollo_svincolo != null)
			{
				sql.append("?,");
			}
			if (tipologia_sottoprodotto != null)
			{
				sql.append("?,");
			}

			if (data_comunicazione_svincolo != null)
			{
				sql.append("?,");
			}
			sql.append("?,");

			if(esitoControllo==7)
			{
				sql.append("?,?,?,?,");
			}
			if(esitoControllo==4 || esitoControllo==5 || esitoControllo==6 || esitoControllo==8)
			{
				sql.append("?,?,");
			}
			if(esitoControllo==10 || esitoControllo==11 || esitoControllo==14)
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


			
			sql.append( DatabaseUtils.getNextIntSql("ticket", "ticketid", livello)+",");
			

			if (quantitativo > 0) {
				sql.append("?,");
			}

			if (quintali > 0) {
				sql.append("?,");
			}

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
//			if (nucleoIspettivo > -1) {
//				sql.append(", ?");
//			}
//			if (nucleoIspettivoDue > -1) {
//				sql.append(", ?");
//			}
//			if (nucleoIspettivoTre > -1) {
//				sql.append(", ?");
//			}
//			if (componenteNucleo != null) {
//				sql.append(", ?,? ");			}
//			if (componenteNucleoDue != null) {
//				sql.append(", ?,? ");			}
//			if (componenteNucleoTre != null) {
//				sql.append(", ?,? ");			}
//			if (nucleoIspettivoQuattro > -1) {
//				sql.append(", ?");
//			}
//			if (nucleoIspettivoCinque > -1) {
//				sql.append(", ?");
//			}
//			if (nucleoIspettivoSei > -1) {
//				sql.append(", ?");
//			}
//			if (nucleoIspettivoSette > -1) {
//				sql.append(", ?");
//			}
//			if (nucleoIspettivoOtto > -1) {
//				sql.append(", ?");
//			}
//			if (nucleoIspettivoNove > -1) {
//				sql.append(", ?");
//			}
//			if (nucleoIspettivoDieci > -1) {
//				sql.append(", ?");
//			}
//			if (componenteNucleoQuattro != null) {
//				sql.append(", ?,? ");			}
//			if (componenteNucleoCinque != null) {
//				sql.append(", ?,? ");			}
//			if (componenteNucleoSei != null) {
//				sql.append(", ?,? ");			}
//			if (componenteNucleoSette != null) {
//				sql.append(", ?,? ");			}
//			if (componenteNucleoOtto != null) {
//				sql.append(", ?,? ");			}
//			if (componenteNucleoNove != null) {
//				sql.append(", ?,? ");			}
//			if (componenteNucleoDieci != null) {
//				sql.append(", ?,? ");
//			}

			if (followUp != null) {
				sql.append(", ? ");
			}
			if (codiceAllerta != null) {
				sql.append(", ? ");
			}
			
			if (idLdd >0) {
				sql.append(", ? ");
			}

			if (codiceBuffer != null) {
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
			if (idConcessionario != -1) {
				sql.append(",?");
			}
			if (categoriaTrasportata != -1) {
				sql.append(",?");
			}
			if (specieA != -1) {
				sql.append(",?");
			}
			if(nome_conducente != null && ! "".equals(nome_conducente))
			{
				sql.append(",?");
			}
			if(cognome_conducente != null && ! "".equals(cognome_conducente))
			{
				sql.append(",?");
			}
			if(documento_conducente != null && ! "".equals(documento_conducente))
			{
				sql.append(",?");
			}
			if(tipologia_controllo_cani>0)
			{
				sql.append(",?");
			}
			if(assetId>0)
			{
				sql.append(",?");
			}
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?"); //Aggiunto
			sql.append(", ?");

			if(inserisciContinua){
				sql.append(", true");
			}else{
				sql.append(", false");
			}
			if(latitude!=0){
				sql.append(", ?");
			}
			if(longitude!=0){
				sql.append(", ?");
			}
			sql.append(", ?");

			if(headerAllegatoDocumentale!=null){
				sql.append(", ?");
			}
			if(flag_preavviso!=null)
				sql.append(",?");

			if (data_preavviso_ba!=null)
				sql.append(",?");

			if(flag_checklist!=null)
				sql.append(",?");
			
			//commento al 214
			
			//if(misureFormative!=null)
			//sql.append(",?");
			
			//if(misureRestrittive!=null)
			//sql.append(",?");
			
			//if(misureRiabilitative!=null)
			//sql.append(",?");
			
			//commento al 247
			if (apiariSelezionati!=null)
				sql.append(",?");
			if (apiariSelezionatiMotivo!=null)
				sql.append(",?");
			if (apiariSelezionatiMotivoAltro!=null)
				sql.append(",?");
			if (apiariSelezionatiAlveariControllati>-1)
				sql.append(",?");
			if (apiariSelezionatiEsito!=null)
				sql.append(",?");
			if (apiariSelezionatiEsitoNote!=null)
				sql.append(",?");
			
			if (idTarga >0)
				sql.append(",?");
			
		
			sql.append(") RETURNING ticketid");

			int i = 0;
			
			 PreparedStatement pst = db.prepareStatement(sql.toString());

			DatabaseUtils.setInt(pst,++i, this.id_imprese_linee_attivita);
			DatabaseUtils.setInt(pst, ++i, this.getContactId());
			pst.setString(++i, this.getProblem());


			sql.append("note_altro, ");
			sql.append("ispezione_altro, ");
			sql.append("isAggiornata_categoria, ");
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			DatabaseUtils.setInt(pst, ++i, STATO_APERTO); //Aperto e' primo stato CU
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
			pst.setString( ++i, super.getIpEntered());
			pst.setString( ++i, super.getIpModified());
			
			pst.setString(++i, comuneControllo);
			pst.setString(++i, luogoControlloTarga);
			
			pst.setString(++i, noteAltrodiSistema);
			pst.setString(++i, ispezioneAltro);
			pst.setBoolean(++i, false);
			if (intera_asl!=null)
			{
				pst.setBoolean(++i, intera_asl);
			}
			if (containsIgnoreCase("16a", tipoIspezioneCodiceInterno)) //tossinfezione
			{

				if (alimentiSospetti != null && !"".equals(alimentiSospetti))
				{
					pst.setString(++i, alimentiSospetti);
				}
				if (ricoverati != null && !"".equals(ricoverati))
				{
					pst.setString(++i, ricoverati);
				}
				if (soggettiCoinvolti != null && !"".equals(soggettiCoinvolti))
				{
					pst.setString(++i, soggettiCoinvolti);
				}
				if (dataPasto != null )
				{
					pst.setTimestamp(++i,dataPasto);
					pst.setString(++i,dataPastoTimeZone);
				}
				if (dataSintomi != null )
				{
					pst.setTimestamp(++i,dataSintomi);
					pst.setString(++i,dataSintomiTimeZone);
				}

			}


			pst.setString(++i, data_nascita_conducente);
			pst.setString(++i, luogo_nascita_conducente);
			pst.setString(++i, citta_conducente);
			pst.setString(++i, via_connducente);
			pst.setString(++i, cap_conducente);
			pst.setString(++i, prov_conducente);

			if (codice_azienda!= null)
			{
				pst.setString(++i,codice_azienda);
			}
			if (id_allevamento >0)
			{
				pst.setInt(++i,id_allevamento);

			}

			if (ragione_sociale_allevamento!= null)
			{
				pst.setString(++i,ragione_sociale_allevamento);
			}


			if (containsIgnoreCase("8a", tipoIspezioneCodiceInterno))	//	verifica followup non conformita
			{
				pst.setBoolean( ++i,azione);
				pst.setString(++i,azione_descrizione);
			}
			if (esitoDeclassamento != null && !esitoDeclassamento.equals(""))
			{

				pst.setString(++i,esitoDeclassamento);
				pst.setInt(++i,declassamento);
			}

			pst.setString(++i,ispezioni_desc1);
			pst.setString(++i,ispezioni_desc2);
			pst.setString(++i,ispezioni_desc3);
			pst.setString(++i,ispezioni_desc4);
			pst.setString(++i,ispezioni_desc5);
			pst.setString(++i,ispezioni_desc6);
			pst.setString(++i,ispezioni_desc7);
			pst.setString(++i,ispezioni_desc8);

			pst.setInt(++i,num_specie1);
			pst.setInt(++i,num_specie2);
			pst.setInt(++i,num_specie3);
			pst.setInt(++i,num_specie4);
			pst.setInt(++i,num_specie5);
			pst.setInt(++i,num_specie6);
			pst.setInt(++i,num_specie7);
			pst.setInt(++i,num_specie8);
			pst.setInt(++i,num_specie9);
			pst.setInt(++i,num_specie10);
			pst.setInt(++i,num_specie11);
			pst.setInt(++i,num_specie12);
			pst.setInt(++i,num_specie13);
			pst.setInt(++i,num_specie14);
			pst.setInt(++i,num_specie15);
			pst.setInt(++i,num_specie22);
			pst.setInt(++i,num_specie23);
			pst.setInt(++i,num_specie24);
			pst.setInt(++i,num_specie25);
			pst.setInt(++i,num_specie26);
			pst.setString(++i,num_documento_accompagnamento);


			if (descrizioneCodiceAteco!=null && !descrizioneCodiceAteco.equals(""))
			{
				pst.setString(++i, descrizioneCodiceAteco);
			}
			
			if (altId>0)
				pst.setInt(++i, altId);
			if (farmacosorveglianza == true )
			{
				pst.setInt(++i, orgId);
			}
			else
			{
				if (orgId>0){
					if (DatabaseUtils.getTipologiaPartizione(db, orgId) == Ticket.ALT_ORGANIZATION)
						pst.setInt(++i, orgId);
					else
						pst.setInt(++i, -1);
				}
				else if(this.idStabilimento > 0 && this.idApiario < 0)
					pst.setInt(++i, idStabilimento);
				else 
					pst.setInt(++i, idApiario);

			}


			if(!"".equals(codiceAteco))
			{
				// Nel caso in cuo l'isperzione e' in sorveglianza non e' necessario il codice ateco


				pst.setString(++i, codiceAteco);
			}

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
			if (data_preavviso != null)
			{
				pst.setTimestamp(++i, data_preavviso);
			}
			if (protocollo_preavviso != null)
			{
				pst.setString(++i, protocollo_preavviso);
			}
			if (protocollo_svincolo != null)
			{
				pst.setString(++i, protocollo_svincolo);
			}
			if (tipologia_sottoprodotto != null)
			{
				pst.setString(++i, tipologia_sottoprodotto);
			}

			if (data_comunicazione_svincolo != null)
			{
				pst.setTimestamp(++i, data_comunicazione_svincolo);
			}
			pst.setDouble(++i, peso);

			if(esitoControllo==7)
			{
				pst.setTimestamp(++i, dataddt);
				pst.setString(++i,numDDt);
				pst.setString(++i, quantitaPartita);
				pst.setString(++i, unitaMisura);
			}
			if(esitoControllo==4 || esitoControllo==5 || esitoControllo==6 || esitoControllo==8)
			{
				pst.setString(++i, quantitaPartita);
				pst.setString(++i, unitaMisura);
			}
			if(esitoControllo==10 || esitoControllo==11 || esitoControllo==14)
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

//			if (id > -1) {
//				pst.setInt(++i, id);
//			}

			if (quantitativo > 0) {
				pst.setInt(++i, quantitativo);
			}

			if (quintali > 0) {
				pst.setDouble(++i, quintali);
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
//			if(nucleoIspettivo > -1)
//				pst.setInt(++i , nucleoIspettivo);
//			if(nucleoIspettivoDue > -1)
//				pst.setInt(++i , nucleoIspettivoDue);
//			if(nucleoIspettivoTre > -1)
//				pst.setInt(++i , nucleoIspettivoTre);
//			if (componenteNucleo != null) {
//				pst.setString(++i, componenteNucleo);
//				pst.setInt(++i, componentenucleoid_uno);
//			}
//			if (componenteNucleoDue != null) {
//				pst.setString(++i, componenteNucleoDue);
//				pst.setInt(++i, componentenucleoid_due);
//			}
//			if (componenteNucleoTre != null) {
//				pst.setString(++i, componenteNucleoTre);
//				pst.setInt(++i, componentenucleoid_tre);
//			}
//			if(nucleoIspettivoQuattro > -1)
//				pst.setInt(++i , nucleoIspettivoQuattro);
//			if(nucleoIspettivoCinque > -1)
//				pst.setInt(++i , nucleoIspettivoCinque);     
//			if(nucleoIspettivoSei > -1)
//				pst.setInt(++i , nucleoIspettivoSei);
//			if(nucleoIspettivoSette > -1)
//				pst.setInt(++i , nucleoIspettivoSette);
//			if(nucleoIspettivoOtto > -1)
//				pst.setInt(++i , nucleoIspettivoOtto);
//			if(nucleoIspettivoNove > -1)
//				pst.setInt(++i , nucleoIspettivoNove);
//			if(nucleoIspettivoDieci > -1)
//				pst.setInt(++i , nucleoIspettivoDieci);
//			if (componenteNucleoQuattro != null) {
//				pst.setString(++i, componenteNucleoQuattro);
//				pst.setInt(++i, componentenucleoid_quattro);
//			}
//			if (componenteNucleoCinque != null) {
//				pst.setString(++i, componenteNucleoCinque);
//				pst.setInt(++i, componentenucleoid_cinque);
//			}
//			if (componenteNucleoSei != null) {
//				pst.setString(++i, componenteNucleoSei);
//				pst.setInt(++i, componentenucleoid_sei);
//			}
//			if (componenteNucleoSette != null) {
//				pst.setString(++i, componenteNucleoSette);
//				pst.setInt(++i, componentenucleoid_sette);
//			}
//			if (componenteNucleoOtto != null) {
//				pst.setString(++i, componenteNucleoOtto);
//				pst.setInt(++i, componentenucleoid_otto);
//			}
//			if (componenteNucleoNove != null) {
//				pst.setString(++i, componenteNucleoNove);
//				pst.setInt(++i, componentenucleoid_nove);
//			}
//			if (componenteNucleoDieci != null) {
//				pst.setString(++i, componenteNucleoDieci);
//				pst.setInt(++i, componentenucleoid_dieci);
//			}


			if (followUp != null) {
				pst.setString(++i, followUp);
			}
			if (codiceAllerta != null) {
				pst.setString(++i, codiceAllerta);
			}
			
			if (idLdd >0) {
				pst.setInt(++i, idLdd);
			}
			if (codiceBuffer != null) {
				pst.setString(++i, codiceBuffer);
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
			if (idConcessionario != -1) {
				pst.setInt(++i, idConcessionario);
			}
			if (categoriaTrasportata != -1) {
				pst.setInt(++i, categoriaTrasportata);
			}
			if (specieA != -1) {
				pst.setInt(++i, specieA);
			}
			if(nome_conducente != null && ! "".equals(nome_conducente))
			{
				pst.setString(++i, nome_conducente);
			}
			if(cognome_conducente != null && ! "".equals(cognome_conducente))
			{
				pst.setString(++i, cognome_conducente);
			}
			if(documento_conducente != null && ! "".equals(documento_conducente))
			{
				pst.setString(++i, documento_conducente);
			}
			if(tipologia_controllo_cani>0)
			{
				pst.setInt(++i, tipologia_controllo_cani);
			}
			if(assetId>0)
			{
				pst.setInt(++i, assetId);
			}
			pst.setDouble(++i, contributi_allarme_rapido);
			pst.setDouble(++i, contributi_verifica_risoluzione_nc);
			pst.setDouble(++i, contributi_macellazione);
			pst.setDouble(++i, contributi_macellazione_urgenza);
			pst.setDouble(++i, contributi_rilascio_certificazione);
			//Aggiunta tipoSospetto
			pst.setString(++i, tipoSospetto);
			pst.setDouble(++i, contributi_seguito_campioni_tamponi);
			pst.setDouble(++i, contributi_importazione_scambio);

			pst.setBoolean(++i, ncrilevate);

			if(latitude!=0)
			{
				pst.setDouble(++i,latitude);

			}
			if(longitude != 0)
			{
				pst.setDouble(++i,longitude);
			}	

			pst.setString(++i,"true");

			if(headerAllegatoDocumentale!=null){
				pst.setString(++i,headerAllegatoDocumentale);
			}
			if(flag_preavviso!=null)
				pst.setString(++i,flag_preavviso);

			if (data_preavviso_ba!=null)
				pst.setTimestamp(++i,data_preavviso_ba);

			//pst.setString(++i, this.getPaddedId());
			if(flag_checklist !=null)
				pst.setString(++i,flag_checklist);
			
			//commento al 214
			
			//if (misureFormative !=null)
			//pst.setString(++i, misureFormative);
			
			//if (misureRestrittive !=null)
			//pst.setString(++i, misureRestrittive);
			
			//if (misureRiabilitative !=null)
			//pst.setString(++i, misureRiabilitative);
			
			//commento al 247
			if (apiariSelezionati!=null)
				pst.setString(++i, apiariSelezionati);
			if (apiariSelezionatiMotivo!=null)
				pst.setString(++i, apiariSelezionatiMotivo);
			if (apiariSelezionatiMotivoAltro!=null)
				pst.setString(++i, apiariSelezionatiMotivoAltro);
			if (apiariSelezionatiAlveariControllati>-1)
				pst.setInt(++i, apiariSelezionatiAlveariControllati);
			if (apiariSelezionatiEsito!=null)
				pst.setString(++i, apiariSelezionatiEsito);
			if (apiariSelezionatiEsitoNote!=null)
				pst.setString(++i, apiariSelezionatiEsitoNote);
			
			
			if (idTarga >0)
				pst.setInt(++i,idTarga);
			
		
			 ResultSet rs = pst.executeQuery();
			if (rs.next())
				this.id = rs.getInt("ticketid");
			
			
			


			//Update the rest of the fields
			// this.update(db, true);23
			this.insertTipocontrollo(db, tipoCampione,context);

			this.insertUnitaOperative(db, this.tipoCampione);
			this.insertOggettoControllo(db, ispezioni);
			this.calcoloTipoControllo(db);
			this.updateTipoControllo(db);
			this.insertNucleoIspettivo(db);

			db.commit();
			
			pst=db.prepareStatement("update ticket set id_controllo_ufficiale =trim(to_char( "+this.id+", '"+DatabaseUtils.getPaddedFromId(this.id)+"')) where ticketid = "+this.id);
			pst.execute();
			pst.close();
			
			db.commit();

		} catch (SQLException e) {
			
			if(e.getMessage().contains("un valore chiave duplicato viola il vincolo "))
			{
				db.rollback();
				db.setAutoCommit(true);
				this.insert( db,  ispezioni, context);
			}
			else
			{
			e.printStackTrace();

			db.rollback();
			}

			throw new SQLException(e.getMessage());
		} finally {

			db.setAutoCommit(true);

		}
		return true;
	}


	public void insert_struttura_asl(Connection db ,String[] listaAsl) throws SQLException
	{
		db.prepareStatement("delete from controlli_ufficiali_asl where id_controllo ="+this.id).execute();

		String insert = "insert into controlli_ufficiali_asl (id_controllo,id_nodo) values (?,?)" ;
		PreparedStatement pst = db.prepareStatement(insert);
		for (int i = 0 ; i < listaAsl.length; i++)
		{
			int id_nodo = Integer.parseInt(listaAsl[i]);
			pst.setInt(1, this.id);
			pst.setInt(2,id_nodo);
			pst.execute();

		}
	}

	/*public void  setStrutturaAsl(Connection db) 
	{


		if (intera_asl == false)
		{
			for (int i = 0 ; i <lista_struttura_asl.size() ; i++  )
				lista_struttura_asl.remove(i);

			try
			{
				String sel = "select distinct oia_nodo.* ,asl.short_description as asl_stringa, " +
				"ui.nome_utente as nome_responsabile		, " +
				"ui.cognome_utente as cognome_responsabile  , " +
				"ui.ruolo as ruolo_responsabile             , " +
				"ui.asl as asl_responsabile " +
				"from controlli_ufficiali_asl cuasl " +
				"join oia_nodo on (cuasl.id_nodo = oia_nodo.id ) " +
				"LEFT JOIN lookup_site_id asl ON oia_nodo.id_asl = asl.code " +
				"JOIN v_user_info ui ON oia_nodo.id_utente = ui.id_utente " +
				" where cuasl.id_controllo = ? ";
				PreparedStatement pst = db.prepareStatement(sel) ;
				pst.setInt(1, this.id);
				ResultSet rs = pst.executeQuery() ;
				while (rs.next())
				{
					OiaNodo nodo =  OiaNodo.loadResultSet2(rs,db);
					lista_struttura_asl.add(nodo);
				}

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

	}*/

	private void setDescrizioneOperatore ()
	{
		switch (tipologiaOperatore)
		{
		case 1 : 
		{
			tipologiaOperatoreDescr = "Impresa";
			break;
		}
		case 2 : 
		{
			tipologiaOperatoreDescr = "Allevamento";
			break;
		}
		case 3 : 
		{
			tipologiaOperatoreDescr = "Stabilimento";
			break;
		}
		case 8 : 
		{
			tipologiaOperatoreDescr = "CENTRI RIPRODUZIONE ANIMALE";
			break;
		}
		case 7 : 
		{
			tipologiaOperatoreDescr = "AZIENDE AGRICOLE";
			break;
		}
		case 12 : 
		{
			tipologiaOperatoreDescr = "Operatori non altrove";
			break;
		}

		case 97 : 
		{
			tipologiaOperatoreDescr = "S.O.A.";
			break;
		}
		case 800 : 
		{
			tipologiaOperatoreDescr = "O.S.M. Riconosciuti";
			break;
		}
		case 801 : 
		{
			tipologiaOperatoreDescr = "O.S.M. Registrati";
			break;
		}
		case 9 : 
		{
			tipologiaOperatoreDescr = "Trasporto Animale";
			break;
		}
		case 4 : 
		{
			tipologiaOperatoreDescr = "Operatore Abusivo";
			break;
		}
		case 13 : 
		{
			tipologiaOperatoreDescr = "Operatore Privato";
			break;
		}
		case 14 : 
		{
			tipologiaOperatoreDescr = "Acque di rete";
			break;
		}
		case 22 : 
		{
			tipologiaOperatoreDescr = "Operatore Fuori Regione";
			break;
		}


		case 151 : 
		{
			tipologiaOperatoreDescr = "Farmacosorveglianza";
			break;
		}

		case 10 : 
		{
			tipologiaOperatoreDescr = "Canile";
			break;
		}

		case 20 : 
		{
			tipologiaOperatoreDescr = "Operatore Commerciale";
			break;
		}

		case 5 : 
		{
			tipologiaOperatoreDescr = "Punto Di Sbarco";
			break;
		}

		case 255 : 
		{
			tipologiaOperatoreDescr = "Cane Padronale";
			break;
		}
		case 201 : 
		{
			tipologiaOperatoreDescr = "Banchi Naturali";
			break;
		}
		case 999 : 
		{
			tipologiaOperatoreDescr = "Anagrafica Stabilimenti";
			break;
		}
		case 2000 : 
		{
			tipologiaOperatoreDescr = "Anagrafica SINTESIS";
			break;
		}
		default : 
		{
			tipologiaOperatoreDescr = "&nbsp;";
			break;
		}



		}
	}




	public String getTipologiaOperatoreDescr() {
		return tipologiaOperatoreDescr;
	}


	public void setTipologiaOperatoreDescr(String tipologiaOperatoreDescr) {
		this.tipologiaOperatoreDescr = tipologiaOperatoreDescr;
	}


	protected void buildRecord(ResultSet rs) throws SQLException {

		comuneControllo=rs.getString("comune_controllato");
		luogoControlloTarga=rs.getString("luogo_controllato_targa");
		flag_preavviso = rs.getString("flag_preavviso");
		data_preavviso_ba = rs.getTimestamp("data_preavviso_ba");
		flag_checklist = rs.getString("flag_checklist");
		try
		{
			//commento al 214
			//misureFormative = rs.getString("misure_formative");
			//misureRestrittive = rs.getString("misure_restrittive");
			//misureRiabilitative = rs.getString("misure_riabilitative");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			//commento al 247
			apiariSelezionati = rs.getString("apiari_selezionati");
			apiariSelezionatiMotivo = rs.getString("apiari_selezionati_motivo");
			apiariSelezionatiMotivoAltro = rs.getString("apiari_selezionati_motivo_altro");
			apiariSelezionatiAlveariControllati = rs.getInt("apiari_selezionati_alveari_controllati");
			apiariSelezionatiEsito = rs.getString("apiari_selezionati_esito");
			apiariSelezionatiEsitoNote = rs.getString("apiari_selezionati_esito_note");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		esito_import= rs.getString("esito_import");
		descrizione_errore = rs.getString("descrizione_errore");
		data_import = rs.getTimestamp("data_import");
		idBdn = rs.getInt("id_bdn");
		esito_import_b11= rs.getString("esito_import_b11");
		descrizione_errore_b11 = rs.getString("descrizione_errore_b11");
		data_import_b11 = rs.getTimestamp("data_import_b11");
		this.setIdStabilimento(rs.getInt("id_stabilimento"));
		//ticket table
		this.setId(rs.getInt("ticketid"));
		codice_azienda = rs.getString("codice_azienda");
		try{
			if(rs.findColumn("n_linea")>0)
				codice_azienda = rs.getString("n_linea");
		} catch (SQLException e){
			System.out.println("colonna n_linea non trovata. Vado avanti: " + e.getMessage());
		}
		
		id_allevamento = rs.getInt("id_allevamento");
		ragione_sociale_allevamento = rs.getString("ragione_sociale_allevamento");
		nome_conducente		= rs.getString("nome_conducente");
		cognome_conducente 	= rs.getString("cognome_conducente");
		data_nascita_conducente= rs.getString("data_nascita_conducente");
		luogo_nascita_conducente= rs.getString("luogo_nascita_conducente");
		citta_conducente= rs.getString("citta_conducente");
		via_connducente= rs.getString("via_conducente");
		cap_conducente= rs.getString("cap_conducente");
		prov_conducente= rs.getString("prov_conducente");
		data_preavviso = rs.getTimestamp("data_preavviso");
		protocollo_preavviso = rs.getString("protocollo_preavviso");
		protocollo_svincolo = rs.getString("protocollo_svincolo");
		tipologia_sottoprodotto = rs.getString("tipologia_sottoprodotto");
		data_comunicazione_svincolo = rs.getTimestamp("data_comunicazione_svincolo");
		peso = rs.getDouble("peso");
		intera_asl = rs.getBoolean("intera_asl");
		quantitativo = rs.getInt("quantitativo");
		quintali = rs.getDouble("quintali");
		supervisionato_da = rs.getInt("supervisionato_da");
		supervisionato_in_data = rs.getTimestamp("supervisionato_in_data");
		supervisione_flag_congruo = rs.getBoolean("supervisione_flag_congruo");

		idApiario=rs.getInt("id_apiario");
//		componentenucleoid_uno = rs.getInt("componentenucleoid_uno");
//		componentenucleoid_due = rs.getInt("componentenucleoid_due");
//		componentenucleoid_tre = rs.getInt("componentenucleoid_tre");
//		componentenucleoid_quattro = rs.getInt("componentenucleoid_quattro");
//		componentenucleoid_cinque = rs.getInt("componentenucleoid_cinque");
//		componentenucleoid_sei = rs.getInt("componentenucleoid_sei");
//		componentenucleoid_sette = rs.getInt("componentenucleoid_sette");
//		componentenucleoid_otto = rs.getInt("componentenucleoid_otto");
//		componentenucleoid_nove = rs.getInt("componentenucleoid_nove");
//		componentenucleoid_dieci = rs.getInt("componentenucleoid_dieci");

		supervisione_note = rs.getString("supervisione_note");
		documento_conducente 	= rs.getString("documento_conducente");
		tipologia_controllo_cani =  rs.getInt("tipologia_controllo_cani");
		id_imprese_linee_attivita = rs.getInt("id_imprese_linee_attivita");
		ispezioni_desc1 = rs.getString("ispezioni_desc1");
		ispezioni_desc2 = rs.getString("ispezioni_desc2");
		ispezioni_desc3 = rs.getString("ispezioni_desc3");
		ispezioni_desc4 = rs.getString("ispezioni_desc4");
		ispezioni_desc5 = rs.getString("ispezioni_desc5");
		ispezioni_desc6 = rs.getString("ispezioni_desc6");
		ispezioni_desc7 = rs.getString("ispezioni_desc7");
		ispezioni_desc8 = rs.getString("ispezioni_desc8");
		esitoDeclassamento = rs.getString("esito_declassamento");
		declassamento = rs.getInt("declassamento");
		azione = rs.getBoolean("azione_followup");
		azioneIsNull = rs.getObject("azione_followup")==null ? true : false;		
		azione_descrizione = rs.getString("azione_followup_descrizione");
		soggettiCoinvolti = rs.getString("soggetti_coinvolti");
		ricoverati		= rs.getString("ricoverati");
		alimentiSospetti = rs.getString("alimenti_sospetti");
		dataPasto = rs.getTimestamp("data_pasto");
		dataSintomi = rs.getTimestamp("data_sintomi");
		ragioneSociale = rs.getString("name");
		tipologia_operatore = rs.getInt("tipologia_operatore");
		altId = rs.getInt("alt_id");

		if (idStabilimento>0)
			tipologia_operatore = Ticket.TIPO_OPU;
		if (altId>0 && altId >= 60000000 && altId <= 79999999 )
			tipologia_operatore = Ticket.TIPO_OPU_RICHIESTE;
		if (altId>0 && altId >= 100000000 && altId <= 119999999 )
			tipologia_operatore = Ticket.TIPO_SINTESIS;
		
		tipologiaOperatore = tipologia_operatore;

		setDescrizioneOperatore();
		codiceAteco=rs.getString("codiceAteco");
		descrizioneCodiceAteco = rs.getString("descrizione_codice");
		chiusura_attesa_esito = rs.getBoolean("chiusura_attesa_esito");
		data_chiusura_attesa_esito = rs.getTimestamp("data_chiusura_attesa_esito");
		categoriaTrasportata=rs.getInt("specietrasportata");
		specieA=rs.getInt("animalitrasportati");
		idMacchinetta=rs.getInt("id_macchinetta");

		idConcessionario = rs.getInt("id_concessionario");
		nonConformitaFormali = rs.getString("nonconformitaformali");
		nonConformitaSignificative=rs.getString("nonconformitasignificative");
		nonConformitaGravi=rs.getString("nonconformitagravi");
		puntiFormali=rs.getString("puntiformali");
		puntiSignificativi=rs.getString("puntisignificativi");
		puntiGravi=rs.getString("puntigravi");
		contributi_allarme_rapido = rs.getDouble("contributi_allarme_rapido");
		contributi_verifica_risoluzione_nc = rs.getDouble("contributi_verifica_risoluzione_nc");
		contributi_macellazione = rs.getDouble("contributi_macellazione");
		contributi_macellazione_urgenza = rs.getDouble("contributi_macellazione_urgenza");

		contributi_rilascio_certificazione = rs.getDouble("contributi_rilascio_certificazione");
		tipoSospetto = rs.getString("tipo_sospetto");

		contributi_importazione_scambio = rs.getDouble("contributi_importazione_scambio");

		punteggio=rs.getInt("punteggio");
		categoriaRischio = rs.getInt("categoria_rischio");
		livelloRischio = rs.getInt("livello_rischio");
		contributi_seguito_campioni_tamponi=rs.getDouble("contributi_campioni_tamponi");
		noteAltrodiSistema=rs.getString("note_altro");
		ispezioneAltro = rs.getString("ispezione_altro");
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


		//Trasporti
		num_specie1=rs.getInt("num_specie1");
		num_specie2=rs.getInt("num_specie2");
		num_specie3=rs.getInt("num_specie3");		    
		num_specie4=rs.getInt("num_specie4");
		num_specie5=rs.getInt("num_specie5");
		num_specie6=rs.getInt("num_specie6");
		num_specie7=rs.getInt("num_specie7");
		num_specie8=rs.getInt("num_specie8");
		num_specie9=rs.getInt("num_specie9");
		num_specie10=rs.getInt("num_specie10");
		num_specie11=rs.getInt("num_specie11");
		num_specie12=rs.getInt("num_specie12");
		num_specie13=rs.getInt("num_specie13");
		num_specie14=rs.getInt("num_specie14");
		num_specie15=rs.getInt("num_specie15");
		num_specie22=rs.getInt("num_specie22");
		num_specie23=rs.getInt("num_specie23");
		num_specie24=rs.getInt("num_specie24");
		num_specie25=rs.getInt("num_specie25");
		num_specie26=rs.getInt("num_specie26");
		num_documento_accompagnamento=rs.getString("num_documento_accompagnamento");




		orgId = DatabaseUtils.getInt(rs, "org_id");
		idFarmacia = rs.getInt("id_farmacia");
		if (orgId == -1)
		{
			orgId = idFarmacia;
		}



		problem = rs.getString("problem");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		closed = rs.getTimestamp("closed");
		closed_nolista = rs.getBoolean("closed_nolista");

		codiceAllerta=rs.getString("codice_allerta");
		idLdd=rs.getInt("id_ldd");
		codiceBuffer = rs.getString("codice_buffer");

		solution = rs.getString("solution");

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

		dataPastoTimeZone = rs.getString("data_pasto_timezone");
		dataSintomiTimeZone = rs.getString("data_sintomi_timezone");

		resolutionDateTimeZone = rs.getString("resolution_date_timezone");
		trashedDate = rs.getTimestamp("trashed_date");

		causeId = DatabaseUtils.getInt(rs, "cause_id");

		stateId = DatabaseUtils.getInt(rs, "state_id");
		statusId = DatabaseUtils.getInt(rs, "status_id"); // Stato del controllo (1: Aperto 2: Chiuso 3: Riaperto 4: Disattivo)
		
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

		companyName = rs.getString("name");
		//companyEnabled = rs.getBoolean("orgenabled");
		//companyName = rs.getString("orgname");
		//companyEnabled = rs.getBoolean("orgenabled");

		orgSiteId = DatabaseUtils.getInt(rs, "orgsiteid");


		setPermission();
//		nucleoIspettivo = DatabaseUtils.getInt(rs, "nucleo_ispettivo");
//		nucleoIspettivoDue = DatabaseUtils.getInt(rs, "nucleo_ispettivo_due");
//		nucleoIspettivoTre = DatabaseUtils.getInt(rs, "nucleo_ispettivo_tre");
//		componenteNucleo = rs.getString("componente_nucleo");
//		componenteNucleoDue = rs.getString("componente_nucleo_due");
//		componenteNucleoTre = rs.getString("componente_nucleo_tre");
//		nucleoIspettivoQuattro = DatabaseUtils.getInt(rs, "nucleo_ispettivo_quattro");
//		nucleoIspettivoCinque = DatabaseUtils.getInt(rs, "nucleo_ispettivo_cinque");
//		nucleoIspettivoSei = DatabaseUtils.getInt(rs, "nucleo_ispettivo_sei");
//		nucleoIspettivoSette = DatabaseUtils.getInt(rs, "nucleo_ispettivo_sette");
//		nucleoIspettivoOtto = DatabaseUtils.getInt(rs, "nucleo_ispettivo_otto");
//		nucleoIspettivoNove = DatabaseUtils.getInt(rs, "nucleo_ispettivo_nove");
//		nucleoIspettivoDieci = DatabaseUtils.getInt(rs, "nucleo_ispettivo_dieci");
//		componenteNucleoQuattro = rs.getString("componente_nucleo_quattro");
//		componenteNucleoCinque = rs.getString("componente_nucleo_cinque");
//		componenteNucleoSei = rs.getString("componente_nucleo_sei");
//		componenteNucleoSette = rs.getString("componente_nucleo_sette");
//		componenteNucleoOtto = rs.getString("componente_nucleo_otto");
//		componenteNucleoNove = rs.getString("componente_nucleo_nove");
//		componenteNucleoDieci = rs.getString("componente_nucleo_dieci");
		inserisciContinua = rs.getBoolean("inserisci_continua");
		idControlloUfficiale = rs.getString("id_controllo_ufficiale");
		followUp = rs.getString("follow_up");

		latitude=rs.getDouble("latitudine");
		longitude=rs.getDouble("longitudine");


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
				dataddtTimeZone = sdf.format(dataddt); 
				numDDt = rs.getString("num_ddt");
				quantitaPartita = rs.getString("quantita_partita");
				unitaMisura = rs.getString("unita_misura_text");
			}
		}
		if(esitoControllo==4 || esitoControllo==5 || esitoControllo==6 || esitoControllo==8)
		{
			quantitaPartita = rs.getString("quantita_partita");
			unitaMisura = rs.getString("unita_misura_text");
		}
		if(esitoControllo==10 || esitoControllo == 11 || esitoControllo == 14)
		{
			quantitaBloccata = rs.getString("quantita_partita_bloccata");
			unitaMisura = rs.getString("unita_misura_text");

		}


		idFileAllegato = rs.getInt("id_file_allegato");

		if(idFileAllegato>0)
		{
			listaDistribuzioneAllegata = true ;
		}

		azioneArticolo = rs.getInt("articolo_azioni");

		headerAllegatoDocumentale =  rs.getString("header_allegato_documentale");
		
		idTarga = rs.getInt("id_targa");
		altId = rs.getInt("alt_id");
		checklistMacelli =  rs.getString("checklist_macelli");

		idIndirizzoLuogoControllo = rs.getInt("id_indirizzo_luogo_controllo");

	}


	protected void buildRecord2(ResultSet rs) throws SQLException {
		//ticket table
		this.setId(rs.getInt("ticketid"));
		ragioneSociale = rs.getString("name");
		tipologiaOperatore = rs.getInt("tipologia_operatore");
		setDescrizioneOperatore();
		punteggio=rs.getInt("punteggio");
		idMacchinetta = rs.getInt("id_macchinetta");
		orgId = DatabaseUtils.getInt(rs, "org_id");
		assetId = rs.getInt("link_asset_id");
		problem = rs.getString("problem");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		closed = rs.getTimestamp("closed");
		assignedDate = rs.getTimestamp("assigned_date");
		stateId = DatabaseUtils.getInt(rs, "state_id");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		tipologia = rs.getInt( "tipologia" );
		tipoCampione = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		dataFineControllo = rs.getTimestamp("data_fine_controllo");
		dataProssimoControllo = rs.getTimestamp("data_prossimo_controllo");
		numeoAttivitainCU = rs.getInt("numAttivita");
		altId = DatabaseUtils.getInt(rs, "alt_id");

	}

	protected void buildRecord3(ResultSet rs) throws SQLException {

		this.setData_chiusura_ufficio_prevista(rs.getTimestamp("data_chiusura_ufficio"));
		this.setId(rs.getInt("ticketid"));
		this.setIdControlloUfficiale(rs.getString("id_controllo_ufficiale"));
		ragioneSociale = rs.getString("name");
		tipologia_operatore = rs.getInt("tipologia_operatore");
		tipologiaOperatore =tipologia_operatore;

		setDescrizioneOperatore();
		punteggio=rs.getInt("punteggio");
		idMacchinetta = rs.getInt("id_macchinetta");
		orgId = DatabaseUtils.getInt(rs, "org_id");
		assetId = rs.getInt("link_asset_id");
		problem = rs.getString("problem");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		closed = rs.getTimestamp("closed");
		assignedDate = rs.getTimestamp("assigned_date");
		stateId = DatabaseUtils.getInt(rs, "state_id");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		tipologia = rs.getInt( "tipologia" );
		tipoCampione = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		dataFineControllo = rs.getTimestamp("data_fine_controllo");
		dataProssimoControllo = rs.getTimestamp("data_prossimo_controllo");
		numeoAttivitainCU = rs.getInt("numAttivita");
		try{
			rs.findColumn("alt_id");
		altId = DatabaseUtils.getInt(rs, "alt_id");
		} catch (SQLException e) {
			;
		}
	}



	public int update(Connection db, boolean override) throws SQLException  {
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
				"status_id = ?, trashed_date = ?, site_id = ? ,comune_controllato=?,luogo_controllato_targa=?, ");
		if (!override) {
			sql.append(
					"modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?,ip_modified='"+super.getIpModified()+"', ");
		}


		sql.append(" note_altro = ?, ");
		sql.append(" ispezione_altro = ?, ");
	//	sql.append(" isAggiornata_categoria = ?, ");
		if (intera_asl!=null)
		{
			sql.append(" intera_asl = ?, ");

		}
		if (esitoDeclassamento != null && !esitoDeclassamento.equals(""))
		{

			sql.append("esito_declassamento=?,declassamento=?,");
		}
		if (nome_conducente!= null &&  ! nome_conducente.equals(""))
		{
			sql.append(" nome_conducente = ?, ");

		}
		sql.append("data_nascita_conducente=?,luogo_nascita_conducente=?," +
				"citta_conducente=?,via_conducente=?,cap_conducente=?,prov_conducente=?,");



		if (cognome_conducente!= null &&  ! cognome_conducente.equals(""))
		{
			sql.append(" cognome_conducente = ?, ");

		}
		if (documento_conducente!= null &&  ! documento_conducente.equals(""))
		{
			sql.append(" documento_conducente = ?, ");

		}

		if (tipoIspezioneCodiceInterno.contains("20a")) //Svincoli sanitari
		{
			if (data_preavviso != null)
			{
				sql.append("data_preavviso= ?,");
			}
			if (protocollo_preavviso != null)
			{
				sql.append("protocollo_preavviso= ?,");
			}
			if (protocollo_svincolo != null)
			{
				sql.append("protocollo_svincolo= ?,");
			}
			if (tipologia_sottoprodotto != null)
			{
				sql.append("tipologia_sottoprodotto= ?,");
			}

			if (data_comunicazione_svincolo != null)
			{
				sql.append("data_comunicazione_svincolo= ?,");
			}
			sql.append("peso= ?,"); 
		}

		if (containsIgnoreCase("16a", tipoIspezioneCodiceInterno)) //tossinfezione
		{
			if (dataPasto != null)
			{
				sql.append("data_pasto=?,data_pasto_timezone=?,");
			}
			if (dataSintomi != null)
			{
				sql.append("data_sintomi=?,data_sintomi_timezone=?,");
			}
			if (alimentiSospetti != null && !"".equals(alimentiSospetti))
			{
				sql.append("alimenti_sospetti=?,");
			}
			if (ricoverati != null && !"".equals(ricoverati))
			{
				sql.append("ricoverati=?,");
			}
			if (soggettiCoinvolti != null && !"".equals(soggettiCoinvolti))
			{
				sql.append("soggetti_coinvolti=?,");
			}


		}
		if (codice_azienda!= null)
		{
			sql.append("codice_azienda=?,");
		}
		if (tipologia_controllo_cani>0)
		{
			sql.append("tipologia_controllo_cani=?,");
		}


		if (containsIgnoreCase("8a", tipoIspezioneCodiceInterno))	//	verifica followup non conformita
		{
			sql.append("azione_followup = ?,azione_followup_descrizione = ? ,");
		}

		sql.append("ispezioni_desc1=?,ispezioni_desc2=?,ispezioni_desc3=?,ispezioni_desc4=?,ispezioni_desc5=?,ispezioni_desc6=?,ispezioni_desc7=?,ispezioni_desc8=?,");

		//Trasporti
		sql.append("num_specie1=?,num_specie2=?,num_specie3=?,num_specie4=?,num_specie5=?,num_specie6=?,num_specie7=?,num_specie8=?,");
		sql.append("num_specie9=?,num_specie10=?,num_specie11=?,num_specie12=?,num_specie13=?,num_specie14=?,num_specie15=?,"
				+ "num_specie22=?,num_specie23=?,num_specie24=?,num_specie25=?,num_specie26=?, num_documento_accompagnamento = ?, ");

		if (descrizioneCodiceAteco!=null && !descrizioneCodiceAteco.equals(""))
		{
			sql.append(" descrizione_codice = ?, ");

		}

		if(!"".equals(codiceAteco)){
			sql.append(" codiceAteco = ?, ");
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

		if (headerAllegatoDocumentale!=null) {
			sql.append(" header_allegato_documentale=? ,");
		}
		
		if (idTarga >0)
			sql.append("id_targa = ? , ");

		sql.append(
				"solution = ?, custom_data = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
						"est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
						"cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
						"user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +
						"escalation_level = ?,  resolvedby = ?, resolvedby_department_code = ?, provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, data_accettazione = ?,data_fine_controllo = ? , data_prossimo_controllo = ? , data_accettazione_timezone = ?" +
//						", nucleo_ispettivo = ?, nucleo_ispettivo_due = ?, nucleo_ispettivo_tre = ?, componente_nucleo = ?,componentenucleoid_uno = ? , " +
//						"componentenucleoid_due = ? ,componentenucleoid_tre = ? ,componentenucleoid_quattro = ? ,componentenucleoid_cinque = ? ," +
//						"componentenucleoid_sei = ? ,componentenucleoid_sette = ? ,componentenucleoid_otto = ? ,componentenucleoid_nove = ? ," +
//						"componentenucleoid_dieci = ? ," +
//				" componente_nucleo_due = ?, componente_nucleo_tre = ?, nucleo_ispettivo_quattro = ?, nucleo_ispettivo_cinque = ?, nucleo_ispettivo_sei = ?, nucleo_ispettivo_sette = ?, nucleo_ispettivo_otto = ?, nucleo_ispettivo_nove = ?, nucleo_ispettivo_dieci = ?,componente_nucleo_quattro = ?, componente_nucleo_cinque = ?, componente_nucleo_sei = ?, componente_nucleo_sette = ?, componente_nucleo_otto = ?, componente_nucleo_nove = ?, componente_nucleo_dieci = ?"
				", punteggio = ?, follow_up = ? "+
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
		
		pst.setString(++i, comuneControllo);
		pst.setString(++i, luogoControlloTarga);
		if (!override) {
			pst.setInt(++i, this.getModifiedBy());
		}

		pst.setString(++i, noteAltrodiSistema);
		pst.setString(++i, ispezioneAltro);
	//	pst.setBoolean(++i, categoriaisAggiornata);
		if (intera_asl!=null)
		{
			pst.setBoolean(++i, intera_asl);

		}
		if (esitoDeclassamento != null && !esitoDeclassamento.equals(""))
		{

			pst.setString(++i, esitoDeclassamento);
			pst.setInt(++i, declassamento);
		}
		if (nome_conducente!= null &&  ! nome_conducente.equals(""))
		{
			pst.setString(++i, nome_conducente);

		}
		pst.setString(++i, data_nascita_conducente);
		pst.setString(++i, luogo_nascita_conducente);
		pst.setString(++i, citta_conducente);
		pst.setString(++i, via_connducente);
		pst.setString(++i, cap_conducente);
		pst.setString(++i, prov_conducente);


		if (cognome_conducente!= null &&  ! cognome_conducente.equals(""))
		{
			pst.setString(++i, cognome_conducente);

		}
		if (documento_conducente!= null &&  ! documento_conducente.equals(""))
		{
			pst.setString(++i, documento_conducente);

		}

		if (tipoIspezioneCodiceInterno.contains("20a")) //Svincoli sanitari
		{
			if (data_preavviso != null)
			{
				pst.setTimestamp(++i, data_preavviso);
			}
			if (protocollo_preavviso != null)
			{
				pst.setString(++i, protocollo_preavviso);
			}
			if (protocollo_svincolo != null)
			{
				pst.setString(++i, protocollo_svincolo);
			}
			if (tipologia_sottoprodotto != null)
			{
				pst.setString(++i, tipologia_sottoprodotto);
			}

			if (data_comunicazione_svincolo != null)
			{
				pst.setTimestamp(++i, data_comunicazione_svincolo);
			}
			pst.setDouble(++i, peso);
		}

		if (containsIgnoreCase("16a", tipoIspezioneCodiceInterno)) //tossinfezione
		{
			if (dataPasto != null)
			{
				pst.setTimestamp(++i, dataPasto);
				pst.setString(++i, dataPastoTimeZone);
			}
			if (dataSintomi != null)
			{
				pst.setTimestamp(++i, dataSintomi);
				pst.setString(++i, dataSintomiTimeZone);
			}
			if (alimentiSospetti != null && !"".equals(alimentiSospetti))
			{
				pst.setString(++i, alimentiSospetti);
			}
			if (ricoverati != null && !"".equals(ricoverati))
			{
				pst.setString(++i, ricoverati);
			}
			if (soggettiCoinvolti != null && !"".equals(soggettiCoinvolti))
			{
				pst.setString(++i, soggettiCoinvolti);
			}

		}
		if (codice_azienda!= null)
		{
			pst.setString(++i, codice_azienda);
		}
		if (tipologia_controllo_cani>0)
		{
			pst.setInt(++i, tipologia_controllo_cani);
		}

		if (containsIgnoreCase("8a", tipoIspezioneCodiceInterno))	//	verifica followup non conformita
		{
			pst.setBoolean(++i,azione);
			pst.setString(++i, azione_descrizione);
		}
		pst.setString(++i,ispezioni_desc1);
		pst.setString(++i,ispezioni_desc2);
		pst.setString(++i,ispezioni_desc3);
		pst.setString(++i,ispezioni_desc4);
		pst.setString(++i,ispezioni_desc5);
		pst.setString(++i,ispezioni_desc6);
		pst.setString(++i,ispezioni_desc7);
		pst.setString(++i,ispezioni_desc8);

		pst.setInt(++i,num_specie1);
		pst.setInt(++i,num_specie2);
		pst.setInt(++i,num_specie3);
		pst.setInt(++i,num_specie4);
		pst.setInt(++i,num_specie5);
		pst.setInt(++i,num_specie6);
		pst.setInt(++i,num_specie7);
		pst.setInt(++i,num_specie8);
		pst.setInt(++i,num_specie9);
		pst.setInt(++i,num_specie10);
		pst.setInt(++i,num_specie11);
		pst.setInt(++i,num_specie12);
		pst.setInt(++i,num_specie13);
		pst.setInt(++i,num_specie14);
		pst.setInt(++i,num_specie15);
		pst.setInt(++i,num_specie22);
		pst.setInt(++i,num_specie23);
		pst.setInt(++i,num_specie24);
		pst.setInt(++i,num_specie25);
		pst.setInt(++i,num_specie26);
		pst.setString(++i,num_documento_accompagnamento);


		if (descrizioneCodiceAteco!=null && !descrizioneCodiceAteco.equals(""))
		{

			pst.setString(++i, descrizioneCodiceAteco);
		}

		if(!"".equals(codiceAteco)){



			pst.setString(++i,codiceAteco);
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

		if (headerAllegatoDocumentale!=null) {
			pst.setString(++i, headerAllegatoDocumentale);
		}
		
		if (idTarga >0)
			pst.setInt(++i, idTarga);

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
		pst.setNull(++i, java.sql.Types.INTEGER);
		pst.setNull(++i, java.sql.Types.INTEGER);


		DatabaseUtils.setInt(pst, ++i, tipoCampione);
		DatabaseUtils.setInt(pst, ++i, esitoCampione);
		DatabaseUtils.setInt(pst, ++i, destinatarioCampione);
		pst.setTimestamp(++i, dataAccettazione );
		pst.setTimestamp(++i, dataFineControllo );
		pst.setTimestamp(++i, dataProssimoControllo );
		pst.setString(++i, dataAccettazioneTimeZone );
//		pst.setInt(++i, nucleoIspettivo);
//		pst.setInt(++i, nucleoIspettivoDue);
//		pst.setInt(++i, nucleoIspettivoTre);
//		pst.setString(++i, componenteNucleo);
//
//
//		pst.setInt(++i, componentenucleoid_uno);
//		pst.setInt(++i, componentenucleoid_due);
//		pst.setInt(++i, componentenucleoid_tre);
//		pst.setInt(++i, componentenucleoid_quattro);
//		pst.setInt(++i, componentenucleoid_cinque);
//		pst.setInt(++i, componentenucleoid_sei);
//		pst.setInt(++i, componentenucleoid_sette);
//		pst.setInt(++i, componentenucleoid_otto);
//		pst.setInt(++i, componentenucleoid_nove);
//		pst.setInt(++i, componentenucleoid_dieci);
//
//
//
//		pst.setString(++i, componenteNucleoDue);
//		pst.setString(++i, componenteNucleoTre);
//		pst.setInt(++i, nucleoIspettivoQuattro);
//		pst.setInt(++i, nucleoIspettivoCinque);
//		pst.setInt(++i, nucleoIspettivoSei);
//		pst.setInt(++i, nucleoIspettivoSette);
//		pst.setInt(++i, nucleoIspettivoOtto);
//		pst.setInt(++i, nucleoIspettivoNove);
//		pst.setInt(++i, nucleoIspettivoDieci);
//		pst.setString(++i, componenteNucleoQuattro);
//		pst.setString(++i, componenteNucleoCinque);
//		pst.setString(++i, componenteNucleoSei);
//		pst.setString(++i, componenteNucleoSette);
//		pst.setString(++i, componenteNucleoOtto);
//		pst.setString(++i, componenteNucleoNove);
//		pst.setString(++i, componenteNucleoDieci);
		pst.setInt(++i, punteggio);
		pst.setString(++i, followUp);
		pst.setInt(++i, id);
		/*if (!override && this.getModified() != null) {
		      pst.setTimestamp(++i, this.getModified());
		    }*/
		resultCount = pst.executeUpdate();
		pst.close();
		updateNucleoIspettivo(db);

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


	public int chiudiTemp(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql =
					"UPDATE ticket " +
							"SET chiusura_attesa_esito = true, modified = " + DatabaseUtils.getCurrentTimestamp(
									db) + ", modifiedby = ?,data_chiusura_attesa_esito =  " +DatabaseUtils.getCurrentTimestamp(
											db) + 
											
											//AGGIUNTI PER CHIUDERE CONTROLLO ANCHE IN ATTESA DI ESITO
											", status_id = 2, closed =  "+DatabaseUtils.getCurrentTimestamp(db) + 
											
											" WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);

			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
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


	public int apriTemp(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql =
					"UPDATE ticket " +
							"SET chiusura_attesa_esito = false, modified = " + DatabaseUtils.getCurrentTimestamp(
									db) + ", modifiedby = ?,data_chiusura_attesa_esito = null " +
									" WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);

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

	public int chiudi(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql =
					"UPDATE ticket " +
							"SET closed = ?, status_id = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
									db) + ", modifiedby = ? , closed_nolista = ? " +
									"WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setTimestamp( ++i, new Timestamp( System.currentTimeMillis() ) );
			pst.setInt(++i,2); //Chiuso = 2 e' lo stato del CU
			pst.setInt(++i, this.getModifiedBy());
			pst.setBoolean (++i, this.isClosed_nolista());
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

		return this.logicDelete(db, baseFilePath);
		//	if (this.getId() == -1) {
		//		throw new SQLException("Ticket ID not specified.");
		//	}
		//	boolean commit = db.getAutoCommit();
		//	try {
		//		if (commit) {
		//			db.setAutoCommit(false);
		//		}
		//		
		//		this.deleteAzioniAdottate(db);
		//		
		//		//Per i trasporti
		//		this.deleteSpecieTrasportata(db);
		//
		//		
		//		// delete any related action list items
		//		ActionItemLog.deleteLink(db, this.getId(), Constants.TICKET_OBJECT);
		//		
		//		// Delete any documents
		//		FileItemList fileList = new FileItemList();
		//		fileList.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
		//		fileList.setLinkItemId(this.getId());
		//		fileList.buildList(db);
		//		fileList.delete(db, getFileLibraryPath(baseFilePath, "tickets"));
		//		fileList = null;
		//
		//		// Delete any folder data
		//		CustomFieldRecordList folderList = new CustomFieldRecordList();
		//		folderList.setLinkModuleId(Constants.FOLDERS_TICKETS);
		//		folderList.setLinkItemId(this.getId());
		//		folderList.buildList(db);
		//		folderList.delete(db);
		//		folderList = null;
		//
		//		ActionPlanWorkList workList = new ActionPlanWorkList();
		//		workList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db,
		//				ActionPlan.TICKETS));
		//		workList.setIncludeAllSites(true);
		//		workList.setSiteId(this.getSiteId());
		//		workList.setLinkItemId(this.getId());
		//		workList.buildList(db);
		//		workList.delete(db);
		//
		//		// Delete the ticket tasks
		//		if (tasks == null || tasks.size() == 0) {
		//			this.buildTasks(db);
		//		}
		//		this.getTasks().delete(db);
		//
		//		// delete all history data
		//		PreparedStatement pst = db
		//				.prepareStatement("DELETE FROM ticketlog WHERE ticketid = ?");
		//		pst.setInt(1, this.getId());
		//		pst.execute();
		//		pst.close();
		//
		//		// Delete the related project link
		//		pst = db.prepareStatement("DELETE FROM ticketlink_project "
		//				+ "WHERE ticket_id = ? ");
		//		pst.setInt(1, this.getId());
		//		pst.execute();
		//		pst.close();
		//		// delete related task links
		//		pst = db
		//				.prepareStatement("DELETE FROM tasklink_ticket WHERE ticket_id = ?");
		//		pst.setInt(1, this.getId());
		//		pst.execute();
		//		pst.close();
		//
		//		// delete related activity items
		//		pst = db
		//				.prepareStatement("DELETE FROM trouble_asset_replacement WHERE link_form_id IN (SELECT form_id FROM ticket_sun_form WHERE link_ticket_id = ?)");
		//		pst.setInt(1, this.getId());
		//		pst.execute();
		//		pst.close();
		//
		//		// delete related activity items
		//		pst = db
		//				.prepareStatement("DELETE FROM ticket_sun_form WHERE link_ticket_id = ?");
		//		pst.setInt(1, this.getId());
		//		pst.execute();
		//		pst.close();
		//
		//		// delete related activity items
		//		pst = db
		//				.prepareStatement("DELETE FROM ticket_activity_item WHERE link_form_id IN (SELECT form_id FROM ticket_csstm_form WHERE link_ticket_id = ?) ");
		//		pst.setInt(1, this.getId());
		//		pst.execute();
		//		pst.close();
		//
		//		// delete related activity items
		//		pst = db
		//				.prepareStatement("DELETE FROM ticket_csstm_form WHERE link_ticket_id = ?");
		//		pst.setInt(1, this.getId());
		//		pst.execute();
		//		pst.close();
		//
		//		// delete related contact history
		//		ContactHistory.deleteObject(db, OrganizationHistory.TICKET, this
		//				.getId());
		//
		//		// Delete the ticket
		//		pst = db.prepareStatement("DELETE FROM ticket WHERE ticketid = ?");
		//		pst.setInt(1, this.getId());
		//		pst.execute();
		//		pst.close();
		//		if (commit) {
		//			db.commit();
		//		}
		//	} catch (SQLException e) {
		//		if (commit) {
		//			db.rollback();
		//		}
		//		throw new SQLException(e.getMessage());
		//	} finally {
		//		if (commit) {
		//			db.setAutoCommit(true);
		//		}
		//	}
		//	return true;
	}



	public boolean logicDelete(Connection db, String baseFilePath) throws SQLException {
		if (this.getId() == -1 || this.getIdStabilimento() == -1) 
			throw new SQLException("Ticket ID not specified.");
		boolean commit = db.getAutoCommit();
		try 
		{
			if (commit) 
				db.setAutoCommit(false);

			if (this.getId()>0){
				PreparedStatement pst = db.prepareStatement("UPDATE ticket SET trashed_date = now(), modified = now(), modifiedby = ? WHERE ticketid = ? OR id_controllo_ufficiale = ?;");			
				int i = 0;
				pst.setInt(++i, this.getModifiedBy());
				pst.setInt(++i, this.getId());
				pst.setString(++i, String.valueOf(this.getId()));
				pst.executeUpdate();
				pst.close();
			}
					
			if (this.getId()>0){
				PreparedStatement pst = db.prepareStatement("UPDATE linee_attivita_controlli_ufficiali SET trashed_date = now(), note = concat(note, ' ' || now() || ' - Cancellato in seguito a cancellazione del cu collegato. Utente con id ' || ? ||'. ') WHERE id_controllo_ufficiale in (select ticketid from ticket WHERE (ticketid = ? OR id_controllo_ufficiale = ? ));");
				int i = 0;
				pst.setInt(++i, this.getModifiedBy());
				pst.setInt(++i, this.getId());
				pst.setString(++i, String.valueOf(this.getId()));
				pst.executeUpdate();
				pst.close();
			}
		
			if (commit) 
				db.commit();
		} 
		catch (SQLException e) 
		{
			if (commit) 
				db.rollback();
			throw new SQLException(e.getMessage());
		} 
		finally 
		{
			if (commit) 
				db.setAutoCommit(true);
		}
		return true;
	}


	public boolean logicDeleteCheckListIstanza(Connection db)
			throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("Ticket ID not specified.");
		}
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}

			PreparedStatement pst = db.prepareStatement("UPDATE chk_bns_mod_ist SET trashed_date = now(), modified = now(), modifiedby = ? WHERE idcu = ?;");
			pst.setInt(1, this.getModifiedBy());
			pst.setInt(2, this.getId());
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
			String sql = "UPDATE ticket " + "SET closed = ?, status_id= ? , closed_nolista = ?, chiusura_attesa_esito = ? ,  modified = "
					+ DatabaseUtils.getCurrentTimestamp(db)
					+ ", modifiedby = ? " + "WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setNull(++i, java.sql.Types.TIMESTAMP);
			pst.setInt(++i, this.getStatusId()); //Riaperto = 3 e' lo status_id di ticket
			pst.setNull(++i, java.sql.Types.BOOLEAN);
			pst.setNull(++i, java.sql.Types.BOOLEAN);
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




	public boolean inserimentoLineeAttivita(Connection db,int idControlloUfficiale,String[] linee_attivita)
	{
		boolean esito = false ;
		try
		{
			//da includere nella dbi String delete = "delete from linee_attivita_controlli_ufficiali where id_controllo_ufficiale = "+idControlloUfficiale;
			String insert = " select * from public_functions.insert_linee_attivita_controlli_ufficiali(?, ?, ?,?,?,?)";

			PreparedStatement pstInsert =db.prepareStatement(insert);

			if(linee_attivita!=null)
			{
				for(int i = 0; i < linee_attivita.length; i++)
				{

					pstInsert.setInt(1, idControlloUfficiale);
					pstInsert.setInt(2, Integer.parseInt(linee_attivita[i]));
					if((this.getIdStabilimento()>0 && this.getAltId()<=0) || (this.getAltId()>0 && DatabaseUtils.getTipologiaPartizione(db, this.getAltId()) == Ticket.ALT_OPU))
					{					
						pstInsert.setString(3, "opu_relazione_stabilimento_linee_produttive");
					}
					else if (this.getAltId()>0 && DatabaseUtils.getTipologiaPartizione(db, this.getAltId()) == Ticket.ALT_SINTESIS)
					{
						pstInsert.setString(3, "sintesis_relazione_stabilimento_linee_produttive");

					}
					else if(this.getAltId()>0 &&  DatabaseUtils.getTipologiaPartizione(db, this.getAltId()) == Ticket.ALT_ANAGRAFICA_STABILIMENTI)
					{
						pstInsert.setString(3, "anagrafica.rel_stabilimenti_linee_attivita");
					}
					else if(this.getAltId()>0 &&  DatabaseUtils.getTipologiaPartizione(db, this.getAltId()) == Ticket.ALT_OPU_RICHIESTE)
					{
						pstInsert.setString(3, "suap_ric_scia_relazione_stabilimento_linee_produttive");
					}
					else if (this.getIdStabilimento()<=0 && this.getAltId()<=0)
					{
						pstInsert.setString(3, "");
					}
					
					System.out.println("Tipo operatore: "+this.getTipologia_operatore());
				
					pstInsert.setObject(4, null);
					pstInsert.setObject(5, null);
					pstInsert.setInt(6, this.getTipologia_operatore()); 
					System.out.println("Query inserimento linea attivita CU: "+pstInsert.toString());
					pstInsert.execute();

				}
			}

			esito = true;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}

		return esito;
	}
	
	public boolean aggiornamentoLineaNonConformita(Connection db,int idControlloUfficiale,String[] linee_attivita)
	{
		boolean esito = false ;
		try
		{
			//da includere nella dbi String delete = "delete from linee_attivita_controlli_ufficiali where id_controllo_ufficiale = "+idControlloUfficiale;
			String update = " select * from public.aggiorna_linea_attivita_non_conformita(?, ?)";

			PreparedStatement pstUpdate =db.prepareStatement(update);

			if(linee_attivita!=null)
			{	try {
				pstUpdate.setInt(1, idControlloUfficiale);
				pstUpdate.setInt(2, Integer.parseInt(linee_attivita[0]));
				System.out.println("Query aggiornamento linea attivita NC: "+pstUpdate.toString());
				pstUpdate.execute();
			} catch (Exception e) {}
			}
		

			esito = true;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}

		return esito;
	}



//	public boolean inserimentoLineeAttivitaStabSoa(Connection db,int idControlloUfficiale,String[] linee_attivita,String[] linee_attivita_desc)
//	{
//		boolean esito = false ;
//		try
//		{
//			String delete = "delete from linee_attivita_controlli_ufficiali_stab_soa where id_controllo_ufficiale = "+idControlloUfficiale;
//			String insert = "insert into linee_attivita_controlli_ufficiali_stab_soa (id_controllo_ufficiale , linea_attivita_stabilimenti_soa,linea_attivita_stabilimenti_soa_desc) values (?,?,?)";
//			PreparedStatement pst = db.prepareStatement(delete);
//
//			pst.execute();
//
//			PreparedStatement pstInsert =db.prepareStatement(insert);
//
//			if(linee_attivita!=null)
//			{
//				for(int i = 0; i < linee_attivita.length; i++)
//				{
//					if (linee_attivita[i]!= null && ! linee_attivita[i].equals(""))
//					{
//						pstInsert.setInt(1, idControlloUfficiale);
//						pstInsert.setString(2, linee_attivita[i]);
//						pstInsert.setString(3, linee_attivita_desc[i]);
//
//						pstInsert.execute();
//					}
//
//				}
//			}
//
//			esito = true;
//		}catch(SQLException e)
//		{
//			e.printStackTrace();
//		}
//
//		return esito;
//	}


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

	public Ticket(Connection db,ResultSet rs,boolean all) throws SQLException {

		buildRecord3(rs);


	}



	public boolean haveSottoAttivita ()
	{
		return (numeoAttivitainCU!=0);
	}


	private void calcoloTipoControllo(Connection db) throws SQLException{

		String tipoControlloCalcolato = "";

		LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");

		if(this.getTipoCampione()==3 || this.getTipoCampione()==7){
			if(this.getTipoCampione()==3)
				tipoControlloCalcolato =  "Audit - ";
			else if(this.getTipoCampione()==7)
				tipoControlloCalcolato =  "Audit interno - ";
			tipoControlloCalcolato += AuditTipo.getSelectedValue(this.getAuditTipo());
			if(this.getAuditTipo()==1){


				Iterator<Integer> itTipoAudit = this.getTipoAudit().keySet().iterator();
				while (itTipoAudit.hasNext())
				{
					int tipo_audit = itTipoAudit.next();
					String desc_tipo_audit = this.getTipoAudit().get(tipo_audit);
					tipoControlloCalcolato += desc_tipo_audit ;

					if(this.getTipoAudit().containsKey(2))
					{ // se e' bpi 
						String  bpi="- Controlli Effettuati su bpi : ";

						HashMap<Integer,String> listaBpi= this.getLisaElementibpi();
						Iterator<Integer> valoriBpiSel=this.getLisaElementibpi().keySet().iterator();

						while(valoriBpiSel.hasNext()){
							String bpiSel=listaBpi.get(valoriBpiSel.next());

							bpi=bpi+" "+bpiSel+" - ";
						}

						//tipoControlloCalcolato += "<br>"+bpi+"<br>";
						tipoControlloCalcolato +=" "+ bpi;

					}

					if(this.getTipoAudit().containsKey(3)){ // haccp
						HashMap<Integer,String> listaHaccp= this.getLisaElementihaccp();
						Iterator<Integer> valoriHaccpSel=this.getLisaElementihaccp().keySet().iterator();
						String  haccp="- Controlli Effettuati su haccp: ";
						while(valoriHaccpSel.hasNext()){
							String haccpSel=listaHaccp.get(valoriHaccpSel.next());
							haccp=haccp+" "+haccpSel+" - ";
						}

						//tipoControlloCalcolato += "<br>"+haccp+"<br>";
						tipoControlloCalcolato += " "+ haccp;


					}


				}
			}

		} else{
			if(this.getTipoCampione()==4){

				Iterator<Integer> itTipoIspezione = this.getTipoIspezione().keySet().iterator();
				tipoControlloCalcolato += "- Ispezione: <br> <b>Motivi dell'Ispezione</b> : <br> " ;


				LookupList listaTipi = new LookupList();
				listaTipi.setTable("lookup_tipo_ispezione");
				listaTipi.buildListWithEnabled(db);				while (itTipoIspezione.hasNext())
				{
					int tipo_ispezione = itTipoIspezione.next();

					String desc_tipo_ispezione= this.getTipoIspezione().get(tipo_ispezione);
					tipoControlloCalcolato += desc_tipo_ispezione + "<br>";

					if(listaTipi.getElementfromValue(tipo_ispezione).getCodiceInterno().toLowerCase().equals("2a")) // in piano di monitoraggio
					{
						String  piano=" <br> Piani di monitoraggio: ";
						String pianoScelto="";

						for (Piano piano1 : pianoMonitoraggio)
						{

							pianoScelto=piano1.getDescrizione();
						}


						tipoControlloCalcolato += piano+pianoScelto+"<br>";

					}

					if (listaTipi.getElementfromValue(tipo_ispezione).getCodiceInterno().toLowerCase().equals("16a"))
					{

						if (this.getSoggettiCoinvolti()!=null)
						{
							tipoControlloCalcolato += "Soggetti Coinvolti : "+this.getSoggettiCoinvolti()+"<br>";

						}
						if (this.getRicoverati()!=null)
						{
							tipoControlloCalcolato += "di cui Ricoverati : "+this.getRicoverati()+"<br>";

						}
						if (this.getAlimentiSospetti()!=null)
						{
							tipoControlloCalcolato += "Alimenti Sospetti : "+this.getAlimentiSospetti()+"<br>";

						}
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						if (this.getDataSintomi()!=null)
						{
							tipoControlloCalcolato += "Data Sintomi : "+sdf.format(new java.sql.Date(this.getDataSintomi().getTime()));

						}
						if (this.getDataPasto()!=null)
						{
							tipoControlloCalcolato += "Data Pasto : "+sdf.format(new java.sql.Date(this.getDataPasto().getTime()));

						}


					}

					if(listaTipi.getElementfromValue(tipo_ispezione).getCodiceInterno().toLowerCase().equals("4a")&& this.getIspezioneAltro() != null){
						tipoControlloCalcolato += ": " + this.getIspezioneAltro();
					}

					tipoControlloCalcolato += ".<br>";
					if(listaTipi.getElementfromValue(tipo_ispezione).getCodiceInterno().toLowerCase().equals("5a") || 
							listaTipi.getElementfromValue(tipo_ispezione).getCodiceInterno().toLowerCase().equals("8a") || 
							listaTipi.getElementfromValue(tipo_ispezione).getCodiceInterno().toLowerCase().equals("7a")){

						//tipoControlloCalcolato += "<br>- Contributi in Euro (nei casi in cui e' previsto dal D.Lgs 194/2008): "+this.getContributi();

					}


				}



			}
			else
			{
				tipoControlloCalcolato += "Ispezione in Sorveglianza";
			}

		}

		this.setTipoControllo(tipoControlloCalcolato);

	}

	public boolean updateTipoControllo(Connection db) throws SQLException{

		try
		{
			PreparedStatement pst=db.prepareStatement("update ticket set tipo_controllo = ? where ticketid=? ");
			pst.setString(1, tipoControllo.replaceAll("'", "''"));
			pst.setInt(2, id);
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return true;

	}


	public boolean insertCUOperatore(Connection db, int org_id_op, boolean conservato, int ticket_id_padre) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {

			modifiedBy = enteredBy;
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}

			
			sql.append(
					"INSERT INTO punti_sbarco_operatori_controllati (org_id, ticket_id, conservato, children_ticket_id, ");

			if (entered != null) {
				sql.append("entered, ");
			}
			if (modified != null) {
				sql.append("modified, ");
			}

			sql.append("entered_by, modified_by, trashed_date ");

			sql.append(")");
			sql.append(" VALUES (?, ?, ?, ?, ");

			if (entered != null) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}

			sql.append("?, ?, ?)");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, org_id_op);
			pst.setInt(++i, ticket_id_padre);
			pst.setBoolean(++i, conservato);
			pst.setInt(++i, id);			     
			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}
			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getModifiedBy());
			DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
			pst.execute();
			pst.close();

			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}
	
	public boolean deleteCUIdentificativo(Connection db) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}

			
			sql.append("UPDATE controlli_identificativi_animali set trashed_date = now() where id_controllo_ufficiale = ? and trashed_date is null;");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, id);			     
			pst.execute();
			pst.close();

			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}

	public boolean insertCUIdentificativo(Connection db, String identificativo) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {

			modifiedBy = enteredBy;
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}

			
			sql.append(
					"INSERT INTO controlli_identificativi_animali (identificativo, id_controllo_ufficiale, ");

			if (entered != null) {
				sql.append("entered, ");
			}
			if (modified != null) {
				sql.append("modified, ");
			}

			sql.append("entered_by, modified_by ");

			sql.append(")");
			sql.append(" VALUES (?, ?, ");

			if (entered != null) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}

			sql.append("?, ?)");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setString(++i, identificativo);
			pst.setInt(++i, id);			     
			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}
			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getModifiedBy());
			pst.execute();
			pst.close();

			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}


	public boolean updateIdOperatoreAssociato(Connection db, int org_id_op, int ticket_id_padre, int new_ticketid) throws SQLException {

		// TODO Auto-generated method stub

		PreparedStatement pst = db.prepareStatement(" update ticket set parent_ticket_id = ?  where ticketid = ? ");
		pst.setInt(1, ticket_id_padre);
		//pst.setInt(2, org_id_op);
		pst.setInt(2, new_ticketid);
		pst.executeUpdate();

		return true;

	}		

	public void deleteCUOperatore(Connection db, int ticketId) throws SQLException {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		sql.append("delete from punti_sbarco_operatori_controllati where children_ticket_id = ? ");
		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setInt(1, ticketId);
		pst.execute();
		pst.close();


	}	

	public boolean insertCULaboratori(Connection db, int org_id_op) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {

			modifiedBy = enteredBy;
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}

			sql.append(
					"INSERT INTO laboratori_haccp_controllati (org_id, ticket_id, ");

			if (entered != null) {
				sql.append("entered, ");
			}

			if (modified != null) {
				sql.append("modified, ");
			}

			sql.append("entered_by, modified_by, trashed_date ");

			sql.append(")");
			sql.append(" VALUES (?, ?, ");

			if (entered != null) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}

			sql.append("?, ?, ?)");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, org_id_op);
			pst.setInt(++i, id);

			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}

			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}

			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getModifiedBy());
			DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
			pst.execute();
			pst.close();

			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}

	public boolean insertCULaboratoriNoReg(Connection db, String desc_lab ) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {

			modifiedBy = enteredBy;
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}

			sql.append(
					"INSERT INTO laboratori_haccp_controllati (org_id, ticket_id, dati_lab, ");

			if (entered != null) {
				sql.append("entered, ");
			}

			if (modified != null) {
				sql.append("modified, ");
			}

			sql.append("entered_by, modified_by, trashed_date ");

			sql.append(")");
			sql.append(" VALUES (?, ?, ?,");

			if (entered != null) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}

			sql.append("?, ?, ?)");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, -1);
			pst.setInt(++i, id);
			pst.setString(++i, desc_lab);	


			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}

			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}

			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getModifiedBy());
			DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
			pst.execute();
			pst.close();

			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}

	public void deleteCULaboratori(Connection db, int ticketId) throws SQLException {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		sql.append("delete from laboratori_haccp_controllati where org_id != -1 and ticket_id = ? ");
		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setInt(1, ticketId);
		pst.execute();
		pst.close();


	}	

	public void deleteCULaboratoriNoReg(Connection db, int ticketId) throws SQLException {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		sql.append("delete from laboratori_haccp_controllati where org_id = -1 and ticket_id = ? ");
		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setInt(1, ticketId);
		pst.execute();
		pst.close();

	}	



	public void riapriChiusuraTemporanea(Connection db ) throws SQLException {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		sql.append("update ticket set chiusura_attesa_esito = false,closed=null,status_id = "+this.STATO_APERTO+" where ticketid = ? ");
		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setInt(1, this.id);
		pst.execute();
		pst.close();

	}


	public void deleteTicketIdOfChildren(Connection db, int ticket_id_padre) throws SQLException {

		//Recupera i ticket_id_children per cancellarli...
		String select = "select count(ticketid) as num_record from ticket where parent_ticket_id = ? ";
		String sql = "delete from ticketlog where ticketid  in (select ticketid from ticket where parent_ticket_id = ? ) ";
		String sql_ticket = "update ticket set trashed_date=current_date where ticketid   in (select ticketid from ticket where parent_ticket_id = ? and ticketid <> ? ) ";
		int count = 0;
		PreparedStatement pst = db.prepareStatement(select);
		PreparedStatement pst_log = null;
		PreparedStatement pst_ticket = null;
		pst.setInt(1, ticket_id_padre);

		ResultSet rs = pst.executeQuery();

		while (rs.next()){
			count = rs.getInt("num_record");
		}
		if(count > 0){

			/*pst_log = db.prepareStatement(sql);
			pst_log.setInt(1, ticket_id_padre);
			int value = pst_log.executeUpdate();*/


			pst_ticket= db.prepareStatement(sql_ticket);
			pst_ticket.setInt(1, ticket_id_padre);
			pst_ticket.setInt(2, ticket_id_padre);
			int value_del = pst_ticket.executeUpdate(); 
			pst_ticket.close();



		}
		pst.close();


	}

	//CONTROLLA SE IL PIANO INDICATO CON L'ID E' STATO SELEZIONATO
	public boolean isPianoSelected (int idPiano) {


		Iterator<Piano> it = this.getPianoMonitoraggio().iterator();
		while (it.hasNext()){
			Piano current = it.next();
			if (current.getId()==idPiano)
				return true;
			else return false;
		}
		return false;
	}

	//CONTROLLA SE IL PIANO INDICATO CON IL CODICE INTERNO E' STATO SELEZIONATO
		public boolean isPianoCodiceInternoSelected (Connection db, String codiceInterno) {


			Iterator<Piano> it = this.getPianoMonitoraggio().iterator();
			while (it.hasNext()){
				Piano current = it.next();
				RispostaDwrCodicePiano codiceInternopiano = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio",current.getId());
				if (codiceInternopiano.getCodiceInterno().equalsIgnoreCase(codiceInterno))
					return true;
				else return false;
			}
			return false;
		}
	
	
	
	public void buildListLineeAttivita(Connection db,int idControlloUfficiale,int tipologiaOperatore) throws SQLException
	{
		
		ArrayList<LineeAttivita>  lista_la = new ArrayList<LineeAttivita>();
		//if(tipologiaOperatore==1)
		if(this.getOrgId() > 0 && this.getIdStabilimento()<=0)
		{
			String idCu = ""+idControlloUfficiale;
			 lista_la = LineeAttivita.load_linea_attivita_per_cu(idCu, db,this.getOrgId());
			if (lista_la.size()>0)
			{
				for (LineeAttivita la : lista_la)
				{
					String idLineaAttivita = "" ;

					if (la.isMappato()){
						
						if (la.getAttivita()!=null)
							idLineaAttivita += la.getAttivita() ;
					}
					
					else {
						if (la.getCodice_istat()!=null)
							idLineaAttivita +=" " +la.getCodice_istat() ;
						
						if (la.getCategoria()!=null)
							idLineaAttivita +=" " +la.getCategoria() ;
						
						if (la.getLinea_attivita()!=null)
							idLineaAttivita +=" " +la.getLinea_attivita() ;
					}
					
					
					// val[i] = linea.getCategoria();
					listaLineeProduttive.add(idLineaAttivita);
				}
			}
		}
		else
		{
			if(tipologiaOperatore == 2000)
			{
		      String idCu = ""+idControlloUfficiale;
		      lista_la = LineeAttivita.sin_load_linea_attivita_per_cu(idCu, db);
		      if (lista_la.size()>0)
		      {
		    	  for (LineeAttivita la : lista_la)
		    	  {
		    		  String idLineaAttivita = "" ;
		    	  
		    		  if (!la.getLinea_attivita().isEmpty())
		    			  idLineaAttivita =  la.getLinea_attivita();
		    		  else
		    			  idLineaAttivita =  la.getLinea_attivita();
					// val[i] = linea.getCategoria();
		    		  listaLineeProduttive.add(idLineaAttivita);
		    	  }
		      }
			}
			
			else if(tipologiaOperatore == 3000)
			{
			      String idCu = ""+idControlloUfficiale;
			      lista_la = LineeAttivita.opu_load_linea_attivita_per_cu(idCu, db,this.getAltId());
			      if (lista_la.size()>0)
			      {
			    	  for (LineeAttivita la : lista_la)
			    	  {
			    		  String idLineaAttivita = "" ;
			    	  
			    		  if (!la.getLinea_attivita().isEmpty())
			    			  idLineaAttivita =  la.getLinea_attivita();
			    		  else
			    			  idLineaAttivita =  la.getLinea_attivita();
						// val[i] = linea.getCategoria();
			    		  listaLineeProduttive.add(idLineaAttivita);
			    	  }
			      }
				}
			
		else if(this.getIdStabilimento()>0)
			{
		      String idCu = ""+idControlloUfficiale;
		      lista_la = LineeAttivita.opu_load_linea_attivita_per_cu(idCu, db,this.getIdStabilimento());
		      if (lista_la.size()>0)
		      {
		    	  for (LineeAttivita la : lista_la)
		    	  {
		    		  String idLineaAttivita = "" ;
		    	  
		    		  if (!la.getLinea_attivita().isEmpty())
		    			  idLineaAttivita =  la.getLinea_attivita();
		    		  else
		    			  idLineaAttivita =  la.getLinea_attivita();
					// val[i] = linea.getCategoria();
		    		  listaLineeProduttive.add(idLineaAttivita);
		    	  }
		      }
			}
			else
			{
//				if(tipologiaOperatore==3 || tipologiaOperatore==97)
//				{
//					/*COSTRUZIONE LINEE ATTIVITA PER STABILIMENTI E SOA*/
//					ArrayList<LineaAttivitaSoa> linee_attivita_stabilimenti = new ArrayList<LineaAttivitaSoa>();
//					ArrayList<LineaAttivitaSoa> linee_attivita_stabilimenti_desc = new ArrayList<LineaAttivitaSoa>();
//					String sel = "select * from linee_attivita_controlli_ufficiali_stab_soa where id_controllo_ufficiale = "+idControlloUfficiale;
//					PreparedStatement pst = db.prepareStatement(sel);
//					ResultSet rs = pst.executeQuery();
//					while (rs.next())
//					{
//						LineaAttivitaSoa l = new LineaAttivitaSoa () ;
//						l.setCategoria(rs.getString(2));
//						l.setImpianto(rs.getString(3));
//						String linea = "" ;
//						if (l.getCategoria()!=null)
//						 linea += l.getCategoria() ;
//						
//						if (l.getImpianto()!=null)
//							 linea += " - "+l.getImpianto() ;
//						
//						listaLineeProduttive.add(linea);
//
//					}
//
//				}
				
				if(this.getAltId()>0 &&  DatabaseUtils.getTipologiaPartizione(db, this.getAltId()) == Ticket.ALT_OPU_RICHIESTE)
					{
				      String idCu = ""+idControlloUfficiale;
				      lista_la = LineeAttivita.ric_load_linea_attivita_per_cu(idCu, db);
				      if (lista_la.size()>0)
				      {
				    	  for (LineeAttivita la : lista_la)
				    	  {
				    		  String idLineaAttivita = "" ;
				    	  
				    		  if (!la.getLinea_attivita().isEmpty())
				    			  idLineaAttivita =  la.getLinea_attivita();
				    		  else
				    			  idLineaAttivita =  la.getLinea_attivita();
							// val[i] = linea.getCategoria();
				    		  listaLineeProduttive.add(idLineaAttivita);
				    	  }
				      }
					}
					else if(this.getAltId()>0 &&  DatabaseUtils.getTipologiaPartizione(db, this.getAltId()) == Ticket.ALT_SINTESIS)
					{
					      String idCu = ""+idControlloUfficiale;
					      lista_la = LineeAttivita.sin_load_linea_attivita_per_cu(idCu, db);
					      if (lista_la.size()>0)
					      {
					    	  for (LineeAttivita la : lista_la)
					    	  {
					    		  String idLineaAttivita = "" ;
					    	  
					    		  if (!la.getLinea_attivita().isEmpty())
					    			  idLineaAttivita =  la.getLinea_attivita();
					    		  else
					    			  idLineaAttivita =  la.getLinea_attivita();
								// val[i] = linea.getCategoria();
					    		  listaLineeProduttive.add(idLineaAttivita);
					    	  }
					      }
						}
					else if(this.getAltId()>0 &&  DatabaseUtils.getTipologiaPartizione(db, this.getAltId()) == Ticket.ALT_OPU) 
					{
					      String idCu = ""+idControlloUfficiale;
					      lista_la = LineeAttivita.opu_load_linea_attivita_per_cu_alt(idCu, db, this.getAltId());
					      if (lista_la.size()>0)
					      {
					    	  for (LineeAttivita la : lista_la)
					    	  {
					    		  String idLineaAttivita = "" ;
					    	  
					    		  if (!la.getLinea_attivita().isEmpty())
					    			  idLineaAttivita =  la.getLinea_attivita();
					    		  else
					    			  idLineaAttivita =  la.getLinea_attivita();
								// val[i] = linea.getCategoria();
					    		  listaLineeProduttive.add(idLineaAttivita);
					    	  }
					      }
						}
					else if(this.getAltId()>0 &&  DatabaseUtils.getTipologiaPartizione(db, this.getAltId()) == Ticket.ALT_ANAGRAFICA_STABILIMENTI) 
					{
					      String idCu = ""+idControlloUfficiale;
					      lista_la = LineeAttivita.anagrafica_load_linea_attivita_per_cu(idCu, db);
					      if (lista_la.size()>0)
					      {
					    	  for (LineeAttivita la : lista_la)
					    	  {
					    		  String idLineaAttivita = "" ;
					    	  
					    		  if (!la.getLinea_attivita().isEmpty())
					    			  idLineaAttivita =  la.getLinea_attivita();
					    		  else
					    			  idLineaAttivita =  la.getLinea_attivita();
								// val[i] = linea.getCategoria();
					    		  listaLineeProduttive.add(idLineaAttivita);
					    	  }
					      }
						}
				

			}
		}

	}
	
	
	
	public String getHeaderAllegatoDocumentale() {
		if (headerAllegatoDocumentale !=null && "".equalsIgnoreCase(headerAllegatoDocumentale))
			return "" ;
		return headerAllegatoDocumentale;
	}
	public void setHeaderAllegatoDocumentale(String headerAllegatoDocumentale) {
		this.headerAllegatoDocumentale = headerAllegatoDocumentale;
	}
	public boolean isClosed_nolista() {
		return closed_nolista;
	}
	public void setClosed_nolista(boolean closed_nolista) {
		this.closed_nolista = closed_nolista;
	}
	public void setClosed_nolista(String closed_nolista) {
		try {
			this.closed_nolista = Boolean.parseBoolean(closed_nolista);
		} catch (Exception e){

		}
	}


	public int ricompilaB11(Connection db, String idControllo, int modificato_da) {
		// TODO Auto-generated method stub

		String update = "update chk_bns_mod_ist set trashed_date = "+ DatabaseUtils.getCurrentTimestamp(
				db)+", modified = " + DatabaseUtils.getCurrentTimestamp(
						db)+ ", modifiedby = ?, note = ? where idcu = ? and trashed_date is null and id_alleg in (select code from lookup_chk_bns_mod where description ilike '%condizion%')";
		PreparedStatement pst;
		int aggiorna = 0;
		try {
			pst = db.prepareStatement(update);
			pst.setInt(1, modificato_da);
			pst.setString(2, "CANCELLAZIONE SCHEDA B11 ESISTENTE IN QUANTO OBSOLETO E NON AGGIORNATA CON QUELLA MINISTERIALE");
			pst.setInt(3,Integer.parseInt(idControllo));
			aggiorna = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return aggiorna;
	}

	public void gestisciInfoAddizionali(Connection db, ActionContext context) throws SQLException{

//		List<String> pianiEstesi = new ArrayList<String>();
//
//		pianiEstesi = CuHtmlFields.getPianiEstesi(db);
//
//		Iterator<Piano> it = this.getPianoMonitoraggio().iterator();
//		while (it.hasNext()){
//			Piano current = it.next();
//			RispostaDwrCodicePiano codice_interno = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio", current.getId());
//			if (inList(codice_interno.getCodiceInterno(), pianiEstesi))
//				ControlliUfficialiUtil.insertCUestesi(db, this, codice_interno.getCodiceInterno(), context);
//		}
		
		List<String> codiciEstesi = new ArrayList<String>();
		codiciEstesi = CuHtmlFields.getCodiciEstesi(db);

		Iterator<Piano> it = this.getPianoMonitoraggio().iterator();
		while (it.hasNext()){
			Piano current = it.next();
			RispostaDwrCodicePiano codice_interno = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio", current.getId());
			if (inList(codice_interno.getCodiceInterno(), codiciEstesi))
				ControlliUfficialiUtil.insertCUestesi(db, this, codice_interno.getCodiceInterno(), context);
		}
		
		Iterator<Integer> itTipoIspezione = this.getTipoIspezione().keySet().iterator();
		while (itTipoIspezione.hasNext()) {
			int tipoIspezione = itTipoIspezione.next();
			RispostaDwrCodicePiano codice_interno = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", tipoIspezione);
			if (inList(codice_interno.getCodiceInterno(), codiciEstesi))
				ControlliUfficialiUtil.insertCUestesi(db, this, codice_interno.getCodiceInterno(), context);
		}
		//MotivoIspezione motivoIsp = new MotivoIspezione(db,Integer.parseInt(this.getIdControlloUfficiale()));
		
	}

	private boolean inArray(int value, int[] array){
		for (int i =0; i<array.length; i++){
			if (array[i] == value)
				return true;
		}
		return false;
	}

	private boolean inList(String value, List lista){
		for (int i =0; i<lista.size(); i++){
			if (lista.get(i).equals(value))
				return true;
		}
		return false;
	}

	public boolean isAuditDiFollowUp() {
		return auditDiFollowUp;
	}
	public void setAuditDiFollowUp(boolean auditDiFollowUp) {
		this.auditDiFollowUp = auditDiFollowUp;
	}
	public void setAuditDiFollowUp(Object auditDiFollowUp) {
		if (auditDiFollowUp!=null)
			this.auditDiFollowUp = (Boolean) auditDiFollowUp;
	}
	public void setAuditDiFollowUp(String auditDiFollowUp) {
		if (auditDiFollowUp!=null && auditDiFollowUp.equals("on"))
			this.auditDiFollowUp = true;
		else
			this.auditDiFollowUp = false;
	}
	
	public boolean checkVincoloRegistroTrasgressori(Connection db){
		
		boolean vincolo = false;
		String query = "select * from check_vincolo_registro_trasgressori(?)";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(query);
		
		pst.setInt(1, this.getId());
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			vincolo = rs.getBoolean(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vincolo;
		}
	public boolean isVincoloRegistro() { //TRUE: OK
		return vincoloRegistro;
	}
	public void setVincoloRegistro(boolean vincoloRegistro) {
		this.vincoloRegistro = vincoloRegistro;
	}
	public String getMotivoProceduraRichiamo() {
		return motivoProceduraRichiamo;
	}
	public void setMotivoProceduraRichiamo(String motivoProceduraRichiamo) {
		this.motivoProceduraRichiamo = motivoProceduraRichiamo;
	}
	public String getAllertaNotes() {
		return allertaNotes;
	}
	public void setAllertaNotes(String allertaNotes) {
		this.allertaNotes = allertaNotes;
	}
	public int getIdTarga() {
		return idTarga;
	}
	public void setIdTarga(int idTarga) {
		this.idTarga = idTarga;
	}
	public void setIdTarga(String idTarga) {
		try {
			this.idTarga=Integer.parseInt(idTarga);
		}
		catch(Exception e){
		}
	}
private void setDatiMobile(Connection db) throws SQLException{
	if (this.getIdTarga()>0 && tipologiaOperatore== 999 ){
		DatiMobile dato = new DatiMobile(db, this.getIdTarga());
		this.datiMobile = dato;
	}
	
}
public DatiMobile getDatiMobile() {
	return datiMobile;
}
public void setDatiMobile(DatiMobile datiMobile) {
	this.datiMobile = datiMobile;
}

private void setSintesisAutomezzo(Connection db) throws SQLException{
	if (this.getIdTarga()>0 && tipologiaOperatore== 2000 ){
		SintesisAutomezzo sintesisAutomezzo = new SintesisAutomezzo(db, this.getIdTarga());
		this.sintesisAutomezzo = sintesisAutomezzo;
	}
}

public SintesisAutomezzo getSintesisAutomezzo() {
	return sintesisAutomezzo;
}
public void setSintesisAutomezzo(SintesisAutomezzo sintesisAutomezzo) {
	this.sintesisAutomezzo = sintesisAutomezzo;
}

private void setListaOperatoriMercato(Connection db) throws SQLException{
	if (tipologiaOperatore== 2000 ){
		String query = "select * from controlli_ufficiali_operatori_mercato where id_controllo_ufficiale = ? and trashed_date is null";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(query);
		
		pst.setInt(1, this.getId());
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			SintesisOperatoreMercato operatore  = new SintesisOperatoreMercato (db, rs.getInt("id_operatore_mercato"));
				this.listaOperatoriMercato.add(operatore);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndirizzoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

private void setIndirizzoLuogoControllo(Connection db) throws SQLException{
	if (tipologiaOperatore== 999 && this.idIndirizzoLuogoControllo>0){
		
		Indirizzo indirizzoLuogoControllo = new Indirizzo(db, this.idIndirizzoLuogoControllo);
		this.indirizzoLuogoControllo = indirizzoLuogoControllo;
	}
}

public ArrayList<SintesisOperatoreMercato> getListaOperatoriMercato() {
	return listaOperatoriMercato;
}
public void setListaOperatoriMercato(ArrayList<SintesisOperatoreMercato> listaOperatoriMercato) {
	this.listaOperatoriMercato = listaOperatoriMercato;
}
private void buildNucleoIspettivo(Connection db) throws SQLException{
	String sql = "select * from cu_nucleo where id_controllo_ufficiale = ? and enabled order by id asc";
	PreparedStatement pst = db.prepareStatement(sql);
	pst.setInt(1, this.id);
	ResultSet rs = pst.executeQuery();
	int i = 1;
	while (rs.next()){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo(db, rs.getInt("id"));
		
		switch(i){
		case 1 :
			nucleoIspettivo = comp.getIdQualifica();
			componenteNucleo = comp.getNomeComponente();
			componentenucleoid_uno =comp.getIdComponente();
			break;
		case 2 :
			nucleoIspettivoDue = comp.getIdQualifica();
			componenteNucleoDue = comp.getNomeComponente();
			componentenucleoid_due = comp.getIdComponente();
			break;
		case 3 :
			nucleoIspettivoTre =comp.getIdQualifica();
			componenteNucleoTre = comp.getNomeComponente();
			componentenucleoid_tre = comp.getIdComponente();
			break;
		case 4 :
			nucleoIspettivoQuattro = comp.getIdQualifica();
			componenteNucleoQuattro = comp.getNomeComponente();
			componentenucleoid_quattro = comp.getIdComponente();
			break;
		case 5 :
			nucleoIspettivoCinque = comp.getIdQualifica();
			componenteNucleoCinque = comp.getNomeComponente();
			componentenucleoid_cinque = comp.getIdComponente();
			break;
		case 6 :
			nucleoIspettivoSei = comp.getIdQualifica();
			componenteNucleoSei = comp.getNomeComponente();
			componentenucleoid_sei =comp.getIdComponente();
			break;
		case 7 :
			nucleoIspettivoSette = comp.getIdQualifica();
			componenteNucleoSette = comp.getNomeComponente();
			componentenucleoid_sette = comp.getIdComponente();
			break;
		case 8 :
			nucleoIspettivoOtto = comp.getIdQualifica();
			componenteNucleoOtto = comp.getNomeComponente();
			componentenucleoid_otto = comp.getIdComponente();
			break;
		case 9 :
			nucleoIspettivoNove =comp.getIdQualifica();
			componenteNucleoNove = comp.getNomeComponente();
			componentenucleoid_nove =comp.getIdComponente();
			break;
		case 10 :
			nucleoIspettivoDieci = comp.getIdQualifica();
			componenteNucleoDieci = comp.getNomeComponente();
			componentenucleoid_dieci =comp.getIdComponente();
			break;
		}
		
	i++;
	}
	
}

private void insertNucleoIspettivo(Connection db) throws SQLException{
	
	if (nucleoIspettivo>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_uno);
		comp.insert(db);
	}
	if (nucleoIspettivoDue>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_due);
		comp.insert(db);
	}
	if (nucleoIspettivoTre>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_tre);
		comp.insert(db);
		}
	if (nucleoIspettivoQuattro>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_quattro);
		comp.insert(db);
	}
	if (nucleoIspettivoCinque>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_cinque);
		comp.insert(db);
	}
	if (nucleoIspettivoSei>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_sei);
		comp.insert(db);
	}
	if (nucleoIspettivoSette>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_sette);
		comp.insert(db);
	}
	if (nucleoIspettivoOtto>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_otto);
		comp.insert(db);
	}
	if (nucleoIspettivoNove>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdQualifica(nucleoIspettivoNove);
		comp.insert(db);
	}
	if (nucleoIspettivoDieci>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_dieci);
		comp.insert(db);
	}
	
}
private void updateNucleoIspettivo(Connection db) throws SQLException{
	ArrayList<ComponenteNucleoIspettivo> nucleiNuovi = new ArrayList<ComponenteNucleoIspettivo>();
	if (nucleoIspettivo>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_uno);
		
		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
	}
	if (nucleoIspettivoDue>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_due);
	
		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
	}
	if (nucleoIspettivoTre>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_tre);
		
		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
		}
	if (nucleoIspettivoQuattro>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_quattro);

		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
	}
	if (nucleoIspettivoCinque>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_cinque);

		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
	}
	if (nucleoIspettivoSei>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_sei);

		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
	}
	if (nucleoIspettivoSette>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_sette);

		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
	}
	if (nucleoIspettivoOtto>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_otto);

		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
	}
	if (nucleoIspettivoNove>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_nove);

		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
	}
	if (nucleoIspettivoDieci>0){
		ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
		comp.setIdControlloUfficiale(id);
		comp.setIdComponente(componentenucleoid_dieci);

		comp.costruisci(db);
		if (comp.getId()<0)
			comp.insert(db);
		nucleiNuovi.add(comp);
		
	}
	
	ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
	comp.setIdControlloUfficiale(id);
	comp.rimuoviComponenti(db, nucleiNuovi);
	
	}

public String getChecklistMacelli() {
	return checklistMacelli;
}
public void setChecklistMacelli(String checklistMacelli) {
	this.checklistMacelli = checklistMacelli;
}

public void aggiornaChecklistMacelli(Connection db, int idUtente) {
PreparedStatement pst;
try {
	pst = db.prepareStatement("update ticket set checklist_macelli = ?, note_internal_use_only = concat_ws(';', note_internal_use_only, ?) where ticketid = ? ");
	pst.setString(1, checklistMacelli);
	pst.setString(2, "Checklist codice "+checklistMacelli+" caricata da utente "+idUtente+ " in data "+new Timestamp( System.currentTimeMillis() ));
	pst.setInt(3, id);
	pst.executeUpdate();

} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} }

public boolean isVincoloChecklistMacelli() {
	return vincoloChecklistMacelli;
}
public void setVincoloChecklistMacelli(boolean vincoloChecklistMacelli) {
	this.vincoloChecklistMacelli = vincoloChecklistMacelli;
}
public boolean isChecklistLibera() {
	return checklistLibera;
}
public void setChecklistLibera(boolean checklistLibera) {
	this.checklistLibera = checklistLibera;
}

public boolean isAMR() {
	return AMR;
}
public void setAMR(boolean AMR) {
	this.AMR = AMR;
}

public boolean isAcquacoltura() {
	return acquacoltura;
}
public void setAcquacoltura(boolean acquacoltura) {
	this.acquacoltura = acquacoltura;
}

public void inserisciDatiPunteggio(Connection db, int punteggio, int userId) throws SQLException {
	PreparedStatement pst = db.prepareStatement("update ticket set punteggio = ?, note_internal_use_only = concat_ws(';', note_internal_use_only, 'Punteggio Checklist modificato da utente', ?, 'Data', now(), 'Punteggio precedente', punteggio, 'Punteggio aggiornato', ?), modified = now(), modifiedby = ? where ticketid = ? ");
	
	pst.setInt(1, punteggio);
	pst.setInt(2, userId);
	pst.setInt(3, punteggio);
	pst.setInt(4, userId);
	pst.setInt(5, id);
	pst.executeUpdate();
}

public String isControlloCancellabilePagoPa(Connection db) throws SQLException{
	
	String messaggio = "";
	String sql="select * from is_cu_nc_sa_cancellabile_pagopa(?, -1, -1)";
	PreparedStatement pst=db.prepareStatement(sql);
	pst.setInt(1, id);
	ResultSet rst=pst.executeQuery();

	while(rst.next()){
		messaggio=rst.getString(1);
	}
	return messaggio;
}


//funzione utility
public boolean containsIgnoreCase(String str, ArrayList<String> list){
    for(String i : list){
        if(i != null && i.toLowerCase().contains(str.toLowerCase()))
            return true;
    }
    return false;
}


public String getEsito_import_b11() {
	return esito_import_b11;
}
public void setEsito_import_b11(String esito_import_b11) {
	this.esito_import_b11 = esito_import_b11;
}
public String getDescrizione_errore_b11() {
	return descrizione_errore_b11;
}
public void setDescrizione_errore_b11(String descrizione_errore_b11) {
	this.descrizione_errore_b11 = descrizione_errore_b11;
}
public Timestamp getData_import_b11() {
	return data_import_b11;
}
public void setData_import_b11(Timestamp data_import_b11) {
	this.data_import_b11 = data_import_b11;
}
public String getNum_documento_accompagnamento() {
	return num_documento_accompagnamento;
}
public void setNum_documento_accompagnamento(String num_documento_accompagnamento) {
	this.num_documento_accompagnamento = num_documento_accompagnamento;
}
public Indirizzo getIndirizzoLuogoControllo() {
	return indirizzoLuogoControllo;
}
public void setIndirizzoLuogoControllo(Indirizzo indirizzoLuogoControllo) {
	this.indirizzoLuogoControllo = indirizzoLuogoControllo;
}
public int getIdIndirizzoLuogoControllo() {
	return idIndirizzoLuogoControllo;
}
public void setIdIndirizzoLuogoControllo(int idIndirizzoLuogoControllo) {
	this.idIndirizzoLuogoControllo = idIndirizzoLuogoControllo;
}
public String getApiariSelezionati() {
	return apiariSelezionati;
}
public void setApiariSelezionati(String apiariSelezionati) {
	this.apiariSelezionati = apiariSelezionati;
}
public String getApiariSelezionatiMotivo() {
	return apiariSelezionatiMotivo;
}
public void setApiariSelezionatiMotivo(String apiariSelezionatiMotivo) {
	this.apiariSelezionatiMotivo = apiariSelezionatiMotivo;
}
public String getApiariSelezionatiMotivoAltro() {
	return apiariSelezionatiMotivoAltro;
}
public void setApiariSelezionatiMotivoAltro(String apiariSelezionatiMotivoAltro) {
	this.apiariSelezionatiMotivoAltro = apiariSelezionatiMotivoAltro;
}
public int getApiariSelezionatiAlveariControllati() {
	return apiariSelezionatiAlveariControllati;
}
public void setApiariSelezionatiAlveariControllati(int apiariSelezionatiAlveariControllati) {
	this.apiariSelezionatiAlveariControllati = apiariSelezionatiAlveariControllati;
}
public void setApiariSelezionatiAlveariControllati(String apiariSelezionatiAlveariControllati) {
	try {this.apiariSelezionatiAlveariControllati = Integer.parseInt(apiariSelezionatiAlveariControllati); } catch (Exception e) {}
}
public String getApiariSelezionatiEsito() {
	return apiariSelezionatiEsito;
}
public void setApiariSelezionatiEsito(String apiariSelezionatiEsito) {
	this.apiariSelezionatiEsito = apiariSelezionatiEsito;
}
public String getApiariSelezionatiEsitoNote() {
	return apiariSelezionatiEsitoNote;
}
public void setApiariSelezionatiEsitoNote(String apiariSelezionatiEsitoNote) {
	this.apiariSelezionatiEsitoNote = apiariSelezionatiEsitoNote;
}/*
public int getCategoriaQuantitativa() {
	return categoriaQuantitativa;
}
public void setCategoriaQuantitativa(int categoriaQuantitativa) {
	this.categoriaQuantitativa = categoriaQuantitativa;
}*/
public boolean isControlloChiudibileBiosicurezza(Connection db) throws SQLException {
	
	boolean checkScheda = true;
	boolean hasEvento = false;
	
	for(Piano p :getPianoMonitoraggio()) {
		if (PopolaCombo.hasEventoMotivoCU("isBioSicurezzaSuini", p.getId(), -1))
			hasEvento = true;
	}
	
	if(hasEvento == false){
		//do nothing
	} else {
		
		if(hasEvento){
	
			checkScheda = PopolaCombo.verificaEsistenzaIstanzaBiosicurezza(getId());
		}
	}

	return checkScheda;
	
}

}

