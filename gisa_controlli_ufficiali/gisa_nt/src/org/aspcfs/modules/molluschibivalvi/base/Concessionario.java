/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.molluschibivalvi.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Concessionario extends GenericBean {
	private static Logger log = Logger.getLogger(org.aspcfs.modules.molluschibivalvi.base.Concessionario.class);

	private int id ;
	private String name ;
	private String titolareNome ;

	private String cfTitolare;
	private Vector comuni=new Vector()	;
	private java.sql.Timestamp entered = null		;
	private java.sql.Timestamp modified = null	;
	private int enteredBy 						;
	private int modifiedBy 						;
	private int tipologia 						;
	private int siteId		;
	private String ipEntered ;
	private String ipModified ;
	private int idZona ;
	private String accountNumber ;
	ConcessioniList listaConcessioni = new ConcessioniList();
	

	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIpEntered() {
		return ipEntered;
	}
	public void setIpEntered(String ipEntered) {
		this.ipEntered = ipEntered;
	}
	public String getIpModified() {
		return ipModified;
	}
	public void setIpModified(String ipModified) {
		this.ipModified = ipModified;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTitolareNome() {
		return titolareNome;
	}
	public void setTitolareNome(String titolareNome) {
		this.titolareNome = titolareNome;
	}
	
	public String getCfTitolare() {
		return cfTitolare;
	}
	public void setCfTitolare(String cfTitolare) {
		this.cfTitolare = cfTitolare;
	}
	public OrganizationAddressList getAddressList() {
		return addressList;
	}
	public void setAddressList(OrganizationAddressList addressList) {
		this.addressList = addressList;
	}
	private OrganizationAddressList addressList = new OrganizationAddressList ();
	
	public Vector getComuni() {
		return comuni;
	}


	public void setComuni(Vector comuni) {
		this.comuni = comuni;
	}
	
	public Concessionario(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	public Concessionario() 
	{
		
	}
	public Concessionario(Connection db, int org_id) throws SQLException 
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
		listaConcessioni.setIdConcessionario(this.getId());
		
		listaConcessioni.buildList(db);
	}
	
	


	public ConcessioniList getListaConcessioni() {
		return listaConcessioni;
	}
	public void setListaConcessioni(ConcessioniList listaConcessioni) {
		this.listaConcessioni = listaConcessioni;
	}
	public boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		boolean ret = false;
		try
		{
			if (doCommit = db.getAutoCommit()) 
			{
				db.setAutoCommit(false);
			}
			
		      id =  DatabaseUtils.getNextSeq(db,context, "organization","org_id");

			
			String insert = "INSERT INTO ORGANIZATION (org_id,name,site_id,enteredby,modifiedby,entered," +
					"modified,ip_entered,ip_modified,nome_rappresentante,codice_fiscale,tipologia) " +
			" values (?,?,?,?,?,current_date,current_date,?,?,?,?,211)" ;
			PreparedStatement pst = db.prepareStatement(insert);
			int i = 0 ;
			pst.setInt(++i,id);
			pst.setString(++i,name);
			pst.setInt(++i,siteId);
			pst.setInt(++i,enteredBy);
			pst.setInt(++i,modifiedBy);
			pst.setString(++i,ipEntered);
			pst.setString(++i,ipModified);
			pst.setString(++i,titolareNome);
			pst.setString(++i,cfTitolare);

			pst.execute();
			Iterator iaddress = addressList.iterator();
			while (iaddress.hasNext()) 
			{
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
				if((thisAddress.getCity()!=null) && !(thisAddress.getCity().equals(""))) 
				{
					thisAddress.process(db, id, this.getEnteredBy(), this.getModifiedBy(),context);
				}
			}
			ret = true;
			if(doCommit)
				db.commit();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			if (doCommit) 
			{
				db.rollback();
				ret = false;
				throw e;
			}
		} finally 
		{
			if (doCommit) 
			{
				db.setAutoCommit(true);
			}
		}
		return ret ;
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
		int i = 0 ;
		StringBuffer sql = new StringBuffer();
		sql.append(	"UPDATE organization " +
				"SET site_id = ?,  name = ?, modifiedby=?,modified =? " +
		",nome_rappresentante = ? , codice_fiscale = ?,account_number=?  where org_id = ?" );
		pst = db.prepareStatement(sql.toString());
		pst.setInt(++i, siteId) 					;
		pst.setString(++i, name) 					;
		
		pst.setInt(++i, modifiedBy) 	;
		pst.setTimestamp(++i, modified) ;
		pst.setString(++i, titolareNome) 	;
		pst.setString(++i, cfTitolare) 		;
		pst.setString(++i, accountNumber) 		;

		pst.setInt(++i, id)			;
		pst.execute();
		return resultCount;
	}
	
	




	protected void buildRecord(ResultSet rs) throws SQLException {

		id 		= 	rs.getInt("org_id");
		name			=	rs.getString("name");
		siteId		=	rs.getInt("site_id");
		titolareNome 			=	rs.getString("nome_rappresentante"); // contiene nome e cognome
		cfTitolare 			=	rs.getString("codice_fiscale");
		modified		=	rs.getTimestamp("modified");
		modifiedBy	=	rs.getInt("modifiedby");
		entered 		= 	rs.getTimestamp("entered");
		enteredBy		=	rs.getInt("enteredby");
		tipologia 	= 	rs.getInt("tipologia");
		ipEntered			 	= 	rs.getString("ip_entered");
		ipModified 			= 	rs.getString("ip_modified");
		accountNumber  = rs.getString("account_number");
		
	}

	public void setRequestItems(ActionContext context) {
		addressList = new OrganizationAddressList(context.getRequest());

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
	
	

}
