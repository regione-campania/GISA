/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionecu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.darkhorseventures.framework.beans.GenericBean;

public class Motivo extends GenericBean {
	
	private int idTecnicaControllo ; 				// [ES. ispezione semplice]
	private String descrizioneTecnicaControllo ;
	
	private int idMotivoIspezione ;
	private String descrizioneMotivoIspezione ; 
	private String codiceInternoMotivoIspezione ;
	
	private int idPiano ;
	private String descrizionePiano ;
	private String codiceInternoPiano ;
	
	private String codiceEvento;

	public Motivo() {
		// TODO Auto-generated constructor stub
	}

	
	
	public Motivo(ResultSet rs) throws SQLException {
	buildRecord(rs);
	}



	private void buildRecord(ResultSet rs) throws SQLException {
		this.idMotivoIspezione = rs.getInt("id_tipo_ispezione");
		this.idPiano = rs.getInt("id_piano");
		this.descrizioneMotivoIspezione = rs.getString("descrizione_tipo_ispezione");
		this.descrizionePiano = rs.getString("descrizione_piano");
		this.codiceInternoMotivoIspezione = rs.getString("codice_int_tipo_ispe");
		this.codiceInternoPiano = rs.getString("codice_int_piano");
		this.descrizioneTecnicaControllo = rs.getString("tipo_attivita");
		this.codiceEvento = rs.getString("codice_evento");
	}



	public String getCodiceInternoMotivoIspezione() {
		return codiceInternoMotivoIspezione;
	}



	public void setCodiceInternoMotivoIspezione(String codiceInternoMotivoIspezione) {
		this.codiceInternoMotivoIspezione = codiceInternoMotivoIspezione;
	}



	public String getCodiceInternoPiano() {
		return codiceInternoPiano;
	}



	public void setCodiceInternoPiano(String codiceInternoPiano) {
		this.codiceInternoPiano = codiceInternoPiano;
	}



	public int getIdTecnicaControllo() {
		return idTecnicaControllo;
	}


	public void setIdTecnicaControllo(int idTecnicaControllo) {
		this.idTecnicaControllo = idTecnicaControllo;
	}


	public String getDescrizioneTecnicaControllo() {
		return descrizioneTecnicaControllo;
	}


	public void setDescrizioneTecnicaControllo(String descrizioneTecnicaControllo) {
		this.descrizioneTecnicaControllo = descrizioneTecnicaControllo;
	}


	public int getIdMotivoIspezione() {
		return idMotivoIspezione;
	}


	public void setIdMotivoIspezione(int idMotivoIspezione) {
		this.idMotivoIspezione = idMotivoIspezione;
	}


	public String getDescrizioneMotivoIspezione() {
		return descrizioneMotivoIspezione;
	}


	public void setDescrizioneMotivoIspezione(String descrizioneMotivoIspezione) {
		this.descrizioneMotivoIspezione = descrizioneMotivoIspezione;
	}


	public int getIdPiano() {
		return idPiano;
	}


	public void setIdPiano(int idPiano) {
		this.idPiano = idPiano;
	}


	public String getDescrizionePiano() {
		return descrizionePiano;
	}


	public void setDescrizionePiano(String descrizionePiano) {
		this.descrizionePiano = descrizionePiano;
	}
	
	public static ArrayList<Motivo> buildLista(Connection db, int riferimentoId, String riferimentoIdNomeTab, int anno, String tecnicaFiltro) {
		ArrayList<Motivo> lista = new ArrayList<Motivo>();
		try
		{
			String select = "select * from get_motivi_cu(?,?,?::text[])";
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			pst.setInt(1, 999);
			pst.setInt(2, anno);
			pst.setString(3, tecnicaFiltro);
			rs = pst.executeQuery();
			while (rs.next()){
				Motivo motivo = new Motivo(rs);
				lista.add(motivo);
			}
		}
		catch(SQLException e)
		{	e.printStackTrace();
		}
		return lista;
	}



	public String getCodiceEvento() {
		return codiceEvento;
	}



	public void setCodiceEvento(String codiceEvento) {
		this.codiceEvento = codiceEvento;
	}

}
