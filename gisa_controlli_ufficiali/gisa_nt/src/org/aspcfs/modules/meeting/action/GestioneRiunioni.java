/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.meeting.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiRiunione;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunione;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunioneList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.meeting.base.Referente;
import org.aspcfs.modules.meeting.base.ReferenteApprovazione;
import org.aspcfs.modules.meeting.base.ReferenteList;
import org.aspcfs.modules.meeting.base.Rilascio;
import org.aspcfs.modules.meeting.base.RilascioList;
import org.aspcfs.modules.meeting.base.Riunione;
import org.aspcfs.modules.meeting.base.RiunioneList;
import org.aspcfs.modules.mycfs.base.Mail;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.trasportoanimali.base.Stato;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.HttpMultiPartParser;
import com.oreilly.servlet.MultipartRequest;

public class GestioneRiunioni extends CFSModule{

	Logger logger = Logger.getLogger(GestioneRiunioni.class);



	private static final int MAX_SIZE_REQ = 50000000;


	
	
	
	public String executeCommandListaRevisioniVerbale(ActionContext context) {

		Connection db = null ;
		try
		{	
			db = this.getConnection(context);


			String headerVerbale = context.getRequest().getParameter("headerVerbale");



			context.getRequest().setAttribute("listaRevisioni",listaRevisioniVerbale(context,headerVerbale));

			


		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "ListaRevisioniVerbaleOK";
	}

	public String executeCommandEditApprovazione(ActionContext context) {

		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			int userId = Integer.parseInt(context.getParameter("userId"));
			int idRiunione = Integer.parseInt(context.getParameter("id"));

			Riunione r = new Riunione();
			r.queryRecord(db, idRiunione);
			ReferenteApprovazione referente = new ReferenteApprovazione();

			for (ReferenteApprovazione ref : r.getListaReferenti())
			{
				if (ref.getReferente().getUserId()==userId)
				{
					referente=ref;
					break;
				}
			}
			

			LookupList listaLuoghi = new LookupList(db,"lookup_luogo_riunioni");
			context.getRequest().setAttribute("listaLuoghi", listaLuoghi);

			context.getRequest().setAttribute("listaAllegati",listaAllegati(context,r.getId()));

			LookupList listaStati = new LookupList(db,"lookup_stato_riunione_referenti");
			listaStati.removeElementByLevel(1);
			context.getRequest().setAttribute("listaStati", listaStati);

			context.getRequest().setAttribute("Riunione", r);
			context.getRequest().setAttribute("ApprovazioneReferente", referente);


		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "ToEditApprovazioneOK";
	}

	public String executeCommandSearchForm(ActionContext context) {

		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			LookupList listaContesti = new LookupList(db,"lookup_riunione_contesti");
			listaContesti.addItem(-1,"TUTTI");
			context.getRequest().setAttribute("listaContesti", listaContesti);
			
			LookupList listaLuoghi = new LookupList(db,"lookup_luogo_riunioni");
			context.getRequest().setAttribute("listaLuoghi", listaLuoghi);


			LookupList listaStati = new LookupList(db,"lookup_stato_riunione");
			listaStati.addItem(-1,"TUTTI");
			context.getRequest().setAttribute("listaStati", listaStati);

			LookupList listaEspressioni = new LookupList(db,"lookup_riunioni_espressioni");
			listaEspressioni.addItem(-1,"TUTTI");
			context.getRequest().setAttribute("listaEspressioni", listaEspressioni);



		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "SearchFormOK";
	}
	public String executeCommandSearch(ActionContext  context)
	{
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			PagedListInfo searchListInfo = this.getPagedListInfo(context,"SearchRiunioniListInfo");
			searchListInfo.setLink("GestioneRiunioni.do?command=Search");
			searchListInfo.setListView("all");


			RiunioneList listaRinuonioni = new RiunioneList();

			listaRinuonioni.setPagedListInfo(searchListInfo);
			searchListInfo.setSearchCriteria(listaRinuonioni, context);
			listaRinuonioni.setUserId(getUserId(context));
			listaRinuonioni.buildList(db);
			context.getRequest().setAttribute("RiunioneList", listaRinuonioni);

			LookupList listaLuoghi = new LookupList(db,"lookup_luogo_riunioni");
			context.getRequest().setAttribute("listaLuoghi", listaLuoghi);


			LookupList listaStati = new LookupList(db,"lookup_stato_riunione");
			context.getRequest().setAttribute("listaStati", listaStati);




		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");

		return "SearchOK";
	}



	public String executeCommandAdd(ActionContext  context)
	{


		Connection db = null ;
		try
		{
			db = this.getConnection(context);
			LookupList listaLuoghi = new LookupList(db,"lookup_luogo_riunioni");
			context.getRequest().setAttribute("listaLuoghi", listaLuoghi);
			
			LookupList listaContesti = new LookupList(db,"lookup_riunione_contesti");
			context.getRequest().setAttribute("listaContesti", listaContesti);

			ReferenteList listaRef = new ReferenteList();
			listaRef.buildList(db);
			context.getRequest().setAttribute("ListaReferenti", listaRef);
		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");
		return "AddOK";
	}


	public String executeCommandInsert(ActionContext  context)
	{


		Connection db = null ;
		try
		{

//			String filePath = this.getPath(context, "riunioni");
//			File theDir = new File(filePath);
//	        theDir.mkdirs();

			String filePath = getWebInfPath(context,"tmp_riunioni");

			MultipartRequest multi = new MultipartRequest(context.getRequest(), filePath,MAX_SIZE_REQ,"UTF-8");


			db = this.getConnection(context);
			Riunione riunione = new Riunione();
			riunione.setTitolo(multi.getParameter("titolo"));
			riunione.setData(multi.getParameter("data"));
			riunione.setDescrizioneBreve(multi.getParameter("descrizioneBreve"));
			
			if (multi.getParameter("sede").equals("1"))
			{
				if (multi.getParameter("luogo").equalsIgnoreCase("altro"))
				{
					riunione.setLuogo(multi.getParameter("altro"));
				}
				else
				{
					riunione.setLuogo(multi.getParameter("luogo"));
				}
			}
			else
			{
				riunione.setLuogo("A Distanza");
			}
			
			
			riunione.setEnteredby(getUserId(context));
			riunione.setStato(Riunione.STATO_BOZZA);
			riunione.setListaPartecipanti(multi.getParameterValues("partecipanti") );
			if (multi.getParameter("contesto").equalsIgnoreCase("altro"))
				riunione.setContesto(multi.getParameter("altroContesto"));
			else
				riunione.setContesto(multi.getParameter("contesto"));
			

			String[] listaReferenti = multi.getParameterValues("referenti");
			for (int i = 0 ; i < listaReferenti.length; i++)
			{
				ReferenteApprovazione approvazioneReferente = new ReferenteApprovazione();
				approvazioneReferente.setReferente(new Referente(db, Integer.parseInt(listaReferenti[i])));
				approvazioneReferente.setStato(1);

				riunione.getListaReferenti().add(approvazioneReferente);
			}

			riunione.insert(db);
			
			UserBean u =((UserBean) context.getRequest().getSession().getAttribute("User"));
			
			//String toDest="gisadev@u-s.it;gisaref@u-s.it;orsacampania@izsmportici.it;izshd@u-s.it";
			String toDest=ApplicationProperties.getProperty("DEST_EMAIL");
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String testo = "INSERITA NUOVA RIUNIONE \n DATA "+ dateFormat.format(riunione.getData())+" \n INSERITA DA "+u.getUsername(); 
			sendMailIzsm(context.getRequest(), testo, "##GISA GESTIONE RIUNIONI## - "+riunione.getTitolo(), toDest);

			/*ricostruiscio il bean a partire da cio che e' stato inserito nel db*/
			riunione.queryRecord(db, riunione.getId());




			/**
			 * 
			 */

			caricaVerbali(context, riunione.getId(), multi);

			/**
			 * 
			 */


			//			referenti
			context.getRequest().setAttribute("Riunione", riunione);


		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "InsertOK";
	}
	
	
	
	
	public String executeCommandCaricaAltriFile(ActionContext  context)
	{


		Connection db = null ;
		try
		{

//			String filePath = this.getPath(context, "riunioni");
//			File theDir = new File(filePath);
//	        theDir.mkdirs();

			String filePath = getWebInfPath(context,"tmp_riunioni");

			MultipartRequest multi = new MultipartRequest(context.getRequest(), filePath,MAX_SIZE_REQ,"UTF-8");


			db = this.getConnection(context);
			int id = Integer.parseInt(multi.getParameter("id"));

			/**
			 * 
			 */

			caricaVerbali(context, id, multi);
			
			Riunione riunione = new Riunione();
			riunione.queryRecord(db, id);
			/**
			 * 
			 */


			//			referenti
			context.getRequest().setAttribute("Riunione", riunione);


		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "InsertOK";
	}

	
	
	
	
	public String executeCommandChiudiRiunione(ActionContext  context)
	{
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Riunione riunione = new Riunione();
			riunione.queryRecord(db, Integer.parseInt(context.getParameter("id")));
			
			SystemStatus system= this.getSystemStatus(context);
			
			for (ReferenteApprovazione ref : riunione.getListaReferenti())
			{
				ref.getReferente().setNominativo(ref.getReferente().getNominativo()+" ("+ ((system.getUser(ref.getReferente().getUserId())!=null)?system.getUser(ref.getReferente().getUserId()).getUsername() :"-")+")");
				
			}
			
			
			riunione.setStato(Riunione.STATO_CHIUSO_NON_MODIFICABILE);
			riunione.chiusuraUfficio(db, getUserId(context));
			
			context.getRequest().setAttribute("Riunione", riunione);

			LookupList listaStati = new LookupList(db,"lookup_stato_riunione");
			context.getRequest().setAttribute("listaStati", listaStati);

			LookupList listaStatirEF = new LookupList(db,"lookup_stato_riunione_referenti");
			context.getRequest().setAttribute("listaStatiRef", listaStatirEF);
			context.getRequest().setAttribute("listaAllegati",listaAllegati(context,riunione.getId()));




		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");

		return "InsertOK";
	}

	public String executeCommandDettaglioRiunione(ActionContext  context)
	{
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Riunione riunione = new Riunione();
			riunione.queryRecord(db, Integer.parseInt(context.getParameter("id")));
			
			SystemStatus system= this.getSystemStatus(context);
			
			for (ReferenteApprovazione ref : riunione.getListaReferenti())
			{
				ref.getReferente().setNominativo(ref.getReferente().getNominativo()+" ("+ ((system.getUser(ref.getReferente().getUserId())!=null)?system.getUser(ref.getReferente().getUserId()).getUsername() :"-")+")");
				
			}
			
			
			context.getRequest().setAttribute("Riunione", riunione);

			LookupList listaStati = new LookupList(db,"lookup_stato_riunione");
			context.getRequest().setAttribute("listaStati", listaStati);

			LookupList listaStatirEF = new LookupList(db,"lookup_stato_riunione_referenti");
			context.getRequest().setAttribute("listaStatiRef", listaStatirEF);
			context.getRequest().setAttribute("listaAllegati",listaAllegati(context,riunione.getId()));
			
			LookupList listaContesti = new LookupList(db,"lookup_riunione_contesti");
			context.getRequest().setAttribute("listaContesti", listaContesti);



		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");

		return "DettaglioOK";
	}


	public String executeCommandCambiaStatoReferente(ActionContext  context)
	{
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			MultipartRequest multi = new MultipartRequest(context.getRequest(), ".",MAX_SIZE_REQ,"UTF-8");

			Riunione riunione = new Riunione();
			riunione.queryRecord(db, Integer.parseInt(multi.getParameter("id")));
			context.getRequest().setAttribute("Riunione", riunione);


			int userIdReferente = Integer.parseInt(multi.getParameter("userIdReferente"));
			int nuovoStato = Integer.parseInt(multi.getParameter("stato"));
			LookupList listaStati = new LookupList(db,"lookup_stato_riunione_referenti");

			
			if (nuovoStato==5)
			{
				caricaVerbali(context, riunione.getId(), multi);
				
			}
			
			GestioneAllegatiRiunione doc =new GestioneAllegatiRiunione();
			
			if (context.getRequest().getAttribute("VerbaleRev")!=null)
				doc =(GestioneAllegatiRiunione)context.getRequest().getAttribute("VerbaleRev");
			

			for (ReferenteApprovazione approvazione : riunione.getListaReferenti())
			{
				if (approvazione.getReferente().getUserId()==userIdReferente)
				{
					approvazione.setStato(nuovoStato);
					approvazione.setNote(multi.getParameter("note"));
					if (nuovoStato==5)
					{
						approvazione.setRevVerbale("REV."+doc.getNumeroRevisione());
					}
					else
						approvazione.setRevVerbale(multi.getParameter("sceltaVerbaleGisa"));
					
					
					
					approvazione.updateStato(db, riunione.getId(), getUserId(context));
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String dataApprovazione = sdf.format(new Date(System.currentTimeMillis()));
					
					//String toDest="gisadev@u-s.it;gisaref@u-s.it;orsacampania@izsmportici.it;izshd@u-s.it";
					String toDest=ApplicationProperties.getProperty("DEST_EMAIL");
					
					
					String testo = "SI e' ESPRESSO SULLA RIUNIONE CON ESITO : "+listaStati.getSelectedValue(nuovoStato)+" \n IN DATA "+dataApprovazione+" \n L'UTENTE "+approvazione.getReferente().getNominativo(); 
					sendMailIzsm(context.getRequest(), testo, "##GISA GESTIONE RIUNIONI## - "+riunione.getTitolo(), toDest);
					
				}
			}

			riunione.ricalcolaStato(db);


			
			
			
			

			context.getRequest().setAttribute("Riunione", riunione);
			




		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");

		return "InsertOK";
	}
	
	
	
	public String executeCommandAllegaNuovaRevisione(ActionContext  context)
	{
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
			db.setAutoCommit(false);

//			String filePath = this.getPath(context, "riunioni");
//			File theDir = new File(filePath);
//	        theDir.mkdirs();
			
			String filePath = getWebInfPath(context,"tmp_riunioni");

			MultipartRequest multi = new MultipartRequest(context.getRequest(), filePath,MAX_SIZE_REQ,"UTF-8");

			Riunione riunione = new Riunione();
			riunione.queryRecord(db, Integer.parseInt(multi.getParameter("id")));
			context.getRequest().setAttribute("Riunione", riunione);

			
			
			caricaVerbali(context, riunione.getId(), multi);

			context.getRequest().setAttribute("Riunione", riunione);
			
			
			UserBean u =((UserBean) context.getRequest().getSession().getAttribute("User"));
			//String toDest="gisadev@u-s.it;gisaref@u-s.it;orsacampania@izsmportici.it;izshd@u-s.it";
			String toDest=ApplicationProperties.getProperty("DEST_EMAIL");
			String testo = "ALLEGATA NUOVA VERSIONE VERBALE: \n DALL'UTENTE "+u.getUsername(); 
			sendMailIzsm(context.getRequest(), testo, "##GISA GESTIONE RIUNIONI## - "+riunione.getTitolo(), toDest);

			db.commit();


		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
			try {
				db.rollback();
				db.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");

		return "InsertOK";
	}
	
	public String executeCommandToAllegaNuovaRevisione(ActionContext  context)
	{
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			

			Riunione riunione = new Riunione();
			riunione.queryRecord(db, Integer.parseInt(context.getParameter("id")));
			context.getRequest().setAttribute("Riunione", riunione);
			
			context.getRequest().setAttribute("listaAllegatiInRev",listaAllegati(context,riunione.getId()));


		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");

		return "AllegaFileOK";
	}


	
	private DocumentaleAllegatoRiunioneList listaRevisioniVerbale(ActionContext context, String header) throws SQLException, IOException
	{
		GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
		verbali.setHeaderRevisionato(header);
		return verbali.listaRevisioni(context);
	}
	
	private DocumentaleAllegatoRiunioneList listaAllegati(ActionContext context, int idRiunione) throws SQLException, IOException
	{
		GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
		verbali.setIdRiunione(idRiunione);
		return verbali.listaAllegati(context);
	}

	
public String executeCommandListaAllegati(ActionContext context) throws SQLException, IOException {
		
		GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
		verbali.setIdRiunione(context.getParameter("id"));
		DocumentaleAllegatoRiunioneList lista= verbali.listaAllegati(context);
		context.getRequest().setAttribute("ListaAllegati", lista);
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
		Riunione riunione = new Riunione();
		riunione.queryRecord(db, Integer.parseInt(context.getParameter("id")));
		/**
		 * 
		 */


		//			referenti
		context.getRequest().setAttribute("Riunione", riunione);
		
		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");
		return "ListaAllegatiOK" ;
		
	}


	private int caricaVerbali(ActionContext context, int idRiunione, MultipartRequest parts) throws IllegalArgumentException, IOException, IllegalStateException, SQLException, ServletException, FileUploadException{



		//String filePath = this.getPath(context, "accounts");

		if ( parts.getFile("file1") != null && (Object)  parts.getFile("file1") instanceof File) {
			GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
			verbali.setIdRiunione(idRiunione);
			verbali.setTipoAllegato("VerbaleRiunione");
			File file1 = (File) parts.getFile("file1");
			String subject1 = (String) parts.getParameter("subject1");
			verbali.setFileDaCaricare(file1);
			verbali.setOggetto(subject1);
			verbali.setPrincipale(true);
			verbali.allegaFile(context);
		}

		if ((Object)  parts.getFile("file2") instanceof File) {
			GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
			verbali.setIdRiunione(idRiunione);
			verbali.setTipoAllegato("VerbaleRiunione");
			File file1 = (File) parts.getFile("file2");
			String subject1 = (String) parts.getParameter("subject2");
			verbali.setFileDaCaricare(file1);
			verbali.setOggetto(subject1);
			verbali.allegaFile(context);
		}

		if ((Object)  parts.getFile("file3") instanceof File) {
			GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
			verbali.setIdRiunione(idRiunione);
			verbali.setTipoAllegato("VerbaleRiunione");
			File file1 = (File) parts.getFile("file3");
			String subject1 = (String) parts.getParameter("subject3");
			verbali.setFileDaCaricare(file1);
			verbali.setOggetto(subject1);
			verbali.allegaFile(context);
		}

		if ((Object)  parts.getFile("file4") instanceof File) {
			GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
			verbali.setIdRiunione(idRiunione);
			verbali.setTipoAllegato("VerbaleRiunione");
			File file1 = (File) parts.getFile("file4");
			String subject1 = (String) parts.getParameter("subject4");
			verbali.setFileDaCaricare(file1);
			verbali.setOggetto(subject1);
			verbali.allegaFile(context);
		}

		if ((Object)  parts.getFile("file5") instanceof File) {
			GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
			verbali.setIdRiunione(idRiunione);
			verbali.setTipoAllegato("VerbaleRiunione");
			File file1 = (File) parts.getFile("file5");
			String subject1 = (String) parts.getParameter("subject5");
			verbali.setFileDaCaricare(file1);
			verbali.setOggetto(subject1);
			verbali.allegaFile(context);
		}

		if ((Object)  parts.getFile("file6") instanceof File) {
			GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
			verbali.setIdRiunione(idRiunione);
			verbali.setTipoAllegato("VerbaleRiunione");
			File file1 = (File) parts.getFile("file6");
			String subject1 = (String) parts.getParameter("subject6");
			verbali.setFileDaCaricare(file1);
			verbali.setOggetto(subject1);
			verbali.allegaFile(context);
		}

		if ((Object)  parts.getFile("fileRevisione") instanceof File) {
			GestioneAllegatiRiunione verbali = new GestioneAllegatiRiunione();
			verbali.setIdRiunione(idRiunione);
			verbali.setTipoAllegato("VerbaleRiunione");
			File file1 = (File) parts.getFile("fileRevisione");
			String subject1 = (String) parts.getParameter("subjectRevisione");
			verbali.setFileDaCaricare(file1);
			verbali.setOggetto(subject1);
			verbali.setRevisione(true);
			verbali.setPrincipale(true);
			verbali.setHeaderRevisionato(parts.getParameter("sceltaVerbaleDoc"));
			verbali.allegaFile(context);
			context.getRequest().setAttribute("VerbaleRev", verbali);
			

		}




		return 1;
	}
	
	
	
	
	public  void sendMailIzsm(HttpServletRequest req,String testo,String object,String toDest)
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
			sender.sendMail(object,testo,"gisasuap@cert.izsmportici.it", toDestArray, null);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	
	
	public String executeCommandAddRilascio(ActionContext  context)
	{
		if (!hasPermission(context, "meeting-rilasci-add")) {
			return ("PermissionError");
		}
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
			
		LookupList listaContesti = new LookupList(db,"lookup_riunione_contesti");
		context.getRequest().setAttribute("listaContesti", listaContesti);
		
		
		if (context.getRequest().getParameter("idRiunione")!=null){
			
			
				Riunione riunione = new Riunione();
				riunione.queryRecord(db, Integer.parseInt(context.getParameter("idRiunione")));
				SystemStatus system= this.getSystemStatus(context);
				context.getRequest().setAttribute("Riunione", riunione);
			
			}
		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return "AddRilascioOK";
	}


	public String executeCommandInsertRilascio(ActionContext  context)
	{
		if (!hasPermission(context, "meeting-rilasci-add")) {
			return ("PermissionError");
		}

		Connection db = null ;
		try
		{

			db = this.getConnection(context);
			Rilascio rilascio = new Rilascio();
			rilascio.setOggetto(context.getRequest().getParameter("oggetto"));
			rilascio.setNoteNote(context.getRequest().getParameter("noteNote"));
			rilascio.setNoteModulo(context.getRequest().getParameter("noteModulo"));
			rilascio.setNoteFunzione(context.getRequest().getParameter("noteFunzione"));
			rilascio.setNoteIdContesto(context.getRequest().getParameter("noteIdContesto"));
			rilascio.setData(context.getRequest().getParameter("data"));
			
			for (int i = 0; i<10; i++){
				String idRiunione = context.getRequest().getParameter("idRiunione"+i);
				
				try {
					int idRiu = Integer.parseInt(idRiunione);
					Riunione riunione = new Riunione();
					riunione.setId(idRiu);
					rilascio.getListaRiunioni().add(riunione);
				}
				catch (Exception e) {}
			}
			rilascio.setEnteredby(getUserId(context));
			rilascio.insert(db);
			
			Rilascio ril = new Rilascio();
			ril.queryRecord(db, rilascio.getId());
			context.getRequest().setAttribute("Rilascio", rilascio);
			


		}
		catch(Exception e)
		{
			logger.error("Gestione Rilasci Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "InsertRilascioOK";
	}

	public String executeCommandSearchFormDaRilasci(ActionContext context) {

		context.getRequest().setAttribute("indiceRiunione", context.getRequest().getParameter("indiceRiunione"));
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			LookupList listaContesti = new LookupList(db,"lookup_riunione_contesti");
			listaContesti.addItem(-1,"TUTTI");
			context.getRequest().setAttribute("listaContesti", listaContesti);
			
			LookupList listaLuoghi = new LookupList(db,"lookup_luogo_riunioni");
			context.getRequest().setAttribute("listaLuoghi", listaLuoghi);


			LookupList listaStati = new LookupList(db,"lookup_stato_riunione");
			listaStati.addItem(-1,"TUTTI");
			context.getRequest().setAttribute("listaStati", listaStati);

			LookupList listaEspressioni = new LookupList(db,"lookup_riunioni_espressioni");
			listaEspressioni.addItem(-1,"TUTTI");
			context.getRequest().setAttribute("listaEspressioni", listaEspressioni);



		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return "SearchFormDaRilasciOK";
	}
	
	public String executeCommandSearchDaRilasci(ActionContext  context)
	{
		context.getRequest().setAttribute("indiceRiunione", context.getRequest().getParameter("indiceRiunione"));
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			PagedListInfo searchListInfo = this.getPagedListInfo(context,"SearchRiunioniListInfo");
			searchListInfo.setLink("GestioneRiunioni.do?command=SearchDaRilasci");
			searchListInfo.setListView("all");
			RiunioneList listaRinuonioni = new RiunioneList();

			listaRinuonioni.setPagedListInfo(null);
			searchListInfo.setSearchCriteria(listaRinuonioni, context);
			listaRinuonioni.setUserId(getUserId(context));
			listaRinuonioni.buildList(db);
			context.getRequest().setAttribute("RiunioneList", listaRinuonioni);

			LookupList listaLuoghi = new LookupList(db,"lookup_luogo_riunioni");
			context.getRequest().setAttribute("listaLuoghi", listaLuoghi);


			LookupList listaStati = new LookupList(db,"lookup_stato_riunione");
			context.getRequest().setAttribute("listaStati", listaStati);

		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");

		return "SearchDaRilasciOK";
	}
	
	public String executeCommandDettaglioRilascio(ActionContext  context)
	{
		
		String idRilascioString = null;
		int idRilascio = -1;
		
		idRilascioString = context.getParameter("id");
		if (idRilascioString==null)
			idRilascio = (int) context.getRequest().getAttribute("id");
		else
			idRilascio = Integer.parseInt(idRilascioString);
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			Rilascio rilascio = new Rilascio();
			rilascio.queryRecord(db, idRilascio);
			SystemStatus system= this.getSystemStatus(context);
			
			LookupList listaContesti = new LookupList(db,"lookup_riunione_contesti");
			context.getRequest().setAttribute("listaContesti", listaContesti);
				
			context.getRequest().setAttribute("Rilascio", rilascio);
	}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");

		if (context.getParameter("popup")!=null && context.getParameter("popup").equals("true"))
			return "DettaglioRilascioPopupOK";
		return "DettaglioRilascioOK";
	}
	
	public String executeCommandSearchFormRilasci(ActionContext context) {

		Connection db = null ;
		try
		{
			db = this.getConnection(context);

		LookupList listaContesti = new LookupList(db,"lookup_riunione_contesti");
		listaContesti.addItem(-1,"TUTTI");
		context.getRequest().setAttribute("listaContesti", listaContesti);
		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "SearchFormRilasciOK";
	}
	public String executeCommandSearchRilasci(ActionContext  context)
	{
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

			PagedListInfo searchListInfo = this.getPagedListInfo(context,"SearchRilasciListInfo");
			searchListInfo.setLink("GestioneRiunioni.do?command=SearchRilasci");
			searchListInfo.setListView("all");


			RilascioList listaRilasci = new RilascioList();

			listaRilasci.setPagedListInfo(searchListInfo);
			searchListInfo.setSearchCriteria(listaRilasci, context);
			listaRilasci.buildList(db);
			context.getRequest().setAttribute("RilascioList", listaRilasci);
			
			LookupList listaContesti = new LookupList(db,"lookup_riunione_contesti");
			context.getRequest().setAttribute("listaContesti", listaContesti);

		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "meetinglist", "GestioneRiunioni");

		return "SearchRilasciOK";
	}

	public String executeCommandListaRilasciHomePage(ActionContext context){
		
		ArrayList<Rilascio> listaRilasci = new ArrayList<Rilascio>();
		Rilascio ril = new Rilascio();
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);

		listaRilasci = ril.getListaRilasciHomePage(db);
		context.getRequest().setAttribute("listaRilasci", listaRilasci);
		}
		catch(Exception e)
		{
			logger.error("Gestione Riunioni Errore Ricerca");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		
		return "ListaRilasciHomePageOK";
		
		
	}

	
		
	
}
