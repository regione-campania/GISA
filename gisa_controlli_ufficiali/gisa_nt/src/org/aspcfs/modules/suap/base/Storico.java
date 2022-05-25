/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.aspcfs.modules.registrotrasgressori.base.Trasgressione;
import org.aspcfs.utils.DatabaseUtils;


public class Storico {
	

	private int id = -1;
	private int idOperazione = -1;
	private int idUtente = -1;
	private Timestamp dataOperazione = null;
	private String note = null;
	private int idStabilimento = -1;
	
	private String codFiscaleUtenteRichiedente="";
	private String codFiscaleUtenteDelegato="";
	
	public static int INSERIMENTO_SCIA = 1;
	public static int INSERIMENTO_NON_PRESENTE = 6;
	public static int INSERIMENTO_RICONOSCIUTO = 7;
	public static int INSERIMENTO_LINEA_PRODUTTIVA = 9;
	
	public static int REGISTRAZIONE_NON_DISPONIBILE = 2;
	public static int REGISTRAZIONE_DISPONIBILE = 5;
	public static int APPROVAL_DISPONIBILE = 8;
	public static int CESSAZIONE_PER_CAMBIO_TITOLARITA = 12 ;
	
	public static int CESSAZIONE_STABILIMENTO_LINEA_PRODUTTIVA= 13 ;
	
	public static int VERIFICA_OK = 3;
	public static int VERIFICA_KO = 4;
	
	public static int IMPORT = 10;
	public static int AGGIORNAMENTO_LINEE = 11;
	
	
	 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdOperazione() {
		return idOperazione;
	}
	public void setIdOperazione(int idOperazione) {
		this.idOperazione = idOperazione;
	}
	
	public void setIdOperazione(String idOperazione) {
		if (idOperazione!=null && !idOperazione.equals(""))
		this.idOperazione = Integer.parseInt(idOperazione);
	}
	
		
	public Timestamp getDataOperazione() {
		return dataOperazione;
	}
	public void setDataProt(Timestamp dataOperazione) {
		this.dataOperazione = dataOperazione;
	}
		
	public void setDataProt(String dataOperazione) {
		this.dataOperazione = DatabaseUtils.parseDateToTimestamp(dataOperazione);
	}
	
	
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public void setIdUtente(String idUtente) {
		if (idUtente!=null && !idUtente.equals(""))
		this.idUtente = Integer.parseInt(idUtente);
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	public Storico() {
 
	}
	
	public Storico(ResultSet rs) throws SQLException {

		this.id = rs.getInt("id");
		this.idOperazione = rs.getInt("id_operazione");
		this.idUtente = rs.getInt("id_utente");
		this.dataOperazione = rs.getTimestamp("data_operazione");
		this.note = rs.getString("note");
		this.codFiscaleUtenteRichiedente = rs.getString("cf_richiedente");
		this.codFiscaleUtenteDelegato = rs.getString("cf_delegato");
	}
	


public boolean insert(Connection db) {
	
	StringBuffer sql = new StringBuffer(); 

	sql.append("INSERT INTO suap_storico(");
	sql.append("id_operazione, id_stabilimento, id_utente, note, cf_richiedente, cf_delegato, data_operazione ");
	sql.append(")");
	sql.append("VALUES ( ");
	sql.append("?, ?, ?, ?,?, ?, now() ");
	sql.append(")");
	int i = 0;
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
		
		pst.setInt(++i, this.getIdOperazione());
		pst.setInt(++i, this.getIdStabilimento());
		pst.setInt(++i, this.getIdUtente());
		pst.setString(++i, this.getNote());
		pst.setString(++i, this.getCodFiscaleUtenteRichiedente());
		pst.setString(++i, this.getCodFiscaleUtenteDelegato());
		pst.execute();
		pst.close();

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	return true;

}
public int getIdStabilimento() {
	return idStabilimento;
}
public void setIdStabilimento(int idStabilimento) {
	this.idStabilimento = idStabilimento;
}

public String getCodFiscaleUtenteRichiedente() {
	return codFiscaleUtenteRichiedente;
}
public void setCodFiscaleUtenteRichiedente(String codFiscaleUtenteRichiedente) {
	this.codFiscaleUtenteRichiedente = codFiscaleUtenteRichiedente;
}
public String getCodFiscaleUtenteDelegato() {
	return codFiscaleUtenteDelegato;
}
public void setCodFiscaleUtenteDelegato(String codFiscaleUtenteDelegato) {
	this.codFiscaleUtenteDelegato = codFiscaleUtenteDelegato;
}


public Vector cercaStoricoPratica(Connection db, int idStabilimento){
	ResultSet rs = null;
	Vector storicoList = new Vector();
	PreparedStatement pst;
	try {
		
		String query = "select * from suap_storico where id_stabilimento = ? and enabled order by data_operazione asc";
		pst = db.prepareStatement(query);
		pst.setInt(1, idStabilimento);
		rs = DatabaseUtils.executeQuery(db, pst); 
		 while (rs.next()){
				 Storico sto = new Storico(rs);
				 storicoList.add(sto);
			 }
	rs.close();
	pst.close();
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return storicoList;
}







}




	

