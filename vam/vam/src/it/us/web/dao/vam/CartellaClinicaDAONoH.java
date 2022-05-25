/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AccettazioneNoH;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleNoH;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.AttivitaBdrNoH;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.ClinicaNoH;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.EsameObiettivo;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.bean.vam.StatoTrasferimento;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.TrasferimentoNoH;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupAssociazioniDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
import it.us.web.dao.lookup.LookupDestinazioneAnimaleDAO;
import it.us.web.dao.lookup.LookupEsameObiettivoTipoDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.dao.lookup.LookupSpecieDAO;
import it.us.web.dao.lookup.LookupTipiRichiedenteDAO;
import it.us.web.dao.lookup.LookupTipoTrasferimentoDAO;
import it.us.web.util.DateUtils;
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
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.jmesa.limit.RowSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartellaClinicaDAONoH extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( CartellaClinicaDAONoH.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static ArrayList<CartellaClinicaNoH> getCartelleCliniche( Connection connection, RowSelect rowSelect, 
			boolean tipologiaFiltroMorto1, boolean tipologiaFiltroMorto2, boolean tipologiaFiltroDayHospital1, boolean tipologiaFiltroDayHospital2, 
			String fsFiltro, String dataAperturaFiltroInizio,String dataAperturaFiltroFine,
			String dataChiusuraFiltroInizio,String dataChiusuraFiltroFine,String mcFiltro, int idClinica,String numCcFiltro,String orderFilter) throws SQLException
	{
		ArrayList<CartellaClinicaNoH> cartelle = new ArrayList<CartellaClinicaNoH>();
		String sql = " select cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, cc.data_apertura as dataApertura, cc.id as id, cc.numero, fs.id as fascicolo_id, acc.id as accettazione_id from cartella_clinica cc, fascicolo_sanitario fs, accettazione acc, animale an, utenti ut "
				   + " where cc.trashed_date is null and cc.fascicolo_sanitario = fs.id and "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id and "
				   + "       ut.id = cc.entered_by and "
			       + "       (cc_morto = ? or cc_morto = ? ) and "
				   + "	     (day_hospital = ? or day_hospital = ? ) and "
				   + "       fs.numero ilike ? and "
				   + "       cc.data_apertura >= to_date(?,'dd/MM/yyyy') and "
				   + "       cc.data_apertura <= to_date(?,'dd/MM/yyyy') and "
				   + "       ( cc.data_chiusura >= to_date(?,'dd/MM/yyyy') or cc.data_chiusura is null ) and "
				   + "       ( cc.data_chiusura <= to_date(?,'dd/MM/yyyy') or cc.data_chiusura is null ) and "
				   + "       an.identificativo ilike ? and "
				   + "       ut.clinica = ? and "
				   + "       cc.numero ilike ? "
				   + " order by cc.numero desc, cc.id desc "
				   +   (orderFilter.equals("")?(""):(" , " +orderFilter))
				   + " limit " + rowSelect.getMaxRows() + " offset " + rowSelect.getRowStart();
		PreparedStatement st = connection.prepareStatement(sql);
		st.setBoolean(1, tipologiaFiltroMorto1);
		st.setBoolean(2, tipologiaFiltroMorto2);
		st.setBoolean(3, tipologiaFiltroDayHospital1);
		st.setBoolean(4, tipologiaFiltroDayHospital2);
		st.setString (5, "%"+fsFiltro+"%");
		st.setString (6, dataAperturaFiltroInizio);
		st.setString (7, dataAperturaFiltroFine);
		st.setString (8, dataChiusuraFiltroInizio);
		st.setString (9, dataChiusuraFiltroFine);
		st.setString (10, "%"+mcFiltro+"%");
		st.setInt (   11, idClinica);
		st.setString (12, "%"+numCcFiltro+"%");
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			CartellaClinicaNoH cc = new CartellaClinicaNoH();
			cc = (CartellaClinicaNoH) Bean.populate(cc, rs);
			cc.setDataApertura(rs.getDate("dataApertura"));
			cc.setDataChiusura(rs.getDate("dataChiusura"));
			cc.setFascicoloSanitario(getFascicoloSanitario(connection, rs.getInt("fascicolo_id")));
			cc.setAccettazione(getAccettazione(connection, rs.getInt("accettazione_id")));
			cc.setDiagnosis(getDiagnosis(connection,rs.getInt("id")));
			cc.setCcMorto(rs.getBoolean("ccMorto"));
			cartelle.add(cc);
		}
		return cartelle;
	}
	
	public static Integer getIdAccettazione(int idCc, Connection conn) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Integer id = null;

		try {
			stat = conn.prepareStatement(" select accettazione as id " +
										 " from cartella_clinica cc " +
										 " where cc.id = ? and  " +
										 " cc.trashed_date is null  ");
			stat.setInt(1, idCc);
			rs = stat.executeQuery();

			
			
			if (rs.next()) 
				id = rs.getInt("id");

		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, null);
		}

		return id;
	}
	
	public static CartellaClinicaNoH getCc( Connection connection,int id) throws SQLException
	{
		Context ctxVam;
		CartellaClinicaNoH cc = null;
		Connection conn = null;
		try 
		{
			ctxVam = new InitialContext();
		
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
		conn = dsVam.getConnection();
		
		
		String sql = " select cc.adozione_verso_assoc_canili, cc.adozione_fuori_asl, an.identificativo as identificativo_animale, ut.id as id_utente, ut.nome as nome, ut.cognome as cognome, cc.destinazione_animale, cc.dimissioni_entered_by, cc.dimissioni_entered, cc.progressivo, cc.entered, cc.modified, cc.entered_by , cc.modified_by, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
				   + " cc.data_apertura as dataApertura, cc.id as id, cc.numero, fs.id as fascicolo_id, "
				   + " acc.id as accettazione_id "
				   + " from cartella_clinica cc, fascicolo_sanitario fs, accettazione acc, animale an, utenti_ ut "
				   + " where cc.id = ? and cc.trashed_date is null and cc.fascicolo_sanitario = fs.id and "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id and "
				   + "       ut.id = cc.entered_by ";
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			cc = new CartellaClinicaNoH();
			cc = (CartellaClinicaNoH) Bean.populate(cc, rs);
			cc.setCcMorto(rs.getBoolean("ccMorto"));
			cc.setAdozioneFuoriAsl(rs.getBoolean("adozione_fuori_asl"));
			cc.setAdozioneVersoAssocCanili(rs.getBoolean("adozione_verso_assoc_canili"));
			cc.setProgressivo(rs.getInt("progressivo"));
			cc.setDataApertura(rs.getDate("dataApertura"));
			cc.setDataChiusura(rs.getDate("dataChiusura"));
			cc.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by"), connection));
			cc.setModified(rs.getDate("modified"));
			cc.setEntered(rs.getDate("entered"));
			cc.setModifiedBy(UtenteDAO.getUtenteAll(rs.getInt("modified_by"), connection));
			cc.setFascicoloSanitario(getFascicoloSanitario(connection, rs.getInt("fascicolo_id")));
			cc.setAccettazione(getAccettazione(connection, rs.getInt("accettazione_id")));
			cc.setDimissioniEntered(rs.getDate("dimissioni_entered"));
			cc.setDimissioniEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("dimissioni_entered_by"), connection));
			cc.setTrasferimenti(CartellaClinicaDAONoH.getTrasferimenti(cc.getId(), connection));
			cc.setTrasferimentiByCcPostTrasf(CartellaClinicaDAONoH.getTrasferimentiByCcPostTrasf(cc.getId(), connection));
			cc.setDestinazioneAnimale(LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, rs.getInt("destinazione_animale")));
			cc.setIdentificativoAnimale(rs.getString("identificativo_animale"));
			BUtente utente = new BUtente();
			utente.setId(rs.getInt("id_utente"));
			utente.setNome(rs.getString("nome"));
			utente.setCognome(rs.getString("cognome"));
			cc.setEnteredBy(utente);
			
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Errore nel caricamento della cc: " + e.getMessage());
		}
		finally
		{
			if(conn!=null)
			{
				try
				{
					conn.close();
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			if(conn!=null)
			{
				try
				{
					conn.close();
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
		}
		return cc;
	}
	
	
	public static CartellaClinicaNoH getCcTrasfList( Connection connection,int id) throws SQLException
	{
		CartellaClinicaNoH cc = null;
		String sql = " select cc.cc_post_trasferimento, cc.cc_post_trasferimento_morto, cc.cc_riconsegna, cc.destinazione_animale, cc.dimissioni_entered_by, cc.dimissioni_entered, cc.progressivo, cc.entered, cc.modified, cc.entered_by , cc.modified_by, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
				   + " cc.data_apertura as dataApertura, cc.id as id, cc.numero, fs.id as fascicolo_id, "
				   + " acc.id as accettazione_id "
				   + " from cartella_clinica cc, fascicolo_sanitario fs, accettazione acc, animale an, utenti_ ut "
				   + " where cc.id = ? and cc.trashed_date is null and cc.fascicolo_sanitario = fs.id and "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id and "
				   + "       ut.id = cc.entered_by ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			cc = new CartellaClinicaNoH();
			cc = (CartellaClinicaNoH) Bean.populate(cc, rs);
			cc.setCcMorto(rs.getBoolean("ccMorto"));
			cc.setProgressivo(rs.getInt("progressivo"));
			cc.setDataApertura(rs.getDate("dataApertura"));
			cc.setDataChiusura(rs.getDate("dataChiusura"));
			cc.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by"), connection));
			cc.setModified(rs.getDate("modified"));
			cc.setEntered(rs.getDate("entered"));
			cc.setModifiedBy(UtenteDAO.getUtenteAll(rs.getInt("modified_by"), connection));
			cc.setFascicoloSanitario(getFascicoloSanitario(connection, rs.getInt("fascicolo_id")));
			cc.setAccettazione(getAccettazione(connection, rs.getInt("accettazione_id")));
			cc.setDimissioniEntered(rs.getDate("dimissioni_entered"));
			cc.setDimissioniEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("dimissioni_entered_by"), connection));
			cc.setTrasferimenti(CartellaClinicaDAONoH.getTrasferimenti(cc.getId(), connection));
			cc.setTrasferimentiByCcPostTrasf(CartellaClinicaDAONoH.getTrasferimentiByCcPostTrasf(cc.getId(), connection));
			cc.setDestinazioneAnimale(LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, rs.getInt("destinazione_animale")));
			
			
			
			
			
			cc.setCcRiconsegna(rs.getBoolean("cc_riconsegna"));
			cc.setCcPostTrasferimento(rs.getBoolean("cc_post_trasferimento"));
			cc.setCcPostTrasferimentoMorto(rs.getBoolean("cc_post_trasferimento_morto"));
			cc.setTrasferimenti(CartellaClinicaDAONoH.getTrasferimenti(cc.getId(), connection));
			cc.setTrasferimentiByCcMortoPostTrasf(CartellaClinicaDAONoH.getTrasferimentiByCcPostTrasf(cc.getId(), connection));
			cc.setTrasferimentiByCcPostTrasf(CartellaClinicaDAONoH.getTrasferimentiByCcPostTrasf(cc.getId(), connection));
			cc.setTrasferimentiByCcPostRiconsegna(CartellaClinicaDAONoH.getTrasferimentiByCcPostRiconsegna(cc.getId(), connection));
		}
		return cc;
	}
	
	public static int getDestinazioneAnimale( int idCc, Connection connection) throws SQLException
	{
		int id = 0;
		String sql = " select destinazione_animale from cartella_clinica cc " + 
		             " where cc.id = ? and cc.trashed_date is null ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			id = rs.getInt("destinazione_animale");
		}
		return id;
	}
	
	public static CartellaClinicaNoH getCc( Connection connection,String numero, int idClinica) throws SQLException
	{
		CartellaClinicaNoH cc = null;
		String sql = " select cc.progressivo, cc.entered, cc.modified, cc.entered_by , cc.modified_by, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
				   + " cc.data_apertura as dataApertura, cc.id as id, cc.numero, fs.id as fascicolo_id, "
				   + " acc.id as accettazione_id "
				   + " from cartella_clinica cc, fascicolo_sanitario fs, accettazione acc, animale an, utenti_ ut "
				   + " where cc.numero = ? and ut.clinica = ? and cc.trashed_date is null and cc.fascicolo_sanitario = fs.id and "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id and "
				   + "       ut.id = cc.entered_by ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, numero);
		st.setInt(2, idClinica);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			cc = new CartellaClinicaNoH();
			cc = (CartellaClinicaNoH) Bean.populate(cc, rs);
			cc.setFascicoloSanitario(getFascicoloSanitario(connection, rs.getInt("fascicolo_id")));
			cc.setAccettazione(getAccettazione(connection, rs.getInt("accettazione_id")));
			cc.setDataApertura(rs.getDate("dataApertura"));
			cc.setDataChiusura(rs.getDate("dataChiusura"));
			cc.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by"), connection));
			cc.setModified(rs.getDate("modified"));
			cc.setEntered(rs.getDate("entered"));
			cc.setModifiedBy(UtenteDAO.getUtenteAll(rs.getInt("modified_by"), connection));
			cc.setProgressivo(rs.getInt("progressivo"));
			cc.setCcMorto(rs.getBoolean("ccMorto"));
		}
		return cc;
	}
	
	public static ArrayList<CartellaClinicaNoH> getCcs( Connection connection,String mc, int idClinica) throws SQLException
	{
		ArrayList<CartellaClinicaNoH> ccs = new ArrayList<CartellaClinicaNoH>();
		String sql = " select cc.progressivo, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
				   + " cc.data_apertura as dataApertura, cc.id as id, cc.numero, fs.id as fascicolo_id, "
				   + " acc.id as accettazione_id "
				   + " from cartella_clinica cc, fascicolo_sanitario fs, accettazione acc, animale an, utenti_ ut "
				   + " where an.identificativo = ? and ut.clinica = ? and cc.trashed_date is null and cc.fascicolo_sanitario = fs.id and "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id and "
				   + "       ut.id = cc.entered_by ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, mc);
		st.setInt(2, idClinica);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			CartellaClinicaNoH cc = new CartellaClinicaNoH();
			cc = (CartellaClinicaNoH) Bean.populate(cc, rs);
			cc.setDataApertura(rs.getDate("dataApertura"));
			cc.setDataChiusura(rs.getDate("dataChiusura"));
			cc.setCcMorto(rs.getBoolean("ccMorto"));
			cc.setFascicoloSanitario(getFascicoloSanitario(connection, rs.getInt("fascicolo_id")));
			cc.setAccettazione(getAccettazione(connection, rs.getInt("accettazione_id")));
			cc.setProgressivo(rs.getInt("progressivo"));
			ccs.add(cc);
		}
		return ccs;
	}
	
	public static ArrayList<CartellaClinicaNoH> getCcs( Connection connection,int idAnimale) throws SQLException
	{
		ArrayList<CartellaClinicaNoH> ccs = new ArrayList<CartellaClinicaNoH>();
		String sql = " select cc.destinazione_animale, cc.progressivo, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
				   + " cc.data_apertura as dataApertura, cc.id as id, cc.numero, fs.id as fascicolo_id, "
				   + " acc.id as accettazione_id "
				   + " from cartella_clinica cc, fascicolo_sanitario fs, accettazione acc, animale an, utenti_ ut "
				   + " where an.id = ? and cc.trashed_date is null and cc.fascicolo_sanitario = fs.id and "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id and "
				   + "       ut.id = cc.entered_by ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAnimale);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			CartellaClinicaNoH cc = new CartellaClinicaNoH();
			cc = (CartellaClinicaNoH) Bean.populate(cc, rs);
			cc.setAttivitaBdrs(CartellaClinicaDAONoH.getAttivitaBdrsNoH(cc.getId(), connection));
			cc.setDataApertura(rs.getDate("dataApertura"));
			cc.setDataChiusura(rs.getDate("dataChiusura"));
			cc.setCcMorto(rs.getBoolean("ccMorto"));
			cc.setFascicoloSanitario(getFascicoloSanitario(connection, rs.getInt("fascicolo_id")));
			cc.setAccettazione(getAccettazione(connection, rs.getInt("accettazione_id")));
			cc.setProgressivo(rs.getInt("progressivo"));
			if(rs.getInt("destinazione_animale")>0)
				cc.setDestinazioneAnimale(LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, rs.getInt("destinazione_animale")));
			ccs.add(cc);
		}
		return ccs;
	}
	
	
	public static ArrayList<CartellaClinicaNoH> getCcs( Connection connection,int idClinica, Date dataInizio, Date dataFine) throws SQLException
	{
		ArrayList<CartellaClinicaNoH> ccs = new ArrayList<CartellaClinicaNoH>();
		String sql = " select cc.progressivo, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
				   + " cc.data_apertura as dataApertura, cc.id as id, cc.numero, fs.id as fascicolo_id, "
				   + " acc.id as accettazione_id "
				   + " from cartella_clinica cc, fascicolo_sanitario fs, accettazione acc, animale an, utenti_ ut "
				   + " where cc.data_apertura >= ? and cc.data_apertura <= ? and ut.clinica = ? and cc.trashed_date is null and cc.fascicolo_sanitario = fs.id and "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id and "
				   + "       ut.id = cc.entered_by "
				   + " order by cc.id desc "
				   + " limit 1 ";
		
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setDate(1, new java.sql.Date(dataInizio.getTime()));
		st.setDate(2, new java.sql.Date(dataFine.getTime()));
		st.setInt(3, idClinica);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			CartellaClinicaNoH cc = new CartellaClinicaNoH();
			cc = (CartellaClinicaNoH) Bean.populate(cc, rs);
			cc.setFascicoloSanitario(getFascicoloSanitario(connection, rs.getInt("fascicolo_id")));
			cc.setAccettazione(getAccettazione(connection, rs.getInt("accettazione_id")));
			cc.setProgressivo(rs.getInt("progressivo"));
			cc.setCcMorto(rs.getBoolean("ccMorto"));
			ccs.add(cc);
		}
		return ccs;
	}
	
	
	
	public static FascicoloSanitario getFascicoloSanitario( Connection connection, int id) throws SQLException
	{
		FascicoloSanitario fs = null;
		String sql = " select id, numero from fascicolo_sanitario where id = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			fs = new FascicoloSanitario();
			fs = (FascicoloSanitario) Bean.populate(fs, rs);
			fs.setId(rs.getInt("id"));
		}
		return fs;
	}
	
	
	public static AccettazioneNoH getAccettazione( Connection connection, int id) throws SQLException
	{
		AccettazioneNoH acc = null;
		String sql = " select acc.progressivo, acc.data, acc.id, acc.animale as animale_id, acc.entered_by, "
				+    " acc.proprietario_nome, acc.proprietario_cognome, acc.proprietario_tipo, acc.proprietario_codice_fiscale , "
				+    " acc.proprietario_documento, acc.proprietario_cap, acc.proprietario_provincia, acc.proprietario_comune , "
				+    " acc.proprietario_indirizzo , acc.proprietario_telefono"
				+ "    from accettazione acc, utenti_ u "
				+ "where u.id = acc.entered_by and acc.id = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			acc = new AccettazioneNoH();
			acc = (AccettazioneNoH) Bean.populate(acc, rs);
			acc.setData(rs.getDate("data"));
			acc.setAnimale(getAnimale(connection,rs.getInt("animale_id")));
			acc.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by"),connection));
			acc.setProgressivo(rs.getInt("progressivo"));
			acc.setProprietarioNome(rs.getString("proprietario_nome"));
			acc.setProprietarioCognome(rs.getString("proprietario_cognome"));
			acc.setProprietarioTipo(rs.getString("proprietario_tipo"));
			acc.setProprietarioCodiceFiscale(rs.getString("proprietario_codice_fiscale"));
			acc.setProprietarioDocumento(rs.getString("proprietario_documento"));
			acc.setProprietarioCap(rs.getString("proprietario_cap"));
			acc.setProprietarioProvincia(rs.getString("proprietario_provincia"));
			acc.setProprietarioComune(rs.getString("proprietario_comune"));
			acc.setProprietarioIndirizzo(rs.getString("proprietario_indirizzo"));
			acc.setProprietarioTelefono(rs.getString("proprietario_telefono"));
		}
		return acc;
	}
	
	public static AnimaleNoH getAnimale( Connection connection, int id) throws SQLException
	{
		AnimaleNoH an = null;
		String sql = " select an.data_nascita, eta.description as descrizione_eta, eta.id as id_eta, an.sesso, an.razza_sinantropo, " +   
		             " an.specie_sinantropo, an.data_nascita, an.deceduto_non_anagrafe, an.id, an.identificativo, " + 
				     " an.specie as lookup_specie_id " + 
		             " from animale an " + 
				     " left join lookup_sinantropi_eta eta on eta.id = an.eta "
				     + " where an.id = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			an = new AnimaleNoH();
			an = (AnimaleNoH) Bean.populate(an, rs);
			an.setDataNascita(rs.getDate("data_nascita"));
			an.setDecedutoNonAnagrafe(rs.getBoolean("deceduto_non_anagrafe"));
			an.setLookupSpecie(LookupSpecieDAO.getSpecie(rs.getInt("lookup_specie_id"), connection));
			an.setSpecieSinantropo(rs.getString("specie_sinantropo"));
			an.setRazzaSinantropo(rs.getString("razza_sinantropo"));
			an.setSesso(rs.getString("sesso"));
			
			LookupSinantropiEta eta = new LookupSinantropiEta();
			eta.setId(rs.getInt("id_eta"));
			eta.setDescription(rs.getString("descrizione_eta"));
			an.setEta(eta);
			
			an.setDataNascita(rs.getDate("data_nascita"));
		}
		return an;
	}
	
	
	public static HashSet<Diagnosi> getDiagnosis( Connection connection,int idCc) throws SQLException
	{
		HashSet<Diagnosi> diagnosi = new HashSet<Diagnosi>();
		String sql = " select id, data_diagnosi as dataDiagnosi from diagnosi where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Diagnosi d = new Diagnosi();
			d = (Diagnosi) Bean.populate(d, rs);
			d.setDataDiagnosi(rs.getDate("dataDiagnosi"));
			d.setDiagnosiEffettuate(DiagnosiDAO.getDiagnosiEffettuate(connection, rs.getInt("id")));
			diagnosi.add(d);
		}
		return diagnosi;
	}
	
	
	public static HashSet<TrasferimentoNoH> getTrasferimenti( int idCc, Connection connection) throws SQLException
	{
		HashSet<TrasferimentoNoH> trasf = new HashSet<TrasferimentoNoH>();
		String sql = " select u.id as id_utente,u.nome as nome, u.cognome as cognome,  t.data_richiesta, t.clinica_origine, t.id, t.clinica_destinazione, t.automatico_necroscopia FROM TRASFERIMENTO t, utenti_ u "
				+ " where t.cartella_clinica = ? and u.id = t.entered_by";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			TrasferimentoNoH t = new TrasferimentoNoH();
			t.setId( rs.getInt("id"));
			t.setDataRichiesta(rs.getDate("data_richiesta"));
			t.setOperazioniRichieste(LookupOperazioniAccettazioneDAO.getOperazioniByIdTrasferimento(connection,rs.getInt("id")));
			t.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));
			t.setClinicaOrigine(ClinicaDAONoH.getClinica(rs.getInt("clinica_origine"), connection));
			t.setClinicaDestinazione(ClinicaDAONoH.getClinica(rs.getInt("clinica_destinazione"), connection));
			t.setCartellaClinica(getCcAnimale(connection, idCc));	
			BUtente u = new BUtente();
			u.setId(rs.getInt("id_utente"));
			u.setNome(rs.getString("nome"));
			u.setCognome(rs.getString("cognome"));
			t.setEnteredBy(u);
			trasf.add(t);
		}
		return trasf;
	}
	
	public static ArrayList<EsameObiettivo> getEsameObiettivos(int idCc, Connection connection) throws SQLException
	{
		ArrayList<EsameObiettivo> esami = new ArrayList<EsameObiettivo>();
		String sql = " select tipo, patologie_congenite, altro, entered, modified, entered_by, modified_by, id, data_esame, normale, note "
				+ "    from esame_obiettivo "
				+ "    where cartella_clinica = ? and trashed_date is null ";
		
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt (   1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EsameObiettivo eo = new EsameObiettivo();
			eo.setAltro(rs.getString("altro"));
			eo.setDataEsameObiettivo(rs.getDate("data_esame"));
			eo.setEntered(rs.getDate("entered"));
			eo.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by"), connection));
			eo.setModifiedBy(UtenteDAO.getUtenteAll(rs.getInt("modified_by"), connection));
			eo.setId(rs.getInt("id"));
			eo.setModified(rs.getDate("modified"));
			eo.setNormale(rs.getBoolean("normale"));
			eo.setNote(rs.getString("note"));
			eo.setPatologieCongenite(rs.getString("patologie_congenite"));
			LookupEsameObiettivoTipo eotipo = LookupEsameObiettivoTipoDAO.getTipo(rs.getInt("tipo"),connection);
			eo.setLookupEsameObiettivoTipo(eotipo);
			eo.setEsameObiettivoEsitos(EsameObiettivoDAO.getEsameObiettivoEsitos(eo,connection));
			esami.add(eo);
		}
		return esami;
	}
	
	public static Set<EsameObiettivo> getEsamiObiettivoApparato(LookupEsameObiettivoApparati apparato, ArrayList<EsameObiettivo> esameObiettivos) {
		Set<EsameObiettivo> ret = new HashSet<EsameObiettivo>();

		for (EsameObiettivo eo : esameObiettivos) {
			if ((eo.getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati()!=null && eo.getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati().getId() == apparato.getId()) ||
					(eo.getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati()==null && apparato==null)) {
				ret.add(eo);
			}
		}

		return ret;
	}
	
	
	public static HashSet<AttivitaBdr> getAttivitaBdrs( int idCc, Connection connection) throws SQLException
	{
		HashSet<AttivitaBdr> atts = new HashSet<AttivitaBdr>();
		String sql = " select att.id as idAtt,att.tipo_operazione, op.id as idOp, op.inbdr, att.id_registrazione_bdr "
				+ " from attivita_bdr att "
				+ " left join lookup_operazioni_accettazione op on op.id = att.tipo_operazione"
				+ " where att.cc = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			AttivitaBdr att = new AttivitaBdr();
			att.setIdRegistrazioneBdr( rs.getInt("id_registrazione_bdr"));
			att.setId(rs.getInt("idAtt"));
			att.setOperazioneBdr(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(rs.getInt("tipo_operazione"), connection));
			
			LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
			op.setId( rs.getInt("idOp"));
			op.setInbdr( rs.getBoolean("inbdr"));
			att.setOperazioneBdr(op);

			atts.add(att);
		}
		return atts;
	}
	
	public static HashSet<AttivitaBdrNoH> getAttivitaBdrsNoH( int idCc, Connection connection) throws SQLException
	{
		HashSet<AttivitaBdrNoH> atts = new HashSet<AttivitaBdrNoH>();
		String sql = " select att.id as idAtt,att.tipo_operazione, op.id as idOp, op.inbdr, att.id_registrazione_bdr "
				+ " from attivita_bdr att "
				+ " left join lookup_operazioni_accettazione op on op.id = att.tipo_operazione"
				+ " where att.cc = ? and att.trashed_date is null ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			AttivitaBdrNoH att = new AttivitaBdrNoH();
			att.setIdRegistrazioneBdr( rs.getInt("id_registrazione_bdr"));
			att.setId(rs.getInt("idAtt"));
			att.setOperazioneBdr(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(rs.getInt("tipo_operazione"), connection));
			
			LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
			op.setId( rs.getInt("idOp"));
			op.setInbdr( rs.getBoolean("inbdr"));
			att.setOperazioneBdr(op);

			atts.add(att);
		}
		return atts;
	}
	
	
	public static HashSet<TrasferimentoNoH> getTrasferimentiByCcPostTrasf( int idCc, Connection connection) throws SQLException
	{
		HashSet<TrasferimentoNoH> trasf = new HashSet<TrasferimentoNoH>();
		String sql = " select t.nota_destinatario, t.nota_criuv, t.nota_richiesta, t.nota_approvazione_riconsegna, t.nota_riconsegna, t.data_riconsegna, t.data_rifiuto_riconsegna, t.urgenza, t.data_accettazione_destinatario, t.data_accettazione_criuv, " +
			         " t.data_rifiuto_criuv, t.id, t.clinica_origine, t.automatico_necroscopia, t.clinica_destinazione " +
				     " FROM TRASFERIMENTO t " +
				     " where t.cartella_clinica_destinatario = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			TrasferimentoNoH t = new TrasferimentoNoH();
			t.setId( rs.getInt("id"));
			t.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));
			t.setClinicaOrigine(TrasferimentoDAO.getClinica( rs.getInt("clinica_origine"), connection ) );
			t.setDataRiconsegna(rs.getDate("data_riconsegna"));
			t.setNotaApprovazioneRiconsegna(rs.getString("nota_approvazione_riconsegna"));
			t.setNotaRiconsegna(rs.getString("nota_riconsegna"));
			t.setDataRifiutoRiconsegna(rs.getDate("data_rifiuto_riconsegna"));
			t.setUrgenza(rs.getBoolean("urgenza"));
			t.setNotaDestinatario(rs.getString("nota_destinatario"));
			t.setNotaCriuv(rs.getString("nota_criuv"));
			t.setNotaRichiesta(rs.getString("nota_richiesta"));
			t.setDataAccettazioneDestinatario(rs.getDate("data_accettazione_destinatario"));
			t.setDataAccettazioneCriuv(rs.getDate("data_accettazione_criuv"));
			t.setDataRifiutoCriuv(rs.getDate("data_rifiuto_criuv"));
			t.setClinicaDestinazione(TrasferimentoDAO.getClinica( rs.getInt("clinica_destinazione"), connection ) );
			trasf.add(t);
		}
		return trasf;
	}
	
	
	public static HashSet<TrasferimentoNoH> getTrasferimentiByCcPostRiconsegna( int idCc, Connection connection) throws SQLException
	{
		HashSet<TrasferimentoNoH> trasf = new HashSet<TrasferimentoNoH>();
		String sql = " select t.nota_destinatario, t.nota_criuv, t.nota_richiesta, t.nota_approvazione_riconsegna, t.nota_riconsegna, t.data_riconsegna, t.data_rifiuto_riconsegna, t.urgenza, t.data_accettazione_destinatario, t.data_accettazione_criuv, " +
			         " t.data_rifiuto_criuv, t.id, t.clinica_origine, t.automatico_necroscopia, t.clinica_destinazione " +
				     " FROM TRASFERIMENTO t " +
				     " where t.cartella_clinica_mittente_riconsegna = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			TrasferimentoNoH t = new TrasferimentoNoH();
			t.setId( rs.getInt("id"));
			t.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));
			t.setClinicaOrigine(TrasferimentoDAO.getClinica( rs.getInt("clinica_origine"), connection ) );
			t.setDataRiconsegna(rs.getDate("data_riconsegna"));
			t.setNotaApprovazioneRiconsegna(rs.getString("nota_approvazione_riconsegna"));
			t.setNotaRiconsegna(rs.getString("nota_riconsegna"));
			t.setDataRifiutoRiconsegna(rs.getDate("data_rifiuto_riconsegna"));
			t.setUrgenza(rs.getBoolean("urgenza"));
			t.setNotaDestinatario(rs.getString("nota_destinatario"));
			t.setNotaRichiesta(rs.getString("nota_richiesta"));
			t.setNotaCriuv(rs.getString("nota_criuv"));
			t.setDataAccettazioneDestinatario(rs.getDate("data_accettazione_destinatario"));
			t.setDataAccettazioneCriuv(rs.getDate("data_accettazione_criuv"));
			t.setDataRifiutoCriuv(rs.getDate("data_rifiuto_criuv"));
			t.setClinicaDestinazione(TrasferimentoDAO.getClinica( rs.getInt("clinica_destinazione"), connection ) );
			trasf.add(t);
		}
		return trasf;
	}
	
	public static HashSet<TrasferimentoNoH> getTrasferimentiByCcMortoPostTrasf( int idCc, Connection connection) throws SQLException
	{
		HashSet<TrasferimentoNoH> trasf = new HashSet<TrasferimentoNoH>();
		String sql = " select t.nota_destinatario, t.nota_criuv, t.nota_richiesta, t.nota_approvazione_riconsegna, t.nota_riconsegna, t.data_riconsegna, t.data_rifiuto_riconsegna, t.urgenza, t.data_accettazione_destinatario, t.data_accettazione_criuv, " +
			         " t.data_rifiuto_criuv, t.id, t.clinica_origine, t.automatico_necroscopia, t.clinica_destinazione " +
				     " FROM TRASFERIMENTO t " +
				     " where t.cartella_clinica_morto_destinatario = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			TrasferimentoNoH t = new TrasferimentoNoH();
			t.setId( rs.getInt("id"));
			t.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));
			t.setClinicaOrigine(TrasferimentoDAO.getClinica( rs.getInt("clinica_origine"), connection ) );
			t.setDataRiconsegna(rs.getDate("data_riconsegna"));
			t.setNotaApprovazioneRiconsegna(rs.getString("nota_approvazione_riconsegna"));
			t.setNotaRiconsegna(rs.getString("nota_riconsegna"));
			t.setNotaRichiesta(rs.getString("nota_richiesta"));
			t.setNotaDestinatario(rs.getString("nota_destinatario"));
			t.setDataRifiutoRiconsegna(rs.getDate("data_rifiuto_riconsegna"));
			t.setUrgenza(rs.getBoolean("urgenza"));
			t.setNotaCriuv(rs.getString("nota_criuv"));
			t.setDataAccettazioneDestinatario(rs.getDate("data_accettazione_destinatario"));
			t.setDataAccettazioneCriuv(rs.getDate("data_accettazione_criuv"));
			t.setDataRifiutoCriuv(rs.getDate("data_rifiuto_criuv"));
			t.setClinicaDestinazione(TrasferimentoDAO.getClinica( rs.getInt("clinica_destinazione"), connection ) );
			trasf.add(t);
		}
		return trasf;
	}
	
	private static CartellaClinicaNoH getCcAnimale( Connection connection,int id) throws SQLException
	{
		CartellaClinicaNoH cc = null;
		String sql = " select cc.id, cc.numero, an.identificativo "
				   + " from cartella_clinica cc, accettazione acc, animale an "
				   + " where cc.id = ? and cc.trashed_date is null and  "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			cc = new CartellaClinicaNoH();
			cc.setNumero(rs.getString("numero"));
			cc.setId(rs.getInt("id"));
			cc.setIdentificativoAnimale(rs.getString("identificativo"));
		}
		return cc;
	}
	
	
	public static void chiudi(CartellaClinicaNoH cc, Connection connection) throws SQLException
	{
		String sql = "update cartella_clinica set destinazione_animale = ?, modified = ?, modified_by = ?, adozione_fuori_asl = ?, adozione_verso_assoc_canili = ?, dimissioni_entered = ?, dimissioni_entered_by = ? where id = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt		( 1, cc.getDestinazioneAnimale().getId());
		st.setDate		( 2, new java.sql.Date(cc.getModified().getTime()));
		st.setInt		( 3, cc.getModifiedBy().getId());
		st.setBoolean	( 4, cc.getAdozioneFuoriAsl());
		st.setBoolean	( 5, cc.getAdozioneVersoAssocCanili());
		st.setDate		( 6, new java.sql.Date(cc.getDimissioniEntered().getTime()));
		st.setInt		( 7, cc.getDimissioniEnteredBy().getId());
		st.setInt		( 8, cc.getId());
		st.executeUpdate();
		st.close();
	}
	
	
}
