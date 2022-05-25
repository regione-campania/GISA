/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.rabbia;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.toxoplasmosi.ToAddEdit;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Rabbia;
import it.us.web.bean.vam.lookup.LookupEsitoRabbia;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

public class AddEdit extends GenericAction
{
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("rabbia");
	}

	public void execute() throws Exception
	{
		Rabbia esame;
		
		if(booleanoFromRequest("modify"))
		{
			esame = (Rabbia) persistence.find( Rabbia.class, interoFromRequest( "id" ) );
		}
		else
		{
			esame = new Rabbia();
		}
		
		BeanUtils.populate( esame, req.getParameterMap() );
		
		esame.setCartellaClinica( cc );
		esame.setModified( new Date() );
		esame.setModifiedBy( utente );
		
		if(!booleanoFromRequest("modify"))
		{
			esame.setEntered(new Date());
			esame.setEnteredBy(utente);
		}
		
		esame.setPrelievoSangue( booleanoFromRequest( "prelievoSangue") );
		if( esame.getPrelievoSangue() )
		{
			int idEsitoSangue = interoFromRequest( "idEsitoSangue" );
			if( idEsitoSangue > 0 )
			{
				esame.setEsitoSangue( (LookupEsitoRabbia) persistence.find(LookupEsitoRabbia.class, idEsitoSangue));
			}
			else
			{
				esame.setEsitoSangue( null );
			}
		}
		else
		{
			esame.setEsitoSangue( null );
		}
		
		esame.setPrelievoEncefalo( booleanoFromRequest( "prelievoEncefalo") );
		if( esame.getPrelievoEncefalo() )
		{
			int idEsitoEncefalo = interoFromRequest( "idEsitoEncefalo" );
			if( idEsitoEncefalo > 0 )
			{
				esame.setEsitoEncefalo( (LookupEsitoRabbia) persistence.find(LookupEsitoRabbia.class, idEsitoEncefalo));
			}
			else
			{
				esame.setEsitoEncefalo( null );
			}
		}
		else
		{
			esame.setEsitoEncefalo( null );
		}
		
			
		validaBean( esame, new ToAddEdit() );
			if( esame.getId() > 0 )
			{
				persistence.update( esame );
				setMessaggio( "Esame Rabbia modificato con successo" );
			}
			else
			{
				esame.setEntered( esame.getModified() );
				esame.setEnteredBy( utente );
				
				persistence.insert( esame );			
				setMessaggio( "Esame Rabbia inserito con successo" );
			}
			
			cc.setModified( esame.getModified() );
			cc.setModifiedBy( utente );
			persistence.update( cc );
			persistence.commit();
			
			
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(esame);
			}
			
			
			redirectTo( "vam.cc.rabbia.List.us" );
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta/Modifica Rabbia";
	}
}
