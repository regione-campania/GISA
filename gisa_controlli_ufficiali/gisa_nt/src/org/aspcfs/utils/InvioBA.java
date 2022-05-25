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

public class InvioBA {

	private int id = -1;
	private java.sql.Timestamp dataOp = null;
	private String inviato_da = null;
	private int tot_cu = 0;
	private int tot_cu_inviati = 0;
	private int tot_cu_inviati_ok = 0;
	private int tot_cu_inviati_ko = 0;
	private int tot_cu_inviati_ko_in_anagrafe = 0;
	
	
	public InvioBA() {}
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
	

	public int getTot_cu_inviati() {
		return tot_cu_inviati;
	}
	public void setTot_cu_inviati(int tot_cu_inviati) {
		this.tot_cu_inviati = tot_cu_inviati;
	}
	
	public int getTot_cu_inviati_ok() {
		return tot_cu_inviati_ok;
	}
	public void setTot_cu_inviati_ok(int tot_cu_inviati_ok) {
		this.tot_cu_inviati_ok = tot_cu_inviati_ok;
	}
	public int getTot_cu_inviati_ko() {
		return tot_cu_inviati_ko;
	}
	public void setTot_cu_inviati_ko(int tot_cu_inviati_ko) {
		this.tot_cu_inviati_ko = tot_cu_inviati_ko;
	}
	public int getTot_cu_inviati_ko_in_anagrafe() {
		return tot_cu_inviati_ko_in_anagrafe;
	}
	public void setTot_cu_inviati_ko_in_anagrafe(int tot_cu_inviati_ko_in_anagrafe) {
		this.tot_cu_inviati_ko_in_anagrafe = tot_cu_inviati_ko_in_anagrafe;
	}
	public InvioBA(Connection db, String username) throws SQLException{

		PreparedStatement pst = null;
		pst = db.prepareStatement("SELECT * from invio_massivo_ba where inviato_da = ? and data is not null order by data desc limit 1;" ); 
		pst.setString(1, username);
		ResultSet rs = pst.executeQuery();
		System.out.println("Query select da invio_massivo_ba: "+pst.toString());
		while (rs.next()){
			this.id = rs.getInt("id");
			this.dataOp = rs.getTimestamp("data");
			this.tot_cu = rs.getInt("tot_cu");
			this.tot_cu_inviati = rs.getInt("tot_cu_inviati");
			this.tot_cu_inviati_ok = rs.getInt("tot_cu_inviati_ok");
			this.tot_cu_inviati_ko = rs.getInt("tot_cu_inviati_ko");
			this.tot_cu_inviati_ko_in_anagrafe = rs.getInt("tot_cu_inviati_ko_in_anagrafe");
			this.inviato_da = rs.getString("inviato_da");
		}

	}
	
	public boolean insertBA(Connection db,ActionContext context) throws SQLException {
		
		PreparedStatement pst = null;
		pst = db.prepareStatement(
				"INSERT INTO invio_massivo_ba " +
				"( id, data," +
				"  tot_cu, " +
				"  inviato_da) " +
				"  VALUES ( ?, ?, ?, ?);" );

		int i = 0;
		id = DatabaseUtils.getNextSeqInt(db,context, "invio_massivo_ba","id");
		pst.setInt(++i, id);
		pst.setTimestamp(++i, this.getDataOp());
		pst.setInt(++i, tot_cu);
		pst.setString(++i, inviato_da);	
		pst.execute();
		System.out.println("Query insert in invio_massivo_ba: "+pst.toString());
		pst.close();

		return true;
	}
	
public boolean updateBA(Connection db) throws SQLException {
		
		PreparedStatement pst = null;
		pst = db.prepareStatement("UPDATE invio_massivo_ba SET tot_cu_inviati = ?, tot_cu_inviati_ok = ?, tot_cu_inviati_ko = ?, tot_cu_inviati_ko_in_anagrafe = ? where id = ?");
		pst.setInt(1, tot_cu_inviati);
		pst.setInt(2, tot_cu_inviati_ok);
		pst.setInt(3, tot_cu_inviati_ko);
		pst.setInt(4, tot_cu_inviati_ko_in_anagrafe);
		pst.setInt(5, id);
		pst.execute();
		System.out.println("Query update invio_massivo_ba: "+pst.toString());
		pst.close();

		return true;
	}
	
	
}
