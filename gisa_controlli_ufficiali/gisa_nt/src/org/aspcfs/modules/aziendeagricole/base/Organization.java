/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.aziendeagricole.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.aziendeagricole.base.OrganizationEmailAddressList;
import org.aspcfs.modules.aziendeagricole.base.OrganizationPhoneNumberList;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.modules.aziendeagricole.base.OrganizationAddress;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.aziendeagricole.base.OrganizationAddressList;
import org.aspcfs.modules.aziendeagricole.base.OrganizationList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.daffodilwoods.daffodildb.server.sql99.dml.contextuallytypedrowvalueconstructor;
import com.daffodilwoods.daffodildb.server.sql99.expression.booleanvalueexpression.booleanfactor;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

/**
 * @author Unlimited
 *
 */
public class Organization extends GenericBean {
	
	private static final long serialVersionUID = 7127566929908851547L;

	private static Logger logger = Logger.getLogger(org.aspcfs.modules.aziendeagricole.base.Organization.class);
	
	private static java.util.logging.Logger log = java.util.logging.Logger.getLogger("MainLogger");
	
	  private static final int TIPOLOGIA_AZIENDA_AGRICOLA = 7 ;
	  private static final int INT = Types.INTEGER;
		private static final int STRING = Types.VARCHAR;
		private static final int DOUBLE = Types.DOUBLE;
		private static final int FLOAT = Types.FLOAT;
		private static final int TIMESTAMP = Types.TIMESTAMP;
		private static final int DATE = Types.DATE;
		private static final int BOOLEAN = Types.BOOLEAN;
	  
		
	private String note= "" ;	
	  //protected double YTD = 0;
	  private Vector comuni=new Vector();
	  protected PagedListInfo pagedListInfo = null; 
	 
	  private OrganizationList elenco_op_controllati = new OrganizationList();
	  
	  private OrganizationAddressList addressList = new OrganizationAddressList();
	  private OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
	  
	protected OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
	  private int siteId = -1;
	  private int enteredBy = -1;
	  private java.sql.Timestamp entered = null;
	  private java.sql.Timestamp modified = null;
	  private int modifiedBy = -1;
	  private java.sql.Timestamp trashedDate = null;
	  private int orgId ;
	  private String ip_entered;
	  private String ip_modified;
	  private String accountName;
	  private String alert;
	  private String name;
	  private String asl;
	  private String numaut;
	  private String codice10;
	  private String notes;
	  private java.sql.Timestamp date1 = null;
	  private java.sql.Timestamp date2 = null;
	  private java.sql.Timestamp data_attribuzione_codice = null;
	  private String codice_fiscale;
	  private String partita_iva;
	  private String codiceImpresaInterno ;
	  private String codiceFiscaleRappresentante = null;
		private String nomeRappresentante = null;
		private String cognomeRappresentante = null;
		private String partitaIva = null;
		private String codiceFiscale = null;
		 private String accountNumber = "";
		 
		 
		 
		public String getCodiceImpresaInterno() {
			return codiceImpresaInterno;
		}


		public void setCodiceImpresaInterno(String codiceImpresaInterno) {
			this.codiceImpresaInterno = codiceImpresaInterno;
		}


		public String getNumReg(){return accountNumber; }
	

		public void setAccount_number(String account_number) {
			this.account_number = account_number;
		}

	public String getNomeRappresentante() {
			return nomeRappresentante;
		}

		public void setNomeRappresentante(String nomeRappresentante) {
			this.nomeRappresentante = nomeRappresentante;
		}

	public String getCodiceFiscaleRappresentante() {
			return codiceFiscaleRappresentante;
		}

		public void setCodiceFiscaleRappresentante(String codiceFiscaleRappresentante) {
			this.codiceFiscaleRappresentante = codiceFiscaleRappresentante;
		}

		public String getCognomeRappresentante() {
			return cognomeRappresentante;
		}

		public void setCognomeRappresentante(String cognomeRappresentante) {
			this.cognomeRappresentante = cognomeRappresentante;
		}

		public String getPartitaIva() {
			return partitaIva;
		}

		public void setPartitaIva(String partitaIva) {
			this.partitaIva = partitaIva;
		}

		public String getCodiceFiscale() {
			return codiceFiscale;
		}

		public void setCodiceFiscale(String codiceFiscale) {
			this.codiceFiscale = codiceFiscale;
		}

	public String getCodice_Fiscale() {
			return codice_fiscale;
		}

	  public void setCodice_Fiscale(String cf) {
			this.codice_fiscale = cf;
		}

	  public String getPartita_Iva() {
			return partita_iva;
		}

	  public void setPartita_Iva(String partita_iva) {
			this.partita_iva = partita_iva;
		}
	  
	  public String getNumaut() {
		return numaut;
	}

	public void setNumaut(String numaut) {
		this.numaut = numaut;
	}

	public String getCodice10() {
		return codice10;
	}

	public void setCodice10(String codice10) {
		this.codice10 = codice10;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public java.sql.Timestamp getdate1() {
		return date1;
	}
	 
	public String getDate1String() {
		    String tmp = "";
		    try {
		      return DateFormat.getDateInstance(3).format(date1);
		    } catch (NullPointerException e) {
		    }
		    return tmp;
	  }
	
	public void setDate1(String tmp) {
		this.date1 = DatabaseUtils.parseDateToTimestamp(tmp);
	}
	
	public void setate1(java.sql.Timestamp date1) {
		this.date1 = date1;
	}

	public java.sql.Timestamp getdate2() {
		return date2;
	}

	public void setdate2(java.sql.Timestamp date2) {
		this.date2 = date2;
	}

	public java.sql.Timestamp getData_attribuzione_codice() {
		return data_attribuzione_codice;
	}

	public void setData_attribuzione_codice(
			java.sql.Timestamp data_attribuzione_codice) {
		this.data_attribuzione_codice = data_attribuzione_codice;
	}


	
	  
	  public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}




	//Corrisponde alla targa dell'impresa, utile per l'associazione dell'operatore all'impresa 
	  private String nomeCorrentista;
	  private boolean conservato;
	  
	  public String getNomeCorrentista() {
		return nomeCorrentista;
	  }

	 public void setNomeCorrentista(String nome_correntista) {
		this.nomeCorrentista = nome_correntista;
	 }
	 
	 public boolean getConservato() {
			return conservato;
	 }

	 public void setConservato(boolean conservato) {
			this.conservato = conservato;
	 }

	 
	private int categoriaRischio=-1;
	  private int categoria_precedente=-1;
	  private int accountSize = -1;
	  private int orgIdC = -1;
	  
	  private int tipologia = -1;
	  private String account_number = null;
  
	  public OrganizationList getOpControllatiList() {
		    return elenco_op_controllati;
	   }
	  
	  public void setElenco_op_controllati(OrganizationList elenco_op_controllati) {
			this.elenco_op_controllati = elenco_op_controllati;
		}
	  /**
	   *  Sets the account size attribute of the Organization object
	   *
	   * @param  tmp  The new account size value
	   */
	  public void setAccountSize(String tmp) {
	    this.accountSize = Integer.parseInt(tmp);
	  }
	  
	  /**
	   *  Gets the AccountSize attribute of the Organization object adding account
	   *  size to the ad account form
	   *
	   * @return    The Account size value
	   */
	  public int getAccountSize() {
	    return accountSize;
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

	  public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String name) {
		this.accountName = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAccountNumber() {
		return account_number;
	}

	public void setAccountNumber(String an) {
		this.account_number = an;
	}
	
	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
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
	
	public int getOrgIdC() {
		return orgIdC;
	}

	public void setOrgIdC(int orgIdC) {
		this.orgIdC = orgIdC;
	}
	
	public Integer getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(String categoriaRischio) {
	    this.categoriaRischio = Integer.parseInt(categoriaRischio);
	  }
	public void setCategoriaRischio(Integer categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}

	public Integer getCategoriaPrecedente() {
		return categoria_precedente;
	}

	public void setCategoriaPrecedente(String categoriaRischio) {
	    this.categoria_precedente = Integer.parseInt(categoriaRischio);
	  }
	public void setCategoriaPrecedente(Integer categoriaRischio) {
		this.categoria_precedente = categoriaRischio;
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
	/**
	   *  Constructor for the Organization object, creates an empty Organization
	   *
	   * @since    1.0
	   */
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
		        sql.append("select comune from comuni order by comune where notused is null;");
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

	 
	 protected void buildRecordSearch(ResultSet rs) throws SQLException {
		    //organization table
			 
		  
		  	

		 try
		 {
			categoriaRischio=rs.getInt("categoria_rischio");
		 }
		 catch(SQLException e)
		 {
			 
		 }
		    this.setOrgId(rs.getInt("org_id"));
		    name = rs.getString("impresa");
		    accountNumber = rs.getString("num_reg");
		   
		    	nomeCorrentista =  rs.getString("targa_sede_operativa");
//		    enteredBy = rs.getInt("enteredby");
//		    modifiedBy = rs.getInt("modifiedby");
		    //Added
		   
		   
		    
		    partitaIva = rs.getString("partita_iva");
			codiceFiscale = rs.getString("codice_fiscale");
			siteId = DatabaseUtils.getInt(rs, "id_asl");
//			cognomeRappresentante = rs.getString("cognome_rappresentante");
//			nomeRappresentante = rs.getString("nome_rappresentante");
			
			//setCessato(rs.getInt("cessato"));
			//setTipo_opu_operatore(rs.getInt("tipo_operatore_old"));
			  
	  } 
	 
	 
	 protected void buildRecord(ResultSet rs) throws SQLException {
		    
		    this.setOrgId(rs.getInt("org_id"));
		    name = rs.getString("name");
		    account_number = rs.getString("account_number");
		    numaut = rs.getString("numaut");
		    notes = rs.getString("notes");
		    codiceImpresaInterno = rs.getString("codice_impresa_interno");
		    codice10 = rs.getString("codice10");
		    date1= rs.getTimestamp("date1");
		    date2= rs.getTimestamp("date2");
		    codice_fiscale = rs.getString("codice_fiscale");
		    partita_iva = rs.getString("partita_iva");
		    data_attribuzione_codice = rs.getTimestamp("data_attribuzione_codice"); 
		    siteId = rs.getInt("site_id");
		    entered = rs.getTimestamp("entered");
		    enteredBy = rs.getInt("enteredby");
		    modified = rs.getTimestamp("modified");
		    modifiedBy = rs.getInt("modifiedby");
		    trashedDate = rs.getTimestamp("trashed_date");
		    note = rs.getString("note1");
	 } 
	 
	 /**
	   *  Description of the Method
	   *
	   * @param  db             Description of Parameter
	   * @return                Description of the Returned Value
	   * @throws  SQLException  Description of Exception
	   */
	  public int update(Connection db,ActionContext context) throws SQLException {
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
	        //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
	        
	        //Solo se la provincia viene selezionata allora avviene il salvataggio       
	        if(thisAddress.getCity()!=null ) {
	        thisAddress.process(
	            db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
	        }
	        
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
	  
	  
	  public int completaDati(Connection db) throws SQLException {
		    int i = 1;
		    boolean doCommit = false;
		    try {
		      if (doCommit = db.getAutoCommit()) {
		        db.setAutoCommit(false);
		      }
		      String updte = "update organization set notes = ? , date1 = ? , account_number = ?,modifiedby=?,modified = current_timestamp where org_id = ?";
		      PreparedStatement pst = db.prepareStatement(updte);
		      pst.setString(1, this.getNotes());
		      pst.setTimestamp(2, this.getdate1());
		      pst.setString(3, this.getAccountNumber());
		      pst.setInt(4, modifiedBy);
		      pst.setInt(5, this.getOrgId());
		      pst.execute();
		    
		      
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
	        "SET name = ?, ip_modified = ? , ");
	   
	    if (!override) {
	      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
	    }
	    
	    if (account_number != null) {
	        sql.append("account_number = ?, ");
	    }
	    
	    if (notes != null) {
	        sql.append("notes = ?, ");
	    }
	    
	    if (codice_fiscale != null) {
	        sql.append("codice_fiscale = ?, ");
	    }
	    
	    if (partita_iva != null) {
	        sql.append("partita_iva = ?, ");
	    }

	    if (date1 != null) {
	        sql.append("date1 = ?, ");
	    }
	    
	    sql.append(
	        "modifiedby = ?" );
	    sql.append(" WHERE org_id = ? ");
	  
	    int i = 0;
	    pst = db.prepareStatement(sql.toString());
	    pst.setString(++i, name);
	    pst.setString(++i, ip_modified);
	    
	    if (account_number != null) {
	    	pst.setString(++i, account_number);
	    }
	    
	    if (notes != null) {
	    	pst.setString(++i, notes);
	    }
	    
	    if (codice_fiscale != null) {
	    	pst.setString(++i, codice_fiscale);
	    }
	    
	    if (partita_iva != null) {
	    	pst.setString(++i, partita_iva);
	    }
	    
	    if (date1 != null) {
	    	pst.setTimestamp(++i, date1);
	    }
	    	    
	    pst.setInt(++i, this.getModifiedBy());
	    pst.setInt(++i, this.getOrgId());
	    resultCount = pst.executeUpdate();
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
	  
	  
	  public boolean insert(Connection db,ActionContext context) throws SQLException {
		    StringBuffer sql = new StringBuffer();
		    boolean doCommit = false;
		    try {
		      modifiedBy = enteredBy;
		      
		      if (doCommit = db.getAutoCommit()) {
		        db.setAutoCommit(false);
		      }
		      
		      orgId =  DatabaseUtils.getNextSeq(db, context,"organization","org_id");

		      
		      //idFarmacia = DatabaseUtils.getNextSeq(db, "farmacia_id_seq");
		      sql.append(
		          "INSERT INTO organization (org_id, name, site_id, ");
		      
		      if (entered != null) {
		        sql.append("entered, ");
		      }
		      
		      if (modified != null) {
		        sql.append("modified, ");
		      }
		      
		      if (account_number != null) {
			        sql.append("account_number, ");
			  }
		      
		      if (codice_fiscale != null) {
			        sql.append("codice_fiscale, ");
			  }
		      
		      if (partita_iva != null) {
			        sql.append("partita_iva, ");
			  }
		      
		      if (date1 != null) {
			        sql.append("date1, ");
			  }
		      
		      if (notes != null) {
			        sql.append("notes, ");
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
		      
		      if (account_number != null) {
			        sql.append("?, ");
			  }
			      
		      if (codice_fiscale != null) {
			        sql.append("?, ");
			  }
		      
		      if (partita_iva != null) {
			        sql.append("?, ");
			  }
		      
		      if (date1 != null) {
			        sql.append("?, ");
			  }
		      
		      if (notes != null) {
			        sql.append("?, ");
			  }
			        
		      sql.append("?, ?, ?)");
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      pst.setInt(++i, orgId);
		      pst.setString(++i, name);
		      pst.setInt(++i, siteId);
		      
		      if (entered != null) {
		        pst.setTimestamp(++i, entered);
		      }
		      
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      }
		      
		      if (account_number != null) {
		    	  pst.setString(++i, account_number);
			  }
			      
		      if (codice_fiscale != null) {
		    	  pst.setString(++i, codice_fiscale);
			  }
		      
		      if (partita_iva != null) {
		    	  pst.setString(++i, partita_iva);
			  }
		      
		      if (date1 != null) {
		    	  pst.setTimestamp(++i, date1);
			  }
		      
		      if (notes != null) {
		    	  pst.setString(++i, notes);
			  }
		      
		      pst.setInt(++i, this.getEnteredBy());
		      pst.setInt(++i, this.getModifiedBy());
		      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
		      pst.setBoolean(++i, false);
		      pst.setInt(++i, 7);
		      pst.setString(++i, ip_entered);
		      pst.setString(++i, ip_modified);
		      pst.execute();
		      pst.close();
		      
		      Iterator iaddress = getAddressList().iterator();
		      while (iaddress.hasNext()) {
		    	  
		          OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
		          //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
		          
		          
		          if((thisAddress.getCity()!=null) && !(thisAddress.getCity().equals(""))) {
		              thisAddress.process(
		                  db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
		              }
		        }
		      
		      
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

	  public boolean logicdelete(Connection db)
		throws SQLException {
	if (orgId == -1) {
		throw new SQLException("Org ID not specified.");
	}
	boolean commit = db.getAutoCommit();
	try {
			PreparedStatement pst = db
				.prepareStatement("update organization set trashed_date = current_date ,modified=current_date , modifiedby = ? WHERE org_id = ?");
		pst.setInt(1, this.getModifiedBy());
		pst.setInt(2,orgId);
		pst.execute();
		pst.close();
		
		
		
	} catch (SQLException e) {
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

	  
	  protected String getAslProgressive (Connection db) throws SQLException
		{
			//ResultSet
			ResultSet rs=null;

			//NOME ASL
			String asl=null;
			int id_asl = -1;

			if (this.getOrgId() == -1) {
				throw new SQLException("Organization ID not specified");
			}

			StringBuffer sql = new StringBuffer();

			//Creazione della query per ottenere la ASL di appartenenza
			sql.append(
					"SELECT code, description " +
							"FROM lookup_site_id " +
							"WHERE code = (SELECT site_id FROM organization WHERE org_id = "+ this.getOrgId() + ") " );  

			Statement pst = db.createStatement();
			rs =pst.executeQuery(sql.toString());
			if (rs.next()) {
				asl = rs.getString("description");
				id_asl = rs.getInt("code");
			}

			/* Viene chiamato il metodo per ottenere il progressivo 
			 * della specifica ASL*/
			Integer progressive=this.getProgressive(db, id_asl);  


			pst.close();

			String result=null;

			//Il progressivo deve essere a 6 caratteri
			result=org.aspcfs.utils.StringUtils.zeroPad(progressive,6);	    	


			//Il progressivo per quella ASL viene ritornato       
			//return progressive.toString();
			return result;
		}  
	  
	  protected int getProgressive (Connection db, int asl) throws SQLException
		{
			ResultSet rs=null;
			int progressive=0;
			StringBuffer sql = new StringBuffer();

			switch (asl) {
			case 201:
				sql.append("SELECT nextval('avellino_seq') " );  
				break;
			case 202:
				sql.append("SELECT nextval('benevento_seq') " );
				break;
			case 203:
				sql.append("SELECT nextval('caserta_seq') " );
				break;
			case 204:
				sql.append("SELECT nextval('napoli1c_seq') " );  
				break;
			case 205:
				sql.append("SELECT nextval('napoli2n_seq') " );  
				break;
			case 206:
				sql.append("SELECT nextval('napoli3s_seq') " );  
				break;
			case 207:
				sql.append("SELECT nextval('salerno_seq') " );  
				break;

			}



			Statement pst = db.createStatement();
			rs =pst.executeQuery(sql.toString());

			if (rs.next()) {
				//Progressivo per quell'ASL
				progressive= rs.getInt(1);
			}

			pst.close();

			//Il codice del progressivo ASL viene ritornato      
			return progressive;

		}


public boolean generaCodice (Connection db, String codice) throws SQLException
{ 

	boolean codEs = false;
	//Buffer di creazione query
	StringBuffer sql = new StringBuffer();
	ResultSet rs=null;


			String codiceOsa=codice+"01.00.00"+this.getAslProgressive(db);

			Timestamp dataCodice=new Timestamp(System.currentTimeMillis());

			StringBuffer sq = new StringBuffer();

			//Creazione della query per ottenere la ASL di appartenenza
			sq.append(
					"SELECT account_number " +
							"FROM organization " +
							"WHERE account_number ilike '"+ codiceOsa + "'" );  

			Statement ps = db.createStatement();
			rs =ps.executeQuery(sq.toString());
			if (rs.next()) {
				codEs = true;
				ps.close();
				return codEs;
			}else{

				/* Viene chiamato il metodo per ottenere il progressivo 
				 * della specifica ASL*/

				/* Query che inserisce il codice osa nella tabella organization*/	    
				sql.append(
						"UPDATE organization SET account_number = '"+codiceOsa+"' "+
								"WHERE org_id= "+ orgId + "" );  

				PreparedStatement pst = db.prepareStatement(sql.toString());
			
				pst.executeUpdate();
				ps.close(); 

			}
	
	return codEs;
}

	
}


	

