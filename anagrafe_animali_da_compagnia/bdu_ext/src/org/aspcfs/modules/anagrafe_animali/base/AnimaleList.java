/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

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
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class AnimaleList extends Vector implements SyncableList {

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
	protected Boolean flagDecesso= null; //FALSE: AGGIUNGI ALLA LISTA SOLO ANIMALI VIVI -- TRUE SOLO ANIMALI MORTI --NULL IGNORA 
	protected Boolean flagSmarrimento = null; //FALSE: AGGIUNGI ALLA LISTA SOLO ANIMALI NON SMARRITI -- TRUE SOLO ANIMALI SMARRITI --NULL IGNORA
	protected Boolean flagFurto = null;  //FALSE: AGGIUNGI ALLA LISTA SOLO ANIMALI NON CON FURTO -- TRUE SOLO ANIMALI CON FURTO --NULL IGNORA
	private ArrayList<String> listaMicrochip = new ArrayList<String>();
	private boolean includeTrashed = false;
	
	private Boolean filtriPianoLeish = false;
	


	
	public Boolean getFlagDecesso() {
		return flagDecesso;
	}

	public void setFlagDecesso(Boolean flagDecesso) {
		this.flagDecesso = flagDecesso;
	}
	
	public void setFlagDecesso(String tipo){
		if (tipo != null && ("1").equals(tipo)){
			flagDecesso = false;
		}else if (tipo != null && ("2").equals(tipo) ){
			flagDecesso = true;
		}
	}
	
	
	

	public Boolean getFlagFurto() {
		return flagFurto;
	}

	public void setFlagFurto(Boolean flagFurto) {
		this.flagFurto = flagFurto;
	}

	public Boolean getFlagSmarrimento() {
		return flagSmarrimento;
	}

	public ArrayList<String> getListaMicrochip() {
		return listaMicrochip;
	}

	
	
	public boolean isIncludeTrashed() {
		return includeTrashed;
	}

	public void setIncludeTrashed(boolean includeTrashed) {
		this.includeTrashed = includeTrashed;
	}

	public void setListaMicrochip(ArrayList<String> listaMicrochip) {
		this.listaMicrochip = listaMicrochip;
	}

	public void setFlagSmarrimento(Boolean flagSmarrimento) {
		this.flagSmarrimento = flagSmarrimento;
	}

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
	private int annoNascita = -1;
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
	
	
	//Escludi animali con data prelievo leish nell'anno corrente
	private boolean checkControlloLeishAnnoCorrent = false;

	// Array degli stati
	private ArrayList<Integer> statiDaIncludere = new ArrayList<Integer>();
	// Array degli stati da escludere
	private ArrayList<Integer> statiDaEscludere = new ArrayList<Integer>();
	
	
	private boolean buildProprietario = true;
	private boolean filtroProprietarioDetentoreComune = false;

	/**
	 * Constructor for the AnimaliList object, creates an empty list. After
	 * setting parameters, call the build method.
	 * 
	 * @since 1.1
	 */
	public AnimaleList() {
	}
	
	public boolean isFiltroProprietarioDetentoreComune() {
		return filtroProprietarioDetentoreComune;
	}

	public void setFiltroProprietarioDetentoreComune(boolean filtroProprietarioDetentoreComune) {
		this.filtroProprietarioDetentoreComune = filtroProprietarioDetentoreComune;
	}

	public boolean isBuildProprietario() {
		return buildProprietario;
	}

	public void setBuildProprietario(boolean buildProprietario) {
		this.buildProprietario = buildProprietario;
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

	public int getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(int annoNascita) {
		this.annoNascita = annoNascita;
	}

	public void setAnnoNascita(String annoNascita) {
		this.annoNascita = new Integer(annoNascita).intValue();
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
	
	



	public boolean isCheckControlloLeishAnnoCorrent() {
		return checkControlloLeishAnnoCorrent;
	}

	public void setCheckControlloLeishAnnoCorrent(
			boolean checkControlloLeishAnnoCorrent) {
		this.checkControlloLeishAnnoCorrent = checkControlloLeishAnnoCorrent;
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
			Animale thisAnimale = this.getObject(rs);
			// Costruisco proprietario e detentore
			Operatore proprietario = null;
			Operatore detentore = null;

			if (buildProprietario && rs.getInt("id_proprietario") > -1) {
				proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, rs
						.getInt("id_proprietario"));
			}

			thisAnimale.setProprietario(proprietario);
			// thisAnimale.setDetentore(detentore);
//			if (this.getFlagDecesso()){ //SE DEVO CERCARE SOLO ANIMALI VIVI
//				if (!thisAnimale.getFlagDecesso()) //SE L'ANIMALE E' VIVO
//					this.add(thisAnimale);}
//			else
				this.add(thisAnimale); //ALTRIMENTI AGGIUNGILO COMUNQUE
			
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

if(checkControlloLeishAnnoCorrent){
	//sqlCount.append(" case (date_part('year',data_prelievo)) when (? ) then true else  false end as situazione_esito_leish, ");
	sqlCount.append(" case when ( e.data_prelievo is null ) then false else  true end as situazione_esito_leish , ");
}
				
				sqlCount.append(" COUNT(distinct a.* ) AS recordcount "+
				"from animale a " +
				"left join opu_operatori_denormalizzati prop on a.id_proprietario = prop.id_rel_stab_lp " +

		
		//cane
	//	"left join cane c on c.id_animale = a.id " +
		"left join opu_operatori_denormalizzati detcane on a.id_detentore = detcane.id_rel_stab_lp " );
	
		//gatto
		
	//	"left join gatto g on g.id_animale = a.id " +
	//	"left join opu_operatori_denormalizzati detgatto on g.id_detentore = detgatto.id " );
			/*	+ "from animale a left join opu_operatore p on (a.id_proprietario = p.id) "
				+ "left join opu_rel_operatore_soggetto_fisico rel on rel.id_operatore = p.id "
				+ "left join opu_soggetto_fisico soggp on soggp.id = rel.id_soggetto_fisico "
				+ "left join cane c on c.id_animale = a.id "
				+ " left join opu_operatore d on (c.id_detentore = d.id) " +
				  "left join gatto g on g.id_animale = a.id  " +
				  "left join opu_operatore og on (g.id_detentore = og.id) "
				+ "left join opu_rel_operatore_soggetto_fisico reld on reld.id_operatore = d.id "
				+ "left join opu_soggetto_fisico soggd on soggd.id = reld.id_soggetto_fisico ");*/

sqlCount.append(" WHERE a.id >= 0 ");

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
				+ DatabaseUtils.toLowerCase(db) + "(a.nome) < ? ");
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
	pagedListInfo.setColumnToSortBy("a.microchip");
	pagedListInfo.appendSqlTail(db, sqlOrder);

	// Optimize SQL Server Paging
	// sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization "
	// + sqlOrder.toString());
} else {
	sqlOrder.append("ORDER BY a.microchip asc ");
}

// Need to build a base SQL statement for returning records
if (pagedListInfo != null) {
	pagedListInfo.appendSqlSelectHead(db, sqlSelect);
} else {
	sqlSelect.append("SELECT ");
	
}



sqlSelect.
append("distinct a.*,prop.ragione_sociale as ragione_sociale_prop, prop.comune as id_comune_proprietario, detcane.ragione_sociale as ragione_sociale_det ");

if(checkControlloLeishAnnoCorrent){
	//sqlSelect.append(", case (date_part('year',data_prelievo)) when (? ) then true else  false end as situazione_esito_leish ");
	sqlSelect.append(", case when ( e.data_prelievo is null ) then false else  true end as situazione_esito_leish ");
}
/*						sqlSelect.append ("from animale a left join opu_operatore p on (a.id_proprietario = p.id) "
				+ "left join opu_rel_operatore_soggetto_fisico rel on rel.id_operatore = p.id "
				+ "left join opu_soggetto_fisico soggp on soggp.id = rel.id_soggetto_fisico "
				+ "left join cane c on c.id_animale = a.id "
				+ "left join opu_operatore d on (c.id_detentore = d.id) "
				+ "left join gatto g on g.id_animale = a.id  " +
				  "left join opu_operatore og on (g.id_detentore = og.id) "
				+ "left join opu_rel_operatore_soggetto_fisico reld on reld.id_operatore = d.id "
				+ "left join opu_soggetto_fisico soggd on soggd.id = reld.id_soggetto_fisico ");*/


sqlSelect.append("from animale a  " +
		"left join opu_operatori_denormalizzati prop on a.id_proprietario = prop.id_rel_stab_lp " +
		
		
		//cane
		//"left join cane c on c.id_animale = a.id " +
		"left join opu_operatori_denormalizzati detcane on a.id_detentore = detcane.id_rel_stab_lp " 

		
		//gatto
		
		//"left join gatto g on g.id_animale = a.id " +
	//	"left join opu_operatori_denormalizzati detgatto on g.id_detentore = detgatto.id " 

		

);
				if(checkControlloLeishAnnoCorrent){	
					/*sqlSelect.append(" left join esiti_leishmaniosi e on (e.id_animale = a.id and data_prelievo = " +
							"(select max (data_prelievo) from esiti_leishmaniosi where identificativo = a.microchip )) ");*/
					
					sqlSelect.append(" left join esiti_leishmaniosi e on e.id_animale = a.id and date_part('year',e.data_prelievo) = ? " );
				}

sqlSelect.append("WHERE a.id >= 0 ");

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
	
	/**
	 *  buildlist a partire dalle tabelle dell'operatore unico non materializzato
	 */
	/*public ResultSet queryList(Connection db, PreparedStatement pst)
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
		
		if(checkControlloLeishAnnoCorrent){
			sqlCount.append(" case (date_part('year',data_prelievo)) when (? ) then true else  false end as situazione_esito_leish, ");
		}
						
						sqlCount.append(" COUNT(distinct a.* ) AS recordcount "+
						"from animale a " +
						"left join opu_relazione_stabilimento_linee_produttive rel  on (a.id_proprietario = rel.id) "+
				"left join opu_stabilimento stab on (rel.id_stabilimento = stab.id) "+
				"left join opu_operatore o on (stab.id_operatore = o.id) "+
				"left join opu_rel_operatore_soggetto_fisico  rels on rels.id_operatore = o.id "+
				"left join opu_soggetto_fisico soggp on soggp.id = rels.id_soggetto_fisico " +
				
				//cane
				"left join cane c on c.id_animale = a.id " +
				"left join opu_relazione_stabilimento_linee_produttive reld  on (c.id_detentore = reld.id) "+ 
				"left join opu_stabilimento stabd on (reld.id_stabilimento = stabd.id) "+
				"left join opu_operatore od on (stabd.id_operatore = od.id) "+
				"left join opu_rel_operatore_soggetto_fisico  relsd on relsd.id_operatore = od.id "+
				"left join opu_soggetto_fisico soggd on soggd.id = relsd.id_soggetto_fisico "+
				
				//gatto
				
				"left join gatto g on g.id_animale = a.id " +
				"left join opu_relazione_stabilimento_linee_produttive relgd  on (g.id_detentore = relgd.id) " +
				"left join opu_stabilimento stabgd on (relgd.id_stabilimento = stabgd.id) "+
				"left join opu_operatore ogd on (stabgd.id_operatore = ogd.id) "+
				"left join opu_rel_operatore_soggetto_fisico  relsgd on relsgd.id_operatore = ogd.id "+
				"left join opu_soggetto_fisico sogggd on sogggd.id = relsgd.id_soggetto_fisico ");
					

		sqlCount.append(" WHERE a.id >= 0 ");

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
						+ DatabaseUtils.toLowerCase(db) + "(a.nome) < ? ");
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
			pagedListInfo.setColumnToSortBy("a.nome");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			// Optimize SQL Server Paging
			// sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization "
			// + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY a.nome ");
		}

		// Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
			
		}
		


		sqlSelect
				.append("distinct a.*, o.ragione_sociale as ragione_sociale_prop, coalesce (od.ragione_sociale, ogd.ragione_sociale) as ragione_sociale_det ");
		
		if(checkControlloLeishAnnoCorrent){
			sqlSelect.append(", case (date_part('year',data_prelievo)) when (? ) then true else  false end as situazione_esito_leish ");
		}

		
		
		sqlSelect.append("from animale a  " +
				"left join opu_relazione_stabilimento_linee_produttive rel  on (a.id_proprietario = rel.id) "+
				"left join opu_stabilimento stab on (rel.id_stabilimento = stab.id) "+
				"left join opu_operatore o on (stab.id_operatore = o.id) "+
				"left join opu_rel_operatore_soggetto_fisico  rels on rels.id_operatore = o.id "+
				"left join opu_soggetto_fisico soggp on soggp.id = rels.id_soggetto_fisico " +
				
				//cane
				"left join cane c on c.id_animale = a.id " +
				"left join opu_relazione_stabilimento_linee_produttive reld  on (c.id_detentore = reld.id) "+ 
				"left join opu_stabilimento stabd on (reld.id_stabilimento = stabd.id) "+
				"left join opu_operatore od on (stabd.id_operatore = od.id) "+
				"left join opu_rel_operatore_soggetto_fisico  relsd on relsd.id_operatore = od.id "+
				"left join opu_soggetto_fisico soggd on soggd.id = relsd.id_soggetto_fisico "+
				
				//gatto
				
				"left join gatto g on g.id_animale = a.id " +
				"left join opu_relazione_stabilimento_linee_produttive relgd  on (g.id_detentore = relgd.id) " +
				"left join opu_stabilimento stabgd on (relgd.id_stabilimento = stabgd.id) "+
				"left join opu_operatore ogd on (stabgd.id_operatore = ogd.id) "+
				"left join opu_rel_operatore_soggetto_fisico  relsgd on relsgd.id_operatore = ogd.id "+
				"left join opu_soggetto_fisico sogggd on sogggd.id = relsgd.id_soggetto_fisico "
				
		
		);
						if(checkControlloLeishAnnoCorrent){	
							sqlSelect.append(" left join esiti_leishmaniosi e on (e.id_animale = a.id) ");
						}

		sqlSelect.append("WHERE a.id >= 0 ");

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
	}*/

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
	
	protected void createFilter(Connection db, StringBuffer sqlFilter){
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}
		
		

		
		if (idAnimale > -1 || (microchip != null && !"".equals(microchip))||
				(tatuaggio != null && !"".equals(tatuaggio)) || 
				(numeroPassaporto != null && !"".equals(numeroPassaporto)) //||
			//	(denominazioneProprietario != null && !("").equals(denominazioneProprietario)) ||
			//	(denominazioneDetentore != null && !("").equals(denominazioneDetentore))  
				//(codiceFiscaleProprietario != null && !("").equals(codiceFiscaleProprietario)) ||
			//	(partitaIvaProprietario != null && !("").equals(partitaIvaProprietario))
		){
			
		
		if (idAnimale > -1){
			sqlFilter.append("and a.id = ?");
		}
		

		if (microchip != null && !"".equals(microchip)) {
			if (tatuaggio != null && !"".equals(tatuaggio))
				sqlFilter.append("and a.microchip = ? ");
			else
				sqlFilter.append("and (a.microchip = ? or a.tatuaggio =?)");

		}
		
		


		if (numeroPassaporto != null && !"".equals(numeroPassaporto)) {
			sqlFilter.append("and (passaporto_numero ilike ? or numero_passaporto ilike ? )");
		}
		if (tatuaggio != null && !"".equals(tatuaggio)) {
			sqlFilter.append("and a.tatuaggio = ? ");
		}
		
/*		if (denominazioneProprietario != null
				&& !"".equals(denominazioneProprietario)) {
			sqlFilter
					.append("and (prop.ragione_sociale ILIKE ?)");
		}
		
		if (denominazioneDetentore != null
				&& !"".equals(denominazioneDetentore)) {
			sqlFilter
					.append("and (detcane.ragione_sociale ILIKE ?)");
		}
		
		if (codiceFiscaleProprietario != null
				&& !"".equals(codiceFiscaleProprietario)) {
			sqlFilter.append("and prop.codice_fiscale = ? ");
		}

		if (partitaIvaProprietario != null
				&& !"".equals(partitaIvaProprietario)) {
			sqlFilter.append("and prop.partita_iva = ? ");
		}
*/		
		if (flagDecesso != null){
			sqlFilter.append(" and (a.flag_decesso = " + flagDecesso);
			if (!flagDecesso)
				sqlFilter.append(" or a.flag_decesso is null");
			sqlFilter.append(")");
			
		}
		
		if (!filtroProprietarioDetentoreComune && nomeComuneProprietario != null && !("").equals(nomeComuneProprietario)){
			sqlFilter.append(" and prop.comune in (select id from comuni1 where nome ilike ?)");
		}
		
		}
		else
		{
			
			if (listaMicrochip.size() > 0){
				sqlFilter.append("and (a.microchip  IN (");
				
				for (int h = 0; h < listaMicrochip.size(); h++){
					if (h > 0){
						sqlFilter.append(",");
					}
					sqlFilter.append("'" + listaMicrochip.get(h) + "'");
				}
				
				
				sqlFilter.append(") or a.tatuaggio IN (");
				
				for (int h = 0; h < listaMicrochip.size(); h++){
					if (h > 0){
						sqlFilter.append(",");
					}
					sqlFilter.append("'" + listaMicrochip.get(h) +"'");
				}
				sqlFilter.append(")");
				sqlFilter.append(")");
			}
			
			if (idSpecie > -1) {
				sqlFilter.append("and a.id_specie = ?");
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

//		if (codiceFiscaleProprietario != null
//				&& !"".equals(codiceFiscaleProprietario)) {
//			sqlFilter.append("and prop.codice_fiscale = ? ");
//		}
//
//		if (partitaIvaProprietario != null
//				&& !"".equals(partitaIvaProprietario)) {
//			sqlFilter.append("and prop.partita_iva = ? ");
//		}
		
		if (!filtroProprietarioDetentoreComune && nomeComuneProprietario != null && !("").equals(nomeComuneProprietario)){
			sqlFilter.append(" and prop.comune in (select id from comuni1 where nome ilike ?)");
		}



		if (codiceFiscaleDetentore != null
				&& !"".equals(codiceFiscaleDetentore)) {
			sqlFilter.append("and detcane.codice_fiscale = ? ");
		}

		if (partitaIvaDetentore != null && !"".equals(partitaIvaDetentore)) {
			sqlFilter.append("and detcane.partita_iva = ? ");
		}


		
		//if (!filtroProprietarioDetentoreComune && nomeComuneDetentore != null && !("").equals(nomeComuneDetentore)){
		if (nomeComuneDetentore != null && !("").equals(nomeComuneDetentore)){
			sqlFilter.append(" and detcane.comune in (select id from comuni1 where nome ilike ?)");
		}

		if (anno > -1) {
			sqlFilter
					.append(" AND a.data_inserimento > ? AND a.data_inserimento <  ? ");
		}

		if (annoNascita > -1) {
			sqlFilter
					.append(" AND a.data_nascita <  ? ");
		}
		
		if (id_proprietario_o_detentore > -1) {

			sqlFilter
					.append(" AND ( a.id_proprietario =  ? OR a.id_detentore =  ? )");
		}
		
		if (id_detentore > -1){
			sqlFilter
			.append(" AND  a.id_detentore =  ? ");
			
		}
		
		if (id_proprietario > -1){
			sqlFilter
			.append(" AND  a.id_proprietario =  ? ");
			
			
		}

		if (idStato > -1 && flagDecesso == null ) {
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
		
		
		if (statiDaEscludere.size() > 0) {
			sqlFilter.append(" AND a.stato not IN (");

			for (int k = 0; k < statiDaEscludere.size(); k++) {
				if (k == 0)
					sqlFilter.append(statiDaEscludere.get(k));
				else
					sqlFilter.append("," + statiDaEscludere.get(k));
			}
			sqlFilter.append(")");
		}
		
		if (flagDecesso != null){
			sqlFilter.append(" and (a.flag_decesso = " + flagDecesso);
			if (!flagDecesso)
				sqlFilter.append(" or a.flag_decesso is null");
			sqlFilter.append(")");
			
		}
		
		if (flagSmarrimento != null){
			sqlFilter.append(" and( a.flag_smarrimento is null or a.flag_smarrimento = " + flagSmarrimento + ")");
		}
		
		
		if (flagFurto != null){
			sqlFilter.append(" and( a.flag_furto is null or a.flag_furto = " + flagFurto + ")");
		}
		
		if (denominazioneProprietario != null
				&& !"".equals(denominazioneProprietario)) {
			sqlFilter
					.append("and (prop.ragione_sociale ILIKE ?)");
		}
		
		if (denominazioneDetentore != null
				&& !"".equals(denominazioneDetentore)) {
			sqlFilter
					.append("and (detcane.ragione_sociale ILIKE ?)");
		}
		
		if (codiceFiscaleProprietario != null
				&& !"".equals(codiceFiscaleProprietario)) {
			sqlFilter.append("and prop.codice_fiscale = ? ");
		}

		if (partitaIvaProprietario != null
				&& !"".equals(partitaIvaProprietario)) {
			sqlFilter.append("and prop.partita_iva = ? ");
		}
		}
		
		if (!includeTrashed)
			sqlFilter.append(" AND a.data_cancellazione is NULL and a.trashed_date is NULL ");
		
		if (filtroProprietarioDetentoreComune)
			sqlFilter.append(" and (prop.comune in (select id from comuni1 where nome ilike ?) or detcane.comune in (select id from comuni1 where nome ilike ?)) ");

		if (filtriPianoLeish){
			sqlFilter.append(" and prop.id_linea_produttiva in (3,5) and date_part('year',  age(a.data_nascita)) >=1  and date_part('year',  age(a.data_nascita)) <=8 ");
		}
		
		

	}
	
	/*
	 * per operatori non denormalizzati
	 */
	/*protected void createFilter(Connection db, StringBuffer sqlFilter) {

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
			if (tatuaggio != null && !"".equals(tatuaggio))
				sqlFilter.append("and a.microchip = ? ");
			else
				sqlFilter.append("and (a.microchip = ? or a.tatuaggio =?)");

		}

		if (numeroPassaporto != null && !"".equals(numeroPassaporto)) {
			sqlFilter.append("and passaporto_numero = ? ");
		}

		if (tatuaggio != null && !"".equals(tatuaggio)) {
			sqlFilter.append("and a.tatuaggio = ? ");
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

		if (annoNascita > -1) {
			sqlFilter
					.append(" AND a.data_nascita <  ? ");
		}
		
		if (id_proprietario_o_detentore > -1) {

			sqlFilter
					.append(" AND ( a.id_proprietario =  ? OR c.id_detentore =  ? OR g.id_detentore =  ? )");
		}
		
		if (id_detentore > -1){
			sqlFilter
			.append(" AND  c.id_detentore =  ? ");
			
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
		
		sqlFilter.append(" AND a.data_cancellazione is NULL ");



	}*/

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
		GregorianCalendar gc = new GregorianCalendar();
		
		if (checkControlloLeishAnnoCorrent){			
			pst.setInt(++i, gc.get(Calendar.YEAR));
		}

		

		if (idAnimale > -1 || (microchip != null && !"".equals(microchip)) || (tatuaggio != null && !"".equals(tatuaggio)) || 
				(numeroPassaporto != null && !"".equals(numeroPassaporto)) //|| 
					//	(denominazioneProprietario != null && !("").equals(denominazioneProprietario)) ||
					//	(denominazioneDetentore != null && !("").equals(denominazioneDetentore) )) //|| 
					//	(codiceFiscaleProprietario != null && !("").equals(codiceFiscaleProprietario)) ||
					//	(partitaIvaProprietario != null && !("").equals(partitaIvaProprietario)))
			)
		{
			
		if (idAnimale > -1){
			pst.setInt(++i, idAnimale);
		}
		
	

		if (microchip != null && !"".equals(microchip)) {
			
			if (tatuaggio != null && !"".equals(tatuaggio))
				pst.setString(++i, microchip);
			else
				pst.setString(++i, microchip);
			pst.setString(++i, microchip);


		}
		

		

		if (numeroPassaporto != null && !"".equals(numeroPassaporto)) {

			pst.setString(++i, numeroPassaporto);
			pst.setString(++i, numeroPassaporto);
		}

		if (tatuaggio != null && !"".equals(tatuaggio)) {
			pst.setString(++i, tatuaggio);
		}
		
		if (!filtroProprietarioDetentoreComune && nomeComuneProprietario != null
				&& !"".equals(nomeComuneProprietario)) {
			pst.setString(++i, nomeComuneProprietario);
		}
		
/*		
	if (denominazioneProprietario != null
				&& !"".equals(denominazioneProprietario)) {
			pst.setString(++i, denominazioneProprietario);
		//	pst.setString(++i, denominazioneProprietario); PRIMA C'ERA NOME E COGNOME SEPARATI IN RICERCA
		}
		
		if (denominazioneDetentore != null
				&& !"".equals(denominazioneDetentore)) {
			pst.setString(++i, denominazioneDetentore);
		//	pst.setString(++i, denominazioneDetentore); PRIMA C'ERA NOME E COGNOME SEPARATI IN RICERCA
		}
		
		if (codiceFiscaleProprietario != null
				&& !"".equals(codiceFiscaleProprietario)) {
			pst.setString(++i, codiceFiscaleProprietario);
		}

		if (partitaIvaProprietario != null
				&& !"".equals(partitaIvaProprietario)) {
			pst.setString(++i, partitaIvaProprietario);
		}
 
 */
		}
		else
		{
		if (idSpecie > -1) {
			pst.setInt(++i, idSpecie);
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

//		if (codiceFiscaleProprietario != null
//				&& !"".equals(codiceFiscaleProprietario)) {
//			pst.setString(++i, codiceFiscaleProprietario);
//		}
//
//		if (partitaIvaProprietario != null
//				&& !"".equals(partitaIvaProprietario)) {
//			pst.setString(++i, partitaIvaProprietario);
//		}
		
		if (!filtroProprietarioDetentoreComune && nomeComuneProprietario != null
				&& !"".equals(nomeComuneProprietario)) {
			pst.setString(++i, nomeComuneProprietario);
		}

//		if (denominazioneProprietario != null
//				&& !"".equals(denominazioneProprietario)) {
//			pst.setString(++i, denominazioneProprietario);
//		//	pst.setString(++i, denominazioneProprietario); PRIMA C'ERA NOME E COGNOME SEPARATI IN RICERCA
//		}

		if (codiceFiscaleDetentore != null
				&& !"".equals(codiceFiscaleDetentore)) {
			pst.setString(++i, codiceFiscaleDetentore);
		}

		if (partitaIvaDetentore != null && !"".equals(partitaIvaDetentore)) {
			pst.setString(++i, partitaIvaDetentore);
		}

//		if (denominazioneDetentore != null
//				&& !"".equals(denominazioneDetentore)) {
//			pst.setString(++i, denominazioneDetentore);
//		//	pst.setString(++i, denominazioneDetentore); PRIMA C'ERA NOME E COGNOME SEPARATI IN RICERCA
//		}
		
		//if (!filtroProprietarioDetentoreComune && nomeComuneDetentore != null && !"".equals(nomeComuneDetentore)) {
		if (nomeComuneDetentore != null	&& !"".equals(nomeComuneDetentore)) {
			pst.setString(++i, nomeComuneDetentore);
		}

		if (anno > -1) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd/MM/yyyy");
			pst.setTimestamp(++i, DatabaseUtils.parseDateToTimestampAssigendFormat("01/01/"
					+ anno, simpleDateFormat) );
			pst.setTimestamp(++i, DatabaseUtils.parseDateToTimestampAssigendFormat("31/12/"
					+ anno, simpleDateFormat));
		}

		if (annoNascita > -1) {
			pst.setTimestamp(++i, DatabaseUtils.parseDateToTimestamp("31/12/"
					+ annoNascita));
		}
		
		if (id_proprietario_o_detentore > -1) {

			pst.setInt(++i, id_proprietario_o_detentore);
			pst.setInt(++i, id_proprietario_o_detentore);
		//	pst.setInt(++i, id_proprietario_o_detentore);
		}
		
		if (id_detentore > -1){
			pst.setInt(++i, id_detentore);
			
		}
		
		if (id_proprietario > -1){
			pst.setInt(++i, id_proprietario);
			
		}

		if (idStato > -1 && flagDecesso == null) {
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
		
		if (denominazioneProprietario != null
				&& !"".equals(denominazioneProprietario)) {
			pst.setString(++i, denominazioneProprietario);
		//	pst.setString(++i, denominazioneProprietario); PRIMA C'ERA NOME E COGNOME SEPARATI IN RICERCA
		}
		
		if (denominazioneDetentore != null
				&& !"".equals(denominazioneDetentore)) {
			pst.setString(++i, denominazioneDetentore);
		//	pst.setString(++i, denominazioneDetentore); PRIMA C'ERA NOME E COGNOME SEPARATI IN RICERCA
		}
		
		if (codiceFiscaleProprietario != null
				&& !"".equals(codiceFiscaleProprietario)) {
			pst.setString(++i, codiceFiscaleProprietario);
		}

		if (partitaIvaProprietario != null
				&& !"".equals(partitaIvaProprietario)) {
			pst.setString(++i, partitaIvaProprietario);
		}
		
		
		}
		
		if (filtroProprietarioDetentoreComune) 
		{
			pst.setString(++i, nomeComuneProprietario);
			pst.setString(++i, nomeComuneDetentore);
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
	
	
	
	public ArrayList<Integer> getStatiDaEscludere() {
		return statiDaEscludere;
	}

	public void setStatiDaEscludere(ArrayList<Integer> statiDaEscludere) {
		this.statiDaEscludere = statiDaEscludere;
	}

	public void buildListImportatori(Connection dbImportatori,Connection db) throws SQLException,
			IndirizzoNotFoundException {
		PreparedStatement pst = null;
		
		ResultSet rs = queryListImportatori(dbImportatori, pst);
		while (rs.next()) {
			Animale thisAnimale = this.getObject(rs);
		
			// Costruisco proprietario e detentore
			Operatore proprietario = null;
			Operatore detentore = null;
		
			if (rs.getInt("id_proprietario") > -1) {
				proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, rs
						.getInt("id_proprietario"));
			}
		
			thisAnimale.setProprietario(proprietario);
			// thisAnimale.setDetentore(detentore);
			this.add(thisAnimale);
		
		}
		
		rs.close();
		if (pst != null) {
			pst.close();
		}
		// buildResources(db);
}
	

	public ResultSet queryListImportatori(Connection db, PreparedStatement pst)
	throws SQLException {
ResultSet rs = null;
int items = -1;

StringBuffer sqlSelect = new StringBuffer();
StringBuffer sqlCount = new StringBuffer();
StringBuffer sqlFilter = new StringBuffer();
StringBuffer sqlOrder = new StringBuffer();

// Ne




sqlSelect.append("select distinct a.* ");


				sqlSelect.append ("from animale a ");

sqlSelect.append("WHERE a.id_partita_circuito_commerciale = "+idPartita);

pst = db.prepareStatement(sqlSelect.toString());


rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
if (pagedListInfo != null) {
	pagedListInfo.doManualOffset(db, rs);
}
return rs;
}

	public Boolean getFiltriPianoLeish() {
		return filtriPianoLeish;
	}

	public void setFiltriPianoLeish(Boolean filtriPianoLeish) {
		this.filtriPianoLeish = filtriPianoLeish;
	}

	

}
