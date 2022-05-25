/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspcfs.modules.oia.base.OiaNodo;
import com.darkhorseventures.database.ConnectionPool;

public class ServletStruttureAsl extends HttpServlet {


	public ServletStruttureAsl() {
		super();
		// TODO Auto-generated constructor stub
	}
 

	Logger logger= Logger.getLogger(ServletStruttureAsl.class);
	@Override
	public void init(ServletConfig config) throws ServletException {
		Connection db = null;
		ConnectionPool cp = null ;
		
		
		   
		
		try
		{

			

			cp = (ConnectionPool)config.getServletContext().getAttribute("ConnectionPool");
			db = cp.getConnection(null,null);	

			OiaNodo  nodoTemp = new OiaNodo();

			
			
			
			HashMap<Integer,ArrayList<OiaNodo>> strutture_asl = new HashMap<Integer, ArrayList<OiaNodo>>();
			

			
			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno_corrente = calCorrente.get(Calendar.YEAR);
			 logger.info("Caricamento Strutture Per Conto Di In memoria Anno "+anno_corrente); 
			 
			 if (config.getServletContext().getAttribute("StruttureOIA")== null){
				 logger.info("Caricamento Strutture Per Conto Di (8) In memoria Anno "+anno_corrente); 
				 strutture_asl.put(8, nodoTemp.loadbyRegione(db));
			 }
			 
			logger.info("Caricamento Strutture Per Conto Di (201) In memoria Anno "+anno_corrente); 
			strutture_asl.put(201, nodoTemp.loadbyidAsl("201",anno_corrente, db));
			
			logger.info("Caricamento Strutture Per Conto Di (202) In memoria Anno "+anno_corrente); 
   			strutture_asl.put(202, nodoTemp.loadbyidAsl("202",anno_corrente, db));
   			
			logger.info("Caricamento Strutture Per Conto Di (203) In memoria Anno "+anno_corrente); 
			strutture_asl.put(203, nodoTemp.loadbyidAsl("203",anno_corrente, db));
			
			logger.info("Caricamento Strutture Per Conto Di (204) In memoria Anno "+anno_corrente); 
			strutture_asl.put(204, nodoTemp.loadbyidAsl("204",anno_corrente, db));
			
			logger.info("Caricamento Strutture Per Conto Di (205) In memoria Anno "+anno_corrente); 
			strutture_asl.put(205, nodoTemp.loadbyidAsl("205",anno_corrente, db));
			
			logger.info("Caricamento Strutture Per Conto Di (206) In memoria Anno "+anno_corrente); 
			strutture_asl.put(206, nodoTemp.loadbyidAsl("206",anno_corrente, db));
			
			logger.info("Caricamento Strutture Per Conto Di (207) In memoria Anno "+anno_corrente); 
			strutture_asl.put(207, nodoTemp.loadbyidAsl("207",anno_corrente, db));

			 config.getServletContext().setAttribute("StruttureOIA",strutture_asl);
			
			
			
			//Verifica se il db collegato e' MASTER O SLAVE
			String mode = "";
			PreparedStatement pst = db.prepareStatement("show transaction_read_only");
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				mode = rs.getString(1);
			}
			if (mode.equals("on")){
				System.out.println("DB SLAVE");
				config.getServletContext().setAttribute("ambiente","SLAVE");
			}
			else{
				System.out.println("DB MASTER");
				config.getServletContext().setAttribute("ambiente","MASTER");
			}
			pst.close();
			rs.close();
			 
			
			
			BufferedReader br = new BufferedReader(new FileReader(new File(config.getServletContext().getRealPath("templates")+File.separator+ "avviso_messaggio_urgente.txt")));
			String mes = "" ;
			mes = br.readLine();
			while (mes != null && ! "".equals(mes))
			{
				mes +=mes ;
				mes = br.readLine();
			}
			br.close();
			
			
			Timestamp dataUltimaModifica = new Timestamp(System.currentTimeMillis());
			config.getServletContext().setAttribute("MessaggioHome", dataUltimaModifica+"&&"+mes);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			cp.free(db,null);
		
	}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		

	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
