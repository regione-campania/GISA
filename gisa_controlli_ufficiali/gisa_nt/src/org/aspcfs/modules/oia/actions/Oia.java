/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.oia.actions;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.base.OiaList;
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.oia.base.OiaTipologiaCompetenzaNodo;
import org.aspcfs.modules.oia.base.Organization;
import org.aspcfs.modules.oia.base.ResponsabileNodo;
import org.aspcfs.modules.oia.base.UserInfo;
import org.aspcfs.modules.vigilanza.base.MotivoAudit;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.HtmlOption;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class Oia extends CFSModule {

	private static final int COMUNI			= 1;
	private static final int MACROCATEGORIE	= 2;
	private static final String L1_RUOLO_RESPONSABILE 			= "16";
	private static final String L2_RUOLO_RESPONSABILE_SIAN 		= "21";
	private static final String L2_RUOLO_RESPONSABILE_VETERINARI= "19";
	private static final String L4_RUOLO_SIAN		= "42";
	private static final String L4_RUOLO_VETERINARI		= "43";

	//private static final String L3_RUOLO_RESPONSABILE_VETERINARI= "43,41";


	public String executeCommandDefault(ActionContext context) {
		return executeCommandLista( context );
	}



	public String executeCommandLista(ActionContext context) {
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {
			db = this.getConnection(context);

			HashMap<Integer,ArrayList<OiaNodo>> strutture = (HashMap<Integer,ArrayList<OiaNodo>>) context.getServletContext().getAttribute("StruttureOIA");
		     ArrayList<OiaNodo> nodi = new ArrayList<OiaNodo>();
		     if(utente.getSiteId()>0)
		     {
		    	nodi = strutture.get(utente.getSiteId());
		     }
		     else
		     {
		    	 Iterator<Integer> itK = strutture.keySet().iterator();
		    	 while (itK.hasNext())
		    	 {
		    		 int k = itK.next();
		    		 ArrayList<OiaNodo> nodiTemp = strutture.get(k);
		    		 for(OiaNodo nodotemp : nodiTemp)
		    		 {
		    			 nodi.add(nodi.size(), nodotemp);
		    		 }
		    		 
		    	 }
		     }

			context.getRequest().setAttribute("nodi_livello", nodi);
			LookupList lookup_comuni = ritorna_lookup_tutti_comuni(context,-1, db);
			lookup_comuni.setSelectName("comune");
			context.getRequest().setAttribute("lookup_comuni", lookup_comuni);

			LookupList lookupASL = new LookupList(db, "lookup_site_id");
			lookupASL.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupASL", lookupASL);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	


		return ("OiaListaOK");
	}
	
	
	public String executeCommandSearchForm(ActionContext context) {
		Connection db = null;
		ArrayList<OiaNodo> lista_nodi = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {
			db = this.getConnection(context);
			
			
				
				String t = "(select codice_interno_fk as code , descrizione_struttura as description ,'' as short_description,false as default_item,1 as level,true as enabled " +
							"from dpat_strutture_asl oia_nodo " +
							" where n_livello !=3 and  disabilitato = false " ;
			int idAsl = getUserSiteId(context);
			if (idAsl>0)
				t += " and oia_nodo.id_asl="+idAsl + ")" ;
			else 
				t += ")";
			LookupList lookupTipologia = new LookupList(db, t);
			
			context.getRequest().setAttribute("lookupTipologia", lookupTipologia);

			
			String tableNameStrutture = "(select o.org_id as code , name as description , name as short_desription,false as default_item,1 as level,true as enabled " +
										"from organization o where o.tipologia = 6 ";
			
			
			if(getUserSiteId(context)>0)
				tableNameStrutture += " and site_id = "+getUserSiteId(context)  + ")" ;
			else
				tableNameStrutture += " )";
			LookupList lookupASL = new LookupList(db, tableNameStrutture);
			lookupASL.addItem(-1,  "TUTTE");
			context.getRequest().setAttribute("lookupASL", lookupASL);
			
			
			LookupList lookupAudit = new LookupList(db, "lookup_tipo_controllo");
			
			lookupAudit.addItem(-1, "-- SELEZIONA VOCE --");
			lookupAudit.removeElementByLevel(2);
			lookupAudit.removeElementByLevel(3);
			lookupAudit.removeElementByLevel(4); // solo per molluschi
			lookupAudit.removeElementByCode(23);
			lookupAudit.removeElementByLevel(23);
			lookupAudit.removeElementByLevel(1); //Cancello vecchio audit
			
			
			lookupAudit.addItem(22, "Supervisione mediante simulazione");
			
			lookupAudit.addItem(-1,  "");
			context.getRequest().setAttribute("lookupAudit", lookupAudit);
			
			
			LookupList lookupMotiviAudit = new LookupList();
			lookupMotiviAudit.addItem(-1,"MOTIVO AUDIT");
			
			ArrayList<MotivoAudit> listaMotiviAudit =  ControlliUfficialiUtil.getMotiviAudit(db, "(0,1,2,3,4,5)");
			for (MotivoAudit motivo : listaMotiviAudit)
			{
				if (!lookupMotiviAudit.containsKey(motivo.getIdAuditTipo()))
				lookupMotiviAudit.addItem(motivo.getIdAuditTipo(),motivo.getDescrizioneAuditTipo());
			}
			context.getRequest().setAttribute("OggettoAudit", lookupMotiviAudit);
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	


		return ("OiaSearchFormOK");
	}
	
	
	
	private void resetPagedListInfo(ActionContext context) {
		
		this.deletePagedListInfo(context, "AccountFolderInfo");
		this.deletePagedListInfo(context, "RptListInfo");
		this.deletePagedListInfo(context, "OpportunityPagedInfo");
		this.deletePagedListInfo(context, "AccountTicketInfo");
		this.deletePagedListInfo(context, "AutoGuideAccountInfo");
		this.deletePagedListInfo(context, "RevenueListInfo");
		this.deletePagedListInfo(context, "AccountDocumentInfo");
		this.deletePagedListInfo(context, "ServiceContractListInfo");
		this.deletePagedListInfo(context, "AssetListInfo");
		this.deletePagedListInfo(context, "AccountProjectInfo");
		this.deletePagedListInfo(context, "orgHistoryListInfo");
	}
	
	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}

		UserBean user = (UserBean) context.getSession().getAttribute("User");

		
		OiaList organizationList = new OiaList();
	
		

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchOrgDipListInfo");
		searchListInfo.setLink("Accounts.do?command=Search");
		searchListInfo.setListView("all");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.addItem(-2, "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList lookup_comuni = ritorna_lookup_tutti_comuni(context,-1, db);
			lookup_comuni.setSelectName("comune");
			context.getRequest().setAttribute("lookup_comuni", lookup_comuni);
			// Display list of accounts if user chooses not to list contacts
			searchListInfo.setSearchCriteria(organizationList, context);
					
					searchListInfo.setLink("Oia.do?command=Search");
				// Build the organization list
				organizationList.setPagedListInfo(searchListInfo);
				organizationList.buildList(db);
			
				context.getRequest().setAttribute("OrgList", organizationList);
				context.getSession().setAttribute("previousSearchType",
						"accounts");
				
				return "OiaSearchOK";
			
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	
	public String executeCommandReloadStrutture(ActionContext context) {
		
		Connection db = null;
		try
		{
			
			String anno = context.getParameter("anno");
			int annoInt = Calendar.YEAR;
			if (anno!=null)
				annoInt = Integer.parseInt(anno);
			db = this.getConnection(context);
			ricaricaStrutturaInMemoria(context, 201,annoInt, db);
			ricaricaStrutturaInMemoria(context, 202,annoInt, db);
			ricaricaStrutturaInMemoria(context, 203,annoInt, db);
			ricaricaStrutturaInMemoria(context, 204,annoInt, db);
			ricaricaStrutturaInMemoria(context, 205,annoInt, db);
			ricaricaStrutturaInMemoria(context, 206,annoInt, db);
			ricaricaStrutturaInMemoria(context, 207,annoInt, db);
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		
		return "-none-";
	}
	
	
	
	
	public void ricaricaStrutturaInMemoria(ActionContext context, int idAsl,int anno ,Connection db)
	{
		 
		HashMap<Integer, ArrayList<OiaNodo>> strutture = ( HashMap<Integer, ArrayList<OiaNodo>>)context.getServletContext().getAttribute("StruttureOIA");
		OiaNodo temp = new OiaNodo();
		
		
		strutture.put(idAsl, temp.loadbyidAsl(idAsl+"",anno, db));
		
	}

	


	


	private LookupList ritorna_lookup_tipologia_competenza_selezionati(ArrayList<OiaTipologiaCompetenzaNodo> tipologieCompetenzeNodo) {
		LookupList ret = new LookupList();

		for (OiaTipologiaCompetenzaNodo o : tipologieCompetenzeNodo) {
			LookupElement thisElement = new LookupElement();
			thisElement.setCode(o.getId_lookup_tipologia_competenza() );
			thisElement.setDescription(o.getTipologia_competenza_stringa());
			thisElement.setEnabled(true);
			ret.add(thisElement);
		}
		return ( ret );
	}

	private LookupList ritorna_lookup_tutti_comuni(ActionContext context,int idAsl, Connection db) {
		LookupList ret = new LookupList();
		int i=-1;

		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		String sql = "SELECT codiceistatcomune, comune FROM comuni ";
		if (idAsl>0)
		{
			sql += " where 1=1 and codiceistatasl='"+idAsl+"'"; 
		}

		try {
			stat = db.prepareStatement( sql );
			res		= stat.executeQuery();

			while( res.next() )
			{
				LookupElement thisElement = new LookupElement();
				thisElement.setCode(Integer.parseInt(res.getString(1)) );
				thisElement.setDescription(res.getString(2));
				thisElement.setEnabled(true);
				ret.add(thisElement);
			}
			return ( ret );

		} catch (Exception e) {	
			e.printStackTrace();
			return (null);
		} 
	}

	private LookupList ritorna_lookup_tutti_macrocategorie(ActionContext context, Connection db) {
		LookupList ret = new LookupList();
		int i=-1;

		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		String sql = "SELECT id, descrizione FROM nodo_macrocategorie ";
		try {
			stat = db.prepareStatement( sql );
			res		= stat.executeQuery();

			while( res.next() )
			{
				LookupElement thisElement = new LookupElement();
				thisElement.setCode(Integer.parseInt(res.getString(1)) );
				thisElement.setDescription(res.getString(2));
				thisElement.setEnabled(true);
				ret.add(thisElement);
			}
			return ( ret );

		} catch (Exception e) {	
			e.printStackTrace();
			return (null);
		} 
	}

	private String getRuoliByLivello_e_Tipologia( int livello, String tipologia) {
		String ruoli = null;

		switch (livello) {
		case 1:
			ruoli = L1_RUOLO_RESPONSABILE;
			break;
		default :
			if ( tipologia.equalsIgnoreCase("3") || tipologia.equalsIgnoreCase("4")  ) {
				ruoli = L2_RUOLO_RESPONSABILE_SIAN + ","+L4_RUOLO_SIAN;
			} else if ( tipologia.equalsIgnoreCase("2") || tipologia.equalsIgnoreCase("5") ) {
				ruoli = L2_RUOLO_RESPONSABILE_VETERINARI + ","+L4_RUOLO_VETERINARI;
			} 
			break;
		

		}

		return ruoli;
	}


	private LookupList ritorna_lookup_responsabile(ArrayList<UserInfo> utenti) {
		LookupList ret = new LookupList();
		String ruolo = "" ;
		for (UserInfo o : utenti) {
		
			
			LookupElement thisElement = new LookupElement();
			
			thisElement.setCode(o.getId_utente() );
			thisElement.setDescription(o.getCognome_utente() + " " + o.getNome_utente());
			thisElement.setEnabled(true);
			ret.add(thisElement);
		}
		return ( ret );
	}

	private LookupList ritorna_lookup_tipologia(int livello) {
		LookupList ret = new LookupList();

		if ( livello == 1) {
			LookupElement thisElement = new LookupElement();
			thisElement.setCode("Dipartimento di Prevenzione");
			thisElement.setDescription("Dipartimento di Prevenzione");
			thisElement.setEnabled(true);
			ret.add(thisElement);
		} else {
			LookupElement thisElement = new LookupElement();
			thisElement.setCode("SIAN");
			thisElement.setDescription("SIAN");
			thisElement.setEnabled(true);
			ret.add(thisElement);

			thisElement = new LookupElement();
			thisElement.setCode("Veterinari");
			thisElement.setDescription("Veterinari");
			thisElement.setEnabled(true);
			ret.add(thisElement);
		}
		return ( ret );
	}
	
	

	  public String executeCommandViewVigilanza(ActionContext context) {
	  
		    if (!hasPermission(context, "oia-oia-vigilanza-view")) {
		      return ("PermissionError");
		    }
		    Connection db = null;
		    org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		    
		    
		    String  strutturaId = context.getParameter("searchcodetipologia_struttura");
		    String idTipoProvvedimenti = "";
		    String idTipoAudit = context.getParameter("searchcodeaudit");
		    String idNodo = context.getRequest().getParameter("searchcodeid_asl");
	
		    if (idTipoAudit!=null && idTipoAudit.equals("22")){ //Modifica per supervisione
		    	idTipoProvvedimenti = idTipoAudit;
		    	idTipoAudit = "";
		    }
		    
		   
		    try {
		      db = this.getConnection(context);
		      	
//			  OiaNodo nodo = new OiaNodo(db, Integer.parseInt(idNodo), this.getSystemStatus(context));
		      Organization nodo = null;
		      
		      if (Integer.parseInt(idNodo)!=-1)
		    	  nodo = new Organization(db, Integer.parseInt(idNodo));
		      else
		    	  nodo = new Organization();

			  
			  int orgId = -1;
			  int nodoId = -1;
			  if (nodo!=null){
				  orgId = nodo.getOrgId();
				  
			  }
			  
			  //Prepare PagedLi	stInfo
			    PagedListInfo ticketListInfo = this.getPagedListInfo(
			        context, "AccountTicketInfo", "t.entered", "desc");
			    
			    String link = "Oia.do?command=ViewVigilanza&orgId=" + orgId+ "&strutturaId=" + strutturaId +"&idNodo=" + nodoId + 
				        "&searchcodeaudit=" + idTipoAudit +
				        "&searchcodeid_asl=" + idNodo;
			    if(strutturaId!=null && !strutturaId.equals("null"))
			    	link += "&searchcodetipologia_struttura=" + strutturaId;
			    ticketListInfo.setLink(link);
			    
			    ticList.setPagedListInfo(ticketListInfo);
			    
			    ticketListInfo.setSearchCriteria(ticList, context);
		      
		      SystemStatus systemStatus = this.getSystemStatus(context);
		      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
		      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("TipoCampione", TipoCampione);
		      
		      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
		      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
		      //find record permissions for portal users
		      
		      LookupList AuditTipo = new LookupList(db,"lookup_audit_tipo");
		      context.getRequest().setAttribute("AuditTipo", AuditTipo);

		      ticList.setOrgId(orgId);
		      ticList.setTipoAudit(idTipoAudit);
		      ticList.setTipologiaOperatore(6);
		      ticList.setTipoProvvedimenti(idTipoProvvedimenti);
		      
		      
		    	  ticList.setSiteId(nodo.getSiteId());
		    	  context.getRequest().setAttribute("idStruttura", strutturaId);
		      
		    	  UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
					if (thisUser.getRoleId()==Role.RUOLO_CRAS)
						ticList.setIdRuolo(thisUser.getRoleId());
					
		      ticList.buildList(db);
		      context.getRequest().setAttribute("OrgDetails", nodo);
		      context.getRequest().setAttribute("TicList", ticList);
		      addModuleBean(context, "View Accounts", "Accounts View");
		    } catch (Exception errorMessage) {
		    	errorMessage.printStackTrace();
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    return getReturn(context, "ViewVigilanza");
		  }
	  
private int CalcolaIdAslDaIdStruttura(String struttura, Connection db) throws SQLException{
	int idAsl = -1;
	
	PreparedStatement pst = null;
	String query = "select id_asl from oia_nodo where id = ? ";
	pst  = db.prepareStatement( query );
	pst.setInt(1,  Integer.parseInt(struttura));
	ResultSet rs = pst.executeQuery();
	if (rs.next())
		idAsl = rs.getInt("id_asl");
	rs.close();
	pst.close();
	
	return idAsl;
}

}
