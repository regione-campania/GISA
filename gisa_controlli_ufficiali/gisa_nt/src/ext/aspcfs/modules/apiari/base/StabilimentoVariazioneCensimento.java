/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StabilimentoVariazioneCensimento {
	
	private Timestamp dataCensimento ;
	private Timestamp dataAssegnazioneCensimento;
	private String note ;
	private int numAlveari ;
	private int numSciami ;
	private int numPacchi ;

	private int numRegine ;

	private int idApicoltoreApiario ;
	private int idApicoltore ;
	
	private int id ;
	private int idBda ;
	private Timestamp entered ;
	


	public int getIdBda() {
		return idBda;
	}



	public void setIdBda(int idBda) {
		this.idBda = idBda;
	}

	public Timestamp getDataCensimento() {
		return dataCensimento;
	}



	public void setDataCensimento(Timestamp dataCensimento) {
		this.dataCensimento = dataCensimento;
	}
	
	public Timestamp getEntered() {
		return entered;
	}



	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	
	public Timestamp getDataAssegnazioneCensimento() {
		return dataAssegnazioneCensimento;
	}



	public void setDataAssegnazioneCensimento(Timestamp dataAssegnazioneCensimento) {
		this.dataAssegnazioneCensimento = dataAssegnazioneCensimento;
	}
	
	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public int getNumAlveari() {
		return numAlveari;
	}



	public void setNumAlveari(int numAlveari) {
		this.numAlveari = numAlveari;
	}



	public int getNumSciami() {
		return numSciami;
	}



	public void setNumSciami(int numSciami) {
		this.numSciami = numSciami;
	}



	public int getIdApicoltoreApiario() {
		return idApicoltoreApiario;
	}
	


	public void setIdApicoltoreApiario(int idApicoltoreApiario) {
		this.idApicoltoreApiario = idApicoltoreApiario;
	}

	

	public int getNumPacchi() {
		return numPacchi;
	}



	public void setNumPacchi(int numPacchi) {
		this.numPacchi = numPacchi;
	}

	public void setNumPacchi(String numPacchi) {
		if (numPacchi!=null && !"".equals(numPacchi))
			this.numPacchi = Integer.parseInt(numPacchi);
	}


	public int getNumRegine() {
		return numRegine;
	}



	public void setNumRegine(int numRegine) {
		this.numRegine = numRegine;
	}


	public void setNumRegine(String numRegine) {
		if (numRegine!=null && !"".equals(numRegine))
			this.numRegine = Integer.parseInt(numRegine);
	}

	public int getIdApicoltore() {
		return idApicoltore;
	}



	public void setIdApicoltore(int idApicoltore) {
		this.idApicoltore = idApicoltore;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	//table : apicoltura_apiari_variazioni_censimenti
	public void buildRecord(ResultSet rs) throws SQLException
	{
		entered =  rs.getTimestamp("entered");
		dataCensimento =  rs.getTimestamp("data_assegnazione_censimento");
		dataAssegnazioneCensimento =  rs.getTimestamp("data_assegnazione_censimento");
		if(dataCensimento==null)
			dataCensimento =  rs.getTimestamp("data_censimento");
		numSciami = rs.getInt("num_sciami");
		numAlveari= rs.getInt("num_alveari");
		numPacchi = rs.getInt("num_pacchi");
		numRegine = rs.getInt("num_regine");
		idBda = rs.getInt("id_bda");
		id = rs.getInt("id");
		idApicoltoreApiario = rs.getInt("id_apicoltura_apiario");
		
	}

	public StabilimentoVariazioneCensimento(Connection db, int id) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from apicoltura_apiari_variazioni_censimenti where id = "+id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}



	public StabilimentoVariazioneCensimento() {
		// TODO Auto-generated constructor stub
	}

	
}
