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

import org.aspcfs.utils.DatabaseUtils;


public class StoricoRichieste {
	

	private int id = -1;
	private int idSuapRicSciaOperatore = -1;
	private Timestamp dataOperazione = null;
	private int idStabilimento = -1;
	private int idUtente = -1;
	private Stabilimento richiesta = null;
	
	
	
	 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		
	
	public StoricoRichieste() {
 
	}
	
	public StoricoRichieste(ResultSet rs,Connection db,boolean flag) throws Exception
	{
		this(rs,db);
		Stabilimento stab = new Stabilimento();
		stab.queryRecordStabilimentoIdOperatore(db, this.getIdSuapRicSciaOperatore());
		this.setRichiesta(stab);
	}
	
	public StoricoRichieste(ResultSet rs, Connection db) throws Exception {

		this.id = rs.getInt("id");
		this.idStabilimento = rs.getInt("id_stabilimento");
		this.dataOperazione = rs.getTimestamp("data_operazione");
		this.setIdSuapRicSciaOperatore(rs.getInt("id_suap_ric_scia_operatore"));
		this.setIdUtente(rs.getInt("id_utente"));
		Stabilimento stab = new Stabilimento();
		stab.queryRecordStabilimentoIdOperatore(db, this.getIdSuapRicSciaOperatore());
		this.setRichiesta(stab);
		
		
	}
	
public int getIdStabilimento() {
	return idStabilimento;
}
public void setIdStabilimento(int idStabilimento) {
	this.idStabilimento = idStabilimento;
}



public Vector cercaStoricoRichieste(Connection db, int idStabilimento) throws Exception{
	ResultSet rs = null;
	Vector storicoList = new Vector();
	PreparedStatement pst;
	try {
		
		String query = "select * from suap_storico_richieste where id_stabilimento = ? and enabled order by data_operazione desc";
		pst = db.prepareStatement(query);
		pst.setInt(1, idStabilimento);
		rs = DatabaseUtils.executeQuery(db, pst); 
		 while (rs.next()){
				 StoricoRichieste sto = new StoricoRichieste(rs, db);
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


public Vector cercaStoricoRichiesteVersioneConPendenti(Connection db, int idStabilimento) throws Exception{
	ResultSet rs = null;
	Vector storicoList = new Vector();
	PreparedStatement pst;
	try {
		
		String query = "select * from suap_storico_richieste where id_stabilimento = ? and enabled order by data_operazione desc";
		pst = db.prepareStatement(query);
		pst.setInt(1, idStabilimento);
		rs = DatabaseUtils.executeQuery(db, pst); 
		 while (rs.next()){
				 StoricoRichieste sto = new StoricoRichieste(rs, db,true);
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


public int getIdSuapRicSciaOperatore() {
	return idSuapRicSciaOperatore;
}
public void setIdSuapRicSciaOperatore(int idSuapRicSciaOperatore) {
	this.idSuapRicSciaOperatore = idSuapRicSciaOperatore;
}
public Stabilimento getRichiesta() {
	return richiesta;
}
public void setRichiesta(Stabilimento richiesta) {
	this.richiesta = richiesta;
}
public int getIdUtente() {
	return idUtente;
}
public void setIdUtente(int idUtente) {
	this.idUtente = idUtente;
}







}




	

