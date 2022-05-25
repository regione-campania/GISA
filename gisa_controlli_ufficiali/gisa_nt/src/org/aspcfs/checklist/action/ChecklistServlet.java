/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.checklist.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.checklist.base.Audit;
import org.aspcfs.checklist.base.Organization;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.FileAesKeyException;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.ParameterUtils;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class ChecklistServlet
 */
public class ChecklistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChecklistServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	 public static byte[] lenientHexToBytes(String hex) {
	      byte[] result = null;
	      if (hex != null) {
	          // remove all non alphanumeric chars like colons, whitespace, slashes
	          //hex = hex.replaceAll("[^a-zA-Z0-9]", "");
	          // from http://forums.sun.com/thread.jspa?threadID=546486
	          // (using BigInteger to convert to byte array messes up by adding extra 0 if first byte > 7F and this method
	          //  will not rid of leading zeroes like the flawed method 
	          
	          //byte[] bts = new BigInteger(hex, 16).toByteArray();)
	          result = new byte[hex.length() / 2];
	          for (int i = 0; i < result.length; i++) {
	              result[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	          }
	      }
	   
	      return result;
	  }
	  
	  public static SecretKeySpec getKeySpec(String path) throws IOException, NoSuchAlgorithmException,FileAesKeyException {
		    byte[] bytes = new byte[16];
		    File f = new File(path.replaceAll("%20", " "));
		   
		    SecretKeySpec spec = null;
		    if (f.exists()) 
		    {
		      new FileInputStream(f).read(bytes);
		      
		    } else {
		      /* KeyGenerator kgen = KeyGenerator.getInstance("AES");
		       kgen.init(128);
		       key = kgen.generateKey();
		       bytes = key.getEncoded();
		       new FileOutputStream(f).write(bytes);*/
		    	throw new FileAesKeyException("File aes_key not found");
		    	
		    }
		    spec = new SecretKeySpec(bytes,"AES");
		    return spec;
		  }
	  
	  
	  public static String decrypt(String text,URL url) throws Exception {
		  if(url == null)
			  throw new FileAesKeyException("File aes_key not found");

		  SecretKeySpec spec = getKeySpec(url.getPath());
		  Cipher cipher = Cipher.getInstance("AES");
		  cipher.init(Cipher.DECRYPT_MODE, spec);
		  sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		return (new String(cipher.doFinal(dec.decodeBuffer(text))));
	  }
	  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		

		
		UserBean user = (UserBean) request.getSession().getAttribute("User");
		
		 String decrypteToken = "" ;
		    String encrypteToken = request.getParameter("encryptedToken");
		    String[] params = null ;
		    long loginTime =0;
			  try {
				  
				  decrypteToken = decrypt(new String(lenientHexToBytes(encrypteToken)), this.getClass().getResource("aes_key"));
				  params = decrypteToken.split("@");
				  loginTime = Long.parseLong(params[0]);
				  
			  } catch (NoSuchAlgorithmException e) {
				  // TODO Auto-generated catch block
				  request.setAttribute("dettagli_problema", "Si e' verificato un problema nella decriptazione del token ");
				  e.printStackTrace();
				 
			  } catch (BadPaddingException e) {
				  // TODO Auto-generated catch block
				  request.setAttribute("dettagli_problema", "Si e' verificato un problema nella decriptazione del token ");
				  e.printStackTrace();
				 
			  } catch (InvalidKeyException e) {
				  // TODO Auto-generated catch block
				  request.setAttribute("dettagli_problema", "Si e' verificato un problema nella decriptazione del token ");
				  e.printStackTrace();
				 
			  } catch (NoSuchPaddingException e) {
				  // TODO Auto-generated catch block
				  request.setAttribute("dettagli_problema", "Si e' verificato un problema nella decriptazione del token ");
				  e.printStackTrace();
				  
			  } catch(Exception e){
				  request.setAttribute("dettagli_problema", "Si e' verificato un problema nella decriptazione del token ");
				  e.printStackTrace();
				  
			  }
		     
		    
			// long loginTime = Long.parseLong(params[0]);
			 long currTime = System.currentTimeMillis();
			
			
			 if(params[1].equalsIgnoreCase("izsmchecklist"))
			 {
				 
			 
		
		boolean 			isValid				= 	false	;
		Connection 			db 					= 	null	;
		int 				resultCount 		= 	-1		;
		int 				resultCountCk 		= 	-1		;
		int 				resultCountCkTp 	= 	-1		;
		Ticket newTic=null; 
		String 			punti 		= request.getParameter("punteggioUltimiAnni")	;
		int 			punteggio 	= 0		;
		Organization 	organ 		= null	;
		Audit 			thisAudit 		= null ;
		ArrayList<Parameter> risposte 			= ParameterUtils.list(request, "risposta")		;
		ArrayList<Parameter> valoreRange 		= ParameterUtils.list(request, "valoreRange")	;
		ArrayList<Parameter> operazione			= ParameterUtils.list(request, "operazione")	;
		ArrayList<Parameter> nota			 	= ParameterUtils.list(request, "nota")			;
		ArrayList<Parameter> paragrafiabilitati = ParameterUtils.list(request, "disabilita")	;
		String stato = request.getParameter("stato");
		response.setContentType("text/html");
		String idC = request.getParameter("idC")	;
		try {
			db = GestoreConnessioni.getConnection();
			thisAudit 		= new Audit(db,request.getParameter("id"))	;
			thisAudit.setIdLastDomanda(request.getParameter("idLastDomanda"))						;
			thisAudit.setLivelloRischio(Integer.parseInt(request.getParameter("livelloRischio")));
			newTic = new Ticket();

			newTic.setId(Integer.parseInt(idC));
			newTic.queryRecord(db, Integer.parseInt(idC));
			thisAudit.setOrgId(Integer.parseInt(request.getParameter("orgId")));


			if ((punti != null)&&(!punti.equals("")))
			{
				punteggio = Integer.parseInt(punti);}
			else 
			{
				punteggio = 0;
			}

			thisAudit.setPunteggioUltimiAnni(punteggio);

			thisAudit.setStato(stato);
			resultCount = thisAudit.update(db, risposte, valoreRange, operazione, nota, punteggio,paragrafiabilitati);
			response.setStatus(200);
			response.getWriter().print("OK");

		} catch (SQLException errorMessage) 
		{
			response.setStatus(2012); // set il codice di risposta http per l'applet in caso di eccezione
			response.getWriter().print("KO");
			if (stato.equalsIgnoreCase("temporanea"))
			{
				request.setAttribute("Error", errorMessage);
			}

			try {
				throw errorMessage ;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		finally 
		{
			GestoreConnessioni.freeConnection(db);
		}
			 }
			 else
			 {
				 response.setStatus(2013);
			 }





	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
