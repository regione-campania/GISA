/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.ruoli;

import it.us.web.action.Action;
import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.RuoloDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.permessi.Permessi;
import it.us.web.util.properties.Message;

public class Add extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "RUOLI", "ADD" );
		can( gui, "w" );
	}

	@Override
	public void execute() throws Exception
	{
		Action returnAction = null;
		
		String ruoloDaClonare	= stringaFromRequest( "ruoloDaClonare" );
		String nomeruolo		= stringaFromRequest( "ruolo" );
		String descrizione		= stringaFromRequest( "descrizione" );
		
		if( !verificaCampi( nomeruolo, descrizione ) )
		{
			setErrore( "Inserire ruolo e descrizione" );
			returnAction = new ToAdd();
		}
		else
		{
			//Indica se in fase di creazione bisogna anche  
			//clonare i permessi di un altro ruolo
			boolean clone = false;
			if( !isEmpty( ruoloDaClonare ) )
			{
				clone = true;
			}
			
			//Se non esiste gia'' un ruolo con quel nome
			if( RuoloDAO.getRuoloByName( nomeruolo ) == null )
			{
				RuoloDAO.insertRuolo( nomeruolo, descrizione );
				//Clonazione permessi
				Permessi.createCategory( nomeruolo, clone, ruoloDaClonare );
				setMessaggio( Message.getSmart( "OPERAZIONE_ESEGUITA_CON_SUCCESSO" ) );
			}
			else
			{
				setErrore( Message.getSmart( "RUOLO_GIA'_ESISTENTE" ) + ": " + nomeruolo );
			}
			
			returnAction = new List();
		}
		
		goToAction( returnAction );
		
	}

	private boolean verificaCampi( String nomeruolo, String descrizione )
	{
		return !isEmpty( nomeruolo ) && !isEmpty( descrizione );
	}

}
