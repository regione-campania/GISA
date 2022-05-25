/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.DNA.base;


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
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.opu_ext.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu_ext.base.Operatore;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;
import java.util.ArrayList;

/**
 * Contains a list of organizations... currently used to build the list from the
 * database with any of the parameters to limit the results.
 * 
 * @author mrajkowski
 * @created August 30, 2001
 * @version $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski Exp
 *          $
 */
public class ListaConvocazioneList extends Vector implements SyncableList {

	private static Logger log = Logger
			.getLogger(org.aspcfs.modules.anagrafe_animali_ext.base.AnimaleList.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}

	public final static String tableName = "dati_convocazione";
	public final static String uniqueField = "id";
	protected Boolean minerOnly = null;
	protected int typeId = 0;
	protected int stageId = -1;
	
	
	
	
	

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public void setStageId(String tmp) {
		if (tmp != null) {
			this.stageId = Integer.parseInt(tmp);
		} else {
			this.stageId = -1;
		}
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

	protected java.sql.Timestamp lastAnchor = null;
	protected java.sql.Timestamp nextAnchor = null;
	protected int syncType = Constants.NO_SYNC;
	protected PagedListInfo pagedListInfo = null;

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		ListaConvocazioneList.log = log;
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

	
	private int idConvocazione = -1;
	private String denominazione;
	private String nomeFile;
	private Timestamp dataInizio = null;
	private Timestamp dataFine = null;
	private int idComune = -1;
	private int idCircoscrizione = -1;
	private Timestamp dataInserimento = null;
	private Timestamp dataModifica = null;
	private int utenteInserimento = -1;
	private int utenteModifica = -1;
	private Timestamp dataCancellazione = null;
	private String note = "";
	private int stato = -1;
	private int idAsl = -1;
	
	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public void setIdAsl(String idAsl) {
		this.idAsl = Integer.parseInt(idAsl);
	}

	public ListaConvocazioneList() {
	}

	
	public void setLastAnchor(java.sql.Timestamp tmp) {
		this.lastAnchor = tmp;
	}

	/**
	 * Sets the lastAnchor attribute of the OrganizationList object
	 * 
	 * @param tmp
	 *            The new lastAnchor value
	 */
	public void setLastAnchor(String tmp) {
		this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
	}

	/**
	 * Sets the nextAnchor attribute of the OrganizationList object
	 * 
	 * @param tmp
	 *            The new nextAnchor value
	 */
	public void setNextAnchor(java.sql.Timestamp tmp) {
		this.nextAnchor = tmp;
	}

	/**
	 * Sets the nextAnchor attribute of the OrganizationList object
	 * 
	 * @param tmp
	 *            The new nextAnchor value
	 */
	public void setNextAnchor(String tmp) {
		this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
	}

	/**
	 * Sets the syncType attribute of the OrganizationList object
	 * 
	 * @param tmp
	 *            The new syncType value
	 */
	public void setSyncType(int tmp) {
		this.syncType = tmp;
	}

	
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}

	public String getHtmlSelect(String selectName) {
		return getHtmlSelect(selectName, -1);
	}

	
	
	public String getHtmlSelect(String selectName, int defaultKey) {
		HtmlSelect convocazioniListSelect = new HtmlSelect();

		Iterator i = this.iterator();
		while (i.hasNext()) {
			ListaConvocazione thisLista = (ListaConvocazione) i.next();
			convocazioniListSelect.addItem(thisLista.getIdListaConvocazione(), thisLista.getDenominazione());
		}

		return convocazioniListSelect.getHtml(selectName, defaultKey);
	}

	/**
	 * Gets the HtmlSelectDefaultNone attribute of the OrganizationList object
	 * 
	 * @param selectName
	 *            Description of Parameter
	 * @param thisSystem
	 *            Description of the Parameter
	 * @return The HtmlSelectDefaultNone value
	 */
	public String getHtmlSelectDefaultNone(SystemStatus thisSystem,
			String selectName) {
		return getHtmlSelectDefaultNone(thisSystem, selectName, -1);
	}

	/**
	 * Gets the htmlSelectDefaultNone attribute of the OrganizationList object
	 * 
	 * @param selectName
	 *            Description of the Parameter
	 * @param defaultKey
	 *            Description of the Parameter
	 * @param thisSystem
	 *            Description of the Parameter
	 * @return The htmlSelectDefaultNone value
	 */
	public String getHtmlSelectDefaultNone(SystemStatus thisSystem,
			String selectName, int defaultKey) {
		HtmlSelect animaleListSelect = new HtmlSelect();
		animaleListSelect.addItem(-1, thisSystem
				.getLabel("calendar.none.4dashes"));

		Iterator i = this.iterator();
		while (i.hasNext()) {
			ListaConvocazione thisList = (ListaConvocazione) i.next();
			animaleListSelect.addItem(thisList.getIdListaConvocazione(), thisList.getDenominazione());
		}

		/*
		 * if (!(this.getHtmlJsEvent().equals(""))) {
		 * orgListSelect.setJsEvent(this.getHtmlJsEvent()); }
		 */

		return animaleListSelect.getHtml(selectName, defaultKey);
	}

	/**
	 * Description of the Method
	 * 
	 * @param db
	 *            Description of the Parameter
	 * @throws SQLException
	 *             Description of the Exception
	 * @throws IndirizzoNotFoundException
	 */
	public void select(Connection db) throws SQLException,
			IndirizzoNotFoundException {
		buildList(db);
	}

	/**
	 * Queries the database, using any of the filters, to retrieve a list of
	 * organizations. The organizations are appended, so build can be run any
	 * number of times to generate a larger list for a report.
	 * 
	 * @param db
	 *            Description of Parameter
	 * @throws SQLException
	 *             Description of Exception
	 * @throws IndirizzoNotFoundException
	 * @since 1.1
	 */

	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			ListaConvocazione thisLista = this.getObject(rs);
			// Costruisco proprietario e detentore
			this.add(thisLista); //ALTRIMENTI AGGIUNGILO COMUNQUE
		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		// buildResources(db);
	}

	/**
	 * Gets the object attribute of the OrganizationList object
	 * 
	 * @param rs
	 *            Description of the Parameter
	 * @return The object value
	 * @throws SQLException
	 *             Description of the Exception
	 */
	public ListaConvocazione getObject(ResultSet rs) throws SQLException {
		ListaConvocazione thisLista = new ListaConvocazione(rs);
		return thisLista;
	}

	/**
	 * This method is required for synchronization, it allows for the resultset
	 * to be streamed with lower overhead
	 * 
	 * @param db
	 *            Description of the Parameter
	 * @param pst
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @throws SQLException
	 *             Description of the Exception
	 */
	//
	public ResultSet queryList(Connection db, PreparedStatement pst)
	throws SQLException {
ResultSet rs = null;
int items = -1;

StringBuffer sqlSelect = new StringBuffer();
StringBuffer sqlCount = new StringBuffer();
StringBuffer sqlFilter = new StringBuffer();
StringBuffer sqlOrder = new StringBuffer();

// Need to build a base SQL statement for counting records

sqlCount
		.append("Select " );

				
				sqlCount.append(" COUNT(distinct dc.* ) AS recordcount "+
				"from dati_convocazione dc " );
				sqlCount.append(" WHERE dc.id >= 0 ");

createFilter(db, sqlFilter);

if (pagedListInfo != null) {
	// Get the total number of records matching filter
	pst = db.prepareStatement(sqlCount.toString()
			+ sqlFilter.toString());
	// UnionAudit(sqlFilter,db);

	items = prepareFilter(pst);

	rs = pst.executeQuery();
	if (rs.next()) {
		int maxRecords = rs.getInt("recordcount");
		pagedListInfo.setMaxRecords(maxRecords);
	}
	rs.close();
	pst.close();

	// Determine the offset, based on the filter, for the first record
	// to show
	if (!pagedListInfo.getCurrentLetter().equals("")) {
		pst = db.prepareStatement(sqlCount.toString()
				+ sqlFilter.toString() + "AND "
				+ DatabaseUtils.toLowerCase(db) + "(dc.denominazione) < ? ");
		items = prepareFilter(pst);
		pst.setString(++items, pagedListInfo.getCurrentLetter()
				.toLowerCase());
		
		
		rs = pst.executeQuery();
		if (rs.next()) {
			int offsetCount = rs.getInt("recordcount");
			pagedListInfo.setCurrentOffset(offsetCount);
		}
		rs.close();
		pst.close();
	}

	// Determine column to sort by
	pagedListInfo.setColumnToSortBy("dc.denominazione");
	pagedListInfo.appendSqlTail(db, sqlOrder);

	// Optimize SQL Server Paging
	// sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization "
	// + sqlOrder.toString());
} else {
	sqlOrder.append("ORDER BY dc.denominazione ");
}

// Need to build a base SQL statement for returning records
if (pagedListInfo != null) {
	pagedListInfo.appendSqlSelectHead(db, sqlSelect);
} else {
	sqlSelect.append("SELECT ");
	
}



sqlSelect.
append("distinct dc.* ");


sqlSelect.append("from dati_convocazione dc " );
				
sqlSelect.append("WHERE dc.id >= 0 ");

pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
		+ sqlOrder.toString());
items = prepareFilter(pst);

if (pagedListInfo != null) {
	pagedListInfo.doManualOffset(db, pst);
}
if (System.getProperty("DEBUG") != null) 
	System.out.println("QUERY!!!:  " +pst.toString());
rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
if (pagedListInfo != null) {
	pagedListInfo.doManualOffset(db, rs);
}
return rs;
}
	
	
	protected void createFilter(Connection db, StringBuffer sqlFilter) {

		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}
		
		if (idComune > 0){
			sqlFilter.append("and dc.id_comune = ?");
		}
		
		if (idAsl > 0){
			sqlFilter.append("and dc.id_asl = ?");
		}
		
		
	}
	
	
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		GregorianCalendar gc = new GregorianCalendar();
		
		if (idComune > 0){
			pst.setInt(++i, idComune);
		}
		
		if (idAsl > 0){
			pst.setInt(++i, idAsl);
		}
		
		
		return i;
	}

	/**
	 * Convenience method to get a list of phone numbers for each contact
	 * 
	 * @param db
	 *            Description of Parameter
	 * @throws SQLException
	 *             Description of Exception
	 * @since 1.5
	 */
	protected void buildResources(Connection db) throws SQLException {
		Iterator i = this.iterator();
		while (i.hasNext()) {
			ListaConvocazione thisLista = (ListaConvocazione) i.next();

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

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub

	}

	public void setIdConvocazione(int idConvocazione) {
		this.idConvocazione = idConvocazione;
	}

	public int getIdConvocazione() {
		return idConvocazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}

	public Timestamp getDataFine() {
		return dataFine;
	}

	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}

	public int getIdComune() {
		return idComune;
	}

	public void setIdCircoscrizione(int idCircoscrizione) {
		this.idCircoscrizione = idCircoscrizione;
	}

	public int getIdCircoscrizione() {
		return idCircoscrizione;
	}

	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Timestamp getDataInserimento() {
		return dataInserimento;
	}

	public void setDataModifica(Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Timestamp getDataModifica() {
		return dataModifica;
	}

	public void setUtenteInserimento(int utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}

	public int getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteModifica(int utenteModifica) {
		this.utenteModifica = utenteModifica;
	}

	public int getUtenteModifica() {
		return utenteModifica;
	}

	public void setDataCancellazione(Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Timestamp getDataCancellazione() {
		return dataCancellazione;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public int getStato() {
		return stato;
	}


}
