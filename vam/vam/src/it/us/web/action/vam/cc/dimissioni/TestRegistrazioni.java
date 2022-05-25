/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.dimissioni;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.Transient;

import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.Registrazioni;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.dao.vam.CartellaClinicaDAONoH;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;

public class TestRegistrazioni extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@Override
	public void execute() throws Exception
	{
		CartellaClinica cc = (CartellaClinica) persistence.find( CartellaClinica.class, interoFromRequest( "idCc" ) );
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		CartellaClinicaNoH ccNoH = CartellaClinicaDAONoH.getCc(connection, cc.getId());
		
		if( eseguireOperazioniInBDR( cc, persistence ) && 
			(cc.getDestinazioneAnimale().getId()==1 || 
			 cc.getDestinazioneAnimale().getId()==4 || 
			 cc.getDestinazioneAnimale().getId()==2 || 
			 cc.getDestinazioneAnimale().getId()==3 || 
			 cc.getDestinazioneAnimale().getId()==8 || 
			 cc.getDestinazioneAnimale().getId()==5) &&
			(cc.getDestinazioneAnimale().getId()==1 && 
			 cc.getAccettazione().getAnimale().getLookupSpecie().getId()!=Specie.SINANTROPO && 
			AnimaliUtil.getInserireRitornoProprietario(ccNoH.getAccettazione(),req,connection) || 
			   (cc.getDestinazioneAnimale().getId()!=1)
			 )
		   )
		{
			switch ( cc.getAccettazione().getAnimale().getLookupSpecie().getId() )
			{
			case CANE:
				redirectTo( "vam.cc.dimissioni.ToRegistrazioniInterattive.us?idCc=" + cc.getId() );
				break;
			case GATTO:
				redirectTo( "vam.cc.dimissioni.ToRegistrazioniInterattive.us?idCc=" + cc.getId() );
				break;
			case SINANTROPO:
				redirectTo( "vam.cc.dimissioni.ToRegistrazioniInterattive.us?idCc=" + cc.getId() );
				break;
			}
		}
		else if ( cc != null )
		{
			req.setAttribute("idTipoAttivitaBdrCompletata", getIdTipoAttivitaBdrCompletata(cc));
			req.setAttribute("idTipoAttivitaBdrRichiesta", getIdTipoAttivitaBdrRichiesta(cc));
			req.setAttribute("operazione", getOperazioneRichiestaBdr(cc, persistence));
			
			redirectTo( "vam.cc.dimissioni.Detail.us?idCc=" + cc.getId() );
		}
		else
		{
			setErrore( "errore [vam.cc.dimissioni.TestRegistrazioni]: cc non può essere null" );
			goToAction( new Detail()  );
		}
	}
	

	private boolean eseguireOperazioniInBDR(CartellaClinica cc, Persistence persistence) throws Exception
	{
		return cc != null && getOperazioneRichiestaBdrNonEseguita(cc, persistence)!=null;
	}
	
	
	private LookupOperazioniAccettazione getOperazioneRichiestaBdrNonEseguita(CartellaClinica cc, Persistence persistence) throws Exception
	{
		LookupOperazioniAccettazione ret = new LookupOperazioniAccettazione();
		
		LookupOperazioniAccettazione opBdrRichiesta	= getOperazioneRichiestaBdr(cc, persistence);
		LookupOperazioniAccettazione opBdrEseguita		= new LookupOperazioniAccettazione();
		for( AttivitaBdr abdr: cc.getAttivitaBdrs() )
		{
			//Ster.non avviene in dimissione
			if(abdr.getIdRegistrazioneBdr()!=null && abdr.getIdRegistrazioneBdr()>0 && abdr.getOperazioneBdr().getId()!=IdOperazioniBdr.sterilizzazione)
				opBdrEseguita = abdr.getOperazioneBdr();
		}
		
		if(opBdrRichiesta==opBdrEseguita)
			opBdrRichiesta = null;
		ret = opBdrRichiesta;
		
		return ret;
	}
	
	private LookupOperazioniAccettazione getOperazioneRichiestaBdr(CartellaClinica cc, Persistence persistence) throws Exception
	{
		LookupOperazioniAccettazione ret = new LookupOperazioniAccettazione();
		
		if(cc.getDestinazioneAnimale().getId()==3)//Adozione
			ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
		if(cc.getDestinazioneAnimale().getId()==5)//Re-immissione
			ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
		if(cc.getDestinazioneAnimale().getId()==8)//Ritorno asl origine
			ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
		if(cc.getDestinazioneAnimale().getId()==4)//Trasf a canile
			ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
		if(cc.getDestinazioneAnimale().getId()==1)//Ritorno a prop.
			ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
		if(cc.getDestinazioneAnimale().getId()==2)//Decesso
			ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.decesso);
		
		
		return ret;
	}
	
	
	private Integer getIdTipoAttivitaBdrCompletata(CartellaClinica cc)
	{
		Integer idTipoAttivitaBdr = null;
		Iterator<AttivitaBdr> iter = cc.getAttivitaBdrs().iterator();
		while(iter.hasNext())
		{
			AttivitaBdr att = iter.next();
			if(att.getIdRegistrazioneBdr()!=null && att.getIdRegistrazioneBdr()>0 && att.getOperazioneBdr().getId()!=IdOperazioniBdr.sterilizzazione)
				idTipoAttivitaBdr = att.getOperazioneBdr().getId();
		}
		return idTipoAttivitaBdr;
	}
	
	
	private Integer getIdTipoAttivitaBdrRichiesta(CartellaClinica cc)
	{
		Integer idTipoAttivitaBdr = null;
		Iterator<AttivitaBdr> iter = cc.getAttivitaBdrs().iterator();
		while(iter.hasNext())
		{
			AttivitaBdr att = iter.next();
			if(att.getOperazioneBdr().getId()!=IdOperazioniBdr.sterilizzazione)
				idTipoAttivitaBdr = att.getOperazioneBdr().getId();
		}
		return idTipoAttivitaBdr;
	}
	
	
}
