/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.richiesteIstopatologici;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoInteressamentoLinfonodale;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoSedeLesione;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoPrelievo;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTumore;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTumoriPrecedenti;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.AnimaliUtil;

public class FindAnimale extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
//		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "DETAIL", "MAIN" );
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

	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		String identificativo = stringaFromRequest( "identificativo" );
		
		ServicesStatus status = new ServicesStatus();
		HashMap<String, Object> fetchAnimale = AnimaliUtil.fetchAnimale( identificativo, persistence, utente, status, connection,req );
		Animale animale = (Animale)fetchAnimale.get("animale");
		
		if( animale == null ) 
		{
			if( status.isAllRight() )
			{
				setErrore("Nessun animale presente con l'identificativo " + identificativo);
			}
			else
			{
				setErrore( "Si è verificato un errore di comunicazione con la BDR di riferimento: " + status.getError() );
			}
			redirectTo("Index.us");				
			
		}
		else 
		{
			
			ArrayList<LookupEsameIstopatologicoInteressamentoLinfonodale> interessamentoLinfonodales
			= (ArrayList<LookupEsameIstopatologicoInteressamentoLinfonodale>) persistence.findAll(LookupEsameIstopatologicoInteressamentoLinfonodale.class);

			ArrayList<LookupEsameIstopatologicoTipoPrelievo> tipoPrelievos
			= (ArrayList<LookupEsameIstopatologicoTipoPrelievo>)persistence.createCriteria( LookupEsameIstopatologicoTipoPrelievo.class )
				.add( Restrictions.eq("deceduto", ((cc.getCcMorto()!=null && cc.getCcMorto())?(true):(false))) )
				.list();
	
			ArrayList<LookupEsameIstopatologicoTumore> tumores
				= (ArrayList<LookupEsameIstopatologicoTumore>) persistence.findAll(LookupEsameIstopatologicoTumore.class);
			
			ArrayList<LookupEsameIstopatologicoTumoriPrecedenti> tumoriPrecedentis
				= (ArrayList<LookupEsameIstopatologicoTumoriPrecedenti>) persistence.findAll(LookupEsameIstopatologicoTumoriPrecedenti.class);
			
			ArrayList<LookupEsameIstopatologicoSedeLesione> sediLesioniPadre
				= (ArrayList<LookupEsameIstopatologicoSedeLesione>)persistence.createCriteria( LookupEsameIstopatologicoSedeLesione.class )
					.add( Restrictions.isNull( "padre" ) )
					.addOrder( Order.asc( "level" ) )
					.list();
			
			ArrayList<LookupEsameIstopatologicoTipoDiagnosi> tipoDiagnosis
				= (ArrayList<LookupEsameIstopatologicoTipoDiagnosi>) persistence.findAll(LookupEsameIstopatologicoTipoDiagnosi.class);
			
			ArrayList<LookupEsameIstopatologicoWhoUmana> whoUmanaPadre
				= (ArrayList<LookupEsameIstopatologicoWhoUmana>)persistence.createCriteria( LookupEsameIstopatologicoWhoUmana.class )
					.add( Restrictions.isNull( "padre" ) )
					.addOrder( Order.asc( "level" ) )
					.list();
			
			ArrayList<LookupAlimentazioni> listAlimentazioni = (ArrayList<LookupAlimentazioni>) persistence.createCriteria( LookupAlimentazioni.class )
				.addOrder( Order.asc( "level" ) )
				.list();
			
			ArrayList<LookupHabitat> listHabitat = (ArrayList<LookupHabitat>) persistence.createCriteria( LookupHabitat.class )
				.addOrder( Order.asc( "level" ) )
				.list();
			
						
			
			req.setAttribute( "interessamentoLinfonodales", interessamentoLinfonodales );
			req.setAttribute( "tipoPrelievos", tipoPrelievos );
			req.setAttribute( "tumores", tumores );
			req.setAttribute( "tumoriPrecedentis", tumoriPrecedentis );
			req.setAttribute( "sediLesioniPadre", sediLesioniPadre );
			req.setAttribute( "tipoDiagnosis", tipoDiagnosis );
			req.setAttribute( "whoUmanaPadre", whoUmanaPadre );
			
			req.setAttribute( "animale", animale );
			
			req.setAttribute("listAlimentazioni", listAlimentazioni);		
			req.setAttribute("listHabitat", listHabitat);	
			
			
			boolean liberoProfessionista = booleanoFromRequest("liberoProfessionista");
			req.setAttribute("liberoProfessionista", liberoProfessionista);
			
			if (liberoProfessionista) {
				gotoPage("public", "/jsp/vam/richiesteIstopatologici/add.jsp");				
			}
			else {
				gotoPage( "/jsp/vam/richiesteIstopatologici/add.jsp");
			}
		}
		
	}

}
