/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAssociazioni;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdOperazioniDM;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.IdTipiRichiedente;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupAssociazioniDAO;
import it.us.web.dao.lookup.LookupAttivitaEsterneDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.dao.lookup.LookupTipiRichiedenteDAO;
import it.us.web.dao.lookup.LookupTipoTrasferimentoDAO;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.dao.vam.AnimaleDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.dwr.vam.accettazione.Test;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
public class ToAdd extends GenericAction  implements Specie
{

	final static Logger logger = LoggerFactory.getLogger(ToAdd.class);
	
	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
        long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
			
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(conn bdu)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		String          idAnimaleString = req.getParameter( "idAnimale" );
		int				idAnimale		= interoFromRequest( "idAnimale" );
		boolean			modify			= booleanoFromRequest( "modify" );
		Animale			animale			= null;
		Accettazione	accettazione	= null;
		ServicesStatus	status			= new ServicesStatus();
		Gatto gatto = null;
		Cane cane   = null;
		String esit_descrizione          = "";
		
		
		boolean testPossibilita = true;
		
		if(utente.getRuolo()!=null && !( utente.getRuolo().equalsIgnoreCase("Referente Asl") || utente.getRuolo().equals("14"))){			
			Test testPossibilitAprire = new Test();
			esit_descrizione = testPossibilitAprire.possibileAprire( idAnimaleString,  String.valueOf(utente.getId()), req);
			
			if (esit_descrizione != null && !("").equals(esit_descrizione)){
				testPossibilita = false;
			}
		}
		
		
		Iterator<LookupAsl> asl = LookupAslDAO.getAsl(connection).iterator();
		while(asl.hasNext())
		{
			LookupAsl aslTemp = asl.next();
			req.setAttribute("personaleAsl"+ aslTemp.getId(), UtenteDAO.getUtenti(connection,aslTemp.getId(),-1));
			
		}
		
	if (testPossibilita || modify){
		
		if( modify )
		{
			logger.info("Modifica accettazione");
			accettazione	= AccettazioneDAO.getAccettazione(interoFromRequest( "id" ), connection); 
			animale			= accettazione.getAnimale();
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(find accettazione)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
		}
		else
		{
			logger.info("Redirect to inserimento accettazione");
			
			animale			= AnimaleDAO.getAnimale(idAnimale, connection);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(find animale)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			logger.info("Recuperato l'animale. Mc: " + animale.getIdentificativo());
			accettazione	= AnimaliUtil.newAccettazione( persistence, animale, utente, status, req, connectionBdu );
			accettazione.setAnimale(animale);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(new accettazione)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			logger.info("Recuperata l'accettazione. Id: " + accettazione.getId());
		}
		
		/*if( status.isAllRight() )
		{*/
			Date dataDecesso = null;
			Date dataNascita = null;
			String dataDecessoCertezza = "";
			String dataNascitaCertezza = "";
			String specieAnimale = null;
			if(animale.getDecedutoNonAnagrafe())
			{
				switch ( animale.getLookupSpecie().getId() )
				{
				case CANE:
					specieAnimale = "canina";
					dataDecesso = animale.getDataMorte();
					dataDecessoCertezza = animale.getDataMorteCertezza();
					dataNascita = animale.getDataNascita();
					dataNascitaCertezza = animale.getDataNascitaCertezza();
					break;
				case GATTO:
					specieAnimale = "felina";
					dataDecesso = animale.getDataMorte();
					dataDecessoCertezza = animale.getDataMorteCertezza();
					dataNascita = animale.getDataNascita();
					dataNascitaCertezza = animale.getDataNascitaCertezza();
					break;
				case SINANTROPO:
					specieAnimale = "sinantropi";
					dataDecesso = animale.getDataMorte();
					dataDecessoCertezza = animale.getDataMorteCertezza();
					dataNascita = animale.getDataNascita();
					dataNascitaCertezza = animale.getDataNascitaCertezza();
					break;
				}
			}
			else
			{
				switch ( animale.getLookupSpecie().getId() )
				{
				case CANE:
					specieAnimale = "canina";
					
					cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), utente, status, connectionBdu,req);
					RegistrazioniCaninaResponse decessoReg = CaninaRemoteUtil.getInfoDecesso( cane );
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					dataEnd  	= new Date();
					utente = ((BUtente) req.getSession().getAttribute( "utente" ));
					timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(find cane/info decesso)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					dataDecesso = (decessoReg == null) ? null : decessoReg.getDataEvento();
					break;
				case GATTO:
					specieAnimale = "felina";
					gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), utente, status, connectionBdu,req);
					RegistrazioniFelinaResponse felinaDec = FelinaRemoteUtil.getInfoDecesso( gatto );
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					dataEnd  	= new Date();
					utente = ((BUtente) req.getSession().getAttribute( "utente" ));
					timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(find gatto/info decesso)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					dataDecesso = (felinaDec == null) ? null : felinaDec.getDataEvento();

					break;
				case SINANTROPO:
					specieAnimale = "sinantropi";
					RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, animale);
					dataDecesso = (rsr == null) ? null : rsr.getDataEvento();
					break;
				}
				
				ArrayList<LookupTipoTrasferimento> tipiTrasferimenti = LookupTipoTrasferimentoDAO.getTipiTrasferimenti(connection);
				req.setAttribute("tipiTrasferimenti", tipiTrasferimenti);
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				dataEnd  	= new Date();
				utente = ((BUtente) req.getSession().getAttribute( "utente" ));
				timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(tipi trasferimenti)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				/*ArrayList<LookupComuni> lookupComuni  = (ArrayList<LookupComuni>) persistence.createCriteria(LookupComuni.class )
						.addOrder( Order.asc( "id" ) )
						.list();*/
				ArrayList<LookupComuni> lookupComuni  = (ArrayList<LookupComuni>)req.getServletContext().getAttribute("listComuni");
				req.setAttribute("lookupComuni", lookupComuni);
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				dataEnd  	= new Date();
				utente = ((BUtente) req.getSession().getAttribute( "utente" ));
				timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(find comuni)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();

						
						
			}
			
			
			ArrayList<LookupAccettazioneAttivitaEsterna> lookupAttivitaEsterne  = LookupAttivitaEsterneDAO.getAttivitaEsterne(connection);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(find attivitaesterne)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			req.setAttribute("lookupAttivitaEsterne", lookupAttivitaEsterne);
			
			ArrayList<LookupOperazioniAccettazione> operazioniInBdr = new ArrayList<LookupOperazioniAccettazione>();
			ArrayList<LookupOperazioniAccettazione> operazioniNonBdr = new ArrayList<LookupOperazioniAccettazione>();
			ArrayList<LookupOperazioniAccettazione> operazioniRichPrelieviInf = new ArrayList<LookupOperazioniAccettazione>();
			ArrayList<LookupOperazioniAccettazione> operazioniPsADM = new ArrayList<LookupOperazioniAccettazione>();
			ArrayList<LookupOperazioniAccettazione> operazioniPsASC = new ArrayList<LookupOperazioniAccettazione>();
			ArrayList<LookupOperazioniAccettazione> operazioniPsDSS = new ArrayList<LookupOperazioniAccettazione>();
			
			if(animale.getDecedutoNonAnagrafe())
			{
				ArrayList<LookupOperazioniAccettazione> operazioniCovid = LookupOperazioniAccettazioneDAO.getOperazioniCovid(connection);
				req.setAttribute("operazioniCovid", operazioniCovid);
			}
			
			//Filtri comuni a tutte le motivazioni
			ArrayList<LookupOperazioniAccettazione> operazioni = LookupOperazioniAccettazioneDAO.getOperazioniAccettazione(connection, specieAnimale);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(operazioni)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			//Itero tutte le motivazioni e ad ogni occorrenza aggiungo al relativo ArrayList
			Iterator<LookupOperazioniAccettazione> operazioniIter = operazioni.iterator();
			while(operazioniIter.hasNext())
			{
				LookupOperazioniAccettazione temp = operazioniIter.next();
				if( (temp.isInbdr() || temp.getId() == IdOperazioniBdr.ritrovamentoSmarrNonDenunciato ) && !temp.getApprofondimenti() && temp.getId() != IdOperazioniBdr.prelievoDna && temp.getId() != IdOperazioniBdr.prelievoLeishmania)
				{
					operazioniInBdr.add(temp);
				}
				else if(!temp.isInbdr() && !temp.getApprofondimenti() && !temp.getRichiestaPrelieviMalattieInfettive())
				{
					operazioniNonBdr.add(temp);
				}
				else if(!temp.getApprofondimenti() && temp.getRichiestaPrelieviMalattieInfettive())
				{
					operazioniRichPrelieviInf.add(temp);
				}
				else if(temp.getApprofondimenti() && temp.getApprofondimentoDiagnosticoMedicina())
				{
					operazioniPsADM.add(temp);
				}
				else if(temp.getApprofondimenti() && temp.getAltaSpecialitaChirurgica())
				{
					operazioniPsASC.add(temp);
				}
				else if(temp.getApprofondimenti() && temp.getDiagnosticaStrumentale())
				{
					operazioniPsDSS.add(temp);
				}
			}
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(while)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			ArrayList<LookupTipiRichiedente> richiedenti = new ArrayList<LookupTipiRichiedente>();
			ArrayList<LookupTipiRichiedente> richiedentiForzaPubblica = new ArrayList<LookupTipiRichiedente>();
			
			//Filtri comuni a tutti i richiedentei
			ArrayList<LookupTipiRichiedente> richiedentiAll = LookupTipiRichiedenteDAO.getTipiRichiedente(connection);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(richiedentiAll)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			//Itero tutte le motivazioni e ad ogni occorrenza aggiungo al relativo ArrayList
			Iterator<LookupTipiRichiedente> richiedentiIter = richiedentiAll.iterator();
			while(richiedentiIter.hasNext())
			{
				LookupTipiRichiedente temp = richiedentiIter.next();
				if(temp.getForzaPubblica())
				{
					richiedentiForzaPubblica.add(temp);
				}
				else if(!temp.getForzaPubblica())
				{
					richiedenti.add(temp);
				}
			}
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(richiedentiIter)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
					
			
			ArrayList<LookupAsl> listaAltreAsl = LookupAslDAO.getAltreAsl(connection, utente.getClinica().getLookupAsl().getId());
					
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(list altre asl)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			req.setAttribute("listaAltreAsl", listaAltreAsl);
			ArrayList<LookupAsl> listaAsl = LookupAslDAO.getAsl(connection);
			req.setAttribute( "asl", listaAsl );		
					
			ArrayList<LookupAssociazioni> associazioni = LookupAssociazioniDAO.getAssociazioni(connection);

			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(associazioni)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			ArrayList<Integer> operazioniRichiesteId = new ArrayList<Integer>();
			Iterator<LookupOperazioniAccettazione> iter = accettazione.getOperazioniRichieste().iterator();
			while(iter.hasNext())
			{
				operazioniRichiesteId.add(iter.next().getId());
			}
			
			
			ArrayList<LookupPersonaleInterno> personaleInterno = LookupAslDAO.getPersonaleInterno(connection, utente.getClinica().getLookupAsl());
			req.setAttribute( "personaleInterno", personaleInterno );
			
			
			
			req.setAttribute( "operazioniRichiesteId", operazioniRichiesteId );
			req.setAttribute( "dataDecesso", dataDecesso );
			req.setAttribute( "dataDecessoCertezza", dataDecessoCertezza );
			req.setAttribute( "dataNascitaCertezza", dataNascitaCertezza );
			req.setAttribute( "idOpsBdr", IdOperazioniBdr.getInstance() );
			req.setAttribute( "idRichiesteVarie", IdRichiesteVarie.getInstance() );
			req.setAttribute( "idTipiRichiedente", IdTipiRichiedente.getInstance() );
			req.setAttribute( "modify", modify );
			req.setAttribute( "animale", animale );
			req.setAttribute( "accettazione", accettazione );
			req.setAttribute( "richiedenti", richiedenti );
			req.setAttribute( "richiedentiForzaPubblica", richiedentiForzaPubblica );
			req.setAttribute( "associazioni", associazioni );
			req.setAttribute( "operazioniInBdr", operazioniInBdr );
			req.setAttribute( "operazioniNonBdr", operazioniNonBdr );
			req.setAttribute( "operazioniPsADM", operazioniPsADM );
			req.setAttribute( "operazioniRichPrelieviInf", operazioniRichPrelieviInf );
			req.setAttribute( "operazioniPsASC", operazioniPsASC );
			req.setAttribute( "operazioniPsDSS", operazioniPsDSS );
			req.setAttribute( "specie", SpecieAnimali.getInstance() );
			req.setAttribute( "idOps", 		IdOperazioniDM.getInstance() );
			
			
		//	if(animale.getDecedutoNonAnagrafe() || animale.getLookupSpecie().getId()==Specie.SINANTROPO)
		//		req.setAttribute("causaMorte", animale.getCausaMorte());

			req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(animale, cane, gatto, persistence, utente, status, connectionBdu,req));
			req.setAttribute("flagAnagrafe", stringaFromRequest("flagAnagrafe"));
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(pre gotoPage)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			gotoPage( "/jsp/vam/accettazione/add.jsp" );
			
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.accettazione.ToAdd.us(post gotoPage)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
		/*}
		else //endif status.isAllRight
		{
			setErrore( status.getError() );
			gotoPage( "/jsp/errore/errore.jsp" );
		}*/
	
	}else{
		req.setAttribute( "errore_apertura_nuova_accettazione", esit_descrizione);
		//gotoPage( "/jsp/vam/accettazione/riepilogoAnimale.jsp" );
		animale			= AnimaleDAO.getAnimale(idAnimale, connection);
		req.setAttribute( "identificativo", animale.getIdentificativo());
		
		String from = (String) stringaFromRequest("from");
		
		if (from == null || ("").equals(from)){
			goToAction(new FindAnimale(), req, res);
		}
		else{
			req.setAttribute( "idAnimale", animale.getId());
			goToAction(new List(), req, res);
			
		}
	
	}
	}

}
