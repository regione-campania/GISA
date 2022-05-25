/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.orders.base.OrderAddress;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupList;

public class EventoSterilizzazione extends Evento {

	public static final int idTipologiaDB = 2;
	public static final int idTipoSoggettoASL = 1;
	public static final int idTipoSoggettoLLP = 2;

	private int id;
	private java.sql.Timestamp dataSterilizzazione;
	private boolean flagContributoRegionale = false;
	private int idProgettoSterilizzazioneRichiesto;
	private int idTipologiaSoggettoSterilizzante = -1;
	private int veterinarioAsl1 = -1;
	private int veterinarioAsl2 = -1;
	
	/**
	 * creare lookup tipologia_soggento sterilizzante con valori possibili asl o
	 * llp
	 */
	private int idSoggettoSterilizzante = -1; // id asl o id llp
	private int idAslEsecutrice = -1;
	private int idAslProprietario = -1;
	private int idEvento;

	// public static final HashMap campi = new HashMap();

	public int getId() {
		return id;
	}

	public java.sql.Timestamp getDataSterilizzazione() {
		return dataSterilizzazione;
	}

	public void setDataSterilizzazione(java.sql.Timestamp dataSterilizzazione) {
		this.dataSterilizzazione = dataSterilizzazione;
	}

	public void setDataSterilizzazione(String dataSter) {
		this.dataSterilizzazione = DateUtils.parseDateStringNew(dataSter,
				"dd/MM/yyyy");
	}

	public boolean isFlagContributoRegionale() {
		return flagContributoRegionale;
	}

	public void setFlagContributoRegionale(boolean flagContributoRegionale) {
		this.flagContributoRegionale = flagContributoRegionale;
	}

	public void setFlagContributoRegionale(String flag) {
		this.flagContributoRegionale = DatabaseUtils.parseBoolean(flag);
	}

	public int getIdProgettoSterilizzazioneRichiesto() {
		return idProgettoSterilizzazioneRichiesto;
	}

	public void setIdProgettoSterilizzazioneRichiesto(
			int idProgettoSterilizzazioneRichiesto) {
		this.idProgettoSterilizzazioneRichiesto = idProgettoSterilizzazioneRichiesto;
	}

	public void setIdProgettoSterilizzazioneRichiesto(
			String idProgettoSterilizzazioneRichiesto) {
		this.idProgettoSterilizzazioneRichiesto = new Integer(
				idProgettoSterilizzazioneRichiesto).intValue();
	}

	public int getIdTipologiaSoggettoSterilizzante() {
		return idTipologiaSoggettoSterilizzante;
	}

	public void setIdTipologiaSoggettoSterilizzante(
			int idTipologiaSoggettoSterilizzante) {
		this.idTipologiaSoggettoSterilizzante = idTipologiaSoggettoSterilizzante;
	}

	public void setIdTipologiaSoggettoSterilizzante(
			String idTipologiaSoggettoSterilizzante) {
		this.idTipologiaSoggettoSterilizzante = new Integer(
				idTipologiaSoggettoSterilizzante).intValue();
	}

	public int getVeterinarioAsl1() 
	{
		return veterinarioAsl1;
	}

	public void setVeterinarioAsl1(int veterinarioAsl1) 
	{
		this.veterinarioAsl1 = veterinarioAsl1;
	}
	
	public int getVeterinarioAsl2() 
	{
		return veterinarioAsl2;
	}

	public void setVeterinarioAsl2(int veterinarioAsl2) 
	{
		this.veterinarioAsl2 = veterinarioAsl2;
	}

	public int getIdSoggettoSterilizzante() {
		return idSoggettoSterilizzante;
	}

	public void setIdSoggettoSterilizzante(int idSoggettoSterilizzante) {
		this.idSoggettoSterilizzante = idSoggettoSterilizzante;
	}

	public void setIdSoggettoSterilizzante(String idSoggettoSterilizzante) {
		this.idSoggettoSterilizzante = new Integer(idSoggettoSterilizzante)
				.intValue();
	}
	
	

	public int getIdAslEsecutrice() {
		return idAslEsecutrice;
	}

	public void setIdAslEsecutrice(int idAslEsecutrice) {
		this.idAslEsecutrice = idAslEsecutrice;
	}
	
	public void setIdAslEsecutrice(String idAslEsecutrice) {
		this.idAslEsecutrice = new Integer(
				idAslEsecutrice).intValue();
	}
	


	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EventoSterilizzazione() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdAslProprietario() {
		return idAslProprietario;
	}

	public void setIdAslProprietario(int idAslProprietario) {
		this.idAslProprietario = idAslProprietario;
	}

	public EventoSterilizzazione(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.dataSterilizzazione = rs.getTimestamp("data_sterilizzazione");
		this.idTipologiaSoggettoSterilizzante = rs
				.getInt("tipologia_soggetto_sterilizzante");
		this.idProgettoSterilizzazioneRichiesto = rs
				.getInt("id_progetto_di_sterilizzazione_richiesto");
		this.idSoggettoSterilizzante = rs.getInt("id_soggetto_sterilizzante");
		this.flagContributoRegionale = rs.getBoolean("flag_richiesta_contributo_regionale");
		this.veterinarioAsl1 = rs.getInt("veterinario_asl_1");
		this.veterinarioAsl2 = rs.getInt("veterinario_asl_2");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public boolean insert(Connection db) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		try {

			super.insert(db);
			idEvento = super.getIdEvento();

			
			id = DatabaseUtils.getNextSeq(db, "evento_sterilizzazione_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append(" INSERT INTO evento_sterilizzazione(id_evento, data_sterilizzazione, flag_richiesta_contributo_regionale ");

			if (idProgettoSterilizzazioneRichiesto != -1) {
				sql.append(", id_progetto_di_sterilizzazione_richiesto");
			}

			if (idTipologiaSoggettoSterilizzante != -1) {
				sql.append(",tipologia_soggetto_sterilizzante");
			}
			
			if (idTipologiaSoggettoSterilizzante == 1 && veterinarioAsl1 != -1) {
				sql.append(",veterinario_asl_1");
			}
			
			if (idTipologiaSoggettoSterilizzante == 1 && veterinarioAsl2 != -1) {
				sql.append(",veterinario_asl_2");
			}

			if (idSoggettoSterilizzante != -1) {
				sql.append(",id_soggetto_sterilizzante");
			}

			if (idAslProprietario > -1) {
				sql.append(", id_asl_proprietario");
			}

			sql.append(")VALUES(?,?,?");

			if (idProgettoSterilizzazioneRichiesto != -1) {
				sql.append(",?");
			}

			if (idTipologiaSoggettoSterilizzante != -1) {
				sql.append(",?");
			}
			
			if (idTipologiaSoggettoSterilizzante == 1 && veterinarioAsl1 != -1) {
				sql.append(",?");
			}
			
			if (idTipologiaSoggettoSterilizzante == 1 && veterinarioAsl2 != -1) {
				sql.append(",?");
			}

			if (idSoggettoSterilizzante != -1) {
				sql.append(",?");
			}
			if (idAslProprietario > -1) {
				sql.append(", ?");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataSterilizzazione);
			pst.setBoolean(++i, flagContributoRegionale);

			if (idProgettoSterilizzazioneRichiesto != -1) {
				pst.setInt(++i, idProgettoSterilizzazioneRichiesto);
			}

			if (idTipologiaSoggettoSterilizzante != -1) {
				pst.setInt(++i, idTipologiaSoggettoSterilizzante);
			}
			
			if (idTipologiaSoggettoSterilizzante == 1 && veterinarioAsl1 != -1) {
				pst.setInt(++i, veterinarioAsl1);
			}
			
			if (idTipologiaSoggettoSterilizzante == 1 && veterinarioAsl2 != -1) {
				pst.setInt(++i, veterinarioAsl2);
			}

			if (idSoggettoSterilizzante != -1) {
				pst.setInt(++i, idSoggettoSterilizzante);
			}
			if (idAslProprietario > -1) {
				pst.setInt(++i, idAslProprietario);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db,
					"evento_sterilizzazione_id_seq", id);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	/*
	 * public static ArrayList getFields(Connection db){
	 * 
	 * ArrayList fields = new ArrayList(); HashMap fields1 = new HashMap();
	 * fields1.put("name", "dataEvento"); fields1.put("type", "data");
	 * fields1.put("label", "Data sterilizzazione"); fields.add(fields1);
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "flagContributoRegionale");
	 * fields1.put("type", "checkbox"); fields1.put("label",
	 * "Contributo regionale"); fields.add(fields1);
	 * 
	 * 
	 * 
	 * fields1 = new HashMap(); String html = ""; try { LookupList lookuplist =
	 * new LookupList(db, "lookup_razza"); lookuplist.addItem(-1,
	 * "--Seleziona--"); html =
	 * lookuplist.getHtmlSelect("idProgettoSterilizzazioneRichiesto", -1); }
	 * catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * fields1.put("name", "idProgettoSterilizzazioneRichiesto");
	 * fields1.put("type", "select"); fields1.put("label",
	 * "Progetto di sterilizzazione"); fields1.put("html", html);
	 * fields.add(fields1);
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "idSoggettoSterilizzante");
	 * fields1.put("type", "LP"); fields1.put("label", "Sterilizzatore");
	 * fields.add(fields1);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * return fields; }
	 */

	public EventoSterilizzazione(Connection db, int idEventoPadre)
			throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_sterilizzazione f on (e.id_evento = f.id_evento) where e.id_evento = ?");
		pst.setInt(1, idEventoPadre);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);

		if (rs.next()) {
			buildRecord(rs);
		}

		if (idEventoPadre == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}
	public void GetSterilizzazioneByIdAnimale(Connection db, int idAnimale) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e join evento_sterilizzazione f on (e.id_evento = f.id_evento) where e.id_animale = ? and e.data_cancellazione is null");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
		}

		if (idAnimale == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
		
		
	}
	public int update(Connection conn) throws SQLException {
		try {
			super.update(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int result = -1;
		try {
			
			StringBuffer sql = new StringBuffer();

			sql.append("update evento_sterilizzazione set data_sterilizzazione = ? where id_evento = ?");
			PreparedStatement pst = conn.prepareStatement(sql.toString());

			int i = 0;
			pst.setTimestamp(++i, this.dataSterilizzazione);
			pst.setInt(++i, this.getIdEvento());
			
			result = pst.executeUpdate();
			pst.close();
			
			}catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} 

		return result;
	}
	
	
	public EventoSterilizzazione salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale, Connection db) throws Exception{
	try{	
		
		super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
		 
		Animale oldAnimale = new Animale(db, this.getIdAnimale());
		
		switch (this.getSpecieAnimaleId()) {
		case Cane.idSpecie:
			thisAnimale = new Cane(db,
					this.getIdAnimale());
			break;
		case Gatto.idSpecie:
			thisAnimale = new Gatto(db,
					this.getIdAnimale());
			break;
		case Furetto.idSpecie:
			thisAnimale = new Furetto(db,
					this.getIdAnimale());
			break;
		default:
			break;
		}
		


		if (this.getIdTipologiaSoggettoSterilizzante() == 1) { // Se
			// il
			// soggetto
			// sterilizzante
			// è
			// asl
			this
					.setIdSoggettoSterilizzante(this.getIdAslEsecutrice());

			if (this.isFlagRegistrazioneForzata()) {
				this.setIdAslRiferimento(this
						.getIdSoggettoSterilizzante());
			}
		}
		// Animale oldAnimale = new Animale(db,
		// dettagli_base.getIdAnimale());
		int idAslProprietario = -1;
		if (oldAnimale.getIdProprietario() > 0) {
			idAslProprietario = ((Stabilimento) oldAnimale
					.getProprietario().getListaStabilimenti()
					.get(0)).getIdAsl();
		}
		this.setIdAslProprietario(idAslProprietario);
		this.insert(db);
		
		thisAnimale.setFlagSterilizzazione(true);
		thisAnimale.setDataSterilizzazione(this
				.getDataSterilizzazione());

		if (thisAnimale.getIdSpecie() != 3) { // SOLO PER CANI E
			// GATTI
			thisAnimale.setIdPraticaContributi(this
					.getIdProgettoSterilizzazioneRichiesto());

			thisAnimale.setFlagContributoRegionale(this
					.isFlagContributoRegionale());

			if (this
					.getIdProgettoSterilizzazioneRichiesto() > 0) { // DEVO
																	// AGGIORNARE
																	// CONTATORE
																	// PRATICA
				Pratica praticaContributi = new Pratica(
						db,
						this
								.getIdProgettoSterilizzazioneRichiesto());

				if (thisAnimale.isRandagio(db)) {
					praticaContributi
							.aggiornaCatturati(
									db,
									this
											.getIdProgettoSterilizzazioneRichiesto(),
									thisAnimale.getIdSpecie());
				} else {
					praticaContributi
							.aggiornaPadronali(
									db,
									this
											.getIdProgettoSterilizzazioneRichiesto(),
									thisAnimale.getIdSpecie());
				}
			}

		}
	


		
		
		aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
		aggiornaStatoAnimale(db, thisAnimale);
	
	}catch (Exception e){
		throw e;
	}
return this;
	}
	
	
	public EventoSterilizzazione build(ResultSet rs) throws Exception{
		try{	
			
			super.build(rs);
			buildRecord(rs);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
	
	

}
