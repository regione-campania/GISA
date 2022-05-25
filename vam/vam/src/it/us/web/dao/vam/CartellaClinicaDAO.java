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
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.Ecg;
import it.us.web.bean.vam.EcoAddome;
import it.us.web.bean.vam.EcoCuore;
import it.us.web.bean.vam.Ehrlichiosi;
import it.us.web.bean.vam.EsameCitologico;
import it.us.web.bean.vam.EsameCoprologico;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.EsameObiettivo;
import it.us.web.bean.vam.EsameSangue;
import it.us.web.bean.vam.EsameUrine;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.bean.vam.Felv;
import it.us.web.bean.vam.Fip;
import it.us.web.bean.vam.Fiv;
import it.us.web.bean.vam.Leishmaniosi;
import it.us.web.bean.vam.Rabbia;
import it.us.web.bean.vam.Rickettsiosi;
import it.us.web.bean.vam.Rx;
import it.us.web.bean.vam.Tac;
import it.us.web.bean.vam.Toxoplasmosi;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAutopsiaPatologiePrevalenti;
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
import it.us.web.dao.lookup.LookupAutopsiaPatologiePrevalentiDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
import it.us.web.dao.lookup.LookupEsameIstopatologicoSedeLesioneDAO;
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

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.jmesa.limit.RowSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartellaClinicaDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( CartellaClinicaDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static ArrayList<CartellaClinica> getCartelleCliniche( Connection connection, RowSelect rowSelect, 
			boolean tipologiaFiltroMorto1, boolean tipologiaFiltroMorto2, boolean tipologiaFiltroDayHospital1, boolean tipologiaFiltroDayHospital2, 
			String fsFiltro, String dataAperturaFiltroInizio,String dataAperturaFiltroFine,
			String dataChiusuraFiltroInizio,String dataChiusuraFiltroFine,String mcFiltro, int idClinica,String numCcFiltro,String orderFilter) throws SQLException
	{
		ArrayList<CartellaClinica> cartelle = new ArrayList<CartellaClinica>();
		String sql = " select cc.entered_by, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, cc.data_apertura as dataApertura, cc.id as id, cc.numero, fs.id as fascicolo_id, acc.id as accettazione_id from cartella_clinica cc, fascicolo_sanitario fs, accettazione acc, animale an, utenti ut "
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
			CartellaClinica cc = new CartellaClinica();
			cc = (CartellaClinica) Bean.populate(cc, rs);
			cc.setDayHospital(rs.getBoolean("dayHospital"));
			cc.setDataApertura(rs.getDate("dataApertura"));
			cc.setDataChiusura(rs.getDate("dataChiusura"));
			cc.setFascicoloSanitario(getFascicoloSanitario(connection, rs.getInt("fascicolo_id")));
			cc.setAccettazione(getAccettazione(connection, rs.getInt("accettazione_id")));
			cc.setDiagnosis(getDiagnosis(connection,rs.getInt("id")));
			cc.setCcMorto(rs.getBoolean("ccMorto"));
			cc.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by")));
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
	
	public static CartellaClinica getCc( Connection connection,int id) throws SQLException
	{
		CartellaClinica cc = null;
		String sql = " select cc.adozione_verso_assoc_canili, cc.adozione_fuori_asl, cc.dimissioni_entered_by, cc.dimissioni_entered, cc.progressivo, cc.entered, cc.modified, cc.entered_by , cc.modified_by, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
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
			cc = new CartellaClinica();
			cc = (CartellaClinica) Bean.populate(cc, rs);
			cc.setDayHospital(rs.getBoolean("dayHospital"));
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
			cc.setAdozioneFuoriAsl(rs.getBoolean("adozione_fuori_asl"));
			cc.setAdozioneVersoAssocCanili(rs.getBoolean("adozione_verso_assoc_canili"));
		}
		return cc;
	}
	
	public static CartellaClinica getCcDaIsto( Connection connection,int id) throws SQLException
	{
		CartellaClinica cc = null;
		String sql = " select cc.dimissioni_entered_by, cc.dimissioni_entered, cc.progressivo, cc.entered, cc.modified, cc.entered_by , cc.modified_by, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
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
			cc = new CartellaClinica();
			cc = (CartellaClinica) Bean.populate(cc, rs);
			cc.setDayHospital(rs.getBoolean("dayHospital"));
			cc.setCcMorto(rs.getBoolean("ccMorto"));
			cc.setProgressivo(rs.getInt("progressivo"));
			cc.setDataApertura(rs.getDate("dataApertura"));
			cc.setDataChiusura(rs.getDate("dataChiusura"));
			cc.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by"), connection));
			cc.setModified(rs.getDate("modified"));
			cc.setEntered(rs.getDate("entered"));
			cc.setModifiedBy(UtenteDAO.getUtenteAll(rs.getInt("modified_by"), connection));
			cc.setFascicoloSanitario(getFascicoloSanitario(connection, rs.getInt("fascicolo_id")));
			cc.setAccettazione(AccettazioneDAO.getAccettazione(rs.getInt("accettazione_id"), connection));
			cc.setDimissioniEntered(rs.getDate("dimissioni_entered"));
			cc.setDimissioniEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("dimissioni_entered_by"), connection));
			cc.setEsameIstopatologicos(getEsamiIsto(connection, rs.getInt("id")));
			cc.setEsameCitologicos(getEsamiCitologico(connection, rs.getInt("id")));
			cc.setEsameCoprologicos(getEsamiCoprologico(connection, rs.getInt("id")));
			cc.setEsameObiettivos(getEsamiObiettivo(connection, rs.getInt("id")));
			cc.setEsameSangues(getEsamiSangue(connection, rs.getInt("id")));
			cc.setEsamiUrine(getEsamiUrine(connection, rs.getInt("id")));
			cc.setEcgs(getEcgs(connection, rs.getInt("id")));
			cc.setEcoAddomes(getEcoAddomes(connection, rs.getInt("id")));
			cc.setEcoCuores(getEcoCuores(connection, rs.getInt("id")));
			cc.setEhrlichiosis(getEhrlichiosis(connection, rs.getInt("id")));
			cc.setFelvs(getFelvs(connection, rs.getInt("id")));
			cc.setFips(getFips(connection, rs.getInt("id")));
			cc.setFivs(getFivs(connection, rs.getInt("id")));
			cc.setLeishmaniosis(getLeishmaniosis(connection, rs.getInt("id")));
			cc.setRabbias(getRabbias(connection, rs.getInt("id")));
			cc.setRickettsiosis(getRickettsiosis(connection, rs.getInt("id")));
			cc.setRxes(getRxes(connection, rs.getInt("id")));
			cc.setTacs(getTacs(connection, rs.getInt("id")));
			cc.setToxoplasmosis(getToxoplasmosis(connection, rs.getInt("id")));
		}
		return cc;
	}
	
	private static HashSet<EsameIstopatologico> getEsamiIsto( Connection connection, int idCc) throws SQLException
	{
		HashSet<EsameIstopatologico> esami = new HashSet<EsameIstopatologico>();
		String sql = " select sede_lesione, data_richiesta,data_esito, id from esame_istopatologico where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EsameIstopatologico esame = new EsameIstopatologico();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esame.setSedeLesione(LookupEsameIstopatologicoSedeLesioneDAO.getSede(rs.getInt("sede_lesione"), connection));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<EsameCitologico> getEsamiCitologico( Connection connection, int idCc) throws SQLException
	{
		HashSet<EsameCitologico> esami = new HashSet<EsameCitologico>();
		String sql = " select data_richiesta,data_esito, id from esame_citologico where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EsameCitologico esame = new EsameCitologico();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<EsameCoprologico> getEsamiCoprologico( Connection connection, int idCc) throws SQLException
	{
		HashSet<EsameCoprologico> esami = new HashSet<EsameCoprologico>();
		String sql = " select data_richiesta,data_esito, id from esame_coprologico where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EsameCoprologico esame = new EsameCoprologico();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<EsameObiettivo> getEsamiObiettivo( Connection connection, int idCc) throws SQLException
	{
		HashSet<EsameObiettivo> esami = new HashSet<EsameObiettivo>();
		String sql = " select  normale, tipo, data_esame, id from esame_obiettivo where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EsameObiettivo esame = new EsameObiettivo();
			esame.setId(rs.getInt("id"));
			esame.setDataEsameObiettivo(rs.getDate("data_esame"));
			esame.setLookupEsameObiettivoTipo(LookupEsameObiettivoTipoDAO.getTipo(rs.getInt("tipo"), connection));
			esami.add(esame);
			esame.setNormale(rs.getBoolean("normale"));
		}
		return esami;
	}
	
	private static HashSet<EsameSangue> getEsamiSangue( Connection connection, int idCc) throws SQLException
	{
		HashSet<EsameSangue> esami = new HashSet<EsameSangue>();
		String sql = " select  data_richiesta,data_esito, id from esame_sangue where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EsameSangue esame = new EsameSangue();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	
	private static HashSet<Ecg> getEcgs( Connection connection, int idCc) throws SQLException
	{
		HashSet<Ecg> esami = new HashSet<Ecg>();
		String sql = " select  data_richiesta,data_esito, id from ecg where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Ecg esame = new Ecg();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<EcoAddome> getEcoAddomes( Connection connection, int idCc) throws SQLException
	{
		HashSet<EcoAddome> esami = new HashSet<EcoAddome>();
		String sql = " select  data_richiesta,data_esito, id from eco_addome where id_cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EcoAddome esame = new EcoAddome();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<EcoCuore> getEcoCuores( Connection connection, int idCc) throws SQLException
	{
		HashSet<EcoCuore> esami = new HashSet<EcoCuore>();
		String sql = " select  data_richiesta,data_esito, id from eco_cuore where id_cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EcoCuore esame = new EcoCuore();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Felv> getFelvs( Connection connection, int idCc) throws SQLException
	{
		HashSet<Felv> esami = new HashSet<Felv>();
		String sql = " select  data_richiesta,data_esito, id from felv where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Felv esame = new Felv();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Ehrlichiosi> getEhrlichiosis( Connection connection, int idCc) throws SQLException
	{
		HashSet<Ehrlichiosi> esami = new HashSet<Ehrlichiosi>();
		String sql = " select  data_richiesta,data_esito, id from ehrlichiosi where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Ehrlichiosi esame = new Ehrlichiosi();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Fip> getFips( Connection connection, int idCc) throws SQLException
	{
		HashSet<Fip> esami = new HashSet<Fip>();
		String sql = " select  data_richiesta,data_esito, id from fip where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Fip esame = new Fip();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Fiv> getFivs( Connection connection, int idCc) throws SQLException
	{
		HashSet<Fiv> esami = new HashSet<Fiv>();
		String sql = " select  data_richiesta,data_esito, id from fiv where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Fiv esame = new Fiv();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Leishmaniosi> getLeishmaniosis( Connection connection, int idCc) throws SQLException
	{
		HashSet<Leishmaniosi> esami = new HashSet<Leishmaniosi>();
		String sql = " select  data_esito_leishmaniosi,data_prelievo_leishmaniosi, id from leishmaniosi where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Leishmaniosi esame = new Leishmaniosi();
			esame.setId(rs.getInt("id"));
			esame.setDataEsitoLeishmaniosi(rs.getDate("data_esito_leishmaniosi"));
			esame.setDataPrelievoLeishmaniosi(rs.getDate("data_prelievo_leishmaniosi"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Rabbia> getRabbias( Connection connection, int idCc) throws SQLException
	{
		HashSet<Rabbia> esami = new HashSet<Rabbia>();
		String sql = " select  data_richiesta,data_esito, id from rabbia where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Rabbia esame = new Rabbia();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Rickettsiosi> getRickettsiosis( Connection connection, int idCc) throws SQLException
	{
		HashSet<Rickettsiosi> esami = new HashSet<Rickettsiosi>();
		String sql = " select  data_richiesta,data_esito, id from rickettsiosi where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Rickettsiosi esame = new Rickettsiosi();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Rx> getRxes( Connection connection, int idCc) throws SQLException
	{
		HashSet<Rx> esami = new HashSet<Rx>();
		String sql = " select  data_richiesta,data_esito, id from rx where id_cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Rx esame = new Rx();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Tac> getTacs( Connection connection, int idCc) throws SQLException
	{
		HashSet<Tac> esami = new HashSet<Tac>();
		String sql = " select  data_richiesta,data_esito, id from tac where id_cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Tac esame = new Tac();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<Toxoplasmosi> getToxoplasmosis( Connection connection, int idCc) throws SQLException
	{
		HashSet<Toxoplasmosi> esami = new HashSet<Toxoplasmosi>();
		String sql = " select  data_richiesta,data_esito, id from toxoplasmosi where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Toxoplasmosi esame = new Toxoplasmosi();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
	}
	
	private static HashSet<EsameUrine> getEsamiUrine( Connection connection, int idCc) throws SQLException
	{
		HashSet<EsameUrine> esami = new HashSet<EsameUrine>();
		String sql = " select  data_richiesta,data_esito, id from esame_urine where cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EsameUrine esame = new EsameUrine();
			esame.setId(rs.getInt("id"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			esami.add(esame);
		}
		return esami;
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
	
	public static CartellaClinica getCc( Connection connection,String numero, int idClinica) throws SQLException
	{
		CartellaClinica cc = null;
		String sql = " select cc.dimissioni_entered, cc.dimissioni_entered_by, cc.adozione_verso_assoc_canili, cc.adozione_fuori_asl, cc.progressivo, cc.entered, cc.modified, cc.entered_by , cc.modified_by, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
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
			cc = new CartellaClinica();
			cc = (CartellaClinica) Bean.populate(cc, rs);
			cc.setDayHospital(rs.getBoolean("dayHospital"));
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
			cc.setAdozioneFuoriAsl(rs.getBoolean("adozione_fuori_asl"));
			cc.setAdozioneVersoAssocCanili(rs.getBoolean("adozione_verso_assoc_canili"));
			cc.setDimissioniEntered(rs.getDate("dimissioni_entered"));
			cc.setDimissioniEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("dimissioni_entered_by"), connection));
		
			
			
		}
		return cc;
	}
	
	public static ArrayList<CartellaClinica> getCcs( Connection connection,String mc, int idClinica) throws SQLException
	{
		ArrayList<CartellaClinica> ccs = new ArrayList<CartellaClinica>();
		String sql = " select cc.entered_by, cc.progressivo, cc.cc_morto as ccMorto, cc.day_hospital as dayHospital, cc.data_chiusura as dataChiusura, "
				   + " cc.data_apertura as dataApertura, cc.id as id, cc.numero, fs.id as fascicolo_id, "
				   + " acc.id as accettazione_id "
				   + " from cartella_clinica cc, fascicolo_sanitario fs, accettazione acc, animale an, utenti_ ut "
				   + " where an.identificativo = ? and (?=-1 or ut.clinica = ?) and cc.trashed_date is null and cc.fascicolo_sanitario = fs.id and "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id and "
				   + "       ut.id = cc.entered_by ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, mc);
		st.setInt(2, idClinica);
		st.setInt(3, idClinica);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			CartellaClinica cc = new CartellaClinica();
			cc = (CartellaClinica) Bean.populate(cc, rs);
			cc.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by")));
			cc.setDayHospital(rs.getBoolean("dayHospital"));
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
	
	
	public static ArrayList<CartellaClinica> getCcs( Connection connection,int idClinica, Date dataInizio, Date dataFine) throws SQLException
	{
		ArrayList<CartellaClinica> ccs = new ArrayList<CartellaClinica>();
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
			CartellaClinica cc = new CartellaClinica();
			cc = (CartellaClinica) Bean.populate(cc, rs);
			cc.setDayHospital(rs.getBoolean("dayHospital"));
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
		}
		return fs;
	}
	
	
	public static Accettazione getAccettazione( Connection connection, int id) throws SQLException
	{
		Accettazione acc = null;
		String sql = " select acc.richiedente_tipo, acc.progressivo, acc.data, acc.id, acc.animale as animale_id, acc.entered_by, "
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
			acc = new Accettazione();
			acc = (Accettazione) Bean.populate(acc, rs);
			acc.setData(rs.getDate("data"));
			acc.setAnimale(AnimaleDAO.getAnimale(rs.getInt("animale_id"),connection));
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
			acc.setLookupTipiRichiedente(LookupTipiRichiedenteDAO.getTipoRichiedente(rs.getInt("richiedente_tipo"), connection));
		}
		return acc;   
	}
	
	
	public static Autopsia getAutopsia( Connection connection, int id) throws SQLException
	{
		Autopsia a = null;
		String sql = " select a.data_autopsia, a.data_esito, a.id, a.entered_by, a.patologia_definitiva "
				+ "    from autopsia a, utenti_ u "
				+ "where u.id = a.entered_by and a.cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			a = new Autopsia();
			a.setDataEsito(rs.getDate("data_esito"));
			a.setDataAutopsia(rs.getDate("data_autopsia"));
			a.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by"),connection));
			a.setPatologiaDefinitiva(LookupAutopsiaPatologiePrevalentiDAO.getPatologia(rs.getInt("patologia_definitiva"), connection));
		}
		return a;   
	}
	
	public static Animale getAnimale( Connection connection, int id) throws SQLException
	{
		Animale an = null;
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
			an = new Animale();
			an = (Animale) Bean.populate(an, rs);
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
	
	
	public static HashSet<Trasferimento> getTrasferimenti( int idCc, Connection connection) throws SQLException
	{
		HashSet<Trasferimento> trasf = new HashSet<Trasferimento>();
		String sql = " select t.id, t.automatico_necroscopia FROM TRASFERIMENTO t "
				+ " where t.cartella_clinica = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idCc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Trasferimento t = new Trasferimento();
			t.setId( rs.getInt("id"));
			t.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));
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
			if ((apparato!=null && eo.getLookupEsameObiettivoTipo()!=null && eo.getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati()!=null && eo.getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati().getId() == apparato.getId()) ||
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
	
	
}
