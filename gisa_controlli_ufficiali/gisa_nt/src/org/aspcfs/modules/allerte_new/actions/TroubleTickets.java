/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.allerte_new.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.sun.mail.handlers.multipart_mixed;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.ProjectList;
import com.zeroio.webutils.FileDownload;







import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.FilterList;
import org.aspcfs.modules.base.GenericControlliUfficiali;
import org.aspcfs.modules.communications.base.CampaignList;


//import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiUpload;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.allerte_new.allegatof.AllegatoF;
import org.aspcfs.modules.allerte_new.allegatof.AllegatoFNote;
import org.aspcfs.modules.allerte_new.base.AllerteAslHistory;
import org.aspcfs.modules.allerte_new.base.AllerteHistory;
import org.aspcfs.modules.allerte_new.base.AslCoinvolte;
import org.aspcfs.modules.allerte_new.base.GestionePEC;
import org.aspcfs.modules.allerte_new.base.ImpreseCoinvolte;
import org.aspcfs.modules.allerte_new.base.ListaDistribuzione;
import org.aspcfs.modules.allerte_new.base.Ticket;
import org.aspcfs.modules.allerte_new.base.TicketList;
import org.aspcfs.modules.allerte_new.base.TicketLog;
import org.aspcfs.modules.allerte_new.base.AllegatoF.Azione;
import org.aspcfs.utils.AuditReport;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.*;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: TroubleTickets.java,v 1.37 2002/12/20 14:07:55 mrajkowski Exp
 *          $
 * @created March 15, 2002
 */
public final class TroubleTickets extends CFSModule {

	
	private Logger logger1 =  Logger.getLogger(TroubleTickets.class);
	private java.util.logging.Logger logger =   java.util.logging.Logger.getLogger("MainLogger");
	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandDefault(ActionContext context) {
		if (!(hasPermission(context, "allerte-allerte-view"))) {
			return ("DefaultError");
		}
		return (this.executeCommandHome(context));
	}
	
	
	public String executeCommandViewAllerteCancellate(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-cancellate-view")) {
			logger.info("Non si dispone dei permessi necessari per Visualizzare le allerte Cancellate");
			return ("PermissionError");
		}
		logger.info("Visualizzazzione Allerte Cancellate");
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		//Prepare ticket state form data
			
			try {
			db = this.getConnection(context);
			//Prepare pagedListInfo
		    PagedListInfo searchListInfo = this.getPagedListInfo(
		        context, "SearchOrgListInfo");
		    searchListInfo.setLink("TroubleTicketsAllerteNew.do?command=ViewAllerteCancellate");
		    searchListInfo.setListView("all");
		
			TicketList ticList = new TicketList();
			ticList.setPagedListInfo(searchListInfo);
			ticList.buildListAllerteCancellate(db);
			context.getRequest().setAttribute("ListaAllerte", ticList);
		
			
			return ("ViewAllerteCancellateOK");
		} catch (Exception errorMessage) {
			logger.severe("Errore nella visualizzazzione delle allerte Cancellate");
			
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}
	

	/**
	 * Description of the Method
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		try {
			db = this.getConnection(context);


			SystemStatus systemStatus = this.getSystemStatus(context);
			
			if (context.getRequest().getAttribute("allerte.insert.error")!=null)
				context.getRequest().setAttribute("allerte.insert.error", "Attenzione non si dispone dei permessi necessari per inserimento allerte Sian o Veterinari");
			
			LookupList lookup_specie_alimento = new LookupList(db,"lookup_specie_alimento");
			lookup_specie_alimento.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupSpecieAlimento",lookup_specie_alimento);

			LookupList lookup_tipologia_alimento = new LookupList(db,"lookup_tipologia_alimento");
			lookup_tipologia_alimento.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupTipologiaAlimento",lookup_tipologia_alimento);

			LookupList AnimaliNonAlimentari = new LookupList(db,
			"lookup_animali_non_alimentari");
	AnimaliNonAlimentari.addItem(-1,
	"-- SELEZIONA VOCE --");
	context.getRequest().setAttribute("AnimaliNonAlimentari",
			AnimaliNonAlimentari);
	
			LookupList UnitaMisura = new LookupList(db,"lookup_unita_misura_allerta");
			UnitaMisura.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("UnitaMisura",UnitaMisura);

			
			context.getRequest().setAttribute("Allerte", "Allerte");
			
			LookupList alimentinontrasformati = new LookupList(db,
					"lookup_alimenti_origine_animale_non_trasformati");
			alimentinontrasformati.addItem(-1,
			"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiNonTrasformati",
					alimentinontrasformati);
			
			LookupList molluschi = new LookupList(db,
			"lookup_molluschi");
			molluschi.addItem(-1,
			"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupmolluschi",
					molluschi);

			// aggiunto da d.dauria
			LookupList alimentinontrasformativalori = new LookupList(db,
			"lookup_alimenti_origine_animale_non_trasformati_valori");
			alimentinontrasformativalori.addItem(-1, "Specie...");
			context.getRequest().setAttribute("AlimentiNonTrasformatiValori",
					alimentinontrasformativalori);


			// aggiunto da d.dauria
			LookupList alimentitrasformati = new LookupList(db,
			"lookup_alimenti_origine_animale_trasformati");
			alimentitrasformati.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiTrasformati",
					alimentitrasformati);

			// aggiunto da d.dauria
			LookupList alimentivegetalinonTrafortmati = new LookupList(db,
			"lookup_alimenti_origine_vegetale_macrocategorie");
			alimentivegetalinonTrafortmati.addItem(-1, "-- SELEZIONA VOCE --");




			alimentivegetalinonTrafortmati.removeElementByLevel(6);
			alimentivegetalinonTrafortmati.removeElementByLevel(1);

			alimentivegetalinonTrafortmati.removeElementByLevel(7);
			context.getRequest().setAttribute("AlimentiVegetaliNonTraformati",
					alimentivegetalinonTrafortmati);



			LookupList alimentivegetaliTrafortmati = new LookupList(db,
			"lookup_alimenti_origine_vegetale_macrocategorie");





			alimentivegetaliTrafortmati.removeElementByLevel(0);
			alimentivegetaliTrafortmati.removeElementByLevel(3);
			alimentivegetaliTrafortmati.removeElementByLevel(4);
			alimentivegetaliTrafortmati.removeElementByLevel(5);
			alimentivegetaliTrafortmati.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiVegetaliTraformati",
					alimentivegetaliTrafortmati);



			LookupList altrialimenti = new LookupList(db, "lookup_altrialimenti_nonanimale");
			altrialimenti.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("AltriAlimenti", altrialimenti);


			LookupList alimentivegetaliFruttaFresca = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			alimentivegetaliFruttaFresca.removeElementByLevel(1);
			alimentivegetaliFruttaFresca.removeElementByLevel(2);
			alimentivegetaliFruttaFresca.removeElementByLevel(3);
			alimentivegetaliFruttaFresca.removeElementByLevel(4);
			alimentivegetaliFruttaFresca.removeElementByLevel(5);
			alimentivegetaliFruttaFresca.removeElementByLevel(6);
			alimentivegetaliFruttaFresca.removeElementByLevel(7);
			alimentivegetaliFruttaFresca.removeElementByLevel(8);

			alimentivegetaliFruttaFresca.addItem(-1, "-- SELEZIONA VOCE --");

			alimentivegetaliFruttaFresca.setMultiple(true);
			alimentivegetaliFruttaFresca.setSelectSize(7);

			context.getRequest().setAttribute("FruttaFresca",
					alimentivegetaliFruttaFresca);





			LookupList alimentivegetaliFruttaSecca = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			alimentivegetaliFruttaSecca.removeElementByLevel(0);
			alimentivegetaliFruttaSecca.removeElementByLevel(2);
			alimentivegetaliFruttaSecca.removeElementByLevel(3);
			alimentivegetaliFruttaSecca.removeElementByLevel(4);
			alimentivegetaliFruttaSecca.removeElementByLevel(5);
			alimentivegetaliFruttaSecca.removeElementByLevel(6);
			alimentivegetaliFruttaSecca.removeElementByLevel(7);
			alimentivegetaliFruttaSecca.removeElementByLevel(8);

			alimentivegetaliFruttaSecca.addItem(-1, "-- SELEZIONA VOCE --");

			alimentivegetaliFruttaSecca.setMultiple(true);
			alimentivegetaliFruttaSecca.setSelectSize(7);
			context.getRequest().setAttribute("FruttaSecca",
					alimentivegetaliFruttaSecca);








			LookupList funghi = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			funghi.removeElementByLevel(1);
			funghi.removeElementByLevel(2);
			funghi.removeElementByLevel(3);
			funghi.removeElementByLevel(0);
			funghi.removeElementByLevel(5);
			funghi.removeElementByLevel(6);
			funghi.removeElementByLevel(7);
			funghi.removeElementByLevel(8);

			funghi.addItem(-1, "-- SELEZIONA VOCE --");

			funghi.setMultiple(true);
			funghi.setSelectSize(7);
			context.getRequest().setAttribute("Funghi",
					funghi);



			LookupList ortaggi = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			ortaggi.removeElementByLevel(1);
			ortaggi.removeElementByLevel(2);
			ortaggi.removeElementByLevel(0);
			ortaggi.removeElementByLevel(4);
			ortaggi.removeElementByLevel(5);
			ortaggi.removeElementByLevel(6);
			ortaggi.removeElementByLevel(7);
			ortaggi.removeElementByLevel(8);


			ortaggi.addItem(-1, "-- SELEZIONA VOCE --");


			ortaggi.setMultiple(true);
			ortaggi.setSelectSize(7);
			context.getRequest().setAttribute("Ortaggi",
					ortaggi);



			LookupList derivati = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			derivati.removeElementByLevel(0);
			derivati.removeElementByLevel(1);
			derivati.removeElementByLevel(3);
			derivati.removeElementByLevel(4);
			derivati.removeElementByLevel(5);
			derivati.removeElementByLevel(6);
			derivati.removeElementByLevel(7);
			derivati.removeElementByLevel(8);

			derivati.addItem(-1, "-- SELEZIONA VOCE --");


			derivati.setMultiple(true);
			derivati.setSelectSize(7);
			context.getRequest().setAttribute("Derivati",
					derivati);



			LookupList conservati = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			conservati.removeElementByLevel(0);
			conservati.removeElementByLevel(1);
			conservati.removeElementByLevel(2);
			conservati.removeElementByLevel(3);
			conservati.removeElementByLevel(4);
			conservati.removeElementByLevel(6);
			conservati.removeElementByLevel(7);
			conservati.removeElementByLevel(8);

			conservati.addItem(-1, "-- SELEZIONA VOCE --");

			conservati.setMultiple(true);
			conservati.setSelectSize(7);
			context.getRequest().setAttribute("Conservati",
					conservati);



			LookupList zuppe = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			zuppe.removeElementByLevel(0);
			zuppe.removeElementByLevel(1);
			zuppe.removeElementByLevel(2);
			zuppe.removeElementByLevel(3);
			zuppe.removeElementByLevel(4);
			zuppe.removeElementByLevel(5);
			zuppe.removeElementByLevel(6);
			zuppe.removeElementByLevel(7);

			zuppe.addItem(-1, "-- SELEZIONA VOCE --");

			zuppe.setMultiple(true);
			zuppe.setSelectSize(7);
			context.getRequest().setAttribute("Zuppe",
					zuppe);






			LookupList grassi = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			grassi.removeElementByLevel(0);
			grassi.removeElementByLevel(1);
			grassi.removeElementByLevel(2);
			grassi.removeElementByLevel(3);
			grassi.removeElementByLevel(4);
			grassi.removeElementByLevel(5);
			grassi.removeElementByLevel(7);
			grassi.removeElementByLevel(8);

			grassi.addItem(-1, "-- SELEZIONA VOCE --");

			grassi.setMultiple(true);
			grassi.setSelectSize(7);
			context.getRequest().setAttribute("Grassi",
					grassi);




			LookupList vino = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			vino.removeElementByLevel(0);
			vino.removeElementByLevel(1);
			vino.removeElementByLevel(2);
			vino.removeElementByLevel(3);
			vino.removeElementByLevel(4);
			vino.removeElementByLevel(6);
			vino.removeElementByLevel(5);
			vino.removeElementByLevel(8);

			vino.addItem(-1, "-- SELEZIONA VOCE --");

			vino.setMultiple(true);
			vino.setSelectSize(7);
			context.getRequest().setAttribute("Vino",
					vino);

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoCampione", TipoCampione);


			LookupList Acque = new LookupList(db, "lookup_acqua");
			Acque.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("Acque", Acque);


			LookupList TipoSpecie_latte = new LookupList(db, "lookup_alimenti_origine_animale_non_trasformati_specielatte");
			TipoSpecie_latte.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoSpecie_latte", TipoSpecie_latte);


			LookupList TipoSpecie_uova = new LookupList(db, "lookup_alimenti_origine_animale_non_trasformati_specieuova");
			TipoSpecie_uova.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));


			context.getRequest().setAttribute("TipoSpecie_uova", TipoSpecie_uova);



			LookupList TipoCampione_batteri = new LookupList(db, "lookup_tipo_campione_batteri");
			TipoCampione_batteri.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			TipoCampione_batteri.setMultiple(true);
			TipoCampione_batteri.setSelectSize(6);


			context.getRequest().setAttribute("TipoCampione_batteri", TipoCampione_batteri);




			LookupList TipoCampione_fisico = new LookupList(db, "lookup_tipo_campione_fisico");
			TipoCampione_fisico.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			TipoCampione_fisico.setMultiple(true);
			TipoCampione_fisico.setSelectSize(6);


			context.getRequest().setAttribute("TipoCampione_fisico", TipoCampione_fisico);


			LookupList TipoCampione_virus = new LookupList(db, "lookup_tipo_campione_virus");
			TipoCampione_virus.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");

			TipoCampione_virus.setMultiple(true);
			TipoCampione_virus.setSelectSize(6);

			context.getRequest().setAttribute("TipoCampione_virus", TipoCampione_virus);


			LookupList TipoCampione_parassiti = new LookupList(db, "lookup_tipo_campione_parassiti");
			TipoCampione_parassiti.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			TipoCampione_parassiti.setMultiple(true);
			TipoCampione_parassiti.setSelectSize(6);

			context.getRequest().setAttribute("TipoCampione_parassiti", TipoCampione_parassiti);


			LookupList TipoCampione_chimico = new LookupList(db, "lookup_tipo_campione_chimicio");
			TipoCampione_chimico.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));


			context.getRequest().setAttribute("TipoCampione_chimico", TipoCampione_chimico);


			LookupList TipoCampione_sottochimico = new LookupList(db, "lookup_tipo_campione_sottochimicio");
			TipoCampione_sottochimico.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico.setMultiple(true);
			TipoCampione_sottochimico.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico", TipoCampione_sottochimico);

			LookupList TipoCampione_sottochimico2 = new LookupList(db, "lookup_tipo_campione_sottochimicio2");
			TipoCampione_sottochimico2.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico2.setMultiple(true);
			TipoCampione_sottochimico2.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico2", TipoCampione_sottochimico2);


			LookupList TipoCampione_sottochimico3 = new LookupList(db, "lookup_tipo_campione_sottochimicio3");
			TipoCampione_sottochimico3.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico3.setMultiple(true);
			TipoCampione_sottochimico3.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico3", TipoCampione_sottochimico3);


			LookupList TipoCampione_sottochimico4 = new LookupList(db, "lookup_tipo_campione_sottochimicio4");
			TipoCampione_sottochimico4.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico4.setMultiple(true);
			TipoCampione_sottochimico4.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico4", TipoCampione_sottochimico4);


			LookupList TipoCampione_sottochimico5 = new LookupList(db, "lookup_tipo_campione_sottochimicio5");
			TipoCampione_sottochimico5.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico5.setMultiple(true);
			TipoCampione_sottochimico5.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico5", TipoCampione_sottochimico5);

			Statement stmt=db.createStatement();
			ResultSet rstSiteid= stmt.executeQuery("select * from lookup_site_id where enabled=true and code not in (16) order by code ");
			HashMap<Integer, String> siteIdList=new HashMap<Integer, String>();
			while(rstSiteid.next()){

				Integer code=rstSiteid.getInt("code");
				String descr=rstSiteid.getString("description");
				siteIdList.put(code, descr);
			}

			context.getRequest().setAttribute("SiteIdListUtil", siteIdList);

			//GIUSEPPE




			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList regioni = new LookupList(db, "lookup_regioni");
			regioni.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Regioni", regioni);


			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
			SanzioniAmministrative.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);

			LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
			SanzioniPenali.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

			LookupList TipoAlimento = new LookupList(db, "lookup_tipo_alimento");
			TipoAlimento.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAlimento", TipoAlimento);

			LookupList Origine = new LookupList(db, "lookup_origine");
			Origine.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Origine", Origine);

			LookupList AlimentoInteressato = new LookupList(db, "lookup_alimento_interessato");
			AlimentoInteressato.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentoInteressato", AlimentoInteressato);

			LookupList NonConformita = new LookupList(db, "lookup_non_conformita");
			NonConformita.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("NonConformita", NonConformita);

			LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
			ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);


			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);
			
			LookupList tipoNotifica = new LookupList(db,"lookup_allerte_notifica");
			tipoNotifica.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("tipoNotifica",tipoNotifica);

			if (context.getRequest().getParameter("refresh") != null || (context.getRequest().getParameter(
			"contact") != null && context.getRequest().getParameter("contact").equals(
			"on"))) {
				newTic = (Ticket) context.getFormBean();
			
			} else {
				newTic = new Ticket();
			}

			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);


			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "AddTicket", "Ticket Add");
		if (context.getRequest().getParameter("actionSource") != null) {
			context.getRequest().setAttribute(
					"actionSource", context.getRequest().getParameter("actionSource"));
			return "AddTicketOK";
		}
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));

		String retPage = "SystemError";

		String tipo_richiesta = context.getRequest().getParameter( "tipo_richiesta" );
		tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

		retPage = "AddOK";


		return ( retPage );
	}


	/**
	 * Description of the Method
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-edit")) {
			return ("PermissionError");
		}
		boolean ripianifica = false;
		
		Connection db = null;
		Ticket newTic = null;
		//Parameters
		String ticketId = context.getRequest().getParameter("id");
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);
			User user = this.getUser(context, this.getUserId(context));
			//Load the ticket
			if (context.getRequest().getParameter("companyName") == null) {
				newTic = new Ticket();
				newTic.queryRecord(db, Integer.parseInt(ticketId));
			} else {
				newTic = (Ticket) context.getFormBean();
				if (newTic.getOrgId() != -1) {
				}
			}

			//find record permissions for portal users
			//      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
			//        return ("PermissionError");
			//      }
			if (context.getRequest().getParameter("ripianifica")!=null)
			{
				context.getRequest().setAttribute("ripianifica", "true");
				ripianifica = true;
			}
			
			
			LookupList lookup_specie_alimento = new LookupList(db,
			"lookup_specie_alimento");
			lookup_specie_alimento.addItem(-1,
			"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupSpecieAlimento",
					lookup_specie_alimento);

			LookupList lookup_tipologia_alimento = new LookupList(db,
			"lookup_tipologia_alimento");
			lookup_tipologia_alimento.addItem(-1,
			"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupTipologiaAlimento",
					lookup_tipologia_alimento);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");

			SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList alimentinontrasformati = new LookupList(db,
			"lookup_alimenti_origine_animale_non_trasformati");
			alimentinontrasformati.addItem(-1,
			"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiNonTrasformati",
					alimentinontrasformati);

			LookupList molluschi = new LookupList(db,
			"lookup_molluschi");
			molluschi.addItem(-1,
			"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupmolluschi",
					molluschi);
			
			LookupList UnitaMisura = new LookupList(db,"lookup_unita_misura_allerta");
			UnitaMisura.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("UnitaMisura",UnitaMisura);

			//aggiunto da d.dauria
			LookupList alimentinontrasformativalori = new LookupList(db,
			"lookup_alimenti_origine_animale_non_trasformati_valori");
			alimentinontrasformativalori.addItem(-1, "Specie...");
			context.getRequest().setAttribute("AlimentiNonTrasformatiValori",
					alimentinontrasformativalori);

			LookupList regioni = new LookupList(db, "lookup_regioni");
			regioni.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Regioni", regioni);

			//aggiunto da d.dauria
			LookupList alimentitrasformati = new LookupList(db,
			"lookup_alimenti_origine_animale_trasformati");
			alimentitrasformati.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiTrasformati",
					alimentitrasformati);

			//aggiunto da d.dauria
			LookupList alimentivegetalinonTrafortmati = new LookupList(db,
			"lookup_alimenti_origine_vegetale_macrocategorie");
			alimentivegetalinonTrafortmati.addItem(-1, "-- SELEZIONA VOCE --");



			alimentivegetalinonTrafortmati.removeElementByLevel(6);
			alimentivegetalinonTrafortmati.removeElementByLevel(1);

			alimentivegetalinonTrafortmati.removeElementByLevel(7);
			context.getRequest().setAttribute("AlimentiVegetaliNonTraformati",
					alimentivegetalinonTrafortmati);

			LookupList altrialimenti = new LookupList(db, "lookup_altrialimenti_nonanimale");
			altrialimenti.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("AltriAlimenti", altrialimenti);

			LookupList alimentivegetaliTrafortmati = new LookupList(db,
			"lookup_alimenti_origine_vegetale_macrocategorie");





			alimentivegetaliTrafortmati.removeElementByLevel(0);
			alimentivegetaliTrafortmati.removeElementByLevel(3);
			alimentivegetaliTrafortmati.removeElementByLevel(4);
			alimentivegetaliTrafortmati.removeElementByLevel(5);
			alimentivegetaliTrafortmati.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiVegetaliTraformati",
					alimentivegetaliTrafortmati);







			LookupList alimentivegetaliFruttaFresca = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			alimentivegetaliFruttaFresca.removeElementByLevel(1);
			alimentivegetaliFruttaFresca.removeElementByLevel(2);
			alimentivegetaliFruttaFresca.removeElementByLevel(3);
			alimentivegetaliFruttaFresca.removeElementByLevel(4);
			alimentivegetaliFruttaFresca.removeElementByLevel(5);
			alimentivegetaliFruttaFresca.removeElementByLevel(6);
			alimentivegetaliFruttaFresca.removeElementByLevel(7);
			alimentivegetaliFruttaFresca.removeElementByLevel(8);

			alimentivegetaliFruttaFresca.addItem(-1, "-- SELEZIONA VOCE --");

			alimentivegetaliFruttaFresca.setMultiple(true);
			alimentivegetaliFruttaFresca.setSelectSize(7);

			context.getRequest().setAttribute("FruttaFresca",
					alimentivegetaliFruttaFresca);





			LookupList alimentivegetaliFruttaSecca = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			alimentivegetaliFruttaSecca.removeElementByLevel(0);
			alimentivegetaliFruttaSecca.removeElementByLevel(2);
			alimentivegetaliFruttaSecca.removeElementByLevel(3);
			alimentivegetaliFruttaSecca.removeElementByLevel(4);
			alimentivegetaliFruttaSecca.removeElementByLevel(5);
			alimentivegetaliFruttaSecca.removeElementByLevel(6);
			alimentivegetaliFruttaSecca.removeElementByLevel(7);
			alimentivegetaliFruttaSecca.removeElementByLevel(8);

			alimentivegetaliFruttaSecca.addItem(-1, "-- SELEZIONA VOCE --");

			alimentivegetaliFruttaSecca.setMultiple(true);
			alimentivegetaliFruttaSecca.setSelectSize(7);
			context.getRequest().setAttribute("FruttaSecca",
					alimentivegetaliFruttaSecca);








			LookupList funghi = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			funghi.removeElementByLevel(1);
			funghi.removeElementByLevel(2);
			funghi.removeElementByLevel(3);
			funghi.removeElementByLevel(0);
			funghi.removeElementByLevel(5);
			funghi.removeElementByLevel(6);
			funghi.removeElementByLevel(7);
			funghi.removeElementByLevel(8);

			funghi.addItem(-1, "-- SELEZIONA VOCE --");

			funghi.setMultiple(true);
			funghi.setSelectSize(7);
			context.getRequest().setAttribute("Funghi",
					funghi);



			LookupList ortaggi = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			ortaggi.removeElementByLevel(1);
			ortaggi.removeElementByLevel(2);
			ortaggi.removeElementByLevel(0);
			ortaggi.removeElementByLevel(4);
			ortaggi.removeElementByLevel(5);
			ortaggi.removeElementByLevel(6);
			ortaggi.removeElementByLevel(7);
			ortaggi.removeElementByLevel(8);


			ortaggi.addItem(-1, "-- SELEZIONA VOCE --");


			ortaggi.setMultiple(true);
			ortaggi.setSelectSize(7);
			context.getRequest().setAttribute("Ortaggi",
					ortaggi);



			LookupList derivati = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			derivati.removeElementByLevel(0);
			derivati.removeElementByLevel(1);
			derivati.removeElementByLevel(3);
			derivati.removeElementByLevel(4);
			derivati.removeElementByLevel(5);
			derivati.removeElementByLevel(6);
			derivati.removeElementByLevel(7);
			derivati.removeElementByLevel(8);

			derivati.addItem(-1, "-- SELEZIONA VOCE --");


			derivati.setMultiple(true);
			derivati.setSelectSize(7);
			context.getRequest().setAttribute("Derivati",
					derivati);



			LookupList conservati = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			conservati.removeElementByLevel(0);
			conservati.removeElementByLevel(1);
			conservati.removeElementByLevel(2);
			conservati.removeElementByLevel(3);
			conservati.removeElementByLevel(4);
			conservati.removeElementByLevel(6);
			conservati.removeElementByLevel(7);
			conservati.removeElementByLevel(8);

			conservati.addItem(-1, "-- SELEZIONA VOCE --");

			conservati.setMultiple(true);
			conservati.setSelectSize(7);
			context.getRequest().setAttribute("Conservati",
					conservati);



			LookupList zuppe = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			zuppe.removeElementByLevel(0);
			zuppe.removeElementByLevel(1);
			zuppe.removeElementByLevel(2);
			zuppe.removeElementByLevel(3);
			zuppe.removeElementByLevel(4);
			zuppe.removeElementByLevel(5);
			zuppe.removeElementByLevel(6);
			zuppe.removeElementByLevel(7);

			zuppe.addItem(-1, "-- SELEZIONA VOCE --");

			zuppe.setMultiple(true);
			zuppe.setSelectSize(7);
			context.getRequest().setAttribute("Zuppe",
					zuppe);






			LookupList grassi = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			grassi.removeElementByLevel(0);
			grassi.removeElementByLevel(1);
			grassi.removeElementByLevel(2);
			grassi.removeElementByLevel(3);
			grassi.removeElementByLevel(4);
			grassi.removeElementByLevel(5);
			grassi.removeElementByLevel(7);
			grassi.removeElementByLevel(8);

			grassi.addItem(-1, "-- SELEZIONA VOCE --");

			grassi.setMultiple(true);
			grassi.setSelectSize(7);
			context.getRequest().setAttribute("Grassi",
					grassi);




			LookupList vino = new LookupList(db,
			"lookup_alimenti_origine_vegetale_valori");
			vino.removeElementByLevel(0);
			vino.removeElementByLevel(1);
			vino.removeElementByLevel(2);
			vino.removeElementByLevel(3);
			vino.removeElementByLevel(4);
			vino.removeElementByLevel(6);
			vino.removeElementByLevel(5);
			vino.removeElementByLevel(8);

			vino.addItem(-1, "-- SELEZIONA VOCE --");

			vino.setMultiple(true);
			vino.setSelectSize(7);
			context.getRequest().setAttribute("Vino",
					vino);

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoCampione", TipoCampione);


			LookupList Acque = new LookupList(db, "lookup_acqua");
			Acque.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("Acque", Acque);


			LookupList TipoSpecie_latte = new LookupList(db, "lookup_alimenti_origine_animale_non_trasformati_specielatte");
			TipoSpecie_latte.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoSpecie_latte", TipoSpecie_latte);


			LookupList TipoSpecie_uova = new LookupList(db, "lookup_alimenti_origine_animale_non_trasformati_specieuova");
			TipoSpecie_uova.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));


			context.getRequest().setAttribute("TipoSpecie_uova", TipoSpecie_uova);



			LookupList TipoCampione_batteri = new LookupList(db, "lookup_tipo_campione_batteri");
			TipoCampione_batteri.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			TipoCampione_batteri.setMultiple(true);
			TipoCampione_batteri.setSelectSize(6);


			context.getRequest().setAttribute("TipoCampione_batteri", TipoCampione_batteri);



			LookupList TipoCampione_fisico = new LookupList(db, "lookup_tipo_campione_fisico");
			TipoCampione_fisico.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			TipoCampione_fisico.setMultiple(true);
			TipoCampione_fisico.setSelectSize(6);


			context.getRequest().setAttribute("TipoCampione_fisico", TipoCampione_fisico);

			LookupList TipoCampione_virus = new LookupList(db, "lookup_tipo_campione_virus");
			TipoCampione_virus.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");

			TipoCampione_virus.setMultiple(true);
			TipoCampione_virus.setSelectSize(6);

			context.getRequest().setAttribute("TipoCampione_virus", TipoCampione_virus);


			LookupList TipoCampione_parassiti = new LookupList(db, "lookup_tipo_campione_parassiti");
			TipoCampione_parassiti.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			TipoCampione_parassiti.setMultiple(true);
			TipoCampione_parassiti.setSelectSize(6);

			context.getRequest().setAttribute("TipoCampione_parassiti", TipoCampione_parassiti);


			LookupList TipoCampione_chimico = new LookupList(db, "lookup_tipo_campione_chimicio");
			TipoCampione_chimico.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));


			context.getRequest().setAttribute("TipoCampione_chimico", TipoCampione_chimico);


			LookupList TipoCampione_sottochimico = new LookupList(db, "lookup_tipo_campione_sottochimicio");
			TipoCampione_sottochimico.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico.setMultiple(true);
			TipoCampione_sottochimico.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico", TipoCampione_sottochimico);

			LookupList TipoCampione_sottochimico2 = new LookupList(db, "lookup_tipo_campione_sottochimicio2");
			TipoCampione_sottochimico2.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico2.setMultiple(true);
			TipoCampione_sottochimico2.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico2", TipoCampione_sottochimico2);


			LookupList TipoCampione_sottochimico3 = new LookupList(db, "lookup_tipo_campione_sottochimicio3");
			TipoCampione_sottochimico3.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico3.setMultiple(true);
			TipoCampione_sottochimico3.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico3", TipoCampione_sottochimico3);


			LookupList TipoCampione_sottochimico4 = new LookupList(db, "lookup_tipo_campione_sottochimicio4");
			TipoCampione_sottochimico4.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico4.setMultiple(true);
			TipoCampione_sottochimico4.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico4", TipoCampione_sottochimico4);


			LookupList TipoCampione_sottochimico5 = new LookupList(db, "lookup_tipo_campione_sottochimicio5");
			TipoCampione_sottochimico5.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));

			TipoCampione_sottochimico5.setMultiple(true);
			TipoCampione_sottochimico5.setSelectSize(6);
			context.getRequest().setAttribute("TipoCampione_sottochimico5", TipoCampione_sottochimico5);

			Statement stmt=db.createStatement();
			ResultSet rstSiteid= stmt.executeQuery("select * from lookup_site_id where enabled=true");
			HashMap<Integer, String> siteIdList=new HashMap<Integer, String>();
			while(rstSiteid.next()){
				
				Integer code=rstSiteid.getInt("code");
				
				String descr=rstSiteid.getString("description");
				siteIdList.put(code, descr);
			}

			context.getRequest().setAttribute("SiteIdListUtil", siteIdList);

			

			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
			SanzioniAmministrative.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);

			LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
			SanzioniPenali.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);


			LookupList TipoAlimento = new LookupList(db, "lookup_tipo_alimento");
			TipoAlimento.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAlimento", TipoAlimento);

			LookupList Origine = new LookupList(db, "lookup_origine");
			Origine.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Origine", Origine);

			LookupList AlimentoInteressato = new LookupList(db, "lookup_alimento_interessato");
			AlimentoInteressato.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentoInteressato", AlimentoInteressato);

			LookupList NonConformita = new LookupList(db, "lookup_non_conformita");
			NonConformita.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("NonConformita", NonConformita);

			LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
			ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);

			LookupList tipoNotifica = new LookupList(db,"lookup_allerte_notifica");
			tipoNotifica.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("tipoNotifica",tipoNotifica);

			
			Organization orgDetails = new Organization();//db, newTic.getOrgId());
			context.getRequest().setAttribute("OrgDetails", orgDetails);

			context.getRequest().setAttribute("TicketDetails", newTic);
			addRecentItem(context, newTic);

			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("TicketDetails", newTic);
		addModuleBean(context, "ViewTickets", "View Tickets");

		String retPage = "Modify";
		
		if (ripianifica)
			retPage = "Ripianifica";
	
		return getReturn( context, retPage );
	}


	/**
	 * Description of the Method
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		String ticketId = null;
		SystemStatus systemStatus = this.getSystemStatus(context);

		try {
			db = this.getConnection(context);

			LookupList UnitaMisura = new LookupList(db,"lookup_unita_misura_allerta");
			UnitaMisura.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("UnitaMisura",UnitaMisura);
			
//			LookupList lookup_specie_alimento = new LookupList(db,
//			"lookup_specie_alimento");
//			lookup_specie_alimento.addItem(-1,
//			"-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("lookupSpecieAlimento",
//					lookup_specie_alimento);
//
//			LookupList lookup_tipologia_alimento = new LookupList(db,
//			"lookup_tipologia_alimento");
//			lookup_tipologia_alimento.addItem(-1,
//			"-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("lookupTipologiaAlimento",
//					lookup_tipologia_alimento);
//			LookupList alimentinontrasformati = new LookupList(db,
//			"lookup_alimenti_origine_animale_non_trasformati");
//			alimentinontrasformati.addItem(-1,"-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("AlimentiNonTrasformati",
//					alimentinontrasformati);
//			
//			LookupList molluschi = new LookupList(db,
//			"lookup_molluschi");
//			molluschi.addItem(-1,
//			"-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("lookupmolluschi",
//					molluschi);
//
//			LookupList altrialimenti = new LookupList(db, "lookup_altrialimenti_nonanimale");
//			altrialimenti.addItem(-1, systemStatus
//					.getLabel("calendar.none.4dashes"));
//			context.getRequest().setAttribute("AltriAlimenti", altrialimenti);
//
//			// aggiunto da d.dauria
//			LookupList alimentinontrasformativalori = new LookupList(db,
//			"lookup_alimenti_origine_animale_non_trasformati_valori");
//			alimentinontrasformativalori.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("AlimentiNonTrasformatiValori",
//					alimentinontrasformativalori);
//
//			// aggiunto da d.dauria
//			LookupList alimentitrasformati = new LookupList(db,
//			"lookup_alimenti_origine_animale_trasformati");
//			alimentitrasformati.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("AlimentiTrasformati",
//					alimentitrasformati);
//
//			// aggiunto da d.dauria
//			LookupList alimentivegetali = new LookupList(db,
//			"lookup_alimenti_origine_vegetale_macrocategorie");
//			alimentivegetali.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("AlimentiVegetali",
//					alimentivegetali);

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

		
			// Parameters
			ticketId = context.getRequest().getParameter("id");
			if(ticketId==null)
				ticketId=""+(Integer)context.getRequest().getAttribute("idticket");


			// Reset the pagedLists since this could be a new visit to this ticket
			deletePagedListInfo(context, "TicketDocumentListInfo");
			deletePagedListInfo(context, "SunListInfo");
			deletePagedListInfo(context, "TMListInfo");
			deletePagedListInfo(context, "CSSListInfo");
			deletePagedListInfo(context, "TicketsFolderInfo");
			deletePagedListInfo(context, "TicketTaskListInfo");
			deletePagedListInfo(context, "ticketPlanWorkListInfo");

			// Load the ticket
			newTic = new Ticket();
			newTic.queryRecord(db, Integer.parseInt(ticketId));
			
		

			

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList regioni = new LookupList(db, "lookup_regioni");
			regioni.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Regioni", regioni);



//			LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
//			SanzioniPenali.addItem(-1,  "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);
//
//			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
//			Sequestri.addItem(-1,  "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("Sequestri", Sequestri);
//
//			LookupList TipoAlimento = new LookupList(db, "lookup_tipo_alimento");
//			TipoAlimento.addItem(-1,  "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("TipoAlimento", TipoAlimento);

			LookupList Origine = new LookupList(db, "lookup_origine");
			Origine.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Origine", Origine);
//
//			LookupList AlimentoInteressato = new LookupList(db, "lookup_alimento_interessato");
//			AlimentoInteressato.addItem(-1,  "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("AlimentoInteressato", AlimentoInteressato);
//
//			LookupList NonConformita = new LookupList(db, "lookup_non_conformita");
//			NonConformita.addItem(-1,  "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("NonConformita", NonConformita);
//
			LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
			ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);

			LookupList tipoNotifica = new LookupList(db,"lookup_allerte_notifica");
			tipoNotifica.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("tipoNotifica",tipoNotifica);

			
			//AllegatoF
			//if (newTic.getResolutionDate()!=null)
				prepareAllegatoF(db, context, newTic);
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("TicketDetails", newTic);
		addRecentItem(context, newTic);
		addModuleBean(context, "ViewTickets", "View Tickets");

		String retPage = "DetailsOK";
		String tipo_richiesta = newTic.getTipo_richiesta();
		tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

		retPage = "DetailsOK";

	

		return ( retPage );
	}

	
	public String executeCommandViewLegenda(ActionContext context) {
		
	
		return "ViewLegendaOK";

	}
	


	
	public String executeCommandSaveData(ActionContext context) {
		
	if (!(hasPermission(context, "allerte-allerte-edit"))) {
		return ("PermissionError");
	}

	int resultCount = -1;
	Connection db = null;
	Ticket thisTicket = null;
	try {
		db = this.getConnection(context);
		String dataDefinitiva =  context.getRequest().getParameter("dataChiusuraDef");	
		thisTicket = new Ticket(
				db, Integer.parseInt(context.getRequest().getParameter("id")));
		Ticket oldTicket = new Ticket(db, thisTicket.getId());
		//check permission to record
		if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
			return ("PermissionError");
		}
		thisTicket.setModifiedBy(getUserId(context));
		resultCount = thisTicket.chiudi(db, dataDefinitiva);
		
		Hashtable<Integer, ListaDistribuzione> liste_distribuzione = null;
		if (thisTicket.getListaCommercializzazione()==1){
			liste_distribuzione = ListaDistribuzione.getListeDistribuzione(  thisTicket.getId(), thisTicket.getIdAllerta(), db );
		for (int i =1; i<=liste_distribuzione.size();i++){
			ListaDistribuzione lista = (ListaDistribuzione) liste_distribuzione.get(i);
			if (lista.getData_chiusura()==null){
				lista.chiudi(db, dataDefinitiva);
			}
		}
		}
		
		if (resultCount == -1) {
			return (executeCommandDetails(context));
		} else if (resultCount == 1) {
			thisTicket.queryRecord(db, thisTicket.getId());
			this.processUpdateHook(context, oldTicket, thisTicket);	
			return executeCommandChiudiAllerta(context);
		}
	} catch (Exception errorMessage) {
		context.getRequest().setAttribute("Error", errorMessage);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
	return ("UserError");

	}
	
	
	public String executeCommandSaveDataLista(ActionContext context) {
		
		if (!(hasPermission(context, "allerte-allerte-edit"))) {
			return ("PermissionError");
		}

		int resultCount = -1;
		Connection db = null;
		ListaDistribuzione thisLista = null;
		try {
			db = this.getConnection(context);
			String dataDefinitiva =  context.getRequest().getParameter("dataChiusuraDefLista");	
			thisLista = new ListaDistribuzione(
					db, Integer.parseInt(context.getRequest().getParameter("idListaDaChiudere")));
			ListaDistribuzione oldLista = new ListaDistribuzione(db, thisLista.getId());
			
			thisLista.setId_utente_modifica(getUserId(context));
			resultCount = thisLista.chiudi(db, dataDefinitiva);
			
			context.getRequest().setAttribute("idticket", thisLista.getId_allerta());
			return (executeCommandDetails(context));
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
		}
	/**
	 * View Tickets History
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandViewHistory(ActionContext context) {

		if (!(hasPermission(context, "allerte-allerte-view"))) {
			return ("PermissionError");
		}

		Connection db = null;
		Ticket thisTic = null;
		String ticketId = null;

		try {
			ticketId = context.getRequest().getParameter("id");
			db = this.getConnection(context);
			thisTic = new Ticket();
			SystemStatus systemStatus = this.getSystemStatus(context);
			thisTic.setSystemStatus(systemStatus);
			thisTic.queryRecord(db, Integer.parseInt(ticketId));

			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
				return ("PermissionError");
			}

			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		addModuleBean(context, "View Tickets", "Ticket Details");
		context.getRequest().setAttribute("TicketDetails", thisTic);
		addModuleBean(context, "ViewTickets", "View Tickets");
		addRecentItem(context, thisTic);
		return ("ViewHistoryOK");
	}


	
	
	public String executeCommandHome(ActionContext context) {
		int MINIMIZED_ITEMS_PER_PAGE = 4;
		if (!(hasPermission(context, "allerte-allerte-view"))) {
			return ("PermissionError");
		}
		context.getSession().removeAttribute("searchTickets");
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);


		//TicketList assignedToMeList = new TicketList();
		//TicketList openList = new TicketList();
		//TicketList createdByMeList = new TicketList();
		TicketList allTicketsList = new TicketList();
		//TicketList userGroupTicketList = new TicketList();
		User user = this.getUser(context, this.getUserId(context));
		String sectionId = null;
		if (context.getRequest().getParameter("pagedListSectionId") != null) {
			sectionId = context.getRequest().getParameter("pagedListSectionId");
		}
		//reset the paged lists
		if (context.getRequest().getParameter("resetList") != null && context.getRequest().getParameter(
		"resetList").equals("true")) {
			context.getSession().removeAttribute("AssignedToMeInfo");
			context.getSession().removeAttribute("OpenInfo");
			context.getSession().removeAttribute("CreatedByMeInfo");
			context.getSession().removeAttribute("AllTicketsInfo");
			context.getSession().removeAttribute("UserGroupTicketInfo");
		}
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		//Assigned To Me
		PagedListInfo assignedToMeInfo = this.getPagedListInfo(
				context, "AssignedToMeInfo", "t.data_apertura", "desc");
		assignedToMeInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!assignedToMeInfo.getExpandedSelection()) {
				if (assignedToMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					assignedToMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (assignedToMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					assignedToMeInfo.setItemsPerPage(
							PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(assignedToMeInfo.getId())) {
			assignedToMeInfo.setExpandedSelection(true);
		}

		//Other Tickets In My Department
		PagedListInfo openInfo = this.getPagedListInfo(
				context, "OpenInfo", "t.data_apertura", "desc");
		openInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!openInfo.getExpandedSelection()) {
				if (openInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					openInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (openInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					openInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(openInfo.getId())) {
			openInfo.setExpandedSelection(true);
		}

		//Tickets Created By Me
		PagedListInfo createdByMeInfo = this.getPagedListInfo(
				context, "CreatedByMeInfo", "t.data_apertura", "desc");
		createdByMeInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!createdByMeInfo.getExpandedSelection()) {
				if (createdByMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					createdByMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (createdByMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					createdByMeInfo.setItemsPerPage(
							PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(createdByMeInfo.getId())) {
			createdByMeInfo.setExpandedSelection(true);
		}

		//Tickets in my User Group
		PagedListInfo userGroupTicketInfo = this.getPagedListInfo(
				context, "UserGroupTicketInfo", "t.data_apertura", "desc");
		userGroupTicketInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!userGroupTicketInfo.getExpandedSelection()) {
				if (userGroupTicketInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					userGroupTicketInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (userGroupTicketInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					userGroupTicketInfo.setItemsPerPage(
							PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(userGroupTicketInfo.getId())) {
			userGroupTicketInfo.setExpandedSelection(true);
		}

		
		PagedListInfo allTicketsInfo = this.getPagedListInfo(
				context, "AllTicketsInfo", "t.data_apertura", "desc");
		allTicketsInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!allTicketsInfo.getExpandedSelection()) {
				if (allTicketsInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					allTicketsInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (allTicketsInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					allTicketsInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(allTicketsInfo.getId())) {
			allTicketsInfo.setExpandedSelection(true);
		}
		if (sectionId == null || allTicketsInfo.getExpandedSelection()) {
			allTicketsList.setPagedListInfo(allTicketsInfo);
			allTicketsList.setSiteId(user.getSiteId());
			if (user.getSiteId() > -1) {
				allTicketsList.setExclusiveToSite(true);
				allTicketsList.setIncludeAllSites(false);
			}
			allTicketsList.setUnassignedToo(true);
			allTicketsList.setOnlyOpen(true);
		}
		try {

			db = this.getConnection(context);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			allTicketsList.setIncludiChiuse(false);

			if (sectionId == null || allTicketsInfo.getExpandedSelection()) {
				allTicketsList.buildList(db);
			}

			
			
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}


		addModuleBean(context, "VisualizzaRichiesta", "Visualizza Richiesta");
//		context.getRequest().setAttribute("CreatedByMeList", createdByMeList);
//		context.getRequest().setAttribute("AssignedToMeList", assignedToMeList);
//		context.getRequest().setAttribute("OpenList", openList);
		context.getRequest().setAttribute("AllTicketsList", allTicketsList);
//		context.getRequest().setAttribute("UserGroupTicketList", userGroupTicketList);
		addModuleBean(context, "VisualizzaRichiesta", "Visualizza Richiesta");
		return ("HomeOK");
	}


	//aggiunto da d.dauria
	public String executeCommandStorico(ActionContext context) {
		int MINIMIZED_ITEMS_PER_PAGE = 5;
		if (!(hasPermission(context, "allerte-allerte-view"))) {
			return ("PermissionError");
		}
		context.getSession().removeAttribute("searchTickets");
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);


		TicketList assignedToMeList = new TicketList();
		TicketList openList = new TicketList();
		TicketList createdByMeList = new TicketList();
		TicketList allTicketsList = new TicketList();
		TicketList userGroupTicketList = new TicketList();
		User user = this.getUser(context, this.getUserId(context));

		String sectionId = null;
		if (context.getRequest().getParameter("pagedListSectionId") != null) {
			sectionId = context.getRequest().getParameter("pagedListSectionId");
		}
		//reset the paged lists
		if (context.getRequest().getParameter("resetList") != null && context.getRequest().getParameter(
		"resetList").equals("true")) {
			context.getSession().removeAttribute("AssignedToMeInfo");
			context.getSession().removeAttribute("OpenInfo");
			context.getSession().removeAttribute("CreatedByMeInfo");
			context.getSession().removeAttribute("AllTicketsInfo");
			context.getSession().removeAttribute("UserGroupTicketInfo");
		}
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		//Assigned To Me
		PagedListInfo assignedToMeInfo = this.getPagedListInfo(
				context, "AssignedToMeInfo", "t.entered", "desc");
		assignedToMeInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!assignedToMeInfo.getExpandedSelection()) {
				if (assignedToMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					assignedToMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (assignedToMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					assignedToMeInfo.setItemsPerPage(
							PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(assignedToMeInfo.getId())) {
			assignedToMeInfo.setExpandedSelection(true);
		}
		if (sectionId == null || assignedToMeInfo.getExpandedSelection() == true) {
			assignedToMeList.setPagedListInfo(assignedToMeInfo);
			assignedToMeList.setAssignedTo(user.getId());
			assignedToMeList.setIncludeAllSites(true);
			assignedToMeList.setOnlyOpen(true);
			if ("assignedToMe".equals(assignedToMeInfo.getListView())) {
				assignedToMeList.setAssignedTo(user.getId());
			}
		}
		//Other Tickets In My Department
		PagedListInfo openInfo = this.getPagedListInfo(
				context, "OpenInfo", "t.entered", "desc");
		openInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!openInfo.getExpandedSelection()) {
				if (openInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					openInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (openInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					openInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(openInfo.getId())) {
			openInfo.setExpandedSelection(true);
		}
		if (sectionId == null || openInfo.getExpandedSelection() == true) {
			openList.setPagedListInfo(openInfo);
			openList.setUnassignedToo(true);
			openList.setBuildDepartmentTickets(true);
			openList.setDepartment(
					thisUser.getUserRecord().getContact().getDepartment());
			openList.setExcludeAssignedTo(user.getId());
			if (UserUtils.getUserSiteId(context.getRequest()) != -1) {
				openList.setIncludeAllSites(false);
			}
			openList.setExclusiveToSite(true);
			openList.setSiteId(UserUtils.getUserSiteId(context.getRequest()));
			openList.setOnlyOpen(true);
		}
		//Tickets Created By Me
		PagedListInfo createdByMeInfo = this.getPagedListInfo(
				context, "CreatedByMeInfo", "t.entered", "desc");
		createdByMeInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!createdByMeInfo.getExpandedSelection()) {
				if (createdByMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					createdByMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (createdByMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					createdByMeInfo.setItemsPerPage(
							PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(createdByMeInfo.getId())) {
			createdByMeInfo.setExpandedSelection(true);
		}
		if (sectionId == null || createdByMeInfo.getExpandedSelection() == true) {
			createdByMeList.setPagedListInfo(createdByMeInfo);
			createdByMeList.setEnteredBy(user.getId());
			createdByMeList.setSiteId(user.getSiteId());
			if (user.getSiteId() != -1) {
				createdByMeList.setExclusiveToSite(true);
				createdByMeList.setIncludeAllSites(false);
			}
			createdByMeList.setOnlyOpen(true);
		}
		//Tickets in my User Group
		PagedListInfo userGroupTicketInfo = this.getPagedListInfo(
				context, "UserGroupTicketInfo", "t.entered", "desc");
		userGroupTicketInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!userGroupTicketInfo.getExpandedSelection()) {
				if (userGroupTicketInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					userGroupTicketInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (userGroupTicketInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					userGroupTicketInfo.setItemsPerPage(
							PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(userGroupTicketInfo.getId())) {
			userGroupTicketInfo.setExpandedSelection(true);
		}
		if (sectionId == null || userGroupTicketInfo.getExpandedSelection() == true) {
			userGroupTicketList.setPagedListInfo(userGroupTicketInfo);
			userGroupTicketList.setInMyUserGroups(user.getId());
			userGroupTicketList.setIncludeAllSites(true);
			userGroupTicketList.setOnlyOpen(true);
		}
		//All Tickets
		PagedListInfo allTicketsInfo = this.getPagedListInfo(
				context, "AllTicketsInfo", "t.entered", "desc");
		allTicketsInfo.setLink("TroubleTicketsAllerteNew.do?command=Home");
		if (sectionId == null) {
			if (!allTicketsInfo.getExpandedSelection()) {
				if (allTicketsInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
					allTicketsInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
				}
			} else {
				if (allTicketsInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
					allTicketsInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
				}
			}
		} else if (sectionId.equals(allTicketsInfo.getId())) {
			allTicketsInfo.setExpandedSelection(true);
		}
		if (sectionId == null || allTicketsInfo.getExpandedSelection()) {
			allTicketsList.setPagedListInfo(allTicketsInfo);
			allTicketsList.setSiteId(user.getSiteId());
			if (user.getSiteId() > -1) {
				allTicketsList.setExclusiveToSite(true);
				allTicketsList.setIncludeAllSites(false);
			}
			allTicketsList.setUnassignedToo(true);
			allTicketsList.setOnlyOpen(true);
		}
		try {
			db = this.getConnection(context);
			if (sectionId == null || assignedToMeInfo.getExpandedSelection()) {
				Hashtable l = assignedToMeList.costruisciStorico(db,user.getSiteId(),user.getRoleId());  //aggiunto da d.dauria
				ArrayList listaTipoAlimento = assignedToMeList.tipoAlimenti(db);
				ArrayList listaOrigine = assignedToMeList.origine(db);   
				context.getRequest().getSession().setAttribute("listaStorico", l);
				context.getRequest().getSession().setAttribute("listaTipoAlimento", listaTipoAlimento);
				context.getRequest().getSession().setAttribute("listaOrigine", listaOrigine);
			}
		
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "VisualizzaRichiesta", "Visualizza Richiesta");
		//   context.getRequest().setAttribute("CreatedByMeList", createdByMeList);
		context.getRequest().setAttribute("AssignedToMeList", assignedToMeList);
		// context.getRequest().setAttribute("OpenList", openList);
		// context.getRequest().setAttribute("AllTicketsList", allTicketsList);
		// context.getRequest().setAttribute("UserGroupTicketList", userGroupTicketList);
		addModuleBean(context, "VisualizzaRichiesta", "Visualizza Richiesta");
		return ("StoricoOK");
	}



	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandSearchTickets(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-view")) {
			return ("PermissionError");
		}
		PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListInfo");

		Connection db = null;
		User user = this.getUser(context, this.getUserId(context));
		TicketList ticList = new TicketList();
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		String dataApertura = (String)context.getRequest().getParameter("searchtimestampDataApertura");
		String dataApertura2 = (String)context.getRequest().getParameter("searchtimestampDataApertura2");
		String codiceAllerta = (String)context.getRequest().getParameter("searchcodiceAllerta"); 
		String stato = (String)context.getRequest().getParameter("searchStato");
		if( stato != null )
		{
			ticList.setStato(stato);
		}

		String stato_asl_corrente = context.getParameter( "searchcodeStatoAslCorrente" );
		if( stato_asl_corrente != null && !"-1".equalsIgnoreCase( stato_asl_corrente ) )
		{
			ticList.setStatoAslCorrente( stato_asl_corrente );
		}
		
		if(codiceAllerta != null && ! codiceAllerta.equals(""))
		{
			ticList.setCodiceAllerta(codiceAllerta);
		}
		
		ticListInfo.setColumnToSortBy("t.data_chiusura , t.data_apertura");
		ticListInfo.setSortOrder("desc");
		
		ticList.setDataApertura(dataApertura);
		ticList.setDataApertura2(dataApertura2);
		ticListInfo.setLink("TroubleTicketsAllerteNew.do?command=SearchTickets");
		ticList.setPagedListInfo(ticListInfo);
		ticListInfo.setSearchCriteria(ticList, context);
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		try 
		{
			db = this.getConnection(context);
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			  
			context.getRequest().setAttribute("SiteIdList", SiteIdList);


			if (ticList.getSiteId() == Constants.INVALID_SITE) {
				ticList.setSiteId(user.getSiteId());
				ticList.setIncludeAllSites(true);
			} else if (ticList.getSiteId() == -1) {
				ticList.setExclusiveToSite(true);
				ticList.setIncludeAllSites(false);
			} else {
				ticList.setExclusiveToSite(true);
				ticList.setIncludeAllSites(false);
			}
			if ("unassigned".equals(ticListInfo.getListView())) {
				ticList.setUnassignedToo(true);
				ticList.setBuildDepartmentTickets(true);
				ticList.setDepartment(
						thisUser.getUserRecord().getContact().getDepartment());
			} else if ("assignedToMe".equals(ticListInfo.getListView())) {
				ticList.setAssignedTo(getUserId(context));
			} else {
				ticList.setUnassignedToo(true);
				if ("createdByMe".equals(ticListInfo.getListView())) {
					ticList.setEnteredBy(getUserId(context));
				}
			}
			//set the status
			if (ticListInfo.getFilterKey("listFilter1") == 1) {
				ticList.setOnlyOpen(true);
			} else if (ticListInfo.getFilterKey("listFilter1") == 2) {
				ticList.setOnlyClosed(true);
			}

			ticList.buildList(db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "SearchTickets", "Search Tickets");
		context.getRequest().setAttribute("TicList", ticList);
		context.getSession().setAttribute("searchTickets", "yes");
		addModuleBean(context, "SearchTickets", "Search Tickets");
		return ("ResultsOK");
	}


	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandReopen(ActionContext context) {

		if (!(hasPermission(context, "tickets-tickets-edit"))) {
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(
					db, Integer.parseInt(context.getRequest().getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
				return ("PermissionError");
			}
			thisTicket.setModifiedBy(getUserId(context));
			resultCount = thisTicket.reopen(db);
			if (resultCount == -1) {
				return (executeCommandDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				return (executeCommandDetails(context));
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");
	}


	/**
	 * Description of the Method
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public String executeCommandInsert(ActionContext context) throws SQLException, IOException {
		if (!(hasPermission(context, "allerte-allerte-add"))) {
			return ("PermissionError");
		}
		logger.info("Inserimento Nuova Allerta") ;
		
		context.getSession().setAttribute("problemaInvioMail", "no");
		
		Connection db = null;
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		Ticket newTicket = null;
		Ticket allerta = null ;
		HttpServletRequest request = context.getRequest();
		String newContact = request.getParameter("contact");

		Ticket newTic = (Ticket) context.getFormBean();
		newTic.setTipoCampione(request.getParameter("TipoCampione"));
		newTic.setTipo_richiesta( request.getParameter("tipo_richiesta" ) );
		newTic.setEnteredBy(getUserId(context));
		newTic.setModifiedBy(getUserId(context));
		newTic.setNoteAnalisi(request.getParameter("noteAnalisi"));
		newTic.setUnitaMisura(Integer.parseInt(context.getRequest().getParameter("unitaMisura")));
		newTic.setDataApertura(request.getParameter("dataApertura"));
		newTic.setDenominazione_prodotto(context.getRequest().getParameter("denominazione_prodotto"));
		newTic.setNumero_lotto(context.getRequest().getParameter("numero_lotto"));
		newTic.setFabbricante_produttore(context.getRequest().getParameter("fabbricante_produttore"));
		newTic.setData_scadenza_allerta(context.getRequest().getParameter("data_scadenza_allerta"));
		
		newTic.setTipo_notifica_allerta(Integer.parseInt(context.getRequest().getParameter("tipo_notifica_allerta")));
		newTic.setNumero_notifica_allerta(context.getRequest().getParameter("numero_notifica_allerta"));
		
		boolean flag_tipo_allerta = false ;
		if(context.getParameter("flag_tipo_allerta")!=null)
		{
			flag_tipo_allerta = true ;
		}
		
		newTic.setFlagTipoAllerta(flag_tipo_allerta);
		
		boolean flag = false ;
		if(context.getParameter("flag_pubblicazione_allerte")!=null)
		{
			flag = true ;
		}
		
		newTic.setFlag_produzione(flag);
		if(flag == true)
		{
		newTic.setData_inizio_pubblicazione(context.getParameter("data_inizio_pubblicazione_allerte"));

		newTic.setData_fine_pubblicazione(context.getParameter("data_fine_pubblicazione_allerte"));
		newTic.setTipo_rischio(context.getParameter("tipo_rischio_allerte"));
		newTic.setProvvedimento_esito(context.getParameter("provvedimenti_esito_allerte"));
		}
		if(request.getParameter("Origine").equals("1") || request.getParameter("Origine").equals("2") ){

			newTic.setOrigineAllerta(Integer.parseInt(request.getParameter("aslOrigine")));

		}else{
			if(request.getParameter("Origine").equals("3") ){
				newTic.setOrigineAllerta(Integer.parseInt(request.getParameter("regioneOrigine")));		

			}

		}
		if (request.getParameter("inserita_da_sian")==null)
		{
			context.getRequest().setAttribute("allerte.insert.error", "Attenzione non si dispone dei permessi necessari per inserimento allerte Sian o Veterinari");
			return executeCommandAdd(context);
		}
		
		GenericControlliUfficiali.setParameterTipiAlimenti(null, request, newTic, "allerteNew");

		newTic.setProblem(request.getParameter("problem"));
		newTic.setOrigine(Integer.parseInt(request.getParameter("Origine")));
		newTic.setListaCommercializzazione(request.getParameter("ListaCommercializzazione"));
		newTic.setIdAllerta(request.getParameter("idAllerta"));
		newTic.setTipo_allerta(request.getParameter("tipo_allerta"));
		Boolean b = new Boolean(request.getParameter("inserita_da_sian"));
		newTic.setInserita_da_sian((b));
		newTic.setDescrizioneBreve(request.getParameter("oggettoAllerta"));

		try {
			db = this.getConnection(context);
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
			SanzioniAmministrative.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);

			LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
			SanzioniPenali.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			LookupList UnitaMisura = new LookupList(db,"lookup_unita_misura_allerta");
			UnitaMisura.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("UnitaMisura",UnitaMisura);
			
			LookupList altrialimenti = new LookupList(db, "lookup_altrialimenti_nonanimale");
			altrialimenti.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("AltriAlimenti", altrialimenti);

			LookupList TipoAlimento = new LookupList(db, "lookup_tipo_alimento");
			TipoAlimento.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAlimento", TipoAlimento);

			LookupList Origine = new LookupList(db, "lookup_origine");
			Origine.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Origine", Origine);

			LookupList AlimentoInteressato = new LookupList(db, "lookup_alimento_interessato");
			AlimentoInteressato.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentoInteressato", AlimentoInteressato);

			LookupList NonConformita = new LookupList(db, "lookup_non_conformita");
			NonConformita.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("NonConformita", NonConformita);

			LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
			ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);

			
			
			LookupList alimentinontrasformati = new LookupList(db,
			"lookup_alimenti_origine_animale_non_trasformati");
			alimentinontrasformati.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiNonTrasformati",
					alimentinontrasformati);
			
			LookupList molluschi = new LookupList(db,
			"lookup_molluschi");
			molluschi.addItem(-1,
			"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupmolluschi",
					molluschi);

			//aggiunto da d.dauria
			LookupList alimentinontrasformativalori = new LookupList(db,
			"lookup_alimenti_origine_animale_non_trasformati_valori");
			alimentinontrasformativalori.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiNonTrasformatiValori",
					alimentinontrasformativalori);

			//aggiunto da d.dauria
			LookupList alimentitrasformati = new LookupList(db,
			"lookup_alimenti_origine_animale_trasformati");
			alimentitrasformati.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiTrasformati",
					alimentitrasformati);

			//aggiunto da d.dauria
			LookupList alimentivegetali = new LookupList(db,
			"lookup_alimenti_origine_vegetale_macrocategorie");
			
			alimentivegetali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentiVegetali",
					alimentivegetali);

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);


			String tipoAlimentiVegetale=request.getParameter("tipoAlimento");
			if(request.getParameter("alimentiOrigineVegetale")!=null)
				if(request.getParameter("alimentiOrigineVegetale").equalsIgnoreCase("on")){
					newTic.setAlimentiOrigineVegetale(true);
					newTic.setIsvegetaletrasformato(Integer.parseInt(tipoAlimentiVegetale));
					if(tipoAlimentiVegetale.equals("0")){
						newTic.setAlimentiOrigineVegetaleValori(request.getParameter("alimentiOrigineVegetaleValoriNonTrasformati"));

					}else{
						if(tipoAlimentiVegetale.equals("1")){
							newTic.setAlimentiOrigineVegetaleValori(request.getParameter("alimentiOrigineVegetaleValoriTrasformati"));

						}

					}
				}

			
			
				
				if (isValid) {
					//Check if portal user can insert this record
				
					
				
						if (newTic.getOrgId() > 0) {
							newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
						}
						isValid = this.validateObject(context, db, newTic) && isValid;
						

						if (isValid) {
							if(newTic.getAlimentiOrigineAnimaleNonTrasformati()>-1 || newTic.getAlimentiOrigineAnimaleTrasformati()>-1){

								newTic.setNoteAlimenti(request.getParameter("notealimenti"));
							}else{

								if(newTic.getAlimentiOrigineVegetaleValori()>-1){
									newTic.setNoteAlimenti(request.getParameter("notealimenti2"));
								}		
							}
//							newTic.setCurr_val_sequence();
//							ResultSet rs = db.prepareStatement(newTic.getCurr_val_sequence()).executeQuery();
//							int seq_curr = -1 ;
//							if (rs.next())
//								seq_curr = rs.getInt(1);
//							logger.info("Inserimento allerta Valore sequence prima del nuovo inserimento "+seq_curr) ;
							recordInserted = newTic.insert(db,context);
							ArrayList<String> arr = new ArrayList<String>();
							if(context.getRequest().getParameter("size")!=null)
							{
								int size = Integer.parseInt(context.getRequest().getParameter("size"));
								for (int i = 1 ; i<=size; i++)
								{
									if(context.getRequest().getParameter("analitiId_"+i)!= null && !context.getRequest().getParameter("analitiId_"+i).equals(""))
									{
										int idFoglia = Integer.parseInt(context.getRequest().getParameter("analitiId_"+i));
										String path = context.getRequest().getParameter("pathAnaliti_"+i);
										arr.add(path);
										String delimiter = "->";
										String [] temp = path.split(delimiter);
										/* print substrings */
										for(int j =0;j< temp.length ; j++)
											temp [j] = temp[j];
										//Temp[0] contiene il tipo di esame
								
										newTic.insertAnaliti(db, idFoglia, path, temp[0]);
										
									}
								}
							}
							
							if(context.getRequest().getParameter("size1")!=null)
							{
								int size = Integer.parseInt(context.getRequest().getParameter("size1"));
								for (int i = 1 ; i<=size; i++)
								{
									if(context.getRequest().getParameter("idMatrice_"+i)!= null && !context.getRequest().getParameter("idMatrice_"+i).equals(""))
									{
										int idFoglia = Integer.parseInt(context.getRequest().getParameter("idMatrice_"+i));
										String path = context.getRequest().getParameter("path_"+i);
										newTic.insertMatrici(db, idFoglia, path);
									}

								}
							}
							
							
							
//							rs = db.prepareStatement(newTic.getCurr_val_sequence()).executeQuery();
//							 seq_curr = -1 ;
//							if (rs.next())
//								seq_curr = rs.getInt(1);
//							logger.info("Inserimento allerta Valore sequence dopo l'inserimento "+seq_curr) ;
							
							
							
						/*GESTIONE PREGRESSA MATRICI E ANALITI

							String [] tipi=null;
							if(newTic.getTipoCampione()==1){
								tipi=request.getParameterValues("TipoCampione_batteri");

							}
							if(newTic.getTipoCampione()==2){
								tipi=request.getParameterValues("TipoCampione_virus");

							}
							if(newTic.getTipoCampione()==4){
								tipi=request.getParameterValues("TipoCampione_parassiti");

							}

							if(newTic.getTipoCampione()==8){
								tipi=request.getParameterValues("TipoCampione_fisico");

							}

							String tipo="";
							if(newTic.getTipoCampione()==5){
								tipo=request.getParameter("TipoCampione_chimico");
								tipi=new String[1];
								tipi[0]=tipo;
								String[] tipiChimici=null;
								if(tipo.equals("1")){
									tipiChimici=	request.getParameterValues("TipoCampione_sottochimico");


								}else{

									if(tipo.equals("2")){
										tipiChimici=	request.getParameterValues("TipoCampione_sottochimico2");


									}else{
										if(tipo.equals("3")){
											tipiChimici=	request.getParameterValues("TipoCampione_sottochimico3");

										}else{
											if(tipo.equals("4")){
												tipiChimici=	request.getParameterValues("TipoCampione_sottochimico4");


											}else{
												if(tipo.equals("5")){
													tipiChimici=	request.getParameterValues("TipoCampione_sottochimico5");

												}
												else{
													tipiChimici=new String[1];
													tipiChimici[0]=tipo;
												}
											}
										}

									}


								}

								newTic.insertTipoChimico(db, tipiChimici);

							}


							newTic.insertTipoCampione(db, tipi);

							String[] tipiAlimentiOrigineVegetale=null;

							if(newTic.getAlimentiOrigineVegetaleValori()==1){

								tipiAlimentiOrigineVegetale=request.getParameterValues("fruttaFresca");

							}else{
								if(newTic.getAlimentiOrigineVegetaleValori()==2){
									tipiAlimentiOrigineVegetale=request.getParameterValues("fruttaSecca");	

								}else{
									if(newTic.getAlimentiOrigineVegetaleValori()==4){//ortaggi
										tipiAlimentiOrigineVegetale=request.getParameterValues("ortaggi");

									}else{
										if(newTic.getAlimentiOrigineVegetaleValori()==5){//funghi
											tipiAlimentiOrigineVegetale=request.getParameterValues("funghi");

										}else{
											if(newTic.getAlimentiOrigineVegetaleValori()==6){//derivati
												tipiAlimentiOrigineVegetale=request.getParameterValues("derivati");

											}else{
												if(newTic.getAlimentiOrigineVegetaleValori()==7){//conservati
													tipiAlimentiOrigineVegetale=request.getParameterValues("conservati");

												}
												else{
													if(newTic.getAlimentiOrigineVegetaleValori()==8){

														tipiAlimentiOrigineVegetale=request.getParameterValues("graqssi");

													}
													else{
														if(newTic.getAlimentiOrigineVegetaleValori()==9){

															tipiAlimentiOrigineVegetale=request.getParameterValues("vino");
														}
														else{
															if(newTic.getAlimentiOrigineVegetaleValori()==11){

																tipiAlimentiOrigineVegetale=request.getParameterValues("zuppe");

															}
														}
													}
												}

											}

										}

									}

								}	

							}

							newTic.insertAlimentiOrigineVegetale(db, tipiAlimentiOrigineVegetale);
*/
						}
						
					}
			
			
			if (recordInserted) {
				
				//Parametri lista distribuzione
				
				ListaDistribuzione ldd = new ListaDistribuzione();
				if ("1".equals(context.getRequest().getParameter("ListaCommercializzazione"))){
					ldd.setId_allerta(newTic.getId());
					ldd.setData_lista(DatabaseUtils.parseDateToTimestamp(context.getRequest().getParameter("data_lista")));
					ldd.setData_chiusura(DatabaseUtils.parseDateToTimestamp(context.getRequest().getParameter("data_chiusura")));
					ldd.setNome_fornitore(context.getRequest().getParameter("nome_fornitore"));
					ldd.store(db);
				}
				
				
				AllerteAslHistory historyAslAllerta = new AllerteAslHistory();
				AllerteHistory historyAllerta = new AllerteHistory();
				historyAllerta.setId_allerta(newTic.getId());
				historyAllerta.setId_ldd(ldd.getId());
				historyAllerta.setData_operazione(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
				historyAllerta.setTipo_operazione("INSERIMENTO");
				historyAllerta.setUser_id(newTic.getEnteredBy());
				historyAllerta.setId(db);
				historyAllerta.store(db);
				allerta = new Ticket(db,newTic.getId());
				String[] asl_coinvolte = request.getParameterValues( "asl_coinvolte" );
				int cuPianificati=-1;
				UserBean user = (UserBean) context.getSession().getAttribute("User");
				boolean flag_sian = false ; 
				if (user.getRoleId()==37)
				{
					flag_sian = true ;
				}
				
				for( String asl: asl_coinvolte )
				{
					String cuAsl=request.getParameter("cu_"+asl);
					 try {
						 if(!cuAsl.equals("") ){
								cuPianificati=Integer.parseInt(cuAsl);
							}else{
								cuPianificati=-1;
							}
						}
						catch(NumberFormatException nFE) {
								logger1.error ("Not an Integer value for Num.CU");
						}
					
					AslCoinvolte ac = new AslCoinvolte();
					if(asl.equals("16")){
						ac.setNoteFuoriRegione(request.getParameter("noteFuoriRegione"));
					}else{
						ac.setNoteFuoriRegione("");
					}
					
					
					ac.setId_ldd(ldd.getId());
					
					ac.setId_allerta( newTic.getId() );
					ac.setId_asl( Integer.parseInt( asl ) );
					ac.setCu_pianificati_da( getUserId(context) );
					ac.setControlliUfficialiRegionaliPianificati(cuPianificati);
					ac.setCu_pianificati(cuPianificati);
					ac.store( db );
					historyAslAllerta.setAsl(Integer.parseInt( asl ));
					historyAslAllerta.setCu_pianificati_regione(cuPianificati);
					historyAslAllerta.setData(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
					historyAslAllerta.setId_allerte_history(historyAllerta.getId());
					historyAslAllerta.setTipo_operazione("AGGIUNTA ASL");
					historyAslAllerta.setStato_allegatof("Non Inviato");
					historyAslAllerta.setStato(Integer.parseInt( asl ),newTic.getId(),db);
					historyAslAllerta.store(db);
				
				}
				
//				try
//				{
//				for( String asl: asl_coinvolte )
//				{
//					GestionePEC.preparaSpedisciPECallerta(asl, allerta,flag_sian);
//				}
//				}catch(EmailException mailex)
//				{
//					context.getSession().setAttribute("problemaInvioMail", "si");
//				}
				
				Hashtable<String, AslCoinvolte> aslcoinvolte = AslCoinvolte.getAslConvolteByIdLdd(ldd.getId(),newTic.getIdAllerta(),newTic.getId(),true, db);
				for (String kiave : aslcoinvolte.keySet())
				{
					AslCoinvolte temp = aslcoinvolte.get(kiave);
					
					ImpreseCoinvolte imprese_coinvolte = new ImpreseCoinvolte();
					
					String[] impreseCoinvolte = request.getParameterValues("ragione_sociale"+temp.getId_asl());
					String[] indirizziImpreseCoinvolte = request.getParameterValues("indirizzo_"+temp.getId_asl());
					
					imprese_coinvolte.setIdAslcoinvolta(temp.getId());
					imprese_coinvolte.setImpreseCoinvolte(ImpreseCoinvolte.convertiInLista(impreseCoinvolte));
					imprese_coinvolte.setIndirizziImpreseCoinvolte(ImpreseCoinvolte.convertiInLista(indirizziImpreseCoinvolte));
					imprese_coinvolte.setIdAsl(temp.getId_asl());
					imprese_coinvolte.store(db);
					
				}
				
				//Prepare the ticket for the response
				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);
				
				addRecentItem(context, newTicket);
				processInsertHook(context, newTicket);

			} else {
				if (newTic.getOrgId() != -1) {
					Organization thisOrg = new Organization(db, newTic.getOrgId());
					newTic.setCompanyName(thisOrg.getName());
				}
			}
			//db.commit();

		}
		catch (Exception e) {
			
			try {
				db.rollback();
				logger.severe("Errore inserimento nuova allerta effettuto rollback database") ;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (newTic!=null)
			{
				if(newTic.getReset_sequence_roll()!=null && ! "".equals(newTic.getReset_sequence_roll()))
				{
					try {
						ResultSet rs = db.prepareStatement(newTic.getCurr_val_sequence()).executeQuery();
						int seq_curr = -1 ;
						if (rs.next())
							seq_curr = rs.getInt(1);
						logger.severe("Errore Inserimento allerte Valore sequence prima del rollback "+seq_curr) ;
						db.prepareStatement(newTic.getReset_sequence_roll()).execute();
						
						rs = db.prepareStatement(newTic.getCurr_val_sequence()).executeQuery();
						seq_curr = -1 ;
						if (rs.next())
							seq_curr = rs.getInt(1);
						
						logger.severe("Errore Inserimento allerte Valore sequence dopo rollback "+seq_curr) ;
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "ViewTickets", "Ticket Insert ok");
		if (recordInserted && isValid) {

		
			String retPage = "DetailsOK";
			String tipo_richiesta = newTic.getTipo_richiesta();
			tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

			retPage = "InsertOK";

			
			
//			if(context.getRequest().getParameter("ListaCommercializzazione").equalsIgnoreCase("1"))
//			{
//				context.getRequest().setAttribute("tId", ""+newTic.getId());
//				context.getRequest().setAttribute("parentId", "-1");
//				context.getRequest().setAttribute("folderId", "-1");
//				context.getRequest().setAttribute("ListaCommercializzazzione", "1");
//				context.getRequest().setAttribute("idAllerta", allerta.getIdAllerta());
//				
//				context.getRequest().setAttribute("ticketId", ""+newTic.getId());
//				context.getRequest().setAttribute("op", "allerte");
//				GestioneAllegatiUpload gu = new GestioneAllegatiUpload();
//				return gu.executeCommandListaAllegati(context);
//				
//				//return "GoToUploadOK";
//			}
			

			//return ( retPage );//("InsertOK");
			context.getRequest().setAttribute("idticket", newTic.getId());
			return executeCommandDetails(context); 
		}
		return (executeCommandAdd(context));
	}
	
	public String executeCommandUploadToInsert(ActionContext context) {
		
		Connection db = null;
		String ticketid = context.getRequest().getParameter("tId");
		try
		{
			db = this.getConnection(context);
			
			Ticket newTicket = new Ticket(db, Integer.parseInt(ticketid));
			context.getRequest().setAttribute("TicketDetails",newTicket);
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("tId",ticketid);
		context.getRequest().setAttribute("parentId",context.getRequest().getParameter("parentId"));
		context.getRequest().setAttribute("folderId",context.getRequest().getParameter("folderId"));
		context.getRequest().setAttribute("ListaCommercializzazzione",context.getRequest().getParameter("ListaCommercializzazzione"));
		context.getRequest().setAttribute("idAllerta",context.getRequest().getParameter("idAllerta"));
		
		return "UploadOK";
	
	}

	/**
	 * Prepares supplemental form data that a user can search by
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandSearchTicketsForm(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		//Prepare ticket state form data
	
		PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListInfo");
		try {
			db = this.getConnection(context);
			//Prepare severity list form data
				//aggiunto da d.dauria
			LookupList TipoAlimento = new LookupList(db, "lookup_tipo_alimento");
			TipoAlimento.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAlimento", TipoAlimento);

			LookupList Origine = new LookupList(db, "lookup_origine");
			Origine.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Origine", Origine);

			LookupList AlimentoInteressato = new LookupList(db, "lookup_alimento_interessato");
			AlimentoInteressato.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentoInteressato", AlimentoInteressato);

			LookupList NonConformita = new LookupList(db, "lookup_non_conformita");
			NonConformita.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("NonConformita", NonConformita);

			LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
			ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);

	//check if account/owner is already selected, if so build it
			if (!"".equals(ticListInfo.getSearchOptionValue("searchcodeOrgId")) && !"-1".equals(
					ticListInfo.getSearchOptionValue("searchcodeOrgId"))) {
				String orgId = ticListInfo.getSearchOptionValue("searchcodeOrgId");
				Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
				context.getRequest().setAttribute("OrgDetails", thisOrg);
			}

			if (!"".equals(ticListInfo.getSearchOptionValue("searchcodeUserGroupId")) && !"-1".equals(ticListInfo.getSearchOptionValue("searchcodeUserGroupId"))) {
				String groupId = ticListInfo.getSearchOptionValue("searchcodeUserGroupId");
				UserGroup group = new UserGroup(db, Integer.parseInt(groupId));
				context.getRequest().setAttribute("userGroup", group);
			}

			//sites lookup
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteList", siteList);

			return ("SearchTicketsFormOK");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	/**
	 * Description of the Method
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandUpdate(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-edit")) {
			return ("PermissionError");
		}
		int resultCount = 0;
		int catCount = 0;
		TicketCategory thisCat = null;
		boolean catInserted = false;
		boolean isValid = true;
		Ticket newTic = (Ticket) context.getFormBean();
		HttpServletRequest request = context.getRequest();
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);
			
			Organization orgDetails = new Organization();//(db, newTic.getOrgId());
			
			
			newTic.setId(context.getRequest().getParameter("id"));
			Ticket tt = new Ticket( db,  newTic.getId()  );
			
			if(context.getRequest().getParameter("dataApertura") != null && !"".equals(context.getRequest().getParameter("dataApertura")))
				newTic.setDataApertura(context.getRequest().getParameter("dataApertura"));
			else
			{
				
				newTic.setDataApertura(tt.getDataApertura());
			}
				
			
			newTic.setTipoCampione(request.getParameter("TipoCampione"));
			newTic.setUnitaMisura(Integer.parseInt(context.getRequest().getParameter("unitaMisura")));
			newTic.setNoteAnalisi(request.getParameter("noteAnalisi"));
			newTic.setDenominazione_prodotto(context.getRequest().getParameter("denominazione_prodotto"));
			newTic.setNumero_lotto(context.getRequest().getParameter("numero_lotto"));
			newTic.setFabbricante_produttore(context.getRequest().getParameter("fabbricante_produttore"));
			newTic.setData_scadenza_allerta(context.getRequest().getParameter("data_scadenza_allerta"));
			
			boolean flag_tipo_allerta = false ;
			if("on".equalsIgnoreCase(context.getParameter("flag_tipo_allerta")))
			{
				flag_tipo_allerta = true ;
			}
			
			newTic.setFlagTipoAllerta(flag_tipo_allerta);
			
			boolean flag = false ;
			if("on".equalsIgnoreCase(context.getParameter("flag_pubblicazione_allerte")))
			{
				flag = true ;
			}
			
			
			newTic.setFlag_produzione(flag);
			if(flag == true)
			{
			newTic.setData_inizio_pubblicazione(context.getParameter("data_inizio_pubblicazione_allerte"));

			newTic.setData_fine_pubblicazione(context.getParameter("data_fine_pubblicazione_allerte"));
			newTic.setTipo_rischio(context.getParameter("tipo_rischio_allerte"));
			newTic.setProvvedimento_esito(context.getParameter("provvedimenti_esito_allerte"));
			}
			GenericControlliUfficiali.setParameterTipiAlimenti(null, request, newTic, "allerteNew");
			if("1".equals(request.getParameter("Origine")) ||  "2".equals(request.getParameter("Origine")) ){

				newTic.setOrigineAllerta(Integer.parseInt(request.getParameter("aslOrigine")));

			}else{
				if("".equals(request.getParameter("Origine").equals("3"))){
					newTic.setOrigineAllerta(Integer.parseInt(request.getParameter("regioneOrigine")));		

				}

			}

			newTic.setDescrizioneBreve(request.getParameter("oggettoAllerta"));	
			newTic.setProblem(request.getParameter("problem"));
			newTic.setOrigine(Integer.parseInt(request.getParameter("Origine")));
			newTic.setListaCommercializzazione(request.getParameter("ListaCommercializzazione"));
			newTic.setIdAllerta(request.getParameter("idAllerta"));
			
			newTic.setTipo_notifica_allerta(Integer.parseInt(context.getRequest().getParameter("tipo_notifica_allerta")));
			newTic.setNumero_notifica_allerta(context.getRequest().getParameter("numero_notifica_allerta"));
			
			
			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
			SanzioniAmministrative.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);

			LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
			SanzioniPenali.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			LookupList UnitaMisura = new LookupList(db,"lookup_unita_misura_allerta");
			UnitaMisura.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("UnitaMisura",UnitaMisura);
			
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList TipoAlimento = new LookupList(db, "lookup_tipo_alimento");
			TipoAlimento.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAlimento", TipoAlimento);

			LookupList Origine = new LookupList(db, "lookup_origine");
			Origine.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Origine", Origine);

			LookupList AlimentoInteressato = new LookupList(db, "lookup_alimento_interessato");
			AlimentoInteressato.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AlimentoInteressato", AlimentoInteressato);

			LookupList altrialimenti = new LookupList(db, "lookup_altrialimenti_nonanimale");
			altrialimenti.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("AltriAlimenti", altrialimenti);

			LookupList NonConformita = new LookupList(db, "lookup_non_conformita");
			NonConformita.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("NonConformita", NonConformita);

			LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
			ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);

			newTic.setTipo_richiesta( request.getParameter( "tipo_richiesta" ) );
			newTic.setModifiedBy(getUserId(context));
			newTic.setNoteAnalisi(request.getParameter("noteAnalisi"));

			//Get the previousTicket, update the ticket, then send both to a hook
			Ticket previousTicket = new Ticket(db, newTic.getId());
			
			newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
			isValid = this.validateObject(context, db, newTic) && isValid;

			String tipoAlimentiVegetale=request.getParameter("tipoAlimento");
			if(request.getParameter("alimentiOrigineVegetale")!=null)
				if(request.getParameter("alimentiOrigineVegetale").equalsIgnoreCase("on")){
					newTic.setAlimentiOrigineVegetale(true);

					newTic.setIsvegetaletrasformato(Integer.parseInt(tipoAlimentiVegetale));
					if(tipoAlimentiVegetale.equals("0")){
						newTic.setAlimentiOrigineVegetaleValori(request.getParameter("alimentiOrigineVegetaleValoriNonTrasformati"));

					}else{
						if(tipoAlimentiVegetale.equals("1")){
							newTic.setAlimentiOrigineVegetaleValori(request.getParameter("alimentiOrigineVegetaleValoriTrasformati"));

						}

					}
				}

			if (isValid) {
				
				ArrayList<String> arr = new ArrayList<String>();
				if(context.getRequest().getParameter("size")!=null)
				{
					int size = Integer.parseInt(context.getRequest().getParameter("size"));
					for (int i = 1 ; i<=size; i++)
					{
						if(context.getRequest().getParameter("analitiId_"+i)!= null && !context.getRequest().getParameter("analitiId_"+i).equals(""))
						{
							int idFoglia = Integer.parseInt(context.getRequest().getParameter("analitiId_"+i));
							String path = context.getRequest().getParameter("pathAnaliti_"+i);
							String delimiter = "->";
							String [] temp = path.split(delimiter);
							/* print substrings */
							//for(int j =0;j< temp.length ; j++)
							//	temp [j] = temp[j];
							//Temp[0] contiene il tipo di esame
					
							newTic.updateAnaliti(db, idFoglia, path, temp[0]);
							arr.add(path);
						}
					}
				}
				
				if(context.getRequest().getParameter("size1")!=null)
				{
					int size = Integer.parseInt(context.getRequest().getParameter("size1"));
					for (int i = 1 ; i<=size; i++)
					{
						if(context.getRequest().getParameter("idMatrice_"+i)!= null && !context.getRequest().getParameter("idMatrice_"+i).equals(""))
						{
							int idFoglia = Integer.parseInt(context.getRequest().getParameter("idMatrice_"+i));
							String path = context.getRequest().getParameter("path_"+i);
							newTic.updateMatrici(db, idFoglia, path);
						}

					}
				}

				
				
				GenericControlliUfficiali.setParameterTipiAlimenti(null, request, newTic, "allerteNew");


				String[] tipiAlimentiOrigineVegetale=null;

				if(newTic.getAlimentiOrigineVegetaleValori()==1){

					tipiAlimentiOrigineVegetale=request.getParameterValues("fruttaFresca");

				}else{
					if(newTic.getAlimentiOrigineVegetaleValori()==2){
						tipiAlimentiOrigineVegetale=request.getParameterValues("fruttaSecca");	

					}else{
						if(newTic.getAlimentiOrigineVegetaleValori()==4){//ortaggi
							tipiAlimentiOrigineVegetale=request.getParameterValues("ortaggi");

						}else{
							if(newTic.getAlimentiOrigineVegetaleValori()==5){//funghi
								tipiAlimentiOrigineVegetale=request.getParameterValues("funghi");

							}else{
								if(newTic.getAlimentiOrigineVegetaleValori()==6){//derivati
									tipiAlimentiOrigineVegetale=request.getParameterValues("derivati");

								}else{
									if(newTic.getAlimentiOrigineVegetaleValori()==7){//conservati
										tipiAlimentiOrigineVegetale=request.getParameterValues("conservati");

									}else{
										if(newTic.getAlimentiOrigineVegetaleValori()==8){//conservati
											tipiAlimentiOrigineVegetale=request.getParameterValues("grassi");

										}else{
											if(newTic.getAlimentiOrigineVegetaleValori()==9){//conservati
												tipiAlimentiOrigineVegetale=request.getParameterValues("vino");

											}else{

												if(newTic.getAlimentiOrigineVegetaleValori()==11){//conservati
													tipiAlimentiOrigineVegetale=request.getParameterValues("zuppe");

												}

											}
										}
									}

								}
							}

						}
					}	
				}
				newTic.aggiornaAlimentiOrigineVegetale(db, tipiAlimentiOrigineVegetale);

				String [] tipi=null;
				if(newTic.getTipoCampione()==1){
					tipi=request.getParameterValues("TipoCampione_batteri");

				}
				if(newTic.getTipoCampione()==2){
					tipi=request.getParameterValues("TipoCampione_virus");

				}
				if(newTic.getTipoCampione()==4){
					tipi=request.getParameterValues("TipoCampione_parassiti");

				}


				if(newTic.getTipoCampione()==8){
					tipi=request.getParameterValues("TipoCampione_fisico");

				}
				String tipo="";
				String[] tipiChimici=null;
				if(newTic.getTipoCampione()==5){
					tipo=request.getParameter("TipoCampione_chimico");
					tipi=new String[1];
					tipi[0]=tipo;

					if(tipo.equals("1")){
						tipiChimici=	request.getParameterValues("TipoCampione_sottochimico");


					}else{

						if(tipo.equals("2")){
							tipiChimici=	request.getParameterValues("TipoCampione_sottochimico2");


						}else{
							if(tipo.equals("3")){
								tipiChimici=	request.getParameterValues("TipoCampione_sottochimico3");

							}else{
								if(tipo.equals("4")){
									tipiChimici=	request.getParameterValues("TipoCampione_sottochimico4");


								}else{
									if(tipo.equals("5")){
										tipiChimici=	request.getParameterValues("TipoCampione_sottochimico5");

									}
									else{
										tipiChimici=new String[1];
										tipiChimici[0]=tipo;
									}
								}
							}

						}
					}

				}

				
				LookupList AnimaliNonAlimentari = new LookupList(db,
				"lookup_animali_non_alimentari");
				AnimaliNonAlimentari.addItem(-1,
				"-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("AnimaliNonAlimentari",
						AnimaliNonAlimentari);


				newTic.aggiornatipiCampioni(db, tipi, tipiChimici);
				resultCount = newTic.update(db);

//				AllerteHistory historyAllerta = new AllerteHistory();
//				historyAllerta.setId_allerta(newTic.getId());
//				historyAllerta.setData_operazione(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
//				if(context.getRequest().getParameter("ripianificazione")!=null)
//				{
//					historyAllerta.setTipo_operazione("MODIFICA - R");
//				}
//				else
//				{
//					historyAllerta.setTipo_operazione("MODIFICA");
//				}
//				historyAllerta.setUser_id(newTic.getModifiedBy());
//				historyAllerta.setId(db);
//				historyAllerta.setMotivo(context.getRequest().getParameter("motivo_ripianificazione_modifica"));
//				historyAllerta.store(db);
//				UserBean user = (UserBean) context.getSession().getAttribute("User");
//				boolean flag_sian = false ; 
//				if (newTic.getIdAllerta().contains("S"))
//				{
//					flag_sian = true ;
//				}
//				AslCoinvolte.updateAslCoinvolteAllerta( newTic.getId(), request.getParameterValues("asl_coinvolte"), getUserId(context), db,request,newTic,historyAllerta.getId(),flag_sian);
//				
//				
//				Hashtable<String, AslCoinvolte> aslcoinvolte = AslCoinvolte.getAslConvolte(newTic.getId(),newTic.getIdAllerta(),true, db);
//				for (String kiave : aslcoinvolte.keySet())
//				{
//					AslCoinvolte temp = aslcoinvolte.get(kiave);
//					
//					ImpreseCoinvolte imprese_coinvolte = new ImpreseCoinvolte();
//					
//					String[] impreseCoinvolte = request.getParameterValues("pippo_"+temp.getId_asl());
//					String[] indirizziImpreseCoinvolte = request.getParameterValues("indirizzo_"+temp.getId_asl());
//					
//					imprese_coinvolte.setIdAslcoinvolta(temp.getId());
//					imprese_coinvolte.delete(db);
//					imprese_coinvolte.setImpreseCoinvolte(ImpreseCoinvolte.convertiInLista(impreseCoinvolte));
//					imprese_coinvolte.setIndirizziImpreseCoinvolte(ImpreseCoinvolte.convertiInLista(indirizziImpreseCoinvolte));
//					imprese_coinvolte.setIdAsl(temp.getId_asl());
//					imprese_coinvolte.store(db);
//					
//				}
				
				String isPregresso = request.getParameter("pregresso");
				newTic.setIsPregresso(isPregresso);
				//newTic.updateIsPregresso(db);
				
				if(isPregresso!=null && isPregresso.equals("si")){
					String dataChiusura = request.getParameter("dataChiusura");
					if(dataChiusura != null && !dataChiusura.equals(""))
					{
						SimpleDateFormat sdfr = new SimpleDateFormat ("dd/MM/yyyy");
						Date data_chiusura = sdfr.parse(dataChiusura);
						Timestamp timestampChiusura = new Timestamp (data_chiusura.getTime());
						
						int id_asl			= getUser( context, getUserId(context) ).getSiteId();
						
					
						
						
						try
						{
							
							/**
							 * per id asl > 00 non accadra mai in quanto un supervisore di un asl non puo inserire una allerta 
							 */
							
							db = this.getConnection(context);
							if( id_asl > 0 )
							{
								AslCoinvolte ac = AslCoinvolte.load(newTic.getId() , id_asl, db );
								if( ac != null )
								{
									ac.setData_chiusura( timestampChiusura );
									ac.setChiusa_da( getUserId(context) );
									ac.update( db );
								}
							}
							else
							{
								
								AslCoinvolte.chiusura_pregresso(""+newTic.getId(), getUserId(context),timestampChiusura, db);
								
								
								tt.setDataChiusura( timestampChiusura );
								tt.setModifiedBy( getUserId(context) );
								tt.setModified( timestampChiusura );
								
									
								tt.update(db);
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
							context.getRequest().setAttribute("problemaInvioMail", "si");
						}
						finally
						{
							this.freeConnection(context, db);
						}
						
					}
					
				}
	
				}
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			context.getRequest().setAttribute("problemaInvioMail", "si");
			
		} finally {
			this.freeConnection(context, db);
		}
		if (resultCount == 1) {
			if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
			"return").equals("list")) {
				return (executeCommandHome(context));
			}
			
			context.getRequest().setAttribute("idticket", newTic.getId());
			return executeCommandDetails(context);
			
		}
		return (executeCommandModify(context));
	}


	public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "sanzioni-sanzioni-edit"))) {
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(
					db, Integer.parseInt(context.getRequest().getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
				return ("PermissionError");
			}
			thisTicket.setModifiedBy(getUserId(context));
			resultCount = thisTicket.chiudi(db);
			if (resultCount == -1) {
				return (executeCommandDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				return (executeCommandDetails(context));
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");


	}



	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!(hasPermission(context, "allerte-allerte-delete"))) {
			return ("PermissionError");
		}
		HtmlDialog htmlDialog = new HtmlDialog();
		Ticket ticket = null;
		String id = context.getRequest().getParameter("id");
		Connection db = null;
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			ticket = new Ticket(db, Integer.parseInt(id));
			//check permission to record
			if (!isRecordAccessPermitted(context, db, ticket.getOrgId())) {
				return ("PermissionError");
			}
			String returnType = (String) context.getRequest().getParameter("return");
			DependencyList dependencies = ticket.processDependencies(db);
			dependencies.setSystemStatus(systemStatus);
			htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));

			htmlDialog.addMessage(
					systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
			htmlDialog.setDeleteUrl("TroubleTicketsAllerteNew.do?command=Trash&id=" + id + "&return=searchResults" + RequestUtils.addLinkParams(
								context.getRequest(), "popup|popupType|actionId") + "'");
			/*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
			if ("searchResults".equals(returnType)) {
				htmlDialog.addButton(
						systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='TroubleTicketsAllerteNew.do?command=Trash&id=" + id + "&return=searchResults" + RequestUtils.addLinkParams(
								context.getRequest(), "popup|popupType|actionId") + "'");

			} else {
				htmlDialog.addButton(
						systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='TroubleTicketsAllerteNew.do?command=Trash&id=" + id + RequestUtils.addLinkParams(
								context.getRequest(), "popup|popupType|actionId") + "'");
			}
			htmlDialog.addButton(
					systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getSession().setAttribute("Dialog", htmlDialog);
		return ("ConfirmDeleteOK");
	}


	/**
	 * Deletes the specified ticket and triggers any hooks
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDelete(ActionContext context) {
		if (!hasPermission(context, "tickets-tickets-delete")) {
			return ("PermissionError");
		}
		boolean recordDeleted = false;
		Ticket thisTic = null;
		Connection db = null;
		//Parameters
		String passedId = context.getRequest().getParameter("id");
		try {
			db = this.getConnection(context);
			thisTic = new Ticket(db, Integer.parseInt(passedId));
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
				return ("PermissionError");
			}
			recordDeleted = thisTic.delete(db, getDbNamePath(context));
			if (recordDeleted) {
				processDeleteHook(context, thisTic);
				//del

				String returnType = (String) context.getRequest().getParameter(
						"return");
				if ("searchResults".equals(returnType)) {
					context.getRequest().setAttribute(
							"refreshUrl", "TroubleTicketsSanzioni.do?command=SearchTickets" + RequestUtils.addLinkParams(
									context.getRequest(), "popup|popupType|actionId"));
				} else {
					context.getRequest().setAttribute(
							"refreshUrl", "TroubleTicketsSanzioni.do?command=Home" + RequestUtils.addLinkParams(
									context.getRequest(), "popup|popupType|actionId"));
				}
				return ("DeleteOK");
			}
			return (executeCommandHome(context));
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	public String executeCommandAssegnaNumeroCU(ActionContext context)
	{
		if (!hasPermission(context, "allerte-allerte-cu-view")) {
			return ("PermissionError");
		}

		Connection db		= null;
		String id_allerta	= context.getParameter( "id_allerta" );
		String numero_cu	= context.getParameter( "cu" );
		String motivazione =  context.getParameter( "motivazione" );


		int id_asl			= getUser( context, getUserId(context) ).getSiteId();

		try
		{
			db = this.getConnection(context);
			AslCoinvolte ac = AslCoinvolte.load( Integer.parseInt(id_allerta) , id_asl, db );
			if((""+ac.getControlliUfficialiRegionaliPianificati()).equals(numero_cu))
				motivazione="";

			if( ac != null )
			{
				ac.setCu_pianificati( Integer.parseInt( numero_cu ) );


				ac.setMotivazione(motivazione);

				ac.update( db );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return executeCommandDetails(context);
	}

	
	private Ticket addTicket(ActionContext context, Connection db) throws SQLException {
	    String ticketId = (String) context.getRequest().getParameter("tId");
	    if (ticketId == null) {
	      ticketId = (String) context.getRequest().getAttribute("tId");
	    }
	    return addTicket(context, db, ticketId);
	  }
	
	  private Ticket addTicket(ActionContext context, Connection db, String ticketId) throws SQLException {
		    context.getRequest().setAttribute("tId", ticketId);
		    Ticket thisTicket = new Ticket(db, Integer.parseInt(ticketId));
		    context.getRequest().setAttribute("TicketDetails", thisTicket);
		    return thisTicket;
		  }
	
	  
	  
	  public String executeCommandUploadEClose(ActionContext context) throws SQLException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, EmailException {

		    Connection db = null;

		    boolean recordInserted = false;
		    boolean isValid = false;
		    Ticket  newTic = null;
		    String id = "";
		    HashMap parts = null;
		    try {
		      String filePath = this.getPath(context, "tickets");

		      //Process the form data
		      HttpMultiPartParser multiPart = new HttpMultiPartParser();
		      multiPart.setUsePathParam(false);
		      multiPart.setUseUniqueName(true);
		      multiPart.setUseDateForFolder(true);
		      multiPart.setExtensionId(getUserId(context));
		       parts = multiPart.parseData(context.getRequest(), filePath);
		      db = getConnection(context);
		       id = (String) parts.get("id");
		      
		      newTic = new Ticket (db,Integer.parseInt(id));
		      String subject = (String) parts.get("subject");
		      String folderId = (String) parts.get("folderId");
		      if (folderId != null) {
		        context.getRequest().setAttribute("folderId", folderId);
		      }
		      if (subject != null) {
		        context.getRequest().setAttribute("subject", subject);
		      }
		      //Load the ticket and the organization
		      Ticket thisTicket = addTicket(context, db, id);
		      int ticketId = thisTicket.getId();
		      //check permission to record
//		      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//		        return ("PermissionError");
//		      }

		      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {

		        //Update the database with the resulting file
		        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

		        FileItem thisItem = new FileItem();
		        thisItem.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
		        thisItem.setLinkItemId(ticketId);
		        thisItem.setEnteredBy(getUserId(context));
		        thisItem.setModifiedBy(getUserId(context));
		        thisItem.setFolderId(Integer.parseInt(folderId));
		        thisItem.setSubject(subject);
		        thisItem.setClientFilename(newFileInfo.getClientFileName());
		        thisItem.setFilename(newFileInfo.getRealFilename());
		        thisItem.setVersion(1.0);
		        thisItem.setSize(newFileInfo.getSize());

		        isValid = this.validateObject(context, db, thisItem);
		        if (isValid) {
		          recordInserted = thisItem.insert(db);
		          
			     
			    
		          
		        }
		      } else {
		        recordInserted = false;
		        HashMap errors = new HashMap();
		        SystemStatus systemStatus = this.getSystemStatus(context);
		        errors.put(
		            "actionError", systemStatus.getLabel(
		                "object.validation.incorrectFileName"));
		        if (subject != null && "".equals(subject.trim())) {
		          errors.put(
		              "subjectError", systemStatus.getLabel(
		                  "object.validation.required"));
		        }
		        processErrors(context, errors);
		      }
		    } catch (Exception e) {
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		    } finally {
		      freeConnection(context, db);
		    }
		    
		    if(context.getRequest().getParameter("allegato")!=null){
		    context.getRequest().setAttribute("isAllegato", "1");
		    return (executeCommandAdd(context));
		    	
		    }else{

		    	int id_asl			= getUser( context, getUserId(context) ).getSiteId();
				
				String chiusuraUfficio = (String) parts.get("chiusuraUfficio");
				
				
				try
				{
					
					if( id_asl > 0 )
					{
						AslCoinvolte ac = AslCoinvolte.load(Integer.parseInt(id) , id_asl, db );
						if( ac != null )
						{
							ac.setMotivo_chiusura((String)parts.get("motivo_chiusura"));
							ac.setData_chiusura( new Timestamp( System.currentTimeMillis() ) );
							ac.setChiusa_da( getUserId(context) );
							ac.update( db );
							
							AllerteHistory historyAllerta = new AllerteHistory();
							historyAllerta.setId_allerta(newTic.getId());
							historyAllerta.setData_operazione(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
							if(((String)parts.get("motivo_chiusura"))!=null)
							{
								historyAllerta.setTipo_operazione("CHIUSURA ASL-F");
								historyAllerta.setMotivo((String)parts.get("motivo_chiusura"));

							}
							else
							{
								historyAllerta.setTipo_operazione("CHIUSURA ASL");
								historyAllerta.setMotivo("");

							}
							historyAllerta.setUser_id(newTic.getModifiedBy());
							historyAllerta.setId(db);
							historyAllerta.store(db);
							
							AllerteAslHistory historyAslAllerta = new AllerteAslHistory();
							historyAslAllerta.setAsl(id_asl);
							historyAslAllerta.setCu_pianificati_regione(ac.getControlliUfficialiRegionaliPianificati());
							historyAslAllerta.setData(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
							historyAslAllerta.setId_allerte_history(historyAllerta.getId());
							if(((String)parts.get("motivo_chiusura"))!=null)
							{
								historyAslAllerta.setTipo_operazione("CHIUSURA ASL-F");
							}
							else
							{
								historyAslAllerta.setTipo_operazione("CHIUSURA ASL");
						
							}
							historyAslAllerta.setStato_allegatof("Inviato in data : "+new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date(historyAslAllerta.getData().getTime()))) ;
							historyAslAllerta.setStato(id_asl,newTic.getId(),db);
							historyAslAllerta.store(db);
						}
					}
					else
					{
						Ticket t = new Ticket( db, Integer.parseInt( id ) );
						Timestamp time = new Timestamp(System.currentTimeMillis());
						t.setDataChiusura( time );
						t.setModifiedBy( getUserId(context) );
						t.setModified( time );
						t.chiudi(db);
						AllerteHistory historyAllerta = new AllerteHistory();
						historyAllerta.setId_allerta(newTic.getId());
						historyAllerta.setData_operazione(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
						if(t.isChiusuraUfficio()==true)
						{
							historyAllerta.setTipo_operazione("CHIUSURA REGIONE-F");
							historyAllerta.setMotivo((String)parts.get("motivo_chiusura"));
						}
						else
						{
							historyAllerta.setTipo_operazione("CHIUSURA REGIONE");
							historyAllerta.setMotivo("");

						}
						historyAllerta.setUser_id(newTic.getModifiedBy());
						historyAllerta.setId(db);
						historyAllerta.store(db);
						if(chiusuraUfficio!=null){
							AslCoinvolte.chiusura_ufficio(id, getUserId(context), db,historyAllerta.getId(),newTic);
							t.setChiusuraUfficio(true);	
							t.update(db);
						}
						
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				finally
				{
					this.freeConnection(context, db);
				}
				
				// INVIO PEC CON FILE ALLEGATI PER LA CHIUSURA DELL'ALLERTA
				SystemStatus systemStatus = this.getSystemStatus(context);
				LookupList SiteIdList = new LookupList(db, "lookup_site_id");
				
				SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("SiteIdList", SiteIdList);
				context.getSession().setAttribute("problemaInvioMail", "no");
				 UserBean user = (UserBean)context.getSession().getAttribute("User");
				String tipoAlimenti = (String)parts.get("tipoAlimenti");
				String specie = (String)parts.get("specie_alimenti");
				newTic.setNoteAlimenti(""+specie+": "+tipoAlimenti);
				if( id_asl > 0 )
				{
					boolean flag_sian = false ;
					if (newTic.getIdAllerta().contains("S"))
					{
						flag_sian = true ;
					}
					
					
					AslCoinvolte ac = AslCoinvolte.load(Integer.parseInt(id) , id_asl, db );
					ac.setStato_allegatof(true);
					ac.setData_invio_allegato(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
					ac.update(db);
//					try
//					{
//					GestionePEC.preparaSpedisciPEC_allerta(db,SiteIdList.getValueFromId(user.getSiteId()) ,""+user.getSiteId(), newTic,context,ac.getMotivo_chiusura(),flag_sian);
//					}catch(EmailException em)
//					{
//					context.getSession().setAttribute("problemaInvioMail", "si");
//					}
						
					
					
				}
				
				try{
				

//				LookupList alimentinontrasformati = new LookupList(db,
//						"lookup_alimenti_origine_animale_non_trasformati");
//				alimentinontrasformati.addItem(-1,"-- SELEZIONA VOCE --");
//				context.getRequest().setAttribute("AlimentiNonTrasformati",
//						alimentinontrasformati);
//				
//				LookupList molluschi = new LookupList(db,
//				"lookup_molluschi");
//				molluschi.addItem(-1,
//				"-- SELEZIONA VOCE --");
//				context.getRequest().setAttribute("lookupmolluschi",
//						molluschi);
//
//				LookupList altrialimenti = new LookupList(db, "lookup_altrialimenti_nonanimale");
//				altrialimenti.addItem(-1, systemStatus
//						.getLabel("calendar.none.4dashes"));
//				context.getRequest().setAttribute("AltriAlimenti", altrialimenti);

//				// aggiunto da d.dauria
//				LookupList alimentinontrasformativalori = new LookupList(db,
//				"lookup_alimenti_origine_animale_non_trasformati_valori");
//				alimentinontrasformativalori.addItem(-1, "-- SELEZIONA VOCE --");
//				context.getRequest().setAttribute("AlimentiNonTrasformatiValori",
//						alimentinontrasformativalori);
//
//				// aggiunto da d.dauria
//				LookupList alimentitrasformati = new LookupList(db,
//				"lookup_alimenti_origine_animale_trasformati");
//				alimentitrasformati.addItem(-1, "-- SELEZIONA VOCE --");
//				context.getRequest().setAttribute("AlimentiTrasformati",
//						alimentitrasformati);
//
//				// aggiunto da d.dauria
//				LookupList alimentivegetali = new LookupList(db,
//				"lookup_alimenti_origine_vegetale_macrocategorie");
//				alimentivegetali.addItem(-1, "-- SELEZIONA VOCE --");
//				context.getRequest().setAttribute("AlimentiVegetali",
//						alimentivegetali);

				LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
				TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("TipoCampione", TipoCampione);

				

				// Parameters
				
				
				newTic.queryRecord(db, newTic.getId());

				// Reset the pagedLists since this could be a new visit to this ticket
				deletePagedListInfo(context, "TicketDocumentListInfo");
				deletePagedListInfo(context, "SunListInfo");
				deletePagedListInfo(context, "TMListInfo");
				deletePagedListInfo(context, "CSSListInfo");
				deletePagedListInfo(context, "TicketsFolderInfo");
				deletePagedListInfo(context, "TicketTaskListInfo");
				deletePagedListInfo(context, "ticketPlanWorkListInfo");

				// Load the ticket
				
				//newTic.queryRecord(db, Integer.parseInt(ticketId));

				//find record permissions for portal users
				//      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
				//        return ("PermissionError");
				//      }

				Organization orgDetails = new Organization();//new Organization(db, newTic.getOrgId());
				// check wether or not the product id exists
			
				

				LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
				Provvedimenti.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

				LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
				SanzioniAmministrative.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);

		

				LookupList regioni = new LookupList(db, "lookup_regioni");
				regioni.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("Regioni", regioni);



				LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
				SanzioniPenali.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

				LookupList Sequestri = new LookupList(db, "lookup_sequestri");
				Sequestri.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("Sequestri", Sequestri);

				LookupList TipoAlimento = new LookupList(db, "lookup_tipo_alimento");
				TipoAlimento.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("TipoAlimento", TipoAlimento);

				LookupList Origine = new LookupList(db, "lookup_origine");
				Origine.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("Origine", Origine);

				LookupList AlimentoInteressato = new LookupList(db, "lookup_alimento_interessato");
				AlimentoInteressato.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("AlimentoInteressato", AlimentoInteressato);

				LookupList NonConformita = new LookupList(db, "lookup_non_conformita");
				NonConformita.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("NonConformita", NonConformita);

				LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
				ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);

			} catch (SQLException e) {
				context.getRequest().setAttribute("Error", e);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			context.getRequest().setAttribute("TicketDetails", newTic);
			addRecentItem(context, newTic);
			addModuleBean(context, "ViewTickets", "View Tickets");

			String retPage = "DetailsOK";
			String tipo_richiesta = newTic.getTipo_richiesta();
			tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

			return "DetailsOK";

		  }}
	  
	
	
	
	public String executeCommandGoChiudiAllerta(ActionContext context)
	{
		if (!hasPermission(context, "allerte-allerte-chiusura-view")) {
			return ("PermissionError");
		}

		
		String id_allerta	= context.getParameter( "id" );
		int id_asl			= getUser( context, getUserId(context) ).getSiteId();
		
		try
		{
			String tipoAlimenti = context.getRequest().getParameter("tipoAlimenti");
			String idAllerta = context.getRequest().getParameter("id");
			String numCu = context.getRequest().getParameter("numCu");
			String specie_alimenti = context.getRequest().getParameter("specie_alimenti");
			context.getRequest().setAttribute("tipoAlimenti", tipoAlimenti);
			context.getRequest().setAttribute("idAllerta", idAllerta);
			context.getRequest().setAttribute("numCu", numCu);
			context.getRequest().setAttribute("specie_alimenti", specie_alimenti);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		

		return "PopupChiusura";
	}
	
	public String executeCommandHistoryAllerta(ActionContext context)
	{
		if (!hasPermission(context, "allerte-allerte-history-view")) {
			return ("PermissionError");
		}

		Connection db		= null;
		String id_allerta	= context.getParameter( "tId" );
		context.getRequest().setAttribute("id_allerta", id_allerta);
		ArrayList<AllerteHistory> history_allerta = new ArrayList<AllerteHistory>();
		
		try
		{	db = this.getConnection(context);
			String sel = "select tipo_operazione , data_operazione,motivo,(c.namelast|| c.namefirst) as utente,id " +
					" from allerte_history history left join contact c on (history.user_id=c.user_id) " +
					" where id_allerta = ? order by data_operazione";
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1,Integer.parseInt(id_allerta));
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				String tipo_operazione = rs.getString(1);
				Timestamp data_operazione = rs.getTimestamp(2);
				String motivo = rs.getString(3);
				String utente = rs.getString(4);
				int id = rs.getInt(5);
				AllerteHistory history = new AllerteHistory();
				history.setData_operazione(data_operazione);
				history.setId(id);
				history.setMotivo(motivo);
				history.setTipo_operazione(tipo_operazione);
				history.setNominativo(utente);
				history.loadHistory(db);
				history_allerta.add(history);
			}
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SystemStatus systemStatus = this.getSystemStatus(context);
			SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			context.getRequest().setAttribute("HistoryAllerte", history_allerta);
			
		}catch(Exception e)
		{
			
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "HistoryAllertaOK";
	}
	
	public String executeCommandChiudiAllerta(ActionContext context)
	{
		if (!hasPermission(context, "allerte-allerte-chiusura-view")) {
			return ("PermissionError");
		}

		Connection db		= null;
		String id_allerta	= context.getParameter( "id" );
		int id_asl			= getUser( context, getUserId(context) ).getSiteId();
		
		String chiusuraUfficio = context.getRequest().getParameter("chiusuraUfficio");
	
		try
		{
			
			db = this.getConnection(context);
			Ticket t = new Ticket( db, Integer.parseInt( id_allerta ) );
			if( id_asl > 0 )
			{
				AslCoinvolte ac = AslCoinvolte.load(Integer.parseInt(id_allerta) , id_asl, db );
				if( ac != null )
				{
					ac.setMotivo_chiusura(context.getRequest().getParameter("motivo_chiusura"));
					ac.setData_chiusura( new Timestamp( System.currentTimeMillis() ) );
					ac.setChiusa_da( getUserId(context) );
					ac.update( db );
					AllerteHistory historyAllerta = new AllerteHistory();
					historyAllerta.setId_allerta(t.getId());
					historyAllerta.setData_operazione(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
					if(context.getRequest().getParameter("motivo_chiusura")!=null)
					{
						historyAllerta.setTipo_operazione("CHIUSURA ASL-F");
						historyAllerta.setMotivo(context.getRequest().getParameter("motivo_chiusura"));

					}
					else
					{
						historyAllerta.setTipo_operazione("CHIUSURA ASL");
						historyAllerta.setMotivo("");

					}
					historyAllerta.setUser_id(t.getModifiedBy());
					historyAllerta.setId(db);
					historyAllerta.store(db);
					
					AllerteAslHistory historyAslAllerta = new AllerteAslHistory();
					historyAslAllerta.setAsl(id_asl);
					historyAslAllerta.setCu_pianificati_regione(ac.getControlliUfficialiRegionaliPianificati());
					historyAslAllerta.setData(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
					historyAslAllerta.setStato_allegatof("Inviato in data : "+new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date(historyAslAllerta.getData().getTime()))) ;
					historyAslAllerta.setId_allerte_history(historyAllerta.getId());
					if(context.getRequest().getParameter("motivo_chiusura")!=null)
					{
						historyAslAllerta.setTipo_operazione("CHIUSURA ASL-F");
					}
					else
					{
						historyAslAllerta.setTipo_operazione("CHIUSURA ASL");
				
					}
					historyAslAllerta.setStato(id_asl,t.getId(),db);
					historyAslAllerta.store(db);
					
					
				}
			}
			else
			{
				
				Timestamp time = new Timestamp(System.currentTimeMillis());
				t.setDataChiusura( time );
				t.setModifiedBy( getUserId(context) );
				t.setModified( time );
				t.chiudi(db);
				AllerteHistory historyAllerta = new AllerteHistory();
				historyAllerta.setId_allerta(t.getId());
				historyAllerta.setData_operazione(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
				if(t.isChiusuraUfficio()==true)
				{
					historyAllerta.setTipo_operazione("CHIUSURA REGIONE-F");
					historyAllerta.setMotivo(context.getRequest().getParameter("motivo_chiusura"));
				}
				else
				{
					historyAllerta.setTipo_operazione("CHIUSURA REGIONE");
					historyAllerta.setMotivo("");

				}
				historyAllerta.setUser_id(t.getModifiedBy());
				historyAllerta.setId(db);
				historyAllerta.store(db);
				if(chiusuraUfficio!=null){
					AslCoinvolte.chiusura_ufficio(id_allerta, getUserId(context), db,historyAllerta.getId(),t);
					t.setChiusuraUfficio(true);	t.update(db);
					
				}
				
			}
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SystemStatus systemStatus = this.getSystemStatus(context);
			SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			context.getSession().setAttribute("problemaInvioMail", "no");
			
			//R.M: gestione chiudura definitiva
			String salvaData = context.getRequest().getParameter("saveData");
			if(salvaData != null && !salvaData.equals("")){
				this.executeCommandSaveData(context);
			}
			
			
			if (user.getSiteId()!=-1)
			{
				boolean flag_sian = false ;
				if (t.getIdAllerta().contains("S"))
				{
					flag_sian = true ;
				}
				AslCoinvolte ac = AslCoinvolte.load(Integer.parseInt(id_allerta) , id_asl, db );
				ac.setMotivo_chiusura(context.getRequest().getParameter("motivo_chiusura"));
				
				ac.setStato_allegatof(true);
				ac.setData_invio_allegato(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
				ac.update(db);
				try
				{
					GestionePEC.preparaSpedisciPEC_allerta(db,SiteIdList.getValueFromId(user.getSiteId()) ,""+user.getSiteId(), t,context,ac.getMotivo_chiusura(),flag_sian);

					
				}catch(EmailException em)
				{
					context.getSession().setAttribute("problemaInvioMail", "si");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					context.getSession().setAttribute("problemaInvioMail", "si");
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}


		return executeCommandDetails(context);
		
	}
	
	
	public String executeCommandDownloadAllegatoFRegione(ActionContext context) throws IOException, DocumentException
	{
	
		Connection db = null ;
		try
		{
			 
			db = this.getConnection(context);
			if (context.getRequest().getAttribute("View")!=null)
			{
				context.getRequest().setAttribute("View", ""+context.getRequest().getAttribute("View"));
			}
		
			
			String id_allerta	= context.getParameter( "id" );
			if (id_allerta==null)
			{
				id_allerta = ""+context.getRequest().getAttribute("id_allerta");
			}
			Ticket t = new Ticket( db, Integer.parseInt( id_allerta ) );
			LookupList asl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", asl);
			context.getRequest().setAttribute("id_allerta", id_allerta);
			context.getRequest().setAttribute("Allerta", t);
			context.getRequest().setAttribute("tipoAlimenti", context.getRequest().getParameter("tipoAlimenti"));
			context.getRequest().setAttribute("specie_alimenti", context.getRequest().getParameter("specie_alimenti"));
			
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}


		return ("DownloadAllegatoFRegioneOK");
		
		
	}
	
	public String executeCommandPupUpConfirmChiusura(ActionContext context)
	{
		String retPage = "PupUpConfirmChiusuraOK" ;
	
		context.getRequest().setAttribute("cueseguiti", context.getRequest().getParameter("cueseguiti"));
		context.getRequest().setAttribute("idAllerta", context.getRequest().getParameter("idAllerta"));
		context.getRequest().setAttribute("chiusuraUfficio", context.getRequest().getParameter("chiusuraUfficio"));
		context.getRequest().setAttribute("idAslUtente", context.getRequest().getParameter("idAslUtente"));
		context.getRequest().setAttribute("numero_cu_seguiti", context.getRequest().getParameter("numero_cu_seguiti"));
		context.getRequest().setAttribute("cu_pianificati", context.getRequest().getParameter("cu_pianificati"));
		context.getRequest().setAttribute("tipo_alimenti", context.getRequest().getParameter("tipo_alimenti"));
		context.getRequest().setAttribute("specie_alimenti", context.getRequest().getParameter("specie_alimenti"));
		context.getRequest().setAttribute("msg", context.getRequest().getParameter("msg"));
		return retPage ;
		
	}
	
	
	/*
	public String executeCommandDownloadAllegatoF(ActionContext context) throws IOException, DocumentException
	{
		HttpServletResponse res = context.getResponse();
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
			String id_allerta	= context.getParameter( "ticketid" );
			int id_asl	= -1 ;
			id_asl	= getUser( context, getUserId(context) ).getSiteId();
			if (id_asl == -1)
			{
			if (context.getRequest().getParameter("tipo_download") != null)
			{
				if (context.getRequest().getParameter("tipo_download").equals("2")) // allegato per asl
				{
					 id_asl = Integer.parseInt(context.getRequest().getParameter("asl_regione_corrente"));
				}
			}
			}
			
			context.getRequest().setAttribute("id_allerta", id_allerta);
			Ticket t = new Ticket( db, Integer.parseInt( id_allerta ) );
			
			
			String reportName = "AllegatoF";
			Random random = new Random();
			int num = random.nextInt();
			int id = Integer.parseInt(context.getRequest().getParameter("ticketid"));
			AllegatoF allegato = new AllegatoF();
			
			String tipo_file = context.getRequest().getParameter("tipo_file");
			
			
				if (tipo_file.equals("pdf"))
				{
					res.setHeader( "Content-Disposition","attachment; filename=\"" + reportName +"_"+num + ".pdf\";" ); 

				res.setContentType("application/pdf");
				String	dirFilAllegatoF		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "template_report"+File.separator+"AllegatoF.pdf";
				String tipo_alimenti = context.getRequest().getParameter("tipoAlimenti");
				String specie_alimenti = context.getRequest().getParameter("specie_alimenti");
				File f = new File (dirFilAllegatoF);
				//FileOutputStream fos = new FileOutputStream(f);
				allegato.setIdAslUtente(id_asl);
				allegato.generate(db,res.getOutputStream(),id_asl,t,specie_alimenti+": "+tipo_alimenti,context);
				res.getOutputStream().flush();
				}
				else
				{
					res.setHeader( "Content-Disposition","attachment; filename=\"" + reportName +"_"+num + ".doc\";" ); 

					res.setContentType("application/doc");
					String	dirFilAllegatoF		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "template_report"+File.separator+"AllegatoF.pdf";
					String tipo_alimenti = context.getRequest().getParameter("tipoAlimenti");
					String specie_alimenti = context.getRequest().getParameter("specie_alimenti");
					File f = new File (dirFilAllegatoF);
					allegato.setIdAslUtente(id_asl);

					String file =allegato.generate_doc(db,res.getOutputStream(),id_asl,t,specie_alimenti+": "+tipo_alimenti,context);
				
					AuditReport auditReport = new AuditReport();
					auditReport.id_asl = id_asl;
					res.getOutputStream().print(file);
					res.getOutputStream().flush();
				
				}
				
				
				
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		
		
		
		return ("-none-");

	}

	public String executeCommandStampaAllegatoF(ActionContext context) throws IOException, DocumentException
	{
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
			String id_allerta	= context.getRequest().getParameter( "ticketid" );
			Ticket t = new Ticket( db, Integer.parseInt( id_allerta ) );
			LookupList tipiAlimenti = new LookupList(db,"lookup_alimenti_origine_animale_non_trasformati");
			context.getRequest().setAttribute("tipiAlimenti", tipiAlimenti);
			LookupList tipiAlimentiTrasformati = new LookupList(db,"lookup_alimenti_origine_animale_trasformati");
			context.getRequest().setAttribute("tipiAlimentiTrasformati", tipiAlimentiTrasformati);
			
			int id_asl	= -1 ;
			id_asl	= getUser( context, getUserId(context) ).getSiteId();
			if (id_asl == -1)
			{
			if (context.getRequest().getParameter("tipo_download") != null)
			{
				if (context.getRequest().getParameter("tipo_download").equals("2")) // allegato per asl
				{
					 id_asl = Integer.parseInt(context.getRequest().getParameter("asl_regione_corrente"));
				}
			}
			}
			
			int id = Integer.parseInt(context.getRequest().getParameter("ticketid"));
			//String tipo_alimenti = context.getRequest().getParameter("tipoAlimenti");
			//String specie_alimenti = context.getRequest().getParameter("specie_alimenti");
			
			context.getRequest().setAttribute("id_allerta", id_allerta);
			context.getRequest().setAttribute("ticketid", id);
			//context.getRequest().setAttribute("tipoAlimenti", tipo_alimenti);
			//context.getRequest().setAttribute("specie_alimenti", specie_alimenti);
			context.getRequest().setAttribute("allerta", t);
			
			LookupList lookup_asl = new LookupList(db,"lookup_site_id");
			AslCoinvolte aslCoinvolta = t.getAsl_coinvolte().get(lookup_asl.getValueFromId(id_asl));
			context.getRequest().setAttribute("aslCoinvolta", aslCoinvolta);
			
			
			int numCu = getNumeroControlliAllerta(db, t, id_asl);
			context.getRequest().setAttribute("numCu", String.valueOf(numCu));
			
			AllegatoF f = new AllegatoF();
			
			HashMap<String,Integer> num_cu_esiti = f.getNumeroControlliAllertaEsito(db,t, id_asl);
			HashMap<String,Integer> num_cu_ritiro = f.getNumeroControlliAllertapProceduraRitiro(db,t, id_asl);
			HashMap<String,Integer> num_cu_richiamo = f.getNumeroControlliAllertapProceduraRichiamo(db,t, id_asl);
			HashMap<String,Azione> num_cu_azioni =  f.getNumeroControlliAllertaAzioniAdottate(db,t, id_asl);
			HashMap<String,Azione> num_cu_esito_partita =  f.getNumeroControlliAllertaEsitoPartita(db,t, id_asl);
			HashMap<String,Integer> num_cu_comunicazione_rischio = f.getNumeroControlliRicevutaComunicazione(db,t, id_asl);
			ArrayList<String> note_lista = f.getNoteComunicazioneRischio(db,t, id_asl);
			
			context.getRequest().setAttribute("num_cu_esiti", num_cu_esiti);
			context.getRequest().setAttribute("num_cu_ritiro", num_cu_ritiro);
			context.getRequest().setAttribute("num_cu_richiamo", num_cu_richiamo);
			context.getRequest().setAttribute("num_cu_azioni", num_cu_azioni);
			context.getRequest().setAttribute("num_cu_esito_partita", num_cu_esito_partita);
			context.getRequest().setAttribute("num_cu_comunicazione_rischio", num_cu_comunicazione_rischio);
			context.getRequest().setAttribute("note_lista", note_lista);
			
			if (context.getRequest().getParameter("tipo_download").equals("2"))
				context.getRequest().setAttribute("scelta", "asl");
			else if (context.getRequest().getParameter("tipo_download").equals("1")){
				context.getRequest().setAttribute("scelta", "globale");
				
				int num_cu_regionali = 0 ;
				int num_cu_pianificati = 0 ;
				Iterator<String> itKey = t.getAsl_coinvolte().keySet().iterator();
				while (itKey.hasNext())
				{
					AslCoinvolte asl = t.getAsl_coinvolte().get(itKey.next());
					num_cu_regionali += asl.getControlliUfficialiRegionaliPianificati();
					num_cu_pianificati += asl.getCu_pianificati();
					context.getRequest().setAttribute("num_cu_regionali", String.valueOf(num_cu_regionali));
					context.getRequest().setAttribute("num_cu_pianificati", String.valueOf(num_cu_pianificati));
				}
		
			}
					
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		
		
		
		return ("StampaAllegatoFOk");

	}
*/
	public int getNumeroControlliAllerta(Connection db , org.aspcfs.modules.allerte_new.base.Ticket t,int id_asl)
	{
		int numCu = 0;
		try
		{
			String sql = "select count (ticketid) " +
			"from ticket where tipologia = 3 and trashed_date is null " +
			"and codice_allerta = ?";
			
			if (id_asl != -1)
			{
				sql += " and site_id = ? ";
			}
			
			PreparedStatement pst = db.prepareStatement(sql);
			
			pst.setString(1, t.getIdAllerta());
			if (id_asl!=-1)
			{
				pst.setInt(2, id_asl)	;
			}
			
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				numCu = rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return numCu ;
	}
	
	
	
	
	public String executeCommandAddListaDistribuzione(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = new Ticket();
		int ticketId = Integer.parseInt(context.getRequest().getParameter("idAllerta"));
		context.getRequest().setAttribute("idAllerta", String.valueOf(ticketId));
		
		try {
			db = this.getConnection(context);

			newTic.queryRecord(db, ticketId);
			
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
			ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);
			
			Statement stmt=db.createStatement();
			ResultSet rstSiteid= stmt.executeQuery("select * from lookup_site_id where enabled=true and code not in (16) order by code ");
			HashMap<Integer, String> siteIdList=new HashMap<Integer, String>();
			while(rstSiteid.next()){

				Integer code=rstSiteid.getInt("code");
				String descr=rstSiteid.getString("description");
				siteIdList.put(code, descr);
			}
	context.getRequest().setAttribute("SiteIdListUtil", siteIdList);


			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);


			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));

		String retPage = "AddListaDistribuzioneOK";
		return ( retPage );
	}
	
	public String executeCommandInsertListaDistribuzione(ActionContext context){
		int idAllerta = Integer.parseInt(context.getRequest().getParameter("idAllerta"));
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			Ticket newTic = new Ticket(db, idAllerta);

		ListaDistribuzione ldd = new ListaDistribuzione();
		ldd.setId_allerta(idAllerta);
		ldd.setData_lista(DatabaseUtils.parseDateToTimestamp(context.getRequest().getParameter("data_lista")));
		ldd.setData_chiusura(DatabaseUtils.parseDateToTimestamp(context.getRequest().getParameter("data_chiusura")));
		ldd.setNome_fornitore(context.getRequest().getParameter("nome_fornitore"));
		ldd.setId_utente_inserimento(getUserId(context));
		ldd.store(db);
		
		String[] asl_coinvolte = context.getRequest().getParameterValues( "asl_coinvolte" );
		int cuPianificati=-1;
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		boolean flag_sian = false ; 
		if (user.getRoleId()==37)
		{
			flag_sian = true ;
		}
		
		for( String asl: asl_coinvolte )
		{
			String cuAsl=context.getRequest().getParameter("cu_"+asl);
			 try {
				 if(!cuAsl.equals("") ){
						cuPianificati=Integer.parseInt(cuAsl);
					}else{
						cuPianificati=-1;
					}
				}
				catch(NumberFormatException nFE) {
				    logger1.error("Not an Integer value for Num.CU");
				}
			
			AslCoinvolte ac = new AslCoinvolte();
			if(asl.equals("16")){
				ac.setNoteFuoriRegione(context.getRequest().getParameter("noteFuoriRegione"));
			}else{
				ac.setNoteFuoriRegione("");
			}
			
			ac.setId_ldd(ldd.getId());
			ac.setId_allerta( idAllerta);
			ac.setId_asl( Integer.parseInt( asl ) );
			ac.setCu_pianificati_da( getUserId(context) );
			ac.setControlliUfficialiRegionaliPianificati(cuPianificati);
			ac.setCu_pianificati(cuPianificati);
			ac.store( db );
			
			Hashtable<String, AslCoinvolte> aslcoinvolte = AslCoinvolte.getAslConvolteByIdLdd(ldd.getId(),newTic.getIdAllerta(),newTic.getId(),true, db);
			for (String kiave : aslcoinvolte.keySet())
			{
				AslCoinvolte temp = aslcoinvolte.get(kiave);
				
				ImpreseCoinvolte imprese_coinvolte = new ImpreseCoinvolte();
				
				String[] impreseCoinvolte = context.getRequest().getParameterValues("ragione_sociale"+temp.getId_asl());
				String[] indirizziImpreseCoinvolte = context.getRequest().getParameterValues("indirizzo_"+temp.getId_asl());
				imprese_coinvolte.setIdAslcoinvolta(temp.getId());
				imprese_coinvolte.setImpreseCoinvolte(ImpreseCoinvolte.convertiInLista(impreseCoinvolte));
				imprese_coinvolte.setIndirizziImpreseCoinvolte(ImpreseCoinvolte.convertiInLista(indirizziImpreseCoinvolte));
				imprese_coinvolte.setIdAsl(temp.getId_asl());
				imprese_coinvolte.store(db);
				
			}
		
		}
		
		context.getRequest().setAttribute("idAllerta", String.valueOf(idAllerta));
		context.getRequest().setAttribute("inserito", "OK");
		
		}
		catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		e.printStackTrace();
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
		String retPage = "InsertListaDistribuzioneOK";
		return ( retPage );
	}
	
	public String executeCommandModifyListaDistribuzione(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		int idLista = Integer.parseInt(context.getRequest().getParameter("idLdd"));
		Ticket newTic = new Ticket();
		
		try {
			db = this.getConnection(context);
			
			ListaDistribuzione lista = new ListaDistribuzione(db, idLista);
			int ticketId = lista.getId_allerta();
			newTic.queryRecord(db, ticketId);
			lista.aggiungiAslCoinvolte(db, "", lista.getId_allerta()) ;
			context.getRequest().setAttribute("ListaDistribuzione", lista);
			
						
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
			ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);
			
			Statement stmt=db.createStatement();
			ResultSet rstSiteid= stmt.executeQuery("select * from lookup_site_id where enabled=true and code not in (16) order by code ");
			HashMap<Integer, String> siteIdList=new HashMap<Integer, String>();
			while(rstSiteid.next()){

				Integer code=rstSiteid.getInt("code");
				String descr=rstSiteid.getString("description");
				siteIdList.put(code, descr);
			}
	context.getRequest().setAttribute("SiteIdListUtil", siteIdList);


			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);


			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));

		String retPage = "ModifyListaDistribuzioneOK";
		return ( retPage );
	}
	
	public String executeCommandUpdateListaDistribuzione(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-edit")) {
			return ("PermissionError");
		}
		int resultCount = 0;
		int catCount = 0;
		TicketCategory thisCat = null;
		boolean catInserted = false;
		boolean isValid = true;
		
		Ticket newTic = null;
		ListaDistribuzione lista = null;
		ListaDistribuzione listaOld = null;
		HttpServletRequest request = context.getRequest();
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
				db = this.getConnection(context);
			
				int idLdd = Integer.parseInt(context.getRequest().getParameter("idLdd"));
				lista = new ListaDistribuzione(db, idLdd);
				listaOld = new ListaDistribuzione(db, idLdd);
				listaOld.setId_utente_modifica(getUserId(context));
				
				lista.setId_utente_modifica(getUserId(context));
				lista.setNome_fornitore(context.getRequest().getParameter("nome_fornitore"));
				lista.setData_lista(DatabaseUtils.parseDateToTimestamp(context.getRequest().getParameter("data_lista")));
				lista.setData_chiusura(DatabaseUtils.parseDateToTimestamp(context.getRequest().getParameter("data_chiusura")));
				lista.update(listaOld, db);
				
				newTic = new Ticket(db, lista.getId_allerta());
				Timestamp time = new Timestamp(System.currentTimeMillis());
				newTic.setModifiedBy( getUserId(context) );
				newTic.setModified( time );
			
				AllerteHistory historyAllerta = new AllerteHistory();
				historyAllerta.setId_allerta(lista.getId_allerta());
				historyAllerta.setId_ldd(idLdd);
				historyAllerta.setData_operazione(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
				historyAllerta.setTipo_operazione("MODIFICA - R");
				
				historyAllerta.setUser_id(newTic.getModifiedBy());
				historyAllerta.setId(db);
				historyAllerta.setMotivo(context.getRequest().getParameter("motivo_ripianificazione_modifica"));
				historyAllerta.store(db);
				UserBean user = (UserBean) context.getSession().getAttribute("User");
				boolean flag_sian = false ; 
				if (newTic.getIdAllerta().contains("S"))
				{
					flag_sian = true ;
				}
				AslCoinvolte.updateAslCoinvolteAllertaByIdLdd( newTic.getId(), idLdd, request.getParameterValues("asl_coinvolte"), getUserId(context), db,request,newTic,historyAllerta.getId(),flag_sian);
				
				
				Hashtable<String, AslCoinvolte> aslcoinvolte = AslCoinvolte.getAslConvolteByIdLdd(idLdd, newTic.getIdAllerta(), newTic.getId(),true, db);
				for (String kiave : aslcoinvolte.keySet())
				{
					AslCoinvolte temp = aslcoinvolte.get(kiave);
					
					ImpreseCoinvolte imprese_coinvolte = new ImpreseCoinvolte();
					
					String[] impreseCoinvolte = request.getParameterValues("pippo_"+temp.getId_asl());
					String[] indirizziImpreseCoinvolte = request.getParameterValues("indirizzo_"+temp.getId_asl());
					
					imprese_coinvolte.setIdAslcoinvolta(temp.getId());
					imprese_coinvolte.delete(db);
					imprese_coinvolte.setImpreseCoinvolte(ImpreseCoinvolte.convertiInLista(impreseCoinvolte));
					imprese_coinvolte.setIndirizziImpreseCoinvolte(ImpreseCoinvolte.convertiInLista(indirizziImpreseCoinvolte));
					imprese_coinvolte.setIdAsl(temp.getId_asl());
					imprese_coinvolte.store(db);
					
				}
				
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			context.getRequest().setAttribute("problemaInvioMail", "si");
			
		} finally {
			this.freeConnection(context, db);
		}
		
			context.getRequest().setAttribute("id", newTic.getId());
			context.getRequest().setAttribute("idticket", newTic.getId());
			return executeCommandDetails(context);
		
	}
	
	
	public String executeCommandPrepareRipianifica(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		String ticketId = null;

		try {
			db = this.getConnection(context);

			LookupList UnitaMisura = new LookupList(db,"lookup_unita_misura_allerta");
			UnitaMisura.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("UnitaMisura",UnitaMisura);
		
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

		
			// Parameters
			ticketId = context.getRequest().getParameter("id");
			if(ticketId==null)
				ticketId=""+(Integer)context.getRequest().getAttribute("idticket");

			// Load the ticket
			newTic = new Ticket();
			newTic.queryRecord(db, Integer.parseInt(ticketId));
		
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList regioni = new LookupList(db, "lookup_regioni");
			regioni.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Regioni", regioni);

			LookupList Origine = new LookupList(db, "lookup_origine");
			Origine.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Origine", Origine);

			LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
			ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);

			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("TicketDetails", newTic);
	

		String retPage = "PrepareRipianificaOK";
		return ( retPage );
	}
	
	
	public String executeCommandPrepareChiusuraLista(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			// Parameters
			int idLista  = Integer.parseInt(context.getRequest().getParameter("idLista"));
			
			ListaDistribuzione ldd = new ListaDistribuzione(db, idLista);
			Ticket newTic = new Ticket (db, ldd.getId_allerta());
			
			Hashtable<String, AslCoinvolte> aslcoinvolte = AslCoinvolte.getAslConvolteByIdLdd(ldd.getId(),newTic.getIdAllerta() ,ldd.getId_allerta(),true, db);
			ldd.setAsl_coinvolte(aslcoinvolte);
			
			boolean forzata = true;
			
			if (ldd.getNumCuEseguiti()==ldd.getNumCuPianificati())
				forzata = false;
			
			context.getRequest().setAttribute("ListaDistribuzione", ldd);
			context.getRequest().setAttribute("forzata", String.valueOf(forzata));
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		String retPage = "PrepareChiusuraListaOK";
		return ( retPage );
	}
	
	public String executeCommandChiusuraLista(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			// Parameters
			int idLista  = Integer.parseInt(context.getRequest().getParameter("idLista"));
			boolean forzata = false;
			
			if (context.getRequest().getParameter("chiusuraForzata")!=null)
				forzata = true;
			
			String data = context.getRequest().getParameter("data_chiusura");
			
			ListaDistribuzione ldd = new ListaDistribuzione(db, idLista);
			ldd.setChiusura_forzata(forzata);
			
			ldd.chiudi(db, data);
			
			for (int i = 201; i<=207; i++){
				
				ldd.setId_utente_modifica(this.getUserId(context));
				ldd.chiudiPerAsl(db, data, i);
			}
			
			
			
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		String retPage = "ChiusuraOK";
		return ( retPage );
	}
	
	public String executeCommandChiusuraListaAsl(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-chiudi-lista-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		ListaDistribuzione ldd = null;
		
		int idLista = -1;
		int idAllerta = -1;
		int idAsl = -1;
		String data = null;
		
		try {
			db = this.getConnection(context);
			// Parameters
			idLista  = Integer.parseInt(context.getRequest().getParameter("idLista"));
			idAllerta =  Integer.parseInt(context.getRequest().getParameter("idAllerta"));
			idAsl =  Integer.parseInt(context.getRequest().getParameter("idAsl"));
			data = context.getRequest().getParameter("dataChiusuraLista");
			
			if (idLista > 0){
				ldd = new ListaDistribuzione(db, idLista);
				ldd.setId_utente_modifica(this.getUserId(context));
				ldd.chiudiPerAsl(db, data, idAsl);
			}
			else
			{
				AslCoinvolte asl = new AslCoinvolte();
				asl = AslCoinvolte.load(idAllerta, idAsl, db);
				asl.chiudiPerAsl(db, data, idAsl, this.getUserId(context));
			}
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		context.getRequest().setAttribute("id", idAllerta);
		context.getRequest().setAttribute("idticket",  idAllerta);
		return executeCommandDetails(context);
	}
	
	
	public String executeCommandPrepareChiusuraAllerta(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			// Parameters
			
			boolean forzata = false;
			int idAllerta  = Integer.parseInt(context.getRequest().getParameter("idAllerta"));
			
			Ticket newTic = new Ticket (db, idAllerta);
			Hashtable<Integer, ListaDistribuzione> liste_distribuzione = null;
			liste_distribuzione = ListaDistribuzione.getListeDistribuzione(  newTic.getId(), newTic.getIdAllerta(), db );
			for (int i =1; i<=liste_distribuzione.size();i++){
				ListaDistribuzione lista = (ListaDistribuzione) liste_distribuzione.get(i);
				if (lista.getData_chiusura()==null){
					forzata = true;
					break;
				}
			}
			context.getRequest().setAttribute("idAllerta", String.valueOf(idAllerta));
			context.getRequest().setAttribute("forzata", String.valueOf(forzata));
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		String retPage = "PrepareChiusuraAllertaOK";
		return ( retPage );
	}
	
	public String executeCommandChiusuraAllerta(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			// Parameters
			int idAllerta  = Integer.parseInt(context.getRequest().getParameter("idAllerta"));
			boolean forzata = false;
			
			if (context.getRequest().getParameter("chiusuraForzata")!=null)
				forzata = true;
			
			String data = context.getRequest().getParameter("data_trasmissione");
			String note = context.getRequest().getParameter("note");
			
			
			Ticket newTic = new Ticket (db, idAllerta);
			Hashtable<Integer, ListaDistribuzione> liste_distribuzione = null;
			liste_distribuzione = ListaDistribuzione.getListeDistribuzione(  newTic.getId(), newTic.getIdAllerta(), db );
			for (int i =1; i<=liste_distribuzione.size();i++){
				ListaDistribuzione lista = (ListaDistribuzione) liste_distribuzione.get(i);
				if (lista.getData_chiusura()==null){
					lista.setChiusura_forzata(forzata);
					lista.chiudi(db, data);
				}
			}
			
			newTic.setChiusuraUfficio(forzata);
			newTic.setNote_motivazione(note);
			newTic.setModifiedBy( this.getUserId(context));
			newTic.setResolvedBy( this.getUserId(context));
			newTic.chiudi(db, data);
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		String retPage = "ChiusuraOK";
		return ( retPage );
	}
	
	public String executeCommandPrepareAllegatoF(ActionContext context){
	
		String idAllerta = context.getRequest().getParameter("idAllerta");
		context.getRequest().setAttribute("idAllerta", idAllerta);
	
		Connection db = null;
		try {
			db = this.getConnection(context);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			User user = this.getUser(context, this.getUserId(context));
			
			if ( user.getSiteId()>0){
				SiteIdList.removeItemfromLookup(db, "lookup_site_id", " code <> "+user.getSiteId());
			}
			context.getRequest().setAttribute("SiteIdListF", SiteIdList);
			
		
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		String retPage = "prepareAllegatoFOK";
		return ( retPage );
	}
	
	
	public void prepareAllegatoF(Connection db, ActionContext context, Ticket allerta) throws SQLException{
		
		context.getRequest().setAttribute("idAllerta", String.valueOf(allerta.getId()));
		LookupList SiteIdList = new LookupList(db, "lookup_site_id");
		SiteIdList.removeElementByCode(16);
		
		User user = this.getUser(context, this.getUserId(context));
			
			if ( user.getSiteId()>0){
				
				LookupList soloUnAsl = new LookupList(db, "(select * from lookup_site_id where code = "+user.getSiteId()+")");
				SiteIdList = soloUnAsl;
			}
			context.getRequest().setAttribute("SiteIdListF", SiteIdList);
			
			HashMap<Integer, String> note = new HashMap<Integer, String>();
			for (int i = 201; i<=207; i++){
				AllegatoFNote allNotes = new AllegatoFNote(db, allerta.getId(),i);
				String notes = allNotes.getNote();
				note.put(i, notes);
			}
			context.getRequest().setAttribute("NoteAllegatoF", note);
			
			HashMap<Integer, Boolean> AllegatoFGenerabile = new HashMap<Integer, Boolean>();
			AllegatoFGenerabile = ListaDistribuzione.getAllegatoFGenerabile(db, allerta);
			context.getRequest().setAttribute("AllegatoFGenerabile", AllegatoFGenerabile);

			

			}
	
	
	public String executeCommandGeneraAllegatoF(ActionContext context){
		
		String idAllertaString = context.getRequest().getParameter("idAllerta");
		String idAslString = context.getRequest().getParameter("idAsl");
		String tipoAllegato = context.getRequest().getParameter("tipoAllegato");
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			
			int idAllerta = Integer.parseInt(idAllertaString);
			int idAsl = Integer.parseInt(idAslString);
			
			if (tipoAllegato.equals("Regione"))
				idAsl = -1;
			
			AllegatoF allegato = new AllegatoF ();
			allegato.setAsl(SiteIdList.getSelectedValue(idAsl));
			allegato.buildAllegatoF(db, idAllerta, idAsl);
			context.getRequest().setAttribute("AllegatoFDetails", allegato);
		
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		String retPage = "allegatoF"+tipoAllegato+"OK";
		return ( retPage );
	}
	
public String executeCommandGeneraAllegatoFModificabile(ActionContext context){
		
		String idAllertaString = context.getRequest().getParameter("idAllerta");
		String idAslString = context.getRequest().getParameter("idAsl");
		String tipoAllegato = context.getRequest().getParameter("tipoAllegato");
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			
			int idAllerta = Integer.parseInt(idAllertaString);
			int idAsl = Integer.parseInt(idAslString);
			
			if (tipoAllegato.equals("Regione"))
				idAsl = -1;
			
			AllegatoF allegato = new AllegatoF ();
			allegato.setAsl(SiteIdList.getSelectedValue(idAsl));
			allegato.buildAllegatoF(db, idAllerta, idAsl);
			context.getRequest().setAttribute("AllegatoFDetails", allegato);
		
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		String retPage = "allegatoFModificabile"+tipoAllegato+"OK";
		return ( retPage );
	}
	
	public String executeCommandUpdateNote(ActionContext context) {
		if (!hasPermission(context, "allerte-allegatof-asl-edit")) {
			return ("PermissionError");
		}
		int resultCount = 0;
		int catCount = 0;
		TicketCategory thisCat = null;
		boolean catInserted = false;
		boolean isValid = true;
		
		Ticket newTic = null;
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
				db = this.getConnection(context);
				

				AllegatoFNote notes = new AllegatoFNote();
				int idAllerta = Integer.parseInt(context.getRequest().getParameter("idAllerta"));
				int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
				
				newTic = new Ticket(db, idAllerta);
				
				String note = context.getRequest().getParameter("note");
				Timestamp time = new Timestamp(System.currentTimeMillis());
				notes.setIdUtenteInserimento( getUserId(context) );
				notes.setDataCancellazione( time );
				notes.setNote(note);
				notes.setIdAsl(idAsl);
				notes.setIdAllerta(idAllerta);
				notes.store(db);
				
				
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			context.getRequest().setAttribute("problemaInvioMail", "si");
			
		} finally {
			this.freeConnection(context, db);
		}
		
			context.getRequest().setAttribute("id", newTic.getId());
			context.getRequest().setAttribute("idticket", newTic.getId());
			return executeCommandDetails(context);
		
	}
	
	public String executeCommandInsertNumeroNotifica(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-numeronotifica-add")) {
			return ("PermissionError");
		}
		int resultCount = 0;
		int catCount = 0;
		TicketCategory thisCat = null;
		boolean catInserted = false;
		boolean isValid = true;
		
		Ticket newTic = null;
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
				db = this.getConnection(context);
				
				int idAllerta = Integer.parseInt(context.getRequest().getParameter("idAllerta"));
				newTic = new Ticket(db, idAllerta);
				
				String numeroNotifica = context.getRequest().getParameter("numeroNotifica");
				newTic.setNumero_notifica_allerta(numeroNotifica);
				Timestamp time = new Timestamp(System.currentTimeMillis());
				newTic.setModifiedBy( getUserId(context) );
				newTic.setModified( time );
				int result = newTic.updateNumeroNotifica(db);
				if (result==-1){
					context.getRequest().setAttribute("Errore", "Numero di notifica gia' esistente nel sistema.");
				}
				
				
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			context.getRequest().setAttribute("problemaInvioMail", "si");
			
		} finally {
			this.freeConnection(context, db);
		}
		
			context.getRequest().setAttribute("id", newTic.getId());
			context.getRequest().setAttribute("idticket", newTic.getId());
			return executeCommandDetails(context);
		
	}
	
	public String executeCommandRipianificaCUAsl(ActionContext context) {
		if (!hasPermission(context, "allerte-allerte-ripianifica-lista-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		ListaDistribuzione ldd = null;
		
		int idLista = -1;
		int idAllerta = -1;
		int idAsl = -1;
		int newPianificati = -1;
		String motivazione = null;
		int uscita = -1; 
		
		try {
			db = this.getConnection(context);
			// Parameters
			idLista  = Integer.parseInt(context.getRequest().getParameter("idLista"));
			idAllerta =  Integer.parseInt(context.getRequest().getParameter("idAllerta"));
			idAsl =  Integer.parseInt(context.getRequest().getParameter("idAsl"));
			newPianificati = Integer.parseInt(context.getRequest().getParameter("newPianificati"));
			motivazione = context.getRequest().getParameter("motivazione");
			
			Ticket newTic = new Ticket (db, idAllerta);
		
			if (idLista > 0){
				ldd = new ListaDistribuzione(db, idLista);
				Hashtable<String, AslCoinvolte> aslcoinvolte = AslCoinvolte.getAslConvolteByIdLdd(ldd.getId(),newTic.getIdAllerta() ,ldd.getId_allerta(),true, db);
				ldd.setAsl_coinvolte(aslcoinvolte);
				ldd.setId_utente_modifica(this.getUserId(context));
				uscita = ldd.ripianificaPerAsl(db, newPianificati, idAsl, motivazione);
			}
			else
			{
				AslCoinvolte asl = new AslCoinvolte();
				asl = AslCoinvolte.load(idAllerta, idAsl, db);
				uscita = asl.ripianificaPerAsl(db, newPianificati, idAsl, motivazione, this.getUserId(context));
			}
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		context.getRequest().setAttribute("id", idAllerta);
		context.getRequest().setAttribute("idticket",  idAllerta);
		
		if (uscita == 7){
			context.getRequest().setAttribute("Errore", "Impossibile effettuare questa modifica poich� il numero di CU ripianificati � inferiore al numero di CU (aperti+chiusi) gia' eseguiti.");
		}
		
		return executeCommandDetails(context);
	}
}