/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DbUtil;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

ResultSet rs = null;
PreparedStatement pst = null;
		for (int i = 0; i < 1000; i++){
			Connection conn = null;
		//	System.out.println(i);
			String dbName = ApplicationProperties.getProperty("dbnameBdu");
			String username = ApplicationProperties
					.getProperty("usernameDbbdu");
			String pwd = ApplicationProperties.getProperty("passwordDbbdu"); 
			String host = "172.16.3.250";

//			try {
//			//	conn = DbUtil.getConnection(dbName, username, pwd, host);
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}		finally {
//			//	DbUtil.chiudiConnessioneJDBC(rs, pst, conn);
//			}
		}
	}

}
