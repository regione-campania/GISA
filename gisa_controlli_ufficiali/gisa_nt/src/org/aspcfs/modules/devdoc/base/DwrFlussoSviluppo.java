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
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.darkhorseventures.database.ConnectionElement;

public class DwrFlussoSviluppo { 

	
	public static String[] recuperaFlusso(int id) throws SQLException
	{
		String descrizioneFlusso = "";
		String tagsFlusso = "";
		String idFlusso = "";
		String[] valori = new String[3];
		
		Connection db = null ;
		WebContext ctx = WebContextFactory.get();
		ConnectionElement ce = (ConnectionElement) ctx.getSession().getAttribute("ConnectionElement");
		SystemStatus systemStatus = (SystemStatus) ((Hashtable) ctx.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
		HttpServletRequest req = ctx.getHttpServletRequest();
		
		UserBean user = (UserBean) ctx.getSession().getAttribute("User");
		int userId = user.getUserId();
		
		try {
			db = GestoreConnessioni.getConnection();
			String sqlVer = "select * from sviluppo_flussi where id_flusso = ? and data_cancellazione is null";
			PreparedStatement pst = null ;
			ResultSet rs = null ;
			pst = db.prepareStatement(sqlVer);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next())
			{
				idFlusso = rs.getString("id_flusso");
				descrizioneFlusso = rs.getString("descrizione");
				tagsFlusso = rs.getString("tags");
				valori[0]=idFlusso;
				valori[1]=descrizioneFlusso;
				valori[2]=tagsFlusso;
			}
			
		} catch (SQLException e) {
			throw e ;
		}
		catch (NumberFormatException g) {
			throw g ;
		}	
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return valori;
	}
	
//	public static String consegnaFlusso(int idFlusso) throws SQLException
//	{
//		Connection db = null ;
//		WebContext ctx = WebContextFactory.get();
//		ConnectionElement ce = (ConnectionElement) ctx.getSession().getAttribute("ConnectionElement");
//		SystemStatus systemStatus = (SystemStatus) ((Hashtable) ctx.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
//		HttpServletRequest req = ctx.getHttpServletRequest();
//	
//		String ret = null;
//		
//		try {
//			db = GestoreConnessioni.getConnection();
//			String sqlVer = "update sviluppo_flussi set data_consegna = now() where id = ?";
//			PreparedStatement pst = null ;
//			pst = db.prepareStatement(sqlVer);
//			pst.setInt(1, idFlusso);
//			pst.executeUpdate();
//
//			String sqlSel = "select to_char(data_consegna,'dd/MM/yyyy')  from sviluppo_flussi where id = ?";
//			pst = null ;
//			pst = db.prepareStatement(sqlSel);
//			pst.setInt(1, idFlusso);
//			ResultSet rs = pst.executeQuery();
//			
//			if (rs.next())
//				ret = rs.getString(1);
//			
//			
//		} catch (SQLException e) {
//			throw e ;
//		}
//		catch (NumberFormatException g) {
//			throw g ;
//		}	
//		finally
//		{
//			GestoreConnessioni.freeConnection(db);
//		}
//		
//		return ret;
//	}
	
	public static String nonPresenzaModulo(int idFlusso, int idTipo) throws SQLException
	{
		Connection db = null ;
		WebContext ctx = WebContextFactory.get();
		ConnectionElement ce = (ConnectionElement) ctx.getSession().getAttribute("ConnectionElement");
		SystemStatus systemStatus = (SystemStatus) ((Hashtable) ctx.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
		HttpServletRequest req = ctx.getHttpServletRequest();
		
		UserBean user = (UserBean) ctx.getSession().getAttribute("User");
		int userId = user.getUserId();
	
		String ret = null;
		
		try {
			db = GestoreConnessioni.getConnection();

			Modulo mod = new Modulo();
			mod.setIdFlusso(idFlusso);
			mod.setIdTipo(idTipo);
			mod.setNonDisponibile(true);
			mod.setIdUtente(userId);
			mod.insert(db);
			
		} catch (SQLException e) {
			throw e ;
		}
		catch (NumberFormatException g) {
			throw g ;
		}	
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return "NON DISPONIBILE";
	}
	
}


