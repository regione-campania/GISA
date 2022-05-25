/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.webservicesa_generale.richiesta.suap;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.json.JSONArray;
import org.json.JSONObject;

import com.wrapper.WrapperUnrar;

@Path("/services")
public class RestControllerSAGenerale {

	@Context ServletContext context;
	Logger logger = Logger.getLogger(this.getClass());
	File[] filesToDelete;
	
	
	@POST
	@Path("/send")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response metodoSciaGetRispostaJson( @Context HttpServletRequest request
			,FormDataMultiPart fdmp) 
	{
		Response resp = null;
		Properties props = new Properties();
		InputStream isPerProps = null;
		//String jsonResp = "{'code': 'codeValue', 'description' : 'descriptionValue'}";
		JSONObject objJ = new JSONObject();
		boolean conFirmaDigitale = false;
		OutputStream os = null;
		HashMap<String,String> parametriInputsPerSciaComeSuXml;
		
		
		
		
		
		
		
		logger.info("REST CONTROLLER > ARRIVATA RICHIESTA SERVIZIO ");
		try
		{
			/*PARAMETRI DI CONFIGURAZIONE PER IL WEB SERVICE VERSO IL SISTEMA GISA------------------------------------------------------------------------
			 * 
			 QUESTI INDIRIZZI DOVRANNO ESSERE CONOSCIUTI DALL'UTILIZZATORE, E NON PRESI DALLE PROPRIETA LOCALI !
			*/ 
			
//			URL urlAutenticat = new URL("http://localhost:80/suap/Login.do?command=LoginSuapWithToken");
			URL urlAutenticat = new URL(request.getScheme(),request.getServerName(),request.getServerPort(),"/suap/Login.do?command=LoginSuapWithToken");
			ConnettoreAdAutenticazioneGenerale connettoreAutenticatore= new ConnettoreAdAutenticazioneGenerale(urlAutenticat);
			
//			URL urlConnettoreGisa = new URL("http://localhost:80/gisa_nt/insertIntoGisa");
			URL urlConnettoreAGisa = new URL(request.getScheme(),request.getServerName(),request.getLocalPort(),request.getContextPath()+"/insertIntoGisa");
			ConnettoreAGisaGenerale connettoreAGisa = new ConnettoreAGisaGenerale(urlConnettoreAGisa);
			
			
//			URL urlConnettoreDocumentaleControlloAllegati = new URL("http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CONTROLLO"));
//			URL urlDocumentaleInvioAllegati = new URL("http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CARICATI"));
			URL urlConnettoreDocumentaleControlloAllegati  = new URL("http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CONTROLLO"));
			URL urlDocumentaleInvioAllegati = new URL("http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CARICATI"));
			ConnettoreAlDocumentaleGenerale connettoreAlDocumentale = new ConnettoreAlDocumentaleGenerale(
					urlConnettoreDocumentaleControlloAllegati,urlDocumentaleInvioAllegati, 2000);
			
			String appNameGisa = ApplicationProperties.getProperty("APP_NAME_GISA");
			String pathCodiciServizio = context.getRealPath("/suap/codiciServizioRest.properties");
			/*--------------------------------------------------------------------------------------------------------------------------*/
			
			
			
			
			
			
			
			//props.load(this.getClass().getResourceAsStream("/codiciServizioRest.properties"));
			props.load(new FileInputStream(pathCodiciServizio)); 
			
			Map<String, List<FormDataBodyPart>> inputs = fdmp.getFields();
			String suapIp = inputs.get("suap_ip").get(0).getValue();
			String encryptedToken = inputs.get("encrypted_token").get(0).getValue();
			conFirmaDigitale= inputs.get("con_firma_digitale") != null && Boolean.parseBoolean(inputs.get("con_firma_digitale").get(0).getValue()) ? true : false;
			
			String debugServizioRest = inputs.get("debugServizioRest") != null ? "true" : null;
			
		 
			String[] tId = new String[1];
			boolean autenticato = connettoreAutenticatore.autentica(suapIp, encryptedToken, debugServizioRest, tId);
			String idUser = tId[0]+"";
			
	 
			
			if(!autenticato)
			{
//				risp = new BeanRisposta(Integer.parseInt(props.getProperty("errore_autenticazione")),props.getProperty("descr_errore_autenticazione") );
				
				objJ.put("code", Integer.parseInt(props.getProperty("errore_autenticazione"))+"");
				objJ.put("description", props.getProperty("descr_errore_autenticazione"));
				
//				jsonResp = jsonResp.replace("codeValue",risp.code+"");
//				jsonResp = jsonResp.replace("descriptionValue", risp.description);
//				resp = Response.ok().entity(jsonResp).build();
			}
			else
			{
				logger.info("REST CONTROLLER > *INIZIO* ESTRAZIONE XML DA ARCHIVIO ");
				File tempFile = null;
				ZipInputStream zIs = null;
				ZipEntry zipEntry = null;
				File tempXml = null;
				File tempFoldPerClient = null;
				FileOutputStream fos = null;
				FileOutputStream fosXml = null;
				File tempFoldPerAltriAllegati = null;
				InputStream tempInputStream = null;
				File[] fileContenutiNelRar = null;
				ZipOutputStream zos0 = null;
				FileInputStream fis0 = null;
				FileOutputStream fos99 = null;
				int idTipoOperazione = -1;
				
				boolean isRar = false;
				try
				{
					String nomeFile0 = fdmp.getFields().get("file1").get(0).getContentDisposition().getFileName();
					String estensione = nomeFile0.substring(nomeFile0.lastIndexOf(".")+1);
					isRar = estensione.equalsIgnoreCase("rar");
					
					HashMap<String,String> tagFigliDelTagAllegati = null;
					HashMap<String,File> altriFileAllegati = new HashMap<String,File>(); //la chiave e il nome gruppo
					
					//prendo l'allegato come input
					InputStream istrAllegato = fdmp.getFields().get("file1").get(0).getValueAs(InputStream.class);
					
					
					
					//mi devo prima scrivere in un file temporaneo il file (il nome del file temp e l'ip del client senza i punti)
					//tutti i file temporanei usati per l'elaborazione, vengono creati sotto tempCat4/tempClientIPCLIENT
					
	//				File tempRootFoldCat4 = new File(context.getRealPath("/")+"/tempFoldCat4");
					String tempFoldPerClientPath = context.getRealPath("/tempRestSA")+"/tempFoldCat4/"+"tempFoldPerClient"+suapIp.replace(".", "");
					logger.info("REST CONTROLLER > *CREAZIONE* CARTELLA TEMP IN ? ".replace("?",tempFoldPerClientPath));
					tempFoldPerClient = new File(tempFoldPerClientPath);
					if(!tempFoldPerClient.exists())
					{
						tempFoldPerClient.mkdirs();
					}
					
					//genero il nome del file da salvare temporaneo, usando numero dei file gia presenti
					int idPerFile = tempFoldPerClient.list().length;
					tempFile = new File(tempFoldPerClient+"/"+idPerFile+"."+estensione);
					fos = new FileOutputStream(tempFile);
					byte[] buff = new byte[1024];
					int letti = -1;
					while((letti = istrAllegato.read(buff))>0)
					{
						fos.write(buff,0,letti);
					}
					fos.flush();
					fos.close();
					istrAllegato.close();
					
					//-------------------intercetto il file, e se e di tipo .rar lo apro prima e lo riscrivo come .zip (per mantenere
					//la struttura del codice di estrazione ed analisi)
					
					if(isRar)
					{
						logger.info("REST CONTROLLER > *CONVERSIONE* ARCHIVIO RAR IN ZIP");
						fileContenutiNelRar = WrapperUnrar.estraiFilesDaArchivio(tempFile);
						String pathFileZip = tempFile.getAbsolutePath();
						pathFileZip = pathFileZip.substring(0,pathFileZip.lastIndexOf(".")+1)+"zip";
						
						File fileVersioneZip = new File(pathFileZip);
						
					    zos0 = new ZipOutputStream(new FileOutputStream(fileVersioneZip));
//					    ZipEntry zE = null;
						for(File fEstrattoDaRar : fileContenutiNelRar)
						{
							zos0.putNextEntry(new ZipEntry(fEstrattoDaRar.getName()));
							fis0 = new FileInputStream(fEstrattoDaRar);
							
							while((letti = fis0.read(buff))!= -1)
							{
								zos0.write(buff,0,letti);
							}
							fis0.close();
							zos0.closeEntry();
							
						}
						tempFile = fileVersioneZip;
					}
					
					//e poi riaprirlo
					zIs = new ZipInputStream(new FileInputStream(tempFile));
					zipEntry = null;
	//				ArrayList<ZipEntry> zipEntries = new ArrayList<ZipEntry>();
					boolean almenoUnEntry = false;
					//scorro nel file e cerco la richiesta.xml
					boolean noRichiestaNelloZip = true;
					boolean isValidXml = false;
					boolean lineeObbligatorie = true;
					boolean isCessazioneGlob = false;
					
					while((zipEntry = zIs.getNextEntry())!=null)
					{
						almenoUnEntry = true;
						
						if(zipEntry.isDirectory())
							continue;
						
						//SE LA TROVO...
						if(zipEntry.getName().equalsIgnoreCase("richiesta.xml"))  
						{   //GESTISCO l'XML
							//creo file xml temporaneo che indica la richiesta, utilizzando l'id precedentemente ottenuto
							logger.info("REST CONTROLLER > TROVATO FILE richiesta.xml NELL'ARCHIVIO");
							noRichiestaNelloZip = false;
							String nomeTempXml = "richiesta"+idPerFile+".xml";
							tempXml = new File(tempFoldPerClient, nomeTempXml);
							fosXml = new FileOutputStream(tempXml);
							byte[] buff2 = new byte[1024];
							int l2 = -1;
							while((l2 = zIs.read(buff2)) > 0)
							{
								fosXml.write(buff2, 0, l2);
							}
							fosXml.flush();
							fosXml.close();
							
							
							//TENTO LA VALIDAZIONE DELLO SCHEMA SUL FILE XML CREATO
							URL urlSchemaXsd = new URL(request.getScheme(),request.getServerName(),request.getServerPort(),"/gisa_nt/schema_gisa.xsd" );
							ArrayList<String[]> campiInvalidi = new ArrayList<String[]>();
							HashMap<String,Boolean> nomiGruppiFileRichiesti = new HashMap<String,Boolean>(); //uso hashmap poiche ogni gruppo viene preso una singola volta anche se compare per piu' di un'attivita
							tempInputStream = new FileInputStream(tempXml);
							isValidXml =
									XSDSchemaRestGisaValidatore.validateAgainstXSD(tempInputStream, urlSchemaXsd, tempXml,campiInvalidi);
							if(isValidXml) //se l'xml e valido come schema, in ca
							{
								logger.info("REST CONTROLLER > SCHEMA XML VALIDO");
								
								XmlParserGenerico pars = new XmlParserGenerico(tempXml);
								//NB MODIFICA: PER PRIMA COSA CONTROLLO CHE SE E' UN TIPO DI RICHIESTA CHE NECESSITA DELLE LINEE DI ATTIVITA
								//E DEGLI ALLEGATI
								
								//il II par sono i campi non voluti
								parametriInputsPerSciaComeSuXml = pars.getAllChildsValuesFor("item", new String[]{"#text","allegati","rev_ml","id_protocollo_origine","lista_linee"} );
								
								String tipoOperazione = parametriInputsPerSciaComeSuXml.get("tipo_operazione");
								//estraggo codici attivita (vuoto se non sono stati dati tag <linea> figli di <lista_linee>
								ArrayList<String> codiciAttivita = pars.getAllAttributeContentsFor("attivita", "code");
								
								/*PER :
								-LA VARIAZIONE TITOLARITA 
								-LA CESSAZIONE (SE LA SI VUOLE GLOBALE)
								-MODIFICA STATO DEI LUOGHI
								NON E' OBBLIGATORIO SPECIFICARE LE LINEE (TODO PUO' CAMBIARE)*/
								
								idTipoOperazione = Integer.parseInt(connettoreAGisa.ottieniIdDaDescrizioneUsandoTabellaLookup( "suap_lookup_tipo_richiesta", "code", "description", tipoOperazione, true));
								
								String tipoAttivitaDescr = parametriInputsPerSciaComeSuXml.get("tipo_attivita");
								int idTipoAttivita = Integer.parseInt(connettoreAGisa.ottieniIdDaDescrizioneUsandoTabellaLookup( "opu_lookup_tipologia_attivita", "code", "description", tipoAttivitaDescr, true));
								
								
								//per prima cosa controllo che se si tratta di cessazione, deve esister eil tag <cessazione_globale> che ci dice se e globale o meno
								if(idTipoOperazione == 3)
								{
									if(parametriInputsPerSciaComeSuXml.get("cessazione_globale")==null)
										throw new EccezioneTagCessazioneGlobaleAssente();
									else
									{ //e stato specificato, e allora valuto...
										String val = parametriInputsPerSciaComeSuXml.get("cessazione_globale");
										if(val.equalsIgnoreCase("true")) 
										{ //...se e cessazione globale
											isCessazioneGlob = true;
											//in tal caso controllo che non ci siano linee inserite
											if(codiciAttivita.size() > 0)
											{
												throw new EccezioneLineeAttivitaNonNecessarie();
											}
										}
										else 
										{	//..o meno
											isCessazioneGlob = false;
										}
										//INOLTRE DEVE ESSERCI SEMPRE VALORIZZATA (CHE SIA GLOBALE O MENO) LA DATA FINE ATTIVITA 
										//poiche se e puntuale vale per le linee, altrimenti per lo stabilimento
										if(parametriInputsPerSciaComeSuXml.get("data_fine_attivita") == null || parametriInputsPerSciaComeSuXml.get("data_fine_attivita").trim().length()==0)
										{
												throw new EccezioneDataFineAttivitaCessazioneMancante();
										}
										
									}
								}
								
								
								//il tipo di operazione : nuovo stab/ampliamento/variazione/cessaz ci dice se e obbligatorio specificare linee
								//di attivita
								lineeObbligatorie = (idTipoOperazione != 4 
										&& (idTipoOperazione != 3 ||  !isCessazioneGlob ) 
										&& idTipoOperazione != 6) 
											? true : false; //cioe sono obbligatorie per tutti i casi in cui non sia var titolarita, modifica stato dei luoghi  e cessazione globale
								
								
								
								
								
								
								
								//NEL CASO IN CUI SIA UN TIPO DI OPERAZIONE CHE RICHIEDE LISTA LINEE, MA QUESTE NON VENGONO FORNITE
								if( lineeObbligatorie && (codiciAttivita.size() == 0) )
								{
									throw new EccezioneListaLineeRichiestePerOperazioneScelta();
								}
								 
								
								//NEL CASO IN CUI SI TRATTI DI TIPO ATTIVITA APICOLTURA, E DI UN TIPO
								//DI OPERAZIONE CHE RICHIEDE LINEE DI ATTIVITA,
								//ALLORA CI PUO' ESSERE SOLO UNA LINEA DI ATTIVITA E NON DI PIU'
								if( lineeObbligatorie && idTipoAttivita == 3 )
								{
									if(codiciAttivita.size() != 1)
									{
										//
										throw new EccezioneTroppeLineePerApicoltura();
									}
								}
								
								
								
								//-----EFFETTUO IL CONTROLLO CHE SE SI TRATTA DI variazione di titolarita (come operazione)
								//deve avere per forza il tag piva_variazione (NB numero_reg_variazione e opzionale, mentre se si 
								//tratta di ampliamento o cessazione o sospensione, la piva variazione non serve, e il numero registrazione variazione e opzionale
								//quindi in tutti questi ultimi casi non serve controllo)
								
								if(idTipoOperazione == 4) //SE E' VARIAZIONE DI TITOLARITA
								{
									if(parametriInputsPerSciaComeSuXml.get("partita_iva_variazione") == null) //DEVE AVERE PIVAVARIAZIONE
									{
										throw new EccezioneTagPIvaVariazioneMancante();
									}
									
									if(codiciAttivita.size() > 0) //E NON DEVE AVERE LINEE SPECIFICATE
									{ 
										throw new EccezioneLineeAttivitaNonNecessarie();
									}
								}
								
								
								//estraggo tag figli del tag allegati
								HashMap<String,String> tempTag = pars.getAllChildsValuesFor("allegati", new String[]{ "#text"});
								//mi servono in lower
								tagFigliDelTagAllegati = new HashMap<String,String>();
								for(String nomeGru : tempTag.keySet())
								{
									tagFigliDelTagAllegati.put(nomeGru.toLowerCase(),tempTag.get(nomeGru).toLowerCase());
								}
								
								
								
								//SE E' modifica stato dei luoghi...
								if( idTipoOperazione == 6 )
								{
									if(!tagFigliDelTagAllegati.containsKey("planimetria")) //SE NON E' PRESENTE IL TAG PLANIMETRIA
									{//... devo avere per forza il tag <planimetria> sotto <allegati>
										throw new EccezionePlanimetriaMancantePerModStatoLuoghi();
									}
									if(codiciAttivita.size() > 0) //SE SONO PRESENTI LINEE
									{
										throw new EccezioneLineeAttivitaNonNecessarie();
									}
									//setto l'allegato richiesto (non compariva ancora poiche non e un'informazione estraibile dalle linee)
									//
									nomiGruppiFileRichiesti.put("planimetria", true);
									
								}
								
								
								
								//OCCORRE CONTROLLARE CHE PER CIASCUNO DEI CODICI LINEA ATTIVITA SPECIFICATI, SIA PREVISTO IL TIPO ATTIVITA ASSOCIATO
								//(SE PRESENTI LINEE)
								
								HashMap<String,Boolean> lineeValidePerTipoAttivita= connettoreAGisa.controllaSetLineeAttivitaOnTipoAttivita(idTipoAttivita, codiciAttivita);
								//controllo se esiste almeno una non valida (in tal caso ritorno eccezione e metto quelle non valide nella risposta json)
								JSONArray arrayJLineeNonValide = new JSONArray(); //qui salvo quelle non valide, per mandarle al client
								
								for(Map.Entry<String, Boolean> entry : lineeValidePerTipoAttivita.entrySet())
								{
									if(entry.getValue() == false)
									{
										JSONObject jObj0 = new JSONObject();
										jObj0.put("codice_attivita", entry.getKey());
										arrayJLineeNonValide.put(jObj0);
									}
								}
								if(arrayJLineeNonValide.length() > 0)
								{
									throw new EccezioneLineeNonValidePerTipoAttivita(arrayJLineeNonValide);
								}
								
								
								//creo tutto il set
								//dei documenti necessari. Tali documenti necessari dovranno essere poi presenti a loro volta come tags figli nel tag <allegati>
								//in realta e un'informazione che proviene dalla sola linea attivita (la foglia della terna)
								HashMap<String,Boolean> tempTags2  = connettoreAGisa.getNomiGruppiFileRichiesti(codiciAttivita);
								//mi servono in lowercase
								 nomiGruppiFileRichiesti = new HashMap<String,Boolean> ();
								 for(String nomeGruRic : tempTags2.keySet())
								 {
									 nomiGruppiFileRichiesti.put(nomeGruRic.toLowerCase(), true);
								 }
									 
								//se arrivo qui ho estratto correttamente tutti i codici gruppi che dovranno anche essere presenti sul file
								//come tag figli del tag <allegati>
								 
								
									
								
								//creo due var temp di appoggio
								HashMap<String,String> copiaTagFigliDelTagAllegati = (HashMap<String, String>) tagFigliDelTagAllegati.clone();
								HashMap<String,String> copiaNomiGruppiFileRichiesti = (HashMap<String, String>) nomiGruppiFileRichiesti.clone();
								//costruisco preventivamente il messaggio da allegare alla descrizione con i tag richiesti, come json array dove ogni oggetto e {nomeTag : "nomeGRUPPO"}
								JSONArray jAT = new JSONArray();
								for(String to : copiaNomiGruppiFileRichiesti.keySet())
								{
										JSONObject jObjT = new JSONObject();
										jObjT.put("nomeTag", to);
										jAT.put(jObjT);
								}
								
								
								//quindi ora controllo innanzitutto che il numero di tag figli del tag allegati sia corretto
								
								
								if(tagFigliDelTagAllegati.keySet().size() != nomiGruppiFileRichiesti.keySet().size())
								{
									throw new EccezioneTagFileAllegatiInvalidi(jAT);
								}
								//ora controllo che tutti quelli presenti nel tag <allegati> siano anche tutti quelli necessari per le linee
								//ne' piu' ne' meno
								
								
								for(Map.Entry<String, String> el : tagFigliDelTagAllegati.entrySet())
								{
									copiaTagFigliDelTagAllegati.remove(el.getKey());
									nomiGruppiFileRichiesti.remove(el.getKey());
								}
								//se non sono svuotati entrambi, allora non c'era una perfetta corrispondenza uno a uno
								if(nomiGruppiFileRichiesti.keySet().size() > 0 || copiaTagFigliDelTagAllegati.keySet().size() > 0)
								{
									throw new EccezioneTagFileAllegatiInvalidi(jAT);
								}
								
								
								
								
								
								
							}
							else
							{ 
								
								logger.info("REST CONTROLLER > LA richiesta.xml NON RISPETTA LO SCHEMA XML ! OPERAZIONE ANNULLATA");
								//se il formato xml non e valido, rimando lista
//								String stringaTuttiCampiInvalidi = "Formato XML non valido.<br>";
//								for(String[] s : campiInvalidi)
//								{
//									//rimpiazzo 
//									
//									
//									stringaTuttiCampiInvalidi +="<br>"+"<b>riga:</b> "+s[0]+", <b>col:</b> "+s[1]+", <b>tag:</b> "+s[2]+", <b>val:</b> "+s[3];
//								}
								
								//ALLEGARE CAMPI ERRATI
//								risp = new BeanRisposta(Integer.parseInt(props.getProperty("errore_xml")),props.getProperty("descr_errore_xml") );
								noRichiestaNelloZip = false;
								objJ.put("code", Integer.parseInt(props.getProperty("errore_xml"))+"");
								objJ.put("description", props.getProperty("descr_errore_xml"));
								
								JSONArray arrayCampiInv = new JSONArray();
								
								
								for(String[] s : campiInvalidi)
								{
									JSONObject objCampiInv = new JSONObject();
									objCampiInv.put("tag_name", s[2]);
									objCampiInv.put("tag_value", s[3]);
									objCampiInv.put("col", s[1]);
									objCampiInv.put("riga", s[0]);
									arrayCampiInv.put(objCampiInv);
								}
								
								objJ.put("invalid_tags",arrayCampiInv);
//								jsonResp = jsonResp.replace("codeValue",risp.code+"");
//								jsonResp = jsonResp.replace("descriptionValue", risp.description);
//								resp = Response.ok().entity(jsonResp).build();
								
							}
							
					 
							//cancello il file temporaneo dell'xml della richiesta
							
							
							break;
						}
					  
					}
					
					
					zIs.close();
					
					
					
					if(almenoUnEntry == false) //per qualche motivo il multipartbodypart restituisce stream anche se non viene allegato file
					{
						throw new Exception("");
					}
					if(noRichiestaNelloZip) //entra qui se non ha trovato il file richiesta.xml
					{
//						risp = new BeanRisposta(Integer.parseInt(props.getProperty("errore_file")),props.getProperty("descr_errore_file") );
						objJ.put("code", Integer.parseInt(props.getProperty("errore_file"))+"");
						objJ.put("description", props.getProperty("descr_errore_file"));
						logger.info("REST CONTROLLER > NON E' STATO TROVATO richiesta.xml NELL'ARCHIVIO. OPERAZIONE ANNULLATA");
//						jsonResp = jsonResp.replace("codeValue",risp.code+"");
//						jsonResp = jsonResp.replace("descriptionValue", risp.description);
//						resp = Response.ok().entity(jsonResp).build();
					}
					else if(isValidXml) //entra qui se esisteva almeno una entry, c'era la richiesta.xml e l'xml risulta valido rispetto schema
					{
						
						//se sono arrivato qui allora c'e' corrispondenza perfetta tra quelli che sono i codici file nel tag <allegati>
						//e quelli che servono per la linea
						//quindi uso i nomi contenuti nei tag, per estrarre i file nello zip,
						//e solo se e previsto il check della firma digitale, contatto anche il documentale
						
						//a questo punto controlliamo che gli altri file allegati siano corretti, come numero e nome (ed eventualmente siano firmati digitalmente se e richiesto)
						
						//in tagFigliDelTagAllegati ho i nomi dei file che mi servono
						//creo una cartella temporanea nella cartella del client 
						tempFoldPerAltriAllegati = new File(tempFoldPerClient,"AltriAllegati"+idPerFile);
						if(!tempFoldPerAltriAllegati.exists())
						{
							tempFoldPerAltriAllegati.mkdirs();
						}
						//riapro lo zip
						zIs = new ZipInputStream(new FileInputStream(tempFile));
						int numeroAltriAllegati = 0;
						while((zipEntry = zIs.getNextEntry())!=null)
						{
							numeroAltriAllegati++;
							if(zipEntry.getName().equalsIgnoreCase("RICHIESTA.XML") || zipEntry.isDirectory())
								continue;
							String nomeFile = zipEntry.getName().toUpperCase();
							//cerco a quale nome gruppo e associato quel nome file
							String nomeGruppoAssociato = null;
							for(String nomeGruppo : tagFigliDelTagAllegati.keySet())
							{
								if(tagFigliDelTagAllegati.get(nomeGruppo).equalsIgnoreCase(nomeFile))
								{
									nomeGruppoAssociato = nomeGruppo;
									break;
								}
							}
							//se esiste tra quelli indicati 
							if(nomeGruppoAssociato != null)
							{
								//scrivo questo file nella directory
								File tempFile2 = new File(tempFoldPerAltriAllegati,zipEntry.getName());
								fos = new FileOutputStream(tempFile2);
								byte[] bufft = new byte[1024];
								int n2 = -1;
								while((n2=zIs.read(bufft))>0)
								{
									fos.write(bufft,0,n2);
								}
								fos.close();
								
								altriFileAllegati.put(nomeGruppoAssociato, tempFile2); //me li salvo
								//qui scrivo...
								
								//QUI VA MESSA EVENTUALMENTE CONTROLLO FIRMA DIGITALE
								if(conFirmaDigitale)
								{
//									String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CONTROLLO");
//									int timeoutPerDocumentale = 20000;
//									connettoreAlDocumentale = new ConnettoreAlDocumentalePerControlloFirmaDigitaleSA(urlDocumentale,timeoutPerDocumentale);
									boolean esito = Boolean.parseBoolean(connettoreAlDocumentale.controllaFirmaDigitaleFile(tempFile2)[1]);
									System.out.println("(DOCUMENTALE) CONTROLLO FIRMA DIGITALE > Per il file "+tempFile2.getName()+ " esito: "+esito);
									if(!esito)
									{
										throw new FirmaDigitaleNonValida(tempFile2.getName());
									}
								}
								
								
							}
							else
							{ //e fornito un file nello zip che non e  presnete nei tag degli allegati dell'xml
								throw new EccezioneFileContenutiRispettoAiTag();
							}
						}
						
						if(numeroAltriAllegati -1 != tagFigliDelTagAllegati.keySet().size())
						{//se cioe ci sono troppi o troppi pochi file allegati
							throw new EccezioneFileContenutiRispettoAiTag();
						}
						
						
						
						//ARRIVATO QUI TUTTO E' ANDATO BENE COL CONTROLLO DELLO ZIP
						
						boolean isOpGlobale = isCessazioneGlob; //nel caso in cui si tratti di operazioni che non hanno necessita di questo flag, esso viene ignorato automaticamente della dbi
						
						zIs.close();
						
						logger.info("REST CONTROLLER > L'ARCHIVIO, GLI ALLEGATI ED IL FILE .XML DI RICHIESTA SONO CORRETTI");
						//in tal caso estraggo anche l'id protocollo origine	
						XmlParserGenerico pars = new XmlParserGenerico(tempXml);
						String idProtocolloOrigine = pars.getFirstEntryTagContentFor("id_protocollo_origine");
						if(idProtocolloOrigine != null)
						{
							objJ.put("id_protocollo_origine",idProtocolloOrigine);
						}
						
						objJ.put("code", Integer.parseInt(props.getProperty("nessun_errore"))+"");
						objJ.put("description", props.getProperty("descr_nessun_errore"));
						
						
						
						//inizio l'elaborazione per inserimento richiesta,
						
						
						//estraggo dalla richiesta xml il contenuto di quello che rappresenta le info
						tempInputStream.close();
						tempInputStream = new FileInputStream(tempXml);
						
						ArrayList<String> lineeToInsert = pars.getAllAttributeContentsFor("attivita","code");
						
						
						
						
						HashMap<String,File> allegati = new HashMap<String,File>(); //la chiave e Allegato NOMEGRUPPO (es Allegato A)
						 
						for(String nome : altriFileAllegati.keySet())
						{
							allegati.put("Allegato "+nome, altriFileAllegati.get(nome));
						}
						
//						File[] foldsToDelete = new File[2];
//						foldsToDelete[0] = tempFoldPerClient;
//						foldsToDelete[0] = tempFoldPerAltriAllegati;
						
						
						/*prima di lanciare il thread, preparo la lista dei file che andranno cancellati alla terminazione del thread nel metodo di callback qui*/
						filesToDelete = new File[4];
						filesToDelete[0]= tempXml;
						filesToDelete[1] = tempFile;
						filesToDelete[2] = tempFoldPerClient;
						filesToDelete[3] = tempFoldPerAltriAllegati;
						 
						//il secondo parametro sono quelli non voluti
						parametriInputsPerSciaComeSuXml = pars.getAllChildsValuesFor("item", new String[]{"#text","allegati","rev_ml","id_protocollo_origine","lista_linee"} );
						 
						
						//lancio thread apposito
						
						ThreadElaborazioneAsincronaRichiestaRestSAGenerale threadElabAsinc = new ThreadElaborazioneAsincronaRichiestaRestSAGenerale(connettoreAGisa,this,context,parametriInputsPerSciaComeSuXml,lineeToInsert,connettoreAlDocumentale,allegati,Integer.parseInt(idUser), appNameGisa,isOpGlobale);
						threadElabAsinc.start();
						
						//ARRIVATO QUI RITORNO SUCCESS SENZA ASPETTARE LA TERMINAZIONE DELL'ELABORAZIONE 
						
					}
					
					
				}
				catch(EccezioneTroppeLineePerApicoltura ex)
				{
					objJ.put("code",Integer.parseInt(props.getProperty("errore_troppe_linee_per_apicoltura")));
					objJ.put("description", props.getProperty("descr_errore_troppe_linee_per_apicoltura"));
				}
				catch(EccezioneLineeNonValidePerTipoAttivita ex)
				{
					objJ.put("code",Integer.parseInt(props.getProperty("errore_linee_nonvalide_per_tipo_attivita")));
					objJ.put("description", props.getProperty("descr_errore_linee_nonvalide_per_tipo_attivita"));
					objJ.put("linee_non_valide",ex.lineeNonValide);
					logger.info("REST CONTROLLER > PER IL TIPO_ATTIVITA SCELTO, LE LINEE SPECIFICATE NON SONO CORRETTE. OP ANNULLATA");
				}
				catch(EccezioneTagCessazioneGlobaleAssente ex)
				{
					objJ.put("code", Integer.parseInt(props.getProperty("errore_tag_cessazione_globale"))+"");
					objJ.put("description", props.getProperty("descr_errore_tag_cessazione_globale"));
					logger.info("REST CONTROLLER > PER L'OPERAZIONE DI CESSAZIONE E' OBBLIGATORIO SPECIFICARE IL TAG <CESSAZIONE_GLOBALE>. OP ANNULLATA");
				}
				catch(EccezioneLineeAttivitaNonNecessarie ex)
				{
					objJ.put("code", Integer.parseInt(props.getProperty("errore_linee_non_necessarie"))+"");
					objJ.put("description", props.getProperty("descr_errore_linee_non_necessarie"));
					logger.info("REST CONTROLLER > PER L'OPERAZIONE SPECIFICATA NON VANNO INDICATE LINEE DI ATTIVITA. OP ANNULLATA");
				}
				catch(EccezionePlanimetriaMancantePerModStatoLuoghi ex)
				{
					objJ.put("code", Integer.parseInt(props.getProperty("errore_planimetria_mancante"))+"");
					objJ.put("description", props.getProperty("descr_errore_planimetria_mancante"));
					logger.info("REST CONTROLLER > TAG PLANIMETRIA NECESSARIA PER MODIFICA STATO LUOGHI. OP ANNULLATA");
				}
				catch(EccezioneDataFineAttivitaCessazioneMancante ex)
				{
					objJ.put("code", Integer.parseInt(props.getProperty("errore_data_fine_mancante"))+"");
					objJ.put("description", props.getProperty("descr_errore_data_fine_mancante"));
					logger.info("REST CONTROLLER > DATA_FINE_ATTIVITA NECESSARIA PER CESSAZIONE. OP ANNULLATA");
				}
				catch(EccezioneListaLineeRichiestePerOperazioneScelta ex)
				{
					objJ.put("code", Integer.parseInt(props.getProperty("errore_lista_linee_assente"))+"");
					objJ.put("description", props.getProperty("descr_errore_lista_linee_assente"));
					logger.info("REST CONTROLLER > LISTA_LINEE NECESSARIO PER L'OPERAZIONE SCELTA. OP ANNULLATA");
				}
				catch(EccezioneTagPIvaVariazioneMancante ex)
				{
					objJ.put("code", Integer.parseInt(props.getProperty("errore_piva_variazione_assente"))+"");
					objJ.put("description", props.getProperty("descr_errore_piva_variazione_assente"));
					logger.info("REST CONTROLLER > TAG PARTITA_IVA_VARIAZIONE MANCANTE PER UNA RICHIESTA DI VARIAZIONE. OP ANNULLATA");
				}
				catch(EccezioneTagFileAllegatiInvalidi ex)
				{
					objJ.put("code", Integer.parseInt(props.getProperty("errore_gruppi_file_allegati"))+"");
					objJ.put("description", props.getProperty("descr_errore_gruppi_file_allegati") ); 
					objJ.put("tag_richiesti_allegati", ex.getTagNecessariEccezione());
					logger.info("REST CONTROLLER > UNO O PIU' TAG IN <ALLEGATI> NON SONO VALIDI PER LE LINEE INSERITE. OP ANNULLATA");
				}
				catch(EccezioneFileContenutiRispettoAiTag ex)
				{
					objJ.put("code", Integer.parseInt(props.getProperty("errore_file_allegati_rispetto_ai_tag"))+"");
					objJ.put("description", props.getProperty("descr_errore_file_allegati_rispetto_ai_tag") ); 
					logger.info("REST CONTROLLER > UNO O PIU' ALLEGATI NON SONO VALIDI RISPETO A QUANTO SPECIFICATO NELLA RICHIESTA. OP ANNULLATA");
				}
				catch(FirmaDigitaleNonValida ex)
				{
					objJ.put("code", Integer.parseInt(props.getProperty("errore_firma_digitale_allegato"))+"");
					objJ.put("description", props.getProperty("descr_errore_firma_digitale_allegato") ); 
					objJ.put("file_firma_errata",ex.getNomeFile());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
//					risp = new BeanRisposta(Integer.parseInt(props.getProperty("errore_file")),props.getProperty("descr_errore_file") );
					objJ.put("code", Integer.parseInt(props.getProperty("errore_file"))+"");
					objJ.put("description", props.getProperty("descr_errore_file"));
					logger.info("REST CONTROLLER > ERRORE: ?. OP ANNULLATA".replace("?",props.getProperty("descr_errore_file")));
					
//					jsonResp = jsonResp.replace("codeValue",risp.code+"");
//					jsonResp = jsonResp.replace("descriptionValue", risp.description);
//					resp = Response.ok().entity(jsonResp).build();
				}
				finally
				{
					try{tempInputStream.close();}catch(Exception ex){}
					try{zIs.close();}catch(Exception ex){}
					try{fos.close();}catch(Exception ex){}
					try{fos99.close();}catch(Exception ex){}
					try{fis0.close();}catch(Exception ex){}
					try{zos0.close();}catch(Exception ex){}
					try{fosXml.close();}catch(Exception ex){}
					
					/*questi vanno chiusi alla fine del thread thread*/
					/*
					try{tempXml.delete();} catch(Exception ex){} 
					try{tempFile.delete();} catch(Exception ex){}
					try{tempFoldPerClient.delete();} catch(Exception ex){}
					try{tempFoldPerAltriAllegati.delete();} catch(Exception ex){} 
					try{GestoreConnessioni.freeConnection(db);}catch(Exception ex){} */
				}
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
//			risp = new BeanRisposta(Integer.parseInt(props.getProperty("errore_generico")),props.getProperty("errore") );
			objJ.put("code", Integer.parseInt(props.getProperty("errore_generico"))+"");
			objJ.put("description", props.getProperty("errore"));
			
//			jsonResp = jsonResp.replace("codeValue",risp.code+"");
//			jsonResp = jsonResp.replace("descriptionValue", risp.description);
//			resp = Response.ok().entity(jsonResp).build();
		}
		finally
		{
			try{ os.close(); }catch(Exception x){}
		}
		
		
		resp = Response.ok().entity(objJ.toString()).build();	 
		return resp;
		
	}
	
	
	




	public void callbackTerminazioneThreadElaborazioneRichiesta()
	{
		logger.info("REST CONTROLLER > *INIZIO* PULIZIA FILE TEMPORANEI ");
		boolean cancellato = true;
		for(File f : filesToDelete)
		{
			 
			try 
			{
				cancellato = cancellato &&  f.delete();
			}
			catch(Exception ex){ex.printStackTrace(); cancellato = false;}
		}
		
		if(!cancellato )
		{
			logger.info("REST CONTROLLER > almeno un file temporaneo non e stato cancellato");
		}
		 
		
		 
		
		
	}
	
	
 
	class EccezioneTroppeLineePerApicoltura extends Exception
	{
		
	}

	class EccezioneListaLineeRichiestePerOperazioneScelta extends Exception
	{
		
	}
	
	
	class EccezioneTagCessazioneGlobaleAssente extends Exception
	{
		
	}
	
	class EccezioneTagPIvaVariazioneMancante extends Exception
	{
		
	}
	
	class EccezioneDataFineAttivitaCessazioneMancante extends Exception
	{
		
	}
	
	class EccezioneLineeAttivitaNonNecessarie extends Exception
	{
		
	}
	
	class EccezioneLineeNonValidePerTipoAttivita extends Exception
	{
		JSONArray lineeNonValide;
		public EccezioneLineeNonValidePerTipoAttivita(){}
		public EccezioneLineeNonValidePerTipoAttivita(JSONArray lineeNonValide)
		{
			this.lineeNonValide = lineeNonValide;
		}
		
	}
	
	class EccezioneTagFileAllegatiInvalidi extends Exception
	{
		
		JSONArray tagNecessari;
		public EccezioneTagFileAllegatiInvalidi()
		{
			
		}
		
		public EccezioneTagFileAllegatiInvalidi(JSONArray array)
		{
			tagNecessari = array;
		}
		public JSONArray getTagNecessariEccezione(){return tagNecessari; }
	}
	
	class EccezionePlanimetriaMancantePerModStatoLuoghi extends Exception
	{
		
	}
	
	class EccezioneFileContenutiRispettoAiTag extends Exception
	{
		
	}
	
	class FirmaDigitaleNonValida extends Exception
	{
		String nomeFile;
		public FirmaDigitaleNonValida()
		{
			
		}
		
		public FirmaDigitaleNonValida(String nomeFile)
		{
			this.nomeFile = nomeFile;
		}
		
		public String getNomeFile()
		{
			return this.nomeFile;
		}
	}
	
	
	
}
