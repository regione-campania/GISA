/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestoriacquenew.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspcfs.modules.login.beans.UserBean;

public class ExcelParserGestoriAcque {
	
	public static String getCellValueSafe(Cell cella)
	{
		DataFormatter df = new DataFormatter();
		String toRet = null;
		if(cella != null)
		{
			toRet = df.formatCellValue(cella);
			if(toRet != null)
			{
				toRet = toRet.replaceAll("\u00A0","");
				toRet = toRet.trim();
			}
		}
		if(toRet != null)
		{
			String toRetTrimmed = toRet.trim();
			return toRetTrimmed;
		}
		else
			return toRet;	
		
	}
	
	public static Timestamp getDateSafe(Cell cella, String nomeCampo) throws ParseException 
	{
		Date value = null;
		try
		{
			value = cella.getDateCellValue();
		}
		catch(Exception e)
		{
			String valueString = cella.getStringCellValue();
			if(valueString != null)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				return new Timestamp(sdf.parse(valueString).getTime());
			}
			else
				return null;
		}
		if(value != null)
			return new Timestamp(cella.getDateCellValue().getTime());
		return null;
	}
	
	
	public static HashMap<String,GestoreAcque> parseSheetPerImportMassivoPuntiPrelievoDifferentiGestori(org.apache.poi.ss.usermodel.Workbook wb,Connection db,ArrayList<String> alEsitiParsingFile)
	{
		
		HashMap<String,GestoreAcque> gestoriAcque = new HashMap<String,GestoreAcque>() ; //[k : denominazione gestore , v : gestore]
	 
		
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
		
		for(int ir = 1; ir< sheet.getLastRowNum();ir++)
		{
			try
			{
				org.apache.poi.ss.usermodel.Row row = sheet.getRow(ir);
				int u = 0;
				 
				
				
//				String descrAsl = getCellValueSafe(row.getCell(u++));
				/*cambiamento : la descr asl non la prendiamo piu' dal file ma la inferiamo dall'id comune */
				
				String descComune =  getCellValueSafe(row.getCell(u++));
//				if(descComune == null || descComune.trim().length() == 0)
//					throw new EccezioneDati("Comune Mancante");
				
				String descrAsl = null;
				Integer idAsl = null;
				try
				{
					Object[] t = getIdDescAslFromDescComune(db, descComune);
					descrAsl = (String)t[1];
					idAsl = (Integer)t[0];
					if(descrAsl == null || descrAsl.trim().length() == 0 || idAsl == null)
					{
						//throw new Exception();
					}
					
				}
				catch(Exception ex)
				{
					//throw new EccezioneDati("Impossibile ottenere ID ASL/ DESC DA desc Comune("+descComune+")");
				}
				
				
				
				String indirizzoDaNormalizzare = getCellValueSafe(row.getCell(u++));
				String denominazionePuntoPrelievo =  getCellValueSafe(row.getCell(u++));
				String descrizioneTipologia =  getCellValueSafe(row.getCell(u++));
				String denominazioneGestore =  getCellValueSafe(row.getCell(u++));
				
				if(denominazioneGestore == null || denominazionePuntoPrelievo == null ) /*riga vuota */
					throw new Exception();
				String stato = getCellValueSafe(row.getCell(u++));
				String codicePuntoPrelievo =  getCellValueSafe(row.getCell(u++));
				
				/*codice non piu' obbligatorio */
				/*
				if(codicePuntoPrelievo == null || codicePuntoPrelievo.trim().length() == 0)
				{
					throw new EccezioneDati("Codice Mancante");
				}
				*/
				if(descComune == null || descComune.trim().length() == 0)
				{
					throw new EccezioneDati("Comune Punto di Prelievo mancante");
				}
				
				if(gestoriAcque.containsKey(denominazioneGestore) == false)
				{
					GestoreAcque gest = new GestoreAcque(denominazioneGestore,db,0,false); /*ritrovo il gestore, non mi interessano i punti prelievo poiche' li stiamo inserendo noi ora da file e neanche i controlli*/
					gest.puntiPrelievo = new ArrayList<PuntoPrelievo>(); /*li resetto */
					gestoriAcque.put(denominazioneGestore, gest);
				}
				
				/* costruisco il nesting dei bean, m anon avvio ancora nessun inserimento ricorsivo */
				GestoreAcque gest = gestoriAcque.get(denominazioneGestore.trim());
				/*per hp non ci sono punti prelievo duplicati per gestore */
				PuntoPrelievo ppToAdd = new PuntoPrelievo();
				ppToAdd.setDescrizioneAsl(descrAsl!= null ? descrAsl.trim() : null);
				ppToAdd.getIndirizzo().setDescrizioneComune(descComune!= null ? descComune.trim() : null);
				ppToAdd.getIndirizzo().setVia(indirizzoDaNormalizzare); /*poi verra' spacchettato in fase di inserimento */
				ppToAdd.setDenominazione(denominazionePuntoPrelievo.trim());
				ppToAdd.setDescrizioneTipologia(descrizioneTipologia.trim());
				ppToAdd.setStato(stato);
				ppToAdd.setCodice(codicePuntoPrelievo);
				ppToAdd.gestorePadre = gest;
				ppToAdd.setIdGestore(gest.getId());
				
				gest.puntiPrelievo.add(ppToAdd);
			}
			catch(GestoreNotFoundException ex)
			{
				alEsitiParsingFile.add("<font color=\"orange\"><br>RIGA "+ir+" SALTATA: GESTORE NON TROVATO IN ANAGRAFICA</font>");
			}
			catch(EccezioneDati ex)
			{
				alEsitiParsingFile.add("<font color=\"orange\"><br>RIGA "+ir+" SALTATA: "+ex.getMessage()+"</font>");
			}
			catch(Exception ex)
			{
//				ex.printStackTrace(s);;
//				ex.printStackTrace();
				alEsitiParsingFile.add("<font color=\"orange\"><br>RIGA "+ir+" SALTATA</font>");
			}
			
		}
		
		try
		{
			wb.close();
		}
		catch(Exception ex)
		{
			
		}
		
		return gestoriAcque;
	}
	
	
	
	
	
	
	/*Metodo per analizzare il foglio excel per l'import delle anagrafiche massive di tutti i gestori */
	/*Scorre sul file, e prende i gestori e li mette nell'hashmap.
	 * Gli inserimenti sono fatti solo alla fine, poiche' se un gestore compare piu' di una volta, vuol dire che 
	 * esiste piu' di un'anagrafica per esso, quindi tutte le anagrafiche vengono prima raccolte e poi vengono aggiunte tutte le anagrafiche alla fine
	 */
	
	public static ArrayList<GestoreAcque> parseSheetPerImportMassivoAnagDifferentiGestori(org.apache.poi.ss.usermodel.Workbook wb,Connection db,ArrayList<String> alEsitiParsingFile)
	{
		
		HashMap<String,GestoreAcque> gestoriToAdd = new HashMap<String,GestoreAcque>(); /*[k: denominazione gestore, v : gestore] */
		
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
		
		for(int ir = 1; ir<= sheet.getLastRowNum();ir++) /*ogni riga di questo file excel rappresenta un'anagrafica gestore associata ad un gestore (un gestore puo' averne piu' di una) */
		{
			try
			{
				org.apache.poi.ss.usermodel.Row row = sheet.getRow(ir);
				int u = 0;
				
				/*qui sono i dati del gestore a cui e' associata la riga presente che rappresenta l'anagrafica del gestore */
				
				/*denominazione gestore OBBLIGATORIA*/
				String denominazioneGestore = getCellValueSafe(row.getCell(u++));
				
				if(denominazioneGestore == null || denominazioneGestore.trim().equals(""))
					throw new Exception();
				
				if(!gestoriToAdd.containsKey(denominazioneGestore))
				{
					GestoreAcque toAdd = new GestoreAcque();
					toAdd.setNome(denominazioneGestore);
					gestoriToAdd.put(denominazioneGestore, toAdd);
				}
//				else  
//				{
//					continue;
//				}
				
				GestoreAcque toinsert = gestoriToAdd.get(denominazioneGestore);
				
				/*qui iniziano i dati dell'anagrafica del gestore, rappresentati dalla riga */
				
				/*indirizzo */
				String indirizzoDaNormalizzare = getCellValueSafe(row.getCell(u++));
				
				/*comune --> se il comune e' vuoto, lascio comune , id comune e asl vuoti
				 * se invece c'e', potrebbe schiattare perche' non riesce ad associargli asl, e in quel caso
				 * gli setto fuori do fuori regione automaticamente*/
				String descComune =  getCellValueSafe(row.getCell(u++));
				Integer idComune = null;
				Integer idAsl = null; 
				
				if(descComune != null && descComune.trim().length() > 0)
				{
					try
					{
						idComune = ControlloInterno.getCodeFromLookupDesc(db, "comuni1", "id", "nome", descComune, ""); /*puo 'lanciare eccezione qua se non troviamo il comune in comuni1 quindi no id */
						/*se trova id comune allora prova a matchare un'asl campana, se non ci riesce, automaticamente la funzione getIdDescAslFromIdComune ritorna fuori regione */
						Object[] t = getIdDescAslFromIDComune(db,idComune);
						idAsl = (Integer)t[0];
						
					}
					catch(Exception ex)
					{
						/*se non e' riuscito a trovar eil comune in tabella */
						System.out.println("-----------Non e' stato possibile trovare il comune inserito per gestore("+denominazioneGestore+"). Setto comune, asl vuoti"); 
						idAsl = 0;
						idComune = -1;
					}
				}
				else
				{ /*per chiarezza di lettura */
					
					idAsl = 0;
					idComune = -1;
				}
				 
				 
				
				/*provincia*/
				String descProvincia =  getCellValueSafe(row.getCell(u++));
//				
				/*telefono */
				String telefono = getCellValueSafe(row.getCell(u++));
				
//				String nominativoRappLegale = ""; /*non compare piu' nella nuova versione del file al 15/09/2017//getCellValueSafe(row.getCell(u++)); */
				
				/*dom digitale */
				String domDigitale = getCellValueSafe(row.getCell(u++));
				
				
				/*TODO COMUNI COPERTI
				 * E' STATA CHIESTA L'ELIMINAZIONE -> NB: se due diverse righe del file excel, associate allo stesso gestore, contengono comuni diversi,
				 * questi vanno in union stretta (no duplicati) */
				/***********
				 * 
				 * 
				 * *****************************
				 */
				/*
				String allComuniCopertiStr = getCellValueSafe(row.getCell(u++));
				HashMap<String,Integer> descComuniToId = toinsert.getComuniAccettatiPerPP();//new HashMap<String,Integer>();  quella che dovremo mettere nel gestore acque  
				if(allComuniCopertiStr == null || allComuniCopertiStr.trim().length() == 0)
					throw new EccezioneDati("Lista Comuni accettati non valida");
				
				String[] comunis = allComuniCopertiStr.split(",");
				for(String comuneStr : comunis)
				{
					try
					{
						comuneStr = comuneStr.trim();
						int idComuneAccettato = ControlloInterno.getCodeFromLookupDesc(db, "comuni1", "id", "nome", comuneStr, "");
						descComuniToId.put(comuneStr, idComuneAccettato);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(descComuniToId.keySet().size() == 0)
				{
					//non sono riuscito a raccogliere neanche un comune, quindi lancio errore 
					throw new EccezioneDati("Lista Comuni accettati non valida");
				}
				 
				*/
				
				if(denominazioneGestore == null || denominazioneGestore.trim().length() == 0 ) /*riga vuota */
					throw new Exception();
				
				AnagraficaGestore toAppend = new AnagraficaGestore(); /*questa e' l'anagrafica, rappresentata dalla riga del file excel, agganciata a quel gestore */
				toAppend.setIndirizzo(indirizzoDaNormalizzare);
				toAppend.setIdComune(idComune);
				toAppend.setIdAsl(idAsl);
				toAppend.setTelefono(telefono);
//				toAppend.setNominativoRappLegale(nominativoRappLegale); /*non ci sta piu' nel file al 15/09/2017 */
				toAppend.setDomicilioDigitale(domDigitale);
				toAppend.setProvincia(descProvincia);
				toinsert.getAnagraficheDelGestore().add(toAppend);
				
				
				
			}
			catch(EccezioneDati ex)
			{
				alEsitiParsingFile.add("<font color=\"orange\"><br>RIGA "+ir+" SALTATA: "+ex.getMessage()+"</font>");
			}
			catch(Exception ex)
			{
//				ex.printStackTrace(s);;
//				ex.printStackTrace();
				alEsitiParsingFile.add("<font color=\"orange\"><br>RIGA "+ir+" SALTATA</font>");
			}
			
		}
		
		try
		{
			wb.close();
		}
		catch(Exception ex)
		{
			
		}
		
		/*trasformo l'hash map di appoggio in un array list da ritornare */
		
		ArrayList<GestoreAcque> toRet = new ArrayList<GestoreAcque>();
		for(Map.Entry<String,GestoreAcque> gestore : gestoriToAdd.entrySet())
		{
			toRet.add(gestore.getValue());
		}
		return  toRet;
	}
	
	
	
	
	
	/*analizza foglio excel per fare import delle anagrafiche e dei punti di prelievo del solo gestore associato all'utente loggato .Quindi il campo
	 * del gestore sul foglio excel E' IGNORATO
	 Ritorna gestore acque popolato con I SOLI PUNTI PRELIEVO NUOVI PARSED DAL FILE
	 */
	public static ArrayList<PuntoPrelievo> parseSheetPerImportGestoreLoggato(org.apache.poi.ss.usermodel.Workbook wb,Connection db,ArrayList<String> alEsitiParsingFile, GestoreAcque gest)
	{
	 
		/*elimino i punti di prelievo gia' associati, gli metto solo i nuovi presi dal file, e uso le funzionalita' del bean per inserirli.
		 * 
		 */
		
		ArrayList<PuntoPrelievo> toRet = new ArrayList<PuntoPrelievo>();
		
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
		
		int contatoreRiga=2;
		
		//Questo ArrayList contiene i codici del punto di prelievo già inseriti nel file
		ArrayList<String> codiciPuntoPrelievo = new ArrayList<String>();
		for(int ir = 1; ir<= sheet.getLastRowNum();ir++)
		{
			try
			{
				org.apache.poi.ss.usermodel.Row row = sheet.getRow(ir);
				int u = 0;
				
				//Controllo che la riga sia popolata, altrimenti si interrompe l'elaborazione
				if(!rigaValorizzata(row,9))
				{
					break;
				}
				 
//				String descrAsl = getCellValueSafe(row.getCell(u++));
				/*CAMBIATO ORA LA DESCR ASL E' PRESA DAL COMUNE */
				String descComune =  getCellValueSafe(row.getCell(u++));
				String indirizzoDaNormalizzare = getCellValueSafe(row.getCell(u++));
				String denominazionePuntoPrelievo =  getCellValueSafe(row.getCell(u++));
				String descrizioneTipologia =  getCellValueSafe(row.getCell(u++));
				String nomeGestoreDummy = getCellValueSafe(row.getCell(u++)); 
				String stato = getCellValueSafe(row.getCell(u++));
				String codicePuntoPrelievo =  getCellValueSafe(row.getCell(u++));
				String latitudine =  getCellValueSafe(row.getCell(u++));
				String longitudine =  getCellValueSafe(row.getCell(u++));
				
				
				
				Integer idAsl = null;
				String descAsl = null;
				Object[] t = getIdDescAslFromDescComune(db, descComune);
				
				try
				{
					idAsl = (Integer)t[0];
					descAsl = (String)t[1];
					if(idAsl == null || descAsl == null || descAsl.trim().length() == 0)
					{
						throw new Exception("Il comune specificato nel record non e' stato trovato nel sistema. Comune: "+descComune);
					}
				}
				catch(Exception ex)
				{
					throw new EccezioneDati(ex.getMessage());
				}
				
				
				
				
				if(  denominazionePuntoPrelievo == null ) /*riga vuota */
					throw new Exception();
				
				
				if(!nomeGestoreDummy.trim().equalsIgnoreCase(gest.getNome()))
				{
					throw new EccezioneDati("Per il record e' specificato un nome gestore differente da quello dell'utente loggato");
				}
				
				if(codicePuntoPrelievo == null || codicePuntoPrelievo.trim().length() == 0)
				{
					throw new EccezioneDati("Codice Mancante");
				}
				
				ArrayList<PuntoPrelievo> punti = PuntoPrelievo.search(db, gest.getId(), false, null, codicePuntoPrelievo);
				if(!punti.isEmpty())
					throw new EccezioneDati("Codice punto prelievo già esistente per il gestore ");
				
				if(codiciPuntoPrelievo.contains(codicePuntoPrelievo.toUpperCase()))
					throw new EccezioneDati("Codice punto prelievo già presente nel file");
					
				/* costruisco il nesting dei bean, m anon avvio ancora nessun inserimento ricorsivo */
			 
				/*per hp non ci sono punti prelievo duplicati per gestore */
				
				double longitudineD = 0;
				double latitudineD  = 0;
				try 
				{
					longitudineD = Double.parseDouble(longitudine.replace(',', '.'));
					latitudineD  = Double.parseDouble(latitudine.replace(',', '.'));
					
				} 
				catch (Exception e) 
				{
					throw new EccezioneDati("Coordinate in formato non corretto");
				}
				if(latitudineD < 39.988475  || latitudineD > 41.503754)
				{
					throw new EccezioneDati("Valore errato per la colonna Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754");
		   		}
				if(longitudineD < 13.7563172 || longitudineD > 15.8032837)
				{
					throw new EccezioneDati("Valore errato per la colonna Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837");
			   	}
					
				PuntoPrelievo ppToAdd = new PuntoPrelievo();
				ppToAdd.setDescrizioneAsl(descAsl);
				ppToAdd.getIndirizzo().setLongitudine(longitudine);
				ppToAdd.getIndirizzo().setLatitudine(latitudine);
				ppToAdd.getIndirizzo().setDescrizioneComune(descComune);
				ppToAdd.getIndirizzo().setVia(indirizzoDaNormalizzare); /*poi verra' spacchettato in fase di inserimento */
				ppToAdd.setDenominazione(denominazionePuntoPrelievo);
				ppToAdd.setDescrizioneTipologia(descrizioneTipologia);
				ppToAdd.setStato(stato);
				ppToAdd.setCodice(codicePuntoPrelievo);
				ppToAdd.gestorePadre = gest;
				ppToAdd.setIdGestore(gest.getId());
				
				codiciPuntoPrelievo.add(codicePuntoPrelievo.toUpperCase());
				toRet.add(ppToAdd);
			}
			catch(EccezioneDati ex)
			{
				alEsitiParsingFile.add("<font color=\"orange\"><br>RIGA "+contatoreRiga+" SALTATA: "+ex.getMessage()+"</font>");
			}
			catch(Exception ex)
			{
//				ex.printStackTrace(s);;
//				ex.printStackTrace();
				alEsitiParsingFile.add("<font color=\"orange\"><br>RIGA "+contatoreRiga+" SALTATA</font>");
			}
			
			contatoreRiga++;
		}
		
		try
		{
			wb.close();
		}
		catch(Exception ex)
		{
			
		}
		
		return toRet;
	}
	
	
	public static HashMap<String,PuntoPrelievo> parseSheetPerImportControlliDecreto28(Workbook wb, Connection db,
			ArrayList<String> esitiErroriParsingfile, GestoreAcque gest,UserBean user) {
		 
		HashMap<String,PuntoPrelievo> controlliPerCodicePP = new HashMap<String,PuntoPrelievo>(); // [k : codice punto prelievo , v : PuntoPrelievo con  ]
		
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
		
		Iterator<Row> iterator = sheet.iterator();
        
        int rigaIniziale = 0;
        int rigaFinale = 0;
        int contatoreRiga= 0;
        
        ArrayList<String> ternaCodicecodiciPuntoPrelievo = new ArrayList<String>();
        
        while (iterator.hasNext()) 
        {
            Row row = iterator.next();
             
            //Non considero le prime righe
            if(contatoreRiga>3)
            {

    			try
    			{ 
    				int u = 0;
    				 
    				//Controllo che la riga sia popolata, altrimenti si interrompe l'elaborazione
    				if(!rigaValorizzata(row,43))
    				{
    					break;
    				}
    	    				
    				String campione_codiceGisa = getCellValueSafe(row.getCell(u++)); /*usato per fare il match col pp */
    				Timestamp campione_dataPrelievo =  getDateSafe (row.getCell(u++), "Data Prelievo");
    				String campione_oraPrelievo =  getCellValueSafe(row.getCell(u++));
    				String campione_finalitaMisura =  getCellValueSafe(row.getCell(u++));
    				String campione_notaFinalitaMisura =  getCellValueSafe(row.getCell(u++));
    				String campione_motivoPrelievo =  getCellValueSafe(row.getCell(u++));
    				String campione_notaMotivoPrelievo =  getCellValueSafe(row.getCell(u++));
    				
    				String fornitura_denominazioneZona =  getCellValueSafe(row.getCell(u++));
    				String fornitura_denominazioneGestore =  getCellValueSafe(row.getCell(u++));

    				String punto_tipoAcqua =  getCellValueSafe(row.getCell(u++));
    				String punto_note =  getCellValueSafe(row.getCell(u++));

    				String campionamento_numeroPrelievi =  getCellValueSafe(row.getCell(u++));
    				String campionamento_chi =  getCellValueSafe(row.getCell(u++));
    				
    				String DI_alfaTotaleMar =  getCellValueSafe(row.getCell(u++));
    				String DI_alfaTotaleMisura =  getCellValueSafe(row.getCell(u++));
    				String DI_alfaTotaleIncertezza =  getCellValueSafe(row.getCell(u++));
    				String DI_alfaTotaleDataMisura =  getCellValueSafe(row.getCell(u++));
    				String DI_alfaTotaleLaboratorio =  getCellValueSafe(row.getCell(u++));
    				String DI_alfaTotaleMetodoProva =  getCellValueSafe(row.getCell(u++));
    				
    				String DI_betaTotaleMar =  getCellValueSafe(row.getCell(u++));
    				String DI_betaTotaleMisura =  getCellValueSafe(row.getCell(u++));
    				String DI_betaTotaleIncertezza =  getCellValueSafe(row.getCell(u++));
    				String DI_betaTotaleDataMisura =  getCellValueSafe(row.getCell(u++));
    				String DI_betaTotaleLaboratorio =  getCellValueSafe(row.getCell(u++));
    				String DI_betaTotaleMetodoProva =  getCellValueSafe(row.getCell(u++));
    				
    				String DI_betaResiduaMar =  getCellValueSafe(row.getCell(u++));
    				String DI_betaResiduaMisura =  getCellValueSafe(row.getCell(u++));
    				String DI_betaResiduaIncertezza =  getCellValueSafe(row.getCell(u++));
    				String DI_betaResiduaDataMisura =  getCellValueSafe(row.getCell(u++));
    				String DI_betaResiduaLaboratorio =  getCellValueSafe(row.getCell(u++));
    				String DI_betaResiduaMetodoProva =  getCellValueSafe(row.getCell(u++));

    				String Radon_concentrazioneMar =  getCellValueSafe(row.getCell(u++));
    				String Radon_concentrazioneMisura =  getCellValueSafe(row.getCell(u++));
    				String Radon_concentrazioneIncertezza =  getCellValueSafe(row.getCell(u++));
    				String Radon_concentrazioneDataMisura =  getCellValueSafe(row.getCell(u++));
    				String Radon_concentrazioneLaboratorio =  getCellValueSafe(row.getCell(u++));
    				String Radon_concentrazioneMetodoProva =  getCellValueSafe(row.getCell(u++));
    				
    				String Trizio_concentrazioneMar =  getCellValueSafe(row.getCell(u++));
    				String Trizio_concentrazioneMisura =  getCellValueSafe(row.getCell(u++));
    				String Trizio_concentrazioneIncertezza =  getCellValueSafe(row.getCell(u++));
    				String Trizio_concentrazioneDataMisura =  getCellValueSafe(row.getCell(u++));
    				String Trizio_concentrazioneLaboratorio =  getCellValueSafe(row.getCell(u++));
    				String Trizio_concentrazioneMetodoProva =  getCellValueSafe(row.getCell(u++));
    				 
    				controlloObbligatorio(campione_codiceGisa, "Identificativi del campione prelevato - Codice Gisa");
    				controlloObbligatorio(campione_dataPrelievo, "Identificativi del campione prelevato - Data Prelievo");
    				controlloObbligatorio(campione_oraPrelievo, "Identificativi del campione prelevato - Ora Prelievo");
    				controlloObbligatorio(campione_finalitaMisura, "Identificativi del campione prelevato - Finalita' della misura");

    				if (campione_finalitaMisura.equalsIgnoreCase("C"))
        				controlloObbligatorio(campione_notaFinalitaMisura, "Identificativi del campione prelevato - Nota su finalita' della misura");
    				
    				controlloObbligatorio(campione_motivoPrelievo, "Identificativi del campione prelevato - Motivo del prelievo");
    				
    				if (campione_motivoPrelievo.equalsIgnoreCase("D"))
        				controlloObbligatorio(campione_notaMotivoPrelievo, "Identificativi del campione prelevato - Nota su motivo del prelievo");
    				
    				controlloObbligatorio(fornitura_denominazioneZona, "Informazioni sulla zona di fornitura - Denominazione della zona di Fornitura");
    				controlloObbligatorio(fornitura_denominazioneGestore, "Informazioni sulla zona di fornitura - Gestore Zdf");

    				controlloObbligatorio(punto_tipoAcqua, "Informazioni sul punto di prelievo - Tipo di acqua");

    				controlloObbligatorio(DI_alfaTotaleMar, "Controlli Dose Indicativa (DI) - Attivita' alfa totale - Valore MAR");
    				//controlloObbligatorio(DI_alfaTotaleMisura, "Controlli Dose Indicativa (DI) - Attivita' alfa totale - Risultato misura");
    				controlloObbligatorio(DI_alfaTotaleIncertezza, "Controlli Dose Indicativa (DI) - Attivita' alfa totale - Incertezza sull'attivita' alfa totale");
    				controlloObbligatorio(DI_alfaTotaleDataMisura, "Controlli Dose Indicativa (DI) - Attivita' alfa totale - Data della misura");
    				controlloObbligatorio(DI_alfaTotaleLaboratorio, "Controlli Dose Indicativa (DI) - Attivita' alfa totale - Laboratorio che ha effettuato la misura");
    				controlloObbligatorio(DI_alfaTotaleMetodoProva, "Controlli Dose Indicativa (DI) - Attivita' alfa totale - Metodo di prova utilizzato");

    				controlloObbligatorio(DI_betaTotaleMar, "Controlli Dose Indicativa (DI) - Attivita' beta totale - Valore MAR");
    				//controlloObbligatorio(DI_betaTotaleMisura, "Controlli Dose Indicativa (DI) - Attivita' beta totale - Risultato misura");
    				controlloObbligatorio(DI_betaTotaleIncertezza, "Controlli Dose Indicativa (DI) - Attivita' beta totale - Incertezza sull'attivita' beta totale");
    				controlloObbligatorio(DI_betaTotaleDataMisura, "Controlli Dose Indicativa (DI) - Attivita' beta totale - Data della misura");
    				controlloObbligatorio(DI_betaTotaleLaboratorio, "Controlli Dose Indicativa (DI) - Attivita' beta totale - Laboratorio che ha effettuato la misura");
    				controlloObbligatorio(DI_betaTotaleMetodoProva, "Controlli Dose Indicativa (DI) - Attivita' beta totale - Metodo di prova utilizzato");
    				
    				controlloObbligatorio(DI_betaResiduaMar, "Controlli Dose Indicativa (DI) - Attivita' beta residua - Valore MAR");
    				//controlloObbligatorio(DI_betaResiduaMisura, "Controlli Dose Indicativa (DI) - Attivita' beta residua - Risultato misura");
    				controlloObbligatorio(DI_betaResiduaIncertezza, "Controlli Dose Indicativa (DI) - Attivita' beta residua - Incertezza sull'attivita' beta residua");
    				controlloObbligatorio(DI_betaResiduaDataMisura, "Controlli Dose Indicativa (DI) - Attivita' beta residua - Data della misura");
    				controlloObbligatorio(DI_betaResiduaLaboratorio, "Controlli Dose Indicativa (DI) - Attivita' beta residua - Laboratorio che ha effettuato la misura");
    				controlloObbligatorio(DI_betaResiduaMetodoProva, "Controlli Dose Indicativa (DI) - Attivita' beta residua - Metodo di prova utilizzato");
    				
    				controlloObbligatorio(Radon_concentrazioneMar, "Controllo della concentrazione di attivita' di radon - Valore MAR");
    				//controlloObbligatorio(Radon_concentrazioneMisura, "Controllo della concentrazione di attivita' di radon - Risultato misura");
    				controlloObbligatorio(Radon_concentrazioneIncertezza, "Controllo della concentrazione di attivita' di radon - Incertezza sulla concentrazione");
    				controlloObbligatorio(Radon_concentrazioneDataMisura, "Controllo della concentrazione di attivita' di radon - Data della misura");
    				controlloObbligatorio(Radon_concentrazioneLaboratorio, "Controllo della concentrazione di attivita' di radon - Laboratorio che ha effettuato la misura");
    				controlloObbligatorio(Radon_concentrazioneMetodoProva, "Controllo della concentrazione di attivita' di radon - Metodo di prova utilizzato");
    				
    				if(!ControlloInterno.search(db, campione_codiceGisa, campione_dataPrelievo, campione_oraPrelievo, "28").isEmpty())
    					throw new EccezioneDati("Controllo con Codice Gisa punto prelievo, data e ora già esistente");
    				
    				if(ternaCodicecodiciPuntoPrelievo.contains(campione_codiceGisa+campione_dataPrelievo.toString()+campione_oraPrelievo.toUpperCase()))
    					throw new EccezioneDati("Controllo con Codice Gisa punto prelievo, data e ora già presente nel file");
    				
    				/*scorro sulle righe, e associo i controlli ai punti di prelievo */
    				
    				if(!controlliPerCodicePP.containsKey(campione_codiceGisa))
    				{
    					PuntoPrelievo pp = PuntoPrelievo.searchByCodiceGisaPP(db, gest.getId(), campione_codiceGisa,false);
    					/*svuoto i controlli e gli metto solo quelli che sto trovando sul file */
    					pp.setControlliInterni(new ArrayList<ControlloInterno>());
    					controlliPerCodicePP.put(campione_codiceGisa,  pp);
    				}
    				PuntoPrelievo pp = controlliPerCodicePP.get(campione_codiceGisa);
    				List<ControlloInterno> controlli = pp.getControlliInterni(); /*quelli fino ad ora aggiunti, dal file, per il pp in questione */
    				
    				ControlloInterno controllo = new ControlloInterno();
    				controllo.setStatusId(1); /*aperto*/
    		
    				controllo.setPuntoPrelievoPadre(pp);
    				controllo.setSiteId(controllo.getPuntoPrelievoPadre().getIdAsl());
    				controllo.setEnteredBy(user.getUserId());
    				controllo.setModifiedBy(user.getUserId());
    				controllo.setTipologia(3); 
    				
    				controllo.setOra(campione_oraPrelievo);
    				controllo.setDataInizioControllo(campione_dataPrelievo);
    				
    				controllo.setCampione_finalitaMisura(campione_finalitaMisura);
    				controllo.setCampione_notaFinalitaMisura(campione_notaFinalitaMisura);
    				controllo.setCampione_motivoPrelievo(campione_motivoPrelievo);
    				controllo.setCampione_notaMotivoPrelievo(campione_notaMotivoPrelievo);

    				controllo.setFornitura_denominazioneZona(fornitura_denominazioneZona);
    				controllo.setFornitura_denominazioneGestore(fornitura_denominazioneGestore);

    				controllo.setPunto_tipoAcqua(punto_tipoAcqua);
    				controllo.setPunto_note(punto_note);

    				controllo.setCampionamento_numeroPrelievi(campionamento_numeroPrelievi);
    				controllo.setCampionamento_chi(campionamento_chi);

    				controllo.setDI_alfaTotaleMar(DI_alfaTotaleMar);
    				controllo.setDI_alfaTotaleMisura(DI_alfaTotaleMisura);
    				controllo.setDI_alfaTotaleIncertezza(DI_alfaTotaleIncertezza);
    				controllo.setDI_alfaTotaleDataMisura(DI_alfaTotaleDataMisura);
    				controllo.setDI_alfaTotaleLaboratorio(DI_alfaTotaleLaboratorio);
    				controllo.setDI_alfaTotaleMetodoProva(DI_alfaTotaleMetodoProva);

    				controllo.setDI_betaTotaleMar(DI_betaTotaleMar);
    				controllo.setDI_betaTotaleMisura(DI_betaTotaleMisura);
    				controllo.setDI_betaTotaleIncertezza(DI_betaTotaleIncertezza);
    				controllo.setDI_betaTotaleDataMisura(DI_betaTotaleDataMisura);
    				controllo.setDI_betaTotaleLaboratorio(DI_betaTotaleLaboratorio);
    				controllo.setDI_betaTotaleMetodoProva(DI_betaTotaleMetodoProva);

    				controllo.setDI_betaResiduaMar(DI_betaResiduaMar);
    				controllo.setDI_betaResiduaMisura(DI_betaResiduaMisura);
    				controllo.setDI_betaResiduaIncertezza(DI_betaResiduaIncertezza);
    				controllo.setDI_betaResiduaDataMisura(DI_betaResiduaDataMisura);
    				controllo.setDI_betaResiduaLaboratorio(DI_betaResiduaLaboratorio);
    				controllo.setDI_betaResiduaMetodoProva(DI_betaResiduaMetodoProva);

    				controllo.setRadon_concentrazioneMar(Radon_concentrazioneMar);
    				controllo.setRadon_concentrazioneMisura(Radon_concentrazioneMisura);
    				controllo.setRadon_concentrazioneIncertezza(Radon_concentrazioneIncertezza);
    				controllo.setRadon_concentrazioneDataMisura(Radon_concentrazioneDataMisura);
    				controllo.setRadon_concentrazioneLaboratorio(Radon_concentrazioneLaboratorio);
    				controllo.setRadon_concentrazioneMetodoProva(Radon_concentrazioneMetodoProva);

    				controllo.setTrizio_concentrazioneMar(Trizio_concentrazioneMar);
    				controllo.setTrizio_concentrazioneMisura(Trizio_concentrazioneMisura);
    				controllo.setTrizio_concentrazioneIncertezza(Trizio_concentrazioneIncertezza);
    				controllo.setTrizio_concentrazioneDataMisura(Trizio_concentrazioneDataMisura);
    				controllo.setTrizio_concentrazioneLaboratorio(Trizio_concentrazioneLaboratorio);
    				controllo.setTrizio_concentrazioneMetodoProva(Trizio_concentrazioneMetodoProva);
    				
    				int idTipoControllo = ControlloInterno.getCodeFromLookupDesc(db, "lookup_tipo_controllo", "code", "description", "Controllo Interno", "");
    				controllo.setTipoControllo( idTipoControllo ); /*TODO LEVA CABLATO */
    				controllo.setOggettoIspezione(19); /*TODO LEVA CABLATO */
    				controllo.setIdComponenteNucleoIspettivo(-1); /*cablato, aggiorna */
    				
    				//Per decreto 28 il protocollo di default è sempre radioattività
    				controllo.setProtocolloRadioattivita(true); 
    				controllo.setTipoDecreto("28");
    				controlli.add(controllo);
    				ternaCodicecodiciPuntoPrelievo.add(campione_codiceGisa+campione_dataPrelievo.toString()+campione_oraPrelievo.toUpperCase());
    			}
    			catch(EccezioneDati ex)
    			{
    				esitiErroriParsingfile.add("<font color=\"orange\"><br>RIGA "+contatoreRiga+" SALTATA : "+ex.getMessage()+"</font>");
    			}
    			catch(ParseException ex)
    			{
    				esitiErroriParsingfile.add("<font color=\"orange\"><br>RIGA "+contatoreRiga+" SALTATA : CAMPO "+ex.getMessage()+" IN FORMATO NON CORRETTO. IL FORMATO CORRETTO E' dd/MM/yyyy </font>");
    			}
    			catch(Exception ex)
    			{
//    				ex.printStackTrace(s);;
//    				ex.printStackTrace();
    				esitiErroriParsingfile.add("<font color=\"orange\"><br>RIGA "+contatoreRiga+" SALTATA POICHE' CAMPO " + ex.getMessage() + " VUOTO </font>");
    			}
    		
            }
            
            contatoreRiga++;
          
            if (rigaIniziale>0 && rigaFinale > 0)
        	    break;
        }
		
		
		
		
		
		
		try
		{
			wb.close();
		}
		catch(Exception ex)
		{
			
		}
		
		return controlliPerCodicePP;
	}
	
	public static HashMap<String,PuntoPrelievo> parseSheetPerImportControlliDecreto31(Workbook wb, Connection db,
			ArrayList<String> esitiErroriParsingfile, GestoreAcque gest,UserBean user) {
		
		
	 
		 
		 
		HashMap<String,PuntoPrelievo> controlliPerCodicePP = new HashMap<String,PuntoPrelievo>(); // [k : codice punto prelievo , v : PuntoPrelievo con  ]
		
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
		
		Iterator<Row> iterator = sheet.iterator();
        
        int rigaIniziale = 0;
        int rigaFinale = 0;
        int contatoreRiga= 0;
        
        ArrayList<String> ternaCodicecodiciPuntoPrelievo = new ArrayList<String>();
        
        while (iterator.hasNext()) 
        {
            Row row = iterator.next();
             
            //Non considero la prima riga
            if(contatoreRiga>0)
            {

    			try
    			{ 
    				int u = 0;
    				 
    				//Controllo che la riga sia popolata, altrimenti si interrompe l'elaborazione
    				if(!rigaValorizzata(row,12))
    				{
    					break;
    				}
 	    		
    				String codiceGisa = getCellValueSafe(row.getCell(u++)); 
    				codiceGisa = codiceGisa.trim();
    				Timestamp dataInizioControllo =  getDateSafe (row.getCell(u++), "Data Prelievo");
    				String ora =  getCellValueSafe(row.getCell(u++));
    				String protocolloRoutineStr =  getCellValueSafe(row.getCell(u++));
    				String protocolloVerificaStr =  getCellValueSafe(row.getCell(u++));
    				String protocolloReplicaMicroStr =  getCellValueSafe(row.getCell(u++));
    				String protocolloRicercaFitosanitariStr = getCellValueSafe(row.getCell(u++));
    				String cloro =  getCellValueSafe(row.getCell(u++));
    				String temperatura =  getCellValueSafe(row.getCell(u++));
    				String esito = getCellValueSafe(row.getCell(u++));
    				String parametriNonConformi = getCellValueSafe(row.getCell(u++));
    				String note = getCellValueSafe(row.getCell(u++));
    				
    				//Vecchi
    				/*
    				String protocolloReplicaChimicaStr =  getCellValueSafe(row.getCell(u++));
    				String protocolloRadioattivitaStr=  getCellValueSafe(row.getCell(u++));
    				*/
    				
    				 
    				controlloObbligatorio(codiceGisa, "Codice Gisa");
    				controlloObbligatorio(dataInizioControllo, "Data Prelievo");
    				controlloObbligatorio(temperatura, "Temperatura");
    				controlloObbligatorio(cloro, "Cloro");
    				controlloObbligatorio(ora, "Ora");
    				
    				if(!ControlloInterno.search(db, codiceGisa, dataInizioControllo, ora, "31").isEmpty())
    					throw new EccezioneDati("Controllo con Codice Gisa punto prelievo, data e ora già esistente");
    				
    				if(ternaCodicecodiciPuntoPrelievo.contains(codiceGisa+dataInizioControllo.toString()+ora.toUpperCase()))
    					throw new EccezioneDati("Controllo con Codice Gisa punto prelievo, data e ora già presente nel file");
    				
    				if(esito == null || esito.trim().length() == 0 || (!esito.equalsIgnoreCase("CONFORME") && !esito.equalsIgnoreCase("NON CONFORME")) )
    				{
    					throw new EccezioneDati("L'esito del Controllo e' obbligatorio, e i valori possibili sono \"CONFORME\" o \"NON CONFORME\"");
    				}
    				 
    				/*scorro sulle righe, e associo i controlli ai punti di prelievo */
    				
    				if(!controlliPerCodicePP.containsKey(codiceGisa))
    				{
    					PuntoPrelievo pp = PuntoPrelievo.searchByCodiceGisaPP(db, gest.getId(), codiceGisa,false);
    					/*svuoto i controlli e gli metto solo quelli che sto trovando sul file */
    					pp.setControlliInterni(new ArrayList<ControlloInterno>());
    					controlliPerCodicePP.put(codiceGisa,  pp);
    				}
    				PuntoPrelievo pp = controlliPerCodicePP.get(codiceGisa);
    				List<ControlloInterno> controlli = pp.getControlliInterni(); /*quelli fino ad ora aggiunti, dal file, per il pp in questione */
    				
    				
    				ControlloInterno controllo = new ControlloInterno();
    				
    				controllo.setCloro(cloro);
    				controllo.setStatusId(1); /*aperto*/
    				controllo.setOra(ora);
    				controllo.setTemperatura(temperatura);
//    				controllo.setIdUnitaOperativa(0); /*TODO LEVA CABLATO */
    				controllo.setEsito(esito);
    				controllo.setNonConformita(parametriNonConformi);
    				controllo.setNote(note);
    				
//    				controllo.setMotivoIspezione(4375); /*TODO LEVA CABLATO */
    				controllo.setDataInizioControllo(dataInizioControllo);
    				controllo.setPuntoPrelievoPadre(pp);
    				controllo.setSiteId(controllo.getPuntoPrelievoPadre().getIdAsl());
    				controllo.setEnteredBy(user.getUserId());
    				controllo.setModifiedBy(user.getUserId());
    				controllo.setTipologia(3); 
    				
    				int idTipoControllo = ControlloInterno.getCodeFromLookupDesc(db, "lookup_tipo_controllo", "code", "description", "Controllo Interno", "");
    				controllo.setTipoControllo( idTipoControllo ); /*TODO LEVA CABLATO */
    				controllo.setOggettoIspezione(19); /*TODO LEVA CABLATO */
    				controllo.setIdComponenteNucleoIspettivo(-1); /*cablato, aggiorna */
    				
    				int numProtocolli = 0; /*un solo protocollo e non piu' di uno */
    				/*controllo validita' protocolli */
    				numProtocolli = (protocolloRoutineStr != null && protocolloRoutineStr.trim().length() > 0) ? numProtocolli+1 : numProtocolli;
    				numProtocolli = (protocolloVerificaStr != null  && protocolloVerificaStr.trim().length() > 0) ? numProtocolli+1 : numProtocolli;
    				numProtocolli = (protocolloReplicaMicroStr != null  && protocolloReplicaMicroStr.trim().length() > 0) ? numProtocolli+1 : numProtocolli;
    				//numProtocolli = (protocolloReplicaChimicaStr != null  && protocolloReplicaChimicaStr.trim().length() > 0) ? numProtocolli+1 : numProtocolli;
    				//numProtocolli = (protocolloRadioattivitaStr != null  && protocolloRadioattivitaStr.trim().length() > 0) ? numProtocolli+1 : numProtocolli;
    				numProtocolli = (protocolloRicercaFitosanitariStr != null  && protocolloRicercaFitosanitariStr.trim().length() > 0) ? numProtocolli+1 : numProtocolli;
    				
    				if(numProtocolli > 1)
    				{
    					throw new EccezioneDati("Non e' possibile selezionare piu' di un protocollo per controllo");
    				}
    				else if(numProtocolli == 0)
    				{
    					throw new EccezioneDati("Selezionare almeno un protocollo di controllo");
    				}
    				
    				if(protocolloRoutineStr != null && protocolloRoutineStr.trim().length() > 0) { controllo.setProtocolloRoutine(true); }
    				if(protocolloVerificaStr != null  && protocolloVerificaStr.trim().length() > 0) { controllo.setProtocollVerifica(true); }
    				if(protocolloReplicaMicroStr != null  && protocolloReplicaMicroStr.trim().length() > 0) { controllo.setProtocolloReplicaMicro(true); }
    				//if(protocolloReplicaChimicaStr != null  && protocolloReplicaChimicaStr.trim().length() > 0) { controllo.setProtocolloReplicaChim(true); }
    				//if(protocolloRadioattivitaStr != null  && protocolloRadioattivitaStr.trim().length() > 0) { controllo.setProtocolloRadioattivita(true); }
    				if(protocolloRicercaFitosanitariStr != null  && protocolloRicercaFitosanitariStr.trim().length() > 0) { controllo.setProtocolloRicercaFitosanitari(true); }
    				
    				controllo.setTipoDecreto("31");
    				
    				controlli.add(controllo);
    				ternaCodicecodiciPuntoPrelievo.add(codiceGisa+dataInizioControllo.toString()+ora.toUpperCase());
    			}
    			catch(EccezioneDati ex)
    			{
    				esitiErroriParsingfile.add("<font color=\"orange\"><br>RIGA "+contatoreRiga+" SALTATA : "+ex.getMessage()+"</font>");
    			}
    			catch(ParseException ex)
    			{
    				esitiErroriParsingfile.add("<font color=\"orange\"><br>RIGA "+contatoreRiga+" SALTATA : CAMPO "+ex.getMessage()+" IN FORMATO NON CORRETTO. IL FORMATO CORRETTO E' dd/MM/yyyy </font>");
    			}
    			catch(Exception ex)
    			{
//    				ex.printStackTrace(s);;
//    				ex.printStackTrace();
    				esitiErroriParsingfile.add("<font color=\"orange\"><br>RIGA "+contatoreRiga+" SALTATA POICHE' CAMPO " + ex.getMessage() + " VUOTO </font>");
    			}
    			
    		
            }
            
            contatoreRiga++;
          
            if (rigaIniziale>0 && rigaFinale > 0)
        	    break;
        }
		
		
		
		
		
		
		
		
		
		
		
		
		try
		{
			wb.close();
		}
		catch(Exception ex)
		{
			
		}
		
		return controlliPerCodicePP;
	}
	
	
	public static Object[] getIdDescAslFromDescComune(Connection conn, String descComune) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		Integer codeAsl = null;
		String descAsl = null;
		
		try
		{
			pst = conn.prepareStatement("select asl.description, asl.code from lookup_site_id asl join comuni1 com on com.codiceistatasl = asl.code::text "
					+ " where lower(com.nome) = lower(?)");
			pst.setString(1, descComune);
			rs = pst.executeQuery();
			if(rs.next()){
				codeAsl = rs.getInt("code");
				descAsl = rs.getString("description");
			}
			return new Object[]{codeAsl,descAsl};
						
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			
		}
	}
	
	public static Object[] getIdDescAslFromIDComune(Connection conn, Integer idCom) 
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		Object[] toRet = null;
		
		try
		{
			pst = conn.prepareStatement("select asl.description, asl.code from lookup_site_id asl join comuni1 com on com.codiceistatasl = asl.code::text "
					+ " where com.id = ?");
			pst.setInt(1, idCom);
			rs = pst.executeQuery();
			rs.next();
			Integer codeAsl = rs.getInt("code");
			String descAsl = rs.getString("description");
			toRet = new Object[]{codeAsl,descAsl};
		}
		catch(Exception ex)
		{
			 System.out.println("Per il comune passato, non e' stato possibile individuare asl campana. Assegno fuori regione");
			 return new Object[]{16,"fuori regione"};
			
			
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}
		}
		
		return toRet;
	}
	
	
	private static void controlloObbligatorio(Object valore, String nomeCampo) throws Exception
	{
		String valoreString = null;
		boolean isString = false;
		
		if(valore instanceof String )
		{
			valoreString = (String)valore;
			isString = true;
		}
		if(valore==null || (isString && valoreString.equals("")))
		{
			throw new Exception(nomeCampo);
		}
	}
	
	private static Double controlloNumerico(String valoreToConvert,String nomeCampo) throws EccezioneDati
	{
		Double variabile = null;
		if(valoreToConvert!=null && !valoreToConvert.equals(""))
		{
			try
			{
				valoreToConvert = valoreToConvert.replaceAll(",", ".");
				variabile =  Double.parseDouble(valoreToConvert);
			}
			catch(NumberFormatException ex)
			{
				throw new EccezioneDati("Il valore del campo " + nomeCampo + " deve essere numerico.");
			}
			return variabile;
		}
		return null;
	}
	
	private static boolean rigaValorizzata(org.apache.poi.ss.usermodel.Row row, int numMaxColonne)
	{
		int i=1;
		for(i=1;i<=numMaxColonne;i++)
		{
			Cell cella = row.getCell(i);
			String valore = null;
			if(cella!=null)
				valore = getCellValueSafe(cella);
			if(cella!=null && valore!=null && !valore.equals(""))
				return true;
		}
		return false;
	}
}
