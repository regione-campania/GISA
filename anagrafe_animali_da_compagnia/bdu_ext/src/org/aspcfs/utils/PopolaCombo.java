/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import oracle.sql.TIMESTAMP;

import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.OperatoreList;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;


import com.darkhorseventures.framework.actions.ActionContext;

public class PopolaCombo {


	static Logger logger = Logger.getLogger("MainLogger");

	public SoggettoFisico verificaAslSoggetto(String cf ) throws SQLException
	{
		
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		SoggettoFisico soggettoEsistente = null ;
		try
		{
			db = GestoreConnessioni.getConnection()	;
		
		//Modifica del 04/03/2020 - Ticket 015572
		OperatoreList operatoreList = new OperatoreList();
		operatoreList.setCodiceFiscale(cf);
		Integer[] idLineaProduttiva = new Integer[9];
		idLineaProduttiva[0] = 1;
		idLineaProduttiva[1] = 2;
		idLineaProduttiva[2] = 3;
		idLineaProduttiva[3] = 4;
		idLineaProduttiva[4] = 5;
		idLineaProduttiva[5] = 6;
		idLineaProduttiva[6] = 7;
		idLineaProduttiva[7] = 8;
		idLineaProduttiva[8] = 9;
		operatoreList.setIdLineaProduttiva(idLineaProduttiva);
		operatoreList.buildList(db);
		if(!operatoreList.isEmpty())
			soggettoEsistente = new SoggettoFisico(cf,db);
		
		
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			GestoreConnessioni.freeConnection(db);
		}
		
		/*Metodo richiamato sul soggetto fisico proveniente dalla request*/
		/**/
		
		return soggettoEsistente ;
		
	}
	
	public static Object[] getSchema(String tabella) {
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<String> nomi = new ArrayList<String>();
		ArrayList<String> tipi = new ArrayList<String>();
		Object[] toRet = new Object[2];
		try {
			db = DbUtil.getConnection();
			String sel = " SELECT  a.attname as Column,"
					+ " pg_catalog.format_type(a.atttypid, a.atttypmod) as Datatype "
					+ "FROM   pg_catalog.pg_attribute a "
					+ "WHERE a.attnum > 0 AND NOT a.attisdropped AND a.attrelid = ( "
					+ "SELECT c.oid FROM pg_catalog.pg_class c LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace WHERE c.relname =? "
					+ " AND pg_catalog.pg_table_is_visible(c.oid) " + " ) ";
			pst = db.prepareStatement(sel);
			pst.setString(1, tabella);
			rs = pst.executeQuery();
			while (rs.next()) {
				nomi.add(rs.getString(1));
				tipi.add(rs.getString(2));

			}

			Object[] nomiObj = new Object[nomi.size()];
			int i = 0;
			for (String nome : nomi) {
				nomiObj[i] = nome;
				i++;

			}

			Object[] tipiObj = new Object[nomi.size()];
			i = 0;
			for (String nome : tipi) {
				tipiObj[i] = nome;
				i++;

			}
			toRet[0] = nomiObj;
			toRet[1] = tipiObj;

		} catch (SQLException e) {

		} finally {
			DbUtil.close(rs, pst, db);
		}

		return toRet;

	}








	/**
	 * 
	 * Questa funzione seleziona i comuni dal DB per asl
	 * 
	 **/
	public static Object[] getValoriComboComuniAsl(int idAsl) {

		Object[] ret = new Object[2];

		// HashMap<String, String> valori =new HashMap<String,String>();
		ArrayList<String> valori = new ArrayList<String>();

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = DbUtil.getConnection();

			String select = "select id, nome from comuni1 c,lookup_asl_rif asl where c.codiceistatasl=asl.codiceistat and asl.enabled=true and trashed is false ";

			if (idAsl != -1 && idAsl != -2) {
				select += " and asl.code = ? order by nome ";
			} else {
				select += " order by nome ";
			}

			pst = db.prepareStatement(select);
			if (idAsl != -1 && idAsl != -2) {
				pst.setInt(1, idAsl);
			}
			rs = pst.executeQuery();
			int i = 1;

			while (rs.next()) {
				String value = rs.getString("nome");
				valori.add(valori.size(), (value.replaceAll("'", "-")));

			}
			Object[] ind = new Object[valori.size() + 1];
			Object[] val = new Object[valori.size() + 1];

			ind[0] = "";
			val[0] = "                ";

			for (String kiave : valori) {
				ind[i] = kiave;
				val[i] = kiave;
				i++;
			}
			ret[0] = ind;
			ret[1] = val;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, pst, db);
		}

		return ret;

	}

	/**
	 * 
	 * Questa funzione seleziona i comuni dal DB per asl, estraendo anche l'id
	 * del comune MODIFICA VERONICA: in caso di pratica per canili
	 * (idTipologiaPratica = 2) seleziona l'elenco dei canili per asl
	 * 
	 **/
	public static Object[] getValoriComboComuniAslId(int idAsl,
			int idTipologiaPratica) {

		Object[] ret = new Object[2];

		// HashMap<String, String> valori =new HashMap<String,String>();
		ArrayList<Integer> valori = new ArrayList<Integer>();

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			db = DbUtil.getConnection();

			if (idTipologiaPratica == 1 || idTipologiaPratica==3) {

				String select = "select id, nome from comuni1 c,lookup_asl_rif asl where c.codiceistatasl=asl.codiceistat and asl.enabled=true and trashed is false ";

				if (idAsl != -1 && idAsl != -2) {
					select += " and asl.code = ? order by nome ";
				} 
				else if(idTipologiaPratica==3){
					select += " and asl.code >= 201 and asl.code <=207 order by nome ";
				}
				else {
					select += " order by nome ";
				}

				pst = db.prepareStatement(select,
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);

				if (idAsl != -1 && idAsl != -2) {
					pst.setInt(1, idAsl);
				}
				rs = pst.executeQuery();
				int i = 1;

				int rowcount = 0;

				if (rs.last()) {
					rowcount = rs.getRow();
					rs.beforeFirst(); // not rs.first() because the rs.next()
										// below will move on, missing the first
										// element
				}

				Object[] ind = new Object[rowcount + 1];
				Object[] val = new Object[rowcount + 1];

				ind[0] = "";
				val[0] = "                ";

				while (rs.next()) {
					String value = rs.getString("nome");
					int id = rs.getInt("id");
					ind[i] = id;
					val[i] = value.replaceAll("'", "-");
					i++;

				}

				ret[0] = ind;
				ret[1] = val;
			} else if (idTipologiaPratica == 2) { // Canili

				OperatoreList listaCanili = new OperatoreList();
				if (idAsl != -1 && idAsl != -2) {
					listaCanili.setIdAsl(idAsl);
					}
					try {
						Integer[] idLineaProduttiva = new Integer[1];
						idLineaProduttiva[0] = 5;						
						listaCanili.setIdLineaProduttiva(idLineaProduttiva);
						listaCanili.buildList(db);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					 int rowcount = listaCanili.size();
					
					int i = 1;
					Object[] ind = new Object[rowcount + 1];
					Object[] val = new Object[rowcount + 1];

					ind[0] = "";
					val[0] = "                ";
					
					Iterator j = listaCanili.iterator();
				while (j.hasNext()){
					Operatore op = (Operatore) j.next();
					if( op.getListaStabilimenti()!=null &&  !op.getListaStabilimenti().isEmpty())
					{
						Stabilimento stab = (Stabilimento) op.getListaStabilimenti().get(0);
						LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
						ind[i] = lp.getId();
						val[i] = op.getRagioneSociale();
						i++;
					}
				}
				
				ret[0] = ind;
				ret[1] = val;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, pst, db);
		}

		return ret;

	}

	public static Object[] getValoriAsl(int idcomune) {
		Object[] ret = new Object[2];
		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = DbUtil.getConnection();

			String select = "select code, description from lookup_site_id where enabled = true ";

			if (idcomune > 0)
				select += " and codiceistat IN (select codiceistatasl from comuni1 where id = ? and trashed is false ) ";

			pst = db.prepareStatement(select);
			if (idcomune > 0)
				pst.setInt(1, idcomune);
			rs = pst.executeQuery();
			int i = 0;

			while (rs.next()) {
				int code = rs.getInt("code");
				String value = rs.getString("description");
				valori.put(code, value);

			}
			Object[] ind = new Object[valori.size()];
			Object[] val = new Object[valori.size()];

			for (Integer kiave : valori.keySet()) {
				ind[i] = kiave;
				val[i] = valori.get(kiave);
				i++;
			}
			ret[0] = ind;
			ret[1] = val;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, pst, db);
		}

		return ret;

	}

	public static Object[] getValoriComuniASL(String comune) {
		Object[] ret = new Object[2];
		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = DbUtil.getConnection();

			String select = "select code, description from lookup_site_id where enabled = true ";

			if (comune != null && !comune.equals("")) {
				select += " and codiceistat IN (select codiceistatasl from comuni where comune = ? ) ";
			} else {
				select += " ; ";
			}

			pst = db.prepareStatement(select);
			if (comune != null && !comune.equals("")) {
				pst.setString(1, comune);
			}
			rs = pst.executeQuery();
			int i = 0;

			while (rs.next()) {
				int code = rs.getInt("code");
				String value = rs.getString("description");
				valori.put(code, value);

			}
			Object[] ind = new Object[valori.size()];
			Object[] val = new Object[valori.size()];

			for (Integer kiave : valori.keySet()) {
				ind[i] = kiave;
				val[i] = valori.get(kiave);
				i++;
			}
			ret[0] = ind;
			ret[1] = val;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, pst, db);
		}

		return ret;

	}

	// Metodo che a partire dalla provincia seleziona l'asl

	public static Object[] getValoriAslProvincia(String provincia) {

		Object[] ret = new Object[2];
		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		// HashMap<String, String> valori =new HashMap<String,String>();
		ArrayList<Integer> asl_id = new ArrayList<Integer>();

		if (provincia != null) {
			if (provincia.equals("AV")) {
				asl_id.add(201);

			} else if (provincia.equals("BN")) {
				asl_id.add(202);
			} else if (provincia.equals("CE")) {
				asl_id.add(203);

			} else if (provincia.equals("NA")) {
				asl_id.add(204);
				asl_id.add(205);
				asl_id.add(206);

			} else if (provincia.equals("SA")) {
				asl_id.add(207);

			}
		}

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = DbUtil.getConnection();
			String select = null;
			String array_valori = asl_id.toString();
			int i = array_valori.length();
			String solo_valori_array = array_valori.substring(1, i - 1);
			select = "select code,description from lookup_site_id where code IN  ("
					+ solo_valori_array + ") and enabled = true ";
			pst = db.prepareStatement(select);

			rs = pst.executeQuery();

			int j = 1;

			while (rs.next()) {
				int code = rs.getInt("code");
				String value = rs.getString("description");
				valori.put(code, value);

			}

			Object[] ind = new Object[valori.size() + 1];
			Object[] val = new Object[valori.size() + 1];

			ind[0] = -1;
			val[0] = "--- SELEZIONA VOCE ---";

			for (Integer kiave : valori.keySet()) {
				ind[j] = kiave;
				val[j] = valori.get(kiave);
				j++;
			}
			ret[0] = ind;
			ret[1] = val;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, pst, db);
		}

		return ret;

	}


	public boolean aggiornaMQCanile(String idLinea, String mqOLD, String mqNEW, int idUtente) {

		try 
		{
			int mqInt = Integer.parseInt(mqNEW);
		}
		catch (Exception e)
		{
			return false;
		}
		 Connection db = null;
	     PreparedStatement pst = null;
	     ResultSet rs = null;
	        
		 String sqlExist = "select count(*) as conta from opu_informazioni_canile where id_relazione_stabilimento_linea_produttiva = ?";
	     String sqlInsert = "insert into opu_informazioni_canile (id_relazione_stabilimento_linea_produttiva,mq_disponibili) values (?,?)";
		 String sql = "update opu_informazioni_canile set note_internal_use_only = concat_ws (?, note_internal_use_only, ?),  mq_disponibili = ? where id_relazione_stabilimento_linea_produttiva = ?;";
       
        try 
        {
        		db = DbUtil.getConnection();
        		
        		pst = db.prepareStatement(sqlExist);
                pst.setInt(1, Integer.parseInt(idLinea));
                rs = pst.executeQuery();
                if(rs.next() && rs.getInt("conta")>0)
	            {
	                pst = db.prepareStatement(sql);
	                
	                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String data = sdf.format(new Date(System.currentTimeMillis()));
					String note = "Valore precedente quadratura: "+mqOLD+"- MQ attuali: "+mqNEW+"- Modifica effettuata in data "+data+" da utente "+idUtente;
	                int i = 0;
	                pst.setString(++i, ";");
	                pst.setString(++i, note);
	                pst.setInt(++i, Integer.parseInt(mqNEW));
	                pst.setInt(++i, Integer.parseInt(idLinea));
	                pst.execute();
	                return true;
	            }
                else
                {
                    pst = db.prepareStatement(sqlInsert);
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    				String data = sdf.format(new Date(System.currentTimeMillis()));
    				String note = "Valore precedente quadratura: "+mqOLD+"- MQ attuali: "+mqNEW+"- Modifica effettuata in data "+data+" da utente "+idUtente;
                    int i = 0;
                    pst.setInt(++i, Integer.parseInt(idLinea));
                    pst.setInt(++i, Integer.parseInt(mqNEW));
                    pst.execute();
                    return true;
                }

        } catch (Exception e) {

                return false;
        } finally {
        	DbUtil.close(rs, pst, db);
        }

}

	public boolean bloccaSbloccaCanile(String idLinea, String operazione, int idUtente, String notes) {

		java.util.Date date= new java.util.Date();
		Timestamp ts =  new Timestamp(date.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String data = sdf.format(date);
		
		String note = "Eseguita operazione: "+operazione+"- Modifica effettuata in data "+data+" da utente "+idUtente;
		
        String sql = "update opu_relazione_stabilimento_linee_produttive set note_internal_use_only = concat_ws (?, note_internal_use_only, ?),  data_fine = ? where id = ?;";
        Connection db = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
        	db = DbUtil.getConnection();
                 pst = db.prepareStatement(sql);
                
              
                int i = 0;
                pst.setString(++i, ";");
                pst.setString(++i, note);
                if (operazione.equals("BLOCCO"))
                	pst.setTimestamp(++i, ts);
                else
                	pst.setTimestamp(++i, null);
                
                pst.setInt(++i, Integer.parseInt(idLinea));
                pst.execute();
                
                String sqlOperatore = "select id_operatore from opu_stabilimento where id in (select id_stabilimento from opu_relazione_stabilimento_linee_produttive where id = ?)";
                PreparedStatement pstOperatore = null;
                pstOperatore = db.prepareStatement(sqlOperatore);
                      
                pstOperatore.setInt(1, Integer.parseInt(idLinea));
                ResultSet rsOperatore = pstOperatore.executeQuery();
                int idOperatore = -1;
                if(rsOperatore.next())
                {
                	idOperatore = rsOperatore.getInt("id_operatore");
                }
                
                if(idOperatore>0)
                {
                	String sqlTabMaterializzata = "select * from public_functions.update_opu_materializato(?);";
                	PreparedStatement pstTabMaterializzata = null;
                	pstTabMaterializzata = db.prepareStatement(sqlTabMaterializzata);
                      
                	pstTabMaterializzata.setInt(1, idOperatore);
                	pstTabMaterializzata.executeQuery();
                }
                
                
                String sqlStorico = "insert into canili_operazioni(data, id_utente, id_rel_stab_lp, operazione, note) values(?, ?, ?, ?, ?);";
                PreparedStatement pstStorico = null;
                pstStorico = db.prepareStatement(sqlStorico);
                      
                int j = 0;
                pstStorico.setTimestamp(++j, ts);
                pstStorico.setInt(++j, idUtente);
                pstStorico.setInt(++j, Integer.parseInt(idLinea));
                pstStorico.setString(++j, operazione);
                pstStorico.setString(++j, notes);
                pstStorico.execute();  
                
                return true;

        } catch (Exception e) {

                return false;
        } finally {
        	DbUtil.close(rs, pst, db);
        }

}
	
	
	public String recuperaInfoSoggetto(String idSoggetto) {

        String sql = "select * from get_info_operatori_collegati_soggetto_fisico(?);";
        Connection db = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String esito = "";
        try {
        	db = DbUtil.getConnection();
                 pst = db.prepareStatement(sql);
                
              
                int i = 0;
                pst.setInt(++i, Integer.parseInt(idSoggetto));
                rs =  pst.executeQuery();
              
                while (rs.next()){
                	String ragione = rs.getString("ragione_sociale");
                	String cf = rs.getString("codice_fiscale");
                	String tipologia = rs.getString("tipologia");
                	int num = rs.getInt("num_animali");
                	esito+="\n "+ragione+" ("+cf+") - "+tipologia+" ("+num+" animali);";
                }
                
                return esito;

        } catch (Exception e) {

                return esito;
        } finally {
        	DbUtil.close(rs, pst, db);
        }

}
	
}
