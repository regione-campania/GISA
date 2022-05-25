/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.gestione_modifiche;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class CampoModificato extends GenericBean {

	private int idCampoModificato = -1;
	private int idModificaStatica = -1;
	private int idCampo = -1;
	private String nomeCampo = "";
	private String descrizioneCampo = "";
	private String valorePrecedente = "";
	private String valoreModificato = "";
	private String valorePrecedenteStringa = "";
	private String valoreModificatoStringa = "";

	private int idSpecie = -1;

	public int getIdCampoModificato() {
		return idCampoModificato;
	}

	public void setIdCampoModificato(int idCampoModificato) {
		this.idCampoModificato = idCampoModificato;
	}

	public int getIdModificaStatica() {
		return idModificaStatica;
	}

	public void setIdModificaStatica(int idModificaStatica) {
		this.idModificaStatica = idModificaStatica;
	}

	public int getIdCampo() {
		return idCampo;
	}

	public void setIdCampo(int idCampo) {
		this.idCampo = idCampo;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getDescrizioneCampo() {
		return descrizioneCampo;
	}

	public void setDescrizioneCampo(String descrizioneCampo) {
		this.descrizioneCampo = descrizioneCampo;
	}

	public String getValorePrecedente() {
		return valorePrecedente;
	}

	public void setValorePrecedente(String valorePrecedente) {
		this.valorePrecedente = valorePrecedente;
	}

	public String getValoreModificato() {
		return valoreModificato;
	}

	public void setValoreModificato(String valoreModificato) {
		this.valoreModificato = valoreModificato;
	}

	public int getIdSpecie() {
		return idSpecie;
	}

	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
	}

	public String getValorePrecedenteStringa() {
		return valorePrecedenteStringa;
	}

	public void setValorePrecedenteStringa(String valorePrecedenteStringa) {
		this.valorePrecedenteStringa = valorePrecedenteStringa;
	}

	public String getValoreModificatoStringa() {
		return valoreModificatoStringa;
	}

	public void setValoreModificatoStringa(String valoreModificatoStringa) {
		this.valoreModificatoStringa = valoreModificatoStringa;
	}

	public CampoModificato() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			

//			idCampo = DatabaseUtils.getNextSeqPostgres(db,
//					"lista_attributi_modificati_animale_id_seq");

			sql
					.append("INSERT INTO lista_attributi_modificati_animale("
							+ " id_modifica_statica, id_campo, valore_precedente, valore_attuale, valore_precedente_stringa, valore_modificato_stringa)"
							+ " VALUES ( ?, ?, ?, ?, ?, ?)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idModificaStatica);
			pst.setInt(++i, this.getIdCampoModificatoDaDb(db));
			pst.setString(++i, valorePrecedente);
			pst.setString(++i, valoreModificato);
			pst.setString(++i, valorePrecedenteStringa);
			pst.setString(++i, valoreModificatoStringa);

			pst.execute();
			pst.close();

			this.idCampo = DatabaseUtils.getCurrVal(db,
					"lista_attributi_modificati_animale_id_seq", idCampo);

			
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;
	}

	private int getIdCampoModificatoDaDb(Connection db) {
		int id = -1;
		String nomeClasse = "";
		String nomeClasseAnimale = "org.aspcfs.modules.anagrafe_animali.base.Animale";

		switch (this.idSpecie) {
		case Cane.idSpecie: {
			nomeClasse = "org.aspcfs.modules.anagrafe_animali.base.Cane";
			break;
		}
		case Gatto.idSpecie: {
			nomeClasse = "org.aspcfs.modules.anagrafe_animali.base.Gatto";
			break;
		}
		default: {
			break;
		}
		}

		try {
			String sql = "Select id from lista_campi_classi where nome_campo = ? and nome_classe IN (?, ?)";

			PreparedStatement pst = db.prepareStatement(sql);

			pst.setString(1, nomeCampo);
			pst.setString(2, nomeClasse);
			pst.setString(3, nomeClasseAnimale);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				id = rs.getInt("id");
			}

		} catch (SQLException e) {

		} finally {

		}

		return id;
	}

	private String getNomeCampoModificatoDaDb(Connection db) {
		String nomeCampo = "";

		try {
			String sql = "Select nome_campo from lista_campi_classi where id = ?";

			PreparedStatement pst = db.prepareStatement(sql);

			pst.setInt(1, idCampoModificato);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nomeCampo = rs.getString("nome_campo");
			}

		} catch (SQLException e) {

		} finally {

		}

		return nomeCampo;
	}

	private String getDescrizioneCampoModificatoDaDb(Connection db) {
		String descrizioneCampo = "";

		try {
			String sql = "Select descrizione_campo from lista_campi_classi where id = ?";

			PreparedStatement pst = db.prepareStatement(sql);

			pst.setInt(1, idCampoModificato);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				descrizioneCampo = rs.getString("descrizione_campo");
			}

		} catch (SQLException e) {

		} finally {

		}

		return descrizioneCampo;
	}

	public CampoModificato(Connection db, ResultSet rs) throws SQLException {
		this.idCampo = rs.getInt("id");
		this.idCampoModificato = rs.getInt("id_campo");
		this.idModificaStatica = rs.getInt("id_modifica_statica");
		this.valoreModificato = rs.getString("valore_attuale");
		this.valorePrecedente = rs.getString("valore_precedente");
		this.valorePrecedenteStringa = rs
				.getString("valore_precedente_stringa");
		this.valoreModificatoStringa = rs
				.getString("valore_modificato_stringa");
		this.nomeCampo = this.getNomeCampoModificatoDaDb(db);
		this.descrizioneCampo = this.getDescrizioneCampoModificatoDaDb(db);
	}

}
