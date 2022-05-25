/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.decessi.ToAdd;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.TerapiaDegenza;
import it.us.web.bean.vam.TipoIntervento;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupFerite;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

public class StampaDetailNew extends GenericAction implements Specie {

	
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
		int id = interoFromRequest("idCartellaClinica");
		
		if(id>0)
			session.setAttribute("idCc", id);		
		
		CartellaClinica cc = (CartellaClinica) persistence.find(CartellaClinica.class, (Integer)session.getAttribute("idCc"));
		Animale animale = cc.getAccettazione().getAnimale();
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
				RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, status, connection ,req);
				req.setAttribute("res", res);
				dataDecesso = (res == null) ? null : res.getDataEvento();
			}
			else if (animale.getLookupSpecie().getId() == Specie.GATTO && !animale.getDecedutoNonAnagrafe()) 
			{
				RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection ,req);
				req.setAttribute("res", rfr);
				dataDecesso = (rfr == null) ? null : rfr.getDataEvento();
			}
			else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.SINANTROPO && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
			{
				RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
				req.setAttribute("res", rsr);
				dataDecesso = (rsr == null) ? null : rsr.getDataEvento();
			}
		}
		
		//Recupero di tutte le terapie
		ArrayList<TerapiaDegenza> tdList = (ArrayList<TerapiaDegenza>) persistence.getNamedQuery("GetTerapieDegenzaByCC").setInteger("idCartellaClinica", idCc).list();
		ArrayList<Diagnosi> diagnosi = (ArrayList<Diagnosi>) persistence.getNamedQuery("GetDiagnosiByCC").setInteger("idCartellaClinica", idCc).list();

		ArrayList<Trasferimento> trasfs = null;
		if(!cc.getCcPostTrasferimento() && !cc.getCcRiconsegna())
			trasfs = cc.getTrasferimentiOrderByStato();
		else if(cc.getCcPostTrasferimentoMorto())
			trasfs = cc.getTrasferimentiByCcMortoPostTrasfOrderByStato();
		else if(cc.getCcPostTrasferimento())
			trasfs = cc.getTrasferimentiByCcPostTrasfOrderByStato();
		else if(cc.getCcRiconsegna())
			trasfs = cc.getTrasferimentiByCcPostRiconsegnaOrderByStato();
		
		
		req.setAttribute("tdList", tdList);
		req.setAttribute("trasferimenti", trasfs);

		req.setAttribute("diagnosi", diagnosi);		
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(cc.getAccettazione().getAnimale(), persistence, utente, status, connection,req));
		
		gotoPage("popup", "/jsp/vam/cc/popupDetails_new.jsp");
	}
}
