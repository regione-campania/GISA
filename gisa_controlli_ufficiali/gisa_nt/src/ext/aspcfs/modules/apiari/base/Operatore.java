/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import it.izs.apicoltura.apianagraficaattivita.ws.Api;
import it.izs.apicoltura.apianagraficaattivita.ws.Apiatt;
import it.izs.apicoltura.apianagraficaattivita.ws.BusinessWsException_Exception;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaApi;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaAttivita;
import it.izs.apicoltura.apianagraficaazienda.ws.Apiazienda;
import it.izs.apicoltura.apianagraficaazienda.ws.WsApiAnagraficaAziende;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gucinterazioni.GucInterazioni;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.utils.DatabaseUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

import ext.aspcfs.modules.apicolture.actions.OperatoreAction;
import ext.aspcfs.modules.apicolture.actions.StabilimentoAction;

public class Operatore extends GenericBean {

	private int idOperatore;
	protected String ragioneSociale;
	private String partitaIva;
	private String codFiscale;
	private String telefono1;
	private String telefono2;
	private String email;
	private String fax;
	private String note;
	private java.sql.Timestamp dataInizio;
	private java.sql.Timestamp dataChiusura;
	private String domicilioDigitale;
	private SoggettoFisico rappLegale;
	private String action;
	private String tipo_impresa;
	private String tipo_societa;
	private String codiceInternoImpresa;
	private int idTipoAttivita;
	private String codiceAzienda;
	private int stato;
	private int idBda;
	private String codiceAziendaRegionale;
	private boolean flagProduzioneConLaboratorio;
	private boolean sincronizzatoBdn;
	private boolean flagScia = false;
	private int idRichiestaSuap;
	private boolean apicoltore ;
	private boolean delegato ;

	private String codice_fiscale_impresa ;
	private int id_utente_access_ext_delegato; 
	
	
	
	
	public String getcodice_fiscale_impresa() {
		return codice_fiscale_impresa;
	}

	public void setcodice_fiscale_impresa(String codice_fiscale_impresa) {
		this.codice_fiscale_impresa = codice_fiscale_impresa;
	}
	
	public int getid_utente_access_ext_delegato() {
		return id_utente_access_ext_delegato;
	}

	public void setid_utente_access_ext_delegato(int id_utente_access_ext_delegato) {
		this.id_utente_access_ext_delegato = id_utente_access_ext_delegato;
	}

	/**
	 * LISTA DELLE SEDI DELL'IMPRESA ( CONTIENE SOLO SEDE LEGALE)
	 */
	private SedeList listaSediOperatore = new SedeList();

	/**
	 * LISTA DEGLI APIARI
	 */
	private StabilimentoList listaStabilimenti = new StabilimentoList();
	
	
	/**
	 * LISTA DEGLI APIARI PER ESTRAZIONE
	 */
	private StabilimentoListEstrazione listaStabilimentiEstrazione = new StabilimentoListEstrazione();

	/**
	 * CONTIENE LE MODIFICHE FATTE AL RAPPRESENTANTE DELL'IMPRESA (COINCIDE CON
	 * IL PROPRIETARIOS)
	 */
	
	public boolean getApicoltore() {
		return apicoltore;
	}

	public void setDelegato(boolean delegato) {
		this.delegato = delegato;
	}
	
	public boolean getDelegato() {
		return delegato;
	}

	public void setApicoltore(boolean apicoltore) {
		this.apicoltore = apicoltore;
	}
	
	
	private SoggettoFisicoList storicoSoggettoFisico = new SoggettoFisicoList();

	public int getIdRichiestaSuap() {
		return idRichiestaSuap;
	}

	public void setIdRichiestaSuap(int idRichiestaSuap) {
		this.idRichiestaSuap = idRichiestaSuap;
	}

	/**
	 * CAMPI DI SERVIZIO
	 */
	private int modifiedBy = -1;
	private int enteredBy = -1;
	private String ipEnteredBy;
	private String ipModifiedBy;
	private java.sql.Timestamp entered;
	private java.sql.Timestamp modified;

	public boolean isFlagScia() {
		return flagScia;
	}

	public void setFlagScia(boolean flagScia) {
		this.flagScia = flagScia;
	}
	
	public java.sql.Timestamp getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(java.sql.Timestamp dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public boolean isFlagProduzioneConLaboratorio() {
		return flagProduzioneConLaboratorio;
	}

	public void setFlagProduzioneConLaboratorio(boolean flagProduzioneConLaboratorio) {
		this.flagProduzioneConLaboratorio = flagProduzioneConLaboratorio;
	}

	public int getIdBda() {
		return idBda;
	}

	public void setIdBda(int idBda) {
		this.idBda = idBda;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public int getIdTipoAttivita() {
		return idTipoAttivita;
	}

	public void setIdTipoAttivita(int idTipoAttivita) {
		this.idTipoAttivita = idTipoAttivita;
	}

	public void setIdTipoAttivita(String idTipoAttivita) {
		if (!"".equals(idTipoAttivita))
			this.idTipoAttivita = Integer.parseInt(idTipoAttivita);
	}

	public String getTipo_impresa() {
		return tipo_impresa;
	}

	public void setTipo_impresa(String tipo_impresa) {
		this.tipo_impresa = tipo_impresa;
	}

	public String getTipo_societa() {
		return tipo_societa;
	}

	public void setTipo_societa(String tipo_societa) {
		this.tipo_societa = tipo_societa;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Operatore(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	public Operatore() {
	}

	public Operatore(Connection db, int idOperatore) {
		try {
			queryRecordOperatoreSenzaStabilimenti(db, idOperatore);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndirizzoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Operatore(Connection db, String codiceAzienda, String  cfImpresa, String ragioneSociale, Integer idStato, ArrayList<Integer> idStatoExclude) {
		try {
			
			queryRecordOperatore(db, codiceAzienda, cfImpresa, ragioneSociale, idStato,idStatoExclude);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndirizzoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Operatore(Connection db, String  cfImpresa, String partitaIva) 
	{
		try 
		{
			queryRecordOperatore(db, cfImpresa, partitaIva);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (IndirizzoNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	public void deleteRichiesta(Connection db, int modifiedby) throws SQLException {
		String sql = "update apicoltura_imprese set trashed_date = current_timestamp , modifiedby = ? where id = ?";

		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, modifiedby);
		pst.setInt(2, this.getIdOperatore());
		pst.execute();

		Iterator<Stabilimento> itStab = listaStabilimenti.iterator();
		while (itStab.hasNext()) {
			Stabilimento curr = itStab.next();
			curr.deleteRichiesta(db, modifiedby);

		}

	}

	public void cessaAttivita(Connection db, String dataCessazione) throws SQLException, ParseException {
		String sql = "update apicoltura_imprese set data_fine = ? , data_cessazione=current_timestamp,cessato_da=?,stato=? where id =?";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp dataFine = new Timestamp(sdf.parse(dataCessazione).getTime());
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1, dataFine);
		pst.setInt(2, modifiedBy);
		pst.setInt(3, StabilimentoAction.API_STATO_CESSATO_DA_SINCRONIZZARE);
		pst.setInt(4, this.getIdOperatore());
		pst.execute();
		Iterator itStab = this.getListaStabilimenti().iterator();
		while (itStab.hasNext()) 
		{
			Stabilimento stab = (Stabilimento) itStab.next();
			stab.setModifiedBy(this.getModifiedBy());
			stab.setDataChiusura(dataFine);
			stab.cessaAttivita(db);
		}
		
		
		pst = db.prepareStatement(" update apicoltura_deleghe  set modified_by = ?,  modified = now(), data_cessazione_delega = ? where codice_fiscale_delegante = ? ");
		pst.setInt(1, modifiedBy);
		pst.setTimestamp(2, dataFine);
		pst.setString(3, this.getPartitaIva());
		pst.execute();
		
		pst = db.prepareStatement(" select * from dbi_disable_utente_ext((select access_ext.user_id from access_ext, contact_ext where contact_ext.contact_id = access_ext.contact_id and contact_ext.visibilita_delega = ? )::character varying) ");
		pst.setString(1, this.getCodFiscale());
		pst.execute();
		
		
		pst = db.prepareStatement(" select access_ext.username from access_ext, contact_ext where contact_ext.contact_id = access_ext.contact_id and contact_ext.visibilita_delega = ? ");
		pst.setString(1, this.getPartitaIva());
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		{
				GucInterazioni guc = new GucInterazioni();
				guc.getDbiDisableGuc(guc.recuperaConnessioneGuc(), rs.getString("username"));
		}
		
		
	}
	
	public void cessaAttivitaSincronizzata(Connection db) throws SQLException, ParseException {
		String sql = "update apicoltura_imprese set data_sincronizzata_cessazione = current_timestamp, stato=? where id =?";

		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, StabilimentoAction.API_STATO_CESSATO_SINCRONIZZATO);
		pst.setInt(2, this.getIdOperatore());
		pst.execute();
		Iterator itStab = this.getListaStabilimenti().iterator();
		while (itStab.hasNext()) 
		{
			Stabilimento stab = (Stabilimento) itStab.next();
			stab.setModifiedBy(this.getModifiedBy());
			stab.cessaAttivitaSincronizzata(db);
		}
	}
	
	public String sincronizzaCessazioneAttivita(Connection db, int idUtente) throws Exception
	{
		WsApiAnagraficaAttivita serviceAttivita = new WsApiAnagraficaAttivita();
		
		Iterator itStab = this.getListaStabilimenti().iterator();
		String esito = "";
		while (itStab.hasNext()) 
		{
			Stabilimento stab = (Stabilimento) itStab.next();
			stab.setDataChiusura(stab.getOperatore().getDataChiusura());
			if(stab.getIdBda()>0)
			{
				WsApiAnagraficaApi serviceApi = new WsApiAnagraficaApi();
				esito = serviceApi.modificaApiAnagraficaApiario(stab, db, idUtente);
				if(esito!=null && !esito.equals("OK"))
					return esito;
			}
			stab.cessaAttivitaSincronizzata(db);
		}
		if(this.getIdBda()>0)
		{
			esito = serviceAttivita.cessa(this, db, idUtente);
			if(esito!=null && !esito.equals("OK"))
				return esito;
			
			WsApiAnagraficaAziende serviceAziende = new WsApiAnagraficaAziende();
			esito = serviceAziende.cessa(this, db, idUtente);
			if(esito!=null && !esito.equals("OK"))
				return esito;
		}
		this.cessaAttivitaSincronizzata(db);
		
		return "OK";
	}

	public SoggettoFisicoList getStoricoSoggettoFisico() {
		return storicoSoggettoFisico;
	}

	public void setStoricoSoggettoFisico(SoggettoFisicoList storicoSoggettoFisico) {
		this.storicoSoggettoFisico = storicoSoggettoFisico;
	}

	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}

	public void setDomicilioDigitale(String domicilioDigitale) {
		this.domicilioDigitale = domicilioDigitale;
	}

	public StabilimentoList getListaStabilimenti() {
		return listaStabilimenti;
	}

	public void setListaStabilimenti(StabilimentoList listaStabilimenti) {
		this.listaStabilimenti = listaStabilimenti;
	}
	
	public StabilimentoListEstrazione getListaStabilimentiEstrazione() {
		return listaStabilimentiEstrazione;
	}

	public void setListaStabilimentiEstrazione(StabilimentoListEstrazione listaStabilimentiEstrazione) {
		this.listaStabilimentiEstrazione = listaStabilimentiEstrazione;
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
		this.listaSediOperatore.setIdOperatore(idOperatore);
		// this.listaIter.setIdOperatore(idOperatore);
	}

	public void setIdOperatore(String id) {
		this.idOperatore = new Integer(id).intValue();
		this.listaSediOperatore.setIdOperatore(new Integer(id).intValue());
		// this.listaIter.setIdOperatore(new Integer(id).intValue());
	}

	public String getRagioneSociale() {
		return (ragioneSociale != null ? ragioneSociale.trim() : "");
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPartitaIva() {
		return (partitaIva != null ? partitaIva.trim() : "");
	}

	public SedeList getListaSediOperatore() {
		return listaSediOperatore;
	}

	public void setListaSediOperatore(SedeList listaSediOperatore) {
		this.listaSediOperatore = listaSediOperatore;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getCodFiscale() {
		return (codFiscale != null ? codFiscale.trim() : "");

	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public String getIpEnteredBy() {
		return ipEnteredBy;
	}

	public void setIpEnteredBy(String ipEnteredBy) {
		this.ipEnteredBy = ipEnteredBy;
	}

	public String getIpModifiedBy() {
		return ipModifiedBy;
	}

	public void setIpModifiedBy(String ipModifiedBy) {
		this.ipModifiedBy = ipModifiedBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public SoggettoFisico getRappLegale() {
		return rappLegale;
	}

	public void setRappLegale(SoggettoFisico rappLegale) {
		this.rappLegale = new SoggettoFisico();
		this.rappLegale = rappLegale;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public java.sql.Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(java.sql.Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public void setDataInizio(String dataInizio) throws ParseException {
		if (dataInizio != null && !"".equals(dataInizio)) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataInizio = new Timestamp(sdf.parse(dataInizio).getTime());
		}

	}

	public java.sql.Timestamp getEntered() {
		return entered;
	}

	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}

	public String getCodiceInternoImpresa() {
		return codiceInternoImpresa;
	}

	public void setCodiceInternoImpresa(String codiceInternoImpresa) {
		this.codiceInternoImpresa = codiceInternoImpresa;
	}

	public boolean insert(Connection db, ActionContext context) throws SQLException {

		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			modifiedBy = enteredBy;

			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			idOperatore = DatabaseUtils.getNextSeqInt(db, context, "apicoltura_imprese", "id");

			sql.append("INSERT INTO apicoltura_imprese (ragione_sociale, codice_fiscale_impresa,note, partita_iva,codice_interno_impresa,tipo_attivita_apicoltura,telefono_fisso,faxt,telefono_cellulare,data_inizio,id_asl,stato,flag_scia,id_richiesta_suap");

			if (stato == StabilimentoAction.API_STATO_VALIDATO) {
				sql.append(",sincronizzato_bdn");
			}

			sql.append(",flag_laboratorio_annesso");

			if (idOperatore > -1) {
				sql.append(",id ");
			}

			if (enteredBy > -1) {
				sql.append(", enteredby");
			}

			if (modifiedBy > -1) {
				sql.append(", modifiedby");
			}

			if (ipEnteredBy != null && !ipEnteredBy.equals("")) {
				sql.append(", ipenteredby");
			}

			if (ipModifiedBy != null && !ipModifiedBy.equals("")) {
				sql.append(", ipmodifiedby");
			}
			if (codiceAzienda != null && !"".equals(codiceAzienda)) {
				sql.append(", codice_azienda");
			}

			sql.append(", entered, modified,domicilio_digitale");

			sql.append(")");

			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,(select codiceistatasl::int from comuni1 where id = ? ),?,?,?");

			if (stato == StabilimentoAction.API_STATO_VALIDATO) {
				sql.append(",?");
			}

			sql.append(",? ");

			if (idOperatore > -1) {
				sql.append(",? ");
			}

			if (enteredBy > -1) {
				sql.append(",?");
			}

			if (modifiedBy > -1) {
				sql.append(",?");
			}

			if (ipEnteredBy != null && !ipEnteredBy.equals("")) {
				sql.append(",?");
			}

			if (ipModifiedBy != null && !ipModifiedBy.equals("")) {
				sql.append(",?");
			}
			if (codiceAzienda != null && !"".equals(codiceAzienda)) {
				sql.append(",?");
			}

			sql.append(",current_timestamp,current_timestamp, ? ");

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setString(++i, this.getRagioneSociale());
			pst.setString(++i, this.getCodFiscale());
			pst.setString(++i, this.getNote());
			pst.setString(++i, this.getPartitaIva());
			pst.setString(++i, this.getCodiceInternoImpresa());
			pst.setInt(++i, getIdTipoAttivita());
			pst.setString(++i, this.getTelefono1());
			pst.setString(++i, this.getFax());
			pst.setString(++i, this.getTelefono2());
			pst.setTimestamp(++i, dataInizio);
			pst.setInt(++i, this.getSedeLegale().getComune());
			if (stato <= 0)
				pst.setInt(++i, StabilimentoAction.API_STATO_INCOMPLETO);
			else
				pst.setInt(++i, stato);
			pst.setBoolean(++i, flagScia);
			pst.setInt(++i, idRichiestaSuap);

			if (stato == StabilimentoAction.API_STATO_VALIDATO) {
				pst.setBoolean(++i, true);
			}

			pst.setBoolean(++i, flagProduzioneConLaboratorio);
			
			if (idOperatore > -1) {
				pst.setInt(++i, idOperatore);

			}
			if (enteredBy > -1) {
				pst.setInt(++i, this.enteredBy);
			}

			if (modifiedBy > -1) {
				pst.setInt(++i, this.modifiedBy);
			}

			if (ipEnteredBy != null && !ipEnteredBy.equals("")) {
				pst.setString(++i, this.ipEnteredBy);
			}

			if (ipModifiedBy != null && !ipModifiedBy.equals("")) {
				pst.setString(++i, this.ipModifiedBy);
			}
			if (codiceAzienda != null && !"".equals(codiceAzienda)) {
				pst.setString(++i, codiceAzienda);

			}

			pst.setString(++i, domicilioDigitale);

			pst.execute();
			pst.close();

			if (this.getRappLegale() != null)
				this.aggiungiRelazioneSoggettoFisico(db, 1, context);

			Indirizzo sedeLegale = null;

			SedeList listaInd = this.getListaSediOperatore();
			Iterator<Indirizzo> it = listaInd.iterator();
			while (it.hasNext()) {

				Indirizzo temp = it.next();
				this.aggiungiSede(db, temp, context);
			}

			if (doCommit) {
				db.commit();
			}
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

	public void queryRecordOperatore(Connection db, int idOperatore) throws SQLException, IndirizzoNotFoundException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select *, o.id as idOperatore from apicoltura_imprese o where o.id = ?");
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		listaSediOperatore.setIdOperatore(idOperatore);
		listaSediOperatore.setOnlyActive(1);
		listaSediOperatore.buildListSediOperatore(db);
		
		listaStabilimenti.setIdOperatore(idOperatore);
		listaStabilimenti.buildList(db);
		
		rs.close();
		pst.close();
		
		buildRappresentante(db);
		rs.close();
		pst.close();
	}
	
	
	
	public void queryRecordStabilimentiEstrazione(Connection db) throws SQLException, IndirizzoNotFoundException {

		listaStabilimentiEstrazione.setApicoltore(apicoltore);
		listaStabilimentiEstrazione.setDelegato(delegato);
		listaStabilimentiEstrazione.setid_utente_access_ext_delegato(id_utente_access_ext_delegato);
		if(codice_fiscale_impresa!=null && !codice_fiscale_impresa.equals(""))
			listaStabilimentiEstrazione.setcodice_fiscale_impresa(codice_fiscale_impresa);
		else
			listaStabilimentiEstrazione.setcodice_fiscale_impresa(null);
		listaStabilimentiEstrazione.setIdOperatore(idOperatore);
		if(apicoltore || delegato)
			listaStabilimentiEstrazione.buildList(db);
		
	}

	public void queryRecordOperatoreSenzaStabilimenti(Connection db, int idOperatore) throws SQLException,
			IndirizzoNotFoundException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select *, o.id as idOperatore from apicoltura_imprese o where o.id = ?");
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		listaSediOperatore.setIdOperatore(idOperatore);
		listaSediOperatore.setOnlyActive(1);
		listaSediOperatore.buildListSediOperatore(db);
		rs.close();
		pst.close();
		buildRappresentante(db);
		rs.close();
		pst.close();

	}

	public void queryRecordOperatoreStorico(Connection db, int idOperatore) throws SQLException,
			IndirizzoNotFoundException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select *, o.id as idOperatore from apicoltura_imprese o where o.id = ?");
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		listaSediOperatore.setIdOperatore(idOperatore);
		listaSediOperatore.setOnlyActive(1);
		listaSediOperatore.buildListSediOperatoreStorico(db);

		/*
		 * listaStabilimenti.setIdOperatore(idOperatore);
		 * listaStabilimenti.setFlag_dia(false);
		 * listaStabilimenti.buildList(db);
		 */

		rs.close();
		pst.close();
		storicoSoggettoFisico.setIdOperatore(idOperatore);
		storicoSoggettoFisico.buildListStorico(db);

		rs.close();
		pst.close();
	}

	public void updateSoggettoFisico(Connection db, ActionContext context) throws SQLException {
		if (this.getRappLegale() != null)
			this.aggiungiRelazioneSoggettoFisico(db, 1, context);
	}

	public void queryRecordOperatore(Connection db, String codiceAzienda, String cfImpresa, String ragioneSociale, Integer idStato, ArrayList<Integer> idStatoExclude) throws SQLException,
			IndirizzoNotFoundException {

		
		String query = "Select *, o.id as idOperatore from apicoltura_imprese o where (o.codice_fiscale_impresa ilike ?  or ? is null) and (o.codice_azienda ilike ?  or ? is null) and (o.ragione_sociale ilike ?  or ? is null) and (o.stato=? or ? is null) and trashed_date is null ";
		
		if(idStatoExclude!=null)
		{
			Iterator<Integer> statiToExclude = idStatoExclude.iterator();
			while(statiToExclude.hasNext())
				query += "and o.stato!= " + statiToExclude.next();
		}
		
		PreparedStatement pst = db.prepareStatement(query);
		pst.setString(1, cfImpresa);
		pst.setString(2, cfImpresa);
		pst.setString(3, codiceAzienda);
		pst.setString(4, codiceAzienda);
		pst.setString(5, ragioneSociale);
		pst.setString(6, ragioneSociale);
		if(idStato==null)
			pst.setNull(7, java.sql.Types.INTEGER);
		else
			pst.setInt(7, idStato);
		if(idStato==null)
			pst.setNull(8, java.sql.Types.INTEGER);
		else
			pst.setInt(8, idStato);
		
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}

		if (idOperatore > 0) {
			listaSediOperatore.setIdOperatore(idOperatore);
			listaSediOperatore.setOnlyActive(1);
			listaSediOperatore.buildListSediOperatore(db);
			rs.close();
			pst.close();
			buildRappresentante(db);
			rs.close();
			pst.close();
		}

	}
	
	public void queryRecordOperatore(Connection db, String cfImpresa, String partitaIva) throws SQLException, IndirizzoNotFoundException 
	{

		PreparedStatement pst = db.prepareStatement("Select *, o.id as idOperatore, sogg.codice_fiscale as codiceFiscaleProprietario " +
													"from apicoltura_imprese o " + 
													"left join apicoltura_rel_impresa_soggetto_fisico rel on rel.enabled and rel.id_apicoltura_imprese = o.id and rel.data_fine is null " +
													"left join opu_soggetto_fisico sogg on sogg.id = rel.id_soggetto_fisico and sogg.trashed_date is null " +
													"where (o.codice_fiscale_impresa ilike ? or o.partita_iva = ? or sogg.codice_fiscale ilike ?) and o.trashed_date is null");
		pst.setString(1, cfImpresa);
		pst.setString(2, partitaIva);
		pst.setString(3, cfImpresa);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}
		
		if (idOperatore > 0) 
		{
			listaSediOperatore.setIdOperatore(idOperatore);
			listaSediOperatore.setOnlyActive(1);
			listaSediOperatore.buildListSediOperatore(db);
			rs.close();
			pst.close();
			buildRappresentante(db);
			rs.close();
			pst.close();
		}
		
		}
	
	public void queryRecordOperatoreCfImpresaAttivo(Connection db, String cfImpresa) throws SQLException,
	IndirizzoNotFoundException {

PreparedStatement pst = db
		.prepareStatement("Select *, o.id as idOperatore from apicoltura_imprese o where o.codice_fiscale_impresa ilike ? and o.stato !=4 and o.stato !=3 and trashed_date is null");
pst.setString(1, cfImpresa);
ResultSet rs = pst.executeQuery();
if (rs.next()) {
	this.buildRecord(rs);
}

if (idOperatore > 0) {
	listaSediOperatore.setIdOperatore(idOperatore);
	listaSediOperatore.setOnlyActive(1);
	listaSediOperatore.buildListSediOperatore(db);
	rs.close();
	pst.close();
	buildRappresentante(db);
	rs.close();
	pst.close();
}

}

	public void setRequestItems(ActionContext context) {

		rappLegale = new SoggettoFisico(context.getRequest());
	}

	public void update(Connection db, ActionContext context) {

		String sql = "update apicoltura_imprese set ragione_sociale =?, tipo_attivita_apicoltura = ? ,telefono_fisso=?,telefono_cellulare=?,domicilio_digitale=?,modified=current_timestamp,modifiedby=?,stato=?,faxt=? where id =?";
		Indirizzo sl = this.getSedeLegale();
		int i = 0;
		try {

			sl.setIdIndirizzo(-1);
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(++i, ragioneSociale);

			pst.setInt(++i, idTipoAttivita);
			pst.setString(++i, telefono1);
			pst.setString(++i, telefono2);
			pst.setString(++i, domicilioDigitale);
			pst.setInt(++i, modifiedBy);
			pst.setInt(++i, StabilimentoAction.API_STATO_DA_NOTIFICARE);
			pst.setString(++i, fax);
			pst.setInt(++i, idOperatore);
			pst.execute();

			sl.insert(db, context);
			this.aggiungiSede(db, sl, context);
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		this.setIdOperatore(rs.getInt("idOperatore"));
		this.setRagioneSociale(rs.getString("ragione_sociale"));
		this.setCodFiscale(rs.getString("codice_fiscale_impresa"));
		this.setPartitaIva(rs.getString("partita_iva"));
		this.setEnteredBy(rs.getInt("enteredby"));
		this.setModifiedBy(rs.getInt("modifiedby"));
		this.setEntered(rs.getTimestamp("entered"));
		this.setModified(rs.getTimestamp("modified"));
		this.setIdTipoAttivita(rs.getInt("tipo_attivita_apicoltura"));
		this.setDomicilioDigitale(rs.getString("domicilio_digitale"));
		this.setTelefono1(rs.getString("telefono_fisso"));
		this.setTelefono2(rs.getString("telefono_cellulare"));
		this.setFax(rs.getString("faxt"));
		this.setCodiceAzienda(rs.getString("codice_azienda"));
		this.setIdAsl(rs.getInt("id_asl"));
		this.setDataInizio(rs.getTimestamp("data_inizio"));
		this.setDataChiusura(rs.getTimestamp("data_fine"));
		this.setStato(rs.getInt("stato"));
		idBda = rs.getInt("id_bda");
		this.setFlagProduzioneConLaboratorio(rs.getBoolean("flag_laboratorio_annesso"));
		this.setCodiceAziendaRegionale(rs.getString("codice_azienda_regionale"));
		this.sincronizzatoBdn = rs.getBoolean("sincronizzato_bdn");

		this.setFlagScia(rs.getBoolean("flag_scia"));
		this.setIdRichiestaSuap(rs.getInt("id_richiesta_suap"));
	}

	public boolean isSincronizzatoBdn() {
		return sincronizzatoBdn;
	}

	public void setSincronizzatoBdn(boolean sincronizzatoBdn) {
		this.sincronizzatoBdn = sincronizzatoBdn;
	}

	public boolean aggiungiRelazioneSoggettoFisico(Connection db, int tipoLegame, ActionContext context)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			// Controllare se c'e' gia' soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db, context, "apicoltura_rel_impresa_soggetto_fisico", "id");

			sql.append("update apicoltura_rel_impresa_soggetto_fisico set enabled = false,data_fine=current_date where id_apicoltura_imprese =?; INSERT INTO apicoltura_rel_impresa_soggetto_fisico (");

			if (idRelazione > -1)
				sql.append("id,");

			sql.append("id_apicoltura_imprese, id_soggetto_fisico, tipo_soggetto_fisico, enabled, data_inizio )");

			if (idRelazione > -1) {
				sql.append("VALUES (?");
			}

			sql.append(",?,?,?,true,?");
			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idOperatore);
			if (idRelazione > -1) {
				pst.setInt(++i, idRelazione);
			}

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

			pst.setInt(++i, idOperatore);
			pst.setInt(++i, this.getRappLegale().getIdSoggetto());
			pst.setInt(++i, tipoLegame);
			pst.setTimestamp(++i, dataInizio);

			pst.execute();
			pst.close();

			/*
			 * Aggiornamento dati soggetto fisico se cambiano tutti i dati
			 * tranne quelli per il calcolo del codice fiscale
			 */

			/**
			 * Commentato da Veronica perche' dalla maschera di inserimento non
			 * si possono modificare in ogni caso i dati del soggetto
			 */
			// this.getRappLegale().update(db);
		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public boolean aggiornaRelazioneSoggettoFisico(Connection db, int tipoLegame, ActionContext context)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db
					.prepareStatement("Update apicoltura_rel_impresa_soggetto_fisico set enabled=false where "
							+ "id_apicoltura_imprese = ? ");

			int i = 0;
			pst.setInt(++i, idOperatore);

			int resultCount = pst.executeUpdate();
			pst.close();

			this.aggiungiRelazioneSoggettoFisico(db, 1, context);

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;
	}
	
	
	public boolean aggiornaRagioneSocialePiva(Connection db, int idUtente)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db
					.prepareStatement("Update apicoltura_imprese set note_hd = concat(note_hd, '. Modifica titolare da ' || ragione_sociale || ' a ' || ? || ' in data ' || current_timestamp || '.'), modified = current_timestamp, modifiedby = ?, ragione_sociale = ?, partita_iva = ? where "
							+ "id = ? ");

			int i = 0;
			pst.setString(++i, ragioneSociale);
			pst.setInt(++i, idUtente);
			pst.setString(++i, ragioneSociale);
			pst.setString(++i, partitaIva);
			pst.setInt(++i, idOperatore);

			pst.executeUpdate();
			pst.close();

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;
	}

	public boolean aggiungiSede(Connection db, Indirizzo indirizzo, ActionContext context) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			// Controllare se c'e' gia' soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db, context, "apicoltura_relazione_imprese_sede_legale", "id");

			sql.append("update apicoltura_relazione_imprese_sede_legale set enabled = false where id_apicoltura_imprese =? ;"
					+ "INSERT INTO apicoltura_relazione_imprese_sede_legale (");

			sql.append("id_apicoltura_imprese, id_indirizzo, tipologia_sede, stato_sede,enabled,modified_by,id");

			sql.append(")");

			sql.append("VALUES (?,?,?, ?,true,?,?");

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idOperatore);

			pst.setInt(++i, idOperatore);
			pst.setInt(++i, indirizzo.getIdIndirizzo());
			pst.setInt(++i, indirizzo.getTipologiaSede());
			pst.setInt(++i, 1);
			pst.setInt(++i, indirizzo.getModifiedBy());
			pst.setInt(++i, idRelazione);

			pst.execute();
			pst.close();

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public boolean cancellaSedeLegale(Connection db, Indirizzo indirizzo) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			sql.append("UPDATE apicoltura_relazione_imprese_sede_legale SET enabled = false WHERE id_apicoltura_imprese = ?  and id_indirizzo=? ");
			int i = 0;

			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idOperatore);
			pst.setInt(++i, indirizzo.getIdIndirizzo());
			pst.execute();
			pst.close();

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public boolean modificaSedeLegale(Connection db, Indirizzo indirizzo) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			sql.append("UPDATE apicoltura_relazione_imprese_sede_legale SET id_indirizzo = ? WHERE id_apicoltura_imprese = ? ");
			int i = 0;

			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, indirizzo.getIdIndirizzo());
			pst.setInt(++i, idOperatore);
			pst.execute();
			pst.close();

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public Indirizzo getSedeLegale() {
		Indirizzo sedeLegale = null;

		SedeList listaInd = this.getListaSediOperatore();
		Iterator<Indirizzo> it = listaInd.iterator();
		while (it.hasNext()) {

			Indirizzo temp = it.next();
			if (temp.getTipologiaSede() == 1) {
				sedeLegale = temp;
			}
		}

		return sedeLegale;
	}

	public Indirizzo getSedeOperativa() {
		Indirizzo sedeOperativa = null;

		SedeList listaInd = this.getListaSediOperatore();
		Iterator<Indirizzo> it = listaInd.iterator();
		while (it.hasNext()) {

			Indirizzo temp = it.next();
			if (temp.getTipologiaSede() == 2) {
				sedeOperativa = temp;
			}
		}

		return sedeOperativa;
	}

	public boolean aggiornaRelazioneSede(Connection db, Indirizzo oldIndirizzo, Indirizzo newIndirizzo,
			ActionContext context) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db
					.prepareStatement("Update apicoltura_relazione_imprese_sede_legale set stato_sede = 2 where "
							+ "stato_sede = 1 and " + "id_apicoltura_imprese = ? " + "and tipologia_sede = ? ");

			int i = 0;
			pst.setInt(++i, idOperatore);
			pst.setInt(++i, oldIndirizzo.getTipologiaSede());
			int resultCount = pst.executeUpdate();
			pst.close();

			this.aggiungiSede(db, newIndirizzo, context);

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;
	}

	public void updateStato(Connection db, int idOperatore) {
		try {
			int idStato = -1;
			PreparedStatement pst = db.prepareStatement("select stato from apicoltura_imprese where id =?");
			ResultSet rs = null;
			pst.setInt(1, idOperatore);
			rs = pst.executeQuery();
			if (rs.next())
				idStato = rs.getInt(1);

			if (idStato == StabilimentoAction.API_STATO_INCOMPLETO) {

				pst = db.prepareStatement("update apicoltura_imprese set stato =? where id = ? ");
				pst.setInt(1, StabilimentoAction.API_STATO_DA_NOTIFICARE);
				pst.setInt(2, idOperatore);
				pst.execute();

			}

		} 
		catch (Exception e) 
		{
			System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] ECCEZIONE IN UPDATE STATO OPERATORE " + e.getMessage()); 
			e.printStackTrace();

		}

	}
	
	public void updateTelefono(Connection db, int idOperatore, String telefono) {
		try {
			PreparedStatement pst = db.prepareStatement("update apicoltura_imprese set telefono_fisso =? where id = ? ");
			pst.setString(1, telefono);
			pst.setInt(2, idOperatore);
			pst.execute();


		} 
		catch (Exception e) 
		{
			System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] ECCEZIONE IN UPDATE TELEFONO " + ""); 
			e.printStackTrace();
		}

	}

	public boolean esegui_voltura(Connection db) {

		PreparedStatement pst = null;
		int i = 0;
		try {
			pst = db.prepareStatement("update apicoltura_imprese set ragione_sociale = ? , partita_iva = ? where id = ?;update apicoltura_rel_impresa_soggetto_fisico set id_soggetto_fisico = ? where id = ?");
			pst.setString(++i, ragioneSociale);
			pst.setString(++i, partitaIva);
			pst.setInt(++i, this.getIdOperatore());
			pst.setInt(++i, this.getRappLegale().getIdSoggetto());
			pst.setInt(++i, this.getIdOperatore());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	protected void buildRappresentanteLegale(ResultSet rs) {

		try {
			rappLegale = new SoggettoFisico(rs);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// TODO da eliminare
	public void buildRappresentante(Connection db) {
		SoggettoFisico rapp = new SoggettoFisico();

		StringBuffer sql = new StringBuffer();
		try {

			StringBuffer sqlSelect = new StringBuffer("");
			sqlSelect
					.append("SELECT distinct max(storico.id) as id_soggetto_storico ,"
							+ "o.nome,o.cognome,"
							+ "o.codice_fiscale,o.comune_nascita,o.provincia_nascita,'R'||comuni1.codiceasl_bdn as codice_asl,o.data_nascita,o.sesso,o.telefono1,o.telefono,"
							+ "o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera,"
							+ " o.id as id_soggetto ,"
							+ "i.id,i.comune,i.comune_testo,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,comuni1.cod_comune as istat_comune,lp.cod_provincia as sigla_provincia,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune,"
							+ "lp.description as descrizione_provincia   "
							+ "FROM opu_soggetto_fisico o "
							+ "left join opu_soggetto_fisico_storico storico on o.id = storico.id_opu_soggetto_fisico "
							+ "left join opu_indirizzo i on o.indirizzo_id=i.id "
							+ "left join comuni1 on (comuni1.id = i.comune) "

							+ "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
							+ "left join lookup_province lp on lp.code = comuni1.cod_provincia::int "
							+ "join apicoltura_rel_impresa_soggetto_fisico os on (o.id = os.id_soggetto_fisico) and os.enabled "
							+ "join apicoltura_imprese op on (os.id_apicoltura_imprese = op.id)  where os.id_apicoltura_imprese = ? "
							+ "group by o.nome,o.cognome,'R'||comuni1.codiceasl_bdn,"
							+ "o.codice_fiscale,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono,"
							+ "o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera,"
							+ " o.id  ,"
							+ "i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code ,comuni1.cod_comune,lp.cod_provincia ,asl.description ,comuni1.cod_provincia,comuni1.nome ,"
							+ "lp.description ");

			PreparedStatement pst = db.prepareStatement(sqlSelect.toString());

			int i = 0;
			pst.setInt(++i, idOperatore);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRappresentanteLegale(rs);
			}

		} catch (SQLException e) {

		} finally {

		}

	}

	public boolean checkEsistenzaOperatore(Connection db) {
		boolean exist = false;
		String query = "select *  from apicoltura_imprese o "
				+ "where o.trashed_date is null and o.codice_fiscale_impresa ilike ? and o.stato!= "
				+ StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE + " and o.stato!= "
				+ StabilimentoAction.API_STATO_CESSATO + " and o.stato!= "
						+ StabilimentoAction.API_STATO_CESSATO_SINCRONIZZATO + " and o.stato!= "
								+ StabilimentoAction.API_STATO_CESSATO_DA_SINCRONIZZARE;

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setString(++i, getRappLegale().getCodFiscale());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				this.idOperatore = rs.getInt("id");
				exist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return exist;
	}

	public List<Operatore> checkEsistenzaOperatoreSuap(Connection db) throws IndirizzoNotFoundException {

		String query = "select *,o.id as idOperatore   from apicoltura_imprese o " + "where o.partita_iva = ? ";

		Operatore operatore = null;
		List<Operatore> listaOp = new ArrayList<Operatore>();
		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setString(++i, this.partitaIva);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				operatore = new Operatore();
				operatore.queryRecordOperatore(db, rs.getInt("idOperatore"));
				listaOp.add(operatore);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaOp;
	}

	public int compareTo(Operatore otherOpertore) {

		String denominazione = otherOpertore.getRagioneSociale();
		String pIva = otherOpertore.getPartitaIva();
		String codFiscale = otherOpertore.getCodFiscale();

		SoggettoFisico legaleRapp = otherOpertore.getRappLegale();
		Indirizzo indirizzo = otherOpertore.getSedeLegale();

		if (this.getRagioneSociale().equalsIgnoreCase(denominazione) && this.getPartitaIva().equalsIgnoreCase(pIva)
				&& this.getCodFiscale().equalsIgnoreCase(codFiscale) && this.getRappLegale().compareTo(legaleRapp) == 0
				&& this.getSedeLegale().compareTo(indirizzo) == 0)
			return 0;
		return 1;

	}

	public HashMap<String, Object> getHashmapOperatore() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		Field[] campi = this.getClass().getDeclaredFields();
		Method[] metodi = this.getClass().getMethods();
		for (int i = 0; i < campi.length; i++) {
			String nomeCampo = campi[i].getName();

			if (!nomeCampo.equalsIgnoreCase("domicilioDigitale") && !nomeCampo.equalsIgnoreCase("rappLegale")
					&& !nomeCampo.equalsIgnoreCase("storicoSoggettoFisico")
					&& !nomeCampo.equalsIgnoreCase("listaSediOperatore")
					&& !nomeCampo.equalsIgnoreCase("listaStabilimenti")
					&& !nomeCampo.equalsIgnoreCase("phoneNumberList")
					&& !nomeCampo.equalsIgnoreCase("emailAddressList")) {
				for (int j = 0; j < metodi.length; j++) {

					if (metodi[j].getName().equalsIgnoreCase("GET" + nomeCampo)) {

						map.put(nomeCampo, new String("" + metodi[j].invoke(this)));

					}
				}

			}

		}

		if (rappLegale != null) {

			JSONObject rapp = new JSONObject(rappLegale.getHashmapSoggettoFisico());
			map.put("rapplegale", rapp);
		}

		if (getSedeLegale() != null) {

			JSONObject sedeleg = new JSONObject(getSedeLegale().getHashmapIndirizzo());
			map.put("sedelegale", sedeleg);

		}

		return map;

	}

	public String toString() {

		JSONArray array = new JSONArray();
		HashMap<String, Object> obj = new HashMap<String, Object>();

		String txt = "";

		String ret = "";
		ret += "{_id_:_" + idOperatore + "_,";
		obj.put("_id_", idOperatore);

		ret += "_ragionesociale_:_" + ragioneSociale + "_,";
		obj.put("_ragionesociale_", ragioneSociale);

		ret += "_partitaiva_:_" + partitaIva + "_,";
		obj.put("_partitaiva_", partitaIva);

		ret += "_codicefiscaleimpresa_:_" + codiceInternoImpresa + "_,";
		obj.put("_codicefiscaleimpresa_", codiceInternoImpresa);

		ret += "_proprietario_:" + rappLegale.toString() + "}";

		return ret;

	}

	public String getCodiceAziendaRegionale() {
		return codiceAziendaRegionale;
	}

	public void setCodiceAziendaRegionale(String codiceAziendaRegionale) {
		this.codiceAziendaRegionale = codiceAziendaRegionale;
	}

	public void sincronizzaBdn(Connection db, int modifiedby) throws SQLException {
		String sql = "update apicoltura_imprese set sincronizzato_bdn = true , sincronizzato_da = ?,data_sincronizzazione = current_timestamp where id = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, modifiedby);
		pst.setInt(2, this.getIdOperatore());
		pst.execute();
	}

	public void aggiornaDatiApicoltore(Connection db) throws SQLException {
		int i = 0;
		PreparedStatement pst = null;

		try {
			pst = db.prepareStatement("update apicoltura_imprese set data_inizio =?,  telefono_fisso=?, telefono_cellulare=?,  tipo_attivita_apicoltura=?,  domicilio_digitale=?,flag_laboratorio_annesso=?  where id =?");
			pst.setTimestamp(++i, dataInizio);
			pst.setString(++i, telefono1);
			pst.setString(++i, telefono2);
			pst.setInt(++i, idTipoAttivita);
			pst.setString(++i, domicilioDigitale);
			pst.setBoolean(++i, flagProduzioneConLaboratorio);
			pst.setInt(++i, idOperatore);
			pst.execute();
		} catch (SQLException e) {
			throw e;
		}

	}

	public Operatore(Apiatt attivitaBdn, Connection db, ActionContext context) throws SQLException {

		this.ragioneSociale = attivitaBdn.getDenominazione();
		this.codiceAzienda = attivitaBdn.getAziendaCodice();
		this.codFiscale = attivitaBdn.getPropIdFiscale();
		this.telefono1 = attivitaBdn.getNumTelFisso();
		this.telefono2 = attivitaBdn.getNumTelMobile();
		this.domicilioDigitale = attivitaBdn.getEmail();
		this.setDataInizio(new Timestamp(attivitaBdn.getDtInizioAttivita().getMillisecond()));
		if (attivitaBdn.getDtCessazione() != null)
			this.setDataChiusura(new Timestamp(attivitaBdn.getDtCessazione().getMillisecond()));

		PreparedStatement pst = db
				.prepareStatement("select code from apicoltura_lookup_tipo_attivita where codice_attivita =? ");
		pst.setString(1, attivitaBdn.getApitipattCodice());
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			this.setIdTipoAttivita(rs.getString(1));

		Indirizzo sedeLegale = new Indirizzo();
		sedeLegale.setCap(attivitaBdn.getComSlCap());
		sedeLegale.setVia(attivitaBdn.getIndirizzoSl());
		sedeLegale.setProvincia(attivitaBdn.getComSlProSigla());
		sedeLegale.setComune(ComuniAnagrafica.getIdComune(attivitaBdn.getComSlDescrizione(), db));
		sedeLegale.setComuneTesto(attivitaBdn.getComSlDescrizione());
		sedeLegale.setTipologiaSede(1);
		sedeLegale.insert(db, context);

		this.getListaSediOperatore().add(sedeLegale);

	}

	
	public void aggiornaDatiLaboratorio(Connection db, String note, int userId) {
		String noteHd = "[Laboratorio di smielatura modificato a: "+flagProduzioneConLaboratorio+"; in data: "+ (new Timestamp(System.currentTimeMillis())) +"; da utente: "+userId+"; con note: "+note+"]";
		int i = 0 ;
		PreparedStatement pst = null;
		try {
			pst =db.prepareStatement("update apicoltura_imprese set note_hd = concat(note_hd, ?), flag_laboratorio_annesso = ? where id = ?;");
			pst.setString(++i, noteHd);
			pst.setBoolean(++i, flagProduzioneConLaboratorio);
			pst.setInt(++i, idOperatore);
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void aggiornaRecapitiApicoltore(Connection db) throws SQLException {
		int i = 0;
		PreparedStatement pst = null;

		try {
			pst = db.prepareStatement("update apicoltura_imprese set telefono_fisso=?, telefono_cellulare=?, domicilio_digitale=?, faxt = ?  where id =?");
			pst.setString(++i, telefono1);
			pst.setString(++i, telefono2);
			pst.setString(++i, domicilioDigitale);
			pst.setString(++i, fax);
			pst.setInt(++i, idOperatore);
			pst.execute();
		} catch (SQLException e) {
			throw e;
		}

	}
	
}
