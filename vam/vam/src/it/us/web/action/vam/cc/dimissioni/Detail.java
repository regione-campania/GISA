/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.dimissioni;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.SinantropoDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.lookup.LookupAttivitaEsterneDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.dao.vam.AnimaleDAO;
import it.us.web.dao.vam.CartellaClinicaDAO;
import it.us.web.dao.vam.CartellaClinicaDAONoH;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cacheonix.util.array.HashSet;

public class Detail extends GenericAction 
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
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);

		//int tipologiaAnimale = cc.getAccettazione().getAnimale().getLookupSpecie().getId();
		
		CartellaClinicaNoH ccNoH = CartellaClinicaDAONoH.getCc(connection, cc.getId());
		
		int tipologiaAnimale = AnimaleDAO.getTipologiaByCc(cc.getId(),connection);
		boolean ritornoDaComunicare = false;
		
		String identificativo = AnimaleDAO.getIdentificativoByCc(cc.getId(), connection);
		
		boolean decedutoNonAnagrafe = AnimaleDAO.getDecedutoNonAnagrafeByCc(cc.getId(),connection);
		int idAcc = CartellaClinicaDAO.getIdAccettazione(cc.getId(),connection);
		
		java.util.HashSet<LookupOperazioniAccettazione> ops = AccettazioneDAO.getOperazioniRichieste(idAcc, connection);
		
		/*int dest = 0;
		if (cc.getDestinazioneAnimale()!=null)
			dest = cc.getDestinazioneAnimale().getId();*/
		
		Integer dest = CartellaClinicaDAO.getDestinazioneAnimale(cc.getId(),connection);
		
		LookupOperazioniAccettazione operazione = null;
		
		if (tipologiaAnimale == Specie.CANE) 
		{
			if(!decedutoNonAnagrafe)
				ritornoDaComunicare = AnimaliUtil.getInserireRitornoProprietario(identificativo, idAcc, ops, req);
			if(dest==3)//Adozione
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.adozione, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
			if(dest==5)//Re-immissione
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.reimmissione, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
			if(dest==8)//Ritorno ad asl di origine
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.ritornoAslOrigine, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
			if(dest==4)//Trasf a canile
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.trasfCanile, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
			if(dest==1)//Ritorno a proprietario
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.ritornoProprietario, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
			if(dest==9)//Ritorno a canile origine
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.ritornoCanileOrigine, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoCanileOrigine);
			req.setAttribute("operazione", operazione);
		}
		
		else if (tipologiaAnimale == Specie.GATTO) 
		{
			if(!decedutoNonAnagrafe)
				ritornoDaComunicare = AnimaliUtil.getInserireRitornoProprietario(ccNoH.getAccettazione(),req,connection);
			operazione = null;
			if(dest==3)//Adozione
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.adozione, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
			if(dest==5)//Re-immissione
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.reimmissione, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
			if(dest==8)//Ritorno ad asl di origine
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.ritornoAslOrigine, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
			if(dest==4)//Trasf a canile
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.trasfCanile, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
			if(dest==1)//Ritorno a proprietario
				operazione = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.ritornoProprietario, connection); //operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
			req.setAttribute("operazione", operazione);
		}
		
		ServicesStatus status = new ServicesStatus();
		
		if (tipologiaAnimale == 1) 
		{
			if(dest!=null && dest==2)
			{
				RegistrazioniCaninaResponse res = CaninaRemoteUtil.getInfoDecesso(identificativo, utente, status, connectionBdu ,req);
				req.setAttribute("res", res);
			}
			
			//Errore nella comunicazione con il Wrapper
			if (!status.isAllRight()) {
				setMessaggio("Errore nella comunicazione con la BDR di riferimento");
				goToAction(new it.us.web.action.vam.cc.Detail());
			}
			
		
		}
		else if (tipologiaAnimale == 2) 
		{
			
			if(dest!=null && dest==2)
			{
				RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( identificativo, utente, status, connectionBdu ,req);
				req.setAttribute("res", rfr);
			}
			//Errore nella comunicazione con il Wrapper
			if (!status.isAllRight()) {
				setMessaggio("Errore nella comunicazione con la BDR di riferimento");
				goToAction(new it.us.web.action.vam.cc.Detail());
			}
			
			
		}
		else if (tipologiaAnimale== 3) 
		{
			if(dest!=null && dest==2)
			{
				//RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
				RegistrazioniSinantropiResponse rsr = SinantropoDAO.getInfoDecesso(connection, AnimaleDAO.getAnimale(identificativo, connection));
				req.setAttribute("res", rsr);
			}
			
			
		}
		//Fine parte inerente i dati della morte
		
		if((tipologiaAnimale==Specie.CANE || tipologiaAnimale==Specie.GATTO) && !decedutoNonAnagrafe)
			//RegistrazioniUtil.sincronizzaDaBdu(null, cc, persistence, connectionBdu, utente, false,req);
			//RegistrazioniUtil.sincronizzaDaBdu(null, cc, persistence, connectionBdu, utente, false,req,connection);
			RegistrazioniUtil.sincronizzaDaBduNoH(null, ccNoH, cc, persistence, connectionBdu, utente, false,req,connection);
		
	
		
		req.setAttribute("idTipoAttivitaBdrCompletata", getIdTipoAttivitaBdrCompletata(cc));
		req.setAttribute("idTipoAttivitaBdrRichiesta", getIdTipoAttivitaBdrRichiesta(cc));
		
		if (dest!=0){
			req.setAttribute("operazione", getOperazioneRichiestaBdr(cc, dest));
		} else {
			req.setAttribute("operazione", new LookupOperazioniAccettazione());
		}
		
		req.setAttribute("ritornoDaComunicare", ritornoDaComunicare);
		req.setAttribute("cc", ccNoH);
		gotoPage( "/jsp/vam/cc/dimissioni/detail.jsp" );
	}
	
	
	private LookupOperazioniAccettazione getOperazioneRichiestaBdr(CartellaClinica cc, Integer dest) throws Exception
	{
		LookupOperazioniAccettazione ret = new LookupOperazioniAccettazione();
		
		if(dest!=null && dest==3)//Adozione
			ret = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.adozione , connection); //ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
		if(dest!=null && dest==5)//Re-immissione
			ret = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.reimmissione , connection); //ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
		if(dest!=null && dest==8)//Ritorno asl origine
			ret = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.ritornoAslOrigine , connection); //ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
		if(dest!=null && dest==4)//Trasf a canile
			ret = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.trasfCanile , connection); //ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
		if(dest!=null && dest==2)//Decesso
			ret = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.decesso , connection); //ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.decesso);
		if(dest!=null && dest==1)//Ritorno a proprietario
			ret = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.ritornoProprietario , connection); //ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
		if(dest!=null && dest==9)//Restituzione a canile origine
			ret = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.ritornoCanileOrigine , connection); //ret = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoCanileOrigine);
		
		return ret;
	}
	
	private Integer getIdTipoAttivitaBdrCompletata(CartellaClinica cc) throws SQLException
	{
		Integer idTipoAttivitaBdr = null;
		Iterator<AttivitaBdr> iter = CartellaClinicaDAO.getAttivitaBdrs(cc.getId(), connection).iterator();
		while(iter.hasNext())
		{
			AttivitaBdr att = iter.next();
			if(att.getIdRegistrazioneBdr()!=null && att.getIdRegistrazioneBdr()>0 && att.getOperazioneBdr().getId()!=IdOperazioniBdr.sterilizzazione)
				idTipoAttivitaBdr = att.getOperazioneBdr().getId();
		}
		return idTipoAttivitaBdr;
	}
	
	private Integer getIdTipoAttivitaBdrRichiesta(CartellaClinica cc) throws SQLException
	{
		Integer idTipoAttivitaBdr = null;
		Iterator<AttivitaBdr> iter = CartellaClinicaDAO.getAttivitaBdrs(cc.getId(), connection).iterator();
		while(iter.hasNext())
		{
			AttivitaBdr att = iter.next();
			if(att.getOperazioneBdr().getId()!=IdOperazioniBdr.sterilizzazione)
				idTipoAttivitaBdr = att.getOperazioneBdr().getId();
		}
		return idTipoAttivitaBdr;
	}

}
