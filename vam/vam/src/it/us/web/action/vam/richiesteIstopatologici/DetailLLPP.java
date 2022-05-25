/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.richiesteIstopatologici;

import java.util.Set;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class DetailLLPP extends GenericAction {

	
	public void can() throws AuthorizationException
	{
//		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
//		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("istopatologico");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		EsameIstopatologico esame = (EsameIstopatologico) persistence.find (EsameIstopatologico.class, interoFromRequest("id") );	
		
		req.setAttribute( "esame", esame );		
	
		Set<LookupAlimentazioni> 	la = esame.getLookupAlimentazionis();
		Set<LookupHabitat> 			lh = esame.getLookupHabitats();
		
		req.setAttribute( "la", la );
		req.setAttribute( "lh", lh );
		
		boolean liberoProfessionista = booleanoFromRequest("liberoProfessionista");
		
		req.setAttribute("liberoProfessionista", liberoProfessionista);
		
		gotoPage("/jsp/vam/richiesteIstopatologici/detailLLPP.jsp" );
	}
}


