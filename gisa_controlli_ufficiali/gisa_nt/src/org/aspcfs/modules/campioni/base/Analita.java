/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;

public class Analita implements Serializable{
	private int idAnalita ;
	private String descrizione ; 
	
	private Timestamp esito_data ;
	private int esito_id ;
	private int esito_punteggio ;
	private int esito_responsabilita_positivita ;
	private boolean esito_allarme_rapido ;
	private boolean esito_segnalazione_informazioni ;
	private String esito_note_esame ;
	private String esito_motivazione_respingimento ;
	private String esito_nonconforme_nonc;
	
	public int getIdAnalita() {
		return idAnalita;
	}
	public void setIdAnalita(int idAnalita) {
		this.idAnalita = idAnalita;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Timestamp getEsito_data() {
		return esito_data;
	}
	public void setEsito_data(Timestamp esito_data) {
		this.esito_data = esito_data;
	}
	public void setEsito_data(String esito_data) {
		try {
			this.esito_data = DatabaseUtils.parseTimestamp2(esito_data);
		} catch(Exception e){		
		}
	}
	
	public int getEsito_id() {
		return esito_id;
	}
	public void setEsito_id(int esito_id) {
		this.esito_id = esito_id;
	}
	public void setEsito_id(String esito_id) {
		try {
			this.esito_id = Integer.parseInt(esito_id);
		} catch(Exception e){		
		}
	}
	public int getEsito_punteggio() {
		return esito_punteggio;
	}
	public void setEsito_punteggio(int esito_punteggio) {
		this.esito_punteggio = esito_punteggio;
	}
	public void setEsito_punteggio(String esito_punteggio) {
		try {
			this.esito_punteggio = Integer.parseInt(esito_punteggio);
		} catch(Exception e){		
		}
	}
	public int getEsito_responsabilita_positivita() {
		return esito_responsabilita_positivita;
	}
	public void setEsito_responsabilita_positivita(
			int esito_responsabilita_positivita) {
		this.esito_responsabilita_positivita = esito_responsabilita_positivita;
	}
	public void setEsito_responsabilita_positivita(String esito_responsabilita_positivita) {
		try {
			this.esito_responsabilita_positivita = Integer.parseInt(esito_responsabilita_positivita);
		} catch(Exception e){		
		}
	}
	public boolean isEsito_allarme_rapido() {
		return esito_allarme_rapido;
	}
	public void setEsito_allarme_rapido(boolean esito_allarme_rapido) {
		this.esito_allarme_rapido = esito_allarme_rapido;
	}
	public void setEsito_allarme_rapido(String esito_allarme_rapido) {
		try {
			this.esito_allarme_rapido = DatabaseUtils.parseBoolean(esito_allarme_rapido);
		} catch(Exception e){		
		}
	}
	public boolean isEsito_segnalazione_informazioni() {
		return esito_segnalazione_informazioni;
	}
	public void setEsito_segnalazione_informazioni(
			boolean esito_segnalazione_informazioni) {
		this.esito_segnalazione_informazioni = esito_segnalazione_informazioni;
	}
	public void setEsito_segnalazione_informazioni(String esito_segnalazione_informazioni) {
		try {
			this.esito_segnalazione_informazioni = DatabaseUtils.parseBoolean(esito_segnalazione_informazioni);
		} catch(Exception e){		
		}
	}
	public String getEsito_note_esame() {
		return esito_note_esame;
	}
	public void setEsito_note_esame(String esito_note_esame) {
		this.esito_note_esame = esito_note_esame;
	}
	public String getEsito_motivazione_respingimento() {
		return esito_motivazione_respingimento;
	}
	public void setEsito_motivazione_respingimento(
			String esito_motivazione_respingimento) {
		this.esito_motivazione_respingimento = esito_motivazione_respingimento;
	}

	
	public void updateEsito(Connection db,int idAnalita,int idCampione) throws SQLException
	{
		String sql = "UPDATE analiti_campioni "+
					"SET esito_data=?,esito_id=?, esito_punteggio=?, esito_responsabilita_positivita=?,esito_allarme_rapido=?, " +
					"esito_segnalazione_informazioni=?, esito_note_esame=?,esito_motivazione_respingimento=?, esito_nonconforme_nonc = ? "+
					"WHERE id_campione=? and analiti_id=? ";
		PreparedStatement pst = db.prepareStatement(sql);
		int i = 0 ;
		pst.setTimestamp(++i, esito_data);
		pst.setInt(++i, esito_id);
		pst.setInt(++i, esito_punteggio);
		pst.setInt(++i, esito_responsabilita_positivita);
		pst.setBoolean(++i, esito_allarme_rapido);
		pst.setBoolean(++i, esito_segnalazione_informazioni);
		pst.setString(++i, esito_note_esame);
		pst.setString(++i, esito_motivazione_respingimento);
		pst.setString(++i, esito_nonconforme_nonc);
		pst.setInt(++i, idCampione);
		pst.setInt(++i, idAnalita); 
		pst.execute(); 
		
		String aggiornaPunteggio = "update ticket set punteggio = (select sum(esito_punteggio) from analiti_campioni where id_campione= ? ) where ticketid = ? ";
		pst=db.prepareStatement(aggiornaPunteggio);
		pst.setInt(1, idCampione);
		pst.setInt(2, idCampione);
		pst.execute();


		
		
		
		
		
	}
	
	public int getIdAnalitaDaNome(Connection db,String nome) throws SQLException
	{
		String sql = "SELECT * from analiti where nome = ?";
		PreparedStatement pst = db.prepareStatement(sql);
		int i = 0 ;
		pst.setString(++i, nome);
		ResultSet rs = pst.executeQuery();
		int result = -1;
		if (rs.next()){
			result = rs.getInt("analiti_id");
		}
		return result;		
		
	}
	public String getEsito_nonconforme_nonc() {
		return esito_nonconforme_nonc;
	}
	public void setEsito_nonconforme_nonc(String esito_nonconforme_nonc) {
		this.esito_nonconforme_nonc = esito_nonconforme_nonc;
	}
	public ArrayList<Integer> getEsito_nonconforme_noncList() { 
		ArrayList<Integer> listaMotivi = new ArrayList<Integer>();
		if (esito_nonconforme_nonc!=null){
			  String[] nonc = esito_nonconforme_nonc.split(",");
			  for (int n=0; n<nonc.length; n++)
				  listaMotivi.add(Integer.parseInt(nonc[n].trim()));
		}
		return listaMotivi;
	}

	
}
