/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mycfs.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Enumeration;

import javax.xml.parsers.ParserConfigurationException;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.controller.actions.Tree;
import org.aspcfs.modules.programmazzionecu.base.PianoMonitoraggioList;
import org.aspcfs.utils.web.LookupList;
import org.xml.sax.SAXException;

import com.darkhorseventures.framework.actions.ActionContext;
import com.sun.jndi.toolkit.dir.ContextEnumerator;

public class MatriciPiani extends CFSModule {
	

	public String executeCommandViewAssociazioni(ActionContext context) throws IOException 
	  {
		Connection db = null ;
		
		try
		{
			db = getConnection(context);
			
			PianoMonitoraggioList listaPiani = new PianoMonitoraggioList();
			listaPiani.buildList(db);
			//listaPiani.buildListConfiguratore(db);
			LookupList listaPiani1 = new LookupList(db,"lookup_piano_monitoraggio");
			context.getRequest().setAttribute("ListaPianiLookup", listaPiani1);
			context.getRequest().setAttribute("ListaPiani", listaPiani);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			 
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return "ViewOK" ;
	  }
	
	public String executeCommandSalvaConfigurazione(ActionContext context) throws IOException, ParserConfigurationException, SAXException 
	  {
		Connection db = null ;
		PreparedStatement pst = null ;
		Tree t = new Tree();
		int idPiano = Integer.parseInt(context.getParameter("idPiano"));
		try
		{
			
			
			context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));
			context.getRequest().setAttribute("idPiano", ""+idPiano);
			db = getConnection(context);
		
			String tabella = context.getParameter("tabellaMappingPiani");
			String  colonnatabella= context.getParameter("colonnatabellaMappingPiani");
			//String del = "delete from "+tabella+ " where id_piano = ? ";
			String del = "update "+tabella+ " set trashed_date = now(), modified = now(), modifiedby = ?, note = concat_ws(';', note, 'Cancellato in seguito a modifica associazione da Configura Piani') where id_piano = ? ";
			pst = db.prepareStatement(del);
			pst.setInt(1, getUserId(context));
			pst.setInt(2, idPiano);
			pst.execute(); 
			String insert = "INSERT INTO "+tabella+"(id_piano, "+colonnatabella+", enteredby, entered) VALUES (?, ?, ?, current_timestamp);";
			
			
			context.getRequest().setAttribute("Error", "ok");
			//LookupList listaPiani = new LookupList(db,"lookup_piano_monitoraggio");
			//context.getRequest().setAttribute("ListaPianiLookup", listaPiani);
			
			Enumeration<String> parametri = context.getRequest().getParameterNames();
			String oldparam = "" ;
			while (parametri.hasMoreElements())
			{
				String param = parametri.nextElement();
				if (param.startsWith("nodo_"))
				{
				if (!param.equalsIgnoreCase(oldparam))
				{
				//String value = 
				String[] value = context.getRequest().getParameterValues(param);
				pst = db.prepareStatement(insert);
				pst.setInt(1, idPiano);
				for (int i = 0 ; i<value.length; i++)
				{
				pst.setInt(2, new Integer(value[i]));
				pst.setInt(3, getUserId(context));
				pst.execute();
				}
				
				
				
				oldparam = param ;
				}
				
				
				}
				
			}
			context.getRequest().setAttribute("ConfSalvata", "ok");
			
			  
		}
		catch(Exception e)
		{
			context.getRequest().setAttribute("Error", "ko");
			
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		
		return t.executeCommandConfigPianiTree(context);
		
	  }
	
	
	

}
