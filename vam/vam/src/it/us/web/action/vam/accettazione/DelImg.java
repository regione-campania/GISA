/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import it.us.web.action.GenericAction;
import it.us.web.bean.vam.Immagine;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.json.JSONObject;

import java.io.PrintWriter;
import java.util.Date;

public class DelImg extends GenericAction
{
	
	public DelImg() {		
	}
	
	@Override
	public void can() throws AuthorizationException
	{
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	@Override
	public void execute() throws Exception
	{
    	JSONObject jsonObj = new JSONObject();
    	
		try
		{
	    	Immagine	img		= (Immagine) persistence.find( Immagine.class, interoFromRequest( "id" ));
	    	
	    	img.setTrashedDate( new Date() );
	    	img.setModified( img.getTrashedDate() );
	    	img.setModifiedBy( utente );
	    	
	    	persistence.update( img );

			jsonObj.put( "status", "ok" );
			jsonObj.put( "messaggio", "Immagine '" + img.getDisplayName() + "' eliminata con successo" );
		}
		catch ( Exception e )
		{
			e.printStackTrace();

			jsonObj.put( "status", "ko" );
			jsonObj.put( "messaggio", "Errore: " + e.getMessage() );
		}

    	PrintWriter writer = res.getWriter();
    	
    	writer.println( jsonObj );
	}

}
