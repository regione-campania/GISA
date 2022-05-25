/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.global_search.base.OrganizationView;
import org.aspcfs.modules.registrotrasgressori.base.PagoPa;
import org.aspcfs.modules.registrotrasgressori.base.Trasgressione;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class RegistroTrasgressori extends CFSModule{

	public String executeCommandDefault(ActionContext context) {

		return executeCommandRegistroSanzioni(context);
		
	}
	public String executeCommandRegistroSanzioni(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori-view")) {
			return ("PermissionError");
		}
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		context.getRequest().setAttribute("anno", String.valueOf(year));
		return "PrepareRegistroSanzioniOk";
	}
	
	public String executeCommandRegistroSanzioniDettaglio(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori-view")) {
			return ("PermissionError");
		}
		
	//	int year = Calendar.getInstance().get(Calendar.YEAR);
		String anno= context.getRequest().getParameter("anno");
		return executeCommandRegistroSanzioniAnno(context);
			
	}
	
	
	public String executeCommandRegistroSanzioniArchivio(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori-view")) {
			return ("PermissionError");
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		context.getRequest().setAttribute("anno", String.valueOf(year));
		
	return "RegistroSanzioniPrepareArchivioOk";
	}
	
	public String executeCommandRegistroSanzioniArchivioDettaglio(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori-view")) {
			return ("PermissionError");
		}
		
	String anno = context.getRequest().getParameter("anno");	
	int year = -1;
	if (anno!=null && !anno.equals("null") && !anno.equals(""))
		year = Integer.parseInt(anno);
	
	context.getRequest().setAttribute("anno", String.valueOf(year));
	
	int trimestre = Integer.parseInt(context.getRequest().getParameter("trimestre"));
	context.getRequest().setAttribute("trimestre", String.valueOf(trimestre));
	
	// COSTRUISCI ELENCO SANZIONI
	Connection db = null;
	try {
		db = this.getConnection(context);
	
	Trasgressione trasgr = new Trasgressione();
	Vector trasgrList = new Vector();
	trasgrList = trasgr.cercaControlli(db, year, trimestre);
	Collections.sort(trasgrList);
	context.getRequest().setAttribute("listaTrasgressioni", trasgrList);
	
	List<Integer> gruppiUtente = new ArrayList<Integer>();
	context.getRequest().setAttribute("gruppiUtente", gruppiUtente);
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return ("SystemError");
	}
	finally
	{
		this.freeConnection(context, db);
	}

	String layout = context.getRequest().getParameter("layout");
	if (layout!=null && layout.equals("style"))
		return ("RegistroSanzioniStampaOk");
	
	return "RegistroSanzioniArchivioOk";
	}
	
	public String executeCommandPrepareRicerca(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori-view")) {
			return ("PermissionError");
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		context.getRequest().setAttribute("anno", String.valueOf(year));
		
	return "RegistroSanzioniPrepareRicercaOk";
	}
	
	public String executeCommandRicerca(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori-view")) {
			return ("PermissionError");
		}
		
		String anno = context.getRequest().getParameter("anno");
		String trasgressore = context.getRequest().getParameter("trasgressore");
		String obbligato = context.getRequest().getParameter("obbligato");
		String ente = context.getRequest().getParameter("ente");
		String pv = context.getRequest().getParameter("pv");
		Trasgressione trasgr = new Trasgressione();
		trasgr.setAnno(Integer.parseInt(anno));
		trasgr.setTrasgressore(trasgressore);
		trasgr.setObbligatoInSolido(obbligato);
		trasgr.setEnte1(ente);
		trasgr.setPV(pv);
		Vector trasgrList = new Vector();
		Connection db = null;
		try {
			db = this.getConnection(context);
		trasgrList = trasgr.cercaControllidaRicerca(db);
		Collections.sort(trasgrList);
		context.getRequest().setAttribute("listaTrasgressioni", trasgrList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
		}
	context.getRequest().setAttribute("anno", anno);
	return "RegistroSanzioniRicercaOk";
	}
	
	public String executeCommandDettaglioControllo(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori-view")) {
			return ("PermissionError");
		}
		
		String idControllo = context.getRequest().getParameter("idControllo");
		
		// COSTRUISCI ELENCO SANZIONI
		Connection db = null;
		try {
			db = this.getConnection(context);
			Ticket t = new Ticket(db, Integer.parseInt(idControllo));
			String link = "";
			link = t.getURlDettaglio()+"Vigilanza.do?command=TicketDetails&id="+idControllo+"&orgId="+t.getOrgId();	
			context.getRequest().setAttribute("link", link);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "DettaglioControlloOK";
	
	}
	
	public String executeCommandRegistroSanzioniAnno(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori-view")) {
			return ("PermissionError");
		}
		
		String messaggio = (String) context.getRequest().getAttribute("messaggio");
		if (messaggio!=null && !messaggio.equals("null"))
			context.getRequest().setAttribute("messaggio", messaggio);
		
		int year = -1;
		try {year = Integer.parseInt(context.getRequest().getParameter("anno"));} catch (Exception e){}
		context.getRequest().setAttribute("anno", String.valueOf(year));
		
		int trimestre = -1;
		try {trimestre = Integer.parseInt(context.getRequest().getParameter("trimestre"));} catch (Exception e){}
		context.getRequest().setAttribute("trimestre", String.valueOf(trimestre));
		
		// COSTRUISCI ELENCO SANZIONI
		Connection db = null;
		try {
			db = this.getConnection(context);
		
		Trasgressione trasgr = new Trasgressione();
		trasgr.setAnno(year);
		Vector trasgrList = new Vector();
		trasgrList = trasgr.cercaControlli(db, year, trimestre);
		Collections.sort(trasgrList);
		context.getRequest().setAttribute("listaTrasgressioni", trasgrList);
		
		List<Integer> gruppiUtente = new ArrayList<Integer>();
		if (hasPermission(context, "registro_trasgressori_gruppo1-edit")) {
			gruppiUtente.add(1);
		}
		if (hasPermission(context, "registro_trasgressori_gruppo2-edit")) {
			gruppiUtente.add(2);
		}
		context.getRequest().setAttribute("gruppiUtente", gruppiUtente);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		String layout = context.getRequest().getParameter("layout");
		if (layout!=null && layout.equals("style"))
			return ("RegistroSanzioniStampaOk");
		
		if (hasPermission(context, "registro_trasgressori-add")){
				return "RegistroSanzioniCorrenteOk";
			}
		else
			return "RegistroSanzioniArchivioOk";
	
	}
	
	public String executeCommandModificaSanzione(ActionContext context) { 
		if (!hasPermission(context, "registro_trasgressori-edit")) {
			return ("PermissionError");
		}
		
		int id = -1;
		String messaggio = null;
		
		try {id = Integer.parseInt(context.getRequest().getParameter("id"));} catch (Exception e) {}
		
		if (id==-1)
			try {id = Integer.parseInt((String)context.getRequest().getAttribute("id"));} catch (Exception e) {}
		
		try {messaggio = context.getRequest().getParameter("messaggio");} catch (Exception e) {}
		
		if (messaggio==null)
			try {messaggio = (String) context.getRequest().getAttribute("messaggio");} catch (Exception e) {}
		
		// COSTRUISCI ELENCO SANZIONI
		Connection db = null;
		try {
			db = this.getConnection(context);
		
		Trasgressione trasgr = new Trasgressione(db, id);
		context.getRequest().setAttribute("trasgr", trasgr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		List<Integer> gruppiUtente = new ArrayList<Integer>();
		
		if (hasPermission(context, "registro_trasgressori_gruppo1-edit")) {
			gruppiUtente.add(1);
		}
		if (hasPermission(context, "registro_trasgressori_gruppo2-edit")) {
			gruppiUtente.add(2);
		}
		context.getRequest().setAttribute("gruppiUtente", gruppiUtente);
		context.getRequest().setAttribute("messaggio", messaggio);
		
		return "ModificaSanzioneOk";
	
	}
	
	public String executeCommandUpdateSanzione(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori-edit")) {
			return ("PermissionError");
		}
		
		int id = Integer.parseInt(context.getRequest().getParameter("id"));
		boolean recordUpdated = false;
		
		// COSTRUISCI ELENCO SANZIONI
		Connection db = null;
		try {
			db = this.getConnection(context);
		
		Trasgressione trasgr = new Trasgressione(db, id);
		trasgr.completaDaRequest(context);
		recordUpdated = trasgr.update(db, getUserId(context));
		
		if (recordUpdated){
			context.getRequest().setAttribute("messaggio", "Le modifiche sono state salvate.");
			trasgr.aggiornaAllegati(db, context, getUserId(context));
		}
		else
			context.getRequest().setAttribute("messaggio", "Errore nel salvataggio dei dati.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		
		context.getRequest().setAttribute("id", String.valueOf(id));
		return executeCommandModificaSanzione(context);
	
	}
		
	public String executeCommandPrepareInserisciSanzione(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori_aggiunta_sanzioni-view")) {
			return ("PermissionError");
		}
		
		Connection db = null;
	    try {
	      db = getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.removeElementByCode(16);

	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      List<Integer> gruppiUtente = new ArrayList<Integer>();

			if (hasPermission(context, "registro_trasgressori_gruppo1-edit")) {
				gruppiUtente.add(1);
			}
			if (hasPermission(context, "registro_trasgressori_gruppo2-edit")) {
				gruppiUtente.add(2);
			}
			context.getRequest().setAttribute("gruppiUtente", gruppiUtente);
	      
	    } catch (Exception e) {
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }
			
		return "InserisciSanzioneOk";
	}
	
	public String executeCommandInsertSanzione(ActionContext context) {
		if (!hasPermission(context, "registro_trasgressori_aggiunta_sanzioni-view")) {
			return ("PermissionError");
		}
		
		int id = -1;
		
		int idEnteAccertato = Integer.parseInt(context.getRequest().getParameter("id_impresa_sanzionata"));
		int tipologiaEnteAccertato = Integer.parseInt(context.getRequest().getParameter("id_tipologia_operatore"));
		String enteAccertato = context.getRequest().getParameter("descrizione_impresa_sanzionata");
		
		int idAsl = Integer.parseInt(context.getRequest().getParameter("asl_competenza"));

		String trasgressore = context.getRequest().getParameter("trasgressore");
		String trasgressoreAltro = context.getRequest().getParameter("trasgressore_altro");

		String obbligato = context.getRequest().getParameter("obbligato");
		String obbligatoAltro = context.getRequest().getParameter("obbligato_altro");
		
		String pv = context.getRequest().getParameter("pv");
		String dataAccertamento = context.getRequest().getParameter("data_accertamento");
		
		// COSTRUISCI ELENCO SANZIONI
		Connection db = null;
		try {
			db = this.getConnection(context);
			PreparedStatement pst = db.prepareStatement("select * from insert_registro_trasgressori_sanzione(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			int i = 0;
			pst.setInt(++i, idEnteAccertato);
			pst.setInt(++i, tipologiaEnteAccertato);
			pst.setString(++i, enteAccertato);
			pst.setInt(++i, idAsl);
			pst.setString(++i, trasgressore.equalsIgnoreCase("altro") ? trasgressoreAltro : trasgressore);
			pst.setString(++i, obbligato.equalsIgnoreCase("altro") ? obbligatoAltro : obbligato);
			pst.setString(++i, pv);
			pst.setTimestamp(++i,  DatabaseUtils.parseDateToTimestamp(dataAccertamento));
			pst.setInt(++i, getUserId(context));
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				id = rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		
		context.getRequest().setAttribute("id", String.valueOf(id));
		if (id>0)
			context.getRequest().setAttribute("messaggio", "Sanzione inserita nel Registro Trasgressori.");
		return executeCommandModificaSanzione(context);
	
	}
}
