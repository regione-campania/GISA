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
import it.us.web.dao.hibernate.Persistence;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

public class StampaIstoMultiplo extends GenericAction {
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
				RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, status, connection, req );
				req.setAttribute("res", res);
				dataDecesso = (res == null) ? null : res.getDataEvento();
			}
			else if (animale.getLookupSpecie().getId() == Specie.GATTO && !animale.getDecedutoNonAnagrafe()) 
			{
				RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection, req );
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
				
	
		
		Set<EsameIstopatologico> istopatologici = new HashSet<EsameIstopatologico>();
		if(!cc.getTrasferimentiByCcPostTrasf().isEmpty())
		{
			istopatologici = cc.getTrasferimentiByCcPostTrasf().iterator().next().getCartellaClinica().getEsameIstopatologicos();
		}
		if(!cc.getEsameIstopatologicos().isEmpty())
		{
			Iterator<EsameIstopatologico> iters = cc.getEsameIstopatologicos().iterator();
			while(iters.hasNext())
			{
				istopatologici.add(iters.next());
			}
		}
		
		Iterator<EsameIstopatologico> tuttiIstopatologici = istopatologici.iterator();
		HashMap<EsameIstopatologico, ArrayList<EsameIstopatologico>> gruppiIstoToReturn = new HashMap<EsameIstopatologico, ArrayList<EsameIstopatologico>>();
		boolean aggiunto = false;
		while(tuttiIstopatologici.hasNext())
		{
			aggiunto = false;
			EsameIstopatologico istoToAdd = tuttiIstopatologici.next();
			ArrayList<EsameIstopatologico> gruppoToAdd = new ArrayList<EsameIstopatologico>();
			if(gruppiIstoToReturn.isEmpty())
			{
				gruppoToAdd.add(istoToAdd);
				gruppiIstoToReturn.put(istoToAdd, gruppoToAdd);
			}
			else
			{
				Iterator<EsameIstopatologico> listIstoAdded = gruppiIstoToReturn.keySet().iterator();
				while(listIstoAdded.hasNext() && !aggiunto)
				{
					EsameIstopatologico istoAdded = listIstoAdded.next();
					if(istoUguali(istoAdded,istoToAdd))
					{
						aggiunto = true;
						ArrayList<EsameIstopatologico> listEsameIstoToAdd = gruppiIstoToReturn.get(istoAdded);
						listEsameIstoToAdd.add(istoToAdd);
						gruppiIstoToReturn.remove(istoAdded);
						gruppiIstoToReturn.put(istoToAdd, listEsameIstoToAdd);
					}
				}
				if(aggiunto==false)
				{
					gruppoToAdd.add(istoToAdd);
					gruppiIstoToReturn.put(istoToAdd, gruppoToAdd);
				}
			}
		}
		
		
		req.setAttribute("gruppiIsto", gruppiIstoToReturn);
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(animale, persistence, utente, status, connection, req));
		
		
		gotoPage("popup", "/jsp/vam/cc/esamiIstopatologici/esamiMultipli.jsp");
	}
	
	private boolean istoUguali(EsameIstopatologico isto1, EsameIstopatologico isto2) throws Exception
	{
		int lass1 = 0;
		int lass2 = 0;
		if(isto1.getLass()!=null)
			lass1 = isto1.getLass().getId();
		if(isto2.getLass()!=null)
			lass2 = isto2.getLass().getId();
		int tipoPrelievo1 = 0;
		int tipoPrelievo2 = 0;
		if(isto1.getTipoPrelievo()!=null)
			tipoPrelievo1 = isto1.getTipoPrelievo().getId();
		if(isto2.getTipoPrelievo()!=null)
			tipoPrelievo2 = isto2.getTipoPrelievo().getId();
		int tumori1 = 0;
		int tumori2 = 0;
		if(isto1.getTumoriPrecedenti()!=null)
			tumori1 = isto1.getTumoriPrecedenti().getId();
		if(isto2.getTumoriPrecedenti()!=null)
			tumori2 = isto2.getTumoriPrecedenti().getId();
		int linfo1 = 0;
		int linfo2 = 0;
		if(isto1.getInteressamentoLinfonodale()!=null)
			linfo1 = isto1.getInteressamentoLinfonodale().getId();
		if(isto2.getInteressamentoLinfonodale()!=null)
			linfo2 = isto2.getInteressamentoLinfonodale().getId();
		int t1 = 0;
		int t2 = 0;
		if(isto1.getT()!=null)
			t1 = isto1.getT().intValue();
		if(isto2.getT()!=null)
			t2 = isto2.getT().intValue();
		int n1 = 0;
		int n2 = 0;
		if(isto1.getN()!=null)
			n1 = isto1.getN().intValue();
		if(isto2.getN()!=null)
			n2 = isto2.getN().intValue();
		int m1 = 0;
		int m2 = 0;
		if(isto1.getM()!=null)
			m1 = isto1.getM().intValue();
		if(isto2.getM()!=null)
			m2 = isto2.getM().intValue();
		int d1 = 0;
		int d2 = 0;
		if(isto1.getDimensione()!=null)
			d1 = isto1.getDimensione().intValue();
		if(isto2.getDimensione()!=null)
			d2 = isto2.getDimensione().intValue();
		
		if(isto1.getDataRichiesta().getTime()!=isto2.getDataRichiesta().getTime())
			return false;
		else if(!isto1.getNumeroAccettazioneSigla().equals(isto2.getNumeroAccettazioneSigla()))
			return false;
		else if(!isto1.getTipoAccettazione().equals(isto2.getTipoAccettazione()))
			return false;
		else if(lass1!=lass2)
			return false;
		else if(tipoPrelievo1!=tipoPrelievo2)
			return false;
		else if(tumori1!=tumori2)
			return false;
		else if(t1!=t2)
			return false;
		else if(n1!=n2)
			return false;
		else if(m1!=m2)
			return false;
		else if(d1!=d2)
			return false;
		else if(linfo1!=linfo2)
			return false;
		else
			return true;
		
			
	}
}
