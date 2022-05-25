/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedaAdozioneCani.actions;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.schedaAdozioneCani.base.Criterio;
import org.aspcfs.modules.schedaAdozioneCani.base.Indice;
import org.aspcfs.modules.schedaAdozioneCani.base.SchedaAdozione;
import org.aspcfs.modules.schedaAdozioneCani.base.Valutazione;

import com.darkhorseventures.framework.actions.ActionContext;

public class SchedaAdozioneCaniAction extends CFSModule 
{

	private final static Logger log = Logger.getLogger(org.aspcfs.modules.anagrafe_animali.actions.AnimaleAction.class);

	public String executeCommandDetail(ActionContext context) 
	{
		if (!hasPermission(context, "schedaadozionecani-view")) 
		{
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		try 
		{
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			//Recupero parametri in input
			int idAnimale = -1;
			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
			if(idAnimaleString==null || idAnimaleString.equals("") || idAnimaleString.equals("-1"))
			{
				throw new Exception("Parametro idAnimale non trovato");
			}
			else
			{
				idAnimale = Integer.parseInt(idAnimaleString);
			}
			//Fine Recupero parametri in input
			
			//if exist schedaadozionecani per il cane
			SchedaAdozione scheda = new SchedaAdozione();
			ArrayList<SchedaAdozione> schede = scheda.getByIdAnimale(db, idAnimale);
			if(schede.size()>0)
			{
				context.getRequest().setAttribute("schede", schede);
				
				Valutazione valutazione = scheda.getValutazione(db,idAnimale);
				context.getRequest().setAttribute("valutazione", valutazione);
			
				Animale animaleDettaglio = new Animale(db, idAnimale);
				context.getRequest().setAttribute("animaleDettaglio", animaleDettaglio);
				
				return getReturn(context, "Detail");
			}
			else
			{
				return executeCommandToAdd(context);
			}

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
	}
	
	public String executeCommandToAdd(ActionContext context) 
	{
		if (!hasPermission(context, "schedaadozionecani-add")) 
		{
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		try 
		{
			Thread t = Thread.currentThread();
			db = this.getConnection(context);
			//Recupero lookup
			Criterio criterio = new Criterio();
			ArrayList<Criterio> criteri = criterio.getAll(db);
			context.getRequest().setAttribute("criteri", criteri);
			
			Indice indice = new Indice();
			ArrayList<Indice> indici = indice.getAll(db);
			context.getRequest().setAttribute("indici", indici);
			
			
			int idAnimale = -1;
			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
			if(idAnimaleString==null || idAnimaleString.equals("") || idAnimaleString.equals("-1"))
			{
				throw new Exception("Parametro idAnimale non trovato");
			}
			else
			{
				idAnimale = Integer.parseInt(idAnimaleString);
			}
			Animale animaleDettaglio = new Animale(db, idAnimale);
			context.getRequest().setAttribute("animaleDettaglio", animaleDettaglio);

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}

		return getReturn(context, "Add");
	}
	
	public String executeCommandToEdit(ActionContext context) 
	{
		if (!hasPermission(context, "schedaadozionecani-add")) 
		{
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		try 
		{
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			//Recupero parametri in input
			int idAnimale = -1;
			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
			if(idAnimaleString==null || idAnimaleString.equals("") || idAnimaleString.equals("-1"))
			{
				throw new Exception("Parametro idAnimale non trovato");
			}
			else
			{
				idAnimale = Integer.parseInt(idAnimaleString);
			}
			
			Animale animaleDettaglio = new Animale(db, idAnimale);
			context.getRequest().setAttribute("animaleDettaglio", animaleDettaglio);
			//Fine Recupero parametri in input
			
			//if exist schedaadozionecani per il cane
			SchedaAdozione scheda = new SchedaAdozione();
			ArrayList<SchedaAdozione> schede = scheda.getByIdAnimale(db, idAnimale);
			context.getRequest().setAttribute("schede", schede);
			
			Criterio criterio = new Criterio();
			ArrayList<Criterio> criteri = criterio.getAll(db);
			context.getRequest().setAttribute("criteri", criteri);
			
			Indice indice = new Indice();
			ArrayList<Indice> indici = indice.getAll(db);
			context.getRequest().setAttribute("indici", indici);
			
			
			return getReturn(context, "Edit");

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
	}
	
	public String executeCommandAdd(ActionContext context) 
	{
		if (!hasPermission(context, "schedaadozionecani-add")) 
		{
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		try 
		{
			Thread t = Thread.currentThread();
			db = this.getConnection(context);
			
			int idAnimale = -1;
			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
			if(idAnimaleString==null || idAnimaleString.equals("") || idAnimaleString.equals("-1"))
			{
				throw new Exception("Parametro idAnimale non trovato");
			}
			else
			{
				idAnimale = Integer.parseInt(idAnimaleString);
			}
			
			Criterio criterio = new Criterio();
			ArrayList<Criterio> criteri = criterio.getAll(db);
			
			//Inserimento scheda
			int i=0;
			while(i<criteri.size())
			{
				criterio = criteri.get(i);
				int idIndice = -1;
				String idIndiceString = (String) context.getRequest().getParameter("indice"+criterio.getId());
				if(idIndiceString!=null && !idIndiceString.equals("") && !idIndiceString.equals("-1"))
				{
					idIndice = Integer.parseInt(idIndiceString);
				}
				
				SchedaAdozione scheda = new SchedaAdozione();
				scheda.setIdAnimale(idAnimale);
				scheda.setEnteredBy(user.getUserId());
				scheda.setIdCriterio(criterio.getId());
				scheda.setIdIndice(idIndice);
				scheda.setModifiedBy(user.getUserId());
				scheda.insert(db);
				
				i++;
			}
			
			return executeCommandDetail(context);

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
	}
	
	public String executeCommandEdit(ActionContext context) 
	{
		if (!hasPermission(context, "schedaadozionecani-add")) 
		{
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		try 
		{
			Thread t = Thread.currentThread();
			db = this.getConnection(context);
			
			int idAnimale = -1;
			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
			if(idAnimaleString==null || idAnimaleString.equals("") || idAnimaleString.equals("-1"))
			{
				throw new Exception("Parametro idAnimale non trovato");
			}
			else
			{
				idAnimale = Integer.parseInt(idAnimaleString);
			}
			
			Criterio criterio = new Criterio();
			ArrayList<Criterio> criteri = criterio.getAll(db);
			
			//Inserimento scheda
			int i=0;
			while(i<criteri.size())
			{
				criterio = criteri.get(i);
				
				int idIndice = -1;
				String idIndiceString = (String) context.getRequest().getParameter("indice"+criterio.getId());
				if(idIndiceString!=null && !idIndiceString.equals("") && !idIndiceString.equals("-1"))
				{
					idIndice = Integer.parseInt(idIndiceString);
				}
				
				SchedaAdozione scheda = new SchedaAdozione();
				scheda = scheda.get(db, criterio.getId(),idAnimale);
				scheda.setIdIndice(idIndice);
				scheda.setModifiedBy(user.getUserId());
				scheda.insertStorico(db);
				scheda.update(db);
				
				i++;
			}
			
			return executeCommandDetail(context);

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
	}
	
	
	

}
