/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.trasferimenti;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.StatoTrasferimento;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.Set;

public class ToRiconsegna extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("trasferimenti");
	}

	@Override
	public void execute() throws Exception
	{
	
		Set<Trasferimento> trasfs = null;
		if(!cc.getCcPostTrasferimento() && !cc.getCcRiconsegna())
			trasfs = cc.getTrasferimenti();
		else if(cc.getCcPostTrasferimentoMorto())
			trasfs = cc.getTrasferimentiByCcMortoPostTrasf();
		else if(cc.getCcPostTrasferimento())
			trasfs = cc.getTrasferimentiByCcPostTrasf();
		else if(cc.getCcRiconsegna())
			trasfs = cc.getTrasferimentiByCcPostRiconsegna();
		
		Trasferimento trasferimento = null;
		for( Trasferimento trasf: trasfs )
		{
			if( trasf.getStato().stato == StatoTrasferimento.ACCETTATO_DESTINATARIO || trasf.getStato().stato == StatoTrasferimento.RIFIUTATO_RINCONSEGNA_CRIUV )//trasferimento aperto
			{
				trasferimento = trasf;
			}
		}
		
		req.setAttribute( "trasferimento", trasferimento );
		
		gotoPage( "/jsp/vam/cc/trasferimenti/riconsegna.jsp" );
	}

}
