/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

public class Find extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "MAIN", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sinantropi_ricerca");
	}

	public void execute() throws Exception
	{
		
		String numeroSinantropo = stringaFromRequest("numeroSinantropo");
		
		//Recupero del Sinantropo per numero Ufficiale assenato dall'istituo faunistico
		ArrayList<Sinantropo> sinantropiUfficiali = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroUfficiale")
				.setBoolean("zoo", false)
				.setBoolean("marini", false)
				.setBoolean("sinantropo", true)
				.setString("numeroSinantropo", numeroSinantropo)
				.list();
				
		//Recupero del Sinantropo per numero Automatico assegnato dal sistema
		ArrayList<Sinantropo> sinantropiAutomatici = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByNumeroAutomatico")
				.setBoolean("zoo", false)
				.setBoolean("marini", false)
				.setBoolean("sinantropo", true)
				.setString("numeroSinantropo", numeroSinantropo.toUpperCase())
				.list();
		
		//Recupero del Sinantropo per mc
		ArrayList<Sinantropo> sinantropiMc = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByMc")
				.setBoolean("zoo", false)
				.setBoolean("marini", false)
				.setBoolean("sinantropo", true)
				.setString("mc", numeroSinantropo).list();

		//Recupero del Sinantropo per codice ispra
		ArrayList<Sinantropo> sinantropiIspra = (ArrayList<Sinantropo>) persistence.getNamedQuery("GetSinantropoByCodiceIspra")
				.setBoolean("zoo", false)
				.setBoolean("marini", false)
				.setBoolean("sinantropo", true)
				.setString("numeroSinantropo", numeroSinantropo).list();
				
		if (sinantropiUfficiali.size() == 0 && sinantropiAutomatici.size() == 0 && sinantropiMc.size()==0 && sinantropiIspra.size()==0) {
			setErrore("Nessun Sinantropo presente con il numero " + " " + numeroSinantropo);				
			gotoPage("sinantropi_default","/jsp/sinantropi/find.jsp");	
			
			
		}
		else if (sinantropiUfficiali.size() > 0)
		{
			
			Sinantropo s = (Sinantropo) sinantropiUfficiali.get(0);				
			req.setAttribute("s", s);	
			//req.setAttribute("taglia", s.getTaglia().getDescription());
			//req.setAttribute("mantello", s.getMantello());
			req.setAttribute("razza", s.getRazza());
			gotoPage("sinantropi_default","/jsp/sinantropi/detail.jsp");
			
		}
		
		else if (sinantropiAutomatici.size() > 0)
		{
			
			Sinantropo s = (Sinantropo) sinantropiAutomatici.get(0);				
			req.setAttribute("s", s);
			//if(s.getTaglia()!=null)
				//req.setAttribute("taglia", s.getTaglia().getDescription());
			//req.setAttribute("mantello", s.getMantello());
			req.setAttribute("razza", s.getRazza());
			gotoPage("sinantropi_default","/jsp/sinantropi/detail.jsp");
			
		}
		
		else if (sinantropiIspra.size() > 0)
		{
			Sinantropo s = (Sinantropo) sinantropiIspra.get(0);				
			req.setAttribute("s", s);
			req.setAttribute("razza", s.getRazza());
			gotoPage("sinantropi_default","/jsp/sinantropi/detail.jsp");
		}
		
		else if (sinantropiMc.size() > 0)
		{
			
			Sinantropo s = (Sinantropo) sinantropiMc.get(0);				
			req.setAttribute("s", s);		
			//req.setAttribute("taglia", s.getTaglia().getDescription());
			//req.setAttribute("mantello", s.getMantello());
			req.setAttribute("razza", s.getRazza());
			gotoPage("sinantropi_default","/jsp/sinantropi/detail.jsp");
			
		}
		
	}
		
}

