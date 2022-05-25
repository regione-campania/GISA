/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu_ext.base;

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
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @created    August 30, 2001
 * @version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class OperatoreList extends Vector implements SyncableList {

  private static Logger log = Logger.getLogger(org.aspcfs.modules.opu_ext.base.OperatoreList.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    } 
  }

  

  public final static String tableName = "organization";
  public final static String uniqueField = "org_id";
  protected Boolean minerOnly = null;
  protected int typeId = 0;
  protected int stageId = -1;
  boolean sindacoFR = false;
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



 protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected PagedListInfo pagedListInfo = null;
  private String ragioneSociale;
  private String codiceFiscale;
  private String partIva;
  private int idAsl = -1;
  /*campi utili per la ricerca su stabilimenti*/
  
  private Integer[] idLineaProduttiva ;
  private String[] descrizioneLineaProduttiva ;
	
  private int idOperatore;
	private int siteId;
	
	
	
	public String[] getDescrizioneLineaProduttiva() {
		return descrizioneLineaProduttiva;
	}

	public void setDescrizioneLineaProduttiva(String[] descrizioneLineaProduttiva) {
		this.descrizioneLineaProduttiva = descrizioneLineaProduttiva;
	}

	public Integer[] getIdLineaProduttiva() {
		return idLineaProduttiva;
	}

	public void setIdLineaProduttiva(Integer[] idLineaProduttiva) {
		this.idLineaProduttiva = idLineaProduttiva;
	}
	
	public void setIdLineaProduttiva(String[] idLineaProduttiva) {
		
		this.idLineaProduttiva = new Integer[idLineaProduttiva.length];
		for(int i = 0 ; i<idLineaProduttiva.length; i++)
		{
			this.idLineaProduttiva[i] = Integer.parseInt(idLineaProduttiva[i]) ;
		}
	}

	
	


	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public void setIdAsl(String idAsl) {
		this.idAsl = new Integer(idAsl).intValue();
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}
	
	
	public void setIdOperatore(String idOperatore) {
		this.idOperatore = new Integer(idOperatore).intValue();
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



	private String telefono1;
	private String telefono2;
	private int owner;
	private int enteredBy;
	private String email;
	private String fax;
	private String note;
	private int modifiedBy;
	private int idComuneStabilimento ;
	
	
  
  
  public int getIdComuneStabilimento() {
		return idComuneStabilimento;
	}

	public void setIdComuneStabilimento(int idComuneStabilimento) {
		this.idComuneStabilimento = idComuneStabilimento;
	}
	
	public void setIdComuneStabilimento(String idComuneStabilimento) {
		if(idComuneStabilimento!=null && !"".equals(idComuneStabilimento))
		this.idComuneStabilimento = Integer.parseInt(idComuneStabilimento);
	}
	
	
	


public static Logger getLog() {
	return log;
}

public static void setLog(Logger log) {
	OperatoreList.log = log;
}

public String getRagioneSociale() {
	return ragioneSociale;
}

public void setRagioneSociale(String ragioneSociale) {
	this.ragioneSociale = ragioneSociale;
}

public String getPartIva() {
	return partIva;
}

public void setPartIva(String partIva) {
	this.partIva = partIva;
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
  
  
  

  
  public boolean isHasAlertDate() {
	return hasAlertDate;
}

public void setHasAlertDate(boolean hasAlertDate) {
	this.hasAlertDate = hasAlertDate;
}

public String getCodiceFiscale() {
	  return codiceFiscale;
  }
  
  public void setCodiceFiscale (String codiceFiscale) {
	  this.codiceFiscale=codiceFiscale;
  }
  
  /**
   *  Constructor for the OrganizationList object, creates an empty list. After
   *  setting parameters, call the build method.
   *
   * @since    1.1
   */
  public OperatoreList() { }



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





  
  


  
  
  public void buildList(Connection db) throws SQLException, IndirizzoNotFoundException {
	    PreparedStatement pst = null;

	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) {
	      Operatore thisOperatore = this.getObject(rs);
	      
	      Integer[] lineaProduttiva = new Integer[1];
	      
	      lineaProduttiva[0] = rs.getInt("id_linea_produttiva");
	      
	      thisOperatore.getListaStabilimenti().setIdOperatore(thisOperatore.getIdOperatore());
	      thisOperatore.getListaStabilimenti().setIdLineaProduttiva(lineaProduttiva);
	      thisOperatore.getListaStabilimenti().buildList(db);
	        this.add(thisOperatore);
	     
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
  public Operatore getObject(ResultSet rs) throws SQLException {
	  Operatore thisOperatore = new Operatore(rs);
    return thisOperatore;
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
    		"Select COUNT( * ) AS recordcount " +
    				"from opu_operatore o " +
    				"left join opu_rel_operatore_soggetto_fisico oss on (o.id = oss.id_operatore) " +
					"left join opu_soggetto_fisico f on (oss.id_soggetto_fisico = f.id) " +
	"left join opu_stabilimento s on s.id_operatore = o.id " +
	"left join opu_indirizzo i on i.id = s.id_indirizzo " );
		sqlCount.append(" left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_stabilimento = s.id ");

           sqlCount.append(" WHERE o.id >= 0 and rslp.trashed_date is null ");

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
            "AND " + DatabaseUtils.toLowerCase(db) + "(o.ragione_sociale) < ? ");
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
      pagedListInfo.setColumnToSortBy("o.ragione_sociale");
      pagedListInfo.appendSqlTail(db, sqlOrder);
            
      //Optimize SQL Server Paging
      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
    } else {
      sqlOrder.append("ORDER BY o.ragione_sociale ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
     
    sqlSelect.append(
    		" distinct o.*, o.id as idOperatore, f.*,  f.id as id_soggetto, rslp.id_linea_produttiva as id_linea_produttiva  " +
    				"from opu_operatore o " +
    				"left join opu_rel_operatore_soggetto_fisico oss on (o.id = oss.id_operatore) " +
    				"left join opu_soggetto_fisico f on (oss.id_soggetto_fisico = f.id) " +
    				"left join opu_stabilimento s on s.id_operatore = o.id " +
    				"left join opu_indirizzo i on i.id = s.id_indirizzo" );
    
	sqlSelect.append(" left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_stabilimento = s.id ");

   
    	 	sqlSelect.append("WHERE o.id >= 0 and rslp.trashed_date is null ");
    	 	//sqlSelect.append("and oss.stato_ruolo = 1 ");
    	 	
  
      
  
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    
    
   
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    
    
    rs = pst.executeQuery() ;
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
    
    
    if (ragioneSociale != null && ! "".equals(ragioneSociale))
    {
    	sqlFilter.append("and o.ragione_sociale ILIKE ? ");  		
    		
    	 }
    if (codiceFiscale != null && !"".equals(codiceFiscale)){
    	sqlFilter.append("and o.codice_fiscale_impresa = ? " );
    }
    
    
    if (partIva != null && !"".equals(partIva)){
    	sqlFilter.append("and o.partita_iva = ? " );
    }
    
    
 
    
    if (idComuneStabilimento > 0)
    {
    	sqlFilter.append("AND  i.comune = ? ");
    	
    }
    
    if (idLineaProduttiva != null && idLineaProduttiva.length>0)
    {
    	
    	ArrayList<Integer> temp = new ArrayList(Arrays.asList(idLineaProduttiva)); 
    	
    	if (temp.contains(LineaProduttiva.IdAggregazioneOperatoreCommerciale)){
    		temp.add(temp.size()-1, LineaProduttiva.idAggregazioneImportatore); //Importatore
    		
    	}
    	
    	


    	sqlFilter.append("AND  rslp.id_linea_produttiva in ( ");
    	for (int i = 0 ; i<temp.size()-1 ; i++)
    	{
    		if (temp.get(i) == 7) //SindacoFR, non mi serve asl giu
    			sindacoFR = true;
    		
    		if(! temp.get(i).equals("-1"))
    			sqlFilter.append(temp.get(i)+",");
    	}
    	if(! temp.get(temp.size()-1).equals("-1"))
    		sqlFilter.append(idLineaProduttiva[idLineaProduttiva.length-1]+") ");
    	else
    		sqlFilter.append(") ");
    	
    	
    }
    
    
    
    
    if (idAsl > -1 && !sindacoFR){
    	sqlFilter.append("AND s.id_asl = ?");
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
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    
    
    
    if (ragioneSociale != null && ! "".equals(ragioneSociale))
    {
    	pst.setString(++i, ragioneSociale);	
    		
    	 }
    if (codiceFiscale != null && !"".equals(codiceFiscale)){
    	pst.setString(++i, codiceFiscale);	
    }
    
    
    if (partIva != null && !"".equals(partIva)){
    	pst.setString(++i, partIva);	
    }
    
    

    if (idComuneStabilimento > 0)
    {
    	pst.setInt(++i, idComuneStabilimento);
    	
    }
    
    
    if (idAsl > -1 && !sindacoFR){
    	pst.setInt(++i, idAsl);
    }
    
    return i;
  }
  
  

  

  /**
   *  Gets the orgById attribute of the OrganizationList object This method
   *  assumes that the value of id is > 0
   *
   * @param  id  The value of id is always greater than 0.
   * @return     returns the matched organization or returns null
   */
  public Operatore getOrgById(int id) {
	  Operatore result = null;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
    	Operatore org = (Operatore) iter.next();
      if (org.getIdOperatore() == id) {
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

