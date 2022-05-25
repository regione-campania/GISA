/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedaMorsicatura.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.schedaMorsicatura.base.Criterio;
import org.aspcfs.modules.schedaMorsicatura.base.Indice;
import org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicatura;
import org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicaturaRecords;
import org.aspcfs.modules.schedaMorsicatura.base.Valutazione;
import org.bouncycastle.ocsp.Req;

import com.darkhorseventures.framework.actions.ActionContext;

public class SchedaMorsicaturaAction extends CFSModule 
{

	private final static Logger log = Logger.getLogger(org.aspcfs.modules.anagrafe_animali.actions.AnimaleAction.class);

	public String executeCommandDetail(ActionContext context) 
	{
		if (!hasPermission(context, "schedamorsicatura-view")) 
		{
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		
		String fromRegistrazione = context.getRequest().getParameter("fromRegistrazione");
		context.getRequest().setAttribute("fromRegistrazione", fromRegistrazione);
		
		Connection db = null;
		try 
		{
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			//Recupero parametri in input
			int idAnimale = -1;
			int id = -1;
			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
			String idString = (String) context.getRequest().getParameter("id");
			if(idAnimaleString==null || idAnimaleString.equals("") || idAnimaleString.equals("-1"))
			{
				if(idString==null || idString.equals("") || idString.equals("-1"))
				{
					throw new Exception("Parametro idAnimale non trovato");
				}
				else
				{
					id = Integer.parseInt(idString);
				}
			}
			else
			{
				idAnimale = Integer.parseInt(idAnimaleString);
			}
			//Fine Recupero parametri in input
			
			Criterio criterio = new Criterio();
			ArrayList<Criterio> criteri = criterio.getAll(db);
			context.getRequest().setAttribute("criteri", criteri);

			ArrayList<SchedaMorsicatura> schede = null;
			if(idAnimale>0)
				schede = new SchedaMorsicatura().getByIdAnimale(db, idAnimale);
			else
			{
				schede = new ArrayList<SchedaMorsicatura>();
				schede.add(new SchedaMorsicatura().getById(db, id));
			}
			
			//if exist schedamorsicatura per il cane
			if(!schede.isEmpty())
			{
				//Mostro l'ultima inserita
				SchedaMorsicatura scheda = schede.get(0);
				ArrayList<SchedaMorsicaturaRecords> records = new SchedaMorsicaturaRecords().getByIdScheda(db, scheda.getId());
				scheda.setRecords(records);
				context.getRequest().setAttribute("scheda", scheda);
				
				Valutazione valutazione = scheda.getValutazione(db,scheda.getId());
				context.getRequest().setAttribute("valutazione", valutazione);
			
				if(idAnimale<=0)
					idAnimale = scheda.getIdAnimale();
				Animale animaleDettaglio = new Animale(db, idAnimale);
				context.getRequest().setAttribute("animaleDettaglio", animaleDettaglio);
				
				
				if(fromRegistrazione!=null && fromRegistrazione.equals("true"))
					return getReturn(context, "DetailNoNAV");
				else
					return getReturn(context, "Detail");
			}
			else
			{
				context.getRequest().setAttribute("scheda", null);
				
				if(fromRegistrazione!=null && fromRegistrazione.equals("true"))
					return getReturn(context, "DetailNoNAV");
				else
					return getReturn(context, "Detail");
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
		if (!hasPermission(context, "schedamorsicatura-add")) 
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
			
			int i=0;
			while(i<criteri.size()) 
			{
				Criterio criterioTemp = criteri.get(i);
				Indice indice = new Indice();
				ArrayList<Indice> indici = indice.getByCriterio(db, criterioTemp.getId());
				criterioTemp.setIndici(indici);
				criteri.remove(i);
				criteri.add(i, criterioTemp);
				i++;
			}
			
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
		
		String fromRegistrazione = context.getRequest().getParameter("fromRegistrazione");
		context.getRequest().setAttribute("fromRegistrazione", fromRegistrazione);
		if(fromRegistrazione!=null && fromRegistrazione.equals("true"))
			return getReturn(context, "AddNoNAV");
		else
			return getReturn(context, "Add");

		
	}
	
	public String executeCommandAdd(ActionContext context) 
	{
		if (!hasPermission(context, "schedamorsicatura-add")) 
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
			SchedaMorsicatura scheda = new SchedaMorsicatura();
			scheda.setIdAnimale(idAnimale);
			scheda.setEnteredBy(user.getUserId());
			scheda.setModifiedBy(user.getUserId());
			scheda.insert(db);
			
			SchedaMorsicaturaRecords schedaRecord = new SchedaMorsicaturaRecords();
			schedaRecord.setIdScheda(scheda.getId());
			schedaRecord.setEnteredBy(user.getUserId());
			schedaRecord.setModifiedBy(user.getUserId());
			
			int i=0;
			while(i<criteri.size())
			{
				
				criterio = criteri.get(i);
				
				if(criterio.getFormulaCalcoloPunteggio().equals("divisione_indice"))
				{
					ArrayList<Indice> indici = new Indice().getByCriterio(db, criterio.getId());
					int j=0;
					while(j<indici.size())
					{
						Indice indice = indici.get(j);
						String valoreManuale = (String) context.getRequest().getParameter("valoreManualeIndice" + indice.getId());
						schedaRecord.setIdIndice(indice.getId());
						schedaRecord.setValoreManuale(valoreManuale);
						schedaRecord.insert(db);
						j++;
					}
					
				}
				else
				{
					int idIndice = -1;
					String idIndiceString = (String) context.getRequest().getParameter("indice"+criterio.getId());
					if(idIndiceString!=null && !idIndiceString.equals("") && !idIndiceString.equals("-1"))
					{
						idIndice = Integer.parseInt(idIndiceString);
					}
					schedaRecord.setIdIndice(idIndice);
					schedaRecord.setValoreManuale(null);
					schedaRecord.insert(db);
				}
				i++;
			}
			
			context.getRequest().setAttribute("stampaAutomatica", true);
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
