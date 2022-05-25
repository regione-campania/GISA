/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.noscia.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.gestioneanagrafica.base.SoggettoFisico;
import org.aspcfs.utils.Bean;


public class SoggettoFisicoDAO extends GenericDAO
{
	public SoggettoFisico soggettofisico;
	public String piva;

	
	//Costruttore 1: tutti i filtri vuoti - utile per tirare fuori tutti i record
	public SoggettoFisicoDAO(){}
	
	//Costruttore 2: solo id valorizzato - utile per ricerche puntuali
	public SoggettoFisicoDAO(Integer id)
	{
		soggettofisico = new SoggettoFisico(id);
	}
	
	public SoggettoFisicoDAO(ResultSet rs) throws SQLException 
	{
		Bean.populate(this, rs);
	}
			
	public SoggettoFisicoDAO(SoggettoFisico soggettofisico) 
	{
		this.soggettofisico=soggettofisico;
	}
		
		
	public ArrayList<SoggettoFisico> checkEsistenza(Connection conn) throws SQLException 
	{
		String sql = " select cognome, nome, codice_fiscale, telefono, email, data_nascita, id_comune_nascita as \"comune_nascita.id\" , comune_nascita_nome as \"comune_nascita.nome\""
				+    " ,documento_identita, sesso, telefono, id_nazionalita_nascita as \"comune_nascita.provincia.nazione.code\", nazionalita_nascita as \"comune_nascita.provincia.nazione.description\""
				+    " ,id_provincia_residenza as \"indirizzo.provincia.code\", provincia_residenza as\"indirizzo.provincia.description\", id_comune_residenza as \"indirizzo.comune.id\", comune_residenza_nome as  \"indirizzo.comune.nome\""
				+	 " ,id_toponimo_residenza as \"indirizzo.toponimo.code\", toponimo_residenza as \"indirizzo.toponimo.description\""
				+    " ,via_residenza as \"indirizzo.via\", civico_residenza as \"indirizzo.civico\", cap_residenza as \"indirizzo.cap\", id_nazione_residenza as \"indirizzo.nazione.code\", nazione_residenza as \"indirizzo.nazione.description\""
				+ 	 " from  public_functions.cerca_verifica_soggetto(null,null,null,null,null,?)";
		

		PreparedStatement st = conn.prepareStatement(sql);
		st.setString( 1,soggettofisico.getCodice_fiscale());
		ResultSet rs = st.executeQuery();
		ArrayList<SoggettoFisico> soggFis = new ArrayList<SoggettoFisico>();
			
			while(rs.next())
			{
				
				soggFis.add(new SoggettoFisico(rs));
			}
			
			return soggFis;
	}
	
	
	public boolean exist(Connection conn) throws SQLException 
	{
		return !getItems(conn).isEmpty();
	}
/*	public Integer getIdEsistente(Connection conn) throws SQLException 
	{
		return getItems(conn).get(0).getId();
	}
	*/

	@Override
	public ArrayList<?> getItems(Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
