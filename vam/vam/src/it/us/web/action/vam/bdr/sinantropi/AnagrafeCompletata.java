/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.bdr.sinantropi;

import java.sql.Connection;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.vam.Animale;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.AnimaliUtil;

public class AnagrafeCompletata extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
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
		HashMap<String, Object> fetchAnimale = AnimaliUtil.fetchAnimale( identificativo, persistence, utente, status, connection ,req);
		Animale animale = (Animale)fetchAnimale.get("animale");
		if(animale!=null)
			animale.setClinicaChippatura(utente.getClinica());
		
		if( status.isAllRight() )
		{
			if( animale == null ) //animale non presente in locale nè nelle bdr remote -> va inserito in bdr
			{
				setErrore("Non hai inserito in BDR l'animale per il quale si sta procedendo all'accettazione");
				gotoPage( "/jsp/vam/bdr/inserimentoAnagrafe_sceltaSistema.jsp" );
			}
			else
			{
				//flagAnagrafe=on serve a flaggare in automatico l'operazione "Inserimento Anagrafe"
				redirectTo( "vam.accettazione.ToAdd.us?idAnimale=" + animale.getId() + "&flagAnagrafe=on" );
			}
		}
		else
		{
			setErrore( "Si è verificato un errore di comunicazione con la BDR di riferimento: " + status.getError() );
			gotoPage( "/jsp/errore/errore.jsp" );
		}
	}
	
}




