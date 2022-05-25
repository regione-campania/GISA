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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import jxl.Cell;
import jxl.CellFormat;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;

import org.aspcfs.modules.anagrafe_animali_ext.base.Animale;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu_ext.base.Operatore;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;




import com.darkhorseventures.framework.beans.GenericBean;


public class ConvocazioneTemporale extends GenericBean{


	public static int in_corso = 1;
	public static int scaduta = 2;
	
	private int id = -1;

	private String denominazione = "";
	private Timestamp dataFine;


	private Timestamp dataInserimento;
	private Timestamp dataModifica;
	private int idUtenteInserimento = -1;
	private int idUtenteModifica = -1;
	private int idListaConvocazionePadre = -1;
	
	private boolean buildAppartenentiAConvocazione = true;
	
	private int idStato = in_corso;
	
	
	//DATI STATISTICI
	
	private int numeroTotali = 0;
	private int numeroConvocati = 0;
	private int numeroPresentati = 0;
	private int numeroEsclusiPerRegolarizzazioneSuccessiva = 0;
	private int numeroDaConvocare = 0;
	
	private double percentualeDiCompletamento = 0.0;




	private ArrayList<Convocato> convocazioni = new ArrayList<Convocato>();
	private int idStatoConvocati = -1;

	public int getIdStatoConvocati() {
		return idStatoConvocati;
	}



	public void setIdStatoConvocati(int idStatoConvocati) {
		this.idStatoConvocati = idStatoConvocati;
	}



	public ConvocazioneTemporale() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getIdListaConvocazionePadre() {
		return idListaConvocazionePadre;
	}



	public void setIdListaConvocazionePadre(int idListaConvocazionePadre) {
		this.idListaConvocazionePadre = idListaConvocazionePadre;
	}



	public int getIdStato() {
		return idStato;
	}



	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}



	public ArrayList<Convocato> getConvocazioni() {
		return convocazioni;
	}



	public void setConvocazioni(ArrayList<Convocato> convocazioni) {
		this.convocazioni = convocazioni;
	}



	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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




	public boolean isBuildAppartenentiAConvocazione() {
		return buildAppartenentiAConvocazione;
	}



	public void setBuildAppartenentiAConvocazione(
			boolean buildAppartenentiAConvocazione) {
		this.buildAppartenentiAConvocazione = buildAppartenentiAConvocazione;
	}


	

	public int getNumeroTotali() {
		return numeroTotali;
	}



	public void setNumeroTotali(int numeroTotali) {
		this.numeroTotali = numeroTotali;
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



	/**
	 * Description of the Method
	 */
	



	public boolean insert(Connection db) throws SQLException {

		boolean inserted = true;

		StringBuffer sql = new StringBuffer();

		try {

			sql
					.append("INSERT INTO dati_convocazione_temporale ( denominazione, data_fine, id_lista_convocazione, ");

			sql
					.append(" data_inserimento, data_modifica, utente_inserimento, utente_modifica, stato ) VALUES ( ? , ? , ? , " +
							"CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setString(++i, denominazione);

			pst.setTimestamp(++i, dataFine);
			pst.setInt(++i, idListaConvocazionePadre);
	
			// pst.setTimestamp( ++i, dataInserimento );
			// pst.setTimestamp( ++i, dataModifica );
			pst.setInt(++i, idUtenteInserimento);
			pst.setInt(++i, idUtenteModifica);
			pst.setInt(++i, idStato);


			pst.execute();
			
			this.id = DatabaseUtils.getCurrVal(db,
					"dati_convocazione_temporale_id_seq", id);
			
			//Aggiorno informazioni convocati
			Iterator iT = this.getConvocazioni().iterator();
			
			while (iT.hasNext()){
				Convocato conv = (Convocato) iT.next();
				//conv.setIdListaConvocazioneTemporale(id); non posso aggiornare qua perchè devo conservare prima lo stato
				conv.updateInformazioniConvocazione(db, id);
			}


			
			pst.close();

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}

		return inserted;
	}



	public ConvocazioneTemporale(int idListaConvocazione, Connection db)
			throws SQLException {

		if (idListaConvocazione == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select * from dati_convocazione_temporale where id = ?");
		pst.setInt(1, idListaConvocazione);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			this.buildRecord(rs);
			if (buildAppartenentiAConvocazione)
				this.buildConvocati(db);

		}

		if (idListaConvocazione == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();

	}

	private void buildConvocati(Connection db) throws SQLException {

		StringBuffer select = new StringBuffer();
		select.append("Select * from relazione_convocazione_convocati rel left join convocazioni conv " +
						"on (conv.id = rel.id_convocato) where rel.id_lista_convocazione_temporale = ? ");
		
		if (idStatoConvocati > 0)
			select.append(" and conv.id_stato_presentazione = ?" );
		PreparedStatement pst = db
				.prepareStatement(select.toString());
		pst.setInt(1, id);
		if (idStatoConvocati > 0)
			pst.setInt(2, idStatoConvocati);
		
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		while (rs.next()) {
			Convocato conv = new Convocato();

			conv.buildRecord(rs);
			
			convocazioni.add(conv);

		}
		buildNumeriPercentuali(db);
	}
	
	
	public void build(Connection db) throws SQLException{


		if (id == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select * from dati_convocazione_temporale where id = ?");
		pst.setInt(1, id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			this.buildRecord(rs);
			if (buildAppartenentiAConvocazione)
				this.buildConvocati(db);

		}

		if (id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();

	
	}
	
	public ConvocazioneTemporale(ResultSet rs) throws SQLException {
		this.buildRecord(rs);
	}

	public void buildRecord(ResultSet rs) throws SQLException {

		this.id = rs.getInt("id");
		this.denominazione = rs.getString("denominazione");
		this.dataFine = rs.getTimestamp("data_fine");
		this.dataInserimento = rs.getTimestamp("data_inserimento");
		this.dataModifica = rs.getTimestamp("data_modifica");
		this.idUtenteInserimento = rs.getInt("utente_inserimento");
		this.idUtenteModifica = rs.getInt("utente_modifica");
		this.dataModifica = rs.getTimestamp("data_modifica");
		this.idListaConvocazionePadre = rs.getInt("id_lista_convocazione");

		
	}
	
	
	public void buildRecord(Connection db, ResultSet rs) throws SQLException {

		buildRecord(rs);
		
		this.buildConvocati(db);

		
	}
	
	
	public StringBuffer createCSV(Connection db){
		
		String header=""; 
	     header+= "CODICE_FISCALE"+";";
		 header+= "NOME"+";";
		 header+= "COGNOME"+";";
		 header+= "MICROCHIP"+";";
		 header+= "DATA_NASCITA"+";";
		 header+= "INDIRIZZO"+";";
		
		 StringBuffer sb = new StringBuffer(); 
		 sb.append(header+"\n");
		 
		 Convocato thisConvocato = new Convocato();
		
			for (int i = 0;i < convocazioni.size(); i++) {
				thisConvocato = (Convocato) convocazioni.get(i);
				if (thisConvocato.getIdStatoPresentazione() == Convocato.convocato_non_presentato){//NON SI é ANCORA PRESENTATO			
			
					sb.append(""+thisConvocato.getCodiceFiscale()+";" );
					sb.append(""+thisConvocato.getNome()+";" );
					sb.append(""+thisConvocato.getCognome() +";" );
					sb.append(""+thisConvocato.getMicrochip() +";" );
					sb.append(""+thisConvocato.getDataNascita()+";" );
					sb.append(""+thisConvocato.getIndirizzo()+";" );
					sb.append("\n");
				}
			}

			
			return sb;
		 
		
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
	
	
	private void calcoloPercentualeCompletamento()
	{
		percentualeDiCompletamento = round(
				100.0 * this.numeroPresentati / (this.numeroTotali - this.numeroEsclusiPerRegolarizzazioneSuccessiva), 2);
	} 
	
	
	private void buildNumeriPercentuali(Connection db) throws SQLException{
		
	
		StringBuffer select = new StringBuffer();
		select.append("Select * from relazione_convocazione_convocati rel left join convocazioni conv " +
						"on (conv.id = rel.id_convocato) where rel.id_lista_convocazione_temporale = ? ");

		
		PreparedStatement pst = db
				.prepareStatement(select.toString());
		
		pst.setInt(1, id);

		
		
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		while (rs.next()) {
			Convocato conv = new Convocato();

			conv.buildRecord(rs);
			numeroTotali++;

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
}
