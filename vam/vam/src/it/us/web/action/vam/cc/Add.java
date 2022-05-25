/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc;

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
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.bean.vam.lookup.LookupEventoAperturaCc;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.RuoloDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.lookup.LookupEventoAperturaCcDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.dao.vam.FascicoloSanitarioDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CCUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;






import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class Add extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("cc");
	}

	public void execute() throws Exception
	{
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		Cane cane = null;
		Gatto gatto = null;
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBDU = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBDU);
		final Logger logger = LoggerFactory.getLogger(Add.class);
		final DecimalFormat decimalFormatFascicolo = new DecimalFormat( "000000000" );
		
		
		
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(conn bdu)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
		
		
		CartellaClinica cc		= new CartellaClinica();
		int idClinica 			= utente.getClinica().getId();
		int idAccettazione 		= interoFromRequest("idAccettazione");
		
		try {
			Context ctxVAM = new InitialContext();
			javax.sql.DataSource dsVAM = (javax.sql.DataSource) ctxVAM.lookup("java:comp/env/jdbc/vamM");
			connection = dsVAM.getConnection();
			
			
			//Accettazione acc = (Accettazione) persistence.find(Accettazione.class, idAccettazione);
			Accettazione acc = AccettazioneDAO.getAccettazione(idAccettazione,connection);
			//animale = accettazione.getAnimale();
			
			
			
			if (acc.getCartellaClinicas().isEmpty()){
			
			 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			 dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(find acc)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			Animale animale  = acc.getAnimale();
			ArrayList<CartellaClinica> cartelleCliniche = new ArrayList<CartellaClinica>();
			//Recupera le cc tramite animale.getAccettaziones()
			//cartelleCliniche = persistence.getNamedQuery("GetCCByIdCane").setInteger("idAnimale", acc.getAnimale().getId()).list();
			if (cartelleCliniche.size()>0){
					setErrore( "Non è possibile aprire una nuova cartella clinica: esiste già la cc " + cartelleCliniche.get(0).getNumero() + " aperta nella clinica " + cartelleCliniche.get(0).getEnteredBy().getClinica().getNome() +" per lo stesso animale." );
					redirectTo( "vam.accettazione.Detail.us?id=" + acc.getId() );
					
					
					 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					utente = ((BUtente) req.getSession().getAttribute( "utente" ));
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(get cc bycane)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
			}
			else 
			{
				FascicoloSanitario fascicolo = null;
				CartellaClinica ccVivo = acc.getCcVivo();
				if(ccVivo!=null)
				{
					//Eredita il fascicolo sanitario della cc del vivo
					fascicolo = ccVivo.getFascicoloSanitario();
				}
				else
				{
					//Generazione nuovo fascicolo sanitario
					fascicolo = new FascicoloSanitario();
					fascicolo.setId(FascicoloSanitarioDAO.getNextProgressivo(connection));
					fascicolo.setNumero( decimalFormatFascicolo.format(fascicolo.getId()) );
					fascicolo.setDataApertura(acc.getData());
					fascicolo.setEntered(new Date());
					fascicolo.setEnteredBy(utente);
					fascicolo.setModified(new Date());
					fascicolo.setModifiedBy(utente);
				}
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				utente = ((BUtente) req.getSession().getAttribute( "utente" ));
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(set fascicolo)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				//Creazione cartella clinica
				int progressivo = CCUtil.getSequence(connection, idClinica,acc.getData().getYear());
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				utente = ((BUtente) req.getSession().getAttribute( "utente" ));
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(getsequence)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				//String numeroCC = CCUtil.assignedNumber(persistence, progressivo, idClinica,acc.getData().getYear());
				String numeroCC = CCUtil.assignedNumber(connection, progressivo, idClinica,acc.getData().getYear());
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				utente = ((BUtente) req.getSession().getAttribute( "utente" ));
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(get assigned)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				
				cc.setDataApertura(acc.getData());
				cc.setAccettazione(acc);
				cc.setEntered(new Date());
				cc.setEnteredBy(utente);
				cc.setRuoloEnteredBy( ((HashMap<Integer, String>)context.getAttribute("ruoliUtenti")).get(utente.getSuperutente().getId()) );
				cc.setModified( cc.getEntered() );
				cc.setModifiedBy( utente );
				cc.setFascicoloSanitario(fascicolo);
				cc.setProgressivo(progressivo);
				cc.setNumero(numeroCC);
				
				if(ccVivo!=null)
				{
					LookupEventoAperturaCc evento = LookupEventoAperturaCcDAO.getEvento(4,connection);
					cc.setEventoApertura(evento);
				}
				else
				{
					LookupEventoAperturaCc evento = LookupEventoAperturaCcDAO.getEvento(1,connection);
					cc.setEventoApertura(evento);
				}
				
				// di default
				cc.setDayHospital(false);
				

				//Se il cane non è morto senza mc bisogna leggere in bdr le info sulla morte
				ServicesStatus status = new ServicesStatus();
				Boolean decedutoNonAnagrafe = animale.getDecedutoNonAnagrafe();
						
				if (animale.getLookupSpecie().getId() == Specie.CANE && !decedutoNonAnagrafe) 
				{
					cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), utente, status, connectionBdu,req);
					RegistrazioniCaninaResponse res = CaninaRemoteUtil.getInfoDecesso( cane );
					
					req.setAttribute("res", res);
					if(res!=null && res.getDataEvento()!=null)
						cc.setCcMorto(true);
				}
				else if (animale.getLookupSpecie().getId() == Specie.GATTO && !decedutoNonAnagrafe) 
				{
					gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), utente, status, connectionBdu,req);
					RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( gatto );
					
					req.setAttribute("res", rfr);
					if(rfr!=null && rfr.getDataEvento()!=null)
						cc.setCcMorto(true);
				}
				else if (animale.getLookupSpecie().getId() == Specie.SINANTROPO && !decedutoNonAnagrafe) 
				{
					RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
					req.setAttribute("res", rsr);
					if(rsr!=null && rsr.getDataEvento()!=null)
						cc.setCcMorto(true);
				}
				
				
				/* Gestione per la richiesta delle operazioni su un cane morto.
				 * Deve dare vita all'apertura di una CC solo con Decesso e Autopsia*/
				LookupOperazioniAccettazione autopsia			= LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.esameAutoptico, connection);
				LookupOperazioniAccettazione smaltimentoCarogna	= LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.smaltimentoCarogna, connection);
					
				if(decedutoNonAnagrafe)
				{
					cc.setCcMorto(true);
				}
				
				if(!cc.getCcMorto())
				{
					for(Accettazione accett:animale.getAccettaziones())
					{
						Set<LookupOperazioniAccettazione> lop = (Set<LookupOperazioniAccettazione>) acc.getOperazioniRichieste();
						if(lop.contains(autopsia) || lop.contains(smaltimentoCarogna))
						{
							cc.setCcMorto(true);
							break;
						}
						for(CartellaClinica ccCurr:accett.getCartellaClinicas())
						{
							if(ccCurr.getAutopsia()!=null || (ccCurr.getEsameIstopatologicos()!=null && !ccCurr.getEsameIstopatologicos().isEmpty()))
							{	
								cc.setCcMorto(true);
								break;
							}
						}
						if(cc.getCcMorto())
							break;
					}
				}
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				utente = ((BUtente) req.getSession().getAttribute( "utente" ));
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(setccMorto)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				
				if(ccVivo==null)
				{
					persistence.insert(fascicolo);
					
					 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					utente = ((BUtente) req.getSession().getAttribute( "utente" ));
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(insert fascicolo)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
				}
				
				persistence.insert(cc);	
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				utente = ((BUtente) req.getSession().getAttribute( "utente" ));
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(insert cc)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				persistence.commit();
			}
			} else{
				Iterator i = acc.getCartellaClinicas().iterator();
				while (i.hasNext()){
					cc = (CartellaClinica) i.next();
				}
				ServicesStatus status = new ServicesStatus();
				if (acc.getAnimale().getLookupSpecie().getId() == Specie.CANE && !acc.getAnimale().getDecedutoNonAnagrafe()) 
				{
					cane = CaninaRemoteUtil.findCane(acc.getAnimale().getIdentificativo(), utente, status, connectionBdu,req);
					RegistrazioniCaninaResponse res = CaninaRemoteUtil.getInfoDecesso( cane );
					
					req.setAttribute("res", res);
					if(res!=null && res.getDataEvento()!=null)
						cc.setCcMorto(true);
				}
				else if (acc.getAnimale().getLookupSpecie().getId() == Specie.GATTO && !acc.getAnimale().getDecedutoNonAnagrafe()) 
				{
					gatto = FelinaRemoteUtil.findGatto(acc.getAnimale().getIdentificativo(), utente, status, connectionBdu,req);
					RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( gatto );
					
					req.setAttribute("res", rfr);
					if(rfr!=null && rfr.getDataEvento()!=null)
						cc.setCcMorto(true);
				}
				else if (acc.getAnimale().getLookupSpecie().getId() == Specie.SINANTROPO && !acc.getAnimale().getDecedutoNonAnagrafe()) 
				{
					RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
					req.setAttribute("res", rsr);
					if(rsr!=null && rsr.getDataEvento()!=null)
						cc.setCcMorto(true);
				}
				setErrore("Esiste già una Cartella Clinica associata per questa accettazione");
			}
			
		}
		catch (RuntimeException e)
		{
			try
			{		
				persistence.rollBack();				
			}
			catch (HibernateException e1)
			{				
				logger.error("Error during Rollback transaction" + e1.getMessage());
			}
			logger.error("Cannot save Cartella Clinica" + e.getMessage());
			throw e;		
		}
				

		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(cc.getAccettazione().getAnimale(), cane, gatto, persistence, utente, new ServicesStatus(), connectionBdu,req));
		
		memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(pre redirectTo)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		redirectTo( "vam.cc.Detail.us?idCartellaClinica="+cc.getId() );	
		
		memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "vam.cc.Add.us(post redirectTo)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();

		
	}
	
	
}
