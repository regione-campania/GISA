/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioni.utils.PdfTool;
import org.aspcfs.modules.macellazioni.utils.Stats;
import org.aspcfs.modules.macellazionidocumenti.base.Articolo17;
import org.aspcfs.modules.mu.base.CapoUnivoco;
import org.aspcfs.modules.mu.base.CapoUnivocoList;
import org.aspcfs.modules.mu.base.PartitaUnivoca;
import org.aspcfs.modules.mu.base.SedutaUnivoca;
import org.aspcfs.modules.mu.operazioni.base.Macellazione;
import org.aspcfs.modules.mu.operazioni.base.MorteANM;
import org.aspcfs.modules.mu.operazioni.base.VisitaPM;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.web.LookupList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.darkhorseventures.framework.actions.ActionContext;

public class MacellazioneUnicaDocumenti extends CFSModule {
	
	private static final SimpleDateFormat sdf  = new SimpleDateFormat( "dd/MM/yyyy" );
	
	public String executeCommandRegistroMacellazioni(ActionContext context)
	{
//		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
//		{
//			return ("PermissionError");
//		}
		
		Connection db = null;
		VisitaPM pm = null;
		MorteANM mam = null;
		
		try
		{
			
			
			String idSeduta = "";

			idSeduta = context.getRequest().getParameter("idSeduta");
			if (idSeduta == null || idSeduta.equals("") || idSeduta.equals("null"))
				idSeduta = (String) context.getRequest().getAttribute("idSeduta");
			
			
			db = this.getConnection( context );


			CapoUnivocoList listaCapiSeduta = new CapoUnivocoList();
			listaCapiSeduta.setIdSeduta(idSeduta);
			listaCapiSeduta.setFlagBuildDettagliPartita(true);
			listaCapiSeduta.buildList(db);
		//	listaCapiSeduta.setIdStato(CapoUnivoco.idStatoMacellato);
			
			
			SedutaUnivoca seduta = new SedutaUnivoca(db, Integer.parseInt(idSeduta));
			
			Iterator i = listaCapiSeduta.iterator();
			
			context.getRequest().setAttribute("listaCapiSeduta", listaCapiSeduta);
			
			LookupList esitiPm = new LookupList(db, "m_lookup_esiti_vpm");
			context.getRequest().setAttribute("esitiPm", esitiPm);
			
			LookupList statiCapo = new LookupList(db, "mu_lookup_stati");
			context.getRequest().setAttribute("statiCapo", statiCapo);
			
			
			
			


			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		return "PrintRegistroOK";
	}
	
	
	
	public String executeCommandArt17(ActionContext context) throws SQLException, IOException{
		
		Connection db = null;
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
//		String ip = context.getIpAddress();
//		int user_id = user.getUserRecord().getId();
//		String idSeduta = context.getRequest().getParameter("idSeduta");
//		String idPartita = context.getRequest().getParameter("idPartita");
		
		String idArticolo17 = context.getRequest().getParameter("idArticolo17");
		
		if (idArticolo17 == null || ("").equals(idArticolo17)){
			
			idArticolo17 = (String) context.getRequest().getAttribute("idArticolo17");
		}
		try {
		 db = this.getConnection(context);
		org.aspcfs.modules.mu.base.Articolo17 articolo17 = new org.aspcfs.modules.mu.base.Articolo17(Integer.parseInt(idArticolo17), db);
		context.getRequest().setAttribute("art17", articolo17);
		
		Organization macello = new Organization(db, articolo17.getIdMacello());
		context.getRequest().setAttribute("macello", macello);
		
		Organization esercente = null;
		if (articolo17.getIdEsercente() > 0)
			esercente = new Organization (db, articolo17.getIdEsercente());
		context.getRequest().setAttribute("esercente", esercente);
		
		
		if (articolo17.getIdStato() == org.aspcfs.modules.mu.base.Articolo17.idStatoAperto)
			articolo17.close(articolo17.getId());
		
		LookupList esitiPm = new LookupList(db, "m_lookup_esiti_vpm");
		context.getRequest().setAttribute("esitiPm", esitiPm);
		
		
		// Lookup dei veterinari
		
		HashMap<String, ArrayList<Contact>> listaUtentiAttiviV = ControlliUfficialiUtil
				.getUtentiAttiviperaslVeterinari(db, user.getSiteId());
		context.getRequest().setAttribute("listaVeterinari", listaUtentiAttiviV);
		
		}
		catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");}
		finally {
			this.freeConnection(context, db);
		}
		

		
		return "PrintArticolo17OK";
		
	}

}
