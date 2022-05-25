/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.dimissioni;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.StatoTrasferimento;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.SinantropoDAO;
import it.us.web.dao.lookup.LookupDestinazioneAnimaleDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.CCUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToEdit extends GenericAction 
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("dimissioni");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
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
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(conn bdu)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
	
			ArrayList<LookupDestinazioneAnimale> destinazioneAnimale = null;

			Set<Trasferimento> listaTrasferimenti = cc.getTrasferimentiByCcPostTrasf();
			int tipologiaAnimale = cc.getAccettazione().getAnimale().getLookupSpecie().getId();
			
			if (tipologiaAnimale == Specie.CANE) {
				
				if (listaTrasferimenti != null && listaTrasferimenti.size() > 0){
					//destinazioneAnimale = (ArrayList<LookupDestinazioneAnimale>) persistence.createCriteria( LookupDestinazioneAnimale.class ).add( Restrictions.eq( "cane", true ) ).add( Restrictions.eq( "dimissioniCcTrasferimento", true ) ).addOrder( Order.asc( "level" ) ).list();
					destinazioneAnimale=LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, "cane", true);
					
				}else{
			
				//destinazioneAnimale = (ArrayList<LookupDestinazioneAnimale>) persistence.createCriteria( LookupDestinazioneAnimale.class ).add( Restrictions.eq( "cane", true ) )		.addOrder( Order.asc( "level" ) ).list();
				destinazioneAnimale=LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, "cane", false);
				
				}
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(dest animale)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
			}
			
			else if (tipologiaAnimale == Specie.GATTO) {
				
				if (listaTrasferimenti != null && listaTrasferimenti.size() > 0){
					//destinazioneAnimale = (ArrayList<LookupDestinazioneAnimale>) persistence.createCriteria( LookupDestinazioneAnimale.class ).add( Restrictions.eq( "gatto", true ) ).add( Restrictions.eq( "dimissioniCcTrasferimento", true ) ).addOrder( Order.asc( "level" ) ).list();
					destinazioneAnimale=LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, "gatto", true);
					
				}else{
				
				//destinazioneAnimale = (ArrayList<LookupDestinazioneAnimale>) persistence.createCriteria( LookupDestinazioneAnimale.class ).add( Restrictions.eq( "gatto", true ) )			.addOrder( Order.asc( "level" ) ).list();
				destinazioneAnimale=LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, "gatto", false);
				}
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(dest animale 2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
			}
			
			if (tipologiaAnimale == Specie.SINANTROPO) {
				
				if (listaTrasferimenti != null && listaTrasferimenti.size() > 0){
					//destinazioneAnimale = (ArrayList<LookupDestinazioneAnimale>) persistence.createCriteria( LookupDestinazioneAnimale.class ).add( Restrictions.eq( "sinantropo", true ) ).add( Restrictions.eq( "dimissioniCcTrasferimento", true ) ).addOrder( Order.asc( "level" ) ).list();
					destinazioneAnimale=LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, "sinantropo", true);
					
				}else{
				
				//destinazioneAnimale = (ArrayList<LookupDestinazioneAnimale>) persistence.createCriteria( LookupDestinazioneAnimale.class ).add( Restrictions.eq( "sinantropo", true ) )			.addOrder( Order.asc( "level" ) ).list();
				destinazioneAnimale=LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, "sinantropo", false);
				}
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(dest animale 3)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				//req.setAttribute("codiceIspra", SinantropoUtil.getSinantropoByNumero(persistence, cc.getAccettazione().getAnimale().getIdentificativo()).getCodiceIspra());
				req.setAttribute("codiceIspra", SinantropoDAO.getCodiceIspra(connection, cc.getAccettazione().getAnimale().getIdentificativo()));
			}
			
			

			
			
			//Se si è nella fase di riconsegna di un trasferimento, le dimissioni redirigono
			//alla gestione dello stessa
			if (CCUtil.riconsegnaPossibile(cc) == true) {
				redirectTo("vam.cc.trasferimenti.ToRiconsegna.us");
			}
			
	
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			 dataEnd  	= new Date();
			 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(cmi)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			ServicesStatus status = new ServicesStatus();
			
			if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 1) {
				RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connectionBdu ,req);
				
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(info decesso)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				//Errore nella comunicazione con il Wrapper
				if (!status.isAllRight()) {
					setMessaggio("Errore nella comunicazione con la BDR di riferimento");
					goToAction(new it.us.web.action.vam.cc.Detail());
				}
				
				req.setAttribute("res", res);
			}
			else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 2) {
				RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connectionBdu ,req);
				
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(info decesso 2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				//Errore nella comunicazione con il Wrapper
				if (!status.isAllRight()) {
					setMessaggio("Errore nella comunicazione con la BDR di riferimento");
					goToAction(new it.us.web.action.vam.cc.Detail());
				}
				
				req.setAttribute("res", rfr);
			}
			else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 3) {
				//RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
				RegistrazioniSinantropiResponse rsr = SinantropoDAO.getInfoDecesso(connection, cc.getAccettazione().getAnimale());
				
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(info decesso 3)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				req.setAttribute("res", rsr);
			}
			//req.setAttribute("listCMI", listCMI);
			//Fine parte inerente i dati della morte
			
			
			
			req.setAttribute( "destinazioneAnimale", destinazioneAnimale );
			
			req.setAttribute( "opRichieste", IdRichiesteVarie.getInstance() );

				
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(pre gotoPage)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			gotoPage( "/jsp/vam/cc/dimissioni/edit.jsp" );
			
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "vam.dimissioni.ToEdit.us(post gotoPage)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
	}

}
