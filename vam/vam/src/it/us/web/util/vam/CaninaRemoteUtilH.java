/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniCanina;
import it.us.web.bean.test.Utente;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleAnagrafica;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupProvince;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdOperazioniInBdr;
import it.us.web.constants.IdTipiTrasferimentoAccettazione;
import it.us.web.constants.Specie;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.ModificaBdrException;
import it.us.web.util.properties.Application;
import it.us.web.util.vam.DataCaching;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CaninaRemoteUtilH
{
	private static GsonBuilder gb;
	private static Gson gson;
	
	final static Logger logger = LoggerFactory.getLogger(CaninaRemoteUtilH.class);
	
	static
	{
		gb = new GsonBuilder();
		gb.setDateFormat( "yyyy-MM-dd HH:mm:ss" );
		gson = gb.create();
	}
	
	public static Cane findCane( String identificativo, BUtente utente, ServicesStatus status, Connection connection ) throws ClassNotFoundException, SQLException, NamingException 
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
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceCanina();
		}
		
		Cane ret = null;
		List<Cane> cani = null;
		if(false)
		{
//			st = connection.createStatement();
//			rs = st.executeQuery("select * from public_functions.getdaticane('"+identificativo+"')");
			DataCaching data = new DataCaching(connection);
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				QueryResult qr = data.execute("select * from public_functions.getdaticane('"+identificativo+"')", parameters);
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
			cani = persistence.createSQLQuery("select false as ricatturato, '' as soggetto_sterilizzazione,null as note_ritrovamento, 0 as importo_sanzione_smarrimento, false as circuito_commerciale, * from public_functions.getdaticane('"+identificativo+"')", Cane.class).list();
		
		if(false)
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
				ret.setIdTipoDecesso((Integer) cane.get("id_tipo_decesso"));
				ret.setDataNascitaPresunta((Boolean) cane.get("is_data_nascita_presunta"));
				ret.setDataDecessoPresunta((Boolean) cane.get("is_data_decesso_presunta"));
				ret.setMantello((Integer) cane.get("mantello"));
				ret.setMc((String) cane.get("mc"));
				ret.setNoteRitrovamento((String) cane.get("note_ritrovamento"));
				ret.setRazza((Integer) cane.get("razza"));
				ret.setSesso((String) cane.get("sesso"));
				ret.setStatoAttuale((String) cane.get("stato_attuale"));
				ret.setSterilizzato((Boolean) cane.get("sterilizzato"));
				ret.setTrashedDate((Date) cane.get("trashed_date"));
				ret.setDataSterilizzazione((Date) cane.get("data_sterilizzazione"));
				ret.setOperatoreSterilizzazione((String) cane.get("soggetto_sterilizzante"));
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

	public static ProprietarioCane findProprietario( String identificativo, BUtente utente, Connection connection ) throws Exception
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
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceCanina();
		}
		
		List<ProprietarioCane> proprietari = null;
		ProprietarioCane ret = null;
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
				ret = new ProprietarioCane();
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

	public static RegistrazioniCanina findRegistrazioniEffettuabili( String identificativo, BUtente utente, Connection connection ) throws ClassNotFoundException, SQLException, NamingException
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		if(false)
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceCanina();
		}
		
		RegistrazioniCanina ret = null;
		List<RegistrazioniCanina> reList = null;
		if(false)
		{
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getlistaregistrazionicane('"+identificativo+"')");
			if(rs.next())
			{
				ret = new RegistrazioniCanina();
				ret.setAdozione(rs.getBoolean("adozione"));
				ret.setDecesso(rs.getBoolean("decesso"));
				ret.setFurto(rs.getBoolean("furto"));
				ret.setRicattura(rs.getBoolean("ricattura"));
				ret.setRitrovamento(rs.getBoolean("ritrovamento"));
				ret.setRitrovamentoSmarrNonDenunciato(rs.getBoolean("ritrovamentosmarrnondenunciato"));
				ret.setSmarrimento(rs.getBoolean("smarrimento"));
				ret.setTrasferimento(rs.getBoolean("trasferimento"));
				ret.setSterilizzazione(rs.getBoolean("sterilizzazione"));
				ret.setPrelievoDna(rs.getBoolean("prelievodna"));
				ret.setPrelievoLeishmania(rs.getBoolean("prelievoLeishmania"));
				ret.setReimmissione(rs.getBoolean("reimmissione"));
			}
		}
		else
			reList = persistence.createSQLQuery("select 0 as idCane, * from public_functions.getlistaregistrazionicane('"+identificativo+"')", RegistrazioniCanina.class).list();
	
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