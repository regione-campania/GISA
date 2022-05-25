/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali_ext.base;

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
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.opu_ext.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu_ext.base.Operatore;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
public class AnimaleList extends Vector implements SyncableList {

	private static Logger log = Logger
			.getLogger(org.aspcfs.modules.anagrafe_animali_ext.base.AnimaleList.class);
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
		AnimaleList.log = log;
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

	
	private int idAnimale = -1;
	private String microchip;
	private String numeroPassaporto;
	private String tatuaggio = "";
	private int idRazza = -1;
	private String sesso;
	private int idMantello = -1;
	private int idAsl = -1;
	private boolean flagPensione = false;
	private int idTaglia = -1;
	private int idSpecie = -1;
	private int anno = -1;
	private int idStato = -1;

	private int id_proprietario = -1;
	private int id_detentore = -1;

	private int id_proprietario_o_detentore = -1;
	
	private int idUtenteInserimento = -1;

	// Dati proprietario

	private String denominazioneProprietario = "";
	private String codiceFiscaleProprietario = "";
	private String partitaIvaProprietario = "";
	private String nomeComuneProprietario = "";

	// Dati detentore

	private String denominazioneDetentore = "";
	private String codiceFiscaleDetentore = "";
	private String partitaIvaDetentore = "";
	private String nomeComuneDetentore = "";

	// dati partita di appartenenza
	private int idPartita = -1;
	private boolean checkFlagSulVincolo = false;
	private boolean flagVincolato;

	// Array degli stati
	private ArrayList<Integer> statiDaIncludere = new ArrayList<Integer>();

	/**
	 * Constructor for the AnimaliList object, creates an empty list. After
	 * setting parameters, call the build method.
	 * 
	 * @since 1.1
	 */
	public AnimaleList() {
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

	public int getId_proprietario() {
		return id_proprietario;
	}

	public void setId_proprietario(int id_proprietario) {
		this.id_proprietario = id_proprietario;
	}

	public int getId_detentore() {
		return id_detentore;
	}

	public void setId_detentore(int id_detentore) {
		this.id_detentore = id_detentore;
	}

	public int getId_proprietario_o_detentore() {
		return id_proprietario_o_detentore;
	}

	public void setId_proprietario_o_detentore(int id_proprietario_o_detentore) {
		this.id_proprietario_o_detentore = id_proprietario_o_detentore;
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

	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public void setIdStato(String idStato) {
		this.idStato = new Integer(idStato).intValue();
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

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public void setAnno(String anno) {
		this.anno = new Integer(anno).intValue();
	}

	public String getDenominazioneProprietario() {
		return denominazioneProprietario;
	}

	public void setDenominazioneProprietario(String denominazioneProprietario) {
		this.denominazioneProprietario = denominazioneProprietario;
	}

	public String getCodiceFiscaleProprietario() {
		return codiceFiscaleProprietario;
	}

	public void setCodiceFiscaleProprietario(String codiceFiscaleProprietario) {
		this.codiceFiscaleProprietario = codiceFiscaleProprietario;
	}
	

	public int getIdAnimale() {
		return idAnimale;
	}

	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}
	
	public void setIdAnimale(String idAnimale) {
		this.idAnimale = new Integer (idAnimale).intValue();
	}

	public String getPartitaIvaProprietario() {
		return partitaIvaProprietario;
	}

	public void setPartitaIvaProprietario(String partitaIvaProprietario) {
		this.partitaIvaProprietario = partitaIvaProprietario;
	}

	public String getNomeComuneProprietario() {
		return nomeComuneProprietario;
	}

	public void setNomeComuneProprietario(String nomeComuneProprietario) {
		this.nomeComuneProprietario = nomeComuneProprietario;
	}

	public String getDenominazioneDetentore() {
		return denominazioneDetentore;
	}

	public void setDenominazioneDetentore(String denominazioneDetentore) {
		this.denominazioneDetentore = denominazioneDetentore;
	}

	public String getCodiceFiscaleDetentore() {
		return codiceFiscaleDetentore;
	}

	
	
	public void setCodiceFiscaleDetentore(String codiceFiscaleDetentore) {
		this.codiceFiscaleDetentore = codiceFiscaleDetentore;
	}

	public String getPartitaIvaDetentore() {
		return partitaIvaDetentore;
	}

	public void setPartitaIvaDetentore(String partitaIvaDetentore) {
		this.partitaIvaDetentore = partitaIvaDetentore;
	}

	public String getNomeComuneDetentore() {
		return nomeComuneDetentore;
	}

	public void setNomeComuneDetentore(String nomeComuneDetentore) {
		this.nomeComuneDetentore = nomeComuneDetentore;
	}

	
	
	public int getIdUtenteInserimento() {
		return idUtenteInserimento;
	}

	public void setIdUtenteInserimento(int idUtenteInserimento) {
		this.idUtenteInserimento = idUtenteInserimento;
	}
	
	
	public void setIdUtenteInserimento(String idUtenteInserimento) {
		this.idUtenteInserimento = new Integer (idUtenteInserimento).intValue();
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
		HtmlSelect animaleListSelect = new HtmlSelect();

		Iterator i = this.iterator();
		while (i.hasNext()) {
			Animale thisAn = (Animale) i.next();
			animaleListSelect.addItem(thisAn.getIdAnimale(), thisAn.getNome());
		}

		return animaleListSelect.getHtml(selectName, defaultKey);
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
			Animale thisAn = (Animale) i.next();
			animaleListSelect.addItem(thisAn.getIdAnimale(), thisAn.getNome());
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

	
	
	
	public ArrayList<Animale> buildList(Connection db,LookupList mantello , LookupList taglie , LookupList razze, LookupList specie) throws SQLException,
	IndirizzoNotFoundException {
PreparedStatement pst = null;
ArrayList<Animale> lista = new ArrayList<Animale>();
ResultSet rs = queryList(db, pst);
while (rs.next()) {
	Animale thisAnimale = this.getObject(rs);
	thisAnimale.setDescrSpecie(specie.getSelectedValue(rs.getInt("id_specie")));
	thisAnimale.setDescrRazza(razze.getSelectedValue(rs.getInt("id_razza")));
	thisAnimale.setDescrMantello(mantello.getSelectedValue(rs.getInt("id_tipo_mantello")));
	thisAnimale.setDescrTaglia(taglie.getSelectedValue(rs.getInt("id_taglia")));

	// thisAnimale.setDetentore(detentore);
	lista.add(thisAnimale);

}

rs.close();
if (pst != null) {
	pst.close();
}
return lista ;
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
	public Animale getObject(ResultSet rs) throws SQLException {
		Animale thisAnimale = new Animale(rs);
		return thisAnimale;
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
	public ResultSet queryList(Connection db, PreparedStatement pst)
			throws SQLException {
		ResultSet rs = null;
		int items = -1;

		

		StringBuffer sqlSelect = new StringBuffer("");
		StringBuffer sqlFilter = new StringBuffer("");
		
		createFilter(db, sqlFilter);
		
		sqlSelect.append("Select distinct a.*,c.id_taglia,lsa.description as stato_import "
						+ "from animale a left join cane c on a.id = c.id_animale left join lookup_stati_import_animale lsa on lsa.code = a.id_stato " );
						

		sqlSelect.append("WHERE a.id >= 0 ");

		pst = db.prepareStatement(sqlSelect.toString()+ sqlFilter.toString() );
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

	/**
	 * Builds a base SQL where statement for filtering records to be used by
	 * sqlSelect and sqlCount
	 * 
	 * @param sqlFilter
	 *            Description of Parameter
	 * @param db
	 *            Description of the Parameter
	 * @since 1.2
	 */
	protected void createFilter(Connection db, StringBuffer sqlFilter) {

		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (idAnimale > -1){
			sqlFilter.append("and a.id = ?");
		}
		if (idSpecie > -1) {
			sqlFilter.append("and a.id_specie = ?");
		}

		if (microchip != null && !"".equals(microchip)) {
			sqlFilter.append("and a.microchip = ? ");

		}

		if (numeroPassaporto != null && !"".equals(numeroPassaporto)) {
			sqlFilter.append("and passaporto_numero = ? ");
		}

		if (tatuaggio != null && !"".equals(tatuaggio)) {
			sqlFilter.append("and tatuaggio = ? ");
		}

		if (idRazza > -1) {
			sqlFilter.append("and id_razza = ? ");
		}

		if (sesso != null && !"".equals(sesso)) {
			sqlFilter.append("and a.sesso = ? ");
		}

		if (idMantello > -1) {
			sqlFilter.append("and id_tipo_mantello = ? ");
		}

		if (idAsl > -1) {
			sqlFilter.append("and id_asl_riferimento = ? ");
		} else if (idAsl == -1) {
			// tutte le asl
		}

		if (flagPensione) {
			sqlFilter.append("and flag_pensione = true ");
		}

		if (idTaglia > -1) {
			sqlFilter.append("and id_taglia = ? ");
		}

		if (codiceFiscaleProprietario != null
				&& !"".equals(codiceFiscaleProprietario)) {
			sqlFilter.append("and soggp.codice_fiscale = ? ");
		}

		if (partitaIvaProprietario != null
				&& !"".equals(partitaIvaProprietario)) {
			sqlFilter.append("and soggp.partita_iva = ? ");
		}

		if (denominazioneProprietario != null
				&& !"".equals(denominazioneProprietario)) {
			sqlFilter
					.append("and (soggp.nome ILIKE ? or soggp.cognome ILIKE ?)");
		}

		if (codiceFiscaleDetentore != null
				&& !"".equals(codiceFiscaleDetentore)) {
			sqlFilter.append("and soggd.codice_fiscale = ? ");
		}

		if (partitaIvaDetentore != null && !"".equals(partitaIvaDetentore)) {
			sqlFilter.append("and soggd.partita_iva = ? ");
		}

		if (denominazioneDetentore != null
				&& !"".equals(denominazioneDetentore)) {
			sqlFilter
					.append("and (soggd.nome ILIKE ? or soggd.cognome ILIKE ?)");
		}

		if (anno > -1) {
			sqlFilter
					.append(" AND a.data_inserimento > ? AND a.data_inserimento <  ? ");
		}

		if (id_proprietario_o_detentore > -1) {

			sqlFilter
					.append(" AND ( a.id_proprietario =  ? OR c.id_detentore =  ? )");
		}

		if (idStato > -1) {
			sqlFilter.append(" AND a.stato = ? ");
		}

		if (idPartita > -1) {
			sqlFilter.append(" AND a.id_partita_circuito_commerciale = ? ");
		}

		if (checkFlagSulVincolo) {
			sqlFilter.append(" AND a.flag_vincolato = ?");
		}
		
		if (idUtenteInserimento > -1){
			sqlFilter.append(" AND a.utente_inserimento = ?");
		}

		if (statiDaIncludere.size() > 0) {
			sqlFilter.append(" AND a.stato IN (");

			for (int k = 0; k < statiDaIncludere.size(); k++) {
				if (k == 0)
					sqlFilter.append(statiDaIncludere.get(k));
				else
					sqlFilter.append("," + statiDaIncludere.get(k));
			}
			sqlFilter.append(")");
		}

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

		if (idAnimale > -1){
			pst.setInt(++i, idAnimale);
		}
		
		if (idSpecie > -1) {
			pst.setInt(++i, idSpecie);
		}

		if (microchip != null && !"".equals(microchip)) {
			pst.setString(++i, microchip);

		}

		if (numeroPassaporto != null && !"".equals(numeroPassaporto)) {

			pst.setString(++i, numeroPassaporto);
		}

		if (tatuaggio != null && !"".equals(tatuaggio)) {
			pst.setString(++i, tatuaggio);
		}

		if (idRazza > -1) {
			pst.setInt(++i, idRazza);
		}

		if (sesso != null && !"".equals(sesso)) {
			pst.setString(++i, sesso);
		}

		if (idMantello > -1) {
			pst.setInt(++i, idMantello);
		}

		if (idAsl > -1) {
			pst.setInt(++i, idAsl);
		}

		if (flagPensione) {
			pst.setBoolean(++i, flagPensione);
		}

		if (idTaglia > -1) {
			pst.setInt(++i, idTaglia);
		}

		if (codiceFiscaleProprietario != null
				&& !"".equals(codiceFiscaleProprietario)) {
			pst.setString(++i, codiceFiscaleProprietario);
		}

		if (partitaIvaProprietario != null
				&& !"".equals(partitaIvaProprietario)) {
			pst.setString(++i, partitaIvaProprietario);
		}

		if (denominazioneProprietario != null
				&& !"".equals(denominazioneProprietario)) {
			pst.setString(++i, denominazioneProprietario);
			pst.setString(++i, denominazioneProprietario);
		}

		if (codiceFiscaleDetentore != null
				&& !"".equals(codiceFiscaleDetentore)) {
			pst.setString(++i, codiceFiscaleDetentore);
		}

		if (partitaIvaDetentore != null && !"".equals(partitaIvaDetentore)) {
			pst.setString(++i, partitaIvaDetentore);
		}

		if (denominazioneDetentore != null
				&& !"".equals(denominazioneDetentore)) {
			pst.setString(++i, denominazioneDetentore);
			pst.setString(++i, denominazioneDetentore);
		}

		if (anno > -1) {
			pst.setTimestamp(++i, DatabaseUtils.parseDateToTimestamp("01/01/"
					+ anno));
			pst.setTimestamp(++i, DatabaseUtils.parseDateToTimestamp("31/12/"
					+ anno));
		}

		if (id_proprietario_o_detentore > -1) {

			pst.setInt(++i, id_proprietario_o_detentore);
			pst.setInt(++i, id_proprietario_o_detentore);
		}

		if (idStato > -1) {
			pst.setInt(++i, idStato);
		}

		if (idPartita > -1) {
			pst.setInt(++i, idPartita);
		}

		if (checkFlagSulVincolo) {
			pst.setBoolean(++i, flagVincolato);
		}
		
		if (idUtenteInserimento > -1){
			pst.setInt(++i, idUtenteInserimento);
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
			Animale thisAnimale = (Animale) i.next();

		}
	}

	public String getMicrochip() {
		return microchip;
	}

	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}

	public String getNumeroPassaporto() {
		return numeroPassaporto;
	}

	public void setNumeroPassaporto(String numeroPassaporto) {
		this.numeroPassaporto = numeroPassaporto;
	}

	public String getTatuaggio() {
		return tatuaggio;
	}

	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}

	public int getIdRazza() {
		return idRazza;
	}

	public void setIdRazza(int idRazza) {
		this.idRazza = idRazza;
	}

	public void setIdRazza(String id) {
		this.idRazza = new Integer(id).intValue();
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public int getIdMantello() {
		return idMantello;
	}

	public void setIdMantello(int idMantello) {
		this.idMantello = idMantello;
	}

	public void setIdMantello(String id) {
		this.idMantello = new Integer(id).intValue();
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public void setIdAsl(String id) {
		this.idAsl = new Integer(id).intValue();
	}

	public boolean isFlagPensione() {
		return flagPensione;
	}

	public void setFlagPensione(boolean flagPensione) {
		this.flagPensione = flagPensione;
	}

	public int getIdTaglia() {
		return idTaglia;
	}

	public void setIdTaglia(int idTaglia) {
		this.idTaglia = idTaglia;
	}

	public void setIdTaglia(String id) {
		this.idTaglia = new Integer(id).intValue();
	}

	public void setIdSpecie(String id) {
		this.idSpecie = new Integer(id).intValue();
	}

	public int getIdSpecie() {
		return idSpecie;
	}

	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
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

	public int getIdPartita() {
		return idPartita;
	}

	public void setIdPartita(int idPartita) {
		this.idPartita = idPartita;
	}

	public boolean isFlagVincolato() {
		return flagVincolato;
	}

	public void setFlagVincolato(boolean flagVincolato) {
		this.flagVincolato = flagVincolato;
	}

	public void setFlagVincolato(String flagVincolato) {
		this.flagVincolato = DatabaseUtils.parseBoolean(flagVincolato);
	}

	public boolean isCheckFlagSulVincolo() {
		return checkFlagSulVincolo;
	}

	public void setCheckFlagSulVincolo(boolean checkFlagSulVincolo) {
		this.checkFlagSulVincolo = checkFlagSulVincolo;
	}

	public void setCheckFlagSulVincolo(String flagVincolato) {
		this.checkFlagSulVincolo = DatabaseUtils.parseBoolean(flagVincolato);
	}

	public ArrayList<Integer> getStatiDaIncludere() {
		return statiDaIncludere;
	}

	public void setStatiDaIncludere(ArrayList<Integer> statiDaIncludere) {
		this.statiDaIncludere = statiDaIncludere;
	}

}
