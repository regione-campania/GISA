/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.dwr.vam.accettazione;


import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.remoteBean.Registrazioni;
import it.us.web.bean.remoteBean.RegistrazioniInterface;
import it.us.web.bean.vam.AccettazioneNoH;
import it.us.web.bean.vam.AnimaleNoH;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.AttivitaBdrNoH;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.ClinicaNoH;
import it.us.web.bean.vam.StatoTrasferimento;
import it.us.web.bean.vam.TrasferimentoNoH;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazioneCondizionate;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdOperazioniInBdr;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.constants.TipiRichiedente;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.util.dwr.TestAccettazioneDAO;
import it.us.web.dao.vam.AnimaleDAONoH;
import it.us.web.dao.vam.CartellaClinicaDAONoH;
import it.us.web.dao.vam.ClinicaDAONoH;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

import java.beans.Statement;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Test 
{
	Logger logger = LoggerFactory.getLogger( Test.class );
	
	public String check( String[] opSelezionateArray, String idUtente, String idAnimale, String idRichiedenteS, String idClinicaDestinazione, String tipoEsecuzione, Integer idTipoTrasf, boolean intraFuoriAsl, boolean versoAssocCanili, HttpServletRequest req )
	{
		String ret = "";
		Connection connectionVam = null;
		Connection connection    = null;
		try
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			Context ctxVam = new InitialContext();
			javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
			connectionVam = dsVam.getConnection();
			aggiornaConnessioneApertaSessione(req);
			//BUtente utente 										 	 = (BUtente)persistence.find(BUtente.class, Integer.parseInt(idUtente));
			BUtente utente = UtenteDAO.getUtente(Integer.parseInt(idUtente));
			Integer idRichiedente 									 = null;
			ClinicaNoH clinicaDestinazione								 = null;
			System.out.println("test.check 2");
			if(tipoEsecuzione.equals("accettazione"))
				idRichiedente = Integer.parseInt(idRichiedenteS);
			if(tipoEsecuzione.equals("trasferimento"))
				//clinicaDestinazione = (Clinica)persistence.find(Clinica.class, Integer.parseInt(idClinicaDestinazione));
				clinicaDestinazione = ClinicaDAONoH.getClinica(Integer.parseInt(idClinicaDestinazione), connectionVam);
			Set<LookupOperazioniAccettazione> opSelezionate 	 	 = convertToSet(opSelezionateArray, connectionVam);
			Iterator<LookupOperazioniAccettazione> opSelezionateIter = opSelezionate.iterator();
			//Animale animale 									 	 = getAnimale(Integer.parseInt(idAnimale), persistence);
			AnimaleNoH animale 									 	 = AnimaleDAONoH.getAnimale(Integer.parseInt(idAnimale), connectionVam);
			RegistrazioniInterface opEffettuabiliBdr			 	 = AnimaliUtil.findRegistrazioniEffettuabili( connectionVam, animale, utente, connection ,req);
			
			while(opSelezionateIter.hasNext())
			{
				boolean anomaliaRiscontrata = false;
				LookupOperazioniAccettazione operazione = opSelezionateIter.next();
				
				if(tipoEsecuzione.equals("accettazione") && nonEffettuabileInBdr(operazione, opEffettuabiliBdr, idTipoTrasf, connectionVam, connection, animale, intraFuoriAsl, versoAssocCanili, req) && !verificaRichiedenteAssociazione(idRichiedente, operazione) && !operazioneAbilitanteSelezionata(opSelezionate, operazione))
				{
					ret += "- " + operazione.getDescription() + " non risulta effettuabile in BDR.\n" ;
					anomaliaRiscontrata=true;
				}
				if(tipoEsecuzione.equals("trasferimento") && nonEffettuabileInBdr(operazione, opEffettuabiliBdr, idTipoTrasf, connectionVam, connection, animale, intraFuoriAsl, versoAssocCanili, req) && !operazioneAbilitanteSelezionata(opSelezionate, operazione))
				{
					ret += "- " + operazione.getDescription() + " non risulta effettuabile in BDR.\n" ;
					anomaliaRiscontrata=true;
				}
				if(tipoEsecuzione.equals("accettazione") && !anomaliaRiscontrata && nonEffettuabileAnimaliDeceduti(operazione, animale, utente, connectionVam, connection, req) && !verificaRichiedenteAssociazione(idRichiedente, operazione) && !operazioneAbilitanteSelezionata(opSelezionate, operazione))
				{
					ret += "- " + operazione.getDescription() + " non risulta effettuabile per animali deceduti.\n" ;
					anomaliaRiscontrata=true;	
				}
				if(tipoEsecuzione.equals("trasferimento") && !anomaliaRiscontrata && nonEffettuabileAnimaliDeceduti(operazione, animale, utente, connectionVam, connection, req) && !operazioneAbilitanteSelezionata(opSelezionate, operazione))
				{
					ret += "- " + operazione.getDescription() + " non risulta effettuabile per animali deceduti.\n" ;
					anomaliaRiscontrata=true;	
				}
				if(tipoEsecuzione.equals("accettazione") && !anomaliaRiscontrata && nonEffettuabileAnimaliVivi(operazione, animale, utente, connectionVam, connection, req) &&  !verificaRichiedenteAssociazione(idRichiedente, operazione) && !operazioneAbilitanteSelezionata(opSelezionate, operazione))
				{
					ret += "- " + operazione.getDescription() + " non risulta effettuabile per animali vivi.\n" ;
					anomaliaRiscontrata=true;
				}
				if(tipoEsecuzione.equals("trasferimento") && !anomaliaRiscontrata && nonEffettuabileAnimaliVivi(operazione, animale, utente, connectionVam, connection, req) && !operazioneAbilitanteSelezionata(opSelezionate, operazione))
				{
					ret += "- " + operazione.getDescription() + " non risulta effettuabile per animali vivi.\n" ;
					anomaliaRiscontrata=true;
				}
				if(tipoEsecuzione.equals("accettazione") && !anomaliaRiscontrata && nonEffettuabileAnimaliFuoriAsl(operazione, animale, utente, connectionVam, connection, req) && nonEffettuabileAnimaliFuoriAslDeceduti(operazione, animale, utente, connectionVam, connection, req) && !verificaRichiedenteAssociazione(idRichiedente, operazione) && !operazioneAbilitanteSelezionata(opSelezionate, operazione))
				{
					ret += "- " + operazione.getDescription() + " non risulta effettuabile per animali fuori asl.\n" ;
					anomaliaRiscontrata=true;
				}
				if(tipoEsecuzione.equals("trasferimento") && !anomaliaRiscontrata && nonEffettuabileAnimaliFuoriAsl(operazione, animale, utente, clinicaDestinazione, connectionVam) && nonEffettuabileAnimaliFuoriAslDeceduti(operazione, animale, utente, connectionVam, connection, req) && !operazioneAbilitanteSelezionata(opSelezionate, operazione))
				{
					ret += "- " + operazione.getDescription() + " non risulta effettuabile per animali fuori asl.\n" ;
					anomaliaRiscontrata=true;
				}
				LookupOperazioniAccettazione opDisabilitante = operazioneDisabilitanteSelezionata(opSelezionate, operazione);
				if(!anomaliaRiscontrata && opDisabilitante!=null)
				{
					ret += "- " + operazione.getDescription() + " non risulta effettuabile perchè è stata selezionata l'operazione " + opDisabilitante.getDescription() + ".\n";
					anomaliaRiscontrata=true;
				}
			}
			
			if(!ret.equals(""))
				ret="L'accettazione non è registrabile per i seguenti motivi:\n"+ret;
			
			connection.close();
			aggiornaConnessioneChiusaSessione(req);
			connectionVam.close();
			aggiornaConnessioneChiusaSessione(req);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
		finally
		{
			if(connectionVam!=null)
			{
				try
				{
					connectionVam.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			if(connection!=null)
			{
				try
				{
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		}
		return ret;
		
	}
	
	private boolean nonEffettuabileInBdr(LookupOperazioniAccettazione operazione, RegistrazioniInterface opEffettuabiliBdr, Integer idTipoTrasf, Connection connectionVam, Connection connection, AnimaleNoH animale, boolean intraFuoriAsl,boolean versoAssocCanili, HttpServletRequest req) throws Exception
	{
		Integer idTipoPrevistaInBdu = null;
		boolean trasfPossibile = false;
		if(operazione.getId()==IdOperazioniBdr.trasferimento)
		{
			idTipoPrevistaInBdu = RegistrazioniUtil.getIdTipoBdrPreAcc(animale, idTipoTrasf, intraFuoriAsl, versoAssocCanili, operazione, connectionVam, connection, req);
			System.out.println("id tipo prevista in bdu: " + idTipoPrevistaInBdu);
			if(idTipoPrevistaInBdu==IdOperazioniInBdr.trasferimento)
				trasfPossibile = opEffettuabiliBdr.getTrasferimento()!=null && opEffettuabiliBdr.getTrasferimento();
			else if(idTipoPrevistaInBdu==IdOperazioniInBdr.adozioneDaCanile || idTipoPrevistaInBdu==IdOperazioniInBdr.adozioneDaColonia)
				trasfPossibile = opEffettuabiliBdr.getAdozione()!=null && opEffettuabiliBdr.getAdozione();
			else if(idTipoPrevistaInBdu==IdOperazioniInBdr.cessione)
				trasfPossibile = opEffettuabiliBdr.getCessione()!=null && opEffettuabiliBdr.getCessione();
			else if(idTipoPrevistaInBdu==IdOperazioniInBdr.adozioneFuoriAsl)
				trasfPossibile = opEffettuabiliBdr.getAdozioneFuoriAsl()!=null && opEffettuabiliBdr.getAdozioneFuoriAsl();
			else if(idTipoPrevistaInBdu==IdOperazioniInBdr.adozioneVersoAssocCanili)
				trasfPossibile = opEffettuabiliBdr.getAdozioneVersoAssocCanili()!=null && opEffettuabiliBdr.getAdozioneVersoAssocCanili();
			else if(idTipoPrevistaInBdu==IdOperazioniInBdr.trasferimentoFuoriRegione)
				trasfPossibile = opEffettuabiliBdr.getTrasfRegione()!=null && opEffettuabiliBdr.getTrasfRegione();
			else if(idTipoPrevistaInBdu==IdOperazioniInBdr.trasferimentoResidenzaProprietario)
				trasfPossibile = opEffettuabiliBdr.getTrasferimentoResidenzaProp()!=null && opEffettuabiliBdr.getTrasferimentoResidenzaProp();
		}
				
		System.out.println("operazione id: "  + operazione.getId());
		System.out.println("trasferimento: "  + IdOperazioniBdr.trasferimento);
		System.out.println("trasfPossibile: " + trasfPossibile);
		
		return (operazione.getId() == IdOperazioniBdr.adozione && intraFuoriAsl==false && (opEffettuabiliBdr.getAdozione()==null || !opEffettuabiliBdr.getAdozione())) ||
			   (operazione.getId() == IdOperazioniBdr.adozione && intraFuoriAsl && (opEffettuabiliBdr.getAdozioneFuoriAsl()==null || !opEffettuabiliBdr.getAdozioneFuoriAsl())) ||
			   (operazione.getId() == IdOperazioniBdr.adozione && versoAssocCanili && (opEffettuabiliBdr.getAdozioneVersoAssocCanili()==null || !opEffettuabiliBdr.getAdozioneVersoAssocCanili())) ||
			   (operazione.getId() == IdOperazioniBdr.furto 			&& (opEffettuabiliBdr.getFurto()==null || !opEffettuabiliBdr.getFurto())) ||
			   (operazione.getId() == IdOperazioniBdr.decesso 			&& (opEffettuabiliBdr.getDecesso()==null || !opEffettuabiliBdr.getDecesso())) ||
			   (operazione.getId() == IdOperazioniBdr.trasferimento 	&& !trasfPossibile) ||
			   (operazione.getId() == IdOperazioniBdr.smarrimento 		&& (opEffettuabiliBdr.getSmarrimento()==null || !opEffettuabiliBdr.getSmarrimento())) ||
			   (operazione.getId() == IdOperazioniBdr.ritrovamento 		&& (opEffettuabiliBdr.getRitrovamento()==null || !opEffettuabiliBdr.getRitrovamento())) ||
			   (operazione.getId() == IdOperazioniBdr.sterilizzazione   && (opEffettuabiliBdr.getSterilizzazione()==null || !opEffettuabiliBdr.getSterilizzazione())) ||
			   (operazione.getId() == IdOperazioniBdr.ritrovamentoSmarrNonDenunciato && (opEffettuabiliBdr.getRitrovamentoSmarrNonDenunciato()==null || !opEffettuabiliBdr.getRitrovamentoSmarrNonDenunciato())) ||
			   (operazione.getId() == IdOperazioniBdr.passaporto 		&&(opEffettuabiliBdr.getPassaporto()==null || !opEffettuabiliBdr.getPassaporto())) ||
			   (operazione.getId() == IdOperazioniBdr.prelievoDna 		&&(opEffettuabiliBdr.getPrelievoDna()==null || !opEffettuabiliBdr.getPrelievoDna()))||
			   (operazione.getId() == IdOperazioniBdr.prelievoLeishmania 		&&(opEffettuabiliBdr.getPrelievoLeishmania()==null || !opEffettuabiliBdr.getPrelievoLeishmania()))||
			   (operazione.getId() == IdOperazioniBdr.rinnovoPassaporto 		&&(opEffettuabiliBdr.getRinnovoPassaporto()==null || !opEffettuabiliBdr.getRinnovoPassaporto()))||
			   (operazione.getId() == IdOperazioniBdr.ricattura 		&&(opEffettuabiliBdr.getRicattura()==null || !opEffettuabiliBdr.getRicattura())
			   )
			   ;
		
	}
	
	//Se il richiedente è un'associazione, il ritrovamento si può fare in ogni caso
	private boolean verificaRichiedenteAssociazione(int idRichiedente, LookupOperazioniAccettazione operazione)
	{
		return idRichiedente==TipiRichiedente.ASSOCIAZIONE && operazione.getId()==IdOperazioniBdr.ritrovamento;
	}
	
	//Se un'operazione abilitante è stata selezionata, allora l'operazione che stiamo esaminando si può fare in ogni caso
	private boolean operazioneAbilitanteSelezionata(Set<LookupOperazioniAccettazione> opSelezionate, LookupOperazioniAccettazione operazione)
	{
		Iterator<LookupOperazioniAccettazione> opSelezionateIter = opSelezionate.iterator();
		while(opSelezionateIter.hasNext())
		{
			LookupOperazioniAccettazione opTemp = opSelezionateIter.next();
			Iterator<LookupOperazioniAccettazioneCondizionate> opCondizionate = opTemp.getOperazioniCondizionate().iterator();
			while(opCondizionate.hasNext())
			{
				LookupOperazioniAccettazioneCondizionate opCondizionateTemp = opCondizionate.next();
				if(opCondizionateTemp.getOperazioneCondizionata().getId()==operazione.getId() && opCondizionateTemp.getOperazioneDaFare().equals("enable"))
					return true;
			}
		}
		return false;
	}
	
	//Se un'operazione disabilitante è stata selezionata, allora l'operazione che stiamo esaminando non si può fare in ogni caso
	private LookupOperazioniAccettazione operazioneDisabilitanteSelezionata(Set<LookupOperazioniAccettazione> opSelezionate, LookupOperazioniAccettazione operazione)
	{
		Iterator<LookupOperazioniAccettazione> opSelezionateIter = opSelezionate.iterator();
		while(opSelezionateIter.hasNext())
		{
			LookupOperazioniAccettazione opTemp = opSelezionateIter.next();
			Iterator<LookupOperazioniAccettazioneCondizionate> opCondizionate = opTemp.getOperazioniCondizionate().iterator();
			while(opCondizionate.hasNext())
			{
				LookupOperazioniAccettazioneCondizionate opCondizionateTemp = opCondizionate.next();
				if(opCondizionateTemp.getOperazioneCondizionata().getId()==operazione.getId() && opCondizionateTemp.getOperazioneDaFare().equals("disable"))
					return opCondizionateTemp.getOperazioneCondizionante();
			}
		}
		return null;
	
	}
	
	//Verifico se non è possibile per animali deceduti
	private boolean nonEffettuabileAnimaliDeceduti(LookupOperazioniAccettazione operazione, AnimaleNoH animale, BUtente utente, Connection connectionVam,Connection connection,HttpServletRequest req) throws Exception
	{
		Date dataDecesso = null;
		if(animale.getLookupSpecie().getId()==SpecieAnimali.cane && CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection ,req)!=null)
			dataDecesso = CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection ,req).getDataEvento();
		else if(animale.getLookupSpecie().getId()==SpecieAnimali.gatto && FelinaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection, req )!=null)
			dataDecesso = FelinaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection ,req).getDataEvento();
		else if(animale.getLookupSpecie().getId()==SpecieAnimali.sinantropo && SinantropoUtil.getInfoDecesso(connectionVam, animale)!=null)
			dataDecesso = SinantropoUtil.getInfoDecesso(connectionVam, animale).getDataEvento();
		return !(operazione.getEffettuabileDaMorto()==null || operazione.getEffettuabileDaMorto()) && (animale.getDecedutoNonAnagrafe() || dataDecesso!=null);
	}
	
	//Verifico se non è possibile per animali vivi
	private boolean nonEffettuabileAnimaliVivi(LookupOperazioniAccettazione operazione, AnimaleNoH animale, BUtente utente, Connection connectionVam,Connection connection,HttpServletRequest req) throws Exception
	{
		Date dataDecesso = null;
		if(animale.getLookupSpecie().getId()==SpecieAnimali.cane && CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection,req )!=null)
			dataDecesso = CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection, req ).getDataEvento();
		else if(animale.getLookupSpecie().getId()==SpecieAnimali.gatto && FelinaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection,req )!=null)
			dataDecesso = FelinaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection, req ).getDataEvento();
		else if(animale.getLookupSpecie().getId()==SpecieAnimali.sinantropo && SinantropoUtil.getInfoDecesso(connectionVam, animale)!=null)
			dataDecesso = SinantropoUtil.getInfoDecesso(connectionVam, animale).getDataEvento();
		return !(operazione.getEffettuabileDaVivo()==null || operazione.getEffettuabileDaVivo()) && !animale.getDecedutoNonAnagrafe() && dataDecesso==null ;
	}
	
	//Verifico se non è possibile per animali fuori asl, nel caso di accettazione
	private boolean nonEffettuabileAnimaliFuoriAsl(LookupOperazioniAccettazione operazione, AnimaleNoH animale, BUtente utente, Connection connectionVam,Connection connection,HttpServletRequest req) throws Exception
	{
		boolean fuoriAsl = false;
		Integer asl = null;
		ArrayList<LookupAsl> listAsl = null;
		if(animale.getLookupSpecie().getId()==SpecieAnimali.cane)
		{
			ProprietarioCane prop = CaninaRemoteUtil.findProprietario(animale.getIdentificativo(), utente, connection, req);
			if(prop!=null)
				asl =CaninaRemoteUtil.findProprietario(animale.getIdentificativo(), utente, connection, req).getAsl();
			if(asl!=null)
				fuoriAsl = asl!=utente.getClinica().getLookupAsl().getId();
		}
		else if(animale.getLookupSpecie().getId()==SpecieAnimali.gatto)
		{
			ProprietarioGatto prop = FelinaRemoteUtil.findProprietario(animale.getIdentificativo(), utente, connection, req);
			if(prop!=null)
				asl =FelinaRemoteUtil.findProprietario(animale.getIdentificativo(), utente, connection, req).getAsl();
			if(asl!=null)
				fuoriAsl = asl!=utente.getClinica().getLookupAsl().getId();
		}
		return !(operazione.getEffettuabileFuoriAsl()==null || operazione.getEffettuabileFuoriAsl()) && animale.getLookupSpecie().getId()!=SpecieAnimali.sinantropo && fuoriAsl;
	}
	
	//Verifico se non è possibile per animali fuori asl, nel caso di trasferimento
	private boolean nonEffettuabileAnimaliFuoriAsl(LookupOperazioniAccettazione operazione, AnimaleNoH animale, BUtente utente, ClinicaNoH clinicaDestinazione, Connection connectionVam) throws UnsupportedEncodingException
	{
		boolean fuoriAsl = false;
		LookupAsl asl = null;
		ArrayList<LookupAsl> listAsl = null;
		if(animale.getLookupSpecie().getId()==SpecieAnimali.cane)
		{
			//Cane cane = ((ArrayList<Cane>)persistence.getNamedQuery("GetCaneByMc").setString( "mc", animale.getIdentificativo()).list()).get(0);
			/*listAsl = (ArrayList<LookupAsl>)persistence.getNamedQuery("GetProprietarioAslCane").setInteger( "idCane", cane.getId()).list();
			if(!listAsl.isEmpty())
				asl = listAsl.get(0);
			if(asl!=null)
				fuoriAsl = asl.getId()!=clinicaDestinazione.getLookupAsl().getId();*/
		}
		else if(animale.getLookupSpecie().getId()==SpecieAnimali.gatto)
		{
			//Gatto gatto = ((ArrayList<Gatto>)persistence.getNamedQuery("GetGattoByMc").setString( "mc", animale.getIdentificativo()).list()).get(0);
			/*listAsl = (ArrayList<LookupAsl>)persistence.getNamedQuery("GetProprietarioAslGatto").setInteger( "idGatto", gatto.getId()).list();
			if(!listAsl.isEmpty())
				asl = listAsl.get(0);
			if(asl!=null)
				fuoriAsl = asl.getId()!=clinicaDestinazione.getLookupAsl().getId();*/
		}
		return !(operazione.getEffettuabileFuoriAsl()==null || operazione.getEffettuabileFuoriAsl()) && animale.getLookupSpecie().getId()!=SpecieAnimali.sinantropo && fuoriAsl;
	}
	
	//Verifico se non è possibile per animali fuori asl deceduti
	private boolean nonEffettuabileAnimaliFuoriAslDeceduti(LookupOperazioniAccettazione operazione, AnimaleNoH animale, BUtente utente, Connection connectionVam, Connection connection,HttpServletRequest req) throws Exception
	{
		Date dataDecesso = null;
		if(animale.getLookupSpecie().getId()==SpecieAnimali.cane && CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection , req)!=null)
			dataDecesso = CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection, req ).getDataEvento();
		else if(animale.getLookupSpecie().getId()==SpecieAnimali.gatto && FelinaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection , req)!=null)
			dataDecesso = FelinaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, new ServicesStatus(), connection, req ).getDataEvento();
		else if(animale.getLookupSpecie().getId()==SpecieAnimali.sinantropo && SinantropoUtil.getInfoDecesso(connectionVam, animale)!=null)
			dataDecesso = SinantropoUtil.getInfoDecesso(connectionVam, animale).getDataEvento();
		return !((operazione.getEffettuabileFuoriAslMorto()==null || operazione.getEffettuabileFuoriAslMorto()) && (animale.getDecedutoNonAnagrafe() || dataDecesso!=null));
	}
	
	private Set<LookupOperazioniAccettazione> convertToSet(String[] opSelezionateArray, Connection connection) throws Exception
	{
		int i = 0;
		Set<LookupOperazioniAccettazione> opSelezionate = new HashSet<LookupOperazioniAccettazione>();
		while(i<opSelezionateArray.length)
		{
			opSelezionate.add(TestAccettazioneDAO.getOperazioneAccettazione(Integer.parseInt(opSelezionateArray[i]), connection));
			//opSelezionate.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, Integer.parseInt(opSelezionateArray[i]) )  );
			i++;
		}
		return opSelezionate;
	}
	
	//Controlla se è possibile aprire un'accettazione in accordo col documento "CR VAM - vincoli su inserimento accettazione"
	//Se si può aprire ritorna una stringa vuota, altrimenti il messaggio indicante il motivo
	public String possibileAprire(String idAnimale, String idUtente,HttpServletRequest req)
	{
		Connection connectionBdu = null;
		Connection connection    = null;
		try
		{
			Context ctxBdu = new InitialContext();
			javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
			connectionBdu = dsBdu.getConnection();
			aggiornaConnessioneApertaSessione(req);
			
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			
			
			
			BUtenteAll utente 										 = UtenteDAO.getUtenteAll(Integer.parseInt(idUtente));
			//Animale animale 									 	 = getAnimale(Integer.parseInt(idAnimale), connection);
			AnimaleNoH animale 									 	 = AnimaleDAONoH.getAnimale(Integer.parseInt(idAnimale), connection);
			AccettazioneNoH accettazione 								 = null;
			String messaggio 										 = "";
			ClinicaNoH clinica										 = null;
			CartellaClinicaNoH cc 									 = null;
			ClinicaNoH clinicaDest 									 = null;
			TrasferimentoNoH t										 = null;
			ClinicaNoH clinicaUtente = ClinicaDAONoH.getClinica(utente.getClinica().getId(), connection);
	
			
			
			if((accettazione = animale.getAccettazioneConOpDaCompletare())!=null)
				messaggio = "Apertura impossibile: esiste già l'accettazione con n° " + accettazione.getProgressivoFormattato() + " che ha delle operazioni in sospeso.";
			else if((accettazione = animale.getAccettazioneConCcDaAprire())!=null && accettazione.getObbligoAprireCartellaClinica())
				messaggio = "Apertura impossibile: esiste già l'accettazione con n° " + accettazione.getProgressivoFormattato() + " su cui deve essere aperta la cartella clinica.";
			else if((accettazione = AnimaliUtil.getAccettazioneConCcAprireRitrovamentoCambioDetentore(animale, req))!=null)
				messaggio = "Apertura impossibile: esiste già l'accettazione con n° " + accettazione.getProgressivoFormattato() + " su cui deve essere aperta la cartella clinica";
			else if((clinicaDest = getClinicaCcChiusaperTrasferimento(animale, connection, utente))!=null && clinicaDest.getId()!=utente.getClinica().getId())
				messaggio = "Apertura impossibile: animale in trasferimento verso la clinica " + clinicaDest.getNomeBreve();
			else if((t = getTrasferimentoAppesoCriuvVersoClinicaCorrente(animale, utente, connection))!=null)
				messaggio = "Apertura impossibile: animale in trasferimento (previa approvazione Criuv) verso la tua clinica";
			
			
			Registrazioni reg = CaninaRemoteUtil.getRitrovamentoCambioDetentore(animale.getIdentificativo(),connectionBdu,req);
			if(CaninaRemoteUtil.esisteRitrovamentoCambioDetentoreBdu(reg))
				messaggio = "Apertura impossibile: registrare la restituzione al proprietario in Bdu";
			else if((accettazione = animale.getAccettazionePerSmaltimento())!=null && animale.getDataSmaltimentoCarogna()==null)
				messaggio = "Apertura impossibile: esiste già l'accettazione con n° " + accettazione.getProgressivoFormattato() + " su cui deve essere registrato il trasporto spoglie. Se si deve inserire una cartella nescroscopica proseguire da questa accettazione.";
			else if((cc = AnimaleDAONoH.getCcRicovero(animale, connection))!=null && (!animaleInArrivo(animale,clinicaUtente) || !animaleRientrante(animale,utente,connection)))
				messaggio = "Apertura impossibile: l'animale è ricoverato presso " + ((cc.getEnteredBy().getClinica()==utente.getClinica())?("la tua clinica"):("la clinica " + cc.getEnteredBy().getClinica().getNome())) + " con la cc numero " + cc.getNumero();
			else if((cc = getCcConOpDaCompletare(animale, connection, req))!=null && animale.getLookupSpecie().getId()!=Specie.SINANTROPO)
				messaggio = "Apertura impossibile: esiste la cc " + cc.getNumero() + " con registrazioni in sospeso nelle dimissioni";
			else if((animale.getEsameNecroscopico()!=null || animale.getDataSmaltimentoCarogna()!=null) && (!animaleInArrivo(animale,clinicaUtente) || !animaleRientrante(animale,utente,connection)))
				messaggio = "Apertura impossibile: l'animale è morto ed è stata fatta una necroscopia o registrato il trasporto spoglie.";
			connectionBdu.close();
			aggiornaConnessioneChiusaSessione(req);
			connection.close();
			aggiornaConnessioneChiusaSessione(req);
			
			return messaggio;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
		finally
		{
			if(connectionBdu!=null)
			{
				try
				{
					connectionBdu.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			if(connection!=null)
			{
				try
				{
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		}
	}
	
	private TrasferimentoNoH getTrasferimentoAppesoCriuvVersoClinicaCorrente(AnimaleNoH animale, BUtenteAll utente, Connection connection) throws SQLException
	{
		String tipo = "I";
		ArrayList<TrasferimentoNoH> trasf = ClinicaDAONoH.getTrasferimenti(tipo, utente.getClinica().getId(), animale.getId(), StatoTrasferimento.ATTESA_CRIUV, connection);
		
		if(!trasf.isEmpty())
			return trasf.get(0);
		return null;
	}
	
	private CartellaClinicaNoH getCcConOpDaCompletare(AnimaleNoH animale, Connection connectionVam,HttpServletRequest req) throws Exception
	{
		
		ArrayList<CartellaClinicaNoH> ccList = CartellaClinicaDAONoH.getCcs(connectionVam, animale.getId());
		//ArrayList<CartellaClinica> ccList = (ArrayList<CartellaClinica>) persistence.getNamedQuery("GetCCByIdCane2").setInteger("idAnimale", animale.getId()).list();
		
		Iterator<CartellaClinicaNoH> iter = ccList.iterator();
		while(iter.hasNext())
		{
			CartellaClinicaNoH cc = iter.next();
			if(cc.getDestinazioneAnimale()!=null)
			{
				if(getIdTipoAttivitaBdrCompletata(cc)==null && ((cc.getDestinazioneAnimale().getId()==1 && AnimaliUtil.getInserireRitornoProprietario(cc.getAccettazione(), req)) ||cc.getDestinazioneAnimale().getId()==4 ||cc.getDestinazioneAnimale().getId()==3 || cc.getDestinazioneAnimale().getId()==8 || cc.getDestinazioneAnimale().getId()==5))
				{
					return cc;
				}
			}
		}
		
		return null;
	}
	
	
	//Controlla se c'è una riconsegna in atto per quest'animale
	private boolean animaleRientrante(AnimaleNoH animale, BUtenteAll utente, Connection connectionVam) throws SQLException
	{

		PreparedStatement stat = null;
		ResultSet rs = null;

		
		try 
		{
			stat = connectionVam.prepareStatement(" select count(*) aS conta from trasferimento t "+
					" left join cartella_clinica cc on cc.id = t.cartella_clinica "+
					" left join accettazione acc on acc.id = cc.accettazione  "+
					" left join animale an on an.id = acc.animale "+
					" left join cartella_clinica cc_mittente_ricosegna on cc.id = t.cartella_clinica_mittente_riconsegna "+
					" where clinica_origine = ? and "+
					" cartella_clinica_mittente_riconsegna is not null and "+
					" an.id = ? and "+
					" cc_mittente_ricosegna.modified < cc_mittente_ricosegna.entered ");
			stat.setInt(1, utente.getClinica().getId());
			stat.setInt(2, animale.getId());
			rs = stat.executeQuery();

			if (rs.next()) 
			{
				if(rs.getInt("conta")>0)
					return true;
				else
					return false;
			}
			return false;

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		} 
		finally 
		{
			stat.close();
			rs.close();
		}
	}
	
	//Controlla se c'è un trasferimento in ingresso per quest'animale
	private boolean animaleInArrivo(AnimaleNoH animale, ClinicaNoH clinica)
	{
		for(TrasferimentoNoH t : clinica.getTrasferimentiIngresso())
		{
			if( t.getStato().getStato()!=1 && 
				t.getStato().getStato()!=6 && 
				t.getDataAccettazioneDestinatario()==null && 
				t.getCartellaClinica().getAccettazione().getAnimale()==animale)
				return true;
		}
		return false;
	}
	
	
	private ClinicaNoH getClinicaCcChiusaperTrasferimento(AnimaleNoH animale, Connection connectionVam, BUtenteAll utente) throws SQLException
	{
		//Animale in arrivo
		for(AccettazioneNoH acc : animale.getAccettaziones())
		{
			for(CartellaClinicaNoH cc : acc.getCartellaClinicas())
			{
				for(TrasferimentoNoH t : cc.getTrasferimenti())
				{
					if( t.getStato().getStato()!=6 && 
						t.getDataAccettazioneDestinatario()==null && 
						t.getClinicaDestinazione().getId()!=utente.getClinica().getId())
						return t.getClinicaDestinazione();
				}
			}
		}
		
		
		//Animale rientrante
		ArrayList<TrasferimentoNoH> trasferimentiRientranti = ClinicaDAONoH.getTrasferimentiRientranti(animale.getId(), connectionVam);
		
		if(!trasferimentiRientranti.isEmpty())
		{
			for(TrasferimentoNoH t : trasferimentiRientranti)
			{
				if( t.getClinicaOrigine().getId()!=utente.getClinica().getId())
					return t.getClinicaOrigine();
			}
		}
		return null;
		
	}
	
	
	
	
	
	
	//Controlla se esiste un'accettazione più recente per l'animale in questa clinica
	public String controlloDataAccettazioniRecenti(String idAnimale, String idUtente, String dataImmessa, int idClinica,HttpServletRequest req)
	{
		Connection connectionVam = null;
		try
		{
			Context ctxVam = new InitialContext();
			javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
			connectionVam = dsVam.getConnection();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date data = sdf.parse(dataImmessa);
			//Animale animale 									 	 = getAnimale(Integer.parseInt(idAnimale), connection);
			AnimaleNoH animale 									 	 = AnimaleDAONoH.getAnimale(Integer.parseInt(idAnimale), connectionVam);
			AccettazioneNoH accettazione 								 = null;
			String messaggio 										 = "";
			
			if((accettazione = animale.getAccettazionePiuRecenteByClinica(idClinica))!=null && accettazione.getData().after(data))
				messaggio = "Salvataggio impossibile: esiste un'accettazione più recente: " + accettazione.getProgressivoFormattato();
			
			System.out.println("Fine test data accettazioni recenti");
			return messaggio;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
		finally
		{
			if(connectionVam!=null)
			{
				try
				{
					connectionVam.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		}
	}
	
	
	private Integer getIdTipoAttivitaBdrCompletata(CartellaClinicaNoH cc)
	{
		Integer idTipoAttivitaBdr = null;
		Iterator<AttivitaBdrNoH> iter = cc.getAttivitaBdrs().iterator();
		while(iter.hasNext())
		{
			AttivitaBdrNoH att = iter.next();
			if(att.getIdRegistrazioneBdr()!=null && att.getIdRegistrazioneBdr()>0 && att.getOperazioneBdr().getId()!=IdOperazioniBdr.sterilizzazione)
				idTipoAttivitaBdr = att.getOperazioneBdr().getId();
		}
		return idTipoAttivitaBdr;
	}
	
	public void aggiornaConnessioneApertaSessione(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (0);
			numConnessioniDb = numConnessioniDb+1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			req.getSession().setAttribute("timeConnOpen",     new Date());
		}
	}
	
	public void aggiornaConnessioneChiusaSessione(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (1);
			numConnessioniDb = numConnessioniDb-1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			if(numConnessioniDb==0)
				req.getSession().setAttribute("timeConnOpen",     null);
		}
	}
	
	
	
	
	public boolean esisteMc( String mc,HttpServletRequest req) throws SQLException
	{
		Connection connectionBdu = null;
		boolean toReturn = false;
		try
		{
			Context ctxBdu = new InitialContext();
			javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
			connectionBdu = dsBdu.getConnection();
			aggiornaConnessioneApertaSessione(req);
			
			PreparedStatement stat = connectionBdu.prepareStatement("select count(*) from animale where (microchip = '" + mc + "' or tatuaggio = '" + mc + "') and data_cancellazione is null and trashed_date is null ");
			ResultSet rs = stat.executeQuery();
			
			if(rs.next() && rs.getInt(1)>0)
				toReturn = true;
			
			connectionBdu.close();
			aggiornaConnessioneChiusaSessione(req);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return toReturn;
		}
		finally
		{
			if(connectionBdu!=null)
			{
				try
				{
					connectionBdu.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		}
		
		return toReturn;
		
	}
}