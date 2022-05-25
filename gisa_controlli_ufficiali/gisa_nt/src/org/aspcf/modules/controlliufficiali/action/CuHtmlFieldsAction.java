/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.aspcf.modules.controlliufficiali.base.CuHtmlFields;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.RispostaDwrCodicePiano;

import com.darkhorseventures.framework.actions.ActionContext;

public class CuHtmlFieldsAction  extends CFSModule {
	public String executeCommandTicketDetailsExtra(ActionContext context) {
		
		 String idControllo = context.getRequest().getParameter("idCU");
		 String idPiano = context.getRequest().getParameter("idPiano");
		 
		 Connection db = null ;
			try {
				db = this.getConnection(context);
				RispostaDwrCodicePiano codice_interno = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio", Integer.parseInt(idPiano));
					
				//Dati finestra modale
		  		//ArrayList<CampioniHtmlFields> campi = new ArrayList<CampioniHtmlFields>();
		  		LinkedHashMap<String, ArrayList<CuHtmlFields>> campi = new LinkedHashMap<String, ArrayList<CuHtmlFields>>();
		  		
		  		String query = "select campi.*, mod.valore from cu_html_fields campi "+ 
		  						" LEFT JOIN cu_fields_value mod on mod.id_cu_html_fields = campi.id and mod.id_controllo = ? " +
		  						" where (enabled = true or enabled is null) and codice_interno = ? and enabled = true"+
		  						" order by codice_interno, ordine_campo asc";

		  		
		  		
			
		  		
		  		PreparedStatement pst;
				
					pst = db.prepareStatement(query);
				
		  		pst.setInt(1, Integer.parseInt(idControllo));
		  		pst.setString(2, codice_interno.getCodiceInterno());
		  	
		  		ResultSet rs = pst.executeQuery();
		  		while (rs.next())
		  		{
		  			CuHtmlFields campo = new CuHtmlFields();
		  			campo.buildRecord(db, rs);
		  			
		  			if  (!campi.containsKey(campo.getNome_campo())){ // Se non e' presente la chiave
		  				ArrayList<CuHtmlFields> campoArray = new ArrayList<CuHtmlFields>(); //Crea array vuoto
		  				campoArray.add(campo); //Aggiungi campo
		  				campi.put(campo.getNome_campo(), campoArray); //Aggiungi array con nuovo elemento
		  			}
		  			else
		  				campi.get(campo.getNome_campo()).add(campo);
		  		}
		  		pst.close();
		  		
		  		CuHtmlFields c = new CuHtmlFields();
		  		LinkedHashMap<String, String> ris = c.costruisciHtmlDettaglioDaHashMap(db, campi);
		  		context.getRequest().setAttribute("campiHash", ris);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return ("SystemError");
				} finally{
					freeConnection(context, db);
				}
				return "ticketDetailsExtraOK";
		}
	
	public String executeCommandTicketDetailsEstesi(ActionContext context) {
		
		 String idControllo = context.getRequest().getParameter("idCU");
		 String idCodiceInterno = context.getRequest().getParameter("idCodiceInterno");
		 
		 Connection db = null ;
			try {
				db = this.getConnection(context);
					
				//Dati finestra modale
		  		//ArrayList<CampioniHtmlFields> campi = new ArrayList<CampioniHtmlFields>();
		  		LinkedHashMap<String, ArrayList<CuHtmlFields>> campi = new LinkedHashMap<String, ArrayList<CuHtmlFields>>();
		  		
		  		String query = "select campi.*, mod.valore from cu_html_fields campi "+ 
		  						" LEFT JOIN cu_fields_value mod on mod.id_cu_html_fields = campi.id and mod.id_controllo = ? " +
		  						" where (enabled = true or enabled is null) and codice_interno = ? and enabled = true"+
		  						" order by codice_interno, ordine_campo asc";
		
		  		PreparedStatement pst;
				
					pst = db.prepareStatement(query);
				
		  		pst.setInt(1, Integer.parseInt(idControllo));
		  		pst.setString(2, idCodiceInterno);
		  	
		  		ResultSet rs = pst.executeQuery();
		  		while (rs.next())
		  		{
		  			CuHtmlFields campo = new CuHtmlFields();
		  			campo.buildRecord(db, rs);
		  			
		  			if  (!campi.containsKey(campo.getNome_campo())){ // Se non e' presente la chiave
		  				ArrayList<CuHtmlFields> campoArray = new ArrayList<CuHtmlFields>(); //Crea array vuoto
		  				campoArray.add(campo); //Aggiungi campo
		  				campi.put(campo.getNome_campo(), campoArray); //Aggiungi array con nuovo elemento
		  			}
		  			else
		  				campi.get(campo.getNome_campo()).add(campo);
		  		}
		  		pst.close();
		  		
		  		CuHtmlFields c = new CuHtmlFields();
		  		LinkedHashMap<String, String> ris = c.costruisciHtmlDettaglioDaHashMap(db, campi);
		  		context.getRequest().setAttribute("campiHash", ris);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return ("SystemError");
				} finally{
					freeConnection(context, db);
				}
				return "ticketDetailsExtraOK";
		}
}
