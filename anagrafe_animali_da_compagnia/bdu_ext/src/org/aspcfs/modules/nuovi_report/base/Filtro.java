/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nuovi_report.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Locale;
import java.util.Date;

import org.aspcfs.modules.nuovi_report.util.ApplicationProperties;

import com.darkhorseventures.framework.beans.GenericBean;

public class Filtro extends GenericBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id_asl            = -1;			//id dell'asl
	private Timestamp entered     = null;		//data in cui è stato inserito il microchip
	private Timestamp fine        = null;		
	private int totCani           = 0;			//numero totale di cani sterilizzati- microchippati
	
	private int totCaniCatt           = 0;		
	private int totCaniSter           = 0;		
	
	private String fine2          = null;		//data in cui è stato inserito il microchip
	private String entered2       = null;
	
	private String asl            = null;		//nome dell'asl
	private static 			Date 				data_inizio			= null;
	private static final 	String 				format 				= "yyyy-MM-dd HH:mm:ss";
	private static final 	String 				inizio				= "2000-06-01 00:00:00";
	private Timestamp ster_inizio = null;		//data in cui il cane èstato sterilizzato
	private Timestamp ster_fine   = null;
	private String ster_inizio2   = null;		//data in cui il cane èstato sterilizzato
	private String ster_fine2     = null;
	
	private String vivo			  = null;
	private String tat_mic		  = null;
	static String tatuaggio		  ="TATUAGGIO";
	static String  microchip	  = "MICROCHIP";
	
	private String restituiti=null;
	
	private static 			SimpleDateFormat	sdf					= null;
	private String cattura1=null; //liite inf
	private String cattura2=null;//limite max
	
	private Timestamp cattura_in1   = null;
	private Timestamp cattura_fin2   = null;

	private int totCessioni=0;

	private int totSmarriti=0;
	private boolean regione = false;

	private String cattura;

	private String statoC;

	private String contributo;

	private int totCaniSterCatt=0;

	private int totCaniSterReimmessi=0;

	private int totcaniSterAdottati=0;

	private String comune_cattura;

	private String comune_proprietario;

	private int id_comune=-1;
	
	/**
	   * Constructor for the Filtro object
	   */
	public Filtro() { }
	
	public ResultSet queryRecord2( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			 //inizializzazione della connessione
			 Connection conn = db;
			 String qry = componiQuery();
			 	 
			 PreparedStatement pst = db.prepareStatement(qry);
			 
			 int i=0;
			 if(id_asl != -1)
					pst.setInt(++i, id_asl);
			 if(entered != null)
					pst.setTimestamp(++i, entered);
			 if(fine != null)
				 pst.setTimestamp(++i, fine);
				 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
		public String componiQuery(){
			String qry=null;
		//controllo se fuori regione
			if(regione==true){
				qry=ApplicationProperties.getProperty( "FUORI_REGIONE" );
			}
			else{
				qry=ApplicationProperties.getProperty( "NUM_CANI_ANAGRAFATI" );
				if(id_asl != -1) {
					qry+= ApplicationProperties.getProperty( "ASL" );
				}
			}
			if (tat_mic != null){
					if(tat_mic.equals("3")){
						qry+= ApplicationProperties.getProperty( "CHIP" );
					/*	if(entered != null) {
							qry+= ApplicationProperties.getProperty( "REG_IN" );
						}
						if(fine != null) {
							qry+= ApplicationProperties.getProperty( "REG_FIN" );
						}*/
					}
					else {
						qry+= ApplicationProperties.getProperty( "TAT" );
					/*	if(entered != null) {
							qry+= ApplicationProperties.getProperty( "REG_IN" );
						}
						if(fine != null) {
							qry+= ApplicationProperties.getProperty( "REG_FIN" );
						}*/
					}
					
				}
			if(entered != null) {
				qry+= ApplicationProperties.getProperty( "REG_IN" );
			}
			if(fine != null) {
				qry+= ApplicationProperties.getProperty( "REG_FIN" );
			}
				if(vivo != null){
					if(vivo.equals("1")){
						qry+= ApplicationProperties.getProperty( "CANI_MORTI" );
					}
					else { 
						if (vivo.equals("2")) {
							qry+= ApplicationProperties.getProperty( "CANI_VIVI" );
						}
					}
				
			}
		
		return qry;
	}
	public String calcoloASL(Connection db)throws SQLException {
	     
		try {

			//inizializzazione della connessione
			 Connection conn = db;
			 ResultSet rs = null;
			 String qry = ApplicationProperties.getProperty( "RIF_ASL" );
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1,id_asl);	
			 rs = pst.executeQuery();
			 
			 if(rs.next())
				 this.asl = rs.getString( "description" );
		 } catch (SQLException e) {
		      throw new SQLException(e.getMessage());
		 }
		return this.asl;
	  }
	
	public String sterilizzatiQuery() {
		
		String qry = ApplicationProperties.getProperty( "NUM_CANI_STER" );
		if( id_asl != -1 ) {
			qry+= ApplicationProperties.getProperty( "NUMERO_CANI_ASL" );
		} 
		if( ster_inizio != null ) {
			qry+= ApplicationProperties.getProperty( "DATA_INS" );
		}
		
		if( ster_fine != null ) {	
			qry+= ApplicationProperties.getProperty( "DATA_ENDS" );
		}
	
		return qry;
	}

public ResultSet queryRecord4(Connection db)throws SQLException {
	    
		ResultSet rs = null;
		try{
			 //inizializzazione della connessione
			 Connection conn = db;
			 String qry = sterilizzatiQuery();
			 	 
			 PreparedStatement pst = db.prepareStatement(qry);
			 int i=0;
			 if(id_asl != -1)
					pst.setInt(++i, id_asl);
			 if(entered != null)
					pst.setTimestamp(++i, entered);
			 if(fine != null)
				 pst.setTimestamp(++i, fine);
			
			 rs=pst.executeQuery();
			
			
			
		} catch (SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord3(Connection db)throws SQLException {
	    
		ResultSet rs = null;
		try{
			 //inizializzazione della connessione
			 Connection conn = db;
			 String qry = sterilizzatiQuery();
			 	 
			 PreparedStatement pst = db.prepareStatement(qry);
			 int i = 0;
			 if(id_asl != -1)
					pst.setInt(++i, id_asl);
			 if(ster_inizio != null)
					pst.setTimestamp(++i, ster_inizio);
			 if(ster_fine != null)
				 pst.setTimestamp(++i, ster_fine);
			 rs = pst.executeQuery();
			  
		} catch (SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }

	//setta l'id dell'asl selezionata
	public void setId_asl( int id_asl ) {
			this.id_asl = id_asl;
	}
	
	public int getId_asl() {
			return id_asl;
	}
	
	public void setEntered( Timestamp entered ) {
		this.entered = entered;
	}

    public void setEntered( String tmp ) throws ParseException {
	   
    	if (tmp != "" && tmp!=null){
    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    		Date parsedDate = dateFormat.parse(tmp);
    		this.entered = new Timestamp(parsedDate.getTime());
    	}
    }
    
    public Timestamp getEntered() {
		return entered;
	}

	public void setTotCani(int totCani) {
		this.totCani = totCani;
	}

	public int getTotCani() {
		return totCani;
	}

	public void setFine(String fine) throws ParseException {
		
		if (fine != "" && fine!=null){
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
			Date parsedDate2 = dateFormat2.parse(fine);
			this.fine =new Timestamp(parsedDate2.getTime());
		}
	}

	public void setFine(Timestamp fine) {
		this.fine = fine;
	}
	
	public Timestamp getFine() {
		return this.fine;
	}

	public String getFine1() {
		
		if(fine != null){
				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
				fine2 = formatter.format(fine);}
		else 
				fine2 = "";
        return fine2;
   }
	
	public void setAsl(String tmp){
		this.asl = tmp;
	}
	
	public String getAsl() {
		return this.asl;
	}

	public String getEntered2() {
		
		if(entered != null){
				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
				entered2 = formatter.format(entered);}
		else 
				entered2 = "";
        
		return entered2;
   }
		
	protected void buildRecord(ResultSet rs) throws SQLException {
	    entered = rs.getTimestamp("po_microcip");
	    id_asl  = rs.getInt("asl_rif");
	    fine    = rs.getTimestamp("po_microcip");
	}
	
	
	public void setSter_inizio( Timestamp entered ) {
		this.ster_inizio = entered;
	}

    public void setSter_inizio( String tmp ) throws ParseException {
	   
    	if (tmp != ""){
    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    		Date parsedDate = dateFormat.parse(tmp);
    		this.ster_inizio = new Timestamp(parsedDate.getTime());
    	}
    }
    
    public Timestamp getSter_inizio() {
		return this.ster_inizio;
	}
    
    public Timestamp getSter_fine() {
		return this.ster_fine;
	}
    
    public void setSter_fine(String fine) throws ParseException {
		
		if (fine != ""){
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
			Date parsedDate2 = dateFormat2.parse(fine);
			this.ster_fine =new Timestamp(parsedDate2.getTime());
		}
	}

	public void setSter_fine(Timestamp fine) {
		this.ster_fine = fine;
	}
	
	public String getSter_fine2() {
		
		if(ster_fine != null){
				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
				ster_fine2 = formatter.format(ster_fine);}
		else 
				ster_fine2 = "";
        return ster_fine2;
   }
	
public String getSter_inizio2() {
		
		if(ster_inizio != null){
				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
				ster_inizio2 = formatter.format(ster_inizio);}
		else 
			ster_inizio2 = "";
        
		return ster_inizio2;
   }

public void setStato(String vivo) {
	this.vivo = vivo;
}

public String getStato() {
	return vivo;
}

public void setDetails(String tat_mic) {
	this.tat_mic = tat_mic;
}

public String getDetails() {
	return tat_mic;
}

public void setRegione(boolean regione) {
	this.regione = regione;
}

public boolean getRegione() {
	return regione;
}

public void setCattura_in1(Timestamp cattura_in1) {
	this.cattura_in1 = cattura_in1;
}
public void setCattura_in1(String cattura_in1) throws ParseException {
	   
	if (cattura_in1 != ""){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date parsedDate = dateFormat.parse(cattura_in1);
		this.cattura_in1 = new Timestamp(parsedDate.getTime());
	}
}

public Timestamp getCattura_in1() {
	return cattura_in1;
}

public void setCattura_fin2(Timestamp cattura_fin2) {
	this.cattura_fin2 = cattura_fin2;
}
public void setCattura_fin2(String cattura_fin2) throws ParseException {
	   
	if (cattura_fin2 != ""){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date parsedDate = dateFormat.parse(cattura_fin2);
		this.cattura_fin2 = new Timestamp(parsedDate.getTime());
	}
}

public ResultSet queryRecord5( Connection db )throws SQLException {
    
	ResultSet rs = null;
	try{
		 //inizializzazione della connessione
		 Connection conn = db;
		 String qry = componiQueryCattura();
		 	 
		 PreparedStatement pst = db.prepareStatement(qry);
		 
		 int i=0;
		 if(id_asl != -1){
				pst.setInt(++i, id_asl);
				pst.setInt(++i, id_asl);
				}
		 if(entered != null){
				pst.setTimestamp(++i, entered);
				pst.setTimestamp(++i, entered);
				}
		 if(fine != null){
			 pst.setTimestamp(++i, fine);
			 pst.setTimestamp(++i, fine);
			 }
		 if(comune_cattura != null){
			 pst.setString(++i, comune_cattura);
			 }
		 rs=pst.executeQuery();
		  
	 } catch ( SQLException e) {
	      throw new SQLException(e.getMessage());
	    }
	return rs;
  }
//compone la query del calcono dei cani catturati
public String componiQueryCattura(){
	
	String qry= ApplicationProperties.getProperty( "CANI_CATTURATI" );
	if(id_asl != -1) {
			qry+= ApplicationProperties.getProperty( "CATTURATI_ASL" );
	} 
	if(entered != null) {
		qry+= ApplicationProperties.getProperty( "CATTURATI_DAL" );
	}
	
	if(fine != null) {
		qry+= ApplicationProperties.getProperty( "CATTURATI_AL" );
	}
	if(comune_cattura != null) {
		qry+= ApplicationProperties.getProperty( "CATTURATI_COMUNE" );
	}
	
	return qry;
}

public Timestamp getCattura_fin2() {
	return cattura_fin2;
}

public void setCattura2(String cattura2) {
	this.cattura2 = cattura2;
}

public String getCattura2() {
	return cattura2;
}

public void setCattura1(String cattura1){
	this.cattura1 = cattura1;
}

public String getCattura1() {
	return cattura1;
}

public void setTotCaniCatt(int totCaniCatt) {
	this.totCaniCatt = totCaniCatt;
}

public int getTotCaniCatt() {
	return totCaniCatt;
}

public void setTotCaniSter(int totCaniSter) {
	this.totCaniSter = totCaniSter;
}

public int getTotCaniSter() {
	return totCaniSter;
}

public ResultSet queryRecord6( Connection db )throws SQLException {
    
	ResultSet rs = null;
	try{
		 //inizializzazione della connessione
		 Connection conn = db;
		 String qry = componiQuerySterilizzati();
		 PreparedStatement pst = db.prepareStatement(qry);
		 
		 int i=0;
		 if(id_asl != -1){
				pst.setInt(++i, id_asl);
				}
		 if(entered != null){
				pst.setTimestamp(++i, entered);
				}
		 if(fine != null){
			 pst.setTimestamp(++i, fine);
		 }
			 
		if(contributo != null){
			if (contributo.equals("4")){
			 pst.setBoolean(++i, true);
			}
			else
				if (contributo.equals("5")){
				pst.setBoolean(++i, false);
				}
		}
		if(cattura != null){
			if (cattura.equals("6")){
			 pst.setBoolean(++i, true);
			}
			else
				if (cattura.equals("7")){
				pst.setBoolean(++i, false);
				}
		}
		 rs=pst.executeQuery();
		  
	 } catch ( SQLException e) {
	      throw new SQLException(e.getMessage());
	    }
	return rs;
  }
public String componiQuerySterilizzati(){
	String qry=ApplicationProperties.getProperty("Sterilizzati");

	if(id_asl != -1) {
		qry+= ApplicationProperties.getProperty( "asl" );
	} 
	if(entered != null) {
		qry+= ApplicationProperties.getProperty( "dal" );
	}
	
	if(fine != null) {
		qry+= ApplicationProperties.getProperty( "al" );
	}
	if(contributo!=null){
		qry+= ApplicationProperties.getProperty( "contributo" );
	}
	if(cattura!=null){
		qry+= ApplicationProperties.getProperty( "cattura" );
	}
	qry+= ApplicationProperties.getProperty( "raggruppa" );
	return qry;
}

public String componiQueryCessioni(){
	String qry= ApplicationProperties.getProperty( "CESSIONE" );
	if((id_asl != -1)&&(id_asl!=14)) {
			qry+= ApplicationProperties.getProperty( "CESSIONE_ASL" );
	} 
	if(entered != null) {
		qry+= ApplicationProperties.getProperty( "CESSIONE_DAL" );
	}
	
	if(fine != null) {
		qry+= ApplicationProperties.getProperty( "CESSIONE_AL" );
	}
	
	return qry;
	
}

public ResultSet queryRecord7( Connection db )throws SQLException {
    
	ResultSet rs = null;
	try{
		 //inizializzazione della connessione
		 Connection conn = db;
		 String qry = componiQueryCessioni();
		 	 
		 PreparedStatement pst = db.prepareStatement(qry);
		 
		 int i=0;
		 if((id_asl != -1)&&(id_asl!=14)){
				pst.setInt(++i, id_asl);
				}
		 if(entered != null){
				pst.setTimestamp(++i, entered);
				}
		 if(fine != null){
			 pst.setTimestamp(++i, fine);
			 }
		 rs=pst.executeQuery();
		  
	 } catch ( SQLException e) {
	      throw new SQLException(e.getMessage());
	    }
	return rs;
  }

public int getTotCessioni() {
	// TODO Auto-generated method stub
	return totCessioni;
}

public void setTotCessioni(int totCessioni) {
	this.totCessioni = totCessioni;
}
public void setTotSmar(int smar) {
	this.totSmarriti = smar;
}

public String getTotSmar() {
	// TODO Auto-generated method stub
	return null;
}

public void setCattura(String cattura) {
	// TODO Auto-generated method stub
	this.cattura=cattura;
}

public void setStatoC(String stato) {
	// TODO Auto-generated method stub
	this.statoC=stato;
	if (stato.equals("1"))
		this.statoC="Reimmissione";
	else if (stato.equals("2"))
		this.statoC="xxx";
	else
		this.statoC="Adozione";
}

public void setContributo(String contributo) {
	// TODO Auto-generated method stub
	this.contributo=contributo;
}

public String getCattura() {
	// TODO Auto-generated method stub
	return cattura;
}

public String getStatoC() {
	// TODO Auto-generated method stub
	return statoC;
}

public String getContributo() {
	// TODO Auto-generated method stub
	return contributo;
}


public ResultSet queryRecord8( Connection db )throws SQLException {
    
	ResultSet rs = null;
	try{
		 //inizializzazione della connessione
		 Connection conn = db;
		 String qry = componiQueryRestituiti();
		 	 
		 PreparedStatement pst = db.prepareStatement(qry);
		 
		 int i=0;
		 if(id_asl != -1){
				pst.setInt(++i, id_asl);
				}
		 if(entered != null){
				pst.setTimestamp(++i, entered);
				}
		 if(fine != null){
			 pst.setTimestamp(++i, fine);
			 }
		 if(comune_proprietario != null){
			 pst.setString(++i, comune_proprietario);
			 }
			 	 
		 rs=pst.executeQuery();
		  
	 } catch ( SQLException e) {
	      throw new SQLException(e.getMessage());
	    }
	return rs;
  }

public String componiQueryRestituiti(){
	String qry= ApplicationProperties.getProperty( "RESTITUITI" );
	if(id_asl != -1) {
			qry+= ApplicationProperties.getProperty( "RESTITUITI_ASL" );
	} 
	if(entered != null) {
		qry+= ApplicationProperties.getProperty( "RESTITUITI_DAL" );
	}
	
	if(fine != null) {
		qry+= ApplicationProperties.getProperty( "RESTITUITI_AL" );
	}
	if(comune_proprietario != null){
		qry+= ApplicationProperties.getProperty( "RESTITUITI_COMUNE" );
		 }
		 	 
	return qry;
	
}

public void setTotCaniSmar(int int1) {
	// TODO Auto-generated method stub
	this.totSmarriti=int1;
	
}

public void setTotCaniSterCatt(int int1) {
	// TODO Auto-generated method stub
	this.totCaniSterCatt=int1;
}

public void setTotCaniSterReimmessi(int int1) {
	// TODO Auto-generated method stub
	this.totCaniSterReimmessi=int1;
	
}

public void setTotCaniSterAdottati(int int1) {
	// TODO Auto-generated method stub
	this.totcaniSterAdottati=int1;
}

public int getTotCaniReimmessi() {
	// TODO Auto-generated method stub
	return this.totCaniSterReimmessi;
}

public int getTotCaniAdottati() {
	// TODO Auto-generated method stub
	return this.totcaniSterAdottati;
}

public void setComuneCattura(String tmp) {
		this.comune_cattura=tmp;
}
public String getComuneCattura() {
	// TODO Auto-generated method stub
	return this.comune_cattura;
}
public void setComuneProprietario(String tmp) {
	this.comune_proprietario=tmp;
}
public String getComuneProprietario() {
// TODO Auto-generated method stub
return this.comune_proprietario;
}


public void setComuneId( int id_comune ) {
	this.id_comune = id_comune;
}

public int getComuneId() {
	return id_comune;
}


}