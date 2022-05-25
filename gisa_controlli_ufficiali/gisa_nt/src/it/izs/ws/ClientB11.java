/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.ws;


import it.izs.ws.Exception;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;


import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;























import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.InvioB11;
import org.aspcfs.utils.InvioBA;
import org.aspcfs.utils.RiepilogoImport;

import com.darkhorseventures.framework.actions.ActionContext;





public class ClientB11 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	
	static Logger logger = Logger.getLogger(ClientB11.class);
	private static String getData() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String rit = sdf.format( gc.getTime() );//.replace(":", "_");
		return ( rit );
	}
	
	public static Timestamp getTime ()
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		return time ;
	}
	


}

				