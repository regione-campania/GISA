/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.diagnosticaImmagini.ecoAddome;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EcoAddome;
import it.us.web.bean.vam.EcoAddomeEsito;
import it.us.web.bean.vam.lookup.LookupEcoAddomeTipo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.ArrayList;

import org.hibernate.criterion.Restrictions;


public class ToEdit extends GenericAction 
{

	@Override
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("ecoAddome");
	}
	
	@Override
	public void execute() throws Exception
	{
			int idEcoAddome = interoFromRequest("idEcoAddome");
		
			//Recupero Bean EcoAddome
			EcoAddome ecoAddome = (EcoAddome) persistence.find(EcoAddome.class, idEcoAddome);
		
			//Recupero Lista Esiti EcoAddome
			ArrayList<EcoAddomeEsito> ecoAddomeEsitos = (ArrayList<EcoAddomeEsito>) persistence.createCriteria( EcoAddomeEsito.class ).add( Restrictions.eq( "ecoAddome.id", idEcoAddome ) ).list();

			//Recupero tipi di esami di Eco-Addome
			ArrayList<LookupEcoAddomeTipo> ecoAddomeTipi = (ArrayList<LookupEcoAddomeTipo>) persistence.findAll(LookupEcoAddomeTipo.class);

		
			req.setAttribute( "ecoAddomeTipi", ecoAddomeTipi);
			req.setAttribute( "ecoAddome", ecoAddome);
			req.setAttribute( "ecoAddomeEsitos", ecoAddomeEsitos);
		
			gotoPage( "/jsp/vam/cc/diagnosticaImmagini/ecoAddome/edit.jsp" );
	}

}
