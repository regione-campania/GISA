/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.sinantropi.lookup.LookupSpecieSinantropi;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToAddDecedutoNonAnagrafe extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		/*
		 * se giunge un animale deceduto senza che questo sia presente nelle anagrafi regionali, si inseriscono i dati dell'animale
		 * in banca dati locale (viene assegnato un identificativo fittizio) e poi si procede con l'accettazione
		 */
		
		ArrayList<LookupSpecie>		specie		= (ArrayList<LookupSpecie>) persistence.createCriteria( LookupSpecie.class ).addOrder( Order.asc( "level" ) ).list();
		
		ArrayList<LookupSinantropiEta> listEta = (ArrayList<LookupSinantropiEta>) persistence.createCriteria( LookupSinantropiEta.class )
				.list();
		
/*		ArrayList<LookupComuni> listComuniBN = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "bn", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupComuni> listComuniNA = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "na", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupComuni> listComuniSA = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "sa", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupComuni> listComuniAV = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "av", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupComuni> listComuniCE = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "ce", true ) )
		.addOrder( Order.asc( "level" ) )
		.list(); */
				
		req.setAttribute("listEta", listEta);
/*		req.setAttribute("listComuniBN", listComuniBN);
		req.setAttribute("listComuniNA", listComuniNA);
		req.setAttribute("listComuniSA", listComuniSA);
		req.setAttribute("listComuniCE", listComuniCE);
		req.setAttribute("listComuniAV", listComuniAV); */		
		
		ArrayList<LookupComuni> comuni = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class ).addOrder( Order.asc( "description" ) ).list();

		
//		ArrayList<LookupTipiRichiedente> richiedenti				= (ArrayList<LookupTipiRichiedente>) persistence.createCriteria( LookupTipiRichiedente.class )
//																		.add( Restrictions.eq( "forzaPubblica", false ) )
//																		.addOrder( Order.asc( "level" ) )
//																		.list();
//		ArrayList<LookupTipiRichiedente> richiedentiForzaPubblica	= (ArrayList<LookupTipiRichiedente>) persistence.createCriteria( LookupTipiRichiedente.class )
//																		.add( Restrictions.eq( "forzaPubblica", true ) )
//																		.addOrder( Order.asc( "level" ) )
//																		.list();
//		
//		ArrayList<LookupAsl> asl = (ArrayList<LookupAsl>) persistence.createCriteria( LookupAsl.class ).addOrder( Order.asc( "level" ) ).list();
//
//		ArrayList<LookupAssociazioni> associazioni = (ArrayList<LookupAssociazioni>) persistence.createCriteria( LookupAssociazioni.class ).addOrder( Order.asc( "level" ) ).list();
//		
//		req.setAttribute( "richiedenti", richiedenti );
//		req.setAttribute( "richiedentiForzaPubblica", richiedentiForzaPubblica );
//		req.setAttribute( "asl", asl );
//		req.setAttribute( "associazioni", associazioni );
		
		
		//Parte inerente i dati della morte
/*		ArrayList<LookupCMI> listCMI = (ArrayList<LookupCMI>) persistence.createCriteria( LookupCMI.class )
		.addOrder( Order.asc( "level" ) )
		.list();
		req.setAttribute("listCMI", listCMI); */
		
		//Fine parte inerente i dati della morte
		
		ArrayList<LookupSpecieSinantropi> listUccelli = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
				.add( Restrictions.eq( "uccello", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();	
				
		ArrayList<LookupSpecieSinantropi> listMammiferi = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
				.add( Restrictions.eq( "mammifero", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
				
		ArrayList<LookupSpecieSinantropi> listRettiliAnfibi = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
				.add( Restrictions.eq( "rettileAnfibio", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
		
		ArrayList<LookupSpecieSinantropi> listUccelliZ = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
				.add( Restrictions.eq( "uccelloZ", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();	
				
		ArrayList<LookupSpecieSinantropi> listMammiferiZ = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
				.add( Restrictions.eq( "mammiferoZ", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
				
		ArrayList<LookupSpecieSinantropi> listRettiliAnfibiZ = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
				.add( Restrictions.eq( "rettileAnfibioZ", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
		
		ArrayList<LookupSpecieSinantropi> listSelaci = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
				.add( Restrictions.eq( "selaci", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();	
				
				ArrayList<LookupSpecieSinantropi> listMammiferiCetacei = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
				.add( Restrictions.eq( "mammiferoCetaceo", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
				
				ArrayList<LookupSpecieSinantropi> listRettiliTestuggini = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
				.add( Restrictions.eq( "rettileTestuggine", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
				
		req.setAttribute("listUccelli", listUccelli);
		req.setAttribute("listMammiferi", listMammiferi);
		req.setAttribute("listRettiliAnfibi", listRettiliAnfibi);
		req.setAttribute("listUccelliZ", listUccelliZ);
		req.setAttribute("listMammiferiZ", listMammiferiZ);
		req.setAttribute("listRettiliAnfibiZ", listRettiliAnfibiZ);
		req.setAttribute("listSelaci", listSelaci);
		req.setAttribute("listMammiferiCetacei", listMammiferiCetacei);
		req.setAttribute("listRettiliTestuggini", listRettiliTestuggini);
		
		req.setAttribute( "comuni", comuni );
		req.setAttribute( "specie", specie );
		
		if( utente.getClinica() != null )
		{
			persistence.update( utente.getClinica() );
		}
		
		gotoPage( "/jsp/vam/accettazione/addDecedutoNonAnagrafe.jsp" );
	}

}
