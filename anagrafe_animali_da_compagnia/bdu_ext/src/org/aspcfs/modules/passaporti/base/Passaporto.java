/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.passaporti.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.aspcfs.utils.Column;




import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.beans.GenericBean;

public class Passaporto extends GenericBean {
	
	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "passaporto";
	
	
	@Column(columnName = "id_uos", columnType = INT, table = nome_tabella)
	private Integer idUos = -1;
	@Column(columnName = "nr_passaporto", columnType = STRING, table = nome_tabella)
	private String nrPassaporto = "";
	@Column(columnName = "data_precaricamento", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataPrecaricamento = null;
	@Column(columnName = "data_utilizzo", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataModifica = null;
	@Column(columnName = "data_modifica", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataUtilizzo = null;
	@Column(columnName = "id_asl_appartenenza", columnType = INT, table = nome_tabella)
	private int idAslAppartenenza = -1;
	@Column(columnName = "id_utente_precaricamento", columnType = INT, table = nome_tabella)
	private int idUtentePrecaricamento = -1;
	@Column(columnName = "id_utente_utilizzo", columnType = INT, table = nome_tabella)
	private int idUtenteUtilizzo = -1;
	@Column(columnName = "id_import", columnType = INT, table = nome_tabella)
	private int idImport = -1;
	@Column(columnName = "status_id", columnType = INT, table = nome_tabella)
	private int idStatus = -1;
	@Column(columnName = "id", columnType = INT, table = nome_tabella)
	private int id = -1;
	@Column(columnName = "data_cancellazione", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataCancellazione = null;
	@Column(columnName = "flag_abilitato", columnType = BOOLEAN, table = nome_tabella)
	private boolean flagAbilitato = true;
	@Column(columnName = "id_animale", columnType = INT, table = nome_tabella)
	private int idAnimale = -1;
	@Column(columnName = "id_specie", columnType = INT, table = nome_tabella)
	private int idSpecie = -1;
	
	
	public String getNrPassaporto() {
		return nrPassaporto;
	}
	public void setNrPassaporto(String nrPassaporto) {
		this.nrPassaporto = nrPassaporto;
	}
	public Integer getIdUos() {
		return idUos;
	}
	public void setIdUos(Integer idUos) {
		this.idUos = idUos;
	}
	public Timestamp getDataPrecaricamento() {
		return dataPrecaricamento;
	}
	public void setDataPrecaricamento(Timestamp dataPrecaricamento) {
		this.dataPrecaricamento = dataPrecaricamento;
	}
	public Timestamp getDataUtilizzo() {
		return dataUtilizzo;
	}
	public void setDataUtilizzo(Timestamp dataUtilizzo) {
		this.dataUtilizzo = dataUtilizzo;
	}
	public int getIdAslAppartenenza() {
		return idAslAppartenenza;
	}
	public void setIdAslAppartenenza(int idAslAppartenenza) {
		this.idAslAppartenenza = idAslAppartenenza;
	}
	public void setIdAslAppartenenza(String idAslAppartenenza) {
		this.idAslAppartenenza = Integer.valueOf(idAslAppartenenza);
	}
	public int getIdUtentePrecaricamento() {
		return idUtentePrecaricamento;
	}
	public void setIdUtentePrecaricamento(int idUtentePrecaricamento) {
		this.idUtentePrecaricamento = idUtentePrecaricamento;
	}
	public int getIdUtenteUtilizzo() {
		return idUtenteUtilizzo;
	}
	public void setIdUtenteUtilizzo(int idUtenteUtilizzo) {
		this.idUtenteUtilizzo = idUtenteUtilizzo;
	}
	public int getIdImport() {
		return idImport;
	}
	public void setIdImport(int idImport) {
		this.idImport = idImport;
	}
	

	public int getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}
	public Timestamp getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public boolean isFlagAbilitato() {
		return flagAbilitato;
	}
	public void setFlagAbilitato(boolean flagAbilitato) {
		this.flagAbilitato = flagAbilitato;
	}
	public int getIdAnimale() {
		return idAnimale;
	}
	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}
	
	
	
	

	public Timestamp getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
	
	
	public int getIdSpecie() {
		return idSpecie;
	}
	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
	}
	
	public void setIdSpecie(String idSpecie) {
		this.idSpecie = Integer.valueOf(idSpecie);
	}
	public Passaporto store(Connection db) throws Exception

	{
		try {

			this.dataPrecaricamento = new Timestamp(System.currentTimeMillis());
			this.dataModifica = this.dataPrecaricamento;
			
			id = DatabaseUtils.getNextSeqInt(db, "passaporto_id_seq");
			String identificativo_isto = "ISTO_G_"+id;
			
		

			String sql = "INSERT INTO passaporto( ";

			// Field[] f = this.getClass().getDeclaredFields();
			// String[] campi = new
			// String[RichiestaIstopatologico.class.getFields().length];
			HashMap<String, Integer> campi = new HashMap<String, Integer>();
			int k = 0;

			for (Field f : Passaporto.class.getDeclaredFields()) {
				Column column = f.getAnnotation(Column.class);
				if (column != null) {
					System.out.println(column.columnName());
					if (column.table() == null || (nome_tabella).equals(column.table()) )
					campi.put(column.columnName(), column.columnType());
				}
			}

			Method[] m = this.getClass().getMethods();
			Vector<Method> v = new Vector<Method>();
			Vector<Integer> v2 = new Vector<Integer>();
			boolean firstField = true;

			Iterator it = campi.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				System.out.println(pairs.getKey() + " = " + pairs.getValue());

				// for( int i = 0; i < campi.length; i++ )
				// {
				String field = (String) pairs.getKey();

				for (int j = 0; j < m.length; j++) {
					String met = m[j].getName();
					if (
//					!field.equalsIgnoreCase("id")
//							&&
					(met.equalsIgnoreCase("GET" + field.replaceAll("_", "")) || met.equalsIgnoreCase("IS"
									+ field.replaceAll("_", "")))) {
						v.add(m[j]);
						v2.add((Integer) pairs.getValue());
						sql += (firstField) ? ("") : (",");
						firstField = false;
						sql += " " + field;
					}
				}

			}

			sql += " ) VALUES (";
			firstField = true;

			for (int i = 0; i < v.size(); i++) {
				{
					sql += (firstField) ? ("") : (",");
					sql += " ?";
					firstField = false;
				}
			}

			sql += " )";

			PreparedStatement stat = db.prepareStatement(sql);

			for (int i = 1; i <= v.size(); i++) {
				Object o = v.elementAt(i - 1).invoke(this);

				if (o == null) {
					stat.setNull(i, v2.elementAt(i - 1));
				} else {
					switch (parseType(o.getClass())) {
					case INT:
						stat.setInt(i, (Integer) o);
						break;
					case STRING:
						String s = (String) o;
						s = s.replaceAll("u13", " ");
						s = s.replaceAll("u10", " ");
						s = s.replaceAll("\\r", " ");
						s = s.replaceAll("\\n", " ");
						s = s.replaceAll("à", "a'");
						s = s.replaceAll("è", "e'");
						s = s.replaceAll("é", "e'");
						s = s.replaceAll("ì", "i'");
						s = s.replaceAll("ò", "o'");
						s = s.replaceAll("ù", "u'");
						stat.setString(i, s);
						break;
					case BOOLEAN:
						stat.setBoolean(i, (Boolean) o);
						break;
					case TIMESTAMP:
						stat.setTimestamp(i, (Timestamp) o);
						break;
					case DATE:
						stat.setDate(i, (Date) o);
						break;
					case FLOAT:
						stat.setFloat(i, (Float) o);
						break;
					case DOUBLE:
						stat.setDouble(i, (Double) o);
						break;
					}
				}
			}
			
			
			stat.execute();
			stat.close();
			
			
			return this;
		} catch (Exception e) {
			System.out.println("Errore Capo.store ->" + e.getMessage());
			throw e;
		}

	}
	
	
	protected static int parseType(Class<?> type) {
		int ret = -1;

		String name = type.getSimpleName();

		if (name.equalsIgnoreCase("int") || name.equalsIgnoreCase("integer")) {
			ret = INT;
		} else if (name.equalsIgnoreCase("string")) {
			ret = STRING;
		} else if (name.equalsIgnoreCase("double")) {
			ret = DOUBLE;
		} else if (name.equalsIgnoreCase("float")) {
			ret = FLOAT;
		} else if (name.equalsIgnoreCase("timestamp")) {
			ret = TIMESTAMP;
		} else if (name.equalsIgnoreCase("date")) {
			ret = DATE;
		} else if (name.equalsIgnoreCase("boolean")) {
			ret = BOOLEAN;
		}

		return ret;
	}
	
	
	public static Passaporto load(int id, Connection db) throws Exception
	{

		Passaporto				ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		boolean closeConnection = false;
		
		if( id > 0 )
		{
			try
			{
			
				if (db == null){
					db = GestoreConnessioni.getConnection();
					closeConnection = true;
				}
				stat	= db.prepareStatement( "SELECT * FROM passaporto p  WHERE p.id = ? and r.trashed_date IS NULL" );
				stat.setInt( 1, id );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res );
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
					
					if (closeConnection){
						GestoreConnessioni.freeConnection(db);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		
	
	
	
	return ret;
	
	}
	
	
	
	public static Passaporto load(String valore_passaporto, Connection db) throws Exception
	{
	    Passaporto ret = null;
	    PreparedStatement stat = null;
	    ResultSet res = null;
	    boolean closeConnection = false;
	    
	    if ((valore_passaporto != null) && (!"".equals(valore_passaporto)))
	    {
	      try
	      {

	        if (db == null) {
	          db = GestoreConnessioni.getConnection();
	          closeConnection = true;
	        }
	        stat = db.prepareStatement("SELECT * FROM get_passaporto_priori(?,?)");
	        stat.setObject(1, null);
	        stat.setString(2, valore_passaporto);
	        res = stat.executeQuery();
	        if (res.next())
	        {
	          ret = loadResultSet(res);
	        }
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	        


	        try
	        {
	          if (res != null)
	          {
	            res.close();
	            res = null;
	          }
	          
	          if (stat != null)
	          {
	            stat.close();
	            stat = null;
	          }
	          
	          if (closeConnection) {
	            GestoreConnessioni.freeConnection(db);
	          }
	        }
	        catch (Exception ex)
	        {
	          ex.printStackTrace();
	        }
	      }
	      finally
	      {
	        try
	        {
	          if (res != null)
	          {
	            res.close();
	            res = null;
	          }
	          
	          if (stat != null)
	          {
	            stat.close();
	            stat = null;
	          }
	          
	          if (closeConnection) {
	            GestoreConnessioni.freeConnection(db);
	          }
	        }
	        catch (Exception e)
	        {
	          e.printStackTrace();
	        }
	      }
	    }
	    




	    return ret;
	    }
	
	public static Passaporto loadResultSet( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			Passaporto ret = new Passaporto();
			
			HashMap<String, Integer> campi = new HashMap<String, Integer>();
			int k = 0;

			for (Field f : Passaporto.class.getDeclaredFields()) {
				Column column = f.getAnnotation(Column.class);
				if (column != null) {
					System.out.println(column.columnName());
					//if (column.table() == null || (nome_tabella).equals(column.table()) )
						campi.put(column.columnName(), column.columnType());
				}
			} 
		
			Method[]	m = ret.getClass().getMethods();
			
			Iterator it = campi.entrySet().iterator();
			while (it.hasNext()) {
				
				Map.Entry pairs = (Map.Entry) it.next();
				System.out.println(pairs.getKey() + " = " + pairs.getValue());

				// for( int i = 0; i < campi.length; i++ )
				// {
				String field = (String) pairs.getKey();
				Method getter	= null;
		    	 Method setter	= null;
		         for( int j = 0; j < m.length; j++ )
		         {
		             String met = m[j].getName();
		             if( met.equalsIgnoreCase("GET" + field.replaceAll("_", "")) || met.equalsIgnoreCase("IS"
								+ field.replaceAll("_", ""))) 
		             {
		                  getter = m[j];
		             }
		             else if(met.equalsIgnoreCase("SET" + field.replaceAll("_", "")) )
		             {
		            	 if (!(( (Integer) pairs.getValue() )  == INT && m[j].getParameterTypes() [0] == String.class) && 
		            			 !(( (Integer) pairs.getValue() )  == TIMESTAMP && m[j].getParameterTypes() [0] == String.class))
		                 setter = m[j];
		             }
		         }	
		     
		     
		         if( (getter != null) && (setter != null) && (field != null) )
		         {
		        	 Object o = null;
		             
		             switch ( ( (Integer) pairs.getValue() ) )
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
	
	
	public static boolean verifyDuplicate(Connection db, String nrPassaporto) throws SQLException {
		String passaporto = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT p.nr_passaporto FROM passaporto p WHERE p.nr_passaporto = ? ");
		pst.setString(1, nrPassaporto);
		rs = pst.executeQuery();

		if (rs.next()) {
			passaporto = rs.getString("nr_passaporto");
		}
		rs.close();
		pst.close();

		if (passaporto != null){
			return false;
		}else{
			return true;
		}
	}
	
	
	public static boolean isAssigned(Connection db, String nrPassaporto) throws SQLException {
		boolean rit = true;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT * FROM animale a WHERE a.numero_passaporto = ? and a.trashed_date is null and a.data_cancellazione is null");
		pst.setString(1, nrPassaporto);
		rs = pst.executeQuery();

		if (rs.next()) {
			rit = false;
			
		}

		rs.close();
		pst.close();
		return rit;
	}
	
	
	
	public static boolean checkMyPassaporto(Connection db, String passaporto, int idAsl) throws SQLException {
		boolean rit = false;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT nr_passaporto as my_p FROM passaporto m WHERE m.nr_passaporto = ? AND data_cancellazione IS NULL");
		if (idAsl > 0){
			query.append(" AND m.id_asl_appartenenza = ?");
		}
		//pst = db.prepareStatement("SELECT nr_passaporto as my_p FROM passaporto m WHERE m.nr_passaporto = ? AND m.id_asl_appartenenza = ? AND data_cancellazione IS NULL");
		pst = db.prepareStatement(query.toString());
		pst.setString(1, passaporto);
		
		if (idAsl > 0){
			pst.setInt(2, idAsl);
		}
		
		rs = pst.executeQuery();

		if (rs.next()) {
			rit = true;
		}

		rs.close();
		pst.close();
		
		return rit;
	}
	
	
	public static boolean isAbilitato(Connection db, String passaporto) throws Exception {
		boolean rit = false;
		
		
		Passaporto thisPassaporto = new Passaporto();
		thisPassaporto.load(passaporto, db);
		if (thisPassaporto.isFlagAbilitato()){
			rit = true;
		}
		
		return rit;
	}
	
	
	public static int eseguiScaricoPassaporto(Connection db, String nrPassaporto ) throws SQLException {
		int resultCount = 0;
		
		PreparedStatement pst = null;
		pst = db.prepareStatement("UPDATE passaporto SET flag_abilitato = false, data_disabilitazione = CURRENT_TIMESTAMP WHERE nr_passaporto = ?");
		pst.setString(1, nrPassaporto);
		resultCount = pst.executeUpdate();

		pst.close();
		
		return resultCount;
	}
	
	public static int assegnaPassaportiUos(Connection db, ArrayList<Integer> passaportiDaAssociare, int idUos ) throws SQLException 
	{
		int resultCount = 0;
		
		PreparedStatement pst = null;
		String update = " UPDATE passaporto SET id_uos = ? WHERE id in  (-1 ";
		Iterator<Integer> iter = passaportiDaAssociare.iterator();
		while(iter.hasNext())
		{
			update += ", ?";
			iter.next();
		}
		update += ") ";
		
		pst = db.prepareStatement(update);
		
		pst.setInt(1, idUos);
		
		iter = passaportiDaAssociare.iterator();
		int i=2;
		while(iter.hasNext())
		{
			pst.setInt(i, iter.next());
			i++;
		}
			
		
		resultCount = pst.executeUpdate();

		pst.close();
		
		return resultCount;
	}
	
	
	public void setPassaportoNonUtilizzato(Connection db) throws SQLException 
	{
		PreparedStatement pst = null;
		int i = 0;
		pst = db.prepareStatement("UPDATE passaporto SET data_modifica = current_timestamp, id_animale=-1, id_specie = null, note_hd = concat(note_hd, '. Passaporto non piu utilizzato da animale con id ' || id_animale || ' dal ' || current_timestamp ) where id = ? ");
	    pst.setInt(++i, this.getId());
		pst.executeUpdate();
		pst.close();
	}
	
	public static void setPassaportiNonUtilizzati(Connection db, String passaporti, int idAnimale ) throws SQLException 
	{
		PreparedStatement pstPass = null;
		
		String queryPassNNUtil="UPDATE passaporto " +
		" SET " +
		" data_modifica = current_timestamp, " +
		" id_animale=-1, " +
		" id_specie = null, note_hd = concat(note_hd, '. Passaporto non piu utilizzato da animale con id ' || " +idAnimale +" || ' dal ' || current_timestamp ) " +
		" where nr_passaporto in  ( "+ passaporti + ") ";
		

		pstPass = db.prepareStatement(queryPassNNUtil);
		pstPass.executeUpdate();
		pstPass.close();	
	}
	
	public void setPassaportoUtilizzato(Connection db) throws SQLException {
		int resultCount = 0;
		
		PreparedStatement pst = null;
		int i = 0;
		pst = db.prepareStatement("UPDATE passaporto   SET  data_utilizzo = CURRENT_TIMESTAMP, "
				+ "  id_utente_utilizzo=?,  id_animale=?, id_specie = ? where id = ? ");
		pst.setInt(++i, this.getIdUtenteUtilizzo());
		pst.setInt(++i, this.getIdAnimale());
		pst.setInt(++i, this.getIdSpecie());
	    pst.setInt(++i, this.getId());
		resultCount = pst.executeUpdate();

		pst.close();
		
	}
	
	
	public static int getTotaliAsl(Connection db, int idAsl) throws SQLException {
		
		int tot = 0;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT COUNT(*) as totale FROM passaporto WHERE  data_cancellazione  IS NULL ");
		
		if (idAsl > 0)
			sqlQuery.append("AND id_asl_appartenenza = ? ");
		pst = db.prepareStatement(sqlQuery.toString());
		
		if (idAsl > 0)
			pst.setInt(1, idAsl);
		rs = pst.executeQuery();

		if (rs.next()) {
			tot = rs.getInt("totale");
		}

		rs.close();
		pst.close();

		return tot;
	}
	
	public static int getTotaliUtilizzatiAsl(Connection db, int idAsl) throws SQLException {
		
		int tot = 0;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT COUNT(*) as totale FROM passaporto WHERE data_utilizzo is not NULL AND data_cancellazione  IS NULL ");
		if (idAsl > 0)
			sqlQuery.append(" AND id_asl_appartenenza = ?");
		pst = db.prepareStatement(sqlQuery.toString());
		
		if (idAsl > 0)
			pst.setInt(1, idAsl);
		
		rs = pst.executeQuery();

		if (rs.next()) {
			tot = rs.getInt("totale");
		}

		rs.close();
		pst.close();

		return tot;
	}
	
	
	
	
	
	public static int getTotaliDisabilitatiAsl(Connection db, int idAsl) throws SQLException {
		
		int tot = 0;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT COUNT(*) as totale FROM passaporto WHERE flag_abilitato is false AND data_cancellazione  IS NULL ");
		
		if (idAsl > 0)
			sqlQuery.append(" AND id_asl_appartenenza = ?");
		
		pst = db.prepareStatement(sqlQuery.toString());
		
		
		if (idAsl > 0)
			pst.setInt(1, idAsl);
		
		rs = pst.executeQuery();

		if (rs.next()) {
			tot = rs.getInt("totale");
		}

		rs.close();
		pst.close();

		return tot;
	}

}
