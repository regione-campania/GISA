/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiesteerratacorrige.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RichiestaErrataCorrigeCampo {
	
	private int id = -1;
	private int idErrataCorrige = -1;
	private int idLookupInfoDaModificare = -1;
	private String datoErrato = "";
	private String datoCorretto="";
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdErrataCorrige() {
		return idErrataCorrige;
	}

	public void setIdErrataCorrige(int idErrataCorrige) {
		this.idErrataCorrige = idErrataCorrige;
	}

	public int getIdLookupInfoDaModificare() {
		return idLookupInfoDaModificare;
	}

	public void setIdLookupInfoDaModificare(int idLookupInfoDaModificare) {
		this.idLookupInfoDaModificare = idLookupInfoDaModificare;
	}

	public String getDatoErrato() {
		return datoErrato;
	}

	public void setDatoErrato(String datoErrato) {
		this.datoErrato = datoErrato;
	}

	public String getDatoCorretto() {
		return datoCorretto;
	}

	public void setDatoCorretto(String datoCorretto) {
		this.datoCorretto = datoCorretto;
	}

	public RichiestaErrataCorrigeCampo() {
 
	}
	
	public RichiestaErrataCorrigeCampo(ResultSet rs) throws SQLException {

		this.id = rs.getInt("id");
		this.idErrataCorrige = rs.getInt("id_richieste_errata_corrige");
		this.idLookupInfoDaModificare = rs.getInt("id_richieste_errata_corrige_lookup_info_da_modificare");
		this.datoErrato = rs.getString("dato_errato");
		this.datoCorretto = rs.getString("dato_corretto");
	}

	public void insert(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into richieste_errata_corrige_campi (id_richieste_errata_corrige, id_richieste_errata_corrige_lookup_info_da_modificare, dato_errato, dato_corretto)  values (?, ?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idErrataCorrige);
		pst.setInt(++i, idLookupInfoDaModificare);
		pst.setString(++i, datoErrato);
		pst.setString(++i, datoCorretto);

		if (!datoErrato.equals("") || !datoCorretto.equals(""))
			pst.execute();
		
	}

	
}




	

