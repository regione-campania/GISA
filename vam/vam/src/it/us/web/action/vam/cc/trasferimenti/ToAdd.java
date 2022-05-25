/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.trasferimenti;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.Detail;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.ClinicaNoH;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniDM;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.ClinicaDAONoH;
import it.us.web.constants.IdOperazioniBdr;

import java.sql.Connection;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToAdd extends GenericAction  implements Specie
{

	@Override
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
		canCc(cc);
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("trasferimenti");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		if(cc.getAccettazione().getAnimale().getDataSmaltimentoCarogna()!=null)
		{
			setErrore("Impossibile trasferire un animale per cui è stato registrato il trasporto spoglie");
			goToAction(new it.us.web.action.vam.cc.trasferimenti.List());
		}
		else
		{
			boolean urgenza = booleanoFromRequest( "urgenza" );
			
			//if( urgenza ) ora anche se non c'è urgenza si permette la scelta della clinica di destinazione
			{
				ArrayList<ClinicaNoH> cliniche = ClinicaDAONoH.getCliniche( connection);
				/*ArrayList<Clinica> cliniche = (ArrayList<Clinica>) persistence.createCriteria( Clinica.class )
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.add( Restrictions.ne( "id", utente.getClinica().getId() ))
					.createAlias("lookupAsl", "asl")
					.addOrder( Order.asc( "asl.description" ) )
					.addOrder( Order.asc( "nome" ) )
					.list();*/
				
				req.setAttribute( "cliniche", cliniche );
			}
			
			String specieAnimale = null;
			switch ( cc.getAccettazione().getAnimale().getLookupSpecie().getId() )
			{
			case CANE:
				specieAnimale = "canina";
				break;
			case GATTO:
				specieAnimale = "felina";
				break;
			case SINANTROPO:
				specieAnimale = "sinantropi";
				break;
			}
			
			/**
			 *  approfondimento diagnostico medicina
			 */
			ArrayList<LookupOperazioniAccettazione> operazioniPsADM = (ArrayList<LookupOperazioniAccettazione>) persistence.createCriteria( LookupOperazioniAccettazione.class )
				.add( Restrictions.eq( specieAnimale, true ) )
				.add( Restrictions.eq( "approfondimenti", true ) )
				.add( Restrictions.eq( "approfondimentoDiagnosticoMedicina", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
			
			/**
			 * approfondimenti - alta specialità chirurgica
			 */
			ArrayList<LookupOperazioniAccettazione> operazioniPsASC = (ArrayList<LookupOperazioniAccettazione>) persistence.createCriteria( LookupOperazioniAccettazione.class )
				.add( Restrictions.eq( specieAnimale, true ) )
				.add( Restrictions.eq( "approfondimenti", true ) )
				.add( Restrictions.eq( "altaSpecialitaChirurgica", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
			
			/**
			 * approfondimenti - diagnostica specialistica strumentale
			 */
			ArrayList<LookupOperazioniAccettazione> operazioniPsDSS = (ArrayList<LookupOperazioniAccettazione>) persistence.createCriteria( LookupOperazioniAccettazione.class )
				.add( Restrictions.eq( specieAnimale, true ) )
				.add( Restrictions.eq( "approfondimenti", true ) )
				.add( Restrictions.eq( "diagnosticaStrumentale", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
			
			/**
			 * Richieste Varie
			 */
			ArrayList<LookupOperazioniAccettazione> operazioniNonBdr = (ArrayList<LookupOperazioniAccettazione>) persistence.createCriteria( LookupOperazioniAccettazione.class )
			.add( Restrictions.eq( specieAnimale, true ) )
			.add( Restrictions.eq( "inbdr", false ) )
			.add( Restrictions.eq( "approfondimenti", false ) )
			.add( Restrictions.eq( "richiestaPrelieviMalattieInfettive", false ) )
			.addOrder( Order.asc( "level" ) )
			.list();
			
			//Fin quando non verrà implementata in Bdr la registrazione di ritrovamentoSmarrNonDenunciato
			//questa operazione la impostiamo col flag inbdr = false però facciamo in modo che appaia sempre nella lista "Anagrafi Regionali"
			//per cui la tolgo dalla lista operazioniInBdr e la metto in operazioniNonBdr
			LookupOperazioniAccettazione ritrovamentoSmarrNonDenunciato = 
				(LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritrovamentoSmarrNonDenunciato);
			operazioniNonBdr.remove(ritrovamentoSmarrNonDenunciato);
			
			req.setAttribute( "idRichiesteVarie", IdRichiesteVarie.getInstance() );
			req.setAttribute( "urgenza", urgenza );
			
			req.setAttribute( "operazioniNonBdr", operazioniNonBdr );
			req.setAttribute( "operazioniPsADM", operazioniPsADM );
			req.setAttribute( "operazioniPsASC", operazioniPsASC );
			req.setAttribute( "operazioniPsDSS", operazioniPsDSS );

			
			gotoPage( "/jsp/vam/cc/trasferimenti/add.jsp" );
		}
	}

}
