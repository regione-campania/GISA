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
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.suap.actions.StabilimentoAction;
import org.aspcfs.modules.suap.base.SoggettoFisico;
import org.aspcfs.modules.suap.base.Operatore;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;
import org.json.JSONObject;

import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.newvaluescorrelationname;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Operatore extends GenericBean {


	public static final int OP_NUOVO_STAB = 1, OP_AMPLIAMENTO = 2, OP_CESSAZIONE=3, OP_VARIAZIONE = 4;
	
	protected int idOperatore;
	protected String ragioneSociale;
	protected String partitaIva;
	protected String codFiscale;
	protected String telefono1;
	protected String telefono2;
	protected String email;
	protected String fax;
	protected String note;
	protected int modifiedBy = -1;
	protected int enteredBy = -1;
	protected String ipEnteredBy;
	protected String ipModifiedBy;
	protected java.sql.Timestamp dataInizio;
	protected java.sql.Timestamp entered;
	protected java.sql.Timestamp modified;
	protected String domicilioDigitale ;
	private Indirizzo sedeLegaleImpresa = new Indirizzo();
	protected SoggettoFisico rappLegale;
	protected SedeList listaSediOperatore = new SedeList();
	protected String action ;
	protected int tipo_impresa	;
	protected int tipo_societa ;
	protected String codiceInternoImpresa;
	protected int idOperazione ;
	protected StabilimentoList listaStabilimenti = new StabilimentoList();
	
	private boolean aslPossessoDocumenti;
	
	public boolean isAslPossessoDocumenti() {
		return aslPossessoDocumenti;
	}





	public void setAslPossessoDocumenti(boolean aslPossessoDocumenti) {
		this.aslPossessoDocumenti = aslPossessoDocumenti;
	}

	
	protected boolean validato = false;
	
	public boolean isValidato() {
		return validato;
	}

	public void setValidato(boolean validato) {
		this.validato = validato;
	}

	public StabilimentoList getListaStabilimenti()
	{
		return listaStabilimenti;
	}
	
	public void setListaStabilimenti(StabilimentoList li)
	{
		listaStabilimenti = li;
	}
	
	public int getIdOperazione() {
		return idOperazione;
	}
	
	public String getDescrizioneOperazione() {
		String operazione= "" ;
		switch(this.idOperazione)
		{
		case StabilimentoAction.SCIA_NUOVO_STABILIMENTO :
		{
			operazione = "NUOVO STABILIMENTO";
			break ;
		}
		case StabilimentoAction.SCIA_AMPLIAMENTO :
		{
			operazione = "AMPLIAMENTO";
			break ;
		}
		case StabilimentoAction.SCIA_CESSAZIONE :
		{
			operazione = "CESSAZIONE";
			break ;
		}
		case StabilimentoAction.SCIA_MODIFICA_STATO_LUOGHI :
		{
			operazione = "MODIFICA STATO DEI LUOGHI";
			break ;
		}
		case StabilimentoAction.SCIA_VARIAZIONE_TITOLARITA :
		{
			operazione = "VARIAZIONE DI TITOLARITA";
			break ;
		}
		}
		
		return operazione;
	}

	public void setIdOperazione(int idOperazione) {
		this.idOperazione = idOperazione;
	}

	public Indirizzo getSedeLegaleImpresa() {
		return sedeLegaleImpresa;
	}

	public void setSedeLegaleImpresa(Indirizzo sedeLegaleImpresa) {
		this.sedeLegaleImpresa = sedeLegaleImpresa;
	}

	public int getTipo_impresa() {
		return tipo_impresa;
	}

	public void setTipo_impresa(int tipo_impresa) {
		this.tipo_impresa = tipo_impresa;
	}

	public int getTipo_societa() {
		return tipo_societa;
	}

	public void setTipo_societa(int tipo_societa) {
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

	


	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}

	public void setDomicilioDigitale(String domicilioDigitale) {
		this.domicilioDigitale = domicilioDigitale;
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
		return (ragioneSociale !=null ? ragioneSociale.trim() :"");
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPartitaIva() {
		return (partitaIva !=null ? partitaIva.trim() :"");
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
		return (codFiscale !=null ? codFiscale.trim() :"");
		
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

	public boolean insert(Connection db,ActionContext context) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			modifiedBy = enteredBy;

			
			idOperatore = DatabaseUtils.getNextSeq(db, "suap_ric_scia_operatore_id_seq");
			sql.append("INSERT INTO suap_ric_scia_operatore (");

			sql.append("id_tipo_richiesta,tipo_impresa,tipo_societa,ragione_sociale,codice_fiscale_impresa, "
					+ "partita_iva");

			if (idOperatore > -1) {
				sql.append(",id ");
			}

			if (note!= null && ! note.equals("")) {
				sql.append(", note ");
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

			sql.append(", entered, modified,domicilio_digitale,id_indirizzo");
			
			sql.append(")");

			sql.append("VALUES (?,?,?,?,?,?");

			if (idOperatore > -1) {
				sql.append(",? ");
			}
			if (note!= null && ! note.equals("")) {
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

			sql.append(", ?, ?,?,? ");

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idOperazione);
			pst.setInt(++i, tipo_impresa);
			pst.setInt(++i, tipo_societa);
			pst.setString(++i, this.getRagioneSociale());
			pst.setString(++i, this.getCodFiscale());
			pst.setString(++i, this.getPartitaIva());
			if (idOperatore > -1) {
				pst.setInt(++i, idOperatore);
			
			}
			if (note!= null && ! note.equals("")) {
				pst.setString(++i, this.note);
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
			
			pst.setString(++i, domicilioDigitale);
			
			Indirizzo sedeLegale =  new Indirizzo();
			SedeList listaInd = this.getListaSediOperatore();
			Iterator<Indirizzo> it = listaInd.iterator();
			if (it.hasNext()) {

				sedeLegale = it.next();
				
				
			}
			pst.setInt(++i, sedeLegale.getIdIndirizzo());

			pst.execute();
			pst.close();
			
			aggiungiInfoSuAslPossessoDocumenti(db);

			if (this.getRappLegale() != null)
				this.aggiungiRelazioneSoggettoFisico(db, 1,context);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	private void aggiungiInfoSuAslPossessoDocumenti(Connection db) {
		PreparedStatement pst = null;
		
		try
		{
			
			pst = db.prepareStatement("insert into allegati_in_possesso_asl(id_richiesta,in_possesso) values(?,?)");
			pst.setInt(1, getIdOperatore());
			pst.setBoolean(2, isAslPossessoDocumenti());
			pst.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
		}
		
		
	}





	public void queryRecordOperatore(Connection db, int idOperatore)
	throws SQLException, IndirizzoNotFoundException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}
		
		PreparedStatement pst = db
		.prepareStatement("Select *, o.id as idOperatore from suap_ric_scia_operatore o where o.id = ?");
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		
		buildRappresentante(db);

		listaSediOperatore.setIdOperatore(idOperatore);
		listaSediOperatore.setOnlyActive(1);
		listaSediOperatore.buildListSediOperatore(db);
		if(listaSediOperatore.size()>0 && this.tipo_impresa!=1 )
			sedeLegaleImpresa= (Indirizzo)listaSediOperatore.get(0);
		else
			if (rappLegale!=null)
			sedeLegaleImpresa = rappLegale.getIndirizzo();

	
		listaStabilimenti.setIdOperatore(idOperatore);
		
		listaStabilimenti.setFlag_dia(false);
		listaStabilimenti.buildList(db);
		
		
		rs.close();
		pst.close();
		
		
	}
	

	public void queryRecordOperatoreEsclusaSedeProduttiva(Connection db, int idOperatore)
	throws SQLException, IndirizzoNotFoundException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
		.prepareStatement("Select *, o.id as idOperatore from suap_ric_scia_operatore o where o.id = ?");
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
			sedeLegaleImpresa = new Indirizzo(db,rs.getInt("id_indirizzo"));
		}

		if (idOperatore == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		
		
		rs.close();
		pst.close();
		buildRappresentante(db);
		rs.close();
		pst.close();
	}
	
	
	
	
	public void updateSoggettoFisico(Connection db,ActionContext context) throws SQLException
	{
		if (this.getRappLegale() != null)
			this.aggiungiRelazioneSoggettoFisico(db, 1,context);
	}
	
	public void queryRecordOperatorePartitaIva(Connection db, String pIva)
	throws SQLException, IndirizzoNotFoundException {

		

		PreparedStatement pst = db
		.prepareStatement("Select *, o.id as idOperatore from suap_ric_scia_operatore o where o.partita_iva ilike ?");
		pst.setString(1, pIva);
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


	

	public void queryRecordOperatorebyCodiceFiscale(Connection db,
			String codiceFiscale) throws SQLException, IllegalAccessException, InstantiationException {

		if (idOperatore == -1) {
			throw new SQLException("Invalid Account");
		}

		int idLineaProduttiva = -1;

		PreparedStatement pst = db
		.prepareStatement("Select o.*, o.id as idOperatore, r.id as idlinea "
				+ "from suap_ric_scia_operatore o  "
				+ "join suap_ric_scia_stabilimento s on s.id_operatore = o.id "
				+ "join suap_ric_scia_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id "
				+ "where o.codice_fiscale = ?");
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
		listaSediOperatore.buildListSediOperatore(db);


		rs.close();
		pst.close();
		buildRappresentante(db);
		rs.close();
		pst.close();
	}

	public void setRequestItems(ActionContext context) {
		
		rappLegale = new SoggettoFisico(context.getRequest());
	}


   
    
	protected void buildRecord(ResultSet rs) throws SQLException {

		this.setIdOperatore(rs.getInt("idOperatore"));
		this.setRagioneSociale(rs.getString("ragione_sociale"));
		this.setCodFiscale(rs.getString("codice_fiscale_impresa"));
		this.setPartitaIva(rs.getString("partita_iva"));
		this.tipo_impresa=rs.getInt("tipo_impresa");
		this.tipo_societa= rs.getInt("tipo_societa");
		this.setDomicilioDigitale(rs.getString("domicilio_digitale"));
		
		
		try
		{
			this.setEnteredBy(rs.getInt("enteredby"));
			this.setModifiedBy(rs.getInt("modifiedby"));
			this.setEntered(rs.getTimestamp("entered"));
			this.setModified(rs.getTimestamp("modified"));
			this.idOperazione = rs.getInt("id_tipo_richiesta");
			this.validato = rs.getBoolean("validato");

		}catch(SQLException e)
		{
			
		}
		
		

	}  



	public boolean aggiungiRelazioneSoggettoFisico(Connection db, int tipoLegame,ActionContext context)
	throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			// Controllare se c'e' gia' soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db, "suap_ric_scia_rel_operatore_soggetto_fisico_id_seq");

			sql.append("update suap_ric_scia_rel_operatore_soggetto_fisico set enabled = false where id_operatore =?; INSERT INTO suap_ric_scia_rel_operatore_soggetto_fisico (");

			if (idRelazione > -1)
				sql.append("id,");

			sql
			.append("id_operatore, id_soggetto_fisico, tipo_soggetto_fisico, data_inizio, stato_ruolo,enabled");

			sql.append(")");

			sql.append(" VALUES ( ");
			
			if (idRelazione > -1) {
				sql.append("?,");
			}
			
			sql.append(" ?, ?, ?, ?, ?,true ");
			
			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idOperatore);
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

	public boolean aggiornaRelazioneSoggettoFisico(Connection db, int tipoLegame,ActionContext context)
	throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar
					.getTime().getTime());

			// Disabilito vecchia relazione

			PreparedStatement pst = db
			.prepareStatement("Update suap_ric_scia_rel_operatore_soggetto_fisico set stato_ruolo = 2, data_fine = ? where "
					+ "stato_ruolo = 1 and "
					+ "id_operatore = ? "
					+ "and tipo_soggetto_fisico = ? ");

			int i = 0;
			pst.setTimestamp(++i, currentTimestamp);
			pst.setInt(++i, idOperatore);
			pst.setInt(++i, tipoLegame);
			int resultCount = pst.executeUpdate();
			pst.close();

			this.aggiungiRelazioneSoggettoFisico(db, 1,context);

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;
	}


	
	
	
	
	
	public boolean modificaSedeLegale(Connection db, Indirizzo indirizzo)
			throws SQLException {

				StringBuffer sql = new StringBuffer();
				try {
					
					sql.append("UPDATE suap_ric_scia_operatore SET id_indirizzo = ? WHERE id = ? ");
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
			.append("SELECT distinct i.civico,comnasc.id as  id_comune_nascita," +
					"o.nome,o.cognome," +
					"o.codice_fiscale,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
					"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
					" o.id as id_soggetto ,i.toponimo," +
					"i.id,i.comune,i.comune_testo,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune," +
					"lp.description as descrizione_provincia, lt.description as descrizione_toponimo   " 
					+ "FROM suap_ric_scia_soggetto_fisico o "
					+ "left join comuni1 comnasc on (comnasc.nome ilike o.comune_nascita) " 
					+ "left join opu_indirizzo i on o.indirizzo_id=i.id "
					+ "left join comuni1 on (comuni1.id = i.comune) "
					+ "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
					+ "left join lookup_province lp on lp.code = comuni1.cod_provincia::int "
					+ "left join lookup_toponimi lt on lt.code = i.toponimo::int "
					+ "join suap_ric_scia_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) and os.enabled "
					+ "join suap_ric_scia_operatore op on (os.id_operatore = op.id)  where os.id_operatore = ? and stato_ruolo = 1");

			PreparedStatement pst = db.prepareStatement(sqlSelect.toString());

			int i = 0;
			pst.setInt(++i, idOperatore);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRappresentanteLegale(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

		}

	}

	public void buildRappresentanteOpu(Connection db) {
		SoggettoFisico rapp = new SoggettoFisico();

		StringBuffer sql = new StringBuffer();
		try {

			StringBuffer sqlSelect = new StringBuffer("");
			sqlSelect
			.append("SELECT distinct i.civico,comnasc.id as  id_comune_nascita," +
					"o.nome,o.cognome," +
					"o.codice_fiscale,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
					"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
					" o.id as id_soggetto ,i.toponimo," +
					"i.id,i.comune,i.comune_testo,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune," +
					"lp.description as descrizione_provincia   " 
					+ "FROM opu_soggetto_fisico o "
					+ "left join comuni1 comnasc on (comnasc.nome ilike o.comune_nascita) " 
					+ "left join opu_indirizzo i on o.indirizzo_id=i.id "
					+ "left join comuni1 on (comuni1.id = i.comune) "
					+ "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
					+ "left join lookup_province lp on lp.code = comuni1.cod_provincia::int "
					+ "join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) and os.enabled "
					+ "join opu_operatore op on (os.id_operatore = op.id)  where os.id_operatore = ? ");

			PreparedStatement pst = db.prepareStatement(sqlSelect.toString());

			int i = 0;
			pst.setInt(++i, idOperatore);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRappresentanteLegale(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

		}

	}

public static List<Operatore> checkEsistenzaOperatoreSuap(Connection db,String partitaIva ) throws IndirizzoNotFoundException 
{
		
		String query = "select distinct idOperatore as idOperatore,codice_fiscale_impresa  ,  note , partita_iva, ragione_sociale ,domicilio_digitale ,tipo_impresa ,tipo_societa, id_indirizzo from suap_get_lista_richieste(?,null,null) ";
		
		Operatore operatore = null;
		List<Operatore> listaOp = new ArrayList<Operatore>();
		PreparedStatement pst;
		try 
		{
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setString(++i, partitaIva);
			//pst.setInt(++i, idComuneRichiesta);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) 
			{
				operatore = new Operatore();
				operatore.buildRecord(rs);
				
				operatore.buildRappresentante(db);

				operatore.setSedeLegaleImpresa(new Indirizzo(db, rs.getInt("id_indirizzo")));
				if (operatore.getSedeLegaleImpresa().getComune()<=0)
				{
					operatore.setSedeLegaleImpresa(operatore.getRappLegale().getIndirizzo());
				}
				
				inserisciinListaNoDuplicato(listaOp,operatore);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return listaOp;
	}


public int compareTo(Operatore op1,Operatore op2) {
    
	
	String denominazione = op2.getRagioneSociale();
	String pIva= op2.getPartitaIva();
	String codFiscale=op2.getCodFiscale();
	
	SoggettoFisico legaleRapp = op2.getRappLegale();
	Indirizzo indirizzo = op2.getSedeLegaleImpresa();
	
	if (
			op1.getRagioneSociale().equalsIgnoreCase(denominazione) &&
			op1.getPartitaIva().equalsIgnoreCase(pIva )&&
			op1.getCodFiscale().equalsIgnoreCase(codFiscale) && 
			op1.getRappLegale().compareTo(legaleRapp)==0 &&
					op1.getSedeLegaleImpresa().compareTo(indirizzo)==0
			)
		return 0;
	return 1 ;
	

}

private static void inserisciinListaNoDuplicato(List<Operatore> lista , Operatore op)
{
	
	boolean trovato = false ;
	
	for (Operatore operatore : lista)
	{
		if (op.compareTo(operatore,op)==0)
		{
			trovato =   true ;
			
			break;
		}
	}
	
	if(trovato==false)
		lista.add(op);
}
	
public List<Operatore> getFromOpu(Connection db){

	String query = "select * from suap_trova_impresa_opu(?)";
	
	Operatore operatore = null;
	List<Operatore> listaOp = new ArrayList<Operatore>();
	PreparedStatement pst;
	try {
		int i = 0;
		pst = db.prepareStatement(query);
		pst.setString(++i, this.getPartitaIva());
			 
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			operatore = new Operatore();
			operatore.buildRecord(rs);
			
			operatore.buildRappresentanteOpu(db);

			operatore.setSedeLegaleImpresa(new Indirizzo(db, rs.getInt("id_indirizzo")));
			if (operatore.getSedeLegaleImpresa().getComune()<=0)
			{
				operatore.setSedeLegaleImpresa(operatore.getRappLegale().getIndirizzo());
			}
			
			
			
			inserisciinListaNoDuplicato(listaOp,operatore);
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return listaOp;

}

public List<Operatore> getFromOrganization(Connection db){
	
	
String query = "select * from suap_trova_impresa_org(?)";
	
	Operatore operatore = null;
	List<Operatore> listaOp = new ArrayList<Operatore>();
	PreparedStatement pst;
	try {
		
		LookupList provinceList = new LookupList(db, "lookup_province");
		int i = 0;
		pst = db.prepareStatement(query);
		pst.setString(++i, this.getPartitaIva());
			 
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			operatore = new Operatore();
			operatore.buildRecord(rs);
			
			//Fix partite iva con spazi
			operatore.setPartitaIva(operatore.getPartitaIva().trim());
			
			SoggettoFisico rappLegale = new SoggettoFisico();
			
			rappLegale.setNome(rs.getString("rapp_nome"));
			rappLegale.setCognome(rs.getString("rapp_cognome"));
			rappLegale.setCodFiscale(rs.getString("rapp_cf"));
			
			String comuneNascita = fixDaComuni1(db, rs.getString("rapp_nascita_comune"));
			
			rappLegale.setComuneNascita(comuneNascita);
			
			String dataNascita = fixData(rs.getString("rapp_nascita_data"));
			rappLegale.setDataNascita(dataNascita);
			rappLegale.setDataNascitaString(dataNascita);
			
			Indirizzo residenzaLegale = new Indirizzo();
			
			int comuneResidenza = trovaIdComune(db, rs.getString("rapp_residenza_comune"));
			String comuneResidenzaTesto = trovaNomeComune(db, comuneResidenza);
			String provinciaResidenza = trovaCodeProvincia(db,comuneResidenza);
			String provinciaResidenzaTesto =provinceList.getSelectedValue(provinciaResidenza);
			residenzaLegale.setComune(comuneResidenza);
			residenzaLegale.setDescrizioneComune(comuneResidenzaTesto);
			residenzaLegale.setProvincia(provinciaResidenza);
			residenzaLegale.setDescrizione_provincia(provinciaResidenzaTesto);
			residenzaLegale.setVia(rs.getString("rapp_residenza_via"));
			
			rappLegale.setIndirizzo(residenzaLegale);
			
			operatore.setRappLegale(rappLegale);
						
			Indirizzo sedeLegale = new Indirizzo();
			int comuneLegale = trovaIdComune(db, rs.getString("legale_comune"));
			String comuneLegaleTesto = trovaNomeComune(db, comuneLegale);
			String provinciaLegale = trovaCodeProvincia(db,comuneLegale);
			String provinciaLegaleTesto =provinceList.getSelectedValue(provinciaLegale);
			sedeLegale.setComune(comuneLegale);
			sedeLegale.setDescrizioneComune(comuneLegaleTesto);
			sedeLegale.setProvincia(provinciaLegale);
			sedeLegale.setDescrizione_provincia(provinciaLegaleTesto);

			sedeLegale.setVia(rs.getString("legale_via"));
			operatore.setSedeLegaleImpresa(sedeLegale);
			
			inserisciinListaNoDuplicato(listaOp,operatore);
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return listaOp;
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

private String fixDaComuni1(Connection db, String comune){
String query = "select nome from comuni1 where nome ilike ?";
	PreparedStatement pst;
	
		int i = 0;
		try {
			pst = db.prepareStatement(query);
		
		pst.setString(++i, comune);
		ResultSet rs = pst.executeQuery();

		if (rs.next())
			return rs.getString("nome");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
}

private String fixData(String currentDate){
	try
	 {
	  
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    java.util.Date tempDate=simpleDateFormat.parse(currentDate);
	    SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/YYYY");           
	  return outputDateFormat.format(tempDate);
	  } catch (ParseException ex) 
	  {
	        System.out.println("Parse Exception");
	  }
	return "";
}

}
