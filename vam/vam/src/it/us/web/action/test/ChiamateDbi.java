/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.test;
import java.sql.Connection;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.RegistrazioniCanina;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.UrlUtil;
import it.us.web.util.properties.Application;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.CaninaRemoteUtilH;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtilH;
import it.us.web.util.vam.RegistrazioniUtil;

public class ChiamateDbi extends GenericAction
{
	@Override
	public void can() throws AuthorizationException
	{
		
	}

	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		
		ServicesStatus status = new ServicesStatus();
		
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "1" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		CaninaRemoteUtil.findCane("380260080283505", utente, status, connection, req);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "2" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		CaninaRemoteUtil.findProprietario("380260080283505", utente, connection, req);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "3" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		CaninaRemoteUtil.findRegistrazioniEffettuabili("380260080283505", utente, connection, req);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "4" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		FelinaRemoteUtil.findColonia("977200008177495", utente, connection, req);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "5" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		FelinaRemoteUtil.findGatto("977200008177495", utente, status, connection, req);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "6" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		FelinaRemoteUtil.findProprietario("977200008177495", utente, connection, req);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "7" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
		System.out.println("Modulo in esecuzione: " + "1" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		CaninaRemoteUtilH.findCane("380260080283505", utente, status, connection);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "2" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		CaninaRemoteUtilH.findProprietario("380260080283505", utente, connection);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "3" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		CaninaRemoteUtilH.findRegistrazioniEffettuabili("380260080283505", utente, connection);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "4" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		FelinaRemoteUtilH.findColonia("977200008177495", utente, connection);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "5" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		FelinaRemoteUtilH.findGatto("977200008177495", utente, status, connection);
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "6" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		FelinaRemoteUtilH.findProprietario("977200008177495", utente, connection);
		
			
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "7" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}
}
