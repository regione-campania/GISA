/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.trasferimentosedestabilimento.actions;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class TrasferimentoSedeStabilimento extends CFSModule{

	Logger logger = Logger.getLogger(TrasferimentoSedeStabilimento.class);


	public String executeCommandAdd(ActionContext context)
	{
		
		if (!(hasPermission(context, "trasferimento_sede_stabilimento-add"))) {
			return ("PermissionError");
		}
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
		
			int id = -1;
			
			String idString = context.getRequest().getParameter("id");
			if (idString==null)
				idString = (String) context.getRequest().getAttribute("id");
			id = Integer.parseInt(idString);
			
			Stabilimento stab = new Stabilimento(db, id);
			context.getRequest().setAttribute("StabilimentoDettaglio", stab);
			context.getRequest().setAttribute("Operatore", stab.getOperatore());
		
			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.removeElementByCode(16);
			if (!(hasPermission(context, "trasferimento_sede_stabilimento_tutte_asl-view"))) {
				for (int i = 201; i<=207; i++){
					if (i!=stab.getIdAsl())
						aslList.removeElementByCode(i);
				}
			
			}
			context.getRequest().setAttribute("AslList", aslList);

			LookupList listaToponimi = new LookupList();
			listaToponimi.setTable("lookup_toponimi");
			listaToponimi.buildList(db);
			listaToponimi.setRequired(true);
			context.getRequest().setAttribute("ToponimiList", listaToponimi);

			LookupList ListaStati = new LookupList(db, "lookup_stato_lab");
			context.getRequest().setAttribute("ListaStati", ListaStati);
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return"addTrasferimentoOK";

	}
	
	public String executeCommandInsert(ActionContext context)
	{
		if (!(hasPermission(context, "trasferimento_sede_stabilimento-add"))) {
			return ("PermissionError");
		}
		
		Connection db = null ;
		
		int idStabilimentoNuovo = -1;
		int codiceUscita = -1;
		String messaggioUscita = "";
		String esitoTrasferimento = "";
		
		int idStabilimento =  Integer.parseInt(context.getRequest().getParameter("idStabilimento"));
		int idAsl =  Integer.parseInt(context.getRequest().getParameter("asl"));
		int idComune =  Integer.parseInt(context.getRequest().getParameter("comune"));
		int idToponimo =  Integer.parseInt(context.getRequest().getParameter("toponimo"));
		String via =  context.getRequest().getParameter("via");
		String civico =  context.getRequest().getParameter("civico");
		String cap =  context.getRequest().getParameter("cap");
		Double lat =  Double.parseDouble(context.getRequest().getParameter("lat"));
		Double lon =  Double.parseDouble(context.getRequest().getParameter("lon"));
		
		boolean generaNumeri = true;
		if (context.getRequest().getParameter("numeri")!=null && context.getRequest().getParameter("numeri").equals("vecchi"))
			generaNumeri = false;

		Stabilimento oldStab = null;
		Stabilimento newStab = null;
		
		try
		{
			db = this.getConnection(context);
			
			LookupList ListaStati = new LookupList(db, "lookup_stato_lab");
			context.getRequest().setAttribute("ListaStati", ListaStati);
			
			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);
			
			oldStab = new Stabilimento(db, idStabilimento);
			
			PreparedStatement pst = db.prepareStatement("select * from public_functions.trasferimento_sede_stabilimento(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			int i = 0;
			pst.setInt(++i, idStabilimento);
			pst.setInt(++i, idAsl);
			pst.setInt(++i, idComune);
			pst.setInt(++i, idToponimo);
			pst.setString(++i, via);
			pst.setString(++i, civico);
			pst.setString(++i,  cap);
			pst.setDouble(++i, lat);
			pst.setDouble(++i, lon);
			pst.setInt(++i, getUserId(context));
			pst.setBoolean(++i, generaNumeri);
			
			System.out.println("TRASFERIMENTO SEDE OPERATIVA QUERY: "+pst.toString());
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()){
				String esito = rs.getString(1);
				String codici[] = esito.split(";;");
				codiceUscita = Integer.parseInt(codici[0]);
				messaggioUscita = codici[1];
				idStabilimentoNuovo = Integer.parseInt(codici[2]);
			}
			
			esitoTrasferimento= messaggioUscita;

			if (codiceUscita == 1){
				oldStab = new Stabilimento(db, idStabilimento);
				newStab = new Stabilimento(db, idStabilimentoNuovo);
				
				if (oldStab.getIdAsl() != newStab.getIdAsl())
					invioMailTrasferimentoSedeStabilimento(db, context, oldStab, newStab, getUserId(context));
				
			}
			
			context.getRequest().setAttribute("newStabilimento", newStab);
			
			}
		catch(Exception e)
		{
			e.printStackTrace();
			esitoTrasferimento ="ERRORE";
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("oldStabilimento", oldStab);
		context.getRequest().setAttribute("esitoTrasferimento", esitoTrasferimento);
		return "InsertOK";
	}

	private void invioMailTrasferimentoSedeStabilimento(Connection db, ActionContext context, Stabilimento oldStab, Stabilimento newStab,
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
				//+ "Stabilimento: "+oldStab.getOperatore().getRagioneSociale()+ " (" + oldStab.getNumero_registrazione()+" -> "+newStab.getNumero_registrazione()+")<br/><br/>"
				//+ "Indirizzo pre trasferimento: "+oldStab.getSedeOperativa().getDescrizioneToponimo()+" "+oldStab.getSedeOperativa().getVia() + " " +oldStab.getSedeOperativa().getCivico()+","+ oldStab.getSedeOperativa().getDescrizioneComune()+"<br/>"
				//+ "Indirizzo post trasferimento: "+newStab.getSedeOperativa().getDescrizioneToponimo()+" "+newStab.getSedeOperativa().getVia() + " " +newStab.getSedeOperativa().getCivico()+","+ newStab.getSedeOperativa().getDescrizioneComune()+"<br/><br/>"
				//+ "ASL "+oldStab.getSedeOperativa().getDescrizioneAsl()+" -> "+newStab.getSedeOperativa().getDescrizioneAsl();
		
		String tabella= "<table style=\"border: 1px solid black; border-collapse: collapse\"><col width=\"20%\"><col width=\"40%\"><col width=\"40%\">"
				+ "<tr bgcolor=\"#d7e3f7\"><td align=\"center\" style=\"border:1px solid black;\"></td><td align=\"center\" style=\"border:1px solid black;\"><b>PRE TRASFERIMENTO</b></TD><td align=\"center\" style=\"border:1px solid black;\"><b>POST TRASFERIMENTO</b></td></tr>"
				+ "<tr><td align=\"center\" style=\"border:1px solid black;\" bgcolor=\"#d7e3f7\"><b>NUM REGISTRAZIONE</b></td><td align=\"center\" style=\"border:1px solid black;\">"+oldStab.getNumero_registrazione()+"</td><td align=\"center\" style=\"border:1px solid black;\">"+newStab.getNumero_registrazione()+"</td></tr>"
				+ "<tr><td align=\"center\" style=\"border:1px solid black;\" bgcolor=\"#d7e3f7\"><b>INDIRIZZO</b></td><td align=\"center\" style=\"border:1px solid black;\">"+oldStab.getSedeOperativa().getDescrizioneToponimo()+" "+oldStab.getSedeOperativa().getVia() + " " +oldStab.getSedeOperativa().getCivico()+","+ oldStab.getSedeOperativa().getDescrizioneComune()+"</td><td align=\"center\" style=\"border:1px solid black;\">"+ newStab.getSedeOperativa().getDescrizioneToponimo()+" "+newStab.getSedeOperativa().getVia() + " " +newStab.getSedeOperativa().getCivico()+","+ newStab.getSedeOperativa().getDescrizioneComune() +"</tr>"
				+ "<tr><td align=\"center\" style=\"border:1px solid black;\" bgcolor=\"#d7e3f7\"><b>ASL</b></td><td align=\"center\" style=\"border:1px solid black;\">"+oldStab.getSedeOperativa().getDescrizioneAsl()+"</td><td align=\"center\" style=\"border:1px solid black;\">"+newStab.getSedeOperativa().getDescrizioneAsl()+"</td></tr>"
				+ "</table>";
		
		File allegato = null;
		
		sendMailTrasferimento(context.getRequest(), testo+tabella, oggetto, toDest, allegato);

	}
	
	
	public static void sendMailTrasferimento(HttpServletRequest req,String testo,String object,String toDest, File allegato)
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
	
	public String executeCommandStorico(ActionContext context)
	{
		if (!(hasPermission(context, "trasferimento_sede_stabilimento_storico-view"))) {
			return ("PermissionError");
		}
		
		Connection db = null ;
		ArrayList<String> listaStorico = new ArrayList<String>();
		
		try
		{
			db = this.getConnection(context);
			
			
			PreparedStatement pst = db.prepareStatement("select * from opu_trasferimento_sede_storico order by data_trasferimento desc");
			ResultSet rs = pst.executeQuery();

			while (rs.next()){	
				String data = rs.getString("data_trasferimento");
				int idStabVecchio = rs.getInt("id_stabilimento_vecchio");
				int idStabNuovo = rs.getInt("id_stabilimento_nuovo");
				int idAslVecchia = rs.getInt("id_asl_vecchia");		
				int idAslNuova = rs.getInt("id_asl_nuova");		
				int idIndirizzoVecchio = rs.getInt("id_indirizzo_vecchio");
				int idIndirizzoNuovo = rs.getInt("id_indirizzo_nuovo");	
				int idUtente = rs.getInt("id_utente");	

				String ragioneSociale="";
				String partitaIva = "";
				String indirizzoVecchio="";
				String indirizzoNuovo=";;";
				
				Stabilimento oldStab = new Stabilimento(db, idStabVecchio);
				ragioneSociale = oldStab.getOperatore().getRagioneSociale();
				partitaIva = oldStab.getOperatore().getPartitaIva();
				
				Indirizzo oldIndirizzo = new Indirizzo(db, idIndirizzoVecchio);
				indirizzoVecchio = oldIndirizzo.getDescrizioneToponimo()+" "+oldIndirizzo.getVia()+" "+oldIndirizzo.getCivico()+", "+oldIndirizzo.getDescrizioneComune();
				
				Indirizzo newIndirizzo = new Indirizzo(db, idIndirizzoNuovo);
				indirizzoNuovo = newIndirizzo.getDescrizioneToponimo()+" "+newIndirizzo.getVia()+" "+newIndirizzo.getCivico()+", "+newIndirizzo.getDescrizioneComune();
				
				String res = ragioneSociale+";;"+partitaIva+";;"+data+";;"+ idStabVecchio+";;"+ idStabNuovo+";;"+ idAslVecchia+";;"+ idAslNuova+";;"+ indirizzoVecchio+";;"+ indirizzoNuovo+";;"+ idUtente;
				listaStorico.add(res);
			}
			
			context.getRequest().setAttribute("listaStorico", listaStorico);
	
			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);
		
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "StoricoOK";
	}
	
}
