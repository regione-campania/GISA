/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.fip;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Fip;
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
		setSegnalibroDocumentazione("fip");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
		Fip fip;
		
		if(booleanoFromRequest("modify"))
		{
			fip = (Fip) persistence.find( Fip.class, interoFromRequest( "id" ) );
		}
		else
		{
			fip = new Fip();
		}
		
		BeanUtils.populate( fip, req.getParameterMap() );
		
		fip.setCartellaClinica( cc );
		fip.setModified( new Date() );
		fip.setModifiedBy( utente );
		
			
		validaBean( fip , new ToAddEdit() );
		
		if( fip.getId() > 0 )
		{
			persistence.update( fip );
			setMessaggio( "Esame Fip modificato con successo" );
		}
		else
		{
			fip.setEntered( fip.getModified() );
			fip.setEnteredBy( utente );
			
			persistence.insert( fip );			
			setMessaggio( "Esame Fip inserito con successo" );
		}
		
		cc.setModified( fip.getModified() );
		cc.setModifiedBy( utente );
		persistence.update( cc );
		persistence.commit();
		
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(fip);
		}
		
		redirectTo( "vam.cc.fip.List.us" );
		
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta/Modifica Fip";
	}
}
