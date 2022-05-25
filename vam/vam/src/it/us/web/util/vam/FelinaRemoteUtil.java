/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Colonia;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelina;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupProvince;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.ModificaBdrException;
import it.us.web.util.TimeoutControl;
import it.us.web.util.UrlUtil;
import it.us.web.util.properties.Application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

public class FelinaRemoteUtil
{
	private static GsonBuilder gb;
	private static Gson gson;
	
	final static Logger logger = LoggerFactory.getLogger(FelinaRemoteUtil.class);
	
	static
	{
		gb = new GsonBuilder();
		gb.setDateFormat( "yyyy-MM-dd HH:mm:ss" );
		gson = gb.create();
	}
	
	public static Gatto findGatto( String identificativo, ServicesStatus status, Connection connection , HttpServletRequest req) throws ClassNotFoundException, SQLException, NamingException 
	{
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		Gatto ret = null;
		List<Gatto> gatti = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
//			st = connection.createStatement();
//			rs = st.executeQuery("select * from public_functions.getdatigatto('"+identificativo+"')");
			
			DataCaching data = new DataCaching(connection);
			
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				QueryResult qr = data.execute("select * from public_functions.getdatigatto( '"+identificativo+"')", parameters);
				
				result = (ArrayList) qr.getRows();
				
				Iterator<String> it = result.iterator();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			gatti = persistence.createSQLQuery("select * from public_functions.getdatigatto('"+identificativo+"')", Gatto.class).list();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
				
				HashMap<String, Object> gatto = (HashMap<String, Object>) result.iterator().next();
				
				if (gatto.get("id")!=null && (Integer) gatto.get("id") >0 ) {
					ret = new Gatto();
					ret.setDataDecesso((Date) gatto.get("data_decesso"));
					ret.setDataNascita((Date) gatto.get("data_nascita"));
					ret.setDecessoValue((String) gatto.get("decesso_value"));
					ret.setDescrizioneMantello((String) gatto.get("descrizione_mantello"));
					ret.setDescrizioneRazza((String) gatto.get("descrizione_razza"));
					ret.setDescrizioneTaglia((String) gatto.get("descrizione_taglia"));
					ret.setId((Integer) gatto.get("id"));
					ret.setIdTaglia((Integer) gatto.get("id_taglia"));
					if(gatto.get("id_tipo_decesso")!=null)
						ret.setIdTipoDecesso((Integer) gatto.get("id_tipo_decesso"));
					ret.setDataRegistrazione((Date) gatto.get("data_registrazione"));
					ret.setDataChippatura((Date) gatto.get("data_chippatura"));
					ret.setIdProprietario((Integer) gatto.get("id_proprietario"));
					ret.setIdDetentore((Integer) gatto.get("id_detentore"));
					ret.setIsDataDecessoPresunta((Boolean) gatto.get("is_data_decesso_presunta"));
					ret.setIsDataNascitaPresunta((Boolean) gatto.get("is_data_nascita_presunta"));
					ret.setMantello((Integer) gatto.get("mantello"));
					ret.setMc((String) (gatto.get("mc") != null && !("").equals(gatto.get("mc"))? gatto.get("mc") : gatto.get("tatuaggio")));
					ret.setNoteRitrovamento((String) gatto.get("note_ritrovamento"));
					ret.setRazza((Integer) gatto.get("razza"));
					ret.setSesso((String) gatto.get("sesso"));
					ret.setStatoAttuale((String) gatto.get("stato_attuale"));
					ret.setSterilizzato((Boolean) gatto.get("sterilizzato"));
					ret.setTrashedDate((Date) gatto.get("trashed_date"));
					ret.setDataSterilizzazione((Date) gatto.get("data_sterilizzazione"));
					ret.setOperatoreSterilizzazione((String) gatto.get("soggetto_sterilizzante"));
					ret.setTatuaggio((String) gatto.get("tatuaggio"));
				}
				
				
//				if(rs.next() && rs.getInt("id")>0)
//				{
//				ret = new Gatto();
//				ret.setDataDecesso(rs.getDate("data_decesso"));
//				ret.setDataNascita(rs.getDate("data_nascita"));
//				ret.setDecessoValue(rs.getString("decesso_value"));
//				ret.setDescrizioneMantello(rs.getString("descrizione_mantello"));
//				ret.setDescrizioneRazza(rs.getString("descrizione_razza"));
//				ret.setDescrizioneTaglia(rs.getString("descrizione_taglia"));
//				ret.setId(rs.getInt("id"));
//				ret.setIdTaglia(rs.getInt("id_taglia"));
//				ret.setIdTipoDecesso(rs.getInt("id_tipo_decesso"));
//				ret.setIsDataDecessoPresunta(rs.getBoolean("is_data_decesso_presunta"));
//				ret.setIsDataNascitaPresunta(rs.getBoolean("is_data_nascita_presunta"));
//				ret.setMantello(rs.getInt("mantello"));
//				ret.setMc(rs.getString("mc"));
//				ret.setNoteRitrovamento(rs.getString("note_ritrovamento"));
//				ret.setRazza(rs.getInt("razza"));
//				ret.setSesso(rs.getString("sesso"));
//				ret.setStatoAttuale(rs.getString("stato_attuale"));
//				ret.setSterilizzato(rs.getBoolean("sterilizzato"));
//				ret.setTrashedDate(rs.getDate("trashed_date"));
//			}
			
		}
		else
		{
			if( gatti.size() > 0 && gatti.get(0)!=null )
			{
				ret = gatti.get( 0 );
			}
		}
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		
		return ret;
	}

	public static ProprietarioGatto findProprietario( String identificativo, BUtente utente , Connection connection, HttpServletRequest req) throws Exception
	{
		
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", mc: " + identificativo);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		List<ProprietarioGatto> proprietari = null;
		ProprietarioGatto ret = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			DataCaching data = new DataCaching(connection);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			 dataEnd  	= new Date();
			 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto(0)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
					   ", mc: " + identificativo);
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				QueryResult qr = data.execute("select * from public_functions.getdatiproprietarioanimale('"+identificativo+"')", parameters);
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto(1)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				result = (ArrayList) qr.getRows();
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto(2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				Iterator<String> it = result.iterator();
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto(3)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				/*while (it.hasNext()){
					System.out.println("Record: "+it.next());
				}*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			HashMap<String,Object> proprietario = (HashMap<String, Object>) result.iterator().next();
			if (proprietario.get("id")!=null && (Integer) proprietario.get("id")>0){
				ret = new ProprietarioGatto();
				ret.setAsl((Integer) proprietario.get("id_asl"));
				ret.setCap((String) proprietario.get("cap"));
				ret.setCitta((String) proprietario.get("citta"));
				ret.setCodiceFiscale((String) proprietario.get("codicefiscale"));
				ret.setCognome((String) proprietario.get("cognome"));
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto(4)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				ret.setDocumentoIdentita((String) proprietario.get("documentoidentita"));
				ret.setId((Integer) proprietario.get("id"));
				ret.setNazione((String) proprietario.get("nazione"));
				ret.setNome((String) proprietario.get("nome"));
				ret.setNumeroTelefono((String) proprietario.get("numerotelefono"));
				ret.setTipo((String) proprietario.get("tipo"));
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto(5)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				
				Integer provincia = null;
				try
				{
					String provinciaString = ((String)proprietario.get("provincia"));
					if(provinciaString!=null && !provinciaString.equals(""))
						provinciaString=provinciaString.replaceAll(" ", "");
					provincia = Integer.parseInt(provinciaString);
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto(6)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
							   ", mc: " + identificativo);
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
				}
				catch(NumberFormatException ex)
				{
					//Se è una stringa
					ret.setProvincia((String) proprietario.get("provincia"));
				}
				if(provincia!=null && provincia>0)
				{
					//Se è un identificativo da decodificare
					Persistence persistence2 = PersistenceFactory.getPersistenceCanina();
					aggiornaConnessioneApertaSessione(req);
					LookupProvince provinciaLookup = (LookupProvince)persistence2.find(LookupProvince.class, provincia);
					ret.setProvincia(provinciaLookup.getDescription());
					PersistenceFactory.closePersistence( persistence2, false );
					aggiornaConnessioneChiusaSessione(req);
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto(6)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
							   ", mc: " + identificativo);
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
				}
				
				ret.setTrashedDate((Date) proprietario.get("trasheddate"));
				ret.setVia((String) proprietario.get("via"));
			}
			
			
			
//			st = connection.createStatement();
//			rs = st.executeQuery("select * from public_functions.getdatiproprietarioanimale('"+identificativo+"')");
//			if(rs.next() && rs.getInt("id")>0)
//			{
//				ret = new ProprietarioGatto();
//				ret.setAsl(rs.getInt("id_asl"));
//				ret.setCap(rs.getString("cap"));
//				ret.setCitta(rs.getString("citta"));
//				ret.setCodiceFiscale(rs.getString("codicefiscale"));
//				ret.setCognome(rs.getString("cognome"));
//				ret.setDocumentoIdentita(rs.getString("documentoidentita"));
//				ret.setId(rs.getInt("id"));
//				ret.setNazione(rs.getString("nazione"));
//				ret.setNome(rs.getString("nome"));
//				ret.setNumeroTelefono(rs.getString("numerotelefono"));
//				ret.setProvincia(rs.getString("provincia"));
//				ret.setTrashedDate(rs.getDate("trasheddate"));
//				ret.setVia(rs.getString("via"));
//			}
		}
		else
		{
			proprietari = persistence.createSQLQuery("select * from public_functions.getdatiproprietarioanimale('"+identificativo+"')", ProprietarioGatto.class).list();
			if( proprietari.size() > 0 && proprietari.get(0)!=null)
			{
				ret = proprietari.get( 0 );
			}
		}
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		
		
		memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findPropGatto(7)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", mc: " + identificativo);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
		return ret;
	}

	public static Colonia findColonia( String identificativo, BUtente utente, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException
	{
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findColonia" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", mc: " + identificativo);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		List<Colonia> colonie = null;
		Colonia ret = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
			DataCaching data = new DataCaching(connection);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			 dataEnd  	= new Date();
			 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findColonia(0)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
					   ", mc: " + identificativo);
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				QueryResult qr = data.execute("select * from public_functions.getdaticolonia('"+identificativo+"')", parameters);
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findColonia(1)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				result = (ArrayList) qr.getRows();
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findColonia(2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				Iterator<String> it = result.iterator();
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findColonia(3)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				/*while (it.hasNext()){
					System.out.println("Record: "+it.next());
				}*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			HashMap<String,Object> colonia = (HashMap<String, Object>) result.iterator().next();
			if (colonia.get("id")!=null && (Integer) colonia.get("id")>0){
				ret = new Colonia();
				ret.setAsl((Integer) colonia.get("id_asl"));
				ret.setCap((String) colonia.get("cap"));
				ret.setCittaColonia((String) colonia.get("citta_colonia"));
				ret.setCodiceFiscaleReferente((String) colonia.get("codicefiscale_referente"));
				ret.setCognomeReferente((String) colonia.get("cognome_referente"));
				ret.setDataCensimentoNumGatti((Date) colonia.get("data_censimento_gatti"));
				ret.setDataRegistrazioneColonia((Date) colonia.get("data_registrazione_colonia"));
				ret.setDocumentoIdentita((String) colonia.get("documentoidentita"));
				ret.setId((Integer) colonia.get("id"));
				ret.setIndirizzoColonia((String) colonia.get("via_colonia"));
				ret.setNazione((String) colonia.get("nazione"));
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findColonia(4)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				ret.setNomeReferente((String) colonia.get("nome_referente"));
				ret.setNumeroGatti((Integer) colonia.get("numero_gatti"));
				ret.setNumeroProtocollo((String) colonia.get("numero_protocollo"));
				ret.setTelefonoReferente((String) colonia.get("numerotelefono"));
				ret.setProvinciaColonia((String) colonia.get("provincia_colonia"));
				ret.setTelefonoReferente((String) colonia.get("numerotelefono_referente"));
				ret.setTrashedDate((Date) colonia.get("trasheddate"));
				ret.setVeterinario((String) colonia.get("nome_veterinario"));
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findColonia(5)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
			}
			
//			st = connection.createStatement();
//			rs = st.executeQuery("select * from public_functions.getdaticolonia('"+identificativo+"')");
//			if(rs.next() && rs.getInt("id")>0)
//			{
//				ret = new Colonia();
//				ret.setAsl(rs.getInt("id_asl"));
//				ret.setCap(rs.getString("cap"));
//				ret.setCittaColonia(rs.getString("citta_colonia"));
//				ret.setCodiceFiscaleReferente(rs.getString("codicefiscale_referente"));
//				ret.setCognomeReferente(rs.getString("cognome_referente"));
//				ret.setDataCensimentoNumGatti(rs.getDate("data_censimento_gatti"));
//				ret.setDataRegistrazioneColonia(rs.getDate("data_registrazione_colonia"));
//				ret.setDocumentoIdentita(rs.getString("documentoidentita"));
//				ret.setId(rs.getInt("id"));
//				ret.setIndirizzoColonia(rs.getString("via_colonia"));
//				ret.setNazione(rs.getString("nazione"));
//				ret.setNomeReferente(rs.getString("nome_referente"));
//				ret.setNumeroGatti(rs.getInt("numero_gatti"));
//				ret.setNumeroProtocollo(rs.getString("numero_protocollo"));
//				ret.setProvinciaColonia(rs.getString("provincia_colonia"));
//				ret.setTelefonoReferente(rs.getString("numerotelefono_referente"));
//				ret.setTrashedDate(rs.getDate("trasheddate"));
//				ret.setVeterinario(rs.getString("nome_veterinario"));
//			}
		}
		else
		{
			colonie = persistence.createSQLQuery("select * from public_functions.getdaticolonia('"+identificativo+"')", Colonia.class).list();
			if( colonie.size() > 0 && colonie.get(0)!=null )
			{
				ret = colonie.get( 0 );
			}
		}
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		
		
		memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findColonia(6)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", mc: " + identificativo);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		return ret;
	}

	public static RegistrazioniFelina findRegistrazioniEffettuabili( String identificativo, BUtente utente, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		RegistrazioniFelina ret = null;
		List<RegistrazioniFelina> reList = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getlistaregistrazionigatto('"+identificativo+"'" /*+","+utente.getClinica().getLookupAsl().getId()*/+")");
			if(rs.next())
			{
				ret = new RegistrazioniFelina();
				ret.setAdozione(rs.getBoolean("adozione"));
				ret.setAdozioneFuoriAsl(rs.getBoolean("adozionefa"));
				ret.setAdozioneVersoAssocCanili(rs.getBoolean("adozioneassoccanili"));
				ret.setDecesso(rs.getBoolean("decesso"));
				ret.setFurto(rs.getBoolean("furto"));
				ret.setRicattura(rs.getBoolean("ricattura"));
				ret.setRitrovamento(rs.getBoolean("ritrovamento"));
				//ret.setRitrovamentoSmarrNonDenunciato(rs.getBoolean("ritrovamento_smarr_non_denunciato"));
				ret.setSmarrimento(rs.getBoolean("smarrimento"));
				ret.setTrasfCanile(rs.getBoolean("trasfCanile"));
				ret.setRitornoProprietario(rs.getBoolean("ritornoproprietario"));
				ret.setSterilizzazione(rs.getBoolean("sterilizzazione"));
				ret.setTrasferimento(rs.getBoolean("trasferimento"));
				ret.setReimmissione(rs.getBoolean("reimmissione"));
				ret.setRitornoAslOrigine(rs.getBoolean("ritornoAslOrigine"));
				ret.setPrelievoLeishmania(rs.getBoolean("prelievoLeishmania"));
				ret.setPassaporto(rs.getBoolean("passaporto"));
				ret.setRinnovoPassaporto(rs.getBoolean("rinnovo_passaporto"));
				ret.setCessione(rs.getBoolean("cessione"));
				ret.setTrasfRegione(rs.getBoolean("trasfregione"));
				ret.setTrasferimentoResidenzaProp(rs.getBoolean("trasfresidenzaprop"));
				ret.setRicattura(rs.getBoolean("ricattura"));
				ret.setRitornoCanileOrigine(rs.getBoolean("restituzionecanileorigine"));
			}
		}
		else
			reList = persistence.createSQLQuery("select * from public_functions.getlistaregistrazioni('"+identificativo+"')", RegistrazioniFelina.class).list();
	
		if(!Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			if( reList.size() > 0 && reList.get(0)!=null )
			{
				ret = reList.get( 0 );
			}
		}
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		
		return ret;
	}
	
	public static void eseguiRilascioPassaporto(
			Animale animale, String dataPassaporto,
			String numeroPassaporto, String notePassaporto, BUtente utente, HttpServletRequest req ) throws UnsupportedEncodingException, Exception, ModificaBdrException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		String username = utente.getUsername();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			connection.setAutoCommit(false);
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{
			List<Integer> result = null;
			
			notePassaporto = notePassaporto.replaceAll("'", "''");
			
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				st = connection.createStatement();
				rs = st.executeQuery("select * from public_functions.inseriscipassaporto(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+dataPassaporto+"' as timestamp), " +
						"'"+numeroPassaporto+ "'," +
						"'"+notePassaporto+ "'," +
						"'"+username+ "'," +
						"2)");
				if(rs.next() && rs.getInt(1)>0)
				{
					result = new ArrayList<Integer>();
					result.add(rs.getInt(1));
				}
			}
			else
				result = persistence.createSQLQuery( 
					"select * from public_functions.inseriscipassaporto(" +
					"'"+animale.getIdentificativo()+ "'," +
					"cast('"+dataPassaporto+"' as timestamp), " +
					"'"+numeroPassaporto+ "'," +
					"'"+notePassaporto+ "'," +
					"'"+username+ "'," +
					"2)"
			).list();
					
			if( result==null || result.isEmpty() || result.get(0)==null || result.get(0)<0 )
			{
				connection.rollback();
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				throw new ModificaBdrException("la funzione ha ritornato un valore inatteso.");
			}
			else
			{
				if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
				{
					connection.commit();
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				else
				{
					persistence.commit();
					PersistenceFactory.closePersistence( persistence, false );
					aggiornaConnessioneChiusaSessione(req);
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Errore nella registrazione in Bdr del passaaporto del gatto.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Felina fallito: " + e.getMessage());
		}
		
		
		
	}
	
	public static void eseguiSmarrimento(
			Animale animale, String dataSmarrimento,
			String luogoSmarrimento, String noteSmarrimento, boolean sanzione, String importoSanzione,
			BUtente utente , HttpServletRequest req) throws UnsupportedEncodingException, Exception, ModificaBdrException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		String username = utente.getUsername();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			connection.setAutoCommit(false);
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{
			
			List<Integer> result = null;
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				st = connection.createStatement();
				rs = st.executeQuery("select * from public_functions.inseriscismarrimento(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+dataSmarrimento+"' as timestamp), " +
						"'"+luogoSmarrimento+ "'," +
						"'"+noteSmarrimento+ "'," +
						sanzione+","+
						(importoSanzione==null||importoSanzione.equals("")?(null):(importoSanzione))+"," +
						"'"+username+ "'," +
						"2)");
				if(rs.next() && rs.getInt(1)>0)
				{
					result = new ArrayList<Integer>();
					result.add(rs.getInt(1));
				}
			}
			else
				result = persistence.createSQLQuery( 
						"select * from public_functions.inseriscismarrimento(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+dataSmarrimento+"' as timestamp), " +
						"'"+luogoSmarrimento+ "'," +
						"'"+noteSmarrimento+ "'," +
						sanzione+","+
						(importoSanzione==null||importoSanzione.equals("")?(null):(importoSanzione))+"," +
						"'"+username+ "'," +
						"2)"
			).list();
					
			if( result==null || result.isEmpty() || result.get(0)==null || result.get(0)<0 )
			{
				connection.rollback();
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				throw new ModificaBdrException("la funzione ha ritornato un valore inatteso.");
			}
			else
			{
				if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
				{
					connection.commit();
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				else
				{
					persistence.commit();
					PersistenceFactory.closePersistence( persistence, false );
					aggiornaConnessioneChiusaSessione(req);
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Errore nella registrazione in Bdr dello smarrimento del gatto.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Felina fallito: " + e.getMessage());
		}
		
		
	
	
	
	}
	
	public static void eseguiRitrovamento(
			Animale animale, String dataRitrovamento,
			String luogoRitrovamento, String comune, String noteRitrovamento, BUtente utente , HttpServletRequest req) throws UnsupportedEncodingException, Exception, ModificaBdrException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		String username = utente.getUsername();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			connection.setAutoCommit(false);
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{

			List<Integer> result = null;
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				st = connection.createStatement();
				rs = st.executeQuery("select * from public_functions.inserisciritrovamento(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+dataRitrovamento+"' as timestamp), " +
						"'"+luogoRitrovamento+ "'," +
						"'"+comune + "'," +
						"'"+noteRitrovamento+ "'," +
						"'"+username+ "'," +
						"2)");
				if(rs.next() && rs.getInt(1)>0)
				{
					result = new ArrayList<Integer>();
					result.add(rs.getInt(1));
				}
			}
			else
				result = persistence.createSQLQuery( 
						"select * from public_functions.inserisciritrovamento(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+dataRitrovamento+"' as timestamp), " +
						"'"+luogoRitrovamento+ "'," +
						"'"+comune + "'," +
						"'"+noteRitrovamento+ "'," +
						"'"+username+ "'," +
						"2)"
			).list();
					
			if( result==null || result.isEmpty() || result.get(0)==null || result.get(0)<0 )
			{
				connection.rollback();
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				throw new ModificaBdrException("la funzione ha ritornato un valore inatteso.");
			}
			else
			{
				if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
				{
					connection.commit();
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				else
				{
					persistence.commit();
					PersistenceFactory.closePersistence( persistence, false );
					aggiornaConnessioneChiusaSessione(req);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Errore nella registrazione in Bdr del ritrovamento del gatto.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Felina fallito: " + e.getMessage());
		}
		
		
	
	}
	
	public static void eseguiRitrovamentoSmarrNonDenunciato(
			Animale animale, String dataRitrovamento,
			String luogoRitrovamento, String comune, String noteRitrovamento, BUtente utente , HttpServletRequest req) throws UnsupportedEncodingException, Exception, ModificaBdrException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		String username = utente.getUsername();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			connection.setAutoCommit(false);
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{

			List<Integer> result = null;
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				st = connection.createStatement();
				rs = st.executeQuery("select * from public_functions.inserisciritrovamentosmarrnondenunciato(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+dataRitrovamento+"' as timestamp), " +
						"'"+luogoRitrovamento+ "'," +
						"'"+comune + "'," +
						"'"+noteRitrovamento+ "'," +
						"'"+username+ "'," +
						"2)");
				if(rs.next() && rs.getInt(1)>0)
				{
					result = new ArrayList<Integer>();
					result.add(rs.getInt(1));
				}
			}
			else
				result = persistence.createSQLQuery( 
						"select * from public_functions.inserisciritrovamentosmarrnondenunciato(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+dataRitrovamento+"' as timestamp), " +
						"'"+luogoRitrovamento+ "'," +
						"'"+comune + "'," +
						"'"+noteRitrovamento+ "'," +
						"'"+username+ "'," +
						"2)"
			).list();
					
			if( result==null || result.isEmpty() || result.get(0)==null || result.get(0)<0 )
			{
				connection.rollback();
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				throw new ModificaBdrException("la funzione ha ritornato un valore inatteso.");
			}
			else
			{
				if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
				{
					connection.commit();
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				else
				{
					persistence.commit();
					PersistenceFactory.closePersistence( persistence, false );
					aggiornaConnessioneChiusaSessione(req);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Errore nella registrazione in Bdr del ritrovamento del gatto.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Felina fallito: " + e.getMessage());
		}
		
		
	
	}
	
	
	public static void eseguiDecesso(
			Animale animale, int decessoCode, String dataMorte, boolean dataDecessoPresunta, String comune, String indirizzo, String note, BUtente utente , HttpServletRequest req) throws UnsupportedEncodingException, Exception, ModificaBdrException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		String username = utente.getUsername();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			connection.setAutoCommit(false);
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{
			List<Integer> result = null;
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				st = connection.createStatement();
				rs = st.executeQuery("select * from public_functions.inseriscidecesso(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+dataMorte+"' as timestamp), " +
						""+decessoCode+ "," +
						dataDecessoPresunta+ "," +
						"'"+comune + "'," +
						"'"+indirizzo+"',"+
						"'"+note+"',"+
						"'"+username+ "'," +
						"2)");
				if(rs.next() && rs.getInt(1)>0)
				{
					result = new ArrayList<Integer>();
					result.add(rs.getInt(1));
				}
			}
			else
				result = persistence.createSQLQuery( 
						"select * from public_functions.inseriscidecesso(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+dataMorte+"' as timestamp), " +
						""+decessoCode+ "," +
						dataDecessoPresunta+ "," +
						"'"+comune + "'," +
						"'"+indirizzo+"',"+
						"'"+note+"',"+
						"'"+username+ "'," +
						"2)"
			).list();
					
			if( result==null || result==null || result.isEmpty() || result.get(0)==null || result.get(0)<0 )
			{
				connection.rollback();
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				throw new ModificaBdrException("la funzione ha ritornato un valore inatteso.");
			}
			else
			{
				if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
				{
					connection.commit();
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				else
				{
					persistence.commit();
					PersistenceFactory.closePersistence( persistence, false );
					aggiornaConnessioneChiusaSessione(req);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Errore nella registrazione in Bdr del decesso del gatto.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Felina fallito: " + e.getMessage());
		}
		
		
	
	}
	
	public static void eseguiModificaAnagrafica(Animale animale, int razza,int mantello, 
			String sesso, LookupTaglie taglia,BUtente utente, HttpServletRequest req) throws UnsupportedEncodingException, Exception, ModificaBdrException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		String username = utente.getUsername();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			connection.setAutoCommit(false);
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{
			List<Integer> result = null;
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				result = new ArrayList<Integer>();
				st = connection.createStatement();
				rs = st.executeQuery("select * from public_functions.updatedatianagrafici(" +
						"'"+animale.getIdentificativo()+ "'," +
						""+mantello+ "," +
						"'"+sesso+ "'," +
						"null," +
						""+razza+ "," +
						"'"+username+ "'" +
						")");
				if(rs.next() && rs.getInt(1)>0)
				{
					result = new ArrayList<Integer>();
					result.add(rs.getInt(1));
				}
			}
			else
				result = persistence.createSQLQuery( 
						"select * from public_functions.updatedatianagrafici(" +
						"'"+animale.getIdentificativo()+ "'," +
						""+mantello+ "," +
						"'"+sesso+ "'," +
						"null," +
						""+razza+ "," +
						"'"+username+ "'" +
						")"
			).list();
					
			if( result==null || result.isEmpty() || result.get(0)==null || result.get(0)<0 )
			{
				connection.rollback();
				connection.close();
				aggiornaConnessioneChiusaSessione(req);
				throw new ModificaBdrException("la funzione ha ritornato un valore inatteso.");
			}
			else
			{
				if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
				{
					connection.commit();
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				else
				{
					persistence.commit();
					PersistenceFactory.closePersistence( persistence, false );
					aggiornaConnessioneChiusaSessione(req);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Errore nella registrazione in Bdr della modifica dati anagrafici del gatto.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Felina fallito: " + e.getMessage());
		}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static RegistrazioniFelinaResponse getInfoDecesso(String identificativo, BUtente utente, ServicesStatus status, Connection connection , HttpServletRequest req) throws UnsupportedEncodingException, SQLException, ClassNotFoundException, NamingException 
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceCanina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		RegistrazioniFelinaResponse ret = null;
		List<RegistrazioniFelinaResponse> cani = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
//			st = connection.createStatement();
//			rs = st.executeQuery("select * from public_functions.getdaticane('"+identificativo+"')");
			DataCaching data = new DataCaching(connection);
			
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				QueryResult qr = data.execute("select * from public_functions.getinfodecesso('"+identificativo+"')", parameters);
				
				result = (ArrayList) qr.getRows();
				
				Iterator<String> it = result.iterator();
				
				
				/*while (it.hasNext()){
					System.out.println("Record: "+it.next());
				}*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		else
			cani = persistence.createSQLQuery("select * from public_functions.getinfodecesso('"+identificativo+"')", RegistrazioniCaninaResponse.class).list();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
			HashMap<String,Object> decesso = (HashMap<String, Object>) result.iterator().next();
			
			if (decesso.get("data_decesso")!=null ) 
			{
				ret = new RegistrazioniFelinaResponse();
				ret.setDecessoValue((String) decesso.get("decesso_value"));
				if(decesso.get("id_tipo_decesso")!=null)
					ret.setIdTipoDecesso((Integer) decesso.get("id_tipo_decesso"));
				ret.setDataDecessoPresunta((Boolean) decesso.get("is_data_decesso_presunta"));
				ret.setDataEvento((Date) decesso.get("data_decesso"));
			}
			
//			if(rs.next() && rs.getInt("id")>0)
//			{
//				ret = new Cane();
//				ret.setDataDecesso(rs.getDate("data_decesso"));
//				ret.setDataNascita(rs.getDate("data_nascita"));
//				ret.setDecessoValue(rs.getString("decesso_value"));
//				ret.setDescrizioneMantello(rs.getString("descrizione_mantello"));
//				ret.setDescrizioneRazza(rs.getString("descrizione_razza"));
//				ret.setDescrizioneTaglia(rs.getString("descrizione_taglia"));
//				ret.setId(rs.getInt("id"));
//				ret.setIdTaglia(rs.getInt("id_taglia"));
//				ret.setIdTipoDecesso(rs.getInt("id_tipo_decesso"));
//				ret.setDataNascitaPresunta(rs.getBoolean("is_data_nascita_presunta"));
//				ret.setDataDecessoPresunta(rs.getBoolean("is_data_decesso_presunta"));
//				ret.setMantello(rs.getInt("mantello"));
//				ret.setMc(rs.getString("mc"));
//				ret.setNoteRitrovamento(rs.getString("note_ritrovamento"));
//				ret.setRazza(rs.getInt("razza"));
//				ret.setSesso(rs.getString("sesso"));
//				ret.setStatoAttuale(rs.getString("stato_attuale"));
//				ret.setSterilizzato(rs.getBoolean("sterilizzato"));
//				ret.setTrashedDate(rs.getDate("trashed_date"));
//			}
			
		}
		else
		{
			if( cani.size() > 0 && cani.get(0)!=null )
			{
				ret = cani.get( 0 );
			}
		}
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		return ret;
		
	}
	
	public static List<LookupMantelli> getMantelli( Connection connection, HttpServletRequest req) throws ClassNotFoundException, SQLException, NamingException
	{
		RegistrazioniFelinaResponse ret = null;
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		List<LookupMantelli> mantelli = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getmantelli()");
			mantelli = new ArrayList<LookupMantelli>();
			while(rs.next())
			{
				LookupMantelli mantello = new LookupMantelli();
				mantello.setCane(rs.getBoolean("cane"));
				mantello.setDescription(rs.getString("description"));
				mantello.setEnabled(rs.getBoolean("enabled"));
				mantello.setGatto(rs.getBoolean("gatto"));
				mantello.setId(rs.getInt("id"));
				mantello.setLevel(rs.getInt("level"));
				mantelli.add(mantello);
			}
		}
		else
			mantelli = persistence.createSQLQuery("select * from public_functions.getmantelli()", LookupMantelli.class).list();
		
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		
		return mantelli;
	}
	
	public static List<LookupRazze> getRazze( Connection connection, HttpServletRequest req) throws SQLException, ClassNotFoundException, NamingException
	{
		RegistrazioniFelinaResponse ret = null;
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		List<LookupRazze> razze = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getrazze()");
			razze = new ArrayList<LookupRazze>();
			while(rs.next())
			{
				LookupRazze razza = new LookupRazze();
				razza.setCane(rs.getBoolean("cane"));
				razza.setDescription(rs.getString("description"));
				razza.setEnabled(rs.getBoolean("enabled"));
				razza.setGatto(rs.getBoolean("gatto"));
				razza.setId(rs.getInt("id"));
				razza.setLevel(rs.getInt("level"));
				razza.setEnci(rs.getString("enci"));
				razze.add(razza);
			}
		}
		else
			razze = persistence.createSQLQuery("select * from public_functions.getrazze()", LookupRazze.class).list();
		
		
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		
		return razze;
	}
	
	public static LookupRazze getRazza( int razza, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		LookupRazze ret = null;
		List<LookupRazze> razze = null;
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getrazza("+razza+")");
			if(rs.next())
			{
				ret = new LookupRazze();
				ret.setCane(rs.getBoolean("cane"));
				ret.setDescription(rs.getString("description"));
				ret.setEnabled(rs.getBoolean("enabled"));
				ret.setGatto(rs.getBoolean("gatto"));
				ret.setId(rs.getInt("id"));
				ret.setLevel(rs.getInt("level"));
				ret.setEnci(rs.getString("enci"));
			}
		}
		else
		{
			razze = persistence.createSQLQuery("select * from public_functions.getrazza("+razza+")", LookupRazze.class).list();
			
			if( razze.size() > 0 && razze.get(0)!=null)
			{
				ret = razze.get( 0 );
			}
		}
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		
		return ret;
	}
	
	public static LookupMantelli getMantello( int mantello, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		LookupMantelli ret = null;
		List<LookupMantelli> mantelli = null;
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getmantello("+mantello+")");
			if(rs.next())
			{
				ret = new LookupMantelli();
				ret.setCane(rs.getBoolean("cane"));
				ret.setDescription(rs.getString("description"));
				ret.setEnabled(rs.getBoolean("enabled"));
				ret.setGatto(rs.getBoolean("gatto"));
				ret.setId(rs.getInt("id"));
				ret.setLevel(rs.getInt("level"));
			}
		}
		else
		{
			mantelli = persistence.createSQLQuery("select * from public_functions.getmantello("+mantello+")", LookupMantelli.class).list();
			
			if( mantelli.size() > 0 && mantelli.get(0)!=null)
			{
				ret = mantelli.get( 0 );
			}
		}
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		
		return ret;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static RegistrazioniFelinaResponse getInfoDecesso(Gatto gatto ) throws UnsupportedEncodingException, SQLException, ClassNotFoundException, NamingException 
	{
		RegistrazioniFelinaResponse ret = null;
		if(gatto!=null)
		{
			if( gatto.getDataDecesso() != null )
			{
				ret = new RegistrazioniFelinaResponse();
				ret.setDataEvento( gatto.getDataDecesso() );
				ret.setDataDecessoPresunta( gatto.getIsDataDecessoPresunta() );
				ret.setDecessoValue(gatto.getDecessoValue());
			}
		}
			
		return ret;
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
	
	
	public static Gatto findGatto( String identificativo, BUtente utente, ServicesStatus status, Connection connection , HttpServletRequest req) throws ClassNotFoundException, SQLException, NamingException 
	{
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findGatto" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", mc: " + identificativo);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();

		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		Gatto ret = null;
		List<Gatto> gatti = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
//			st = connection.createStatement();
//			rs = st.executeQuery("select * from public_functions.getdatigatto('"+identificativo+"')");
			
			DataCaching data = new DataCaching(connection);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			 dataEnd  	= new Date();
			 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findGatto(0)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
					   ", mc: " + identificativo);
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				QueryResult qr = data.execute("select * from public_functions.getdatigatto( '"+identificativo+"')", parameters);
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findGatto(1)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				result = (ArrayList) qr.getRows();
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findGatto(2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				Iterator<String> it = result.iterator();
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findGatto(3)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				/*while (it.hasNext()){
					System.out.println("Record: "+it.next());
				}*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			gatti = persistence.createSQLQuery("select * from public_functions.getdatigatto('"+identificativo+"')", Gatto.class).list();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
				
				HashMap<String, Object> gatto = (HashMap<String, Object>) result.iterator().next();
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findGatto(5)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if (gatto.get("id")!=null && (Integer) gatto.get("id") >0 ) {
					ret = new Gatto();
					ret.setDataDecesso((Date) gatto.get("data_decesso"));
					ret.setDataNascita((Date) gatto.get("data_nascita"));
					ret.setDecessoValue((String) gatto.get("decesso_value"));
					ret.setDescrizioneMantello((String) gatto.get("descrizione_mantello"));
					ret.setDescrizioneRazza((String) gatto.get("descrizione_razza"));
					ret.setDescrizioneTaglia((String) gatto.get("descrizione_taglia"));
					ret.setId((Integer) gatto.get("id"));
					ret.setIdTaglia((Integer) gatto.get("id_taglia"));
					if(gatto.get("id_tipo_decesso")!=null)
						ret.setIdTipoDecesso((Integer) gatto.get("id_tipo_decesso"));
					ret.setDataRegistrazione((Date) gatto.get("data_registrazione"));
					ret.setDataChippatura((Date) gatto.get("data_chippatura"));
					ret.setIdProprietario((Integer) gatto.get("id_proprietario"));
					ret.setIdDetentore((Integer) gatto.get("id_detentore"));
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findGatto(6)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
							   ", mc: " + identificativo);
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
					ret.setIsDataDecessoPresunta((Boolean) gatto.get("is_data_decesso_presunta"));
					ret.setIsDataNascitaPresunta((Boolean) gatto.get("is_data_nascita_presunta"));
					ret.setMantello((Integer) gatto.get("mantello"));
					ret.setMc((String) (gatto.get("mc") != null && !("").equals(gatto.get("mc"))? gatto.get("mc") : gatto.get("tatuaggio")));
					ret.setNoteRitrovamento((String) gatto.get("note_ritrovamento"));
					ret.setRazza((Integer) gatto.get("razza"));
					ret.setSesso((String) gatto.get("sesso"));
					ret.setStatoAttuale((String) gatto.get("stato_attuale"));
					ret.setSterilizzato((Boolean) gatto.get("sterilizzato"));
					ret.setTrashedDate((Date) gatto.get("trashed_date"));
					ret.setDataSterilizzazione((Date) gatto.get("data_sterilizzazione"));
					ret.setOperatoreSterilizzazione((String) gatto.get("soggetto_sterilizzante"));
					ret.setTatuaggio((String) gatto.get("tatuaggio"));
					
					memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
					 dataEnd  	= new Date();
					 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
					System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findGatto(7)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
							   "Memoria usata: " + (memFreeStart - memFreeEnd) +
							   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
							   ", mc: " + identificativo);
					memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
					dataStart 		= new Date();
					
					
				}
				
				
//				if(rs.next() && rs.getInt("id")>0)
//				{
//				ret = new Gatto();
//				ret.setDataDecesso(rs.getDate("data_decesso"));
//				ret.setDataNascita(rs.getDate("data_nascita"));
//				ret.setDecessoValue(rs.getString("decesso_value"));
//				ret.setDescrizioneMantello(rs.getString("descrizione_mantello"));
//				ret.setDescrizioneRazza(rs.getString("descrizione_razza"));
//				ret.setDescrizioneTaglia(rs.getString("descrizione_taglia"));
//				ret.setId(rs.getInt("id"));
//				ret.setIdTaglia(rs.getInt("id_taglia"));
//				ret.setIdTipoDecesso(rs.getInt("id_tipo_decesso"));
//				ret.setIsDataDecessoPresunta(rs.getBoolean("is_data_decesso_presunta"));
//				ret.setIsDataNascitaPresunta(rs.getBoolean("is_data_nascita_presunta"));
//				ret.setMantello(rs.getInt("mantello"));
//				ret.setMc(rs.getString("mc"));
//				ret.setNoteRitrovamento(rs.getString("note_ritrovamento"));
//				ret.setRazza(rs.getInt("razza"));
//				ret.setSesso(rs.getString("sesso"));
//				ret.setStatoAttuale(rs.getString("stato_attuale"));
//				ret.setSterilizzato(rs.getBoolean("sterilizzato"));
//				ret.setTrashedDate(rs.getDate("trashed_date"));
//			}
			
		}
		else
		{
			if( gatti.size() > 0 && gatti.get(0)!=null )
			{
				ret = gatti.get( 0 );
			}
		}
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		
		
		
		memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "FelinaRemoteUtil.findGatto(8)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", mc: " + identificativo);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		return ret;
	}

}