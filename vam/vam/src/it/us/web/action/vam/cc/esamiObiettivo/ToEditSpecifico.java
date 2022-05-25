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
import it.us.web.bean.vam.EsameObiettivoPatologieCongenite;
import it.us.web.bean.vam.EsameObiettivoSintoPatologia;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoInsorgenzaSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoPeriodoInsorgenzaSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToEditSpecifico extends GenericAction 
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
			req.setAttribute("idApparato", idApparato);
			
			
			
			/*Nel caso la cartella clinica contiene già degli esami obiettivo
			 * lo scopo è quello di visualizzare nella pagina di edit gli esiti già
			 * checkati in precedenza.*/
			if(cc.getEsameObiettivos().size()>0) {	
				
				ArrayList<EsameObiettivoEsito> superListChecked = new ArrayList();
							
				req.setAttribute( "dataEsame", cc.getEsameObiettivos().iterator().next().getDataEsameObiettivo());
				
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
			
			
			
			/**
			 * Gestione sezione SINTOMATOLOGIA/PATOLOGIA
			 */
			 //caricamento elenco possibili sintomi
			ArrayList<LookupEsameObiettivoSintomi> sintomi = (ArrayList<LookupEsameObiettivoSintomi>) persistence.createCriteria( LookupEsameObiettivoSintomi.class )
				.add( Restrictions.eq( "apparato", leoa ) )
				.addOrder( Order.asc( "level" ) )
				.addOrder( Order.asc( "description" ) )
				.list();
				
			//caricamento elenco possibili insorgenze sintomi
			ArrayList<LookupEsameObiettivoInsorgenzaSintomi> insorgenze = (ArrayList<LookupEsameObiettivoInsorgenzaSintomi>) persistence.createCriteria( LookupEsameObiettivoInsorgenzaSintomi.class )
				.add( Restrictions.eq( "apparato", leoa ) )
				.addOrder( Order.asc( "level" ) )
				.addOrder( Order.asc( "description" ) )
				.list();
			
			//caricamento elenco possibili periodi insorgenze sintomi
			ArrayList<LookupEsameObiettivoPeriodoInsorgenzaSintomi> periodoInsorgenze = (ArrayList<LookupEsameObiettivoPeriodoInsorgenzaSintomi>) persistence
				.createCriteria( LookupEsameObiettivoPeriodoInsorgenzaSintomi.class )
					.add( Restrictions.eq( "apparato", leoa ) )
					.addOrder( Order.asc( "level" ) )
					.addOrder( Order.asc( "description" ) )
				.list();
			
			//caricamento record SINTOMATOLOGIA/PATOLOGIA eventualmente già presente
			ArrayList<EsameObiettivoSintoPatologia> sintopat = (ArrayList<EsameObiettivoSintoPatologia>) persistence.createCriteria( EsameObiettivoSintoPatologia.class )
				.add( Restrictions.eq( "cartellaClinica", cc ) )
				.add( Restrictions.eq( "apparato", leoa ) )
				.list();
			
			//caricamento record PATOLOGIE CONGENITE eventualmente già presente
			List<EsameObiettivoPatologieCongenite> patologieCongenite = persistence.createCriteria( EsameObiettivoPatologieCongenite.class )
				.add( Restrictions.eq( "cartellaClinica", cc ) )
				.add( Restrictions.eq( "apparato", leoa ) )
				.list();
			
			req.setAttribute( "sintomi", sintomi );
			req.setAttribute( "insorgenze", insorgenze );
			req.setAttribute( "periodoInsorgenze", periodoInsorgenze );
			if( sintopat.size() > 0 )
			{
				req.setAttribute( "sintopatologia", sintopat.get( 0 ) );
			}
			if( patologieCongenite.size() > 0 )
			{
				req.setAttribute( "patologieCongenite", patologieCongenite.get( 0 ) );
			}

			gotoPage( "/jsp/vam/cc/esamiObiettivo/edit.jsp" );
	}

}

