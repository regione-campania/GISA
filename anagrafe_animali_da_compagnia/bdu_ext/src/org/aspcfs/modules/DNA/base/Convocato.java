/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.DNA.base;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.beans.GenericBean;

public class Convocato extends GenericBean {
	
	public static int da_convocare = 1;
	public static int convocato_non_presentato = 2;
	public static int convocato_ma_escluso_per_regolarizzazione = 3;
	public static int presentato = 4;
	

	private int id = -1;
	private String codiceFiscale = "";
	private String nome = "";
	private String cognome = "";
	private Timestamp dataNascita = null;
	private int numeroCani = -1;
	private String indirizzo = "";
	private String microchip = "";
	private int idListaConvocazione = -1;
	private int idListaConvocazioneTemporale = -1; //Ultimo gruppo di convocazione di appartenenza
	private int idStatoPresentazione = -1;
	private int idComune = -1;
	
	private Timestamp dataPrelievo = null; //Informazione aggiornata da BDU
	
	public int getIdComune() {
		return idComune;
	}


	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}


	private Timestamp dataInserimento;
	private Timestamp dataModifica;
	private int idUtenteInserimento = -1;
	private int idUtenteModifica = -1;
	
	
	public Convocato() {
		
		super();
		idStatoPresentazione = da_convocare;
		// TODO Auto-generated constructor stub
	}
	
	
	public int getIdStatoPresentazione() {
		return idStatoPresentazione;
	}

	
	
	public int getIdListaConvocazioneTemporale() {
		return idListaConvocazioneTemporale;
	}

	

	public Timestamp getDataPrelievo() {
		return dataPrelievo;
	}


	public void setDataPrelievo(Timestamp dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}


	public void setIdListaConvocazioneTemporale(int idListaConvocazioneTemporale) {
		this.idListaConvocazioneTemporale = idListaConvocazioneTemporale;
	}


	public void setIdStatoPresentazione(int idStatoPresentazione) {
		this.idStatoPresentazione = idStatoPresentazione;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	
	public Timestamp getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = DatabaseUtils.parseDateToTimestamp(dataNascita);;
	}
	public int getNumeroCani() {
		return numeroCani;
	}
	public void setNumeroCani(int numeroCani) {
		this.numeroCani = numeroCani;
	}
	
	public void setNumeroCani(String numeroCani) {
		this.numeroCani = new Integer(numeroCani);
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	
	
	public Timestamp getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Timestamp getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(Timestamp dataModifica) {
		this.dataModifica = dataModifica;
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
	
	
	public String getMicrochip() {
		return microchip;
	}
	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}
	public int getIdListaConvocazione() {
		return idListaConvocazione;
	}
	public void setIdListaConvocazione(int idListaConvocazione) {
		this.idListaConvocazione = idListaConvocazione;
	}
	public boolean insert(Connection db) throws SQLException{
		
		boolean inserted = true;
		
		StringBuffer sql = new StringBuffer();

		try {
			
			sql.append("INSERT INTO convocazioni ( codice_fiscale, nome, cognome, data_nascita, indirizzo, microchip, id_lista_convocazione, id_comune, " +
					" id_stato_presentazione, " ); 
	
			
			sql.append(" data_inserimento, data_modifica, utente_inserimento, utente_modifica ) VALUES ( ? , ? , ? , ?, ? , ?, ?,? ,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?)");
			
			
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setString(++i, codiceFiscale);
			pst.setString(++i, nome);
			pst.setString(++i, cognome);
			pst.setTimestamp(++i, dataNascita);
			pst.setString(++i, indirizzo);
			pst.setString(++i, microchip);
			pst.setInt(++i, idListaConvocazione);
			pst.setInt(++i, idComune);
			pst.setInt(++i, idStatoPresentazione);
			//pst.setTimestamp( ++i, dataInserimento );
			//pst.setTimestamp( ++i, dataModifica );
			pst.setInt(++i, idUtenteInserimento);
			pst.setInt(++i, idUtenteModifica);

			

			pst.execute();
			
			this.id = DatabaseUtils.getCurrVal(db, "convocazioni_id_seq",
					id);
			pst.close();

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}

		
		
		return inserted;
	}
	
	
	public void buildRecord(ResultSet rs) throws SQLException {
		
		this.id = rs.getInt("id");
		this.codiceFiscale = rs.getString("codice_fiscale");
		this.nome = rs.getString("nome");
		this.cognome = rs.getString("cognome");
		this.indirizzo = rs.getString("indirizzo");
		this.dataNascita = rs.getTimestamp("data_nascita");
		this.dataInserimento = rs.getTimestamp("data_inserimento");
		this.dataModifica = rs.getTimestamp("data_modifica");
		this.id = rs.getInt("id");
		this.idListaConvocazione = rs.getInt("id_lista_convocazione");
		this.idUtenteInserimento = rs.getInt("utente_inserimento");
		this.idUtenteModifica = rs.getInt("utente_modifica");
		this.microchip = rs.getString("microchip");
		this.idStatoPresentazione = rs.getInt("id_stato_presentazione");
		this.idListaConvocazioneTemporale = rs.getInt("id_lista_convocazione_temporale_ultima");
		this.idComune = rs.getInt("id_comune");
		this.dataPrelievo = rs.getTimestamp("data_prelievo_dna");
		
	}
	
	
	public void updateInformazioniConvocazione(Connection db, int idConvocazioneTemp) throws SQLException{
		
		int i = 0;
		
		if (this.idListaConvocazioneTemporale > 0){ //l'ultima, quindi apparteneva gia a una convocazione
			//Se appartiene già a una lista di convocazione conservo questa informazione come storico
		//	String insert_storico = "insert into storico_convocazioni(id_convocato, id_lista_convocazione) values (?,?)";
//			PreparedStatement pst = db.prepareStatement(insert_storico);
//			//int i = 0;
//			pst.setInt(++i, this.id);
//			pst.setInt(++i, this.getIdListaConvocazioneTemporale());
//			pst.execute();
			
			
			String disableOld = "update relazione_convocazione_convocati set enabled = false where id_convocato = ? and enabled = true";
			PreparedStatement pst = db.prepareStatement(disableOld);
			//int i = 0;
			pst.setInt(++i, this.id);
			//pst.setInt(++i, this.getIdListaConvocazioneTemporale());
			pst.execute();
		}

		//Dopo aver salvato lo storico aggiorno id della convocazione temporale
		this.setIdListaConvocazioneTemporale(idConvocazioneTemp);
		String insert_relazione = "insert into relazione_convocazione_convocati (id_convocato, id_lista_convocazione_temporale, enabled, id_stato_presentazione) " +
				"values (?, ?, true, ?)";
		PreparedStatement pst1 = db.prepareStatement(insert_relazione);
		i = 0;
		pst1.setInt(++i, id);
		pst1.setInt(++i, this.idListaConvocazioneTemporale);
		pst1.setInt(++i, convocato_non_presentato);
		
		pst1.execute();
		
		
		//Aggiorno record del convocato
		String update_convocato = "update convocazioni set id_lista_convocazione_temporale_ultima = ?, id_stato_presentazione = ? where id = ?";
		PreparedStatement pst2 = db.prepareStatement(update_convocato);
		i = 0;
		pst2.setInt(++i, this.idListaConvocazioneTemporale);
		pst2.setInt(++i, convocato_non_presentato);
		pst2.setInt(++i, id);
		
		pst2.execute();
		
		idStatoPresentazione = convocato_non_presentato;
		
	}
	
	
	public boolean checkEsistenza(Connection db) throws SQLException{
		
		boolean esistente  = false;
		PreparedStatement pst = db.prepareStatement("Select * from verifica_esistenza_convocato(?, ?, ?)");
		pst.setString(1, this.getCodiceFiscale());
		pst.setString(2, this.getMicrochip());
		pst.setInt(3, this.getIdComune());
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
			esistente = true;
		
		return esistente;
	}
	
public void updateStatoConvocazione(Connection db, int idConvocazioneTemp) throws SQLException{
		
		int i = 0;
		
		//Aggiorno record del convocato
		String update_convocato = "update convocazioni set id_lista_convocazione_temporale_ultima = ?, id_stato_presentazione = ? where id = ?";
		PreparedStatement pst2 = db.prepareStatement(update_convocato);
		i = 0;
		pst2.setInt(++i, this.idListaConvocazioneTemporale);
		pst2.setInt(++i, this.idStatoPresentazione);
		pst2.setInt(++i, id);
		
		pst2.execute();
		
		
		
	}

	public ConvocazioneTemporale getUltimaConvocazioneTemporaleSoggetto() throws Exception{
		
		ConvocazioneTemporale temp = null;
		Connection db = null;
		
		try{
		
//		String bdu_db_name = ApplicationProperties.getProperty("PORTALE_DBNAME");
//		String bdu_db_user = ApplicationProperties.getProperty("PORTALE_DBUSER");
//		String bdu_db_pwd = ApplicationProperties.getProperty("BPORTALE_DBPWD");
//		String bdu_db_host = InetAddress.getByName("IMPORTATORIDBSERVER").getHostAddress();
		int i = 0;
		//db = DbUtil.getConnection(bdu_db_name, bdu_db_user, bdu_db_pwd, bdu_db_host);
		
		db = GestoreConnessioni.getConnection();
		
		temp = new ConvocazioneTemporale(this.getIdListaConvocazioneTemporale(), db);
		}catch (SQLException e) {
			throw e;
		}finally{
			GestoreConnessioni.freeConnection(db);
		}
		
	return temp;
}
	
}
