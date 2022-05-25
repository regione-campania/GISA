/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cliniche;

import java.util.ArrayList;
import java.util.List;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.vam.Clinica;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class ToAssociatesUserEdit extends GenericAction {

	
	public void execute() throws Exception
	{
		//Recupero bean Utente
		BUtente utente = (BUtente)persistence.find(BUtente.class , interoFromRequest("idUtente"));
		req.setAttribute("utenteSelezionato", utente);
		
		//Recupero elenco Cliniche
		ArrayList<Clinica> elencoCliniche = (ArrayList<Clinica>)persistence.findAll(Clinica.class);
		req.setAttribute("elencoCliniche", elencoCliniche);
		
		gotoPage( "/jsp/vam/cliniche/associatesUserEdit.jsp" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}
	
	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "UTENTI", "ASSOCIAZIONE CLINICA" );
		can( gui, "w" );
	}
}