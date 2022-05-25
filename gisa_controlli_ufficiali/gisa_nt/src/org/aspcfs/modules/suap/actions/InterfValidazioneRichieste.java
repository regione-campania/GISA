/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.actions;





import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.opu.base.GestoreComunicazioniVam;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineeMobiliHtmlFields;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.suap.base.CodiciBackEndRicercaGlobaleValidazione;
import org.aspcfs.modules.suap.base.CodiciBackendValidazione;
import org.aspcfs.modules.suap.base.CodiciRisultatoFrontEnd;
import org.aspcfs.modules.suap.base.LineaProduttiva;
import org.aspcfs.modules.suap.base.LineaProduttivaList;
import org.aspcfs.modules.suap.base.LineaProduttivaValidazione;
//import org.aspcfs.modules.suap.base.LineaProduttiva;
import org.aspcfs.modules.suap.base.ListaRichiesteSuap;
import org.aspcfs.modules.suap.base.Operatore;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.suap.base.Richiesta;
import org.aspcfs.modules.suap.base.RisultatoValidazioneRichiesta;
import org.aspcfs.modules.suap.base.Stabilimento;
import org.aspcfs.modules.suap.utils.CodiciRisultatiRichiesta;
import org.aspcfs.modules.trasportoanimali.base.Stato;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class InterfValidazioneRichieste extends CFSModule{

	public final int OP_NUOVO_STAB = 1;
	public final int OP_AMPLIAMENTO = 2;
	public final int OP_CESSAZIONE=3;
	public final int OP_VARIAZIONE = 4;
	public final int OP_SOSPENSIONE = 5;
	public final int OP_MODIFICA_STATO_LUOGHI = 6;

	public final int FLUSSO_REGISTRABILI =4;
	public final int FLUSSO_RICONOSCIMENTO =1;
	public final int FLUSSO_AZIENDE_ZOOTECNICHE=3;
	public final int FLUSSO_APICOLTURA =2;
	public final int FLUSSO_SOA =5;



	Logger logger = Logger.getLogger(InterfValidazioneRichieste.class);




	public String executeCommandDownloadAllegatoSingoloPerRichiesta(ActionContext cont)
	{
		String idRichiesta = null;
		int indiceAllegatoRichiesto = -1;
		File[] allegati = null;
		Connection db = null;
		//uso un metodo gia' implementato in StabilimentoAction per scaricare file da documentale data richiesta
		try
		{
			db = getConnection(cont);
			idRichiesta = cont.getRequest().getParameter("idRichiesta");

			Stabilimento stab = new Stabilimento( );
			stab.queryRecordStabilimentoIdOperatore(db, Integer.parseInt(idRichiesta));
			int altId = stab.getAltId();  //mi serve poiche' gli allegati sono associati all'alt id dello stab della richiesta

			indiceAllegatoRichiesto = Integer.parseInt(cont.getRequest().getParameter("indiceAllegato"));
			allegati = new StabilimentoAction().scaricaFilesDaDocumentale(cont,altId);
			File fileRichiesto = allegati[indiceAllegatoRichiesto];
			return downloadFileToClient(cont,fileRichiesto);
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			try
			{
				freeConnection(cont,db);
			}
			catch(Exception ex){}
		}

		return "-none-";
	}







	public String downloadFileToClient(ActionContext context, File fileRichiesto/*String nomeFile, String pathFile*/)
	{
		String fileType = "application/octet-stream";


		//  if (new File(fileName).exists()){
		// Find this file id in database to get file name, and file type

		// You must tell the browser the file type you are going to send
		// for example application/pdf, text/plain, text/html, image/jpg
		context.getResponse().setContentType(fileType);

		// Make sure to show the download dialog
		context.getResponse().setHeader("Content-disposition","attachment; filename=\""+fileRichiesto.getName()+"\"");

		// Assume file name is retrieved from database
		// For example D:\\file\\test.pdf



		// This should send the file to browser
		try
		{	 OutputStream out = context.getResponse().getOutputStream();

		FileInputStream in = new FileInputStream(fileRichiesto);
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0){
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();
		out.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "SystemError";
		}
		return ("-none-");	
	}





	public String executeCommandMostraRichiesteDaValutare(ActionContext cont)
	{


		int idAslUtente = ((UserBean)cont.getSession().getAttribute("User")).getSiteId();
		String viewName = null;

		int tipoRichieste = ListaRichiesteSuap.RICHIESTE_VALIDABILI;
		String tipoRichiesteString = cont.getRequest().getParameter("tipoRichieste");
		try {tipoRichieste = Integer.parseInt(tipoRichiesteString);} catch (Exception e){}
		cont.getRequest().setAttribute("tipoRichieste", String.valueOf(tipoRichieste));
		Connection db = null;
		try
		{

			db = getConnection(cont);
			
			ComuniAnagrafica c = new ComuniAnagrafica();
			c.setInRegione(true);
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) cont.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			cont.getRequest().setAttribute("ComuniList", comuniList);
			
			ListaRichiesteSuap richieste = new ListaRichiesteSuap(db,idAslUtente, tipoRichieste);
			ArrayList<Richiesta> alTutteRichieste = new ArrayList<Richiesta>();
			ArrayList<Richiesta> alRichiesteVisualizzabili = new ArrayList<Richiesta>();
			ArrayList<Boolean> almenoUnPermessoValidoPerRichiesta = new ArrayList<Boolean>();


			LookupList listaStatiRichieste = new LookupList(db,"lookup_stato_validazione_richieste");
			cont.getRequest().setAttribute("ListaStati", listaStatiRichieste);
			if (richieste!=null)
			{
				alTutteRichieste = richieste.getRichieste();
				for(Richiesta richiesta : alTutteRichieste)
				{	
					boolean atLeastOnePermission = false; 

					//NB: LE PRENDO TUTTE
					//poiche' voglio visualizzarle tutte...
					alRichiesteVisualizzabili.add(richiesta);

					//...pero' voglio sapere se l'utente ha almeno un permesso che matcha per almeno uno dei flussi associati a quella richiesta
					//per sapere se dovro' attivare il bottone prosegui
					if(richiesta.getArrayPermessiRichiesta() == null || richiesta.getArrayPermessiRichiesta()[0]== null) //se l'array permessi e' null, allora la prendo sempre
					{//...se non c'e' associato un array di permessi, sono dati spuri ed in ogni caso il bottone prosegui non potra' esserci
						
						if(hasPermission(cont,"suap-asl-validazione-view") || hasPermission(cont,"suap-regione-validazione-view"))
						{
							atLeastOnePermission = true;
							}else
							
							atLeastOnePermission = false;
					}
					else
					{
						for(String permessoSuRichiesta : richiesta.getArrayPermessiRichiesta())
						{
							//..in tal caso almeno un flusso matcha con un permesso dell'utente, quindi ci sara' attivato il bottone prosegui per questa richiesta client side
							if(hasPermission(cont,permessoSuRichiesta+"-view"))
							{
								atLeastOnePermission = true;
								break;
							}
						}
					}

					almenoUnPermessoValidoPerRichiesta.add(atLeastOnePermission);
				}

				cont.getRequest().setAttribute("lista_richieste", alRichiesteVisualizzabili);
				cont.getRequest().setAttribute("proseguibilita_richieste", almenoUnPermessoValidoPerRichiesta);

			}

			viewName = "mostraRichiesteDaValutare";
			
		}
		catch(Exception ex)
		{
			viewName = "SystemError";
			ex.printStackTrace();
		}
		finally
		{
			try { freeConnection(cont,db); } catch(Exception ex) { }
		}


		return viewName;
	}
	
	public String executeCommandGetCampiAggiuntiviDiValidazione(ActionContext cont)
	{
		Integer idLinea = Integer.parseInt(cont.getRequest().getParameter("idLinea"));
		Integer idRichiesta = Integer.parseInt(cont.getRequest().getParameter("idRichiesta"));
		 
		Connection db = null;
		try
		{
			db = getConnection(cont);
			System.out.println("INVOCATO METODO PER OTTENIMENTO CAMPI AGGIUNTIVI PER LA LINEA "+idLinea);
			Stabilimento richiesta = new Stabilimento();
			richiesta.queryRecordStabilimentoIdOperatore(db, idRichiesta);
			
			 
			LineaProduttivaList listaLinee = richiesta.getListaLineeProduttive();
			for(Object lineaO : listaLinee)
			{
				LineaProduttiva linea = (LineaProduttiva)lineaO;
				if(linea.getIdRelazioneAttivita() == idLinea.intValue())
				{
					linea.searchCampiEstesiValidazione(db);
					cont.getRequest().setAttribute("lineaConCampiAssociati", linea );
					break;
				}
				
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{ freeConnection(cont,db); }catch(Exception ex){}
		}
		 
		return "campiAggiuntiviJsonResp";
	}
	

	
	public String executeCommandPaginaPerRichiesta(ActionContext cont)
	{
		String viewName = "paginaPerRichiesta";
		String pIvaImpresa = cont.getRequest().getParameter("pIvaImpresa");
		String codiceFiscaleImpresa = cont.getRequest().getParameter("codiceFiscaleImpresa");
		String idRichiesta = cont.getRequest().getParameter("idRichiesta");
		String idTipoRichiesta = cont.getRequest().getParameter("idTipoRichiesta");

		cont.getRequest().setAttribute("GenericError", cont.getRequest().getAttribute("GenericError"));
		cont.getRequest().setAttribute("MailSendError", cont.getRequest().getAttribute("MailSendError"));
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection db = null;

		try
		{	

			db = getConnection(cont);

			Stabilimento richiesta = new Stabilimento();
			richiesta.queryRecordStabilimentoIdOperatore(db, Integer.parseInt(idRichiesta));
			
			/*try
			{
				LineaProduttivaList listaLinee = richiesta.getListaLineeProduttive();
				for(Object lineaO : listaLinee)
				{
					LineaProduttiva linea = (LineaProduttiva)lineaO;
					linea.searchCampiEstesiValidazione(db);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println("Fallito tentativo di ottenimento campi estesi di validazione per le linee. CONTINUO UGUALMENTE");
			}
			*/
			LookupList listaScia = new LookupList(db,"lookup_scia_stato_validazione");
			cont.getRequest().setAttribute("StatiValidazioneScia", listaScia);

			switch(Integer.parseInt(idTipoRichiesta))
			{

			case OP_NUOVO_STAB :
			{
				String codiceRegionale = richiesta.generaNumeroRegistrazione(db, idRichiesta,false);
				cont.getRequest().setAttribute("codice_regionale",codiceRegionale);
				
				break ;
			}

			case OP_VARIAZIONE :
			{
				String codiceRegionale = richiesta.generaNumeroRegistrazione(db,idRichiesta,true);
				cont.getRequest().setAttribute("codice_regionale",codiceRegionale);
				break ;
			}
			}



			cont.getRequest().setAttribute("Richiesta", richiesta);



		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName="SystemError";  
		}
		finally
		{
			try {this.freeConnection(cont,db); } catch(Exception ex) {}
		}

		return viewName;
	}


	public String executeCommandValidaEConvergi(ActionContext cont) throws SQLException, IOException
	//generico, grabba per qualunque tipo di richiesta, e fa multiplexing a seconda del tipo
	{
		
		int idStabilimento = -1 ;

		Stabilimento stabRichiesta = new Stabilimento();
		String comuneRichiesta ="";
		String istatComuneRichiesta ="";
		boolean obbligoCheckFile = false;

		Integer ret = null;
		RisultatoValidazioneRichiesta esitoValidazioneModifiche = new RisultatoValidazioneRichiesta() ;
		String idRichiesta = cont.getRequest().getParameter("idRichiesta");
		int idTipoRichiesta =Integer.parseInt( cont.getRequest().getParameter("idTipoRichiesta"));

		Connection db = null;
		ResultSet rs = null;
		PreparedStatement pst= null;
		try
		{
			db = getConnection(cont);

			stabRichiesta.queryRecordStabilimentoIdOperatore(db, Integer.parseInt(idRichiesta));
			/*campi estesi di validazione */
			try
			{
				LineaProduttivaList listaLinee = stabRichiesta.getListaLineeProduttive();
				for(Object lineaO : listaLinee)
				{
					LineaProduttiva linea = (LineaProduttiva)lineaO;
					linea.searchCampiEstesiValidazione(db);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println("Fallito tentativo di ottenimento campi estesi di validazione per le linee. CONTINUO UGUALMENTE");
			}
			
			
			
			comuneRichiesta = getComuneRichiedente(db, stabRichiesta.getIdOperatore());
			istatComuneRichiesta = getIstatComune(db, comuneRichiesta);
			obbligoCheckFile = checkFirmaObbligatoria(db, istatComuneRichiesta);


			if(stabRichiesta.getOperatore().isValidato()==true) 
			{
				ret = CodiciRisultatiRichiesta.TENTATO_INSERIMENTO_CON_REFRESH;
				esitoValidazioneModifiche.setIdRisultato(CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_KO_RICHIESTA_EVASA);
				cont.getRequest().setAttribute("EsitoValidazione",esitoValidazioneModifiche);

				cont.getRequest().setAttribute("risultato_richiesta",ret+"");
				//return executeCommandPaginaPerRichiesta(cont);
				
				//mettere ritorno al dettaglio dell operatore apicoltura	
				cont.getRequest().setAttribute("id_stabilimento", getIdStabilimentoApiDaRichiesta(db, idRichiesta));
				return "details_temp_apicoltura";
				
			}
			
			
			/*
			 *  1->Validato
			 *  2-> Non Validabile
			 * */
			String statoValidazione = cont.getParameter("statoValidazione");


			switch(idTipoRichiesta)
			{
			case OP_NUOVO_STAB:{ 

				esitoValidazioneModifiche =validazioneInserimentoNuovoStabilimento(cont,stabRichiesta);
				cont.getRequest().setAttribute("id_stabilimento", getIdStabilimentoApiDaRichiesta(db, idRichiesta));
				break;
			}
			default :
			{
				
				int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));
				/*GESTITE LE OPERAZIONI DI AMPLIAMENTO-VARIAZIONE-CESSAZIONE-SOSPENSIONE-MODIFICA STATO LUOGHI*/
				if(cont.getRequest().getParameter("candidato_scelto") == null && idStatoValidazione==1 ) 
				{
					esitoValidazioneModifiche = validazionePerAmpliamentoOCessazioneOVariazioneConRicercaGlobale(cont,true,stabRichiesta);
									
				}
				else
				{ 	//allora non si passa per la ricerca globale
					esitoValidazioneModifiche = validazionePerAmpliamentoOCessazioneOVariazioneConRicercaGlobale(cont,false,stabRichiesta);
				}
				break;
			}
			
			
			}

			cont.getRequest().setAttribute("EsitoValidazione",esitoValidazioneModifiche);

		}
		catch(Exception ex)
		{
			if (esitoValidazioneModifiche==null)
				esitoValidazioneModifiche= new RisultatoValidazioneRichiesta();
			
			esitoValidazioneModifiche.setIdRisultato(CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_KO_ERRORE);
			cont.getRequest().setAttribute("EsitoValidazione",esitoValidazioneModifiche);
			return executeCommandPaginaPerRichiesta(cont);
			//rimandare a pagina di gestione errore

		}
		finally
		{
			try {
				this.freeConnection(cont, db);
			} catch(Exception ex) 
			{}
		}

		//return executeCommandPaginaPerRichiesta(cont);
		
		//mettere ritorno al dettaglio dell operatore apicoltura
		return "details_temp_apicoltura";
	}
	
	
	private String getIdStabilimentoApiDaRichiesta(Connection db, String id_richiesta){
		
		String id_stab_out = "";
		String sql = "select id from apicoltura_apiari where id_operatore = (select id from apicoltura_imprese where id_richiesta_suap = ?)";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);

			pst.setInt(1, Integer.parseInt(id_richiesta));
			ResultSet rs= pst.executeQuery();
			if (rs.next())
			{	
				id_stab_out = rs.getString("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id_stab_out;
	}



	public boolean convergiDocumenti(int altId, int idStabilimento,ActionContext context) throws IOException 
	{



		HttpURLConnection conn = null;
		String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_ALLEGATI");
		// STAMPE

		URL obj;
		try {
			obj = new URL(url);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("altId");
			requestParams.append("=").append(altId);
			requestParams.append("&");
			requestParams.append("stabId");
			requestParams.append("=").append(idStabilimento);
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(getUserId(context));
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("ConvergenzaSuapAllegati");
			

			System.out.println("CONVERGENZA ALLEGATI IN VALIDAZIONE SCIA - URL : "+url+"&"+requestParams.toString());
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();

			conn.getContentLength();


			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto);
			}
			in.close();



		} catch (ConnectException e1) {
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "documents");
			throw e1 ;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "ERRORE NEL CARICAMENTO DEL FILE");
			context.getRequest().setAttribute("label", "documents");
			throw e ;
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "ERRORE NEL CARICAMENTO DEL FILE");
			context.getRequest().setAttribute("label", "documents");
			throw e ;
		}

		finally {
			conn.disconnect();
		}


		return true ;



	}


	public String executeCommandArchivia(ActionContext context)
	{

		Connection db = null ;
		try
		{
			db = getConnection(context);
			String idRichiesta = context.getParameter("stabId");
			String statoRichiesta = context.getParameter("statoRichiesta");
			String note = context.getParameter("noteValidazione");


			String sel = "update suap_ric_scia_stabilimento set stato_validazione = ? , note_validazione = ? where id_operatore = ?";
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, Integer.parseInt(statoRichiesta));
			pst.setString(2,note);
			pst.setInt(3, Integer.parseInt(idRichiesta));
			pst.execute();

		}
		catch(Exception e)
		{

		}
		finally
		{
			this.freeConnection(context, db);
		}

		return executeCommandMostraRichiesteDaValutare(context);


	}


	public RisultatoValidazioneRichiesta chiamaRicercaGlobale(Connection db , ActionContext cont,Stabilimento richiesta) throws SQLException
	{

		RisultatoValidazioneRichiesta risultatoValidazione = new RisultatoValidazioneRichiesta();


		Integer[] esitoRicercaGlobale = invocaDbiRicercaGlobalerPerAmpliamentoCessazioneVariazione(cont,db,richiesta.getOperatore().getIdOperatore(),risultatoValidazione.getListaAnagraficheCandidate());
		risultatoValidazione.setIdRisultato(esitoRicercaGlobale[0]);
		switch(esitoRicercaGlobale[0])
		{
		case CodiciBackEndRicercaGlobaleValidazione.VALIDAZIONE_BACKEND_RICERCAGLOBALE_DUPLICATI_OPU :
		{
			/*booleano che indica se occorre ritornare l'esito trovato o si puo continuare con la validazione*/
			
			risultatoValidazione.setAltEseguitaRicercaGlobale(true);
			break ;

		}
		case CodiciBackEndRicercaGlobaleValidazione.VALIDAZIONE_BACKEND_RICERCAGLOBALE_DUPLICATI_ORG :
		{
			risultatoValidazione.setAltEseguitaRicercaGlobale(true);
			break ;

		}
		case CodiciBackEndRicercaGlobaleValidazione.VALIDAZIONE_BACKEND_RICERCAGLOBALE_ANAGRAFICA_NONTROVATA :
		{
			risultatoValidazione.setAltEseguitaRicercaGlobale(true);
			break ;

		}
		case CodiciBackEndRicercaGlobaleValidazione.VALIDAZIONE_BACKEND_RICERCAGLOBALE_UNICO_ORG :
		{
			risultatoValidazione.setAltEseguitaRicercaGlobale(true);
			break ;

		}
		
		

		default :
		{
			if(esitoRicercaGlobale[0]== CodiciBackEndRicercaGlobaleValidazione.VALIDAZIONE_BACKEND_RICERCAGLOBALE_UNICO_IN_OPU )
			{
				risultatoValidazione.setIdStabilimentoTrovato(esitoRicercaGlobale[2]);
				risultatoValidazione.setAltEseguitaRicercaGlobale(false);
				break ;
			}
			else
			{
				risultatoValidazione.setIdRisultato(CodiciBackEndRicercaGlobaleValidazione.VALIDAZIONE_BACKEND_RICERCAGLOBALE_ANAGRAFICA_NONTROVATA);
				risultatoValidazione.setAltEseguitaRicercaGlobale(true);
				break ;
			}
		}

		}


		return risultatoValidazione ;
	}


	//	public Integer tentaValidazioneEConvergenzaPerAmpliamentoOCessazioneConRicercaGlobale(ActionContext cont,int idTipoRichiesta, String pIva, boolean conRicercaGlobale, boolean isAmpliamento, boolean isCessazioneGlobale) throws Exception
	//SICCOME LA CESSAZIONE E' QUASI IDENTICA ALL'AMPLICAMENTO, UTILIZZO stesso metodo
	public RisultatoValidazioneRichiesta validazionePerAmpliamentoOCessazioneOVariazioneConRicercaGlobale(ActionContext cont, boolean conRicercaGlobale, Stabilimento richiesta) throws Exception
	{

		Connection db = null;
		PreparedStatement pst = null, pst2 = null;
		ResultSet rs = null, rs2 = null;
		try
		{
			
			int userId = cont.getRequest().getSession().getAttribute("User") != null ? ((UserBean)cont.getRequest().getSession().getAttribute("User")).getUserId() : -1;
			Integer[] risposta ;
			int res = -1;
			int tRes = -1;

			String codiciNazionali = cont.getRequest().getParameter("codiciNazionali");
			int tipoLineaProduttiva = cont.getRequest().getParameter("idTipoLinea") != null ? Integer.parseInt(cont.getRequest().getParameter("idTipoLinea")) : -1;
			int idLineaProd = cont.getRequest().getParameter("idLinea") != null ? Integer.parseInt(cont.getRequest().getParameter("idLinea")) : -1;
			int idRichiesta = Integer.parseInt(cont.getRequest().getParameter("idRichiesta"));

			db = getConnection(cont);
			int idTipoRichiesta = richiesta.getOperatore().getIdOperazione();

			RisultatoValidazioneRichiesta risultato=  null ;


			if(conRicercaGlobale) //cerco l'anagrafica in tutto il sistema data la richiesta
			{
				risultato=  chiamaRicercaGlobale(db, cont, richiesta);
				if(risultato.isAltEseguitaRicercaGlobale()==true)
					return risultato;

			}
			else
			{ 

				risultato = new RisultatoValidazioneRichiesta();
				int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));

				if(cont.getRequest().getParameter("candidato_scelto")!=null && idStatoValidazione==1)
					risultato.setIdStabilimentoTrovato(Integer.parseInt(cont.getRequest().getParameter("candidato_scelto")));

			}

			
			
			switch(idTipoRichiesta)
			{
			case OP_AMPLIAMENTO :
			{

//				if(tipoLineaProduttiva!=FLUSSO_REGISTRABILI)
//				{
					if(!salvaCodiceNazionalePerLinea(cont,db,idLineaProd, codiciNazionali)  )
					{
						risultato.setIdRisultato(CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_KO_ERRORE);
						return risultato ;

					}
				//}
				res = invocaValidazioneAmpliamentoScia(cont,db,idRichiesta,idLineaProd,risultato.getIdStabilimentoTrovato() )[0];
				System.out.println("- res = invocaValidazioneAmpliamentoScia(cont,db,idRichiesta,idLineaProd,risultato.getIdStabilimentoTrovato() )[0]");
				System.out.println("- res = " + res);
				if(res == 5){
					System.out.println("- errore ampliamento: la linea inserita risulta gia presente ed attiva!");
				}
				risultato.setIdRisultato(res);
				
				/*Trasmetto la modifica anche alla bdu*/
				int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));

				if(risultato.getIdStabilimentoTrovato()>0 && idStatoValidazione ==1)
				{
					RicercheAnagraficheTab.inserOpu(db, risultato.getIdStabilimentoTrovato());
					try {
	
						GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
						gBdu.inserisciNuovaSciaBdu(risultato.getIdStabilimentoTrovato());
						
					}catch(SQLException e){
						System.out.println("ERRORE NON BLOCCANTE NEL CONTATTARE BDU ! CONTINUO");
						e.printStackTrace();
					}
					catch(Exception ex)
					{
						System.out.println("ERRORE NON BLOCCANTE NEL CONTATTARE BDU ! CONTINUO");
						ex.printStackTrace();
					}
				}

				break ;
			}

			case OP_CESSAZIONE :
			{
				
				GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
				
				if (gBdu.verificaCessazioneInBdu(risultato.getIdStabilimentoTrovato())==true)
				{
				if(richiesta.isCessazioneStabilimento()==false)
				{
					res = invocaValidazioneCessazionePuntualeScia(cont,db,idRichiesta,idLineaProd,risultato.getIdStabilimentoTrovato() )[0];
				}
				else
				{
					res = invocaValidazioneCessazioneGlobaleScia(cont,db,idRichiesta,risultato.getIdStabilimentoTrovato() )[0];
					
					
				}
				risultato.setIdRisultato(res);
				/*Trasmetto la modifica anche alla bdu*/
				int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));

				if(risultato.getIdStabilimentoTrovato()>0 && idStatoValidazione ==1 && risultato.getIdRisultato()==1)
				{
					RicercheAnagraficheTab.inserOpu(db, risultato.getIdStabilimentoTrovato());
				
				try {

					gBdu.cessazioneOperatoreBduScia(risultato.getIdStabilimentoTrovato(),richiesta.getDataFineAttivita());
					
				}catch(SQLException e){
					e.printStackTrace();
				}
				}
				}
				else
				{
				risultato.setIdRisultato(CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_CESSAZIONE_KO_ANIMALI_PRESENTI);
				}
				
				break ;
			}

			case OP_SOSPENSIONE :
			{
				if(richiesta.isSospensioneStabilimento()==false)
				{
					res = invocaValidazioneSospensionePuntualeScia(cont,db,idRichiesta,idLineaProd,risultato.getIdStabilimentoTrovato() )[0];
				}
				else
				{
					res = invocaValidazioneSospensioneGlobaleScia(cont,db,idRichiesta,risultato.getIdStabilimentoTrovato() )[0];
				}
				RicercheAnagraficheTab.inserOpu(db, risultato.getIdStabilimentoTrovato());
				risultato.setIdRisultato(res);
				break ;
			}
			case OP_VARIAZIONE :
			{

				Integer[] t = invocaValidazioneVariazioneGlobale(cont,db,idRichiesta,risultato.getIdStabilimentoTrovato() ); 
				res = t[0].intValue(); //nel caso di variazione, oltre al risultato ci risultato.getIdStabilimentoTrovato()  anche l'id dello stabilimento fittizio nuovo creato
				risultato.setIdRisultato(res);
				RicercheAnagraficheTab.inserOpu(db, risultato.getIdStabilimentoTrovato());
				
				try {
					GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
					gBdu.variazioneOperatoreBduSciaNoCessazione(risultato.getIdStabilimentoTrovato(),richiesta, getUserId(cont));
					
					GestoreComunicazioniVam gVam = new GestoreComunicazioniVam();
					gVam.variazioneOperatoreVamSciaNoCessazione(risultato.getIdStabilimentoTrovato(),richiesta, getUserId(cont));
					
				}catch(SQLException e){
					e.printStackTrace();
				}
				
				break;
			}
			case OP_MODIFICA_STATO_LUOGHI :
			{
				
				res = invocaValidazioneModificaStatoLuoghiGlobale(cont,db,idRichiesta,richiesta.getAltId(),risultato.getIdStabilimentoTrovato() )[0];
				risultato.setIdRisultato(res);
				RicercheAnagraficheTab.inserOpu(db, risultato.getIdStabilimentoTrovato());
				
				try 
				{
					if(richiesta.getSuperficie()>0)
					{
						int idRelStabLp = -1;
						org.aspcfs.modules.opu.base.Stabilimento stab = new org.aspcfs.modules.opu.base.Stabilimento(db, risultato.getIdStabilimentoTrovato());
				        Iterator<org.aspcfs.modules.opu.base.LineaProduttiva> itLp = stab.getListaLineeProduttive().iterator();
				        while (itLp.hasNext())
				        {
				        	org.aspcfs.modules.opu.base.LineaProduttiva lp = itLp.next();
				        	if(lp.getId()==635 || lp.getId()==636)
				        	{
				        		idRelStabLp = lp.getId_rel_stab_lp();
				        		break;
				        	}
						}
						
			        	LineeMobiliHtmlFields.updateValorePerFieldValuesSuperficie(db, richiesta.getSuperficie()+"", 81, 102, idRelStabLp);
			        	GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
			        	gBdu.variazioneSuperficie(risultato.getIdStabilimentoTrovato(),richiesta, getUserId(cont));
					}
				}
				catch(SQLException e){
					e.printStackTrace();
				}
				
				break;

			}
			default:{
				risultato.setIdRisultato(CodiciRisultatiRichiesta.TIPO_DI_OPERAZIONE_NONSUPPORTATA);
				break;
			}


			}

			if(risultato.getIdRisultato() == CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_OK)
			{
				if(idTipoRichiesta == OP_AMPLIAMENTO)
				{
					scaricaCampiAggiuntiviPerTutteLeLineeRichiestaValidata(userId,risultato.getIdStabilimentoTrovato(),richiesta,db);
				}
			
				
				try {
					convergiDocumenti(richiesta.getAltId(),risultato.getIdStabilimentoTrovato(),cont);
				} catch (IOException e) {
					risultato.setIdRisultato(CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_OK_DOCUMENTALE_OFFLINE);
					
				}
				
				
				
					if(risultato.getIdStabilimentoTrovato()>0)
					{
							RicercheAnagraficheTab.inserOpu(db, risultato.getIdStabilimentoTrovato());
							RicercheAnagraficheTab.inserRichiesta(db, richiesta.getAltId());
							
					}
					
	
					inviaPecAllegatiSciaVariazioneInserita(db, cont, richiesta, risultato);
			}


			
				
			


			return risultato;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
			
		}
		finally
		{
			super.freeConnection(cont,db);



		}




	}



	private boolean salvaCodiceNazionalePerLinea(ActionContext cont, Connection db, int idLineaProd,
			String valoreInseritoCodiceNazionale) 
	{
		PreparedStatement pst = null;
		//devo eseguire aggiornamento solo di una linea produttiva

		try
		{


			pst = db.prepareStatement("update suap_ric_scia_relazione_stabilimento_linee_produttive set codice_nazionale=?"+
					" where id = ?");
			
			pst.setString(1,valoreInseritoCodiceNazionale);
			//			pst.setInt(2,idStabilimento);
			pst.setInt(2, idLineaProd);
			//			System.out.println("------>"+pst);
			pst.executeUpdate();	

			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		finally
		{
			if(pst != null)  try { pst.close(); } catch(Exception ex){}
		}


	}





	//a seconda dell'intero ritornato sappiamo l'esito della richiesta di validazione / convergenza
	public RisultatoValidazioneRichiesta validazioneInserimentoNuovoStabilimento(ActionContext cont,Stabilimento richiesta) throws Exception 
	{ //ritorna un codice che descrive l'esito 

		RisultatoValidazioneRichiesta res = new RisultatoValidazioneRichiesta();
		Integer resultRichiesta = -1;
		String codiciNazionali = cont.getRequest().getParameter("codiciNazionali");
		int tipoFlussoLineaProduttiva = Integer.parseInt(cont.getRequest().getParameter("idTipoLinea"));
		int idLineaProd = Integer.parseInt(cont.getRequest().getParameter("idLinea")); /*questo e' id rel stab id (delle richieste)*/

		//lo risetto anche come parametro, in maniera tale che possa essere rimostrato al ritorno dal client
		//cont.getRequest().setAttribute("codiciNazionali",codiciNazionali);
		cont.getRequest().setAttribute("idLineaRichiesta",idLineaProd); //mi serve per mappare con il bottone valida tra i tanti, sul client quindi gliela risetto
		int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User") ) != null ? 
				((UserBean) cont.getRequest().getSession().getAttribute("User") ).getUserId() : -1;
				
		Connection db =null;
		PreparedStatement pst = null, pst2 = null;
		ResultSet rs = null, rs2 = null;
		Integer[] esitoDbi = new Integer[2];
		int idStabilimento = -1;
		boolean isAutocommit = false;

		try
		{
			db = this.getConnection(cont);
			
			isAutocommit = db.getAutoCommit();
			
//			db.setAutoCommit(false);
			//System.out.println("settato autocommit a false");
			
			idStabilimento = richiesta.getIdStabilimento();

			//se il tipo di linea produttiva, associata alla richiesta, e' di tipo diverso da 4, allora va aggiornata
			//solo quella linea produttiva utilizzando il codice nazionale associato
			//altrimenti se il tipo e' uguale a 4, vanno prese tutte le aggregate
			
			
//			if(tipoFlussoLineaProduttiva != FLUSSO_REGISTRABILI)
//			{
				String valoreInseritoCodiceNazionale = codiciNazionali;
				if(!salvaCodiceNazionalePerLinea(cont, db, idLineaProd, valoreInseritoCodiceNazionale))
				{
					System.out.println("ERRORE SALVATAGGIO CODICE NAZIONALE PER SCIA ");
					throw new Exception("ERRORE SALVATAGGIO CODICE NAZIONALE PER SCIA ");
				}
//			}


			if(richiesta.getTipoInserimentoScia()==richiesta.TIPO_SCIA_APICOLTURA)
			{
				
				
				esitoDbi =invocaDbiInserimentoLineaApicoltura(cont,db,richiesta.getOperatore().getIdOperatore(),idLineaProd);
			}
			else
			{
				
				esitoDbi = invocaDbiInserimentoLinea(cont,db,richiesta.getOperatore().getIdOperatore(),idLineaProd);
				if(esitoDbi[2]>0)
				{
					
					RicercheAnagraficheTab.inserOpu(db, esitoDbi[2]);
					RicercheAnagraficheTab.inserRichiesta(db, richiesta.getAltId());
				}
				
				

			}
			
			
			
			

			switch(esitoDbi[0])
			{

			case CodiciRisultatiRichiesta.INSERIMENTO_NUOVO_OPERATORE_OK_NON_ESISTEVA_INSERITONUOVO :
			{
				int idNuovoOp = esitoDbi[1];

				try 
				{
					convergiDocumenti(richiesta.getAltId(),esitoDbi[2],cont);
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					res.setIdRisultato(CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_OK_DOCUMENTALE_OFFLINE);
				}
				cont.getRequest().setAttribute("idNuovoOperatoreAggiunto", idNuovoOp+"");

				break ;
			}

			case CodiciRisultatiRichiesta.INSERIMENTO_NUOVO_OPERATORE_OK_ESISTEVA_INSERITONUOVO :
			{
				int idNuovoOp = esitoDbi[1];
				try {
					convergiDocumenti(richiesta.getAltId(),esitoDbi[2],cont);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					res.setIdRisultato(CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_OK_DOCUMENTALE_OFFLINE);
				}
				cont.getRequest().setAttribute("idNuovoOperatoreAggiunto", idNuovoOp+"");
				break ;
			}

			case CodiciRisultatiRichiesta.INSERIMENTO_NUOVO_OPERATORE_OK_ESISTEVA_CONVERSO :
			{
				int idOpPreesistente = esitoDbi[1];
				try {
					convergiDocumenti(richiesta.getAltId(),esitoDbi[2],cont);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					res.setIdRisultato(CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_OK_DOCUMENTALE_OFFLINE);
				}
				cont.getRequest().setAttribute("idOperatorePreesistente",idOpPreesistente+"");
				break ;
			}
			


			}


			if(esitoDbi[0] != CodiciRisultatiRichiesta.VALIDATA_LINEA_PARZIALE && esitoDbi[0] != CodiciRisultatiRichiesta.INSERIMENTO_NUOVO_OPERATORE_KO_STABILIMENTO_PREESISTENTE_FATTO_NULLA)
			{
				
				/*solo in questo caso gestisco i campi estesi per tutte le linee della richiesta */
				scaricaCampiAggiuntiviPerTutteLeLineeRichiestaValidata(userId,esitoDbi[2],richiesta,db);
				
				int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));

				/*Trasmetto la modifica anche alla bdu*/
				if(esitoDbi[2]>0 && idStatoValidazione==1)
				{
					try {
	
						GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
						gBdu.inserisciNuovaSciaBdu(esitoDbi[2]);
						
					}catch(SQLException e){
						e.printStackTrace();
						System.out.println("ERRORE NEL TRASMETTERE LA MODIFICA ALLA BDU (ERRORE NON BLOCCANTE, CONTINUO UGUALMENTE)");
					}
					catch(Exception ex)
					{
						System.out.println("ERRORE NEL TRASMETTERE LA MODIFICA ALLA BDU (ERRORE NON BLOCCANTE, CONTINUO UGUALMENTE)");
						ex.printStackTrace();
					}
				}
				
				
				if(richiesta.getTipoInserimentoScia()==richiesta.TIPO_SCIA_APICOLTURA)
				{
					
					RicercheAnagraficheTab.inserApi(db, esitoDbi[2]);
					RicercheAnagraficheTab.inserRichiesta(db, richiesta.getAltId());
					
				}
				else
				{
					
					RicercheAnagraficheTab.inserOpu(db, esitoDbi[2]);
					RicercheAnagraficheTab.inserRichiesta(db, richiesta.getAltId());

					

				}
				//if (idStatoValidazione!=2)
					//inviaPecAllegatiNuovaSciaInserita(db, cont, richiesta, esitoDbi);
	
			}

			res.setIdRisultato(esitoDbi[0]);

			
			//db.commit();
			System.out.println("COMMIT ESEGUITO");
			
			return res;


		}
		catch(SQLException e)
		{
			logger.error("Er rore : "+e.getMessage());
			e.printStackTrace();
			//db.rollback();
			//System.out.println("ROLLBACK ESEGUITO");
			throw e;
		}
		finally
		{
			//db.setAutoCommit(isAutocommit);
		}



	}


	/*GESTIONE DEI CAMPI ESTESI MESSI IN FASE DI VALIDAZIONE O CH EIN FASE DI VALIDAZIONE VANNO GENERATI ES. EX CODE L30 (= CODICE STAZIONE) */
	public void scaricaCampiAggiuntiviPerTutteLeLineeRichiestaValidata(int userId,int stabDopoValid,Stabilimento richiesta, Connection db,org.aspcfs.modules.opu.base.LineaProduttivaList linee) throws Exception
	{
		 
		if(linee==null)
		{
		
		LineaProduttivaList allLineeRichiesta = richiesta.getListaLineeProduttive();
		/*individuo quella che sto validando */
		for(Object lineaO : allLineeRichiesta)
		{
			LineaProduttiva linea = (LineaProduttiva)lineaO;
			Integer idRelStabLPPrimaDiValidazione = linea.getId(); //----> QUESTO E' ID_REL_STAB_ID DELLA RICHIESTA (in suap)

			ArrayList<LineeMobiliHtmlFields> campiAggiuntiviValidaz = linea.getHtmlFieldsValidazione();
			/*estraggo i campi aggiuntivi per quella linea */
			for(LineeMobiliHtmlFields campoAggiuntivo : campiAggiuntiviValidaz)
			{
				Integer idLineaMLDelCampo = campoAggiuntivo.getId_linea();  //----> QUESTO E' ID DELLA LINEA IN MASTER LIST, 
				/*data la linea richiesta, passo da questa alla nuova che e' stata validata */
				Integer idOpuRelStabLPDopoValidaz = getRelStabLPDopoValidaz(idRelStabLPPrimaDiValidazione,db);  //----> OTTENGO E' ID_REL_STAB_ID DELLA LINEA VALIDATA

				if(campoAggiuntivo.getDbiGenerazione() != null && campoAggiuntivo.getDbiGenerazione().trim().length( )> 0)
				{
					/*allora per questo campo aggiuntivo e' prevista la generazione server side usando una dbi */
					String dbiToCall = campoAggiuntivo.getDbiGenerazione();
					String generated = callDbiGenerazioneCampoEstesoValidazione(dbiToCall,richiesta.getIdOperatore(),db,null);
					System.out.println(">>>>SALVO VALORE CAMPO AGGIUNTIVO DI VALIDAZIONE PER LINEA CON ID (REL STAB ID )"+linea.getId()+", ID ATTIVITA LINEA"+linea.getIdRelazioneAttivita()+", ID CAMPO"+campoAggiuntivo.getId());
					salvaValoreCampoAggiuntivoValidazione(db,generated,campoAggiuntivo.getId(),idLineaMLDelCampo,idRelStabLPPrimaDiValidazione,idOpuRelStabLPDopoValidaz,stabDopoValid,userId);
				}
				else
				{ /*altrimenti e' stata inserita dall'utente */
					/*TODO IMPLEMENTA */
				}
			}
		}
		}
		else
		{

			/*individuo quella che sto validando */
			for(Object lineaO : linee)
			{
				org.aspcfs.modules.opu.base.LineaProduttiva linea = (org.aspcfs.modules.opu.base.LineaProduttiva)lineaO;
				Integer idRelStabLPPrimaDiValidazione = linea.getId(); //----> QUESTO E' ID_REL_STAB_ID DELLA RICHIESTA (in suap)

				linea.searchCampiEstesiValidazione(db);
				
				ArrayList<LineeMobiliHtmlFields> campiAggiuntiviValidaz = linea.getHtmlFieldsValidazione();
				/*estraggo i campi aggiuntivi per quella linea */
				for(LineeMobiliHtmlFields campoAggiuntivo : campiAggiuntiviValidaz)
				{
					Integer idLineaMLDelCampo = campoAggiuntivo.getId_linea();  //----> QUESTO E' ID DELLA LINEA IN MASTER LIST, 
					/*data la linea richiesta, passo da questa alla nuova che e' stata validata */
					Integer idOpuRelStabLPDopoValidaz = getOpuRelStabLP(stabDopoValid, idLineaMLDelCampo, db); //----> OTTENGO E' ID_REL_STAB_ID DELLA LINEA VALIDATA

					if(campoAggiuntivo.getDbiGenerazione() != null && campoAggiuntivo.getDbiGenerazione().trim().length( )> 0)
					{
						/*allora per questo campo aggiuntivo e' prevista la generazione server side usando una dbi */
						String dbiToCall = campoAggiuntivo.getDbiGenerazione();
						String generated = callDbiGenerazioneCampoEstesoValidazione(dbiToCall,null,db,stabDopoValid);
						System.out.println(">>>>SALVO VALORE CAMPO AGGIUNTIVO DI VALIDAZIONE PER LINEA CON ID (REL STAB ID )"+linea.getId()+", ID ATTIVITA LINEA"+linea.getIdRelazioneAttivita()+", ID CAMPO"+campoAggiuntivo.getId());
						salvaValoreCampoAggiuntivoValidazione(db,generated,campoAggiuntivo.getId(),idLineaMLDelCampo,idRelStabLPPrimaDiValidazione,idOpuRelStabLPDopoValidaz,stabDopoValid,userId);
					}
					else
					{ /*altrimenti e' stata inserita dall'utente */
						/*TODO IMPLEMENTA */
					}
				}
			}
			
		}
	}
	
	
	public void scaricaCampiAggiuntiviPerTutteLeLineeRichiestaValidata(int userId,int stabDopoValid,Stabilimento richiesta, Connection db) throws Exception
	{
		scaricaCampiAggiuntiviPerTutteLeLineeRichiestaValidata(userId,stabDopoValid,richiesta,db,null);
	}
	

    private Integer getRelStabLPDopoValidaz(Integer opuRelStabLPPrimaDiValid,Connection db) throws Exception
    {
    	
    	Integer toRet = null;
    	PreparedStatement pst = null;
    	ResultSet rs = null;
    	try
    	{
    		pst = db.prepareStatement("select id from opu_relazione_stabilimento_linee_produttive where id_suap_rel_stab_lp = ?");
    		pst.setInt(1, opuRelStabLPPrimaDiValid.intValue());
    		rs = pst.executeQuery();
    		rs.next();
    		toRet = rs.getInt(1);
    		return toRet;
    	}
    	catch(Exception ex)
    	{
    		throw ex;
    	}
    	finally
    	{
    		try{pst.close();}catch(Exception ex) {}
    		try{rs.close();}catch(Exception ex) {}
    	}
    	 
    }
    
    
    
    private Integer getOpuRelStabLP(Integer idStabilimento,Integer idLinea, Connection db) throws Exception
    {
    	
    	Integer toRet = null;
    	PreparedStatement pst = null;
    	ResultSet rs = null;
    	try
    	{
    		pst = db.prepareStatement("select id from opu_relazione_stabilimento_linee_produttive where enabled  and id_linea_produttiva = ? and id_stabilimento = ?");
    		pst.setInt(1, idLinea);
    		pst.setInt(2, idStabilimento);
    		rs = pst.executeQuery();
    		rs.next();
    		toRet = rs.getInt(1);
    		return toRet;
    	}
    	catch(Exception ex)
    	{
    		throw ex;
    	}
    	finally
    	{
    		try{pst.close();}catch(Exception ex) {}
    		try{rs.close();}catch(Exception ex) {}
    	}
    }	
    	
    	
	private void salvaValoreCampoAggiuntivoValidazione( 
			Connection db,String valoreCampo,Integer idCampo, Integer idLineaML, Integer idSuapRelStabLP, Integer idOpuRelStabLP, Integer stabIdDopoValid, Integer userId) throws Exception {
		 
		PreparedStatement pst = null;
		try
		{
			pst = db.prepareStatement("insert into linee_mobili_fields_value(id_rel_stab_linea,id_linee_mobili_html_fields,valore_campo,indice,enabled,id_utente_inserimento,data_inserimento,id_opu_rel_stab_linea,riferimento_org_id) "
					+ "values(?,?,?,?,?,?,?,?,?)");
			
			int u = 0;
			 
			pst.setInt(++u,idSuapRelStabLP );
			pst.setInt(++u, idCampo );
			pst.setString(++u, valoreCampo);
			pst.setInt(++u,1 );
			pst.setBoolean(++u, true );
			pst.setInt(++u,  userId );
			pst.setTimestamp(++u, new Timestamp( new Date().getTime() ) );
			pst.setInt(++u, idOpuRelStabLP);
			pst.setInt(++u, stabIdDopoValid );
			 
			
			pst.executeUpdate();
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
		}
		
	}







	private void inviaPecAllegatiSciaVariazioneInserita(Connection db,ActionContext cont, Stabilimento richiesta,RisultatoValidazioneRichiesta risultato)
	{
		//-----GESTIONE DELL'INVIO DELLA MAIL PEC CON LO ZIP ALLEGATO
		Date data = new Date();
		DateFormat form = new SimpleDateFormat("YYYYMMddHHmmss");
		//genero il file xml dal db e scarico gli allegati dal documentale

		File[] tempAllFiles = null;
		File xmlDaDb = null;
		File zipped = null;

		String nomeFileXmlDaGenerare = richiesta.getOperatore().getPartitaIva()+form.format(data)+".xml";

		//File xmlDaDb = generaFileXmlDaDb(cont, db,idRichiestaNuovoOp , idStabilimentoDopoConvergenza,nomeFileXmlDaGenerare);
		File[] allegatiDaDocumentale = null;
		String comune = "" ;

		try 
		{

			//NB: se e' stata fatta validazione per variazione, l'id dello stabilimento da usare per la ricerca sul documentale
			//non e' l'id dello stabilimento trovato dalla ricerca globale ma l'id dello stabilimento fittizio nuovo che e' stato creato

			allegatiDaDocumentale = scaricaFilesDaDocumentale(cont,risultato.getIdStabilimentoTrovato() );
			//xmlDaDb = generaFileXmlDaDbSecondoSchemaXSDPerValidazione( cont,db,idRichiesta, idStabilimentoDopoConvergenza,nomeFileXmlDaGenerare, allegatiDaDocumentale );
			Boolean[] isValidXml = new Boolean[]{false};
			String destPath = getWebInfPath(cont, "tmp_attachment_mail");
			xmlDaDb = new XSDSchemaRestGisaGeneratore().generaFileXmlSecondoSchema(db, destPath ,new int[]{risultato.getIdStabilimento(), richiesta.getOperatore().getIdOperatore()}, nomeFileXmlDaGenerare ,allegatiDaDocumentale, "VALIDAZIONE", isValidXml);





			//li metto tutti assieme in un array di File
			tempAllFiles = new File[allegatiDaDocumentale.length+1];
			for(int i=0;i<allegatiDaDocumentale.length;i++)
			{
				tempAllFiles[i] = allegatiDaDocumentale[i];
			}
			tempAllFiles[allegatiDaDocumentale.length] = xmlDaDb;
			//li zippo, il nome del file sara PIVAYYYYMMDDHHMMSS

			String nomeZipDaGenerare = richiesta.getOperatore().getPartitaIva()+form.format(data)+".zip";
			zipped = zippaFiles(cont,tempAllFiles,nomeZipDaGenerare);

			String tipoOperazionePerOggetto = null;
			switch(richiesta.getOperatore().getIdOperazione())
			{
			case OP_AMPLIAMENTO: tipoOperazionePerOggetto = "AMPLIAMENTO STABILIMENTO"; break;
			case OP_CESSAZIONE: tipoOperazionePerOggetto = "CESSAZIONE STABILIMENTO";break;
			case OP_VARIAZIONE: tipoOperazionePerOggetto = "VARIAZIONE STABILIMENTO";break;
			case OP_SOSPENSIONE: tipoOperazionePerOggetto = "SOSPENSIONE STABILIMENTO";break;
			case OP_MODIFICA_STATO_LUOGHI: tipoOperazionePerOggetto ="MODIFICA STATO DEI LUOGHI"; break;
			}


			comune = getComuneRichiedente(db,richiesta.getOperatore().getIdOperazione());
			//R.M 16/03-> in accordo con Dante l'invio PEC viene commentato.
			//inviaMailPec(cont,db,richiesta.getOperatore().getIdOperatore(),richiesta.getOperatore().getIdOperazione(),zipped, tipoOperazionePerOggetto,comune);


		} 
		catch(EccezioneGenerazioneXml e)
		{
			e.printStackTrace();
			cont.getRequest().setAttribute("GenericError", "Attenzione :L'operazione si e' conclusa ma Si e verificato un errore nella generazione dell'xml");
			logger.error("Errore in generaFileXmlDaDb "+e.getMessage());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			cont.getRequest().setAttribute("GenericError", "Attenzione :L'operazione si e' conclusa ma Si e verificato un errore nell'invio della mail");
			logger.error("Errore in invio mail "+e.getMessage());
		}
		try {zipped.delete();}catch(Exception ex){}
		try
		{
			for(File el : tempAllFiles)
			{

				el.delete();
			}
		} 
		catch(Exception ex){}
	}

	private void inviaPecAllegatiNuovaSciaInserita(Connection db,ActionContext cont, Stabilimento richiesta,Integer[] esitoDbi)
	{

		File xmlDaDb = null;
		File[] tempAllFiles = null;
		File[] allegatiDaDocumentale = null;

		//-----GESTIONE DELL'INVIO DELLA MAIL PEC CON LO ZIP ALLEGATO
		Date data = new Date();
		DateFormat form = new SimpleDateFormat("YYYYMMddHHmmss");
		//genero il file xml dal db e scarico gli allegati dal documentale
		String nomeFileXmlDaGenerare = richiesta.getOperatore().getPartitaIva()+form.format(data)+".xml";
		int idStabilimentoDopoConvergenza = esitoDbi[2];



		File zipped=null;

		try
		{

			String comune = getComuneRichiedente(db,richiesta.getOperatore().getIdOperatore()) ;

			//NB: se e' stata fatta validazione per variazione, l'id dello stabilimento da usare per la ricerca sul documentale
			//non e' l'id dello stabilimento trovato dalla ricerca globale ma l'id dello stabilimento fittizio nuovo che e' stato creato
			//******NB: NON PUO' ACCADERE PERCHE' QUI QUESTO METODO E' ESCLUSIVO PER NUOVO STAB***

			allegatiDaDocumentale = scaricaFilesDaDocumentale(cont,idStabilimentoDopoConvergenza/*idsStabilimentiTrovati.get(0)*/);

			//xmlDaDb = generaFileXmlDaDb(cont,db,idStabilimentoDopoConvergenza,nomeFileXmlDaGenerare,idRichiestaNuovoOp);
			//xmlDaDb = generaFileXmlDaDbSecondoSchemaXSDPerValidazione( cont,db,idRichiestaNuovoOp, idStabilimentoDopoConvergenza,nomeFileXmlDaGenerare, allegatiDaDocumentale );
			Boolean[] isValidXml = new Boolean[]{false};

			String destPath = getWebInfPath(cont, "tmp_attachment_mail");
			xmlDaDb = new XSDSchemaRestGisaGeneratore().generaFileXmlSecondoSchema(db,destPath, new int[]{idStabilimentoDopoConvergenza, richiesta.getOperatore().getIdOperatore()}, nomeFileXmlDaGenerare ,allegatiDaDocumentale, "VALIDAZIONE", isValidXml);
			//li metto tutti assieme in un array di File
			tempAllFiles = new File[allegatiDaDocumentale.length+1];
			for(int i=0;i<allegatiDaDocumentale.length;i++)
			{
				tempAllFiles[i] = allegatiDaDocumentale[i];
			}
			tempAllFiles[allegatiDaDocumentale.length] = xmlDaDb;
			String nomeZipDaGenerare = richiesta.getOperatore().getPartitaIva()+form.format(data)+".zip";
			zipped = zippaFiles(cont,tempAllFiles,nomeZipDaGenerare);

			//R.M 16/03-> in accordo con Dante l'invio PEC viene commentato.
			//inviaMailPec(cont,db,richiesta.getOperatore().getIdOperatore(),richiesta.getOperatore().getIdOperazione(),zipped,"INSERIMENTO NUOVO STABILIMENTO",comune);
		}
		catch(EccezioneGenerazioneXml e)
		{
			
			cont.getRequest().setAttribute("GenericError", "Attenzione :L'operazione si e' conclusa ma Si e verificato un errore nella generazione dell'xml");
			// TODO Auto-generated catch block
			logger.error("Errore in generaFileXmlDaDb "+e.getMessage());
		}
		catch (Exception e) {
			cont.getRequest().setAttribute("GenericError", "Attenzione :L'operazione si e' conclusa ma Si e verificato un errore nell'invio della mail");
			// TODO Auto-generated catch block
			logger.error("Errore in invio mail "+e.getMessage());
		}
		finally
		{
			this.freeConnection(cont, db);
			try
			{
				for(File el : tempAllFiles )
					el.delete();
			}
			catch(Exception ex){}

		}
	}


	private Integer[] invocaValidazioneVariazioneGlobale(ActionContext cont, Connection db, int idRichiesta,
			int idStabilimento) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			Integer[] esitoDbi = new Integer[3];

			int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));
			String noteValidazione = cont.getParameter("noteValidazione");

			int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User")).getUserId();
			String query = "select * from public_functions.suap_validazione_scia_variazione_titolarita(?,?,?,?,?)";
			pst = db.prepareStatement(query);
			pst.setInt(1,idRichiesta);
			pst.setInt(2,idStabilimento);
			pst.setInt(3, userId);
			pst.setInt(4,idStatoValidazione);
			pst.setString(5, noteValidazione);
			
			rs = pst.executeQuery();
			rs.next();
			esitoDbi[0] = rs.getInt("id_esito");
			esitoDbi[1] = rs.getInt("id_stabilimento_opu");
			
			RicercheAnagraficheTab.inserOpu(db, esitoDbi[1] );

			return esitoDbi;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}


	}


	private Integer[] invocaValidazioneModificaStatoLuoghiGlobale
	(ActionContext cont, Connection db, int idRichiesta, int altId,
			int idStabilimento) throws SQLException {


		PreparedStatement pst = null,pst2 = null,pst3 = null;
		ResultSet rs = null;
		Integer[] esitoDbi = new Integer[3];

		try
		{
			if(this.convergiDocumenti(altId, idStabilimento, cont))
			{
				
				
				//aggiungo allo storico
				pst3 = db.prepareStatement("insert into suap_storico_richieste (id_stabilimento,id_suap_ric_scia_operatore,data_operazione,enabled,id_utente)"
						+"values(?,?,current_timestamp,true,?); update suap_ric_scia_operatore set validato = true where id=?;" );
				pst3.setInt(1, idStabilimento);
				pst3.setInt(2, idRichiesta);
				int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User")).getUserId();
				pst3.setInt(3,userId);
				pst3.setInt(4,idRichiesta);
				pst3.executeUpdate();
				
				esitoDbi[0] = CodiciBackendValidazione.CODICE_VALIDAZIONE_LINEA_OK_TOTALE;
				
			}
			else
			{
				esitoDbi[0] = 1;
			}
		}
		catch(Exception ex)
		{
			esitoDbi[0] = 1;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(pst2 != null) try{pst2.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}
		
		
		return esitoDbi;
		

	}


	private Integer[] invocaValidazioneSospensioneGlobaleScia(ActionContext cont, Connection db, int idRichiesta,
			int idStabilimento) throws SQLException {


		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			Integer[] esitoDbi = new Integer[3];
			int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));
			String noteValidazione = cont.getParameter("noteValidazione");

			int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User")).getUserId();
			String query = "select * from public_functions.suap_sospensione_stabilimento(?,?,-1,?,?,?)";
			pst = db.prepareStatement(query);
			pst.setInt(1,idRichiesta);
			pst.setInt(2,idStabilimento);
			pst.setInt(3, userId);
			pst.setInt(4,idStatoValidazione);
			pst.setString(5, noteValidazione);
			rs = pst.executeQuery();
			rs.next();
			esitoDbi[0] = rs.getInt("id_esito");

			return esitoDbi;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}

	}

	private Integer[] invocaValidazioneCessazioneGlobaleScia(ActionContext cont, Connection db, int idRichiesta,
			int idStabilimento) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			Integer[] esitoDbi = new Integer[3];
			int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));
			String noteValidazione = cont.getParameter("noteValidazione");

			int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User")).getUserId();
			String query = "select * from public_functions.suap_cessazione_stabilimento(?,?,-1,?,?,?)";
			pst = db.prepareStatement(query);
			pst.setInt(1,idRichiesta);
			pst.setInt(2,idStabilimento);
			pst.setInt(3, userId);
			pst.setInt(4,idStatoValidazione);
			pst.setString(5, noteValidazione);
			rs = pst.executeQuery();
			rs.next();
			esitoDbi[0] = rs.getInt("id_esito");

			return esitoDbi;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}
	}




	private Integer[] invocaValidazioneCessazionePuntualeScia(ActionContext cont, Connection db, int idRichiesta, int idLineaProd,
			int idStabilimento) throws SQLException 
	{

		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			Integer[] esitoDbi = new Integer[3];
			int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));
			String noteValidazione = cont.getParameter("noteValidazione");

			

			int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User")).getUserId();
			String query = "select * from public_functions.suap_validazione_scia_cessazione(?,?,?,?,?)";
			pst = db.prepareStatement(query);
			pst.setInt(1,idLineaProd);
			pst.setInt(2,idStabilimento);
			pst.setInt(3, userId);
			pst.setInt(4,idStatoValidazione);
			pst.setString(5, noteValidazione);
			
			rs = pst.executeQuery();
			rs.next();
			esitoDbi[0] = rs.getInt("id_esito");

			return esitoDbi;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}

	}

	private Integer[] invocaValidazioneSospensionePuntualeScia(ActionContext cont, Connection db, int idRichiesta, int idLineaProd,
			int idStabilimento) throws SQLException 
	{

		PreparedStatement pst = null;
		ResultSet rs = null;
		try{

			Integer[] esitoDbi = new Integer[3];
			
			
			int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));
			String noteValidazione = cont.getParameter("noteValidazione");

			

			int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User")).getUserId();
			String query = "select * from public_functions.suap_validazione_scia_sospensione(?,?,?,?,?)";
			pst = db.prepareStatement(query);
			pst.setInt(1,idLineaProd);
			pst.setInt(2,idStabilimento);
			pst.setInt(3, userId);
			pst.setInt(4,idStatoValidazione);
			pst.setString(5, noteValidazione);
			rs = pst.executeQuery();
			rs.next();
			esitoDbi[0] = rs.getInt("id_esito");

			return esitoDbi;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}

	}





	public Integer[] invocaValidazioneAmpliamentoScia(ActionContext cont, Connection db,int idRichiestaNuovoOp,int idNuovaLinea,int idStab) throws SQLException
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			Integer[] esitoDbi = new Integer[3];

			
			int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User")).getUserId();
			
			
			int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));
			String noteValidazione = cont.getParameter("noteValidazione");

			String query = "select * from public_functions.suap_validazione_scia_ampliamento(?,?,?,?,?)";
			pst = db.prepareStatement(query);
			pst.setInt(1,idNuovaLinea);
			pst.setInt(2,idStab);
			pst.setInt(3, userId);
			
			pst.setInt(4,idStatoValidazione);
			pst.setString(5, noteValidazione);
			
			rs = pst.executeQuery();
			rs.next();
			esitoDbi[0] = rs.getInt("id_esito");

			return esitoDbi;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}

	}


	
	
	
	
	
	
	
	public Integer[] invocaDbiInserimentoLineaApicoltura(ActionContext cont, Connection db,int idRichiestaNuovoOp,int idNuovaLinea) throws SQLException
	{

		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			Integer[] esitoDbi = new Integer[3];

			int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User")).getUserId();

			int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));
			String noteValidazione = cont.getParameter("noteValidazione");
			
			String query = "select * from public_functions.suap_validazione_scia_nuovo_stabilimento_apicoltura(?,?,?,?)";
			pst = db.prepareStatement(query);
			
			pst.setInt(1,idNuovaLinea);
			pst.setInt(2, userId);
			pst.setInt(3,idStatoValidazione);
			pst.setString(4, noteValidazione);
			rs = pst.executeQuery();

			rs.next();
			esitoDbi[0] = rs.getInt("id_esito");
			esitoDbi[1] = rs.getInt("id_impresa_opu");
			esitoDbi[2] = rs.getInt("id_stabilimento_opu");

			return esitoDbi;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}

	}


	public Integer[] invocaDbiInserimentoLinea(ActionContext cont, Connection db,int idRichiestaNuovoOp,int idNuovaLinea) throws SQLException
	{

		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			Integer[] esitoDbi = new Integer[3];

			int userId = ((UserBean) cont.getRequest().getSession().getAttribute("User")).getUserId();

			int idStatoValidazione = Integer.parseInt(cont.getParameter("statoValidazione"));
			String noteValidazione = cont.getParameter("noteValidazione");

			String query = "select * from public_functions.suap_validazione_scia_nuovo_stabilimento(?,?,?,?)";
			pst = db.prepareStatement(query);
			pst.setInt(1,idNuovaLinea);
			pst.setInt(2, userId);
			pst.setInt(3,idStatoValidazione);
			pst.setString(4, noteValidazione);
			rs = pst.executeQuery();
			
			rs.next();
			esitoDbi[0] = rs.getInt("id_esito");
			esitoDbi[1] = rs.getInt("id_impresa_opu");
			esitoDbi[2] = rs.getInt("id_stabilimento_opu");

			return esitoDbi;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}

	}


	public Integer[] invocaDbiRicercaGlobalerPerAmpliamentoCessazioneVariazione(ActionContext cont, Connection db,int idRichiesta,HashMap<Integer,Stabilimento> operatoriTrovati) throws SQLException
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			Integer[] esitoDbi = new Integer[3];


			String query = "select * from public_functions.suap_ricerca_globale_anagrafica(?)";
			pst = db.prepareStatement(query);
			pst.setInt(1,idRichiesta);
			rs = pst.executeQuery();




			rs.next();
			Integer esito = rs.getInt("id_esito"); //questo e' l'id dell'esito

			esitoDbi[0] = esito;
			switch(esito)
			{
			case 1: //trovato unico in opu
				esitoDbi[0] = 1;
				esitoDbi[1] = rs.getInt("id_opu_impresa");
				esitoDbi[2] = rs.getInt("id_opu_stabilimento");
				break;
			case 8: //piu' di uno in opu
				esitoDbi[0] = 8;
				//devo scorrere la lista per ottenere tutti i candidati da mandare al client
				Stabilimento tStab = null;



				do 
				{
					tStab = new Stabilimento();
					tStab.setIdStabilimento(rs.getInt("id_opu_stabilimento"));
					tStab.setRagioneSociale(rs.getString("ragione_sociale"));
					tStab.setPartitaIva(rs.getString("partita_iva"));
					tStab.setCfRappresentante(rs.getString("cf_rappresentante"));
					tStab.setNumeroRegistrazione(rs.getString("numero_registrazione"));
					tStab.setComuneSedeLegale(rs.getString("comune_sede_legale"));
					tStab.setIndirizzoSedeLegale(rs.getString("indirizzo_sede_legale"));
					tStab.setComuneSedeOperativa(rs.getString("comune_sede_operativa"));
					tStab.setIndirizzoSedeOperativa(rs.getString("indirizzo_sede_operativa"));

					operatoriTrovati.put(tStab.getIdStabilimento(), tStab);

				} while (rs.next());

				break;
			case 9: //uno solo in org
				esitoDbi[1] = rs.getInt("org_id");
				esitoDbi[0] = 9;
				break;
			case 10: //piu di uno in org
				esitoDbi[0] = 10;
				//devo scorrere la lista per ottenere tutti i candidati


				do 
				{
					tStab = new Stabilimento();
					tStab.setIdStabilimento(rs.getInt("org_id"));
					tStab.setRagioneSociale(rs.getString("ragione_sociale"));
					tStab.setPartitaIva(rs.getString("partita_iva"));
					tStab.setCfRappresentante(rs.getString("cf_rappresentante"));
					tStab.setNumeroRegistrazione(rs.getString("numero_registrazione"));
					tStab.setComuneSedeLegale(rs.getString("comune_sede_legale"));
					tStab.setIndirizzoSedeLegale(rs.getString("indirizzo_sede_legale"));
					tStab.setComuneSedeOperativa(rs.getString("comune_sede_operativa"));
					tStab.setIndirizzoSedeOperativa(rs.getString("indirizzo_sede_operativa"));

					operatoriTrovati.put(tStab.getIdStabilimento(), tStab);

				} while (rs.next());



				//TODO
				break;
			case 11: //non trovata proprio
				esitoDbi[0] = 11;
				break;
			}

			return esitoDbi;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) try{pst.close();} catch(Exception ex){}
			if(rs != null) try{rs.close();} catch(Exception ex){}
		}

	}


	public File zippaFiles(ActionContext cont,File[] filesToZip,String nomeZip) throws Exception
	{
		String tmpFolderPath = getWebInfPath(cont,"tmp_attachment_pecmail");
		File fileZip = new File(tmpFolderPath+nomeZip);
		InputStream iS = null;

		ZipOutputStream zOs = new ZipOutputStream(new FileOutputStream(fileZip));
		try
		{
			for(File fileToAdd : filesToZip)
			{
				ZipEntry zipEntry = new ZipEntry(fileToAdd.getName());
				zOs.putNextEntry(zipEntry);
				iS = new FileInputStream(fileToAdd);
				int t = -1;
				while((t = iS.read()) != -1)
				{
					zOs.write(t);
				}
				try{zOs.closeEntry();} catch(Exception ex){}
				try{iS.close();} catch(Exception ex){}
				 

			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(zOs != null)
				zOs.close();
			if(iS != null)
				iS.close();

		}
		return fileZip;
	}

	public File[] scaricaFilesDaDocumentale(ActionContext cont,int idStab) throws IOException { //ritorna file nella cartella temporanea che sono stati scaricati per quello stabilimento da gisa
		URL urlServizioListaCodici = null;
		HttpURLConnection conn = null;
		BufferedReader in = null;
		OutputStreamWriter wr = null;
		URL urlSpecificoDocumento = null;
		InputStream iS = null;
		OutputStream oS = null;
		String path_doc = null;
		ArrayList<File> alFileScaricati = new ArrayList<File>();

		//recupero l'id timbro
		//		String codDocumento 		=  null;
		//		codDocumento = context.getRequest().getParameter("codDocumento");
		//		if (codDocumento==null)
		//			codDocumento = (String)context.getRequest().getAttribute("codDocumento");
		//		String idDocumento 				= null;
		//		idDocumento = context.getRequest().getParameter("idDocumento");
		//
		//		String titolo="";
		//		String provenienza = ApplicationProperties.getProperty("APP_NAME_GISA");
		String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI");
		String download_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE");

		try
		{
			//			path_doc = "C:/Users/davide/Desktop/temp_fold/";
			path_doc = getWebInfPath(cont,"tmp_attachment_pecmail");
			File theDir = new File(path_doc);
			theDir.mkdirs();


			urlServizioListaCodici = new URL(lista_url);
			conn = (HttpURLConnection) urlServizioListaCodici.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("stabId");
			requestParams.append("="+idStab);
			requestParams.append("&app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));

			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();

			in = new BufferedReader( new InputStreamReader(conn.getInputStream()));

			StringBuffer rcv = new StringBuffer();
			if (in != null) 
			{
				rcv.append(in.readLine()); 
			}

			JSONArray jo = new JSONArray(rcv.toString());

			DocumentaleAllegatoList docList = new DocumentaleAllegatoList();
			docList.creaElenco(jo);

			for(Object infoDocumentoDaRichiedere : docList)
			{
				String codiceDocu = ((DocumentaleAllegato)infoDocumentoDaRichiedere).getIdHeader();
				//uso il codice per costruire l'url al quale mandare la richiesta per scaricare file
				String url_specificoDocumento = download_url+"?codDocumento="+codiceDocu;
				urlSpecificoDocumento = new URL(url_specificoDocumento);
				iS= urlSpecificoDocumento.openStream();
				String nomeOggetto = ((DocumentaleAllegato)infoDocumentoDaRichiedere).getOggetto() != null ? ((DocumentaleAllegato)infoDocumentoDaRichiedere).getOggetto() : "file_allegato";
				String titoloFileTemp = nomeOggetto+"_"+codiceDocu+"."+((DocumentaleAllegato)infoDocumentoDaRichiedere).getEstensione();
				File f = new File(path_doc+titoloFileTemp);
				oS = new FileOutputStream(f);
				int t = -1;
				while( (t = iS.read()) != -1)
				{
					oS.write(t);

				}
				oS.close();
				iS.close();
				alFileScaricati.add(f);

			}

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(in!= null)
				in.close();
			if(iS != null)
				iS.close();
			if(oS != null)
				oS.close();
		}


		return alFileScaricati.toArray(new File[alFileScaricati.size()]);

	}



	public File generaFileXmlDaDb(ActionContext cont,Connection db,int idStabilimento,String nomeFileXml,int idRichiesta) throws Exception
	{
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		ResultSet rs3 = null;
		String query = null;

		try
		{
			String tmpFolderPath = getWebInfPath(cont,"tmp_attachment_pecmail");
			//			String tmpFolderPath = "C:/Users/davide/Desktop/temp_fold/";
			File tmpFold = new File(tmpFolderPath);
			tmpFold.mkdir();

			//estraggo la stringa xml che rappresenta il file


			//in realta' non c'e' bisogno di questo split nei 3 casi 
			//poiche' e' la dbi che se ne occupa

			String queryAnagrafica = "select * from suap_query_anagrafica(?,?)";
			pst4 = db.prepareStatement(queryAnagrafica);
			pst4.setInt(1, idStabilimento);
			pst4.setInt(2, idRichiesta);

			query = "select query_to_xml('"+pst4.toString()+"',true,false,'')";  


			//String query = "select query_to_xml('select tipo_impresa, tipo_societa, ragione_sociale, partita_iva, codice_fiscale_impresa, nome_rapp_sede_legale, cognome_rapp_sede_legale, cf_rapp_sede_legale, indirizzo_rapp_sede_legale, indirizzo_sede_legale, cap_sede_legale, comune_sede_legale, prov_sede_legale,opu_operatori_denormalizzati_view.numero_registrazione, indirizzo_stab, cap_stab, comune_stab, latitudine, longitudine, stab_descrizione_attivita, stab_descrizione_carattere, opu_operatori_denormalizzati_view.data_generazione_numero, opu_operatori_denormalizzati_view.data_inizio_attivita, opu_operatori_denormalizzati_view.data_fine_attivita, stab_descrizione_carattere, stab_descrizione_carattere, opu_operatori_denormalizzati_view.data_generazione_numero, linea_numero_registrazione from opu_operatori_denormalizzati_view left join opu_stabilimento os on opu_operatori_denormalizzati_view.id_stabilimento=os.id left join opu_indirizzo oi on os.id_indirizzo=oi.id left join opu_stabilimento_mobile m on m.id_stabilimento = os.id left join lookup_tipo_mobili t on m.tipo = t.code where opu_operatori_denormalizzati_view.id_stabilimento ="+idStabilimento+"',true,false,'')";



			//			String query = "select query_to_xml('select distinct "+
			//							"tipo_impresa, tipo_societa, ragione_sociale, partita_iva, codice_fiscale_impresa, nome_rapp_sede_legale, cognome_rapp_sede_legale, cf_rapp_sede_legale, , tipo_impresa,tipo_societa, "+
			//							"s.id_opu_operatore as id_richiesta, s.ragione_sociale, s.partita_iva, s.codice_fiscale_impresa,s.comune_sede_legale, s.istat_legale, s.cap_sede_legale, "+
			//							"s.prov_sede_legale,s.domicilio_digitale, "+
			//							"s.cf_rapp_sede_legale, s.nome_rapp_sede_legale, s.cognome_rapp_sede_legale, s.indirizzo_rapp_sede_legale,"+
			//							"s.comune_stab, s.indirizzo_stab, s.cap_stab, s.prov_stab, "+
			//							"lta.description as tipo_attivita ,ltc.description as carattere, s.data_inizio_attivita,s.data_fine_attivita,macroarea,aggregazione,linea_attivita "+
			//							"from  suap_ric_scia_operatori_denormalizzati_view s "+
			//							"join opu_lookup_tipologia_attivita lta on lta.code = s.stab_id_attivita "+
			//							"join opu_lookup_tipologia_carattere ltc on ltc.code = s.stab_id_carattere "+
			//							"join suap_lookup_tipo_richiesta op on op.code ="+tipoRichiesta+
			//							" where id_opu_operatore="+idRichiestaNuovoOp+"',true,false,'')"; 



			//			pst3.setInt(1,idRichiestaNuovoOp);
			pst3 = db.prepareStatement(query);
			rs3 = pst3.executeQuery();
			rs3.next();
			File xmlFile = new File(tmpFold.getAbsolutePath()+"/"+nomeFileXml);

			FileOutputStream os = new FileOutputStream(xmlFile);
			os.write(rs3.getString(1).getBytes()); //scrivo il contenuto estratto dal db sul file
			os.close();
			return xmlFile;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex; //poiche' la gestiamo fuori
		}
	}


	public void inviaMailPec(ActionContext cont,Connection db,int idRichiestaNuovoOp,int tipoRichiesta,File fileAllegato, String tipoOperazione,String comuneRichiedente) throws MessagingException, SQLException 
	{



		//configuro sender di mail usando proprieta salvate a sistema

		HashMap<String,String> configs = new HashMap<String,String>();
		configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
		configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
		configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
		configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
		configs.put("mail.smtp.ssl.enable",ApplicationProperties.getProperty("mail.smtp.ssl.enable"));
		configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
		configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		configs.put("mail.smtp.socketFactory.fallback", "false");
		
		PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
		//	creo cartella temporanea per salvare file xml il cui contenuto e' estratto dal db	
		try {
			String destinatario = "";
			String sql = "select * from suap_get_pec_comune_richiestente(?)";
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, comuneRichiedente);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				if (rs.getString(1)!=null && !rs.getString(1).equals("")){
					if (ApplicationProperties.getProperty("ambiente").equalsIgnoreCase("ufficiale"))
						destinatario = "gisadev@u-s.it"; //destinatario = rs.getString(1);
					else
						destinatario = "gisadev@u-s.it";	
				}
			}

			if (destinatario == null || destinatario.equals("")) {
				System.out.println("DESTINATARIO NULL ! MAIL NON INVIATA");
				throw new AddressException();
			}
			sender.sendMail("Validazione richiesta per " + tipoOperazione, "Inserimento richiesta operatore "
					+ idRichiestaNuovoOp + " effettuata con successo. " + "Nuovo operatore inserito",
					"gisasuap@cert.izsmportici.it", destinatario, fileAllegato);
		} catch (AddressException e) {

			logger.error("MAIL NON INVIATA IN FASE DI VALIDAZIONE : " + e.getMessage());
			throw e;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			logger.error("MAIL NON INVIATA IN FASE DI VALIDAZIONE : " + e.getMessage());
			throw e;
		}	




	}

	private String getComuneRichiedente(Connection db,int idRichiesta) throws SQLException
	{
		String comuneRichiedente ="";
		String sql = "select comune_richiesta from suap_ric_scia_operatori_denormalizzati_view where id_opu_operatore=?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idRichiesta);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			comuneRichiedente=rs.getString(1);
		return comuneRichiedente;
	}




	private boolean checkFirmaObbligatoria(Connection db, String istat){

		boolean firmaObbligatoria = false;
		String sql = "select * from suap_check_firma_obbligatoria_by_istat(?)";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);

			pst.setString(1, istat);
			ResultSet rs= pst.executeQuery();
			if (rs.next())
			{	
				firmaObbligatoria = rs.getBoolean(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return firmaObbligatoria;
	}

	private String getIstatComune(Connection db, String comune){

		String istat = "";
		String sql = "select istat from comuni1 where nome ilike ?";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);

			pst.setString(1, comune);
			ResultSet rs= pst.executeQuery();
			if (rs.next())
			{	
				istat = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return istat;
	}
	
	private String callDbiGenerazioneCampoEstesoValidazione(String dbi, Integer idRichiesta, Connection db,Integer idStabilimento) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			String query = "select * from "+dbi;
			/*TODO AGGIUNGERE EVENTUALI ALTRI CAMPI */
			query = query.replace(":idRichiesta:", idRichiesta + "");
			query = query.replace(":idStabilimento:", idStabilimento + "");
			pst =  db.prepareStatement(query);
			rs = pst.executeQuery();
			rs.next();
			String generated = rs.getString(1);
			System.out.println(generated);
			return generated;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{rs.close();} catch(Exception ex){}
			try{pst.close();} catch(Exception ex){}
		}
	}
	
	
	
	
}
