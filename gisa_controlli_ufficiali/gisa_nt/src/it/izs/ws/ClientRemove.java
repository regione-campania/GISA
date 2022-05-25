/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.ws;


import it.izs.bdn.bean.InfoAllevamentoBean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ServiceException;

import org.aspcfs.utils.AjaxCalls;

//import com.itextpdf.text.log.SysoLogger;


public class ClientRemove {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	private static String getData() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH:mm:ss");
		String rit = sdf.format( gc.getTime() ).replace(":", "_");
		return ( rit );
	}
	
	public static Timestamp getTime ()
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		return time ;
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException, ServiceException  {
 
		
	
			
		ControlliallevamentiService service = new ControlliallevamentiService();
		Controlliallevamenti client = service.getControlliallevamentiPort();
		//AUTENTICAZIONE
		AutenticazioneTO autenticazioneTo = new AutenticazioneTO();
		autenticazioneTo.setUsername("izsna_006");
		autenticazioneTo.setPassword("na.izs34");
		

		//AUTORIZZAZIONE
		AutorizzazioneTO autorizzazioneTo = new AutorizzazioneTO();
		autorizzazioneTo.setRuolo("regione");
		autorizzazioneTo.setGrspeCodice("");
		autorizzazioneTo.setCodiceLingua("IT");

			
				ControlliallevamentiWS con = new ControlliallevamentiWS();
				con.setCaId(65458);
				DeleteControlliallevamentiBA delBa = new DeleteControlliallevamentiBA();
				delBa.setControlliallevamenti(con);
				
				DeleteControlliallevamentiBAResponse response = null;
				
					try {
						response = client.deleteControlliallevamentiBA(delBa, autenticazioneTo, autorizzazioneTo);
					} catch (BusinessException_Exception | Exception_Exception
							| IzsWsException_Exception
							| SOAPException_Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				

				if (response!=null) {
					
					
					
				}
						
						
					}
					
			
		
	
	
	


}

				