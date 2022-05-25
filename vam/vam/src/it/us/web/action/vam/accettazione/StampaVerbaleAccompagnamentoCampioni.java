/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.SinantropoDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.dao.vam.AttivitaBdrDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;


public class StampaVerbaleAccompagnamentoCampioni extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@Override
	public void execute() throws Exception
	{
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		ServicesStatus status = new ServicesStatus();
		
		Accettazione accettazione = AccettazioneDAO.getAccettazione(interoFromRequest( "idAccettazione" ), connection);
		req.setAttribute( "accettazione", accettazione );
		
		int specie = accettazione.getAnimale().getLookupSpecie().getId();
		Gatto gatto = null;
		Cane cane = null;
		if(!accettazione.getAnimale().getDecedutoNonAnagrafe())
		{
			if (specie == CANE ) 
			{
				cane = CaninaRemoteUtil.findCane(accettazione.getAnimale().getIdentificativo(), utente, status, connectionBdu, req);
				HashSet<AttivitaBdr> attivitas = AttivitaBdrDAO.getAttivitaBdrCompletata(51, accettazione.getId(), connection);
				Integer idBdr = null;
				if(!attivitas.isEmpty())
					idBdr = ((AttivitaBdr)attivitas.iterator().next()).getIdRegistrazioneBdr();
				req.setAttribute("comunecattura", CaninaRemoteUtil.getComuneCatturaRicattura(accettazione.getAnimale().getIdentificativo(), idBdr, utente, connectionBdu, req, accettazione.getData()));
				
			}
			else if (specie == GATTO) 
			{
				gatto = FelinaRemoteUtil.findGatto(accettazione.getAnimale().getIdentificativo(), utente, status, connectionBdu, req);
				HashSet<AttivitaBdr> attivitas = AttivitaBdrDAO.getAttivitaBdrCompletata(51, accettazione.getId(), connection);
				Integer idBdr = null;
				if(!attivitas.isEmpty())
					idBdr = ((AttivitaBdr)attivitas.iterator().next()).getIdRegistrazioneBdr();
				req.setAttribute("comunecattura", CaninaRemoteUtil.getComuneCatturaRicattura(accettazione.getAnimale().getIdentificativo(), idBdr, utente, connectionBdu, req, accettazione.getData()));
				
			}
			else if (specie == SINANTROPO) 
			{
				SinantropoDAO s = new SinantropoDAO();
				Catture c = SinantropoUtil.getLastCattura(s.getSinantropoByNumero(connection, accettazione.getAnimale().getIdentificativo()));
				req.setAttribute("comunecattura", c.getComuneCattura().getDescription());
			}
		}
		if(accettazione.getAnimale().getDecedutoNonAnagrafe())
		{
			req.setAttribute("comunecattura", accettazione.getAnimale().getComuneRitrovamento().getDescription());
		}
		
		
		ArrayList<LookupOperazioniAccettazione> operazioni = LookupOperazioniAccettazioneDAO.getOperazioniCovid(connection);
		req.setAttribute("operazioni", operazioni);
		
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(accettazione.getAnimale(), cane, gatto, persistence, utente, status, connectionBdu, req));
		
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		
		gotoPage( "popup", "/jsp/vam/accettazione/stampaVerbaleAccompagnamentoCampioni.jsp" );
	} 

}

