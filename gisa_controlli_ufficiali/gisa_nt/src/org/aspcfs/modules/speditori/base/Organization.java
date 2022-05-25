/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.speditori.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.modules.speditori.base.OrganizationAddress;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.speditori.base.OrganizationAddressList;
import org.aspcfs.modules.speditori.base.OrganizationList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

/**
 * @author Unlimited
 *
 */
public class Organization extends GenericBean {
	
	private static final long serialVersionUID = 7127566929908851547L;

	private static Logger logger = Logger.getLogger(org.aspcfs.modules.speditori.base.Organization.class);
	
	private static java.util.logging.Logger log = java.util.logging.Logger.getLogger("MainLogger");
	
	  private static final int TIPOLOGIA_SPEDITORE = 11 ;
	 
	  private Vector comuni=new Vector();
	  protected PagedListInfo pagedListInfo = null; 
		  
	  private OrganizationAddressList addressList = new OrganizationAddressList();
	  
	  private int siteId = -1;
	  private int enteredBy = -1;
	  private int tipologia = -1;
	  private java.sql.Timestamp entered = null;
	  private java.sql.Timestamp modified = null;
	  private int modifiedBy = -1;
	  private java.sql.Timestamp trashedDate = null;
	  private int orgId ;
	  private String ip_entered;
	  private String ip_modified;
	  private String name;
	  private String asl;
	  private String accountNumber = null;
	  private String accountName = null;
	  
	  
	  //Corrisponde alla targa dell'impresa, utile per l'associazione dell'operatore all'impresa 
	  private String nomeRappresentante;
	 
	  public String getNomeRappresentante() {
		return nomeRappresentante != null && !nomeRappresentante.equals("") ? nomeRappresentante : "N.D.";
	  }

	  public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	  }
	 
	  public String getAccountName() {
			// TODO Auto-generated method stub
			return accountName;
	  }
	  
	  public void setAccountName(String accountName) {
			this.accountName = accountName;
	  }

		
	  
	  /**
	   *  Gets the id attribute of the Organization object
	   *
	   * @return    The id value
	   */
	  public int getId() {
	    return orgId;
	  }

	  public int getTipologia() {
		    return tipologia;
		  }
	  
	  public void setTipologia(int t){
		  this.tipologia = t;
	  }
	  
	 public String getIp_entered() {
			return ip_entered;
	 }

	public void setIp_entered(String ip_entered) {
			this.ip_entered = ip_entered;
		}

    public String getIp_modified() {
			return ip_modified;
		}
		
    public void setIp_modified(String ip_modified) {
			this.ip_modified = ip_modified;
	}
	public String getName() {
		return name != null && !name.equals("") ? name : "N.D.";
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAccountNumber() {
		return accountNumber != null && !accountNumber.equals("") ? accountNumber : "N.D.";
	}

	public void setAccountNumber(String an) {
		this.accountNumber = an;
	}
	
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public java.sql.Timestamp getEntered() {
		return entered;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}

	public String getAsl () {
		return asl;
	}
	
	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	 public int getSiteId() {
			return siteId;
	}

	public Timestamp getTrashedDate() {
		    return trashedDate;
	}
	
	public Organization() { }


	  /**
	   *  Constructor for the Organization object
	   *
	   * @param  rs                Description of Parameter
	   * @exception  SQLException  Description of the Exception
	   * @throws  SQLException     Description of the Exception
	   * @throws  SQLException     Description of Exception
	   */
	  public Organization(ResultSet rs) throws SQLException {
	    buildRecord(rs);
	    addressList.setOrgId(this.getOrgId());
	  }
	   
	  
	  public int getOrgId() {
		return orgId;
	}


	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	 public void setComuni (Connection db) throws SQLException {
			
		  	Statement st = null;
		    ResultSet rs = null;
		    StringBuffer sql = new StringBuffer();
		    
		    //sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= (select site_id from organization where org_id="+ this.getOrgId() + "))");
		    //if(codeUser==-1){
		        sql.append("select comune from comuni order by comune;");
		   /*}else{
		    sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= "+ codeUser + ")");
		    }*/
		    st = db.createStatement();
		    rs = st.executeQuery(sql.toString());
		    
		    while (rs.next()) {
		      comuni.add(rs.getString("comune"));
		    }
		    rs.close();
		    st.close();
		  
		}
	 public void setRequestItems(ActionContext context) {
		   
		    addressList = new OrganizationAddressList(context.getRequest());
	 }
	 
	 public Vector getComuni () throws SQLException {
	      
		    return comuni;
		   
		  }

	 public OrganizationAddressList getAddressList() {
		    return addressList;
	  }
	
	 public boolean isTrashed() {
		    return (trashedDate != null);
		  }

	 public Organization(Connection db, int orgId) throws SQLException {
		 
		 if (DatabaseUtils.getTipologiaPartizione(db, orgId) == Ticket.ALT_OPU){
			 PreparedStatement pst = db.prepareStatement(
						"select s.id as org_id, o.ragione_sociale as name, "
						+ "s.id_asl as site_id, "
						+ "999 as tipologia, "
						+ "s.numero_registrazione as account_number, "
						+ "'' as nome_rappresentante, s.entered, -1 as enteredby, "
						+ "s.modified, -1 as modifiedby, s.trashed_date "
						+ "from opu_stabilimento s left join opu_operatore o on s.id_operatore = o.id where alt_id = ? " );
						pst.setInt(1, orgId);
					ResultSet rs = DatabaseUtils.executeQuery(db, pst, logger);
					if (rs.next()) {
						buildRecord(rs);
					}
					rs.close();
					pst.close();
		 }
		 else  if (DatabaseUtils.getTipologiaPartizione(db, orgId) == Ticket.ALT_SINTESIS){
			 	PreparedStatement pst = db.prepareStatement(
						"select s.id as org_id, o.ragione_sociale as name, "
						+ "s.id_asl as site_id, "
						+ "2000 as tipologia, "
						+ "s.approval_number as account_number, "
						+ "'' as nome_rappresentante, s.entered, -1 as enteredby, "
						+ "s.modified, -1 as modifiedby, s.trashed_date "
						+ "from sintesis_stabilimento s left join sintesis_operatore o on s.id_operatore = o.id where s.alt_id = ? " );
						pst.setInt(1, orgId);
					ResultSet rs = DatabaseUtils.executeQuery(db, pst, logger);
					if (rs.next()) {
						buildRecord(rs);
					}
					rs.close();
					pst.close();
		 }
		 
		 else {
			
		if (orgId == -1) {
			      throw new SQLException("Invalid Account");
		} 
		PreparedStatement pst = db.prepareStatement(
			"SELECT o.* " +
			"FROM organization o " +
			"WHERE  o.org_id = ? " );
			pst.setInt(1, orgId);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst, logger);
		if (rs.next()) {
			addressList.setOrgId(orgId);
			addressList.buildList(db);
			buildRecord(rs);
		}
		rs.close();
		pst.close();
		if (orgId == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		 } 
	 }

	 protected void buildRecord(ResultSet rs) throws SQLException {
		    
		    this.setOrgId(rs.getInt("org_id"));
		    name = rs.getString("name");
		    siteId = rs.getInt("site_id");
		    tipologia = rs.getInt("tipologia");
			accountNumber = rs.getString("account_number");
			nomeRappresentante = rs.getString("nome_rappresentante");
		    entered = rs.getTimestamp("entered");
		    enteredBy = rs.getInt("enteredby");
		    modified = rs.getTimestamp("modified");
		    modifiedBy = rs.getInt("modifiedby");
		    trashedDate = rs.getTimestamp("trashed_date");
	 } 
	 
	  public int update(Connection db) throws SQLException {
	    int i = -1;
	    boolean doCommit = false;
	    try {
	      if (doCommit = db.getAutoCommit()) {
	        db.setAutoCommit(false);
	      }
	      i = this.update(db, false);
	      
	      Iterator iaddress = getAddressList().iterator();
	      while (iaddress.hasNext()) {
	        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
	        thisAddress.setModifiedBy(modifiedBy);
	        thisAddress.update(db, modifiedBy);
	     
	     }
	      
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
	  
	  
	  /**
	   *  Description of the Method
	   *
	   * @param  db             Description of Parameter
	   * @param  override       Description of Parameter
	   * @return                Description of the Returned Value
	   * @throws  SQLException  Description of Exception
	   */
	  public int update(Connection db, boolean override) throws SQLException {
	    int resultCount = 0;
	    PreparedStatement pst = null;
	    StringBuffer sql = new StringBuffer();
	    sql.append(
	        "UPDATE organization " +
	        "SET name = ?, account_number = ?, ip_modified = ? , ");
	   
	    if (!override) {
	      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
	    }

	    sql.append("site_id = " +siteId+ ", ");
	    
	    
	    if (nomeRappresentante != null) {
	        sql.append("nome_rappresentante = ?, ");
	    }
	    
	    sql.append(
	        "modifiedby = ?" );
	    sql.append(" WHERE org_id = ? ");
	  
	    int i = 0;
	    pst = db.prepareStatement(sql.toString());
	    pst.setString(++i, name);
	    pst.setString(++i, accountNumber);
	    pst.setString(++i, ip_modified);
	    
	    if (nomeRappresentante != null) {
	    	pst.setString(++i, nomeRappresentante);
	    }
	    
	    pst.setInt(++i, this.getModifiedBy());
	    pst.setInt(++i, this.getOrgId());
	    resultCount=pst.executeUpdate();
	    pst.close();
	    return resultCount;
	    
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
		          "UPDATE organization " +
		          "SET trashed_date = ?, " +
		          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
		          "modifiedby = ? " +
		          "WHERE org_id = ? ");
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
	  
	  
	  public boolean delete(Connection db, ActionContext context, String baseFilePath) throws SQLException {
		    if (this.getOrgId() == -1) {
		      throw new SQLException("Speditore ID non specificato.");
		    }
		    boolean commit = db.getAutoCommit();
		    try { 
		      if (commit) {
		        db.setAutoCommit(false);
		      }
		 
		      Statement st = db.createStatement();
		      st.executeUpdate(
		          "UPDATE organization set trashed_date = "+ System.currentTimeMillis()+" WHERE org_id = " + this.getOrgId());
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
		  
	  public boolean disable(Connection db) throws SQLException {
	    if (this.getOrgId() == -1) {
	      throw new SQLException("Speditore ID not specified");
	    }
	
	    PreparedStatement pst = null;
	    StringBuffer sql = new StringBuffer();
	    boolean success = false;
	
	    sql.append(
	        "UPDATE organization set enabled = ? " +
	        "WHERE org_id = ? ");
	
	    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
	
	    int i = 0;
	    pst = db.prepareStatement(sql.toString());
	    pst.setBoolean(++i, false);
	    pst.setInt(++i, orgId);
	
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

	public int selectAsl(Connection db, String comune) throws SQLException {
		  
		  StringBuffer sql = new StringBuffer();
		  int i=0, asl_code = 0 ;
		  sql.append("SELECT codiceistatasl as asl from comuni where comune ilike ? ");
		  PreparedStatement pst = db.prepareStatement(sql.toString());
		  pst.setString(++i, comune.toLowerCase().replaceAll(" ", "%"));
		  ResultSet rs = pst.executeQuery();
		  while (rs.next()){
			  asl_code = rs.getInt("asl");
		  }
		  return asl_code;
	 }
	  
	  
	  public boolean insert(Connection db, OrganizationAddress oa,ActionContext context) throws SQLException {
		    StringBuffer sql = new StringBuffer();
		    boolean doCommit = false;
		    try {
		      modifiedBy = enteredBy;
		      
		      if (doCommit = db.getAutoCommit()) {
		        db.setAutoCommit(false);
		      }
		      orgId = DatabaseUtils.getNextSeqInt(db, context , "organization","org_id");
		      
		      //idFarmacia = DatabaseUtils.getNextSeq(db, "farmacia_id_seq");
		      sql.append(
		          "INSERT INTO organization (org_id, name, site_id, account_number, nome_rappresentante, ");
		      
		      if (entered != null) {
		        sql.append("entered, ");
		      }
		      if (modified != null) {
		        sql.append("modified, ");
		      }
		      
		      sql.append("enteredby, modifiedby, trashed_date, pregresso,tipologia,ip_entered,ip_modified ");
		      
		      sql.append(")");
		      sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ");
		         
		      if (entered != null) {
		        sql.append("?, ");
		      }
		      if (modified != null) {
		        sql.append("?, ");
		      }
		      
		      sql.append("?, ?, ?, ?, ?)");
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      pst.setInt(++i, orgId);
		      pst.setString(++i, name);
		      pst.setInt(++i, siteId);
		      pst.setString(++i, accountNumber);
		      pst.setString(++i, nomeRappresentante);
		      if (entered != null) {
		        pst.setTimestamp(++i, entered);
		      }
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      }
		      pst.setInt(++i, this.getModifiedBy());
		      pst.setInt(++i, this.getModifiedBy());
		      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
		      pst.setBoolean(++i, false);
		      pst.setInt(++i, 11);
		      pst.setString(++i, ip_entered);
		      pst.setString(++i, ip_modified);
		      pst.execute();
		      pst.close();
		     
		      oa.setOrgId(orgId);
		      oa.setEnteredBy(enteredBy);
		      oa.setModifiedBy(modifiedBy);
		      oa.insert(db,context);
		      
		      //this.update(db, true);
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

	  /**
	   *  Description of the Method
	   *
	   * @param  db             Description of the Parameter
	   * @param  tmpOrgId       Description of the Parameter
	   * @return                Description of the Return Value
	   * @throws  SQLException  Description of the Exception
	   */
	  public static int getOrganizationSiteId(Connection db, int tmpOrgId) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int organizationSiteId = -1;
	    String sqlSelect =
	        "SELECT site_id " +
	        "FROM organization " +
	        "WHERE org_id = ? ";
	    int i = 0;
	    pst = db.prepareStatement(sqlSelect);
	    pst.setInt(++i, tmpOrgId);
	    rs = pst.executeQuery();
	    if (rs.next()) {
	      organizationSiteId = DatabaseUtils.getInt(rs, "site_id");
	    }
	    rs.close();
	    pst.close();
	    return organizationSiteId;
	  }

	

	
}


	

