/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.richiesteIstopatologici;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.lookup.LookupAutopsiaSalaSettoria;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoInteressamentoLinfonodale;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoSedeLesione;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoPrelievo;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTumore;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTumoriPrecedenti;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToEdit extends GenericAction {

	
	public void can() throws AuthorizationException
	{
//		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
//		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("istopatologico");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
		boolean modify = booleanoFromRequest( "modify" );
				
		EsameIstopatologico esame = (EsameIstopatologico) persistence.find(EsameIstopatologico.class, interoFromRequest("idEsame") );
		Animale animale = (Animale) esame.getAnimale();
		req.setAttribute( "animale", animale );
		req.setAttribute( "esame", esame );
				
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
		
		ArrayList<LookupAutopsiaSalaSettoria>		listAutopsiaSalaSettoria    = (ArrayList<LookupAutopsiaSalaSettoria>) persistence.createCriteria( LookupAutopsiaSalaSettoria.class )
				.add( Restrictions.eq( "enabled", true ) )
				.add( Restrictions.eq( "esameRiferimento", "Istopatologico" ) )
				.addOrder( Order.asc( "esterna" ) )
				.addOrder( Order.asc( "level" ) )
				.addOrder( Order.asc( "description" ) ).list();
		
		req.setAttribute( "listAutopsiaSalaSettoria", listAutopsiaSalaSettoria );
		
		req.setAttribute( "modify", modify );
		req.setAttribute( "interessamentoLinfonodales", interessamentoLinfonodales );
		req.setAttribute( "tipoPrelievos", tipoPrelievos );
		req.setAttribute( "tumores", tumores );
		req.setAttribute( "tumoriPrecedentis", tumoriPrecedentis );
		req.setAttribute( "sediLesioniPadre", sediLesioniPadre );
		req.setAttribute( "tipoDiagnosis", tipoDiagnosis );
		req.setAttribute( "whoUmanaPadre", whoUmanaPadre );
		
		gotoPage("/jsp/vam/richiesteIstopatologici/add.jsp");
	}
}

