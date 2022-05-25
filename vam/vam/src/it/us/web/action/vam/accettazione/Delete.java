/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import java.util.Date;

import org.hibernate.Hibernate;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.Accettazione;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class Delete extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "DELETE", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	@Override
	public void execute() throws Exception
	{
		Accettazione accettazione = (Accettazione) persistence.find( Accettazione.class, interoFromRequest( "id" ) );
		if( !accettazione.getCancellabile() )
		{
			req.setAttribute( "specie", SpecieAnimali.getInstance() );
			setErrore( "Non è possibile cancellare l'accettazione" );
			redirectTo( "vam.accettazione.Detail.us?id=" + accettazione.getId() );
		}
		else
		{
			accettazione.setTrashedDate( new Date() );
			Hibernate.initialize( accettazione.getOperazioniRichieste() );
			persistence.update( accettazione );
			persistence.commit();
			
			setMessaggio( "Accettazione eliminata con successo" );
			redirectTo( "vam.accettazione.Home.us" );
		}
	} 

}
