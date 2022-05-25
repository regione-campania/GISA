/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.aspcf.modules.report.util.ApplicationProperties;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationAddressList;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddressList;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumberList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;



import com.daffodilwoods.daffodildb.server.sql99.dml.contextuallytypedrowvalueconstructor;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class ChecklistIstanza extends GenericBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
	   *  Constructor for the Organization object, creates an empty Organization
	   *
	   * @since    1.0
	   */
	  public ChecklistIstanza() { }
	  
	private int id;
	private int idAlleg;
	private int idCU;
	private int orgId;
	private int enteredBy;
	private int modifiedBy;

	
	private java.sql.Timestamp entered = null;
	private java.sql.Timestamp modified = null;
	private java.sql.Timestamp trashedDate = null;
	private java.sql.Timestamp dataChk = null;
	
	private String  mod_b11_note  ;
	private boolean  mod_b11_flag_preavviso  ;
	private String  mod_b11_data  ;
	private String  mod_b11_telefono  ;
	private String  mod_b11_fax  ;
	private String  mod_b11_altra_forma  ;
	private boolean  mod_b11_flag_rilascio_copia  ;
	private String  mod_b11_provvedimenti  ;
	
	private ArrayList<Risposta> risposte = new ArrayList<Risposta>();
    
	boolean bozza = true;
	
	
	
	
	public String getMod_b11_note() {
		return mod_b11_note;
	}
	public void setMod_b11_note(String mod_b11_note) {
		this.mod_b11_note = mod_b11_note;
	}
	public boolean isMod_b11_flag_preavviso() {
		return mod_b11_flag_preavviso;
	}
	public void setMod_b11_flag_preavviso(boolean mod_b11_flag_preavviso) {
		this.mod_b11_flag_preavviso = mod_b11_flag_preavviso;
	}
	public String getMod_b11_data() {
		return mod_b11_data;
	}
	public void setMod_b11_data(String mod_b11_data) {
		this.mod_b11_data = mod_b11_data;
	}
	public String getMod_b11_telefono() {
		return mod_b11_telefono;
	}
	public void setMod_b11_telefono(String mod_b11_telefono) {
		this.mod_b11_telefono = mod_b11_telefono;
	}
	public String getMod_b11_fax() {
		return mod_b11_fax;
	}
	public void setMod_b11_fax(String mod_b11_fax) {
		this.mod_b11_fax = mod_b11_fax;
	}
	public String getMod_b11_altra_forma() {
		return mod_b11_altra_forma;
	}
	public void setMod_b11_altra_forma(String mod_b11_altra_forma) {
		this.mod_b11_altra_forma = mod_b11_altra_forma;
	}
	public boolean isMod_b11_flag_rilascio_copia() {
		return mod_b11_flag_rilascio_copia;
	}
	public void setMod_b11_flag_rilascio_copia(boolean mod_b11_flag_rilascio_copia) {
		this.mod_b11_flag_rilascio_copia = mod_b11_flag_rilascio_copia;
	}
	public String getMod_b11_provvedimenti() {
		return mod_b11_provvedimenti;
	}
	public void setMod_b11_provvedimenti(String mod_b11_provvedimenti) {
		this.mod_b11_provvedimenti = mod_b11_provvedimenti;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdAlleg() {
		return idAlleg;
	}
	public void setIdAlleg(int idAlleg) {
		this.idAlleg = idAlleg;
	}
	
	public void setIdAlleg(String idAlleg) {
		this.idAlleg = Integer.parseInt(idAlleg);
	}
	public int getIdCU() {
		return idCU;
	}
	public void setIdCU(int idCU) {
		this.idCU = idCU;
	}
	
	public void setIdCU(String idC) {
		this.idCU = Integer.parseInt(idC);
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public void setOrgId(String org) {
		this.orgId = Integer.parseInt(org);
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
	public java.sql.Timestamp getTrashedDate() {
		return trashedDate;
	}
	public void setTrashedDate(java.sql.Timestamp trashedDate) {
		this.trashedDate = trashedDate;
	}
	public java.sql.Timestamp getDataChk() {
		return dataChk;
	}
	public void setDataChk(java.sql.Timestamp dataChk) {
		this.dataChk = dataChk;
	}
	
	public ArrayList<Risposta> getRisposte() {
		return risposte;
	}
	
	public void setRisposte(ArrayList<Risposta> risposte) {
		this.risposte = risposte;
	}
	
	 public boolean isBozza() {
		return bozza;
	}
	public void setBozza(boolean bozza) {
		this.bozza = bozza;
	}
	protected void buildRecord(ResultSet rs) throws SQLException {
		 
		 //chk_bns_mod_ist table
		 id = rs.getInt("id");
		 idAlleg = rs.getInt("id_alleg");
		 idCU = rs.getInt("idcu");
		 orgId = rs.getInt("orgId");
		 entered = rs.getTimestamp("entered");
		 enteredBy = rs.getInt("enteredby");
		 modified = rs.getTimestamp("modified");
		 modifiedBy = rs.getInt("modifiedBy");	 
		 bozza = rs.getBoolean("bozza");
		 
		 
		 /*PER LA GESTIONE MODELLO B11 */
		 mod_b11_note = rs.getString("mod_b11_note");
		 mod_b11_flag_preavviso = rs.getBoolean("mod_b11_flag_preavviso");
		 mod_b11_data = rs.getString("mod_b11_data");
		 mod_b11_telefono = rs.getString("mod_b11_telefono");
		 mod_b11_fax = rs.getString("mod_b11_fax");
		 mod_b11_altra_forma = rs.getString("mod_b11_altra_forma");
		 mod_b11_flag_rilascio_copia = rs.getBoolean("mod_b11_flag_rilascio_copia");
		 mod_b11_provvedimenti = rs.getString("mod_b11_provvedimenti");
		 
		 
			
			
		    
	 }
	

	public ChecklistIstanza(Connection db, int idControllo, int codice_specie) 
		throws SQLException {
			
			  
		    if (idControllo == -1) {
		      throw new SQLException("Invalid TicketID");
		    } 
		    PreparedStatement pst = db.prepareStatement(
		        "select * from chk_bns_mod_ist ist left join lookup_chk_bns_mod mod on mod.code = ist.id_alleg " +
		        "where idcu = ? and mod.codice_specie = ? and ist.trashed_date is null");

		    pst.setInt(1, idControllo);
		    pst.setInt(2, codice_specie);
		    
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		    }
		    
		    rs.close();
		    pst.close();
		    
		    Risposta risposta = new Risposta();
		    this.setRisposte(risposta.buildList(db,idControllo, codice_specie));
		  
	}
	  
	
	
	public boolean insert(Connection db,ActionContext context) throws SQLException {
	    
		StringBuffer sql = new StringBuffer();
	    
	    try {
		      modifiedBy = enteredBy;
	   
		      id = DatabaseUtils.getNextSeqInt(db, context,"chk_bns_mod_ist","id");
            
		      boolean committ = db.getAutoCommit();
		      /*PER LA GESTIONE MODELLO B11 */
				
		      if (committ==true)
		      {
		    	    db.setAutoCommit(false);
		      }
		      
		      sql.append(
		          "INSERT INTO chk_bns_mod_ist (id_alleg, idcu, orgid, entered, enteredBy, modifiedBy, trashed_date, bozza ");
		      if (id > -1) {
		        sql.append(",id ");
		      }
		      if (modified != null) {
		        sql.append(",modified ");
		      }
		      
		      if(this.idAlleg == 15 || this.idAlleg == 20) {
		      
		      if (mod_b11_note != null) {
			        sql.append(",mod_b11_note ");
			      }
		      
		     
			        sql.append(",mod_b11_flag_preavviso,mod_b11_flag_rilascio_copia ");
			 
		      if (mod_b11_data != null) {
			        sql.append(",mod_b11_data ");
			      }
		      if (mod_b11_telefono != null) {
			        sql.append(",mod_b11_telefono ");
			      }
		      if (mod_b11_fax != null) {
			        sql.append(",mod_b11_fax ");
			      }
		      if (mod_b11_altra_forma != null) {
			        sql.append(",mod_b11_altra_forma ");
			      }
		      if (mod_b11_provvedimenti != null) {
			        sql.append(",mod_b11_provvedimenti ");
			      }
		      
	    }
				  
				 
				  
				  
				  
				  
				 
		      
		      sql.append(")");
		      
		      sql.append("VALUES (?,?,?,?,?,?,?, ? ");
		     
		      if (id > -1) {
		        sql.append(",?");
		      }
		      if (modified != null) {
		        sql.append(",?");
		      }
		      
		      if(this.idAlleg == 15 || this.idAlleg == 20) {
		    	  
		    
		      if (mod_b11_note != null) {
		    	  sql.append(",?");
			      }
		      
		     
			        sql.append(",?,? ");
			 
		      if (mod_b11_data != null) {
		    	  sql.append(",?");
			      }
		      if (mod_b11_telefono != null) {
		    	  sql.append(",?");
			      }
		      if (mod_b11_fax != null) {
		    	  sql.append(",?");
			      }
		      if (mod_b11_altra_forma != null) {
		    	  sql.append(",?");
			      }
		      if (mod_b11_provvedimenti != null) {
		    	  sql.append(",?");
			      }
		      }
		      
		      sql.append(")");
	      
		      int i = 0;
		      Timestamp dataCorrente = new Timestamp((new java.sql.Date(System.currentTimeMillis())).getTime());
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      pst.setInt(++i, this.idAlleg);
		      pst.setInt(++i, this.idCU);
		      pst.setInt(++i, this.orgId);
		      DatabaseUtils.setTimestamp(pst, ++i, new Timestamp(System.currentTimeMillis()));
		      pst.setInt(++i, this.getModifiedBy());
		      pst.setInt(++i, this.getModifiedBy());
		      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
		      
		      //Adding Bozza
		      pst.setBoolean(++i, this.bozza);
		      
		      if (id > -1) {
		        pst.setInt(++i, id);
		      }
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      }
		      
		      if(this.idAlleg == 15 || this.idAlleg == 20) {
			       
		      
		      if (mod_b11_note != null) {
		    	  pst.setString(++i, mod_b11_note);
			      }
		      
			       pst.setBoolean(++i,mod_b11_flag_preavviso );
		      pst.setBoolean(++i,mod_b11_flag_rilascio_copia );
			 
		      if (mod_b11_data != null) {
		    	  pst.setString(++i, mod_b11_data);
			   }
		      if (mod_b11_telefono != null) {
		    	  pst.setString(++i, mod_b11_telefono);
		    	  
			      }
		      if (mod_b11_fax != null) {
		    	  pst.setString(++i, mod_b11_fax);
			      }
		      if (mod_b11_altra_forma != null) {
		    	  pst.setString(++i, mod_b11_altra_forma);
			      }
		      if ( mod_b11_provvedimenti!= null) {
		    	  pst.setString(++i, mod_b11_provvedimenti);
			      }
		      }
		      
		    boolean chkBnsModIstInserita = false; 
		  	int chkBnsModIstInseritaIndex = 0;
		  	do {
		  		try {
		  			System.out.println("PROVO INSERIMENTO IN chk_bns_mod_ist");
				    pst.execute();
		  			chkBnsModIstInserita = true;
		  			}
		  		catch (Exception e) {
		  			System.out.println("ERRORE INSERIMENTO IN chk_bns_mod_ist");
		  			e.printStackTrace();
		  		}
		  		chkBnsModIstInseritaIndex++;
		  	}
		  	while (!chkBnsModIstInserita && chkBnsModIstInseritaIndex<10);
		      
		  	pst.close();
		      
			if (chkBnsModIstInserita) {
		      //Insert the addresses if there are any
		      Iterator risposteList = this.risposte.iterator();
		      while (risposteList.hasNext()) {
		    	  
		         Risposta thisRisposta = (Risposta) risposteList.next();
		         thisRisposta.setIdModIst(this.id);
		         thisRisposta.insert(db,context);		        
		      }
		      
		      db.commit();
			}
		     
	      } catch (SQLException e) {
	    	 	  db.rollback();
	  	        throw new SQLException(e.getMessage());

	      }finally {
	          db.setAutoCommit(true);
	    }

	    return true;
	  }

	
	 public int update(Connection db) throws SQLException {
		    int i = -1;
		    boolean doCommit = false;
		    try {
		      if (doCommit = db.getAutoCommit()) {
		        db.setAutoCommit(false);
		      }
		      //First update checklist istance
		      i = this.update(db, false);

		      //Insert the addresses if there are any
		      Iterator<Risposta> risposta = this.getRisposte().iterator();
		      while (risposta.hasNext()) {
		        Risposta r = (Risposta) risposta.next();	
		        r.update(db, this.modifiedBy);		  
		       
		      }
		  
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

	 
	 public int update(Connection db, boolean override) throws SQLException {
		    
		 int resultCount = 0;
		 PreparedStatement pst = null;
		 StringBuffer sql = new StringBuffer();
		 sql.append(
		        "UPDATE chk_bns_mod_ist " +
		        "SET modifiedBy = ?, bozza = ? ");

		 if (!override) {
			 sql.append(", modified = ? ");
		 }
		 
		 if(this.idAlleg == 15 || this.idAlleg == 20){
			 
			 if ( mod_b11_note!= null) {
		    	  sql.append(",mod_b11_note =?");
			      }
		      
		     
			        sql.append(",mod_b11_flag_preavviso=?,mod_b11_flag_rilascio_copia=?  ");
			 
		      if (mod_b11_data != null) {
		    	  sql.append(",mod_b11_data=? ");
			      }
		      if (mod_b11_telefono != null) {
		    	  sql.append(",mod_b11_telefono=? ");
			      }
		      if (mod_b11_fax != null) {
		    	  sql.append(",mod_b11_fax=? ");
			      }
		      if (mod_b11_altra_forma != null) {
		    	  sql.append(",mod_b11_altra_forma=? ");
			      }
		      if (mod_b11_provvedimenti != null) {
		    	  sql.append(",mod_b11_provvedimenti=? ");
			      }
		      
	 }//fine if solo per la condizionalita'
		 sql.append(" WHERE idcu = ? ");
		 
		 int i = 0;
		 pst = db.prepareStatement(sql.toString());
		 pst.setInt(++i, this.modifiedBy);
		 pst.setBoolean(++i, this.bozza);
		 
		 if (!override) {
		   DatabaseUtils.setTimestamp(
		                pst, ++i, new Timestamp(System.currentTimeMillis()));
		  }
		 
		 if(this.idAlleg == 15 || this.idAlleg == 20){
		
		 if (mod_b11_note != null) {
	    	  pst.setString(++i, mod_b11_note);
		      }
	      
		       pst.setBoolean(++i,mod_b11_flag_preavviso );
		       pst.setBoolean(++i,mod_b11_flag_rilascio_copia );
		 
	      if (mod_b11_data != null) {
	    	  pst.setString(++i, mod_b11_data);
		   }
	      if (mod_b11_telefono != null) {
	    	  pst.setString(++i, mod_b11_data);
	    	  
		      }
	      if (mod_b11_fax != null) {
	    	  pst.setString(++i, mod_b11_fax);
		      }
	      if (mod_b11_altra_forma != null) {
	    	  pst.setString(++i, mod_b11_altra_forma);
		      }
	      if ( mod_b11_provvedimenti!= null) {
	    	  pst.setString(++i, mod_b11_provvedimenti);
		      }
	 	 
		 }
		 pst.setInt(++i, this.idCU);
		 resultCount = pst.executeUpdate();
		 pst.close();

		 return resultCount;
	 }
	
	
	 public void setRisposteFromRequestInseriti(HttpServletRequest request, Connection db, int idCU, int specie) throws NumberFormatException, SQLException {
		 
		 int c = 1;
		 
			
		 ArrayList<Capitolo> capitoliList = Capitolo.buildRecordCapitoliGiaInseriti(db, idCU, specie);

		 Iterator<Capitolo> it = capitoliList.iterator();
		 ArrayList<Risposta> risposteList = new ArrayList<Risposta>();

		 //Per ogni capitolo...
	     while(it.hasNext()){
	  	  		Capitolo capitolo = it.next();
	  	  		ArrayList<Domanda> domande = capitolo.getDomandeList();

	  	  		int numDomande = domande.size();
	  	  		int d = 1;
	  	  		while (d <= numDomande){
	  	  			
	  	  			org.aspcf.modules.checklist_benessere.base.Risposta thisRisposta = new Risposta();
	  	  			thisRisposta.buildRecord(request, c, d);
	  	  			thisRisposta.setDescCap(capitolo.getDescription());
	  	  			
	  	  			//SET IDCAPFKEY CON CAPITOLO.GETCODE
	  	  			thisRisposta.setIdCap_fKey(capitolo.getCode());
	  	  			Domanda dom = domande.get(d-1);
	  	  			thisRisposta.setDescDom(dom.getDescription());
	  	  			risposteList.add(thisRisposta);
	  	  			d++;
	  	  		}
	  	  		
	  	  		//incremento l'indice delle domande
				++c;	
	     }
	     
	     this.setRisposte(risposteList);
		         
	 }	
	 
 public void setRisposteFromRequest(HttpServletRequest request, Connection db, int versione, int specie) throws NumberFormatException, SQLException {
		 
		 int c = 1;
		 
		
		 ArrayList<Capitolo> capitoliList = Capitolo.buildRecordCapitoli(db, specie, versione);

		 Iterator<Capitolo> it = capitoliList.iterator();
		 ArrayList<Risposta> risposteList = new ArrayList<Risposta>();

		 //Per ogni capitolo...
	     while(it.hasNext()){
	  	  		Capitolo capitolo = it.next();
	  	  		ArrayList<Domanda> domande = capitolo.getDomandeList();

	  	  		int numDomande = domande.size();
	  	  		int d = 1;
	  	  		while (d <= numDomande){
	  	  			
	  	  			org.aspcf.modules.checklist_benessere.base.Risposta thisRisposta = new Risposta();
	  	  			thisRisposta.buildRecord(request, c, d);
	  	  			thisRisposta.setDescCap(capitolo.getDescription());
	  	  		
	  	  			//SET IDCAPFK CON CAPITOLO.GETCODE
	  	  			thisRisposta.setIdCap_fKey(capitolo.getCode());
	  	  			Domanda dom = domande.get(d-1);
	  	  			thisRisposta.setDescDom(dom.getDescription());
	  	  			thisRisposta.setClassews(dom.getClassews());
	  	  			risposteList.add(thisRisposta);
	  	  			d++;
	  	  		}
	  	  		
	  	  		//incremento l'indice delle domande
				++c;	
	     }
	     
	     this.setRisposte(risposteList);
		         
	 }	
	 
 
 public void setParametriModB11FromRequest(HttpServletRequest req)
 {
	 
	 
	 mod_b11_note = req.getParameter("mod_b11_note");
	 mod_b11_data = req.getParameter("mod_b11_data");
	 mod_b11_telefono = req.getParameter("mod_b11_telefono");
	 mod_b11_fax = req.getParameter("mod_b11_fax");
	 mod_b11_altra_forma = req.getParameter("mod_b11_altra_forma");
	 mod_b11_provvedimenti = req.getParameter("mod_b11_provvedimenti");
	 if (req.getParameter("mod_b11_flag_rilascio_copia")!= null && "si".equalsIgnoreCase(req.getParameter("mod_b11_flag_rilascio_copia")))
		 mod_b11_flag_rilascio_copia = true ;
	 
	 if (req.getParameter("mod_b11_flag_preavviso")!= null && "si".equalsIgnoreCase(req.getParameter("mod_b11_flag_preavviso")))
		 mod_b11_flag_preavviso = true ;
	 
	 
 }
	 
	 public boolean verificaScheda(Connection db,int ticketid, int specie) throws SQLException{

		 boolean esiste = false;
		 String qry = "select count(*) as conta from chk_bns_mod_ist ist "+
		 " left join lookup_chk_bns_mod lk on lk.code = ist.id_alleg " +
		 " where idcu = ? and lk.codice_specie = ? and ist.trashed_date is null ";
		 PreparedStatement pst = db.prepareStatement(qry);
		 pst.setInt(1,ticketid);
		 pst.setInt(2,specie);
		 ResultSet rs = pst.executeQuery();
		 while(rs.next()){
			 
			 if(rs.getInt("conta") > 0){
				 esiste = true;
			 }
		 }
			      
		 return esiste;
	 }
	
	    
}
