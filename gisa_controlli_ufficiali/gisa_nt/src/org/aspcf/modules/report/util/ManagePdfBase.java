/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.report.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.derby.tools.sysinfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.soa.base.LineaAttivitaSoa;
import org.aspcfs.modules.vigilanza.base.Ticket;

import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.newvaluescorrelationname;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.zeroio.webutils.FileDownload;

public class ManagePdfBase {
	
	/*public void getFieldsFromQueryImpresa(ResultSet rs, Filtro f, int tipoModulo){
		  
		  try {
			  if (rs.next())
			  {
				  if(tipoModulo != 1){
					  f.setRagioneSociale(rs.getString("ragione_sociale"));
				      f.setTipologiaAttivita(rs.getString("tipologia"));
				      f.setRicCE(rs.getString("approval_number"));
				      f.setNum_reg(rs.getString("n_reg"));
				      f.setComune(rs.getString("comune"));
				      f.setIndirizzo(rs.getString("indirizzo"));
				      f.setCodiceFiscale(rs.getString("codice_fiscale"));
				      f.setPartitaIVA(rs.getString("partita_iva"));
				      f.setSedeLegale(rs.getString("sede_legale"));
				      f.setIndirizzoLegale(rs.getString("indirizzo_legale"));
				      f.setLegaleRappresentante(rs.getString("nome_rappresentante")+" "+rs.getString("cognome_rappresentante"));
				      f.setData_nascita_rappresentante(rs.getString("data_nascita_rappresentante"));
				      f.setLuogo_nascita_rappresentante(rs.getString("luogo_nascita_rappresentante"));
				      f.setCityLegaleRappresentante(rs.getString("citta_legale_rapp"));
				      f.setIndirizzoLegaleRappresentante(rs.getString("indirizzo_legale_rapp"));
				      f.setProvinciaLegaleRappresentante(rs.getString("provincia_legale_rapp")); 
			  		} else {
					  f.setRagioneSociale(rs.getString("ragione_sociale"));
				      f.setNum_reg(rs.getString("n_reg"));
				      f.setComune(rs.getString("comune"));
				      f.setCodiceFiscale(rs.getString("codice_fiscale"));
				      f.setPartitaIVA(rs.getString("partita_iva"));
				      f.setSedeLegale(rs.getString("sede_legale"));
				      f.setIndirizzoLegale(rs.getString("indirizzo_legale")); 
			  		}
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	 public void getFieldsFromQueryAllevamento(ResultSet rs, Filtro f){
		  
		  try {
			if (rs.next())
			  {
				  f.setRagioneSociale(rs.getString("ragione_sociale"));
			      f.setTipologiaAttivita(rs.getString("tipologia"));
			      f.setRicCE(rs.getString("approval_number"));
			      f.setNum_reg(rs.getString("n_reg"));
			      f.setComune(rs.getString("comune"));
			      f.setIndirizzo(rs.getString("indirizzo"));
			      //f.setCodiceFiscale(rs.getString("codice_fiscale"));
			      f.setPartitaIVA(rs.getString("partita_iva"));
			      f.setSedeLegale(rs.getString("sede_legale"));
			      f.setIndirizzoLegale(rs.getString("indirizzo_legale"));
			      f.setLegaleRappresentante(rs.getString("nome_rappresentante")+" "+rs.getString("cognome_rappresentante"));
			      f.setData_nascita_rappresentante(rs.getString("data_nascita_rappresentante"));
			      f.setLuogo_nascita_rappresentante(rs.getString("luogo_nascita_rappresentante"));
			      f.setCityLegaleRappresentante(rs.getString("citta_legale_rapp"));
			      f.setIndirizzoLegaleRappresentante(rs.getString("indirizzo_legale_rapp"));
			      f.setProvinciaLegaleRappresentante(rs.getString("provincia_legale_rapp")); 
			  }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	 
	 public void getFieldsFromQueryStabilimento(ResultSet rs, Filtro f){
		  
		  try {
			if (rs.next())
			  {
				  f.setRagioneSociale(rs.getString("ragione_sociale"));
			      f.setTipologiaAttivita(rs.getString("tipologia"));
			      f.setRicCE(rs.getString("approval_number"));
			      f.setNum_reg(rs.getString("n_reg"));
			      f.setCodiceImpianto(rs.getString("codice_impianto"));
			      f.setCodiceSezione(rs.getString("codice_sezione"));
			      f.setComune(rs.getString("comune"));
			      f.setIndirizzo(rs.getString("indirizzo"));
			      f.setCodiceFiscale(rs.getString("codice_fiscale"));
			      f.setSedeLegale(rs.getString("sede_legale"));
			      
			  }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } 
	 
	 public void getFieldsFromQuerySoa(ResultSet rs, Filtro f){
		  
		  try {
			if (rs.next())
			  {
				  f.setRagioneSociale(rs.getString("ragione_sociale"));
			      f.setTipologiaAttivita(rs.getString("tipologia"));
			      f.setRicCE(rs.getString("approval_number"));
			      f.setComune(rs.getString("comune"));
			      f.setIndirizzo(rs.getString("indirizzo"));
			      
			  }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } 
	 
	 public void getFieldsFromQueryOsm(ResultSet rs, Filtro f){
		  
		  try {
			if (rs.next())
			  {
				  f.setRagioneSociale(rs.getString("ragione_sociale"));
				  f.setCodiceFiscale(rs.getString("codice_fiscale"));
			      f.setTipologiaAttivita(rs.getString("tipologia"));
			      f.setNum_reg(rs.getString("n_reg"));
			      f.setComune(rs.getString("comune"));
			      f.setIndirizzo(rs.getString("indirizzo"));
			      
			  }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } 
	 */
	 
	 
	  /*public void setFieldFromQueryImpresa(AcroFields form, Filtro f) throws IOException, DocumentException{
		
		  form.setField("ragione_sociale", f.getRagioneSociale());	  
		  form.setField("tipologia_attivita", f.getTipologiaAttivita());
		  form.setField("n_reg",f.getNum_reg());
		  form.setField("ric",f.getRicCE());
		  form.setField("comune", f.getComune());
		    
		  if(f.getIndirizzo() != null && f.getIndirizzo().trim().length() >= 0){
			  form.setField("indirizzo", f.getIndirizzo());
		  }
		    
		  if(f.getCodiceFiscale().equals("")){
			  form.setField("codice_fiscale", f.getPartitaIVA());
		  }
		  else {
			  form.setField("codice_fiscale",f.getCodiceFiscale());
		  }
		 
		  form.setField("sede_legale",f.getSedeLegale()); 
		  form.setField("luogo_residenza",f.getCityLegaleRappresentante());
		  form.setField("indirizzo_legale",f.getIndirizzoLegale());
		  form.setField("indirizzo_legale_rappresentante",f.getIndirizzoLegaleRappresentante());
		  form.setField("legale_rappresentante", f.getLegaleRappresentante());
		  form.setField("luogo_nascita",f.getLuogo_nascita_rappresentante());
		  if(!f.getGiornoNascita().equals("")) {
			  form.setField("giorno_nascita",f.getGiornoNascita());
		      form.setField("mese_nascita",f.getMeseNascita());
		      form.setField("anno_nascita",f.getAnnoNascita().substring(2));
		  }
		
	  }
	    
	  public void setFieldFromQueryAllevamento(AcroFields form, Filtro f) throws IOException, DocumentException{
			
		  form.setField("ragione_sociale", f.getRagioneSociale());	  
		  form.setField("tipologia_attivita", f.getTipologiaAttivita());
		  form.setField("n_reg",f.getNum_reg());
		  form.setField("ric",f.getRicCE());
		  form.setField("comune", f.getComune());
		    
		  if(f.getIndirizzo() != null && f.getIndirizzo().trim().length() >= 0){
			  form.setField("indirizzo", f.getIndirizzo());
		  }
		   
		  form.setField("sede_legale",f.getSedeLegale()); 
		  form.setField("luogo_residenza",f.getCityLegaleRappresentante());
		  form.setField("indirizzo_legale",f.getIndirizzoLegale());
		  form.setField("indirizzo_legale_rappresentante",f.getIndirizzoLegaleRappresentante());
		  form.setField("legale_rappresentante", f.getLegaleRappresentante());
		  form.setField("luogo_nascita",f.getLuogo_nascita_rappresentante());
		  if(!f.getGiornoNascita().equals("")) {
			  form.setField("giorno_nascita",f.getGiornoNascita());
		      form.setField("mese_nascita",f.getMeseNascita());
		      form.setField("anno_nascita",f.getAnnoNascita().substring(2));
		  }
		 
	  }
	  
	  public void setFieldFromQuerystabilimento(AcroFields form, Filtro f) throws IOException, DocumentException
	  {
			
		  form.setField("ragione_sociale", f.getRagioneSociale());	  
		  form.setField("tipologia_attivita", f.getTipologiaAttivita());
		  form.setField("n_reg",f.getNum_reg());
		  form.setField("ric",f.getRicCE());
		  form.setField("comune", f.getComune());
		  form.setField("codice_fiscale",f.getCodiceFiscale());  	 
		  form.setField("sede_legale",f.getSedeLegale()); 
		  form.setField("indirizzo",f.getIndirizzo());
		
	  }
	  
	  
	  public void setFieldFromQuerySoa(AcroFields form, Filtro f) throws IOException, DocumentException
	  {
			
		  form.setField("ragione_sociale", f.getRagioneSociale());	  
		  form.setField("tipologia_attivita", f.getTipologiaAttivita());
		  form.setField("ric",f.getRicCE());
		  form.setField("comune", f.getComune()); 
		  form.setField("indirizzo",f.getIndirizzo());
		
	  }
	  
	  
	  public void setFieldFromQueryOsm(AcroFields form, Filtro f) throws IOException, DocumentException
	  {
			
		  form.setField("ragione_sociale", f.getRagioneSociale());	  
		  form.setField("tipologia_attivita", f.getTipologiaAttivita());
		  form.setField("codice_fiscale", f.getCodiceFiscale());
		  form.setField("n_reg",f.getNum_reg());
		  form.setField("comune", f.getComune()); 
		  form.setField("indirizzo",f.getIndirizzo());
		
	  }*/
	  
	   public int calculateIndex(String s, int index){
			
			while(s.charAt(index)!= ' '){
				//System.out.println("charAt vale: "+ s.charAt(index));
				index -- ;
			}
			
			return index;
	  }
	   
	   public int getTipologia (int orgId, Connection db){
		   int tipologia = 0;
		   try {
			   String sql = "select tipologia from organization where org_id = ? ";
			   PreparedStatement pst = db.prepareStatement(sql);
			   pst.setInt(1,orgId);
			   ResultSet rs = pst.executeQuery();
			   if(rs.next()){
				   tipologia = rs.getInt("tipologia");
			   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tipologia;
		   
		  
	   }
	   
	   /*Get site_id from operatore for logo*/
	   public int getAslFromOSA (int orgId, Connection db){
		   int tipologia = 0;
		   try {
			   String sql = "select site_id from organization where org_id = ? ";
			   PreparedStatement pst = db.prepareStatement(sql);
			   pst.setInt(1,orgId);
			   ResultSet rs = pst.executeQuery();
			   if(rs.next()){
				   tipologia = rs.getInt("site_id");
			   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tipologia;
		   
		  
	   }
	   
	   
	   public void setBarcodeImpresa(AcroFields form, PdfStamper stamper, String code){  
	   
	   	  PdfContentByte overContent = stamper.getOverContent(2);
		  Barcode128 code128 = new Barcode128();
		  code128.setCode(code);
		  Image image128 = code128.createImageWithBarcode(overContent, null, null);
		  float[] barcodeArea = form.getFieldPositions("barcode");
		  Rectangle rect = new Rectangle(
					barcodeArea[1], barcodeArea[2], barcodeArea[3], barcodeArea[4]);
		  image128.scaleToFit(170,40);
		  //image128.setAbsolutePosition(26.4F,16.18F);
		  image128.setAbsolutePosition(barcodeArea[1], barcodeArea[2]);
		  try {
			overContent.addImage(image128);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	   }
	   
	   public void setBarcodeOrgid(AcroFields form, PdfStamper stamper, String code){  
		   
		   	  PdfContentByte overContent = stamper.getOverContent(1);
			  Barcode128 code128 = new Barcode128();
			  code128.setCode(code);
			  Image image128 = code128.createImageWithBarcode(overContent, null, null);
			  float[] barcodeArea = form.getFieldPositions("barcode_orgId");
			  Rectangle rect = new Rectangle(
						barcodeArea[1], barcodeArea[2], barcodeArea[3], barcodeArea[4]);
			  image128.scaleToFit(170,40);
			  //image128.setAbsolutePosition(26.4F,16.18F);
			  image128.setAbsolutePosition(barcodeArea[1],barcodeArea[2]);

			  //image128.setAbsolutePosition(barcodeArea[1], barcodeArea[2]);
			  try {
				overContent.addImage(image128);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		   }
		
	   public void setBarcodeStabilimento_Soa_Osm(AcroFields form, PdfStamper stamper, String code){  
		   
		   	  PdfContentByte overContent = stamper.getOverContent(2);
			  Barcode128 code128 = new Barcode128();
			  code128.setCode(code);
			  Image image128 = code128.createImageWithBarcode(overContent, null, null);
			  float[] barcodeArea = form.getFieldPositions("barcode");
			  Rectangle rect = new Rectangle(
						barcodeArea[1], barcodeArea[2], barcodeArea[3], barcodeArea[4]);
			  image128.scaleToFit(170,40);
			  image128.setAbsolutePosition(barcodeArea[1], barcodeArea[2]);
			  //image128.setAbsolutePosition(26.4F,5.08F);
			  //image128.setWidthPercentage(30);
			  try {
				overContent.addImage(image128);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
	   }
	   
	   public BufferedImage createBarcodeImage(String code){  
		   
		   Barcode128 code128 = new Barcode128();
			code128.setCode(code);
			java.awt.Image im = code128.createAwtImage(Color.BLACK, Color.WHITE);
			int w = im.getWidth(null);
		    int h = im.getHeight(null);
		    BufferedImage img = new BufferedImage(w, h+12, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2d = img.createGraphics();
		    g2d.drawImage(im, 0, 0, null);
		    g2d.drawRect(0, h, w, 12);
		    g2d.fillRect(0, h+1, w, 12);
		    g2d.setColor(Color.WHITE);
		    String s = code128.getCode();
		    g2d.setColor(Color.BLACK);
		    g2d.drawString(s,h+2,34);
		    g2d.dispose();
		    
		    return img;
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
		      
	   
	   
	   
	    public String generaCodice( Connection db, int suffix, int orgId, int ticketId ) throws SQLException {
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
			
			int sequence = -1, j=0;
			sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_prelievo_id_seq");
			//String codicePrelievo = this.getCodice(db, ticketId, idpvsuf);
			PreparedStatement pst = null;
			
			
			if (ticketId != 0){
				//Per ora non e' gestito il controllo ufficiale
				codicePrelievo = ""+ticketId+sequence+idpvsuf;	
				sql.append("INSERT INTO barcode_osa(serial_id, org_id,ticket_id,barcode) VALUES(?,?,?,?)"); 
				pst=db.prepareStatement(sql.toString());
				pst.setInt(++j,sequence);
				pst.setInt(++j,orgId);
				pst.setInt(++j,ticketId);
				pst.setString(++j, codicePrelievo);
				
			}
			else if(orgId != 0){
				codicePrelievo = this.getPaddedId(sequence)+idpvsuf;
				sql.append("INSERT INTO barcode_osa(serial_id,org_id,barcode) VALUES(?,?,?)"); 
				pst=db.prepareStatement(sql.toString());
				pst.setInt(++j,sequence);
				pst.setInt(++j,orgId);
				pst.setString(++j, codicePrelievo);
			}
			
			pst.execute();
			pst.close();
			
			return codicePrelievo;
			          
	    }
	     
	    public String getCodice(Connection db,int ticketId, String idpvsuf) throws SQLException{
	    	
	    	PreparedStatement pst = null;
			ResultSet res = null;
			String codice = "";
			StringBuffer sql = new StringBuffer();
			sql.append("select barcode from barcode_osa where ticket_id = ? and barcode ilike ? ");
			pst= db.prepareStatement(sql.toString());
			pst.setInt(1,ticketId);
			pst.setString(2,"%"+idpvsuf+"%");
			res = pst.executeQuery();
			while(res.next()){
				  codice = res.getString("barcode");
			}
			
			res.close();
			pst.close();
			return codice;
	    }
	    
	    
	    public String getPaddedId(int sequence) {
		    String padded = (String.valueOf(sequence));
		    while (padded.length() < 6) {
		      padded = "0" + padded;
		    }
		    return padded;
		  }

	    
	    public void rollback_sequence(Connection db, int suffix) throws SQLException {
		  	
		  	String sequence = "" ;
		  	sequence = "barcode_prelievo_id_seq";
		  	String roll_back = "select setval('"+sequence+"', (select last_value from "+sequence+")-1)";
		  	db.prepareStatement(roll_back).execute();
	    }
	    
	    public void rollback_sequence(Connection db, int suffix, int org_id) throws SQLException {
		  	
		  	String sequence = "" ;
		  	sequence = "barcode_prelievo_id_seq";
		  	String roll_back = "select setval('"+sequence+"', (select last_value from "+sequence+")-1)";
		  	db.prepareStatement(roll_back).execute();
	    }

	    public String generaCodice( Connection db, int orgId, int ticketId ) throws SQLException {
			  
			//Identificativo prelievo suffisso
			int sequence = -1;
			sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_etichetta_vp_id_seq");
			String barcode = "E"+this.getPaddedId(sequence)+"VP";
			
			return barcode;
			          
	    }
	    
	    public String generaCodice( Connection db) throws SQLException {
			  
			//Identificativo prelievo suffisso
			int sequence = -1;
			sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_etichetta_vp_id_seq");
			String barcode = "E"+this.getPaddedId(sequence)+"VP";
			
			return barcode;
			          
	    }
	    
	    
	    public ArrayList<String> getCodiceMatrici( Connection db, int orgId, int ticketId ) throws SQLException {
			  
			ArrayList<String> codici_matrici = new ArrayList<String>();
			//recuperare il codice_esame di tutti i livelli...fino ad un massimo di 3
			
			
			
			
			
			
			return codici_matrici;
			          
	    } 
	    
	    
	   //R.M : Per ora il codice analita e' l'ID. Quando saranno disponibili quelli effettivi, riempire la colonna 
	   //codice_esame della tabella analiti.
	    public ArrayList<String> getCodiceAnalita( Connection db, int orgId, int ticketId ) throws SQLException {
			  
			StringBuffer sql_analita = new StringBuffer();
			StringBuffer sql  = new StringBuffer();
			ArrayList<String> codice_analita = new ArrayList<String>();
			PreparedStatement pst_analita = null;
			//Per prova e' stato preso l'id come codice_esame 
			sql_analita.append("select codice_esame from analiti a left join analiti_campioni ac on a.analiti_id= ac.analiti_id where ac.id_campione = ?");
			pst_analita = db.prepareStatement(sql_analita.toString());
			pst_analita.setInt(1, ticketId);
			ResultSet rs = pst_analita.executeQuery();
			int i=-1;
			while (rs.next()){
				i++;
				codice_analita.add(rs.getString("codice_esame")); 
			}
			
			PreparedStatement pst = null;
			int sequence = -1, j=0;
			sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_prelievo_id_seq");
			
			//gestire il pregresso...codice_analita puo essere null
			//nel caso del barcodeAnaliti il codice puo essere ripetuto per diversi campioni;
			//questo e' il motivo per il quale non viene generato sequence+codice_esame ma solo codice_esame.
			
			if (ticketId != 0){
				sql.append("INSERT INTO barcode_osa(serial_id, org_id,ticket_id,barcode) VALUES(?,?,?,?)"); 
				pst=db.prepareStatement(sql.toString());
				for(int k=0;k<codice_analita.size();k++){
					//Per ora non e' gestito il controllo ufficiale 
					pst.setInt(++j,sequence);
					pst.setInt(++j,orgId);
					pst.setInt(++j,ticketId);
					pst.setString(++j, codice_analita.get(k));
					pst.execute();
					j=0;
					
				}
				pst.close();	
			}
			else {
				sql.append("INSERT INTO barcode_osa(serial_id,org_id,barcode) VALUES(?,?,?)"); 
				pst=db.prepareStatement(sql.toString());
				for(int k=0;k<codice_analita.size();k++){
					pst.setInt(++j,sequence);
					pst.setInt(++j,orgId);
					pst.setString(++j, codice_analita.get(k));
					pst.execute();
					j=0;
					
				}
				pst.close();
			}
			
			//pst.execute();
			
			return codice_analita;
			          
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
	    
	    //R.M : Per ora il codice matrice e' l'ID. Quando saranno disponibili quelli effettivi, riempire la colonna 
		//codice_esame della tabella matrici.
	    public String getCodiceMatrice( Connection db, int orgId, int ticketId ) throws SQLException {
			  
			StringBuffer sql_matrice = new StringBuffer();
			StringBuffer sql  = new StringBuffer();
			String codice_matrice = null;
			PreparedStatement pst_matrice = null;
			sql_matrice.append("select codice_esame from matrici m left join matrici_campioni mc on m.matrice_id= mc.id_matrice where mc.id_campione = ?");
			pst_matrice = db.prepareStatement(sql_matrice.toString());
			pst_matrice.setInt(1, ticketId);
			ResultSet rs = pst_matrice.executeQuery();
			while (rs.next()){
				codice_matrice = rs.getString("codice_esame");
			}
			
			PreparedStatement pst = null;
			int sequence = -1, j=0;
			sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_prelievo_id_seq");
			
			String codicePrelievo = codice_matrice;
			
			if (ticketId != 0){
				//Per ora non e' gestito il controllo ufficiale 
				sql.append("INSERT INTO barcode_osa(serial_id,org_id,ticket_id,barcode) VALUES(?,?,?,?)"); 
				pst=db.prepareStatement(sql.toString());
				pst.setInt(++j,sequence);
				pst.setInt(++j,orgId);
				pst.setInt(++j,ticketId);
				pst.setString(++j, codicePrelievo);
				
			}
			else {
				sql.append("INSERT INTO barcode_osa(serial_id,org_id,barcode) VALUES(?,?,?)"); 
				pst=db.prepareStatement(sql.toString());
				pst.setInt(++j,sequence);
				pst.setInt(++j,orgId);
				pst.setString(++j, codicePrelievo);
			}
			
			pst.execute();
			pst.close();
			
			return codicePrelievo;
			          
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
	    
	    
	    public String getDescFromCode(Connection db,int code, String nomeDb, String colonna) throws SQLException {
			  
			StringBuffer sql  = new StringBuffer();
			String descrizione = null;
			PreparedStatement pst = null;
			sql.append("select nome from "+nomeDb+" where "+colonna+" = ?");
			pst = db.prepareStatement(sql.toString());
			pst.setInt(1, code);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				descrizione = rs.getString("nome");
			}
		
			return descrizione;
			          
	    } 
	    
	    
	    public void rollback_sequence_etichette(Connection db) throws SQLException {
		  	
		  	String sequence = "" ;
		  	sequence = "barcode_etichetta_vp_id_seq";
		  	String roll_back = "select setval('"+sequence+"', (select last_value from "+sequence+")-1)";
		  	db.prepareStatement(roll_back).execute();
	    }

	   
	    
		public void setBarcodePrelievo(AcroFields form, PdfStamper stamper, String code) {
			
			  //Ora genero il barcode!
			  PdfContentByte overContent = stamper.getOverContent(1);
			  Barcode128 code128 = new Barcode128();
			  code128.setCode(code);
			  Image image128 = code128.createImageWithBarcode(overContent, null, null);
			  float[] barcodeArea = form.getFieldPositions("barcode_prelievo");
			  Rectangle rect = new Rectangle(
						barcodeArea[1], barcodeArea[2], barcodeArea[3], barcodeArea[4]);
			  //image128.setAbsolutePosition(480.7F,760.695F);
			  image128.setAbsolutePosition(barcodeArea[1], barcodeArea[2]);

			  try {
					overContent.addImage(image128);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  /*image128.scaleToFit(170,40);
			  //image128.setWidthPercentage(30);
			  try {
				//overContent.addImage(image128);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
				
		}
		
		public void setBarcodeMatrici(AcroFields form, PdfStamper stamper, String code,int i){  
			  
		   	  //PdfContentByte overContent = stamper.getOverContent(1);
		   	  PdfContentByte overContent = stamper.getOverContent(2);
			  Barcode128 code128 = new Barcode128();
			  code128.setCode(code);
			  Image image128 = code128.createImageWithBarcode(overContent, null, null);
			  try {
				  float[] barcodeArea = form.getFieldPositions("barcode_matrici_"+i);
				  if(image128 != null){
					  image128.scaleToFit(170,40);
					  image128.setAbsolutePosition(barcodeArea[1],barcodeArea[2]);
					  overContent.addImage(image128);
				  }
			
			  //image128.setAbsolutePosition(barcodeArea[1]+226F,barcodeArea[2]-255);
			 
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
			
			
		   }
	
		
		@SuppressWarnings("null")
		public void setBarcodeMatrici(AcroFields form, PdfStamper stamper, String code){  
			   
		   	  PdfContentByte overContent = stamper.getOverContent(1);		   	  
			  Barcode128 code128 = new Barcode128();
			  code128.setCode(code);
			  Image image128 = code128.createImageWithBarcode(overContent, null, null);
			  float[] barcodeArea = form.getFieldPositions("barcode_matrici");
			   image128.scaleToFit(170,40);
			  image128.setAbsolutePosition(barcodeArea[1],barcodeArea[2]);
			  //image128.setAbsolutePosition(barcodeArea[1]+186F,barcodeArea[2]-165);
			  try {
				overContent.addImage(image128);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
		
		public void setBarcodeMotivazione(AcroFields form, PdfStamper stamper, String code){  
			   
		   	  PdfContentByte overContent = stamper.getOverContent(2);
			  Barcode128 code128 = new Barcode128();
			  code128.setCode(code);
			  Image image128 = code128.createImageWithBarcode(overContent, null, null);
			  float[] barcodeArea = form.getFieldPositions("barcode_mot");
			  image128.scaleToFit(170,40);
			  image128.setAbsolutePosition(barcodeArea[1],barcodeArea[2]);
			  //image128.setAbsolutePosition(barcodeArea[1]+186F,barcodeArea[2]-165);
			  try {
				overContent.addImage(image128);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
		
		
		public void setBarcodeAnaliti(AcroFields form, PdfStamper stamper, String code,int i){  
			  
			  ++i;
		   	  //PdfContentByte overContent = stamper.getOverContent(1);
		   	  PdfContentByte overContent = stamper.getOverContent(2);
			  Barcode128 code128 = new Barcode128();
			  code128.setCode(code);
			  Image image128 = code128.createImageWithBarcode(overContent, null, null);
			  try {
				  float[] barcodeArea = form.getFieldPositions("barcode_analiti_"+i);
				  if(image128 != null){
					  image128.scaleToFit(170,40);
					  image128.setAbsolutePosition(barcodeArea[1],barcodeArea[2]);
					  overContent.addImage(image128);
				  }
			
			  //image128.setAbsolutePosition(barcodeArea[1]+226F,barcodeArea[2]-255);
			 
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
			
			
		   }
		public void setDescFromCode(AcroFields form, PdfStamper stamper, String desc) {
		
			
		}
		
		
		public void setLogoFromAsl(String reportDir, String reportDirIm, AcroFields form, PdfStamper stamper, int value_logo,Connection db) throws DocumentException, MalformedURLException, IOException, SQLException{
		
			PdfContentByte cb = stamper.getOverContent(1);
			Image image = null;
			if(value_logo == -1){
		    	image = Image.getInstance(reportDir + "/images/regionecampania.jpg");
		    }
			
			LookupList asl = new LookupList(db,"lookup_site_id");
			image = Image.getInstance(reportDirIm + "/images/"+asl.getSelectedValue(value_logo)+".jpg");
			cb.addImage(image, 70F, 0, 0, 50F, 30, PageSize.A4.getHeight()- 70);
			cb.stroke();
			cb.saveState();
			
		
		}
		
		// Modifica toUppercase
		public void setFieldFromQuery(ResultSet rs, AcroFields form, Filtro f, int tipoOp) throws SQLException, IOException, DocumentException{
			 ResultSetMetaData rsMetaData = rs.getMetaData();
			  int numberOfColumns = rsMetaData.getColumnCount();
			  while(rs.next()){
			
				  switch (tipoOp){
				  	case 1 : f.setNum_reg(rs.getString("n_reg").toUpperCase()); break;
				  	case 3:  f.setRicCE(rs.getString("approval_number").toUpperCase());
				      		 f.setCodiceImpianto(rs.getString("codice_impianto").toUpperCase());
				      		 f.setCodiceSezione(rs.getString("codice_sezione").toUpperCase());
				      		 break;
				  	case 97:  f.setRicCE(rs.getString("approval_number").toUpperCase()); break;
				  	case 800: f.setNum_reg(rs.getString("n_reg").toUpperCase()); 
				  			  if(rs.getString("tipologia") != null && !(rs.getString("tipologia").equals("")) ){
					  			  f.setTipologiaAttivita("OSM: "+rs.getString("tipologia").toUpperCase());	break;
				  			  }
				  			  else {
				  				  f.setTipologiaAttivita("OSM RICONOSCIUTI");
				  			  }
				  			  break;
				  	default:  
				  			if(rs.getString("n_reg") != null && !rs.getString("n_reg").equals(""))
				  				f.setNum_reg(rs.getString("n_reg").toUpperCase());
				  			if(rs.getString("approval_number") != null && !rs.getString("approval_number").equals(""))
				  				f.setRicCE(rs.getString("approval_number").toUpperCase());
				  	break;
				  }
				  
				  String nomeC = "";
				  for(int i=1;i<=numberOfColumns;i++){
					  nomeC = rsMetaData.getColumnName(i);
					  if(form.getField(nomeC) != null && (rs.getString(nomeC)) != null){
						  form.setField(nomeC, rs.getString(nomeC).toUpperCase());
					  }  
				  }
				  
			  }//End while
		
		}
		
		
		public void setFieldFromCU(AcroFields form, ResultSet rs, Filtro f, Connection db) throws SQLException, IOException, DocumentException {
			 
			ResultSetMetaData rsMetaData = rs.getMetaData();
			  int numberOfColumns = rsMetaData.getColumnCount();
			
			while(rs.next()){
	    		
				//Id padre per recuperare la linea di attivita
				f.setIdControllo(Integer.parseInt(rs.getString("idControllo")));
				f.setMotivazione(rs.getString("barcode_motivazione"));
				f.setPiano(rs.getString("piano"));
				f.setData_nascita_rappresentante(rs.getString("data_nascita_rappresentante"));
	    		f.setData_referto(rs.getString("data_referto"));
				String nomeC = "";
				  for(int i=1;i<=numberOfColumns;i++){
					  nomeC = rsMetaData.getColumnName(i);
					  if(form.getField(nomeC) != null && (rs.getString(nomeC)) != null){
						  form.setField(nomeC, rs.getString(nomeC).toUpperCase());
					  }  
				  }
			}
			if(f.getData_referto()!= null){
			    	form.setField("anno",f.getData_referto().substring(0,4));
				    form.setField("giorno",f.getData_referto().substring(8,11));
				    form.setField("mese",f.getMeseFromData(f.getData_referto()));
			}
			    

    		if(!f.getGiornoNascita().equals("")) {
		    	form.setField("giorno_nascita",f.getGiornoNascita());
		    	form.setField("mese_nascita",f.getMeseNascita());
		    	form.setField("anno_nascita",f.getAnnoNascita());
			}
    		
    		//Gestione tipologia attivita'
    		// Gestione nuova tipologia
		    ArrayList<LineeAttivita> tipologia = LineeAttivita.load_linea_attivita_per_cu(
					Integer.toString(f.getIdControllo()), db,-1);

			if (tipologia.size() > 0) {
				LineeAttivita linea = tipologia.get(0);
				form.setField("tipologia",linea.getCategoria());//tipologiaAttivita
			}
			
//			 ArrayList<String> stab_soa = LineaAttivitaSoa.load_linea_attivita_per_cu(
//						Integer.toString(f.getIdControllo()), db);
//			 
//			 if (stab_soa.size() > 0) {
//					form.setField("tipologia",stab_soa.get(0));//tipologiaAttivita
//				}
			 
		}
		
		public void setFieldCampione(Connection db, AcroFields form, Filtro f) throws SQLException, IOException, DocumentException{
		
			org.aspcfs.modules.campioni.base.Ticket tic = new org.aspcfs.modules.campioni.base.Ticket(db, f.getIdCampione());
			int idC = 0;
			if(tic.getIdControlloUfficiale().length()< 6){
				idC = Integer.parseInt(tic.getIdControlloUfficiale().substring(1));
			}
			else {
				idC = Integer.parseInt(tic.getIdControlloUfficiale());
			}
			
			Ticket controllo = new Ticket(db,idC);
			  
			  String idLineaAttivita = ""+controllo.getId_imprese_linee_attivita();
		      LineeAttivita la = LineeAttivita.load_linea_attivita_per_id(idLineaAttivita, db);
		     
		      if (la != null)
		      {
		      if (!la.getLinea_attivita().isEmpty())
		    	  idLineaAttivita = la.getCodice_istat() + " : " + la.getCategoria() + " - " + la.getLinea_attivita();
					// val[i] = linea.getCategoria() + " - " + linea.getLinea_attivita();
				else
				  idLineaAttivita = la.getCodice_istat() + " : " + la.getCategoria();
		      
		      form.setField("tipologia", idLineaAttivita);
		      }
		      else if(controllo.getCodiceAteco()!= null){
		    	  form.setField("tipologia", controllo.getCodiceAteco()+" "+controllo.getDescrizioneCodiceAteco());
		      }
		      
		      
			/*if(tic.getMotivazione().toLowerCase().contains("sospetto")){
				form.setField("su_sospetto","On");
		 	}
			else if(tic.getMotivazione().toLowerCase().contains("monitoraggio")) {
				form.setField("piano_monitoraggio","On");
			}
			else{
				form.setField("per", "On");
				form.setField("edit_per", tic.getNoteMotivazione());
			}*/
		}
		
		
		public void insertImageBarcode(Connection db, FileInputStream fis, File outputfile, int orgId, int ticketId, String barcode, boolean automatico){
			
		    String sql = "INSERT INTO etichette_verbali_prelievo(org_id,ticket_id,barcode,bmp,automatico) VALUES(?,?,?,?,?)";
		    PreparedStatement pst;
		    int j=0;
			try {
				pst = db.prepareStatement(sql);
				pst.setInt(++j,orgId);
				pst.setInt(++j,ticketId);
				pst.setString(++j, barcode);
				pst.setBinaryStream(++j, fis, (int) outputfile.length());
				pst.setBoolean(++j,automatico);
				pst.execute();
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    
			
		}
		
		
		public String retrieveImageBarcode(Connection db, OutputStream out, int org_id, int ticket_id) throws IOException{
			
			//Cambiare la query in seguito alla creazione della nuova tabella.
			PreparedStatement ps;
			String imageName = null;
			int i=0;
			try {
				ps = db.prepareStatement("SELECT bmp, barcode FROM etichette_verbali_prelievo WHERE org_id= ? and ticket_id = ?");
				ps.setInt(++i, org_id);
				ps.setInt(++i,ticket_id);
				 ResultSet rs = ps.executeQuery();
				    byte[] imgBytes = null;
				    if (rs != null) {
				        while(rs.next()) {
				            imgBytes = rs.getBytes(1);
				            imageName = rs.getString(2);
				        }
				        out.write(imgBytes);
				        rs.close();  
				    }
				    ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 
			} 
		   
			
		    return imageName;
			
		}

		
		
	    
}
