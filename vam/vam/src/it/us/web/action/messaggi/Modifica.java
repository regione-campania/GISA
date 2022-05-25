/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.messaggi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.exceptions.AuthorizationException;

public class Modifica extends GenericAction {

	@Override
	public void can() throws AuthorizationException
	{
		can(GuiViewDAO.getView("AMMINISTRAZIONE", "MAIN", "MAIN"), "w");
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		String messaggio = stringaFromRequest("messaggio");

        String pathFile = context.getRealPath("jsp/messaggi/messaggio.txt");

        File f = new File(pathFile);

        FileOutputStream fos = new FileOutputStream(f);
        PrintStream ps = new PrintStream(fos);
        ps.print("<b>"+messaggio+"</b>");
        ps.flush();
        setMessaggio("Messaggio inserito con successo");

        Timestamp dataUltimaModifica = new Timestamp(System.currentTimeMillis());
        context.setAttribute("MessaggioHome", dataUltimaModifica+"&&<b>"+messaggio+"</b>");
		
		gotoPage("/jsp/messaggi/modifica.jsp");
		
	}
}	
