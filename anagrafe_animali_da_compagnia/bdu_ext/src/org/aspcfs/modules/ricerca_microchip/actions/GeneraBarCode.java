/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.ricerca_microchip.actions;



import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.*;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.actions.AnimaleAction;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.ricerca_microchip.base.BarcodeUtil;


import org.aspcfs.servlets.Download;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;



import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.pdf.Barcode128;
import com.zeroio.webutils.FileDownload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class GeneraBarCode extends CFSModule {
	
	public String executeCommandDefault(ActionContext context) {

		return executeCommandSearchForm(context);
	}
	
/*	protected static byte[] getSchedaIZSM(String templateDir, String m_c) {

		byte[] out = null;

		try {
			// recupero del path in cui sono contenuti i template pdf
			InputStream template = new FileInputStream( "C:/modulo_esami_cane_template.pdf" );

			Map<String, Object> formMap = new HashMap<String, Object>();
			
			if(m_c != null && !"".equals(m_c)){
				Barcode128 code128 = new Barcode128();
				code128.setCode(m_c);
				formMap.put( "barcode", code128 );
			}

			out = PdfReport.fillDocument(template, null, formMap);

		} catch (Exception errorMessage) {
			logger.severe("[CANINA] - GeneraBarcode - Errore nella generazione della scheda IZSM");
			errorMessage.printStackTrace();
		}
		return out;
	}*/
	
	public String connessione_DB(String m_c, String nomeFile, String fieldImgName, String tipo,ActionContext context) throws IOException, NamingException, SQLException
	{	
		boolean responso=false;
		String pathToReturn = "/WEB-INF/prova";
		String pathPdfFile = "C:/";
		String varSearch = "nonTrovato";

	
				
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;   
		ConnectionPool cp = null;

     
		ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

		

		
		
		String query1 = "select id from animale where trashed_date is null and ( microchip='"+m_c+"')";

		try
		{
		 
			cp = new ConnectionPool("java:comp/env/jdbc/bduM");
			con = cp.getConnection(context);
		    st = con.prepareStatement(query1);

		    rs = st.executeQuery();
		       
		    if(rs.next())
		    {
		     	responso=true;
				System.out.println("Trovato nel primo controllo");
		    }
		    else
		    {
		    	responso=false;
				String query2 = "select serial_number from mc_non_standard where serial_number='"+m_c+"'";
     			st = con.prepareStatement(query2);
			    rs = st.executeQuery();
			    
			    if(rs.next())
			    {
						responso = true;
						System.out.println("Trovato nel secondo controllo");
			    }
		    }
			if (responso==true)
			{
				if (tipo.equals("1"))
				{	
					
					try{
					BufferedImage img = BarcodeUtil.generateImage(m_c);
				
					
					String path="/"+m_c+".png";
					System.out.println(path);
					
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					ImageIO.write(img,"png", os); 
					
					
					HttpServletResponse res = context.getResponse();
					String mimeType = "image/png";
					if (mimeType != null) 
					{
						res.setHeader("Content-Type", mimeType);
						res.setContentType(mimeType);
					}

					FileDownload fileDownload = new FileDownload();
					fileDownload.setDisplayName(m_c + ".png");
					fileDownload.sendFile(context, os.toByteArray(), "application/octet-stream");
					
					os.close();
					
					}catch (Exception e) {
						// TODO: handle exception
					}
					
					
					//ImageIO.write(img, "png", new File(path));
					
				//	String red_url ="Download?nome="+path+"&titolo="+m_c+".png";

					//context.getResponse().sendRedirect(red_url);
					//return getReturn(context, "viewBarCode");
				}
				else
				{
					//String HOST_CORRENTE= ApplicationProperties.getProperty("APP_HOST_CANINA");
				/*	String SERVER_BDU  	=  InetAddress.getByName("hostAppBduPublic").getHostAddress();
					String APPLICATION_PORT = prefs.get("APPLICATION.PORT");
					String APPLICATION_NAME = prefs.get("APPLICATION.NAME");

					String url = "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT).concat("/").concat(APPLICATION_NAME).concat("/");
					
					context.getRequest().setAttribute("SERVER_BDU", url);
*/
					LookupList siteList = new LookupList(con, "lookup_asl_rif");
					siteList.addItem(-1, "-- SELEZIONA VOCE --");
					context.getRequest().setAttribute("aslList", siteList);
					
					Animale animale = new Animale(con, context.getRequest().getParameter("microchip"));
					
					context.getRequest().setAttribute("ricadentePianoLeishmania", animale.getRicadentePianoLeishmania(con, animale.getIdAnimale()));
									
					ComuniAnagrafica c = new ComuniAnagrafica();
					// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI ALL'ASL
					// UTENTE
					ArrayList<ComuniAnagrafica> listaComuni = c.buildList(con, -1, 1);
					LookupList comuniList = new LookupList(listaComuni, -1);
					comuniList.addItem(-1, "");
					context.getRequest().setAttribute("comuniList", comuniList);
					
					
					context.getRequest().setAttribute("animale", animale);
					context.getRequest().setAttribute("microchip", context.getRequest().getParameter("microchip"));
					System.out.println("Fine generazione barcode");
					return getReturn(context, "viewRichiestaCampioniEmpty");				
				}
			}
			else if(responso==false)
			{
			    context.getRequest().setAttribute("ErroreRicerca", varSearch);
			    context.getRequest().setAttribute("tipo", tipo);

				return "ricercaErrata";				
			}			
		} 
		catch (SQLException ex)
		{
		    System.out.println("Connessione Fallita");
			Logger lgr = Logger.getLogger(GeneraBarCode.class.getName());
		    lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}finally{
			
			//	con.close();
			
			if (con != null)
					cp.closeConnection(con, context, st, rs);
			
		}
	
		return "-none-";
	}
			
	public String executeCommandSearchForm(ActionContext context){


		logger.info("Generazione pdf per barcode in corso...");

		String m_c 			= 	context.getRequest().getParameter("microchip");
		String nomeFile 	= 	context.getRequest().getParameter("file_name");
		String fieldImgName =  	context.getRequest().getParameter("fieldImg");
 		String tipo         =  	context.getRequest().getParameter("tipo");

		try {
			if (m_c != null &&  !("").equals(m_c))
				return connessione_DB(m_c, nomeFile, fieldImgName,tipo, context);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "TuttoOK";
	}	
	
}