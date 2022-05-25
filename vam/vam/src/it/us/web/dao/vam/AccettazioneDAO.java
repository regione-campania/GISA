/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.vam;

import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.SuperUtenteAll;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AccettazioneNoH;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.AttivitaBdrNoH;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.SuperUtenteDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupAssociazioniDAO;
import it.us.web.dao.lookup.LookupCMIDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
import it.us.web.dao.lookup.LookupEsameIstopatologicoSedeLesioneDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.dao.lookup.LookupSpecieDAO;
import it.us.web.dao.lookup.LookupTipiRichiedenteDAO;
import it.us.web.dao.lookup.LookupTipoTrasferimentoDAO;
import it.us.web.dao.sinantropi.lookup.LookupSinantropiEtaDAO;
import it.us.web.util.DateUtils;
import it.us.web.util.bean.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Vector;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccettazioneDAO extends GenericDAO {
	public static final Logger logger = LoggerFactory.getLogger(AccettazioneDAO.class);

	public static Accettazione getAccettazione(int id, Connection conn) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Accettazione acc = new Accettazione();

		try {
			stat = conn.prepareStatement("select acc.adozione_verso_assoc_canili, acc.id_acc_multipla,acc.cc_vivo, cl.fascicolo_sanitario, acc.id, animale as id_animale, randagio, acc.progressivo, data,"
					+ " richiedente_cognome, richiedente_altro, richiedente_asl, richiedente_asl_user, richiedente_associazione, "
					+ " richiedente_codice_fiscale, richiedente_documento, richiedente_forza_pubblica_comando, "
					+ " richiedente_forza_pubblica_provincia, richiedente_forza_pubblica_comune, richiedente_nome, "
					+ " richiedente_proprietario, richiedente_residenza, richiedente_telefono, richiedente_tipo, richiedente_tipo_proprietario, "
					+ " acc.entered_by , proprietario_nome, proprietario_cognome, proprietario_tipo, proprietario_codice_fiscale ,proprietario_documento, "
					+ " proprietario_cap, proprietario_provincia, proprietario_comune ,proprietario_indirizzo ,proprietario_telefono ,randagio ,"
					+ " asl_ritrovamento ,asl_ritrovamento, acc.adozione_fuori_asl, note_altro, note_ricovero_in_canile ,note_incompatibilita_ambientale , "
					+ " comune_attivita_esterna, indirizzo_attivita_esterna, "
					+ " att_est.id as idAttEst, att_est.enabled as enabledAttEst, att_est.description as descriptionAttEst, " 
					+ " an.id as idAn, identificativo, eta, taglia, specie_sinantropo, razza_sinantropo, mantello, " 
					+ " tipo_trasferimento, razza, sesso, comune_ritrovamento, provincia_ritrovamento, indirizzo_ritrovamento, note_ritrovamento,  "
					+ " clinica_chippatura, tatuaggio, deceduto_non_anagrafe_data_morte_presunta, data_nascita, specie as lookup_specie_id, "
					+ " deceduto_non_anagrafe, deceduto_non_anagrafe_data_morte, data_smaltimento_carogna, deceduto_non_anagrafe_causa_morte, "
					+ " ditta_autorizzata, ddt, cl.fascicolo_sanitario  "
					+ " from accettazione acc "
					+ " left join lookup_accettazione_attivita_esterna att_est on att_est.id = acc.attivita_esterna "
					+ " left join animale an on an.id = acc.animale  left join cartella_clinica cl on acc.cc_vivo = cl.id "
					+ " where acc.id = ? ");
			stat.setInt(1, id);
			rs = stat.executeQuery();

			if (rs.next()) {
				//acc = (Accettazione) Bean.populate(acc, rs);
				if (rs.getInt("cc_vivo")>0)
					   acc.setCcVivo(getCcVivo(rs.getInt("cc_vivo"), rs.getInt("fascicolo_sanitario"),conn));
					else
					   acc.setCcVivo(getCcVivo(-1,-1,conn));
					
				
				
				
				acc.setId(rs.getInt("id"));
				acc.setIdAccMultipla(rs.getString("id_acc_multipla"));
				acc.setAnimale(getAnimale(rs,conn));
				acc.setProgressivo(rs.getInt("progressivo"));
				acc.setData(rs.getDate("data"));
				acc.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by"),conn));
				acc.setCartellaClinicas(getCartellaClinicas(rs.getInt("id"), conn));
				acc.setOperazioniRichieste(getOperazioniRichieste(rs.getInt("id"), conn));
				acc.setAttivitaBdrs(getAttivitaBdrs(rs.getInt("id"), conn));
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
				acc.setRandagio(rs.getBoolean("randagio"));
				acc.setRichiedenteCognome(rs.getString("richiedente_cognome"));
				acc.setRichiedenteAltro(rs.getString("richiedente_altro"));
				acc.setRichiedenteAsl(LookupAslDAO.getAsl(rs.getInt("richiedente_asl"), conn)  );
				acc.setRichiedenteAslUser(UtenteDAO.getUtente(rs.getInt("richiedente_asl_user")));
				acc.setRichiedenteAssociazione(LookupAssociazioniDAO.getAssociazione(rs.getInt("richiedente_associazione"), conn)  );
				acc.setRichiedenteCodiceFiscale(rs.getString("richiedente_codice_fiscale"));
				acc.setRichiedenteDocumento(rs.getString("richiedente_documento"));
				acc.setRichiedenteForzaPubblicaComando(rs.getString("richiedente_forza_pubblica_comando"));
				acc.setRichiedenteForzaPubblicaProvincia(rs.getString("richiedente_forza_pubblica_provincia"));
				acc.setRichiedenteForzaPubblicaComune(rs.getString("richiedente_forza_pubblica_comune"));
				acc.setRichiedenteNome(rs.getString("richiedente_nome"));
				acc.setRichiedenteProprietario(rs.getBoolean("richiedente_proprietario"));
				acc.setRichiedenteResidenza(rs.getString("richiedente_residenza"));
				acc.setRichiedenteTelefono(rs.getString("richiedente_telefono"));
				acc.setRichiedenteTipoProprietario(rs.getString("richiedente_tipo_proprietario"));
				acc.setLookupTipiRichiedente(LookupTipiRichiedenteDAO.getTipoRichiedente(rs.getInt("richiedente_tipo"), conn)  );
				acc.setPersonaleInterno(getPersonaleInterno(rs.getInt("id"), conn));
				acc.setAslRitrovamento(LookupAslDAO.getAsl(rs.getInt("asl_ritrovamento"), conn)  );
				acc.setTipoTrasferimento(LookupTipoTrasferimentoDAO.getTipoTrasferimento(rs.getInt("tipo_trasferimento"), conn));
				acc.setAdozioneFuoriAsl(rs.getBoolean("adozione_fuori_asl"));
				acc.setAdozioneVersoAssocCanili(rs.getBoolean("adozione_verso_assoc_canili"));
				acc.setNoteAltro(rs.getString("note_altro"));
				acc.setNoteRicoveroInCanile(rs.getString("note_ricovero_in_canile"));
				acc.setNoteIncompatibilitaAmbientale(rs.getString("note_incompatibilita_ambientale"));
				acc.setAttivitaEsterna(getAttivitaEsterna(rs));
				acc.setComuneAttivitaEsterna(LookupComuniDAO.getComune(rs.getInt("comune_attivita_esterna"), conn));
				acc.setIndirizzoAttivitaEsterna(rs.getString("indirizzo_attivita_esterna"));
			}

		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, null);
		}

		return acc;
	}
	
	
	
	
	public static int getNextProgressivo(Date data1, Date data2, int idClinica, Connection connection) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		int nextProgressivo = 1;

		try {
			stat = connection.prepareStatement("select max(progressivo)+1 as progressivo "
					+ " from accettazione acc, utenti_ as u "
					+ " where acc.data>= ? and "
					+ " acc.data < ? and "
					+ " u.clinica = ? and "
					+ " acc.entered_by = u.id and "
					+ " acc.trashed_date is null ");
			stat.setDate(1, new java.sql.Date(data1.getTime()));
			stat.setDate(2, new java.sql.Date(data2.getTime()));
			stat.setInt(3, idClinica);
			rs = stat.executeQuery();

			if (rs.next()) 
			{
				nextProgressivo = rs.getInt("progressivo");
			}

		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, null);
		}

		return nextProgressivo;
	}
	
	public static int getNextId(Connection connection) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		int nextId = -1;

		try {
			stat = connection.prepareStatement(" select nextval('accettazione_id_seq')  as nextId ");
			rs = stat.executeQuery();

			rs.next(); 
			nextId = rs.getInt("nextId");

		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, null);
		}

		return nextId;
	}

	
	private static HashSet<CartellaClinica> getCartellaClinicas( int idAcc, Connection connection) throws SQLException
	{
		HashSet<CartellaClinica> ccs = new HashSet<CartellaClinica>();
		String sql = " select id, numero, data_apertura, data_chiusura,cc_riconsegna from cartella_clinica where accettazione = ? and trashed_date is null";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAcc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			CartellaClinica cc = new CartellaClinica();
			cc.setId( rs.getInt("id"));
			cc.setDataApertura(rs.getDate("data_apertura"));
			cc.setDataChiusura(rs.getDate("data_chiusura"));
			cc.setNumero( rs.getString("numero"));
			cc.setCcRiconsegna(rs.getBoolean("cc_riconsegna"));
			cc.setTrasferimenti(CartellaClinicaDAO.getTrasferimenti(cc.getId(),connection));
			cc.setAutopsia(getAutopsia(cc.getId(),connection));
			ccs.add(cc);
		}
		return ccs;
	}
	
	
	
	private static LookupAccettazioneAttivitaEsterna getAttivitaEsterna( ResultSet rs) throws SQLException
	{
		LookupAccettazioneAttivitaEsterna op = null;
		
		if(rs.getInt("idAttEst")>0)
		{
			op = new LookupAccettazioneAttivitaEsterna();
			op.setDescription(rs.getString("descriptionAttEst"));
			op.setEnabled(rs.getBoolean("enabledAttEst"));
			op.setId(rs.getInt("idAttEst"));
		}
		return op;
	}
	
	private static Autopsia getAutopsia( int idCc, Connection conn) throws SQLException
	{
		Autopsia a =null;
		String sql = " select a.id, a.data_autopsia from autopsia a where cartella_clinica = ? ";
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, idCc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			a =  new Autopsia();
			a.setId( rs.getInt("id"));
			a.setDataAutopsia(rs.getDate("data_autopsia"));
		}
		return a;
	}
	
	public static HashSet<LookupOperazioniAccettazione> getOperazioniRichieste( int idAcc, Connection connection) throws SQLException
	{
		HashSet<LookupOperazioniAccettazione> ops = new HashSet<LookupOperazioniAccettazione>();
		String sql = " select op.verso_assoc_canili, op.id, op.inbdr, op.description , op.scelta_asl, op.effettuabile_da_morto, "
				+ " op.effettuabile_da_vivo, op.effettuabile_fuori_asl, op.effettuabile_fuori_asl_morto, "
				+ " op.enabled_in_page, op.id_bdr, op.intra_fuori_asl, op.obbligo_cc, op.covid "
				+ " from lookup_operazioni_accettazione op, accettazione_operazionirichieste aop "
				+ " where aop.accettazione = ? and aop.operazione_richiesta = op.id ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAcc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
			op.setId( rs.getInt("id"));
			op.setInbdr( rs.getBoolean("inbdr"));
			op.setDescription( rs.getString("description"));
			op.setSceltaAsl( rs.getBoolean("scelta_asl"));
			op.setEffettuabileDaMorto(rs.getBoolean("effettuabile_da_morto"));
			op.setEffettuabileDaVivo(rs.getBoolean("effettuabile_da_vivo"));
			op.setEffettuabileFuoriAsl(rs.getBoolean("effettuabile_fuori_asl"));
			op.setEffettuabileFuoriAslMorto(rs.getBoolean("effettuabile_fuori_asl_morto"));
			op.setEnabledInPage(rs.getBoolean("enabled_in_page"));
			op.setIdBdr(rs.getInt("id_bdr"));
			op.setIntraFuoriAsl(rs.getBoolean("intra_fuori_asl"));
			op.setVersoAssocCanili(rs.getBoolean("verso_assoc_canili"));
			op.setObbligoCc(rs.getBoolean("obbligo_cc"));
			op.setCovid(rs.getBoolean("covid"));
			ops.add(op);
		}
		return ops;
	}
	
	public static HashSet<AttivitaBdr> getAttivitaBdrs( int idAcc, Connection connection) throws SQLException
	{
		HashSet<AttivitaBdr> atts = new HashSet<AttivitaBdr>();
		String sql = " select att.id as idAtt,att.tipo_operazione, op.id as idOp, op.inbdr, att.id_registrazione_bdr "
				+ " from attivita_bdr att "
				+ " left join lookup_operazioni_accettazione op on op.id = att.tipo_operazione"
				+ " where att.accettazione = ? and att.trashed_date is null ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAcc);
		
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
	
	public static HashSet<LookupPersonaleInterno> getPersonaleInterno( int idAcc, Connection connection) throws SQLException
	{
		HashSet<LookupPersonaleInterno> pis = new HashSet<LookupPersonaleInterno>();
		String sql = " select pi.id, pi.enabled, pi.nominativo "
				+ " from lookup_personale_interno pi, accettazione_personaleinterno api "
				+ " where api.accettazione = ? and api.personale_interno = pi.id ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAcc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			LookupPersonaleInterno pi = new LookupPersonaleInterno();
			pi.setEnabled(rs.getBoolean("enabled"));
			pi.setId(rs.getInt("id"));
			pi.setNominativo(rs.getString("nominativo"));
			pis.add(pi);
		}
		return pis;
	}
	
	public static HashSet<SuperUtenteAll> getPersonaleAsl( int idAcc, Connection connection) throws SQLException
	{
		HashSet<SuperUtenteAll> personali = new HashSet<SuperUtenteAll>();
		String sql = " select pasl.id as id "
				+ " from utenti_super_ pasl, accettazione_personaleasl apasl "
				+ " where apasl.accettazione = ? and apasl.personale_asl = pasl.id ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAcc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			SuperUtenteAll personale = new SuperUtenteAll();
			personale.setId(rs.getInt("id"));
			personale.setUtenti(SuperUtenteDAO.getUtentiAll(connection, rs.getInt("id")));
			personali.add(personale);
		}
		return personali;
	}
	
	
	
	private static Animale getAnimale(ResultSet rs,Connection conn) throws SQLException 
	{
		Animale animale = new Animale();
		animale.setId(rs.getInt("idAn"));
		animale.setIdentificativo(rs.getString("identificativo"));
		animale.setLookupSpecie(LookupSpecieDAO.getSpecie(rs.getInt("lookup_specie_id"),conn));
		animale.setDecedutoNonAnagrafe(rs.getBoolean("deceduto_non_anagrafe"));
		animale.setDataMorte(rs.getDate("deceduto_non_anagrafe_data_morte"));
		animale.setDataSmaltimentoCarogna(rs.getDate("data_smaltimento_carogna"));
		animale.setTatuaggio(rs.getString("tatuaggio"));
		animale.setDataNascita(rs.getDate("data_nascita"));
		animale.setEta(LookupSinantropiEtaDAO.getEta(rs.getInt("eta"),conn));
		animale.setClinicaChippatura(ClinicaDAO.getClinica(rs.getInt("clinica_chippatura"), conn));
		animale.setDataMortePresunta(rs.getBoolean("deceduto_non_anagrafe_data_morte_presunta"));
		animale.setCausaMorte(LookupCMIDAO.getCMI(rs.getInt("deceduto_non_anagrafe_causa_morte"),conn));
		animale.setComuneRitrovamento(LookupComuniDAO.getComune(rs.getInt("comune_ritrovamento"), conn));
		animale.setProvinciaRitrovamento(rs.getString("provincia_ritrovamento"));
		animale.setIndirizzoRitrovamento(rs.getString("indirizzo_ritrovamento"));
		animale.setNoteRitrovamento(rs.getString("note_ritrovamento"));
		animale.setDataSmaltimentoCarogna(rs.getDate("data_smaltimento_carogna"));
		animale.setDittaAutorizzata(rs.getString("ditta_autorizzata"));
		animale.setDdt(rs.getString("ddt"));
		animale.setAccettaziones(getAccettaziones(animale.getId(),conn));
		animale.setTaglia(rs.getInt("taglia"));
		animale.setSpecieSinantropo(rs.getString("specie_sinantropo"));
		animale.setRazzaSinantropo(rs.getString("razza_sinantropo"));
		animale.setMantello(rs.getInt("mantello"));
		animale.setRazza(rs.getInt("razza"));
		animale.setSesso(rs.getString("sesso"));
		return animale;
	}
	
	
	
	private static HashSet<Accettazione> getAccettaziones( int idAnimale, Connection connection) throws SQLException
	{
		HashSet<Accettazione> pis = new HashSet<Accettazione>();
		String sql = " select acc.id, sterilizzato, acc.data "
				+ " from accettazione acc "
				+ " where acc.trashed_date is null and acc.animale = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAnimale);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Accettazione acc = new Accettazione();
			acc.setId(rs.getInt("id"));
			acc.setData(rs.getDate("data"));
			acc.setSterilizzato(rs.getBoolean("sterilizzato"));
			pis.add(acc);
		}
		return pis;
	}
	
	
	private static CartellaClinica getCcVivo(int id,int idFs, Connection conn) throws SQLException 
	{
		CartellaClinica cl = null;
		
		if (id>0){
			cl = new CartellaClinica();
			cl.setFascicoloSanitario(FascicoloSanitarioDAO.getFascicoloSanitario(conn, idFs));
		}
		return cl;
	}
	
	
	
	
	
	public static ArrayList<Accettazione> getAccettazioniMultiple( String idAccMultipla, Connection connection) throws SQLException
	{
		ArrayList<Accettazione> accs = new ArrayList<Accettazione>();
		String sql = " select id from accettazione where id_acc_multipla = ? and trashed_date is null" ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, idAccMultipla);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			Accettazione acc = AccettazioneDAO.getAccettazione(rs.getInt("id"), connection);
			accs.add(acc);
		}
		return accs;
	}
	
	
	public static void insert(AccettazioneNoH acc, Connection connection) throws SQLException
	{
		String sql = "INSERT INTO accettazione( id, asl_animale, data, descrizione, entered, modified, note, " +
           " progressivo, proprietario_cap, proprietario_codice_fiscale, proprietario_cognome, " +
           "  proprietario_comune, proprietario_documento, proprietario_indirizzo,  " +
           "  proprietario_nome, proprietario_provincia, proprietario_telefono,  " +
           "  richiedente_altro, richiedente_codice_fiscale, richiedente_cognome,  " +
           "  richiedente_documento, richiedente_forza_pubblica_comando, richiedente_forza_pubblica_comune,  " +
           "  richiedente_forza_pubblica_provincia, richiedente_nome, richiedente_proprietario,  " +
           "  trashed_date, animale, entered_by, modified_by,  " +
           "  randagio, sterilizzato, note_altro, note_incompatibilita_ambientale,  " +
           "  note_ricovero_in_canile, richiedente_residenza,  " +
           "  richiedente_telefono, indirizzo_attivita_esterna, " +
           "  richiedente_tipo_proprietario, proprietario_tipo,  " +
           "  adozione_fuori_asl, adozione_verso_assoc_canili, id_acc_multipla ";
        	 if(acc.getCcVivo()!=null)
        		sql += " ,cc_vivo " ;
        	 if(acc.getAttivitaEsterna()!=null)
        		sql += " ,attivita_esterna " ;
        	 if(acc.getLookupTipiRichiedente()!=null)
                 sql += " ,richiedente_tipo ";
             if(acc.getRichiedenteAsl()!=null)
          	   sql += " ,richiedente_asl ";
             if(acc.getRichiedenteAslUser()!=null)
          	   sql += " ,richiedente_asl_user ";
             if(acc.getRichiedenteAssociazione()!=null)
          	   sql += " ,richiedente_associazione ";
             if(acc.getAslRitrovamento()!=null)
          	   sql += " ,asl_ritrovamento ";
             if(acc.getTipoTrasferimento()!=null)
          	   sql += " ,tipo_trasferimento ";
             if(acc.getComuneAttivitaEsterna()!=null)
          	   sql += " ,comune_attivita_esterna ";
           sql += ") " +
           " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?,  ?, ?, ?, ?, ?, " +
           "  ?, ?, ?, ?, ?, ?, ?, ?, " +
           "  ?, ?  ";
           if(acc.getCcVivo()!=null)
        	   sql += " ,? ";
           if(acc.getAttivitaEsterna()!=null)
        	   sql += " ,? ";
            if(acc.getLookupTipiRichiedente()!=null)
               sql += " ,? ";
           if(acc.getRichiedenteAsl()!=null)
        	   sql += " ,? ";
           if(acc.getRichiedenteAslUser()!=null)
        	   sql += " ,? ";
           if(acc.getRichiedenteAssociazione()!=null)
        	   sql += " ,? ";
           if(acc.getAslRitrovamento()!=null)
        	   sql += " ,? ";
           if(acc.getTipoTrasferimento()!=null)
        	   sql += " ,? ";
           if(acc.getComuneAttivitaEsterna()!=null)
        	   sql += " ,? ";
           sql += " ); ";
		
         int i=1;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(i++, acc.getId());
		st.setString(i++, acc.getAslAnimale());
		st.setDate(i++, new java.sql.Date(acc.getData().getTime()));
		st.setString(i++, acc.getDescrizione());
		st.setDate(i++, new java.sql.Date(acc.getEntered().getTime()));
		st.setDate(i++,  new java.sql.Date(acc.getModified().getTime()));
		st.setString(i++, acc.getNote());
		st.setInt(i++, acc.getProgressivo());
		st.setString(i++, acc.getProprietarioCap());
		st.setString(i++, acc.getProprietarioCodiceFiscale());
		st.setString(i++, acc.getProprietarioCognome());
		st.setString(i++, acc.getProprietarioComune());
		st.setString(i++, acc.getProprietarioDocumento());
		st.setString(i++, acc.getProprietarioIndirizzo());
		st.setString(i++, acc.getProprietarioNome());
		st.setString(i++, acc.getProprietarioProvincia());
		st.setString(i++, acc.getProprietarioTelefono());
		st.setString(i++, acc.getRichiedenteAltro());
		st.setString(i++, acc.getRichiedenteCodiceFiscale());
		st.setString(i++, acc.getRichiedenteCognome());
		st.setString(i++, acc.getRichiedenteDocumento());
		st.setString(i++, acc.getRichiedenteForzaPubblicaComando());
		st.setString(i++, acc.getRichiedenteForzaPubblicaComune());
		st.setString(i++, acc.getRichiedenteForzaPubblicaProvincia());
		st.setString(i++, acc.getRichiedenteNome());
		st.setBoolean(i++, acc.getRichiedenteProprietario());
		st.setDate(i++, (acc.getTrashedDate()!=null)?(new java.sql.Date(acc.getTrashedDate().getTime())):(null));
		st.setInt(i++, acc.getAnimale().getId());
		st.setInt(i++, acc.getEnteredBy().getId());
		st.setInt(i++, acc.getModifiedBy().getId());
		st.setBoolean(i++, acc.getRandagio());
		st.setBoolean(i++, acc.getSterilizzato());
		st.setString(i++, acc.getNoteAltro());
		st.setString(i++, acc.getNoteIncompatibilitaAmbientale());
		st.setString(i++, acc.getNoteRicoveroInCanile());
		st.setString(i++, acc.getRichiedenteResidenza());
		st.setString(i++, acc.getRichiedenteTelefono());
		st.setString(i++, acc.getIndirizzoAttivitaEsterna());
		st.setString(i++, acc.getRichiedenteTipoProprietario());
		st.setString(i++, acc.getProprietarioTipo());
		st.setBoolean(i++, acc.getAdozioneFuoriAsl());
		st.setBoolean(i++, acc.getAdozioneVersoAssocCanili());
		st.setString(i++, acc.getIdAccMultipla());
        if(acc.getCcVivo()!=null)
        	st.setInt(i++, acc.getCcVivo().getId());
        if(acc.getAttivitaEsterna()!=null)
        	st.setInt(i++, acc.getAttivitaEsterna().getId());
        if(acc.getLookupTipiRichiedente()!=null)
        	st.setInt(i++, acc.getLookupTipiRichiedente().getId());
        if(acc.getRichiedenteAsl()!=null)
        	st.setInt(i++, acc.getRichiedenteAsl().getId());
        if(acc.getRichiedenteAslUser()!=null)
        	st.setInt(i++, acc.getRichiedenteAslUser().getId());
        if(acc.getRichiedenteAssociazione()!=null)
        	st.setInt(i++, acc.getRichiedenteAssociazione().getId());
        if(acc.getAslRitrovamento()!=null)
        	st.setInt(i++, acc.getAslRitrovamento().getId());
        if(acc.getTipoTrasferimento()!=null)
        	st.setInt(i++, acc.getTipoTrasferimento().getId());
        if(acc.getComuneAttivitaEsterna()!=null)
        	st.setInt(i++, acc.getComuneAttivitaEsterna().getId());
        System.out.println("***************** QUERY CHIUSURA CC: " + st.toString() + " ****************");
		st.execute();
		st.close();
	}
	
	
	
	
	public static void update(AccettazioneNoH acc, Connection connection) throws SQLException
	{
		String sql = "update accettazione set data  = ?, modified = ?,  " +
           "  richiedente_altro= ?, richiedente_codice_fiscale= ?, richiedente_cognome= ?,  " +
           "  richiedente_documento= ?, richiedente_forza_pubblica_comando= ?, richiedente_forza_pubblica_comune= ?,  " +
           "  richiedente_forza_pubblica_provincia= ?, richiedente_nome= ?, richiedente_proprietario= ?,  " +
           "  modified_by= ?,  " +
           "  note_altro= ?, note_incompatibilita_ambientale= ?,  " +
           "  note_ricovero_in_canile= ?, richiedente_residenza= ?,  " +
           "  richiedente_telefono= ?, indirizzo_attivita_esterna= ?, " +
           "  richiedente_tipo_proprietario= ?, " +
           "  adozione_fuori_asl = ?, adozione_verso_assoc_canili = ? ";
        	 if(acc.getAttivitaEsterna()!=null)
        		sql += " , attivita_esterna = ? " ;
        	 if(acc.getLookupTipiRichiedente()!=null)
                 sql += " ,richiedente_tipo = ? ";
             if(acc.getRichiedenteAsl()!=null)
          	   sql += " ,richiedente_asl = ? ";
             if(acc.getRichiedenteAslUser()!=null)
          	   sql += " ,richiedente_asl_user  = ?";
             if(acc.getRichiedenteAssociazione()!=null)
          	   sql += " ,richiedente_associazione = ? ";
             if(acc.getAslRitrovamento()!=null)
          	   sql += " ,asl_ritrovamento = ? ";
             if(acc.getTipoTrasferimento()!=null)
          	   sql += " ,tipo_trasferimento = ? ";
             if(acc.getComuneAttivitaEsterna()!=null)
          	   sql += " ,comune_attivita_esterna  = ?";
             sql += " where id = ? ";
		
         int i=1;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setDate(i++, new java.sql.Date(acc.getData().getTime()));
		st.setDate(i++,  new java.sql.Date(acc.getModified().getTime()));
		st.setString(i++, acc.getRichiedenteAltro());
		st.setString(i++, acc.getRichiedenteCodiceFiscale());
		st.setString(i++, acc.getRichiedenteCognome());
		st.setString(i++, acc.getRichiedenteDocumento());
		st.setString(i++, acc.getRichiedenteForzaPubblicaComando());
		st.setString(i++, acc.getRichiedenteForzaPubblicaComune());
		st.setString(i++, acc.getRichiedenteForzaPubblicaProvincia());
		st.setString(i++, acc.getRichiedenteNome());
		st.setBoolean(i++, acc.getRichiedenteProprietario());
		st.setInt(i++, acc.getModifiedBy().getId());
		st.setString(i++, acc.getNoteAltro());
		st.setString(i++, acc.getNoteIncompatibilitaAmbientale());
		st.setString(i++, acc.getNoteRicoveroInCanile());
		st.setString(i++, acc.getRichiedenteResidenza());
		st.setString(i++, acc.getRichiedenteTelefono());
		st.setString(i++, acc.getIndirizzoAttivitaEsterna());
		st.setString(i++, acc.getRichiedenteTipoProprietario());
		st.setBoolean(i++, acc.getAdozioneFuoriAsl());
		st.setBoolean(i++, acc.getAdozioneVersoAssocCanili());
        if(acc.getAttivitaEsterna()!=null)
        	st.setInt(i++, acc.getAttivitaEsterna().getId());
        if(acc.getLookupTipiRichiedente()!=null)
        	st.setInt(i++, acc.getLookupTipiRichiedente().getId());
        if(acc.getRichiedenteAsl()!=null)
        	st.setInt(i++, acc.getRichiedenteAsl().getId());
        if(acc.getRichiedenteAslUser()!=null)
        	st.setInt(i++, acc.getRichiedenteAslUser().getId());
        if(acc.getRichiedenteAssociazione()!=null)
        	st.setInt(i++, acc.getRichiedenteAssociazione().getId());
        if(acc.getAslRitrovamento()!=null)
        	st.setInt(i++, acc.getAslRitrovamento().getId());
        if(acc.getTipoTrasferimento()!=null)
        	st.setInt(i++, acc.getTipoTrasferimento().getId());
        if(acc.getComuneAttivitaEsterna()!=null)
        	st.setInt(i++, acc.getComuneAttivitaEsterna().getId());
        st.setInt(i++, acc.getId());
        
		st.executeUpdate();
		st.close();
	}
	
	
	public static void insertOperazioniRichieste( int acc, int op, Connection connection) throws SQLException
	{
		String sql = "INSERT INTO accettazione_operazionirichieste( accettazione, operazione_richiesta) VALUES (?, ?);";
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, acc);
		st.setInt(2, op);
		st.execute();
		st.close();
	}
	
	public static void insertPersonaleAsl( int acc, int sUt, Connection connection) throws SQLException
	{
		String sql = "INSERT INTO accettazione_personaleasl( accettazione, personale_asl) VALUES (?, ?);";
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, acc);
		st.setInt(2, sUt);
		st.execute();
		st.close();
	}
	
	public static void insertPersonaleInterno( int acc, int persInterno, Connection connection) throws SQLException
	{
		String sql = "INSERT INTO accettazione_personaleinterno( accettazione, personale_interno) VALUES (?, ?);";
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, acc);
		st.setInt(2, persInterno);
		st.execute();
		st.close();
	}
	
	
}
