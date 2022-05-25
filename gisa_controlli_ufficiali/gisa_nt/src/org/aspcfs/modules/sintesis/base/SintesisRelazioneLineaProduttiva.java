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

import org.aspcfs.modules.gestioneml.actions.GestioneMasterList;
import org.aspcfs.modules.opu.util.StabilimentoImportUtil;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;


public class SintesisRelazioneLineaProduttiva extends GenericBean {
	
	private int idRelazione = -1;
	private int idLineaMasterList = -1;
	private int  idStabilimento = -1;
	private int stato = -1;
	private Timestamp dataInizio = null;
	private Timestamp  dataFine = null;
	private int enteredby = -1;
	private int modifiedby = -1;
	private Timestamp entered = null;
	private Timestamp modified = null;
	private Timestamp trashedDate = null;

	private String tipoAutorizzazione = null;
	private String imballaggio = null;
	private String paesiAbilitatiExport = null;
	private String remark = null;
	private String species = null;
	private String sintesisAttivita = null;
	private String sintesisDescrizioneSezione = null;
	private String sintesisDescrizioneStato = null;
	private String pathCompletoLineaProduttivaOld = null;
	private SintesisStabilimento stabilimento = null;
	private ArrayList<SintesisAutomezzo> automezzi = new ArrayList<SintesisAutomezzo>();

	private String pathCompleto = null;
	private String codiceUnivoco = null;

	 private Timestamp dataUltimoAggiornamentoSintesis = null;


	public SintesisRelazioneLineaProduttiva(Connection db, int id) throws SQLException {
	
			String sqlSelect = "select * from sintesis_relazione_stabilimento_linee_produttive where id = ?";
			PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
			pstSelect.setInt(1, id);
			ResultSet rsSelect = pstSelect.executeQuery();
			if (rsSelect.next()){
				buildRecord(rsSelect);
				setStabilimento(db, idStabilimento);
				setAutomezzi(db);
			}
		
	}
	public void buildRecord(ResultSet rs) throws SQLException {
		 idRelazione = rs.getInt("id");
		 idLineaMasterList = rs.getInt("id_linea_produttiva");
		idStabilimento = rs.getInt("id_stabilimento");
		stato = rs.getInt("stato");
		 dataInizio = rs.getTimestamp("data_inizio");
		dataFine = rs.getTimestamp("data_fine");
		  enteredby = rs.getInt("enteredby");
		  modifiedby = rs.getInt("modifiedby");
		  entered = rs.getTimestamp("entered");
		  modified = rs.getTimestamp("modified");
		  trashedDate = rs.getTimestamp("trashed_date");

		  tipoAutorizzazione = rs.getString("tipo_autorizzazione");
		  imballaggio = rs.getString("imballaggio");
		  paesiAbilitatiExport = rs.getString("paesi_abilitati_export");
		  remark = rs.getString("remark");
		  species = rs.getString("species");
		  sintesisAttivita = rs.getString("sintesis_attivita");
		  sintesisDescrizioneSezione = rs.getString("sintesis_descrizione_sezione");
		  
		  dataUltimoAggiornamentoSintesis = rs.getTimestamp("data_ultimo_aggiornamento_sintesis");
		  setPathCompletoLineaProduttivaOld(rs.getString("path_completo_linea_produttiva_old"));

		
	}
	private void setStabilimento(Connection db, int idStabilimento) throws SQLException {
		SintesisStabilimento stab = new SintesisStabilimento (db, idStabilimento);
		this.stabilimento = stab;
	}
	
	public SintesisRelazioneLineaProduttiva() {
		// TODO Auto-generated constructor stub
	}
	public SintesisRelazioneLineaProduttiva(Connection db, String approvalNumber, int idLineaMasterList) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select rel.* from sintesis_stabilimento s inner join sintesis_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id where s.approval_number = ? and rel.id_linea_produttiva in (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice = (select codice from ml8_linee_attivita_nuove_materializzata where id_nuova_linea_attivita =?)) and s.trashed_date is null and rel.trashed_date is null");
		pst.setString(1, approvalNumber);
		pst.setInt(2, idLineaMasterList);
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()){
			buildRecord(rs);
			setStabilimento(db, idStabilimento);
		}
	}
	public int getIdRelazione() {
		return idRelazione;
	}
	public void setIdRelazione(int idRelazione) {
		this.idRelazione = idRelazione;
	}
	public int getIdLineaMasterList() {
		return idLineaMasterList;
	}
	public void setIdLineaMasterList(int idLineaMasterList) {
		this.idLineaMasterList = idLineaMasterList;
	}
	public int getIdStabilimento() {
		return idStabilimento;
	}
	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public Timestamp getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = DatabaseUtils.parseDateToTimestamp(dataInizio);
	}
	public Timestamp getDataFine() {
		return dataFine;
	}
	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = DatabaseUtils.parseDateToTimestamp(dataFine);
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
	public String getTipoAutorizzazione() {
		return tipoAutorizzazione;
	}
	public void setTipoAutorizzazione(String tipoAutorizzazione) {
		this.tipoAutorizzazione = tipoAutorizzazione;
	}
	public String getImballaggio() {
		return imballaggio;
	}
	public void setImballaggio(String imballaggio) {
		this.imballaggio = imballaggio;
	}
	public String getPaesiAbilitatiExport() {
		return paesiAbilitatiExport;
	}
	public void setPaesiAbilitatiExport(String paesiAbilitatiExport) {
		this.paesiAbilitatiExport = paesiAbilitatiExport;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getSintesisAttivita() {
		return sintesisAttivita;
	}
	public void setSintesisAttivita(String sintesisAttivita) {
		this.sintesisAttivita = sintesisAttivita;
	}
	public String getSintesisDescrizioneSezione() {
		return sintesisDescrizioneSezione;
	}
	public void setSintesisDescrizioneSezione(String sintesisDescrizioneSezione) {
		this.sintesisDescrizioneSezione = sintesisDescrizioneSezione;
	}
	
public String getSintesisDescrizioneStato() {
		return sintesisDescrizioneStato;
	}
	public void setSintesisDescrizioneStato(String sintesisDescrizioneStato) {
		this.sintesisDescrizioneStato = sintesisDescrizioneStato;
	}
public SintesisStabilimento getStabilimento() {
		return stabilimento;
	}
	public void setStabilimento(SintesisStabilimento stabilimento) {
		this.stabilimento = stabilimento;
	}
public void codificaCampi(Connection db) throws SQLException{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		//convertire descrizione comune in id
		
		pst = db.prepareStatement("select * from lookup_stato_attivita_sintesis where description ilike trim(?)");
		pst.setString(1, sintesisDescrizioneStato);
		rs = pst.executeQuery();
		while (rs.next()){
			this.stato = rs.getInt("code");
		}
		
		if (this.idLineaMasterList<=0){
		pst = db.prepareStatement("select * from mapping_linea_attivita(?, ?, ?)");
		pst.setInt(1, 1);
		pst.setString(2, sintesisAttivita);
		pst.setString(3, sintesisDescrizioneSezione);
		rs = pst.executeQuery();
		while (rs.next()){
			this.idLineaMasterList = rs.getInt(1);
		}
		}
	}


public void insertRelazione(Connection db) throws SQLException{
	int idInsertRelazione = -1;
//	
//	String sqlSelect = "select id from sintesis_relazione_stabilimento_linee_produttive where approval_number ilike ? and id_indirizzo = ? and id_operatore = ?";
//	PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
//	pstSelect.setString(1, approvalNumber);
//	pstSelect.setInt(2, idIndirizzo);
//	pstSelect.setInt(3, idOperatore);
//
//	ResultSet rsSelect = pstSelect.executeQuery();
//	if (rsSelect.next())
//		idInsertStabilimento = rsSelect.getInt("id");
	
	if (idInsertRelazione == -1){
		String sqlInsert = "insert into sintesis_relazione_stabilimento_linee_produttive ("
				+ "id_stabilimento, stato, id_linea_produttiva, data_inizio, data_fine,   tipo_autorizzazione, imballaggio,  paesi_abilitati_export,  remark,  species ,  sintesis_attivita ,  sintesis_descrizione_sezione, enteredby, entered, data_ultimo_aggiornamento_sintesis, enabled )  values ("
				+ "?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?) returning id as id_inserito";
		PreparedStatement pstInsert = db.prepareStatement(sqlInsert);
		int i = 0;
		pstInsert.setInt(++i, idStabilimento);
		pstInsert.setInt(++i, stato);
		pstInsert.setInt(++i, idLineaMasterList);
		pstInsert.setTimestamp(++i, dataInizio);
		pstInsert.setTimestamp(++i, dataFine);
		pstInsert.setString(++i, tipoAutorizzazione);
		pstInsert.setString(++i, imballaggio);
		pstInsert.setString(++i, paesiAbilitatiExport);
		pstInsert.setString(++i, remark);
		pstInsert.setString(++i, species);
		pstInsert.setString(++i, sintesisAttivita);
		pstInsert.setString(++i, sintesisDescrizioneSezione);
		pstInsert.setInt(++i, enteredby);
		pstInsert.setTimestamp(++i, dataUltimoAggiornamentoSintesis);
		pstInsert.setBoolean(++i, true);
		
		ResultSet rsInsert = pstInsert.executeQuery();
		if (rsInsert.next())
			idInsertRelazione = rsInsert.getInt("id_inserito");
	}
		idRelazione = idInsertRelazione;
}
public boolean sonoDiversi(SintesisRelazioneLineaProduttiva relDaImportare, SintesisRelazioneLineaProduttiva relEsistente) {

	boolean esisteDiverso = false;
	
	if (relDaImportare.getStabilimento().getStato()!= relEsistente.getStabilimento().getStato()){
		esisteDiverso = true;
	}
	
	if (checkStringhe(relDaImportare.getStabilimento().getDenominazione(),  relEsistente.getStabilimento().getDenominazione())){
		esisteDiverso = true;
	}
	if (checkStringhe(relDaImportare.getStabilimento().getOperatore().getRagioneSociale(),  relEsistente.getStabilimento().getOperatore().getRagioneSociale())){
		esisteDiverso = true;
	}
	
	if (checkStringhe(relDaImportare.getStabilimento().getIndirizzo().getVia(),  relEsistente.getStabilimento().getIndirizzo().getVia())){
		esisteDiverso = true;
	}
	if (checkStringhe(relDaImportare.getStabilimento().getIndirizzo().getCivico(),  relEsistente.getStabilimento().getIndirizzo().getCivico())){
		esisteDiverso = true;
	}
	if (relDaImportare.getStabilimento().getIndirizzo().getToponimo()!= relEsistente.getStabilimento().getIndirizzo().getToponimo()){
		esisteDiverso = true;
	}
		
	if (relDaImportare.getStabilimento().getIndirizzo().getComune()!= relEsistente.getStabilimento().getIndirizzo().getComune()){
		esisteDiverso = true;
	}
	
	
	if (relDaImportare.getStato()!= relEsistente.getStato()){
		esisteDiverso = true;
	}
	if (relDaImportare.getDataInizio()!= relEsistente.getDataInizio()){
		esisteDiverso = true;
	}
	if (relDaImportare.getDataFine()!= relEsistente.getDataFine()){
		esisteDiverso = true;
	}
	if (checkStringhe(relDaImportare.getTipoAutorizzazione(),  relEsistente.getTipoAutorizzazione())){
		esisteDiverso = true;
	}
	if (checkStringhe(relDaImportare.getImballaggio(),  relEsistente.getImballaggio())){
		esisteDiverso = true;
	}
	if (checkStringhe(relDaImportare.getPaesiAbilitatiExport(),  relEsistente.getPaesiAbilitatiExport())){
		esisteDiverso = true;
	}
	if (checkStringhe(relDaImportare.getRemark(),  relEsistente.getRemark())){
		esisteDiverso = true;
	}
	
	if (checkStringhe(relDaImportare.getSpecies(),  relEsistente.getSpecies())){
		esisteDiverso = true;
	}


	return esisteDiverso;
	
}

public boolean isDiversa(Connection db, SintesisRelazioneLineaProduttiva relEsistente) {
	boolean idDiverso = false;
	
	if (this.getStato()!= relEsistente.getStato()){
		idDiverso = true;
	}
	
	if (this.getDataInizio()!= relEsistente.getDataInizio()){
		idDiverso = true;
	}
	
	if (this.getDataFine()!= relEsistente.getDataFine()){
		idDiverso = true;
	}
	
	if (!this.getTipoAutorizzazione().equalsIgnoreCase(relEsistente.getTipoAutorizzazione())){
		idDiverso = true;
	}
	if (!this.getImballaggio().equalsIgnoreCase(relEsistente.getImballaggio())){
		idDiverso = true;
	}
	if (!this.getPaesiAbilitatiExport().equalsIgnoreCase(relEsistente.getPaesiAbilitatiExport())){
		idDiverso = true;
	}
	if (!this.getRemark().equalsIgnoreCase(relEsistente.getRemark())){
		idDiverso = true;
	}
	if (!this.getSpecies().equalsIgnoreCase(relEsistente.getSpecies())){
		idDiverso = true;
	}
	
	return idDiverso;
}
public void aggiornaRelazione(Connection db) throws SQLException {
	
	String sql = "update sintesis_relazione_stabilimento_linee_produttive set data_inizio = ?, data_fine = ?, tipo_autorizzazione = ? , imballaggio = ?, paesi_abilitati_export = ?, remark = ?, species = ?, modifiedby = ?, modified = now(), data_ultimo_aggiornamento_sintesis =?";
	if (stato>-1)
		sql+=", stato = ?";
	sql+= "where id = ?";
	
	PreparedStatement pst = db.prepareStatement(sql);
	int i = 0;
	pst.setTimestamp(++i, dataInizio);
	pst.setTimestamp(++i, dataFine);
	pst.setString(++i, tipoAutorizzazione);
	pst.setString(++i, imballaggio);
	pst.setString(++i, paesiAbilitatiExport);
	pst.setString(++i, remark);
	pst.setString(++i, species);
	pst.setInt(++i, modifiedby);
	pst.setTimestamp(++i, dataUltimoAggiornamentoSintesis);
	
	if (stato>-1)
		pst.setInt(++i, stato);

	pst.setInt(++i, idRelazione);
	pst.executeUpdate();

}
public String getPathCompleto() {
	return pathCompleto;
}
public void setPathCompleto(String pathCompleto) {
	this.pathCompleto = pathCompleto;
}

public void setPathCompleto(Connection db) throws SQLException {
	String lineaAttivitaMasterList = GestioneMasterList.getPathCompleto(db, idLineaMasterList);
	this.pathCompleto = lineaAttivitaMasterList;
}
public void setCodiceUnivoco(Connection db) throws SQLException {
	String codiceUnivoco = GestioneMasterList.getCodiceUnivoco(db, idLineaMasterList);
	this.codiceUnivoco = codiceUnivoco;
}

public String getPathCompletoLineaProduttivaOld() {
	return pathCompletoLineaProduttivaOld;
}
public void setPathCompletoLineaProduttivaOld(String pathCompletoLineaProduttivaOld) {
	this.pathCompletoLineaProduttivaOld = pathCompletoLineaProduttivaOld;
}
public Timestamp getDataUltimoAggiornamentoSintesis() {
	return dataUltimoAggiornamentoSintesis;
}
public void setDataUltimoAggiornamentoSintesis(Timestamp dataUltimoAggiornamentoSintesis) {
	this.dataUltimoAggiornamentoSintesis = dataUltimoAggiornamentoSintesis;
}
private boolean checkStringhe(String string1, String string2){
	if (string1==null && string2==null)
		return false;
	else if ((string1!=null && string2 ==null) || (string1==null && string2 !=null))
		return true;
	else if (!string1.equalsIgnoreCase(string2))
		return true;
	return false;
	
}
public boolean rispettaWorkFlow(Connection db, SintesisRelazioneLineaProduttiva relEsistente, SintesisStabilimento stabilimentoEsistente) throws SQLException {
	boolean rispettaStab = false;
	boolean rispettaLinea = false;
	
	
	int stabStatoAttuale = stabilimentoEsistente.getStato();
	int stabStatoFuturo = this.getStabilimento().getStato();
	
	int lineaStatoAttuale = relEsistente.getStato();
	int lineaStatoFuturo = this.getStato();
	
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	if (stabStatoAttuale == stabStatoFuturo){
		rispettaStab = true;
	}
	else {
		//wf stab
		pst = db.prepareStatement("select * from sintesis_workflow_stabilimento where id_stato = ? and id_stato_futuro = ?");
		pst.setInt(1, stabStatoAttuale);
		pst.setInt(2, stabStatoFuturo);
		rs = pst.executeQuery();
		if (rs.next())
			rispettaStab = true;
	}
	
	if (lineaStatoAttuale == lineaStatoFuturo){
		rispettaLinea = true;
	}
	else {
		//wf linea
		pst = db.prepareStatement("select * from sintesis_workflow_attivita where id_stato = ? and id_stato_futuro = ?");
		pst.setInt(1, lineaStatoAttuale);
		pst.setInt(2, lineaStatoFuturo);
		rs = pst.executeQuery();
		if (rs.next())
			rispettaLinea = true;
	}
	
	StabilimentoImportUtil.stampaLog("[SINTESIS] WorkFlow rispetta Sede Operativa: "+rispettaStab+".");
	StabilimentoImportUtil.stampaLog("[SINTESIS] WorkFlow rispetta Linea: "+rispettaStab+".");

	
	if (rispettaLinea && rispettaStab)
		return true;
	else
		return false;
}

public void setAutomezzi(Connection db) throws SQLException {
	this.automezzi = SintesisAutomezzo.getElencoAutomezzi(db, idRelazione);
	}
public ArrayList<SintesisAutomezzo> getAutomezzi() {
	return automezzi;
}
public void setAutomezzi(ArrayList<SintesisAutomezzo> automezzi) {
	this.automezzi = automezzi;
}
public String getCodiceUnivoco() {
	return codiceUnivoco;
}
public void setCodiceUnivoco(String codiceUnivoco) {
	this.codiceUnivoco = codiceUnivoco;
}
}
