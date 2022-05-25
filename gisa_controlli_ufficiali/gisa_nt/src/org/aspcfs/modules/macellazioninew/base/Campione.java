/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninew.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Vector;

import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;

import com.darkhorseventures.framework.beans.GenericBean;

public class Campione extends GenericBean
{
	private static final long serialVersionUID = 8313006891554941893L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id;
	private int id_capo;
	private int id_partita;
	private int id_seduta;
	private int id_tipo_analisi;
	private int id_molecole;
	private int	id_esito;
	private int	id_motivo;
	private Timestamp	data_ricezione_esito;
	

	private int 		matrice;
	private String		note;

	
	private int			entered_by;
	private int			modified_by;
	private Timestamp	entered;
	private Timestamp	modified;
	private Timestamp	trashed_date;
	
	
	
	public int getId_partita() {
		return id_partita;
	}

	public void setId_partita(Integer id_partita) {
		this.id_partita = id_partita;
	}

	public int getId_molecole() {
		return id_molecole;
	}

	public void setId_molecole(int id_molecole) {
		this.id_molecole = id_molecole;
	}
	
	
	public int getId_capo() {
		return id_capo;
	}

	public void setId_capo(int id_capo) {
		this.id_capo = id_capo;
	}
	
	public int getId_seduta() {
		return id_seduta;
	}
	public void setId_seduta(int id_seduta) {
		this.id_seduta = id_seduta;
	}

	public int getId_tipo_analisi() {
		return id_tipo_analisi;
	}

	public void setId_tipo_analisi(int id_tipo_analisi) {
		this.id_tipo_analisi = id_tipo_analisi;
	}

	public int getId_esito() {
		return id_esito;
	}

	public void setId_esito(int idEsito) {
		id_esito = idEsito;
	}

	public int getId_motivo() {
		return id_motivo;
	}

	public void setId_motivo(int idMotivo) {
		id_motivo = idMotivo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getMatrice() {
		return matrice;
	}

	public void setMatrice(int matrice) {
		this.matrice = matrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEntered_by() {
		return entered_by;
	}

	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}

	public int getModified_by() {
		return modified_by;
	}

	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public Timestamp getTrashed_date() {
		return trashed_date;
	}

	public void setTrashed_date(Timestamp trashed_date) {
		this.trashed_date = trashed_date;
	}

	public Timestamp getData_ricezione_esito() {
		return data_ricezione_esito;
	}

	public void setData_ricezione_esito(Timestamp dataRicezioneEsito) {
		data_ricezione_esito = dataRicezioneEsito;
	}

	public void store( Connection db, ConfigTipo configTipo )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		String sql = "INSERT INTO m_vpm_campioni( ";
		
		Field[]	f = this.getClass().getDeclaredFields();
	    Method[] m = this.getClass().getMethods();
		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		boolean firstField = true;
		
	    for( int i = 0; i < f.length; i++ )
	    {
	        String field = f[i].getName();
	        for( int j = 0; j < m.length; j++ )
	        {
	            String met = m[j].getName();
	            if( !field.equalsIgnoreCase( "id" ) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
	            {
	            	// COSA ZOZZISSIMA APPARATA
	            	//if (field.equalsIgnoreCase("id_capo") || field.equalsIgnoreCase("id_partita"))
	            		//	field = configTipo.getNomeCampoRifAltreTabelle();
	                 v.add( m[j] );
	                 v2.add( f[i] );
	                 sql += (firstField) ? ("") : (",");
	                 firstField = false;
	                 sql += " " + field;
	            }
	        }
	        
	    }
	    
	    sql += " ) VALUES (";
	    firstField = true;
	    
	    for( int i = 0; i < v.size(); i++ )
	    {
	        {
	            sql += (firstField) ? ("") : (",");
	            sql += " ?";
	            firstField = false;
	        }
	    }
	
	    sql += " )";
	    
	    PreparedStatement stat = db.prepareStatement( sql );
		
	    for( int i = 1; i <= v.size(); i++ )
	    {
	        Object o = v.elementAt( i - 1 ).invoke( this );
	        
	        if( o == null )
	        {
	            stat.setNull( i, parseType( v2.elementAt( i - 1 ).getType() ) );
	        }
	        else
	        {
	            switch ( parseType( o.getClass() ) )
	            {
	            case INT:
	                stat.setInt( i, (Integer)o );
	                break;
	            case STRING:
	                stat.setString( i, (String)o );
	                break;
	            case BOOLEAN:
	                stat.setBoolean( i, (Boolean)o );
	                break;
	            case TIMESTAMP:
	                stat.setTimestamp( i, (Timestamp)o );
	                break;
	            case DATE:
	                stat.setDate( i, (Date)o );
	                break;
	            case FLOAT:
	                stat.setFloat( i, (Float)o );
	                break;
	            case DOUBLE:
	                stat.setDouble( i, (Double)o );
	                break;
	            }
	        }
	    }
	    stat.execute();
	    stat.close();
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
    
	public static ArrayList<Campione> load( int id, ConfigTipo configTipo, Connection db )
	{

		ArrayList<Campione>	ret		= new ArrayList<Campione>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM m_vpm_campioni WHERE "+configTipo.getNomeCampoRifAltreTabelle()+" = ? and " + configTipo.getNomeCampoRifAltreTabelle() + " > 0 and trashed_date IS NULL" );
			stat.setInt( 1, id );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
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
	
	
	public static ArrayList<Campione> loadAssociazioneEsiti( String data, String matricola, int asl, Connection db )
	{

		ArrayList<Campione>	ret		= new ArrayList<Campione>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			String sqlBovini = "";
			String sqlPartita = "";
			String sqlSeduta = "";
			
			sqlBovini = "select c.id as id_capo, " +
					    "camp.id as id,  " +
						"0 as id_seduta,  " +
						"c.cd_matricola as matricola,  " +
						"'' as numero_macellazione, " +
						"'Bovini' as tipo_macellazione,  " +
						"c.vpm_data as data_macellazione,  " +
						//"CASE WHEN org.name is not null THEN org.name ELSE opu.ragione_sociale END AS impresa," +
						"org.denominazione AS impresa," +

						"mot.description as motivo,  " +
						"mat.description as matrice,  " +
						"t_analisi.description as tipo_analisi,  " +
						"mol.description as molecole,  " +
						"camp.note as note_molecole  " +
						"from m_vpm_campioni camp  " +
						"left join m_capi c on c.id = camp.id_capo and  " +
						"                      (c.cd_matricola = ? or ? = '') and  ";
			if(!data.equals(""))
				sqlBovini +=  " (c.vpm_data = cast(? as timestamp) or c.vpm_data is null) and  ";
			sqlBovini +=  "				       c.trashed_date is null  " +
						//"left join organization org on org.org_id = c.id_macello and org.trashed_date is null " +
						"left join sintesis_stabilimento org on org.alt_id = c.id_macello and org.trashed_date is null " +
						
						"left join opu_operatore opu on opu.id =(select id_operatore from opu_stabilimento where alt_id = c.id_macello) "+ 
						"left join m_lookup_campioni_motivi mot on mot.code = camp.id_motivo " +
						"left join m_lookup_matrici mat on mat.code = camp.matrice " +
						"left join m_lookup_tipo_analisi t_analisi on t_analisi.code = camp.id_tipo_analisi " +
						"left join m_lookup_molecole mol on mol.code = camp.id_molecole " +
						"where camp.trashed_date is null and  " +
						"      camp.id_esito = -1 and " +
						//"      (org.site_id = " + asl + " or " + asl+"=-1 ) and " +
						"      (org.id_asl = " + asl + " or " + asl+"=-1 ) and " +

					    "      c.id is not null and c.articolo17 ";
			
			sqlPartita = "select p.id as id_capo, " +
						"camp.id as id,  " +
						"0 as id_seduta,  " +
						"p.cd_partita as matricola,  " +
						"'' as numero_macellazione, " +
						"'Ovicaprini' as tipo_macellazione,  " +
						"p.vpm_data as data_macellazione,  " +
						//"CASE WHEN org.name is not null THEN org.name ELSE opu.ragione_sociale END AS impresa," +
						"org.denominazione AS impresa," +
						
						"mot.description as motivo,  " +
						"mat.description as matrice,  " +
						"t_analisi.description as tipo_analisi,  " +
						"mol.description as molecole,  " +
						"camp.note as note_molecole  " +
						"from m_vpm_campioni camp  " +
						"left join m_partite p on p.id = camp.id_partita and  " +
						"                      (p.cd_partita = ? or ? = '') and  ";
			if(!data.equals(""))
				sqlPartita +=  " (p.vpm_data = cast(? as timestamp) or p.vpm_data is null) and  ";
			sqlPartita +=  "				       p.trashed_date is null  " +
						//"left join organization org on org.org_id = p.id_macello and org.trashed_date is null " +
						"left join sintesis_stabilimento org on org.alt_id = p.id_macello and org.trashed_date is null " +
						
						"left join opu_operatore opu on opu.id =(select id_operatore from opu_stabilimento where alt_id = p.id_macello)  "+
						"left join m_lookup_campioni_motivi mot on mot.code = camp.id_motivo " +
						"left join m_lookup_matrici mat on mat.code = camp.matrice " +
						"left join m_lookup_tipo_analisi t_analisi on t_analisi.code = camp.id_tipo_analisi " +
						"left join m_lookup_molecole mol on mol.code = camp.id_molecole " +
						"where camp.trashed_date is null and  " +
						"      camp.id_esito = -1 and " +
						//"      (org.site_id = " + asl + " or " + asl+"=-1 ) and " +
						"      (org.id_asl = " + asl + " or " + asl+"=-1 ) and " +
						
					    "      p.id is not null and (select count(*) from m_partite_sedute s2 where s2.trashed_date is null and s2.articolo17=false and s2.id_partita = p.id) = 0 and p.articolo17 ";
			
			sqlSeduta = "select p.id as id_capo, " +
					"camp.id as id,  " +
					"s.id as id_seduta,  " +
					"p.cd_partita as matricola,  " +
					"s.numero as numero_macellazione, " +
					"'Ovicaprini' as tipo_macellazione,  " +
					"s.vpm_data as data_macellazione,  " +
					//"CASE WHEN org.name is not null THEN org.name ELSE opu.ragione_sociale END AS impresa," +
					"org.denominazione AS impresa," +
					
					"mot.description as motivo,  " +
					"mat.description as matrice,  " +
					"t_analisi.description as tipo_analisi,  " +
					"mol.description as molecole,  " +
					"camp.note as note_molecole  " +
					"from m_vpm_campioni camp  " +
					"left join m_partite_sedute s on s.id = camp.id_seduta and  " ;
			if(!data.equals(""))
				sqlSeduta +=  " (s.vpm_data = cast(? as timestamp) or s.vpm_data is null) and  ";
			sqlSeduta +=  "				       s.trashed_date is null  " +
					"left join m_partite p on p.id = s.id_partita and p.trashed_date is null " +
					//"left join organization org on org.org_id = p.id_macello and org.trashed_date is null " +
					"left join sintesis_stabilimento org on org.alt_id = p.id_macello and org.trashed_date is null " +
					
					"left join opu_operatore opu on opu.id =(select id_operatore from opu_stabilimento where alt_id= p.id_macello ) "+
					"left join m_lookup_campioni_motivi mot on mot.code = camp.id_motivo " +
					"left join m_lookup_matrici mat on mat.code = camp.matrice " +
					"left join m_lookup_tipo_analisi t_analisi on t_analisi.code = camp.id_tipo_analisi " +
					"left join m_lookup_molecole mol on mol.code = camp.id_molecole " +
					"where camp.trashed_date is null and    " +
					"      (p.cd_partita = ? or ? = '') and " +
					"      camp.id_esito = -1 and " +
					//"      (org.site_id = " + asl + " or " + asl+"=-1 ) and " +
					"      (org.id_asl = " + asl + " or " + asl+"=-1 ) and " +
					
					"      s.id is not null and s.articolo17 ";
			
			String sql = "select * from (" + sqlBovini + " union " + sqlPartita + " union " + sqlSeduta + ") t "
					+ "ORDER BY t.id_capo, t.id_seduta;";
			stat	= db.prepareStatement(sql);
			int i=1;
			stat.setString(i++, matricola);
			stat.setString(i++, matricola);
			if(!data.equals(""))
				stat.setString(i++, data);
			stat.setString(i++, matricola);
			stat.setString(i++, matricola);
			if(!data.equals(""))
			{
				stat.setString(i++, data);
				stat.setString(i++, data);
			}
			stat.setString(i++, matricola);
			stat.setString(i++, matricola);
			
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetAssociazioneEsiti( res ) );
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
	
	
	public static ArrayList<Campione> loadCampioniTBC( int id_capo, Connection db, ConfigTipo configTipo )
	{

		ArrayList<Campione>	ret		= new ArrayList<Campione>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM m_vpm_campioni WHERE "+ configTipo.getNomeCampoRifAltreTabelle() + "= ? and id_molecole = 264 and trashed_date IS NULL" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
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
	
	
	public static ArrayList<Campione> loadCampioniBRC( int id_capo, Connection db, ConfigTipo configTipo )
	{

		ArrayList<Campione>	ret		= new ArrayList<Campione>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM m_vpm_campioni WHERE  "+ configTipo.getNomeCampoRifAltreTabelle() + " = ? and id_molecole = 265 and trashed_date IS NULL" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
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
	
    
	private static Campione loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Campione ret = new Campione();
	
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
	
	private static Campione loadResultSetAssociazioneEsiti( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			CampioneAssociazioneEsiti ret = new CampioneAssociazioneEsiti();
			ret.setIdCapo(res.getInt("id_capo"));
			ret.setIdSeduta(res.getInt("id_seduta"));
			ret.setMatricola(res.getString("matricola"));
			ret.setNumeroMacellazione(res.getString("numero_macellazione"));
			ret.setTipoMacellazione(res.getString("tipo_macellazione"));
			ret.setDataMacellazione(res.getTimestamp("data_macellazione"));
			ret.setImpresa(res.getString("impresa"));
			ret.setMotivo(res.getString("motivo"));
			ret.setMatriceDesc(res.getString("matrice"));
			ret.setTipoAnalisi(res.getString("tipo_analisi"));
			ret.setMolecole(res.getString("molecole"));
			ret.setNoteMolecole(res.getString("note_molecole"));
			ret.setId(res.getInt("id"));
			return ret;
		}

	public void update(Connection db)
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "UPDATE m_vpm_campioni SET ";
		
		Field[]	f = this.getClass().getDeclaredFields();
	    Method[] m = this.getClass().getMethods();
		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		boolean firstField = true;
		
	    for( int i = 0; i < f.length; i++ )
	    {
	        String field = f[i].getName();
	        for( int j = 0; j < m.length; j++ )
	        {
	            String met = m[j].getName();
	            if( !field.equalsIgnoreCase( "id" ) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
	            {
	                 v.add( m[j] );
	                 v2.add( f[i] );
	                 sql += (firstField) ? ("") : (",");
	                 firstField = false;
	                 sql += " " + field + " = ?";
	            }
	        }
	        
	    }
	    
	    sql += " WHERE id = ?";
	
	    PreparedStatement stat = db.prepareStatement( sql );
		
	    for( int i = 1; i <= v.size(); i++ )
	    {
	        Object o = v.elementAt( i - 1 ).invoke( this );
	        
	        if( o == null )
	        {
	            stat.setNull( i, parseType( v2.elementAt( i - 1 ).getType() ) );
	        }
	        else
	        {
	            switch ( parseType( o.getClass() ) )
	            {
	            case INT:
	                stat.setInt( i, (Integer)o );
	                break;
	            case STRING:
	                stat.setString( i, (String)o );
	                break;
	            case BOOLEAN:
	                stat.setBoolean( i, (Boolean)o );
	                break;
	            case TIMESTAMP:
	                stat.setTimestamp( i, (Timestamp)o );
	                break;
	            case DATE:
	                stat.setDate( i, (Date)o );
	                break;
	            case FLOAT:
	                stat.setFloat( i, (Float)o );
	                break;
	            case DOUBLE:
	                stat.setDouble( i, (Double)o );
	                break;
	            }
	        }
	    }
	    
	    stat.setInt( v.size() + 1, id );
	    
	    stat.execute();
	    stat.close();
			
	}

	public static void delete( int id, int user_id, Connection db )
	{
		PreparedStatement	stat	= null;
		try
		{
			stat	= db.prepareStatement( 
					"UPDATE m_vpm_campioni SET modified = CURRENT_TIMESTAMP, trashed_date = CURRENT_TIMESTAMP, modified_by = ? " +
					"WHERE id = ? AND trashed_date IS NULL" );

			stat.setInt( 1, user_id );
			stat.setInt( 2, id );
			
			stat.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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
	}

}
