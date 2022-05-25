/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.bdr.canina;

import java.sql.Connection;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AnimaleAnagrafica;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdOperazioniInBdr;
import it.us.web.constants.IdTipiTrasferimentoAccettazione;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

public class RegistrazioniInterattiveInserite extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "ADD", "MAIN" );
		can( gui, "w" );
	}

	@Override
	public void execute() throws Exception
	{
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		connectionBdu = dsBdu.getConnection();
		

		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		connection = ds.getConnection();
		
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		int idAcc = interoFromRequest( "idAccettazione" );
		Accettazione accettazione = (Accettazione) persistence.find( Accettazione.class, idAcc );

		Set<LookupOperazioniAccettazione> registrazioniDaSalvare = accettazione.getOperazioniRichiesteBdrNonEseguite();
		
		String stato = stringaFromRequest("stato");
		int idTipoRegBdr =interoFromRequest("idTipoRegBdr");
		
		for( LookupOperazioniAccettazione loa: registrazioniDaSalvare )
		{
				AttivitaBdr abdr = new AttivitaBdr();
				abdr.setAccettazione	( accettazione );
				abdr.setEntered			( accettazione.getEntered() );
				abdr.setEnteredBy		( utente.getId() );
				abdr.setModified		( abdr.getEntered() );
				abdr.setModifiedBy		( utente.getId() );
				abdr.setOperazioneBdr	( loa );
				
				int idTipoRegBdrDaInserire = RegistrazioniUtil.getIdTipoBdrPreAcc(accettazione.getAnimale(), (accettazione.getTipoTrasferimento()==null)?(null):(accettazione.getTipoTrasferimento().getId()), accettazione.getAdozioneFuoriAsl(), accettazione.getAdozioneVersoAssocCanili(), loa, connection, connectionBdu,req);
				
				if((accettazione.getAnimale().getLookupSpecie().getId()==Specie.CANE || accettazione.getAnimale().getLookupSpecie().getId()==Specie.GATTO) && idTipoRegBdr==idTipoRegBdrDaInserire && CaninaRemoteUtil.getUltimaRegistrazione(accettazione.getAnimale().getIdentificativo(), idTipoRegBdrDaInserire, connectionBdu,req)!=null)
					abdr.setIdRegistrazioneBdr(CaninaRemoteUtil.getUltimaRegistrazione(accettazione.getAnimale().getIdentificativo(), idTipoRegBdrDaInserire, connectionBdu,req));
				//else
					//abdr.setIdRegistrazioneBdr(SinantropoUtil.getUltimaRegistrazione(accettazione.getAnimale().getIdentificativo(), idTipoRegBdr));
				
				System.out.println("Cerco l'ultima reg di id tipo: " + idTipoRegBdr);
				if(CaninaRemoteUtil.getUltimaRegistrazione(accettazione.getAnimale().getIdentificativo(), idTipoRegBdrDaInserire, connectionBdu,req)!=null)
					persistence.insert( abdr );
		}
		
		persistence.commit();
		
		redirectTo( "vam.accettazione.TestRegistrazioni.us?idAccettazione=" + accettazione.getId() );
//		redirectTo( "vam.accettazione.Detail.us?id=" + accettazione.getId() );
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}
	
}
