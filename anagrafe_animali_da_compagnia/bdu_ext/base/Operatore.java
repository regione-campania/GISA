/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationAddressList;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddressList;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumberList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Operatore extends GenericBean {

	public static final int BDU_PRIVATO = 1;
	public static final int BDU_SINDACO = 3;
	public static final int BDU_SINDACO_FR = 7;
	public static final int BDU_COLONIA = 9;
	public static final int BDU_CANILE = 5;

	private static Logger log = Logger
			.getLogger(org.aspcfs.modules.opu.base.Operatore.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}

	private int idOperatore;
	private String ragioneSociale;
	private String partitaIva;
	private String codFiscale;
	private String telefono1;
	private String telefono2;
	private int owner;
	private String email;
	private String fax;
	private String note;
	private int modifiedBy = -1;
	private int enteredBy = -1;
	private String ipEnteredBy;
	private String ipModifiedBy;
	private java.sql.Timestamp dataInizio;
	private java.sql.Timestamp entered;
	private java.sql.Timestamp modified;

	private SoggettoFisico rappLegale;
	// private Indirizzo sedeLegale;

	private SedeList listaSediOperatore = new SedeList();

	private StabilimentoList listaStabilimenti = new StabilimentoList();

	private OperatoreAddressList addressList = new OperatoreAddressList();
	private OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
	protected OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();

	public Operatore() {
	}

	public StabilimentoList getListaStabilimenti() {
		return listaStabilimenti;
	}

	public void setListaStabilimenti(StabilimentoList listaStabilimenti) {
		this.listaStabilimenti = listaStabilimenti;
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
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPartitaIva() {
		return partitaIva;
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
		return codFiscale;
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

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
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

	/*
	 * public Indirizzo getSedeLegale() { return sedeLegale; }
	 * 
	 * 
	 * public void setSedeLegale(Indirizzo sedeLegale) { this.sedeLegale = new
	 * Indirizzo(); this.sedeLegale = sedeLegale; }
	 */

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public OperatoreAddressList getAddressList() {
		return addressList;
	}

	public void setAddressList(OperatoreAddressList addressList) {
		this.addressList = addressList;
	}

	public OrganizationPhoneNumberList getPhoneNumberList() {
		return phoneNumberList;
	}

	public void setPhoneNumberList(OrganizationPhoneNumberList phoneNumberList) {
		this.phoneNumberList = phoneNumberList;
	}

	public OrganizationEmailAddressList getEmailAddressList() {
		return emailAddressList;
	}

	public void setEmailAddressList(
			OrganizationEmailAddressList emailAddressList) {
		this.emailAddressList = emailAddressList;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		Operatore.log = log;
	}

	public java.sql.Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(java.sql.Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public java.sql.Timestamp getEntered() {
		return entered;
	}

	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}

	public void queryRecordOperatore(Connection db, int idOperatore)
			throws SQLException, IndirizzoNotFoundException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select *, o.id as idOperatore from opu_operatore o where o.id = ?");
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
		listaSediOperatore.buildListOperatore(db);

		listaStabilimenti.setIdOperatore(idOperatore);
		listaStabilimenti.buildList(db);

		rs.close();
		pst.close();
		buildRappresentante(db);
		rs.close();
		pst.close();
	}

	public void queryRecordOperatorebyIdLineaProduttiva(Connection db,
			int idLineaProduttiva) throws SQLException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select o.*, o.id as idOperatore "
						+ "from opu_operatore o  "
						+ "join opu_stabilimento s on s.id_operatore = o.id "
						+ "join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id "
						+ "where r.id = ? and r.trashed_date is null ");
		pst.setInt(1, idLineaProduttiva);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		listaSediOperatore.setIdOperatore(idOperatore);
		listaSediOperatore.setOnlyActive(1);
		listaSediOperatore.buildListOperatore(db);

		listaStabilimenti.setIdOperatore(idOperatore);
		listaStabilimenti.buildStabilimento(db, idLineaProduttiva);

		rs.close();
		pst.close();
		buildRappresentante(db);
		rs.close();
		pst.close();
	}

	/*
	 * public Operatore(Connection db, int idOperatore) throws SQLException{
	 * 
	 * if (idOperatore == -1){ throw new SQLException("Invalid Account"); }
	 * 
	 * PreparedStatement pst =db.prepareStatement(
	 * "Select *, o.id as idOperatore from operatore o where o.id = ?");
	 * pst.setInt(1, idOperatore); ResultSet rs = DatabaseUtils.executeQuery(db,
	 * pst, log); if (rs.next()) { buildRecord(rs); }
	 * 
	 * if (idOperatore == -1) { throw new
	 * SQLException(Constants.NOT_FOUND_ERROR); }
	 * 
	 * listaSediOperatore.setIdOperatore(idOperatore);
	 * listaSediOperatore.setOnlyActive(1);
	 * listaSediOperatore.buildListOperatore(db);
	 * 
	 * listaStabilimenti.setIdOperatore(idOperatore);
	 * listaStabilimenti.setIdAsl(3); listaStabilimenti.buildList(db);
	 * 
	 * rs.close(); pst.close(); buildRappresentante(db); rs.close();
	 * pst.close(); }
	 */

	public int update(Connection db) throws SQLException,
			IndirizzoNotFoundException {
	//	System.out.println("INIZIO UPDATE OPERATORE");
		int resultCount = 0;
		
//		System.out.println("Dati operatore: ");
//		System.out.println("Ragione Sociale: "+ragioneSociale);
//		System.out.println("CF Impresa: "+codFiscale);
//		System.out.println("IVA: "+partitaIva);
//		System.out.println("Note: "+note);
		
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();

		// Operatore old = new Operatore( db, idOperatore);

		Operatore old = new Operatore();
		old.queryRecordOperatore(db, idOperatore);
		sql.append("UPDATE opu_operatore o "
				//+ "SET ragione_sociale= ?, partita_iva= ?, codice_fiscale_impresa = ?, "
				+ "SET note= ? ");

		if (ipModifiedBy != null && !ipModifiedBy.equals("")) 
			sql.append(", ipmodifiedby = ?");
		

		if (modifiedBy > -1) 
			sql.append(", modifiedby = ?");
				
		if (ragioneSociale!=null && !ragioneSociale.equals(""))
			sql.append(",ragione_sociale= ?");
		
		if (partitaIva!=null && !partitaIva.equals(""))
			sql.append(",partita_iva= ?");
		
		if (codFiscale!=null && !codFiscale.equals(""))
			sql.append(",codice_fiscale_impresa= ?");
		
		sql.append(" where o.id = ?");

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setString(++i, note);
		
		if (ipModifiedBy != null && !ipModifiedBy.equals("")) 
			pst.setString(++i, ipModifiedBy);
		
		if (modifiedBy > -1) 
			pst.setInt(++i, modifiedBy);
		
		if (ragioneSociale!=null && !ragioneSociale.equals(""))
			pst.setString(++i, ragioneSociale);
		
		if (partitaIva!=null && !partitaIva.equals(""))
			pst.setString(++i, partitaIva);
		
		if (codFiscale!=null && !codFiscale.equals(""))
			pst.setString(++i, codFiscale);
		
		
		pst.setInt(++i, idOperatore);

		resultCount = pst.executeUpdate();
		pst.close();
		
	
		
		
		ResultSet rs = pst.executeQuery();
		String newId="";
		while (rs.next()) {
			   newId = rs.getString("id");
		//	  System.out.println("Nuovo Id: "+newId + "\n");
			}
		
		
		if (this.getRappLegale() != null && old.getRappLegale() != null) {
			// controllo se è cambiato soggetto fisico rappresentante legale, se
			// sì lo aggiorno disabilitando il vecchio record
			if (old.getRappLegale().getIdSoggetto() != this.getRappLegale()
					.getIdSoggetto()) {
				this.aggiornaRelazioneSoggettoFisico(db, 1);
			} else {
				this.getRappLegale().update(db);
			}
		} else {
			this.aggiungiRelazioneSoggettoFisico(db, 1);
		}

		if (this.getSedeLegale() != null && old.getSedeLegale() != null) {

			if (old.getSedeLegale().getIdIndirizzo() != this.getSedeLegale()
					.getIdIndirizzo()) {
				this.aggiornaRelazioneSede(db, old.getSedeLegale(), this
						.getSedeLegale());
			}
		} else if (this.getSedeLegale() != null) {
			this.aggiungiSede(db, this.getSedeLegale());
		}

		/*
		 * if (old.getSedeOperativa().getIdIndirizzo() !=
		 * this.getSedeOperativa().getIdIndirizzo()){
		 * this.aggiornaRelazioneSede(db, old.getSedeOperativa(),
		 * this.getSedeOperativa()); }
		 */
		//System.out.println("FINE UPDATE");
		return resultCount;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			modifiedBy = enteredBy;

			
			idOperatore = DatabaseUtils.getNextSeq(db, "opu_operatore_id_seq");
			sql.append("INSERT INTO opu_operatore (");

			sql.append("ragione_sociale, codice_fiscale_impresa, "
					+ "note, partita_iva");

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

			sql.append(")");

			sql.append("VALUES (?,?,?,?");

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

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setString(++i, this.getRagioneSociale());
			pst.setString(++i, this.getCodFiscale());
			pst.setString(++i, this.getNote());
			pst.setString(++i, this.getPartitaIva());
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

			pst.execute();
			pst.close();

			this.idOperatore = DatabaseUtils.getCurrVal(db,
					"opu_operatore_id_seq", idOperatore);

			if (this.getRappLegale() != null)
				this.aggiungiRelazioneSoggettoFisico(db, 1);

			Indirizzo sedeLegale = null;

			SedeList listaInd = this.getListaSediOperatore();
			Iterator<Indirizzo> it = listaInd.iterator();
			while (it.hasNext()) {

				Indirizzo temp = it.next();
				this.aggiungiSede(db, temp);
			}

			// Rappresentante Legale
			// SoggettoFisico rappresentante_legale = this.getRappLegale();
			// Controllare se già esiste la persona censita, se no, censirla
			// (Attraverso il CF?)
			// rappresentante_legale.insert(db, idOperatore, 1); //Creare lookup
			// tipo_soggetto_fisico

			// Aggiungi sedi se ce ne sono
			// Insert the addresses if there are any
			/*
			 * Iterator iteratorSedi = getAddressList().iterator(); while
			 * (iteratorSedi.hasNext()) {
			 * 
			 * OperatoreAddress thisAddress = (OperatoreAddress)
			 * iteratorSedi.next(); //thisAddress.insert(db, this.getOrgId(),
			 * this.getEnteredBy());
			 * 
			 * 
			 * if((thisAddress.getCity()!=null) &&
			 * !(thisAddress.getCity().equals(""))) { thisAddress.process( db,
			 * idOperatore, this.getEnteredBy(), this.getModifiedBy()); } }
			 */

			
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} 
		return true;

	}

	public void setRequestItems(ActionContext context) {
		phoneNumberList = new OrganizationPhoneNumberList(context);
		addressList = new OperatoreAddressList(context.getRequest());
		emailAddressList = new OrganizationEmailAddressList(context
				.getRequest());
		rappLegale = new SoggettoFisico(context.getRequest());
	}

	/*
	 * public void setTempSessionData(ActionContext context){ Operatore temp =
	 * (Operatore) context.getSession().getAttribute("Operatore");
	 * setSedeLegale(temp.getSedeLegale()); setRappLegale(temp.getRappLegale());
	 * }
	 */

	public Operatore(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		this.setIdOperatore(rs.getInt("idOperatore"));
		this.setRagioneSociale(rs.getString("ragione_sociale"));
		this.setCodFiscale(rs.getString("codice_fiscale_impresa"));
		this.setPartitaIva(rs.getString("partita_iva"));
		this.setNote(rs.getString("note"));
		this.setEnteredBy(rs.getInt("enteredby"));
		this.setModifiedBy(rs.getInt("modifiedby"));
		this.setEntered(rs.getTimestamp("entered"));
		this.setModified(rs.getTimestamp("modified"));

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public boolean aggiungiRelazioneSoggettoFisico(Connection db, int tipoLegame)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			// Controllare se c'è già soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db,
					"opu_rel_operatore_soggetto_fisico_seq");

			sql.append("INSERT INTO opu_rel_operatore_soggetto_fisico (");

			if (idRelazione > -1)
				sql.append("id,");

			sql
					.append("id_operatore, id_soggetto_fisico, tipo_soggetto_fisico, data_inizio, stato_ruolo");

			sql.append(")");

			sql.append("VALUES (?,?,?,?, ?");

			if (idRelazione > -1) {
				sql.append(",?");
			}
			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			if (idRelazione > -1) {
				pst.setInt(++i, idRelazione);
			}

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar
					.getTime().getTime());

			pst.setInt(++i, idOperatore);
			pst.setInt(++i, this.getRappLegale().getIdSoggetto());
			pst.setInt(++i, tipoLegame);
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, 1);

			pst.execute();
			pst.close();

			/*
			 * Aggiornamento dati soggetto fisico se cambiano tutti i dati
			 * tranne quelli per il calcolo del codice fiscale
			 */

			this.getRappLegale().update(db);

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public boolean aggiornaRelazioneSoggettoFisico(Connection db, int tipoLegame)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar
					.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db
					.prepareStatement("Update opu_rel_operatore_soggetto_fisico set stato_ruolo = 2, data_fine = ? where "
							+ "stato_ruolo = 1 and "
							+ "id_operatore = ? "
							+ "and tipo_soggetto_fisico = ? ");

			int i = 0;
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, idOperatore);
			pst.setInt(++i, tipoLegame);
			int resultCount = pst.executeUpdate();
			pst.close();

			this.aggiungiRelazioneSoggettoFisico(db, 1);

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;
	}

	public boolean aggiungiSede(Connection db, Indirizzo indirizzo)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			// Controllare se c'è già soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db,
					"opu_relazione_operatore_sede_id_seq");

			sql.append("INSERT INTO opu_relazione_operatore_sede (");

			if (idRelazione > -1)
				sql.append("id,");

			sql
					.append("id_operatore, id_indirizzo, tipologia_sede, data_inizio, stato_sede");

			sql.append(")");

			sql.append("VALUES (?,?,?,?, ?");

			if (idRelazione > -1) {
				sql.append(",?");
			}
			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			if (idRelazione > -1) {
				pst.setInt(++i, idRelazione);
			}

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar
					.getTime().getTime());

			pst.setInt(++i, idOperatore);
			pst.setInt(++i, indirizzo.getIdIndirizzo());
			pst.setInt(++i, indirizzo.getTipologiaSede());
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, 1);

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

	public boolean aggiornaRelazioneSede(Connection db, Indirizzo oldIndirizzo,
			Indirizzo newIndirizzo) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar
					.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db
					.prepareStatement("Update opu_relazione_operatore_sede set stato_sede = 2, data_fine = ? where "
							+ "stato_sede = 1 and "
							+ "id_operatore = ? "
							+ "and tipologia_sede = ? ");

			int i = 0;
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, idOperatore);
			pst.setInt(++i, oldIndirizzo.getTipologiaSede());
			int resultCount = pst.executeUpdate();
			pst.close();

			this.aggiungiSede(db, newIndirizzo);

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;
	}

	/*
	 * protected void buildSede(ResultSet rs){
	 * 
	 * sedeLegale = new Indirizzo(); try {
	 * 
	 * sedeLegale.setIdIndirizzo(rs.getInt("id_indirizzo"));
	 * sedeLegale.setCap(rs.getString("cap"));
	 * sedeLegale.setComune(rs.getString("comune"));
	 * sedeLegale.setVia(rs.getString("via"));
	 * sedeLegale.setProvincia(rs.getString("provincia"));
	 * sedeLegale.setNazione(rs.getString("nazione"));
	 * sedeLegale.setLatitudine(rs.getDouble("latitudine"));
	 * sedeLegale.setLongitudine(rs.getDouble("longitudine"));
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

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
					.append("SELECT distinct o.*, o.id as id_soggetto ,"
							+ "i.*,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia   "
							+ "FROM opu_soggetto_fisico o "
							+ "left join opu_indirizzo i on o.indirizzo_id=i.id "
							+ "left join comuni1 on (comuni1.id = i.comune) "
							+ "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
							+ "left join lookup_province lp on lp.code = comuni1.cod_provincia::int "
							+ "join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) "
							+ "join opu_operatore op on (os.id_operatore = op.id)  where os.id_operatore = ? and stato_ruolo = 1");

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

	public boolean checkEsistenzaLineaProduttiva(Connection db,
			int idLineaTipologiaLineaProduttiva) {
		boolean exist = false;
		String query = "select rel.* from opu_operatore o left join opu_stabilimento s on (o.id = s.id_operatore) "
				+ "left join opu_relazione_stabilimento_linee_produttive rel on (s.id = rel.id_stabilimento) "
				+ "where o.partita_iva = ? and rel.id_linea_produttiva = ? and rel.trashed_date is null ";

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setString(++i, this.partitaIva);
			pst.setInt(++i, idLineaTipologiaLineaProduttiva);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()){
				exist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return exist;
	}

}
