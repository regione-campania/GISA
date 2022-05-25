/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
//commento al 214
package org.aspcfs.modules.canipadronali.base;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.util.imports.DbUtil;
import org.aspcfs.utils.Cane;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.IndirizzoProprietario;
import org.aspcfs.utils.Proprietario;
import org.aspcfs.utils.UrlUtil;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OperazioniCaniDAO implements CaneDAO {
	private java.util.logging.Logger logger =   java.util.logging.Logger.getLogger("MainLogger");

	
	public static final int TIPOLOGIA_PROPRIETARI_CANI_PADRONALI = 255 ;
	private Connection db ;
	private static GsonBuilder gb;
	private static Gson gson;
	
	static
	{
		gb = new GsonBuilder();
		gb.setDateFormat( "yyyy-MM-dd HH:mm:ss" );
		gson = gb.create();
	}
	
	public Connection getDb() {
		return db;
	}

	public void setDb(Connection db) {
		this.db = db;
	}

	public int inserisciCane(org.aspcfs.modules.canipadronali.base.Proprietario proprietario,UserBean user) throws SQLException {
		int orgId =proprietario.getOrgId();
		boolean doCommit = false;
		try
		{
			if (doCommit = db.getAutoCommit()) {
		        db.setAutoCommit(false);
		      }
			
			if(proprietario!=null)
			{
					String inser_into_organization 			= "insert into organization (org_id,name,nome_rappresentante,cognome_rappresentante,codice_fiscale_rappresentante ,data_nascita_rappresentante,luogo_nascita_rappresentante,documento,site_id,entered,modified,modifiedby,enteredby,tipologia,tipo_proprietario) values (?,?,?,?,?,?,?,?,?,current_date,current_date,?,?,?,?)" ;
					PreparedStatement pst_prop = db.prepareStatement(inser_into_organization);
					int i = 0	;
					Date data_nascita_prop = null ;
					int id_asl_prop = proprietario.getIdAsl() ;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					
					if(proprietario.getDataNascitaAsString()!=null && ! "".equals(proprietario.getDataNascitaAsString().trim()) )
					try 
					{
							data_nascita_prop =  new Date (sdf.parse(proprietario.getDataNascitaAsString()).getTime());
					} 
					catch (ParseException e) 
					{
						logger.warning("Errore conversione data nascita proprietario");
					}
					
					if (orgId == -1 || orgId == 0)
					{
//						orgId = DatabaseUtils.getNextSeqInt(db, "organization_org_id_seq");
						
						int livello=1 ;
						
						if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA)
							livello=2;
						
						orgId = DatabaseUtils.getNextInt( db, "organization","org_id",livello);

						pst_prop.setInt(++i, orgId) ;
						pst_prop.setString(++i, proprietario.getRagioneSociale()) ;
						pst_prop.setString(++i, proprietario.getNome()) ;
						pst_prop.setString(++i, proprietario.getCognome()) ;
						pst_prop.setString(++i, proprietario.getCodiceFiscale()) ;
						pst_prop.setDate(++i, 	data_nascita_prop) ;
						pst_prop.setString(++i, proprietario.getLuogoNascita()) ;
						pst_prop.setString(++i, proprietario.getDocumentoIdentita()) ;
						pst_prop.setInt(++i, id_asl_prop) ;
						pst_prop.setInt(++i, proprietario.getEnteredBy()) ;
						pst_prop.setInt(++i, proprietario.getModifiedBy()) ;
						pst_prop.setInt(++i, TIPOLOGIA_PROPRIETARI_CANI_PADRONALI) ;
						pst_prop.setString(++i, proprietario.getTipoProprietarioDetentore()) ;
						pst_prop.execute() ;
						
						for ( IndirizzoProprietario indirizzo : proprietario.getLista_indirizzi())
						{
							indirizzo.setOrgId(orgId);
							indirizzo.insert(db,user);
						}
						
					}
					else
					{
						String update = "update organization set name = ? ,nome_rappresentante = ?," +
						"cognome_rappresentante=?,data_nascita_rappresentante=?," +
						"luogo_nascita_rappresentante=?,documento=?,modified=current_Date,modifiedby=?,codice_fiscale_rappresentante=? ," +
						"tipo_proprietario=? where org_id = ?";
						
						pst_prop = db.prepareStatement(update);
						pst_prop.setString(++i, proprietario.getRagioneSociale()) ;
						pst_prop.setString(++i, proprietario.getNome()) ;
						pst_prop.setString(++i, proprietario.getCognome()) ;
						pst_prop.setDate(++i, 	data_nascita_prop) ;
						pst_prop.setString(++i, proprietario.getLuogoNascita()) ;
						pst_prop.setString(++i, proprietario.getDocumentoIdentita()) ;
						pst_prop.setInt(++i, proprietario.getModifiedBy()) ;
						pst_prop.setString(++i, proprietario.getCodiceFiscale()) ;
						pst_prop.setString(++i, proprietario.getTipoProprietarioDetentore()) ;
						pst_prop.setInt(++i, proprietario.getOrgId()) ;
						pst_prop.execute() ;
						
						for ( IndirizzoProprietario indirizzo : proprietario.getLista_indirizzi())
						{
							indirizzo.setOrgId(orgId);
							indirizzo.update(db);
						}
					}
					
					
					for (org.aspcfs.modules.canipadronali.base.Cane cane : proprietario.getListaCani())
					{
						i = 0 ;
						Date data_nascita_cane = null ;
						if(cane.getDataNascita()!=null )
							data_nascita_cane = new Date(cane.getDataNascita().getTime());

						String inser_into_asset 				= "insert into asset (asset_id,serial_number,account_id,site_id,data_nascita,razza,sesso,enteredby,modifiedby,entered,modified,taglia,mantello) values (?,?,?,?,?,?,?,?,?,current_date,current_date,?,?)" ;

						int assetId = cane.getId() ;

						if (assetId==-1)
						{
							int livello=1 ;
							
							if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA)
								livello=2;
							
							assetId = DatabaseUtils.getNextInt( db, "asset","asset_id",livello);

							PreparedStatement pst_cane = db.prepareStatement(inser_into_asset);
							pst_cane.setInt(++i,assetId) ;
							pst_cane.setString(++i, cane.getMc());
							pst_cane.setInt(++i,orgId) ;
							pst_cane.setInt(++i, id_asl_prop) ;
							pst_cane.setDate(++i,data_nascita_cane);
							pst_cane.setString(++i, cane.getRazza()) ;
							pst_cane.setString(++i, cane.getSesso()) ;
							pst_cane.setInt(++i, cane.getEnteredby()) ;
							pst_cane.setInt(++i, cane.getModifiedby()) ;
							pst_cane.setString(++i, cane.getTaglia());
							pst_cane.setString(++i, cane.getMantello());
							pst_cane.execute() ;
						}
						else
						{
							String update_asset = "update asset set account_id =?,data_nascita=?,razza=?,sesso=?," +
							"modifiedby=?,modified=current_date,taglia=?,mantello=?,serial_number = ? where asset_id = ?" ;

							PreparedStatement pst_cane = db.prepareStatement(update_asset);
							pst_cane.setInt(++i,orgId) ;
							pst_cane.setDate(++i,data_nascita_cane);
							pst_cane.setString(++i, cane.getRazza()) ;
							pst_cane.setString(++i, cane.getSesso()) ;
							pst_cane.setInt(++i, cane.getModifiedby()) ;
							pst_cane.setString(++i, cane.getTaglia());
							pst_cane.setString(++i, cane.getMantello());
							pst_cane.setString(++i, cane.getMc());
							pst_cane.setInt(++i, cane.getId());

							pst_cane.execute() ;
						}
						cane.setId(assetId); 
						logger.info("CANI PADRONALI INSERITI CORRETTAMENTE IN GISA" );
					}
				
			}
			 if (doCommit) {
			        db.commit();
			 }
		
		}
		catch(SQLException e)
		{
			
			if (doCommit) {
					db.rollback();
					throw new SQLException(e.getMessage());
			}
		} finally {
		      if (doCommit) {    	 
		          db.setAutoCommit(true); 
		         
		      } }
		return orgId;
	}
	
	
	

	//commento al 214
	public CaneList searchCaneByMC(String mc ,String cf_prop,String reg ,int siteId,PagedListInfo searchListInfo, ActionContext context,String id_canina,String id_gisa,boolean cani_canile) {
		
		
		logger.info("RICERCA CANI PADRONALI IN GISA" );
		CaneList lista_cani = new CaneList();
		String nomeDb 			= ApplicationProperties.getProperty("DATABASECANINA") ;
		String userName 		= ApplicationProperties.getProperty("USERNAMECANINA") ;
		String password		    = ApplicationProperties.getProperty("PASSWORDCANINA") ;
		ApplicationPrefs prefs = (ApplicationPrefs) context.getSession().getServletContext().getAttribute("applicationPrefs");
		String ipAddress		    = "dbserverCanina";//ApplicationProperties.getProperty("URLCANINA") ;
		//String ipAddress	 	= "dbserver" ;
		//String ipAddress = "";
		/*if(url != null && url.contains("//") && url.contains(":5432")){
			ipAddress = url.substring(url.indexOf("//")+2, url.indexOf(":5432"));
		}*/
		Connection db = null ;
		String filtro = " where 1=1 " ;
		if ((id_canina != null && !"".equals(id_canina)) || (id_gisa != null && !"".equals(id_gisa)))
			filtro = "  " ;
		
		if (mc != null && !"".equals(mc) && !cani_canile)
		{
			filtro += " and mc ilike ? ";
		}
		else if (mc != null && !"".equals(mc) && cani_canile)
		{
			filtro += " and a.microchip ilike ? ";
		}
		if (cf_prop != null && !"".equals(cf_prop))
		{
			filtro += " and cf_proprietario ilike ? ";
		}
		if (id_canina != null && !"".equals(id_canina))
		{
			filtro += " and r_det.id = ? ";
		}
		if (id_gisa != null && !"".equals(id_gisa))
		{
			filtro += " and st_det.id_stabilimento_gisa = ? ";
		}
		if(reg!=null && !reg.equals("") && !reg.equals("null"))
		{
			filtro += " and stato ilike '%registrato%' ";
		}
		ConnectionPool cp = null ;
		try
		{
			
			cp = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPoolBdu");
			db = cp.getConnection(null, context);
			StringBuffer sqlOrder = new StringBuffer();
			searchListInfo.setColumnToSortBy("stato"); 
			searchListInfo.appendSqlTail(db, sqlOrder);
			String sqlCount = "select count(*) as recordcount from view_cani_padronali_gisa " +filtro ;
			String sql = "select * from view_cani_padronali_gisa "+filtro ;
			if (cani_canile)
			{
				sql = "SELECT DISTINCT a.id AS asset_id_canina, " +
					    "a.microchip, " +
					    "r.id AS org_id_canina, " +
					    "r_det.id as org_id_canina_det, " +
					    "a.microchip AS mc, " +
					    "rr.description AS razza, " +
					    "a.sesso, " +
					    "a.data_nascita, " +
					    "t.description AS taglia, " +
					    "a.stato AS id_stato, " +
					    "m.description AS mantello, " +
					    "stato.description AS stato, " +
					    "sogg.nome AS nome_proprietario, " +
					    "sogg.cognome AS cognome_proprietario, " +
					    "sogg.data_nascita AS data_nascita_proprietario, " +
					    "''::text AS luogo_nascita_proprietario, " +
					    "l.description AS tipo_proprietario, " +
					    "sogg.documento_identita AS documento_proprietario, " +
					    "op.codice_fiscale_impresa AS cf_proprietario, " +
					    "st.id_asl, " +
					    "asl.description AS asl_descrizione, " +
					    "c.nome AS citta_residenza, " +
					    "i.via AS indirizzo_residenza, " +
					    "sp.sigla AS provincia_residenza, " +
					    "i.cap AS cap_residenza, " +
					    "to_char(decesso.data_decesso, 'dd/MM/yyyy'::text) AS data_decesso, " +
					    "st_det.id_stabilimento_gisa as id_gisa " +
					    "from opu_stabilimento st_det  " +
					    "LEFT JOIN opu_relazione_stabilimento_linee_produttive r_det ON st_det.id = r_det.id_stabilimento  " +
					    "LEFT JOIN animale a on r_det.id = a.id_detentore " +
					     "LEFT JOIN evento ON a.id = evento.id_animale AND evento.id_tipologia_evento = 9 AND evento.data_cancellazione IS NULL AND evento.trashed_date IS NULL  " +
					     "LEFT JOIN evento_decesso decesso ON decesso.id_evento = evento.id_evento " +
					     "JOIN lookup_tipologia_stato stato ON stato.code = a.stato " +
					     "LEFT JOIN lookup_razza rr ON rr.code = a.id_razza " +
					     "LEFT JOIN lookup_mantello m ON a.id_tipo_mantello = m.code " +
					     "LEFT JOIN lookup_taglia t ON a.id_taglia = t.code " +
					     "LEFT JOIN opu_relazione_stabilimento_linee_produttive r ON r.id = a.id_proprietario " +
					     "LEFT JOIN opu_relazione_attivita_produttive_aggregazioni lp ON lp.id = r.id_linea_produttiva " +
					     "LEFT JOIN opu_relazione_attivita_produttive_aggregazioni lp_det ON lp_det.id = r_det.id_linea_produttiva " +
					     "LEFT JOIN opu_lookup_attivita_linee_produttive_aggregazioni l ON lp.id_attivita_aggregazione = l.code " +
					     "LEFT JOIN opu_lookup_attivita_linee_produttive_aggregazioni l_det ON lp_det.id_attivita_aggregazione = l_det.code " +
					     "LEFT JOIN opu_stabilimento st ON st.id = r.id_stabilimento " +
					     "LEFT JOIN lookup_asl_rif asl ON asl.code = st.id_asl " +
					     "LEFT JOIN opu_soggetto_fisico sogg ON sogg.id = st.id_soggetto_fisico " +
					     "LEFT JOIN opu_operatore op ON op.id = st.id_operatore " +
					     "LEFT JOIN opu_indirizzo i ON i.id = st.id_indirizzo " +
					     "LEFT JOIN sigla_province sp ON " +
					      "  CASE " +
					       "     WHEN textregexeq(i.provincia::text, '^[[:digit:]]+(\\.[[:digit:]]+)?$'::text) = true THEN i.provincia::integer = sp.code " +
					        "    ELSE lower(btrim(i.provincia::text)) = lower(btrim(sp.sigla::text)) " +
					        " END " +
					     "JOIN comuni1 c ON c.id = i.comune " +
					  "WHERE op.trashed_date IS NULL AND a.trashed_date IS NULL AND a.data_cancellazione IS NULL " +filtro ;
				
				sqlCount = "Select count(*) as recordcount from (SELECT DISTINCT a.id AS asset_id_canina, " +
					    "a.microchip, " +
					    "r.id AS org_id_canina, " +
					    "r_det.id as org_id_canina_det, " +
					    "a.microchip AS mc, " +
					    "rr.description AS razza, " +
					    "a.sesso, " +
					    "a.data_nascita, " +
					    "t.description AS taglia, " +
					    "a.stato AS id_stato, " +
					    "m.description AS mantello, " +
					    "stato.description AS stato, " +
					    "sogg.nome AS nome_proprietario, " +
					    "sogg.cognome AS cognome_proprietario, " +
					    "sogg.data_nascita AS data_nascita_proprietario, " +
					    "''::text AS luogo_nascita_proprietario, " +
					    "l.description AS tipo_proprietario, " +
					    "sogg.documento_identita AS documento_proprietario, " +
					    "op.codice_fiscale_impresa AS cf_proprietario, " +
					    "st.id_asl, " +
					    "asl.description AS asl_descrizione, " +
					    "c.nome AS citta_residenza, " +
					    "i.via AS indirizzo_residenza, " +
					    "sp.sigla AS provincia_residenza, " +
					    "i.cap AS cap_residenza, " +
					    "to_char(decesso.data_decesso, 'dd/MM/yyyy'::text) AS data_decesso, " +
					    "st_det.id_stabilimento_gisa as id_gisa " +
					    "from opu_stabilimento st_det  " +
					    "LEFT JOIN opu_relazione_stabilimento_linee_produttive r_det ON st_det.id = r_det.id_stabilimento  " +
					    "LEFT JOIN animale a on r_det.id = a.id_detentore " +
					     "LEFT JOIN evento ON a.id = evento.id_animale AND evento.id_tipologia_evento = 9 AND evento.data_cancellazione IS NULL AND evento.trashed_date IS NULL  " +
					     "LEFT JOIN evento_decesso decesso ON decesso.id_evento = evento.id_evento " +
					     "JOIN lookup_tipologia_stato stato ON stato.code = a.stato " +
					     "LEFT JOIN lookup_razza rr ON rr.code = a.id_razza " +
					     "LEFT JOIN lookup_mantello m ON a.id_tipo_mantello = m.code " +
					     "LEFT JOIN lookup_taglia t ON a.id_taglia = t.code " +
					     "LEFT JOIN opu_relazione_stabilimento_linee_produttive r ON r.id = a.id_proprietario " +
					     "LEFT JOIN opu_relazione_attivita_produttive_aggregazioni lp ON lp.id = r.id_linea_produttiva " +
					     "LEFT JOIN opu_relazione_attivita_produttive_aggregazioni lp_det ON lp_det.id = r_det.id_linea_produttiva " +
					     "LEFT JOIN opu_lookup_attivita_linee_produttive_aggregazioni l ON lp.id_attivita_aggregazione = l.code " +
					     "LEFT JOIN opu_lookup_attivita_linee_produttive_aggregazioni l_det ON lp_det.id_attivita_aggregazione = l_det.code " +
					     "LEFT JOIN opu_stabilimento st ON st.id = r.id_stabilimento " +
					     "LEFT JOIN lookup_asl_rif asl ON asl.code = st.id_asl " +
					     "LEFT JOIN opu_soggetto_fisico sogg ON sogg.id = st.id_soggetto_fisico " +
					     "LEFT JOIN opu_operatore op ON op.id = st.id_operatore " +
					     "LEFT JOIN opu_indirizzo i ON i.id = st.id_indirizzo " +
					     "LEFT JOIN sigla_province sp ON " +
					      "  CASE " +
					       "     WHEN textregexeq(i.provincia::text, '^[[:digit:]]+(\\.[[:digit:]]+)?$'::text) = true THEN i.provincia::integer = sp.code " +
					        "    ELSE lower(btrim(i.provincia::text)) = lower(btrim(sp.sigla::text)) " +
					        " END " +
					     "JOIN comuni1 c ON c.id = i.comune " +
					  "WHERE op.trashed_date IS NULL AND a.trashed_date IS NULL AND a.data_cancellazione IS NULL " + filtro + ") as a" ;
			}
					
					
			PreparedStatement pst_count = db.prepareStatement(sqlCount);
			
			System.out.println("conta cani nel canile: " + pst_count.toString());
			int i = 0 ;
			if (mc != null && !"".equals(mc))
			{
				pst_count.setString(++i, mc);
			}
			
			if (cf_prop != null && !"".equals(cf_prop))
			{
				pst_count.setString(++i, cf_prop);
			}
			if (id_canina != null && !"".equals(id_canina))
			{
				pst_count.setInt(++i, Integer.parseInt(id_canina));
			}
			if (id_gisa != null && !"".equals(id_gisa))
			{
				pst_count.setInt(++i, Integer.parseInt(id_gisa));
			}
			ResultSet rs_count = pst_count.executeQuery();
			
			if (rs_count.next()) {
		        int maxRecords = rs_count.getInt("recordcount");
		        searchListInfo.setMaxRecords(maxRecords);
		       
		      }
			
			i = 0 ;
			PreparedStatement pst = db.prepareStatement(sql + " "+sqlOrder.toString());
			if (mc != null && !"".equals(mc))
			{
				pst.setString(++i, mc);
			}
			
			if (cf_prop != null && !"".equals(cf_prop))
			{
				pst.setString(++i, cf_prop);
			}
			if (id_canina != null && !"".equals(id_canina))
			{
				pst.setInt(++i, Integer.parseInt(id_canina));
			}
			if (id_gisa != null && !"".equals(id_gisa))
			{
				pst.setInt(++i, Integer.parseInt(id_gisa));
			}
			ResultSet rs = pst.executeQuery();
			System.out.println("lista cani nel canile: " + pst.toString());
			while(rs.next())
			{
				org.aspcfs.modules.canipadronali.base.Cane c = new org.aspcfs.modules.canipadronali.base.Cane();
				org.aspcfs.modules.canipadronali.base.Proprietario p = new org.aspcfs.modules.canipadronali.base.Proprietario();
				
				String mc1 = rs.getString("mc");
				String razza = rs.getString("razza");
				String sesso = rs.getString("sesso");
				Date data_nascita = rs.getDate("data_nascita");
				String taglia = rs.getString("taglia");
				String mantello = rs.getString("mantello");
				int id_stato = rs.getInt("id_stato");
				
				String data_decesso = rs.getString("data_decesso");
				String nome_proprietario = rs.getString("nome_proprietario");
				String cognome_proprietario = rs.getString("cognome_proprietario");
				String cf_proprietario = rs.getString("cf_proprietario");
				String documento_proprietario = rs.getString("documento_proprietario");
				String citta = rs.getString("citta_residenza");
				citta.replaceAll("'", "\\'");
				String indirizzo = rs.getString("indirizzo_residenza");
				String prov = rs.getString("provincia_residenza");
				String stato = rs.getString("stato");
				String cap = rs.getString("cap_residenza");
				IndirizzoProprietario indirizzo_prop = new IndirizzoProprietario();
				indirizzo_prop.setCap(cap);
				indirizzo_prop.setCitta(citta);
				indirizzo_prop.setProvincia(prov);
				indirizzo_prop.setVia(indirizzo);
				
				ArrayList<IndirizzoProprietario> indirizzi = p.getLista_indirizzi();
				indirizzi.add(indirizzo_prop);
				p.setLista_indirizzi(indirizzi);
				
				int id_asl = rs.getInt("id_asl");
				String asl_descrizione = rs.getString("asl_descrizione");
				String luogo_nascita_proprietario = rs.getString("luogo_nascita_proprietario");
				Date data_nascita_prop = rs.getDate("data_nascita_proprietario");

				c.setMc(mc1);
				c.setAslRiferimentoStringa(asl_descrizione);
				c.setDataNascita(data_nascita);
				c.setSesso(sesso);
				c.setRazza(razza);
				c.setMantello(mantello);
				c.setTaglia(taglia);
				c.setDataDecesso(data_decesso);
				c.setIdStato(id_stato);
				
				p.setTipoProprietarioDetentore(rs.getString("tipo_proprietario"));
				p.setCodiceFiscale(cf_proprietario);
				p.setNome(nome_proprietario);
				p.setCognome(cognome_proprietario);
				p.setDocumentoIdentita(documento_proprietario);
				p.setLuogoNascita(luogo_nascita_proprietario);
				p.setDataNascita(data_nascita_prop);
				p.setIdAsl(id_asl);
				c.setNominativoProprietario(nome_proprietario+" "+cognome_proprietario);
				c.setStato(stato);
				c.setProprietario(p);
				
				lista_cani.add(c);
			}
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.warning("Errore Ricerca cani in BDR");
			// TODO: handle exception
		}
		finally
		{
			
				cp.free(db);
			
			
		}
		
		return lista_cani;
	}
	
	
	
	
	private org.aspcfs.modules.canipadronali.base.Cane searchCaneBdr(String mc)
	{
		 	org.aspcfs.modules.canipadronali.base.Cane c = null;
		 	org.aspcfs.modules.canipadronali.base.Proprietario p = null ;
			
			String url_info_cane = ApplicationProperties.getProperty("URL_SERVIZIO_INFOCANE") + "?mc=" + mc + "&responseType=JSON";
			String url_info_prop = ApplicationProperties.getProperty("URL_SERVIZIO_INFOPROPRIETARIO") + "?mc=" + mc + "&responseType=JSON";
			try
			{
				logger.info("INIZIO CHIAMATA INFOCANE");
				c = gson.fromJson( UrlUtil.getUrlResponse( url_info_cane ), org.aspcfs.modules.canipadronali.base.Cane.class );
				logger.info("FINE CHIAMATA INFOCANE");
				if ( c!=null)
				{
					logger.info("INIZIO CHIAMATA INFOPROPRIETARIO");
					p = gson.fromJson( UrlUtil.getUrlResponse( url_info_prop ), org.aspcfs.modules.canipadronali.base.Proprietario.class );
					logger.info("FINE CHIAMATA INFOPROPRIETARIO");
					if (p !=null)
					{
						c.setProprietario(p);
					}
					
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return c ;
		
		
	}
	
	
	
	
	private void buildRecordProprietario(ResultSet rs , org.aspcfs.modules.canipadronali.base.Proprietario p)
	{
		try 
		{

			IndirizzoProprietario ind = new IndirizzoProprietario();
			ArrayList<IndirizzoProprietario> lista = p.getLista_indirizzi();
			ind.setCitta(rs.getString("citta_proprietario"));
			ind.setProvincia(rs.getString("provincia_proprietario"));
			ind.setId(rs.getInt("id_address"));
			ind.setVia(rs.getString("indirizzo_proprietario"));
			ind.setCap(rs.getString("cap_proprietario"));
			lista.add(ind);
			
			
			p.setName(rs.getString("nominativo"));
			p.setLista_indirizzi(lista);
			p.setIdProprietario(rs.getInt("org_id"));
			p.setOrgId(p.getIdProprietario());
			p.setIdAsl(rs.getInt("id_asl_prop")) ;
			p.setRagioneSociale(rs.getString("nominativo"));
			p.setTipoProprietarioDetentore(rs.getString("tipo_proprietario"));
			p.setNome(rs.getString("nome"));
			p.setCognome(rs.getString("cognome"));
			p.setDataNascita(rs.getDate("data_nascita_prop"));
			p.setLuogoNascita(rs.getString("luogo_nascita_prop"));
			p.setCodiceFiscale(rs.getString("cf_prop"));
			p.setDocumentoIdentita(rs.getString("documento"));



		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private void buildListaCani(ResultSet rs , org.aspcfs.modules.canipadronali.base.Proprietario p)
	{
		try 
		{
				org.aspcfs.modules.canipadronali.base.Cane c = new org.aspcfs.modules.canipadronali.base.Cane();
				c.setId(rs.getInt("id_cane"));
				c.setMc(rs.getString("mc"));
				c.setRazza(rs.getString("razza"));
				c.setTaglia(rs.getString("taglia"));
				c.setMantello(rs.getString("mantello"));
				c.setSesso(rs.getString("sesso"));
				c.setDettagliAddizionali(rs.getString("dett_add"));
				c.setDataNascita(rs.getDate("data_nascita_cane"));
				c.setNominativoProprietario(p.getName());
				c.setOrgId(p.getIdProprietario());
				c.setSiteId(p.getIdAsl());
				
				ArrayList<org.aspcfs.modules.canipadronali.base.Cane> lista_cani = p.getListaCani();
				lista_cani.add(c);
				p.setListaCani(lista_cani);
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public org.aspcfs.modules.canipadronali.base.Proprietario dettaglioProprietario(int orgId, int idControllo) {

		org.aspcfs.modules.canipadronali.base.Proprietario proprietario = null ;
		String query_proprietario 	= ApplicationProperties.getProperty("GET_PROPRIETARIO");
		String query_lista_cani 	= ApplicationProperties.getProperty("GET_LISTA_CANI");


		try
		{
			PreparedStatement pst = db.prepareStatement(query_proprietario);
			pst.setInt(1, orgId);
			ResultSet rs = pst.executeQuery() ;
			if (rs.next())
			{
				proprietario = new org.aspcfs.modules.canipadronali.base.Proprietario(); 
				buildRecordProprietario(rs, proprietario);
			}
			
			PreparedStatement pstCani = db.prepareStatement(query_lista_cani);
			pstCani.setInt(1, orgId);
			pstCani.setInt(2, idControllo);
			ResultSet rsCani = pstCani.executeQuery() ;
			while (rsCani.next())
			{
				org.aspcfs.modules.canipadronali.base.Cane cane = new org.aspcfs.modules.canipadronali.base.Cane(); 
				buildListaCani(rsCani,proprietario);
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return proprietario;
	}
	
	public org.aspcfs.modules.canipadronali.base.Proprietario dettaglioProprietario(int orgId) {

		org.aspcfs.modules.canipadronali.base.Proprietario proprietario = null ;
		String query_proprietario 	= ApplicationProperties.getProperty("GET_PROPRIETARIO");
//		String query_lista_cani 	= ApplicationProperties.getProperty("GET_LISTA_CANI");


		try
		{
			PreparedStatement pst = db.prepareStatement(query_proprietario);
			pst.setInt(1, orgId);
			ResultSet rs = pst.executeQuery() ;
			if (rs.next())
			{
				proprietario = new org.aspcfs.modules.canipadronali.base.Proprietario(); 
				buildRecordProprietario(rs, proprietario);
			}
			
		

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return proprietario;
	}
	

	

	
}

