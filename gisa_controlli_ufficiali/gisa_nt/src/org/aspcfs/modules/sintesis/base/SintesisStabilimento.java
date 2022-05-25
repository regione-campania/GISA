/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.modules.troubletickets.base.Ticket;

import com.darkhorseventures.framework.beans.GenericBean;

public class SintesisStabilimento extends GenericBean {

	 private int idStabilimento = -1;
	 private int enteredby = -1;
	 private int modifiedby = -1;
	 private Timestamp entered = null;
	 private Timestamp modified = null;
	 private Timestamp trashedDate = null;
	 private int idOperatore = -1;
	 private int idAsl = -1;
	 private int idSoggettoFisico = -1;
	 private int idIndirizzo = -1; 
	 private int stato = -1;
	 private String sintesisDescrizioneStatoSedeOperativa = null;
	 private String denominazione = null;
	 private String approvalNumber = null;
	 private int altId = -1;
	 private Timestamp dataUltimoAggiornamentoSintesis = null;
	 private int riferimentoOrgId = -1;

	 
	 
	private SintesisOperatore operatore = null;
	private SintesisIndirizzo indirizzo = null;
	private ArrayList<SintesisRelazioneLineaProduttiva> linee = new ArrayList<SintesisRelazioneLineaProduttiva>();
	
	
	//CU
	private int tipologia ;
	private String action = "" ;
	private String name = "";
	private int siteId ;
	private int categoriaRischio ;





	public SintesisStabilimento(Connection db, int id) throws SQLException {
		String sqlSelect = "select * from sintesis_stabilimento where id = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setInt(1, id);
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next()){
			buildRecord(rsSelect);
			setIndirizzo(db, idIndirizzo);
			setOperatore (db, idOperatore);
			setLinee(db);
		}
	}
	
	public SintesisStabilimento(Connection db, int altId, boolean alt) throws SQLException {
		String sqlSelect = "select * from sintesis_stabilimento where alt_id = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setInt(1, altId);
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next()){
			buildRecord(rsSelect);
			setIndirizzo(db, idIndirizzo);
			setOperatore (db, idOperatore);
			setLinee(db);
		}
	}
	
	public void queryRecord(Connection db, int id, int idLinea) throws SQLException {
		String sqlSelect = "select * from sintesis_stabilimento where id = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setInt(1, id);
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next()){
			buildRecord(rsSelect);
			setIndirizzo(db, idIndirizzo);
			setOperatore (db, idOperatore);
			setLinea(db, idLinea);
		}
	}
	
	private void setLinee(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from sintesis_relazione_stabilimento_linee_produttive where trashed_date is null and id_stabilimento = ?");
		pst.setInt(1, idStabilimento);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			SintesisRelazioneLineaProduttiva linea = new SintesisRelazioneLineaProduttiva();
			linea.buildRecord(rs);
			linea.setAutomezzi(db);
			linea.setPathCompleto(db); 
			linea.setCodiceUnivoco(db);
			linee.add(linea);
		}
		
	}
	
	private void setLinea(Connection db, int idLinea) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from sintesis_relazione_stabilimento_linee_produttive where trashed_date is null and id_stabilimento = ? and id_linea_produttiva = ?");
		pst.setInt(1, idStabilimento);
		pst.setInt(2, idLinea);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			SintesisRelazioneLineaProduttiva linea = new SintesisRelazioneLineaProduttiva();
			linea.buildRecord(rs);
			linea.setPathCompleto(db);
			linee.add(linea);
		}
		
	}

	private void buildRecord(ResultSet rs) throws SQLException {
		 idStabilimento = rs.getInt("id");
		 enteredby = rs.getInt("entered_by");
		 modifiedby = rs.getInt("modified_by");
		 entered = rs.getTimestamp("entered");
		 modified  = rs.getTimestamp("modified");
		trashedDate  = rs.getTimestamp("trashed_date");
		idOperatore = rs.getInt("id_operatore");
		 idAsl = rs.getInt("id_asl");
		 idSoggettoFisico = rs.getInt("id_soggetto_fisico");
		 idIndirizzo = rs.getInt("id_indirizzo"); 
		 stato = rs.getInt("stato");
		denominazione = rs.getString("denominazione");
		 approvalNumber = rs.getString("approval_number");
		 altId = rs.getInt("alt_id");
		 setTipologia(Ticket.TIPO_SINTESIS);
		 dataUltimoAggiornamentoSintesis = rs.getTimestamp("data_ultimo_aggiornamento_sintesis");
		 riferimentoOrgId = rs.getInt("riferimento_org_id");
		 categoriaRischio = rs.getInt("categoria_rischio");

		
	}

	private void setOperatore(Connection db, int idOperatore) throws SQLException {
		SintesisOperatore op = new SintesisOperatore(db, idOperatore);
		this.operatore = op;
		this.setName(operatore.getRagioneSociale());
	}

	private void setIndirizzo(Connection db, int idIndirizzo) throws SQLException {
		SintesisIndirizzo ind = new SintesisIndirizzo(db, idIndirizzo);
		this.indirizzo = ind;
	}

	public SintesisStabilimento(Connection db, String approvalNumber) throws SQLException {
		String sqlSelect = "select * from sintesis_stabilimento where approval_number ilike ? and trashed_date is null";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setString(1, approvalNumber);
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next()){
			buildRecord(rsSelect);
			setIndirizzo(db, idIndirizzo);
			setOperatore (db, idOperatore);
		}
	}
	
	public SintesisStabilimento() {
		// TODO Auto-generated constructor stub
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}
	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}
	public int getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}
	public int getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
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
	public Timestamp getTrashedDate() {
		return trashedDate;
	}
	public void setTrashedDate(Timestamp trashedDate) {
		this.trashedDate = trashedDate;
	}
	public int getIdOperatore() {
		return idOperatore;
	}
	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public int getIdSoggettoFisico() {
		return idSoggettoFisico;
	}
	public void setIdSoggettoFisico(int idSoggettoFisico) {
		this.idSoggettoFisico = idSoggettoFisico;
	}
	public int getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public String getSintesisDescrizioneStatoSedeOperativa() {
		return sintesisDescrizioneStatoSedeOperativa;
	}
	public void setSintesisDescrizioneStatoSedeOperativa(String sintesisDescrizioneStatoSedeOperativa) {
		this.sintesisDescrizioneStatoSedeOperativa = sintesisDescrizioneStatoSedeOperativa;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getApprovalNumber() {
		return approvalNumber;
	}
	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	 
	public SintesisOperatore getOperatore() {
		return operatore;
	}
	public void setOperatore(SintesisOperatore operatore) {
		this.operatore = operatore;
	}
	public SintesisIndirizzo getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(SintesisIndirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}


	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public String getAction()
	{
		return action ;
	}
	
	public  String getPrefissoAction(String actionName)
	{
	action = "StabilimentoSintesisAction";
	return action ;
	}
	
	public String getContainer()
	{
		String container = "sintesis" ;
		
		return container ;
	} 
	
	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}
	
	public int getOrgId() {
		return altId;
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

	
public int getAltId() {
		return altId;
	}

	public void setAltId(int altId) {
		this.altId = altId;
	}

public Timestamp getDataUltimoAggiornamentoSintesis() {
		return dataUltimoAggiornamentoSintesis;
	}

	public void setDataUltimoAggiornamentoSintesis(Timestamp dataUltimoAggiornamentoSintesis) {
		this.dataUltimoAggiornamentoSintesis = dataUltimoAggiornamentoSintesis;
	}

public int getRiferimentoOrgId() {
		return riferimentoOrgId;
	}

	public void setRiferimentoOrgId(int riferimentoOrgId) {
		this.riferimentoOrgId = riferimentoOrgId;
	}

public void codificaCampi(Connection db) throws SQLException{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		//convertire descrizione comune in id
		
		pst = db.prepareStatement("select * from lookup_stato_stabilimento_sintesis where description ilike trim(?)");
		pst.setString(1, sintesisDescrizioneStatoSedeOperativa);
		rs = pst.executeQuery();
		while (rs.next()){
			this.stato = rs.getInt("code");
		}
	}


	public void insertStabilimento(Connection db) throws SQLException{
		int idInsertStabilimento = -1;
		
		String sqlSelect = "select id from sintesis_stabilimento where approval_number ilike ? and id_indirizzo = ? and id_operatore = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setString(1, approvalNumber);
		pstSelect.setInt(2, idIndirizzo);
		pstSelect.setInt(3, idOperatore);

		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next())
			idInsertStabilimento = rsSelect.getInt("id");
		
		if (idInsertStabilimento == -1){
			String sqlInsert = "insert into sintesis_stabilimento (approval_number, id_indirizzo, id_asl,  id_operatore, denominazione, stato, riferimento_org_id,  entered_by, entered, data_ultimo_aggiornamento_sintesis)  values (?, ?, ?, ?, ?, ?, ?, ?,  now(), ?) returning id as id_inserito";
			PreparedStatement pstInsert = db.prepareStatement(sqlInsert);
			int i = 0;
			pstInsert.setString(++i, approvalNumber);
			pstInsert.setInt(++i, idIndirizzo);
			pstInsert.setInt(++i, idAsl);
			pstInsert.setInt(++i, idOperatore);
			pstInsert.setString(++i, denominazione);
			pstInsert.setInt(++i, stato);
			pstInsert.setInt(++i, riferimentoOrgId);
			pstInsert.setInt(++i, enteredby);
			pstInsert.setTimestamp(++i, dataUltimoAggiornamentoSintesis);

	
			ResultSet rsInsert = pstInsert.executeQuery();
			if (rsInsert.next()){
				idInsertStabilimento = rsInsert.getInt("id_inserito");
//				if (riferimentoOrgId>0)
//					updateOldOrganization(db, idInsertStabilimento);
			}
		}
			idStabilimento = idInsertStabilimento;
	}
	

//	private void updateOldOrganization(Connection db, int idInsertStabilimento) throws SQLException {
//
//		String msg = "organization cancellata e importata nel modulo SINTESIS sintesis_stabilimento.id="+idInsertStabilimento+" da utente="+enteredby+".";
//		String sql = "update organization set trashed_date = now(), modified=now(), note_hd = concat_ws(';', note_hd, ?)   where org_id = ? and trashed_date is null";
//		PreparedStatement pst = db.prepareStatement(sql);
//		pst.setString(1, msg);
//		pst.setInt(2, riferimentoOrgId);
//		pst.executeUpdate();
//		
//		sql = "select * from org_insert_into_ricerche_anagrafiche_old_materializzata(?)";
//		pst = db.prepareStatement(sql);
//		pst.setString(1, msg);
//		pst.setInt(2, riferimentoOrgId);
//		ResultSet rs = pst.executeQuery();
//	
//	}

	public boolean isDiverso(Connection db, SintesisStabilimento stabilimento) {

		boolean idDiverso = false;
		
		if (this.getStato()!= stabilimento.getStato()){
			idDiverso = true;
		}
		
		if (this.getIdOperatore()!= stabilimento.getIdOperatore()){
			idDiverso = true;
		}
		
		if (this.getIdIndirizzo()!= stabilimento.getIdIndirizzo()){
			idDiverso = true;
		}
		
		if (!this.getDenominazione().equalsIgnoreCase(stabilimento.getDenominazione())){
			idDiverso = true;
		}
		
		return idDiverso;
		
	}

	public void aggiornaStabilimento(Connection db) throws SQLException {
		String sql = "update sintesis_stabilimento set denominazione = ?, id_indirizzo = ?, id_operatore = ?, modified_by = ?, modified = now(), data_ultimo_aggiornamento_sintesis = ? ";
		if (stato>-1)
			sql+=", stato = ? ";
		sql+=" where id = ?";
		
		PreparedStatement pst = db.prepareStatement(sql);
		int i = 0;
		pst.setString(++i, denominazione);
		pst.setInt(++i, idIndirizzo);
		pst.setInt(++i, idOperatore);
		pst.setInt(++i, modifiedby);
		pst.setTimestamp(++i, dataUltimoAggiornamentoSintesis);
		
		if (stato>-1)
			pst.setInt(++i, stato);

		pst.setInt(++i, idStabilimento);
		pst.executeUpdate();	
		
	}

	public ArrayList<SintesisRelazioneLineaProduttiva> getLinee() {
		return linee;
	}

	public void setLinee(ArrayList<SintesisRelazioneLineaProduttiva> linee) {
		this.linee = linee;
	}

	public void queryRecordByApprovalPivaLinea(Connection db, String approval, String piva, int idLinea) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select s.id, rel.id_linea_produttiva from sintesis_stabilimento s inner join sintesis_operatore o on o.id = s.id_operatore inner join sintesis_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id where s.approval_number ilike ? and o.partita_iva ilike ? and rel.id_linea_produttiva = ? and s.trashed_date is null and o.trashed_date is null and rel.trashed_date is null");
		pst.setString(1, approval);
		pst.setString(2, piva);
		pst.setInt(3, idLinea);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			this.queryRecord(db, rs.getInt("id"), rs.getInt("id_linea_produttiva"));
		}
	}
	
	


	
		
}
