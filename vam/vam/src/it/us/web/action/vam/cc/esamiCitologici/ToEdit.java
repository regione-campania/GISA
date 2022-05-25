/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiCitologici;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameCitologico;
import it.us.web.bean.vam.EsameCoprologico;
import it.us.web.bean.vam.lookup.LookupEsameCitologicoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameCitologicoTipoPrelievo;
import it.us.web.bean.vam.lookup.LookupEsameCoprologicoElminti;
import it.us.web.bean.vam.lookup.LookupEsameCoprologicoProtozoi;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToEdit extends GenericAction implements Specie
{

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("citologico");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
		EsameCitologico esame = (EsameCitologico) persistence.find(EsameCitologico.class, interoFromRequest("idEsame") );
		
		ArrayList<LookupEsameCitologicoTipoPrelievo> tipiPrelievo = (ArrayList<LookupEsameCitologicoTipoPrelievo>) persistence.createCriteria( LookupEsameCitologicoTipoPrelievo.class )
			.list();
		
		ArrayList<LookupEsameCitologicoDiagnosi> diagnosi = (ArrayList<LookupEsameCitologicoDiagnosi>) persistence.createCriteria( LookupEsameCitologicoDiagnosi.class )
			.list();
		
			
		req.setAttribute( "modify", true );
		req.setAttribute( "esame", esame );
		req.setAttribute( "diagnosi", diagnosi );
		req.setAttribute( "tipiPrelievo", tipiPrelievo );
		
		gotoPage("/jsp/vam/cc/esamiCitologici/addEdit.jsp");
	}
}

