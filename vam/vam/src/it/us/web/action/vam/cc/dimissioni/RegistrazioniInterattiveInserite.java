/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.dimissioni;

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
import it.us.web.bean.vam.CartellaClinica;
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


public class RegistrazioniInterattiveInserite extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
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
		int dest = cc.getDestinazioneAnimale().getId();
		LookupOperazioniAccettazione operazione = null;
		if(dest==3)//Adozione
			operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
		if(dest==5)//Re-immissione
			operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
		if(dest==8)//Ritorno asl origine
			operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
		if(dest==4)//Trasf a canile
			operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
		if(dest==1)//Ritorno a proprietario
			operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
		if(dest==9)//Ritorno a proprietario
			operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoCanileOrigine);
		
		
		AttivitaBdr abdr = new AttivitaBdr();
		abdr.setCc				( cc );
		abdr.setEntered			( cc.getModified() );
		abdr.setEnteredBy		( utente.getId() );
		abdr.setModified		( abdr.getEntered() );
		abdr.setModifiedBy		( utente.getId() );
		abdr.setOperazioneBdr	( operazione );
		
		int idTipoRegBdr =interoFromRequest("idTipoRegBdr");
		
		if(cc.getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.CANE || cc.getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.GATTO)
			abdr.setIdRegistrazioneBdr(CaninaRemoteUtil.getUltimaRegistrazione(cc.getAccettazione().getAnimale().getIdentificativo(), idTipoRegBdr, connection,req));
		//else
			//abdr.setIdRegistrazioneBdr(SinantropoUtil.getUltimaRegistrazione(cc.getAccettazione().getAnimale().getIdentificativo(), idTipoRegBdr));
		
		persistence.insert( abdr );
		
		persistence.commit();
		
		
		redirectTo("vam.cc.Detail.us?"); 

	}
	
}
