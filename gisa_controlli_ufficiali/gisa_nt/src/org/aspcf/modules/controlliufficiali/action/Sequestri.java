/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.sequestri.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketCategory;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.troubletickets.base.TicketDefectList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class Sequestri extends CFSModule {

	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-sequestri-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		try {

			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
			String idControlloUfficiale = context.getRequest().getParameter("idControllo");

			context.getRequest().setAttribute("idControllo",idControlloUfficiale);

			if (context.getRequest().getParameter("dataC") != null)
			{
				context.getRequest().setAttribute("dataC", context.getRequest().getParameter("dataC"));
			}
			else
			{
				context.getRequest().setAttribute("dataC", context.getRequest().getAttribute("dataC"));
			}

			String id = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idC",id);
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(id));
			context.getRequest().setAttribute("CU", cu);
			context.getRequest().setAttribute("id_asl",cu.getSiteId());
			
			LookupList SequestroDi = new LookupList(db, "lookup_sequestri_amministrative");
			//Eliminare "Stabilimento" e "Locali per Sequestro Amministrativo"
			SequestroDi.removeElementByLevel(0);
			SequestroDi.removeElementByLevel(9);
			SequestroDi.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SequestroDi", SequestroDi);
			
			LookupList SequestroDi_sp = new LookupList(db, "lookup_sequestri_amministrative");
			SequestroDi_sp.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SequestroDi_sp", SequestroDi_sp);
			
			
			LookupList tipoSequestro = new LookupList(db, "lookup_tipo_sequestro");
			tipoSequestro.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoSequestro", tipoSequestro);

			LookupList EsitiSequestri = new LookupList(db, "lookup_esiti_sequestri");
			EsitiSequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitiSequestri", EsitiSequestri);

			LookupList SequestroDiStabilimento = new LookupList(db, "lookup_sequestri_amministrative_stabilimento");
			SequestroDiStabilimento.setMultiple(true);
			SequestroDiStabilimento.setSelectSize(5);

			context.getRequest().setAttribute("SequestroDiStabilimento", SequestroDiStabilimento);


			LookupList SequestroDiLocali = new LookupList(db, "lookup_sequestri_amministrative_locali");
			SequestroDiLocali.setMultiple(true);
			SequestroDiLocali.setSelectSize(5);


			context.getRequest().setAttribute("SequestroDiLocali", SequestroDiLocali);


			LookupList SequestroDiAttrezzature = new LookupList(db, "lookup_sequestri_amministrative_attrezzature");
			SequestroDiAttrezzature.setMultiple(true);
			SequestroDiAttrezzature.setSelectSize(5);


			context.getRequest().setAttribute("SequestroDiAttrezzature", SequestroDiAttrezzature);


			LookupList SequestroDiAlimentiorigineAnimale = new LookupList(db, "lookup_sequestri_amministrative_alimentiorigineanimale");
			SequestroDiAlimentiorigineAnimale.setMultiple(true);
			SequestroDiAlimentiorigineAnimale.setSelectSize(5);

			context.getRequest().setAttribute("SequestroDiAlimentiorigineAnimale", SequestroDiAlimentiorigineAnimale);


			LookupList SequestroDiAlimentiorigineVegetale = new LookupList(db, "lookup_sequestri_amministrative_alimentioriginevegetale");
			SequestroDiAlimentiorigineVegetale.setMultiple(true);
			SequestroDiAlimentiorigineVegetale.setSelectSize(5);

			context.getRequest().setAttribute("SequestroDiAlimentiorigineVegetale", SequestroDiAlimentiorigineVegetale);


			LookupList SequestroDiAnimali = new LookupList(db, "lookup_sequestri_amministrative_animali");
			SequestroDiAnimali.setMultiple(true);
			SequestroDiAnimali.setSelectSize(5);

			context.getRequest().setAttribute("SequestroDiAnimali", SequestroDiAnimali);


			LookupList SequestroDiLocalieAttrezzature = new LookupList(db, "lookup_sequestri_amministrative_locali_attrezzature");
			SequestroDiLocalieAttrezzature.setMultiple(true);
			SequestroDiLocalieAttrezzature.setSelectSize(5);

			context.getRequest().setAttribute("SequestroDiLocalieAttrezzature", SequestroDiLocalieAttrezzature);


			LookupList SequestroDivegetaleEanimale = new LookupList(db, "lookup_sequestri_amministrative_vegetale_animale");
			SequestroDivegetaleEanimale.setMultiple(true);
			SequestroDivegetaleEanimale.setSelectSize(5);

			context.getRequest().setAttribute("SequestroDivegetaleEanimale", SequestroDivegetaleEanimale);


			LookupList AzioneNonConforme = new LookupList(db, "lookup_sequestri_penali");
			AzioneNonConforme.setMultiple(true);
			AzioneNonConforme.setSelectSize(7);
			context.getRequest().setAttribute("AzioneNonConforme", AzioneNonConforme);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			//Load the organization
			Organization thisOrganization = new Organization(db, Integer.parseInt(id));
			if (context.getParameter("orgId")==null && context.getParameter("stabId")!=null)
			{
				thisOrganization.setIdStabilimento(Integer.parseInt(context.getParameter("stabId")));
				thisOrganization.setOrgId(-1);
			
			}
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc")); 
			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute( "systemStatus", this.getSystemStatus(context));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}


		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		return ("AddOK");
	}


	public String executeCommandInsert(ActionContext context) throws SQLException {
		if (!(hasPermission(context, "accounts-accounts-sequestri-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = true;
		Ticket newTicket = null;
		String id = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idC",id);
		Ticket newTic = (Ticket) context.getFormBean();
		UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
		context.getRequest().setAttribute("dataC", ""+newTic.getAssignedDate());
		newTic.setEsitoSequestro(Integer.parseInt( context.getRequest().getParameter("esitoSequestro")));  
		newTic.setDescrizione(context.getRequest().getParameter("descrizione"));
	   	newTic.setValutazione(context.getRequest().getParameter("nonConformitaGraviValutazione"));

		String site =  context.getRequest().getParameter("siteId");
		if(site.equals("201")){
			site = "AV";	
		}else if(site.equals("202")){
			site = "BN";
		}else if(site.equals("203")){
			site = "CE";
		}else if(site.equals("204")){
			site = "NA1C";
		}else if(site.equals("205")){
			site = "NA2N";
		}else if(site.equals("206")){
			site = "NA3S";
		}else if(site.equals("207")){
			site = "SA";
		}/*else if(site.equals("8")){
			site = "NA3";
		}else if(site.equals("9")){
			site = "NA4";
		}else if(site.equals("10")){
			site = "NA5";
		}else if(site.equals("11")){
			site = "SA1";
		}else if(site.equals("12")){
			site = "SA2";
		}else if(site.equals("13")){
			site = "SA3";
		}*/
		else{
			if(site.equals(16))
			{
				site ="FuoriRegione";
			}
		}
		String idControllo = context.getRequest().getParameter("idControlloUfficiale");
		String idC = context.getRequest().getParameter("idC");
		newTic.setTipo_nc(Integer.parseInt(context.getRequest().getParameter("tipoNc")));
    	String idControlloUfficiale = (String.valueOf(Integer.parseInt(id)));
        while (idControlloUfficiale.length() < 6) {
        	idControlloUfficiale = "0" + idControlloUfficiale;
        }
        newTic.setIdControlloUfficiale(idControlloUfficiale);
        
        
        
        

		newTic.setNoteSequestrodi(context.getRequest().getParameter("notesequestridi"));
		newTic.setIdentificativonc(context.getRequest().getParameter("identificativoNC"));
		newTic.setEnteredBy(getUserId(context));
		newTic.setModifiedBy(getUserId(context));
		newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
		newTic.setCodiceArticolo(Integer.parseInt(context.getRequest().getParameter("articolo")));
		String tipoSeq = context.getRequest().getParameter("TipoSequestro");
		newTic.setTipologiaSequestro(Integer.parseInt(tipoSeq));
		String oggettiSequestrati = ""; 
		if(tipoSeq.equals("1")){
			oggettiSequestrati=  context.getRequest().getParameter("SequestroDi");
		}
		else {
			oggettiSequestrati=  context.getRequest().getParameter("SequestroDi_sp");
		}
		newTic.setSequestroDi(Integer.parseInt(oggettiSequestrati));

		try {
			db = this.getConnection(context);
			
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(id));
	cu.controlloBloccoCu(db,cu.getId());
			
			cu.setFlagBloccoNonConformita(db, cu.getId());
			if(cu.isflagBloccoCu()==true && cu.isFlagBloccoNonConformita()==true)
			{
				newTic.setFlag_posticipato(true);
				newTic.setFlag_campione_non_conforme(false);
			}
			else
			{
				if(cu.isflagBloccoCu()==true && cu.isFlagBloccoNonConformita()==false)
				{
					newTic.setFlag_posticipato(true);
					newTic.setFlag_campione_non_conforme(true);
				}
			}
			
			newTic.setAssignedDate(cu.getAssignedDate());
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList SequestriAmministrative = new LookupList(db, "lookup_sequestri_amministrative");
			SequestriAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SequestriAmministrative", SequestriAmministrative);

			LookupList SequestriPenali = new LookupList(db, "lookup_sequestri_penali");
			SequestriPenali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SequestriPenali", SequestriPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);
			
			if (cu.getAltId()>0)
			{
				newTic.setAltId(cu.getAltId());
			}

			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				newTic.setTestoAppoggio("Inserimento"); //aggiunto da d.dauria
				recordInserted = newTic.insert(db,context);
				String[] oggettiSequestratiLista=null;
				if(newTic.getSequestroDi()==1){
					oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiStabilimento");

				}else{
					if(newTic.getSequestroDi()==2){
						oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiAttrezzature");

					}else{
						if(newTic.getSequestroDi()==3){
							oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiLocali");
						}
						else{
							if(newTic.getSequestroDi()==4){
								oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiLocalieAttrezzature");
							}
							else{
								if(newTic.getSequestroDi()==5){
									oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiAnimali");
								}else{
									if(newTic.getSequestroDi()==6){
										oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiAlimentiorigineAnimale");
									}else{
										if(newTic.getSequestroDi()==7){
											oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiAlimentiorigineVegetale");

										}else{
											if(newTic.getSequestroDi()==8){
												oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDivegetaleEanimale");
											}
										}
									}
								}
							}
						}
					}

				}
				newTic.insertOggettiSequestratiOAzionenonConforme(db, oggettiSequestratiLista);

			}


			if (recordInserted) {

				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);

			} 


		

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		 if (!"null".equals(context.getRequest().getParameter("inseriti")) && context.getRequest().getParameter("inseriti")!=null)
		 {
			 if (! "".equals(context.getRequest().getParameter("inseriti")))
			  {
				  context.getRequest().setAttribute("inserito",context.getRequest().getParameter("inseriti")+";"+newTicket.getId());
			  }
			  else
			  {
				  context.getRequest().setAttribute("inserito",newTicket.getId());
			  }
			    String tipo_nc = context.getRequest().getParameter("tipoNc");
				
				if (tipo_nc.equals("3"))
				{
					if(! "".equals(context.getRequest().getParameter("attivita_gravi_inseriti")))
			    	{
					
						context.getRequest().setAttribute("attivita_gravi_inseriti",context.getRequest().getParameter("attivita_gravi_inseriti")+ ";"+newTicket.getId()+"-seq");

			    	}
			    	else
			    	{
				    	 context.getRequest().setAttribute("attivita_gravi_inseriti",newTicket.getId()+"-seq");

			    	}				}
				   
		  }
		  else
		  {
			    context.getRequest().setAttribute("inserito", ""+newTicket.getId()+";");

		  }
		   context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc"));
		
		   
		  return executeCommandAdd(context); 
	}
	
	
	
	 public String executeCommandModifyTicket(ActionContext context) {
		   			
		    Connection db = null;
		    Ticket newTic = null;
		    //Parameters
		    String ticketId = context.getRequest().getParameter("id");
		    SystemStatus systemStatus = this.getSystemStatus(context);
		    try {
		      db = this.getConnection(context);
		      User user = this.getUser(context, this.getUserId(context));
		      //Load the ticket
		      if (context.getRequest().getParameter("companyName") == null || 
		    		  context.getRequest().getParameter("companyName").length() > 0 ) {
		        newTic = new Ticket(db, Integer.parseInt(ticketId));
		      } else {
		        newTic = (Ticket) context.getFormBean();
		      }
		      String id = context.getRequest().getParameter("idC");	
		    	context.getRequest().setAttribute("idC",
		    			id);
		    	
		    	String id2 = context.getRequest().getParameter("idNC");
		    	context.getRequest().setAttribute("idNC",
		    			id2);
		      //check permission to record
		     
		     
		       
		     
		      
		      LookupList EsitiSequestri = new LookupList(db, "lookup_esiti_sequestri");
		      EsitiSequestri.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("EsitiSequestri", EsitiSequestri);
		      
		      
		     
		     
		     
		      LookupList SequestroDiStabilimento = new LookupList(db, "lookup_sequestri_amministrative_stabilimento");
		      SequestroDiStabilimento.setMultiple(true);
		      SequestroDiStabilimento.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		     
		      
		      LookupList SequestroDiLocali = new LookupList(db, "lookup_sequestri_amministrative_locali");
		      SequestroDiLocali.setMultiple(true);
		      SequestroDiLocali.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		      
		      
		      LookupList SequestroDiAttrezzature = new LookupList(db, "lookup_sequestri_amministrative_attrezzature");
		      SequestroDiAttrezzature.setMultiple(true);
		      SequestroDiAttrezzature.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		    
		      
		      
		      LookupList SequestroDiAlimentiorigineAnimale = new LookupList(db, "lookup_sequestri_amministrative_alimentiorigineanimale");
		      SequestroDiAlimentiorigineAnimale.setMultiple(true);
		      SequestroDiAlimentiorigineAnimale.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		    
		      LookupList SequestroDiAlimentiorigineVegetale = new LookupList(db, "lookup_sequestri_amministrative_alimentioriginevegetale");
		      SequestroDiAlimentiorigineVegetale.setMultiple(true);
		      SequestroDiAlimentiorigineVegetale.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		    
		      
		      
		      LookupList SequestroDiAnimali = new LookupList(db, "lookup_sequestri_amministrative_animali");
		      SequestroDiAnimali.setMultiple(true);
		      SequestroDiAnimali.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		     
		      
		      LookupList SequestroDiLocalieAttrezzature = new LookupList(db, "lookup_sequestri_amministrative_locali_attrezzature");
		      SequestroDiLocalieAttrezzature.setMultiple(true);
		      SequestroDiLocalieAttrezzature.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		    
		      
		      
		      LookupList SequestroDivegetaleEanimale = new LookupList(db, "lookup_sequestri_amministrative_vegetale_animale");
		      SequestroDivegetaleEanimale.setMultiple(true);
		      SequestroDivegetaleEanimale.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		      
		      
		      
		      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
		      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
		         
		      //R.M per la modifica
		      LookupList SequestroDi = new LookupList(db, "lookup_sequestri_amministrative");
				//Eliminare "Stabilimento" e "Locali per Sequestro Amministrativo"
				SequestroDi.removeElementByLevel(0);
				SequestroDi.removeElementByLevel(9);
				context.getRequest().setAttribute("SequestroDi", SequestroDi);
				
				LookupList SequestroDi_sp = new LookupList(db, "lookup_sequestri_amministrative");
				context.getRequest().setAttribute("SequestroDi_sp", SequestroDi_sp);
		      
		      LookupList tipoSequestro = new LookupList(db, "lookup_tipo_sequestro");

		      LookupList multipleSelects=new LookupList();
		      
		      
		      
		      HashMap<Integer, String> ListaOggetti=newTic.getOggettiSequestrati();
		      Iterator<Integer> iteraKiavi= newTic.getOggettiSequestrati().keySet().iterator();
		      while(iteraKiavi.hasNext()){
		      int kiave=iteraKiavi.next();
		      String valore=ListaOggetti.get(kiave);
		    	  
		    	  multipleSelects.addItem(kiave,valore);
		    	  
		      }
		      
		      
		      if(newTic.getSequestroDi()==1){
		    	     
		   	      
		   	   SequestroDiStabilimento.setMultipleSelects(multipleSelects);
		    	  
		      }else{
		    	  if(newTic.getSequestroDi()==2){
		    		SequestroDiAttrezzature.setMultipleSelects(multipleSelects);
		        	  
		        	  
		          }else{
		        	  if(newTic.getSequestroDi()==3){
		            	SequestroDiLocali.setMultipleSelects(multipleSelects);  
		        		  
		            	  
		              }
		        	  else{
		        		  if(newTic.getSequestroDi()==4){
			            	SequestroDiLocalieAttrezzature.setMultipleSelects(multipleSelects);  
			            	  
			              }
		        		  else{
		        			  if(newTic.getSequestroDi()==5){
		    	            	SequestroDiAnimali.setMultipleSelects(multipleSelects);  

		    	            	  
		    	              }else{
		    	            	  if(newTic.getSequestroDi()==6){
		    		            	  SequestroDiAlimentiorigineAnimale.setMultipleSelects(multipleSelects);
		    	            		 
		    		            	  
		    		              }else{
		    		            	  if(newTic.getSequestroDi()==7){
		    		            	 SequestroDiAlimentiorigineVegetale.setMultipleSelects(multipleSelects);
		    		            		  
		    			              }else{
		    			            	  if(newTic.getSequestroDi()==8){
		    				            	  SequestroDivegetaleEanimale.setMultipleSelects(multipleSelects);
		    			            		
		    				            	  
		    				              }
		    			              }
		    		              }
		    	              }
		        		  }
		        	  }
		          }
		    	  
		      }
		     
		context.getRequest().setAttribute("SequestroDivegetaleEanimale", SequestroDivegetaleEanimale);
		      
		      context.getRequest().setAttribute("SequestroDiLocalieAttrezzature", SequestroDiLocalieAttrezzature);
		      context.getRequest().setAttribute("SequestroDiAnimali", SequestroDiAnimali);
		      context.getRequest().setAttribute("SequestroDiLocali", SequestroDiLocali);
		      context.getRequest().setAttribute("SequestroDiStabilimento", SequestroDiStabilimento);
		      context.getRequest().setAttribute("SequestroDiAlimentiorigineAnimale", SequestroDiAlimentiorigineAnimale);
		      context.getRequest().setAttribute("SequestroDiAlimentiorigineVegetale", SequestroDiAlimentiorigineVegetale);
		      context.getRequest().setAttribute("SequestroDiAttrezzature", SequestroDiAttrezzature);
		      
		      
		      context.getRequest().setAttribute("SequestroDi", SequestroDi);
		      context.getRequest().setAttribute("SequestroDi_sp", SequestroDi_sp);
		      context.getRequest().setAttribute("TipoSequestro", tipoSequestro);

		      LookupList AzioneNonConforme = new LookupList(db, "lookup_sequestri_penali");
		      
		      
		      AzioneNonConforme.setMultiple(true);
		      AzioneNonConforme.setSelectSize(7);
		      
		  
		      LookupList multipleSelects1=new LookupList();
		      
		      HashMap<Integer, String> ListaAzioni=newTic.getAzioniNonConformi();
		      Iterator<Integer> iteraKiaviAzioni= newTic.getAzioniNonConformi().keySet().iterator();
		      while(iteraKiaviAzioni.hasNext()){
		      int kiave=iteraKiaviAzioni.next();
		      String valore=ListaAzioni.get(kiave);
		    	  
		    	  multipleSelects1.addItem(kiave,valore);
		    	  
		      }
				org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
				context.getRequest().setAttribute("CU", cu);
		      AzioneNonConforme.setMultipleSelects(multipleSelects1);
		     
		      context.getRequest().setAttribute("AzioneNonConforme", AzioneNonConforme);
		      
		      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
		      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Sequestri", Sequestri);

		      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
		      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
		      SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
		      context.getRequest().setAttribute("SiteIdList", SiteIdList);

		    
		      addRecentItem(context, newTic);
		      context.getRequest().setAttribute("TicketDetails", newTic);
		      addModuleBean(context, "View Accounts", "View Tickets");

		      //getting current date in mm/dd/yyyy format
		      String currentDate = getCurrentDateAsString(context);
		      context.getRequest().setAttribute("currentDate", currentDate);

		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    //return "ModifyautorizzazionetrasportoanimaliviviOK";
		    return "";
		    //return getReturn(context, "ModifyTicket");
		  }
		  
	 

	  /**
	   * Update the specified ticket
	   *
	   * @param context Description of Parameter
	   * @return Description of the Returned Value
	   */
	  public String executeCommandUpdateTicket(ActionContext context) {
	  
	    Connection db = null;
	    int resultCount = 0;

	    int catCount = 0;
	    TicketCategory thisCat = null;
	    boolean catInserted = false;
	    boolean isValid = true;

	    Ticket newTic = (Ticket) context.getFormBean();
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
	    newTic.setId_nonconformita(context.getParameter("id_nonconformita"));
	    newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
	    newTic.setDescrizione(context.getRequest().getParameter("descrizione"));
	   	newTic.setValutazione(context.getRequest().getParameter("nonConformitaGraviValutazione"));
	
	    String id = context.getRequest().getParameter("idC");
	  	context.getRequest().setAttribute("idC",
	  			id);
	  	
	  	String id2 = context.getRequest().getParameter("idNC");
	  	context.getRequest().setAttribute("idNC",
	  			id2);
	  

	    try {
	      db = this.getConnection(context);
	      
	      Ticket oldTic = new Ticket(db, newTic.getId());
	      String tipoSeq = context.getRequest().getParameter("TipoSequestro");
		  newTic.setTipologiaSequestro(Integer.parseInt(tipoSeq));
		  org.aspcfs.modules.vigilanza.base.Ticket cu =new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(id));
			newTic.setAssignedDate(cu.getAssignedDate());
		  String oggettiSequestrati = "";
	      
	      if(tipoSeq.equals("1")){
	    	  oggettiSequestrati=  context.getRequest().getParameter("SequestroDi");
	      }
	      else {
	    	  oggettiSequestrati=  context.getRequest().getParameter("SequestroDi_sp");  
	      }
	      
	      if (oggettiSequestrati!=null && !oggettiSequestrati.equals("null") && !oggettiSequestrati.equals(""))
	      newTic.setSequestroDi(Integer.parseInt(oggettiSequestrati));

	      
	      newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
		    newTic.setCodiceArticolo(Integer.parseInt(context.getRequest().getParameter("articolo")));
		    newTic.setEsitoSequestro(Integer.parseInt( context.getRequest().getParameter("esitoSequestro")));   
	     newTic.setNotaSequestro(context.getRequest().getParameter("sequestroNota"));
		newTic.setNoteSequestrodi(context.getRequest().getParameter("notesequestridi"));	   
		    
	      //Get the previousTicket, update the ticket, then send both to a hook
	      Ticket previousTicket = new Ticket(db, Integer.parseInt(context.getParameter("id")));
	      newTic.setModifiedBy(getUserId(context));
	      newTic.setSiteId(context.getParameter("siteId"));
	      isValid = this.validateObject(context, db, newTic) && isValid;
	      if (isValid) {
	    	newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
	        resultCount = newTic.update(db);
			  
			  
	      }
	      if (resultCount == 1) {
	        newTic.queryRecord(db, newTic.getId());
	        processUpdateHook(context, previousTicket, newTic);
//	        TicketCategoryList ticketCategoryList = new TicketCategoryList();
//	        ticketCategoryList.setEnabledState(Constants.TRUE);
//	        ticketCategoryList.setSiteId(newTic.getSiteId());
//	        ticketCategoryList.setExclusiveToSite(true);
//	        ticketCategoryList.buildList(db);
//	        context.getRequest().setAttribute("ticketCategoryList", ticketCategoryList);
	      }
	      Integer idtampone = newTic.getId();
//			BeanSaver.save( null, newTic, newTic.getId(),
//		      		"ticket", context, db, null, "O.S.A: Modifica Sequestro", idtampone.toString() );
	      
		      

	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }

	   
	    return "";
	  }
	  
	  public String executeCommandTicketDetails(ActionContext context) {
		  
		    Connection db = null;
		    
		    if(context.getRequest().getAttribute("Messaggio")!=null)
			{
			
				context.getRequest().setAttribute("Messaggio", context.getRequest().getAttribute("Messaggio"));
			}
		    //Parameters
		    String ticketId = context.getRequest().getParameter("id");
		    String tickId = context.getRequest().getParameter("ticketId");
		    String retPag = null;
		    
		    String id = context.getRequest().getParameter("idC");
		  	context.getRequest().setAttribute("idC",
		  			id);
		  	
		  	String id2 = context.getRequest().getParameter("idNC");
		  	context.getRequest().setAttribute("idNC",
		  			id2);

		    try {
		      db = this.getConnection(context);
		      // Load the ticket
		      Ticket newTic = new Ticket();
		      SystemStatus systemStatus = this.getSystemStatus(context);
		      newTic.setSystemStatus(systemStatus);
		      if(tickId == null)
		        newTic.queryRecord(db, Integer.parseInt(ticketId));
		      else
		    	newTic.queryRecord(db, Integer.parseInt(tickId));

		      //find record permissions for portal users
		    
		  

		      
		      int passedId = newTic.getOrgId();
		      org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList();
		      int passId = newTic.getOrgId();
		      sanzioniList.setOrgId(passedId);
		      sanzioniList.buildListControlli(db, passId, ticketId,8);
		      context.getRequest().setAttribute("SanzioniList", sanzioniList);
		      
		      org.aspcfs.modules.reati.base.TicketList reatiList = new org.aspcfs.modules.reati.base.TicketList();
		      int passIdR = newTic.getOrgId();
		      reatiList.setOrgId(passedId);
		      reatiList.buildListControlli(db, passIdR, ticketId,8);
		      context.getRequest().setAttribute("ReatiList", reatiList);
		      
		      org.aspcfs.modules.sequestri.base.TicketList seqList = new org.aspcfs.modules.sequestri.base.TicketList();
		      int passIdS = newTic.getOrgId();
		      seqList.setOrgId(passedId);
		      seqList.buildListControlli(db, passIdS, ticketId,8);
		      context.getRequest().setAttribute("SequestriList", seqList);
		      
		      
		      
		      
		     
		      
		      LookupList tipoSequestro = new LookupList(db, "lookup_tipo_sequestro");
			  tipoSequestro.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("TipoSequestro", tipoSequestro);
		      
		      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
		      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SiteIdList", SiteIdList);
		      
		      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
		      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
		      
		      LookupList SequestroDi = new LookupList(db, "lookup_sequestri_amministrative");
		      SequestroDi.removeElementByLevel(0);
			  SequestroDi.removeElementByLevel(9);
		      SequestroDi.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SequestroDi", SequestroDi);
		      
		      LookupList SequestroDi_sp = new LookupList(db, "lookup_sequestri_amministrative");
		      SequestroDi.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SequestroDi_sp", SequestroDi_sp);
		      
		      
		      LookupList AzioneNonConforme = new LookupList(db, "lookup_sequestri_penali");
		      AzioneNonConforme.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("AzioneNonConforme", AzioneNonConforme);
		      
		      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
		      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Sequestri", Sequestri);

		      context.getRequest().setAttribute("TicketDetails", newTic);
		      
		      retPag = "DettaglioOK";
		      
		      addRecentItem(context, newTic);
		      // Load the organization for the header
		     
		      addModuleBean(context, "View Accounts", "View Tickets");
		      // Reset any pagedLists since this could be a new visit to this ticket
		      deletePagedListInfo(context, "AccountTicketsFolderInfo");
		      deletePagedListInfo(context, "AccountTicketDocumentListInfo");
		      deletePagedListInfo(context, "AccountTicketTaskListInfo");
		      deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
		   
		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    //return getReturn(context, "TicketDetails");
		    return "";
		  }

}
