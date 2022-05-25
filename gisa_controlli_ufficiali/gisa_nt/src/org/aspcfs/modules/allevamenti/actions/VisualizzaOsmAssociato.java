/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allevamenti.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.allevamenti.base.Organization;
import org.aspcfs.modules.base.Constants;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Actions for the 'Visualizzazione OSM associato all'allevamento corrente'
 * 
 * @author Maria
 * @created Luglio 2012
 */
public final class VisualizzaOsmAssociato extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");
	
	
	public String executeCommandView(ActionContext context) {
		// verifica permessi  
		if (!(hasPermission(context, "allevamenti-associazioneosmregistrati-view"))) {
			return ("PermissionError");
		}

		// lettura parametri
		String orgId = (String) context.getRequest().getParameter("orgId");
		if (orgId == null && !"".equals(orgId)) {
			orgId = (String) context.getRequest().getAttribute("orgId");
		}

		// inizializzazione
		Exception errorMessage = null;
		Connection db = null;
		Organization thisOrg = null;
		String numRegOsm = null;
		int idAssociazione;
		
		try {
			// recupero connessione al DB
			db = getConnection(context);

			// verifica permessi di accesso al record di organization
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}

			// lettura record di organization
			thisOrg = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrg);

			logger.info("Visualizzazione OSM registrati associati all'allevamento [" + thisOrg.getName() + "].....");
			
			PreparedStatement pst = db.prepareStatement(
					"SELECT azosm.* FROM organization org " +
					"INNER JOIN aziendezootecniche_osm azosm ON azosm.cod_azienda = org.account_number " +
					"WHERE org.org_id = ?"
			);

			pst.setInt(1, thisOrg.getOrgId());

			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {	// deve esistere un unico record ?????????			
				numRegOsm = rs.getString("num_reg_osm");
				idAssociazione = rs.getInt("id");
				
				if ( (numRegOsm == null) || numRegOsm.trim().equals("")) {
					// e' presente un record in aziendezootecniche_osm, ma il numero registrazione OSM e' vuoto o nullo
					rs.close();
					pst.close();
					throw new SQLException(Constants.NOT_FOUND_ERROR);
				}
				
				context.getRequest().setAttribute("numRegOsm", numRegOsm);
				context.getRequest().setAttribute("idAssociazione", idAssociazione +"");
				logger.info("All'allevamento [" + thisOrg.getName() + "] e' associato un OSM registrato con numero di registrazione [" + numRegOsm + "]");
			}
			else {
				// non e' presente alcun record in aziendezootecniche_osm
				logger.info("All'allevamento [" + thisOrg.getName() + "] non e' associato nessun OSM registrato.");
				
				// metto in sessione l'allevamento corrente
				// dopo aver recuperato la descrizione del comune dall'account number
				String primoPezzoCodiceIstat = thisOrg.getAccountNumber().substring(0,3);
				String provincia = thisOrg.getAccountNumber().substring(3,5);

				String codiceProvincia = "";				
				if (provincia.equals("CE")) codiceProvincia = "61";
					else if (provincia.equals("BN")) codiceProvincia = "62";
			    		else if (provincia.equals("NA")) codiceProvincia = "63";
			    			else if (provincia.equals("AV")) codiceProvincia = "64";
			    				else if (provincia.equals("SA")) codiceProvincia = "65";
				
				String codiceComune = codiceProvincia + primoPezzoCodiceIstat; 
				
				// query
				PreparedStatement pstComune = db.prepareStatement("SELECT comune FROM comuni WHERE codiceistatcomune = ?");
				pstComune.setString(1, codiceComune);
				
				ResultSet rsComune = pstComune.executeQuery();
				if (rsComune.next()) {
					String descrizioneComune = rsComune.getString(1);
					logger.info("Comune di appartenenza dell'allevamento = " + descrizioneComune);
					thisOrg.setCity(descrizioneComune);				
					context.getSession().setAttribute("allevamentoACuiAssociareOsm", thisOrg);
				}
				else {
					rsComune.close();
					pstComune.close();
					throw new SQLException("Errore durante il recupero del comune di appartenenza dell'allevamento");
				}				
				rsComune.close();
				pstComune.close();
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			errorMessage = e;
		} finally {
			this.freeConnection(context, db);
		}

		if (errorMessage == null) { //nessuna eccezione
			if (numRegOsm != null && !numRegOsm.trim().equals("")) {
				// esiste un OSM associato all'allevamento
				return (getReturn(context, "visualizzaOsm"));
			}
			else {
				// nessun OSM associato all'allevamento				
				context.getRequest().setAttribute("ricercaOsmAssociabiliAttribute", true);
				return (getReturn(context, "associaOsm"));
			}
		}
		
		logger.severe(errorMessage.getMessage());
		context.getRequest().setAttribute("Error", errorMessage.getMessage());
		return ("SystemError");
	}
}
