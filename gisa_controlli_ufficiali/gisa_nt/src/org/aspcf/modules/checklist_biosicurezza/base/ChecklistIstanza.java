/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_biosicurezza.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcf.modules.checklist_farmacosorveglianza.base.Giudizio;

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
	private int idCU;
	private int orgId;
	private int enteredBy;
	private int modifiedBy;
	private boolean bozza = true;
	
	private java.sql.Timestamp entered = null;
	private java.sql.Timestamp modified = null;
	private java.sql.Timestamp trashedDate = null;
	
	private ArrayList<Domanda> domande = new ArrayList<Domanda>();
	
	private Dati dati = new Dati();
    
	protected void buildRecord(ResultSet rs) throws SQLException {
		 
		 id = rs.getInt("id");
		 idCU = rs.getInt("idcu");
		 orgId = rs.getInt("org_id");
		 entered = rs.getTimestamp("entered");
		 enteredBy = rs.getInt("enteredby");
		 modified = rs.getTimestamp("modified");
		 modifiedBy = rs.getInt("modifiedBy");	 
		 bozza = rs.getBoolean("bozza");
			
		    
	 }
	

	public ChecklistIstanza(Connection db, int idControllo, int codice_specie) 
		throws SQLException {
			
			  
		    if (idControllo == -1) {
		      throw new SQLException("Invalid TicketID");
		    } 
		    PreparedStatement pst = db.prepareStatement("select * from biosicurezza_istanze where idcu = ? and trashed_date is null");

		    pst.setInt(1, idControllo);
		    
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		    }
		    
		    rs.close();
		    pst.close();
		    
		    this.setDati(db);
		    
		    Domanda domanda = new Domanda();
		    this.setDomande(domanda.getListaDomande(db, this.id, codice_specie));
		  
	}


	private void setDati(Connection db) throws SQLException {
		Dati dati = new Dati(db, id);
		this.setDati(dati);		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdCU() {
		return idCU;
	}


	public void setIdCU(int idCU) {
		this.idCU = idCU;
	}


	public int getOrgId() {
		return orgId;
	}


	public void setOrgId(int orgId) {
		this.orgId = orgId;
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


	public boolean isBozza() {
		return bozza;
	}


	public void setBozza(boolean bozza) {
		this.bozza = bozza;
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


	public ArrayList<Domanda> getDomande() {
		return domande;
	}


	public void setDomande(ArrayList<Domanda> domande) {
		this.domande = domande;
	}
	  
	
	public void recuperaDaForm(ActionContext context, Connection db) throws SQLException {
		
		Dati dati = new Dati(context);
		this.dati = dati;
				
		ArrayList<Domanda> domandeConRisposta = new ArrayList<Domanda>();
		for (int i = 0; i<domande.size(); i++){
			   Domanda domanda = (Domanda) domande.get(i);
				for (int r = 0; r<domanda.getListaRisposte().size(); r++){
					Risposta risposta = (Risposta) domanda.getListaRisposte().get(r);
					risposta.setRisposto(false);
					if (risposta.getTipo().equals("radio")){
						if (context.getRequest().getParameter("dom_"+domanda.getId()+"_risposta")!=null && Integer.parseInt(context.getRequest().getParameter("dom_"+domanda.getId()+"_risposta")) ==  risposta.getId())
							risposta.setRisposto(true);
					}
					else if (risposta.getTipo().equals("textarea")){
						if (context.getRequest().getParameter("dom_"+domanda.getId()+"_risposta_"+risposta.getId())!=null && !(context.getRequest().getParameter("dom_"+domanda.getId()+"_risposta_"+risposta.getId()).equals(""))){
							risposta.setRisposto(true);
							risposta.setNote(context.getRequest().getParameter("dom_"+domanda.getId()+"_risposta_"+risposta.getId()));
						}
					}
				}
			   domandeConRisposta.add(domanda);
	}
	domande = domandeConRisposta;
}
	
	public void upsert(Connection db) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		
		int idEsitoClassyfarmPrecedente = -1;
		String descrizioneErroreClassyfarmPrecedente = null;
		String descrizioneMessaggioClassyfarmPrecedente = null;
		Timestamp dataInvioPrecedente = null;
			
		System.out.println("CONTROLLO SE ESISTE GIA' UN'ISTANZA");
		pst = db.prepareStatement("select id, id_esito_classyfarm, descrizione_errore_classyfarm, descrizione_messaggio_classyfarm, data_invio from biosicurezza_istanze where idcu = ? and trashed_date is null");
		pst.setInt(1, idCU);
		rs = pst.executeQuery();
		if (rs.next()){
			this.id = rs.getInt("id");
			idEsitoClassyfarmPrecedente =  rs.getInt("id_esito_classyfarm");
			descrizioneErroreClassyfarmPrecedente =  rs.getString("descrizione_errore_classyfarm");
			descrizioneMessaggioClassyfarmPrecedente = rs.getString("descrizione_messaggio_classyfarm");
			dataInvioPrecedente = rs.getTimestamp("data_invio");
		}
		System.out.println("ID ISTANZA: "+this.id);
		
		System.out.println("SE ESISTE, DISABILITO L'ISTANZA");

		if (this.id>0){
			pst = db.prepareStatement("update biosicurezza_istanze set trashed_date = now() where id = ? and trashed_date is null");
			pst.setInt(1, this.id);
			pst.executeUpdate();
			
			pst = db.prepareStatement("update biosicurezza_dati_ext set trashed_date = now() where id_istanza = ? and trashed_date is null");
			pst.setInt(1, this.id);
			pst.executeUpdate();
			
			pst = db.prepareStatement("update biosicurezza_istanze_risposte set trashed_date = now() where id_istanza = ? and trashed_date is null");
			pst.setInt(1, this.id);
			pst.executeUpdate();
		}
		
		System.out.println("INSERISCO LA NUOVA ISTANZA");
		pst = db.prepareStatement("insert into biosicurezza_istanze (idcu, org_id, enteredby, modifiedby, bozza, id_esito_classyfarm, descrizione_errore_classyfarm, descrizione_messaggio_classyfarm, data_invio) values (?, ?, ?, ?, ?, ?, ?, ?, ?) returning id as id_inserito");
		pst.setInt(1, idCU);
		pst.setInt(2, orgId);
		pst.setInt(3, enteredBy);
		pst.setInt(4, enteredBy);
		pst.setBoolean(5, bozza);
		pst.setInt(6, idEsitoClassyfarmPrecedente);
		pst.setString(7, descrizioneErroreClassyfarmPrecedente);
		pst.setString(8, descrizioneMessaggioClassyfarmPrecedente);
		pst.setTimestamp(9, dataInvioPrecedente);
		rs = pst.executeQuery();
		
		if (rs.next())
			this.id = rs.getInt("id_inserito");

		System.out.println("INSERISCO I NUOVI DATI EXT");
		dati.insertDati(db, id);
		
		System.out.println("INSERISCO LE NUOVE RISPOSTE");
		
		for (int d = 0 ; d<domande.size(); d++) {
			Domanda domanda = (Domanda) domande.get(d);
			for (int r = 0; r<domanda.getListaRisposte().size(); r++){
				Risposta risposta = (Risposta) domanda.getListaRisposte().get(r);
				if (risposta.isRisposto())
					risposta.insertRisposta(db, id, domanda.getId(), enteredBy);
			}

	   }
		
		}


	public Dati getDati() {
		return dati;
	}


	public void setDati(Dati dati) {
		this.dati = dati;
	}
 
 
	    
}
