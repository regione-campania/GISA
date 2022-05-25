/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.lang.reflect.Method;
 
import org.directwebremoting.AjaxFilter;
import org.directwebremoting.AjaxFilterChain;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.LoginRequiredException;
  
public class DwrSessionFilter implements AjaxFilter {
    public Object doFilter(Object obj, Method method, Object[] params, AjaxFilterChain chain) throws Exception {
 
        //Check if session has timedout/invalidated
        if( WebContextFactory.get().getSession( false ) == null ) {
            //Throw an exception
            throw new LoginRequiredException( "Sessione di Lavoro Scaduta." );
        }
 
        return chain.doFilter( obj, method, params );
    }
}
