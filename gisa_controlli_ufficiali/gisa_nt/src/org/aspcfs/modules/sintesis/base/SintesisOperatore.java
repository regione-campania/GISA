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

import com.darkhorseventures.framework.beans.GenericBean;

public class SintesisOperatore extends GenericBean {

	
	private int idOperatore = -1;
	private String codiceFiscaleImpresa = null;
	private String partitaIva = null;
	private String ragioneSociale = null;
	private int enteredby = -1;
	private int modifiedby = -1;
	private Timestamp entered = null;
	private Timestamp modified = null;
	private Timestamp trashedDate = null;
	private String domicilioDigitale = null;
	private int tipoImpresa = -1;
	private int tipoSocieta = -1;
	private int idIndirizzo = -1;
	
	private int idRappLegale = -1;
	
	 private Timestamp dataUltimoAggiornamentoSintesis = null;


	private SintesisSoggettoFisico rappLegale = null;
	private SintesisIndirizzo sedeLegale = null;

	
	public SintesisOperatore(Connection db, int id) throws SQLException {
		String sqlSelect = "select * from sintesis_operatore where id = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setInt(1, id);
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next()){
			buildRecord(rsSelect);
			setSoggettoFisico(db);
			setIndirizzo(db);
		}
	}
	
	public void queryRecordOperatore (Connection db, int id) throws SQLException {
		String sqlSelect = "select * from sintesis_operatore where id = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setInt(1, id);
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next()){
			buildRecord(rsSelect);
			setSoggettoFisico(db);
			setIndirizzo(db);
		}
	}
	
	private void setIndirizzo(Connection db) throws SQLException {
		
		SintesisIndirizzo ind = new SintesisIndirizzo(db, idIndirizzo);
		this.setSedeLegale(ind);
	}


	private void setSoggettoFisico(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select id_soggetto_fisico from sintesis_rel_operatore_soggetto_fisico where id_operatore = ? and data_fine is null and enabled is not false");
		pst.setInt(1, idOperatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			SintesisSoggettoFisico soggetto = new SintesisSoggettoFisico(db, rs.getInt("id_soggetto_fisico"));
			this.setRappLegale(soggetto);
		}
		else{
			SintesisSoggettoFisico soggetto = new SintesisSoggettoFisico();
			this.setRappLegale(soggetto);
		}
		
	}
	public SintesisOperatore() {
		// TODO Auto-generated constructor stub
	}
	private void buildRecord(ResultSet rs) throws SQLException {
		idOperatore = rs.getInt("id");
		codiceFiscaleImpresa = rs.getString("codice_fiscale_impresa");
		  partitaIva =  rs.getString("partita_iva");
		  ragioneSociale =  rs.getString("ragione_sociale");
		  enteredby =  rs.getInt("enteredby");
		  modifiedby =  rs.getInt("modifiedby");
		  entered =  rs.getTimestamp("entered");
		  modified =  rs.getTimestamp("modified");
		  trashedDate =  rs.getTimestamp("trashed_date");
		  domicilioDigitale =  rs.getString("domicilio_digitale");
		  tipoImpresa =  rs.getInt("tipo_impresa");
		  tipoSocieta =  rs.getInt("tipo_societa");
		  idIndirizzo =  rs.getInt("id_indirizzo");
		  setDataUltimoAggiornamentoSintesis(rs.getTimestamp("data_ultimo_aggiornamento_sintesis"));
		
	}
	public int getIdOperatore() {
		return idOperatore;
	}
	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}
	public String getCodiceFiscaleImpresa() {
		return codiceFiscaleImpresa;
	}
	public void setCodiceFiscaleImpresa(String codiceFiscaleImpresa) {
		this.codiceFiscaleImpresa = codiceFiscaleImpresa;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
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
	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}
	public void setDomicilioDigitale(String domicilioDigitale) {
		this.domicilioDigitale = domicilioDigitale;
	}
	public int getTipoImpresa() {
		return tipoImpresa;
	}
	public void setTipoImpresa(int tipoImpresa) {
		this.tipoImpresa = tipoImpresa;
	}
	public void setTipoImpresa(String tipoImpresa) {
		try {this.tipoImpresa = Integer.parseInt(tipoImpresa);} catch (Exception e){};
	}
	public int getTipoSocieta() {
		return tipoSocieta;
	}
	public void setTipoSocieta(int tipoSocieta) {
		this.tipoSocieta = tipoSocieta;
	}
	public void setTipoSocieta(String tipoSocieta) {
		try {this.tipoSocieta = Integer.parseInt(tipoSocieta);} catch (Exception e){};
	}
	public int getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	
	public void insertOperatore(Connection db) throws SQLException{
		int idInsertOperatore = -1;
		
		String sqlSelect = "select id from sintesis_operatore where ragione_sociale ilike ? and partita_iva ilike ? and codice_fiscale_impresa ilike ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setString(1, ragioneSociale);
		pstSelect.setString(2, partitaIva);
		pstSelect.setString(3, codiceFiscaleImpresa);

		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next())
			idInsertOperatore = rsSelect.getInt("id");
		
		if (idInsertOperatore == -1){
			String sqlInsert = "insert into sintesis_operatore (ragione_sociale, partita_iva, codice_fiscale_impresa, id_indirizzo, tipo_impresa, tipo_societa, domicilio_digitale, enteredby, entered, data_ultimo_aggiornamento_sintesis)  values (?, ?, ?,?, ?, ?, ?, ?, now(), ?) returning id as id_inserito";
			PreparedStatement pstInsert = db.prepareStatement(sqlInsert);
			int i = 0;
			pstInsert.setString(++i, ragioneSociale);
			pstInsert.setString(++i, partitaIva);
			pstInsert.setString(++i, codiceFiscaleImpresa);
			pstInsert.setInt(++i,  idIndirizzo);
			pstInsert.setInt(++i,  tipoImpresa);
			pstInsert.setInt(++i,  tipoSocieta);
			pstInsert.setString(++i, domicilioDigitale);
			pstInsert.setInt(++i,  enteredby);
			pstInsert.setTimestamp(++i,  dataUltimoAggiornamentoSintesis);

			ResultSet rsInsert = pstInsert.executeQuery();
			if (rsInsert.next())
				idInsertOperatore = rsInsert.getInt("id_inserito");
		}
			idOperatore = idInsertOperatore;
			
//			String sql = "update sintesis_rel_operatore_soggetto_fisico set data_fine = now() where id_operatore = ? and data_fine is null";
//			PreparedStatement pst = db.prepareStatement(sql);
//			pst.setInt(1,  idOperatore);
//			pst.executeUpdate();
//			
			
			String sqlSelectRelSd = "select id from sintesis_rel_operatore_soggetto_fisico where id_operatore = ?";
			PreparedStatement pst = db.prepareStatement(sqlSelectRelSd);
			pst.setInt(1,  idOperatore);
			ResultSet rs = pst.executeQuery();
			
			if (!rs.next()){
				
				int idRappresentante = -1; 
				if (idRappLegale<=0){
					SintesisIndirizzo sfInd = new SintesisIndirizzo();
					sfInd.insertIndirizzo(db); 
				
					SintesisSoggettoFisico sf = new SintesisSoggettoFisico();
					sf.setIdIndirizzo(sfInd.getIdIndirizzo());
					sf.insertSoggetto(db);
					idRappresentante = sf.getId();
				}
				else
					idRappresentante = idRappLegale;
				
				String sql = "insert into sintesis_rel_operatore_soggetto_fisico(id_operatore, id_soggetto_fisico, data_inizio, tipo_soggetto_fisico) values (?, ?, now(), 1)";
				pst = db.prepareStatement(sql);
				pst.setInt(1,  idOperatore);
				pst.setInt(2,  idRappresentante);
				pst.executeUpdate();
			}
			
			
			
	}
	public boolean isDiverso(Connection db, SintesisOperatore operatore) {
	boolean idDiverso = false;
		
		
		if (!this.getPartitaIva().equalsIgnoreCase(operatore.getPartitaIva())){
			idDiverso = true;
		}
		
		if (!this.getCodiceFiscaleImpresa().equalsIgnoreCase(operatore.getCodiceFiscaleImpresa())){
			idDiverso = true;
		}
		
		if (!this.getRagioneSociale().equalsIgnoreCase(operatore.getRagioneSociale())){
			idDiverso = true;
		}
		
		return idDiverso;
	}
	
	public boolean isDiversoCompleta(Connection db, SintesisOperatore operatore) {
		boolean idDiverso = false;
			
			
			if (!this.getDomicilioDigitale().equalsIgnoreCase(operatore.getDomicilioDigitale())){
				idDiverso = true;
			}
			
			if (this.getTipoImpresa()!=operatore.getTipoImpresa()){
				idDiverso = true;
			}
			if (this.getTipoSocieta()!=operatore.getTipoSocieta()){
				idDiverso = true;
			}
			if (this.getIdIndirizzo()!=operatore.getIdIndirizzo()){
				idDiverso = true;
			}
			
			return idDiverso;
		}
	
	public void aggiornaOperatoreCompleta(Connection db) throws SQLException {
		
	PreparedStatement pst = db.prepareStatement("update sintesis_operatore set tipo_impresa = ?, tipo_societa = ?, domicilio_digitale = ?, id_indirizzo = ?, modifiedby = ?, modified = now() where id = ?");
	int i = 0;
	pst.setInt(++i, tipoImpresa);
	pst.setInt(++i, tipoSocieta);
	pst.setString(++i, domicilioDigitale);
	pst.setInt(++i, idIndirizzo);
	pst.setInt(++i, modifiedby);
	pst.setInt(++i, idOperatore);
	pst.executeUpdate();
	}


	public void buildDaCampi(Connection db) throws SQLException {
		String sqlSelect = "select * from sintesis_operatore where ragione_sociale ilike ? and partita_iva ilike ? and codice_fiscale_impresa ilike ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setString(1, ragioneSociale);
		pstSelect.setString(2, partitaIva);
		pstSelect.setString(3, codiceFiscaleImpresa);

		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next()){
			buildRecord(rsSelect);
			setSoggettoFisico(db);
			setIndirizzo(db);
		}
		
	}


	public SintesisIndirizzo getSedeLegale() {
		return sedeLegale;
	}


	public void setSedeLegale(SintesisIndirizzo sedeLegale) {
		this.sedeLegale = sedeLegale;
	}


	public SintesisSoggettoFisico getRappLegale() {
		return rappLegale;
	}


	public void setRappLegale(SintesisSoggettoFisico rappLegale) {
		this.rappLegale = rappLegale;
	}


	public int getIdRappLegale() {
		return idRappLegale;
	}


	public void setIdRappLegale(int idRappLegale) {
		this.idRappLegale = idRappLegale;
	}

	public Timestamp getDataUltimoAggiornamentoSintesis() {
		return dataUltimoAggiornamentoSintesis;
	}

	public void setDataUltimoAggiornamentoSintesis(Timestamp dataUltimoAggiornamentoSintesis) {
		this.dataUltimoAggiornamentoSintesis = dataUltimoAggiornamentoSintesis;
	}

}
