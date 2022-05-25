/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.felv;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Felv;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class AddEdit extends GenericAction
{
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("felv");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
		Felv felv;
		
		if(booleanoFromRequest("modify"))
		{
			felv = (Felv) persistence.find( Felv.class, interoFromRequest( "id" ) );
		}
		else
		{
			felv = new Felv();
		}
		
		BeanUtils.populate( felv, req.getParameterMap() );
		
		felv.setCartellaClinica( cc );
		felv.setModified( new Date() );
		felv.setModifiedBy( utente );
		
			
		validaBean( felv, new ToAddEdit() );
		if( felv.getId() > 0 )
		{
			persistence.update( felv );
			setMessaggio( "Esame Felv modificato con successo" );
		}
		else
		{
			felv.setEntered( felv.getModified() );
			felv.setEnteredBy( utente );
			
			persistence.insert( felv );			
			setMessaggio( "Esame Felv inserito con successo" );
		}
		
		cc.setModified( felv.getModified() );
		cc.setModifiedBy( utente );
		persistence.update( cc );
		persistence.commit();
		
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(felv);
		}
		
		redirectTo( "vam.cc.felv.List.us" );
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta/Modifica Felv";
	}
}
