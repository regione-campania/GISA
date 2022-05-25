/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LineaProduttivaValidazione  {
	
	private int id ;
	private String descrizioneArea ;
	private String permessoValidazione ;
	private String descrizioneRuolo ;
	private int idRuolo ;

	private int esitoValidazioneScia ;
	private int validatoDa ;
	private Timestamp validatoData ;
	private String noteValidazione ;
	
	
	public int getIdRuolo() {
		return idRuolo;
	}
	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescrizioneArea() {
		return descrizioneArea;
	}
	public void setDescrizioneArea(String descrizioneArea) {
		this.descrizioneArea = descrizioneArea;
	}
	public String getPermessoValidazione() {
		return permessoValidazione;
	}
	public void setPermessoValidazione(String permessoValidazione) {
		this.permessoValidazione = permessoValidazione;
	}
	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}
	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}
	public int getEsitoValidazioneScia() {
		return esitoValidazioneScia;
	}
	public void setEsitoValidazioneScia(int esitoValidazioneScia) {
		this.esitoValidazioneScia = esitoValidazioneScia;
	}
	public int getValidatoDa() {
		return validatoDa;
	}
	public void setValidatoDa(int validatoDa) {
		this.validatoDa = validatoDa;
	}
	public Timestamp getValidatoData() {
		return validatoData;
	}
	public void setValidatoData(Timestamp validatoData) {
		this.validatoData = validatoData;
	}
	public String getNoteValidazione() {
		return noteValidazione;
	}
	public void setNoteValidazione(String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}
	

	public void buildRecord(ResultSet rs) throws SQLException{
		
		this.setIdRuolo(rs.getInt("id_ruolo"));
		this.setDescrizioneRuolo(rs.getString("descrizione_ruolo"));
		this.setPermessoValidazione(rs.getString("nome_permeso"));
		this.setValidatoData(rs.getTimestamp("data_validazione"));
		this.setValidatoDa(rs.getInt("utente_validazione"));
		this.setNoteValidazione(rs.getString("note_validazione"));
		this.setId(rs.getInt("id_suap_ric_scia_validazione_rel_stab_lp"));
		this.setEsitoValidazioneScia(rs.getInt("stato"));
	}
	
	
	

}
