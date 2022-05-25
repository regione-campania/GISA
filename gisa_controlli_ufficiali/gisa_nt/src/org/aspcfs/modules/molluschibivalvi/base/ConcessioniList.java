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
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
public class ConcessioniList extends Vector {

		private static Logger log = Logger.getLogger(ConcessioniList.class);

		private int id ;
		private String name ;
		private String titolare_nome ;
		private String titolare_cognome;

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
		protected PagedListInfo pagedListInfo = null;
		private int idZona ;
		private int idConcessionario ;
		private boolean inScadenza = false ;
		private boolean enabled = true ;
		private boolean isTrashed = false ;
		
		public boolean isEnabled() {
			return enabled;
		}


		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}


		public boolean isTrashed() {
			return isTrashed;
		}


		public void setTrashed(boolean isTrashed) {
			this.isTrashed = isTrashed;
		}


		public boolean isInScadenza() {
			return inScadenza;
		}


		public void setInScadenza(boolean inScadenza) {
			this.inScadenza = inScadenza;
		}


		public int getIdConcessionario() {
			return idConcessionario;
		}


		public void setIdConcessionario(int idConcessionario) {
			this.idConcessionario = idConcessionario;
		}


		public int getIdZona() {
			return idZona;
		}


		public void setIdZona(int idZona) {
			this.idZona = idZona;
		}


		public PagedListInfo getPagedListInfo() {
			return pagedListInfo;
		}


		public void setPagedListInfo(PagedListInfo pagedListInfo) {
			this.pagedListInfo = pagedListInfo;
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
		
		public String getTitolare_nome() {
			return titolare_nome;
		}
		public void setTitolare_nome(String titolare_nome) {
			this.titolare_nome = titolare_nome;
		}
		public String getTitolare_cognome() {
			return titolare_cognome;
		}
		public void setTitolare_cognome(String titolare_cognome) {
			this.titolare_cognome = titolare_cognome;
		}
		public String getCfTitolare() {
			return cfTitolare;
		}
		public void setCfTitolare(String cfTitolare) {
			this.cfTitolare = cfTitolare;
		}
	
		public Concessionario getObject(ResultSet rs) throws SQLException {
			Concessionario thisOrganization = new Concessionario(rs);
			return thisOrganization;
		}
		
		protected void buildResources(Connection db) throws SQLException {
		    Iterator i = this.iterator();
		    while (i.hasNext()) {
		      org.aspcfs.modules.molluschibivalvi.base.Concessionario thisOrganization = (org.aspcfs.modules.molluschibivalvi.base.Concessionario ) i.next();
		      thisOrganization.getAddressList().buildList(db);
		     
		    }
		  }
		
		public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    ResultSet rs = queryList(db, pst);
		    while (rs.next()) 
		    {
		    	Concessionario thisOrganization = this.getObject(rs);
		    	
		    	
		    	OrganizationAddressList lista = new OrganizationAddressList();
		    	lista.setOrgId(thisOrganization.getId());
		    	lista.buildList(db);
		    	thisOrganization.setAddressList(lista);
		    	Concessione concessione = new Concessione();
		    	concessione.setConcessionario(thisOrganization);
		    	concessione.setDataConcessione(rs.getTimestamp("data_concessione"));
		    	concessione.setDataScadenza(rs.getTimestamp("data_scadenza"));
		    	concessione.setNumConcessione(rs.getString("num_concessione"));
		    	concessione.setEnabled(rs.getBoolean("enabled"));
		    	concessione.setIdSospensione(rs.getInt("id_sospensione"));
		    	concessione.setIdConcessionario(rs.getInt("id_concessionario"));
		    	concessione.setIdZona(rs.getInt("id_zona"));
		    	concessione.setDataSospensione(rs.getTimestamp("conc_data_sospensione"));
		    	concessione.setTrashed(rs.getTimestamp("trashed_date"));
		    	concessione.setNumeroDecreto(rs.getString("numero_decreto"));
		    	concessione.setDataDecreto(rs.getTimestamp("data_decreto"));
		    	Organization zona = new Organization(db,rs.getInt("id_zona"),false);
		    	concessione.setZona(zona);
		    	
		    	this.add(concessione);
		     
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
			
			createFilter(db, sqlFilter);
			
			sqlSelect.append(
			        "SELECT distinct o.*, " +
			        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode," +
			        "oa.county as o_county,oa.addrline1,oa.latitude,oa.longitude,c_associati.data_sospensione as conc_data_sospensione, c_associati.*  " +
			        "FROM organization o "  + 
			        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " +
					"left join concessionari_associati_zone_in_concessione c_associati on (c_associati.id_concessionario = o.org_id) " +
					"where o.tipologia = 211  " );

			pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + " order by o.name,c_associati.id");
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
			
			
			if(idZona > 0)
			{
				sqlFilter.append(" and c_associati.id_zona = ? ");
			}
			if(siteId > 0)
			{
				sqlFilter.append(" and site_id = ? ");
			}

			if(name!=null && !"".equals("name"))
			{
				sqlFilter.append(" and name ilike ? ");
			}
			if(idConcessionario > 0)
			{
				sqlFilter.append(" and c_associati.id_concessionario = ? ");
			}
			if(inScadenza==true)
			{
				sqlFilter.append(" and current_date >= c_associati.data_scadenza -interval'3 month' ");
			}
			if (siteId>0)
			{
				sqlFilter.append(" and o.site_id = ?");
			}
			if (enabled == true)
			{
				sqlFilter.append(" and c_associati.enabled = true ");
			}
			if (isTrashed==false)
			{
				sqlFilter.append(" and c_associati.trashed_date is null  ");
			}

		
		}


		protected int prepareFilter(PreparedStatement pst) throws SQLException {
			int i = 0;
			
			if(idZona > 0)
			{
				pst.setInt(++i, idZona);
			}

			if(siteId > 0)
			{
				pst.setInt(++i, siteId);
			}

			if(name!=null && !"".equals("name"))
			{
				pst.setString(++i, name);
			}
			if(idConcessionario > 0)
			{
				pst.setInt(++i, idConcessionario);
			}
			if(siteId > 0)
			{
				pst.setInt(++i, siteId);
			}
			return i;
		}

		

	}

