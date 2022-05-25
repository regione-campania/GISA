/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.focolai.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import org.aspcfs.modules.base.Constants;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class Focolaio extends GenericBean
{
 private int focolaioId = -1;
 private int orgId = -1;
 private String localita = "";
 private Timestamp dataSospetto = null ;
 private Timestamp dataProva = null;
 private Timestamp dataApertura = null;
 private String malattia = "";
 private String specieAnimale = "";
 private String origineMalattia = "";
 private boolean provenienzaRegionale = false;
 private Timestamp dataProvenienzaRegionale = null;
 private boolean provenienzaExtraRegionale = false;
 private Timestamp dataProvenienzaExtraregionale = null;
 private boolean pascoloInfetto = false;
 private boolean montaEsterna = false;
 private boolean reinfezione = false;
 private boolean iatrogena = false;
 private boolean caniInfetti = false;
 private Timestamp dataProvvedimenti;
 private String numeroProvvedimenti = "";
 private String proposteAdozione = "";
 private Timestamp dataImmunizzanti;
 private String osservazioni = "";
 private ArrayList specie = new ArrayList();
 private ArrayList complessivo = new ArrayList();
 private ArrayList natiStalla = new ArrayList();
 private ArrayList introdotti = new ArrayList();
 private ArrayList morti = new ArrayList();
 private ArrayList ammalati = new ArrayList();
 private ArrayList denuncia = new ArrayList();
 private ArrayList abbattuti = new ArrayList();
 private ArrayList guariti = new ArrayList();
 private ArrayList totaleMalati = new ArrayList();
 private ArrayList smarriti = new ArrayList();
 private ArrayList sani = new ArrayList();
 private ArrayList estinti = new ArrayList();
 
 private boolean apertura = true;
 
 private Timestamp dataUltimoCaso = null ;
 private Timestamp dataRevocaSindaco = null ;
 private String proposteRevoca = "";
 private int idFocolaioChiuso = -1;
 
 private boolean trashed = false;  //trashed indica la validita', se e' true significa che e' valido
 
 
 
 
 


 public void setTrashed(String temp) {
		this.trashed = DatabaseUtils.parseBoolean(temp);
	}
 
 public boolean getTrashed() {
	return trashed;
}

public void setTrashed(boolean trashed) {
	this.trashed = trashed;
}

public ArrayList getAbbattuti() {
	return abbattuti;
}

public void setAbbattuti(ArrayList abbattuti) {
	this.abbattuti = abbattuti;
}

public ArrayList getGuariti() {
	return guariti;
}

public void setGuariti(ArrayList guariti) {
	this.guariti = guariti;
}

public ArrayList getTotaleMalati() {
	return totaleMalati;
}

public void setTotaleMalati(ArrayList totaleMalati) {
	this.totaleMalati = totaleMalati;
}

public ArrayList getSmarriti() {
	return smarriti;
}

public void setSmarriti(ArrayList smarriti) {
	this.smarriti = smarriti;
}

public ArrayList getSani() {
	return sani;
}

public void setSani(ArrayList sani) {
	this.sani = sani;
}

public ArrayList getEstinti() {
	return estinti;
}

public void setEstinti(ArrayList estinti) {
	this.estinti = estinti;
}

public void setIdFocolaioChiuso(String idFocolaioChiuso) {
		this.idFocolaioChiuso = Integer.parseInt(idFocolaioChiuso);
	}
 
 public int getIdFocolaioChiuso() {
	return idFocolaioChiuso;
}

public void setIdFocolaioChiuso(int idFocolaioChiuso) {
	this.idFocolaioChiuso = idFocolaioChiuso;
}

public Timestamp getDataUltimoCaso() {
	return dataUltimoCaso;
}

public void setDataUltimoCaso(Timestamp dataUltimoCaso) {
	this.dataUltimoCaso = dataUltimoCaso;
}

public void setDataUltimoCaso(String dataUltimoCaso) {
	this.dataUltimoCaso = DatabaseUtils.parseDateToTimestamp(dataUltimoCaso, new Locale("IT"));
}

public Timestamp getDataRevocaSindaco() {
	return dataRevocaSindaco;
}

public void setDataRevocaSindaco(Timestamp dataRevocaSindaco) {
	this.dataRevocaSindaco = dataRevocaSindaco;
}

public void setDataRevocaSindaco(String dataRevocaSindaco) {
	this.dataRevocaSindaco = DatabaseUtils.parseDateToTimestamp(dataRevocaSindaco, new Locale("IT"));
}

public String getProposteRevoca() {
	return proposteRevoca;
}

public void setProposteRevoca(String proposteRevoca) {
	this.proposteRevoca = proposteRevoca;
}

public String getMalattia() {
	return malattia;
}

public void setMalattia(String malattia) {
	this.malattia = malattia;
}

public String getSpecieAnimale() {
	return specieAnimale;
}

public void setSpecieAnimale(String specieAnimale) {
	this.specieAnimale = specieAnimale;
}

public void setApertura(String temp) {
		this.apertura = DatabaseUtils.parseBoolean(temp);
	}
 
 public boolean getApertura() {
	return apertura;
}

public void setApertura(boolean apertura) {
	this.apertura = apertura;
}

public ArrayList getDenuncia() {
	return denuncia;
}

public void setDenuncia(ArrayList denuncia) {
	this.denuncia = denuncia;
}

public void setFocolaioId(String focolaioID) {
     this.focolaioId = Integer.parseInt(focolaioID);
   }
 
public int getFocolaioId() {
	return focolaioId;
}
public void setFocolaioId(int focolaioId) {
	this.focolaioId = focolaioId;
}
public Timestamp getDataProvenienzaExtraregionale() {
	return dataProvenienzaExtraregionale;
}
public void setDataProvenienzaExtraregionale(
		Timestamp dataProvenienzaExtraregionale) {
	this.dataProvenienzaExtraregionale = dataProvenienzaExtraregionale;
}
public ArrayList getSpecie() {
	return specie;
}
public void setSpecie(ArrayList specie) {
	this.specie = specie;
}
public ArrayList getComplessivo() {
	return complessivo;
}
public void setComplessivo(ArrayList complessivo) {
	this.complessivo = complessivo;
}
public ArrayList getNatiStalla() {
	return natiStalla;
}
public void setNatiStalla(ArrayList natiStalla) {
	this.natiStalla = natiStalla;
}
public ArrayList getIntrodotti() {
	return introdotti;
}
public void setIntrodotti(ArrayList introdotti) {
	this.introdotti = introdotti;
}
public ArrayList getMorti() {
	return morti;
}
public void setMorti(ArrayList morti) {
	this.morti = morti;
}
public ArrayList getAmmalati() {
	return ammalati;
}
public void setAmmalati(ArrayList ammalati) {
	this.ammalati = ammalati;
}
public int getOrgId() {
	return orgId;
}
public void setOrgId(int orgId) {
	this.orgId = orgId;
}
public void setOrgId(String orgID) {
      this.orgId = Integer.parseInt(orgID);
    }
public String getLocalita() {
	return localita;
}
public void setLocalita(String localita) {
	this.localita = localita;
}
public Timestamp getDataSospetto() {
	return dataSospetto;
}
public void setDataSospetto(Timestamp dataSospetto) {
	this.dataSospetto = dataSospetto;
}

public void setDataSospetto(String dataSospetto) {
	this.dataSospetto = DatabaseUtils.parseDateToTimestamp(dataSospetto, new Locale("IT"));
}

public Timestamp getDataProva() {
	return dataProva;
}
public void setDataProva(Timestamp dataProva) {
	this.dataProva = dataProva;
}
public void setDataProva(String dataProva) {
	this.dataProva = DatabaseUtils.parseDateToTimestamp(dataProva, new Locale("IT"));
}

public Timestamp getDataApertura() {
	return dataApertura;
}
public void setDataApertura(Timestamp dataApertura) {
	this.dataApertura = dataApertura;
}

public void setDataApertura(String dataApertura) {
	this.dataApertura = DatabaseUtils.parseDateToTimestamp(dataApertura, new Locale("IT"));
}
public String getOrigineMalattia() {
	return origineMalattia;
}
public void setOrigineMalattia(String origineMalattia) {
	this.origineMalattia = origineMalattia;
}
public boolean getProvenienzaRegionale() {
	return provenienzaRegionale;
}
public void setProvenienzaRegionale(boolean provenienzaRegionale) {
	this.provenienzaRegionale = provenienzaRegionale;
}
public void setProvenienzaRegionale(String temp) {
	this.provenienzaRegionale = DatabaseUtils.parseBoolean(temp);
}

public Timestamp getDataProvenienzaRegionale() {
	return dataProvenienzaRegionale;
}
public void setDataProvenienzaRegionale(Timestamp dataProvenienzaRegionale) {
	this.dataProvenienzaRegionale = dataProvenienzaRegionale;
}
public void setDataProvenienzaRegionale(String dataProvenienzaRegionale) {
	this.dataProvenienzaRegionale = DatabaseUtils.parseDateToTimestamp(dataProvenienzaRegionale, new Locale("IT"));
}

public boolean getProvenienzaExtraRegionale() {
	return provenienzaExtraRegionale;
}
public void setProvenienzaExtraRegionale(boolean provenienzaExtraRegionale) {
	this.provenienzaExtraRegionale = provenienzaExtraRegionale;
}
public void setProvenienzaExtraRegionale(String temp) {
	this.provenienzaExtraRegionale = DatabaseUtils.parseBoolean(temp);
}

public Timestamp getDataProvenienzaExtraRegionale() {
	return dataProvenienzaExtraregionale;
}
public void setDataProvenienzaExtraRegionale(
		Timestamp dataProvenienzaExtraregionale) {
	this.dataProvenienzaExtraregionale = dataProvenienzaExtraregionale;
}

public void setDataProvenienzaExtraRegionale(String dataProvenienzaExtraRegionale) {
	this.dataProvenienzaExtraregionale = DatabaseUtils.parseDateToTimestamp(dataProvenienzaExtraRegionale, new Locale("IT"));
}


public boolean getPascoloInfetto() {
	return pascoloInfetto;
}
public void setPascoloInfetto(boolean pascoloInfetto) {
	this.pascoloInfetto = pascoloInfetto;
}
public void setPascoloInfetto(String temp) {
	this.pascoloInfetto = DatabaseUtils.parseBoolean(temp);
}
public boolean getMontaEsterna() {
	return montaEsterna;
}
public void setMontaEsterna(boolean montaEsterna) {
	this.montaEsterna = montaEsterna;
}
public void setMontaEsterna(String temp) {
	this.montaEsterna = DatabaseUtils.parseBoolean(temp);
}
public boolean getReinfezione() {
	return reinfezione;
}
public void setReinfezione(boolean reinfezione) {
	this.reinfezione = reinfezione;
}
public void setReinfezione(String temp) {
	this.reinfezione = DatabaseUtils.parseBoolean(temp);
}
public boolean getIatrogena() {
	return iatrogena;
}
public void setIatrogena(boolean iatrogena) {
	this.iatrogena = iatrogena;
}
public void setIatrogena(String temp) {
	this.iatrogena = DatabaseUtils.parseBoolean(temp);
}
public boolean getCaniInfetti() {
	return caniInfetti;
}
public void setCaniInfetti(boolean caniInfetti) {
	this.caniInfetti = caniInfetti;
}
public void setCaniInfetti(String temp) {
	this.caniInfetti = DatabaseUtils.parseBoolean(temp);
}
public Timestamp getDataProvvedimenti() {
	return dataProvvedimenti;
}
public void setDataProvvedimenti(Timestamp dataProvvedimenti) {
	this.dataProvvedimenti = dataProvvedimenti;
}
public void setDataProvvedimenti(String dataProvvedimenti) {
	this.dataProvvedimenti = DatabaseUtils.parseDateToTimestamp(dataProvvedimenti, new Locale("IT"));
}
public String getNumeroProvvedimenti() {
	return numeroProvvedimenti;
}
public void setNumeroProvvedimenti(String numeroProvvedimenti) {
	this.numeroProvvedimenti = numeroProvvedimenti;
}
public String getProposteAdozione() {
	return proposteAdozione;
}
public void setProposteAdozione(String proposteAdozione) {
	this.proposteAdozione = proposteAdozione;
}
public Timestamp getDataImmunizzanti() {
	return dataImmunizzanti;
}
public void setDataImmunizzanti(Timestamp dataImmunizzanti) {
	this.dataImmunizzanti = dataImmunizzanti;
}
public void setDataImmunizzanti(String dataImmunizzanti) {
	this.dataImmunizzanti = DatabaseUtils.parseDateToTimestamp(dataImmunizzanti, new Locale("IT"));
}
public String getOsservazioni() {
	return osservazioni;
}
public void setOsservazioni(String osservazioni) {
	this.osservazioni = osservazioni;
}
 


 //costruttore per modulo A
public Focolaio(Connection db, int focolaio_id) throws SQLException {
    if (focolaio_id == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
            "SELECT * from focolai where focolaio_id = ? ");
    pst.setInt(1, focolaio_id);
    ResultSet rs = pst.executeQuery();
    if(rs.next())
    {
    	buildRecord(rs);
    }
    rs.close();
    
    //creazione della tabella di denuncia
    pst = db.prepareStatement(
    "SELECT * from focolaidenunciaanimali where focolaio_id = ? order by denuncia_id");
    pst.setInt(1, focolaio_id);
    rs = pst.executeQuery();
    ArrayList specie =  new ArrayList();
    ArrayList complessivo =  new ArrayList();
    ArrayList natiStalla =  new ArrayList();
    ArrayList introdotti =  new ArrayList();
    ArrayList ammalati =  new ArrayList();
    ArrayList morti =  new ArrayList();
    ArrayList denuncia = new ArrayList();
    
    

    while (rs.next())
    {
      specie.add(rs.getString("specie"));
      complessivo.add(rs.getInt("complessivo"));
      natiStalla.add(rs.getInt("nati_stalla"));
      introdotti.add(rs.getInt("introdotti"));
      ammalati.add(rs.getInt("ammalati"));
      morti.add(rs.getInt("morti"));   
      denuncia.add(rs.getInt("denuncia_id"));
    }
    this.specie = specie;
    this.complessivo = complessivo;
    this.natiStalla = natiStalla;
    this.introdotti = introdotti;
    this.ammalati = ammalati;
    this.morti = morti;
    this.denuncia = denuncia;
    
  
    this.setTrashed(true);  //damiano
    
    pst.close();
    if (focolaio_id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }        
} 
    
    //costruttore modulo B
public Focolaio(Connection db, int focolaio_id, int discriminante) throws SQLException {
    if (focolaio_id == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
            "SELECT * from focolai where focolaio_id = ? ");
    pst.setInt(1, focolaio_id);
    ResultSet rs = pst.executeQuery();
    if(rs.next())
    {
    	buildRecordModuloB(rs);
    }
    rs.close();
    
    //creazione della tabella di denuncia
    pst = db.prepareStatement(
    "SELECT * from focolaiodecorso where focolaio_id = ? order by denuncia_id");
    pst.setInt(1, focolaio_id);
    rs = pst.executeQuery();
    ArrayList specie =  new ArrayList();
    ArrayList morti =  new ArrayList();
    ArrayList abbattuti =  new ArrayList();
    ArrayList guariti =  new ArrayList();
    ArrayList totaleMalati =  new ArrayList();
    ArrayList smarriti =  new ArrayList();
    ArrayList sani = new ArrayList();
    ArrayList estinti = new ArrayList();
    ArrayList denuncia = new ArrayList();

    while (rs.next())
    {
      specie.add(rs.getString("specie"));
      morti.add(rs.getInt("morti"));
      abbattuti.add(rs.getInt("abbattuti"));
      guariti.add(rs.getInt("guariti"));
      totaleMalati.add(rs.getInt("totale_malati"));
      smarriti.add(rs.getInt("smarriti"));
      sani.add(rs.getInt("sani"));
      estinti.add(rs.getInt("esistenti"));
      
      denuncia.add(rs.getInt("denuncia_id"));
    }
    this.specie = specie;
    this.morti = morti;
    this.abbattuti = abbattuti;
    this.guariti = guariti;
    this.totaleMalati = totaleMalati;
    this.smarriti = smarriti;
    this.sani = sani;
    this.estinti = estinti;
  
    this.denuncia = denuncia;
    
    
    pst.close();
    if (focolaio_id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }        
} 




public Focolaio()
{
	
}



protected void buildRecord(ResultSet rs) throws SQLException {
    //organization table
	this.setFocolaioId(rs.getInt("focolaio_id"));
    this.setOrgId(rs.getInt("org_id"));
    this.setLocalita(rs.getString("localita"));
    this.setDataSospetto(rs.getTimestamp("data_sospetto"));
    this.setDataProva(rs.getTimestamp("data_prova"));
    this.setDataApertura(rs.getTimestamp("data_apertura"));
    this.setOrigineMalattia(rs.getString("origine_malattia"));
    this.setProvenienzaRegionale(rs.getBoolean("provenienza_regionale"));
    this.setDataProvenienzaRegionale(rs.getTimestamp("data_provenienza_regionale"));
    this.setProvenienzaExtraRegionale(rs.getBoolean("provenienza_extraregionale"));
    this.setDataProvenienzaExtraRegionale(rs.getTimestamp("data_provenienza_extraregionale"));
    this.setPascoloInfetto(rs.getBoolean("pascolo_infetto"));
    this.setMontaEsterna(rs.getBoolean("monta_esterna"));
    this.setReinfezione(rs.getBoolean("reinfezione"));
    this.setIatrogena(rs.getBoolean("iatrogena"));
    this.setCaniInfetti(rs.getBoolean("cani_infetti"));
    this.setDataProvvedimenti(rs.getTimestamp("data_provvedimenti"));
    this.setNumeroProvvedimenti(rs.getString("numero_provvedimenti"));
    this.setProposteAdozione(rs.getString("proposte_adozione"));
    this.setDataImmunizzanti(rs.getTimestamp("data_immunizzanti"));
    this.setOsservazioni(rs.getString("osservazioni"));
    this.setMalattia(rs.getString("malattia"));
    this.setSpecieAnimale(rs.getString("specie_animale"));
    this.setIdFocolaioChiuso(rs.getInt("id_focolaio_chiuso"));
    
  }


protected void buildRecordModuloB(ResultSet rs) throws SQLException {
    //organization table
	this.setFocolaioId(rs.getInt("focolaio_id"));
    this.setOrgId(rs.getInt("org_id"));
    this.setLocalita(rs.getString("localita"));
    this.setDataSospetto(rs.getTimestamp("data_sospetto"));
    this.setDataProva(rs.getTimestamp("data_prova"));
    this.setDataApertura(rs.getTimestamp("data_apertura"));
    this.setOrigineMalattia(rs.getString("origine_malattia"));
    this.setProvenienzaRegionale(rs.getBoolean("provenienza_regionale"));
    this.setDataProvenienzaRegionale(rs.getTimestamp("data_provenienza_regionale"));
    this.setProvenienzaExtraRegionale(rs.getBoolean("provenienza_extraregionale"));
    this.setDataProvenienzaExtraRegionale(rs.getTimestamp("data_provenienza_extraregionale"));
    this.setPascoloInfetto(rs.getBoolean("pascolo_infetto"));
    this.setMontaEsterna(rs.getBoolean("monta_esterna"));
    this.setReinfezione(rs.getBoolean("reinfezione"));
    this.setIatrogena(rs.getBoolean("iatrogena"));
    this.setCaniInfetti(rs.getBoolean("cani_infetti"));
    this.setDataProvvedimenti(rs.getTimestamp("data_provvedimenti"));
    this.setNumeroProvvedimenti(rs.getString("numero_provvedimenti"));
    this.setProposteAdozione(rs.getString("proposte_adozione"));
    this.setDataImmunizzanti(rs.getTimestamp("data_immunizzanti"));
    this.setOsservazioni(rs.getString("osservazioni"));
    this.setMalattia(rs.getString("malattia"));
    this.setSpecieAnimale(rs.getString("specie_animale"));
    this.setDataUltimoCaso(rs.getTimestamp("dataUltimoCaso"));
	this.setDataRevocaSindaco(rs.getTimestamp("dataRevocaSindaco")); 
	this.setProposteRevoca(rs.getString("proposteRevoca"));
	this.setApertura(false);
	this.setIdFocolaioChiuso(rs.getInt("id_focolaio_chiuso"));

  }


//inserimento modulo A
public boolean insert(Connection db) throws SQLException {
	    StringBuffer sql = new StringBuffer();
	    boolean commit = db.getAutoCommit();
	    try {
	      if (commit) {
	        db.setAutoCommit(false);
	      }
	      
	      sql.append(
	    		  "INSERT INTO FOCOLAI (org_id,localita,data_sospetto," +
	    		  "data_prova, data_apertura, origine_malattia, " +
	    		  "provenienza_regionale, data_provenienza_regionale, " +
	    		  "provenienza_extraregionale, data_provenienza_extraregionale, " +
	    		  "pascolo_infetto, monta_esterna, reinfezione, iatrogena, " +
	    		  "cani_infetti, data_provvedimenti, numero_provvedimenti, " +
	    		  " proposte_adozione, data_immunizzanti, osservazioni, apertura, malattia, specie_animale)");
	     
	      sql.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	      
	      int i = 0;
	      PreparedStatement pst = db.prepareStatement(sql.toString());
	      pst.setInt(++i, orgId);
	      pst.setString(++i, localita);
	      pst.setTimestamp(++i, dataSospetto);
	      pst.setTimestamp(++i, dataProva);
	      pst.setTimestamp(++i, dataApertura);
	      pst.setString(++i, origineMalattia);
	      pst.setBoolean(++i, provenienzaRegionale);
	      pst.setTimestamp(++i, dataProvenienzaRegionale);
	      pst.setBoolean(++i, provenienzaExtraRegionale);
	      pst.setTimestamp(++i, dataProvenienzaExtraregionale);
	      pst.setBoolean(++i, pascoloInfetto);
	      pst.setBoolean(++i, montaEsterna);
	      pst.setBoolean(++i, reinfezione);
	      pst.setBoolean(++i, iatrogena);
	      pst.setBoolean(++i, caniInfetti);
	      pst.setTimestamp(++i, dataProvvedimenti);
	      pst.setString(++i, numeroProvvedimenti);
	      pst.setString(++i, proposteAdozione);
	      pst.setTimestamp(++i, dataImmunizzanti);
	      pst.setString(++i, osservazioni);
	      pst.setBoolean(++i, true); //apertura
	      pst.setString(++i, malattia);
	      pst.setString(++i, specieAnimale);
	      
	      pst.execute();
	    		  
	      int id = 0;
	     // id = DatabaseUtils.getCurrVal(db, "focolai_focolaioid_seq", id);
	      
	    
	     
	    } catch (SQLException e) {
	       e.printStackTrace();
	    } finally {
	      if (commit) {
	        db.setAutoCommit(true);
	      }
	    }
	    return true;
	  }

 


//update
public boolean update(Connection db,int focolaio_id) throws SQLException {
	    StringBuffer sql = new StringBuffer();
	    boolean commit = db.getAutoCommit();
	    try {
	      if (commit) {
	        db.setAutoCommit(false);
	      }
	      
	      
	      
	      sql.append(
	    		  "update FOCOLAI set org_id = ?,");
	    //  if( !this.getLocalita().equals("") )
	    	      sql.append("localita = ? ");
	    //  if(this.dataSospetto != null)
	    	   sql.append(", data_sospetto = ? ");
	    //  if(this.dataProva != null)
	    	  sql.append(", data_prova = ? ");
	    //  if(this.dataApertura != null)
	    	  sql.append(", data_apertura = ? ");
	   //   if( !this.getOrigineMalattia().equals("") )
    	      sql.append(", origine_malattia = ? ");
    	      sql.append(", provenienza_regionale = ? ");
    //	  if(this.dataProvenienzaRegionale != null)
    	    	  sql.append(", data_provenienza_regionale = ? ");
    	  sql.append(", provenienza_extraregionale = ? ");
   // 	  if(this.dataProvenienzaExtraregionale != null)
	    	  sql.append(", data_provenienza_extraregionale = ? ");
    	  sql.append(", pascolo_infetto = ? ");
    	  sql.append(", monta_esterna = ? "); 
    	  sql.append(", reinfezione = ? "); 
    	  sql.append(", iatrogena = ? "); 
    	  sql.append(", cani_infetti = ? ");
   // 	  if(this.dataProvvedimenti != null)
	    	  sql.append(", data_provvedimenti = ? ");
   // 	  if( !this.getNumeroProvvedimenti().equals("") )
    	      sql.append(", numero_provvedimenti = ? ");
   // 	  if( !this.getProposteAdozione().equals("") )
    	      sql.append(", proposte_adozione = ? ");	
    //	  if(this.dataImmunizzanti != null)
	    	  sql.append(", data_immunizzanti = ? ");
    //	  if( !this.getOsservazioni().equals("") )
    	      sql.append(", osservazioni = ? ");
   // 	  if( !this.getMalattia().equals("") )
    	      sql.append(", malattia = ? ");
    /*	  if( !this.getSpecieAnimale().equals("") )
    	      sql.append(",specie_animale = ? ");
	  */
	      sql.append(" where focolaio_id = ?");
	      
	      
	      int i = 0;
	      PreparedStatement pst = db.prepareStatement(sql.toString());
	      pst.setInt(++i, orgId);
	 //     if( !this.getLocalita().equals("") )
	         pst.setString(++i, localita);
	      
	  //    if(this.dataSospetto != null)	  	    
	         pst.setTimestamp(++i, dataSospetto);
	      
	 //     if(this.dataProva != null)
	        pst.setTimestamp(++i, dataProva);
	      
	 //     if(this.dataApertura != null)
	        pst.setTimestamp(++i, dataApertura);
	      
	 //     if( !this.getOrigineMalattia().equals("") )   	
	         pst.setString(++i, origineMalattia);
	      
	      pst.setBoolean(++i, provenienzaRegionale);
	 	  
	  //    if(this.dataProvenienzaRegionale != null)	 		  
	         pst.setTimestamp(++i, dataProvenienzaRegionale);
	      
	      pst.setBoolean(++i, provenienzaExtraRegionale);
	      
	//	  if(this.dataProvenienzaExtraregionale != null)				
	        pst.setTimestamp(++i, dataProvenienzaExtraregionale);
		  
	      pst.setBoolean(++i, pascoloInfetto);
	      
	      pst.setBoolean(++i, montaEsterna);
	      
	      pst.setBoolean(++i, reinfezione);
	      
	      pst.setBoolean(++i, iatrogena);
	      
	      pst.setBoolean(++i, caniInfetti);
	     
	 //     if(this.dataProvvedimenti != null)	  	    
	        pst.setTimestamp(++i, dataProvvedimenti);
	      
	  //    if( !this.getNumeroProvvedimenti().equals("") )    	 
	        pst.setString(++i, numeroProvvedimenti);
	      
	   //   if( !this.getProposteAdozione().equals("") )     	 
	         pst.setString(++i, proposteAdozione);
	      
	  //    if(this.dataImmunizzanti != null)  	    
	        pst.setTimestamp(++i, dataImmunizzanti);
	      
	  //    if( !this.getOsservazioni().equals("") )      	
	        pst.setString(++i, osservazioni);
	      
	  //    if( !this.getMalattia().equals("") )      	
		        pst.setString(++i, malattia);
	      
	    /*  if( !this.getSpecieAnimale().equals("") )      	
		        pst.setString(++i, specieAnimale);
	      */
	      pst.setInt(++i, focolaio_id);
	      
	      pst.execute();
	    		  
	      int id = 0;
	     // id = DatabaseUtils.getCurrVal(db, "focolai_focolaioid_seq", id);
	      
	    
	     
	    } catch (SQLException e) {
	       e.printStackTrace();
	    } finally {
	      if (commit) {
	        db.setAutoCommit(true);
	      }
	    }
	    return true;
	  }


//inserimento modulo B
public boolean insertModuloB(Connection db,int focolaioId) throws SQLException {
	    StringBuffer sql = new StringBuffer();
	    StringBuffer sql2 = new StringBuffer();
	    boolean commit = db.getAutoCommit();
	    try {
	      if (commit) {
	        db.setAutoCommit(false);
	      }
	      
	     
	      //serve a disabilitare il modulo precedente...se prima era aperto, ora e' chiuso, setto la riga di aperto a false
	      
	      sql2.append("update focolai set trashed="+false +" where focolaio_id ="+focolaioId);
	      PreparedStatement pst = db.prepareStatement(sql2.toString());
	      pst.execute();
	      
	      
	      sql.append(
	    		  "INSERT INTO FOCOLAI (org_id,localita,data_sospetto," +
	    		  "data_prova, data_apertura, origine_malattia, " +
	    		  "provenienza_regionale, data_provenienza_regionale, " +
	    		  "provenienza_extraregionale, data_provenienza_extraregionale, " +
	    		  "pascolo_infetto, monta_esterna, reinfezione, iatrogena, " +
	    		  "cani_infetti, data_provvedimenti, numero_provvedimenti, " +
	    		  " proposte_adozione, data_immunizzanti, osservazioni, apertura, malattia, specie_animale, dataUltimoCaso, dataRevocaSindaco, proposteRevoca, id_focolaio_chiuso)");
	     
	      sql.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	      
	      int i = 0;
	      pst = db.prepareStatement(sql.toString());
	      pst.setInt(++i, orgId);
	      pst.setString(++i, localita);
	      pst.setTimestamp(++i, dataSospetto);
	      pst.setTimestamp(++i, dataProva);
	      pst.setTimestamp(++i, dataApertura);
	      pst.setString(++i, origineMalattia);
	      pst.setBoolean(++i, provenienzaRegionale);
	      pst.setTimestamp(++i, dataProvenienzaRegionale);
	      pst.setBoolean(++i, provenienzaExtraRegionale);
	      pst.setTimestamp(++i, dataProvenienzaExtraregionale);
	      pst.setBoolean(++i, pascoloInfetto);
	      pst.setBoolean(++i, montaEsterna);
	      pst.setBoolean(++i, reinfezione);
	      pst.setBoolean(++i, iatrogena);
	      pst.setBoolean(++i, caniInfetti);
	      pst.setTimestamp(++i, dataProvvedimenti);
	      pst.setString(++i, numeroProvvedimenti);
	      pst.setString(++i, proposteAdozione);
	      pst.setTimestamp(++i, dataImmunizzanti);
	      pst.setString(++i, osservazioni);
	      pst.setBoolean(++i, false); //chiusura
	      pst.setString(++i, malattia);
	      pst.setString(++i, specieAnimale);
	      pst.setTimestamp(++i, dataUltimoCaso);
	      pst.setTimestamp(++i, dataRevocaSindaco);
	      pst.setString(++i, proposteRevoca);
	      pst.setInt(++i, focolaioId);
	      //pst.setBoolean(++i, true);  //setto la chiusura
	      
	      pst.execute();
	    		  
	      int id = 0;
	     // id = DatabaseUtils.getCurrVal(db, "focolai_focolaioid_seq", id);
	      
	    
	     
	    } catch (SQLException e) {
	       e.printStackTrace();
	    } finally {
	      if (commit) {
	        db.setAutoCommit(true);
	      }
	    }
	    return true;
	  }


//update modulo B
public boolean updateModuloB(Connection db,int focolaio_id) throws SQLException {
	    StringBuffer sql = new StringBuffer();
	    boolean commit = db.getAutoCommit();
	    try {
	      if (commit) {
	        db.setAutoCommit(false);
	      }
	      
	      
	      
	      sql.append(
	    		  "update FOCOLAI set org_id = ?,");
	   
	    	      sql.append("localita = ? ");
	  
	    	   sql.append(", data_sospetto = ? ");
	  
	    	  sql.append(", data_prova = ? ");
	  
	    	  sql.append(", data_apertura = ? ");
	
    	      sql.append(", origine_malattia = ? ");
    	      sql.append(", provenienza_regionale = ? ");

    	    	  sql.append(", data_provenienza_regionale = ? ");
    	  sql.append(", provenienza_extraregionale = ? ");

	    	  sql.append(", data_provenienza_extraregionale = ? ");
    	  sql.append(", pascolo_infetto = ? ");
    	  sql.append(", monta_esterna = ? "); 
    	  sql.append(", reinfezione = ? "); 
    	  sql.append(", iatrogena = ? "); 
    	  sql.append(", cani_infetti = ? ");
	    	  sql.append(", data_provvedimenti = ? ");
  
    	      sql.append(", numero_provvedimenti = ? ");
 
    	      sql.append(", proposte_adozione = ? ");	
  
	    	  sql.append(", data_immunizzanti = ? ");
  
    	      sql.append(", osservazioni = ? ");
 
    	      sql.append(", malattia = ? ");
    	      
    	      sql.append(", dataUltimoCaso = ? ");
    	      
    	      sql.append(", dataRevocaSindaco = ? ");
    	      
    	      sql.append(", proposteRevoca = ? ");
    	      
    	      
    	      
	      sql.append(" where focolaio_id = ?");
	      
	      
	      int i = 0;
	      PreparedStatement pst = db.prepareStatement(sql.toString());
	      pst.setInt(++i, orgId);
	         pst.setString(++i, localita);
	      
	         pst.setTimestamp(++i, dataSospetto);
	      
	        pst.setTimestamp(++i, dataProva);
	      
	        pst.setTimestamp(++i, dataApertura);
	      
	         pst.setString(++i, origineMalattia);
	      
	      pst.setBoolean(++i, provenienzaRegionale);
	 	  
	          pst.setTimestamp(++i, dataProvenienzaRegionale);
	      
	      pst.setBoolean(++i, provenienzaExtraRegionale);
	      
	        pst.setTimestamp(++i, dataProvenienzaExtraregionale);
		  
	      pst.setBoolean(++i, pascoloInfetto);
	      
	      pst.setBoolean(++i, montaEsterna);
	      
	      pst.setBoolean(++i, reinfezione);
	      
	      pst.setBoolean(++i, iatrogena);
	      
	      pst.setBoolean(++i, caniInfetti);
	     
	        pst.setTimestamp(++i, dataProvvedimenti);
	      
	        pst.setString(++i, numeroProvvedimenti);
	      
	         pst.setString(++i, proposteAdozione);
	      
	        pst.setTimestamp(++i, dataImmunizzanti);
	      
	        pst.setString(++i, osservazioni);
	      
	 	 pst.setString(++i, malattia);
	 	 
	 	 pst.setTimestamp(++i, dataUltimoCaso);
	      
	      pst.setTimestamp(++i, dataRevocaSindaco);
	      
	      pst.setString(++i, proposteRevoca);
	      
	      pst.setInt(++i, focolaio_id);
	      
	    
	      
	      
	      
	      pst.execute();
	    		  
	      int id = 0;
	      
	    
	     
	    } catch (SQLException e) {
	       e.printStackTrace();
	    } finally {
	      if (commit) {
	        db.setAutoCommit(true);
	      }
	    }
	    return true;
	  }


	
	
}
