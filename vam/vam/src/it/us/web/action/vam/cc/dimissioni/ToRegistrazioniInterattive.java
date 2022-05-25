/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.dimissioni;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.CartellaClinicaDAONoH;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.RegistrazioniUtil;

import java.sql.Connection;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToRegistrazioniInterattive extends GenericAction  implements Specie
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
		setSegnalibroDocumentazione("dimissioni");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
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
		
		CartellaClinicaNoH ccNoH = CartellaClinicaDAONoH.getCc(connectionVam, cc.getId());
		req.setAttribute("adozioneFuoriAsl", ccNoH.getAdozioneFuoriAsl());
		req.setAttribute("adozioneVersoAssocCanili", ccNoH.getAdozioneVersoAssocCanili());
		
		switch ( cc.getAccettazione().getAnimale().getLookupSpecie().getId() )
		{
		case CANE:
			int dest = cc.getDestinazioneAnimale().getId();
			LookupOperazioniAccettazione operazione = null;
			if(dest==8 || dest==3 || dest==5 || dest==4 || dest==1 || dest==9)
				req.setAttribute("animale", cc.getAccettazione().getAnimale());
			if(dest==3)//Adozione
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
			if(dest==5)//Re-immissione
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
			if(dest==8)//Ritorno asl origine
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
			if(dest==4)//Trasf a canile
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
			if(dest==1)//Ritorno a prop.
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
			if(dest==9)//Rest a canile origine per randagi ritrovati da altra asl.
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoCanileOrigine);
			req.setAttribute("operazione", operazione);
			req.setAttribute("idTipoRegBdr", RegistrazioniUtil.getIdTipoBdr(cc.getAccettazione().getAnimale(), cc.getAccettazione(), operazione, connectionVam, utente, connection,req,ccNoH.getAdozioneFuoriAsl(),ccNoH.getAdozioneVersoAssocCanili()));
			gotoPage( "/jsp/vam/cc/dimissioni/registrazioniInterattiveCanina.jsp" );
			break;
		case GATTO:
			dest = cc.getDestinazioneAnimale().getId();
			operazione = null;
			if(dest==8 || dest==3 || dest==5 || dest==4 || dest==1)
				req.setAttribute("animale", cc.getAccettazione().getAnimale());
			if(dest==3)//Adozione
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
			if(dest==5)//Re-immissione
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
			if(dest==8)//Ritorno Asl Origine
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
			if(dest==4)//Trasf a canile
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
			if(dest==1)//Ritorno a prop.
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
			if(dest==9)//Ritorno a prop.
				operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoCanileOrigine);
			req.setAttribute("operazione", operazione);
			req.setAttribute("idTipoRegBdr", RegistrazioniUtil.getIdTipoBdr(cc.getAccettazione().getAnimale(), cc.getAccettazione(), operazione, connectionVam, utente, connection,req, ccNoH.getAdozioneFuoriAsl(),ccNoH.getAdozioneVersoAssocCanili()));
			gotoPage( "/jsp/vam/cc/dimissioni/registrazioniInterattiveFelina.jsp" );
			break;
		case SINANTROPO:
			req.setAttribute("sinantropo", SinantropoUtil.getSinantropoByNumero(persistence, cc.getAccettazione().getAnimale().getIdentificativo()));
			gotoPage( "/jsp/vam/cc/dimissioni/registrazioniInterattiveSinantropi.jsp" );
			break;
		}
			
		
		
		
	
	}

}












