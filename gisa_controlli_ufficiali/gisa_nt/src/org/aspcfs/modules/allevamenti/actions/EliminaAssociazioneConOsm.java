/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allevamenti.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

import org.aspcfs.modules.actions.CFSModule;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Actions for the 'Associazione azienda zootecnica a OSM'
 * 
 * @author Maria
 * @created Luglio 2012
 */
public final class EliminaAssociazioneConOsm extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");
	
	public String executeCommandDelete(ActionContext context) {
		// verifica permessi  
		if (!(hasPermission(context, "allevamenti-associazioneosmregistrati-delete"))) {
			return ("PermissionError");
		}

		// lettura parametri
		String orgId = (String) context.getRequest().getParameter("orgId");
		if (orgId == null && !"".equals(orgId)) {
			orgId = (String) context.getRequest().getAttribute("orgId");
		}
		String idAssociazione = (String)context.getRequest().getParameter("idAssociazione");

		// inizializzazione
		Exception errorMessage = null;
		Connection db = null;

		try {
			logger.info("Eliminazione collegamento fra azienda zootecnica e OSM registrato.....");
			logger.info("ID associazione da eliminare = [" + idAssociazione + "]");
			
			// recupero connessione al DB
			db = getConnection(context);

			// verifica permessi di accesso al record di organization
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}			

			//eliminazione associazione da aziendezootecniche_osm
			PreparedStatement pst = db.prepareStatement("DELETE FROM aziendezootecniche_osm WHERE id = ?");

			pst.setInt(1, Integer.parseInt(idAssociazione));

			pst.execute();
			pst.close();

		} catch (Exception e) {
			errorMessage = e;
		} finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) {
			logger.info("Eliminazione associazione effettuata con successo.");
			context.getRequest().setAttribute("orgId", orgId);
			return ("EliminazioneAssociazioneOK");
		}
		
		logger.severe(errorMessage.getMessage());
		context.getRequest().setAttribute("Error", errorMessage.getMessage());
		return ("SystemError");
	}
}
