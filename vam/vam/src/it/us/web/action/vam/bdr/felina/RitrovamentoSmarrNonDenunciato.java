/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.bdr.felina;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RitrovamentoSmarrNonDenunciato extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	@Override
	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(RitrovamentoSmarrNonDenunciato.class);

		logger.info("Esecuzione ritrovamento(smarrimento non denunciato) gatto in Vam");

		Accettazione				accettazione	= (Accettazione) persistence.find( Accettazione.class, interoFromRequest( "idAccettazione" ) );
		
		LookupOperazioniAccettazione ritr = (LookupOperazioniAccettazione) persistence.find( LookupOperazioniAccettazione.class, IdOperazioniBdr.ritrovamentoSmarrNonDenunciato );

		AttivitaBdr abdr = new AttivitaBdr();
		abdr.setAccettazione		( accettazione );
		abdr.setEntered				( new Date() );
		abdr.setEnteredBy			( utente.getId() );
		abdr.setModified			( abdr.getEntered() );
		abdr.setModifiedBy			( utente.getId() );
		abdr.setOperazioneBdr		( ritr );
		
		persistence.insert( abdr );
		
		String provincia = stringaFromRequest("provincia");
		String comune = "0"; 
		if (provincia.equals("BN"))		
			comune = stringaFromRequest("comuneBN");
		else if (provincia.equals("NA"))	
			comune = stringaFromRequest("comuneNA");
		else if (provincia.equals("SA"))	
			comune = stringaFromRequest("comuneSA");
		else if (provincia.equals("CE"))	
			comune = stringaFromRequest("comuneCE");
		else if (provincia.equals("AV"))	
			comune = stringaFromRequest("comuneAV");
		
		FelinaRemoteUtil.eseguiRitrovamentoSmarrNonDenunciato(
				accettazione.getAnimale(), 
				stringaFromRequest( "dataRitrovamento" ), 
				stringaFromRequest( "luogoRitrovamento" ), 
				comune,
				"##### RITROVAMENTO CON SMARRIMENTO NON DENUNCIATO: " + stringaFromRequest( "noteRitrovamento" ) + " #####", 
				utente,req);
		

		persistence.commit();

		setMessaggio( "Registrazione di ritrovamento(smarrimento non denunciato) inserita con successo in BDR" );
		redirectTo( "vam.accettazione.TestRegistrazioni.us?idAccettazione=" + accettazione.getId() );
	}
}
