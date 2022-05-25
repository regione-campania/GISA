/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.servlets;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.MiddleServlet;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.UserSession;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.UserOperation;
import org.aspcfs.modules.anagrafe_animali.actions.AnimaleAction;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationPropertiesStart;
import org.aspcfs.utils.DateUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import sun.misc.Perf.GetPerfAction;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;

public class GeneraXLSJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		

		JobDataMap jdMap = arg0.getJobDetail().getJobDataMap();
		ServletConfig config = (ServletConfig) jdMap.get("config");
		String path = "";
		
			
		System.out.println("[BDU] Inizio Procedura Automatica di Salvataggio SituazioneCanili ["+new Date()+"] - ");
		
		ConnectionPool ce = (ConnectionPool) config.getServletContext().getAttribute("ConnectionPool");
		SystemStatus systemStatus = null;
		
		if (ce!=null){
			Object o = ((Hashtable) config.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());		
		//if(o != null){
				
				path = getPath( config.getServletContext(), "estrazioni_canili");
		//	}
		}
		
		System.out.println(path);
		Stabilimento.generaXlsCaniInCanile(path, config.getServletContext());
		System.out.println("[BDU] Fine Procedura Automatica di Salvataggio SituazioneCanili ["+new Date()+"] - ");
	
		// TODO Auto-generated method stub
		
		
		

		
	}
	protected String getPath(ServletContext context, String moduleFolderName) {
		ApplicationPrefs prefs = (ApplicationPrefs) context.getAttribute(
		"applicationPrefs");
		java.util.Date date= new java.util.Date();
		 System.out.println(new Timestamp(date.getTime()));
		 
		return (prefs.get("FILELIBRARY") +
				(getDbName(context) == null ? "" : getDbName(context) + System.getProperty("file.separator")) +
				(moduleFolderName == null ? "" : moduleFolderName + System.getProperty("file.separator")) 
				// + getDatePath(
						//new Timestamp(date.getTime()))
						);
	}
	
	public static String getDbName(ServletContext context) {
		ConnectionPool ce = (ConnectionPool) context.getAttribute(
		"ConnectionPool");
		if (ce != null) {
			return ce.getDbName();
		} else {
			ConnectionPool cp = new ConnectionPool();
			try {
				cp = new ConnectionPool(ApplicationPropertiesStart.getProperty("MainPool"));
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return cp.getDbName();
		}
	}
	
	
	public static String getDatePath(java.sql.Timestamp fileDate) {
		return DateUtils.getDatePath(fileDate);
	}
}
