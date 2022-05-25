/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.SuperUtenteAll;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.constants.TipiRichiedente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.report.PdfReport;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

public class Pdf extends GenericAction implements TipiRichiedente
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		int idAcc = interoFromRequest( "id" );
		Accettazione accettazione = (Accettazione) persistence.find( Accettazione.class, idAcc );
		
		String outputFileName = accettazione.getProgressivoFormattato() + ".pdf";
		
		InputStream template = null;
		if(!accettazione.getProprietarioCognome().startsWith("<b>"))
		{
			template = this.getClass().getResourceAsStream( "Accettazione.pdf" );
		}
		else
		{
			template = this.getClass().getResourceAsStream( "AccettazioneGattoColonia.pdf" );
		}
		if (accettazione.getAnimale().getRandagio() && accettazione.getAnimale().getDecedutoNonAnagrafe())
		{
			template = this.getClass().getResourceAsStream( "AccettazioneRandagio.pdf" );
		}
		Map<String, Object> mappaProprietaAddizionali = new Hashtable<String, Object>();
		mappaProprietaAddizionali.put( "oggi",  new Date() );
		mappaProprietaAddizionali.put( "richiedenteString",  formattaRichiedente( accettazione ) );
		
		
		if((accettazione.getAnimale().getLookupSpecie().getId() == Specie.CANE || accettazione.getAnimale().getLookupSpecie().getId() == Specie.GATTO) && !accettazione.getAnimale().getDecedutoNonAnagrafe())
		{
			if(accettazione.getAnimale().getLookupSpecie().getId()==Specie.CANE)
			{
				Cane cane = CaninaRemoteUtil.findCane(accettazione.getAnimale().getIdentificativo(), utente, new ServicesStatus(), connection, req);
				mappaProprietaAddizionali.put( "razza",  (cane.getDescrizioneRazza()==null)?(""):(cane.getDescrizioneRazza()));
				mappaProprietaAddizionali.put( "taglia",  (cane.getDescrizioneTaglia()==null)?(""):(cane.getDescrizioneTaglia()));
				mappaProprietaAddizionali.put( "sesso",  cane.getSesso());
				mappaProprietaAddizionali.put( "statoAttuale",  cane.getStatoAttuale());
				if (cane.getDescrizioneMantello()!=null)
					mappaProprietaAddizionali.put( "mantello", cane.getDescrizioneMantello());
				RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( accettazione.getAnimale().getIdentificativo(), utente, null, connection, req );
				mappaProprietaAddizionali.put("dataMorte", (res==null || res.getDataEvento()==null)?(""):(res.getDataEvento()));
				mappaProprietaAddizionali.put("dataMorteCertezza", (res==null)?(""):(res.getDataMorteCertezza()));
				mappaProprietaAddizionali.put("causaMorte", (res==null || res.getDecessoValue()==null)?(""):(res.getDecessoValue()));
				mappaProprietaAddizionali.put("sesso", cane.getSesso());
				mappaProprietaAddizionali.put("sterilizzato", (cane.getSterilizzato()?("Si"):("No")));
			}
			else if(accettazione.getAnimale().getLookupSpecie().getId()==Specie.GATTO)
			{
				Gatto gatto = FelinaRemoteUtil.findGatto(accettazione.getAnimale().getIdentificativo(), utente, new ServicesStatus(), connection, req);
				mappaProprietaAddizionali.put( "razza",  (gatto.getDescrizioneRazza()==null)?(""):(gatto.getDescrizioneRazza()));
				mappaProprietaAddizionali.put( "taglia",  "Non prevista");
				mappaProprietaAddizionali.put( "sesso",  gatto.getSesso());
				mappaProprietaAddizionali.put( "statoAttuale",  gatto.getStatoAttuale());
				if (gatto.getDescrizioneMantello()!=null)
					mappaProprietaAddizionali.put( "mantello", gatto.getDescrizioneMantello());
				if(accettazione.getProprietarioCognome().startsWith("<b>"))
				{
					String indirizzo = "";
					indirizzo += accettazione.getProprietarioIndirizzo() + ", " + accettazione.getProprietarioComune();
					if(!accettazione.getProprietarioProvincia().equals(""))
						indirizzo += " (" + accettazione.getProprietarioProvincia() + ")";
					if(!accettazione.getProprietarioCap().equals(""))
						indirizzo += " - " + accettazione.getProprietarioCap();
					mappaProprietaAddizionali.put( "indirizzo", indirizzo);
				}
				RegistrazioniCaninaResponse rfr = CaninaRemoteUtil.getInfoDecesso(accettazione.getAnimale().getIdentificativo(), utente, null, connection, req );
				mappaProprietaAddizionali.put("dataMorte", (rfr==null || rfr.getDataEvento()==null)?(""):(rfr.getDataEvento()));
				mappaProprietaAddizionali.put("dataMorteCertezza", (rfr==null)?(""):(rfr.getDataMorteCertezza()));
				mappaProprietaAddizionali.put("causaMorte", (rfr==null || rfr.getDecessoValue()==null)?(""):(rfr.getDecessoValue()));
				mappaProprietaAddizionali.put("sesso", gatto.getSesso());
				mappaProprietaAddizionali.put("sterilizzato", (gatto.getSterilizzato()?("Si"):("No")));
			}
				
		}
		if(accettazione.getAnimale().getDecedutoNonAnagrafe())
		{
			if(accettazione.getAnimale().getTaglia()!=null && accettazione.getAnimale().getTaglia()>0)
				mappaProprietaAddizionali.put("taglia", ((LookupTaglie)persistence.find(LookupTaglie.class, accettazione.getAnimale().getTaglia())).getDescription());
			mappaProprietaAddizionali.put("sesso", accettazione.getAnimale().getSesso());
			mappaProprietaAddizionali.put("sterilizzato", accettazione.getSterilizzatoString());
			mappaProprietaAddizionali.put( "statoAttuale",  "Decesso");
			mappaProprietaAddizionali.put( "sesso",  accettazione.getAnimale().getSesso());
			if(accettazione.getAnimale().getLookupSpecie().getId()== Specie.CANE || accettazione.getAnimale().getLookupSpecie().getId()== Specie.GATTO)
			{
				if(accettazione.getAnimale().getRazza()!=null && accettazione.getAnimale().getRazza()>0 && accettazione.getAnimale().getLookupSpecie().getId()==Specie.CANE)
					mappaProprietaAddizionali.put("razza", (CaninaRemoteUtil.getRazza(accettazione.getAnimale().getRazza(), connection, req).getDescription()));
				else if(accettazione.getAnimale().getRazza()!=null && accettazione.getAnimale().getRazza()>0 && accettazione.getAnimale().getLookupSpecie().getId()==Specie.GATTO)
					mappaProprietaAddizionali.put("razza", (FelinaRemoteUtil.getRazza(accettazione.getAnimale().getRazza(), connection, req).getDescription()));
				if(accettazione.getAnimale().getMantello()!=null && accettazione.getAnimale().getMantello()>=0 && accettazione.getAnimale().getLookupSpecie().getId()==Specie.CANE)
					mappaProprietaAddizionali.put("mantello", (CaninaRemoteUtil.getMantello(accettazione.getAnimale().getMantello(), connection, req).getDescription()));
				else if(accettazione.getAnimale().getMantello()!=null && accettazione.getAnimale().getMantello()>=0 && accettazione.getAnimale().getLookupSpecie().getId()==Specie.GATTO)
					mappaProprietaAddizionali.put("mantello", (FelinaRemoteUtil.getMantello(accettazione.getAnimale().getMantello(), connection, req).getDescription()));
			}
			else
			{
				if(accettazione.getAnimale().getMantelloSinantropo()!=null)
					mappaProprietaAddizionali.put( "mantello",  accettazione.getAnimale().getMantelloSinantropo());
				if(accettazione.getAnimale().getRazzaSinantropo()!=null)
					mappaProprietaAddizionali.put( "razza",  accettazione.getAnimale().getSpecieSinantropoString() + " - " + accettazione.getAnimale().getRazzaSinantropo());
				else
					mappaProprietaAddizionali.put( "razza",  accettazione.getAnimale().getSpecieSinantropoString());
			}
			
			if(accettazione.getAnimale().getLookupSpecie().getId()== Specie.GATTO && accettazione.getProprietarioCognome().startsWith("<b>"))
			{
				String indirizzo = "";
				indirizzo += accettazione.getProprietarioIndirizzo() + ", " + accettazione.getProprietarioComune();
				if(!accettazione.getProprietarioProvincia().equals(""))
					indirizzo += " (" + accettazione.getProprietarioProvincia() + ")";
				if(!accettazione.getProprietarioCap().equals(""))
					indirizzo += " - " + accettazione.getProprietarioCap();
				mappaProprietaAddizionali.put( "indirizzo", indirizzo);
			}
			mappaProprietaAddizionali.put("dataMorte", (accettazione.getAnimale()==null || accettazione.getAnimale().getDataMorte()==null)?(""):(accettazione.getAnimale().getDataMorte()));
			mappaProprietaAddizionali.put("dataMorteCertezza", (accettazione.getAnimale()==null)?(""):(accettazione.getAnimale().getDataMorteCertezza()));
			mappaProprietaAddizionali.put("causaMorte", (accettazione.getAnimale()==null || accettazione.getAnimale().getCausaMorte()==null)?(""):(accettazione.getAnimale().getCausaMorte().getDescription()));
			
		}
		if(accettazione.getAnimale().getLookupSpecie().getId()==Specie.SINANTROPO && !accettazione.getAnimale().getDecedutoNonAnagrafe())
		{
			mappaProprietaAddizionali.put("sterilizzato", accettazione.getSterilizzatoString());
			Sinantropo sinantropo = SinantropoUtil.getSinantropoByNumero(persistence, accettazione.getAnimale().getIdentificativo());
			mappaProprietaAddizionali.put( "statoAttuale",  sinantropo.getStatoAttuale());
			if(accettazione.getAnimale().getMantelloSinantropo()!=null)
				mappaProprietaAddizionali.put( "mantello",  accettazione.getAnimale().getMantelloSinantropo());
			if(accettazione.getAnimale().getTaglia()!=null && accettazione.getAnimale().getTaglia()>0)
				mappaProprietaAddizionali.put( "taglia",  ((LookupTaglie)persistence.find(LookupTaglie.class, accettazione.getAnimale().getTaglia())).getDescription());
			mappaProprietaAddizionali.put( "sesso",  accettazione.getAnimale().getSesso());
			if(accettazione.getAnimale().getRazzaSinantropo()!=null)
				mappaProprietaAddizionali.put( "razza",  accettazione.getAnimale().getSpecieSinantropoString() + " - " + accettazione.getAnimale().getRazzaSinantropo());
			else
				mappaProprietaAddizionali.put( "razza",  accettazione.getAnimale().getSpecieSinantropoString());
			RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, accettazione.getAnimale());
			mappaProprietaAddizionali.put("dataMorte", (rsr==null || rsr.getDecessoValue()==null)?(""):(rsr.getDecessoValue()));
			mappaProprietaAddizionali.put("dataMorteCertezza", (rsr==null)?(""):(rsr.getDataMorteCertezza()));
			mappaProprietaAddizionali.put("causaMorte", (rsr==null || rsr.getDecessoValue()==null)?(""):(rsr.getDecessoValue()));
			mappaProprietaAddizionali.put("sesso", accettazione.getAnimale().getSesso());
		}
		
		
		res.setContentType( "application/pdf" );
		res.setHeader( "Content-Disposition","attachment; filename=\"" + outputFileName + "\";" );
		
		ServletOutputStream sout = res.getOutputStream();
		sout.write( PdfReport.fillDocument( template, accettazione, mappaProprietaAddizionali ) );
		sout.flush();
	}

	private String formattaRichiedente(Accettazione accettazione)
	{
		StringBuffer ret = new StringBuffer();
		
		ret.append( "Richiesta effettuata da: " + accettazione.getLookupTipiRichiedente().getDescription() );
		
		if( accettazione.getRichiedenteProprietario() )
		{
			ret.append( "\n\nCoincide con il proprietario" );
		}
		else
		{
			switch ( accettazione.getLookupTipiRichiedente().getId() )
			{
			//case PERSONALE_INTERNO:
				//ret.append( "\n\n" + accettazione.getRichiedenteAslUser().toString() );
				//break;
			case PERSONALE_ASL:
				ret.append( " di " + accettazione.getRichiedenteAsl().getDescription() );
				Iterator<SuperUtenteAll> u = accettazione.getPersonaleAsl().iterator();
				while(u.hasNext())
				{
					ret.append( "\n" + u.next().toString() );
				}
				break;
			case ASSOCIAZIONE:
				ret.append( "\n\n" + accettazione.getRichiedenteAssociazione().getDescription() );
				appendNCCFD( accettazione, ret );
				break;
			case ALTRO:
				ret.append( "\n\n" + accettazione.getRichiedenteAltro() );
				appendNCCFD( accettazione, ret );
				break;
			case PRIVATO:
				appendNCCFD( accettazione, ret );
				break;
			default:  //forza pubblica
				appendNCCFD( accettazione, ret );
				ret.append( "\n\nComando: " + accettazione.getRichiedenteForzaPubblicaComando() );
				ret.append( "\nDel comune di: " + accettazione.getRichiedenteForzaPubblicaComune() );
				ret.append( " (" + accettazione.getRichiedenteForzaPubblicaProvincia() + ")" );
				break;
			}
			
			if(!accettazione.getPersonaleInterno().isEmpty())
			{
				ret.append( "\n\nIntervento Personale Interno: ");
			}
			Iterator<LookupPersonaleInterno> u = accettazione.getPersonaleInterno().iterator();
			while(u.hasNext())
			{
				ret.append( "\n" + u.next().getNominativo() );
			}
			
		}
		
		return ret.toString();
	}

	/**
	 * concatena: Nome, cognome, codice fiscale e documento
	 * @param ret
	 */
	private void appendNCCFD( Accettazione accettazione, StringBuffer ret )
	{
		if(accettazione.getRichiedenteCognome()!=null && !accettazione.getRichiedenteCognome().equals(""))
			ret.append( "\n\nCognome: " + accettazione.getRichiedenteCognome() );
		if(accettazione.getRichiedenteNome()!=null && !accettazione.getRichiedenteNome().equals(""))
			ret.append( "\nNome: " + accettazione.getRichiedenteNome() );
		if(accettazione.getRichiedenteCodiceFiscale()!=null && !accettazione.getRichiedenteCodiceFiscale().equals(""))
			ret.append( "\nCodice Fiscale: " + accettazione.getRichiedenteCodiceFiscale() );
		if(accettazione.getRichiedenteDocumento()!=null && !accettazione.getRichiedenteDocumento().equals(""))
			ret.append( "\nDocumento: " + accettazione.getRichiedenteDocumento() );
		if(accettazione.getRichiedenteTelefono()!=null && !accettazione.getRichiedenteTelefono().equals(""))
			ret.append( "\nTelefono: " + accettazione.getRichiedenteTelefono() );
		if(accettazione.getRichiedenteResidenza()!=null && !accettazione.getRichiedenteResidenza().equals(""))
			ret.append( "\nResidenza: " + accettazione.getRichiedenteResidenza() );
	}

}



















