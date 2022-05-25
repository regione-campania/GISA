/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.partitecommerciali_ext.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.anagrafe_animali_ext.base.Animale;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu_ext.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu_ext.base.Operatore;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;




import com.darkhorseventures.framework.beans.GenericBean;

public class PartitaCommerciale extends GenericBean {
	

	private static Logger log = Logger.getLogger( org.aspcfs.modules.partitecommerciali_ext.base.PartitaCommerciale.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}
	
	public static final int ID_STATO_APERTO = 1 ;
	public static final int ID_STATO_CHIUSO = 2 ;
	public static final int ID_STATO_INVIATO= 3 ;
	
	private int idStato ;
	private int idPartitaCommerciale = -1;
	private int idAslRiferimento = -1;
	private int idTipoPartita = -1;
	private int idImportatore = -1;
	private String nrCertificato = "";
	private int idNazioneProvenienza = -1;
	private int nrAnimaliPartita = -1;
	private java.sql.Timestamp dataArrivoPrevista = null;
	private java.sql.Timestamp dataArrivoEffettiva = null;
	private Operatore operatoreCommerciale = null;
	
	private boolean flagPresenzaVincoloSanitario = false;
	private boolean flagControlloDocumentaleRichiesto = false;
	private boolean flagControlloIdentitaRichiesto = false;
	private boolean flagControlloFisicoRichiesto = false;
	private boolean flagControlloLaboratorioRichiesto = false;
	
	private int idStatoImportatore = -1;
	
	private ArrayList<String> listMicrochipAnimaliConVincolo = new ArrayList<String>();
	
	
	private int idUtenteInserimento = -1;
	private int idUtenteModifica = -1;
	private java.sql.Timestamp dataInserimento = null;
	private java.sql.Timestamp dataModifica = null;
	
	
	
	public int getIdStatoImportatore() {
		return idStatoImportatore;
	}
	public void setIdStatoImportatore(int idStatoImportatore) {
		this.idStatoImportatore = idStatoImportatore;
	}
	public int getIdStato() {
		return idStato;
	}
	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}
	public static Logger getLog() {
		return log;
	}
	public static void setLog(Logger log) {
		PartitaCommerciale.log = log;
	}
	public int getIdPartitaCommerciale() {
		return idPartitaCommerciale;
	}
	public void setIdPartitaCommerciale(int idPartitaCommerciale) {
		this.idPartitaCommerciale = idPartitaCommerciale;
	}
	public void setIdPartitaCommerciale(String idPartitaCommerciale) {
		this.idPartitaCommerciale = new Integer (idPartitaCommerciale).intValue();
	}
	public int getIdAslRiferimento() {
		return idAslRiferimento;
	}
	public void setIdAslRiferimento(int idAslRiferimento) {
		this.idAslRiferimento = idAslRiferimento;
	}
	public void setIdAslRiferimento(String idAslRiferimento) {
		this.idAslRiferimento = new Integer (idAslRiferimento).intValue();
	}
	public int getIdTipoPartita() {
		return idTipoPartita;
	}
	public void setIdTipoPartita(int idTipoPartita) {
		this.idTipoPartita = idTipoPartita;
	}
	public void setIdTipoPartita(String idTipoPartita) {
		this.idTipoPartita = new Integer(idTipoPartita).intValue();
	}
	public int getIdImportatore() {
		return idImportatore;
	}
	public void setIdImportatore(int idImportatore) {
		this.idImportatore = idImportatore;
	}
	public void setIdImportatore(String idImportatore) {
		this.idImportatore = new Integer(idImportatore).intValue();
	}
	public String getNrCertificato() {
		return nrCertificato;
	}
	public void setNrCertificato(String nrCertificato) {
		this.nrCertificato = nrCertificato;
	}
	public int getIdNazioneProvenienza() {
		return idNazioneProvenienza;
	}
	public void setIdNazioneProvenienza(int idNazioneProvenienza) {
		this.idNazioneProvenienza = idNazioneProvenienza;
	}
	public void setIdNazioneProvenienza(String idNazioneProvenienza) {
		this.idNazioneProvenienza = new Integer (idNazioneProvenienza).intValue();
	}
	public int getNrAnimaliPartita() {
		return nrAnimaliPartita;
	}
	public void setNrAnimaliPartita(int nrAnimaliPartita) {
		this.nrAnimaliPartita = nrAnimaliPartita;
	}
	public void setNrAnimaliPartita(String nrAnimaliPartita) {
		this.nrAnimaliPartita = new Integer (nrAnimaliPartita).intValue();
	}
	public java.sql.Timestamp getDataArrivoPrevista() {
		return dataArrivoPrevista;
	}
	public void setDataArrivoPrevista(java.sql.Timestamp dataArrivoPrevista) {
		this.dataArrivoPrevista = dataArrivoPrevista;
	}
	public void setDataArrivoPrevista(String dataArrivoPrevista) {
		this.dataArrivoPrevista = DateUtils.parseDateStringNew(dataArrivoPrevista, "dd/MM/yyyy");
	}
	public java.sql.Timestamp getDataArrivoEffettiva() {
		return dataArrivoEffettiva;
	}
	public void setDataArrivoEffettiva(java.sql.Timestamp dataArrivoEffettiva) {
		this.dataArrivoEffettiva = dataArrivoEffettiva;
	}
	public void setDataArrivoEffettiva(String dataArrivoEffettiva) {
		this.dataArrivoEffettiva = DateUtils.parseDateStringNew(dataArrivoEffettiva, "dd/MM/yyyy");
	}
	public boolean isFlagPresenzaVincoloSanitario() {
		return flagPresenzaVincoloSanitario;
	}
	public void setFlagPresenzaVincoloSanitario(boolean flagPresenzaVincoloSanitario) {
		this.flagPresenzaVincoloSanitario = flagPresenzaVincoloSanitario;
	}
	public void setFlagPresenzaVincoloSanitario(String tmp) {
		this.flagPresenzaVincoloSanitario = DatabaseUtils.parseBoolean(tmp);
	}
	public boolean isFlagControlloDocumentaleRichiesto() {
		return flagControlloDocumentaleRichiesto;
	}
	public void setFlagControlloDocumentaleRichiesto(
			boolean flagControlloDocumentaleRichiesto) {
		this.flagControlloDocumentaleRichiesto = flagControlloDocumentaleRichiesto;
	}
	public void setFlagControlloDocumentaleRichiesto(String tmp) {
		this.flagControlloDocumentaleRichiesto = DatabaseUtils.parseBoolean(tmp);
	}
	public boolean isFlagControlloIdentitaRichiesto() {
		return flagControlloIdentitaRichiesto;
	}
	public void setFlagControlloIdentitaRichiesto(
			boolean flagControlloIdentitaRichiesto) {
		this.flagControlloIdentitaRichiesto = flagControlloIdentitaRichiesto;
	}
	public void setFlagControlloIdentitaRichiesto(String tmp) {
		this.flagControlloIdentitaRichiesto = DatabaseUtils.parseBoolean(tmp);
	}
	public boolean isFlagControlloFisicoRichiesto() {
		return flagControlloFisicoRichiesto;
	}
	public void setFlagControlloFisicoRichiesto(boolean flagControlloFisicoRichiesto) {
		this.flagControlloFisicoRichiesto = flagControlloFisicoRichiesto;
	}
	public void setFlagControlloFisicoRichiesto(String tmp) {
		this.flagControlloFisicoRichiesto = DatabaseUtils.parseBoolean(tmp);
	}
	public boolean isFlagControlloLaboratorioRichiesto() {
		return flagControlloLaboratorioRichiesto;
	}
	public void setFlagControlloLaboratorioRichiesto(
			boolean flagControlloLaboratorioRichiesto) {
		this.flagControlloLaboratorioRichiesto = flagControlloLaboratorioRichiesto;
	}
	public void setFlagControlloLaboratorioRichiesto(String tmp) {
		this.flagControlloLaboratorioRichiesto = DatabaseUtils.parseBoolean(tmp);
	}
	
	public Operatore getOperatoreCommerciale() {
		return operatoreCommerciale;
	}
	public void setOperatoreCommerciale(Operatore operatoreCommerciale) {
		this.operatoreCommerciale = operatoreCommerciale;
	}


	
	public int getIdUtenteInserimento() {
		return idUtenteInserimento;
	}
	public void setIdUtenteInserimento(int idUtenteInserimento) {
		this.idUtenteInserimento = idUtenteInserimento;
	}
	public int getIdUtenteModifica() {
		return idUtenteModifica;
	}
	public void setIdUtenteModifica(int idUtenteModifica) {
		this.idUtenteModifica = idUtenteModifica;
	}
	public java.sql.Timestamp getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(java.sql.Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public java.sql.Timestamp getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(java.sql.Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}
	
	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = DateUtils.parseDateStringNew(dataInserimento, "dd/MM/yyyy");
	}
	public void setDataModifica(String dataModifica) {
		this.dataModifica = DateUtils.parseDateStringNew(dataModifica, "dd/MM/yyyy");
	}
	
	
	
	public ArrayList<String> getListMicrochipAnimaliConVincolo() {
		return listMicrochipAnimaliConVincolo;
	}
	public void setListMicrochipAnimaliConVincolo(
			ArrayList<String> listMicrochipAnimaliConVincolo) {
		this.listMicrochipAnimaliConVincolo = listMicrochipAnimaliConVincolo;
	}
	
	public void setListMicrochipAnimaliConVincolo(
			String[] listMicrochipAnimaliConVincolo) {
		System.out.println("ppo");
		for (int i = 0; i < listMicrochipAnimaliConVincolo.length; i++){
			this.listMicrochipAnimaliConVincolo.add(listMicrochipAnimaliConVincolo[i]);
		}
	}
	public PartitaCommerciale() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			


			idPartitaCommerciale = DatabaseUtils.getNextSeqPostgres(db, "partita_commerciale_id_partita_commerciale_seq");
			// sql.append("INSERT INTO animale (");





			sql.append("INSERT INTO partita_commerciale( ");
			
			if (idPartitaCommerciale > -1) {
				sql.append("id_partita_commerciale ");
			}
						
			if (idAslRiferimento > -1 )
			{
				sql.append(", id_asl_riferimento");
			}
			if (idTipoPartita > -1 )
			{
				sql.append(", id_tipo_partita");
			}
			
			
			if (idImportatore > -1)
			{
				sql.append(", id_importatore");
			}
			if (nrCertificato != "")
			{
				sql.append(",nr_certificato");
			}
			if (idNazioneProvenienza > -1)
			{
				sql.append(", id_nazione_provenienza");
			}
			if (nrAnimaliPartita > -1)
			{
				sql.append(", nr_animali_partita");
			}
			
			if (dataArrivoPrevista != null){
				
				sql.append(", data_arrivo_prevista");
			}
			
			if (dataArrivoEffettiva != null){
				
				sql.append(", data_arrivo_effettiva");
			}
			
			if(flagPresenzaVincoloSanitario){
				sql.append(", flag_presenza_vincolo_sanitario");
			}
			
			if(flagControlloDocumentaleRichiesto){
				sql.append(", flag_controllo_documentale_richiesto");
			}
			
			if(flagControlloFisicoRichiesto){
				sql.append(", flag_controllo_fisico_richiesto");
			}
			
			if(flagControlloIdentitaRichiesto){
				sql.append(", flag_controllo_identita_richiesto");
			}
			
			if (flagControlloLaboratorioRichiesto){
				sql.append(", flag_controllo_laboratorio_richiesto");
			}
			
			sql.append(", id_utente_inserimento, id_utente_modifica, data_inserimento, data_modifica,id_stato");
			sql.append(")");

			sql.append("VALUES ( ") ;

			if (idPartitaCommerciale > -1) {
				sql.append("? ");
			}

			if (idAslRiferimento > -1 )
			{
				sql.append(", ?");
			}
			if (idTipoPartita > -1 )
			{
				sql.append(", ?");
			}
			
			
			if (idImportatore > -1)
			{
				sql.append(", ?");
			}
			if (nrCertificato != "")
			{
				sql.append(", ?");
			}
			if (idNazioneProvenienza > -1)
			{
				sql.append(", ?");
			}
			if (nrAnimaliPartita > -1)
			{
				sql.append(", ?");
			}
			
			if (dataArrivoPrevista != null){
				
				sql.append(", ?");
			}
			if (dataArrivoEffettiva != null){
				
				sql.append(", ?");
			}
			
			if(flagPresenzaVincoloSanitario){
				sql.append(", ?");
			}
			
			if(flagControlloDocumentaleRichiesto){
				sql.append(", ?");
			}
			
			if(flagControlloFisicoRichiesto){
				sql.append(", ?");
			}
			
			if(flagControlloIdentitaRichiesto){
				sql.append(", ?");
			}
			
			if (flagControlloLaboratorioRichiesto){
				sql.append(", ?");
			}
			
			sql.append(", ?, ?, ?, ?,?");
			sql.append(")");


			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());



			if (idPartitaCommerciale > -1) {
				pst.setInt(++i, idPartitaCommerciale);
			}



			if (idAslRiferimento > -1 )
			{
				pst.setInt(++i, idAslRiferimento);
			}
			if (idTipoPartita > -1 )
			{
				pst.setInt(++i, idTipoPartita);
			}
			
			
			if (idImportatore > -1)
			{
				pst.setInt(++i, idImportatore);
			}
			
			if (nrCertificato != "")
			{
				pst.setString(++i, nrCertificato);
			}
			if (idNazioneProvenienza > -1)
			{
				pst.setInt(++i, idNazioneProvenienza);
			}
			if (nrAnimaliPartita > -1)
			{
				pst.setInt(++i, nrAnimaliPartita);
			}
			
			if (dataArrivoPrevista != null){
				
				pst.setTimestamp(++i, dataArrivoPrevista);
			}
			if (dataArrivoEffettiva != null){
				
				pst.setTimestamp(++i, dataArrivoEffettiva);
			}
			
			if(flagPresenzaVincoloSanitario){
				pst.setBoolean(++i, flagPresenzaVincoloSanitario);
			}
			
			if(flagControlloDocumentaleRichiesto){
				pst.setBoolean(++i, flagControlloDocumentaleRichiesto);
			}
			
			if(flagControlloFisicoRichiesto){
				pst.setBoolean(++i, flagControlloFisicoRichiesto);
			}
			
			if(flagControlloIdentitaRichiesto){
				pst.setBoolean(++i, flagControlloIdentitaRichiesto);
			}
			
			if (flagControlloLaboratorioRichiesto){
				pst.setBoolean(++i, flagControlloLaboratorioRichiesto);
			}
			
			pst.setInt(++i, idUtenteInserimento);
			pst.setInt(++i, idUtenteModifica);
			pst.setTimestamp(++i, dataInserimento);
			pst.setTimestamp(++i, dataModifica);
			pst.setInt(++i, 1);
			sql.append(")");
			pst.execute();
			pst.close();

			this.idPartitaCommerciale = DatabaseUtils.getCurrVal(db, "partita_commerciale_id_partita_commerciale_seq", idPartitaCommerciale);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}
	
	
	
	
	
	public PartitaCommerciale (Connection db, int idPartita) throws SQLException{
		
		if (idPartita == -1){
			throw new SQLException("Invalid Partita");
		}

	//	PreparedStatement pst = db.prepareStatement("Select * from partita_commerciale where id_partita_commerciale = ?");
		
		PreparedStatement pst = db.prepareStatement("Select * from public_functions.get_dettaglio_partita(?)");
		pst.setInt(1, idPartita);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			this.buildRecord(rs);
			
			if (rs.getInt("id_importatore") > -1)
			{
				operatoreCommerciale = new Operatore();
				operatoreCommerciale.queryRecordOperatorebyIdLineaProduttiva(db,rs.getInt("id_importatore"));
			}

			
		



			this.setOperatoreCommerciale(operatoreCommerciale);
			
		}

		if (idPartita == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}



		rs.close();
		pst.close();
	}

		
	private void buildRecord(ResultSet rs) throws SQLException {
		
		this.idPartitaCommerciale = rs.getInt("id_partita_commerciale");
		this.idAslRiferimento = rs.getInt("id_asl_riferimento");
		this.idImportatore = rs.getInt("id_importatore");
		this.idNazioneProvenienza = rs.getInt("id_nazione_provenienza");
		this.idTipoPartita = rs.getInt("id_tipo_partita");
		
		this.idStatoImportatore = rs.getInt("id_stato_importatore");
		
		
		this.idUtenteInserimento = rs.getInt("id_utente_inserimento");
		this.idUtenteModifica = rs.getInt("id_utente_modifica");
		this.dataInserimento = rs.getTimestamp("data_inserimento");
		this.dataModifica = rs.getTimestamp("data_modifica");
		
		this.idStato = rs.getInt("id_stato");
		this.flagControlloDocumentaleRichiesto = rs.getBoolean("flag_controllo_documentale_richiesto");
		this.flagControlloFisicoRichiesto = rs.getBoolean("flag_controllo_fisico_richiesto");
		this.flagControlloIdentitaRichiesto = rs.getBoolean("flag_controllo_identita_richiesto");
		this.flagControlloLaboratorioRichiesto = rs.getBoolean("flag_controllo_laboratorio_richiesto");
		this.flagPresenzaVincoloSanitario = rs.getBoolean("flag_presenza_vincolo_sanitario");
		
		this.dataArrivoEffettiva = rs.getTimestamp("data_arrivo_effettiva");
		this.dataArrivoPrevista = rs.getTimestamp("data_arrivo_prevista");
		this.nrCertificato = rs.getString("nr_certificato");
		this.nrAnimaliPartita = rs.getInt("nr_animali_partita");
		





	}
	
	
	public int update(Connection db) throws SQLException {
		int resultCount = 0;
	
		if (this.getIdPartitaCommerciale() == -1) {
			return -1;
		}
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE partita_commerciale" 				
				+" SET id_asl_riferimento=?, id_tipo_partita=?,"
				+" id_importatore=?, nr_certificato=?, id_nazione_provenienza=?, "
				+"nr_animali_partita=?, data_arrivo_prevista=?, flag_presenza_vincolo_sanitario=?," 
				+"flag_controllo_documentale_richiesto=?, flag_controllo_identita_richiesto=?, "
				+" flag_controllo_fisico_richiesto=?, flag_controllo_laboratorio_richiesto=?, "
				+" data_arrivo_effettiva=?, id_utente_modifica=?, "
				+" data_modifica="+DatabaseUtils.getCurrentTimestamp(db)
				+" WHERE id_partita_commerciale = ?");
				

		int i = 0;
		pst = db.prepareStatement(sql.toString());
	
		pst.setInt(++i, idAslRiferimento);
		pst.setInt(++i, idTipoPartita);
		pst.setInt(++i, idImportatore);
		pst.setString(++i, nrCertificato);
		pst.setInt(++i, idNazioneProvenienza);
		pst.setInt(++i, nrAnimaliPartita);
		pst.setTimestamp(++i, dataArrivoPrevista);
		pst.setBoolean(++i, flagPresenzaVincoloSanitario);
		pst.setBoolean(++i, flagControlloDocumentaleRichiesto);
		pst.setBoolean(++i, flagControlloIdentitaRichiesto);
		pst.setBoolean(++i, flagControlloFisicoRichiesto);
		pst.setBoolean(++i, flagControlloLaboratorioRichiesto);
		pst.setTimestamp(++i, dataArrivoEffettiva);
		pst.setInt(++i, idUtenteModifica);
		pst.setInt(++i, idPartitaCommerciale);
		
		
		

		
		
		
		resultCount = pst.executeUpdate();
		pst.close();
	
		return resultCount;
	}
	
	
	
	public int cambiaStatoPartita(Connection db) throws SQLException {
		int resultCount = 0;
	
		if (this.getIdPartitaCommerciale() == -1) {
			return -1;
		}
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from public_functions.update_stato_partita(?,?,?)");
		
/*		sql.append("UPDATE partita_commerciale" 				
				+" SET id_stato = ? , id_utente_modifica=?, "
				+" data_modifica="+DatabaseUtils.getCurrentTimestamp(db)
				+" WHERE id_partita_commerciale = ?");*/
				

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setInt(++i, idPartitaCommerciale);
		pst.setInt(++i, idStato);
		pst.setInt(++i, idUtenteModifica);
		
		
		 pst.execute();
		pst.close();
	
		return resultCount;
	}
	
	
	public PartitaCommerciale(ResultSet rs) throws SQLException {
		this.buildRecord(rs);
	}
	
	
	public void updateDataArrivoEffettivo(Connection con) throws SQLException{
		PreparedStatement pst = con.prepareStatement("select * from public_functions.update_data_arrivo_effettivo(?,?::timestamp,?)");
	    int i = 0;	
		pst.setInt(++i, this.getIdPartitaCommerciale());
		pst.setTimestamp(++i, this.getDataArrivoEffettiva());
		pst.setInt(++i, this.getIdUtenteModifica());
		pst.execute();
		
		pst.close();
	}
	


	
	

}
