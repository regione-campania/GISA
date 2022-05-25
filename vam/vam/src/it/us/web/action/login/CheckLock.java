/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckLock {

	
	public static boolean checkLocked(Connection db, String ip, String username) throws SQLException{
		boolean locked = false;
		PreparedStatement pst = db.prepareStatement("select * from check_locked(?, ?)");
		pst.setString(1, ip);
		pst.setString(2, username);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			locked = rs.getBoolean(1);
		return locked;
	}
	
	public static int resetLock(Connection db, String ip, String username) throws SQLException{
		int badcount = -1;
		PreparedStatement pst = db.prepareStatement("select * from reset_lock(?, ?)");
		pst.setString(1, ip);
		pst.setString(2, username);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			badcount = rs.getInt(1);
		return badcount;
	}
	
	public static int incLock(Connection db, String ip, String username) throws SQLException{
		int badcount = -1;
		PreparedStatement pst = db.prepareStatement("select * from inc_lock(?, ?)");
		pst.setString(1, ip);
		pst.setString(2, username);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			badcount = rs.getInt(1);
		return badcount;
	}
	
}
