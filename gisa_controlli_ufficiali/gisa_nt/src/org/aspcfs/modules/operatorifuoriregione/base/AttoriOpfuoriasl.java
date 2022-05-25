/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.operatorifuoriregione.base;

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
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
	import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;

import org.aspcfs.modules.trasportoanimali.base.Personale;
	import org.aspcfs.utils.CsvLunghezzaFissa;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.daffodilwoods.daffodildb.server.sql99.expression.stringvalueexpression.sys_contextfunction;
import com.darkhorseventures.framework.beans.GenericBean;

	public class AttoriOpfuoriasl extends GenericBean
	{
		
		private static final long serialVersionUID = 7127566929908851546L;

		private static final int INT = Types.INTEGER;

		private static final int STRING = Types.VARCHAR;

		private static final int DOUBLE = Types.DOUBLE;

		private static final int FLOAT = Types.FLOAT;

		private static final int TIMESTAMP = Types.TIMESTAMP;

		private static final int DATE = Types.DATE;

		private static final int BOOLEAN = Types.BOOLEAN;
		
		private int entered_by = -1;
		public void setEntered_by(int tmp) {
			    this.entered_by = tmp;
			  }

	    public void setEntered_by(String tmp) {
			    this.entered_by = Integer.parseInt(tmp);
			  }
	    public int getEntered_by() {
	        return entered_by;
	      }

		private Timestamp entered = null;
		
		public void setEntered(java.sql.Timestamp tmp) {
		    this.entered = tmp;
		  }
		public void setEntered(String tmp) {
			    this.entered = DateUtils.parseTimestampString(tmp);
			  }
		 
	    public java.sql.Timestamp getEntered() {
	        return entered;
	      }

		private int modified_by = -1;
		public void setModified_by(int modifiedBy) {
			    this.modified_by = modifiedBy;
			  }
       public void setModified_by(String modifiedBy) {
			    this.modified_by = Integer.parseInt(modifiedBy);
			  }
       public int getModified_by() {
    	    return modified_by;
    	  }
		private Timestamp modified= null;
		public void setModified(java.sql.Timestamp tmp) {
			    this.modified = tmp;
			  }
		public void setModified(String tmp) {
		    this.modified = DateUtils.parseTimestampString(tmp);
		  }
		public java.sql.Timestamp getModified() {
		    return modified;
		  }
		private int tipologia = -1;
		public int getTipologia() {
			return tipologia;
		}

		public void setTipologia(int tipologia) {
			this.tipologia = tipologia;
		}
		private String ragione_sociale =null;
		public String getRagione_sociale() {
			return ragione_sociale;
		}

		public void setRagione_sociale(String ragione_sociale) {
			this.ragione_sociale = ragione_sociale;
		}
		private String merce =null;
		public String getMerce() {
			return merce;
		}

		public void setMerce(String merce) {
			this.merce = merce;
		}
		private String cognome =null;
		public String getCognome() {
			return cognome;
		}

		public void setCognome(String cognome) {
			this.cognome = cognome;
		}		
		private String nome =null;
		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}	
		private Timestamp data_nascita =null;
		public void setData_nascita(java.sql.Timestamp tmp) {
		    this.data_nascita = tmp;
		  }
	 
	
	   public java.sql.Timestamp getData_nascita() {
	    return data_nascita;
	  }
		private String luogo_nascita =null;
		public String getLuogo_nascita() {
			return luogo_nascita;
		}
		
		  public void setData_nascita(String tmp) {
			   this.data_nascita = DateUtils.parseDate(tmp);   
		  }

		public void setLuogo_nascita(String luogo_nascita) {
			this.luogo_nascita = luogo_nascita;
		}	
		private String comune =null;
		public String getComune() {
			return comune;
		}

		public void setComune(String comune) {
			this.comune = comune;
		}
		private String indirizzo =null;
		public String getIndirizzo() {
			return indirizzo;
		}

		public void setIndirizzo(String indirizzo) {
			this.indirizzo = indirizzo;
		}
		private String provincia =null;
		public String getProvincia() {
			return provincia;
		}

		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}
		private String documento =null;
		public String getDocumento() {
			return documento;
		}

		public void setDocumento(String documento) {
			this.documento = documento;
		}
		private String note =null;
		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}
		private int org_id;
		
		public int getOrg_id() {
			return org_id;
		}

		public void setOrg_id(int org_id) {
			this.org_id = org_id;
		}

		private int id = -1;
		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		
		public static Map<String, AttoriOpfuoriasl> getPresidentsByUniqueIds(int orgid,Connection db){
			Map<String, AttoriOpfuoriasl> m=new HashMap<String, AttoriOpfuoriasl>();
			
			
			try{
				
				java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement("select * from attori_fuoriasl where org_id=?");
				
				pst.setInt(1, orgid);
				ResultSet rs=pst.executeQuery();
				while(rs.next()){
					if(rs.getInt("tipologia")==1){
					String luogo_nascita=rs.getString("luogo_nascita");
				    Timestamp data_nascita=rs.getTimestamp("data_nascita");
					String nome=rs.getString("nome");
					String cognome=rs.getString("cognome");
					String documento=rs.getString("documento");
					String comune=rs.getString("comune");
					String indirizzo=rs.getString("indirizzo");
					String provincia=rs.getString("provincia");
					AttoriOpfuoriasl dist=new AttoriOpfuoriasl(cognome,nome,data_nascita,luogo_nascita,comune,indirizzo,provincia,documento);
					m.put(documento, dist);
					}else{
						String ragione_sociale=rs.getString("ragione_sociale");
						String note=rs.getString("note");
						String comune=rs.getString("comune");
						String indirizzo=rs.getString("indirizzo");
						String provincia=rs.getString("provincia");
						String merce = rs.getString("merce");
						AttoriOpfuoriasl dist=new AttoriOpfuoriasl(ragione_sociale,comune,indirizzo,provincia,note);
						m.put(ragione_sociale, dist);
					}
					
				}
				
				
				
				
				
				
				
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			
			return m;
		}
		
		 public AttoriOpfuoriasl() { }
		
		public void updateAttoriopfuoriasl(Connection db, int idA, int orgId){
			
			try{
							
					String sql="update attori_opfuoriasl set modified= ?,modified_by=? , comune=? , indirizzo=? , provincia=?,cognome=?, nome=?, ragione_sociale=?,merce=?, luogo_nascita=?, data_nascita=?, documento=?,note=? ";
					sql = sql + " where id=? and org_id=?";
					java.sql.PreparedStatement pst=(java.sql.PreparedStatement) db.prepareStatement(sql);
					Timestamp dataModifica = new Timestamp(System.currentTimeMillis());
					pst.setTimestamp(1, dataModifica);
					pst.setInt(2, this.modified_by);
					pst.setString(3, this.comune);
					pst.setString(4, this.indirizzo);
					pst.setString(5, this.provincia);
					pst.setString(6, this.cognome);
					pst.setString(7, this.nome);
					pst.setString(8, this.ragione_sociale);
					pst.setString(9, this.merce);
					pst.setString(10, this.luogo_nascita);
					pst.setTimestamp(11, data_nascita);
					pst.setString(12, this.documento);
					pst.setString(13, this.note);
					pst.setInt(14, idA);
					pst.setInt(15, orgId);				
					pst.execute();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
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

	     private static Timestamp parseDate( String date )
	     {
	    	 Timestamp ret = null;
	    	 
	    	 SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
	    	 try 
	    	 {
				ret = new Timestamp( sdf.parse( date ).getTime() );
	    	 }
	    	 catch (ParseException e)
	    	 {
				e.printStackTrace();
	    	 }
	    	 
	    	 return ret;
	     }
	     public AttoriOpfuoriasl (String cognome,String nome,Timestamp data_nascita,String luogo_nascita,String comune,String indirizzo,String provincia,String documento){
	    		
	 		this.setCognome(cognome);
	 		this.setNome(nome);
	 		this.setData_nascita(data_nascita);
	 		this.setLuogo_nascita(luogo_nascita);
	 		this.setComune(comune);
	 		this.setIndirizzo(indirizzo);
	 		this.setProvincia(provincia);
	 		this.setDocumento(documento);
	 				
	 	}
	     
	     public AttoriOpfuoriasl (String ragione_sociale,String comune,String indirizzo,String provincia,String note){
	    		
		 		this.setRagione_sociale(ragione_sociale);
		 		this.setComune(comune);
		 		this.setIndirizzo(indirizzo);
		 		this.setProvincia(provincia);
		 		this.setNote(note);
		 		this.setMerce(merce);
		 				
		 	}
	     private static Logger log = Logger.getLogger(org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl.class);
	     static {
	       if (System.getProperty("DEBUG") != null) {
	         log.setLevel(Level.DEBUG);
	       }
	     }
	     
	     public AttoriOpfuoriasl(Connection db, int orgId, int tipo) throws SQLException {
	    		
	    	    if (orgId == -1) {
	    	      throw new SQLException("Invalid Account");
	    	    } 
	    	    PreparedStatement pst = db.prepareStatement("SELECT * FROM attori_opfuoriasl WHERE org_id = ? and tipologia = ?;");
	    	    pst.setInt(1, orgId);
	    	    pst.setInt(2, tipo);
	    	    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
	    	    if (rs.next()) {
	    	      buildRecord(rs);
	    	    }
	    	    rs.close();
	    	    pst.close();
	    	    if (orgId == -1) {
	    	      throw new SQLException(Constants.NOT_FOUND_ERROR);
	    	    }
	    	      
	    	  }
	     
	     protected void buildRecord(ResultSet rs) throws SQLException {
	    	    //organization table
	    	    this.setId(rs.getInt("id"));
	    	    ragione_sociale = rs.getString("ragione_sociale");
	    	    merce = rs.getString("merce");
	    	    cognome = rs.getString("cognome");
	    	    nome = rs.getString( "nome");
	    	    documento = rs.getString("documento");
	    	    note = rs.getString("note");
	    	    data_nascita = rs.getTimestamp("data_nascita");
	    	    luogo_nascita = rs.getString("luogo_nascita");
	    	    comune = rs.getString("comune");
	    	    indirizzo = rs.getString("indirizzo");
	    	    provincia = rs.getString("provincia");
	     	}
	 		 	
		public void insert(Connection db,int org_id, int tipologia ) throws SQLException {
			  StringBuffer sql_1 = new StringBuffer();
			  StringBuffer sql_2 = new StringBuffer();
			  StringBuffer sql = new StringBuffer();
			  boolean doCommit = false;
	 		try{
	 			 modified_by = entered_by;
	 			 entered = new Timestamp(System.currentTimeMillis());
	 			 modified = entered;
	 			if (doCommit = db.getAutoCommit()) {
	 		        db.setAutoCommit(false);
	 		      }
	 		    id = DatabaseUtils.getNextSeqTipo(db, "attori_opfuoriasl_id_seq");
	 			
	 			sql_1.append("INSERT INTO attori_opfuoriasl (id, entered, entered_by, modified, modified_by, tipologia, org_id ");
	 			sql_2.append(" VALUES (?,?,?,?,?,?,? ");
	 			
	 			if (this.nome!=null){
	 		    	  sql_1.append(",nome");
	 		    	  sql_2.append(",?");
	 		      }
	 		      if (this.cognome!=null){
	 		    	  sql_1.append(",cognome");
	 		    	  sql_2.append(",?");
	 		      }
	 		      if ((data_nascita != null)&&(!data_nascita.equals(""))) {
	 		    	  sql_1.append(",data_nascita");
	 		    	  sql_2.append(",?");
	 		      }
	 		      if (this.luogo_nascita!=null){
	 		    	  sql_1.append(",luogo_nascita");
	 		    	  sql_2.append(",?");
	 		      }
	 		    
	 		    if (this.ragione_sociale!=null){
	 		    	  sql_1.append(",ragione_sociale");
	 		    	  sql_2.append(",?");
	 		      }
	 		   if (this.merce!=null){
	 		    	  sql_1.append(",merce");
	 		    	  sql_2.append(",?");
	 		      }
	 		      if (this.comune!=null){
	 		    	  sql_1.append(",comune");
	 		    	  sql_2.append(",?");
	 		      }
	 		     if (this.indirizzo!=null){
	 		    	  sql_1.append(",indirizzo");
	 		    	  sql_2.append(",?");
	 		      }
	 		      if (this.provincia!=null){
	 		    	  sql_1.append(",provincia");
	 		    	  sql_2.append(",?");
	 		      }
	 		     if (this.documento!=null){
	 		    	  sql_1.append(",documento");
	 		    	  sql_2.append(",?");
	 		      }
	 		      if (this.note!=null){
	 		    	  sql_1.append(",note");
	 		    	  sql_2.append(",?");
	 		      }
	 		      sql_1.append(")");
	 		      sql_2.append(")");
	 		      sql.append(sql_1);
	 		      sql.append(sql_2);
	 		     int i = 0;
	 		     PreparedStatement pst = db.prepareStatement(sql.toString());
	 		     pst.setInt(++i, id);
	 		     DatabaseUtils.setTimestamp(pst, ++i, this.entered);
	 		     pst.setInt(++i, this.entered_by);
	 		     DatabaseUtils.setTimestamp(pst, ++i, this.modified);
	 		     pst.setInt(++i, this.modified_by);
	 		     pst.setInt(++i, this.tipologia);
	 		     pst.setInt(++i, this.org_id);
	 		    if (this.nome!=null){
	 		    	  pst.setString(++i, nome);
	 		      }
	 		      if (this.cognome!=null){
	 		    	 pst.setString(++i, cognome);
	 		      }
	 		      if ((data_nascita != null)&&(!data_nascita.equals(""))) {
	 		    	 DatabaseUtils.setTimestamp(pst, ++i, this.data_nascita);
	 		      }
	 		      if (this.luogo_nascita!=null){
	 		    	 pst.setString(++i,luogo_nascita);
	 		      }
	 		    
	 		    if (this.ragione_sociale!=null){
	 		    	 pst.setString(++i, ragione_sociale);
	 		      }
	 		   if (this.merce!=null){
	 		    	 pst.setString(++i, merce);
	 		      }
	 		      if (this.comune!=null){
	 		    	 pst.setString(++i, comune);
	 		      }
	 		     if (this.indirizzo!=null){
	 		    	 pst.setString(++i, indirizzo);
	 		      }
	 		      if (this.provincia!=null){
	 		    	 pst.setString(++i, provincia);
	 		      }
	 		     if (this.documento!=null){
	 		    	 pst.setString(++i, documento);
	 		      }
	 		      if (this.note!=null){
	 		    	 pst.setString(++i, note);
	 		      }
	 			 pst.execute();
	 		     pst.close();

	 		   if (doCommit) {
		          db.commit();
		        }
		      } catch (SQLException e) {
		        if (doCommit) {
		          db.rollback();
		        }
		        throw new SQLException(e.getMessage());
		      } finally {
		        if (doCommit) {
		          db.setAutoCommit(true);
		        }
		      }	
	 			 		
	 	}
	

}
