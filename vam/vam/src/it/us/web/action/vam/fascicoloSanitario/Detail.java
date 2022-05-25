/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.fascicoloSanitario;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

public class Detail extends GenericAction implements Specie
{
	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "FASCICOLO_SANITARIO", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("fascicoloSanitario");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{	
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		int id = interoFromRequest("id");
		FascicoloSanitario fascicoloSanitario = (FascicoloSanitario)persistence.find (FascicoloSanitario.class, id );
		
		//Se il cane non è morto senza mc bisogna leggere in bdr le info sulla morte e stato attuale
		ServicesStatus status = new ServicesStatus();
		
		Cane cane = null;
		Gatto gatto = null;
		
		if(!fascicoloSanitario.getAnimale().getDecedutoNonAnagrafe())
		{
			if (fascicoloSanitario.getAnimale().getLookupSpecie().getId() == CANE ) 
			{
				cane = CaninaRemoteUtil.findCane(fascicoloSanitario.getAnimale().getIdentificativo(), utente, status, connection,req);
				RegistrazioniCaninaResponse res = CaninaRemoteUtil.getInfoDecesso( cane );
				req.setAttribute("res", res);
			}
			else if (fascicoloSanitario.getAnimale().getLookupSpecie().getId() == GATTO) 
			{
				gatto = FelinaRemoteUtil.findGatto(fascicoloSanitario.getAnimale().getIdentificativo(), utente, status, connection,req);
				RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( gatto );
				req.setAttribute("res", rfr);
			}
			else if (fascicoloSanitario.getAnimale().getLookupSpecie().getId() == SINANTROPO) 
			{
				RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, fascicoloSanitario.getAnimale());
				req.setAttribute("res", rsr);
			}
		}
		
		req.setAttribute( "fascicoloSanitario", fascicoloSanitario );
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(fascicoloSanitario.getAnimale(), cane, gatto, persistence, utente, status, connection,req));

		gotoPage("/jsp/vam/fascicoloSanitario/detail.jsp");
			
	}
}
