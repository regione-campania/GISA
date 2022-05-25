/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.UrlUtil;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;


public class DocumentaleUtil extends CFSModule {

	public DocumentaleUtil(){
		
	}
	
	public String executeCommandVerificaDocumentaleOnline(ActionContext context) throws SQLException, IOException{
		boolean esito=  verificaDocumentaleOnline(context);
		System.out.println("[GISA] Esito verifica Documentale online: "+ esito);
		context.getRequest().setAttribute("verificaDocumentaleOnline", esito);
		return "verificaDocumentaleOnlineOK";
	}
	
	public static boolean verificaDocumentaleOnline(ActionContext context) throws SQLException, IOException{
		
		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	
		if (!documentaleDisponibile){
			return false;
		}
		
		String esito="";
		String urlTest = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_TEST");
		esito = UrlUtil.getUrlResponse(urlTest);
		
		if (esito!=null && esito.toUpperCase().contains("ONLINE"))
			return true;
		return false;
	}
	
}
