/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.izsmibr.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiInvii;
import org.aspcfs.modules.izsmibr.base.PrelievoPNAA;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;

public class GestioneInvioPNAA extends CFSModule{

	private static final int MAX_SIZE_REQ = 50000000;

	public String executeCommandToImport(ActionContext context)
	{
		return "ToImportOK";
	}


	public String executeCommandImport(ActionContext context) throws Exception
	{

		if (!hasPermission(context, "izsm-pnaa-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		int idImport = -1;

		try {
			db = this.getConnection(context);
			idImport = PrelievoPNAA.getMaxIdImport(db)+1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}

		System.out.flush();
		stampaLog("[PNAA] ######## Inizio procedura di analisi del file. ######## ");

		//BLOCCO #1: LETTURA DEL FILE 


		String filePath = this.getPath(context, "tmp_pnaa");
		String fileName = "";
		HttpMultiPartParser multiPart = new HttpMultiPartParser();

		HashMap parts = null;
		try {
			parts = multiPart.parseData(context.getRequest(), filePath);
		} catch (IllegalArgumentException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


		String xlsFile = "";
		String csvFile = "";

		if ((Object)  parts.get("file") instanceof FileInfo) {
			//Update the database with the resulting file
			FileInfo newFileInfo = (FileInfo) parts.get("file");
			//Insert a file description record into the database
			com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
			thisItem.setLinkModuleId(Constants.ACCOUNTS);
			thisItem.setEnteredBy(getUserId(context));
			thisItem.setModifiedBy(getUserId(context));
			thisItem.setClientFilename(newFileInfo.getClientFileName());
			thisItem.setFilename(newFileInfo.getRealFilename());
			fileName = newFileInfo.getRealFilename();
			thisItem.setVersion(1.0);
			thisItem.setSize(newFileInfo.getSize());
			xlsFile = thisItem.getFullFilePath();
		}

		xlsFile = filePath + fileName;



		/*ALLEGO IL FILE INVOCANDO IL DOCUMENTALE*/
		stampaLog("[INVIO PNAA] Carico file sul documentale.");

		/*ALLEGO IL FILE INVOCANDO IL DOCUMENTALE*/
		GestioneAllegatiInvii gestioneAllegati = new GestioneAllegatiInvii();
		gestioneAllegati.setIdInvio(idImport);
		gestioneAllegati.setTipoAllegato("PNAACA");
		gestioneAllegati.setOggetto("INVIO PRELIEVI_PNAA_"+idImport);
		gestioneAllegati.setFileDaCaricare(new File(xlsFile));
		try {
			gestioneAllegati.allegaFile(context, getUserId(context));
		} catch (IllegalStateException | SQLException | ServletException | FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				
		stampaLog("[INVIO PNAA] Inizio lettura del file.");


		String excelFilePath = xlsFile;
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		org.apache.poi.ss.usermodel.Workbook workbook =  WorkbookFactory.create(inputStream);
		org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);

		Iterator<Row> iterator = firstSheet.iterator();

		int rigaIniziale = 1;
		int contatoreRiga= 0;
		iterator = firstSheet.iterator();

		ArrayList<PrelievoPNAA> prelieviPNAA = new   ArrayList<PrelievoPNAA>();

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();

			if (contatoreRiga >= rigaIniziale) {
				stampaLog("[INVIO PNAA] Foglio 1 Leggo riga #"+contatoreRiga+".");

				PrelievoPNAA pnaa = new PrelievoPNAA();
				pnaa.completaDaFoglio(nextRow);
				pnaa.setIdImport(idImport);
				prelieviPNAA.add(pnaa);            	
			}
			contatoreRiga++;
		}
		
		stampaLog("[INVIO PNAA] Letto fino a riga: "+contatoreRiga+".");

		workbook.close();
		inputStream.close();

		stampaLog("[INVIO PNAA] Letto fino a riga: "+contatoreRiga+".");

		//BLOCCO #2: SCRITTURA DEI RECORD SUL DB

		for (int i = 0; i<prelieviPNAA.size(); i++){

			PrelievoPNAA pnaa = (PrelievoPNAA) prelieviPNAA.get(i);

			try {
				db =this.getConnection(context);
				stampaLog("[INVIO PNAA] Leggo da file prelievo ("+(i+1)+"/"+prelieviPNAA.size()+ " per Numero Scheda: "+pnaa.getNumeroScheda() +".");
				pnaa.setEnteredBy(getUserId(context));
				pnaa.insert(db);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {this.freeConnection(context, db);
			}


		}

		//BLOCCO #3: INVIO

		for (int i = 0; i<prelieviPNAA.size(); i++){

			PrelievoPNAA pnaa = (PrelievoPNAA) prelieviPNAA.get(i);

			try {
				db =this.getConnection(context);
				pnaa.invio(db, getUserId(context));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {this.freeConnection(context, db);
			}


		}

		context.getRequest().setAttribute("idImport", String.valueOf(idImport)); 
		return executeCommandListaInvii(context);


	}


	public String executeCommandListaInvii(ActionContext context) throws Exception
	{

		if (!hasPermission(context, "izsm-pnaa-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		int idImport = -1;

		try {idImport = Integer.parseInt(context.getRequest().getParameter("idImport"));} catch (Exception e){};
		if (idImport==-1)
			try {idImport = Integer.parseInt((String)context.getRequest().getAttribute("idImport"));} catch (Exception e){};

			try {
				db = this.getConnection(context);
				ArrayList<PrelievoPNAA> listaInvii = new ArrayList<PrelievoPNAA>();
				listaInvii = PrelievoPNAA.buildListaInvii(db, idImport);
				context.getRequest().setAttribute("listaInvii", listaInvii);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				this.freeConnection(context, db);
			}

			return "ListaInviiOK";

	}

	public String executeCommandListaInviiMassivi(ActionContext context) throws Exception
	{

		if (!hasPermission(context, "izsm-pnaa-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		int idImport = -1;

		try {
			db = this.getConnection(context);
			ArrayList<PrelievoPNAA> listaInvii = new ArrayList<PrelievoPNAA>();
			listaInvii = PrelievoPNAA.buildListaInviiMassivi(db);
			context.getRequest().setAttribute("listaInvii", listaInvii);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}

		return "ListaInviiMassiviOK";

	}

	public static void stampaLog(String text){
		System.out.println(text);

		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String data = sdf.format(cal.getTime());

		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter("log_pnaa.txt", true));

			bufferedWriter.write(data+" "+text);
			bufferedWriter.write(System.lineSeparator());

			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
