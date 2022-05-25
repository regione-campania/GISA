/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.richiesteIstopatologici;

import it.us.web.action.GenericAction;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class Find extends GenericAction {

	
	public void can() throws AuthorizationException
	{
//		BGuiView gui = GuiViewDAO.getView( "CC", "LIST", "MAIN" );
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
			
		String numeroEsame = stringaFromRequest("numeroEsame");
		
		//Recupero di un esame istopatologico a partire dal numero
		ArrayList<EsameIstopatologico> esami = (ArrayList<EsameIstopatologico>) persistence.getNamedQuery("GetEsameIstopatologicoByNumero").setString("numeroEsame", numeroEsame).list();
					
		if (esami.size() == 0) {
			
			setErrore("Nessuna Esame con il numero inserito");				
			gotoPage("/jsp/vam/richiesteIstopatologici/findEsame.jsp");
			
		}		
		else {
			
			EsameIstopatologico esame = (EsameIstopatologico) esami.get(0);
			
			if (esame.getOutsideCC()) {			
				
				Animale animale = (Animale) esame.getAnimale();
				req.setAttribute( "animale", animale );
				req.setAttribute( "esame", esame );													
				gotoPage("/jsp/vam/richiesteIstopatologici/detail.jsp" );
			}
			else {
				setErrore("Accesso negato all'esame con numero " + " " + esame.getNumero());				
				gotoPage("/jsp/vam/richiesteIstopatologici/findEsame.jsp");
			}
			
		}
				
			
	}
}
