/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cliniche;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Clinica;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class ToAssociatesUser extends GenericAction {

	
	public void execute() throws Exception
	{
		//Recupero Utenti già associati ad una clinica
		List<BUtente> utentiAssociati = persistence.createCriteria( BUtente.class )
		.add( Restrictions.isNotNull("clinica") )
		.list();
		req.setAttribute("utentiAssociati", utentiAssociati);
		
		//Recupero Utenti senza cliniche associate
		List<BUtente> utentiNonAssociati = persistence.createCriteria( BUtente.class )
		.add( Restrictions.isNull("clinica") )
		.list();
		req.setAttribute("utentiNonAssociati", utentiNonAssociati);

		//Recupero elenco Cliniche
		ArrayList<Clinica> elencoCliniche = (ArrayList<Clinica>)persistence.findAll(Clinica.class);
		req.setAttribute("elencoCliniche", elencoCliniche);
		
		gotoPage( "/jsp/vam/cliniche/associatesUser.jsp" );
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