/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.controller;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.servlets.ControllerGlobalItemsHook;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.actionplans.base.ActionStep;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Configures globally available items for CFS.
 *
 * @author mrajkowski
 * @version $Id: GlobalItemsHook.java,v 1.15 2002/12/23 16:12:28 mrajkowski
 *          Exp $
 * @created July 9, 2001
 */
public class GlobalItemsHook implements ControllerGlobalItemsHook {

  /**
   * Generates all of the HTML for the permissable items.
   *
   * @param request Description of Parameter
   * @param servlet Description of the Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  public String generateItems(Servlet servlet, HttpServletRequest request) {
    ConnectionElement ce = (ConnectionElement) request.getSession().getAttribute(
            "ConnectionElement");
    if (ce == null) {
      return null;
    }
    Hashtable systems = (Hashtable) servlet.getServletConfig().getServletContext().getAttribute(
            "SystemStatus");
    if (systems == null) {
      return null;
    }
    SystemStatus systemStatus = (SystemStatus) (systems).get(ce.getUrl());
    if (systemStatus == null) {
      return null;
    }
    UserBean thisUser = (UserBean) request.getSession().getAttribute("User");
    if (thisUser == null) {
      return null;
    }
 


    StringBuffer items = new StringBuffer();


    items.append( "<script type=\"text/javascript\" src=\"http://maps.google.com/maps/api/js?sensor=false\"></script>" );
	items.append( "<script type=\"text/javascript\">");
	items.append( "function getCoordinate(addr, citt, prov,cap,fieldLat,fieldLon)" +
			      "{ if (citt=='' || prov==''){alert('Attenzione! riemprire i campi Comune e Provincia!');}"
			      + "else{");
	items.append( 	"var geocoder = new google.maps.Geocoder();");
	
	
	items.append( 	"var address = '';");
			items.append( 	"if( addr==null || addr==''){");
					items.append( 	"address = citt + ' '+prov+',italy';");
					items.append( 	"}");		
					items.append( 	"else{");
									items.append( 	"address = addr+','+citt+' '+prov+',italy';");
									items.append( 	"}");
	items.append( 	"geocoder.geocode( { 'address': address}, function(results, status) " +
				    "{");
	
	
	items.append( 		"if (status == google.maps.GeocoderStatus.OK) " +
			        	"{");
	
	 items.append( "alert('Indirizzo  Trovato : '+results[0]['formatted_address']);"+		
	
	 
 "var latitude = results[0].geometry.location.lng();" +
 "var longitude = results[0].geometry.location.lat();" +
 " fieldLat.value=latitude;" +
 "fieldLon.value=longitude;" +
 "}");		
//	 "if (results[i]['address_components'][1]['short_name'].toLowerCase() == citt.toLowerCase() )" +
//		 "{" +
//	 "var latitude = results[i].geometry.location.lng();" +
//	 "var longitude = results[i].geometry.location.lat();" +
//	 " fieldLat.value=latitude;" +
//	 "fieldLon.value=longitude;" +
//	 "trovato = true ;" +
//	 "break ;" + 	
//		 "}" +
//	"if (trovato==false)" +
//		" alert(\"Attenzione ! La combinazione Indirizzo / Comune non e stata Trovata \");" +
//	 "}");
 
  
//	items.append( 			"var latitude = results[0].geometry.location.lng();");
//	items.append( 			"var longitude = results[0].geometry.location.lat();");
//	items.append( 			"document.getElementById('geo_lat').value=latitude;");
//	items.append( 			"document.getElementById('geo_lon').value=longitude;");
	items.append( 		
					"});}}</script>");
	
	
	items.append( "<script type=\"text/javascript\">");
	items.append( "function getVisualizzaSuMappa(lon,lat)" +
		      "{ ");
	items.append( 	"if (lon=='' || lat == '') { alert('Selezionare prima una coppia di coordinate!'); return false;}");
	items.append( 	"window.open('https://www.google.it/maps/@'+lon+','+lat+',14z');");
	items.append("}</script>");
	
	
	items.append( "<table class=\"details\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">" +
        "<tr class=\"globalItem\" ><th style=\"background-color:#405c81;color:#fff7bd;\" class=\"globalItem\" colspan=\"2\">Ricerca Coordinate</th></tr>" +
        "<tr><td>" );
	items.append( "indirizzo</td><td> <input type=\"text\" name=\"geo_addr\" id=\"geo_addr\"/></tr><tr><td>\n" );
	items.append( "citt&agrave;</td><td> <input type=\"text\" name=\"geo_citt\" id=\"geo_citt\"/></tr><tr><td>\n" );
	items.append( "provincia</td><td> <input type=\"text\" name=\"geo_prov\" id=\"geo_prov\"/></tr><tr><td>\n" );
	items.append( "cap</td><td> <input type=\"text\" name=\"geo_cap\" id=\"geo_cap\"/></tr><tr><td>\n" );
	items.append( "lat</td><td> <input type=\"text\" name=\"geo_lon\" id=\"geo_lon\"/></tr><tr><td>\n" );
	items.append( "lon</td><td> <input type=\"text\" name=\"geo_lat\" id=\"geo_lat\"/></tr><tr><td colspan=\"2\">\n" );
	
	items.append( 
			"<input type=\"button\" " +
			"onclick=\"javascript:getCoordinate( document.getElementById('geo_addr').value," +
			"document.getElementById('geo_citt').value," +
			"document.getElementById('geo_prov').value,document.getElementById('geo_cap').value, document.getElementById('geo_lat'),document.getElementById('geo_lon'))\" " +
			"value=\"Cerca\" />" );
	
	items.append( 
			"<input type=\"button\" " +
			"onclick=\"javascript:getVisualizzaSuMappa( document.getElementById('geo_lon').value," +
			"document.getElementById('geo_lat').value)\" " +
			"value=\"Visualizza su mappa\" />" );
	
	items.append("<input type=\"hidden\" name=\"coord_type\" id=\"coord_type\" value=\"0\" /></td></tr></table>");
	
	
	
  

    if (items.length() > 0) {
      //If they have any modules, then create a cell to hold them...
   //   return (items.toString()); 
    	return "";
    } else {
      //No global items
      return "";
    }
  }


  /**
   * Description of the Method
   *
   * @param count Description of the Parameter
   * @return Description of the Return Value
   */
  private static String paint(int count) {
    if (count > 0) {
      return "<font color=\"red\">" + count + "</font>";
    } else {
      return "" + count;
    }
  }

}


