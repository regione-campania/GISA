/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationAddressList;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddressList;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumberList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessione;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.newvaluescorrelationname;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Operatore extends GenericBean {

	public static final int BDU_PRIVATO = 1;
	public static final int BDU_SINDACO = 3;
	public static final int BDU_SINDACO_FR = 7;
	public static final int BDU_COLONIA = 9;
	public static final int BDU_CANILE = 5;

	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.Operatore.class);
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
	private float indice;
	private float occupazioneAttuale;
	private int numeroCaniVivi;
	private int mq_disponibili;
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
	
	public int id_linea_produttiva = -1;

	private SoggettoFisico rappLegale;
	// private Indirizzo sedeLegale;

	private SedeList listaSediOperatore = new SedeList();

	private StabilimentoList listaStabilimenti = new StabilimentoList();

	private OperatoreAddressList addressList = new OperatoreAddressList();
	private OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
	protected OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
	
	// VARIABILI CONTROLLO BLOCCO CANILE
	private java.sql.Timestamp data_operazione_blocco;
	private java.sql.Timestamp data_sospensione_blocco;
	private java.sql.Timestamp data_riattivazione_blocco;
	private int user_id_blocco;
	private String  note_blocco;
	private String motivo_blocco;
	private boolean bloccato;
	private boolean trashed_bloccato;
	// Storico blocco
	private List storicoBlocco;
	
	public List getStoricoBlocco(){
		return this.storicoBlocco;
	}
	
	public void setStoricoBlocco(List storico){
		this.storicoBlocco=storico;
	}
	
	public java.sql.Timestamp getData_operazione_blocco() {
		return data_operazione_blocco;
	}

	public void setData_operazione_blocco(java.sql.Timestamp data_operazione_blocco) {
		this.data_operazione_blocco = data_operazione_blocco;
	}

	public java.sql.Timestamp getData_sospensione_blocco() {
		return data_sospensione_blocco;
	}

	public void setData_sospensione_blocco(java.sql.Timestamp data_sospensione_blocco) {
		this.data_sospensione_blocco = data_sospensione_blocco;
	}

	public java.sql.Timestamp getData_riattivazione_blocco() {
		return data_riattivazione_blocco;
	}

	public void setData_riattivazione_blocco(java.sql.Timestamp data_riattivazione_blocco) {
		this.data_riattivazione_blocco = data_riattivazione_blocco;
	}

	public int getUser_id_blocco() {
		return user_id_blocco;
	}

	public void setUser_id_blocco(int user_id_blocco) {
		this.user_id_blocco = user_id_blocco;
	}

	public String getNote_blocco() {
		return note_blocco;
	}

	public void setNote_blocco(String note_blocco) {
		this.note_blocco = note_blocco;
	}

	public String getMotivo_blocco() {
		return motivo_blocco;
	}

	public void setMotivo_blocco(String motivo_blocco) {
		this.motivo_blocco = motivo_blocco;
	}

	public boolean isBloccato() {
		return bloccato;
	}

	public void setBloccato(boolean bloccato) {
		this.bloccato = bloccato;
	}

	public boolean isTrashed_bloccato() {
		return trashed_bloccato;
	}

	public void setTrashed_bloccato(boolean trashed_bloccato) {
		this.trashed_bloccato = trashed_bloccato;
	}

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

	public int getId_linea_produttiva() {
		return id_linea_produttiva;
	}

	public void setId_linea_produttiva(int id_linea_produttiva) {
		this.id_linea_produttiva = id_linea_produttiva;
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
	
	public int getMq_disponibili() {
		return mq_disponibili;
	}

	public void setMq_disponibili(int mq_disponibili) {
		this.mq_disponibili = mq_disponibili;
	}
	
	public float getIndice() {
		return indice;
	}

	public void setIndice(float indice) {
		this.indice = indice;
	}
	
	public float getOccupazioneAttuale() {
		return occupazioneAttuale;
	}

	public void setOccupazioneAttuale(float occupazioneAttuale) {
		this.occupazioneAttuale = occupazioneAttuale;
	}
	
	public int getNumeroCaniVivi() {
		return numeroCaniVivi;
	}

	public void setNumeroCaniVivi(int numeroCaniVivi) {
		this.numeroCaniVivi = numeroCaniVivi;
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

	public void setEmailAddressList(OrganizationEmailAddressList emailAddressList) {
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

	public void queryRecordOperatore(Connection db, int idOperatore) throws SQLException, IndirizzoNotFoundException {

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

	
	
	
	
	
	public Operatore(Connection db, int idRelStabLp)
	{
		StringBuffer sql = new StringBuffer();
		try 
		{
			//Se id non valido non valorizza il bean Operatore
			if(idRelStabLp>0)
			{
				sql.append(" select op.*, op.id as idOperatore, sogg.id as idSoggetto from opu_relazione_stabilimento_linee_produttive rel_stab_lp " +
							 " left join opu_stabilimento stab on stab.id = rel_stab_lp.id_Stabilimento  " +
							 " left join opu_operatore op on op.id = stab.id_operatore and op.trashed_date is null  " +
							 " left join opu_rel_operatore_soggetto_fisico rel_op_sogg on rel_op_sogg.id_operatore = op.id and (rel_op_sogg.enabled or rel_op_sogg.enabled is null) " +
							 " left join opu_soggetto_fisico sogg on sogg.id = rel_op_sogg.id_soggetto_fisico and sogg.trashed_date is null" +
							 " where rel_stab_lp.trashed_date is null and  rel_stab_lp.id = ?");

				PreparedStatement pst = db.prepareStatement(sql.toString());

				pst.setInt(1, idRelStabLp);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) 
				{
					buildRecord(rs);
					listaStabilimenti.setIdOperatore(idOperatore);
					listaStabilimenti.buildList(db);
					if(rs.getInt("idSoggetto")>0)
						rappLegale = new SoggettoFisico(db, rs.getInt("idSoggetto"));
				}
				
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{

		}
	}
	
	public void queryRecordOperatorebyIdLineaProduttiva(Connection db, int idLineaProduttiva) throws SQLException {

		if (idLineaProduttiva<10000000){
			
		
		
		
		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement("Select o.*, o.id as idOperatore"
				+ ", bl.bloccato as canile_bloccato, bl.data_sospensione, bl.data_riattivazione, bl.motivo as motivo_blocco, bl.data_operazione "
				+ " from opu_operatore o  "
				+ "join opu_stabilimento s on s.id_operatore = o.id "
				+ "join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id "
				+ "left join blocco_sblocco_canile bl on (bl.id_canile = r.id and bl.trashed = false and bl.trashed_date is null) " 
				+ "where r.id = ? and r.trashed_date is null ");
		pst.setInt(1, idLineaProduttiva);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}

		// if (idOperatore == -1) {
		// throw new SQLException(Constants.NOT_FOUND_ERROR);
		// }

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
		
		}else{
			PreparedStatement pst = db.prepareStatement("select * from opu_operatori_denormalizzati_origine where id_rel_stab_lp = ?");
			pst.setInt(1, idLineaProduttiva);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				this.setIdOperatore(idLineaProduttiva);
				this.setRagioneSociale(rs.getString("ragione_sociale"));
				this.setCodFiscale(rs.getString("codice_fiscale"));
				this.setEntered(rs.getTimestamp("entered"));
			}


			rs.close();
			pst.close();

		}
	}
	
	
	public int getComuneColonia(Connection db, int idLineaProduttiva) throws SQLException 
	{
			int idComune = -1;
			PreparedStatement pst = db.prepareStatement("select * from opu_operatori_denormalizzati where id_rel_stab_lp = ? and id_linea_produttiva = 8 ");
			pst.setInt(1, idLineaProduttiva);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) 
				idComune = rs.getInt("comune");

			rs.close();
			pst.close();
			
			return idComune;
	}

	public void queryRecordOperatorebyCodiceFiscale(Connection db, String codiceFiscale) throws SQLException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		int idLineaProduttiva = -1;

		PreparedStatement pst = db.prepareStatement("Select o.*, o.id as idOperatore, r.id as idlinea "
				+ "from opu_operatore o  " + "join opu_stabilimento s on s.id_operatore = o.id "
				+ "join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id "
				+ "where o.codice_fiscale = ? and r.trashed_date is null ");
		pst.setString(1, codiceFiscale);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
			idLineaProduttiva = rs.getInt("idlinea");
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

	
	
	public int update(Connection db) throws IndirizzoNotFoundException, SQLException 
	{
		return update(db,true);
		
	}
	public int update(Connection db,boolean aggiornaIndirizzo) throws IndirizzoNotFoundException, SQLException {
		// System.out.println("INIZIO UPDATE OPERATORE");
		int resultCount = 0;
		Operatore old = new Operatore();
//		System.out.println("Dati operatore: ");
//		System.out.println("Ragione Sociale: " + ragioneSociale);
//		System.out.println("CF Impresa: " + codFiscale);
//		System.out.println("IVA: " + partitaIva);
//		System.out.println("Note: " + note);
		try {
			PreparedStatement pst = null;
			StringBuffer sql = new StringBuffer();

			// Operatore old = new Operatore( db, idOperatore);

			old.queryRecordOperatore(db, idOperatore);
			sql.append("UPDATE opu_operatore o SET ");
			// , partita_iva= ?,

			sql.append("ragione_sociale= ?");
			sql.append(",modified = now() ");

			if (codFiscale != null && !codFiscale.equals(""))
				sql.append(",codice_fiscale_impresa= ?");

			if (partitaIva != null && !partitaIva.equals(""))
				sql.append(",partita_iva= ?");

			if (note != null && !note.equals(""))
				sql.append(", note = ?");

			if (ipModifiedBy != null && !ipModifiedBy.equals(""))
				sql.append(", ipmodifiedby = ?");

			if (modifiedBy > -1)
				sql.append(", modifiedby = ?");

			sql.append(" where o.id = ?");

			int i = 0;

			pst = db.prepareStatement(sql.toString());

			pst.setString(++i, ragioneSociale);

			if (codFiscale != null && !codFiscale.equals(""))
				pst.setString(++i, codFiscale);

			if (partitaIva != null && !partitaIva.equals(""))
				pst.setString(++i, partitaIva);

			if (note != null && !note.equals(""))
				pst.setString(++i, note);

			if (ipModifiedBy != null && !ipModifiedBy.equals(""))
				pst.setString(++i, ipModifiedBy);

			if (modifiedBy > -1)
				pst.setInt(++i, modifiedBy);

			pst.setInt(++i, idOperatore);

			resultCount = pst.executeUpdate();
			pst.close();
			// pst chiuso prima
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {

		}

		/*
		 * ResultSet rs = pst.executeQuery(); String newId=""; while (rs.next())
		 * { newId = rs.getString("id"); System.out.println("Nuovo Id: "+newId +
		 * "\n"); }
		 */

		if (this.getRappLegale() != null && old.getRappLegale() != null) {
			// controllo se è cambiato soggetto fisico rappresentante legale, se
			// sì lo aggiorno disabilitando il vecchio record
			if (old.getRappLegale().getIdSoggetto() != this.getRappLegale().getIdSoggetto()) {
				this.aggiornaRelazioneSoggettoFisico(db, 1);
			} else {
				this.getRappLegale().update(db,aggiornaIndirizzo);
			}
		} else {
			this.aggiungiRelazioneSoggettoFisico(db, 1);
		}

		if (this.getSedeLegale() != null && old.getSedeLegale() != null) {

			if (old.getSedeLegale().getIdIndirizzo() != this.getSedeLegale().getIdIndirizzo()) {
				this.aggiornaRelazioneSede(db, old.getSedeLegale(), this.getSedeLegale());
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

		// CERCO L'ID DELL'INDIRIZZO RELATIVO ALL'OPERATORE CORRENTE
		PreparedStatement pst2 = null;
		StringBuffer sql2 = new StringBuffer();
		sql2.append("SELECT ind.id from opu_indirizzo as ind inner join opu_relazione_operatore_sede sed on ind.id = sed.id_indirizzo inner join opu_operatore op on op.id = sed.id_operatore where op.id ="
				+ idOperatore);
		try {
			pst2 = db.prepareStatement(sql2.toString());

			ResultSet rs = pst2.executeQuery();
			String idInd = "";
			while (rs.next()) {
				idInd = rs.getString("id");
			//	System.out.println("id indirizzo: " + idInd + "\n");
			}
			pst2.close();
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			/*
			 */
		}
		//System.out.println("Query indirizzo: " + pst2.toString());

		// AGGIORNO (PER ORA CON DATI INVENTATI) L'INDIRIZZO TROVATO
		/*
		 * PreparedStatement pst3 = null; StringBuffer sql3 = new
		 * StringBuffer();sql3.append(
		 * "UPDATE opu_indirizzo SET via='provaprova', provincia='AA' where id="
		 * +idInd); pst3 = db.prepareStatement(sql3.toString());
		 * pst3.executeUpdate(); pst3.close();
		 * System.out.println("Query indirizzo: "+pst3.toString())
		 * 
		 * int idIndInt = Integer.parseInt(idInd); Indirizzo ind = new
		 * Indirizzo(); ind.update(idIndInt, db);;
		 */

		return resultCount;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			modifiedBy = enteredBy;

			idOperatore = DatabaseUtils.getNextSeq(db, "opu_operatore_id_seq");
			sql.append("INSERT INTO opu_operatore (");

			sql.append("ragione_sociale, codice_fiscale_impresa, " + "note, partita_iva");

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

			sql.append(", entered, modified ");

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

			sql.append(", ?, ? ");

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

			pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
			pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));

			pst.execute();
			pst.close();

			this.idOperatore = DatabaseUtils.getCurrVal(db, "opu_operatore_id_seq", idOperatore);

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
		} finally {
		}
		return true;

	}

	public void setRequestItems(ActionContext context) {
		phoneNumberList = new OrganizationPhoneNumberList(context);
		addressList = new OperatoreAddressList(context.getRequest());
		emailAddressList = new OrganizationEmailAddressList(context.getRequest());
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
		try	{ rs.findColumn("indice"); this.setIndice(rs.getFloat("indice")); }catch(Exception ex){ }
		try { rs.findColumn("mq_disponibili"); this.setMq_disponibili(rs.getInt("mq_disponibili")); }catch(Exception ex){ }
		try { rs.findColumn("occupazione_attuale"); this.setOccupazioneAttuale(rs.getFloat("occupazione_attuale")); }catch(Exception ex){ }
		try { rs.findColumn("numero_cani_vivi"); this.setNumeroCaniVivi(rs.getInt("numero_cani_vivi")); }catch(Exception ex){ }
		try { rs.findColumn("id_linea_produttiva"); this.setId_linea_produttiva(rs.getInt("id_linea_produttiva")); }catch(Exception ex){ }
			
		try { rs.findColumn("canile_bloccato"); this.setBloccato(rs.getBoolean("canile_bloccato")); }catch(Exception ex){ }
		try { rs.findColumn("data_sospensione"); this.setData_sospensione_blocco(rs.getTimestamp("data_sospensione")); }catch(Exception ex){ }
		try { rs.findColumn("data_riattivazione"); this.setData_riattivazione_blocco(rs.getTimestamp("data_riattivazione")); }catch(Exception ex){ }
		try { rs.findColumn("motivo_blocco"); this.setMotivo_blocco(rs.getString("motivo_blocco")); }catch(Exception ex){ }
		try { rs.findColumn("data_operazione"); this.setData_operazione_blocco(rs.getTimestamp("data_operazione")); }catch(Exception ex){ }
		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public boolean aggiungiRelazioneSoggettoFisico(Connection db, int tipoLegame) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			// Controllare se c'è già soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db, "opu_rel_operatore_soggetto_fisico_id_seq");

			sql.append("INSERT INTO opu_rel_operatore_soggetto_fisico (");

			if (idRelazione > -1)
				sql.append("id,");

			sql.append("id_operatore, id_soggetto_fisico, tipo_soggetto_fisico, data_inizio, stato_ruolo");

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
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

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

			/**
			 * Commentato da Veronica perchè dalla maschera di inserimento non
			 * si possono modificare in ogni caso i dati del soggetto
			 */
			// this.getRappLegale().update(db);
		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public boolean aggiornaRelazioneSoggettoFisico(Connection db, int tipoLegame) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db
					.prepareStatement("Update opu_rel_operatore_soggetto_fisico set stato_ruolo = 2, data_fine = ? where "
							+ "stato_ruolo = 1 and " + "id_operatore = ? " + "and tipo_soggetto_fisico = ? ");

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

	public boolean aggiungiSede(Connection db, Indirizzo indirizzo) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			// Controllare se c'è già soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db, "opu_relazione_operatore_sede_id_seq");

			sql.append("INSERT INTO opu_relazione_operatore_sede (");

			if (idRelazione > -1)
				sql.append("id,");

			sql.append("id_operatore, id_indirizzo, tipologia_sede, data_inizio, stato_sede, modified, modifiedby");

			sql.append(")");

			sql.append("VALUES (?,?,?,?, ?, ?, ?");

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
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

			pst.setInt(++i, idOperatore);
			pst.setInt(++i, indirizzo.getIdIndirizzo());
			pst.setInt(++i, indirizzo.getTipologiaSede());
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, 1);
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, modifiedBy);

			pst.execute();
			pst.close();

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public void updateAggiornamentoOperatore(Connection db) {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db.prepareStatement("Update opu_operatore set  modifiedby = ? "
					+ ", modified = CURRENT_TIMESTAMP where id = ?");

			int i = 0;
			// pst.setTimestamp(++i, currentTimestamp);

			// pst.setInt(++i, oldIndirizzo.getTipologiaSede());
			pst.setInt(++i, this.modifiedBy);
			pst.setInt(++i, idOperatore);
			int resultCount = pst.executeUpdate();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public boolean aggiornaRelazioneSede(Connection db, Indirizzo oldIndirizzo, Indirizzo newIndirizzo)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db
					.prepareStatement("Update opu_relazione_operatore_sede set stato_sede = 2, data_fine = ?, modifiedby= ?, modified=CURRENT_TIMESTAMP where "
							+ "stato_sede = 1 and " + "id_operatore = ? " + "and tipologia_sede = ? ");

			int i = 0;
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, this.modifiedBy);
			pst.setInt(++i, idOperatore);
			pst.setInt(++i, oldIndirizzo.getTipologiaSede());
			int resultCount = pst.executeUpdate();
			pst.close();

			this.aggiungiSede(db, newIndirizzo);

			this.updateAggiornamentoOperatore(db);
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			/*
			 */
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
							+ " i.id as id_indirizzo, i.*,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia   "
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

	public boolean checkEsistenzaLineaProduttiva(Connection db, int idLineaTipologiaLineaProduttiva) {
		boolean exist = false;
		String query = "select rel.* from opu_operatore o left join opu_stabilimento s on (o.id = s.id_operatore) "
				+ "left join opu_relazione_stabilimento_linee_produttive rel on (s.id = rel.id_stabilimento) "
				+ "where lower(o.partita_iva) = lower(?) and rel.id_linea_produttiva = ? and o.trashed_Date is null and rel.trashed_date is null ";

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setString(++i, this.partitaIva);
			pst.setInt(++i, idLineaTipologiaLineaProduttiva);

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

	public boolean checkEsistenzaLineaProduttivaSindaco(Connection db, int idcomune, int idLineaTipologiaLineaProduttiva) {
		boolean exist = false;
		String query = "select rel.* from opu_operatore o left join opu_stabilimento s on (o.id = s.id_operatore) join opu_indirizzo i on i.id = s.id_indirizzo "
				+ "left join opu_relazione_stabilimento_linee_produttive rel on (s.id = rel.id_stabilimento) "
				+ "where i.comune = ? and rel.id_linea_produttiva = ? and o.trashed_Date is null and rel.trashed_date is null ";

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setInt(++i, idcomune);
			pst.setInt(++i, idLineaTipologiaLineaProduttiva);

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

	public int getIdLineaProduttivaIfExists(Connection db, int idLineaTipologiaLineaProduttiva) {
		int idRelazioneStabLineaProd = -1;
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

			if (rs.next()) {
				idRelazioneStabLineaProd = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idRelazioneStabLineaProd;
	}

	public ArrayList<String[]> getListaDWR(int id_asl, int id_tipo_linea_prod, int id_relazione_stabilim_linea_prod) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		OperatoreList listaOp = new OperatoreList();

		Integer[] lineaP = new Integer[1];
		lineaP[0] = id_tipo_linea_prod;
		listaOp.setIdAsl(id_asl);
		listaOp.setIdLineaProduttiva(lineaP);
		if (id_relazione_stabilim_linea_prod > 0) { // Utente referente canile
			listaOp.setIdRelazioneStabilimentoLineaProduttiva(id_relazione_stabilim_linea_prod);
		}
		Connection db = null;
		try {
			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username = ApplicationProperties
			// .getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();

			// db = DbUtil.getConnection(dbName, username, pwd, host);
			// db = GestoreConnessioni.getConnection();

			db = GestoreConnessioni.getConnection();
			listaOp.buildList(db);

			for (int i = 0; i < listaOp.size(); i++) {

				Operatore op = (Operatore) listaOp.get(i);

				for (int h = 0; h < op.getListaStabilimenti().size(); h++) {
					Stabilimento stab = (Stabilimento) op.getListaStabilimenti().get(h);
					for (int s = 0; s < stab.getListaLineeProduttive().size(); s++) {
						LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(s);
						String[] a = new String[2];
						a[0] = op.getRagioneSociale();
						a[1] = String.valueOf(lp.getId());
						list.add(a);

					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
			// DbUtil.chiudiConnessioneJDBC(null, null, db);
		}

		return list;

	}

	public EsitoControllo importProprietarioPrivatoDaEventoCessione(int idAnimale, int userid) {

		int inserted = -1;
		String campo_mancante = "";
		EsitoControllo esito = new EsitoControllo();
		Connection db = null;
		User user = null;

		try {
			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username = ApplicationProperties
			// .getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();

			// db = DbUtil.getConnection(dbName, username, pwd, host);
			db = GestoreConnessioni.getConnection();
			user = new User(db, userid);
			EventoCessione cessione = new EventoCessione();
			cessione.GetCessioneApertaByIdAnimale(db, idAnimale);
			String cod_fiscale_to_insert = cessione.getCodiceFiscale();
			if (cod_fiscale_to_insert == null || ("").equals(cod_fiscale_to_insert)
					|| cod_fiscale_to_insert.length() < 16) {
				esito.setIdEsito(inserted);
				esito.setDescrizione("Dati proprietario incompleti, codice fiscale non corretto o non valorizzato");
				return esito;
			}
			Operatore opToInsert = new Operatore();
			opToInsert.setCodFiscale(cod_fiscale_to_insert);
			opToInsert.setPartitaIva(cod_fiscale_to_insert);
			opToInsert.setEnteredBy(userid);
			boolean exist = opToInsert.checkEsistenzaLineaProduttiva(db, LineaProduttiva.idAttivitaPrivato);

			if (exist) {
				esito.setIdEsito(inserted);
				esito.setDescrizione("Esiste già un proprietario di tipo privato identificato dal c.f"
						+ opToInsert.getCodFiscale() + ", cortesemente selezionalo dalla banca dati");
				return esito;
			} else {

				// Controlla correttezza campi
				campo_mancante = cessione.checkPossibilitaImportAutomaticoProprietario();

				if (!("").equals(campo_mancante)) {
					esito.setIdEsito(inserted);
					esito.setDescrizione("Dati proprietario incompleti," + campo_mancante
							+ " non corretto o non valorizzato");
					return esito;
				}
				// Ho tutti i campi necessari, posso aggiungere

				opToInsert.setRagioneSociale(cessione.getCognome() + ", " + cessione.getNome());
				SoggettoFisico soggettoAdded = new SoggettoFisico(cessione, db, user);
				opToInsert.setRappLegale(soggettoAdded);

				opToInsert.getListaSediOperatore().add(soggettoAdded.getIndirizzo());

				boolean insertOpOk = opToInsert.insert(db);

				// Aggiungo stabilimento

				Stabilimento newStabilimento = new Stabilimento();

				newStabilimento.setEnteredBy(userid);
				newStabilimento.setFlagFuoriRegione(false); // Solo in regione
															// trattandosi di
															// una cessione

				newStabilimento.setRappLegale(soggettoAdded);
				Indirizzo indTemp = soggettoAdded.getIndirizzo();
				newStabilimento.setTelefono1(cessione.getNumeroTelefono());
				indTemp.setTipologiaSede(5); // Operativa
				newStabilimento.setSedeOperativa(indTemp);

				Object[] asl;

				asl = DwrUtil.getValoriAsl(indTemp.getComune());

				Object[] aslVal = (Object[]) asl[0];

				newStabilimento.setIdAsl((Integer) aslVal[0]);

				LineaProduttiva lp = new LineaProduttiva(db, LineaProduttiva.idAggregazionePrivato);
				newStabilimento.setIdOperatore(opToInsert.getIdOperatore());
				lp.setTelefono1(cessione.getNumeroTelefono());

				newStabilimento.insert(db, false);
				lp = newStabilimento.aggiungiLineaProduttiva(db, lp);

				/**
				 * aggiornamento della tabella che materializza la vista degli
				 * operatori denormalizzati.l'aggiornamento avviene cancellando
				 * e reinserendo il record dalla tabella. il record inserito è
				 * preso dalla vista.
				 */
				opToInsert.aggiornaVistaMaterializzata(db);

				esito.setIdEsito(lp.getId()); // Id relzione stabilim linea prod
												// appena inserita
				esito.setDescrizione(opToInsert.getRappLegale().getNome() + " "
						+ opToInsert.getRappLegale().getCognome());

			}

		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage().contains("check_capna"))
				esito.setDescrizione("Proprietario non importabile perchè l'indirizzo contiene il cap 80100. Inserire il proprietario correttamente usando il link 'Seleziona nuovo proprietario'");
			else
				esito.setDescrizione(e.getMessage());
		} finally {
			GestoreConnessioni.freeConnection(db);
			// DbUtil.chiudiConnessioneJDBC(null, null, db);
		}

		return esito;

	}

	public EsitoControllo importProprietarioPrivatoDaEventoAdozione(int idAnimale, int userid) {

		int inserted = -1;
		String campo_mancante = "";
		EsitoControllo esito = new EsitoControllo();
		Connection db = null;
		User user = null;

		try {
			db = GestoreConnessioni.getConnection();
			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username = ApplicationProperties
			// .getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// db = DbUtil.getConnection(dbName, username, pwd, host);
			user = new User(db, userid);
			EventoAdozioneFuoriAsl cessione = new EventoAdozioneFuoriAsl();
			cessione.GetAdozioneApertaByIdAnimale(db, idAnimale);
			String cod_fiscale_to_insert = cessione.getCodiceFiscale();
			if (cod_fiscale_to_insert == null || ("").equals(cod_fiscale_to_insert)
					|| cod_fiscale_to_insert.length() < 16) {
				esito.setIdEsito(inserted);
				esito.setDescrizione("Dati proprietario incompleti, codice fiscale non corretto o non valorizzato");
				return esito;
			}
			Operatore opToInsert = new Operatore();
			opToInsert.setCodFiscale(cod_fiscale_to_insert);
			opToInsert.setPartitaIva(cod_fiscale_to_insert);
			opToInsert.setEnteredBy(userid);
			boolean exist = opToInsert.checkEsistenzaLineaProduttiva(db, LineaProduttiva.idAttivitaPrivato);

			if (exist) {
				esito.setIdEsito(inserted);
				esito.setDescrizione("Esiste già un proprietario di tipo privato identificato dal c.f"
						+ opToInsert.getCodFiscale() + ", cortesemente selezionalo dalla banca dati");
				return esito;
			} else {

				// Controlla correttezza campi
				campo_mancante = cessione.checkPossibilitaImportAutomaticoProprietario();

				if (!("").equals(campo_mancante)) {
					esito.setIdEsito(inserted);
					esito.setDescrizione("Dati proprietario incompleti," + campo_mancante
							+ " non corretto o non valorizzato");
					return esito;
				}
				// Ho tutti i campi necessari, posso aggiungere

				opToInsert.setRagioneSociale(cessione.getNome() + ", " + cessione.getCognome());
				SoggettoFisico soggettoAdded = new SoggettoFisico(cessione, db, user);
				opToInsert.setRappLegale(soggettoAdded);

				opToInsert.getListaSediOperatore().add(soggettoAdded.getIndirizzo());

				boolean insertOpOk = opToInsert.insert(db);

				// Aggiungo stabilimento

				Stabilimento newStabilimento = new Stabilimento();

				newStabilimento.setEnteredBy(userid);
				newStabilimento.setFlagFuoriRegione(false); // Solo in regione
															// trattandosi di
															// una cessione

				newStabilimento.setRappLegale(soggettoAdded);
				Indirizzo indTemp = soggettoAdded.getIndirizzo();
				newStabilimento.setTelefono1(cessione.getNumeroTelefono());
				indTemp.setTipologiaSede(5); // Operativa
				newStabilimento.setSedeOperativa(indTemp);

				Object[] asl;

				asl = DwrUtil.getValoriAsl(indTemp.getComune());

				Object[] aslVal = (Object[]) asl[0];

				newStabilimento.setIdAsl((Integer) aslVal[0]);

				LineaProduttiva lp = new LineaProduttiva(db, LineaProduttiva.idAggregazionePrivato);
				newStabilimento.setIdOperatore(opToInsert.getIdOperatore());
				lp.setTelefono1(cessione.getNumeroTelefono());

				newStabilimento.insert(db, false);
				lp = newStabilimento.aggiungiLineaProduttiva(db, lp);

				/**
				 * aggiornamento della tabella che materializza la vista degli
				 * operatori denormalizzati.l'aggiornamento avviene cancellando
				 * e reinserendo il record dalla tabella. il record inserito è
				 * preso dalla vista.
				 */
				opToInsert.aggiornaVistaMaterializzata(db);

				esito.setIdEsito(lp.getId()); // Id relzione stabilim linea prod
												// appena inserita
				esito.setDescrizione(opToInsert.getRappLegale().getNome() + " "
						+ opToInsert.getRappLegale().getCognome() + " - " + opToInsert.getRappLegale().getCodFiscale());

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
			// DbUtil.chiudiConnessioneJDBC(null, null, db);
		}

		return esito;

	}

	public String aggiornaVistaMaterializzata(Connection db) throws SQLException {
		String esito = "";
		String sql = "select * from public_functions.update_opu_materializato(?) ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			esito = rs.getString(1);
		return esito;

	}

	public boolean checkModificaResidenzaStabilimento() {

		if (this.getIdOperatore() > 0) {

			Stabilimento stab = (Stabilimento) this.getListaStabilimenti().get(0);
			return stab.isFlagModificaResidenzaFuoriAslInCorso();
		} else
			return false;

	}

	public boolean salvaModificheOperatore(Connection db, Operatore oldOperatore, Operatore newOperatore,
			int idRelazione, int idUtente) throws SQLException {
		StringBuffer sql = new StringBuffer("");
		// Controllo cambiamenti dati operatore

		if (oldOperatore.getRagioneSociale() != null
				&& !oldOperatore.getRagioneSociale().equals(newOperatore.getRagioneSociale()))
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
					+ idRelazione + "," + "'Ragione Sociale'" + ", ? , ?, " + idUtente + ", now());");
		if (oldOperatore.getPartitaIva() != null && !oldOperatore.getPartitaIva().equals(newOperatore.getPartitaIva()))
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
					+ idRelazione + "," + "'Partita Iva'" + ", ?, ?, " + idUtente + ", now());");
		if (oldOperatore.getCodFiscale() != null && !oldOperatore.getCodFiscale().equals(newOperatore.getCodFiscale()))
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
					+ idRelazione + "," + "'Codice Fiscale Impresa'" + ", ?, ?, " + idUtente + ", now());");

		if (oldOperatore.getRappLegale() != null) {
			// Controllo cambiamenti dati rappresentante legale
			if (oldOperatore.getRappLegale().getIdSoggetto() != newOperatore.getRappLegale().getIdSoggetto())
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione
						+ ","
						+ "'idSoggetto'"
						+ ", "
						+ oldOperatore.getRappLegale().getIdSoggetto()
						+ ", " + newOperatore.getRappLegale().getIdSoggetto() + ", " + idUtente + ", now());");
			if (!oldOperatore.getRappLegale().getNome().equals(newOperatore.getRappLegale().getNome()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Nome Privato/Rapp.'" + ", ?, ?, " + idUtente + ", now());");
			if (!oldOperatore.getRappLegale().getCognome().equals(newOperatore.getRappLegale().getCognome()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Cognome Privato/Rapp.'" + ", ?, ?, " + idUtente + ", now());");
			if (!oldOperatore.getRappLegale().getSesso().equals(newOperatore.getRappLegale().getSesso()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Sesso Privato/Rapp.'" + ", ?, ?, " + idUtente + ", now());");
			if (!oldOperatore.getRappLegale().getComuneNascita()
					.equals(newOperatore.getRappLegale().getComuneNascita()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Comune nascita Privato/Rapp.'" + ", ?, ?, " + idUtente + ", now());");
			if (oldOperatore.getRappLegale().getDataNascita() != null
					&& !oldOperatore.getRappLegale().getDataNascita()
							.equals(newOperatore.getRappLegale().getDataNascita()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Data Nascita Privato/Rapp.'" + ", ?, ?, " + idUtente + ", now());");
			if (!oldOperatore.getRappLegale().getCodFiscale().equals(newOperatore.getRappLegale().getCodFiscale()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Codice Fiscale Privato/Rapp.'" + ", ?, ?, " + idUtente + ", now());");
			if (oldOperatore.getRappLegale().getDocumentoIdentita() == null
					&& newOperatore.getRappLegale().getDocumentoIdentita() != null
					&& !newOperatore.getRappLegale().getDocumentoIdentita().equals(""))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione
						+ ","
						+ "'Documento Identita Privato/Rapp.'"
						+ ", ?, ?, "
						+ idUtente
						+ ", now());");
			if (oldOperatore.getRappLegale().getDocumentoIdentita() != null
					&& (!oldOperatore.getRappLegale().getDocumentoIdentita()
							.equals(newOperatore.getRappLegale().getDocumentoIdentita())))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione
						+ ","
						+ "'Documento Identita Privato/Rapp.'"
						+ ", ?, ?, "
						+ idUtente
						+ ", now());");
		}

		if (oldOperatore.getSedeLegale() != null) {
			// Controllo cambiamenti indirizzo sede legale
			if (oldOperatore.getSedeLegale().getIdIndirizzo() != newOperatore.getSedeLegale().getIdIndirizzo())
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione
						+ ","
						+ "'idIndirizzo Residenza Privato/Rapp.'"
						+ ", "
						+ oldOperatore.getSedeLegale().getIdIndirizzo()
						+ ", "
						+ newOperatore.getSedeLegale().getIdIndirizzo() + ", " + idUtente + ", now());");
		}

		Stabilimento oldStab = (Stabilimento) oldOperatore.getListaStabilimenti().get(0);
		Stabilimento newStab = (Stabilimento) newOperatore.getListaStabilimenti().get(0);
		SoggettoFisico oldSoggetto = oldStab.getRappLegale();
		SoggettoFisico newSoggetto = newStab.getRappLegale();

		if (oldStab.getSedeOperativa() != null) {
			// Controllo cambiamenti indirizzo sede operativa
			if (oldStab.getSedeOperativa().getIdIndirizzo() != newStab.getSedeOperativa().getIdIndirizzo())
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione
						+ ","
						+ "'idIndirizzo Stabilimento'"
						+ ", "
						+ oldStab.getSedeOperativa().getIdIndirizzo()
						+ ", "
						+ newStab.getSedeOperativa().getIdIndirizzo() + ", " + idUtente + ", now());");
		}

		if (oldSoggetto != null) {
			// Controllo cambiamenti dati responsabile stabilimento legale
			if (oldSoggetto.getIdSoggetto() != newSoggetto.getIdSoggetto())
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione
						+ ","
						+ "'idSoggettoResp'"
						+ ", "
						+ oldSoggetto.getIdSoggetto()
						+ ", "
						+ newSoggetto.getIdSoggetto() + ", " + idUtente + ", now());");
			if (!oldSoggetto.getNome().equals(newSoggetto.getNome()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Nome Resp/Tutore'" + ", ?, ?, " + idUtente + ", now());");
			if (!oldSoggetto.getCognome().equals(newSoggetto.getCognome()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Cognome Resp/Tutore'" + ", ?, ?, " + idUtente + ", now());");
			if (!oldSoggetto.getSesso().equals(newSoggetto.getSesso()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Sesso Resp/Tutore'" + ", ?, ?, " + idUtente + ", now());");
			if (!oldSoggetto.getComuneNascita().equals(newSoggetto.getComuneNascita()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Comune nascita Resp/Tutore'" + ", ?, ?, " + idUtente + ", now());");
			if (oldSoggetto.getDataNascita() != null
					&& !oldSoggetto.getDataNascita().equals(newSoggetto.getDataNascita()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Data Nascita Resp/Tutore'" + ", ?, ?, " + idUtente + ", now());");
			if (!oldSoggetto.getCodFiscale().equals(newSoggetto.getCodFiscale()))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
						+ idRelazione + "," + "'Codice fiscale Resp/Tutore'" + ", ?, ?, " + idUtente + ", now());");

			if (oldSoggetto.getIndirizzo() != null) {
				// Controllo cambiamenti indirizzo responsabile stabilimento
				if (oldSoggetto.getIndirizzo().getIdIndirizzo() != newSoggetto.getIndirizzo().getIdIndirizzo())
					sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
							+ idRelazione
							+ ","
							+ "'idIndirizzo Residenza Resp/Tutore'"
							+ ", "
							+ oldSoggetto.getIndirizzo().getIdIndirizzo()
							+ ", "
							+ newSoggetto.getIndirizzo().getIdIndirizzo() + ", " + idUtente + ", now());");
			}
		}

		try {
			PreparedStatement pst = db.prepareStatement(sql.toString());
			int i = 0;

			if (!oldOperatore.getRagioneSociale().equals(newOperatore.getRagioneSociale())) {
				pst.setString(++i, oldOperatore.getRagioneSociale());
				pst.setString(++i, newOperatore.getRagioneSociale());
			}
			if (!oldOperatore.getPartitaIva().equals(newOperatore.getPartitaIva())) {
				pst.setString(++i, oldOperatore.getPartitaIva());
				pst.setString(++i, newOperatore.getPartitaIva());
			}

			if (!oldOperatore.getCodFiscale().equals(newOperatore.getCodFiscale())) {
				pst.setString(++i, oldOperatore.getCodFiscale());
				pst.setString(++i, newOperatore.getCodFiscale());
			}

			if (oldOperatore.getRappLegale() != null) {
				// Controllo cambiamenti dati rappresentante legale
				if (!oldOperatore.getRappLegale().getNome().equals(newOperatore.getRappLegale().getNome())) {
					pst.setString(++i, oldOperatore.getRappLegale().getNome());
					pst.setString(++i, newOperatore.getRappLegale().getNome());
				}

				if (!oldOperatore.getRappLegale().getCognome().equals(newOperatore.getRappLegale().getCognome())) {
					pst.setString(++i, oldOperatore.getRappLegale().getCognome());
					pst.setString(++i, newOperatore.getRappLegale().getCognome());
				}

				if (!oldOperatore.getRappLegale().getSesso().equals(newOperatore.getRappLegale().getSesso())) {
					pst.setString(++i, oldOperatore.getRappLegale().getSesso());
					pst.setString(++i, newOperatore.getRappLegale().getSesso());
				}

				if (!oldOperatore.getRappLegale().getComuneNascita()
						.equals(newOperatore.getRappLegale().getComuneNascita())) {
					pst.setString(++i, oldOperatore.getRappLegale().getComuneNascita());
					pst.setString(++i, newOperatore.getRappLegale().getComuneNascita());
				}

				if (oldOperatore.getRappLegale().getDataNascita() != null
						&& !oldOperatore.getRappLegale().getDataNascita()
								.equals(newOperatore.getRappLegale().getDataNascita())) {
					pst.setTimestamp(++i, oldOperatore.getRappLegale().getDataNascita());
					pst.setTimestamp(++i, newOperatore.getRappLegale().getDataNascita());
				}

				if (!oldOperatore.getRappLegale().getCodFiscale().equals(newOperatore.getRappLegale().getCodFiscale())) {
					pst.setString(++i, oldOperatore.getRappLegale().getCodFiscale());
					pst.setString(++i, newOperatore.getRappLegale().getCodFiscale());
				}
				if (oldOperatore.getRappLegale().getDocumentoIdentita() == null
						&& newOperatore.getRappLegale().getDocumentoIdentita() != null
						&& !newOperatore.getRappLegale().getDocumentoIdentita().equals("")) {
					pst.setString(++i, "null");
					pst.setString(++i, newOperatore.getRappLegale().getDocumentoIdentita());
				}
				if (oldOperatore.getRappLegale().getDocumentoIdentita() != null
						&& (!oldOperatore.getRappLegale().getDocumentoIdentita()
								.equals(newOperatore.getRappLegale().getDocumentoIdentita()))) {
					pst.setString(++i, oldOperatore.getRappLegale().getDocumentoIdentita());
					pst.setString(++i, newOperatore.getRappLegale().getDocumentoIdentita());
				}
			}

			if (oldSoggetto != null) {
				// Controllo cambiamenti dati responsabile stabilimento
				if (!oldSoggetto.getNome().equals(newSoggetto.getNome())) {
					pst.setString(++i, oldSoggetto.getNome());
					pst.setString(++i, newSoggetto.getNome());
				}

				if (!oldSoggetto.getCognome().equals(newSoggetto.getCognome())) {
					pst.setString(++i, oldSoggetto.getCognome());
					pst.setString(++i, newSoggetto.getCognome());
				}

				if (!oldSoggetto.getSesso().equals(newSoggetto.getSesso())) {
					pst.setString(++i, oldSoggetto.getSesso());
					pst.setString(++i, newSoggetto.getSesso());
				}

				if (!oldSoggetto.getComuneNascita().equals(newSoggetto.getComuneNascita())) {
					pst.setString(++i, oldSoggetto.getComuneNascita());
					pst.setString(++i, newSoggetto.getComuneNascita());
				}

				if (oldSoggetto.getDataNascita() != null
						&& !oldSoggetto.getDataNascita().equals(newSoggetto.getDataNascita())) {
					pst.setTimestamp(++i, oldSoggetto.getDataNascita());
					pst.setTimestamp(++i, newSoggetto.getDataNascita());
				}

				if (!oldSoggetto.getCodFiscale().equals(newSoggetto.getCodFiscale())) {
					pst.setString(++i, oldSoggetto.getCodFiscale());
					pst.setString(++i, newSoggetto.getCodFiscale());
				}
			}

			if (pst != null)
				pst.execute();
			pst.close();

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			/*
			 */
			// GestoreConnessioni.freeConnection(db);
			// DbUtil.chiudiConnessioneJDBC(null, null, db);
		}

		return true;
	}

	public int updateSoloRagioneSociale(Connection db) throws SQLException, UnknownHostException {

		int resultCount = 0;
		try {
			PreparedStatement pst = null;
			StringBuffer sql = new StringBuffer();

			// Operatore old = new Operatore( db, idOperatore);

			sql.append("UPDATE opu_operatore o SET ");
			// , partita_iva= ?,

			sql.append("ragione_sociale= ?");
			sql.append(",modified = now() ");

			if (codFiscale != null && !codFiscale.equals(""))
				sql.append(",codice_fiscale_impresa= ?");

			if (partitaIva != null && !partitaIva.equals(""))
				sql.append(",partita_iva= ?");

			if (note != null && !note.equals(""))
				sql.append(", note = ?");

			if (ipModifiedBy != null && !ipModifiedBy.equals(""))
				sql.append(", ipmodifiedby = ?");

			if (modifiedBy > -1)
				sql.append(", modifiedby = ?");

			sql.append(" where o.id = ?");

			int i = 0;

			pst = db.prepareStatement(sql.toString());

			pst.setString(++i, ragioneSociale);

			if (codFiscale != null && !codFiscale.equals(""))
				pst.setString(++i, codFiscale);

			if (partitaIva != null && !partitaIva.equals(""))
				pst.setString(++i, partitaIva);

			if (note != null && !note.equals(""))
				pst.setString(++i, note);

			if (ipModifiedBy != null && !ipModifiedBy.equals(""))
				pst.setString(++i, ipModifiedBy);

			if (modifiedBy > -1)
				pst.setInt(++i, modifiedBy);

			pst.setInt(++i, idOperatore);

			resultCount = pst.executeUpdate();
			pst.close();
			// pst chiuso prima
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {

		}

		return resultCount;

	}
	
	// Metodo per il controllo blocco canile
	public void controlloBloccoCanile(int idRelazioneAttivitaLineaProduttiva, Connection con) throws SQLException{
		//periodiDiBlocco(idRelazioneAttivitaLineaProduttiva,con);
		ResultSet rs = null;
		PreparedStatement pst = null;
		pst = con.prepareStatement(" select * from blocco_sblocco_canile where id_canile = ? and trashed=false  and trashed_date is null order by id desc limit 1");
		pst.setInt(1, idRelazioneAttivitaLineaProduttiva);
		rs = pst.executeQuery();
		if(rs.next()){
			this.setData_operazione_blocco(rs.getTimestamp("data_operazione"));
			this.setData_sospensione_blocco(rs.getTimestamp("data_sospensione"));
			this.setData_riattivazione_blocco(rs.getTimestamp("data_riattivazione"));
			this.setUser_id_blocco(rs.getInt("user_id"));
			this.setNote_blocco(rs.getString("note"));
			this.setMotivo_blocco(rs.getString("motivo"));
			this.setBloccato(rs.getBoolean("bloccato"));
		}
		// RIEMPIO LO STORICO DEI BLOCCHI
		this.setStoricoBlocco(periodiDiBlocco(idRelazioneAttivitaLineaProduttiva, con));
	}
	
	// Metodo per l'annullamento dell'operazione pianificata
	public void annullaBloccoSblocco(int idRelazioneAttivitaLineaProduttiva, Connection con) throws SQLException{
		ResultSet rs = null;
		PreparedStatement pst = null;
		// SELEZIONO L'ULTIMO INSERIMENTO UTILE
		int max_id=0;
		boolean bloccato = false;
		pst = con.prepareStatement(" select id,bloccato from blocco_sblocco_canile where id_canile = ? and trashed=false  and trashed_date is null order by id desc limit 1");
		pst.setInt(1, idRelazioneAttivitaLineaProduttiva);
		rs = pst.executeQuery();
		if(rs.next()){
			max_id=rs.getInt("id");
			bloccato=rs.getBoolean("bloccato");
		}

		// ELIMINO LA PIANIFICAZIONE INDESIDERATA
		String delete = "UPDATE blocco_sblocco_canile set TRASHED_DATE=now() WHERE id = ? ;";
		pst = con.prepareStatement(delete);
		pst.setInt(1, max_id);
		pst.executeUpdate();
		
		String annulla="";
		if(!bloccato){
			annulla=", data_riattivazione=null ";
		}
		// RIATTIVO L'ULTIMO STATO UTILE
		String update = "UPDATE blocco_sblocco_canile SET trashed = false" + annulla+" where "
				+ "id = (select id from blocco_sblocco_canile where id < " + max_id + " and id_canile = ? and trashed_date is null order by id desc limit 1 )";
		pst = con.prepareStatement(update);
		pst.setInt(1, idRelazioneAttivitaLineaProduttiva);
		pst.executeUpdate();		

		pst.close();
	}

	

	// Metodo per l'inserimento di un blocco o di uno sblocco
	public void inserisciBloccoSblocco(int idRelazioneAttivitaLineaProduttiva, Connection con) throws SQLException{
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		String chiudi_finestra_blocco="";
		if(!this.isBloccato()){
			chiudi_finestra_blocco=", data_riattivazione = ? ";
		}
		
		String update = "UPDATE blocco_sblocco_canile SET trashed = true" + chiudi_finestra_blocco + " where id = "
				+ "(select id from blocco_sblocco_canile where id_canile = ? and trashed=false  and trashed_date is null order by id desc limit 1 )";
		pst = con.prepareStatement(update);
		int i=0;
		if(!this.isBloccato()){
			pst.setTimestamp(++i, this.getData_riattivazione_blocco());
		}
		
		pst.setInt(++i, idRelazioneAttivitaLineaProduttiva);
		
		
		pst.executeUpdate();
		
		
		int id= DatabaseUtils.getNextSeq(con, "blocco_sblocco_canile_seq");
		String insert="INSERT INTO blocco_sblocco_canile(id, id_canile, ";
		
		if(this.isBloccato())
			insert+=" data_sospensione,";
		else
			insert+=" data_riattivazione,";
		
		insert+="user_id, note, motivo, bloccato, trashed)  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		i = 0;
		pst = con.prepareStatement(insert);

		pst.setInt(++i, id);
		pst.setInt(++i, idRelazioneAttivitaLineaProduttiva);
		if(this.isBloccato())
			pst.setTimestamp(++i, this.getData_sospensione_blocco());
		else
			pst.setTimestamp(++i, this.getData_riattivazione_blocco());
		pst.setInt(++i, this.getUser_id_blocco());
		pst.setString(++i, this.getNote_blocco());
		pst.setString(++i, this.getMotivo_blocco());
		pst.setBoolean(++i, this.isBloccato());
		pst.setBoolean(++i, false);
		pst.execute();

		pst.close();
		}
	
	// CONTROLLO SE LA DATA DELLA REGISTRAZIONE RICADE IN UN PERIODO DI BLOCCO DEL CANILE
	public String controlloRegistrazioneInCanileBloccato(int idRelazioneAttivitaLineaProduttiva, Connection con, String data_registrazione) throws SQLException, ParseException{
		String canile_bloccato="";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date date_reg = format.parse(data_registrazione);
	    // Controllo in eventuale blocco corrente
		controlloBloccoCanile(idRelazioneAttivitaLineaProduttiva, con);
		if(this.isBloccato() && this.getData_riattivazione_blocco()==null){
			if (date_reg.compareTo(format.parse(this.getData_sospensione_blocco().toString()))>=0) {
				canile_bloccato="ERRORE! OPERAZIONE NON CONSENTITA. "
		    			+ "IL CANILE SELEZIONATO COME DETENTORE IN DATA REGISTRAZIONE " +data_registrazione.substring(0,10)+ " RISULTA BLOCCATO DAL "+this.getData_sospensione_blocco().toString().substring(0,10)+".";
			}
		}
		// Controllo in eventuali registrazioni passate
		List date =  this.getStoricoBlocco();
		for (int i=0; i<date.size(); i++){
			String data_i = (String) date.get(i);
			String data_min = data_i.split("&")[0];
			String data_max = data_i.split("&")[1];
		    java.util.Date date1 = format.parse(data_min+" 00:00:00");
		    java.util.Date date2 = format.parse(data_max+" 00:00:00");
		    if (date_reg.compareTo(date2)<0 && date_reg.compareTo(date1)>=0) {
		    	canile_bloccato="ERRORE! OPERAZIONE NON CONSENTITA. "
		    			+ "IL CANILE SELEZIONATO COME DETENTORE IN DATA REGISTRAZIONE " +data_registrazione.substring(0,10)+ " RISULTA BLOCCATO (DAL "+data_min.substring(0,10)+" AL "+data_max.substring(0,10)+").";
		    }
		}
		
		// CONTROLLO SE SI TRATTA DI UN CANILE CHIUSO/BLOCCATO PREGRESSO
		if(canile_bloccato.equals("")){
			ResultSet rs = null;
			PreparedStatement pst = null;
			pst = con.prepareStatement(" select * from opu_operatori_denormalizzati_view where id_rel_stab_lp = ? and data_fine::timestamp <= ?::timestamp ");
			pst.setInt(1, idRelazioneAttivitaLineaProduttiva);
			pst.setString(2, data_registrazione.toString().substring(0,10));
			rs = pst.executeQuery();
			if(rs.next()){
		    	canile_bloccato="ERRORE! OPERAZIONE NON CONSENTITA. "
		    			+ "IL CANILE RISULTA CHIUSO O BLOCCATO DAL " +rs.getString("data_fine").substring(0,10)+ ".";				
			}
		}
		
		
		return canile_bloccato;         
	}
	
	// CONTROLLO SE, INSERITA LA DATA DI BLOCCO, CI SIANO DELLE REGISTRAZIONE GIA' EFFETTUATE 
	// CON DATA POSTERIORE
	public String controlloRegistrazioneGiaEffettuateInCanileBloccato(int idCanile, Connection con, String data_blocco) throws SQLException, ParseException{
		String canile_bloccato="";
		PreparedStatement pst = con.prepareStatement(
				"select id_evento, data_registrazione as data_evento,  'AGGIUNTA ANIMALE' as tipo_evento from evento_registrazione_bdu where id_detentore_registrazione_bdu = ?   and data_registrazione::timestamp >= ?::timestamp union "+
				"select id_evento, data_cattura as data_evento,  'CATTURA' as tipo_evento from evento_cattura where id_detentore_cattura = ? and data_cattura::timestamp >= ?::timestamp union "+
				"select id_evento, data_cambio_detentore as data_evento,  'CAMBIO DETENTORE' as tipo_evento from evento_cambio_detentore where id_detentore = ? and data_cambio_detentore::timestamp >= ?::timestamp union "+
				"select id_evento, data_cessione as data_evento,  'CESSIONE' as tipo_evento from evento_cessione where id_nuovo_detentore_cessione = ? and data_cessione::timestamp >= ?::timestamp union "+
				"select id_evento, data_presa_in_carico as data_evento,  'PRESA IN CARICO DA CESSIONE' as tipo_evento from evento_presa_in_carico_cessione where id_nuovo_proprietario_presa_cessione = ? and data_presa_in_carico::timestamp >= ?::timestamp union "+
				"select id_evento, data_ritrovamento as data_evento,  'RITROVAMENTO' as tipo_evento from evento_ritrovamento where id_detentore_dopo_ritrovamento = ? and data_ritrovamento::timestamp >= ?::timestamp union "+
				"select id_evento, data_ritrovamento_nd  as data_evento,  'RITROVAMENTO NON DENUNCIATO' as tipo_evento from evento_ritrovamento_non_denunciato where id_detentore_dopo_ritrovamento_nd = ? and data_ritrovamento_nd::timestamp >= ?::timestamp union "+
				"select id_evento, data_trasferimento  as data_evento,  'TRASFERIMENTO' as tipo_evento from evento_trasferimento where id_nuovo_detentore = ? and data_trasferimento::timestamp >= ?::timestamp union "+
				"select id_evento, data_trasferimento_canile as data_evento,  'TRASFERIMENTO CANILE' as tipo_evento from evento_trasferimento_canile where id_canile_detentore = ? and data_trasferimento_canile::timestamp >= ?::timestamp 	");
		for (int i=0; i < 18; i=i) if(i%2==0) pst.setInt(++i, idCanile); else pst.setString(++i, data_blocco.toString().substring(0,10));
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			canile_bloccato+="- Operazione di "+rs.getString("tipo_evento")+" effettuata in data "+rs.getString("data_evento").subSequence(0, 10)+".\\n";
		}
		return canile_bloccato;
	}
	
	
	// RESTITUISCE I PERIODI BOCCATI DI UN CANILE
	public List periodiDiBlocco(int idRelazioneAttivitaLineaProduttiva, Connection con) throws SQLException{
		List date = new ArrayList();
		PreparedStatement pst = con.prepareStatement("select * from blocco_sblocco_canile where id_canile = ? and (data_sospensione is not null and data_riattivazione is not null) and trashed_date is null");
		pst.setInt(1, idRelazioneAttivitaLineaProduttiva);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			date.add(rs.getString("data_sospensione").substring(0,10)+"&"+rs.getString("data_riattivazione").subSequence(0, 10));
		}
		return date;
	}
	
	
	public static void unificaProprietari(Connection db, int idLineaDaRimuovere, int idLineaDaConservare,
			int idOperatoreDaRimuovere, int idAslOperatoreDaRimuovere, int idAslOperatoreDaConservare, int user_id)
			throws Exception {

		PreparedStatement pst = db
				.prepareStatement("select * from public_functions.unifica_proprietari(?, ?, ?, ?, ?, ?)");
		int k = 0;
		pst.setInt(++k, idLineaDaRimuovere);
		pst.setInt(++k, idLineaDaConservare);
		pst.setInt(++k, idOperatoreDaRimuovere);
		pst.setInt(++k, idAslOperatoreDaRimuovere);
		pst.setInt(++k, idAslOperatoreDaConservare);
		pst.setInt(++k, user_id);

		pst.execute();

		// Aggiorno tabelle con chiave esterna

		ArrayList<HashMap<String, String>> listaTabelle = DbUtil.getTabelleCampiFk(
				"opu_relazione_stabilimento_linee_produttive", "id", "opu");

		Iterator i = listaTabelle.iterator();

		while (i.hasNext()) {

			HashMap<String, String> map = (HashMap<String, String>) i.next();

			Set list = map.keySet();
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				String tabella = (String) iter.next();
				String campo = (String) map.get(tabella);
				pst = db.prepareStatement("update " + tabella + " set " + campo + " = ? where " + campo + " = ?");
				k = 0;
				pst.setInt(++k, idLineaDaConservare);
				pst.setInt(++k, idLineaDaRimuovere);

				pst.execute();
			}

		}

		pst.close();

	}

	public EsitoControllo verificaEliminazione(Connection db) throws Exception {

		EsitoControllo esito = new EsitoControllo();
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean ret = false;

		Stabilimento stab = (Stabilimento) this.getListaStabilimenti().get(0);
		LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

		pst = db.prepareStatement("select * from public_functions.checkProprietarioDetentoreAnimali(" + lp.getId()
				+ ")");

		rs = pst.executeQuery();
		if (rs.next()) {
			ret = rs.getBoolean("checkProprietarioDetentoreAnimali");
		}

		if (ret) {
			esito.setIdEsito(-1);
			esito.setDescrizione("Il proprietario ha animali assegnati, non è possibile procedere con l'eliminazione");
			return esito;
		}

		// Controllo riferimenti in tabelle esterne
		ArrayList<HashMap<String, String>> listaTabelle = DbUtil.getTabelleCampiFk(
				"opu_relazione_stabilimento_linee_produttive", "id", "opu");

		Iterator i = listaTabelle.iterator();

		while (i.hasNext()) {

			HashMap<String, String> map = (HashMap<String, String>) i.next();

			Set list = map.keySet();
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				String tabella = (String) iter.next();
				String campo = (String) map.get(tabella);
				

				if (!tabella.equals("evento_presa_modifica_residenza_operatore") && !tabella.equals("evento_modifica_residenza_operatore") && tabella.startsWith("evento_")) {
					try {

						pst = db.prepareStatement("select * from " + tabella
								+ " t left join evento e on (e.id_evento = t.id_evento)  where " + campo
								+ " = ? and e.trashed_date is null and e.data_cancellazione is null");
						pst.setInt(1, lp.getId());
					//	System.out.println(pst.toString());
						rs = pst.executeQuery();
					} catch (SQLException e) {
						if (("42703").equals(e.getSQLState())) {
							pst = db.prepareStatement("select * from " + tabella
									+ " t left join registrazione_operatore e on (t.id_registrazione = e.id)  where " + campo
									+ " = ? and e.data_cancellazione is null");
							pst.setInt(1, lp.getId());
						//	System.out.println(pst.toString());
							rs = pst.executeQuery();

						}

					}
				} else if(tabella.equals("evento_modifica_residenza_operatore") || tabella.equals("evento_presa_modifica_residenza_operatore")){
					pst = db.prepareStatement("select * from " + tabella + " where " + campo
							+ " = ? ");
					pst.setInt(1, lp.getId());
					try {
						rs = pst.executeQuery();
					} catch (SQLException e) {
						if (("42703").equals(e.getSQLState())) {
							try {
								pst = db.prepareStatement("select * from " + tabella + " where " + campo
										+ " = ? and data_cancellazione is null");
								pst.setInt(1, lp.getId());
								rs = pst.executeQuery();
							} catch (SQLException e1) {
								if (("42703").equals(e1.getSQLState())) {
									pst = db.prepareStatement("select * from " + tabella + " where " + campo + " = ?");
									pst.setInt(1, lp.getId());
									rs = pst.executeQuery();

								}

							}
						}
					}
				}
				else {
					pst = db.prepareStatement("select * from " + tabella + " where " + campo
							+ " = ? and trashed_date is null");
					pst.setInt(1, lp.getId());
					try {
						rs = pst.executeQuery();
					} catch (SQLException e) {
						if (("42703").equals(e.getSQLState())) {
							try {
								pst = db.prepareStatement("select * from " + tabella + " where " + campo
										+ " = ? and data_cancellazione is null");
								pst.setInt(1, lp.getId());
								rs = pst.executeQuery();
							} catch (SQLException e1) {
								if (("42703").equals(e1.getSQLState())) {
									pst = db.prepareStatement("select * from " + tabella + " where " + campo + " = ?");
									pst.setInt(1, lp.getId());
									rs = pst.executeQuery();

								}

							}
						}
					}
				}
				if (rs.next()) {
					esito.setIdEsito(-1);
					esito.setDescrizione("Il proprietario ha riferimenti nella tabella " + tabella + ", campo " + campo
							+ ", non è possibile procedere con l'eliminazione");
					return esito;

				}


			}


		}

		return esito;
	}

	public void delete(Connection db, String noteCancellazione) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;

		Stabilimento stab = (Stabilimento) this.getListaStabilimenti().get(0);
		LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

		pst = db.prepareStatement("select * from public_functions.deleteOperatore(" + lp.getId() + ", '"
				+ noteCancellazione.replaceAll("'",  "''") + "')");

		pst.executeQuery();

	}
}
