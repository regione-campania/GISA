/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.actions;

import java.sql.Connection;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.campioni.base.MacroareaAnomalieValutazione;
import org.aspcfs.modules.campioni.base.MacroareaValutazioneComportamentale;
import org.aspcfs.modules.campioni.base.Ticket;
import org.aspcfs.modules.canili.actions.CaniliCampioni;
import org.aspcfs.modules.canipadronali.actions.CaniPadronaliCampioni;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.operatori_commerciali.actions.AccountCampioni;

import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class IuvValutazioneComportamentale extends CFSModule {

	


	
	
	public String executeCommandToViewCampione(ActionContext context) {

		Connection db = null;
		Ticket thisTicket = null;
	
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("idCampione")));
			String classeMacroarea = context.getRequest().getParameter("moduloMacroarea");
			Class generic = Class.forName(classeMacroarea);
			context.getRequest().setAttribute("idCampione", ""+thisTicket.getId());
			context.getRequest().setAttribute("orgId", ""+thisTicket.getOrgId());
			if (classeMacroarea.startsWith("org.aspcfs.modules.canipadronali.actions.CaniPadronali"))
			{
				CaniPadronaliCampioni action = (CaniPadronaliCampioni)generic.newInstance();
				return action.executeCommandTicketDetails(context)+"_canipadronali";
			}
			
			if (classeMacroarea.startsWith("org.aspcfs.modules.operatori_commerciali.actions.OperatoriCommerciali"))
			{
				AccountCampioni action = (AccountCampioni)generic.newInstance();
				return action.executeCommandTicketDetails(context)+"_operatoricomm";
			}
			
			if (classeMacroarea.startsWith("org.aspcfs.modules.canili.actions.Canili"))
			{
				CaniliCampioni action = (CaniliCampioni)generic.newInstance();
				return action.executeCommandTicketDetails(context)+"_canili";
			}
				
			
			
		} catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);

		} finally {
			this.freeConnection(context, db);
		}
		return null ;
		
	}

	public String executeCommandToViewValutazione(ActionContext context) {

		Connection db = null;
		Ticket thisTicket = null;
	
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("idCampione")));
			MacroareaValutazioneComportamentale scheda = new MacroareaValutazioneComportamentale(db,thisTicket.getId());
			context.getRequest().setAttribute("idCampione", thisTicket.getId());
			if (scheda.getId()>0)
			{
				return executeCommandView(context);
			}
			else
			{
				return executeCommandAdd(context);
			}
			
			
			
		} catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);

		} finally {
			this.freeConnection(context, db);
		}
		return null ;
		
	}

		/**
		 * Re-opens a ticket
		 * 
		 * @param context
		 *            Description of the Parameter
		 * @return Description of the Return Value
		 */
		public String executeCommandAdd(ActionContext context) {

			Connection db = null;
			Ticket thisTicket = null;
			
			String layout= context.getRequest().getParameter("layout");
			if (layout==null)
				layout=(String) context.getRequest().getAttribute("layout");
			context.getRequest().setAttribute("layout", layout);
		
			try {
				db = this.getConnection(context);
				if(context.getRequest().getParameter("idCampione")!=null)
					thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("idCampione")));
				else
					thisTicket = new Ticket(db, ((Integer)context.getRequest().getAttribute("idCampione")));
				LookupList lookup_stato_generale = new LookupList(db,"lookup_campioni_iuv_stato_generale");
				lookup_stato_generale.addItem(-1,"SELEZIONA VOCE");

				LookupList lookup_anomalie = new LookupList(db,"lookup_campioni_iuv_anomalie_comportamentali");
				lookup_anomalie.addItem(-1,"SELEZIONA VOCE");
				
				LookupList lookup_tolleranza = new LookupList(db,"lookup_campioni_iuv_tolleranza_manipolazioni");
				lookup_tolleranza.addItem(-1,"SELEZIONA VOCE");
				
				LookupList lookup_adottabilita = new LookupList(db,"lookup_campioni_iuv_adottabilita");
				lookup_adottabilita.addItem(-1,"SELEZIONA VOCE");
				
				context.getRequest().setAttribute("lookup_stato_generale", lookup_stato_generale);
				context.getRequest().setAttribute("lookup_anomalie", lookup_anomalie);
				context.getRequest().setAttribute("lookup_tolleranza", lookup_tolleranza);
				context.getRequest().setAttribute("lookup_adottabilita", lookup_adottabilita);
				context.getRequest().setAttribute("DettaglioCampione", thisTicket);
				
			} catch (Exception errorMessage) 
			{
				context.getRequest().setAttribute("Error", errorMessage);

			} finally {
				this.freeConnection(context, db);
			}

			if (layout!=null && layout.equals("style"))
				return "AddStyleOK";
			return "AddOK";
		}
		
		
		public String executeCommandInsert(ActionContext context) {

			Connection db = null;
			Ticket thisTicket = null;
			
			String layout= context.getRequest().getParameter("layout");
			if (layout==null)
				layout=(String) context.getRequest().getAttribute("layout");
			context.getRequest().setAttribute("layout", layout);
			
			try 
			{
				db = this.getConnection(context);
				thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("idCampione")));
				
				MacroareaValutazioneComportamentale scheda = (MacroareaValutazioneComportamentale) context.getFormBean() ;
				UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
				scheda.setModifiedBy(user.getUserId());
				/*
				String[] arrayAnomalie = context.getRequest().getParameterValues("listaAnomalie");
				for(int i = 0 ; i < arrayAnomalie.length; i ++)
				{
					scheda.getListaAnomalie().add(new MacroareaAnomalieValutazione(Integer.parseInt(arrayAnomalie[i])));
				}
				*/
				scheda.insert(db,context);
				
				context.getRequest().setAttribute("TicketDetails", thisTicket);
				context.getRequest().setAttribute("SchedaValutazione", scheda);



			} catch (Exception errorMessage) 
			{
				context.getRequest().setAttribute("Error", errorMessage);

			} finally {
				this.freeConnection(context, db);
			}

			if (layout!=null && layout.equals("style"))
				return executeCommandView(context);
			return "InsertOK";
		}
		
		public String executeCommandView(ActionContext context) {

			Connection db = null;
			Ticket thisTicket = null;
			
			String layout= context.getRequest().getParameter("layout");
			if (layout==null)
				layout=(String) context.getRequest().getAttribute("layout");
			context.getRequest().setAttribute("layout", layout);

			try 
			{
				db = this.getConnection(context);
				if(context.getRequest().getParameter("idCampione")!=null)
					thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("idCampione")));
				else
					thisTicket = new Ticket(db, ((Integer)context.getRequest().getAttribute("idCampione")));
				
				//MacroareaValutazioneComportamentale scheda = new MacroareaValutazioneComportamentale(db,thisTicket.getId());
				MacroareaValutazioneComportamentale scheda = new MacroareaValutazioneComportamentale(db,thisTicket.getId());
				context.getRequest().setAttribute("SchedaValutazione", scheda);
				
				context.getRequest().setAttribute("DettaglioCampione", thisTicket);
				LookupList lookup_stato_generale = new LookupList(db,"lookup_campioni_iuv_stato_generale");
				lookup_stato_generale.addItem(-1,"SELEZIONA VOCE");

				LookupList lookup_anomalie = new LookupList(db,"lookup_campioni_iuv_anomalie_comportamentali");
				lookup_anomalie.addItem(-1,"SELEZIONA VOCE");
				
				LookupList lookup_tolleranza = new LookupList(db,"lookup_campioni_iuv_tolleranza_manipolazioni");
				lookup_tolleranza.addItem(-1,"SELEZIONA VOCE");
				
				LookupList lookup_adottabilita = new LookupList(db,"lookup_campioni_iuv_adottabilita");
				lookup_adottabilita.addItem(-1,"SELEZIONA VOCE");
				
				context.getRequest().setAttribute("lookup_stato_generale", lookup_stato_generale);
				context.getRequest().setAttribute("lookup_anomalie", lookup_anomalie);
				context.getRequest().setAttribute("lookup_tolleranza", lookup_tolleranza);
				context.getRequest().setAttribute("lookup_adottabilita", lookup_adottabilita);

			} catch (Exception errorMessage) 
			{
				context.getRequest().setAttribute("Error", errorMessage);

			} finally {
				this.freeConnection(context, db);
			}

			if (layout!=null && layout.equals("style"))
				return "ViewStyleOK";
			return "ViewOK";
		}
		
		public String executeCommandModify(ActionContext context) {

			Connection db = null;
			Ticket thisTicket = null;
			
			String layout= context.getRequest().getParameter("layout");
			if (layout==null)
				layout=(String) context.getRequest().getAttribute("layout");
			context.getRequest().setAttribute("layout", layout);
			
			try 
			{
				db = this.getConnection(context);
				thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("idCampione")));
				
				MacroareaValutazioneComportamentale scheda = new MacroareaValutazioneComportamentale(db,thisTicket.getId());
				context.getRequest().setAttribute("SchedaValutazione", scheda);
				thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("idCampione")));
				LookupList lookup_stato_generale = new LookupList(db,"lookup_campioni_iuv_stato_generale");
				lookup_stato_generale.addItem(-1,"SELEZIONA VOCE");

				LookupList lookup_anomalie = new LookupList(db,"lookup_campioni_iuv_anomalie_comportamentali");
				lookup_anomalie.addItem(-1,"SELEZIONA VOCE");
				
				LookupList lookup_tolleranza = new LookupList(db,"lookup_campioni_iuv_tolleranza_manipolazioni");
				lookup_tolleranza.addItem(-1,"SELEZIONA VOCE");
				
				LookupList lookup_adottabilita = new LookupList(db,"lookup_campioni_iuv_adottabilita");
				lookup_adottabilita.addItem(-1,"SELEZIONA VOCE");
				
				context.getRequest().setAttribute("lookup_stato_generale", lookup_stato_generale);
				context.getRequest().setAttribute("lookup_anomalie", lookup_anomalie);
				context.getRequest().setAttribute("lookup_tolleranza", lookup_tolleranza);
				context.getRequest().setAttribute("lookup_adottabilita", lookup_adottabilita);
				context.getRequest().setAttribute("DettaglioCampione", thisTicket);
				

			} catch (Exception errorMessage) 
			{
				context.getRequest().setAttribute("Error", errorMessage);

			} finally {
				this.freeConnection(context, db);
			}

			if (layout!=null && layout.equals("style"))
				return "ModifyStyleOK";
			return "ModifyOK";
		}
		
		public String executeCommandUpdate(ActionContext context) {

			Connection db = null;
			Ticket thisTicket = null;
			
			String layout= context.getRequest().getParameter("layout");
			if (layout==null)
				layout=(String) context.getRequest().getAttribute("layout");
			context.getRequest().setAttribute("layout", layout);
			
			try 
			{
				db = this.getConnection(context);
				thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("idCampione")));
				
				MacroareaValutazioneComportamentale scheda = (MacroareaValutazioneComportamentale) context.getFormBean() ;
				UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
				scheda.setModifiedBy(user.getUserId());
				/*String[] arrayAnomalie = context.getRequest().getParameterValues("listaAnomalie");
				for(int i = 0 ; i < arrayAnomalie.length; i ++)
				{
					scheda.getListaAnomalie().add(new MacroareaAnomalieValutazione(Integer.parseInt(arrayAnomalie[i])));
				}
				*/
				scheda.update(db,context);
				
				context.getRequest().setAttribute("DettaglioCampione", thisTicket);
				context.getRequest().setAttribute("idCampione", thisTicket.getId());

			} catch (Exception errorMessage) 
			{
				context.getRequest().setAttribute("Error", errorMessage);

			} finally {
				this.freeConnection(context, db);
			}
			return executeCommandView(context);

			

		}

}
