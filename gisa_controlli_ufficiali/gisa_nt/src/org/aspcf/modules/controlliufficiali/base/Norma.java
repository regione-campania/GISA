/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Norma implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code = -1;
	private String description ;
	private boolean gruppo;
	private boolean viewDiffida ;
	private boolean enabled ;
	private String noteHd;
	private String ordinamento ;
	
	private String codiceTariffa;
	private boolean competenzaUod;
	
	
	
	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public boolean isViewDiffida() {
		return viewDiffida;
	}



	public void setViewDiffida(boolean viewDiffida) {
		this.viewDiffida = viewDiffida;
	}

	public void setViewDiffida(String viewDiffida) {
		if (viewDiffida!=null && (viewDiffida.equals("on") || viewDiffida.equals("true")))
				this.viewDiffida = true;
		else
			this.viewDiffida = false;
	}


	public boolean isEnabled() {
		return enabled;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setEnabled(String enabled) {
		if (enabled!=null && (enabled.equals("on") || enabled.equals("true")))
				this.enabled = true;
		else
			this.enabled = false;
	}




	public String getNoteHd() {
		return noteHd;
	}



	public void setNoteHd(String noteHd) {
		this.noteHd = noteHd;
	}



	public String getOrdinamento() {
		return ordinamento;
	}



	public void setOrdinamento(String ordinamento) {
		this.ordinamento = ordinamento;
	}



	public String getCodiceTariffa() {
		return codiceTariffa;
	}



	public void setCodiceTariffa(String codiceTariffa) {
		this.codiceTariffa = codiceTariffa;
	}



	public boolean isCompetenzaUod() {
		return competenzaUod;
	}



	public void setCompetenzaUod(boolean competenzaUod) {
		this.competenzaUod = competenzaUod;
	}

	public void setCompetenzaUod(String competenzaUod) {
		if (competenzaUod!=null && (competenzaUod.equals("on") || competenzaUod.equals("true")))
				this.competenzaUod = true;
		else
			this.competenzaUod = false;
	}


	public boolean isGruppo() {
		return gruppo;
	}



	public void setGruppo(boolean gruppo) {
		this.gruppo = gruppo;
	}

	public void setGruppo(String gruppo) {
		if (gruppo!=null && (gruppo.equals("on") || gruppo.equals("true")))
				this.gruppo = true;
		else
			this.gruppo = false;
	}

	public Norma () {
		
	}
	
	public Norma(ResultSet rs) throws SQLException{
		buildRecord(rs);
	}
	
	public Norma(Connection db, int code) throws SQLException{
		PreparedStatement pst = db.prepareStatement("select * from lookup_norme where code = ?");
		pst.setInt(1, code);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		
	}
	
	private void buildRecord(ResultSet rs) throws SQLException{
		code = rs.getInt("code");
		description = rs.getString("description");
		viewDiffida = rs.getBoolean("view_diffida");
		gruppo = rs.getBoolean("gruppo");
		enabled = rs.getBoolean("enabled");
		ordinamento = rs.getString("ordinamento");
		codiceTariffa = rs.getString("codice_tariffa");
		competenzaUod = rs.getBoolean("competenza_uod");

	}
	
	public void insert(Connection db, int idUtente) throws SQLException{
		PreparedStatement pst = db.prepareStatement("insert into lookup_norme (code, description, enabled, view_diffida, gruppo, ordinamento, codice_tariffa, competenza_uod, note_hd) values ((select max(code)+1 from lookup_norme), ?, ?,?, ?, ?, ?, ?, concat_ws(';', now(), ?));");
		int i = 0;
		pst.setString(++i, description);
		pst.setBoolean(++i, enabled);
		pst.setBoolean(++i, viewDiffida);
		pst.setBoolean(++i, gruppo);
		pst.setString(++i, ordinamento);
		pst.setString(++i, codiceTariffa);
		pst.setBoolean(++i, competenzaUod);
		pst.setString(++i, "Inserito da utente "+idUtente);
		pst.executeUpdate();
		
		pst = db.prepareStatement("SELECT * FROM aggiorna_ordinamento_norme()");
		pst.executeQuery();
	}


	public void update(Connection db, int idUtente) throws SQLException{
		PreparedStatement pst = db.prepareStatement("update lookup_norme set trashed_date = now(), note_hd = concat_ws(';', note_hd, now(), ?) where code = ?");
		pst.setString(1, "Disabilitato in seguito a modifica da utente "+idUtente);
		pst.setInt(2, code);
		pst.executeUpdate();
		insert(db, idUtente); 
	}
	
	
}
