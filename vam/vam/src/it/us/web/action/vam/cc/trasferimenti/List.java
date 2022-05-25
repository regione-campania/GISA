/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.trasferimenti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.StatoTrasferimento;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.TrasferimentoNoH;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.CartellaClinicaDAONoH;
import it.us.web.dao.vam.TrasferimentoDAO;
import it.us.web.exceptions.AuthorizationException;

public class List extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("trasferimenti");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		boolean nuovoTrasferimentoPossibile	= true;
		boolean riconsegnaPossibile			= false;
		
		CartellaClinicaNoH ccNoH = CartellaClinicaDAONoH.getCcTrasfList(connection, cc.getId());
		
		ArrayList<TrasferimentoNoH> trasfs = null;
		if(!ccNoH.getCcPostTrasferimento() && !ccNoH.getCcRiconsegna())
			trasfs = ccNoH.getTrasferimentiOrderByStato();
		else if(ccNoH.getCcPostTrasferimentoMorto())
			trasfs = ccNoH.getTrasferimentiByCcMortoPostTrasfOrderByStato();
		else if(ccNoH.getCcPostTrasferimento())
			trasfs = ccNoH.getTrasferimentiByCcPostTrasfOrderByStato();
		else if(ccNoH.getCcRiconsegna())
			trasfs = ccNoH.getTrasferimentiByCcPostRiconsegnaOrderByStato();
		
		if(!ccNoH.getCcPostTrasferimento() || ccNoH.getCcRiconsegna())
		{
			if( utente.getClinica().getId() == ccNoH.getEnteredBy().getClinica().getId() )//i trasferimenti sono in uscita
			{
				riconsegnaPossibile = false;
				for( TrasferimentoNoH trasf: trasfs )
				{
					if( trasf.getStato().stato != StatoTrasferimento.RICONSEGNATO )//trasferimento aperto
					{
						nuovoTrasferimentoPossibile = false;
					}
				}
			}
			else//i trasferimenti sono in ingresso
			{
				nuovoTrasferimentoPossibile = false;
				for( TrasferimentoNoH trasf: trasfs )
				{
					if( trasf.getStato().stato == StatoTrasferimento.ACCETTATO_DESTINATARIO && trasf.getCartellaClinica().getId()!=ccNoH.getId() )//trasferimento aperto
					{
						riconsegnaPossibile = true;
					}
				}
			}
		}
		else if(ccNoH.getCcPostTrasferimento() && !ccNoH.getCcPostTrasferimentoMorto())
		{
			nuovoTrasferimentoPossibile = false;
			
			for( TrasferimentoNoH trasf: trasfs )
			{
				//Se il trasferimento ha una cc morto del destinatario allora non è possibile la riconsegna perchè la riconsegna va fatta sulla cc morto
				if( (trasf.getStato().stato == StatoTrasferimento.ACCETTATO_DESTINATARIO || trasf.getStato().stato == StatoTrasferimento.RIFIUTATO_RINCONSEGNA_CRIUV) 
						&& TrasferimentoDAO.cartellaClinicaMortoDestinatarioEsiste(connection, trasf.getId())==false )//trasferimento aperto
				{
					riconsegnaPossibile = true;
				}
			}
		}
		else if(ccNoH.getCcPostTrasferimentoMorto())
		{
			nuovoTrasferimentoPossibile = false;
			for( TrasferimentoNoH trasf: trasfs )
			{
				//Se il trasferimento ha una cc morto del destinatario allora non è possibile la riconsegna perchè la riconsegna va fatta sulla cc morto
				if( trasf.getStato().stato == StatoTrasferimento.ACCETTATO_DESTINATARIO)//trasferimento aperto
				{
					riconsegnaPossibile = true;
				}
			}
		}
		
		req.setAttribute("trasferimenti", trasfs);
		req.setAttribute( "nuovoTrasferimentoPossibile", nuovoTrasferimentoPossibile );
		req.setAttribute( "riconsegnaPossibile", riconsegnaPossibile );
		
		req.setAttribute("cc", ccNoH);

		gotoPage("/jsp/vam/cc/trasferimenti/list.jsp");
	}
}



