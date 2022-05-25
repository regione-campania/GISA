/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.utils.ApplicationProperties;
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
    	String suff = "";
		if (config.getServletContext().getInitParameter("context_starting")!=null){
			suff=(String)config.getServletContext().getInitParameter("context_starting");
		}
    	
    	if (ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente")!=null &&
    			ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") ){
        	boolean flag = true;
			try {    
				for (int i=1;i<=3;i++){
					SchedulerFactory sf = new StdSchedulerFactory();
					Scheduler sche = sf.getScheduler();
					sche.start();
				
					String time_interval = (String)ApplicationProperties.getProperty("cleancache_time_interval_"+i); 
					JobDetail jDetail = new JobDetail("B_myjob"+suff+i,sche.DEFAULT_GROUP,JobImplement.class);
					jDetail.getJobDataMap().put("config", config);
					jDetail.getJobDataMap().put("type", String.valueOf(i));
		   			CronTrigger crTrigger = new CronTrigger("B_cronTrigger_"+suff+i, sche.DEFAULT_GROUP, time_interval);
					sche.scheduleJob(jDetail, crTrigger);
				}
				
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
			
				String time_interval = (String)ApplicationProperties.getProperty("cleancache_time_interval_1"); 
				JobDetail jDetail = new JobDetail("B_myjob4",sche.DEFAULT_GROUP,GeneraXLSJob.class);
				jDetail.getJobDataMap().put("config", config);
				jDetail.getJobDataMap().put("type", 4);
	   			CronTrigger crTrigger = new CronTrigger("B_cronTrigger_4", sche.DEFAULT_GROUP);
	   			crTrigger.setCronExpression("0 0 5 1 * ?"); //IL PRIMO GIORNO DI OGNI MESE ALLE 05:00
	   			//crTrigger.setCronExpression(time_interval); 
				sche.scheduleJob(jDetail, crTrigger);
			} catch (Exception e){
				flag = false;
				e.printStackTrace();
			} finally {
				if (flag==true)
					System.out.println("["+suff+"] AVVIATO SCHEDULER STORICO OPERAZIONI UTENTE");
				else
					System.out.println("["+suff+"] ERRORE DURANTE L'AVVIO DELLO SCHEDULER STORICO OPERAZIONI UTENTE");
			} 
    	}  else {
    		System.out.println("["+suff+"] STORICO OPERAZIONI UTENTE DISATTIVATO");
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

