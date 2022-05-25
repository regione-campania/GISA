/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfiguratoreHosts  extends HttpServlet {


	public ConfiguratoreHosts() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void init(ServletConfig config) {
		HashMap<String, InetAddress> hosts = new HashMap<String, InetAddress>();
				
		try { hosts.put("srvGISAL", InetAddress.getByName("srvGISAL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvGISAW", InetAddress.getByName("srvGISAW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbGISAL", InetAddress.getByName("dbGISAL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbGISAW", InetAddress.getByName("dbGISAW"));	} catch (UnknownHostException e) { ; }  

		try { hosts.put("srvGISA_EXTL", InetAddress.getByName("srvGISA_EXTL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvGISA_EXTW", InetAddress.getByName("srvGISA_EXTW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbGISA_EXTL", InetAddress.getByName("dbGISA_EXTL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbGISA_EXTW", InetAddress.getByName("dbGISA_EXTW"));	} catch (UnknownHostException e) { ; }  
		
		try { hosts.put("srvVAML", InetAddress.getByName("srvVAML"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvVAMW", InetAddress.getByName("srvVAMW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbVAML", InetAddress.getByName("dbVAML"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbVAMW", InetAddress.getByName("dbVAMW"));	} catch (UnknownHostException e) { ; }  

		try { hosts.put("srvBDUL", InetAddress.getByName("srvBDUL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvBDUW", InetAddress.getByName("srvBDUW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbBDUL", InetAddress.getByName("dbBDUL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbBDUW", InetAddress.getByName("dbBDUW"));	} catch (UnknownHostException e) { ; }  

		try { hosts.put("srvBDU_EXTL", InetAddress.getByName("srvBDU_EXTL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvBDU_EXTW", InetAddress.getByName("srvBDU_EXTW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbBDU_EXTL", InetAddress.getByName("dbBDU_EXTL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbBDU_EXTW", InetAddress.getByName("dbBDU_EXTW"));	} catch (UnknownHostException e) { ; }  
		
		try { hosts.put("srvGUCL", InetAddress.getByName("srvGUCL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvGUCW", InetAddress.getByName("srvGUCW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbGUCL", InetAddress.getByName("dbGUCL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbGUCW", InetAddress.getByName("dbGUCW"));	} catch (UnknownHostException e) { ; }  

		try { hosts.put("srvSCAL", InetAddress.getByName("srvSCAL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvSCAW", InetAddress.getByName("srvSCAW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbSCAL", InetAddress.getByName("dbSCAL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbSCAW", InetAddress.getByName("dbSCAW"));	} catch (UnknownHostException e) { ; }
		
		try { hosts.put("srvDOCUMENTALEL", InetAddress.getByName("srvDOCUMENTALEL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvDOCUMENTALEW", InetAddress.getByName("srvDOCUMENTALEW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbDOCUMENTALEL", InetAddress.getByName("dbDOCUMENTALEL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbDOCUMENTALEW", InetAddress.getByName("dbDOCUMENTALEW"));	} catch (UnknownHostException e) { ; }

		try { hosts.put("srvDIGEMONL", InetAddress.getByName("srvDIGEMONL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvDIGEMONW", InetAddress.getByName("srvDIGEMONW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbDIGEMONL", InetAddress.getByName("dbDIGEMONL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbDIGEMONW", InetAddress.getByName("dbDIGEMONW"));	} catch (UnknownHostException e) { ; }
		
		try { hosts.put("srvIMPORTATORIL", InetAddress.getByName("srvIMPORTATORIL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("srvIMPORTATORIW", InetAddress.getByName("srvIMPORTATORIW"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbIMPORTATORIL", InetAddress.getByName("dbIMPORTATORIL"));	} catch (UnknownHostException e) { ; }  
		try { hosts.put("dbIMPORTATORIW", InetAddress.getByName("dbIMPORTATORIW"));	} catch (UnknownHostException e) { ; }

		try { hosts.put("srvVAF", InetAddress.getByName("srvVAF"));	} catch (UnknownHostException e) { ; }

  	    config.getServletContext().setAttribute("hosts",hosts);
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
