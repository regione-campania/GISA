/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedeadeguamenti.actions;

import java.sql.Connection;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.schedeadeguamenti.base.SchedaBiogas;
import org.aspcfs.modules.schedeadeguamenti.base.SchedaPiccioni;

import com.darkhorseventures.framework.actions.ActionContext;

public final class SchedeAdeguamentiAction extends CFSModule {
	
	
		public String executeCommandViewSchedaBiogas(ActionContext context) {
			
			int orgId = -1;
			int altId = -1;
			
			try {orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));} catch (Exception e) {}
			if (orgId ==-1)
				try { orgId = Integer.parseInt((String) context.getRequest().getAttribute("orgId"));} catch (Exception e) {}
			
			try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e) {}
			if (altId ==-1)
				try { altId = Integer.parseInt((String) context.getRequest().getAttribute("altId"));} catch (Exception e) {}
			
			context.getRequest().setAttribute("orgId", String.valueOf(orgId));
			context.getRequest().setAttribute("altId", String.valueOf(altId));

				Connection db = null;
						
				try {
					db = this.getConnection(context);
					SchedaBiogas dettaglioBiogas = new SchedaBiogas();
					dettaglioBiogas.loadByAnagrafica(db, orgId, altId);
					context.getRequest().setAttribute("DettaglioBiogas", dettaglioBiogas);

				} catch (Exception e) {
					context.getRequest().setAttribute("Error", e);
				} finally{
					freeConnection(context, db);
				}
				return getReturn(context, "ViewSchedaBiogas");
		}
		public String executeCommandAddSchedaBiogas(ActionContext context) {
			
			int orgId = -1;
			int altId = -1;
			
			try {orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));} catch (Exception e) {}
			if (orgId ==-1)
				try { orgId = Integer.parseInt((String) context.getRequest().getAttribute("orgId"));} catch (Exception e) {}
			
			try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e) {}
			if (altId ==-1)
				try { altId = Integer.parseInt((String) context.getRequest().getAttribute("altId"));} catch (Exception e) {}
			
			context.getRequest().setAttribute("orgId", String.valueOf(orgId));
			context.getRequest().setAttribute("altId", String.valueOf(altId));

				return getReturn(context, "AddSchedaBiogas");
		}
			
			public String executeCommandInsertSchedaBiogas(ActionContext context) {
				
				Connection db = null;
				
				
				try {
					db = this.getConnection(context);
					
					SchedaBiogas schedaBiogas = new SchedaBiogas(context);
					
					if (schedaBiogas.getOrgId()>0 || schedaBiogas.getAltId()>0)
						schedaBiogas.insert(db,  getUserId(context));
					
					if (schedaBiogas.getId()>0)
						context.getRequest().setAttribute("MessaggioSchedaBiogas", "Adeguamento Scheda Biogas effettuato con successo.");
					else
						context.getRequest().setAttribute("MessaggioSchedaBiogas", "Si e' verificato un errore nel salvataggio della Scheda Adeguamento Biogas.");
				
					context.getRequest().setAttribute("orgId", String.valueOf(schedaBiogas.getOrgId()));	
					context.getRequest().setAttribute("altId", String.valueOf(schedaBiogas.getAltId()));	

				} catch (Exception e) {
					context.getRequest().setAttribute("Error", e);
				} finally{
					freeConnection(context, db);
				}
				return executeCommandViewSchedaBiogas(context);

		}
			
			public String executeCommandViewSchedaPiccioni(ActionContext context) {
				
				int orgId = -1;
				
				try {orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));} catch (Exception e) {}
				if (orgId ==-1)
					try { orgId = Integer.parseInt((String) context.getRequest().getAttribute("orgId"));} catch (Exception e) {}
			
				context.getRequest().setAttribute("orgId", String.valueOf(orgId));

					Connection db = null;
							
					try {
						db = this.getConnection(context);
						SchedaPiccioni dettaglioPiccioni = new SchedaPiccioni();
						dettaglioPiccioni.loadByAnagrafica(db, orgId);
						context.getRequest().setAttribute("DettaglioPiccioni", dettaglioPiccioni);

					} catch (Exception e) {
						context.getRequest().setAttribute("Error", e);
					} finally{
						freeConnection(context, db);
					}
					return getReturn(context, "ViewSchedaPiccioni");
			}
			public String executeCommandAddSchedaPiccioni(ActionContext context) {
				
				int orgId = -1;
				
				try {orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));} catch (Exception e) {}
				if (orgId ==-1)
					try { orgId = Integer.parseInt((String) context.getRequest().getAttribute("orgId"));} catch (Exception e) {}
				
				context.getRequest().setAttribute("orgId", String.valueOf(orgId));

					return getReturn(context, "AddSchedaPiccioni");
			}
				
				public String executeCommandInsertSchedaPiccioni(ActionContext context) {
					
					Connection db = null;
					
					
					try {
						db = this.getConnection(context);
						
						SchedaPiccioni schedaPiccioni = new SchedaPiccioni(context);
						
						if (schedaPiccioni.getOrgId()>0)
							schedaPiccioni.insert(db,  getUserId(context));
						
						if (schedaPiccioni.getId()>0)
							context.getRequest().setAttribute("MessaggioSchedaPiccioni", "Adeguamento Scheda Piccioni effettuato con successo.");
						else
							context.getRequest().setAttribute("MessaggioSchedaPiccioni", "Si e' verificato un errore nel salvataggio della Scheda Adeguamento Piccioni.");
					
						context.getRequest().setAttribute("orgId", String.valueOf(schedaPiccioni.getOrgId()));	

					} catch (Exception e) {
						context.getRequest().setAttribute("Error", e);
					} finally{
						freeConnection(context, db);
					}
					return executeCommandViewSchedaPiccioni(context);

			}

}
