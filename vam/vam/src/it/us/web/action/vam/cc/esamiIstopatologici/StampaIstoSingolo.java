/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiIstopatologici;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.TerapiaDegenza;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

public class StampaIstoSingolo extends GenericAction {
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
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		//Se idCartellaClinica vuol dire che è già in sessione	
		int id = interoFromRequest("id");
		
		if(id>0)
			session.setAttribute("idEsame", id);		
		
		EsameIstopatologico esame = (EsameIstopatologico) persistence.find (EsameIstopatologico.class, id );	
		esame.getId();
		if(esame.getTipoDiagnosi().getId()!=3)
			esame.setDiagnosiNonTumorale("");
			
		Animale animale = null;
	    if(!esame.getOutsideCC())
	    	animale = esame.getCartellaClinica().getAccettazione().getAnimale();
	    else
	    	animale = esame.getAnimale();
	    
		esame.setAnimale(animale);
		animale.getIdentificativo();
		Date dataDecesso = null;
		
		//Se il cane non è morto senza mc bisogna leggere in bdr le info sulla morte
		ServicesStatus status = new ServicesStatus();
		if(animale.getDecedutoNonAnagrafe())
		{
			//req.setAttribute("fuoriAsl", false);
		}
		else
		{	
			if (animale.getLookupSpecie().getId() == Specie.CANE && !animale.getDecedutoNonAnagrafe()) 
			{
				RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, status, connection,req );
				req.setAttribute("res", res);
				dataDecesso = (res == null) ? null : res.getDataEvento();
			}
			else if (animale.getLookupSpecie().getId() == Specie.GATTO && !animale.getDecedutoNonAnagrafe()) 
			{
				RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, status, connection,req );
				req.setAttribute("res", rfr);
				dataDecesso = (rfr == null) ? null : rfr.getDataEvento();
			}
			else if (animale.getLookupSpecie().getId() == Specie.SINANTROPO && !animale.getDecedutoNonAnagrafe()) 
			{
				RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, animale);
				req.setAttribute("res", rsr);
				dataDecesso = (rsr == null) ? null : rsr.getDataEvento();
			}
		}
				
		req.setAttribute( "esame", esame);
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(animale, persistence, utente, status, connection,req));
		
		
		gotoPage("popup", "/jsp/vam/cc/esamiIstopatologici/istoSingolo.jsp");
	}
}
