/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.servlet;

import it.us.web.action.Action;
import it.us.web.action.GenericAction;
import it.us.web.action.Index;
import it.us.web.db.DbUtil;
import it.us.web.exceptions.ActionNotValidException;
import it.us.web.exceptions.NotLoggedException;
import it.us.web.util.DateUtils;
import it.us.web.util.FloatConverter;
import it.us.web.util.MyDoubleConverter;
import it.us.web.util.MyIntegerConverter;
import it.us.web.util.properties.Message;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;

public class Dispatcher extends HttpServlet
{
	static
	{
		ConvertUtils.register( new DateUtils.MyUtilDateConverter(), Date.class );
		ConvertUtils.register( new FloatConverter()   ,  Float.class );
		ConvertUtils.register( new FloatConverter()   ,  Float.TYPE );
		ConvertUtils.register( new MyDoubleConverter(),  Double.class);
		ConvertUtils.register( new MyIntegerConverter(),   Integer.class);
	}

	private static final long serialVersionUID = -8397394451535054535L;
	private static final String actionPackage = "it.us.web.action.";

	protected void service(HttpServletRequest req, HttpServletResponse res)
		throws
			ServletException,
			IOException
	{
		
		String action = parseAction( req );
		
		Action act = null;
		Connection db = null ;
		try
		{
			System.out.println("Dispatch to action");
			act = getActionClass( action );
		
			db = DbUtil.getConnection() ;
			act.setConnectionDb(db);
			act.startup( req, res, getServletContext() );
			act.can();
			act.execute();
			
			DbUtil.close(db);
		}
		catch (Exception e)
		{
			

			try {
				DbUtil.close(db);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			req.setAttribute( "errore", e.getMessage() );
			if( e instanceof NotLoggedException )
			{
				try
				{
					GenericAction.goToAction( new Index(), req, res );
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				GenericAction.gotoPage( "/jsp/errore/errore.jsp", req, res );
			}
		}
		
		
	}
	
	private String parseAction(HttpServletRequest req)
	{
		String action = null;
		String temp = req.getServletPath();
		if( temp != null )
		{
			temp = temp.replace("/", "");
			temp = temp.replace(".us", "");
			action = temp;
		}
		return action;
	}
	
	@SuppressWarnings("unchecked")
	private Action getActionClass( String action ) throws ActionNotValidException
	{
		Class c = null;
		Action act = null;
		
		try
		{
			c = Class.forName( actionPackage + action );
			act = (Action) c.newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ActionNotValidException( Message.getSmart( "azione_sconosciuta" ) );
		}
		
		return act;
	}
}
