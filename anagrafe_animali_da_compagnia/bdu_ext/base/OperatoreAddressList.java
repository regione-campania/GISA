/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.modules.base.AddressList;

public class OperatoreAddressList extends AddressList{

	
	  public OperatoreAddressList() {
	  }
	  
	  
	  
	  public OperatoreAddressList(HttpServletRequest request) {
		    int i = 1;
		    int primaryAddress = -1;
		    if (request.getParameter("primaryAddress") != null) {
		      primaryAddress = Integer.parseInt(
		          (String) request.getParameter("primaryAddress"));
		    }
		    
		    while (request.getParameter("address" + (i) + "type") != null) {
		    	org.aspcfs.modules.opu.base.OperatoreAddress thisAddress = new  org.aspcfs.modules.opu.base.OperatoreAddress();
		    	thisAddress.buildRecord(request, i);
		      if (primaryAddress == i) {
		        thisAddress.setPrimaryAddress(true);
		      }
		      if (thisAddress.isValid()) {
		        this.addElement(thisAddress);
		      }
		      i++;
		    }
		  
		    i=1;
		    while (request.getParameter("address4"  + "type"+(i)) != null) {
		    	
		    	// org.aspcfs.modules.requestor.base.OrganizationAddress thisAddress = new  org.aspcfs.modules.requestor.base.OrganizationAddress();
		        /*thisAddress.buildRecord2(request, i);
		       
		        if (thisAddress.isValid()) {
		          this.addElement(thisAddress);
		        }
		        i++;*/
		      }
		    
		  }
	  
	  
}
