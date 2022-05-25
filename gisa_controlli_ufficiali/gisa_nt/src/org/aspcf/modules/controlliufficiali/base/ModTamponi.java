/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.soa.base.LineaAttivitaSoa;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;


public class ModTamponi {

	
	String barcodeMotivazione;
	String barcodeOSA;
	String barcodePrelievo;
	String barcodeMatrice;
	String matrice;
	String codiciMatrice;
	String analiti;
	
	String idControllo;
	String piani;
	String piano;
	String motivazione;
	String motivazione_campione;
	String tipoModulo;
	

	
	public ModTamponi(){}
	
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

	public String getBarcodeMatrice() {
		return barcodeMatrice;
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


	
	public ModTamponi(ResultSet rs) throws SQLException{
		
		if(rs.next()) {	
			
			idControllo = rs.getString("idControllo");
			piano = rs.getString("piano");
			motivazione = rs.getString("motivazione");
			motivazione_campione = rs.getString("motivazione_campione");
			barcodeMotivazione = rs.getString("barcode_motivazione");
			
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
                   " codice_esame "+
                   "  FROM "+ nomeDb+" WHERE id_padre = -1 and enabled = true "+ 
                   " UNION ALL "+
                   " SELECT "+
                   " t."+colonna+",t.nome,t.livello,t.id_padre,t.enabled, "+
                   " CAST(rt.path_id || ';' || t.id_padre As varchar(1000)),CAST(rt.path_desc || '->' || t.nome As varchar(1000)) As path_desc  ,t.enabled, "+
                   " rt.codice_esame || ';' || t.codice_esame "+
                   "  FROM "+nomeDb+" t "+
                   "   JOIN recursetree rt ON rt."+colonna+" = t.id_padre and t.enabled = true "+ 
                   "  ) "+
                   " SELECT * FROM recursetree where "+colonna+" = ? "); 
		pst = db.prepareStatement(sql.toString());
		pst.setInt(1, code);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			pathCodes.add(rs.getString("path_desc"));
			pathCodes.add(rs.getString("codice_esame"));
		}
	
		return pathCodes;
		          
    } 
	
	 public String generaCodice( Connection db, int suffix, int orgId, int id_tampone ) throws SQLException {
		 
		 StringBuffer sql = new StringBuffer();
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
		
		if (id_tampone != 0){
			 PreparedStatement check_barcode = db.prepareStatement("select * from barcode_osa where org_id = ? and id_campione = ? and barcode ilike ?");
			 check_barcode.setInt(1,orgId);
			 check_barcode.setString(2,Integer.toString(id_tampone));
			 check_barcode.setString(3,"%"+idpvsuf);
			 ResultSet rs = check_barcode.executeQuery();
			 while(rs.next()){	 
				 codicePrelievo = rs.getString("barcode");
			 }

			 
			 //non c'e' barcode
			 if(codicePrelievo.equals("") || codicePrelievo==null){
				 	int sequence = -1, j=0;
					sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_osa_serial_id_seq");
					String idCU = "";
					
					//prelevo id_controllo_ufficiale del campione
					PreparedStatement pst = null;
					pst = db.prepareStatement("select id_controllo_ufficiale from ticket where ticketid="+id_tampone);
					rs = pst.executeQuery();
					while (rs.next()){
						idCU = rs.getString("id_controllo_ufficiale");
					}
					pst=null;
					
					//inserisco il tampone in barcode_osa
					codicePrelievo = ""+id_tampone+sequence+idpvsuf;	
					sql.append("INSERT INTO barcode_osa(serial_id, org_id,id_campione,barcode,ticket_id) VALUES(?,?,?,?,?)"); 
					pst=db.prepareStatement(sql.toString());
					pst.setInt(++j,sequence);
					pst.setInt(++j,orgId);
					pst.setString(++j,Integer.toString(id_tampone));
					pst.setString(++j, codicePrelievo);
					pst.setInt(++j, Integer.parseInt(idCU));		
					pst.execute();
					pst.close();
			 }
			 rs.close();
			 check_barcode.close();
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
		    ArrayList<LineeAttivita> tipologia = LineeAttivita.load_linea_attivita_per_cu(
					Integer.toString(idControllo), db,-1);

			if (tipologia.size() > 0) {
				LineeAttivita linea = tipologia.get(0);
				org.setTipologia_att(linea.getCategoria());//tipologiaAttivita
			}

			else if (tic_vig.getCodiceAteco() != null) { 
				org.setTipologia_att(tic_vig.getCodiceAteco()+ " " + tic_vig.getDescrizioneCodiceAteco());
			}
			
			//Gestione tipologia attivita' Stabilimenti e SOA
			

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
			//Gestione dei barcode
			String barCodePrelievo = this.generaCodice(db, tipoMod, Integer.parseInt(orgId), idCampione);
			//Barcode Prelievo
			this.setBarcodePrelievo(barCodePrelievo);
			
			ArrayList<String> path_matrice = new ArrayList<String>();
			String descrizione = "";
			ArrayList<Integer> idMatrice = this.getIdFoglia(db,idCampione,"matrici","id_matrice");
			if(idMatrice.size() > 0){
				path_matrice = this.getPathAndCodesFromId(db, idMatrice.get(0),"matrici","matrice_id");
				if(path_matrice.size() > 0){
					this.setMatrice(path_matrice.get(0));
				}
					
				if( path_matrice.get(1)!= null && !path_matrice.get(1).equals("")){
					this.setCodiciMatrice(path_matrice.get(1));
				}
			}
			
			
				
			//Analiti
			ArrayList<String> path_analiti = new ArrayList<String>();
			ArrayList<Integer> idFoglie = new ArrayList<Integer>();
			idFoglie = this.getIdFoglia(db, idCampione,"analiti","analiti_id");
			for (int i = 0 ; i < idFoglie.size(); i++)
			{
				path_analiti = this.getPathAndCodesFromId(db,idFoglie.get(i),"analiti","analiti_id");
				if(path_analiti.size() > 0){
					 descrizione += path_analiti.get(0)+"  ---------  ";
				}
			}
			this.setAnaliti(descrizione);
				
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
			 		
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
	


}