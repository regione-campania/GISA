/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiesteerratacorrige.actions;

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

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.richiesteerratacorrige.base.RichiestaErrataCorrige;
import org.aspcfs.modules.richiesteerratacorrige.base.RichiestaErrataCorrigeCampo;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.PecMailSender;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneRichiesteErrataCorrige extends CFSModule {
	
	
	public String executeCommandGestioneRichiesteErrataCorrige(ActionContext context) throws IndirizzoNotFoundException{
		

		int riferimentoId = -1;
		String riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");

		String riferimentoIdString = context.getRequest().getParameter("riferimentoId");
		if (riferimentoIdString==null)
			riferimentoIdString = (String) context.getRequest().getAttribute("riferimentoId");
		
		try {riferimentoId = Integer.parseInt(riferimentoIdString);} catch (Exception e){}
		
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			ArrayList<RichiestaErrataCorrige> listaErrataCorrige = new ArrayList<RichiestaErrataCorrige>();
			
			String sql="select * from richieste_errata_corrige where 1=1 and riferimento_id = ? and riferimento_id_nome_tab = ? order by data desc";
			
			PreparedStatement pst = db.prepareStatement(sql);
			
				pst.setInt(1, riferimentoId);
				pst.setString(2, riferimentoIdNomeTab);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				RichiestaErrataCorrige richiesta = new RichiestaErrataCorrige(rs);
				richiesta.setCampi(db);
				
				if (richiesta.getIdUtente() == getUserId(context) || hasPermission(context, "richieste_errata_corrige-edit"))
					listaErrataCorrige.add(richiesta);
			}

			Animale animale = new Animale(db, riferimentoId);

			context.getRequest().setAttribute("listaErrataCorrige", listaErrataCorrige);
			context.getRequest().setAttribute("Animale", animale);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
		return "ListaErrataCorrigeOK";
	}

	public String executeCommandNuovaRichiestaErrataCorrige(ActionContext context) throws IndirizzoNotFoundException{

		int riferimentoId = -1;
		
		String riferimentoIdString = context.getRequest().getParameter("riferimentoId");
		String riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");
	
		try {riferimentoId = Integer.parseInt(riferimentoIdString);} catch (Exception e){}

		
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			LookupList LookupTipoInformazione = new LookupList(db,"richieste_errata_corrige_lookup_info_da_modificare");
			context.getRequest().setAttribute("LookupTipoInformazione", LookupTipoInformazione);
			LookupList LookupTipoMotivoCorrezione = new LookupList(db,"richieste_errata_corrige_lookup_motivo_correzione");
			context.getRequest().setAttribute("LookupMotivoCorrezione", LookupTipoMotivoCorrezione);
			
			Animale animale = new Animale(db, riferimentoId);

			context.getRequest().setAttribute("Animale", animale);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
		return "NuovaErrataCorrigeOK";
	}

	public String executeCommandSalvaErrataCorrige(ActionContext context) throws IndirizzoNotFoundException{

		int riferimentoId = -1;
					
		String riferimentoIdString = context.getRequest().getParameter("riferimentoId");
		String riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");
		
		riferimentoId = Integer.parseInt(riferimentoIdString);
		
		int idUtente = getUserId(context);
		String note = context.getRequest().getParameter("note");
		String telefono = context.getRequest().getParameter("telefono");
		String mail = context.getRequest().getParameter("mail");
		String motivo = context.getRequest().getParameter("motivoCorrezione");

		
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			Animale animale = new Animale(db, riferimentoId);
	
			RichiestaErrataCorrige richiesta = new RichiestaErrataCorrige();
			richiesta.setRiferimentoId(riferimentoId);
			richiesta.setRiferimentoIdNomeTab(riferimentoIdNomeTab);
			richiesta.setIdAsl(animale.getIdAslRiferimento());
			richiesta.setIdUtente(idUtente);
			richiesta.setIdLookupMotivoCorrezione(motivo);
			richiesta.setTelefono(telefono);
			richiesta.setMail(mail);
			richiesta.setNote(note);
			richiesta.insert(db);
			
			for (int i = 0; i<5; i++){
				RichiestaErrataCorrigeCampo campo = new RichiestaErrataCorrigeCampo();
				campo.setIdErrataCorrige(richiesta.getId());
				campo.setIdLookupInfoDaModificare(Integer.parseInt(context.getRequest().getParameter("tipoInformazione"+i)));
				campo.setDatoErrato(context.getRequest().getParameter("datoErrato"+i));
				campo.setDatoCorretto(context.getRequest().getParameter("datoCorretto"+i));
				campo.insert(db);
			}
			
			//context.getRequest().setAttribute("RichiestaAppenaInserita", richiesta);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("riferimentoId", String.valueOf(riferimentoId));
		
		return executeCommandGestioneRichiesteErrataCorrige(context);
	}


	public String executeCommandModuloRichiestaErrataCorrige(ActionContext context) throws IndirizzoNotFoundException{

		int id = Integer.parseInt(context.getRequest().getParameter("id"));
			
		Connection db = null;
		
		try {
			db = this.getConnection(context);
		
			RichiestaErrataCorrige richiesta = new RichiestaErrataCorrige(db, id);
			context.getRequest().setAttribute("Richiesta", richiesta);
			
			LookupList LookupTipoInformazione = new LookupList(db,"richieste_errata_corrige_lookup_info_da_modificare");
			context.getRequest().setAttribute("LookupTipoInformazione", LookupTipoInformazione);
			LookupList LookupTipoMotivoCorrezione = new LookupList(db,"richieste_errata_corrige_lookup_motivo_correzione");
			context.getRequest().setAttribute("LookupMotivoCorrezione", LookupTipoMotivoCorrezione);
		
			Animale animale = new Animale(db, richiesta.getRiferimentoId());
			context.getRequest().setAttribute("Animale", animale);
			
			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			context.getRequest().setAttribute("SpecieList", specieList);
			
			User user = new User(db, richiesta.getIdUtente());
			context.getRequest().setAttribute("Utente", user);
			
			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
		return "ModuloRichiestaErrataCorrigeOK";
	}

	public static void associaHeaderEInviaMail(ActionContext context, Connection db, int id, String headerDocumento, String file) throws SQLException, IndirizzoNotFoundException {
		RichiestaErrataCorrige richiesta = new RichiestaErrataCorrige(db, id);
		
		if (richiesta.getHeaderDocumento()==null) {
			richiesta.setHeaderDocumento(headerDocumento);
			richiesta.updateHeaderDocumento(db);
			
			User user = new User (db, richiesta.getIdUtente());
			
			Animale animale = new Animale(db, richiesta.getRiferimentoId());
					
			String toDest=ApplicationProperties.getProperty("DEST_EMAIL_ERRATA_CORRIGE");
			String oggetto =ApplicationProperties.getProperty("OGGETTO_EMAIL_ERRATA_CORRIGE");
		
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String testo = "E' stata generata una nuova richiesta di Errata Corrige.<br/><br/>"
					+ "Richiedente: "+ user.getContact().getNameFirst() + " "+ user.getContact().getNameLast() + " ("+user.getUsername()+") "+"<br/>"
					+ "Num. telefono: "+richiesta.getTelefono()+" Mail: "+richiesta.getMail()+" <br/>"
					+ "Animale: "+animale.getMicrochip()+"<br/>"
					+ "Data: "+ dateFormat.format(richiesta.getData());
			
			File allegato = new File(file);
			
			sendMailErrataCorrige(context.getRequest(), testo, oggetto, toDest, allegato);
		}
		
	}


	public static void sendMailErrataCorrige(HttpServletRequest req,String testo,String object,String toDest, File allegato)
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
