/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.modules.base.Parameter;

public class ParameterUtils
{
	public static ArrayList<Parameter> list( HttpServletRequest req, String prefisso )
	{
		ArrayList<Parameter> ret = new ArrayList<Parameter>();
		
		Enumeration e = (Enumeration)req.getParameterNames();
		
		while(e.hasMoreElements())
		{
			String nome_parametro = (String)e.nextElement();
			if( nome_parametro.startsWith( prefisso ) )
			{
				
				Parameter p = new Parameter();
				
				p.setPrefisso( prefisso );
				p.setNome( nome_parametro );
				for(String s : req.getParameterValues( nome_parametro )){
					p.getValori().add(s);
					p.setValore(s);
				}
				
				try
				{
					String id = p.getNome().replace( prefisso, "" );
					if(id != null && !id.equals("")){
						p.setId( Integer.parseInt( id ) );
					}
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				//System.out.println("id type "+nome_parametro+" valore "+req.getParameter( nome_parametro ) );
				ret.add( p );
			}
		}
		Collections.sort( ret );
		return ret;
	}
	
	
	public static Parameter get( HttpServletRequest req, String nomeParametro )
	{
	
		
				Parameter p = new Parameter();
				p.setPrefisso( nomeParametro );
				p.setNome( nomeParametro );
				p.setValore(req.getParameter(nomeParametro));
			
				System.out.println("id type "+nomeParametro+" valore "+req.getParameter( nomeParametro ) );
				
		
		
		return p;
	}
}
