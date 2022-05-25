/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneExcel.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.FormulaParser;
import org.apache.poi.ss.formula.FormulaRenderer;
import org.apache.poi.ss.formula.FormulaType;
import org.apache.poi.ss.formula.ptg.AreaPtg;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.formula.ptg.RefPtgBase;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspcfs.modules.util.imports.DbUtil;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;



public class ExcelDpatVersione2016 {


//	public static final String PATH_TEMPLATE = "/templateExcel/Template_nuovo.xlsx";
	static HashMap<String,String> codici = new HashMap<String,String>();
	static
	{
		//TODO: questi mapping vanno tirati fuori dal DB
		codici.put("201","Avellino");
		codici.put("202","Benevento"	);
		codici.put("203","Caserta");
		codici.put("204","Napoli 1 Centro");
		codici.put("205","Napoli 2 Nord");
		codici.put("206","Napoli 3 Sud");
		codici.put("207","Salerno");
	}
	
	
	public static void importFoglio(String pathFile,String codiceAsl, int anno, Connection conn,int idAreaSelezionata) throws IOException, SQLException, ParseException, Exception
	{
		updateEntriesUsandoFoglio(pathFile, codiceAsl, anno, conn,idAreaSelezionata);
	}
	
//	public static String generaFoglio(String pathTemplate,String codAsl,int anno, Connection conn, String outputPath,int idAreaSelezionata,boolean confogliotrasposto) throws EncryptedDocumentException, InvalidFormatException, IOException, SQLException
//	{
//		
//		return generaFoglio(codAsl,anno,pathTemplate,false,conn,outputPath,idAreaSelezionata,confogliotrasposto);
//	}
//	
	
	
	
	//routine che effettua la query per ottenere tutte le celle di una localita di riferimento per un anno di riferimento, e ottiene 
	//per ciascun piano, dal vecchio template, le formule per calcolare l'ui, e le inserisce in una tabella dpat_formula del db.

//	public static void updateFormulasInDbDaVecchioTemplate(String pathOldTemplate,int codiceLocalitaRiferimento, int anno) throws IOException, SQLException
//	{   
//		FileInputStream fIs = new FileInputStream(new File(pathOldTemplate));
//		XSSFWorkbook workbook = new XSSFWorkbook(fIs);
//		XSSFSheet sheet = workbook.getSheetAt(1);
//		Connection  db = null ;
//		db=DbUtil.getConnection();
//
//
//		PreparedStatement pst = db.prepareStatement("delete from dpat_formula");
//		pst.executeUpdate();
//		pst.close();
//
//		String sql = "select distinct sezione,id_attivita,piano,attivita,descrizione_indicatore,ordinamento,ordinamento_figli from public_functions.dbi_get_programmazione_dpat_all(?,?) order by " +
//				"ordinamento,ordinamento_figli ";
//		pst = db.prepareStatement(sql);
//		pst.setInt(1, codiceLocalitaRiferimento);
//		pst.setInt(2, anno);
//
//		ResultSet rs = pst.executeQuery();
//
//
//		String oldSezione = new String("");
//		String oldPiano = new String("");
//		String oldAttivita = new String("");
//		String oldIdAttivita = new String("");
//		final int rigaSezione = 1;
//		final int rigaPiano = 2;
//		final int rigaAttivita = 3;
//		final int rigaIndicatore = 6;
//		final int rigaSottopiani = 4;
//		final int rigaDaInserireVuota = 5;
//		final int rigaUo = 7;
//		int colIndicatore = 2 ;
//
//		//indici per i merge di header
//		int colStartSezione = -1;
//		int colStartPiano = -1; 
//		int colStartAttivita = -1;
//
//		boolean firstTime = true;
//		boolean toCreateUi = false;
//		boolean flag = false;
//
//		long incremId = 0;
//
//		while(rs.next())
//		{
//			String sezione = rs.getString("sezione");
//			String piano = rs.getString("piano");
//			String attivita = rs.getString("attivita");
//			String indicatore = rs.getString("descrizione_indicatore");
//			String idattivita = rs.getString("id_attivita");
//
//
//			colIndicatore++;
//
//
//
//			if(!piano.equals(oldPiano))
//			{
//				Cell c = null;
//				if(!firstTime)
//				{
//					//sto su colonna UI
//					try
//					{
//						c = sheet.getRow(rigaUo).getCell(colIndicatore);
//						String formula = c.getCellFormula();
//						//							if( ((XSSFCell)c).getReference().equals("AO7") )
//
//						//							System.out.println(formula + oldIdAttivita);
//
//						PreparedStatement pst2 = db.prepareStatement("insert into dpat_formula(id,id_piano_attivita,formula) values(?,?,?)");
//						pst2.setLong(1, ++incremId);
//						pst2.setInt(2,Integer.parseInt(oldIdAttivita));
//						pst2.setString(3,formula);
//						pst2.executeUpdate();
//						System.out.println(pst2);
//						pst2.close();
//
//						//inserisco UO
//					}
//					catch(Exception ex)
//					{
//						//blank cell
//						System.out.println( ((XSSFCell)c).getReference()+" "+oldIdAttivita);
//					}
//					colIndicatore++;
//				}
//
//				if(!sezione.equals(oldSezione))
//				{
//					if(!firstTime)
//					{	
//						//spazio vuoto
//
//
//						colIndicatore++;
//						flag = true;
//
//
//					}
//
//				}
//
//			}
//
//
//			//riempio indicatore e sottopiano
//			if(!piano.equals("PIANO C8"))
//			{
//
//
//			}
//			else
//			{
//				colIndicatore--;
//			}
//
//			oldPiano = piano;
//			oldSezione = sezione;
//			oldAttivita = attivita;
//			oldIdAttivita = idattivita;
//			firstTime = false;
//
//
//		}
//
//		pst.close();
//		//prendo formula dall'ultimo ui e inserisco in db
//		Cell c = sheet.getRow(rigaUo).getCell(++colIndicatore);
//		String formula = c.getCellFormula();
//		pst = db.prepareStatement("insert into dpat_formula(id,id_piano_attivita,formula) values(?,?,?)");
//		pst.setString(3, formula);
//		pst.setInt(2,Integer.parseInt(oldIdAttivita));
//		pst.setLong(1, ++incremId);
//		pst.executeUpdate();
//		pst.close();
//
//		//			System.out.println(formula + " "+oldIdAttivita);
//
//		//inserisco le formule delle ultime 2 colonne di saldo nelle 2 entry dummy di dpat_formula
//		colIndicatore+=2;
//		c = sheet.getRow(rigaUo).getCell(colIndicatore);
//		formula = c.getCellFormula();
//		pst = db.prepareStatement("insert into dpat_formula values(?,?,?,?)");
//		pst.setString(4, formula);
//		pst.setString(3, "somma delle ui delle sezioni"); 
//		pst.setInt(2,-1); //-1 e l'id piano attivita su dpat_formula usato per conservare la formula del penultimo saldo
//		pst.setLong(1,8888);
//		pst.executeUpdate();
//
//		colIndicatore++;
//		formula = sheet.getRow(rigaUo).getCell(colIndicatore).getCellFormula();
//		pst.setString(4, formula);
//		pst.setString(3, "saldo tra le ui"); 
//		pst.setInt(2,-2); //-1 e l'id piano attivita su dpat_formula usato per conservare la formula del penultimo saldo
//		pst.setLong(1,9999);
//		pst.executeUpdate();
//
//		pst.close();
//
//		//rs.close();
//
//
//
//	}
//		
//	
	
	
	
	
	//routine per generare un nuovo template vuoto aggiornato con la struttura gisa, partendo da un template vuoto 
	
	private static void allineaTemplateConGisa(String startingTemplate, String outputPath, int codiceLocRiferimento,int annoRiferimento, Connection db) throws IOException, SQLException
	{
		FileInputStream fIs = new FileInputStream(new File(startingTemplate));
		XSSFWorkbook workbook = new XSSFWorkbook(fIs);
		XSSFSheet sheet = workbook.getSheetAt(1);
		workbook.removeSheetAt(0); //elimino primo foglio
		String sql = "select distinct sezione,id_attivita,piano,attivita,descrizione_indicatore,ordinamento,ordinamento_figli from public_functions.dbi_get_programmazione_dpat_all(?,?) order by " +
				"ordinamento,ordinamento_figli ";
		PreparedStatement pst0 = db.prepareStatement(sql);
		pst0.setInt(1, codiceLocRiferimento);
		pst0.setInt(2,annoRiferimento);
		ResultSet rs = pst0.executeQuery();
		
		
		//stili------------------------------------------------------------------------------
		Font font1 = workbook.createFont();
	    font1.setFontHeightInPoints((short)9);
	    font1.setFontName("Arial");

	    Font fontGrassetto = workbook.createFont();
	    fontGrassetto.setFontHeightInPoints((short)9);
	    fontGrassetto.setFontName("Arial");
	    fontGrassetto.setBold(true);

	    
		
		
		XSSFCellStyle stileNeroProtetto = workbook.createCellStyle();
		stileNeroProtetto.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		stileNeroProtetto.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stileNeroProtetto.setLocked(true);
		
		XSSFCellStyle stileGrigio = workbook.createCellStyle();
		stileGrigio.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		stileGrigio.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stileGrigio.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		stileGrigio.setAlignment(CellStyle.ALIGN_CENTER);
		stileGrigio.setFont(font1);
		stileGrigio.setWrapText(true);
		stileGrigio.setLocked(true);
		stileGrigio.setBorderBottom(CellStyle.BORDER_THIN);
		stileGrigio.setBorderTop(CellStyle.BORDER_THIN);
		stileGrigio.setBorderLeft(CellStyle.BORDER_THIN);
		stileGrigio.setBorderRight(CellStyle.BORDER_THIN);
		
		
		//stili per sezioni
		ArrayList<XSSFCellStyle> stili = new ArrayList<XSSFCellStyle>();
		int indLastSectionStile = -1;
		
		XSSFCellStyle stileVerde = workbook.createCellStyle();
		byte[] rgb = new byte[]{10,(byte)180,50};
		stileVerde.setFillForegroundColor(new XSSFColor(rgb));
		stileVerde.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stileVerde.setLocked(true);
		stili.add(stileVerde);
		
		
		XSSFCellStyle stileAzzurro = workbook.createCellStyle();
		stileAzzurro.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		stileAzzurro.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stileAzzurro.setLocked(true);
		stili.add(stileAzzurro);
		
		
		XSSFCellStyle stileViola = workbook.createCellStyle();
		stileViola.setFillForegroundColor(IndexedColors.PINK.getIndex());
		stileViola.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stileViola.setLocked(true);
		stili.add(stileViola);
		
		
		XSSFCellStyle stileArancio = workbook.createCellStyle();
		stileArancio.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		stileArancio.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stileArancio.setLocked(true);
		stili.add(stileArancio);
		
		
		
		XSSFCellStyle stileMarrone = workbook.createCellStyle();
		stileMarrone.setFillForegroundColor(IndexedColors.BROWN.getIndex());
		stileMarrone.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stileMarrone.setLocked(true);
		stili.add(stileMarrone);
		
		XSSFCellStyle stileRosso = workbook.createCellStyle();
		stileRosso.setFillForegroundColor(IndexedColors.RED.getIndex());
		stileRosso.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stileRosso.setLocked(true);
		stili.add(stileRosso);
		
		//setto i bordi per tutti gli stili delle celle
		for(int i=0;i<stili.size();i++)
		{
			stili.get(i).setBorderTop(CellStyle.BORDER_THIN);
			stili.get(i).setBorderBottom(CellStyle.BORDER_THIN);
			stili.get(i).setBorderLeft(CellStyle.BORDER_THIN);
			stili.get(i).setBorderRight(CellStyle.BORDER_THIN);
			stili.get(i).setFont(fontGrassetto);
			stili.get(i).setAlignment(CellStyle.ALIGN_CENTER);
			stili.get(i).setWrapText(true);
		}
		
		
		
		final int rigaSezione = 1;
		final int rigaPiano = 2;
		final int rigaAttivita = 3;
		final int rigaIndicatore = 6;
		final int rigaSottopiani = 4;
		final int rigaDaInserireVuota = 5;
		final int rigaUo = 7;
		
		String oldSezione = new String("");
		String oldPiano = new String("");
		String oldAttivita = new String("");
		String oldIdAttivita = new String("");
		
		HashMap<String,String> fromColUiToIdAttivita = new HashMap<String,String>(); //usato per ottenere le formule per ciascun ui
		
		
		
//		int colSezione = 2;
//		int colPiano = 1;
//		int colAttivita = 1;
		int colIndicatore = 2 ;
	
		//indici per i merge di header
		int colStartSezione = -1;
		int colStartPiano = -1; 
		int colStartAttivita = -1;
		
//		for(int i=0;i<2;i++)
//			itSezione.next();
		
		
		
		Cell t = sheet.getRow(rigaDaInserireVuota).createCell(3);
		t.setCellValue("");
		
		
		boolean firstTime = true;
		boolean toCreateUi = false;
		boolean flag = false;
//		boolean lastEntry = false;
		
		while(rs.next())
		{
			String sezione = rs.getString("sezione");
			String piano = rs.getString("piano");
			String attivita = rs.getString("attivita");
			String indicatore = rs.getString("descrizione_indicatore");
			String idattivita = rs.getString("id_attivita");
			
//			if(rs.isLast()) //in tal caso settiamo flag che fara in modo di recuperare ultimo salto sezione
//				lastEntry = true;
			
//			if(!sezione.equals(oldSezione))
//			{
//				Cell c = itSezione.next();
//				System.out.println(c+ " ref "+((XSSFCell)c).getReference());
////				c.setCellValue(sezione);
//				oldSezione = sezione;
//			}
			
//			if(!piano.equals(oldPiano))
//			{
//				colPiano++;
//				sheet.getRow(rigaPiano).getCell(colPiano).setCellValue(piano);
//				oldPiano = piano;
//				toCreateUi = true;
//			}
//			if(!attivita.equals(oldAttivita))
//			{
//				colAttivita++;
//				sheet.getRow(rigaAttivita).getCell(colAttivita).setCellValue(attivita);
//				oldAttivita = attivita;
//			}
//			
//			if(indicatore == null)
//			{
//				continue;
//			}
//			
			
			colIndicatore++;
			
			
			if(!piano.equals(oldPiano))
			{
				Cell c = null;
				if(!firstTime)
				{
					XSSFCellStyle stileDaSezione = stili.get(indLastSectionStile);
					//creo ui sulla colonna attuale
					c = sheet.getRow(rigaIndicatore).createCell(colIndicatore);
					c.setCellValue("U.I");
					c.setCellStyle(stileGrigio);
					//inserisco UO
					c = sheet.getRow(rigaUo).createCell(colIndicatore);
					c.setCellValue(0.0);
					c.setCellStyle(stileDaSezione);
					
					//e salvo riferimento di questo UO come chiave della struttura che mi mantiene corrispondenza con gli id piani, che usero poi per tirare fuori le formule
					fromColUiToIdAttivita.put(((XSSFCell)c).getReference(), oldIdAttivita);
					
					
					//devo riempire con lo stile anche la cella del sottopiano (che non ha mai testo) relativamente alla colonna ui
					sheet.getRow(rigaSottopiani).createCell(colIndicatore).setCellStyle(stileDaSezione);
					
					//e lo stesso per la riga vuota 
					sheet.getRow(rigaDaInserireVuota).createCell(colIndicatore).setCellStyle(stileDaSezione);
					
					colIndicatore++;
				}

				if(!sezione.equals(oldSezione))
				{
					if(!firstTime)
					{	
						//spazio vuoto
						sheet.getRow(rigaPiano).createCell(colIndicatore);
						sheet.getRow(rigaSezione).createCell(colIndicatore);
						sheet.getRow(rigaAttivita).createCell(colIndicatore);
						sheet.getRow(rigaIndicatore).createCell(colIndicatore);
						sheet.getRow(rigaSottopiani).createCell(colIndicatore);
						sheet.getRow(rigaDaInserireVuota).createCell(colIndicatore);
						//merge sezione precedente
						sheet.addMergedRegion( new CellRangeAddress(rigaSezione,rigaSezione,colStartSezione,colIndicatore-1));
						
						colIndicatore++;
						flag = true;
						
						
					}
					
					c = sheet.getRow(rigaSezione).createCell(colIndicatore);
					c.setCellValue(sezione.toUpperCase());
					
					indLastSectionStile++;
					XSSFCellStyle stileDaSezione = stili.get(indLastSectionStile);
					c.setCellStyle(stileDaSezione);
					//devo mettere stile nella riga vuota
					sheet.getRow(rigaDaInserireVuota).createCell(colIndicatore).setCellStyle(stileDaSezione);
					
					colStartSezione = colIndicatore;
				}
				
				
				//riempio la riga del piano
				c = sheet.getRow(rigaPiano).createCell(colIndicatore);
				c.setCellValue(piano.toUpperCase());
				XSSFCellStyle stileDaSezione = stili.get(indLastSectionStile);
				c.setCellStyle(stileDaSezione);
				
				//e riempio la riga attivita
				c = sheet.getRow(rigaAttivita).createCell(colIndicatore);
				c.setCellValue(attivita.toUpperCase());
				c.setCellStyle(stileDaSezione);
				//e metto stile nella riga vuota
				sheet.getRow(rigaDaInserireVuota).createCell(colIndicatore).setCellStyle(stileDaSezione);
				
				
				
				//faccio i merge di intestazioni per piano e per attivita
				if(!firstTime)
				{	int off = (flag) ? 2 : 1;
					sheet.addMergedRegion(new CellRangeAddress(rigaPiano,rigaPiano,colStartPiano,colIndicatore-off));
					sheet.addMergedRegion(new CellRangeAddress(rigaAttivita,rigaAttivita,colStartAttivita,colIndicatore-off));
					flag = false;
				}
				colStartPiano = colIndicatore;
				colStartAttivita = colIndicatore;
				
				
			}
			
			
			//riempio indicatore e sottopiano
			if(!piano.equals("PIANO C8"))
			{
				Cell c = sheet.getRow(rigaIndicatore).createCell(colIndicatore);
				//come valore dell'indicatore metto il nome preso dal db escludendo il prefisso (una cui parte e un'intestazione che ci serve)
				
				
				int tI = indicatore.indexOf("_");
				String intestSottopiano = "";
				String nomeIndicatore = indicatore;
				
				if(tI > 0)
				{
					intestSottopiano = indicatore.charAt(tI+1)+"";
					nomeIndicatore = indicatore.substring(tI+2);
				}
				else
				{	//non esiste char _
					
					//quindi sia che sia un ATT sia che e un caso come ad esempio D33N. ISPEZIONI oppure D33 N.ISPEZIONI o ATT D33 N.ISPEZIONI
					
					//preventivamente tiro fuori la label del nome del piano //ES D38 estratto da "PIANO D38" (senza usare _)
					String labelPiano = piano.split("\\s++")[1]; //splitto e prendo secondo token, usando una o piu' occorrenze di char vuoto
					//prendo indice dal quale partire col nome dell'indicatore
					//che e uguale a quanto viene dopo la label del piano (se c'e' uno spazio vuoto devo saltarlo) quindi
					
//					System.out.println(piano+ " "+indicatore+ " "+labelPiano);
					
					int tI3 = indicatore.indexOf(labelPiano);
					nomeIndicatore = indicatore.substring( tI3 + labelPiano.length() );
					if(nomeIndicatore.charAt(0)==' ') //caso di spazio dopo il piano
					{
						nomeIndicatore = nomeIndicatore.substring(1);
						//mentre l'intestazione sottopiano rimane vuota
					}
					
				}
				XSSFCellStyle stileDaSezione = stili.get(indLastSectionStile);
				
			
				
				
				//setto nome indicatore
				c.setCellValue(nomeIndicatore);
				//nel caso della colonna MN correzione....
				if(((XSSFCell)c).getReference().equalsIgnoreCase("MN7"))
					c.setCellValue(nomeIndicatore.substring(1));
				c.setCellStyle(stileGrigio);
				
				//setto sottopiano
				c = sheet.getRow(rigaSottopiani).createCell(colIndicatore);
				c.setCellValue(intestSottopiano);
				
				//nel caso della colonna MN correzione....
				if(((XSSFCell)c).getReference().equalsIgnoreCase("MN5"))
					c.setCellValue("AB");
				c.setCellStyle(stileDaSezione);
				
				//inserisco UO
				c = sheet.getRow(rigaUo).createCell(colIndicatore);
				c.setCellValue(0.0);
				c.setCellStyle(stileDaSezione);
				

				//stile per la riga vuota 
				sheet.getRow(rigaDaInserireVuota).createCell(colIndicatore).setCellStyle(stileDaSezione);
				
			}
			else
			{
				colIndicatore--;
			}
			
			oldPiano = piano;
			oldSezione = sezione;
			oldAttivita = attivita;
			oldIdAttivita = idattivita;
			firstTime = false;
			
			
		}
		
		
		
		//rifiniture sull'ultima sezione/piano.......................................
		//creo UI su ultima sezione/piano
		//creo ui sulla colonna attuale
		Cell c = sheet.getRow(rigaIndicatore).createCell(++colIndicatore);
		c.setCellValue("U.I");
		CellStyle stileDaSezione = stili.get(indLastSectionStile);
		c.setCellStyle(stileGrigio);
		//inserisco UO
		c = sheet.getRow(rigaUo).createCell(colIndicatore);
		c.setCellValue(0.0);
		c.setCellStyle(stileDaSezione);
		//aggiungo riferimento di quell'UO per studio formule successivo
		fromColUiToIdAttivita.put( ((XSSFCell)c).getReference() , oldIdAttivita );
		//stile riga vuota
		sheet.getRow(rigaDaInserireVuota).createCell(colIndicatore).setCellStyle(stileDaSezione);
		//stile riga sottopiano
		sheet.getRow(rigaSottopiani).createCell(colIndicatore).setCellStyle(stileDaSezione);
		
		//colonna vuota vuoto
		colIndicatore++;
		sheet.getRow(rigaPiano).createCell(colIndicatore);
		sheet.getRow(rigaSezione).createCell(colIndicatore);
		sheet.getRow(rigaAttivita).createCell(colIndicatore);
		sheet.getRow(rigaIndicatore).createCell(colIndicatore);
		sheet.getRow(rigaSottopiani).createCell(colIndicatore);
		sheet.getRow(rigaDaInserireVuota).createCell(colIndicatore);
		//merge sezione precedente
		sheet.addMergedRegion(new CellRangeAddress(rigaSezione,rigaSezione,colStartSezione,colIndicatore-1));
		colIndicatore++;
		//merge piani e attivita
		sheet.addMergedRegion(new CellRangeAddress(rigaPiano,rigaPiano,colStartPiano,colIndicatore-2));
		sheet.addMergedRegion(new CellRangeAddress(rigaAttivita,rigaAttivita,colStartAttivita,colIndicatore-2));
		//......................................................
		//creo ultime 2 colonne dai titoli cablati e per far si che nella fase successiva delle formule vengano considerate anche queste
		//inserisco riferimenti a queste colonne nella struttura degli ui, anche se non sono ui
		indLastSectionStile++;
		c = sheet.getRow(rigaAttivita).createCell(colIndicatore);
		c.setCellValue("SOMMA DELLE UI. DELLE SEZIONI A-B-C-D-E");
		c.setCellStyle(stili.get(indLastSectionStile));
		//merge verticale
		sheet.addMergedRegion(new CellRangeAddress(rigaAttivita,rigaIndicatore,colIndicatore,colIndicatore));
		//per creazione futura formula
		c = sheet.getRow(rigaUo).createCell(colIndicatore);
		c.setCellValue(0.0);
		c.setCellStyle(stili.get(indLastSectionStile));
		fromColUiToIdAttivita.put( ((XSSFCell)c).getReference()  , -1+""); //l'oid aattivita e finto ed e -1 nella tabella delle formule
		//stile per le prime 2 righe di questa colonna
		sheet.getRow(rigaSezione).createCell(colIndicatore).setCellStyle(stili.get(indLastSectionStile));
		sheet.getRow(rigaPiano).createCell(colIndicatore).setCellStyle(stili.get(indLastSectionStile));
		
		c = sheet.getRow(rigaAttivita).createCell(++colIndicatore);
		c.setCellValue("SALDO TRA LE U.I. MINIME STABILITE (COLONNA B) E QUELLE DESTINATE ALL'EFFETTUAZIONE DEI PIANI DI MONITORAGGIO E DELLE ATTIVITA' (COLONNA TC) IL RISULTATO DEVE ESSERE 0");
		c.setCellStyle(stili.get(indLastSectionStile));
		//merge verticale
		sheet.addMergedRegion(new CellRangeAddress(rigaAttivita,rigaIndicatore,colIndicatore,colIndicatore));
		//per ottenimento formula
		c = sheet.getRow(rigaUo).createCell(colIndicatore);
		c.setCellValue(0.0);
		c.setCellStyle(stili.get(indLastSectionStile));
		fromColUiToIdAttivita.put(((XSSFCell)c).getReference(), -2+"");
		//stile per le prime 2 righe di questa colonna
		sheet.getRow(rigaSezione).createCell(colIndicatore).setCellStyle(stili.get(indLastSectionStile));
		sheet.getRow(rigaPiano).createCell(colIndicatore).setCellStyle(stili.get(indLastSectionStile));
		
		
		
		
		
		
		
		
		
//		String[] sortedKeys = new String[fromColUiToIdAttivita.keySet().size()]; 
//		sortedKeys = fromColUiToIdAttivita.keySet().toArray(sortedKeys);
//		
//		Arrays.sort(sortedKeys);
		
		
		pst0.close();
		PreparedStatement pst = null;
		boolean invalid = false;
		
//		riporto le formule sugli U.O.
		//NB: ATTUALMENTE QUELLI CHE DEVONO ESSERE NERI SONO QUELLI CHE IN DPAT_FORMULA NON HANNO ENTRY
		for(String cellRef : fromColUiToIdAttivita.keySet())
		{
			
			CellReference ref = new CellReference(cellRef);
			c = sheet.getRow(rigaUo).getCell(ref.getCol());
			
			pst = db.prepareStatement("select formula from dpat_formula where id_piano_attivita = ?");
			pst.setInt( 1, Integer.parseInt(fromColUiToIdAttivita.get(cellRef)) );
			ResultSet rs2 = pst.executeQuery();
						
			if(!rs2.next()) 
			{
				c.setCellValue("");
				c.setCellStyle(stileNeroProtetto);
				continue;
			}

			String formula = rs2.getString(1);
			
			if(formula != null) 
			{
				c.setCellFormula(formula);
				
			}
			else
			{ //quelli neri
				c.setCellValue("");
				c.setCellStyle(stileNeroProtetto);
				
			}
			
			
			
			pst.close();
			rs2.close();
		
		}
		
		
		
		
		
		//esistono infine delle colonne che devono essere nere nonostante non siano UI senza formula
		String[] colonneCablate = new String[]{"AE","DH","DO","DR","DT","LJ","QD","QE","RW"}; //le colonne nere che non sono uid non validi
		
		for(String ref : colonneCablate)
		{
			CellReference cRef = new CellReference(ref+(rigaUo+1));
			Cell c2 = sheet.getRow(cRef.getRow()).getCell(cRef.getCol());
			c2.setCellValue("");
			c2.setCellStyle(stileNeroProtetto);
		}
		
		
		
		FileOutputStream outFile =new FileOutputStream(new File(outputPath));
		workbook.write(outFile);
		outFile.close();
		workbook.close();
		
	}
	
	
	private static void updateEntriesUsandoFoglio(String path,String codiceLocalita,int anno, Connection conn,int idAreaSelezionata) throws Exception
	{
		
		
		//NB: la primissima cosa e controllare che il timestamp di generazione del foglio usato per l'import
		//sia successivo a TUTTE le date del campo modified della tabella strutture_asl di quell'asl e quell'anno
		//apro foglio e vedo:
		//se non c'e' la keyword nelle core properties, sicuramente e foglio riempito partendo dal template vecchio e sicuramente senza metadati
		//altrimenti e creato partendo dal nuovo template, e la keyword ci dice se sono stati usati o meno metadati
//		String nomeLocalita = path.substring(path.lastIndexOf("/")+1);
//		nomeLocalita = nomeLocalita.substring(0, nomeLocalita.indexOf(".xls"));
//		
//		String codiceLocalita = null;
//		
//		for(String codice : codici.keySet())
//		{
//			if(nomeLocalita.equals(codici.get(codice)))
//			{
//				codiceLocalita = codice;
//				break;
//			}
//		}
		
//		if(codiceLocalita == null)
//		{
//			System.out.println("Err - Inserire nome localita valido");
//			return;
//		}
		
		
		FileInputStream fIs = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(fIs);
		
		//estraggo il timestamp (attualmente settato nel titolo)
		String title = workbook.getProperties().getCoreProperties().getTitle();
		int iT = title.substring(0,title.indexOf("/")).lastIndexOf(" ");
		String dataStr = title.substring(iT+1,title.length());
		
//		String dataStr = title.split(" ")[1]+" "+title.split(" ")[2];
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date data = null;
		java.sql.Date dataSqlFoglio = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		//trasformo in data sql
		data = dateFormat.parse(dataStr);
		dataSqlFoglio =  new java.sql.Date(data.getTime());
		//estraggo il max delle date dalla tabella del campo modified
		pst = conn.prepareStatement("select max(modified) from strutture_asl where id_asl=? and anno=?");
		pst.setInt(1, Integer.parseInt(codiceLocalita));
		pst.setInt(2, anno);
		rs = pst.executeQuery();
		rs.next();
		java.sql.Date dataSqlDaDb =  rs.getDate(1);
		

//		if(dataSqlFoglio.compareTo(dataSqlDaDb) < 0)
//		{
//			System.out.println("Errore: il foglio utilizzato per l'import e obsoleto!");
//			return;
//		}
		
		
		
//		
		
		String keyword = workbook.getProperties().getCoreProperties().getKeywords();
		if(keyword == null)
		{
			
			updateEntriesUsandoFoglioSenzaMetadati(path,true,codiceLocalita,anno,conn,idAreaSelezionata);
		}
		else
		{
			if(keyword.equalsIgnoreCase("true"))
			{
				
				updateEntriesUsandoFoglioConMetadati(path,codiceLocalita,anno, conn); //puo essere solo da nuovo template
			}
			else
			{	
				
				updateEntriesUsandoFoglioSenzaMetadati(path,false,codiceLocalita,anno, conn,idAreaSelezionata);
			}
		}
		
		workbook.close();
	}
	
	private static void updateEntriesUsandoFoglioConMetadati(String path,String codiceLocalita,int anno, Connection db) throws IOException, SQLException
	{
		HashMap<Integer,String> mapStruttureSommeUiESaldi = new HashMap<Integer,String>(); //per ogni id struttura salviamo la somma degli ui e i saldi come stringa concatenata (la chiave e id struttura
		//in dpat_strutture_asl)
		int oldIdStruttura = -1;
		
		String nomeLocalita = path.substring(path.lastIndexOf("/")+1);
		nomeLocalita = nomeLocalita.substring(0, nomeLocalita.indexOf(".xls"));
		
		
//		String codiceLocalita = null;
//		
//		for(String codice : codici.keySet())
//		{
//			if(nomeLocalita.equals(codici.get(codice)))
//			{
//				codiceLocalita = codice;
//				break;
//			}
//		}
//		if(codiceLocalita == null)
//		{
//			System.out.println("Err - Inserire nome localita valido");
//			return;
//		}
		
		
		
		FileInputStream fIs = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(fIs);
		//se e stato inserito un workbook che ha ancora il primo foglio (vecchia versione)
		XSSFSheet sheet = null;
		try
		{
			sheet = workbook.getSheetAt(1);
		}
		catch(IllegalArgumentException ex)
		{
			sheet =  workbook.getSheetAt(0);
		}
		
		XSSFCell cell = null, oldCell = null;
		
		
		POIXMLProperties props = workbook.getProperties();
		POIXMLProperties.CustomProperties custProp = props.getCustomProperties();
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		
		
		int startingRowIndex = 7; //0-based
		int lastRowIndex = sheet.getLastRowNum(); //0 based
		ArrayList<HashMap<String,String>> entries = new ArrayList<HashMap<String,String>>();
		
		int indiceColonnaSaldi = sheet.getRow(9).getLastCellNum();
		
		while( sheet.getRow(2).getCell(indiceColonnaSaldi) == null )
		{
			indiceColonnaSaldi--;
		}
		
		int i = 0;
		int indiceUltimaRiga=-1; //usiamo questa variabile di appoggio poiche non e detto che lastRowIndex sia indice ultima riga piena
		for(i=startingRowIndex; i<=lastRowIndex;i++)
		{
				Row riga = sheet.getRow(i);
////			String descrizione_struttura = riga.getCell(0) +""; //il toString() ci fornisce la descrizione
////			System.out.println(descrizione_struttura);POIXMLProperties props = workbook.getProperties();
//			
//			
			for(Iterator<Cell> it = riga.cellIterator(); it.hasNext();)
			{
				Cell cella = it.next();
				
				String ref = ((XSSFCell)cella).getReference();
				CTProperty propEntry = custProp.getProperty(ref);
				if(propEntry != null)
				{
					indiceUltimaRiga = i;
					String strEntry = propEntry.getLpwstr();
					String[] tokens = strEntry.split("-"); //a questo punto ho in tokens le stringhe dei metadati, associate al valore contenuto in quella cella con quella reference
//					forProperties.add(new String(actRef+"-"+startCol+"-"+ (startRow-1) +"-"+idIndicatore+"-"+idPiano+"-"+idSezione+"-"+idStruttura));
					String cellRef = tokens[0];
					Integer idIndicatore = Integer.parseInt(tokens[3]);
					Integer idPiano = Integer.parseInt(tokens[4]);
					Integer idSezione = Integer.parseInt(tokens[5]);
					Integer idDpat = Integer.parseInt(tokens[6]);
					Integer idStruttura = Integer.parseInt(tokens[7]);
					
					if(idStruttura != oldIdStruttura)
					{
						if(oldIdStruttura != -1)
						{
							//valuto colonna somma ui e colonna saldi per quella riga
							Integer sommaUiPerStrutt =(int) Math.round(evaluator.evaluate(sheet.getRow(i-1).getCell(indiceColonnaSaldi-1)).getNumberValue());
//							Integer saldoPerStrutt =  (int) Math.round(evaluator.evaluate(sheet.getRow(i-1).getCell(indiceColonnaSaldi)).getNumberValue());
							Integer saldoPerStrutt = arrotonda(evaluator.evaluate(sheet.getRow(i-1).getCell(indiceColonnaSaldi)).getNumberValue());
							
							mapStruttureSommeUiESaldi.put(oldIdStruttura, sommaUiPerStrutt+ " "+saldoPerStrutt);
//							System.out.println(mapStruttureSommeUiESaldi.get((long)oldIdStruttura));
						}
						oldIdStruttura = idStruttura;
						oldCell = (XSSFCell) cella;
					}
					
					String refSommaUiCell = tokens[8];
					String sommaUi = null;
					try
					{
						sommaUi = ""+(int)Math.round(evaluator.evaluate(riga.getCell(new CellReference(refSommaUiCell).getCol())).getNumberValue());
					}
					catch(Exception ex) //poiche possono aver rimosso la formula UI dalla cella quando l'hanno modificato
					{
						
						sommaUi = "0";
					}
					HashMap<String,String> entry = new HashMap<String,String>(); 
					
					entry.put("id_indicatore",idIndicatore+"");
					entry.put("id_dpat",idDpat+"");
					entry.put("id_struttura",idStruttura+"");
					entry.put("id_attivita",idPiano+"");
					entry.put("id_sezione", idSezione+"");
					//quelli presi dal valore attuale delle celle excel
					
					String uiProgramm = null;
					try
					{
						uiProgramm = ((int)Math.round(Double.parseDouble(cella+"")))+"";
					}
					catch(Exception ex)
					{
						uiProgramm = "0";
					}
					
					
					entry.put("ui_programmato",uiProgramm);
					entry.put("ui",sommaUi); 
					
					
					entries.add(entry);
					
				}
			}
		
		}
		
		//aggiungo ultima
		Integer sommaUiPerStrutt = (int) Math.round(evaluator.evaluate(sheet.getRow(indiceUltimaRiga).getCell(indiceColonnaSaldi-1)).getNumberValue());
//		Integer saldoPerStrutt =  (int)Math.round( evaluator.evaluate(sheet.getRow(indiceUltimaRiga).getCell(indiceColonnaSaldi)).getNumberValue());
		Integer saldoPerStrutt = arrotonda(evaluator.evaluate(sheet.getRow(indiceUltimaRiga).getCell(indiceColonnaSaldi)).getNumberValue());
		mapStruttureSommeUiESaldi.put(oldIdStruttura, sommaUiPerStrutt+ " " + saldoPerStrutt ); 
		

	
		
		
		
		
		scaricaEntriesSuDb(db, entries,Integer.parseInt(codiceLocalita),anno,mapStruttureSommeUiESaldi);
		
		workbook.close();
		
	}
	
	
	@SuppressWarnings("deprecation")
	private static void updateEntriesUsandoFoglioSenzaMetadati(String path, boolean fromOldTemplate,String codiceLocalita ,int anno, Connection db,int idAreaSelezionata) throws IOException, SQLException, Exception
	{
		HashMap<Integer,String> mapStruttureSommeUiESaldi = new HashMap<Integer,String>(); //per ogni id struttura salviamo la somma degli ui e i saldi come stringa concatenata (la chiave e id struttura
		String nomeLocalita = path.substring(path.lastIndexOf("/")+1);
		nomeLocalita = nomeLocalita.substring(0, nomeLocalita.indexOf(".xls"));
		
//		String codiceLocalita = null;
//		
//		for(String codice : codici.keySet())
//		{
//			if(nomeLocalita.equals(codici.get(codice)))
//			{
//				codiceLocalita = codice;
//				break;
//			}
//		}
//		if(codiceLocalita == null)
//		{
//			System.out.println("Err - Inserire nome localita valido");
//			return;
//		}
		
		FileInputStream fIs = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(fIs);
		//se e stato inserito un workbook che ha ancora il primo foglio (vecchia versione)
		
		
		if(workbook.getNumberOfSheets() > 1)
		{
			
			workbook.close();
			throw new Exception("FILE EXCEL UTILIZZATO PER L'IMPORT CON TROPPI FOGLI!");
		}
		
		
		XSSFSheet sheet = null;
		sheet = workbook.getSheetAt(0);
		if(sheet.getSheetName().contains("_VERTICALE"))
		{
			transpose(workbook,0,true);
		}
		sheet = workbook.getSheetAt(0);
		
		
		
		FileOutputStream fout = new FileOutputStream("C:\\Users\\davide\\Desktop\\test.xlsx");
		workbook.write(fout);
		fout.close();
		
		
		
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		int indiceColonnaSaldi = sheet.getRow(7).getLastCellNum();
				
		while( sheet.getRow(2).getCell(indiceColonnaSaldi) == null )
		{
			indiceColonnaSaldi--;
		}
		
		String sql ="select id_indicatore,id_dpat,id_sezione,ui_calcolabile,id_attivita,descrizione_struttura,id_struttura,descrizione_indicatore,ui,ui_programmato,carico_di_lavoro_effettivo_annualeminimo_di_struttura,saldo ,pathstrutt"+
				" from public_functions.dbi_get_programmazione_dpat_all("+codiceLocalita+",?,?)  "+
				" order by pathstrutt,ordinamento,ordinamento_figli";
		
		
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, anno);
		pst.setInt(2,idAreaSelezionata);
		ResultSet rs = pst.executeQuery();
		
		
		int riga = 6;
		int colonna = -1;
		String descrizioneStrutturaLetta = "", descrizioneStrutturaLettaOld = "";
		int idSezioneLetto = -1,idSezioneLettoOld = -1;
		int idPianoLetto = -1, idPianoLettoOld = -1;
		int idStrutturaOld = -1;
		int idIndicatoreLetto = -1;
		boolean calcolabileLetto ;
		int idDpat = -1;
		int idStruttura = -1;
		ArrayList<HashMap<String,String>> entries = new ArrayList<HashMap<String,String>>();
//		int colonnaSpuriaDaSaltare = new CellReference("JF10").getCol();
		
		
		
		while(rs.next())
		{
			//questi servono per capire il posizionamento
			descrizioneStrutturaLetta = rs.getString("descrizione_struttura");
			idIndicatoreLetto=rs.getInt("id_indicatore");
			idSezioneLetto =rs.getInt("id_sezione");
			idPianoLetto = rs.getInt("id_attivita");
			calcolabileLetto = rs.getBoolean("ui_calcolabile");
			idDpat = rs.getInt("id_dpat");
			idStruttura = rs.getInt("id_struttura");
			
			if(!descrizioneStrutturaLetta.equals(descrizioneStrutturaLettaOld))
			{
				if(riga > 6) //andando su una nuova riga, c'e' da recuperare l'ui dell'ultimo piano
				{
					//quindi prima di risettare la colonna
					Integer uiDaExcel = null;
					try
					{
						uiDaExcel = Math.round((int)evaluator.evaluate(sheet.getRow(riga).getCell(colonna)).getNumberValue());
					}
					catch(NullPointerException ex)
					{
						ex.printStackTrace();
						
						uiDaExcel = 0;
					}
//					System.out.print("UI:"+uiDaExcel+" ");
					for(int i = entries.size()-1; i>=0;i--)
					{
						if( ! entries.get(i).get("ui").equals("DA_SETTARE"))
						{
							break;
						}
						entries.get(i).put("ui", uiDaExcel+"");
						
					}
					//salvo riferimento per l'idstruttura -> riga somme ui e saldo
					
					Integer sommaUiPerStrutt = (int) Math.round(evaluator.evaluate(sheet.getRow(riga).getCell(indiceColonnaSaldi-1)).getNumberValue());
//					Integer saldiPerPerStrutt = (int) Math.round(evaluator.evaluate(sheet.getRow(riga).getCell(indiceColonnaSaldi)).getNumberValue());
					Integer saldiPerPerStrutt = arrotonda( evaluator.evaluate(sheet.getRow(riga).getCell(indiceColonnaSaldi)).getNumberValue() );
					
					
					
					mapStruttureSommeUiESaldi.put(idStrutturaOld,sommaUiPerStrutt+" "+saldiPerPerStrutt );
//					System.out.println(mapStruttureSommeUiESaldi.get(idStrutturaOld));
				}
				
				idStrutturaOld = idStruttura;
				riga++;
				colonna = 3;
				idPianoLettoOld = -1;
				idSezioneLettoOld = -1;
//				System.out.println();
//				System.out.print(descrizioneStrutturaLetta+ " : ");
			}
			
			
			
			
			if(idPianoLetto != idPianoLettoOld && idPianoLettoOld > 0 /* && colonna != new CellReference("JH10").getCol() */)
			{
				//estraggo l'ui e lo riporto per tutte le entry precedenti che non lo avevano ancora settato
				Integer uiDaExcel = null;
				
				try
				{
					uiDaExcel = (int)Math.round(evaluator.evaluate(sheet.getRow(riga).getCell(colonna)).getNumberValue());
				}
				catch(NullPointerException ex) //nel caso in cui all'UI hanno rimosso la formula nel riempimento (l'evaluate ritorna null)
				{
//					ex.printStackTrace();
					
					uiDaExcel = 0;
				}
				for(int i = entries.size()-1; i>=0;i--)
				{
					if( ! entries.get(i).get("ui").equals("DA_SETTARE"))
					{
						break;
					}
					entries.get(i).put("ui", uiDaExcel+"");
					
				}
				
//				System.out.print("UI:"+uiDaExcel+" ");
				
				if(colonna != new CellReference("JG8").getCol())
					colonna++;
			
			}
			
			
			if(idSezioneLetto != idSezioneLettoOld && idSezioneLettoOld > 0)
			{
				
				colonna++;
				
//				System.out.print("     ");
				
			}
			
			//costruisco l'entry aggiornata unendo campi aggiornati dal file excel e campi non presenti nel file excel ma presi dal result set
			
			HashMap<String,String> entry = new HashMap<String,String>();
			//quelli presi dal result set :
			entry.put("id_indicatore", idIndicatoreLetto+"");
			entry.put("id_dpat",idDpat+"");
			entry.put("id_struttura",idStruttura+"");
			entry.put("id_attivita",idPianoLetto+"");
			entry.put("id_sezione", idSezioneLetto+"");
			entry.put("ui_calcolabile",calcolabileLetto+"" );
			//quelli presi dal valore attuale delle celle excel
			entry.put("descrizione_struttura",sheet.getRow(riga).getCell(0).getStringCellValue());
			entry.put("carico_di_lavoro_effettivo_annualeminimo_di_struttura",sheet.getRow(riga).getCell(1)+"");
			
			String val = sheet.getRow(riga).getCell(colonna)+"";
			
			String uiProgramm = null;
			try
			{
				uiProgramm = ((int)Math.round(Double.parseDouble( val )))+""; 
			}
			catch(Exception ex)
			{
				uiProgramm = "0";
			}
			
			entry.put("ui_programmato", uiProgramm);// proprio il valore della cella su cui ci siamo posizionati
			entry.put("ui","DA_SETTARE"); //questo verra settato all'indietro quando incontreremo una colonna relativa all'ui
			
			if(colonna != new CellReference("JF8").getCol())
				entries.add(entry);
			
			
			
//			System.out.print(sheet.getRow(riga).getCell(colonna).getReference()+entry.get("ui_programmato")+" ");
			
			
			colonna++;
			
			idPianoLettoOld = idPianoLetto;
			descrizioneStrutturaLettaOld = descrizioneStrutturaLetta;
			idSezioneLettoOld = idSezioneLetto;
		}
		
		
		
		
		//arrivati alla fine del result set, sicuramente siamo arrivati alla fine del file, quindi dobbiamo recuperare l'ultimo ui dell'ultima riga
		Integer uiDaExcel = null;
		try
		{
			uiDaExcel = (int)Math.round(evaluator.evaluate(sheet.getRow(riga).getCell(colonna)).getNumberValue());
		}
		catch(NullPointerException ex) //per il caso in cui sia stata eliminata formula da cella UI
		{
//			ex.printStackTrace();
			uiDaExcel = 0;
			
		}
//		System.out.print("UI:"+uiDaExcel+" ");
		//e li riporto sulle ultime entry senza ui (del suo piano quindi)
		for(int i = entries.size()-1; i>=0;i--)
		{
			if( ! entries.get(i).get("ui").equals("DA_SETTARE"))
			{
				break;
			}
			entries.get(i).put("ui", uiDaExcel+"");
			
		}
		
		Integer sommaUiPerStrutt = (int) Math.round(evaluator.evaluate(sheet.getRow(riga).getCell(indiceColonnaSaldi-1)).getNumberValue());
//		Integer saldiPerPerStrutt = (int) Math.round(evaluator.evaluate(sheet.getRow(riga).getCell(indiceColonnaSaldi)).getNumberValue());
		Integer saldiPerPerStrutt = arrotonda(evaluator.evaluate(sheet.getRow(riga).getCell(indiceColonnaSaldi)).getNumberValue());
		mapStruttureSommeUiESaldi.put(idStrutturaOld,sommaUiPerStrutt+" "+saldiPerPerStrutt );
		
//		for(Long k : mapStruttureSommeUiESaldi.keySet())
//		{
//			System.out.println(mapStruttureSommeUiESaldi.get(k));
//		}
 
		scaricaEntriesSuDb(db,entries,Integer.parseInt(codiceLocalita),anno,mapStruttureSommeUiESaldi);
		
		
		pst.close();
//		db.close();
		
	}
	
	
	private static void scaricaEntriesSuDb( Connection db , ArrayList<HashMap<String,String>> entries,int codiceLocalita,int anno,HashMap<Integer,String> sommeUiESaldi ) throws SQLException
	{
		
//		
//		for(Integer idStrutture : sommeUiESaldi.keySet())
//		{
//			System.out.println("id strutt : "+idStrutture+" --> "+sommeUiESaldi.get(idStrutture));
//		}
//		
		
		
		
		java.util.Date today = new java.util.Date();
//		String disableQuery = "update dpat_struttura_indicatore set enabled=false, data_fine_validita= ?  where enabled = true and cast(descr_sezione as int) in (select s.id from dpat_sezione s join dpat d on s.id_dpat_istanza = d.id_dpat_istanza where s.anno = ? and d.id_asl = ?)";
//		String disableQuery = "update dpat_struttura_indicatore set enabled=false, data_fine_validita= ?  where enabled = true";
//		String disableQuery = "update dpat_struttura_indicatore set enabled=false, data_fine_validita=? where enabled = true and id_struttura=?";
		String disableQuery = "update dpat_struttura_indicatore set enabled=false, data_fine_validita=? where enabled = true and id_struttura in (select id from dpat_strutture_asl sa where sa.enabled and sa.anno = ? and sa.id_asl = ? )";
		
		String updateQuery = "update dpat_struttura_indicatore set somma_ui = ?, ui = ?, enabled = true, data_fine_validita = NULL where id_dpat = ? and id_struttura = ? and id_indicatore = ?";
		String insertQuery = "insert into dpat_struttura_indicatore(id_dpat,id_struttura,id_indicatore,ui,somma_ui,enabled,entered,modified,entered_by,modified_by,descr_indicatore,descr_attivita,descr_piano,descr_sezione) values(?,?,?,?,?,?,?,?,?,?,?,?,(select codice_piano from dpat_attivita where id=?),?)";
		String getQuery = "select id_dpat, id_struttura, id_indicatore from dpat_struttura_indicatore where id_dpat=? and id_struttura=? and id_indicatore= ?";
		
		int totInserite = 0,totUpdate = 0;
		
		PreparedStatement pst2 = null;
		PreparedStatement pst0 = null;;
		
		pst0 = db.prepareStatement(disableQuery);
		pst0.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
		pst0.setInt(2, anno);
		pst0.setInt(3,codiceLocalita);
		
		pst0.executeUpdate();
		
//		//disabilito chi devo disabilitare
//		for(HashMap<String,String> toUpdate : entries)
//		{
//			pst0 = db.prepareStatement(disableQuery);
//			pst0.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
//			pst0.setInt(2,Integer.parseInt(toUpdate.get("id_struttura")));
//			pst0.executeUpdate();
//			pst0.close();
//		}
		
		
		int i=0;
		//ora faccio inserimenti/updates
		for(HashMap<String,String> toUpdate : entries)
		{
			
//			if(Integer.parseInt(toUpdate.get("id_attivita")) == 1257 &&  Integer.parseInt(toUpdate.get("id_struttura")) == 1013)
//				System.out.println(toUpdate);
//			
			//siccome l'ui programmato da excel potrebbe essere venuto con la virgola per id ecimali, dobbiamo rimettergli il punto
			
			if((int)Double.parseDouble(toUpdate.get("ui_programmato")) == 0)
				continue;
			
			
			pst2 = db.prepareStatement(getQuery);
			pst2.setInt(1, Integer.parseInt(toUpdate.get("id_dpat")));
			pst2.setInt(2, Integer.parseInt(toUpdate.get("id_struttura")));
			pst2.setInt(3, Integer.parseInt(toUpdate.get("id_indicatore"))-2000);
////			System.out.println(pst2+"");
			if( pst2.executeQuery().next() ) //esisteva gia quell'entry, quindi ne aggiorno i valori
			{
				pst2.close();
				pst2 = db.prepareStatement(updateQuery);
				pst2.setString(1, toUpdate.get("ui") );
				pst2.setString(2, toUpdate.get("ui_programmato") );
				pst2.setInt(3, Integer.parseInt(toUpdate.get("id_dpat")) );
				pst2.setInt(4, Integer.parseInt(toUpdate.get("id_struttura")) );
				pst2.setInt(5, Integer.parseInt(toUpdate.get("id_indicatore"))-2000);
				pst2.executeUpdate();
//				System.out.println(pst2);
//				System.out.println("UPDATE");
////				System.out.println("UPDATING PER "+v+ " RIGHE");
//				System.out.println(pst2);
				totUpdate++;
			}
			else
			{
				
				
				pst2.close();
				pst2 = db.prepareStatement(insertQuery);
				pst2.setInt(1,Integer.parseInt(toUpdate.get("id_dpat")));
				pst2.setInt(2,Integer.parseInt(toUpdate.get("id_struttura")));
				pst2.setInt(3,Integer.parseInt(toUpdate.get("id_indicatore"))-2000);
				pst2.setString(4,toUpdate.get("ui_programmato"));
				pst2.setString(5,toUpdate.get("ui"));
				pst2.setBoolean(6,true);
				pst2.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				pst2.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
//				pst2.setDate(7,new java.sql.Date(today.getTime())); //data inserimento
//				pst2.setDate(8,new java.sql.Date(today.getTime())); //data modifica
				pst2.setInt(9, 291 );
				pst2.setInt(10, 291);
				pst2.setInt(11,Integer.parseInt(toUpdate.get("id_indicatore")));
				pst2.setInt(12,Integer.parseInt(toUpdate.get("id_attivita")));
				pst2.setInt(13, Integer.parseInt(toUpdate.get("id_attivita")));
				pst2.setInt(14,Integer.parseInt(toUpdate.get("id_sezione")));
				pst2.executeUpdate();
//				System.out.println(pst2);
//				System.out.println("------------------------------------------------");
//				System.out.println("idDpat:"+Integer.parseInt(toUpdate.get("id_dpat")));
//				System.out.println("idstruttura:"+Integer.parseInt(toUpdate.get("id_struttura")));
//				System.out.println("idindicatore:"+Integer.parseInt(toUpdate.get("id_indicatore")));
//				System.out.println("uiprogrammato:"+Double.parseDouble(toUpdate.get("ui_programmato")));
//				System.out.println("(somma)ui:"+Double.parseDouble(toUpdate.get("ui")));
//				System.out.println(pst2);
//				System.out.println("------------------------------------------------");
//				System.out.println("INSERITO");
				totInserite++;
			}
			i++;
			pst2.close();
		}
		
		System.out.println("inserite : "+totInserite+" update: "+totUpdate);
		//TO DO: gestire
		
		String queryPerSaldoCanc = "delete from saldi_strutture where id_struttura = ?";
		String queryPerSaldoGetOldId = "select id from saldi_strutture where id_struttura = ?";
		String queryPerSaldoUpdateConVecchioId = "update saldi_strutture set somma_ui=?, saldo=? where id_struttura=?";
		String queryPerSaldoInsertConNuovoId = "insert into saldi_strutture(id_struttura,somma_ui,saldo) values(?,?,?)"; 
		
		
		//aggiungo i saldi e le somme ui
		for(Integer idStruttura : sommeUiESaldi.keySet())
		{
			
			String sommaUi = sommeUiESaldi.get(idStruttura).split(" ")[0];
			String saldo = 	sommeUiESaldi.get(idStruttura).split(" ")[1];
			
			PreparedStatement pst3 = db.prepareStatement(queryPerSaldoGetOldId);
			pst3.setInt(1,idStruttura);
			ResultSet rs3 = pst3.executeQuery();
			
			if(rs3.next()) //esisteva gia entry per quella struttura quindi riutilizziamo quell'entry in update
			{
				pst3.close();
				pst3 = db.prepareStatement(queryPerSaldoUpdateConVecchioId);
				pst3.setInt(3,idStruttura);
				pst3.setString(1,sommaUi);
				pst3.setString(2,saldo);
			}
			else //invece non esisteva
			{
				pst3.close();
				pst3 = db.prepareStatement(queryPerSaldoInsertConNuovoId);
				pst3.setInt(1, idStruttura);
				pst3.setString(2, sommaUi);
				pst3.setString(3, saldo);
			}
			//eseguo inserimento
			
			pst3.executeUpdate();
			pst3.close();
			
		}
		
		
//		pst2.close();
	}
	
	
	
	//routine che riempie un foglio di template, con i dati estratti dal db, per una localita e per un dato anno, inserendo o non inserendo i metadati (per successivi import) nel foglio
	
	
	private static Integer arrotonda(Double toRound)
	{
//		double t = Math.abs(toRound - Math.floor(toRound));
//		if (t >= 0.5 && t<=0.5001)
//		{
//			t = Math.floor(toRound);
//		}
//		else
//		{
//			t =  Math.round(toRound);
//		}
		double t = Math.signum(toRound) * Math.round(Math.abs(toRound));
		return new Integer((int) t) ;
	}
	
	
	private static boolean copyFormula(XSSFSheet sheet, XSSFCell org, XSSFCell dest) {
		  int shiftRows = dest.getRowIndex() - org.getRowIndex();
		    int shiftCols = dest.getColumnIndex() - org.getColumnIndex();
		    
	    if (org == null || dest == null || sheet == null 
	            || org.getCellType() != Cell.CELL_TYPE_FORMULA)
	    {
	    	   return false;
	        
	    }
	    if (org.isPartOfArrayFormulaGroup())
	        return false;
	    String formula = org.getCellFormula();
	  
	    
	    XSSFEvaluationWorkbook workbookWrapper = 
	            XSSFEvaluationWorkbook.create((XSSFWorkbook) sheet.getWorkbook());
	    Ptg[] ptgs = FormulaParser.parse(formula, workbookWrapper, FormulaType.CELL
	            , sheet.getWorkbook().getSheetIndex(sheet));
	    for (Ptg ptg : ptgs) {
	        if (ptg instanceof RefPtgBase) // base class for cell references
	        {
	            RefPtgBase ref = (RefPtgBase) ptg;
	            if (ref.isColRelative())
	                ref.setColumn(ref.getColumn() + shiftCols);
	            if (ref.isRowRelative())
	                ref.setRow(ref.getRow() + shiftRows);
	        } else if (ptg instanceof AreaPtg) // base class for range references
	        {
	            AreaPtg ref = (AreaPtg) ptg;
	            if (ref.isFirstColRelative())
	                ref.setFirstColumn(ref.getFirstColumn() + shiftCols);
	            if (ref.isLastColRelative())
	                ref.setLastColumn(ref.getLastColumn() + shiftCols);
	            if (ref.isFirstRowRelative())
	                ref.setFirstRow(ref.getFirstRow() + shiftRows);
	            if (ref.isLastRowRelative())
	                ref.setLastRow(ref.getLastRow() + shiftRows);
	        }
	    }
	    formula = FormulaRenderer.toFormulaString(workbookWrapper, ptgs);
//	    System.out.println("Formula ROUND("+formula+",0) copiata in Cella "+dest.getReference());

	    
//	    dest.setCellFormula("ROUND("+formula+",0)");
	    dest.setCellFormula(formula);

	    return true;
	}
	
	
	
	
	private static String getFormula(XSSFSheet sheet, Cell org) {
		 
		    
	    if (org == null || sheet == null 
	            || org.getCellType() != Cell.CELL_TYPE_FORMULA)
	    {
//	     System.out.println("Attenzione Cella non di Tipo Formula");
	    	return null;
	        
	    }
	    if (org.isPartOfArrayFormulaGroup())
	        return null;
	    String formula = org.getCellFormula();
	  
	    
	    XSSFEvaluationWorkbook workbookWrapper = 
	            XSSFEvaluationWorkbook.create((XSSFWorkbook) sheet.getWorkbook());
	    Ptg[] ptgs = FormulaParser.parse(formula, workbookWrapper, FormulaType.CELL
	            , sheet.getWorkbook().getSheetIndex(sheet));
	    for (Ptg ptg : ptgs) {
	        if (ptg instanceof RefPtgBase) // base class for cell references
	        {
	            RefPtgBase ref = (RefPtgBase) ptg;
	            if (ref.isColRelative())
	                ref.setColumn(ref.getColumn());
	            if (ref.isRowRelative())
	                ref.setRow(ref.getRow() );
	        } else if (ptg instanceof AreaPtg) // base class for range references
	        {
	            AreaPtg ref = (AreaPtg) ptg;
	            if (ref.isFirstColRelative())
	                ref.setFirstColumn(ref.getFirstColumn() );
	            if (ref.isLastColRelative())
	                ref.setLastColumn(ref.getLastColumn() );
	            if (ref.isFirstRowRelative())
	                ref.setFirstRow(ref.getFirstRow() );
	            if (ref.isLastRowRelative())
	                ref.setLastRow(ref.getLastRow() );
	        }
	    }
	    formula = FormulaRenderer.toFormulaString(workbookWrapper, ptgs);
	    return formula ;
	}
	
	private static void removeRow(XSSFSheet sheet, int rowIndex) {
	    int lastRowNum=sheet.getLastRowNum();
	    if(rowIndex>=0&&rowIndex<lastRowNum){
	        sheet.shiftRows(rowIndex+1,lastRowNum, -1);
	    }
	    if(rowIndex==lastRowNum){
	        Row removingRow=sheet.getRow(rowIndex);
	        if(removingRow!=null){
	            sheet.removeRow(removingRow);
	        }
	    }
	}
	
	
	
	
	
	
	static class Pair<T,T2>
	{
		T first;
		T2 second;
		
		public Pair(T f,T2 s)
		{
			first = f; second = s;
		}
		
		T getFirst(){return first;}
		T2 getSecond(){return second;}
	}
	
	
	public static void transpose(Workbook wb, int sheetNum, boolean replaceOriginalSheet) {
	    Sheet sheet = wb.getSheetAt(sheetNum);

	    Pair<Integer, Integer> lastRowColumn = getLastRowAndLastColumn(sheet);
	    int lastRow = lastRowColumn.getFirst();
	    int lastColumn = lastRowColumn.getSecond();

	    //LOG.debug("Sheet {} has {} rows and {} columns, transposing ...", new Object[] {sheet.getSheetName(), 1+lastRow, lastColumn});

	    List<CellModel> allCells = new ArrayList<CellModel>();
	    for (int rowNum = 0; rowNum <= lastRow; rowNum++) {
	        Row row = sheet.getRow(rowNum);
	        if (row == null) {
	            continue;
	        }
	        for (int columnNum = 0; columnNum < lastColumn; columnNum++) {
	            Cell cell = row.getCell(columnNum);
	            allCells.add(new CellModel(cell));
	        }
	    }
	   // LOG.debug("Read {} cells ... transposing them", allCells.size());

	    Sheet tSheet = wb.createSheet(sheet.getSheetName() +"_VERTICALE");
	    
	    Sheet dummyS = wb.createSheet("dummySheet"); //CREO FOGLIO DUMMY TEMPORANEO PER TRASPORRE LE FORMULE
	    
	    for (CellModel cm : allCells) {
	        if (cm.isBlank()) {
	            continue;
	        }

	        int tRow = cm.getColNum();
	        int tColumn = cm.getRowNum();

	        Row row = tSheet.getRow(tRow);
	        if (row == null) {
	            row = tSheet.createRow(tRow);
	        }

	        Cell cell = row.createCell(tColumn);
	        cm.insertInto(cell,sheet,dummyS);
	    }
	    
	    wb.removeSheetAt( wb.getNumberOfSheets()-1 ); //RIMUOVO FOGLIO DUMMY USATO PER TRASPORRE LE FORMULE

	    lastRowColumn = getLastRowAndLastColumn(sheet);
	    lastRow = lastRowColumn.getFirst();
	    lastColumn = lastRowColumn.getSecond();
	    //LOG.debug("Transposing done. {} now has {} rows and {} columns.", new Object[] {tSheet.getSheetName(), 1+lastRow, lastColumn});

	    if (replaceOriginalSheet) {
	        int pos = wb.getSheetIndex(sheet);
	        wb.removeSheetAt(pos);
	        wb.setSheetOrder(tSheet.getSheetName(), pos);
	    }

	}

	private static Pair<Integer, Integer> getLastRowAndLastColumn(Sheet sheet) {
	    int lastRow = sheet.getLastRowNum();
	    int lastColumn = 0;
	    for (Row row : sheet) {
	        if (lastColumn < row.getLastCellNum()) {
	            lastColumn = row.getLastCellNum();
	        }
	    }
	    return new Pair<Integer, Integer>(lastRow, lastColumn);
	}
	
	
	
	static class CellModel {
	    private int rowNum = -1;
	    private int colNum = -1;
	    private CellStyle cellStyle;
	    private int cellType = -1;
	    private Object cellValue;

	    public CellModel(Cell cell) {
	        if (cell != null) {
	            this.rowNum = cell.getRowIndex();
	            this.colNum = cell.getColumnIndex();
	            this.cellStyle = cell.getCellStyle();
	            this.cellType = cell.getCellType();
	            switch (this.cellType) {
	                case Cell.CELL_TYPE_BLANK:
	                    break;
	                case Cell.CELL_TYPE_BOOLEAN:
	                    cellValue = cell.getBooleanCellValue();
	                    break;
	                case Cell.CELL_TYPE_ERROR:
	                    cellValue = cell.getErrorCellValue();
	                    break;
	                case Cell.CELL_TYPE_FORMULA:
	                    cellValue = cell.getCellFormula();
	                    
	                    break;
	                case Cell.CELL_TYPE_NUMERIC:
	                    cellValue = cell.getNumericCellValue();
	                    break;
	                case Cell.CELL_TYPE_STRING:
	                    cellValue = cell.getRichStringCellValue();
	                    break;
	            }
	        }
	    }

	    public boolean isBlank() {
	        return this.cellType == -1 && this.rowNum == -1 && this.colNum == -1;
	    }
 
	    public void insertInto(Cell cell, Sheet sheet,Sheet dummySheet) {
	        if (isBlank()) {
	            return;
	        }
	        
	        cell.setCellStyle(this.cellStyle);
	        cell.setCellType(this.cellType);
	        switch (this.cellType) {
	            case Cell.CELL_TYPE_BLANK:
	                break;
	            case Cell.CELL_TYPE_BOOLEAN:
	                cell.setCellValue((boolean) this.cellValue);
	                break;
	            case Cell.CELL_TYPE_ERROR:
	                cell.setCellErrorValue((byte) this.cellValue);
	                break;
	                
	            case Cell.CELL_TYPE_FORMULA:
	                
	                String formulaStr = cellValue + "";
	                StringBuffer sbuff = new StringBuffer();
                    Pattern p = Pattern.compile("[A-Z]+[0-9]+");
            		Matcher m = p.matcher(formulaStr);
            		
            		while(m.find())
            		{
            			//System.out.println(m.group());
            			String tok = m.group();
//            			int len = 2;
            			CellReference cref = new CellReference(tok);
            			Cell c = sheet.getRow(cref.getRow()).getCell(cref.getCol());
            			
            			
            			Cell cT = null;
            			
            			if(dummySheet.getRow(cref.getCol())==null)
            			{
            				dummySheet.createRow(cref.getCol());
            				dummySheet.getRow(cref.getCol()).createCell(cref.getRow());
            			}
            			if(dummySheet.getRow(cref.getCol()).getCell(cref.getRow())==null)
            			{
            				dummySheet.getRow(cref.getCol()).createCell(cref.getRow());
            			}
            			cT = dummySheet.getRow(cref.getCol()).getCell(cref.getRow());
            			
            			
            			String tokT = ((XSSFCell)cT).getReference();
            			m.appendReplacement(sbuff, tokT);
            			
            		}
            		
            		m.appendTail(sbuff);
            		cell.setCellFormula((String) sbuff.toString());
	                
	                break;
	            case Cell.CELL_TYPE_NUMERIC:
	                cell.setCellValue((double) this.cellValue);
	                break;
	            case Cell.CELL_TYPE_STRING:
	                cell.setCellValue((RichTextString) this.cellValue);
	                break;
	        }
	    }

	    public CellStyle getCellStyle() {
	        return cellStyle;
	    }

	    public int getCellType() {
	        return cellType;
	    }

	    public Object getCellValue() {
	        return cellValue;
	    }

	    public int getRowNum() {
	        return rowNum;
	    }

	    public int getColNum() {
	        return colNum;
	    }

	}
	
	
	
	
	
	
	
	public static void stilizzatoreVerticale(Workbook wb,int indFoglio)
	{
		Sheet sheet = wb.getSheetAt(indFoglio);
		sheet.setColumnWidth(0, 0); //ATTENZIONE LA PRIMA COLONNA C'E' MA NON E' VISUALIZZATA
		//per l'intestazione SEZIONE (la prima colonna, solo per le prime due righe)...
		sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
		//e lo stesso per le successive intestazioni delle colonne C-D-E
		sheet.addMergedRegion(new CellRangeAddress(0,1,2,2));
		sheet.addMergedRegion(new CellRangeAddress(0,1,3,3));
		sheet.addMergedRegion(new CellRangeAddress(0,1,4,4));
		
		//allargo la colonna F
		sheet.setColumnWidth(5,(int)( sheet.getColumnWidth(5)*2.5));
		//e merge della F e della G orizzontalmente solo per le prime due righe dell'intestazione
		sheet.addMergedRegion(new CellRangeAddress(0,0,5,6));
		sheet.addMergedRegion(new CellRangeAddress(1,1,5,6));
		//allargo la colonna della prima struttura
		sheet.setColumnWidth(7, 15000);
//		
		Pair<Integer,Integer> startSezione = new Pair(3,1);
		Pair<Integer,Integer> startSezione2 = new Pair(3,2);
		Pair<Integer,Integer> startSezione3 = new Pair(3,3);
		Pair<Integer,Integer> startSezione6 = new Pair(3,6);
		mergeParteColonna(sheet,startSezione,sheet.getLastRowNum()-1,wb,5000,2000,CellStyle.ALIGN_CENTER);
		mergeParteColonna(sheet,startSezione2,sheet.getLastRowNum()-1,wb,5000,2000,CellStyle.ALIGN_CENTER);
		mergeParteColonna(sheet,startSezione3,sheet.getLastRowNum()-1,wb,5000,800,CellStyle.ALIGN_CENTER);
		mergeParteColonna(sheet,startSezione6,sheet.getLastRowNum()-1,wb,13000,800,CellStyle.ALIGN_FILL);
		
		((XSSFSheet)sheet).lockInsertRows(true);
		((XSSFSheet)sheet).lockSort(false);
		((XSSFSheet)sheet).lockAutoFilter(false);
		((XSSFSheet)sheet).enableLocking();
		
		
	}
	
	public static void mergeParteColonna(Sheet sheet, Pair<Integer,Integer> startingAt,int lastIndRiga,Workbook wb,int width, int height,short align)
	{
		int a = startingAt.getFirst();
		int b = a+1;
		
		
		sheet.setColumnWidth(startingAt.getSecond(), width);
		
		
		//System.out.println("L'ultima e "+((XSSFCell)sheet.getRow(startingAt.getFirst()).getCell(lastInd-1) ).getReference() );
		while(true)
		{
			while( b<lastIndRiga && sheet.getRow(b).getCell(startingAt.getSecond()) != null && (sheet.getRow(b).getCell(startingAt.getSecond()).toString()+"").length() < 1 )
			{
				b++;
			}
			if(b > lastIndRiga)
			{
				try
				{
					System.out.println("TERMNATO CON "+ ( (XSSFCell)sheet.getRow(b).getCell(startingAt.getSecond())).getReference());
				}	
				catch(Exception ex)
				{
					//ex.printStackTrace();
				}
				break;
			}
			//System.out.println("MERGED REGION TRA "+( (XSSFCell)sheet.getRow(a).getCell(startingAt.getSecond())).getReference()+" e "+( (XSSFCell)sheet.getRow(b -1).getCell(startingAt.getSecond())).getReference());
			sheet.addMergedRegion( new CellRangeAddress(a,b - 1,startingAt.getSecond(),startingAt.getSecond()));
			//metto stesso stile della prima in tutte le altre
			for(int i=a;i<=b - 1;i++)
			{
				((XSSFCell)sheet.getRow(i).getCell(startingAt.getSecond())).getCellStyle().setVerticalAlignment(align);
				((XSSFCell)sheet.getRow(i).getCell(startingAt.getSecond())).getCellStyle().setBorderTop(CellStyle.BORDER_THIN);
				((XSSFCell)sheet.getRow(i).getCell(startingAt.getSecond())).getCellStyle().setBorderBottom(CellStyle.BORDER_THIN);
				((XSSFCell)sheet.getRow(i).getCell(startingAt.getSecond())).getCellStyle().setBorderLeft(CellStyle.BORDER_THIN);
				((XSSFCell)sheet.getRow(i).getCell(startingAt.getSecond())).getCellStyle().setBorderRight(CellStyle.BORDER_THIN);
				 sheet.getRow(i).setHeight((short)height);
			}
			
			a=b;
			b=a+1;
			
		}
		
		
//		
//		 CellStyle style = ((XSSFCell)sheet.getRow(startingAt.getFirst()).getCell(startingAt.getSecond())).getCellStyle();;
//		 CellRangeAddress range = new CellRangeAddress(a,b - (separatoreBianco ? 2 : 1),startingAt.getSecond(),startingAt.getSecond());
//		 RegionUtil.setBorderBottom(style.getBorderBottom(), range, sheet, sheet.getWorkbook());
//		 RegionUtil.setBorderTop(style.getBorderTop(), range, sheet, sheet.getWorkbook());
//		 RegionUtil.setBorderLeft(style.getBorderLeft(), range, sheet, sheet.getWorkbook());
//		 RegionUtil.setBorderRight(style.getBorderRight(), range, sheet, sheet.getWorkbook());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
