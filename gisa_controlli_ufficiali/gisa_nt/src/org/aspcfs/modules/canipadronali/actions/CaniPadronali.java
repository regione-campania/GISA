/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
//commento al 214
package org.aspcfs.modules.canipadronali.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.canipadronali.base.Cane;
import org.aspcfs.modules.canipadronali.base.CaneList;
import org.aspcfs.modules.canipadronali.base.OperazioniCaniDAO;
import org.aspcfs.modules.canipadronali.base.Proprietario;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.FileAesKeyException;
import org.aspcfs.utils.UrlUtil;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.darkhorseventures.framework.actions.ActionContext;

import crypto.nuova.gestione.ClientSCAAesServlet;


public class CaniPadronali extends CFSModule {
	
	
	  public String executeCommandSearchForm(ActionContext context) 
	  {
		
		String retPage = "SearchFormOK" ;
		addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		return retPage ;
		  
	  }
	  
	  public String executeCommandSearchFormCane(ActionContext context) 
	  {
		 addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		String retPage = "SearchFormCaneOK" ;
		context.getRequest().setAttribute("Search", "search");
		return retPage ;
		  
	  }
	  
	  
	  public String executeCommandSearch(ActionContext context) 
	  {
		
		String retPage = "ListOK" ;
		Connection db = null ;
		String mc = context.getRequest().getParameter("mc") ;
		boolean cani_canile =  false;
		if(context.getRequest().getParameter("cani_canile")!=null)
			cani_canile = true;
		String cf_prop = context.getRequest().getParameter("cf") ;
		String id_canina = context.getRequest().getParameter("id_canina") ;
		String id_gisa = context.getRequest().getParameter("id_gisa") ;
		
		if(id_canina!=null && id_canina.equals("null"))
			id_canina = null;
		if(id_gisa!=null && id_gisa.equals("null"))
			id_gisa = null;
		String reg = context.getParameter("searchregistrati");
		context.getRequest().setAttribute("mc", mc);
		context.getRequest().setAttribute("cf_prop", cf_prop);
		context.getRequest().setAttribute("id_canina", id_canina);
		context.getRequest().setAttribute("id_gisa", id_gisa);
		context.getRequest().setAttribute("searchregistrati", reg);
		context.getRequest().setAttribute("cani_canile", cani_canile);
		
		if(id_canina!=null || id_gisa!=null)
			retPage = "ListCaniCanileOK" ;
		CaneList lista_cani = null ;
		try
		{
			UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User")	;
			
			db = getConnection(context)	;
			LookupList lista_asl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", lista_asl);
			OperazioniCaniDAO operazioni_cani = new OperazioniCaniDAO()	;
			
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo")	;
			searchListInfo.setLink("CaniPadronali.do?command=Search&siteId="+user.getSiteId()+"&cani_canile="+cani_canile+((mc!=null)?("&mc="+mc):(""))+((cf_prop!=null)?("&cf="+cf_prop):(""))+((reg!=null)?("&searchregistrati="+reg):(""))+((id_canina!=null)?("&id_canina="+id_canina):(""))+((id_gisa!=null)?("&id_gisa="+id_gisa):("")))	;
			searchListInfo.setListView("all");

			lista_cani = operazioni_cani.searchCaneByMC(mc,cf_prop,reg, user.getSiteId(),searchListInfo, context, id_canina, id_gisa,cani_canile) ;
			
			context.getRequest().setAttribute("ListaCani", lista_cani)	;
			
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		
		
		return retPage ;
		  
	  }
	  
	  
	  public String executeCommandDetails(ActionContext context) 
	  {
		
		String retPage = "ListOK" ;
		Connection db = null ;
		
		CaneList lista_cani = null ;
		try
		{
			UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User")	;
			
			db = getConnection(context)	;
			LookupList lista_asl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", lista_asl);
			OperazioniCaniDAO operazioni_cani = new OperazioniCaniDAO()	;
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(Integer.parseInt(context.getParameter("orgId"))) ;
			
			context.getRequest().setAttribute("Proprietario", proprietario);
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		
		
		return "DetailsOK" ;
		  
	  }
	  
	 
	  
	  public String executeCommandAdd(ActionContext context) 
	  {
		
		String retPage = "AddOK" ;
		String url = ApplicationProperties.getProperty("InfoCaneLoginAction");
		String result = UrlUtil.getUrlResponse( url );
		context.getRequest().setAttribute("result", result);
		return retPage ;
		  
	  }
	  

	  public static String asHex(byte buf[]) {
	        StringBuffer sb = new StringBuffer(buf.length * 2);
	        for (int i = 0; i < buf.length; i++) {
	            if (((int) buf[i] & 0xff) < 0x10) {
	                sb.append("0");
	            }

	            sb.append(Long.toString((int) buf[i] & 0xff, 16));
	        }

	        return sb.toString();
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
		  public static byte[] encrypt(String text,URL url) throws IOException, NoSuchAlgorithmException,FileAesKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
			  
			  if(url ==null)
			  {
				  throw new FileAesKeyException("File aes_key not found");
			  }
			  SecretKeySpec spec = getKeySpec(url.getPath());
			  Cipher cipher = Cipher.getInstance("AES");
			  cipher.init(Cipher.ENCRYPT_MODE, spec);
			  sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
		    
		    return enc.encode(cipher.doFinal(text.getBytes())).getBytes() ;
		  }

		  
		 

	  public String executeCommandLoginCanina(ActionContext context) throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, FileAesKeyException, Exception 
	  {
		  UserBean user = (UserBean)context.getSession().getAttribute("User");
		  /**COSTRUZIONE DEL TOKEN**/
		
		  String originalToken = System.currentTimeMillis() + "@"+user.getUsername();
		  String encryptedToken = null ;
		  /*gestione vecchia
		  encryptedToken = NEWencrypt(originalToken,this.getClass().getResource("aes_key"));
		  */
		  /*gestione nuova */
		  ClientSCAAesServlet cclient = new ClientSCAAesServlet();
		  encryptedToken = cclient.crypt(originalToken); 
		  
		  
		  String inetHostBDU =InetAddress.getByName("srvBDUW").getHostAddress(); // InetAddress.getByName("endpointAPPBDU").getHostAddress();
		  context.getResponse().sendRedirect( "http://"+inetHostBDU+"/bdu/Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));
//			  context.getResponse().sendRedirect( "http://172.16.3.194:8080/CanAgr_Priv/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
		
		  return "-none-" ;
	  }
	  
	
	  
	  public String executeCommandInsert(ActionContext context) 
	  {
		
		String retPage = "" ;
		addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		return retPage ;
		  
	  }
	  
	  
	  
	  public String executeCommandViewVigilanza(ActionContext context) {
		  addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		  
		    return "ListaCUOK";
		  }


		public static  String NEWencrypt(String input, URL url){
			byte[] crypted = null;
			try{
				//SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
				SecretKeySpec skey = getKeySpec(url.getPath());
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, skey);
				crypted = cipher.doFinal(input.getBytes());
			}catch(Exception e){
				System.out.println(e.toString());
			}
			return new String(org.apache.commons.codec.binary.Base64.encodeBase64(crypted));
		}

}
