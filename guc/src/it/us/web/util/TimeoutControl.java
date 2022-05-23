/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import it.us.web.util.properties.Application;
import java.io.*;
import java.net.*;

public class TimeoutControl {	
	
	//private static final String ESB_URL 			= 	Application.get( "ESB_URL" );
	//private static final int 	ESB_PORT 			=   Integer.parseInt( Application.get( "ESB_PORT" ));
		
	private static final String CANINA_WRAPPER_URL	= 	Application.get( "CANINA_WRAPPER_URL" );
	private static final int 	CANINA_WRAPPER_PORT =   Integer.parseInt( Application.get( "CANINA_WRAPPER_PORT" ));
		
	private static final String FELINA_WRAPPER_URL 	= 	Application.get( "FELINA_WRAPPER_URL" );
	private static final int 	FELINA_WRAPPER_PORT	=   Integer.parseInt( Application.get( "FELINA_WRAPPER_PORT" ));
	
	private static final String CANINA_URL			= 	Application.get( "CANINA_URL" );
	private static final int 	CANINA_PORT 		=   Integer.parseInt( Application.get( "CANINA_PORT" ));
		
	private static final String FELINA_URL 			= 	Application.get( "FELINA_URL" );
	private static final int 	FELINA_PORT			=   Integer.parseInt( Application.get( "FELINA_PORT" ));
	
	private static final int 	timeoutTime			=	14000;  //millisecondi
		
	
	/* Testa tutte le applicazioni del Wrapper*/
	public static boolean testAllConnection () {
		
		boolean allOnLine = true;
		
		if (/*testESBConnection() == -1 ||*/ testCaninaWrapperConnection() == -1 || testFelinaWrapperConnection() == -1)
			allOnLine = false;
		
		return allOnLine;
		
	}
	
/*	public static long testESBConnection () {
		return testConnection(ESB_URL, ESB_PORT, timeoutTime);
	}*/
	
	public static long testCaninaWrapperConnection () {
		return testConnection(CANINA_WRAPPER_URL, CANINA_WRAPPER_PORT, timeoutTime);
	}
	
	public static long testFelinaWrapperConnection () {
		return testConnection(FELINA_WRAPPER_URL, FELINA_WRAPPER_PORT, timeoutTime);
	}
		
	private static long testCaninaConnection () {
		return testConnection(CANINA_URL, CANINA_PORT, timeoutTime);
	}
	
	private static long testFelinaConnection () {
		return testConnection(FELINA_URL, FELINA_PORT, timeoutTime);
	}
	
	
	private static long testConnection(String addr1, int port, int timeoutMs) {
			
	long start 	= -1;	 //default check value
	long end 	= -1; 	//default check value
	long total 	= -1; 	// default for bad connection
			
	//Nuova socket
	Socket theSock = new Socket();

	try {
		
		InetAddress addr= InetAddress.getByName(addr1);
		SocketAddress sockaddr = new InetSocketAddress(addr, port);
	
		//Create the socket with a timeout
		//when a timeout occurs, we will get timout exp.
		//also time our connection this gets very close to the real time
		start = System.currentTimeMillis();
		theSock.connect(sockaddr, timeoutMs);
		end = System.currentTimeMillis();
	} 
	catch (UnknownHostException e) {
		start = -1;
		end = -1;
	} 
	catch (SocketTimeoutException e) {
		start = -1;
		end = -1;
	} 
	catch (IOException e) {
		start = -1;
		end = -1;
	} 
	finally {
		if (theSock != null) {
		try {
			theSock.close();
		} 
		catch (IOException e) {
		}
	}

	if ((start != -1) && (end != -1)) {
		total = end - start;
	}
	
	}

	return total; //returns -1 if timeout
	}
	
	
	public static void main (String []argv) {		
		//System.out.println("ESB" 			+ TimeoutControl.testESBConnection());
		System.out.println("CANINA WRAPPER" + TimeoutControl.testCaninaWrapperConnection());;
		System.out.println("FELINA WRAPPER" + TimeoutControl.testFelinaWrapperConnection());		
		System.out.println("CANINA" + TimeoutControl.testCaninaConnection());;
		System.out.println("FELINA" + TimeoutControl.testFelinaConnection());
		
		System.out.println("All connection are on-line?" + testAllConnection());
	}
	
}