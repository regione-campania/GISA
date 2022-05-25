/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazioneMultipla;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.accettazione.FindAnimale;
import it.us.web.action.vam.accettazione.List;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleAnagrafica;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAssociazioni;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdOperazioniDM;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.IdTipiRichiedente;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.dwr.vam.accettazione.Test;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

public class ToAdd extends GenericAction  implements Specie
{

	final static Logger logger = LoggerFactory.getLogger(ToAdd.class);
	
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
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		
		String[] mcAnagrafati = req.getParameter("mcAnagrafati").split(";");
		
		ArrayList<Animale>			animali			= new ArrayList<Animale>();
		ArrayList<Accettazione> accettazioni = new ArrayList<Accettazione>();
		ArrayList<AnimaleAnagrafica>			anagraficheAnimali			= new ArrayList<AnimaleAnagrafica>();
		ServicesStatus	status			= new ServicesStatus();
		Gatto gatto = null;
		Cane cane   = null;
		
		logger.info("Redirect to inserimento accettazione");
		int i=0;
		while(i<mcAnagrafati.length)
		{
			String identificativo = mcAnagrafati[i];
			HashMap<String, Object> fetchAnimale = AnimaliUtil.fetchAnimale(identificativo, connection, persistence, utente, status, connectionBdu, req);
			Animale animale = (Animale)fetchAnimale.get("animale");
			animali.add(animale);
			logger.info("Recuperato l'animale. Mc: " + animale.getIdentificativo());
			Accettazione accettazione	= AnimaliUtil.newAccettazione( persistence, animale, utente, status, req, connectionBdu );
			accettazione.setAnimale(animale);
			accettazioni.add(accettazione);
			logger.info("Recuperato l'animale. Mc: " + animale.getIdentificativo());
			logger.info("Recuperata l'accettazione. Id: " + accettazione.getId());
			
			String specieAnimale = null;
			switch ( animale.getLookupSpecie().getId() )
			{
				case CANE:
					specieAnimale = "canina";
					cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), utente, status, connectionBdu,req);
					break;
				case GATTO:
					specieAnimale = "felina";
					gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), utente, status, connectionBdu,req);
					break;
			}
			
			AnimaleAnagrafica anag = AnimaliUtil.getAnagrafica(animale, cane, gatto, persistence, utente, status, connectionBdu,req);
			req.setAttribute("anagraficaAnimale"+(i+1), anag);
			i++;
		}
		
		
		Iterator<LookupAsl> asl = LookupAslDAO.getAsl(connection).iterator();
		while(asl.hasNext())
		{
			LookupAsl aslTemp = asl.next();
			req.setAttribute("personaleAsl"+ aslTemp.getId(), UtenteDAO.getUtenti(connection,aslTemp.getId(),-1));
			
		}
		
		ArrayList<LookupComuni> lookupComuni  = (ArrayList<LookupComuni>)req.getServletContext().getAttribute("listComuni");
		req.setAttribute("lookupComuni", lookupComuni);
		
		
		//Filtri comuni a tutte le motivazioni
		ArrayList<LookupOperazioniAccettazione> operazioni =  new ArrayList<LookupOperazioniAccettazione>();
		LookupOperazioniAccettazione iscriz = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.iscrizione, connection);
		operazioni.add(iscriz);
		
		ArrayList<LookupTipiRichiedente> richiedenti = new ArrayList<LookupTipiRichiedente>();
		ArrayList<LookupTipiRichiedente> richiedentiForzaPubblica = new ArrayList<LookupTipiRichiedente>();
			
		//Filtri comuni a tutti i richiedentei
		ArrayList<LookupTipiRichiedente> richiedentiAll = (ArrayList<LookupTipiRichiedente>) persistence.createCriteria( LookupTipiRichiedente.class )
				.addOrder( Order.asc( "level" ) )
				.list();
			
			
		//Itero tutte le motivazioni e ad ogni occorrenza aggiungo al relativo ArrayList
		Iterator<LookupTipiRichiedente> richiedentiIter = richiedentiAll.iterator();
		while(richiedentiIter.hasNext())
		{
			LookupTipiRichiedente temp = richiedentiIter.next();
			if(temp.getForzaPubblica())
			{
				richiedentiForzaPubblica.add(temp);
			}
			else if(!temp.getForzaPubblica())
			{
				richiedenti.add(temp);
			}
		}
			
		ArrayList<LookupAsl> listaAltreAsl = (ArrayList<LookupAsl>) persistence.createCriteria( LookupAsl.class )
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.add( Restrictions.not( Restrictions.eq("id", utente.getClinica().getLookupAsl().getId())) )
				.addOrder( Order.asc( "description" ) )
				.list();
		
		req.setAttribute("listaAltreAsl", listaAltreAsl);
		listaAltreAsl.add(utente.getClinica().getLookupAsl());
		req.setAttribute( "asl", listaAltreAsl );		
				
		ArrayList<LookupAssociazioni> associazioni = (ArrayList<LookupAssociazioni>) persistence.createCriteria( LookupAssociazioni.class ).addOrder( Order.asc( "level" ) ).list();
		
		ArrayList<LookupPersonaleInterno> personaleInterno = LookupAslDAO.getPersonaleInterno(connection, utente.getClinica().getLookupAsl());
		req.setAttribute( "personaleInterno", personaleInterno );
		
		req.setAttribute( "idOpsBdr", IdOperazioniBdr.getInstance() );
		req.setAttribute( "idRichiesteVarie", IdRichiesteVarie.getInstance() );
		req.setAttribute( "idTipiRichiedente", IdTipiRichiedente.getInstance() );
		req.setAttribute( "animali", animali );
		req.setAttribute( "accettazioni", accettazioni );
		req.setAttribute( "richiedenti", richiedenti );
		req.setAttribute( "richiedentiForzaPubblica", richiedentiForzaPubblica );
		req.setAttribute( "associazioni", associazioni );
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		req.setAttribute( "idOps", 		IdOperazioniDM.getInstance() );
		req.setAttribute( "anagraficheAnimali", anagraficheAnimali );
		req.setAttribute( "flagAnagrafe", stringaFromRequest("flagAnagrafe"));
		req.setAttribute( "operazioniInBdr", operazioni);
		
		gotoPage( "/jsp/vam/accettazioneMultipla/add.jsp" );
	}

}
