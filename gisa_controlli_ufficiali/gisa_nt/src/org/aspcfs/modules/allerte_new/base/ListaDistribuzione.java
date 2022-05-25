/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte_new.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.beans.GenericBean;

public class ListaDistribuzione extends GenericBean
{
	
	
	private static final long serialVersionUID = 7620936339377012629L;
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id = -1;
	private int id_allerta = -1;
	private Timestamp data_lista ;
	private Timestamp data_chiusura ;
	private String nome_fornitore ;
	
	private int id_utente_inserimento;
	private int id_utente_modifica;
	private Timestamp data_inserimento ;
	private Timestamp data_modifica ;
	
	private boolean chiusura_forzata = false;
	
	private Hashtable<String, AslCoinvolte> asl_coinvolte;
	private Hashtable<String, ImpreseCoinvolte> imprese_coinvolte;

	

	public ListaDistribuzione() {
		// TODO Auto-generated constructor stub
	}

	public ListaDistribuzione(Connection db, int idListaDistribuzione) {
		// TODO Auto-generated constructor stub
		String select = "select * from allerte_ldd where id = ? ";
		PreparedStatement stat;
		try {
			stat = db.prepareStatement(select);
			stat.setInt(1, idListaDistribuzione);
			ResultSet rs = stat.executeQuery();
			if (rs.next()){
				buildRecord(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buildRecord(ResultSet rs) throws SQLException{
		this.id = rs.getInt("id");
		this.data_lista = rs.getTimestamp("data_lista");
		this.data_chiusura = rs.getTimestamp("data_chiusura");
		this.nome_fornitore = rs.getString("nome_fornitore");
		this.id_allerta = rs.getInt("id_allerta");
		this.chiusura_forzata = rs.getBoolean("chiusura_forzata");
	}
	
	public Hashtable<String, ImpreseCoinvolte> getImprese_coinvolte() {
		return imprese_coinvolte;
	}


	public void setImprese_coinvolte(
			Hashtable<String, ImpreseCoinvolte> imprese_coinvolte) {
		this.imprese_coinvolte = imprese_coinvolte;
	}
	private String descrizioneBreve="";



	public Hashtable<String, AslCoinvolte> getAsl_coinvolte()
	{
		return asl_coinvolte;
	}

	public String getAsl_coinvolteAsString()
	{
		String ret = "";

		Enumeration<String> e = asl_coinvolte.keys(); 
		while( e.hasMoreElements() )
		{
			String key = e.nextElement();
			if( ret.length() > 0 )
			{
				ret += ", ";
			}
			ret += key;
		}

		return ret;
	}

	public void setAsl_coinvolte(Hashtable<String, AslCoinvolte> asl_coinvolte)
	{
		
		this.asl_coinvolte = asl_coinvolte;
	}


	public Timestamp getData_lista() {
		return data_lista;
	}
	public void setData_lista(Timestamp dataLista) {
		this.data_lista = dataLista;
	}
	
	public Timestamp getData_chiusura() {
		return data_chiusura;
	}
	public void setData_chiusura(Timestamp dataChiusura) {
		this.data_chiusura = dataChiusura;
	}
	
	public String getNome_fornitore() {
		return nome_fornitore;
	}
	public void setNome_fornitore(String nomeFornitore) {
		this.nome_fornitore = nomeFornitore;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getId_allerta() {
		return id_allerta;
	}
	public void setId_allerta(int idAllerta) {
		this.id_allerta = idAllerta;
	}
	public int getId_utente_inserimento() {
		return id_utente_inserimento;
	}

	public void setId_utente_inserimento(int id_utente_inserimento) {
		this.id_utente_inserimento = id_utente_inserimento;
	}

	public int getId_utente_modifica() {
		return id_utente_modifica;
	}

	public void setId_utente_modifica(int id_utente_modifica) {
		this.id_utente_modifica = id_utente_modifica;
	}

	public Timestamp getData_inserimento() {
		return data_inserimento;
	}

	public void setData_inserimento(Timestamp data_inserimento) {
		this.data_inserimento = data_inserimento;
	}

	public Timestamp getData_modifica() {
		return data_modifica;
	}

	public void setData_modifica(Timestamp data_modifica) {
		this.data_modifica = data_modifica;
	}

	public void store( Connection db )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		this.id = DatabaseUtils.getNextInt(db, "allerte_ldd", "id", 1);
		String sql = "INSERT INTO allerte_ldd(nome_fornitore, data_lista, data_chiusura, id_allerta, id_utente_inserimento, data_inserimento) VALUES (?, ?, ?, ?, ?, now()) ";
		 PreparedStatement stat = db.prepareStatement( sql );
		
	    int i =0;
	    
	    stat.setString(++i, nome_fornitore);
	    stat.setTimestamp(++i, data_lista);
	    stat.setTimestamp(++i, data_chiusura);
	    stat.setInt(++i, id_allerta);
	    stat.setInt(++i, id_utente_inserimento);
	    stat.execute();
	    stat.close();
	    
	    controllaMancanzaLista(db);
	}

	private void controllaMancanzaLista(Connection db){
		String sql3 = "SELECT * from allerte_asl_coinvolte where id_ldd <0 and id_allerta = ? ";
		PreparedStatement stat3;
		try {
			stat3 = db.prepareStatement( sql3 );
			stat3.setInt(1, id_allerta);
			ResultSet rs = stat3.executeQuery();
			if (rs.next()){		
				String sql = "UPDATE allerte_asl_coinvolte set enabled = false where id_ldd <0 and id_allerta = ? ";
				PreparedStatement stat = db.prepareStatement( sql );
				stat.setInt(1, id_allerta);
				stat.executeUpdate();
				String sql2 = "UPDATE ticket set lista_commercializzazione = 1 where ticketid = ? ";
				PreparedStatement stat2 = db.prepareStatement( sql2 );
				stat2.setInt(1, id_allerta);
				stat2.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Hashtable<Integer, ListaDistribuzione> getListeDistribuzione( int id_allerta, String codice_allerta, Connection db )
	{
		Hashtable<Integer, ListaDistribuzione>	ret		= new Hashtable<Integer, ListaDistribuzione>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		try
		{

			stat = db.prepareStatement("SELECT * FROM allerte_ldd WHERE id_allerta = ? and enabled order by id asc");
			stat.setInt( 1, id_allerta );
			res		= stat.executeQuery();
			while( res.next() )
			{
				
				ListaDistribuzione lista =  loadResultSet( res );
				//AslCoinvolte asl = AslCoinvolte.load( res.getInt( "id" ) + "", db,contaCu ) ;
				lista.aggiungiAslCoinvolte(db, codice_allerta, id_allerta);
				ret.put( res.getRow() ,lista );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static Hashtable<Integer, ListaDistribuzione> getListeDistribuzioneSenza( int id_allerta, String codice_allerta, Connection db )
	{
		Hashtable<Integer, ListaDistribuzione>	ret		= new Hashtable<Integer, ListaDistribuzione>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		try
		{
				ListaDistribuzione lista =  new ListaDistribuzione();
				//AslCoinvolte asl = AslCoinvolte.load( res.getInt( "id" ) + "", db,contaCu ) ;
				lista.aggiungiAslCoinvolte(db, codice_allerta, id_allerta);
				ret.put( -1 ,lista );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	private static ListaDistribuzione loadResultSet( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			ListaDistribuzione ret = new ListaDistribuzione();
		
			Field[]	f = ret.getClass().getDeclaredFields();
			Method[]	m = ret.getClass().getMethods();
			for( int i = 0; i < f.length; i++ )
			{
				Method getter	= null;
		    	 Method setter	= null;
		    	 Field	campo	= f[i];
		         String field = f[i].getName();
		        
		         for( int j = 0; j < m.length; j++ )
		         {
		             String met = m[j].getName();
		             if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
		             {
		                  getter = m[j];
		             }
		             else if( met.equalsIgnoreCase( "SET" + field ) )
		             {
		                 setter = m[j];
		             }
		         }
		     
		     
		         if( (getter != null) && (setter != null) && (campo != null) )
		         {
		        	 Object o = null;
		             
		             switch ( parseType( campo.getType() ) )
		             {
		             case INT:
		                 o = res.getInt( field );
		                 break;
		             case STRING:
		                 o = res.getString( field );
		                 break;
		             case BOOLEAN:
		                 o = res.getBoolean( field );
		                 break;
		             case TIMESTAMP:
		                 o = res.getTimestamp( field );
		                 break;
		             case DATE:
		                 o = res.getDate( field );
		                 break;
		             case FLOAT:
		                 o = res.getFloat( field );
		                 break;
		             case DOUBLE:
		                 o = res.getDouble( field );
		                 break;
		             }
		             
		             setter.invoke( ret, o );
		         
		         }
			   }
		
			return ret;
		}
	
	protected static int parseType(Class<?> type)
	{
	    int ret = -1;
	    
	    String name = type.getSimpleName();
	    
	    if( name.equalsIgnoreCase( "int" ) || name.equalsIgnoreCase("integer") )
	    {
	        ret = INT;
	    }
	    else if( name.equalsIgnoreCase( "string" ) )
	    {
	        ret = STRING;
	    }
	    else if( name.equalsIgnoreCase( "double" ) )
	    {
	        ret = DOUBLE;
	    }
	    else if( name.equalsIgnoreCase( "float" ) )
	    {
	        ret = FLOAT;
	    }
	    else if( name.equalsIgnoreCase( "timestamp" ) )
	    {
	        ret = TIMESTAMP;
	    }
	    else if( name.equalsIgnoreCase( "date" ) )
	    {
	        ret = DATE;
	    }
	    else if( name.equalsIgnoreCase( "boolean" ) )
	    {
	        ret = BOOLEAN;
	    }
	    
	    return ret;
	}

	public void aggiungiAslCoinvolte(Connection db, String codice_allerta, int idAllerta) throws SQLException{
		Hashtable<String, AslCoinvolte> asl_coinvolte = AslCoinvolte.getAslConvolteByIdLdd( id,codice_allerta,idAllerta, true, db );
		Iterator<String> itkey = asl_coinvolte.keySet().iterator();
		while(itkey.hasNext())
		{
			String key = itkey.next();
			asl_coinvolte.put(key, asl_coinvolte.get(key));
			
		}
		
		this.setAsl_coinvolte( asl_coinvolte);
		this.setImprese_coinvolte(ImpreseCoinvolte.getImpreseCoinvoteAllAslByIdLdd(db,id, idAllerta));
	}
	
	
	public AslCoinvolte getAslCoinvolta(int user_asl)
	{
		AslCoinvolte ret = null;

		Enumeration<AslCoinvolte> e = asl_coinvolte.elements();
		while( e.hasMoreElements() && (ret == null) )
		{
			AslCoinvolte temp = e.nextElement();
			ret = ( (temp.getId_asl() == user_asl) ? (temp) : (null) );
		}

		return ret;
	}
	
	public ImpreseCoinvolte getImpresaCoinvolta(int user_asl)
	{
		ImpreseCoinvolte ret = null;

		Enumeration<ImpreseCoinvolte> e = imprese_coinvolte.elements();
		while( e.hasMoreElements() && (ret == null) )
		{
			ImpreseCoinvolte temp = e.nextElement();
			ret = ( (temp.getIdAsl() == user_asl) ? (temp) : (null) );
		}

		return ret;
	}
	
	public String getStato( int id_asl )
	{
		String ret = Ticket.ASL_NON_COINVOLTA;

		AslCoinvolte ac = getAslCoinvolta( id_asl );
		if( ac != null )
		{
			ret = parseStato( ac );
		}

		return ret;
	}
	
//	private String parseStato(AslCoinvolte ac)
//	{
//		String ret = "";
//
//		if( ac.getData_chiusura() != null )
//		{
//			ret = Ticket.CHIUSA;
//		}
//		else if( ac.getCu_pianificati() <= 0 )
//		{
//			ret = Ticket.ATTIVA;
//		}
//		else if( ( ac.getCu_pianificati() - ac.getCu_eseguiti() ) > 0 && (ac.getFlag_ripianificazione()==false ) )
//		{
//			ret = Ticket.CONTROLLI_IN_CORSO;
//		}
//		else if( ac.getFlag_ripianificazione()==true && ( ac.getCu_pianificati() - ac.getCu_eseguiti() ) > 0)
//		{
//			ret = Ticket.RIPIANIFICAZIONE;
//		}
//		else
//		{
//			if (ac.getFlag_ripianificazione()==true)
//			{
//				ret = Ticket.CONTROLLI_COMPLETATI_R ;
//			}
//			else ret = Ticket.CONTROLLI_COMPLETATI;
//		}
//		
//		return ret;
//	}
	
	private String parseStato(AslCoinvolte ac)
	{
		String ret = "";

		if( ac.getData_chiusura() != null )
		{
			ret = Ticket.CHIUSA;
		}
//		else if( ac.getCu_pianificati() <= 0 )
//		{
//			ret = Ticket.ATTIVA;
//		}
		else if( ( ac.getCu_pianificati() - ac.getCu_eseguiti() ) > 0 && (ac.getFlag_ripianificazione()==false ) )
		{
			ret = Ticket.CONTROLLI_IN_CORSO;
		}
		else if( ac.getFlag_ripianificazione()==true && ( ac.getCu_pianificati() - ac.getCu_eseguiti() ) > 0)
		{
			ret = Ticket.RIPIANIFICAZIONE;
		}
		else
		{
			if (ac.getFlag_ripianificazione()==true)
			{
				ret = Ticket.CONTROLLI_COMPLETATI_R ;
			}
			else ret = Ticket.CONTROLLI_COMPLETATI;
		}
		
		return ret;
	}

	public boolean canSetCUPianificati( UserBean user )
	{
		boolean ret = false;
		//allerte-allerte-cu

		int user_asl = user.getSiteId();

		AslCoinvolte ac = this.getAslCoinvolta( user_asl );

		ret = ( (ac == null) ? (false) : (ac.getCu_pianificati() < 0) );

		return ret;
	}
	
	
	public void update(ListaDistribuzione listaOld, Connection db){
		boolean uguali  = checkListeUguali(this, listaOld);
		if (!uguali){
			salvaStoricoLista(listaOld, db);
			update(db);
		}
	}
	
	private boolean checkListeUguali(ListaDistribuzione listaNew, ListaDistribuzione listaOld){
		String fornitoreNew = listaNew.getNome_fornitore();
		String fornitoreOld = listaOld.getNome_fornitore();
		Timestamp dataListaNew = listaNew.getData_lista();
		Timestamp dataListaOld = listaOld.getData_lista();
		Timestamp dataChiusuraNew = listaNew.getData_chiusura();
		Timestamp dataChiusuraOld = listaOld.getData_chiusura();
		
		if ((fornitoreNew==null && fornitoreOld != null) || (fornitoreNew!=null && fornitoreOld == null))
			return false;
		if ((dataListaNew==null && dataListaOld != null) || (dataListaNew!=null && dataListaOld == null))
			return false;		
		if ((dataChiusuraNew==null && dataChiusuraOld != null) || (dataChiusuraNew!=null && dataChiusuraOld == null))
			return false;
		if (!fornitoreNew.equals(fornitoreOld))
			return false;
		if ((dataListaNew!=null && dataListaOld!=null) && !dataListaNew.equals(dataListaOld))
			return false;
		if ((dataChiusuraNew!=null && dataChiusuraOld!=null) &&  !dataChiusuraNew.equals(dataChiusuraOld))
			return false;
		return true;
	}
	
	private void salvaStoricoLista(ListaDistribuzione lista, Connection db){
		String sql = "INSERT INTO allerte_ldd_history(nome_fornitore, data_lista, data_chiusura, id_allerta, id_ldd, data_modifica, id_utente_modifica) VALUES (?, ?, ?, ?, ?, now(), ?) ";
		 PreparedStatement stat;
		try {
			stat = db.prepareStatement( sql );
		
		 int i =0;
	    
	    stat.setString(++i, lista.getNome_fornitore());
	    stat.setTimestamp(++i, lista.getData_lista());
	    stat.setTimestamp(++i, lista.getData_chiusura());
	    stat.setInt(++i, lista.getId_allerta());
	    stat.setInt(++i, lista.getId());
	    stat.setInt(++i, lista.getId_utente_modifica());
	    
	    stat.execute();
	    stat.close();} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void update(Connection db){
		String sql = "UPDATE allerte_ldd set nome_fornitore = ?, data_lista = ?, data_chiusura = ?, data_modifica= now(), id_utente_modifica = ? where id = ?";
		 PreparedStatement stat;
		try {
			stat = db.prepareStatement( sql );
		
		 int i =0;
	    
	    stat.setString(++i, this.getNome_fornitore());
	    stat.setTimestamp(++i, this.getData_lista());
	    stat.setTimestamp(++i, this.getData_chiusura());
	    stat.setInt(++i, this.getId_utente_modifica());
	    stat.setInt(++i, this.getId());
	    
	    stat.executeUpdate();
	    stat.close();} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int chiudi(Connection db, String data) throws SQLException, ParseException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date parsedDate;
			Timestamp timestamp; 
			parsedDate = dateFormat.parse(data);
			timestamp = new java.sql.Timestamp(parsedDate.getTime()); 
			String sql =
				"UPDATE allerte_ldd " +
				"SET data_chiusura = ?, data_modifica = " + DatabaseUtils.getCurrentTimestamp(
						db) + ", id_utente_modifica = ?, chiusura_forzata = ? " +
						"WHERE id = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setTimestamp( ++i, timestamp);
			pst.setInt(++i, this.getId_utente_modifica());
			pst.setBoolean(++i, this.isChiusura_forzata());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}

	public int chiudiPerAsl(Connection db, String data, int idAsl) throws SQLException, ParseException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date parsedDate;
			Timestamp timestamp; 
			parsedDate = dateFormat.parse(data);
			timestamp = new java.sql.Timestamp(parsedDate.getTime()); 
			String sql =
				"UPDATE allerte_asl_coinvolte " +
				"SET data_chiusura = ?,  chiusa_da = ? " +
						"WHERE id_ldd = ? and id_asl = ? and data_chiusura is null ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setTimestamp( ++i, timestamp);
			pst.setInt(++i, this.getId_utente_modifica());
			pst.setInt(++i, this.getId());
			pst.setInt(++i, idAsl);
			resultCount = pst.executeUpdate();
			pst.close();
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}

	
	public int getNumCuPianificati()
	{
		AslCoinvolte ret = null;
		int tot = 0;
		Enumeration<AslCoinvolte> e = asl_coinvolte.elements();
		while( e.hasMoreElements() && (ret == null) )
		{
			AslCoinvolte temp = e.nextElement();
			tot = tot + temp.getCu_pianificati();
		}
		return tot;
	}
	
	public int getNumCuEseguiti()
	{
		AslCoinvolte ret = null;
		int tot = 0;
		Enumeration<AslCoinvolte> e = asl_coinvolte.elements();
		while( e.hasMoreElements() && (ret == null) )
		{
			AslCoinvolte temp = e.nextElement();
			tot = tot + temp.getCu_eseguiti();
		}
		return tot;
	}
	
	public int getNumCuEseguitiPerAsl(int idAsl)
	{
		AslCoinvolte ret = null;
		int tot = 0;
		Enumeration<AslCoinvolte> e = asl_coinvolte.elements();
		while( e.hasMoreElements() && (ret == null) )
		{
			AslCoinvolte temp = e.nextElement();
			if (temp.getId_asl()==idAsl)
				tot = tot + temp.getCu_eseguiti() + temp.getNumCuEseguiti_aperti();
		}
		return tot;
	}

	public boolean isChiusura_forzata() {
		return chiusura_forzata;
	}

	public void setChiusura_forzata(boolean chiusura_forzata) {
		this.chiusura_forzata = chiusura_forzata;
	}
	
	
	
	public static HashMap<Integer, Boolean> getAllegatoFGenerabile(Connection db, Ticket allerta){
		HashMap<Integer, Boolean> AllegatoFGenerabile = new HashMap<Integer, Boolean>();
		
			if (allerta.getListaCommercializzazione()==2){
				Hashtable<String, AslCoinvolte> aslcoinvolte = AslCoinvolte.getAslConvolte(allerta.getId() ,allerta.getIdAllerta(),true, db);
			
				for (Entry<String, AslCoinvolte> entry : aslcoinvolte.entrySet()) {
					String key = entry.getKey();
				    AslCoinvolte value = (AslCoinvolte) entry.getValue();
					if (value.getData_chiusura()==null){
						AllegatoFGenerabile.put(value.getId_asl(), false);
					}
					else if (value.getData_chiusura()!=null){
						if (!AllegatoFGenerabile.containsKey(value.getId_asl()))
							AllegatoFGenerabile.put(value.getId_asl(), true);
					}
				}
		
			}
			else
			{
			for (int i = 1; i<=allerta.getListe_distribuzione().size(); i++ ){
				ListaDistribuzione lista = (ListaDistribuzione) allerta.getListe_distribuzione().get(i);
			
				Hashtable<String, AslCoinvolte> aslcoinvolte = AslCoinvolte.getAslConvolteByIdLdd(lista.getId(),allerta.getIdAllerta() ,lista.getId_allerta(),true, db);
				lista.setAsl_coinvolte(aslcoinvolte);
			
				for (Entry<String, AslCoinvolte> entry : aslcoinvolte.entrySet()) {
					String key = entry.getKey();
				    AslCoinvolte value = (AslCoinvolte) entry.getValue();
					if (value.getData_chiusura()==null){
						AllegatoFGenerabile.put(value.getId_asl(), false);
					}
					else if (value.getData_chiusura()!=null){
						if (!AllegatoFGenerabile.containsKey(value.getId_asl()))
							AllegatoFGenerabile.put(value.getId_asl(), true);
					}
				}
		}
			}	
			
		return AllegatoFGenerabile;
	}
	
	
	
	public int ripianificaPerAsl(Connection db, int newPianificati, int idAsl, String motivazione) throws SQLException, ParseException {
		
		int numCuEseguiti = getNumCuEseguitiPerAsl(idAsl);
		
		if ( newPianificati < numCuEseguiti){
			return 7;
		}
		
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
		
			String sql =
				"UPDATE allerte_asl_coinvolte " +
				"SET cu_pianificati = ?,  utente_modifica = ?, data_modifica = now(), motivazione = concat_ws(?, motivazione,?), note = ? " +
						"WHERE id_ldd = ? and id_asl = ? and data_chiusura is null ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setInt( ++i, newPianificati);
			pst.setInt(++i, this.getId_utente_modifica());
			pst.setString(++i, ";");
			pst.setString(++i, motivazione);
			pst.setString(++i, "Ripianificati CU da ASL");
			pst.setInt(++i, this.getId());
			pst.setInt(++i, idAsl);
			resultCount = pst.executeUpdate();
			pst.close();
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}
	
	
	
	
}

	


	


  
