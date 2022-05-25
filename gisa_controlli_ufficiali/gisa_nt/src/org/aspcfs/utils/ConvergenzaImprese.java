/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.ApplicationPrefs;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

public class ConvergenzaImprese {
	
	
	
	
	public String eliminaImpreseSenzaStabilimenti(int idImpresa)
	{
		
		String query_senza_stabilimenti ="update opu_operatore set modifiedby=6567 , note_internal_use_only_hd='ELIMINATO IN QUANTO NON SONO PRESENTI STABILIMENTI. ELIMINAZIONE AVVENUTA DA FUNZIONE DI CONVERGENZA.' , trashed_date = current_date where id =?";
		
		String verifica = "select * from opu_stabilimento where id_operatore= ? and trashed_Date is null";
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		
		ApplicationPrefs applicationPrefs = (ApplicationPrefs) request.getServletContext().getAttribute(
				"applicationPrefs");

		ApplicationPrefs prefs = (ApplicationPrefs) request.getServletContext().getAttribute("applicationPrefs");
		String ceDriver = prefs.get("GATEKEEPER.DRIVER");
		String ceHost = prefs.get("GATEKEEPER.URL");
		String ceUser = prefs.get("GATEKEEPER.USER");
		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
		ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
		ce.setDriver(ceDriver);

		ConnectionPool cp = null ;
		Connection db = null ;
		cp = (ConnectionPool)request.getServletContext().getAttribute("ConnectionPool");
		
		
		try
		{
			db = cp.getConnection(ce,null);
			
			PreparedStatement pstVer = db.prepareStatement(verifica);
			pstVer.setInt(1, idImpresa);
			ResultSet rs = pstVer.executeQuery();
			if (!rs.next())
			{
			PreparedStatement pst = db.prepareStatement(query_senza_stabilimenti);
			pst.setInt(1, idImpresa);
			pst.execute();
			return "Cancellazione Avvenuta con Successo. Refreshare La pagina";
			}
			else
			{
				return "WARNING! None possibile cancellare l'impresa in quanto sono stati aggiunti stabilimenti in fase di convergenza";
			}
		}catch(Exception e)
		{
			
			return "Errore Nella Cancellazione :"+e.getMessage();
		}
		finally
		{
			cp.free(db);
		}
		
	}
	
	
	
	
	public String convergenzaStabilimento(int idImpresaScelta,Integer[] arrayImpreseDaRaggruppare,int idIndirizzoScelto)
	{

		
		String sql="select * from public_functions.suap_convergenza_stabilimenti(?,?::int[],6567,?)";
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		
		ApplicationPrefs applicationPrefs = (ApplicationPrefs) request.getServletContext().getAttribute(
				"applicationPrefs");

		ApplicationPrefs prefs = (ApplicationPrefs) request.getServletContext().getAttribute("applicationPrefs");
		String ceDriver = prefs.get("GATEKEEPER.DRIVER");
		String ceHost = prefs.get("GATEKEEPER.URL");
		String ceUser = prefs.get("GATEKEEPER.USER");
		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
		ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
		ce.setDriver(ceDriver);

		ConnectionPool cp = null ;
		Connection db = null ;
		cp = (ConnectionPool)request.getServletContext().getAttribute("ConnectionPool");
		
		try
		{	
			db = cp.getConnection(ce,null);

			Array listaImpreseDaRaggruppareSQL = db.createArrayOf("int", arrayImpreseDaRaggruppare);
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idImpresaScelta);
			pst.setArray(2, listaImpreseDaRaggruppareSQL);
			pst.setInt(3, idIndirizzoScelto);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return "Convergenza eseguita con successo su stabilimento scelto :"+rs.getInt(1)+". Verifica il Log  in tab log_convergenza_Stabilimenti";
			

		}catch(Exception e)
		{
			e.printStackTrace();
			return "Errore Nella convergenza :"+e.getMessage();
		}
		finally
		{
			cp.free(db);
		}
		
		return "Nessun Risultato Ritornato";

	}
	
	public String convergenzaImpresa(int idImpresaScelta,Integer[] arrayImpreseDaRaggruppare,int idIndirizzoScelto)
	{

		
		String sql="select * from public_functions.suap_convergenza_impresa(?,?::int[],6567,?)";
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		
		ApplicationPrefs applicationPrefs = (ApplicationPrefs) request.getServletContext().getAttribute(
				"applicationPrefs");

		ApplicationPrefs prefs = (ApplicationPrefs) request.getServletContext().getAttribute("applicationPrefs");
		String ceDriver = prefs.get("GATEKEEPER.DRIVER");
		String ceHost = prefs.get("GATEKEEPER.URL");
		String ceUser = prefs.get("GATEKEEPER.USER");
		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
		ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
		ce.setDriver(ceDriver);

		ConnectionPool cp = null ;
		Connection db = null ;
		cp = (ConnectionPool)request.getServletContext().getAttribute("ConnectionPool");
		
		try
		{	
			db = cp.getConnection(ce,null);

			Array listaImpreseDaRaggruppareSQL = db.createArrayOf("int", arrayImpreseDaRaggruppare);
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idImpresaScelta);
			pst.setArray(2, listaImpreseDaRaggruppareSQL);
			pst.setInt(3, idIndirizzoScelto);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return "Convergenza eseguita con successo su impresa scelta :"+rs.getInt(1)+". Verifica il Log  in tab log_convergenza_imprese";
			

		}catch(Exception e)
		{
			e.printStackTrace();
			return "Errore Nella convergenza :"+e.getMessage();
		}
		finally
		{
			cp.free(db);
		}
		
		return "Nessun Risultato Ritornato";

	}

}
