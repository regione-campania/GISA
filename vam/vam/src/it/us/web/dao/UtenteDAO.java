/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.constants.Sql;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupEsameIstopatologicoSedeLesioneDAO;
import it.us.web.dao.lookup.LookupEsameIstopatologicoTipoDiagnosiDAO;
import it.us.web.dao.vam.ClinicaDAO;
import it.us.web.util.Md5;
import it.us.web.util.bean.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtenteDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( UtenteDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static BUtente authenticate( String un, String pw, Persistence persistence ) 
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.PASSWORD_CHECK );		
			stat.setString( 1, un );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{
				String pwd = rs.getString("PASSWORD");
				
				if( pwd.equals( Md5.encrypt(pw) ) )
				{
					utente = (BUtente) persistence.find( BUtente.class, rs.getInt( "id" ) );//createBeanUtente( rs );
				}	
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;		
	}
	
	public static void setAccessPosition( BUtente utente, Connection connection ) 
	{
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			stat = connection.prepareStatement( "update utenti set access_position_lat = ?, access_position_lon = ? , access_position_err = ? where id = ? " );		
			stat.setDouble(1, utente.getAccessPositionLat());
			stat.setDouble(2, utente.getAccessPositionLon());
			stat.setString(3, utente.getAccessPositionErr());
			stat.setDouble(4, utente.getId());
			stat.executeUpdate();
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, null );
		}
	}
	
	@SuppressWarnings("unchecked")
	public static SuperUtente superAuthenticate( String un, String pw, Persistence persistence ) 
	{
		
		
		List<SuperUtente> sus = (List<SuperUtente>)persistence.createCriteria( SuperUtente.class )
									.add( Restrictions.eq( "username", un ) )
									.add( Restrictions.eq( "password", Md5.encrypt(pw) ) )
									.add( Restrictions.isNull( "trashedDate") )
									.add( Restrictions.eq( "enabled", true ) )
									.add( Restrictions.or( Restrictions.isNull( "dataScadenza" ) , Restrictions.gt( "dataScadenza", new Date() )) )
								.list();
		
		return sus.size() > 0 ? sus.get( 0 ) : null;
	}
	
	public static SuperUtente superAuthenticate( String un, String pw, Connection connection ) throws SQLException 
	{
		List<SuperUtente> sus = new ArrayList<SuperUtente>();
		String sql = "select entered as entered_string, luogo,num_iscrizione_albo as numIscrizioneAlbo,sigla_provincia as siglaProvincia, id,data_scadenza as dataScadenza, enabled,enabled_date as enabledDate, entered, entered_by as enteredBy,modified, modified_by as modifiedBy, note,password, trashed_date as trashedDate, username,access_position_lat as accessPositionLat,access_position_lon as accessPositionLon,access_position_err as accessPositionErr from utenti_super where trashed_date is null and username = ? and (password = ? or ? is null or ? = '') and enabled " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, un);
		st.setString(2, Md5.encrypt(pw));
		st.setString(3, pw);
		st.setString(4, pw);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			SuperUtente su = new SuperUtente();
			su = (SuperUtente)Bean.populate(su, rs);
			su.setEnabled(true);
			su.setNumIscrizioneAlbo(rs.getString("numIscrizioneAlbo"));
			su.setSiglaProvincia(rs.getString("siglaProvincia"));
			su.setDataScadenza(rs.getDate("dataScadenza"));
			su.setEntered(rs.getDate("entered_string"));
			
			//Popolo getUtenti
			List<BUtente> us = new ArrayList<BUtente>();
			sql = "select username_ as username, id, cap,codice_fiscale as codiceFiscale,cognome,nome,comune,comune_nascita as comuneNascita, " + 
					"data_nascita as dataNascita, data_scadenza as dataScadenza,enabled,enabled_date as enabledDate,entered,modified,password,ruolo,trashed_date as trashedDate, username,asl_referenza as aslReferenza,access_position_lat as accessPositionLat,access_position_lon as accessPositionLon,access_position_err as accessPositionErr, clinica as clinica_id, superutente as superutente_id from utenti where trashed_date is null and superutente = ? and enabled " ;
			PreparedStatement st2 = connection.prepareStatement(sql);
			st2.setInt(1, su.getId());
			ResultSet rs2 = st2.executeQuery();
			
			while(rs2.next())
			{
				us.add(UtenteDAO.getUtente(rs2.getInt("id"), connection));
			}
			su.setUtenti(us);
			//Fine popolo getUtenti
			
			sus.add(su);
		}
		
		return sus.size() > 0 ? sus.get( 0 ) : null;
	}
	
	public static SuperUtente superAuthenticateNoPassword( String un, Connection connection ) throws SQLException 
	{
		List<SuperUtente> sus = new ArrayList<SuperUtente>();
		String sql = "select  luogo,num_iscrizione_albo as numIscrizioneAlbo,sigla_provincia as siglaProvincia, id,data_scadenza as dataScadenza, enabled,enabled_date as enabledDate, entered, entered_by as enteredBy,modified, modified_by as modifiedBy, note,password, trashed_date as trashedDate, username,access_position_lat as accessPositionLat,access_position_lon as accessPositionLon,access_position_err as accessPositionErr  from utenti_super where trashed_date is null and username = ? and enabled " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, un);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			SuperUtente su = new SuperUtente();
			su = (SuperUtente)Bean.populate(su, rs);
			su.setEnabled(true);
			su.setNumIscrizioneAlbo(rs.getString("numIscrizioneAlbo"));
			su.setSiglaProvincia(rs.getString("siglaProvincia"));
			
			//Popolo getUtenti
			List<BUtente> us = new ArrayList<BUtente>();
			sql = "select username_ as username, id, cap,codice_fiscale as codiceFiscale,cognome,nome,comune,comune_nascita as comuneNascita, " + 
						"data_nascita as dataNascita, data_scadenza as dataScadenza,enabled,enabled_date as enabledDate,entered,modified,password,ruolo,trashed_date as trashedDate, username,asl_referenza as aslReferenza,access_position_lat as accessPositionLat,access_position_lon as accessPositionLon,access_position_err as accessPositionErr, clinica as clinica_id, superutente as superutente_id   from utenti where trashed_date is null and superutente = ? and enabled " ;
			PreparedStatement st2 = connection.prepareStatement(sql);
			st2.setInt(1, su.getId());
			ResultSet rs2 = st2.executeQuery();
			
			while(rs2.next())
			{
				BUtente ut = UtenteDAO.getUtente(rs2.getInt("id"), connection);
				ut.setSuperutente(su);
				ut.setId(rs2.getInt("id"));
				us.add(ut);
			}
			su.setUtenti(us);
			//Fine popolo getUtenti
			
			sus.add(su);
		}
		
		return sus.size() > 0 ? sus.get( 0 ) : null;
	}
	
	@SuppressWarnings("unchecked")
	public static SuperUtente superAuthenticateNoPassword( String un, Persistence persistence ) 
	{
		List<SuperUtente> sus = (List<SuperUtente>)persistence.createCriteria( SuperUtente.class )
									.add( Restrictions.eq( "username", un ) )
									.add( Restrictions.isNull( "trashedDate") )
									.add( Restrictions.eq( "enabled", true ) )
									.add( Restrictions.or( Restrictions.isNull( "dataScadenza" ) , Restrictions.gt( "dataScadenza", new Date() )) )
								.list();
		
		return sus.size() > 0 ? sus.get( 0 ) : null;
	}
	
	public static BUtente createBeanUtente(ResultSet rs)
	{
		BUtente utente = new BUtente();
		
		try 
		{
			utente.setCap( rs.getString("CAP") );
			utente.setCodiceFiscale( rs.getString("CODICE_FISCALE") );
			utente.setCognome( rs.getString("COGNOME") );
			utente.setComune( rs.getString("COMUNE") );
			utente.setComuneNascita( rs.getString("COMUNE_NASCITA") );
			utente.setDataNascita(rs.getDate("data_nascita"));
			utente.setDomandaSegreta(rs.getString("domanda_segreta"));
			utente.setEmail( rs.getString("EMAIL") );
			utente.setEntered(rs.getTimestamp("entered"));
			utente.setEnteredBy(rs.getInt("entered_By"));
			utente.setFax(rs.getString("fax"));
			utente.setId( rs.getInt("ID") );
			utente.setIndirizzo(rs.getString("indirizzo"));
			utente.setModified(rs.getTimestamp("modified"));
			utente.setModifiedBy(rs.getInt("modified_By"));
			utente.setNome( rs.getString("NOME") );
			utente.setPassword(rs.getString("password"));
			utente.setProvincia(rs.getString("provincia"));
			utente.setRispostaSegreta(rs.getString("risposta_segreta"));
			utente.setStato(rs.getString("stato"));
			utente.setTelefono1(rs.getString("telefono1"));
			utente.setTelefono2(rs.getString("telefono2"));
			utente.setTrashedDate(rs.getTimestamp("trashed_Date"));
			utente.setUsername				( rs.getString("USERNAME") );
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return utente;
	}
	
	
	public static BUtenteAll createBeanUtenteAll(ResultSet rs)
	{
		BUtenteAll utente = new BUtenteAll();
		
		try 
		{
			utente.setCap( rs.getString("CAP") );
			utente.setCodiceFiscale( rs.getString("CODICE_FISCALE") );
			utente.setCognome( rs.getString("COGNOME") );
			utente.setComune( rs.getString("COMUNE") );
			utente.setComuneNascita( rs.getString("COMUNE_NASCITA") );
			utente.setDataNascita(rs.getDate("data_nascita"));
			utente.setDomandaSegreta(rs.getString("domanda_segreta"));
			utente.setEmail( rs.getString("EMAIL") );
			utente.setEntered(rs.getTimestamp("entered"));
			utente.setEnteredBy(rs.getInt("entered_By"));
			utente.setFax(rs.getString("fax"));
			utente.setId( rs.getInt("ID") );
			utente.setIndirizzo(rs.getString("indirizzo"));
			utente.setModified(rs.getTimestamp("modified"));
			utente.setModifiedBy(rs.getInt("modified_By"));
			utente.setNome( rs.getString("NOME") );
			utente.setPassword(rs.getString("password"));
			utente.setProvincia(rs.getString("provincia"));
			utente.setRispostaSegreta(rs.getString("risposta_segreta"));
			utente.setStato(rs.getString("stato"));
			utente.setTelefono1(rs.getString("telefono1"));
			utente.setTelefono2(rs.getString("telefono2"));
			utente.setTrashedDate(rs.getTimestamp("trashed_Date"));
			utente.setUsername				( rs.getString("USERNAME") );
			utente.setRuolo(rs.getString("ruolo"));
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return utente;
	}

	public static BUtente getUtente(String username)
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try 
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_USER );
			stat.setString( 1 ,  username );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{		
				utente = createBeanUtente( rs );
			}
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;
	}
	
	public static BUtente getUtente( int id )
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try 
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_USER_FROM_ID );
			stat.setInt( 1 ,  id );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{		
				utente = createBeanUtente( rs );
				utente.setClinica(ClinicaDAO.getClinica(rs.getInt("clinica"), conn));
				utente.setSuperutente(SuperUtenteDAO.getSuperUtente(rs.getInt("superutente"), conn));
			}
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;
	}
	
	
	public static BUtenteAll getUtenteAll( int id )
	{
		BUtenteAll				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try 
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_USER_FROM_ID_ALL );
			stat.setInt( 1 ,  id );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{		
				utente = createBeanUtenteAll( rs );
				utente.setClinica(ClinicaDAO.getClinica(rs.getInt("clinica"), conn));
				utente.setSuperutente(SuperUtenteDAO.getSuperUtente(rs.getInt("superutente"), conn));
			}
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;
	}
	
	public static BUtenteAll getUtenteAll( int id, Connection conn )
	{
		BUtenteAll				utente	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try 
		{
			stat = conn.prepareStatement( Sql.GET_USER_FROM_ID_ALL );
			stat.setInt( 1 ,  id );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{		
				utente = createBeanUtenteAll( rs );
				utente.setClinica(ClinicaDAO.getClinica(rs.getInt("clinica"), conn));
				utente.setSuperutente(SuperUtenteDAO.getSuperutenteAll(rs.getInt("superutente")));
			}
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, null );
		}
		
		return utente;
	}
	
	public static BUtente getUtente( int id, Connection connection ) throws SQLException
	{
		BUtente bu = new BUtente();
		String sql = " select asl.id as idAsl, asl.description as descriptionAsl,asl.level as levelAsl,asl.enabled as enabledAsl,cl.id as idC,cl.data_cessazione,cl.canile_bdu,cl.id_stabilimento_gisa,cl.nome as nomeC,cl.nome_breve as nomeBreveC, username_ as username, u.id, cap,codice_fiscale as codiceFiscale,cognome,u.nome,u.comune,comune_nascita as comuneNascita, " + 
					 " to_char(data_nascita,'dd/MM/yyyy HH:mi:ss') as dataNascita, data_scadenza, u.entered as entered_string,to_char(data_scadenza,'dd/MM/yyyy HH:mi:ss') as dataScadenza,u.enabled,to_char(u.enabled_date,'dd/MM/yyyy HH:mi:ss') as enabledDate,to_char(u.entered,'dd/MM/yyyy HH:mi:ss') as entered,to_char(u.modified,'dd/MM/yyyy HH:mi:ss') as modified,password,ruolo,to_char(u.trashed_date,'dd/MM/yyyy HH:mi:ss') as trashedDate, username,asl_referenza as aslReferenza,access_position_lat as accessPositionLat,access_position_lon as accessPositionLon,access_position_err as accessPositionErr, clinica as clinica_id, superutente as superutente_id " + 
				     " from utenti u, clinica cl, lookup_asl asl " + 
					 " where u.clinica = cl.id and cl.asl = asl.id and u.trashed_date is null and u.id = ? and u.enabled " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			bu = (BUtente)Bean.populate(bu, rs);
			bu.setSuperutente(SuperUtenteDAO.getSuperUtente(rs.getInt("superutente_id"), connection));
			bu.setClinica(getClinica(rs));
			bu.setRuolo(rs.getString("ruolo"));
			bu.setEnabled(true);
			bu.setDataScadenza(rs.getDate("data_scadenza"));
			bu.setCodiceFiscale(rs.getString("codiceFiscale"));
			bu.setEntered(rs.getDate("entered_string"));
			bu.setAslReferenza(LookupAslDAO.getAsl(rs.getInt("aslReferenza"), connection));
		}
		return bu;
	}
	
	

	public static ArrayList<BUtente> getUtenti( String substring_nome, String substring_cognome,String substring_ruolo )
	{
		String nome		= (substring_nome == null) ? ("%") : ("%" + substring_nome.trim() + "%");
		String cognome	= (substring_cognome == null) ? ("%") : ("%" + substring_cognome.trim() + "%");
		String ruolo	= (substring_ruolo == "" || substring_ruolo == null) ? ("") : (substring_ruolo);
		
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<BUtente>	v		= new ArrayList<BUtente>();
		
		try 
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_UTENTI_FILTERED );
			stat.setString( 1, nome );
			stat.setString( 2, cognome );
			rs = stat.executeQuery();
			
			while( rs.next() )
			{		
				
				BUtente utente = null;
				utente = createBeanUtente( rs );
				if (utente.getRuolo() != null)
				{
					if( ruolo.equals("") || utente.getRuolo().equalsIgnoreCase(ruolo) )
					{
						v.add(utente);
					}	
				}
				else
				{
					if( ruolo.equals("<nessuno>") || ruolo.equals("") )
					{
						v.add(utente);
					}
				}
			}
						
		}
		catch(Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return v;
	}
	
	public static ArrayList<BUtente> getUtenti(Connection connection, int idAsl, int idClinica) throws SQLException
	{
		ArrayList<BUtente> utenti = new ArrayList<BUtente>();
		String sql = " select max(u.id) as id , u.nome, u.cognome, u.ruolo, u.superutente "
				+    " from utenti u, clinica c, lookup_asl asl "
				+    " where u.trashed_date is null and u.clinica = c.id and asl.id = c.asl and (asl.id = ? or -1 = ?)  and (c.id = ? or -1 = ?)"
				+    " group by u.nome, u.cognome, u.ruolo,  u.superutente  "
				+    " order by u.cognome, u.nome " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAsl);
		st.setInt(2, idAsl);
		st.setInt(3, idClinica);
		st.setInt(4, idClinica);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			BUtente u = new BUtente();
			SuperUtente su = new SuperUtente();
			u.setId(rs.getInt("id"));
			u.setNome(rs.getString("nome"));
			u.setCognome(rs.getString("cognome"));
			u.setRuolo(rs.getString("ruolo"));
			su.setId(rs.getInt("superutente"));
			u.setSuperutente(su);
			utenti.add(u);
		}
		return utenti;
	}
	
	public static Clinica getClinica(ResultSet rs) throws SQLException
	{
		Clinica cl = null;
		cl = new Clinica();
		cl.setId(rs.getInt("idC"));
		cl.setNomeBreve(rs.getString("nomeBreveC"));
		cl.setNome(rs.getString("nomeC"));
		cl.setCanileBdu(rs.getInt("canile_bdu"));
		cl.setIdStabilimentoGisa(rs.getInt("id_stabilimento_gisa"));
		cl.setDataCessazione(rs.getDate("data_cessazione"));
		cl.setLookupAsl(getAsl(rs));
		return cl;
	}
	
	public static LookupAsl getAsl(ResultSet rs) throws SQLException
	{
		LookupAsl asl = null;
		asl = new LookupAsl();
		asl.setId(rs.getInt("idAsl"));
		asl.setDescription(rs.getString("descriptionAsl"));
		asl.setLevel(rs.getInt("levelAsl"));
		asl.setEnabled(rs.getBoolean("enabledAsl"));
		return asl;
	}
	
	public static ArrayList<EsameIstopatologico> getEsamiIstopatologici(Connection connection, int idSuperUtente) throws SQLException
	{
		ArrayList<EsameIstopatologico> esami = new ArrayList<EsameIstopatologico>();
		String sql = " select e.tipo_diagnosi, e.sede_lesione, e.numero,e.id, e.diagnosi_non_tumorale, e.data_esito, e.data_richiesta, e.outsideCC, "
						+    " wu.id as wu_id, wu.description as wu_description, wu.enabled as wu_enabled, wu.codice as wu_codice, wu.level as wu_level, "
		+    " an.identificativo, "
		+    " cc.id as id_cc, "
		+    " acc.id as id_acc, "
		+    " an2.id as id_animale, an2.identificativo as identificativo2 "
+    " from esame_istopatologico e "
+    " left join utenti_ u on u.id = e.entered_by "
+    " left join utenti_super_ u_super on u_super.id = u.superutente "
+    " left join animale an on an.id = e.animale and an.trashed_date is null "
+    " left join cartella_clinica cc on cc.id = e.cartella_clinica and cc.trashed_date is null "
+    " left join accettazione acc on cc.accettazione = acc.id and acc.trashed_date is null "
		+    " left join animale an2 on an2.id = acc.animale and an2.trashed_date is null "
+    " left join lookup_esame_istopatologico_who_umana wu on wu.id = e.who_umana and wu.enabled "
		+    " where e.entered_by = u.id and e.trashed_date is null and u_super.id = ? "
		+    " order by e.data_richiesta desc " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idSuperUtente);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			EsameIstopatologico esame = new EsameIstopatologico();

			esame.setTipoDiagnosi(LookupEsameIstopatologicoTipoDiagnosiDAO.get(rs.getInt("tipo_diagnosi"),connection));

			if(rs.getString("identificativo")!=null)
			{
				Animale animale = new Animale();
				animale.setIdentificativo(rs.getString

						("identificativo"));
				esame.setAnimale(animale);
			}

			if(rs.getString("identificativo2")!=null)
			{
				Animale an = new Animale();
				an.setId(rs.getInt("id_animale"));
				an.setIdentificativo(rs.getString

						("identificativo2"));
				CartellaClinica cc = new CartellaClinica();
				cc.setId(rs.getInt("id_cc"));
				Accettazione acc = new Accettazione();
				acc.setId(rs.getInt("id_acc"));
				acc.setAnimale(an);
				cc.setAccettazione(acc);
				esame.setCartellaClinica(cc);
			}

			if(rs.getInt("wu_id")>0)
			{
				LookupEsameIstopatologicoWhoUmana wu = new 

						LookupEsameIstopatologicoWhoUmana();
				wu.setId(rs.getInt("wu_id"));
				wu.setCodice(rs.getString("wu_codice"));
				wu.setDescription(rs.getString("wu_description"));
				wu.setEnabled(rs.getBoolean("wu_enabled"));
				wu.setLevel(rs.getInt("wu_level"));
				esame.setWhoUmana(wu);
			}

			esame.setOutsideCC(rs.getBoolean("outsideCC"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			
			if(rs.getInt("tipo_diagnosi")!=3)
				esame.setDiagnosiNonTumorale("");
			else
				esame.setDiagnosiNonTumorale(rs.getString("diagnosi_non_tumorale"));
		
			esame.setId(rs.getInt("id"));
			esame.setNumero(rs.getString("numero"));
			esame.setSedeLesione

			(LookupEsameIstopatologicoSedeLesioneDAO.getSede(rs.getInt

					("sede_lesione"),connection));
			esami.add(esame);
		}
		return esami;
	}
	
	
}
