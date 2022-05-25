/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiTrasgressori;
import org.aspcfs.modules.opu.util.StabilimentoImportUtil;

import com.darkhorseventures.framework.actions.ActionContext;

public class PagoPaUtil {

	public static String fixDescrizioneErrore(String descrizioneErrore) {
		if (descrizioneErrore == null || descrizioneErrore.equals(""))
			return "ERRORE NELLA COOPERAZIONE APPLICATIVA. IL SERVIZIO POTREBBE NON ESSERE RAGGIUNGIBILE. SI PREGA DI RIPROVARE.";
		else
			return descrizioneErrore.replaceAll("'", " ").replaceAll("/", "").replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");
	}

	public static String calcolaDataScadenza(String dataPartenza, String tipoNotifica, String tipoPagamento,
			String tipoRiduzione, int numeroRata) throws ParseException {
		String dataScadenza = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = dateFormat.parse(dataPartenza);
		Timestamp dataPartenzaTimestamp = new java.sql.Timestamp(parsedDate.getTime());

		Calendar cal = Calendar.getInstance();
		cal.setTime(dataPartenzaTimestamp);

		if (tipoPagamento.equalsIgnoreCase("PV")) {
			if (tipoRiduzione.equalsIgnoreCase("U")) {
				cal.add(Calendar.DAY_OF_MONTH, 5);
			} else if (tipoRiduzione.equalsIgnoreCase("R")) {
				cal.add(Calendar.DAY_OF_MONTH, 60);
			}
		} else if (tipoPagamento.equalsIgnoreCase("NO")) {
			cal.add(Calendar.DAY_OF_MONTH, 30 * numeroRata);
		}

		Timestamp dataTimestampNew = new Timestamp(cal.getTime().getTime());
		dataScadenza = new SimpleDateFormat("yyyy-MM-dd").format(dataTimestampNew).toString();

		return dataScadenza;
	}

	public static void salvaStorico(Connection db, int idUtente, int idSanzione, int idPagamento, String operazione)
			throws SQLException {
		PreparedStatement pst = db
				.prepareStatement("insert into pagopa_storico(id_utente, id_sanzione, id_pagamento, operazione) values (?, ?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idUtente);
		pst.setInt(++i, idSanzione);
		pst.setInt(++i, idPagamento);
		pst.setString(++i, operazione);
		pst.executeUpdate();
	}

	public static void salvaStoricoOperazioniAutomatiche(Connection db, int idSanzione, int idPagamento,
			String vecchiaDataScadenza, String nuovaDataScadenza, String messaggio) throws SQLException {
		PreparedStatement pst = db
				.prepareStatement("insert into pagopa_storico_operazioni_automatiche(id_sanzione, id_pagamento, vecchia_data_scadenza, nuova_data_scadenza, messaggio) values (?, ?, ?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idSanzione);
		pst.setInt(++i, idPagamento);
		pst.setString(++i, vecchiaDataScadenza);
		pst.setString(++i, nuovaDataScadenza);
		pst.setString(++i, messaggio);
		pst.executeUpdate();
	}


	public static void inviaADocumentale(String localFile, String urlFile, int idSanzione, int userId) throws IOException {
		
		File file = null;
		String tipoAllegato = "";
		String oggettoAllegato = "";

		if (localFile!=null && !"".equals(localFile)) {
			
			file = new File(localFile);
			tipoAllegato = "ArchivioPagoPA";
			oggettoAllegato ="ARCHIVIO PAGOPA "+idSanzione;
			
		} else if (urlFile!=null && !"".equals(urlFile)){
			
			String iuv = urlFile.substring(urlFile.indexOf("iuv=")+4, urlFile.length());
			
			URL url = new URL(urlFile);
			
			String tDir = System.getProperty("java.io.tmpdir"); 
			String path = tDir + iuv + ".pdf"; 
			file = new File(path); 
			file.deleteOnExit(); 
			FileUtils.copyURLToFile(url, file);
			
			tipoAllegato = "AvvisoPagoPA";
			oggettoAllegato ="AVVISO PAGOPA "+idSanzione;

		}
		
		byte[] buffer = new byte[(int) file.length()];
		InputStream ios = null;
		try {
			ios = new FileInputStream(file);
			if (ios.read(buffer) == -1) {
				throw new IOException("EOF reached while trying to read the whole file");
			}
		} finally {
			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}

		byte[] ba = buffer;
		
		String fileName = file.getName();
		
		fileName = fileName.replaceAll("temp",  "");

		GestioneAllegatiTrasgressori gestioneAllegati = new GestioneAllegatiTrasgressori();
		gestioneAllegati.setTicketId(idSanzione);
		gestioneAllegati.setTipoAllegato(tipoAllegato);
		gestioneAllegati.setFilename(fileName);
		gestioneAllegati.setFileDimension(String.valueOf(file.length()));
		gestioneAllegati.setBa(ba);
		gestioneAllegati.setOggetto(oggettoAllegato);
		gestioneAllegati.setIdUtente(userId); 
		try {
			gestioneAllegati.chiamaServerDocumentale();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
}
