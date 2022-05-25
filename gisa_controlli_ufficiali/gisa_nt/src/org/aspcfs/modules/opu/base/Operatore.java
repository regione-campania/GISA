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
import java.sql.SQLException;
import java.sql.Timestamp;
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



	
	protected boolean flagFuoriRegione = false ;
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
	protected boolean presentataScia ;
	protected String domicilioDigitale ;
	protected boolean flagRicCe ;
	protected String numRicCe ;
	
	private Indirizzo sedeLegaleImpresa = new Indirizzo();
	protected SoggettoFisico rappLegale;
	protected SoggettoFisicoList storicoSoggettoFisico = new SoggettoFisicoList();
	protected SedeList listaSediOperatore = new SedeList();
	
	
	protected StabilimentoList listaStabilimenti = new StabilimentoList();
	protected OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
	protected OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
	
	protected String action ;

	protected int tipo_impresa	;
	protected int tipo_societa ;
	protected String codiceInternoImpresa;
	private int idOperatorePrecedente;
	
	
	
	
	
	public int getIdOperatorePrecedente() {
		return idOperatorePrecedente;
	}

	public void setIdOperatorePrecedente(int idOperatorePrecedente) {
		this.idOperatorePrecedente = idOperatorePrecedente;
	}

	public boolean isPresentataScia() {
		return presentataScia;
	}

	public void setPresentataScia(boolean presentataScia) {
		this.presentataScia = presentataScia;
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
		if(tipo_societa<=0)
			return -1;
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
		this.setSedeLegaleImpresa(new Indirizzo());
		this.rappLegale=new SoggettoFisico();
		
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

	public boolean isFlagRicCe() {
		return flagRicCe;
	}

	public void setFlagRicCe(boolean flagRicCe) {
		this.flagRicCe = flagRicCe;
	}

	public String getNumRicCe() {
		return numRicCe;
	}

	public void setNumRicCe(String numRicCe) {
		this.numRicCe = numRicCe;
	}

	public boolean isFlagFuoriRegione() {
		return flagFuoriRegione;
	}

	public void setFlagFuoriRegione(boolean flagFuoriRegione) {
		this.flagFuoriRegione = flagFuoriRegione;
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



	public OrganizationPhoneNumberList getPhoneNumberList() {
		return phoneNumberList;
	}

	public void setPhoneNumberList(OrganizationPhoneNumberList phoneNumberList) {
		this.phoneNumberList = phoneNumberList;
	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public OrganizationEmailAddressList getEmailAddressList() {
		return emailAddressList;
	}

	public void setEmailAddressList(
			OrganizationEmailAddressList emailAddressList) {
		this.emailAddressList = emailAddressList;
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
		boolean doCommit = false;
		try {
			modifiedBy = enteredBy;

		
			idOperatore = DatabaseUtils.getNextSeq(db, "opu_operatore_id_seq");
			sql.append("INSERT INTO opu_operatore (");

			sql.append("tipo_impresa,tipo_societa,ragione_sociale, codice_fiscale_impresa,id_operatore_precedente, "
					+ "partita_iva");

			if (idOperatore > -1) {
				sql.append(",id,codice_interno_impresa ");
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

			sql.append(", entered, modified,domicilio_digitale,flag_ric_ce,num_ric_ce,id_indirizzo,flag_clean");

			sql.append(")");

			sql.append("VALUES (?,?,?,?,?,?,?,?,?");

			if (idOperatore > -1) {
				sql.append(",?,? ");
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

			sql.append(", ?, ? , ?,true");

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, tipo_impresa);
			pst.setInt(++i, tipo_societa);
			pst.setString(++i, this.getRagioneSociale());
			pst.setString(++i, this.getCodFiscale());
			pst.setInt(++i, this.getIdOperatorePrecedente());
			pst.setString(++i, this.getPartitaIva());
			if (idOperatore > -1) {
				pst.setInt(++i, idOperatore);
				if(codiceInternoImpresa!=null)
					pst.setString(++i, ""+codiceInternoImpresa);
				else
					pst.setString(++i, ""+idOperatore);
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
			pst.setBoolean(++i, flagRicCe);
			pst.setString(++i, numRicCe);
			
			Indirizzo sedeLegale =  new Indirizzo();
			SedeList listaInd = this.getListaSediOperatore();
			Iterator<Indirizzo> it = listaInd.iterator();
			if (it.hasNext()) {

				sedeLegale = it.next();
				
				
			}
			pst.setInt(++i, sedeLegale.getIdIndirizzo());
			
			

			pst.execute();
			pst.close();

		
			if (this.getRappLegale() != null)
				this.aggiungiRelazioneSoggettoFisico(db, 1,context);

			
			

			

		
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;

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
	
	public void queryRecordOperatoreSenzaStabilimenti(Connection db, int idOperatore)
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
				
				buildRappresentante(db);

				listaSediOperatore.setIdOperatore(idOperatore);
				listaSediOperatore.setOnlyActive(1);
				listaSediOperatore.buildListSediOperatore(db);
				if(listaSediOperatore.size()>0 && this.tipo_impresa!=1 )
					sedeLegaleImpresa= (Indirizzo)listaSediOperatore.get(0);
				else
					if (rappLegale!=null)
					sedeLegaleImpresa = rappLegale.getIndirizzo();

				
				
				
				rs.close();
				pst.close();
				
				
			}
	
	public void queryRecordOperatorePerSuap(Connection db, int idOperatore)
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
		.prepareStatement("Select *, o.id as idOperatore from opu_operatore o where o.id = ?");
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
	
	
	
	public void queryRecordOperatoreStorico(Connection db, int idOperatore)
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
				listaSediOperatore.buildListSediOperatoreStorico(db);

				/*listaStabilimenti.setIdOperatore(idOperatore);
				listaStabilimenti.setFlag_dia(false);
				listaStabilimenti.buildList(db);*/

				rs.close();
				pst.close();
				storicoSoggettoFisico.setIdOperatore(idOperatore);
				storicoSoggettoFisico.buildListStorico(db);
					
				rs.close();
				pst.close();
			}
	
	public void updateSoggettoFisico(Connection db,ActionContext context) throws SQLException
	{
		if (this.getRappLegale() != null)
			this.aggiungiRelazioneSoggettoFisico(db, 1,context);
	}
	
	
	public void updateSedeLegale(Connection db,ActionContext context) throws SQLException
	{
		
		
		PreparedStatement pst = db.prepareStatement("UPDATE opu_operatore set id_indirizzo = ? where id = ?");
		pst.setInt(1, this.getSedeLegale().getIdIndirizzo());
		pst.setInt(2, this.getIdOperatore());
		pst.execute();
	}
	
	public void queryRecordOperatorePartitaIva(Connection db, String pIva)
	throws SQLException, IndirizzoNotFoundException {

		

		PreparedStatement pst = db
		.prepareStatement("Select *, o.id as idOperatore from opu_operatore o where o.partita_iva ilike ?");
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
				+ "from opu_operatore o  "
				+ "join opu_stabilimento s on s.id_operatore = o.id "
				+ "join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id "
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

		listaStabilimenti.setIdOperatore(idOperatore);
		listaStabilimenti.buildStabilimento(db, idLineaProduttiva);

		rs.close();
		pst.close();
		buildRappresentante(db);
		rs.close();
		pst.close();
	}

	public void setRequestItems(ActionContext context) {
		phoneNumberList = new OrganizationPhoneNumberList(context);
		emailAddressList = new OrganizationEmailAddressList(context.getRequest());
		rappLegale = new SoggettoFisico(context.getRequest());
	}


   
    
	protected void buildRecord(ResultSet rs) throws SQLException {

		this.setIdOperatore(rs.getInt("idOperatore"));
		this.setRagioneSociale(rs.getString("ragione_sociale"));
		this.setCodFiscale(rs.getString("codice_fiscale_impresa"));
		this.setPartitaIva(rs.getString("partita_iva"));
		this.tipo_impresa=rs.getInt("tipo_impresa");
		this.tipo_societa= rs.getInt("tipo_societa");
		
		this.setEnteredBy(rs.getInt("enteredby"));
		this.setModifiedBy(rs.getInt("modifiedby"));
		this.setEntered(rs.getTimestamp("entered"));
		this.setModified(rs.getTimestamp("modified"));
		this.setDomicilioDigitale(rs.getString("domicilio_digitale"));
		
		

	}  



	public boolean aggiungiRelazioneSoggettoFisico(Connection db, int tipoLegame,ActionContext context)
	throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			// Controllare se c'e' gia' soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db, "opu_rel_operatore_soggetto_fisico_id_seq");

			sql.append("update opu_rel_operatore_soggetto_fisico set enabled = false where id_operatore =?; INSERT INTO opu_rel_operatore_soggetto_fisico (");

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
					
					sql.append("UPDATE opu_operatore SET id_indirizzo = ? WHERE id = ? ");
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
		Indirizzo sedeLegale = new Indirizzo();

		SedeList listaInd = this.getListaSediOperatore();
		Iterator<Indirizzo> it = listaInd.iterator();
		while (it.hasNext()) {

			Indirizzo temp = it.next();
			sedeLegale = temp;
			
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

//	public boolean aggiornaRelazioneSede(Connection db, Indirizzo oldIndirizzo,
//			Indirizzo newIndirizzo,ActionContext context) throws SQLException {
//
//		StringBuffer sql = new StringBuffer();
//		try {
//
//			Calendar calendar = Calendar.getInstance();
//			Timestamp currentTimestamp = new java.sql.Timestamp(calendar
//					.getTime().getTime());
//
//			// Disabilito vecchia relazione
//
//			PreparedStatement pst = db
//			.prepareStatement("Update opu_relazione_operatore_sede set stato_sede = 2, data_fine = ? where "
//					+ "stato_sede = 1 and "
//					+ "id_operatore = ? "
//					+ "and tipologia_sede = ? ");
//
//			int i = 0;
//			pst.setTimestamp(++i, currentTimestamp);
//			pst.setInt(++i, idOperatore);
//			pst.setInt(++i, oldIndirizzo.getTipologiaSede());
//			int resultCount = pst.executeUpdate();
//			pst.close();
//
//			this.aggiungiSede(db, newIndirizzo,context);
//
//		} catch (SQLException e) {
//
//			throw new SQLException(e.getMessage());
//		} finally {
//
//		}
//
//		return true;
//	}

	public boolean esegui_voltura(Connection db)
	{

		PreparedStatement pst = null ;
		int i = 0 ;
		try
		{
			pst = db.prepareStatement("update opu_operatore set ragione_sociale = ? , partita_iva = ? where id = ?;update opu_rel_operatore_soggetto_fisico set id_soggetto_fisico = ? where id = ?");
			pst.setString(++i, ragioneSociale);
			pst.setString(++i, partitaIva);
			pst.setInt(++i, this.getIdOperatore());
			pst.setInt(++i, this.getRappLegale().getIdSoggetto());
			pst.setInt(++i, this.getIdOperatore());
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false ;
		}


		return true ;
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
			.append("SELECT distinct max(storico.id) as id_soggetto_storico ,i.civico,comnasc.id as  id_comune_nascita," +
					"o.nome,o.cognome," +
					"o.codice_fiscale,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
					"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
					" o.id as id_soggetto ,i.toponimo," +
					"i.id,i.comune,i.comune_testo,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune," +
					"lp.description as descrizione_provincia ,  "  +
					"lt.description as descrizione_toponimo   " 
					+ "FROM opu_soggetto_fisico o "+
					"left join opu_soggetto_fisico_storico storico on o.id = storico.id_opu_soggetto_fisico " 
					+ "left join comuni1 comnasc on (comnasc.nome ilike o.comune_nascita) " 
					+ "left join opu_indirizzo i on o.indirizzo_id=i.id "
					+ "left join comuni1 on (comuni1.id = i.comune) "
					+ "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
					+ "left join lookup_province lp on lp.code = comuni1.cod_provincia::int "
					+ "join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) and os.enabled "
					+ "join lookup_toponimi lt on lt.code = i.toponimo and lt.enabled "
					+ "join opu_operatore op on (os.id_operatore = op.id)  where os.id_operatore = ? "+
					"group by o.nome,o.cognome," +
					"o.codice_fiscale,comnasc.id,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
					"o.email,i.civico,i.toponimo,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
					" o.id  ," +
					"i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome ," +
			"lp.description,i.comune_testo "
			+ ", lt.description ");

			PreparedStatement pst = db.prepareStatement(sqlSelect.toString());

			int i = 0;
			pst.setInt(++i, idOperatore);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRappresentanteLegale(rs);
			}
			else
			{
				rappLegale = new SoggettoFisico();
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

		}

	}

	public boolean checkEsistenzaOperatore(Connection db) {
		boolean exist = false;
		String query = "select *  from opu_operatore o " 
			+ "where o.partita_iva = ? ";

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setString(++i, this.partitaIva);

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
	
	
public List<Operatore> checkEsistenzaOperatoreSuap(Connection db,String operazione) throws IndirizzoNotFoundException {
		
		String query = "select distinct * FROM opu_get_lista_imprese(?,?,null,null) where 1=1 ";
		
		
		if (operazione!=null &&  (operazione.equals("new") ||  operazione.equals("cambioTitolarita")) )
			query +=" and flag_clean = true" ;
		
		
		Operatore operatore = null;
		List<Operatore> listaOp = new ArrayList<Operatore>();
		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setString(++i, this.partitaIva);
			pst.setString(++i, this.codFiscale);
			System.out.println("#######################OPERATORE ACTION SUAP ####################################  CHECK ESISTENZA OPERATORE SUAP QUERY "+pst.toString());
	 
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				operatore = new Operatore();
				operatore.queryRecordOperatore(db,rs.getInt("idoperatore"));
				operatore.setPresentataScia(rs.getBoolean("flag_clean"));
				listaOp.add(operatore);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaOp;
	}



public List<Operatore> checkEsistenzaOperatoreSuap(Connection db,String operazione,int idStabilimento) throws IndirizzoNotFoundException {
	
	String query = "select distinct * FROM opu_get_lista_imprese(?,?,null,null) where 1=1 ";
	
	
	if (operazione!=null &&  (operazione.equals("new") ||  operazione.equals("cambioTitolarita")) )
		query +=" and flag_clean = true" ;
	
	
	Operatore operatore = null;
	List<Operatore> listaOp = new ArrayList<Operatore>();
	PreparedStatement pst;
	try {
		int i = 0;
		pst = db.prepareStatement(query);
		pst.setString(++i, this.partitaIva);
		pst.setString(++i, this.codFiscale);
		System.out.println("#######################OPERATORE ACTION SUAP ####################################  CHECK ESISTENZA OPERATORE SUAP QUERY "+pst.toString());
 
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			operatore = new Operatore();
			operatore.queryRecordOperatore(db,rs.getInt("idoperatore"));
			operatore.setPresentataScia(rs.getBoolean("flag_clean"));
			listaOp.add(operatore);
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return listaOp;
}

public List<Operatore> checkOperatorePartitaIva(Connection db) throws IndirizzoNotFoundException {
	
	String query = "select * FROM opu_operatore where partita_iva ilike ?";
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
			operatore.queryRecordOperatore(db,rs.getInt("id"));
			listaOp.add(operatore);
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return listaOp;
}

	/*public List<Operatore> checkEsistenzaOperatoreSuap(Connection db) throws IndirizzoNotFoundException { // LA LOGICA DI ESISTENZA DELLA PARTITA IVA e' STATA TRASFERITA BELLA DBI
	 * 
		
		String query = "select distinct on (id) id,o.id as idOperatore   from opu_operatore o " 
			+ "where flag_clean = true and (1=1 ";
		
		
		boolean trovata_partita_iva=false;
		boolean solo_ragSociale=true;
		
		if(partitaIva!= null && ! "".equals(partitaIva)){
			query+=" and o.partita_iva  ilike ?  ";
			trovata_partita_iva=true;
			solo_ragSociale=false;
		}
			
		 if(codFiscale!= null && ! "".equals(codFiscale)){
			 solo_ragSociale=false;
			 if(trovata_partita_iva)
				 query+=" or o.codice_fiscale_impresa ilike ?  ";
			 else
				 query+=" and o.codice_fiscale_impresa ilike ?  ";
		 }
		
		 if (solo_ragSociale){
				if(ragioneSociale!= null && ! "".equals(ragioneSociale))
					query+=" and o.ragione_sociale  ilike ?  ";
		 }
		 
		 query+=") and trashed_date is null ";
		
		Operatore operatore = null;
		List<Operatore> listaOp = new ArrayList<Operatore>();
		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			
			if(partitaIva!= null && ! "".equals(partitaIva))
				pst.setString(++i, this.partitaIva);
				
			 if(codFiscale!= null && ! "".equals(codFiscale))
				 pst.setString(++i, this.codFiscale);
			 
			 if(solo_ragSociale){
				 if(ragioneSociale!= null && ! "".equals(ragioneSociale))
					 pst.setString(++i, this.ragioneSociale);	
			 }
			 
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				operatore = new Operatore();
				operatore.queryRecordOperatore(db,rs.getInt("idOperatore"));
				listaOp.add(operatore);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaOp;
	}*/


	public int getIdLineaProduttivaIfExists(Connection db,
			int idLineaTipologiaLineaProduttiva) {
		int idRelazioneStabLineaProd = -1;
		String query = "select rel.* from opu_operatore o left join opu_stabilimento s on (o.id = s.id_operatore) "
			+ "left join opu_relazione_stabilimento_linee_produttive rel on (s.id = rel.id_stabilimento) "
			+ "where o.partita_iva = ? and rel.id_linea_produttiva = ?";

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

	public int compareTo(Operatore otherOpertore) {
	       
			
			String denominazione = otherOpertore.getRagioneSociale();
			String pIva= otherOpertore.getPartitaIva();
			String codFiscale=otherOpertore.getCodFiscale();
			
			SoggettoFisico legaleRapp = otherOpertore.getRappLegale();
			Indirizzo indirizzo = otherOpertore.getSedeLegale();
			
			if (
					this.getRagioneSociale().equalsIgnoreCase(denominazione) &&
					this.getPartitaIva().equalsIgnoreCase(pIva ) &&
					this.getCodFiscale().equalsIgnoreCase(codFiscale) && 
					this.getRappLegale().compareTo(legaleRapp)==0 &&
						(	this.getSedeLegale()!=null && indirizzo!=null && this.getSedeLegale().compareTo(indirizzo)==0)
					)
				return 0;
			return 1 ;
			

	    }
	
	
	public int compareToDatiImpresa(Operatore otherOpertore) {
	       
		
		String denominazione = otherOpertore.getRagioneSociale();
		String pIva= otherOpertore.getPartitaIva();
		String codFiscale=otherOpertore.getCodFiscale();
		
		int tipoSoc = (this.getTipo_societa()>0) ? this.getTipo_societa() : -1 ;
		int tipoSocAltro = (otherOpertore.getTipo_societa()>0) ? otherOpertore.getTipo_societa() : -1 ;
		Indirizzo indirizzo = otherOpertore.getSedeLegale();
		
		if (
				this.getTipo_impresa()==otherOpertore.getTipo_impresa() && 
				tipoSoc == tipoSocAltro &&
				this.getRagioneSociale().equalsIgnoreCase(denominazione) &&
				this.getPartitaIva().equalsIgnoreCase(pIva ) &&
				this.getCodFiscale().equalsIgnoreCase(codFiscale) && 
				this.getSedeLegale().compareTo(indirizzo)==0
				)
			return 0;
		return 1 ;
		

    }

	
//	public HashMap<String, Object> getHashmapOperatore() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
//	{
//
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		Field[] campi = this.getClass().getDeclaredFields();
//		Method[] metodi = this.getClass().getMethods();
//		for (int i = 0 ; i <campi.length; i++)
//		{
//			String nomeCampo = campi[i].getName();
//
//			
//			
//			
//			
//			
//			
//			if ( ! nomeCampo.equalsIgnoreCase("domicilioDigitale") && ! nomeCampo.equalsIgnoreCase("rappLegale") && ! nomeCampo.equalsIgnoreCase("storicoSoggettoFisico") && ! nomeCampo.equalsIgnoreCase("listaSediOperatore") 
//					&& ! nomeCampo.equalsIgnoreCase("listaStabilimenti")  && ! nomeCampo.equalsIgnoreCase("phoneNumberList") 
//					&& ! nomeCampo.equalsIgnoreCase("emailAddressList"))
//			{
//				for (int j=0; j<metodi.length; j++ )
//				{
//
//					if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
//					{
//						
//						map.put(nomeCampo,new String (""+metodi[j].invoke(this)));
//						
//						
//
//					}
//				}
//
//			}
//
//		}
//		
//		if (rappLegale!=null)
//		{
//			
//			JSONObject rapp = new JSONObject(rappLegale.getHashmapSoggettoFisico());
//			map.put("rapplegale", rapp);
//		}
//			
//		if(getSedeLegale()!=null)
//		{
//			
//			JSONObject sedeleg = new JSONObject(getSedeLegale().getHashmapIndirizzo());
//				map.put("sedelegale",sedeleg );
//		
//
//
//		}
//
//
//
//		return map ;
//
//	}


	public int verificaEsistenzaErrataCorrigeImpresa(Connection db,int idOperatorePrecedente) throws SQLException
	{
		
		String sql = "select id from opu_operatore where id_operatore_precedente = ? ";
		
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idOperatorePrecedente);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
			return rs.getInt(1) ;
		
		return -1 ;
	}
	
//	public Operatore applicaErrataCorrige(Connection db,ActionContext context) throws SQLException
//	{
//		int idOperatore = this.getIdOperatore();
//		int idOperatoreEC = verificaEsistenzaErrataCorrigeImpresa(db,idOperatore);
//		if(idOperatoreEC<0)
//		{
//			this.setIdOperatorePrecedente(idOperatore);
//			this.setIdOperatore(-1);
//			this.insert(db, context);
//		}
//		else
//		{
//			this.setIdOperatore(idOperatoreEC);
//		}
//		
//		return this ;
//	}
	
	public Operatore applicaErrataCorrige(Connection db,ActionContext context) throws SQLException, IllegalAccessException, InstantiationException, IndirizzoNotFoundException
	{
		int idSoggettoFisicoPrimaDellaModifica = this.getIdOperatore();
		boolean impresaEsistente = false;
		
		OperatoreList listaOperatori =new OperatoreList();
		
		if(this.getPartitaIva()!=null && !this.getPartitaIva().equals(""))
			listaOperatori.setPartitaIva(this.getPartitaIva());
		else if (this.getCodFiscale()!=null && !this.getCodFiscale().equals(""))
			listaOperatori.setCodiceFiscale(this.getCodFiscale());
		if(this.getDomicilioDigitale()!=null && !this.getDomicilioDigitale().equals(""))
			listaOperatori.setDomicilioDigitale(this.getDomicilioDigitale());
		else if (this.getIdOperatore()>0)
			listaOperatori.setIdOperatore(this.getIdOperatore());
		
		listaOperatori.buildListErratacorrige(db);
		
		for(int i = 0 ; i < listaOperatori.size();i++)
		{
			Operatore thisOperatore = (Operatore) listaOperatori.get(i);
			
			if(this.compareToDatiImpresa(thisOperatore)==0 &&
				this.getRappLegale().compareToDatiAnagrafici(thisOperatore.getRappLegale())==0 &&
				this.getRappLegale().compareToDatiResidenza(thisOperatore.getRappLegale())==0 )
			{
				impresaEsistente=true ;
				this.setIdOperatore(thisOperatore.getIdOperatore());
			}
		}
			
			
		
		
		if(impresaEsistente==false)
		{
			/*duplico il soggetto con i dati modificati */
			this.setIdOperatorePrecedente(this.getIdOperatore());
			this.setIdOperatore(-1);
			this.insert(db, context);
		}
		
		
		
		
		
		return this;
	}
	


}
