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
package org.aspcfs.modules.operatorinonaltrove.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.utils.ProjectUtils;
import com.zeroio.webdav.utils.ICalendar;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.RecipientList;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.modules.mycfs.base.OrganizationEventList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import java.io.Serializable;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;




public class Organization extends GenericBean implements Serializable {

	
	private static final long serialVersionUID = 4372988956627990716L;


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
	private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}


	protected double YTD = 0;

	private Vector comuni=new Vector();
	private int orgId = -1;
	private String name = "";
	private String notes = "";
	private int accountSize = -1;
	private String accountSizeName = null;
	private int siteId = -1;
	private int tipologia = -1;
	private Integer statoLab = -1;
	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	private int enteredBy;
	private int modifiedBy;
	private java.sql.Timestamp entered = null;
	private java.sql.Timestamp modified = null;
	private java.sql.Timestamp contractEndDate = null;
	private java.sql.Timestamp date1 = null;
	private java.sql.Timestamp date2 = null;
	private String accountNumber = "";
	private Timestamp trashedDate = null;
	private OrganizationAddressList addressList = new OrganizationAddressList();
	private String enteredByName = "";
	private String modifiedByName = "";
	private Timestamp prossimoControllo ;
	// campi nuovi - progetto STI
	private String partitaIva = null;
	private String codiceFiscale = null;
	private String nomeCorrentista = null;
	private String codiceFiscaleCorrentista = null;
	private int livelloRischio = -1;
	private int livelloRischioFinale = -1;
	/*dati aggiunti da d.dauria per gestire il riferimento */
	private String codiceFiscaleRappresentante = null;
	private String nomeRappresentante = null;
	private String cognomeRappresentante = null;
	private String emailRappresentante = null;
	private String ritiReligiosi="";
	private int categoriaRischio=-1;
	private int    titoloRappresentante = -1;
	private String telefonoRappresentante = null;
	private java.sql.Timestamp dataNascitaRappresentante =null;
	private String luogoNascitaRappresentante = "";
	private String tipoAutorizzazzione="";
	private Timestamp dataPresentazione ;
	private int tipologiaId = -1;
	private Timestamp dataProssimoControllo ;


	public Timestamp getDataPresentazione() {
		return dataPresentazione;
	}

	public void setDataPresentazione(String dataPresentazione) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dataPresentazione!=null && !dataPresentazione.equals(""))
			try {
				this.dataPresentazione = new Timestamp(sdf.parse(dataPresentazione).getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public Timestamp getProssimoControllo() {
		return prossimoControllo;
	}

	public void setProssimoControllo(Timestamp prossimoControllo) {
		this.prossimoControllo = prossimoControllo;
	}



	public void setComuni (Connection db, int codeUser) throws SQLException {

		Statement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		//sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= (select site_id from organization where org_id="+ this.getOrgId() + "))");

		sql.append("select comune from comuni where true = true");
		if(codeUser!=-1)
			sql.append(" and codiceistatasl= (select codiceistat from lookup_site_id where code= "+ codeUser + ")");
		//sql.append("select comune from comuni");

		st = db.createStatement();
		rs = st.executeQuery(sql.toString());

		while (rs.next()) {
			comuni.add(rs.getString("comune"));
		}
		rs.close();
		st.close();

	}

	public Vector getComuni()
	{
		return comuni ; 
	}
	public Timestamp getDataProssimoControllo() {
		return dataProssimoControllo;
	}

	public void setDataProssimoControllo(Timestamp dataProssimoControllo) {
		this.dataProssimoControllo = dataProssimoControllo;
	}

	private String fax = null;
	private String ip_entered;
	private String ip_modified ;


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
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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
	public String getTipoAutorizzazzione() {
		return tipoAutorizzazzione;
	}

	public void setTipoAutorizzazzione(String tipoAutorizzazzione) {
		this.tipoAutorizzazzione = tipoAutorizzazzione;
	}



	public void updateCategoriaRischio(Connection db, int categoriaR, int orgid) throws SQLException{


		PreparedStatement pst=db.prepareStatement("UPDATE organization set categoria_rischio = ? where org_id = ?");
		pst.setInt(1, categoriaR);
		pst.setInt(2, orgid);
		pst.execute();

	}

	public String getRitiReligiosi() {
		return ritiReligiosi;
	}

	public void setRitiReligiosi(String ritiReligiosi) {
		this.ritiReligiosi = ritiReligiosi;
	}

	public String getCodiceFiscaleRappresentante() {
		return codiceFiscaleRappresentante;
	}

	public void setCodiceFiscaleRappresentante(String codiceFiscaleRappresentante) {
		this.codiceFiscaleRappresentante = codiceFiscaleRappresentante;
	}

	public String getNomeRappresentante() {
		return nomeRappresentante;
	}

	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}

	public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}

	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}

	public String getEmailRappresentante() {
		return emailRappresentante;
	}

	public void setEmailRappresentante(String emailRappresentante) {
		this.emailRappresentante = emailRappresentante;
	}

	public java.sql.Timestamp getDataNascitaRappresentante() {
		return dataNascitaRappresentante;
	}
	public void setDataNascitaRappresentante(String tmp) {
		this.dataNascitaRappresentante = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	}

	public void setDataNascitaRappresentante(java.sql.Timestamp tmp) {
		this.dataNascitaRappresentante = tmp;
	}
	public String getLuogoNascitaRappresentante() {
		return luogoNascitaRappresentante;
	}

	public void setLuogoNascitaRappresentante(String luogoRappresentante) {
		this.luogoNascitaRappresentante = luogoRappresentante;
	}

	public int getTitoloRappresentante() {
		return titoloRappresentante;
	}

	public void setTitoloRappresentante(int titoloRappresentante) {
		this.titoloRappresentante = titoloRappresentante;
	}

	public String getTelefonoRappresentante() {
		return telefonoRappresentante;
	}

	public void setTelefonoRappresentante(String telefonoRappresentante) {
		this.telefonoRappresentante = telefonoRappresentante;
	}

	public int getLivelloRischio() {
		return livelloRischio;
	}

	public void setLivelloRischio(int tmp) {
		this.livelloRischio = tmp;
	}

	private Timestamp dataAudit = null;
	public Timestamp getDataAudit() {
		return dataAudit;
	}

	public void setDataAudit(Timestamp tmp) {
		this.dataAudit = tmp;
	}

	public int getLivelloRischioFinale() {
		return livelloRischioFinale;
	}

	public void setLivelloRischioFinale(int tmp) {
		this.livelloRischioFinale = tmp;
	}


	// campi nuovi - metodi get e set
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}



	public void setTpologia(int tipologia) {
		this.tipologia = tipologia;
	}

	public String getCodiceFiscaleCorrentista() {
		return codiceFiscaleCorrentista;
	}

	public void setCodiceFiscaleCorrentista(String codiceFiscaleCorrentista) {
		this.codiceFiscaleCorrentista = codiceFiscaleCorrentista;
	}



	public String getNomeCorrentista() {
		return nomeCorrentista;
	}

	public void setNomeCorrentista(String nomeCorrentista) {
		this.nomeCorrentista = nomeCorrentista;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
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
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db                Description of Parameter
	 * @param  org_id            Description of Parameter
	 * @exception  SQLException  Description of the Exception
	 * @throws  SQLException     Description of the Exception
	 * @throws  SQLException     Description of Exception
	 */
	public Organization(Connection db, int org_id) throws SQLException {
		if (org_id == -1) {
			throw new SQLException("Invalid Account");
		} 
		PreparedStatement pst = db.prepareStatement(
				"SELECT o.* " +
				
				"FROM organization o " +
				"WHERE o.org_id = ? ");
		pst.setInt(1, org_id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
		if (rs.next()) {
			buildRecord(rs);
		}
		rs.close();
		pst.close();
		if (orgId == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}


		addressList.setOrgId(this.getOrgId());
		addressList.buildList(db);

		this.livelloRischioFinale = getLivelloRischioFinale(db);
		this.dataAudit = getDataAudit(db);
	}

	



	/**
	 *  Sets the EnteredByName attribute of the Organization object
	 *
	 * @param  enteredByName  The new EnteredByName value
	 */
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}


	/**
	 *  Sets the ModifiedByName attribute of the Organization object
	 *
	 * @param  modifiedByName  The new ModifiedByName value
	 */
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}



	/**
	 *  Sets the trashedDate attribute of the Organization object
	 *
	 * @param  tmp  The new trashedDate value
	 */
	public void setTrashedDate(Timestamp tmp) {
		this.trashedDate = tmp;
	}


	/**
	 *  Sets the trashedDate attribute of the Organization object
	 *
	 * @param  tmp  The new trashedDate value
	 */
	public void setTrashedDate(String tmp) {
		this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
	}




	/**
	 *  Gets the trashedDate attribute of the Organization object
	 *
	 * @return    The trashedDate value
	 */
	public Timestamp getTrashedDate() {
		return trashedDate;
	}




	/**
	 *  Sets the Entered attribute of the Organization object
	 *
	 * @param  tmp  The new Entered value
	 */
	public void setEntered(java.sql.Timestamp tmp) {
		this.entered = tmp;
	}




	/**
	 *  Sets the Modified attribute of the Organization object
	 *
	 * @param  tmp  The new Modified value
	 */
	public void setModified(java.sql.Timestamp tmp) {
		this.modified = tmp;
	}


	/**
	 *  Sets the entered attribute of the Organization object
	 *
	 * @param  tmp  The new entered value
	 */
	public void setEntered(String tmp) {
		this.entered = DateUtils.parseTimestampString(tmp);
	}


	/**
	 *  Sets the modified attribute of the Organization object
	 *
	 * @param  tmp  The new modified value
	 */
	public void setModified(String tmp) {
		this.modified = DateUtils.parseTimestampString(tmp);
	}




	/**
	 *  Sets the ContractEndDate attribute of the Organization object
	 *
	 * @param  contractEndDate  The new ContractEndDate value
	 */
	public void setContractEndDate(java.sql.Timestamp contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public void setDate1(java.sql.Timestamp val) {
		this.date1 = val;
	}
	public void setDate2(java.sql.Timestamp val) {
		this.date2 = val;
	}

	/**
	 *  Sets the YTD attribute of the Organization object
	 *
	 * @param  YTD  The new YTD value
	 */
	public void setYTD(double YTD) {
		this.YTD = YTD;
	}


	/**
	 *  Sets the ContractEndDate attribute of the Organization object
	 *
	 * @param  tmp  The new ContractEndDate value
	 */
	public void setContractEndDate(String tmp) {
		this.contractEndDate = DatabaseUtils.parseDateToTimestamp(tmp);
	}
	public void setDate1(String tmp) {
		this.date1 = DatabaseUtils.parseDateToTimestamp(tmp);
	}
	public void setDate2(String tmp) {
		this.date2 = DatabaseUtils.parseDateToTimestamp(tmp);
	}




	/**
	 *  Sets the orgId attribute of the Organization object
	 *
	 * @param  tmp  The new orgId value
	 */
	public void setOrgId(int tmp) {
		this.orgId = tmp;
		addressList.setOrgId(tmp);

	}


	/**
	 *  Sets the ModifiedBy attribute of the Organization object
	 *
	 * @param  modifiedBy  The new ModifiedBy value
	 */
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	/**
	 *  Sets the ModifiedBy attribute of the Organization object
	 *
	 * @param  modifiedBy  The new ModifiedBy value
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = Integer.parseInt(modifiedBy);
	}


	/**
	 *  Sets the AccountNumber attribute of the Organization obA  9
	 *
	 * @param  accountNumber  The new AccountNumber value
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	/**
	 *  Sets the OrgId attribute of the Organization object
	 *
	 * @param  tmp  The new OrgId value
	 */
	public void setOrgId(String tmp) {
		this.setOrgId(Integer.parseInt(tmp));
	}




	/**
	 *  Sets the Name attribute of the Organization object
	 *
	 * @param  tmp  The new Name value
	 */
	public void setName(String tmp) {
		this.name = tmp;
	}




	/**
	 *  Sets the Notes attribute of the Organization object
	 *
	 * @param  tmp  The new Notes value
	 */
	public void setNotes(String tmp) {
		this.notes = tmp;
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
	 *  Sets the siteId attribute of the Organization object
	 *
	 * @param  siteId  The new siteId value
	 */
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}


	/**
	 *  Sets the siteId attribute of the Organization object
	 *
	 * @param  tmp  The new siteId value
	 */
	public void setSiteId(String tmp) {
		this.siteId = Integer.parseInt(tmp);
	}

	/**
	 *  Gets the siteId attribute of the Organization object
	 *
	 * @return    The siteId value
	 */
	public int getSiteId() {
		return siteId;
	}


	/**
	 *  Sets the siteId attribute of the Organization object
	 *
	 * @param  siteId  The new siteId value
	 */
	public void setTipologiaId(int tipologiaId) {
		this.tipologiaId = tipologiaId;
	}


	/**
	 *  Sets the siteId attribute of the Organization object
	 *
	 * @param  tmp  The new siteId value
	 */
	public void setTipologiaId(String tmp) {
		this.tipologiaId = Integer.parseInt(tmp);
	}

	/**
	 *  Gets the siteId attribute of the Organization object
	 *
	 * @return    The siteId value
	 */
	public int getTipologiaId() {
		return tipologiaId;
	}

	/**
	 *  Sets the AddressList attribute of the Organization object
	 *
	 * @param  tmp  The new AddressList value
	 */
	public void setAddressList(OrganizationAddressList tmp) {
		this.addressList = tmp;
	}


	/**
	 *  Sets the Enteredby attribute of the Organization object
	 *
	 * @param  tmp  The new Enteredby value
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}


	/**
	 *  Sets the EnteredBy attribute of the Organization object
	 *
	 * @param  tmp  The new EnteredBy value
	 */
	public void setEnteredBy(String tmp) {
		this.enteredBy = Integer.parseInt(tmp);
	}



	/**
	 *  Since dynamic fields cannot be auto-populated, passing the request to this
	 *  method will populate the indicated fields.
	 *
	 * @param  context  The new requestItems value
	 * @since           1.15
	 */
	public void setRequestItems(ActionContext context) {
		addressList = new OrganizationAddressList(context.getRequest());
	}


	/**
	 *  Gets the YTDValue attribute of the Organization object
	 *
	 * @return    The YTDValue value
	 */
	public String getYTDValue() {
		double value_2dp = (double) Math.round(YTD * 100.0) / 100.0;
		String toReturn = String.valueOf(value_2dp);
		if (toReturn.endsWith(".0")) {
			toReturn = toReturn.substring(0, toReturn.length() - 2);
		}

		if (Integer.parseInt(toReturn) == 0) {
			toReturn = "";
		}

		return toReturn;
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
	 *  Gets the ContractEndDate attribute of the Organization object
	 *
	 * @return    The ContractEndDate value
	 */
	public java.sql.Timestamp getContractEndDate() {
		return contractEndDate;
	}
	public java.sql.Timestamp getDate1() {
		return date1;
	}
	public java.sql.Timestamp getDate2() {
		return date2;
	}
	/**
	 *  Gets the ContractEndDateString attribute of the Organization object
	 *
	 * @return    The ContractEndDateString value
	 */
	public String getContractEndDateString() {
		String tmp = "";
		try {
			return DateFormat.getDateInstance(3).format(contractEndDate);
		} catch (NullPointerException e) {
		}
		return tmp;
	}
	public String getDate1String() {
		String tmp = "";
		try {
			return DateFormat.getDateInstance(3).format(date1);
		} catch (NullPointerException e) {
		}
		return tmp;
	}
	public String getDate2String() {
		String tmp = "";
		try {
			return DateFormat.getDateInstance(3).format(date2);
		} catch (NullPointerException e) {
		}
		return tmp;
	}

	/**
	 *  Gets the contractEndDateBufferedString attribute of the Organization
	 *  object
	 *
	 * @return    The contractEndDateBufferedString value
	 */
	public String getContractEndDateBufferedString() {
		String tmp = "None";
		try {
			return DateFormat.getDateInstance(3).format(contractEndDate);
		} catch (NullPointerException e) {
		}
		return tmp;
	}
	public String getDate1BufferedString() {
		String tmp = "None";
		try {
			return DateFormat.getDateInstance(3).format(date1);
		} catch (NullPointerException e) {
		}
		return tmp;
	}
	public String getDate2BufferedString() {
		String tmp = "None";
		try {
			return DateFormat.getDateInstance(3).format(date2);
		} catch (NullPointerException e) {
		}
		return tmp;
	}






	/**
	 *  Gets the EnteredByName attribute of the Organization object
	 *
	 * @return    The EnteredByName value
	 */
	public String getEnteredByName() {
		return enteredByName;
	}


	/**
	 *  Gets the ModifiedByName attribute of the Organization object
	 *
	 * @return    The ModifiedByName value
	 */
	public String getModifiedByName() {
		return modifiedByName;
	}



	/**
	 *  Gets the AccountNumber attribute of the Organization object
	 *
	 * @return    The AccountNumber value
	 */
	public String getAccountNumber() {
		return accountNumber;
	}




	/**
	 *  Gets the orgId attribute of the Organization object
	 *
	 * @return    The orgId value
	 */
	public int getOrgId() {
		return orgId;
	}


	/**
	 *  Gets the id attribute of the Organization object
	 *
	 * @return    The id value
	 */
	public int getId() {
		return orgId;
	}


	/**
	 *  Gets the Name attribute of the Organization object
	 *
	 * @return    The Name value
	 */
	public String getName() {
		if (name != null && name.trim().length() > 0) {
			return name;
		}
		return "" ;
	}


	/**
	 *  Gets the accountNameOnly attribute of the Organization object
	 *
	 * @return    The accountNameOnly value
	 */
	public String getAccountNameOnly() {
		return name;
	}




	/**
	 *  Gets the Notes attribute of the Organization object
	 *
	 * @return    The Notes value
	 */
	public String getNotes() {
		return notes;
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
	 *  Gets the AccountSize attribute of the Organization object
	 *
	 * @return    The IndustryName value
	 */
	public String getAccountSizeName() {
		return accountSizeName;
	}




	/**
	 *  Gets the Address attribute of the Organization object
	 *
	 * @param  thisType  Description of Parameter
	 * @return           The Address value
	 */
	public Address getAddress(String thisType) {
		return getAddressList().getAddress(thisType);
	}



	/**
	 *  Gets the primaryAddress attribute of the Organization object
	 *
	 * @return    The primaryAddress value
	 */
	public Address getPrimaryAddress() {
		return getAddressList().getPrimaryAddress();
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
	 *  Gets the AddressList attribute of the Organization object
	 *
	 * @return    The AddressList value
	 */
	public OrganizationAddressList getAddressList() {
		return addressList;
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







	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of the Exception
	 */
	public boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			modifiedBy = enteredBy;

			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			
		    orgId =  DatabaseUtils.getNextSeq(db,context, "organization","org_id");

			sql.append(
					"INSERT INTO organization (name, account_number," +
			"site_id,impianto, ip_entered,ip_modified, ");
			if (orgId > -1) {
				sql.append("org_id, ");
			}
			if (entered != null) {
				sql.append("entered, ");
			}

			if (modified != null) {
				sql.append("modified, ");
			}

			sql.append("enteredBy, modifiedBy, tipologia");

			if ((dataPresentazione != null)&&(!dataPresentazione.equals(""))) {

				sql.append(", datapresentazione");
			}

			if ((dataPresentazione != null)&&(!dataPresentazione.equals(""))) {
				sql.append(", prossimo_controllo");
			}



			if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
				sql.append(", nome_rappresentante ");
			}
			if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
				sql.append(", cognome_rappresentante ");
			}
			if ( ( dataNascitaRappresentante != null) && !"".equals( dataNascitaRappresentante ) ) {
				sql.append(", data_nascita_rappresentante ");
			}
			if ( ( luogoNascitaRappresentante != null) && !"".equals( luogoNascitaRappresentante ) ) {
				sql.append(", luogo_nascita_rappresentante ");
			}
			
			if ( ( emailRappresentante != null) && !"".equals( emailRappresentante ) ) {
				sql.append(", email_rappresentante ");
			}
			if ( ( telefonoRappresentante != null) && !"".equals( telefonoRappresentante ) ) {
				sql.append(", telefono_rappresentante ");
			}
			if ( ( fax != null) && !"".equals( fax ) ) {
				sql.append(", fax ");
			}
			if ( ( partitaIva != null) && !"".equals( partitaIva ) ) {
				sql.append(", partita_iva ");
			}
			if ( ( statoLab != null) && statoLab>-1 ) {
				sql.append(", stato_lab ");
			}
			sql.append(", categoria_rischio)");
			sql.append("VALUES (?,?,?,?,?,?,");

			if (orgId > -1) {
				sql.append("?,");
			}
			if (entered != null) {
				sql.append("?, ");
			}

			if (modified != null) {
				sql.append("?, ");
			}
			//fine campi nuovi

			sql.append("?,?,12");

			if ((dataPresentazione != null)&&(!dataPresentazione.equals(""))) {

				sql.append(", ? ");
			}


			if ((dataPresentazione != null)&&(!dataPresentazione.equals(""))) {
				sql.append(", ? ");
			}


			if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
				sql.append(", ? ");
			}
			if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
				sql.append(", ? ");
			}
			if ( ( dataNascitaRappresentante != null) && !"".equals( dataNascitaRappresentante ) ) {
				sql.append(", ? ");
			}
			if ( ( luogoNascitaRappresentante != null) && !"".equals( luogoNascitaRappresentante ) ) {
				sql.append(", ? ");
			}
			
			if ( ( emailRappresentante != null) && !"".equals( emailRappresentante ) ) {
				sql.append(", ? ");
			}
			if ( ( telefonoRappresentante != null) && !"".equals( telefonoRappresentante ) ) {
				sql.append(", ? ");
			}

			if ( ( fax != null) && !"".equals( fax ) ) {
				sql.append(", ? ");
			}
			if ( ( partitaIva != null) && !"".equals( partitaIva ) ) {
				sql.append(", ? ");
			}
			if ( ( statoLab != null) && statoLab>-1 ) {
				sql.append(", ? ");
			}

			sql.append(", ?)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setString(++i, this.getName());

			pst.setString(++i, this.getAccountNumber());
			DatabaseUtils.setInt(pst, ++i, this.getSiteId());
			DatabaseUtils.setInt(pst, ++i, this.getTipologiaId());
			pst.setString(++i,ip_entered);
			pst.setString(++i, ip_modified);
			if (orgId > -1) {
				pst.setInt(++i, orgId);
			}
			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}

			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}


			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getModifiedBy());
			//campi stabilimenti


			if ((dataPresentazione != null)&&(!dataPresentazione.equals(""))) {

				pst.setTimestamp(++i, dataPresentazione);

			}


			if ((dataPresentazione != null)&&(!dataPresentazione.equals(""))) {


				Timestamp prossimo_controllo = new Timestamp(dataPresentazione.getTime())  ;
				prossimo_controllo.setDate(prossimo_controllo.getDate()+30);
				pst.setTimestamp(++i, prossimo_controllo);
			}





			if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
				pst.setString(++i, this.getNomeRappresentante());
			}
			if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
				pst.setString(++i, this.getCognomeRappresentante());
			}
			if ( ( dataNascitaRappresentante != null) && !"".equals( dataNascitaRappresentante ) ) {
				pst.setTimestamp(++i, dataNascitaRappresentante);
			}
			if ( ( luogoNascitaRappresentante != null) && !"".equals( luogoNascitaRappresentante ) ) {
				pst.setString(++i, this.getLuogoNascitaRappresentante());
			}
			
			
			if ( ( emailRappresentante != null) && !"".equals( emailRappresentante ) ) {
				pst.setString(++i, this.getEmailRappresentante());
			}
			if ( ( telefonoRappresentante != null) && !"".equals( telefonoRappresentante ) ) {
				pst.setString(++i, this.getTelefonoRappresentante());
			}
			if ( ( fax != null) && !"".equals( fax ) ) {
				pst.setString(++i, this.getFax());
			}
			if ( ( partitaIva != null) && !"".equals( partitaIva ) ) {
				pst.setString(++i, this.getPartitaIva());
			}
			if ( ( statoLab != null) && statoLab>-1 ) {
				pst.setInt(++i, this.getStatoLab());
			}

			pst.setInt(++i, this.getCategoriaRischio());
			pst.execute();
			pst.close();

			


			//Insert the addresses if there are any
			Iterator iaddress = getAddressList().iterator();
			while (iaddress.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				//thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());

				thisAddress.process(context,
						db, orgId, this.getEnteredBy(), this.getModifiedBy());
			}



			// this.update(db, true);
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
		return true;
	}

	

	public String getPaddedCodiceAutorizzazzione(int nextSeq) {
		String padded = (String.valueOf(nextSeq));
		while (padded.length() < 5) {
			padded = "0" + padded;
		}
		return padded;
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
					thisAddress.process(context,
							db, orgId, this.getEnteredBy(), this.getModifiedBy());
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
				"UPDATE organization SET name = ?, ");
		sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
		sql.append( "modifiedby = ?,ip_modified=? ");
		sql.append(
				",datapresentazione = ?, " +
				"nome_rappresentante=?," +
				"telefono_Rappresentante=?," +
				"fax=?," +
				"email_rappresentante=?," +
				"account_number=? , " +
				"cognome_rappresentante = ? ," +
				"data_nascita_rappresentante = ? ," +
				"luogo_nascita_rappresentante=? ," +
		"site_id=?, impianto = ? where org_id =? ");

		int i = 0 ;
		pst = db.prepareStatement(sql.toString());
		pst.setString(++i, name);
		pst.setInt(++i, modifiedBy);
		pst.setString(++i, ip_modified);



		pst.setTimestamp(++i, dataPresentazione);
		pst.setString(++i, nomeRappresentante);
		pst.setString(++i, telefonoRappresentante);
		pst.setString(++i, fax);
		pst.setString(++i, emailRappresentante);
		pst.setString(++i, accountNumber);
		pst.setString(++i, cognomeRappresentante);
		pst.setTimestamp(++i, getDataNascitaRappresentante());
		pst.setString(++i, luogoNascitaRappresentante);

		pst.setInt(++i, siteId);
		pst.setInt(++i, tipologiaId);
		pst.setInt(++i, orgId);

		//fine campi nuovi    
		resultCount = pst.executeUpdate();
		pst.close();

		// When an account name gets updated,
		// the stored org_name in contact needs to be updated



		return resultCount;
	}

	public String getPaddedId(int sequence) {
		String padded = (String.valueOf(sequence));
		while (padded.length() < 6) {
			padded = "0" + padded;
		}
		return padded;
	}



	


	/**
	 *  Description of the Method
	 *
	 * @return    Description of the Returned Value
	 */
	public String toString() {
		StringBuffer out = new StringBuffer();
		out.append("=================================================\r\n");
		out.append("Organization Name: " + this.getName() + "\r\n");
		out.append("ID: " + this.getOrgId() + "\r\n");
		return out.toString();
	}

	public String getDataPresenazioneString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dataPresentazione!=null)
		{
			return sdf.format(new Date(dataPresentazione.getTime()));
		}
		return "" ;
	}
	/**
	 *  Description of the Method
	 *
	 * @param  rs             Description of Parameter
	 * @throws  SQLException  Description of Exception
	 */
	protected void buildRecord(ResultSet rs) throws SQLException {
		//organization table
		categoriaRischio=rs.getInt("categoria_rischio");
		this.setOrgId(rs.getInt("org_id"));
		name = rs.getString("name");
		dataPresentazione = rs.getTimestamp("datapresentazione");
		accountNumber = rs.getString("account_number");
		telefonoRappresentante = rs.getString("telefono_rappresentante");
		emailRappresentante = rs.getString("email_rappresentante");
		tipologia = rs.getInt("tipologia");
		notes = rs.getString("notes");
		dataProssimoControllo = rs.getTimestamp("prossimo_controllo");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		contractEndDate = rs.getTimestamp("contract_end");
		date1 = rs.getTimestamp("date1");
		date2 = rs.getTimestamp("date2");
		trashedDate = rs.getTimestamp("trashed_date");
		statoLab = DatabaseUtils.getInt(rs, "stato_lab");
		accountSize = DatabaseUtils.getInt(rs, "account_size");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		tipologiaId = DatabaseUtils.getInt(rs, "impianto");
		partitaIva = rs.getString("partita_iva");
		codiceFiscale = rs.getString("codice_fiscale");
		nomeCorrentista = rs.getString("nome_correntista");
		codiceFiscaleCorrentista = rs.getString("cf_correntista");
		titoloRappresentante = rs.getInt("titolo_rappresentante");
		codiceFiscaleRappresentante = rs.getString("codice_fiscale_rappresentante");
		nomeRappresentante = rs.getString("nome_rappresentante");
		cognomeRappresentante = rs.getString("cognome_rappresentante");
		nomeRappresentante = rs.getString("nome_rappresentante");
		emailRappresentante = rs.getString("email_rappresentante");
		telefonoRappresentante = rs.getString("telefono_rappresentante");
		dataNascitaRappresentante = rs.getTimestamp("data_nascita_rappresentante");
		luogoNascitaRappresentante = rs.getString("luogo_nascita_rappresentante");
		fax = rs.getString("fax");
	}






	public int getLivelloRischio(Connection db) throws SQLException {
		int livelloRischio = -1;
		PreparedStatement pst = db.prepareStatement("SELECT livello_rischio FROM audit WHERE org_id = ? ORDER BY data_1 DESC");
		pst.setInt(1, this.getOrgId());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			livelloRischio = rs.getInt("livello_rischio");
		}
		rs.close();
		pst.close();
		return livelloRischio;
	}

	public int getIdImpianto(String attivita,Connection db) throws SQLException {
		int livelloRischio = -1;
		PreparedStatement pst = db.prepareStatement("SELECT code FROM  lookup_impianto_osm WHERE description=?");
		pst.setString(1, attivita);
		ResultSet rs = pst.executeQuery();
		int idImpianto=-1;
		if (rs.next()) {
			idImpianto = rs.getInt("code");
		}
		rs.close();
		pst.close();
		return idImpianto;
	}


	public int getLivelloRischioFinale(Connection db) throws SQLException {
		int livelloRischio = -1;
		PreparedStatement pst = db.prepareStatement("SELECT livello_rischio_finale FROM audit WHERE org_id = ? ORDER BY data_2 IS NULL, data_2 DESC");
		pst.setInt(1, this.getOrgId());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			livelloRischio = rs.getInt("livello_rischio_finale");
		}
		rs.close();
		pst.close();
		return livelloRischio;
	}

	public Timestamp getDataAudit(Connection db) throws SQLException {
		Timestamp data = null;
		PreparedStatement pst = db.prepareStatement("SELECT data_2 FROM audit WHERE org_id = ? ORDER BY data_2 IS NULL, data_2 DESC");
		pst.setInt(1, this.getOrgId());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			data = rs.getTimestamp("data_2");
		}
		rs.close();
		pst.close();
		return data;
	}
	
	
	public Integer getStatoLab() {
		return statoLab;
	}
	
	public void setStatoLab(String tmp) {
		this.statoLab = Integer.parseInt(tmp);
	}
	
	
	public String getDecodificaStatoLab(Connection db) {
		LookupList statoLab = new LookupList();
		try {

			statoLab.setTable("lookup_stato_lab_impianti");
			statoLab.buildList(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return statoLab.getSelectedValue(this.getStatoLab());
	}
	
//	public void cessazioneAttivita(Connection db,String noteCessazione,int userId) throws SQLException
//	{
//		PreparedStatement pst = db.prepareStatement("UPDATE organization set modified=current_timestamp,modifiedby=?, stato_lab = ?, contract_end = ?, data_fine_carattere=? , date2 = ? ,cessato =1,data_cessazione_attivita=?,note_cessazione_attivita=? where org_id=?");
//		int i = 0;	
//		pst.setInt(++i, userId);
//		pst.setInt(++i, 4);
//		pst.setTimestamp(++i, date2);
//		pst.setTimestamp(++i, date2);
//		pst.setTimestamp(++i, date2);
//		pst.setTimestamp(++i, date2);
//		pst.setString(++i, noteCessazione);
//		pst.setInt(++i, this.orgId);
//		pst.execute();
//		
//	}
}

