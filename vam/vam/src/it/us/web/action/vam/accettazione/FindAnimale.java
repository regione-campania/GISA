/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sun.corba.se.spi.orbutil.fsm.State;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Colonia;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.remoteBean.RegistrazioniFelina;
import it.us.web.bean.sinantropi.Detenzioni;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.TrasferimentoNoH;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.dao.vam.TrasferimentoDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.ModificaBdrException;
import it.us.web.util.properties.Application;
import it.us.web.util.properties.Message;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.DataCaching;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.QueryResult;
import it.us.web.util.vam.RegistrazioniUtil;

public class FindAnimale extends GenericAction implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
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
		long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataStart 		= new Date();
			
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		Date dataEnd  	= new Date();
		long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(conn bdu)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();

		SimpleDateFormat sdf = new SimpleDateFormat();
		
		Cane cane = null;
		Gatto gatto = null;
		
		String identificativo = stringaFromRequest( "identificativo" );
		
		if (identificativo == null || ("").equals(identificativo))
			identificativo = (String) req.getAttribute("identificativo");
		
		req.setAttribute( "errore_apertura_nuova_accettazione", 		req.getAttribute("errore_apertura_nuova_accettazione"));
		
		ServicesStatus status = new ServicesStatus();
		HashMap<String, Object> fetchAnimale = AnimaliUtil.fetchAnimale( identificativo, connection,persistence,  utente, status, connectionBdu,req ); 
		Animale animale = (Animale)fetchAnimale.get("animale");
		
		 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(fetch Animale)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
		
		/*if( status.isAllRight() )
		{*/
			if( animale == null ) //animale non presente in locale nè nelle bdr remote -> va inserito in bdr
			{
				if (!context.getAttribute("dbMode").equals("slave")){
					gotoPage( "/jsp/vam/bdr/inserimentoAnagrafe_sceltaSistema.jsp" );
				}
				else {
					throw new AuthorizationException( Message.getSmart( "azione_non_consentita_in_ambiente_slave" ) );
				}
			}
			else //pagina riepilogo dati cani, con precedenti accettazioni
			{
				req.setAttribute( "animale", animale );
				
				
				//VERSIONE PEZZOTTA JNDI
				Context ctx2 = new InitialContext();
				javax.sql.DataSource ds2 = (javax.sql.DataSource)ctx2.lookup("java:comp/env/jdbc/vamM");
				Connection connection2 = ds2.getConnection();
				GenericAction.aggiornaConnessioneApertaSessione(req);
				Statement st1 = connection2.createStatement();
				ResultSet rs1 = null;

				
				//TROVA TRASFERIMENTI PER LA CLINICA DELL'UTENTE
				ArrayList<TrasferimentoNoH> trasfIngresso = new ArrayList<TrasferimentoNoH>();
				trasfIngresso = TrasferimentoDAO.getTrasferimentiNoH("I", animale.getId(), utente.getClinica().getId(), connection2);
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(trasf ing)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				if( trasfIngresso.size() > 0 )
				{
					req.setAttribute( "tr",  trasfIngresso.get( 0 ) );
					//req.setAttribute( "tr",  trasfIngresso );
				}
				
				connection2.close();
				GenericAction.aggiornaConnessioneChiusaSessione(req);
				//FINE PEZZOTTO JNDI
				
				int specie = animale.getLookupSpecie().getId();
				
				//Se il cane non è morto senza mc bisogna leggere in bdr l'info sullo stato attuale
				if(animale.getDecedutoNonAnagrafe())
				{
					if( animale.getDataMorte()!=null)
						req.setAttribute("dataDecesso",  sdf.format( animale.getDataMorte()));
					req.setAttribute("dataDecessoCertezza",  animale.getDataMorteCertezza());
					Iterator<Accettazione> accettazioni = animale.getAccettaziones().iterator();
					//E'stato inserito solo l'animale, senza l'accettazione
					if(accettazioni.hasNext())
					{
						Accettazione accettazione = accettazioni.next();
						req.setAttribute("proprietarioCognome",  accettazione.getProprietarioCognome());
						req.setAttribute("proprietarioNome",  accettazione.getProprietarioNome());
						req.setAttribute("proprietarioCodiceFiscale",  accettazione.getProprietarioCodiceFiscale());
						req.setAttribute("proprietarioDocumento",  accettazione.getProprietarioDocumento());
						req.setAttribute("proprietarioIndirizzo",  accettazione.getProprietarioIndirizzo());
						req.setAttribute("proprietarioCap",  accettazione.getProprietarioCap());
						req.setAttribute("proprietarioComune",  accettazione.getProprietarioComune());
						req.setAttribute("proprietarioProvincia",  accettazione.getProprietarioProvincia());
						req.setAttribute("proprietarioTelefono",  accettazione.getProprietarioTelefono());
						req.setAttribute("proprietarioTipo",  accettazione.getProprietarioTipo());
					}
				}
				else
				{
					if (specie == CANE) 
					{
						cane = (Cane)fetchAnimale.get("cane");
						if(cane==null)
							cane = CaninaRemoteUtil.findCane(identificativo, utente, status, connectionBdu,req);
						
						memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						 dataEnd  	= new Date();
						 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(findCane)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
						
						
						if (cane != null && cane.getDataDecesso()!=null) {
							req.setAttribute("dataDecesso",  sdf.format(cane.getDataDecesso()));
						}
						
						ProprietarioCane proprietario = CaninaRemoteUtil.findProprietario(identificativo, utente, connectionBdu,req);
						
						memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						 dataEnd  	= new Date();
						 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(findProp)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
						
						
						
						if(proprietario!=null)
						{
							req.setAttribute("proprietarioCognome",  proprietario.getCognome());
							req.setAttribute("proprietarioNome",  proprietario.getNome());
							req.setAttribute("proprietarioCodiceFiscale",  proprietario.getCodiceFiscale());
							req.setAttribute("proprietarioDocumento",  proprietario.getDocumentoIdentita());
							req.setAttribute("proprietarioIndirizzo",  proprietario.getVia());
							req.setAttribute("proprietarioCap",  proprietario.getCap());
							req.setAttribute("proprietarioComune",  proprietario.getCitta());
							req.setAttribute("proprietarioProvincia",  proprietario.getProvincia());
							req.setAttribute("proprietarioTelefono",  proprietario.getNumeroTelefono());
							req.setAttribute("proprietarioTipo",  proprietario.getTipo());
						}
					}
					else if (specie == GATTO) 
					{
						gatto = (Gatto)fetchAnimale.get("gatto");
						if(gatto==null)
							gatto = FelinaRemoteUtil.findGatto(identificativo, utente, status, connectionBdu,req);
						
						memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						 dataEnd  	= new Date();
						 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(findGatto)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
						
						
						if(gatto.getDataDecesso()!=null)
							req.setAttribute("dataDecesso",  sdf.format(gatto.getDataDecesso()));
						
						Colonia colonia = (Colonia)fetchAnimale.get("colonia");
						if(colonia==null)
							colonia = FelinaRemoteUtil.findColonia(identificativo, utente, connectionBdu,req);
						
						memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						 dataEnd  	= new Date();
						 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(findColonia)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
						
						
						req.setAttribute("colonia", colonia);
						
						ProprietarioGatto proprietario = FelinaRemoteUtil.findProprietario(identificativo, utente, connectionBdu,req);
						
						memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						 dataEnd  	= new Date();
						 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(findProp)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
						
						
						if(proprietario!=null)
						{
							req.setAttribute("proprietarioCognome",  proprietario.getCognome());
							req.setAttribute("proprietarioNome",  proprietario.getNome());
							req.setAttribute("proprietarioCodiceFiscale",  proprietario.getCodiceFiscale());
							req.setAttribute("proprietarioDocumento",  proprietario.getDocumentoIdentita());
							req.setAttribute("proprietarioIndirizzo",  proprietario.getVia());
							req.setAttribute("proprietarioCap",  proprietario.getCap());
							req.setAttribute("proprietarioComune",  proprietario.getCitta());
							req.setAttribute("proprietarioProvincia",  proprietario.getProvincia());
							req.setAttribute("proprietarioTelefono",  proprietario.getNumeroTelefono());
							req.setAttribute("proprietarioTipo",  proprietario.getTipo());
						}
					}
					else if (specie == SINANTROPO) 
					{
						Sinantropo sinantropo = SinantropoUtil.getSinantropoByNumero(persistence, identificativo);
						Detenzioni detenzioni = SinantropoUtil.getLastActiveDetentore( persistence, animale.getIdentificativo() );
						if (detenzioni !=null && detenzioni.getLookupDetentori()!=null){
							if( detenzioni.getLookupDetentori().getId() == 1 ) //detentore privato
							{
								req.setAttribute("proprietarioCognome",  detenzioni.getDetentorePrivatoCognome());
								req.setAttribute("proprietarioNome",  detenzioni.getDetentorePrivatoNome());
								req.setAttribute("proprietarioCodiceFiscale",  detenzioni.getDetentorePrivatoCodiceFiscale());
								req.setAttribute("proprietarioDocumento", ((detenzioni.getLookupTipologiaDocumento()!=null)? detenzioni.getLookupTipologiaDocumento().getDescription():"") + ": "+ detenzioni.getDetentorePrivatoNumeroDocumento()  );
								req.setAttribute("proprietarioIndirizzo",  detenzioni.getLuogoDetenzione());
								req.setAttribute("proprietarioCap",   detenzioni.getComuneDetenzione().getCap());
								req.setAttribute("proprietarioComune",  detenzioni.getComuneDetenzione().getDescription());
								req.setAttribute("proprietarioProvincia", calcolaProvinciaSinantropo(detenzioni.getComuneDetenzione()));
								req.setAttribute("proprietarioTelefono",  detenzioni.getDetentorePrivatoTelefono());
								req.setAttribute("proprietarioTipo", "Detentore");
							}
							else //detentore non privato
							{
								if( detenzioni.getLookupDetentori().getLookupComuni() != null )
								{
									req.setAttribute("proprietarioCap",detenzioni.getLookupDetentori().getLookupComuni().getCap() );
									req.setAttribute("proprietarioComune", detenzioni.getLookupDetentori().getLookupComuni().getDescription() );
									req.setAttribute("proprietarioProvincia", calcolaProvinciaSinantropo( detenzioni.getLookupDetentori().getLookupComuni() ) );
								}
								req.setAttribute("proprietarioIndirizzo", detenzioni.getLookupDetentori().getIndirizzo() );
								req.setAttribute("proprietarioNome", detenzioni.getLookupDetentori().getDescription() );
								req.setAttribute("proprietarioTelefono", detenzioni.getLookupDetentori().getTelefono() );
								req.setAttribute("proprietarioTipo", "Detentore");
							}
						}
						
						memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
						 dataEnd  	= new Date();
						 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
						System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(getSina)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
								   "Memoria usata: " + (memFreeStart - memFreeEnd) +
								   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
						memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
						dataStart 		= new Date();
						
						if(sinantropo.getDataDecesso()!=null)
							req.setAttribute("dataDecesso",  sdf.format(sinantropo.getDataDecesso()));
					}
					
					if(animale.getAccettazioneConOpDaCompletare()!=null)
						RegistrazioniUtil.sincronizzaDaBdu(animale.getAccettazioneConOpDaCompletare(), null, persistence, connectionBdu, utente, false,req, connection);
				}
				
				req.setAttribute( "specie", 		  SpecieAnimali.getInstance() );
				
				
				//VECCHIA VERSIONE
				//Recupero il numero di accettazioni esistenti per questo animale, per la clinica dell'utente
				/*ArrayList<Accettazione> accettazioni = (ArrayList<Accettazione>) persistence.createCriteria( Accettazione.class )
				.add( Restrictions.eq( "animale", animale ) )
				.createCriteria( "enteredBy" )
					.add( Restrictions.eq( "clinica", utente.getClinica() ) ).list();*/
				
				
				
				
				
				//VERSIONE PEZZOTTA JNDI
				Context ctx3 = new InitialContext();
				javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
				Connection connection3 = ds3.getConnection();
				GenericAction.aggiornaConnessioneApertaSessione(req);
							
				Accettazione acc = null;
				List<Accettazione> accettazioni = new ArrayList<Accettazione>();
				st1 = connection3.createStatement();
				rs1 = st1.executeQuery("select acc.* from accettazione acc, animale an, utenti_ u " + 
									  "where acc.animale = an.id and an.id = " + animale.getId() + " and acc.trashed_date is null and acc.entered_by = u.id and u.clinica = " + utente.getClinica().getId());
				while(rs1.next())
				{
					acc = new Accettazione();
					acc.setId(rs1.getInt("id"));
					accettazioni.add(acc);
				}
				connection3.close();
				GenericAction.aggiornaConnessioneChiusaSessione(req);
				//FINE PEZZOTTO JNDI
				
				
				if(!accettazioni.isEmpty())
					req.setAttribute("accettazioniSize", accettazioni.size());
				else 
					req.setAttribute("accettazioniSize", 0);

				req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica((Animale)fetchAnimale.get("animale"), (Cane)fetchAnimale.get("cane"), (Gatto)fetchAnimale.get("gatto"), persistence, utente, status, connectionBdu,req));
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(getAnagrafica)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(pre gotopage)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
				
				gotoPage( "/jsp/vam/accettazione/riepilogoAnimale.jsp" );
			
				
				
				memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
				 dataEnd  	= new Date();
				 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
				System.out.println("Modulo in esecuzione: " + "vam.accettazione.FindAnimale.us(post gotoPage)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
						   "Memoria usata: " + (memFreeStart - memFreeEnd) +
						   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
				memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
				dataStart 		= new Date();
			
			}
	}
	
	private static String calcolaProvinciaSinantropo( LookupComuni comune )
	{
		String ret = "";
		
		if( comune.getAv() ) { ret = "AV"; }
		else if( comune.getBn() ) { ret = "BN"; }
		else if( comune.getCe() ) { ret = "CE"; }
		else if( comune.getNa() ) { ret = "NA"; }
		else if( comune.getSa() ) { ret = "SA"; }
		
		return ret;
	}

}
