/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

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


import com.darkhorseventures.framework.actions.ActionContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;
import java.util.ArrayList;

/**
 *  Contains a list of indirizzi... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @created    August 30, 2001
 * @version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class SedeList extends Vector implements SyncableList {

	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.SedeList.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}



	public final static String tableName = "indirizzo";
	public final static String uniqueField = "id";
	protected Boolean minerOnly = null;
	protected int typeId = 0;
	protected int stageId = -1;
	protected java.sql.Timestamp lastAnchor = null;
	protected java.sql.Timestamp nextAnchor = null;
	protected int syncType = Constants.NO_SYNC;
	protected PagedListInfo pagedListInfo = null;
	private int idIndirizzo = -1;
	private String cap;
	private String comune;
	private String provincia;
	private String via;
	private String nazione;
	private double latitudine;
	private double longitudine;
	private int idComune ;
	private int idOperatore = -1;
	private int idStabilimento = -1;
	private int enteredBy;
	private int modifiedBy;
	private String ipEnteredBy;
	private String ipModifiedBy;
	private int idTipologia = -1;
	private int onlyActive = -1;




	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public void setStageId(String tmp) {
		if (tmp!=null){
			this.stageId = Integer.parseInt(tmp);
		}else{this.stageId = -1;}
	}

	public Boolean getMinerOnly() {
		return minerOnly;
	}

	public void setMinerOnly(Boolean minerOnly) {
		this.minerOnly = minerOnly;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getIdComune() {
		return idComune;
	}

	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}

	public void setIdComune(String idComune) {
		if (idComune != null && !"".equals(idComune))
			this.idComune = Integer.parseInt(idComune);
	}

	public int getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public double getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}

	public double getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
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

	public String getIpEnteredBy() {
		return ipEnteredBy;
	}

	public void setIpEnteredBy(String ipEnteredBy) {
		this.ipEnteredBy = ipEnteredBy;
	}

	public String getIpModifiedBy() {
		return ipModifiedBy;
	}

	public void setIpModifiedBy(String ipModifiedBy) {
		this.ipModifiedBy = ipModifiedBy;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		SedeList.log = log;
	}

	public int getIdTipologia() {
		return idTipologia;
	}

	public void setIdTipologia(int idTipologia) {
		this.idTipologia = idTipologia;
	}

	public int getOnlyActive() {
		return onlyActive;
	}

	public void setOnlyActive(int onlyActive) {
		this.onlyActive = onlyActive;
	}

	public java.sql.Timestamp getLastAnchor() {
		return lastAnchor;
	}

	public java.sql.Timestamp getNextAnchor() {
		return nextAnchor;
	}

	public int getSyncType() {
		return syncType;
	}

	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}


	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}

	
	public void setLastAnchor(java.sql.Timestamp tmp) {
		this.lastAnchor = tmp;
	}

	public void setLastAnchor(String tmp) {
		this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
	}


	/**
	 *  Sets the nextAnchor attribute of the OrganizationList object
	 *
	 * @param  tmp  The new nextAnchor value
	 */
	public void setNextAnchor(java.sql.Timestamp tmp) {
		this.nextAnchor = tmp;
	}


	/**
	 *  Sets the nextAnchor attribute of the OrganizationList object
	 *
	 * @param  tmp  The new nextAnchor value
	 */
	public void setNextAnchor(String tmp) {
		this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
	}


	/**
	 *  Sets the syncType attribute of the OrganizationList object
	 *
	 * @param  tmp  The new syncType value
	 */
	public void setSyncType(int tmp) {
		this.syncType = tmp;
	}


	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}








	/**
	 *  Queries the database, using any of the filters, to retrieve a list of
	 *  organizations. The organizations are appended, so build can be run any
	 *  number of times to generate a larger list for a report.
	 *
	 * @param  db             Description of Parameter
	 * @throws  SQLException  Description of Exception
	 * @since                 1.1
	 */


	public void buildListSediOperatore(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryListSediOperatore(db, pst);
		while (rs.next()) {
			Indirizzo thisIndirizzo = this.getObject(rs);

			this.add(thisIndirizzo);

		}
		if (this.size()==0)
		{
			Indirizzo sedeLegaleVuota = new Indirizzo();
			sedeLegaleVuota.setTipologiaSede(1);
			this.add(sedeLegaleVuota);
		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		
	}
	
	public void buildListSediOperatoreStorico(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryListSediOperatoreStorico(db, pst);
		while (rs.next()) {
			Indirizzo thisIndirizzo = this.getObject(rs);
			thisIndirizzo.setModifiedBy(rs.getInt("modified_by"));
			thisIndirizzo.setModified(rs.getTimestamp("data_inizio"));
			this.add(thisIndirizzo);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		
	}





	public void buildSediListStabilimento(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryListSediStabilimento(db, pst);
		while (rs.next()) {
			Indirizzo thisIndirizzo = this.getObject(rs);

			this.add(thisIndirizzo);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}


	/**
	 *  Gets the object attribute of the OrganizationList object
	 *
	 * @param  rs             Description of the Parameter
	 * @return                The object value
	 * @throws  SQLException  Description of the Exception
	 */
	public Indirizzo getObject(ResultSet rs) throws SQLException {
		Indirizzo thisInd = new Indirizzo(rs);
		return thisInd;
	}


	/**
	 *  This method is required for synchronization, it allows for the resultset
	 *  to be streamed with lower overhead
	 *
	 * @param  db             Description of the Parameter
	 * @param  pst            Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	public ResultSet queryListSediOperatore(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for counting records

		sqlCount.append(
				"SELECT  COUNT(distinct i.*) AS recordcount " +
				"FROM indirizzo i left join apicoltura_relazione_imprese_sede_legale r on (r.id_indirizzo = i.id and r.enabled = true)  " +
				"left join apicoltura_imprese o on (o.id = r.id_apicoltura_imprese) " +
		"left join comuni1 on (cast(comuni1.id as char(1000) )= i.comune) ");


		if (idOperatore > -1){

			sqlCount.append("");
		}



		sqlCount.append(" WHERE i.id >= 0 ");

		createFilterOperatore(db, sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString());
			// UnionAudit(sqlFilter,db);

			items = prepareFilterOperatore(pst);


			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(
						sqlCount.toString() +
						sqlFilter.toString() +
						"AND " + DatabaseUtils.toLowerCase(db) + "(i.comune) < ? ");
				items = prepareFilterOperatore(pst);
				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
				rs = pst.executeQuery();
				if (rs.next()) {
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("i.comune");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY i.comune ");
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}

		sqlSelect.append(
				"distinct i.*, r.tipologia_sede,comuni1.cod_provincia,comuni1.nome as descrizione_comune,'R'||comuni1.codiceasl_bdn as codice_asl,lp.description as descrizione_provincia,comuni1.cod_comune as istat_comune,lp.cod_provincia as sigla_provincia " +
				"FROM opu_indirizzo i left join apicoltura_relazione_imprese_sede_legale r on r.id_indirizzo = i.id and r.enabled = true " +
				"left join apicoltura_imprese o on o.id = r.id_apicoltura_imprese " +
				"left join comuni1 on (comuni1.id = i.comune) " +

		"left join lookup_province lp on lp.code = comuni1.cod_provincia::int ");



		if (idOperatore > -1){

			sqlCount.append("");
		}


		sqlSelect.append("WHERE i.id >= 0 ");




		pst = db.prepareStatement(
				sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilterOperatore(pst);



		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}


		rs = pst.executeQuery();
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}
	
	
	
	
	
	public ResultSet queryListSediOperatoreStorico(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for counting records

		sqlCount.append(
				"SELECT  COUNT(distinct i.*) AS recordcount " +
				"FROM indirizzo i left join relazione_operatore_sede r on (r.id_indirizzo = i.id and r.enabled = true)  " +
				"left join operatore o on (o.id = r.id_apicoltura_imprese) " +
		"left join comuni1 on (cast(comuni1.id as char(1000) )= i.comune) ");


		if (idOperatore > -1){

			sqlCount.append("");
		}



		sqlCount.append(" WHERE i.id >= 0 ");

		createFilterOperatore(db, sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString());
			// UnionAudit(sqlFilter,db);

			items = prepareFilterOperatore(pst);


			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(
						sqlCount.toString() +
						sqlFilter.toString() +
						"AND " + DatabaseUtils.toLowerCase(db) + "(i.comune) < ? ");
				items = prepareFilterOperatore(pst);
				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
				rs = pst.executeQuery();
				if (rs.next()) {
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("data_inizio");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY data_inizio ");
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}

		sqlSelect.append(
				"distinct i.*, r.tipologia_sede,comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia ,data_inizio ,r.modified_by " +
				"FROM opu_indirizzo i left join apicoltura_relazione_imprese_sede_legale r on r.id_indirizzo = i.id  " +
				"left join apicoltura_imprese o on o.id = r.id_apicoltura_imprese " +
				"left join comuni1 on (comuni1.id = i.comune) " +
		"left join lookup_province lp on lp.code = comuni1.cod_provincia::int ");



		if (idOperatore > -1){

			sqlCount.append("");
		}


		sqlSelect.append("WHERE i.id >= 0 ");




		pst = db.prepareStatement(
				sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilterOperatore(pst);



		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}


		rs = pst.executeQuery();
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}

	public ResultSet queryListSediStabilimento(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for counting records

		sqlCount.append(
				"SELECT  COUNT(distinct i.*) AS recordcount " +
				"FROM opu_indirizzo i  " +
				"left join opu_stabilimento o on (i.id = o.id_indirizzo) " +
		"left join comuni1 on (cast(comuni1.id as char(1000) )= i.comune) ");


		if (idOperatore > -1){

			sqlCount.append("");
		}



		sqlCount.append(" WHERE i.id >= 0 ");

		createFilterStabilimento(db, sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString());
			// UnionAudit(sqlFilter,db);

			items = prepareFilterStabilimento(pst);


			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(
						sqlCount.toString() +
						sqlFilter.toString() +
						"AND " + DatabaseUtils.toLowerCase(db) + "(i.comune) < ? ");
				items = prepareFilterStabilimento(pst);
				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
				rs = pst.executeQuery();
				if (rs.next()) {
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("i.comune");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY i.comune ");
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}
		//UTILIZZO CAST PERCHe' PER RETROCOMPATIBILITa' IL NOME COMUNE IN INDIRIZZO E' ANCORA STRINGA
		sqlSelect.append(
				"distinct i.*, r.tipologia_sede,comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia " +
				"FROM opu_indirizzo i " +
				"left join opu_stabilimento o on o.id_indirizzo = i.id " +
				"left join comuni1 on (cast(comuni1.id as char(1000) )= i.comune)" +
		"left join lookup_province lp on lp.code = comuni1.cod_provincia::int ");


		if (idOperatore > -1){

			sqlCount.append("");
		}


		sqlSelect.append("WHERE i.id >= 0 ");




		pst = db.prepareStatement(
				sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilterStabilimento(pst);



		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}


		rs = pst.executeQuery();
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}





	/**
	 *  Builds a base SQL where statement for filtering records to be used by
	 *  sqlSelect and sqlCount
	 *
	 * @param  sqlFilter  Description of Parameter
	 * @param  db         Description of the Parameter
	 * @since             1.2
	 */
	protected void createFilterOperatore(Connection db, StringBuffer sqlFilter) {
		//andAudit( sqlFilter );
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}


		if (idOperatore > -1){
			sqlFilter.append(" and o.id = ? ");
		}



		if (idComune >0 )
		{
			sqlFilter.append("and comuni1.id =? ");  		

		}



		if (provincia != null && ! "".equals(provincia))
		{
			sqlFilter.append("and i.provincia ilike ? ");  		

		}


		if (cap != null && !"".equals(cap)){

			sqlFilter.append("and i.cap ilike ? ");  
		}

		if (onlyActive > -1){

			sqlFilter.append("and stato_sede = ?");


		}
	}

	protected void createFilterStabilimento(Connection db, StringBuffer sqlFilter) {
		//andAudit( sqlFilter );
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}


		if (idStabilimento > -1){
			sqlFilter.append(" and o.id = ? ");
		}



		if (comune != null && ! "".equals(comune))
		{
			sqlFilter.append("and comuni1.nome ilike ? ");  		

		}



		if (provincia != null && ! "".equals(provincia))
		{
			sqlFilter.append("and i.provincia ilike ? ");  		

		}


		if (cap != null && !"".equals(cap)){

			sqlFilter.append("and i.cap ilike ? ");  
		}

		if (onlyActive > -1){

			sqlFilter.append("and stato_sede = ?");


		}

	}

	/**
	 *  Convenience method to get a list of phone numbers for each contact
	 *
	 * @param  db             Description of Parameter
	 * @throws  SQLException  Description of Exception
	 * @since                 1.5
	 */
	protected void buildResources(Connection db) throws SQLException {
		Iterator i = this.iterator();
		while (i.hasNext()) {
			Indirizzo thisInd = (Indirizzo) i.next();
			//   thisSoggetto.getPhoneNumberList().buildList(db);
			//thisOrganization.getAddressList().buildList(db); AGGIUNGERE BUILD LIST A OPERATOREADDRESSLIST
			//  thisOrganization.getEmailAddressList().buildList(db);
			//if this is an individual account, panagraficalate the primary contact record
			/*      if (thisOrganization.getNameLast() != null) {
        thisOrganization.panagraficalatePrimaryContact(db);
      }*/
			//  thisOrganization.buildTypes(db);
		}
	}




	/**
	 *  Sets the parameters for the preparedStatement - these items must
	 *  correspond with the createFilter statement
	 *
	 * @param  pst            Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 * @since                 1.2
	 */
	protected int prepareFilterOperatore(PreparedStatement pst) throws SQLException {
		int i = 0;


		if (idOperatore > -1){
			pst.setInt(++i, idOperatore);
		}


		if (idComune>0)
		{
			pst.setInt(++i, idComune);	
		}



		if (provincia != null && ! "".equals(provincia))
		{
			pst.setString(++i, provincia);

		}


		if (cap != null && !"".equals(cap)){

			pst.setString(++i, cap); 
		}


		if (onlyActive > -1){
			pst.setInt(++i, onlyActive); //Solo sedi attive
		}

		return i;
	}

	protected int prepareFilterStabilimento(PreparedStatement pst) throws SQLException {
		int i = 0;


		if (idStabilimento > -1){
			pst.setInt(++i, idStabilimento);
		}


		if (comune != null && ! "".equals(comune))
		{
			pst.setString(++i, comune);	
		}



		if (provincia != null && ! "".equals(provincia))
		{
			pst.setString(++i, provincia);

		}


		if (cap != null && !"".equals(cap)){

			pst.setString(++i, cap); 
		}


		if (onlyActive > -1){
			pst.setInt(++i, onlyActive); //Solo sedi attive
		}

		return i;
	}




	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  baseFilePath   Description of the Parameter
	 * @param  forceDelete    Description of the Parameter
	 * @param  context        Description of the Parameter
	 * @throws  SQLException  Description of the Exception
	 */
	/*  public void delete(Connection db, ActionContext context, String baseFilePath, boolean forceDelete) throws SQLException {
    Iterator organizationIterator = this.iterator();
    while (organizationIterator.hasNext()) {
      Organization thisOrganization = (Organization) organizationIterator.next();
      thisOrganization.setContactDelete(true);
      thisOrganization.setRevenueDelete(true);
      thisOrganization.setDocumentDelete(true);
      thisOrganization.setForceDelete(forceDelete);
      thisOrganization.delete(db, context, baseFilePath);
    }
  }
	 */






	/**
	 *  Gets the orgById attribute of the OrganizationList object This method
	 *  assumes that the value of id is > 0
	 *
	 * @param  id  The value of id is always greater than 0.
	 * @return     returns the matched organization or returns null
	 */
	public Indirizzo getIndById(int id) {
		Indirizzo result = null;
		Iterator iter = (Iterator) this.iterator();
		while (iter.hasNext()) {
			Indirizzo org = (Indirizzo) iter.next();
			if (org.getIdIndirizzo() == id) {
				result = org;
				break;
			}
		}
		return result;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub

	}





}

