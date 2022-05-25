/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.aspcfs.modules.actions.CFSModule;

import com.darkhorseventures.framework.actions.ActionContext;


public class DestinatarioPecMailChecker extends CFSModule{
	public static final int ESITO_OK = 1;
	public static final int PROVIDER_INVALIDO = 2;
	public static final int FORMATO_INVALIDO = 3;
	
	public String executeCommandDefault(ActionContext cont)
	{
		String viewName = null;
		Connection db = null;
		String mailAddr = null;
		Integer esito;
		try
		{
			
			db = getConnection(cont);
			
			mailAddr = cont.getRequest().getParameter("mail_to_check");
			
			if(	!checkCorrettezzaFormatoIndirizzo(mailAddr) )
			{
				esito = FORMATO_INVALIDO;
			}
			else if( !checkCorrettezzaProvider(db, mailAddr) )
			{
				esito = PROVIDER_INVALIDO;
			}
			else
			{
				esito = ESITO_OK;
			}
				
			cont.getRequest().setAttribute("esito_validazione_pec", esito+"");
			
			viewName="suapJsonValidazionePec";
		}
		catch(Exception ex)
		{
			viewName="SystemError";
			ex.printStackTrace();
		}
		finally
		{
			freeConnection(cont, db);
		}
	
		return viewName;
	}
	
	
	public static boolean checkCorrettezzaFormatoIndirizzo(String mailAddr)
	{
		
		try 
		{
		      InternetAddress emailAddr = new InternetAddress(mailAddr);
		      emailAddr.validate();
		      return true;
		} 
		catch (AddressException ex) 
		{
		      return false;
		}
		
	}
	
	public static boolean checkCorrettezzaProvider(Connection db,String mailAddr) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		//ritorna true se il provider e  nel db come stringa precisa o in like delle sue componenti
		try
		{
			//estraggo dominio dopo la @
			String indirizzoCompletoVariDomini = mailAddr.substring(mailAddr.indexOf("@")+1);

//			pst = db.prepareStatement("select * from provider_pec_validi where dominio like '%"+ dominio+"%'");
			pst = db.prepareStatement("select * from provider_pec_validi where dominio = ?");
			pst.setString(1, indirizzoCompletoVariDomini);
			rs = pst.executeQuery();
			if(rs.next()) //l'ha trovato come nome preciso
			{
				return true;
			}
			
//			pst.close();
//			rs.close();
//			//altrimenti non l'ha trovato, quindi estraggo i domini dei vari livelli (ad esclusione dell'ultimo .com/.it etc)
//			String[] domini = indirizzoCompletoVariDomini.split("[\\.\\-\\_]");
//			for(int i=0;i<domini.length-1;i++) //l'ultimo dominio (il root) non lo considero
//			{
//				System.out.println("----------dominio:  "+domini[i]);
//				pst = db.prepareStatement("select * from provider_pec_validi where dominio like '%"+domini[i]+"%'");
//				rs = pst.executeQuery();
//				if(rs.next()) //trovato in like con uno dei sottodomini
//				{
//					return true;
//				}
//				pst.close();
//				rs.close();
//			}
			//se invece contiene almeno cert ritorniamo true
			if(indirizzoCompletoVariDomini.contains("cert"))
				return true;
			
			return false;
			
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) pst.close();
			if(rs != null) rs.close();
		}
	}
	
	public static boolean checkRicezioneDestinatario(String mailAddr)
	{
		return false;
	}
	
	
}
