/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.stabilimenti.storico;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class CampoModificato extends GenericBean {

	private int idCampoModificato = -1;
	private int idStoricoModifica = -1;
	private int idCampo = -1;
	private String nomeCampo = "";
	private String nomeClasse = "";
	private String descrizioneCampo = "";
	private String valorePrecedente = "";
	private String valoreModificato = "";
	private String valorePrecedenteStringa = "";
	private String valoreModificatoStringa = "";

	public int getIdCampoModificato() {
		return idCampoModificato;
	}

	public void setIdCampoModificato(int idCampoModificato) {
		this.idCampoModificato = idCampoModificato;
	}

	public int getIdStoricoModifica() {
		return idStoricoModifica;
	}

	public void setIdStoricoModifica(int idStoricoModifica) {
		this.idStoricoModifica = idStoricoModifica;
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

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
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

	public boolean insert(Connection db, String bean,ActionContext context) throws SQLException {

		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}

			idCampo = DatabaseUtils.getNextSeqInt(db,context,"lista_attributi_modificati_stab","id_campo");
			

			sql
					.append("INSERT INTO lista_attributi_modificati_stab("
							+ " id_storico_modif, id_campo, valore_precedente, valore_attuale, valore_precedente_stringa, valore_modificato_stringa)"
							+ " VALUES ( ?, ?, ?, ?, ?, ?)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idStoricoModifica);
			pst.setInt(++i, this.getIdCampoModificatoDaDb(db,bean));
			pst.setString(++i, valorePrecedente);
			pst.setString(++i, valoreModificato);
			pst.setString(++i, valorePrecedenteStringa);
			pst.setString(++i, valoreModificatoStringa);

			pst.execute();
			pst.close();

		
			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}

	private int getIdCampoModificatoDaDb(Connection db, String nome) {
		int id = -1;
		String nomeClasse = "org.aspcfs.modules.stabilimenti.base."+nome;

		try {
			String sql = "Select id from lista_campi_classi where nome_campo = ? and nome_classe = ?";

			PreparedStatement pst = db.prepareStatement(sql);

			pst.setString(1, nomeCampo);
			pst.setString(2, nomeClasse);
		

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

	private String getNomeClasseDaDb(Connection db) {
		String classe = "";

		try {
			String sql = "Select nome_classe from lista_campi_classi where id = ?";

			PreparedStatement pst = db.prepareStatement(sql);

			pst.setInt(1, idCampoModificato);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				classe = rs.getString("nome_classe");
			}

		} catch (SQLException e) {

		} finally {

		}

		return classe;
	}
	
	
	public CampoModificato(Connection db, ResultSet rs) throws SQLException {
		this.idCampo = rs.getInt("id");
		this.idCampoModificato = rs.getInt("id_campo");
		//this.idCampoModificato = rs.getInt("id_campo"); mi serve??
		this.idStoricoModifica = rs.getInt("id_storico_modif");
		this.valoreModificato = rs.getString("valore_attuale");
		this.valorePrecedente = rs.getString("valore_precedente");
		this.valorePrecedenteStringa = rs
				.getString("valore_precedente_stringa");
		this.valoreModificatoStringa = rs
				.getString("valore_modificato_stringa");
		this.nomeCampo = this.getNomeCampoModificatoDaDb(db);
		this.descrizioneCampo = this.getDescrizioneCampoModificatoDaDb(db);
		this.nomeClasse = this.getNomeClasseDaDb(db);
		
	}

}
