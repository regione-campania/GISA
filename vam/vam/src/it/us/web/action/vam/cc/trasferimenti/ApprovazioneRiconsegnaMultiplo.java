/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.trasferimenti;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.Date;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ApprovazioneRiconsegnaMultiplo extends GenericAction {

	
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
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		int[] trasferimentiId = {
		131,
		122,
		180,
		173,
		505,
		514,
		571,
		515,
		536,
		529,
		597,
		645,
		140,
		197,
		238,
		496,
		495,
		481,
		523,
		469,
		606,
		594,
		634,
		357,
		462,
		502,
		503,
		494,
		486,
		539,
		367,
		576,
		104,
		134,
		93,
		136,
		101,
		137,
		138,
		125,
		394,
		126,
		135,
		452,
		712,
		681,
		688,
		802,
		804,
		769,
		949,
		778,
		798,
		705,
		706,
		618,
		526,
		528,
		709,
		676,
		703,
		533,
		596,
		855,
		850,
		845,
		803,
		590,
		572,
		467,
		155,
		442,
		444,
		512,
		810,
		102,
		772};

		for(int i=0;i<trasferimentiId.length;i++)
		{
			
			
			Trasferimento trasferimento = (Trasferimento) persistence.find( Trasferimento.class, trasferimentiId[i] );
			if(trasferimento!=null)
			{
				trasferimento.setDataApprovazioneRiconsegna( dataFromRequest( "dataApprovazioneRiconsegna" ) );
				trasferimento.setDataRiconsegna( dataFromRequest( "dataApprovazioneRiconsegna" ) );
				trasferimento.setNotaRiconsegna( stringaFromRequest( "notaApprovazioneRiconsegna" ) );
				
				trasferimento.setModified( new Date() );
				trasferimento.setModifiedBy( utente );
				
				Iterator<LookupOperazioniAccettazione> iter = trasferimento.getOperazioniRichieste().iterator();
				while(iter.hasNext())
				{
					LookupOperazioniAccettazione op = iter.next();
					op.getOperazioniCondizionate();
					op.getOperazioniCondizionanti();
				}
				
				
				// Apertura nuova cc mittente
				BUtenteAll ut = UtenteDAO.getUtenteAll(trasferimento.getCartellaClinica().getAccettazione().getEnteredBy().getId());
				Accettazione accettazioneNuovaMittente = Riconsegna.nuovaACCmittente(trasferimento, persistence, connection, ut);
				persistence.insert(accettazioneNuovaMittente);
				
				// 2.Creazione nuova cc per il mittente
				CartellaClinica ccNuovaMittente = Riconsegna.nuovaCCmittente(trasferimento, accettazioneNuovaMittente, persistence,connection);
				persistence.insert(ccNuovaMittente);
				
				persistence.update( trasferimento );			
			}
			
		}
		
		persistence.commit();
		
		setMessaggio( "Richieste di rientro approvate con successo" );
		redirectTo( "vam.cc.trasferimenti.Home.us" );
	}
}
