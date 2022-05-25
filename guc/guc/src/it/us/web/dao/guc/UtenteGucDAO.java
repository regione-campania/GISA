/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.guc;

import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Canile;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.Importatori;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Struttura;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.ClinicaDAO;
import it.us.web.dao.GenericDAO;
import it.us.web.util.guc.GUCEndpoint;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;









import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtenteGucDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( UtenteGucDAO.class );




	public static ArrayList<Utente> listaUtenti(Connection db,String endpoint,int ruolo,boolean utentiAttivi)
	{
		String query = " Select u.*, " +
				"gr.ruolo_string as gisa_role, gr.id as gisa_id, " +
				"gr_ext.ruolo_string as gisa_ext_role, gr_ext.id as gisa_ext_id, " +
				"br.ruolo_string as bdu_role,  br.id as bdu_id, " +
				"vr.ruolo_string as vam_role, vr.id as vam_id, " +
				"ir.ruolo_string as importatori_role, ir.id as importatori_id, " +
				"dr.ruolo_string as digemon_role, dr.id as digemon_id, " +
				"a.id as id_asl,a.nome as descrizione_asl from guc_utenti u join asl a on u.asl_id = a.id " +
				"left join listaruoli gr on (gr.id_utente = u.id and gr.endpoint ilike 'gisa') " +
				"left join listaruoli gr_ext on (gr_ext.id_utente = u.id and gr_ext.endpoint ilike 'gisa_ext') " +
				"left join listaruoli br on (br.id_utente = u.id and br.endpoint ilike 'bdu') " +
				"left join listaruoli dr on (dr.id_utente = u.id and dr.endpoint ilike 'digemon') " +
				"left join listaruoli vr on (vr.id_utente = u.id and vr.endpoint ilike 'vam') " +
				"left join listaruoli ir on (ir.id_utente = u.id and ir.endpoint ilike 'importatori') " +
				"where u.enabled = ?"; 

		ArrayList<Utente> listautenti = new ArrayList<Utente>();		
		//		String query = "Select u.*,a.id as id_asl,a.nome as descrizione_asl from guc_utenti u join asl a on u.asl_id = a.id where u.enabled = ? ";
		//se e'' selezionato un endpoint o un ruolo

		if(!endpoint.equals("") || ruolo > -1 ){
			query = query + " and u.id in ( select r.id_utente from guc_ruoli r where 1 = 1 " 
					+ (!endpoint.equals("") ? " and r.endpoint = ? and r.ruolo_Integer > 0 " : "") 
					+ (ruolo > -1 ? " and r.ruolo_Integer = ? " : "") + ")";
		}

		query = query + " order by u.modified desc ";
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		try
		{
			int i = 0 ;
			stat = db.prepareStatement( query ) ;
			stat.setBoolean(++i, utentiAttivi) ;
			if(!endpoint.equals("")){
				stat.setString(++i, endpoint) ;
			}
			if(ruolo > -1){
				stat.setInt(++i, ruolo);
			}			
			rs = stat.executeQuery();
			System.out.println(stat.toString());
			Set<Ruolo> ru = new HashSet<Ruolo>();
			String descr_role = "";
			int id_role;
			while( rs.next() )
			{
				Utente utente = createBeanUtente(rs);
				//utente.setDataScadenza(db);
				for(GUCEndpoint e : GUCEndpoint.values()){
					Ruolo r = new Ruolo();
					r.setEndpoint(e.toString());
					r.setRuoloInteger(rs.getInt(e.toString().toLowerCase()+"_id"));
					descr_role = (rs.getString(e.toString().toLowerCase()+"_role"));
					if (descr_role != null && !descr_role.equals(""))
						r.setRuoloString(descr_role.toUpperCase());
					else
						r.setRuoloString("N.D");
					r.setUtente(utente);
					ru.add(r);
				}
				utente.setRuoli(ru);
				ru.clear();
				//			utente.setRuoli(RuoloDAO.getRuoliByIdUtente(utente, db));
				listautenti.add(utente);


			}
		}		
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}
		return listautenti ;

	}


	public static ArrayList<Utente> listaUtentibyUsername(Connection db,String username)
	{
		ArrayList<Utente> listautenti = new ArrayList<Utente>();
		String query = "Select u.*,a.id as id_asl,a.nome as descrizione_asl from guc_utenti u join asl a on u.asl_id = a.id where username ilike ? ";
		//se e'' selezionato un endpoint o un ruolo
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		try
		{
			int i = 0 ;
			stat = db.prepareStatement( query ) ;
			stat.setString(++i, username) ;
			rs = stat.executeQuery();
			while( rs.next() )
			{
				Utente utente = createBeanUtente(rs) ;
				utente.setDataScadenza(db);
				utente.setRuoli(RuoloDAO.getRuoliByIdUtente(utente, db));
				listautenti.add(utente);


			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}
		return listautenti ;

	}
	
	
	public static ArrayList<Utente> listaUtentibyCf(Connection db,String cf,String nome,String cognome)
	{
		ArrayList<Utente> listautenti = new ArrayList<Utente>();
		String query = "Select u.*,a.id as id_asl,a.nome as descrizione_asl from guc_utenti  u join asl a on u.asl_id = a.id where enabled and codice_fiscale ilike ? and upper(trim(u.nome)) != upper(trim(?)) and upper(trim(cognome)) != upper(trim(?)) ";
		//se e'' selezionato un endpoint o un ruolo
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		try
		{
			int i = 0 ;
			stat = db.prepareStatement( query ) ;
			stat.setString(++i, cf) ;
			stat.setString(++i, nome) ;
			stat.setString(++i, cognome) ;
			rs = stat.executeQuery();
			while( rs.next() )
			{
				Utente utente = createBeanUtente(rs) ;
				utente.setDataScadenza(db);
				utente.setRuoli(RuoloDAO.getRuoliByIdUtente(utente, db));
				listautenti.add(utente);


			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}
		return listautenti ;

	}
	
	public static int checkEsistenzaComuneGestore(Connection db,int comuneGestore, int gestore) throws SQLException
	{
		String query = "Select count(*) as count from guc_utenti u  where gestore_acque = ? and comune_gestore_acque = ? ";
		//se e'' selezionato un endpoint o un ruolo
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		int i = 0 ;
		stat = db.prepareStatement( query ) ;
		stat.setInt(++i, gestore) ;
		stat.setInt(++i, comuneGestore) ;
		rs = stat.executeQuery();
		if( rs.next() )
		{
			return rs.getInt("count");
		}
		return 0;
	}

	public static ArrayList<Utente> listaUtentibyId(Connection db,int id)
	{
		ArrayList<Utente> listautenti = new ArrayList<Utente>();
		String query = "Select u.*,a.id as id_asl,a.nome as descrizione_asl, ut.username as enteredby_username from guc_utenti u join asl a on u.asl_id = a.id left join utenti ut on ut.id = u.enteredby where u.id=?  ";
		//se e'' selezionato un endpoint o un ruolo
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		try
		{
			int i = 0 ;
			stat = db.prepareStatement( query ) ;
			stat.setInt(++i, id) ;
			rs = stat.executeQuery();
			while( rs.next() )
			{
				Utente utente = createBeanUtente(rs) ;
				utente.setDataScadenza(db);
				utente.setRuoli(RuoloDAO.getRuoliByIdUtente(utente, db));

				utente.setClinicheVam(ClinicaDAO.getClinicheByRuolo(db, utente.getId()));
				utente.setStruttureGisa(ClinicaDAO.getStruttureByRuolo(db, utente.getId()));
				utente.setCaniliBdu(ClinicaDAO.getCaniliByRuolo(db, utente.getId()));
				utente.setImportatori(ClinicaDAO.getImportatoriByRuolo(db, utente.getId()));
				utente.setExtOption(ClinicaDAO.getExeOpt(db, utente.getId()));

				listautenti.add(utente);		
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}
		return listautenti ;

	}
	
	
	public static ArrayList<Utente> listaUtentiAnagraficaById(Connection db,int id)
	{
		ArrayList<Utente> listautenti = new ArrayList<Utente>();
		String query = "Select u.*,a.id as id_asl,a.nome as descrizione_asl from guc_utenti u join asl a on u.asl_id = a.id where u.id=?  ";
		//se e'' selezionato un endpoint o un ruolo
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		try
		{
			int i = 0 ;
			stat = db.prepareStatement( query ) ;
			stat.setInt(++i, id) ;
			rs = stat.executeQuery();
			while( rs.next() )
			{
				Utente utente = createBeanUtenteAnagrafica(rs) ;
				utente.setRuoli(RuoloDAO.getRuoliByIdUtente(utente, db));
//
//				utente.setClinicheVam(ClinicaDAO.getClinicheByRuolo(db, utente.getId()));
//				utente.setStruttureGisa(ClinicaDAO.getStruttureByRuolo(db, utente.getId()));
//				utente.setCaniliBdu(ClinicaDAO.getCaniliByRuolo(db, utente.getId()));
//				utente.setImportatori(ClinicaDAO.getImportatoriByRuolo(db, utente.getId()));
//				utente.setExtOption(ClinicaDAO.getExeOpt(db, utente.getId()));

				listautenti.add(utente);		
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}
		return listautenti ;

	}


	public static void diasble(Connection db,Utente u)
	{
		String insert = "UPDATE guc_utenti_ set data_scadenza = ?,cessato=?,modified=current_timestamp where id =?";
		try
		{
			PreparedStatement pst =  db.prepareStatement(insert);
			pst.setDate(1, new Date( u.getDataScadenza().getTime()));
			pst.setBoolean(2, u.isCessato());
			pst.setInt(3, u.getId());
			pst.execute();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void rollback(Connection db,Utente u)
	{
		
		/**
		 * DEVO RECUPERARE L'ID DELL'UTENTE NON ATTUALIZZATO PER POTERLO CANCELLARE.
		 * SE VADO NELLA VISTA NON LO POSSO RECUPERARE 
		 */
		String sql = "select id from guc_utenti_ where id_utente_guc_old=?";
		
		int idUtente=-1 ;
		String rollback = "delete from guc_ruoli where id_utente = ?;"+
"delete from log_ruoli_utenti where id_log_utente  in ( select id from log_utenti where id_utente =?);"+
"delete from log_utenti where id_utente =?;delete from guc_utenti_ where id =? ;update guc_utenti_ set data_scadenza = null where id = ?;";
		try
		{
			PreparedStatement pst =  db.prepareStatement(sql);
			pst.setInt(1,u.getId());
			ResultSet rs= pst.executeQuery();
			if (rs.next())
			{
				idUtente = rs.getInt(1);
			}
			
			 	pst =  db.prepareStatement(rollback);
				pst.setInt(1,idUtente);
				pst.setInt(2,idUtente);
				pst.setInt(3,idUtente);
				pst.setInt(4,idUtente);
				pst.setInt(5,u.getId());
				pst.execute();
				

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}


	public static void insert(Connection db,Utente u, EndPointConnector epc) throws SQLException
	{
	
		//Controllo ruoli
		
		int idRuoloGisa = -1;
		int idRuoloGisaExt = -1;
		int idRuoloBdu = -1;
		int idRuoloVam = -1;
		int idRuoloImportatori = -1;
		int idRuoloDigemon = -1;

		
		String descrizioneRuoloGisa ="";
		String descrizioneRuoloGisaExt = "";
		String descrizioneRuoloBdu ="";
		String descrizioneRuoloVam ="";
		String descrizioneRuoloImportatori = "";
		String descrizioneRuoloDigemon = "";

		
		for (Ruolo r : u.getRuoli())
		{
			if (r.getEndpoint().equals("Gisa")){
				idRuoloGisa = r.getRuoloInteger();
				descrizioneRuoloGisa = r.getRuoloString();
			}
			else if (r.getEndpoint().equals("Gisa_ext")){
				idRuoloGisaExt = r.getRuoloInteger();
				descrizioneRuoloGisaExt = r.getRuoloString();
			}
			else if (r.getEndpoint().equals("bdu")){
				idRuoloBdu = r.getRuoloInteger();
				descrizioneRuoloBdu = r.getRuoloString();
			}
			else if (r.getEndpoint().equals("Vam")){
				idRuoloVam = r.getRuoloInteger();
				descrizioneRuoloVam = r.getRuoloString();
			}
			else if (r.getEndpoint().equals("Importatori")){
				idRuoloImportatori = r.getRuoloInteger();
				descrizioneRuoloImportatori = r.getRuoloString();
			}
			else if (r.getEndpoint().equals("Digemon")){
				idRuoloDigemon = r.getRuoloInteger();
				descrizioneRuoloDigemon = r.getRuoloString();
			}
		}

		String insert = epc.getSql();
		int i = 0;
		try
		{
			java.sql.Timestamp exp = null ;
			if(u.getExpires()!=null)
			{
				exp = new java.sql.Timestamp(u.getExpires().getTime());
			}
			PreparedStatement pst =  db.prepareStatement(insert);
			pst.setString(++i, u.getCodiceFiscale());
			pst.setString(++i, u.getCognome());
			pst.setString(++i, u.getEmail());
			pst.setBoolean(++i, u.isEnabled());
			pst.setInt(++i, u.getEnteredBy());
			pst.setTimestamp(++i, exp);
			pst.setInt(++i, u.getModifiedBy());
			pst.setString(++i, u.getNote());
			pst.setString(++i, u.getNome());
			pst.setString(++i, u.getPassword());
			pst.setString(++i, u.getUsername());
			if(u.getAsl()==null)
				pst.setInt(++i, -1);
			else
				pst.setInt(++i, u.getAsl().getId());
			pst.setString(++i, u.getPasswordEncrypted());
			pst.setInt(++i, u.getCanileId());
			pst.setString(++i, u.getCanileDescription());
			pst.setString(++i, u.getLuogo());
			pst.setString(++i, u.getNumAutorizzazione());
			pst.setInt(++i, u.getId_importatore());
			pst.setInt(++i, u.getCanilebduId());
			pst.setString(++i, u.getCanilebduDescription());
			pst.setString(++i, u.getImportatoriDescription());

			//SOLO PER UNINA E LP
			pst.setInt(++i, u.getId_provincia_iscrizione_albo_vet_privato());  //-1 no provincia
			pst.setString(++i, u.getNr_iscrione_albo_vet_privato());
			pst.setInt(++i, u.getId());  //-1 no provincia
			pst.setString(++i, u.getIpSuap());  //-1 no provincia
			pst.setString(++i, u.getComuneSuap());  //-1 no provincia
			pst.setString(++i, u.getPecSuap());  //-1 no provincia
			pst.setString(++i, u.getCallbackSuap());  //-1 no provincia
			pst.setString(++i, u.getSharedKeySuap());  //-1 no provincia
			pst.setString(++i, u.getCallbackSuap_ko());  //-1 no provincia
			pst.setString(++i, u.getNumRegStab());
			pst.setInt(++i, u.getLivelloAccreditamentoSuap());
			pst.setString(++i, u.getDescrizioneLivelloAccreditamento());
			pst.setString(++i, u.getTelefono());

			//Ruoli
			pst.setInt(++i, idRuoloGisa);
			pst.setString(++i, descrizioneRuoloGisa);
			pst.setInt(++i, idRuoloGisaExt);
			pst.setString(++i, descrizioneRuoloGisaExt);
			pst.setInt(++i, idRuoloBdu);
			pst.setString(++i, descrizioneRuoloBdu);
			pst.setInt(++i, idRuoloVam);
			pst.setString(++i, descrizioneRuoloVam);
			pst.setInt(++i, idRuoloImportatori);
			pst.setString(++i, descrizioneRuoloImportatori);
			pst.setInt(++i, idRuoloDigemon);
			pst.setString(++i, descrizioneRuoloDigemon);
			pst.setString(++i, u.getLuogoVam());
			pst.setInt(++i, u.getId_provincia_iscrizione_albo_vet_privato_vam());
			pst.setString(++i, u.getNr_iscrione_albo_vet_privato_vam());
			pst.setInt(++i, u.getGestore());
			pst.setInt(++i, u.getComuneGestore());
			pst.setString(++i, u.getPiva());
			
			pst.setString(++i, u.getTipoAttivitaApicoltore());
			pst.setInt(++i, u.getComuneApicoltore());
			pst.setString(++i, u.getIndirizzoApicoltore());
			pst.setString(++i, u.getCapIndirizzoApicoltore());
			pst.setInt(++i, u.getComuneTrasportatore());
			pst.setString(++i, u.getIndirizzoTrasportatore());
			pst.setString(++i, u.getCapIndirizzoTrasportatore());
			
			System.out.println("############################QUERY INSERIMENTO IN GUC  "+pst.toString());
			int idUtente = -1;
			
			//La dbi restituisce esito_idutente es      OK;;123;;spid_usr_0001     KO-Username esistente;;100;;
			
			ResultSet rs = pst.executeQuery();
			String esito_msg ="";
			String esito_user_id = "";
			String esito_username = "";
			String esito_password = "";

			if (rs.next()){
				String res = rs.getString(1);
				String res2[] = res.split(";;");
				esito_msg = res2[0];
				esito_user_id = res2[1];
				esito_username = res2[2];
				esito_password = res2[3];
				
			}
			
			if (esito_msg.equalsIgnoreCase("OK"))
				idUtente = Integer.parseInt(esito_user_id);
			
			if (idUtente>0){
				u.setId(idUtente);
				u.setUsername(esito_username);
				u.setPassword(esito_password);
//				for (Ruolo r : u.getRuoli())
//				{
//					r.setUtente(u);
//					RuoloDAO.insert(db, r);
//				}
	
	
				PreparedStatement stat = db.prepareStatement( "INSERT INTO guc_cliniche_vam (id_utente,id_clinica,descrizione_clinica) VALUES (?,?,?) " );
				for(Clinica c : u.getClinicheVam()){
					stat.setInt(1, idUtente) ;
					stat.setInt(2, c.getIdClinica()) ;
					stat.setString(3, c.getDescrizioneClinica()) ;
					stat.executeUpdate();
				}
	
	
				stat = db.prepareStatement( "INSERT INTO guc_canili_bdu (id_utente,id_canile,descrizione_canile) VALUES (?,?,?) " );
				for(Canile c : u.getCaniliBdu()){
					stat.setInt(1, idUtente) ;
					stat.setInt(2, c.getIdCanile()) ;
					stat.setString(3, c.getDescrizioneCanile()) ;
					stat.executeUpdate();
				}
	
	
				/*	GISA NON e'' ANCORA SUPPORTATO
			  	stat	= db.prepareStatement( "INSERT INTO guc_strutture_gisa (id_utente,id_struttura,descrizione_struttura,id_padre,n_livello) VALUES (?,?,?,?,?) " );
				for(Struttura s : u.getStruttureGisa()){
					stat.setInt(1, id) ;
					stat.setInt(2, s.getIdStruttura()) ;
					stat.setString(3, s.getDescrizioneStruttura()) ;
					stat.setInt(4,s.getIdPadre());
					stat.setInt(5,s.getN_livello());
					stat.executeUpdate();
				} */
	
				stat = db.prepareStatement( "INSERT INTO guc_importatori (id_utente,id_importatore,descrizione_importatore,partita_iva) VALUES (?,?,?,?) " );
				for(Importatori imp  : u.getImportatori()){
					stat.setInt(1, idUtente) ;
					stat.setInt(2, imp.getIdImportatore()) ;
					stat.setString(3, imp.getRagioneSociale());
					stat.setString(4, imp.getPartitaIva());
					stat.executeUpdate();
				}
	
				stat = db.prepareStatement("INSERT INTO extended_option (key,val,user_id,endpoint) VALUES (?,?,?,?)" );
				Iterator it = (Iterator) u.getExtOption().entrySet().iterator();
				while (it.hasNext()){
					Entry<String, HashMap<String, String>> mapEntry = (Entry<String, HashMap<String, String>>) it.next();
					Iterator it2 = mapEntry.getValue().entrySet().iterator();
					while (it2.hasNext()){
						Map.Entry e = (Map.Entry)it2.next();
						String k = (String)e.getKey();
						String v = (String)e.getValue();
						stat.setString(1, k);
						stat.setString(2, v);
						stat.setInt(3, idUtente);
						stat.setString(4, mapEntry.getKey());
						stat.executeUpdate();
					}
				}
	
			}
			else {
				System.out.println("Si e verificato un errore in inserimento utente guc: "+esito_msg);
	}	
		}
			
		catch(Exception e)
		{
			
			System.out.println("si e verificato un errore grave in inserimento utente guc");
			e.printStackTrace();
		}

	}

	public static void updateAnagraficaUtente(Connection db,Utente u)
	{



		String insert = "update guc_utenti_ set CODICE_FISCALE =? ,COGNOME=?,EMAIL =?  " +
				",modified=current_date,modifiedby=?,note=? ,nome=? ,password=? ,username=?,  password_encrypted = ?,id_provincia_iscrizione_albo_vet_privato = ?," 
				+ " nr_iscrione_albo_vet_privato=?,luogo=? ,num_autorizzazione=?, nr_iscrione_albo_vet_privato_vam=?,luogo_vam=? ,id_provincia_iscrizione_albo_vet_privato_vam=?, gestore_acque=?, comune_gestore_acque = ?, piva = ? " +
				"  where id = ?";
		try
		{

			int i =0; 
			PreparedStatement pst =  db.prepareStatement(insert);
			pst.setString(++i, u.getCodiceFiscale());
			pst.setString(++i, u.getCognome());
			pst.setString(++i, u.getEmail());

			pst.setInt(++i, u.getModifiedBy());
			pst.setString(++i, u.getNote());
			pst.setString(++i, u.getNome());
			pst.setString(++i, u.getPassword());
			pst.setString(++i, u.getUsername());
			pst.setString(++i, u.getPasswordEncrypted());
			pst.setInt(++i, u.getId_provincia_iscrizione_albo_vet_privato());
			pst.setString(++i, u.getNr_iscrione_albo_vet_privato());
			pst.setString(++i, u.getLuogo());
			pst.setString(++i, u.getNumAutorizzazione());

			pst.setString(++i, u.getNr_iscrione_albo_vet_privato_vam());
			pst.setString(++i, u.getLuogoVam());
			pst.setInt(++i, u.getId_provincia_iscrizione_albo_vet_privato_vam());
			pst.setInt(++i, u.getGestore());
			pst.setInt(++i, u.getComuneGestore());
			pst.setString(++i, u.getPiva());
			
			pst.setInt(++i, u.getId());
			
			
			pst.executeUpdate();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void update(Connection db,Utente u)
	{

		String insert = "update guc_utenti_ set CLINICA_DESCRIPTION =? ,CLINICA_ID =? ,CODICE_FISCALE =? ,COGNOME=?,EMAIL =? ,ENABLED =? ,expires=? " +
				",modified=current_date,modifiedby=?,note=? ,nome=? ,password=? ,username=?, asl_id = ?, password_encrypted = ?, canile_id = ?, canile_description = ?," +
				"luogo=? ,num_autorizzazione=?, id_importatore = ?,canilebdu_id = ? , canilebdu_description = ?,importatori_description = ?," +
				"id_provincia_iscrizione_albo_vet_privato = ?, nr_iscrione_albo_vet_privato =?, luogo_vam = ?, id_provincia_iscrizione_albo_vet_privato_vam = ?, nr_iscrione_albo_vet_privato_vam =?, gestore_acque=?, comune_gestore_acque = ?, piva = ?  where id = ?";
		try
		{
			java.sql.Timestamp exp = null ;
			if(u.getExpires()!=null)
			{
				exp = new java.sql.Timestamp(u.getExpires().getTime());
			}
			PreparedStatement pst =  db.prepareStatement(insert);
			pst.setString(1, u.getClinicaDescription());
			pst.setInt(2, u.getClinicaId());
			pst.setString(3, u.getCodiceFiscale());
			pst.setString(4, u.getCognome());
			pst.setString(5, u.getEmail());
			pst.setBoolean(6, u.isEnabled());
			pst.setTimestamp(7, exp);
			pst.setInt(8, u.getModifiedBy());
			pst.setString(9, u.getNote());
			pst.setString(10, u.getNome());
			pst.setString(11, u.getPassword());
			pst.setString(12, u.getUsername());
			pst.setInt(13, u.getAsl().getId());
			pst.setString(14, u.getPasswordEncrypted());
			pst.setInt(15, u.getCanileId());
			pst.setString(16, u.getCanileDescription());
			pst.setString(17, u.getLuogo());
			pst.setString(18, u.getNumAutorizzazione());
			pst.setInt(19, u.getId_importatore());
			pst.setInt(20, u.getCanilebduId());
			pst.setString(21, u.getCanilebduDescription());
			pst.setString(22, u.getImportatoriDescription());
			pst.setInt(23, u.getId_provincia_iscrizione_albo_vet_privato());
			pst.setString(24, u.getNr_iscrione_albo_vet_privato());	
			pst.setString(25, u.getLuogoVam());
			pst.setInt(26, u.getId_provincia_iscrizione_albo_vet_privato_vam());
			pst.setString(27, u.getNr_iscrione_albo_vet_privato_vam());	
			pst.setInt(28, u.getGestore());
			pst.setInt(29, u.getComuneGestore());
			pst.setString(30, u.getPiva());
			pst.setInt(31, u.getId());
			
			System.out.println("query update guc: "+pst.toString()+ " enabled: "+u.isEnabled());
			pst.executeUpdate();

			PreparedStatement stat	= db.prepareStatement( "delete from guc_cliniche_vam where id_utente = ?" );
			stat.setInt(1, u.getId());
			stat.execute() ;
			stat	= db.prepareStatement( "INSERT INTO guc_cliniche_vam (id_utente,id_clinica,descrizione_clinica) VALUES (?,?,?) " );
			for(Clinica c : u.getClinicheVam()){
				stat.setInt(1, u.getId()) ;
				stat.setInt(2, c.getIdClinica()) ;
				stat.setString(3, c.getDescrizioneClinica()) ;
				stat.execute();
			}

			stat = db.prepareStatement( "delete from extended_option where user_id = ?" );
			stat.setInt(1, u.getId());
			stat.execute() ;
			stat	= db.prepareStatement( "INSERT INTO guc_canili_bdu (id_utente,id_canile,descrizione_canile) VALUES (?,?,?) " );
			for(Canile c : u.getCaniliBdu()){
				stat.setInt(1,  u.getId()) ;
				stat.setInt(2, c.getIdCanile()) ;
				stat.setString(3, c.getDescrizioneCanile()) ;
				stat.executeUpdate();
			}

			/*	GISA NON e'' ANCORA SUPPORTATO
			stat = db.prepareStatement( "delete from guc_strutture_gisa where id_utente = ?" );
			stat.setInt(1, u.getId());
			stat.execute() ;
			stat	= db.prepareStatement( "INSERT INTO guc_strutture_gisa (id_utente,id_struttura,descrizione_struttura,id_padre,n_livello) VALUES (?,?,?,?,?)" );
			for(Struttura s : u.getStruttureGisa()){
				stat.setInt(1,  u.getId()) ;
				stat.setInt(2, s.getIdStruttura()) ;
				stat.setString(3, s.getDescrizioneStruttura()) ;
				stat.setInt(4,s.getIdPadre());
				stat.setInt(5, s.getN_livello());
				stat.executeUpdate();
			} */

			stat = db.prepareStatement( "delete from guc_importatori where id_utente = ?" );
			stat.setInt(1, u.getId());
			stat.execute() ;
			stat	= db.prepareStatement( "INSERT INTO guc_importatori (id_utente,id_importatore,descrizione_importatore,partita_iva) VALUES (?,?,?,?) " );
			for(Importatori imp  : u.getImportatori()){
				stat.setInt(1, u.getId()) ;
				stat.setInt(2, imp.getIdImportatore()) ;
				stat.setString(3, imp.getRagioneSociale()) ;
				stat.setString(4, imp.getPartitaIva());
				stat.executeUpdate();
			}

			stat = db.prepareStatement( "delete from guc_importatori where id_utente = ?" );
			stat.setInt(1, u.getId());
			stat.execute() ;
			stat = db.prepareStatement("INSERT INTO extended_option (key,val,user_id,endpoint) VALUES (?,?,?,?)" );
			Iterator it = (Iterator) u.getExtOption().entrySet().iterator();
			while (it.hasNext()){
				Entry<String, HashMap<String, String>> mapEntry = (Entry<String, HashMap<String, String>>) it.next();
				Iterator it2 = mapEntry.getValue().entrySet().iterator();
				while (it2.hasNext()){
					Map.Entry e = (Map.Entry)it2.next();
					String k = (String)e.getKey();
					String v = (String)e.getValue();
					stat.setString(1, k);
					stat.setString(2, v);
					stat.setInt(3, u.getId());
					stat.setString(4, mapEntry.getKey());
					stat.executeUpdate();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public static Utente createBeanUtente(ResultSet rs)
	{
		Utente utente = new Utente();

		try 
		{
			
			
			
			utente.setLivelloAccreditamentoSuap(rs.getInt("suap_livello_accreditamento"));
			utente.setTelefono(rs.getString("telefono"));
			utente.setId( rs.getInt("ID") );
			utente.setClinicaDescription(rs.getString("CLINICA_DESCRIPTION"));
			utente.setClinicaId(rs.getInt("CLINICA_ID"));
			utente.setCodiceFiscale( rs.getString("CODICE_FISCALE") != null ? rs.getString("CODICE_FISCALE") : "" );
			utente.setCognome( rs.getString("COGNOME") != null ? rs.getString("COGNOME") : "" );
			utente.setEmail( rs.getString("EMAIL") != null ? rs.getString("EMAIL") : "" );
			utente.setEnabled(rs.getBoolean("ENABLED"));
			utente.setEntered(rs.getTimestamp("entered"));
			utente.setEnteredBy(rs.getInt("enteredby"));
			try {utente.setEnteredByUsername(rs.getString("enteredby_username"));} catch (Exception e) {}
			utente.setExpires(rs.getDate("expires"));
			utente.setModified(rs.getTimestamp("modified"));
			utente.setId_importatore(rs.getInt("id_importatore"));
			utente.setNote(rs.getString("note") != null ? rs.getString("note") : "");
			utente.setNome( rs.getString("NOME") != null ? rs.getString("NOME") : "" );
			utente.setPassword(rs.getString("password"));
			utente.setPasswordEncrypted(rs.getString("password_encrypted") != null ? rs.getString("password_encrypted") : "");
			utente.setUsername( rs.getString("USERNAME") );
			utente.setOldUsername( utente.getUsername() );
			utente.setCanileId(rs.getInt("canile_id"));
			utente.setImportatoriDescription(rs.getString("importatori_Description"));
			utente.setCanilebduId(rs.getInt("canilebdu_id"));
			utente.setCanilebduDescription(rs.getString("canilebdu_description"));
			utente.setNumRegStab(rs.getString("num_registrazione_stab"));
			utente.setCanileDescription(rs.getString("canile_description"));
			utente.setLuogo(rs.getString("luogo"));
			utente.setLuogoVam(rs.getString("luogo_vam"));
			utente.setGestore(rs.getInt("gestore_acque"));
			utente.setComuneGestore(rs.getInt("comune_gestore_acque"));
			utente.setNumAutorizzazione(rs.getString("num_autorizzazione"));
			utente.setPiva(rs.getString("piva"));
			Asl asl = new Asl();
			asl.setId(rs.getInt("id_asl"));
			asl.setNome(rs.getString("descrizione_asl"));
			utente.setAsl(asl);
			utente.setDataScadenza(rs.getDate("data_scadenza"));
			utente.setId_provincia_iscrizione_albo_vet_privato(rs.getInt("id_provincia_iscrizione_albo_vet_privato"));
			utente.setId_provincia_iscrizione_albo_vet_privato_vam(rs.getInt("id_provincia_iscrizione_albo_vet_privato_vam"));
			utente.setNr_iscrione_albo_vet_privato(rs.getString("nr_iscrione_albo_vet_privato"));
			utente.setNr_iscrione_albo_vet_privato_vam(rs.getString("nr_iscrione_albo_vet_privato_vam"));
			
			
			utente.setIpSuap(rs.getString("suap_ip_address"));
			utente.setCallbackSuap(rs.getString("suap_callback"));
			utente.setCallbackSuap_ko(rs.getString("suap_callback_ko"));

			utente.setSharedKeySuap(rs.getString("suap_shared_key"));
			utente.setComuneSuap(rs.getString("suap_istat_comune"));
			utente.setPecSuap(rs.getString("suap_pec"));
			
			utente.setTipoAttivitaApicoltore(rs.getString("tipo_attivita_apicoltore"));
			utente.setComuneApicoltore(rs.getInt("comune_apicoltore"));
			utente.setIndirizzoApicoltore(rs.getString("indirizzo_apicoltore"));
			utente.setCapIndirizzoApicoltore(rs.getString("cap_apicoltore"));
			utente.setComuneTrasportatore(rs.getInt("comune_trasportatore"));
			utente.setIndirizzoTrasportatore(rs.getString("indirizzo_trasportatore"));
			utente.setCapIndirizzoTrasportatore(rs.getString("cap_trasportatore"));

			

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return utente;
	}
	
	
	public static Utente createBeanUtenteAnagrafica(ResultSet rs)
	{
		Utente utente = new Utente();

		try 
		{
			utente.setId( rs.getInt("ID") );
			utente.setCodiceFiscale( rs.getString("CODICE_FISCALE") != null ? rs.getString("CODICE_FISCALE") : "" );
			utente.setCognome( rs.getString("COGNOME") != null ? rs.getString("COGNOME") : "" );
			utente.setEmail( rs.getString("EMAIL") != null ? rs.getString("EMAIL") : "" );
			utente.setEnabled(rs.getBoolean("ENABLED"));
			utente.setEntered(rs.getTimestamp("entered"));
			utente.setExpires(rs.getDate("expires"));
			utente.setModified(rs.getTimestamp("modified"));
			utente.setNome( rs.getString("NOME") != null ? rs.getString("NOME") : "" );
			utente.setPassword(rs.getString("password"));
			utente.setPasswordEncrypted(rs.getString("password_encrypted") != null ? rs.getString("password_encrypted") : "");
			utente.setUsername( rs.getString("USERNAME") );
			utente.setOldUsername( utente.getUsername() );
			

			Asl asl = new Asl();
			asl.setId(rs.getInt("id_asl"));
			asl.setNome(rs.getString("descrizione_asl"));
			utente.setAsl(asl);
			utente.setDataScadenza(rs.getDate("data_scadenza"));
			


		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return utente;
	}

	public static ArrayList<Integer> getIdUtentibyEndpoint(Connection db,String endpoint)
	{
		ArrayList<Integer> listaIdUtenti = new ArrayList<Integer>();
		String query = "select id_utente from guc_ruoli where endpoint ilike ? and ruolo_integer > 0";
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;

		try{
			stat = db.prepareStatement( query ) ;
			stat.setString(1, endpoint) ;
			rs = stat.executeQuery();
			while( rs.next() ){
				listaIdUtenti.add(rs.getInt("id_utente"));
			}
		}
		catch (Exception e){
			logger.error( "", e );
		}
		finally{
			close( rs, stat );
		}

		return listaIdUtenti ;

	}

}
