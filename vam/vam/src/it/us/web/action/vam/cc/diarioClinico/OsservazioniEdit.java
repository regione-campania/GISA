/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.diarioClinico;

import org.apache.commons.beanutils.BeanUtils;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.DiarioClinico;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class OsservazioniEdit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("diarioClinico");
	}

	public void execute() throws Exception
	{
		int idDc = interoFromRequest( "idDc" );
		DiarioClinico dc = (DiarioClinico) persistence.find( DiarioClinico.class, idDc );
		
		BeanUtils.populate( dc, req.getParameterMap() );
		
		persistence.update( dc );
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(dc);
		}
		
		setMessaggio( "Osservazione modificata con successo" );
		
		redirectTo( "vam.cc.diarioClinico.Detail.us" );
	}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Diario clinico";
	}
}



