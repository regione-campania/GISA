/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.asterisk.manager.event.NewStateEvent;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.allevamenti.base.AllevamentoAjax;
import org.aspcfs.modules.allevamenti.base.OrganizationAddress;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.campioni.base.ModuliCampioniHtmlFields;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.lineeattivita.base.LineeCampiEstesi;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.suap.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Sede;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.LookupList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Stabilimento extends GenericBean {


	
	
	public static final int STATO_RICHIESTA_IN_VALIDAZIONE  =1;
	public static final int STATO_RICHIESTA_NON_VALIDABILE  =2;
	public static final int STATO_RICHIESTA_NON_VALIDABILE_TEMPORANEAMENTE  =3;
	
	
	public static final int TIPO_SCIA_REGISTRABILI  =4;
	public static final int TIPO_SCIA_RICONOSCIUTI  =6;
	public static final int TIPO_SCIA_APICOLTURA  =2;

	public static final int LOOKUP_ATTIVITA_APICOLTURA  =3;

	
	private List<Operatore> listaOperatori = null ;

	public static final String OPERAZIONE_INSERIMENTO_OK  ="1";
	public static final String OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE  ="2";
	public static final String OPERAZIONE_INSERIMENTO_KO_STABILIMENTO_ESISTENTE  ="4";
	public static final String OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE_ORGANIZATION  ="organization";
	
	
	public static final int STATO_IN_DOMANDA  =3;
	public static final int STATO_AUTORIZZATO = 0;
	public static final int STATO_CESSATO = 4;
	public static final int STATO_RESPINTO = 6;
	public static final int STATO_REGISTRAZIONE_ND = 7; 

	private String action = "" ;
	private String pageInclude = "" ;

	private Timestamp dataInizioSospensione;
	private String dataInizioAttivitaString ; 
	private String dataFineAttivitaString ; 
	private int superficie ; 
	private int idOperatore ;
	private Indirizzo sedeOperativa =new Indirizzo();
	private LineaProduttivaList listaLineeProduttive  = new  LineaProduttivaList() ;
	private Timestamp entered;
	private Timestamp modified;
	private int enteredBy ;
	private int modifiedBy ;
	private SoggettoFisico rappLegale = new SoggettoFisico();

	/*PARAMETRO USATO PER MANTENERE LA GENERALIZZAZIONE DELLA GESTIONE DEI CONTROLLI*/
	private int siteId ;
	private String name ;
	private Timestamp dataCessazione ;
	private Operatore operatore ;
	private int stato ;
	private boolean cessazioneStabilimento;
	private String erroreSuap;
	private String codiceErroreSuap;
	private String denominazione;

	private int tipologia ;

	private String note = "";
	private boolean sospensioneStabilimento ;
	private int tipoCarattere;
	private int tipoAttivita ; 
	
	private Timestamp dataInizioAttivita ;
	private Timestamp dataFineAttivita ;
	private BeanPerXmlRichiesta beanPerXml;
	private String partitaIvaVariazione ;
	
	private int tipoInserimentoScia ;
	
	private String numeroRegistrazioneVariazione ;
	
	
	private ArrayList <DatiMobile> datiMobile = new ArrayList <DatiMobile>();
	private Timestamp dataRichiestaSciaAsl ;
	
	private String telefono ;
	
	
	private String ragioneSociale;
	private String partitaIva;
	
	private int categoriaRischio ;

	public String getPartitaIva() {
		return partitaIva;
	}

	private boolean validabilitaApicoltura ;
	private String codiceAziendaApicoltura;
	
	private boolean flagProsegubilita = false ;
	
	

	public boolean isFlagProsegubilita() {
		return flagProsegubilita;
	}





	public void setFlagProsegubilita(boolean flagProsegubilita) {
		this.flagProsegubilita = flagProsegubilita;
	}





	public boolean isValidabilitaApicoltura() {
		return validabilitaApicoltura;
	}





	public void setValidabilitaApicoltura(boolean validabilitaApicoltura) {
		this.validabilitaApicoltura = validabilitaApicoltura;
	}





	public String getCodiceAziendaApicoltura() {
		return codiceAziendaApicoltura;
	}





	public void setCodiceAziendaApicoltura(String codiceAziendaApicoltura) {
		this.codiceAziendaApicoltura = codiceAziendaApicoltura;
	}





	public Timestamp getDataInizioSospensione() {
		return dataInizioSospensione;
	}





	public void setDataInizioSospensione(Timestamp dataInizioSospensione) {
		this.dataInizioSospensione = dataInizioSospensione;
	}



	public void setDataInizioSospensione(String dataInizioSospensione) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			this.dataInizioSospensione = new Timestamp(sdf.parse(dataInizioSospensione).getTime());
		}
		catch(Exception e)
		{
			
		}
	}


	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}





	public String getCfRappresentante() {
		return cfRappresentante;
	}


	

	public boolean isSospensioneStabilimento() {
		return sospensioneStabilimento;
	}





	public void setSospensioneStabilimento(boolean sospensioneStabilimento) {
		this.sospensioneStabilimento = sospensioneStabilimento;
	}





	public boolean getCessazioneStabilimento()
	{
		return cessazioneStabilimento;
	}
	
	public void setCfRappresentante(String cfRappresentante) {
		this.cfRappresentante = cfRappresentante;
	}





	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}





	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}





	public String getComuneSedeLegale() {
		return comuneSedeLegale;
	}





	public void setComuneSedeLegale(String comuneSedeLegale) {
		this.comuneSedeLegale = comuneSedeLegale;
	}





	public String getIndirizzoSedeLegale() {
		return indirizzoSedeLegale;
	}





	public void setIndirizzoSedeLegale(String indirizzoSedeLegale) {
		this.indirizzoSedeLegale = indirizzoSedeLegale;
	}





	public String getComuneSedeOperativa() {
		return comuneSedeOperativa;
	}





	public void setComuneSedeOperativa(String comuneSedeOperativa) {
		this.comuneSedeOperativa = comuneSedeOperativa;
	}





	public String getIndirizzoSedeOperativa() {
		return indirizzoSedeOperativa;
	}





	public void setIndirizzoSedeOperativa(String indirizzoSedeOperativa) {
		this.indirizzoSedeOperativa = indirizzoSedeOperativa;
	}











	private String cfRappresentante;
	private String numeroRegistrazione;
	private String comuneSedeLegale;
	private String indirizzoSedeLegale;
	private String comuneSedeOperativa;
	private String indirizzoSedeOperativa;


	
	
	
	
	
	
	public String getTelefono() {
		return telefono;
	}





	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}





	public String getPartitaIvaVariazione() {
		return partitaIvaVariazione;
	}





	public void setPartitaIvaVariazione(String partitaIvaVariazione) {
		this.partitaIvaVariazione = partitaIvaVariazione;
	}





	public Timestamp getDataRichiestaSciaAsl() {
		return dataRichiestaSciaAsl;
	}





	public void setDataRichiestaSciaAsl(Timestamp dataRichiestaSciaAsl) {
		this.dataRichiestaSciaAsl = dataRichiestaSciaAsl;
	}





	public int getTipoInserimentoScia() {
		return tipoInserimentoScia;
	}
	
	

	

	public String getNumeroRegistrazioneVariazione() {
		return numeroRegistrazioneVariazione;
	}





	public void setNumeroRegistrazioneVariazione(String numeroRegistrazioneVariazione) {
		this.numeroRegistrazioneVariazione = numeroRegistrazioneVariazione;
	}





	public void setTipoInserimentoScia(int tipoInserimentoScia) {
		this.tipoInserimentoScia = tipoInserimentoScia;
	}



	public Timestamp getDataInizioAttivita() {
		return dataInizioAttivita;
	}



	public void setDataInizioAttivita(Timestamp dataInizioAttivita) {
		this.dataInizioAttivita = dataInizioAttivita;
		
		if (this.dataInizioAttivita!= null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataInizioAttivitaString = sdf.format(new Date(dataInizioAttivita.getTime()));
			
		}
	}

	public String getDataInizioAttivitaString() {
		return dataInizioAttivitaString;
	}



	public void setDataInizioAttivitaString(String dataInizioAttivitaString) {
		this.dataInizioAttivitaString = dataInizioAttivitaString;
	}



	public String getDataFineAttivitaString() {
		return dataFineAttivitaString;
	}



	public void setDataFineAttivitaString(String dataFineAttivitaString) {
		this.dataFineAttivitaString = dataFineAttivitaString;
	}



	public void setDataInizioAttivita(String dataInizioAttivita) throws ParseException {
		if (dataInizioAttivita!= null && !"".equals(dataInizioAttivita))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataInizioAttivita = new Timestamp(sdf.parse(dataInizioAttivita).getTime());
			
		}
		
	}
	
	
	public void setDataRichiestaSciaAsl(String dataRichiestaSciaAsl) throws ParseException {
		if (dataRichiestaSciaAsl!= null && !"".equals(dataRichiestaSciaAsl))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataRichiestaSciaAsl = new Timestamp(sdf.parse(dataRichiestaSciaAsl).getTime());
			
		}
	}


	public Timestamp getDataFineAttivita() {
		return dataFineAttivita;
	}



	public void setDataFineAttivita(Timestamp dataFineAttivita) {
		this.dataFineAttivita = dataFineAttivita;
		
		if (this.dataFineAttivita!= null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataFineAttivitaString = sdf.format(new Date(dataFineAttivita.getTime()));
			
		}
	}

	public void setDataFineAttivita(String dataFineAttivita) throws ParseException {
		if (dataFineAttivita!= null && !"".equals(dataFineAttivita))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataFineAttivita = new Timestamp(sdf.parse(dataFineAttivita).getTime());
			
		}
		
	}

	public int getTipoCarattere() {
		return tipoCarattere;
	}



	public void setTipoCarattere(int tipoCarattere) {
		this.tipoCarattere = tipoCarattere;
	}

	public void setTipoCarattere(String tipoCarattere) {
		if (tipoCarattere!= null && !"".equals(tipoCarattere))
		{
		this.tipoCarattere = Integer.parseInt(tipoCarattere);
		}
	}


	public int getTipoAttivita() {
		return tipoAttivita;
	}



	public void setTipoAttivita(int tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}

	public void setTipoAttivita(String tipoAttivita) {
		if (tipoAttivita!= null && !"".equals(tipoAttivita))
		{
		this.tipoAttivita = Integer.parseInt(tipoAttivita);
		}
	}

	

	public String getCodiceErroreSuap() {
		return codiceErroreSuap;
	}



	public void setCodiceErroreSuap(String codiceErroreSuap) {
		this.codiceErroreSuap = codiceErroreSuap;
	}



	public String getErroreSuap() {
		return erroreSuap;
	}



	public void setErroreSuap(String erroreSuap) {
		this.erroreSuap = erroreSuap;
	}



	public String getDenominazione() {
		return denominazione;
	}



	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}






	public int getTipologia() {
		return tipologia;
	}



	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}





	public int getStato() {
		return stato;
	}



	public void setStato(int stato) {
		this.stato = stato;
	}



	public Stabilimento(){

		rappLegale = new SoggettoFisico();
		sedeOperativa = new Indirizzo() ;


	}



	public Stabilimento(Connection db, int idStabilimento) throws SQLException, IndirizzoNotFoundException{

		queryRecordStabilimento(db,idStabilimento);
		operatore = new Operatore();
		operatore.queryRecordOperatore(db, idOperatore);
		this.setName(operatore.getRagioneSociale());
	//	datiMobile = recuperaDatiMobile(db, this.getIdStabilimento());

	}
	
	public Stabilimento(Connection db, int altId, boolean nuova) throws SQLException, IndirizzoNotFoundException{

		queryRecordStabilimentoAlt(db,altId);
		operatore = new Operatore();
		operatore.queryRecordOperatore(db, idOperatore);
		this.setName(operatore.getRagioneSociale());
	//	datiMobile = recuperaDatiMobile(db, this.getIdStabilimento());

	}

	public LineaProduttiva getLineaProduttivaPrimaria()
	{
		Iterator<LineaProduttiva> itProd = listaLineeProduttive.iterator();
		while (itProd.hasNext())
		{
			LineaProduttiva thisLine = itProd.next();
			if (thisLine.isPrincipale())
				return thisLine;
		}
		return null ;
	}


	public Operatore getOperatore() {
		return operatore;
	}



	public void setOperatore(Operatore operatore) {
		this.operatore = operatore;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	


	public int getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}

	public void setIdOperatore(String idOperatore) {
		if (idOperatore != null && ! "".equals(idOperatore))
			this.idOperatore = Integer.parseInt(idOperatore);
	}

	
	public Stabilimento(ResultSet rs) throws SQLException{
		this.buildRecordStabilimento(rs);
	}

	public void setSedeOperativa(Sede sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}

	public SoggettoFisico getRappLegale() {
		return rappLegale;
	}

	public void setRappLegale(SoggettoFisico rappLegale) {
		this.rappLegale = rappLegale;
	}



	public void setIdAsl(String idAsl) {
		if (idAsl!= null && !idAsl.equals(""))
			this.idAsl = Integer.parseInt(idAsl);
	}
	public int getIdAsl() {

		return	this.idAsl;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
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

	public LineaProduttivaList getListaLineeProduttive() {
		return listaLineeProduttive;
	}

	public void setListaLineeProduttive(LineaProduttivaList listaLineeProduttive) {
		this.listaLineeProduttive = listaLineeProduttive;
	}


	public Timestamp getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Timestamp dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public Indirizzo getSedeOperativa() {
		return sedeOperativa;
	}

	public void setSedeOperativa(Indirizzo sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}
	
	
	public void setCessazioneStabilimento(String cessazioneStabilimento) {
		this.cessazioneStabilimento = (cessazioneStabilimento!=null && cessazioneStabilimento.equalsIgnoreCase("on"));
	}
	
	
	public void setSospensioneStabilimento(String sospensioneStabilimento) {
		this.sospensioneStabilimento = (sospensioneStabilimento!=null && sospensioneStabilimento.equalsIgnoreCase("on"));
	}
	
	
	
	public boolean isCessazioneStabilimento() {
		return cessazioneStabilimento;
	}

	public void setCessazioneStabilimento(boolean cessazioneStabilimento) {
		this.cessazioneStabilimento = cessazioneStabilimento;
	}





	





	public void queryRecordStabilimento(Connection db , int idStabilimento) throws SQLException, IndirizzoNotFoundException
	{
		if (idStabilimento == -1){
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement("Select o.*,impresa.ragione_sociale, "
				+ "CASE "+
	            "WHEN apicoltura.id <= 0 OR apicoltura.id IS NULL THEN true "+
	            "WHEN apicoltura.id > 0 AND apicoltura.sincronizzato_bdn = true THEN true "+
	            "ELSE false "+
	        "END AS validabile_apicoltura, "+
	    "apicoltura.codice_azienda AS codice_azienda_apicoltura "
				+ "from suap_ric_scia_stabilimento o join suap_ric_scia_operatore impresa on impresa.id=o.id_operatore LEFT JOIN apicoltura_imprese apicoltura ON impresa.id = apicoltura.id_richiesta_suap where o.id = ?");
		pst.setInt(1, idStabilimento);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecordStabilimento(rs);
			sedeOperativa = new Indirizzo(db,sedeOperativa.getIdIndirizzo()) ;
			listaLineeProduttive.setIdStabilimento(idStabilimento);
			listaLineeProduttive.buildList(db);
		}

		if (idStabilimento == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		rs.close();
		pst.close();



	}
	
	public void queryRecordStabilimentoAlt(Connection db , int altId) throws SQLException, IndirizzoNotFoundException
	{
		if (altId == -1){
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement("Select o.*,impresa.ragione_sociale, "
				+ "CASE "+
	            "WHEN apicoltura.id <= 0 OR apicoltura.id IS NULL THEN true "+
	            "WHEN apicoltura.id > 0 AND apicoltura.sincronizzato_bdn = true THEN true "+
	            "ELSE false "+
	        "END AS validabile_apicoltura, "+
	    "apicoltura.codice_azienda AS codice_azienda_apicoltura "
				+ "from suap_ric_scia_stabilimento o join suap_ric_scia_operatore impresa on impresa.id=o.id_operatore LEFT JOIN apicoltura_imprese apicoltura ON impresa.id = apicoltura.id_richiesta_suap where o.alt_id = ?");
		pst.setInt(1, altId);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecordStabilimento(rs);
			sedeOperativa = new Indirizzo(db,sedeOperativa.getIdIndirizzo()) ;
			listaLineeProduttive.setIdStabilimento(idStabilimento);
			listaLineeProduttive.buildList(db);
		}

		if (altId == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		rs.close();
		pst.close();



	}
	
	
	public void queryRecordStabilimento(Connection db , String numeroRegistrazione) throws SQLException, IndirizzoNotFoundException
	{
		if (idStabilimento == -1){
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement("Select o.*,impresa.ragione_sociale, "
				+ "CASE "+
	            "WHEN apicoltura.id <= 0 OR apicoltura.id IS NULL THEN true "+
	            "WHEN apicoltura.id > 0 AND apicoltura.sincronizzato_bdn = true THEN true "+
	            "ELSE false "+
	        "END AS validabile_apicoltura, "+
	    "apicoltura.codice_azienda AS codice_azienda_apicoltura "
				+ "from suap_ric_scia_stabilimento o join suap_ric_scia_operatore impresa on impresa.id=o.id_operatore LEFT JOIN apicoltura_imprese apicoltura ON impresa.id = apicoltura.id_richiesta_suap where o.numero_registrazione ilike ?");
		pst.setString(1, numeroRegistrazione);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecordStabilimento(rs);
			sedeOperativa = new Indirizzo(db,sedeOperativa.getIdIndirizzo()) ;
			listaLineeProduttive.setIdStabilimento(idStabilimento);
			listaLineeProduttive.buildList(db);
		}

		if (idStabilimento == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		rs.close();
		pst.close();

	}
	
	
	public void queryRecordStabilimentoIdOperatore(Connection db , int idOperatore) throws SQLException, IndirizzoNotFoundException
	{
		if (idOperatore == -1){
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement("Select o.*,impresa.ragione_sociale, "
				+ "CASE "+
            "WHEN apicoltura.id <= 0 OR apicoltura.id IS NULL THEN true "+
            "WHEN apicoltura.id > 0 AND apicoltura.sincronizzato_bdn = true THEN true "+
            "ELSE false "+
        "END AS validabile_apicoltura, "+
    "apicoltura.codice_azienda AS codice_azienda_apicoltura "
				+ " from suap_ric_scia_stabilimento o join suap_ric_scia_operatore impresa on impresa.id=o.id_operatore  LEFT JOIN apicoltura_imprese apicoltura ON impresa.id = apicoltura.id_richiesta_suap where o.id_operatore = ?");
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecordStabilimento(rs);
			sedeOperativa = new Indirizzo(db,sedeOperativa.getIdIndirizzo()) ;
			listaLineeProduttive.setIdStabilimento(idStabilimento);
			listaLineeProduttive.buildList(db);
		}
		operatore = new Operatore();
		operatore.queryRecordOperatore(db, idOperatore);
		
		
		if (idStabilimento == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		rs.close();
		pst.close();
		
		


	}
	
	public void aggiungiInfoLineeProduttive(Connection db) throws SQLException
	{
		listaLineeProduttive.setIdStabilimento(idStabilimento);
		listaLineeProduttive.buildList(db);
	}




	public boolean insert (Connection db, boolean insertLinee,ActionContext context) throws SQLException
	{
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			modifiedBy = enteredBy;

			
			if (this.getIdStabilimento()<=0)
			{
				this.idStabilimento =DatabaseUtils.getNextSeq(db, "suap_ric_scia_stabilimento_id_seq");
			}
			sql.append("INSERT INTO suap_ric_scia_stabilimento (tipo_reg_ric,stato,tipo_carattere,tipo_attivita,cessazione_stabilimento,sospensione_stabilimento,");

			
			if (superficie>0)
			{
				sql.append("superficie, ");
			}
			
			if (telefono!=null)
			{
				sql.append("telefono, ");
			}
			
			if (partitaIvaVariazione!=null)
			{
				sql.append("partita_iva_variazione, ");
			}
			
			
			if (dataRichiestaSciaAsl!=null)
			{
				sql.append("data_ricezione_richiesta, ");
			}
			
			if (numeroRegistrazioneVariazione!=null)
			{
				sql.append("numero_registrazione_variazione, ");
			}
			
			if (dataInizioAttivita!=null)
			{
				sql.append("data_inizio_attivita, ");
			}
			if (dataFineAttivita!=null)
			{
				sql.append("data_fine_attivita, ");
			}
			
			if (dataInizioSospensione!=null)
			{
				sql.append("data_inizio_sospensione, ");
			}
			if (idAsl > 0) {
				sql.append("id_asl, ");
			}

			if (denominazione!= null && ! denominazione.equals("")) {
				sql.append("denominazione, ");
			}


			if (idStabilimento > -1) {
				sql.append("id, ");
			}

			if (enteredBy > -1){
				sql.append("entered_by,");
			}

			if (modifiedBy > -1){
				sql.append("modified_by,");
			}
			if (idOperatore > -1){
				sql.append("id_operatore,");
			}
			if(rappLegale!=null && rappLegale.getIdSoggetto()>0)
			{
				sql.append("id_soggetto_fisico,");

			}
			if(sedeOperativa!=null)
			{
				sql.append("id_indirizzo,");

			}

			
			if (note!= null && ! note.equals("")) {
				sql.append("note, ");
			}
			
			
			sql.append("entered,modified)");
			sql.append("VALUES (?,?,?,?,?,?,");
			
		
			if (superficie>0)
			{
				sql.append("?, ");
			}
			if (telefono!=null)
			{
				sql.append("?, ");
			}
			if (partitaIvaVariazione!=null)
			{
				sql.append("?, ");
			}
			
			if (dataRichiestaSciaAsl!=null)
			{
				sql.append("?, ");			} 
			if (numeroRegistrazioneVariazione!=null)
			{
				sql.append("?, ");
			}
			
			if (dataInizioAttivita!=null)
			{
				sql.append("?, ");			}
			if (dataFineAttivita!=null)
			{
				sql.append("?, ");			}
			
			if (dataInizioSospensione!=null)
			{
				sql.append("?, ");			
			}

			if (idAsl > 0) {
				sql.append("?, ");
			}
			if (denominazione!= null && ! denominazione.equals("")) {
				sql.append("?, ");
			}


			if (idStabilimento > -1) {
				sql.append("?,");
			}

			if (enteredBy > -1){
				sql.append("?,");
			}


			if (modifiedBy > -1){
				sql.append("?,");
			}
			if (idOperatore > -1){
				sql.append("?,");
			}
			if(rappLegale!=null && rappLegale.getIdSoggetto()>0)
			{
				sql.append("?,");

			}
			if(sedeOperativa!=null)
			{
				sql.append("?,");

			}

			
			if (note!= null && ! note.equals("")) {
				sql.append("?, ");
			}
			
			sql.append("current_date,current_date)");


			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, tipoInserimentoScia);
			pst.setInt(++i, stato);
			pst.setInt(++i, tipoCarattere);
			pst.setInt(++i, tipoAttivita);
			pst.setBoolean(++i, cessazioneStabilimento);
			pst.setBoolean(++i, sospensioneStabilimento);	
			
			if (superficie>0)
			{
				pst.setInt(++i, superficie);
			}
			
			if (telefono!=null)
			{
				pst.setString(++i, telefono);
			}
			
			if (partitaIvaVariazione!=null)
			{
				pst.setString(++i, partitaIvaVariazione);
			}
			
			if (dataRichiestaSciaAsl!=null)
			{
				pst.setTimestamp(++i, dataRichiestaSciaAsl);		} 
			if (numeroRegistrazioneVariazione!=null)
			{
				pst.setString(++i,numeroRegistrazioneVariazione);
			}
			if (dataInizioAttivita!=null)
			{
				pst.setTimestamp(++i, dataInizioAttivita);
			}
			if (dataFineAttivita!=null)
			{
				pst.setTimestamp(++i, dataFineAttivita);			
				
			}
			
			if (dataInizioSospensione!=null)
			{
				pst.setTimestamp(++i, dataInizioSospensione);			
			}
			
			if (idAsl > 0) {
				pst.setInt(++i,idAsl);
			}

			if (denominazione!= null && ! denominazione.equals("")) {
				pst.setString(++i,denominazione);
			}

			if (idStabilimento > -1) {
				pst.setInt(++i,idStabilimento);
			}

			if (enteredBy > -1){
				pst.setInt(++i, this.enteredBy);
			}


			if (modifiedBy > -1){ 
				pst.setInt(++i, this.modifiedBy);
			}
			if (idOperatore > -1){
				pst.setInt(++i, idOperatore);
			}
			if(rappLegale!=null && rappLegale.getIdSoggetto()>0)
			{
				pst.setInt(++i, rappLegale.getIdSoggetto());

			}
			if(sedeOperativa!=null)
			{
				pst.setInt(++i, sedeOperativa.getIdIndirizzo());

			}

		
			if (note!= null && ! note.equals("")) {
				pst.setString(++i, note);
			}
			
			
			pst.execute();
			pst.close();

			if (insertLinee){

				LineaProduttivaList listaLineeProduttive = this.getListaLineeProduttive();
				Iterator<LineaProduttiva> itLp= listaLineeProduttive.iterator();
				while(itLp.hasNext()){

					LineaProduttiva temp = itLp.next();
				
					this.aggiungiLineaProduttiva(db, temp);


				}
			}
			
			
			
			db.prepareStatement("update suap_ric_scia_stabilimento set modified=current_timestamp where id ="+idStabilimento).execute();

			if (doCommit) {
				db.commit();
			}
			
			
			this.altId = DatabaseUtils.generaAltId(db, this.idStabilimento, Ticket.ALT_OPU_RICHIESTE);
			
			
		} catch (SQLException e) {
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


	public void updateLineeProduttive(Connection db) throws SQLException
	{

		String update = "update suap_ric_scia_relazione_stabilimento_linee_produttive set enabled= false,modified = current_timestamp , modifiedby=? where id_stabilimento = ?";
		PreparedStatement pst = db.prepareStatement(update);
		pst.setInt(1, this.getModifiedBy());
		pst.setInt(2, this.getIdStabilimento());
		pst.execute();

		LineaProduttivaList listaLineeProduttive = this.getListaLineeProduttive();
		Iterator<LineaProduttiva> itLp= listaLineeProduttive.iterator();
		while(itLp.hasNext()){

			LineaProduttiva temp = itLp.next();
			this.aggiungiLineaProduttiva(db, temp);
			//				temp.getInfoStab().setId_opu_rel_stab_lp(temp.getId());
			//				temp.getInfoStab().sdb);

		}
		db.prepareStatement("update suap_ric_scia_stabilimento set modified=current_timestamp where id ="+idStabilimento).execute();

	}
	
	
	public void aggiungiLineeProduttive(Connection db) throws SQLException
	{

	

		LineaProduttivaList listaLineeProduttive = this.getListaLineeProduttive();
		Iterator<LineaProduttiva> itLp= listaLineeProduttive.iterator();
		while(itLp.hasNext()){

			LineaProduttiva temp = itLp.next();
			
			this.aggiungiLineaProduttiva(db, temp);
			//				temp.getInfoStab().setId_opu_rel_stab_lp(temp.getId());
			//				temp.getInfoStab().insert(db);

		}
		db.prepareStatement("update suap_ric_scia_stabilimento set modified=current_timestamp where id ="+idStabilimento).execute();

	}

	

	public LineaProduttiva aggiungiLineaProduttiva (Connection db , LineaProduttiva lp)
	{
		StringBuffer sql = new StringBuffer();
		PreparedStatement pst0 = null;
		Statement st0 = null;
//		ResultSet rs0 = null;
		try
		{
			int i = 0 ;
			
			
					
			int id = DatabaseUtils.getNextSeq(db, "suap_ric_scia_relazione_stabilimento_linee_produttive_id_seq");	
			//id e' quello che su fields value e' chiamato id_rel_stab_linea
			lp.setId(id);
			sql.append("INSERT INTO suap_ric_scia_relazione_stabilimento_linee_produttive (id, id_stabilimento,id_linea_produttiva,data_inizio,data_fine,stato,tipo_attivita_produttiva,primario,modified,modifiedby,enabled");
			
			
			sql.append(") values (?,?,?,?,?,?,?,?,current_timestamp,?,true");
			
			
			sql.append(") ");
			
			PreparedStatement pst = db.prepareStatement(sql.toString());
			
			i=0;
			pst.setInt(++i, id) ;
			pst.setInt(++i, idStabilimento) ;
			pst.setInt(++i, lp.getIdRelazioneAttivita()); //PRIMA pst.setInt(++i, lp.getId());

			pst.setTimestamp(++i, lp.getDataInizio());
			pst.setTimestamp(++i, lp.getDataFine());
			pst.setInt(++i, lp.getStato());
			pst.setInt(++i, lp.getTipoAttivitaProduttiva());

			pst.setBoolean(++i, lp.isPrincipale());
			pst.setInt(++i, this.getModifiedBy());
			
			pst.execute();

			lp.setId(lp.getIdRelazioneAttivita());
			
			
			/**************************************************************/
			
			 
			
			
			for(Integer idLineeMobiliHtmlFields : lp.getCampiEstesi().keySet())
			{
				pst0 = db.prepareStatement("insert into linee_mobili_fields_value(id_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, id_utente_inserimento) values(?,?,?,current_timestamp,?)");
				//select * from public.dbi_insert_campi_estesi                   (id_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, indice, id_utente_inserimento, id_opu_rel_stab_linea, riferimento_org_id)
				pst0 = db.prepareStatement("select * from public.dbi_insert_campi_estesi(?, ?, ?,-1, ?, -1, -1)");

				
				int idRelStabLinea = id;
				
				pst0.setInt(1,idRelStabLinea);
				pst0.setInt(2,idLineeMobiliHtmlFields);
				pst0.setInt(4,this.userId);
				
				String valoreCampo = "" ;
				
				if (lp.getCampiEstesi().get(idLineeMobiliHtmlFields).size()>1)
				{
				for (LineaProduttivaCampoEsteso valore : lp.getCampiEstesi().get(idLineeMobiliHtmlFields))
				{
					valoreCampo+=""+valore.getValore()+";";
					
				}
				}
				else
				{
					valoreCampo = lp.getCampiEstesi().get(idLineeMobiliHtmlFields).get(0).getValore();
				}
				
				pst0.setString(3, valoreCampo);
				pst0.execute();

				
				pst0.close();
				
				
			}
			
			
			/**************************************************************/
			
			
			if (db.getAutoCommit()==false)
			{
				db.commit();
			}



		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
			try{pst0.close();} catch(Exception ex){}
//			try{rs0.close();} catch(Exception ex){}

		}
		return lp ;

	}



	protected void buildRecordStabilimento(ResultSet rs) throws SQLException {

		//this.setDataCessazione(rs.getTimestamp("data_cessazione"));
		
		
		try
		{
			this.setSuperficie(rs.getInt("superficie"));
		}
		catch(PSQLException e)
		{

		}
		
		try
		{
			this.setTipoInserimentoScia(rs.getInt("tipo_reg_ric"));
		}
		catch(PSQLException e)
		{

		}
		
		try
		{
			this.setDataCessazione(rs.getTimestamp("data_fine_attivita"));
		}
		catch(PSQLException e)
		{

		}
		try
		{
			this.setDataFineAttivita(rs.getTimestamp("data_fine_attivita"));
		}
		catch(PSQLException e)
		{

		}
		
		try
		{
			this.setValidabilitaApicoltura(rs.getBoolean("validabile_apicoltura"));
		}
		catch(PSQLException e)
		{

		}
		
		try
		{
			this.setCodiceAziendaApicoltura(rs.getString("codice_azienda_apicoltura"));
		}
		catch(PSQLException e)
		{

		}
		
		
		try
		{
			this.setName(rs.getString("ragione_sociale"));
		}
		catch(PSQLException e)
		{

		}
		
		try
		{
			this.setCessazioneStabilimento(rs.getBoolean("cessazione_stabilimento"));
		}
		catch(PSQLException e)
		{

		}
		
		try
		{
			this.setDenominazione(rs.getString("denominazione"));
		}
		catch(PSQLException e)
		{

		}
		
		try
		{
			this.setDataRichiestaSciaAsl(rs.getTimestamp("data_ricezione_richiesta"));
		}
		catch(PSQLException e)
		{

		}
		
		
		try
		{
			this.sospensioneStabilimento=rs.getBoolean("sospensione_stabilimento");
		}
		catch(PSQLException e)
		{

		}
		
		try
		{
			this.dataInizioSospensione=rs.getTimestamp("data_inizio_sospensione");		}
		catch(PSQLException e)
		{

		}
		
		
		
		setTipologia(Ticket.TIPO_OPU_RICHIESTE);

		this.setIdStabilimento(rs.getInt("id"));
		this.setPartitaIvaVariazione(rs.getString("partita_iva_variazione"));
		this.setEnteredBy(rs.getInt("entered_by"));
		this.setModifiedBy(rs.getInt("modified_by"));
		this.setIdOperatore(rs.getInt("id_operatore"));
		this.setIdAsl(rs.getInt("id_asl"));
		this.setTipoAttivita(rs.getInt("tipo_attivita"));
		sedeOperativa.setIdIndirizzo(rs.getInt("id_indirizzo"));
		this.stato=rs.getInt("stato");
		this.altId=rs.getInt("alt_id");
	
	}




	public boolean checkAslStabilimentoUtente(UserBean user) {
		return (this.getIdAsl() == user.getSiteId());
	}











	int idNorma = -1 ;
	private int userId;
	

	public String getAction()
	{
		return action ;
	}

	public String getPageInclude()
	{
		return pageInclude ;
	}


	public List<Operatore> getListaOperatori() {
		return listaOperatori;
	}



	public void setListaOperatori(List<Operatore> listaOperatori) {
		this.listaOperatori = listaOperatori;
	}




	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}


	


	
	
	
	
	public void insertCampiMobile(Connection db, String targa, String tipo, String carta){
		
		try {
				String sql = "insert into suap_ric_scia_stabilimento_mobile (id_stabilimento, targa, tipo, carta) values (?, ?, ?, ?)";
				PreparedStatement pst = db.prepareStatement(sql.toString());
				pst.setInt(1, this.getIdStabilimento());
				pst.setString(2, targa);
				pst.setInt(3, Integer.parseInt(tipo));
				pst.setString(4, carta);
				pst.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private ArrayList<DatiMobile> recuperaDatiMobile(Connection db, int idStabilimento) {
		ArrayList<DatiMobile> datiMobile = new ArrayList<DatiMobile>();
		PreparedStatement pst;
		
		try {
			pst = db.prepareStatement("Select * from suap_ric_scia_stabilimento_mobile where id_stabilimento = ? and enabled order by id ASC");
			pst.setInt(1, idStabilimento);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			while (rs.next()) {
				DatiMobile dato = new DatiMobile(rs);
				datiMobile.add(dato);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	System.out.println("Scheda centralizzata errore su query "+query);
			e.printStackTrace();
		} 
		return datiMobile;
	}

	public ArrayList <DatiMobile> getDatiMobile() {
		return datiMobile;
	}

	public void setDatiMobile(ArrayList <DatiMobile> datiMobile) {
		this.datiMobile = datiMobile;
	}
	
	
	public StabilimentoList inserisciinListaNoDuplicato(List<Stabilimento> lista , Stabilimento op)
	{
		
		StabilimentoList listaToRet = new StabilimentoList();
		
		if (listaToRet.size()>0)
		{
		for (Stabilimento operatore : lista)
		{
			if (op.compareTo(operatore,op)!=0)
			{
				listaToRet.add(op);
			}
		}
		}
		else
			listaToRet.add(op);
		return listaToRet ;
	}


public int compareTo(Stabilimento op1,Stabilimento op2) {
    
	
	
	
	if (op1.getSedeOperativa().compareTo(op2.getSedeOperativa()) ==0)
		return 0;
	return 1 ;
	

}
	
	
	//OVERRIDE DEL METODO EQUALS PER IL CONFRONTO CON LA BDN
		public ArrayList equals(AllevamentoAjax all)
		{
			ArrayList differenze = new ArrayList();

			if (!this.getOperatore().getRagioneSociale().equals(all.getDenominazione()))
				differenze.add("denominazione");
			
			
			
			
					
		
			
				
			return differenze;
			
		}
		
		public StabilimentoList getFromOpu(Connection db, String partitaIva, String comune,String numeroReg,String tipoAttivita){

			String query = "select * from suap_trova_stabilimento_opu(?, ?,?) where  1=1 ";
			
			if (tipoAttivita!=null && !"".equals(tipoAttivita))
				query+= " and tipo_attivita = ?";
			
			
			Stabilimento stabilimento = null;
			StabilimentoList listaStab = new StabilimentoList();
			PreparedStatement pst;
			try {
				int i = 0;
				pst = db.prepareStatement(query);
				
				
				if (numeroReg!= null && !numeroReg.equals(""))
				{
				pst.setString(++i, partitaIva);
				pst.setString(++i, "");
				pst.setString(++i, numeroReg);
			}
			else
			{
				pst.setString(++i, partitaIva);
				pst.setString(++i, comune);
				pst.setString(++i, "");
			}
				if (tipoAttivita!=null && !"".equals(tipoAttivita))
					pst.setInt(++i, Integer.parseInt(tipoAttivita));

				
					 
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					

					Operatore operatoreSuap = new Operatore();
					stabilimento = new Stabilimento();
					SoggettoFisico rappLegaleSuap = new SoggettoFisico();
					
					//org.aspcfs.modules.opu.base.Stabilimento stabOpu = new org.aspcfs.modules.opu.base.Stabilimento(rs);
					org.aspcfs.modules.opu.base.Indirizzo sedeLegaleOpu = new org.aspcfs.modules.opu.base.Indirizzo();
					org.aspcfs.modules.opu.base.Indirizzo sedeOperativaOpu = new org.aspcfs.modules.opu.base.Indirizzo(db, rs.getInt("id_indirizzo"));
					org.aspcfs.modules.opu.base.Operatore operatoreOpu = new org.aspcfs.modules.opu.base.Operatore();
					org.aspcfs.modules.opu.base.SoggettoFisico rappLegaleOpu = new org.aspcfs.modules.opu.base.SoggettoFisico();
					
					operatoreOpu.queryRecordOperatore(db, rs.getInt("id_operatore"));
					
					if (operatoreOpu.getSedeLegale()!=null)
						sedeLegaleOpu = operatoreOpu.getSedeLegale();
					
					operatoreSuap.setSedeLegaleImpresa(sedeLegaleOpu);
					
					if (operatoreOpu.getRappLegale()!=null)
					{
						rappLegaleOpu = operatoreOpu.getRappLegale();
					
					rappLegaleSuap.setNome(rappLegaleOpu.getNome());
					rappLegaleSuap.setCognome(rappLegaleOpu.getCognome());
					rappLegaleSuap.setCodFiscale(rappLegaleOpu.getCodFiscale());
					}
					
					operatoreSuap.setRappLegale(rappLegaleSuap);
					operatoreSuap.setRagioneSociale(operatoreOpu.getRagioneSociale());
					operatoreSuap.setPartitaIva(operatoreOpu.getPartitaIva());
					
					stabilimento.setSedeOperativa(sedeOperativaOpu);
					stabilimento.setOperatore(operatoreSuap);
					stabilimento.setNumeroRegistrazione(rs.getString("numero_registrazione"));
					
					this.setName(rs.getString("ragione_sociale"));
					
					//datiMobile = recuperaDatiMobile(db, this.getIdStabilimento());
				
					listaStab.add(stabilimento);
				}
//				listaStab = inserisciinListaNoDuplicato(listaStab,stabilimento);
				
			} catch (SQLException | IndirizzoNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return listaStab;

		}
		
		public StabilimentoList getFromOrganization(Connection db, String partitaIva, String comune){

			String query = "select * from suap_trova_stabilimento_org(?, ?)";
			
			Stabilimento stabilimento = null;
			StabilimentoList listaStab = new StabilimentoList();
			PreparedStatement pst;
			try {
				
				LookupList provinceList = new LookupList(db, "lookup_province");
				
				int i = 0;
				pst = db.prepareStatement(query);
				pst.setString(++i, partitaIva);
				pst.setString(++i, comune);
					 
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					stabilimento = new Stabilimento(rs);
					
					Indirizzo sedeOperativa = new Indirizzo();
					int comuneOperativa = trovaIdComune(db, rs.getString("operativa_comune"));
					String comuneOperativaTesto = trovaNomeComune(db, comuneOperativa);
					String provinciaOperativa = trovaCodeProvincia(db,comuneOperativa);
					String provinciaOperativaTesto =provinceList.getSelectedValue(provinciaOperativa);
					sedeOperativa.setComune(comuneOperativa);
					sedeOperativa.setDescrizioneComune(comuneOperativaTesto);
					sedeOperativa.setProvincia(provinciaOperativa);
					sedeOperativa.setDescrizione_provincia(provinciaOperativaTesto);
					sedeOperativa.setVia(rs.getString("operativa_via"));
					stabilimento.setSedeOperativa(sedeOperativa);
					Operatore op = new Operatore();
					op.setRagioneSociale(rs.getString("ragione_sociale"));
					op.setPartitaIva(partitaIva);
					op.setRappLegale(new SoggettoFisico());
					op.setSedeLegaleImpresa(new Indirizzo(db, -1));
					stabilimento.setOperatore(op);
					listaStab.add(stabilimento);
					
					
				}
				
//				listaStab = inserisciinListaNoDuplicato(listaStab,stabilimento);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return listaStab;

		}
		

private int trovaIdComune(Connection db, String comune){
String query = "select id from comuni1 where nome ilike trim(?)";
	PreparedStatement pst;
	
		int i = 0;
		try {
			pst = db.prepareStatement(query);
		
		pst.setString(++i, comune);
		ResultSet rs = pst.executeQuery();

		if (rs.next())
			return rs.getInt("id");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
}

private String trovaNomeComune(Connection db, int comune){
String query = "select nome from comuni1 where id = ?";
	PreparedStatement pst;
	
		int i = 0;
		try {
			pst = db.prepareStatement(query);
		
		pst.setInt(++i, comune);
		ResultSet rs = pst.executeQuery();

		if (rs.next())
			return rs.getString("nome");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
}

private String trovaCodeProvincia(Connection db, int idComune){
String query = "select cod_provincia from comuni1 where id = ?";
	PreparedStatement pst;
	
		int i = 0;
		try {
			pst = db.prepareStatement(query);
		
		pst.setInt(++i, idComune);
		ResultSet rs = pst.executeQuery();

		if (rs.next())
			return rs.getString("cod_provincia");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
}





public BeanPerXmlRichiesta getBeanPerXml() {
	return beanPerXml;
}





public void setBeanPerXml(BeanPerXmlRichiesta beanPerXml) {
	this.beanPerXml = beanPerXml;
}





public void setRagioneSociale(String string) {
	this.ragioneSociale = string;
	
}

public String getRagioneSociale() {return this.ragioneSociale; }


public int getOrgId() {
	return altId;
}


public int getAltId() {
	return altId;
}





public void setAltId(int altId) {
	this.altId = altId;
}

public int getSuperficie() {
	return superficie;
}



public void setSuperficie(int superficie) {
	this.superficie = superficie;
}



public  String getPrefissoAction(String actionName)
{
action = "GisaSuapStab";
return action ;
}

public String getContainer()
{
	String container = "suaprichieste" ;
	
	return container ;
} 

public int getCategoriaRischio() {
	return categoriaRischio;
}

public void setCategoriaRischio(int categoriaRischio) {
	this.categoriaRischio = categoriaRischio;
}


public String generaNumeroRegistrazione(Connection db, String idRichiesta,boolean writeMode) throws Exception
{
	PreparedStatement pst = null;
	ResultSet rs = null;
	try
	{
		char mode = writeMode ? 'w' : 'r';
		pst= db.prepareStatement("select suap_genera_numero_registrazione from public_functions.suap_genera_numero_registrazione(?,?)");
		pst.setInt(1,Integer.parseInt(idRichiesta));
		pst.setString(2, mode+"");
		rs = pst.executeQuery();
		rs.next();
		String codiceRegionale = rs.getString(1);
		setNumeroRegistrazione(codiceRegionale);
		return getNumeroRegistrazione();
	}
	catch(Exception ex)
	{
		throw ex;
	}
	finally
	{
		if(pst!=null) pst.close();
		if(rs!=null) rs.close();
	}
	
}


public void setUserId(int idUser) {

	this.userId = idUser;
}

public int getUserId(){return this.userId;}


public boolean getEsistenzaInserimento(Connection db, int idUtente, int tipoAttivita, String partitaIva, String comune, String via, String civico, int linea){
	boolean esito = false;
	PreparedStatement pst;
	try {
		pst = db.prepareStatement("select * from public_functions.suap_verifica_scia_nuovo_stabilimento(?, ?, ?, ?, ?, ?, ?);");
	
	int i = 0;
	pst.setInt(++i, idUtente);
	pst.setInt(++i, tipoAttivita);
	pst.setString(++i, partitaIva);
	pst.setString(++i, comune);
	pst.setString(++i, via);
	pst.setString(++i, civico);
	pst.setInt(++i, linea);

	ResultSet rs = pst.executeQuery();
	if (rs.next()) {
		esito = rs.getBoolean(1);
	}

	rs.close();
	pst.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return esito;
}



}
