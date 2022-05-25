/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazioneMultipla;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.Parameter;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.remoteBean.RegistrazioniInterface;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAssociazioni;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazioneCondizionate;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdOperazioniInBdr;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.constants.TipiRichiedente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.ModificaBdrException;
import it.us.web.util.DateUtils;
import it.us.web.util.dwr.vam.accettazione.Test;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

public class ToAddAnimale extends GenericAction  implements TipiRichiedente, Specie
{

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

	/**
	 * synchronized per evitare problemi col progressivo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		connectionBdu = dsBdu.getConnection();
		setConnectionBdu(connectionBdu);
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		
		ArrayList<Parameter> mcs = parameterList("microchip", true);
		ArrayList<Parameter> mcsRipulito = new ArrayList<Parameter>();
		
		String mcAnagrafato = stringaFromRequest("mcAnagrafato");
		LinkedHashMap<String,Boolean> mcAnagrafati = new LinkedHashMap<String,Boolean>();
		
		int i = 0;
		boolean trovatoPrimoMcDaAnagrafare=false;
		boolean anagrafato = false;
		if(mcAnagrafato!=null)
			anagrafato=true;
			
		while(i<mcs.size())
		{
			if(!mcs.get(i).getValore().equals(""))
			{
				mcsRipulito.add(mcs.get(i));
				mcAnagrafati.put(mcs.get(i).getValore(), anagrafato);
				if(!anagrafato && !trovatoPrimoMcDaAnagrafare)
				{
					req.setAttribute("mcDaAnagrafare", mcs.get(i).getValore());
					trovatoPrimoMcDaAnagrafare=true;
				}
				if(mcAnagrafato!=null && mcAnagrafato.equals(mcs.get(i).getValore()))
					anagrafato = false;
					
			}
			i++;
		}

		req.setAttribute("mcAnagrafati", mcAnagrafati);
		req.setAttribute("operazioneIscrizione", (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.iscrizione));
		
		Iterator<String> mcsIter = mcAnagrafati.keySet().iterator();
		String mcToPass = "";
		while(mcsIter.hasNext())
		{
			String mc = mcsIter.next();
			if(mcsIter.hasNext())
				mcToPass+=mc+";";
			else
				mcToPass+=mc;
		}
		
		if(mcAnagrafato!=null)
		{
			HashMap<String,Object> datiAnimale = AnimaliUtil.getDatiAnimaleAccMultipla(mcAnagrafato, connection, persistence, connectionBdu, req);
			req.setAttribute("datiAnimale", datiAnimale);
		}
		
		if(trovatoPrimoMcDaAnagrafare)
			gotoPage( "/jsp/vam/accettazioneMultipla/anagrafa.jsp" );
		else
			redirectTo("vam.accettazioneMultipla.ToAdd.us?mcAnagrafati="+mcToPass, req, res);
	}
	
}

