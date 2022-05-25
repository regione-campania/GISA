/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.EsitoLeishmaniosi;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.Registrazioni;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniCanina;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AccettazioneNoH;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleNoH;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupProvince;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.ModificaBdrException;
import it.us.web.util.properties.Application;
import it.us.web.util.vam.DataCaching;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import crypto.nuova.gestione.ClientSCAAesServlet;

public class CaninaRemoteUtil
{
	private static GsonBuilder gb;
	private static Gson gson;
	
	final static Logger logger = LoggerFactory.getLogger(CaninaRemoteUtil.class);
	
	static
	{
		gb = new GsonBuilder();
		gb.setDateFormat( "yyyy-MM-dd HH:mm:ss" );
		gson = gb.create();
	}
	
	public static Cane findCane( String identificativo, ServicesStatus status, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException 
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
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceCanina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		Cane ret = null;
		List<Cane> cani = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
//			st = connection.createStatement();
//			rs = st.executeQuery("select * from public_functions.getdaticane('"+identificativo+"')");
			DataCaching data = new DataCaching(connection);
			
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				QueryResult qr = data.execute("select * from public_functions.getdaticane('"+identificativo+"')", parameters);
				
				result = (ArrayList) qr.getRows();
				
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
			cani = persistence.createSQLQuery("select * from public_functions.getdaticane('"+identificativo+"')", Cane.class).list();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
			HashMap<String,Object> cane = (HashMap<String, Object>) result.iterator().next();
			
			if (cane.get("id")!=null && (Integer) cane.get("id")> 0) {
				ret = new Cane();
				ret.setDataDecesso((Date) cane.get("data_decesso"));
				ret.setDataNascita((Date) cane.get("data_nascita"));
				ret.setDecessoValue((String) cane.get("decesso_value"));
				ret.setDescrizioneMantello((String) cane.get("descrizione_mantello"));
				ret.setDescrizioneRazza((String) cane.get("descrizione_razza"));
				ret.setDescrizioneTaglia((String) cane.get("descrizione_taglia"));
				ret.setId((Integer) cane.get("id"));
				ret.setIdTaglia((Integer) cane.get("id_taglia"));
				if(cane.get("id_tipo_decesso")!=null)
					ret.setIdTipoDecesso((Integer) cane.get("id_tipo_decesso"));
				ret.setDataRegistrazione((Date) cane.get("data_registrazione"));
				ret.setDataChippatura((Date) cane.get("data_chippatura"));
				ret.setIdProprietario((Integer) cane.get("id_proprietario"));
				ret.setIdDetentore((Integer) cane.get("id_detentore"));
				ret.setDataNascitaPresunta((Boolean) cane.get("is_data_nascita_presunta"));
				ret.setDataDecessoPresunta((Boolean) cane.get("is_data_decesso_presunta"));
				ret.setMantello((Integer) cane.get("mantello"));
				ret.setMc((String) (cane.get("mc") != null && !("").equals(cane.get("mc"))? cane.get("mc") :cane.get("tatuaggio")));
				ret.setNoteRitrovamento((String) cane.get("note_ritrovamento"));
				ret.setRazza((Integer) cane.get("razza"));
				ret.setSesso((String) cane.get("sesso"));
				ret.setStatoAttuale((String) cane.get("stato_attuale"));
				ret.setSterilizzato((Boolean) cane.get("sterilizzato"));
				ret.setTrashedDate((Date) cane.get("trashed_date"));
				ret.setDataSterilizzazione((Date) cane.get("data_sterilizzazione"));
				ret.setOperatoreSterilizzazione((String) cane.get("soggetto_sterilizzante"));
				ret.setTatuaggio((String) cane.get("tatuaggio"));
				
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

	public static ProprietarioCane findProprietario( String identificativo, BUtente utente, Connection connection , HttpServletRequest req) throws Exception
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
		System.out.println("Modulo in esecuzione: " + "CaninaRemoteUtil.findPropCane" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", mc: " + identificativo);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
		
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
		
		List<ProprietarioCane> proprietari = null;
		ProprietarioCane ret = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			DataCaching data = new DataCaching(connection);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			 dataEnd  	= new Date();
			 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "CaninaRemoteUtil.findPropCane(0)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
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
				System.out.println("Modulo in esecuzione: " + "CaninaRemoteUtil.findPropCane(1)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				result = (ArrayList) qr.getRows();
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "CaninaRemoteUtil.findPropCane(2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				Iterator<String> it = result.iterator();
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "CaninaRemoteUtil.findPropCane(3)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
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
				ret = new ProprietarioCane();
				ret.setAsl((Integer) proprietario.get("id_asl"));
				ret.setCap((String) proprietario.get("cap"));
				ret.setCitta((String) proprietario.get("citta"));
				ret.setCodiceFiscale((String) proprietario.get("codicefiscale"));
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "CaninaRemoteUtil.findPropCane(4)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
						   ", mc: " + identificativo);
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				ret.setCognome((String) proprietario.get("cognome"));
				ret.setDocumentoIdentita((String) proprietario.get("documentoidentita"));
				ret.setId((Integer) proprietario.get("id"));
				ret.setNazione((String) proprietario.get("nazione"));
				ret.setNome((String) proprietario.get("nome"));
				ret.setNumeroTelefono((String) proprietario.get("numerotelefono"));
				ret.setTipo((String) proprietario.get("tipo"));
				
				 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "CaninaRemoteUtil.findPropCane(5)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
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
					System.out.println("Modulo in esecuzione: " + "CaninaRemoteUtil.findPropCane(6)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
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
				}
				
				ret.setTrashedDate((Date) proprietario.get("trasheddate"));
				ret.setVia((String) proprietario.get("via"));
			}
//			st = connection.createStatement();
//			rs = st.executeQuery("select * from public_functions.getdatiproprietarioanimale('"+identificativo+"')");
//			if(rs.next() && rs.getInt("id")>0)
//			{
//				ret = new ProprietarioCane();
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
			proprietari = persistence.createSQLQuery("select * from public_functions.getdatiproprietarioanimale('"+identificativo+"')", ProprietarioCane.class).list();
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
		System.out.println("Modulo in esecuzione: " + "CaninaRemoteUtil.findPropCane" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", mc: " + identificativo);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
		
		return ret;
	}

	public static String getLoginUrl( BUtente utente, String microchip, Animale animale, LookupOperazioniAccettazione operazione, Accettazione accettazione, Integer idRegBdr,Connection connection, HttpServletRequest req, String caller ) throws Exception
	{
		String BDU_PORTALE_URL 		= /*"http://";*/ "";
		String BDU_URL 		  		= ((InetAddress)((HashMap<String, InetAddress>)req.getSession().getServletContext().getAttribute("hosts")).get("srvBDUW")).getHostAddress();
	    //InetAddress iadd = InetAddress.getByName(BDU_URL);
	     //System.out.println(iadd.getHostName());
		//String BDU_URL_ALIAS = iadd.getHostName();
		String BDU_PORT 		  		= Application.get("BDU_PORT");
		String BDU_APPLICATION_NAME 	= Application.get("BDU_APPLICATION_NAME");
		BDU_PORTALE_URL =     /*BDU_PORTALE_URL +     BDU_URL + ":" + BDU_PORT +*/ "/" + BDU_APPLICATION_NAME;
		
		String username = utente.getUsername();
		String ret = BDU_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso" + Token.generate( username );
		if(caller!=null)
			ret+="&caller=" + caller;
		
		ret+="&id_canile=" + utente.getClinica().getId();
		ret+="&id_stabilimento_gisa=" + utente.getClinica().getIdStabilimentoGisa();
			
		Context ctxVam = new InitialContext();
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = dsVam.getConnection();
		aggiornaConnessioneApertaSessione(req);
		
		ret = accodaParametriPerRegBdr(microchip, animale, operazione, ret, connectionVam, accettazione, utente, idRegBdr, connection,req);
		System.out.println("Login BDU: " + ret);
		connectionVam.close();
		aggiornaConnessioneChiusaSessione(req);
		return ret;
	}
	
	public static String getLoginUrl( BUtente utente, String microchip, Animale animale, LookupOperazioniAccettazione operazione, Accettazione accettazione, Integer idRegBdr,Connection connection, HttpServletRequest req, String caller, Boolean adozioneFuoriAsl, Boolean adozioneVersoAssocCanili ) throws Exception
	{
		String BDU_PORTALE_URL 		= /*"http://";*/ "";
		String BDU_URL 		  		= ((InetAddress)((HashMap<String, InetAddress>)req.getSession().getServletContext().getAttribute("hosts")).get("srvBDUW")).getHostAddress();
	    //InetAddress iadd = InetAddress.getByName(BDU_URL);
	     //System.out.println(iadd.getHostName());
		//String BDU_URL_ALIAS = iadd.getHostName();
		String BDU_PORT 		  		= Application.get("BDU_PORT");
		String BDU_APPLICATION_NAME 	= Application.get("BDU_APPLICATION_NAME");
		BDU_PORTALE_URL =     /*BDU_PORTALE_URL +     BDU_URL + ":" + BDU_PORT +*/ "/" + BDU_APPLICATION_NAME;
		
		String username = utente.getUsername();
		String ret = BDU_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso" + Token.generate( username );
		if(caller!=null)
			ret+="&caller=" + caller;
		
		ret+="&id_canile=" + utente.getClinica().getId();
		ret+="&id_stabilimento_gisa=" + utente.getClinica().getIdStabilimentoGisa();
			
		Context ctxVam = new InitialContext();
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = dsVam.getConnection();
		aggiornaConnessioneApertaSessione(req);
		
		ret = accodaParametriPerRegBdr(microchip, animale, operazione, ret, connectionVam, accettazione, utente, idRegBdr, connection,req, adozioneFuoriAsl,adozioneVersoAssocCanili);
		System.out.println("Login BDU: " + ret);
		connectionVam.close();
		aggiornaConnessioneChiusaSessione(req);
		return ret;
	}
	
	public static String getLoginUrl( BUtente utente, String microchip, AnimaleNoH animale, LookupOperazioniAccettazione operazione, Accettazione accettazione, Integer idRegBdr,Connection connection, HttpServletRequest req, String caller ) throws Exception
	{
		String BDU_PORTALE_URL 		= /*"http://";*/ "";
		String BDU_URL 		  		= ((InetAddress)((HashMap<String, InetAddress>)req.getSession().getServletContext().getAttribute("hosts")).get("srvBDUW")).getHostAddress();
	    //InetAddress iadd = InetAddress.getByName(BDU_URL);
	     //System.out.println(iadd.getHostName());
		//String BDU_URL_ALIAS = iadd.getHostName();
		String BDU_PORT 		  		= Application.get("BDU_PORT");
		String BDU_APPLICATION_NAME 	= Application.get("BDU_APPLICATION_NAME");
		BDU_PORTALE_URL =     /*BDU_PORTALE_URL +     BDU_URL + ":" + BDU_PORT +*/ "/" + BDU_APPLICATION_NAME;
		
		String username = utente.getUsername();
		String ret = BDU_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso" + Token.generate( username );
		if(caller!=null)
			ret+="&caller=" + caller;
		
		ret+="&id_canile=" + utente.getClinica().getId();
		ret+="&id_stabilimento_gisa=" + utente.getClinica().getIdStabilimentoGisa();
			
		Context ctxVam = new InitialContext();
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = dsVam.getConnection();
		aggiornaConnessioneApertaSessione(req);
		
		ret = accodaParametriPerRegBdr(microchip, animale, operazione, ret, connectionVam, accettazione, utente, idRegBdr, connection,req);
		System.out.println("Login BDU: " + ret);
		connectionVam.close();
		aggiornaConnessioneChiusaSessione(req);
		return ret;
	}
	
	public static String getLoginUrl( BUtente utente, String microchip, AnimaleNoH animale, LookupOperazioniAccettazione operazione, AccettazioneNoH accettazione, Integer idRegBdr,Connection connection, HttpServletRequest req, String caller ) throws Exception
	{
		String BDU_PORTALE_URL 		= /*"http://";*/ "";
		String BDU_URL 		  		= ((InetAddress)((HashMap<String, InetAddress>)req.getSession().getServletContext().getAttribute("hosts")).get("srvBDUW")).getHostAddress();
	    //InetAddress iadd = InetAddress.getByName(BDU_URL);
	     //System.out.println(iadd.getHostName());
		//String BDU_URL_ALIAS = iadd.getHostName();
		String BDU_PORT 		  		= Application.get("BDU_PORT");
		String BDU_APPLICATION_NAME 	= Application.get("BDU_APPLICATION_NAME");
		BDU_PORTALE_URL =     /*BDU_PORTALE_URL +     BDU_URL + ":" + BDU_PORT +*/ "/" + BDU_APPLICATION_NAME;
		
		String username = utente.getUsername();
		String ret = BDU_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso" + Token.generate( username );
		if(caller!=null)
			ret+="&caller=" + caller;
		
		ret+="&id_canile=" + utente.getClinica().getId();
		ret+="&id_stabilimento_gisa=" + utente.getClinica().getIdStabilimentoGisa();
			
		Context ctxVam = new InitialContext();
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = dsVam.getConnection();
		aggiornaConnessioneApertaSessione(req);
		
		ret = accodaParametriPerRegBdr(microchip, animale, operazione, ret, connectionVam, accettazione, utente, idRegBdr, connection,req);
		System.out.println("Login BDU: " + ret);
		connectionVam.close();
		aggiornaConnessioneChiusaSessione(req);
		return ret;
	}
	
	public static String getLoginUrlNuovo( BUtente utente, String microchip, AnimaleNoH animale, LookupOperazioniAccettazione operazione, Accettazione accettazione, Integer idRegBdr,Connection connection, HttpServletRequest req, String caller ) throws Exception
	{
		String BDU_PORTALE_URL 		= /*"http://";*/ "";
		String BDU_URL 		  		= ((InetAddress)((HashMap<String, InetAddress>)req.getSession().getServletContext().getAttribute("hosts")).get("srvBDUW")).getHostAddress();
	    //InetAddress iadd = InetAddress.getByName(BDU_URL);
	     //System.out.println(iadd.getHostName());
		//String BDU_URL_ALIAS = iadd.getHostName();
		String BDU_PORT 		  		= Application.get("BDU_PORT");
		String BDU_APPLICATION_NAME 	= Application.get("BDU_APPLICATION_NAME");
		BDU_PORTALE_URL =     /*BDU_PORTALE_URL +     BDU_URL + ":" + BDU_PORT +*/ "/" + BDU_APPLICATION_NAME;
		
		String username = utente.getUsername();
		
		String ret = BDU_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso" + Token.generate( username );
		
		  
		
		
		
		
		if(caller!=null)
			ret+="&caller=" + caller;
		
		ret+="&id_canile=" + utente.getClinica().getId();
		ret+="&id_stabilimento_gisa=" + utente.getClinica().getIdStabilimentoGisa();
			
		Context ctxVam = new InitialContext();
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = dsVam.getConnection();
		aggiornaConnessioneApertaSessione(req);
		
		ret = accodaParametriPerRegBdr(microchip, animale, operazione, ret, connectionVam, accettazione, utente, idRegBdr, connection,req);
		System.out.println("Login BDU: " + ret);
		connectionVam.close();
		aggiornaConnessioneChiusaSessione(req);
		return ret;
	}
	
	public static String getLoginUrlAccMultipla( BUtente utente, String microchip, Animale animale, LookupOperazioniAccettazione operazione, Accettazione accettazione, Integer idRegBdr,Connection connection, HttpServletRequest req, String caller, HashMap<String, Object> datiAnimale ) throws Exception
	{
		String BDU_PORTALE_URL 		= /*"http://";*/ "";
		String BDU_URL 		  		= ((InetAddress)((HashMap<String,InetAddress>)req.getSession().getServletContext().getAttribute("hosts")).get("srvBDUW")).getHostAddress();
	    //InetAddress iadd = InetAddress.getByName(BDU_URL);
	    //System.out.println(iadd.getHostName());
		//String BDU_URL_ALIAS = iadd.getHostName();
		String BDU_PORT 		  		= Application.get("BDU_PORT");
		String BDU_APPLICATION_NAME 	= Application.get("BDU_APPLICATION_NAME");
		//BDU_PORTALE_URL = BDU_PORTALE_URL + BDU_URL_ALIAS + ":" + BDU_PORT + "/" + BDU_APPLICATION_NAME;
		BDU_PORTALE_URL = /*BDU_PORTALE_URL + BDU_URL + ":" + BDU_PORT + */ "/" + BDU_APPLICATION_NAME;
		
		String username = utente.getUsername();
		String ret = BDU_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso" + Token.generate( username );
		if(caller!=null)
			ret+="&caller=" + caller;
		
		ret+="&id_canile=" + utente.getClinica().getId();
		ret+="&id_stabilimento_gisa=" + utente.getClinica().getIdStabilimentoGisa();
		
		ret+="&accMultipla=true";
		
		Iterator<Object> datiAnimaleValues = datiAnimale.values().iterator();
		Iterator<String> datiAnimaleKeys = datiAnimale.keySet().iterator();
		while(datiAnimaleKeys.hasNext())
		{
			ret += "&" + datiAnimaleKeys.next() + "=" + datiAnimaleValues.next();
		}
		
		Context ctxVam = new InitialContext();
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = dsVam.getConnection();
		aggiornaConnessioneApertaSessione(req);
		ret = accodaParametriPerRegBdr(microchip, animale, operazione, ret, connectionVam, accettazione, utente, idRegBdr, connection,req);
		System.out.println("Login BDU: " + ret);
		connectionVam.close();
		aggiornaConnessioneChiusaSessione(req);
		
		return ret;
	}
	
	public static String getLogoutUrl( BUtente utente, HttpServletRequest req) throws UnknownHostException {
        String BDU_PORTALE_URL = /*"http://"*/ "";
        String BDU_URL = ((InetAddress)((HashMap<String,InetAddress>)req.getSession().getServletContext().getAttribute("hosts")).get("srvBDUW")).getHostAddress();
        //InetAddress iadd = InetAddress.getByName(BDU_URL);
	    //System.out.println(iadd.getHostName());
		//String BDU_URL_ALIAS = iadd.getHostName();
		
		String BDU_PORT 		  		= Application.get("BDU_PORT");
		String BDU_APPLICATION_NAME 	= Application.get("BDU_APPLICATION_NAME");
		//BDU_PORTALE_URL = BDU_PORTALE_URL + BDU_URL_ALIAS + ":" + BDU_PORT + "/" + BDU_APPLICATION_NAME;
		BDU_PORTALE_URL = /*BDU_PORTALE_URL + BDU_URL + ":" + BDU_PORT +*/ "/" + BDU_APPLICATION_NAME;
		
		return BDU_PORTALE_URL + "/Login.do?command=Logout";
	}

	public static RegistrazioniCanina findRegistrazioniEffettuabili( String identificativo, BUtente utente, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
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
		
		RegistrazioniCanina ret = null;
		List<RegistrazioniCanina> reList = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getlistaregistrazionicane('"+identificativo+"'" + ","+utente.getClinica().getLookupAsl().getId()+")");
			if(rs.next())
			{
				ret = new RegistrazioniCanina();
				ret.setAdozione(rs.getBoolean("adozione"));
				ret.setAdozioneFuoriAsl(rs.getBoolean("adozionefa"));
				ret.setAdozioneVersoAssocCanili(rs.getBoolean("adozioneAssocCanili"));
				ret.setDecesso(rs.getBoolean("decesso"));
				ret.setFurto(rs.getBoolean("furto"));
				ret.setRicattura(rs.getBoolean("ricattura"));
				ret.setRitrovamento(rs.getBoolean("ritrovamento"));
				ret.setRitrovamentoSmarrNonDenunciato(rs.getBoolean("ritrovamentosmarrnondenunciato"));
				ret.setSmarrimento(rs.getBoolean("smarrimento"));
				ret.setTrasfCanile(rs.getBoolean("trasfCanile"));
				ret.setRitornoProprietario(rs.getBoolean("ritornoproprietario"));
				ret.setTrasferimento(rs.getBoolean("trasferimento"));
				ret.setSterilizzazione(rs.getBoolean("sterilizzazione"));
				ret.setPrelievoDna(rs.getBoolean("prelievodna"));
				ret.setPrelievoLeishmania(rs.getBoolean("prelievoLeishmania"));
				ret.setRinnovoPassaporto(rs.getBoolean("rinnovo_passaporto"));
				ret.setReimmissione(rs.getBoolean("reimmissione"));
				ret.setPassaporto(rs.getBoolean("passaporto"));
				ret.setCessione(rs.getBoolean("cessione"));
				ret.setTrasfRegione(rs.getBoolean("trasfregione"));
				ret.setTrasferimentoResidenzaProp(rs.getBoolean("trasfresidenzaprop"));
				ret.setRicattura(rs.getBoolean("ricattura"));
				ret.setRitornoAslOrigine(rs.getBoolean("ritornoAslOrigine"));
				ret.setRitornoCanileOrigine(rs.getBoolean("restituzionecanileorigine"));
				
			}
		}
		else
			reList = persistence.createSQLQuery("select * from public_functions.getlistaregistrazionicane('"+identificativo+"')", RegistrazioniCanina.class).list();
	
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
			String numeroPassaporto, String notePassaporto, BUtente utente , HttpServletRequest req) throws UnsupportedEncodingException, Exception, ModificaBdrException
	{
		String username = utente.getUsername();
		
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
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
			persistence = PersistenceFactory.getPersistenceCanina();
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
			logger.error("Errore nella registrazione in Bdr del passaaporto del cane.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Canina fallito: " + e.getMessage());
		}
		
		
		
	}
	
	public static void eseguiSmarrimento(
			Animale animale, String dataSmarrimento,
			String luogoSmarrimento, String noteSmarrimento, boolean sanzione, String importoSanzione,
			BUtente utente, HttpServletRequest req ) throws UnsupportedEncodingException, Exception, ModificaBdrException
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
			persistence = PersistenceFactory.getPersistenceCanina();
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
			logger.error("Errore nella registrazione in Bdr dello smarrimento del cane.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Canina fallito: " + e.getMessage());
		}
		
		
	
	
	
	}
	
	public static void eseguiRitrovamento(
			Animale animale, String dataRitrovamento,
			String luogoRitrovamento, String comune, String noteRitrovamento, BUtente utente, HttpServletRequest req ) throws UnsupportedEncodingException, Exception, ModificaBdrException
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
			persistence = PersistenceFactory.getPersistenceCanina();
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
			logger.error("Errore nella registrazione in Bdr del ritrovamento del cane.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Canina fallito: " + e.getMessage());
		}
		
		
	
	}
	
	public static void eseguiRitrovamentoSmarrNonDenunciato(
			Animale animale, String dataRitrovamento,
			String luogoRitrovamento, String comune, String noteRitrovamento, BUtente utente, HttpServletRequest req ) throws UnsupportedEncodingException, Exception, ModificaBdrException
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
			persistence = PersistenceFactory.getPersistenceCanina();
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
			logger.error("Errore nella registrazione in Bdr del ritrovamento del cane.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Canina fallito: " + e.getMessage());
		}
		
		
	
	}
	
	
	public static void eseguiDecesso(
			Animale animale, int decessoCode, String dataMorte, boolean dataDecessoPresunta, String comune, String indirizzo, String note, BUtente utente, HttpServletRequest req ) throws UnsupportedEncodingException, Exception, ModificaBdrException
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
			persistence = PersistenceFactory.getPersistenceCanina();
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
			logger.error("Errore nella registrazione in Bdr del decesso del cane.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Canina fallito: " + e.getMessage());
		}
		
		
	
	}
	
	public static void eseguiRicattura( AnimaleNoH animale, String data, BUtente utente, HttpServletRequest req ) throws UnsupportedEncodingException, Exception, ModificaBdrException
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
			persistence = PersistenceFactory.getPersistenceCanina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{
			List<Integer> result = null;
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				st = connection.createStatement();
				rs = st.executeQuery("select * from public_functions.inserisciricattura(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+data+"' as timestamp), " +
						"'"+username+ "'," +
						utente.getClinica().getCanileBdu() + "," +
						"2)");
				if(rs.next() && rs.getInt(1)>0)
				{
					result = new ArrayList<Integer>();
					result.add(rs.getInt(1));
				}
			}
			else
				result = persistence.createSQLQuery( 
						"select * from public_functions.inserisciricattura(" +
						"'"+animale.getIdentificativo()+ "'," +
						"cast('"+data+"' as timestamp), " +
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
			logger.error("Errore nella registrazione in Bdu della ricattura dell'animale.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Inserimento automatico della ricattura fallito: " + e.getMessage() + ". <br/>Si prega di inserirla manualmente");
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
			persistence = PersistenceFactory.getPersistenceCanina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{
			List<Integer> result = null;
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				result = new ArrayList<Integer>();
				st = connection.createStatement();
				String a = "select * from public_functions.updatedatianagrafici(" +
						"'"+animale.getIdentificativo()+ "'," +
						""+mantello+ "," +
						"'"+sesso+ "'," +
						""+taglia.getId()+ "," +
						""+razza+ "," +
						"'"+username+ "'" +
						")";
				
				rs = st.executeQuery("select * from public_functions.updatedatianagrafici(" +
						"'"+animale.getIdentificativo()+ "'," +
						""+mantello+ "," +
						"'"+sesso+ "'," +
						""+taglia.getId()+ "," +
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
						""+taglia.getId()+ "," +
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
			logger.error("Errore nella registrazione in Bdr della modifica dati anagrafici del cane.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Canina fallito: " + e.getMessage());
		}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static RegistrazioniCaninaResponse getInfoDecesso(String identificativo, BUtente utente, ServicesStatus status, Connection connection , HttpServletRequest req) throws UnsupportedEncodingException, SQLException, ClassNotFoundException, NamingException 
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
		
		RegistrazioniCaninaResponse ret = null;
		List<RegistrazioniCaninaResponse> cani = null;
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
				ret = new RegistrazioniCaninaResponse();
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
	
	
	@SuppressWarnings("unchecked")
	public static RegistrazioniCaninaResponse getInfoDecesso(Cane cane ) throws UnsupportedEncodingException, SQLException, ClassNotFoundException, NamingException 
	{
		RegistrazioniCaninaResponse ret = null;
		if(cane!=null)
		{
			if( cane.getDataDecesso() != null )
			{
				ret = new RegistrazioniCaninaResponse();
				ret.setDataEvento( cane.getDataDecesso() );
				ret.setDataDecessoPresunta( cane.isDataDecessoPresunta() );
				ret.setDecessoValue(cane.getDecessoValue());
			}
		}
			
		return ret;
	}
	
	
	public static ArrayList<EsitoLeishmaniosi> getEsitiLeishmaniosi( String identificativo, Date dataChiusuraCc, BUtente utente, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException 
	{
			
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		ArrayList<EsitoLeishmaniosi> ret = null;
		List<EsitoLeishmaniosi> esiti = null;
		DataCaching data = new DataCaching(connection);
		
		ArrayList parameters = new ArrayList();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String inputData = "null";
			if(dataChiusuraCc!=null)
				inputData = " cast('" + sdf.format(dataChiusuraCc) + "' as timestamp) ";
			
			QueryResult qr = data.execute("select * from public_functions.getesitileishmaniosi('"+identificativo+"', "+ inputData + ")", parameters);
			
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
		
		
		
		Iterator iter = result.iterator();
		ret = new ArrayList<EsitoLeishmaniosi>();
		while(iter.hasNext())
		{
			HashMap<String,Object> esito = (HashMap<String, Object>) iter.next();
			
			if (esito.get("identificativo")!=null) 
			{
				EsitoLeishmaniosi esitoTemp = new EsitoLeishmaniosi();
				esitoTemp.setDataPrelievoLeishmaniosi((Date) esito.get("data_prelievo_leishmaniosi"));
				esitoTemp.setDataAccertamento((Date) esito.get("data_accertamento"));
				esitoTemp.setDataEsitoLeishmaniosi((Date) esito.get("data_esito_leishmaniosi"));
				esitoTemp.setEsito((String) esito.get("esito"));
				esitoTemp.setEsitoCar((String) esito.get("esito_car"));
				esitoTemp.setIdentificativo((String) esito.get("identificativo"));
				ret.add(esitoTemp);
			}
			
		}
			
		
		return ret;
	}
	
	
	public static String getComuneCatturaRicattura( String identificativo, Integer idBdr, BUtente utente, Connection connection, HttpServletRequest req, Date dataAccettazione ) throws ClassNotFoundException, SQLException, NamingException 
	{
			
		Class.forName("org.postgresql.Driver");
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		DataCaching data = new DataCaching(connection);
		
		ArrayList parameters = new ArrayList();
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String inputData = " cast('" + sdf.format(dataAccettazione) + "' as timestamp) ";
			
			System.out.println("id " + identificativo);
			System.out.println("id " + idBdr);
			System.out.println("id " + inputData);
			QueryResult qr = data.execute("select * from public_functions.getcomunecatturaricattura('"+identificativo+"', " + idBdr +", "+ inputData + ")", parameters);
			result = (ArrayList) qr.getRows();
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		}
		
		Iterator iter = result.iterator();
		
		while(iter.hasNext())
		{
			HashMap<String,Object> esito = (HashMap<String, Object>) iter.next();
			
			if (esito.get("getcomunecatturaricattura")!=null) 
				return ((String) esito.get("getcomunecatturaricattura"));
			
		}
		
		return null;
	}
	
	
	public static List<LookupMantelli> getMantelli( Connection connection, HttpServletRequest req) throws ClassNotFoundException, SQLException, NamingException
	{
		RegistrazioniCaninaResponse ret = null;
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
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
		RegistrazioniCaninaResponse ret = null;
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
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
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceCanina();
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
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceCanina();
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
	
	public static List<LookupTaglie> getTaglie( HttpServletRequest req)
	{
		Persistence persistence = null;
		persistence = PersistenceFactory.getPersistenceCanina();
		aggiornaConnessioneApertaSessione(req);
		
		List<LookupTaglie> taglie = null;
		taglie = persistence.createSQLQuery("select * from public_functions.gettaglie()", LookupTaglie.class).list();
		
		PersistenceFactory.closePersistence( persistence, false );
		aggiornaConnessioneChiusaSessione(req);
		
		return taglie;
	}
	
	public static LookupTaglie getTaglia( int taglia, Connection connection, HttpServletRequest req )
	{
		Persistence persistence = null;
		persistence = PersistenceFactory.getPersistenceCanina();
		aggiornaConnessioneApertaSessione(req);
		
		LookupTaglie ret = null;
		List<LookupTaglie> taglie = null;
		taglie = persistence.createSQLQuery("select * from public_functions.gettaglia("+taglia+")", LookupTaglie.class).list();
		
		if( taglie.size() > 0 && taglie.get(0)!=null)
		{
			ret = taglie.get( 0 );
		}
		
		PersistenceFactory.closePersistence( persistence, false );
		aggiornaConnessioneChiusaSessione(req);
		
		return ret;
	}
	
	private static String accodaParametriPerRegBdr( String microchip, Animale animale, LookupOperazioniAccettazione operazione, String url, Connection connectionVam, Accettazione accettazione, BUtente utente, Integer idRegBdr, Connection connection , HttpServletRequest req) throws Exception
	{
		boolean casoDettaglioReg = idRegBdr!=null && idRegBdr>0;
		//Caso Iscrizione Anagrafe: non ho l'oggetto animale ma ho il microchip da anagrafare
		boolean casoIscrizioneAnagrafe = microchip!=null; 
		
		if(animale!=null && !casoDettaglioReg)
			url += "&microchip=" + animale.getIdentificativo();
		if( casoIscrizioneAnagrafe && !casoDettaglioReg)
			url += "&microchip=" + microchip;
		if(casoDettaglioReg)
		{	
			if(idRegBdr>1)
				url += "&id=" + idRegBdr;
			url += "&tipologiaEvento=" + RegistrazioniUtil.getIdTipoBdr(animale, accettazione, operazione, connectionVam, utente, connection,req);
		}
		if(operazione!=null && !casoDettaglioReg)
			url += "&idTipologiaEvento=" + RegistrazioniUtil.getIdTipoBdr(animale, accettazione, operazione, connectionVam, utente, connection,req);
		
		if (accettazione != null)
		{
			CartellaClinica cartellaClinica = null;
			Set<CartellaClinica> cc = accettazione.getCartellaClinicas();
			Iterator  i = cc.iterator();
			
			while (i.hasNext())
			{
				 cartellaClinica  = (CartellaClinica) i.next();
			}
	
			if (cartellaClinica != null && cartellaClinica.getDataChiusura() != null && operazione.getId()!=IdOperazioniBdr.prelievoLeishmania)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataRegistrazione=" +dateFormat.format(cartellaClinica.getDataChiusura());
			}
			else if (cartellaClinica != null && cartellaClinica.getDataChiusura() == null && operazione.getId()==IdOperazioniBdr.prelievoLeishmania)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataAperturaCC=" +dateFormat.format(cartellaClinica.getDataApertura());
			}
			else if (accettazione.getData() != null)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataRegistrazione=" +dateFormat.format(accettazione.getData());
			}
		}
		
		return url;
	}
	
	
	private static String accodaParametriPerRegBdr( String microchip, Animale animale, LookupOperazioniAccettazione operazione, String url, Connection connectionVam, Accettazione accettazione, BUtente utente, Integer idRegBdr, Connection connection , HttpServletRequest req, Boolean adozioneFuoriAsl, Boolean adozioneVersoAssocCanili) throws Exception
	{
		boolean casoDettaglioReg = idRegBdr!=null && idRegBdr>0;
		//Caso Iscrizione Anagrafe: non ho l'oggetto animale ma ho il microchip da anagrafare
		boolean casoIscrizioneAnagrafe = microchip!=null; 
		
		if(animale!=null && !casoDettaglioReg)
			url += "&microchip=" + animale.getIdentificativo();
		if( casoIscrizioneAnagrafe && !casoDettaglioReg)
			url += "&microchip=" + microchip;
		if(casoDettaglioReg)
		{	
			if(idRegBdr>1)
				url += "&id=" + idRegBdr;
			url += "&tipologiaEvento=" + RegistrazioniUtil.getIdTipoBdr(animale, accettazione, operazione, connectionVam, utente, connection,req, adozioneFuoriAsl,adozioneVersoAssocCanili);
		}
		if(operazione!=null && !casoDettaglioReg)
			url += "&idTipologiaEvento=" + RegistrazioniUtil.getIdTipoBdr(animale, accettazione, operazione, connectionVam, utente, connection,req, adozioneFuoriAsl,adozioneVersoAssocCanili);
		
		if (accettazione != null)
		{
			CartellaClinica cartellaClinica = null;
			Set<CartellaClinica> cc = accettazione.getCartellaClinicas();
			Iterator  i = cc.iterator();
			
			while (i.hasNext())
			{
				 cartellaClinica  = (CartellaClinica) i.next();
			}
	
			if (cartellaClinica != null && cartellaClinica.getDataChiusura() != null && operazione.getId()!=IdOperazioniBdr.prelievoLeishmania)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataRegistrazione=" +dateFormat.format(cartellaClinica.getDataChiusura());
			}
			else if (cartellaClinica != null && cartellaClinica.getDataChiusura() == null && operazione.getId()==IdOperazioniBdr.prelievoLeishmania)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataAperturaCC=" +dateFormat.format(cartellaClinica.getDataApertura());
			}
			else if (accettazione.getData() != null)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataRegistrazione=" +dateFormat.format(accettazione.getData());
			}
		}
		
		return url;
	}
	
	private static String accodaParametriPerRegBdr( String microchip, AnimaleNoH animale, LookupOperazioniAccettazione operazione, String url, Connection connectionVam, Accettazione accettazione, BUtente utente, Integer idRegBdr, Connection connection , HttpServletRequest req) throws Exception
	{
		boolean casoDettaglioReg = idRegBdr!=null && idRegBdr>0;
		//Caso Iscrizione Anagrafe: non ho l'oggetto animale ma ho il microchip da anagrafare
		boolean casoIscrizioneAnagrafe = microchip!=null; 
		
		if(animale!=null && !casoDettaglioReg)
			url += "&microchip=" + animale.getIdentificativo();
		if( casoIscrizioneAnagrafe && !casoDettaglioReg)
			url += "&microchip=" + microchip;
		if(casoDettaglioReg)
		{	
			if(idRegBdr>1)
				url += "&id=" + idRegBdr;
			url += "&tipologiaEvento=" + RegistrazioniUtil.getIdTipoBdr(animale, accettazione, operazione, connectionVam, utente, connection,req);
		}
		if(operazione!=null && !casoDettaglioReg)
			url += "&idTipologiaEvento=" + RegistrazioniUtil.getIdTipoBdr(animale, accettazione, operazione, connectionVam, utente, connection,req);
		
		if (accettazione != null)
		{
			CartellaClinica cartellaClinica = null;
			Set<CartellaClinica> cc = accettazione.getCartellaClinicas();
			Iterator  i = cc.iterator();
			
			while (i.hasNext())
			{
				 cartellaClinica  = (CartellaClinica) i.next();
			}
	
			if (cartellaClinica != null && cartellaClinica.getDataChiusura() != null && operazione.getId()!=IdOperazioniBdr.prelievoLeishmania)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataRegistrazione=" +dateFormat.format(cartellaClinica.getDataChiusura());
			}
			else if (cartellaClinica != null && cartellaClinica.getDataChiusura() == null && operazione.getId()==IdOperazioniBdr.prelievoLeishmania)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataAperturaCC=" +dateFormat.format(cartellaClinica.getDataApertura());
			}
			else if (accettazione.getData() != null)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataRegistrazione=" +dateFormat.format(accettazione.getData());
			}
		}
		
		return url;
	}
	
	private static String accodaParametriPerRegBdr( String microchip, AnimaleNoH animale, LookupOperazioniAccettazione operazione, String url, Connection connectionVam, AccettazioneNoH accettazione, BUtente utente, Integer idRegBdr, Connection connection , HttpServletRequest req) throws Exception
	{
		boolean casoDettaglioReg = idRegBdr!=null && idRegBdr>0;
		//Caso Iscrizione Anagrafe: non ho l'oggetto animale ma ho il microchip da anagrafare
		boolean casoIscrizioneAnagrafe = microchip!=null; 
		
		if(animale!=null && !casoDettaglioReg)
			url += "&microchip=" + animale.getIdentificativo();
		if( casoIscrizioneAnagrafe && !casoDettaglioReg)
			url += "&microchip=" + microchip;
		if(casoDettaglioReg)
		{	
			if(idRegBdr>1)
				url += "&id=" + idRegBdr;
			url += "&tipologiaEvento=" + RegistrazioniUtil.getIdTipoBdr(animale, accettazione, operazione, connectionVam, utente, connection,req);
		}
		if(operazione!=null && !casoDettaglioReg)
			url += "&idTipologiaEvento=" + RegistrazioniUtil.getIdTipoBdr(animale, accettazione, operazione, connectionVam, utente, connection,req);
		
		if (accettazione != null)
		{
			CartellaClinicaNoH cartellaClinica = null;
			Set<CartellaClinicaNoH> cc = accettazione.getCartellaClinicas();
			Iterator  i = cc.iterator();
			
			while (i.hasNext())
			{
				 cartellaClinica  = (CartellaClinicaNoH) i.next();
			}
	
			if (cartellaClinica != null && cartellaClinica.getDataChiusura() != null && operazione.getId()!=IdOperazioniBdr.prelievoLeishmania)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataRegistrazione=" +dateFormat.format(cartellaClinica.getDataChiusura());
			}
			else if (cartellaClinica != null && cartellaClinica.getDataChiusura() == null && operazione.getId()==IdOperazioniBdr.prelievoLeishmania)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataAperturaCC=" +dateFormat.format(cartellaClinica.getDataApertura());
			}
			else if (accettazione.getData() != null)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				url += "&dataRegistrazione=" +dateFormat.format(accettazione.getData());
			}
		}
		
		return url;
	}
	
	
	public static Integer getUltimaRegistrazione( String identificativo, int idTipoRegBdr, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException 
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
		
		Integer ret = null;
		List<Integer> regs = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			DataCaching data = new DataCaching(connection);
			ArrayList parameters = new ArrayList();
			try {
				System.out.println("mc: " + identificativo + ", reg: " + idTipoRegBdr);
				QueryResult qr = data.execute("select * from public_functions.getultimaregistrazione('"+identificativo+"'," + idTipoRegBdr + ")", parameters);
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
			regs = persistence.createSQLQuery("select * from public_functions.getultimaregistrazione('"+identificativo+"'," + idTipoRegBdr + ")", Integer.class).list();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
			HashMap<String,Integer> reg = (HashMap<String, Integer>) result.iterator().next();
			if (reg.get("getultimaregistrazione")!=null && (Integer) reg.get("getultimaregistrazione")> 0) {
				ret = ((Integer) reg.get("getultimaregistrazione"));
			}
		}
		else
		{
			if( regs.size() > 0 && regs.get(0)!=null )
			{
				ret = regs.get( 0 );
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
	
	
	public static Integer getIdTipoUltimaRegistrazione( String identificativo, Connection connection, HttpServletRequest req) throws ClassNotFoundException, SQLException, NamingException 
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
		
		Integer ret = null;
		List<Integer> regs = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			DataCaching data = new DataCaching(connection);
			ArrayList parameters = new ArrayList();
			try {
				
				QueryResult qr = data.execute("select * from public_functions.get_id_tipo_ultima_registrazione('"+identificativo+"')", parameters);
				result = (ArrayList) qr.getRows();
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
			regs = persistence.createSQLQuery("select * from public_functions.get_id_tipo_ultima_registrazione('"+identificativo+"')", Integer.class).list();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
			HashMap<String,Integer> reg = (HashMap<String, Integer>) result.iterator().next();
			if (reg.get("get_id_tipo_ultima_registrazione")!=null && (Integer) reg.get("get_id_tipo_ultima_registrazione")> 0) {
				ret = ((Integer) reg.get("get_id_tipo_ultima_registrazione"));
			}
		}
		else
		{
			if( regs.size() > 0 && regs.get(0)!=null )
			{
				ret = regs.get( 0 );
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
	
	
	public static Registrazioni getRitrovamentoCambioDetentore( String identificativo, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException 
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		persistence = PersistenceFactory.getPersistenceCanina();
		aggiornaConnessioneApertaSessione(req);
		
		Registrazioni ret = null;
		List<Registrazioni> regs = null;
		regs = persistence.createSQLQuery("select * from public_functions.getultimaregistrazione('"+identificativo+"')", Registrazioni.class).list();
		
		if( regs.size() > 0 && regs.get(0)!=null )
		{
			ret = regs.get( 0 );
		}
		
		PersistenceFactory.closePersistence( persistence, false );
		aggiornaConnessioneChiusaSessione(req);
		
		return ret;
	}
	
	
	
	public static boolean esisteRitrovamentoCambioDetentoreBdu(Registrazioni reg) throws ClassNotFoundException, SQLException, NamingException 
	{
		if(reg==null)
			return false;
		else if(reg.getCambioDetentore()!=null && reg.getCambioDetentore() && reg.getOrigineRegistrazione()==1)
			return true;
		else
			return false;
	}
	
	public static boolean esisteRitrovamentoCambioDetentore(Registrazioni reg) throws ClassNotFoundException, SQLException, NamingException 
	{
		if(reg==null)
			return false;
		else if(reg.getCambioDetentore()!=null && reg.getCambioDetentore())
			return true;
		else
			return false;
	}
	
	
	
	
	public static Integer getRegistrazioneBduByID( int idRegistrazioneBDU, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException 
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
		
		Integer ret = -1;
		List<Integer> regs = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			DataCaching data = new DataCaching(connection);
			ArrayList parameters = new ArrayList();
			try {
				System.out.println("id registrazione bdu: " + idRegistrazioneBDU );
				QueryResult qr = data.execute("select * from public_functions.getregistrazioneBduByID("+ idRegistrazioneBDU +")", parameters);
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
			regs = persistence.createSQLQuery("select * from public_functions.geregistrazioneBduByID("+ idRegistrazioneBDU +")", Integer.class).list();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
			HashMap<String,Integer> reg = (HashMap<String, Integer>) result.iterator().next();
			if (reg.get("getregistrazionebdubyid")!=null && (Integer) reg.get("getregistrazionebdubyid")> 0) {
				ret = ((Integer) reg.get("getregistrazionebdubyid"));
			}
		}
		else
		{
			if( regs.size() > 0 && regs.get(0)!=null )
			{
				ret = regs.get( 0 );
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
	
	
	public static Integer getRegistrazioneCancellabileBDU( String microchip, int id_registrazione, Connection connection, HttpServletRequest req ) throws ClassNotFoundException, SQLException, NamingException 
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
		
		Integer ret = -1;
		List<Integer> regs = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			DataCaching data = new DataCaching(connection);
			ArrayList parameters = new ArrayList();
			try {
				System.out.println("Mc: " + microchip );
				QueryResult qr = data.execute("select * from public_functions.checkregistrazionecancellabilebymc('"+ microchip +"'," + id_registrazione+")", parameters);
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
			regs = persistence.createSQLQuery("select * from public_functions.checkregistrazionecancellabilebymc('"+ microchip +"'," + id_registrazione+")", Integer.class).list();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
			HashMap<String,Integer> reg = (HashMap<String, Integer>) result.iterator().next();
			if (reg.get("checkregistrazionecancellabilebymc")!=null && (Integer) reg.get("checkregistrazionecancellabilebymc")> 0) {
				ret = ((Integer) reg.get("checkregistrazionecancellabilebymc"));
			}
		}
		else
		{
			if( regs.size() > 0 && regs.get(0)!=null )
			{
				ret = regs.get( 0 );
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
	
	
	public static void comunicaTrasferimentoClinicaExtraAsl(
			Animale animale, String dataAccettazioneTrasferimento, int idClinicaOrigine, int idClinicaDestinazione, int idAslClinicaDestinazione,
			BUtente utente , HttpServletRequest req) throws UnsupportedEncodingException, Exception, ModificaBdrException
	{
		String username = utente.getUsername();
		
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
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
			persistence = PersistenceFactory.getPersistenceCanina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{
			List<Integer> result = null;
			
			
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				st = connection.createStatement();
				rs = st.executeQuery("select * from public_functions.inserisciregistrazionetrasferimentoclinicavam(" +
						"cast('"+animale.getIdentificativo()+"' as text), " +
						"cast('"+dataAccettazioneTrasferimento+"' as timestamp), " +
						""+idClinicaOrigine+ "," +
						""+idClinicaDestinazione+ "," +
						"cast('"+utente.getUsername()+"' as text), " +
						""+idAslClinicaDestinazione+ ")");
				
				if(rs.next() && rs.getInt(1)>0)
				{
					result = new ArrayList<Integer>();
					result.add(rs.getInt(1));
				}
			}
			else
				result = persistence.createSQLQuery("select * from public_functions.inserisciregistrazionetrasferimentoclinicavam(" +
						"cast('"+animale.getIdentificativo()+"' as text), " +
						"cast('"+dataAccettazioneTrasferimento+"' as timestamp), " +
						""+idClinicaOrigine+ "," +
						""+idClinicaDestinazione+ "," +
						"cast('"+utente.getUsername()+"' as text), " +
						""+idAslClinicaDestinazione+ ")").list();
					
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
			logger.error("Errore nella registrazione in Bdr del passaaporto del cane.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Canina fallito: " + e.getMessage());
		}
	}
	
	
	public static void comunicaRiconsegnaClinicaExtraAsl(
			Animale animale, String dataAccettazioneTrasferimento, int idClinicaOrigine, int idClinicaDestinazione, int idAslClinicaDestinazione,
			BUtente utente , HttpServletRequest req) throws UnsupportedEncodingException, Exception, ModificaBdrException
	{
		String username = utente.getUsername();
		
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
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
			persistence = PersistenceFactory.getPersistenceCanina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		try
		{
			List<Integer> result = null;
			
			
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				st = connection.createStatement();
				rs = st.executeQuery("select * from public_functions.inserisciregistrazionericonsegnaclinicavam(" +
						"cast('"+animale.getIdentificativo()+"' as text), " +
						"cast('"+dataAccettazioneTrasferimento+"' as timestamp), " +
						""+idClinicaOrigine+ "," +
						""+idClinicaDestinazione+ "," +
						"cast('"+utente.getUsername()+"' as text), " +
						""+idAslClinicaDestinazione+ ")");
				
				if(rs.next() && rs.getInt(1)>0)
				{
					result = new ArrayList<Integer>();
					result.add(rs.getInt(1));
				}
			}
			else
				result = persistence.createSQLQuery("select * from public_functions.inserisciregistrazionericonsegnaclinicavam(" +
						"cast('"+animale.getIdentificativo()+"' as text), " +
						"cast('"+dataAccettazioneTrasferimento+"' as timestamp), " +
						""+idClinicaOrigine+ "," +
						""+idClinicaDestinazione+ "," +
						"cast('"+utente.getUsername()+"' as text), " +
						""+idAslClinicaDestinazione+ ")").list();
					
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
			logger.error("Errore nella registrazione in Bdr della riconsegna del cane.Descrizione errore:" + e.toString());
			throw new ModificaBdrException("Aggiornamento in Canina fallito: " + e.getMessage());
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
	
	
	
	public static Cane findCane(String identificativo, BUtente utente, ServicesStatus status, Connection connection, HttpServletRequest req)
		    throws ClassNotFoundException, SQLException, NamingException
		  {
		    long memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		    Date dataStart = new Date();
		    
		    Persistence persistence = null;
		    Class.forName("org.postgresql.Driver");
		    
		    Statement st = null;
		    ResultSet rs = null;
		    ArrayList result = null;
		    
		    long memFreeEnd = Runtime.getRuntime().freeMemory() / 1048576L;
		    Date dataEnd = new Date();
		    long timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000L;
		    System.out.println("Modulo in esecuzione: CaninaRemoteUtil.findCane - " + (timeExecution >= 5L ? "(ATTENZIONE)" : "") + "Execution time:" + timeExecution + "s - " + 
		      "Memoria usata: " + (memFreeStart - memFreeEnd) + 
		      ", utente: " + (utente == null ? "non loggato" : utente.getUsername()) + 
		      ", mc: " + identificativo);
		    memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		    dataStart = new Date();
		    if (!Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		    {
		      persistence = PersistenceFactory.getPersistenceCanina();
		      aggiornaConnessioneApertaSessione(req);
		    }
		    Cane ret = null;
		    List<Cane> cani = null;
		    if (Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		    {
		      DataCaching data = new DataCaching(connection);
		      
		      memFreeEnd = Runtime.getRuntime().freeMemory() / 1048576L;
		      dataEnd = new Date();
		      timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000L;
		      System.out.println("Modulo in esecuzione: CaninaRemoteUtil.findCane(0) - " + (timeExecution >= 5L ? "(ATTENZIONE)" : "") + "Execution time:" + timeExecution + "s - " + 
		        "Memoria usata: " + (memFreeStart - memFreeEnd) + 
		        ", utente: " + (utente == null ? "non loggato" : utente.getUsername()) + 
		        ", mc: " + identificativo);
		      memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		      dataStart = new Date();
		      ArrayList parameters = new ArrayList();
		      try
		      {
		        QueryResult qr = data.execute("select * from public_functions.getdaticane('" + identificativo + "')", parameters);
		        
		        memFreeEnd = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataEnd = new Date();
		        timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000L;
		        System.out.println("Modulo in esecuzione: CaninaRemoteUtil.findCane(1) - " + (timeExecution >= 5L ? "(ATTENZIONE)" : "") + "Execution time:" + timeExecution + "s - " + 
		          "Memoria usata: " + (memFreeStart - memFreeEnd) + 
		          ", utente: " + (utente == null ? "non loggato" : utente.getUsername()) + 
		          ", mc: " + identificativo);
		        memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataStart = new Date();
		        
		        result = (ArrayList)qr.getRows();
		        
		        memFreeEnd = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataEnd = new Date();
		        timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000L;
		        System.out.println("Modulo in esecuzione: CaninaRemoteUtil.findCane(2) - " + (timeExecution >= 5L ? "(ATTENZIONE)" : "") + "Execution time:" + timeExecution + "s - " + 
		          "Memoria usata: " + (memFreeStart - memFreeEnd) + 
		          ", utente: " + (utente == null ? "non loggato" : utente.getUsername()) + 
		          ", mc: " + identificativo);
		        memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataStart = new Date();
		        
		        Iterator<String> it = result.iterator();
		        
		        memFreeEnd = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataEnd = new Date();
		        timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000L;
		        System.out.println("Modulo in esecuzione: CaninaRemoteUtil.findCane(3) - " + (timeExecution >= 5L ? "(ATTENZIONE)" : "") + "Execution time:" + timeExecution + "s - " + 
		          "Memoria usata: " + (memFreeStart - memFreeEnd) + 
		          ", utente: " + (utente == null ? "non loggato" : utente.getUsername()) + 
		          ", mc: " + identificativo);
		        memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataStart = new Date();
		      }
		      catch (IOException e)
		      {
		        e.printStackTrace();
		      }
		      catch (IllegalAccessException e)
		      {
		        e.printStackTrace();
		      }
		      catch (InstantiationException e)
		      {
		        e.printStackTrace();
		      }
		    }
		    else
		    {
		      cani = persistence.createSQLQuery("select * from public_functions.getdaticane('" + identificativo + "')", Cane.class).list();
		    }
		    if (Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		    {
		      HashMap<String, Object> cane = (HashMap)result.iterator().next();
		      
		      memFreeEnd = Runtime.getRuntime().freeMemory() / 1048576L;
		      dataEnd = new Date();
		      timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000L;
		      System.out.println("Modulo in esecuzione: CaninaRemoteUtil.findCane(4) - " + (timeExecution >= 5L ? "(ATTENZIONE)" : "") + "Execution time:" + timeExecution + "s - " + 
		        "Memoria usata: " + (memFreeStart - memFreeEnd) + 
		        ", utente: " + (utente == null ? "non loggato" : utente.getUsername()) + 
		        ", mc: " + identificativo);
		      memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		      dataStart = new Date();
		      if ((cane.get("id") != null) && (((Integer)cane.get("id")).intValue() > 0))
		      {
		        ret = new Cane();
		        ret.setDataDecesso((Date)cane.get("data_decesso"));
		        ret.setDataNascita((Date)cane.get("data_nascita"));
		        ret.setDecessoValue((String)cane.get("decesso_value"));
		        ret.setDescrizioneMantello((String)cane.get("descrizione_mantello"));
		        ret.setDescrizioneRazza((String)cane.get("descrizione_razza"));
		        ret.setDescrizioneTaglia((String)cane.get("descrizione_taglia"));
		        ret.setId(((Integer)cane.get("id")).intValue());
		        ret.setIdTaglia((Integer)cane.get("id_taglia"));
		        if (cane.get("id_tipo_decesso") != null) {
		          ret.setIdTipoDecesso((Integer)cane.get("id_tipo_decesso"));
		        }
		        ret.setDataRegistrazione((Date)cane.get("data_registrazione"));
		        ret.setDataChippatura((Date)cane.get("data_chippatura"));
		        ret.setIdProprietario((Integer)cane.get("id_proprietario"));
		        ret.setIdDetentore((Integer)cane.get("id_detentore"));
		        
		        memFreeEnd = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataEnd = new Date();
		        timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000L;
		        System.out.println("Modulo in esecuzione: CaninaRemoteUtil.findCane(5) - " + (timeExecution >= 5L ? "(ATTENZIONE)" : "") + "Execution time:" + timeExecution + "s - " + 
		          "Memoria usata: " + (memFreeStart - memFreeEnd) + 
		          ", utente: " + (utente == null ? "non loggato" : utente.getUsername()) + 
		          ", mc: " + identificativo);
		        memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataStart = new Date();
		        
		        ret.setDataNascitaPresunta((Boolean)cane.get("is_data_nascita_presunta"));
		        ret.setDataDecessoPresunta((Boolean)cane.get("is_data_decesso_presunta"));
		        ret.setMantello((Integer)cane.get("mantello"));
		        ret.setMc((String)((cane.get("mc") != null) && (!"".equals(cane.get("mc"))) ? cane.get("mc") : cane.get("tatuaggio")));
		        ret.setNoteRitrovamento((String)cane.get("note_ritrovamento"));
		        ret.setRazza((Integer)cane.get("razza"));
		        ret.setSesso((String)cane.get("sesso"));
		        ret.setStatoAttuale((String)cane.get("stato_attuale"));
		        ret.setSterilizzato((Boolean)cane.get("sterilizzato"));
		        ret.setTrashedDate((Date)cane.get("trashed_date"));
		        ret.setDataSterilizzazione((Date)cane.get("data_sterilizzazione"));
		        ret.setOperatoreSterilizzazione((String)cane.get("soggetto_sterilizzante"));
		        ret.setTatuaggio((String)cane.get("tatuaggio"));
		        
		        memFreeEnd = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataEnd = new Date();
		        timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000L;
		        System.out.println("Modulo in esecuzione: CaninaRemoteUtil.findCane(6) - " + (timeExecution >= 5L ? "(ATTENZIONE)" : "") + "Execution time:" + timeExecution + "s - " + 
		          "Memoria usata: " + (memFreeStart - memFreeEnd) + 
		          ", utente: " + (utente == null ? "non loggato" : utente.getUsername()) + 
		          ", mc: " + identificativo);
		        memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		        dataStart = new Date();
		      }
		    }
		    else if ((cani.size() > 0) && (cani.get(0) != null))
		    {
		      ret = (Cane)cani.get(0);
		    }
		    if (!Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		    {
		      PersistenceFactory.closePersistence(persistence, false);
		      aggiornaConnessioneChiusaSessione(req);
		    }
		    memFreeEnd = Runtime.getRuntime().freeMemory() / 1048576L;
		    dataEnd = new Date();
		    timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000L;
		    System.out.println("Modulo in esecuzione: CaninaRemoteUtil.findCane - " + (timeExecution >= 5L ? "(ATTENZIONE)" : "") + "Execution time:" + timeExecution + "s - " + 
		      "Memoria usata: " + (memFreeStart - memFreeEnd) + 
		      ", utente: " + (utente == null ? "non loggato" : utente.getUsername()) + 
		      ", mc: " + identificativo);
		    memFreeStart = Runtime.getRuntime().freeMemory() / 1048576L;
		    dataStart = new Date();
		    
		    return ret;
		  }
	
}
		  
		  
