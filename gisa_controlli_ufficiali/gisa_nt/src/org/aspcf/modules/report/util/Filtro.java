/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.report.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.aspcf.modules.checklist_benessere.base.Capitolo;
import org.aspcf.modules.checklist_benessere.base.Domanda;
import org.aspcfs.modules.accounts.base.Provincia;


import com.darkhorseventures.framework.beans.GenericBean;


public class Filtro extends GenericBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 	CAMPI DELL IMPRESA
	 */
	
	private String ragioneSociale			;
	private String legaleRappresentante		;
	private int    id_asl					;
	private String desc_asl					;
	private String tipologiaAttivita		;
	private String descrizione_codice       ;
	private String componente_nucleo        ;
	private String componente_nucleo_due    ;
	private String componente_nucleo_tre    ;
	private String componente_nucleo_quattro;
	private String componente_nucleo_cinque ;
	private String componente_nucleo_sei    ;
	private String componente_nucleo_sette  ;
	private String componente_nucleo_otto   ;
	private String componente_nucleo_nove   ;
	private String componente_nucleo_dieci  ;
	private String data_referto             ;
	private String num_reg                  ;
	private String comune					;
	private String piano					;
	private String indirizzo				;
	private String numero					;
	private String codice_fiscale			;
	private String sede_legale				;
	private String indirizzo_legale            ;
	private String data_nascita_rappresentante ;
	private String luogo_nascita_rappresentante ;
	private int controlloUfficiale;
	private String data_fine_controllo;
	private String ric_CE;
	private String city_legale_rapp;
	private String ind_legale_rapp;
	private String prov_legale_rapp;
	private String num_civico_rappresentante;
	private String partita_iva;
	
	//Campi utilizzati per la gestione delle info inserite dall'utente
	private String servizio;
	private String id_controllo_ufficiale;
	private String flag;
	private String uo;
	private String via_amm;
	private String via_ispezione;
	private String nome_presente_ispezione;
	private String luogo_nascita_presente_ispezione;
	private String giorno_presente_ispezione;
	private String mese_presente_ispezione;
	private String anno_presente_ispezione;
	private String luogo_residenza_presente_ispezione;
	private String indirizzo_presente_ispezione;
	private String num_civico_presente_ispezione;
	private String doc_identita_presente_ispezione;
	private String strumenti_ispezione;
	private String descrizione_inizio;
	private String descrizione;
	private String dichiarazione;
	private String responsabile_procedimento;
	private String numero_copie;
	private String note;
	private String codiceImpianto;
	private String codiceSezione;
	
	private String luogo_partenza_trasporto;
	private String data_partenza_trasporto;
	private String ora_partenza_trasporto;
	private String destinazione_trasporto;
	private String nazione_destinazione_trasporto;
	private String nazione_partenza_trasporto;
	private String data_arrivo_trasporto;
	private String ora_arrivo_trasporto;
	private String certificato_trasporto;
	private String data_certificato_trasporto;
	private String luogo_rilascio_trasporto;
	private String ore;
	private String num_campioni;
	
	
	//Campi aggiuntivi per i campioni
	private String motivazione;


	/**
	 * 	CAMPI DEI CAMPIONI
	 */
	
	private String numVerbale				;
	private int idCampione					;
	private int idTampone					;
	private int idControllo					;
	private int orgId						;
	
	/**
	 * 	CAMPI DEI TAMPONI IN MACELLO
	 */
	
	
	
	/**
	   * Constructor for the Filtro object
	   */
	public Filtro() { }
	
	
	/**
	 * RITORNA IL RESULT SET 
	 * DOPO AVER ESEGUITO LA QUERY DI SELEZIONE DEL
	 * CAMPIONE CON ID UGUALE A IDCAMPIONE
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	public ResultSet queryRecord_campioni( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_CAMPIONE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idCampione);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }

	public ResultSet queryRecord_tamponi( Connection db )throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_TAMPONE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idTampone);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_tamponin_sedute_macellazione( Connection db,String dataMacellazione,String sessioneMcellazione,String idMacello )throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			if (sessioneMcellazione== null || "".equals(sessioneMcellazione ))
					{
				sessioneMcellazione = "0" ;
					}
			 String qry = ApplicationProperties.getProperty("GET_TAMPONE_SEDUTA_MACELLAZIONE") +" where 1=1 and id_macello = ? and  to_char(data_macellazione, 'dd/MM/yyyy') = ? and sessione_macellazione=?";
			 
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, Integer.parseInt(idMacello));
			 pst.setString(2, dataMacellazione);
			 pst.setInt(3, Integer.parseInt(sessioneMcellazione));
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_controlli(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	
public ResultSet queryRecord_controlliMolluschiBivalvi(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_MOLLUSCHI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_tipologia_attivita(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_TIPOLOGIA_CONTROLLO");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	
	public ResultSet queryRecord_impresa(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_DATI_IMPRESA");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	
	public ResultSet queryRecord_stabilimenti(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_DATI_STABILIMENTO");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_soa(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_DATI_SOA");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_osm(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_DATI_OSM");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_DATI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_allevamento(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_ALLEVAMENTI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_controlli_stabilimenti(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_STABILIMENTI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_operatori_mercatoIttico(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_MERCATI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	

	public ResultSet queryRecord_operatori_macello(Connection db,int idMacello, String dataMacellazione) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_STABILIMENTI_MACELLI");
			 qry += " and tic.id_macello = ? and to_char(tic.data_macellazione, 'dd/MM/yyyy') = ? " ;
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idMacello);
			 pst.setString(2, dataMacellazione);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
public ResultSet queryRecord_operatori_macello_opu(Connection db,int idMacello, String dataMacellazione) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_OPU_MACELLI");
			 qry += " and tic.id_macello = ? and to_char(tic.data_macellazione, 'dd/MM/yyyy') = ? " ;
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idMacello);
			 pst.setString(2, dataMacellazione);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	

public ResultSet queryRecord_operatori_macello_sintesis(Connection db,int idMacello, String dataMacellazione) throws SQLException {
    
	ResultSet rs = null;
	try{
		 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_SINTESIS_MACELLI");
		 qry += " and tic.id_macello = ? and to_char(tic.data_macellazione, 'dd/MM/yyyy') = ? " ;
		 PreparedStatement pst = db.prepareStatement(qry);
		 pst.setInt(1, idMacello);
		 pst.setString(2, dataMacellazione);
		 
		 rs=pst.executeQuery();
		  
	 } catch ( SQLException e) {
	      throw new SQLException(e.getMessage());
	    }
	return rs;
  }
public ResultSet queryRecord_operatori_commerciali(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_OP_COMMERCIALI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	

	public ResultSet queryRecord_controlli_osm(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_OSM");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_controlli_opu(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_OPU");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
public ResultSet queryRecord_controlli_richieste(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_OPU_RICHIESTE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }

public ResultSet queryRecord_controlli_sintesis(Connection db) throws SQLException {
    
	ResultSet rs = null;
	try{
		 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_SINTESIS");
		 PreparedStatement pst = db.prepareStatement(qry);
		 pst.setInt(1, idControllo);
		 
		 rs=pst.executeQuery();
		  
	 } catch ( SQLException e) {
	      throw new SQLException(e.getMessage());
	    }
	return rs;
  }

public ResultSet queryRecord_controlli_api(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_API");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }

	public ResultSet queryRecord_controlli_soa(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_SOA");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_controlli_aziendeagricole(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_AZIENDE_AGRICOLE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
public ResultSet queryRecord_controlli_acquerete(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_ACQUE_DI_RETE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
public ResultSet queryRecord_controlli_riproduzioneanimale(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_RIPRODUZIONE_ANIMALE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_chk_bns_allevamento(Connection db) throws SQLException {
    
	ResultSet rs = null;
	try{
		 String qry = ApplicationProperties.getProperty("GET_CHK_BNS_ALLEVAMENTO");
		 PreparedStatement pst = db.prepareStatement(qry);
		 pst.setInt(1, orgId);
		 pst.setInt(2, idCampione);
		 
		 rs=pst.executeQuery();
		  
	 } catch ( SQLException e) {
	      throw new SQLException(e.getMessage());
	    }
	return rs;
	}
	
	public ResultSet queryRecord_chk_bns_allevamento_opu(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CHK_BNS_ALLEVAMENTO_OPU");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idStabilimento);
			 pst.setInt(2, idCampione);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
		}
	
	public ResultSet queryRecord_biosicurezza_allevamento(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_BIOSICUREZZA_ALLEVAMENTO");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
		}
	
public ResultSet queryRecord_farmacosorveglianza_allevamento(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_FARMACOSORVEGLIANZA_ALLEVAMENTO");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
		}
	
	public ArrayList<Capitolo> queryRecord_chk_bns_capitoli(Connection db, int specie) throws SQLException {
	    
		ArrayList<Capitolo> capitoli = new ArrayList<Capitolo>();
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CHK_BNS_CAP");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1,specie);
			 rs=pst.executeQuery();
			 while(rs.next()){
				 
				 int codice = rs.getInt("code");
				 String descrizione = rs.getString("description");
				 Capitolo c = new Capitolo();
				 c.setCode(codice);
				 c.setDescription(descrizione);
				 ArrayList<Domanda> domande = this.queryRecord_chk_bns_domande(db, codice);
				 c.setDomandeList(domande);
				 capitoli.add(c);
			 }
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return capitoli;
		}
	
	
	
public ArrayList<Domanda> queryRecord_chk_bns_domande(Connection db, int capitolo) throws SQLException {
	    
		ArrayList<Domanda> domande = new ArrayList<Domanda>();
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CHK_BNS_DOM_BY_ID");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1,capitolo);
			 rs=pst.executeQuery();
			 while(rs.next()){
				 
				 int codice = rs.getInt("code");
				 String descrizione = rs.getString("description");
				 Domanda d = new Domanda();
				 d.setCode(codice);
				 d.setDescription(descrizione);
				 d.setIdCap(capitolo);
				 domande.add(d);
			 }
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return domande;
		}
	
	public ResultSet queryRecord_controlli_farmacosorveglianza(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_FARMACOSORVEGLIANZA");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	
	public ResultSet queryRecord_utente(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_DATI_UTENTE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_controlli_canile(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_CANILE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_controlli_cani_padronali(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_OPERATORI_NON_ALTROVE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
public ResultSet queryRecord_controlli_operatori_sperimentazione_animale(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_OPERATORI_SPERIMENTAZIONE_ANIMALE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
public ResultSet queryRecord_controlli_lab_haccp(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_LAB_HACCP");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	public ResultSet queryRecord_controlli_abusivi(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_ABUSIVI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
	
   public ResultSet queryRecord_controlli_privati(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_PRIVATI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
	
   public ResultSet queryRecord_controlli_op_fuori_asl(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_OP_FUORI_ASL");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
    
   public ResultSet queryRecord_controlli_distributori(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_OP_ABUSIVO");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
   
   public ResultSet queryRecord_controlli_allevamenti(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_ALLEVAMENTI");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
   
   public ResultSet queryRecord_controlli_trasporto(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_TRASPORTO");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
   
   public ResultSet queryRecord_controlli_imbarcazione(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_IMBARCAZIONE");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
   
   public ResultSet queryRecord_operatori_sin(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			 String qry = ApplicationProperties.getProperty("GET_OP_SIN");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	 }
   
   public ResultSet queryRecord_operatori_pnaa(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			
			 String qry = ApplicationProperties.getProperty("GET_OP_PNAA");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, orgId);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	 }
   
   public ResultSet queryRecord_controlli_anagrafica(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_ANAGRAFICA");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
   
   public ResultSet queryRecord_controlli_zone_controllo(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_ZONE_CONTROLLO");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
   
   public ResultSet queryRecord_controlli_punti_di_sbarco(Connection db) throws SQLException {
	    
		ResultSet rs = null;
		try{
			 String qry = ApplicationProperties.getProperty("GET_CONTROLLO_PUNTI_DI_SBARCO");
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1, idControllo);
			 
			 rs=pst.executeQuery();
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return rs;
	  }
 
   
   public boolean isOperatoreMercatoIttico(Connection db) throws SQLException{
		
		PreparedStatement pst = db.prepareStatement("select direct_bill from organization where org_id = ? ");
		pst.setInt(1, orgId);
		ResultSet rs = pst.executeQuery();
		boolean mercatoIttico = false;
		while (rs.next()){
			mercatoIttico = rs.getBoolean("direct_bill");
				
		}
			return mercatoIttico;
		
	}   
   
   public void rollback_sequence(Connection db, int suffix, int org_id) throws SQLException {
	  	
	  	String sequence = "" ;
	  	sequence = "barcode_prelievo_id_seq";
	  	String roll_back = "select setval('"+sequence+"', (select last_value from "+sequence+")-1)";
	  	db.prepareStatement(roll_back).execute();
   }
   	
   
	public String getRagioneSociale() {
		return ragioneSociale;
	}


	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	
	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	
	public void setPiano(String piano) {
		this.piano = piano;
	}
	
	public String getIndirizzo(){
		return indirizzo;
	}
	
	public void setIndirizzo(String indirizzo){
		this.indirizzo = indirizzo;
	}
	
	public String getId_controllo_ufficiale() {
		return id_controllo_ufficiale;
	}


	public void setId_controllo_ufficiale(String id_controllo_ufficiale) {
		this.id_controllo_ufficiale = id_controllo_ufficiale;
	}
	
	
	public void setNumero(String numero){
		this.numero = numero;
	}

	
	public String getNumero(){
		String ind = this.getIndirizzo();
		String num =ind.substring(ind.length()-3,ind.length());
		return num;	
	}
	
	public boolean alfanum (String valore){
		int num = 0;
		int str = 0;
		char [] comodo = valore.toCharArray();
		char prova;

		for (int i=0; i<valore.length(); i++){
			prova = comodo[i];
			if (Character.isDigit(prova)==true){
				str=1;
			} else{
				num=1;
			}	
		}
		if ((str==1) /*&& (num==1)*/) {
			return true;
		} else{
			return false;
		}
	}
	
	public String getNumeroIndLegale(){
		String ind = this.getIndirizzoLegale();
		String num =ind.substring(ind.length()-3,ind.length());
		return num;
	}
	
	public String getCodiceFiscale(){
		return codice_fiscale;
	}
	
	public void setCodiceFiscale(String codice_fiscale){
		this.codice_fiscale = codice_fiscale;
	}
	
	public String getNum_reg() {
		return num_reg;
	}

	public void setNum_reg(String num_reg) {
		this.num_reg = num_reg;
	}

	public String getSedeLegale() {
		return sede_legale;
	}

	public void setSedeLegale(String sede_legale) {
		this.sede_legale = sede_legale;
	}
	
	public void setPartitaIVA(String partita_iva) {
		this.partita_iva = partita_iva;
	}
	
	public String getIndirizzoLegale(){
		return indirizzo_legale;
	}
	
	public void setIndirizzoLegale(String indirizzo_legale){
		this.indirizzo_legale = indirizzo_legale;
	}
	
	public String getLegaleRappresentante() {
		return legaleRappresentante;
	}

	public void setLegaleRappresentante(String legaleRappresentante) {
		this.legaleRappresentante = legaleRappresentante;
	}

	public String getData_nascita_rappresentante() {
		return data_nascita_rappresentante;
	}


	public void setData_nascita_rappresentante(String data_nascita_rappresentante) {
		this.data_nascita_rappresentante = data_nascita_rappresentante;
	}

	public String getLuogo_nascita_rappresentante() {
		return luogo_nascita_rappresentante;
	}

	public void setLuogo_nascita_rappresentante(String luogo_nascita_rappresentante) {
		this.luogo_nascita_rappresentante = luogo_nascita_rappresentante;
	}

	public String getCityLegaleRappresentante() {
		return city_legale_rapp;
	}

	public void setCityLegaleRappresentante(String clr) {
		this.city_legale_rapp= clr;
	}
	
	public String getIndirizzoLegaleRappresentante() {
		return ind_legale_rapp;
	}

	public void setIndirizzoLegaleRappresentante(String ilr) {
		this.ind_legale_rapp= ilr;
	}
	
	public String getProvLegaleRappresentante() {
		return prov_legale_rapp;
	}

	public void setProvinciaLegaleRappresentante(String plr) {
		this.prov_legale_rapp= plr;
	}
	
	public String getNumCivicoRappresentante() {
		return num_civico_rappresentante;
	}

	public void setNumCivicoRappresentante(String num) {
		this.num_civico_rappresentante= num;
	}

	
	public String getData_fine_controllo() {
		return data_fine_controllo;
	}

	public void setData_fine_controllo(String data_fine_controllo) {
		this.data_fine_controllo = data_fine_controllo;
	}
	
	public String getPartitaIVA() {
		return partita_iva;
	}

	
	public String getGiornoNascita(){
		try{
			return this.data_nascita_rappresentante.substring(8,11);
		}catch(NullPointerException ex){
			return "";
		}
	}
	
	public String getAnnoNascita(){
		try{
			return this.data_nascita_rappresentante.substring(0,4);
		}catch(NullPointerException ex){
			return "";
		}
	}
	
	public String getMeseNascita(){
		try{
			return this.data_nascita_rappresentante.substring(5,7);
		}catch(NullPointerException ex){
			return "";
		}
		
	} 
	
	public int getId_asl() {
		return id_asl;
	}


	public void setId_asl(int id_asl) {
		this.id_asl = id_asl;
	}


	public String getDesc_asl() {
		return desc_asl;
	}


	public void setDesc_asl(String desc_asl) {
		this.desc_asl = desc_asl;
	}


	public String getTipologiaAttivita() {
		return tipologiaAttivita;
	}


	public void setTipologiaAttivita(String tipologiaAttivita) {
		this.tipologiaAttivita = tipologiaAttivita;
	}

	public String getRicCE() {
		return ric_CE;
	}


	public void setRicCE(String ric) {
		this.ric_CE = ric;
	}

	public String getNumVerbale() {
		return numVerbale;
	}


	public void setNumVerbale(String numVerbale) {
		this.numVerbale = numVerbale;
	}

	public void setControlloUfficiale(int controlloUfficiale) {
		this.controlloUfficiale = controlloUfficiale;
	}

	public void setServizio(String servizio){
		this.servizio = servizio;
	}
	
	public String getServizio(){
		return servizio;
	}
	
	public void setFlag(String flag){
		this.flag = flag;
	}
	
	public String getFlag(){
		return flag;
	}
	
	
	public int getIdCampione() {
		return idCampione;
	}

	public void setIdCampione(int idCampione) {
		this.idCampione = idCampione;
	}

	public int getIdTampone() {
		return idTampone;
	}

	public void setIdTampone(int idT) {
		this.idTampone = idT;
	}
	
	public void setIdControllo(int idControllo) {
		this.idControllo = idControllo;
	}
	
	public int getIdControllo(){
		return idControllo;
	}
	
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public int getOrgId(){
		return orgId;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public String getDescrizione_codice() {
		return descrizione_codice;
	}

	public int getControlloUfficiale(){
		return controlloUfficiale;
	}
	
	public void setDescrizione_codice(String descrizione_codice) {
		this.descrizione_codice = descrizione_codice;
	}


	public String getComponente_nucleo() {
		return componente_nucleo;
	}


	public void setComponente_nucleo(String componente_nucleo) {
		this.componente_nucleo = componente_nucleo;
	}


	public String getComponente_nucleo_due() {
		return componente_nucleo_due;
	}


	public void setComponente_nucleo_due(String componente_nucleo_due) {
		this.componente_nucleo_due = componente_nucleo_due;
	}


	public String getComponente_nucleo_tre() {
		return componente_nucleo_tre;
	}


	public void setComponente_nucleo_tre(String componente_nucleo_tre) {
		this.componente_nucleo_tre = componente_nucleo_tre;
	}

	public String getComponente_nucleo_quattro() {
		return componente_nucleo_quattro;
	}


	public void setComponente_nucleo_quattro(String componente_nucleo_quattro) {
		this.componente_nucleo_quattro = componente_nucleo_quattro;
	}
	
	
	public String getComponente_nucleo_cinque() {
		return componente_nucleo_cinque;
	}


	public void setComponente_nucleo_cinque(String componente_nucleo_cinque) {
		this.componente_nucleo_cinque = componente_nucleo_cinque;
	}
	
	
	public String getComponente_nucleo_sei() {
		return componente_nucleo_sei;
	}

	public void setComponente_nucleo_sei(String componente_nucleo_sei) {
		this.componente_nucleo_sei = componente_nucleo_sei;
	}

	public String getComponente_nucleo_sette() {
		return componente_nucleo_sette;
	}

	public void setComponente_nucleo_sette(String componente_nucleo_sette) {
		this.componente_nucleo_sette = componente_nucleo_sette;
	}

	public String getComponente_nucleo_otto() {
		return componente_nucleo_otto;
	}

	public void setComponente_nucleo_otto(String componente_nucleo_otto) {
		this.componente_nucleo_otto = componente_nucleo_otto;
	}

	public String getComponente_nucleo_nove() {
		return componente_nucleo_nove;
	}

	public void setComponente_nucleo_nove(String componente_nucleo_nove) {
		this.componente_nucleo_nove = componente_nucleo_nove;
	}

	public String getComponente_nucleo_dieci() {
		return componente_nucleo_dieci;
	}

	public void setComponente_nucleo_dieci(String componente_nucleo_dieci) {
		this.componente_nucleo_dieci = componente_nucleo_dieci;
	}


	
	public String getData_referto() {
		return data_referto;
	}


	public void setData_referto(String data_referto) {
		this.data_referto = data_referto;
	}

	public String getUo() {
		return uo;
	}


	public void setUo(String uo) {
		this.uo = uo;
	}


	public String getVia_amm() {
		return via_amm;
	}


	public void setVia_amm(String via_amm) {
		this.via_amm = via_amm;
	}


	public String getNome_presente_ispezione() {
		return nome_presente_ispezione;
	}


	public void setNome_presente_ispezione(String nome_presente_ispezione) {
		this.nome_presente_ispezione = nome_presente_ispezione;
	}


	public String getLuogo_nascita_presente_ispezione() {
		return luogo_nascita_presente_ispezione;
	}


	public void setLuogo_nascita_presente_ispezione(
			String luogo_nascita_presente_ispezione) {
		this.luogo_nascita_presente_ispezione = luogo_nascita_presente_ispezione;
	}


	public String getMese_presente_ispezione() {
		return mese_presente_ispezione;
	}


	public void setMese_presente_ispezione(String mese_presente_ispezione) {
		this.mese_presente_ispezione = mese_presente_ispezione;
	}


	public String getLuogo_residenza_presente_ispezione() {
		return luogo_residenza_presente_ispezione;
	}


	public void setLuogo_residenza_presente_ispezione(
			String luogo_residenza_presente_ispezione) {
		this.luogo_residenza_presente_ispezione = luogo_residenza_presente_ispezione;
	}


	public String getIndirizzo_presente_ispezione() {
		return indirizzo_presente_ispezione;
	}


	public void setIndirizzo_presente_ispezione(String indirizzo_presente_ispezione) {
		this.indirizzo_presente_ispezione = indirizzo_presente_ispezione;
	}


	public String getNum_civico_presente_ispezione() {
		return num_civico_presente_ispezione;
	}


	public void setNum_civico_presente_ispezione(
			String num_civico_presente_ispezione) {
		this.num_civico_presente_ispezione = num_civico_presente_ispezione;
	}


	public String getStrumenti_ispezione() {
		return strumenti_ispezione;
	}


	public void setStrumenti_ispezione(String strumenti_ispezione) {
		this.strumenti_ispezione = strumenti_ispezione;
	}


	public String getResponsabile_procedimento() {
		return responsabile_procedimento;
	}


	public void setResponsabile_procedimento(String responsabile_procedimento) {
		this.responsabile_procedimento = responsabile_procedimento;
	}


	public String getNumero_copie() {
		return numero_copie;
	}


	public void setNumero_copie(String numero_copie) {
		this.numero_copie = numero_copie;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getGiorno_presente_ispezione() {
		return giorno_presente_ispezione;
	}


	public void setGiorno_presente_ispezione(String giorno_presente_ispezione) {
		this.giorno_presente_ispezione = giorno_presente_ispezione;
	}


	public String getAnno_presente_ispezione() {
		return anno_presente_ispezione;
	}


	public void setAnno_presente_ispezione(String anno_presente_ispezione) {
		this.anno_presente_ispezione = anno_presente_ispezione;
	}


	public String getDoc_identita_presente_ispezione() {
		return doc_identita_presente_ispezione;
	}

	
	public void setDoc_identita_presente_ispezione(
			String doc_identita_presente_ispezione) {
		this.doc_identita_presente_ispezione = doc_identita_presente_ispezione;
	}

	public String getVia_ispezione() {
		return via_ispezione;
	}

	public void setVia_ispezione(String via_ispezione){
		this.via_ispezione = via_ispezione;
	}
	
	public String getDescrizione_inizio() {
		return descrizione_inizio;
	}

	public void setDescrizione_inizio(String descrizione_inizio) {
		this.descrizione_inizio = descrizione_inizio;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getDichiarazione() {
		return dichiarazione;
	}


	public void setDichiarazione(String dichiarazione) {
		this.dichiarazione = dichiarazione;
	}
	
	public String getCodiceImpianto() {
		// TODO Auto-generated method stub
		return codiceImpianto;
	}
		
	public void setCodiceImpianto(String codiceImpianto) {
		// TODO Auto-generated method stub
		this.codiceImpianto = codiceImpianto;
	}
	
	public String getCodiceSezione() {
		// TODO Auto-generated method stub
		return codiceSezione;
	}
		
	public void setCodiceSezione(String codiceSezione) {
		// TODO Auto-generated method stub
		this.codiceSezione = codiceSezione;
	}
	
	
	public String getLuogo_partenza_trasporto() {
		return luogo_partenza_trasporto;
	}


	public void setLuogo_partenza_trasporto(String luogo_partenza_trasporto) {
		this.luogo_partenza_trasporto = luogo_partenza_trasporto;
	}


	public String getData_partenza_trasporto() {
		return data_partenza_trasporto;
	}


	public void setData_partenza_trasporto(String data_partenza_trasporto) {
		this.data_partenza_trasporto = data_partenza_trasporto;
	}


	public String getOra_partenza_trasporto() {
		return ora_partenza_trasporto;
	}


	public void setOra_partenza_trasporto(String ora_partenza_trasporto) {
		this.ora_partenza_trasporto = ora_partenza_trasporto;
	}


	public String getDestinazione_trasporto() {
		return destinazione_trasporto;
	}


	public void setDestinazione_trasporto(String destinazione_trasporto) {
		this.destinazione_trasporto = destinazione_trasporto;
	}


	public String getNazione_destinazione_trasporto() {
		return nazione_destinazione_trasporto;
	}


	public void setNazione_destinazione_trasporto(
			String nazione_destinazione_trasporto) {
		this.nazione_destinazione_trasporto = nazione_destinazione_trasporto;
	}


	public String getData_arrivo_trasporto() {
		return data_arrivo_trasporto;
	}


	public void setData_arrivo_trasporto(String data_arrivo_trasporto) {
		this.data_arrivo_trasporto = data_arrivo_trasporto;
	}


	public String getOra_arrivo_trasporto() {
		return ora_arrivo_trasporto;
	}


	public void setOra_arrivo_trasporto(String ora_arrivo_trasporto) {
		this.ora_arrivo_trasporto = ora_arrivo_trasporto;
	}


	public String getCertificato_trasporto() {
		return certificato_trasporto;
	}


	public void setCertificato_trasporto(String certificato_trasporto) {
		this.certificato_trasporto = certificato_trasporto;
	}


	public String getData_certificato_trasporto() {
		return data_certificato_trasporto;
	}


	public void setData_certificato_trasporto(String data_certificato_trasporto) {
		this.data_certificato_trasporto = data_certificato_trasporto;
	}


	public String getLuogo_rilascio_trasporto() {
		return luogo_rilascio_trasporto;
	}


	public void setLuogo_rilascio_trasporto(String luogo_rilascio_trasporto) {
		this.luogo_rilascio_trasporto = luogo_rilascio_trasporto;
	}

	public String getNazione_partenza_trasporto() {
		return nazione_partenza_trasporto;
	}


	public void setNazione_partenza_trasporto(String nazione_partenza_trasporto) {
		this.nazione_partenza_trasporto = nazione_partenza_trasporto;
	}

	public void setOre(String ore) {
		this.ore = ore;
	}

	public String getOre() {
		return ore;
	}

	public void setNumCampioni(String n) {
		this.num_campioni = n;
	}

	public String getNumCampioni() {
		return num_campioni;
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
	
	
		if(rs_campione.getInt("motivazione_campione") == 2 && rs_campione.getInt("codice_piano") > 0) {
				
				sql.append(" SELECT od.descrizione_lunga as uo, padre.descrizione_lunga as servizio from tipocontrolloufficialeimprese tcu left join oia_nodo od on tcu.id_unita_operativa = od.id " +
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
				sql.append(" SELECT od.descrizione_lunga as uo, padre.descrizione_lunga as servizio from tipocontrolloufficialeimprese tcu left join oia_nodo od on tcu.id_unita_operativa = od.id " +
		                 " left join oia_nodo padre on padre.id = od.id_padre where idcontrollo = ? and id_unita_operativa > 0 ");
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1,rs_campione.getInt("id_controllo_ufficiale"));
				//pst.setInt(2,rs_campione.getInt("motivazione_campione")); non va bene nei casi di motivazione campione (X)
				rs = pst.executeQuery();
				
			}
			while(rs.next()){
				this.setServizio(rs.getString("servizio"));
				this.setUo(rs.getString("uo"));
			}
			
			
		
		
	}
	


	
}