/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.ws;

import it.us.web.action.GenericAction;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.exceptions.AuthorizationException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.criterion.Restrictions;

public class AnimaleRicoverato extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		
	}

	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	@Override
	public void execute() throws Exception
	{
		
		//Si controlla se esiste un fascicolo sanitario aperto prima e chiuso dopo una certa data
		PrintWriter pw = res.getWriter();
		
		String mc  = stringaFromRequest("mc");
		Date data  = dataFromRequest("data");
		ArrayList<CartellaClinica> cc = (ArrayList<CartellaClinica>)persistence.getNamedQuery("AnimaleRicoverato").setString( "mc", mc ).setDate( "data", data ).list();
		
		if( !cc.isEmpty() )
		{
			pw.write("OK");
			pw.flush();
		}
		else
		{
			pw.write("KO");
			pw.flush();
		}
		
		
		
//		String username  = stringaFromRequest("username");
//		ArrayList<BUtente> a = (ArrayList<BUtente>)persistence.getNamedQuery("GetUtente").setString("username",username).list();
//		if(!a.isEmpty())
//		{
//
//			pw.write("OK");
//			pw.flush();
//		}
//		else
//		{
//			pw.write("KO");
//			pw.flush();
//		}
	}
	
}
