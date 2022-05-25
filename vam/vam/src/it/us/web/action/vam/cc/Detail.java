/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupFerite;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.lookup.LookupAlimentazioniDAO;
import it.us.web.dao.lookup.LookupFeriteDAO;
import it.us.web.dao.lookup.LookupHabitatDAO;
import it.us.web.dao.vam.CartellaClinicaDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

public class Detail extends GenericAction implements Specie {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("cc");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		
			
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		Cane cane = null;
		Gatto gatto = null;
		//Se idCartellaClinica vuol dire che è già in sessione	
		int id = interoFromRequest("idCartellaClinica");
		
		if(id>0){
			session.setAttribute("idCc", id);
		}
		else{
			id=(Integer)session.getAttribute("idCc");
		}
		
		cc = CartellaClinicaDAO.getCc(connection, id);
		
		Autopsia a = CartellaClinicaDAO.getAutopsia(connection, id);
		
		req.setAttribute("a", a);
		
		Animale animale = cc.getAccettazione().getAnimale();
		
		//Se il cane non è morto senza mc bisogna leggere in bdr le info sulla morte
		ServicesStatus status = new ServicesStatus();
		int specie = animale.getLookupSpecie().getId();
		if (specie == Specie.CANE && !animale.getDecedutoNonAnagrafe()) 
		{
			cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), utente, status, connectionBdu,req);
			RegistrazioniCaninaResponse res1 = CaninaRemoteUtil.getInfoDecesso( cane );
			req.setAttribute("res", res1);
		}
		else if (specie == Specie.GATTO && !animale.getDecedutoNonAnagrafe()) 
		{
			gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), utente, status, connectionBdu,req);
			RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( gatto);
			req.setAttribute("res", rfr);
		}
		else if (specie == Specie.SINANTROPO && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
		{
			RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
			req.setAttribute("res", rsr);
		}
		
		/* Gestione per la richiesta delle operazioni su un cane morto.
		 * Deve dare vita all'apertura di una CC solo con Decesso e Autopsia*/
			
		//Se la cc non è chiusa carica le info per : ALIMENTAZIONI, HABITAT, FERITE
		
		
		ArrayList<LookupAlimentazioni> listAlimentazioni = LookupAlimentazioniDAO.getAlimentazioni(connection);
			ArrayList<LookupHabitat> listHabitat = LookupHabitatDAO.getHabitat(connection);
			ArrayList<LookupFerite> listFerite = LookupFeriteDAO.getFerite(connection);
			
			req.setAttribute("listFerite", listFerite);
			req.setAttribute("listAlimentazioni",   listAlimentazioni);		
			req.setAttribute("listHabitat", 	    listHabitat);	
		
		session.setAttribute("cc", cc);
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(cc.getAccettazione().getAnimale(), cane, gatto, persistence, utente, status, connectionBdu,req));

		
		gotoPage("/jsp/vam/cc/home.jsp");
	}
}
