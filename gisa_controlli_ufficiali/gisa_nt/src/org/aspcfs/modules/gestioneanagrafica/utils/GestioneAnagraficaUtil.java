/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.actions.CFSModule;

public final class GestioneAnagraficaUtil extends CFSModule {
	
	 
	 public static boolean isCancellabile(Connection db, int altId) throws SQLException {
			boolean esito = false;
			PreparedStatement pst = db.prepareStatement("select * from is_anagrafica_cancellabile(?)");
			pst.setInt(1, altId);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
				esito = rs.getBoolean(1);
			return esito;
		}
		 
	public static boolean deleteCentralizzato(Connection db, int altId, String note, int userId) throws SQLException {
		boolean esito = false;
		PreparedStatement pst = db.prepareStatement("select * from anagrafica_delete_centralizzato(?, ?, ?)");
		pst.setInt(1, altId);
		pst.setString(2, note);
		pst.setInt(3, userId);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			esito = rs.getBoolean(1);
		return esito;
	}
	 
	 
}
