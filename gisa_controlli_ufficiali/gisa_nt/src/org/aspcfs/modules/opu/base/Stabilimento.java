/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
import java.util.Map;

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
import org.aspcfs.modules.gestioneanagrafica.base.OggettoPerStorico;
import org.aspcfs.modules.lineeattivita.base.LineeCampiEstesi;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.util.StabilimentoImportUtil;
import org.aspcfs.modules.suap.actions.InterfValidazioneRichieste;
import org.aspcfs.modules.suap.base.LineaProduttivaCampoEsteso;
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

	public static final int TIPO_SCIA_REGISTRABILI = 1;
	public static final int TIPO_SCIA_RICONOSCIUTI = 2;

	private String targaImportata;
	private int idTipoVeicolo;

	private List<Operatore> listaOperatori = null;

	public static final String OPERAZIONE_INSERIMENTO_OK = "1";
	public static final String OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE = "2";
	public static final String OPERAZIONE_INSERIMENTO_KO_STABILIMENTO_ESISTENTE = "4";
	public static final String OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE_ORGANIZATION = "organization";

	public static final int STATO_IN_DOMANDA = 3;
	public static final int STATO_AUTORIZZATO = 0;
	public static final int STATO_SOSPESO = 2;
	public static final int STATO_CESSATO = 4;

	public static final int STATO_RESPINTO = 6;
	public static final int STATO_REGISTRAZIONE_ND = 7;

	public static final int ATTIVITA_FISSA = 1;
	public static final int ATTIVITA_MOBILE = 2;
	public static final int ATTIVITA_API = 3;

	private String action = "";
	private String pageInclude = "";
	private String codiceInternoReg;

	private int idRelStabLpMobile;
	private String dataInizioAttivitaString;
	private String dataFineAttivitaString;
	private int idOperatore;
	private Indirizzo sedeOperativa = new Indirizzo();
	private LineaProduttivaList listaLineeProduttive = new LineaProduttivaList();
	private LineaProduttivaList listaLineeProduttivePregresse = new LineaProduttivaList();
	private Timestamp entered;
	private Timestamp modified;
	private int enteredBy;
	private int modifiedBy;
	private String codiceInterno;
	private boolean flagFuoriRegione = false;
	private SoggettoFisico rappLegale = new SoggettoFisico();
	private int categoriaRischio;
	private Timestamp dataProssimoControllo;

	private String numProtocollo;
	/*
	 * PARAMETRO USATO PER MANTENERE LA GENERALIZZAZIONE DELLA GESTIONE DEI
	 * CONTROLLI
	 */
	private int orgId = -1;
	private int siteId;
	private String name;
	private boolean flagDia;
	private Timestamp datafineDia;
	private Timestamp dataCessazione;
	private Operatore operatore;
	private int stato;
	private ArrayList<Distrubutore> listaDistributori = new ArrayList<Distrubutore>();
	private String attivitaOld;
	private String numero_registrazione;

	private boolean lineePregresse = false;

	private String erroreSuap;
	private String codiceErroreSuap;
	private String denominazione;

	private int tipologia;

	private String note = "";

	private int tipoCarattere;
	private int tipoAttivita;

	private Timestamp dataInizioAttivita;
	private Timestamp dataFineAttivita;

	private int tipoInserimentoScia;

	private boolean importato = false;

	private String codice_ufficiale_esistente;

	private int tipoVecchiaOrg = -1;

	private int altId = -1;

	private ArrayList<DatiMobile> datiMobile = new ArrayList<DatiMobile>();

	private boolean flagLineaSospesa;

	

	public String getTargaImportata() {
		return targaImportata;
	}

	public void setTargaImportata(String targaImportata) {
		this.targaImportata = targaImportata;
	}

	public int getIdTipoVeicolo() {
		return idTipoVeicolo;
	}

	public void setIdTipoVeicolo(int idTipoVeicolo) {
		this.idTipoVeicolo = idTipoVeicolo;
	}

	public int getIdRelStabLpMobile() {
		return idRelStabLpMobile;
	}

	public void setIdRelStabLpMobile(int idRelStabLpMobile) {
		this.idRelStabLpMobile = idRelStabLpMobile;
	}

	public boolean isFlagLineaSospesa() {

		boolean isSospeso = false;
		Iterator<LineaProduttiva> lpIt = listaLineeProduttive.iterator();
		while (lpIt.hasNext()) {
			LineaProduttiva lp = lpIt.next();

			if (lp.getStato() == STATO_SOSPESO && lp.isSciaSospensione() == true) {
				isSospeso = true;
				break;
			}
		}
		return isSospeso;
	}

	public void setFlagLineaSospesa(boolean flagLineaSospesa) {
		this.flagLineaSospesa = flagLineaSospesa;
	}

	public void setCodice_ufficiale_esistente(String codice_ufficiale_esistente) {
		this.codice_ufficiale_esistente = codice_ufficiale_esistente;
	}

	public String getCodice_ufficiale_esistente() {
		return codice_ufficiale_esistente;
	}

	public int getTipoInserimentoScia() {
		return tipoInserimentoScia;
	}

	public String getCodiceInternoReg() {
		return codiceInternoReg;
	}

	public void setCodiceInternoReg(String codiceInternoReg) {
		this.codiceInternoReg = codiceInternoReg;
	}

	public void setTipoInserimentoScia(int tipoInserimentoScia) {
		this.tipoInserimentoScia = tipoInserimentoScia;
	}

	public Timestamp getDataInizioAttivita() {
		return dataInizioAttivita;
	}

	public void setDataInizioAttivita(Timestamp dataInizioAttivita) {
		this.dataInizioAttivita = dataInizioAttivita;

		if (this.dataInizioAttivita != null) {
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
		if (dataInizioAttivita != null && !"".equals(dataInizioAttivita)) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataInizioAttivita = new Timestamp(sdf.parse(dataInizioAttivita).getTime());

		}

	}

	public Timestamp getDataFineAttivita() {
		return dataFineAttivita;
	}

	public void setDataFineAttivita(Timestamp dataFineAttivita) {
		this.dataFineAttivita = dataFineAttivita;

		if (this.dataFineAttivita != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataFineAttivitaString = sdf.format(new Date(dataFineAttivita.getTime()));

		}
	}

	public void setDataFineAttivita(String dataFineAttivita) throws ParseException {
		if (dataFineAttivita != null && !"".equals(dataFineAttivita)) {
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
		if (tipoCarattere != null && !"".equals(tipoCarattere)) {
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
		if (tipoAttivita != null && !"".equals(tipoAttivita)) {
			this.tipoAttivita = Integer.parseInt(tipoAttivita);
		}
	}

	public String getNumProtocollo() {
		return numProtocollo;
	}

	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
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

	public String getCodiceInterno() {
		return codiceInterno;
	}

	public void setCodiceInterno(String codiceInterno) {
		this.codiceInterno = codiceInterno;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}

	public String getAttivitaOld() {
		return attivitaOld;
	}

	public void setAttivitaOld(String attivitaOld) {
		this.attivitaOld = attivitaOld;
	}

	public String getNumero_registrazione() {
		return numero_registrazione;
	}

	public void setNumero_registrazione(String numero_registrazione) {
		this.numero_registrazione = numero_registrazione;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public Stabilimento() {

		rappLegale = new SoggettoFisico();
		sedeOperativa = new Indirizzo();
		this.operatore = new Operatore();
		this.setSedeOperativa(new Indirizzo());

	}

	public Stabilimento(Connection db, int idStabilimento) throws SQLException, IndirizzoNotFoundException {

		queryRecordStabilimento(db, idStabilimento);
		listaDistributori = getDistributori(db, this.getIdStabilimento());
		operatore = new Operatore();
		operatore.queryRecordOperatore(db, idOperatore);
		this.setName(operatore.getRagioneSociale());
		datiMobile = recuperaDatiMobile(db, this.getIdStabilimento());
		setStatoStabilimentoCalcola(db, this.altId);
	}

	public Stabilimento(Connection db, int altId, boolean nuova) throws SQLException, IndirizzoNotFoundException {

		queryRecordStabilimentoAlt(db, altId);
		listaDistributori = getDistributori(db, this.getIdStabilimento());
		operatore = new Operatore();
		operatore.queryRecordOperatore(db, idOperatore);
		this.setName(operatore.getRagioneSociale());
		datiMobile = recuperaDatiMobile(db, this.getIdStabilimento());
		setStatoStabilimentoCalcola(db, this.altId);
	}
	
	private void setStatoStabilimentoCalcola(Connection db, int altId) throws SQLException{
			
			int id_stato_stab = 0;
			String sql = "select get_stato_stabilimento as id_stato_stab from get_stato_stabilimento(?)";
        	PreparedStatement st = db.prepareStatement(sql);
        	st.setInt(1, altId);
            ResultSet rs = st.executeQuery();
			while(rs.next()){
				id_stato_stab = rs.getInt("id_stato_stab");
            }
			this.setStato(id_stato_stab);
	}

	public LineaProduttiva getLineaProduttivaPrimaria() {
		Iterator<LineaProduttiva> itProd = listaLineeProduttive.iterator();
		while (itProd.hasNext()) {
			LineaProduttiva thisLine = itProd.next();
			if (thisLine.isPrincipale())
				return thisLine;
		}
		return null;
	}

	public Operatore getOperatore() {
		return operatore;
	}

	public void setOperatore(Operatore operatore) {
		this.operatore = operatore;
	}

	public ArrayList<Distrubutore> getListaDistributori() {
		return listaDistributori;
	}

	public void setListaDistributori(ArrayList<Distrubutore> listaDistributori) {
		this.listaDistributori = listaDistributori;
	}

	public boolean isFlagDia() {
		return flagDia;
	}

	public void setFlagDia(boolean flagDia) {
		this.flagDia = flagDia;
	}

	public Timestamp getDatafineDia() {
		return datafineDia;
	}

	public void setDatafineDia(Timestamp datafineDia) {
		this.datafineDia = datafineDia;
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

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public void setOrgId(String orgId) {
		if (orgId != null && !orgId.equals("") && !orgId.equals("null"))
			this.orgId = Integer.parseInt(orgId);
	}

	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}

	public void setIdOperatore(String idOperatore) {
		if (idOperatore != null && !"".equals(idOperatore))
			this.idOperatore = Integer.parseInt(idOperatore);
	}

	public boolean isFlagFuoriRegione() {
		return flagFuoriRegione;
	}

	public void setFlagFuoriRegione(boolean flagFuoriRegione) {
		this.flagFuoriRegione = flagFuoriRegione;
	}

	public void setFlagFuoriRegione(String flagInRegione) {
		this.flagFuoriRegione = (("NO").equalsIgnoreCase(flagInRegione));
	}

	public Stabilimento(ResultSet rs) throws SQLException {
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
		if (idAsl != null && !idAsl.equals(""))
			this.idAsl = Integer.parseInt(idAsl);
	}

	public int getIdAsl() {

		return this.idAsl;
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

	public Timestamp getDataProssimoControllo() {
		return dataProssimoControllo;
	}

	public void setDataProssimoControllo(Timestamp dataProssimoControllo) {
		this.dataProssimoControllo = dataProssimoControllo;
	}

	public boolean isImportato() {
		return importato;
	}

	public void setImportato(boolean importato) {
		this.importato = importato;
	}

	public void queryRecordStabilimento(Connection db, int idStabilimento)
			throws SQLException, IndirizzoNotFoundException {
		if (idStabilimento == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement(
				"Select o.*,impresa.ragione_sociale from opu_stabilimento o join opu_operatore impresa on impresa.id=o.id_operatore where o.id = ?");
		pst.setInt(1, idStabilimento);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecordStabilimento(rs);
			sedeOperativa = new Indirizzo(db, sedeOperativa.getIdIndirizzo());
			listaLineeProduttive.setIdStabilimento(idStabilimento);
			listaLineeProduttive.buildList(db);
			this.operatore = new Operatore();
			this.operatore.queryRecordOperatoreSenzaStabilimenti(db, this.getIdOperatore());
		}

		if (idStabilimento == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		rs.close();
		pst.close();

	}

	public void queryRecordStabilimentoAlt(Connection db, int altId) throws SQLException, IndirizzoNotFoundException {
		if (altId == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement(
				"Select o.*,impresa.ragione_sociale from opu_stabilimento o join opu_operatore impresa on impresa.id=o.id_operatore where o.alt_id = ?");
		pst.setInt(1, altId);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecordStabilimento(rs);
			sedeOperativa = new Indirizzo(db, sedeOperativa.getIdIndirizzo());
			listaLineeProduttive.setIdStabilimento(idStabilimento);
			listaLineeProduttive.buildList(db);
		}

		if (idStabilimento == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		rs.close();
		pst.close();

	}

	public void queryRecordStabilimento(Connection db, String numeroRegistrazione)
			throws SQLException, IndirizzoNotFoundException {
		if (idStabilimento == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement(
				"Select o.*,impresa.ragione_sociale from opu_stabilimento o join opu_operatore impresa on impresa.id=o.id_operatore where o.numero_registrazione ilike ?");
		pst.setString(1, numeroRegistrazione);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecordStabilimento(rs);
			sedeOperativa = new Indirizzo(db, sedeOperativa.getIdIndirizzo());
			listaLineeProduttive.setIdStabilimento(idStabilimento);
			listaLineeProduttive.buildList(db);
		}

		if (idStabilimento == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		rs.close();
		pst.close();

	}

	public boolean insert(Connection db, boolean insertLinee, ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		try {
			modifiedBy = enteredBy;

			if (this.getIdStabilimento() <= 0) {
				this.idStabilimento = DatabaseUtils.getNextSeq(db, "opu_stabilimento_id_seq");
			}
			sql.append("INSERT INTO opu_stabilimento (stato,num_protocollo,tipo_carattere,tipo_attivita,");

			if (codice_ufficiale_esistente != null) {
				sql.append("codice_ufficiale_esistente, ");
			}

			if (codiceInternoReg != null) {
				sql.append("codiceInternoReg, ");
			}

			if (dataProssimoControllo != null) {
				sql.append("data_prossimo_controllo, ");
			}

			if (categoriaRischio > -1) {
				sql.append("categoria_rischio, ");
			}

			if (this.getOrgId() > 0) {
				sql.append("riferimento_org_id, ");
			}

			if (dataInizioAttivita != null) {
				sql.append("data_inizio_attivita, ");
			}
			if (dataFineAttivita != null) {
				sql.append("data_fine_attivita, ");
			}
			if (idAsl > 0) {
				sql.append("id_asl, ");
			}

			if (denominazione != null && !denominazione.equals("")) {
				sql.append("denominazione, ");
			}

			if (idStabilimento > -1) {
				sql.append("id, ");
			}

			if (enteredBy > -1) {
				sql.append("entered_by,");
			}

			if (modifiedBy > -1) {
				sql.append("modified_by,");
			}
			if (idOperatore > -1) {
				sql.append("id_operatore,");
			}
			if (rappLegale != null && rappLegale.getIdSoggetto() > 0) {
				sql.append("id_soggetto_fisico,");

			}
			if (sedeOperativa != null) {
				sql.append("id_indirizzo,");

			}

			if (flagFuoriRegione) {
				sql.append("flag_fuori_regione,");
			}

			if (note != null && !note.equals("")) {
				sql.append("note, ");
			}
			if (importato) {
				sql.append("importato,");
			}

			

			sql.append("entered,modified)");
			sql.append("VALUES (?,?,?,?,");

			if (codice_ufficiale_esistente != null) {
				sql.append("?, ");
			}
			if (codiceInternoReg != null) {
				sql.append("?, ");
			}
			if (dataProssimoControllo != null) {
				sql.append("?, ");
			}

			if (categoriaRischio > -1) {
				sql.append("?, ");
			}

			if (this.getOrgId() > 0) {
				sql.append("?, ");
			}

			if (dataInizioAttivita != null) {
				sql.append("?, ");
			}
			if (dataFineAttivita != null) {
				sql.append("?, ");
			}

			if (idAsl > 0) {
				sql.append("?, ");
			}
			if (denominazione != null && !denominazione.equals("")) {
				sql.append("?, ");
			}

			if (idStabilimento > -1) {
				sql.append("?,");
			}

			if (enteredBy > -1) {
				sql.append("?,");
			}

			if (modifiedBy > -1) {
				sql.append("?,");
			}
			if (idOperatore > -1) {
				sql.append("?,");
			}
			if (rappLegale != null && rappLegale.getIdSoggetto() > 0) {
				sql.append("?,");

			}
			if (sedeOperativa != null) {
				sql.append("?,");

			}

			if (flagFuoriRegione) {
				sql.append("?,");
			}
			if (note != null && !note.equals("")) {
				sql.append("?, ");
			}
			if (importato) {
				sql.append("?, ");
			}

			

			sql.append("current_date,current_date)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, stato);
			pst.setString(++i, numProtocollo);
			pst.setInt(++i, tipoCarattere);
			pst.setInt(++i, tipoAttivita);

			if (codice_ufficiale_esistente != null) {
				pst.setString(++i, this.getCodice_ufficiale_esistente());
			}

			if (codiceInternoReg != null) {
				pst.setString(++i, this.getCodiceInternoReg());
			}

			if (dataProssimoControllo != null) {
				pst.setTimestamp(++i, this.getDataProssimoControllo());
			}

			if (categoriaRischio > -1) {
				pst.setInt(++i, this.getCategoriaRischio());
			}

			if (this.getOrgId() > 0) {
				pst.setInt(++i, this.getOrgId());
			}

			if (dataInizioAttivita != null) {
				pst.setTimestamp(++i, dataInizioAttivita);
			}
			if (dataFineAttivita != null) {
				pst.setTimestamp(++i, dataFineAttivita);

			}
			if (idAsl > 0) {
				pst.setInt(++i, idAsl);
			}

			if (denominazione != null && !denominazione.equals("")) {
				pst.setString(++i, denominazione);
			}

			if (idStabilimento > -1) {
				pst.setInt(++i, idStabilimento);
			}

			if (enteredBy > -1) {
				pst.setInt(++i, this.enteredBy);
			}

			if (modifiedBy > -1) {
				pst.setInt(++i, this.modifiedBy);
			}
			if (idOperatore > -1) {
				pst.setInt(++i, idOperatore);
			}
			if (rappLegale != null && rappLegale.getIdSoggetto() > 0) {
				pst.setInt(++i, rappLegale.getIdSoggetto());

			}
			if (sedeOperativa != null) {
				pst.setInt(++i, sedeOperativa.getIdIndirizzo());

			}

			if (flagFuoriRegione) {
				pst.setBoolean(++i, flagFuoriRegione); // di default e' false
			}

			if (note != null && !note.equals("")) {
				pst.setString(++i, note);
			}
			if (importato) {
				pst.setBoolean(++i, importato);
			}

			

			pst.execute();
			pst.close();

			if (insertLinee) {

				LineaProduttivaList listaLineeProduttive = this.getListaLineeProduttive();
				Iterator<LineaProduttiva> itLp = listaLineeProduttive.iterator();
				while (itLp.hasNext()) {

					LineaProduttiva temp = itLp.next();
					int idRelStabLp = this.aggiungiLineaProduttiva(db, temp,modifiedBy);
					temp.setId(idRelStabLp);

				}
			}
			db.prepareStatement("update opu_stabilimento set modified=current_timestamp where id =" + idStabilimento)
					.execute();

		} catch (SQLException e) {
			e.printStackTrace();

			throw new SQLException(e.getMessage());
		} finally {

		}
		return true;
	}

	public void updateLineeProduttive(Connection db) throws SQLException {

		String update = "update opu_relazione_stabilimento_linee_produttive set enabled= false,modified = current_timestamp , modifiedby=? where id_stabilimento = ?";
		PreparedStatement pst = db.prepareStatement(update);
		pst.setInt(1, this.getModifiedBy());
		pst.setInt(2, this.getIdStabilimento());
		pst.execute();

		LineaProduttivaList listaLineeProduttive = this.getListaLineeProduttive();
		Iterator<LineaProduttiva> itLp = listaLineeProduttive.iterator();
		while (itLp.hasNext()) {

			LineaProduttiva temp = itLp.next();
			this.aggiungiLineaProduttiva(db, temp,this.getModifiedBy());
			// temp.getInfoStab().setId_opu_rel_stab_lp(temp.getId());
			// temp.getInfoStab().insert(db);

		}
		db.prepareStatement("update opu_stabilimento set modified=current_timestamp where id =" + idStabilimento)
				.execute();

	}

	
	public String getApprovalNumber(Connection db) throws SQLException {
		String approvalNumber = null;
		PreparedStatement pst = db.prepareStatement(
				"select COALESCE(codice_ufficiale_esistente, codice_nazionale) from opu_relazione_stabilimento_linee_produttive where id_stabilimento = ? and enabled and codice_ufficiale_esistente is not null");
		pst.setInt(1, this.getIdStabilimento());
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			approvalNumber = rs.getString(1);

		return approvalNumber;

	}

	public int aggiungiLineaProduttiva(Connection db, LineaProduttiva lp, int userId) {
		int id = -1;
		StringBuffer sql = new StringBuffer();
		try {
			int i = 0;

			String sql1 = "select * from opu_relazione_stabilimento_linee_produttive where id_stabilimento = ? and id_linea_produttiva = ?";
			PreparedStatement pst1 = db.prepareStatement(sql1);
			pst1.setInt(++i, idStabilimento);
			pst1.setInt(++i, lp.getIdRelazioneAttivita());
			ResultSet rs = pst1.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
				lp.setNumeroRegistrazione(rs.getString("numero_registrazione"));
				lp.setCodice_ufficiale_esistente(getApprovalNumber(db));
				lp.setId(rs.getInt("id"));

				if (this.isImportato() && tipoAttivita != 2) {
					importaControlliUfficiali(db, lp.getIdVecchiaLinea(), id, lp.isPrincipale(), lp.getCodice(),userId);
				} else {
					if (this.isImportato() && tipoAttivita == 2) {
						this.idRelStabLpMobile = id;
						importaTarga(db, this.targaImportata, this.getIdTipoVeicolo() + "", id);
						importaControlliUfficialionTarga(db, lp.getIdVecchiaLinea(), id, lp.isPrincipale(),
								this.targaImportata, lp.getCodice(),userId);

					}
				}

			} else {
				id = DatabaseUtils.getNextSeq(db, "opu_relazione_stabilimento_linee_produttive_id_seq");

				String numReg = null;
				if (numero_registrazione != null)
					numReg = this.numero_registrazione + org.aspcfs.utils.StringUtils
							.zeroPad(lp.getProgressivoLineaAttivita(db, numero_registrazione), 3);
				lp.setNumeroRegistrazione(numReg);
				this.idRelStabLpMobile = id;
				sql.append(
						"INSERT INTO opu_relazione_stabilimento_linee_produttive (id, id_stabilimento,id_linea_produttiva,data_inizio,data_fine,stato,tipo_attivita_produttiva,primario,modified,modifiedby,entered, enteredby, enabled,numero_registrazione,codice_ufficiale_esistente,id_vecchia_linea,num_protocollo,codice_nazionale");

				if (dataProssimoControllo != null) {
					sql.append(",data_prossimo_controllo ");
				}

				if (categoriaRischio > -1) {
					sql.append(",categoria_rischio");
				}
				if (tipoVecchiaOrg > 0) {
					sql.append(",id_tipo_vecchio_operatore ");
				}

				sql.append(
						",pregresso_o_import,note) values (?,?,?,?,?,?,?,?,current_timestamp,?,current_timestamp,?,true,?,?,?,?,?");

				if (dataProssimoControllo != null) {
					sql.append(",? ");
				}

				if (categoriaRischio > -1) {
					sql.append(",? ");
				}
				if (tipoVecchiaOrg > 0) {
					sql.append(",?");
				}

				sql.append(",true ");
				sql.append(", ?) ");

				PreparedStatement pst = db.prepareStatement(sql.toString());

				i = 0;
				pst.setInt(++i, id);
				pst.setInt(++i, idStabilimento);
				pst.setInt(++i, lp.getIdRelazioneAttivita()); // PRIMA
																// pst.setInt(++i,
																// lp.getId());

				pst.setTimestamp(++i, lp.getDataInizio());
				pst.setTimestamp(++i, lp.getDataFine());
				pst.setInt(++i, lp.getStato());
				pst.setInt(++i, lp.getTipoAttivitaProduttiva());

				pst.setBoolean(++i, lp.isPrincipale());
				pst.setInt(++i, this.getModifiedBy());
				pst.setInt(++i, this.getModifiedBy());
				pst.setString(++i, lp.getNumeroRegistrazione());
				pst.setString(++i, lp.getCodice_ufficiale_esistente());
				pst.setInt(++i, lp.getIdVecchiaLinea());
				pst.setString(++i, lp.getNum_protocollo());

				pst.setString(++i, lp.getCodice());

				if (dataProssimoControllo != null) {
					pst.setTimestamp(++i, lp.getDataProssimoControllo());
				}

				if (categoriaRischio > -1) {
					pst.setInt(++i, lp.getCategoriaRischio());
				}

				if (tipoVecchiaOrg > 0) {
					pst.setInt(++i, this.getTipoVecchiaOrg());
				}

				// nelle note metto id linea opu fittizia (la pregressa) verso
				// cui è stato fatto mapping
				pst.setString(++i, "");

				pst.execute();

				lp.setId(lp.getIdRelazioneAttivita());
				lp.setId(id);

				if (this.isImportato() && tipoAttivita != 2) {
					importaControlliUfficiali(db, lp.getIdVecchiaLinea(), id, lp.isPrincipale(), lp.getCodice(),this.getModifiedBy());
				} else {
					if (this.isImportato() && tipoAttivita == 2) {
						importaTarga(db, this.targaImportata, this.getIdTipoVeicolo() + "", id);
						importaControlliUfficialionTarga(db, lp.getIdVecchiaLinea(), id, lp.isPrincipale(),
								this.targaImportata, lp.getCodice(),userId);

					}
				}
			}

			if (db.getAutoCommit() == false) {
				db.commit();
			}

			if (lp.getCampiEstesi() != null && tipoAttivita != 2)
				for (Integer idLineeMobiliHtmlFields : lp.getCampiEstesi().keySet()) {
					// PreparedStatement pst0 = db.prepareStatement("insert into
					// linee_mobili_fields_value(id_rel_stab_linea,id_linee_mobili_html_fields,valore_campo,id_utente_inserimento)
					// values(?,?,?,current_timestamp,?)");
					// select * from
					// public.dbi_insert_campi_estesi(id_rel_stab_linea,
					// id_linee_mobili_html_fields, valore_campo, indice,
					// id_utente_inserimento, id_opu_rel_stab_linea,
					// riferimento_org_id)
					PreparedStatement pst0 = db
							.prepareStatement("select * from public.dbi_insert_campi_estesi(?, ?, ?, -1, ?, -1, -1)");

					int idRelStabLinea = id;

					pst0.setInt(1, idRelStabLinea);
					pst0.setInt(2, idLineeMobiliHtmlFields);
					pst0.setInt(4, this.enteredBy);

					String valoreCampo = "";

					if (lp.getCampiEstesi().get(idLineeMobiliHtmlFields).size() > 1) {
						for (LineaProduttivaCampoEsteso valore : lp.getCampiEstesi().get(idLineeMobiliHtmlFields)) {
							valoreCampo += "" + valore.getValore() + ";";

						}
					} else {
						valoreCampo = lp.getCampiEstesi().get(idLineeMobiliHtmlFields).get(0).getValore();
					}

					pst0.setString(3, valoreCampo);
					pst0.execute();

					pst0.close();

				}

			if (db.getAutoCommit() == false) {
				db.commit();
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return id;

	}

	protected void buildRecordStabilimento(ResultSet rs) throws SQLException {

		// this.setDataCessazione(rs.getTimestamp("data_cessazione"));
		try {
			this.setName(rs.getString("ragione_sociale"));
		} catch (PSQLException e) {

		}
		try {
			this.setDenominazione(rs.getString("denominazione"));
		} catch (PSQLException e) {

		}

		try {
			this.setTipoCarattere(rs.getInt("tipo_Carattere"));
		} catch (PSQLException e) {

		}

		try {
			this.setDataInizioAttivita(rs.getTimestamp("data_inizio_attivita"));
		} catch (PSQLException e) {

		}

		setTipologia(Ticket.TIPO_OPU);
		this.setImportato(rs.getBoolean("importato"));
		this.setCodiceInterno(rs.getString("codice_interno"));
		this.setIdStabilimento(rs.getInt("id"));
		// R.M: non so il motivo per cui era settato l'org_id con l'id dello
		// stabilimento...
		// this.setOrgId(this.getIdStabilimento());
		this.setOrgId(rs.getString("riferimento_org_id"));
		this.setEnteredBy(rs.getInt("entered_by"));
		this.setModifiedBy(rs.getInt("modified_by"));
		this.setIdOperatore(rs.getInt("id_operatore"));
		this.setIdAsl(rs.getInt("id_asl"));
		this.setTipoAttivita(rs.getInt("tipo_attivita"));
		this.setFlagDia(rs.getBoolean("flag_dia"));
		this.setCategoriaRischio(rs.getInt("categoria_rischio"));
		this.setDataProssimoControllo(rs.getTimestamp("data_prossimo_controllo"));
		sedeOperativa.setIdIndirizzo(rs.getInt("id_indirizzo"));
		this.stato = rs.getInt("stato");
		numero_registrazione = rs.getString("numero_registrazione");
		attivitaOld = rs.getString("linea_attivita_aziende_agricole_old");

		try {
			this.setLineePregresse(rs.getBoolean("linee_pregresse"));
		} catch (PSQLException e) {

		}

		try {
			this.setAltId(rs.getInt("alt_id"));
		} catch (PSQLException e) {

		}
	}

	public boolean checkAslStabilimentoUtente(UserBean user) {
		return (this.getIdAsl() == user.getSiteId());
	}

	public int updateSedeOperativa(Connection db) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		modifiedBy = enteredBy;
		// if (doCommit = db.getAutoCommit()) {
		// db.setAutoCommit(false);
		// }
		// this.idStabilimento =DatabaseUtils.getNextSeq(db,
		// "opu_stabilimento_id_seq");
		int i = 0;
		sql.append("Update opu_stabilimento set id_indirizzo = ?, id_asl = ? where id= ?");

		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setInt(++i, this.getSedeOperativa().getIdIndirizzo());
		pst.setInt(++i, this.getSedeOperativa().getIdAsl());
		pst.setInt(++i, this.getIdStabilimento());
		int resultCount = pst.executeUpdate();
		pst.close();

		return resultCount;
	}

	public boolean checkEsistenzaStabilimento(Connection db) {
		boolean exist = false;
		String query = "select * " + " from opu_stabilimento o "
				+ " where o.id_indirizzo = ? and id_operatore = ? and tipo_attivita =? ";

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setInt(++i, this.getSedeOperativa().getIdIndirizzo());
			pst.setInt(++i, this.getIdOperatore());
			pst.setInt(++i, this.getTipoAttivita());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return exist;
	}

	public String checkEsistenzaStabilimentoSuap(Connection db,String esitoEsistensaImpresa,boolean importato) {
		boolean exist = false;
		String query = "select o.id as id_stab ,o.* ,r.id as id_linea" +
				" from opu_stabilimento o join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = o.id and r.enabled"
				+ " join opu_indirizzo ind on ind.id=o.id_indirizzo join lookup_toponimi top on top.code = ind.toponimo join opu_operatore op on op.id = o.id_operatore " 
				+ " where o.trashed_date is null and op.partita_iva <> null  and o.stato!=  "+STATO_CESSATO+" ";
		
		
		if(this.getOperatore().getPartitaIva()!=null && !"".equals(this.getOperatore().getPartitaIva()))
		{
			query += " and ( partita_iva = ?) ";
		}
		else
		{
			if(this.getOperatore().getCodFiscale()!=null && !"".equals(this.getOperatore().getCodFiscale()))
			{
				query += " and ( codice_fiscale_impresa = ?) ";
			}
			
		}
		

		if(tipoAttivita==2)
		{
			
			if(this.getListaLineeProduttive().size()>0)
			{
				query +=" and r.id_linea_produttiva = ?" ;
			}
		}
		else
		{
			query +=" and levenshtein( (lower(coalesce(top.description||'','')) ||' ' || lower(coalesce(ind.via,''))),lower(?))<=5  and ind.comune =? and ind.civico ilike ? " ;
		}
		if(idStabilimento>0)
		{
			query +=" and o.id ="+idStabilimento;
		}
		query+= " and tipo_attivita = "+tipoAttivita;
			
		PreparedStatement pst;
		try {
			
			
			
			
			if (importato==false)
			{
				
				/*VERIFICA ESISTENZA NELLA VECCHIA ANAGRAFICA*/
				LookupList lookupToponimo = new LookupList(db,"lookup_toponimi");
				String query1 = "select o.* " +
						" from organization o "
						+ "left join organization_address oa5  on o.org_id=oa5.org_id AND oa5.address_type=5 "
						+ "left join organization_address oa7  on o.org_id=oa7.org_id AND oa7.address_type=7 "
						+ "left join organization_address oa1  on o.org_id=oa1.org_id AND oa1.address_type=1 "
						+ " where ( partita_iva = ?) and o.tipologia not in (0, 3, 97, 2, 9) and o.trashed_Date is null "
						+ " and (?=2 and o.tipo_dest ilike 'Autoveicolo') and "  // Condizione aggiunt aper risolvere il ticket TT 012191
						+ " ((levenshtein(upper(replace(coalesce(coalesce(oa5.addrline1,oa7.addrline1),oa1.addrline1),' ','')),?)<=6 and levenshtein(upper(replace(coalesce(coalesce(oa5.city,oa7.city),oa1.city),' ','')),?)<= 3 ) "
						+ "OR (levenshtein(upper(replace(coalesce(coalesce(oa5.addrline1,oa7.addrline1),oa1.addrline1),' ','')),?)<=6 and levenshtein(upper(replace(coalesce(coalesce(oa5.city,oa7.city),oa1.city),' ','')),?)<= 3) ) "
						+ " and case when o.tipologia=1 then o.tipo_dest ilike ? else 1=1 end ";
				 
				 
				 
				 	int i = 0;
					pst = db.prepareStatement(query1);
					
					if(this.getOperatore().getPartitaIva()!=null && !"".equals(this.getOperatore().getPartitaIva()))
					{
						pst.setString(++i, this.getOperatore().getPartitaIva());
					}
					else
					{
						pst.setString(++i, this.getOperatore().getCodFiscale());
					}
					
					pst.setInt(++i, tipoAttivita);

				
					

					if(this.getTipoAttivita()==1)
					{
						pst.setString(++i, (lookupToponimo.getSelectedValue(this.getSedeOperativa().getToponimo())+ ""+ this.getSedeOperativa().getVia()).replaceAll(" ", "").toUpperCase());
						pst.setString(++i, ComuniAnagrafica.getDescrizione(db, this.getSedeOperativa().getComune()));
						
						pst.setString(++i, (this.getSedeOperativa().getVia()).replaceAll(" ", "").toUpperCase());
						pst.setString(++i, ComuniAnagrafica.getIstat(db, this.getSedeOperativa().getComune()));
						pst.setString(++i, "Es. Commerciale");

					}
					else
					{
						//sedeLegaleImpresa
						pst.setString(++i, (lookupToponimo.getSelectedValue(this.getOperatore().getSedeLegale().getToponimo())+ ""+ this.getOperatore().getSedeLegale().getVia()).replaceAll(" ", "").toUpperCase());
						pst.setString(++i, ComuniAnagrafica.getDescrizione(db, this.getOperatore().getSedeLegale().getComune()));
						
						pst.setString(++i, (this.getOperatore().getSedeLegale().getVia()).replaceAll(" ", "").toUpperCase());
						pst.setString(++i, ComuniAnagrafica.getIstat(db, this.getOperatore().getSedeLegale().getComune()));
						pst.setString(++i, "Autoveicolo");

					}

					
					System.out.println("QUERY ESISTENZA VECCHIA ANAGRAFICA: " + pst.toString());
					ResultSet rs = pst.executeQuery();
					if (rs.next()) {
						return "organization";
					}
				
			}
			
			
			/*VERIFICA ESISTENZA NELLA NUOVA ANAGRAFICA*/
			int i = 0;
			pst = db.prepareStatement(query);

			if(this.getOperatore().getPartitaIva()!=null && !"".equals(this.getOperatore().getPartitaIva()))
			{
				pst.setString(++i, this.getOperatore().getPartitaIva());
			}
			else
			{
				if(this.getOperatore().getCodFiscale()!=null && !"".equals(this.getOperatore().getCodFiscale()))
				{
					pst.setString(++i, this.getOperatore().getCodFiscale());
				}
				
			}
			
			
			if(tipoAttivita==2)
			{
				if(this.getListaLineeProduttive().size()>0)
				{
					
					if(this.getListaLineeProduttive().size()>0)
					{
						pst.setInt(++i, ((LineaProduttiva )this.getListaLineeProduttive().get(0)).getIdRelazioneAttivita());
					}
				}
				
			}
			else
			{
				
				LookupList lp = new LookupList(db,"lookup_toponimi");
				pst.setString(++i, lp.getSelectedValue(this.getSedeOperativa().getToponimo()) +" "+this.getSedeOperativa().getVia());
				pst.setInt(++i, this.getSedeOperativa().getComune());
				pst.setString(++i, this.getSedeOperativa().getCivico());
				
			}
			
			
			
			 ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				this.setNumero_registrazione(rs.getString("numero_registrazione"));
				this.stato=rs.getInt("stato");
				this.numProtocollo=rs.getString("num_protocollo");
				idRelStabLpMobile = rs.getInt("id_linea");
				return "4"; 
			}
			
			else
			{
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return esitoEsistensaImpresa;
	}

	public Integer getIdEsistenteStabilimentoSuap(Connection db, boolean importato) {
		boolean exist = false;
		String query = "select o.id as id_stab ,o.* ,r.id as id_linea"
				+ " from opu_stabilimento o join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = o.id and r.enabled"
				+ " join opu_indirizzo ind on ind.id=o.id_indirizzo join lookup_toponimi top on top.code = ind.toponimo join opu_operatore op on op.id = o.id_operatore "
				+ " where o.trashed_date is null  and o.stato!=  " + STATO_CESSATO + " ";

		if (this.getOperatore().getPartitaIva() != null && !"".equals(this.getOperatore().getPartitaIva())) {
			query += " and ( partita_iva = ?) ";
		} else {
			if (this.getOperatore().getCodFiscale() != null && !"".equals(this.getOperatore().getCodFiscale())) {
				query += " and ( codice_fiscale_impresa = ?) ";
			}

		}

		if (tipoAttivita == 2) {

			if (this.getListaLineeProduttive().size() > 0) {
				query += " and r.id_linea_produttiva = ?";
			}
		} else {
			query += " and levenshtein( (lower(coalesce(top.description||'','')) ||' ' || lower(coalesce(ind.via,''))),lower(?))<=5  and ind.comune =? and ind.civico ilike ? ";
		}
		if (idStabilimento > 0) {
			query += " and o.id =" + idStabilimento;
		}
		query += " and tipo_attivita = " + tipoAttivita;

		PreparedStatement pst;
		try {

			if (importato == false) {
				return null;
			}

			/* VERIFICA ESISTENZA NELLA NUOVA ANAGRAFICA */
			int i = 0;
			pst = db.prepareStatement(query);

			if (this.getOperatore().getPartitaIva() != null && !"".equals(this.getOperatore().getPartitaIva())) {
				pst.setString(++i, this.getOperatore().getPartitaIva());
			} else {
				if (this.getOperatore().getCodFiscale() != null && !"".equals(this.getOperatore().getCodFiscale())) {
					pst.setString(++i, this.getOperatore().getCodFiscale());
				}

			}

			if (tipoAttivita == 2) {
				if (this.getListaLineeProduttive().size() > 0) {

					if (this.getListaLineeProduttive().size() > 0) {
						pst.setInt(++i,
								((LineaProduttiva) this.getListaLineeProduttive().get(0)).getIdRelazioneAttivita());
					}
				}

			} else {

				LookupList lp = new LookupList(db, "lookup_toponimi");
				pst.setString(++i, lp.getSelectedValue(this.getSedeOperativa().getToponimo()) + " "
						+ this.getSedeOperativa().getVia());
				pst.setInt(++i, this.getSedeOperativa().getComune());
				pst.setString(++i, this.getSedeOperativa().getCivico());

			}

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				this.setNumero_registrazione(rs.getString("numero_registrazione"));
				this.stato = rs.getInt("stato");
				this.numProtocollo = rs.getString("num_protocollo");
				idRelStabLpMobile = rs.getInt("id_linea");
				return rs.getInt("id_stab");
			}

			else {

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void cambiainOsa(Connection db, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement(
				"update opu_stabilimento set flag_dia = false , data_fine_dia=current_timestamp ,modified_by = ? where id = ?");
		pst.setInt(1, userId);
		pst.setInt(2, idStabilimento);
		pst.execute();

	}

	public int updateResponsabile(Connection db, int newResponsabile) throws SQLException {
		StringBuffer sql = new StringBuffer();
		modifiedBy = enteredBy;
		int i = 0;
		sql.append("Update opu_stabilimento set id_soggetto_fisico = ? where id= ?");

		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setInt(++i, newResponsabile);
		pst.setInt(++i, this.getIdStabilimento());
		int resultCount = pst.executeUpdate();
		pst.close();

		return resultCount;
	}

	public boolean esegui_voltura(Connection db) {

		PreparedStatement pst = null;
		int i = 0;
		try {
			pst = db.prepareStatement("update opu_stabilimento set id_soggetto_fisico = ? where id = ?");

			pst.setInt(++i, this.getRappLegale().getIdSoggetto());
			pst.setInt(++i, this.getIdStabilimento());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public String getIdVoltura(Connection db, int org_id) {

		String idVoltura = null;
		try {

			String sql = "select max(ticketid) as ticketid from ticket where id_stabilimento=? and tipologia = 4 and trashed_Date is null";
			java.sql.PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idStabilimento);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				idVoltura = rs.getString("ticketid");

			}
			pst.close();
			rs.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return idVoltura;

	}

	protected String getCodAttivita(Connection db) throws SQLException {
		// Result Set
		ResultSet rs = null;

		// Codice attivita'
		String codiceAttivita = null;
		if (this.getOrgId() == -1) {
			throw new SQLException("Organization ID not specified");
		}

		StringBuffer sql = new StringBuffer();

		// Creazione della query (cf_correntista rappresenta il cod. attivita')
		sql.append("select la.codice from opu_relazione_stabilimento_linee_produttive v1  "
				+ "join opu_relazione_attivita_produttive_aggregazioni lp on lp.id =v1.id_linea_produttiva "
				+ "join  opu_lookup_attivita_linee_produttive_aggregazioni la on la.code = lp.id_attivita_aggregazione "
				+ "where primario = true and id_stabilimento = " + this.getIdStabilimento() + " ");

		Statement pst = db.createStatement();
		rs = pst.executeQuery(sql.toString());
		if (rs.next()) {
			codiceAttivita = rs.getString(1);
		}
		pst.close();

		return codiceAttivita;
	}

	protected String getCodAttivita(ActionContext ctx, int idLineaProduttiva, Connection db) throws SQLException {
		// Result Set
		ResultSet rs = null;

		// Codice attivita'
		String codiceAttivita = null;

		StringBuffer sql = new StringBuffer();

		this.getPrefissoAction(ctx.getAction().getActionName());

		switch (this.idNorma) {
		case 7: {
			codiceAttivita = "01.00.00";
			break;
		}
		default: {
			// Creazione della query (cf_correntista rappresenta il cod.
			// attivita')
			sql.append(
					"select l.codice from opu_relazione_stabilimento_linee_produttive v1 join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita=v1.id_linea_produttiva  "
							+ "where  v1.enabled and l.id_nuova_linea_attivita=" + idLineaProduttiva
							+ "  and id_stabilimento = " + this.getIdStabilimento() + " ");

			Statement pst = db.createStatement();
			rs = pst.executeQuery(sql.toString());
			if (rs.next()) {
				codiceAttivita = rs.getString(1);
			}
			pst.close();
			break;
		}
		}

		return codiceAttivita;
	}

	protected int getProgressive(Connection db, String istatcomune, String siglaProvincia) throws SQLException {
		ResultSet rs = null;
		int progressivoAsl = 0;

		String sql = "select * from dbi_opu_get_progressivo_per_comune(?,?)";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, istatcomune);

		pst.setString(2, siglaProvincia);
		rs = pst.executeQuery();

		if (rs.next()) {
			// Progressivo per quell'ASL
			progressivoAsl = rs.getInt(1);
		}

		pst.close();

		// Il codice del progressivo ASL viene ritornato
		return progressivoAsl;

	}

	public int getIdNorma() {
		if (this.getListaLineeProduttive().size() > 0)
			return ((LineaProduttiva) this.getListaLineeProduttive().get(0)).getIdNorma();
		else
			return -1;
	}

	int idNorma = -1;
	private String asl;
	public String getAsl() {
		return asl;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}

	public String getTipoAttivitaDesc() {
		return tipoAttivitaDesc;
	}

	public void setTipoAttivitaDesc(String tipoAttivitaDesc) {
		this.tipoAttivitaDesc = tipoAttivitaDesc;
	}

	private String tipoAttivitaDesc;

	public String getContainer() {

		String container = "";
		switch (idNorma) {
		case 1: {
			if (this.flagDia == false && ((this.getListaLineeProduttive().size() > 0
					&& ((LineaProduttiva) this.getListaLineeProduttive().get(0)).getInfoStab().getTipoAttivita() != 3))
					|| this.getListaDistributori().size() > 0)
				container = "stabilimenti852opu";
			else if (flagDia == true)
				container = "requestoropu";
			else
				container = "distributoristabilimenti852opu";
			break;
		}
		case -1: {
			//container = "suap";
			/* da decommentare per nuova gestione anagrafica e commentare riga sopra */
			if(this.getListaLineeProduttive().size() > 0) {
				boolean flagscia = false;
				for(int i = 0; i < this.getListaLineeProduttive().size(); i++){
					if( !((LineaProduttiva) this.getListaLineeProduttive().get(i)).getFlags().isNoScia() )
					{
						flagscia = true;
					}
				}
				if(flagscia)
					container = "gestioneanagrafica";
				else
					container = "gestioneanagraficanoscia";
			}
			
			break;
		}
		case 7: {

			container = "aziendeagricoleopu";

			break;
		}
		default: {
			container = "opu";
			break;
		}
		}
		return container;
	}

	public String getAction() {
		return action;
	}

	public String getPageInclude() {
		return pageInclude;
	}

	public String getPrefissoAction(String actionName) {

		// String uri = request.getRequestURI();
		// String action = uri.substring(0, uri.lastIndexOf('/'));

		if (actionName.contains("AziendeAgricoleOpuStab") || actionName.contains("AziendeAgricoleOpu"))
			idNorma = LineaProduttiva.NORMA_AZIENDE_AGRICOLE;
		else if (actionName.contains("Stabilimenti852") || actionName.contains("Distributori852"))
			idNorma = LineaProduttiva.NORMA_STABILIMENTI_852;
		else if (actionName.contains("Suap") || actionName.contains("Suap"))
			idNorma = -1;

		switch (idNorma) {
		case 1: {
			if (((this.flagDia == false && ((this.getListaLineeProduttive().size() > 0
					&& ((LineaProduttiva) this.getListaLineeProduttive().get(0)).getInfoStab().getTipoAttivita() != 3))
					|| this.getListaLineeProduttive().size() == 0)) || this.getListaDistributori().size() > 0)
				action = "Stabilimenti852";
			else if (flagDia == true)
				action = "Requestor";
			else
				action = "Distributori852";

			break;
		}
		case 7:

		{
			action = "AziendeAgricoleOpuStab";
			break;
		}

		default: {
			action = "OpuStab";
			break;
		}
		}
		return action;
	}

	public String getPrefissoPageCampiEstesi(String actionName) {

		int idNorma = -1;
		if (actionName.contains("AziendeAgricoleOpu"))
			idNorma = LineaProduttiva.NORMA_AZIENDE_AGRICOLE;
		else if (actionName.contains("Stabilimenti852") || actionName.contains("Distributori852"))
			idNorma = LineaProduttiva.NORMA_STABILIMENTI_852;

		String page = "";
		switch (idNorma) {
		case 1: {

			pageInclude = "opu_informazioni_852";

			break;
		}
		default: {
			pageInclude = "";
			break;
		}
		}
		return page;
	}

	protected String getAslProgressive(Connection db, String istatComune, String siglaProvincia) throws SQLException {
		// ResultSet
		ResultSet rs = null;

		// NOME ASL
		String asl = null;

		/*
		 * Viene chiamato il metodo per ottenere il progressivo della specifica
		 * ASL
		 */
		Integer progressive = this.getProgressive(db, istatComune, siglaProvincia);

		String result = null;

		// Il progressivo deve essere a 6 caratteri
		result = org.aspcfs.utils.StringUtils.zeroPad(progressive, 6);

		// Il progressivo per quella ASL viene ritornato
		// return progressive.toString();
		return result;
	}

	protected String getFlagDia(Connection db, int servizioCompetente) throws SQLException {

		ResultSet rs = null;
		String flag = "";
		if (this.getOrgId() == -1) {
			throw new SQLException("Organization ID not specified");
		}

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT description " + "FROM lookup_requestor_stage " + "WHERE code = ? ");

		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setInt(1, servizioCompetente);
		rs = pst.executeQuery();

		if (rs.next()) {
			flag = rs.getString(1);
		}

		pst.close();
		if (flag.equals(""))
			return "";
		return flag.substring(0, 1);
	}

	public ArrayList<Distrubutore> getDistributori(Connection db, int org_id) {

		ArrayList<Distrubutore> lista = new ArrayList<Distrubutore>();
		try {

			LookupList tipoDitributore = new LookupList(db, "lookup_tipo_distributore");

			String sql = "select * from distributori_automatici,lookup_tipo_distributore where code=alimenti_distribuiti and id_stabilimento="
					+ org_id;
			java.sql.PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				String matricola = rs.getString("matricola");
				String comune = rs.getString("comune");
				String provincia = rs.getString("provincia");
				String latitudine = rs.getString("latitudine");
				String longitudine = rs.getString("longitudine");
				String cap = rs.getString("cap");
				String note = rs.getString("note");
				String indirizzo = rs.getString("indirizzo");
				Date data = rs.getDate("data");
				String description = rs.getString("description");
				int alimentiDstribuiti = rs.getInt("alimenti_distribuiti");
				String ubicazione = rs.getString("ubicazione");

				Distrubutore dist = new Distrubutore(matricola, comune, indirizzo, cap, provincia, latitudine,
						longitudine, note, data, alimentiDstribuiti, ubicazione);
				dist.setDescrizioneTipoAlimenti(description);
				dist.setOrg_id(org_id);
				lista.add(dist);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;

	}

	public List<Operatore> getListaOperatori() {
		return listaOperatori;
	}

	public void setListaOperatori(List<Operatore> listaOperatori) {
		this.listaOperatori = listaOperatori;
	}

	// public HashMap<String, Object> getHashmapStabilimento()
	// {
	//
	//
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// try
	// {
	// Field[] campi = this.getClass().getDeclaredFields();
	// Method[] metodi = this.getClass().getMethods();
	// for (int i = 0 ; i <campi.length; i++)
	// {
	// String nomeCampo = campi[i].getName();
	//
	//
	// if ( ! nomeCampo.equalsIgnoreCase("rappLegale") && !
	// nomeCampo.equalsIgnoreCase("listaOperatori") && !
	// nomeCampo.equalsIgnoreCase("listaLineeProduttive") && !
	// nomeCampo.equalsIgnoreCase("operatore") && !
	// nomeCampo.equalsIgnoreCase("listaDistributori")
	// && ! nomeCampo.equalsIgnoreCase("sedeOperativa") )
	// {
	// for (int j=0; j<metodi.length; j++ )
	// {
	//
	// if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
	// {
	//
	// map.put(nomeCampo,new String ( ""+metodi[j].invoke(this)));
	//
	//
	//
	// }
	// }
	//
	// }
	//
	// }
	//
	//
	// if (listaOperatori != null)
	// {
	// JSONArray array = new JSONArray();
	// for(Operatore tmp : listaOperatori)
	// {
	// JSONObject operatoreJson = new JSONObject(tmp.getHashmapOperatore());
	// array.put(operatoreJson);
	//
	// }
	// map.put("operatori",array );
	// }
	// else
	// {
	// if (operatore!=null)
	// {
	//
	// JSONObject operatoreJson = new
	// JSONObject(operatore.getHashmapOperatore());
	//
	// map.put("operatore",operatoreJson );
	// }
	// }
	//
	// if(listaLineeProduttive!=null)
	// {
	//
	// //map.put("listalineeproduttive",
	// listaLineeProduttive.getHashmapListaLineeProduttive());
	//
	// }
	// if(sedeOperativa!=null)
	// {
	// JSONObject sedeop = new JSONObject(sedeOperativa.getHashmapIndirizzo());
	//
	// map.put("sedeoperativa", sedeop);
	//
	// }
	// }catch(Exception e)
	// {
	//
	// }
	//
	//
	//
	//
	// return map ;
	//
	// }

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void updateValidazione(Connection db, boolean esitoValidazione) throws SQLException {

		String update = "update opu_stabilimento set stato = ? ";
		update += " , modified  = now(), modified_by = ? ";
		update += " where id = ? ";

		PreparedStatement pst = db.prepareStatement(update);

		if (esitoValidazione) {
			pst.setInt(1, STATO_AUTORIZZATO);
			pst.setInt(2, this.getModifiedBy());
			pst.setInt(3, this.idStabilimento);
			pst.executeUpdate();
		}

	}

	public boolean generaNumeroRegistrazione(ActionContext context, Connection db)
			throws SQLException, IndirizzoNotFoundException {

		Operatore op = new Operatore();
		op.queryRecordOperatore(db, this.getIdOperatore());

		ComuniAnagrafica com = null;
		int idComune = -1;

		String numRegStab = "";
		String sql1 = "select * from public_functions.opu_genera_numero_registrazione(?)";
		PreparedStatement pst1 = db.prepareStatement(sql1);
		pst1.setInt(1, idStabilimento);
		ResultSet rs1 = pst1.executeQuery();
		if (rs1.next()) {
			numero_registrazione = rs1.getString(1);

			numRegStab = rs1.getString(1);
		}

		boolean codEs = false;

		// Buffer di creazione query
		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		Timestamp dataCodice = new Timestamp(System.currentTimeMillis());

		sql = new StringBuffer();
		sql.append("UPDATE opu_stabilimento SET numero_registrazione = ? ,modified_by=?,data_generazione_numero=? "
				+ "WHERE id= ? ;");

		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setString(1, numRegStab);
		pst.setInt(2, this.modifiedBy);
		pst.setTimestamp(3, dataCodice);
		pst.setInt(4, idStabilimento);
		pst.executeUpdate();

		Iterator it = listaLineeProduttive.iterator();
		while (it.hasNext()) {
			LineaProduttiva lp = (LineaProduttiva) it.next();

			String numeroRegLinea = numRegStab;

			int progressivo = lp.getProgressivoLineaAttivita(db, numRegStab);
			String paddingProg = org.aspcfs.utils.StringUtils.zeroPad(progressivo, 3);

			numeroRegLinea += paddingProg;
			sql = new StringBuffer();
			sql.append(
					"update opu_relazione_stabilimento_linee_produttive set numero_registrazione = ? where id_stabilimento = ? and id = ? and enabled and numero_registrazione is null");
			pst = db.prepareStatement(sql.toString());

			pst.setString(1, numeroRegLinea);
			pst.setInt(2, idStabilimento);
			pst.setInt(3, lp.getId());

			try {
				pst.executeUpdate();

			} catch (SQLException e) {

			}

		}

		return codEs;
	}

	public boolean generaApprovalNumber(String approval, Connection db) throws SQLException {
		// StringBuffer sql = new StringBuffer();
		// ResultSet rs=null;
		// sql = new StringBuffer();
		// sql.append(
		// "UPDATE opu_relazione_stabilimento_linee_produttive SET
		// codice_nazionale = ? , data_generazione_numero=now() "+
		// " from ml8_linee_attivita_nuove l "
		// + " WHERE l.id_nuova_linea_attivita=id_linea_produttiva and
		// l.id_tipo_linea_produttiva="+Stabilimento.TIPO_SCIA_RICONOSCIUTI + "
		// and id_stabilimento= ? ;" );
		//
		// PreparedStatement pst = db.prepareStatement(sql.toString());
		// pst.setString(1, approval);
		// pst.setInt( 2, idStabilimento );
		// pst.executeUpdate();
		// return true;

		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		sql = new StringBuffer();
		sql.append(
				"UPDATE opu_relazione_stabilimento_linee_produttive SET codice_nazionale = ? , data_generazione_codice_nazionale=now(), modified=now(), modifiedby = ? "
						+ " WHERE id_stabilimento= ? ;");

		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setString(1, approval);
		pst.setInt(2, this.getModifiedBy());
		pst.setInt(3, idStabilimento);
		pst.executeUpdate();
		return true;

	}

	public boolean controllaApproval(String approval, Connection db) throws SQLException {

		if (approval.replaceAll(" ", "").equals(""))
			return false;

		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		sql = new StringBuffer();
		sql.append("select * from opu_relazione_stabilimento_linee_produttive where codice_nazionale = ? and enabled");

		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setString(1, approval);

		rs = pst.executeQuery();

		if (rs.next())
			return false;
		return true;

	}

	public boolean isLineePregresse() {
		return lineePregresse;
	}

	public void setLineePregresse(boolean lineePregresse) {
		this.lineePregresse = lineePregresse;
	}


	public void gestisciLineeProduttivePregresseVersioneNuova(Connection db,int userId) throws SQLException {

		LineaProduttivaList listaLineeProduttive = this.getListaLineeProduttive();
		Iterator<LineaProduttiva> itLp = listaLineeProduttive.iterator();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			while (itLp.hasNext()) {

				LineaProduttiva temp = itLp.next();
				String nota = " . linea mappata da  linea pregressa (fittizia) con id ->" + temp.getIdVecchiaLinea()
						+ " proveniente da linea vecchia (rappresent. in lista_linee_attivita_vecchia_anagrafica) con id "
						+ (temp.getMessage() != null ? temp.getMessage()
								: "-non è stato possibile ottenere rappresentazione in lista_linee_attivita_vecchia_anagrafica-");

				pst = db.prepareStatement(
						"update opu_relazione_stabilimento_linee_produttive set modified = current_timestamp, id_linea_produttiva_old = id_linea_produttiva, id_linea_produttiva = ?,id_linea_fittizia_pre_aggiornamento = ? , note = concat(note,?) , data_inizio = ?, data_fine = ?, modifiedby = ?   where id_stabilimento = ? and id_linea_produttiva = ?");

				pst.setInt(1, temp.getIdRelazioneAttivita()); // la linea nuova
				pst.setInt(2, temp.getIdVecchiaLinea()); // quella fittizia non
															// realmente
															// esistente in ml
				pst.setString(3, nota);
				pst.setTimestamp(4, temp.getDataInizio());
				pst.setTimestamp(5, temp.getDataFine());
				pst.setInt(6, userId);
				pst.setInt(7, idStabilimento);
				pst.setInt(8, temp.getIdVecchiaLinea());
				int aggiornate = pst.executeUpdate();
				pst.close();
				
				try {
					pst = db.prepareStatement("update opu_stabilimento set tipo_attivita = (select case when f.fisso then 1 when f.mobile then 2 else -1 end from master_list_flag_linee_attivita f join ml8_linee_attivita_nuove_materializzata ml8 on f.codice_univoco = ml8.codice_attivita where ml8.id_nuova_linea_attivita ="+ temp.getIdRelazioneAttivita()+ ") where tipo_attivita is null and id = "+idStabilimento);
					pst.executeUpdate();
				} catch (Exception e) {}

				if (temp.getCampiEstesi() != null && temp.getCampiEstesi().size() > 0 && aggiornate > 0) {

					// gestisco eventuali campi estesi
					// quindi trovo id_rel_stab_linea (quello che ho appena
					// aggiornato)
					pst = db.prepareStatement(
							"select * from opu_relazione_stabilimento_linee_produttive where id_linea_produttiva = ? and id_stabilimento = ? and enabled = true");
					pst.setInt(1, temp.getIdRelazioneAttivita());
					pst.setInt(2, idStabilimento);
					rs = pst.executeQuery();

					rs.next();

					int id_rels_stab_lineaPerCampiEstesi = rs.getInt("id");

					for (Integer idLineeMobiliHtmlFields : temp.getCampiEstesi().keySet()) {
						// PreparedStatement pst0 = db.prepareStatement("insert
						// into
						// linee_mobili_fields_value(id_rel_stab_linea,id_linee_mobili_html_fields,valore_campo,id_utente_inserimento)
						// values(?,?,?,current_timestamp,?)");
						// select * from
						// public.dbi_insert_campi_estesi(id_rel_stab_linea,
						// id_linee_mobili_html_fields, valore_campo, indice,
						// id_utente_inserimento, id_opu_rel_stab_linea,
						// riferimento_org_id)
						PreparedStatement pst0 = db.prepareStatement(
								"select * from public.dbi_insert_campi_estesi(?, ?, ?, -1, ?, -1, -1)");

						pst0.setInt(1, id_rels_stab_lineaPerCampiEstesi);
						pst0.setInt(2, idLineeMobiliHtmlFields);
						pst0.setInt(4, this.enteredBy);

						String valoreCampo = "";

						if (temp.getCampiEstesi().get(idLineeMobiliHtmlFields).size() > 1) {
							for (LineaProduttivaCampoEsteso valore : temp.getCampiEstesi()
									.get(idLineeMobiliHtmlFields)) {
								valoreCampo += "" + valore.getValore() + ";";

							}
						} else {
							valoreCampo = temp.getCampiEstesi().get(idLineeMobiliHtmlFields).get(0).getValore();
						}

						pst0.setString(3, valoreCampo);
						pst0.execute();
						pst0.close();

					}

					PreparedStatement pst2 = db.prepareStatement("select * from aggiorna_campi_estesi_opu_dopo_mapping(?)"); 
					pst2.setInt(1, id_rels_stab_lineaPerCampiEstesi);
					pst2.execute();
				}

			}

			pst.close();
			pst = db.prepareStatement("update opu_stabilimento set modified=current_timestamp, modified_by="
					+ this.getModifiedBy() + ", linee_pregresse=false where id =" + idStabilimento);
			pst.executeUpdate();

			RicercheAnagraficheTab.inserOpu(db, idStabilimento);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (Exception ex) {
			}
			try {
				rs.close();
			} catch (Exception ex) {
			}
		}

	}

	public void importaTarga(Connection db, String targa, String tipoAutomezzo, int idRelStabLp) {

		try {

			int indice = 1;
			String maxIndice = "select case when max(indice) is null then 1 else max(indice)+1 end  from linee_mobili_fields_value where id_rel_stab_linea = ? ";
			PreparedStatement pstInd = db.prepareStatement(maxIndice);
			pstInd.setInt(1, idRelStabLp);
			ResultSet rs = pstInd.executeQuery();
			if (rs.next()) {
				indice = rs.getInt(1);
			}
			// RECUPERO ID LINEA
			String rec = "(select id from linee_mobili_html_fields where id_linea in (select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive where id=?  ) and nome_campo ilike ?)";
			PreparedStatement pst0 = db.prepareStatement(rec);
			pst0.setInt(1, idRelStabLp);
			pst0.setString(2, "targa");// field targa
			ResultSet rs0 = pst0.executeQuery();
			if (rs0.next()) {

				int lda = rs0.getInt("id");

				// String insert = "insert into linee_mobili_fields_value
				// (id_rel_stab_linea,id_linee_mobili_html_fields,valore_campo,indice,id_utente_inserimento,riferimento_org_id)"+
				// "values (?,?,?,?,true,?,?)" ;
				// select * from
				// public.dbi_insert_campi_estesi(id_rel_stab_linea,
				// id_linee_mobili_html_fields, valore_campo, indice,
				// id_utente_inserimento, id_opu_rel_stab_linea,
				// riferimento_org_id)

				String insert = "select * from public.dbi_insert_campi_estesi(?, ?, ?, ?, ?, -1, ?)";

				PreparedStatement pst = db.prepareStatement(insert);
				pst.setInt(1, idRelStabLp);
				pst.setInt(2, lda);
				pst.setString(3, targa);
				pst.setInt(4, indice);
				pst.setInt(5, enteredBy);
				pst.setInt(6, this.orgId);
				pst.execute();

				rec = "(select id from linee_mobili_html_fields where id_linea in (select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive where id=?  ) and nome_campo ilike ?)";
				pst0 = db.prepareStatement(rec);
				pst0.setInt(1, idRelStabLp);
				pst0.setString(2, "tipo_veicolo");
				rs0 = pst0.executeQuery();
				if (rs0.next()) {
					lda = rs0.getInt("id");

					pst.setInt(1, idRelStabLp);
					pst.setInt(2, lda);
					pst.setString(3, tipoAutomezzo);
					pst.setInt(4, indice);
					pst.setInt(5, enteredBy);
					pst.setInt(6, this.orgId);
					pst.execute();

					rec = "(select id from linee_mobili_html_fields where id_linea in (select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive where id=?  ) and nome_campo ilike ?)";
					pst0 = db.prepareStatement(rec);
					pst0.setInt(1, idRelStabLp);
					pst0.setString(2, "carta_circolazione");
					rs0 = pst0.executeQuery();
					if (rs0.next()) {
						lda = rs0.getInt("id");

						pst.setInt(1, idRelStabLp);
						pst.setInt(2, lda);
						pst.setString(3, "");
						pst.setInt(4, indice);
						pst.setInt(5, enteredBy);
						pst.setInt(6, this.orgId);
						pst.execute();

						rec = "(select id from linee_mobili_html_fields where id_linea in (select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive where id=?  ) and nome_campo ilike ?)";
						pst0 = db.prepareStatement(rec);
						pst0.setInt(1, idRelStabLp);
						pst0.setString(2, "data_acquisto");
						rs0 = pst0.executeQuery();
						if (rs0.next()) {
							lda = rs0.getInt("id");

							pst.setInt(1, idRelStabLp);
							pst.setInt(2, lda);
							pst.setString(3, "");
							pst.setInt(4, indice);
							pst.setInt(5, enteredBy);
							pst.setInt(6, this.orgId);
							pst.execute();

							rec = "(select id from linee_mobili_html_fields where id_linea in (select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive where id=?  ) and nome_campo ilike ?)";
							pst0 = db.prepareStatement(rec);
							pst0.setInt(1, idRelStabLp);
							pst0.setString(2, "data_dismissione");
							rs0 = pst0.executeQuery();
							if (rs0.next()) {
								lda = rs0.getInt("id");

								pst.setInt(1, idRelStabLp);
								pst.setInt(2, lda);
								pst.setString(3, "data_dismissione");// fie
								pst.setInt(4, indice);
								pst.setInt(5, enteredBy);
								pst.setInt(6, this.orgId);
								pst.execute();
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void importaControlliUfficiali(Connection db, int vecchia, int nuova, boolean primaria, String codice, int userId) {

		try {

			// PASSO 1: AGGIORNARE ID STABILIMENTO SU TUTTI I TICKET
			String sqlCu = "update ticket set note_internal_use_only = concat(note_internal_use_only, current_timestamp, '. Import controllo da ', id_stabilimento,  ' a "+this.getIdStabilimento()+" da utente "+userId+"' ), id_stabilimento_old = id_stabilimento, id_stabilimento = ?, modified = current_timestamp, modifiedby = ?, trashed_date= null where org_id = ? and (trashed_date is null or trashed_date = '1970-01-01') ";
			PreparedStatement pstCu = db.prepareStatement(sqlCu.toString());
			pstCu.setInt(1, this.getIdStabilimento());
			pstCu.setInt(2, userId);
			pstCu.setInt(3, this.getOrgId());
			pstCu.executeUpdate();

			// PASSO 2: RECUPERARSI TUTTI I CONTROLLI ASSOCIATI ALLA VECCHIA
			// LINEA DI ATTIVITA'
			String sqlVecchi = " SELECT l.id_controllo_ufficiale, o.tipologia AS tipo_operatore " + 
							   " FROM linee_attivita_controlli_ufficiali l " + 
							   " LEFT JOIN ticket t ON t.ticketid = l.id_controllo_ufficiale AND t.tipologia = 3 " + 
							   " LEFT JOIN organization o ON o.org_id = t.org_id " + 
							   " WHERE (t.trashed_date IS NULL or  t.trashed_date = '1970-01-01') AND " + 
							   "       l.trashed_date IS NULL and " + 
							   "       l.id_linea_attivita = ? and " + 
							   "       o.tipologia = ? and " + 
							   "       l.trashed_date is null ;";
			PreparedStatement pstVecchi = db.prepareStatement(sqlVecchi.toString());

			pstVecchi.setInt(1, vecchia);
			pstVecchi.setInt(2, this.getTipoVecchiaOrg());

			ResultSet rsVecchi = pstVecchi.executeQuery();
			boolean trovataLinea = false;

			// PASSO 3: INSERIRE NELLA TABELLA OPU IL VECCHIO ID CONTROLLO CON
			// LA NUOVA LINEA ATTIVITA
			while (rsVecchi.next()) {
				trovataLinea = true;
				int idCU = rsVecchi.getInt("id_controllo_ufficiale");
				int tipoOperatore = rsVecchi.getInt("tipo_operatore");

				String sqlNuovi = "select * from public_functions.insert_linee_attivita_controlli_ufficiali(?,?,?,?,?,?,?)";
				PreparedStatement pstNuovi = db.prepareStatement(sqlNuovi.toString());
				pstNuovi.setInt(1, idCU);
				pstNuovi.setInt(2, nuova);
				pstNuovi.setString(3, "opu_relazione_stabilimento_linee_produttive");
				pstNuovi.setString(4, "Controllo importato a seguito di trasferimento in nuova anagrafica");
				pstNuovi.setInt(5, vecchia);
				pstNuovi.setInt(6, this.getTipoVecchiaOrg());
				pstNuovi.setInt(7, userId);

				pstNuovi.execute();
			}

			// PASSO 4: INSERIRE NELLA TABELLA OPU IL VECCHIO ID CONTROLLO CON
			// LA NUOVA LINEA ATTIVITA PRIMARIA NEL CASO IN CUI NON CI SIANO
			// LINEE ASSOCIATE

			String sqlVecchiSenzaLinea = "select t.ticketid as id_controllo_ufficiale, o.tipologia as tipo_operatore from ticket t join organization o on coalesce(t.org_id,t.org_id_old) = o.org_id and (t.trashed_date is null or t.trashed_date = '1970-01-01') and t.tipologia= 3 left join linee_attivita_controlli_ufficiali_view l on l.id_controllo_ufficiale = t.ticketid  where l.id_controllo_ufficiale is null and coalesce(t.org_id,t.org_id_old) = ? and t.provvedimenti_prescrittivi <> 5  and assigned_date >'2016-01-01'";
			PreparedStatement pstVecchiSenzaLinea = db.prepareStatement(sqlVecchiSenzaLinea.toString());
			pstVecchiSenzaLinea.setInt(1, this.getOrgId());
			ResultSet rsVecchiSenzaLinea = pstVecchiSenzaLinea.executeQuery();

			// PASSO 5: INSERIRE NELLA TABELLA OPU IL VECCHIO ID CONTROLLO CON
			// LA NUOVA LINEA ATTIVITA E LA VECCHIA A -1
			while (rsVecchiSenzaLinea.next()) {
				int idCU = rsVecchiSenzaLinea.getInt("id_controllo_ufficiale");
				int tipoOperatore = rsVecchiSenzaLinea.getInt("tipo_operatore");

				String sqlNuoviSenzaLinea = "select * from public_functions.insert_linee_attivita_controlli_ufficiali(?,?,?,?,?,?,?)";
				PreparedStatement pstNuoviSenzaLinea = db.prepareStatement(sqlNuoviSenzaLinea.toString());
				pstNuoviSenzaLinea.setInt(1, idCU);
				pstNuoviSenzaLinea.setInt(2, nuova);
				pstNuoviSenzaLinea.setString(3, "opu_relazione_stabilimento_linee_produttive");
				pstNuoviSenzaLinea.setString(4, "Controllo importato a seguito di trasferimento in nuova anagrafica");
				pstNuoviSenzaLinea.setInt(5, -1);
				pstNuoviSenzaLinea.setInt(6, tipoOperatore);
				pstNuoviSenzaLinea.setInt(7, userId);
				
				
				pstNuoviSenzaLinea.execute();

			}
			
			// PASSO 5: SVUOTARE ORG_ID SU TUTTI I TICKET AGGIORNATI
						String sqlOrg = "update ticket set note_internal_use_only = concat (note_internal_use_only,'. ', current_timestamp, ' - SVUOTAMENTO ORG_ID DOPO IMPORT CONTROLLO EFFETTUATO DA UTENTE ', " +userId+"), modified=current_timestamp, modifiedby = ? , org_id_old = org_id, org_id = null, trashed_date=null where org_id = ? and id_stabilimento = ? and (trashed_date is null or trashed_date='1970-01-01')";
						PreparedStatement pstOrg = db.prepareStatement(sqlOrg.toString());
						pstOrg.setInt(1, userId);
						pstOrg.setInt(2, this.getOrgId());
						pstOrg.setInt(3, this.getIdStabilimento());
						pstOrg.executeUpdate();
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
	public void importaControlliUfficialiCompletaDati(Connection db, int vecchia, int nuova, boolean primaria, String codice, int userId) {

		try {

			// PASSO 1: AGGIORNARE ID STABILIMENTO SU TUTTI I TICKET
			String sqlCu = "update ticket set note_internal_use_only = concat(note_internal_use_only, current_timestamp, '. Import controllo da org_id ', org_id,  ' a id_stabilimento "+this.getIdStabilimento()+" da utente "+userId+"' ), id_stabilimento_old = id_stabilimento, id_stabilimento = ?, modified = current_timestamp, modifiedby = ?, trashed_date = null where org_id = ? and (trashed_date is null or trashed_date='1970-01-01')";
			PreparedStatement pstCu = db.prepareStatement(sqlCu.toString());
			pstCu.setInt(1, this.getIdStabilimento());
			pstCu.setInt(2, userId);
			pstCu.setInt(3, this.getOrgId());
			pstCu.executeUpdate();

			// PASSO 2: RECUPERARSI TUTTI I CONTROLLI ASSOCIATI ALLA VECCHIA
			// LINEA DI ATTIVITA'
			String sqlVecchi = "";
			//802 tipologia vecchie anagrafiche farmacie parafarmacie grossisti
			if(this.getTipoVecchiaOrg() == 802){ 
				sqlVecchi = " SELECT t.ticketid as id_controllo_ufficiale, "
						+ " o.tipologia AS tipo_operatore "
						+ " FROM ticket t JOIN organization o ON o.org_id = t.org_id "
						+ " WHERE t.trashed_date IS NULL AND "
						+ " o.org_id = ? and o.tipologia = ? ;";			
			} else {
				sqlVecchi = " SELECT l.id_controllo_ufficiale, o.tipologia AS tipo_operatore " + 
						   " FROM linee_attivita_controlli_ufficiali l " + 
						   " LEFT JOIN ticket t ON t.ticketid = l.id_controllo_ufficiale AND t.tipologia = 3 " + 
						   " LEFT JOIN organization o ON o.org_id = t.org_id " + 
						   " WHERE (t.trashed_date IS NULL or t.trashed_date = '1970-01-01') AND " + 
						   "       l.trashed_date IS NULL and " + 
						   "       l.id_linea_attivita = ? and " + 
						   "       o.tipologia = ? and " + 
						   "       l.trashed_date is null ;";
			}
			PreparedStatement pstVecchi = db.prepareStatement(sqlVecchi.toString());

			pstVecchi.setInt(1, vecchia);
			pstVecchi.setInt(2, this.getTipoVecchiaOrg());

			ResultSet rsVecchi = pstVecchi.executeQuery();
			boolean trovataLinea = false;

			// PASSO 3: INSERIRE NELLA TABELLA OPU IL VECCHIO ID CONTROLLO CON
			// LA NUOVA LINEA ATTIVITA
			while (rsVecchi.next()) {
				trovataLinea = true;
				int idCU = rsVecchi.getInt("id_controllo_ufficiale");
				int tipoOperatore = rsVecchi.getInt("tipo_operatore");

				String sqlNuovi = "select * from public_functions.insert_linee_attivita_controlli_ufficiali(?,?,?,?,?,?,?)";
				PreparedStatement pstNuovi = db.prepareStatement(sqlNuovi.toString());
				pstNuovi.setInt(1, idCU);
				pstNuovi.setInt(2, nuova);
				pstNuovi.setString(3, "opu_relazione_stabilimento_linee_produttive");
				pstNuovi.setString(4, "Controllo importato a seguito di trasferimento in nuova anagrafica");
				pstNuovi.setInt(5, vecchia);
				pstNuovi.setInt(6, this.getTipoVecchiaOrg());
				pstNuovi.setInt(7, userId);

				pstNuovi.execute();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
	public void importaControlliUfficialiCuSenzaLinea(Connection db,  int userId) {

		try {

			// PASSO 4: INSERIRE NELLA TABELLA OPU IL VECCHIO ID CONTROLLO CON
			// LA NUOVA LINEA ATTIVITA PRIMARIA NEL CASO IN CUI NON CI SIANO
			// LINEE ASSOCIATE

			String sqlVecchiSenzaLinea = "select t.ticketid as id_controllo_ufficiale, o.tipologia as tipo_operatore from ticket t join organization o on coalesce(t.org_id,t.org_id_old) = o.org_id and t.trashed_date is null and t.tipologia= 3 left join linee_attivita_controlli_ufficiali l on l.id_controllo_ufficiale = t.ticketid  where l.id_controllo_ufficiale is null and coalesce(t.org_id,t.org_id_old) = ? and t.provvedimenti_prescrittivi <> 5  and assigned_date >'2016-01-01'";
			PreparedStatement pstVecchiSenzaLinea = db.prepareStatement(sqlVecchiSenzaLinea.toString());
			pstVecchiSenzaLinea.setInt(1, this.getOrgId());
			ResultSet rsVecchiSenzaLinea = pstVecchiSenzaLinea.executeQuery();

			// PASSO 5: INSERIRE NELLA TABELLA OPU IL VECCHIO ID CONTROLLO CON
			// LA NUOVA LINEA ATTIVITA E LA VECCHIA A -1
			while (rsVecchiSenzaLinea.next()) {
				int idCU = rsVecchiSenzaLinea.getInt("id_controllo_ufficiale");
				int tipoOperatore = rsVecchiSenzaLinea.getInt("tipo_operatore");

				String sqlNuoviSenzaLinea = "select * from public_functions.insert_linee_attivita_controlli_ufficiali(?,?,?,?,?,?,?)";
				PreparedStatement pstNuoviSenzaLinea = db.prepareStatement(sqlNuoviSenzaLinea.toString());
				pstNuoviSenzaLinea.setInt(1, idCU);
				pstNuoviSenzaLinea.setInt(2, -1);
				pstNuoviSenzaLinea.setString(3, "opu_relazione_stabilimento_linee_produttive");
				pstNuoviSenzaLinea.setString(4, "Controllo importato a seguito di trasferimento in nuova anagrafica");
				pstNuoviSenzaLinea.setInt(5, -1);
				pstNuoviSenzaLinea.setInt(6, tipoOperatore);
				pstNuoviSenzaLinea.setInt(7, userId);
				
				
				pstNuoviSenzaLinea.execute();

			}
			
			// PASSO 5: SVUOTARE ORG_ID SU TUTTI I TICKET AGGIORNATI
						String sqlOrg = "update ticket set note_internal_use_only = concat (note_internal_use_only,'. ', current_timestamp, ' - SVUOTAMENTO ORG_ID DOPO IMPORT CONTROLLO EFFETTUATO DA UTENTE ', " +userId+"), modified=current_timestamp, modifiedby = ? , org_id_old = org_id, org_id = null, trashed_date=null where org_id = ? and id_stabilimento = ? and (trashed_date is null or trashed_date ='1970-01-01') ";
						PreparedStatement pstOrg = db.prepareStatement(sqlOrg.toString());
						pstOrg.setInt(1, userId);
						pstOrg.setInt(2, this.getOrgId());
						pstOrg.setInt(3, this.getIdStabilimento());
						pstOrg.executeUpdate();
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	
	
	public ArrayList<DatiMobile> cercaAltreTarghe(Connection db) throws SQLException {
		ArrayList<DatiMobile> listaTarghe = new ArrayList<DatiMobile>();

		String sql = "select count(lai.*), o.org_id, upper(nome_correntista)as targa ,case when tipo_struttura = 5 then 'Mezzo di Trasporto' when tipo_struttura = 4 then 'Contenitori' when tipo_struttura =3 then 'Cisterne' else 'Altro' end as tipo_veicolo,name as ragione_sociale, "
				+ "trim(partita_iva) as piva ,trim(codice_fiscale_rappresentante) as cf ,nome_rappresentante,cognome_rappresentante, "
				+ "trim(coalesce(oa.city,'')) || ' , '|| trim(coalesce(oa.addrline1,'')) as sede_legale,"
				+ "case when tipo_struttura = 5 then 2 when tipo_struttura = 4 then 1 when tipo_struttura =3 then 0 else 3 end as id_tipo_veicolo "
				+ "from organization o "
				+ "left join la_imprese_linee_attivita lai on lai.org_id = o.org_id and lai.trashed_date is null  "
				+ "join organization_address oa on oa.org_id = o.org_id and oa.address_type = 1 "
				+ "where   tipo_dest ilike 'autoveicolo' and o.trashed_date is null and nome_correntista is not null and nome_correntista !='' and partita_iva = ? "
				+ " group by o.org_id, upper(nome_correntista) ,case when tipo_struttura = 5 then 'Mezzo di Trasporto' when tipo_struttura = 4 then 'Contenitori' when tipo_struttura =3 then 'Cisterne' else 'Altro' end ,name , "
				+ "trim(partita_iva)  ,trim(codice_fiscale_rappresentante) ,nome_rappresentante,cognome_rappresentante, "
				+ "trim(coalesce(oa.city,'')) || ' , '|| trim(coalesce(oa.addrline1,'')) ,"
				+ "case when tipo_struttura = 5 then 2 when tipo_struttura = 4 then 1 when tipo_struttura =3 then 0 else 3 end  "
				+ " having count(lai.*)=1 limit 50";

		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, operatore.getPartitaIva());
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			DatiMobile dati = new DatiMobile();
			dati.setPiva(rs.getString("piva"));
			dati.setRagioneSociale(rs.getString("ragione_sociale"));
			dati.setNomeRappresentante(rs.getString("nome_rappresentante"));
			dati.setCognomeRappresentante(rs.getString("cognome_rappresentante"));
			dati.setSedeLegale(rs.getString("sede_legale"));
			dati.setTipoVeicoloDescr(rs.getString("tipo_veicolo"));
			dati.setCfRappresentante(rs.getString("cf"));
			dati.setIdTipoVeicoloOpu(rs.getInt("id_tipo_veicolo"));

			dati.setTarga(rs.getString("targa"));
			dati.setOrgId(rs.getInt("org_id"));
			listaTarghe.add(dati);
		}

		return listaTarghe;
	}

	public void importaControlliUfficialionTarga(Connection db, int vecchia, int nuova, boolean primaria,
			String targa, String codice,int userId) {

		try {

			int idTarga = 0;
			// PASSO 0: seleziono id targa da settare sui cu
			if (targa != null && !targa.trim().equals("")) {
				String sqlTarga = "select id from opu_stabilimento_mobile where targa ilike ?  ";
				PreparedStatement pstTarga = db.prepareStatement(sqlTarga.toString());
				pstTarga.setString(1, targa);
				ResultSet rsTrga = pstTarga.executeQuery();
				if (rsTrga.next())
					idTarga = rsTrga.getInt(1);
			}

			// PASSO 1: AGGIORNARE ID STABILIMENTO SU TUTTI I TICKET
			String sqlCu = "update ticket set note_internal_use_only = concat(note_internal_use_only, current_timestamp, '. Import controllo da ', id_stabilimento,  ' a "+this.getIdStabilimento()+" da utente "+userId+"' ),  modifiedby = ?, modified = current_timestamp, id_stabilimento_old = id_stabilimento,id_targa_old=id_targa, id_stabilimento = ?,id_targa=?, trashed_date=null where org_id = ? and (trashed_date is null or trashed_date = '1970-01-01') ";
			PreparedStatement pstCu = db.prepareStatement(sqlCu.toString());
			pstCu.setInt(1, userId);
			pstCu.setInt(2, this.getIdStabilimento());
			pstCu.setInt(3, idTarga);
			pstCu.setInt(4, this.getOrgId());
			pstCu.executeUpdate();

			// PASSO 2: RECUPERARSI TUTTI I CONTROLLI ASSOCIATI ALLA VECCHIA
			// LINEA DI ATTIVITA'
			String sqlVecchi = "select id_controllo_ufficiale, tipo_operatore from linee_attivita_controlli_ufficiali_view where id_linea_attivita = ? and tipo_operatore = ? and trashed_date is null";
			PreparedStatement pstVecchi = db.prepareStatement(sqlVecchi.toString());

			pstVecchi.setInt(1, vecchia);
			pstVecchi.setInt(2, this.getTipoVecchiaOrg());

			ResultSet rsVecchi = pstVecchi.executeQuery();

			// PASSO 3: INSERIRE NELLA TABELLA OPU IL VECCHIO ID CONTROLLO CON
			// LA NUOVA LINEA ATTIVITA
			while (rsVecchi.next()) {
				int idCU = rsVecchi.getInt("id_controllo_ufficiale");
				int tipoOperatore = rsVecchi.getInt("tipo_operatore");

				String sqlNuovi = "select * from public_functions.insert_linee_attivita_controlli_ufficiali(?,?,?,?,?,?,?)";
				PreparedStatement pstNuovi = db.prepareStatement(sqlNuovi.toString());
				pstNuovi.setInt(1, idCU);
				pstNuovi.setInt(2, nuova);
				pstNuovi.setString(3, "opu_linee_attivita_controllo_ufficiale");
				pstNuovi.setString(4, "Controllo importato a seguito di trasferimento in nuova anagrafica");
				pstNuovi.setInt(5, vecchia);
				pstNuovi.setInt(6, this.getTipoVecchiaOrg());
				pstNuovi.setInt(7, userId);

			}

			// PASSO 4: INSERIRE NELLA TABELLA OPU IL VECCHIO ID CONTROLLO CON
			// LA NUOVA LINEA ATTIVITA PRIMARIA NEL CASO IN CUI NON CI SIANO
			// LINEE ASSOCIATE
			if (primaria) {
				String sqlVecchiSenzaLinea = "select t.ticketid as id_controllo_ufficiale, o.tipologia as tipo_operatore from ticket t join organization o on t.org_id = o.org_id and (t.trashed_date is null or t.trashed_date = '1970-01-01') and t.tipologia= 3 and o.trashed_date is null left join linee_attivita_controlli_ufficiali_view l on l.id_controllo_ufficiale = t.ticketid where l.id_controllo_ufficiale is null and t.org_id = ?  and t.provvedimenti_prescrittivi <> 5 ";
				PreparedStatement pstVecchiSenzaLinea = db.prepareStatement(sqlVecchiSenzaLinea.toString());
				pstVecchiSenzaLinea.setInt(1, this.getOrgId());
				ResultSet rsVecchiSenzaLinea = pstVecchiSenzaLinea.executeQuery();

				// PASSO 5: INSERIRE NELLA TABELLA OPU IL VECCHIO ID CONTROLLO
				// CON LA NUOVA LINEA ATTIVITA E LA VECCHIA A -1
				while (rsVecchiSenzaLinea.next()) {
					int idCU = rsVecchiSenzaLinea.getInt("id_controllo_ufficiale");
					int tipoOperatore = rsVecchiSenzaLinea.getInt("tipo_operatore");

					String sqlNuoviSenzaLinea = "select * from public_functions.insert_linee_attivita_controlli_ufficiali(?,?,?,?,?,?,?)";
					PreparedStatement pstNuoviSenzaLinea = db.prepareStatement(sqlNuoviSenzaLinea.toString());
					pstNuoviSenzaLinea.setInt(1, idCU);
					pstNuoviSenzaLinea.setInt(2, nuova);
					pstNuoviSenzaLinea.setString(3, "opu_relazione_stabilimento_linee_produttive");
					pstNuoviSenzaLinea.setString(4, "Controllo importato a seguito di trasferimento in nuova anagrafica");
					pstNuoviSenzaLinea.setInt(5, -1);
					pstNuoviSenzaLinea.setInt(6, tipoOperatore);
					pstNuoviSenzaLinea.setInt(7, userId);
					
					pstNuoviSenzaLinea.execute();
					
					
				}
			}
			
			// PASSO 6: SVUOTARE ORG_ID SU TUTTI I TICKET AGGIORNATI
			String sqlOrg = "update ticket set note_internal_use_only = concat (note_internal_use_only,'. ', current_timestamp, ' - SVUOTAMENTO ORG_ID DOPO IMPORT CONTROLLO EFFETTUATO DA UTENTE ', " +userId+"), modified=current_timestamp, modified_by = ? , org_id_old = org_id, org_id = null, trashed_date=null where org_id = ? and id_stabilimento = ? and (trashed_date is null or trashed_date = '1970-01-01')";
			PreparedStatement pstOrg = db.prepareStatement(sqlOrg.toString());
			pstOrg.setInt(1, userId);
			pstOrg.setInt(2, this.getOrgId());
			pstOrg.setInt(3, this.getIdStabilimento());
			pstOrg.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void aggiornaCategoriaRischio(Connection db) {

		Timestamp dataProssimoControllo = this.dataProssimoControllo;
		Integer categoriaRischio = this.getCategoriaRischio();

		Integer max = categoriaRischio;

		Iterator<LineaProduttiva> itProd = listaLineeProduttive.iterator();
		while (itProd.hasNext()) {
			LineaProduttiva thisLine = itProd.next();
			if (thisLine.getIdTipoVecchiaOrg() == 3) {
				categoriaRischio = thisLine.getCategoriaRischio();
				dataProssimoControllo = thisLine.getDataProssimoControllo();
				break;
			}

			if (thisLine.getCategoriaRischio() > max) {
				max = thisLine.getCategoriaRischio();
				dataProssimoControllo = thisLine.getDataProssimoControllo();
			}
		}
		
		if(categoriaRischio==0)
			categoriaRischio=null;

		String sqlCat = "update opu_stabilimento set categoria_rischio = ?, data_prossimo_controllo = ? where id = ?";
		PreparedStatement pstCat;
		try {
			pstCat = db.prepareStatement(sqlCat.toString());

			pstCat.setObject(1, categoriaRischio);
			pstCat.setTimestamp(2, dataProssimoControllo);
			pstCat.setInt(3, this.getIdStabilimento());
			pstCat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RicercheAnagraficheTab.inserOpu(db, this.getIdStabilimento());

	}

	public void aggiornaCategoriaRischio(Connection db,Organization org)
	{
		Timestamp dataProssimoControllo = null;
		if(org.getProssimoControllo()!=null)
			dataProssimoControllo = new Timestamp(org.getProssimoControllo().getTime());
		Integer categoriaRischio = (org.getCategoriaRischio()==0)?(null):(org.getCategoriaRischio());
		
		String sqlCat = "update opu_stabilimento set categoria_rischio = ?, data_prossimo_controllo = ? where id = ?";
		PreparedStatement pstCat;
		try 
		{
			pstCat = db.prepareStatement(sqlCat.toString());
			pstCat.setObject(1, categoriaRischio);
			pstCat.setObject(2, dataProssimoControllo);
			pstCat.setInt(3, this.getIdStabilimento());
			pstCat.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		RicercheAnagraficheTab.inserOpu(db, this.getIdStabilimento());

	}

	public int getTipoVecchiaOrg() {
		return tipoVecchiaOrg;
	}

	public void setTipoVecchiaOrg(int tipoVecchiaOrg) {
		this.tipoVecchiaOrg = tipoVecchiaOrg;
	}

	public void cessazioneStabilimento(Connection db) throws SQLException {

		Timestamp curr = new Timestamp(System.currentTimeMillis());

		int i = 0;
		String sql = "";
		PreparedStatement pst = null;
		if (this.getDataFineAttivita() != null) {
			sql = "update opu_stabilimento set data_fine_attivita=?,modified_by=?,modified=current_timestamp  ";
			sql += " ,stato=" + STATO_CESSATO;
			sql += "  where id = ? ;";
			pst = db.prepareStatement(sql);

			pst.setTimestamp(++i, this.getDataFineAttivita());
			pst.setInt(++i, modifiedBy);
			pst.setInt(++i, idStabilimento);
			pst.execute();
		}

		Iterator<LineaProduttiva> itLineeProduttive = listaLineeProduttive.iterator();

		while (itLineeProduttive.hasNext()) {

			sql = "update opu_relazione_stabilimento_linee_produttive set data_fine = ? ,modifiedby=?,modified=current_timestamp ";

			i = 0;
			LineaProduttiva linea = itLineeProduttive.next();
			if ((linea.getDataFine() != null)) {
				sql += " ,stato=" + STATO_CESSATO;
			} else {
				if (this.getDataFineAttivita() != null) {
					sql += " ,stato=" + STATO_CESSATO;
				}
			}

			sql += " where id_stabilimento = ? and id_linea_produttiva=? ;";
			pst = db.prepareStatement(sql);
			if ((linea.getDataFine() != null)) {
				pst.setTimestamp(++i, linea.getDataFine());
			} else {
				if (this.getDataFineAttivita() != null) {
					pst.setTimestamp(++i, getDataFineAttivita());
				} else
					pst.setTimestamp(++i, null);
			}
			pst.setInt(++i, modifiedBy);
			pst.setInt(++i, idStabilimento);
			pst.setInt(++i, linea.getIdRelazioneAttivita());
			pst.execute();
		}

	}

	public static List<Integer> getTipologieDaNonImportare() {
		List<Integer> tipologie = new ArrayList<Integer>();
		tipologie.add(0); // DIA
		tipologie.add(9); // TRASPORTO ANIMALE
		//tipologie.add(17); // IMBARCAZIONI
		// tipologie.add(12); //OPERATORI NON ALTROVE
		//tipologie.add(13); // OPERATORI PRIVATI
		tipologie.add(22); // OPERATORI FR
		tipologie.add(255); // CANI PADRONALI
		tipologie.add(5); // PUNTI DI SBARCO
		tipologie.add(6); // ASL
		tipologie.add(14); // ACQUE DI RETE
		tipologie.add(11); // SPEDITORI
		tipologie.add(15); // ZONE DI CONTROLLO
		tipologie.add(16); // COLONIE
		tipologie.add(4); // ABUSIVI
		return tipologie;

	}

	public String getValoreCampiEstesiLinee(Connection db, int idRel, String label) throws SQLException {
		String ret = "";
		PreparedStatement pst = db.prepareStatement(
				"Select valore_campo from opu_campi_estesi_lda where id_rel_stab_lp = ? and label_campo ilike ?");
		pst.setInt(1, idRel);
		pst.setString(2, label);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			ret = rs.getString(1);
		}
		rs.close();
		pst.close();
		return ret;
	}

	public void insertCampiMobile(Connection db, String targa, String tipo, String carta) {

		try {
			String sql = "insert into opu_stabilimento_mobile (id_stabilimento, targa, tipo, carta) values (?, ?, ?, ?)";
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

	public void insertAutomezziImport(Connection db, int idOrg) {

		PreparedStatement pst;

		try {
			pst = db.prepareStatement("Select * from public_functions.insert_autoveicoli(?)");
			pst.setInt(1, idOrg);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// System.out.println("Scheda centralizzata errore su query
			// "+query);
			e.printStackTrace();
		}

	}

	private ArrayList<DatiMobile> recuperaDatiMobile(Connection db, int idStabilimento) {
		ArrayList<DatiMobile> datiMobile = new ArrayList<DatiMobile>();
		PreparedStatement pst;

		try {

//			pst = db.prepareStatement("Select * from opu_stabilimento_mobile where id_stabilimento = ? and enabled and targa <>'' order by id ASC");
//			pst.setInt(1, idStabilimento); 
			
			pst = db.prepareStatement("select v.id, -1 as id_stabilimento, f.label_campo as label, v.valore_campo as targa, -1 as tipo, null as carta, true as enabled, 1 as indice from linee_mobili_html_fields f join linee_mobili_fields_value v on f.id = v.id_linee_mobili_html_fields join ml8_linee_attivita_nuove_materializzata o on o.id_nuova_linea_attivita = f.id_linea where v.valore_campo <> '' and ( v.id_rel_stab_linea in (select rs.id from opu_relazione_stabilimento_linee_produttive rs join opu_stabilimento s on rs.id_stabilimento = s.id where s.id = ? and enabled) or v.id_opu_rel_stab_linea in (select rs.id from opu_relazione_stabilimento_linee_produttive rs join opu_stabilimento s on rs.id_stabilimento = s.id where s.id = ? and enabled) ) and v.enabled and f.enabled and (f.label_campo = ? or f.label_campo = ?) order by v.valore_campo asc");

			pst.setInt(1, idStabilimento);
			pst.setInt(2, idStabilimento);
			pst.setString(3, "targa");
			pst.setString(4, "matricola");

			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			while (rs.next()) {
				DatiMobile dato = new DatiMobile(rs);
				datiMobile.add(dato);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// System.out.println("Scheda centralizzata errore su query
			// "+query);
			e.printStackTrace();
		}
		return datiMobile;
	}

	public ArrayList<DatiMobile> getDatiMobile() {
		return datiMobile;
	}

	public void setDatiMobile(ArrayList<DatiMobile> datiMobile) {
		this.datiMobile = datiMobile;
	}

	// OVERRIDE DEL METODO EQUALS PER IL CONFRONTO CON LA BDN
	public ArrayList equals(AllevamentoAjax all, Connection db, int idRel) throws SQLException {
		ArrayList differenze = new ArrayList();

		// if
		// (!this.getOperatore().getRagioneSociale().equals(all.getDenominazione()))
		// differenze.add("denominazione");
		//
		//// if (this.getSpecieA()!=all.getSpecie_allevata())
		//// differenze.add("specie_allevata");
		////
		//// if (this.getNumeroCapi()!=all.getNumero_capi())
		//// differenze.add("numero_capi");
		//
		// if
		// (!this.getOperatore().getPartitaIva().trim().equals(all.getCodice_fiscale()))
		// differenze.add("codice_fiscale");
		//
		// if
		// (!this.getOperatore().getRappLegale().getCodFiscale().equals(all.getCfProprietario()))
		// differenze.add("cfProprietario");
		//
		//
		// //INDIRIZZO SEDE LEGALE
		//
		// Indirizzo ind = this.getOperatore().getSedeLegale();
		//
		// if (!ind.getVia().equals(all.getIndirizzo()))
		// differenze.add("indirizzo");
		// if (!ind.getComuneTesto().equals(all.getComune()))
		// differenze.add("comune");
		//
		//
		String numeroCapiString = getValoreCampiEstesiLinee(db, idRel, "Numero capi");
		int numeroCapi = -1;
		try {
			numeroCapi = Integer.parseInt(numeroCapiString);
		} catch (Exception e) {
		}
		if (all.getNumero_capi() != numeroCapi)
			differenze.add("numero_capi");

		String codiceTipoAllevamento = getValoreCampiEstesiLinee(db, idRel, "Codice tipo allevamento");
		System.out.println("all.getCodiceTipoAllevamento(): " + all.getCodiceTipoAllevamento());
		System.out.println("codiceTipoAllevamento: " + codiceTipoAllevamento);
		if ((all.getCodiceTipoAllevamento() != null && codiceTipoAllevamento != null)
				&& ((all.getCodiceTipoAllevamento() == null || codiceTipoAllevamento == null)
						|| (!all.getCodiceTipoAllevamento().equals(codiceTipoAllevamento))))
			differenze.add("codice_tipo_allevamento");

		return differenze;

	}

	public int getAltId() {
		return altId;
	}

	public void setAltId(int altId) {
		this.altId = altId;
	}

	public boolean isMacello() {

		int count = 0;

		Iterator it = this.getListaLineeProduttive().iterator();
		while (it.hasNext()) {
			LineaProduttiva lp = (LineaProduttiva) it.next();
			if (lp.getId() == 7044 || lp.getId() == 7058 || lp.getId() == 7067 || lp.getId() == 7360)
				count++;
		}

		if (count == 0)
			return false;
		else
			return true;
	}

	public void eliminaAnagrafica(Connection db, int userId) throws SQLException {
		//PreparedStatement pst = db.prepareStatement("update opu_stabilimento set trashed_date = current_date , trashed_by=? where id =? ");
		PreparedStatement pst = db.prepareStatement("select * from gestione_anagrafica_elimina_scheda(?,?)");
		pst.setInt(1, userId);
		pst.setInt(2, idStabilimento);
		//pst.execute();
		ResultSet rs = pst.executeQuery();
	}

	public void updateCategoriaPrecedente(Connection db, int categoriaR, int orgid) throws SQLException {

		PreparedStatement pst = db
				.prepareStatement("UPDATE opu_stabilimento set categoria_precedente = ? where id = ?");
		pst.setInt(1, categoriaR);
		pst.setInt(2, orgid);
		pst.execute();

	}

	public void applicaErrataCorrige(Connection db, ActionContext context) throws SQLException {
		String sql = "UPDATE opu_stabilimento set id_operatore = ? where id = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, this.getOperatore().getIdOperatore());
		pst.setInt(2, this.getIdStabilimento());
		pst.execute();

	}

	public LinkedHashMap<Integer, LinkedHashMap<Integer, LineeCampiEstesi>> getCampiEstesiLinee2(Connection db)
			throws SQLException {

		LinkedHashMap<Integer, LinkedHashMap<Integer, LineeCampiEstesi>> campiPerLinea = new LinkedHashMap<Integer, LinkedHashMap<Integer, LineeCampiEstesi>>();

		LinkedHashMap<Integer, LineeCampiEstesi> campi = null;

		Iterator<LineaProduttiva> itLineeProduttive = listaLineeProduttive.iterator();
		while (itLineeProduttive.hasNext()) {
			campi = new LinkedHashMap<Integer, LineeCampiEstesi>();
			LineaProduttiva linea = itLineeProduttive.next();
			PreparedStatement pst = db.prepareStatement(
					"select f.id, f.nome_campo, f.label_campo, v.indice, v.valore_campo, f.tipo_campo, f.tabella_lookup, case when f.readonly then false else true end as modificabile, true as visibile, v.id as id_valore, v.id_opu_rel_stab_linea as id_rel_stab_lp, o.path_descrizione, v.id_utente_inserimento, v.data_inserimento   from linee_mobili_html_fields f left join linee_mobili_fields_value v on f.id = v.id_linee_mobili_html_fields and (v.enabled and (v.id_opu_rel_stab_linea  = ? or v.id_rel_stab_linea  = ?)) join ml8_linee_attivita_nuove_materializzata o on o.id_nuova_linea_attivita = f.id_linea where f.id_linea in (select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive where id = ?) and f.enabled order by f.id_linea, v.indice, f.id");
			pst.setInt(1, linea.getId_rel_stab_lp());
			pst.setInt(2, linea.getId_rel_stab_lp());
			pst.setInt(3, linea.getId_rel_stab_lp());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				LineeCampiEstesi campiLinea = new LineeCampiEstesi();
				campiLinea.buildRecord(rs);
				campi.put(Integer.valueOf(campiLinea.getIdValore()), campiLinea); //sommo i i valori chiave per differenziare ogni campo esteso per il dato indice
			}
			if (campi.size() > 0)
				campiPerLinea.put(linea.getId_rel_stab_lp(), campi);
			rs.close();
			pst.close();
		}
		return campiPerLinea;

	}

	public LineaProduttivaList getListaLineeProduttivePregresse() {
		return listaLineeProduttivePregresse;
	}

	public void setListaLineeProduttivePregresse(LineaProduttivaList listaLineeProduttivePregresse) {
		this.listaLineeProduttivePregresse = listaLineeProduttivePregresse;
	}

	public void buildListLineeProduttivePregresse(Connection db) throws SQLException, IndirizzoNotFoundException {
		listaLineeProduttivePregresse.setIdStabilimento(idStabilimento);
		// listaLineeProduttivePregresse.setPregresse(true);
		listaLineeProduttivePregresse.buildList(db);

	}

	public int getSuperficieMqCanile(Connection db, int idRelCanile) throws SQLException {
		int superficie = -1;
		PreparedStatement pst = db.prepareStatement(
				" select valore_campo , id_linee_mobili_html_fields " + 
		        " from linee_mobili_fields_value where (id_opu_rel_stab_linea  = ?  or id_rel_stab_linea = ?) and enabled and " + 
				" id_linee_mobili_html_fields = (" + 
				" select field.id from linee_mobili_html_fields field " + 
				" join opu_relazione_stabilimento_linee_produttive rel on field.id_linea = rel.id_linea_produttiva and rel.id = ? " + 
				" where field.nome_campo = 'superficie' )");
		pst.setInt(1, idRelCanile);
		pst.setInt(2, idRelCanile);
		pst.setInt(3, idRelCanile);

		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			try {
				superficie = Integer.parseInt(rs.getString("valore_campo"));
			} catch (Exception e) {
			}
		}
		return superficie;
	}

	public void setAction(String string) {
		this.action = string; 
	}

	public void associaPraticaSuap(Connection db, String numeroPratica, int comunePratica, int userId) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select associa_pratica_suap from associa_pratica_suap(?,?,?,?)";
    	PreparedStatement pst = db.prepareStatement(sql);
    	pst.setString(1, numeroPratica);
		pst.setInt(2, comunePratica);
		pst.setInt(3, this.getIdStabilimento());
		pst.setInt(4, userId);
		ResultSet rs= pst.executeQuery();
		
	}

	public void escludiDaRicerca(Connection db, int idStab) {
		// TODO Auto-generated method stub
		String sql = "select * from public.opu_escludi_da_ricerca(?)";
    	PreparedStatement st;
		try {
			st = db.prepareStatement(sql);
			st.setInt(1,idStab);
	        ResultSet rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	public String inserisci_stabilimento(Connection db, Map campiFissiTemp, Map campiEstesi, int userId, 
			   int idTipologiaPratica, String numeroPratica, int idComunePratica) throws Exception{

		String sql = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		sql = "select * from public.insert_gestione_anagrafica(?,?,?,?,?,?)";
		st = db.prepareStatement(sql);
		st.setObject(1, campiFissiTemp);
		st.setObject(2, campiEstesi);
		st.setInt(3, userId);
		st.setInt(4, idTipologiaPratica);
		st.setString(5, numeroPratica);
		st.setInt(6, idComunePratica);

		System.out.println(st);

		rs = st.executeQuery();
		String id_stabilimento = "";

		while(rs.next())
		{
			id_stabilimento = rs.getString("insert_gestione_anagrafica");
			/*
			 //in quel metodo scaricaCampiAggiuntiviPerTutteLeLineeRichiestaValidata vengono passati dati errati
			org.aspcfs.modules.opu.base.LineaProduttivaList listaLineeProduttive = new org.aspcfs.modules.opu.base.LineaProduttivaList();
			listaLineeProduttive.setIdStabilimento(Integer.parseInt(id_stabilimento));
			listaLineeProduttive.buildList(db);
			InterfValidazioneRichieste interf = new InterfValidazioneRichieste();
			interf.scaricaCampiAggiuntiviPerTutteLeLineeRichiestaValidata(userId, Integer.parseInt(id_stabilimento), null, db, listaLineeProduttive);
			*/
		}

		this.setIdStabilimento(id_stabilimento);
		System.out.println("stabilimento inserito: " + id_stabilimento);
		
		return id_stabilimento;
		}

	public void getDatiByAltId(Connection db, int altId) throws SQLException{
		// TODO Auto-generated method stub
		String sqlStab = "select o.tipo_attivita::text as tipo_attivita, "
    			+ "o.id_asl::text as id_asl, lsi.description::text as descrizione_asl "
    			+ "from opu_stabilimento o join lookup_site_id lsi on o.id_asl = lsi.code where alt_id  = " + String.valueOf(altId);
    	PreparedStatement pstStab = db.prepareStatement(sqlStab);
    	ResultSet rsStab = pstStab.executeQuery();
    	while (rsStab.next()) {
    	    this.asl = rsStab.getString("descrizione_asl").toLowerCase();
    	    this.tipoAttivitaDesc =  rsStab.getString("tipo_attivita").toLowerCase();
    	}
	}

	public LinkedHashMap<String, String> getValoriAnagrafica(Connection db, int altId) {
		// TODO Auto-generated method stub
        LinkedHashMap<String, String> valoriAnagrafica = new LinkedHashMap<String, String>();
        String sqlValoriAnagrafica = "select * from public.get_valori_anagrafica(?)";
    	
    	try {
        	PreparedStatement pstValoriAnagrafica = db.prepareStatement(sqlValoriAnagrafica);
			pstValoriAnagrafica.setInt(1, altId);
			ResultSet rsValoriAnagrafica = pstValoriAnagrafica.executeQuery();
	        ResultSetMetaData rsmd = rsValoriAnagrafica.getMetaData();
	        int columnCount = rsmd.getColumnCount();

	        while (rsValoriAnagrafica.next()) {
		        for (int i = 1; i <= columnCount; i++ ) {
		          String key = rsmd.getColumnName(i);
		          String value = rsValoriAnagrafica.getString(key)!=null ? rsValoriAnagrafica.getString(key).replaceAll("\"",  "'").replaceAll("\n",  ";") : "";
		      	  String htmlValue = value;  
		          valoriAnagrafica.put(key, htmlValue);
		        }
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return valoriAnagrafica;
	}
	
	public LinkedHashMap<String, String> getValoriAnagraficaEstesi(Connection db, int altId) {
		// TODO Auto-generated method stub
        LinkedHashMap<String, String> valoriEstesi = new LinkedHashMap<String, String>();
        String sqlValoriEstesi = "select * from public.get_valori_anagrafica_estesi(?)";
    	
    	try {
        	PreparedStatement pstValoriEstesi = db.prepareStatement(sqlValoriEstesi);
			pstValoriEstesi.setInt(1, altId);
			ResultSet rsValoriEstesi = pstValoriEstesi.executeQuery();
	       
	        while (rsValoriEstesi.next()) {
		          String key = rsValoriEstesi.getString("nome_campo");
		          String value = rsValoriEstesi.getString("valore_campo") !=null ? rsValoriEstesi.getString("valore_campo").replaceAll("\"",  "'") : "";
		      	  String htmlValue = value;  
		          valoriEstesi.put(key, htmlValue);
		      
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return valoriEstesi;
	}
	
	public LinkedHashMap<String, String> getValoriInfoSpecifica(Connection db, int id_info_specifica) {
		
        LinkedHashMap<String, String> valoriInfoSpecifica = new LinkedHashMap<String, String>();
        String sqlValoriInfoSpecifica = "select * from public.get_valori_anagrafica_info_specifica(?)";
    	
    	try {
        	PreparedStatement pstValoriInfoSpecifica = db.prepareStatement(sqlValoriInfoSpecifica);
			pstValoriInfoSpecifica.setInt(1, id_info_specifica);
			ResultSet rsValoriInfoSpecifica = pstValoriInfoSpecifica.executeQuery();
			int i = 0;
	        while (rsValoriInfoSpecifica.next()) {
		          String key = rsValoriInfoSpecifica.getString("nome_campo");
		          String value = rsValoriInfoSpecifica.getString("valore_campo") !=null ? rsValoriInfoSpecifica.getString("valore_campo").replaceAll("\"",  "'") : "";
		      	  String htmlValue = key + value; 
		      	  valoriInfoSpecifica.put(String.valueOf(i++), htmlValue);	      
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return valoriInfoSpecifica;
	}
	
	
	public String RecuperaTemplateOsaDaLinea(Connection db, LineaProduttiva lda_template){
		
		String codice_output = "";
		String codice = lda_template.getCodice();
		String query = "select distinct codice_univoco_ml as codice from schema_anagrafica where codice_univoco_ml ilike ?";
		PreparedStatement pstRecuperaTemplate;
		try {
			pstRecuperaTemplate = db.prepareStatement(query);
			pstRecuperaTemplate.setString(1, codice);
			ResultSet rsRisultato = pstRecuperaTemplate.executeQuery();
			
			while(rsRisultato.next()){
				codice_output = rsRisultato.getString("codice");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return codice_output;
	}
	
	public int numeroLineeAggiungibili(Connection db, int stabId, int tipo_gruppo_utente){
		
		int numero_linee = 0;
		String sqlNumeroLinee = "select count(*)::integer numero_linee "
								+ "from master_list_flag_linee_attivita "
								+ "where fisso and no_scia is false and rev = 10 "
								+ "and (case when ? = 11 then visibilita_asl end or case when ? = 15 then visibilita_regione end) "
								+ "and id_linea not in "
									+ "(select distinct regexp_split_to_table(string_agg(incompatibilita,','),',')::integer "
									+ "from master_list_flag_linee_attivita where id_linea in "
									+ "(select id_linea_produttiva from opu_relazione_stabilimento_linee_produttive "
										+ "where enabled and id_stabilimento = ?))";
		PreparedStatement stNumeroLinee;
		try {
			stNumeroLinee = db.prepareStatement(sqlNumeroLinee);
			stNumeroLinee.setInt(1, tipo_gruppo_utente);
			stNumeroLinee.setInt(2, tipo_gruppo_utente);
			stNumeroLinee.setInt(3, stabId);
			ResultSet rsNumeroLinee = stNumeroLinee.executeQuery();
			
			while(rsNumeroLinee.next()){
				numero_linee = rsNumeroLinee.getInt("numero_linee");
	        }
 	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return numero_linee;
	}

	public int contaLinee(Connection db, int altId) {
		// TODO Auto-generated method stub
		int numero_linee = 0;
		String sqlNumeroLinee = "select count(ml8.codice)::integer numero_linee "
				+ "from opu_relazione_stabilimento_linee_produttive rel "
				+ "join opu_stabilimento s on rel.id_stabilimento = s.id "
				+ "join ml8_linee_attivita_nuove_materializzata ml8 on rel.id_linea_produttiva = ml8.id_nuova_linea_attivita "
				+ "where s.alt_id = ? and rel.enabled";
			PreparedStatement stNumeroLinee;
			try {
				stNumeroLinee = db.prepareStatement(sqlNumeroLinee);
				stNumeroLinee.setInt(1, altId);
				ResultSet rsNumeroLinee = stNumeroLinee.executeQuery();
				
				while(rsNumeroLinee.next()){
					numero_linee = rsNumeroLinee.getInt("numero_linee");
		        }
	 	        
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return numero_linee;
	}

	public LinkedHashMap<String, String> getValoriAnagraficaModifica(Connection db, int altId2) {
		// TODO Aut-generated method stub
    	LinkedHashMap<String, String> valoriAnagrafica = new LinkedHashMap<String, String>();
    	try {
	    	String sqlValoriAnagrafica = "select * from public.get_valori_anagrafica_modifica(?)";
	    	PreparedStatement pstValoriAnagrafica;
			pstValoriAnagrafica = db.prepareStatement(sqlValoriAnagrafica);
			pstValoriAnagrafica.setInt(1, altId);
	        ResultSet rsValoriAnagrafica = pstValoriAnagrafica.executeQuery();
	        ResultSetMetaData rsmd = rsValoriAnagrafica.getMetaData();
	        int columnCount = rsmd.getColumnCount();

	        while (rsValoriAnagrafica.next()) {
		        for (int i = 1; i <= columnCount; i++ ) {
		          String key = rsmd.getColumnName(i);
		          String value = rsValoriAnagrafica.getString(key)!=null ? rsValoriAnagrafica.getString(key).replaceAll("\"",  "'") : "";
		      	  String htmlValue = value;  
		          valoriAnagrafica.put(key, htmlValue);
		        }
	        }
	        
	        String sqlValoriAnagraficaExtra = "select * from public.get_valori_anagrafica_estesi_modifica(?)";
	        PreparedStatement pstValoriAnagraficaExtra;
	        pstValoriAnagraficaExtra = db.prepareStatement(sqlValoriAnagraficaExtra);
	        pstValoriAnagraficaExtra.setInt(1, altId);
	        ResultSet rsValoriAnagraficaExtra = pstValoriAnagraficaExtra.executeQuery();
	        
	        while(rsValoriAnagraficaExtra.next()){
	        	String key = rsValoriAnagraficaExtra.getString("nome_campo");
	        	String value = rsValoriAnagraficaExtra.getString("valore_campo")!=null ? rsValoriAnagraficaExtra.getString("valore_campo").replaceAll("\"",  "'") : "";;
	        	valoriAnagrafica.put(key, value);
	        }
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	
		
		return valoriAnagrafica;
	}
	
	public LinkedHashMap<String, String> getValoriAnagraficaImport(Connection db, int altId) {
		// TODO Aut-generated method stub
		    LinkedHashMap<String, String> valoriAnagrafica = new LinkedHashMap<String, String>();
		    String sqlValoriAnagrafica = "select * from public.get_valori_anagrafica_import(?)";
		    System.out.println("sqlValoriAnagrafica: " + sqlValoriAnagrafica);
			try {
				PreparedStatement pstValoriAnagrafica = db.prepareStatement(sqlValoriAnagrafica);
				pstValoriAnagrafica.setInt(1, altId);
				ResultSet rsValoriAnagrafica = pstValoriAnagrafica.executeQuery();
				ResultSetMetaData rsmd = rsValoriAnagrafica.getMetaData();
				int columnCount = rsmd.getColumnCount();

				while (rsValoriAnagrafica.next()) {
					for (int i = 1; i <= columnCount; i++ ) {
				          String key = rsmd.getColumnName(i);
				          String value = rsValoriAnagrafica.getString(key)!=null ? rsValoriAnagrafica.getString(key).replaceAll("\"",  "'") : "";
				      	  String htmlValue = value;  
				          valoriAnagrafica.put(key, htmlValue);
				        }
				    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return valoriAnagrafica;
	}
	
	
	public LinkedHashMap<String, String> getValoriAnagraficaExtra(Connection db, int altId) {
		// TODO Aut-generated method stub
        LinkedHashMap<String, String> valoriExtra = new LinkedHashMap<String, String>();
		String sqlValoriExtra = "select * from public.get_valori_anagrafica_extra(?)";
		PreparedStatement pstValoriExtra;
		try {
			pstValoriExtra = db.prepareStatement(sqlValoriExtra);
			pstValoriExtra.setInt(1, altId);
			ResultSet rsValoriExtra = pstValoriExtra.executeQuery();
			int i = 0;
			while (rsValoriExtra.next()) { 

				String inserito_da = rsValoriExtra.getString("inserito_da");
				String inserito_il = rsValoriExtra.getString("inserito_il");
				String categoria_di_rischio = rsValoriExtra.getString("categoria_di_rischio");
				String data_prossimo_controllo = rsValoriExtra.getString("data_prossimo_controllo");
				String data_inizio = rsValoriExtra.getString("data_inizio");
				String stato = rsValoriExtra.getString("stato");
				String tipo_attivita = rsValoriExtra.getString("tipo_attivita");
				
				String note = rsValoriExtra.getString("note");
				if(note != null){
					note = note.replace("\"","'");
				}
				
				String note_impresa = rsValoriExtra.getString("note_impresa");
				if(note_impresa != null){
					note_impresa = note_impresa.replace("\"","'"); 
				}
				String categorizzabili = rsValoriExtra.getString("categorizzabili");

				String htmlValue = "";
				htmlValue = "<td class='formLabel'>inserito da</td><td>"+inserito_da+"</td>";
			    valoriExtra.put(String.valueOf(i++), htmlValue);
			    htmlValue = "<td class='formLabel'>inserito il</td><td>"+inserito_il+"</td>";
				valoriExtra.put(String.valueOf(i++), htmlValue);
			    if(categorizzabili != null && categorizzabili.trim().equalsIgnoreCase("true")){
			    	htmlValue = "<td class='formLabel'>categoria di rischio</td><td>"+categoria_di_rischio+"</td>";
					valoriExtra.put(String.valueOf(i++), htmlValue);
					htmlValue = "<td class='formLabel'>data prossimo controllo</td><td>"+data_prossimo_controllo+"</td>";
					valoriExtra.put(String.valueOf(i++), htmlValue);
					htmlValue = "<td class='formLabel'>data inizio</td><td>"+data_inizio+"</td>";
					valoriExtra.put(String.valueOf(i++), htmlValue);
				}
			    htmlValue = "<td class='formLabel'>stato</td><td>"+stato+"</td>";
				valoriExtra.put(String.valueOf(i++), htmlValue);
			    htmlValue = "<td class='formLabel'>tipo attivita</td><td>"+tipo_attivita+"</td>";
				valoriExtra.put(String.valueOf(i++), htmlValue);
				
				htmlValue = (note_impresa!=null ? "<td class='formLabel'>note impresa</td><td>"+note_impresa+"</td>" : "<td class='formLabel'>note impresa</td><td></td>");
				valoriExtra.put(String.valueOf(i++), htmlValue);
			    htmlValue = (note!=null ? "<td class='formLabel'>note</td><td>"+note+"</td>" : "<td class='formLabel'>note</td><td></td>");
				valoriExtra.put(String.valueOf(i++), htmlValue);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return valoriExtra;
	}
	
	public LinkedHashMap<String, String> getValoriAnagraficaLinee(Connection db, int altId) {
		
        LinkedHashMap<String, String> valoriLinee = new LinkedHashMap<String, String>();
		String sqlValoriLinee = "select * from public.get_valori_anagrafica_linee(?)";
	   	PreparedStatement pstValoriLinee;
		try {
			pstValoriLinee = db.prepareStatement(sqlValoriLinee);
			pstValoriLinee.setInt(1, altId);
			ResultSet rsValoriLinee = pstValoriLinee.executeQuery();
			int i = 0;
			while (rsValoriLinee.next()) {
		       	String macroarea = rsValoriLinee.getString("macroarea");
		       	String aggregazione = rsValoriLinee.getString("aggregazione");
		       	String attivita = rsValoriLinee.getString("attivita");
		       	String livelli_aggiuntivi = rsValoriLinee.getString("livelli_aggiuntivi");
		       	String dataInizio = rsValoriLinee.getString("data_inizio");
		       	String dataFine = rsValoriLinee.getString("data_fine");
		       	String carattere = rsValoriLinee.getString("carattere");
		       	String numeroRegistrazione = rsValoriLinee.getString("numero_registrazione");
		       	String codifica_ufficiale_esistente = rsValoriLinee.getString("gia_codificato_come");

		       	String cun = rsValoriLinee.getString("cun");
		       	String norma = rsValoriLinee.getString("norma");
		       	String stato = rsValoriLinee.getString("stato");
		       	String variazione_stato = rsValoriLinee.getString("variazione_stato");
		       	String dati_aggiuntivi = rsValoriLinee.getString("dati_aggiuntivi");
		       	

		       	String htmlValue = "";
		       	htmlValue += "<td class='formLabel'>"+norma+"</td>";
		       	htmlValue += "<td>";
		       	htmlValue += (numeroRegistrazione!=null ? "Num. registrazione: <b>"+numeroRegistrazione+"</b><br/>" : "");
		       	htmlValue += (codifica_ufficiale_esistente!=null ? "Gia codificato come: <b>"+codifica_ufficiale_esistente+"</b><br/>" : "");

		       	htmlValue += macroarea+"-><br/>"+aggregazione+"-><br/>"+attivita + "<br>";
		       	htmlValue += "Carattere: <b>"+carattere+"</b>&nbsp;&nbsp;&nbsp;&nbsp;" +
		       				(dataInizio!=null ? " Data Inizio: <b>"+dataInizio+"</b>&nbsp;&nbsp;&nbsp;&nbsp;" : "") +
		       				(dataFine!=null ? " Data Fine: <b>"+dataFine+"</b>&nbsp;&nbsp;&nbsp;&nbsp;" : ""); 
		       	
		       	if(stato.trim().equalsIgnoreCase("attivo")){
		       		htmlValue += "<span style='background-color:lime;'> Stato: <b>"+stato+"</b> </span> <br/>"; 
		       	} else if(stato.trim().equalsIgnoreCase("cessato") || stato.trim().equalsIgnoreCase("cessato/revocato")){
		       		htmlValue += "<span style='background-color:red;'> Stato: <b>"+stato+"</b> </span><br/>"; 
		       	} else if(stato.trim().equalsIgnoreCase("sospeso")){
		       		htmlValue += "<span style='background-color:orange;'> Stato: <b>"+stato+"</b> </span><br/>"; 
		       	} else {
		       		htmlValue += " Stato: <b>"+stato+"</b> <br/>"; 
		       	}
		       	
		       	if(!variazione_stato.equalsIgnoreCase("")){
		       		htmlValue += variazione_stato + "<br>";
		       	}
		       	
		       	htmlValue +=(cun!=null ? "CUN: <b>" + cun +"</b> <br/>" : "");
		       	htmlValue +=(!livelli_aggiuntivi.equalsIgnoreCase("") ? "<br><b>LIVELLI AGGIUNTIVI</b><br><i>" + livelli_aggiuntivi + "</i><br>" : "");

		       	if(!dati_aggiuntivi.equalsIgnoreCase("")){
		       		htmlValue += "<br><div><center>" + dati_aggiuntivi + "</center></div><br>";
		       	}
		       	htmlValue += "</td>";
		       	valoriLinee.put(String.valueOf(i++), htmlValue);
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		
		return valoriLinee;
		
	}
	
	
	
	
	public int aggiornaLinee(Connection db,Organization org, LineaProduttiva lp, Stabilimento stabNew,int altId,ActionContext contextPrincipale, ActionContext contextLinee, int i,int j) throws SQLException, ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		String nameDataInizio = "_b_lineaattivita_"+i+"_data_inizio_attivita";
		if(contextPrincipale.getRequest().getParameter("_b_lineaattivita_"+i+"_data_inizio_attivita")==null || contextPrincipale.getRequest().getParameter("_b_lineaattivita_"+i+"_data_inizio_attivita").equals(""))
			nameDataInizio = "_b_lineaattivita_data_inizio_attivita";
		Timestamp dataInizio =  new Timestamp(sdf.parse(contextPrincipale.getRequest().getParameter(nameDataInizio)).getTime());
		Timestamp dataFine = null;
		if(contextPrincipale.getRequest().getParameter("_b_lineaattivita_"+i+"_data_fine_attivita")!=null && !contextPrincipale.getRequest().getParameter("_b_lineaattivita_"+i+"_data_fine_attivita").equals("") && !contextPrincipale.getRequest().getParameter("_b_lineaattivita_"+i+"_data_fine_attivita").equals("null"))
			dataFine = new Timestamp(sdf.parse(contextPrincipale.getRequest().getParameter("_b_lineaattivita_"+i+"_data_fine_attivita")).getTime());
		boolean lineaMappata = contextPrincipale.getRequest().getParameter("lineaMappata"+i)!=null && !contextPrincipale.getRequest().getParameter("lineaMappata"+i).equals("null") && contextPrincipale.getRequest().getParameter("lineaMappata"+i).equals("true");
		if(!lineaMappata)
		{
			if(contextLinee!=null)
			{
				if(contextLinee.getRequest().getParameter("vecchiaLineaDataInizio"+j)!=null && !contextLinee.getRequest().getParameter("vecchiaLineaDataInizio"+j).equals("") && !contextLinee.getRequest().getParameter("vecchiaLineaDataInizio"+j).equals("null"))
					dataInizio =   new Timestamp(sdf.parse(contextLinee.getRequest().getParameter("vecchiaLineaDataInizio"+j)).getTime());
				if(contextLinee.getRequest().getParameter("vecchiaLineaDataFine"+j)!=null && !contextLinee.getRequest().getParameter("vecchiaLineaDataFine"+j).equals("") && !contextLinee.getRequest().getParameter("vecchiaLineaDataFine"+j).equals("null"))
					dataFine = new Timestamp(sdf.parse(contextLinee.getRequest().getParameter("vecchiaLineaDataFine"+j)).getTime());
			}
			j++;
		}
		
		
		String sql4 = " update opu_relazione_stabilimento_linee_produttive set pregresso_o_import = true, codice_ufficiale_esistente = ?,id_vecchia_linea = ?,data_inizio = ?, data_fine = ? where id_linea_produttiva = ? and id_stabilimento = ? ";
		PreparedStatement pst4 = db.prepareStatement(sql4);
		if(org.getTipologia() == 802){
			pst4.setString(1, contextPrincipale.getRequest().getParameter("n_reg_import_802"));
		} else {
			pst4.setString(1, org.get_numero_registrazione(db,altId) == null ? "" : org.get_numero_registrazione(db,altId));
		}
		pst4.setInt(2, lp.getIdVecchiaLinea());
		pst4.setTimestamp(3, dataInizio);
		pst4.setTimestamp(4, dataFine);
		pst4.setInt(5, lp.getId());
		
		pst4.setInt(6, stabNew.getIdStabilimento());
		pst4.execute();
		
		if(dataFine!=null && dataFine.before(new Timestamp(new java.util.Date().getTime())))
		{
			pst4 = db.prepareStatement(" update opu_relazione_stabilimento_linee_produttive set stato = 4 where id_linea_produttiva = ? and id_stabilimento = ? ");
			pst4.setInt(1, lp.getId());
			pst4.setInt(2, stabNew.getIdStabilimento());
			pst4.execute();
		}
		
		return j;
	}
	
	public void aggiornaInfoOp(Connection db,Organization org,int userId,boolean flag_pregresse, Stabilimento stabNew,int altId) throws SQLException
	{
		String sql1 = "update opu_stabilimento set importato = ?, codice_ufficiale_esistente = ?, riferimento_org_id = ?, notes_hd = 'Stabilimento importato in data ' || now() || ' da utente con user_id ' || ?, linee_pregresse = ? where id = ?; " + 
		              "update opu_relazione_stabilimento_linee_produttive set id_tipo_vecchio_operatore = ? where id_stabilimento = ?; " + 
				      "select * from opu_insert_into_ricerche_anagrafiche_old_materializzata(?)";
		PreparedStatement pst1 = db.prepareStatement(sql1);
		pst1.setBoolean(1, true);
		pst1.setString(2, org.get_numero_registrazione(db,altId) == null ? "" : org.get_numero_registrazione(db,altId));
		pst1.setInt(3, altId);

		pst1.setInt(4, userId);
		pst1.setBoolean(5, flag_pregresse);
		pst1.setInt(6, stabNew.getIdStabilimento());
		pst1.setInt(7, org.getTipologia());
		pst1.setInt(8, stabNew.getIdStabilimento());
		pst1.setInt(9, stabNew.getIdStabilimento());
		pst1.execute();
	}
	
	public String inserisci_richiesta_apicoltura(Connection db, Map campiFissiTemp, Map campiEstesi, int userId, 
			   int idTipologiaPratica, String numeroPratica, int idComunePratica) throws Exception{
	
		String sql = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		sql = "select * from public.insert_richiesta_apicoltura_new(?,?,?,?,?,?)";
		st = db.prepareStatement(sql);
		st.setObject(1, campiFissiTemp);
		st.setObject(2, campiEstesi);
		st.setInt(3, userId);
		st.setInt(4, idTipologiaPratica);
		st.setString(5, numeroPratica);
		st.setInt(6, idComunePratica);
	
		System.out.println(st);
	
		rs = st.executeQuery();
		String id_stab_richiesta_apicoltura = "";
	
		while(rs.next())
		{
			id_stab_richiesta_apicoltura = rs.getString("insert_richiesta_apicoltura_new");
		}
	
		System.out.println("id richiesta apicoltura inserita: " + id_stab_richiesta_apicoltura);
		
		return id_stab_richiesta_apicoltura;
	}

}
