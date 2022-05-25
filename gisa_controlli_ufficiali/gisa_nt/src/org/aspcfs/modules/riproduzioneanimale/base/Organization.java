/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.riproduzioneanimale.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddress;
import org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddressList;
import org.aspcfs.utils.DatabaseUtils;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 * Exp $
 */
public class Organization extends GenericBean {


	private static final long serialVersionUID = 4320567602597719160L;
	private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
	private int accountSize = -1;

	private Timestamp trashedDate = null;
	private int categoriaRischio=-1;
	private int id = -1						;
	private int orgId ;
	private int orgIdC = -1;
	private String stato;
	public String getStato(){return this.stato;}
	public void setStato(String stato){this.stato = stato;}

	private String name = ""						;
	private int siteId = -1						;
	private java.sql.Timestamp entered = null		;
	private java.sql.Timestamp modified = null	;
	
	private java.sql.Timestamp data_assegnazione_approval_number = null; 
	private java.sql.Timestamp date2 = null;
	private int enteredBy 						;
	private int modifiedBy 						;
	private String ipEntered					;
	private int tipologia 						;
	private String ipModified ;
	
	private String nome_rappresentante;
	private String codice_fiscale;
	private String partita_iva;
	private String tipologia_strutt;
	private String codice10;
	private String numaut;
	
	private Timestamp dataInizio;
	  
	  public Timestamp getDataInizio()
	  {
		  return this.dataInizio;
	  }
	  
	  public void setDataInizio(Timestamp ts)
	  {
		  this.dataInizio = ts;
	  }
	
	private int statoLab = -1;
	
	   public int getStatoLab(){return this.statoLab;}
	   public void setStatoLab(int stato){this.statoLab = stato;}
	
	public java.sql.Timestamp getData_assegnazione_approval_number() {
		return data_assegnazione_approval_number;
	}

	public void setData_assegnazione_approval_number(
			java.sql.Timestamp data_assegnazione_approval_number) {
		this.data_assegnazione_approval_number = data_assegnazione_approval_number;
	}

	public java.sql.Timestamp getDate2() {
		return date2;
	}

	public void setDate2(
			java.sql.Timestamp date2) {
		this.date2 = date2;
	}
	
	public void setData_assegnazione_approval_number(String tmp) {
		this.data_assegnazione_approval_number = DatabaseUtils.parseDateToTimestamp(tmp);
	}
	
	public String getDate2String() {
	    String tmp = "";
	    try {
	      return DateFormat.getDateInstance(3).format(date2);
	    } catch (NullPointerException e) {
	    }
	    return tmp;
     }
	
	public int getOrgIdC() {
		return orgIdC;
	}

	public void setOrgIdC(int orgIdC) {
		this.orgIdC = orgIdC;
	}

	
	public void setDate2(String tmp) {
		this.date2 = DatabaseUtils.parseDateToTimestamp(tmp);
	}
	
	public String getNome_rappresentante() {
		return nome_rappresentante;
	}

	public void setNome_rappresentante(String nome_rappresentante) {
		this.nome_rappresentante = nome_rappresentante;
	}


	public String getCodice_fiscale() {
		return codice_fiscale;
	}


	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}


	public String getPartita_iva() {
		return partita_iva;
	}


	public void setPartita_iva(String partita_iva) {
		this.partita_iva = partita_iva;
	}


	public String getTipologia_strutt() {
		return tipologia_strutt;
	}


	public void setTipologia_strutt(String tipologia_strutt) {
		this.tipologia_strutt = tipologia_strutt;
	}


	public String getCodice10() {
		return codice10;
	}


	public void setCodice10(String codice10) {
		this.codice10 = codice10;
	}


	public String getNumaut() {
		return numaut;
	}


	public void setNumaut(String numaut) {
		this.numaut = numaut;
	}

	private OrganizationAddressList addressList = new OrganizationAddressList();
	
	private OrganizationStazioniList stazioniList = new OrganizationStazioniList();

	private Vector comuni=new Vector()	;
	private java.util.Date prossimoControllo= null;
	
	
	public java.util.Date getProssimoControllo() {
		return prossimoControllo;
	}


	public void setProssimoControllo(java.util.Date prossimoControllo) {
		this.prossimoControllo = prossimoControllo;
	}


	public int getCategoriaRischio() {
		return categoriaRischio;
	}


	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}


	public Timestamp getTrashedDate() {
		return trashedDate;
	}


	public void setTrashedDate(Timestamp trashedDate) {
		this.trashedDate = trashedDate;
	}


	public int getAccountSize() {
		return accountSize;
	}


	public void setAccountSize(int accountSize) {
		this.accountSize = accountSize;
	}

	  public void setAccountSize(String tmp) {
		    this.accountSize = Integer.parseInt(tmp);
		  }

	public int getOrgId() {
		return orgId;
	}


	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	  
	
	public boolean isTrashed() {
		    return (trashedDate != null);
		  }

	public String getIpModified() {
		return ipModified;
	}

	public void setIpModified(String ipModified) {
		this.ipModified = ipModified;
	}

	public String getIpEntered() {
		return ipEntered;
	}

	public void setIpEntered(String ipEntered) {
		this.ipEntered = ipEntered;
	}

	public OrganizationAddressList getAddressList() {
		return addressList;
	}


	public void setAddressList(OrganizationAddressList addressList) {
		this.addressList = addressList;
	}

	public OrganizationStazioniList getStazioniList() {
		return stazioniList;
	}
	
	public void setStazioniList(OrganizationStazioniList stazioniList) {
		this.stazioniList = stazioniList;
	}
	
	public static Logger getLog() {
		return log;
	}


	public static void setLog(Logger log) {
		Organization.log = log;
	}


	public int getId() {
		return orgId;
	}


	public void setId(int orgId) {
		this.orgId = orgId;
	}
	
	public void setId(String orgId) {
		this.id = Integer.parseInt(orgId);
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getSiteId() {
		return siteId;
	}


	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = Integer.parseInt(siteId);
	}

	public java.sql.Timestamp getEntered() {
		return entered;
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


	public int getEnteredBy() {
		return enteredBy;
	}


	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}


	public int getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public int getTipologia() {
		return tipologia;
	}


	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}


	

	public Vector getComuni() {
		return comuni;
	}


	public void setComuni(Vector comuni) {
		this.comuni = comuni;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public Organization() { }


	public Organization(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	public Organization(Connection db, int org_id) throws SQLException 
	{
		if (org_id == -1) {
			throw new SQLException("Invalid Account");
		} 
		PreparedStatement pst = db.prepareStatement(
				"SELECT o.*" +
				"FROM organization o " +
		"WHERE o.org_id = ? " );
		pst.setInt(1, org_id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
		if (rs.next()) {
			buildRecord(rs);
		}
		rs.close();
		pst.close();
		if (id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		addressList.setOrgId(this.getId());
		addressList.buildList(db);
		
		//ADDING STAZIONI
		stazioniList.setOrg_id(this.getId());
		stazioniList.buildList(db);
		
	}

//	public void cessazioneAttivita(Connection db,String noteCessazione,int userId) throws SQLException
//	{
//		PreparedStatement pst = db.prepareStatement("UPDATE organization set modified=current_timestamp,modifiedby=?,date2 = ? ,stato_lab=4,data_cessazione_attivita=?,note_cessazione_attivita=? , cessato = 1, stato = 'Cessato'  , data_cambio_stato = CURRENT_TIMESTAMP where org_id=?");
//		pst.setInt(1, userId);
//		pst.setTimestamp(2, date2);
//		pst.setTimestamp(3, date2);
//		pst.setString(4, noteCessazione);
//		pst.setInt(5, this.orgId);
//		pst.execute();
//		
//	}
	
	public boolean insert(Connection db,ActionContext context) throws SQLException {
	    StringBuffer sql = new StringBuffer();
	    boolean doCommit = false;
	    try {
	      modifiedBy = enteredBy;
	      
	      if (doCommit = db.getAutoCommit()) {
	        db.setAutoCommit(false);
	      }
	      orgId = DatabaseUtils.getNextSeqInt(db, context,"organization","org_id");
	      
	      //idFarmacia = DatabaseUtils.getNextSeq(db, "farmacia_id_seq");
	      sql.append(
	          "INSERT INTO organization (org_id, name, site_id, ");
	      
	      if (entered != null) {
	        sql.append("entered, ");
	      }
	      
	      if (modified != null) {
	        sql.append("modified, ");
	      }
	      
	      if (numaut != null) {
		        sql.append("numaut, ");
		  }
	      
	      if (codice_fiscale != null) {
		        sql.append("codice_fiscale, ");
		  }
	      
	      if (partita_iva != null) {
		        sql.append("partita_iva, ");
		  }
	      
	      if (date2 != null) {
		        sql.append("date1, ");
		  }
	      
	      if (nome_rappresentante != null) {
		        sql.append("nome_rappresentante, ");
		  }
	      
	      if (tipologia_strutt != null) {
		        sql.append("tipologia_strutt, ");
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
	      
	      if (numaut != null) {
		        sql.append("?, ");
		  }
		      
	      if (codice_fiscale != null) {
		        sql.append("?, ");
		  }
	      
	      if (partita_iva != null) {
		        sql.append("?, ");
		  }
	      
	      if (date2 != null) {
		        sql.append("?, ");
		  }
	      
	      if (nome_rappresentante != null) {
		        sql.append("?, ");
		  }
	      
	      if (tipologia_strutt != null) {
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
	      
	      if (numaut != null) {
	    	  pst.setString(++i, numaut);
		  }
		      
	      if (codice_fiscale != null) {
	    	  pst.setString(++i, codice_fiscale);
		  }
	      
	      if (partita_iva != null) {
	    	  pst.setString(++i, partita_iva);
		  }
	      
	      if (date2 != null) {
	    	  pst.setTimestamp(++i, date2);
		  }
	      
	      if (nome_rappresentante != null) {
	    	  pst.setString(++i, nome_rappresentante);
		  }
	      
	      if (tipologia_strutt != null) {
		        pst.setString(++i,tipologia_strutt);
		  }
	      
	      pst.setInt(++i, this.getEnteredBy());
	      pst.setInt(++i, this.getModifiedBy());
	      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
	      pst.setBoolean(++i, false);
	      pst.setInt(++i, 8);
	      pst.setString(++i, ipEntered);
	      pst.setString(++i, ipModified);
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
	      
	      //Adding station
	      
	      
	      
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
	public int update(Connection db,ActionContext context) throws SQLException {
		int i = -1;
		boolean doCommit = false;
		try 
		{
			if (doCommit = db.getAutoCommit()) 
			{
				db.setAutoCommit(false);
			}
			i = this.update(db, false);
			Iterator iaddress = getAddressList().iterator();
			while (iaddress.hasNext()) 
			{
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				if((thisAddress.getCity()!=null)) 
				{
					thisAddress.process(db, id, this.getEnteredBy(), this.getModifiedBy(),context);
				}
			}
			if (doCommit) 
			{
				db.commit();
			}
		} 
		catch (SQLException e) 
		{
			if (doCommit) 
			{
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} 
		finally 
		{
			if (doCommit) 
			{
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
	    
	    if (numaut != null) {
	        sql.append("numaut = ?, ");
	    }
	    
	    if (nome_rappresentante != null) {
	        sql.append("nome_rappresentante = ?, ");
	    }
	    
	    if (codice_fiscale != null) {
	        sql.append("codice_fiscale = ?, ");
	    }
	    
	    if (partita_iva != null) {
	        sql.append("partita_iva = ?, ");
	    }

	    if (date2 != null) {
	        sql.append("date2 = ?, ");
	    }
	    
	    sql.append(
	        "modifiedby = ?" );
	    sql.append(" WHERE org_id = ? ");
	  
	    int i = 0;
	    pst = db.prepareStatement(sql.toString());
	    pst.setString(++i, name);
	    pst.setString(++i, ipModified);
	    
	    if (numaut != null) {
	    	pst.setString(++i, numaut);
	    }
	    
	    if (nome_rappresentante != null) {
	    	pst.setString(++i, nome_rappresentante);
	    }
	    
	    if (codice_fiscale != null) {
	    	pst.setString(++i, codice_fiscale);
	    }
	    
	    if (partita_iva != null) {
	    	pst.setString(++i, partita_iva);
	    }
	    
	    if (date2 != null) {
	    	pst.setTimestamp(++i, date2);
	    }
	    	    
	    pst.setInt(++i, this.getModifiedBy());
	    pst.setInt(++i, this.getOrgId());
	    resultCount = pst.executeUpdate();
	    pst.close();
	    
	    
	    return resultCount;
	    
	  }
	
	
	protected void buildRecord(ResultSet rs) throws SQLException {

		id 		= 	rs.getInt("org_id");
		orgId 	=  	id ;
		name	=	rs.getString("name");
		siteId	=	rs.getInt("site_id");
		nome_rappresentante = rs.getString("nome_rappresentante");
		codice_fiscale = rs.getString("codice_fiscale");
		partita_iva =  rs.getString("partita_iva");
		tipologia_strutt = rs.getString("tipologia_strutt");
		codice10 = rs.getString("codice10");
		numaut = rs.getString("numaut");
		data_assegnazione_approval_number = rs.getTimestamp("data_assegnazione_approval_number");
		date2 = rs.getTimestamp("date2");
		modified		=	rs.getTimestamp("modified");
		modifiedBy	=	rs.getInt("modifiedby");
		entered 		= 	rs.getTimestamp("entered");
		enteredBy		=	rs.getInt("enteredby");
		tipologia 	= 	rs.getInt("tipologia");
		ipEntered			 	= 	rs.getString("ip_entered");
		ipModified 			= 	rs.getString("ip_modified");
		prossimoControllo = rs.getTimestamp("prossimo_controllo");
		categoriaRischio=rs.getInt("categoria_rischio");
		statoLab = DatabaseUtils.getInt(rs, "stato_lab");
		setCessato(rs.getInt("cessato"));
		
		try
		{
			setDataInizio(rs.getTimestamp("data_inizio"));
		}
		catch(Exception ex)
		{
			
		}
		
		try
		{
			setStato(rs.getString("stato"));
		}
		catch(Exception ex){}
		
		
	}

	public void setRequestItems(ActionContext context) {
		addressList = new OrganizationAddressList(context.getRequest());

	}	  
	
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

	public void setComuni (Connection db, int codeUser) throws SQLException 
	{
		Statement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		if(codeUser>0)
			sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= "+ codeUser + ")");
		else
			sql.append("select comune from comuni ");
		st = db.createStatement();
		rs = st.executeQuery(sql.toString());

		while (rs.next()) {
			comuni.add(rs.getString("comune"));
		}
		rs.close();
		st.close();

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
	
}

