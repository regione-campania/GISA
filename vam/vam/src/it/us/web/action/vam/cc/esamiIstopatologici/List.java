/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiIstopatologici;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class List extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
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
		Set<EsameIstopatologico> istopatologici = new HashSet<EsameIstopatologico>();
		if(!cc.getTrasferimentiByCcPostTrasf().isEmpty())
		{
			istopatologici = cc.getTrasferimentiByCcPostTrasf().iterator().next().getCartellaClinica().getEsameIstopatologicos();
		}
		if(!cc.getEsameIstopatologicos().isEmpty())
		{
			Iterator<EsameIstopatologico> iters = cc.getEsameIstopatologicos().iterator();
			while(iters.hasNext())
			{
				EsameIstopatologico esame=iters.next();
				if(esame.getTipoDiagnosi().getId()!=3)
					esame.setDiagnosiNonTumorale("");
				istopatologici.add(esame);
			}
		}
		
		
		req.setAttribute("istopatologici", istopatologici);
				
		gotoPage("/jsp/vam/cc/esamiIstopatologici/list.jsp");
	}
}



