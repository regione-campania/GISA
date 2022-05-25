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
package org.aspcfs.modules.molluschibivalvi.base;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.molluschibivalvi.base.Organization;
import org.aspcfs.modules.molluschibivalvi.base.OrganizationAddressList;
import org.aspcfs.modules.relationships.base.Relationship;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


public class OrganizationList extends Vector {

	private static Logger log = Logger.getLogger(org.aspcfs.modules.molluschibivalvi.base.OrganizationList.class);
	protected PagedListInfo pagedListInfo = null;
	private int orgId = -1						;
	private String name = ""					;
	private int siteId = -1						;
	private java.sql.Timestamp entered = null	;
	private java.sql.Timestamp modified = null	;
	private int enteredBy 						;
	private int modifiedBy 						;
	private int tipologia 						;
	private int tipoMolluschi 					;
	private int classe 							;
	private Timestamp dataClassificazione 		;
	private boolean sottopostaDeclassamento 	;
	private int declassataIn 					;
	private String decretoClassificazione 		;
	private String nome 						;
	private String cognome 						;
	private String note 						;
	private int tipoMolluschicoltura			;
	private String comune ;
	private String cun ;
	private String[] specieMolluschi ;
	private int stato ;
	private boolean scadenzario = false ;
	private String concessionario = "";
	private boolean onlyCun = false;
	
	
	
	public String getConcessionario() {
		return concessionario;
	}


	public void setConcessionario(String concessionario) {
		this.concessionario = concessionario;
	}


	public String[] getSpecieMolluschi() {
		return specieMolluschi;
	}


	public void setSpecieMolluschi(String[] specieMolluschi) {
		this.specieMolluschi = specieMolluschi;
	}


	public String getCun() {
		return cun;
	}


	public void setCun(String cun) {
		this.cun = cun;
	}


	public boolean isScadenzario() {
		return scadenzario;
	}


	public void setScadenzario(boolean scadenzario) {
		this.scadenzario = scadenzario;
	}


	public int getStato() {
		return stato;
	}


	public void setStato(int stato) {
		this.stato = stato;
	}
	
	public void setStato(String stato) {
		
		this.stato = Integer.parseInt(stato);
	}


	public String getComune() {
		return comune;
	}


	public void setComune(String comune) {
		this.comune = comune;
	}


	public int getTipoMolluschicoltura() {
		return tipoMolluschicoltura;
	}


	public void setTipoMolluschicoltura(int tipoMolluschicoltura) {
		this.tipoMolluschicoltura = tipoMolluschicoltura;
	}


	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}


	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}


	public int getOrgId() {
		return orgId;
	}


	public void setOrgId(int orgId) {
		this.orgId = orgId;
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


	public int getTipoMolluschi() {
		return tipoMolluschi;
	}


	public void setTipoMolluschi(int tipoMolluschi) {
		this.tipoMolluschi = tipoMolluschi;
	}
	
	public void setTipoMolluschi(String tipoMolluschi) {
		this.tipoMolluschi = Integer.parseInt(tipoMolluschi);
	}



	public int getClasse() {
		return classe;
	}


	public void setClasse(int classe) {
		this.classe = classe;
	}
	
	public void setClasse(String classe) {
		this.classe = Integer.parseInt(classe);
	}


	public Timestamp getDataClassificazione() {
		return dataClassificazione;
	}


	public void setDataClassificazione(Timestamp dataClassificazione) {
		this.dataClassificazione = dataClassificazione;
	}


	public boolean isSottopostaDeclassamento() {
		return sottopostaDeclassamento;
	}


	public void setSottopostaDeclassamento(boolean sottopostaDeclassamento) {
		this.sottopostaDeclassamento = sottopostaDeclassamento;
	}


	public int getDeclassataIn() {
		return declassataIn;
	}


	public void setDeclassataIn(int declassataIn) {
		this.declassataIn = declassataIn;
	}


	public String getDecretoClassificazione() {
		return decretoClassificazione;
	}


	public void setDecretoClassificazione(String decretoClassificazione) {
		this.decretoClassificazione = decretoClassificazione;
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


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Organization getObject(ResultSet rs) throws SQLException {
		Organization thisOrganization = new Organization(rs);
		return thisOrganization;
	}
	
	protected void buildResources(Connection db) throws SQLException {
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      org.aspcfs.modules.molluschibivalvi.base.Organization thisOrganization = (org.aspcfs.modules.molluschibivalvi.base.Organization ) i.next();
	      thisOrganization.getAddressList().buildList(db);
	     
	    }
	  }
	
	public void buildList(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) 
	    {
	    	Organization thisOrganization = this.getObject(rs);
	    	thisOrganization.setTipoMotivi(db);
	    	OrganizationAddressList lista = new OrganizationAddressList();
	    	lista.setOrgId(thisOrganization.getId());
	    	lista.buildList(db);
	    	thisOrganization.setAddressList(lista);
	        this.add(thisOrganization);
	     
	    }
	 
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	   
	  }

	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		sqlCount.append("SELECT  COUNT(distinct o.*) AS recordcount " +
						"FROM organization o " +
						" left join organization_address oa on (oa.org_id = o.org_id) " +
						 " left join concessionari_associati_zone_in_concessione c_associati on (c_associati.id_zona = o.org_id) "+ 
						 " left join organization org_conc on org_conc.org_id = c_associati.id_concessionario " + 
						 " join tipo_molluschi on o.org_id = id_molluschi " );
		sqlCount.append(" WHERE o.org_id >= 0 and o.tipologia = 201 and o.trashed_date is null ");
		createFilter(db, sqlFilter);
		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() +sqlFilter.toString());
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
				pst = db.prepareStatement(sqlCount.toString() +sqlFilter.toString() +"AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");
				items = prepareFilter(pst);
				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
				rs = pst.executeQuery();
				if (rs.next()) 
				{
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("o.cun NULLS LAST, o.site_id,oa.city");
			pagedListInfo.appendSqlTail(db, sqlOrder);
		} else 
		{
			sqlOrder.append("ORDER BY o.cun NULLS LAST, o.name ");
		}

		if (pagedListInfo != null) 
		{
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else 
		{
			sqlSelect.append("SELECT ");
		}
		sqlSelect.append(
		        "distinct o.*, " +
		        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode," +
		        "oa.county as o_county,oa.addrline1,oa.latitude,oa.longitude  " +
		        " FROM organization o "  +
		        " left join concessionari_associati_zone_in_concessione c_associati on (c_associati.id_zona = o.org_id) "+ 
				" left join organization org_conc on org_conc.org_id = c_associati.id_concessionario " + 
		        " join tipo_molluschi on o.org_id = id_molluschi " +
		        " LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) where o.tipologia = 201  and o.trashed_date is null ");
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}
		rs = DatabaseUtils.executeQuery(db, pst, log);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}
	
	


	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		
		
		if (specieMolluschi!= null && specieMolluschi.length>1)
		{
			
			sqlFilter.append(" and id_matrice in ( ");
			
			for (int i = 0 ; i < specieMolluschi.length-1;i++)
			{
				if (!specieMolluschi[i].equals("-1"))
					sqlFilter.append("?,");
			}
			
			if (!specieMolluschi[specieMolluschi.length-1].equals("-1"))
				sqlFilter.append("?) ");
		}
		else
		{
			if (specieMolluschi!= null && specieMolluschi.length==1 && !"-1".equals(specieMolluschi[0]))
			{
				sqlFilter.append(" and id_matrice in ( ?) ");
				
			}
		}
		
		if (cun!=null && !cun.equalsIgnoreCase(""))
		{
			sqlFilter.append(" and o.cun ilike ? ");
		}
		if (concessionario!=null && !concessionario.equalsIgnoreCase(""))
		{
			sqlFilter.append("and org_conc.name ilike ? and c_associati.enabled = true  and c_associati.trashed_date is null and org_conc.tipologia = 211 ");
		}
		if (scadenzario == true)
		{
			sqlFilter.append(" and (current_timestamp>o.data_fine_classificazione or (current_timestamp>o.data_fine_classificazione-interval '6 month') ) and o.data_fine_classificazione is not null ");
		}
		if(tipoMolluschi>0)
		{
			sqlFilter.append(" and o.tipologia_acque = ? ");
		}
		if(siteId > 0)
		{
			sqlFilter.append(" and o.site_id = ? ");
		}

		if(comune!=null && ! "".equals(comune) && ! "%-1%".equals(comune))
		{
			sqlFilter.append(" and oa.city ilike ? ");
		}
		if (classe >0)
		{
			sqlFilter.append(" and o.classificazione = ? ");
		}
		if (stato>-1)
			sqlFilter.append(" and o.stato_classificazione=? ");
		/*if (stato!= null && stato.equalsIgnoreCase("%da classificare%"))
		{
			sqlFilter.append(" and o.data_fine_classificazione is null and o.tipologia_acque not in (4) ");
		}
		else
		{
			if (stato!= null && stato.equalsIgnoreCase("%classificato%"))
			{
				sqlFilter.append(" and o.data_fine_classificazione > current_date + interval '3 month' and o.tipologia_acque not in (4) ");
			}
			else
			{
				if (stato!= null && stato.equalsIgnoreCase("%in scadenza%"))
				{
					sqlFilter.append(" and o.data_fine_classificazione > current_date and o.data_fine_classificazione < current_date + interval '3 month' and o.tipologia_acque not in (4) ");
				}
				else
				{
					if (stato!= null && stato.equalsIgnoreCase("%scaduto%"))
					{
						sqlFilter.append(" and o.data_fine_classificazione < current_date and o.tipologia_acque not in (4) ");
					}
					else
						if (stato!= null && stato.equalsIgnoreCase("%non classificato%"))
						{
							sqlFilter.append(" and o.tipologia_acque =4 ");
						}
					
				}
				
			}
		}*/
		
		if (onlyCun)
		{
			sqlFilter.append(" and o.cun is not null and o.cun <> '' ");
		}
		
	}


	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		
		if (specieMolluschi!= null && specieMolluschi.length>1)
		{
			
			
			for (int j = 0 ; j < specieMolluschi.length;j++)
			{
				if (!specieMolluschi[j].equals("-1"))
				pst.setInt(++i, Integer.parseInt(specieMolluschi[j]));

			}
		}
		else
		{
			if (specieMolluschi!= null && specieMolluschi.length==1 && !"-1".equals(specieMolluschi[0]))
			{
				pst.setInt(++i, Integer.parseInt(specieMolluschi[0]));
				
			}
		}
		
		if (cun!=null && !cun.equalsIgnoreCase(""))
		{
			pst.setString(++i, cun);
		}
		
		if (concessionario!=null && !concessionario.equalsIgnoreCase(""))
		{
			pst.setString(++i, concessionario);
		}
		
		if(tipoMolluschi>0)
		{
			pst.setInt(++i, tipoMolluschi);
		}
		if(siteId > 0)
		{
			pst.setInt(++i, siteId);
		}

		if(comune!=null && ! "".equals(comune) && ! "%-1%".equals(comune))
		{
			pst.setString(++i, comune);
		}
		if (classe >0)
		{
			pst.setInt(++i, classe);
		}
		if (stato>-1)
			pst.setInt(++i, stato);
		return i;
	}


	public boolean isOnlyCun() {
		return onlyCun;
	}


	public void setOnlyCun(boolean onlyCun) {
		this.onlyCun = onlyCun;
	}
	
	public void setOnlyCun(String onlyCun) {
		try {this.onlyCun = Boolean.parseBoolean(onlyCun);} catch (Exception e) {}
	}
	

}

