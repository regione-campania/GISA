/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneml.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.aspcfs.modules.gestioneml.actions.GestioneMasterList;

public class MasterListImportRiga {

private String norma;
private String codiceNorma;
private String codiceSezione;
private String macroarea;
private String codiceAttivita;
private String aggregazione;
private String codiceProdottoSpecie;
private String lineaAttivita;
private String codiceUnivoco;
private String attivitaSpecifica;
private String caratterizzazioneSpecifica;
private String dettaglioSpecializzazioneProduttiva;
private String noteInformative;
private String documentiDaAllegare;
private String chiInseriscePratica;
private String chiValida;
private String codiceNazionaleRichiesto;
private String schedaSupplementare;
private String tipo;
private String note;
private String mappingAteco;
private String ateco1;
private String ateco2;
private String ateco3;
private String ateco4;
private String ateco5;
private String ateco6;
private String ateco7;
private String ateco8;
private String ateco9;
private String ateco10;
private String ateco11;
private String ateco12;
private String ateco13;
private String ateco14;
private String ateco15;
private String ateco16;
private String ateco17;
private String ateco18;


private int idMacroarea;
private int idAggregazione;
private int idLineaAttivita;

private int idFlussoOriginale;
private String errore = "";

public String getNorma() {
	return norma;
}
public void setNorma(String norma) {
	this.norma = norma;
}
public String getCodiceNorma() {
	return codiceNorma;
}
public void setCodiceNorma(String codiceNorma) {
	this.codiceNorma = codiceNorma;
}
public String getCodiceSezione() {
	return codiceSezione;
}
public void setCodiceSezione(String codiceSezione) {
	this.codiceSezione = codiceSezione;
}
public String getMacroarea() {
	return macroarea;
}
public void setMacroarea(String macroarea) {
	this.macroarea = macroarea;
}

public String getCodiceAttivita() {
	return codiceAttivita;
}
public void setCodiceAttivita(String codiceAttivita) {
	this.codiceAttivita = codiceAttivita;
}
public String getAggregazione() {
	return aggregazione;
}
public void setAggregazione(String aggregazione) {
	this.aggregazione = aggregazione;
}
public String getCodiceProdottoSpecie() {
	return codiceProdottoSpecie;
}
public void setCodiceProdottoSpecie(String codiceProdottoSpecie) {
	this.codiceProdottoSpecie = codiceProdottoSpecie;
}
public String getLineaAttivita() {
	return lineaAttivita;
}
public void setLineaAttivita(String lineaAttivita) {
	this.lineaAttivita = lineaAttivita;
}
public String getCodiceUnivoco() {
	return codiceUnivoco;
}
public void setCodiceUnivoco(String codiceUnivoco) {
	this.codiceUnivoco = codiceUnivoco;
}
public String getAttivitaSpecifica() {
	return attivitaSpecifica;
}
public void setAttivitaSpecifica(String attivitaSpecifica) {
	this.attivitaSpecifica = attivitaSpecifica;
}
public String getCaratterizzazioneSpecifica() {
	return caratterizzazioneSpecifica;
}
public void setCaratterizzazioneSpecifica(String caratterizzazioneSpecifica) {
	this.caratterizzazioneSpecifica = caratterizzazioneSpecifica;
}
public String getDettaglioSpecializzazioneProduttiva() {
	return dettaglioSpecializzazioneProduttiva;
}
public void setDettaglioSpecializzazioneProduttiva(String dettaglioSpecializzazioneProduttiva) {
	this.dettaglioSpecializzazioneProduttiva = dettaglioSpecializzazioneProduttiva;
}
public String getNoteInformative() {
	return noteInformative;
}
public void setNoteInformative(String noteInformative) {
	this.noteInformative = noteInformative;
}
public String getDocumentiDaAllegare() {
	return documentiDaAllegare;
}
public void setDocumentiDaAllegare(String documentiDaAllegare) {
	this.documentiDaAllegare = documentiDaAllegare;
}
public String getChiInseriscePratica() {
	return chiInseriscePratica;
}
public void setChiInseriscePratica(String chiInseriscePratica) {
	this.chiInseriscePratica = chiInseriscePratica;
}
public String getChiValida() {
	return chiValida;
}
public void setChiValida(String chiValida) {
	this.chiValida = chiValida;
}
public String getCodiceNazionaleRichiesto() {
	return codiceNazionaleRichiesto;
}
public void setCodiceNazionaleRichiesto(String codiceNazionaleRichiesto) {
	this.codiceNazionaleRichiesto = codiceNazionaleRichiesto;
}
public String getSchedaSupplementare() {
	return schedaSupplementare;
}
public void setSchedaSupplementare(String schedaSupplementare) {
	this.schedaSupplementare = schedaSupplementare;
}
public String getTipo() {
	return tipo;
}
public void setTipo(String tipo) {
	this.tipo = tipo;
}
public String getNote() {
	return note;
}
public void setNote(String note) {
	this.note = note;
}
public String getMappingAteco() {
	return mappingAteco;
}
public void setMappingAteco(String mappingAteco) {
	this.mappingAteco = mappingAteco;
}
public String getAteco1() {
	return ateco1;
}
public void setAteco1(String ateco1) {
	this.ateco1 = ateco1;
}
public String getAteco2() {
	return ateco2;
}
public void setAteco2(String ateco2) {
	this.ateco2 = ateco2;
}
public String getAteco3() {
	return ateco3;
}
public void setAteco3(String ateco3) {
	this.ateco3 = ateco3;
}
public String getAteco4() {
	return ateco4;
}
public void setAteco4(String ateco4) {
	this.ateco4 = ateco4;
}
public String getAteco5() {
	return ateco5;
}
public void setAteco5(String ateco5) {
	this.ateco5 = ateco5;
}
public String getAteco6() {
	return ateco6;
}
public void setAteco6(String ateco6) {
	this.ateco6 = ateco6;
}
public String getAteco7() {
	return ateco7;
}
public void setAteco7(String ateco7) {
	this.ateco7 = ateco7;
}
public String getAteco8() {
	return ateco8;
}
public void setAteco8(String ateco8) {
	this.ateco8 = ateco8;
}
public String getAteco9() {
	return ateco9;
}
public void setAteco9(String ateco9) {
	this.ateco9 = ateco9;
}
public String getAteco10() {
	return ateco10;
}
public void setAteco10(String ateco10) {
	this.ateco10 = ateco10;
}
public String getAteco11() {
	return ateco11;
}
public void setAteco11(String ateco11) {
	this.ateco11 = ateco11;
}
public String getAteco12() {
	return ateco12;
}
public void setAteco12(String ateco12) {
	this.ateco12 = ateco12;
}
public String getAteco13() {
	return ateco13;
}
public void setAteco13(String ateco13) {
	this.ateco13 = ateco13;
}
public String getAteco14() {
	return ateco14;
}
public void setAteco14(String ateco14) {
	this.ateco14 = ateco14;
}
public String getAteco15() {
	return ateco15;
}
public void setAteco15(String ateco15) {
	this.ateco15 = ateco15;
}
public String getAteco16() {
	return ateco16;
}
public void setAteco16(String ateco16) {
	this.ateco16 = ateco16;
}
public String getAteco17() {
	return ateco17;
}
public void setAteco17(String ateco17) {
	this.ateco17 = ateco17;
}
public String getAteco18() {
	return ateco18;
}
public void setAteco18(String ateco18) {
	this.ateco18 = ateco18;
}

public int getIdMacroarea() {
	return idMacroarea;
}
public void setIdMacroarea(int idMacroarea) {
	this.idMacroarea = idMacroarea;
}
public int getIdAggregazione() {
	return idAggregazione;
}
public void setIdAggregazione(int idAggregazione) {
	this.idAggregazione = idAggregazione;
}
public int getIdLineaAttivita() {
	return idLineaAttivita;
}
public void setIdLineaAttivita(int idLineaAttivita) {
	this.idLineaAttivita = idLineaAttivita;
}
public MasterListImportRiga(int indiceFoglio, Row nextRow) {
	   Iterator<Cell> cellIterator = nextRow.cellIterator();
         int indiceCella = 0;
         while (cellIterator.hasNext()) {
             Cell cell = cellIterator.next();
             String valore = "";
             
             if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) 
                  try {valore = String.valueOf(cell.getNumericCellValue());} catch (Exception e) {}
             else
                  try {valore = cell.getStringCellValue().trim().toUpperCase(); } catch (Exception e) {}
             
             if (indiceFoglio==MasterListImportUtilScia.INDICE_FOGLIO){
	             switch (indiceCella){
	             		case MasterListImportUtilScia.COL_NORMA : setNorma(valore); break;
	             		case MasterListImportUtilScia.COL_CODICENORMA : setCodiceNorma(valore); break;
	             		case MasterListImportUtilScia.COL_CODICESEZIONE : setCodiceSezione(valore); break;
	             		case MasterListImportUtilScia.COL_MACROAREE : setMacroarea(valore); break;
	             		case MasterListImportUtilScia.COL_CODICEATTIVITA : setCodiceAttivita(valore); break;
	             		case MasterListImportUtilScia.COL_AGGREGAZIONI : setAggregazione(valore); break;
	             		case MasterListImportUtilScia.COL_CODICEPRODOTTOSPECIE : setCodiceProdottoSpecie(valore); break;
	             		case MasterListImportUtilScia.COL_LINEAATTIVITA : setLineaAttivita(valore); break;
	             		case MasterListImportUtilScia.COL_CODICEUNIVOCO : setCodiceUnivoco(valore); break;
	             		case MasterListImportUtilScia.COL_ATTIVITASPECIFICA : setAttivitaSpecifica(valore); break;
	             		case MasterListImportUtilScia.COL_CARATTERIZZAZIONESPECIFICA : setCaratterizzazioneSpecifica(valore); break;
	             		case MasterListImportUtilScia.COL_DETTAGLIOSPECIALIZZAZIONEPRODUTTIVA : setDettaglioSpecializzazioneProduttiva(valore); break;
	             		case MasterListImportUtilScia.COL_NOTEINFORMATIVE : setNoteInformative(valore);break;
//	             		case MasterListImportUtilScia.COL_DOCUMENTIDALLEGARE : setDocumentiDaAllegare(valore); break;
	             		case MasterListImportUtilScia.COL_CHIINSERISCELAPRATICA : setChiInseriscePratica(valore); break;
	             		case MasterListImportUtilScia.COL_CODICENAZIONALERICHIESTO: setCodiceNazionaleRichiesto(valore); break;
	             		case MasterListImportUtilScia.COL_SCHEDASUPPLEMENTARE : setSchedaSupplementare(valore); break;
	             		case MasterListImportUtilScia.COL_TIPO : setTipo(valore); break;
	             		case MasterListImportUtilScia.COL_NOTE : setNote(valore); break;
//	             		case MasterListImportUtilScia.COL_MAPPINGATECO : setMappingAteco(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO1 : setAteco1(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO2 : setAteco2(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO3 : setAteco3(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO4 : setAteco4(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO5 : setAteco5(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO6 : setAteco6(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO7 : setAteco7(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO8 : setAteco8(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO9 : setAteco9(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO10 : setAteco10(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO11 : setAteco11(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO12 : setAteco12(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO13 : setAteco13(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO14 : setAteco14(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO15 : setAteco15(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO16 : setAteco16(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO17 : setAteco17(valore); break;
//	             		case MasterListImportUtilScia.COL_ATECO18 : setAteco18(valore); break;
	              }  
	             }
             else if (indiceFoglio==MasterListImportUtilAllevamenti.INDICE_FOGLIO){
	             switch (indiceCella){
	             		case MasterListImportUtilAllevamenti.COL_NORMA : setNorma(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_CODICENORMA : setCodiceNorma(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_CODICESEZIONE : setCodiceSezione(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_MACROAREE : setMacroarea(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_CODICEATTIVITA : setCodiceAttivita(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_AGGREGAZIONI : setAggregazione(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_CODICEPRODOTTOSPECIE : setCodiceProdottoSpecie(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_LINEAATTIVITA : setLineaAttivita(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_CODICEUNIVOCO : setCodiceUnivoco(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATTIVITASPECIFICA : setAttivitaSpecifica(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_CARATTERIZZAZIONESPECIFICA : setCaratterizzazioneSpecifica(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_DETTAGLIOSPECIALIZZAZIONEPRODUTTIVA : setDettaglioSpecializzazioneProduttiva(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_NOTEINFORMATIVE : setNoteInformative(valore);break;
	             		case MasterListImportUtilAllevamenti.COL_DOCUMENTIDALLEGARE : setDocumentiDaAllegare(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_CHIINSERISCELAPRATICA : setChiInseriscePratica(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_CHIVALIDA : setChiValida(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_CODICENAZIONALERICHIESTO: setCodiceNazionaleRichiesto(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_SCHEDASUPPLEMENTARE : setSchedaSupplementare(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_TIPO : setTipo(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_NOTE : setNote(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_MAPPINGATECO : setMappingAteco(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO1 : setAteco1(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO2 : setAteco2(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO3 : setAteco3(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO4 : setAteco4(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO5 : setAteco5(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO6 : setAteco6(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO7 : setAteco7(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO8 : setAteco8(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO9 : setAteco9(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO10 : setAteco10(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO11 : setAteco11(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO12 : setAteco12(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO13 : setAteco13(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO14 : setAteco14(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO15 : setAteco15(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO16 : setAteco16(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO17 : setAteco17(valore); break;
	             		case MasterListImportUtilAllevamenti.COL_ATECO18 : setAteco18(valore); break;
	              }  
	             }
             if (indiceFoglio==MasterListImportUtilNoScia.INDICE_FOGLIO){
	             switch (indiceCella){
	             		case MasterListImportUtilNoScia.COL_NORMA : setNorma(valore); break;
	             		case MasterListImportUtilNoScia.COL_CODICENORMA : setCodiceNorma(valore); break;
	             		case MasterListImportUtilNoScia.COL_CODICESEZIONE : setCodiceSezione(valore); break;
	             		case MasterListImportUtilNoScia.COL_MACROAREE : setMacroarea(valore); break;
	             		case MasterListImportUtilNoScia.COL_CODICEATTIVITA : setCodiceAttivita(valore); break;
	             		case MasterListImportUtilNoScia.COL_AGGREGAZIONI : setAggregazione(valore); break;
	             		case MasterListImportUtilNoScia.COL_CODICEPRODOTTOSPECIE : setCodiceProdottoSpecie(valore); break;
	             		case MasterListImportUtilNoScia.COL_LINEAATTIVITA : setLineaAttivita(valore); break;
	             		case MasterListImportUtilNoScia.COL_CODICEUNIVOCO : setCodiceUnivoco(valore); break;
	             		case MasterListImportUtilNoScia.COL_ATTIVITASPECIFICA : setAttivitaSpecifica(valore); break;
	             		case MasterListImportUtilNoScia.COL_CARATTERIZZAZIONESPECIFICA : setCaratterizzazioneSpecifica(valore); break;
	             		case MasterListImportUtilNoScia.COL_DETTAGLIOSPECIALIZZAZIONEPRODUTTIVA : setDettaglioSpecializzazioneProduttiva(valore); break;
	             		case MasterListImportUtilNoScia.COL_NOTE : setNote(valore); break;
	             		case MasterListImportUtilNoScia.COL_MAPPINGATECO : setMappingAteco(valore); break;
	             	 }  
	             }
             
             indiceCella++;
       }
       
     }

public void insertMacroarea(Connection db) throws SQLException{
	int idInsertMacroarea = -1;
	
	String sqlSelect = "select id from "+MasterListImportUtil.TAB_MACROAREA+ " where codice_sezione ilike ? and macroarea ilike ?";
	PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
	pstSelect.setString(1, codiceSezione);
	pstSelect.setString(2, macroarea);
	ResultSet rsSelect = pstSelect.executeQuery();
	if (rsSelect.next())
		idInsertMacroarea = rsSelect.getInt("id");
	
	if (idInsertMacroarea == -1){
		String sqlInsert = "insert into " +MasterListImportUtil.TAB_MACROAREA+ " (codice_sezione, codice_norma, norma, macroarea)  values (?, ?, ?, ?) returning id as id_inserito";
		PreparedStatement pstInsert = db.prepareStatement(sqlInsert);
		int i = 0;
		pstInsert.setString(++i, codiceSezione);
		pstInsert.setString(++i, codiceNorma);
		pstInsert.setString(++i, norma);
		pstInsert.setString(++i, macroarea);
		
		ResultSet rsInsert = pstInsert.executeQuery();
		if (rsInsert.next())
			idInsertMacroarea = rsInsert.getInt("id_inserito");
	}
		idMacroarea = idInsertMacroarea;
}

public void insertAggregazione(Connection db) throws SQLException{
	int idInsertAggregazione = -1;
	
	String sqlSelect = "select id from "+MasterListImportUtil.TAB_AGGREGAZIONE+ " where codice_attivita ilike ? and aggregazione ilike ? and id_flusso_originale = ?";
	PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
	pstSelect.setString(1, codiceAttivita);
	pstSelect.setString(2, aggregazione);
	pstSelect.setInt(3, idFlussoOriginale);
	ResultSet rsSelect = pstSelect.executeQuery();
	if (rsSelect.next())
		idInsertAggregazione = rsSelect.getInt("id");
	
	if (idInsertAggregazione == -1){
		if (codiceAttivita==null)
			System.out.println("aaa");
		String sqlInsert = "insert into " +MasterListImportUtil.TAB_AGGREGAZIONE+ " (codice_attivita, aggregazione, id_macroarea, id_flusso_originale)  values (?, ?, ?, ?) returning id as id_inserito";
		PreparedStatement pstInsert = db.prepareStatement(sqlInsert);
		int i = 0;
		pstInsert.setString(++i, codiceAttivita);
		pstInsert.setString(++i, aggregazione);
		pstInsert.setInt(++i, idMacroarea);
		pstInsert.setInt(++i, idFlussoOriginale);

	
		ResultSet rsInsert = pstInsert.executeQuery();
		if (rsInsert.next())
			idInsertAggregazione = rsInsert.getInt("id_inserito");
	}
		idAggregazione = idInsertAggregazione;
}

public void insertLineaAttivita(Connection db) throws SQLException{
	int idInsertLineaAttivita = -1;
	
	//String sqlSelect = "select id from "+MasterListImportUtil.TAB_LINEA_ATTIVITA+ " where codice_prodotto_specie ilike ?";
	String sqlSelect = "select id from "+MasterListImportUtil.TAB_LINEA_ATTIVITA+ " where codice_univoco ilike ?";
	PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
	//pstSelect.setString(1, codiceProdottoSpecie);
	pstSelect.setString(1, codiceUnivoco);
	ResultSet rsSelect = pstSelect.executeQuery();
	if (rsSelect.next())
		idInsertLineaAttivita = rsSelect.getInt("id");
	
	if (idInsertLineaAttivita == -1){
		String sqlInsert = "insert into " +MasterListImportUtil.TAB_LINEA_ATTIVITA+ " (codice_prodotto_specie, linea_attivita, tipo, scheda_supplementare, note, "
				+ "mapping_ateco,   codice_univoco, codice_nazionale_richiesto, chi_inserisce_pratica, chi_valida, "
				+ "ateco1, ateco2, ateco3, ateco4, ateco5, ateco6, ateco7, ateco8, ateco9, ateco10, ateco11, ateco12, ateco13, ateco14, ateco15, ateco16, ateco17, ateco18, "
				+ "id_aggregazione) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,  ?, ?, ?, ?, ?) returning id as id_inserito";
		PreparedStatement pstInsert = db.prepareStatement(sqlInsert);
		int i = 0;
		pstInsert.setString(++i, codiceProdottoSpecie);
		pstInsert.setString(++i, lineaAttivita);
		pstInsert.setString(++i, tipo);
		pstInsert.setString(++i, schedaSupplementare);
		pstInsert.setString(++i, note);
		pstInsert.setString(++i, mappingAteco);
		pstInsert.setString(++i, codiceUnivoco);
		pstInsert.setString(++i, codiceNazionaleRichiesto);
		pstInsert.setString(++i, chiInseriscePratica);
		pstInsert.setString(++i, chiValida);
		pstInsert.setString(++i, ateco1);
		pstInsert.setString(++i, ateco2);
		pstInsert.setString(++i, ateco3);
		pstInsert.setString(++i, ateco4);
		pstInsert.setString(++i, ateco5);
		pstInsert.setString(++i, ateco6);
		pstInsert.setString(++i, ateco7);
		pstInsert.setString(++i, ateco8);
		pstInsert.setString(++i, ateco9);
		pstInsert.setString(++i, ateco10);
		pstInsert.setString(++i, ateco11);
		pstInsert.setString(++i, ateco12);
		pstInsert.setString(++i, ateco13);
		pstInsert.setString(++i, ateco14);
		pstInsert.setString(++i, ateco15);
		pstInsert.setString(++i, ateco16);
		pstInsert.setString(++i, ateco17);
		pstInsert.setString(++i, ateco18);
		pstInsert.setInt(++i, idAggregazione);

		
		ResultSet rsInsert = pstInsert.executeQuery();
		if (rsInsert.next())
			idInsertLineaAttivita = rsInsert.getInt("id_inserito");
	}
	idLineaAttivita = idInsertLineaAttivita;
}

public void insertAllegati(Connection db) throws SQLException{
	if (documentiDaAllegare!=null){
	String allegatiSplittati[] = documentiDaAllegare.split(",");
	ArrayList<Integer> idAllegati = new ArrayList<Integer>();
	
	ArrayList<String> codiciAllegatiNonPresenti = new ArrayList<String>();
	
	for(String codiceGruppo : allegatiSplittati)
	{
		codiceGruppo = codiceGruppo.trim();
		if(codiceGruppo.equals(""))
			continue;
		
		PreparedStatement pst = db.prepareStatement("select id from " + MasterListImportUtil.TAB_ALLEGATI_PROCEDURE + " where codice_gruppo ilike ? ");
		pst.setString(1,codiceGruppo);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
		{
			idAllegati.add(rs.getInt(1));
		}
		else
		{
			System.out.println("$$$ Codice gruppo allegati non trovato: "+codiceGruppo+ " $$$");
			if (!codiciAllegatiNonPresenti.contains(codiceGruppo))
				codiciAllegatiNonPresenti.add(codiceGruppo);
		}
		
		if (codiciAllegatiNonPresenti.size()>0)
			setErrore("\\n Codice gruppo allegati non trovato: "+codiciAllegatiNonPresenti.toString());
	}
	
	for(Integer idAllegato : idAllegati)
	{
	
		int idInsertAllegato = -1;
		//CONTROLLO SE PER QUELLA LINEA E' GIA' STATO INSERITO
		String sqlSelect = "select id from "+MasterListImportUtil.TAB_ALLEGATI_PROCEDURE_RELAZIONE+ " where id_master_list_linea_attivita = ? and id_master_list_suap_gruppo_allegati = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setInt(1, idLineaAttivita);
		pstSelect.setInt(2, idAllegato.intValue());
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next())
			idInsertAllegato = rsSelect.getInt("id");
		
		if (idInsertAllegato == -1){	
			PreparedStatement pst = db.prepareStatement("insert into " + MasterListImportUtil.TAB_ALLEGATI_PROCEDURE_RELAZIONE + "(id_master_list_linea_attivita,id_master_list_suap_gruppo_allegati) values(?,?)");
			pst.setInt(1, idLineaAttivita);
			pst.setInt(2, idAllegato.intValue());
			pst.executeUpdate();
			}
	}
	}
}

public void insertLivelliAggiuntivi(Connection db) throws SQLException{

	int idLivelloAggiuntivo = -1;
	
	String sqlInsert = "";
	PreparedStatement pstInsert = null;
	String sqlInsertValore = "";
	PreparedStatement pstInsertValore = null;
	ResultSet rsInsert = null;
	int i = 0;
	
	if (attivitaSpecifica!=null && !attivitaSpecifica.equals("")){
		
		sqlInsert = "insert into " +MasterListImportUtil.TAB_LIVELLI_AGGIUNTIVI+ " (id_linea_attivita, id_padre, nome)  values (?, ?, ?) returning id as id_inserito";
		pstInsert = db.prepareStatement(sqlInsert);
		i = 0;
		pstInsert.setInt(++i, idLineaAttivita);
		pstInsert.setInt(++i, -1);
		pstInsert.setString(++i, "Attivita' Specifica");
		
		rsInsert = pstInsert.executeQuery();
		if (rsInsert.next())
			idLivelloAggiuntivo = rsInsert.getInt("id_inserito");
		
		sqlInsertValore = "insert into " +MasterListImportUtil.TAB_LIVELLI_AGGIUNTIVI_VALORI+ " (id_livello_aggiuntivo, valore)  values (?, ?)";
		pstInsertValore = db.prepareStatement(sqlInsertValore);
		i = 0;
		pstInsertValore.setInt(++i, idLivelloAggiuntivo);
		pstInsertValore.setString(++i, attivitaSpecifica);
		pstInsertValore.executeUpdate();
		
		if (caratterizzazioneSpecifica!=null && !caratterizzazioneSpecifica.equals("")){
			
			sqlInsert = "insert into " +MasterListImportUtil.TAB_LIVELLI_AGGIUNTIVI+ " (id_linea_attivita, id_padre, nome)  values (?, ?, ?) returning id as id_inserito";
			pstInsert = db.prepareStatement(sqlInsert);
			i = 0;
			pstInsert.setInt(++i, idLineaAttivita);
			pstInsert.setInt(++i, idLivelloAggiuntivo);
			pstInsert.setString(++i, "Caratterizzazione Specifica");
			
			rsInsert = pstInsert.executeQuery();
			if (rsInsert.next())
				idLivelloAggiuntivo = rsInsert.getInt("id_inserito");
			
			sqlInsertValore = "insert into " +MasterListImportUtil.TAB_LIVELLI_AGGIUNTIVI_VALORI+ " (id_livello_aggiuntivo, valore)  values (?, ?)";
			pstInsertValore = db.prepareStatement(sqlInsertValore);
			i = 0;
			pstInsertValore.setInt(++i, idLivelloAggiuntivo);
			pstInsertValore.setString(++i, caratterizzazioneSpecifica);
			pstInsertValore.executeUpdate();
			
			if (dettaglioSpecializzazioneProduttiva!=null && !dettaglioSpecializzazioneProduttiva.equals(""))  {
				
				sqlInsert = "insert into " +MasterListImportUtil.TAB_LIVELLI_AGGIUNTIVI+ " (id_linea_attivita, id_padre, nome)  values (?, ?, ?) returning id as id_inserito";
				pstInsert = db.prepareStatement(sqlInsert);
				i = 0;
				pstInsert.setInt(++i, idLineaAttivita);
				pstInsert.setInt(++i, idLivelloAggiuntivo);
				pstInsert.setString(++i, "Dettaglio Spcializzazione Produttiva");
				
				rsInsert = pstInsert.executeQuery();
				if (rsInsert.next())
					idLivelloAggiuntivo = rsInsert.getInt("id_inserito");	
				
				sqlInsertValore = "insert into " +MasterListImportUtil.TAB_LIVELLI_AGGIUNTIVI_VALORI+ " (id_livello_aggiuntivo, valore)  values (?, ?)";
				pstInsertValore = db.prepareStatement(sqlInsertValore);
				i = 0;
				pstInsertValore.setInt(++i, idLivelloAggiuntivo);
				pstInsertValore.setString(++i, dettaglioSpecializzazioneProduttiva);
				pstInsertValore.executeUpdate();
			}
		}
		
	}
}


public String getErrore() {
	return errore;
}
public void setErrore(String errore) {
	this.errore = this.errore + "; "+errore;
}
public int getIdFlussoOriginale() {
	return idFlussoOriginale;
}
public void setIdFlussoOriginale(int idFlussoOriginale) {
	this.idFlussoOriginale = idFlussoOriginale;
}
public void setIdFlussoOriginale() {

	int flusso = MasterListImportUtil.FLUSSO_REGISTRATI;
	
	if (("regione").equalsIgnoreCase(chiInseriscePratica) && ("sintesi").equalsIgnoreCase(codiceNazionaleRichiesto) && ("riconoscibili").equalsIgnoreCase(tipo)){
		flusso = MasterListImportUtil.FLUSSO_SINTESIS_RICONOSCIUTI;
	}
	
	else if (("regione").equalsIgnoreCase(chiInseriscePratica) && ("cun").equalsIgnoreCase(codiceNazionaleRichiesto) && ("riconoscibili").equalsIgnoreCase(tipo)){
		flusso = MasterListImportUtil.FLUSSO_RICONOSCIUTI;
	}
	
	else if (("asl/suap").equalsIgnoreCase(chiInseriscePratica) && ("riconoscibili").equalsIgnoreCase(tipo)){
		flusso = MasterListImportUtil.FLUSSO_RICONOSCIUTI;
	}
	else if (("asl/suap").equalsIgnoreCase(chiInseriscePratica) && ("sintesis-soa").equalsIgnoreCase(codiceNazionaleRichiesto) && ("senza sede fissa").equalsIgnoreCase(tipo)){
		flusso = MasterListImportUtil.FLUSSO_SINTESIS_REGISTRATI;
	}
	else if (("asl/suap").equalsIgnoreCase(chiInseriscePratica) && ("sintesis-soa + codice rilasciato da asl").equalsIgnoreCase(codiceNazionaleRichiesto) && ("senza sede fissa").equalsIgnoreCase(tipo)){
		flusso = MasterListImportUtil.FLUSSO_SINTESIS_REGISTRATI;
	}
	
	else if (("asl/suap").equalsIgnoreCase(chiInseriscePratica) && ("").equalsIgnoreCase(codiceNazionaleRichiesto) && ("senza sede fissa").equalsIgnoreCase(tipo)){
		flusso = MasterListImportUtil.FLUSSO_REGISTRATI;
	}
	else if (("asl/suap").equalsIgnoreCase(chiInseriscePratica) && ("").equalsIgnoreCase(codiceNazionaleRichiesto) && ("con sede fissa").equalsIgnoreCase(tipo)){
		flusso = MasterListImportUtil.FLUSSO_REGISTRATI;
	}
	
	else if (("asl/suap").equalsIgnoreCase(chiInseriscePratica) && ("api").equalsIgnoreCase(codiceNazionaleRichiesto) && ("con sede fissa").equalsIgnoreCase(tipo)){
		flusso = MasterListImportUtil.FLUSSO_APICOLTURA;
	}
	else if (("asl/suap").equalsIgnoreCase(chiInseriscePratica) && ("api").equalsIgnoreCase(codiceNazionaleRichiesto) && ("senza sede fissa").equalsIgnoreCase(tipo)){
		flusso = MasterListImportUtil.FLUSSO_APICOLTURA;
	}
	
	this.setIdFlussoOriginale(flusso);
	
}



}
