/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.guc;

import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Utente;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.db.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuoloDAO extends GenericDAO
{
	public static final Logger logger = LoggerFactory.getLogger( RuoloDAO.class );
	
	
	
	
	public static ArrayList<Ruolo> getRuoliByEndPoint(String endPoint,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<Ruolo>		ret		= new ArrayList<Ruolo>();
		
		try
		{
			
			stat	= db.prepareStatement( Sql.GET_RUOLI_BY_ENDPOINT );
			stat.setString(1, endPoint) ;
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				Ruolo ruolo = new Ruolo();
				ruolo.setRuoloInteger( rs.getInt		("ruoloInteger") );
				
				ruolo.setRuoloString( rs.getString	("ruoloString") );
				
				ret.add( ruolo );
			}
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

		return ret;
	}
	
	public static ArrayList<String> getRolesByIdUtenteN(int idUtente,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<String> ret = new ArrayList<String> ();
		
		try
		{
			
			//stat	= db.prepareStatement( "select * from guc_ruoli where id_utente  = ? and ruolo_integer > 0 and trashed is null " );
			
			stat = db.prepareStatement("select * from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id where gr.ruolo_integer > 0 and "
					+ " gr.trashed is null and gr.codice_raggruppamento in (select codice_raggruppamento from guc_ruoli  where id_utente = ? )");	

			stat.setInt(1, idUtente) ;
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				//Ruolo ruolo = createBeanUtente(rs);
				//ruolo.setUtente(utente);
				ret.add(rs.getString("ruolo_string") );
			}
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

		return ret;
	}
	

	public static ArrayList<String> getUsernameByEndpoint(int idUtente,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<String> ret = new ArrayList<String> ();
		
		try
		{
			stat = db.prepareStatement(" select * from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id where gr.ruolo_integer > 0 and "
					+ " gr.trashed is null and gr.codice_raggruppamento in (select codice_raggruppamento from guc_ruoli  where id_utente = ? )");	

			stat.setInt(1, idUtente) ;
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				//Ruolo ruolo = createBeanUtente(rs);
				//ruolo.setUtente(utente);
				ret.add(rs.getString("username") );
			}
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

		return ret;
	}
	
	
	
	public static ArrayList<String> getRuoliByNomeUtente(String username,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<String> ret = new ArrayList<String> ();
		
		try
		{
			
			stat	= db.prepareStatement( "select * from guc_ruoli where trashed is null and  id_utente in (select id from guc_utenti where username = ?) "
					+ " and ruolo_integer > 0" );
			stat.setString(1, username) ;
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				ret.add(rs.getString("ruolo_string") );
			}
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

		return ret;
	}
	
	public static ArrayList<String> getEndpointByNomeUtente(String username,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<String> ret =  new ArrayList<String> ();
		
		try
		{
			
			stat	= db.prepareStatement( "select * from guc_ruoli where trashed is null and  id_utente in (select id from guc_utenti where username = ?) "
					+ " and ruolo_integer > 0" );
			
			stat.setString(1, username) ;
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				ret.add(rs.getString("endpoint") );
			}
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

		return ret;
	}
	
	
	public static Set<Ruolo> getRuoliByIdUtente(Utente utente,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		Set<Ruolo> ret = new HashSet<Ruolo>();
		
		try
		{
			
			//stat	= db.prepareStatement( "select * from guc_ruoli where id_utente  =? and ruolo_integer > 0" );
			stat	= db.prepareStatement( "select gr.codice_raggruppamento, * from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id "
					+ "	where gr.ruolo_integer > 0 and gr.trashed is null and gr.codice_raggruppamento in (select codice_raggruppamento from guc_ruoli "
					+ " where id_utente = ? " );
			
			
			stat.setInt(1, utente.getId()) ;
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				Ruolo ruolo = createBeanUtente(rs);
				ruolo.setUtente(utente);
				ret.add( ruolo );
			}
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

		return ret;
	}
	
	
	
	
	public static void insert(Connection db ,Ruolo r)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			
			//int idGucRuolo = DbUtil.getNextSeqTipo(db, "guc_ruoli_id_seq");
			stat	= db.prepareStatement( "INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (?,?,?,?,?) " );
			stat.setString(1, r.getEndpoint()) ;
			stat.setInt(2, r.getRuoloInteger()) ;
			stat.setString(3, r.getRuoloString()) ;
			stat.setInt(4, r.getUtente().getId()) ;
			stat.setString(5, r.getNote()) ;
			stat.executeUpdate();
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

	}
	
	
	
	public static void updateForImport(Connection db ,Ruolo r)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			
			//int idGucRuolo = DbUtil.getNextSeqTipo(db, "guc_ruoli_id_seq");
			stat	= db.prepareStatement( "delete from guc_ruoli  where id_utente = ? ;INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (?,?,?,?,?) " );
			
			stat.setInt(1, r.getUtente().getId());
			stat.setString(2, r.getEndpoint()) ;
			stat.setInt(3, r.getRuoloInteger()) ;
			stat.setString(4, r.getRuoloString()) ;
			stat.setInt(5, r.getUtente().getId()) ;
			stat.setString(6, r.getNote()) ;
			stat.executeUpdate();
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

	}
	
	public static void update(Connection db ,Ruolo r)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			
			stat	= db.prepareStatement( "update guc_ruoli set endpoint=?,ruolo_integer=?,ruolo_string=?,note=? where id = ? " );
			stat.setString(1, r.getEndpoint()) ;
			stat.setInt(2, r.getRuoloInteger()) ;
			stat.setString(3, r.getRuoloString()) ;
			stat.setString(4, r.getNote()) ;
			stat.setInt(5, r.getId()) ;
			stat.executeUpdate();
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

	}
	
	
	
	public static Ruolo createBeanUtente(ResultSet rs)
	{
		Ruolo ruolo = new Ruolo();
		
		try 
		{
			ruolo.setId(rs.getInt("id"));
			ruolo.setNote(rs.getString("note"));
			ruolo.setRuoloInteger(rs.getInt("ruolo_integer"));
			ruolo.setRuoloString(rs.getString("ruolo_string"));
			ruolo.setEndpoint(rs.getString("endpoint"));
			
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return ruolo;
	}
	
	public static ResultSet getEndpointByIdUtente(int idUtente,Connection db)
	{
		
		if (db==null)
		System.out.println("******************************* DB NULL");
		
		PreparedStatement	stat	= null;
		PreparedStatement	stat2	= null;
		ResultSet			rs		= null;
		ResultSet			rs2		= null;
		try
		{
			
			//stat	= db.prepareStatement( "select * from guc_ruoli where id_utente  = ? and ruolo_integer > 0 and trashed is null " );
			stat = db.prepareStatement("select distinct on (ep, ruolo_string, asl,canilebdu_description)  ep as endpoint, ruolo_string, asl, username,num_registrazione_stab,piva,canilebdu_description from (select case when gu.asl_id = 201 then 'AVELLINO' when gu.asl_id = 202 then 'BENEVENTO'"
					+ "	when gu.asl_id = 203 then 'CASERTA'	when gu.asl_id = 204 then 'NAPOLI 1 CENTRO'	when gu.asl_id = 205 then 'NAPOLI 2 NORD'"
					+ "	when gu.asl_id = 206 then 'NAPOLI 3 SUD' when gu.asl_id = 207 then 'SALERNO' ELSE 'TUTTE' end as asl,gr.endpoint as ep,* from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id  left join extended_option ext on ext.user_id = gr.id_utente and ext.key=concat(gr.endpoint , '_access') and ext.endpoint=gr.endpoint where  (ext.val='true' or ext.val is null) and gr.ruolo_integer > 0 and "
					+ " gr.trashed is null and gr.codice_raggruppamento in (select codice_raggruppamento from guc_ruoli  where id_utente = ? ) "
					+ " order by gr.endpoint, gr.ruolo_string ) a order by asl ");	

			/*stat2 = db.prepareStatement("select distinct on (endpoint, ruolo_string, asl) endpoint, ruolo_string, asl, username from (select case when gu.asl_id = 201 then 'AVELLINO' when gu.asl_id = 202 then 'BENEVENTO'"
					+ "	when gu.asl_id = 203 then 'CASERTA'	when gu.asl_id = 204 then 'NAPOLI 1 CENTRO'	when gu.asl_id = 205 then 'NAPOLI 2 NORD'"
					+ "	when gu.asl_id = 206 then 'NAPOLI 3 SUD' when gu.asl_id = 207 then 'SALERNO' ELSE 'TUTTE' end as asl,* from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id where gr.ruolo_integer > 0 and "
					+ " gr.trashed is null and id_utente = ? order by gr.endpoint, gr.ruolo_string) a order by asl ");
			*/
			stat2 =db.prepareStatement("WITH q as (select case when gu.asl_id = 201 then 'AVELLINO' when gu.asl_id = 202 then 'BENEVENTO' when gu.asl_id = 203 then 'CASERTA'        "
					+ "when gu.asl_id = 204 then 'NAPOLI 1 CENTRO'        when gu.asl_id = 205 then 'NAPOLI 2 NORD' when gu.asl_id = 206 then 'NAPOLI 3 SUD' when gu.asl_id = 207 "
					+ "then 'SALERNO' ELSE 'TUTTE' end as asl,gr.endpoint as ep,* from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id   left join extended_option ext on ext.user_id = gr.id_utente and ext.key=concat(gr.endpoint , '_access') and ext.endpoint=gr.endpoint where   (ext.val='true' or ext.val is null) and  gr.ruolo_integer > 0 and gr.trashed is null and id_utente =  ? "
					+ "order by gr.endpoint, gr.ruolo_string) select distinct on (ep, ruolo_string, asl,canilebdu_description) ep as endpoint, ruolo_string, asl, username, num_registrazione_stab,piva,canilebdu_description from q order by asl");
			
			stat.setInt(1, idUtente) ;
			rs		= stat.executeQuery();
			
			if(!rs.isBeforeFirst()){
				stat2.setInt(1,idUtente);
				rs2		= stat2.executeQuery();
				rs = rs2;
			}
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			logger.error( "", e );
		}
		finally
		{
		
		}
		
		return rs;
	}
	
	
	public static ResultSet getEndpointByCf(String cf,Connection db)
	{
		
		if (db==null)
		System.out.println("******************************* DB NULL");
		
		//PreparedStatement	stat	= null;
		PreparedStatement	stat2	= null;
		//ResultSet			rs		= null;
		ResultSet			rs2		= null;
		try
		{
			
			/*stat = db.prepareStatement("select distinct on (ep, ruolo_string, asl,user_id,canilebdu_description,num_registrazione_stab,piva) ep as endpoint, ruolo_string, asl, username, num_registrazione_stab,canilebdu_description from (select case when gu.asl_id = 201 then 'AVELLINO' when gu.asl_id = 202 then 'BENEVENTO'"
					+ "	when gu.asl_id = 203 then 'CASERTA'	when gu.asl_id = 204 then 'NAPOLI 1 CENTRO'	when gu.asl_id = 205 then 'NAPOLI 2 NORD'"
					+ "	when gu.asl_id = 206 then 'NAPOLI 3 SUD' when gu.asl_id = 207 then 'SALERNO' ELSE 'TUTTE' end as asl,gr.endpoint as ep,* from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id left join extended_option ext on ext.user_id = gr.id_utente and ext.key=concat(gr.endpoint , '_access') and ext.endpoint=gr.endpoint where gr.ruolo_string not in ( 'GIAVA' ) and  (ext.val='true' or ext.val is null) and gr.ruolo_integer > 0 and "
					+ " gr.trashed is null and gr.codice_raggruppamento in (select codice_raggruppamento from guc_ruoli  where id_utente  in ( SELECT id FROM GUC_UTENTI WHERE ENABLED AND upper(CODICE_FISCALE) = upper(?) ) ) "
					+ " order by gr.endpoint, gr.ruolo_string ) a order by ep ");	*/

			stat2 =db.prepareStatement("WITH q as (select case when gu.asl_id = 201 then 'AVELLINO' when gu.asl_id = 202 then 'BENEVENTO' when gu.asl_id = 203 then 'CASERTA'        "
					+ "when gu.asl_id = 204 then 'NAPOLI 1 CENTRO'        when gu.asl_id = 205 then 'NAPOLI 2 NORD' when gu.asl_id = 206 then 'NAPOLI 3 SUD' when gu.asl_id = 207 "
					+ "then 'SALERNO' ELSE 'TUTTE' end as asl,gr.endpoint as ep,* from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id  left join extended_option ext on ext.user_id = gr.id_utente and ext.key=concat(gr.endpoint , '_access') and ext.endpoint=gr.endpoint where gr.ruolo_string not in ( 'GIAVA' ) and  (ext.val='true' or ext.val is null) and gr.ruolo_integer > 0 and gr.trashed is null and id_utente  in ( SELECT id FROM GUC_UTENTI WHERE ENABLED AND upper(CODICE_FISCALE) = upper(?) ) "
					+ "order by gr.endpoint, gr.ruolo_string) select distinct on (ep, ruolo_string, asl,user_id,canilebdu_description,num_registrazione_stab,piva) ep as endpoint, ruolo_string, asl, username, num_registrazione_stab,piva,canilebdu_description from q order by ep");
			
			
			//stat.setString(1, cf) ;
			//rs		= stat.executeQuery();
			
			//if(!rs.isBeforeFirst()){
				stat2.setString(1,cf);
				rs2		= stat2.executeQuery();
				//rs = rs2;
			//}
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			logger.error( "", e );
		}
		finally
		{
		
		}
		
		//return rs;
		return rs2;
	}
	
	public static ResultSet getEndpointByCF(String codice_fiscale,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		try
		{
			
			//stat	= db.prepareStatement( "select * from guc_ruoli where id_utente  = ? and ruolo_integer > 0 and trashed is null " );
			stat = db.prepareStatement("select distinct on (endpoint, ruolo_string, asl,canilebdu_description) endpoint, ruolo_string, asl, username,num_registrazione_stab,piva,canilebdu_description  from (select case when gu.asl_id = 201 then 'AVELLINO' when gu.asl_id = 202 then 'BENEVENTO'"
					+ "	when gu.asl_id = 203 then 'CASERTA'	when gu.asl_id = 204 then 'NAPOLI 1 CENTRO'	when gu.asl_id = 205 then 'NAPOLI 2 NORD'"
					+ "	when gu.asl_id = 206 then 'NAPOLI 3 SUD' when gu.asl_id = 207 then 'SALERNO' ELSE 'TUTTE' end as asl,* from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id where gr.ruolo_integer > 0 and "
					+ " gr.trashed is null and gu.codice_fiscale = ? "
					+ " order by gr.endpoint, gr.ruolo_string ) a order by asl ");	
			
			stat.setString(1, codice_fiscale) ;
			rs		= stat.executeQuery();		
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			logger.error( "", e );
		}
		finally
		{
		
		}
		
		return rs;
	}
	
	
	public static ResultSet getEndpointByUsername(String username,Connection db,boolean loginSpid){
		
		PreparedStatement	stat	= null;
		PreparedStatement	stat2	= null;
		ResultSet			rss		= null;
		ResultSet			rs2		= null;
		try
		{
			
			String orderBy = (loginSpid)?("endpoint"):("asl");
			
			//stat	= db.prepareStatement( "select * from guc_ruoli where id_utente  = ? and ruolo_integer > 0 and trashed is null " );
			stat = db.prepareStatement("select distinct on (ep, ruolo_string, asl,canilebdu_description) ep as endpoint, ruolo_string, asl, username, codice_fiscale from (select case when gu.asl_id = 201 then 'AVELLINO' when gu.asl_id = 202 then 'BENEVENTO' " + 
					"						when gu.asl_id = 203 then 'CASERTA'	when gu.asl_id = 204 then 'NAPOLI 1 CENTRO'	when gu.asl_id = 205 then 'NAPOLI 2 NORD' " + 
					"						when gu.asl_id = 206 then 'NAPOLI 3 SUD' when gu.asl_id = 207 then 'SALERNO' ELSE 'TUTTE' end as asl,gr.endpoint as ep, * from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id  left join extended_option ext on ext.user_id = gr.id_utente and ext.key=concat(gr.endpoint , '_access') and ext.endpoint=gr.endpoint where (ext.val='true' or ext.val is null) and gr.ruolo_integer > 0 and " + 
					"					 gr.trashed is null and gr.codice_raggruppamento in (select codice_raggruppamento from guc_ruoli where id_utente in (select id from guc_utenti where username = ? )) " + 
					"					 order by gr.endpoint, gr.ruolo_string ) a order by " + orderBy );

			/*stat2 = db.prepareStatement("select distinct on (endpoint, ruolo_string, asl) endpoint, ruolo_string, asl, username from (select case when gu.asl_id = 201 then 'AVELLINO' when gu.asl_id = 202 then 'BENEVENTO'"
					+ "	when gu.asl_id = 203 then 'CASERTA'	when gu.asl_id = 204 then 'NAPOLI 1 CENTRO'	when gu.asl_id = 205 then 'NAPOLI 2 NORD'"
					+ "	when gu.asl_id = 206 then 'NAPOLI 3 SUD' when gu.asl_id = 207 then 'SALERNO' ELSE 'TUTTE' end as asl,* from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id where gr.ruolo_integer > 0 and "
					+ " gr.trashed is null and gu.username = ? order by gr.endpoint, gr.ruolo_string) a order by asl ");*/
			
			stat2 =db.prepareStatement(" WITH q as (select case when gu.asl_id = 201 then 'AVELLINO' when gu.asl_id = 202 then 'BENEVENTO' when gu.asl_id = 203 then 'CASERTA'        " + 
					"					when gu.asl_id = 204 then 'NAPOLI 1 CENTRO'        when gu.asl_id = 205 then 'NAPOLI 2 NORD' when gu.asl_id = 206 then 'NAPOLI 3 SUD' when gu.asl_id = 207 " + 
					"					then 'SALERNO' ELSE 'TUTTE' end as asl,gr.endpoint as ep,* from guc_ruoli gr join guc_utenti gu on gr.id_utente = gu.id  left join extended_option ext on ext.user_id = gr.id_utente and ext.key=concat(gr.endpoint , '_access') and ext.endpoint=gr.endpoint where (ext.val='true' or ext.val is null) and gr.ruolo_integer > 0 and gr.trashed is null and gu.username = ? " + 
					"					order by gr.endpoint, gr.ruolo_string) select distinct on (ep, ruolo_string, asl,canilebdu_description) ep as endpoint, ruolo_string, asl, username, codice_fiscale, num_registrazione_stab,piva,canilebdu_description from q order by " + orderBy );
			
			
			stat.setString(1, username) ;
			rss		= stat.executeQuery();
			
			if(!rss.isBeforeFirst()){
				stat2.setString(1,username);
				
				rs2		= stat2.executeQuery();
				
				
				rss = rs2;
			}
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			logger.error( "", e );
		}
/*		finally
		{
			try {
				db.close();
				stat.close();
				stat2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		return rss;
	}
	
	
}
