/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.xls;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.permessi.Permessi;
import it.us.web.util.XlsUtil;
import it.us.web.util.properties.Label;

import java.util.Vector;

import javax.servlet.ServletOutputStream;

public class ListaRuoli extends GenericAction {

	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "RUOLI", "MAIN" );
		can( gui, "w" );
	}

	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	@SuppressWarnings("unchecked")
	public void execute()
		throws
			Exception
	{
		String ruolo	= (String)req.getParameter("ruolo");
		Vector v		= Permessi.getPermissionsOnRuolo( ruolo );
		
		StringBuffer csv = new StringBuffer( "FUNZIONE\tSUBFUNZIONE\tGUIOBJECT\tPERMESSI\n\n" );
		for( int i = 0; i < v.size(); i++ )
		{
			BGuiView g	= (BGuiView)v.elementAt(i);
			String p	= g.getDiritti();
			
			if( (p != null) && p.equalsIgnoreCase( "r" ) )
			{
				p = "ro";
			}
			else if( (p != null) && p.equalsIgnoreCase( "w" ) )
			{
				p = "rw";
			}
			else
			{
				p = "no";
			}
			
			csv.append( Label.get( g.getNome_funzione() ) + "\t"+
						Label.get( g.getNome_subfunzione() ) + "\t" +
						Label.get( g.getNome_gui() ) + "\t" +
						p + "\n" );
		}
		
		
		res.setContentType( "application/vnd.ms-excel" );
		res.setHeader( "Content-Disposition", "inline; filename=\"lista_ruoli.xls\"" );
		
		ServletOutputStream out = res.getOutputStream();

		XlsUtil.write( csv.toString(), out );
		
	}

}
