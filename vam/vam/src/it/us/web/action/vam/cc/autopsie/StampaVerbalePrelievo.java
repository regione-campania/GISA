/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.autopsie;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleAnagrafica;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.AutopsiaOrganoPatologie;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.TerapiaDegenza;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami;
import it.us.web.bean.vam.lookup.LookupAutopsiaFenomeniCadaverici;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrgani;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrganiTipiEsamiEsiti;
import it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami;
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

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class StampaVerbalePrelievo extends GenericAction {
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
		//Inizializzazione variabili utile al riempimento del documento
		Autopsia autopsia 		  = (Autopsia) persistence.find (Autopsia.class, interoFromRequest("idAutopsia") );	
		req.setAttribute( "a",autopsia);
		
		
		Animale animale = autopsia.getCartellaClinica().getAccettazione().getAnimale();
		AnimaleAnagrafica animaleAnagrafica = AnimaliUtil.getAnagrafica(animale, persistence, utente, new ServicesStatus(), connection, req );
		
		String barcodeNecroscopico="";
		int idNecro = autopsia.getId();
		barcodeNecroscopico = String.valueOf(idNecro);
		while (barcodeNecroscopico.length()<=9)
			barcodeNecroscopico="0"+barcodeNecroscopico;
		req.setAttribute( "barcodeNecroscopico", barcodeNecroscopico );
		

		ServicesStatus status = new ServicesStatus();			
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(animale, persistence, utente, status, connection, req));
		
		if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 1 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
		{
			RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection, req);
			req.setAttribute("res", res);
		}
		else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 2 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
		{
			RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection, req);
			req.setAttribute("res", rfr);
		}
		else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 3 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
		{
			RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
			req.setAttribute("res", rsr);
		}

		
		req.setAttribute("animaleAnagrafica",animaleAnagrafica);
		
		gotoPage("popup", "/jsp/vam/cc/autopsie/verbalePrelievo.jsp");
	}
}
