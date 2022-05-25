/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Servlet implementation class CronScheduler
 */
public class CronSchedulerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**    
     * @see HttpServlet#HttpServlet()
     */
    public CronSchedulerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
    	
    	//Tracing XML request/responses with JAX-WS
    			System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
    			System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
    			System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
    			System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    			
    			
    	String suff = "";
		if (config.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI")!=null){
			suff=(String)config.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
		}
		
    	if( ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente") != null 
    			&& ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") ){
    		boolean flag = true;
    		
			try {
				for (int i=1;i<=3;i++){
					SchedulerFactory sf = new StdSchedulerFactory();
					Scheduler sche = sf.getScheduler();
					sche.start();
					
					String time_interval = (String)ApplicationProperties.getProperty("cleancache_time_interval_"+i); 
					JobDetail jDetail = new JobDetail("G_myjob"+suff+i,sche.DEFAULT_GROUP,JobImplement.class);
					jDetail.getJobDataMap().put("config", config);
					jDetail.getJobDataMap().put("type", String.valueOf(i));

		   			CronTrigger crTrigger = new CronTrigger("G_cronTrigger_"+suff+i, sche.DEFAULT_GROUP, time_interval);
					sche.scheduleJob(jDetail, crTrigger);
				}
			} catch (Exception e){
				flag = false;
				e.printStackTrace();
			} finally {
				if (flag==true)
					System.out.println("[GISA"+suff+"] AVVIATO SCHEDULER STORICO OPERAZIONI UTENTE");
				else
					System.out.println("[GISA"+suff+"] ERRORE DURANTE L'AVVIO DELLO SCHEDULER STORICO OPERAZIONI UTENTE");
			}
    	} else {
    		System.out.println("[GISA"+suff+"] STORICO OPERAZIONI UTENTE DISATTIVATO");
    	}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
