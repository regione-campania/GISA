/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.vam;

import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleNoH;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.lookup.LookupCMIDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
import it.us.web.dao.lookup.LookupSpecieDAO;
import it.us.web.dao.sinantropi.lookup.LookupSinantropiEtaDAO;
import it.us.web.util.bean.Bean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnimaleDAO extends GenericDAO {
	public static final Logger logger = LoggerFactory.getLogger(AnimaleDAO.class);

	public static Animale getAnimale(int id, Connection conn) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Animale animale = new Animale();

		try {
			stat = conn.prepareStatement("select necroscopia_non_effettuabile, razza, mantello,taglia, eta, sesso, ddt, ditta_autorizzata, deceduto_non_anagrafe_causa_morte, specie_sinantropo, razza_sinantropo, data_smaltimento_carogna, id, identificativo, eta, comune_ritrovamento, provincia_ritrovamento, indirizzo_ritrovamento, note_ritrovamento,  clinica_chippatura, tatuaggio, deceduto_non_anagrafe_data_morte_presunta, data_nascita, specie as lookup_specie_id, deceduto_non_anagrafe, deceduto_non_anagrafe_data_morte from animale where id = ?");
			stat.setInt(1, id);
			rs = stat.executeQuery();

			
			
			if (rs.next()) {
				animale.setId(rs.getInt("id"));
				animale.setIdentificativo(rs.getString("identificativo"));
				animale.setLookupSpecie(LookupSpecieDAO.getSpecie(rs.getInt("lookup_specie_id"),conn));
				
				animale.setRazza(rs.getInt("razza"));
				animale.setMantello(rs.getInt("mantello"));
				animale.setTaglia(rs.getInt("taglia"));				
				
				animale.setNecroscopiaNonEffettuabile(rs.getBoolean("necroscopia_non_effettuabile"));
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
				animale.setSpecieSinantropo(rs.getString("specie_sinantropo"));
				animale.setRazzaSinantropo(rs.getString("razza_sinantropo"));
				animale.setSesso(rs.getString("sesso")); 
				 
				
			}

		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, null);
		}

		return animale;
	}
	
	public static Integer getTipologiaByCc(int idCc, Connection conn) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Integer id = null;

		try {
			stat = conn.prepareStatement(" select specie.id as id " +
										 " from cartella_clinica cc " +
										 " join accettazione acc on cc.accettazione = acc.id and acc.trashed_date is null " +
										 " join animale an on acc.animale = an.id and an.trashed_date is null " +
										 " join lookup_specie specie on specie.id = an.specie " +
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
	
	public static String getIdentificativoByCc(int idCc, Connection conn) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		String id = null;

		try {
			stat = conn.prepareStatement(" select an.identificativo " +
										 " from cartella_clinica cc " +
										 " join accettazione acc on cc.accettazione = acc.id and acc.trashed_date is null " +
										 " join animale an on acc.animale = an.id and an.trashed_date is null " +
										 " join lookup_specie specie on specie.id = an.specie " +
										 " where cc.id = ? and  " +
										 " cc.trashed_date is null  ");
			stat.setInt(1, idCc);
			rs = stat.executeQuery();

			
			
			if (rs.next()) 
				id = rs.getString("identificativo");

		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, null);
		}

		return id;
	}
	
	public static Boolean getDecedutoNonAnagrafeByCc(int idCc, Connection conn) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Boolean id = null;

		try {
			stat = conn.prepareStatement(" select an.deceduto_non_anagrafe as deceduto_non_anagrafe " +
										 " from cartella_clinica cc " +
										 " join accettazione acc on cc.accettazione = acc.id and acc.trashed_date is null " +
										 " join animale an on acc.animale = an.id and an.trashed_date is null " +
										 " where cc.id = ? and  " +
										 " cc.trashed_date is null  ");
			stat.setInt(1, idCc);
			rs = stat.executeQuery();

			
			
			if (rs.next()) 
				id = rs.getBoolean("deceduto_non_anagrafe");

		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, null);
		}

		return id;
	}
	
	public static Animale getAnimale(String identificativo, Connection conn) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Animale animale = null;

		try {
			stat = conn.prepareStatement("select ddt, ditta_autorizzata, deceduto_non_anagrafe_causa_morte, sesso, data_smaltimento_carogna, razza_sinantropo, id, identificativo, eta, comune_ritrovamento, provincia_ritrovamento, indirizzo_ritrovamento, note_ritrovamento,  clinica_chippatura, tatuaggio, deceduto_non_anagrafe_data_morte_presunta, data_nascita, specie as lookup_specie_id, deceduto_non_anagrafe, deceduto_non_anagrafe_data_morte "
					+ " from animale a where (a.identificativo = ? or a.tatuaggio = ?) and a.trashed_date is null");
			stat.setString(1, identificativo);
			stat.setString(2, identificativo);
			rs = stat.executeQuery();

			if (rs.next()) 
			{
				animale = new Animale();
				animale.setId(rs.getInt("id"));
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
				animale.setDittaAutorizzata(rs.getString("ditta_autorizzata"));
				animale.setRazzaSinantropo(rs.getString("razza_sinantropo"));
				animale.setDdt(rs.getString("ddt"));
				animale.setSesso(rs.getString("sesso"));
			}

		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, null);
		}

		return animale;
	}

	public static BRuolo getRuoloByName(String nomeruolo) {
		BRuolo ruolo = null;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			conn = retrieveConnection();
			stat = conn.prepareStatement(Sql.GET_RUOLO_BY_NOME);

			stat.setString(1, nomeruolo);

			rs = stat.executeQuery();

			if (rs.next()) {
				ruolo = new BRuolo();
				ruolo.setId(rs.getInt("ID"));
				ruolo.setRuolo(rs.getString("NOME"));
				ruolo.setDescrizione(rs.getString("DESCRIZIONE"));
			}
		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, conn);
		}

		return ruolo;

	}

	public static BRuolo getRuoloById(int idRuolo) {
		BRuolo ruolo = null;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			conn = retrieveConnection();
			stat = conn.prepareStatement(Sql.GET_RUOLO_BY_ID);

			stat.setInt(1, idRuolo);

			rs = stat.executeQuery();

			if (rs.next()) {
				ruolo = new BRuolo();
				ruolo.setId(rs.getInt("ID"));
				ruolo.setRuolo(rs.getString("NOME"));
				ruolo.setDescrizione(rs.getString("DESCRIZIONE"));
			}
		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, conn);
		}

		return ruolo;

	}

	public static void insertRuolo(String nomeruolo, String descrizione) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;

		try {
			conn = retrieveConnection();
			stat = conn.prepareStatement(Sql.INSERT_RUOLO);
			stat.setString(1, nomeruolo);
			stat.setString(2, descrizione);
			stat.execute();
			// conn.commit();
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		} finally {
			close(stat, conn);
		}
	}

	public static String modificaDescrizioneRuolo(String nuovaDescrizione, String nomeRuolo) {
		String errore = "";
		Connection conn = null;
		PreparedStatement stat = null;
		if ((nuovaDescrizione == null) || (nuovaDescrizione.equals(""))) {
			return "Campo descrizione obbligatorio.";
		} else {
			try {
				conn = retrieveConnection();

				stat = conn.prepareStatement("UPDATE permessi_RUOLI SET DESCRIZIONE = ? WHERE NOME = ? ");
				stat.setString(1, nuovaDescrizione);
				stat.setString(2, nomeRuolo);
				stat.execute();
				conn.commit();
			} catch (SQLException e) {
				logger.error("", e);
			} finally {
				close(stat, conn);
			}
		}

		return errore;
	}

	public static void delete(String nome_ruolo, BUtente utente, HttpServletRequest req) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = retrieveConnection();
			stat = conn.prepareStatement(Sql.DELETE_RUOLO);
			stat.setString(1, nome_ruolo);
			stat.execute();
			conn.commit();
		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(stat, conn);
		}
	}
	
		public static CartellaClinicaNoH getCcRicovero(Animale animale, Connection conn) {
			PreparedStatement stat = null;
			ResultSet rs = null;
			CartellaClinicaNoH cc = null;

			
			try {
				stat = conn.prepareStatement(" select c.id " +
										     " from cartella_clinica c," + 
											 "             fascicolo_sanitario fs, " +
										     "             accettazione acc, " + 
											 "             animale an " +
											 " where c.fascicolo_sanitario = fs.id and " +
											 "       fs.data_chiusura is null and " +
											 "       acc.id = c.accettazione and " +
											 "       an.id = acc.animale and " +
											 "       (an.identificativo = ? or an.tatuaggio = ? ) and " +
											 "       c.trashed_date is null and " + 
											 "       c.data_chiusura is null " + 
											 " order by c.data_apertura desc ");
				stat.setString(1, animale.getIdentificativo());
				stat.setString(2, animale.getIdentificativo());
				rs = stat.executeQuery();

				if (rs.next()) 
				{
					cc = new CartellaClinicaNoH();
					cc = CartellaClinicaDAONoH.getCc(conn, rs.getInt(0));
				}

			} catch (SQLException e) {
				logger.error("", e);
			} finally {
				close(rs, stat, null);
			}

			return cc;
		}
		
	public static void updateClinicaChippatura(Connection conn, AnimaleNoH animale) {
		PreparedStatement stat = null;
			try {
				stat = conn.prepareStatement("UPDATE animale set clinica_chippatura = ? where id = ? ");
				stat.setInt(1, animale.getClinicaChippatura().getId());
				stat.setInt(2, animale.getId());
				stat.execute();
				conn.commit();
			} catch (SQLException e) {
				logger.error("", e);
			} finally {
				close(stat, null);
			}

	} 
}
