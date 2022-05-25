/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.actions;

/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */


import com.darkhorseventures.framework.actions.ActionContext;

import org.aspcf.modules.controlliufficiali.base.ModCampioni;
import org.aspcf.modules.controlliufficiali.base.Piano;
import org.aspcf.modules.report.util.Filtro;
import org.aspcf.modules.report.util.StampaPdf;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.GenericControlliUfficiali;
import org.aspcfs.modules.base.Parameter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioni.base.Capo;
import org.aspcfs.modules.macellazioni.utils.MacelliUtil;
import org.aspcfs.modules.macellazioninew.base.BeanAjax;
import org.aspcfs.modules.macellazioninew.base.Campione;
import org.aspcfs.modules.macellazioninew.base.Esito;
import org.aspcfs.modules.macellazioninew.base.GenericBean;
import org.aspcfs.modules.macellazioninew.base.Partita;
import org.aspcfs.modules.macellazioninew.base.PartitaSeduta;
import org.aspcfs.modules.macellazioninew.base.Tampone;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninew.utils.ReflectionUtil;
import org.aspcfs.modules.molluschibivalvi.base.Coordinate;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.campioni.base.Analita;
import org.aspcfs.modules.campioni.base.Pnaa;
import org.aspcfs.modules.campioni.base.SpecieAnimali;
import org.aspcfs.modules.campioni.base.Ticket;
import org.aspcfs.modules.campioni.util.CampioniUtil;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.ParameterUtils;
import org.aspcfs.utils.web.RequestUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;

public final class CampioniAssociazioneEsiti extends CFSModule 
{
	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandRicerca( context );
	}

    public String executeCommandRicerca( ActionContext context )
	{
		String			ret		= "RicercaOK";

		if (!hasPermission(context, "associazione_esiti_campioni-view"))
		{
			return ("PermissionError");
		}

		Connection		db		= null;

		try
		{
			db		= this.getConnection( context );
			
			String data = 		context.getParameter( "data" );
			String matricola =  context.getParameter( "matricola" );
			
			ArrayList<Campione> campioni = new ArrayList<Campione>();
			int asl = ((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId();
			campioni = Campione.loadAssociazioneEsiti ( data, matricola, asl, db);
			if(campioni.isEmpty())
			{
				context.getRequest().setAttribute("matricola", matricola);
				context.getRequest().setAttribute("data", data);
				context.getRequest().setAttribute("Error", "Nessun campione trovato con i criteri selezionati");
				return executeCommandToRicerca(context);
			
			}
			else
			{
				context.getRequest().setAttribute("Campioni", campioni);
				context.getRequest().setAttribute("matricola", matricola);
				context.getRequest().setAttribute("data", data);
				caricaLookup(context, true);
			}
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return ret;
	}
    
    public String executeCommandToRicerca( ActionContext context )
	{
		String			ret		= "ToRicercaOK";

		if (!hasPermission(context, "associazione_esiti_campioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		try
		{
			caricaLookup(context, true);
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		
		
		

		return ret;
	}
    
    
    public String executeCommandAssocia( ActionContext context )
	{
		String			ret		= "RicercaOK";

		if (!hasPermission(context, "associazione_esiti_campioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		try
		{
			db		= this.getConnection( context );
			
			ArrayList<Parameter> esiti			= ParameterUtils.list( context.getRequest(), "cmp_id_esito_" );
			ArrayList<Parameter> date_ricezione	= ParameterUtils.list( context.getRequest(), "cmp_data_ricezione_esito_" );
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			int i=0;
			while(i<esiti.size())
			{
				int p = Integer.parseInt(esiti.get(i).getValore());
				if(p>0)
				{
					String p2 = date_ricezione.get(i).getValore();
					int indiceTabella = Integer.parseInt(esiti.get(i).getNome().replace("cmp_id_esito_", ""));
							
					String idCampione = context.getParameter( "id_campione_" + indiceTabella);
					org.aspcfs.modules.macellazioni.base.Campione camp = org.aspcfs.modules.macellazioni.base.Campione.load(idCampione, db);
					camp.setId_esito		( p );
					
					if(p2 != null && !p2.equals(""))
					{
						Date s = sdf.parse( p2 );
						Timestamp t = new Timestamp(s.getTime());
						camp.setData_ricezione_esito ( t )  ;
					}
					camp.update(db);
					
					if(camp.getId_capo()>0)
					{
						Capo c = Capo.load(camp.getId_capo()+"", db);
						c.update(db, org.aspcfs.modules.macellazioni.base.Campione.load(camp.getId_capo(), db));
					}
					else if(camp.getId_seduta()>0)
					{
						PartitaSeduta sed = (PartitaSeduta)PartitaSeduta.load(camp.getId_seduta()+"", db, new ConfigTipo(2));
						sed.update(db, PartitaSeduta.loadCampioni	 ( camp.getId_seduta(), db ), new ConfigTipo(2));
					}
					else if(camp.getId_partita()>0)
					{
						Partita part = (Partita)Partita.load(camp.getId_partita()+"", db, new ConfigTipo(2));
						part.update(db, org.aspcfs.modules.macellazioninew.base.Campione.load(camp.getId_partita(),new ConfigTipo(2), db), new ConfigTipo(2));
					}
				}
				i++;
			}
			context.getRequest().setAttribute("Error2", "Esiti associati");
			
			String data = 		context.getParameter( "data" );
			String matricola =  context.getParameter( "matricola" );
			
			context.getRequest().setAttribute("matricola", matricola);
			context.getRequest().setAttribute("data", data);
			
			return executeCommandRicerca(context);
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return ret;
	}
    
    
    private void caricaLookup(ActionContext context, boolean select)
	{
		Hashtable<String, String> lu = new Hashtable<String, String>();
		lu.put( "EsitiCampioni",			"m_lookup_campioni_esiti" );
		
		Enumeration<String> e = lu.keys();
		Connection db = null;

		try
		{
			db = this.getConnection( context );
			while( e.hasMoreElements() )
			{
				String key		= e.nextElement();
				String value	= lu.get( key );
				LookupList list	= new LookupList( db, value );
				if(select)
				{
					list.addItem( -1, " -- SELEZIONA -- " );
				}
				context.getRequest().setAttribute( key, list );
			}
			UserBean user=(UserBean)context.getSession().getAttribute("User");
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
	}




        
}

