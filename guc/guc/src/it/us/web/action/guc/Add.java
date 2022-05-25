/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;


import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.guc.Canile;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.Importatori;
import it.us.web.bean.guc.LogReloadUtente;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Utente;
import it.us.web.constants.ExtendedOptions;
import it.us.web.dao.AslDAO;
import it.us.web.dao.guc.LogUtenteDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.PasswordHash;
import it.us.web.util.guc.UrlUtil;
import it.us.web.util.guc.Utility;
import it.us.web.util.mail.bean.PecMailSenderThread;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

public class Add extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( Add.class );
	static byte[] keyValue = new byte[] { 'U', 'S', '9', '5', '6', '0', '0', '3', '1', '.', 'd','o', 'd', 'i', 'c', 'i' };

	
	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

		
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		
		Utente u = null;
		boolean okEndpoint;
		String erroreEndpoint = "";
		HashMap<String, String> query=new HashMap<String, String>();
		try{
		
			u = new Utente();
			BeanUtils.populate(u, req.getParameterMap());
			String origPassword = u.getPassword();
		//	u.setPassword(PasswordHash.encrypt(origPassword));
		//	u.setPasswordEncrypted( Utility.getInstance().encrypt(origPassword) );
			
			u.setLuogoVam(stringaFromRequest("luogoVam"));
			u.setNr_iscrione_albo_vet_privato_vam(stringaFromRequest("nr_iscrione_albo_vet_privato_vam"));
			u.setId_provincia_iscrizione_albo_vet_privato_vam(interoFromRequest("id_provincia_iscrizione_albo_vet_privato_vam"));
			
			//Controllo univocita'' comune e gestore
			if(interoFromRequest("roleIdGisa_ext")==10000006 && 
			   UtenteGucDAO.checkEsistenzaComuneGestore(db,u.getComuneGestore(),u.getGestore()) > 0)
			{
				setErrore("Utente gia'' esistente per comune e gestore acque");
				req.setAttribute("UserRecord", u);
				goToAction(new ToAdd());
			}
			else{
			
			//Controllo univocita'' dell'username su guc_utenti
				
			//List<Utente> utentiList = UtenteGucDAO.listaUtentibyUsername(db,u.getUsername()); //persistence.createCriteria(Utente.class).add(Restrictions.ilike("username", u.getUsername())).list();
			if(1==2/* && utentiList.size() > 0*/){
				setErrore("Username gia'' esistente");
				req.setAttribute("UserRecord", u);
				goToAction(new ToAdd());
			} 

	
			else if(UtenteGucDAO.listaUtentibyCf(db,u.getCodiceFiscale(),u.getNome(),u.getCognome()).size() > 0)
			{
				setErrore("Codice fiscale gia'' usato per altri utenti con nome e cognome diversi.");
				req.setAttribute("UserRecord", u);
				goToAction(new ToAdd());
			}
			else {
			u.setEntered(new Date());
			u.setEnteredBy(utente.getId());
			u.setModified(u.getEntered());
			u.setModifiedBy(u.getEnteredBy());
			
		
			//Set asl e ruoli
			u.setAsl( AslDAO.getAslbyId(db,interoFromRequest("idAsl")) );
			
			
			
			System.out.println("IL NUMERO IMMESSO PER STAB E "+u.getNumRegStab());
			/*ASSOCIAZIONE DELLE CLINICHE AL RUOLO VAM*/
			costruisciListaCliniche();
			if(req.getAttribute("clinicheUtenteHashVam")!=null){
				TreeMap<Integer, ArrayList<Clinica>> clinicheUtenteHash = (TreeMap<Integer, ArrayList<Clinica>>)req.getAttribute("clinicheUtenteHashVam");
				String[] idCliniche = req.getParameterValues("clinicaId");
				
				ArrayList<Clinica> clinicheAsl = null;
				if(clinicheUtenteHash != null){
					if(u.getAsl().getId() > 0){
						clinicheAsl = clinicheUtenteHash.get(u.getAsl().getId());
					}
					else{
						clinicheAsl = new ArrayList<Clinica>();
						for(int asl : clinicheUtenteHash.keySet()){
							clinicheAsl.addAll(clinicheUtenteHash.get(asl));
						}
					}
				}
	
				u.setClinicheVam(new ArrayList<Clinica>());
				if (idCliniche!=null && clinicheAsl!=null){
					for(int i = 0 ; i < idCliniche.length; i++){
						int idClinica = Integer.parseInt(idCliniche[i]);
						for(Clinica cc : clinicheAsl){
							if (cc.getIdClinica()==idClinica){
								u.getClinicheVam().add(cc);
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
				if(idCanili!=null && caniliAsl!=null){
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
			
			/*ASSOCIAZIONE DELLE STRUTTURE AL RUOLO GISA*/
			//GISA NON e'' ANCORA SUPPORTATO
	/*		costruisciListaStruttureGisa();
			if(req.getAttribute("struttureUtenteHashGisa")!=null){
				TreeMap<Integer, ArrayList<Struttura>> strutturaUtenteHash = (TreeMap<Integer, ArrayList<Struttura>>)req.getAttribute("struttureUtenteHashGisa");
				String[] idStrutture = req.getParameterValues("strutturaId");
				
				ArrayList<Struttura> struttureAsl = null;
				if(strutturaUtenteHash != null){
					if(u.getAsl().getId() > 0){
						struttureAsl = strutturaUtenteHash.get(u.getAsl().getId());
					}
					else{
						struttureAsl = new ArrayList<Struttura>();
						for(int asl : strutturaUtenteHash.keySet()){
							struttureAsl.addAll(strutturaUtenteHash.get(asl));
						}
					}
				}
	
				u.setStruttureGisa(new ArrayList<Struttura>());
				for(int i = 0 ; i < idStrutture.length; i++){
					int idStruttura = Integer.parseInt(idStrutture[i]);
					for(Struttura s : struttureAsl){
						if (s.getIdStruttura()==idStruttura){
							u.getStruttureGisa().add(s);
						}
					}
				}
			} */
			
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
			
			
			
			Set<Ruolo> ruoli = new HashSet<Ruolo>();
			HashMap<String, HashMap<String,String>> map = new HashMap<String, HashMap<String,String>>(); 
			ExtendedOptions e = new ExtendedOptions();
			Ruolo r = null;
			int ruoloI;
			String ruoloS;
			for(GUCEndpoint endpoint : GUCEndpoint.values()){
				r = new Ruolo();
				r.setEndpoint(endpoint.toString());
				ruoloI = req.getParameter("roleId" + endpoint) != null && 
						 !req.getParameter("roleId" + endpoint).trim().equals("") ? 
						 interoFromRequest("roleId" + endpoint) : -1;
				r.setRuoloInteger( ruoloI );
				ruoloS = req.getParameter("roleDescription" + endpoint) != null ? 
						 stringaFromRequest("roleDescription" + endpoint) : "";
				r.setRuoloString( ruoloS );
				r.setUtente(u);
				ruoli.add(r);
				
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
			
			
			u.setRuoli(ruoli);
			u.setExtOption(map);
			
			EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
			EndPointConnector epcInserimentoGUC = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.INSERTUTENTE, EndPoint.GUC);
			
			/*INSERIMENTO IN GUC*/
			BUtente   BBButente        = (BUtente) req.getSession().getAttribute( "utente" );
			UtenteGucDAO.insert(db, u, epcInserimentoGUC);
			//LogUtenteDAO.loggaUtente(db, u, "Inserimento", BBButente);

			
			//Inserimento utente nei vari endpoint
			
			
			EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.INSERTUTENTE); 
			
			EndPointConnector epcAccreditoSuap = null;

			Connection conn = null;
			String urlReloadUtenti = "";
			
			  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
				  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);

				okEndpoint = true;
				urlReloadUtenti = "";
				try{

					String dbDatasource = epc.getEndPoint().getDataSourceSlave();
					urlReloadUtenti = epc.getUrlReloadUtenti();
					System.out.println("DEBUG RELOAD UTENTI: " + urlReloadUtenti);
					
				
					if(epc.getEndPoint().getId()==EndPoint.GISA_EXT)
					{
						epcAccreditoSuap = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.ACCREDITASUAP, epc.getEndPoint().getId()); 
					}
					
					String QQQuery = epc.getSql();
					
					conn = DbUtil.ottieniConnessioneJDBC(epc.getEndPoint().getDataSourceSlave());
					
					ruoloI = req.getParameter("roleId" + epc.getEndPoint().getNome()) != null && 
					 		 !req.getParameter("roleId" + epc.getEndPoint().getNome()).trim().equals("") ? 
					         interoFromRequest("roleId" + epc.getEndPoint().getNome()) : -1;
					u.setRuoloId(ruoloI);
					u.setEnabled(u.getRuoloId() > 0);
					
					//GESTIONE UNICA per guc 2.1
					if(u.isEnabled()){
						switch (epc.getEndPoint().getId()) {
						//case "endpointDBVam" : 
						case EndPoint.VAM: 
						{
								if (u.getClinicheVam().size()>0){
								for (int j=0; j<u.getClinicheVam().size(); j++){
									u.setClinicaId(u.getClinicheVam().get(j).getIdClinica());
									okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
								}
							} else {
								u.setClinicaId(-1);
								okEndpoint = this.gestioneInserimentoUtente(epc, u, conn );
							} 
							break;
						}
						//case "endpointDBGisa" : 
						case EndPoint.GISA:
						{
								if (u.getStruttureGisa().size()>0){
								for (int j=0; j<u.getStruttureGisa().size(); j++){
									u.setStrutturagisaId(u.getStruttureGisa().get(j).getIdStruttura());
									okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
									
								}
							} else {
								u.setStrutturagisaId(-1);
								okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
							}
							
						break;
					}
					//case "endpointDBGisa" : 
					case EndPoint.GISA_EXT:
					{
									okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
									  okEndpoint = this.gestioneAccreditamentoSuap(epcAccreditoSuap, u, conn);
									
						
							break ;
						}
						//case "endpointDBBdu" : 
						case EndPoint.BDU : 
						{
								if (u.getCaniliBdu().size()>0){
								for (int j=0; j<u.getCaniliBdu().size(); j++){
									u.setCanilebduId(u.getCaniliBdu().get(j).getIdCanile());
									okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
								}
							} else {
								u.setCanilebduId(-1);
								okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
							}
							break;
						}
						//case "endpointDBImportatori" : 
						case EndPoint.IMPORTATORI : 
						{
								if (u.getImportatori().size()>0){
								for (int j=0; j<u.getImportatori().size(); j++){
									u.setId_importatore(u.getImportatori().get(j).getIdImportatore());
									okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
								}
							} else {
								u.setId_importatore(-1);
								okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
							}
							break;
						}
						case EndPoint.DIGEMON : 
						{
							okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
							break;
						}
						
						default :
							
							break;
						}
						if(okEndpoint)
							query.put(epc.getEndPoint().getNome().toUpperCase(), super.DBI); super.DBI="";
					  }
					  else {
						  System.out.println("DEBUG RELOAD UTENTI: SETTO urlReloadUtenti a string vuota" );
						  urlReloadUtenti = "";
					  } 
				}
				catch(Exception excEndpoint){
					okEndpoint = false;
					logger.error("Attenzione Si e'' verificato un errore Su EndPoint "+epc.getEndPoint().getNome());
				}
				finally {
					DbUtil.chiudiConnessioneJDBC(null, conn);
				}
				
				if(!okEndpoint){
					logger.error("Errore durante l'inserimento dell'utente " + u.getUsername() + " nell'endpoint " + epc.getEndPoint().getNome());
					erroreEndpoint = erroreEndpoint + "Errore durante l'inserimento dell'utente in " + epc.getEndPoint().getNome() + "\n" ;
				}
				else {
					if (urlReloadUtenti!=null && !urlReloadUtenti.equals("")){
						System.out.println("DEBUG RELOAD UTENTI: urlReloadUtenti != null " + urlReloadUtenti);
						urlReloadUtenti = urlReloadUtenti+""+u.getUsername();	//RELOAD PUNTUALE
						System.out.println("DEBUG RELOAD UTENTI: urlReloadUtenti = " + urlReloadUtenti);
						String resp = null ;
						try
						{
							resp = UrlUtil.getUrlResponse(urlReloadUtenti);
							System.out.println("DEBUG RELOAD UTENTI: resp: " );
							System.out.println("DEBUG RELOAD UTENTI: resp: " + resp);
						}
						catch(Exception ee)
						{
							System.out.println("DEBUG RELOAD UTENTI: logger " );
							logger.warn("Attenzione hostname per ReloadUtenti non riconosciuto");
						}
						/**
						 * LOG  DELLA CHIAMATA RELOAD UTENTE
						 */
						LogReloadUtente logReload = new LogReloadUtente();
						logReload.setUrl_invocata(urlReloadUtenti);
						logReload.setResponse(resp);
						logReload.setUsername(u.getUsername());
						logReload.setEndpoint(epc.getEndPoint().getNome());
						logReload.setId_utente(u.getId());
						logReload.setTipo_op("add");
						logReload.insert(db);
						
						logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
						urlReloadUtenti = "";
						System.out.println("DEBUG RELOAD UTENTI: urlReloadUtenti = \"\"" );
						if (resp==null || !resp.equalsIgnoreCase("OK")){
							
//							try
//							{
//								resp = UrlUtil.getUrlResponse(urlReloadUtenti);
//							}
//							catch(Exception ee)
//							{
//								logger.warn("Attenzione hostname per ReloadUtenti non riconosciuto");
//							}
//							
//							if (resp==null || !resp.equalsIgnoreCase("OK"))
//								erroreEndpoint = erroreEndpoint + "Errore durante il reload dell'utente in " + epc.getEndPoint().getNome() + "\n";
						}
					} 
				}
			}
			
			if(erroreEndpoint != null && !erroreEndpoint.equals("")){
				setErrore(erroreEndpoint);
			} else{
				setMessaggio("Utente inserito con successo"); 
			}
			u.setQuery(query);
			LogUtenteDAO.loggaUtente(db, u, "Inserimento", BBButente,"");

			if (req.getParameter("inviomail")!=null && req.getParameter("inviomail").equalsIgnoreCase("si"))
				invioMail(u);
			
			salvaStorico(db, u, origPassword, (String)req.getRemoteAddr());

 		    
 		    
		
 			redirectTo("guc.Detail.us?id=" + u.getId());
			}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			setErrore("Errore durante l'inserimento dell'utente");
			req.setAttribute("UserRecord", u);
			goToAction(new ToAdd());
		}
		
		
	}

//	private void inviaMail(Utente u, String origPassword){
//			//qui bisogna inviare la mail
//			System.out.println("Invio la mail a "+u.getEmail());
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Date date = new Date();
//			String time = sdf.format(date);
//			String currentTime    = String.valueOf(time);
//			String oggetto = "Comunicazione Creazione Credenziali";
//			String messaggio ="In data " + currentTime +" sono state create le credenziali per la seguente utenza:\n\nUsername: "+u.getUsername()+" \nPassword: "+origPassword;
//			
//			if (u.getHashRuoli().get(GUCEndpoint.Gisa.toString())!=null && u.getHashRuoli().get(GUCEndpoint.Gisa.toString()).getRuoloInteger()>0)
//					messaggio+="\nGISA: "+u.getHashRuoli().get(GUCEndpoint.Gisa.toString()).getRuoloString();
//			if (u.getHashRuoli().get(GUCEndpoint.Gisa_ext.toString())!=null && u.getHashRuoli().get(GUCEndpoint.Gisa_ext.toString()).getRuoloInteger()>0)
//				messaggio+="\nGISA: "+u.getHashRuoli().get(GUCEndpoint.Gisa_ext.toString()).getRuoloString();
//			if (u.getHashRuoli().get(GUCEndpoint.bdu.toString())!=null && u.getHashRuoli().get(GUCEndpoint.bdu.toString()).getRuoloInteger()>0)
//				messaggio+="\nBDU: "+u.getHashRuoli().get(GUCEndpoint.bdu.toString()).getRuoloString();
//			if (u.getHashRuoli().get(GUCEndpoint.Vam.toString())!=null && u.getHashRuoli().get(GUCEndpoint.Vam.toString()).getRuoloInteger()>0)
//				messaggio+="\nVAM: "+u.getHashRuoli().get(GUCEndpoint.Vam.toString()).getRuoloString();
//			if (u.getHashRuoli().get(GUCEndpoint.Digemon.toString())!=null && u.getHashRuoli().get(GUCEndpoint.Digemon.toString()).getRuoloInteger()>0)
//				messaggio+="\nDIGEMON: "+u.getHashRuoli().get(GUCEndpoint.Digemon.toString()).getRuoloString();
//			messaggio+="\n\nSi consiglia di modificare la password dopo il primo accesso.";
//			SendMail sm = new SendMail();
//		    sm.send(u.getEmail(), "gisadev@u-s.it",  messaggio, oggetto);
//		    
//	}
	
	private void salvaStorico(Connection db, Utente u, String origPassword, String ip) {
		String pwdNewEncrypted = null;
		try {
			//pwdNewEncrypted = encryptPassword(origPassword);
			PreparedStatement pst = db.prepareStatement("insert into storico_cambio_password(id_utente, ip_modifica,  data_modifica, nuova_password, nuova_password_decript) values (?, ?, now(), ?,  ?);");
			int i = 0;
			pst.setInt(++i, u.getId());
			pst.setString(++i, ip);
			pst.setString(++i, u.getPassword());
			pst.setString(++i, pwdNewEncrypted);
			pst.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String encryptPassword(String password) throws Exception{
		// byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
		String encrypteToken = encrypt(password, keyValue);
		return encrypteToken;
	 }
	
	public static String encrypt(String plainText, byte[] encryptionKey) throws Exception {
		  Key key = generateKey(encryptionKey);
	      Cipher c = Cipher.getInstance("AES");
	      c.init(Cipher.ENCRYPT_MODE, key);
	      byte[] encVal = c.doFinal(plainText.getBytes());
	      String encryptedValue = new BASE64Encoder().encode(encVal);
	      return encryptedValue;
	  }
	private static Key generateKey(byte[] keyValue) throws Exception {
	    Key key = new SecretKeySpec(keyValue, "AES");
	    return key;
	}
	
	
	private void invioMail(Utente u) throws SendFailedException, MessagingException{
		
		//qui bisogna inviare la mail
		System.out.println("Invio la mail a "+u.getEmail());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String time = sdf.format(date);
		String currentTime    = String.valueOf(time);
		String oggetto = "Comunicazione Creazione Credenziali";
		//String messaggio ="In data " + currentTime +" sono state create le credenziali per la seguente utenza:\n\nUsername: "+u.getUsername()+" \nPassword: "+u.getPassword();
		String messaggio ="In data " + currentTime +" l'utente e'' stato profilato per il seguente accesso:\n";
		
		if (u.getHashRuoli().get(GUCEndpoint.Gisa.toString())!=null && u.getHashRuoli().get(GUCEndpoint.Gisa.toString()).getRuoloInteger()>0)
				messaggio+="\nGISA: "+u.getHashRuoli().get(GUCEndpoint.Gisa.toString()).getRuoloString();
		if (u.getHashRuoli().get(GUCEndpoint.Gisa_ext.toString())!=null && u.getHashRuoli().get(GUCEndpoint.Gisa_ext.toString()).getRuoloInteger()>0)
			messaggio+="\nGISA: "+u.getHashRuoli().get(GUCEndpoint.Gisa_ext.toString()).getRuoloString();
		if (u.getHashRuoli().get(GUCEndpoint.bdu.toString())!=null && u.getHashRuoli().get(GUCEndpoint.bdu.toString()).getRuoloInteger()>0)
			messaggio+="\nBDU: "+u.getHashRuoli().get(GUCEndpoint.bdu.toString()).getRuoloString();
		if (u.getHashRuoli().get(GUCEndpoint.Vam.toString())!=null && u.getHashRuoli().get(GUCEndpoint.Vam.toString()).getRuoloInteger()>0)
			messaggio+="\nVAM: "+u.getHashRuoli().get(GUCEndpoint.Vam.toString()).getRuoloString();
		if (u.getHashRuoli().get(GUCEndpoint.Digemon.toString())!=null && u.getHashRuoli().get(GUCEndpoint.Digemon.toString()).getRuoloInteger()>0)
			messaggio+="\nDIGEMON: "+u.getHashRuoli().get(GUCEndpoint.Digemon.toString()).getRuoloString();
		//messaggio+="\n\nSi consiglia di modificare la password dopo il primo accesso.";
		messaggio+="\n\nDa questo momento sara'' possibile accedere tramite Spid.";
		
		
		String mailUS = ApplicationProperties.getProperty("mail.cc");

		System.out.println("[GUC] Preparazione invio mail a "+u.getEmail()+" e "+mailUS);
		
		Runnable run = new PecMailSenderThread(oggetto, messaggio, currentTime, u.getEmail(), mailUS);
		new Thread(run).start();
	
	}

}
