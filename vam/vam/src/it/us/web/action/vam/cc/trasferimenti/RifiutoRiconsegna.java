/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.trasferimenti;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.Date;
import java.util.Iterator;

public class RifiutoRiconsegna extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "TRASFERIMENTI", "CRIUV", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("trasferimenti");
	}

	public void execute() throws Exception
	{
		Trasferimento trasferimento = (Trasferimento) persistence.find( Trasferimento.class, interoFromRequest( "idTrasferimento" ) );
		
		trasferimento.setDataRifiutoRiconsegna( dataFromRequest( "dataRifiutoRiconsegna" ) );
		trasferimento.setNotaApprovazioneRiconsegna( stringaFromRequest( "notaApprovazioneRiconsegna" ) );
		
		trasferimento.setModified( new Date() );
		trasferimento.setModifiedBy( utente );
		
		Iterator<LookupOperazioniAccettazione> iter = trasferimento.getOperazioniRichieste().iterator();
		while(iter.hasNext())
		{
			LookupOperazioniAccettazione op = iter.next();
			op.getOperazioniCondizionate();
			op.getOperazioniCondizionanti();
		}
		
		//La cartella clinica del destinatario viene riaperta
		CartellaClinica clinica = trasferimento.getCartellaClinicaDestinatario();
		clinica.setDataChiusura(null);
		clinica.setModified(new Date());
		clinica.setModifiedBy(utente);
		
		persistence.update( clinica );
		persistence.update( trasferimento );			
		persistence.commit();
		
		setMessaggio( "Richiesta di riconsegna rifiutata con successo." );
		redirectTo( "vam.cc.trasferimenti.Home.us" );
	}
}
