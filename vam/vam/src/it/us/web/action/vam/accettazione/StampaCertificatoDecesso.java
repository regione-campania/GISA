/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
//package it.us.web.action.vam.accettazione;
//
//import java.io.InputStream;
//import java.sql.Connection;
//import java.util.Date;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.Map;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.servlet.ServletOutputStream;
//
//import it.us.web.action.GenericAction;
//import it.us.web.bean.BGuiView;
//import it.us.web.bean.ServicesStatus;
//import it.us.web.bean.SuperUtente;
//import it.us.web.bean.remoteBean.Cane;
//import it.us.web.bean.remoteBean.Gatto;
//import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
//import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
//import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
//import it.us.web.bean.sinantropi.Sinantropo;
//import it.us.web.bean.vam.Accettazione;
//import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
//import it.us.web.bean.vam.lookup.LookupRazze;
//import it.us.web.bean.vam.lookup.LookupTaglie;
//import it.us.web.constants.Specie;
//import it.us.web.constants.TipiRichiedente;
//import it.us.web.dao.GuiViewDAO;
//import it.us.web.exceptions.AuthorizationException;
//import it.us.web.util.report.PdfReport;
//import it.us.web.util.sinantropi.SinantropoUtil;
//import it.us.web.util.vam.CaninaRemoteUtil;
//import it.us.web.util.vam.FelinaRemoteUtil;
//
//public class StampaCertificatoDecesso extends GenericAction implements TipiRichiedente
//{
//
//	@Override
//	public void can() throws AuthorizationException
//	{
//		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "DETAIL", "MAIN" );
//		try
//		{
//			can( gui, "w" );
//		}
//		catch(AuthorizationException e)
//		{
//			gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
//			can( gui, "w" );
//		}
//	}
//	
//	@Override
//	public void setSegnalibroDocumentazione()
//	{
//		setSegnalibroDocumentazione("accettazione");
//	}
//
//	@Override
//	public void execute() throws Exception
//	{
//		Context ctx = new InitialContext();
//		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
//		Connection connection = ds.getConnection();
//		setConnectionBdu(connection);
//		int idAcc = interoFromRequest( "accettazione" );
//		Accettazione accettazione = (Accettazione) persistence.find( Accettazione.class, idAcc );
//		
//		String outputFileName = "Certificato_Decesso_" + accettazione.getAnimale().getIdentificativo() + ".pdf";
//		
//		InputStream template = null;
//		if(!accettazione.getProprietarioCognome().startsWith("<b>"))
//		{
//			if(accettazione.getAnimale().getLookupSpecie().getId()==Specie.SINANTROPO)
//				template = this.getClass().getResourceAsStream( "StampaCertificatoDecessoSinantropo.pdf" );
//			else
//			{
//				if(accettazione.getAnimale().getRandagio())
//					template = this.getClass().getResourceAsStream( "StampaCertificatoDecessoRandagio.pdf" );
//				else
//					template = this.getClass().getResourceAsStream( "StampaCertificatoDecesso.pdf" );
//			}
//		}
//		else
//		{
//			template = this.getClass().getResourceAsStream( "StampaCertificatoDecessoGattoColonia.pdf" );
//		}
//		
//		Map<String, Object> mappaProprietaAddizionali = new Hashtable<String, Object>();
//		mappaProprietaAddizionali.put( "oggi",  new Date() );
//		
//		
//		if((accettazione.getAnimale().getLookupSpecie().getId() == Specie.CANE || accettazione.getAnimale().getLookupSpecie().getId() == Specie.GATTO) && !accettazione.getAnimale().getDecedutoNonAnagrafe())
//		{
//			if(accettazione.getAnimale().getLookupSpecie().getId()==Specie.CANE)
//			{
//				Cane cane = CaninaRemoteUtil.findCane(accettazione.getAnimale().getIdentificativo(), utente, new ServicesStatus(), connection);
//				mappaProprietaAddizionali.put( "razza",  cane.getDescrizioneRazza());
//				mappaProprietaAddizionali.put( "taglia",  (cane.getDescrizioneTaglia()==null)?(""):(cane.getDescrizioneTaglia()));
//				mappaProprietaAddizionali.put( "sesso",  cane.getSesso());
//				mappaProprietaAddizionali.put( "statoAttuale",  cane.getStatoAttuale());
//				if (cane.getDescrizioneMantello()!=null)
//					mappaProprietaAddizionali.put( "mantello", cane.getDescrizioneMantello());
//				RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( accettazione.getAnimale().getIdentificativo(), utente, null, connection );
//				mappaProprietaAddizionali.put("dataMorte", (res==null || res.getDataEvento()==null)?(""):(res.getDataEvento()));
//				mappaProprietaAddizionali.put("dataMorteCertezza", (res==null)?(""):(res.getDataMorteCertezza()));
//				mappaProprietaAddizionali.put("causaMorte", (res==null || res.getDecessoValue()==null)?(""):(res.getDecessoValue()));
//				mappaProprietaAddizionali.put("sesso", cane.getSesso());
//				mappaProprietaAddizionali.put("sterilizzato", (cane.getSterilizzato()?("Si"):("No")));
//			}
//			else if(accettazione.getAnimale().getLookupSpecie().getId()==Specie.GATTO)
//			{
//				Gatto gatto = FelinaRemoteUtil.findGatto(accettazione.getAnimale().getIdentificativo(), utente, new ServicesStatus(), connection);
//				mappaProprietaAddizionali.put( "razza",  gatto.getDescrizioneRazza());
//				mappaProprietaAddizionali.put( "taglia",  "Non prevista");
//				mappaProprietaAddizionali.put( "sesso",  gatto.getSesso());
//				mappaProprietaAddizionali.put( "statoAttuale",  gatto.getStatoAttuale());
//				if (gatto.getDescrizioneMantello()!=null)
//					mappaProprietaAddizionali.put( "mantello", gatto.getDescrizioneMantello());
//				if(accettazione.getProprietarioCognome().startsWith("<b>"))
//					mappaProprietaAddizionali.put( "indirizzo", accettazione.getProprietarioIndirizzo() + ", " + accettazione.getProprietarioComune() + " (" + accettazione.getProprietarioProvincia() + ") - " + accettazione.getProprietarioCap());
//				RegistrazioniCaninaResponse rfr = CaninaRemoteUtil.getInfoDecesso(accettazione.getAnimale().getIdentificativo(), utente, null, connection );
//				mappaProprietaAddizionali.put("dataMorte", (rfr==null || rfr.getDataEvento()==null)?(""):(rfr.getDataEvento()));
//				mappaProprietaAddizionali.put("dataMorteCertezza", (rfr==null)?(""):(rfr.getDataMorteCertezza()));
//				mappaProprietaAddizionali.put("causaMorte", (rfr==null || rfr.getDecessoValue()==null)?(""):(rfr.getDecessoValue()));
//				mappaProprietaAddizionali.put("sesso", gatto.getSesso());
//				mappaProprietaAddizionali.put("sterilizzato", (gatto.getSterilizzato()?("Si"):("No")));
//			}
//				
//		}
//		if(accettazione.getAnimale().getDecedutoNonAnagrafe())
//		{
//			if(accettazione.getAnimale().getTaglia()!=null && accettazione.getAnimale().getTaglia()>0)
//				mappaProprietaAddizionali.put("taglia", ((LookupTaglie)persistence.find(LookupTaglie.class, accettazione.getAnimale().getTaglia())).getDescription());
//			mappaProprietaAddizionali.put("sesso", accettazione.getAnimale().getSesso());
//			mappaProprietaAddizionali.put("sterilizzato", accettazione.getSterilizzatoString());
//			mappaProprietaAddizionali.put( "statoAttuale",  "Decesso");
//			mappaProprietaAddizionali.put( "sesso",  accettazione.getAnimale().getSesso());
//			if(accettazione.getAnimale().getLookupSpecie().getId()== Specie.CANE || accettazione.getAnimale().getLookupSpecie().getId()== Specie.GATTO)
//			{
//				if(accettazione.getAnimale().getRazza()!=null && accettazione.getAnimale().getRazza()>0 && accettazione.getAnimale().getLookupSpecie().getId()==Specie.CANE)
//					mappaProprietaAddizionali.put("razza", (CaninaRemoteUtil.getRazza(accettazione.getAnimale().getRazza(), connection).getDescription()));
//				else if(accettazione.getAnimale().getRazza()!=null && accettazione.getAnimale().getRazza()>0 && accettazione.getAnimale().getLookupSpecie().getId()==Specie.GATTO)
//					mappaProprietaAddizionali.put("razza", (FelinaRemoteUtil.getRazza(accettazione.getAnimale().getRazza(), connection).getDescription()));
//				if(accettazione.getAnimale().getMantello()!=null && accettazione.getAnimale().getMantello()>=0 && accettazione.getAnimale().getLookupSpecie().getId()==Specie.CANE)
//					mappaProprietaAddizionali.put("mantello", (CaninaRemoteUtil.getMantello(accettazione.getAnimale().getMantello(), connection).getDescription()));
//				else if(accettazione.getAnimale().getMantello()!=null && accettazione.getAnimale().getMantello()>=0 && accettazione.getAnimale().getLookupSpecie().getId()==Specie.GATTO)
//					mappaProprietaAddizionali.put("mantello", (FelinaRemoteUtil.getMantello(accettazione.getAnimale().getMantello(), connection).getDescription()));
//			}
//			else
//			{
//				String eta = "";
//				if(accettazione.getAnimale().getEta()!=null)
//				{
//					eta += accettazione.getAnimale().getEta();
//				}
//				if(accettazione.getAnimale().getDataNascita()!=null)
//				{
//					eta += "(" + accettazione.getAnimale().getDataNascita() + ")";
//				}
//				mappaProprietaAddizionali.put( "eta", eta );
//				mappaProprietaAddizionali.put( "mantello",  (accettazione.getAnimale().getMantelloSinantropo()==null)?(""):(accettazione.getAnimale().getMantelloSinantropo()));
//				if(accettazione.getAnimale().getRazzaSinantropo()!=null)
//					mappaProprietaAddizionali.put( "razza",  accettazione.getAnimale().getSpecieSinantropoString() + " - " + accettazione.getAnimale().getRazzaSinantropo());
//				else
//					mappaProprietaAddizionali.put( "razza",  accettazione.getAnimale().getSpecieSinantropoString());
//			}
//			
//			if(accettazione.getAnimale().getLookupSpecie().getId()== Specie.GATTO && accettazione.getProprietarioCognome().startsWith("<b>"))
//				mappaProprietaAddizionali.put( "indirizzo", accettazione.getProprietarioIndirizzo() + ", " + accettazione.getProprietarioComune() + " (" + accettazione.getProprietarioProvincia() + ") - " + accettazione.getProprietarioCap() + ")");
//			mappaProprietaAddizionali.put("dataMorte", (accettazione.getAnimale()==null || accettazione.getAnimale().getDataMorte()==null)?(""):(accettazione.getAnimale().getDataMorte()));
//			mappaProprietaAddizionali.put("dataMorteCertezza", (accettazione.getAnimale()==null)?(""):(accettazione.getAnimale().getDataMorteCertezza()));
//			mappaProprietaAddizionali.put("causaMorte", (accettazione.getAnimale()==null || accettazione.getAnimale().getCausaMorte()==null)?(""):(accettazione.getAnimale().getCausaMorte().getDescription()));
//			
//		}
//		if(accettazione.getAnimale().getLookupSpecie().getId()==Specie.SINANTROPO && !accettazione.getAnimale().getDecedutoNonAnagrafe())
//		{
//			mappaProprietaAddizionali.put("sterilizzato", accettazione.getSterilizzatoString());
//			String eta = "";
//			if(accettazione.getAnimale().getEta()!=null)
//			{
//				eta += accettazione.getAnimale().getEta();
//			}
//			if(accettazione.getAnimale().getDataNascita()!=null)
//			{
//				eta += "(" + accettazione.getAnimale().getDataNascita() + ")";
//			}
//			mappaProprietaAddizionali.put("eta", eta);
//			Sinantropo sinantropo = SinantropoUtil.getSinantropoByNumero(persistence, accettazione.getAnimale().getIdentificativo());
//			mappaProprietaAddizionali.put( "statoAttuale",  sinantropo.getStatoAttuale());
//			
//			String mant =  accettazione.getAnimale().getMantelloSinantropo();
//			if (mant!=null)
//				mappaProprietaAddizionali.put( "mantello",  accettazione.getAnimale().getMantelloSinantropo());
//			else
//				mappaProprietaAddizionali.put( "mantello",  "");
//
//			if(accettazione.getAnimale().getTaglia()!=null && accettazione.getAnimale().getTaglia()>0)
//				mappaProprietaAddizionali.put( "taglia",  ((LookupTaglie)persistence.find(LookupTaglie.class, accettazione.getAnimale().getTaglia())).getDescription());
//			mappaProprietaAddizionali.put( "sesso",  accettazione.getAnimale().getSesso());
//			if(accettazione.getAnimale().getRazzaSinantropo()!=null)
//				mappaProprietaAddizionali.put( "razza",  accettazione.getAnimale().getSpecieSinantropoString() + " - " + accettazione.getAnimale().getRazzaSinantropo());
//			else
//				mappaProprietaAddizionali.put( "razza",  accettazione.getAnimale().getSpecieSinantropoString());
//			RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, accettazione.getAnimale());
//			mappaProprietaAddizionali.put("dataMorte", (rsr==null || rsr.getDataEvento()==null)?(""):(rsr.getDataEvento()));
//			mappaProprietaAddizionali.put("dataMorteCertezza", (rsr==null)?(""):(rsr.getDataMorteCertezza()));
//			mappaProprietaAddizionali.put("causaMorte", (rsr==null || rsr.getDecessoValue()==null)?(""):(rsr.getDecessoValue()));
//			mappaProprietaAddizionali.put("sesso", accettazione.getAnimale().getSesso());
//		}
//		
//		
//		res.setContentType( "application/pdf" );
//		res.setHeader( "Content-Disposition","attachment; filename=\"" + outputFileName + "\";" );
//		
//		ServletOutputStream sout = res.getOutputStream();
//		sout.write( PdfReport.fillDocument( template, accettazione, mappaProprietaAddizionali ) );
//		sout.flush();
//	}
//
//}
//
//
//
package it.us.web.action.vam.accettazione;

import java.sql.Connection;
import java.util.ArrayList;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;


public class StampaCertificatoDecesso extends GenericAction  implements Specie
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
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		Accettazione accettazione = AccettazioneDAO.getAccettazione(interoFromRequest( "id" ), connection);
		req.setAttribute("Accettazione", accettazione);
		req.setAttribute( "idOpsBdr", 		IdOperazioniBdr.getInstance() );
		req.setAttribute( "idRichiesteVarie", IdRichiesteVarie.getInstance() );
		//Se il cane non è morto senza mc bisogna leggere in bdr le info sulla morte e stato attuale
		ServicesStatus status = new ServicesStatus();
		int specie = accettazione.getAnimale().getLookupSpecie().getId();
		Gatto gatto = null;
		Cane cane = null;
		if(!accettazione.getAnimale().getDecedutoNonAnagrafe())
		{
			if (specie == CANE ) 
			{
				cane = CaninaRemoteUtil.findCane(accettazione.getAnimale().getIdentificativo(), utente, status, connectionBdu, req);
				RegistrazioniCaninaResponse res	= AnimaliUtil.fetchDatiDecessoCane(cane);
				req.setAttribute("res", res);
			}
			else if (specie == GATTO) 
			{
				gatto = FelinaRemoteUtil.findGatto(accettazione.getAnimale().getIdentificativo(), utente, status, connectionBdu, req);
				RegistrazioniFelinaResponse rfr	= AnimaliUtil.fetchDatiDecessoGatto(gatto);
				req.setAttribute("res", rfr);
			}
			else if (specie == SINANTROPO) 
			{
				RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, accettazione.getAnimale());
				req.setAttribute("res", rsr);
				req.setAttribute("fuoriAsl", false);
				req.setAttribute("versoAssocCanili", false);
			}
			
			if(specie==CANE || specie==GATTO)
				RegistrazioniUtil.sincronizzaDaBdu(accettazione, null, persistence, connectionBdu, utente, false, req, connection);
		}
		req.setAttribute( "idOpsBdr", 		IdOperazioniBdr.getInstance() );
		req.setAttribute( "idRichiesteVarie", IdRichiesteVarie.getInstance() );
		req.setAttribute( "accettazione", accettazione );
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(accettazione.getAnimale(), cane, gatto, persistence, utente, status, connectionBdu, req));
		gotoPage( "popup", "/jsp/vam/accettazione/stampaCertificatoDecesso.jsp" );
	} 

}

