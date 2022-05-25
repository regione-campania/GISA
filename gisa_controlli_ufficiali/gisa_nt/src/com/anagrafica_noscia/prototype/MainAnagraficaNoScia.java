/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype;

import java.sql.Connection;
import java.util.ArrayList;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.json.JSONObject;

import com.anagrafica_noscia.prototype.anagrafica.AnagraficaBase;
import com.anagrafica_noscia.prototype.anagrafica.AnagraficaInserimento;
import com.darkhorseventures.framework.actions.ActionContext;
import com.anagrafica_noscia.prototype.anagrafica.AnagraficaRicerca;
import com.anagrafica_noscia.prototype.base_beans.Impresa;

public class MainAnagraficaNoScia extends CFSModule {
	
	public String executeCommandDefault(ActionContext context)
	{
//		return "Home";
		return "PaginaRicercaAnagrafica";
	}
	
	/*-------------------------------------------------------------------------------
	 * PARTE NUOVA ANAGRAFICA
	 */
	
	public String executeCommandPaginaNuovaAnagrafica (ActionContext context)
	{
		return "PaginaNuovaAnagrafica";
	}
	
	public String executeCommandSalvaAnagrafica(ActionContext context)
	{
		String viewName = "SerializzaJSON";
		JSONObject obResp = new JSONObject();
		int  statoOp = -1;
		Connection conn = null;
		
		try
		{
			conn = getConnection(context);
			/*l'input arriva come stringone json */
			String dataAsJSON = context.getRequest().getParameter("dataAsJSON"); 
			JSONObject mappazzoneJSON = new JSONObject(dataAsJSON);
			/*costruisco bean che raggruppa tutte le entita' e lancio inserimento */
			int userId = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId();
			 statoOp = AnagraficaInserimento.build_and_insert_indirect (mappazzoneJSON, conn , userId); 
			
			
		 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			statoOp = -1;
		}
		finally
		{
			obResp.put("statoOp", statoOp+"");
			context.getRequest().setAttribute("oggettoRisposta", obResp);
		}
		
		
		return viewName;
	}
	
	
	
	
	
	
	 
	/*-------------------------------------------------------------------------------
	 * PARTE RICERCA ANAGRAFICA 
	 */
	
	public String executeCommandPaginaRicercaAnagrafica (ActionContext context)
	{
		return "PaginaRicercaAnagrafica";
	}
	
	public String executeCommandCercaAnagrafica(ActionContext context)
	{
		String viewName = "PaginaRisultatoRicerca";
		Connection db = null;
		JSONObject obInput = null;
		
		
		ArrayList<AnagraficaBase> results = new ArrayList<AnagraficaBase>();
		try
		{
			/*se veniamo dal sotto menu non ripetiamo la ricerca
			 * ma lasciamo che la pagina mostri i risultati che sicuramente stanno in sessione
			 *
			 *altrimenti...*/
			if(context.getRequest().getParameter("fromSubmenu") == null)
			{
				db = getConnection(context);
				
				obInput = new JSONObject(context.getRequest().getParameter("dataAsJSON"));
				
				results = AnagraficaRicerca.build_and_search(obInput, db);
				context.getRequest().getSession().setAttribute("results", results); /*per comodita' switchando tra i containers */
			}
				
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName="SystemError";
		}
		finally
		{
			freeConnection(context, db);
		}
		
		return viewName;
	}
	
	
	/*
	 * -----------------------------------------------------------------------
	 * PARTE PER IL DETTAGLIO 
	 * a questo dettaglio si arriva o dal cavaliere no scia
	 * o dal cavaliere di ricerca anagrafica generale 
	 */
	
	public String executeCommandDetails(ActionContext context)
	{
		
		Integer idImpresa = null; 
		Connection conn = null;
		
		try
		{
			conn = getConnection(context);
			/*se stiamo arrivando nel dettaglio dal cavaliere apposito no scia */
			/*allora sara' valorizzato il parametro idImpresa o l'attributo */
			if( context.getRequest().getParameter("idImpresa") != null || context.getRequest().getAttribute("idImpresa") != null)
			{
				try
				{
					idImpresa = Integer.parseInt(
							
									 context.getRequest().getParameter("idImpresa") != null
									 ?  context.getRequest().getParameter("idImpresa") 
									 : 	(String)context.getRequest().getAttribute("idImpresa")
								);
				}
				catch(NumberFormatException ex)
				{
					/*number format exception, non e' riuscito a fare parsing di idimpresa 
					 * allora abbiamo cliccato sul submenu "dettaglio anagrafica" senza aver ancora scelto
					nessun dettaglio, quindi in tal caso rimandiamo alla ricerca
					 */
					return "PaginaRicercaAnagrafica"; /*e questo rimanda alla pagina dove settare criteri di ricerca nel cavaliere no scia */
				}
				
			}
			else if (context.getRequest().getAttribute("opId") != null)/*altrimenti se si arriva da anagrafica stabilimenti (la ricerca generale) avremo valorizzato opId */
			{
				idImpresa = Integer.parseInt((String)context.getRequest().getAttribute("opId"));
			}
			else /*se non e' proprio valorizzato, allora     */
			{
				return "PaginaRicercaAnagrafica"; /*e questo rimanda alla pagina dove settare criteri di ricerca nel cavaliere no scia */
			}

			Impresa impresa = Impresa.getByOid(conn, idImpresa);
			context.getRequest().setAttribute("impresa", impresa);
		}
		catch(Exception ex)
		{
			return "SystemError";
		}
		finally
		{
			freeConnection(context,conn);
		}
		
		return "PaginaDettaglio";
	}
	
	
	
	
	
	//-------------------------------------
	/*PARTE MODIFICA ANAGRAFICA
	 * metodo chiamato in maniera asincrona (dal servizio ajax clientside) per modificare dati anagrafica
	 */
	public String executeCommandModifyAnagrafica(ActionContext context)
	{
		String viewName ="SerializzaJSON";
		JSONObject respObj = new JSONObject();
		String statoOp = "-1";
		Connection conn = null;
		try
		{
			/*TODO IMPLEMENTARE */
			throw new Exception("OPERAZIONE NON IMPLEMENTATA -----------------------------------------------------");
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			freeConnection(context, conn);
			respObj.put("statoOp", statoOp);
			context.getRequest().setAttribute("oggettoRisposta", respObj);
		}
		
		return viewName;
	}
	
}
