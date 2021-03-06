/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiObiettivo;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameObiettivo;
import it.us.web.bean.vam.EsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.hibernate.criterion.Restrictions;

public class ToEditGenerale extends GenericAction 
{

	@Override
	public void can() throws AuthorizationException
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

			
			ArrayList<LookupEsameObiettivoTipo> listEsameObiettivoTipo = (ArrayList<LookupEsameObiettivoTipo>) persistence.createCriteria( LookupEsameObiettivoTipo.class )
				.add( Restrictions.eq( "specifico", false ) )	
				.list();
			
			int sizeEsameObiettivoTipo = listEsameObiettivoTipo.size();	
					
			ArrayList<ArrayList<LookupEsameObiettivoEsito>> superList   = new ArrayList();
							
			ArrayList<String> descrizioniEOTipo 						= new ArrayList();
			
			for (int i = 0; i < sizeEsameObiettivoTipo; i++) 
			{
				LookupEsameObiettivoTipo leot = (LookupEsameObiettivoTipo) listEsameObiettivoTipo.get(i);
				
				ArrayList<LookupEsameObiettivoEsito> listEsameObiettivo = (ArrayList<LookupEsameObiettivoEsito>) persistence.createCriteria( LookupEsameObiettivoEsito.class )
				.add( Restrictions.eq( "lookupEsameObiettivoTipo.id", leot.getId() ) )
				.add( Restrictions.isNull("lookupEsameObiettivoEsito") )
				.list();
				
				descrizioniEOTipo.add(listEsameObiettivo.get(0).getLookupEsameObiettivoTipo().getDescription());
				superList.add(listEsameObiettivo);
			}
			
			// La lista di tutti i padri
			ArrayList<LookupEsameObiettivoEsito> listEsameObiettivoFigliList = (ArrayList<LookupEsameObiettivoEsito>) persistence.createCriteria( LookupEsameObiettivoEsito.class )			
			.add( Restrictions.isNotNull("lookupEsameObiettivoEsito") )	
			.list();	
			
			HashMap<Integer, Integer> listEsameObiettivoPadri = new HashMap<Integer,Integer>();
			Iterator<LookupEsameObiettivoEsito> listEsameObiettivoFigliIter = listEsameObiettivoFigliList.iterator();
			Integer id = null;
			while(listEsameObiettivoFigliIter.hasNext())
			{
				id = listEsameObiettivoFigliIter.next().getLookupEsameObiettivoEsito().getId();
				listEsameObiettivoPadri.put(id, id);
			}
			
			req.setAttribute("listEsameObiettivoPadri", listEsameObiettivoPadri);
			req.setAttribute("listEsameObiettivoFigliList", listEsameObiettivoFigliList);
			req.setAttribute("superList", superList);
			req.setAttribute("descrizioniEOTipo", descrizioniEOTipo);
			req.setAttribute("idApparato", 0);
			
			
			/* Nel caso la cartella clinica contiene gi? degli esami obiettivo
			 * lo scopo ? quello di visualizzare nella pagina di edit gli esiti gi?
			 * checkati in precedenza.*/
			if(cc.getEsameObiettivos().size()>0) 
			{	
				
				ArrayList<EsameObiettivoEsito> superListChecked = new ArrayList();
							
				//req.setAttribute( "dataEsame", cc.getEsameObiettivos().iterator().next().getDataEsameObiettivo());
				
				Iterator eoList = cc.getEsameObiettivos().iterator();
				
				while(eoList.hasNext()) {
					EsameObiettivo eo = (EsameObiettivo)eoList.next();
					if(eo.getEsameObiettivoEsitos().size()>0) {
						Iterator eoeList = eo.getEsameObiettivoEsitos().iterator();
						while(eoeList.hasNext()) {
							EsameObiettivoEsito eoeRe = (EsameObiettivoEsito) eoeList.next();						
							superListChecked.add(eoeRe);
						}
					}
				}
				req.setAttribute("superListChecked", superListChecked);
				
			}
			
			if (cc.getFebbres().size()>0) {
				req.setAttribute("isFebbre", true);
			}
			else {
				req.setAttribute("isFebbre", false);
			}
			
			
			if(cc.getEsameObiettivos().size()>0) {
				req.setAttribute( "dataEsame", cc.getEsameObiettivos().iterator().next().getDataEsameObiettivo());
			}
			else if (cc.getEsameObiettivos().size()==0 && cc.getFebbres().size()>0) {
				req.setAttribute( "dataEsame", cc.getFebbres().iterator().next().getEntered());
			}
			
			gotoPage( "/jsp/vam/cc/esamiObiettivo/edit.jsp" );
	}

}

