/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Colonia;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
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

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

public class FelinaRemoteUtilH
{
	private static GsonBuilder gb;
	private static Gson gson;
	
	final static Logger logger = LoggerFactory.getLogger(FelinaRemoteUtilH.class);
	
	static
	{
		gb = new GsonBuilder();
		gb.setDateFormat( "yyyy-MM-dd HH:mm:ss" );
		gson = gb.create();
	}
	
	public static Gatto findGatto( String identificativo, BUtente utente, ServicesStatus status, Connection connection ) throws ClassNotFoundException, SQLException, NamingException 
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;

		
		if(false)
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
		}
		
		Gatto ret = null;
		List<Gatto> gatti = null;
		if(false)
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
			gatti = persistence.createSQLQuery("select '' as note_ritrovamento, * from public_functions.getdatigatto('"+identificativo+"')", Gatto.class).list();
		
		if(false)
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
					ret.setIdTipoDecesso((Integer) gatto.get("id_tipo_decesso"));
					ret.setIsDataDecessoPresunta((Boolean) gatto.get("is_data_decesso_presunta"));
					ret.setIsDataNascitaPresunta((Boolean) gatto.get("is_data_nascita_presunta"));
					ret.setMantello((Integer) gatto.get("mantello"));
					ret.setMc((String) gatto.get("mc"));
					ret.setNoteRitrovamento((String) gatto.get("note_ritrovamento"));
					ret.setRazza((Integer) gatto.get("razza"));
					ret.setSesso((String) gatto.get("sesso"));
					ret.setStatoAttuale((String) gatto.get("stato_attuale"));
					ret.setSterilizzato((Boolean) gatto.get("sterilizzato"));
					ret.setTrashedDate((Date) gatto.get("trashed_date"));
					ret.setDataSterilizzazione((Date) gatto.get("data_sterilizzazione"));
					ret.setOperatoreSterilizzazione((String) gatto.get("soggetto_sterilizzante"));
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
		
		if(false)
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
		}
		
		
		
		return ret;
	}

	public static ProprietarioGatto findProprietario( String identificativo, BUtente utente , Connection connection) throws Exception
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		if(false)
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
		}
		
		List<ProprietarioGatto> proprietari = null;
		ProprietarioGatto ret = null;
		if(false)
		{
			DataCaching data = new DataCaching(connection);
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				QueryResult qr = data.execute("select * from public_functions.getdatiproprietarioanimale('"+identificativo+"')", parameters);
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
			
			HashMap<String,Object> proprietario = (HashMap<String, Object>) result.iterator().next();
			if (proprietario.get("id")!=null && (Integer) proprietario.get("id")>0){
				ret = new ProprietarioGatto();
				ret.setAsl((Integer) proprietario.get("id_asl"));
				ret.setCap((String) proprietario.get("cap"));
				ret.setCitta((String) proprietario.get("citta"));
				ret.setCodiceFiscale((String) proprietario.get("codicefiscale"));
				ret.setCognome((String) proprietario.get("cognome"));
				ret.setDocumentoIdentita((String) proprietario.get("documentoidentita"));
				ret.setId((Integer) proprietario.get("id"));
				ret.setNazione((String) proprietario.get("nazione"));
				ret.setNome((String) proprietario.get("nome"));
				ret.setNumeroTelefono((String) proprietario.get("numerotelefono"));
				ret.setTipo((String) proprietario.get("tipo"));
				
				Integer provincia = null;
				try
				{
					String provinciaString = ((String)proprietario.get("provincia"));
					if(provinciaString!=null && !provinciaString.equals(""))
						provinciaString=provinciaString.replaceAll(" ", "");
					provincia = Integer.parseInt(provinciaString);
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
					LookupProvince provinciaLookup = (LookupProvince)persistence2.find(LookupProvince.class, provincia);
					ret.setProvincia(provinciaLookup.getDescription());
					PersistenceFactory.closePersistence( persistence2, false );
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
		
		if(false)
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
		}
		
		return ret;
	}

	public static Colonia findColonia( String identificativo, BUtente utente, Connection connection ) throws ClassNotFoundException, SQLException, NamingException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		if(false)
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
		}
		
		List<Colonia> colonie = null;
		Colonia ret = null;
		if(false)
		{
			
			DataCaching data = new DataCaching(connection);
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				QueryResult qr = data.execute("select * from public_functions.getdaticolonia('"+identificativo+"')", parameters);
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
				ret.setNomeReferente((String) colonia.get("nome_referente"));
				ret.setNumeroGatti((Integer) colonia.get("numero_gatti"));
				ret.setNumeroProtocollo((String) colonia.get("numero_protocollo"));
				ret.setTelefonoReferente((String) colonia.get("numerotelefono"));
				ret.setProvinciaColonia((String) colonia.get("provincia_colonia"));
				ret.setTelefonoReferente((String) colonia.get("numerotelefono_referente"));
				ret.setTrashedDate((Date) colonia.get("trasheddate"));
				ret.setVeterinario((String) colonia.get("nome_veterinario"));
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
		
		if(false)
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
		}
		
		return ret;
	}

	public static RegistrazioniFelina findRegistrazioniEffettuabili( String identificativo, BUtente utente, Connection connection ) throws ClassNotFoundException, SQLException, NamingException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		if(false)
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/felina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceFelina();
		}
		
		RegistrazioniFelina ret = null;
		List<RegistrazioniFelina> reList = null;
		if(false)
		{
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getlistaregistrazionigatto('"+identificativo+"')");
			if(rs.next())
			{
				ret = new RegistrazioniFelina();
				ret.setAdozione(rs.getBoolean("adozione"));
				ret.setDecesso(rs.getBoolean("decesso"));
				ret.setFurto(rs.getBoolean("furto"));
				ret.setRicattura(rs.getBoolean("ricattura"));
				ret.setRitrovamento(rs.getBoolean("ritrovamento"));
				//ret.setRitrovamentoSmarrNonDenunciato(rs.getBoolean("ritrovamento_smarr_non_denunciato"));
				ret.setSmarrimento(rs.getBoolean("smarrimento"));
				ret.setSterilizzazione(rs.getBoolean("sterilizzazione"));
				ret.setTrasferimento(rs.getBoolean("trasferimento"));
				ret.setReimmissione(rs.getBoolean("reimmissione"));
			}
		}
		else
			reList = persistence.createSQLQuery("select * from public_functions.getlistaregistrazioni('"+identificativo+"')", RegistrazioniFelina.class).list();
	
		if(!false)
		{
			if( reList.size() > 0 && reList.get(0)!=null )
			{
				ret = reList.get( 0 );
			}
		}
		
		if(false)
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
		}
		
		return ret;
	}
	
	

}