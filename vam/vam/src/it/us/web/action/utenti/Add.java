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
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.ValidationBeanException;
import it.us.web.permessi.Permessi;
import it.us.web.util.Md5;
import it.us.web.util.properties.Message;

import java.sql.Timestamp;

import org.apache.commons.beanutils.BeanUtils;

public class Add extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "UTENTI", "ADD" );
		can( gui, "w" );
	}

	@Override
	public void execute() throws Exception
	{
		Action returnAction = null;
		
		BUtente newUser = new BUtente();
		BeanUtils.populate( newUser, req.getParameterMap() );
		newUser.setEnabled(true);
		String validationError = validate( newUser );
		if( validationError == null )
		{
	 		persistence.insert( newUser );
	 		persistence.commit();
	 		
	 		if( newUser.getId() > 0 )
	 		{
	 			Permessi.startupUser( newUser );
	 			returnAction = new Detail();// new List();
	 			setMessaggio( Message.getSmart( "UTENTE_AGGIUNTO" ) );
	 		}
	 		else
	 		{
	 			returnAction = new ToAdd();
	 			setErrore( Message.getSmart( "ERRORE_INSERIMENTO_UTENTE" ) );
	 		}
			
		}
		else
		{
			setErrore( validationError );
			returnAction = new ToAdd();
		}

 		req.setAttribute( "userDetail", newUser );
		goToAction( returnAction );
	}
	
	/**
	 * controlla che i valori passati siano corretti e imposta i dati di sistema 
	 * @param newUser
	 * @return
	 * @throws ValidationBeanException 
	 */
	private String validate(BUtente newUser) throws ValidationBeanException
	{
		String ret = null;
		
		validaBean( newUser, new ToAdd() );
		
		String password = stringaFromRequest( "password_1" );
		String confirmPassword = stringaFromRequest( "password_2" );
		if( password == null || !password.equals( confirmPassword ) )
		{
			ret = "\nla password e la sua conferma non coincidono";
		}
		else if( password.length() <= 8 )
		{
			ret = "\nlunghezza minima della password 8 caratteri";
		}
		else
		{
			newUser.setPassword( Md5.encrypt( password ) );
		}
		
		newUser.setEntered( new Timestamp( System.currentTimeMillis() ) );
		newUser.setEnteredBy( (int)utente.getId() );
		newUser.setModified( newUser.getEntered() );
		newUser.setModifiedBy( (int)utente.getId() );
		
		return ret;
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}

}
