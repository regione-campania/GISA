/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import it.us.web.bean.Parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class ParameterUtils
{
	@SuppressWarnings("unchecked")
	public static ArrayList<Parameter> list( HttpServletRequest req, String prefisso )
	{
		ArrayList<Parameter> ret = new ArrayList<Parameter>();
		
		Enumeration<String> e = (Enumeration<String>)req.getParameterNames();
		
		while(e.hasMoreElements())
		{
			String nome_parametro = (String)e.nextElement();
			if( nome_parametro.startsWith( prefisso ) )
			{
				Parameter p = new Parameter();
				
				p.setPrefisso( prefisso );
				p.setNome( nome_parametro );
				p.setValore( req.getParameter( nome_parametro ) );
				
				try
				{
					String id = p.getNome().replace( prefisso, "" );
					p.setId( Integer.parseInt( id ) );
				}
				catch (Exception e1)
				{
					
				}
				ret.add( p );
			}
		}
		
		Collections.sort( ret );
		
		return ret;
	}
}
