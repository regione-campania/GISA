/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.canipadronali.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.canipadronali.base.Cane;
import org.aspcfs.modules.canipadronali.base.CaneList;
import org.aspcfs.modules.canipadronali.base.OperazioniCaniDAO;
import org.aspcfs.modules.canipadronali.base.Proprietario;
import org.aspcfs.modules.canipadronali.base.ProprietarioList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.DwrPreaccettazione;
import org.aspcfs.utils.IndirizzoProprietario;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class CaniPadronaliVigilanza extends CFSModule {

	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "canipadronali-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;

		try {
			db = this.getConnection(context);

			context.getRequest().setAttribute("siteId",-1);
			context.getRequest().setAttribute("tipologia",255);
			context.getRequest().setAttribute("tipoDest",null);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza c = new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			c.executeCommandAdd(context,db);
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		 UserBean user = (UserBean)context.getSession().getAttribute("User");
		 String nameContext=context.getRequest().getServletContext().getServletContextName();
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
				return getReturn(context, "AddTipo2");
			return getReturn(context, "Add");	}

	
	public String executeCommandInsert(ActionContext context) throws ParseException {
		if (!(hasPermission(context, "canipadronali-vigilanza-add"))) 
		{
			return ("PermissionError");
		}
		context.getRequest().setAttribute("reload", "false");
		String retPage = "InsertOK";

		Connection db = null;
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		Ticket newTicket = null;
		try
		{
			db = this.getConnection(context);
			
			String save = context.getRequest().getParameter("Save");
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			
			Ticket newTic = (Ticket) context.getFormBean();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			Proprietario prop = new Proprietario();
			prop.setRagioneSociale(context.getParameter("nominativo_proprietario"));
			prop.setNome(context.getParameter("nome_proprietario"));
			prop.setCognome(context.getParameter("cognome_proprietario"));
			prop.setTipoProprietarioDetentore(context.getParameter("tipo_proprietario"));
			prop.setCodiceFiscale(context.getParameter("cf_proprietario"));
			prop.setLuogoNascita(context.getParameter("luogo_nascita_proprietario"));
			prop.setDataNascitaAsString(context.getParameter("data_nascita_proprietario").trim().replaceAll(" ",""));
			String asl_proprietario = context.getParameter("asl_proprietario");
			String citta_proprietario = context.getParameter("citta_proprietario");
			if(asl_proprietario==null || asl_proprietario.equals("") ||  asl_proprietario.equals("null") )
			{
				ComuniAnagrafica comune = new ComuniAnagrafica(db, citta_proprietario);
				if(comune.getIdAsl()>0)
					asl_proprietario = comune.getIdAsl()+"";
			}
			
			if (!"".equals(asl_proprietario))
				prop.setIdAsl(Integer.parseInt(asl_proprietario));
			/*else
			{
				prop.setIdAsl(Integer.parseInt(context.getRequest().getParameter("siteId")));
			}*/
			
			IndirizzoProprietario indirizzo = new IndirizzoProprietario();
			ArrayList<IndirizzoProprietario> indirizzi = new ArrayList<IndirizzoProprietario>();
			if (! "".equals(context.getParameter("cap_proprietario")))
			{
				
				indirizzo.setCap(context.getParameter("cap_proprietario"));
			}
			indirizzo.setModificato_da(getUserId(context));
			indirizzo.setInserito_da(getUserId(context));
			indirizzo.setCitta(context.getParameter("citta_proprietario"));
			indirizzo.setProvincia(context.getParameter("provincia_proprietario"));
			indirizzo.setVia(context.getParameter("indirizzo_proprietario"));
			indirizzi.add(indirizzo);
			prop.setLista_indirizzi(indirizzi);
			prop.setDocumentoIdentita(context.getParameter("documento_proprietario"));
			
			String num_cani_aggiunti = context.getParameter("size_p");
			int num_cani_aggiunti_int = Integer.parseInt(num_cani_aggiunti);
			Integer[] cani_inseriti = new Integer[num_cani_aggiunti_int];
			
			int orgId = -1 ;
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			for (int i = 1 ; i<=num_cani_aggiunti_int; i++)
			{
			Cane cane_form = new Cane();
			cane_form.setEnteredby(getUserId(context));
			cane_form.setModifiedby(getUserId(context));
			cane_form.setId(-1);
			cane_form.setOrgId(-1);
			cane_form.setTaglia(context.getParameter("taglia_"+i));
			cane_form.setMantello(context.getParameter("mantello_"+i));
			cane_form.setMc(context.getParameter("mc_"+i));
			cane_form.setRazza(context.getParameter("razza_"+i));
			cane_form.setSesso(context.getParameter("sesso_"+i));
			if (!"".equals(asl_proprietario))
				cane_form.setSiteId(Integer.parseInt(asl_proprietario));
			else
			{
				cane_form.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));
			}
			
			if (context.getParameter("data_nascita_cane_"+i)!= null &&! "".equals(context.getParameter("data_nascita_cane_"+i)))
			{
				cane_form.setDataNascita(sdf.parse(context.getParameter("data_nascita_cane_"+i)));
			}
			cane_form.setNominativoProprietario(context.getParameter("nominativo_proprietario"));
			cane_form.setProprietario(prop);
			
			ArrayList<Cane> lista = prop.getListaCani();
			lista.add(cane_form);
			prop.setListaCani(lista);
			
			}
			
			UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
			orgId = op.inserisciCane(prop,user);
			
			context.getRequest().setAttribute("mc_gia_inseriti",true);
		
			CaneList listacani = new CaneList();
			context.getRequest().setAttribute("orgId",orgId);
			context.getRequest().setAttribute("assetId",cani_inseriti);

			for (Cane cane : prop.getListaCani())
			{
				listacani.add(cane);
			}
			
				int idAsl = -1 ;
				if (!"".equals(asl_proprietario))
					idAsl = Integer.parseInt(asl_proprietario);
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("tipologia", 255);
			context.getRequest().setAttribute("OrgId", prop.getOrgId());

			 
			context.getRequest().setAttribute("name", context.getParameter("nominativo_proprietario"));
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			
			controlliGeneric.executeCommandInsert(context,db);
			recordInserted = (Boolean) context.getRequest().getAttribute("inserted");
			isValid = (Boolean) context.getRequest().getAttribute("isValid");
			if (recordInserted && isValid) {
				Integer idControllo = (Integer)context.getRequest().getAttribute("idControlloCaniPadronali");
				listacani.set_asset_cu(db,orgId,idControllo);
				String tipo_richiesta = newTic.getTipo_richiesta();
				
				tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
				context.getRequest().setAttribute("assetId", cani_inseriti);
				retPage = "InsertOK";
			}
			context.getRequest().setAttribute("assetId", cani_inseriti);
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
		    return ("SystemError");
		}
		finally   
		{
			this.freeConnection(context, db);
		}
		
		return ( retPage );//("InsertOK"); 
	}
	/**
	 * Update the specified ticket
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandSupervisiona(ActionContext context) {
		
		Connection db = null;
		int resultCount = 0;
		boolean isValid = true;



		


		try {


			db = this.getConnection(context);

			
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandSupervisiona(context, db);
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (resultCount == 1 && isValid) {
			//return "UpdateOK";
			return "DettaglioOK";
		}
		return executeCommandTicketDetails(context);
	}
	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "canipadronali-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		//Parameters
		String ticketId = context.getRequest().getParameter("id");
		String retPag = null;
		PreparedStatement pst = null;
		String sql = null;
		String flagMod5 = null;
		ResultSet rs = null;
		try {
			db = this.getConnection(context);
			// Load the ticket
			int i = 0;
			//Read flag for mod5 from ticket table
			sql = "select flag_mod5 as flag from ticket where ticketid = ? ";
			pst=db.prepareStatement(sql);
			pst.setInt(++i,Integer.parseInt(ticketId));
			rs = pst.executeQuery();
			if(rs.next()){
				 flagMod5 = rs.getString("flag");
			}
			LookupList tipi_controlli_cani_padronali = new LookupList(db,"lookup_tipo_controllo_cani_padronali") ;
			context.getRequest().setAttribute("TipiControlliCani", tipi_controlli_cani_padronali);
			
			
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");			
			int id_prop = Integer.parseInt(temporgId);
			Ticket newTic = (Ticket) context.getFormBean();
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(ticketId)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("tipologia", 255);
			context.getRequest().setAttribute("tipoDest",null);
			context.getRequest().setAttribute("bozza", flagMod5);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandTicketDetails(context,db);
			
			context.getRequest().setAttribute("OrgDetails",proprietario);
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU"); 
			
			GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
			context.getRequest().setAttribute("isPresenteRegistrazione", gBdu.isPresenteRegistrazione(Integer.parseInt(ticketId)));
			
			retPag = "DettaglioOK";


		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return getReturn(context, "TicketDetails");
		return retPag;
	}
	
	public String executeCommandModifyTicket(ActionContext context) {
		if (!hasPermission(context, "canipadronali-vigilanza-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;


		try 
		{
			db = this.getConnection(context);
			String ticketId = context.getRequest().getParameter("id");

			
			if (context.getRequest().getParameter("companyName") == null) 
			{
				newTic = new Ticket(db, Integer.parseInt(ticketId));

			} else 
			{
				newTic = (Ticket) context.getFormBean();
			}
			
			//gestione della modifica del controllo ufficale
			boolean isModificabile = true;
			PreparedStatement pst = db.prepareStatement("select * from get_lista_sottoattivita(?, ?, ?)");
			pst.setInt(1, newTic.getId());
			pst.setInt(2, 2);
			pst.setBoolean(3, false);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				int idCampione = rs.getInt("ticketid");
				//Preaccettazione
				DwrPreaccettazione dwrPreacc = new DwrPreaccettazione();
				String result = dwrPreacc.Preaccettazione_RecuperaCodPreaccettazione(String.valueOf(idCampione));
				JSONObject jsonObj;
				jsonObj = new JSONObject(result);
				String codicePreaccettazione = jsonObj.getString("codice_preaccettazione");
				if (!codicePreaccettazione.equalsIgnoreCase("")){
					isModificabile = false;
				}
				
			}
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			if (user.getUserRecord().getRoleId()!=Role.RUOLO_ORSA && user.getUserRecord().getRoleId()!=Role.HD_1LIVELLO && user.getUserRecord().getRoleId()!=Role.HD_2LIVELLO ){
				if (!isModificabile){
					context.getRequest().setAttribute("Error", "Controllo non modificabile perche' associato ad un codice preaccettazione");
					return(executeCommandTicketDetails(context));
				}
			}
//			if (!isModificabile){
//				context.getRequest().setAttribute("Error", "Controllo non modificabile perche' associato ad un codice preaccettazione");
//				return(executeCommandTicketDetails(context));
//			}
			//gestione della modifica del controllo ufficiale
			
			LookupList tipi_controlli_cani_padronali = new LookupList(db,"lookup_tipo_controllo_cani_padronali") ;
			context.getRequest().setAttribute("TipiControlliCani", tipi_controlli_cani_padronali);
			
			
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");			
			int id_prop = Integer.parseInt(temporgId);
			int id_cane = Integer.parseInt(assetId) ;
			
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(ticketId)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("tipoDest",null);
			//Fix per problema segnalato in issue 6176: venivano caricati, in modifica cu, i "per conto di" della sola asl del proprietario. Nella jsp poi c'e un controllo per cui asl cu = asl per conto di
			//context.getRequest().setAttribute("siteId", proprietario.getIdAsl());
			context.getRequest().setAttribute("siteId",-1);
			context.getRequest().setAttribute("tipologia", 255);
			//context.getRequest().setAttribute("OrgDetails", proprietario);
			context.getRequest().setAttribute("TicketDetails", newTic);

			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandModifyTicket(context,db);
			context.getRequest().setAttribute("OrgDetails", proprietario);
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return "ModifyautorizzazionetrasportoanimaliviviOK";
		 UserBean user = (UserBean)context.getSession().getAttribute("User");
		 String nameContext=context.getRequest().getServletContext().getServletContextName();
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
				return getReturn(context, "ModifyTipo2");
			return getReturn(context, "Modify");		//return getReturn(context, "ModifyTicket");
	}
	
	public String executeCommandUpdateTicket(ActionContext context) {
		if (!hasPermission(context, "canipadronali-vigilanza-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = 0;
		boolean isValid = true;


		Ticket newTic = (Ticket) context.getFormBean();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		
		Proprietario prop = new Proprietario();
		prop.setNome(context.getParameter("nome_proprietario"));
		prop.setOrgId(Integer.parseInt(context.getParameter("orgId")));

		prop.setCognome(context.getParameter("cognome_proprietario"));
		prop.setTipoProprietarioDetentore(context.getParameter("tipo_proprietario"));
		prop.setCodiceFiscale(context.getParameter("cf_proprietario"));
		prop.setLuogoNascita(context.getParameter("luogo_nascita_proprietario"));
		prop.setDataNascitaAsString(context.getParameter("data_nascita_proprietario").trim().replaceAll(" ",""));
		if (!"".equals(context.getParameter("asl_proprietario")))
			prop.setIdAsl(Integer.parseInt(context.getParameter("asl_proprietario")));
		else
		{
			prop.setIdAsl(Integer.parseInt(context.getRequest().getParameter("siteId")));
		}
		
		IndirizzoProprietario indirizzo = new IndirizzoProprietario();
		ArrayList<IndirizzoProprietario> indirizzi = new ArrayList<IndirizzoProprietario>();
		if (! "".equals(context.getParameter("cap_proprietario")))
		{
			
			indirizzo.setCap(context.getParameter("cap_proprietario"));
		}
		indirizzo.setModificato_da(getUserId(context));
		indirizzo.setInserito_da(getUserId(context));
		indirizzo.setCitta(context.getParameter("citta_proprietario"));
		indirizzo.setProvincia(context.getParameter("provincia_proprietario"));
		indirizzo.setVia(context.getParameter("indirizzo_proprietario"));
		indirizzi.add(indirizzo);
		prop.setLista_indirizzi(indirizzi);
		prop.setDocumentoIdentita(context.getParameter("documento_proprietario"));
		
		String num_cani_aggiunti = context.getParameter("size_p");
		int num_cani_aggiunti_int = Integer.parseInt(num_cani_aggiunti);
		Integer[] cani_inseriti = new Integer[num_cani_aggiunti_int];
		
		int orgId = -1 ;
		for (int i = 1 ; i<=num_cani_aggiunti_int; i++)
		{
		Cane cane_form = new Cane();
		cane_form.setId(Integer.parseInt(context.getParameter("assetId_"+i)));
		cane_form.setModifiedby(getUserId(context));
		cane_form.setOrgId(Integer.parseInt(context.getParameter("orgId")));
		cane_form.setTaglia(context.getParameter("taglia_"+i));
		cane_form.setMantello(context.getParameter("mantello_"+i));
		cane_form.setMc(context.getParameter("mc_"+i));
		cane_form.setRazza(context.getParameter("razza_"+i));
		cane_form.setSesso(context.getParameter("sesso_"+i));
		if (!"".equals(context.getParameter("asl_proprietario")))
			cane_form.setSiteId(Integer.parseInt(context.getParameter("asl_proprietario")));
		else
		{
			cane_form.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));
		}
		
		if (! "".equals(context.getParameter("data_nascita_cane_"+i)))
		{
			try {
				cane_form.setDataNascita(sdf.parse(context.getParameter("data_nascita_cane_"+i)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cane_form.setNominativoProprietario(context.getParameter("nominativo_proprietario"));
		cane_form.setProprietario(prop);
		
		ArrayList<Cane> lista = prop.getListaCani();
		lista.add(cane_form);
		prop.setListaCani(lista);
		
		}
		
		if(context.getRequest().getParameter("codici_selezionabili")!=null){
			newTic.setCodiceAteco(context.getRequest().getParameter("codici_selezionabili"));
		}

		newTic.setCodiceAllerta(context.getRequest().getParameter("idAllerta"));
		

		if("1".equals(context.getRequest().getParameter("ncrilevate")))
			newTic.setNcrilevate(true);
		else
			newTic.setNcrilevate(false);


		newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));

		try {


			db = this.getConnection(context);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			//int idProp = op.inserisciCane(prop);
			
			String temporgId = context.getRequest().getParameter("orgId");
			//String assetId = context.getRequest().getParameter("assetId");			
			//int id_cane = Integer.parseInt(assetId) ;
			
			Proprietario proprietario = op.dettaglioProprietario(Integer.parseInt(temporgId), newTic.getId()) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;

			context.getRequest().setAttribute("siteId",proprietario.getIdAsl());
			context.getRequest().setAttribute("tipologia",255);
			context.getRequest().setAttribute("tipoDest",null);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandUpdateTicket(context,db);
			context.getRequest().setAttribute("OrgDetails", proprietario);
			resultCount = (Integer) context.getRequest().getAttribute("resultCount");
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (resultCount == 1 && isValid) {
			//return "UpdateOK";
			return "DettaglioOK";
		}
		return (executeCommandTicketDetails(context));
	}
	
	
	
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "canipadronali-vigilanza-delete")) {
			return ("PermissionError");
		}

		Connection db = null ;
		try 
		{	db = this.getConnection(context);
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");		
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			context.getRequest().setAttribute("url_confirm","CaniPadronaliVigilanza.do?command=DeleteTicket&assetId="+assetId+"&orgId="+temporgId);
			controlliGeneric.executeCommandConfirmDelete(context,db);

			return ("ConfirmDeleteOK");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		}
	}
	public String executeCommandChiudi(ActionContext context)
	{
		if (!(hasPermission(context, "canipadronali-vigilanza-edit"))){
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());

			Integer idT = thisTicket.getId();
			String idCU = idT.toString(); 

			thisTicket.setClosed(new Timestamp(System.currentTimeMillis()));

			



			String ticketId = context.getRequest().getParameter("id");



			org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
			int passedId = thisTicket.getOrgId();
			ticList.setOrgId(passedId);
			ticList.buildListControlli(db, passedId, ticketId);


			org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
			int pasId = thisTicket.getOrgId();
			tamponiList.setOrgId(passedId);
			tamponiList.buildListControlli(db, pasId, ticketId);




			Iterator campioniIterator=ticList.iterator();
			Iterator tamponiIterator=tamponiList.iterator();



			int flag=0;
			while(campioniIterator.hasNext()){

				org.aspcfs.modules.campioni.base.Ticket tic = (org.aspcfs.modules.campioni.base.Ticket) campioniIterator.next();

				if(tic.getClosed()==null){
					flag=1;

					break;

				}

			}


			while(tamponiIterator.hasNext()){

				org.aspcfs.modules.tamponi.base.Ticket tic = (org.aspcfs.modules.tamponi.base.Ticket) tamponiIterator.next();

				if(tic.getClosed()==null){
					flag=1;

					break;
				}

			}


			if(thisTicket.isNcrilevate()==true){
				org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
				int passIdN = thisTicket.getOrgId();
				nonCList.setOrgId(passedId);
				nonCList.buildListControlli(db, passIdN, ticketId);

				Iterator ncIterator=nonCList.iterator();

				while(ncIterator.hasNext()){

					org.aspcfs.modules.nonconformita.base.Ticket tic = (org.aspcfs.modules.nonconformita.base.Ticket) ncIterator.next();

					if(tic.getClosed()==null){
						flag=1;

						break;

					}

				}

			}


			if(thisTicket.getTipoCampione()==5){

				org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
				int AuditOrgId = thisTicket.getOrgId();
				String idTi = thisTicket.getPaddedId();
				audit.setOrgId(AuditOrgId);

				audit.buildListControlli(db, AuditOrgId, idTi);

				Iterator itAudit=audit.iterator();

				if(!itAudit.hasNext()){

					flag=2;

				}else{

					while(itAudit.hasNext()){

						org.aspcfs.checklist.base.Audit auditTemp = (org.aspcfs.checklist.base.Audit) itAudit.next();

						if(thisTicket.isCategoriaisAggiornata()==false){
							flag=2;

							break;

						}

					}}
			}

			String attivitaCollegate=context.getRequest().getParameter("altro");

			if(attivitaCollegate==null){
				if(flag==1 || flag==2){

					context.getRequest().setAttribute("Chiudi", ""+flag);
					return (executeCommandTicketDetails(context));

				}
			}

			if(flag==0){
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudi(db);
			}
			if (resultCount == -1) {
				return ( executeCommandTicketDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				return (executeCommandTicketDetails(context));
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");


	}
	
	public String executeCommandDeleteTicket(ActionContext context) {
		if (!hasPermission(context, "canipadronali-vigilanza-delete")) {
			return ("PermissionError");
		}
		boolean recordDeleted = false;
		Connection db = null;

		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		String passedId = context.getRequest().getParameter("id");
		try {
			db = this.getConnection(context);
			 //String org_id_temp = context.getRequest().getParameter("orgId");
		    String idControllo 		= "" ;
		    String dataControllo 	= "" ;
		    String mc 				= "" ;
		    String cf_prop			= "" ;
		    String param = "searchidControllo="+idControllo+"&searchdataControllo="+dataControllo+"&searchmicrochip="+mc+"&searchcfprop="+cf_prop ;
		   
			
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");			
			int id_prop = Integer.parseInt(temporgId);
			int id_cane = Integer.parseInt(assetId) ;
			
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(passedId)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			context.getRequest().setAttribute("url","CaniPadronaliVigilanza.do?command=SearchVigilanza&"+param);

			controlliGeneric.executeCommandDeleteTicket(context,db);
			recordDeleted = (Boolean) context.getRequest().getAttribute("recordDeleted");


		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (recordDeleted) {
			return ("DeleteTicketOK");
		} else {
			return (executeCommandTicketDetails(context));
		}
	}

	/**
	 * Re-opens a ticket
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandReopenTicket(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-vigilanza-edit")) {
			return ("PermissionError");
		}
		int resultCount = -1;
		
		Connection db = null ;
		try {
			db = this.getConnection(context);
			
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandReopenTicket(context,db);
			resultCount = (Integer)context.getRequest().getAttribute("resultCount");

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (resultCount == -1) {
			return (executeCommandTicketDetails(context));
		} else if (resultCount == 1) {
			return (executeCommandTicketDetails(context));
		} else {
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}
	
	
	public String executeCommandViewVigilanza(ActionContext context) {
		  
	    if (!hasPermission(context, "canipadronali-vigilanza-view")) {
	      return ("PermissionError");
	    }
	    
	    int orgId = -1 ;
	    int assetId = -1 ;
	    
	    String org_id_temp = context.getRequest().getParameter("orgId");
	    String ass_id_temp = context.getRequest().getParameter("assetId");
	    orgId 	 = Integer.parseInt(org_id_temp) 		;
	    
	    if(ass_id_temp!=null)
	    	assetId	 = Integer.parseInt(ass_id_temp) ;
	    
	    
	    Connection db = null;
	    org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
	    
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));
	    
	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "CaniPadronaliVigilanza.do?command=ViewVigilanza&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
		  OperazioniCaniDAO op = new OperazioniCaniDAO() ;
		  op.setDb(db);
	     
		  if(assetId>0)
		  {
		  Proprietario proprietario = op.dettaglioProprietario(orgId, assetId) ;
		  context.getRequest().setAttribute("CaneDetails", proprietario) ;
		  }
		  else
		  {
			  Proprietario proprietario = op.dettaglioProprietario(orgId) ;
			  context.getRequest().setAttribute("CaneDetails", proprietario) ;
		  }
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
	      //find record permissions for portal users
	     if(assetId>0)
	    	 ticList.setAssetId(assetId);
	      
	     ticList.setOrgId(passedId);
	     
	     UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
			if (thisUser.getRoleId()==Role.RUOLO_CRAS)
				ticList.setIdRuolo(thisUser.getRoleId());
			
	      ticList.buildList(db);
	      context.getRequest().setAttribute("TicList", ticList);
	      addModuleBean(context, "View CaniPadronali", "Accounts View");
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    if(assetId>0)
	    return "ListaCUOK";
	    return "ListaCUOperatoreOK";
	  }
	
	public String executeCommandSearchVigilanza(ActionContext context) {
		  
	    if (!hasPermission(context, "canipadronali-vigilanza-view")) {
	      return ("PermissionError");
	    }
	    
	   // int orgId = -1 ;
	   // int assetId = -1 ;
	    
	    //String org_id_temp = context.getRequest().getParameter("orgId");
	    String idControllo 		= context.getRequest().getParameter("searchidControllo");
	    String dataControllo 	= context.getRequest().getParameter("searchdataControllo");
	    String mc 				= context.getRequest().getParameter("searchmicrochip");
	    String cf_prop			= context.getRequest().getParameter("searchcfprop");

	    //orgId 	 = Integer.parseInt(org_id_temp) ;
	   // assetId	 = Integer.parseInt(ass_id_temp) ;
	    
	    
	    Connection db = null;
	    org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
	    
	    ticList.setIdControllo(idControllo);
	    ticList.setCfproprietario(cf_prop);
	    ticList.setTipologiaOperatore(255);
	    ticList.setDataControllo(dataControllo);
	    ticList.setMc(mc);
	   
	    String param = "searchidControllo="+idControllo+"&searchdataControllo="+dataControllo+"&searchmicrochip="+mc+"&searchcfprop="+cf_prop ;
	    if (context.getRequest().getParameter("siteId")!=null)
	    {
	    	ticList.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));
	    	param+="&siteId="+context.getRequest().getParameter("siteId");
	    }
	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "CaniPadronaliVigilanza.do?command=SearchVigilanza&"+param);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      	addModuleBean(context, "View CaniPadronali", "Accounts View");
	      	LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
		    AuditTipo.addItem(-1, "Selezionare una voce");
		  
		    context.getRequest().setAttribute("AuditTipo", AuditTipo);
		      
		    LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
		    TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
		    context.getRequest().setAttribute("TipoAudit", TipoAudit);
			
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
	      
			LookupList siteIdList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", siteIdList);
			
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
	      //find record permissions for portal users
	     ticList.setTipologiaOperatore(OperazioniCaniDAO.TIPOLOGIA_PROPRIETARI_CANI_PADRONALI);
	      //ticList.setAssetId(assetId);
	      //ticList.setOrgId(passedId);
	      ticList.buildList(db);
	      context.getRequest().setAttribute("TicList", ticList);
	     
	    } catch (Exception errorMessage) {
	    	errorMessage.printStackTrace();
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return "ListaSearchCUOK";
	  }
	public String executeCommandChiudiTutto(ActionContext context) {
		// public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "canipadronali-vigilanza-edit"))) {
			return ("PermissionError");
		}

		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		Ticket oldTicket = null;
		
		try {
			db = this.getConnection(context);

//			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
//			Ticket oldTicket = new Ticket(db, thisTicket.getId());
			
			thisTicket =  new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			oldTicket =  thisTicket;

//			if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//				return ("PermissionError");
//			}

			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			int flag=controlliGeneric.executeCommandChiudiTutto(context,db, thisTicket, oldTicket);

			if (flag == 4) {
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudiTemp(db);
				// return (executeCommandTicketDetails(context));
			}

			context.getRequest().setAttribute("Chiudi", "" + flag); if (flag == 0 || flag == 1 || flag == 3) {
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudi(db);
			}
			if (resultCount == -1) {
				return (executeCommandTicketDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				context.getRequest().setAttribute("Chiudi", "" + flag);
				return (executeCommandTicketDetails(context));
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");
	}

	
	public String executeCommandChiudiTemp(ActionContext context)
	{
		if (!(hasPermission(context, "canipadronali-vigilanza-edit"))){
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());

			Integer idT = thisTicket.getId();
			String idCU = idT.toString(); 

		
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudiTemp(db);
			
			if (resultCount == -1) {
				return ( executeCommandTicketDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				return (executeCommandTicketDetails(context));
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");


	}

	
	public String executeCommandCalcolaPunteggio(ActionContext context) {
		
		if(context.getParameter("searchCfProprietario")==null)
		{
			return "CalcoloOK" ;
		}
		ProprietarioList listProprietari=null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			String cfProprietario = context.getParameter("searchCfProprietario");
			
			PagedListInfo ticketListInfo = this.getPagedListInfo(context, "CaniPadronaliPunteggiInfo", "o.name", "desc");
			ticketListInfo.setLink( "CaniPadronaliVigilanza.do?command=CalcolaPunteggio&searchCfProprietario=" + cfProprietario);
			
			listProprietari = new ProprietarioList();
			listProprietari.setCfProprietario(cfProprietario);
			listProprietari.setPagedListInfo(ticketListInfo);
			try 
			{
				listProprietari.buildList(db);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("ListProprietari", listProprietari);
		addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");

		
		return ("CalcoloOK");
	}
	
}
