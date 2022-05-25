/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionecu.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.darkhorseventures.framework.beans.GenericBean;

public class Anagrafica extends GenericBean {

	private String ragioneSociale;
	private String partitaIva;
	private String numRegistrazione;

	private int riferimentoId;
	private String riferimentoIdNomeTab;


	public Anagrafica() { 

	}

	public Anagrafica(Connection db, int riferimentoId, String riferimentoIdNomeTab) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from public.get_anagrafica_by_id(?,?);");
		pst.setInt(1, riferimentoId);
		pst.setString(2, riferimentoIdNomeTab);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
		}

	}

	private void buildRecord(ResultSet rs) throws SQLException{
		this.riferimentoId = rs.getInt("riferimento_id");
		this.riferimentoIdNomeTab = rs.getString("riferimento_id_nome_tab");
		this.ragioneSociale = rs.getString("ragione_sociale");
		this.partitaIva = rs.getString("partita_iva");
		this.numRegistrazione = rs.getString("n_reg");

	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public int getRiferimentoId() {
		return riferimentoId;
	}
	public void setRiferimentoId(int riferimentoId) {
		this.riferimentoId = riferimentoId;
	}
	public String getRiferimentoIdNomeTab() {
		return riferimentoIdNomeTab;
	}
	public void setRiferimentoIdNomeTab(String riferimentoIdNomeTab) {
		this.riferimentoIdNomeTab = riferimentoIdNomeTab;
	}

	public String getNumRegistrazione() {
		return numRegistrazione;
	}

	public void setNumRegistrazione(String numRegistrazione) {
		this.numRegistrazione = numRegistrazione;
	}

}
