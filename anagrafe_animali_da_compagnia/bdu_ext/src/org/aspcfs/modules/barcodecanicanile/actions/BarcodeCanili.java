/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.barcodecanicanile.actions;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.anagrafe_animali.base.Leish;
import org.aspcfs.modules.barcodecanicanile.base.BarcodeAnimale;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoLeishmania;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;


public class BarcodeCanili extends CFSModule {

	public String executeCommandDefault(ActionContext context) {

		return executeCommandView(context);
	}

	public String executeCommandView(ActionContext context) {
		
		if (!hasPermission(context, "barcode-canili-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		try {
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			
		}

		return ("getCanili");
	}
	
	
public String executeCommandListaCani(ActionContext context) {
		
		if (!hasPermission(context, "barcode-canili-view")) {
			return ("PermissionError");
		}

		boolean filtriPianoLeish = false;
		if (context.getRequest().getParameter("leish")!=null && context.getRequest().getParameter("leish").equals("true"))
			filtriPianoLeish = true;
		context.getRequest().setAttribute("leish", String.valueOf(filtriPianoLeish));
		
		Connection db = null;

		try {
			db = this.getConnection(context);
			int idCanile = -1;
			if ((String) context.getRequest().getParameter("selectCanili") != null && !("").equals((String) context.getRequest().getParameter("selectCanili")))
			 idCanile = new Integer((String) context.getRequest().getParameter("selectCanili")).intValue();
			
			AnimaleList listAnimali = new AnimaleList();
			listAnimali.setId_detentore(idCanile);
			
			listAnimali.setCheckControlloLeishAnnoCorrent(true);
			listAnimali.setFlagDecesso(false); //CERCA SOLO ANIMALI VIVI
			listAnimali.setFlagSmarrimento(false); //ESCLUDI ANIMALI SMARRITI
			listAnimali.setFlagFurto(false); //ESCLUDI ANIMALI RUBATI
			listAnimali.setBuildProprietario(false);
			listAnimali.setFiltriPianoLeish(filtriPianoLeish);
			listAnimali.buildList(db);
			
			context.getRequest().setAttribute("listaAnimali", listAnimali);
			
			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,
					-1, 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			
			
			
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);
			
			LookupList specieList = new LookupList(db, "lookup_specie");
			specieList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("specieList", specieList);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			
		}

		return ("listCaniOK");
	}


//public String executeCommandStampaXLS(ActionContext context) {
//	
//	if (!hasPermission(context, "barcode-canili-view")) {
//		return ("PermissionError");
//	}
//
//	Connection db = null;
//
//	try {
//		db = this.getConnection(context);
//		int idCanile = -1;
//		if ((String) context.getRequest().getParameter("idCanile") != null && !("").equals((String) context.getRequest().getParameter("idCanile")))
//			idCanile =Integer.valueOf((String) context.getRequest().getParameter("idCanile"));
//		
//		AnimaleList listAnimali = new AnimaleList();
//		listAnimali.setId_detentore(idCanile);
//		
//		listAnimali.setCheckControlloLeishAnnoCorrent(true);
//		listAnimali.setFlagDecesso(false); //CERCA SOLO ANIMALI VIVI
//		listAnimali.setFlagSmarrimento(false); //ESCLUDI ANIMALI SMARRITI
//		listAnimali.setBuildProprietario(false);
//		listAnimali.buildList(db);
//		
//		context.getRequest().setAttribute("listaAnimali", listAnimali);
//		
//		Operatore canile = new Operatore();
//		canile.queryRecordOperatorebyIdLineaProduttiva(db, idCanile);
//		
//		ComuniAnagrafica c = new ComuniAnagrafica();
//		ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,
//				-1, 1);
//		LookupList comuniList = new LookupList(listaComuni, -1);
//		comuniList.addItem(-1, "");
//		context.getRequest().setAttribute("ComuniList", comuniList);
//		
//		
//		
//		LookupList siteList = new LookupList(db, "lookup_asl_rif");
//		siteList.addItem(-1, "-- SELEZIONA VOCE --");
//		context.getRequest().setAttribute("AslList", siteList);
//		
//		HttpServletResponse response = context.getResponse();
//		response.setContentType("application/vnd.ms-excel");
//
//	     response.setHeader("Content-Disposition", 
//	    "attachment; filename="+canile.getRagioneSociale().replaceAll(" ",  "")+".xls");
//
//	     WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream()); 
//	     createXLS(listAnimali, w, db);
//		 
//		
//		
//	} catch (Exception e) {
//		e.printStackTrace();
//		context.getRequest().setAttribute("Error", e);
//		return ("SystemError");
//	} finally {
//		this.freeConnection(context, db);
//		
//	}
//
//	return ("-none-");
//}

public String executeCommandPrintRichiestaCampioni(ActionContext context){
	
	if (!hasPermission(context, "barcode-canili-view")) {
		return ("PermissionError");
	}
	
	Connection db = null;

	Cane thisCane = null;
	Gatto thisGatto = null;
	int idAnimale = -1;
	int idSpecie = -1;
	
	boolean filtriPianoLeish = false;
	if (context.getRequest().getParameter("leish")!=null && context.getRequest().getParameter("leish").equals("true"))
		filtriPianoLeish = true;
	context.getRequest().setAttribute("leish", String.valueOf(filtriPianoLeish));

	try {
		
		db = this.getConnection(context);
		
		ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
		//String HOST_CORRENTE= ApplicationProperties.getProperty("APP_HOST_CANINA");
		/* String SERVER_BDU  	=  InetAddress.getByName("hostAppBduPublic").getHostAddress();
		String APPLICATION_PORT = prefs.get("APPLICATION.PORT");
		String APPLICATION_NAME = prefs.get("APPLICATION.NAME");
		
		String url = "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT).concat("/").concat(APPLICATION_NAME).concat("/");
		
		context.getRequest().setAttribute("SERVER_BDU", url); */
		
		LookupList siteList = new LookupList(db, "lookup_asl_rif");
		siteList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("aslList", siteList);
		
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		int idLineaProduttiva = new Integer ((String)context.getRequest().getParameter("lineaId"));
		Operatore prop = new Operatore();
		prop.queryRecordOperatorebyIdLineaProduttiva(db, idLineaProduttiva);
		
		

		
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		
		/*//String idAnimaleString = (String)context.getRequest().getParameter("idAnimale");
	//	String idSpecieString = (String)context.getRequest().getParameter("idSpecie");
		
		if (idAnimaleString != null && !("").equals(idAnimaleString)){
			idAnimale = new Integer(idAnimaleString).intValue();
		}
		
		if (idSpecieString != null && !("").equals(idSpecieString)){
			idSpecie = new Integer(idSpecieString).intValue();
		}
		
		switch (idSpecie) {
		case 1:{
			thisCane = new Cane  (db, idAnimale);
			context.getRequest().setAttribute("Cane", thisCane);

			break;
		}
		
		case 2:{
			thisGatto = new Gatto  (db, idAnimale);
			context.getRequest().setAttribute("Gatto", thisGatto);
			break;
		}

		default:{
			break;
		}
		}
		*/
/*		EventoRegistrazioneBDU dati_registrazione = new EventoRegistrazioneBDU();
		dati_registrazione.getEventoRegistrazione(db, idAnimale);*/
		context.getRequest().setAttribute("dati_registrazione", prop); //Canile detentore
		
		//lista cani
		AnimaleList listAnimali = new AnimaleList();
		listAnimali.setId_detentore(idLineaProduttiva);
		Operatore detentoreCanile = new Operatore();
		detentoreCanile.queryRecordOperatorebyIdLineaProduttiva(db, idLineaProduttiva);
		context.getRequest().setAttribute("detentoreCanile", detentoreCanile);
		listAnimali.setCheckControlloLeishAnnoCorrent(true);
		listAnimali.setFlagDecesso(false); //CERCA SOLO ANIMALI VIVI
		listAnimali.setFlagSmarrimento(false); //ESCLUDI ANIMALI SMARRITI
		listAnimali.setBuildProprietario(false);
		listAnimali.setFiltriPianoLeish(filtriPianoLeish);
		listAnimali.buildList(db);
		
		
		int numeroProgressivo = Animale.getProgressivoCampione(db);
		int numero_cani = listAnimali.size();
		boolean valueCane = false;
		ArrayList<String> microchips = new ArrayList<String>();
		for(int i=0;i<numero_cani; i++) {
				Animale this_cane = (Animale)listAnimali.get(i);
				context.getRequest().getParameterMap();
				valueCane=new Boolean ((String)context.getRequest().getParameter("checkCane"+this_cane.getMicrochip())).booleanValue();
				if (valueCane){
					microchips.add((this_cane.getMicrochip() != null && !("").equals(this_cane.getMicrochip())) ? this_cane.getMicrochip() : this_cane.getTatuaggio() );
					this_cane.insertMicrochipStampaMassiva(db, thisUser, numeroProgressivo);
				}
		}
		
		
		context.getRequest().setAttribute("microchips", microchips);
		
/*			proprietario.queryRecordOperatorebyIdLineaProduttiva(db, dati_registrazione.getIdProprietario());
		context.getRequest().setAttribute("proprietario", proprietario);*/
		
		//Operatore detentore = new Operatore();
/*			detentore.queryRecordOperatorebyIdLineaProduttiva(db, dati_registrazione.getIdDetentore());
		context.getRequest().setAttribute("detentore", detentore);*/
		
		
/*		LookupList specieList = new LookupList(db, "lookup_specie");
		specieList.addItem(-1, "--Tutti--");
		context.getRequest().setAttribute("specieList", specieList);*/



/*		LookupList razzaList = new LookupList();
		razzaList.setTable("lookup_razza");
		razzaList.setIdSpecie(idSpecie);
		razzaList.buildList(db);
		razzaList.addItem(-1, systemStatus
				.getLabel("calendar.none.4dashes"));
		// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
		context.getRequest().setAttribute("razzaList", razzaList);

		LookupList tagliaList = new LookupList(db, "lookup_taglia");
		tagliaList.addItem(-1, systemStatus
				.getLabel("calendar.none.4dashes"));
		// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
		context.getRequest().setAttribute("tagliaList", tagliaList);

		LookupList mantelloList = new LookupList(db, "lookup_mantello");
		mantelloList.addItem(-1, systemStatus
				.getLabel("calendar.none.4dashes"));
		// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
		context.getRequest().setAttribute("mantelloList", mantelloList);*/
		
		ComuniAnagrafica c = new ComuniAnagrafica();
		// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI ALL'ASL
		// UTENTE
		ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
		LookupList comuniList = new LookupList(listaComuni, -1);
		comuniList.addItem(-1, "");
		context.getRequest().setAttribute("comuniList", comuniList);
		
		

	
		

	
	
}catch (Exception e) {
	e.printStackTrace();
} finally {
	this.freeConnection(context, db);
}
return getReturn(context, "viewRichiestaCampioni");

}


//public WritableWorkbook createXLS(AnimaleList listaAnimali, WritableWorkbook w, Connection db){
//	
//	try{
//	
//	WritableSheet sheet = w.createSheet("CANI_IN_CANILE", 0);
//	  
//	
//	sheet.setColumnView(0, 20);
//	sheet.setColumnView(1, 20);
//	sheet.setColumnView(2, 20);
//	sheet.setColumnView(3, 20);
//	sheet.setColumnView(4, 20);
//	sheet.setColumnView(5, 60);
//	sheet.setColumnView(5, 60);
//	sheet.setColumnView(5, 60);
//	Label label = null;
//	 
//	int h = 0;
//	int j = 0;
//	 
//	label = new Label(h++, j, "MICROCHIP");
//	sheet.addCell(label);
//	label = new Label(h++, j, "TATUAGGIO");
//	sheet.addCell(label);
//	label = new Label(h++, j, "PROPRIETARIO");
//	sheet.addCell(label);
//	label = new Label(h++, j, "DETENTORE");
//	sheet.addCell(label);
//	label = new Label(h++, j, "SESSO");
//	sheet.addCell(label);
//	label = new Label(h++, j, "MANTELLO");
//	sheet.addCell(label);
//	label = new Label(h++, j, "DATA_ULTIMO_PRELIEVO_LEISH");
//	sheet.addCell(label);
//	label = new Label(h++, j, "ULTIMO_ESITO_LEISH_DISPONIBILE");
//	sheet.addCell(label);
//	
//	
//
//	
//	 Animale thisAnimale = new Animale();
//		
//		for (int i = 0; i < listaAnimali.size(); i++) {
//			thisAnimale = (Animale) listaAnimali.get(i);
//			
//		Timestamp ultimoPrelievoLeish  = 	EventoPrelievoLeishmania.getDataPrimoPrelievoUtente(db, thisAnimale.getMicrochip());
//		String esitoLeish = Leish.getUltimoEsitoLeish(db, thisAnimale.getIdAnimale());
//		String ultimoPrelievo = "";
//		
//		if (ultimoPrelievoLeish != null)
//			ultimoPrelievo = new SimpleDateFormat("dd/MM/yyyy").format(ultimoPrelievoLeish);
//			
//			
//			LookupList mantelloList = new LookupList();
//			mantelloList.setTable("lookup_mantello");
//			mantelloList.setIdSpecie(thisAnimale.getIdSpecie());
//			mantelloList.buildList(db);
//			
//
//			
//			j++;
//			h=0;
//			//if (thisConvocato.getIdStatoPresentazione() == Convocato.convocato_non_presentato){ HO SOLO I PRESENTATI			
//			label = new Label(h++, j, thisAnimale.getMicrochip());
//			sheet.addCell(label);
//			label = new Label(h++, j, thisAnimale.getTatuaggio());
//			sheet.addCell(label);
//			label = new Label(h++, j, thisAnimale.getNomeCognomeProprietario());
//			sheet.addCell(label);
//			label = new Label(h++, j, thisAnimale.getNomeCognomeDetentore());
//			sheet.addCell(label);
//			label = new Label(h++, j, thisAnimale.getSesso());
//			sheet.addCell(label);
//			label = new Label(h++, j, mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello()));
//			sheet.addCell(label);
//			label = new Label(h++, j,  ultimoPrelievo);
//			sheet.addCell(label);
//			label = new Label(h++, j,  esitoLeish);
//			sheet.addCell(label);
//			
//			//}
//		}
//		
//		
//	//Scrivo effettivamente tutte le celle ed i dati aggiunti
//	w.write();
//	 
//	//Chiudo il foglio excel
//	w.close();
//
//	
//}catch (Exception e) {
//	e.printStackTrace();
//}
//return w;
//}


//public String executeCommandGeneraExcel(ActionContext context){
//		
//	if (!hasPermission(context, "barcode-canili-view")) {
//		return ("PermissionError");
//	}
//
//	String nome="";
// 	String	fullPath = "";
// 	
//	Connection db = null;
//
//	try {
//		db = this.getConnection(context);
//		int idCanile = -1;
//		if ((String) context.getRequest().getParameter("idCanile") != null && !("").equals((String) context.getRequest().getParameter("idCanile")))
//			idCanile =Integer.valueOf((String) context.getRequest().getParameter("idCanile"));
//		
//		ArrayList<BarcodeAnimale> listAnimali = new ArrayList<BarcodeAnimale>();
//		
//		PreparedStatement pst = db.prepareStatement("select * from get_barcode_cani_canile("+idCanile+")");
//		ResultSet rs = pst.executeQuery();
//		while (rs.next()){
//			BarcodeAnimale thisAnimale = new BarcodeAnimale (rs);
//			listAnimali.add(thisAnimale);
//		}
//		
//		//Cancello duplicati
//		ArrayList<BarcodeAnimale>  listAnimali2 = new ArrayList<BarcodeAnimale> ();
//		for (int i = 0; i<listAnimali.size();i++){
//			BarcodeAnimale thisAnimale = (BarcodeAnimale) listAnimali.get(i);
//			if (!contieneIdAnimale(thisAnimale.getIdAnimale(), listAnimali2)){
//				Timestamp maxDataPrelievo = null;
//				String maxEsito = null;
//				maxDataPrelievo = trovaMaxDataPrelievo(thisAnimale.getIdAnimale(), listAnimali);
//				maxEsito = trovaMaxEsito(thisAnimale.getIdAnimale(), listAnimali);
//				thisAnimale.setDataPrelievoLeish(maxDataPrelievo);
//				thisAnimale.setEsitoLeish(maxEsito);
//				listAnimali2.add(thisAnimale);
//			}
//		}
//
//		context.getRequest().setAttribute("listaAnimali", listAnimali2);
//		
//		Operatore canile = new Operatore();
//		canile.queryRecordOperatorebyIdLineaProduttiva(db, idCanile);
//	
//			
//	 	String path = getWebInfPath(context,"tmp_excel");
//	 	
//	 	Date data = new Date();
//	 	DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd_hh-MM-ss");
//	 	
//	 	File theDir = new File(path);
//	  	theDir.mkdirs();
//	    nome="Barcode_"+canile.getRagioneSociale().replaceAll(" ",  "")+"_"+dateFormatter.format(data.getTime())+".xlsx";
//	 	fullPath = path+nome;
//	 	ExcelBarcodeCanili.generaFoglio(fullPath, listAnimali2); 
//	     
//			
//	} catch (Exception e) {
//		e.printStackTrace();
//		context.getRequest().setAttribute("Error", e);
//		return ("SystemError");
//	} finally {
//		this.freeConnection(context, db);
//		
//	}
//
//	return downloadExcel(context, nome,fullPath);
//	
//}


private boolean contieneIdAnimale(int idAnimale,  ArrayList<BarcodeAnimale> listAnimali){
	for (int i = 0; i< listAnimali.size(); i++){
		BarcodeAnimale thisAnimale = (BarcodeAnimale)listAnimali.get(i);
		if (idAnimale == thisAnimale.getIdAnimale())
			return true;
	}
	return false;
}

private Timestamp trovaMaxDataPrelievo(int idAnimale,  ArrayList<BarcodeAnimale> listAnimali){
	Timestamp max = null;
	
	for (int i = 0; i< listAnimali.size(); i++){
		BarcodeAnimale thisAnimale = (BarcodeAnimale)listAnimali.get(i);
		
		if (thisAnimale.getIdAnimale()==idAnimale){
			if ( thisAnimale.getDataPrelievoLeish()!=null && ((max==null) ||  (thisAnimale.getDataPrelievoLeish().after(max))))     
				max = thisAnimale.getDataPrelievoLeish();
	}
		}
	return max;
}

private String trovaMaxEsito(int idAnimale,  ArrayList<BarcodeAnimale> listAnimali){
	Timestamp max = null;
	String esito = null;
	
	for (int i = 0; i< listAnimali.size(); i++){
		BarcodeAnimale thisAnimale = (BarcodeAnimale)listAnimali.get(i);
		if (thisAnimale.getIdAnimale()==idAnimale){
			if ( thisAnimale.getDataEsitoLeish()!=null && ((max==null) ||  (thisAnimale.getDataEsitoLeish().after(max)))){     
			max = thisAnimale.getDataEsitoLeish();
			esito = thisAnimale.getEsitoLeish();
			}
		}
	}
	return esito;
}

//public String downloadExcel(ActionContext context, String nomeFile, String pathFile)
//{
//		 String fileType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//         
//         
//      //  if (new File(fileName).exists()){
//         // Find this file id in database to get file name, and file type
//
//         // You must tell the browser the file type you are going to send
//         // for example application/pdf, text/plain, text/html, image/jpg
//         context.getResponse().setContentType(fileType);
//
//         // Make sure to show the download dialog
//         context.getResponse().setHeader("Content-disposition","attachment; filename=\""+nomeFile+"\"");
//
//         // Assume file name is retrieved from database
//         // For example D:\\file\\test.pdf
//         File inputFile = new File(pathFile);
//         File my_file = new File(inputFile.getAbsolutePath());
//
//         // This should send the file to browser
//         try
//         {	 OutputStream out = context.getResponse().getOutputStream();
//         	 
//	         FileInputStream in = new FileInputStream(my_file);
//	         byte[] buffer = new byte[4096];
//	         int length;
//	         while ((length = in.read(buffer)) > 0){
//	            out.write(buffer, 0, length);
//	         }
//	         in.close();
//	         out.flush();
//	         out.close();
//         }
//         catch(Exception ex)
//         {
//        	 ex.printStackTrace();
//        	 return "SystemError";
//         }
//         return ("-none-");	
//}


public String executeCommandStampaXLS2(ActionContext context) {
	
	if (!hasPermission(context, "barcode-canili-view")) {
		return ("PermissionError");
	}

	boolean filtriPianoLeish = false;
	if (context.getRequest().getParameter("leish")!=null && context.getRequest().getParameter("leish").equals("true"))
		filtriPianoLeish = true;
	
	Connection db = null;

	try {
		db = this.getConnection(context);
		int idCanile = -1;
		if ((String) context.getRequest().getParameter("idCanile") != null && !("").equals((String) context.getRequest().getParameter("idCanile")))
			idCanile =Integer.valueOf((String) context.getRequest().getParameter("idCanile"));
		
		ArrayList<BarcodeAnimale> listAnimali = new ArrayList<BarcodeAnimale>();
		
		PreparedStatement pst = null;
		
		if (!filtriPianoLeish)
			pst = db.prepareStatement("select * from get_barcode_cani_canile("+idCanile+")");
		else
			pst = db.prepareStatement("select * from get_barcode_cani_canile_leish("+idCanile+")");
		
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			BarcodeAnimale thisAnimale = new BarcodeAnimale (rs);
			listAnimali.add(thisAnimale);
		}
		
		//Cancello duplicati
		ArrayList<BarcodeAnimale>  listAnimali2 = new ArrayList<BarcodeAnimale> ();
		for (int i = 0; i<listAnimali.size();i++){
			BarcodeAnimale thisAnimale = (BarcodeAnimale) listAnimali.get(i);
			if (!contieneIdAnimale(thisAnimale.getIdAnimale(), listAnimali2)){
				Timestamp maxDataPrelievo = null;
				String maxEsito = null;
				maxDataPrelievo = trovaMaxDataPrelievo(thisAnimale.getIdAnimale(), listAnimali);
				maxEsito = trovaMaxEsito(thisAnimale.getIdAnimale(), listAnimali);
				thisAnimale.setDataPrelievoLeish(maxDataPrelievo);
				thisAnimale.setEsitoLeish(maxEsito);
				listAnimali2.add(thisAnimale);
			}
		}

		context.getRequest().setAttribute("listaAnimali", listAnimali2);
		
		Operatore canile = new Operatore();
		canile.queryRecordOperatorebyIdLineaProduttiva(db, idCanile);
		
		HttpServletResponse response = context.getResponse();
		response.setContentType("application/vnd.ms-excel");

	     response.setHeader("Content-Disposition", 
	    "attachment; filename="+canile.getRagioneSociale().replaceAll(" ",  "")+".xls");

	     WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream()); 
	     createXLS2(listAnimali, w, db);
		 
		
		
	} catch (Exception e) {
		e.printStackTrace();
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
		
	}

	return ("-none-");
}

public WritableWorkbook createXLS2(ArrayList<BarcodeAnimale> listaAnimali, WritableWorkbook w, Connection db){
	
	try{
	
	WritableSheet sheet = w.createSheet("CANI_IN_CANILE", 0);
	  
	
	sheet.setColumnView(0, 20);
	sheet.setColumnView(1, 20);
	sheet.setColumnView(2, 20);
	sheet.setColumnView(3, 50);
	sheet.setColumnView(4, 50);
	sheet.setColumnView(5, 20);
	sheet.setColumnView(6, 60);
	sheet.setColumnView(7, 60);
	sheet.setColumnView(8, 60);
	
	
	Label label = null;
	 
	int h = 0;
	int j = 0;
	 
	label = new Label(h++, j, "MICROCHIP",  getCellFormat(Colour.GREEN, Pattern.NONE));
	sheet.addCell(label);
	label = new Label(h++, j, "TATUAGGIO",  getCellFormat(Colour.GREEN, Pattern.NONE));
	sheet.addCell(label);
	label = new Label(h++, j, "SPECIE",  getCellFormat(Colour.GREEN, Pattern.NONE));
	sheet.addCell(label);
	label = new Label(h++, j, "PROPRIETARIO",  getCellFormat(Colour.GREEN, Pattern.NONE));
	sheet.addCell(label);
	label = new Label(h++, j, "DETENTORE",  getCellFormat(Colour.GREEN, Pattern.NONE));
	sheet.addCell(label);
	label = new Label(h++, j, "SESSO",  getCellFormat(Colour.GREEN, Pattern.NONE));
	sheet.addCell(label);
	label = new Label(h++, j, "MANTELLO",  getCellFormat(Colour.GREEN, Pattern.NONE));
	sheet.addCell(label);
	label = new Label(h++, j, "DATA_ULTIMO_PRELIEVO_LEISH",  getCellFormat(Colour.GREEN, Pattern.NONE));
	sheet.addCell(label);
	label = new Label(h++, j, "ULTIMO_ESITO_LEISH_DISPONIBILE",  getCellFormat(Colour.GREEN, Pattern.NONE));
	sheet.addCell(label);
	
	BarcodeAnimale thisAnimale = new BarcodeAnimale();
		
		for (int i = 0; i < listaAnimali.size(); i++) {
			thisAnimale = (BarcodeAnimale) listaAnimali.get(i);
			
			j++;
			h=0;
			//if (thisConvocato.getIdStatoPresentazione() == Convocato.convocato_non_presentato){ HO SOLO I PRESENTATI			
			label = new Label(h++, j, thisAnimale.getMicrochip());
			sheet.addCell(label);
			label = new Label(h++, j, thisAnimale.getTatuaggio());
			sheet.addCell(label);
			label = new Label(h++, j, thisAnimale.getSpecie());
			sheet.addCell(label);
			label = new Label(h++, j, thisAnimale.getProprietario());
			sheet.addCell(label);
			label = new Label(h++, j, thisAnimale.getDetentore());
			sheet.addCell(label);
			label = new Label(h++, j, thisAnimale.getSesso());
			sheet.addCell(label);
			label = new Label(h++, j, thisAnimale.getMantello());
			sheet.addCell(label);
			label = new Label(h++, j,  (thisAnimale.getDataPrelievoLeish()!=null) ? thisAnimale.getDataPrelievoLeish().toString() : "");
			sheet.addCell(label);
			label = new Label(h++, j,  (thisAnimale.getEsitoLeish()!=null) ? thisAnimale.getEsitoLeish() : "");
			sheet.addCell(label);
			
			//}
		}
		
		
	//Scrivo effettivamente tutte le celle ed i dati aggiunti
	w.write();
	 
	//Chiudo il foglio excel
	w.close();

	
}catch (Exception e) {
	e.printStackTrace();
}
return w;
}


// Create cell font and format
private static WritableCellFormat getCellFormat(Colour colour, Pattern pattern) throws WriteException {
  WritableFont cellFont = new WritableFont(WritableFont.TIMES, 16);
  WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
  cellFormat.setBackground(colour, pattern);
  return cellFormat;
}


}
