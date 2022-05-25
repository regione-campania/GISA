/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.praticacontributi.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.beans.GenericBean;

public class Pratica extends GenericBean implements Serializable {

	/**
	 * 
	 */
	
	
	
	private static final long serialVersionUID = 1L;
	
	public static int idPraticaComune = 1;
	public static int idPraticaCanile = 2;
	public static int idPraticaLP = 3;
	
	private int id = -1;
	private int numeroDecretoPratica     			= 0;	//numero del decreto della pratica
	private String oggettoPratica 		   			= null;//descrizione della pratica
	private Timestamp dataDecreto 		   			= null;//data di emissione del decreto per la pratica
	private int idTipologiaPratica = 				-1;
   
	private Timestamp dataInizioSterilizzazione  	= null;
	private Timestamp dataFineSterilizzazione	   	= null;
	private int totaleCaniMaschi	   				= 0;	//numero totale di cani che partecipano al pagamento del contributo per la pratica in esame
	private int totaleCaniFemmina    	   			= 0;
	private int totaleCaniCatturati	   				= 0;	//numero totale di cani che partecipano al pagamento del contributo per la pratica in esame
	private int totaleGattiCatturati	   			= 0;	//numero totale di gatti che partecipano al pagamento del contributo per la pratica in esame
	private int totaleCaniPadronali	   				= 0;	//numero totale di cani che partecipano al pagamento del contributo per la pratica in esame
	private int totaleGattiPadronali	   			= 0;	//numero totale di gatti che partecipano al pagamento del contributo per la pratica in esame
	private int caniRestantiMaschi	   				= 0;	
	private int caniRestantiFemmina	   				= 0;	
	private int caniRestantiPadronali				= 0;
	private int gattiRestantiPadronali				= 0;
	private int caniRestantiCatturati				= 0;
	private int gattiRestantiCatturati				= 0;
	private int idAslPratica			   			= -1; 
	private String descrizioneAslPratica 			= null;
	private int enteredBy 						    = -1;
	private int modifiedBy 						    = -1;
	private java.sql.Timestamp entered 			    = null;
	private java.sql.Timestamp modified 			= null;
	private Integer[] comuni							= null;	//array dei comuni 
	private Integer[] canili							= null;	//array dei canili 
	private Integer[] vet							= null;	//array dei comuni 
  	private Vector<Integer> comuniElenco = new Vector<Integer>();//elenco dei comuni
  	private Vector<Integer> caniliElenco = new Vector<Integer>();//elenco dei canili
  	private Vector<Integer> vetElenco = new Vector<Integer>();
  	private Vector<String> caniliElencoNome = new Vector<String>();//elenco dei canili
  	private Vector<String> comuniElencoNome = new Vector<String>();//elenco dei canili
  	private Vector<String> vetElencoNome = new Vector<String>();
	private String dataFineSterilizzazioneStringa;
	private String comuneScelto                     = null;
	private String dataInizioSterilizzazioneStringa;
	private Timestamp data_chiusura_pratica         = null;
	private int statoP = -1;
	
	
	
	public Vector<Integer> getVeterinariElenco() {
	
		return vetElenco;
	
	}
	
	public void setVeterinariElenco(Vector<Integer> vetElenco) {
	
		this.vetElenco = vetElenco;
	
	}
	
	
	
	
	public Vector<String> getVeterinariElencoNome() {
		return vetElencoNome;
	}

	public void setVeterinariElencoNome(Vector<String> vetElencoNome) {
		this.vetElencoNome = vetElencoNome;
	}
	
	
	public Vector<Integer> getComuniElenco() {
		
		return comuniElenco;
	
	}
	
	public void setComuniElenco(Vector<Integer> comuniElenco) {
	
		this.comuniElenco = comuniElenco;
	
	}
	
	
	
	
	public Vector<String> getComuniElencoNome() {
		return comuniElencoNome;
	}

	public void setComuniElencoNome(Vector<String> comuniElencoNome) {
		this.comuniElencoNome = comuniElencoNome;
	}

	public Vector<Integer> getCaniliElenco() {
		
		return caniliElenco;
	
	}
	
	public void setCaniliElenco(Vector<Integer> caniliElenco) {
	
		this.caniliElenco = caniliElenco;
	
	}
	
	
	
	
	
	public Vector<String> getCaniliElencoNome() {
		return caniliElencoNome;
	}

	public void setCaniliElencoNome(Vector<String> caniliElencoNome) {
		this.caniliElencoNome = caniliElencoNome;
	}
	
	
	

	public int getIdTipologiaPratica() {
		return idTipologiaPratica;
	}

	public void setIdTipologiaPratica(int idTipologiaPratica) {
		this.idTipologiaPratica = idTipologiaPratica;
	}
	
	public void setIdTipologiaPratica(String idTipologiaPratica) {
		this.idTipologiaPratica = new Integer (idTipologiaPratica).intValue();
	}


	public void setDataDecreto(Timestamp dataDecreto) {
	
		this.dataDecreto = dataDecreto;
		
	}
	
	public void setDataDecreto(String data) {
		
		this.dataDecreto= DatabaseUtils.parseDateToTimestamp(data);
	
	}
	
	public Timestamp getDataDecreto() {
	
		return dataDecreto;
	
	}
	
	public String getDataDecretoFormattata() {
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String data_dec = "";
		if (dataDecreto!=null){
			java.util.Date data = new Date( dataDecreto.getTime() );
			data_dec=sdf.format(data);
		}
		return data_dec;
	
	}

	public void setDataDecretoFormattata(String data) {

		this.dataDecreto= DatabaseUtils.parseDateToTimestamp(data);
	
	}

	public void setDataInizioSterilizzazione(Timestamp dataInizioSterilizzazione) {

		this.dataInizioSterilizzazione = dataInizioSterilizzazione;
	
	}
	
	public void setDataInizioSterilizzazione(String dataInizioSterilizzazione) {
	
		this.dataInizioSterilizzazione = DatabaseUtils.parseDateToTimestamp(dataInizioSterilizzazione);
	
	}
	
	public Timestamp getDataInizioSterilizzazione() {
	
		return dataInizioSterilizzazione;
	
	}
	
	public void setOggettoPratica(String oggettoPratica) {
	
		this.oggettoPratica = oggettoPratica;
	
	}

	public String getOggettoPratica() {
	
		return oggettoPratica;
	
	}
	
	public void setTotaleCaniCatturati(int totaleCaniCatturati) {
	
		this.totaleCaniCatturati = totaleCaniCatturati;
	
	}
	
	public void setTotaleCaniCatturati(String totaleCaniCatturati) {
	
		this.totaleCaniCatturati = Integer.parseInt(totaleCaniCatturati);
	
	}
	
	public int getTotaleCaniCatturati() {
	
		return totaleCaniCatturati;
	
	}
	
	public void setTotaleGattiCatturati(int totaleGattiCatturati) {
	
		this.totaleGattiCatturati = totaleGattiCatturati;
	
	}
	
	public void setTotaleGattiCatturati(String totaleGattiCatturati) {
	
		this.totaleGattiCatturati = Integer.parseInt(totaleGattiCatturati);
	
	}
	
	public int getTotaleGattiCatturati() {
	
		return totaleGattiCatturati;
	
	}
	
	
	public void setTotaleCaniMaschi(int totaleCaniMaschi) {
		
		this.totaleCaniMaschi = totaleCaniMaschi;
	
	}
	
	public void setTotaleCaniMaschi(String totaleCaniMaschi) {
	
		this.totaleCaniMaschi = Integer.parseInt(totaleCaniMaschi);
	
	}
	
	public int getTotaleCaniMaschi() {
	
		return totaleCaniMaschi;
	
	}
	
	public void setTotaleCaniFemmina(int totaleCaniFemmina) {
	
		this.totaleCaniFemmina = totaleCaniFemmina;
	
	}
	
	public void setTotaleCaniFemmina(String totaleCaniFemmina) {
	
		this.totaleCaniFemmina = Integer.parseInt(totaleCaniFemmina);
	
	}
	
	public int getTotaleCaniFemmina() {
	
		return totaleCaniFemmina;
	
	}
	
	public void setTotaleCaniPadronali(int totaleCaniPadronali) {
	
		this.totaleCaniPadronali = totaleCaniPadronali;
	
	}
	
	public void setTotaleCaniPadronali(String totaleCaniPadronali) {
	
		this.totaleCaniPadronali = Integer.parseInt(totaleCaniPadronali);
	
	}
	
	public int getTotaleCaniPadronali() {
	
		return totaleCaniPadronali;
	
	}
	
	public void setTotaleGattiPadronali(int totaleGattiPadronali) {
	
		this.totaleGattiPadronali = totaleGattiPadronali;
	
	}
	
	public void setTotaleGattiPadronali(String totaleGattiPadronali) {
	
		this.totaleGattiPadronali = Integer.parseInt( totaleGattiPadronali);
	
	}

	public int getTotaleGattiPadronali() {

		return totaleGattiPadronali;
	
	}
	
	public void setCaniRestantiPadronali(int caniRestantiPadronali) {
	
		this.caniRestantiPadronali = caniRestantiPadronali;
	
	}
	
	public int getCaniRestantiPadronali() {
	
		return caniRestantiPadronali;
	
	}
	
	public void setGattiRestantiPadronali(int gattiRestantiPadronali) {
	
		this.gattiRestantiPadronali = gattiRestantiPadronali;
	
	}
	
	public int getGattiRestantiPadronali() {
	
		return gattiRestantiPadronali;
	
	}
	
	public void setCaniRestantiMaschi(int caniRestantiMaschi) {
	
		this.caniRestantiMaschi = caniRestantiMaschi;
	
	}
	
	public int getCaniRestantiMaschi() {
	
		return caniRestantiMaschi;
	
	}
	
	public void setCaniRestantiFemmina(int caniRestantiFemmina) {
	
		this.caniRestantiFemmina = caniRestantiFemmina;
	
	}
	
	public int getCaniRestantiFemmina() {
	
		return caniRestantiFemmina;
	
	}
	
	public void setCaniRestantiCatturati(int caniRestantiCatturati) {
		
		this.caniRestantiCatturati = caniRestantiCatturati;
	
	}
	
	public int getCaniRestantiCatturati() {
	
		return caniRestantiCatturati;
	
	}
	
	public void setGattiRestantiCatturati(int gattiRestantiCatturati) {
	
		this.gattiRestantiCatturati = gattiRestantiCatturati;
	
	}
	
	public int getGattiRestantiCatturati() {
	
		return gattiRestantiCatturati;
	
	}
	
	public void setIdAslPratica(int idAslPratica) {
	
		this.idAslPratica = idAslPratica;
	
	}
	
	public int getIdAslPratica() {
		
		return idAslPratica;
	
	}
	
	public void setDescrizioneAslPratica(String descrizioneAslPratica) {
		
		this.descrizioneAslPratica = descrizioneAslPratica;
	
	}
	
	public String getDescrizioneAslPratica() {
		
		return descrizioneAslPratica;
	
	}
	
	public void setId(int id) {
		
		this.id = id;

	}
	
	public void setId(String tmp) {
	
		this.id = Integer.parseInt(tmp);
	
	}
	
	public int getId() {
	
		return id;

	}
	
	public void setEnteredBy(int enteredBy) {
	
		this.enteredBy = enteredBy;
	
	}
	
	public void setEnteredBy(String tmp) {
	
		this.enteredBy = Integer.parseInt(tmp);
	
	}
	
	public int getEnteredBy() {

		return enteredBy;
	
	}
	
	public void setModifiedBy(int modifiedBy) {
	
		this.modifiedBy = modifiedBy;
	
	}
	
	public void setModifiedBy(String tmp) {
	
		this.modifiedBy = Integer.parseInt(tmp);
	
	}	
	
	public int getModifiedBy() {
	
		return modifiedBy;
	
	}
	
	public void setEntered(java.sql.Timestamp entered) {
	
		this.entered = entered;
	
	}
	
	public java.sql.Timestamp getEntered() {
	
		return entered;
	
	}
	
	public void setModified(java.sql.Timestamp modified) {
	
		this.modified = modified;
	
	}
	
	public java.sql.Timestamp getModified() {
	
		return modified;
	
	}


	public void setDataFineSterilizzazione(Timestamp dataFineSterilizzazione) {
	
		this.dataFineSterilizzazione = dataFineSterilizzazione;
	
	}
	
	public Timestamp getDataFineSterilizzazione() {
	
		return dataFineSterilizzazione;
	
	}
	
	public void setNumeroDecretoPratica(int numeroDecretoPratica) {
	
		this.numeroDecretoPratica = numeroDecretoPratica;
	
	}
	
	public void setNumeroDecretoPratica(String numeroDecretoPratica) {
	
		this.numeroDecretoPratica = Integer.parseInt(numeroDecretoPratica);
	
	}
	
	public int getNumeroDecretoPratica() {
	
		return numeroDecretoPratica;
	
	}
	
	public void setDataFineSterilizzazioneStringa(String dataFineSterilizzazioneStringa) {
		
			this.dataFineSterilizzazioneStringa = dataFineSterilizzazioneStringa;
	
	}
	
	public String getDataFineSterilizzazioneStringa() {
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (dataFineSterilizzazione!=null){
			dataFineSterilizzazioneStringa=sdf.format(dataFineSterilizzazione);
		}
		return dataFineSterilizzazioneStringa;
	
	}
	
	public void setDataFineSterilizzazione(String dataFineSterilizzazione) {
	
		this.dataFineSterilizzazione = DatabaseUtils.parseDateToTimestamp(dataFineSterilizzazione);
	
	}
	
	public void setDataInizioSterilizzazioneStringa(String dataInizioSterilizzazioneStringa) {

		this.dataInizioSterilizzazioneStringa = dataInizioSterilizzazioneStringa;
	
	}
	
	public String getDataInizioSterilizzazioneStringa() {
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (dataInizioSterilizzazione!=null){
			dataInizioSterilizzazioneStringa=sdf.format(dataInizioSterilizzazione);
		}
		return dataInizioSterilizzazioneStringa;
	
	}
	
	public Pratica() {
	
		errors.clear();
    
	}
    
	public Pratica(ResultSet rs)  throws SQLException {
    
		errors.clear();
        buildRecord(rs);      
    
	}
	
	public Pratica(Connection db, Integer tmpId) throws SQLException {
	    
		errors.clear();
        queryRecord(db, tmpId);
    
	}
    
	    
	public void queryRecord(Connection db, int tmpAssetId) throws SQLException {
	
		PreparedStatement pst = null;
	    ResultSet rs = null;
	    pst = db.prepareStatement("Select l.description,p.id, p.asl,p.descrizione, p.numero_decreto, p.data_decreto ," +
	    		"p.entered,p.enteredby," +
	    		"p.modified, p.modifiedby, p.numero_totale_cani_catturati,p.numero_totale_cani_padronali," +
	    		"p.numero_totale_gatti_catturati,p.numero_totale_gatti_padronali, p.data_inizio_sterilizzazione," +
	    		"p.numero_totale_cani_maschi,p.numero_totale_cani_femmina, " +
	    		"p.numero_restante_cani_padronali, p.numero_restante_gatti_padronali,p.numero_restante_cani_catturati, " +
	    		"p.numero_restante_cani_maschi, p.numero_restante_cani_femmina, " +
	    		"p.numero_restante_gatti_catturati," +
	    		"p.data_fine_sterilizzazione, p.data_chiusura_pratica, p.id_tipologia_pratica  " +
	    		" from pratiche_contributi p left join lookup_asl_rif l on l.code=p.asl where  id = ?");
	    pst.setInt(1, tmpAssetId);
	    rs = pst.executeQuery();
	     if (rs.next()) {
		      buildRecord(rs);
		      popolaVet(db,id);
		      popolaComuni(db,id);
		      popolaCanili(db,id);
		      
		  }
	    rs.close();
	    pst.close();
    }	
	
    void buildRecord(ResultSet rs) throws SQLException {
    
    	//pratica table
        id = rs.getInt("id");
        entered =rs.getTimestamp("entered");
        modified=rs.getTimestamp("modified");
        enteredBy=rs.getInt("enteredby");
        modifiedBy=rs.getInt("modifiedby");
        descrizioneAslPratica=rs.getString("description");
        numeroDecretoPratica=rs.getInt("numero_decreto");
        oggettoPratica=rs.getString("descrizione");
        totaleCaniCatturati=rs.getInt("numero_totale_cani_catturati");
        totaleGattiCatturati=rs.getInt("numero_totale_gatti_catturati");
        totaleCaniPadronali=rs.getInt("numero_totale_cani_padronali");
        totaleGattiPadronali=rs.getInt("numero_totale_gatti_padronali");
        totaleCaniMaschi=rs.getInt("numero_totale_cani_maschi");
        totaleCaniFemmina=rs.getInt("numero_totale_cani_femmina");
        dataInizioSterilizzazione=rs.getTimestamp("data_inizio_sterilizzazione");
       	dataFineSterilizzazione=rs.getTimestamp("data_fine_sterilizzazione");
       	dataDecreto=rs.getTimestamp("data_decreto");
       	idAslPratica=rs.getInt("asl");
       	caniRestantiPadronali = rs.getInt("numero_restante_cani_padronali");
       	gattiRestantiPadronali = rs.getInt("numero_restante_gatti_padronali");
       	caniRestantiCatturati = rs.getInt("numero_restante_cani_catturati");
       	gattiRestantiCatturati = rs.getInt("numero_restante_gatti_catturati");
    	caniRestantiMaschi = rs.getInt("numero_restante_cani_maschi");
    	caniRestantiFemmina = rs.getInt("numero_restante_cani_femmina");
        data_chiusura_pratica  = rs.getTimestamp("data_chiusura_pratica");
        idTipologiaPratica = rs.getInt("id_tipologia_pratica");
    
    }
    
	public void setComuni(Integer[] comuni) {
	
		this.comuni = comuni;
	
	}
	
	
	public void setComuni(String[] comuni){

		Integer[] results = new Integer[comuni.length];

		for (int i = 0; i < comuni.length; i++) {
		    try {
		        results[i] = Integer.parseInt(comuni[i]);
		    } catch (NumberFormatException nfe) {};
		}
		
		this.comuni = results;
	}
	
	public Integer[] getVeterinari() {
	
		return vet;
	
	}
	
	
	public void setVeterinari(Integer[] vet) {
		
		this.vet = vet;
	
	}
	
	
	public void setVeterinari(String[] vet){

		Integer[] results = new Integer[vet.length];

		for (int i = 0; i < vet.length; i++) {
		    try {
		        results[i] = Integer.parseInt(vet[i]);
		    } catch (NumberFormatException nfe) {};
		}
		
		this.vet = results;
	}
	
	public Integer[] getComuni() {
	
		return comuni;
	
	}
	
	
	public void setCanili(Integer[] canili) {
		
		this.canili = canili;
	
	}
	
	
	public void setCanili(String[] canili){

		Integer[] results = new Integer[canili.length];

		for (int i = 0; i < canili.length; i++) {
		    try {
		        results[i] = Integer.parseInt(canili[i]);
		    } catch (NumberFormatException nfe) {};
		}
		
		this.canili = results;
	}
	
	public Integer[] getCanili() {
	
		return canili;
	
	}
	
	//metodo per l'inserimento di una nuova pratica
	public boolean insert(Connection db) throws SQLException {
	
		PreparedStatement pst = null;
        id = DatabaseUtils.getNextSeq(db, "pratiche_contributi_id_seq");
        pst = db.prepareStatement(
            "INSERT INTO pratiche_contributi " +
            "(" + (id > -1 ? "id, " : "") + "asl,descrizione," +
            "numero_decreto, " +
            "data_decreto, " +
            "enteredby, " +
            "modifiedby, " +
            "numero_totale_cani_padronali, " +
            "numero_totale_gatti_padronali, " +
            "numero_totale_cani_catturati, " +
            "numero_totale_gatti_catturati, " +
            "numero_totale_cani_maschi, " +
            "numero_totale_cani_femmina, " +
            "numero_restante_cani_padronali, " +
            "numero_restante_gatti_padronali, " +
            "numero_restante_cani_catturati, " +
            "numero_restante_gatti_catturati, " +
            "numero_restante_cani_maschi, " +
            "numero_restante_cani_femmina, " +
            "data_inizio_sterilizzazione, " +
            "data_fine_sterilizzazione," +
            "id_tipologia_pratica) " +
           // "id_tipologia)"+
            "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?)");
            
            int i = 0;
            if (id > -1) {
              pst.setInt(++i, id);
            }
            pst.setInt(++i, idAslPratica);
            pst.setString(++i,oggettoPratica);
            pst.setInt(++i, numeroDecretoPratica);
            DatabaseUtils.setTimestamp(pst, ++i, dataDecreto);
            pst.setInt(++i, enteredBy);
            pst.setInt(++i, modifiedBy);
            pst.setInt(++i, totaleCaniPadronali);
            pst.setInt(++i, totaleGattiPadronali);
            pst.setInt(++i, totaleCaniCatturati);
            pst.setInt(++i, totaleGattiCatturati);
            pst.setInt(++i, totaleCaniMaschi);
            pst.setInt(++i, totaleCaniFemmina);
            pst.setInt(++i, caniRestantiPadronali);
            pst.setInt(++i, gattiRestantiPadronali);
            pst.setInt(++i, caniRestantiCatturati);
            pst.setInt(++i, gattiRestantiCatturati);
            pst.setInt(++i, caniRestantiMaschi);
            pst.setInt(++i, caniRestantiFemmina);
            DatabaseUtils.setTimestamp(pst, ++i, dataInizioSterilizzazione);
            DatabaseUtils.setTimestamp(pst, ++i, dataFineSterilizzazione);
            pst.setInt(++i, idTipologiaPratica);
    	    pst.execute();
		    id = DatabaseUtils.getCurrVal(db, "pratiche_contributi_id_seq", id);
		    
		    if (idTipologiaPratica == Pratica.idPraticaComune || idTipologiaPratica == Pratica.idPraticaLP){
		    	insertComuni(db,comuni,id);
		    }
		    else if (idTipologiaPratica == Pratica.idPraticaCanile){
		    	insertCanili(db);
		    }
		    
		    if ( idTipologiaPratica == Pratica.idPraticaLP){
		    	insertVeterinari(db,vet,id);
		    }
   	      pst.close();
          
	return true;
	
	}

	 //recupero dei comuni di una pratica sulla base dell'id che l'identifica
	public void popolaComuni (Connection db, int codeUser) throws SQLException {
			Statement st = null;
			ResultSet rs = null;
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT comune, nome FROM pratiche_contributi_comuni " +
					"left join comuni1 on (pratiche_contributi_comuni.comune = comuni1.id) WHERE id_pratica  = "+ codeUser );
			
			st = db.createStatement();
			
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
		  		comuniElenco.add(rs.getInt("comune"));
		  		comuniElencoNome.add(rs.getString("nome"));
			}
			rs.close();
			st.close();
	
	}
	
	
	public void popolaVet (Connection db, int codeUser) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT distinct veterinario, contact_.namelast || ' ' || contact_.namefirst as nome FROM pratiche_contributi_veterinari " +
				"left join access_ on (pratiche_contributi_veterinari.veterinario = access_.user_id) left join contact_ on contact_.user_id = access_.user_id WHERE id_pratica  = "+ codeUser );
		
		st = db.createStatement();
		
		rs = st.executeQuery(sql.toString());
		while (rs.next()) {
	  		vetElenco.add(rs.getInt("veterinario"));
	  		vetElencoNome.add(rs.getString("nome"));
		}
		rs.close();
		st.close();

}
	   
	public void popolaCanili (Connection db, int codeUser) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT org_id_canile FROM pratiche_contributi_canili WHERE id_pratica  = "+ codeUser);
		
		st = db.createStatement();
		
		rs = st.executeQuery(sql.toString());
		while (rs.next()) {
			
			int idLineaProduttivaCanileCorrente = rs.getInt("org_id_canile");
			Operatore canileCorrente = new Operatore();
			canileCorrente.queryRecordOperatorebyIdLineaProduttiva(db, idLineaProduttivaCanileCorrente);
	  		caniliElenco.add(idLineaProduttivaCanileCorrente);
	  		caniliElencoNome.add(canileCorrente.getRagioneSociale());
		}
		rs.close();
		st.close();

	}
	

	
	public boolean insertComuni(Connection db,Integer[] comune,int tmp) throws SQLException {
	    	int id_tmp=-1;
	    	
	    	//Flusso 251: modifiche del 03/08 - INIZIO
	    	String comuneToAdd = "?";
	    	if(idTipologiaPratica==idPraticaLP)
	    		comuneToAdd = " (select codiceistatasl::integer from comuni1 where id = ?) ";
	    	//Flusso 251: modifiche del 03/08 - FINE
	    	for (int j=0;j<comuni.length;j++){
	        
	    	PreparedStatement pst = null;
	        id_tmp = DatabaseUtils.getNextSeq(db, "pratiche_contributi_comuni_id_seq");
	        pst = db.prepareStatement(
	            "INSERT INTO pratiche_contributi_comuni " +
	          //  "(" + (id_tmp > -1 ? "id, " : "") + "asl, comune ," +
	            "( asl, comune ," +
	            "enteredby, " +
	            "modifiedby, " +
	            "id_pratica)"+
	            //"VALUES (" + (id_tmp > -1 ? "?," : "") + "?,?,?,?,? )");
	            "VALUES (" + comuneToAdd + ",?,?,?,? )");
	            int i = 0;
/*	            if (id_tmp > -1) {
	              pst.setInt(++i, id);
	            }*/
	          //Flusso 251: modifiche del 03/08 - INIZIO
		    	if(idTipologiaPratica!=idPraticaLP)
		    		pst.setInt(++i, idAslPratica);
		    	else
		    		pst.setInt(++i, comune[j]);
		    	//Flusso 251: modifiche del 03/08 - FINE
	            pst.setInt(++i, comune[j]);//comune
	            pst.setInt(++i, enteredBy);
	            pst.setInt(++i, modifiedBy);
	            pst.setInt(++i, tmp);//id_pratica
	            pst.execute();
			    pst.close();
	            
	    	}
		return true;
	}
	
	
	public boolean insertVeterinari(Connection db,Integer[] veterinario,int tmp) throws SQLException {
    	int id_tmp=-1;
    	for (int j=0;j<vet.length;j++){
        
    	PreparedStatement pst = null;
        id_tmp = DatabaseUtils.getNextSeq(db, "pratiche_contributi_veterinari_id_seq");
        pst = db.prepareStatement(
            "INSERT INTO pratiche_contributi_veterinari " +
            "(  veterinario ," +
            "enteredby, " +
            "modifiedby, " +
            "id_pratica)"+
            "VALUES (?,?,?,? )");
            int i = 0;
            pst.setInt(++i, veterinario[j]);
            pst.setInt(++i, enteredBy);
            pst.setInt(++i, modifiedBy);
            pst.setInt(++i, tmp);
            pst.execute();
		    pst.close();
            
    	}
	return true;
}
	
	
	public boolean insertCanili(Connection db) throws SQLException {
    	
		int id_tmp=-1;
    	for (int j=0;j<canili.length;j++){
        
    		
		

		Operatore canileCorrente = new Operatore();
		canileCorrente.queryRecordOperatorebyIdLineaProduttiva(db, canili[j]);
    	PreparedStatement pst = null;
        id_tmp = DatabaseUtils.getNextSeq(db, "pratiche_contributi_canili_id_seq");
        pst = db.prepareStatement(
            "INSERT INTO pratiche_contributi_canili " +
          //  "(" + (id_tmp > -1 ? "id, " : "") + "asl, comune ," +
            "( asl, canile ," +
            "enteredby, " +
            "modifiedby, " +
            "id_pratica," +
            "org_id_canile)"+
            //"VALUES (" + (id_tmp > -1 ? "?," : "") + "?,?,?,?,? )");
            "VALUES (?,?,?,?,?, ?)");
            int i = 0;
/*	            if (id_tmp > -1) {
              pst.setInt(++i, id);
            }*/
            pst.setInt(++i, idAslPratica);
            pst.setString(++i, canileCorrente.getRagioneSociale());//canile ragione sociale
            pst.setInt(++i, enteredBy);
            pst.setInt(++i, modifiedBy);
            pst.setInt(++i, this.id);//id_pratica
            pst.setInt(++i, canili[j]);//canile
            pst.execute();
		    pst.close();
            
    	}
	return true;
}
	  
	//decremento del numero di cani catturati
	public int aggiornaCatturati(Connection db,int pratica, int idSpecie) throws SQLException {
			int resultCount = 0;
			int i=0;
			PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer();
		    
		    switch (idSpecie){
		    	case Cane.idSpecie : {
		    		sql.append("UPDATE pratiche_contributi SET " +
		            " numero_restante_cani_catturati =numero_restante_cani_catturati-1  WHERE id= ? ");
		    		break;
		    	}
		    	case Gatto.idSpecie : {
		    		sql.append("UPDATE pratiche_contributi SET " +
		            " numero_restante_gatti_catturati = numero_restante_gatti_catturati-1  WHERE id= ? ");
		    	}
		    	
		    }
		    
		    pst = db.prepareStatement(sql.toString());
		    pst.setInt(++i,pratica);
		    resultCount = pst.executeUpdate();
		    pst.close();
		    
		    return resultCount;
		    
		}
	
	public static int aggiornaMaschiFemmina(Connection db, int idPratica, String sesso, int incremento) throws SQLException {
		int resultCount = 0;
		int i=0;
		PreparedStatement pst = null;
	    StringBuffer sql = new StringBuffer();
	    sesso = sesso.toUpperCase();
	    
	    switch (sesso){
	    	case "M" : {
	    		sql.append("UPDATE pratiche_contributi SET " +
	            " numero_restante_cani_maschi =numero_restante_cani_maschi - ?  WHERE id= ? ");
	    		break;
	    	}
	    	case "F" : {
	    		sql.append("UPDATE pratiche_contributi SET " +
	    	            " numero_restante_cani_femmina =numero_restante_cani_femmina - ?  WHERE id= ? ");
	    	}
	    	
	    }
	    
	    pst = db.prepareStatement(sql.toString());
	    pst.setInt(++i,incremento);
	    pst.setInt(++i,idPratica);
	    resultCount = pst.executeUpdate();
	    pst.close();
	    
	    return resultCount;
	    
	}
		
		//decremento del numero di cani padronali
		public int aggiornaPadronali(Connection db,int pratica, int idSpecie) throws SQLException {
			int resultCount = 0;
			int i=0;
			PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer();
		    
		    switch (idSpecie){
	    	case Cane.idSpecie : {
	    		sql.append("UPDATE pratiche_contributi SET " +
		            " numero_restante_cani_padronali = numero_restante_cani_padronali-1  WHERE id= ? ");
	    		break;
	    	}	
	    	case Gatto.idSpecie : {
	    		sql.append("UPDATE pratiche_contributi SET " +
	            " numero_restante_gatti_padronali = numero_restante_gatti_padronali-1  WHERE id= ? ");
	    	}
		    }
		    
		    pst = db.prepareStatement(sql.toString());
		    pst.setInt(++i,pratica);
		    resultCount = pst.executeUpdate();
		    pst.close();
		    
		    return resultCount;	    
		}
		
		//incremento del numero di cani catturati
		public int aggiornaCatturatiIncremento(Connection db,int pratica, int idSpecie) throws SQLException {
				int resultCount = 0;
				int i=0;
				PreparedStatement pst = null;
			    StringBuffer sql = new StringBuffer();
			    
			    switch (idSpecie){
			    	case Cane.idSpecie : {
			    		sql.append("UPDATE pratiche_contributi SET " +
			            " numero_restante_cani_catturati =numero_restante_cani_catturati+1  WHERE id= ? ");
			    		break;
			    	}
			    	case Gatto.idSpecie : {
			    		sql.append("UPDATE pratiche_contributi SET " +
			            " numero_restante_gatti_catturati = numero_restante_gatti_catturati+1  WHERE id= ? ");
			    	}
			    	
			    }
			    
			    pst = db.prepareStatement(sql.toString());
			    pst.setInt(++i,pratica);
			    resultCount = pst.executeUpdate();
			    pst.close();
			    
			    return resultCount;
			    
			}
		
			//decremento del numero di cani padronali
			public int aggiornaPadronaliIncremento(Connection db,int pratica, int idSpecie) throws SQLException {
				int resultCount = 0;
				int i=0;
				PreparedStatement pst = null;
			    StringBuffer sql = new StringBuffer();
			    
			    switch (idSpecie){
		    	case Cane.idSpecie : {
		    		sql.append("UPDATE pratiche_contributi SET " +
			            " numero_restante_cani_padronali =numero_restante_cani_padronali+1  WHERE id= ? ");
		    		break;
		    	}	
		    	case Gatto.idSpecie : {
		    		sql.append("UPDATE pratiche_contributi SET " +
		            " numero_restante_gatti_padronali = numero_restante_gatti_padronali+1  WHERE id= ? ");
		    	}
			    }
			    
			    pst = db.prepareStatement(sql.toString());
			    pst.setInt(++i,pratica);
			    resultCount = pst.executeUpdate();
			    pst.close();
			    
			    return resultCount;	    
			}
			
			
		public static int controlli_pratica(Connection db,int pratica,String comune, int asl) throws SQLException {
			int sn = -1;
			int i=0;
			PreparedStatement pst = null;
		    ResultSet rs = null;
			
			pst= db.prepareStatement("Select count(*) as  records from pratiche_contributi a " +
		    		" join  pratiche_contributi_comuni p on p.id_pratica=a.id " +
		    		"where a.asl= ? and a.id= ?  and p.comune= ? and trashed_date is null");
		    

		    pst.setInt(++i,asl);
		    pst.setInt(++i,pratica);
		    pst.setString(++i,comune);
		    rs = pst.executeQuery();
		     if (rs.next()) {
		   	  	sn = rs.getInt("records");
		     }
		    pst.close();
		    rs.close();
			    
		    return sn;
		    
		}
		
		private boolean buildCompleteParentList = false;
		/**
		   *  Sets the buildCompleteParentList attribute of the Asset object
		   *
		   * @param  tmp  The new buildCompleteParentList value
		   */
		  public void setBuildCompleteParentList(boolean tmp) {
		    this.buildCompleteParentList = tmp;
		  }
		  

		  /**
		   *  Gets the buildCompleteParentList attribute of the Asset object
		   *
		   * @return    The buildCompleteParentList value
		   */
		  public boolean getBuildCompleteParentList() {
		    return buildCompleteParentList;
		  }
		  private boolean includeMe = false;
		  /**
		   *  Gets the includeMe attribute of the Asset object
		   *
		   * @return    The includeMe value
		   */
		  public boolean getIncludeMe() {
		    return includeMe;
		  }


		  /**
		   *  Sets the includeMe attribute of the Asset object
		   *
		   * @param  tmp  The new includeMe value
		   */
		  public void setIncludeMe(boolean tmp) {
		    this.includeMe = tmp;
		  }


		  /**
		   *  Sets the includeMe attribute of the Asset object
		   *
		   * @param  tmp  The new includeMe value
		   */
		  public void setIncludeMe(String tmp) {
		    this.includeMe = DatabaseUtils.parseBoolean(tmp);
		  }


		  /**
		   *  Gets the buildCompleteHierarchy attribute of the Asset object
		   *
		   * @return    The buildCompleteHierarchy value
		   */
		  public boolean getBuildCompleteHierarchy() {
		    return buildCompleteHierarchy;
		  }


		  /**
		   *  Sets the buildCompleteHierarchy attribute of the Asset object
		   *
		   * @param  tmp  The new buildCompleteHierarchy value
		   */
		  public void setBuildCompleteHierarchy(boolean tmp) {
		    this.buildCompleteHierarchy = tmp;
		  }


		  /**
		   *  Sets the buildCompleteHierarchy attribute of the Asset object
		   *
		   * @param  tmp  The new buildCompleteHierarchy value
		   */
		  public void setBuildCompleteHierarchy(String tmp) {
		    this.buildCompleteHierarchy = DatabaseUtils.parseBoolean(tmp);
		  }
  

		  private boolean buildCompleteHierarchy = false;
		  
		  public void buildCompleteHierarchy(Connection db) throws SQLException {
			    childList = new PraticaList();
			    childList.setBuildCompleteHierarchy(this.getBuildCompleteHierarchy());
			    childList.setParentId(this.getId());
			    childList.buildList(db);
			  }
		  
		  private PraticaList childList = null;
		  private PraticaList parentList = null;
		  
		  

		  /**
		   *  Gets the childList attribute of the Asset object
		   *
		   * @return    The childList value
		   */
		  public PraticaList getChildList() {
		    return childList;
		  }


		  /**
		   *  Sets the childList attribute of the Asset object
		   *
		   * @param  tmp  The new childList value
		   */
		  public void setChildList(PraticaList tmp) {
		    this.childList = tmp;
		  }


		  public PraticaList getParentList() {
			    return parentList;
			  }

		  public void setParentList(PraticaList tmp) {
			    this.parentList = tmp;
			  }
		public void setComuneScelto(String comuneScelto) {
			this.comuneScelto = comuneScelto;
		}
		public String getComuneScelto() {
			return comuneScelto;
		}

		public void setData_chiusura_pratica(Timestamp data_chiusura_pratica) {
			this.data_chiusura_pratica = data_chiusura_pratica;
		}

		public void setData_chiusura_pratica(String data_chiusura_pratica) {
			this.data_chiusura_pratica =DatabaseUtils.parseDateToTimestamp( data_chiusura_pratica);
		}

		public Timestamp getData_chiusura_pratica() {
			return data_chiusura_pratica;
		}

		public void setStatoP(int statoP) {
			this.statoP = statoP;
		}

		public int getStatoP() {
			return statoP;
		}

		public boolean settaChiusura(Connection db,int id) throws SQLException {
			
			// TODO Auto-generated method stub
			boolean chiuso=false;
			
			int resultCount = 0;
			int i=0;
			PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer();
		    sql.append("UPDATE pratiche_contributi SET modified=CURRENT_TIMESTAMP, modifiedby= ?, data_chiusura_pratica= CURRENT_TIMESTAMP  WHERE id= ? ");
		    
		    pst = db.prepareStatement(sql.toString());
		    
		    pst.setInt(++i, modifiedBy);
		    pst.setInt(++i,id);
		    resultCount = pst.executeUpdate();
		    pst.close();
		    
		    if (resultCount == 1) {
			      chiuso = true;
			    }
			return true;
			
		}

		public boolean settaProroga(Connection db,int user, int id2, Timestamp dataProroga) throws SQLException {
			// TODO Auto-generated method stub
			boolean chiuso = false;
			
			try {
			
			      int resultCount = 0;
			      int i=0;
			      PreparedStatement pst = null;
			      StringBuffer sql = new StringBuffer();
			      
			      sql.append("UPDATE pratiche_contributi SET modified=CURRENT_TIMESTAMP, modifiedby= ?, data_fine_sterilizzazione= ?  WHERE id= ? ");
			      
			      pst = db.prepareStatement(sql.toString());
		    
			      pst.setInt(++i, user);
			      DatabaseUtils.setTimestamp(pst, ++i, dataProroga);
			      pst.setInt(++i,id2);
		
			      resultCount = pst.executeUpdate();
			      pst.close();
		    
			      if (resultCount == 1) {
			    	  chiuso = true;
			      }
			      
				}
			catch (SQLException e) {
			     
			      throw new SQLException(e.getMessage());
			    } finally {
			      
			    }
			 return true;
		}

		public int buildListFromDet(Connection db, int detentoreId) {
			
			int i=0;
			PreparedStatement pst = null;
		    ResultSet rs = null;
		    int count = 0;
			try {
				pst= db.prepareStatement("Select count(*) as  records from pratiche_contributi a " +
						" join  pratiche_contributi_canili p on p.id_pratica=a.id " +
						" where p.org_id_canile = ? and a.data_chiusura_pratica is null and a.trashed_date is null ");
				
				 pst.setInt(++i,detentoreId);
				 rs = pst.executeQuery();
				 if (rs.next()) {
				   	  	count = rs.getInt("records");
				 }
				 
				 pst.close();
				 rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    return count;
		    
		}

		public Vector <String> getNomiComuni(Connection db) throws SQLException{
			
			ComuniAnagrafica comuni = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni =  comuni.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			Vector <String> comuniNomi = new Vector<String>();
			
			for (int i = 0 ; i < this.getComuniElenco().size(); i++ ){
				comuniNomi.add(comuniList.getSelectedValue(this.getComuniElenco().get(i)));
			}
			
			return comuniNomi;
		}
		
		
		
		public String getComuniElencoNomeTruncate() 
		{
			String comuni = "";
			int i=0;
			for(i=0;i<comuniElencoNome.size() && i<=10;i++)
			{
				if(i>0)
					comuni+=", " + comuniElencoNome.get(i);
				else
					comuni+=comuniElencoNome.get(i);
			}
			return comuni;
			
		}
		
		
		
		public String getComuniElencoNomeToString() 
		{
			String comuni = "";
			int i=0;
			for(i=0;i<comuniElencoNome.size();i++)
			{
				if(i>0)
					comuni+=", " + comuniElencoNome.get(i).replaceAll("'", "");
				else
					comuni+=comuniElencoNome.get(i).replaceAll("'", "");
			}
			return comuni;
			
		}
		
		
}
