/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.servlets;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.MiddleServlet;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.UserSession;
import org.aspcfs.modules.admin.base.UserOperation;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.darkhorseventures.database.ConnectionPool;

/**
*
* @author shreyansh.shah
*/
public class JobImplement implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jdMap = context.getJobDetail().getJobDataMap();
		ServletConfig config = (ServletConfig) jdMap.get("config");
		String type = (String)jdMap.get("type");
		switch (type) {
			case "1" : type = "(Orario Lavorativo)"; break;
			case "2" : type = "(Orario Notturno)"; break;
			case "3" : type = "(Festivo)"; break;
			default : type ="";
		} 
		
		String suff = "";
		if (config.getServletContext().getInitParameter("context_starting")!=null){
			suff=(String)config.getServletContext().getInitParameter("context_starting");
		}	
		System.out.println("["+suff+"] Inizio Procedura Automatica di LOG OPERAZIONI UTENTE ["+new Date()+"] - "+type);
		
		ConnectionPool ce = (ConnectionPool) config.getServletContext().getAttribute("ConnectionPool");
		SystemStatus systemStatus = null;
		
		if (ce!=null){
			Object o = ((Hashtable) config.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());		
			if(o != null){
				systemStatus = (SystemStatus) o;
				
				HashMap sessions = systemStatus.getSessionManager().getSessions();
				Iterator it = sessions.entrySet().iterator();
				if(sessions != null && sessions.size() > 0){	    	
					try{
						while (it.hasNext()){
							Entry entry = (Entry) it.next();
							UserSession s = (UserSession)entry.getValue();
							ArrayList<UserOperation> op = (ArrayList<UserOperation>)s.getSessionUser().getAttribute("operazioni");
							String query = (String)s.getSessionUser().getAttribute("AccessUpdate");
							s.getSessionUser().removeAttribute("operazioni");
							s.getSessionUser().removeAttribute("AccessUpdate");
							MiddleServlet.writeStorico(op, query, true,(String)config.getServletContext().getInitParameter("context_starting"));
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("["+suff+"] Fine Procedura Automatica di LOG OPERAZIONI UTENTE ["+new Date()+"] - "+type);
	}
}
