/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Colonia;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.Indirizzo;
import it.us.web.bean.remoteBean.Proprietario;
import it.us.web.bean.remoteBean.Registrazioni;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniInterface;
import it.us.web.bean.remoteBean.Telefono;
import it.us.web.bean.sinantropi.Detenzioni;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AccettazioneNoH;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleAnagrafica;
import it.us.web.bean.vam.AnimaleAnagraficaNoH;
import it.us.web.bean.vam.AnimaleNoH;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.Specie;
import it.us.web.dao.SinantropoDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupAssociazioniDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
import it.us.web.dao.lookup.LookupTaglieDAO;
import it.us.web.dao.lookup.LookupTipiRichiedenteDAO;
import it.us.web.dao.lookup.LookupTipoTrasferimentoDAO;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.dao.vam.AnimaleDAO;
import it.us.web.exceptions.ValidationBeanException;
import it.us.web.util.DateUtils;
import it.us.web.util.dwr.vam.accettazione.Test;
import it.us.web.util.sinantropi.SinantropoUtil;

public class AnimaliUtil implements Specie
{

	Logger logger = LoggerFactory.getLogger( AnimaliUtil.class );
	
	/**
	 * 1)   cerca localmente l'animale<br/>
	 * 2)   se non lo trova lo cerca nelle banche dati remote (canina, felina, sinantropi)<br/>
	 * 2.1) se lo trova in una delle banche dati lo inserisce localmente
	 * @param identificativo
	 * @param persistence
	 * @param utente 
	 * @param status 
	 * @return Il bean dell'animale corrispondente se trovato, null altrimenti
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String,Object> fetchAnimale(String identificativo, Persistence persistence, BUtente utente, ServicesStatus status,Connection connection, HttpServletRequest req)
		throws Exception
	{
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		HashMap<String,Object> ret 	 = new HashMap<String, Object>();
		boolean inLocale = false;
		boolean updateTatuaggio = false;
		
		if( (identificativo != null) && (identificativo.length() > 0) )
		{
			//cerco l'animale in locale
			List<Animale> list = persistence.getNamedQuery( "GetAnimaleByIdentificativo" ).setString( "identificativo", identificativo ).list();
			
			long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			Date dataEnd  	= new Date();
			long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(getAnById)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			if( list.size() > 0 )  //animale trovato in locale
			{
				ret.put("animale", list.get( 0 ));
				inLocale = true;
			}
			
			if( ret.get("animale") == null )
			{
				//cerco tra i sinantropi
				Sinantropo sin = SinantropoUtil.getSinantropoByNumero( persistence, identificativo );
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(getSin)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if( sin != null )
				{
					Animale an = new Animale();
					an.setLookupSpecie( (LookupSpecie) persistence.find( LookupSpecie.class, SINANTROPO ) );
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(specie sin)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();

					an.setEntered( new Date() );
					an.setModified( an.getEntered() );
					
					//Per l'accesso esterno degli LLPP
					if(utente!=null)
					{
						an.setEnteredBy( utente.getId() );
						an.setModifiedBy( utente.getId() );
					}
					an.setIdentificativo( identificativo );
					
					an.setDataNascitaPresunta( true );
					an.setDataNascita(sin.getDataNascitaPresunta());
					an.setEta(sin.getEta());
					an.setSesso( sin.getSesso() );
					if(sin.getTaglia()!=null)
						an.setTaglia( sin.getTaglia().getId());
					an.setRazzaSinantropo(sin.getRazza());
					an.setMantelloSinantropo(sin.getMantello());
					
					if (sin.getLookupSpecieSinantropi().getUccello()==true)
						an.setSpecieSinantropo("1");
					else if (sin.getLookupSpecieSinantropi().getMammifero()==true)
						an.setSpecieSinantropo("2");
					else if (sin.getLookupSpecieSinantropi().getRettileAnfibio()==true)
						an.setSpecieSinantropo("3");
					else if (sin.getLookupSpecieSinantropi().getUccelloZ()==true)
						an.setSpecieSinantropo("1");
					else if (sin.getLookupSpecieSinantropi().getMammiferoZ()==true)
						an.setSpecieSinantropo("2");
					else if (sin.getLookupSpecieSinantropi().getRettileAnfibioZ()==true)
						an.setSpecieSinantropo("3");
					else if (sin.getLookupSpecieSinantropi().getMammiferoCetaceo()==true)
						an.setSpecieSinantropo("1");
					else if (sin.getLookupSpecieSinantropi().getRettileTestuggine()==true)
						an.setSpecieSinantropo("2");
					else if (sin.getLookupSpecieSinantropi().getSelaci()==true)
						an.setSpecieSinantropo("3");
					
					ret.put("animale", an);

				}
			}
			
			if( ret.get("animale") == null ) //se non l'ho trovato cerco l'animale in remoto e se lo trovo lo salvo in locale. comincio con canina
			{
				//cerco tra i cani
				Cane cane = CaninaRemoteUtil.findCane( identificativo, status, connection,req );
				
				
				
				if (cane != null && cane.getMc() != null && !("").equals(cane.getMc()) && cane.getTatuaggio()!=null && !(cane.getTatuaggio()).equals(cane.getMc())){
				//Controllo se nn esisteva già come primo microchip, nel qual caso prendo l'animale esistente
				
					list = persistence.getNamedQuery( "GetAnimaleByIdentificativo" ).setString( "identificativo", cane.getMc() ).list();
					
					if (list.size() > 0) {
						Animale animaleFound = (Animale) list.get( 0 );
						animaleFound.setTatuaggio(identificativo);
						ret.put("animale", animaleFound);
						inLocale = true;
						updateTatuaggio = true;
					}
				
				}
				
				
				ret.put("cane", cane);
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(finfCane)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if( /*status.isAllRight() &&*/ ( cane != null && !inLocale ) )
				{
					Animale an = new Animale();
					an.setLookupSpecie( (LookupSpecie) persistence.find( LookupSpecie.class, CANE ) );
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(find tipo cane)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
					an.setTaglia( cane.getIdTaglia() );
					an.setEntered( new Date() );
					an.setModified( an.getEntered() );
					
					//Per l'accesso esterno degli LLPP
					if(utente!=null)
					{
						an.setEnteredBy( utente.getId() );
						an.setModifiedBy( utente.getId() );
					}
					an.setIdentificativo( cane.getMc() );
					
					an.setDataNascita( cane.getDataNascita() );
					an.setSesso( cane.getSesso() );
					an.setRazza( cane.getRazza() );
					an.setMantello( cane.getMantello() );
					an.setTatuaggio(cane.getTatuaggio());
					
					ret.put("animale", an);
				}
			}
				
			if( /*status.isAllRight() &&*/ (ret.get("animale") == null) )
			{
					//cerco tra i gatti
					Gatto gatto = FelinaRemoteUtil.findGatto( identificativo, status, connection, req );
					
					
					//Controllo se nn esisteva già come primo microchip, nel qual caso prendo l'animale esistente
					
					if (gatto != null && gatto.getMc() != null && !("").equals(gatto.getMc()) && gatto.getTatuaggio()!=null && !(gatto.getTatuaggio()).equals(gatto.getMc())){
					
					list = persistence.getNamedQuery( "GetAnimaleByIdentificativo" ).setString( "identificativo", gatto.getMc() ).list();					
					if (list.size() > 0) {
						Animale animaleFound = (Animale) list.get( 0 );
						animaleFound.setTatuaggio(identificativo);
						ret.put("animale", animaleFound);
						inLocale = true;
						updateTatuaggio = true;
					}
				}
				
				
				ret.put("gatto", gatto);
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(findGatto)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if( /*status.isAllRight() &&*/ (gatto != null && !inLocale) )
				{
					Animale an = new Animale();
					an.setLookupSpecie( (LookupSpecie) persistence.find( LookupSpecie.class, GATTO ) );
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(get tipo sin)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
					if( gatto.getIdTaglia() != null && gatto.getIdTaglia() > 0 )
					{
						an.setTaglia( gatto.getIdTaglia() );
					}
					an.setEntered( new Date() );
					an.setModified( an.getEntered() );
					an.setEnteredBy( utente.getId() );
					an.setModifiedBy( utente.getId() );
					an.setIdentificativo( identificativo );
					an.setTatuaggio(gatto.getTatuaggio());
					Colonia colonia = FelinaRemoteUtil.findColonia(identificativo, utente, connection,req);
					if(colonia!=null )
					{
						ret.put("colonia", colonia);
						an.setInColonia(true);
					}
					else
						an.setInColonia(false);
					
					
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(findColonia)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
					
					an.setDataNascita( gatto.getDataNascita() );
					an.setSesso( gatto.getSesso() );
					
					if (gatto.getRazza() == null)
						an.setRazza( 0 );
					else 
						an.setRazza(gatto.getRazza());
					
					if (gatto.getMantello() == null)
						an.setMantello( gatto.getMantello() );
					else 
						an.setMantello( 0);
					
					ret.put("animale", an);
					
				}
			}
			
			if( ret.get("animale") != null && !inLocale ) //ho trovato l'animale e non era in locale, è necessario l'inserimento nella tabella animale
			{
				//salvo l'animale
				try
				{
					GenericAction.validaBean( (Animale)ret.get("animale"), null );
				}
				catch(ValidationBeanException e)
				{
					System.out.println(e.getMessage());
				}
				persistence.insert( ret.get("animale") );
				persistence.commit();
			} else  if (updateTatuaggio){
				persistence.update(ret.get("animale"));
				persistence.commit();
				
			}
				
		}//endif (controllo identificativo non null/vuoto)
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		
		System.out.println("Modulo eseguito: " + "AnimaliUtil.fetchAnimale" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		
		return ret;
	}
	
	
	
	public static HashMap<String,Object> getDatiAnimaleAccMultipla(String mc, Connection connection, Persistence persistence, Connection connectionBdu, HttpServletRequest req ) throws Exception
	{
		Cane cane 		= CaninaRemoteUtil.findCane( mc, new ServicesStatus(), connectionBdu,req );
		Gatto gatto     = null;
		if(cane==null)
			gatto       = FelinaRemoteUtil.findGatto( mc, new ServicesStatus(), connectionBdu,req );
		
		
		HashMap<String, Object> datiAnimale = new HashMap<String, Object>();
		if(cane!=null)
		{
			datiAnimale.put("idSpecie", 1);
			datiAnimale.put("opId", cane.getIdProprietario());
			datiAnimale.put("opIdDetentore", cane.getIdDetentore());
			datiAnimale.put("tipologiaSoggetto", 1);
			datiAnimale.put("dataRegistrazione", cane.getDataRegistrazione());
			datiAnimale.put("razza", cane.getRazza());
			datiAnimale.put("sesso", cane.getSesso());
			datiAnimale.put("dataNascita", cane.getDataNascita());
			datiAnimale.put("mantello", cane.getMantello());
			datiAnimale.put("taglia", cane.getIdTaglia());
			datiAnimale.put("dataChippatura", cane.getDataChippatura());
		}
		else if(gatto!=null)
		{
			datiAnimale.put("idSpecie", 2);
			datiAnimale.put("opId", gatto.getIdProprietario());
			datiAnimale.put("opIdDetentore", gatto.getIdDetentore());
			datiAnimale.put("tipologiaSoggetto", 1);
			datiAnimale.put("dataRegistrazione", gatto.getDataRegistrazione());
			datiAnimale.put("razza", gatto.getRazza());
			datiAnimale.put("sesso", gatto.getSesso());
			datiAnimale.put("dataNascita", gatto.getDataNascita());
			datiAnimale.put("mantello", gatto.getMantello());
			datiAnimale.put("taglia", gatto.getIdTaglia());
			datiAnimale.put("dataChippatura", gatto.getDataChippatura());
		}
		return datiAnimale;
	}
	
	public static HashMap<String,Object> fetchAnimale(String identificativo, Connection connection, Persistence persistence, BUtente utente, ServicesStatus status,Connection connectionBdu, HttpServletRequest req)
			throws Exception
		{
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		HashMap<String,Object> ret 	 = new HashMap<String, Object>();
		boolean inLocale = false;
		boolean updateTatuaggio = false;
		
		if( (identificativo != null) && (identificativo.length() > 0) )
		{
			//cerco l'animale in locale
			Animale an2 = AnimaleDAO.getAnimale(identificativo, connection);
			
			long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			Date dataEnd  	= new Date();
			long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(getAnById)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			if( an2!=null )  //animale trovato in locale
			{
				ret.put("animale", an2);
				inLocale = true;
			}
			
			if( ret.get("animale") == null )
			{
				//cerco tra i sinantropi
				Sinantropo sin = SinantropoUtil.getSinantropoByNumero( persistence, identificativo );
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(getSin)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if( sin != null )
				{
					Animale an = new Animale();
					an.setLookupSpecie( (LookupSpecie) persistence.find( LookupSpecie.class, SINANTROPO ) );
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(specie sin)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();

					an.setEntered( new Date() );
					an.setModified( an.getEntered() );
					
					//Per l'accesso esterno degli LLPP
					if(utente!=null)
					{
						an.setEnteredBy( utente.getId() );
						an.setModifiedBy( utente.getId() );
					}
					an.setIdentificativo( identificativo );
					
					an.setDataNascitaPresunta( true );
					an.setDataNascita(sin.getDataNascitaPresunta());
					an.setEta(sin.getEta());
					an.setSesso( sin.getSesso() );
					if(sin.getTaglia()!=null)
						an.setTaglia( sin.getTaglia().getId());
					an.setRazzaSinantropo(sin.getRazza());
					an.setMantelloSinantropo(sin.getMantello());
					
					if (sin.getLookupSpecieSinantropi().getUccello()==true)
						an.setSpecieSinantropo("1");
					else if (sin.getLookupSpecieSinantropi().getMammifero()==true)
						an.setSpecieSinantropo("2");
					else if (sin.getLookupSpecieSinantropi().getRettileAnfibio()==true)
						an.setSpecieSinantropo("3");
					
					ret.put("animale", an);

				}
			}
			
			if( ret.get("animale") == null ) //se non l'ho trovato cerco l'animale in remoto e se lo trovo lo salvo in locale. comincio con canina
			{
				//cerco tra i cani
				Cane cane = CaninaRemoteUtil.findCane( identificativo, status, connectionBdu,req );
				
				
				
				if (cane != null && cane.getMc() != null && !("").equals(cane.getMc()) && cane.getTatuaggio()!=null && !(cane.getTatuaggio()).equals(cane.getMc())){
				//Controllo se nn esisteva già come primo microchip, nel qual caso prendo l'animale esistente
				
					an2 = AnimaleDAO.getAnimale(identificativo, connection);
					
					if (an2!=null) {
						Animale animaleFound = (Animale) an2;
						animaleFound.setTatuaggio(identificativo);
						ret.put("animale", animaleFound);
						inLocale = true;
						updateTatuaggio = true;
					}
				
				}
				
				
				ret.put("cane", cane);
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(finfCane)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if( /*status.isAllRight() &&*/ ( cane != null && !inLocale ) )
				{
					Animale an = new Animale();
					an.setLookupSpecie( (LookupSpecie) persistence.find( LookupSpecie.class, CANE ) );
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(find tipo cane)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
					an.setTaglia( cane.getIdTaglia() );
					an.setEntered( new Date() );
					an.setModified( an.getEntered() );
					
					//Per l'accesso esterno degli LLPP
					if(utente!=null)
					{
						an.setEnteredBy( utente.getId() );
						an.setModifiedBy( utente.getId() );
					}
					an.setIdentificativo( cane.getMc() );
					
					an.setDataNascita( cane.getDataNascita() );
					an.setSesso( cane.getSesso() );
					an.setRazza( cane.getRazza() );
					an.setMantello( cane.getMantello() );
					an.setTatuaggio(cane.getTatuaggio());
					
					ret.put("animale", an);
				}
			}
				
			if( /*status.isAllRight() &&*/ (ret.get("animale") == null) )
			{
					//cerco tra i gatti
					Gatto gatto = FelinaRemoteUtil.findGatto( identificativo, status, connectionBdu, req );
					
					
					//Controllo se nn esisteva già come primo microchip, nel qual caso prendo l'animale esistente
					
					if (gatto != null && gatto.getMc() != null && !("").equals(gatto.getMc()) && gatto.getTatuaggio()!=null && !(gatto.getTatuaggio()).equals(gatto.getMc())){
					
					an2 = AnimaleDAO.getAnimale(gatto.getMc(), connection);				
					if (an2!=null) {
						Animale animaleFound = (Animale) an2;
						animaleFound.setTatuaggio(identificativo);
						ret.put("animale", animaleFound);
						inLocale = true;
						updateTatuaggio = true;
					}
				}
				
				
				ret.put("gatto", gatto);
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(findGatto)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if( /*status.isAllRight() &&*/ (gatto != null && !inLocale) )
				{
					Animale an = new Animale();
					an.setLookupSpecie( (LookupSpecie) persistence.find( LookupSpecie.class, GATTO ) );
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(get tipo sin)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
					if( gatto.getIdTaglia() != null && gatto.getIdTaglia() > 0 )
					{
						an.setTaglia( gatto.getIdTaglia() );
					}
					an.setEntered( new Date() );
					an.setModified( an.getEntered() );
					an.setEnteredBy( utente.getId() );
					an.setModifiedBy( utente.getId() );
					an.setIdentificativo( identificativo );
					an.setTatuaggio(gatto.getTatuaggio());
					Colonia colonia = FelinaRemoteUtil.findColonia(identificativo, utente, connectionBdu,req);
					if(colonia!=null )
					{
						ret.put("colonia", colonia);
						an.setInColonia(true);
					}
					else
						an.setInColonia(false);
					
					
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.fetchAnimale(findColonia)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
					
					an.setDataNascita( gatto.getDataNascita() );
					an.setSesso( gatto.getSesso() );
					
					if (gatto.getRazza() == null)
						an.setRazza( 0 );
					else 
						an.setRazza(gatto.getRazza());
					
					if (gatto.getMantello() == null)
						an.setMantello( gatto.getMantello() );
					else 
						an.setMantello( 0);
					
					ret.put("animale", an);
					
				}
			}
			
			if( ret.get("animale") != null && !inLocale ) //ho trovato l'animale e non era in locale, è necessario l'inserimento nella tabella animale
			{
				//salvo l'animale
				try
				{
					GenericAction.validaBean( (Animale)ret.get("animale"), null );
				}
				catch(ValidationBeanException e)
				{
					System.out.println(e.getMessage());
				}
				persistence.insert( ret.get("animale") );
				persistence.commit();
			} else  if (updateTatuaggio){
				persistence.update(ret.get("animale"));
				persistence.commit();
				
			}
				
		}//endif (controllo identificativo non null/vuoto)
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		
		System.out.println("Modulo eseguito: " + "AnimaliUtil.fetchAnimale" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		
		return ret;
	}
	
	/**
	 * Crea un oggetto accettazione precaricato con i dati dell'attuale proprietario dell'animale<br/>
	 * (effettua una consultazione in BDR)
	 * @param animale
	 * @param status 
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public static Accettazione newAccettazione(Persistence persistence, Animale animale, BUtente utente, ServicesStatus status, HttpServletRequest req,Connection connection) throws NumberFormatException, Exception
	{
		Accettazione ret = new Accettazione();
		Proprietario proprietario = null;
		
		Colonia colonia = null;
		
		if( animale.getLookupSpecie().getId() == CANE )
		{
			proprietario = CaninaRemoteUtil.findProprietario( animale.getIdentificativo(), utente, connection, req );
		}
		else if( animale.getLookupSpecie().getId() == GATTO ) //NB gestire le colonie
		{
			if( animale.getInColonia()==null || !animale.getInColonia() )
			{
				proprietario = FelinaRemoteUtil.findProprietario( animale.getIdentificativo(), utente, connection,req );
			}
			else
			{
				colonia = FelinaRemoteUtil.findColonia( animale.getIdentificativo(), utente, connection,req);
				if( colonia != null )
				{
					ret.setProprietarioNome( colonia.getCognomeReferente());
					ret.setProprietarioCognome( "<b>Colonia:</b> " + colonia.getNumeroProtocollo() + ((colonia.getDataRegistrazioneColonia()==null)?(""):(" registrata il: " + DateUtils.dataToString( colonia.getDataRegistrazioneColonia()))) );
					ret.setProprietarioCap(colonia.getCap());
					ret.setProprietarioCodiceFiscale(colonia.getCodiceFiscaleReferente());
					ret.setProprietarioComune(colonia.getCittaColonia());
					ret.setProprietarioDocumento(colonia.getDocumentoIdentita());
					ret.setProprietarioIndirizzo(colonia.getIndirizzoColonia());
					ret.setProprietarioProvincia(colonia.getProvinciaColonia());
					ret.setProprietarioTelefono(colonia.getTelefonoReferente());
					ret.setProprietarioTipo("colonia");
				}
			}
		}
		else if( animale.getLookupSpecie().getId() == SINANTROPO )
		{
			Detenzioni detenzioni = SinantropoUtil.getLastActiveDetentore( persistence, animale.getIdentificativo() );
			
			if( detenzioni == null ||  detenzioni.getLookupDetentori()==null) //detentore null
			{

			}
			else if( detenzioni.getLookupDetentori().getId() == 1 ) //detentore privato
			{
				ret.setProprietarioCap( detenzioni.getComuneDetenzione().getCap() );
				ret.setProprietarioCognome( detenzioni.getDetentorePrivatoCognome() );
				ret.setProprietarioComune( detenzioni.getComuneDetenzione().getDescription() );
				ret.setProprietarioDocumento( 
						(detenzioni.getLookupTipologiaDocumento() != null) ? (detenzioni.getLookupTipologiaDocumento().getDescription() + ": "+ detenzioni.getDetentorePrivatoNumeroDocumento()) : ("") );
				ret.setProprietarioIndirizzo( detenzioni.getLuogoDetenzione() );
				ret.setProprietarioNome( detenzioni.getDetentorePrivatoNome() );
				ret.setProprietarioProvincia( calcolaProvinciaSinantropo(detenzioni.getComuneDetenzione()) );
				ret.setProprietarioTelefono( detenzioni.getDetentorePrivatoTelefono() );
				ret.setProprietarioCodiceFiscale( detenzioni.getDetentorePrivatoCodiceFiscale() );
				ret.setProprietarioTipo("Detentore");
			}
			else //detentore non privato
			{
				if( detenzioni.getLookupDetentori().getLookupComuni() != null )
				{
					ret.setProprietarioCap( detenzioni.getLookupDetentori().getLookupComuni().getCap() );
					ret.setProprietarioComune( detenzioni.getLookupDetentori().getLookupComuni().getDescription() );
					ret.setProprietarioProvincia( calcolaProvinciaSinantropo( detenzioni.getLookupDetentori().getLookupComuni() ) );
				}
				ret.setProprietarioIndirizzo( detenzioni.getLookupDetentori().getIndirizzo() );
				ret.setProprietarioNome( detenzioni.getLookupDetentori().getDescription() );
				ret.setProprietarioTelefono( detenzioni.getLookupDetentori().getTelefono() );
				ret.setProprietarioTipo("Detentore");
			}
			
		}
		
		if( proprietario != null && colonia == null)
		{
			Indirizzo ind = new Indirizzo();
			if( true )//proprietario.getTipologiaIndirizzo() != null && proprietario.getTipologiaIndirizzo().equalsIgnoreCase( "Residenza" ) )
			{
				ind.setCap(proprietario.getCap());
				ind.setCitta(proprietario.getCitta());
				ind.setNazione(proprietario.getNazione());
				ind.setProvincia(proprietario.getProvincia());
//				ind.setTipologia(proprietario.getTipologiaIndirizzo());
				ind.setVia(proprietario.getVia());
			}
			
			Telefono tel = new Telefono();
			if( true )//proprietario.getNumeroTelefono() != null )
			{
					tel.setNumero(proprietario.getNumeroTelefono());
					//tel.setTipologia(proprietario.getTipologiaTelefono());
			}
			
			ret.setProprietarioCap( (ind.getCap() != null) ? (ind.getCap().toString()) : (null) );
			ret.setProprietarioCodiceFiscale( proprietario.getCodiceFiscale() );
			ret.setProprietarioCognome( proprietario.getCognome() );
			ret.setProprietarioTipo( proprietario.getTipo() );
			ret.setProprietarioComune( ind.getCitta() );
			ret.setProprietarioDocumento( proprietario.getDocumentoIdentita() );
			ret.setProprietarioIndirizzo( ind.getVia() );
			ret.setProprietarioNome( proprietario.getNome() );
			ret.setProprietarioProvincia( ind.getProvincia() );
			ret.setProprietarioTelefono( tel.getNumero() );
			ret.setAslAnimale( proprietario.getAslString() );
		}
		else if( colonia != null )
		{
			//
		}
		else if((req.getParameter("randagio")==null || req.getParameter("randagio").equals("false"))&& animale.getDecedutoNonAnagrafe() && ( animale.getLookupSpecie().getId() == GATTO ||  animale.getLookupSpecie().getId() == CANE ||  animale.getLookupSpecie().getId() == SINANTROPO))
		{
			Indirizzo ind = new Indirizzo();
			ind.setCap(req.getParameter("proprietarioCap"));
			ind.setCitta(req.getParameter("proprietarioComune"));
			ind.setProvincia(req.getParameter("proprietarioProvincia"));
			ind.setVia(req.getParameter("proprietarioIndirizzo"));
			
			Telefono tel = new Telefono();
			tel.setNumero(req.getParameter("proprietarioTelefono"));
			
			ret.setProprietarioCap( (ind.getCap() != null) ? (ind.getCap().toString()) : (null) );
			ret.setProprietarioCodiceFiscale(req.getParameter("proprietarioCodiceFiscale"));
			ret.setProprietarioCognome(req.getParameter("proprietarioCognome"));
			ret.setProprietarioTipo(req.getParameter("proprietarioTipo"));
			ret.setProprietarioComune(req.getParameter("proprietarioComune"));
			ret.setProprietarioDocumento(req.getParameter("proprietarioDocumento"));
			ret.setProprietarioIndirizzo( ind.getVia() );
			ret.setProprietarioNome(req.getParameter("proprietarioNome"));
			ret.setProprietarioProvincia( ind.getProvincia() );
			ret.setProprietarioTelefono( tel.getNumero() );
		}
		else if(req.getParameter("randagio")!=null)
		{
			ret.setRandagio(req.getParameter("randagio")!=null);
			if((ret.getRandagio()!=null && ret.getRandagio()) && req.getParameter("comuneSindaco")!=null && Integer.parseInt(req.getParameter("comuneSindaco"))>0)
				ret.setProprietarioNome("Sindaco del comune di " + ((LookupComuni)persistence.find(LookupComuni.class, Integer.parseInt(req.getParameter("comuneSindaco")))).getDescription());
		}
		//se un animale è deceduto senza identificativo o è un sinantropo non è un errore non avere un proprietario
		else if(animale.getLookupSpecie().getId() != SINANTROPO )
		{
			status.setError( "Si è verificato un errore di comunicazione con la BDR di riferimento" );
		}
		if(req.getParameter("sterilizzato")!=null)
			ret.setSterilizzato(req.getParameter("sterilizzato")!=null);
		
		return ret;
	}

	private static String calcolaProvinciaSinantropo( LookupComuni comune )
	{
		String ret = "";
		
		if( comune.getAv() ) { ret = "AV"; }
		else if( comune.getBn() ) { ret = "BN"; }
		else if( comune.getCe() ) { ret = "CE"; }
		else if( comune.getNa() ) { ret = "NA"; }
		else if( comune.getSa() ) { ret = "SA"; }
		
		return ret;
	}

	public static RegistrazioniInterface findRegistrazioniEffettuabili(Connection connectionVam, Animale animale, BUtente utente,Connection connection, HttpServletRequest req)
		throws ParseException, ClassNotFoundException, SQLException, NamingException
	{
		RegistrazioniInterface ret = null;
		
		switch ( animale.getLookupSpecie().getId() )
		{
		case CANE:
			ret = CaninaRemoteUtil.findRegistrazioniEffettuabili( animale.getIdentificativo(), utente, connection,req );
			break;
		case GATTO:
			ret = FelinaRemoteUtil.findRegistrazioniEffettuabili( animale.getIdentificativo(), utente, connection,req );
			break;
		case SINANTROPO:
			ret = SinantropoUtil.findRegistrazioniEffettuabili( connectionVam, animale.getIdentificativo() );
			break;
		}
		
		return ret;
	}
	
	public static RegistrazioniInterface findRegistrazioniEffettuabili(Connection connectionVam, AnimaleNoH animale, BUtente utente,Connection connection, HttpServletRequest req)
			throws ParseException, ClassNotFoundException, SQLException, NamingException
		{
			RegistrazioniInterface ret = null;
			
			switch ( animale.getLookupSpecie().getId() )
			{
			case CANE:
				ret = CaninaRemoteUtil.findRegistrazioniEffettuabili( animale.getIdentificativo(), utente, connection,req );
				break;
			case GATTO:
				ret = FelinaRemoteUtil.findRegistrazioniEffettuabili( animale.getIdentificativo(), utente, connection,req );
				break;
			case SINANTROPO:
				ret = SinantropoUtil.findRegistrazioniEffettuabili( connectionVam, animale.getIdentificativo() );
				break;
			}
			
			return ret;
		}
	
	
	public static RegistrazioniCaninaResponse fetchDatiDecessoCane(Cane cane)
	{
		RegistrazioniCaninaResponse ret = new RegistrazioniCaninaResponse();
		ret.setDataEvento( cane.getDataDecesso() );
		ret.setDataDecessoPresunta( cane.isDataDecessoPresunta() );
		ret.setDecessoValue(cane.getDecessoValue());
		return ret;
	}
	
	public static RegistrazioniFelinaResponse fetchDatiDecessoGatto(Gatto gatto)
	{
		RegistrazioniFelinaResponse ret = new RegistrazioniFelinaResponse();
		ret.setDataEvento( gatto.getDataDecesso() );
		ret.setDataDecessoPresunta( gatto.getIsDataDecessoPresunta() );
		ret.setDecessoValue(gatto.getDecessoValue());
		return ret;
	}
	
	
	
	public static AnimaleAnagrafica getAnagrafica(Animale animale, Connection connectionVam, ServicesStatus status,Connection connection, HttpServletRequest req) throws Exception
		{
			long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			Date dataStart 		= new Date();
			
			AnimaleAnagrafica animaleAnagrafica = new AnimaleAnagrafica();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			animaleAnagrafica.setAnimale(animale);
			if(animale.getDecedutoNonAnagrafe() || animale.getLookupSpecie().getId()==Specie.SINANTROPO)
			{
				if(animale.getTaglia()!=null && animale.getTaglia()>0)
				{
					//animaleAnagrafica.setTaglia(((LookupTaglie)persistence.find(LookupTaglie.class, animale.getTaglia())).getDescription());
					animaleAnagrafica.setTaglia(LookupTaglieDAO.getTaglia(animale.getTaglia(), connectionVam).getDescription());
				}
				if(animale.getLookupSpecie().getId()==Specie.SINANTROPO){
					Sinantropo sinantropo = SinantropoDAO.getSinantropoByNumero(connectionVam, animale.getIdentificativo());
					animaleAnagrafica.setRazza(getSpecieSinantropoString(animale,sinantropo) + " - " + animale.getRazzaSinantropo());
					
					if (animale.getDecedutoNonAnagrafe()==true) {
						animaleAnagrafica.setStatoAttuale("Decesso");
					}
					else {
						animaleAnagrafica.setStatoAttuale(sinantropo.getStatoAttuale());
					}
					animaleAnagrafica.setSterilizzato("N.D.");
				}
				else
				{
					if(animale.getMantello()!=null && animale.getMantello()>0 && animale.getLookupSpecie().getId()==Specie.CANE)
					{
						animaleAnagrafica.setMantello((CaninaRemoteUtil.getMantello(animale.getMantello(), connection,req).getDescription()));
					}
					else if(animale.getMantello()!=null && animale.getMantello()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
					{
						animaleAnagrafica.setMantello((FelinaRemoteUtil.getMantello(animale.getMantello(), connection,req).getDescription()));
					}
					else 
						animaleAnagrafica.setMantello("");
					
					if(animale.getRazza()!=null && animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.CANE)
					{
						animaleAnagrafica.setRazza((CaninaRemoteUtil.getRazza(animale.getRazza(), connection,req).getDescription()));
					}
					else if(animale.getRazza()!=null && animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
					{
						animaleAnagrafica.setRazza((FelinaRemoteUtil.getRazza(animale.getRazza(), connection,req).getDescription()));
					}
					else 
						animaleAnagrafica.setRazza(""); 
					
					if(!animale.getAccettaziones().isEmpty())
						animaleAnagrafica.setSterilizzato(animale.getAccettaziones().iterator().next().getSterilizzatoString());
					animaleAnagrafica.setStatoAttuale("Decesso");
				}
				animaleAnagrafica.setIdTaglia(animale.getTaglia());
				animaleAnagrafica.setIdMantello(animale.getMantello());
				animaleAnagrafica.setIdRazza(animale.getRazza());
				animaleAnagrafica.setSesso(animale.getSesso());
			}
			else if (animale.getLookupSpecie().getId() == CANE ) 
			{
				Cane cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), status, connection,req);
				
				if(cane.getDescrizioneMantello()!=null)
				{
					animaleAnagrafica.setMantello(cane.getDescrizioneMantello());
				}
				animaleAnagrafica.setRazza(cane.getDescrizioneRazza());
				animaleAnagrafica.setSesso(cane.getSesso());
				animaleAnagrafica.setTaglia(cane.getDescrizioneTaglia());
				
				if (cane.getMantello()!=null)
				{
					animaleAnagrafica.setIdMantello(cane.getMantello());
				}
				animaleAnagrafica.setIdRazza(cane.getRazza());
				animaleAnagrafica.setIdTaglia(cane.getIdTaglia());
				animaleAnagrafica.setStatoAttuale(cane.getStatoAttuale());

				if(cane.getDataSterilizzazione()!=null)
				{
					animaleAnagrafica.setDataSterilizzazione(cane.getDataSterilizzazione());
					animaleAnagrafica.setOperatoreSterilizzazione(cane.getOperatoreSterilizzazione());
					animaleAnagrafica.setSterilizzato("Effettuata da " + cane.getOperatoreSterilizzazione() + " il " + sdf.format(cane.getDataSterilizzazione()));
				}
				else	
					animaleAnagrafica.setSterilizzato("Non effettuata");
			}
			else if (animale.getLookupSpecie().getId() == GATTO) 
			{
				Gatto gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), status, connection,req);
				
				if(gatto.getDescrizioneMantello()!=null)
				{
					animaleAnagrafica.setMantello(gatto.getDescrizioneMantello());
				}
				animaleAnagrafica.setRazza(gatto.getDescrizioneRazza());
				animaleAnagrafica.setSesso(gatto.getSesso());
				animaleAnagrafica.setTaglia(gatto.getDescrizioneTaglia());
				if(gatto.getMantello()!=null)
				{
					animaleAnagrafica.setIdMantello(gatto.getMantello());
				}
				animaleAnagrafica.setIdRazza(gatto.getRazza());
				animaleAnagrafica.setIdTaglia(gatto.getIdTaglia());
				animaleAnagrafica.setStatoAttuale(gatto.getStatoAttuale());
				if(gatto.getDataSterilizzazione()!=null)
				{
					animaleAnagrafica.setDataSterilizzazione(gatto.getDataSterilizzazione());
					animaleAnagrafica.setOperatoreSterilizzazione(gatto.getOperatoreSterilizzazione());
					animaleAnagrafica.setSterilizzato("Effettuata da " + gatto.getOperatoreSterilizzazione() + " il " + sdf.format(gatto.getDataSterilizzazione()));
				}
				else	
					animaleAnagrafica.setSterilizzato("Non effettuata");
			}
		return animaleAnagrafica;
	}
	
	public static AnimaleAnagraficaNoH getAnagrafica(AnimaleNoH animale, Connection connectionVam, ServicesStatus status,Connection connection, HttpServletRequest req) throws Exception
	{
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		AnimaleAnagraficaNoH animaleAnagrafica = new AnimaleAnagraficaNoH();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		animaleAnagrafica.setAnimale(animale);
		if(animale.getDecedutoNonAnagrafe() || animale.getLookupSpecie().getId()==Specie.SINANTROPO)
		{
			if(animale.getTaglia()!=null && animale.getTaglia()>0)
			{
				//animaleAnagrafica.setTaglia(((LookupTaglie)persistence.find(LookupTaglie.class, animale.getTaglia())).getDescription());
				animaleAnagrafica.setTaglia(LookupTaglieDAO.getTaglia(animale.getTaglia(), connectionVam).getDescription());
			}
			if(animale.getLookupSpecie().getId()==Specie.SINANTROPO){
				Sinantropo s = SinantropoDAO.getSinantropoByNumero(connection, animale.getIdentificativo());
				animaleAnagrafica.setRazza(getSpecieSinantropoString(animale,s) + " - " + animale.getRazzaSinantropo());
				
				if (animale.getDecedutoNonAnagrafe()==true) {
					animaleAnagrafica.setStatoAttuale("Decesso");
				}
				else {
					Sinantropo sinantropo = SinantropoDAO.getSinantropoByNumero(connectionVam, animale.getIdentificativo());
					animaleAnagrafica.setStatoAttuale(sinantropo.getStatoAttuale());
				}
				animaleAnagrafica.setSterilizzato("N.D.");
			}
			else
			{
				if(animale.getMantello()!=null && animale.getMantello()>0 && animale.getLookupSpecie().getId()==Specie.CANE)
				{
					animaleAnagrafica.setMantello((CaninaRemoteUtil.getMantello(animale.getMantello(), connection,req).getDescription()));
				}
				else if(animale.getMantello()!=null && animale.getMantello()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
				{
					animaleAnagrafica.setMantello((FelinaRemoteUtil.getMantello(animale.getMantello(), connection,req).getDescription()));
				}
				else 
					animaleAnagrafica.setMantello("");
				
				if(animale.getRazza()!=null && animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.CANE)
				{
					animaleAnagrafica.setRazza((CaninaRemoteUtil.getRazza(animale.getRazza(), connection,req).getDescription()));
				}
				else if(animale.getRazza()!=null && animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
				{
					animaleAnagrafica.setRazza((FelinaRemoteUtil.getRazza(animale.getRazza(), connection,req).getDescription()));
				}
				else 
					animaleAnagrafica.setRazza(""); 
				
				if(!animale.getAccettaziones().isEmpty())
					animaleAnagrafica.setSterilizzato(animale.getAccettaziones().iterator().next().getSterilizzatoString());
				animaleAnagrafica.setStatoAttuale("Decesso");
			}
			animaleAnagrafica.setIdTaglia(animale.getTaglia());
			animaleAnagrafica.setIdMantello(animale.getMantello());
			animaleAnagrafica.setIdRazza(animale.getRazza());
			animaleAnagrafica.setSesso(animale.getSesso());
		}
		else if (animale.getLookupSpecie().getId() == CANE ) 
		{
			Cane cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), status, connection,req);
			
			if(cane.getDescrizioneMantello()!=null)
			{
				animaleAnagrafica.setMantello(cane.getDescrizioneMantello());
			}
			animaleAnagrafica.setRazza(cane.getDescrizioneRazza());
			animaleAnagrafica.setSesso(cane.getSesso());
			animaleAnagrafica.setTaglia(cane.getDescrizioneTaglia());
			
			if (cane.getMantello()!=null)
			{
				animaleAnagrafica.setIdMantello(cane.getMantello());
			}
			animaleAnagrafica.setIdRazza(cane.getRazza());
			animaleAnagrafica.setIdTaglia(cane.getIdTaglia());
			animaleAnagrafica.setStatoAttuale(cane.getStatoAttuale());

			if(cane.getDataSterilizzazione()!=null)
			{
				animaleAnagrafica.setDataSterilizzazione(cane.getDataSterilizzazione());
				animaleAnagrafica.setOperatoreSterilizzazione(cane.getOperatoreSterilizzazione());
				animaleAnagrafica.setSterilizzato("Effettuata da " + cane.getOperatoreSterilizzazione() + " il " + sdf.format(cane.getDataSterilizzazione()));
			}
			else	
				animaleAnagrafica.setSterilizzato("Non effettuata");
		}
		else if (animale.getLookupSpecie().getId() == GATTO) 
		{
			Gatto gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), status, connection,req);
			
			if(gatto.getDescrizioneMantello()!=null)
			{
				animaleAnagrafica.setMantello(gatto.getDescrizioneMantello());
			}
			animaleAnagrafica.setRazza(gatto.getDescrizioneRazza());
			animaleAnagrafica.setSesso(gatto.getSesso());
			animaleAnagrafica.setTaglia(gatto.getDescrizioneTaglia());
			if(gatto.getMantello()!=null)
			{
				animaleAnagrafica.setIdMantello(gatto.getMantello());
			}
			animaleAnagrafica.setIdRazza(gatto.getRazza());
			animaleAnagrafica.setIdTaglia(gatto.getIdTaglia());
			animaleAnagrafica.setStatoAttuale(gatto.getStatoAttuale());
			if(gatto.getDataSterilizzazione()!=null)
			{
				animaleAnagrafica.setDataSterilizzazione(gatto.getDataSterilizzazione());
				animaleAnagrafica.setOperatoreSterilizzazione(gatto.getOperatoreSterilizzazione());
				animaleAnagrafica.setSterilizzato("Effettuata da " + gatto.getOperatoreSterilizzazione() + " il " + sdf.format(gatto.getDataSterilizzazione()));
			}
			else	
				animaleAnagrafica.setSterilizzato("Non effettuata");
		}
	return animaleAnagrafica;
}
	
	
	public static AnimaleAnagrafica getAnagrafica(Animale animale, Cane cane, Gatto gatto, Persistence persistence, BUtente utente, ServicesStatus status,Connection connection,HttpServletRequest req) throws Exception
	{
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		AnimaleAnagrafica animaleAnagrafica = new AnimaleAnagrafica();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		animaleAnagrafica.setAnimale(animale);
		if(animale.getDecedutoNonAnagrafe() || animale.getLookupSpecie().getId()==Specie.SINANTROPO)
		{
			if(animale.getTaglia()!=null && animale.getTaglia()>0)
			{
				animaleAnagrafica.setTaglia(((LookupTaglie)persistence.find(LookupTaglie.class, animale.getTaglia())).getDescription());
				long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				Date dataEnd  	= new Date();
				long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find taglia)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
			}
			if(animale.getLookupSpecie().getId()==Specie.SINANTROPO)
			{
				Sinantropo s = SinantropoUtil.getSinantropoByNumero(persistence, animale.getIdentificativo());
				animaleAnagrafica.setRazza(getSpecieSinantropoString(animale,s) );
				
				long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				Date dataEnd  	= new Date();
				long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find razza)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				if (animale.getDecedutoNonAnagrafe()==true) {
					animaleAnagrafica.setStatoAttuale("Decesso");
				}
				else {
					Sinantropo sinantropo = SinantropoUtil.getSinantropoByNumero(persistence, animale.getIdentificativo());
					animaleAnagrafica.setStatoAttuale(sinantropo.getStatoAttuale());
				}
				animaleAnagrafica.setSterilizzato("N.D.");
			}
			else
			{
				if(animale.getMantello()!=null && animale.getMantello()>0 && animale.getLookupSpecie().getId()==Specie.CANE)
				{
					animaleAnagrafica.setMantello((CaninaRemoteUtil.getMantello(animale.getMantello(), connection,req).getDescription()));
					
					long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					Date dataEnd  	= new Date();
					long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find mantello)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
				}
				else if(animale.getMantello()!=null && animale.getMantello()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
				{
					animaleAnagrafica.setMantello((FelinaRemoteUtil.getMantello(animale.getMantello(), connection,req).getDescription()));
					
					long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					Date dataEnd  	= new Date();
					long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find mantello 2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
				}
				else 
					animaleAnagrafica.setMantello("");
				
				if(animale.getRazza()!=null && animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.CANE)
				{
					animaleAnagrafica.setRazza((CaninaRemoteUtil.getRazza(animale.getRazza(), connection, req).getDescription()));
					
					long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					Date dataEnd  	= new Date();
					long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find razza)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
				}
				else if(animale.getRazza()!=null && animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
				{
					animaleAnagrafica.setRazza((FelinaRemoteUtil.getRazza(animale.getRazza(), connection, req).getDescription()));
					
					long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					Date dataEnd  	= new Date();
					long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find razza 2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
				}
				else 
					animaleAnagrafica.setRazza(""); 
				
				if(!animale.getAccettaziones().isEmpty())
					animaleAnagrafica.setSterilizzato(animale.getAccettaziones().iterator().next().getSterilizzatoString());
				animaleAnagrafica.setStatoAttuale("Decesso");
			}
			animaleAnagrafica.setIdTaglia(animale.getTaglia());
			animaleAnagrafica.setIdMantello(animale.getMantello());
			animaleAnagrafica.setIdRazza(animale.getRazza());
			animaleAnagrafica.setSesso(animale.getSesso());
		}
		else if (animale.getLookupSpecie().getId() == CANE ) 
		{
			if(cane==null)
				cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), status, connection, req);
			
			if(cane.getDescrizioneMantello()!=null)
			{
				animaleAnagrafica.setMantello(cane.getDescrizioneMantello());
			}
			animaleAnagrafica.setRazza(cane.getDescrizioneRazza());
			animaleAnagrafica.setSesso(cane.getSesso());
			animaleAnagrafica.setTaglia(cane.getDescrizioneTaglia());
			
			if (cane.getMantello()!=null)
			{
				animaleAnagrafica.setIdMantello(cane.getMantello());
			}
			animaleAnagrafica.setIdRazza(cane.getRazza());
			animaleAnagrafica.setIdTaglia(cane.getIdTaglia());
			animaleAnagrafica.setStatoAttuale(cane.getStatoAttuale());

			if(cane.getDataSterilizzazione()!=null)
			{
				animaleAnagrafica.setDataSterilizzazione(cane.getDataSterilizzazione());
				animaleAnagrafica.setOperatoreSterilizzazione(cane.getOperatoreSterilizzazione());
				animaleAnagrafica.setSterilizzato("Effettuata da " + cane.getOperatoreSterilizzazione() + " il " + sdf.format(cane.getDataSterilizzazione()));
			}
			else	
				animaleAnagrafica.setSterilizzato("Non effettuata");
		}
		else if (animale.getLookupSpecie().getId() == GATTO) 
		{
			if(gatto==null)
				gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), status, connection, req);

			if(gatto.getDescrizioneMantello()!=null)
			{
				animaleAnagrafica.setMantello(gatto.getDescrizioneMantello());
			}
			animaleAnagrafica.setRazza(gatto.getDescrizioneRazza());
			animaleAnagrafica.setSesso(gatto.getSesso());
			animaleAnagrafica.setTaglia(gatto.getDescrizioneTaglia());
			if(gatto.getMantello()!=null)
			{
				animaleAnagrafica.setIdMantello(gatto.getMantello());
			}
			animaleAnagrafica.setIdRazza(gatto.getRazza());
			animaleAnagrafica.setIdTaglia(gatto.getIdTaglia());
			animaleAnagrafica.setStatoAttuale(gatto.getStatoAttuale());
			if(gatto.getDataSterilizzazione()!=null)
			{
				animaleAnagrafica.setDataSterilizzazione(gatto.getDataSterilizzazione());
				animaleAnagrafica.setOperatoreSterilizzazione(gatto.getOperatoreSterilizzazione());
				animaleAnagrafica.setSterilizzato("Effettuata da " + gatto.getOperatoreSterilizzazione() + " il " + sdf.format(gatto.getDataSterilizzazione()));
			}
			else	
				animaleAnagrafica.setSterilizzato("Non effettuata");
		}
	return animaleAnagrafica;
}
	
	
	
	public static ArrayList<Accettazione> getAccettazioni(Set<Accettazione> accettazioni, Integer idClinica) throws Exception
	{
		ArrayList<Accettazione> accettazioniClinica = new ArrayList<Accettazione>();
		Iterator<Accettazione> accIter = accettazioni.iterator();
		while(accIter.hasNext())
		{
			Accettazione accTemp = accIter.next();
			if(accTemp.getEnteredBy().getClinica().getId()==idClinica)
				accettazioniClinica.add(accTemp);
		}
		return accettazioniClinica;
}
	
	public static AccettazioneNoH getAccettazioneConCcAprireRitrovamentoCambioDetentore(AnimaleNoH animale,HttpServletRequest req) throws Exception
	{
		Iterator<AccettazioneNoH> iters = animale.getAccettaziones().iterator();
		while(iters.hasNext())
		{
			AccettazioneNoH acc = iters.next();
			if(getInserireRitornoProprietario(acc, req) && acc.getCartellaClinicas().isEmpty())
				return acc;
		}
		return null;
		
	}
	
	public static Accettazione getAccettazioneConCcAprireRitrovamentoCambioDetentore(Animale animale,HttpServletRequest req) throws Exception
	{
		Iterator<Accettazione> iters = animale.getAccettaziones().iterator();
		while(iters.hasNext())
		{
			Accettazione acc = iters.next();
			if(getInserireRitornoProprietario(acc, req) && acc.getCartellaClinicas().isEmpty())
				return acc;
		}
		return null;
		
	}
	
	
	//Ritorna se per quest'accettazione bisogna inserire il ritorno al proprietario
	public static boolean getInserireRitornoProprietario(AccettazioneNoH accettazione,HttpServletRequest req, Connection connectionVam) throws Exception
	{
		Connection connection = null;
		Persistence persistence = PersistenceFactory.getPersistence();
		aggiornaConnessioneApertaSessione(req);
		
		try
		{
		
		//Se non è stato richiesto il ritrovamento
		Iterator<LookupOperazioniAccettazione> it = AccettazioneDAO.getOperazioniRichieste(accettazione.getId(), connectionVam).iterator();
		
		boolean esiste = false;
		while(it.hasNext())
		{
			LookupOperazioniAccettazione temp = it.next();
			if(temp.getId()==IdOperazioniBdr.ritrovamento || temp.getId()==IdOperazioniBdr.ritrovamentoSmarrNonDenunciato)
			{
				esiste = true;
			}
		}
		if(!esiste)
		{
			PersistenceFactory.closePersistence( persistence, true );
			aggiornaConnessioneChiusaSessione(req);
			return false;
		}
		
		ArrayList operazioni = new ArrayList();
		operazioni.add(IdOperazioniBdr.ritrovamento);
		operazioni.add(IdOperazioniBdr.ritrovamentoSmarrNonDenunciato);
		
		ArrayList<AttivitaBdr> atts = (ArrayList<AttivitaBdr>)persistence.createCriteria(AttivitaBdr.class)
							.add(Restrictions.eq("accettazione.id", accettazione.getId()))
							.add(Restrictions.in("operazioneBdr.id", operazioni))
							.add(Restrictions.isNotNull("idRegistrazioneBdr"))
							.list();
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		connection = ds.getConnection();
		aggiornaConnessioneApertaSessione(req);
		
		if(!atts.isEmpty())
		{
			Registrazioni reg = CaninaRemoteUtil.getRitrovamentoCambioDetentore(accettazione.getAnimale().getIdentificativo(), connection, req);
			if(CaninaRemoteUtil.esisteRitrovamentoCambioDetentore(reg) && reg.getId()==atts.get(0).getIdRegistrazioneBdr())
			{
				PersistenceFactory.closePersistence( persistence, true );
				aggiornaConnessioneChiusaSessione(req);
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				return true;
			}
			else
			{
				PersistenceFactory.closePersistence( persistence, true );
				aggiornaConnessioneChiusaSessione(req);
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				return false;
			}
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, true );
			aggiornaConnessioneChiusaSessione(req);
			connection.close();
			aggiornaConnessioneChiusaSessione(req);
			return false;
		}
		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					System.out.println(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		
		}
	}
	
		public static boolean getInserireRitornoProprietario(AccettazioneNoH accettazione,HttpServletRequest req) throws Exception
		{
			Connection connection = null;
			Persistence persistence = PersistenceFactory.getPersistence();
			aggiornaConnessioneApertaSessione(req);
			
			try
			{
			
			//Se non è stato richiesto il ritrovamento
			Iterator<LookupOperazioniAccettazione> it = accettazione.getOperazioniRichieste().iterator();
			boolean esiste = false;
			while(it.hasNext())
			{
				LookupOperazioniAccettazione temp = it.next();
				if(temp.getId()==IdOperazioniBdr.ritrovamento || temp.getId()==IdOperazioniBdr.ritrovamentoSmarrNonDenunciato)
				{
					esiste = true;
				}
			}
			if(!esiste)
			{
				PersistenceFactory.closePersistence( persistence, true );
				aggiornaConnessioneChiusaSessione(req);
				return false;
			}
			
			ArrayList operazioni = new ArrayList();
			operazioni.add(IdOperazioniBdr.ritrovamento);
			operazioni.add(IdOperazioniBdr.ritrovamentoSmarrNonDenunciato);
			
			ArrayList<AttivitaBdr> atts = (ArrayList<AttivitaBdr>)persistence.createCriteria(AttivitaBdr.class)
								.add(Restrictions.eq("accettazione.id", accettazione.getId()))
								.add(Restrictions.in("operazioneBdr.id", operazioni))
								.add(Restrictions.isNotNull("idRegistrazioneBdr"))
								.list();
			
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			
			if(!atts.isEmpty())
			{
				Registrazioni reg = CaninaRemoteUtil.getRitrovamentoCambioDetentore(accettazione.getAnimale().getIdentificativo(), connection, req);
				if(CaninaRemoteUtil.esisteRitrovamentoCambioDetentore(reg) && reg.getId()==atts.get(0).getIdRegistrazioneBdr())
				{
					PersistenceFactory.closePersistence( persistence, true );
					aggiornaConnessioneChiusaSessione(req);
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
					return true;
				}
				else
				{
					PersistenceFactory.closePersistence( persistence, true );
					aggiornaConnessioneChiusaSessione(req);
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
					return false;
				}
			}
			else
			{
				PersistenceFactory.closePersistence( persistence, true );
				aggiornaConnessioneChiusaSessione(req);
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				return false;
			}
			}
			finally
			{
				if(connection!=null)
				{
					try
					{
						connection.close();
						aggiornaConnessioneChiusaSessione(req);
					}
					catch(SQLException e)
					{
						System.out.println(e.getMessage());
					}
				}
				aggiornaConnessioneChiusaSessione(req);
			
			}
		}
		
		public static boolean getInserireRitornoProprietario(Accettazione accettazione,HttpServletRequest req) throws Exception
		{
			
			Connection connection = null;
			Persistence persistence = PersistenceFactory.getPersistence();
			aggiornaConnessioneApertaSessione(req);
			
			try
			{
			//Se non è stato richiesto il ritrovamento
			Iterator<LookupOperazioniAccettazione> it = accettazione.getOperazioniRichieste().iterator();
			boolean esiste = false;
			while(it.hasNext())
			{
				LookupOperazioniAccettazione temp = it.next();
				if(temp.getId()==IdOperazioniBdr.ritrovamento || temp.getId()==IdOperazioniBdr.ritrovamentoSmarrNonDenunciato)
				{
					esiste = true;
				}
			}
			if(!esiste)
			{
				PersistenceFactory.closePersistence( persistence, true );
				aggiornaConnessioneChiusaSessione(req);
				return false;
			}
			
			ArrayList operazioni = new ArrayList();
			operazioni.add(IdOperazioniBdr.ritrovamento);
			operazioni.add(IdOperazioniBdr.ritrovamentoSmarrNonDenunciato);
			
			ArrayList<AttivitaBdr> atts = (ArrayList<AttivitaBdr>)persistence.createCriteria(AttivitaBdr.class)
								.add(Restrictions.eq("accettazione.id", accettazione.getId()))
								.add(Restrictions.in("operazioneBdr.id", operazioni))
								.add(Restrictions.isNotNull("idRegistrazioneBdr"))
								.list();
			
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			
			if(!atts.isEmpty())
			{
				Registrazioni reg = CaninaRemoteUtil.getRitrovamentoCambioDetentore(accettazione.getAnimale().getIdentificativo(), connection, req);
				if(CaninaRemoteUtil.esisteRitrovamentoCambioDetentore(reg) && reg.getId()==atts.get(0).getIdRegistrazioneBdr())
				{
					PersistenceFactory.closePersistence( persistence, true );
					aggiornaConnessioneChiusaSessione(req);
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
					return true;
				}
				else
				{
					PersistenceFactory.closePersistence( persistence, true );
					aggiornaConnessioneChiusaSessione(req);
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
					return false;
				}
			}
			else
			{
				PersistenceFactory.closePersistence( persistence, true );
				aggiornaConnessioneChiusaSessione(req);
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				return false;
			}
			}
			finally
			{
				if(connection!=null)
				{
					try
					{
						connection.close();
						aggiornaConnessioneChiusaSessione(req);
					}
					catch(SQLException e)
					{
						System.out.println(e.getMessage());
					}
				}
				aggiornaConnessioneChiusaSessione(req);
			
			}
		}
		
		//SENZA HIBERNATE
		public static boolean getInserireRitornoProprietario(String identificativo, int idAcc, HashSet<LookupOperazioniAccettazione> ops,  HttpServletRequest req) throws Exception
		{
			Persistence persistence = PersistenceFactory.getPersistence();
			aggiornaConnessioneApertaSessione(req);
			
			//Se non è stato richiesto il ritrovamento
			Iterator<LookupOperazioniAccettazione> it = ops.iterator();
			boolean esiste = false;
			while(it.hasNext())
			{
				LookupOperazioniAccettazione temp = it.next();
				if(temp.getId()==IdOperazioniBdr.ritrovamento || temp.getId()==IdOperazioniBdr.ritrovamentoSmarrNonDenunciato)
				{
					esiste = true;
				}
			}
			if(!esiste)
			{
				PersistenceFactory.closePersistence( persistence, true );
				aggiornaConnessioneChiusaSessione(req);
				return false;
			}
			
			ArrayList operazioni = new ArrayList();
			operazioni.add(IdOperazioniBdr.ritrovamento);
			operazioni.add(IdOperazioniBdr.ritrovamentoSmarrNonDenunciato);
			
			ArrayList<AttivitaBdr> atts = (ArrayList<AttivitaBdr>)persistence.createCriteria(AttivitaBdr.class)
								.add(Restrictions.eq("accettazione.id", idAcc))
								.add(Restrictions.in("operazioneBdr.id", operazioni))
								.add(Restrictions.isNotNull("idRegistrazioneBdr"))
								.list();
			
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			Connection connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			
			if(!atts.isEmpty())
			{
				Registrazioni reg = CaninaRemoteUtil.getRitrovamentoCambioDetentore(identificativo, connection, req);
				if(CaninaRemoteUtil.esisteRitrovamentoCambioDetentore(reg) && reg.getId()==atts.get(0).getIdRegistrazioneBdr())
				{
					PersistenceFactory.closePersistence( persistence, true );
					aggiornaConnessioneChiusaSessione(req);
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
					return true;
				}
				else
				{
					PersistenceFactory.closePersistence( persistence, true );
					aggiornaConnessioneChiusaSessione(req);
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
					return false;
				}
			}
			else
			{
				PersistenceFactory.closePersistence( persistence, true );
				aggiornaConnessioneChiusaSessione(req);
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				return false;
			}
		}
		
		
		public static void aggiornaConnessioneApertaSessione(HttpServletRequest req)
		{
			if(req!=null)
			{
				int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (0);
				numConnessioniDb = numConnessioniDb+1;
				req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
				req.getSession().setAttribute("timeConnOpen",     new Date());
			}
		}
		
		public static void aggiornaConnessioneChiusaSessione(HttpServletRequest req)
		{
			if(req!=null)
			{
				int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (1);
				numConnessioniDb = numConnessioniDb-1;
				req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
				if(numConnessioniDb==0)
					req.getSession().setAttribute("timeConnOpen",     null);
			}
		}
		
		
		public static AnimaleAnagrafica getAnagrafica(Animale animale, Persistence persistence, BUtente utente, ServicesStatus status,Connection connection, HttpServletRequest req) throws Exception
		{
			long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			Date dataStart 		= new Date();
			
			AnimaleAnagrafica animaleAnagrafica = new AnimaleAnagrafica();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			animaleAnagrafica.setAnimale(animale);
			if(animale.getDecedutoNonAnagrafe() || animale.getLookupSpecie().getId()==Specie.SINANTROPO)
			{
				if(animale.getTaglia()!=null && animale.getTaglia()>0)
				{
					animaleAnagrafica.setTaglia(((LookupTaglie)persistence.find(LookupTaglie.class, animale.getTaglia())).getDescription());
					long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					Date dataEnd  	= new Date();
					long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find taglia)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
				}
				if(animale.getLookupSpecie().getId()==Specie.SINANTROPO){
					Sinantropo s = SinantropoUtil.getSinantropoByNumero(persistence, animale.getIdentificativo());
					animaleAnagrafica.setRazza(getSpecieSinantropoString(animale,s) );
					
					long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					Date dataEnd  	= new Date();
					long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find razza)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					if (animale.getDecedutoNonAnagrafe()==true) {
						animaleAnagrafica.setStatoAttuale("Decesso");
					}
					else {
						Sinantropo sinantropo = SinantropoUtil.getSinantropoByNumero(persistence, animale.getIdentificativo());
						animaleAnagrafica.setStatoAttuale(sinantropo.getStatoAttuale());
					}
					animaleAnagrafica.setSterilizzato("N.D.");
				}
				else
				{
					if(animale.getMantello()!=null && animale.getMantello()>0 && animale.getLookupSpecie().getId()==Specie.CANE)
					{
						animaleAnagrafica.setMantello((CaninaRemoteUtil.getMantello(animale.getMantello(), connection,req).getDescription()));
						
						long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						Date dataEnd  	= new Date();
						long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find mantello)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
						
					}
					else if(animale.getMantello()!=null && animale.getMantello()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
					{
						animaleAnagrafica.setMantello((FelinaRemoteUtil.getMantello(animale.getMantello(), connection,req).getDescription()));
						
						long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						Date dataEnd  	= new Date();
						long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find mantello 2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
						
					}
					else 
						animaleAnagrafica.setMantello("");
					
					if(animale.getRazza()!=null && animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.CANE)
					{
						animaleAnagrafica.setRazza((CaninaRemoteUtil.getRazza(animale.getRazza(), connection,req).getDescription()));
						
						long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						Date dataEnd  	= new Date();
						long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find razza)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
					}
					else if(animale.getRazza()!=null && animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
					{
						animaleAnagrafica.setRazza((FelinaRemoteUtil.getRazza(animale.getRazza(), connection,req).getDescription()));
						
						long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						Date dataEnd  	= new Date();
						long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find razza 2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
					}
					else 
						animaleAnagrafica.setRazza(""); 
					
					if(!animale.getAccettaziones().isEmpty())
						animaleAnagrafica.setSterilizzato(animale.getAccettaziones().iterator().next().getSterilizzatoString());
					animaleAnagrafica.setStatoAttuale("Decesso");
				}
				animaleAnagrafica.setIdTaglia(animale.getTaglia());
				animaleAnagrafica.setIdMantello(animale.getMantello());
				animaleAnagrafica.setIdRazza(animale.getRazza());
				animaleAnagrafica.setSesso(animale.getSesso());
			}
			else if (animale.getLookupSpecie().getId() == CANE ) 
			{
				Cane cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), utente, status, connection,req);
				
				long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				Date dataEnd  	= new Date();
				long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find cane)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if(cane.getDescrizioneMantello()!=null)
				{
					animaleAnagrafica.setMantello(cane.getDescrizioneMantello());
				}
				animaleAnagrafica.setRazza(cane.getDescrizioneRazza());
				animaleAnagrafica.setSesso(cane.getSesso());
				animaleAnagrafica.setTaglia(cane.getDescrizioneTaglia());
				
				if (cane.getMantello()!=null)
				{
					animaleAnagrafica.setIdMantello(cane.getMantello());
				}
				animaleAnagrafica.setIdRazza(cane.getRazza());
				animaleAnagrafica.setIdTaglia(cane.getIdTaglia());
				animaleAnagrafica.setStatoAttuale(cane.getStatoAttuale());

				if(cane.getDataSterilizzazione()!=null)
				{
					animaleAnagrafica.setDataSterilizzazione(cane.getDataSterilizzazione());
					animaleAnagrafica.setOperatoreSterilizzazione(cane.getOperatoreSterilizzazione());
					animaleAnagrafica.setSterilizzato("Effettuata da " + cane.getOperatoreSterilizzazione() + " il " + sdf.format(cane.getDataSterilizzazione()));
				}
				else	
					animaleAnagrafica.setSterilizzato("Non effettuata");
			}
			else if (animale.getLookupSpecie().getId() == GATTO) 
			{
				Gatto gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), utente, status, connection,req);
				
				long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				Date dataEnd  	= new Date();
				long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "AnimaliUtil.getAnagrafica(find gatto)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if(gatto.getDescrizioneMantello()!=null)
				{
					animaleAnagrafica.setMantello(gatto.getDescrizioneMantello());
				}
				animaleAnagrafica.setRazza(gatto.getDescrizioneRazza());
				animaleAnagrafica.setSesso(gatto.getSesso());
				animaleAnagrafica.setTaglia(gatto.getDescrizioneTaglia());
				if(gatto.getMantello()!=null)
				{
					animaleAnagrafica.setIdMantello(gatto.getMantello());
				}
				animaleAnagrafica.setIdRazza(gatto.getRazza());
				animaleAnagrafica.setIdTaglia(gatto.getIdTaglia());
				animaleAnagrafica.setStatoAttuale(gatto.getStatoAttuale());
				if(gatto.getDataSterilizzazione()!=null)
				{
					animaleAnagrafica.setDataSterilizzazione(gatto.getDataSterilizzazione());
					animaleAnagrafica.setOperatoreSterilizzazione(gatto.getOperatoreSterilizzazione());
					animaleAnagrafica.setSterilizzato("Effettuata da " + gatto.getOperatoreSterilizzazione() + " il " + sdf.format(gatto.getDataSterilizzazione()));
				}
				else	
					animaleAnagrafica.setSterilizzato("Non effettuata");
			}
		return animaleAnagrafica;
	}
		
		
		
		
	public static String getSpecieSinantropoString(Animale a, Sinantropo s)
	{
		if(!a.getDecedutoNonAnagrafe())
		{
			if(s.getMarini())
			{
				if(a.getSpecieSinantropo()!=null)
				{
					if(a.getSpecieSinantropo().equals("1"))
						return "Mammiferi/Cetacei - " + a.getRazzaSinantropo();
					else if(a.getSpecieSinantropo().equals("2"))
						return "Rettili/Testuggini - " + a.getRazzaSinantropo();
					else
						return "Selaci Chondrichthlyes - " + a.getRazzaSinantropo();
				}
				else
					return "";
			}
			else if(s.isZoo() || s.isSinantropo())
			{
				if(a.getSpecieSinantropo()!=null)
				{
					if(a.getSpecieSinantropo().equals("1"))
						return "Uccello - " + a.getRazzaSinantropo();
					else if(a.getSpecieSinantropo().equals("2"))
						return "Mammifero - " + a.getRazzaSinantropo();
					else
						return "Rettile/Anfibio - " + a.getRazzaSinantropo();
				}
				else
					return "";
			}
			else
				return "";
		}
		else
			return a.getRazzaSinantropo();
	}
	
	
	public static String getSpecieSinantropoString(AnimaleNoH a, Sinantropo s)
	{
		if(!a.getDecedutoNonAnagrafe())
		{
			if(s.getMarini())
			{
				if(a.getSpecieSinantropo()!=null)
				{
					if(a.getSpecieSinantropo().equals("1"))
						return "Mammiferi/Cetacei";
					else if(a.getSpecieSinantropo().equals("2"))
						return "Rettili/Testuggini";
					else
						return "Selaci Chondrichthlyes";
				}
				else
					return "";
			}
			else if(s.isZoo() || s.isSinantropo())
			{
				if(a.getSpecieSinantropo()!=null)
				{
					if(a.getSpecieSinantropo().equals("1"))
						return "Uccello";
					else if(a.getSpecieSinantropo().equals("2"))
						return "Mammifero";
					else
						return "Rettile/Anfibio";
				}
				else
					return "";
			}
			else
				return "";
		}
		else
			return a.getRazzaSinantropo();
	}
	
}
