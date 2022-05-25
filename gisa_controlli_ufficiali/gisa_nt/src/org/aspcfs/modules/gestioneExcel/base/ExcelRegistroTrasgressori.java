/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneExcel.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspcfs.modules.registrotrasgressori.base.Trasgressione;

import com.darkhorseventures.framework.actions.ActionContext;

//DAVIDE
public class ExcelRegistroTrasgressori {
	static String[] intestazioni;
	static
	{
		intestazioni= new String[]{
				"n. prog",
				"Id controllo",
				"ASL di competenza",
				"Ente accertatore 1",
				"Ente accertatore 2",
				"Ente accertatore 3",
				"PV n.",
				"Num. sequestro eventualmente effettuato",
				"Data accertamento",
				"Data prot. in entrata in regione",
				"Trasgressore",
				"Obbligato in solido",
				"Importo sanzione ridotta",
				"Importo sanzione ridotta del 30%",
				"Illecito di competenza della U.O.D. 01",
				"PV oblato in misura ridotta",
				"PV oblato in misura ultraridotta",
				"Data pagamento PV",
				"Funzionario assegnatario",
				"Presentati scritti difensivi",	
				"Presentata richiesta riduzione sanzione e/o rateizzazione",
				"Presentata richiesta audizione",
				"Ordinanza emessa",
				"Num. Ordinanza", 
				"Data di emissione dell'Ordinanza",
				"Giorni di lavorazione pratica",
				"Importo sanzione ingiunta",
				"Data Ultima Notifica Ordinanza",
				"Data Pagamento Ordinanza",
				"Pagamento Ordinanza Effettuato nei Termini",
				"Concessa rateizzazione dell'ordinanza-ingiunzione",
				"Rate pagate",
				"Ordinanza ingiunzione oblata",
				"Importo effettivamente introitato (2)", //30
				"Presentata opposizione all'ordinanza-ingiunzione",
				"Sentenza favorevole al ricorrente",
				"Importo stabilito dalla A.G.",
				"Ordinanza-ingiunzione oblata secondo il dispositivo della sentenza",
				"Importo effettivamente introitato (3)",
				"Avviata per esecuzione forzata",
				"Importo effettivamente introitato (4)",
				"Note Gruppo 1",
				"Note Gruppo 2",
				"Pratica chiusa"
			};
	}
	
	private int anno = -1;
	private String path = null;
	private String fullPath = null;
	private String nome = null;
	
	
	
	
	public String getFullPath() {
		return fullPath;
	}


	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getAnno() {
		return anno;
	}


	public void setAnno(int anno) {
		this.anno = anno;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


    public void genera(Connection db) throws SQLException, IOException {
		
		
        //Creare il file ...(ispirarsi a GestoreGlifo servlet)
        
        
		
		generaFoglioRegistro(anno,fullPath , db);
		
		
		
		
	}
	
	
	private void generaFoglioRegistro(int anno, String outputPath, Connection conn) throws SQLException, IOException
	{
		
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("registro trasgressori");
		
		
		//-----stili----------------------------------------------------
		Font fontGrassetto = wb.createFont();
		fontGrassetto.setFontHeightInPoints((short)9);
		fontGrassetto.setFontName("Arial");
		fontGrassetto.setBold(true);
		
		Font fontNormale = wb.createFont();
		fontNormale.setFontHeightInPoints((short)9);
		fontNormale.setFontName("Arial");
		fontNormale.setBold(false);
		
		XSSFCellStyle stileHeader = wb.createCellStyle();
		stileHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		stileHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stileHeader.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		stileHeader.setAlignment(CellStyle.ALIGN_CENTER);
		stileHeader.setFont(fontGrassetto);
		stileHeader.setWrapText(true);
//		stileHeader.setLocked(true);
		stileHeader.setBorderBottom(CellStyle.BORDER_THIN);
		stileHeader.setBorderTop(CellStyle.BORDER_THIN);
		stileHeader.setBorderLeft(CellStyle.BORDER_THIN);
		stileHeader.setBorderRight(CellStyle.BORDER_THIN);
		
		XSSFCellStyle stileCella = wb.createCellStyle();
		stileCella.setWrapText(true);
//		stileCella.setLocked(true);
		stileCella.setFont(fontNormale);
		stileCella.setVerticalAlignment(CellStyle.ALIGN_LEFT);
		stileCella.setAlignment(CellStyle.ALIGN_CENTER);
		
		XSSFCellStyle stileCellaVerde = wb.createCellStyle();
		stileCellaVerde.setWrapText(true);
		stileCellaVerde.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		stileCellaVerde.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		stileCella.setLocked(true);
		stileCellaVerde.setFont(fontNormale);
		stileCellaVerde.setVerticalAlignment(CellStyle.ALIGN_LEFT);
		stileCellaVerde.setAlignment(CellStyle.ALIGN_CENTER);
		
		XSSFCellStyle stileCellaArancione = wb.createCellStyle();
		stileCellaArancione.setWrapText(true);
		stileCellaArancione.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		stileCellaArancione.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		stileCella.setLocked(true);
		stileCellaArancione.setFont(fontNormale);
		stileCellaArancione.setVerticalAlignment(CellStyle.ALIGN_LEFT);
		stileCellaArancione.setAlignment(CellStyle.ALIGN_CENTER);
		
		//--------------------------------------------------------
		
		Row headerRow = sheet.createRow((short)0);
		int indiceCol = 0;
		 
		
		for(String intest : intestazioni)
		{
			Cell c = headerRow.createCell(indiceCol++);
			c.setCellValue(intest);
			c.setCellStyle(stileHeader);
			
		}
		
		CreationHelper helper = wb.getCreationHelper();
		XSSFDrawing drawing = ((XSSFSheet)sheet).createDrawingPatriarch();
		
		
		//estraggo le entries
		
		ArrayList<Trasgressione> trasgressioniEstratte = ottieniEntriesTrasgressioni(anno,conn);
		int indiceRiga = 0;
//		int[] massimeLarghezzeColonne = new int[intestazioni.length];
		HashMap<Integer,Integer> massimeLarghezzeColonne = new HashMap<Integer,Integer>();
		
		
		
		for(Trasgressione trasg : trasgressioniEstratte)
		{
			indiceCol = 0;
			indiceRiga++;
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			
			int progressivo = trasg.getProgressivo();
			int annoYY = trasg.getAnnoYY();
			String tooltipPrefix = progressivo+"\\"+annoYY;
			
			int idControllo = trasg.getIdControllo();
			String asl = trasg.getAsl();
			HashMap<String,String> listaEnti = trasg.getListaEnti();
			String pv = trasg.getPV();
			String pvSequestro = trasg.getPVsequestro();
			Timestamp dataAccertamento = trasg.getDataAccertamento();
			Timestamp dataProt = trasg.getDataProt();
			String trasgressore = trasg.getTrasgressore();
			String obbligatoInSolido = trasg.getObbligatoInSolido();
			int importoSanzioneRidotta = trasg.getImportoSanzioneRidotta();
			String importoSequestroRiduzione = trasg.getImportoSequestroRiduzione() == null ? "" : trasg.getImportoSequestroRiduzione();
			boolean isCompetenzaRegionale = trasg.isCompetenzaRegionale();
			boolean isPvOblatoRidotta = trasg.isPagopaPvOblatoRidotta();
			boolean isPvOblatoUltraRidotta = trasg.isPagopaPvOblatoUltraRidotta();
			
			Timestamp dataPagamento = trasg.getPagopaPvDataPagamento();	
			
			String fiAssegnatario = trasg.getFiAssegnatario();
			boolean isPresentatiScritti = trasg.isPresentatiScritti();
			boolean isRichiestaRiduzioneSanzione = trasg.isRichiestaRiduzioneSanzione();
			boolean isRichiestaAudizione = trasg.isRichiestaAudizione();
			String ordinanzaEmessa = trasg.getOrdinanzaEmessa();
			Timestamp dataEmissione = trasg.getDataEmissione();
			int giorniLavorazione = trasg.getGiorniLavorazione();
			int importoSanzioneIngiunta = trasg.getImportoSanzioneIngiunta();
			boolean isRichiestaRateizzazione = trasg.isRichiestaRateizzazione();
			String rate = trasg.getPagopaNoRatePagate();
			boolean isIngiunzioneOblata = trasg.isIngiunzioneOblata();
			int importoEffettivamenteVersato2 = trasg.getImportoEffettivamenteVersato2();
			boolean isPresentataOpposizione = trasg.isPresentataOpposizione();
			boolean isSentenzaFavorevole = trasg.isSentenzaFavorevole();
			int importoStabilito = trasg.getImportoStabilito();
			boolean isIngiunzioneOblataSentenza = trasg.isIngiunzioneOblata();
			int importoEffettivamenteVersato3 = trasg.getImportoEffettivamenteVersato3();
			boolean isIscrizioneRuoli = trasg.isIscrizioneRuoliSattoriali();
			int importo4 = trasg.getImportoEffettivamenteVersato4();
			String note1 = trasg.getNote1();
			String note2 = trasg.getNote2();
			//String[] allegati = trasg.getAllegatoDocumentale() == null ? null : trasg.getListaAllegatiDocumentale();
			boolean isPraticaChiusa = trasg.isPraticaChiusa();
			Timestamp ultimaNotifOrdinanza = trasg.getDataUltimaNotificaOrdinanza();
			Timestamp ultimoPagamentoOrdinanza = trasg.getDataPagamentoOrdinanza();
			boolean isPagamentoOrdinanzaNeiTermini = trasg.isPagamentoRidottoConsentitoOrdinanza();
			String numOrdinanza = trasg.getNumOrdinanza(); 
		
			
			
			
			
			
			
			
			
			Row r = sheet.createRow(indiceRiga);
			
			
			
			Cell c = r.createCell(indiceCol);
			String val = progressivo+"\\"+annoYY;
			c.setCellValue(val);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : "C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(idControllo+"");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c= r.createCell(++indiceCol);
			c.setCellValue(asl);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			int numeroEnti = 0;
			for(Map.Entry<String, String> ente : listaEnti.entrySet())
			{	
				numeroEnti++;
				c = r.createCell(++indiceCol);
				c.setCellValue(ente.getValue());
				c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
				
			}
			for(int i=numeroEnti; i<3;i++)
			{
				c = r.createCell(++indiceCol);
				c.setCellValue("");
				c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
				
			}
			
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(pv!=null ? pv : "");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			

			c = r.createCell(++indiceCol);
			c.setCellValue(pvSequestro!=null ? pvSequestro : "");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			if(dataAccertamento != null)
			{
				String t = dateFormat.format(new Date(dataAccertamento.getTime()));
				c.setCellValue(t);
				
			}
			else
			{
				c.setCellValue("");
			}
			
			
			
			c = r.createCell(++indiceCol);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			if(dataProt != null)
			{
				String t = dateFormat.format(new Date(dataProt.getTime()));
				c.setCellValue(t);
			}
			else
			{
				c.setCellValue("");
			}
			
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(trasgressore!=null ? trasgressore : "");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(obbligatoInSolido!=null ? obbligatoInSolido : "");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(importoSanzioneRidotta+"");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(importoSequestroRiduzione+"");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isCompetenzaRegionale ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			

			
			c = r.createCell(++indiceCol);
			c.setCellValue(isPvOblatoRidotta ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isPvOblatoUltraRidotta ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);

			
			c = r.createCell(++indiceCol);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			if(dataPagamento != null)
			{
				c.setCellValue(dateFormat.format(new Date(dataPagamento.getTime())));
			}
			else
			{
				c.setCellValue("");
			}
			
				
			
			c = r.createCell(++indiceCol);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			c.setCellValue(null != fiAssegnatario ? fiAssegnatario : "");
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isPresentatiScritti ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isRichiestaRiduzioneSanzione ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isRichiestaAudizione ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue("A".equals(ordinanzaEmessa) ? "ord. archiviazione" : "B".equals(ordinanzaEmessa) ? "ord. ingiunzione" : "C".equals(ordinanzaEmessa) ? "pratica non lavorata" : "" );
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			c = r.createCell(++indiceCol);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			if(numOrdinanza !=null)
			{
				c.setCellValue(numOrdinanza+"");
			}
			else
			{
				c.setCellValue("");
			}
			
			c = r.createCell(++indiceCol);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			if(dataEmissione != null)
			{
				c.setCellValue( dateFormat.format(new Date(dataEmissione.getTime())) );
			}
			else
			{
				c.setCellValue("");
			}
			
			
			c = r.createCell(++indiceCol);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			if(giorniLavorazione > -1)
			{
				c.setCellValue(giorniLavorazione+"");
			}
			else
			{
				c.setCellValue("");
			}
			
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(importoSanzioneIngiunta+"");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			if(ultimaNotifOrdinanza != null)
			{
				c.setCellValue( dateFormat.format(new Date(ultimaNotifOrdinanza.getTime())) );
			}
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			if(ultimoPagamentoOrdinanza != null)
			{
				c.setCellValue( dateFormat.format(new Date(ultimoPagamentoOrdinanza.getTime())) );
			}
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isPagamentoOrdinanzaNeiTermini ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isRichiestaRateizzazione ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			
			c = r.createCell(++indiceCol);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			String strRate = trasg.getPagopaNoRatePagate();
			c.setCellValue(strRate);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isIngiunzioneOblata ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(importoEffettivamenteVersato2);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isPresentataOpposizione ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isSentenzaFavorevole ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(importoStabilito);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isIngiunzioneOblataSentenza ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(importoEffettivamenteVersato3);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isIscrizioneRuoli ? "SI" : "NO");
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(importo4);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(note1);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			c = r.createCell(++indiceCol);
			c.setCellValue(note2);
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
//			c = r.createCell(++indiceCol);
//			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
//			HSSFRichTextString strAllegati = new HSSFRichTextString("");
//			if(allegati != null)
//			{
//				for(int i=0;i<allegati.length;i++)
//				{
//					strAllegati = new HSSFRichTextString(strAllegati.getString()+allegati[i]+"\n");
//				}
//			}
//			c.setCellValue(strAllegati.getString());
			
			
			c = r.createCell(++indiceCol);
			c.setCellValue(isPraticaChiusa ? "SI" : "NO");
			 
			c.setCellStyle("C".equals(ordinanzaEmessa) ? stileCellaArancione : isPraticaChiusa ? stileCellaVerde : stileCella);
			
			
		}
		
////
		sheet.setColumnWidth(2, 8000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		sheet.setColumnWidth(10, 8000);
		sheet.setColumnWidth(6, 8000);
		sheet.setColumnWidth(8, 8000);
		sheet.setColumnWidth(35, 8000);
		sheet.setColumnWidth(new CellReference("U1").getCol(),8000);
		sheet.setColumnWidth(new CellReference("S1").getCol(),8000);
		sheet.setColumnWidth(new CellReference("L1").getCol(),8000);
		sheet.setColumnWidth(new CellReference("AN1").getCol(),8000);
		sheet.setColumnWidth(new CellReference("W").getCol(),8000);
		sheet.setColumnWidth(new CellReference("AC").getCol(),8000);
		sheet.setColumnWidth(new CellReference("AG").getCol(),8000);
		sheet.setColumnWidth(new CellReference("I1").getCol(),6000);
		sheet.setColumnWidth(new CellReference("S1").getCol(),6000);
		sheet.setColumnWidth(new CellReference("Z1").getCol(),6000);
		sheet.setColumnWidth(new CellReference("Y1").getCol(),6000);
		
//		for(int i=0;i<=indiceCol;i++)
//		{
//			sheet.setColumnWidth(i, massimeLarghezzeColonne.get(i) != null ? massimeLarghezzeColonne.get(i) * 256 : 10);
//		}
		
		
		 
		
		//lock dell'header e delle prime 2 colonne (che scorrono )
		sheet.createFreezePane(2, 1);
		
		
		
		File out = new File(outputPath);
		FileOutputStream fos = new FileOutputStream(out);
		wb.write(fos);
		fos.close();
		
	}
	
	
	
	private ArrayList<Trasgressione> ottieniEntriesTrasgressioni(int anno, Connection conn) throws SQLException
	{
		String query = "select * from registro_trasgressori_vista order by anno desc, progressivo desc";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		ArrayList<Trasgressione> trasgressioniEstratte = new ArrayList<Trasgressione>();
		
		
		while(rs.next())
		{
			Trasgressione tras = new Trasgressione(rs,conn);
			if(anno==-1 || anno == tras.getAnno() )
				trasgressioniEstratte.add(tras);
		}
		
		//Collections.sort(trasgressioniEstratte);
		
		st.close();
		rs.close();
		
		return trasgressioniEstratte;
	}
	
	
}