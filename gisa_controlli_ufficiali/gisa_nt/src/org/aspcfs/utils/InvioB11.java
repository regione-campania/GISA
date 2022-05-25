/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.darkhorseventures.framework.actions.ActionContext;

public class InvioB11 {

	private int id = -1;
	private java.sql.Timestamp dataOp = null;
	private String inviato_da = null;
	private int tot_cu = 0;
	
	public InvioB11() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.sql.Timestamp getDataOp() {
		return dataOp;
	}
	public void setDataOp(java.sql.Timestamp dataOp) {
		this.dataOp = dataOp;
	}
	public String getInviato_da() {
		return inviato_da;
	}
	public void setInviato_da(String inviato_da) {
		this.inviato_da = inviato_da;
	}
	public int getTot_cu() {
		return tot_cu;
	}
	public void setTot_cu(int tot_cu) {
		this.tot_cu = tot_cu;
	}
	

	public boolean insertB11(Connection db,ActionContext context) throws SQLException {
		
		PreparedStatement pst = null;
		pst = db.prepareStatement(
				"INSERT INTO invio_massivo_b11 " +
				"( id, data," +
				"  tot_cu, " +
				"  inviato_da) " +
				"  VALUES ( ?, ?, ?, ?);" );

		int i = 0;
		id = DatabaseUtils.getNextSeqInt(db,context, "invio_massivo_b11","id");
		pst.setInt(++i, id);
		pst.setTimestamp(++i, this.getDataOp());
		pst.setInt(++i, tot_cu);
		pst.setString(++i, inviato_da);	
		pst.execute();
		System.out.println("Query insert in invio_massivo_B11: "+pst.toString());
		pst.close();

		return true;
	}
	
	
}
