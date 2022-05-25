/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Configuratore  {
	
	/*
	
	public static InetAddress srvGISAL;  
	public static InetAddress srvGISAW;  
	public static InetAddress dbGISAL;  
	public static InetAddress dbGISAW;  

	public static InetAddress srvGISA_EXTL;  
	public static InetAddress srvGISA_EXTW;  
	public static InetAddress dbGISA_EXTL;  
	public static InetAddress dbGISA_EXTW;  
	
	public static InetAddress srvVAML;  
	public static InetAddress srvVAMW;  
	public static InetAddress dbVAML;  
	public static InetAddress dbVAMW;  

	public static InetAddress srvBDUL;  
	public static InetAddress srvBDUW;  
	public static InetAddress dbBDUL;  
	public static InetAddress dbBDUW;  

	public static InetAddress srvBDU_EXTL;  
	public static InetAddress srvBDU_EXTW;  
	public static InetAddress dbBDU_EXTL;  
	public static InetAddress dbBDU_EXTW;  

	
	public static InetAddress srvGUCL;  
	public static InetAddress srvGUCW;  
	public static InetAddress dbGUCL;  
	public static InetAddress dbGUCW;  

	public static InetAddress srvSCAL;  
	public static InetAddress srvSCAW;  
	public static InetAddress dbSCAL;  
	public static InetAddress dbSCAW;
	
	public static InetAddress srvDOCUMENTALEL;  
	public static InetAddress srvDOCUMENTALEW;  
	public static InetAddress dbDOCUMENTALEL;  
	public static InetAddress dbDOCUMENTALEW;

	public static InetAddress srvDIGEMONL;  
	public static InetAddress srvDIGEMONW;  
	public static InetAddress dbDIGEMONL;  
	public static InetAddress dbDIGEMONW;
	
	public static InetAddress srvIMPORTATORIL;  
	public static InetAddress srvIMPORTATORIW;  
	public static InetAddress dbIMPORTATORIL;  
	public static InetAddress dbIMPORTATORIW;
	

	private static Configuratore instance = null;
	   protected Configuratore() throws UnknownHostException {
			srvGISAL=InetAddress.getByName("srvGISAL");  
			srvGISAW=InetAddress.getByName("srvGISAW");  
			dbGISAL=InetAddress.getByName("dbGISAL");  
			dbGISAW=InetAddress.getByName("dbGISAW");  

			srvGISA_EXTL=InetAddress.getByName("srvGISA_EXTL");  
			srvGISA_EXTW=InetAddress.getByName("srvGISA_EXTW");  
			dbGISA_EXTL=InetAddress.getByName("dbGISA_EXTL");  
			dbGISA_EXTW=InetAddress.getByName("dbGISA_EXTW");  
			
			srvVAML=InetAddress.getByName("srvVAML");  
			srvVAMW=InetAddress.getByName("srvVAMW");  
			dbVAML=InetAddress.getByName("dbVAML");  
			dbVAMW=InetAddress.getByName("dbVAMW");  

			srvBDUL=InetAddress.getByName("srvBDUL");  
			srvBDUW=InetAddress.getByName("srvBDUW");  
			dbBDUL=InetAddress.getByName("dbBDUL");  
			dbBDUW=InetAddress.getByName("dbBDUW");  

			srvBDU_EXTL=InetAddress.getByName("srvBDU_EXTL");  
			srvBDU_EXTW=InetAddress.getByName("srvBDU_EXTW");  
			dbBDU_EXTL=InetAddress.getByName("dbBDU_EXTL");  
			dbBDU_EXTW=InetAddress.getByName("dbBDU_EXTW");  

			
			srvGUCL=InetAddress.getByName("srvGUCL");  
			srvGUCW=InetAddress.getByName("srvGUCW");  
			dbGUCL=InetAddress.getByName("dbGUCL");  
			dbGUCW=InetAddress.getByName("dbGUCW");  

			srvSCAL=InetAddress.getByName("srvSCAL");  
			srvSCAW=InetAddress.getByName("srvSCAW");  
			dbSCAL=InetAddress.getByName("dbSCAL");  
			dbSCAW=InetAddress.getByName("dbSCAW");
			
			srvDOCUMENTALEL=InetAddress.getByName("srvDOCUMENTALEL");  
			srvDOCUMENTALEW=InetAddress.getByName("srvDOCUMENTALEW");  
			dbDOCUMENTALEL=InetAddress.getByName("dbDOCUMENTALEL");  
			dbDOCUMENTALEW=InetAddress.getByName("dbDOCUMENTALEW");

			srvDIGEMONL=InetAddress.getByName("srvDIGEMONL");  
			srvDIGEMONW=InetAddress.getByName("srvDIGEMONW");  
			dbDIGEMONL=InetAddress.getByName("dbDIGEMONL");  
			dbDIGEMONW=InetAddress.getByName("dbDIGEMONW");
			
			srvIMPORTATORIL=InetAddress.getByName("srvIMPORTATORIL");  
			srvIMPORTATORIW=InetAddress.getByName("srvIMPORTATORIW");  
			dbIMPORTATORIL=InetAddress.getByName("dbIMPORTATORIL");  
			dbIMPORTATORIW=InetAddress.getByName("dbIMPORTATORIW");

		   
		   
	      // Exists only to defeat instantiation.
	   }
	   public static Configuratore getInstance() throws UnknownHostException {
	      if(instance == null) {
	         instance = new Configuratore();
	      }
	      return instance;
	   }
	public static InetAddress getSrvGISAL() {
		return srvGISAL;
	}
	public static InetAddress getSrvGISAW() {
		return srvGISAW;
	}
	public static InetAddress getDbGISAL() {
		return dbGISAL;
	}
	public static InetAddress getDbGISAW() {
		return dbGISAW;
	}
	public static InetAddress getSrvGISA_EXTL() {
		return srvGISA_EXTL;
	}
	public static InetAddress getSrvGISA_EXTW() {
		return srvGISA_EXTW;
	}
	public static InetAddress getDbGISA_EXTL() {
		return dbGISA_EXTL;
	}
	public static InetAddress getDbGISA_EXTW() {
		return dbGISA_EXTW;
	}
	public static InetAddress getSrvVAML() {
		return srvVAML;
	}
	public static InetAddress getSrvVAMW() {
		return srvVAMW;
	}
	public static InetAddress getDbVAML() {
		return dbVAML;
	}
	public static InetAddress getDbVAMW() {
		return dbVAMW;
	}
	public static InetAddress getSrvBDUL() {
		return srvBDUL;
	}
	public static InetAddress getSrvBDUW() {
		return srvBDUW;
	}
	public static InetAddress getDbBDUL() {
		return dbBDUL;
	}
	public static InetAddress getDbBDUW() {
		return dbBDUW;
	}
	public static InetAddress getSrvBDU_EXTL() {
		return srvBDU_EXTL;
	}
	public static InetAddress getSrvBDU_EXTW() {
		return srvBDU_EXTW;
	}
	public static InetAddress getDbBDU_EXTL() {
		return dbBDU_EXTL;
	}
	public static InetAddress getDbBDU_EXTW() {
		return dbBDU_EXTW;
	}
	public static InetAddress getSrvGUCL() {
		return srvGUCL;
	}
	public static InetAddress getSrvGUCW() {
		return srvGUCW;
	}
	public static InetAddress getDbGUCL() {
		return dbGUCL;
	}
	public static InetAddress getDbGUCW() {
		return dbGUCW;
	}
	public static InetAddress getSrvSCAL() {
		return srvSCAL;
	}
	public static InetAddress getSrvSCAW() {
		return srvSCAW;
	}
	public static InetAddress getDbSCAL() {
		return dbSCAL;
	}
	public static InetAddress getDbSCAW() {
		return dbSCAW;
	}
	public static InetAddress getSrvDOCUMENTALEL() {
		return srvDOCUMENTALEL;
	}
	public static InetAddress getSrvDOCUMENTALEW() {
		return srvDOCUMENTALEW;
	}
	public static InetAddress getDbDOCUMENTALEL() {
		return dbDOCUMENTALEL;
	}
	public static InetAddress getDbDOCUMENTALEW() {
		return dbDOCUMENTALEW;
	}
	public static InetAddress getSrvDIGEMONL() {
		return srvDIGEMONL;
	}
	public static InetAddress getSrvDIGEMONW() {
		return srvDIGEMONW;
	}
	public static InetAddress getDbDIGEMONL() {
		return dbDIGEMONL;
	}
	public static InetAddress getDbDIGEMONW() {
		return dbDIGEMONW;
	}
	public static InetAddress getSrvIMPORTATORIL() {
		return srvIMPORTATORIL;
	}
	public static InetAddress getSrvIMPORTATORIW() {
		return srvIMPORTATORIW;
	}
	public static InetAddress getDbIMPORTATORIL() {
		return dbIMPORTATORIL;
	}
	public static InetAddress getDbIMPORTATORIW() {
		return dbIMPORTATORIW;
	}
	*/

	public  static InetAddress getSrvGISAL() throws UnknownHostException {
		return InetAddress.getByName("srvGISAL");
	}
	public  static InetAddress getSrvGISAW()  throws UnknownHostException {
		return InetAddress.getByName("srvGISAW");
	}
	public  static InetAddress getDbGISAL()  throws UnknownHostException {
		return InetAddress.getByName("dbGISAL");
	}
	public  static InetAddress getDbGISAW()  throws UnknownHostException {
		return InetAddress.getByName("dbGISAW");
	}
	public  static InetAddress getSrvGISA_EXTL()  throws UnknownHostException {
		return InetAddress.getByName("srvGISA_EXTL");
	}
	public  static InetAddress getSrvGISA_EXTW()  throws UnknownHostException {
		return InetAddress.getByName("srvGISA_EXTW");
	}
	public  static InetAddress getDbGISA_EXTL()  throws UnknownHostException {
		return InetAddress.getByName("dbGISA_EXTL");
	}
	public  static InetAddress getDbGISA_EXTW()  throws UnknownHostException {
		return InetAddress.getByName("dbGISA_EXTW");
	}
	public  static InetAddress getSrvVAML()  throws UnknownHostException {
		return InetAddress.getByName("srvVAML");
	}
	public  static InetAddress getSrvVAMW()  throws UnknownHostException {
		return InetAddress.getByName("srvVAMW");
	}
	public  static InetAddress getDbVAML()  throws UnknownHostException {
		return InetAddress.getByName("dbVAML");
	}
	public  static InetAddress getDbVAMW()  throws UnknownHostException {
		return InetAddress.getByName("dbVAMW");
	}
	public  static InetAddress getSrvBDUL()  throws UnknownHostException {
		return InetAddress.getByName("srvBDUL");
	}
	public  static InetAddress getSrvBDUW()  throws UnknownHostException {
		return InetAddress.getByName("srvBDUW");
	}
	public  static InetAddress getDbBDUL()  throws UnknownHostException {
		return InetAddress.getByName("dbBDUL");
	}
	public  static InetAddress getDbBDUW()  throws UnknownHostException {
		return InetAddress.getByName("dbBDUW");
	}
	public  static InetAddress getSrvBDU_EXTL()  throws UnknownHostException {
		return InetAddress.getByName("srvBDU_EXTL");
	}
	public  static InetAddress getSrvBDU_EXTW()  throws UnknownHostException {
		return InetAddress.getByName("srvBDU_EXTW");
	}
	public  static InetAddress getDbBDU_EXTL()  throws UnknownHostException {
		return InetAddress.getByName("dbBDU_EXTL");
	}
	public  static InetAddress getDbBDU_EXTW()  throws UnknownHostException {
		return InetAddress.getByName("dbBDU_EXTW");
	}
	public  static InetAddress getSrvGUCL()  throws UnknownHostException {
		return InetAddress.getByName("srvGUCL");
	}
	public  static InetAddress getSrvGUCW()  throws UnknownHostException {
		return InetAddress.getByName("srvGUCW");
	}
	public  static InetAddress getDbGUCL()  throws UnknownHostException {
		return InetAddress.getByName("dbGUCL");
	}
	public  static InetAddress getDbGUCW()  throws UnknownHostException {
		return InetAddress.getByName("dbGUCW");
	}
	public  static InetAddress getSrvSCAL()  throws UnknownHostException {
		return InetAddress.getByName("srvSCAL");
	}
	public  static InetAddress getSrvSCAW()  throws UnknownHostException {
		return InetAddress.getByName("srvSCAW");
	}
	public  static InetAddress getDbSCAL()  throws UnknownHostException {
		return InetAddress.getByName("dbSCAL");
	}
	public  static InetAddress getDbSCAW()  throws UnknownHostException {
		return InetAddress.getByName("dbSCAW");
	}
	public  static InetAddress getSrvDOCUMENTALEL()  throws UnknownHostException {
		return InetAddress.getByName("srvDOCUMENTALEL");
	}
	public  static InetAddress getSrvDOCUMENTALEW()  throws UnknownHostException {
		return InetAddress.getByName("srvDOCUMENTALEW");
	}
	public  static InetAddress getDbDOCUMENTALEL()  throws UnknownHostException {
		return InetAddress.getByName("dbDOCUMENTALEL");
	}
	public  static InetAddress getDbDOCUMENTALEW()  throws UnknownHostException {
		return InetAddress.getByName("dbDOCUMENTALEW");
	}
	public  static InetAddress getSrvDIGEMONL()  throws UnknownHostException {
		return InetAddress.getByName("srvDIGEMONL");
	}
	public  static InetAddress getSrvDIGEMONW()  throws UnknownHostException {
		return InetAddress.getByName("srvDIGEMONW");
	}
	public  static InetAddress getDbDIGEMONL()  throws UnknownHostException {
		return InetAddress.getByName("dbDIGEMONL");
	}
	public  static InetAddress getDbDIGEMONW()  throws UnknownHostException {
		return InetAddress.getByName("dbDIGEMONW");
	}
	public  static InetAddress getSrvIMPORTATORIL()  throws UnknownHostException {
		return InetAddress.getByName("srvIMPORTATORIL");
	}
	public  static InetAddress getSrvIMPORTATORIW()  throws UnknownHostException {
		return InetAddress.getByName("srvIMPORTATORIW");
	}
	public  static InetAddress getDbIMPORTATORIL()  throws UnknownHostException {
		return InetAddress.getByName("dbIMPORTATORIL");
	}
	public  static InetAddress getDbIMPORTATORIW()  throws UnknownHostException {
		return InetAddress.getByName("dbIMPORTATORIW");
	}

}
