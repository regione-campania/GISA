/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.utenti;

import it.us.web.action.Action;
import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.Md5;
import it.us.web.util.properties.Message;

public class CambioPassword extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "UTENTI", "CAMBIO PASSWORD" );
		can( gui, "w" );
	}

	@Override
	public void execute() throws Exception
	{
		Action returnAction = null;
		

		BUtente toUpdateUser = UtenteDAO.getUtenteBId(db, interoFromRequest( "user_id" )) ; //(BUtente) persistence.find( BUtente.class, interoFromRequest( "user_id" ) );
		
		if( validate( toUpdateUser ) )
		{
			UtenteDAO.update(db, toUpdateUser) ;
			//persistence.update( toUpdateUser );
			//persistence.commit();
	 		
 			returnAction = new Detail();
 			setMessaggio( Message.getSmart( "PASSWORD_MODIFICATA" ) );
		}
		else
		{
			setErrore( "Inserire una password di almeno 8 caratteri ed assicurarsi che sia uguale alla sua conferma" );
			returnAction = new ToCambioPassword();
		}

 		req.setAttribute( "userDetail", toUpdateUser );
		goToAction( returnAction );
	}

	private boolean validate(BUtente toUpdateUser)
	{
		boolean ret = true;
		
		String password = stringaFromRequest( "password_1" );
		String confirmPassword = stringaFromRequest( "password_2" );
		if( password == null || !password.equals( confirmPassword ) || password.length() < 8 )
		{
			ret = false;
		}
		else
		{
			toUpdateUser.setPassword( Md5.encrypt( password ) );
		}
		
		return ret;
	}


}
