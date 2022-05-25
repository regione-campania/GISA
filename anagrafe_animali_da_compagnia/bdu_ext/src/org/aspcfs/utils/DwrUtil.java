/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactEmailAddress;
import org.aspcfs.modules.contacts.base.ContactEmailAddressList;
import org.aspcfs.modules.contacts.base.ContactPhoneNumber;
import org.aspcfs.modules.contacts.base.ContactPhoneNumberList;
import org.aspcfs.modules.login.actions.Login;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.base.Mail;
import org.aspcfs.modules.opu_ext.base.Circoscrizione;
import org.aspcfs.modules.opu.base.Municipalita;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.LoginRequiredException;



public class DwrUtil {
	
	public static EsitoControllo verificaInserimentoAnimale(String mc,int userId) throws SQLException, UnknownHostException
	{
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		EsitoControllo esito = new EsitoControllo();
		try
		{

			
//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties.getProperty("usernameDbbdu");
//			String pwd =ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress(); 
//			
//
//			db = DbUtil.getConnection(dbName, username,
//					pwd, host);
			Thread t = Thread.currentThread();
			db = GestoreConnessioni.getConnection();
			
			cs = db.prepareCall("select * from verifica_banca_dati_a_priori (?,?,false)");
			cs.setString(1, mc);
			cs.setInt(2,userId);
			 rs = cs.executeQuery();
			if (rs.next())
			{
				esito.setIdEsito(rs.getInt(1));
				esito.setDescrizione(rs.getString(2));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			esito.setIdEsito(-1);
			esito.setDescrizione("Errore nel recupero MC a priori, se il problema persiste contattare l'Help desk fornendo il microchip che si intende utilizzare.");
		}
		finally
		{
			//DbUtil.chiudiConnessioneJDBC(rs, cs, db);
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public static boolean verificaSterilizzazioneInPratica(String data_sterilizzazione,int id_pratica) throws SQLException, ParseException, UnknownHostException
	{
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		//data_sterilizzazione = data_sterilizzazione.replaceAll("/", "-")+" 00:00:00.000";
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsedDate = dateFormat.parse(data_sterilizzazione);
		java.sql.Timestamp ts_sterilizzazione = new java.sql.Timestamp(parsedDate.getTime());
		
		
		try
		{

			
//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties.getProperty("usernameDbbdu");
//			String pwd =ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//			
//
//			db = DbUtil.getConnection(dbName, username,
//					pwd, host);
			Thread t = Thread.currentThread();
			db = GestoreConnessioni.getConnection();
		
			Pratica pratica = new Pratica (db, id_pratica);
			
			
			if (ts_sterilizzazione.after(pratica.getDataInizioSterilizzazione()) && ts_sterilizzazione.before(pratica.getDataFineSterilizzazione()))
				return true;
			else
				return false;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return true;
	}
	
	public static void Logout(){
		Login loginAction = new Login();
		
		WebContext ctx = WebContextFactory.get();
		loginAction.executeCommandLogout(ctx);
		
	}
	
public static boolean aggiornaDatiLLP(String email, String telefono, int userId) {
		
		Connection db = null;
		boolean ok = true;
		try{
//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties.getProperty("usernameDbbdu");
//			String pwd =ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//			
//			db = DbUtil.getConnection(dbName, username,
//					pwd, host);
			
			Thread t = Thread.currentThread();
			db = GestoreConnessioni.getConnection();
			
			
		User user = new User();
		
		user.setBuildContact(true);
		user.buildRecord(db, userId);
		Contact contactToUpdate = user.getContact();
		
		ContactPhoneNumberList listNumeriContatto = contactToUpdate.getPhoneNumberList();
		ContactPhoneNumber phone = new ContactPhoneNumber();
		phone.setEnteredBy(userId);
		phone.setNumber(telefono);
		phone.setPrimaryNumber(true);
		phone.setType(1);
		
		phone.setContactId(contactToUpdate.getId());
		listNumeriContatto.add(phone);
		
		ContactEmailAddressList listEmailContatto = contactToUpdate.getEmailAddressList();
		ContactEmailAddress emailA = new ContactEmailAddress();
		emailA.setEnteredBy(userId);
		emailA.setEmail(email);
		emailA.setPrimaryEmail(true);
		emailA.setType(1);
		emailA.setContactId(contactToUpdate.getId());
		listEmailContatto.add(emailA);
		
		System.out.println("STO PER AGGIORNARE DATI");
		
		contactToUpdate.updateDatiContatto(db);
		
		System.out.println("DATI AGGIORNATI");
		
		user.setContact(contactToUpdate);
		
		WebContext ctx = WebContextFactory.get();
		UserBean uBean = (UserBean) ctx.getHttpServletRequest().getSession().getAttribute("User");
		uBean.setUserRecord(user);
		ctx.getHttpServletRequest().getSession().setAttribute("User", uBean);
		
		//INVIO EMAIL DI NOTIFICA A HD
		HttpServletRequest req = ctx.getHttpServletRequest();
		ApplicationPrefs prefs = (ApplicationPrefs) req.getSession().getServletContext().getAttribute("applicationPrefs");
		Mail mail = new Mail();
		mail.setHost(prefs.get("MAILSERVER"));
		mail.setFrom(prefs.get("EMAILADDRESS"));
		mail.setUser(prefs.get("EMAILADDRESS"));
		mail.setPass(prefs.get("MAILPASSWORD"));
		mail.setPort(new Integer(prefs.get("PORTSERVER")));
		mail.setRispondiA(ApplicationProperties.getProperty("HD_LEVEL_1_EMAIL_ADDRESS"));
//		if (replyAddr != null && !(replyAddr.equals(""))) {
//			mail.addReplyTo(replyAddr);
//		}
	//	mail.setType("text/html");
		mail.setTo(ApplicationProperties.getProperty("HD_LEVEL_1_EMAIL_ADDRESS"));
		mail.setSogg("[BDU -- Aggiornamento dati LLP " + contactToUpdate.getNameFirst() + " " + contactToUpdate.getNameLast());
//		mail.setSubject(soggetto + "   "
//				+ thisNote.getSubject());

		String testo = "L'utente LLPP " + contactToUpdate.getNameFirst() + " " + contactToUpdate.getNameLast() + " ha aggiornato i suoi dati di contatto</br>" +
		                "</br> Nuovi dati: <br> Email: " + email + "<br> Telefono: " +telefono;
		mail.setTesto(testo);
		if (System.getProperty("DEBUG") != null) {
			System.out.println("Sending Mail .. "
					+ testo);
		}
		
		mail.sendMail();
//		HttpServletRequest req = ctx.getHttpServletRequest();
//		ConnectionElement ce = null;
//		SystemStatus systemStatus = null;
//		ApplicationPrefs prefs = (ApplicationPrefs) req.getSession().getServletContext().getAttribute("applicationPrefs");
//		String ceHost = prefs.get("GATEKEEPER.URL");
//		String ceUser = prefs.get("GATEKEEPER.USER");
//		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
//		
//		ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
//		
//		Object o = ((Hashtable) req.getSession().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
//		
//		if(o != null){
//			systemStatus = (SystemStatus) o;
//			 
//			systemStatus.updateHierarchy(db);
//		}
		
		
		}catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}finally{
			GestoreConnessioni.freeConnection(db);
		}
		
		return ok;
	}
	
	public static EsitoControllo verificaInserimentoAnimaleByMC(String mc) throws SQLException, UnknownHostException
	{
		Connection db = null;
		Connection dbImportatori = null;
		EsitoControllo esito = new EsitoControllo();
		try
		{
			db = GestoreConnessioni.getConnectionBdu();
						
			CallableStatement cs = db.prepareCall("select * from  verifica_banca_dati_a_priori_importatori (?);");
			cs.setString(1, mc);
		//	cs.setInt(2,idAsl);
			ResultSet rs = cs.executeQuery();
			if (rs.next())
			{
				esito.setIdEsito(rs.getInt(1));
				esito.setDescrizione(rs.getString(2));
			}
			
			
			if (esito.getIdEsito() == 1){
				dbImportatori = GestoreConnessioni.getConnection();
			//Controllo se ci sono gi� animali nel portale con qsto MC
				cs = dbImportatori.prepareCall("select * from  verifica_esistenza_mc (?);");
				cs.setString(1, mc);
			//	cs.setInt(2,idAsl);
				ResultSet rs1 = cs.executeQuery();
				if (rs1.next())
				{
					esito.setIdEsito(rs1.getInt(1));
					esito.setDescrizione(rs1.getString(2));
				}
				
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(dbImportatori);
			GestoreConnessioni.freeConnectionBdu(db);
			
		}
		return esito;
	}
	
	public static Object [] getValoriComboComuni1AslFuoriRegione (String in_regione,String estero)
	{
		Object [] ret	= new Object [2] ; 

		HashMap<Integer, String> valori =new  HashMap<Integer,String>();
		

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			String select = " select id,nome from comuni1 c,lookup_asl_rif asl, lookup_province p "+ 
			                " where c.trashed <> true and c.codiceistatasl=asl.codiceistat and p.code = c.cod_provincia::integer and asl.enabled " ;
			
			if (estero!=null && !estero.equals("") && !estero.equals("null") && estero.equals("no"))
				select += " and p.cod_nazione is not null ";
				
			if (in_regione.equalsIgnoreCase("si"))
				select += " and asl.code != 14 ";
			else
				select += " and asl.code = 14  ";
			
			select += " order by nome ";
				
			pst = db.prepareStatement(select);
			rs=pst.executeQuery();
			int i = 1;

			while ( rs.next() )
			{
				String 	value	= rs.getString("nome")	;
				int id = rs.getInt("id");
				valori.put(id, value);

			}
			Object [] ind	= new Object [valori.size()+1];
			Object [] val	= new Object [valori.size()+1];

			ind [0]	= ""				;
			val [0]	= "                "	;

			for (Integer kiave : valori.keySet() )
			{
				ind [i]	= kiave				;
				val [i]	= valori.get(kiave)	;
				i++;
			}
			ret[0]	= 	ind		;
			ret[1]	=	val	;	

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		finally
		{
			GestoreConnessioni.freeConnection( db);
		}

		return ret;

	}	
	
	
	
	
	
	
	
	public static Object [] getValoriProvincia (boolean in_regione)
	{
		Object [] ret	= new Object [2] ; 

		LinkedHashMap<Integer, String> valori =new  LinkedHashMap<Integer,String>();
		

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			String select="";
			if (in_regione)
			  select = " select code as id,description as nome " +
					   " from lookup_province p " +
					   " where p.enabled is true and  " +
					   "       p.id_regione = 15  " +
					   " order by nome ";
			else
				  select = " select code as id,description as nome " +
						   " from lookup_province p " +
						   " where p.enabled is true and  " +
						   "       p.id_regione <> 15  " +
						   " order by nome ";
				
			pst = db.prepareStatement(select);
			rs=pst.executeQuery();
			int i = 1;

			while ( rs.next() )
			{
				String 	value	= rs.getString("nome")	;
				int id = rs.getInt("id");
				valori.put(id, value);

			}
			Object [] ind	= new Object [valori.size()+1];
			Object [] val	= new Object [valori.size()+1];

			ind [0]	= ""				;
			val [0]	= "                "	;

			for (Integer kiave : valori.keySet() )
			{
				ind [i]	= kiave				;
				val [i]	= valori.get(kiave)	;
				i++;
			}
			ret[0]	= 	ind		;
			ret[1]	=	val	;	

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		finally
		{
			GestoreConnessioni.freeConnection( db);
		}

		return ret;

	}

	
	
	public static Object [] getValoriComboComuni1Asl (int idAsl)
	{

		Object [] ret	= new Object [2] ; 

		HashMap<Integer, String> valori =new  HashMap<Integer,String>();
		

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;

			String select 	= 	"select id,nome from comuni1 c,lookup_asl_rif asl where c.trashed <> true and c.codiceistatasl=asl.codiceistat and asl.enabled=true "	;

			if(idAsl!= -1 && idAsl!= -2)
			{
				select+= " and asl.code = ? order by nome ";
			}
			else
			{
				select += " order by nome ";
			}

			pst = db.prepareStatement(select);
			if(idAsl!= -1 && idAsl!= -2)
			{
				pst.setInt(1, idAsl);
			}
			rs = pst.executeQuery();
			int i = 1;

			while ( rs.next() )
			{
				String 	value	= rs.getString("nome")	;
				int id = rs.getInt("id");
				valori.put(id, value);

			}
			Object [] ind	= new Object [valori.size()+1];
			Object [] val	= new Object [valori.size()+1];

			ind [0]	= ""				;
			val [0]	= "                "	;

			for (Integer kiave : valori.keySet() )
			{
				ind [i]	= kiave				;
				val [i]	= valori.get(kiave)	;
				i++;
			}
			ret[0]	= 	ind		;
			ret[1]	=	val	;	

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}catch(LoginRequiredException e)
		{
			throw e;
		}
		finally
		{
			GestoreConnessioni.freeConnection( db);
		}

		return ret;

	}	
	
	public static Object [] getValoriAsl(int idcomune) throws UnknownHostException
	{
		Object [] ret	= new Object [2] ; 
		HashMap<Integer, String> valori =new  HashMap<Integer,String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			Thread t = Thread.currentThread();
			db = GestoreConnessioni.getConnection();
			
//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties
//					.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			db = DbUtil.getConnection(dbName, username, pwd, host);

			String select 	= 	"select code, description from lookup_asl_rif where enabled = true "	;

			if(idcomune>0)
				select+= " and codiceistat IN (select codiceistatasl from comuni1 where id = ? and trashed is false ) ";
			
			
			pst = db.prepareStatement(select);
			if(idcomune>0)
				pst.setInt(1, idcomune);
			rs = pst.executeQuery();
			int i = 0;

			while ( rs.next() )
			{
				int 	code 	= rs.getInt("code")				;
				String 	value	= rs.getString("description")	;
				valori.put(code, value);

			}
			Object [] ind	= new Object [valori.size()];
			Object [] val	= new Object [valori.size()];



			for (Integer kiave : valori.keySet() )
			{
				ind [i]	= kiave				;
				val [i]	= valori.get(kiave)	;
				i++;
			}
			ret[0]	= 	ind		;
			ret[1]	=	val	;	

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			//DbUtil.chiudiConnessioneJDBC(rs, pst, db);
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}
	
	
	public static boolean checkContributo(String mc) throws SQLException, UnknownHostException
	{
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		boolean esito=false;
		//EsitoControllo esito = new EsitoControllo();
		try
		{

//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties.getProperty("usernameDbbdu");
//			String pwd =ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//			
//			db = DbUtil.getConnection(dbName, username,
//					pwd, host);
			Thread t = Thread.currentThread();
			db = GestoreConnessioni.getConnection();
			
			int rit = -1;
			PreparedStatement pst = null;
			pst = db
					.prepareStatement("select id from contributi_lista_univocita where microchip = ?");
			pst.setString(1, mc.trim());

			rs = pst.executeQuery();

			if (rs.next()) {
				rit = rs.getInt("id");
			}
			rs.close();
			pst.close();

			if (rit == -1) {

				esito= false;
			} else {

				esito= true;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			//DbUtil.chiudiConnessioneJDBC(rs, cs, db);
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public static ArrayList getListaCircoscrizioni(int idComune) throws SQLException
	{
		Connection db = null;
		ArrayList<Circoscrizione> toReturn = new ArrayList<Circoscrizione>();
		try
		{
			db = GestoreConnessioni.getConnection();
			CallableStatement cs = db.prepareCall("select * from circoscrizioni where id_comune = ?");
			cs.setInt(1,idComune);
			ResultSet rs = cs.executeQuery();
			while (rs.next())
			{
				Circoscrizione circ = new Circoscrizione();
				circ.setId(rs.getInt("id"));
				circ.setNomeCircoscrizione(rs.getString("nome_circoscrizione"));
				toReturn.add(circ);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return toReturn;
	}
	
	
	/**
	 * Registrazione di cattura
	 * @param idComune
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList getListaMunicipalita(int idComune) throws SQLException
	{
		Connection db = null;
		ArrayList<Municipalita> toReturn = new ArrayList<Municipalita>();
		try
		{
			db = GestoreConnessioni.getConnection();
			CallableStatement cs = db.prepareCall("select * from municipalita where id_comune = ?");
			cs.setInt(1,idComune);
			ResultSet rs = cs.executeQuery();
			while (rs.next())
			{
				Municipalita circ = new Municipalita();
				circ.setId(rs.getInt("id"));
				circ.setNomeMunicipalita(rs.getString("nome_municipalita"));
				toReturn.add(circ);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return toReturn;
	}
	

	
	}
	

	
	
	


