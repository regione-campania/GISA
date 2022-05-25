/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.documentale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.action.Action;
import it.us.web.action.GenericAction;
import it.us.web.action.vam.accettazione.Detail;
import it.us.web.action.vam.accettazione.StampaAccettazione;
import it.us.web.action.vam.accettazione.StampaCertificatoDecesso;
import it.us.web.action.vam.accettazione.StampaMod13A;
import it.us.web.action.vam.accettazione.StampaVerbaleAccompagnamentoCampioni;
import it.us.web.action.vam.accettazioneMultipla.StampaAccettazioni;
import it.us.web.action.vam.cc.StampaDetail;
import it.us.web.action.vam.cc.autopsie.StampaReferto;
import it.us.web.action.vam.cc.autopsie.StampaRichiesta;
import it.us.web.action.vam.cc.autopsie.StampaVerbalePrelievo;
import it.us.web.action.vam.cc.esamiIstopatologici.StampaIstoMultiplo;
import it.us.web.action.vam.cc.esamiIstopatologici.StampaIstoSingolo;
import it.us.web.action.vam.richiesteIstopatologici.StampaIstoSingoloLP;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.json.JSONObject;
import it.us.web.util.properties.Application;

public class DownloadNewPdf extends GenericAction {

	public void can() throws AuthorizationException {
	}

	@Override
	public void setSegnalibroDocumentazione() {
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception {
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);

		
		
		
		String html = "";
		String tipo = stringaFromRequest("tipo");
		Integer idAnimale = -1;
		Animale animale = null;
		Integer id = -1;
		Integer idEsame = interoFromRequest("idEsame");
		String microchip = stringaFromRequest("microchip");
		String glifo = stringaFromRequest("glifo");

		String url = "http://" + req.getLocalAddr() + ":" + req.getLocalPort() + req.getContextPath() + "/";
		String caller = url;
		System.out.println("[DOCUMENTALE VAM] urlChiamante: " + caller);
		String urlOriginale = "";
		String ip = req.getRemoteAddr();
		int idUser = -1;
		if (utente != null)
			idUser = utente.getId();
		Action action = null;

		if (tipo.equals("stampaCc")) {
			animale = cc.getAccettazione().getAnimale();
			id = cc.getId();
			String divDiario = stringaFromRequest("divDiario");
			String divAnamnesi = stringaFromRequest("divAnamnesi");
			String divEsameObiettivoParticolare = stringaFromRequest("divEsameObiettivoParticolare");
			String divEsameObiettivoGenerale = stringaFromRequest("divEsameObiettivoGenerale");
			String divRic = stringaFromRequest("divRic");
			String divDiagn = stringaFromRequest("divDiagn");
			String divDimis = stringaFromRequest("divDimis");
			String divChir = stringaFromRequest("divChir");
			String divTerap = stringaFromRequest("divTerap");
			String divTrasf = stringaFromRequest("divTrasf");
			String divList = stringaFromRequest("divList");
			String divListUrine = stringaFromRequest("divListUrine");
			String divListCoprologico = stringaFromRequest("divListCoprologico");
			String divListDiagnosticaImmagini = stringaFromRequest("divListDiagnosticaImmagini");
			String divListEsterni = stringaFromRequest("divListEsterni");
			String divListECG = stringaFromRequest("divListECG");
			String divListCitologico = stringaFromRequest("divListCitologico");
			String divListIsto = stringaFromRequest("divListIsto");
			String divListNecro = stringaFromRequest("divListNecro");

			url += "vam.cc.StampaDetail.us?divDiario=" + divDiario + "&divAnamnesi=" + divAnamnesi
					+ "&divEsameObiettivoGenerale=" + divEsameObiettivoGenerale + "&divEsameObiettivoParticolare="
					+ divEsameObiettivoParticolare + "&divRic=" + divRic + "&divDiagn=" + divDiagn + "&divDimis="
					+ divDimis + "&divChir=" + divChir + "&divTerap=" + divTerap + "&divTrasf=" + divTrasf
					+ "&divList=" + divList + "&divListUrine=" + divListUrine + "&divListCoprologico="
					+ divListCoprologico + "&divListDiagnosticaImmagini=" + divListDiagnosticaImmagini
					+ "&divListEsterni=" + divListEsterni + "&divListECG=" + divListECG + "&divListCitologico="
					+ divListCitologico + "&divListIsto=" + divListIsto + "&divListNecro=" + divListNecro + "&idCc="
					+ id;
			action = new StampaDetail();
		} else if (tipo.equals("stampaAcc")) {
			Accettazione accettazione = AccettazioneDAO.getAccettazione(interoFromRequest("idAccettazione"),connection);//(Accettazione) persistence.find(Accettazione.class,interoFromRequest("idAccettazione"));
			animale = accettazione.getAnimale();
			id = accettazione.getId();
			url += "vam.accettazione.StampaAccettazione.us?id=" + id;
			action = new StampaAccettazione();
		} 
		else if (tipo.equals("stampaAccMultipla")) {
			String idAccMultipla = stringaFromRequest("idAccMultipla");
			url += "vam.accettazioneMultipla.StampaAccettazioni.us?idAccMultipla=" + idAccMultipla;
			action = new StampaAccettazioni();
		}
		else if (tipo.equals("stampaMod13A")) {
			String idAcc = stringaFromRequest("idAcc");
			url += "vam.accettazione.StampaMod13A.us?idAcc=" + idAcc;
			action = new StampaMod13A();
		}
		else if (tipo.equals("stampaIstoMultiplo")) {
			animale = cc.getAccettazione().getAnimale();
			id = cc.getId();
			url += "vam.cc.esamiIstopatologici.StampaIstoMultiplo.us?id=" + id;
			action = new StampaIstoMultiplo();
		} else if (tipo.equals("stampaIstoSingolo")) {
			if(cc!=null)
			{
				animale = cc.getAccettazione().getAnimale();
				id = cc.getId();
			}
			url += "vam.cc.esamiIstopatologici.StampaIstoSingolo.us?id=" + idEsame;
			action = new StampaIstoSingolo();
		} else if (tipo.equals("stampaIstoSingoloLP")) {
			url += "vam.richiesteIstopatologici.StampaIstoSingoloLP.us?id=" + idEsame;
			action = new StampaIstoSingoloLP();
		} else if (tipo.equals("stampaDecesso")) {
			Accettazione accettazione = AccettazioneDAO.getAccettazione(interoFromRequest("idAccettazione"),connection);
			
			animale = accettazione.getAnimale();
			id = accettazione.getId();
			url += "vam.accettazione.StampaCertificatoDecesso.us?id=" + id;
			action = new StampaCertificatoDecesso();
		} else if (tipo.equals("stampaRefertoNecro")) {
			animale = cc.getAccettazione().getAnimale();
			id = cc.getId();
			url += "vam.cc.autopsie.StampaReferto.us?id=" + idEsame;
			action = new StampaReferto();
		} else if (tipo.equals("stampaVerbalePrelievo")) {
			animale = cc.getAccettazione().getAnimale();
			id = cc.getId();
			url += "vam.cc.autopsie.StampaVerbalePrelievo.us?idAutopsia=" + idEsame;
			action = new StampaVerbalePrelievo();
		} 
		else if (tipo.equals("verbaleAccompagnamentoCampioni")) {
			Accettazione accettazione = AccettazioneDAO.getAccettazione(idEsame,connection);
			animale = accettazione.getAnimale();
			id = idEsame;
			url += "vam.accettazione.StampaVerbaleAccompagnamentoCampioni.us?idAccettazione=" + idEsame;
			action = new StampaVerbaleAccompagnamentoCampioni();
		}
		else if (tipo.equals("stampaRichiesta")) {
			animale = cc.getAccettazione().getAnimale();
			id = cc.getId();
			url += "vam.cc.autopsie.StampaRichiesta.us?id=" + idEsame;
			action = new StampaRichiesta();
		}

		if (animale != null) {
			idAnimale = animale.getId();
			microchip = animale.getIdentificativo();
		}

		// inizializzo la variabile glifo nel caso sia null
		// glifo valorizzata: aggiungi timbro
		boolean glifoBool = false;
		if (glifo == null || glifo.equals("") || glifo.equals("false")) {
			glifo = "";
		} else {
			glifo = "glifo";
			glifoBool = true;
		}

		System.out.println("[DOCUMENTALE VAM] Url da recuperare: " + url);

		// html = getHtml(url, session.getId());
		urlOriginale = url;
		// if (html==null || html.equals(""))
		// {
		// setErrore("Fallita cattura della pagina d'origine.");
		// if(tipo.equals("stampaAcc"))
		// req.setAttribute("id", id);
		// redirectTo(url, req, res);
		// //goToAction(action, req, res);
		// }

		// String md5string = getMd5(html);

		// //modifico il css
		// html=html.replaceAll("print.css", "print_timbro.css");
		//
		// //Per qualche motivo demo non riesce a collegarsi al proprio
		// indirizzo pubblico, pertanto
		// //è necessario sostituire i link dei CSS per farglieli leggere
		// html =
		// html.replaceAll(InetAddress.getByName("hostAppVamPublic").getHostAddress(),InetAddress.getByName("hostAppVam").getHostAddress());
		//

		// gestisco lettere accentate non lette dal gestoreGlifo
		// html = gestioneCaratteriSpeciali(html);
		// System.out.println("[DOCUMENTALE VAM] Gestiti caratteri speciali.");

		Boolean documentaleDisponibile = Boolean.valueOf(Application.get("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = Application.get("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) {
			setErrore(documentaleNonDisponibileMessaggio);
			gotoPage("jsp/documentale/error.jsp");
		}

		else {

			try {
				downloadPdf(html, idAnimale, microchip, id, glifo, idUser, ip, tipo, urlOriginale, caller,
						session.getId());
			} catch (Exception e) {
				e.printStackTrace();
				setErrore("Errore durante il download del file.");
				if (tipo.equals("stampaAcc"))
					req.setAttribute("id", id);
				redirectTo(url, req, res);
				// goToAction(action, req, res);
			}

		}
	}

	private void downloadPdf(String html, Integer idAnimale, String microchip, Integer id, String glifo, int idUser,
			String ip, String tipo, String urlOriginale, String caller, String idSessione) throws Exception {
		String url = "http://srvDOCUMENTALEL:"+ Application.get("DOCUMENTALE_PORT") + "/"
				+ Application.get("DOCUMENTALE_APPLICATION_NAME") + "/newCodificaServlet";
		System.out.println("[DOCUMENTALE VAM] Connessione a: " + url);

		URL obj = new URL(url);

		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();

		requestParams.append("idAnimale_VAM");
		requestParams.append("=").append(idAnimale);
		requestParams.append("&");
		requestParams.append("microchip_VAM");
		requestParams.append("=").append(microchip);
		requestParams.append("&");
		if (tipo.equals("stampaCc"))
			requestParams.append("cc");
		else if (tipo.equals("stampaAcc") || tipo.equals("stampaDecesso"))
			requestParams.append("acc");
		else
			requestParams.append("cc");
		requestParams.append("=").append(id);
		requestParams.append("&");
		requestParams.append("app_name=vam");
		requestParams.append("&");
		requestParams.append("aggiungiGlifo");
		requestParams.append("=").append(glifo);
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append("vam");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(idUser);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(idSessione);
		requestParams.append("&");
		requestParams.append("urlOriginale");
		requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(caller, "ISO-8859-1"));
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE VAM] Conn: " + conn.toString());
		conn.getContentLength();
		System.out.println("[DOCUMENTALE VAM] Fine conn");
		String codDocumento = "", titolo = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		// String inputLine;
		StringBuffer result = new StringBuffer();

		// Leggo l'output: l'header del documento generato e il nome
		// assegnatogli
		// Se il documento è già stato generato da meno di 2 minuti, viene
		// restituito il vecchio
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto);
		}
		in.close();
		JSONObject jo = new JSONObject(result.toString());
		codDocumento = jo.get("codDocumento").toString();
		titolo = jo.get("titolo").toString();
		req.setAttribute("codDocumento", codDocumento);
		req.setAttribute("titolo", titolo);
		goToAction(new DownloadPdf());
	}

	// private String gestioneCaratteriSpeciali(String test){
	//
	// test=test.replaceAll("à", "###agrave###");
	// test=test.replaceAll("è", "###egrave###");
	// test=test.replaceAll("ì", "###igrave###");
	// test=test.replaceAll("ò", "###ograve###");
	// test=test.replaceAll("ù", "###ugrave###");
	//
	// test=test.replaceAll("á", "###aacute###");
	// test=test.replaceAll("é", "###eacute###");
	// test=test.replaceAll("í", "###iacute###");
	// test=test.replaceAll("ó", "###oacute###");
	// test=test.replaceAll("ú", "###uacute###");
	//
	// test=test.replaceAll("À", "###amaiuscgrave###");
	// test=test.replaceAll("È", "###emaiuscgrave###");
	// test=test.replaceAll("Ì", "###imaiuscgrave###");
	// test=test.replaceAll("Ò", "###omaiuscgrave###");
	// test=test.replaceAll("Ù", "###umaiuscgrave###");
	//
	// test=test.replaceAll("Á", "###amaiuscacute###");
	// test=test.replaceAll("É", "###emaiuscacute###");
	// test=test.replaceAll("í", "###imaiuscacute###");
	// test=test.replaceAll("Ó", "###omaiuscacute###");
	// test=test.replaceAll("Ú", "###umaiuscacute###");
	//
	// test=test.replaceAll("°", "###grado###");
	// test=test.replaceAll("µ", "###mu###");
	//
	// return test;
	// }

}
