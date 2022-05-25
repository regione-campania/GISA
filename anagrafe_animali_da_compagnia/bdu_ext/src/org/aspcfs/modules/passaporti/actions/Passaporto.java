/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.passaporti.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.log.base.LogBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.microchip.base.MicrochipList;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.OperatoreList;
import org.aspcfs.modules.passaporti.base.PassaportoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriRegione;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import com.daffodilwoods.daffodildb.server.serversystem.PreparedStatement;
import com.darkhorseventures.framework.actions.ActionContext;

public final class Passaporto extends CFSModule {

	public String executeCommandDefault(ActionContext context) {
		return executeCommandSearch(context);
	}

	public String executeCommandSearch(ActionContext context) {

		if (!(hasPermission(context, "passaporto-view"))) {
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);

			LookupList aslList = new LookupList(db, "lookup_asl_rif");
			aslList.remove(aslList.get("Fuori Regione"));
			aslList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("aslRifList", aslList);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("SearchOK");
	}
	
	
	public String executeCommandListUOS(ActionContext context) 
	{
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		if (!(hasPermission(context, "distribuzione_passaporti-view")) )
		{
			return ("PermissionError");
		}

		OperatoreList operatoreList = new OperatoreList();

		addModuleBean(context, "View Accounts", "Search Results");

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try 
		{
			db = this.getConnection(context);

				operatoreList.setUos(true);
				if(user.getSiteId()>0)
					operatoreList.setIdAsl(user.getSiteId());
				
				
				Integer[] array = new Integer[1];
				array[0] = LineaProduttiva.idAggregazioneCanile;
				operatoreList.setIdLineaProduttiva(array);
				HashMap<Integer, String> lista = operatoreList.buildListUos(db);

				context.getRequest().setAttribute("OrgList", lista);

				LookupList siteList = new LookupList(db, "lookup_asl_rif");
				context.getRequest().setAttribute("SiteIdList", siteList);

				LookupList tipologiaList = new LookupList(db, "opu_lookup_attivita_linee_produttive_aggregazioni");
				// registrazioniList.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("tipologiaList", tipologiaList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, ((UserBean) context.getSession()
						.getAttribute("User")).getSiteId());

				return getReturn(context, "ListUOS");
			
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}

	public String executeCommandList(ActionContext context) {
		  
		PassaportoList passaportoList = new PassaportoList();
	    String asl = context.getParameter("aslRif");
	    
	    //Flusso 238
	    if(true)
	    {
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    if(asl==null && user.getRoleId() == new Integer(ApplicationProperties.getProperty("RUOLO_REFERENTE_UOC")) )
	    	asl = user.getSiteId()+"";
	    }
	    String passaporto  = context.getParameter("nrpassaporto");
	  
	    int totPassaporti			= 0;
	    int totPassaportiAssegnati			= 0;
	    int totPassaportiFuoriUso		= 0;

	    
	    //Flusso 238
	    if(true)
	    {
	    if(context.getParameter("assegnato")!=null &&  !context.getParameter("assegnato").equals(""))
			  passaportoList.setAssegnato(Boolean.parseBoolean(context.getParameter("assegnato")));
		  if(context.getParameter("abilitato")!=null &&  !context.getParameter("abilitato").equals(""))
			  passaportoList.setAbilitato(Boolean.parseBoolean(context.getParameter("abilitato")));
		  if(context.getParameter("id_uos")!=null &&  !context.getParameter("id_uos").equals(""))
			  passaportoList.setIdUos(Integer.parseInt(context.getParameter("id_uos")));
		  
		  context.getRequest().setAttribute("id_uos", context.getParameter("id_uos"));
		  context.getRequest().setAttribute("nome_uos", context.getParameter("nome_uos"));
		  
		  
	    }
	    //Prepare pagedListInfo
		PagedListInfo passaportoListInfo = this.getPagedListInfo(context, "PassaportoListInfo");
		//Flusso 238
		if(true)
		{
		String link = "Passaporto.do?command=List&aslRif="+(asl != null ? asl :"")+"&passaporto="+(passaporto != null ? passaporto :"");
	    if(context.getParameter("assegnato")!=null &&  !context.getParameter("assegnato").equals(""))
			link += "&assegnato=" + context.getParameter("assegnato");
		if(context.getParameter("abilitato")!=null &&  !context.getParameter("abilitato").equals(""))
			link += "&abilitato=" + context.getParameter("abilitato");
		if(context.getParameter("id_uos")!=null &&  !context.getParameter("id_uos").equals(""))
			link += "&id_uos=" + context.getParameter("id_uos");
		if(context.getParameter("nome_uos")!=null &&  !context.getParameter("nome_uos").equals(""))
			link += "&nome_uos=" + context.getParameter("nome_uos");
		  
		passaportoListInfo.setLink(link);
		}
		else
		{
			passaportoListInfo.setLink("Passaporto.do?command=List&aslRif="+(asl != null ? asl :"")+"&passaporto="+(passaporto != null ? passaporto :""));
		}

		Connection db = null;
		
		try {
		  db = this.getConnection(context);
		  
		  //Flusso 238
		  if(true)
		  {
		  if(context.getParameter("associarePassaporti")!=null)
		  {
			  ArrayList<Integer> passaportiDaAssociare = list(context.getRequest(), "passaportiDaAssociare");
			  org.aspcfs.modules.passaporti.base.Passaporto.assegnaPassaportiUos(db, passaportiDaAssociare, Integer.parseInt(context.getParameter("id_uos")));
			  context.getRequest().setAttribute("messaggio", "Passaporti associati correttamente alla uos " + context.getParameter("nome_uos"));
		  }
		  }
		  
		  passaportoList.setPagedListInfo(passaportoListInfo);

		
				  
		  passaportoList.setIdAsl(Integer.parseInt(asl));
				  
				  totPassaporti   		= org.aspcfs.modules.passaporti.base.Passaporto.getTotaliAsl(db, Integer.parseInt(asl));
				  totPassaportiAssegnati 		=  org.aspcfs.modules.passaporti.base.Passaporto.getTotaliUtilizzatiAsl(db, Integer.parseInt(asl));
				  totPassaportiFuoriUso		= org.aspcfs.modules.passaporti.base.Passaporto.getTotaliDisabilitatiAsl(db, Integer.parseInt(asl));
				  
				  context.getRequest().setAttribute("totPassaporti", totPassaporti); //Anche quelli fuori uso
				  context.getRequest().setAttribute("totPassaportiAssegnati", totPassaportiAssegnati);
				  context.getRequest().setAttribute("totPassaportiFuoriUso", totPassaportiFuoriUso);
				  context.getRequest().setAttribute("totPassaportiDisponibili", totPassaporti-totPassaportiAssegnati-totPassaportiFuoriUso);
				  
				  context.getRequest().setAttribute("statistiche", "true");
			  
		  
		  
		  if (passaporto != null && !"".equals(passaporto)) {
			  passaportoList.setNrPassaporto(passaporto);
		  }
		  
		  
	      LookupList lookupSpecie = new LookupList(db,"lookup_specie");
	      context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
		  //set the searchcriteria
	      passaportoListInfo.setSearchCriteria(passaportoList, context);
	      passaportoList.buildList(db);
		  context.getRequest().setAttribute("passaportoList", passaportoList);
		  
			LookupList aslList = new LookupList(db, "lookup_asl_rif");
			context.getRequest().setAttribute("aslRifList", aslList);
			
			
			
			java.sql.PreparedStatement st = db.prepareStatement("select * from uos");
			ResultSet rs = st.executeQuery();
			HashMap<Integer,String> lista_uos = new HashMap<Integer,String>();
			while(rs.next())
				lista_uos.put(rs.getInt("id"), rs.getString("ragione_sociale"));
			context.getRequest().setAttribute("lista_uos", lista_uos);
			
		  
	    } catch (Exception e) {
		    context.getRequest().setAttribute("Error", e);
		    return ("SystemError");
	    } finally {
		    this.freeConnection(context, db);
	    }
		
	    return ("ListOK");
	  }
	
	public String executeCommandListDistribuzionePassaporti(ActionContext context) {
		  
		PassaportoList passaportoList = new PassaportoList();
	    String asl = context.getParameter("aslRif");
	    
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    if(asl==null && user.getRoleId() == new Integer(ApplicationProperties.getProperty("RUOLO_REFERENTE_UOC")) )
	    	asl = user.getSiteId()+"";
	    String passaporto  = context.getParameter("nrpassaporto");
	  
	    int totPassaporti			= 0;
	    int totPassaportiAssegnati			= 0;
	    int totPassaportiFuoriUso		= 0;

	    
	    if(context.getParameter("assegnato")!=null &&  !context.getParameter("assegnato").equals(""))
			  passaportoList.setAssegnato(Boolean.parseBoolean(context.getParameter("assegnato")));
		  if(context.getParameter("abilitato")!=null &&  !context.getParameter("abilitato").equals(""))
			  passaportoList.setAbilitato(Boolean.parseBoolean(context.getParameter("abilitato")));
		  if(context.getParameter("id_uos")!=null &&  !context.getParameter("id_uos").equals(""))
			  passaportoList.setIdUos(Integer.parseInt(context.getParameter("id_uos")));
		  
		  context.getRequest().setAttribute("id_uos", context.getParameter("id_uos"));
		  context.getRequest().setAttribute("nome_uos", context.getParameter("nome_uos"));
		  context.getRequest().setAttribute("aslRif", asl);
		  
		  
	    
	    //Prepare pagedListInfo
		PagedListInfo passaportoListInfo = this.getPagedListInfo(context, "PassaportoListInfo");
		String link = "Passaporto.do?command=ListDistribuzionePassaporti&aslRif="+(asl != null ? asl :"")+"&passaporto="+(passaporto != null ? passaporto :"");
	    if(context.getParameter("assegnato")!=null &&  !context.getParameter("assegnato").equals(""))
			link += "&assegnato=" + context.getParameter("assegnato");
		if(context.getParameter("abilitato")!=null &&  !context.getParameter("abilitato").equals(""))
			link += "&abilitato=" + context.getParameter("abilitato");
		if(context.getParameter("id_uos")!=null &&  !context.getParameter("id_uos").equals(""))
			link += "&id_uos=" + context.getParameter("id_uos");
		if(context.getParameter("nome_uos")!=null &&  !context.getParameter("nome_uos").equals(""))
			link += "&nome_uos=" + context.getParameter("nome_uos");
		  
		passaportoListInfo.setLink(link);

		Connection db = null;
		
		try {
		  db = this.getConnection(context);
		  
		  
		  if(context.getParameter("associarePassaporti")!=null)
		  {
			  ArrayList<Integer> passaportiDaAssociare = list(context.getRequest(), "passaportiDaAssociare");
			  org.aspcfs.modules.passaporti.base.Passaporto.assegnaPassaportiUos(db, passaportiDaAssociare, Integer.parseInt(context.getParameter("id_uos")));
			  context.getRequest().setAttribute("messaggio", "Passaporti associati correttamente alla uos " + context.getParameter("nome_uos"));
		  }
		  
		  
		  passaportoList.setPagedListInfo(passaportoListInfo);

		
				  
		  passaportoList.setIdAsl(Integer.parseInt(asl));
				  
				  totPassaporti   		= org.aspcfs.modules.passaporti.base.Passaporto.getTotaliAsl(db, Integer.parseInt(asl));
				  totPassaportiAssegnati 		=  org.aspcfs.modules.passaporti.base.Passaporto.getTotaliUtilizzatiAsl(db, Integer.parseInt(asl));
				  totPassaportiFuoriUso		= org.aspcfs.modules.passaporti.base.Passaporto.getTotaliDisabilitatiAsl(db, Integer.parseInt(asl));
				  
				  context.getRequest().setAttribute("totPassaporti", totPassaporti); //Anche quelli fuori uso
				  context.getRequest().setAttribute("totPassaportiAssegnati", totPassaportiAssegnati);
				  context.getRequest().setAttribute("totPassaportiFuoriUso", totPassaportiFuoriUso);
				  context.getRequest().setAttribute("totPassaportiDisponibili", totPassaporti-totPassaportiAssegnati-totPassaportiFuoriUso);
				  
				  context.getRequest().setAttribute("statistiche", "true");
			  
		  
		  
		  if (passaporto != null && !"".equals(passaporto)) {
			  passaportoList.setNrPassaporto(passaporto);
		  }
		  
		  
	      LookupList lookupSpecie = new LookupList(db,"lookup_specie");
	      context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
		  //set the searchcriteria
	      passaportoListInfo.setSearchCriteria(passaportoList, context);
	      passaportoList.buildList(db);
		  context.getRequest().setAttribute("passaportoList", passaportoList);
		  
			LookupList aslList = new LookupList(db, "lookup_asl_rif");
			context.getRequest().setAttribute("aslRifList", aslList);
		  

		  
	    } catch (Exception e) {
		    context.getRequest().setAttribute("Error", e);
		    return ("SystemError");
	    } finally {
		    this.freeConnection(context, db);
	    }
		
	    return ("ListPassaportiDistribuzioneOK");
	  }
	

	public String executeCommandScarico(ActionContext context) {

		if (!(hasPermission(context, "passaporto-add"))) {
			return ("PermissionError");
		}

		Connection db = null;

		try {

			db = getConnection(context);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("ScaricoOK");
	}

	public String executeCommandCarico(ActionContext context) {

		if (!(hasPermission(context, "passaporto-add"))) {
			return ("PermissionError");
		}

		Connection db = null;

		try {
			db = getConnection(context);
			LookupList aslList = new LookupList(db, "lookup_asl_rif");
			aslList.remove(aslList.get("Fuori Regione"));
			aslList.addItem(-1, "Seleziona asl");
			context.getRequest().setAttribute("aslRifList", aslList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("CaricoOK");
	}

	public String executeCommandEseguiScarico(ActionContext context) {
		if (!(hasPermission(context, "passaporto-edit"))) {
			return ("PermissionError");
		}

		boolean checkMC = false;
		boolean isNotAssigned = false;
		boolean isAbilitato = false;
		String idasl = null;
		Connection db = null;

		try {

			db = getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			org.aspcfs.modules.passaporti.base.Passaporto p = (org.aspcfs.modules.passaporti.base.Passaporto) context
					.getFormBean();

			// mc = context.getParameter("microchip");

			if (getUserAsl(context) > 0)
				checkMC = org.aspcfs.modules.passaporti.base.Passaporto.checkMyPassaporto(db, p.getNrPassaporto(),
						getUserAsl(context));
			else
				checkMC = true;
			
			isNotAssigned = org.aspcfs.modules.passaporti.base.Passaporto.isAssigned(db, p.getNrPassaporto());
			isAbilitato =  org.aspcfs.modules.passaporti.base.Passaporto.isAbilitato(db, p.getNrPassaporto());
			
			

			if (checkMC && isNotAssigned && isAbilitato) {
				org.aspcfs.modules.passaporti.base.Passaporto.eseguiScaricoPassaporto(db, p.getNrPassaporto());
				context.getRequest().setAttribute("ok", "Scarico Passaporto Eseguito con Successo!");
			} else if (!isAbilitato) {
				context.getRequest().setAttribute("errore", "Passaporto precedentemente disabilitato");
			}else{
				context.getRequest().setAttribute("errore", "Impossibile Eseguire lo Scarico!");
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("ScaricoOK");
	}

	public String executeCommandEseguiCarico(ActionContext context) {
		if (!(hasPermission(context, "passaporto-edit"))) {
			return ("PermissionError");
		}

		boolean lengthOk = false;
		boolean isNotAssigned = false;
		boolean isNotTatAssigned = false;
		boolean isNotduplicated = false;
		String mc = null;
		Connection db = null;

		try {

			db = getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			org.aspcfs.modules.passaporti.base.Passaporto p = (org.aspcfs.modules.passaporti.base.Passaporto) context.getFormBean();

			if (p.getNrPassaporto().length() == 13) { // && m.checkNumber(mc) ){
				lengthOk = true;
			}

			if (p.getIdAslAppartenenza() < 0 && getUserAsl(context) > -1) {
				p.setIdAslAppartenenza(getUserAsl(context));
			}
			
			p.setIdUtentePrecaricamento(getUserId(context));

			// CONTROLLI SU PASSAPORTO

			isNotduplicated = org.aspcfs.modules.passaporti.base.Passaporto.verifyDuplicate(db, p.getNrPassaporto());
			if (isNotduplicated)
				isNotAssigned = org.aspcfs.modules.passaporti.base.Passaporto.isAssigned(db, p.getNrPassaporto());

			if (lengthOk && isNotAssigned) {
				p.store(db);
				context.getRequest().setAttribute("ok", "Carico Passaporto Eseguito con Successo!");
			} else {
				context.getRequest().setAttribute("errore",
						"Impossibile Eseguire il Carico del valore di Passaporto immesso!");
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return executeCommandCarico(context);
	}
	
	
	
	
	
	
	public static ArrayList<Integer> list( HttpServletRequest req, String prefisso )
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		
		Enumeration<String> e = (Enumeration<String>)req.getParameterNames();
		
		while(e.hasMoreElements())
		{
			String nome_parametro = (String)e.nextElement();
			if( nome_parametro.startsWith( prefisso ) )
			{
				ret.add(  Integer.parseInt(req.getParameter( nome_parametro ))  );
			}
		}
		
		
		return ret;
	}

}
