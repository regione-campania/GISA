/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import it.us.web.action.GenericAction;
import it.us.web.action.endpointconnector.ConfigAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.AslDAO;
import it.us.web.dao.guc.LogUtenteDAO;
import it.us.web.dao.guc.RuoloDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.GUCOperationType;
import it.us.web.util.guc.PasswordHash;
import it.us.web.util.guc.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.xml.xpath.XPathConstants;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.oreilly.servlet.MultipartRequest;

public class Import extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( Import.class );

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}


	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		costruisciListaRuoli();
		
		HashMap<String, String> query=new HashMap<String, String>();
		Utente u = null;
		boolean okEndpoint;
		GUCEndpoint nomeEndpoint = null;
		String erroreEndpoint = "";
		boolean esisteUtente;
		String ruoloI = "" ;
		//		MultipartRequest multi = null;
		//		int maxUploadSize = 50000000;
		//		multi = new MultipartRequest(req, ".", maxUploadSize);


		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd_MM_yyyy_hh_mm");

		ServletFileUpload uploader = null ;
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) context.getAttribute("FILES_DIR_FILE");
		fileFactory.setRepository(filesDir);
		uploader = new ServletFileUpload(fileFactory);
		File file = null ;
		if(!ServletFileUpload.isMultipartContent(req)){
			throw new ServletException("Content type is not multipart/form-data");
		}
		FileOutputStream fos = null ;

		try {
			List<FileItem> fileItemsList = uploader.parseRequest(req);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			if(fileItemsIterator.hasNext()){
				FileItem fileItem = fileItemsIterator.next();
				System.out.println("FieldName="+fileItem.getFieldName());
				System.out.println("FileName="+fileItem.getName());
				System.out.println("ContentType="+fileItem.getContentType());
				System.out.println("Size in bytes="+fileItem.getSize());
				String currTime = sdf2.format(new Date(System.currentTimeMillis()));
				File dir = new File (context.getAttribute("FILES_DIR")+File.separator+currTime);
				if (!dir.exists())
					dir.mkdir();

				file = new File(context.getAttribute("FILES_DIR")+File.separator+currTime+File.separator+fileItem.getName());
				fos = new FileOutputStream(context.getAttribute("FILES_DIR")+File.separator+currTime+File.separator+"LOG_"+fileItem.getName());
				System.out.println("Absolute Path at server="+file.getAbsolutePath());
				fileItem.write(file);

			}
		} 
		catch (FileUploadException e) {e.printStackTrace();} 
		catch (Exception e) {e.printStackTrace();}

		PrintWriter pw  = new PrintWriter(fos);
	//	FileInputStream fiStream = new FileInputStream(file);
		/*
		 * LookupList llist = new LookupList(db,"lookup_specie_allevata");
		 * llist.addItem(-1, "-nessuno-");
		 * context.getRequest().setAttribute("SpecieA", llist);
		 */

		//File myFileT = multi.getFile("file");
		Connection conn1 = null;
		this.setChiamaReload(false);
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "" ;
			try {
				int j = 0 ;
				while ((line=br.readLine())!=null && ! line.equalsIgnoreCase("")){
					if (j>0){
						String idruolobdu = "-1";
						String autorizzazionebdu= "";
						String luogobdu = "";
						String luogovam = "";
						String idcanulebdu = "-1";

						String idruolovam = "-1";
						String idruoloExt = "-1";
						String clinichevam = "";
						String [] splitLine = line.split(";");

						String  nome = splitLine[0];
						String  cognome= splitLine[1];
						String  cf= splitLine[2];
						String  username= splitLine[3];
						String  pwd = splitLine[4];
						String  asl = splitLine[5];
						String  dataScadenza = splitLine[6];

						String idruologisa = splitLine[7];
						if (splitLine.length>8){
							idruolobdu = splitLine[8];
							autorizzazionebdu= splitLine[9];
							luogobdu = splitLine[10];
							idcanulebdu = splitLine[11];
						}
						if (splitLine.length>12){
							idruolovam = splitLine[12];
							clinichevam = splitLine[13];
							luogovam = splitLine[10];
						}
						if (splitLine.length>13){
							idruoloExt = splitLine[14];
						}
						
						
						
						u = new Utente();
						u.setOldUsername(username);
						u.setUsername(username);
						u.setPassword(pwd);
						if (!dataScadenza.equals(""))
							u.setExpires(sdf.parse(dataScadenza));
						u.setLuogo(luogobdu);
						u.setLuogoVam(luogovam);
						u.setNome(nome);
						u.setCognome(cognome);
						u.setCodiceFiscale(cf);
						u.setAsl( AslDAO.getAslbyId(db,Integer.parseInt(asl)) );
						u.setNumAutorizzazione(autorizzazionebdu);

						String origPassword = u.getPassword();
						u.setPassword(PasswordHash.encrypt(origPassword));
						u.setPasswordEncrypted( Utility.getInstance().encrypt(origPassword) );
						//Controllo univocita'' dell'username su guc_utenti
						List<Utente> utentiList = UtenteGucDAO.listaUtentibyUsername(db,u.getUsername()); //persistence.createCriteria(Utente.class).add(Restrictions.ilike("username", u.getUsername())).list();
						if(utentiList.size() > 0){
							System.out.println("Username gia'' esistente");
							u.setId(utentiList.get(0).getId());
						}
						u.setEntered(new Date());
						u.setEnteredBy(utente.getId());
						u.setModified(u.getEntered());
						u.setModifiedBy(u.getEnteredBy());


						//Set asl e ruoli

						/*ASSOCIAZIONE DELLE CLINICHE AL RUOLO VAM*/
						
						Set<Ruolo> ruoli = new HashSet<Ruolo>();
						Ruolo r = null;
						String endpoint = "" ;
						if (!idruologisa.equals("") && !idruologisa.equals("-1") &&  !idruologisa.equals("-")){
							r = new Ruolo();
							ArrayList<Ruolo> ruoloUtenteList = (ArrayList<Ruolo>)req.getAttribute("ruoloUtenteListGisa");
							for (Ruolo ruolo : ruoloUtenteList){
								if (ruolo.getRuoloInteger()==Integer.parseInt(idruologisa)){
									r.setRuoloString(ruolo.getRuoloString());
								}
							}
							r.setEndpoint("Gisa");
							r.setRuoloInteger( Integer.parseInt(idruologisa) );
							r.setUtente(u);
							ruoli.add(r);
						}

						if (!idruolobdu.equals("") && !idruolobdu.equals("-1")&&  !idruolobdu.equals("-")){
							r = new Ruolo();
							ArrayList<Ruolo> ruoloUtenteList = (ArrayList<Ruolo>)req.getAttribute("ruoloUtenteListbdu");
							for (Ruolo ruolo : ruoloUtenteList){
								if (ruolo.getRuoloInteger()==Integer.parseInt(idruologisa)){
									r.setRuoloString(ruolo.getRuoloString());
								}
							}							
							r.setEndpoint("bdu");
							r.setRuoloInteger( Integer.parseInt(idruologisa) );
							r.setUtente(u);
							ruoli.add(r);
						}

						if (!idruolovam.equals("") && !idruolovam.equals("-1") && !idruolovam.equals("-")){
							r = new Ruolo();
							ArrayList<Ruolo> ruoloUtenteList = (ArrayList<Ruolo>)req.getAttribute("ruoloUtenteListVam");
							for (Ruolo ruolo : ruoloUtenteList){
								if (ruolo.getRuoloInteger()==Integer.parseInt(idruologisa)){
									r.setRuoloString(ruolo.getRuoloString());
								}
							}
							r.setEndpoint("Vam");
							r.setRuoloInteger( Integer.parseInt(idruologisa) );
							r.setUtente(u);
							ruoli.add(r);
						}
						
						if (!idruoloExt.equals("") && !idruoloExt.equals("-1") && !idruoloExt.equals("-")){
							r = new Ruolo();
							ArrayList<Ruolo> ruoloUtenteList = (ArrayList<Ruolo>)req.getAttribute("ruoloUtenteListGisaExt");
							for (Ruolo ruolo : ruoloUtenteList){
								if (ruolo.getRuoloInteger()==Integer.parseInt(idruoloExt)){
									r.setRuoloString(ruolo.getRuoloString());
								}
							}
							r.setEndpoint("Gisa_ext");
							r.setRuoloInteger( Integer.parseInt(idruoloExt) );
							r.setUtente(u);
							ruoli.add(r);
						}

						u.setRuoli(ruoli);
						
						EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
						EndPointConnector epcInserimentoGUC = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.INSERTUTENTE, EndPoint.GUC);

						if(utentiList.size() <= 0){
							BUtente   BBButente        = (BUtente) req.getSession().getAttribute( "utente" );
							UtenteGucDAO.insert(db, u, epcInserimentoGUC);
							LogUtenteDAO.loggaUtente(db, u, "Inserimento",BBButente);
						}
						else{
							UtenteGucDAO.update(db, u);
							for (Ruolo rr : ruoli){
								RuoloDAO.updateForImport(db, rr);
							}
						}

						okEndpoint = true;
						try{
							//Modifica utente nei vari endpoint
							
							
							EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.CHECKESISTENZAUTENTE); 
							
							 for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
								  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
								okEndpoint = true;
								try{
									
									
										String QQQuery = epc.getSql();
										System.out.println("QQQQ IMPORT "+QQQuery);
										String dataSource = epc.getEndPoint().getDataSourceSlave();

										
										conn1 = DbUtil.ottieniConnessioneJDBC(dataSource);
										

										esisteUtente = this.gestioneEsistenzaUtente(epc, u, conn1);

										if (epc.getEndPoint().getId()==EndPoint.GISA){
											u.setRuoloId(Integer.parseInt(idruologisa));
											u.setEnabled(u.getRuoloId() > 0);
										}
										if (epc.getEndPoint().getId()==EndPoint.VAM){
											u.setRuoloId(Integer.parseInt(idruolovam));
											u.setEnabled(u.getRuoloId() > 0);
										}
										if (epc.getEndPoint().getId()==EndPoint.BDU){
											u.setRuoloId(Integer.parseInt(idruolobdu));
											u.setEnabled(u.getRuoloId() > 0);
										}

//										if(esisteUtente){
//											Element operationModifyUtente = (Element)xpath.evaluate("//endpoint[@name='" + nomeEndpoint + "']/operation[@name='ModifyUtente']",doc, XPathConstants.NODE);
//											operationType = GUCOperationType.valueOf(operationModifyUtente.getAttribute("type"));
//											okEndpoint = this.gestioneAggiornamentoUtente(operationModifyUtente, operationType, u, conn1, nomeEndpoint.toString());
//																										
//											pw.println(u.getUsername()+ ";AGGIORNATO IN ["+nomeEndpoint+"]");
//											pw.flush();
//										}
//										else if(u.isEnabled()){
//											Element operationInsertUtente = (Element)xpath.evaluate("//endpoint[@name='" + nomeEndpoint + "']/operation[@name='InsertUtente']",doc, XPathConstants.NODE);
//											operationType = GUCOperationType.valueOf(operationInsertUtente.getAttribute("type"));
//											okEndpoint = this.gestioneInserimentoUtente(operationInsertUtente, operationType, u,conn1,nomeEndpoint.toString());
//											pw.println(u.getUsername()+ ";INSERITO IN ["+nomeEndpoint+"]");
//											pw.flush();
//										}
										//DbUtil.chiudiConnessioneJDBC(null, conn1);
									
								}
								catch(Exception excEndpoint){
									okEndpoint = false;
									excEndpoint.printStackTrace();
								}
								finally{
									DbUtil.chiudiConnessioneJDBC(null, conn1);
								}

								if(!okEndpoint){
									if (epc.getEndPoint().getId()!= EndPoint.GUC){
									pw.println(u.getUsername()+ ";Errore durante la modifica dell'utente in ["+nomeEndpoint+"]");
									pw.flush();								
									erroreEndpoint = erroreEndpoint + "Errore durante la modifica dell'utente in " + nomeEndpoint + "\n" ;
									}
								}
							}
						}
						catch(Exception excEndpoint){
							okEndpoint = false;
							excEndpoint.printStackTrace();
						}
						if(!okEndpoint){
							pw.println(u.getUsername()+ ";Errore durante l'inserimento dell'utente  in ["+nomeEndpoint+"]");
							pw.flush();		
							System.out.println("Errore durante l'inserimento dell'utente " + u.getUsername() + " nell'endpoint " + nomeEndpoint);
							erroreEndpoint = erroreEndpoint + "Errore durante l'inserimento dell'utente in " + nomeEndpoint + "\n" ;
						}
					}
					j++;
				}
			}
			catch(Exception e){
				e.printStackTrace();
				pw.println("ERRORE;ERRORE NEL TEMPLATE DEL FILE");
				pw.flush();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		goToAction(new ToImport());
	}
}
