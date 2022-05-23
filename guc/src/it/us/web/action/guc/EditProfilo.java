/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import it.us.web.action.GenericAction;
import it.us.web.action.Home;
import it.us.web.action.endpointconnector.ConfigAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Canile;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.GestoreAcque;
import it.us.web.bean.guc.Importatori;
import it.us.web.bean.guc.LogReloadUtente;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Struttura;
import it.us.web.bean.guc.Utente;
import it.us.web.constants.ExtendedOptions;
import it.us.web.dao.AslDAO;
import it.us.web.dao.GestoreAcqueDAO;
import it.us.web.dao.guc.LogUtenteDAO;
import it.us.web.dao.guc.RuoloDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.GUCOperationType;
import it.us.web.util.guc.PasswordHash;
import it.us.web.util.guc.UrlUtil;
import it.us.web.util.guc.Utility;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.naming.NamingException;
import javax.xml.ws.Endpoint;
import javax.xml.xpath.XPathConstants;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class EditProfilo extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( EditProfilo.class );

	private String listaModificheStrutt = "";

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}





	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{

		HashMap<String, String> query=new HashMap<String, String>();
		Utente u = null;
		Utente utentePreModifica = null;
		boolean okEndpoint;
		boolean esisteUtente;
		String erroreEndpoint = "";

		try{

			int id = interoFromRequest("id");

			List<Utente> utentiL1  =  UtenteGucDAO.listaUtentibyId(db, id);
			if( utentiL1.size() > 0 ){
				utentePreModifica =  utentiL1.get(0);
				System.out.println("Trovato utente preModifica");
				System.out.println("Trovato utente preModifica con id " + id + " con cliniche: " + utentePreModifica.getClinicheVam());
			}


			List<Utente> utentiL = UtenteGucDAO.listaUtentiAnagraficaById(db, id);//persistence.createCriteria(Utente.class).add(Restrictions.eq("id", id)).list();
			boolean modificabile = true ;
			if( utentiL.size() > 0 ){
				u = utentiL.get(0);
				BeanUtils.populate(u, req.getParameterMap());
				
				u.setLuogoVam(stringaFromRequest("luogoVam"));
				u.setNr_iscrione_albo_vet_privato_vam(stringaFromRequest("nr_iscrione_albo_vet_privato_vam"));
				u.setId_provincia_iscrizione_albo_vet_privato_vam(interoFromRequest("id_provincia_iscrizione_albo_vet_privato_vam"));


				Date d = new Date(System.currentTimeMillis());
				//d.setDate(d.getDate()-1);
				d.setDate(d.getDate());

				if (u.getDataScadenza().before(d))
				{

					List<String> comuniList = AslDAO.getComuni(db);
					req.setAttribute("comuniList", comuniList);
					
					List<Asl> aslList = AslDAO.getAsl(db);
					req.setAttribute("aslList", aslList);
					
					

					/**
					 * LISTA PROVINCE
					 */
					HashMap<String, Integer> HashProvince = costruisciListaProvince();
					req.setAttribute("HashProvince", HashProvince);

					u.setError("LA DATA DI INIZIO VALIDITA DEL NUOVO PROFILO DEVE ESSERE MAGGIORE DELLA DATA ATTUALE");

					req.setAttribute("UserRecord", u);


					Boolean flag =false ;
					try
					{
						flag = costruisciListaRuoli();
					}
					catch(Exception e)
					{
						logger.info("Errore Costruzione Ruoli ");
					}


					costruisciListaGestori();
					costruisciListaComuni();
					costruisciListaCliniche();
					costruisciListaCaniliBDU();
					costruisciListaImportatori();

					modificabile = false ;
				}

				if ( modificabile==true)
				{
					BeanUtils.populate(u, req.getParameterMap());

					//				u.setDataScadenza(req.getParameter("dataFineValidita"));

					//Controllo univocit� dell'username su guc_utenti
					boolean unique = false;


					if (unique==false){



						TreeMap<String,Ruolo> ruoli = u.getHashRuoli();
						HashMap<String, HashMap<String,String>> map = new HashMap<String, HashMap<String,String>>(); 
						ExtendedOptions e = new ExtendedOptions();
						Ruolo r = null;
						int ruoloI;
						String ruoloS;
						for(GUCEndpoint endpoint : GUCEndpoint.values()){
							r = ruoli.get(endpoint.toString());
							r.setEndpoint(endpoint.toString());
							ruoloI = req.getParameter("roleId" + endpoint) != null && 
									!req.getParameter("roleId" + endpoint).trim().equals("") ? 
											interoFromRequest("roleId" + endpoint) : -1;
											r.setRuoloInteger( ruoloI );
											ruoloS = req.getParameter("roleDescription" + endpoint) != null ? 
													stringaFromRequest("roleDescription" + endpoint) : "";
													r.setRuoloString( ruoloS );
													r.setUtente(u);
													if (ruoloI>0)  //AGGIORNA SET di RUOLI
														u.getRuoli().add(r);

													HashMap<String, String> extOption=new HashMap<String, String>();
													ArrayList<String> opt = e.getListOptions(endpoint.toString());
													if (opt!=null && opt.size()>0){
														for (int i=0;i<opt.size();i++){
															if (req.getParameter("hidden_"+endpoint.toString()+"_"+opt.get(i))!=null){
																extOption.put(endpoint.toString()+"_"+opt.get(i),(String)req.getParameter("hidden_"+endpoint.toString()+"_"+opt.get(i)) );
															}
														}
													}	
													map.put(endpoint.toString(), extOption);	
						}

						//Set asl e ruoli
						u.setAsl( AslDAO.getAslbyId(db, interoFromRequest("idAsl")));
						u.setExtOption(map);

						/*ASSOCIAZIONE DELL CLINICHE AL RUOLO VAM*/
						costruisciListaCliniche();

						if(req.getAttribute("clinicheUtenteHashVam")!=null){
							TreeMap<Integer, ArrayList<Clinica>> clinicheUtenteHash = (TreeMap<Integer, ArrayList<Clinica>>)req.getAttribute("clinicheUtenteHashVam");
							String[] idCliniche = req.getParameterValues("clinicaId");


							ArrayList<Clinica> clinicheAsl = null;
							if(u.getAsl().getId() > 0){
								clinicheAsl = clinicheUtenteHash.get(u.getAsl().getId());
							}
							else{
								clinicheAsl = new ArrayList<Clinica>();
								for(int asl : clinicheUtenteHash.keySet()){
									clinicheAsl.addAll(clinicheUtenteHash.get(asl));
								}
							}

							boolean disattivaTutteCliniche = false;
							if(u.getRuoli()==null || u.getRuoli().isEmpty())
								disattivaTutteCliniche = true;
							
							boolean trovato = false;
							if(!disattivaTutteCliniche)
							{
								Iterator<Ruolo> ruoliIterator = u.getRuoli().iterator();
								Ruolo rTemp = null;
								while(ruoliIterator.hasNext())
								{
									rTemp = ruoliIterator.next();
									if(rTemp.getEndpoint().equalsIgnoreCase("vam"))
									{
										trovato = true;
										if(rTemp.getId()<0)
											disattivaTutteCliniche = true;
									}
								}
								if(!trovato)
									disattivaTutteCliniche = true;
							}
							
							u.setClinicheVam(new ArrayList<Clinica>());
							if(!disattivaTutteCliniche)
							{
								if (idCliniche!=null && clinicheAsl!=null){
									for(int i = 0 ; i < idCliniche.length; i++){
										int idClinica = Integer.parseInt(idCliniche[i]);
										for(Clinica cc : clinicheAsl)
										{
											if (cc.getIdClinica()==idClinica)
											{
												u.getClinicheVam().add(cc);
											}
										}	
									}
								}
							}
							
						}

						/*ASSOCIAZIONE DEI CANILI AL RUOLO BDU*/
						costruisciListaCaniliBDU();
						if(req.getAttribute("caniliUtenteHashbdu")!=null){
							TreeMap<Integer, ArrayList<Canile>> caniliUtenteHash = (TreeMap<Integer, ArrayList<Canile>>)req.getAttribute("caniliUtenteHashbdu");
							String[] idCanili = req.getParameterValues("canilebduId");

							ArrayList<Canile> caniliAsl = null;
							if(caniliUtenteHash != null){
								if(u.getAsl().getId() > 0){
									caniliAsl = caniliUtenteHash.get(u.getAsl().getId());
								}
								else{
									caniliAsl = new ArrayList<Canile>();
									for(int asl : caniliUtenteHash.keySet()){
										caniliAsl.addAll(caniliUtenteHash.get(asl));
									}
								}
							}

							u.setCaniliBdu(new ArrayList<Canile>());
							if (idCanili!=null && caniliAsl!=null){
								for(int i = 0 ; i < idCanili.length; i++){
									int idCanile = Integer.parseInt(idCanili[i]);
									for(Canile c : caniliAsl){
										if (c.getIdCanile()==idCanile){
											u.getCaniliBdu().add(c);
										}
									}
								}
							}
						}



						/*ASSOCIAZIONE IMPORTATORI AL RUOLO IMPORTATORE*/
						costruisciListaImportatori();
						if(req.getAttribute("ImportatoriUtenteHashImportatori")!=null){
							TreeMap<Integer, ArrayList<Importatori>> importatoriUtenteHash = (TreeMap<Integer, ArrayList<Importatori>>)req.getAttribute("ImportatoriUtenteHashImportatori");
							String[] idImportatori = req.getParameterValues("id_importatore");

							ArrayList<Importatori> importatoriAsl = null;
							if(importatoriUtenteHash != null){
								if(u.getAsl().getId() > 0){
									importatoriAsl = importatoriUtenteHash.get(u.getAsl().getId());
								}
								else{
									importatoriAsl = new ArrayList<Importatori>();
									for(int asl : importatoriUtenteHash.keySet()){
										importatoriAsl.addAll(importatoriUtenteHash.get(asl));
									}
								}
							}

							u.setImportatori(new ArrayList<Importatori>());
							if (idImportatori!=null && importatoriAsl!=null){
								for(int i = 0 ; i < idImportatori.length; i++){
									int idImport = Integer.parseInt(idImportatori[i]);
									for(Importatori imp : importatoriAsl){
										if (imp.getIdImportatore()==idImport){
											u.getImportatori().add(imp);
										}
									}
								}
							}
						}

						String cessazione = req.getParameter("cessazione");
						u.setCessato(cessazione!=null);

						BUtente   BBButente        = (BUtente) req.getSession().getAttribute( "utente" );
						
						if (u.confrontaUtente(utentePreModifica, EndPoint.GISA) ||
								u.confrontaUtente(utentePreModifica, EndPoint.GISA_EXT) ||
								u.confrontaUtente(utentePreModifica, EndPoint.BDU) ||
								u.confrontaUtente(utentePreModifica, EndPoint.VAM) ||
								u.confrontaUtente(utentePreModifica, EndPoint.DIGEMON) ||
								u.isCessato()
								)
							UtenteGucDAO.diasble(db, u);
					
											
						//LogUtenteDAO.loggaUtente(db, u, "Settaggio Data Fine Validita Su Profilo Old",BBButente);

						EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
						EndPointConnector epcInserimentoGUC = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.INSERTUTENTE, EndPoint.GUC);

						/*INSERIMENTO IN GUC*/
						u.setModified(new Date());
						u.setModifiedBy(utente.getId());

						


						//Modifica utente nei vari endpoint

						EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.CHECKESISTENZAUTENTE); 

						EndPointConnector epcModificaProfiloUtente = null;
						EndPointConnector epcAccreditoSuap = null;
						EndPointConnector epcInsertUtente = null;



						Connection conn = null;
						String urlReloadUtenti = "";


						TreeMap<String,Ruolo> t1 = u.getHashRuoli();
						TreeMap<String,Ruolo> t2 = utentePreModifica.getHashRuoli();

						for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
							EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
							if (epc.getEndPoint().getId()!=EndPoint.GUC) {
								okEndpoint = true;
								urlReloadUtenti = "";
								try{

									String QQQuery = epc.getSql();
									System.out.println("QQQ EDITPROFILO "+QQQuery);
									String dataSource = epc.getEndPoint().getDataSourceSlave();
									conn = DbUtil.ottieniConnessioneJDBC(dataSource);


									esisteUtente = this.gestioneEsistenzaUtente(epc, u, conn);



									TreeMap treeMap_old = new TreeMap();
									TreeMap treeMap_new = new TreeMap();
									treeMap_old.putAll(utentePreModifica.getExtOption());
									treeMap_new.putAll(u.getExtOption());
									HashMap<String,String> extEndpoint_old = new HashMap<String,String>();
									HashMap<String,String> extEndpoint_new = new HashMap<String,String>();
									extEndpoint_old = (HashMap<String,String>)treeMap_old.get(epc.getEndPoint().getNome());
									extEndpoint_new = (HashMap<String,String>)treeMap_new.get(epc.getEndPoint().getNome());



									ruoloI = req.getParameter("roleId" + epc.getEndPoint().getNome()) != null && 
											!req.getParameter("roleId" + epc.getEndPoint().getNome()).trim().equals("") ? 
													interoFromRequest("roleId" + epc.getEndPoint().getNome()) : -1;
													u.setRuoloId(ruoloI);
													u.setEnabled(u.getRuoloId() > 0);

													//									Element operationModifyUtente = (Element)xpath.evaluate("//endpoint[@name='" + nomeEndpoint + "']/operation[@name='ModifyProfiloUtente']",doc, XPathConstants.NODE);
													//Element operationModifyUtente = (Element)xpath.evaluate("//endpoint[@name='" + nomeEndpoint + "']/operation[@name='ModifyProfiloUtente']",doc, XPathConstants.NODE);
													epcModificaProfiloUtente = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.MODIFYPROFILOUTENTE, epc.getEndPoint().getId()); 


													if	(esisteUtente){								
														//  okEndpoint = this.gestioneAggiornamentoUtente(operationModifyUtente, operationType, u, conn);
														okEndpoint = cambio_profilo(u,utentePreModifica, epcModificaProfiloUtente, conn);

														if(epc.getEndPoint().getId()==EndPoint.GISA_EXT)
														{
															epcAccreditoSuap = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.ACCREDITASUAP, epc.getEndPoint().getId()); 
															okEndpoint = this.gestioneAccreditamentoSuap(epcAccreditoSuap, u, conn);
														}
														urlReloadUtenti = epcModificaProfiloUtente.getUrlReloadUtenti(); 
														query.put(epcModificaProfiloUtente.getEndPoint().getNome().toUpperCase(), super.DBI); super.DBI="";

													} else {


														ruoloI = req.getParameter("roleId" + epcModificaProfiloUtente.getEndPoint().getNome()) != null && 
																!req.getParameter("roleId" + epcModificaProfiloUtente.getEndPoint().getNome()).trim().equals("") ? 
																		interoFromRequest("roleId" + epcModificaProfiloUtente.getEndPoint().getNome()) : -1;
																		u.setRuoloId(ruoloI);
																		u.setEnabled(u.getRuoloId() > 0);

																		//GESTIONE UNICA per guc 2.1
																		if(u.isEnabled()){

																			//Element operationInsertUtente = (Element)xpath.evaluate("//endpoint[@name='" + nomeEndpoint + "']/operation[@name='InsertUtente']",doc, XPathConstants.NODE);
																			epcInsertUtente = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.INSERTUTENTE, epc.getEndPoint().getId()); 


																			switch (epc.getEndPoint().getId()) {
																			//case "endpointDBVam" : 
																			case EndPoint.VAM :
																			{

																				if (u.getClinicheVam().size()>0){
																					for (int j=0; j<u.getClinicheVam().size(); j++){
																						u.setClinicaId(u.getClinicheVam().get(j).getIdClinica());
																						okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);
																					}
																				} else {
																					u.setClinicaId(-1);
																					okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);
																				} 
																				break;
																			}

																			//case "endpointDBGisa" : 
																			case EndPoint.GISA : 
																			{
																				if (u.getStruttureGisa().size()>0){
																					for (int j=0; j<u.getStruttureGisa().size(); j++){
																						u.setStrutturagisaId(u.getStruttureGisa().get(j).getIdStruttura());
																						okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);
																					}
																				} else {
																					u.setStrutturagisaId(-1);
																					okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);
																				}


																				break;
																			}

																			//case "endpointDBGisa" : 
																			case EndPoint.GISA_EXT : 
																			{	

																				okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);


																				epcAccreditoSuap = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.ACCREDITASUAP, epc.getEndPoint().getId());
																				okEndpoint = this.gestioneAccreditamentoSuap(epcAccreditoSuap, u, conn);


																				break ;
																			}
																			//case "endpointDBBdu" : 
																			case EndPoint.BDU : 
																			{
																				if (u.getCaniliBdu().size()>0){
																					for (int j=0; j<u.getCaniliBdu().size(); j++){
																						u.setCanilebduId(u.getCaniliBdu().get(j).getIdCanile());
																						okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);
																					}
																				} else {
																					u.setCanilebduId(-1);
																					okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);
																				}
																				break;
																			}
																			//case "endpointDBImportatori" : 
																			case EndPoint.IMPORTATORI : 
																			{
																				if (u.getImportatori().size()>0){
																					for (int j=0; j<u.getImportatori().size(); j++){
																						u.setId_importatore(u.getImportatori().get(j).getIdImportatore());
																						okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);
																					}
																				} else {
																					u.setId_importatore(-1);
																					okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);
																				}
																				break;
																			}
																			default :
																			{
																				okEndpoint = this.gestioneInserimentoUtente(epcInsertUtente, u, conn);

																				if(epc.getEndPoint().getId()==EndPoint.GISA_EXT)
																				{
																					epcAccreditoSuap = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.ACCREDITASUAP, epc.getEndPoint().getId()); 
																					okEndpoint = this.gestioneAccreditamentoSuap(epcAccreditoSuap, u, conn);

																					break;
																				}
																			}
																			if (okEndpoint)
																				query.put(epc.getEndPoint().getNome().toUpperCase(), super.DBI); super.DBI="";

																			}

																		}
																		System.out.println("Aggiornato ruolo su "+epc.getEndPoint().getNome());


													}  }
								catch(Exception excEndpoint){
									okEndpoint = false;
									logger.error("Errore Su End Point "+epc.getEndPoint().getNome());
								}
								finally{
									DbUtil.chiudiConnessioneJDBC(null, conn);
								}

								if(!okEndpoint){
									if (epc.getEndPoint().getId()!= EndPoint.GUC){
										logger.error("Errore durante la modifica dell'utente " + u.getUsername() + " nell'endpoint " + epc.getEndPoint().getNome());
										erroreEndpoint = erroreEndpoint + "Errore durante la modifica dell'utente in " + epc.getEndPoint().getNome() + "\n" ;
									}
								}else {
									if (urlReloadUtenti!=null && !urlReloadUtenti.equals("")){ 
										if (!utentePreModifica.getUsername().equals(u.getUsername())){ //NEL CASO CAMBIO L'USERNAME FACCIO LA RELOAD SIA SUL VECCHIO USERNAME CHE SUL NUOVO
											String urlReloadUtentiPre = null; 
											urlReloadUtentiPre = urlReloadUtenti+""+utentePreModifica.getUsername()+";-;"+u.getUsername();  //RELOAD PUNTUALE
											String resp = UrlUtil.getUrlResponse(urlReloadUtentiPre);
											logger.info("Reload Utenti (VECCHIA USERNAME) - Url: " + urlReloadUtentiPre + " - Esito: " + resp);
											urlReloadUtentiPre = "";
											if (resp==null || !resp.equalsIgnoreCase("OK")){
												erroreEndpoint = erroreEndpoint + "Errore durante il reload (VECCHIA USERNAME) dell'utente in " + epc.getEndPoint().getNome() + "\n";
											}
										}	
										urlReloadUtenti = urlReloadUtenti+""+u.getUsername();  //RELOAD PUNTUALE
										String resp = null ;
										try
										{
											resp = UrlUtil.getUrlResponse(urlReloadUtenti);
										}
										catch(Exception ee)
										{
											logger.error("Errore  In Reload Utenti : "+ee.getMessage());
										}
										/**
										 * 
										 * LOG  DELLA CHIAMATA RELOAD UTENTE
										 */
										LogReloadUtente logReload = new LogReloadUtente();
										logReload.setUrl_invocata(urlReloadUtenti);
										logReload.setResponse(resp);
										logReload.setUsername(u.getUsername());
										logReload.setEndpoint(epc.getEndPoint().getNome());
										logReload.setId_utente(u.getId());
										logReload.setTipo_op("edit");
										logReload.insert(db);

										logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
										urlReloadUtenti = "";
										if (resp==null || !resp.equalsIgnoreCase("OK")){
										//	erroreEndpoint = erroreEndpoint + "Errore durante il reload dell'utente in " + epc.getEndPoint().getNome() + "\n";
										}
									} 
								}
							} }
						
						
						
						
						
						if (u.isCessato()==false)
						{

							if (u.confrontaUtente(utentePreModifica, EndPoint.GISA) ||
									u.confrontaUtente(utentePreModifica, EndPoint.GISA_EXT) ||
									u.confrontaUtente(utentePreModifica, EndPoint.BDU) ||
									u.confrontaUtente(utentePreModifica, EndPoint.VAM) ||
									u.confrontaUtente(utentePreModifica, EndPoint.DIGEMON)) {
								u.setEnabled(true);
							UtenteGucDAO.insert(db, u, epcInserimentoGUC);
							}
							LogUtenteDAO.loggaUtente(db, u, "Variazione Profilo",BBButente);

						}

						if(erroreEndpoint != null && !erroreEndpoint.equals("")){
							setErrore(erroreEndpoint);
						} else{
							setMessaggio("Utente aggiornato con successo"); 
						}
						u.setQuery(query);
						LogUtenteDAO.loggaUtente(db, u, "Settaggio Data Fine Validita Su Profilo Old",BBButente,"");
						redirectTo("Home.us");	



					}
				}
				else
				{
					gotoPage( "/jsp/guc/edit_profilo.jsp" );

				}
			}
			else{
				setErrore("Utente con id " + id + " non trovato");
				goToAction(new Home());
			}
		}
		catch(Exception e){
			e.printStackTrace();
			setErrore("Errore durante la modifica dell'utente");
			req.setAttribute("UserRecord", u);
			goToAction(new ToEditAnagrafica());
		}

	}

	private int confrontaBean(Utente u_new, Utente u_old) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Boolean flag = false;	
		Boolean flagRuoli = false;
		String endpointModStrutt = getListaModificheStrutt();

		BeanMap map = new BeanMap(u_old);
		PropertyUtilsBean propUtils = new PropertyUtilsBean();
		for (Object propNameObject : map.keySet()) {
			String propertyName = (String) propNameObject;
			Object property1 = propUtils.getProperty(u_old, propertyName);
			Object property2 = propUtils.getProperty(u_new, propertyName);

			if (property1!=null && property2!=null){
				Object p1 = property1.toString();
				Object p2 = property2.toString();

				if (propertyName.equals("ruoli")){    //RUOLI
					Set<Ruolo> r1 = (Set<Ruolo>) property1;
					Set<Ruolo> r2 = (Set<Ruolo>) property2;   	
					ArrayList<Object> a = new ArrayList<Object>(r1);
					ArrayList<Object> b = new ArrayList<Object>(r2);
					if (r1.size()==r2.size()){
						if (confrontaDatiComplessi(a, b)==true)
							flagRuoli = true;
					}
					else {
						flagRuoli=true;
					}
				} else if (propertyName.equals("extOption")){
					TreeMap treeMap_old = new TreeMap();
					TreeMap treeMap_new = new TreeMap();
					treeMap_old.putAll(u_old.getExtOption());
					treeMap_new.putAll(u_new.getExtOption());
					if (u_old.getHashRuoli().toString().equals(u_new.getHashRuoli().toString())){
						if (treeMap_new.toString().equals(treeMap_old.toString())){
							flagRuoli = false;
						} else {
							flagRuoli = true;
						}	
					} else {
						flagRuoli = true;
					}
				} else if (propertyName.equals("clinicheVam")){ //CLINICHE VAM
					ArrayList<Clinica> c1 = (ArrayList<Clinica>)property1;
					ArrayList<Clinica> c2 = (ArrayList<Clinica>)property2;
					if (c1.size()==c2.size()){
						if (confrontaDatiComplessi(c1, c2)==true){
							endpointModStrutt = endpointModStrutt+"endpointDBVam;";
							flagRuoli = true;
						}
					} 
					else {
						endpointModStrutt = endpointModStrutt+"endpointDBVam;";
						flagRuoli=true;
					}
				} else if (propertyName.equals("caniliBdu")){ //CANILI BDU
					ArrayList<Canile> c1 = (ArrayList<Canile>)property1;
					ArrayList<Canile> c2 = (ArrayList<Canile>)property2;
					if (c1.size()==c2.size()){
						if (confrontaDatiComplessi(c1, c2)==true){
							endpointModStrutt = endpointModStrutt+"endpointDBBdu;";
							flagRuoli=true;
						}
					}
					else {
						endpointModStrutt = endpointModStrutt+"endpointDBBdu;";
						flagRuoli=true;
					}
				} else if (propertyName.equals("struttureGisa")){ //STRUTTURE GISA
					ArrayList<Struttura> c1 = (ArrayList<Struttura>)property1;
					ArrayList<Struttura> c2 = (ArrayList<Struttura>)property2;
					if (c1.size()==c2.size()){
						if (confrontaDatiComplessi(c1, c2)==true){
							endpointModStrutt = endpointModStrutt+"endpointDBGisa;";
							flagRuoli=true;
						}
					}
					else {
						endpointModStrutt = endpointModStrutt+"endpointDBGisa;";
						flagRuoli=true;
					}		        
				} else if (propertyName.equals("importatori")){ //IMPORTATORI
					ArrayList<Importatori> c1 = (ArrayList<Importatori>)property1;
					ArrayList<Importatori> c2 = (ArrayList<Importatori>)property2;	        	
					if (c1.size()==c2.size()){
						if (confrontaDatiComplessi(c1, c2)==true){
							endpointModStrutt = endpointModStrutt+"endpointDBImportatori;";
							flagRuoli=true;
						}
					}
					else {
						endpointModStrutt = endpointModStrutt+"endpointDBImportatori;";
						flagRuoli=true;
					}	
				} else{ //CONTROLLA TUTTI I CAMPI IN COMUNE ECCETTO LE STRUTTURE ED I RUOLI 
					if (!p1.equals(p2) && !propertyName.equalsIgnoreCase("hashRuoli") && !propertyName.equalsIgnoreCase("extOption") 
							&& !propertyName.contains("clinica") && !propertyName.contains("canile") && !propertyName.contains("importator") 
							&& !propertyName.contains("strutturagisa")){
						flag=true;
					}
				}
			}
			else if((property1!=null && property2==null) || (property1==null && property1!=null)){
				flag=true;
			}
		}
		int ret = 0;
		if (flag==true && flagRuoli==false){ //CAMBIATI SOLO GLI ATTRIBUTI
			ret=1;
		}else if (flag==false && flagRuoli==true){ //CAMBIATI SOLO I RUOLI
			ret=2;
		}else if (flag==true && flagRuoli==true){ //CAMBIATI SIA RUOLI CHE ATTRIBUTI
			ret=3;
		}

		setListaModificheStrutt(endpointModStrutt);

		return ret;
	}






	private Boolean cambio_profilo(Utente u, Utente utentePremodifica, EndPointConnector epc, Connection conn) throws Exception{
		Boolean okEndpoint = false;
		switch (epc.getEndPoint().getId()) {
		//case "endpointDBVam" : 
		case EndPoint.VAM : 

			if (u.confrontaUtente(utentePremodifica, EndPoint.VAM))
			{
			okEndpoint = this.gestioneCambioProfilo(epc, u, conn);
			}
			else
				okEndpoint=true;
			break;

			//case "endpointDBGisa" : 
		case EndPoint.GISA : 

			if (u.confrontaUtente(utentePremodifica, EndPoint.GISA))
			{
				okEndpoint = this.gestioneCambioProfilo(epc, u, conn);
			}
			else
				okEndpoint=true;

			break;
		case EndPoint.GISA_EXT : 
			
			if (u.confrontaUtente(utentePremodifica, EndPoint.GISA_EXT))
			{
				okEndpoint = this.gestioneCambioProfilo(epc, u, conn);
			}
			else
				okEndpoint=true;

			break;
			// BASTA IL PRECEDENTE (dbGISAL)
			//	case "endpointDBGisa_ext" : 
			//		okEndpoint = this.gestioneCambioProfilo(operationCambioProfilo, operationType, u, conn, nomeEndpoint);

			//		break;

			//case "endpointDBBdu" : 
		case EndPoint.BDU : 
			if (u.confrontaUtente(utentePremodifica, EndPoint.BDU))
			{
			okEndpoint = this.gestioneCambioProfilo(epc, u, conn);
			}
			else
			okEndpoint=true;
			break;

			//case "endpointDBImportatore" : 
		case EndPoint.IMPORTATORI : 
			if (u.confrontaUtente(utentePremodifica, EndPoint.BDU))
			{
			okEndpoint = this.gestioneCambioProfilo(epc, u, conn);
			}else
			okEndpoint=true;
			break;

			//case "endpointDBDigemon" : 
		case EndPoint.DIGEMON : 
			if (u.confrontaUtente(utentePremodifica, EndPoint.DIGEMON))
			{
				okEndpoint = this.gestioneCambioProfilo(epc, u, conn);
			}
			else
			okEndpoint=true;
			break;
	
		default :

			break;
		}
		return okEndpoint;
	}



	private Boolean confrontaDatiComplessi (Object a, Object b) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Boolean flag = false;
		int size1 = (int) a.getClass().getMethod("size", null).invoke(a, null);
		int size2 = (int) b.getClass().getMethod("size", null).invoke(b, null);

		Class[] cArg = new Class[1]; 
		cArg[0]= int.class;

		if (size1>0 && size2>0){
			int cont  = 0;
			for (int i=0;i<size1;i++){
				Method m1 = a.getClass().getMethod("get",cArg);
				Object o1 = m1.invoke(a, i);
				String cc1 = (String)o1.getClass().getMethod("toString", null).invoke(o1, null);
				for (int j=0;j<size2;j++){
					Method m2 = b.getClass().getMethod("get",cArg);
					Object o2 = m2.invoke(b, j);
					String cc2 = (String)o2.getClass().getMethod("toString", null).invoke(o2, null);

					if (cc1.equals(cc2)){
						cont++;			
					}
				}
			}
			if (cont!=size1){
				flag=true;
			}
		}		
		return flag;
	}

	public String getListaModificheStrutt() {
		return listaModificheStrutt;
	}

	public void setListaModificheStrutt(String listaModificheStrutt) {
		this.listaModificheStrutt = listaModificheStrutt;
	}
}
