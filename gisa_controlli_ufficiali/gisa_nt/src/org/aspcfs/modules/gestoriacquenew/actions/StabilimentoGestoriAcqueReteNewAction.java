/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestoriacquenew.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneExcel.actions.GestioneExcel;
import org.aspcfs.modules.gestoriacquenew.base.ControlloInterno;
import org.aspcfs.modules.gestoriacquenew.base.GestoreAcque;
import org.aspcfs.modules.gestoriacquenew.base.GestoreNotFoundException;
import org.aspcfs.modules.gestoriacquenew.base.PuntoPrelievo;
import org.aspcfs.modules.gestoriacquenew.base.StoricoImport;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;


public class StabilimentoGestoriAcqueReteNewAction extends CFSModule {

 
	
	
	/*entry point dal cavaliere */
	/*ottengo corrispondenza tra account e il gestore */
	public String executeCommandDefault(ActionContext context) 
	{
		boolean utenteRegione = (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.role_admin_ext) || (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_ORSA) || (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_REGIONE)|| (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.HD_1LIVELLO)|| (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.HD_2LIVELLO);
		Connection conn = null;
		
		
		try
		{
			conn = getConnection(context);
			if(utenteRegione)
			{
				context.getRequest().getSession().removeAttribute("GestoreAcque");/*per evitare che i dati siano obsoleti nel caso in cui venga cancellato gestore ma rimanga in sessione questo */
				GestoreAcque gestore = new GestoreAcque();
				gestore.setId(-999);
				gestore.puntiPrelievo = PuntoPrelievo.searchChunkPerGestore(conn,-1, 0,false);
				context.getRequest().getSession().setAttribute("GestoreAcque",gestore);
				return executeCommandPaginaPerListaPuntiPrelievo(context);
			}
			else
			{
				return executeCommandPaginaPerListaDatiGestoreAcque(context);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "SystemError";
		}
		finally
		{
			this.freeConnection(context, conn);
		}
		
	}
	
	
	
	

	/*manda alla pagina di riepilogo dei dati del gestore associato all'utente */
	public String executeCommandPaginaPerListaDatiGestoreAcque(ActionContext context)
	{
		
		if (!hasPermission(context, "gestoriacquenew-view")) {
	        return ("PermissionError");
		}
		 
		
		Connection conn = null;
		int userId = -1;
		String viewName = "DatiGestoreAcque";
		boolean utenteRegione = (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.role_admin_ext) || (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_ORSA) || (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_REGIONE)|| (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.HD_1LIVELLO)|| (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.HD_2LIVELLO);
		
		
		try
		{
			conn = getConnection(context);
			userId = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId();
			 
			if(utenteRegione)
			{
				ArrayList<GestoreAcque> gestori = GestoreAcque.getAllGestori(conn, 0, false);
				context.getRequest().setAttribute("gestori",gestori);
			}
			else
			{
				context.getRequest().getSession().removeAttribute("GestoreAcque");/*per evitare che i dati siano obsoleti nel caso in cui venga cancellato gestore ma rimanga in sessione questo */
				GestoreAcque gestore = new GestoreAcque(userId, conn, 0,false);
				context.getRequest().getSession().setAttribute("GestoreAcque",gestore);
			}
			
			 
		}
		catch(GestoreNotFoundException ex)
		{
			System.out.println("GESTORE DI ACQUE NON TROVATO PER L'USER ID "+userId);
			/*non valorizzo l'attributo di sessione gestore */
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName = "SystemError";
		}
		finally
		{
			this.freeConnection(context, conn);
		}
		
		return viewName;
	}
	
	
	public String executeCommandSelezionaGestore(ActionContext context)
	{
		
		if (!hasPermission(context, "gestoriacquenew-view")) {
	        return ("PermissionError");
		}
		 
		
		Connection conn = null;
		String idGestoreString = null;
		int idGestore = -1;
		String viewName = "DatiGestoreAcque";
		
		
		try
		{
			conn = getConnection(context);
			idGestoreString = context.getRequest().getParameter("idGestore");
			if(idGestoreString!=null)
				idGestore = Integer.parseInt(idGestoreString);
			context.getRequest().getSession().removeAttribute("GestoreAcque"); /*per evitare che i dati siano obsoleti nel caso in cui venga cancellato gestore ma rimanga in sessione questo */
			
			GestoreAcque gestore = null;
			if(idGestore==-999)
			{
				gestore = new GestoreAcque();
				gestore.setId(-999);
				gestore.puntiPrelievo = PuntoPrelievo.searchChunkPerGestore(conn,-1, 0,false);
			}
			else
			{
				gestore = new GestoreAcque(idGestore, conn, 0);
			}
			 /*passo 0 come indice chunk tanto neanche mi interessano i punti prelievo ma solo info gestore */
			context.getRequest().getSession().setAttribute("GestoreAcque",gestore);
			
			ArrayList<GestoreAcque> gestori = GestoreAcque.getAllGestori(conn, 0, false);
			context.getRequest().setAttribute("gestori",gestori);
			 
		}
		catch(GestoreNotFoundException ex)
		{
			System.out.println("GESTORE DI ACQUE NON TROVATO PER ID GESTORE  "+idGestore);
			/*non valorizzo l'attributo di sessione gestore */
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName = "SystemError";
		}
		finally
		{
			this.freeConnection(context, conn);
		}
		
		return viewName;
	}
	
	
	
	
	
	/*manda alla pagina dove viene elencata la lista dei punti di prelievo per il gestore associato all'utente*/
	public String executeCommandPaginaPerListaPuntiPrelievo(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-view")) {
	        return ("PermissionError");
		}
		
		String viewName = "ListaPuntiPrelievo";
		Connection conn = null;
		int userId = -1;
		boolean utenteRegione = (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.role_admin_ext) || (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_ORSA) || (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_REGIONE)|| (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.HD_1LIVELLO)|| (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.HD_2LIVELLO);
		
		context.getRequest().setAttribute("idAsl", context.getRequest().getParameter("idAsl"));
		context.getRequest().setAttribute("idGestore", context.getRequest().getParameter("idGestore"));
		
		try
		{
			conn = getConnection(context);
			/*ottengo il parametro che mi dice il limite di risultati da mostrare. Se manca, allora e' di default il primo chunk */
			Integer indiceChunkRichiesto = context.getRequest().getParameter("indiceChunkPuntiPrelievo") != null ? Integer.parseInt(context.getRequest().getParameter("indiceChunkPuntiPrelievo")) : 0;
			userId = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId();
			
			
			if(utenteRegione)
			{
				//Ricarico in sessione per aggironare la lista dei punti di prelievo eventualemnte aggiunti nel frattempo, altrimenti questi vengono caricati solo quando si clicca sul cavaliere
				context.getRequest().getSession().removeAttribute("GestoreAcque");/*per evitare che i dati siano obsoleti nel caso in cui venga cancellato gestore ma rimanga in sessione questo */
				GestoreAcque gestore = new GestoreAcque();
				gestore.setId(-999);
				gestore.puntiPrelievo = PuntoPrelievo.searchChunkPerGestore(conn,-1, 0,false);
				context.getRequest().getSession().setAttribute("GestoreAcque",gestore);
				//Fine ricaricamento
				
				ArrayList<GestoreAcque> gestori = GestoreAcque.getAllGestori(conn, 0, false);
				context.getRequest().setAttribute("gestori",gestori);
			}
			else
			{
				GestoreAcque gestore = new GestoreAcque(userId,conn,indiceChunkRichiesto,false);
				context.getRequest().getSession().setAttribute("GestoreAcque",gestore);
			}
			
			LookupList lookup_asl = new LookupList(conn, "lookup_site_id");
			lookup_asl.addItem(-1, "TUTTI");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);
			
			context.getRequest().setAttribute("indiceChunkPuntiPrelievo", indiceChunkRichiesto); /*lo rimando dietro al client */
			
			 
		}
		catch(GestoreNotFoundException ex)
		{
			System.out.println("GESTORE DI ACQUE NON TROVATO PER L'USER ID "+userId);
			/*non valorizzo l'attributo di sessione gestore */
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName = "SystemError";
		}
		finally
		{
			this.freeConnection(context, conn);
		}
		
		return viewName;
	}
	
	
	public String executeCommandEstrazionePuntiPrelievo(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-view")) {
	        return ("PermissionError");
		}
		
		int userId = -1;
		boolean utenteRegione = (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.role_admin_ext) || (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_ORSA) || (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_REGIONE)|| (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.HD_1LIVELLO)|| (((UserBean)context.getRequest().getSession().getAttribute("User")).getRoleId()==org.aspcfs.modules.admin.base.Role.HD_2LIVELLO);
		
		try
		{
			/*ottengo il parametro che mi dice il limite di risultati da mostrare. Se manca, allora e' di default il primo chunk */
			
			GestoreAcque gestore = (GestoreAcque)context.getRequest().getSession().getAttribute("GestoreAcque");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");
            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("Asl");
            rowhead.createCell(1).setCellValue("Comune");
            rowhead.createCell(2).setCellValue("Latitudine");
            rowhead.createCell(3).setCellValue("Longitudine");
            rowhead.createCell(4).setCellValue("Indirizzo");
            rowhead.createCell(5).setCellValue("Denominazione");
            rowhead.createCell(6).setCellValue("Tipologia");
            rowhead.createCell(7).setCellValue("Stato");
            rowhead.createCell(8).setCellValue("Codice Gisa");
            //rowhead.createCell(9).setCellValue("Codice");
            rowhead.createCell(9).setCellValue("Data inserimento");
            if(utenteRegione)
            	rowhead.createCell(10).setCellValue("Gestore");
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            int i = 1;
			for(PuntoPrelievo pp : gestore.getPuntiPrelievo())
			{
	            rowhead = sheet.createRow((short)i);
	            
	            rowhead.createCell(0).setCellValue(pp.getDescrizioneAsl());
	            rowhead.createCell(1).setCellValue(pp.getIndirizzo().getDescrizioneComune());
	            rowhead.createCell(2).setCellValue(pp.getIndirizzo().getLatitudine());
	            rowhead.createCell(3).setCellValue(pp.getIndirizzo().getLongitudine());
	            rowhead.createCell(4).setCellValue(pp.getIndirizzo().toString());
	            rowhead.createCell(5).setCellValue(pp.getDenominazione());
	            rowhead.createCell(6).setCellValue(pp.getDescrizioneTipologia());
	            rowhead.createCell(7).setCellValue(pp.getStato());
	            rowhead.createCell(8).setCellValue(pp.getCodiceGisa());
	            //rowhead.createCell(9).setCellValue(pp.getCodice());
	            rowhead.createCell(9).setCellValue(sdf.format(pp.getDataInserimento()));
	            if(utenteRegione)
	            	rowhead.createCell(10).setCellValue(pp.getNomeGestore());
	            i++;
			}
			
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            workbook.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			context.getResponse().setContentType("application/vnd.ms-excel");
			context.getResponse().setContentLength(outArray.length);
			context.getResponse().setHeader("Expires:", "0");
			context.getResponse().setHeader("Content-Disposition", "attachment; filename=estrazione_punti_prelievo.xls");
			
			java.io.OutputStream outStream = context.getResponse().getOutputStream();
			workbook.close();
			outStream.write(outArray);
			outStream.flush();
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "SystemError";
		}
		
		return "-none-";
	}
	
	public String executeCommandStoricoImport(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-view")) {
	        return ("PermissionError");
		}
		
		String viewName = "StoricoImport";
		Connection conn = null;
		int userId = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId();;
		try
		{
			conn = getConnection(context);
			Integer indiceChunkRichiesto = context.getRequest().getParameter("indiceChunkPuntiPrelievo") != null ? Integer.parseInt(context.getRequest().getParameter("indiceChunkPuntiPrelievo")) : 0;
			userId = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId();
			StoricoImport storico = new StoricoImport();
			ArrayList<StoricoImport> storicoImportList = storico.getStoricoUtente(conn, userId);
			context.getRequest().setAttribute("storicoImportList", storicoImportList); 
			 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName = "SystemError";
		}
		finally
		{
			this.freeConnection(context, conn);
		}
		
		return viewName;
	}
	
	public String executeCommandVediErrori(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-view")) {
	        return ("PermissionError");
		}
		
		String viewName = "VediErrori";
		Connection conn = null;
		try
		{
			conn = getConnection(context);
			Integer idImport = Integer.parseInt(context.getRequest().getParameter("idImport"));
			StoricoImport storico = new StoricoImport();
			storico = storico.getStorico(conn, idImport);
			
			context.getRequest().setAttribute("erroreInsert", storico.getErroreInsert()); 
			context.getRequest().setAttribute("erroreParsingFile", storico.getErroreParsingFile()); 
			 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName = "SystemError";
		}
		finally
		{
			this.freeConnection(context, conn);
		}
		
		return viewName;
	}
	
	
	/*rimanda alla pagina di dettaglio di un punto di prelievo */
	public String executeCommandPuntoPrelievoDetail(ActionContext context)
	{
		String viewName = "PuntoPrelievoDetail";
		Connection db = null;
		try
		{ 
			
			db=getConnection(context);
			executeCommandPaginaPerListaDatiGestoreAcque(context); /*questo carica come attributo di SESSION il gestore */
			/*riprendo il gestore e carico come attributo di request il punto di prelievo */
			GestoreAcque gest =(GestoreAcque) context.getRequest().getSession().getAttribute("GestoreAcque");
			int idPuntoPrelievoRichiesto = Integer.parseInt(context.getRequest().getParameter("idPuntoPrelievo"));
			
			context.getRequest().setAttribute("idPuntoPrelievo", idPuntoPrelievoRichiesto );
			
			int idGestore = gest.getId();
			String idGestoreString = (String)context.getRequest().getAttribute("idGestore");
			if(idGestoreString!=null && !idGestoreString.equals(""))
				idGestore = Integer.parseInt(idGestoreString);
			
			
			PuntoPrelievo pp = null;
			if(idGestore==-999)
			{
				pp = GestoreAcque.searchPuntoRaccoltaById(db, -1, idPuntoPrelievoRichiesto,false);
			}
			else
			{
				pp = GestoreAcque.searchPuntoRaccoltaById(db, idGestore, idPuntoPrelievoRichiesto,false);
			}
			
			context.getRequest().setAttribute("PuntoPrelievoRichiesto", pp);
			
			//Nel caso il command viene chiamato da executeCommandPuntoPrelievoModifyStato
			context.getRequest().setAttribute("esitoModifyStato", context.getRequest().getAttribute("esitoModifyStato"));
		}
		catch(Exception ex)
		{
		
			ex.printStackTrace();
			viewName ="SystemError";
		}
		finally
		{
			freeConnection(context,db);
		}
		return viewName;
	}
	
	
	/*rimanda alla pagina di dettaglio di un punto di prelievo */
	public String executeCommandPuntoPrelievoModifyStato(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-edit")) {
	        return ("PermissionError");
		}
		String viewName = "PuntoPrelievoDetail";
		Connection db = null;
		try
		{ 
			db=getConnection(context);
			
			UserBean thisUser = ((UserBean)context.getRequest().getSession().getAttribute("User"));
			
			executeCommandPaginaPerListaDatiGestoreAcque(context); /*questo carica come attributo di SESSION il gestore */
			/*riprendo il gestore e carico come attributo di request il punto di prelievo */
			GestoreAcque gest =(GestoreAcque) context.getRequest().getSession().getAttribute("GestoreAcque");
			int idPuntoPrelievoRichiesto = Integer.parseInt(context.getRequest().getParameter("idPuntoPrelievo"));
			
			context.getRequest().setAttribute("idPuntoPrelievo", idPuntoPrelievoRichiesto );
			
			int idGestore = gest.getId();
			String idGestoreString = (String)context.getRequest().getAttribute("idGestore");
			if(idGestoreString!=null && !idGestoreString.equals(""))
				idGestore = Integer.parseInt(idGestoreString);
			
			System.out.println("ID GESTORE: " + idGestore);
			PuntoPrelievo pp = null;
			if(idGestore==-999)
			{
				pp = GestoreAcque.searchPuntoRaccoltaById(db, -1, idPuntoPrelievoRichiesto,false);
			}
			else
			{
				pp = GestoreAcque.searchPuntoRaccoltaById(db, idGestore, idPuntoPrelievoRichiesto,false);
			}
			
			
			
			
			
			String esitoModifyStato = pp.modifyStato(db, thisUser);
			context.getRequest().setAttribute("esitoModifyStato", esitoModifyStato);
		}
		catch(Exception ex)
		{
		
			ex.printStackTrace();
			viewName ="SystemError";
		}
		finally
		{
			freeConnection(context,db);
		}
		return executeCommandPuntoPrelievoDetail(context);
	}
	
	
	
	
	/*rimanda alla pagina dove mostra i controlli associati ad un singolo punto di prelievo */
	public String executeCommandListControlliInterniPuntoPrelievo(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-view")) {
			return ("PermissionError");
		}
		
		String viewName = "listControlliInterniPerPuntoPrelievo";
		Connection db = null;
		try
		{
			db=getConnection(context);
			executeCommandPaginaPerListaDatiGestoreAcque(context); /*questo carica come attributo di SESSION il gestore */
			/*riprendo il gestore e carico come attributo di request il punto di prelievo */
			int idPuntoPrelievoRichiesto = Integer.parseInt(context.getRequest().getParameter("idPuntoPrelievo"));
			
			//Recupero info sul punto di prelievo
			PuntoPrelievo pp = PuntoPrelievo.searchById(db, idPuntoPrelievoRichiesto,false);
			context.getRequest().setAttribute("PuntoPrelievoRichiesto", pp);
			
			//Recupero in lista controlli per decreto 31 e 28
			ArrayList<ControlloInterno> controlli31 = (ArrayList<ControlloInterno>)ControlloInterno.searchAllPerPuntoPrelievo(db, idPuntoPrelievoRichiesto,"31");
			ArrayList<ControlloInterno> controlli28 = (ArrayList<ControlloInterno>)ControlloInterno.searchAllPerPuntoPrelievo(db, idPuntoPrelievoRichiesto,"28");
			context.getRequest().setAttribute("controlli31", controlli31);
			context.getRequest().setAttribute("controlli28", controlli28);
		}
		catch(Exception ex)
		{
		
			ex.printStackTrace();
			viewName ="SystemError";
		}
		finally
		{
			freeConnection(context,db);
		}
		return viewName;
		
		
	}
	
	
	/*rimanda alla pagina di dettaglio di un singolo controllo per un dato punto di prelievo */
	public String executeCommandDettaglioControlloInterno(ActionContext context)
	{
		Connection db = null;
		HttpServletRequest req = context.getRequest();
		HttpServletResponse resp = context.getResponse();
		String viewName = "dettaglioCI";
		
		try
		{
			db=getConnection(context);
			
			int idPuntoPrelievo = Integer.parseInt(req.getParameter("idPuntoPrelievo"));
			int idControlloInterno = Integer.parseInt(req.getParameter("idControlloInterno"));
			PuntoPrelievo pp = PuntoPrelievo.searchById(db, idPuntoPrelievo,true);
			req.setAttribute("PuntoPrelievoRichiesto", pp);
			
			ControlloInterno ci = ControlloInterno.searchByOid(db, idControlloInterno);
			
			if(ci == null)
			{
				viewName ="SystemError"; 
			}
			else
			{
				viewName+=ci.getTipoDecreto();
				req.setAttribute("ControlloRichiesto", ci);
			}
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		
		return viewName;
	}
	 
	
	
	
	/*manda alla pagina dove c'e' la maschera per l'import massivo dei controlli interni per i vari punti di prelievo
	 * associati al gestore associato all'utente */
	public String executeCommandPaginaPerAggiuntaControlliGestoreSpecifico(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-add")) {
	        return ("PermissionError");
		}
	
		String viewName = "AggiuntaControlliInterniGestoreSpecifico";
		
		return viewName;
	}
	
	
	
	/*richiamata dalla pagina dell'import dei controlli interni per i punti associati al gestore associato all'utente
	 **/
	public String executeCommandAggiuntaControlliInterniEffettivo(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-add")) {
	        return ("PermissionError");
		}
		
		String viewName = new GestioneExcel().executeCommandPutExcel(context);
		
		return viewName;
	}
	
	
	
	
	
	/*rimanda alla maschera di import punti di prelievo specifici per il gestore associato all'utente */
	public String executeCommandPaginaPerAggiuntaPuntiPrelievoGestoreSpecifico(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-add")) {
	        return ("PermissionError");
		}
		
		String viewName = "AggiuntaPuntiPrelievoGestoreSpecifico";
		
		return viewName;
	}
	
	
	/*richiamato dalla maschera di aggiunta punti di prelievo per il gestore associato all'utente, quando viene fatto effettivo upload del file excel */
	public String executeCommandAggiuntaPuntiPrelievoGestoreSpecificoEffettivo(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew-add")) {
	        return ("PermissionError");
		}
		
		String viewname = new GestioneExcel().executeCommandPutExcel(context); /*qui viene messo il msg di ritorno */
		
		return viewname;
	}
	
	
	
	
	
	
	
	
	/*rimanda alla pagina dove e' possibile importare tutte le anagrafiche dei gestori
	 * e/o tutti i punti di prelievo per tutti i gestori
	 */
	public String executeCommandPaginaPerImportMassivo(ActionContext context)
	{
		
		if (!hasPermission(context, "gestoriacquenew_importgestorimassivo-add")) {
	        return ("PermissionError");
		}
		
		Connection db = null;
		String viewName = "ImportMassivo";
		
		try
		{
			db = getConnection(context);
			ArrayList<GestoreAcque> tuttiGestGiaImport = GestoreAcque.getAllGestoriConUtenteAssociato(db,0,false);
			context.getRequest().getSession().setAttribute("TuttiGestoriImportatiConUtentiEventuali",tuttiGestGiaImport);
		}
		catch(Exception ex)
		{
			
		}
		finally
		{
			freeConnection(context,db);
		}
		
		
		return viewName;
	}
	
	
	
	/*richiamato dalla pagina dove si fa import massivo di tutti i dati di tutti i gestori
	 * e tutti i punti di prelievo di tutti i gestori, quando si fa upload del file contente tutti i punti di prelievo di tutti i gestori
	 */
	public String executeCommandImportPuntiPrelievoTuttiGestoriEffettivo(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew_importgestorimassivo-add")) {
	        return ("PermissionError");
		}
		
		String viewname = new GestioneExcel().executeCommandPutExcel(context); /*qui viene messo il msg di ritorno */
		return viewname;
	}
	
	
	
	/*richiamato dalla pagina dove si fa import massivo di tutti i dati di tutti i gestori
	 * e tutti i punti di prelievo di tutti i gestori, quando si fa upload del file contente tutti i controlli per tutti i punti di prelievo
	 * di tutti i gestori
	 */
	public String executeCommandImportDatiAnagraficiTuttiGestoriEffettivo(ActionContext context)
	{
		if (!hasPermission(context, "gestoriacquenew_importgestorimassivo-add")) {
	        return ("PermissionError");
		}
		String viewname = new GestioneExcel().executeCommandPutExcel(context); /*qui viene messo il msg di ritorno */
		
		
		
		return viewname;
	}
	
	
	public String executeCommandDownloadImport(ActionContext context)
	{
		int idImport=-1;
		String path_server="", titolo="";
		idImport = Integer.parseInt(context.getRequest().getParameter("idImport"));
		Connection db = null;
		
		try 
		{
			db = this.getConnection(context);
			
			StoricoImport sDao = new StoricoImport();
			StoricoImport s = sDao.getStorico(db, idImport);
	        
			titolo = s.getNomeFile();
	        String fileType = s.getNomeFile().split("\\.")[1];
	        String fileName = s.getNomeFileCompleto();
	         
	        if (new File(fileName).exists())
	        {
	        	 context.getResponse().setContentType(fileType);
	        	 context.getResponse().setHeader("Content-disposition","attachment; filename="+titolo);
	
	        	 File my_file = new File(fileName);
	
	        	 OutputStream out =   context.getResponse().getOutputStream();
	        	 FileInputStream in = new FileInputStream(my_file);
	        	 byte[] buffer = new byte[4096];
	        	 int length;
	        	 while ((length = in.read(buffer)) > 0)
	        	 {
	        		 out.write(buffer, 0, length);
	        	 }
	        	 in.close();
	        	 out.flush();
	         }
	         else
	         {
	        	 PrintWriter out =   context.getResponse().getWriter();
	        	 out.println("File non trovato!");
	        	 out.println(fileName);
	        	 out.println("Si e' verificato un problema con il recupero del file. Si prega di contattare l'HelpDesk.");
	          }
	        
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		} catch (GestoreNotFoundException e) 
		{
			e.printStackTrace();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
				
		return ("-none-");	
		
	}
	
}

