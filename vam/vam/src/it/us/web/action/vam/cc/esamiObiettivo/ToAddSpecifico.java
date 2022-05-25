/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiObiettivo;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.ArrayList;
import org.hibernate.criterion.Restrictions;

public class ToAddSpecifico extends GenericAction 
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
		setSegnalibroDocumentazione("esameObiettivo");
	}
	
	@Override
	public void execute() throws Exception
	{

	
			int idApparato		  = interoFromRequest( "idApparato" );
			
			//Recupero Bean Apparati
			LookupEsameObiettivoApparati leoa = (LookupEsameObiettivoApparati) persistence.find(LookupEsameObiettivoApparati.class, idApparato);
			
			req.setAttribute( "apparato", leoa );
			
			System.out.println("Apparato" + leoa.getDescription());
			
			ArrayList<LookupEsameObiettivoTipo> listEsameObiettivoTipo = (ArrayList<LookupEsameObiettivoTipo>) persistence.createCriteria( LookupEsameObiettivoTipo.class )
				.add( Restrictions.eq( "specifico", true ) )	
				.add( Restrictions.eq( "lookupEsameObiettivoApparati.id", leoa.getId() ) )
				.list();
			
			int sizeEsameObiettivoTipo = listEsameObiettivoTipo.size();	
					
			ArrayList<ArrayList<LookupEsameObiettivoEsito>> superList   = new ArrayList();
			ArrayList<ArrayList<LookupEsameObiettivoEsito>> superListFigli   = new ArrayList();
			
			
			ArrayList<String> descrizioniEOTipo 						= new ArrayList();
			
			for (int i = 0; i < sizeEsameObiettivoTipo; i++) {
				
				LookupEsameObiettivoTipo leot = (LookupEsameObiettivoTipo) listEsameObiettivoTipo.get(i);
				
				ArrayList<LookupEsameObiettivoEsito> listEsameObiettivo = (ArrayList<LookupEsameObiettivoEsito>) persistence.createCriteria( LookupEsameObiettivoEsito.class )
				.add( Restrictions.eq( "lookupEsameObiettivoTipo.id", leot.getId() ) )	
				.add( Restrictions.isNull("lookupEsameObiettivoEsito") )
				.list();					
							
				descrizioniEOTipo.add(listEsameObiettivo.get(0).getLookupEsameObiettivoTipo().getDescription());
				superList.add(listEsameObiettivo);
				
			}
			
			
			// La lista di tutti i figli
			ArrayList<LookupEsameObiettivoEsito> listEsameObiettivoFigli = (ArrayList<LookupEsameObiettivoEsito>) persistence.createCriteria( LookupEsameObiettivoEsito.class )			
			.add( Restrictions.isNotNull("lookupEsameObiettivoEsito") )	
			.list();	
					

			req.setAttribute("listEsameObiettivoFigli", listEsameObiettivoFigli);
			req.setAttribute("superList", superList);
			req.setAttribute("descrizioniEOTipo", descrizioniEOTipo);			
			
			gotoPage( "/jsp/vam/cc/esamiObiettivo/add.jsp" );
	}

}

