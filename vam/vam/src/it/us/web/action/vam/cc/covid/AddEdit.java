/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.covid;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Covid;
import it.us.web.bean.vam.Fip;
import it.us.web.bean.vam.lookup.LookupCovidTipoTest;
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
		setSegnalibroDocumentazione("covid");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
		Covid covid;
		
		if(booleanoFromRequest("modify"))
		{
			covid = (Covid) persistence.find( Covid.class, interoFromRequest( "id" ) );
		}
		else
		{
			covid = new Covid();
		}
		
		BeanUtils.populate( covid, req.getParameterMap() );
		
		if(req.getParameter("esito")==null || req.getParameter("esito").equals(""))
			covid.setEsito(null);
		
		LookupCovidTipoTest tipoTest = (LookupCovidTipoTest) persistence.find( LookupCovidTipoTest.class, interoFromRequest( "idTipoTest" ) );
		covid.setTipoTest(tipoTest);
		
		covid.setCartellaClinica( cc );
		covid.setModified( new Date() );
		covid.setModifiedBy( utente );
		
			
		validaBean( covid , new ToAddEdit() );
		
		if( covid.getId() > 0 )
		{
			persistence.update( covid );
			setMessaggio( "Esame SARS CoV 2 modificato con successo" );
		}
		else
		{
			covid.setEntered( covid.getModified() );
			covid.setEnteredBy( utente );
			
			persistence.insert( covid );			
			setMessaggio( "Esame SARS CoV 2 inserito con successo" );
		}
		
		cc.setModified( covid.getModified() );
		cc.setModifiedBy( utente );
		persistence.update( cc );
		persistence.commit();
		
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(covid);
		}
		
		redirectTo( "vam.cc.covid.List.us" );
		
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta/Modifica SARS CoV 2";
	}
}
