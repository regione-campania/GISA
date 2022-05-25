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
import it.us.web.dao.vam.CartellaClinicaDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Restrictions;

public class ToDetailGenerale extends GenericAction 
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("esameObiettivo");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		
		ArrayList<LookupEsameObiettivoTipo> listEsameObiettivoTipo = (ArrayList<LookupEsameObiettivoTipo>) persistence.createCriteria( LookupEsameObiettivoTipo.class )
			.add( Restrictions.eq( "specifico", false ) )	
			.list();
		
		ArrayList<EsameObiettivo> esameObiettivos = CartellaClinicaDAO.getEsameObiettivos(cc.getId(),connection);
		req.setAttribute("esameObiettivos",esameObiettivos);
		
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
		
		
		/* Nel caso la cartella clinica contiene già degli esami obiettivo
		 * lo scopo è quello di visualizzare nella pagina di edit gli esiti già
		 * checkati in precedenza.*/
		if(CartellaClinicaDAO.getEsamiObiettivoApparato( null, esameObiettivos).size()>0) {	
			
			ArrayList<EsameObiettivoEsito> superListChecked = new ArrayList();
						
			//req.setAttribute( "dataEsame", cc.getEsameObiettivos().iterator().next().getDataEsameObiettivo());
			
			Iterator eoList = CartellaClinicaDAO.getEsamiObiettivoApparato( null, esameObiettivos ).iterator();
			
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
		
		
		if(CartellaClinicaDAO.getEsamiObiettivoApparato( null, esameObiettivos ).size()>0) {
			req.setAttribute( "dataEsame", CartellaClinicaDAO.getEsamiObiettivoApparato( null, esameObiettivos ).iterator().next().getDataEsameObiettivo());
		}
		else if (CartellaClinicaDAO.getEsamiObiettivoApparato( null, esameObiettivos ).size()==0 && cc.getFebbres().size()>0) {
			req.setAttribute( "dataEsame", cc.getFebbres().iterator().next().getEntered());
		}
		
			
		
		if( CartellaClinicaDAO.getEsamiObiettivoApparato( null, esameObiettivos ).size() > 0 || cc.getFebbres().size() > 0 )
			gotoPage( "/jsp/vam/cc/esamiObiettivo/detailGenerale.jsp" );
		else
			gotoPage( "/jsp/vam/cc/esamiObiettivo/edit.jsp" );
	}

}

