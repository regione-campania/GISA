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
import it.us.web.util.properties.Message;

import java.sql.Timestamp;

import org.apache.commons.beanutils.BeanUtils;

public class Modify extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "UTENTI", "EDIT" );
		can( gui, "w" );
	}

	@Override
	public void execute() throws Exception
	{
		Action returnAction = null;
		

		BUtente toUpdateUser = (BUtente) persistence.find( BUtente.class, interoFromRequest( "id" ) );
		
		BeanUtils.populate( toUpdateUser, req.getParameterMap() );
		
		if( validate( toUpdateUser ) )
		{
			persistence.update( toUpdateUser );
			persistence.commit();
	 		
 			returnAction = new Detail();
 			setMessaggio( Message.getSmart( "UTENTE_MODIFICATO" ) );
		}
		else
		{
			setErrore( "ERRORE_AGGIORNAMENTO_UTENTE" );
			returnAction = new ToModify();
		}

 		req.setAttribute( "userDetail", toUpdateUser );
		goToAction( returnAction );
	}

	/**
	 * controlla che i valori passati siano corretti e imposta i dati di sistema 
	 * @param newUser
	 * @return
	 */
	private boolean validate(BUtente newUser)
	{
		boolean ret = true;
		
//		BUtente oldUser = (BUtente) GenericHibernateDAO.find( BUtente.class, newUser.getId() );
//		
//		newUser.setPassword( oldUser.getPassword() );
//		newUser.setDomanda_segreta( newUser.getDomanda_segreta() );
//		newUser.setRisposta_segreta( oldUser.getRisposta_segreta() );
//		newUser.setEntered_by( oldUser.getEntered_by() );
//		newUser.setEntered( oldUser.getEntered() );
		newUser.setModifiedBy( (int)utente.getId() );
		newUser.setModified( new Timestamp( System.currentTimeMillis() ) );
		
		return ret;
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}

}
