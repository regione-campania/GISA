/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.checklist.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.audit.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.CustomLookupElement;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

public class Audit extends GenericBean { 

	//campi privati
	private int id = -1;
	private int orgId = -1;
	private int livelloRischio;
	private int livelloRischioFinale = -1;
	private int punteggioUltimiAnni = 0;
	private String numeroRegistrazione = null;
	private String componentiGruppo = null;
	private String note = null;
	private java.sql.Timestamp data1 = null;
	private java.sql.Timestamp data2 = null;
	private String idControllo = null;
	private boolean aggiornaCategoria = false;
	private java.sql.Timestamp dataProssimoControllo = null;
	private boolean isPrincipale = false ;
	private String stato ;
	private String link_action_checklist ;
	
	
	public String getLink_action_checklist() {
		return link_action_checklist;
	}

	public void setLink_action_checklist(String link_action_checklist) {
		this.link_action_checklist = link_action_checklist;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public boolean isPrincipale() {
		return isPrincipale;
	}

	public void setPrincipale(boolean isPrincipale) {
		this.isPrincipale = isPrincipale;
	}

	public boolean isAggiornaCategoria() {
		return aggiornaCategoria;
	}

	public void setAggiornaCategoria(boolean aggiornaCategoria) {
		this.aggiornaCategoria = aggiornaCategoria;
	}

	// sta&Dam
	private int tipoChecklist = -1;
	//
	
	private Integer categoria=-1;
	private Integer categoria_precedente=-1;
	
	public Timestamp getDataProssimoControllo() {
		return dataProssimoControllo;
	}

	public void setDataProssimoControllo(Timestamp dataProssimoControllo) {
		this.dataProssimoControllo = dataProssimoControllo;
	}
	
	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}
	
	public Integer getCategoria_precedente() {
		return categoria_precedente;
	}

	public void setCategoria_precedente(Integer categoria_precedente) {
		this.categoria_precedente = categoria_precedente;
	}

	//metodi get e set
	public int getId() {
		return id;
	}

	public String getIdControllo() {
		return idControllo;
	}

	public void setIdControllo(String idControllo) {
		this.idControllo = idControllo;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = Integer.parseInt( orgId );
	}

	public int getLivelloRischio() {
		return livelloRischio;
	}

	public void setLivelloRischio(int livelloRischio) {
		this.livelloRischio = livelloRischio;
	}
	
	public int getPunteggioUltimiAnni() {
		return punteggioUltimiAnni;
	}

	public void setPunteggioUltimiAnni(int punteggioUltimiAnni) {
		this.punteggioUltimiAnni = punteggioUltimiAnni;
	}
	
	
	public int getLivelloRischioFinale() {
		return livelloRischioFinale;
	}

	public void setLivelloRischioFinale(int livelloRischioFinale) {
		this.livelloRischioFinale = livelloRischioFinale;
	}
	
	public void setPunteggioUltimiAnni(String livelloRischio) {
		this.punteggioUltimiAnni = Integer.parseInt(livelloRischio);
	}
	
	public void setLivelloRischio(String punteggioUltimiAnni) {
		this.livelloRischio = Integer.parseInt(punteggioUltimiAnni);
	}

	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}

	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}

	public String getComponentiGruppo() {
		return componentiGruppo;
	}

	public void setComponentiGruppo(String componentiGruppo) {
		this.componentiGruppo = componentiGruppo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public java.sql.Timestamp getData1() {
		return data1;
	}

	public void setData1(java.sql.Timestamp data1) {
		this.data1 = data1;
	}
	public void setData1(String tmp) {
	    this.data1 = DatabaseUtils.parseDateToTimestamp(tmp);
	}

	public java.sql.Timestamp getData2() {
		return data2;
	}

	public void setData2(java.sql.Timestamp data2) {
		this.data2 = data2;
	}
	
	public void setDataProssimoControllo(String tmp) {
	    this.dataProssimoControllo = DatabaseUtils.parseDateToTimestamp(tmp);
	}

	public void setData2(String tmp) {
	    this.data2 = DatabaseUtils.parseDateToTimestamp(tmp);
	}
	
	//costruttore
	public Audit(Connection db, String id) throws SQLException {
	  errors.clear();
	  queryRecord(db, Integer.parseInt(id));
	}
	
	public Audit() {
		
	}
	
	public void queryRecord(Connection db, int tmpAuditId) throws SQLException {
	  PreparedStatement pst = null;
	  ResultSet rs = null;
	  pst = db.prepareStatement("SELECT * FROM audit WHERE audit.id = ? ");
	  pst.setInt(1, tmpAuditId);
	  rs = pst.executeQuery();
	  if (rs.next()) {
	    buildRecord(rs);
	  }
	  rs.close();
	  pst.close();
	}
	
	void buildRecord(ResultSet rs) throws SQLException {
	  //audit table
      id = rs.getInt("id");
      dataProssimoControllo = rs.getTimestamp("data_prossimo_controllo");
      categoria=rs.getInt("categoria");
      isPrincipale = rs.getBoolean("is_principale");
      orgId = DatabaseUtils.getInt(rs, "org_id");
      try
      {
      idStabilimento = rs.getInt("id_stabilimento");
      }
      catch(PSQLException e)
      {
    	  
      }
      try
      {
      idApiario = rs.getInt("id_apiario");
      }
      catch(PSQLException e)
      {
    	  
      }
	  livelloRischio = DatabaseUtils.getInt(rs, "livello_rischio");
	  livelloRischioFinale = DatabaseUtils.getInt(rs, "livello_rischio_finale");
	  numeroRegistrazione = rs.getString("numero_registrazione");
	  componentiGruppo = rs.getString("componenti_gruppo");
	  note = rs.getString("note");
	  data1 = rs.getTimestamp("data_1");
	  stato = rs.getString("stato");
	  data2 = rs.getTimestamp("data_2");
	  tipoChecklist = rs.getInt("tipo_check");
	  punteggioUltimiAnni = rs.getInt("punteggio_ultimi_anni");
	  idControllo = rs.getString("id_controllo");
	 idLastDomanda=rs.getString("last");
	}
	
	private String idLastDomanda="";
	
	
	
	
	public String getIdLastDomanda() {
		return idLastDomanda;
	}

	public void setIdLastDomanda(String idLastDomanda) {
		this.idLastDomanda = idLastDomanda;
	}

	public boolean verificaEsistenzaChecklist(Connection db,String idControllo,int tipoChecklist)
	{
		boolean ret = false ;
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{
			pst = db.prepareStatement("select * from audit where tipo_check = ? and id_controllo = ? and trashed_Date is null ");
			pst.setInt(1, tipoChecklist);
			pst.setString(2,idControllo);
			rs = pst.executeQuery() ;
			if (rs.next())
			{
				ret = true ;
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return ret ;
	}
	
	public boolean insert(ActionContext context ,Connection db, ArrayList risposte, ArrayList valoreRange, ArrayList operazione, ArrayList nota, int p,ArrayList paragrafiabilitati) throws SQLException {
      StringBuffer sql = new StringBuffer();
	  boolean doCommit = false;
		
	  try {
        if (doCommit = db.getAutoCommit()) {
	      db.setAutoCommit(false);
		}
        
        
        //inserimento dati nella tabella audit
        id = DatabaseUtils.getNextSeq(db, context,"audit","id");
		sql.append("INSERT INTO audit (org_id, numero_registrazione,is_principale, stato,");
		if (componentiGruppo != null) {sql.append("componenti_gruppo, ");}
		sql.append(" id_controllo,");
		if (note != null) {sql.append("note, ");}
		if (p != -1) {sql.append("punteggio_ultimi_anni, ");}
		if (idStabilimento>0) {sql.append("id_stabilimento, ");}
		if (idApiario>0) {sql.append("id_apiario, ");}
		if (data1 != null) {sql.append("data_1, ");}
		if (data2 != null) {sql.append("data_2, ");}
		if (idLastDomanda != null) {sql.append("last, ");}
		sql.append("livello_rischio, ");
		sql.append("livello_rischio_finale,tipo_check) ");
		sql.append("VALUES (?,?,?,?,");
		if (componentiGruppo != null) {sql.append("?, ");}
		sql.append("?, ");
		if (note != null) {sql.append("?, ");}
		if (p != -1) {sql.append("?, ");}
		if (idStabilimento>0) {sql.append("id_stabilimento, ");}
		if (idApiario>0) {sql.append("id_apiario, ");}
		if (data1 != null) {sql.append("?, ");}
		if (data2 != null) {sql.append("?, ");}	
		
		if(idLastDomanda!=null){sql.append("?, ");}
		
		sql.append("?, ?, ? ) ");  //aggiunto un ?
		
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setInt(++i, this.getOrgId());
		pst.setString(++i, this.getNumeroRegistrazione());
		

		pst.setBoolean(++i, isPrincipale);
		pst.setString(++i, this.getStato());
        if (componentiGruppo != null) {pst.setString(++i, this.getComponentiGruppo());}
        pst.setString(++i, idControllo);
	    if (note != null) {pst.setString(++i, this.getNote());}
	    if (p != -1) {pst.setInt(++i, p);}
	    if (idStabilimento>0) {pst.setInt(++i, idStabilimento);}
	    if (idApiario>0) {pst.setInt(++i, idApiario);}
		if (data1 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData1());}
		if (data2 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData2());}	
		if(idLastDomanda!=null){pst.setString(++i, idLastDomanda);}
		
		pst.setInt(++i, this.livelloRischio);
		pst.setInt(++i, this.livelloRischioFinale);
		pst.setInt(++i, this.tipoChecklist); //aggiunta questa riga
		pst.execute();
		pst.close();
		
		
		//inserimento dati nella tabella audit_checklist
		Iterator itrChecklist = risposte.iterator();
		while(itrChecklist.hasNext()){
		  Parameter paramRisp = (Parameter) itrChecklist.next();

		  //il parametro vale 1 in caso di risposta affermativa, vale 0 in caso di risposta negativa
		  boolean risposta = ((paramRisp.getValore())!=null  ? paramRisp.getValore().equals("1") ? true : false : null);
	
		  //prendo il punteggio della risposta dalla tabella checklist
		  CustomLookupElement selectElement = new CustomLookupElement(db, paramRisp.getId(), "checklist", "id", "*");
		  String field = risposta ? "punti_si" : "punti_no";
		  int punti = org.aspcfs.utils.StringUtils.parseInt(selectElement.getValue(field), -1);
			  
		  AuditChecklist auditChecklist = new AuditChecklist();
		  auditChecklist.setChecklistId(paramRisp.getId());
		  auditChecklist.setAuditId(this.id);
		  auditChecklist.setRisposta(risposta);
		  auditChecklist.setPunti(punti);
		  auditChecklist.insert(db);
		}
		
		//inserimento dati nella tabella audit_checklist_type
	    Iterator itrValoreRange = valoreRange.iterator();
	 
	    Iterator itrOperazione = operazione.iterator();
	    //Iterator itrNota = nota.iterator();
	    Iterator itrParagrafiabilitati = paragrafiabilitati.iterator();
	    
			while(itrValoreRange.hasNext()){
		  Parameter paramValoreRange = (Parameter) itrValoreRange.next();
		  Parameter paramOperazione = (Parameter) itrOperazione.next();
		  //Parameter paramNota = (Parameter) itrNota.next();
		  Parameter paramCapitolo = null;
		  if(itrParagrafiabilitati.hasNext())
		  {
		   paramCapitolo = (Parameter) itrParagrafiabilitati.next();
		}
		  
		  
		  AuditChecklistType auditChecklistType = new AuditChecklistType();
		  auditChecklistType.setChecklistTypeId(paramValoreRange.getId());
		

		  
		  
		  auditChecklistType.setAuditId(this.id);
		  auditChecklistType.setValoreRange(Integer.valueOf(paramValoreRange.getValore().equals("") ? "0" : paramValoreRange.getValore()));
		  auditChecklistType.setOperazione(paramOperazione.getValore());
		  //auditChecklistType.setNota(paramNota.getValore());
		  if(paramCapitolo != null)
		  {
			  	String isabilitato =  paramCapitolo.getValore();
		if(isabilitato!=null)
		{
			if(isabilitato.equals("1"))
			{
				auditChecklistType.setIs_abilitato(true);
			}
			else
			{
				if(isabilitato.equals("2"))
				{
					auditChecklistType.setIs_abilitato(false);
				}
			
			}
		}
		else
		{
			auditChecklistType.setIs_abilitato(true);
		}
		  
		  }
		  else
		  {
			  auditChecklistType.setIs_abilitato(false);
		  }
		  
		  auditChecklistType.insert(db);
		}
	

		
		if (doCommit) {
		  db.commit();
	    }
	  } catch (SQLException e) {
		  e.printStackTrace();
		if (doCommit) {
		  db.rollback();
		}
	    throw new SQLException(e.getMessage());
	  } finally {
		if (doCommit) {
		  db.setAutoCommit(true);
		}
	  }
	  return true;
	}
	
	public boolean insert_su_apertura(ActionContext context,Connection db, ArrayList risposte, ArrayList capitoli ,int p) throws SQLException {
	      StringBuffer sql = new StringBuffer();
		  boolean doCommit = false;
			
		  try {
	        if (doCommit = db.getAutoCommit()) {
		      db.setAutoCommit(false);
			}
	        //inserimento dati nella tabella audit
	        id = DatabaseUtils.getNextSeq(db, context,"audit","id");
			sql.append("INSERT INTO audit (id,org_id, numero_registrazione,is_principale,stato,trashed_date,");
			if (componentiGruppo != null) {sql.append("componenti_gruppo, ");}
			sql.append(" id_controllo,");
			if (note != null) {sql.append("note, ");}
			if (p != -1) {sql.append("punteggio_ultimi_anni, ");}
			if (idStabilimento>0) {sql.append("id_stabilimento, ");}
			if (idApiario>0) {sql.append("id_apiario, ");}
			if (data1 != null) {sql.append("data_1, ");}
			if (data2 != null) {sql.append("data_2, ");}
			if (idLastDomanda != null) {sql.append("last, ");}
			sql.append("livello_rischio, ");
			sql.append("livello_rischio_finale,tipo_check) ");
			sql.append("VALUES (?,?,?,?,?,current_date,");
			if (componentiGruppo != null) {sql.append("?, ");}
			sql.append("?, ");
			if (note != null) {sql.append("?, ");}
			if (p != -1) {sql.append("?, ");}
			if (idStabilimento>0) {sql.append("?, ");}
			if (idApiario>0) {sql.append("?, ");}
			if (data1 != null) {sql.append("?, ");}
			if (data2 != null) {sql.append("?, ");}	
			
			if(idLastDomanda!=null){sql.append("?, ");}
			
			sql.append("?, ?, ? ) ");  //aggiunto un ?
			
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, id);
			pst.setInt(++i, this.getOrgId());
			pst.setString(++i, this.getNumeroRegistrazione());
			pst.setBoolean(++i, isPrincipale);
			pst.setString(++i, stato);
	        if (componentiGruppo != null) {pst.setString(++i, this.getComponentiGruppo());}
	        pst.setString(++i, idControllo);
		    if (note != null) {pst.setString(++i, this.getNote());}
		    if (p != -1) {pst.setInt(++i, p);}
		    if (idStabilimento>0) {pst.setInt(++i,idStabilimento);}
		    if (idApiario>0) {pst.setInt(++i,idApiario);}
			if (data1 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData1());}
			if (data2 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData2());}	
			if(idLastDomanda!=null){pst.setString(++i, idLastDomanda);}
			
			pst.setInt(++i, this.livelloRischio);
			pst.setInt(++i, this.livelloRischioFinale);
			pst.setInt(++i, this.tipoChecklist); //aggiunta questa riga
			pst.execute();
			pst.close();
			
			
			//inserimento dati nella tabella audit_checklist
			Iterator itrChecklist = risposte.iterator();
			while(itrChecklist.hasNext()){
			  Parameter paramRisp = (Parameter) itrChecklist.next();

			  //il parametro vale 1 in caso di risposta affermativa, vale 0 in caso di risposta negativa
			  //boolean risposta = ((paramRisp.getValore())!=null  ? paramRisp.getValore().equals("1") ? true : false : null);
		
			  //prendo il punteggio della risposta dalla tabella checklist
			  CustomLookupElement selectElement = new CustomLookupElement(db, paramRisp.getId(), "checklist", "id", "*");
			  //String field = risposta ? "punti_si" : "punti_no";
			 // int punti = org.aspcfs.utils.StringUtils.parseInt(selectElement.getValue(field), -1);
				  
			  AuditChecklist auditChecklist = new AuditChecklist();
			  auditChecklist.setChecklistId(paramRisp.getId());
			  auditChecklist.setAuditId(this.id);
			  auditChecklist.setStato("NON RISPOSTA");
			  //auditChecklist.setRisposta(risposta);
			  //auditChecklist.setPunti(punti);
			  auditChecklist.insert(db);
			}
			
			//inserimento dati nella tabella audit_checklist_type
		    Iterator itrValoreRange = capitoli.iterator();
		 
				while(itrValoreRange.hasNext()){
			  Parameter paramValoreRange = (Parameter) itrValoreRange.next();
			  
			  AuditChecklistType auditChecklistType = new AuditChecklistType();
			  auditChecklistType.setChecklistTypeId(paramValoreRange.getId());
						  
			  
			  auditChecklistType.setAuditId(this.id);
			  auditChecklistType.setValoreRange(Integer.valueOf(paramValoreRange.getValore().equals("") ? "0" : paramValoreRange.getValore()));
			  auditChecklistType.setOperazione("");
			  auditChecklistType.setNota("");
			  
			  auditChecklistType.insert(db);
			}
		

			
			if (doCommit) {
			  db.commit();
		    }
		  } catch (SQLException e) {
			  e.printStackTrace();
			if (doCommit) {
			  db.rollback();
			}
		    throw new SQLException(e.getMessage());
		  } finally {
			if (doCommit) {
			  db.setAutoCommit(true);
			}
		  }
		  return true;
		}
	
	//metodo modifica risposte
	private void modifica(Connection db, ArrayList risposte, ArrayList valoreRange, ArrayList operazione, ArrayList nota){
		  StringBuffer sql = new StringBuffer();
		  boolean doCommit = false;
			
		  try {
	        if (doCommit = db.getAutoCommit()) {
		      db.setAutoCommit(false);
	        }
			
			//inserimento dati nella tabella audit_checklist
			Iterator itrChecklist = risposte.iterator();
			while(itrChecklist.hasNext()){
			  Parameter paramRisp = (Parameter) itrChecklist.next();

			  //il parametro vale 1 in caso di risposta affermativa, vale 0 in caso di risposta negativa
			  boolean risposta = paramRisp.getValore().equals("1") ? true : false;
		
			  //prendo il punteggio della risposta dalla tabella checklist
			  CustomLookupElement selectElement = new CustomLookupElement(db, paramRisp.getId(), "checklist", "id", "*");
			  String field = risposta ? "punti_si" : "punti_no";
			  int punti = org.aspcfs.utils.StringUtils.parseInt(selectElement.getValue(field), -1);
				  
			  AuditChecklist auditChecklist = new AuditChecklist();
			  auditChecklist.setChecklistId(paramRisp.getId());
			  auditChecklist.setAuditId(this.id);
			  auditChecklist.setRisposta(risposta);
			  auditChecklist.setPunti(punti);
			  auditChecklist.update(db, true);
			}
			
			//inserimento dati nella tabella audit_checklist_type
		    Iterator itrValoreRange = valoreRange.iterator();
		    Iterator itrOperazione = operazione.iterator();
		    Iterator itrNota = nota.iterator();
			while(itrValoreRange.hasNext()){
			  Parameter paramValoreRange = (Parameter) itrValoreRange.next();
			  Parameter paramOperazione = (Parameter) itrOperazione.next();
			  Parameter paramNota = (Parameter) itrNota.next();
		  
			  AuditChecklistType auditChecklistType = new AuditChecklistType();
			  auditChecklistType.setChecklistTypeId(paramValoreRange.getId());
			  auditChecklistType.setAuditId(this.id);
			  auditChecklistType.setValoreRange(Integer.valueOf(paramValoreRange.getValore().equals("") ? "0" : paramValoreRange.getValore()));
			  auditChecklistType.setOperazione(paramOperazione.getValore());
			  auditChecklistType.setNota(paramNota.getValore());
			  auditChecklistType.update(db, true);
			}
		
			this.update(db, true);
			
			if (doCommit) {
			  db.commit();
		    }
		  } catch (SQLException e) {
		  }
	}
	
	public int update(Connection db) throws SQLException {
	  int i = -1;
	  boolean doCommit = false;
	  try {
	    if (doCommit = db.getAutoCommit()) {
	      db.setAutoCommit(false);
	    }
	    i = this.update(db, false);
  
		if (doCommit) {
		  db.commit();
		}
	  } catch (SQLException e) {
	    if (doCommit) {
	      db.rollback();
	    }
	    throw new SQLException(e.getMessage());
	  } finally {
	    if (doCommit) {
		  db.setAutoCommit(true);
		}
	  }
	  return i;
	}
	
	public void updatePunteggio(Connection db, int punti, Timestamp data_prossimo_controllo, int idAudit) throws SQLException {
		  int resultCount = 0;
		  	  
		  PreparedStatement pst = null;
		  StringBuffer sql = new StringBuffer();
		  sql.append("UPDATE audit SET data_prossimo_controllo = ?, livello_rischio = ?, livello_rischio_finale = ? WHERE id = ? "); //aggiunta una condizione
	      int i = 0;
		  pst = db.prepareStatement(sql.toString());
		  DatabaseUtils.setTimestamp(pst, ++i, data_prossimo_controllo);
		  pst.setInt(++i, punti);
		  pst.setInt(++i, punti);
		  pst.setInt(++i, idAudit);
		  
	      resultCount = pst.executeUpdate();
		  pst.close();
		  	  
	}
	
	public int getPunteggioTotaleChecklistInCU(Connection db)
	{
		int punteggioTotale = 0 ;
		try
		{
			PreparedStatement 	pst = db.prepareStatement("SELECT SUM (livello_rischio) from audit where id_controllo = ? and trashed_date is null ") ;
			
			pst.setString(1,idControllo );
			ResultSet 			rs 	= pst.executeQuery();
			if(rs.next())
			{
				punteggioTotale = rs.getInt(1)	;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}
		
		return punteggioTotale ;
	}
	
	public int update(Connection db, boolean override, int livelloRischio) throws SQLException {
		  int resultCount = 0;
		  	  
		  PreparedStatement pst = null;
		  StringBuffer sql = new StringBuffer();
		  sql.append("UPDATE audit SET "); 
		  if (numeroRegistrazione != null) {sql.append("numero_registrazione = ?, ");}
		  if (componentiGruppo != null) {sql.append("componenti_gruppo = ?, ");}
		  sql.append("id_controllo = ?, ");
		  if (note != null) {sql.append("note = ?, ");} 
		  if (punteggioUltimiAnni != -1) {sql.append("punteggioUltimiAnni = ?, ");}  
		  if (data1 != null) {sql.append("data_1 = ?, ");}
		  if (data2 != null) {sql.append("data_2 = ?, ");}
		  if (categoria != null && categoria!=0) {sql.append("categoria = ?, ");}
		  if (dataProssimoControllo != null) {sql.append("data_prossimo_controllo = ?, ");}
		  sql.append("livello_rischio = ?, livello_rischio_finale = ? WHERE id = ? "); //aggiunta una condizione
	      int i = 0;
		  pst = db.prepareStatement(sql.toString());
		  if (numeroRegistrazione != null) {pst.setString(++i, this.getNumeroRegistrazione());}
	      if (componentiGruppo != null) {pst.setString(++i, this.getComponentiGruppo());}
	      pst.setString(++i, this.getIdControllo());
		  if (note != null) {pst.setString(++i, this.getNote());}
		  if (punteggioUltimiAnni != -1) {pst.setInt(++i, this.getPunteggioUltimiAnni());}
	      if (data1 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData1());}
		  if (data2 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData2());}
		  
		  if (categoria != null && categoria!=0) {pst.setInt(++i, categoria);}
		  if (dataProssimoControllo != null) {DatabaseUtils.setTimestamp(pst, ++i, dataProssimoControllo);}
		  
		  pst.setInt(++i, livelloRischio);
		  pst.setInt(++i, livelloRischioFinale);
	//	  pst.setInt(++i, tipoChecklist); //aggiunto questo rigo
		  pst.setInt(++i, id);
		  
	      resultCount = pst.executeUpdate();
		  pst.close();
		  	  
		return resultCount;
	}
	
	public int update(Connection db, boolean override) throws SQLException {
	  int resultCount = 0;
	  	  
	  PreparedStatement pst = null;
	  StringBuffer sql = new StringBuffer();
	  sql.append("UPDATE audit SET "); 
	  if (numeroRegistrazione != null) {sql.append("numero_registrazione = ?, ");}
	  if (componentiGruppo != null) {sql.append("componenti_gruppo = ?, ");}
	  if (note != null) {sql.append("note = ?, ");}  
	  if (data1 != null) {sql.append("data_1 = ?, ");}
	  if (data2 != null) {sql.append("data_2 = ?, ");}
	  if (dataProssimoControllo != null) {sql.append("data_prossimo_controllo = ?, ");}
	  sql.append("livello_rischio = ? WHERE id = ? ");
      int i = 0;
	  pst = db.prepareStatement(sql.toString());
	  if (numeroRegistrazione != null) {pst.setString(++i, this.getNumeroRegistrazione());}
      if (componentiGruppo != null) {pst.setString(++i, this.getComponentiGruppo());}
	  if (note != null) {pst.setString(++i, this.getNote());}
      if (data1 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData1());}
	  if (data2 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData2());}
	
	  if (dataProssimoControllo != null) {DatabaseUtils.setTimestamp(pst, ++i, dataProssimoControllo);}
	  
	  
	  pst.setInt(++i, livelloRischio);
	
	 // pst.setInt(++i, tipoChecklist); //aggiunto questo rigo
	  
	  pst.setInt(++i, id);
      resultCount = pst.executeUpdate();
      if (categoria != null && categoria!=0) {
    	  
      }
	  pst.close();
	  	  
	return resultCount;
    }
	
	public int buildListControlliPiuCheckList(Connection db, int org_id, String idC) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;


	    PreparedStatement pst2 = null;
	    ResultSet rs2 = null;
	    
	    PreparedStatement pst3 = null;
	    ResultSet rs3 = null;

	    int items = -1;
	    int punteggio = 0;
	    int punteggioAgg = 0;
	    int punteggioSott = 0;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCamTam = new StringBuffer();
	    StringBuffer sqlPuntiMeno = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	    		"SELECT SUM(a.livello_rischio_finale)AS punteggioAccumulato " +
		        "FROM audit a " +
		        "LEFT JOIN ticket t  on (a.id_controllo = trim(to_char(t.ticketid  , '000000' ))) " +
		        "WHERE t.tipologia = 3 " +
		        "and a.org_id = ? " + 
		        "and a.id_controllo = ? "); 
	     
 	      pst = db.prepareStatement(sqlCount.toString());
 	      pst.setInt(1, org_id);
 	      pst.setString(2, idC);
	      rs = pst.executeQuery();
	      if (rs.next()) {
		        punteggio = (rs.getInt("punteggioAccumulato") );
		      }
	      pst.close();
	      rs.close();
	      	     	      
	    return punteggio;
	  }
	
	public int updateLivelloFinale(Connection db) throws SQLException {
		  int resultCount = -1;
		  	  
		  PreparedStatement pst = null;
		  ResultSet rs = null;
		  StringBuffer sql = new StringBuffer();
		  
		  sql.append("select sum( x.livello_rischio ) as somma " +
		  				"from (" +
		  				" select b.livello_rischio from " +
		  				" audit b where b.org_id = ? " +"and id_controllo='"+idControllo+"'"+
		  				" and b.livello_rischio_finale >= -1 " +
		  				//"and b.data_1 = ( select max(a.data_1) from audit a " +
		  				//" where a.org_id = b.org_id and a.livello_rischio_finale >= -1 ) " +
		  				"order by b.tipo_check, b.id desc )x "); 
	      int i = 0;
		  pst = db.prepareStatement(sql.toString());
		  
		  pst.setInt(++i, this.getOrgId());
		  rs = pst.executeQuery();
		  if(rs.next()){
			  resultCount = rs.getInt("somma");
		  }
		  
	      
		  pst.close();
		  	  
		return resultCount;
	}
	
	
	public void setPunteggioCalcolatoChecklist(Connection db,int punti) 
	{
		
		try
		{
		String updatePunti = "update audit set livello_rischio = (punteggio_ultimi_anni + ?::int) where id = ?" ;
		PreparedStatement pst = db.prepareStatement(updatePunti);
		pst.setInt(1, punti);
		pst.setInt(2, this.id);	
		pst.execute();
		}
		catch(Exception e)
		{
				System.out.println("Errore in Aggiornamento nel Calcolo del Punteggio della Checklist ");
		}
	}
	
	
	public int getPunteggioChecklist(Connection db , int idChecklist)
	{
		
		int punti = 0 ;
		try
		{
			ResultSet rs = null ;
			PreparedStatement pst = null ;
			String sumPuntiQry = "select sum(punti) from audit_checklist where audit_id = ? ";
			String puntiCapitoli = "select valore_range,operazione from audit_checklist_type where audit_id = ? ";
			
			pst = db.prepareStatement(sumPuntiQry);
			pst.setInt(1, idChecklist);
			rs = pst.executeQuery();
			if (rs.next())
			{
				punti = rs.getInt(1);
			}
			pst = db.prepareStatement(puntiCapitoli);
			pst.setInt(1, idChecklist);
			rs = pst.executeQuery();
			while (rs.next())
			{
				String operazione = rs.getString("operazione");
				int range = rs.getInt("valore_range");
				if ("-".equalsIgnoreCase(operazione) && range >0)
				{
					punti -= range ;
				}
				if (("+".equalsIgnoreCase(operazione) ||"x".equalsIgnoreCase(operazione))  )
				{
					punti += range ;
				}
			}
			
		}catch(Exception e)
		{
			System.out.println("erorre nel calcolo del pun teggio della checkjlist");
			e.printStackTrace();
		}
		
		return punti ;
	}
	
	
	public int update(Connection db, ArrayList risposte, ArrayList valoreRange, ArrayList operazione, ArrayList nota, int p,ArrayList paragrafiabilitati) throws SQLException {
		  int resultCount = 0;
		  	  
		  PreparedStatement pst = null;
		  StringBuffer sql = new StringBuffer();
		  db.setAutoCommit(false);
		  try
		  {
			  
			   
		  sql.append("UPDATE audit SET "); 
		  if (numeroRegistrazione != null) {sql.append("numero_registrazione = ?,trashed_Date=null, ");}
		  if (componentiGruppo != null) {sql.append("componenti_gruppo = ?, ");}
		  if (note != null) {sql.append("note = ?, ");}
		  if (p != -1) {sql.append("punteggio_ultimi_anni = ?, ");}  
		  if (data1 != null) {sql.append("data_1 = ?, ");}
		  if (data2 != null) {sql.append("data_2 = ?, ");}
		  if (stato!=null) {sql.append("stato = ?, ");}
		  if (idLastDomanda != null) {sql.append("last = ?, ");}
		  sql.append("livello_rischio = ? WHERE id = ? ");
	      int i = 0;
		  pst = db.prepareStatement(sql.toString());
		  if (numeroRegistrazione != null) {pst.setString(++i, this.getNumeroRegistrazione());}
	      if (componentiGruppo != null) {pst.setString(++i, this.getComponentiGruppo());}
		  if (note != null) {pst.setString(++i, this.getNote());}
		  if (p != -1) {pst.setInt(++i, p);}	
	      if (data1 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData1());}
		  if (data2 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData2());}
		  if (stato!=null) {pst.setString(++i, stato);}
		  if (idLastDomanda != null) {pst.setString(++i, idLastDomanda);}
		  //if (data2 != null) {DatabaseUtils.setTimestamp(pst, ++i, this.getData2());}
		  pst.setInt(++i, livelloRischio);
		  
		//  pst.setInt(++i, tipoChecklist); //aggiunto questo rigo
		  
		  pst.setInt(++i, id);
		  Iterator risposteNuove = risposte.iterator();
	     // ArrayList<AuditChecklist> risposteVecchieTmp		= AuditChecklist.queryRecord( db, this.id );
	      Hashtable<String, AuditChecklist> risposteVecchie	= new Hashtable<String, AuditChecklist>();
	            int punti= 0;
	      /*for( AuditChecklist temp: risposteVecchieTmp )
	      {
	    	  risposteVecchie.put( temp.getChecklistId() + "", temp );
	      }*/
	      
			while(risposteNuove.hasNext())
			{
			  Parameter paramRisp = (Parameter) risposteNuove.next();
			  
			  //il parametro vale 1 in caso di risposta affermativa, vale 0 in caso di risposta negativa
			  boolean risposta = paramRisp.getValore().equals("1") ? true : false;
		
			  //prendo il punteggio della risposta dalla tabella checklist
			  CustomLookupElement selectElement = new CustomLookupElement(db, paramRisp.getId(), "checklist", "id", "*");
			  String field = risposta ? "punti_si" : "punti_no";
			  punti = org.aspcfs.utils.StringUtils.parseInt(selectElement.getValue(field), -1);
			
			  AuditChecklist auditChecklist = new AuditChecklist();
			  auditChecklist.setChecklistId(paramRisp.getId());
			  auditChecklist.setAuditId(this.id);
			  auditChecklist.setRisposta(risposta);
			  auditChecklist.setStato("RISPOSTA");
			  auditChecklist.setPunti(punti);
			  
			  
			 /* if( risposteVecchie.get( paramRisp.getId() + "" ) == null )
			  {
				  auditChecklist.insert_modify( db );
			  }
			  else
				  
			  {*/
				  //risposteVecchie.remove( paramRisp.getId() + "" );
				  auditChecklist.update(db, true);
			  //}
			}
		
		//aggiorna seconda nota e range
		Iterator rangeNuovo = valoreRange.iterator();
		Iterator opNuova = operazione.iterator();
		Iterator notaNuova = nota.iterator();
	    ArrayList<AuditChecklistType> rangeVecchioTmp		= AuditChecklistType.queryRecord( db, this.id );
	    Hashtable<String, AuditChecklistType> rangeVecchio	= new Hashtable<String, AuditChecklistType>();
		int auditTemp = -1;
	    for(AuditChecklistType temp: rangeVecchioTmp)
	    {
	    	//rangeVecchio.put( temp.getChecklistTypeId() + "", temp );
	    	rangeVecchio.put( temp.getAuditId() + "", temp );
	    	auditTemp = temp.getAuditId();
	    }
	  
		  Iterator itrParagrafiabilitati = paragrafiabilitati.iterator();
	    while( rangeNuovo.hasNext() )
	    {
	      Parameter paramCapitolo = (Parameter) itrParagrafiabilitati.next();
		  Parameter paramRisp = (Parameter) rangeNuovo.next();
		  Parameter paramOp = (Parameter) opNuova.next();
		  Parameter not =null;
		if(notaNuova!=null && notaNuova.hasNext())
			   not = (Parameter) notaNuova.next();
		  int valoreR = org.aspcfs.utils.StringUtils.parseInt(paramRisp.getValore(), -1);
		  String op = paramOp.getValore();
		  String notaN ="";
		  if(not!=null)
		  notaN=not.getValore();
		  
		  CustomLookupElement selectElement = new CustomLookupElement(db, auditTemp, "audit_checklist_type", "audit_id", "*");
		  
		  AuditChecklistType auditChecklistTp = new AuditChecklistType();
		  String audit = "audit_id";
		  String isabilitato =  paramCapitolo.getValore();
			if(isabilitato!=null)
			{
			if(isabilitato.equals("1"))
			{
				auditChecklistTp.setIs_abilitato(true);
			}
			else
			{
				if(isabilitato.equals("2"))
				{
					auditChecklistTp.setIs_abilitato(false);
				}
				
			}
			}
			else
			{
				auditChecklistTp.setIs_abilitato(true);
			}
		  //int punti = org.aspcfs.utils.StringUtils.parseInt(selectElement.getValue(field), -1);
		  int auditId = org.aspcfs.utils.StringUtils.parseInt(selectElement.getValue(audit), -1);
		 
		  //updatePunteggio(db, punti, dataProssimoControllo, auditId)	 ;
		  
		  if(auditId == auditTemp){
		 		  
		  auditChecklistTp.setChecklistTypeId( paramRisp.getId() );
		  auditChecklistTp.setAuditId( auditId );
		  auditChecklistTp.setValoreRange( valoreR );
		  auditChecklistTp.setOperazione( op );
		  auditChecklistTp.setNota( notaN );
		  
		  auditChecklistTp.update(db, true);
		  
		  }
		  else
		  {
			  auditChecklistTp.insert( db );
		  }
	    }
			

	      resultCount = pst.executeUpdate();
		  pst.close();
		  db.commit();
		  db.setAutoCommit(true);
		  
		  livelloRischio = this.getPunteggioChecklist(db, id);	
		  this.setPunteggioCalcolatoChecklist(db, livelloRischio);
		
		  
		  }
		  catch(SQLException e)
		  {
			  db.rollback();
			  throw e ;
		  }
		 
		  	  
		return resultCount;
	    }

	public boolean delete(Connection db) throws SQLException {
	  if (this.getId() == -1) {
		throw new SQLException("Audit Id not specified.");
	  }
	  boolean commit = db.getAutoCommit();
	  try {
	    if (commit) {
	      db.setAutoCommit(false);
	    }
	
        //Cancello le risposte collegate all'audit
	    AuditChecklist auditChecklist = new AuditChecklist();
	    auditChecklist.setAuditId(this.id);
	    auditChecklist.delete(db);
	    
        //Cancello le note e i range collegate all'audit
	    AuditChecklistType auditChecklistType = new AuditChecklistType();
	    auditChecklistType.setAuditId(this.id);
	    auditChecklistType.delete(db);
	    
        PreparedStatement pst = null;
		pst = db.prepareStatement("DELETE FROM audit WHERE id = ? ");
		pst.setInt(1, id);
		pst.execute();
		pst.close();
		if (commit) {
		  db.commit();
		}
	  } catch (SQLException e) {
	    e.printStackTrace();
	    if (commit) {
	      db.rollback();
	    }
	    throw new SQLException(e.getMessage());
	  } finally {
	    if (commit) {
	    db.setAutoCommit(true);
	  }
	}
	return true;
  }
	
	
	public boolean deleteAudit(Connection db,int idUser) throws SQLException {
		  if (this.getId() == -1) {
				throw new SQLException("Audit Id not specified.");
			  }
			  boolean commit = db.getAutoCommit();
			  try {
			    if (commit) {
			      db.setAutoCommit(false);
			    }
			
			    
		        PreparedStatement pst = null;
				pst = db.prepareStatement("UPDATE audit set trashed_date = CURRENT_TIMESTAMP , modified_by = ? WHERE id = ? ");
				pst.setInt(1, idUser);
				pst.setInt(2, id);
				pst.execute();
				pst.close();
				if (commit) {
				  db.commit();
				}
			  } catch (SQLException e) {
			    e.printStackTrace();
			    if (commit) {
			      db.rollback();
			    }
			    throw new SQLException(e.getMessage());
			  } finally {
			    if (commit) {
			    db.setAutoCommit(true);
			  }
			}
			return true;
		  }
	
		
	
	
	public boolean checkIfExists(Connection db, int checkId) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    String sqlSelect = "SELECT count (*)  as num FROM audit WHERE numero_registrazione = ? ";
	    int i = 0;
	    pst = db.prepareStatement(sqlSelect);
	    pst.setInt(++i, checkId);
	    rs = pst.executeQuery();
	    if (rs.next()) {
	      if (rs.getInt("num")>0){
	    	  return true;
	      }
	    }
	    return false;
	}
	
	public int nextRegistrationNumber( Connection db, int orgId ) throws SQLException
	{
		int ret = 1;
		
		PreparedStatement pst = null;
	    ResultSet rs = null;
	    String sqlSelect = "SELECT int4(numero_registrazione) AS num FROM audit WHERE " +
	    		"org_id = ? AND " +
	    		"numero_registrazione IS NOT NULL AND " +
	    		"numero_registrazione <> '' AND " +
	    		"numero_registrazione ~ '^[0-9]+$' " +
	    		"ORDER BY int4(numero_registrazione) DESC LIMIT 1 ";
	    pst = db.prepareStatement(sqlSelect);
	    pst.setInt( 1, orgId );
	    rs = pst.executeQuery();
	    if ( rs.next() )
	    {
	    		ret = (rs.getInt( "num" ) + 1);
	    }
	    
	    rs.close();
	    rs = null;
	    pst.close();
	    pst = null;
		
		return ret;
	}

	public int getTipoChecklist() {
		return tipoChecklist;
	}

	public void setTipoChecklist(int tipoChecklist) {
		this.tipoChecklist = tipoChecklist;
	}
	public void setTipoChecklist(String tipoChecklist) {
		this.tipoChecklist = Integer.parseInt(tipoChecklist);
	}
}
