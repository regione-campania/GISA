/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.devdoc.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.devdoc.base.Flusso;
import org.aspcfs.modules.devdoc.base.FlussoList;
import org.aspcfs.modules.devdoc.base.Modulo;
import org.aspcfs.modules.devdoc.base.ModuloList;
import org.aspcfs.modules.devdoc.base.ModuloNote;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiModuli;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoModuloList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.meeting.action.GestioneRiunioni;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;

public class GestioneFlussoSviluppo extends CFSModule{

	Logger logger = Logger.getLogger(GestioneFlussoSviluppo.class);



	private static final int MAX_SIZE_REQ = 50000000;


	public String executeCommandDashboard(ActionContext context)
	{
		
		if (!hasPermission(context, "devdoc-view")) {
			GestioneRiunioni gr = new GestioneRiunioni();
		    return gr.executeCommandSearchForm(context);
		}
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			LookupList listaTipiModulo = new LookupList(db,"lookup_tipo_modulo_sviluppo");
			context.getRequest().setAttribute("listaTipiModulo", listaTipiModulo);

			FlussoList listaFlussi = new FlussoList();
			listaFlussi.buildList(db);
			context.getRequest().setAttribute("listaFlussi", listaFlussi);
			
			LookupList listaTipiPriorita = new LookupList(db,"lookup_priorita_flusso");
			context.getRequest().setAttribute("listaTipiPriorita", listaTipiPriorita);
			
			String idModulo = null;
			idModulo = context.getRequest().getParameter("idModulo");
			if (idModulo==null)
				idModulo = (String)context.getRequest().getAttribute("idModulo");
			context.getRequest().setAttribute("idModulo", idModulo);
			
			String idFlusso = null;
			idFlusso = context.getRequest().getParameter("idFlusso");
			if (idFlusso==null)
				idFlusso = (String)context.getRequest().getAttribute("idFlusso");
			context.getRequest().setAttribute("idFlusso", idFlusso);

			context.getRequest().setAttribute("Errore", (String)context.getRequest().getAttribute("Errore"));

		}
		catch(Exception e)
		{
			logger.error("Gestione Sviluppo Moduli Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "dashboardOK";
	}
	
	public String executeCommandInsert(ActionContext  context)
	{
		if (!hasPermission(context, "devdoc-mod-a-add") &&
			!hasPermission(context, "devdoc-mod-b-add") &&
			!hasPermission(context, "devdoc-mod-c-add") &&
			!hasPermission(context, "devdoc-mod-ch-add") &&
			!hasPermission(context, "devdoc-mod-d-add") &&
			!hasPermission(context, "devdoc-mod-vce-add")
		) 
			return ("PermissionError");
	
		Connection db = null ;
		try
		{

			db = this.getConnection(context);

			LookupList listaTipiModulo = new LookupList(db,"lookup_tipo_modulo_sviluppo");
			
//			String filePath = this.getPath(context, "modulisviluppo");
//			File theDir = new File(filePath);
//	        theDir.mkdirs();
			
			String filePath = getWebInfPath(context,"tmp_moduli_sviluppo");

			MultipartRequest multi = new MultipartRequest(context.getRequest(), filePath,MAX_SIZE_REQ,"UTF-8");


			db = this.getConnection(context);
			
			Flusso flusso = new Flusso();
			flusso.setIdFlusso(multi.getParameter("idFlusso"));
			flusso.setDescrizione(multi.getParameter("descrizione"));
			flusso.setTags(multi.getParameter("tags"));
			flusso.gestioneInserimento(db);
			
			Modulo modulo = new Modulo();
			modulo.setIdTipo(multi.getParameter("idTipo"));
			modulo.setIdFlusso(flusso.getIdFlusso());
			modulo.setIdUtente(getUserId(context));
			
			boolean invioMailAbilitato = multi.getParameter("flag-invio-mail") != null ? true : false;
			
			boolean moduloInseribileFlussoAperto = flusso.isFlussoAperto(db);
			boolean moduloInseribileModuloDisponibile= modulo.isModuloDisponibile(db);
			
			if (moduloInseribileFlussoAperto && moduloInseribileModuloDisponibile){
							
				modulo.gestioneInserimento(db);
				
				modulo.queryRecord(db, modulo.getId());
	
				UserBean u =((UserBean) context.getRequest().getSession().getAttribute("User"));
				
				//String toDest="gisadev@u-s.it;gisaref@u-s.it;orsacampania@izsmportici.it;izshd@u-s.it";
				String toDest=ApplicationProperties.getProperty("DEST_EMAIL_FLUSSO_SVILUPPO");
				
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				String testo = "E' stato inserito un nuovo modulo nel flusso documentale di sviluppo.<br/><br/>"
						+ "Descrizione: "+ flusso.getDescrizione()+"<br/><br/>"
						+ "Modulo: "+listaTipiModulo.getSelectedValue(modulo.getIdTipo())+"<br/>"
						+ "Flusso: "+flusso.getIdFlusso()+"<br/>"
						+ "Data: "+ dateFormat.format(modulo.getData())+" <br/>"
						+ "Inserito da: "+u.getUsername(); 
				
				File allegato = null;
				
				try {allegato =		(File) multi.getFile("file1"); } catch (Exception e) {}
				if (invioMailAbilitato && modulo.getIdTipo() != 6) //6 E' l'id del modulo tipo VCE
					sendMailNuovoModulo(context.getRequest(), testo, "##GISA GESTIONE FLUSSO SVILUPPO## - "+flusso.getDescrizione(), toDest, allegato);
	
				caricaFile(context, modulo, db, multi);
			}
			else {
				String errore = "Impossibile caricare il modulo in oggetto. ";
				
				if (!moduloInseribileFlussoAperto)
					errore+=" Il flusso di riferimento risulta chiuso e consegnato.";
				else if (!moduloInseribileModuloDisponibile)	
					errore+=" Il modulo risulta segnato come NON DISPONIBILE";
				context.getRequest().setAttribute("Errore", errore);
			}
		}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return executeCommandDashboard(context);
	}
	
	private int caricaFile(ActionContext context, Modulo modulo, Connection db,  MultipartRequest parts) throws IllegalArgumentException, IOException, IllegalStateException, SQLException, ServletException, FileUploadException{

		//Sintassi: Modulo_B-XXX.doc
		
		
		if ( parts.getFile("file1") != null && (Object)  parts.getFile("file1") instanceof File) {
		GestioneAllegatiModuli moduli = new GestioneAllegatiModuli();
		moduli.setIdModulo(modulo.getId());
		moduli.setIdFlusso(modulo.getIdFlusso());
		moduli.setIdTipo(modulo.getIdTipo());
		moduli.setTipoAllegato("Modulo");
		File file1 = (File) parts.getFile("file1");
		moduli.setFileDaCaricare(file1);
		moduli.allegaFile(context);
	}
		

		
		return 1;
	}
	
	private int caricaFileConsegna(ActionContext context, Flusso flusso, Connection db,  MultipartRequest parts) throws IllegalArgumentException, IOException, IllegalStateException, SQLException, ServletException, FileUploadException{
	//Sintassi: Modulo_B-XXX.doc
		
		if ( parts.getFile("fileConsegna1") != null && (Object)  parts.getFile("fileConsegna1") instanceof File) {
		GestioneAllegatiModuli moduli = new GestioneAllegatiModuli();
		moduli.setIdFlusso(flusso.getIdFlusso());
		moduli.setTipoAllegato("AllegatoConsegna");
		File file1 = (File) parts.getFile("fileConsegna1");
		moduli.setFileDaCaricare(file1);
		moduli.allegaFile(context);
	}
		return 1;
	}
	public String executeCommandDettaglioModulo(ActionContext  context)
	{
		
		String idModuloString = null;
		int idModulo = -1;
		
		idModuloString = context.getParameter("id");
		if (idModuloString==null)
			idModulo = (int) context.getRequest().getAttribute("id");
		else
			idModulo = Integer.parseInt(idModuloString);
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Modulo modulo = new Modulo();
			modulo.queryRecord(db, idModulo);
			SystemStatus system= this.getSystemStatus(context);
			
			LookupList listaTipiModulo = new LookupList(db,"lookup_tipo_modulo_sviluppo");
			context.getRequest().setAttribute("listaTipiModulo", listaTipiModulo);
				
			context.getRequest().setAttribute("Modulo", modulo);
			
			context.getRequest().setAttribute("listaAllegati",listaAllegati(context,modulo.getIdFlusso(), modulo.getIdTipo(), modulo.getId()));
			
	}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore Dettaglio");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		if (context.getParameter("popup")!=null && context.getParameter("popup").equals("true"))
			return "DettaglioModuloPopupOK";
		return "DettaglioModuloOK";
	}
	
	private DocumentaleAllegatoModuloList listaAllegati(ActionContext context, int idFlusso, int idTipo, int idModulo) throws SQLException, IOException
	{
		GestioneAllegatiModuli moduli = new GestioneAllegatiModuli();
//		moduli.setIdFlusso(idFlusso);
//		moduli.setIdTipo(idTipo);
		moduli.setIdModulo(idModulo);
		return moduli.listaAllegati(context);
	}
	private DocumentaleAllegatoModuloList listaAllegatiConsegna(ActionContext context, int idFlusso) throws SQLException, IOException
	{
		GestioneAllegatiModuli moduli = new GestioneAllegatiModuli();
		moduli.setIdFlusso(idFlusso);
		moduli.setTipoAllegato("AllegatoConsegna");
		return moduli.listaAllegatiConsegna(context);
	}
	
	
	public String executeCommandUpdateNote(ActionContext  context){
		
		if (!hasPermission(context, "devdoc-edit")) {
		      return ("PermissionError");
		}
	
		Connection db = null;
		String idUtente = context.getRequest().getParameter("idUtente");
		String idModulo = context.getRequest().getParameter("idModulo");
		String note = context.getRequest().getParameter("note"+idUtente);
		try
		{
			db = this.getConnection(context);

			Timestamp time = new Timestamp(System.currentTimeMillis());
			
			ModuloNote notes = new ModuloNote();
			notes.setIdUtente(idUtente);
			notes.setIdModulo(idModulo);
			notes.setNote(note);
			notes.setDataCancellazione( time );
			notes.store(db);
			
			
	}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore Aggiorna note");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("idModulo", idModulo);
		return executeCommandDashboard(context);
	}

	
	public String executeCommandDettaglioFlusso(ActionContext  context)
	{
		
		String idFlussoString = null;
		int idFlusso = -1;
		
		idFlussoString = context.getParameter("id");
		if (idFlussoString==null)
			idFlusso = (int) context.getRequest().getAttribute("id");
		else
			idFlusso = Integer.parseInt(idFlussoString);
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
			
			LookupList listaTipiPriorita = new LookupList(db,"lookup_priorita_flusso");
			context.getRequest().setAttribute("listaTipiPriorita", listaTipiPriorita);

			Flusso flusso = new Flusso();
			flusso.queryRecord(db, idFlusso);
			SystemStatus system= this.getSystemStatus(context);
					
			context.getRequest().setAttribute("Flusso", flusso);
			
			context.getRequest().setAttribute("listaAllegatiConsegna",listaAllegatiConsegna(context,flusso.getIdFlusso()));

				
	}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore Dettaglio");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		if (context.getParameter("popup")!=null && context.getParameter("popup").equals("true"))
			return "DettaglioFlussoPopupOK";
		return "DettaglioFlussoOK";
	}
	

	public  void sendMailNuovoModulo(HttpServletRequest req,String testo,String object,String toDest, File allegato)
	{

		
		String[] toDestArray = toDest.split(";");
		HashMap<String,String> configs = new HashMap<String,String>();
		configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
		configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
		configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
		configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
		configs.put("mail.smtp.ssl.enable",ApplicationProperties.getProperty("mail.smtp.ssl.enable"));
		configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
		configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		configs.put("mail.smtp.socketFactory.fallback", "false");
		
		PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
		try {
			sender.sendMailConAllegato(object,testo,"gisasuap@cert.izsmportici.it", toDestArray, allegato);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	
	
	public String executeCommandScadenzario(ActionContext context)
	{
		
		ArrayList<String> listaFlussiAperti = new ArrayList<String>();
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

		PreparedStatement pst = db.prepareStatement("select * from scadenzario_flussi_aperti()");
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			String riga = "";
			riga= riga+";;"+rs.getString("id_flusso")+";;"+rs.getString("data")+";;"+rs.getString("data_ultima_modifica")+";;"+rs.getString("descrizione")+";;"+rs.getString("situazione");
			listaFlussiAperti.add(riga);
		}
		context.getRequest().setAttribute("listaFlussiAperti", listaFlussiAperti);
		}
		catch(Exception e)
		{
			logger.error("Gestione Sviluppo Moduli Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "scadenzarioFlussiOK";
	}
	
	public String executeCommandPrepareConsegna(ActionContext  context)
	{
		
		String idFlussoString = null;
		int idFlusso = -1;
		
		idFlussoString = context.getParameter("id");
		if (idFlussoString==null)
			idFlusso = (int) context.getRequest().getAttribute("id");
		else
			idFlusso = Integer.parseInt(idFlussoString);
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Flusso flusso = new Flusso();
			flusso.queryRecord(db, idFlusso);
			SystemStatus system= this.getSystemStatus(context);
					
			context.getRequest().setAttribute("Flusso", flusso);
				
	}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore Dettaglio");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "ConsegnaFlussoOK";
	}
	
	public String executeCommandConsegna(ActionContext  context) throws IOException
	{
		
//		String filePath = this.getPath(context, "modulisviluppo");
//		File theDir = new File(filePath);
//        theDir.mkdirs();
        
		String filePath = getWebInfPath(context,"tmp_moduli_sviluppo");

		MultipartRequest multi = new MultipartRequest(context.getRequest(), filePath,MAX_SIZE_REQ,"UTF-8");

		String idFlussoString = null;
		int idFlusso = -1;
		
		idFlussoString = multi.getParameter("idFlusso");
		idFlusso = Integer.parseInt(idFlussoString);
		
		String dataConsegna = multi.getParameter("dataConsegna");
		String noteConsegna = multi.getParameter("noteConsegna");

		UserBean u =((UserBean) context.getRequest().getSession().getAttribute("User"));

		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Flusso flusso = new Flusso();
			flusso.queryRecord(db, idFlusso);
			flusso.setDataConsegna(dataConsegna);
			flusso.setNoteConsegna(noteConsegna);
			flusso.setIdUtenteConsegna(u.getUserId());
			flusso.consegna(db);
			
			
			//String toDest="gisadev@u-s.it;gisaref@u-s.it;orsacampania@izsmportici.it;izshd@u-s.it";
			String toDest=ApplicationProperties.getProperty("DEST_EMAIL_FLUSSO_SVILUPPO");
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String testo = "E' stato consegnato un flusso nel flusso documentale di sviluppo.<br/><br/>"
					+ "Descrizione: "+ flusso.getDescrizione()+"<br/><br/>"
					+ "Flusso: "+flusso.getIdFlusso()+"<br/>"
					+ "Data consegna: "+ dateFormat.format(flusso.getDataConsegna())+" <br/>"
					+ "Note consegna: "+ flusso.getNoteConsegna()+" <br/>"
					+ "Consegnato da: "+u.getUsername(); 
			
			File allegato = null;
			try {
				allegato =		(File) multi.getFile("fileConsegna1"); } catch (Exception e) {}
			sendMailNuovoModulo(context.getRequest(), testo, "##GISA GESTIONE FLUSSO SVILUPPO## - "+flusso.getDescrizione(), toDest, allegato);
			caricaFileConsegna(context, flusso, db, multi);
	}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore Consegna");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("idFlusso", idFlussoString);
		return executeCommandDashboard(context);
	}
	
	
	public String executeCommandPrepareStandby(ActionContext  context)
	{
		
		String idFlussoString = null;
		int idFlusso = -1;
		
		idFlussoString = context.getParameter("id");
		if (idFlussoString==null)
			idFlusso = (int) context.getRequest().getAttribute("id");
		else
			idFlusso = Integer.parseInt(idFlussoString);
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Flusso flusso = new Flusso();
			flusso.queryRecord(db, idFlusso);
			SystemStatus system= this.getSystemStatus(context);
					
			context.getRequest().setAttribute("Flusso", flusso);
				
	}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore Dettaglio");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "StandbyFlussoOK";
	}
	
	public String executeCommandStandby(ActionContext  context) throws IOException
	{
		
		String idFlussoString = null;
		int idFlusso = -1;
		
		idFlussoString = context.getRequest().getParameter("idFlusso");
		idFlusso = Integer.parseInt(idFlussoString);
		
		String dataStandby = context.getRequest().getParameter("dataStandby");
		String noteStandby = context.getRequest().getParameter("noteStandby");

		UserBean u =((UserBean) context.getRequest().getSession().getAttribute("User"));

		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Flusso flusso = new Flusso();
			flusso.queryRecord(db, idFlusso);
			flusso.setDataStandby(dataStandby);
			flusso.setNoteStandby(noteStandby);
			flusso.setIdUtenteStandby(u.getUserId());
			flusso.standby(db);
	}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore standby");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("idFlusso", idFlussoString);
		return executeCommandDashboard(context);
	}
		
	
	public String executeCommandPrepareAnnullamento(ActionContext  context)
	{
		
		String idFlussoString = null;
		int idFlusso = -1;
		
		idFlussoString = context.getParameter("id");
		if (idFlussoString==null)
			idFlusso = (int) context.getRequest().getAttribute("id");
		else
			idFlusso = Integer.parseInt(idFlussoString);
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Flusso flusso = new Flusso();
			flusso.queryRecord(db, idFlusso);
			SystemStatus system= this.getSystemStatus(context);
					
			context.getRequest().setAttribute("Flusso", flusso);
				
	}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore Dettaglio");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "AnnullamentoFlussoOK";
	}
	
	public String executeCommandAnnullamento(ActionContext  context) throws IOException
	{
		
		String idFlussoString = null;
		int idFlusso = -1;
		
		idFlussoString = context.getRequest().getParameter("idFlusso");
		idFlusso = Integer.parseInt(idFlussoString);
		
		String dataAnnullamento = context.getRequest().getParameter("dataAnnullamento");
		String noteAnnullamento = context.getRequest().getParameter("noteAnnullamento");

		UserBean u =((UserBean) context.getRequest().getSession().getAttribute("User"));

		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Flusso flusso = new Flusso();
			flusso.queryRecord(db, idFlusso);
			flusso.setDataAnnullamento(dataAnnullamento);
			flusso.setNoteAnnullamento(noteAnnullamento);
			flusso.setIdUtenteAnnullamento(u.getUserId());
			flusso.annullamento(db);
	}
		catch(Exception e)
		{
			logger.error("Gestione Flusso Sviluppo Errore standby");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("idFlusso", idFlussoString);
		return executeCommandDashboard(context);
	}
	
	public String executeCommandUpdatePriorita(ActionContext context) throws IOException{
		if (!hasPermission(context, "devdoc-priorita-view")) {
		      return ("PermissionError");
		}
		
		int idFlusso = -1;
		int idPriorita = -1;
		
		idFlusso = Integer.parseInt(context.getRequest().getParameter("idFlusso"));
		idPriorita = Integer.parseInt(context.getRequest().getParameter("idPriorita"));
		
		Connection db = null ;
		
		try {
			db = this.getConnection(context);
			
			Flusso flusso = new Flusso();
			flusso.queryRecord(db, idFlusso);
			flusso.setIdPriorita(idPriorita);
			flusso.updatePriorita(db, getUserId(context));
		}
		catch(Exception e) {
			logger.error("Gestione Flusso Sviluppo Errore Update Priorita'");
		}
		finally {
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("idFlusso", idFlusso);
		
		return executeCommandDashboard(context);
	}
	
	public String executeCommandAggiungiNota(ActionContext context) throws IOException {
		int idFlusso = Integer.parseInt(context.getRequest().getParameter("idFlusso"));
		String nota = context.getRequest().getParameter("nuova-nota");
		Connection db = null;
		try {
			db = this.getConnection(context);
			Flusso flusso = new Flusso();
			flusso.queryRecord(db, idFlusso);
			flusso.aggiungiNota(db, getUserId(context), nota);
		}
		catch(Exception e) {
			logger.error("Gestione Flusso Sviluppo Errore Aggiungi Nota");
		}
		finally {
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("idFlusso", idFlusso);
		
		return executeCommandDashboard(context);
	}
	
	
}
