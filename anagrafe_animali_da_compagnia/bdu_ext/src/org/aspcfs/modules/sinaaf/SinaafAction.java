/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sinaaf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.ws.WsPost;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.MediaType;

public class SinaafAction extends CFSModule 
{
	
	
	
	public String executeCommandInvia(ActionContext context) throws SQLException 
	{
		

		if (!hasPermission(context, "proprietari_detentori_modulo-view") && !hasPermission(context, "proprietari_detentori-view")) 
			return ("PermissionError");

		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			
			String esitoInvioSinaaf[] = new Sinaaf().inviaInSinaaf(db, getUserId(context),context.getParameter("id"), context.getParameter("entita"));
			
			String urlToRedirect = context.getParameter("urlToRedirect").replaceAll("_____", "&")+"&errore="+esitoInvioSinaaf[1]+"&messaggio="+esitoInvioSinaaf[0];
			context.getRequest().setAttribute("urlToReturn", urlToRedirect);
			return getReturn(context, "SinaafInvio");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
	} 
	
	public String executeCommandVedi(ActionContext context) throws SQLException 
	{
		

		if (!hasPermission(context, "proprietari_detentori_modulo-view") && !hasPermission(context, "proprietari_detentori-view")) 
			return ("PermissionError");

		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			
			String idSinaaf = context.getParameter("idSinaaf");
			if(idSinaaf==null || idSinaaf.equals("") || idSinaaf.equals("null"))
				context.getRequest().setAttribute("errore", "Non presente in sinaaf");
			else
			{
				String[] esito = new Sinaaf().vediInSinaaf(db, getUserId(context),context.getParameter("id"), context.getParameter("entita"));
				context.getRequest().setAttribute("errore", esito[0]);
				context.getRequest().setAttribute("json",   esito[1]);
			}
			return getReturn(context, "SinaafVediRequest");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}

	
		
	} 
	
	
	public String executeCommandVediRequest(ActionContext context) throws SQLException 
	{
		

		if (!hasPermission(context, "proprietari_detentori_modulo-view")
				&& !hasPermission(context, "proprietari_detentori-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		try {
			
			db = this.getConnection(context);
			
			String id = context.getParameter("id");
			String entita = context.getParameter("entita");
			
			WsPost ws = new WsPost(db,id,entita,ApplicationProperties.getProperty("END_POINT_SINAAF"));
		
			try 
			{
				  String envelope = "";
				  PreparedStatement pst = db.prepareStatement("select * from " + ws.dbiGetEnvelope + "(?)");
				  pst.setString(1, id);
				  ResultSet rs = pst.executeQuery();
				  while (rs.next())
					  envelope = rs.getString(1);
					
				  context.getRequest().setAttribute("json", envelope);
			  
			}
			catch(Exception e)
			{
			  e.printStackTrace();
			}
		
			
			return getReturn(context, "SinaafVediRequest");
			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	
		
	} 


	

}

