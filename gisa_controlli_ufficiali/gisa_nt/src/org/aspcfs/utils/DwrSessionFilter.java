/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.lang.reflect.Method;
 




import javax.servlet.http.HttpSession;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.UserBean;
import org.directwebremoting.AjaxFilter;
import org.directwebremoting.AjaxFilterChain;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.LoginRequiredException;

import com.sun.corba.se.impl.orbutil.StackImpl;
  
public class DwrSessionFilter implements AjaxFilter {
    public Object doFilter(Object obj, Method method, Object[] params, AjaxFilterChain chain) throws Exception {
 
    	
    	HttpSession sessione  = WebContextFactory.get().getSession();
    	UserBean user = (UserBean) sessione.getAttribute("User");
    	
  
    	
    	
    	//Check if session has timedout/invalidated
	    if( WebContextFactory.get().getSession( false ) == null || user==null) {
	        //Throw an exception
	        LoginRequiredException f = new LoginRequiredException("Sessione di Lavoro Scaduta.");
	        StackTraceElement[] stack = new StackTraceElement[1];
	        stack[0] = new StackTraceElement("DwrSessionFilter", "doFilter", "DwrSessionFilter.java", 21);
	        f.setStackTrace(stack);
	        throw f;
	    } 
	    
	    return chain.doFilter( obj, method, params );
//	    else {
//	    	String browser = null;
//	    	if (browser==null){
//	    		browser = user.getBrowserId();
//	    		if (browser.equalsIgnoreCase("moz"))
//	    			browser = "Firefox";
//	    	}
//	    	
//	    	if (browser.contains("Firefox")){   
//	    		return chain.doFilter( obj, method, params );
//	    	} else {
//	    		try {
//	    			return chain.doFilter( obj, method, params );
//	    		} catch (Exception e){
//	    			LoginRequiredException f = new LoginRequiredException("[BROWSER] Operazione non consentita con il browser utilizzato. I dati inseriti non saranno salvati.");
//		            StackTraceElement[] stack = new StackTraceElement[1];
//			        stack[0] = new StackTraceElement("DwrSessionFilter", "doFilter", "DwrSessionFilter.java", 21);
//			        f.setStackTrace(stack);
//		            throw f;
//	    		}        
//	    	}
//	    }
 
    	
     	
    }
}
  