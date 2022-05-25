/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.BUtente;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.util.properties.Application;





import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;




import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RegistroTumoriRemoteUtil {
	
	
	private static GsonBuilder gb;
	private static Gson gson;
	
	final static Logger logger = LoggerFactory.getLogger(RegistroTumoriRemoteUtil.class);
	
	static
	{
		gb = new GsonBuilder();
		gb.setDateFormat( "yyyy-MM-dd HH:mm:ss" );
		gson = gb.create();
	}
	
	
	
	
	public static void aggiuntiEsitoTumorale( String identificativoIstopatologico, BUtente utente ) throws Exception
	{
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
		
		
		//Connection connection = null;
		Connection vamConnection = null;
		Statement st = null;
		ResultSet rs = null;
		
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "RegistroTumoriUtil.aggiuntiEsitoTumorale" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", identificativo istopatologico: " + identificativoIstopatologico);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
		
//		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
//		{
//			//Context ctx = new InitialContext();
//			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
//			//connection = ds.getConnection();
//		}
//		else
//		{
//			persistence = PersistenceFactory.getPersistence();
//		}
		
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
		//	DataCaching data = new DataCaching(connection);
			
			memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			 dataEnd  	= new Date();
			 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
			System.out.println("Modulo in esecuzione: " + "RegistroTumoriUtil.aggiuntiEsitoTumorale" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
					   "Memoria usata: " + (memFreeStart - memFreeEnd) +
					   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
					   ",identificativo istopatologico: " + identificativoIstopatologico);
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			
			
			ArrayList parameters = new ArrayList();
			//parameters.add(identificativo);
			try {
				
				Context ctx = new InitialContext();
				javax.sql.DataSource dsVam = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
				vamConnection = dsVam.getConnection();
				vamConnection.setAutoCommit(false);
			
					st = vamConnection.createStatement();
					rs = st.executeQuery("select * from public_functions.inserisciesitotumorale('"+identificativoIstopatologico+"')");
					vamConnection.commit();
					
					
//					persistence.createSQLQuery("select * from public_functions.inserisciesitotumorale('"+identificativoIstopatologico+"')").SetParameter("bucketId", buckedId).ExecuteUpdate())
//					 persistence.createSQLQuery("select * from public_functions.inserisciesitotumorale('"+identificativoIstopatologico+"')").executeUpdate();
//					 persistence.commit();

				//Statement st1 = con.createStatement();
				
//				 persistence.createSQLQuery("select * from public_functions.inserisciesitotumorale('"+identificativoIstopatologico+"')");
//				 persistence.commit();
				
				//QueryResult qr = data.execute("select * from public_functions.inserisciesitotumorale('"+identificativoIstopatologico+"')", parameters);
			//	st1.executeQuery("select * from public_functions.inserisciesitotumorale('"+identificativoIstopatologico+"'::text)");
//				Query q = persistence.createQuery("select * from public_functions.inserisciesitotumorale('"+identificativoIstopatologico+"')");
//				q.executeUpdate();
				
				
				
				
				/*while (it.hasNext()){
					System.out.println("Record: "+it.next());
				}*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			st.close();
			rs.close();
			vamConnection.close();
		}
		else
		{
			//con.close();
		}
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "RegistroTumoriUtil.aggiuntiEsitoTumorale" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())) +
				   ", identificativo istopatologico: " + identificativoIstopatologico);
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		
		
		}
	}
		
	}
