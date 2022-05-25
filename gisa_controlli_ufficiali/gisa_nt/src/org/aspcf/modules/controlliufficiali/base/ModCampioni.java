/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.aspcf.modules.report.util.ManagePdfBase;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.soa.base.LineaAttivitaSoa;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DwrPreaccettazione;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfStamper;


public class ModCampioni {

	
	String barcodeMotivazione;
	String barcodeOSA;
	String barcodePrelievo;
	String barcodeMatrice;
	String barcodeMolluschi;
	String matrice;
	String codiciMatrice;
	String analiti;
	String data_chiusura_campione;
	String idControllo;
	String piani;
	String piano;
	String motivazione;
	String motivazione_campione;
	String tipoModulo;
	String servizio;
	String uo;
	String barcode_new;
	String barcodeMolluschi_new;
	String barcodePrelievo_new;
	String codPreaccettazione;
	
	public String getBarcode_new() {
		return barcode_new;
	}

	public void setBarcode_new(String barcode_new) {
		this.barcode_new = barcode_new;
	}
	
	public String getData_chiusura_campione() {
		return data_chiusura_campione;
	}

	public void setData_chiusura_campione(String data_chiusura_campione) {
		this.data_chiusura_campione = data_chiusura_campione;
	}

	public String getServizio() {
		return servizio;
	}

	public void setServizio(String servizio) {
		this.servizio = servizio;
	}

	public String getUo() {
		return uo;
	}

	public void setUo(String uo) {
		this.uo = uo;
	}

	public ModCampioni(){}
	
	public String getBarcodeMotivazione() {
		return barcodeMotivazione;
	}

	public void setBarcodeMotivazione(String barcodeMotivazione) {
		this.barcodeMotivazione = barcodeMotivazione;
	}

	public String getBarcodeOSA() {
		return barcodeOSA;
	}

	public void setBarcodeOSA(String barcodeOSA) {
		this.barcodeOSA = barcodeOSA;
	}

	public String getBarcodePrelievo() {
		return barcodePrelievo;
	}

	public void setBarcodePrelievo(String barcodePrelievo) {
		this.barcodePrelievo = barcodePrelievo;
	}
	
	public void setBarcodeMolluschi(String barcodePrelievo) {
		this.barcodeMolluschi = barcodePrelievo;
	}
	
	public String getBarcodeMatrice() {
		return barcodeMatrice;
	}
	
	public String getBarcodeMolluschi() {
		return barcodeMolluschi;
	}

	public String getBarcodeMolluschiNew() {
		return barcodeMolluschi_new;
	}
	
	public String getBarcodePrelievoNew() {
		return barcodePrelievo_new;
	}

	public void setBarcodePrelievoNew(String barcodePrelievo_new) {
		this.barcodePrelievo_new = barcodePrelievo_new;
	}
	
	public void setBarcodeMolluschiNew(String barcodeMolluschi_new) {
		this.barcodeMolluschi_new = barcodeMolluschi_new;
	}
	
	public void setBarcodeMatrice(String barcodeMatrice) {
		this.barcodeMatrice = barcodeMatrice;
	}

	public String getMatrice() {
		return matrice;
	}

	public void setMatrice(String matrice) {
		this.matrice = matrice;
	}

	public String getCodiciMatrice() {
		return codiciMatrice;
	}

	public void setCodiciMatrice(String codiciMatrice) {
		this.codiciMatrice = codiciMatrice;
	}

	public String getAnaliti() {
		return analiti;
	}

	public void setAnaliti(String analiti) {
		this.analiti = analiti;
	}
	
	public String getIdControllo() {
		return idControllo;
	}

	public void setIdControllo(String idControllo) {
		this.idControllo = idControllo;
	}

	public String getPiani() {
		return piani;
	}

	public void setPiani(String piani) {
		this.piani = piani;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public String getMotivazione_campione() {
		return motivazione_campione;
	}

	public void setMotivazione_campione(String motivazione_campione) {
		this.motivazione_campione = motivazione_campione;
	}

	public String getTipoModulo() {
		return tipoModulo;
	}

	public void setTipoModulo(String tipoModulo) {
		this.tipoModulo = tipoModulo;
	}


	
	public ModCampioni(ResultSet rs) throws SQLException{
		
		if(rs.next()) {	
			
			idControllo = rs.getString("idControllo");
			piano = rs.getString("piano");
			motivazione = rs.getString("motivazione");
			motivazione_campione = rs.getString("motivazione_campione");
			barcodeMotivazione = rs.getString("barcode_motivazione");
			data_chiusura_campione = rs.getString("data_chiusura_campione");
			/*Barcode Quesito diagnostico
			if(motivazione != null && !motivazione.equals("")){
				this.setBarcodeMotivazione(rs.getString("motivazione"));
			}
			else {
				this.setBarcodeMotivazione("NON DISPONIBILE");
			}*/
		
		}
		
		
	}
		
	
	public ArrayList<Integer> getIdFoglia( Connection db, int ticketId, String nomeDb, String colonna ) throws SQLException {
	 		  
	 		StringBuffer sql = new StringBuffer();
	 		ArrayList<Integer> idFoglie = new ArrayList<Integer>();
	 		PreparedStatement pst = null;
	 		//Per prova e' stato preso l'id come codice_esame
	 		sql.append("select "+colonna+" from "+nomeDb+"_campioni where id_campione = ?");
	 		pst = db.prepareStatement(sql.toString());
	 		pst.setInt(1, ticketId);
	 		ResultSet rs = pst.executeQuery();
	 		int i=-1;
	 		while (rs.next()){
	 			i++;
	 			idFoglie.add(rs.getInt(colonna)); 
	 		}
	 		
	 		pst.close();
	 		
	 		return idFoglie;
	    }	
		
	public ArrayList<String> getPathAndCodesFromId(Connection db,int code, String nomeDb, String colonna) throws SQLException {
		  
    	ArrayList<String> pathCodes = new ArrayList<String>();
		StringBuffer sql  = new StringBuffer();
		PreparedStatement pst = null;
		sql.append(" WITH RECURSIVE recursetree("+colonna+",nome,livello,id_padre,enabled ) AS ( " +
                   " SELECT "+colonna+",nome,livello,id_padre,enabled,CAST(id_padre As varchar(1000)) as path_id ,CAST(nome As varchar(1000)) As path_desc ,enabled, "+
                   " codice_esame, codice_arpac "+
                   "  FROM "+ nomeDb+" WHERE id_padre = -1 and enabled = true "+ 
                   " UNION ALL "+
                   " SELECT "+
                   " t."+colonna+",t.nome,t.livello,t.id_padre,t.enabled, "+
                   " CAST(rt.path_id || ';' || t.id_padre As varchar(1000)),CAST(rt.path_desc || '->' || t.nome As varchar(1000)) As path_desc  ,t.enabled, "+
                   " rt.codice_esame || ';' || t.codice_esame, "+
                   " rt.codice_arpac || ';' || t.codice_arpac "+
                   "  FROM "+nomeDb+" t "+
                   "   JOIN recursetree rt ON rt."+colonna+" = t.id_padre and t.enabled = true "+ 
                   "  ) "+
                   " SELECT * FROM recursetree where "+colonna+" = ? "); 
		pst = db.prepareStatement(sql.toString());
		pst.setInt(1, code);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			pathCodes.add(rs.getString("path_desc"));
			
			if (rs.getString("codice_arpac")!=null && !rs.getString("codice_arpac").equals(""))
				pathCodes.add(rs.getString("codice_arpac"));
			else
				pathCodes.add(rs.getString("codice_esame"));
		}
	
		return pathCodes;
		          
    } 
	
	public String generaCodice( Connection db, int suffix) throws SQLException {
		
		//Identificativo prelievo suffisso
		String idpvsuf = "";
		String codicePrelievo = "";
		//Make switch for suffix type
		switch (suffix) {
			case 1:  idpvsuf = "vpm"; break;
			case 2:  idpvsuf = "vpb"; break;
			case 3:  idpvsuf = "vpc"; break;
			case 6:  idpvsuf = "vpsa"; break;
			case 10: idpvsuf = "vpsac";break;
			default:System.out.println("Nessuna delle scelte e' valida");
			break;
		}	
			
	    int sequence = -1;
		sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_prelievo_id_seq");
		codicePrelievo = this.getPaddedId(sequence)+idpvsuf;
		return codicePrelievo;
			            
   }
	
   public String generaCodice(Connection db) throws SQLException {
		
		String codicePrelievo = "";
		//Make switch for suffix type
	    int sequence = -1;
		sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_prelievo_id_seq");
		codicePrelievo = this.getPaddedId(sequence);
		return codicePrelievo;
			            
   }
	
	
	public String generaCodiceMolluschi(Connection db, String barcode, int id_campione, int orgId, String idCU) throws SQLException {
		 
	 	StringBuffer sql = new StringBuffer();
	 	PreparedStatement pst = null;
	 	PreparedStatement pst2 = null;
	 	String codicePrelievo = "";
	 	
	 	if(id_campione != 0){
	 		//Contesto classico
			if (!idCU.equals("") && idCU!=null && !idCU.equals("-1")){ 
				
				int sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_osa_serial_id_seq");
				//campione associato ad un CU
				//codicePrelievo = barcode+"vpm";	
				codicePrelievo = barcode;	
				sql.append("select barcode from barcode_osa where barcode = ?"); 
				pst=db.prepareStatement(sql.toString());
				pst.setString(1,codicePrelievo);
				pst.execute();
				
				ResultSet rs_b = pst.executeQuery();
				sql = new StringBuffer();
				//Se entra in questo if abbiamo un campione e 2 verbali stampabili....
				if(!rs_b.next()){
					
					sql.append("INSERT INTO barcode_osa(serial_id, org_id,id_campione,barcode,ticket_id) VALUES(?, ?,?,?,?)"); 
					pst2=db.prepareStatement(sql.toString());
					pst2.setInt(1, sequence);
					pst2.setInt(2,orgId);
					pst2.setString(3,Integer.toString(id_campione));
					pst2.setString(4, codicePrelievo);
					pst2.setInt(5, Integer.parseInt(idCU));
					pst2.execute();
				}else {
					sql.append("INSERT INTO barcode_osa(serial_id, org_id,id_campione,barcode,ticket_id) VALUES(?,?,?,?,?)"); 
					pst2=db.prepareStatement(sql.toString());
					pst2.setInt(1, sequence);
					pst2.setInt(2,orgId);
					pst2.setString(3,Integer.toString(id_campione));
					if(codicePrelievo.contains("vpb")){
						codicePrelievo = codicePrelievo.replace("vpb", "vpm");
						pst2.setString(4, codicePrelievo);	
					}else {
						codicePrelievo = codicePrelievo.replace("vpc", "vpm");
						pst2.setString(4, codicePrelievo);
					}
					pst2.setInt(5, Integer.parseInt(idCU));
					pst2.execute();
					
				}
				
			}
		
		
			pst.close();
			
		 }
		
	 return codicePrelievo;	          
	}
	
	
	public String generaCodiceAcque(Connection db, int idCampione, int idCU, int orgId){
		
		StringBuffer sql = new StringBuffer();
		PreparedStatement pst= null;
		String barcode = "-1";
		
		int sequence = -1, j=0;
		try {
			sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_acque_serial_id_seq");
		
		barcode = Integer.toString(idCampione)+sequence;
		
		sql.append("INSERT INTO barcode_osa(serial_id, org_id,id_campione,barcode,ticket_id) VALUES(?,?,?,?,?)"); 
		pst=db.prepareStatement(sql.toString());
		pst.setInt(++j, sequence);
		pst.setInt(++j,orgId);
		pst.setString(++j,Integer.toString(idCampione));
		pst.setString(++j,barcode);
		pst.setInt(++j,idCU);
		
		pst.execute();
		pst.close();
		
		//VEDI SE ESISTE UN BARCODE GENERATO
		 PreparedStatement check_barcode = db.prepareStatement("select * from barcode_osa where org_id = ? and id_campione = ? ");
		 check_barcode.setInt(1,orgId);
		 check_barcode.setString(2,Integer.toString(idCampione));
		 ResultSet rs = check_barcode.executeQuery();
		 while(rs.next()){	 
			 barcode = rs.getString("barcode");
		 }
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return barcode;
		
	}
	 
	public String generaCodice( Connection db, int suffix, int orgId, int id_campione ) throws SQLException {
		 
	 	StringBuffer sql = new StringBuffer();
	 	StringBuffer sql2 = new StringBuffer();
	 	//Identificativo prelievo suffisso
	 	String idpvsuf = "";
	 	String codicePrelievo = "";
	 	 	
	 	org.aspcfs.modules.campioni.base.Ticket c = new  org.aspcfs.modules.campioni.base.Ticket (db, id_campione);
	 	int asl = c.getSiteId();
	 
		//Verifico prima che tipo di modulo e'
		switch (suffix) {
			case 1:  idpvsuf = "vpm"; break;
		  	case 2:  idpvsuf = "vpb"; break;
		  	case 3:  idpvsuf = "vpc"; break;
		  	case 6:  idpvsuf = "vpsa"; break;
		  	case 10: idpvsuf = "vpsac";break;
		 	case 19: idpvsuf = "vpnaa";break;
		 	case 0:  idpvsuf = "";break;
		  	default:System.out.println("Nessuna delle scelte e' valida");
			break;
		}	
	 	
	 Boolean manuale = false;	
	 
	 if(id_campione!=0 ){// && suffix != 1{
		 
		 //VEDI SE ESISTE UN BARCODE GENERATO
		 PreparedStatement check_barcode = db.prepareStatement("select * from barcode_osa where org_id = ? and id_campione = ? and barcode ilike ?");
		 check_barcode.setInt(1,orgId);
		 check_barcode.setString(2,Integer.toString(id_campione));
		 check_barcode.setString(3,"%"+idpvsuf);
		 ResultSet rs = check_barcode.executeQuery();
		 while(rs.next()){	 
			 codicePrelievo = rs.getString("barcode");
		 }

		//VEDI SE ESISTE UN BARCODE INSERITO MANUALMENTE
		 String s = "";
				 
		 if (codicePrelievo.equals("") || codicePrelievo==null){
			 check_barcode = db.prepareStatement("select location from ticket where ticketid="+id_campione);
			 rs = check_barcode.executeQuery();
			 if (rs.next()){
				 s = rs.getString("location");
			 }
			 if (s!=null && !s.equals("") && !s.equals("AUTOMATICO") && !s.contains("vpm") && !s.contains("vpb") && !s.contains("vpc") && !s.contains("vpsa") && !s.contains("vpsac") && !s.contains("vpnaa")){
				 manuale=true;
				 check_barcode = db.prepareStatement("select * from barcode_osa where org_id = "+orgId+" and id_campione = '"+id_campione+"' and barcode = '"+s+"'");
				 rs = check_barcode.executeQuery();
				 while(rs.next()){	 
					 codicePrelievo = rs.getString("barcode");
				 }
			 }
		 }
		 
		 //non c'e' barcode
		 if(codicePrelievo.equals("") || codicePrelievo==null){
			int sequence = -1, j=0;
			sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_osa_serial_id_seq");
			String idCU = "";
			
			//prelevo id_controllo_ufficiale del campione
			PreparedStatement pst = null;
			pst = db.prepareStatement("select id_controllo_ufficiale from ticket where ticketid="+id_campione);
			rs = pst.executeQuery();
			while (rs.next()){
				idCU = rs.getString("id_controllo_ufficiale");
			}
			pst=null;
			
			if (!idCU.equals("") && idCU!=null && !idCU.equals("-1") && manuale==false){ //campione associato ad un CU
				//codicePrelievo = ""+id_campione+sequence+idpvsuf;
				//codicePrelievo = ""+sequence;
				codicePrelievo = this.getPaddedId(sequence);
				sql.append("INSERT INTO barcode_osa(serial_id, org_id,id_campione,barcode,barcode_new, ticket_id) VALUES(?,?,?,?,?, ?)"); 
				pst=db.prepareStatement(sql.toString());
				pst.setInt(++j, sequence);
				pst.setInt(++j,orgId);
				pst.setString(++j,Integer.toString(id_campione));
				if (manuale==true){
					this.setBarcode_new(nuovoCodice(s, asl));
					pst.setString(++j, s);
					pst.setString(++j, this.getBarcode_new());
					//pst.setString(++j, s+id_campione+idpvsuf);
					//pst.setString(++j,s+idpvsuf);
				}
				else{
					this.setBarcode_new(nuovoCodice(codicePrelievo,asl));
					pst.setString(++j, codicePrelievo);
					pst.setString(++j, this.getBarcode_new());
				}
				pst.setInt(++j, Integer.parseInt(idCU));
			}
			else if(!idCU.equals("") && idCU!=null && !idCU.equals("-1") && manuale==true){
				//codicePrelievo = s+id_campione+idpvsuf;	
				codicePrelievo = s;
				sql.append("INSERT INTO barcode_osa(serial_id, org_id,id_campione,barcode,barcode_new,ticket_id) VALUES(?,?,?,?,?,?)"); 
				pst=db.prepareStatement(sql.toString());
				pst.setInt(++j,sequence);
				pst.setInt(++j,orgId);
				pst.setString(++j,Integer.toString(id_campione));
				pst.setString(++j, codicePrelievo);
				this.setBarcode_new(nuovoCodice(codicePrelievo,asl));
				pst.setString(++j, this.getBarcode_new());
				pst.setInt(++j, Integer.parseInt(idCU));
			}
			else { //campione NON associato ad un CU
				//codicePrelievo = this.getPaddedId(sequence)+idpvsuf;	
				codicePrelievo = this.getPaddedId(sequence);	
				sql.append("INSERT INTO barcode_osa(serial_id, org_id,id_campione,barcode, barcode_new) VALUES(?,?,?,?, ?)"); 
				pst=db.prepareStatement(sql.toString());
				pst.setInt(++j,sequence);
				pst.setInt(++j,orgId);
				pst.setString(++j,Integer.toString(id_campione));
				pst.setString(++j, codicePrelievo);
				this.setBarcode_new(nuovoCodice(codicePrelievo,asl));
				pst.setString(++j, this.getBarcode_new());
			} 

			pst.execute();
			pst.close();
			rs.close();		
		 }
		 check_barcode.close();
		 
	 } else {
		 
		//caso dei molluschi bivalvi... 
		 PreparedStatement check_barcode = null;
		 PreparedStatement pst = null;
		 String s = "";
		 String idCU = "";
		 ResultSet rs = null;
		 check_barcode = db.prepareStatement("select location, id_controllo_ufficiale from ticket where ticketid="+id_campione);
		 rs = check_barcode.executeQuery();
		 //recupero da ticket il numero del verbale
		 if (rs.next()){
			 s = rs.getString("location");
			 idCU = rs.getString("id_controllo_ufficiale");
		 }
		 if (!idCU.equals("") && idCU!=null && !idCU.equals("-1")){ //campione associato ad un CU
					
				sql.append("select barcode from barcode_osa where barcode = ?"); 
				pst=db.prepareStatement(sql.toString());
				pst.setString(1,s);
				ResultSet rs_b = pst.executeQuery();
				//non e' stato trovato il record...
				if(!rs_b.next()) {
					int sequence = -1, j=0;
					sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_osa_serial_id_seq");
					PreparedStatement pst2 = null;
					sql2.append("INSERT INTO barcode_osa(serial_id,org_id,id_campione,barcode,barcode_new,ticket_id) VALUES(?,?,?,?,?,?)"); 
					pst2=db.prepareStatement(sql2.toString());
					pst2.setInt(++j,sequence);
					pst2.setInt(++j,orgId);
					pst2.setString(++j,Integer.toString(id_campione));
					pst2.setString(++j, s);
					this.setBarcode_new(nuovoCodice(s,asl));
					pst.setString(++j, this.getBarcode_new());
					pst2.setInt(++j, Integer.parseInt(idCU));
					pst2.execute();
					pst2.close();
				}
				
				pst.close();
				
				rs_b.close();
				rs.close();
		}
		 
		
		
	}
		
			

	 return codicePrelievo;	          
	}



	 public String getPaddedId(int sequence) {
		 String padded = (String.valueOf(sequence));
		   while (padded.length() < 6) {
		      padded = "0" + padded;
		   }
		   return padded;
	 }

	
	

	public void getFieldsModuli(Connection db, String orgId, int idControllo, Organization org) {

		try {
			
			Ticket tic_vig = new Ticket(db, idControllo);
			tic_vig.setIdControlloUfficiale(Integer.toString(idControllo));
			
			//Gestione Tipologia Attivita' Imprese
		    ArrayList<LineeAttivita> tipologia = null;
		    
		    if (tic_vig.getOrgId()>0)
		    	tipologia =  LineeAttivita.load_linea_attivita_per_cu(Integer.toString(idControllo), db,-1);
		    else if (tic_vig.getIdStabilimento()>0)
		    	tipologia =  LineeAttivita.opu_load_linea_attivita_per_cu(Integer.toString(idControllo), db,-1);
		    else if (tic_vig.getAltId()>0)
		    	tipologia =  LineeAttivita.sin_load_linea_attivita_per_cu(Integer.toString(idControllo), db);
		    
			if (tipologia.size() > 0) {
				LineeAttivita linea = tipologia.get(0);
				System.out.println("linea CU"+ linea.getAttivita());
				System.out.println("linea CU 2"+ linea.getLinea_attivita());
				org.setTipologia_att(linea.getLinea_attivita());//tipologiaAttivita 
			}

			else if (tic_vig.getCodiceAteco() != null) { 
				org.setTipologia_att(tic_vig.getCodiceAteco()+ " " + tic_vig.getDescrizioneCodiceAteco());
			}
			

			// SETFIELD
			if (org.getData_referto() != null) {
				org.setAnnoReferto(org.getData_referto().substring(0, 4));
				org.setGiornoReferto(org.getData_referto().substring(8, 11));
				org.setMeseReferto(this.getMeseFromData(org.getData_referto()));		
			}

			if (org.getIndirizzo() != null
					&& org.getIndirizzo().trim().length() >= 0) {
				org.setIndirizzo(org.getIndirizzo());
			}

			if (org.getLegale_rapp() !=null && org.getLegale_rapp().length() == 9
					&& org.getLegale_rapp().contains("null")) {
				org.setLegale_rapp("");
			} 
			
			org.setDomicilioDigitale(org.getDomicilioDigitale());
			org.setLuogo_nascita_rappresentante(org.getLuogo_nascita_rappresentante());
			
			if(org.getData_fine_controllo() !=null)
			{
			org.setGiorno_chiusura(org.getData_fine_controllo()
					.substring(8, 10));
			org.setMese_chiusura(org.getData_fine_controllo()
					.substring(5, 7));
			org.setAnno_chiusura(org.getData_fine_controllo()
					.substring(0, 4));
			}
			else
			{
				org.setGiorno_chiusura("");
				org.setMese_chiusura("");
				org.setAnno_chiusura("");
			}
		
			if(org.getData_nascita_rappresentante() !=null)
			{
				org.setGiornoNascita(org.getData_nascita_rappresentante()
					.substring(8, 10));
				org.setMeseNascita(org.getData_nascita_rappresentante()
					.substring(5, 7));
				org.setAnnoNascita(org.getData_nascita_rappresentante().substring(0, 4));
			}
			else
			{
				org.setGiornoNascita("");
				org.setMeseNascita("");
				org.setAnnoNascita("");
			}
				
			 //Barcode Codice OSA
			  if(org.getN_reg() != null && !org.getN_reg().equals("")){
					this.setBarcodeOSA(org.getN_reg());			
			  }
			
			  if(org.getApproval_number() != null && !org.getApproval_number().equals("")){
					this.setBarcodeOSA(org.getApproval_number());	
			  }
				
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
		} 
	}

	public void getBarcodes(Connection db, int tipoMod, String orgId, int idCampione) {
		
		try{
			
			org.aspcfs.modules.campioni.base.Ticket c = new  org.aspcfs.modules.campioni.base.Ticket (db, idCampione);
		 	int asl = c.getSiteId();
			
			//Gestione dei barcode
			String barCodePrelievo = this.generaCodice(db, tipoMod, Integer.parseInt(orgId), idCampione);
			String barcodeNew = "";
			if (c.getLocationNew()!=null && !c.getLocationNew().equals(""))
				barcodeNew = nuovoCodice(barCodePrelievo, asl);
			
			//Barcode Prelievo
			this.setBarcodePrelievo(barCodePrelievo);
			this.setBarcodePrelievoNew(barcodeNew);
			this.setBarcodeMotivazione(barcodeMotivazione);
			ArrayList<String> path_matrice = new ArrayList<String>();
			String descrizione = "";
			ArrayList<Integer> idMatrice = this.getIdFoglia(db,idCampione,"matrici","id_matrice");
			if (idMatrice.size()>0){
				path_matrice = this.getPathAndCodesFromId(db, idMatrice.get(0),"matrici","matrice_id");
			
				if(path_matrice.size() > 0){
					this.setMatrice(path_matrice.get(0));
				
				if(path_matrice.size() > 1){	
					if( path_matrice.get(1)!= null && !path_matrice.get(1).equals("")){
					this.setCodiciMatrice(path_matrice.get(1));
				}
				}
			}
			}
			
			//Analiti
			ArrayList<String> path_analiti = new ArrayList<String>();
			ArrayList<Integer> idFoglie = new ArrayList<Integer>();
			idFoglie = this.getIdFoglia(db, idCampione,"analiti","analiti_id");
			if (idFoglie.size()>0){
				for (int i = 0 ; i < idFoglie.size(); i++)
				{
					path_analiti = this.getPathAndCodesFromId(db,idFoglie.get(i),"analiti","analiti_id");
					if(path_analiti.size() > 0){
						 descrizione += path_analiti.get(0)+"  ---------  ";
					}
				}
				this.setAnaliti(descrizione);
				
				//Preaccettazione
			
				DwrPreaccettazione dwrPreacc = new DwrPreaccettazione(); 
				String result = dwrPreacc.Preaccettazione_RecuperaCodPreaccettazione(String.valueOf(idCampione));
				JSONObject jsonObj;
				jsonObj = new JSONObject(result);
								
				try {codPreaccettazione =  jsonObj.getString("codice_preaccettazione");} catch (Exception e) {}
				
					}
		}catch(SQLException ex) {
			ex.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 		
	}
	
	 public String retrieveCode(Connection db, int id,String type) throws SQLException{
	    	
			StringBuffer sql  = new StringBuffer();
			String codice = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			
			if(type.equals("matrice")){
			
				sql.append("select codice_esame from matrici where matrice_id = ? ");
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1, id);
				rs = pst.executeQuery();
				while (rs.next()){
					codice= rs.getString("codice_esame");
				}
				
			}
			else {
				
				sql.append("select codice_esame from analiti where analiti_id = ? ");
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1, id);
				rs = pst.executeQuery();
				while (rs.next()){
					codice= rs.getString("codice_esame");
				}
				
		   }
			
			
			pst.close();
			rs.close();
			
			return codice;
	    }
	    
	 public void retrieveCode(Connection db, int code) throws SQLException{
	    	
			StringBuffer sql  = new StringBuffer();
			PreparedStatement pst = null;
			ResultSet rs = null;
			
			sql.append("select * from quesiti_diagnostici_sigla where code = ? ");
		    pst = db.prepareStatement(sql.toString());
		    pst.setInt(1, code);
		    rs = pst.executeQuery();
		    while (rs.next()){
		    	//Memorizzo il barcode del quesito diagnostico
				this.setBarcodeMotivazione(rs.getString("codice_esame"));
				this.setMotivazione(rs.getString("codice_esame"));
				//Memorizzo la descrizione 
				this.setPiano(rs.getString("description"));
			}
		    
		    pst.close();
			rs.close();
				
	 }
	 
	
	
	public String getMeseFromData(String data_referto){
		String mese = data_referto.substring(5,7);
		
		switch (Integer.parseInt(mese)) {
			case 01 : mese = "Gennaio"    ;  break;
			case 02 : mese = "Febbraio"   ;  break;
			case 03 : mese = "Marzo"      ;  break;
			case 04 : mese = "Aprile"     ;  break;
			case 05 : mese = "Maggio"     ;  break;
			case 06 : mese = "Giugno"     ;  break;
			case 07 : mese = "Luglio"     ;  break;
			//case 08 : mese = "Agosto"     ;  break;
			//case 09 : mese = "Settembre"  ;  break;
			case 10 : mese = "Ottobre"    ;  break;
			case 11 : mese = "Novembre"   ;  break;
			case 12 : mese = "Dicembre"   ;  break;
		}
		if (mese.equals("08")){
			mese = "Agosto"; 
		}
		if (mese.equals("09")){
			mese = "Settembre";
		}
		
		return mese;
	}

	public void getPerContoDi(Connection db, ResultSet rs_campione) throws SQLException {
		// TODO Auto-generated method stub
		
	    StringBuffer sql  = new StringBuffer();
	    PreparedStatement pst = null;
		ResultSet rs = null;
	
		//=2 e' un piano
		if(rs_campione.next()){
			if(rs_campione.getInt("motivazione_campione") == 2 && rs_campione.getInt("codice_piano") > 0) {
				
				sql.append(" SELECT od.descrizione_lunga as uo, padre.descrizione_lunga from tipocontrolloufficialeimprese tcu left join oia_nodo od on tcu.id_unita_operativa = od.id " +
			                 " left join oia_nodo padre on padre.id = od.id_padre where idcontrollo = ? and id_unita_operativa > 0  and pianomonitoraggio = ? ");
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1,rs_campione.getInt("id_controllo_ufficiale"));
				pst.setInt(2,rs_campione.getInt("codice_piano"));
				rs = pst.executeQuery();
				while(rs.next()){
					this.setServizio(rs.getString("servizio"));
					this.setUo(rs.getString("uo"));
				}
				
			}else{
				sql.append(" SELECT od.descrizione_lunga as uo, padre.descrizione_lunga from tipocontrolloufficialeimprese tcu left join oia_nodo od on tcu.id_unita_operativa = od.id " +
		                 " left join oia_nodo padre on padre.id = od.id_padre where idcontrollo = ? and id_unita_operativa > 0  and ispezione = ? ");
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1,rs_campione.getInt("id_controllo_ufficiale"));
				pst.setInt(2,rs_campione.getInt("motivazione_campione"));
				rs = pst.executeQuery();
				
			}
			while(rs.next()){
				this.setServizio(rs.getString("servizio"));
				this.setUo(rs.getString("uo"));
			}
			
			
		}
		
		
	}
	
	public static String nuovoCodice(String barcode, int idAsl){
		String nuovo = "";
		if (barcode==null || barcode.equals(""))
			return "";
		
		nuovo = barcode+"-"+idAsl;
		//this.setBarcode_new(nuovo);
		return nuovo;
	}

	private boolean controllaUnivocitaBarcodeNew(String barcodeNew, Connection db){
		Boolean esito = true;
		
		try {
			String sql_barcodeosa = "select * from barcode_osa where barcode_new = ?";
			PreparedStatement pst_barcodeosa;
			pst_barcodeosa = db.prepareStatement(sql_barcodeosa);
		
		pst_barcodeosa.setString(1, barcodeNew);
		ResultSet rs_barcodeosa = pst_barcodeosa.executeQuery();
		while (rs_barcodeosa.next())
			esito = false;
		
		String sql_ticket = "select * from ticket where location_new = ? and trashed_date is null";
		PreparedStatement pst_ticket = db.prepareStatement(sql_ticket);
		pst_ticket.setString(1, barcodeNew);
		ResultSet rs_ticket = pst_ticket.executeQuery();
		while (rs_ticket.next())
			esito = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return esito;
	}

	public String getCodPreaccettazione() {
		return codPreaccettazione;
	}

	public void setCodPreaccettazione(String codPreaccettazione) {
		this.codPreaccettazione = codPreaccettazione;
	}

}