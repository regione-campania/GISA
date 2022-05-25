/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.ws;

import it.us.web.action.GenericAction;
import it.us.web.bean.vam.Clinica;
import it.us.web.exceptions.AuthorizationException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


public class ListCliniche extends GenericAction
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
		try
		{
			//Recupero di tutte le cliniche
			ArrayList<Clinica> clinicheA = (ArrayList<Clinica>) persistence.findAll(Clinica.class);

			Iterator listaCliniche = clinicheA.iterator();

			String cliniche = "";
			Clinica c;

			while (listaCliniche.hasNext()) {
				c = (Clinica) listaCliniche.next();
				cliniche = cliniche + c.getLookupAsl().getId()+"@@@" + c.getId()+"@@@"+c.getNome()+";;;";	
			}


			PrintWriter pw = res.getWriter();
			pw.write(cliniche);
			pw.flush();
		}
		catch(Exception e)
		{
			PrintWriter pw = res.getWriter();
			pw.write("KO");
			pw.flush();
		}
	}
}