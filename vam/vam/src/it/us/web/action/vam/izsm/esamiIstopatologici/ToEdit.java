/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.izsm.esamiIstopatologici;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.lookup.LookupAutopsiaSalaSettoria;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.AnimaliUtil;

import java.sql.Connection;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToEdit extends GenericAction {

	
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

	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);	
		String numeroEsame = stringaFromRequest("numeroEsame");
		
		//Recupero di un esame istopatologico a partire dal numero
		ArrayList<EsameIstopatologico> esami = (ArrayList<EsameIstopatologico>) persistence.getNamedQuery("GetEsameIstopatologicoByNumero").setString("numeroEsame", numeroEsame).list();
					
		if (esami.size() == 0) {
			
			setMessaggio("Nessuna Esame con il numero inserito");				
			goToAction(new ToFind());
			
		}
		else {
			
			EsameIstopatologico esame = (EsameIstopatologico) esami.get(0);
			
			session.setAttribute("cc", esame.getCartellaClinica());
			session.setAttribute("idCc", esame.getCartellaClinica().getId());
			cc = esame.getCartellaClinica();
			
			if(esame.getTipoDiagnosi().getId()!=3)
				esame.setDiagnosiNonTumorale("");
			
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
			
		
			
			if (esame.getOutsideCC()==true){
				req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(esame.getAnimale(), persistence, utente, new ServicesStatus(), connection,req));
				req.setAttribute("animale", esame.getAnimale());
			}
			else {
				req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(esame.getCartellaClinica().getAccettazione().getAnimale(), persistence, utente, new ServicesStatus(), connection,req));
				req.setAttribute("animale", esame.getCartellaClinica().getAccettazione().getAnimale());
				
				session.setAttribute("cc", esame.getCartellaClinica());
				session.setAttribute("idCc", esame.getCartellaClinica().getId());
				cc = esame.getCartellaClinica();
			}
			
			req.setAttribute( "listAutopsiaSalaSettoria", listAutopsiaSalaSettoria );						
			req.setAttribute( "esame", esame );	
			req.setAttribute( "tipoDiagnosis", tipoDiagnosis );
			req.setAttribute( "whoUmanaPadre", whoUmanaPadre );
									
			gotoPage("/jsp/vam/izsm/esamiIstopatologici/edit.jsp" );
			
		}
				
			
	}
}
