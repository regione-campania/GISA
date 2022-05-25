/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiUploadDelegaApicoltore;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class DelegaList  extends Vector implements SyncableList {
	
	  private final static Logger log = Logger.getLogger(ext.aspcfs.modules.apiari.base.DelegaList.class);

	private PagedListInfo pagedListInfo = null;
	private int id ;
	private String codice_fiscale_delegato ;
	private int id_utente_access_ext_delegato ;
	private String  codice_fiscale_delegante ;
	private int id_soggetto_fisico_delegante ;
	private Timestamp data_assegnazione_delega ;
	private int entered_by ;
	private int modified_by ;
	private Timestamp entered ;
	private Timestamp modified ;
	private boolean  enabled  ;
	
	private int idAsl ;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		if (id != null && !id.equals(""))
		this.id = Integer.parseInt(id);
	}
	
	public String getCodice_fiscale_delegato() {
		return codice_fiscale_delegato;
	}
	
	
	public void setCodice_fiscale_delegato(String codice_fiscale_delegato) {
		this.codice_fiscale_delegato = codice_fiscale_delegato;
	}
	public int getId_utente_access_ext_delegato() {
		return id_utente_access_ext_delegato;
	}
	public void setId_utente_access_ext_delegato(int id_utente_access_ext_delegato) {
		this.id_utente_access_ext_delegato = id_utente_access_ext_delegato;
	}
	
	public void setId_utente_access_ext_delegato(String id_utente_access_ext_delegato) {
		if (id_utente_access_ext_delegato != null && !id_utente_access_ext_delegato.equals(""))
			this.id_utente_access_ext_delegato = Integer.parseInt(id_utente_access_ext_delegato);	
		}
	
	public String getCodice_fiscale_delegante() {
		return codice_fiscale_delegante;
	}
	public void setCodice_fiscale_delegante(String codice_fiscale_delegante) {
		this.codice_fiscale_delegante = codice_fiscale_delegante;
	}
	public Timestamp getData_assegnazione_delega() {
		return data_assegnazione_delega;
	}
	public void setData_assegnazione_delega(Timestamp data_assegnazione_delega) {
		this.data_assegnazione_delega = data_assegnazione_delega;
	}
	
	public void setData_assegnazione_delega(String data_assegnazione_delega) throws ParseException {
		if (data_assegnazione_delega!= null && !"".equals(data_assegnazione_delega))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_assegnazione_delega =new Timestamp(sdf.parse(data_assegnazione_delega).getTime());
			
		}
	}
	
	public int getEntered_by() {
		return entered_by;
	}
	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}
	public int getModified_by() {
		return modified_by;
	}
	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getId_soggetto_fisico_delegante() {
		return id_soggetto_fisico_delegante;
	}
	public void setId_soggetto_fisico_delegante(int id_soggetto_fisico_delegante) {
		this.id_soggetto_fisico_delegante = id_soggetto_fisico_delegante;
	}
	
	public void setId_soggetto_fisico_delegante(String id_soggetto_fisico_delegante) {
		if (id_soggetto_fisico_delegante!=null && !"".equals(id_soggetto_fisico_delegante))
			this.id_soggetto_fisico_delegante = Integer.parseInt(id_soggetto_fisico_delegante);
	}
	
	
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		
		sqlCount.append(" select  count(*) from apicoltura_deleghe delega left join apicoltura_imprese "
				+ " imp on imp.codice_fiscale_impresa ilike delega.codice_fiscale_delegante and imp.trashed_date is null and imp.stato!=4"
				+ " where delega.trashed_date is null and 1=1 ");
		
//		sqlCount.append("select count( delega.id) as recordcount "
//				+ "from apicoltura_deleghe delega where 1=1 and trashed_date is null ");
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

			pagedListInfo.appendSqlTail(db, sqlOrder);

		} else {
			sqlOrder.append("");
		}

		//Need to build a base SQL statement for returning records
	
	
		sqlSelect.append("select  delega.*,imp.codice_azienda_regionale,imp.id as id_attivita,case when imp.id>0 then imp.stato else -1 end as stato_impresa "
				+ " from apicoltura_deleghe delega "
				+ " left join apicoltura_imprese imp on imp.codice_fiscale_impresa ilike  delega.codice_fiscale_delegante and imp.trashed_date is null and imp.stato!=4 "
				+ " where  delega.trashed_date is null and 1=1 ");
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);
		
		rs = pst.executeQuery();
		if (pagedListInfo != null) { 	 	
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}
	
	
	
	protected void createFilter(Connection db, StringBuffer sqlFilter) 
	{
		//andAudit( sqlFilter );
		if (sqlFilter == null) 
		{
			sqlFilter = new StringBuffer();
		}
		
		if ( (codice_fiscale_delegante!= null && !"".equals(codice_fiscale_delegante) ))
		{
			sqlFilter.append(" and upper(delega.codice_fiscale_delegante) = upper(?) ");
		}
		
		if (idAsl>0)
		{
			sqlFilter.append(" and delega.id_asl = ? ");
		}
		if ((codice_fiscale_delegato!= null && !"".equals(codice_fiscale_delegato)) && idAsl<=0)
		{
			sqlFilter.append(" and upper(delega.codice_fiscale_delegato) = upper(?) ");
		}
		
		if (enabled==true)
		{
			sqlFilter.append(" and delega.enabled ");
		}
		
	
		
	    
	    
		
	}
	protected int prepareFilter(PreparedStatement pst) throws SQLException 
	{
		int i = 0;
		if ( (codice_fiscale_delegante!= null && !"".equals(codice_fiscale_delegante)))
		{
			pst.setString(++i, codice_fiscale_delegante);
		}
		
		if (idAsl>0)
		{
			pst.setInt(++i, idAsl);
		}
		
		if ((codice_fiscale_delegato!= null && !"".equals(codice_fiscale_delegato)) && idAsl<=0)
		{
			pst.setString(++i, codice_fiscale_delegato);
		}
		
		
		    
		
		return i;
	}
	
	public void buildList(Connection db,SystemStatus systemStatus,ActionContext context) throws SQLException, IndirizzoNotFoundException 
	{
		PreparedStatement pst = null;
		ResultSet rs = queryList(db, pst);
		//GestioneAllegatiUploadDelegaApicoltore gestioneAllegatiDelega = new GestioneAllegatiUploadDelegaApicoltore();
		
		HashMap<Integer,User> hash = new HashMap<Integer,User>();
		while (rs.next()) 
		{
			Delega thisDelega = new Delega();
			thisDelega.buildRecord(rs);
			SoggettoFisico sogg = new SoggettoFisico();
			sogg.setDatiAnagrafici(db,thisDelega.getId_soggetto_fisico_delegante());
			thisDelega.setSoggetto_fisico_delegante(sogg);
			
			if(systemStatus!=null)
			{
				thisDelega.setDelegato(systemStatus.getUser(thisDelega.getId_utente_access_ext_delegato()));
				//Nel caso l'utente delegato non sia piu presente in access(scaduto, disabilitato, ecc...)
				if(thisDelega.getDelegato()==null)
				{
					User u = null;
					if(hash.containsKey(thisDelega.getId_utente_access_ext_delegato()))
						u = hash.get(thisDelega.getId_utente_access_ext_delegato());
					else
						u = new User(db,thisDelega.getId_utente_access_ext_delegato());
					hash.put(thisDelega.getId_utente_access_ext_delegato(), u);
					if(u!=null)
						thisDelega.setDelegato(u);
				}
			}
			thisDelega.setIdAsl(idAsl);
				
			/*if(context!=null)
			{
				gestioneAllegatiDelega.setIdDelega(thisDelega.getId());
				try
				{
				thisDelega.setDelegaAllegta( gestioneAllegatiDelega.listaAllegati(context));
				}
				catch(Exception e)
				{}
			}*/
			this.add(thisDelega);
		}
	}
	
	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastAnchor(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setNextAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setNextAnchor(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSyncType(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSyncType(String arg0) {
		// TODO Auto-generated method stub
		
	}
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}
	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	

}
