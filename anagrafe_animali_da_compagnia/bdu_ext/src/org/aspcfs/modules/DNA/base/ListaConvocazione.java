/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.DNA.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.opu_ext.base.ComuniAnagrafica;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.CFSFileReader;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.formatter.ContactNameFormatter;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;

public class ListaConvocazione extends GenericBean implements Runnable {

	public static int elaborazione_in_corso = 1;
	public static int terminato_con_errori = 2;
	public static int terminato_senza_errori = 3;
	
	public static int esito_deceduto = 1;
	public static int esito_furto_smarrito = 2;
	public static int esito_convocabile = 3;
	
	public static int cf_censito_altro_comune = 4;

	private int idListaConvocazione = -1;

	private String denominazione = "";
	private String nomeFile = "";
	private Timestamp dataInizio;
	private Timestamp dataFine;
	private int idAsl = -1;
	private int idComune = -1;
	private int idCircoscrizione = -1;
	private int numeroCaniEsclusione = -1;

	private Timestamp dataInserimento;
	private Timestamp dataModifica;
	private int idUtenteInserimento = -1;
	private int idUtenteModifica = -1;
	private int idStato = -1;
	private Thread importThread = null;

	private File fileImport = null;
	
	
	//DATI STATISTICI
	
	private int numeroTotali = 0;
	private int numeroConvocati = 0;
	private int numeroPresentati = 0;
	private int numeroEsclusiPerRegolarizzazioneSuccessiva = 0;
	private int numeroDaConvocare = 0;
	
	private double percentualeDiCompletamento = 0.0;
	
	BufferedWriter fos = null;

	private ArrayList<Convocato> convocazioni = new ArrayList<Convocato>();
	private ArrayList<ConvocazioneTemporale> convocazioniTemporali = new ArrayList<ConvocazioneTemporale>();
	
	private int idStatoConvocati = -1;

	public ListaConvocazione() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public ArrayList<Convocato> getConvocazioni() {
		return convocazioni;
	}



	public void setConvocazioni(ArrayList<Convocato> convocazioni) {
		this.convocazioni = convocazioni;
	}



	public ArrayList<ConvocazioneTemporale> getConvocazioniTemporali() {
		return convocazioniTemporali;
	}



	public int getIdStatoConvocati() {
		return idStatoConvocati;
	}



	public void setIdStatoConvocati(int idStatoConvocati) {
		this.idStatoConvocati = idStatoConvocati;
	}
	
	public void setIdStatoConvocati(String idStatoConvocati) {
		this.idStatoConvocati = Integer.parseInt(idStatoConvocati);
	}



	public void setConvocazioniTemporali(
			ArrayList<ConvocazioneTemporale> convocazioniTemporali) {
		this.convocazioniTemporali = convocazioniTemporali;
	}



	public int getNumeroCaniEsclusione() {
		return numeroCaniEsclusione;
	}



	public void setNumeroCaniEsclusione(int numeroCaniEsclusione) {
		this.numeroCaniEsclusione = numeroCaniEsclusione;
	}
	
	public void setNumeroCaniEsclusione(String numeroCaniEsclusione) {
		this.numeroCaniEsclusione = Integer.parseInt(numeroCaniEsclusione);
	}
	



	public File getFileImport() {
		return fileImport;
	}

	public void setFileImport(File fileImport) {
		this.fileImport = fileImport;
	}

	private Connection db = null;
	private Connection dbBdu = null;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = DatabaseUtils.parseDateToTimestamp(dataInizio);
		;
	}

	public Timestamp getDataFine() {
		return dataFine;
	}

	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}

	public void setDataFine(String dataFine) {
		this.dataFine = DatabaseUtils.parseDateToTimestamp(dataFine);
		;
	}

	public int getIdComune() {
		return idComune;
	}

	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}

	public void setIdComune(String idComune) {
		this.idComune = new Integer(idComune);
	}

	public int getIdCircoscrizione() {
		return idCircoscrizione;
	}

	public void setIdCircoscrizione(int idCircoscrizione) {
		this.idCircoscrizione = idCircoscrizione;
	}

	public void setIdCircoscrizione(String idCircoscrizione) {
		this.idCircoscrizione = new Integer(idCircoscrizione);
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

	public int getIdListaConvocazione() {
		return idListaConvocazione;
	}

	public void setIdListaConvocazione(int idListaConvocazione) {
		this.idListaConvocazione = idListaConvocazione;
	}

	public void setIdListaConvocazione(String idListaConvocazione) {
		this.idListaConvocazione = new Integer(idListaConvocazione);
	}
	
	
	

	public int getNumeroConvocati() {
		return numeroConvocati;
	}



	public void setNumeroConvocati(int numeroConvocati) {
		this.numeroConvocati = numeroConvocati;
	}



	public int getNumeroPresentati() {
		return numeroPresentati;
	}



	public void setNumeroPresentati(int numeroPresentati) {
		this.numeroPresentati = numeroPresentati;
	}



	public int getNumeroEsclusiPerRegolarizzazioneSuccessiva() {
		return numeroEsclusiPerRegolarizzazioneSuccessiva;
	}



	public void setNumeroEsclusiPerRegolarizzazioneSuccessiva(
			int numeroEsclusiPerRegolarizzazioneSuccessiva) {
		this.numeroEsclusiPerRegolarizzazioneSuccessiva = numeroEsclusiPerRegolarizzazioneSuccessiva;
	}



	public int getNumeroDaConvocare() {
		return numeroDaConvocare;
	}



	public void setNumeroDaConvocare(int numeroDaConvocare) {
		this.numeroDaConvocare = numeroDaConvocare;
	}

	
	


	public double getPercentualeDiCompletamento() {
		return percentualeDiCompletamento;
	}



	public void setPercentualeDiCompletamento(double percentualeDiCompletamento) {
		this.percentualeDiCompletamento = percentualeDiCompletamento;
	}

	
	


	public int getNumeroTotali() {
		return numeroTotali;
	}



	public void setNumeroTotali(int numeroTotali) {
		this.numeroTotali = numeroTotali;
	}



	public void start() {
		importThread = new Thread(this);
		importThread.start();
	}
	
	

	@Override
	public void run() {

		ArrayList thisRecord = new ArrayList();
		String line = null;
		StringBuffer error = new StringBuffer();
		int recordCount = 1;
		boolean recordInserted = false;
		boolean done = false;
		Thread currentThread = Thread.currentThread();
		fos = null;

		try {
			// get connection from the manager
			//db = GestoreConnessioni.getConnection();

			
			// create a temporary log file under the $FILELIBRARY/database
			File errorFile = new File(nomeFile + "_error");
			fos = new BufferedWriter(new FileWriter(errorFile));

			this.idStato = elaborazione_in_corso;


			CFSFileReader fileReader = new CFSFileReader(nomeFile, 8);
			fileReader.setColumnDelimiter(",");

			CFSFileReader.Record record = null; // fileReader.nextLine();

			recordError(null, "Dati soggetto", 0, fos);
			int first = 0;

			int i = 0;
			while (importThread == currentThread && !done) {
				if ((record = fileReader.nextLine()) != null) {
					first++;
					if (error.length() > 0) {
						error.delete(0, error.length());
					}
					recordInserted = false;

					++recordCount;

					try {

						if (first > 2 && !record.isEmpty()) {
							System.out.println(recordCount);
							// get the record
							thisRecord = record.data;

							// get the line and pad it if necessary for missing
							// columns
							line = fileReader.padLine(record.line,
									1 - thisRecord.size());
							
							//RECUPERO TUTTI I MICROCHIPS DA BDU
							
							String codice_fiscale  = thisRecord.get(0)
							.toString().replace("\"", "").trim();
						if (codice_fiscale != null && !("").equals(codice_fiscale)){
							
							dbBdu = GestoreConnessioni.getConnectionBdu();
							
							String contaCani = "select count(*) as numero_cani from (select * from public_functions.getmicrochipsbycf(?, ?)) lista where lista.esito = ?";
							PreparedStatement pst1 = dbBdu.prepareStatement(contaCani);
							pst1.setString(1, codice_fiscale);
							pst1.setInt(2, this.getIdComune());
							pst1.setInt(3, esito_convocabile);
							ResultSet rs1 = pst1.executeQuery();
							if (rs1.next()){
							if (numeroCaniEsclusione > 0 && rs1.getInt("numero_cani") > numeroCaniEsclusione){
								
								recordError("Soggetto con un numero cani maggiore di " + numeroCaniEsclusione +", escluso dalla lista ", line,
										recordCount, fos);
							}else{
							
							
							String select = "select * from public_functions.getmicrochipsbycf(?, ?)";
							PreparedStatement pst = dbBdu.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE,  ResultSet.CONCUR_READ_ONLY );
							
						

					

						
							pst.setString(1, codice_fiscale);
							pst.setInt(2, this.getIdComune());
							ResultSet rs = pst.executeQuery();
						//	rs.last();
							//int numeroCaniPosseduti = rs.getRow();
							//rs.beforeFirst();
							
//							if (numeroCaniPosseduti > 1){
//								recordError("Soggetto con un numero cani maggiore di 10, escluso dalla lista ", line,
//										recordCount, fos);
//							}else{
							
							while (rs.next()){
							i++;
							
							Convocato convocazione = new Convocato();
							convocazione.setIdListaConvocazione(this
									.getIdListaConvocazione());
							convocazione.setCodiceFiscale(thisRecord.get(0)
									.toString().replace("\"", "").trim());
							convocazione.setNome(thisRecord.get(1).toString()
									.replace("\"", "").trim());
							convocazione.setCognome(thisRecord.get(2)
									.toString().replace("\"", "").trim());
							convocazione.setNumeroCani(thisRecord.get(4)
									.toString().replace("\"", "").trim());
							convocazione.setDataNascita(thisRecord.get(5)
									.toString().replace("\"", "").trim());
							convocazione.setIndirizzo(thisRecord.get(6)
									.toString().replace("\"", "").trim());
							convocazione.setIdUtenteInserimento(this
									.getIdUtenteInserimento());
							convocazione.setIdUtenteModifica(this
									.getIdUtenteModifica());
							convocazione.setMicrochip(rs.getString("microchip_to_return"));
							convocazione.setIdComune(this.getIdComune());
							
							if (rs.getBoolean("flag_prelievo_dna_b")){
								convocazione.setIdStatoPresentazione(Convocato.presentato);
							}
							
							/**
							 * MODIFICO CONTROLLO SU COMUNE IN CUI E' CENSITO IN BDU COME STABILITO IN VERBALE
							 * 20/05/14: TALI SOGGETTI VENGONO CMQ INCLUSI IN LISTA E RESI DISPONIBILI PER LA
							 * CONVOCAZIONE E PER IL PRELIEVO DNA (CHE è LIBERALIZZATO ANCHE PER ANIMALI DI ASL
							 * DIVERSA). MANTENGO IL SOGGETTO NEL FILE DEGLI ERRORI
							 */
							if (rs.getInt("esito") == cf_censito_altro_comune){ //IL COMUNE IN BDU E' DIVERSO DA QUELLO DELLA LISTA DI CONVOCAZIONE
								recordError(" Soggetto presente in BDU ma censito in altro comune ", line,
										recordCount, fos);
							}  
							if (rs.getInt("esito") == esito_convocabile || rs.getInt("esito") == cf_censito_altro_comune){
								db = GestoreConnessioni.getConnection();//se il soggetto è convocabile (ANIMALE NON DECEDUTO, FURTO O SMARRITO)
									if(!convocazione.checkEsistenza(db)){
										//COntrollo esistenza coppia codice fiscale microchip
											
									if (error.length() == 0) {
										recordInserted = convocazione.insert(db);
										GestoreConnessioni.freeConnection(db);

										
									}
									else {
										recordError(error.toString(), line,
												recordCount, fos);
										GestoreConnessioni.freeConnection(db);
									}
									GestoreConnessioni.freeConnection(db);
								}else{//TENERE TRACCIA DI ESCLUSI XKè GIà PRESENTI??
									recordError(convocazione.getCodiceFiscale() + " Soggetto già presente in una lista di convocazione", line,
											recordCount, fos);
									GestoreConnessioni.freeConnection(db);
									}
							}		
							else
							{
								String motivo="";
								if (rs.getInt("esito")==esito_deceduto)
									motivo = "DECESSO";
								else if (rs.getInt("esito")==esito_furto_smarrito)
									motivo = "FURTO/SMARRIMENTO/TRASFERIMENTO";
								recordError(convocazione.getCodiceFiscale() + " Soggetto non convocabile poichè il microchip "+convocazione.getMicrochip()+
										" possiede attualmente una registrazione di "+motivo+" in BDU.", line,
										recordCount, fos);
							}
							
						}
							}
							}
							if (db != null)
								GestoreConnessioni.freeConnection(db);
							if (dbBdu != null)
								GestoreConnessioni.freeConnectionBdu(dbBdu);
						}else{
							recordError("Soggetto senza codice fiscale, impossibile proseguire ", line,
									recordCount, fos);
						}
						}

					} catch (Exception unknownException) {
						GestoreConnessioni.freeConnection(db);
						unknownException.printStackTrace();
						if (error.length() == 0) {
							recordError(unknownException.toString(), line,
									recordCount, fos);
						} else {
							recordError(error.toString() + "; "
									+ unknownException.toString(), line,
									recordCount, fos);
						}
					}
				} else {
					
					done = true;
				}
			}

			if (done) {
				
				if (i == 0)
					recordError( "Lista non popolata, possibilità di errore. Controllare i dati o contattare l'help desk", line,
						recordCount, fos);
				if (db == null || db.isClosed())
					db = GestoreConnessioni.getConnection();
				this.update(db);
				GestoreConnessioni.freeConnection(db);
				GestoreConnessioni.freeConnectionBdu(dbBdu);
				// this.setStatusId(Import.PROCESSED_UNAPPROVED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			recordError(e.toString(), "", -1, fos);
			try {
				// update status
				// this.setStatusId(FAILED);
			} catch (Exception statusException) {
				e.printStackTrace();
				// error updating status
			}
		} finally {
			if (importThread == currentThread) {
				GestoreConnessioni.freeConnection(db);
				
				stop();
			}
		}

	}

	public void stop() {
		// do not use stop() to stop the thread, nullify it
		importThread = null;

		// perform clean up
		destroy();
	}

	/**
	 * Description of the Method
	 */
	public void destroy() {
		// set status of the import and clean up thread pool
		if (System.getProperty("DEBUG") != null) {
			// logger.info("[CANINA] - ContactImport -> Starting cleanup for ImportId: "
			// + this.getId());
		}

		try {
			// update status, total imported/failed records
			// recordResults(db);

			// flush the log file
			fos.flush();
			fos.close();
			//
			// if (this.getTotalFailedRecords() > 0) {
			// // Store the error file as a version
			// fileItem.setSubject("Error file");
			// fileItem.setFilename(fileItem.getFilename() + "_error");
			// fileItem.setClientFilename(this.getId() + "_error.csv");
			// fileItem.setVersion(ERROR_FILE_VERSION);
			// fileItem.setSize((int) errorFile.length());
			// fileItem.insertVersion(db);
			// } else {
			// errorFile.delete();
			// }

			// report back to manager
			GestoreConnessioni.freeConnection(db);
			GestoreConnessioni.freeConnectionBdu(dbBdu);
		} catch (Exception e) {
			if (System.getProperty("DEBUG") != null) {
				e.printStackTrace();
			}
		} finally {
			// release the database connection
			GestoreConnessioni.freeConnection(db);
		}
	}

	private void recordError(String error, String line, int lineNumber,
			BufferedWriter fos) {
		try {

			if (lineNumber > 0)
				this.idStato = terminato_con_errori;
			// log errors in the temp file created under $FILELIBRARY/_imports/
			if (lineNumber == 0) {
				// header:append error column
				line += "," + "\"_Descrizione_Errore\"";
			} else if (lineNumber == -1) {
				// general error, mostly before import started
				line += error;
			} else if (lineNumber > 0) {
				// append the error
				line += ",\"" + error + "\"";

				// a record has failed, increment the failure count
				// this.incrementTotalFailedRecords();
			}

			// add next line character
			// TODO: Change this to a CUSTOM row delimiter if import type is
			// CUSTOM
			line += "\n";

			fos.write(line);
		} catch (IOException e) {
			// import should not fail because of logging error
		}
	}

	public boolean insert(Connection db) throws SQLException {

		boolean inserted = true;

		StringBuffer sql = new StringBuffer();

		try {
			
			ComuniAnagrafica comune = new ComuniAnagrafica(db, this.getIdComune());

			sql
					.append("INSERT INTO dati_convocazione ( denominazione, nome_file, data_inizio, data_fine, id_comune, id_circoscrizione, ");

			sql
					.append(" data_inserimento, data_modifica, utente_inserimento, utente_modifica, stato, id_asl, numero_cani_esclusione_proprietario ) " +
							"VALUES ( ? , ? , ? , ? , ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setString(++i, denominazione);
			pst.setString(++i, nomeFile);
			pst.setTimestamp(++i, dataInizio);
			pst.setTimestamp(++i, dataFine);
			pst.setInt(++i, idComune);
			pst.setInt(++i, idCircoscrizione);
			// pst.setTimestamp( ++i, dataInserimento );
			// pst.setTimestamp( ++i, dataModifica );
			pst.setInt(++i, idUtenteInserimento);
			pst.setInt(++i, idUtenteModifica);
			pst.setInt(++i, elaborazione_in_corso);
			pst.setInt(++i, comune.getIdAsl());
			pst.setInt(++i, numeroCaniEsclusione);

			pst.execute();

			this.idListaConvocazione = DatabaseUtils.getCurrVal(db,
					"dati_convocazione_id_seq", idListaConvocazione);
			this.idStato = elaborazione_in_corso;
			pst.close();

		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
		}

		return inserted;
	}

	public void update(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();

		try {

			sql.append("update dati_convocazione set stato = ? where id = ? ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			if (idStato == terminato_con_errori)
				pst.setInt(++i, idStato);
			else
				pst.setInt(++i, terminato_senza_errori);
			pst.setInt(++i, idListaConvocazione);

			pst.execute();

			pst.close();

		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
		}

	}

	public ListaConvocazione(int idListaConvocazione, Connection db)
			throws SQLException {

		if (idListaConvocazione == -1) {
			throw new SQLException("Invalid Account");
		}

		StringBuffer select = new StringBuffer();
		PreparedStatement pst = db
				.prepareStatement("Select * from dati_convocazione where id = ?");
		pst.setInt(1, idListaConvocazione);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			this.buildRecord(rs);

			this.buildConvocati(db);
			this.buildListaConvocazioniTemporali(db);

		}

		if (idListaConvocazione == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();

	}
	
	
	public void build(Connection db) throws SQLException{


		if (idListaConvocazione == -1) {
			throw new SQLException("Invalid Account");
		}

		StringBuffer select = new StringBuffer();
		PreparedStatement pst = db
				.prepareStatement("Select * from dati_convocazione where id = ?");
		pst.setInt(1, idListaConvocazione);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			this.buildRecord(rs);

			this.buildConvocati(db);
			this.buildListaConvocazioniTemporali(db);

		}

		if (idListaConvocazione == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();

	
	}

	private void buildConvocati(Connection db) throws SQLException {

		
		StringBuffer select = new StringBuffer();
		select.append("Select * from convocazioni where id_lista_convocazione = ? ");
		
		if (this.idStatoConvocati > 0){
			select.append("and id_stato_presentazione = ? ");
		}
		
		select.append("order by cognome ");
		
		PreparedStatement pst = db
				.prepareStatement(select.toString());
		pst.setInt(1, idListaConvocazione);
		
		if (this.idStatoConvocati > 0){
			pst.setInt(2, idStatoConvocati);
		}
		
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		while (rs.next()) {
			Convocato conv = new Convocato();


			conv.buildRecord(rs);
			
//			if (idStatoConvocati > 0){
//				if (conv.getIdStatoPresentazione() == Convocato.convocato_non_presentato)
//					numeroConvocati++;
//					
//
//				if (conv.getIdStatoPresentazione() == Convocato.convocato_ma_escluso_per_regolarizzazione)
//					numeroEsclusiPerRegolarizzazioneSuccessiva++;
//				
//				if (conv.getIdStatoPresentazione() == Convocato.presentato)
//					numeroPresentati++;
//				
//				if (conv.getIdStatoPresentazione() == Convocato.da_convocare)
//					numeroDaConvocare++;
//				
//				
//				
//				
//			}
			
			convocazioni.add(conv);
			
			

		}
		
		this.buildNumeriPercentuali(db);
		
	//	this.calcoloPercentualeCompletamento();

	}
	
	
	private void buildListaConvocazioniTemporali(Connection db) throws SQLException {

		PreparedStatement pst = db
				.prepareStatement("Select * from dati_convocazione_temporale where id_lista_convocazione = ?");
		pst.setInt(1, idListaConvocazione);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		while (rs.next()) {
			ConvocazioneTemporale tempConv = new ConvocazioneTemporale();

			tempConv.buildRecord(db, rs);
			
			convocazioniTemporali.add(tempConv);

		}

	}
	
	public ListaConvocazione(ResultSet rs) throws SQLException {
		this.buildRecord(rs);
	}

	private void buildRecord(ResultSet rs) throws SQLException {

		this.idListaConvocazione = rs.getInt("id");
		this.denominazione = rs.getString("denominazione");
		this.nomeFile = rs.getString("nome_file");
		this.dataInizio = rs.getTimestamp("data_inizio");
		this.dataFine = rs.getTimestamp("data_fine");
		this.idComune = rs.getInt("id_comune");
		this.idCircoscrizione = rs.getInt("id_circoscrizione");
		this.dataInserimento = rs.getTimestamp("data_inserimento");
		this.dataModifica = rs.getTimestamp("data_modifica");
		this.idUtenteInserimento = rs.getInt("utente_inserimento");
		this.idUtenteModifica = rs.getInt("utente_modifica");
		this.dataModifica = rs.getTimestamp("data_modifica");
		this.idStato = rs.getInt("stato");
		this.numeroCaniEsclusione = rs.getInt("numero_cani_esclusione_proprietario");
		
	}
	
public void aggiornaDaBDU() throws UnknownHostException, SQLException{
//	
//	String bdu_db_name = ApplicationProperties.getProperty("BDU_DBNAME");
//	String bdu_db_user = ApplicationProperties.getProperty("BDU_DBUSER");
//	String bdu_db_pwd = ApplicationProperties.getProperty("BDU_DBPWD");
//	String bdu_db_host = InetAddress.getByName("hostDbBdu").getHostAddress();
	Connection dbBdu = null;
	
	try {
//		dbBdu = DbUtil.getConnection(bdu_db_name, bdu_db_user, bdu_db_pwd, bdu_db_host);
		
		dbBdu = GestoreConnessioni.getConnectionBdu();
	
	  
    ArrayList<Convocato> convocazioniList = (ArrayList<Convocato>) this.getConvocazioni();
		Iterator itr = convocazioniList.iterator();
		if (itr.hasNext()) {
			int rowid = 0;
			int i = 0;
			boolean aggiorna = false;
			while (itr.hasNext()) {
				i++;
				rowid = (rowid != 1 ? 1 : 2);
				Convocato thisConvocato = (Convocato) itr.next();
			
				//aggiorna stato convocato
				String select = "select * from public_functions.dna_aggiorna_convocazione(? , ?)";
				PreparedStatement pst = dbBdu.prepareStatement(select);
				pst.setString(1, thisConvocato.getCodiceFiscale());
				pst.setString(2, thisConvocato.getMicrochip());
				ResultSet rs = pst.executeQuery();
				
				if (rs.next()){ //se trovo risultati, controlla
				
					if (rs.getBoolean("flag_prelievo_dna_b")){
						thisConvocato.setIdStatoPresentazione(Convocato.presentato);
						aggiorna = true;
						
					}
					else if (rs.getInt("esito")!=esito_convocabile){ //se il soggetto non è convocabile
						thisConvocato.setIdStatoPresentazione(Convocato.convocato_ma_escluso_per_regolarizzazione);
						aggiorna = true;
					}
					//convocazione.updateInformazioniConvocazione(dbBdu, idConvocazioneTemp)
				
					if (aggiorna){
						db = GestoreConnessioni.getConnection();
						thisConvocato.updateStatoConvocazione(db, this.idListaConvocazione);
						GestoreConnessioni.freeConnection(db);
					}
					aggiorna = false;
				}
				else { //altrimenti l'animale è stato cancellato. escludi per regolarizzazione
					db = GestoreConnessioni.getConnection();
					thisConvocato.setIdStatoPresentazione(Convocato.convocato_ma_escluso_per_regolarizzazione);
					thisConvocato.updateStatoConvocazione(db, this.idListaConvocazione);
					GestoreConnessioni.freeConnection(db);
				}
				
				}	
}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		GestoreConnessioni.freeConnectionBdu(dbBdu);
	}
}

	private void calcoloPercentualeCompletamento()
	{
		percentualeDiCompletamento = round(
				100.0 * this.numeroPresentati / (this.numeroTotali - this.numeroEsclusiPerRegolarizzazioneSuccessiva), 2);
	} 
	
	
	private void buildNumeriPercentuali(Connection db) throws SQLException{
		
	
		StringBuffer select = new StringBuffer();
		select.append("Select * from convocazioni where id_lista_convocazione = ? ");
		
		
		PreparedStatement pst = db
				.prepareStatement(select.toString());
		
		pst.setInt(1, idListaConvocazione);
		

		
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		
		while (rs.next()) {
			Convocato conv = new Convocato();
             numeroTotali++;

			conv.buildRecord(rs);
			
			
				if (conv.getIdStatoPresentazione() == Convocato.convocato_non_presentato)
					numeroConvocati++;
					

				if (conv.getIdStatoPresentazione() == Convocato.convocato_ma_escluso_per_regolarizzazione)
					numeroEsclusiPerRegolarizzazioneSuccessiva++;
				
				if (conv.getIdStatoPresentazione() == Convocato.presentato)
					numeroPresentati++;
				
				if (conv.getIdStatoPresentazione() == Convocato.da_convocare)
					numeroDaConvocare++;
				
			
			
	}
		calcoloPercentualeCompletamento();
	}
	
	public WritableWorkbook createXLS(WritableWorkbook w){
		
		try{
		
		WritableSheet sheet = w.createSheet("DA_CONVOCARE", 0);
		  
		
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 60);
		Label label = null;
		 
		int h = 0;
		int j = 0;
		
		label = new Label(h++, j, "CODICE_FISCALE");
		sheet.addCell(label);
		label = new Label(h++, j, "NOME");
		sheet.addCell(label);
		label = new Label(h++, j, "COGNOME");
		sheet.addCell(label);
		label = new Label(h++, j, "MICROCHIP");
		sheet.addCell(label);
		label = new Label(h++, j, "DATA_NASCITA");
		sheet.addCell(label);
		label = new Label(h++, j, "INDIRIZZO");
		sheet.addCell(label);
		
		 Convocato thisConvocato = new Convocato();
			
			for (int i = 0; i < convocazioni.size(); i++) {
				thisConvocato = (Convocato) convocazioni.get(i);
				j++;
				h=0;
				//if (thisConvocato.getIdStatoPresentazione() == Convocato.convocato_non_presentato){ HO SOLO I PRESENTATI			
				label = new Label(h++, j, thisConvocato.getCodiceFiscale());
				sheet.addCell(label);
				label = new Label(h++, j, thisConvocato.getNome());
				sheet.addCell(label);
				label = new Label(h++, j, thisConvocato.getCognome());
				sheet.addCell(label);
				label = new Label(h++, j, thisConvocato.getMicrochip());
				sheet.addCell(label);
				label = new Label(h++, j, thisConvocato.getDataNascita().toString());
				sheet.addCell(label);
				label = new Label(h++, j, thisConvocato.getIndirizzo());
				sheet.addCell(label);
				
				//}
			}
			
			
		//Scrivo effettivamente tutte le celle ed i dati aggiunti
		w.write();
		 
		//Chiudo il foglio excel
		w.close();
	
		
	}catch (Exception e) {
		// TODO: handle exception
	}
	return w;
	}
}
