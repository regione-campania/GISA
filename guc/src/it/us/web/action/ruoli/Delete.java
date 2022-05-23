/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.ruoli;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BRuolo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.RuoloDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.permessi.Permessi;
import it.us.web.util.properties.Message;

public class Delete extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "RUOLI", "DELETE" );
		can( gui, "w" );
	}

	@Override
	public void execute() throws Exception
	{
		String nome_ruolo	= stringaFromRequest( "NOME_RUOLO" );		
		BRuolo br			= null;
		
		if( (nome_ruolo == null) || (nome_ruolo.trim().length() == 0) )
		{
			setErrore( Message.getSmart( "PARAMETRI_NON_CORRETTI" ) );
		}
		else
		{
			br = RuoloDAO.getRuoloByName( nome_ruolo );
			
			if( (br == null) || br.isGiaAssegnato() )
			{
				setErrore( Message.getSmart( "AZIONE_NON_CONSENTITA" ) );
			}
			else
			{
				RuoloDAO.delete( nome_ruolo, utente, req );		
				br = RuoloDAO.getRuoloByName( nome_ruolo );
				
				if( br == null )
				{
					Permessi.rimuoviRuolo( nome_ruolo );
					setMessaggio( Message.getSmart( "OPERAZIONE_ESEGUITA_CON_SUCCESSO" ) );
				}
				else
				{
					setErrore( "OPERAZIONE_FALLITA" );
				}
			}
		}
		
		goToAction( new List() );
	}

}
