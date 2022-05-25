/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.laboratorihaccp.base;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Prova extends GenericBean {

	  private static Logger log = Logger.getLogger(org.aspcfs.modules.laboratorihaccp.base.ElencoProve.class);
	  static {
	    if (System.getProperty("DEBUG") != null) {
	      log.setLevel(Level.DEBUG);
	    }
	  }
	  
	  
	  private int id = -1;
	  private int orgId ;
	  private int codiceMatrice ;
	  private int codiceDenominazione = -1;
	  private int codiceEnte = -1;
	  private String norma = null;
	  private String decreto = null;
	  private boolean accreditata = false;
	  private int enteredBy = -1;
	  private java.sql.Timestamp entered = null;
	  private java.sql.Timestamp modified = null;
	  private int modifiedBy = -1;
	  private java.sql.Timestamp trashedDate = null;
	  private boolean enabled = false;
	  private boolean prova_originaria = false;
	  private int id_prova = -1;
	  private HashMap<Integer, Object> listaCampiModificati = null;
	  
	  
		public int getId() {
			    return id;
			  }
	
	    public void setId(int id) {
	    	this.id = id;
				
			}
	    
	    public int getIdProva() {
		    return id_prova;
		  }

	    public void setIdProva(int id) {
    	this.id_prova = id;
			
		}
	    
	    public boolean getProvaOriginaria() {
		    return prova_originaria;
		  }

	    public void setProvaOriginaria(boolean p) {
    	this.prova_originaria = p;
			
		}
		
		public int getOrgId() {
				return orgId;
			}


		public void setOrgId(int orgId) {
				this.orgId = orgId;
			}
		
		public void setOrgId(String orgId) {
			this.orgId = Integer.parseInt(orgId);
		}
			
		public int getCodiceMatrice() {
				return codiceMatrice;
			}
	
	
		public void setCodiceMatrice(int codiceMatrice) {
				this.codiceMatrice = codiceMatrice;
			}
	
		public void setCodiceMatrice(String codiceMatrice) {
			this.codiceMatrice = Integer.parseInt(codiceMatrice);
		}
	
		public int getCodiceDenominazione() {
			return codiceDenominazione;
		}
	
	
		public void setCodiceDenominazione(int codiceDenominazione) {
			this.codiceDenominazione = codiceDenominazione;
		}
	
		public void setCodiceDenominazione(String codiceDenominazione) {
			this.codiceDenominazione = Integer.parseInt(codiceDenominazione);
		}

		
		public int getCodiceEnte() {
			return codiceEnte;
		}
	
	
		public void setCodiceEnte(int codiceEnte) {
			this.codiceEnte = codiceEnte;
		}
	
		public void setCodiceEnte(String codiceEnte) {
			this.codiceEnte = Integer.parseInt(codiceEnte);
		}

	  
		public String getNorma() {
			return norma;
		}



		public void setNorma(String norma) {
			this.norma = norma;
		}
		
		public boolean getAccreditata() {
			return accreditata;
		}


		public void setAccreditata(boolean accreditata) {
			this.accreditata = accreditata;
		}
		
		public void setCampiModificati(HashMap<Integer, Object> o) {
			// TODO Auto-generated method stub
			this.listaCampiModificati = o;
		}
		
		 /**
		   *  Gets the EnteredBy attribute of the Organization object
		   *
		   * @return    The EnteredBy value
		   */
		  public int getEnteredBy() {
		    return enteredBy;
		  }

		  public void setEnteredBy(int enteredby) {
				this.enteredBy = enteredby;
			}
		  
		  public void setModifiedBy(int modifiedBy) {
				this.modifiedBy = modifiedBy;
			}

		  /**
		   *  Gets the Entered attribute of the Organization object
		   *
		   * @return    The Entered value
		   */
		  public java.sql.Timestamp getEntered() {
		    return entered;
		  }
		  
		  public void setEntered(java.sql.Timestamp data) {
			    this.entered = data;
			  }
		  
		  public void setModified(java.sql.Timestamp data) {
			    this.modified = data;
			  
		  }
		  
		  public Object getCampiModificati(){
			  return listaCampiModificati; 
		  }
		  
		  /**
		   *  Gets the Modified attribute of the Organization object
		   *
		   * @return    The Modified value
		   */
		  public java.sql.Timestamp getModified() {
		    return modified;
		  }

		  public String getDecreto() {
				return decreto;
			}

			public void setDecreto(String decreto) {
				this.decreto = decreto;
			}

		  /**
		   *  Gets the ModifiedString attribute of the Organization object
		   *
		   * @return    The ModifiedString value
		   */
		  public String getModifiedString() {
		    String tmp = "";
		    try {
		      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
		          modified);
		    } catch (NullPointerException e) {
		    }
		    return tmp;
		  }


		  /**
		   *  Gets the EnteredString attribute of the Organization object
		   *
		   * @return    The EnteredString value
		   */
		  public String getEnteredString() {
		    String tmp = "";
		    try {
		      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
		          entered);
		    } catch (NullPointerException e) {
		    }
		    return tmp;
		  }

		  /**
		   *  Gets the Enteredby attribute of the Organization object
		   *
		   * @return    The Enteredby value
		   */
		  public int getEnteredby() {
		    return enteredBy;
		  }


		  /**
		   *  Gets the ModifiedBy attribute of the Organization object
		   *
		   * @return    The ModifiedBy value
		   */
		  public int getModifiedBy() {
		    return modifiedBy;
		  }

		  /**
		   *  Gets the trashed attribute of the Organization object
		   *
		   * @return    The trashed value
		   */
		  public boolean isTrashed() {
		    return (trashedDate != null);
		  }
		  
		  /**
		   *  Description of the Method
		   *
		   * @param  db             Description of Parameter
		   * @return                Description of the Returned Value
		   * @throws  SQLException  Description of Exception
		   */
		  public boolean enable(Connection db) throws SQLException {
		    if (this.getOrgId() == -1) {
		      throw new SQLException("Laboratorio ID not specified");
		    }

		    PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer();
		    boolean success = false;

		    sql.append(
		        "UPDATE laboratorihaccp_elenco_prove SET enabled = ? " +
		        "WHERE id = ? ");
		    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
		    int i = 0;
		    pst = db.prepareStatement(sql.toString());
		    pst.setBoolean(++i, true);
		    pst.setInt(++i, id);
		    if(this.getModified() != null){
		      pst.setTimestamp(++i, this.getModified());
		    }
		    int resultCount = pst.executeUpdate();
		    pst.close();
		    if (resultCount == 1) {
		      success = true;
		    }
		    return success;
		  }
		 
		/**
		   *  Constructor for the object, creates an empty Organization
		   *
		   * @since    1.0
		   */
		  public Prova() { }


		  /**
		   *  Constructor for the object
		   *
		   * @param  rs                Description of Parameter
		   * @exception  SQLException  Description of the Exception
		   * @throws  SQLException     Description of the Exception
		   * @throws  SQLException     Description of Exception
		   */
		 public Prova(ResultSet rs) throws SQLException {
		    buildRecord(rs);
		  }
		 
		 public Prova(Connection db, int parseInt) throws SQLException {
				
			  
			    if (parseInt == -1) {
			      throw new SQLException("Invalid Account");
			    } 
			    PreparedStatement pst = db.prepareStatement(
			        "SELECT lep.* " +
			        "FROM laboratorihaccp_elenco_prove lep " +
			        "WHERE  lep.id = ? " );
			    pst.setInt(1, parseInt);
			    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
			    if (rs.next()) {
			      buildRecord(rs);
			      
			       }
			    rs.close();
			    pst.close();
			    if (id == -1) {
			      throw new SQLException(Constants.NOT_FOUND_ERROR);
			    }
			
		}
		 

		protected void buildRecord(ResultSet rs) throws SQLException {
			    
			    this.setId(rs.getInt("id"));
			    orgId = rs.getInt("org_id");
			    codiceMatrice = rs.getInt("codice_matrice");
			    codiceDenominazione = rs.getInt("codice_denominazione");
			    codiceEnte = rs.getInt("codice_ente");
			    norma = rs.getString("norma");
			    accreditata  = rs.getBoolean("accreditata");
			    entered = rs.getTimestamp("entered");
			    enteredBy = rs.getInt("entered_by");
			    modified = rs.getTimestamp("modified");
			    modifiedBy = rs.getInt("modified_by");
			    trashedDate = rs.getTimestamp("trashed_date");
			    enabled  = rs.getBoolean("enabled");
			    decreto = rs.getString("decreto");
			    
			    boolean esiste_colonna_prova = existColumnRs(rs,"prova_originaria") ;
			    if(esiste_colonna_prova==true)
			    {
			    	prova_originaria = rs.getBoolean("prova_originaria");
			    }
			    
			    boolean esiste_colonna_id_prova = existColumnRs(rs,"id_prova") ;
			    if(esiste_colonna_id_prova==true)
			    {
			    	id_prova = rs.getInt("id_prova");
			    }
			   
			  }
		
		 private boolean existColumnRs(ResultSet rs,String nomeColumn) throws SQLException
		 {
			 	int num_col = rs.getMetaData().getColumnCount();
			    boolean esiste = false ;
			    for (int i = 1 ; i<=num_col; i++)
			    {
			    	if(rs.getMetaData().getColumnName(i).equals(nomeColumn))
			    	{
			    		esiste = true ;
			    		break ;
			    	}
			    }
			    return esiste ;
		 }
		 /*Insert new Lab HACCP*/
			public boolean insert(Connection db,ActionContext context) throws SQLException {
			    StringBuffer sql = new StringBuffer();
			    boolean doCommit = false;
			    try {
			      modifiedBy = enteredBy;
			      
			      if (doCommit = db.getAutoCommit()) {
			        db.setAutoCommit(false);
			      }
			      
			      id =  DatabaseUtils.getNextSeq(db,context, "laboratorihaccp_elenco_prove","id");

			      
			      sql.append(
			          "INSERT INTO laboratorihaccp_elenco_prove ( id, org_id, codice_matrice, codice_denominazione, codice_ente, ");
			      
			      if (norma != null) {
			          sql.append("norma, ");
			        }
			          
			      sql.append("accreditata, entered, entered_by, modified, modified_by, trashed_date, enabled ");
			      
			      sql.append(")");
			      sql.append(" VALUES (");
			      sql.append("?, ?, ?, ?, ?, ");
			      
			      if (norma != null) {
			          sql.append("?, ");
			        }
			      		      
			      sql.append("?, ?, ?, ?, ?,?, ?);");
			      int i = 0;
			      PreparedStatement pst = db.prepareStatement(sql.toString());
			      pst.setInt(++i, this.getId());
			      pst.setInt(++i, this.getOrgId());
			      pst.setInt(++i, this.getCodiceMatrice());
			      pst.setInt(++i, this.getCodiceDenominazione());
			      pst.setInt(++i, this.getCodiceEnte());
			      if (norma != null) {
			          pst.setString(++i, norma);
			        }
			      pst.setBoolean(++i, this.getAccreditata());
			      DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
			      pst.setInt(++i, this.getEnteredBy());
			      DatabaseUtils.setTimestamp(pst, ++i, this.getModified());
			      pst.setInt(++i, this.getModifiedBy());
			      DatabaseUtils.setTimestamp(pst, ++i, null);
			      pst.setBoolean(++i, true);
			      pst.execute();
			      pst.close();
			      
			     
			      if (doCommit) {
			        db.commit();
			      }
			    } catch (SQLException e) {
			    	e.printStackTrace();
			      if (doCommit) {
			        db.rollback();
			      }
			      throw new SQLException(e.getMessage());
			    } finally {
			      if (doCommit) {
			        db.setAutoCommit(true);
			      }
			    }
			    return true;
			  }

			/*Insert new Lab HACCP*/
			public boolean insertStorico(Connection db) throws SQLException {
			    StringBuffer sql = new StringBuffer();
			    boolean doCommit = false;
			    try {
			      modifiedBy = enteredBy;
			      
			      if (doCommit = db.getAutoCommit()) {
			        db.setAutoCommit(false);
			      }
			      
			      //id = DatabaseUtils.getNextSeqInt(db, "laboratorihaccp_id_seq");
			      
			      sql.append(
			          "INSERT INTO laboratorihaccp_storico_elenco_prove ( id_prova, org_id, codice_matrice, codice_denominazione, codice_ente, prova_originaria,  ");
			      
			      if (norma != null) {
			          sql.append("norma, ");
			        }
			          
			      sql.append(" accreditata, entered, entered_by, modified, modified_by, enabled ");
			      
			      sql.append(")");
			      sql.append(" VALUES (");
			      sql.append("?, ?, ?, ?, ?, ?, ");
			      
			      if (norma != null) {
			          sql.append("?, ");
			        }
			      		      
			      sql.append("?, ?, ?, ?, ?, ?);");
			      int i = 0;
			      PreparedStatement pst = db.prepareStatement(sql.toString());
			      pst.setInt(++i, this.getId());
			      pst.setInt(++i, this.getOrgId());
			      pst.setInt(++i, this.getCodiceMatrice());
			      pst.setInt(++i, this.getCodiceDenominazione());
			      pst.setInt(++i, this.getCodiceEnte());
			      pst.setBoolean(++i, true);
			      if (norma != null) {
			          pst.setString(++i, norma);
			        }
			      pst.setBoolean(++i, this.getAccreditata());
			      DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
			      pst.setInt(++i, this.getEnteredBy());
			      DatabaseUtils.setTimestamp(pst, ++i, this.getModified());
			      pst.setInt(++i, this.getModifiedBy());
			      pst.setBoolean(++i, true);
			      pst.execute();
			      pst.close();
			      
			     
			      if (doCommit) {
			        db.commit();
			      }
			    } catch (SQLException e) {
			    	e.printStackTrace();
			      if (doCommit) {
			        db.rollback();
			      }
			      throw new SQLException(e.getMessage());
			    } finally {
			      if (doCommit) {
			        db.setAutoCommit(true);
			      }
			    }
			    return true;
			  }

			
			
			/*Modifica*/
			
			public int update(Connection db) throws SQLException {
			    int i = -1;
			    boolean doCommit = false;
			    try {
			      if (doCommit = db.getAutoCommit()) {
			        db.setAutoCommit(false);
			      }
			      i = this.update(db, false);
			   
			      
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
			    return i;
			  }
			
			 public int update(Connection db, boolean override) throws SQLException {
				    int resultCount = 0;
				    PreparedStatement pst = null;
				    StringBuffer sql = new StringBuffer();
				    sql.append(
				        "UPDATE laboratorihaccp_elenco_prove " +
				        "SET codice_matrice = ?, codice_denominazione = ? , " +
				         " codice_ente= ? , norma = ?, " +
				        "accreditata = ?, decreto = ?, ");
				   
				    if (!override) {
				      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
				    }

				    sql.append(
				        "modified_by = ? " );
				    sql.append(" WHERE id = ? ");
				  
				    int i = 0;
				    pst = db.prepareStatement(sql.toString());
				    pst.setInt(++i, codiceMatrice);
				    pst.setInt(++i, codiceDenominazione);
				    pst.setInt(++i, codiceEnte);
				    pst.setString(++i, norma);
				    pst.setBoolean(++i, accreditata);
				    pst.setString(++i, decreto);
				    pst.setInt(++i, this.getModifiedBy());
				    pst.setInt(++i, this.getId());
				    resultCount=pst.executeUpdate();
				    pst.close();

				    return resultCount;
				  }
			
			
			 public boolean delete(Connection db) throws SQLException {
				    if (this.getId() == -1) {
				      throw new SQLException("Prova ID not specified.");
				    }
				    boolean commit = db.getAutoCommit();
				    try { 
				      if (commit) {
				        db.setAutoCommit(false);
				      }
				 
				      Statement st = db.createStatement();
				      st.executeUpdate(
				          "UPDATE laboratorihaccp_elenco_prove SET trashed_date = current_date WHERE id = " + this.getId());
				      st.close();
				      if (commit) {
				        db.commit();
				      }
				    } catch (SQLException e) {
				      e.printStackTrace(System.out);
				      if (commit) {
				        db.rollback();
				      }
				      throw new SQLException(e.getMessage());
				    } finally {
				      if (commit) {
				        db.setAutoCommit(true);
				      }
				    }
				    return true;
				  }
			 
			 public boolean delete(Connection db, int idProva) throws SQLException {
				    if (idProva == -1) {
				      throw new SQLException("Prova ID not specified.");
				    }
				    boolean commit = db.getAutoCommit();
				    try { 
				      if (commit) {
				        db.setAutoCommit(false);
				      }
				 
				      Statement st = db.createStatement();
				      st.executeUpdate(
				          "UPDATE laboratorihaccp_elenco_prove SET trashed_date = current_date WHERE id = " + idProva);
				      st.close();
				      if (commit) {
				        db.commit();
				      }
				    } catch (SQLException e) {
				      e.printStackTrace(System.out);
				      if (commit) {
				        db.rollback();
				      }
				      throw new SQLException(e.getMessage());
				    } finally {
				      if (commit) {
				        db.setAutoCommit(true);
				      }
				    }
				    return true;
				  }

			
}
