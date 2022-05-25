/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.bdr.felina;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.RegistrazioniFelina;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

public class ToRegistrazioni extends GenericAction
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
		setSegnalibroDocumentazione("accettazione");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		
		Context ctxVam = new InitialContext();
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = dsVam.getConnection();
		aggiornaConnessioneApertaSessione(req);
		setConnection(connectionVam);
		
		Accettazione accettazione = (Accettazione) persistence.find( Accettazione.class, interoFromRequest( "idAccettazione" ) );
		
		Set<LookupOperazioniAccettazione> opsAnagrafeEseguire = accettazione.getOperazioniRichiesteBdrNonEseguite();
		
		//N.B.
		// per lookupOperazioneAccettazione
		//     id = 5 => Smarrimento
		//     id = 6 => Ritrovamento
		//     id = 7 => Rich. Rilascio Passaporto
		//     id = 3 => Decesso
		//
		//da eseguire in modo non interattivo: Smarrimento, Ritrovamento, Passaporto e Decesso
		
		//in caso di molteplici operazioni da eseguire decido di eseguire con priorità (5, 6, 7, 3, interattive)
		LookupOperazioniAccettazione decesso						= (LookupOperazioniAccettazione) persistence.find( LookupOperazioniAccettazione.class, IdOperazioniBdr.decesso );
		LookupOperazioniAccettazione smarrimento					= (LookupOperazioniAccettazione) persistence.find( LookupOperazioniAccettazione.class, IdOperazioniBdr.smarrimento );
		LookupOperazioniAccettazione passaporto		= (LookupOperazioniAccettazione) persistence.find( LookupOperazioniAccettazione.class, IdOperazioniBdr.passaporto );
/*		ArrayList<LookupComuni> listComuniBN = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
				.add( Restrictions.eq( "bn", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
				
				ArrayList<LookupComuni> listComuniNA = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
				.add( Restrictions.eq( "na", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
				
				ArrayList<LookupComuni> listComuniSA = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
				.add( Restrictions.eq( "sa", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
				
				ArrayList<LookupComuni> listComuniAV = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
				.add( Restrictions.eq( "av", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
				
				ArrayList<LookupComuni> listComuniCE = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
				.add( Restrictions.eq( "ce", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
				
		req.setAttribute("listComuniBN", listComuniBN);
		req.setAttribute("listComuniNA", listComuniNA);
		req.setAttribute("listComuniSA", listComuniSA);
		req.setAttribute("listComuniCE", listComuniCE);
		req.setAttribute("listComuniAV", listComuniAV);	*/
		req.setAttribute( "accettazione", accettazione );
		
		if( opsAnagrafeEseguire.contains( smarrimento ) )
		{
			if( /*regs.getSmarrimento()*/ true )
			{
				gotoPage( "/jsp/vam/bdr/felina/registrazioneSmarrimento.jsp" );
			}
			else
			{
				setErrore( "ERRORE: richiesta operazione \"" + smarrimento.getDescription() + "\" su un animale che non permette tale registrazione" );
				redirectTo( "vam.accettazione.Home.us" );
			}
		}
		/*else if( opsAnagrafeEseguire.contains( ritrovamento ) )
		{
			gotoPage( "/jsp/vam/bdr/felina/registrazioneRitrovamento.jsp" );
		}
		else if( opsAnagrafeEseguire.contains( ritrovamentoSmarrNonDenunciato ) )
		{
			gotoPage( "/jsp/vam/bdr/felina/registrazioneRitrovamentoSmarrNonDenunciato.jsp" );
		}*/
		/*else if( opsAnagrafeEseguire.contains( passaporto ) )
		{
			if( true )
			{
				gotoPage( "/jsp/vam/bdr/felina/registrazionePassaporto.jsp" );
			}
			else
			{
				setErrore( "ERRORE: richiesta operazione \"" + passaporto.getDescription() + "\" su un animale che non permette tale registrazione" );
				redirectTo( "vam.accettazione.Home.us" );
			}
		}*/
		else if( opsAnagrafeEseguire.contains( decesso ) )
		{
			if( /*regs.getDecesso()*/ true )
			{		
				/*ArrayList<LookupCMI> listCMI = (ArrayList<LookupCMI>) persistence.createCriteria( LookupCMI.class )
					.addOrder( Order.asc( "level" ) ).list();
				
				req.setAttribute("listCMI", listCMI);*/
								
				gotoPage( "/jsp/vam/bdr/felina/registrazioneDecesso.jsp" );
			}
			else
			{
				setErrore( "ERRORE: richiesta operazione \"" + decesso.getDescription() + "\" su un animale che non permette tale registrazione" );
				redirectTo( "vam.accettazione.Home.us" );
			}
		}
		else
		{
			req.setAttribute("animale", accettazione.getAnimale());
			if(opsAnagrafeEseguire!=null && !opsAnagrafeEseguire.isEmpty())
			{
				LookupOperazioniAccettazione operazione = opsAnagrafeEseguire.iterator().next();
				req.setAttribute("operazione", operazione);
				req.setAttribute("idTipoRegBdr", RegistrazioniUtil.getIdTipoBdr(accettazione.getAnimale(), accettazione, operazione, connectionVam, utente, connection,req));
				gotoPage( "/jsp/vam/bdr/felina/registrazioniInterattive.jsp" );
			}
			
		}
		
		
	}
	
}
