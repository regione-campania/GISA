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
package org.aspcfs.modules.vigilanza.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.aspcfs.modules.punti_di_sbarco.base.Organization;
import org.aspcfs.modules.util.imports.DbUtil;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class TicketList extends org.aspcfs.modules.troubletickets.base.TicketList
{
	

	private static Logger log = Logger.getLogger(TicketList.class);
	
	private static final int AUDIT_SULLE_PROPRIE_STRUTTURE=101;
	private static final int AUDIT_NURECU_SISTEMA=102;
	private static final int AUDIT_NURECU_SETTORE=103;
	protected String idControllo ;
	protected Timestamp dataControllo ;
	protected String mc 			;
	protected int idMacchinetta=-1;
	protected boolean idMacchinettaNotNull=false;
	protected int idConcessionario =-1;
	private int tipologiaOperatore ;
	protected int source = -1;
	protected int tipologia = -1;
	protected String tipo_richiesta = null;
	protected int tipoCampione = -1;
	protected int esitoCampione = -1;
	protected int tipoIspezione=-1;
	protected int tipoAudit = -1;
	protected int tipoProvvedimenti = -1;
	protected String cfproprietario;
	private int org_id_pdp = -1 ;
	private int auditTipo;
	private String[] tipologia_struttura  ;
	
	
	private int anno ;
	private int statusId=-1;
	
	private int idRuolo = -1;
	
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public void setStatusId(String statusId) {
		if(statusId!=null && !"".equals(statusId))
		this.statusId = Integer.parseInt(statusId);
	}
	
	public int getAuditTipo() {
		return auditTipo;
	}
	public void setAuditTipo(int auditTipo) {
		this.auditTipo = auditTipo;
	}
	public void setAuditTipo(String auditTipo) {
		if(auditTipo!=null && !"".equals(auditTipo))
		this.auditTipo = Integer.parseInt(auditTipo);
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	public void setAnno(String anno) {
		
		if (anno!=null && !"".equals(anno))
			this.anno = Integer.parseInt(anno);
	}
	
	public String[] getTipologia_struttura() {
		return tipologia_struttura;
	}
	public void setTipologia_struttura(String[] tipologia_struttura) {
		this.tipologia_struttura = tipologia_struttura;
	}
	public int getOrg_id_pdp() {
		return org_id_pdp;
	}
	public void setOrg_id_pdp(int org_id_pdp) {
		this.org_id_pdp = org_id_pdp;
	}
	public String getCfproprietario() {
		return cfproprietario;
	}
	public void setCfproprietario(String cfproprietario) {
		this.cfproprietario = cfproprietario;
	}
	public int getTipologiaOperatore() {
		return tipologiaOperatore;
	}
	public void setTipologiaOperatore(int tipologiaOperatore) {
		this.tipologiaOperatore = tipologiaOperatore;
	}
	public int getIdMacchinetta() {
		return idMacchinetta;
	}
	public boolean getIdMacchinettaNotNull() {
		return idMacchinettaNotNull;
	}
	public void setIdConcessionario(int idConcessionario) {
		this.idConcessionario = idConcessionario;
	}
	
	public int getidConcessionario() {
		return idConcessionario;
	}
	public void setIdMacchinetta(int idMacchinetta) {
		this.idMacchinetta = idMacchinetta;
	}
	public void setIdMacchinettaNotNull(boolean idMacchinettaNotNull) {
		this.idMacchinettaNotNull = idMacchinettaNotNull;
	}
	

	private boolean farmacosorveglianza = false ;
	
	public boolean isFarmacosorveglianza() {
		return farmacosorveglianza;
	}
	public void setFarmacosorveglianza(boolean farmacosorveglianza) {
		this.farmacosorveglianza = farmacosorveglianza;
	}
	public int getTipoAudit() {
		return tipoAudit;
	}
	public void setTipoAudit(String tipoAudit) {
	    try {
	      this.tipoAudit = Integer.parseInt(tipoAudit);
	    } catch (Exception e) {
	      this.tipoAudit = -1;
	    }
	  }
	
	public int getTipoProvvedimenti() {
		return tipoProvvedimenti;
	}
	public void setTipoProvvedimenti(String tipoProvvedimenti) {
	    try {
	      this.tipoProvvedimenti = Integer.parseInt(tipoProvvedimenti);
	    } catch (Exception e) {
	      this.tipoProvvedimenti = -1;
	    }
	  }
	public int getTipoIspezione() {
		return tipoIspezione;
	}
	public void setTipoIspezione(int tipoIspezione) {
		this.tipoIspezione = tipoIspezione;
	}
	
	  public String getTipo_richiesta() {
		return tipo_richiesta;
	}
	  
	  public int getTipologia() {
			return tipologia;
		}

	  public int getEsitoCampione() {
			return esitoCampione;
		}

	  public int getTipoCampione() {
			return tipoCampione;
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
	public void setTipoIspezione(String tipologia) {
		this.tipoIspezione = Integer.parseInt( tipologia );
	}
	
	public void setTipoCampione(int tipoCampione) {
		this.tipoCampione = tipoCampione;
	}
	
	public void setTipoCampione(String tipoCampione) {
		this.tipoCampione = Integer.parseInt( tipoCampione );
	}
	
	public void setEsitoCampione(int esitoCampione) {
		this.esitoCampione = esitoCampione;
	}
	
	public void setEsitoCampione(String esitoCampione) {
		this.esitoCampione = Integer.parseInt( esitoCampione );
	}
	
	
	
	public String getIdControllo() {
		return idControllo;
	}
	public void setIdControllo(String idControllo) {
		this.idControllo = idControllo;
	}
	public Timestamp getDataControllo() {
		return dataControllo;
	}
	public void setDataControllo(String dataControllo) {
		if(dataControllo!= null && !dataControllo.equals(""))
		{	
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				this.dataControllo = new Timestamp(sdf.parse(dataControllo).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	
		
	public int getIdRuolo() {
		return idRuolo;
	}
	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}
	public void buildListLaboratoriHACCP(Connection db, String ticketId)  throws SQLException {
		 
		  PreparedStatement pst = null;
		  ResultSet rs = queryListLaboratoriControllati(db, pst, (Integer.parseInt(ticketId)));
	      while (rs.next()) {
			 org.aspcfs.modules.laboratorihaccp.base.Organization thisOrganization = new org.aspcfs.modules.laboratorihaccp.base.Organization();
			  thisOrganization.setOrgId(rs.getInt("org_id"));
			  thisOrganization.setName(rs.getString("name"));
			  thisOrganization.setCognomeRappresentante(rs.getString("cognome_rappresentante"));
			  thisOrganization.setAccountNumber(rs.getString("account_number"));
			  
			  this.add(thisOrganization);
	      }
		  rs.close();
		  if (pst != null) {
			  pst.close();
		  }
		  
		  buildResourcesLaboratori(db);
	 
	  }
	  
	  
	  private ResultSet queryListLaboratoriControllati(Connection db, PreparedStatement pst, int ticketId) throws SQLException {
		
		  // TODO Auto-generated method stub
		  StringBuffer sqlSelect = new StringBuffer();
		  ResultSet rs = null;
		  sqlSelect.append("SELECT * FROM organization where org_id in (select org_id from laboratori_haccp_controllati where org_id != -1 and ticket_id = ? )") ;
		  pst = db.prepareStatement(sqlSelect.toString());
		  pst.setInt(1, ticketId);
		  rs = DatabaseUtils.executeQuery(db, pst,log);
		  
		  return rs;
	  }
	  
	  protected void buildResourcesLaboratori(Connection db) throws SQLException {
		    Iterator i = this.iterator();
		    while (i.hasNext()) {
		    	org.aspcfs.modules.laboratorihaccp.base.Organization thisOrganization = (org.aspcfs.modules.laboratorihaccp.base.Organization) i.next();
		    	
		    }
	  }
		  
	  public void buildListLaboratoriHACCPNoReg(Connection db, String ticketId)  throws SQLException {
			 
		  PreparedStatement pst = null;
		  ResultSet rs = queryListLaboratoriControllatiNoReg(db, pst, (Integer.parseInt(ticketId)));
	      while (rs.next()) {
			  org.aspcfs.modules.laboratorihaccp.base.Organization thisOrganization = new org.aspcfs.modules.laboratorihaccp.base.Organization();
			  thisOrganization.setName(rs.getString("dati_lab"));
			  this.add(thisOrganization);
	      }
		  rs.close();
		  if (pst != null) {
			  pst.close();
		  }
		  
		  buildResourcesLaboratori(db);
	 
	  }
	  
	  
	  private ResultSet queryListLaboratoriControllatiNoReg(Connection db, PreparedStatement pst, int ticketId) throws SQLException {
		
		  // TODO Auto-generated method stub
		  StringBuffer sqlSelect = new StringBuffer();
		  ResultSet rs = null;
		  sqlSelect.append("SELECT * FROM laboratori_haccp_controllati where org_id = -1 and ticket_id = ? ") ;
		  pst = db.prepareStatement(sqlSelect.toString());
		  pst.setInt(1, ticketId);
		  rs = DatabaseUtils.executeQuery(db, pst,log);
		  
		  return rs;
	  }
	
	
	
	
	
	/*public int buildListControlliUltimiAnniV(Connection db, int org_id) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    int punteggio = 0;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	        "SELECT SUM(t.punteggio)AS punteggioAccumulato " +
	        "FROM ticket t " +
	        "LEFT JOIN organization o ON (t.alt_id = o.alt_id) " +
	        "INNER JOIN ticket tic ON ((tic.tipologia = 3)and(tic.alt_id = t.alt_id)and(tic.id_controllo_ufficiale = t.id_controllo_ufficiale)) "+
	        "INNER JOIN lookup_tipo_controllo tcontrollo ON (tcontrollo.code = 3 and  tcontrollo.code = t.provvedimenti_prescrittivi) "+
	        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
	         "WHERE t.ticketid > 0 AND t.tipologia = 2 and t.alt_id = "+altId+" and t.assigned_date <= now() and t.assigned_date >=now() - interval '5 years'");
	    
	      pst = db.prepareStatement(sqlCount.toString());
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        punteggio += rs.getInt("punteggioAccumulato");
	      }
	    return punteggio;
	  }*/
	
	
	
	public int buildListControlliUltimiAnni(Connection db, int org_id, Timestamp dataC, String idC) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;


	    PreparedStatement pst2 = null;
	    ResultSet rs2 = null;
	    
	    PreparedStatement pst3 = null;
	    ResultSet rs3 = null;

	    int items = -1;
	    int punteggio = 0;
	   
	   // StringBuffer sqlCamTam = new StringBuffer();
	   // StringBuffer sqlPuntiMeno = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    //Need to build a base SQL statement for counting records

//	    sqlCount.append(
//	    		"SELECT SUM(t.punteggio)AS punteggioAccumulato " +
//		        "FROM ticket t " +
//		        "LEFT JOIN tipocontrolloufficialeimprese tc on (t.ticketid = tc.idcontrollo) " +
//		        "WHERE t.ticketid > 0 " +
//		        "AND t.tipologia = 3 " +
//		        "and t.org_id = "+ org_id +" " +
//		        "and ((t.provvedimenti_prescrittivi = 3) " +
//		        "or (t.provvedimenti_prescrittivi =  4 and (tc.tipoispezione = 2 or tc.tipoispezione = 4 or tc.tipoispezione = 5 or tc.tipoispezione = 6 or tc.tipoispezione = 7 or tc.tipoispezione = 8))) " +
//		        "and t.assigned_date <= now() and t.assigned_date >=now() - interval '5 years';");
//	    
	    /*sqlCount.append("SELECT SUM(t.punteggio)AS punteggioAccumulato FROM ticket t "+
	    		"  WHERE t.ticketid > 0 "+
		        "AND t.tipologia = 3 "+
		        "and t.org_id = "+org_id+
		        "and ((t.provvedimenti_prescrittivi = 3) "+
		        "or (t.provvedimenti_prescrittivi =  4 and t.ticketid in (select idcontrollo from tipocontrolloufficialeimprese tc where tc.tipoispezione not in (3)))) "+
		        "and t.assigned_date <= now() - interval '5 days' and t.assigned_date >=now() - interval '5 years' and t.trashed_date is null and t.closed is not null;");
	     */
	    sqlCount.append("select * from  getpunteggioaccumulato (?,'org_id')");

	   /* sqlPuntiMeno.append(
	    		"SELECT SUM(t.punteggio)AS punteggioAccumulatoCT "+
		        "FROM ticket t "+
		        "JOIN ticket cu on (t.id_controllo_ufficiale = trim(to_char(cu.ticketid  , '000000' )) ) "+
		        "WHERE (t.tipologia = 2 OR t.tipologia = 7) "+ 
		        "and t.org_id = ? "+
		       // "and t.id_controllo_ufficiale = ?  "+
		       
		        "and cu.assigned_date <= now() and cu.assigned_date >=now() - interval '5 years' and trashed_date is null and closed is null;");
	     sqlCamTam.append("SELECT SUM(t.punteggio)AS punteggioAccumulatoCamTam "+
		        "FROM ticket t "+
		        //"LEFT JOIN tipocontrolloufficialeimprese tc on (t.ticketid = tc.idcontrollo) "+
		        "WHERE t.ticketid > 0 "+
		        "AND (t.tipologia = 2 or t.tipologia = 7 ) "+
		        "and t.org_id = "+org_id+" "+
		        "and ((t.provvedimenti_prescrittivi = 3) "+
		        "or (t.provvedimenti_prescrittivi =  4 and tc.tipoispezione = 3)) "+
		        "and t.assigned_date <= now() and t.assigned_date >=now() - interval '5 years' and trashed_date is null and closed is null;");
	     */
 	      pst = db.prepareStatement(sqlCount.toString());
 	      pst.setInt(1, org_id);
	      rs = pst.executeQuery();
	      if (rs.next()) {
		        punteggio = (rs.getInt("punteggioaccumulato") );
		      }
	      pst.close();
	      rs.close();
	      
	     
	     
	      //punteggio = punteggio + (punteggioAgg - punteggioSott);
	     	     
	      //punteggio=punteggioSott;
	      
	    return punteggio;
	  }
	
	
	public int buildListControlliUltimiAnni(Connection db, int org_id,int idCu_Sorveglianza) throws SQLException {
						
	    PreparedStatement pst = null;
	    ResultSet rs = null; 
	    int punteggio = 0;
	 
	    StringBuffer sqlPuntiMeno = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    
	    sqlPuntiMeno.append("select * from  getpunteggioaccumulato_ct (?,'org_id',?)");

	    
	    /*sqlPuntiMeno.append("SELECT sum (t.punteggio) as punteggioAccumulatoCT "+
		        " FROM ticket t " +
		        " JOIN ticket cu on (t.id_controllo_ufficiale = trim(to_char(cu.ticketid  , '000000' )) ) " +
		        " WHERE (t.tipologia = 3 ) and  t.id_controllo_ufficiale not in (select distinct id_controllo from audit) and t.provvedimenti_prescrittivi not in (5) "+
		       	" and t.punteggio>0 and t.closed is not null and t.org_id = ?"+
		       	" and cu.assigned_date <= (select t.assigned_date  - interval '7 days' from ticket t where ticketid = ?) and cu.assigned_date >= ( select t.assigned_date - interval '5 years' from ticket t where ticketid = ?)  ");
		       // "and t.id_controllo_ufficiale = ?  "+
		  */    
	    /* sqlCamTam.append("SELECT SUM(t.punteggio)AS punteggioAccumulatoCT "+
		        "FROM ticket t "+
		        "JOIN ticket cu on (t.id_controllo_ufficiale = trim(to_char(cu.ticketid  , '000000' )) ) "+
		        "WHERE (t.tipologia = 3 ) and (t.provvedimenti_prescrittivi not in (5)) " +
		        "and t.closed is not null and t.org_id = ? "+
		        "and cu.assigned_date <= now() and cu.assigned_date >=now() - interval '5 years'");
	     */
 	      pst = db.prepareStatement(sqlPuntiMeno.toString());
 	      pst.setInt(1,org_id);
 	      pst.setInt(2, idCu_Sorveglianza);
 	    //  pst.setInt(3, idCu_Sorveglianza);
	      rs = pst.executeQuery();
	      if (rs.next()) {
		        punteggio = (rs.getInt("getpunteggioaccumulato_ct") );
		      }
	      pst.close();
	      rs.close();
	      /*pst2 = db.prepareStatement(sqlCamTam.toString());
	      pst2.setInt(1,org_id);
	      rs2 = pst2.executeQuery();
	      if (rs2.next()) {
		        
	    	  punteggioAgg = ( rs2.getInt("punteggioAccumulatoCT"));
		        
		      }
	      pst2.close();
	      rs2.close();*/
	     /* pst3 = db.prepareStatement(sqlPuntiMeno.toString());
	      pst3.setInt(1,org_id);
 	      pst3.setInt(2, idCu_Sorveglianza);
 	      pst3.setInt(3, idCu_Sorveglianza);
	      //pst3.setString(2, idC);
	      //pst3.setTimestamp(2, dataC);
	      rs3 = pst3.executeQuery();
	     
	      if (rs3.next()) {
	        	        
	    	  punteggioSott = (rs3.getInt("punteggioAccumulatoCT"));
	        
	      }
	      pst3.close();
	      rs3.close();*/
	      //punteggio = punteggio + (punteggioAgg - punteggioSott);
	     	     
	      //punteggio=punteggioSott;
	      
	    return punteggio;
	  }
	
	
	
	
	public int buildListControlliUltimiAnniOpu(Connection db, int org_id,int idCu_Sorveglianza) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int punteggio = 0;
	  
	    StringBuffer sqlPuntiMeno = new StringBuffer();
	    //Need to build a base SQL statement for counting records	   
	    
	    sqlPuntiMeno.append("select * from  getpunteggioaccumulato_ct (?,'id_stabilimento',?)");
 	      pst = db.prepareStatement(sqlPuntiMeno.toString());
 	      pst.setInt(1,org_id);
 	      pst.setInt(2, idCu_Sorveglianza);
	      rs = pst.executeQuery();
	      if (rs.next()) {
		        punteggio = (rs.getInt("getpunteggioaccumulato_ct") );
		      }
	      pst.close();
	      rs.close();
	     /* pst2 = db.prepareStatement(sqlCamTam.toString());
	      pst2.setInt(1,org_id);
	      rs2 = pst2.executeQuery();
	      if (rs2.next()) {
		        
	    	  punteggioAgg = ( rs2.getInt("punteggioAccumulatoCT"));
		        
		      }
	      pst2.close();
	      rs2.close();
	      pst3 = db.prepareStatement(sqlPuntiMeno.toString());
	      pst3.setInt(1,org_id);
 	      pst3.setInt(2, idCu_Sorveglianza);
 	      pst3.setInt(3, idCu_Sorveglianza);
	      //pst3.setString(2, idC);
	      //pst3.setTimestamp(2, dataC);
	      rs3 = pst3.executeQuery();
	     
	      if (rs3.next()) {
	        	        
	    	  punteggioSott = (rs3.getInt("punteggioAccumulatoCT"));
	        
	      }
	      pst3.close();
	      rs3.close();
	      //punteggio = punteggio + (punteggioAgg - punteggioSott);
	     	     
	      //punteggio=punteggioSott;
	      */
	    return punteggio;
	  }
	
	
	public int buildListControlliUltimiAnniSintesis(Connection db, int org_id,int idCu_Sorveglianza) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;

	    int punteggio = 0;
	    StringBuffer sqlPuntiMeno = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    
	    sqlPuntiMeno.append("select * from  getpunteggioaccumulato_ct (?,'alt_id',?)");

	    /*sqlPuntiMeno.append("SELECT sum (t.punteggio) as punteggioAccumulatoCT "+
		        " FROM ticket t " +
		        " JOIN ticket cu on (t.id_controllo_ufficiale = trim(to_char(cu.ticketid  , '000000' )) ) " +
		        " WHERE (t.tipologia = 3 ) and  t.id_controllo_ufficiale not in (select distinct id_controllo from audit) and t.provvedimenti_prescrittivi not in (5) "+
		       	" and t.punteggio>0 and t.closed is not null and t.id_stabilimento = ?"+
		       	" and cu.assigned_date <= (select t.assigned_date  - interval '7 days' from ticket t where ticketid = ?) and cu.assigned_date >= ( select t.assigned_date - interval '5 years' from ticket t where ticketid = ?)  ");
		       // "and t.id_controllo_ufficiale = ?  "+
	     */
 	      pst = db.prepareStatement(sqlPuntiMeno.toString());
 	      pst.setInt(1,org_id);
 	      pst.setInt(2, idCu_Sorveglianza);
	      rs = pst.executeQuery();
	      if (rs.next()) {
		        punteggio = (rs.getInt("getpunteggioaccumulato_ct") );
		      }
	      pst.close();
	      rs.close();
	      /*pst2 = db.prepareStatement(sqlCamTam.toString());
	      pst2.setInt(1,org_id);
	      rs2 = pst2.executeQuery();
	      if (rs2.next()) {
		        
	    	  punteggioAgg = ( rs2.getInt("punteggioAccumulatoCT"));
		        
		      }
	      pst2.close();
	      rs2.close();
	      pst3 = db.prepareStatement(sqlPuntiMeno.toString());
	      pst3.setInt(1,org_id);
 	      pst3.setInt(2, idCu_Sorveglianza);
 	      pst3.setInt(3, idCu_Sorveglianza);
	      //pst3.setString(2, idC);
	      //pst3.setTimestamp(2, dataC);
	      rs3 = pst3.executeQuery();
	     
	      if (rs3.next()) {
	        	        
	    	  punteggioSott = (rs3.getInt("punteggioAccumulatoCT"));
	        
	      }
	      pst3.close();
	      rs3.close();
	      //punteggio = punteggio + (punteggioAgg - punteggioSott);
	     	     
	      //punteggio=punteggioSott;
	      */
	    return punteggio;
	  }
	
	
	/*public void buildListControlliFollowUp(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    int punteggio = 0;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	        "SELECT t.* " +
	        "FROM ticket t " +
	        "WHERE t.ticketid > 0 AND t.tipologia = 3 and t.follow_up != '' limit 5");
	    
	      pst = db.prepareStatement(sqlCount.toString());
	      rs = pst.executeQuery();
	      
	      rs = pst.executeQuery();
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, rs);
		    }
		    while (rs.next()) {
		      Ticket thisTicket = new Ticket(rs);
		      this.add(thisTicket);
		    }
		    rs.close();
		    pst.close();
		    //Build resources
		    Iterator i = this.iterator();
		    while (i.hasNext()) {
		      Ticket thisTicket = (Ticket) i.next();
		      thisTicket.buildFiles(db);
		      
		    }
	  }*/
	
	public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    ResultSet rs = null;
		    int items = -1;
		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlFilter = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();
		    //Need to build a base SQL statement for counting recordsdb,this.g
		    
		    Filtro filtro = null ;
		    if(super.orgId>0)
		    	 filtro = new Filtro(db,super.orgId);
		    else
		    {
		    	 filtro = new Filtro();
		    	 filtro.queryRecord(db, tipologiaOperatore);
		    }

		    
		    sqlCount.append(
		        "SELECT COUNT(*) as recordcount from (select distinct t.*,tcu.audit_tipo, o.site_id AS orgsiteid, o.name,o.tipologia as tipologia_operatore " +
		        "FROM ticket t "
		        
		        + "left join strutture_controllate_autorita_competenti strutture on strutture.id_controllo = t.ticketid ");
	    	sqlCount.append("left join tipocontrolloufficialeimprese tcu on tcu.idcontrollo=t.ticketid and tcu.enabled " );
	    	sqlCount.append("left join operatori_associati_mercato_ittico op on op.id_operatore = t.org_id and op.importato_in_anagrafica=false ");
//
		    	sqlCount.append("LEFT JOIN organization o ON (t.org_id = o.org_id) and o.trashed_date is null "+ filtro.getSql_join());
//		    	sqlCount.append("LEFT JOIN controlli_punti_di_prelievo_acque_rete  pdp ON (t.ticketid = pdp.id_controllo) " );
//		   sqlCount.append( "LEFT JOIN asset a ON (t.ticketid = a.idControllo) " +
		    	
		    	 if ( idRuolo>0)
				 {
		    	sqlCount.append("left join access_ acc on acc.user_id = t.enteredby ");
		    	sqlCount.append("left join access_ext_ accext on accext.user_id = t.enteredby ");
				 }

		    	sqlCount.append( " WHERE t.tipologia = 3  "+filtro.getSql_campi_variabili());

		    	
		    	
		    	createFilter(sqlFilter, db);
		    if (pagedListInfo != null) {
		      //Get the total number of records matching filter
		      pst = db.prepareStatement(
		          sqlCount.toString() +
		          sqlFilter.toString());
		      items = prepareFilter(pst);
		      rs = pst.executeQuery();
		      if (rs.next()) {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		       
		      }
		      rs.close();
		      pst.close();
		      // Declare default sort, if unset
		      pagedListInfo.setDefaultSort("t.tipo,t.assigned_date, t.ticketid", "DESC");
		      pagedListInfo.setColumnToSortBy("t.tipo,t.assigned_date, t.ticketid");
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
		        
		        System.out.println("QUERY LISTA CONTROLLI: " + pst.toString());
		        
		        rs = pst.executeQuery();
		        if (rs.next()) {
		          int offsetCount = rs.getInt("recordcount");
		          pagedListInfo.setCurrentOffset(offsetCount);
		        }
		        rs.close();
		        pst.close();
		      }
		      //Determine the offset
		      if (statusId<0)
		    	  pagedListInfo.appendSqlTail(db, sqlOrder);
		      else
			      sqlOrder.append("ORDER BY tipo,t.assigned_date DESC, t.ticketid desc ");
		    } else {
		      sqlOrder.append("ORDER BY tipo,t.assigned_date DESC, t.ticketid desc ");
		    }

		    //Need to build a base SQL statement for returning records
		    if (pagedListInfo != null) {
		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		    } else {
		      sqlSelect.append("SELECT ");
		    }
		    sqlSelect.append(
		        " * from ( select  distinct on (t.ticketid) t.ticketid as idcu ,t.*,tcu.audit_tipo, " +
		        "o.site_id AS orgsiteid, " +

		        "o.name,o.tipologia as tipologia_operatore,case when direct_bill=false or direct_bill is null then '1' else '0' end as tipo "+
		    
		        "FROM ticket t "
		        + "left join tipocontrolloufficialeimprese tcu on tcu.idcontrollo=t.ticketid and tcu.enabled "
		        + "left join strutture_controllate_autorita_competenti strutture on strutture.id_controllo = t.ticketid ");
		        	sqlSelect.append("LEFT JOIN organization o ON (t.org_id = o.org_id) "+ filtro.getSql_join());
		        	sqlSelect.append("left join operatori_associati_mercato_ittico op on op.id_operatore = t.org_id and op.importato_in_anagrafica=false ");
		        	
		        	if ( idRuolo>0)
					 {
		        	sqlSelect.append("left join access_ acc on acc.user_id = t.enteredby ");
		        	sqlSelect.append("left join access_ext_ accext on accext.user_id = t.enteredby ");
					 }
       	
		        	
		        	sqlSelect.append(" WHERE  t.tipologia = 3 "+filtro.getSql_campi_variabili() +" ");
		        	
		        	pst = db.prepareStatement(
		        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		    items = prepareFilter(pst);
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, pst);
		    }
		    
		    System.out.println("QUERY LISTA CONTROLLI: " + pst.toString());
		    
		    rs = pst.executeQuery();
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, rs);
		    }
		    while (rs.next()) {
		      Ticket thisTicket = new Ticket(db,rs);
		      
		      
		      int tipologiaOperatore = rs.getInt("tipologia_operatore");
		      if(thisTicket.getIdStabilimento()>0)
		    	  tipologiaOperatore = 999;
		      if(thisTicket.getAltId()>0 && DatabaseUtils.getTipologiaPartizione(db, thisTicket.getAltId()) == Ticket.ALT_SINTESIS)
		    	  tipologiaOperatore = 2000;
//		      if(thisTicket.getAltId()>0 && DatabaseUtils.getTipologiaPartizione(db, thisTicket.getAltId()) == Ticket.ALT_ANAGRAFICA_STABILIMENTI)
//		    	  tipologiaOperatore = 3000;
		      if(thisTicket.getAltId()>0 && DatabaseUtils.getTipologiaPartizione(db, thisTicket.getAltId()) == Ticket.ALT_OPU)
		    	  tipologiaOperatore = 999; //per opu metto sempre anagrafica
		      thisTicket.buildListLineeAttivita(db, thisTicket.getId(),tipologiaOperatore);
		      
		      if (thisTicket.getTipoCampione()==3 ||thisTicket.getTipoCampione()==23 )
		      {
		      thisTicket.getTipoAuditSelezionato(db, thisTicket.getId());
		      if (tipoAudit>0){
		    	  thisTicket.setAuditTipo(rs.getInt("audit_tipo"));
		    	  switch ( tipoAudit )
		    	  {
		    	  case 2 :
		    	  {
		    		  if (thisTicket.getAuditTipo()==AUDIT_SULLE_PROPRIE_STRUTTURE)
			    		   this.add(thisTicket);
		    		  
		    		  break;
		    	  }
		    	  case 3 :
		    	  {
		    		  if (thisTicket.getAuditTipo()==AUDIT_NURECU_SETTORE || thisTicket.getAuditTipo()==AUDIT_NURECU_SISTEMA)
			    		   this.add(thisTicket);
		    		  
		    		  break;
		    	  }
		    	  case 22:
		    	  {
		    		  if (thisTicket.getAuditTipo()==tipoAudit)
			    		   this.add(thisTicket);
		    	  }
		    	  }
		    	 
		      }
		      else
				     // if(((Integer)thisTicket.getIdMacchinetta())==null || thisTicket.getIdMacchinetta()==-1 || thisTicket.getIdMacchinetta()==0 )
				      this.add(thisTicket);
		      
		      }
		      else
		     // if(((Integer)thisTicket.getIdMacchinetta())==null || thisTicket.getIdMacchinetta()==-1 || thisTicket.getIdMacchinetta()==0 )
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
	
	
	
	
	/**
	 *  	COSTRUISCE LA LISTA DEI CONTROLLI APERTI DA 20 GIORNI
	 *   	DA UN UTENTE
	 *  
	 * @param db
	 * @param enteredBy
	 * @throws SQLException
	 */
	public void buildListCuApertiDa(Connection db,int enteredBy,int idasl) throws SQLException {
		
		int startRangeSintesis = DatabaseUtils.getStartRangePartizioneByTipologia(db, 6);
		int endRangeSintesis = DatabaseUtils.getEndRangePartizioneByTipologia(db, 6);
		
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	   
	    sqlSelect.append("select * from public.lista_controlli_aperti_20_giorni (?, ?, ?, ?)");
	    
	   /* sqlSelect.append(
	    		" (select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby,t.entered + interval '1 month' as data_chiusura_ufficio, " +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, " +
	    		" o.tipologia as tipologia_operatore,o.name, o.site_id AS orgsiteid  " +
	    		" from ticket t left join organization o on(t.org_id = o.org_id)  " +
	    		" left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (2,7,8))  " +
	    		" where t.tipologia =3 and t.closed is null and t.org_id >0 and t.trashed_date is null and o.date2 is null and o.trashed_date is null and t.modifiedby = ? and t.site_id = ? " +
	    		" and t.id_stabilimento is null and (t.alt_id is null OR t.alt_id < ? OR t.alt_id > ?) "+
	    		" and (o.cessato is null or o.cessato not in (1)) and (stato_lab is null or stato_lab not in (1)) and attivita.trashed_date is null  " +
	    		" group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby, " +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, " +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, " +
	    		" tipologia_operatore,o.name ,  orgsiteid,t.assigned_date  " +
	    		"  order by assigned_date asc,numAttivita desc) " +
	    		
	    		" union " +
	    		" (select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, t.link_asset_id,t.ticketid,t.punteggio,t.id_stabilimento as org_id,t.problem,t.entered,t.enteredby,t.entered + interval '1 month' as data_chiusura_ufficio, " +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, " +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, " +
	    		" 999 as tipologia_operatore, " +
	    		" op.ragione_sociale as name,  " +
	    		" o.id_asl as orgsiteid  " +
	    		" from ticket t  " +
	    		" left join opu_stabilimento o on(t.id_stabilimento = o.id) " +
	    		" left join opu_operatore op on(o.id_operatore = op.id)  " +
	    		" left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (2,7,8))  " +
	    		" where t.tipologia =3 and t.closed is null and t.id_stabilimento >0  and t.trashed_date is null and o.trashed_date is null and " +
	    		" t.modifiedby = ? and t.site_id = ?  and o.trashed_date is null and attivita.trashed_date is null  " +
	    		" group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby, " +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, " +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, " +
	    		" op.ragione_sociale ,  orgsiteid,t.assigned_date " +
	    		"   order by assigned_date asc,numAttivita desc )"
	    		
				   	+ " union ( "
				   	
				   	+ " select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, "
				   	+ " t.link_asset_id,t.ticketid,t.punteggio,t.alt_id as org_id,t.problem,t.entered,t.enteredby,t.entered + interval '1 month' as data_chiusura_ufficio,"
				   	+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia,"
				   	+ " t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo,"
				   	+ " 2000 as tipologia_operatore,op.ragione_sociale as name, o.id_asl AS orgsiteid " 
				   	+ " from ticket t "
				   	+ " left join sintesis_stabilimento o on(t.alt_id = o.alt_id) "
				   	+ " left join sintesis_operatore op on(o.id_operatore = op.id) "
				   	+ " left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (2,7,8)) "
				   	+ " where "
				   	+ " t.tipologia =3 and t.closed is null and t.trashed_date is null and o.trashed_date is null "
				   	+ " and t.modifiedby = ? and t.site_id = ?  and o.trashed_date is null and attivita.trashed_date is null "   
					+ " and t.id_stabilimento is null and (t.alt_id is not null AND t.alt_id >= ? AND t.alt_id <= ?) "
					+ " group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby, t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, tipologia_operatore, name, orgsiteid,t.assigned_date "
					+ " order by assigned_date asc, numAttivita desc)");
	    		*/

	    pst = db.prepareStatement(sqlSelect.toString() );
		pst.setInt(1, enteredBy);
		pst.setInt(2, idasl);
		pst.setInt(3, startRangeSintesis);
		pst.setInt(4, endRangeSintesis);
		/*pst.setInt(5, enteredBy);
		pst.setInt(6, idasl);
		pst.setInt(7, enteredBy);
		pst.setInt(8, idasl);
		pst.setInt(9, startRangeSintesis);
		pst.setInt(10, endRangeSintesis);*/
		rs = pst.executeQuery();
	  
	    while (rs.next()) {
	      Ticket thisTicket = new Ticket(db,rs,true);
	      this.add(thisTicket);
	     
	    }
	    rs.close();
	    pst.close();
	
	  }
	public void buildListCuApertiASL(Connection db,int idasl) throws SQLException {
		
		int startRangeSintesis = DatabaseUtils.getStartRangePartizioneByTipologia(db, 6);
		int endRangeSintesis = DatabaseUtils.getEndRangePartizioneByTipologia(db, 6);
		
		 PreparedStatement pst = null;
		    ResultSet rs = null;
		    int items = -1;
		    StringBuffer sqlSelect = new StringBuffer();
		   
		    sqlSelect.append("select * from public.lista_controlli_aperti_20_giorni (-1, ?, ?, ?)");


		 /*
		   sqlSelect.append(

		    		" (select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby,t.entered + interval '1 month' as data_chiusura_ufficio," +
		    		"t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
		    		"t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
		    		"o.tipologia as tipologia_operatore,o.name, o.site_id AS orgsiteid "+
		    		" from ticket t left join organization o on(t.org_id = o.org_id) " +
		    		" left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (2,7,8)) "+
		    		" where t.tipologia =3 and t.closed is null and t.trashed_date is null and o.date2 is null and o.trashed_date is null and t.site_id = ? " +
		    		" and t.id_stabilimento is null and (t.alt_id is null OR t.alt_id < ? OR t.alt_id > ?) "+
		    		" and (o.cessato is null or o.cessato not in (1)) and (stato_lab is null or stato_lab not in (1)) and attivita.trashed_date is null  "+
		    		" group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby," +
		    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
		    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
		    		" tipologia_operatore,o.name,  orgsiteid,t.assigned_date "+
		    		" order by assigned_date asc, numAttivita desc limit 100"
		    		
		    		+ " ) union ("
		    		
		    		+ " select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, "
		    		+ " t.link_asset_id,t.ticketid,t.punteggio,t.id_stabilimento as org_id,t.problem,t.entered,t.enteredby,t.entered + interval '1 month' as data_chiusura_ufficio,"
		    		+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia,"
		    		+ " t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo,"
		    		+ " 999 as tipologia_operatore,op.ragione_sociale as name, o.id_asl as orgsiteid "
		    		+ " from ticket t  left join opu_stabilimento o on(t.id_stabilimento = o.id) "
		    		+ " left join opu_operatore op on(o.id_operatore = op.id) "
		    		+ " left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (15))"
		    		+ " left join unita_operative_controllo uoc on uoc.id_controllo = t.ticketid "
		    		+ " left join oia_nodo uo on uo.id = uoc.id_unita_operativa"
		    		+ " left join tipocontrolloufficialeimprese tcui on tcui.idcontrollo = t.ticketid and tcui.tipoispezione > 0"
		    		+ " left join oia_nodo uo2 on uo2.id = tcui.id_unita_operativa"
		    		+ " where t.tipologia = 3 and t.closed is not null and t.trashed_date is null and o.trashed_date is null and"
		    		+ " o.stato not in (4) and t.site_id = ? and o.trashed_date is null and attivita.trashed_date is null and t.assigned_date >= current_timestamp + '-30D'"
		    		+ " group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby,"
		    		+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo,"
		    		+ " tipologia_operatore, name ,  orgsiteid,t.assigned_date, CASE when t.provvedimenti_prescrittivi in ( 3,5) then uo.descrizione_lunga	"
		    		+ " else	uo2.descrizione_lunga	END having  count (attivita.ticketid) != 0 order by assigned_date asc, numAttivita desc limit 100"
		   
				   	+ " ) union ( "
				   	
				   	+ " select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, "
				   	+ " t.link_asset_id,t.ticketid,t.punteggio,t.alt_id as org_id,t.problem,t.entered,t.enteredby,t.entered + interval '1 month' as data_chiusura_ufficio,"
				   	+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia,"
				   	+ " t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo,"
				   	+ " 2000 as tipologia_operatore,op.ragione_sociale as name, o.id_asl AS orgsiteid " 
				   	+ " from ticket t "
				   	+ " left join sintesis_stabilimento o on(t.alt_id = o.alt_id) "
				   	+ " left join sintesis_operatore op on(o.id_operatore = op.id) "
				   	+ " left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (2,7,8)) "
				   	+ " where "
				   	+ " t.tipologia =3 and t.closed is null and t.trashed_date is null and o.trashed_date is null "
		    		+ " and o.stato not in (1,3,4) and t.site_id = ? and o.trashed_date is null and attivita.trashed_date is null and t.assigned_date >= current_timestamp + '-30D'"
					+ " and t.id_stabilimento is null and (t.alt_id is not null AND t.alt_id >= ? AND t.alt_id <= ?) "
					+ " group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby, t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, tipologia_operatore, name, orgsiteid,t.assigned_date "
					+ " order by assigned_date asc, numAttivita desc limit 100 )");
			*/
			
		    pst = db.prepareStatement(sqlSelect.toString() );
		    //pst.setInt(1, enteredBy);
		    pst.setInt(1, idasl);
			pst.setInt(2, startRangeSintesis);
			pst.setInt(3, endRangeSintesis);
		   /* pst.setInt(4, idasl);
		    pst.setInt(5, idasl);
			pst.setInt(6, startRangeSintesis);
			pst.setInt(7, endRangeSintesis);*/

//		    System.out.println("QUERY 4 "+pst.toString());
			rs = pst.executeQuery();
		  
		    while (rs.next()) {
		      Ticket thisTicket = new Ticket(db,rs,true);

		      

		     /* if (thisTicket.getTipoCampione() == 4)
		    		  thisTicket.getTipoIspezioneSelezionatoScadenz(db, thisTicket.getId());
		      else 
		    	  thisTicket.buildUnitaOperativeScadenz(db);
*/
		      this.add(thisTicket);
		     
		    }
		    rs.close();
		    pst.close();
		
	}
	
	
	
	public void buildListCuSorveglianzaApertiDa(Connection db,int enteredBy,int idasl) throws SQLException {
		
		int startRangeSintesis = DatabaseUtils.getStartRangePartizioneByTipologia(db, 6);
		int endRangeSintesis = DatabaseUtils.getEndRangePartizioneByTipologia(db, 6);
		
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	   
	    sqlSelect.append("select * from public.lista_controlli_sorveglianza_tre_mesi (?, ?, ?,?)");

	   //31082016 - adeguamento query per OPU
	    /*sqlSelect.append(
	    		" ( select o.tipologia as tipologia_operatore,t.id_controllo_ufficiale,o.name, o.site_id AS orgsiteid,t.* ,o.prossimo_controllo,t.entered + interval '1 month' as data_chiusura_ufficio"+
	    		" FROM ticket t left join organization o on(t.org_id = o.org_id ) "+
	    		" WHERE t.ticketid > 0  AND t.tipologia = 3  and  t.provvedimenti_prescrittivi = 5  " +
	    		 "and t.isaggiornata_categoria = true and t.trashed_date is null  and o.prossimo_controllo=t.data_prossimo_controllo "+
	    		" and (t.data_prossimo_controllo < now() or t.data_prossimo_controllo<now() + interval '3 month') "+
	    		" and t.modifiedby = ? and t.site_id = ? and o.date2 is null and o.trashed_date is null "+
	    		" and t.id_stabilimento is null and (t.alt_id is null OR t.alt_id < ? OR t.alt_id > ?) "+
	    		" and (o.cessato is null or o.cessato not in (1)) and (stato_lab is null or stato_lab not in (1)) " +
	    		" order by t.assigned_date asc ) union ("
	    		
	    		+ " select 999 as tipologia_operatore, t.id_controllo_ufficiale,op.ragione_sociale as name, o.id_asl as orgsiteid,t.* ,"
	    		+ " o.data_prossimo_controllo,t.entered  + interval '1 month' as data_chiusura_ufficio"
	    		+ "	FROM ticket t "
	    		+ "	left join opu_stabilimento o on(t.id_stabilimento = o.id) "
	    		+ "	left join opu_operatore op on(o.id_operatore = op.id)  "
	    		+ " WHERE t.ticketid > 0  AND t.tipologia = 3  and  t.provvedimenti_prescrittivi = 5 "
	    		+ " and t.isaggiornata_categoria = true and t.trashed_date is null  and o.data_prossimo_controllo=t.data_prossimo_controllo "
	    		+ " and (t.data_prossimo_controllo < now() or t.data_prossimo_controllo<now()  +interval '3 month')"
	    		+ "	and t.modifiedby = ? and t.site_id = ? "
	    		+ "	and o.trashed_date is null and o.stato not in (4)"
	    		+ " order by t.assigned_date asc)"
	    		
	    		
		   	+ " union ( "
		   	
					+ " select 2000 as tipologia_operatore, t.id_controllo_ufficiale,op.ragione_sociale as name, o.id_asl as orgsiteid,t.* ,"
					+ " o.data_prossimo_controllo,t.entered  + interval '1 month' as data_chiusura_ufficio"
				   	+ " from ticket t "
				   	+ " left join sintesis_stabilimento o on(t.alt_id = o.alt_id) "
				   	+ " left join sintesis_operatore op on(o.id_operatore = op.id) "
				   	+ " where t.ticketid > 0  AND t.tipologia =3 and  t.provvedimenti_prescrittivi = 5 "
				   	+ " and t.isaggiornata_categoria = true and t.trashed_date is null  and o.data_prossimo_controllo=t.data_prossimo_controllo "
		    		+ " and (t.data_prossimo_controllo < now() or t.data_prossimo_controllo<now()  +interval '3 month')"
		    		+ "	and t.modifiedby = ? and t.site_id = ? "
		    		+ "	and o.trashed_date is null and o.stato not in (1,3,4)"
					+ " and t.id_stabilimento is null and (t.alt_id is not null AND t.alt_id >= ? AND t.alt_id <= ?) "
					+ " order by assigned_date asc )");
	    */
	    pst = db.prepareStatement(sqlSelect.toString() );
		pst.setInt(1, enteredBy);
		pst.setInt(2, idasl);
		pst.setInt(3, startRangeSintesis);
		pst.setInt(4, endRangeSintesis);
		/*pst.setInt(5, enteredBy);
		pst.setInt(6, idasl);
		pst.setInt(7, enteredBy);
		pst.setInt(8, idasl);
		pst.setInt(9, startRangeSintesis);
		pst.setInt(10, endRangeSintesis);*/
//	    System.out.println("QUERY 1 "+pst.toString());
		rs = pst.executeQuery();
	  
	    while (rs.next()) {
	      Ticket thisTicket = new Ticket(db,rs,true);
	      this.add(thisTicket);
	     
	    }
	    rs.close();
	    pst.close();
	
	  }
	//CU SORVEGLIANZA FILTRATI PER ASL E ORDINATI PER UNITA' OPERATIVA
	public void buildListCuSorveglianzaApertiASL(Connection db,int idasl) throws SQLException {
		
		int startRangeSintesis = DatabaseUtils.getStartRangePartizioneByTipologia(db, 6);
		int endRangeSintesis = DatabaseUtils.getEndRangePartizioneByTipologia(db, 6);
		
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	   
	    
	    sqlSelect.append("select * from public.lista_controlli_sorveglianza_tre_mesi (-1, ?, ?,?)");

	    /*sqlSelect.append(
	    		" (select o.tipologia as tipologia_operatore,t.id_controllo_ufficiale,o.name, o.site_id AS orgsiteid,t.* ,o.prossimo_controllo,t.entered + interval '1 month' as data_chiusura_ufficio,uo.descrizione_lunga"+
	    		" FROM ticket t left join organization o on(t.org_id = o.org_id ) " +
	    		" LEFT JOIN unita_operative_controllo uoc on uoc.id_controllo = t.ticketid " +
	    		" LEFT JOIN oia_nodo uo on uo.id = uoc.id_unita_operativa"+
	    		" WHERE t.ticketid > 0  AND t.tipologia = 3  and  t.provvedimenti_prescrittivi =  5 "+
	    		" and (t.data_prossimo_controllo < now() or t.data_prossimo_controllo<now() + interval '3 month') and t.assigned_date > '2012-12-31' "+
	    		" and t.site_id = ? and o.date2 is null and o.trashed_date is null "+
	    		" and t.id_stabilimento is null and (t.alt_id is null OR t.alt_id < ? OR t.alt_id > ?) "+
	    		" and (o.cessato is null or o.cessato not in (1)) and (stato_lab is null or stato_lab not in (1)) " +
	    		" order by t.assigned_date asc, uo.descrizione_lunga limit 100) union ("
	    		
	    		+ " select 999 as tipologia_operatore, t.id_controllo_ufficiale,op.ragione_sociale as name, o.id_asl AS orgsiteid,t.* ,"
	    		+ "o.data_prossimo_controllo,t.entered + interval '1 month' as data_chiusura_ufficio,uo.descrizione_lunga"+
	    		" FROM ticket t "
	    		+ "	left join opu_stabilimento o on(t.id_stabilimento = o.id) "
	    		+ "	left join opu_operatore op on(o.id_operatore = op.id)  "+
	    		" LEFT JOIN unita_operative_controllo uoc on uoc.id_controllo = t.ticketid " +
	    		" LEFT JOIN oia_nodo uo on uo.id = uoc.id_unita_operativa"+
	    		" WHERE t.ticketid > 0  AND t.tipologia = 3  and  t.provvedimenti_prescrittivi =  5 "+
	    		" and (t.data_prossimo_controllo < now() or t.data_prossimo_controllo<now() + interval '3 month') and t.assigned_date > '2012-12-31' "+
	    		" and t.site_id = ? and o.trashed_date is null and o.stato not in (4) " +
	    		" order by t.assigned_date asc, uo.descrizione_lunga limit 100)"
	    		
	    		
		   	+ " union ( "
		   	
	    			+ " select 2000 as tipologia_operatore, t.id_controllo_ufficiale,op.ragione_sociale as name, o.id_asl AS orgsiteid,t.* ,"
	    			+ "o.data_prossimo_controllo,t.entered + interval '1 month' as data_chiusura_ufficio,uo.descrizione_lunga "
				   	+ " from ticket t "
				   	+ " left join sintesis_stabilimento o on(t.alt_id = o.alt_id) "
				   	+ " left join sintesis_operatore op on(o.id_operatore = op.id) "
		    		+ " LEFT JOIN unita_operative_controllo uoc on uoc.id_controllo = t.ticketid "
		    		+ " LEFT JOIN oia_nodo uo on uo.id = uoc.id_unita_operativa "
				   	+ " left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (2,7,8)) "
				   	+ " WHERE t.ticketid > 0  AND t.tipologia = 3  and  t.provvedimenti_prescrittivi =  5 "
		    		+ " and (t.data_prossimo_controllo < now() or t.data_prossimo_controllo<now() + interval '3 month') and t.assigned_date > '2012-12-31' "
		    		+ " and t.site_id = ? and o.trashed_date is null and o.stato not in (1,3,4) "  
					+ " and t.id_stabilimento is null and (t.alt_id is not null AND t.alt_id >= ? AND t.alt_id <= ?) "
					+ " order by t.assigned_date asc, uo.descrizione_lunga desc limit 100 )");
	    
	    		*/
	    pst = db.prepareStatement(sqlSelect.toString() );
	    pst.setInt(1, idasl);
		pst.setInt(2, startRangeSintesis);
		pst.setInt(3, endRangeSintesis);
	  /*  pst.setInt(4, idasl);
	    pst.setInt(5, idasl);
		pst.setInt(6, startRangeSintesis);
		pst.setInt(7, endRangeSintesis);*/
//	    System.out.println("QUERY 5 "+pst.toString());
		rs = pst.executeQuery();
	  
	    while (rs.next()) {
	      Ticket thisTicket = new Ticket(db,rs,true);
	     /* if (thisTicket.getTipoCampione() == 4)
	    		  thisTicket.getTipoIspezioneSelezionatoScadenz(db, thisTicket.getId());
	      else 
	    	  thisTicket.buildUnitaOperative(db);
	      this.add(thisTicket);*/
	     
	    }
	    rs.close();
	    pst.close();
	
	  }
	
	public void buildListCuChiusiWithFUp(Connection db,int enteredBy,int idasl) throws SQLException {
		
		int startRangeSintesis = DatabaseUtils.getStartRangePartizioneByTipologia(db, 6);
		int endRangeSintesis = DatabaseUtils.getEndRangePartizioneByTipologia(db, 6);
		
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	   
	    sqlSelect.append("select * from public.lista_controlli_chiusi_followup(?,?,?,?)");
	    
	    /*sqlSelect.append(
	    		" (select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby,t.entered + interval '1 month' as data_chiusura_ufficio," +
	    		"t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
	    		"t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
	    		"o.tipologia as tipologia_operatore,o.name, o.site_id AS orgsiteid "+
	    		" from ticket t left join organization o on(t.org_id = o.org_id) " +
	    		" left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (15)) "+
	    		" where t.tipologia =3 and t.closed is not null and t.trashed_date is null and o.date2 is null and o.trashed_date is null  and t.modifiedby = ? and t.site_id = ? " +
	    		" and t.id_stabilimento is null and (t.alt_id is null OR t.alt_id < ? OR t.alt_id > ?) "+
	    		" and (o.cessato is null or o.cessato not in (1)) and (stato_lab is null or stato_lab not in (1)) and attivita.trashed_date is null  "+
	    		" and t.assigned_date <= now() and t.assigned_date >= now() + '-30D' "+
	    		" group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby," +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
	    		" tipologia_operatore,o.name ,  orgsiteid,t.assigned_date "+
	    		" having  count (attivita.ticketid) != 0 " +
	    		" order by numAttivita desc,assigned_date asc) union  "
	    		
	    		+ "( select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, "
	    		+ " t.link_asset_id,t.ticketid,t.punteggio,t.id_stabilimento as org_id,t.problem,t.entered,t.enteredby,t.entered  +interval '1 month' as data_chiusura_ufficio,"
	    		+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, "
	    		+ " t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, "
	    		+ " 999 as tipologia_operatore,op.ragione_sociale as name, o.id_asl as orgsiteid"
	    		+ " from ticket t "
	    		+ " left join opu_stabilimento o on(t.id_stabilimento = o.id) "
	    		+ " left join opu_operatore op on(o.id_operatore = op.id) "
	    		+ " left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (15)) "
	    		+ " where t.tipologia =3 and t.closed is not null and t.trashed_date is null and o.trashed_date is null and  "
	    		+ " o.stato not in (4) and t.modifiedby = ? and t.site_id = ? and o.trashed_date is null and attivita.trashed_date is null  "
	    		+ " and t.assigned_date <= now() and t.assigned_date >= now() + '-30D' "
	    		+ " group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby, "
	    		+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, "
	    		+ " t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, "
	    		+ " tipologia_operatore, name ,  orgsiteid,t.assigned_date "
	    		+ " having  count (attivita.ticketid) != 0 "
	    		+ " order by numAttivita desc,assigned_date asc)"
	    		
		   	+ " union ( "
		   	
		    		+ "select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, "
		    		+ " t.link_asset_id,t.ticketid,t.punteggio,t.alt_id as org_id,t.problem,t.entered,t.enteredby,t.entered  +interval '1 month' as data_chiusura_ufficio,"
		    		+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, "
		    		+ " t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, "
		    		+ " 2000 as tipologia_operatore,op.ragione_sociale as name, o.id_asl as orgsiteid"
				   	+ " from ticket t "
				   	+ " left join sintesis_stabilimento o on(t.alt_id = o.alt_id) "
				   	+ " left join sintesis_operatore op on(o.id_operatore = op.id) "
		    		+ " left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (15)) "
		    		+ " where t.tipologia =3 and t.closed is not null and t.trashed_date is null and o.trashed_date is null and  "
		    		+ " o.stato not in (1,3,4) and t.modifiedby = ? and t.site_id = ? and o.trashed_date is null and attivita.trashed_date is null  "
		    		+ " and t.assigned_date <= now() and t.assigned_date >= now() + '-30D' "
					+ " and t.id_stabilimento is null and (t.alt_id is not null AND t.alt_id >= ? AND t.alt_id <= ?) "
					+ " group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby, "
		    		+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, "
		    		+ " t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, "
		    		+ " tipologia_operatore, name ,  orgsiteid,t.assigned_date "
		    		+ " having  count (attivita.ticketid) != 0 "
		    		+ " order by numAttivita desc,assigned_date asc)");		
	    */
	    pst = db.prepareStatement(sqlSelect.toString() );
		pst.setInt(1, enteredBy);
		pst.setInt(2, idasl);
		pst.setInt(3, startRangeSintesis);
		pst.setInt(4, endRangeSintesis);
		/*pst.setInt(5, enteredBy);
		pst.setInt(6, idasl);
		pst.setInt(7, enteredBy);
		pst.setInt(8, idasl);
		pst.setInt(9, startRangeSintesis);
		pst.setInt(10, endRangeSintesis);*/
//	    System.out.println("QUERY 2 "+pst.toString());
		rs = pst.executeQuery();
	  
	    while (rs.next()) {
	      Ticket thisTicket = new Ticket(db,rs,true);
	      this.add(thisTicket);
	     
	    }
	    rs.close();
	    pst.close();
	
	  }
	// CONTROLLI CHIUSI CON FOLLOWUP PER ASL E UNITA OPERATIVA
	public void buildListCuChiusiWithFUpASL(Connection db,int idasl) throws SQLException {
		
		int startRangeSintesis = DatabaseUtils.getStartRangePartizioneByTipologia(db, 6);
		int endRangeSintesis = DatabaseUtils.getEndRangePartizioneByTipologia(db, 6);
		
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    
	    sqlSelect.append("select * from public.lista_controlli_chiusi_followup_unita_operativa(-1,?,?,?)");
	    
	    /*sqlSelect.append(" (select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, " +
	    		"t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby,t.entered + interval '1 month' as data_chiusura_ufficio," +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
	    		" o.tipologia as tipologia_operatore,o.name, o.site_id AS orgsiteid," +
	    		" CASE when t.provvedimenti_prescrittivi in ( 3,5)" +
	    		"	then uo.descrizione_lunga	else	uo2.descrizione_lunga	END as descrizione_lunga" +
	    		//" then uo.descrizione_lunga	else	uo2.descrizione_lunga	END as unita_operativa" +
	    		" from ticket t left join organization o on(t.org_id = o.org_id)" +
	    		" left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (15))" +
	    		" left join unita_operative_controllo uoc on uoc.id_controllo = t.ticketid" +
	    		" left join oia_nodo uo on uo.id = uoc.id_unita_operativa" +
	    		" left join tipocontrolloufficialeimprese tcui on tcui.idcontrollo = t.ticketid and tcui.tipoispezione > 0" +
	    		" left join oia_nodo uo2 on uo2.id = tcui.id_unita_operativa" +
	    		" where t.tipologia = 3 and t.closed is not null and t.trashed_date is null and o.date2 is null and o.trashed_date is null and t.site_id = ? " +
	    		" and t.id_stabilimento is null and (t.alt_id is null OR t.alt_id < ? OR t.alt_id > ?) "+
	    		" and (o.cessato is null or o.cessato not in (1)) and (stato_lab is null or stato_lab not in (1)) and attivita.trashed_date is null" +
	    		" and t.assigned_date >= current_timestamp + '-30D'" +
	    		" group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby," +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
	    		" tipologia_operatore,o.name ,  orgsiteid,t.assigned_date," +
	    		" CASE when t.provvedimenti_prescrittivi in ( 3,5)" +
	    		" then uo.descrizione_lunga	else	uo2.descrizione_lunga	END" +
	    		" having  count (attivita.ticketid) != 0" +
	    		" order by assigned_date asc, numAttivita desc limit 100) union ( "
	    		
	    		+ " select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, " +
	    		" t.link_asset_id,t.ticketid,t.punteggio,t.id_stabilimento as org_id,t.problem,t.entered,t.enteredby,"
	    		+ " t.entered + interval '1 month' as data_chiusura_ufficio," +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
	    		" 999 as tipologia_operatore,op.ragione_sociale as name, o.id_asl AS orgsiteid," +
	    		" CASE when t.provvedimenti_prescrittivi in ( 3,5)" +
	    		" then uo.descrizione_lunga	else	uo2.descrizione_lunga	END as descrizione_lunga" +
	    		//" then uo.descrizione_lunga	else	uo2.descrizione_lunga	END as unita_operativa" +
	    		" from ticket t "
	    		+ " left join opu_stabilimento o on(t.id_stabilimento = o.id) "
	    		+ " left join opu_operatore op on(o.id_operatore = op.id) " +
	    		" left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (15))" +
	    		" left join unita_operative_controllo uoc on uoc.id_controllo = t.ticketid" +
	    		" left join oia_nodo uo on uo.id = uoc.id_unita_operativa" +
	    		" left join tipocontrolloufficialeimprese tcui on tcui.idcontrollo = t.ticketid and tcui.tipoispezione > 0" +
	    		" left join oia_nodo uo2 on uo2.id = tcui.id_unita_operativa" +
	    		" where t.tipologia = 3 and t.closed is not null and t.trashed_date is null and o.trashed_date is null " +
	    		" and o.stato not in (4) and t.site_id = ?" +
	    		" and o.trashed_date is null and attivita.trashed_date is null" +
	    		" and t.assigned_date >= current_timestamp + '-30D'" +
	    		" group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby," +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
	    		" tipologia_operatore, name ,  orgsiteid,t.assigned_date," +
	    		" CASE when t.provvedimenti_prescrittivi in ( 3,5)" +
	    		" then uo.descrizione_lunga	else	uo2.descrizione_lunga	END" +
	    		" having  count (attivita.ticketid) != 0" +
	    		" order by assigned_date asc, numAttivita desc limit 100)"
	    		
		   	+ " union ( "
		   	
					+ " select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, " +
					" t.link_asset_id,t.ticketid,t.punteggio,t.alt_id as org_id,t.problem,t.entered,t.enteredby,"
					+ " t.entered + interval '1 month' as data_chiusura_ufficio," +
					" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
					" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
					" 999 as tipologia_operatore,op.ragione_sociale as name, o.id_asl AS orgsiteid," +
					" CASE when t.provvedimenti_prescrittivi in ( 3,5)" +
					" then uo.descrizione_lunga	else	uo2.descrizione_lunga	END as descrizione_lunga"
				   	+ " from ticket t "
				   	+ " left join sintesis_stabilimento o on(t.alt_id = o.alt_id) "
				   	+ " left join sintesis_operatore op on(o.id_operatore = op.id) " +
		    		" left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (15))" +
		    		" left join unita_operative_controllo uoc on uoc.id_controllo = t.ticketid" +
		    		" left join oia_nodo uo on uo.id = uoc.id_unita_operativa" +
		    		" left join tipocontrolloufficialeimprese tcui on tcui.idcontrollo = t.ticketid and tcui.tipoispezione > 0" +
		    		" left join oia_nodo uo2 on uo2.id = tcui.id_unita_operativa" +
					" where t.tipologia = 3 and t.closed is not null and t.trashed_date is null and o.trashed_date is null " +
		    		" and o.stato not in (1,3,4) and t.site_id = ?" +
		    		" and o.trashed_date is null and attivita.trashed_date is null" +
		    		" and t.assigned_date >= current_timestamp + '-30D'" +
		    		" and t.id_stabilimento is null and (t.alt_id is not null AND t.alt_id >= ? AND t.alt_id <= ?) " +
		    		" group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby," +
		    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
		    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
		    		" tipologia_operatore, name ,  orgsiteid,t.assigned_date," +
		    		" CASE when t.provvedimenti_prescrittivi in ( 3,5)" +
		    		" then uo.descrizione_lunga	else	uo2.descrizione_lunga	END" +
		    		" having  count (attivita.ticketid) != 0" +
		    		" order by assigned_date asc, numAttivita desc limit 100)");
	    		*/
	    
	    		/*sqlSelect.append(
	    		" select count (attivita.ticketid) as numAttivita,t.id_macchinetta,t.id_controllo_ufficiale, t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby,t.entered + interval '1 month' as data_chiusura_ufficio," +
	    		"t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
	    		"t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
	    		"o.tipologia as tipologia_operatore,o.name, o.site_id AS orgsiteid "+
	    		" from ticket t left join organization o on(t.org_id = o.org_id) " +
	    		" left join ticket attivita on (t.id_controllo_ufficiale=attivita.id_controllo_ufficiale and attivita.tipologia in (15)) "+
	    		" where t.tipologia =3 and t.closed is not null and t.trashed_date is null and o.trashed_date is null and " +
	    		" (o.cessato is null or o.cessato not in (1)) and (stato_lab is null or stato_lab not in (1)) and t.site_id = ? and o.trashed_date is null and attivita.trashed_date is null  "+
	    		" and t.assigned_date <= now() and t.assigned_date >= now() + '-30D' "+
	    		" group by t.id_macchinetta,t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby," +
	    		" t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
	    		" t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
	    		" tipologia_operatore,o.name ,  orgsiteid,t.assigned_date "+
	    		" having  count (attivita.ticketid) != 0 " +
	    		" order by numAttivita desc,assigned_date asc ");*/
	    pst = db.prepareStatement(sqlSelect.toString() );
	    pst.setInt(1, idasl);
		pst.setInt(2, startRangeSintesis);
		pst.setInt(3, endRangeSintesis);
	   /* pst.setInt(4, idasl);
	    pst.setInt(5, idasl);
		pst.setInt(6, startRangeSintesis);
		pst.setInt(7, endRangeSintesis);
	    */
//	    System.out.println("QUERY 6 "+pst.toString());
		rs = pst.executeQuery();
	  
	    while (rs.next()) {
	      Ticket thisTicket = new Ticket(db,rs,true);
	    /*  if (thisTicket.getTipoCampione() == 4)
	    		  thisTicket.getTipoIspezioneSelezionatoScadenz(db, thisTicket.getId());
	      else 
	    	  thisTicket.buildUnitaOperative(db);*/
	      this.add(thisTicket);
	     
	    }
	    rs.close();
	    pst.close();
	
	  }
	
	public void buildListCuChiusuraUfficio(Connection db,int enteredBy,int idasl) throws SQLException {
		
		int startRangeSintesis = DatabaseUtils.getStartRangePartizioneByTipologia(db, 6);
		int endRangeSintesis = DatabaseUtils.getEndRangePartizioneByTipologia(db, 6);
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;

		StringBuffer sql = new StringBuffer("");
		
		sql.append("select * from lista_controlli_chiusi_ufficio(?,?,?,?)");
		
		/*LISTA CONTROLLI UFFICIALI APERTI INSERITI DA UN UTENTE NON IN SORVEGLIANZA LA CUI DATA DI INSERIMENTO DI UNA SETTIMANA E MAGGIORE UGUALE ALLA DATA ODIERNA*/
		/*sql.append(	" (SELECT count(attivita.ticketid) as numAttivita,t.ticketid,t.id_controllo_ufficiale," +
					"t.id_macchinetta, t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby," +
					"t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia," +
					"t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo," +
					"o.tipologia as tipologia_operatore,o.name, o.site_id AS orgsiteid, "+
					"t.entered + interval '1 month' as data_chiusura_ufficio " +
					" FROM ticket t " +
					" left join ticket attivita on (t.id_controllo_ufficiale = attivita.id_controllo_ufficiale and attivita.trashed_Date is null and attivita.tipologia in (2,7) and attivita.closed is null) " +
					"" +
					"left join organization o on(t.org_id = o.org_id) " +
					" WHERE t.ticketid > 0 " + 
					" AND t.tipologia = 3 and t.provvedimenti_prescrittivi!=5 " +
					" and  now() >=t.entered + interval '7 days' " +
					" and t.modifiedby = ? and t.site_id = ? and o.date2 is null and o.trashed_date is null " +
					" and t.id_stabilimento is null and (t.alt_id is null OR t.alt_id < ? OR t.alt_id > ?) "+
					" and (o.cessato is null or o.cessato not in (1)) and " +
					"(stato_lab is null or stato_lab not in (1)) " +
					" and t.trashed_date is null and t.closed is null " +
					"group by " +
					" t.ticketid, " + 
					"t.id_macchinetta, t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby, " + 
					"t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, " +
					"t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, " +
					"o.tipologia ,o.name, o.site_id , " +
					"t.entered + interval '1 month' " +
					"having count(attivita.ticketid )=0"  +
					"order by t.entered ) union ( "
					
					+ " SELECT count(attivita.ticketid) as numAttivita,t.ticketid,t.id_controllo_ufficiale, t.id_macchinetta, t.link_asset_id,t.ticketid,t.punteggio,t.id_stabilimento as org_id,t.problem,t.entered,t.enteredby,"
					+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia,"
					+ " t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, "
					+ " 999 as tipologia_operatore,op.ragione_sociale as name, o.id_asl AS orgsiteid, "
					+ " t.entered + interval '1 month' as data_chiusura_ufficio "
					+ " FROM ticket t "
					+ " left join ticket attivita on (t.id_controllo_ufficiale = attivita.id_controllo_ufficiale and attivita.trashed_Date is null and attivita.tipologia in (2,7) and attivita.closed is null) "
					+ " left join opu_stabilimento o on(t.id_stabilimento = o.id) "
					+ " left join opu_operatore op on(o.id_operatore = op.id) "
					+ " WHERE t.ticketid > 0  "
					+ " AND t.tipologia = 3 and t.provvedimenti_prescrittivi!=5  and  now() >=t.entered + interval '7 days' "
					+ " and t.modifiedby =? and t.site_id = ? and o.trashed_date is null and o.stato not in (4) and t.trashed_date is null and t.closed is null "
					+ " group by t.ticketid, t.id_macchinetta, t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby,  "
					+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, "
					+ " tipologia_operatore, name ,  orgsiteid, t.entered +interval '1 month'  having count(attivita.ticketid )=0  order by t.entered )"
					
					
		   	+ " union ( "
		   	
					+ " SELECT count(attivita.ticketid) as numAttivita,t.ticketid,t.id_controllo_ufficiale, t.id_macchinetta, t.link_asset_id,t.ticketid,t.punteggio,t.alt_id as org_id,t.problem,t.entered,t.enteredby,"
					+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia,"
					+ " t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, "
					+ " 2000 as tipologia_operatore,op.ragione_sociale as name, o.id_asl AS orgsiteid, "
					+ " t.entered + interval '1 month' as data_chiusura_ufficio "
				   	+ " from ticket t "
					+ " left join ticket attivita on (t.id_controllo_ufficiale = attivita.id_controllo_ufficiale and attivita.trashed_Date is null and attivita.tipologia in (2,7) and attivita.closed is null) "
				   	+ " left join sintesis_stabilimento o on(t.alt_id = o.alt_id) "
				   	+ " left join sintesis_operatore op on(o.id_operatore = op.id) "
				   	+ " where t.ticketid > 0 "
					+ " AND t.tipologia = 3 and t.provvedimenti_prescrittivi!=5  and  now() >=t.entered + interval '7 days' "
					+ " and t.modifiedby =? and t.site_id = ? and o.trashed_date is null and o.stato not in (1,3,4) and t.trashed_date is null and t.closed is null "
					+ " and t.id_stabilimento is null and (t.alt_id is not null AND t.alt_id >= ? AND t.alt_id <= ?) "
					+ " group by t.ticketid, t.id_macchinetta, t.link_asset_id,t.ticketid,t.punteggio,t.org_id,t.problem,t.entered,t.enteredby,  "
					+ " t.modified,t.modifiedby,t.closed,t.assigned_date,t.state_id,t.site_id,t.tipologia, t.provvedimenti_prescrittivi,t.data_fine_controllo,t.data_prossimo_controllo, "
					+ " tipologia_operatore, name ,  orgsiteid, t.entered +interval '1 month'  having count(attivita.ticketid )=0  order by t.entered )");
			*/	
		try
		{
		
			pst = db.prepareStatement(sql.toString());
			pst.setInt(1, enteredBy);
			pst.setInt(2, idasl);
			pst.setInt(3, startRangeSintesis);
			pst.setInt(4, endRangeSintesis);
			/*pst.setInt(5, enteredBy);
			pst.setInt(6, idasl);
			pst.setInt(7, enteredBy);
			pst.setInt(8, idasl);
			pst.setInt(9, startRangeSintesis);
			pst.setInt(10, endRangeSintesis);*/
//			System.out.println("QUERY 3 "+pst.toString());
			rs = pst.executeQuery();
			
			while ( rs.next() )
			{
		
					Ticket thisTicket = new Ticket(db,rs,true);
					//thisTicket.setCu(db, rs);
					
						this.add(thisTicket);
					
				
			}
			rs.close();
			pst.close();

		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}
	
	  }
	
	public void buildListConcessioni(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM ticket t " +
	        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
	        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
	       "LEFT JOIN concessioni dist on (t.org_id=dist.id_zona_concessione and t.id_concessionario = dist.id_concessionario) "+
	        "WHERE t.ticketid > 0 AND t.tipologia = 3 ");
	    createFilter(sqlFilter, db);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      items = prepareFilter(pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
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
	      sqlOrder.append("ORDER BY t.assigned_date, t.ticketid ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "t.*, " +
	        
	        "o.name,o.tipologia as tipologia_operatore,"+
	        "o.name AS orgname, " +
	        "o.enabled AS orgenabled, " +
	        "o.site_id AS orgsiteid, " +
	     
	        "a.serial_number AS serialnumber, " +
	        "a.manufacturer_code AS assetmanufacturercode, " +
	        "a.vendor_code AS assetvendorcode, " +
	        "a.model_version AS modelversion, " +
	        "a.location AS assetlocation, " +
	        "a.onsite_service_model AS assetonsiteservicemodel  " +
	      
	        "FROM ticket t " +
	        
	        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
	       
	        "LEFT JOIN concessioni dist on (t.id_concessionario=dist.id_concessionario and t.org_id = dist.id_zona_concessione) "+
	        "LEFT JOIN organization o ON (dist.id_zona_concessione = o.org_id) " +
	        
	        "WHERE t.ticketid > 0 AND t.tipologia = 3 ");
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
	    while (rs.next()) {
	      Ticket thisTicket = new Ticket(db,rs);
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
	
	
	
	public void buildListMacchinette(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	        "SELECT COUNT(distinct t.ticketid) AS recordcount " +
	        "FROM ticket t " +
	        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
	        "LEFT JOIN distributori_automatici dist on (t.org_id=dist.org_id) " +
    		"LEFT JOIN controlli_punti_di_prelievo_acque_rete  pdp ON (t.ticketid = pdp.id_controllo) " + 
	        "WHERE t.ticketid > 0 AND t.tipologia = 3 ");
	    createFilter(sqlFilter, db);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      items = prepareFilter(pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
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
	      sqlOrder.append("ORDER BY t.assigned_date, t.ticketid ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "t.*, " +
	        
	        "o.name,o.tipologia as tipologia_operatore,"+
	        "o.name AS orgname, " +
	        "o.enabled AS orgenabled, " +
	        "o.site_id AS orgsiteid, " +
	       
	        "a.serial_number AS serialnumber, " +
	        "a.manufacturer_code AS assetmanufacturercode, " +
	        "a.vendor_code AS assetvendorcode, " +
	        "a.model_version AS modelversion, " +
	        "a.location AS assetlocation, " +
	        "a.onsite_service_model AS assetonsiteservicemodel , " +
	       
	        "dist.id " +
	      
	        "FROM ticket t " +
	        "LEFT JOIN controlli_punti_di_prelievo_acque_rete  pdp ON (t.ticketid = pdp.id_controllo) " +
	        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
	        
	        "LEFT JOIN distributori_automatici dist on (t.id_macchinetta=dist.id) "+
	        "LEFT JOIN organization o ON (dist.org_id = o.org_id) " +
	        
	        "WHERE t.ticketid > 0 AND t.tipologia = 3 ");
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    items = prepareFilter(pst);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
			if (System.getProperty("DEBUG") != null) {
			}
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      Ticket thisTicket = new Ticket(db,rs);
	      this.add(thisTicket);
	    }
	    rs.close();
	    pst.close();
	    //Build resources
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      Ticket thisTicket = (Ticket) i.next();
	      thisTicket.buildFiles(db);
	    
	    }
	  }
	
protected void createFilter(StringBuffer sqlFilter, Connection db) {
    
		
	 sqlFilter.append("AND t.trashed_date is null ");
	
	 if ( statusId>0)
	 {
		 if (statusId == 1)
			 sqlFilter.append( " AND t.status_id in(?, ?) ");
		 else
			 sqlFilter.append( " AND t.status_id = ? ");
 }
	 
	 if ( auditTipo>0)
	 {
		 sqlFilter.append( " AND audit_tipo= ? ");
	 }
	 
	 if ( anno>0)
	 {
		 sqlFilter.append( " AND date_part('years',assigned_date)= ? ");
	 }
	 
	 if ( tipoAudit>0)
	 {
		 sqlFilter.append( " AND provvedimenti_prescrittivi = ? ");
	 }
	 if (tipologia_struttura!=null && tipologia_struttura.length>1)
	 {
		 sqlFilter.append( " AND strutture.id_struttura in (");
		 int i = 0 ;
		 for ( i = 0 ; i <tipologia_struttura.length-1;i++)
		 {
			 sqlFilter.append( "?,");
		 
		 }
		 sqlFilter.append( "?) ");
		 
		
	 }
	 else
	 {
		 if (tipologia_struttura!= null && tipologia_struttura.length==1 && !tipologia_struttura[0].equalsIgnoreCase("-1"))
		 sqlFilter.append( " AND strutture.id_struttura =? ");
	 }

	 if (tipologiaOperatore>0)
	 {
		 sqlFilter.append( " AND o.tipologia ="+tipologiaOperatore);


	 }
	 if (mc!= null && ! "".equals(mc))
	    {

			 sqlFilter.append( " AND a.serial_number ilike ?");
	    }
	 
	 
	if (siteId > 0)
    {

		 sqlFilter.append("AND t.site_id = "+siteId);
    }
	
	if (cfproprietario!=null && ! cfproprietario.equals(""))
	{
		 sqlFilter.append("AND codice_fiscale_rappresentante ilike ? ");
	}
	
	
	if (idControllo!=null && ! idControllo.equals(""))
	{
		 sqlFilter.append("AND t.id_controllo_ufficiale ilike ? ");
	}
	
	if (dataControllo!=null )
	{
		 sqlFilter.append("AND t.assigned_date = ? ");
	}
	
	if (tipoProvvedimenti>0 )
	{
		 sqlFilter.append("AND t.provvedimenti_prescrittivi = ? ");
	}
    
    
   /* if ((idMacchinetta !=-1)) {
	      sqlFilter.append("AND dist.id = "+idMacchinetta+" ");
	}*/ 
	if (altId>0)
		sqlFilter.append("AND ( t.alt_id = ? )");
	
	    if (farmacosorveglianza == false)
	    {
	    	 if (orgId > 0 && org_id_pdp<=0 ) {
	    		sqlFilter.append("AND ( t.org_id = ? or op.id_mercato_ittico= ?)"); //sqlFilter.append("AND ( t.org_id = ? or pdp.org_id_pdp =? )");
	    	}
	    	 else
	    		 if(orgId>0 && org_id_pdp>0)
	    		 {
	    			 sqlFilter.append("AND ( t.org_id = ? )"); 
	    		 }
	    	else
	    	{
	    		if (idStabilimento>0)
	    		{
	    			sqlFilter.append("AND (t.id_stabilimento = ? or op.id_mercato_ittico= ?) ");
	    		}
	    		
	    		if (idApiario>0)
	    		{
	    			sqlFilter.append("AND t.id_apiario = ? ");
	    		}
	    	}
	    }
	    else
	    {
	    	sqlFilter.append("AND t.id_farmacia = ? ");
	    }
	   
	    if (assetId > -1) {
	      sqlFilter.append("AND t.link_asset_id = ? ");
	    }
	
	
	   
	    
	    if (idMacchinetta > 0) {
		      sqlFilter.append("AND t.id_macchinetta= ? ");
		    }
	    if (idMacchinettaNotNull) {
		      sqlFilter.append(" AND t.id_macchinetta is not null ");
		    }
//	    else
//	    {
//		      sqlFilter.append("AND (t.id_macchinetta is null or t.id_macchinetta =0 or t.id_macchinetta=-1 )");
//
//	    }
//	    if (idConcessionario > -1) {
//		      sqlFilter.append("AND dist.id_concessionario = ? ");
//		    }
	   
		 if ( idRuolo>0)
		 {
			sqlFilter.append( " AND (acc.role_id = ? or accext.role_id = ?) ");
		 }
	    
	    sqlFilter.append(" ) t ");
	  }

protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    int j = 0 ;
    
    if (statusId > 0) {
      	 if (statusId==1){
      		 pst.setInt(++i, 1);
      		 pst.setInt(++i, 3);
      	 }
      	 else {
      		 pst.setInt(++i, statusId);
	 }
 	    }
    
    if ( auditTipo>0)
	 {
    	pst.setInt(++i, auditTipo);
	 }
    
    if ( anno>0)
	 {
    	 pst.setInt(++i, anno);
	 }
    
    if ( tipoAudit>0)
	 {
		 pst.setInt(++i, tipoAudit);
	 }
    
	 if (tipologia_struttura!=null && tipologia_struttura.length>1)
	 {
		 for ( j = 0 ; j <tipologia_struttura.length-1;j++)
		 {
				pst.setInt(++i, Integer.parseInt(tipologia_struttura[j]));
		 
		 }
			pst.setInt(++i, Integer.parseInt(tipologia_struttura[j]));
		 
		
	 }
	 else
	 {
		 if (tipologia_struttura!= null && tipologia_struttura.length==1 && !tipologia_struttura[0].equalsIgnoreCase("-1"))
			pst.setInt(++i, Integer.parseInt(tipologia_struttura[0]));
	 }
    
	if (mc!=null && ! mc.equals(""))
	{
	      pst.setString( ++i, "%"+mc.trim()+"%" ); 
	}
	
	if (org_id_pdp>0)
		pst.setInt(++i, orgId);
	
	if (cfproprietario!=null && ! cfproprietario.equals(""))
	{
		pst.setString(++i,cfproprietario.trim());
	}
	
	if (idControllo!=null && ! idControllo.equals(""))
	{
		pst.setString( ++i, "%"+idControllo.trim()+"%" ); 
	}
	
	if (dataControllo!=null )
	{
		 pst.setTimestamp(++i, dataControllo);
	}
	if (tipoProvvedimenti>0 )
	{
		 pst.setInt(++i, tipoProvvedimenti);
	}
    
	if (altId> 0)
		pst.setInt(++i, altId);
   
 if (orgId > 0 && org_id_pdp <=0) {
    	pst.setInt(++i, orgId);
    	pst.setInt(++i, orgId);
    }else
    if (idStabilimento>0)
	{
        pst.setInt(++i, idStabilimento);
        pst.setInt(++i, idStabilimento);

	}
    if (idApiario>0)
  	{
          pst.setInt(++i, idApiario);
  	}
  
    
   

    if (idMacchinetta > 0) {
    	 pst.setInt(++i, idMacchinetta);
	    }
//    if (idConcessionario > -1) {
//    	 pst.setInt(++i, idConcessionario);
//	    }
   
    if ( idRuolo>0)
	 {
    	 pst.setInt(++i, idRuolo);
    	 pst.setInt(++i, idRuolo);
	 }
    
    return i;
  }
public int buildListControlliUltimiAnniAnagrafica(Connection db, int org_id,int idCu_Sorveglianza) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    int punteggio = 0;
    StringBuffer sqlPuntiMeno = new StringBuffer();
    //Need to build a base SQL statement for counting records
    
    sqlPuntiMeno.append("select * from  getpunteggioaccumulato_ct (?,'alt_id',?)");

    /*sqlPuntiMeno.append("SELECT sum (t.punteggio) as punteggioAccumulatoCT "+
	        " FROM ticket t " +
	        " JOIN ticket cu on (t.id_controllo_ufficiale = trim(to_char(cu.ticketid  , '000000' )) ) " +
	        " WHERE (t.tipologia = 3 ) and  t.id_controllo_ufficiale not in (select distinct id_controllo from audit) and t.provvedimenti_prescrittivi not in (5) "+
	       	" and t.punteggio>0 and t.closed is not null and t.id_stabilimento = ?"+
	       	" and cu.assigned_date <= (select t.assigned_date  - interval '7 days' from ticket t where ticketid = ?) and cu.assigned_date >= ( select t.assigned_date - interval '5 years' from ticket t where ticketid = ?)  ");
	       // "and t.id_controllo_ufficiale = ?  "+
     */
	      pst = db.prepareStatement(sqlPuntiMeno.toString());
	      pst.setInt(1,org_id);
	      pst.setInt(2, idCu_Sorveglianza);
      rs = pst.executeQuery();
      if (rs.next()) {
	        punteggio = (rs.getInt("getpunteggioaccumulato_ct") );
	      }
      pst.close();
      rs.close();
      /*pst2 = db.prepareStatement(sqlCamTam.toString());
      pst2.setInt(1,org_id);
      rs2 = pst2.executeQuery();
      if (rs2.next()) {
	        
    	  punteggioAgg = ( rs2.getInt("punteggioAccumulatoCT"));
	        
	      }
      pst2.close();
      rs2.close();
      pst3 = db.prepareStatement(sqlPuntiMeno.toString());
      pst3.setInt(1,org_id);
	      pst3.setInt(2, idCu_Sorveglianza);
	      pst3.setInt(3, idCu_Sorveglianza);
      //pst3.setString(2, idC);
      //pst3.setTimestamp(2, dataC);
      rs3 = pst3.executeQuery();
     
      if (rs3.next()) {
        	        
    	  punteggioSott = (rs3.getInt("punteggioAccumulatoCT"));
        
      }
      pst3.close();
      rs3.close();
      //punteggio = punteggio + (punteggioAgg - punteggioSott);
     	     
      //punteggio=punteggioSott;
      */
    return punteggio;
  }


	
}

