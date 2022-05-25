/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.SuperUtenteAll;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AccettazioneNoH;
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
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.dao.vam.AccettazioneDAONoH;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;


public class Detail extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	@Override
	public void execute() throws Exception
	{
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		
		Accettazione accettazione = AccettazioneDAO.getAccettazione(interoFromRequest("id"), connection);
		AccettazioneNoH accettazioneNoH = AccettazioneDAONoH.getAccettazione(interoFromRequest("id"), connection);
		
		HashSet<SuperUtenteAll> personaleAslSelezionato = AccettazioneDAO.getPersonaleAsl(accettazione.getId(), connection);
		req.setAttribute("personaleAslSelezionato", personaleAslSelezionato);
		
		//Se il cane non è morto senza mc bisogna leggere in bdr le info sulla morte e stato attuale
		ServicesStatus status = new ServicesStatus();
		int specie = accettazione.getAnimale().getLookupSpecie().getId();
		Gatto gatto = null;
		Cane cane = null;
		if(!accettazione.getAnimale().getDecedutoNonAnagrafe())
		{
			if (specie == CANE ) 
			{
				cane = CaninaRemoteUtil.findCane(accettazione.getAnimale().getIdentificativo(), utente, status, connectionBdu,req);
				RegistrazioniCaninaResponse res	= AnimaliUtil.fetchDatiDecessoCane(cane);
				req.setAttribute("res", res);
			}
			else if (specie == GATTO) 
			{
				gatto = FelinaRemoteUtil.findGatto(accettazione.getAnimale().getIdentificativo(), utente, status, connectionBdu,req);
				RegistrazioniFelinaResponse rfr	= AnimaliUtil.fetchDatiDecessoGatto(gatto);
				req.setAttribute("res", rfr);
			}
			else if (specie == SINANTROPO) 
			{
				RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, accettazione.getAnimale());
				req.setAttribute("res", rsr);
				req.setAttribute("fuoriAsl", false);
				req.setAttribute("versoAssocCanili", false);
			}
			
			if(specie==CANE || specie==GATTO)
				RegistrazioniUtil.sincronizzaDaBduNoH(accettazioneNoH, null, null, persistence, connectionBdu, utente, false, req, connection);
		}
		req.setAttribute( "idOpsBdr", 		IdOperazioniBdr.getInstance() );
		req.setAttribute( "idRichiesteVarie", IdRichiesteVarie.getInstance() );
		req.setAttribute( "accettazione", accettazione );
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		
		ArrayList<Integer> operazioniRichiesteId = new ArrayList<Integer>();
		Iterator<LookupOperazioniAccettazione> iter = accettazione.getOperazioniRichieste().iterator();
		while(iter.hasNext())
		{
			operazioniRichiesteId.add(iter.next().getId());
		}
		
		if(operazioniRichiesteId.contains(IdRichiesteVarie.attivitaEsterne) && accettazione.getAttivitaEsterna().getId()==3)
			req.setAttribute("stampaModello13A", "true");
		else
			req.setAttribute("stampaModello13A", "false");
		
		if(operazioniRichiesteId.contains(IdRichiesteVarie.smaltimentoCarogna))
			req.setAttribute("smaltimentoCarognaDaFare", "true");
		else
			req.setAttribute("smaltimentoCarognaDaFare", "false");
		
		if(operazioniRichiesteId.contains(IdRichiesteVarie.esameNecroscopico))
			req.setAttribute("necroscopicoDaFare", "true");
		else
			req.setAttribute("necroscopicoDaFare", "false");
		
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(accettazione.getAnimale(), cane, gatto, persistence, utente, status, connectionBdu,req));
		gotoPage( "/jsp/vam/accettazione/detail.jsp" );
	} 

}
