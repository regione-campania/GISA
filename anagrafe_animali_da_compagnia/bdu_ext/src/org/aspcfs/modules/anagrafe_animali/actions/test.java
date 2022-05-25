/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class test
 */
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			  
	 
		String s = "ABC";

		byte[] content = s.getBytes();
	       
	        
	  
	        HttpServletResponse resp = null; 
	        
	          
	        try{  
	         //   resp = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();     
	  
	            response.reset();  
	  
	            response.setHeader("Content-Disposition","attachment; filename=\"archive.pdf\"");  
	  
	            response.setContentType("application/pdf");  
	  
	            ServletOutputStream outStream = response.getOutputStream();
	            
	  
	            if(content != null){  
	                outStream.write(content);  
	            }  
	              
	            outStream.flush();  
	  
	            outStream.close();  
	        }  
	        catch(Exception e){  
	            System.err.println("PROBLEM STREAMING DAILY REPORT PDF THROUGH RESPONSE!" + e);  
	              
	            e.printStackTrace();  
	              
	    
	  
	          //  return "";  
	        }  
	  
	     //   FacesContext.getCurrentInstance().responseComplete();  
	  
	     //   return "";
	}
	
	
	

}
