/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

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
public class SoggettoFisicoList extends Vector implements SyncableList {

	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.SoggettoFisicoList.class);
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
	
	private int idComune ;
	private int idOperatore = -1;
	private int idStabilimento = -1;





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

	

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		SoggettoFisicoList.log = log;
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


	public ResultSet queryList(String cf,Connection db,PreparedStatement pst) throws SQLException {


		StringBuffer sqlSelect = new StringBuffer("");
		sqlSelect.append(
				"SELECT distinct max(storico.id) as id_soggetto_storico ,i.toponimo,i.civico,comnasc.id as id_comune_nascita," +
						"o.nome,o.cognome," +
						"o.codice_fiscale,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono,'R'||comuni1.codiceasl_bdn as codice_asl," +
						"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
						" o.id as id_soggetto ," +
						"i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,coalesce(comuni1.nome,i.comune_testo) as descrizione_comune," +
						"lp.description as descrizione_provincia   " +
						"FROM opu_soggetto_fisico o " +
						"left join opu_soggetto_fisico_storico storico on o.id = storico.id_opu_soggetto_fisico " +
						"left join opu_indirizzo i on o.indirizzo_id=i.id " +
						"left join comuni1 on (comuni1.id = i.comune) " +
						"left join comuni1 comnasc on comnasc.nome ilike o.comune_nascita "+
						"left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true " +
						"left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
						"left join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) " +
						"left join opu_operatore op on (os.id_operatore = op.id)  "
						+ "where trim(o.codice_fiscale) ilike ? and o.trashed_date is null " +
						"group by o.nome,o.cognome,comnasc.id," +
						"o.codice_fiscale,o.comune_nascita,i.toponimo,i.civico,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
						"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
						" o.id  ," +
						"i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome ,'R'||comuni1.codiceasl_bdn,coalesce(comuni1.nome,i.comune_testo)," +
				"lp.description " );

		 pst = db.prepareStatement(sqlSelect.toString());
		pst.setString(1, cf);

		ResultSet rs = pst.executeQuery();
		
		return rs ; 


		

	}

	public void buildList(Connection db,String cf) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(cf,db, pst);
		while (rs.next()) {
			SoggettoFisico thissogg = new SoggettoFisico();
			thissogg.buildRecord(rs);
			this.add(thissogg);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		
	}



	
	public void buildListStorico(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryListStorico(db, pst);
		while (rs.next()) {
			SoggettoFisico thissogg = new SoggettoFisico();
			thissogg.buildRecordStorico(rs);
			this.add(thissogg);

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
	
	
	
	

	public ResultSet queryListStorico(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for counting records

		sqlCount.append(
				"SELECT  COUNT(sfi.*) AS recordcount " +
						 "from "+ 
						
						 "opu_rel_operatore_soggetto_fisico sfi "+
						 "join opu_soggetto_fisico_storico  sfs on sfi.id_soggetto_fisico = sfs.id_opu_soggetto_fisico "+  
						 " order by sfs.modified ");
				

		if (idOperatore > -1){

			sqlCount.append("");
		}



		sqlCount.append(" WHERE 1=1 ");

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

			

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("sfs.modified");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY  sfs.modified ");
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}
		
		sqlSelect.append(" distinct sfi.id_operatore,cognome,sfs.nome,comune_nascita,provincia_nascita,codice_fiscale,sesso,data_nascita,indirizzo_id,id_opu_soggetto_fisico,sfs.modified,sfs.modifiedby," +
				"i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune," +
					"lp.description as descrizione_provincia ,sfs.modified   "+
		 "from " + 
		
		 "opu_rel_operatore_soggetto_fisico sfi "+
		 "join opu_soggetto_fisico_storico  sfs on sfi.id_soggetto_fisico = sfs.id_opu_soggetto_fisico " +
		  "left join opu_indirizzo i on sfs.indirizzo_id=i.id " +
			 "left join comuni1 on (comuni1.id = i.comune) " +
			 "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true " +
			  "left join lookup_province lp on lp.code = comuni1.cod_provincia::int "+
			 
		 "");

		if (idOperatore > -1){

			sqlCount.append("");
		}

		sqlSelect.append("WHERE 1=1 ");

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

		if (idOperatore>0)
			sqlFilter.append(" and id_operatore = ? ");
	}

	protected void createFilterStabilimento(Connection db, StringBuffer sqlFilter) {
		//andAudit( sqlFilter );
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
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

		if (idOperatore>0)
			pst.setInt(++i, idOperatore);
		
		return i;
	}

	protected int prepareFilterStabilimento(PreparedStatement pst) throws SQLException {
		int i = 0;


	
		return i;
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

