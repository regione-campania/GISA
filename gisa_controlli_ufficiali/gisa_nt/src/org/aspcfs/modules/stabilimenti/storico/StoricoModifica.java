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
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class StoricoModifica extends GenericBean {

	private int idModifica = -1;
	private int idStabilimento = -1;
	private int idUtente = -1;
	private java.sql.Timestamp dataModifica = null;
	private String motivazioneModifica = "";
	private int nrCampiModificati = 0;
	private ArrayList<CampoModificato> listaCampiModificati = new ArrayList<CampoModificato>();
	private String nomeClasse;
	private String nomeCampo;
	private String tipoCampo;
	
	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public StoricoModifica() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdModifica() {
		return idModifica;
	}

	public void setIdModifica(int idModifica) {
		this.idModifica = idModifica;
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public java.sql.Timestamp getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(java.sql.Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}

	public int getNrCampiModificati() {
		return nrCampiModificati;
	}

	public void setNrCampiModificati(int nrCampiModificati) {
		this.nrCampiModificati = nrCampiModificati;
	}

	public String getMotivazioneModifica() {
		return motivazioneModifica;
	}

	public void setMotivazioneModifica(String motivazioneModifica) {
		this.motivazioneModifica = motivazioneModifica;
	}
	
	public ArrayList<CampoModificato> getListaCampiModificati() {
		return listaCampiModificati;
	}

	public void setListaCampiModificati(
			ArrayList<CampoModificato> listaCampiModificati) {
		this.listaCampiModificati = listaCampiModificati;
	}

	public boolean insert(Connection db, String nomeBean,ActionContext context) throws SQLException {

		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}

			 int nextSeq = -1;
             String nexCode = "select nextval('storico_modif_stab_id_seq')";
             PreparedStatement pst = db.prepareStatement(nexCode);
             ResultSet rs = pst.executeQuery();
             if (rs.next()){
                nextSeq=rs.getInt(1);
             }
             if(nextSeq>-1) 
            	 idModifica=nextSeq;
	
			sql.append("INSERT INTO storico_modif_stab("
							+ " id_stabilimento, id_utente, data_modifica, nr_campi_modificati,"
							+ " note, id)"
							+ "VALUES (?, ?, ?, ?, ?, ?)");

			int i = 0;
			pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idStabilimento);
			pst.setInt(++i, idUtente);
			pst.setTimestamp(++i, dataModifica);
			pst.setInt(++i, nrCampiModificati);
			pst.setString(++i, motivazioneModifica);
			pst.setInt(++i, idModifica);

			pst.execute();
			pst.close();

		
			for (int k = 0; k < listaCampiModificati.size(); k++) {
				CampoModificato campo = (CampoModificato) listaCampiModificati.get(k);
				campo.setIdStoricoModifica(this.idModifica);
				campo.insert(db, nomeBean,context);
			}

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
	
	
	public StoricoModifica(ResultSet rs) throws SQLException {
		this.buildRecord(rs);
	}
	
	
	public StoricoModifica(Connection db, int idModifica) throws SQLException {
		String sql = "select * from storico_modif_stab where id = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idModifica);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			this.buildRecord(rs);
			this.getListaCampiModificatiDb(db);
		}
	}
	
	
	private void buildRecord(ResultSet rs)  throws SQLException {
		this.idStabilimento = rs.getInt("id_stabilimento");
		this.idModifica = rs.getInt("id");
		this.dataModifica = rs.getTimestamp("data_modifica");
		this.idUtente = rs.getInt("id_utente");
		this.nrCampiModificati = rs.getInt("nr_campi_modificati");
		this.motivazioneModifica = rs.getString("note");
		
	}
	
	
	public void getListaCampiModificatiDb(Connection db) throws SQLException{
		String sqlQuery = "Select l.*, lc.nome_campo, lc.tipo_campo, lc.nome_classe from lista_attributi_modificati_stab l "+
		" left join lista_campi_classi lc on l.id_campo = lc.id " +
		" where id_storico_modif = ?";
		
		PreparedStatement pst = db.prepareStatement(sqlQuery);
		pst.setInt(1, idModifica);
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()){
			CampoModificato campo = new CampoModificato(db, rs);
			this.listaCampiModificati.add(campo);
			
		}
	}

}
