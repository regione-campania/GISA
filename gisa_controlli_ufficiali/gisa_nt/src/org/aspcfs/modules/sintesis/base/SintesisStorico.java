/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SintesisStorico {

	public static void salvaStoricoStabilimento(Connection db, int idRecord, int idImport, int idUtente, SintesisStabilimento oldStabilimento, SintesisStabilimento newStabilimento) throws SQLException {
		
		if (oldStabilimento==null){
			oldStabilimento = new SintesisStabilimento();
			oldStabilimento.setIdStabilimento(newStabilimento.getIdStabilimento());
		}
		
		PreparedStatement pst = db.prepareStatement("insert into sintesis_storico_stabilimento(id_sintesis_stabilimenti_import, id_import, data_modifica, id_utente, id_stabilimento, denominazione_old, denominazione_new,"
				+ "id_indirizzo_old, id_indirizzo_new, id_operatore_old, id_operatore_new, stato_old, stato_new)"
				+ "values (?, ?,now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		int i = 0;
		pst.setInt(++i, idRecord);
		pst.setInt(++i, idImport);
		pst.setInt(++i, idUtente);
		pst.setInt(++i, oldStabilimento.getIdStabilimento());
		pst.setString(++i, oldStabilimento.getDenominazione());
		pst.setString(++i, newStabilimento.getDenominazione());
		pst.setInt(++i, oldStabilimento.getIdIndirizzo());
		pst.setInt(++i, newStabilimento.getIdIndirizzo());
		pst.setInt(++i, oldStabilimento.getIdOperatore());
		pst.setInt(++i, newStabilimento.getIdOperatore());
		pst.setInt(++i, oldStabilimento.getStato());
		pst.setInt(++i, newStabilimento.getStato());		
		pst.executeUpdate();	
		}

	public static void salvaStoricoRelazione(Connection db, int idRecord, int idImport, int idUtente, SintesisRelazioneLineaProduttiva oldRelazione, SintesisRelazioneLineaProduttiva newRelazione) throws SQLException {
		
		if (oldRelazione==null){
			oldRelazione = new SintesisRelazioneLineaProduttiva();
			oldRelazione.setIdRelazione(newRelazione.getIdRelazione());
		}
			
		PreparedStatement pst = db.prepareStatement("insert into sintesis_storico_relazione_stabilimento_linee_produttive(id_sintesis_stabilimenti_import, id_import, data_modifica, id_utente, id_relazione, stato_old, stato_new, data_inizio_old, data_inizio_new, data_fine_old, data_fine_new, tipo_autorizzazione_old, tipo_autorizzazione_new, imballaggio_old, imballaggio_new, paesi_abilitati_export_old, paesi_abilitati_export_new, remark_old, remark_new, species_old, species_new)"
				+ " values (?,?, now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		int i = 0;
		pst.setInt(++i, idRecord);
		pst.setInt(++i, idImport);
		pst.setInt(++i, idUtente);
		pst.setInt(++i, newRelazione.getIdRelazione());
		pst.setInt(++i, oldRelazione.getStato());
		pst.setInt(++i, newRelazione.getStato());
		pst.setTimestamp(++i, oldRelazione.getDataInizio());
		pst.setTimestamp(++i, newRelazione.getDataInizio());
		pst.setTimestamp(++i, oldRelazione.getDataFine());
		pst.setTimestamp(++i, newRelazione.getDataFine());
		pst.setString(++i, oldRelazione.getTipoAutorizzazione());
		pst.setString(++i, newRelazione.getTipoAutorizzazione());
		pst.setString(++i, oldRelazione.getImballaggio());
		pst.setString(++i, newRelazione.getImballaggio());
		pst.setString(++i, oldRelazione.getPaesiAbilitatiExport());
		pst.setString(++i, newRelazione.getPaesiAbilitatiExport());
		pst.setString(++i, oldRelazione.getRemark());
		pst.setString(++i, newRelazione.getRemark());
		pst.setString(++i, oldRelazione.getSpecies());
		pst.setString(++i, newRelazione.getSpecies());
		pst.executeUpdate();
		
	}

	public static void salvaStoricoOperatoreCompleta(Connection db, int userId, SintesisOperatore opEsistente,
			SintesisOperatore op) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into sintesis_storico_operatore(data_modifica, id_utente, id_operatore, tipo_impresa_old, tipo_impresa_new, tipo_societa_old, tipo_societa_new, domicilio_digitale_old, domicilio_digitale_new, id_indirizzo_old, id_indirizzo_new)"
				+ " values (now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		int i = 0;
		pst.setInt(++i, userId);
		pst.setInt(++i, opEsistente.getIdOperatore());
		pst.setInt(++i, opEsistente.getTipoImpresa());
		pst.setInt(++i, op.getTipoImpresa());
		pst.setInt(++i, opEsistente.getTipoSocieta());
		pst.setInt(++i, op.getTipoSocieta());
		pst.setString(++i, opEsistente.getDomicilioDigitale());
		pst.setString(++i, op.getDomicilioDigitale());
		pst.setInt(++i, opEsistente.getIdIndirizzo());
		pst.setInt(++i, op.getIdIndirizzo());
		pst.executeUpdate();	
		
	}

	public static void salvaStoricoSoggettoCompleta(Connection db, int userId, SintesisSoggettoFisico soggEsistente,
			SintesisSoggettoFisico sogg) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into sintesis_storico_soggetto_fisico(data_modifica, id_utente, id_soggetto_fisico, nome_old, nome_new, cognome_old, cognome_new, sesso_old, sesso_new, data_nascita_old, data_nascita_new, nazione_nascita_old, nazione_nascita_new, comune_nascita_old, comune_nascita_new, codice_fiscale_old, codice_fiscale_new, indirizzo_id_old, indirizzo_id_new, email_old, email_new)"
				+ " values (now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		int i = 0;
		pst.setInt(++i, userId);
		pst.setInt(++i, soggEsistente.getId());
		pst.setString(++i, soggEsistente.getNome());
		pst.setString(++i, sogg.getNome());
		pst.setString(++i, soggEsistente.getCognome());
		pst.setString(++i, sogg.getCognome());
		pst.setString(++i, soggEsistente.getSesso());
		pst.setString(++i, sogg.getSesso());
		pst.setTimestamp(++i, soggEsistente.getDataNascita());
		pst.setTimestamp(++i, sogg.getDataNascita());
		pst.setInt(++i, soggEsistente.getNazioneNascita());
		pst.setInt(++i, sogg.getNazioneNascita());
		pst.setString(++i, soggEsistente.getComuneNascita());
		pst.setString(++i, sogg.getComuneNascita());
		pst.setString(++i, soggEsistente.getCodiceFiscale());
		pst.setString(++i, sogg.getCodiceFiscale());
		pst.setInt(++i, soggEsistente.getIdIndirizzo());
		pst.setInt(++i, sogg.getIdIndirizzo());
		pst.setString(++i, soggEsistente.getDomicilioDigitale());
		pst.setString(++i, sogg.getDomicilioDigitale());
		pst.executeUpdate();			
	}



}
