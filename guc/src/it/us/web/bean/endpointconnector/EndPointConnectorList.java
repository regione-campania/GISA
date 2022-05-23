/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.endpointconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class EndPointConnectorList extends Vector  {
	
	public void creaLista(Connection db){
		String sql = "select c.* from guc_endpoint_connector_config c left join guc_endpoint e on e.id = c.id_endpoint where e.enabled and c.enabled order by id_endpoint asc"; 
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				EndPointConnector epc = new EndPointConnector(db, rs);
				this.add(epc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		}
	
	
	public EndPointConnectorList getByIdOperazione(int idOperazione){
		EndPointConnectorList listaByOperazione = new EndPointConnectorList();
		for (int i = 0; i < this.size(); i++){
			EndPointConnector epc = (EndPointConnector) this.get(i);
			if (epc.getIdOperazione() == idOperazione)
				listaByOperazione.add(epc);
		}
		return listaByOperazione;
	}
	
	public EndPointConnector getByIdOperazioneIdEndPoint(int idOperazione, int idEndPoint){
		EndPointConnector ret = new EndPointConnector();
		for (int i = 0; i < this.size(); i++){
			EndPointConnector epc = (EndPointConnector) this.get(i);
			if (epc.getIdOperazione() == idOperazione && epc.getIdEndPoint() == idEndPoint)
				ret = epc;
		}
		return ret;
	}
	
	public EndPointConnector getByIdOperazioneNomeEndPoint(int idOperazione, String nomeEndPoint){
		EndPointConnector ret = new EndPointConnector();
		for (int i = 0; i < this.size(); i++){
			EndPointConnector epc = (EndPointConnector) this.get(i);
			if (epc.getIdOperazione() == idOperazione && epc.getEndPoint().getNome().equals(nomeEndPoint))
				ret = epc;
		}
		return ret;
	}
}
