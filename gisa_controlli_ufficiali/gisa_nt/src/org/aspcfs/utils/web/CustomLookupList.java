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
package org.aspcfs.utils.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.aspcfs.modules.buffer.base.Buffer;
import org.aspcfs.modules.buffer.base.BufferList;
import org.aspcfs.utils.DatabaseUtils;

import bsh.org.objectweb.asm.Type;

import com.sun.xml.internal.ws.wsdl.writer.document.Types;

import sun.net.www.ParseUtil;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: CustomLookupList.java 12404 2005-08-05 17:37:07Z mrajkowski $
 * @created January 14, 2003
 */
public class CustomLookupList extends LookupList {
	
	Logger logger = Logger.getLogger("MainLogger");

  ArrayList fields = new ArrayList();
  
  private String ragioneSociale, name, cod_ateco, accountNumber,sedeOperativa;
  private int idAsl;
  private String tipi_piani ; 
  private int idControllo;
  private int orgId ;
  private boolean isPrimaCheckList = false ; 		
  public String getRagioneSociale() {
	return ragioneSociale;
}


public String getSedeOperativa() {
	return sedeOperativa;
}


public void setSedeOperativa(String sedeOperativa) {
	this.sedeOperativa = sedeOperativa;
}


public int getOrgId() {
	return orgId;
}


public void setOrgId(int orgId) {
	this.orgId = orgId;
}


public String getTipi_piani() {
	return tipi_piani;
}


public void setTipi_piani(String tipi_piani) {
	this.tipi_piani = tipi_piani;
}


public void setAteco(String cod_ateco) {
	// TODO Auto-generated method stub
	this.cod_ateco = cod_ateco;
}

public void setRagioneSociale(String ragioneSociale) {
	this.ragioneSociale = ragioneSociale;
}

public void setAccountName(String ragioneSociale) {
	this.name = ragioneSociale;
}

public String getAccountName() {
	return name;
}

public void setAccountNumber(String account_number) {
	this.accountNumber = account_number;
}

public String getAccountNumber() {
	return accountNumber;
}

public String getAteco() {
	return cod_ateco;
}


public int getIdAsl() {
	return idAsl;
}


public void setIdAsl(int idAsl) {
	this.idAsl = idAsl;
}

public void setIdAsl(String idAsl) {
	if(idAsl!=null && ! idAsl.equals("") )
	this.idAsl = Integer.parseInt(idAsl);
}


/**
   * Constructor for the CustomLookupList object
   */
  public CustomLookupList() {
    super();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    int items = -1;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT ");
    Iterator i = fields.iterator();
    while (i.hasNext()) {
      sql.append((String) i.next());
      if (i.hasNext()) {
        sql.append(",");
      }
      sql.append(" ");
    }
    //sql.append("FROM " + tableName + " ORDER BY description");
    if(tableName.equals("lookup_codistat")){
    	sql.append("FROM " + tableName + " ORDER BY description");
    }else {
    	sql.append("FROM " + tableName + " ORDER BY codice_ateco ");	
    }
    
    PreparedStatement pst = db.prepareStatement(sql.toString());

    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      CustomLookupElement thisElement = new CustomLookupElement(rs);
      this.add(thisElement);
    }
    rs.close();
    pst.close();
  }
  
  public void buildListControlloDocumentale(Connection db,int idStab) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT qcd.quesito,qcd.competenza_asl , id_quesito,risposta_asl,risposta_stap,id_stabilimento,note_stap,(c.namelast || c.namefirst) as modificato_da_asl,(c.namelast || c.namefirst) as modificato_da_asl ,(c2.namelast || c2.namefirst) as modificato_da_stap " +
	    		   "from quesiti_controllo_documentale qcd left join quesiti_risposte_controllo_documentale qrcd on (qcd.id=qrcd.id_quesito ) " +
	    		   " join quesiti_controllo_documentale_stabilimenti qcds on (qrcd.id_quesiti_controllo_documentale_stabilimenti=qcds.id and qcds.id_stabilimento = ?) " +
	    		   "left join contact c on (c.user_id=qcds.user_id_asl) " +
	    		   "left join contact c2 on (c2.user_id =qcds.user_id_stap) "+
	    		   "  where  qcd.enabled = true ");
	   PreparedStatement pst = db.prepareStatement(sql.toString());
	   pst.setInt(1, idStab);
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  
  
  public void buildListPiani(Connection db,String filtro,int idAsl) throws SQLException {
	    int items = -1;
	
	    PreparedStatement pst = null ;
	    ResultSet rs = queryListPiani(db, pst, filtro,idAsl);
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      thisElement.buildSottopiani(db, rs.getInt("codice_piano"));
	      this.add(thisElement);
	    }
	    rs.close();
	   
	  }
  
  
  public void buildListPianiCu(Connection db,String filtro,int idAsl,ArrayList<Integer> lista_piani_selezionati) throws SQLException {
	    int items = -1;
	   
	    PreparedStatement pst = null ;
	    ResultSet rs = queryListPianiCu(db, pst, filtro, idAsl);
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      thisElement.buildSottopiani(db,rs.getInt("codice_piano"));
	      if( lista_piani_selezionati.contains(Integer.parseInt(thisElement.getValue("codice_piano")+"")))
	      {
	    	  thisElement.setSelezionato(true);
	      }
	      this.add(thisElement);
	    }
	    rs.close();
	   
	  }
  
  public void buildList2(Connection db,int orgId) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT codici.* ");
	 
	    
	    sql.append(	"from lookup_codistat codici " +
	    			"join organization o on ((codici.description=o.cf_correntista or o.codice1 = codici.description or " +
	    			"o.codice2 = codici.description or o.codice3 = codici.description or o.codice4 = codici.description	" +
	    			"or o.codice5 = codici.description or o.codice6 = codici.description or o.codice7 = codici.description " +
	    			"or o.codice8 = codici.description or o.codice9 = codici.description or o.codice10 = codici.description ) "+
	    			"and o.org_id=?) ");
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    pst.setInt(1, orgId);
	    logger.info("LIST "+pst.toString());
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  
  public void buildList_matrici(Connection db,String filtro_descr) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    sql.append("SELECT prove.* ");
	    sql.append(	" from lookup_matrici_labhaccp prove " +
	    			" where enabled = true ");
	    
	    if (filtro_descr != null && ! "".equals(filtro_descr))
	    {
	    	sql.append(" and description ilike '%"+filtro_descr+"%'");
	    }
	    sqlOrder.append(" order by description ");
	    PreparedStatement pst = db.prepareStatement(sql.toString()+sqlOrder.toString());
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  public void buildList_prove(Connection db,String filtro_descr) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    sql.append("SELECT prove.* ");
	    sql.append(	" from lookup_denominazioni_labhaccp prove " +
	    			" where enabled = true ");
	    if (filtro_descr != null && ! "".equals(filtro_descr))
	    {
	    	sql.append(" and description ilike '%"+filtro_descr+"%'");
	    }
	    sqlOrder.append(" order by description ");
	    PreparedStatement pst = db.prepareStatement(sql.toString()+sqlOrder.toString());
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  
 
  
  
  public void buildList_impianti(Connection db,int orgId) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT codici.description as impianto,categorie.description as categoria ");
	 sql.append(	"from lookup_impianto codici join stabilimenti_sottoattivita stab " +
	    			" on (stab.codice_impianto = codici.code) join lookup_categoria categorie on (stab.codice_sezione = categorie.code) where stab.id_stabilimento = ? ");
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    pst.setInt(1, orgId);
	    logger.info("LIST "+pst.toString());
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  public void buildList_impianti_soa(Connection db,int orgId) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT codici.description as impianto,categorie.description as categoria ");
	 sql.append(	"from lookup_impianto_soa codici join soa_sottoattivita stab " +
	    			" on (stab.codice_impianto = codici.code) join lookup_categoria_soa categorie on (stab.codice_sezione = categorie.code) where stab.not_used is null and stab.id_soa = ? ");
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    pst.setInt(1, orgId);
	    logger.info("LIST "+pst.toString());
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }

  
  public void buildListAllerteImprese(Connection db,String id_asl) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT o.name ,oa.addrline1 , oa.city,oa.state ");
	    
	    sql.append("FROM organization o ,organization_address oa where o.org_id = oa.org_id and o.tipologia = 1 and oa.address_type=1 and o.site_id = "+id_asl+" and o.trashed_date is null ");
	    PreparedStatement pst = db.prepareStatement(sql.toString());

	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  public void buildListAllerteImpreseFiltro(Connection db,String id_asl,String filtroDescr) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT o.name ,oa.addrline1 , oa.city,oa.state ");
	    
	    sql.append("FROM organization o ,organization_address oa where o.name ilike '%"+filtroDescr+"%' and o.org_id = oa.org_id and o.tipologia = 1 and oa.address_type=1 and o.site_id = "+id_asl+" and o.trashed_date is null ");
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	  
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  
  
  
  public void buildListSOA(Connection db,String id_asl) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT o.name ,oa.addrline1 , oa.city,oa.state,o.org_id ");
	    
	    if(!id_asl.equals("-1"))
	    {
	       	sql.append("FROM organization o left join organization_address oa  on (o.org_id = oa.org_id and oa.address_type=5) where   o.tipologia = 97  and o.trashed_date is null ORDER BY o.name ");
	    	   }
	    else
	    {
	    	sql.append("FROM organization o left join organization_address oa  on (o.org_id = oa.org_id and oa.address_type=5) where   o.tipologia = 97   and o.trashed_date is null ORDER BY o.name ");
	    }
	    PreparedStatement pst = db.prepareStatement(sql.toString());

	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  public void buildListSOAFiltro(Connection db,String id_asl,String filtro) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT o.name ,oa.addrline1 , oa.city,oa.state,o.org_id ");
	    
	    if(!id_asl.equals("-1"))
	    {
	       	sql.append("FROM organization o left join organization_address oa  on (o.org_id = oa.org_id and oa.address_type=5) where   o.tipologia = 97 and o.name ilike '%"+filtro+"%'  and o.trashed_date is null ORDER BY o.name ");
	    	   }
	    else
	    {
	    	sql.append("FROM organization o left join organization_address oa  on (o.org_id = oa.org_id and oa.address_type=5) where   o.tipologia = 97 and o.name ilike '%"+filtro+"%'  and o.trashed_date is null ORDER BY o.name ");
	    }
	    PreparedStatement pst = db.prepareStatement(sql.toString());

	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  public void buildListAllerteStabilimenti(Connection db,String id_asl) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT o.name ,oa.addrline1 , oa.city,oa.state ");
	    
	    sql.append("FROM organization o left join organization_address oa on (o.org_id = oa.org_id and oa.address_type=1)  where o.tipologia = 3 and   o.site_id ="+id_asl+" and o.trashed_date is null ORDER BY o.name desc");
	    PreparedStatement pst = db.prepareStatement(sql.toString());

	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  public void buildListAllerteStabilimentiFiltro(Connection db,String id_asl,String filtroDescr) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT o.name ,oa.addrline1 , oa.city,oa.state ");
	    
	    sql.append("FROM organization o left join organization_address oa on (o.org_id = oa.org_id and oa.address_type=1)  where o.name ilike '%"+filtroDescr+"%' and o.tipologia = 3 and   o.site_id ="+id_asl+" and o.trashed_date is null ORDER BY o.name desc");
	    PreparedStatement pst = db.prepareStatement(sql.toString());

	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  
  public void buildListAllerte(Connection db,int id_asl) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT distinct "+tableName+".id_allerta,problem ,"+tableName+".ticketid, "+tableName+".data_apertura as assigned_date, misura.description as unitaMisura ");
	    sql.append (", ldd.id as id_ldd, ldd.nome_fornitore as nome_fornitore_ldd, ldd.data_lista as data_lista_ldd, ldd.data_chiusura as data_chiusura_ldd");
	    Iterator i = fields.iterator();
	   
	    if(id_asl != -1)
	    	sql.append(" FROM " + tableName+"  left join lookup_unita_misura_allerta misura on ( ticket.unita_misura = misura.code)  left join allerte_ldd ldd on (ldd.id_allerta = ticket.ticketid and ldd.data_chiusura is null and ldd.enabled) JOIN allerte_asl_coinvolte  ON ((allerte_asl_coinvolte.id_allerta = ticket.ticketid and ldd.id is null) or (ldd.id= allerte_asl_coinvolte.id_ldd))  and allerte_asl_coinvolte.id_asl="+id_asl+" and allerte_asl_coinvolte.data_chiusura is null and allerte_asl_coinvolte.enabled WHERE "+tableName+".TIPOLOGIA=700 and ticket.data_chiusura is null  and trashed_date is null order by assigned_date desc, id_allerta ");
	    else
	    	sql.append(" FROM " + tableName+"  left join lookup_unita_misura_allerta misura on ( ticket.unita_misura = misura.code)  left join allerte_ldd ldd on (ldd.id_allerta = ticket.ticketid and ldd.data_chiusura is null and ldd.enabled)  JOIN allerte_asl_coinvolte  ON ((allerte_asl_coinvolte.id_allerta = ticket.ticketid and ldd.id is null) or (ldd.id= allerte_asl_coinvolte.id_ldd)) and allerte_asl_coinvolte.data_chiusura is null and allerte_asl_coinvolte.enabled WHERE "+tableName+".TIPOLOGIA=700 and trashed_date is null and ticket.data_chiusura is null  order by assigned_date desc, id_allerta ");
	    

	    PreparedStatement pst = db.prepareStatement(sql.toString());

	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  public void buildListBuffer0(Connection db) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append(" select b.*, c.nome as comune, lbs.description as stato from buffer b " +
		        " left join buffer_comuni_coinvolti bc on bc.id_buffer = b.id "+
		        " left join lookup_buffer_stato lbs on lbs.code = b.stato "+
		        " left join comuni1 c on c.id = bc.id_comune ");
	    
	    Iterator i = fields.iterator();    
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    System.out.println("Query: "+pst.toString());
	    
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  public void buildListBuffer(Connection db) throws SQLException {
	  PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    BufferList buffer = new BufferList();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM buffer b where trashed_Date is null and stato=1 ");
	    buffer.createFilter(sqlFilter, db);
	    if (pagedListInfo != null) {
	     
	      pst = db.prepareStatement( sqlCount.toString() + sqlFilter.toString());
	      items = buffer.prepareFilter(pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      // Declare default sort, if unset
	      pagedListInfo.setDefaultSort("data_evento", null);
	      //Determine the offset, based on the filter, for the first record to show
	      
	      //Determine the offset
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } else {
	      sqlOrder.append("ORDER BY data_evento desc NULLS last, codice_buffer desc");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "b.*,lbs.description as stato_desc " +
	        "from buffer b join lookup_buffer_stato lbs on lbs.code = b.stato " +
	        "WHERE b.trashed_date is null and stato=1 ");

	    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    items = buffer.prepareFilter(pst);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
			if (System.getProperty("DEBUG") != null) {
				//System.out.println(pst);
			}
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      //CustomLookupElement thisbuffer = new CustomLookupElement(rs);
	      Buffer thisbuffer = new Buffer(rs);
	      thisbuffer.queryRecordComuni(db);
	      this.add(thisbuffer);
	    }
	    rs.close();
	    pst.close();
	  
	  }

  
  public void buildListUserGisa(Connection db,int id_asl) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT distinct access.user_id as userid,role.description as ruolo, access.username as username ,contact.namefirst as nome,contact.namelast as cognome from access ,role, contact where access.user_id = contact.user_id and role.role_id = access.role_id ");
	    Iterator i = fields.iterator();
	  
	    if(id_asl != 0)
	    	sql.append(" and access.site_id = ?");
	    else
	    	sql.append("and access.site_id is null");
	   
	    
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    if(id_asl != 0)
	    	pst.setInt(1, id_asl);
	   
	    
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  
  public void buildListUserGisaRicerca(Connection db,int id_asl,String nome, String cognome) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT distinct access.user_id as userid,role.description as ruolo, access.username as username ,contact.namefirst as nome,contact.namelast as cognome from access ,role, contact where access.user_id = contact.user_id and role.role_id = access.role_id ");
	    Iterator i = fields.iterator();
	  
	    if(id_asl != 0)
	    	sql.append(" and access.site_id = ?");
	    else
	    	sql.append(" and access.site_id is null");
	    
	    if(nome!= null && !nome.equals(""))
	    {
	    	sql.append( " and contact.namefirst ilike '%"+nome+"%'" );
	    }
	    if(cognome!= null && !cognome.equals(""))
	    {
	    	sql.append( " and contact.namelast ilike '%"+cognome+"%'" );
	    }
	   
	    
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    if(id_asl != 0)
	    	pst.setInt(1, id_asl);
	   
	    
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  
  public void buildListAllerteRicerca(Connection db,int id_asl) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT distinct "+tableName+".id_allerta,descrizionebreveallerta ,"+tableName+".data_apertura as assigned_date,"+tableName+".ticketid ");
	    Iterator i = fields.iterator();
	   /* while (i.hasNext()) {
	      sql.append((String) i.next());
	      if (i.hasNext()) {
	        sql.append(",");
	      }
	      sql.append(" ");
	    }*/
	    if(id_asl != -1)
	    	sql.append("FROM " + tableName+"  JOIN allerte_asl_coinvolte  ON ("+tableName+".ticketid= allerte_asl_coinvolte.id_allerta and allerte_asl_coinvolte.id_asl="+id_asl+" and allerte_asl_coinvolte.data_chiusura is null and allerte_asl_coinvolte.enabled)  WHERE "+tableName+".TIPOLOGIA=700 and "+tableName+".trashed_date is null order by assigned_date desc");
	    else
	    	sql.append("FROM " + tableName+"  JOIN allerte_asl_coinvolte  ON ("+tableName+".ticketid= allerte_asl_coinvolte.id_allerta and allerte_asl_coinvolte.data_chiusura is null and allerte_asl_coinvolte.enabled )  WHERE "+tableName+".TIPOLOGIA=700 and "+tableName+".trashed_date is null order by assigned_date desc");
	    
	 
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    logger.info("Lista Allerte "+pst.toString());
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
	  }
  
  
  public void buildListDia(Connection db,int idAsl) throws SQLException {
	    int items = -1;
	   
	    LookupList IstatList = new LookupList(db, "lookup_codistat");
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT ");
	    Iterator i = fields.iterator();
	    while (i.hasNext()) {
	      sql.append((String) i.next());
	      if (i.hasNext()) {
	        sql.append(",");
	      }
	      sql.append(" ");
	    }
	    if(idAsl!=-1)
	    sql.append("FROM " + tableName+" where tipologia=1 and source=1 and site_id="+idAsl);
	    else
	    	  sql.append("FROM " + tableName+" where tipologia=1 and source=1");
	    PreparedStatement pst = db.prepareStatement(sql.toString());

	    ResultSet rs = pst.executeQuery();
	    CustomLookupElement thisElement=null;
	    while (rs.next()) {
	       thisElement = new CustomLookupElement(rs);
	     
	       
	    	   String cod1=IstatList.getSelectedValueShort(thisElement.getValue("codice1"), db);
	    	   thisElement.addField("cod1", cod1, java.sql.Types.VARCHAR);
	    	   
	    	   String cod2=IstatList.getSelectedValueShort(thisElement.getValue("codice2"), db);
	    	   thisElement.addField("cod2", cod2, java.sql.Types.VARCHAR);
	    	   
	    	   String cod3=IstatList.getSelectedValueShort(thisElement.getValue("codice3"), db);
	    	   thisElement.addField("cod3", cod3, java.sql.Types.VARCHAR);
	    	   
	    	   String cod4=IstatList.getSelectedValueShort(thisElement.getValue("codice4"), db);
	    	   thisElement.addField("cod4", cod4, java.sql.Types.VARCHAR);
	    	   
	    	   String cod5=IstatList.getSelectedValueShort(thisElement.getValue("codice5"), db);
	    	   thisElement.addField("cod5", cod5, java.sql.Types.VARCHAR);
	    	   
	    	   String cod6=IstatList.getSelectedValueShort(thisElement.getValue("codice6"), db);
	    	   thisElement.addField("cod6", cod6, java.sql.Types.VARCHAR);
	    	   
	    	   String cod7=IstatList.getSelectedValueShort(thisElement.getValue("codice7"), db);
	    	   thisElement.addField("cod7", cod7, java.sql.Types.VARCHAR);
	    	   
	    	   String cod8=IstatList.getSelectedValueShort(thisElement.getValue("codice8"), db);
	    	   thisElement.addField("cod8", cod8, java.sql.Types.VARCHAR);
	    	   
	    	   String cod9=IstatList.getSelectedValueShort(thisElement.getValue("codice9"), db);
	    	   thisElement.addField("cod9", cod9, java.sql.Types.VARCHAR);
	    	   
	    	   String cod10=IstatList.getSelectedValueShort(thisElement.getValue("codice10"), db);
	    	   thisElement.addField("cod10", cod10, java.sql.Types.VARCHAR);
	    	   
	    	   
	    	   
	    	   
	    	  int orgi_id= Integer.parseInt(thisElement.getValue("org_id"));
	    	  
	    	  
	    	  String selecAddress="select address_type, addrline1,  " +
        "addrline2, addrline3, addrline4, city, state, country, postalcode, county, latitude, longitude from organization_address where org_id="+orgi_id;
	    	  PreparedStatement pst1=db.prepareStatement(selecAddress);
	    	  ResultSet rs1=pst1.executeQuery();
	    	  while(rs1.next()){
	    		  
	    		  int tipo=rs1.getInt("address_type");
	    		  //rs1.get;
	    		  String addline1=rs1.getString("addrline1");
	    		  String addline2=rs1.getString("addrline2");
	    		  String addline3=rs1.getString("addrline3");
	    		  String addline4=rs1.getString("addrline4");
	    		  String city=rs1.getString("city");
	    		  String state=rs1.getString("state");
	    		  String country=rs1.getString("country");
	    		  String postalcode=rs1.getString("postalcode");
	    		  String county=rs1.getString("county");
	    		  double latitudine=rs1.getDouble("latitude");
	    		  double longitudine=rs1.getDouble("longitude");
	    		  
	    		  
	    		  if(tipo==1){
	    			  
	    			  thisElement.addField("sedeLegaleaddress1", addline1, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegaleaddress2", addline2, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegaleaddress3", addline3, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegaleaddress4", addline4, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegalecity", city, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegalestate", state, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegalecountry", country, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegalepostalcode", postalcode, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegalecounty", county, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegalelatitudine", ""+latitudine, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeLegalelongitudine", ""+longitudine, java.sql.Types.VARCHAR);
	    			  
	    			  
	    		  }
	    		  
if(tipo==5){
	    			  
	    			  thisElement.addField("sedeOperativaaddress1", addline1, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativaaddress2", addline2, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativaaddress3", addline3, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativaaleaddress4", addline4, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativacity", city, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativastate", state, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativacountry", country, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativapostalcode", postalcode, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativacounty", county, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativalatitudine", ""+latitudine, java.sql.Types.VARCHAR);
	    			  thisElement.addField("sedeOperativalongitudine", ""+longitudine, java.sql.Types.VARCHAR);
	    			  
	    			  
	    		  }

if(tipo==6){
	  
	  thisElement.addField("localiaddress1", addline1, java.sql.Types.VARCHAR);
	  thisElement.addField("localiaddress2", addline2, java.sql.Types.VARCHAR);
	  thisElement.addField("localiaddress3", addline3, java.sql.Types.VARCHAR);
	  thisElement.addField("localiaddress4", addline4, java.sql.Types.VARCHAR);
	  thisElement.addField("localicity", city, java.sql.Types.VARCHAR);
	  thisElement.addField("localistate", state, java.sql.Types.VARCHAR);
	  thisElement.addField("localicountry", country, java.sql.Types.VARCHAR);
	  thisElement.addField("localipostalcode", postalcode, java.sql.Types.VARCHAR);
	  thisElement.addField("localicounty", county, java.sql.Types.VARCHAR);
	  thisElement.addField("localilatitudine", ""+latitudine, java.sql.Types.VARCHAR);
	  thisElement.addField("localilongitudine", ""+longitudine, java.sql.Types.VARCHAR);
	  
	  
}

if(tipo==7){
	  
	  thisElement.addField("mobileaddress1", addline1, java.sql.Types.VARCHAR);
	  thisElement.addField("mobileaddress2", addline2, java.sql.Types.VARCHAR);
	  thisElement.addField("mobileaddress3", addline3, java.sql.Types.VARCHAR);
	  thisElement.addField("mobileaddress4", addline4, java.sql.Types.VARCHAR);
	  thisElement.addField("mobilecity", city, java.sql.Types.VARCHAR);
	  thisElement.addField("mobilestate", state, java.sql.Types.VARCHAR);
	  thisElement.addField("mobilecountry", country, java.sql.Types.VARCHAR);
	  thisElement.addField("mobilepostalcode", postalcode, java.sql.Types.VARCHAR);
	  thisElement.addField("mobilecounty", county, java.sql.Types.VARCHAR);
	  thisElement.addField("mobilelatitudine", ""+latitudine, java.sql.Types.VARCHAR);
	  thisElement.addField("mobilelongitudine", ""+longitudine, java.sql.Types.VARCHAR);
	  
	  
}
	    		  
	    	  
	    		  
	    		  
	    	  }
	    	  
	      this.add(thisElement);
	    }
	    
	   
	    rs.close();
	    pst.close();
	  }
  
  
  
  public void buildListAllerteFiltro(Connection db, String nomeCampo, String valoreCampo,String nomeCampo2, String valorecampo2,int id_asl) throws SQLException {
  int items = -1;
  StringBuffer sql = new StringBuffer();
  sql.append("SELECT distinct "+tableName+".id_allerta,problem ,"+tableName+".ticketid ,"+tableName+".assigned_date,misura.description as unitaMisura ");
  sql.append (", ldd.id as id_ldd, ldd.nome_fornitore as nome_fornitore_ldd, ldd.data_lista as data_lista_ldd, ldd.data_chiusura as data_chiusura_ldd ");
  Iterator i = fields.iterator();
 /* while (i.hasNext()) {
    sql.append((String) i.next());
    if (i.hasNext()) {
      sql.append(",");
    }
    sql.append(" ");
  }*/
  if(id_asl != -1)
  	sql.append(" FROM " + tableName+"  JOIN allerte_asl_coinvolte  ON ("+tableName+".ticketid= allerte_asl_coinvolte.id_allerta  and allerte_asl_coinvolte.id_asl="+id_asl+" and allerte_asl_coinvolte.data_chiusura is null and allerte_asl_coinvolte.enabled) left join lookup_unita_misura_allerta misura on ( ticket.unita_misura = misura.code) left join allerte_ldd ldd on (ldd.id_allerta = ticket.ticketid and ldd.data_chiusura is null and ldd.enabled) WHERE "+tableName+".TIPOLOGIA=700 and ticket.data_chiusura is null  and trashed_date is null ");
  else
  	sql.append(" FROM " + tableName+"  JOIN allerte_asl_coinvolte  ON ("+tableName+".ticketid= allerte_asl_coinvolte.id_allerta and  allerte_asl_coinvolte.data_chiusura is null and allerte_asl_coinvolte.enabled) left join lookup_unita_misura_allerta misura on ( ticket.unita_misura = misura.code) left join allerte_ldd ldd on (ldd.id_allerta = ticket.ticketid and ldd.data_chiusura is null and ldd.enabled) WHERE "+tableName+".TIPOLOGIA=700 and trashed_date is null and ticket.data_chiusura is null ");
  
  if(!valoreCampo.equals(""))
  sql.append(" AND "+tableName+"." + nomeCampo + " ILIKE '%" + valoreCampo + "%'");
  
  
  PreparedStatement pst = db.prepareStatement(sql.toString());
  PreparedStatement pstDate = db.prepareStatement("SELECT EXTRACT(YEAR FROM  (select assigned_date from ticket where tipologia = 700 and ticketid=?))");
  ResultSet rs = pst.executeQuery();
  while (rs.next()) {
	  String anno ="";
	  int ticketid=rs.getInt("ticketid");
	  pstDate = db.prepareStatement("SELECT EXTRACT(YEAR FROM  (select assigned_date from ticket where tipologia = 700 and ticketid=?))");
		 
	  pstDate.setInt(1, ticketid);
	  ResultSet rss = pstDate.executeQuery();
	  if(rss.next())
		  anno = ""+rss.getInt(1);
	  
		if(valorecampo2!= null && !valorecampo2.equals("") ){
			if(valorecampo2.equals(anno)){
				 CustomLookupElement thisElement = new CustomLookupElement(rs);
				    this.add(thisElement);
			}
			
		}
		else{
			CustomLookupElement thisElement = new CustomLookupElement(rs);
			this.add(thisElement);
		}
		rss.close();
		}
  rs.close();
  pst.close();
  
  }
  
  //R.M cambiare query.....!
  public void buildListBufferFiltro(Connection db, String nomeCampo, String valoreCampo,String nomeCampo2, String valorecampo2,int id_asl) throws SQLException {
	  int items = -1;
	  StringBuffer sql = new StringBuffer();
	  sql.append("SELECT distinct "+tableName+".id,"+tableName+".descrizione_breve ,"+tableName+".data_evento,"+tableName+".stato as idStato," +
		" lbs.description as stato ");	  
	  Iterator i = fields.iterator();
	  
	  if(!valoreCampo.equals(""))
	  sql.append(" AND "+tableName+"." + nomeCampo + " ILIKE '%" + valoreCampo + "%'");
	  
	  PreparedStatement pst = db.prepareStatement(sql.toString());
	  PreparedStatement pstDate = db.prepareStatement("SELECT EXTRACT(YEAR FROM  (select assigned_date from ticket where tipologia = 700 and ticketid=?))");
	  ResultSet rs = pst.executeQuery();
	  while (rs.next()) {
		  String anno ="";
		  int ticketid=rs.getInt("ticketid");
		  pstDate = db.prepareStatement("SELECT EXTRACT(YEAR FROM  (select assigned_date from ticket where tipologia = 700 and ticketid=?))");
			 
		  pstDate.setInt(1, ticketid);
		  ResultSet rss = pstDate.executeQuery();
		  if(rss.next())
			  anno = ""+rss.getInt(1);
		  
			if(valorecampo2!= null && !valorecampo2.equals("") ){
				if(valorecampo2.equals(anno)){
					 CustomLookupElement thisElement = new CustomLookupElement(rs);
					    this.add(thisElement);
				}
				
			}
			else{
				CustomLookupElement thisElement = new CustomLookupElement(rs);
				this.add(thisElement);
			}
			rss.close();
			}
	  rs.close();
	  pst.close();
	  
	  }
  
  public void buildListAllerteFiltroRicerca(Connection db, String nomeCampo, String valoreCampo,String nomeCampo2, String valorecampo2,String isChiuse,int id_asl) throws SQLException {
	  int items = -1;
	  StringBuffer sql = new StringBuffer();
	  sql.append("SELECT distinct "+tableName+".id_allerta,descrizionebreveallerta ,"+tableName+".ticketid ,"+tableName+".data_apertura as assigned_date ");
	  Iterator i = fields.iterator();
	 /* while (i.hasNext()) {
	    sql.append((String) i.next());
	    if (i.hasNext()) {
	      sql.append(",");
	    }
	    sql.append(" ");
	  }*/
	  if(id_asl != -1){
	  
		  if(isChiuse.equals("si")){
			  sql.append("FROM " + tableName+"  JOIN allerte_asl_coinvolte  ON ("+tableName+".ticketid= allerte_asl_coinvolte.id_allerta and allerte_asl_coinvolte.id_asl="+id_asl+" and allerte_asl_coinvolte.enabled)  WHERE "+tableName+".TIPOLOGIA=700 ");
		  }
		  else{
			  sql.append("FROM " + tableName+"  JOIN allerte_asl_coinvolte  ON ("+tableName+".ticketid= allerte_asl_coinvolte.id_allerta and allerte_asl_coinvolte.id_asl="+id_asl+" and allerte_asl_coinvolte.data_chiusura is null and allerte_asl_coinvolte.enabled)  WHERE "+tableName+".TIPOLOGIA=700 ");
				
			  
		  }
	  
	  }  else{
		  if(isChiuse.equals("si")){
	  	sql.append("FROM " + tableName+"  JOIN allerte_asl_coinvolte  ON ("+tableName+".ticketid= allerte_asl_coinvolte.id_allerta and allerte_asl_coinvolte.enabled)  WHERE "+tableName+".TIPOLOGIA=700 ");
	 
		  }else{
			  sql.append("FROM " + tableName+"  JOIN allerte_asl_coinvolte  ON ("+tableName+".ticketid= allerte_asl_coinvolte.id_allerta and allerte_asl_coinvolte.data_chiusura is null and allerte_asl_coinvolte.enabled)  WHERE "+tableName+".TIPOLOGIA=700 and ticket.data_chiusura is null ");
				 
		  
	  }
		  
	  }
	  if(!valoreCampo.equals(""))
	  sql.append(" AND "+tableName+"." + nomeCampo + " ILIKE '%" + valoreCampo + "%'");
	  sql.append(" and "+tableName+".trashed_date is null order by assigned_date desc");
	  
	  PreparedStatement pst = db.prepareStatement(sql.toString());
	  PreparedStatement pstDate = db.prepareStatement("SELECT EXTRACT(YEAR FROM  (select data_apertura from ticket where tipologia = 700 and ticketid=?))");
	  ResultSet rs = pst.executeQuery();
	  while (rs.next()) {
		  String anno ="";
		  int ticketid=rs.getInt("ticketid");
		  pstDate = db.prepareStatement("SELECT EXTRACT(YEAR FROM  (select data_apertura from ticket where tipologia = 700 and ticketid=?))");
			 
		  pstDate.setInt(1, ticketid);
		  ResultSet rss = pstDate.executeQuery();
		  if(rss.next())
			  anno = ""+rss.getInt(1);
		  
			if(valorecampo2!= null && !valorecampo2.equals("") ){
				if(valorecampo2.equals(anno)){
					 CustomLookupElement thisElement = new CustomLookupElement(rs);
					    this.add(thisElement);
				}
				
			}
			else{
				CustomLookupElement thisElement = new CustomLookupElement(rs);
				this.add(thisElement);
			}
			rss.close();
			}
	  rs.close();
	  pst.close();
	  
	  }
  
public void buildList2(Connection db, String nomeCampo, String valoreCampo,int orgId) throws SQLException {
	  
	  
	  
	  int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT codici.* ");
	 
	    
	    sql.append(	"from lookup_codistat codici " +
	    			"join organization o on ((codici.description=o.cf_correntista or o.codice1 = codici.description or " +
	    			"o.codice2 = codici.description or o.codice3 = codici.description or o.codice4 = codici.description	" +
	    			"or o.codice5 = codici.description or o.codice6 = codici.description or o.codice7 = codici.description " +
	    			"or o.codice8 = codici.description or o.codice9 = codici.description or o.codice10 = codici.description ) "+
	    			"and o.org_id=?) ");
	    sql.append(" WHERE " + nomeCampo + " ILIKE '%" + valoreCampo + "%'");
	    logger.info("Query costruita : "+sql.toString());
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    pst.setInt(1, orgId);
	    
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
  }
  
  //inserito da Francesco: utilizzato per codice istat
  public void buildList(Connection db, String nomeCampo, String valoreCampo) throws SQLException {
	  
	  
	  
	  int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT ");
	    Iterator i = fields.iterator();
	    while (i.hasNext()) {
	      sql.append((String) i.next());
	      if (i.hasNext()) {
	        sql.append(",");
	      }
	      sql.append(" ");
	    }
	    sql.append("FROM " + tableName);
	    sql.append(" WHERE " + nomeCampo + " ILIKE  ?");
	    
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    pst.setString(1, "%"+valoreCampo+"%");
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
  }
  
  
public void buildListCaniControllo(Connection db,int idControlloUfficiale) throws SQLException {
	  
	  
	  
	  int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT asset_id as id_cane,serial_number  as mc from asset " +
	    		" where idControllo=? ");
	  
	   
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    pst.setInt(1, idControlloUfficiale);
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
  }
  
public void buildListPiani(Connection db, String nomeCampo, String valoreCampo) throws SQLException {
	  
	  
	  
	  int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT * ");
	   
	    sql.append("FROM " + tableName);
	    sql.append(" WHERE " + nomeCampo + " ILIKE ? and enabled = true");
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	    pst.setString(1, "%"+valoreCampo+"%");
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
  }
  
  //inserito da Francesco: utilizzato per checklist rischio
  public void buildListByIdField(Connection db, String nomeCampo, int valoreCampo,boolean isPrincipale) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT *  ");
	    
	    sql.append("FROM " + tableName);
	    sql.append(" WHERE " + nomeCampo + " ="+valoreCampo+" and enabled=true ORDER BY level");
	    PreparedStatement pst = db.prepareStatement(sql.toString());
	  
	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      thisElement.setPrincipale(isPrincipale);
	      
	      
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
  }
  
  //inserito da Francesco: utilizzato per checklist rischio
  public void buildListByType(Connection db, int checklistTypeId) throws SQLException {
	    int items = -1;
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT ");
	    Iterator i = fields.iterator();
	    while (i.hasNext()) {
	      sql.append((String) i.next());
	      if (i.hasNext()) {
	        sql.append(",");
	      }
	      sql.append(" ");
	    }
	    sql.append("FROM " + tableName);
	    sql.append(" WHERE checklist_type_id = " + checklistTypeId + " ORDER BY level");
	    PreparedStatement pst = db.prepareStatement(sql.toString());

	    ResultSet rs = pst.executeQuery();
	    while (rs.next()) {
	      CustomLookupElement thisElement = new CustomLookupElement(rs);
	      this.add(thisElement);
	    }
	    rs.close();
	    pst.close();
  }
  

  /**
   * Adds a feature to the Field attribute of the CustomLookupList object
   *
   * @param fieldName The feature to be added to the Field attribute
   */
  public void addField(String fieldName) {
    if (fields == null) {
      fields = new ArrayList();
    }
    fields.add(fieldName);
  }
  
  //inserito da Francesco
  public void addItem(int tmp1, String tmp2, String tmp3) {
    CustomLookupElement thisElement = new CustomLookupElement();
	thisElement.setCodeDescShortdesc(tmp1, tmp2, tmp3);
	if (this.size() > 0) {
	  this.add(0, thisElement);
	} else {
	  this.add(thisElement);
	}
  }
  
  //inserito da Francesco
  public String getHtmlSelectWithShortDescription(String selectName, int defaultKey) {
    return getHtmlSelect(selectName, defaultKey, false);
  }
  
  
  private void createFiltrer(StringBuffer sqlFilter)
  {
	  if(idAsl>-1 && idAsl != 0)
	  {
		  sqlFilter.append(" and asl_rif = "+idAsl+" ");
	  }
	  if(ragioneSociale != null) {
		  if(!ragioneSociale.equals(""))
		  {
			  sqlFilter.append(" and o.ragione_sociale ilike '%"+ragioneSociale.replaceAll("'", "''")+"%' ");
		  }  
	  }
    
  if(name != null && !name.equals(""))
  {
		  sqlFilter.append(" and ragione_sociale ilike '%"+name.replaceAll("'", "''")+"%' ");
		  
	  } 
	  
//	  if(cod_ateco != null && !cod_ateco.equals("") && !cod_ateco.equals("-1") )
//	  {
//		  sqlFilter.append(" and lci.description ilike '%"+cod_ateco+"%' ");
//	  } 
//	  
	  
	  
  }
  
  private void createFiltrerZone(StringBuffer sqlFilter)
  {
	  if(idAsl>-1 && idAsl != 0)
	  {
		  sqlFilter.append(" and o.site_id = "+idAsl);
	  }
	  if(ragioneSociale != null) {
		  if(!ragioneSociale.equals(""))
		  {
			  sqlFilter.append(" and o.name ilike '%"+ragioneSociale+"%' ");
		  }  
	  }
	  
	  if(name != null && !name.equals(""))
	  {
		  sqlFilter.append(" and o.name ilike '%"+name+"%' ");
	  } 
	  
	  if(sedeOperativa != null && !sedeOperativa.equals(""))
	  {
		  sqlFilter.append(" and oa5.city ilike '%"+name+"%' ");
	  } 
	  
	 
	  
	  
	  
  }
  
  private void createFiltrer(StringBuffer sqlFilter, int tipoabusivo)
  {
	  if(idAsl>-1 && idAsl != 0)
	  {
		  sqlFilter.append(" and o.site_id = "+idAsl);
	  }
	  if(ragioneSociale != null) {
		  if(!ragioneSociale.equals(""))
		  {
			  sqlFilter.append(" and o.name ilike '%"+ragioneSociale+"%' ");
		  }  
	  }
	  
	  if(name != null && !name.equals(""))
	  {
		  sqlFilter.append(" and o.name ilike '%"+name+"%' ");
	  }   
	  
  }
  
  
  private void createFiltrerPiani(StringBuffer sqlFilter,String filtro,boolean asl_territoriali,int idAsl)
  {
	  if(filtro != null && !filtro.equals(""))
	  {
		  sqlFilter.append(" and t.description ilike ? ");
	  }
	  
	
	  if (asl_territoriali == true)
	  {
		  if (tipi_piani != null && tipi_piani.equals("10") && idAsl!=-1)
		  {
			  sqlFilter.append(" and t.site_id = "+idAsl);
		  }
	  }
	  if (idAsl != -1)
	  {
		  sqlFilter.append(" and t.code in ( select piano_monitoraggio from cu_programmazioni cup join cu_programmazioni_asl cupa on (cup.id = cupa.id_programmazione) join oia_nodo o on o.id = cupa.id_nodo and o.n_livello=1 where o.id_asl = "+idAsl+" and cu_pianificati>0 or campioni_pianificati >0)");
	  }
	  if(tipi_piani!=null && ! tipi_piani.equals(""))
	  {
		  sqlFilter.append(" and lspm.code ="+tipi_piani);
	  }
	  
  }
  
  private void createFiltrerPianiCu(StringBuffer sqlFilter,String filtro, int idAsl)
  {
	  if(filtro != null && !filtro.equals(""))
	  {
		  sqlFilter.append(" and descrizione_piano ilike ? ");
	  }
	  
	  if (tipi_piani != null && ! tipi_piani.equals("-1"))
	  {
	  sqlFilter.append(" and categoria_piano = "+tipi_piani) ;
	  
	  }
	  
		 if ( idAsl!=-1)
		  {
			  sqlFilter.append(" and (site_id = "+idAsl + " OR site_id is null or site_id = -1)");
			  //sqlFilter.append(" and cupa.id_asl ="+idAsl);
			  
			  
		  }
	  
	 
	  
  }
  
  
  
  
  public ResultSet queryList(Connection db, PreparedStatement pst,String inRegione) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    int tipologia = -1;
		  String filtroImpreseDefault = null;
		  if(inRegione.equalsIgnoreCase("si"))
		  {
			  tipologia = 1;
			  filtroImpreseDefault = "and tipo_dest = 'Es. Commerciale'";
		  }
		  else
		  {
			  if(inRegione.equalsIgnoreCase("no"))
			  {
				  tipologia = 29;
			  }
		  }
		  createFiltrer(sqlFilter);
		  sqlCount.append("SELECT count(*) as recordcount ");
		  sqlCount.append(" FROM " + tableName+" o join organization_address oa on (o.org_id=oa.org_id and oa.address_type = 5 ) ");
		  sqlCount.append(" left join lookup_site_id asl on (o.site_id=asl.code) WHERE tipologia = "+tipologia+" and o.trashed_date is null ");

	    if (pagedListInfo != null) {
	    	
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString()+sqlFilter.toString());
	     // UnionAudit(sqlFilter,db)    
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
	              "AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");         
	          pst.setString(1, pagedListInfo.getCurrentLetter().toLowerCase());
	          rs = pst.executeQuery();
	          if (rs.next()) {
	            int offsetCount = rs.getInt("recordcount");
	            pagedListInfo.setCurrentOffset(offsetCount);
	          }
	          rs.close();
	          pst.close();
	        }
	   
	      //Determine column to sort by
	      pagedListInfo.setDefaultSort("o.name", null);
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	            
	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append("ORDER BY o.name ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	  
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    Iterator i = fields.iterator();
		  while (i.hasNext()) {
			  sqlSelect.append((String) i.next());
			  if (i.hasNext()) {
				  sqlSelect.append(",");
			  }
			  sqlSelect.append(" ");
		  }
		  
		  
		
		  sqlSelect.append(" FROM " + tableName+" o join organization_address oa on (o.org_id=oa.org_id and oa.address_type = 5 ) ");
		  sqlSelect.append(" left join lookup_site_id asl on (o.site_id=asl.code)  WHERE tipologia = "+tipologia+" and trashed_date is null ");
		  if(tipologia == 1)
			  sqlSelect.append(filtroImpreseDefault);
		   
	   
	    pst = db.prepareStatement(
	        sqlSelect.toString()+sqlFilter.toString()+sqlOrder.toString() );
	   
	   
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    logger.info( "\n*********************************************\nSelect per ricerca stabilimenti:\n" 
	    		+ pst.toString() + "\n*********************************************\n" );
	    
	    logger.info("QQ "+pst.toString());
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	      
	    }
	    return rs;
	  }
  
  public ResultSet queryListPiani(Connection db, PreparedStatement pst,String filtro,int idAsl) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    int tipologia = -1;
		  String filtroImpreseDefault = null;
	

		  createFiltrerPiani(sqlFilter,filtro,false,idAsl);
		
		  sqlCount.append(
			        "SELECT COUNT( t.code ) AS recordcount " +
			        "FROM lookup_piano_monitoraggio t  join lookup_sezioni_piani_monitoraggio lspm on (t.id_sezione =  lspm.code) " + 
			        "WHERE t.enabled=true  ");

		 
	    if (pagedListInfo != null) {
	    	
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString()+sqlFilter.toString());
	     
	      if(filtro!=null && ! filtro.equals(""))
	    	  pst.setString(1, "%"+filtro+"%");
	      
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	        pagedListInfo.setItemsPerPage(maxRecords);
	      }
	      rs.close();
	      pst.close();

	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	          pst = db.prepareStatement(
	              sqlCount.toString() );         
	         
	          rs = pst.executeQuery();
	          if (rs.next()) {
	            int offsetCount = rs.getInt("recordcount");
	            pagedListInfo.setCurrentOffset(offsetCount);
	          }
	          rs.close();
	          pst.close();
	        }
	   
	      //Determine column to sort by
	      pagedListInfo.setDefaultSort("lspm.code,t.ordinamento", null);
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	            
	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append("ORDER BY lspm.code,t.ordinamento ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	  
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(" distinct  ");
	    sqlSelect.append(" t.code as codice_piano,t.description as descrizione_piano,t.level as categoria_piano,t.enabled as abilitato,t.site_id , t.data,t.entered,t.modified,t.enteredby,t.modifiedby " +
		        		",lspm.description as sezione , lspm.code as codice_sezione " +
		        		"FROM lookup_piano_monitoraggio t  " +
		        		" join lookup_sezioni_piani_monitoraggio lspm on (t.id_sezione =  lspm.code) " +
			    		"WHERE t.enabled = true  ");
		   
	   
	    pst = db.prepareStatement(
	        sqlSelect.toString()+sqlFilter.toString()+sqlOrder.toString() );
	   
	    logger.info("pst : "+pst) ;
	    if(filtro!=null && ! filtro.equals(""))
	    	  pst.setString(1, "%"+filtro+"%");
	      
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    
	    
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	      
	    }
	    return rs;
	  }
  
  public ResultSet queryListImpreseZone(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlSelectImpresa = new StringBuffer();
	    StringBuffer sqlSelectAbusivo = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlCountImpresa = new StringBuffer();
	    StringBuffer sqlCountAbusivo = new StringBuffer();
	    StringBuffer sqlFilters = new StringBuffer();
	    StringBuffer sqlFiltersAbusivo = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    int tipologia = -1;
		//String filtroImpreseDefault = null;
	    
	    createFiltrerZone(sqlFilters);
	    sqlCount.append(" SELECT COUNT(*) as recordcount FROM "+tableName+" o left join organization_address oa1 on (o.org_id=oa1.org_id and oa1.address_type = 1 ) ");
	    sqlCount.append(" left join organization_address oa5 on (o.org_id=oa5.org_id and oa5.address_type = 5 ) "); 
	    sqlCount.append(" left join lookup_site_id asl on (o.site_id=asl.code) " +
				  		"WHERE tipologia = 15 and o.trashed_date is null " );
			  
	    sqlCount.append(sqlFilters);
			
			
		
		
		
	    if (pagedListInfo != null) {
	    	
	    	
	    	
		      //Get the total number of records matching filter
		      /*pst = db.prepareStatement(
		          sqlCount.toString()+sqlFilters.toString());*/
		      pst = db.prepareStatement(sqlCount.toString());
	    	
		         
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
		              "AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");         
		          pst.setString(1, pagedListInfo.getCurrentLetter().toLowerCase());
		          rs = pst.executeQuery();
		          if (rs.next()) {
		            int offsetCount = rs.getInt("recordcount");
		            pagedListInfo.setCurrentOffset(offsetCount);
		          }
		          rs.close();
		          pst.close();
		        }
		   
		      
		    	  //pagedListInfo.setDefaultSort("name", null);
		    	  pagedListInfo.setColumnToSortBy("name");
		    	  pagedListInfo.appendSqlTail(db, sqlOrder);
		      
		            
		      //Optimize SQL Server Paging
		      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    	
	    }//Fine if
	    
	    else {
	      sqlOrder.append("ORDER BY o.name ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	  
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	   
			  
			
			 /* */
		  
		  
		  
			 
			 //gia' 
	    sqlSelect.append(" alert,name, account_number, tipologia, o.org_id, oa5.city, oa5.addrline1, oa5.state, oa5.postalcode, asl.description as aslDescr FROM " + tableName+" o left join organization_address oa on (o.org_id=oa.org_id and oa.address_type = 1 ) ");
	    sqlSelect.append(" left join organization_address oa5 on (o.org_id=oa5.org_id and oa5.address_type = 5 ) "); 
	    sqlSelect.append(" left join lookup_site_id asl on (o.site_id=asl.code) " +
			  		"WHERE tipologia = 15 and o.trashed_date is null ");
	    sqlSelect.append(sqlFilters);
			
			pst = db.prepareStatement(
			        sqlSelect.toString()+sqlOrder.toString() );
			
		

	   
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    logger.info( "\n*********************************************\nSelect per ricerca delle imprese e degli abusivi:\n" 
	    		+ pst.toString() + "\n*********************************************\n" );
	    
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	      
	    }
	    return rs;
	  }

  public ResultSet queryListImpreseAbusivi(Connection db, PreparedStatement pst,String tipo) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlSelectImpresa = new StringBuffer();
	    StringBuffer sqlSelectAbusivo = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlCountImpresa = new StringBuffer();
	    StringBuffer sqlCountAbusivo = new StringBuffer();
	    StringBuffer sqlFilters = new StringBuffer();
	    StringBuffer sqlFiltersAbusivo = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    int tipologia = -1;
		//String filtroImpreseDefault = null;
	    
	    createFiltrer(sqlFilters);
	    createFiltrer(sqlFiltersAbusivo, 4);
	    
		if(tipo.equalsIgnoreCase("17"))
		{
			  tipologia = 17;
			  sqlCount.append("SELECT sum(recordcount) as recordcount from ( "); 
			  sqlCount.append("SELECT count(*) as recordcount FROM ricerche_anagrafiche_old_materializzata WHERE ( (attivita ilike '%imbarcazioni%' and tipologia_operatore = 999) or tipologia_operatore = 17) ");
			  sqlCount.append(sqlFilters);
			  sqlCount.append(") as conteggio");

		}
		else if(tipo.equalsIgnoreCase("4"))
		{
			 tipologia = 4;
			 sqlCount.append("SELECT count(*) as recordcount ");
			 sqlCount.append(" FROM " + tableName+" o left join organization_address oa on (o.org_id=oa.org_id and oa.address_type = 7 ) "); 
			 sqlCount.append(" left join lookup_site_id asl on (o.site_id=asl.code) " +
				  		"WHERE tipologia = "+tipologia+ " and o.trashed_date is null ");
			 
			 sqlCount.append(sqlFiltersAbusivo);
			 System.out.println("select count: "+ sqlCount.toString());
			
		}
		//sia imprese che operatori
		else {
			
			  sqlCountImpresa.append("SELECT sum(recordcount) as recordcount from ( ");	 
			  sqlCountImpresa.append(" SELECT count(*) as recordcount FROM ricerche_anagrafiche_old_materializzata WHERE ( (attivita ilike '%imbarcazioni%' and tipologia_operatore = 999) or tipologia_operatore = 17) ");
			  sqlCountAbusivo.append("UNION SELECT count(*) as recordcount");
			  sqlCountAbusivo.append(" FROM " + tableName+" o left join organization_address oa on (o.org_id=oa.org_id and oa.address_type = 7 ) "); 
			  sqlCountAbusivo.append(" left join lookup_site_id asl on (o.site_id=asl.code) " +
					  		"WHERE tipologia = 4 and o.trashed_date is null ");
			  sqlCountAbusivo.append(sqlFiltersAbusivo);
			  sqlCount.append(") conteggio");
			
		}
		
		
	    if (pagedListInfo != null) {
	    	
	    	if(sqlFilters != null && tipo.equalsIgnoreCase("17,4")){
	    		
	    		
	    		pst = db.prepareStatement(
	    		          sqlCountImpresa.toString()+sqlCountAbusivo.toString()+sqlCount.toString());
			}
	    	
	    	else {
	    	
		  
		      pst = db.prepareStatement(sqlCount.toString());
	    	}
		         
		      rs = pst.executeQuery();
		      if (rs.next()) {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		        pagedListInfo.setColumnToSortBy("name");
		    	pagedListInfo.appendSqlTail(db, sqlOrder);
		      }
		      rs.close();
		      pst.close();
	
		      //Determine the offset, based on the filter, for the first record to show
//		      if (!pagedListInfo.getCurrentLetter().equals("")) {
//		          pst = db.prepareStatement(
//		               sqlCount.toString() +
//		              "AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");         
//		          pst.setString(1, pagedListInfo.getCurrentLetter().toLowerCase());
//		          rs = pst.executeQuery();
//		          if (rs.next()) {
//		            int offsetCount = rs.getInt("recordcount");
//		            pagedListInfo.setCurrentOffset(offsetCount);
//		          }
//		          rs.close();
//		          pst.close();
//		        }
		   
		      // add comment
		     /* if(!tipo.equalsIgnoreCase("17,4")) {
		    	  //Determine column to sort by
		    	  pagedListInfo.setDefaultSort("o.name", null);
		    	  pagedListInfo.appendSqlTail(db, sqlOrder);
		      }
		      else {
		    	  pagedListInfo.setColumnToSortBy("name");
		    	  pagedListInfo.appendSqlTail(db, sqlOrder);
		      }*/
		            
		     
	    }//Fine if
	    
	    else {
	      sqlOrder.append("ORDER BY o.name ");
	    }

//	    //Need to build a base SQL statement for returning records
//	    if (pagedListInfo != null) {
//	  
//	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
//	    } else {
//	      sqlSelect.append("SELECT ");
//	    }
	    sqlSelect.append("SELECT ");
	    //add comment
//	    Iterator i = fields.iterator();
//		while (i.hasNext() && (tipo.equalsIgnoreCase("17"))) {
//				  sqlSelect.append((String) i.next());
//				  if (i.hasNext()) {
//					  sqlSelect.append(",");
//				  }
//				  sqlSelect.append(" ");
//		}

			  
			
			 /* */
		  
		  
		  if(tipo.equalsIgnoreCase("17"))
		  {
			  
			  StringBuffer sqlImbarcazioni= new StringBuffer();
			  sqlImbarcazioni.append(" select ragione_sociale as name, coalesce(n_reg,n_linea) as account_number, tipologia_operatore as tipologia, riferimento_id as org_id, "
				  		+ " coalesce(comune_leg, comune) as city, coalesce(indirizzo_leg,indirizzo) as addrline1, "
				  		+ " coalesce(provincia_leg,provincia_stab) as state, coalesce(cap_leg, cap_stab) as postalcode, asl as aslDescr FROM "
				  		+ " ricerche_anagrafiche_old_materializzata WHERE (attivita ilike '%imbarcazioni%' and tipologia_operatore = 999) or tipologia_operatore = 17 ");	  
			  sqlImbarcazioni.append(sqlFilters);
			  sqlSelect = sqlImbarcazioni;
			  
			System.out.println("query imbarcazione: "+ sqlSelect.toString());  
		  }
		 else if(tipo.equalsIgnoreCase("4")){
			  
			  sqlSelect.append(" o.*,asl.description as aslDescr FROM " + tableName+" o  join organization_address oa on (o.org_id=oa.org_id and oa.address_type = 7 ) "); 
			  sqlSelect.append(" left join lookup_site_id asl on (o.site_id=asl.code) " +
					  		"WHERE tipologia = "+tipologia+ "and o.trashed_date is null ");
			  
			  sqlSelect.append(sqlFiltersAbusivo);
			  
		 }
		 else {
			 
			  sqlSelectImpresa.append(" ragione_sociale as name, coalesce(n_reg,n_linea) as account_number, tipologia_operatore as tipologia, riferimento_id as org_id, "
			  		+ " coalesce(comune_leg, comune) as city, coalesce(indirizzo_leg,indirizzo) as addrline1, "
			  		+ " coalesce(provincia_leg,provincia_stab) as state, coalesce(cap_leg, cap_stab) as postalcode, asl as aslDescr FROM ricerche_anagrafiche_old_materializzata WHERE (attivita ilike '%imbarcazioni%' and tipologia_operatore = 999) or tipologia_operatore = 17 ");
			  sqlSelectImpresa.append(sqlFilters);
			  sqlSelectAbusivo.append(" UNION SELECT name, account_number, tipologia, o.org_id, oa.city, oa.addrline1, oa.state, oa.postalcode, asl.description as aslDescr  FROM " + tableName+" o  join organization_address oa on (o.org_id=oa.org_id and oa.address_type = 7 ) "); 
			  sqlSelectAbusivo.append(" left join lookup_site_id asl on (o.site_id=asl.code) " +
					  		"WHERE tipologia = 4  and o.trashed_date is null ");
			  
			  sqlSelectAbusivo.append(sqlFiltersAbusivo);
		 }
		 
		if (tipo.equalsIgnoreCase("17,4")) {
			
			pst = db.prepareStatement(
			        sqlSelect.toString()+sqlSelectImpresa.toString()+sqlSelectAbusivo.toString());
		}
		else {
			
			/*pst = db.prepareStatement(
			        sqlSelect.toString()+sqlFilters.toString()+sqlOrder.toString() );
			*/
			pst = db.prepareStatement(
			        sqlSelect.toString()+sqlOrder.toString() );
			
		}

	   
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    logger.info( "\n*********************************************\nSelect per ricerca delle imprese e degli abusivi:\n" 
	    		+ pst.toString() + "\n*********************************************\n" );
	    
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	      
	    }
	    return rs;
	  }

  
  public ResultSet queryListLab(Connection db, PreparedStatement pst,String tipologia) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    
	    createFiltrer(sqlFilter);
	    sqlCount.append("SELECT count(*) as recordcount ");
		sqlCount.append(" FROM " + tableName+" o where tipologia = "+tipologia+" and trashed_date is null ");
		
	    if (pagedListInfo != null) {
	    	
	    	if(sqlFilter != null){
	    		
	    		pst = db.prepareStatement(
	  		    sqlCount.toString()+sqlFilter.toString());
				
			}
	    		         
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
		              " AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");         
		          pst.setString(1, pagedListInfo.getCurrentLetter().toLowerCase());
		          rs = pst.executeQuery();
		          if (rs.next()) {
		            int offsetCount = rs.getInt("recordcount");
		            pagedListInfo.setCurrentOffset(offsetCount);
		          }
		          rs.close();
		          pst.close();
		        }
		   
		     //pagedListInfo.setDefaultSort("name", null);
		      pagedListInfo.setColumnToSortBy("name");
		      pagedListInfo.appendSqlTail(db, sqlOrder);      
		      //Optimize SQL Server Paging
		      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    	
	    }//Fine if
	    
	    else {
	      sqlOrder.append("ORDER BY o.name ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	  
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    Iterator i = fields.iterator();
		  while (i.hasNext()) {
			  sqlSelect.append((String) i.next());
			  if (i.hasNext()) {
				  sqlSelect.append(",");
			  }
			  sqlSelect.append(" ");
		  }
		  
		 sqlSelect.append(" FROM " + tableName+" o WHERE tipologia = "+tipologia+" and o.trashed_date is null ");  
		  
		 pst = db.prepareStatement(
			        sqlSelect.toString()+sqlFilter.toString() +sqlOrder.toString() );

	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    logger.info( "\n*********************************************\nSelect per ricerca dei laboratori:\n" 
	    		+ pst.toString() + "\n*********************************************\n" );
	    
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	      
	    }
	    return rs;
	  }
  
  
  
  public ResultSet queryListPianiCu(Connection db, PreparedStatement pst,String filtro,int idAsl) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    int tipologia = -1;
		  String filtroImpreseDefault = null;
	

		  createFiltrerPianiCu(sqlFilter,filtro,idAsl);
		
		  sqlCount.append(
			        "SELECT COUNT( distinct codice_piano ) AS recordcount " +
			        "FROM view_piani_monitoraggio where 1=1  and id_padre = -1 ");

		   
	    if (pagedListInfo != null) {
	    	
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString()+sqlFilter.toString());
	     
	      if(filtro!=null && ! filtro.equals(""))
	    	  pst.setString(1, "%"+filtro+"%");
	      
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	        pagedListInfo.setItemsPerPage(maxRecords);
	      }
	      rs.close();
	      pst.close();

	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	          pst = db.prepareStatement(
	              sqlCount.toString() );         
	         
	          rs = pst.executeQuery();
	          if (rs.next()) {
	            int offsetCount = rs.getInt("recordcount");
	            pagedListInfo.setCurrentOffset(offsetCount);
	          }
	          rs.close();
	          pst.close();
	        }
	   
	      pagedListInfo.setColumnToSortBy("sezione,ordinamento");
	      //Determine column to sort by
	      pagedListInfo.setDefaultSort("sezione,ordinamento", null);
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	            
	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOPna 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append("ORDER BY sezione,ordinamento ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	  
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT  ");
	    }
	    sqlSelect.append(" * FROM view_piani_monitoraggio  where 1=1   and id_padre = -1 ");
		   
	   
	    pst = db.prepareStatement(
	        sqlSelect.toString()+sqlFilter.toString()+sqlOrder.toString() );
	   
	    logger.info("pst : "+pst) ;
	    if(filtro!=null && ! filtro.equals(""))
	    	  pst.setString(1, "%"+filtro+"%");
	      
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    
	    
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	      
	    }
	    return rs;
	  }
  
  
  
  public ResultSet queryListStab(Connection db, PreparedStatement pst,String inRegione, int id_norma) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    int tipologia = -1;
		  if(inRegione.equalsIgnoreCase("si"))
		  {
			  tipologia = 3;
			
		  }
		  else
		  {
			  if(inRegione.equalsIgnoreCase("no"))
			  {
				  tipologia = 29;
			  }
		  }

		  //createFiltrer(sqlFilter);
		
		  if(idAsl>-1 && idAsl != 0)
		  {
			  sqlFilter.append(" and o.asl_rif = "+idAsl+" ");
		  }
		  if(ragioneSociale != null) {
			  if(!ragioneSociale.equals(""))
			  {
				  sqlFilter.append(" and o.ragione_sociale ilike '%"+ragioneSociale.replaceAll("'", "''")+"%' ");
			  }  
		  }
		  
		  if(name != null && !name.equals(""))
		  {
			  sqlFilter.append(" and o.ragione_sociale ilike '%"+name.replaceAll("'", "''")+"%' ");
			  
		  } 
		  
		 /* if(cod_ateco != null && !cod_ateco.equals("") && !cod_ateco.equals("-1") )
		  {
			  sqlFilter.append(" and lci.description ilike '%"+cod_ateco+"%' ");
		  }*/ 
		
		  
		  
		  sqlCount.append("SELECT count(*) as recordcount ");
		 
		  
		  /*** OLD ***
		 //Per gli stabilimenti address_type=5;
		  sqlCount.append(" FROM " + tableName+" o left join organization_address oa on (o.org_id=oa.org_id and oa.address_type = 5 ) "); 
		  sqlCount.append(" left join lookup_site_id asl on (o.site_id=asl.code) WHERE tipologia = "+tipologia+" and o.trashed_date is null ");
          ************/
	      tableName="ricerche_anagrafiche_old_materializzata ";

		  sqlCount.append(" FROM " + tableName+" o left join lookup_site_id asl on (o.asl_rif=asl.code) WHERE id_norma = "+id_norma+" ");
          
		  
		  
	    if (pagedListInfo != null) {
	    	
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString()+sqlFilter.toString());
	     // UnionAudit(sqlFilter,db)    
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
	              "AND " + DatabaseUtils.toLowerCase(db) + "(o.ragione_sociale) < ? ");         
	          pst.setString(1, pagedListInfo.getCurrentLetter().toLowerCase());
	          rs = pst.executeQuery();
	          if (rs.next()) {
	            int offsetCount = rs.getInt("recordcount");
	            pagedListInfo.setCurrentOffset(offsetCount);
	          }
	          rs.close();
	          pst.close();
	        }
	   
	      //Determine column to sort by
	      pagedListInfo.setDefaultSort("o.ragione_sociale", null);
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
	      sqlSelect.append("SELECT  ");
	    }
	    Iterator i = fields.iterator();
		  while (i.hasNext()) {
			  sqlSelect.append((String) i.next());
			  if (i.hasNext()) {
				  sqlSelect.append(",");
			  }
			  sqlSelect.append(" ");
		  }
		  
		/**** OLD ******** 
		sqlSelect.append(" FROM " + tableName+" o left join organization_address oa on (o.org_id=oa.org_id and oa.address_type = 5 ) ");
		sqlSelect.append(" left join lookup_site_id asl on (o.site_id=asl.code)  WHERE tipologia = "+tipologia+" and trashed_date is null ");
	   *******************/
		  tableName="ricerche_anagrafiche_old_materializzata";
		  sqlSelect.append(" FROM " + tableName+" o left join lookup_site_id asl on (o.asl_rif=asl.code) WHERE id_norma = "+id_norma);
            
		  
	   logger.info("Queryy :" + sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	   
	    pst = db.prepareStatement(
	        sqlSelect.toString()+sqlFilter.toString()+sqlOrder.toString() );
	   
	   
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    logger.info( "\n*****************************************\nSelect per ricerca stabilimenti:\n" 
	    		+ pst.toString() + "\n*********************************************\n" );
	    
	        
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	      
	    }
	    return rs;
	  }
  
  
  
  
  
  public void buildListDestinazioneCarni(Connection db,String inRegione) throws SQLException {
	  PreparedStatement pst = null;
	  
	  ResultSet rs = queryList(db, pst,inRegione);
	  while (rs.next()) {
		  CustomLookupElement thisElement = new CustomLookupElement(rs);
		  this.add(thisElement);
	  }
	  rs.close();
	  
  }
  
  public void buildListDestinazioneCarniStab(Connection db,String inRegione, int id_norma) throws SQLException {
	  PreparedStatement pst = null;
	  
	  //id_norma : 1 = imprese - 5 = stabilimenti
	  
	  ResultSet rs = queryListStab(db, pst,inRegione, id_norma);
	  while (rs.next()) {
		  CustomLookupElement thisElement = new CustomLookupElement(rs);
		  this.add(thisElement);
	  }
	  rs.close();
	  
  }
  
  public void buildListImpreseAbusivi(Connection db,String tipo) throws SQLException {
	  PreparedStatement pst = null;
	  
	  ResultSet rs = queryListImpreseAbusivi(db, pst, tipo);
	  while (rs.next()) {
		  CustomLookupElement thisElement = new CustomLookupElement(rs);
		  this.add(thisElement);
	  }
	  rs.close();
	  
  }
  
  
  public void buildListImpreseZone(Connection db) throws SQLException {
	  PreparedStatement pst = null;
	  
	  ResultSet rs = queryListImpreseZone(db, pst);
	  while (rs.next()) {
		  CustomLookupElement thisElement = new CustomLookupElement(rs);
		  this.add(thisElement);
	  }
	  rs.close();
	  
  }
  
  public void buildListLaboratori(Connection db,String tipo) throws SQLException {
	  PreparedStatement pst = null;
	  
	  ResultSet rs = queryListLab(db, pst, tipo);
	  while (rs.next()) {
		  CustomLookupElement thisElement = new CustomLookupElement(rs);
		  this.add(thisElement);
	  }
	  rs.close();
	  
  }
  
  
  //inserito da Francesco
  public String getHtmlSelectWithShortDescription(String selectName, int defaultKey, boolean disabled) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setMultiple(multiple);
    thisSelect.setDisabled(disabled);
    thisSelect.setJsEvent(jsEvent);
    Iterator i = this.iterator();
    boolean keyFound = false;
    int lookupDefault = defaultKey;
    while (i.hasNext()) {
      CustomLookupElement thisElement = (CustomLookupElement) i.next();
      // Add the item to the list
      boolean enabled = thisElement.getValue("enabled") == "true" ? true : false;
      boolean defaultItem = thisElement.getValue("default_item") == "true" ? true : false;
      String description = thisElement.getValue("description") + " - " + thisElement.getValue("short_description");
      String codeString = thisElement.getValue("code");
      int code = codeString.startsWith("--") ? -1 : Integer.parseInt(codeString);
      if (enabled || !showDisabledFlag) {
        thisSelect.addItem(code, description);
        if (defaultItem) {
          lookupDefault = code;
        }
      } else if (code == defaultKey) {
        thisSelect.addItem(code, description);
      }
      //Handle --None- case
      if (code == defaultKey && defaultKey > -1) {
        keyFound = true;
      }
    }
    if (keyFound) {
      return thisSelect.getHtml(selectName, defaultKey);
    } else {
      return thisSelect.getHtml(selectName, lookupDefault);
    }
  }

  	public void buildListSpeditori(Connection db, String tipologia) {
		// TODO Auto-generated method stub
		 PreparedStatement pst = null;
		 try {
			 ResultSet rs = queryListSpeditore(db, pst,tipologia);
			 while (rs.next()) {
				 CustomLookupElement thisElement = new CustomLookupElement(rs);
				 this.add(thisElement);
			 }
		  
			 rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
	  }
  	
  	
  	public void buildListAziendeZootecniche(Connection db, String tipologia) {
		// TODO Auto-generated method stub
		 PreparedStatement pst = null;
		 try {
			 ResultSet rs = queryListSpeditore(db, pst,tipologia);
			 while (rs.next()) {
				 CustomLookupElement thisElement = new CustomLookupElement(rs);
				 this.add(thisElement);
			 }
		  
			 rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
	  }
  
  	public ResultSet queryListSpeditore(Connection db, PreparedStatement pst,String tipologia) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    
	    createFiltrerSpeditore(sqlFilter);
	    sqlCount.append("SELECT count(*) as recordcount ");
		sqlCount.append(" FROM " + tableName+" o left join organization_address oa on (o.org_id = oa.org_id) left join lookup_site_id asl on (o.site_id = asl.code) and o.site_id != -1  " +
				"where tipologia = "+tipologia+" and trashed_date is null");
		
	    if (pagedListInfo != null) {
	    	
	    	if(sqlFilter != null){
	    		
	    		pst = db.prepareStatement(
	  		    sqlCount.toString()+sqlFilter.toString());
				
			}
	    		         
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
		              "AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");         
		          pst.setString(1, pagedListInfo.getCurrentLetter().toLowerCase());
		          rs = pst.executeQuery();
		          if (rs.next()) {
		            int offsetCount = rs.getInt("recordcount");
		            pagedListInfo.setCurrentOffset(offsetCount);
		          }
		          rs.close();
		          pst.close();
		        }
		   
		      pagedListInfo.setColumnToSortBy("name");
		      pagedListInfo.appendSqlTail(db, sqlOrder);      
		      //Optimize SQL Server Paging
		      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    	
	    }//Fine if
	    
	    else {
	      sqlOrder.append("ORDER BY o.name ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	  
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    Iterator i = fields.iterator();
		  while (i.hasNext()) {
			  sqlSelect.append((String) i.next());
			  if (i.hasNext()) {
				  sqlSelect.append(",");
			  }
			  sqlSelect.append(" ");
		  }
		  
		 sqlSelect.append(" FROM " + tableName+" o left join organization_address oa on (o.org_id = oa.org_id) left join lookup_site_id asl on (o.site_id = asl.code) and o.site_id != -1  " +
		 		"WHERE tipologia = "+tipologia+" and o.trashed_date is null");  
		  
		 pst = db.prepareStatement(
			        sqlSelect.toString()+sqlFilter.toString()+sqlOrder.toString() );

	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    logger.info( "\n*********************************************\nSelect per ricerca speditori:\n" 
	    		+ pst.toString() + "\n*********************************************\n" );
	    
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	      
	    }
	    return rs;
	  }


  	
  
  	
	private void createFiltrerSpeditore(StringBuffer sqlFilter) {
		 if(name != null) {
			  if(!name.equals(""))
			  {
				  sqlFilter.append(" and o.name ilike '%"+name+"%' ");
			  }  
		  }
		  
		  if(accountNumber != null && !accountNumber.equals(""))
		  {
			  sqlFilter.append(" and account_number ilike '%"+accountNumber+"%' ");
		  } 
		  if(orgId>0)
		  {
			  sqlFilter.append(" and o.org_id = "+orgId);
		  }
		   
		
	}
  
  	
  
}

