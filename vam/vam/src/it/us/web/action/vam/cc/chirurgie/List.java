/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.chirurgie;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdOperazioniInBdr;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.RegistrazioniUtil;


public class List extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("ecoCuore");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{

		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnection(connection);
		
		
		LookupOperazioniAccettazione operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.sterilizzazione);
		
		req.setAttribute("operazione", operazione);	
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		req.setAttribute("regEffettuabili",AnimaliUtil.findRegistrazioniEffettuabili( connection,  cc.getAccettazione().getAnimale(), utente, connectionBdu,req ));
		req.setAttribute("animaleAnagrafica", AnimaliUtil.getAnagrafica(cc.getAccettazione().getAnimale(), persistence, utente, new ServicesStatus(), connectionBdu,req));
		req.setAttribute("idRegBdr", RegistrazioniUtil.getIdRegUnivoca(cc.getAccettazione().getAnimale().getIdentificativo(), connectionBdu, IdOperazioniInBdr.sterilizzazione,req));
		gotoPage("/jsp/vam/cc/chirurgie/list.jsp");
	}
}



