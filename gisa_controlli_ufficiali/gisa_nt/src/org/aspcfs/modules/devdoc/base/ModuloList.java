/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.devdoc.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.json.JSONArray;

public class ModuloList  extends Vector  {

	
	public void buildList(Connection db, int idFlusso) throws SQLException{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		String sql = "select * from sviluppo_moduli WHERE data_cancellazione is null and id_flusso = ? order by data DESC ";
		
		pst = db.prepareStatement(sql);
		pst.setInt(1, idFlusso);
		rs = pst.executeQuery();
		
		while (rs.next()){
			Modulo mod = new Modulo(rs);
			this.add(mod);
		}
		
	}
		
	
	

}
