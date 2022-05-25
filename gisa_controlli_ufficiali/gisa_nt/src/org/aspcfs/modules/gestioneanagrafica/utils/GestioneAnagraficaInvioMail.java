/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneAnagraficaInvioMail {

	public static void invioMailTrasferimentoSedeStabilimento(Connection db, Stabilimento oldStab, Stabilimento newStab,
			int userId) throws SQLException {

		User user = new User (db, userId);
		
		String toDest=ApplicationProperties.getProperty("DEST_EMAIL_TRASFERIMENTO_SEDE_OPERATIVA");
		String oggetto =ApplicationProperties.getProperty("OGGETTO_EMAIL_TRASFERIMENTO_SEDE_OPERATIVA");
		
		String emailAsl1 = ApplicationProperties.getProperty("PEC_ASL_"+oldStab.getIdAsl());
		String emailAsl2 = ApplicationProperties.getProperty("PEC_ASL_"+newStab.getIdAsl());
		
		toDest = toDest+";"+emailAsl1+";"+emailAsl2;
		
		String testo = "E' stato generato un nuovo trasferimento di sede operativa.<br/><br/>"
				+ "Richiedente: "+ user.getContact().getNameFirst() + " "+ user.getContact().getNameLast() + " ("+user.getUsername()+") "+"<br/>"
				+ "Ragione sociale: "+oldStab.getOperatore().getRagioneSociale()+"<br/><br/>";
			
		String tabella= "<table style=\"border: 1px solid black; border-collapse: collapse\"><col width=\"20%\"><col width=\"40%\"><col width=\"40%\">"
				+ "<tr bgcolor=\"#d7e3f7\"><td align=\"center\" style=\"border:1px solid black;\"></td><td align=\"center\" style=\"border:1px solid black;\"><b>PRE TRASFERIMENTO</b></TD><td align=\"center\" style=\"border:1px solid black;\"><b>POST TRASFERIMENTO</b></td></tr>"
				+ "<tr><td align=\"center\" style=\"border:1px solid black;\" bgcolor=\"#d7e3f7\"><b>NUM REGISTRAZIONE</b></td><td align=\"center\" style=\"border:1px solid black;\">"+oldStab.getNumero_registrazione()+"</td><td align=\"center\" style=\"border:1px solid black;\">"+newStab.getNumero_registrazione()+"</td></tr>"
				+ "<tr><td align=\"center\" style=\"border:1px solid black;\" bgcolor=\"#d7e3f7\"><b>INDIRIZZO</b></td><td align=\"center\" style=\"border:1px solid black;\">"+oldStab.getSedeOperativa().getDescrizioneToponimo()+" "+oldStab.getSedeOperativa().getVia() + " " +oldStab.getSedeOperativa().getCivico()+","+ oldStab.getSedeOperativa().getDescrizioneComune()+"</td><td align=\"center\" style=\"border:1px solid black;\">"+ newStab.getSedeOperativa().getDescrizioneToponimo()+" "+newStab.getSedeOperativa().getVia() + " " +newStab.getSedeOperativa().getCivico()+","+ newStab.getSedeOperativa().getDescrizioneComune() +"</tr>"
				+ "<tr><td align=\"center\" style=\"border:1px solid black;\" bgcolor=\"#d7e3f7\"><b>ASL</b></td><td align=\"center\" style=\"border:1px solid black;\">"+oldStab.getSedeOperativa().getDescrizioneAsl()+"</td><td align=\"center\" style=\"border:1px solid black;\">"+newStab.getSedeOperativa().getDescrizioneAsl()+"</td></tr>"
				+ "</table>";
		
		File allegato = null;
		
		sendMailTrasferimento(testo+tabella, oggetto, toDest, allegato);

	}
	
	
	public static void invioMailCambioSedeLegaleOperatore(Connection db, String ragione_sociale, String oldAslid, String oldAslDesc,
			String oldIndirizzoSedeLegale, String num_registrazione, String newAslid, String newAslDesc, String newIndirizzoSedeLegale, int userId) throws SQLException {
		
		User user = new User (db, userId);
		
		String toDest=ApplicationProperties.getProperty("DEST_EMAIL_CAMBIO_SEDE_LEGALE");
		String oggetto =ApplicationProperties.getProperty("OGGETTO_EMAIL_CAMBIO_SEDE_LEGALE");
		
		String emailAsl1 = ApplicationProperties.getProperty("PEC_ASL_"+oldAslid);
		String emailAsl2 = ApplicationProperties.getProperty("PEC_ASL_"+newAslid);
		
		toDest = toDest+";"+emailAsl1+";"+emailAsl2;
		
		String testo = "E' stato generato un nuovo cambio di sede legale.<br/><br/>"
				+ "Richiedente: "+ user.getContact().getNameFirst() + " "+ user.getContact().getNameLast() + " ("+user.getUsername()+") "+"<br/>"
				+ "Ragione sociale: "+ragione_sociale+"<br/><br/>";
				//+ "Stabilimento: "+oldStab.getOperatore().getRagioneSociale()+ " (" + oldStab.getNumero_registrazione()+" -> "+newStab.getNumero_registrazione()+")<br/><br/>"
				//+ "Indirizzo pre trasferimento: "+oldStab.getOperatore().getSedeLegale().getDescrizioneToponimo()+" "+oldStab.getOperatore().getSedeLegale().getVia() + " " +oldStab.getOperatore().getSedeLegale().getCivico()+","+ oldStab.getOperatore().getSedeLegale().getDescrizioneComune()+"<br/>"
				//+ "Indirizzo post trasferimento: "+newStab.getOperatore().getSedeLegale().getDescrizioneToponimo()+" "+newStab.getOperatore().getSedeLegale().getVia() + " " +newStab.getOperatore().getSedeLegale().getCivico()+","+ newStab.getOperatore().getSedeLegale().getDescrizioneComune()+"<br/><br/>"
				//+ "ASL "+oldStab.getOperatore().getSedeLegale().getDescrizioneAsl()+" -> "+newStab.getOperatore().getSedeLegale().getDescrizioneAsl();
		
		String tabella= "<table style=\"border: 1px solid black; border-collapse: collapse\"><col width=\"20%\"><col width=\"40%\"><col width=\"40%\">"
				+ "<tr bgcolor=\"#d7e3f7\"><td align=\"center\" style=\"border:1px solid black;\"></td><td align=\"center\" style=\"border:1px solid black;\"><b>PRE MODIFICA</b></TD><td align=\"center\" style=\"border:1px solid black;\"><b>POST MODIFICA</b></td></tr>"
				+ "<tr><td align=\"center\" style=\"border:1px solid black;\" bgcolor=\"#d7e3f7\"><b>NUM REGISTRAZIONE</b></td><td align=\"center\" style=\"border:1px solid black;\">"+num_registrazione+"</td><td align=\"center\" style=\"border:1px solid black;\">"+num_registrazione+"</td></tr>"
				+ "<tr><td align=\"center\" style=\"border:1px solid black;\" bgcolor=\"#d7e3f7\"><b>INDIRIZZO</b></td><td align=\"center\" style=\"border:1px solid black;\">"+oldIndirizzoSedeLegale+"</td><td align=\"center\" style=\"border:1px solid black;\">"+newIndirizzoSedeLegale+"</tr>"
				+ "<tr><td align=\"center\" style=\"border:1px solid black;\" bgcolor=\"#d7e3f7\"><b>ASL</b></td><td align=\"center\" style=\"border:1px solid black;\">"+oldAslDesc+"</td><td align=\"center\" style=\"border:1px solid black;\">"+newAslDesc+"</td></tr>"
				+ "</table>";
		
		File allegato = null;
		
		sendMailTrasferimento(testo+tabella, oggetto, toDest, allegato);
	}
	
	
	public static void sendMailTrasferimento(String testo,String object,String toDest, File allegato)
	{
		String[] toDestArray = toDest.split(";");
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
		try {
			sender.sendMailConAllegato(object,testo,"gisasuap@cert.izsmportici.it", toDestArray, allegato);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
