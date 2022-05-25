/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.us.web.bean.BUtente;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.CodaServiziBdr;
import it.us.web.bean.vam.CodaServiziBdrTentativi;
import it.us.web.constants.Specie;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.ModificaBdrException;

public class GestioneCodaServiziBdr {

	public static void main(String[] args) 
	{

		Persistence persistence = PersistenceFactory.getPersistence();
		ArrayList<CodaServiziBdr> servizi;
		try
		{
			final Logger logger = LoggerFactory.getLogger(GestioneCodaServiziBdr.class);
			
			logger.info("Inizio esecuzione registrazioni in Bdr rimaste pendenti per errori riscontrati");
			logger.info("Ricerca registrazioni in Bdr rimaste pendenti in corso...");
			servizi = (ArrayList<CodaServiziBdr>)persistence.findAll(CodaServiziBdr.class);
			
			logger.info("Sono state trovate " + servizi.size() + " registrazioni pendenti da riprendere");
			int registrazioniRiuscite = 0;
			int contatore = 1;
			
			for(CodaServiziBdr s:servizi)
			{
				boolean esito 			= false;
				int idRegistrazione 	= 0;
				int errorCode 			= 0;
				String errorDescription = null;
				String url 				= null;
				logger.info("<---------- ESECUZIONE REGISTRAZIONE NUMERO " + contatore + " ---------->");
				logger.info("Dati della registrazione:");
				logger.info("1. Richiesta il " + s.getEntered() + " dall'utente " + s.getEnteredBy().getUsername()+ ";");
				logger.info("2. Registrazione da eseguire: " + s.getOperazione() + " " + s.getAnimale().getLookupSpecie().getDescription() + ";");
				
				Animale animale = s.getAnimale();
				BUtente utente = s.getUtente();
				if(animale.getLookupSpecie().getId()==Specie.CANE)
				{
					try{
					if(s.getOperazione().equals("smarrimento"))
					{
						CaninaRemoteUtil.eseguiSmarrimento(animale, s.getDataSmarrimento(), s.getLuogoSmarrimento(), 
														   s.getNoteSmarrimento(),s.getSanzione(), s.getImportoSanzione(), utente, null);
					}
					else if(s.getOperazione().equals("ritrovamento"))
					{
						CaninaRemoteUtil.eseguiRitrovamento(animale, s.getDataRitrovamento(), s.getLuogoRitrovamento(), 
															s.getNoteRitrovamento(),url, utente, null);
					}
					else if(s.getOperazione().equals("ritrovamentoSmarrNonDenunciato"))
					{
						CaninaRemoteUtil.eseguiRitrovamento(animale, s.getDataRitrovamento(), s.getLuogoRitrovamento(), 
															s.getNoteRitrovamento(),url, utente, null);
					}
					else if(s.getOperazione().equals("decesso"))
					{
						CaninaRemoteUtil.eseguiDecesso(animale, s.getDecessoCode(), s.getDataMorte(), 
													   s.getDataDecessoPresunta(),url, url, url, utente, null);
					}
					else if(s.getOperazione().equals("passaporto"))
					{
						CaninaRemoteUtil.eseguiRilascioPassaporto(animale, s.getDataPassaporto(), 
														          s.getNumeroPassaporto(), s.getNotePassaporto(),utente, null);
					}
					else if(s.getOperazione().equals("modificaAnagraficaAnimale"))
					{
						CaninaRemoteUtil.eseguiModificaAnagrafica(animale, s.getRazza().getId(),s.getMantello().getId(), s.getSesso(), s.getTaglia(),
								utente, null);
					}
					esito=true;
					}
					catch(ModificaBdrException  e)
					{
						e.printStackTrace();
					}
					
					
					
					
				}
				else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				{
					try{
					if(s.getOperazione().equals("smarrimento"))
					{
						FelinaRemoteUtil.eseguiSmarrimento(animale, s.getDataSmarrimento(), s.getLuogoSmarrimento(), 
														   s.getNoteSmarrimento(), esito, url, utente, null);
					}
					else if(s.getOperazione().equals("ritrovamento"))
					{
						FelinaRemoteUtil.eseguiRitrovamento(animale, s.getDataRitrovamento(), s.getLuogoRitrovamento(), 
								s.getNoteRitrovamento(),url, utente, null);
					}
					else if(s.getOperazione().equals("ritrovamentoSmarrNonDenunciato"))
					{
						FelinaRemoteUtil.eseguiRitrovamento(animale, s.getDataRitrovamento(), s.getLuogoRitrovamento(), 
								s.getNoteRitrovamento(),url, utente, null);
					}
					else if(s.getOperazione().equals("decesso"))
					{
						FelinaRemoteUtil.eseguiDecesso(animale, s.getDecessoCode(), s.getDataMorte(), 
								s.getDataDecessoPresunta(),url, url, url, utente, null);
					}
					else if(s.getOperazione().equals("passaporto"))
					{
						FelinaRemoteUtil.eseguiRilascioPassaporto(animale, s.getDataPassaporto(), s.getNumeroPassaporto(), 
								s.getNotePassaporto(),utente, null);
					}
					else if(s.getOperazione().equals("modificaAnagraficaAnimale"))
					{
						FelinaRemoteUtil.eseguiModificaAnagrafica(animale, s.getRazza().getId(),s.getMantello().getId(), s.getSesso(), s.getTaglia(),
								utente, null);
					}
					esito = true;
					}
					catch(ModificaBdrException e)
					{
						e.printStackTrace();
					}
					
				}
					
				if(esito)
				{
					logger.info("L'operazione " + s.getOperazione() + "è stata registrata correttamente in BDR");
					AttivitaBdr attivita = s.getAttivitaBdr();
					if(attivita!=null)
					{
						persistence.update(attivita);
						attivita.setIdRegistrazioneBdr(idRegistrazione);
					}
					s.setTrashedDate(new Date());
					persistence.update(s);
					
					CodaServiziBdrTentativi tentativo = new CodaServiziBdrTentativi();
					tentativo.setEntered(new Date());
					tentativo.setErrorDescription(null);
					tentativo.setModified(new Date());
					tentativo.setServizioInCoda(s);
					tentativo.setTrashedDate(null);
					tentativo.setUrl(url);
					persistence.insert(tentativo);
					
					registrazioniRiuscite++;
				}
				else
				{
					CodaServiziBdrTentativi tentativo = new CodaServiziBdrTentativi();
					tentativo.setEntered(new Date());
					tentativo.setErrorCode(errorCode);
					tentativo.setErrorDescription(errorDescription);
					tentativo.setModified(new Date());
					tentativo.setServizioInCoda(s);
					tentativo.setTrashedDate(null);
					tentativo.setUrl(url);
					persistence.insert(tentativo);
				}
				contatore++;
			}
			persistence.commit();
			
			PersistenceFactory.closePersistence( persistence, true );
			
			logger.info("Sono state effettuate correttamente " + registrazioniRiuscite + " di " + servizi.size() + " registrazioni pendenti.");
		}
		catch(InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
