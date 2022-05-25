/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.aspcfs.modules.campioni.base.ModuloCampione;
import org.aspcfs.modules.campioni.base.Pnaa;
import org.aspcfs.modules.campioni.base.Sin;
import org.aspcfs.modules.campioni.base.Ticket;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.DatabaseUtils;

import com.daffodilwoods.daffodildb.server.datasystem.persistentsystem.DatabaseUser;
import com.darkhorseventures.framework.actions.ActionContext;


public class CampioniUtil {
	
	public static final int TIPO_UOVA = 1;
	public static final int TIPO_LATTE = 2;
	
	private static Logger logger = Logger.getLogger("MainLogger");
	
	/**
	 * genera il numero scheda campione sin a partire dal sequence
	 * del database e dal tipo di allevamento (latte/uova)
	 * 
	 * @param tipo - il tipo scheda (1=uova 2=latte)
	 * @param db - la connessione al db
	 * 
	 * @return il numero scheda sin generato automaticamente
	 *
	 */
	public static String generaNumeroSchedaSin(int tipo, Connection db){
  		
		String codice		= "AAAAXXXXUL";
		String anno 		= "";
		String numScheda 	= "";
		String trails	= "";
		int seq = 0;
		  		
		try{
			
	  		if(tipo == TIPO_LATTE){

	  			trails = "L";
	  		} else if (tipo == TIPO_UOVA) {
	  			trails = "U";
	  		}

			numScheda = String.format("%04d", seq);
			
			// recupero l'anno corrente
			PreparedStatement pst = db.prepareStatement("SELECT DATE_PART('year',current_date)");
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				anno = rs.getString(1);
			}
			
			// costruisco la stringa codice
			codice = anno + numScheda + trails;
		
		}catch(SQLException e){
			e.printStackTrace();
			logger.warning("Errore nella generazione del numero scheda");
		}
		
		return codice;
	}
	
   public static String generaNumeroSchedaPNAA(Connection db, String matrice){
  			  		
		String codice		= "AAAAXXXXOX";
		String anno 		= "";
		String numScheda 	= "";
		String trails	= "";
		int seq = 0;
		
		
		try{
		 	//Acqua di abbeverata
			if(matrice == "m7"){
	  			seq = DatabaseUtils.getNextSeqFromSequence(db, "scheda_pnaa_abbev_num_seq");
	  			trails = "X";
	  		} else {
	  			seq = DatabaseUtils.getNextSeqFromSequence(db, "scheda_pnaa_num_seq");
		  	    trails = "O"; //sulla base di un esempio caricato SINVSA
	  		}		
			
			numScheda = String.format("%04d", seq);	
			// recupero l'anno corrente
			PreparedStatement pst = db.prepareStatement("SELECT DATE_PART('year',current_date)");
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				anno = rs.getString(1);
			}
			
			// costruisco la stringa codice
			codice = "R00"+ anno + numScheda + trails;
		
		}catch(SQLException e){
			e.printStackTrace();
			logger.warning("Errore nella generazione del numero scheda");
		}
		
		return codice;
	}
	
	
	/**
	 * verifica l'esistenza della scheda sin nel db a partire
	 * dal codice campione
	 * 
	 * @param db - la connessione al db
	 * @param codice - il codie campione
	 * 
	 * @return il numero scheda sin in caso di esito positivo, null in caso contrario
	 *
	 */
	public static String checkEsistenzaSchedaSin(Connection db, String codice){
		
		String ret = null;
		
		try{
			PreparedStatement pst = db.prepareStatement
					("SELECT a14 FROM scheda_campioni_sin " +
							"WHERE A12 = ?");
			pst.setString(1, codice);
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				ret = rs.getString(1);
			}
			
		} catch (SQLException e){
			e.printStackTrace();
			logger.warning("Errore nella verifica esistenza scheda");
		}
		return ret;
	}
	
  public static String checkEsistenzaSchedaPnaa(Connection db, String codice){
		
		String ret = null;
		
		try{
			PreparedStatement pst = db.prepareStatement
					("SELECT num_scheda FROM scheda_campioni_pnaa " +
							"WHERE a0 = ?");
			pst.setString(1, codice);
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				ret = rs.getString(1);
			}
			
		} catch (SQLException e){
			e.printStackTrace();
			logger.warning("Errore nella verifica esistenza scheda");
		}
		return ret;
	}
	
	/**
	 * riempie la mappa dei campi della scheda campioni sin per compilare il pdf
	 * 
	 * @param codice - il codie campione
	 * @param map - la mappa
	 * @param db - la connessione al db
	 * 
	 */
	
	public static void riempiSchedaHTML(String codice, Sin sinUtente , Connection db){
		
		try{
			PreparedStatement pst = db.prepareStatement
					("SELECT * FROM scheda_campioni_sin " +
							"WHERE A12 = ?");
			
			pst.setString(1, codice);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()){	
				
				String telefono = rs.getString("telefono"); 	// Campo Telefono
				if(telefono != null && !"".equals(telefono)){
					sinUtente.setTelefono(telefono);
				}
				
				String b1_specie = rs.getString("b1_specie"); 	// Campo Telefono
				if(b1_specie != null && !"".equals(b1_specie)){
					sinUtente.setB1Specie(b1_specie);
				}
				
				String b2_testo = rs.getString("b2_testo"); 	// Campo Telefono
				if(b2_testo != null && !"".equals(b2_testo)){
					sinUtente.setB2Testo(b2_testo);
				}
				
				String a1 = rs.getString("a1"); 	
				if(a1 != null && !"".equals(a1)){
					if(a1.equals("0")){
						sinUtente.setA1_1(true);
					}
					else{
						sinUtente.setA1_2(true);
					}
						
				}
				
				String a2_checked = rs.getString("prelev"); 	// Prelevatore checked
				if(a2_checked != null && !"".equals(a2_checked)){
					sinUtente.setA2_check(a2_checked);
				}
				
				String a2_2_checked = rs.getString("a2_2_check"); 	// Punto checked
				if(a2_2_checked != null && !"".equals(a2_2_checked)){
					sinUtente.setA2_2_check((a2_2_checked));
				}
								
				String b1 = rs.getString("b1"); 	
				if(b1 != null && !"".equals(b1)){
					if(b1.equals("0")){
						sinUtente.setB1_1(true);
					}
					else if(b1.equals("1")){
						sinUtente.setB1_2(true);
						
					} else if(b1.equals("2")){
						sinUtente.setB1_3(true);
					}
					else{
						sinUtente.setB1_4(true);
					}
				}
				
				String b2 = rs.getString("b2"); 
				if(b2 != null && !"".equals(b2)){
					if(b2.equals("0")){
							sinUtente.setB2_1(true);
					} else if(b2.equals("1")){
						sinUtente.setB2_2(true);
					}else if(b2.equals("2")){
						sinUtente.setB2_3(true);
					}else {
						sinUtente.setB2_4(true);
					}
				}
				
				String b3 = rs.getString("b3");
				if(b3 != null && !"".equals(b3)){
					if(b3.equals("0")){
						sinUtente.setB3_1(true);
					}
					else if(b3.equals("1")){
						sinUtente.setB3_2(true);
					}
					else if(b3.equals("2")){
						sinUtente.setB3_3(true);
					}
					else{
						sinUtente.setB3_4(true);
							
					}
				}
								
				//riempio le sotto-tabelle
				
				String a11 = rs.getString("a11"); 	// Tabella alimenti
				if(a11 != null && !"".equals(a11)){
					getTabellaSchedaSin(a11, sinUtente);
				}
				
				String b4 = rs.getString("b4"); 	// Tabella alimenti
				if(b4 != null && !"".equals(b4)){
					getTabellaSchedaSin(b4, sinUtente);
				}
				
				// riempio le sotto-tabelle
				String a4 = rs.getString("a4"); 	
				if(a4 != null && !"".equals(a4)){
					getTabellaSchedaSin(a4, sinUtente);
				}
				
				String a13 = rs.getString("a13"); 	
				if(a13 != null && !"".equals(a13)){
					sinUtente.setA13(a13);
				}
				
				String a6 = rs.getString("a6"); 	
				if(a6 != null && !"".equals(a6)){
					sinUtente.setA6(a6);
				}
				//Codice_fiscale manuale
				/*String a2_1 = rs.getString("a2_1"); 	
				if(a2_1 != null && !"".equals(a2_1)){
					sinUtente.setA2_1(a2_1);
				}*/
				
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
			logger.warning("Errore nella verifica esistenza scheda");
		}
		
	}
	
	

public static void riempiSchedaHTMLRsitrutturato(int idCampione, Pnaa pnaa , Connection db){
		
		try {
			  	
		  	PreparedStatement pst = db.prepareStatement
					("SELECT * FROM campioni_fields_value join campioni_html_fields v2 on v2.id = id_campioni_html_fields  " +
							"WHERE id_campione = ? and enabled = true and enabled_campo = true ");
			
			pst.setInt(1, idCampione);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				if (pnaa.getListaCampiPna().get(rs.getString("nome_campo"))!= null)
				{
					HashMap<String, String> valori_x_nome_campo = pnaa.getListaCampiPna().get(rs.getString("nome_campo")) ;
					
					if (rs.getBoolean("multiple")==false)
						valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore_campione"));
					else
						valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore_campione"), rs.getString("valore_campione"));

					pnaa.getListaCampiPna().put(rs.getString("nome_campo"), valori_x_nome_campo);
				}
				else
				{
					HashMap<String, String> valori_x_nome_campo = new HashMap<String, String>();
					
					if (rs.getBoolean("multiple")==false)
						valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore_campione"));
					else
						valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore_campione"), rs.getString("valore_campione"));

					pnaa.getListaCampiPna().put(rs.getString("nome_campo"), valori_x_nome_campo);
				}
						
			}
				
			
		} catch (Exception e){
			e.printStackTrace();
			logger.warning("Errore nella verifica esistenza scheda");
		}
		
	}

public static void riempiSchedaModuloHTMLRsitrutturato(int idCampione, ModuloCampione modulo , Connection db){
	
	try {
		  	
	  	PreparedStatement pst = db.prepareStatement
				("SELECT * FROM moduli_campioni_fields_value join moduli_campioni_html_fields v2 on v2.id = id_moduli_campioni_html_fields  " +
						"WHERE id_campione = ? and enabled_campo = true and enabled = true");
		
		pst.setInt(1, idCampione);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()){
			
			if (modulo.getListaCampiModulo().get(rs.getString("nome_campo"))!= null)
			{
				HashMap<String, String> valori_x_nome_campo = modulo.getListaCampiModulo().get(rs.getString("nome_campo")) ;
				
				if (rs.getBoolean("multiple")==false)
					valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore_campione"));
				else
					valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore_campione"), rs.getString("valore_campione"));

				modulo.getListaCampiModulo().put(rs.getString("nome_campo"), valori_x_nome_campo);
			}
			else
			{
				HashMap<String, String> valori_x_nome_campo = new HashMap<String, String>();
				
				if (rs.getBoolean("multiple")==false)
					valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore_campione"));
				else
					valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore_campione"), rs.getString("valore_campione"));

				modulo.getListaCampiModulo().put(rs.getString("nome_campo"), valori_x_nome_campo);
			}
					
		}
		
		
	} catch (Exception e){
		e.printStackTrace();
		logger.warning("Errore nella verifica esistenza scheda");
	}
	
}

public static void riempiSchedaModuloVUOTOHTMLRsitrutturato(int tipoModulo, ModuloCampione modulo , Connection db){
	
	try {
		  	
	  	PreparedStatement pst = db.prepareStatement
				("SELECT *, '' as valore_campione FROM moduli_campioni_html_fields  " +
						"WHERE tipo_modulo = ? and enabled_campo ");
		
		pst.setInt(1, tipoModulo);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()){
			
			if (modulo.getListaCampiModulo().get(rs.getString("nome_campo"))!= null)
			{
				HashMap<String, String> valori_x_nome_campo = modulo.getListaCampiModulo().get(rs.getString("nome_campo")) ;
				
				if (rs.getBoolean("multiple")==false)
					valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore_campione"));
				else
					valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore_campione"), rs.getString("valore_campione"));

				modulo.getListaCampiModulo().put(rs.getString("nome_campo"), valori_x_nome_campo);
			}
			else
			{
				HashMap<String, String> valori_x_nome_campo = new HashMap<String, String>();
				
				if (rs.getBoolean("multiple")==false)
					valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore_campione"));
				else
					valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore_campione"), rs.getString("valore_campione"));

				modulo.getListaCampiModulo().put(rs.getString("nome_campo"), valori_x_nome_campo);
			}
					
		}
		
			
		
	} catch (Exception e){
		e.printStackTrace();
		logger.warning("Errore nella verifica esistenza scheda");
	}
	
}
	
public static void riempiSchedaHTML(String codice, Pnaa pnaa , Connection db){
		
		try {
			  	
		  	PreparedStatement pst = db.prepareStatement
					("SELECT * FROM scheda_campioni_pnaa " +
							"WHERE a0 = ?");
			
			pst.setString(1, codice);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()){	
				
				String a2 = rs.getString("a2"); 	// Metodo di campionamento
				if(a2 != null && !"".equals(a2)){
					pnaa.setA2(a2); 
				}
				
				String alimenti = rs.getString("dpa"); 	// Alimenti
				if(alimenti != null && !"".equals(alimenti)){
					pnaa.setAlimenti(alimenti); 
				}
				
				String a2_norma = rs.getString("a2_norma"); 	// Metodo di campionamento
				if(a2_norma != null && !"".equals(a2_norma)){
					pnaa.setA2_norma(a2_norma); 
				}
				
				String a5 = rs.getString("a5"); 	// Campo Luogo di prelievo
				if(a5 != null && !"".equals(a5)){
					pnaa.setA5(a5); 
				}
				

				String a6 = rs.getString("a6"); 	// Codice identificativo luogo
				if(a6 != null && !"".equals(a6)){
					pnaa.setA6(a6); 
				} 
				
				//Nel caso in cui il valore venisse sovrascritto dall'utente
				String a11 = rs.getString("a11");
				StringTokenizer st = new StringTokenizer(a11,"=;");
				while(st.hasMoreTokens()){
					String key = st.nextToken();
					String value = st.nextToken();
					
					if(key.equals("a11_1")){
						pnaa.setA11_1(value);
					}
					
					else if(key.equals("a11_2")){
						pnaa.setA11_2(value);
					}
				}
				
				//Nel caso in cui il valore venisse sovrascritto dall'utente
				String a3_1 = rs.getString("a3_1");
				if(a3_1 !=null && !a3_1.equals("")) {
					StringTokenizer st1 = new StringTokenizer(a3_1,"=;");
					while(st1.hasMoreTokens()){
						String key = st1.nextToken();
						String value = st1.nextToken();
						
						if(key.equals("a3_1_1")){
							pnaa.setA3_1_1(value);
						}
						else if(key.equals("a3_1_2")){
							pnaa.setA3_1_2(value);
						}
						else if(key.equals("a3_1_3")){
							pnaa.setA3_1_3(value);
						}
						else if(key.equals("a3_1_4")){
							pnaa.setA3_1_4(value);
						}
						else if(key.equals("a3_1_5")){
							pnaa.setA3_1_5(value);
						}
						else if(key.equals("a3_1_6")){
							pnaa.setA3_1_6(value);
						}
						else if(key.equals("a3_1_7")){
							pnaa.setA3_1_7(value);
						}
						else if(key.equals("a3_1_8")){
							pnaa.setA3_1_8(value);
						}
						else if(key.equals("a3_1_9")){
							pnaa.setA3_1_9(value);
						}
						else if(key.equals("a3_1_10")){
							pnaa.setA3_1_10(value);
						}
						else if(key.equals("a3_1_11")){
							pnaa.setA3_1_11(value);
						}
					}
					
				}
				
				
				String a14 = rs.getString("a14"); 	// Codice Fiscale...di?
				if(a14 != null && !"".equals(a14)){
					pnaa.setA14(a14); 
				} 
				
				String a15 = rs.getString("a15"); 	// Codice Fiscale...di?
				if(a15 != null && !"".equals(a15)){
					pnaa.setA15(a15); 
				} 
				
				String a15b = rs.getString("a15b"); 	// Codice Fiscale...di?
				if(a15b != null && !"".equals(a15b)){
					pnaa.setA15b(a15b); 
				} 
				
				String a16 = rs.getString("a16"); 	// Campo Telefono
				if(a16 != null && !"".equals(a16)){
					pnaa.setA16(a16); 
				}
				
			
				String b2 = rs.getString("b2"); 
				if(b2 != null && !"".equals(b2)){
					pnaa.setB2(b2); //trattamento applicato al mg prelevato
				}
				
				String b4= rs.getString("b4"); 	
				if(b4 != null && !"".equals(b4)){
					pnaa.setB4(b4);//ragione sociale ditta produttrice
				}
				
				String b5= rs.getString("b5"); 	
				if(b5 != null && !"".equals(b5)){
					pnaa.setB5(b5);//indirizzo ditta produttrice
				}
				
				String b8= rs.getString("b8"); 	
				if(b8 != null && !"".equals(b8)){
					pnaa.setB8(b8);//indirizzo ditta produttrice
				}
				
				String b12= rs.getString("b12"); 	
				if(b12 != null && !"".equals(b12)){
					pnaa.setB12(b12);//Paese di produzione
				}
				
				
				String b14= rs.getString("b14"); 	
				if(b14 != null && !"".equals(b14)){
					pnaa.setB14(b14);//data di scadenza
				}

				
				String b16= rs.getString("b16"); 	
				if(b16 != null && !"".equals(b16)){
					pnaa.setB16(b16); //Dimensione lotto
				}
				
			
				String b17 = rs.getString("b17"); 	
				if(b17 != null && !"".equals(b17)){
					pnaa.setB17(b17); //Ingredienti;*/
				}
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
			logger.warning("Errore nella verifica esistenza scheda");
		}
		
	}
	
	
	/**
	 * costruisce una mappa rappresentante la tebella interna
	 * della scheda sin a partire da una stringa delimitata da separatori = e ;
	 * 
	 * @param table - la stringa contenente la tabella
	 * 
	 * @return out - mappa chiave-valore contenente i campi della tabella
	 * 
	 */
	public static void getTabellaSchedaSin(String table, Map<String, Object> map){
		StringTokenizer st = new StringTokenizer(table,"=;");
		while(st.hasMoreTokens()){
			String key = st.nextToken();
			String value = st.nextToken();
			map.put(key, value);
			if(key.equals("b4_5_5")){
				if(value.equals("0")){
					map.put("b4_5_5_1", true);
				}
				else{
					map.put("b4_5_5_2", true);
				}			
			}
			
			if(key.equals("b4_6_5")){
				if(value.equals("0")){
					map.put("b4_6_5_1", true);
				}
				else{
					map.put("b4_5_5_", true);
				}
			}
			
			if(key.equals("b4_1_5")){
				if(value.equals("0")){
					map.put("b4_1_5_1", true);
				}
				else{
					map.put("b4_1_5_2", true);
				}
				
			}
			
			if(key.equals("b4_2_5")){
				if(value.equals("0")){
					map.put("b4_2_5_1", true);
				}
				else{
					map.put("b4_2_5_2", true);
				}
			}
			
			if(key.equals("b4_3_5")){
				if(value.equals("0")){
					map.put("b4_3_5_1", true);
				}
				else{
					map.put("b4_3_5_2", true);
				}
			}
			
			if(key.equals("b4_4_5")){
				if(value.equals("0")){
					map.put("b4_4_5_1", true);
				}
				else{
					map.put("b4_4_5_2", true);
				}
			}
			
			
		}
			
	}
	
	public static void getTabellaSchedaSin(String table, Sin sinUtente){
		StringTokenizer st = new StringTokenizer(table,"=;");
		while(st.hasMoreTokens()){
			String key = st.nextToken();
			String value = st.nextToken();
			
			
			if(key.equals("a4_1")){
				sinUtente.setA4_1(value);
			}
			
			else if(key.equals("a4_2")){
				sinUtente.setA4_2(value);
			}
			
			if(key.equals("a11_1")){
				sinUtente.setA11_1(value);
			}
			
			else if(key.equals("a11_2")){
				sinUtente.setA11_2(value);
			}
			
			if(key.equals("b4_1_1")){
				sinUtente.setB4_1_1(value);
			}
			else if(key.equals("b4_1_2")){
				sinUtente.setB4_1_2(value);
			}
			else if(key.equals("b4_1_3")){
				sinUtente.setB4_1_3(value);
			}
			else if(key.equals("b4_1_4")){
				sinUtente.setB4_1_4(value);
			}
			else if(key.equals("b4_1_5")){
				if(value.equals("0")){
					sinUtente.setB4_1_5_1(true);
				}
				else{
					sinUtente.setB4_1_5_2(true);
				}
				
			}
			else if(key.equals("b4_2_1")){
				sinUtente.setB4_2_1(value);
			}
			else if(key.equals("b4_2_2")){
				sinUtente.setB4_2_2(value);
			}
			else if(key.equals("b4_2_3")){
				sinUtente.setB4_2_3(value);
			}
			else if(key.equals("b4_2_4")){
				sinUtente.setB4_2_4(value);
			}
			else if(key.equals("b4_2_5")){
				if(value.equals("0")){
					sinUtente.setB4_2_5_1(true);
				}
				else{
					sinUtente.setB4_2_5_2(true);
				}
			}
			else if(key.equals("b4_3_1")){
				sinUtente.setB4_3_1(value);
			}
			else if(key.equals("b4_3_2")){
				sinUtente.setB4_3_2(value);
			}
			else if(key.equals("b4_3_3")){
				sinUtente.setB4_3_3(value);
			}
			else if(key.equals("b4_3_4")){
				sinUtente.setB4_3_4(value);
			}
			else if(key.equals("b4_3_5")){
				if(value.equals("0")){
					sinUtente.setB4_3_5_1(true);
				}
				else{
					sinUtente.setB4_3_5_2(true);
				}
			}
			else if(key.equals("b4_4_1")){
				sinUtente.setB4_4_1(value);
			}
			else if(key.equals("b4_4_2")){
				sinUtente.setB4_4_2(value);
			}
			else if(key.equals("b4_4_3")){
				sinUtente.setB4_4_3(value);
			}
			else if(key.equals("b4_4_4")){
				sinUtente.setB4_4_4(value);
			}
			else if(key.equals("b4_4_5")){
				if(value.equals("0")){
					sinUtente.setB4_4_5_1(true);
				}
				else{
					sinUtente.setB4_4_5_2(true);
				}
				
			}
			else if(key.equals("b4_5_1")){
				sinUtente.setB4_5_1(value);
			}
			else if(key.equals("b4_5_2")){
				sinUtente.setB4_5_2(value);
			}
			else if(key.equals("b4_5_3")){
				sinUtente.setB4_5_3(value);
			}
			else if(key.equals("b4_5_4")){
				sinUtente.setB4_5_4(value);
			}
			else if(key.equals("b4_5_5")){
				if(value.equals("0")){
					sinUtente.setB4_5_5_1(true);
				}
				else{
					sinUtente.setB4_5_5_2(true);
				}
			}
			else if(key.equals("b4_6_1")){
				sinUtente.setB4_6_1(value);
			}
			else if(key.equals("b4_6_2")){
				sinUtente.setB4_6_2(value);
			}
			else if(key.equals("b4_6_3")){
				sinUtente.setB4_6_3(value);
			}
			else if(key.equals("b4_6_4")){
				sinUtente.setB4_6_4(value);
			}
			else if(key.equals("b4_6_5")){
				if(value.equals("0")){
					sinUtente.setB4_6_5_1(true);
				}
				else{
					sinUtente.setB4_6_5_2(true);
				}
			}
			else if(key.equals("b4_7_1")){
				sinUtente.setB4_7_1(value);
			}
			else if(key.equals("b4_7_2")){
				sinUtente.setB4_7_2(value);
			}
			else if(key.equals("b4_7_3")){
				sinUtente.setB4_7_3(value);
			}
			else if(key.equals("b4_7_4")){
				sinUtente.setB4_7_4(value);
			}
			else if(key.equals("b4_7_5")){
				if(value.equals("0")){
					sinUtente.setB4_7_5_1(true);
				}
				else{
					sinUtente.setB4_7_5_2(true);
				}
			}
			
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void insertSchedaPNA(Connection db,Ticket campione,ActionContext context) {

		Logger logger = Logger.getLogger("MainLogger");

		boolean esistente = false;
		esistente = disabilitaVecchiaSchedaPNAA(db, campione.getId());
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();		
		String codiceCampione = context.getRequest().getParameter("a0");
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int user_id = user.getUserRecord().getId();
		
		boolean trovato = false ;
		try {
			
			
			
			// determino l'insieme delle colonne
			sql.append("SELECT id, nome_campo,multiple FROM campioni_html_fields where codice_interno_piano_monitoraggio =? order by ordine_campo");
			pst = db.prepareStatement(sql.toString());
			
			String codiceInterno =ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio", campione.getMotivazione_piano_campione()).getCodiceInterno();
			pst.setString(1,codiceInterno );
			rs = pst.executeQuery();
			
			
				sql = new StringBuffer();
				sql.append("INSERT INTO campioni_fields_value (id_campione,id_campioni_html_fields,id_utente_inserimento, valore_campione) values (?,?,?, ?) ");

				
				
				PreparedStatement pst2 = db.prepareStatement(sql.toString());
				while (rs.next())
				{
					trovato = true ;
					int idCampo = rs.getInt("id");
					String nomeCampo = rs.getString("nome_campo");
			
					
					pst2.setInt(1, campione.getId());
					pst2.setInt(2, idCampo);
					pst2.setInt(3, user_id);
					if (nomeCampo.equals("a0"))
					{
						if (!esistente)
							pst2.setString(4, CampioniUtil.generaNumeroSchedaPNAA(db, context.getParameter("b1")));
						else
							pst2.setString(4, context.getParameter(nomeCampo));
						pst2.execute();
						
					}
					else
					{
					
						if (rs.getBoolean("multiple")==false)
						{
							pst2.setString(4, context.getParameter(nomeCampo));
							pst2.execute();
						}
						else
						{
							
							String[] valueSel = context.getRequest().getParameterValues(nomeCampo);
							if(valueSel!= null)
							for(int i = 0 ; i < valueSel.length; i++)
							{
								pst2.setString(4, valueSel[i]);
								pst2.execute();
							}
							
						}
					
					}
					
				}
				
				
				if (trovato == false )
				{
					
					sql = new StringBuffer();
					sql.append("SELECT id_padre from lookup_piano_monitoraggio where code = "+campione.getMotivazione_piano_campione());
					pst = db.prepareStatement(sql.toString());
					//pst.setInt(1, campione.getMotivazione_piano_campione());
					rs = pst.executeQuery();
					if (rs.next())
					{
						
						campione.setMotivazione_piano_campione(rs.getInt(1));
						if (campione.getMotivazione_piano_campione()>0)
							insertSchedaPNA(db,campione,context);
							
					}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
  			
		} finally {
		}
		
	}
	
	
	public static boolean disabilitaVecchiaSchedaPNAA(Connection db,int idCampione) {
		
		PreparedStatement pst = null;
		int result = 0;
		String sql ="UPDATE campioni_fields_value SET enabled = false where id_campione = ? and enabled = true";
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idCampione);
			result = pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result==0)
			return false;
		return true;
	}
	
	public static void insertSchedaModulo(Connection db,Ticket campione, int tipoModulo, ActionContext context) {

		Logger logger = Logger.getLogger("MainLogger");

		disabilitaVecchiaSchedaModulo(db, campione.getId());
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();		
	
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int user_id = user.getUserRecord().getId();
		
		boolean trovato = false ;
		try {
			
			// determino l'insieme delle colonne
			sql.append("SELECT id, nome_campo,multiple FROM moduli_campioni_html_fields where tipo_modulo =? and enabled_campo order by ordine_campo");
			pst = db.prepareStatement(sql.toString());
			pst.setInt(1, tipoModulo);
			rs = pst.executeQuery();
		
				sql = new StringBuffer();
				sql.append("INSERT INTO moduli_campioni_fields_value (id_campione,id_moduli_campioni_html_fields,id_utente_inserimento, valore_campione) values (?,?,?, ?) ");
		
				PreparedStatement pst2 = db.prepareStatement(sql.toString());
				while (rs.next())
				{
					trovato = true ;
					int idCampo = rs.getInt("id");
					String nomeCampo = rs.getString("nome_campo");
			
					
					pst2.setInt(1, campione.getId());
					pst2.setInt(2, idCampo);
					pst2.setInt(3, user_id);
					
					
						if (rs.getBoolean("multiple")==false)
						{
							pst2.setString(4, context.getParameter(nomeCampo));
							pst2.execute();
						}
						else
						{
							
							String[] valueSel = context.getRequest().getParameterValues(nomeCampo);
							for(int i = 0 ; i < valueSel.length; i++)
							{
								pst2.setString(4, valueSel[i]);
								pst2.execute();
							}
							
						}
					
					
					
				}
				
			} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
  			
		} finally {
		}
		
	}
	
	
	public static void disabilitaVecchiaSchedaModulo(Connection db,int idCampione) {
		
		PreparedStatement pst = null;
		String sql ="UPDATE moduli_campioni_fields_value SET enabled = false where id_campione = ? and enabled = true";
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idCampione);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
