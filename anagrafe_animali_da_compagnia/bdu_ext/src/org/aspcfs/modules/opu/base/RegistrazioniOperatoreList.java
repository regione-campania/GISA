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


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;


public class RegistrazioniOperatoreList extends Vector implements SyncableList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5843191141755214449L;
	private static Logger log = Logger
			.getLogger(org.aspcfs.modules.anagrafe_animali.base.AnimaleList.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}

	public final static String tableName = "animale";
	public final static String uniqueField = "id";
	protected Boolean minerOnly = null;
	protected int typeId = 0;
	protected int stageId = -1;
	private int idAslOrigine = -1;
	private int idAslDestinatariaModificaFuoriResidenza = -1;
	private int idTipologiaRegistrazioneOperatore = -1;
	private boolean flagCercaSoloSospese = false;

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
	
	

	public int getIdAslOrigine() {
		return idAslOrigine;
	}

	public void setIdAslOrigine(int idAslOrigine) {
		this.idAslOrigine = idAslOrigine;
	}
	
	public void setIdAslOrigine(String idAslOrigine) {
		this.idAslOrigine = new Integer (idAslOrigine).intValue();
	}

	public int getIdAslDestinatariaModificaFuoriResidenza() {
		return idAslDestinatariaModificaFuoriResidenza;
	}

	public void setIdAslDestinatariaModificaFuoriResidenza(
			int idAslDestinatariaModificaFuoriResidenza) {
		this.idAslDestinatariaModificaFuoriResidenza = idAslDestinatariaModificaFuoriResidenza;
	}

	public int getIdTipologiaRegistrazioneOperatore() {
		return idTipologiaRegistrazioneOperatore;
	}

	public void setIdTipologiaRegistrazioneOperatore(
			int idTipologiaRegistrazioneOperatore) {
		this.idTipologiaRegistrazioneOperatore = idTipologiaRegistrazioneOperatore;
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
		RegistrazioniOperatoreList.log = log;
	}


	public boolean isFlagCercaSoloSospese() {
		return flagCercaSoloSospese;
	}

	public void setFlagCercaSoloSospese(boolean flagCercaSoloSospese) {
		this.flagCercaSoloSospese = flagCercaSoloSospese;
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

	private int idRegistrazioneOperatore = -1;
	private int idRelazioneStabilimentoLp = -1;

	public int getIdRegistrazioneOperatore() {
		return idRegistrazioneOperatore;
	}

	public void setIdRegistrazioneOperatore(int idRegistrazioneOperatore) {
		this.idRegistrazioneOperatore = idRegistrazioneOperatore;
	}

	public int getIdRelazioneStabilimentoLp() {
		return idRelazioneStabilimentoLp;
	}

	public void setIdRelazioneStabilimentoLp(int idRelazioneStabilimentoLp) {
		this.idRelazioneStabilimentoLp = idRelazioneStabilimentoLp;
	}

	/**
	 * Constructor for the AnimaliList object, creates an empty list. After
	 * setting parameters, call the build method.
	 * 
	 * @since 1.1
	 */
	public  RegistrazioniOperatoreList()
	// TODO Auto-generated constructor stub
	{
	}

	/**
	 * Sets the lastAnchor attribute of the OrganizationList object
	 * 
	 * @param tmp
	 *            The new lastAnchor value
	 */
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

	/**
	 * Sets the PagedListInfo attribute of the OrganizationList object.
	 * <p>
	 * 
	 * <p/>
	 * 
	 * The query results will be constrained to the PagedListInfo parameters.
	 * 
	 * @param tmp
	 *            The new PagedListInfo value
	 * @since 1.1
	 */
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}

	/**
	 * Gets the HtmlSelect attribute of the ContactList object
	 * 
	 * @param selectName
	 *            Description of Parameter
	 * @return The HtmlSelect value
	 * @since 1.8
	 */
	public String getHtmlSelect(String selectName) {
		return getHtmlSelect(selectName, -1);
	}

	/**
	 * Gets the HtmlSelect attribute of the ContactList object
	 * 
	 * @param selectName
	 *            Description of Parameter
	 * @param defaultKey
	 *            Description of Parameter
	 * @return The HtmlSelect value
	 * @since 1.8
	 */
	public String getHtmlSelect(String selectName, int defaultKey) {
		HtmlSelect registrazioniListSelect = new HtmlSelect();

		Iterator i = this.iterator();
		while (i.hasNext()) {
			// RegistrazioneOperatore thisAn = (RegistrazioneOperatore)
			// i.next();
			// animaleListSelect.addItem(thisAn.getIdAnimale(),
			// thisAn.getNome());
		}

		return registrazioniListSelect.getHtml(selectName, defaultKey);
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
		HtmlSelect regListSelect = new HtmlSelect();
		regListSelect.addItem(-1, thisSystem
				.getLabel("calendar.none.4dashes"));

		Iterator i = this.iterator();
		while (i.hasNext()) {
			RegistrazioneOperatore reg = (RegistrazioneOperatore) i.next();
			regListSelect.addItem(reg.getIdRegistrazione(), reg
					.getNote());
		}


		return regListSelect.getHtml(selectName, defaultKey);
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

	public void buildList(Connection db) throws SQLException,
			IndirizzoNotFoundException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			switch (rs.getInt("id_tipologia_registrazione")) {

			case RegistrazioneModificaIndirizzoOperatore.idTipologia:

			{
				RegistrazioneModificaIndirizzoOperatore evento = new RegistrazioneModificaIndirizzoOperatore(rs, db);
				this.add(evento);
				break;
			}

			case RegistrazioneAccettazioneModificaResidenzaOperatore.idTipologia:

			{
				RegistrazioneAccettazioneModificaResidenzaOperatore evento = new RegistrazioneAccettazioneModificaResidenzaOperatore(
						rs);
				this.add(evento);
				break;
			}
			
			case RegistrazioneModificaDimensioneColonia.idTipologia:

			{
				RegistrazioneModificaDimensioneColonia evento = new RegistrazioneModificaDimensioneColonia(
						rs);
				this.add(evento);
				break;
			}
			
			
			case RegistrazioneChiusuraLineaProduttiva.idTipologia:

			{
				RegistrazioneChiusuraLineaProduttiva evento = new RegistrazioneChiusuraLineaProduttiva(
						rs);
				this.add(evento);
				break;
			}
			
			
			case RegistrazioneRiaperturaLineaProduttiva.idTipologia:

			{
				RegistrazioneRiaperturaLineaProduttiva evento = new RegistrazioneRiaperturaLineaProduttiva(
						rs);
				this.add(evento);
				break;
			}
			}
		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		
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
	public RegistrazioneOperatore getObject(ResultSet rs, Connection con) throws SQLException {
		RegistrazioneOperatore reg = new RegistrazioneOperatore(rs, con);
		return reg;
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

		sqlCount.append("Select ");

if (idTipologiaRegistrazioneOperatore <= 0){
		sqlCount
				.append(" COUNT(distinct r.* ) AS recordcount "
						+ " from registrazione_operatore r " +
								"left join evento_modifica_residenza_operatore mr on (r.id = mr.id_registrazione)" +
								"left join evento_presa_modifica_residenza_operatore pr on (r.id = pr.id_registrazione)" +
								"left join evento_modifica_dimensione_colonia mc on (r.id = mc.id_registrazione) " +
								"left join evento_chiusura_linea_produttiva cl on (r.id = cl.id_registrazione)" +
								"left join evento_apertura_linea_produttiva al on (r.id = al.id_registrazione)" +
								"left join opu_operatori_denormalizzati op on (r.id_rel_stab_lp = op.id_rel_stab_lp)");
}
else if (idTipologiaRegistrazioneOperatore == RegistrazioneModificaIndirizzoOperatore.idTipologia){
	sqlCount
	.append(" COUNT(distinct r.* ) AS recordcount "
			+ " from registrazione_operatore r " +
					"left join evento_modifica_residenza_operatore mr on (r.id = mr.id_registrazione)" +
					"left join opu_operatori_denormalizzati op on (r.id_rel_stab_lp = op.id_rel_stab_lp)"
			);
	
}
						


		sqlCount.append(" WHERE r.id >= 0 ");

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
						+ DatabaseUtils.toLowerCase(db) + "(r.note) < ? ");
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
			pagedListInfo.setColumnToSortBy("r.id");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			// Optimize SQL Server Paging
			// sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization "
			// + sqlOrder.toString());
		} else {
			sqlOrder.append(" ORDER BY r.id ");
		}

		// Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");

		}
		if (idTipologiaRegistrazioneOperatore <= 0){
			sqlSelect
				.append(" r.id as id_registrazione_operatore, op.ragione_sociale, r.entered as data_inserimento_registrazione_reg, r.modified as data_modifica_registrazione_reg,    * from registrazione_operatore r " +
						"left join evento_modifica_residenza_operatore mr on (r.id = mr.id_registrazione)" +
						"left join evento_presa_modifica_residenza_operatore pr on (r.id = pr.id_registrazione)" +
						"left join evento_modifica_dimensione_colonia mc on (r.id = mc.id_registrazione)" +
						"left join evento_chiusura_linea_produttiva cl on (r.id = cl.id_registrazione)" +
						"left join evento_apertura_linea_produttiva al on (r.id = al.id_registrazione)" +
						"left join opu_operatori_denormalizzati op on (r.id_rel_stab_lp = op.id_rel_stab_lp)");
			
		}
		
		else if (idTipologiaRegistrazioneOperatore == RegistrazioneModificaIndirizzoOperatore.idTipologia){
			sqlSelect
			.append("r.id as id_registrazione_operatore, op.ragione_sociale, r.entered as data_inserimento_registrazione_reg, r.modified as data_modifica_registrazione_reg, * from registrazione_operatore r " +
			"left join evento_modifica_residenza_operatore mr on (r.id = mr.id_registrazione) " +
			"left join opu_operatori_denormalizzati op on (r.id_rel_stab_lp = op.id_rel_stab_lp)");
		}

		



		sqlSelect.append("WHERE r.id >= 0 ");

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
				+ sqlOrder.toString());
		items = prepareFilter(pst);

		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}

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
		
		if (idRelazioneStabilimentoLp > 0){
			sqlFilter.append(" and r.id_rel_stab_lp = ?");
		}
		
		if (idTipologiaRegistrazioneOperatore > 0){
			sqlFilter.append(" and r.id_tipologia_registrazione = ?");
		}
		
		if (idAslOrigine > 0){
			sqlFilter.append(" and r.id_asl_inserimento_registrazione = ?");
		}
		
		if (idAslDestinatariaModificaFuoriResidenza > 0){
			sqlFilter.append(" and mr.id_asl_destinataria = ?");
		}
		
		if (flagCercaSoloSospese){
			sqlFilter.append(" and mr.modifica_completata = false ");
		}
		
		
		sqlFilter.append(" and r.data_cancellazione is null ");

			

	}

	

	/**
	 * Sets the parameters for the preparedStatement - these items must
	 * correspond with the createFilter statement
	 * 
	 * @param pst
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException
	 *             Description of Exception
	 * @since 1.2
	 */
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		
		if (idRelazioneStabilimentoLp > 0){
			pst.setInt(++i, idRelazioneStabilimentoLp);
		}
		
		
		if (idTipologiaRegistrazioneOperatore > 0){
			pst.setInt(++i, idTipologiaRegistrazioneOperatore);
		}
		
		if (idAslOrigine > 0){
			pst.setInt(++i, idAslOrigine);
		}
		
		if (idAslDestinatariaModificaFuoriResidenza > 0){
			pst.setInt(++i, idAslDestinatariaModificaFuoriResidenza);
		}
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
