/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

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
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @created    August 30, 2001
 * @version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class SoggettoList extends Vector implements SyncableList {

	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.SoggettoList.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}



	public final static String tableName = "soggetto_fisico";
	public final static String uniqueField = "id";
	protected Boolean minerOnly = null;
	protected int typeId = 0;
	protected int stageId = -1;

	protected java.sql.Timestamp lastAnchor = null;
	protected java.sql.Timestamp nextAnchor = null;
	protected int syncType = Constants.NO_SYNC;
	protected PagedListInfo pagedListInfo = null;
	private String nome;
	private String cognome;
	private String codFiscale;
	private int tipoSoggettoFisico;
	private String ragioneSociale;
	private int statoRuolo = -1;

	/**
	 *  Constructor for the OrganizationList object, creates an empty list. After
	 *  setting parameters, call the build method.
	 *
	 * @since    1.1
	 */
	public SoggettoList() { }


	
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

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public int getTipoSoggettoFisico() {
		return tipoSoggettoFisico;
	}

	public void setTipoSoggettoFisico(int tipoSoggettoFisico) {
		this.tipoSoggettoFisico = tipoSoggettoFisico;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		SoggettoList.log = log;
	}

	public boolean isHasExpireDate() {
		return hasExpireDate;
	}

	public void setHasExpireDate(boolean hasExpireDate) {
		this.hasExpireDate = hasExpireDate;
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

	protected boolean hasAlertDate = false;
	protected boolean hasExpireDate = false;





	public int getStatoRuolo() {
		return statoRuolo;
	}

	public void setStatoRuolo(int statoRuolo) {
		this.statoRuolo = statoRuolo;
	}

	public void setStatoRuolo(String statoRuolo) {

		this.statoRuolo = new Integer(statoRuolo).intValue();

	}

	public boolean isHasAlertDate() {
		return hasAlertDate;
	}

	public void setHasAlertDate(boolean hasAlertDate) {
		this.hasAlertDate = hasAlertDate;
	}



	

	/**
	 *  Sets the lastAnchor attribute of the OrganizationList object
	 *
	 * @param  tmp  The new lastAnchor value
	 */
	public void setLastAnchor(java.sql.Timestamp tmp) {
		this.lastAnchor = tmp;
	}


	/**
	 *  Sets the lastAnchor attribute of the OrganizationList object
	 *
	 * @param  tmp  The new lastAnchor value
	 */
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





	/**
	 *  Sets the PagedListInfo attribute of the OrganizationList object. <p>
	 *
	 *  <p/>
	 *
	 *  The query results will be constrained to the PagedListInfo parameters.
	 *
	 * @param  tmp  The new PagedListInfo value
	 * @since       1.1
	 */
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}








	/**
	 *  
	 *
	 * @param  selectName  Description of Parameter
	 * @return             The HtmlSelect value
	 * @since              1.8
	 */
	public String getHtmlSelect(String selectName) {
		return getHtmlSelect(selectName, -1);
	}


	/**
	 *  
	 *
	 * @param  selectName  Description of Parameter
	 * @param  defaultKey  Description of Parameter
	 * @return             The HtmlSelect value
	 * @since              1.8
	 */
	public String getHtmlSelect(String selectName, int defaultKey) {
		HtmlSelect orgListSelect = new HtmlSelect();

		Iterator i = this.iterator();
		while (i.hasNext()) {
			SoggettoFisico thisSo = (SoggettoFisico) i.next();
			orgListSelect.addItem(
					thisSo.getIdSoggetto(),
					thisSo.getNome() + " " + thisSo.getCognome());
		}
		/*
    if (!(this.getHtmlJsEvent().equals(""))) {
      orgListSelect.setJsEvent(this.getHtmlJsEvent());
    }*/

		return orgListSelect.getHtml(selectName, defaultKey);
	}


	/**
	 *  Gets the HtmlSelectDefaultNone attribute of the OrganizationList object
	 *
	 * @param  selectName  Description of Parameter
	 * @param  thisSystem  Description of the Parameter
	 * @return             The HtmlSelectDefaultNone value
	 */
	public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName) {
		return getHtmlSelectDefaultNone(thisSystem, selectName, -1);
	}


	/**
	 *  Gets the htmlSelectDefaultNone attribute of the OrganizationList object
	 *
	 * @param  selectName  Description of the Parameter
	 * @param  defaultKey  Description of the Parameter
	 * @param  thisSystem  Description of the Parameter
	 * @return             The htmlSelectDefaultNone value
	 */
	public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName, int defaultKey) {
		HtmlSelect orgListSelect = new HtmlSelect();
		orgListSelect.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));

		Iterator i = this.iterator();
		while (i.hasNext()) {
			SoggettoFisico thisSo = (SoggettoFisico) i.next();
			orgListSelect.addItem(
					thisSo.getIdSoggetto(),
					thisSo.getNome() + " " + thisSo.getCognome());
		}

		/*    if (!(this.getHtmlJsEvent().equals(""))) {
      orgListSelect.setJsEvent(this.getHtmlJsEvent());
    }*/

		return orgListSelect.getHtml(selectName, defaultKey);
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @throws  SQLException  Description of the Exception
	 */
	public void select(Connection db) throws SQLException {
		buildList(db);
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  timeZone       Description of the Parameter
	 * @param  events         Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	/* public HashMap queryRecordCount(Connection db, TimeZone timeZone, HashMap events) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();

    String sqlDate = ((hasAlertDate ? "alertdate" : "") + (hasExpireDate ? "contract_end" : ""));

    createFilter(db, sqlFilter);

    sqlSelect.append(
        "SELECT " + sqlDate + " AS " + DatabaseUtils.addQuotes(db, "date")+ ", count(*) AS nocols " +
        "FROM organization o " +
        "WHERE o.org_id >= 0 ");
    sqlTail.append("GROUP BY " + sqlDate);
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertDate = DateUtils.getServerToUserDateString(
          timeZone, DateFormat.SHORT, rs.getTimestamp("date"));
      int thisCount = rs.getInt("nocols");
      if (events.containsKey(alertDate)) {
        int tmpCount = ((Integer) events.get(alertDate)).intValue();
        thisCount += tmpCount;
      }
      events.put(alertDate, new Integer(thisCount));
    }
    rs.close();
    pst.close();
    return events;
  }*/


	/*  *//**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @throws  SQLException  Description of the Exception
	 *//*
  public void buildShortList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();

    createFilter(db, sqlFilter);

    sqlSelect.append(
        "SELECT o.id, o.ragione_sociale, " +
        (hasAlertDate ? "o.alertdate, " : "") +
        (hasExpireDate ? "o.contract_end, " : "") +
        " FROM operatore o " +
        "WHERE o.id >= 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Operatore thisOp = new Operatore();
      thisOp.setIdOperatore(rs.getInt("id"));
      thisOp.setRagioneSociale(rs.getString("ragione_sociale"));
      if (hasAlertDate) {
     //   thisOrg.setAlertDate(rs.getTimestamp("alertdate"));
      }
      if (hasExpireDate) {
       // thisOrg.setContractEndDate(rs.getTimestamp("contract_end"));
      }
      thisOrg.setAlertText(rs.getString("alert"));
      thisOrg.setEntered(rs.getTimestamp("entered"));
      thisOrg.setEnteredBy(rs.getInt("enteredby"));
      thisOrg.setOwner(rs.getInt("owner"));
      this.add(thisOp);
    }
    rs.close();
    pst.close();
  }*/


	/**
	 *  Queries the database, using any of the filters, to retrieve a list of
	 *  organizations. The organizations are appended, so build can be run any
	 *  number of times to generate a larger list for a report.
	 *
	 * @param  db             Description of Parameter
	 * @throws  SQLException  Description of Exception
	 * @since                 1.1
	 */


	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			SoggettoFisico thisSoggetto = this.getObject(rs);

			this.add(thisSoggetto);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		buildResources(db);
	}
	/*

  public void buildListView(Connection db) throws SQLException  {

	  PreparedStatement pst = null;
	  ResultSet rs = null;
	  StringBuffer sqlSelect = new StringBuffer();
	  StringBuffer sqlFilter = new StringBuffer();
	  StringBuffer sqlCount = new StringBuffer();
	  StringBuffer sqlOrder = new StringBuffer();

	  sqlCount.append(" select count(*) as recordcount from vista_operatori_globale WHERE TRUE ");

	  //Gestire i filtri
	  createFilterView(db, sqlFilter);

	  if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());

	      prepareFilterView(pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();

	      pagedListInfo.appendSqlTailView(db, sqlOrder);
	  }

	  sqlSelect.append(
	        " SELECT distinct org_id, asl, tipologia_operatore, " +
	        " ragione_sociale, titolare, comune, provincia, codice_fiscale, "+
	        " partita_iva, codice_fiscale_rappresentante, stato_impresa, descrizione, "+
	        " stato_allevamento, stato, num_aut, n_reg "+
	        " FROM vista_operatori_globale " +
	        " WHERE TRUE ");  
	    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    prepareFilterView(pst);
	    rs = pst.executeQuery();
	    while (rs.next()) {
	      Organization thisOrg = new Organization();
	      thisOrg.setOrgId(rs.getInt("org_id"));
	      thisOrg.setName(rs.getString("ragione_sociale"));
	      thisOrg.setAsl(rs.getString("asl"));
	      thisOrg.setTipologia_operatore(rs.getString("tipologia_operatore"));
	      thisOrg.setTitolare(rs.getString("titolare"));
	      thisOrg.setCodiceFiscale(rs.getString("codice_fiscale"));
	      thisOrg.setCodiceFiscaleRappresentante(rs.getString("codice_fiscale_rappresentante"));
	      thisOrg.setCity(rs.getString("comune"));
	      thisOrg.setState(rs.getString("provincia"));
	      thisOrg.setN_reg(rs.getString("n_reg"));
	      thisOrg.setNum_aut(rs.getString("num_aut"));
	      thisOrg.setPartitaIva(rs.getString("partita_iva"));
	      thisOrg.setStato_impresa(rs.getString("stato_impresa"));
	      thisOrg.setStato(rs.getString("stato"));
	      thisOrg.setAlertText(rs.getString("descrizione"));
	      thisOrg.setStato_allevamento(rs.getString("stato_allevamento"));
	      this.add(thisOrg);
	    }
	    rs.close();
	    pst.close();



	  }

  private void prepareFilterView(PreparedStatement pst) throws SQLException {
	 int i = 0;

	 if(orgSiteId!=-1){
		 pst.setInt(++i, orgSiteId);
	 }

	 //Come prendere gli apici?
	 if (name != null && !"".equals(name)) {
	      pst.setString(++i, name.toLowerCase());
	 }

	 if (tipologia > -1) {
		 pst.setInt(++i, tipologia);
	 }

	 if (city != null && !"".equals(city)) {
	      pst.setString(++i, city.toLowerCase());
	 }

	 if (state != null && !"".equals(state)) {
	     pst.setString(++i, state.toLowerCase());
	 }


  }

private void createFilterView(Connection db, StringBuffer sqlFilter) {

	  if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }

	   //Filtro per Asl
	    if(orgSiteId!=-1){
	    	sqlFilter.append("AND asl_rif = ? ");
	    }

	    //Filtro per nome
	    if (name != null && !(name.equals(""))) {
	        if (name.indexOf("%") >= 0) {
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(ragione_sociale) ILIKE ? ");
	        } else {
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(ragione_sociale) = ? ");
	        }
	     }

	    //Aggiungere filtri anche sullo stato
	    if(tipologia != -1){
	    	sqlFilter.append(" AND tipologia= ?");
	    }


	    if (city != null && !"".equals(city)) {
	        if (city.indexOf("%") >= 0) {
	          sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(comune) ILIKE ? ");       
	        } else {
	        	sqlFilter.append(
	  	              "AND " + DatabaseUtils.toLowerCase(db) + "(comune) = ? ");
	        }
	      }

	    if (state != null && !"".equals(state)) {
	      if (state.indexOf("%") >= 0) {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) ILIKE ? ");
	      } else {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) = ? ");
	        }
	    }  

  }*/

	/**
	 *  Gets the object attribute of the OrganizationList object
	 *
	 * @param  rs             Description of the Parameter
	 * @return                The object value
	 * @throws  SQLException  Description of the Exception
	 */
	public SoggettoFisico getObject(ResultSet rs) throws SQLException {
		SoggettoFisico thisSoggetto = new SoggettoFisico(rs);
		return thisSoggetto;
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
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for counting records

		sqlCount.append(
				"SELECT  COUNT(distinct o.*) AS recordcount " +
				"FROM opu_soggetto_fisico o " +
				"left join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico)" +
		"left join opu_operatore op on (os.id_operatore = op.id)");

		//sqlCount.append(")");    
		/*    if (addressType==null || addressType==-1)
    {
    	sqlCount.append(" AND oa.address_type=5 )");
    }
    else
    {
    	sqlCount.append(")");
    }
    if ((istatSecondari!=null && ! istatSecondari.equals(""))) {
    	sqlCount.append("join la_imprese_linee_attivita lla on (o.org_id = lla.org_id) "); 
    	sqlCount.append("join la_rel_ateco_attivita lraa on (lla.id_rel_ateco_attivita = lraa.id)");
    	sqlCount.append("join lookup_codistat lc on (lc.code = lraa.id_lookup_codistat) ");
    }*/

		sqlCount.append(" WHERE o.id >= 0 ");

		createFilter(db, sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString());
			// UnionAudit(sqlFilter,db);

			items = prepareFilter(pst);


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
						"AND " + DatabaseUtils.toLowerCase(db) + "(o.cognome) < ? ");
				items = prepareFilter(pst);
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
			//  pagedListInfo.setColumnToSortBy("o.cognome");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY o.cognome ");
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}

		sqlSelect.append(
				"distinct o.*, o.id as id_soggetto ," +
				"i.*,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia   " +
				"FROM opu_soggetto_fisico o " +
				"left join opu_indirizzo i on o.indirizzo_id=i.id " +
				"left join comuni1 on (comuni1.id = i.comune) " +
				"left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true " +
				"left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
				"left join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) " +
		"left join opu_operatore op on (os.id_operatore = op.id)");

		// sqlSelect.append(" ) ");
		/*    	 	if (addressType==null || addressType==-1)
    	    {
    	 		sqlSelect.append(" AND oa.address_type=5 ) ");
    	    }
    	 	else
    	 	{
    	 		sqlSelect.append(" ) ");

    	 	}
    	 	if ((istatSecondari!=null && ! istatSecondari.equals(""))) {
    	 		sqlSelect.append("join la_imprese_linee_attivita lla on (o.org_id = lla.org_id) "); 
    	 		sqlSelect.append("join la_rel_ateco_attivita lraa on (lla.id_rel_ateco_attivita = lraa.id)");
    	 		sqlSelect.append("join lookup_codistat lc on (lc.code = lraa.id_lookup_codistat) ");
    	    }
		 */
		sqlSelect.append("WHERE o.id >= 0 ");




		pst = db.prepareStatement(
				sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);



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
	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		//andAudit( sqlFilter );
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}


		if (codFiscale != null && ! "".equals(codFiscale))
		{
			sqlFilter.append("and o.codice_fiscale ilike ? ");  		

		}


		if (nome != null && !"".equals(nome)){
			sqlFilter.append("and o.nome ilike ? ");
		}



		if (cognome != null && !"".equals(cognome)){
			sqlFilter.append("and o.cognome ilike ? ");
		}





		if (statoRuolo > -1){
			sqlFilter.append("and os.stato_ruolo = ?");
		}


		if (ragioneSociale != null && !"".equals(ragioneSociale)){
			sqlFilter.append("and op.ragione_sociale ilike ? ");
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
			SoggettoFisico thisSoggetto = (SoggettoFisico) i.next();
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

	//
	//  /**
	//   *  Description of the Method
	//   *
	//   * @param  db             Description of the Parameter
	//   * @param  newOwner       Description of the Parameter
	//   * @return                Description of the Return Value
	//   * @throws  SQLException  Description of the Exception
	//   */
	//  public int reassignElements(Connection db, int newOwner) throws SQLException {
	//    int total = 0;
	//    Iterator i = this.iterator();
	//    while (i.hasNext()) {
	//      Organization thisOrg = (Organization) i.next();
	//      if (thisOrg.reassign(db, newOwner)) {
	//        total++;
	//      }
	//    }
	//    return total;
	//  }
	//
	//
	//  /**
	//   *  Description of the Method
	//   *
	//   * @param  db             Description of the Parameter
	//   * @param  newOwner       Description of the Parameter
	//   * @param  userId         Description of the Parameter
	//   * @return                Description of the Return Value
	//   * @throws  SQLException  Description of the Exception
	//   */
	//  public int reassignElements(Connection db, int newOwner, int userId) throws SQLException {
	//    int total = 0;
	//    Iterator i = this.iterator();
	//    while (i.hasNext()) {
	//      Organization thisOrg = (Organization) i.next();
	//      thisOrg.setModifiedBy(userId);
	//      if (thisOrg.reassign(db, newOwner)) {
	//        total++;
	//      }
	//    }
	//    return total;
	//  }


	/**
	 *  Sets the parameters for the preparedStatement - these items must
	 *  correspond with the createFilter statement
	 *
	 * @param  pst            Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 * @since                 1.2
	 */
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;



		if (codFiscale != null && ! "".equals(codFiscale))
		{
			pst.setString(++i, "%"+codFiscale.replaceAll("%", "")+"%");	
		}



		if (nome != null && ! "".equals(nome))
		{
			pst.setString(++i, nome);	
		}


		if (cognome != null && ! "".equals(cognome))
		{
			pst.setString(++i, cognome);	
		}





		if (statoRuolo > -1){
			pst.setInt(++i, statoRuolo);
		}

		if (ragioneSociale != null && !"".equals(ragioneSociale)){
			pst.setString(++i, ragioneSociale);
		}

		return i;
	}


	//  protected int prepareFilterAdminNa3(PreparedStatement pst) throws SQLException {
	//	    int i = 0;
	//	    //i = setAudit( pst, i );
	//	    if(categoriaRischio!=3 && categoriaRischio!=-1){
	//
	//	        	  pst.setInt(++i, this.getCategoriaRischio());
	//
	//	    }
	//	   
	//	    if (nomeCorrentista != null) {
	//	  	  pst.setString(++i, this.getNomeCorrentista());
	//	    }
	//	    if (!"".equals(codiceFiscaleCorrentista) && codiceFiscaleCorrentista!=null) {
	//	    	pst.setString(++i, codiceFiscaleCorrentista);
	//	    }
	//	    if (!"".equals(cessato) && cessato > -1) {
	//	    pst.setInt(++i, cessato);
	//	    }
	//	    if (stageId > -1) {
	//	      pst.setInt(++i, stageId);
	//	    }
	//	    
	//	    if (minerOnly != null) {
	//	      pst.setBoolean(++i, minerOnly.booleanValue());
	//	    }
	//
	//	    if (enteredBy > -1) {
	//	      pst.setInt(++i, enteredBy);
	//	    }
	//	    
	//	    if (tipologia > -1) {
	//	        pst.setInt(++i, tipologia);
	//	     }
	//
	//	    if (name != null) {
	//	      pst.setString(++i, name.toLowerCase().replaceAll(" ", "%"));
	//	    }
	//
	//	    if (accountSegment != null) {
	//	      pst.setString(++i, accountSegment.toLowerCase());
	//	    }
	//
	//	    if (ownerId > -1) {
	//	      pst.setInt(++i, ownerId);
	//	    }
	//
	//	    if (includeEnabled == TRUE) {
	//	      pst.setBoolean(++i, true);
	//	    } else if (includeEnabled == FALSE) {
	//	      pst.setBoolean(++i, false);
	//	    }
	//
	//	    if (hasAlertDate) {
	//	      if (alertRangeStart != null) {
	//	        pst.setTimestamp(++i, alertRangeStart);
	//	      }
	//	      if (alertRangeEnd != null) {
	//	        pst.setTimestamp(++i, alertRangeEnd);
	//	      }
	//	    }
	//
	//	    if (hasExpireDate) {
	//	      if (alertRangeStart != null) {
	//	        pst.setTimestamp(++i, alertRangeStart);
	//	      }
	//	      if (alertRangeEnd != null) {
	//	        pst.setTimestamp(++i, alertRangeEnd);
	//	      }
	//	    }
	//	    if (syncType == Constants.SYNC_INSERTS) {
	//	      if (lastAnchor != null) {
	//	        pst.setTimestamp(++i, lastAnchor);
	//	      }
	//	      pst.setTimestamp(++i, nextAnchor);
	//	    }
	//	    if (syncType == Constants.SYNC_UPDATES) {
	//	      pst.setTimestamp(++i, lastAnchor);
	//	      pst.setTimestamp(++i, lastAnchor);
	//	      pst.setTimestamp(++i, nextAnchor);
	//	    }
	//
	//	    if (enteredSince != null) {
	//	      pst.setTimestamp(++i, enteredSince);
	//	    }
	//	    
	//	    if (enteredTo != null) {
	//	      pst.setTimestamp(++i, enteredTo);
	//	    }
	//	    
	//	    if (revenueOwnerId > -1) {
	//	      pst.setInt(++i, revenueOwnerId);
	//	    }
	//
	//	    if (accountNumber != null) {
	//	      pst.setString(++i, accountNumber.toLowerCase());
	//	      pst.setString(++i, accountNumber.toLowerCase());
	//	    }
	//	    if (codiceFiscale != null) {
	//	        pst.setString(++i, codiceFiscale.toUpperCase());
	//	      }
	//	    if (!"".equals(partitaIva) && partitaIva!=null) {
	//	        pst.setString(++i, partitaIva.toLowerCase());
	//	      }
	//	    if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
	//	  	  pst.setString(++i, this.getNomeRappresentante());
	//	    }
	//	    if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
	//	  	  pst.setString(++i, this.getCognomeRappresentante());
	//	    }    
	//	   
	//	    if (importId != -1) {
	//	      pst.setInt(++i, importId);
	//	    }
	//
	//	    if (statusId != -1) {
	//	      pst.setInt(++i, statusId);
	//	    }
	//
	//	    if (firstName != null) {
	//	      pst.setString(++i, firstName.toLowerCase());
	//	    }
	//
	//	    if (lastName != null) {
	//	      pst.setString(++i, lastName.toLowerCase());
	//	    }
	//
	//	    if (contactPhoneNumber != null) {
	//	      pst.setString(++i, contactPhoneNumber.toLowerCase());
	//	    }
	//
	//	    if (contactCity != null && !"-1".equals(contactCity)) {
	//	      pst.setString(++i, contactCity.toLowerCase());
	//	    }
	//
	//	    if (contactState != null && !"-1".equals(contactState)) {
	//	      pst.setString(++i, contactState.toLowerCase());
	//	    }
	//	    if (contactCountry != null && !"-1".equals(contactCountry)) {
	//	      pst.setString(++i, contactCountry.toLowerCase());
	//	    }
	//	    if (excludeUnapprovedAccounts) {
	//	      pst.setInt(++i, Import.PROCESSED_APPROVED);
	//	    }
	//
	//	    if (typeId > 0) {
	//	      pst.setInt(++i, typeId);
	//	    }
	//
	//	    if (orgId > 0) {
	//	      pst.setInt(++i, orgId);
	//	    }
	//	    if (projectId > 0) {
	//	      pst.setInt(++i, projectId);
	//	    }
	//	    if (includeOnlyTrashed) {
	//	      // do nothing
	//	    } else if (trashedDate != null) {
	//	      pst.setTimestamp(++i, trashedDate);
	//	    } else {
	//	      // do nothing
	//	    }
	//	    if (postalCode != null) {
	//	      pst.setString(++i, postalCode.toLowerCase());
	//	    }
	//	    if (city != null && !"-1".equals(city)) {
	//	      pst.setString(++i, city.toLowerCase());
	//	    }
	//	    if (state != null && !"-1".equals(state)) {
	//	      pst.setString(++i, state.toLowerCase());
	//	    }
	//	    if (country != null && !"-1".equals(country)) {
	//	      pst.setString(++i, country.toLowerCase());
	//	    }
	//	    if (assetSerialNumber != null) {
	//	      pst.setString(++i, assetSerialNumber);
	//	    }
	//	    return i;
	//	  }


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
	 *  Gets the parent and leaf Accounts in the application Parent accounts are
	 *  the list of root nodes in the organization hierarchies Leaf accounts are
	 *  the list of leaf nodes in the organization hierarchies
	 *
	 * @param  db                Description of the Parameter
	 * @param  typeId            Description of the Parameter
	 * @param  reciprocal        Description of the Parameter
	 * @return                   The parentAccounts value
	 * @exception  SQLException  Description of the Exception
	 */
	/*  public static HashMap getParentAndLeafAccounts(Connection db, int typeId, boolean reciprocal) throws SQLException {
    HashMap allAccounts = new HashMap();
    HashMap leafAccounts = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT org_id FROM organization WHERE trashed_date IS NULL ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      Integer accountId = new Integer(rs.getInt("org_id"));
      allAccounts.put(accountId, accountId);
      leafAccounts.put(accountId, accountId);
    }
    rs.close();
    pst.close();
    RelationshipList thisList = new RelationshipList();
    thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
    thisList.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);
    thisList.setTypeId(typeId);
    thisList.buildList(db);
    Iterator iter = (Iterator) thisList.keySet().iterator();
    while (iter.hasNext()) {
      String relType = (String) iter.next();
      ArrayList tmpList = (ArrayList) thisList.get(relType);
      Iterator j = tmpList.iterator();
      while (j.hasNext()) {
        Relationship rel = (Relationship) j.next();
        //check for parent accounts to be in the child category
        if (allAccounts.get(new Integer(rel.getObjectIdMapsTo())) != null && reciprocal) {
          allAccounts.remove(new Integer(rel.getObjectIdMapsTo()));
        } else if (allAccounts.get(new Integer(rel.getObjectIdMapsFrom())) != null && !reciprocal) {
          allAccounts.remove(new Integer(rel.getObjectIdMapsFrom()));
        }
        //check for child accounts to be in the parent category
        if (leafAccounts.get(new Integer(rel.getObjectIdMapsFrom())) != null && reciprocal) {
          leafAccounts.remove(new Integer(rel.getObjectIdMapsFrom()));
        } else if (leafAccounts.get(new Integer(rel.getObjectIdMapsTo())) != null && !reciprocal) {
          leafAccounts.remove(new Integer(rel.getObjectIdMapsTo()));
        }
      }
    }
    HashMap combinedAccounts = new HashMap();
    combinedAccounts.put("parentNodes", allAccounts);
    combinedAccounts.put("leafNodes", leafAccounts);
    return combinedAccounts;
  }
	 */

	/**
	 *  Description of the Method
	 *
	 * @param  db                Description of the Parameter
	 * @param  childId           Description of the Parameter
	 * @param  skipName          Description of the Parameter
	 * @param  existingAccounts  Description of the Parameter
	 * @return                   Description of the Return Value
	 * @exception  SQLException  Description of the Exception
	 */
	/*  public static String buildParentNameHierarchy(Connection db, int childId, boolean skipName, HashMap existingAccounts) throws SQLException {
    if (existingAccounts == null) {
      existingAccounts = new HashMap();
    }
    StringBuffer parentName = new StringBuffer();
    RelationshipList thisList = new RelationshipList();
    thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
    thisList.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);
    thisList.setObjectIdMapsFrom(childId);
    existingAccounts.put(String.valueOf(childId), new Integer(childId));
    thisList.setTypeId(1);
    thisList.buildList(db);
    Iterator iter = (Iterator) thisList.keySet().iterator();
    if (iter.hasNext()) {
      String relType = (String) iter.next();
      ArrayList tmpList = (ArrayList) thisList.get(relType);
      Iterator j = tmpList.iterator();
      if (j.hasNext()) {
        Relationship rel = (Relationship) j.next();
        if (existingAccounts.get(String.valueOf(rel.getObjectIdMapsTo())) == null) {
          parentName.append(buildParentNameHierarchy(db, rel.getObjectIdMapsTo(), false, existingAccounts));
          if (!skipName) {
            parentName.append(", ");
          }
        }
      }
    }
    Organization org = new Organization(db, childId);
    if (!skipName) {
      parentName.append(org.getName());
    }
    return parentName.toString();
  }*/


	/**
	 *  Gets the orgById attribute of the OrganizationList object This method
	 *  assumes that the value of id is > 0
	 *
	 * @param  id  The value of id is always greater than 0.
	 * @return     returns the matched organization or returns null
	 */
	public SoggettoFisico getOrgById(int id) {
		SoggettoFisico result = null;
		Iterator iter = (Iterator) this.iterator();
		while (iter.hasNext()) {
			SoggettoFisico org = (SoggettoFisico) iter.next();
			if (org.getIdSoggetto() == id) {
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

