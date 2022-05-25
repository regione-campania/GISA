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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class ElencoProve extends Vector<Prova> implements SyncableList {

	  private static Logger log = Logger.getLogger(org.aspcfs.modules.laboratorihaccp.base.ElencoProve.class);
	  static {
	    if (System.getProperty("DEBUG") != null) {
	      log.setLevel(Level.DEBUG);
	    }
	  }
	  
	  protected PagedListInfo pagedListInfo = null;
	  
	  private static final long serialVersionUID = 2268314721560915731L;
	  private int id = -1;
	  private int orgId ;
	  private int codiceMatrice ;
	  private int codiceDenominazione = -1;
	  private String norma = null;
	  private boolean accreditata = false;
	  private int enteredBy = -1;
	  private java.sql.Timestamp entered = null;
	  private java.sql.Timestamp modified = null;
	  private int modifiedBy = -1;
	  private java.sql.Timestamp trashedDate = null;
	  private boolean enabled = false;
	  
	  
	  
	  public PagedListInfo getPagedListInfo() {
		  return pagedListInfo;
	  }

	  public void setPagedListInfo(PagedListInfo pagedListInfo) {
		  this.pagedListInfo = pagedListInfo;
	  }

		public int getId() {
			    return id;
			  }
	
	    private void setId(int id) {
	    	this.id = id;
				
			}
		
		public int getOrgId() {
				return orgId;
			}


		public void setOrgId(int orgId) {
				this.orgId = orgId;
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
		
		 /**
		   *  Gets the EnteredBy attribute of the Organization object
		   *
		   * @return    The EnteredBy value
		   */
		  public int getEnteredBy() {
		    return enteredBy;
		  }


		  /**
		   *  Gets the Entered attribute of the Organization object
		   *
		   * @return    The Entered value
		   */
		  public java.sql.Timestamp getEntered() {
		    return entered;
		  }
		  
		  /**
		   *  Gets the Modified attribute of the Organization object
		   *
		   * @return    The Modified value
		   */
		  public java.sql.Timestamp getModified() {
		    return modified;
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
		  public ElencoProve() { }


		  /**
		   *  Constructor for the object
		   *
		   * @param  rs                Description of Parameter
		   * @exception  SQLException  Description of the Exception
		   * @throws  SQLException     Description of the Exception
		   * @throws  SQLException     Description of Exception
		   */
		 public ElencoProve(ResultSet rs) throws SQLException {
		    buildRecord(rs);
		  }
		  
		 
		 /**
		   *  Description of the Method
		   *
		   * @param  rs             Description of Parameter
		   * @throws  SQLException  Description of Exception
		   */
		  protected void buildRecord(ResultSet rs) throws SQLException {
		    
		    this.setId(rs.getInt("id"));
		    orgId = rs.getInt("org_id");
		    codiceMatrice = rs.getInt("codice_matrice");
		    codiceDenominazione = rs.getInt("codice_denominazione");
		    norma = rs.getString("norma");
		    accreditata  = rs.getBoolean("accreditata");
		    entered = rs.getTimestamp("entered");
		    enteredBy = rs.getInt("enteredby");
		    modified = rs.getTimestamp("modified");
		    modifiedBy = rs.getInt("modifiedby");
		    trashedDate = rs.getTimestamp("trashed_date");
		    enabled  = rs.getBoolean("enabled");
		  
		  }

		

		public boolean updateStatus(Connection db, ActionContext context, boolean toTrash, int tmpUserId) throws SQLException {
			    int count = 0;

			    boolean commit = true;

			    try {
			      commit = db.getAutoCommit();
			      if (commit) {
			        db.setAutoCommit(false);
			      }
			      StringBuffer sql = new StringBuffer();
			      sql.append(
			          "UPDATE laboratorihaccp_elenco_prove " +
			          "SET trashed_date = ?, " +
			          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
			          "modifiedby = ? " +
			          "WHERE id = ? ");
			      int i = 0;
			      PreparedStatement pst = db.prepareStatement(sql.toString());
			      if (toTrash) {
			        DatabaseUtils.setTimestamp(
			            pst, ++i, new Timestamp(System.currentTimeMillis()));
			      } else {
			        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
			      }
			      pst.setInt(++i, tmpUserId);
			      pst.setInt(++i, this.getId());
			      count = pst.executeUpdate();
			      pst.close();
			     		      
			      if (commit) {
			        db.commit();
			      }
			    } catch (SQLException e) {
			      e.printStackTrace();
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
		   
		  public Prova getObject(ResultSet rs) throws SQLException {
			  Prova thisOrganization = new Prova(rs);
		    return thisOrganization;
		  }
		
		
		public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    ResultSet rs = queryList(db, pst);
		    while (rs.next()) {
		      Prova thisOrganization = this.getObject(rs);
		      
		        this.add(thisOrganization);
		      
		    }
		    rs.close();
		    if (pst != null) {
		      pst.close();
		    }
		    buildResources(db);
		  }
		
		public void buildListStorico(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    ResultSet rs = queryListStorico(db, pst);
		    while (rs.next()) {
		      Prova thisOrganization = this.getObject(rs);
		      this.add(thisOrganization);
		      
		    }
		    rs.close();
		    if (pst != null) {
		      pst.close();
		    }
		    buildResources(db);
		  }
		
		
		
		
		public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		    ResultSet rs = null;
		    int items = -1;

		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlFilter = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();

		    //Need to build a base SQL statement for counting records
		    sqlSelect.append(
		        " SELECT lep.* " +
		        " FROM laboratorihaccp_elenco_prove lep" +
		        " WHERE lep.org_id = ? and lep.trashed_date is null ");
		   
		    pst = db.prepareStatement(
		    sqlSelect.toString());
		    pst.setInt(1, this.getOrgId());
		    rs = DatabaseUtils.executeQuery(db, pst, log);
		    
		    return rs;
		  }
		
		
		public ResultSet queryListStorico(Connection db, PreparedStatement pst) throws SQLException {
		    ResultSet rs = null;
		    int items = -1;

		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();

		    //Need to build a base SQL statement for counting records
		    
		   //Need to build a base SQL statement for counting records
		    sqlCount.append(
		        " SELECT COUNT(*) AS recordcount " +
		        " FROM laboratorihaccp_storico_elenco_prove p join laboratorihaccp_elenco_prove l "+
                " on p.org_id = l.org_id WHERE p.org_id = ? and prova_originaria = ? and l.trashed_date is null " );	
		    
		    sqlOrder.append(" ORDER BY modified desc ");
		    if (pagedListInfo != null) {
			      //Get the total number of records matching filter
				 pst = db.prepareStatement( sqlCount.toString());
				 pst.setInt(1, this.getOrgId());
				 pst.setBoolean(2, false);
			     rs = pst.executeQuery();
			     if (rs.next()) {
			        int maxRecords = rs.getInt("recordcount");
			        pagedListInfo.setMaxRecords(maxRecords);
			     }
			     rs.close();
			     pst.close();
			     pagedListInfo.appendSqlTail(db, sqlOrder);
			     
		    }   
		    /*select * from laboratorihaccp_storico_elenco_prove p join laboratorihaccp_elenco_prove l
			  on p.org_id = l.org_id where p.org_id = 795014 and l.trashed_date is null
		    */
		    
			sqlSelect.append(
		        " SELECT lep.* " +
		        " FROM laboratorihaccp_storico_elenco_prove lep join laboratorihaccp_elenco_prove l" +
		        " on lep.org_id = l.org_id WHERE lep.org_id = ?  and l.trashed_date is null " +
		        " and prova_originaria = ? ");
		   
		    pst = db.prepareStatement(
		    sqlSelect.toString() + sqlOrder.toString());
		    pst.setInt(1, this.getOrgId());
		    pst.setBoolean(2, false);
		    rs = DatabaseUtils.executeQuery(db, pst, log);
		    
		    return rs;
		  }
		
		
		
		
		
		protected void buildResources(Connection db) throws SQLException {
		    Iterator i = this.iterator();
		    while (i.hasNext()) {
		    	Prova thisOrganization = (Prova) i.next();
		      
		    }
		  }

		public String getTableName() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getUniqueField() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setLastAnchor(Timestamp tmp) {
			// TODO Auto-generated method stub
			
		}

		public void setLastAnchor(String tmp) {
			// TODO Auto-generated method stub
			
		}

		public void setNextAnchor(Timestamp tmp) {
			// TODO Auto-generated method stub
			
		}

		public void setNextAnchor(String tmp) {
			// TODO Auto-generated method stub
			
		}

		public void setSyncType(int tmp) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setSyncType(String arg0) {
			// TODO Auto-generated method stub
			
		}

}
