/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.report.util;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;


import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.osm.base.SottoAttivita;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.AcroFields;
//import com.itextpdf.text.pdf.Barcode128;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.lowagie.text.DocumentException;
import com.zeroio.webutils.FileDownload;

public class SchedaPrint extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");

/**
 * Gestione delle stampe nel modulo Imprese 
 *
 * @param context
 * 
 */

public String executeCommandStampaScheda(ActionContext context) {
	if (!hasPermission(context, "requestor-requestor-view")
			&& !hasPermission(context, "requestor-requestor-report-view")) {
		return ("PermissionError");
	}
	String id="", file="";
	
	ArrayList<String> locali_collegati = new ArrayList<String>();
	
	locali_collegati.add((String) context.getRequest().getParameter("addressid"));
	locali_collegati.add((String) context.getRequest().getParameter("addressid2"));
	locali_collegati.add((String) context.getRequest().getParameter("addressid3"));

	context.getRequest().setAttribute("localiCollegati", locali_collegati);
	
	Connection db = null;
	try {
		
		db = this.getConnection(context);
		
		id = (String) context.getRequest().getParameter("id");
		file = (String) context.getRequest().getParameter("file");
		
		Map<String, Object> formMap = new HashMap<String, Object>();

		// preparo ed eseguo la query
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "";
		SchedaImpresa schedaDetails = new SchedaImpresa();
		
		//recupero la query dal file di properties queryreports.properties
		if (file.equals("account.xml"))
			query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_impresa");
		else if (file.equals("account_attivitaMobili.xml"))
			query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_mobile");
		else if (file.equals("modelloC.xml"))
			query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("num_attivita");
		else if (file.equals("modelloCOpu.xml"))
			query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("num_attivita_opu");
		 else if (file.equals("modelloCattivitamobile.xml"))
			query =org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("num_attivita_mobile");
		 else if (file.equals("modelloCessazione.xml"))
				query =org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("num_attivita");
		 else if (file.equals("modelloCessazioneOpu.xml"))
				query =org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("num_attivita_opu");
		 else if (file.equals("modelloCessazioneMobili.xml"))
				query =org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("num_attivita_mobile");
		 else if(file.equals("stabilimenti"))
			query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_stabilimento");
		 else if(file.equals("soa"))
			query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_soa");
		 else if(file.equals("imbarcazioni"))
				query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_imbarcazioni");
		 else if(file.equals("osm") || file.equals("osmregistrati"))
				query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_osm");
		 else if(file.equals("molluschi"))
				query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_molluschi");
		 else if(file.equals("canili"))
				query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_canili");
		 else if(file.equals("colonie"))
				query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_colonie");
		 else if(file.equals("operatoricommerciali"))
				query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_operatoricommerciali");
		 else if(file.equals("aziendeagricole"))
				query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("scheda_aziendeagricole");
		
		
		pst = db.prepareStatement(query);
		
		if (file.equals("account.xml") || file.equals("account_attivitaMobili.xml") || file.equals("modelloC.xml")|| file.equals("modelloCattivitamobile.xml")|| file.equals("modelloCessazione.xml") || file.equals("modelloCessazioneMobili.xml")){
		pst.setInt(1, Integer.parseInt(locali_collegati.get(0)));
		pst.setInt(2, Integer.parseInt(locali_collegati.get(1)));
		pst.setInt(3, Integer.parseInt(locali_collegati.get(2)));
		pst.setInt(4,  Integer.parseInt(id));
		}
		else{
			pst.setInt(1,  Integer.parseInt(id));
		}
		System.out.println("Stampa scheda: "+file+" ; Query: "+pst.toString());
		rs = pst.executeQuery();
		if (rs.next()) {
			schedaDetails.buildRecord(rs);
		}
		rs.close();
		pst.close();
		context.getRequest().setAttribute("schedaDetails", schedaDetails);
		
		ArrayList<LineeAttivita> linee_attivita_secondarie  = null;
		if (file.equals("modelloCOpu.xml") || file.equals("modelloCessazioneOpu.xml"))
			 linee_attivita_secondarie = LineeAttivita.load_linee_attivita_secondarie_per_stab_id(id, db);
		else
			 linee_attivita_secondarie = LineeAttivita.load_linee_attivita_secondarie_per_org_id(id, db);
		
		context.getRequest().setAttribute("linee_attivita_secondarie",linee_attivita_secondarie);
		
		if (file.equals("stabilimenti")){
			ArrayList elencoAttivita =  SottoAttivita.loadByStabilimento(Integer.parseInt(id), db);
			context.getRequest().setAttribute("linee_attivita",elencoAttivita);
			
			LookupList categoriaList = new LookupList(db, "lookup_categoria");
		      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("categoriaList", categoriaList);
			
			
			String statoPratica="COMPLETATO";
		LookupList statiStabilimenti = new LookupList(db,
				"lookup_stati_stabilimenti");
		
		org.aspcfs.modules.stabilimenti.base.Organization OrgDetails = new org.aspcfs.modules.stabilimenti.base.Organization(db, Integer.parseInt((id)));
		statoPratica = statiStabilimenti.getSelectedValue(OrgDetails.getStatoIstruttoria());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (OrgDetails.getStatoIstruttoria()==7)
		{
			Timestamp data_assegnazione = OrgDetails.getDataScadenzaNumero();
			if(data_assegnazione!=null)
			{
			data_assegnazione.setMonth(data_assegnazione.getMonth()+3);
			statoPratica+=" Scadenza Pratica : "+sdf.format(new Date(data_assegnazione.getTime()));
			}
		}
		else if (OrgDetails.getStatoIstruttoria()==9)
		{
			Timestamp data_assegnazione = OrgDetails.getDataScadenzaNumero();
			if(data_assegnazione!=null)
			{
			
			data_assegnazione.setMonth(data_assegnazione.getMonth()+6);
			statoPratica+=" Scadenza Pratica : "+sdf.format(new Date(data_assegnazione.getTime()));
			}
		}
		
		
		
		
		context.getRequest().setAttribute("statoPratica", statoPratica);
		
		
		String indirizzoTitolare = "";
		indirizzoTitolare = OrgDetails.getAddress_legale_rapp()+" "+ OrgDetails.getCity_legale_rapp()+" "+OrgDetails.getProv_legale_rapp();
		context.getRequest().setAttribute("indirizzoTitolare", indirizzoTitolare);
		
		}
		

		if (file.equals("soa")){
			org.aspcfs.modules.soa.base.Organization orgSoa = new org.aspcfs.modules.soa.base.Organization(db, Integer.parseInt((id)));
			context.getRequest().setAttribute("OrgSoa", orgSoa);
		}
		if (file.equals("imbarcazioni")){
			org.aspcfs.modules.imbarcazioni.base.Organization orgImbarcazioni = new org.aspcfs.modules.imbarcazioni.base.Organization(db, Integer.parseInt((id)));
			context.getRequest().setAttribute("OrgImbarcazioni", orgImbarcazioni);
		}
		if (file.equals("osmregistrati")){
			org.aspcfs.modules.osmregistrati.base.Organization OrgOSM = new org.aspcfs.modules.osmregistrati.base.Organization(db, Integer.parseInt((id)));
			context.getRequest().setAttribute("OrgOSM", OrgOSM);
		}
		if (file.equals("molluschi")){
			org.aspcfs.modules.molluschibivalvi.base.Organization OrgMolluschi = new org.aspcfs.modules.molluschibivalvi.base.Organization(db, Integer.parseInt((id)));
			context.getRequest().setAttribute("OrgMolluschi", OrgMolluschi);
		}
		
		if (file.equals("canili")){
			org.aspcfs.modules.canili.base.Organization OrgCanili = new org.aspcfs.modules.canili.base.Organization(db, Integer.parseInt((id)));
			context.getRequest().setAttribute("OrgCanili", OrgCanili);
		}
		
		if (file.equals("operatoricommerciali")){
			org.aspcfs.modules.operatori_commerciali.base.Organization OrgOperatoriCommerciali = new org.aspcfs.modules.operatori_commerciali.base.Organization(db, Integer.parseInt((id)));
			context.getRequest().setAttribute("OrgOperatoriCommerciali", OrgOperatoriCommerciali);
		}
		
		
		
		
//		ArrayList<String> locali_collegati = new ArrayList<String>();
//		
//		locali_collegati.add((String) context.getRequest().getParameter("addressid"));
//		locali_collegati.add((String) context.getRequest().getParameter("addressid2"));
//		locali_collegati.add((String) context.getRequest().getParameter("addressid3"));
//	
//		context.getRequest().setAttribute("localiCollegati", locali_collegati);
//		
//		Organization org = new Organization (db, Integer.parseInt(id));
//		context.getRequest().setAttribute("OrgDetails", org);
//		
//		// Aggiungo linee attivita
//		ArrayList<LineeAttivita> linee_attivita = LineeAttivita
//		.load_linee_attivita_per_org_id(id, db);
//		context.getRequest().setAttribute("linee_attivita", linee_attivita);
//		LineeAttivita linea_attivita_principale = LineeAttivita
//				.load_linea_attivita_principale_per_org_id(id, db);
//		context.getRequest().setAttribute("linea_attivita_principale",
//				linea_attivita_principale);
//		ArrayList<LineeAttivita> linee_attivita_secondarie = LineeAttivita
//				.load_linee_attivita_secondarie_per_org_id(id, db);
//		context.getRequest().setAttribute("linee_attivita_secondarie",
//				linee_attivita_secondarie);
		
		
		
		
		
} catch (Exception errorMessage) {
	context.getRequest().setAttribute("Error", errorMessage);
	return ("SystemError");
} finally {
	this.freeConnection(context, db);
}
	if (file.equals("modelloC.xml") || file.equals("modelloCattivitamobile.xml"))
			return ("NumeroRegistrazioneOk");
	else if (file.equals("modelloCessazione.xml") || file.equals("modelloCessazioneMobili.xml"))
		return ("CertificatoCessazioneOk");
	else if (file.equals("stabilimenti"))
		return ("SchedaStabilimentoOk");
	else if (file.equals("imbarcazioni") || file.equals("osm") || file.equals("osmregistrati") || file.equals("molluschi") || file.equals("canili") ||file.equals("colonie")||file.equals("operatoricommerciali") ||file.equals("operatori193") ||file.equals("farmacie") ||file.equals("operatorinonaltrove") ||file.equals("privati") ||file.equals("aziendeagricole") ||file.equals("operatorisperimentazioneanimale") ||file.equals("struttureriproduzioneanimale"))
		return ("SchedaGenerica2Ok");
	else if (file.equals("modelloCOpu.xml"))
		return ("NumeroRegistrazioneOk");
	else if (file.equals("modelloCessazioneOpu.xml"))
		return ("CertificatoCessazioneOk");
	
	return ("SchedaPrintOk");
	
	}
}