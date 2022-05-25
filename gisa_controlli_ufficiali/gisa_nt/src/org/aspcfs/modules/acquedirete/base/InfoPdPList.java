/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.acquedirete.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class InfoPdPList extends Vector implements SyncableList {

	/**
	 * 
	 */
	private int id ;
	private int id_controllo ;
	private int org_id_pdp ;
	private String temperatura ;
	private String cloro ;
	private String ore  ;
	private boolean prot_routine ;
	private boolean prot_verifica ;
	private boolean prot_replica_micro;
	private boolean prot_replica_chim;
	private String altro ;

	private boolean prot_radioattivita;
	private boolean prot_ricerca_fitosanitari;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_controllo() {
		return id_controllo;
	}
	public void setId_controllo(int id_controllo) {
		this.id_controllo = id_controllo;
	}
	public int getOrg_id_pdp() {
		return org_id_pdp;
	}
	public void setOrg_id_pdp(int org_id_pdp) {
		this.org_id_pdp = org_id_pdp;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}
	public String getCloro() {
		return cloro;
	}
	public void setCloro(String cloro) {
		this.cloro = cloro;
	}
	public String getOre() {
		return ore;
	}
	public void setOre(String ore) {
		this.ore = ore;
	}
	public boolean isProt_ricerca_fitosanitari() {
		return prot_ricerca_fitosanitari;
	}
	public void setProt_ricerca_fitosanitari(boolean prot_ricerca_fitosanitari) {
		this.prot_ricerca_fitosanitari = prot_ricerca_fitosanitari;
	}

	public boolean isProt_routine() {
		return prot_routine;
	}
	public void setProt_routine(boolean prot_routine) {
		this.prot_routine = prot_routine;
	}

	public boolean isProt_radioattivita() {
		return prot_radioattivita;
	}
	public void setProt_radioattivita(boolean prot_radioattivita) {
		this.prot_radioattivita = prot_radioattivita;
	}

	
	public boolean isProt_verifica() {
		return prot_verifica;
	}
	public void setProt_verifica(boolean prot_verifica) {
		this.prot_verifica = prot_verifica;
	}
	public boolean isProt_replica_micro() {
		return prot_replica_micro;
	}
	public void setProt_replica_micro(boolean prot_replica_micro) {
		this.prot_replica_micro = prot_replica_micro;
	}
	public boolean isProt_replica_chim() {
		return prot_replica_chim;
	}
	public void setProt_replica_chim(boolean prot_replica_chim) {
		this.prot_replica_chim = prot_replica_chim;
	}
	public String getAltro() {
		return altro;
	}
	public void setAltro(String altro) {
		this.altro = altro;
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
	

	  protected void createFilter(Connection db, StringBuffer sqlFilter) {
		    if (sqlFilter == null) {
		      sqlFilter = new StringBuffer();
		    }
		    
		    if (id_controllo>0)
		    {
		    	sqlFilter.append(" and id_controllo=? ");
		    }
		    
	  }
	  


	  protected int prepareFilter(PreparedStatement pst) throws SQLException {
		    int i = 0;
		  
		    if (id_controllo>0)
		    {
		    		pst.setInt(++i,id_controllo);

		    }
		    
		    return i ;
		    
	  }
	
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	   
	    createFilter(db, sqlFilter);
	    sqlSelect.append("SELECT * from controlli_punti_di_prelievo_acque_rete where 1=1 and enabled = true ");

	   
	   //createFilter(db, sqlFilter);
	   pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	   items = prepareFilter(pst);

	    
	    rs = DatabaseUtils.executeQuery(db, pst);
	    return rs;
	  }
  
	
	  public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    ResultSet rs = queryList(db, pst);
		    while (rs.next()) {
		      InfoPdP thisPdp = new InfoPdP(rs);
		      thisPdp.setOrgdDetails(new Organization(db, thisPdp.getOrg_id_pdp()));
		      this.add(thisPdp);
		    }
		    rs.close();
		    if (pst != null) {
		      pst.close();
		    }
		  }
	  
	  public void buildListFromRequest(ActionContext context)
	  {
		  String[] orgIdPdpList = context.getRequest().getParameterValues("pdp");
		  
		  for ( int i = 0 ; i<orgIdPdpList.length; i++ )
		  {
			  int orgIdCurr = Integer.parseInt(orgIdPdpList[i]);
			  InfoPdP pdp = new InfoPdP();
			  pdp.setOrg_id_pdp(orgIdCurr);
			  pdp.setTemperatura(context.getParameter("field1_"+orgIdCurr));
			  pdp.setCloro(context.getParameter("field2_"+orgIdCurr));
			  pdp.setOre(context.getParameter("field3_"+orgIdCurr));
			  pdp.setProt_routine((context.getParameter("field4_"+orgIdCurr) != null && context.getParameter("field4_"+orgIdCurr).equalsIgnoreCase("on"))? true : false);
			  pdp.setProt_verifica((context.getParameter("field5_"+orgIdCurr)!=null && context.getParameter("field5_"+orgIdCurr).equalsIgnoreCase("on"))? true : false);
			  pdp.setProt_replica_micro((context.getParameter("field6_"+orgIdCurr) != null && context.getParameter("field6_"+orgIdCurr).equalsIgnoreCase("on"))? true : false);
			  pdp.setProt_replica_chim((context.getParameter("field7_"+orgIdCurr) != null && context.getParameter("field7_"+orgIdCurr).equalsIgnoreCase("on"))? true : false);
			  pdp.setAltro(context.getParameter("field8_"+orgIdCurr));
			  
			  pdp.setProt_radioattivita((context.getParameter("field10_"+orgIdCurr) != null && context.getParameter("field10_"+orgIdCurr).equalsIgnoreCase("on"))? true : false);
			  pdp.setProt_ricerca_fitosanitari((context.getParameter("field11_"+orgIdCurr) != null && context.getParameter("field11_"+orgIdCurr).equalsIgnoreCase("on"))? true : false);
			  
			  this.add(pdp);
			  
			  
		  }
	  }
	
	
}
