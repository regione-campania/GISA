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
package org.aspcfs.modules.allerte_new.base;

import java.awt.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;

import org.aspcfs.modules.allerte_new.base.Ticket;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.daffodilwoods.daffodildb.server.sql99.expression.datetimevalueexpression.timestampaddfunction;

public class TicketList extends org.aspcfs.modules.troubletickets.base.TicketList
{
	protected int source = -1;
	protected int tipologia = -1;
	protected String tipo_richiesta = null;
	protected int progressivoAllerta = -1;
	protected int tipoAlimento = -1;
	protected int origine = -1;
	protected int alimentoInteressato = -1;
	protected int nonConformita = -1;
	protected int altreIrregolarita = -1;
	protected java.sql.Timestamp dataApertura = null;
	protected java.sql.Timestamp dataApertura2 = null;
	protected int stato = -1;
	protected int statoAslCorrente = -1;
	protected String codiceAllerta= "";
	private boolean includiChiuse = true;
	
	
	public boolean isIncludiChiuse() {
		return includiChiuse;
	}

	public void setIncludiChiuse(boolean includiChiuse) {
		this.includiChiuse = includiChiuse;
	}

	public String getCodiceAllerta() {
		return codiceAllerta;
	}

	public void setCodiceAllerta(String codiceAllerta) {
		this.codiceAllerta = codiceAllerta;
	}

	public void setStato(String stato) {
		this.stato = Integer.parseInt(stato);
	}
	
	 public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public void setDataApertura2(String tmp) {
		    this.dataApertura2 = DatabaseUtils.parseDateToTimestamp(tmp);
		  }
	
	public java.sql.Timestamp getDataApertura2() {
		return dataApertura2;
	}


	public void setDataApertura2(java.sql.Timestamp dataApertura2) {
		this.dataApertura2 = dataApertura2;
	}
	
	 public void setDataApertura(String tmp) {
		    this.dataApertura = DatabaseUtils.parseDateToTimestamp(tmp);
		  }
	
	public java.sql.Timestamp getDataApertura() {
		return dataApertura;
	}


	public void setDataApertura(java.sql.Timestamp dataApertura) {
		this.dataApertura = dataApertura;
	}


	public int getOrigine() {
		return origine;
	}


	public void setOrigine(String origine) {
		this.origine = Integer.parseInt(origine);
	}
	
	public void setOrigine(int origine) {
		this.origine = origine;
	}


	public int getAlimentoInteressato() {
		return alimentoInteressato;
	}


	public void setAlimentoInteressato(int alimentoInteressato) {
		this.alimentoInteressato = alimentoInteressato;
	}
	
	public void setAlimentoInteressato(String alimentoInteressato) {
		this.alimentoInteressato = Integer.parseInt(alimentoInteressato);
	}


	public int getNonConformita() {
		return nonConformita;
	}


	public void setNonConformita(int nonConformita) {
		this.nonConformita = nonConformita;
	}
	
	public void setNonConformita(String nonConformita) {
		this.nonConformita = Integer.parseInt(nonConformita);
	}


	public int getAltreIrregolarita() {
		return altreIrregolarita;
	}


	public void setAltreIrregolarita(int altreIrregolarita) {
		this.altreIrregolarita = altreIrregolarita;
	}

	public void setAltreIrregolarita(String altreIrregolarita) {
		this.altreIrregolarita = Integer.parseInt(altreIrregolarita);
	}

	public void setTipoAlimento(String tipoAlimento) {
		this.tipoAlimento = Integer.parseInt( tipoAlimento );
	}

	
	public int getTipoAlimento() {
		return tipoAlimento;
	}


	public void setTipoAlimento(int tipoAlimento) {
		this.tipoAlimento = tipoAlimento;
	}


	public void setProgressivoAllerta(String progressivoAllerta) {
		this.progressivoAllerta = Integer.parseInt( progressivoAllerta );
	}


	public int getProgressivoAllerta() {
		return progressivoAllerta;
	}

	public void setProgressivoAllerta(int progressivoAllerta) {
		this.progressivoAllerta = progressivoAllerta;
	}

	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
	
	public void setSource(String source) {
		this.source = Integer.parseInt( source );
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public void setTipologia(String tipologia) {
		this.tipologia = Integer.parseInt( tipologia );
	}
	
	public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    ResultSet rs = null;
		    int items = -1;
		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlFilter = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();
		    //Need to build a base SQL statement for counting records
		    sqlCount.append(
		        "SELECT COUNT( distinct ticketid ) AS recordcount " +
		        "FROM ticket t " +
		        "JOIN allerte_asl_coinvolte ac ON (t.ticketid = ac.id_allerta) " +
		        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
		        "LEFT JOIn analiti_allerte al on al.id_allerta = t.ticketid "+
		        "WHERE t.ticketid > 0 AND t.tipologia = 700 AND ac.enabled ");
		    createFilter(sqlFilter, db);
		    if (pagedListInfo != null) {
		    	
		    	if(includiChiuse==false)
		    	{
		    		sqlFilter.append(" and t.data_chiusura is null and t.resolution_date is null ");
		    	}
		      //Get the total number of records matching filter
		      pst = db.prepareStatement(
		          sqlCount.toString() +
		          sqlFilter.toString());
		      items = prepareFilter(pst);
		     
		      rs = pst.executeQuery();
		      if (rs.next()) {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		        pagedListInfo.setItemsPerPage(maxRecords);
		      }
		      rs.close();
		      pst.close();
		      // Declare default sort, if unset
		      pagedListInfo.setDefaultSort("t.data_apertura", "desc");
		      //Determine the offset, based on the filter, for the first record to show
		      if (pagedListInfo.getMode() == PagedListInfo.DETAILS_VIEW && id > 0) {
		        String direction = null;
		        if ("desc".equalsIgnoreCase(pagedListInfo.getSortOrder())) {
		          direction = ">";
		        } else {
		          direction = "<";
		        }
		        String sqlSubCount =
		            "AND " +
		            (pagedListInfo.getColumnToSortBy().equals("t.problem") ? DatabaseUtils.convertToVarChar(
		            db, pagedListInfo.getColumnToSortBy()) : pagedListInfo.getColumnToSortBy()) + " " +
		            direction + " " +
		            "(SELECT " + (pagedListInfo.getColumnToSortBy().equals(
		            "t.problem") ? DatabaseUtils.convertToVarChar(
		            db, pagedListInfo.getColumnToSortBy()) : pagedListInfo.getColumnToSortBy()) + " " +
		            "FROM ticket t WHERE ticketid = ?) ";
		        pst = db.prepareStatement(
		            sqlCount.toString() +
		            sqlFilter.toString() +
		            sqlSubCount);
		        items = prepareFilter(pst);
		        pst.setInt(++items, id);
		        rs = pst.executeQuery();
		        if (rs.next()) {
		          int offsetCount = rs.getInt("recordcount");
		          pagedListInfo.setCurrentOffset(offsetCount);
		        }
		        rs.close();
		        pst.close();
		      }
		      //Determine the offset
		      pagedListInfo.appendSqlTail(db, sqlOrder);
		    } else {
		      sqlOrder.append("ORDER BY t.data_chiusura desc ,t.assigned_date asc ");
		    }

		    //Need to build a base SQL statement for returning records
		    if (pagedListInfo != null) {
		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		    } else {
		      sqlSelect.append("SELECT ");
		    }
		    sqlSelect.append(" distinct  ");
		    sqlSelect.append(
		        "t.*, " +
		        "al.note,al.tipo_esame, " +		
		        "o.name AS orgname, " +
		        "o.enabled AS orgenabled, " +
		        "o.site_id AS orgsiteid" +
		        " FROM ticket t " +
			    "JOIN allerte_asl_coinvolte ac ON (t.ticketid = ac.id_allerta) "  +
		        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
		        "LEFT JOIn analiti_allerte al on al.id_allerta = t.ticketid "+
		        "WHERE t.ticketid > 0 AND t.tipologia = 700 " +
		        ( ( siteId != -1 ) ? ( " AND ac.enabled " ) : ("") ) );
		    pst = db.prepareStatement(
		        sqlSelect.toString() + sqlFilter.toString() +sqlOrder.toString() );
		    
		    items = prepareFilter(pst);
		    

		  
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, pst);
		    }
				
		    rs = pst.executeQuery();
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, rs);
		    }
		    while (rs.next()) {
		      Ticket thisTicket = new Ticket(rs);
		      thisTicket.setAsl_coinvolte( AslCoinvolte.getAslConvolte( thisTicket.getId(),"",false, db ) );
		      
		      this.add(thisTicket);
		    }
		    rs.close();
		    pst.close();
		    //Build resources
		    Iterator i = this.iterator();
		    while (i.hasNext()) {
		      Ticket thisTicket = (Ticket) i.next();
		   
		    }
		  }
	
	
	
	public void buildListAllerteCancellate(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	        "SELECT COUNT( distinct ticketid ) AS recordcount " +
	        "FROM ticket t " +
	        "WHERE t.ticketid > 0 AND t.tipologia = 700 and  (t.id_allerta ilike  ( select 'E/%/' || date_part('years',current_date)) or t.id_allerta ilike  ( select 'U/%/' || date_part('years',current_date)) )  ");
	  
	    if (pagedListInfo != null) {
	    	
	    	
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() );
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      // Declare default sort, if unset
	      pagedListInfo.setColumnToSortBy("t.entered");
	      //Determine the offset, based on the filter, for the first record to show
	      if (pagedListInfo.getMode() == PagedListInfo.DETAILS_VIEW && id > 0) {
	        String direction = null;
	        if ("desc".equalsIgnoreCase(pagedListInfo.getSortOrder())) {
	          direction = ">";
	        } else {
	          direction = "<";
	        }
	       
	        pst = db.prepareStatement(
	            sqlCount.toString());
	      
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }
	      //Determine the offset
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } else {
	      sqlOrder.append("ORDER BY role_id,t.id_allerta asc");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(" distinct  ");
	    sqlSelect.append("t.ticketid,t.id_allerta,t.descrizionebreveallerta,c.namefirst || c.namelast as inserita_da ," +
	    		"t.entered,c2.namefirst || c2.namelast as cancellata_da , t.trashed_date,t.motivo_cancellazione_allerta," +
	    		" case when t.trashed_date is not null then '<font color=gray>Cancellata</font>'::text "+
	    		"WHEN t.data_chiusura is not null or t.resolution_date is not null THEN '<font color=red>Inattiva</font>'::text"+
	    		"  ELSE '<font color =green>Aperta</font>'::text END AS stato "+
	        "FROM ticket t  join access on (tipologia = 700 and t.enteredby = user_id ) " +
	        " left join contact c on (t.enteredby = c.user_id)"+
	        " left join contact c2 on (t.modifiedby = c2.user_id)"+
		    "WHERE t.ticketid > 0 AND t.tipologia = 700 and  (t.id_allerta ilike  ( select 'E/%/' || date_part('years',current_date)) or t.id_allerta ilike  ( select 'U/%/' || date_part('years',current_date)) )");
	    pst = db.prepareStatement(
	        sqlSelect.toString()  +sqlOrder.toString() );
	 
	  
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	   
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      Ticket thisTicket = new Ticket();
	      int id = rs.getInt("ticketid");
	      String codice_allerta = rs.getString("id_allerta");
	      String descrizione = rs.getString("descrizionebreveallerta");
	      String inserita_da = rs.getString("inserita_da");
	      Timestamp data_inserimento = rs.getTimestamp("entered");
	      String cancellata_da = rs.getString("cancellata_da");
	      String stato_allerta = rs.getString("stato");
	      Timestamp data_cancellazione = rs.getTimestamp("trashed_date");
	      String motivo = rs.getString("motivo_cancellazione_allerta");
	      thisTicket.setId(id);
	      thisTicket.setIdAllerta(codice_allerta);
	      thisTicket.setDescrizioneBreve(descrizione);
	      thisTicket.setInserita_da(inserita_da);
	      thisTicket.setEntered(data_inserimento);
	      thisTicket.setModificata_da(cancellata_da);
	      thisTicket.setTrashedDate(data_cancellazione);
	      thisTicket.setMotivo_cancellazione_allerta(motivo);
	      thisTicket.setAsl_coinvolte( AslCoinvolte.getAslConvolte( thisTicket.getId(),"",false, db ) );
	      thisTicket.setNoteAlimenti(stato_allerta);
	      this.add(thisTicket);
	    }
	    rs.close();
	    pst.close();
	 
	  }
	
	
	public Hashtable costruisciStorico(Connection db,int site,int ruolo) throws SQLException, ParseException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    Calendar cal = new GregorianCalendar(); 
	    int anno = cal.get(Calendar.YEAR); 
	    String inizio = "01/01/"+anno;
	    String fine = "31/12/"+anno;
	    SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
	    Timestamp iniz = new java.sql.Timestamp( sdf.parse(inizio).getTime() );
	   /*
	    iniz.setYear(anno);
	    iniz.setDate(01);
	    iniz.setMonth(01);
	    */
	    Timestamp fin = new java.sql.Timestamp( sdf.parse(fine).getTime()   );
	  /*
	    fin.setYear(anno);
	    fin.setDate(31);
	    fin.setMonth(12);
      */
	    //Need to build a base SQL statement for counting records
	    if((ruolo >=1) && (ruolo!=32))   //non sono amministratore
	    {
	    	sqlCount.append(


	    			"SELECT lo.description as origine,lai.description as alimento,count(*) "+
	    			"FROM "+
	    			/*"ticket t left join lookup_origine lo on(t.origine = lo.code) "+
		" left join lookup_tipo_alimento lai on(t.tipo_alimento = lai.code) "+
	    			 */
	    			"lookup_origine lo, "+
	    			"ticket t, "+
	    			"lookup_tipo_alimento lai," +
	    			" organization o "+ 
	    			"WHERE "+
	    			"t.origine = lo.code AND "+
	    			"t.tipo_alimento = lai.code "+
	    			" and t.tipologia = 700 "+
	    			" and data_apertura >=? and data_apertura <=? and o.org_id = t.org_id and o.site_id="+site +
	    	" GROUP BY lo.description, lai.description order by lo.description ");
	    }
	    else //sono amministratore
	    {
	    	sqlCount.append(


	    			"SELECT lo.description as origine,lai.description as alimento,count(*) "+
	    			"FROM "+
	    			/*"ticket t left join lookup_origine lo on(t.origine = lo.code) "+
		" left join lookup_tipo_alimento lai on(t.tipo_alimento = lai.code) "+
	    			 */
	    			"lookup_origine lo, "+
	    			"ticket t, "+
	    			"lookup_tipo_alimento lai "+ 
	    			"WHERE "+
	    			"t.origine = lo.code AND "+
	    			"t.tipo_alimento = lai.code "+
	    			" and t.tipologia = 700 "+
	    			" and data_apertura >=? and data_apertura <=?" +
	    	" GROUP BY lo.description, lai.description order by lo.description ");
	    }

	    Hashtable hashA = new Hashtable();
	    Hashtable hashB = null;
	     //  createFilter(sqlFilter, db);
	   // if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      //items = prepareFilter(pst);
	      pst.setTimestamp(1, iniz);
	      pst.setTimestamp(2, fin);
	      rs = pst.executeQuery();
	      ArrayList lista = new ArrayList();
	      String oldOrigine = "";
	      int temp = 0;
	      while (rs.next()) 
	      {
	    	  temp = 1;
	    	  //hashB = new Hashtable();
	    	  String nuovaOrigine = (String) rs.getString("origine");
	    	  if(!nuovaOrigine.equals(oldOrigine))  //se la vecchia e' diversa della nuova	  
	    	  {
	    		  if(hashB != null)
	    		  {	  
	    			  hashA.put( oldOrigine, hashB );
	    		  }
	    		  
	    		  hashB = new Hashtable();
	    	  }
	    		  hashB.put( rs.getString(2),(Integer)rs.getInt(3) );
	    		  oldOrigine = nuovaOrigine;
	    		  //hashA.put( oldOrigine, hashB );  //aggiunta da me
	      }
		  if(temp > 0) hashA.put( oldOrigine, hashB );  //controllo presenza di almeno un elemento
	    	  
	      
	        //pagedListInfo.setMaxRecords(maxRecords);
	      
	      rs.close();
	      pst.close();
	      // Declare default sort, if unset
	      pagedListInfo.setDefaultSort("t.entered", null);
	      //Determine the offset, based on the filter, for the first record to show
	      if (pagedListInfo.getMode() == PagedListInfo.DETAILS_VIEW && id > 0) {
	        String direction = null;
	        if ("desc".equalsIgnoreCase(pagedListInfo.getSortOrder())) {
	          direction = ">";
	        } else {
	          direction = "<";
	        }
	      }
	     
	    return hashA;
	    
	  }
	
	
	public ArrayList tipoAlimenti(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	   
	    "SELECT description "+
		"FROM "+
		"lookup_tipo_alimento ");

	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      //items = prepareFilter(pst);
	      rs = pst.executeQuery();
	      ArrayList lista = new ArrayList();
	     
	      
	      while (rs.next()) 
	      {
	    	 String tipoAlimento = rs.getString("description");
	    	 lista.add(tipoAlimento);	    	 
	      }	  
	      
	        //pagedListInfo.setMaxRecords(maxRecords);
	      
	      rs.close();
	      pst.close();
	  return lista;
	    
	  }
	
	public ArrayList origine(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	   
	    "SELECT description "+
		"FROM "+
		"lookup_origine ");

	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      //items = prepareFilter(pst);
	      rs = pst.executeQuery();
	      ArrayList lista = new ArrayList();
	     
	      
	      while (rs.next()) 
	      {
	    	 String origine = rs.getString("description");
	    	 lista.add(origine);	    	 
	      }	  
	      
	        //pagedListInfo.setMaxRecords(maxRecords);
	      
	      rs.close();
	      pst.close();
	  return lista;
	    
	  }
	
	
	
protected void createFilter(StringBuffer sqlFilter, Connection db) {
    if (source > 0) {
	      sqlFilter.append("AND lu_ts.code = ? ");
	}
    if ((tipo_richiesta != null) && (tipo_richiesta.length() > 0)) {
	      sqlFilter.append("AND t.tipo_richiesta = ? ");
	} 
    if ((tipologia == 700)) {
	      sqlFilter.append("AND t.tipologia = 700 ");
	} 
    //aggiunto da d.dauria
    if (codiceAllerta != null && ! "".equals(codiceAllerta)) {
	      sqlFilter.append("AND t.id_allerta ilike ? ");
	} 
    
    if((dataApertura != null) && (dataApertura2 != null))
    	  sqlFilter.append("AND t.data_apertura >= ?  ");
   
    
    
    if((dataApertura2 != null))
  	  sqlFilter.append(" AND t.data_apertura <= ?");
 
    
    //aggiunto da d.dauria
    if ((stato > -1) && (stato==2)) {   //chiuse
	      sqlFilter.append("AND t.data_chiusura is not Null and t.resolution_date is not null ");
	} 
    
    if ((stato > -1) && (stato==1)) {   //aperte
	      sqlFilter.append("AND t.data_chiusura IsNull AND t.resolution_date IsNull ");
	} 
   
    
    
    if ((tipoAlimento > -1)) {
	      sqlFilter.append("AND t.tipo_alimento = ? ");
	} 
    if ((origine > -1)) {
	      sqlFilter.append("AND t.origine = ? ");
	} 
    if ((alimentoInteressato > -1)) {
	      sqlFilter.append("AND t.alimento_interessato = ? ");
	} 
    if ((nonConformita > -1)) {
	      sqlFilter.append("AND t.non_conformita = ? ");
	} 
    if ((altreIrregolarita > -1)) {
	      sqlFilter.append("AND t.altre_irregolarita = ? ");
	} 
   
  
	if (enteredBy > -1) {
	      sqlFilter.append("AND t.enteredby = ? ");
	    }
	    if (description != null) {
	      if (description.indexOf("%") >= 0) {
	        sqlFilter.append(
	            "AND (" + DatabaseUtils.toLowerCase(db) + "(" + DatabaseUtils.convertToVarChar(
	            db, "t.problem") + ") LIKE ?) ");
	      } else {
	        sqlFilter.append(
	            "AND (" + DatabaseUtils.toLowerCase(db) + "(" + DatabaseUtils.convertToVarChar(
	            db, "t.problem") + ") = ?) ");
	      }
	    }
	   
	   
	    if (id > -1) {
	      if (pagedListInfo == null || pagedListInfo.getMode() != PagedListInfo.DETAILS_VIEW) {
	        sqlFilter.append("AND t.ticketid = ? ");
	      }
	    }
	    if (orgId > -1) {
	      sqlFilter.append("AND t.org_id = ? ");
	    }
	    if (contactId > -1) {
	      sqlFilter.append("AND t.contact_id = ? ");
	    }
	    if (serviceContractId > -1) {
	      sqlFilter.append("AND t.link_contract_id = ? ");
	    }
	    if (assetId > -1) {
	      sqlFilter.append("AND t.link_asset_id = ? ");
	    }
	    if (buildDepartmentTickets){
	      if (department > 0) {
	        if (unassignedToo) {
	          sqlFilter.append(
	              "AND (t.department_code in (?, 0, -1) OR ((t.assigned_to IS NULL OR t.assigned_to = 0 OR t.assigned_to = -1) AND (t.user_group_id IS NULL OR t.user_group_id = 0 OR t.user_group_id = -1)))");
	        } else {
	          sqlFilter.append("AND t.department_code = ? ");
	        }
	      } else {
	        sqlFilter.append("AND t.department_code IS NULL ");
	      }
	    }
	    if (assignedTo > -1) {
	      sqlFilter.append("AND t.assigned_to = ? ");
	    }
	    if (excludeAssignedTo > -1) {
	      sqlFilter.append("AND (t.assigned_to <> ? OR t.assigned_to IS NULL) ");
	    }
	    if (onlyAssigned) {
	      sqlFilter.append("AND (t.assigned_to > 0 AND t.assigned_to IS NOT NULL) ");
	    }
	    if (onlyUnassigned) {
	      sqlFilter.append(
	          "AND (t.assigned_to IS NULL OR t.assigned_to = 0 OR t.assigned_to = -1) ");
	    }
	    if (severity > 0) {
	      sqlFilter.append("AND t.scode = ? ");
	    }
	    if (priority > 0) {
	      sqlFilter.append("AND t.pri_code = ? ");
	    }
	    if (escalationLevel > 0) {
	      sqlFilter.append("AND t.escalation_level = ? ");
	    }
	    if (accountOwnerIdRange != null) {
	      sqlFilter.append(
	          "AND t.org_id IN (SELECT org_id FROM organization WHERE owner IN (" + accountOwnerIdRange + ")) ");
	    }
	    if (productId != -1) {
	      sqlFilter.append("AND t.product_id = ? ");
	    }
	    if (customerProductId != -1) {
	      sqlFilter.append("AND t.customer_product_id = ? ");
	    }
	    if (onlyWithProducts) {
	      sqlFilter.append("AND t.product_id IS NOT NULL ");
	    }
	    if (projectId > 0) {
	      sqlFilter.append(
	          "AND t.ticketid IN (SELECT ticket_id FROM ticketlink_project WHERE project_id = ?) ");
	    }
	 

	    if (catCode != -1) {
	      sqlFilter.append("AND t.cat_code = ? ");
	    }
	    if (subCat1 != -1) {
	      sqlFilter.append("AND t.subcat_code1 = ? ");
	    }
	    if (subCat2 != -1) {
	      sqlFilter.append("AND t.subcat_code2 = ? ");
	    }
	    if (subCat3 != -1) {
	      sqlFilter.append("AND t.subcat_code3 = ? ");
	    }
	    if ((!includeAllSites) || (orgId == -1 && contactId == -1 && forProjectUser == -1
	         && id == -1 && serviceContractId == -1 && assetId == -1 && projectId == -1 && userGroupId == -1
	         && inMyUserGroups == -1))
	    {
		      if (siteId != -1)
		      {
		        sqlFilter.append("AND (ac.id_asl = ? ");
		        if (!exclusiveToSite)
		        {
		          sqlFilter.append("OR t.site_id IS NULL ");
		        }
		        sqlFilter.append(") ");
		        
		        if( statoAslCorrente > 0 )
		        {
		        	sqlFilter.append( " AND " + parseCondizioneStatoAsl() );
		        }
		        
		      }
		      else
		      {
			      if (exclusiveToSite) {
							sqlFilter.append("AND t.site_id IS NULL ");
		      }
	      }
	    }
	    if (enteredDateStart != null) {
	      sqlFilter.append("AND t.entered >= ? ");
	    }
	    if (enteredDateEnd != null) {
	      sqlFilter.append("AND t.entered <= ? ");
	    }
	    //Sync API
	    if (syncType == Constants.SYNC_INSERTS) {
	      if (lastAnchor != null) {
	        sqlFilter.append("AND t.entered >= ? ");
	      }
	      sqlFilter.append("AND t.entered < ? ");
	    } else if (syncType == Constants.SYNC_UPDATES) {
	      sqlFilter.append("AND t.modified >= ? ");
	      sqlFilter.append("AND t.entered < ? ");
	      sqlFilter.append("AND t.modified < ? ");
	    } else if (syncType == Constants.SYNC_QUERY) {
	      if (lastAnchor != null) {
	        sqlFilter.append("AND t.entered >= ? ");
	      }
	      if (nextAnchor != null) {
	        sqlFilter.append("AND t.entered < ? ");
	      }
	    } else {
	      //No sync, but still need to factor in age
	      if (minutesOlderThan > 0) {
	        sqlFilter.append("AND t.entered <= ? ");
	      }
	    }
	    if (searchText != null && !searchText.equals("")) {
	      sqlFilter.append(
	          "AND (" + DatabaseUtils.toLowerCase(
	          db, DatabaseUtils.convertToVarChar(db, "t.problem")) + " LIKE ? OR " +
	          DatabaseUtils.toLowerCase(
	          db, DatabaseUtils.convertToVarChar(db, "t." + DatabaseUtils.addQuotes(db, "comment"))) + " LIKE ? OR " +
	          DatabaseUtils.toLowerCase(
	          db, DatabaseUtils.convertToVarChar(db, "t.solution")) + " LIKE ?) ");
	    }
	    if (hasEstimatedResolutionDate) {
	      sqlFilter.append("AND t.est_resolution_date IS NOT NULL ");
	    }
	    if (includeOnlyTrashed) {
	      sqlFilter.append("AND t.trashed_date IS NOT NULL ");
	    } else if (trashedDate != null) {
	      sqlFilter.append("AND t.trashed_date = ? ");
	    } else {
	      sqlFilter.append("AND t.trashed_date IS NULL ");
	    }
	    if (userGroupId != -1) {
	      sqlFilter.append("AND t.user_group_id = ? ");
	    }
	    if (inMyUserGroups != -1) {
	      sqlFilter.append("AND t.user_group_id IN (SELECT group_id FROM user_group_map where user_id = ?) ");
	    }
	    if (defectId > -1) {
	      sqlFilter.append("AND t.defect_id = ? ");
	    }
	    if (stateId > -1) {
	      sqlFilter.append("AND t.state_id = ? ");
	    }
	  }

	private String parseCondizioneStatoAsl()
	{
		String ret = "";
		
		switch (statoAslCorrente) {
		case 1: //Ticket.ATTIVA
			ret = " ac.data_chiusura IS NULL AND ac.cu_pianificati < 0 ";
			break;
		case 2: //Ticket.CONTROLLI_IN_CORSO
			ret = " ac.data_chiusura IS NULL AND (ac.cu_pianificati >= 0) AND ((ac.cu_pianificati - ac.cu_eseguiti) > 0) ";
			break;
		case 3: //Ticket.CONTROLLI_COMPLETATI
			ret = " ac.data_chiusura IS NULL AND (ac.cu_pianificati >= 0) AND ((ac.cu_pianificati - ac.cu_eseguiti) <= 0) ";
			break;
		case 4: //Ticket.CHIUSA
			ret = " ac.data_chiusura IS NOT NULL ";
			break;
		}
		
		return ret;
	}

protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (source > 0) {
    	pst.setInt( ++i, source );
	}
    if ((tipo_richiesta != null) && (tipo_richiesta.length() > 0)) {
	      pst.setString( ++i, tipo_richiesta ); 
	}
    
    if (codiceAllerta != null && ! "".equals(codiceAllerta)) {
	      pst.setString( ++i, codiceAllerta ); 
	}
    
    if ((dataApertura  != null) ) {
	      pst.setTimestamp( ++i, dataApertura );
	     
	}
    
    if ((dataApertura2  != null) ) {
    	 pst.setTimestamp(++i, dataApertura2);
	}
 
   
    
    
    if ((tipoAlimento  > -1)) {
	      pst.setInt( ++i, tipoAlimento ); 
	}
    if ((origine  > -1)) {
	      pst.setInt( ++i, origine ); 
	}
    if ((alimentoInteressato  > -1)) {
	      pst.setInt( ++i, alimentoInteressato ); 
	}
    if ((nonConformita  > -1)) {
	      pst.setInt( ++i, nonConformita ); 
	}
    if ((altreIrregolarita  > -1)) {
	      pst.setInt( ++i, altreIrregolarita ); 
	}
    
  /*  if ((tipologia == 1)) {
	      pst.setInt( ++i, tipologia );
	}*/
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    if (description != null) {
      pst.setString(++i, description.toLowerCase());
    }
    if (id > -1) {
      if (pagedListInfo == null || pagedListInfo.getMode() != PagedListInfo.DETAILS_VIEW) {
        pst.setInt(++i, id);
      }
    }
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    if (contactId > -1) {
      pst.setInt(++i, contactId);
    }
    if (serviceContractId > -1) {
      pst.setInt(++i, serviceContractId);
    }
    if (assetId > -1) {
      pst.setInt(++i, assetId);
    }
    if (department > 0) {
      pst.setInt(++i, department);
    }
    if (assignedTo > -1) {
      pst.setInt(++i, assignedTo);
    }
    if (excludeAssignedTo > -1) {
      pst.setInt(++i, excludeAssignedTo);
    }
    if (severity > 0) {
      pst.setInt(++i, severity);
    }
    if (priority > 0) {
      pst.setInt(++i, priority);
    }
    if (escalationLevel > 0) {
      pst.setInt(++i, escalationLevel);
    }
    if (productId > 0) {
      pst.setInt(++i, productId);
    }
    if (customerProductId > 0) {
      pst.setInt(++i, customerProductId);
    }
    if (projectId > 0) {
      pst.setInt(++i, projectId);
    }
    if (forProjectUser > -1) {
      pst.setInt(++i, forProjectUser);
    }
    if (catCode != -1) {
      pst.setInt(++i, catCode);
    }
    if (subCat1 != -1) {
      pst.setInt(++i, subCat1);
    }
    if (subCat2 != -1) {
      pst.setInt(++i, subCat2);
    }
    if (subCat3 != -1) {
      pst.setInt(++i, subCat3);
    }
    if ((!includeAllSites) || (orgId == -1 && contactId == -1 && forProjectUser == -1
         && id == -1 && serviceContractId == -1 && assetId == -1 && projectId == -1 && userGroupId == -1
         && inMyUserGroups == -1)) {
      if (siteId != -1) {
				pst.setInt(++i, siteId);
				
			}
    }
    if (enteredDateStart != null) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(enteredDateStart.getTime()));
    }
    if (enteredDateEnd != null) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(enteredDateEnd.getTime()));
    }
    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        java.sql.Timestamp adjustedDate = lastAnchor;
        if (minutesOlderThan > 0) {
          Calendar now = Calendar.getInstance();
          now.setTimeInMillis(lastAnchor.getTime());
          now.add(Calendar.MINUTE, minutesOlderThan - (2 * minutesOlderThan));
          adjustedDate = new java.sql.Timestamp(now.getTimeInMillis());
        }
        pst.setTimestamp(++i, adjustedDate);
      }
      if (nextAnchor != null) {
        java.sql.Timestamp adjustedDate = nextAnchor;
        if (minutesOlderThan > 0) {
          Calendar now = Calendar.getInstance();
          now.setTimeInMillis(nextAnchor.getTime());
          now.add(Calendar.MINUTE, minutesOlderThan - (2 * minutesOlderThan));
          adjustedDate = new java.sql.Timestamp(now.getTimeInMillis());
        }
        pst.setTimestamp(++i, adjustedDate);
      }
    } else {
      //No sync, but still need to factor in age
      if (minutesOlderThan > 0) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutesOlderThan - (2 * minutesOlderThan));
        java.sql.Timestamp adjustedDate = new java.sql.Timestamp(
            now.getTimeInMillis());
        pst.setTimestamp(++i, adjustedDate);
      }
    }

    if (searchText != null && !searchText.equals("")) {
      pst.setString(++i, searchText.toLowerCase());
      pst.setString(++i, searchText.toLowerCase());
      pst.setString(++i, searchText.toLowerCase());
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
    }
    if (userGroupId != -1) {
      pst.setInt(++i, userGroupId);
    }
    if (inMyUserGroups != -1) {
      pst.setInt(++i, inMyUserGroups);
    }
    if (defectId > -1) {
      pst.setInt(++i, defectId);
    }
    if (stateId > -1) {
      pst.setInt(++i, stateId);
    }
    return i;
  }

	public void setStatoAslCorrente(String stato_asl_corrente)
	{
		if( stato_asl_corrente != null )
		{
			this.statoAslCorrente = Integer.parseInt( stato_asl_corrente );
		}
	}

	
}

