/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.actions;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspcf.modules.report.util.PrintModulesHTML;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.campioni.actions.Campioni;
import org.aspcfs.modules.campioni.base.Analita;
import org.aspcfs.modules.campioni.base.Ticket;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumentoList;
import org.aspcfs.checklist.base.Audit;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.actions.StabilimentoAction;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.richiesteerratacorrige.actions.GestioneRichiesteErrataCorrige;
import org.aspcfs.modules.schedeCentralizzate.base.SchedaCentralizzata;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneDocumenti extends CFSModule {

//	private String encoded = "";
//	private String tabellaName=ApplicationProperties.getProperty("DOCUMENTALE_GISA_NT_TABELLA_NAME");
//	private int idDocumento=-1; //creazione documenti timbrati, id dell'entry nel db timbro_storage
	//private String driverName = "org.postgresql.Driver"; //dati per connessione a DB
	//String databaseURL = ApplicationPrefs.getPref(context.getServletContext(), "GATEKEEPER.URL");
	//private String databaseURL = "jdbc:postgresql://"+ApplicationProperties.getProperty("GESTOREGLIFODBSERVER")+":"+ApplicationProperties.getProperty("GESTOREGLIFODBPORT")+"/"+ApplicationProperties.getProperty("GESTOREGLIFODBNAME");
	//private String DB_USER= ApplicationProperties.getProperty("DOCUMENTALEDBUSER");
	//private String DB_PASSWORD=ApplicationProperties.getProperty("DOCUMENTALEDBPWD");
		
	public GestioneDocumenti(){
		
	}
	
	public String executeCommandGeneraPDF(ActionContext context) throws SQLException, IOException{
		
		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			return "documentaleError";
		}
		
		String test = ""; //conterra' l'html recuperato
		String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
		String orgId = context.getRequest().getParameter("orgId"); 
		String stabId = context.getRequest().getParameter("stabId"); 
		String urlId =  context.getRequest().getParameter("url");
		String idCU = context.getRequest().getParameter("idCU");
		String altId =  context.getRequest().getParameter("altId");
		String ticketId = context.getRequest().getParameter("ticketId"); 
		String htmlcode = (String)context.getSession().getAttribute("htmlcode"); 
			context.getSession().removeAttribute("htmlcode");
		String glifo =  context.getRequest().getParameter("glifo");
		String actionName = "PrintModulesHTML.do"; //nome action da cchiamare
		String file = context.getRequest().getParameter("file");
		String extra =  context.getRequest().getParameter("extra"); //PARAMETRO AGGIUNTIVO PER VALORI NECESSARI SOLO IN ALCUNI DOCUMENTI (es. anno del registro trasgressori)
		String urlOriginale = "";
		
		//Apparo per OPU
		if (urlId!=null && urlId.endsWith("Opu")){
			if (stabId==null)
				stabId=orgId;
				orgId = null;
		}
		//Apparo per SINTESIS
		else if (urlId!=null && urlId.contains("Sintesis")){
			if (altId==null)
				altId=orgId;
				orgId = null;
		}
		//Apparo per NUOVA ANAGRAFICA
				else if (urlId!=null && urlId.contains("Operatoriprivati")){
					if (altId==null)
						altId=orgId;
						orgId = null;
				}
		
		String generazionePulita =  context.getRequest().getParameter("generazionePulita");
		boolean statico = false;
		String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
		
		System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
		
		//inizializzo la variabile glifo nel caso sia null
		//glifo valorizzata: aggiungi timbro
		if (glifo==null || glifo.equals("")){
			glifo="";
		}
		if (orgId==null){
			orgId="-1";
		}
		if (altId==null){
			altId="-1";
		}
		if (stabId==null){
			stabId="-1";
		}
		if (ticketId==null){
			ticketId="-1";
		}if (idCU==null){
			idCU="-1";
		}
		
		if (htmlcode!=null)
			test = htmlcode; //test contiene il sorgente html della pagina
		
		if (file!=null){
			URL oracle = null;
			
			String id = context.getRequest().getParameter("id"); 
			if (id!=null && !id.equals("null"))
				orgId = id;
			
			if (id==null || id.equals("null"))
				id=stabId;
			
			String add1 = context.getRequest().getParameter("address1");
			String add2 = context.getRequest().getParameter("address2");
			String add3 = context.getRequest().getParameter("address3");
			String idSessione = context.getRequest().getSession().getId();
			
			String idAslCorrente ="";
			String idTipoDownload ="";
			if (tipo.equals("AllegatoF")){
				ticketId = id;
				orgId = "-1";
				idAslCorrente = add1;
				idTipoDownload=add2;
				context.getRequest().setAttribute("asl_regione_corrente", idAslCorrente);
				context.getRequest().setAttribute("tipo_download", idTipoDownload);
			}
					
			String url          	= urlChiamante;
			String metodo="";
			
			if (tipo.equals("SchedaImpresa") || tipo.equals("ComunicazioneNumeroRegistrazione") || tipo.equals("SchedaCessazione") || tipo.equals("SchedaStabilimento") || tipo.equals("SchedaSOA") || tipo.equals("SchedaImbarcazioni")  || tipo.equals("SchedaOSM") || tipo.equals("SchedaMolluschi") || tipo.equals("SchedaCanili") ||tipo.equals("SchedaColonie") || tipo.equals("SchedaOperatoriCommerciali") || tipo.equals("SchedaOperatori193") || tipo.equals("SchedaFarmacie") || tipo.equals("SchedaAziendeAgricole") || tipo.equals("SchedaOperatoriSperimentazioneAnimale") || tipo.equals("SchedaStruttureRiproduzioneAnimale") || tipo.equals("SchedaOperatoriNonAltrove") || tipo.equals("SchedaPrivati")){
				actionName="SchedaPrint.do";
				metodo="StampaScheda";
				}
			else if (tipo.equals("AllegatoTrasporti")){
				actionName="TrasportoAnimali.do";
				metodo="StampaAllegato";
				}
			else if (tipo.equals("SchedaAllevamenti")){
				actionName="Allevamenti.do";
				metodo="StampaSchedaAllevamento";
				}
			else if (tipo.equals("5")){
				actionName="PrintReportVigilanza.do";
				metodo="ViewSchedaIspezione";
				}
			else if (tipo.equals("AllegatoF")){
				actionName="TroubleTicketsAllerte.do";
				metodo="StampaAllegatoF";
				}
			else if (tipo.equals("VariazioneCensimento")){
				actionName="ApicolturaApiari.do";
				metodo="StampaCensimento";
				}
			else if (tipo.equals("ApicolturaAllegatoC")){
				actionName="ApicolturaApiari.do";
				metodo="StampaAllegatoC";
				}
			else if (tipo.equals("ApicolturaMovimentazione")){
				actionName="ApicolturaApiari.do";
				metodo="StampaMovimentazione";
				}
			if (actionName.equals("SchedaPrint.do"))
				url+=actionName+
						"?command="+ metodo + "&id=" + id + "&addressid=" + add1 + "&addressid2=" + add2 + "&addressid3=" + add3 + "&file=" + file; //PAGINA DA LEGGERE E CODIFICARE
			else if(actionName.equals("TrasportoAnimali.do"))
				url+=actionName+
				"?command="+ metodo + "&id=" + id + "&file=" + file;
			else if(actionName.equals("Allevamenti.do"))
				url+=actionName+
				"?command="+ metodo + "&id=" + id;
			else if(actionName.equals("TroubleTicketsAllerte.do"))
				url+=actionName+
				"?command="+ metodo + "&ticketid=" + id+ "&tipo_download=" + idTipoDownload+ "&asl_regione_corrente=" + idAslCorrente;
			else if(actionName.equals("PrintReportVigilanza.do"))
				url+=actionName+
				"?command="+ metodo + "&idControllo=" + idCU+ "&bozza=false" +"&url=" + urlId+ "&stabId=" + orgId+"&print=print";
			else if(actionName.equals("ApicolturaApiari.do"))
				url+=actionName+
				"?command="+ metodo + "&id=" + id;
			
			//PAGINA DA LEGGERE E CODIFICARE
			urlOriginale = url;
			}
		
		if (tipo.equals("RegistroTrasgressori")){
			
			String anno = context.getRequest().getParameter("anno"); 
			extra = anno;
			String idSessione = context.getRequest().getSession().getId();
			String url          	= urlChiamante;
			String metodo="";
			
			if (tipo.equals("RegistroTrasgressori")){
				actionName="RegistroTrasgressori.do";
				metodo="RegistroSanzioniDettaglio";
				}
			
			if (actionName.equals("RegistroTrasgressori.do"))
				url+=actionName+
						"?command="+ metodo + "&anno=" + anno+"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
			//PAGINA DA LEGGERE E CODIFICARE
			urlOriginale = url;
		
		}

		if (test!=null){
			test = fixHtml2(test);
			test = test.replaceAll("style=", "class=\"tableClass\" style=");
		}
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();

		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
		URL obj;
		HttpURLConnection conn=null;
		
		try {
			obj = new URL(url);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			// conn.connect();
			StringBuffer requestParams = new StringBuffer();
			requestParams.append(URLEncoder.encode("html", "UTF-8"));
			requestParams.append("=").append(URLEncoder.encode(test, "UTF-8"));
			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipo);
			requestParams.append("&");
			requestParams.append("orgId");
			requestParams.append("=").append(orgId);
			requestParams.append("&");
			requestParams.append("stabId");
			requestParams.append("=").append(stabId);
			requestParams.append("&");
			requestParams.append("altId");
			requestParams.append("=").append(altId);
			requestParams.append("&");
			requestParams.append("idCU");
			requestParams.append("=").append(idCU);
			requestParams.append("&");
			requestParams.append("ticketId");
			requestParams.append("=").append(ticketId);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("sostituisciCaller");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
			requestParams.append("&");
			requestParams.append("aggiungiGlifo");
			requestParams.append("=").append(glifo);
			requestParams.append("&");
			requestParams.append("tipoTimbro");
			requestParams.append("=").append("GISA");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(user_id);
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);
			requestParams.append("&");
			requestParams.append("generazionePulita");
			requestParams.append("=").append(generazionePulita);
			requestParams.append("&");
			requestParams.append("statico");
			requestParams.append("=").append(statico);
			requestParams.append("&");
			requestParams.append("extra");
			requestParams.append("=").append(extra);
			requestParams.append("&");
			requestParams.append("urlOriginale");
			requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("caller");
			requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("idSessione");
			requestParams.append("=").append(context.getSession().getId());
			
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			//System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
			System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString());
			conn.getContentLength();
		
			String codDocumento="", titolo="";
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
	
			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
			} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
					return "documentaleError";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					conn.disconnect();
				}
				return executeCommandDownloadPDF(context);
	}
	
public String executeCommandGeneraPDFMacelli(ActionContext context) throws SQLException, IOException{
		
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
		String test = ""; //conterra' l'html recuperato
		
		String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
		String orgId = context.getRequest().getParameter("orgId"); 
		String altId =  context.getRequest().getParameter("altId");
		String glifo =  context.getRequest().getParameter("glifo");
		String actionName = "PrintModulesHTML.do"; //nome action da cchiamare
		String idEsercente =  context.getRequest().getParameter("esercente");
		String nomeEsercente =  context.getRequest().getParameter("nomeEsercente");
		if (nomeEsercente!=null)
			nomeEsercente = URLEncoder.encode(nomeEsercente, "ISO-8859-1");
		//String tipoMacello =  context.getRequest().getParameter("tipoMacello");
		String idPartita = context.getRequest().getParameter("idPartita");
		String idSeduta = context.getRequest().getParameter("idSeduta");
		String idErrataCorrige = context.getRequest().getParameter("idErrataCorrige");
		String organo = context.getRequest().getParameter("organo"); 
		String idCapo = context.getRequest().getParameter("idCapo");
		String dataMacello =  context.getRequest().getParameter("data");
		String comboDateMacellazione =  context.getRequest().getParameter("comboDateMacellazione");
		String comboSedutaMacellazione =  context.getRequest().getParameter("comboSedutaMacellazione");
		String tipoModulo = context.getRequest().getParameter("tipoModulo"); 
		String extra =  context.getRequest().getParameter("extra"); //PARAMETRO AGGIUNTIVO PER VALORI NECESSARI SOLO IN ALCUNI DOCUMENTI (es. anno del registro trasgressori)
		String urlOriginale = "";
		
		boolean sendMail = false;
		String subjectMail = "";
		String bodyMail = "";
		String toMail = "";
		
		String generazionePulita =  context.getRequest().getParameter("generazionePulita");
		boolean statico = false;
		String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
		
		System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
		
		//inizializzo la variabile glifo nel caso sia null
		//glifo valorizzata: aggiungi timbro
		if (glifo==null || glifo.equals("")){
			glifo="";
		}
		if (orgId==null){
			orgId="-1";
		}
		if (altId==null){
			altId="-1";
		}
		
		Connection db = this.getConnection(context);
		//Catturo info sul campo statico
		
		String tipoM = tipo;
		
		if (tipoModulo != null && !("").equals(tipoModulo)){
			tipoM += tipoModulo;
		}
		
		StringBuffer sql = new StringBuffer();
 		sql.append("SELECT * from tipi_documenti where tipo= ? ");
		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setString(1, tipoM);
		ResultSet rs = pst.executeQuery();
	    
		if(rs.next()){
		    	//	statico = rs.getBoolean("unico");
		    		sendMail = rs.getBoolean("send_mail_on_generate");
		    		toMail = rs.getString("mail_to");
		    		subjectMail = rs.getString("mail_subject");
		    		bodyMail = rs.getString("mail_body");
	 		    	}
		
		this.freeConnection(context, db);
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();
				
		HttpURLConnection conn=null;
		
			String idSessione = context.getRequest().getSession().getId();
			String url          	= urlChiamante;
			String metodo="";
			
			if (tipo.equals("Macelli_17")){
				actionName="MacellazioniDocumentiAction.do";
				metodo="Art17";
				extra=dataMacello;
				}
			else if (tipo.equals("Macelli_Registro")){
				actionName="MacellazioniDocumentiAction.do";
				metodo="RegistroMacellazioni";
				extra=comboDateMacellazione;
				}
			else if (tipo.equals("Macelli_Modello")){
				actionName="MacellazioniDocumentiAction.do";
				metodo="ModelloGenerico";
				dataMacello = context.getRequest().getParameter("data"+tipoModulo);
				extra = dataMacello;
				tipo = tipo + tipoModulo;
				}
			else if (tipo.equals("Macelli_17_ErrataCorrige")  && (altId!=null && !altId.equals("null") && !altId.equals("") && !altId.equals("-1"))){
				if (idCapo!=null)
					actionName="MacellazioniDocumentiSintesis.do";
				else
					actionName="MacellazioniDocumentiNewSintesis.do";
				metodo="ViewErrataCorrigeArt17";
				}
			else if (tipo.equals("Macelli_17_ErrataCorrige")){
				if (idCapo!=null)
					actionName="MacellazioniDocumenti.do";
				else
					actionName="MacellazioniDocumentiNew.do";
				metodo="ViewErrataCorrigeArt17";
				}
			else if (tipo.equals("ModelloEchinococco")){
					actionName="MacellazioniDocumentiAction.do";
					metodo="ModelloEchinococco";
				}
			
			if (tipo.equals("Macelli_17"))
				url+=actionName+
				"?command="+ metodo + "&esercente=" + idEsercente+ "&nomeEsercente=" + nomeEsercente+ "&orgId=" + orgId+"&altId=" + altId+ "&data=" + dataMacello+"&idPartita="+idPartita+"&idSeduta="+idSeduta;
			else if (tipo.equals("Macelli_Registro"))
				url+=actionName+
				"?command="+ metodo + "&data=" + comboDateMacellazione+ "&seduta=" + comboSedutaMacellazione+ "&orgId=" + orgId +"&altId=" + altId;
			else if (tipo.startsWith("Macelli_Modello"))
				url+=actionName+
				"?command="+ metodo + "&data=" + dataMacello+ "&tipoModulo=" + tipoModulo+ "&orgId=" + orgId+"&altId=" + altId;
			else if (tipo.equals("Macelli_17_ErrataCorrige"))
				url+=actionName+
				"?command="+ metodo + "&idErrataCorrige=" + idErrataCorrige+ "&orgId=" + orgId+ "&idCapo=" + idCapo+ "&idPartita=" + idPartita+"&altId=" + altId;
			else if (tipo.equals("ModelloEchinococco"))
				url+=actionName+
				"?command="+ metodo + "&idPartita=" + idPartita+ "&orgId=" + orgId+ "&organo=" + organo+"&altId=" + altId;
			//PAGINA DA LEGGERE E CODIFICARE
			urlOriginale = url;
			
			if (test!=null){
				test = fixHtml2(test);
				test = test.replaceAll("style=", "class=\"tableClass\" style=");
			}
		
		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
		URL obj;
		try {
			obj = new URL(urlDocumentale);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			// conn.connect();
			StringBuffer requestParams = new StringBuffer();
			requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
			requestParams.append("=").append(URLEncoder.encode(test, "UTF-8"));
			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipo);
			requestParams.append("&");
			requestParams.append("orgId");
			requestParams.append("=").append(orgId);
			requestParams.append("&");
			requestParams.append("altId");
			requestParams.append("=").append(altId);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("sostituisciCaller");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
			requestParams.append("&");
			requestParams.append("aggiungiGlifo");
			requestParams.append("=").append(glifo);
			requestParams.append("&");
			requestParams.append("tipoTimbro");
			requestParams.append("=").append("GISA");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(user_id);
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);
			requestParams.append("&");
			requestParams.append("generazionePulita");
			requestParams.append("=").append(generazionePulita);
			requestParams.append("&");
			requestParams.append("statico");
			requestParams.append("=").append(statico);
			requestParams.append("&");
			requestParams.append("extra");
			requestParams.append("=").append(extra);
			requestParams.append("&");
			requestParams.append("urlOriginale");
			requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("invioMail");
			requestParams.append("=").append(sendMail);
			requestParams.append("&");
			requestParams.append("toMail");
			requestParams.append("=").append(toMail);
			requestParams.append("&");
			requestParams.append("subjectMail");
			requestParams.append("=").append(subjectMail);
			requestParams.append("&");
			requestParams.append("bodyMail");
			requestParams.append("=").append(bodyMail);
			requestParams.append("&");
			requestParams.append("caller");
			requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("idSessione");
			requestParams.append("=").append(context.getSession().getId());
			
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			//System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
			System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString());
			conn.getContentLength();
		
			String codDocumento="", titolo="";
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
	
			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
			} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
					return "documentaleError";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					conn.disconnect();
				}
				return executeCommandDownloadPDF(context);
	}



public String executeCommandGeneraPDFMacelliUnici(ActionContext context) throws SQLException, IOException {

	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	String test = ""; //conterra' l'html recuperato
	
	String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String orgId = context.getRequest().getParameter("orgId"); 
	String glifo =  context.getRequest().getParameter("glifo");
	String actionName = "PrintModulesHTML.do"; //nome action da cchiamare
	String idEsercente =  context.getRequest().getParameter("esercente");
	//String tipoMacello =  context.getRequest().getParameter("tipoMacello");
	String idPartita = context.getRequest().getParameter("idPartita");
	String idSeduta = context.getRequest().getParameter("idSeduta");
	String idErrataCorrige = context.getRequest().getParameter("idErrataCorrige");
	String organo = context.getRequest().getParameter("organo"); 
	String idCapo = context.getRequest().getParameter("idCapo");
	String dataMacello =  context.getRequest().getParameter("data");
	String comboDateMacellazione =  context.getRequest().getParameter("comboDateMacellazione");
	String comboSedutaMacellazione =  context.getRequest().getParameter("comboSedutaMacellazione");
	String tipoModulo = context.getRequest().getParameter("tipoModulo"); 
	String extra =  context.getRequest().getParameter("extra"); //PARAMETRO AGGIUNTIVO PER VALORI NECESSARI SOLO IN ALCUNI DOCUMENTI (es. anno del registro trasgressori)
	String urlOriginale = "";
	String idArticolo17 = context.getRequest().getParameter("idArticolo17");
	
	String generazionePulita =  context.getRequest().getParameter("generazionePulita");
	boolean statico = false;
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
	//inizializzo la variabile glifo nel caso sia null
	//glifo valorizzata: aggiungi timbro
	if (glifo==null || glifo.equals("")){
		glifo="";
	}
	if (orgId==null){
		orgId="-1";
	}
	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();
			
	HttpURLConnection conn=null;
	
		String idSessione = context.getRequest().getSession().getId();
		String url          	= urlChiamante;
		String metodo="";
		
		if (tipo.equals("Macelli_17")){
			actionName="MacellazioneUnicaDocumenti.do";
			metodo="Art17";
			//extra=dataMacello;
			}
		else if (tipo.equals("Macelli_Registro")){
			actionName="MacellazioneUnicaDocumenti.do";
			metodo="RegistroMacellazioni";
			//extra=comboDateMacellazione;
			}
		else if (tipo.equals("Macelli_Modello")){
			actionName="MacellazioniDocumentiAction.do";
			metodo="ModelloGenerico";
			dataMacello = context.getRequest().getParameter("data"+tipoModulo);
			extra = dataMacello;
			tipo = tipo + tipoModulo;
			}
		else if (tipo.equals("Macelli_17_ErrataCorrige")){
			if (idCapo!=null)
				actionName="MacellazioniDocumenti.do";
			else
				actionName="MacellazioniDocumentiNew.do";
			metodo="ViewErrataCorrigeArt17";
			}
		else if (tipo.equals("ModelloEchinococco")){
				actionName="MacellazioniDocumentiAction.do";
				metodo="ModelloEchinococco";
			}
		
		if (tipo.equals("Macelli_17"))
			url+=actionName+
			"?command="+ metodo + "&idArticolo17=" + idArticolo17;
		else if (tipo.equals("Macelli_Registro"))
			url+=actionName+
			"?command="+ metodo + "&idSeduta=" + idSeduta+ "&orgId=" + orgId;
		else if (tipo.startsWith("Macelli_Modello"))
			url+=actionName+
			"?command="+ metodo + "&data=" + dataMacello+ "&tipoModulo=" + tipoModulo+ "&orgId=" + orgId;
		else if (tipo.equals("Macelli_17_ErrataCorrige"))
			url+=actionName+
			"?command="+ metodo + "&idErrataCorrige=" + idErrataCorrige+ "&orgId=" + orgId+ "&idCapo=" + idCapo+ "&idPartita=" + idPartita;
		else if (tipo.equals("ModelloEchinococco"))
			url+=actionName+
			"?command="+ metodo + "&idPartita=" + idPartita+ "&orgId=" + orgId+ "&organo=" + organo;
		//PAGINA DA LEGGERE E CODIFICARE
		urlOriginale = url;
		
		if (test!=null){
			test = fixHtml2(test);
			test = test.replaceAll("style=", "class=\"tableClass\" style=");
		}
	
	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
		requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
		requestParams.append("=").append(URLEncoder.encode(test, "UTF-8"));
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("orgId");
		requestParams.append("=").append(orgId);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("aggiungiGlifo");
		requestParams.append("=").append(glifo);
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("generazionePulita");
		requestParams.append("=").append(generazionePulita);
		requestParams.append("&");
		requestParams.append("statico");
		requestParams.append("=").append(statico);
		requestParams.append("&");
		requestParams.append("extra");
		requestParams.append("=").append(extra);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		//System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);

}

public String executeCommandGeneraPDFChecklist(ActionContext context) throws SQLException, IOException{
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	String test = ""; //conterra' l'html recuperato
	String orgId =  context.getRequest().getParameter("orgId");
	String stabId =  context.getRequest().getParameter("stabId");
	String idCU = context.getRequest().getParameter("idCU"); 
	String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String idAudit = context.getRequest().getParameter("id"); 
	String glifo =  context.getRequest().getParameter("glifo");
	String actionName = "PrintModulesHTML.do"; //nome action da cchiamare
	String extra =  context.getRequest().getParameter("extra");
	String urlOriginale = "";
	
	
	Connection dbGisa = null;
	try {
	 dbGisa = this.getConnection(context);
	 Audit ch = new Audit(dbGisa, idAudit);
	 orgId = String.valueOf(ch.getOrgId());
	 stabId = String.valueOf(ch.getIdStabilimento());
	 idCU = String.valueOf(ch.getIdControllo());
	}
	catch (Exception errorMessage) {
	}
	finally {
		this.freeConnection(context, dbGisa);
	}
 
	
	String generazionePulita =  context.getRequest().getParameter("generazionePulita");
	boolean statico = false;
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
	//inizializzo la variabile glifo nel caso sia null
	//glifo valorizzata: aggiungi timbro
	if (glifo==null || glifo.equals("")){
		glifo="";
	}
	if (orgId==null){
		orgId="-1";
	}
	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();
	
			
	HttpURLConnection conn=null;
	
		String idSessione = context.getRequest().getSession().getId();
		String url          	= urlChiamante;
		String metodo="";
		
		if (tipo.equals("Checklist")){
			actionName="ChecklistDocumenti.do";
			metodo="StampaChecklist";
			}
	
		
		if (tipo.equals("Checklist"))
			url+=actionName+
			"?command="+ metodo + "&id=" + idAudit;
		//PAGINA DA LEGGERE E CODIFICARE
		urlOriginale = url;
		

	if (test!=null){
		test = fixHtml2(test);
		test = test.replaceAll("style=", "class=\"tableClass\" style=");
		}
	
	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
		requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
		requestParams.append("=").append(URLEncoder.encode(test, "UTF-8"));
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("orgId");
		requestParams.append("=").append(orgId);
		requestParams.append("&");
		requestParams.append("stabId");
		requestParams.append("=").append(stabId);
		requestParams.append("&");
		requestParams.append("idCU");
		requestParams.append("=").append(idCU);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("aggiungiGlifo");
		requestParams.append("=").append(glifo);
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("generazionePulita");
		requestParams.append("=").append(generazionePulita);
		requestParams.append("&");
		requestParams.append("statico");
		requestParams.append("=").append(statico);
		requestParams.append("&");
		requestParams.append("extra");
		requestParams.append("=").append(extra);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		//System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);
}

public String executeCommandGeneraPDFCentralizzato(ActionContext context) throws SQLException, IOException{
		
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
		//URL oracle = null;
		String test = ""; //conterra' l'html recuperato
		String tipoOperatore = context.getRequest().getParameter("tipoOperatore"); //tipo certificato
		String orgId = context.getRequest().getParameter("orgId"); 
		String stabId = context.getRequest().getParameter("stabId");
		String altId =  context.getRequest().getParameter("altId");
		String idCampoEsteso =  context.getRequest().getParameter("idCampoEsteso");
		String actionName = "SchedaCentralizzataAction.do"; //nome action da cchiamare
		String urlOriginale = "";
		String extra = context.getRequest().getParameter("extra"); 
		String generazionePulita = "";
		
		String add1 = context.getRequest().getParameter("address1");
		String add2 = context.getRequest().getParameter("address2");
		String add3 = context.getRequest().getParameter("address3");
		String idSessione = context.getRequest().getSession().getId();
		
		String tipoCertificato ="SchedaOperatore";
		
		
		Connection dbGisa = null;
		String nomeFile = "";
		try {
		 dbGisa = this.getConnection(context);
		  nomeFile = SchedaCentralizzata.getNomeFile(dbGisa, tipoOperatore);
	}
		catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			}
		finally {
			this.freeConnection(context, dbGisa);
		}
		
		if (nomeFile!=null && !nomeFile.equals(""))
			tipoCertificato = nomeFile;
		
//		if (context.getRequest().getParameter("tipoCertificato")!=null && !context.getRequest().getParameter("tipoCertificato").equals(""))
//			tipoCertificato = context.getRequest().getParameter("tipoCertificato");
		
		String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
		
		System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
		
		if (orgId==null){
			orgId="-1";
		}
		if (stabId==null){
			stabId="-1";
		}
		if (altId==null){
			altId="-1";
		}
		if (idCampoEsteso==null){
			idCampoEsteso="-1";
		}
				
		if (extra!=null){
			if (extra.equals("_ModificaData_")){
				extra = getDataCorrente() + " - Documento generato prima di modifica in questa data.";
				generazionePulita ="si";
			}
		}
		
		HttpURLConnection conn=null;
			
			String url          	= urlChiamante;
			String metodo="";
			metodo = "GeneraScheda";
			
			url+=actionName+
						"?command="+ metodo + "&orgId=" + orgId + "&stabId=" + stabId + "&altId=" + altId +"&addressid=" + add1 + "&addressid2=" + add2 + "&addressid3=" + add3 + "&idCampoEsteso=" + idCampoEsteso + "&tipoOperatore=" + tipoOperatore; //PAGINA DA LEGGERE E CODIFICARE
			
			//PAGINA DA LEGGERE E CODIFICARE
			urlOriginale = url;
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();

		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
		URL obj;
		try {
			obj = new URL(urlDocumentale);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			// conn.connect();
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipoCertificato);
			requestParams.append("&");
			requestParams.append("orgId");
			requestParams.append("=").append(orgId);
			requestParams.append("&");
			requestParams.append("stabId");
			requestParams.append("=").append(stabId);
			requestParams.append("&");
			requestParams.append("altId");
			requestParams.append("=").append(altId);
			requestParams.append("&");
			requestParams.append("tipoOperatore");
			requestParams.append("=").append(tipoOperatore);
			requestParams.append("&");
			requestParams.append("extra");
			requestParams.append("=").append(extra);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("sostituisciCaller");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
			requestParams.append("&");
			requestParams.append("tipoTimbro");
			requestParams.append("=").append("GISA");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(user_id);
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);
			requestParams.append("&");
			requestParams.append("urlOriginale");
			requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("caller");
			requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("idSessione");
			requestParams.append("=").append(context.getSession().getId());
			requestParams.append("&");
			requestParams.append("generazionePulita");
			requestParams.append("=").append(generazionePulita);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
			conn.getContentLength();
		
			String codDocumento="", titolo="";
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
	
			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
			} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
					return "documentaleError";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					conn.disconnect();
				}
				return executeCommandDownloadPDF(context);
	}

public String executeCommandGeneraPDFModuliCampione(ActionContext context) throws SQLException, IOException{
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	//URL oracle = null;
	String test = ""; //conterra' l'html recuperato
	String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String orgId = context.getRequest().getParameter("orgId"); 
	String altId =  context.getRequest().getParameter("altId");
	String urlP = context.getRequest().getParameter("url"); 
	String idCU = context.getRequest().getParameter("idCU"); 
	String idCampione = context.getRequest().getParameter("ticketId"); 
	String actionName = "PrintModulesHTML.do"; //nome action da cchiamare
	String metodo = "ViewModules";
	String urlOriginale = "";
	
	String idSessione = context.getRequest().getSession().getId();
	
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
	if (orgId==null){
		orgId="-1";
	}
	if (altId==null){
		altId="-1";
	}
	
	Connection dbGisa = null;
	boolean esistente = false;
	try {
	 dbGisa = this.getConnection(context);
	 esistente = Campioni.controllaEsistenzaSchedaModulo(dbGisa, Integer.parseInt(idCampione));
//	if (!esistente){
//		 context.getRequest().setAttribute("error", "Errore. Per stampare il modulo selezionato, completare prima il verbale cliccando sul bottone MODIFICA DATI VERBALE.");
//		 return "popupError";
//	}
	}
	catch (Exception errorMessage) {
		errorMessage.printStackTrace();
		context.getRequest().setAttribute("Error", errorMessage);
		return ("SystemError");}
	finally {
		this.freeConnection(context, dbGisa);
	}
	
			
	HttpURLConnection conn=null;
	
		String url          	= urlChiamante;
			
		url+=actionName+
					"?command="+ metodo + "&orgId=" + orgId + "&ticketId=" + idCampione + "&idCU=" + idCU + "&url=" + urlP + "&tipo=" + tipo + "&esistente="+esistente; //PAGINA DA LEGGERE E CODIFICARE
		
		//PAGINA DA LEGGERE E CODIFICARE
	
		urlOriginale = url;
	

	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();

	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("orgId");
		requestParams.append("=").append(orgId);
		requestParams.append("&");
		requestParams.append("altId");
		requestParams.append("=").append(altId);
		requestParams.append("&");
		requestParams.append("ticketId");
		requestParams.append("=").append(idCampione);
		requestParams.append("&");
		requestParams.append("idCU");
		requestParams.append("=").append(idCU);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);
}


public String executeCommandGeneraPDFAllerte(ActionContext context) throws SQLException, IOException{
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	String test = ""; //conterra' l'html recuperato
	String idAllerta =  context.getRequest().getParameter("idAllerta");
	String idAsl =  context.getRequest().getParameter("idAsl");
	String tipoAllegato = context.getRequest().getParameter("tipoAllegato"); 
	String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String actionName = "PrintModulesHTML.do"; //nome action da cchiamare
	String extra =  context.getRequest().getParameter("extra");
	String urlOriginale = "";
	
	String generazionePulita =  context.getRequest().getParameter("generazionePulita");
	boolean statico = false;
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();
	
			
	HttpURLConnection conn=null;
	
		String idSessione = context.getRequest().getSession().getId();
		String url          	= urlChiamante;
		String metodo="";
		
		if (tipo.equals("AllegatoF")){
			actionName="TroubleTicketsAllerteNew.do";
			metodo="GeneraAllegatoF";
			extra = "Allegato F "+tipoAllegato +" generato per ASL "+idAsl;
			}
	
		
		if (tipo.equals("AllegatoF"))
			url+=actionName+
			"?command="+ metodo + "&idAllerta=" + idAllerta + "&idAsl=" + idAsl + "&tipoAllegato=" + tipoAllegato;
		//PAGINA DA LEGGERE E CODIFICARE
		urlOriginale = url;
	
	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
		requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
		requestParams.append("=").append(URLEncoder.encode(test, "UTF-8"));
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("ticketId");
		requestParams.append("=").append(idAllerta);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("generazionePulita");
		requestParams.append("=").append(generazionePulita);
		requestParams.append("&");
		requestParams.append("statico");
		requestParams.append("=").append(statico);
		requestParams.append("&");
		requestParams.append("extra");
		requestParams.append("=").append(extra);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		//System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);
}

	public String executeCommandDownloadPDF(ActionContext context) throws SQLException, IOException {
		
		//recupero l'id timbro
			String codDocumento 		=  null;
				codDocumento = context.getRequest().getParameter("codDocumento");
			if (codDocumento==null)
				codDocumento = (String)context.getRequest().getAttribute("codDocumento");
			String idDocumento 				= null;
			idDocumento = context.getRequest().getParameter("idDocumento");
			
			String titolo="";
			String provenienza = ApplicationProperties.getProperty("APP_NAME_GISA");
		
			String download_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE");
			
			if (codDocumento!=null && !codDocumento.equals("null")){
				download_url+="?codDocumento="+codDocumento;
				titolo=codDocumento+".pdf";
			}
			else {
				
				download_url+="?idDocumento="+idDocumento+"&provenienza="+provenienza;
				titolo=provenienza+"_"+idDocumento+".pdf";
			}
			
				if (context.getRequest().getAttribute("titolo")!=null)
					titolo= (String)context.getRequest().getAttribute("titolo");
				
			//Cartella temporanea sull'APP
	        String path_doc = getWebInfPath(context,"tmp_documentale");
	        //Creare il file ...(ispirarsi a GestoreGlifo servlet)
	        
	        File theDir = new File(path_doc);
         	theDir.mkdirs();

         	File inputFile = new File(path_doc+titolo);
         	if (!inputFile.exists())
         		inputFile.createNewFile();
         	URL copyurl;
         	InputStream outputFile=null;
        copyurl = new URL(download_url);
        try {
        outputFile = copyurl.openStream();  
        FileOutputStream out2 = new FileOutputStream(inputFile);
        int c;
        while ((c = outputFile.read()) != -1)
            out2.write(c);
        outputFile.close();
        out2.close();
	 
        String fileType = "";
         
      //  if (new File(fileName).exists()){
         // Find this file id in database to get file name, and file type

         // You must tell the browser the file type you are going to send
         // for example application/pdf, text/plain, text/html, image/jpg
         context.getResponse().setContentType(fileType);

         // Make sure to show the download dialog
         context.getResponse().setHeader("Content-disposition","attachment; filename="+titolo);

         // Assume file name is retrieved from database
         // For example D:\\file\\test.pdf

         File my_file = new File(inputFile.getAbsolutePath());

         // This should send the file to browser
         OutputStream out = context.getResponse().getOutputStream();
         FileInputStream in = new FileInputStream(my_file);
         byte[] buffer = new byte[4096];
         int length;
         while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
         }
         in.close();
         out.flush();
  
         return ("-none-");	
}
         
         catch (ConnectException e1){
	        	context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
	        }
	        
		}

public String executeCommandGeneraPDFLogInviiBASA(ActionContext context) throws SQLException, IOException{
		
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
		//URL oracle = null;
		String test = ""; //conterra' l'html recuperato
		String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
		String idInvioMassivo = context.getRequest().getParameter("idInvioMassivo");
		String dataInizio = context.getRequest().getParameter("dataInizio");
		String dataFine = context.getRequest().getParameter("dataFine");
		String tipoInvioMassivo = context.getRequest().getParameter("tipoInvioMassivo");
		String actionName = "GestioneInvioChecklist.do"; //nome action da cchiamare
		String urlOriginale = "";
		String idSessione = context.getRequest().getSession().getId();
		
		String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
		
		System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
		
		HttpURLConnection conn=null;
			
			String url          	= urlChiamante;
			String metodo="";
			metodo = "DettaglioInvioMassivo";
			
			url+=actionName+
						"?command="+ metodo + "&idInvioMassivo="+idInvioMassivo+"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
			
			//PAGINA DA LEGGERE E CODIFICARE
			urlOriginale = url;
				
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();

		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
		URL obj;
		try {
			obj = new URL(urlDocumentale);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			// conn.connect();
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("tipoCertificato");
			requestParams.append("=").append("LogInvioMassivoBASA");
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("sostituisciCaller");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
			requestParams.append("&");
			requestParams.append("tipoTimbro");
			requestParams.append("=").append("GISA");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(user_id);
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);
			requestParams.append("&");
			requestParams.append("urlOriginale");
			requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("caller");
			requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("idSessione");
			requestParams.append("=").append(context.getSession().getId());
			requestParams.append("&");
			requestParams.append("extra");
			requestParams.append("=").append(tipoInvioMassivo+" ("+dataInizio+" - "+dataFine+")");
			
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
			conn.getContentLength();
		
			String codDocumento="", titolo="";
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
	
			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
			} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
					return "documentaleError";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					conn.disconnect();
				}
				return executeCommandDownloadPDF(context);
	}



public String executeCommandGeneraPDFLogInvii(ActionContext context) throws SQLException, IOException{
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
		//URL oracle = null;
		String test = ""; //conterra' l'html recuperato
		String idInvio = context.getRequest().getParameter("idInvio"); //tipo certificato
		
		String idInvioMolluschi = context.getRequest().getParameter("idInvioMolluschi"); //tipo certificato
		
		String actionName = "";
		if(idInvio != null && Integer.parseInt(idInvio)>-1)
			actionName = "GestioneEsitoIbr.do"; //nome action da cchiamare
	
		if(idInvioMolluschi != null && Integer.parseInt(idInvioMolluschi)>-1)
			actionName = "GestioneInvioMolluschi.do"; //nome action da cchiamare
	
		
		String urlOriginale = "";
		String idSessione = context.getRequest().getSession().getId();
		
		String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
		
		System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
		
		HttpURLConnection conn=null;
			
			String url          	= urlChiamante;
			String metodo="";
			metodo = "AllImportRecordsIBR";
			String tipo = "" ;
			if(actionName.equalsIgnoreCase("GestioneInvioMolluschi.do"))
			{
				metodo = "AllImportRecordsMolluschi";
				url+=actionName+"?command="+ metodo + "&idInvio=" + idInvioMolluschi+"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
				tipo="LogMolluschi";
			
			}
			else
			{
				url+=actionName+"?command="+ metodo + "&idInvio=" + idInvio+"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
				tipo="LogIbr";

				
			}
			
			
			//PAGINA DA LEGGERE E CODIFICARE
			urlOriginale = url;
				
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();

		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
		URL obj;
		try {
			obj = new URL(urlDocumentale);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			// conn.connect();
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipo);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("sostituisciCaller");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
			requestParams.append("&");
			requestParams.append("tipoTimbro");
			requestParams.append("=").append("GISA");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(user_id);
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);
			requestParams.append("&");
			requestParams.append("urlOriginale");
			requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("caller");
			requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("idSessione");
			requestParams.append("=").append(context.getSession().getId());
			requestParams.append("&");
			requestParams.append("idInvioIbr");
			requestParams.append("=").append(idInvio);
			requestParams.append("&");
			requestParams.append("idInvioMolluschi");
			requestParams.append("=").append(idInvioMolluschi);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
			conn.getContentLength();
		
			String codDocumento="", titolo="";
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
	
			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
			} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
					return "documentaleError";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					conn.disconnect();
				}
				return executeCommandDownloadPDF(context);
	}

public String executeCommandGeneraPDFLogB11(ActionContext context) throws SQLException, IOException{
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	//URL oracle = null;
	String test = ""; //conterra' l'html recuperato
	String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String dataEstrazione = context.getRequest().getParameter("dataEstrazione");
	String dataInizio = context.getRequest().getParameter("dataInizio");
	String dataFine = context.getRequest().getParameter("dataFine");
	String actionName = "Allevamenti.do"; //nome action da cchiamare
	String extra = dataEstrazione + " ("+dataInizio+" - "+dataFine+")";
	String urlOriginale = "";
	String idSessione = context.getRequest().getSession().getId();
	
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
	HttpURLConnection conn=null;
		
		String url          	= urlChiamante;
		String metodo="";
		metodo = "AllImportRecordsB11";
		
		url+=actionName+
					"?command="+ metodo + "&tipo=" + tipo+"&dataEstrazione="+dataEstrazione+"&searchtimestampInizio_b11="+dataInizio+"&searchtimestampFine_b11="+dataFine+"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
		
		//PAGINA DA LEGGERE E CODIFICARE
		urlOriginale = url;
			
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();

	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("LogB11_"+tipo);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("extra");
		requestParams.append("=").append(extra);
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);
}

public String executeCommandListaDocumentiByTipo(ActionContext context){
		
		//Catturo tutti i possibili campi che possono arrivare per i diversi tipi di certificato
	String orgIdString = context.getRequest().getParameter("orgId");
	String stabIdString = context.getRequest().getParameter("stabId");
	String ticketIdString = context.getRequest().getParameter("ticketId");
	String tipo = context.getRequest().getParameter("tipo");
	String idCUString = context.getRequest().getParameter("idCU");
	String url = context.getRequest().getParameter("url");
	String extra = context.getRequest().getParameter("extra");
	String htmlcode = context.getRequest().getParameter("htmlcode");
	
	int orgId=-1, ticketId=-1, idCU=-1, stabId =-1;
	//Se sono arrivati i campi, convertili in interi
		if (orgIdString!=null && !orgIdString.equals("null"))
			orgId=Integer.parseInt(orgIdString);
		if (ticketIdString!=null && !ticketIdString.equals("null"))
			ticketId=Integer.parseInt(ticketIdString);
		if (idCUString!=null && !idCUString.equals("null"))
			idCU=Integer.parseInt(idCUString);
		if (stabIdString!=null && !stabIdString.equals("null"))
			stabId=Integer.parseInt(stabIdString);
		
		HttpURLConnection conn = null;
		String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+lista_url.toString());

		URL obj;
		try {
			obj = new URL(lista_url);
		
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("ticketId");
		requestParams.append("=").append(ticketId);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("idCU");
		requestParams.append("=").append(idCU);
		requestParams.append("&");
		requestParams.append("orgId");
		requestParams.append("=").append(orgId);
		requestParams.append("&");
		requestParams.append("stabId");
		requestParams.append("=").append(stabId);
		requestParams.append("&");
		requestParams.append("url");
		requestParams.append("=").append(url);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
				
		String codDocumento="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
			
			DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
			 docList.creaElenco(jo);
			 context.getRequest().setAttribute("listaDocumenti", docList);

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			return "documentaleError";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
		context.getRequest().setAttribute("tipo", tipo);
		context.getRequest().setAttribute("orgId", String.valueOf(orgId));
		context.getRequest().setAttribute("stabId", String.valueOf(stabId));
		context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		context.getRequest().setAttribute("idCU", String.valueOf(idCU));
		context.getRequest().setAttribute("url", url);
		context.getRequest().setAttribute("extra", extra);
		context.getRequest().setAttribute("htmlcode", htmlcode);
		return "listaDocTipoOK";
	}
	
public String executeCommandListaDocumenti(ActionContext context) throws SQLException, UnknownHostException {
	//MOSTRA LISTA DOCUMENTI TIMBRATI PER L'ANIMALE
	
	int orgId = -1;
	int ticketId = -1;
	int stabId = -1;
	int altId = -1;
	String orgIdString = context.getRequest().getParameter("orgId");
	String stabIdString = context.getRequest().getParameter("stabId");
	String ticketIdString = context.getRequest().getParameter("ticketId");
	String altIdString = context.getRequest().getParameter("altId");
	
	if (orgIdString != null && !orgIdString.equals("null"))
		orgId = Integer.parseInt(orgIdString);
	if (stabIdString != null && !stabIdString.equals("null"))
		stabId = Integer.parseInt(stabIdString);
	if (ticketIdString != null && !ticketIdString.equals("null"))
		ticketId = Integer.parseInt(ticketIdString);
	if (altIdString != null && !altIdString.equals("null"))
		altId = Integer.parseInt(altIdString);
	
	Connection db = null;
	try {
		db = this.getConnection(context);
		
		if (orgId>0){ 
		org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
		org.getOnlyRagioneSociale(db, orgId);
		context.getRequest().setAttribute("OrgDetails", org);
		}
		else if (stabId>0){
			Stabilimento stab = new Stabilimento (db, stabId);
			context.getRequest().setAttribute("StabilimentoDettaglio", stab);
			
			Operatore operatore = new Operatore() ; 
			operatore.queryRecordOperatore(db, stab.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);
		}
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_OPU_RICHIESTE){
			org.aspcfs.modules.suap.base.Stabilimento stab = new org.aspcfs.modules.suap.base.Stabilimento (db, altId, true);
			context.getRequest().setAttribute("StabilimentoRichiestaDettaglio", stab);
			
			org.aspcfs.modules.suap.base.Operatore operatore = new org.aspcfs.modules.suap.base.Operatore() ;
			operatore.queryRecordOperatore(db, stab.getIdOperatore());	
			context.getRequest().setAttribute("OperatoreRichiesta", operatore);
		}
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_SINTESIS){
			org.aspcfs.modules.sintesis.base.SintesisStabilimento stab = new org.aspcfs.modules.sintesis.base.SintesisStabilimento (db, altId, true);
			context.getRequest().setAttribute("StabilimentoSintesisDettaglio", stab);
			
			org.aspcfs.modules.sintesis.base.SintesisOperatore operatore = new org.aspcfs.modules.sintesis.base.SintesisOperatore() ;
			operatore.queryRecordOperatore(db, stab.getIdOperatore());	
			context.getRequest().setAttribute("OperatoreSintesis", operatore);
		}	
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_ANAGRAFICA_STABILIMENTI){
			org.aspcfs.modules.gestioneanagrafica.base.Stabilimento stab = new org.aspcfs.modules.gestioneanagrafica.base.Stabilimento (db, altId, true);
			context.getRequest().setAttribute("StabilimentoAnagraficaDettaglio", stab);
			context.getRequest().setAttribute("OperatoreAnagrafica", stab.getImpresa());
		}
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_OPU){
			org.aspcfs.modules.opu.base.Stabilimento stab = new org.aspcfs.modules.opu.base.Stabilimento (db, altId, true);
			context.getRequest().setAttribute("StabilimentoDettaglio", stab);
			context.getRequest().setAttribute("Operatore", stab.getOperatore());
		}	
		
	}
	catch(SQLException e1){
		e1.printStackTrace();
	} catch (IndirizzoNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		this.freeConnection(context, db);
	}
	
	context.getRequest().setAttribute("orgId", String.valueOf(orgId));
	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
	context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
	
	HttpURLConnection conn = null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

	URL obj;
	
		try {
			obj = new URL(url);
		
	
	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("orgId");
	requestParams.append("=").append(orgId);
	requestParams.append("&");
	requestParams.append("stabId");
	requestParams.append("=").append(stabId);

	if (stabId <=0){
		requestParams.append("&");
		requestParams.append("altId");
		requestParams.append("=").append(altId);
	}
	
	requestParams.append("&");
	requestParams.append("ticketId");
	requestParams.append("=").append(ticketId);
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	

	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
	conn.getContentLength();
			
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
		
		 DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
		 docList.creaElenco(jo);
		 context.getRequest().setAttribute("listaDocumenti", docList);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "Documenti PDF");
			return "documentaleAllegatiError";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
	return "listaDocOK";
}

public String executeCommandListaDocumentiTrasgressori(ActionContext context) throws SQLException, UnknownHostException {
	//MOSTRA LISTA DOCUMENTI TIMBRATI PER L'ANIMALE
	
	Connection db = null;
	context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
	
	HttpURLConnection conn = null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

	URL obj;
	
		try {
			obj = new URL(url);

	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("tipoCertificato");
	requestParams.append("=").append("RegistroTrasgressori");
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
	conn.getContentLength();
			
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
		
		 DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
		 docList.creaElenco(jo);
		 context.getRequest().setAttribute("listaDocumenti", docList);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "Documenti PDF");
			return "documentaleAllegatiError";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
	return "listaDocOK";
}

public String executeCommandListaDocumentiInvioCUBA(ActionContext context) throws SQLException, UnknownHostException {
	//MOSTRA LISTA DOCUMENTI TIMBRATI PER L'ANIMALE
	
	Connection db = null;
	context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
	
	HttpURLConnection conn = null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

	URL obj;
	
		try {
			obj = new URL(url);

	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("tipoCertificato");
	requestParams.append("=").append("LogBA_massivo");
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
	conn.getContentLength();
			
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
		
		 DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
		 docList.creaElenco(jo);
		 context.getRequest().setAttribute("listaDocumenti", docList);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "Documenti PDF");
			return "documentaleAllegatiError";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
	return "listaDocOK";
}

public String executeCommandListaDocumentiInvioCUBASA(ActionContext context) throws SQLException, UnknownHostException {
	//MOSTRA LISTA DOCUMENTI TIMBRATI PER L'ANIMALE
	
	Connection db = null;
	context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
	
	HttpURLConnection conn = null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

	URL obj;
	
		try {
			obj = new URL(url);

	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("tipoCertificato");
	requestParams.append("=").append("LogInvioMassivoBASA");
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
	conn.getContentLength();
			
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
		
		 DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
		 docList.creaElenco(jo);
		 context.getRequest().setAttribute("listaDocumenti", docList);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "Documenti PDF");
			return "documentaleAllegatiError";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
	return "listaDocOK";
}



public String executeCommandListaDocumentiCUIBR(ActionContext context) throws SQLException, UnknownHostException {
	//MOSTRA LISTA DOCUMENTI TIMBRATI PER L'ANIMALE
	
	Connection db = null;
	context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
	
	HttpURLConnection conn = null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

	URL obj;
	
		try {
			obj = new URL(url);

	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("tipoCertificato");
	requestParams.append("=").append("LogIBR_massivo");
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
	conn.getContentLength();
			
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
		
		 DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
		 docList.creaElenco(jo);
		 context.getRequest().setAttribute("listaDocumenti", docList);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "Documenti PDF");
			return "documentaleAllegatiError";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
	return "listaDocOK";
}

public String executeCommandListaDocumentiInvioCUB11(ActionContext context) throws SQLException, UnknownHostException {
	//MOSTRA LISTA DOCUMENTI TIMBRATI PER L'ANIMALE
	
	Connection db = null;
	context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
	
	HttpURLConnection conn = null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

	URL obj;
	
		try {
			obj = new URL(url);

	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("tipoCertificato");
	requestParams.append("=").append("LogB11_massivo");
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
	conn.getContentLength();
			
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
		
		 DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
		 docList.creaElenco(jo);
		 context.getRequest().setAttribute("listaDocumenti", docList);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "Documenti PDF");
			return "documentaleAllegatiError";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
	return "listaDocOK";
}


public String executeCommandListaDocumentiDpat(ActionContext context) throws SQLException, UnknownHostException {
	//MOSTRA LISTA DOCUMENTI TIMBRATI PER L'ANIMALE
	
	Connection db = null;
	context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
	
	HttpURLConnection conn = null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

	URL obj;
	
		try {
			obj = new URL(url);

	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("tipoCertificato");
	requestParams.append("=").append(URLEncoder.encode("%DPAT%", "ISO-8859-1"));
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
	conn.getContentLength();
			
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
		
		 DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
		 docList.creaElenco(jo);
		 context.getRequest().setAttribute("listaDocumenti", docList);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "Documenti PDF");
			return "documentaleAllegatiError";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
	return "listaDocOK";
}

private String getHtml(String url, String idSessione) 
{
	URL urlToCall	= null; 
	String html 	= "";
	System.out.println("[DOCUMENTALE GISA]: Pagina da recuperare "+url);
	
	try 
	{
		urlToCall = new URL(url); 
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
		return "";
	}

	HttpURLConnection conn;
	try 
	{
		conn = (HttpURLConnection) urlToCall.openConnection();
		conn.setRequestProperty("cookie", "JSESSIONID=" + idSessione);
		conn.setDoOutput(false);
		conn.setReadTimeout(200*1000);
		conn.setConnectTimeout(200*1000);
		
		conn.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String inputLine;
		while ((inputLine = in.readLine()) != null) 
		{
			html += inputLine;
		}
		in.close();
	} 
	catch (IOException e2) 
	{
		e2.printStackTrace();
		return "";
	}
	
	return html;
	
}



public String executeCommandGeneraPDFInvioCampioniIstopatologico(ActionContext context) throws SQLException, IOException{

	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	//URL oracle = null;
	String test = null;
	//String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String idIstopatologico = context.getRequest().getParameter("idIstopatologico"); 
	String actionName = "Macellazioni.do"; //nome action da cchiamare
	String urlOriginale = "";
	
	String idSessione = context.getRequest().getSession().getId();
	
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
			
	HttpURLConnection conn=null;
	
	//	URL oracle = null;
		
		String url          	= urlChiamante;
		String metodo="";
		metodo = "RichiestaIstopatologicoStampaModelloInvioCampioni";
		
		url+=actionName+
					"?command="+ metodo + "&idIstopatologico=" + idIstopatologico; //PAGINA DA LEGGERE E CODIFICARE
		
		//PAGINA DA LEGGERE E CODIFICARE

		
	//	test = getHtml(url, idSessione);
		urlOriginale = url;
//			
//	if (test==null || test.equals("")){ //se e' fallita la cattura, esci
//		context.getRequest().setAttribute("error", "Fallita cattura della pagina d'origine.");
//		return "timbraERROR";
//		}


	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();

	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
//		requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
//		requestParams.append("=").append(URLEncoder.encode(test, "ISO-8859-1"));
//		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("ModuloInvioCampioniIstopatologico");
	//	requestParams.append("&");
		//requestParams.append("orgId");
		//requestParams.append("=").append();
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);

}


public String executeCommandGeneraPDFInvioCampioniIstopatologicoNew(ActionContext context) throws SQLException, IOException{

	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	//URL oracle = null;
	String test = null;
	//String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String idIstopatologico = context.getRequest().getParameter("idIstopatologico"); 
	String actionName = "MacellazioniNew.do"; //nome action da cchiamare
	String urlOriginale = "";
	
	String idSessione = context.getRequest().getSession().getId();
	
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
			
	HttpURLConnection conn=null;
	
		URL oracle = null;
		
		String url          	= urlChiamante;
		String metodo="";
		metodo = "RichiestaIstopatologicoStampaModelloInvioCampioni";
		
		url+=actionName+
					"?command="+ metodo + "&idIstopatologico=" + idIstopatologico; //PAGINA DA LEGGERE E CODIFICARE
		
		//PAGINA DA LEGGERE E CODIFICARE

		
		test = getHtml(url, idSessione);
		urlOriginale = url;
			
	if (test==null || test.equals("")){ //se e' fallita la cattura, esci
		context.getRequest().setAttribute("error", "Fallita cattura della pagina d'origine.");
		return "timbraERROR";
		}


	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();

	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
//		requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
//		requestParams.append("=").append(URLEncoder.encode(test, "ISO-8859-1"));
//		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("ModuloInvioCampioniIstopatologico");
	//	requestParams.append("&");
		//requestParams.append("orgId");
		//requestParams.append("=").append();
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);

}

public String executeCommandGeneraPDFDettaglioCentralizzato(ActionContext context) throws SQLException, IOException{
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	String tipoDettaglio = context.getRequest().getParameter("tipoDettaglio"); //tipo certificato
	String objectId = context.getRequest().getParameter("objectId"); 
	String servletName = "ServletServiziScheda"; //nome action da cchiamare
	String urlOriginale = "";
	
	String add1 = context.getRequest().getParameter("address1");
	String add2 = context.getRequest().getParameter("address2");
	String add3 = context.getRequest().getParameter("address3");
	
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
	if (objectId==null){
		objectId="-1";
	}
	
			
	HttpURLConnection conn=null;
		
		String url          	= urlChiamante;
		
		url+=servletName+
					"?object_id="+ objectId + "&tipo_dettaglio=" + tipoDettaglio + "&output_type=html&visualizzazione=print"; //PAGINA DA LEGGERE E CODIFICARE
		
		//PAGINA DA LEGGERE E CODIFICARE
		urlOriginale = url;
	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();

	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("SchedaOperatore");
		requestParams.append("&");
		requestParams.append("orgId");
		requestParams.append("=").append(objectId);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);
}

public String executeCommandGeneraPDFCUCentralizzato(ActionContext context) throws SQLException, IOException{
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	String idCU = context.getRequest().getParameter("idCU"); 
	String actionName = "SchedaCentralizzataControlloAction.do"; //nome action da cchiamare
	String orgId = "";
	String stabId = "";
	String urlOriginale = "";
	String idSessione = context.getRequest().getSession().getId();
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	Connection db = null;
	try {
		db = this.getConnection(context);
		
		org.aspcfs.modules.vigilanza.base.Ticket t = new org.aspcfs.modules.vigilanza.base.Ticket();
		t.queryRecord(db,  Integer.parseInt(idCU));
		orgId = String.valueOf(t.getOrgId());
		stabId = String.valueOf(t.getIdStabilimento());
		
	}
	catch(SQLException e1){
		e1.printStackTrace();
	}
	finally{
		this.freeConnection(context, db);
	}
	
	
	
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
			
	HttpURLConnection conn=null;
		
		String url          	= urlChiamante;
		String metodo="";
		metodo = "GeneraScheda";
		
		url+=actionName+
					"?command="+ metodo + "&ticketId=" + idCU; //PAGINA DA LEGGERE E CODIFICARE
		
		//PAGINA DA LEGGERE E CODIFICARE
		urlOriginale = url;
	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();

	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+urlDocumentale.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("SchedaControllo");
		requestParams.append("&");
		requestParams.append("idCU");
		requestParams.append("=").append(idCU);
		requestParams.append("&");
		requestParams.append("orgId");
		requestParams.append("=").append(orgId);
		requestParams.append("&");
		requestParams.append("stabId");
		requestParams.append("=").append(stabId);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);
}


private String getDataCorrente(){
	Calendar calendar = null;
	calendar = Calendar.getInstance();
	int mese = calendar.get(Calendar.MONTH)+1;  
	int anno = calendar.get(Calendar.YEAR);  
	int giorno = calendar.get(Calendar.DAY_OF_MONTH);
	int ora = calendar.get(Calendar.HOUR_OF_DAY);
	int minuti = calendar.get(Calendar.MINUTE);
	
	String giornoString = String.valueOf(giorno);
	  if (giornoString.length()!=2)
		  giornoString = "0"+giorno;
	  
	 String meseString = String.valueOf(mese);
	  if (meseString.length()!=2)
		  meseString = "0"+mese;
	
	String oraString = String.valueOf(ora);
	  if (oraString.length()!=2)
		  oraString = "0"+ora;
	  
	  String minutiString = String.valueOf(minuti);
	  if (minutiString.length()!=2)
		  minutiString = "0"+minuti;
	 		   
     return giornoString + "/"  + meseString + "/" + anno + " " + oraString + ":" + minutiString;
}

private String fixHtml2(String test){

	//rimuovo tutti gli onblur (Non riconosciuti in conversione)
			test = test.replaceAll("(?s)onblur=\".*?\"", "");
			test=test.replaceAll("onblur=\"\"", "");
			
			//rimuovo tutti i readonly
			test=test.replaceAll(" readonly=\"\"", "");
			
			//rimuovo finestra modale di attesa
			test = test.replaceAll("(?s)<div id=\"modalWindow\".*?</div>", "");
			
			/* Estraggo tutti i campi di input */
			
					
			Pattern pattern = Pattern.compile("<input (.*?)>");
		    Matcher matcher = pattern.matcher(test);
		 
		    while (matcher.find()) { 
		    	//System.out.println(matcher.group(1));
		    	String pattern_new = matcher.group(1); // INTERO CAMPO DI INPUT
		    	
		    	Pattern pattern3 = Pattern.compile("type=\"(.*?)\"");
			    Matcher matcher3 = pattern3.matcher(pattern_new);
			    String typeInput="";
			     while (matcher3.find())  // CONTENUTO DEL TYPE
			    	 typeInput = matcher3.group(1);
			    
			     if ((typeInput.equals("text") ||typeInput.equals("number") || typeInput.equals("date") || typeInput.equals("time")) && (!pattern_new.contains("hidden") && !pattern_new.contains("style=\"display: none")))
			     {
			     Pattern pattern4 = Pattern.compile("size=\"(.*?)\"");
				    Matcher matcher4 = pattern4.matcher(pattern_new);
				    String sizeString="";
				    int size = 10;
				    while (matcher4.find())  //CONTENUTO DEL SIZE
				    	   	sizeString = matcher4.group(1);
				    try { 
				    	size = Integer.parseInt(sizeString);
				    } catch(NumberFormatException e) { 
				        size = 10;
				    }
				    
				    Pattern pattern5 = Pattern.compile("class=\"(.*?)\"");
				    Matcher matcher5 = pattern5.matcher(pattern_new);
				    String className="";
				   
				    while (matcher5.find())  //CONTENUTO DEL CLASS
				    	className = matcher5.group(1);
				    
				    className ="layout"; //Sovrascrivo e metto layout
				    
				 Pattern pattern2 = Pattern.compile("value=\"(.*?)\"");
			    Matcher matcher2 = pattern2.matcher(pattern_new);
			    while (matcher2.find()) {
			    	//System.out.println(matcher2.group(1));
			    	String pattern_new2 = matcher2.group(1);
			    	if (pattern_new2.replaceAll(" ", "").replaceAll("&nbsp;", "").equals("")){
			    		for (int i=0;i<size;i++)
			    		pattern_new2 = pattern_new2+"&nbsp;"; 
			    		}
			    	else
			    		pattern_new2=pattern_new2.toUpperCase();
			    	//SOSTITUIRE INPUT TYPE CON LABEL
			    	test = test.replace(pattern_new, "<label class = \""+className+"\">"+pattern_new2+"</label>");
			    
			    		  }
			     }
		      }
		  
		    test = togliTagScript(test);
		    
		    //Gestione RadioButton e checkbox
		    String strRegEx = "<[^>]*radio[^>]*checked[^>]*>";
		    test = test.replaceAll(strRegEx, "[X]") ;
		    
	        strRegEx = "<[^>]*checked[^>]*radio[^>]*>";
	        test = test.replaceAll(strRegEx, "[X]") ;
	        
	        strRegEx = "<[^>]*radio[^>]*>";
	        test = test.replaceAll(strRegEx, "[ ]") ;
	        
	        strRegEx = "<[^>]*checkbox[^>]*checked[^>]*>";
		    test = test.replaceAll(strRegEx, "[X]") ;
		    
	        strRegEx = "<[^>]*checked[^>]*checkbox[^>]*>";
	        test = test.replaceAll(strRegEx, "[X]") ;
	        
	        strRegEx = "<[^>]*checkbox[^>]*>";
	        test = test.replaceAll(strRegEx, "[ ]") ;
	        //Fine gestione radio button e checkbox
		    
			//Rimuovo tutti i tag appesi
		    test=test.replaceAll("label>>", "label>");

		    //gestisco lettere accentate non lette in conversione
			//test = gestioneCaratteriSpeciali(test);
			
		    return test;

}


public String executeCommandGeneraPDFRichiestaErrataCorrige(ActionContext context) throws SQLException, IOException, NumberFormatException, IndirizzoNotFoundException{
		
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
		//URL oracle = null;
		String test = ""; //conterra' l'html recuperato
		String id = context.getRequest().getParameter("id");
    	String orgId = context.getRequest().getParameter("orgId");
    	String stabId = context.getRequest().getParameter("stabId");
		String actionName = "GestioneRichiesteErrataCorrige.do"; //nome action da cchiamare
		String urlOriginale = "";
		String extra = context.getRequest().getParameter("extra");
		
		String riferimentoId = context.getRequest().getParameter("riferimentoId");
		String riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");
		
		if (riferimentoIdNomeTab.equals("opu_stabilimento"))
			stabId = riferimentoId;
		else if (riferimentoIdNomeTab.equals("organization"))
			orgId = riferimentoId;
		
		String generazionePulita = "";
	
		String idSessione = context.getRequest().getSession().getId();
		
		String tipoCertificato ="RichiestaErrataCorrige";
		
		String nomeFile = "RichiestaErrataCorrige";
		
	
		String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
		
		System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
		
		if (orgId==null){
			orgId="-1";
		}
		if (stabId==null){
			stabId="-1";
		}
		extra ="ID Errata Corrige: "+id;
		
		HttpURLConnection conn=null;
			
			String url          	= urlChiamante;
			String metodo="";
			metodo = "ModuloRichiestaErrataCorrige";
			
			url+=actionName+
						"?command="+ metodo + "&id=" + id ; //PAGINA DA LEGGERE E CODIFICARE
			
			//PAGINA DA LEGGERE E CODIFICARE
			urlOriginale = url;
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();

		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
		URL obj;
		try {
			obj = new URL(urlDocumentale);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			// conn.connect();
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipoCertificato);
			requestParams.append("&");
			requestParams.append("stabId");
			requestParams.append("=").append(stabId);
			requestParams.append("&");
			requestParams.append("orgId");
			requestParams.append("=").append(orgId);
			requestParams.append("&");
			requestParams.append("extra");
			requestParams.append("=").append(extra);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("sostituisciCaller");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
			requestParams.append("&");
			requestParams.append("tipoTimbro");
			requestParams.append("=").append("GISA");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(user_id);
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);
			requestParams.append("&");
			requestParams.append("urlOriginale");
			requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("caller");
			requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("idSessione");
			requestParams.append("=").append(context.getSession().getId());
			requestParams.append("&");
			requestParams.append("generazionePulita");
			requestParams.append("=").append(generazionePulita);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
			conn.getContentLength();
		
			String codDocumento="", titolo="";
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
	
			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
			
			
			if (codDocumento!=null){
				String file = scaricaFileInLocale(context);
				
				Connection db = null;
				try {
					db = this.getConnection(context);
					GestioneRichiesteErrataCorrige.associaHeaderEInviaMail(context, db, Integer.parseInt(id), codDocumento, file);
					} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					this.freeConnection(context, db);
				}
			}
			
			} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
					return "documentaleError";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					conn.disconnect();
				}
		
		return executeCommandDownloadPDF(context);
	}


public String scaricaFileInLocale(ActionContext context) throws SQLException, IOException {
	
	//recupero l'id timbro
		String codDocumento 		=  null;
			codDocumento = context.getRequest().getParameter("codDocumento");
		if (codDocumento==null)
			codDocumento = (String)context.getRequest().getAttribute("codDocumento");
		String idDocumento 				= null;
		idDocumento = context.getRequest().getParameter("idDocumento");
		
		String titolo="";
		String provenienza = ApplicationProperties.getProperty("APP_NAME_GISA");
	
		String download_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE");
		
		if (codDocumento!=null && !codDocumento.equals("null")){
			download_url+="?codDocumento="+codDocumento;
			titolo=codDocumento+".pdf";
		}
		else {
			
			download_url+="?idDocumento="+idDocumento+"&provenienza="+provenienza;
			titolo=provenienza+"_"+idDocumento+".pdf";
		}
		
			if (context.getRequest().getAttribute("titolo")!=null)
				titolo= (String)context.getRequest().getAttribute("titolo");
			
		//Cartella temporanea sull'APP
        String path_doc = getWebInfPath(context,"tmp_documentale");
        //Creare il file ...(ispirarsi a GestoreGlifo servlet)
        
        File theDir = new File(path_doc);
     	theDir.mkdirs();

     	File inputFile = new File(path_doc+titolo);
     	if (!inputFile.exists())
     		inputFile.createNewFile();
     	URL copyurl;
     	InputStream outputFile=null;
    copyurl = new URL(download_url);
    try {
    outputFile = copyurl.openStream();  
    FileOutputStream out2 = new FileOutputStream(inputFile);
    int c;
    while ((c = outputFile.read()) != -1)
        out2.write(c);
    outputFile.close();
    out2.close();
 
     return inputFile.getAbsolutePath();	
}
     
     catch (ConnectException e1){
        	context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			return "documentaleError";
        }
        
	}




public String executeCommandGeneraPDFAMR(ActionContext context) throws SQLException, IOException{

	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	//URL oracle = null;
	String test = null;
	//String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String idControllo = context.getRequest().getParameter("idControllo"); 
	String actionName = "PrintReportVigilanza.do"; //nome action da cchiamare
	String urlOriginale = "";
	
	String idSessione = context.getRequest().getSession().getId();
	
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
			
	HttpURLConnection conn=null;
	
		URL oracle = null;
		
		String url          	= urlChiamante;
		String metodo="";
		metodo = "StampaAMR";
		
		url+=actionName+
					"?command="+ metodo + "&idControllo=" + idControllo; //PAGINA DA LEGGERE E CODIFICARE
		
		//PAGINA DA LEGGERE E CODIFICARE

		
		test = getHtml(url, idSessione);
		urlOriginale = url;
			
	if (test==null || test.equals("")){ //se e' fallita la cattura, esci
		context.getRequest().setAttribute("error", "Fallita cattura della pagina d'origine.");
		return "timbraERROR";
		}


	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();

	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
//		requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
//		requestParams.append("=").append(URLEncoder.encode(test, "ISO-8859-1"));
//		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("AMR");
		requestParams.append("&");
		requestParams.append("idCU");
		requestParams.append("=").append(idControllo);
		requestParams.append("&");
		requestParams.append("statico");
		requestParams.append("=").append(true);
	//	requestParams.append("&");
		//requestParams.append("orgId");
		//requestParams.append("=").append();
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);

}

public String executeCommandGeneraPDFScarrabile(ActionContext context) throws SQLException, IOException{

	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	//URL oracle = null;
	String test = null;
	//String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String idAutomezzo = context.getRequest().getParameter("id"); 
	String altId = context.getRequest().getParameter("altId"); 
	String extra =  "Documento Automezzo id: "+idAutomezzo; 
	
	String actionName = "StabilimentoSintesisAction.do"; //nome action da cchiamare
	String urlOriginale = "";
	
	String idSessione = context.getRequest().getSession().getId();
	
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
			
	HttpURLConnection conn=null;
	
		URL oracle = null;
		
		String url          	= urlChiamante;
		String metodo="";
		metodo = "DettaglioAutomezzo";
		
		url+=actionName+
					"?command="+ metodo + "&id=" + idAutomezzo+"&print=print"; //PAGINA DA LEGGERE E CODIFICARE
		
		//PAGINA DA LEGGERE E CODIFICARE

		test = getHtml(url, idSessione);
		urlOriginale = url;
			
	if (test==null || test.equals("")){ //se e' fallita la cattura, esci
		context.getRequest().setAttribute("error", "Fallita cattura della pagina d'origine.");
		return "timbraERROR";
		}

	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();

	String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	try {
		obj = new URL(urlDocumentale);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
//		requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
//		requestParams.append("=").append(URLEncoder.encode(test, "ISO-8859-1"));
//		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("Scarrabile");
		requestParams.append("&");
		requestParams.append("altId");
		requestParams.append("=").append(altId);
		requestParams.append("&");
		requestParams.append("extra");
		requestParams.append("=").append(extra);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);

}

public String executeCommandGeneraPDFdaHtml(ActionContext context) throws SQLException, IOException{ 
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		return "documentaleError";
	}
	
	String test = ""; //conterra' l'html recuperato
	String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
	String orgId = context.getRequest().getParameter("orgId"); 
	String stabId = context.getRequest().getParameter("stabId"); 
	String urlId =  context.getRequest().getParameter("url");
	String idCU = context.getRequest().getParameter("idCU");
	String altId =  context.getRequest().getParameter("altId");
	String ticketId = context.getRequest().getParameter("ticketId"); 
	String htmlcode = context.getRequest().getParameter("htmlcode");

	if (htmlcode==null){
		htmlcode = (String)context.getSession().getAttribute("htmlcode"); 
		context.getSession().removeAttribute("htmlcode");
	}
	String glifo =  context.getRequest().getParameter("glifo");
	String extra =  context.getRequest().getParameter("extra"); //PARAMETRO AGGIUNTIVO PER VALORI NECESSARI SOLO IN ALCUNI DOCUMENTI (es. anno del registro trasgressori)
	String urlOriginale = "";
	
	//Apparo per OPU
	if (urlId!=null && urlId.endsWith("Opu")){
		if (stabId==null)
			stabId=orgId;
			orgId = null;
	}
	//Apparo per SINTESIS
	else if (urlId!=null && urlId.contains("Sintesis")){
		if (altId==null)
			altId=orgId;
			orgId = null;
	}
	//Apparo per NUOVA ANAGRAFICA
			else if (urlId!=null && urlId.contains("Operatoriprivati")){
				if (altId==null)
					altId=orgId;
					orgId = null;
			}
	
	String generazionePulita =  context.getRequest().getParameter("generazionePulita");
	boolean statico = false;
	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
	
	System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
	
	//inizializzo la variabile glifo nel caso sia null
	//glifo valorizzata: aggiungi timbro
	if (glifo==null || glifo.equals("")){
		glifo="";
	}
	if (orgId==null){
		orgId="-1";
	}
	if (altId==null){
		altId="-1";
	}
	if (stabId==null){
		stabId="-1";
	}
	if (ticketId==null){
		ticketId="-1";
	}if (idCU==null){
		idCU="-1";
	}
	
	if (htmlcode!=null)
		test = htmlcode; //test contiene il sorgente html della pagina
	

	if (test!=null){
		test = fixHtml2(test);
	}
	
	UserBean user = (UserBean) context.getSession().getAttribute("User");
	String ip = context.getIpAddress();
	int user_id = user.getUserRecord().getId();

	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
	URL obj;
	HttpURLConnection conn=null;
	
	try {
		obj = new URL(url);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		// conn.connect();
		StringBuffer requestParams = new StringBuffer();
		requestParams.append(URLEncoder.encode("html", "UTF-8"));
		requestParams.append("=").append(URLEncoder.encode(test, "UTF-8"));
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("orgId");
		requestParams.append("=").append(orgId);
		requestParams.append("&");
		requestParams.append("stabId");
		requestParams.append("=").append(stabId);
		requestParams.append("&");
		requestParams.append("altId");
		requestParams.append("=").append(altId);
		requestParams.append("&");
		requestParams.append("idCU");
		requestParams.append("=").append(idCU);
		requestParams.append("&");
		requestParams.append("ticketId");
		requestParams.append("=").append(ticketId);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("aggiungiGlifo");
		requestParams.append("=").append(glifo);
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("GISA");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("generazionePulita");
		requestParams.append("=").append(generazionePulita);
		requestParams.append("&");
		requestParams.append("statico");
		requestParams.append("=").append(statico);
		requestParams.append("&");
		requestParams.append("extra");
		requestParams.append("=").append(extra);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		//System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		System.out.println("[DOCUMENTALE GISA] CONNESSIONE: "+conn.toString());
		conn.getContentLength();
	
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();

		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				conn.disconnect();
			}
			return executeCommandDownloadPDF(context);
}


private String togliTagScript(String test)
{
	int inizio = test.indexOf("<script");
    int fine = test.indexOf("/script>")+8;
    while(inizio>0)
    {
    	
    	String toReplace = test.substring(inizio, fine);
    	test = test.replace(toReplace, "");
    	inizio = test.indexOf("<script");
	    fine = test.indexOf("/script>")+8;
    }
    
    return test;
	
}

}
