/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiestecontributi.actions;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.ContributiAnimaleList;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.darkhorseventures.framework.actions.ActionContext;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.nuovi_report.base.Stats;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.modules.praticacontributi.base.PraticaDWR;
import org.aspcfs.modules.richiestecontributi.base.GestionePEC;
import org.aspcfs.modules.richiestecontributi.base.ListaRichieste;
import org.aspcfs.modules.richiestecontributi.base.Pagamenti;
import org.aspcfs.modules.richiestecontributi.base.RichiestaContributi;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import java.util.HashMap;
public class ContributiSterilizzazioni extends CFSModule {

	public String executeCommandDefault(ActionContext context) {
		return executeCommandView(context);
	}

	public String executeCommandView(ActionContext context) {
		if (!hasPermission(context, "richiesta-contributi-view")
				&& !hasPermission(context, "approvazione-contributi-view")) {
			return ("PermissionError");
		}

		return ("init");
	}

	public String executeCommandAddRichiestaCatturati(ActionContext context) {
		Connection db = null;
		if (!hasPermission(context, "richiesta-contributi-view")) {
			return ("PermissionError");
		}
		try {
			db = getConnection(context);
			buildFormElements(context, db);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslRifList", siteList);
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			System.gc();
		}
		return "AddRichiestaCatturati";
	}

	public String executeCommandAddRichiesta(ActionContext context) {
		Connection db = null;
		if (!hasPermission(context, "richiesta-contributi-generale-view")) {
			return ("PermissionError");
		}
		try {
			db = getConnection(context);
			if(this.getUserRole(context) == new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE"))) 
			{
				buildPraticheLP(context, db);
				
				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "--");
				context.getRequest().setAttribute("comuniList", comuniList);
			}
			else
			{
				buildPratiche(context, db);
			}
			
			

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslRifList", siteList);
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			System.gc();
		}
		return "AddRichiestaOk";
	}

	public String executeCommandAddRichiestaPadronali(ActionContext context) {
		Connection db = null;
		if (!hasPermission(context, "richiesta-contributi-view")) {
			return ("PermissionError");
		}
		try {
			db = getConnection(context);
			buildFormElements(context, db);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslRifList", siteList);
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			System.gc();
		}
		return "AddRichiestaPadronali";
	}

	private static String oggi() {
		return (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
	}

	private static int calcolaNumeroPagine(int size) {
		return (int) Math.ceil((size * 1.0) / (page_elem));
	}

	private static final int page_elem = 220;

	public void buildFormElements(ActionContext context, Connection db)
			throws SQLException {
		SystemStatus thisSystem = this.getSystemStatus(context);

		LookupList aslList = new LookupList(db, "lookup_asl_rif");
		aslList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
		context.getRequest().setAttribute("aslRifList", aslList);
		ArrayList<String> comuneList = new ArrayList<String>();
		Hashtable<String, ArrayList<String>> hashtable = new Hashtable<String, ArrayList<String>>();
		String[] x = new String[7];

		UserBean thisUser = (UserBean) context.getSession()
				.getAttribute("User");
		x[0] = "Napoli 1 Centro";
		x[1] = "Napoli 2 Nord";
		x[2] = "Napoli 3 Sud";
		x[3] = "Avellino";
		x[4] = "Caserta";
		x[5] = "Benevento";
		x[6] = "Salerno";
		String qry2 = "select comune from comuni join lookup_asl_rif on (codiceistatasl=codiceistat) where code=?";
		if (thisUser.getSiteId() == 0) {
			for (int i = 201; i <= 207; i++) {
				ArrayList<String> tmp = new ArrayList<String>();
				PreparedStatement pst2 = db.prepareStatement(qry2);
				pst2.setInt(1, i);
				ResultSet rs2 = pst2.executeQuery();

				while (rs2.next()) {
					tmp.add("'" + rs2.getString("comune").replaceAll("'", "")
							+ "'");
				}
				String tempo = "[";
				for (String cc : tmp) {
					tempo = tempo + cc + ",";

				}
				tempo = tempo.substring(0, tempo.length() - 1) + "]";
				hashtable.put("" + i, tmp);
			}
		} else {
			if (thisUser.getSiteId() > 0) {

				ArrayList<String> tmp2 = new ArrayList<String>();
				PreparedStatement pst2 = db.prepareStatement(qry2);
				pst2.setInt(1, thisUser.getSiteId());
				ResultSet rs2 = pst2.executeQuery();
				int i = 0;
				while (rs2.next()) {
					tmp2.add("'" + rs2.getString("comune").replaceAll("'", "")
							+ "'");
					i++;

				}
				context.getRequest().setAttribute("lista", tmp2);
			}

		}
		context.getRequest().setAttribute("hashtable", hashtable);
	}

	public void buildPratiche(ActionContext context, Connection db)
			throws SQLException {
		// recupero utente corrente
		UserBean thisUser = (UserBean) context.getSession()
				.getAttribute("User");
		ArrayList<PraticaDWR> elencoPratiche = new ArrayList<PraticaDWR>();

		// i progetti esclusi sono quelli per il recupero del pregresso e non
		// devono essere inviati alla regione, verranno inseriti tramite help
		// desk
		// String query=
		// "Select * FROM pratiche_contributi where asl= ? and id not in (1,2,3,4,5,6,7,8,9,10) ";
		String query = "Select * FROM pratiche_contributi where asl= ? and data_chiusura_pratica is null "; // per
		// l'applicazione
		// su
		// demo
		PreparedStatement st = db.prepareStatement(query);
		st.setInt(1, thisUser.getSiteId());

		PraticaDWR pratica_tmp = null;

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			pratica_tmp = new PraticaDWR();
			pratica_tmp.setTotale_cani_catturati(rs
					.getInt("numero_totale_cani_catturati"));
			pratica_tmp.setTotale_cani_padronali(rs
					.getInt("numero_totale_cani_padronali"));
			pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
			pratica_tmp.setId(rs.getInt("id"));
			int i = pratica_tmp.getId();
			pratica_tmp.setCani_restanti_catturati(rs
					.getInt("numero_restante_cani_catturati"));
			pratica_tmp.setCani_restanti_padronali(rs
					.getInt("numero_restante_cani_padronali"));
			pratica_tmp.setData_fine_sterilizzazione(rs
					.getTimestamp("data_fine_sterilizzazione"));
			pratica_tmp.setTotale_gatti_catturati(rs
					.getInt("numero_totale_gatti_catturati"));
			pratica_tmp.setTotale_gatti_padronali(rs
					.getInt("numero_totale_gatti_padronali"));
			pratica_tmp.setGatti_restanti_catturati(rs
					.getInt("numero_restante_gatti_catturati"));
			pratica_tmp.setGatti_restanti_padronali(rs
					.getInt("numero_restante_gatti_padronali"));

			pratica_tmp.setData_inizio_sterilizzazione(rs
					.getTimestamp("data_inizio_sterilizzazione"));
			pratica_tmp.setData_decreto(rs.getTimestamp("data_decreto"));
			pratica_tmp.setNumero_decreto_pratica(rs.getInt("numero_decreto"));
			elencoPratiche.add(pratica_tmp);

			//String query2 = "Select comune from pratiche_contributi_comuni where id_pratica=? ";
			
			String query2 = "Select comuni1.nome from pratiche_contributi_comuni left join comuni1 on (comune = comuni1.id) where id_pratica= ?";

			PreparedStatement st2 = db.prepareStatement(query2);
			st2.setInt(1, i);

			ResultSet rs2 = st2.executeQuery();
			ArrayList<String> comuni = new ArrayList<String>();
			while (rs2.next()) {
				comuni.add(rs2.getString("nome"));
				pratica_tmp.setElenco_comuni(comuni);
			}

			String query3 = "Select canile from pratiche_contributi_canili where id_pratica= ?";
			PreparedStatement st3 = db.prepareStatement(query3);
			st3.setInt(1, i);

			ResultSet rs3 = st3.executeQuery();
			ArrayList<String> canili = new ArrayList<String>();

			// Aggiunta
			while (rs3.next()) {
				canili.add(rs3.getString("canile"));
				pratica_tmp.setElenco_canili(canili);
			}

		}

		context.getRequest().setAttribute("elencoPratiche", elencoPratiche);

	}
	
	
	
	public void buildPraticheLP(ActionContext context, Connection db) throws SQLException 
	{
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		ArrayList<PraticaDWR> elencoPratiche = new ArrayList<PraticaDWR>();

		String query = "Select * FROM pratiche_contributi p join pratiche_contributi_comuni c on c.id_pratica = p.id and c.comune = ? where p.id_tipologia_pratica = 3 and  p.data_chiusura_pratica is null "; 
		PreparedStatement st = db.prepareStatement(query);
		st.setInt(1, thisUser.getUserRecord().getIdComune());

		PraticaDWR pratica_tmp = null;

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			pratica_tmp = new PraticaDWR();
			pratica_tmp.setTotale_cani_maschi(rs.getInt("numero_totale_cani_maschi"));
			pratica_tmp.setTotale_cani_femmina(rs.getInt("numero_totale_cani_femmina"));
			pratica_tmp.setId_asl_pratica(rs.getInt("asl"));
			pratica_tmp.setId(rs.getInt("id"));
			int i = pratica_tmp.getId();
			pratica_tmp.setCani_restanti_maschi(rs.getInt("numero_restante_cani_maschi"));
			pratica_tmp.setCani_restanti_femmina(rs.getInt("numero_restante_cani_femmina"));
			pratica_tmp.setData_fine_sterilizzazione(rs.getTimestamp("data_fine_sterilizzazione"));

			pratica_tmp.setData_inizio_sterilizzazione(rs.getTimestamp("data_inizio_sterilizzazione"));
			pratica_tmp.setData_decreto(rs.getTimestamp("data_decreto"));
			pratica_tmp.setNumero_decreto_pratica(rs.getInt("numero_decreto"));
			elencoPratiche.add(pratica_tmp);

			String query2 = "Select comuni1.nome from pratiche_contributi_comuni left join comuni1 on (comune = comuni1.id) where id_pratica= ?";

			PreparedStatement st2 = db.prepareStatement(query2);
			st2.setInt(1, i);

			ResultSet rs2 = st2.executeQuery();
			ArrayList<String> comuni = new ArrayList<String>();
			while (rs2.next()) {
				comuni.add(rs2.getString("nome"));
				pratica_tmp.setElenco_comuni(comuni);
			}

		}

		context.getRequest().setAttribute("elencoPratiche", elencoPratiche);

	}

	public String executeCommandAvviaRichiesta(ActionContext context) {

		// controllo dei permessi
		if (!hasPermission(context, "richiesta-contributi-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		RichiestaContributi rc;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {

			db = this.getConnection(context);
			rc = new RichiestaContributi();

			rc.setInserito_da(this.getUserId(context));
			rc.setAsl(this.getUserAsl(context));

			Integer numero_protocollo = Integer.valueOf(context.getRequest()
					.getParameter("elencoPratiche"));
			ContributiAnimaleList lc = new ContributiAnimaleList();

			if (numero_protocollo != null) {

				rc.setProtocollo(numero_protocollo);
			}

			lc.setIdAsl(this.getUserAsl(context));
			lc.setIdPraticaContributi(Integer.valueOf(numero_protocollo));

			//Flusso 251
			lc.buildListCaniNonPagati(db, ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getIdComune());
			// lc.getLista(db,
			// this.getUserAsl(context),Integer.valueOf(numero_protocollo));

			context.getRequest().setAttribute("richiestaContributi", rc);
			context.getRequest().setAttribute("listaCani", lc);
			context.getSession().setAttribute("richiestaContributi", rc);
			context.getSession().setAttribute("listaCani", lc);
			Pratica nuova_pratica = new Pratica(db, numero_protocollo);
			context.getRequest().setAttribute("nuova_pratica", nuova_pratica);
			context.getSession().setAttribute("tipo_richiesta",
					rc.getTipo_richiesta());

			// Lookups

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			// specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--");
			context.getRequest().setAttribute("comuniList", comuniList);

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			System.gc();
		}
		return ("ViewListaCani");
	}

	public String executeCommandSaveRichiesta(ActionContext context) throws SQLException {

		if (!hasPermission(context, "richiesta-contributi-view")) {
			return ("PermissionError");
		}

		RichiestaContributi rc = (RichiestaContributi) context.getSession()
				.getAttribute("richiestaContributi");

		ContributiAnimaleList lc = (ContributiAnimaleList) context.getSession()
				.getAttribute("listaCani");
		int sizeCani = Integer.parseInt(context.getParameter("sizeCani"));
		List<Animale> listaCaniOk = new ArrayList();
		String valueCane;
		for (int i = 0; i < sizeCani; i++) {
			valueCane = context.getParameter("checkCane" + i);

			if (valueCane.equalsIgnoreCase("true")) {
				listaCaniOk.add((Animale) lc.get(i));
			}
		}
		Connection db = null;

		try {

		//	db = this.getConnection(context);
			
			String dbName ="bduM";// ApplicationProperties.getProperty("dbnameBdu");
			String username = ApplicationProperties
					.getProperty("usernameDbbdu");
			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			String host = ((InetAddress)((HashMap<String, InetAddress>)context.getSession().getServletContext().getAttribute("hosts")).get("dbBDUL")).getHostAddress();;

			db = this.getConnection(context);
			//db = DbUtil.getConnection(dbName, username, pwd, host);

			RichiestaContributi rcFinale = new RichiestaContributi();
			boolean result = (Boolean) rcFinale.saveRichiesta(db, rc,
					listaCaniOk);
			context.getSession().setAttribute("resultRichiesta", result);
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			//this.freeConnection(context, db);
			DbUtil.chiudiConnessioneJDBC(null, db);
			context.getSession().removeAttribute("listaCani");
			context.getSession().removeAttribute("richiestaContributi");
		}
		return "ResultRichiesta";
	}

	public String executeCommandViewRichieste(ActionContext context) {

		if (!hasPermission(context, "approvazione-contributi-view")) {
			return ("PermissionError");
		}

		return "tipologieContributi";
	}

	public String executeCommandViewRichiesteCatturati_Padronali(
			ActionContext context) {

		if (!hasPermission(context, "approvazione-contributi-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		try {

			db = this.getConnection(context);

			ListaRichieste lr = new ListaRichieste();
			List<RichiestaContributi> lrc = lr.getListaRichiesteContributi(db);

			context.getRequest().setAttribute("listaRC", lrc);
			context.getSession().setAttribute("listaRC", lrc);

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "ViewListaRichiestedaApprovare";
	}

	public String executeCommandViewDetailsListaRC(ActionContext context) {

		if (!hasPermission(context, "approvazione-contributi-view")) {
			return ("PermissionError");
		}

		List<RichiestaContributi> lrc = (List) context.getSession()
				.getAttribute("listaRC");

		int idDettaglio = Integer.parseInt(context.getParameter("idDettaglio"));
		int n_prot = Integer.parseInt(context.getParameter("n_protocollo"));
		Connection db = null;

		try {

			db = this.getConnection(context);

			ContributiAnimaleList listaCaniDettaglio = new ContributiAnimaleList();
			// List<Cane> listaCaniDettaglio=(List)lc.getListaDettaglio(db,
			// idDettaglio);
			listaCaniDettaglio.setIdPraticaContributi(idDettaglio);
			listaCaniDettaglio.buildListCaniInviatiPerApprovazione(db);
			context.getSession().setAttribute("listaCaniDettaglio",
					listaCaniDettaglio);
			context.getSession().setAttribute("idDettaglio", idDettaglio);
			context.getSession().setAttribute("n_protocollo", n_prot);

			// Lookups

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			// specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--");
			context.getRequest().setAttribute("Asl", siteList);

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);

		}
		return "ViewDettaglioListaRichieste";
	}

	public String executeCommandRifiutaPagamenti(ActionContext context) {

		if (!hasPermission(context, "approvazione-contributi-view")) {
			return ("PermissionError");
		}

		List<Animale> listaCani = (List) context.getSession().getAttribute(
				"listaCaniDettaglio");

		int idDettaglio = (Integer) context.getSession().getAttribute(
				"idDettaglio");
		int n_protocollo = (Integer) context.getSession().getAttribute(
				"n_protocollo");

		int userId = this.getUserId(context);

		Connection db = null;

		try {

			db = this.getConnection(context);

			Pagamenti p = new Pagamenti();

			boolean result = p.saveRichiestaFailed(db, idDettaglio, userId,
					n_protocollo);

			context.getSession().setAttribute("resultPagamenti", result);

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			context.getSession().removeAttribute("listaCani");
			context.getSession().removeAttribute("richiestaContributi");
			context.getSession().removeAttribute("idDettaglio");
		}
		return "ResultPagamenti";
	}

	public String executeCommandRespingi(ActionContext context) {

		if (!hasPermission(context, "approvazione-contributi-view")) {
			return ("PermissionError");
		}

		List<Animale> listaCani = (List) context.getSession().getAttribute(
				"listaCaniDettaglio");

		int idDettaglio = (Integer) context.getSession().getAttribute(
				"idDettaglio");

		int userId = this.getUserId(context);

		Connection db = null;

		try {

			db = this.getConnection(context);

			Pagamenti p = new Pagamenti();

			boolean result = p.saveRespingiInMOdifica(db, idDettaglio, userId);

			context.getSession().setAttribute("resultPagamenti", result);

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			context.getSession().removeAttribute("listaCani");
			context.getSession().removeAttribute("richiestaContributi");
			context.getSession().removeAttribute("idDettaglio");
		}
		return "ResultPagamenti";
	}

	public String executeCommandViewContributi(ActionContext context) {

		if (!hasPermission(context, "report-contributi-view")) {
			return ("PermissionError");
		}

		String approvazione = (String) context.getRequest().getParameter(
				"approvati");

		Connection db = null;

		try {

			db = this.getConnection(context);

			ListaRichieste lr = new ListaRichieste();
			List<RichiestaContributi> lrc;

			if (approvazione.equalsIgnoreCase("yes")) {
				lrc = lr.getReportContributi(db, true);
				context.getRequest().setAttribute("approvati", true);
			} else {
				lrc = lr.getReportContributi(db, false);
				context.getRequest().setAttribute("approvati", false);

			}

			context.getRequest().setAttribute("listaRC", lrc);
			context.getSession().setAttribute("listaRC", lrc);

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "ViewReportContributi";
	}

	public String executeCommandViewDetailsContributi(ActionContext context) {

		if (!hasPermission(context, "report-contributi-view")) {
			return ("PermissionError");
		}

		List<RichiestaContributi> lrc = (List) context.getSession()
				.getAttribute("listaRC");

		int idDettaglio = Integer.parseInt(context.getParameter("idDettaglio"));
		int numero_protocollo = Integer.parseInt(context
				.getParameter("n_protocollo"));
		Connection db = null;

		try {

			//db = this.getConnection(context);
			/*
			 * ListaCani lc=new ListaCani(); List<Cane>
			 * listaCaniDettaglio=(List)lc.getReportListaDettaglio(db,
			 * idDettaglio);
			 */
			
//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties
//					.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
			
			db = GestoreConnessioni.getConnection();
			
		//db = DbUtil.getConnection(dbName, username, pwd, host);

			ContributiAnimaleList listaCaniDettaglio = new ContributiAnimaleList();
			listaCaniDettaglio.setIdPraticaContributi(idDettaglio);
			listaCaniDettaglio.buildListCaniInviatiPerApprovazione(db);

			context.getSession().setAttribute("listaCaniDettaglio",
					listaCaniDettaglio);
			context.getSession().setAttribute("idDettaglio", idDettaglio);
			context.getRequest().setAttribute("numero_protocollo",
					numero_protocollo);

			// Lookups

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			// specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--");
			context.getRequest().setAttribute("Asl", siteList);

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			//DbUtil.close(null, db);
			GestoreConnessioni.freeConnection(db);

		}
		return "ViewDettaglioReportContributi";
	}

	public String executeCommandConfermaPagamenti(ActionContext context) throws SQLException {

		if (!hasPermission(context, "approvazione-contributi-view")) {
			return ("PermissionError");
		}
		List<Animale> listaCani = (List) context.getSession().getAttribute(
				"listaCaniDettaglio");

		int idDettaglio = (Integer) context.getSession().getAttribute(
				"idDettaglio");
		int userId = this.getUserId(context);
		int sizeCani = Integer.parseInt(context.getParameter("sizeCani"));
		int n_protocollo = (Integer) context.getSession().getAttribute(
				"n_protocollo");
		List<Animale> listaCaniOk = new ArrayList();
		List<Animale> listaCaniDettaglio = new ArrayList();
		String valueCane;
		Animale c = null;
		for (int i = 0; i < sizeCani; i++) {
			valueCane = context.getParameter("pagamentoCane" + i);
			c = listaCani.get(i);
			if (valueCane.equalsIgnoreCase("true")) {
				c.setContributoPagato(true);

				listaCaniOk.add(listaCani.get(i));
			} else {
				c.setContributoPagato(false);
			}
			listaCaniDettaglio.add(c);
		}

		Connection db = null;

		try {

			//db = this.getConnection(context);
			
			String dbName ="bduM";// ApplicationProperties.getProperty("dbnameBdu");
			String username = ApplicationProperties
					.getProperty("usernameDbbdu");
			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			String host = ((InetAddress)((HashMap<String, InetAddress>) context.getSession().getServletContext().getAttribute("hosts")).get("dbBDUL")).getHostAddress();

			db = this.getConnection(context);
			//db = DbUtil.getConnection(dbName, username, pwd, host);

			Pagamenti p = new Pagamenti();

			boolean result = p.saveRichiestaOK(db, listaCaniOk, idDettaglio,
					userId, n_protocollo);

			context.getSession().setAttribute("resultPagamenti", result);

			String nomefile = elenco_pagamenti(db, context);

			context.getSession().setAttribute("listaCaniDettaglio",
					listaCaniDettaglio);
			// String nomefile2=pratica_approvata(context,idDettaglio);
			String nomefile2 = pratica_approvata(db, context, idDettaglio,
					n_protocollo);
			String archivio = "contributi_sterilizzazioni.zip";
			String dir_tmp = context.getServletContext().getRealPath("/")
					+ "WEB-INF" + File.separator
					+ "contributi_sterilizzazioni_tmp/";

			JZip(context, nomefile, nomefile2, archivio, dir_tmp);
			
			 //GestionePEC pec=new GestionePEC(dir_tmp+archivio);
		} catch (Exception e) {
			db.rollback();
			e.printStackTrace();
			context.getRequest().setAttribute("Error", "Si è verificato un problema con la conferma del pagamento per la pratica con id " +idDettaglio+" ");
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			context.getSession().removeAttribute("listaCani");
			context.getSession().removeAttribute("richiestaContributi");
			context.getSession().removeAttribute("idDettaglio");
			context.getSession().removeAttribute("n_protocollo");

		}

		return "ResultPagamenti";

	}

	public String pratica_approvata(Connection db, ActionContext context,
			int id, int num_protocollo) throws Exception {
		if (!hasPermission(context, "report-approvazione-contributi-view")) {
			return ("PermissionError");

		}
		String nomefile = null;
		//try {
			String dir = context.getServletContext().getRealPath("/")
					+ "WEB-INF" + File.separator + "nuovi_report_pdf_template"
					+ File.separator;

			PdfReader reader = new PdfReader(dir + "Richiesta Contributi.pdf");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream out2 = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, out);
			AcroFields form = stamper.getAcroFields();

			ArrayList elenco = new ArrayList();
			Stats header = new Stats();

			float[] sizes = null;

			sizes = richiestaContributi(context, elenco, header, db);
			List<Animale> listaCani = (List) context.getSession().getAttribute(
					"listaCaniDettaglio");

			Pratica p = new Pratica(db, num_protocollo);
			Animale c;
			int si = 0;
			int no = 0;
			boolean pagamento;
			for (int i = 0; i < listaCani.size(); i++) {

				c = (Animale) listaCani.get(i);
				pagamento = c.isContributoPagato(); // controllo si /no
				if (pagamento == true) {
					si++;
				} else
					no++;
			}

			String reportName = "Dettaglio Contributi";
			form.setField("titolo", "Richiesta Contributi");
			form.setField("data", oggi());
			form.setField("approvati", Integer.toString(si));
			form.setField("respinti", Integer.toString(no));
			form.setField("totale", Integer.toString(si + no));

			form.setField("numero", "" + p.getNumeroDecretoPratica());
			form.setField("asl", p.getDescrizioneAslPratica());
			// data decreto formattata
			form.setField("data_decreto", p.getDataDecretoFormattata());
			form.setField("oggetto", "Oggetto: " + p.getOggettoPratica());
			form.setField("dal", "" + p.getDataInizioSterilizzazioneStringa());
			form.setField("al", "" + p.getDataFineSterilizzazioneStringa());
			form.setField("comuni", "" + p.getComuniElencoNome());
			stamper.setFormFlattening(true);
			stamper.close();
			setElenco(context, stamper, elenco, reportName, header, sizes, out,
					out2);
			nomefile = timestamp_inverso() + "_Pratica_approvata_numero" + id
					+ ".pdf";
			// TODO 
			write3( context, out2, reportName,nomefile );

//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return nomefile;
	}

	public String elenco_pagamenti(Connection db, ActionContext context) {
		if (!hasPermission(context, "report-approvazione-contributi-view")) {
			return ("PermissionError");

		}

		String nomefile = null;
		try {
			// logger.info(
			// "[CANINA] - ----------------- Contributi Approvati -----------------"
			// );

			String dir = context.getServletContext().getRealPath("/")
					+ "WEB-INF" + File.separator + "nuovi_report_pdf_template"
					+ File.separator;

			PdfReader reader = new PdfReader(dir + "Contributi_approvati.pdf");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream out2 = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, out);
			AcroFields form = stamper.getAcroFields();
			ArrayList elenco = new ArrayList();
			Stats header = new Stats();

			float[] sizes = null;

			//sizes = contributi_pagati(context, elenco, header, db);

			String reportName = "Contributi approvati";

			form.setField("titolo", "Contributi Approvati");
			form.setField("data", oggi());
			form.setField("approvati", Integer.toString(elenco.size() / 8));

			stamper.setFormFlattening(true);
			stamper.close();
			setElenco(context, stamper, elenco, reportName, header, sizes, out,
					out2);

			nomefile = timestamp_inverso() + "_Contributi_sterilizzazioni.pdf";
			// TODO 
			write3( context, out2, reportName,nomefile );

		} catch (Exception e) {
			e.printStackTrace();
		}

		return nomefile;
	}

	private static String timestamp_inverso() {
		return (new SimpleDateFormat("dd-MM-yyyy")).format(new Date());
	}

	private static String timestamp() {
		return (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(new Date());

	}

	private static void write3(ActionContext context,
			ByteArrayOutputStream out, String reportName, String nomefile)
			throws IOException, EmailException {
		String nome_file = nomefile;
		String dir_tmp = context.getServletContext().getRealPath("/")
				+ "WEB-INF" + File.separator
				+ "contributi_sterilizzazioni_tmp/";

		// crea file .pdf
		File f = new File(dir_tmp + nomefile);
		// controllo che la directory sia vuota altrimenti provvede con
		// l'eliminazione dei file prima di creare i nuovi
		if (f.exists()) {
			if (f.delete()) { // prova a eliminarlo...
				// logger.info("[CANINA] - File eliminato, è possibile crearne uno nuovo!");
				// //e conferma...
			} else {
				// logger.info("[CANINA] - Non e possibile eliminare il file pdf, non esiste");
			}
		}

			// scrive nel nuovo file in una directory tmp
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(out.toByteArray());
			fos.flush();
			fos.close();

		}
	

	public static float[] contributi_pagati(ActionContext context,
			ArrayList elenco, Stats header, Connection db) throws SQLException,
			DocumentException {
		float[] fa = null;
		try {
			ArrayList<Float> ret = new ArrayList<Float>();

			// Lookups

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			specieList.addItem(-1, "--");
			context.getRequest().setAttribute("specieList", specieList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--");
			context.getRequest().setAttribute("comuniList", comuniList);

			// String
			// sql="Select id_richiesta_contributi, microchip, tipologia, data_approvazione,data_sterilizzazione,comune from contributi_lista_univocita ";
			// String
			// sql="Select a.id_richiesta_contributi, a.microchip, a.tipologia,a.data_approvazione, a.data_sterilizzazione,a.comune,p.numero_protocollo,l.description  as asl from contributi_lista_univocita  a join lookup_asl_rif l on l.code=a.asl left join  contributi_richieste p on p.id=a.id_richiesta_contributi and a.id_richiesta_contributi!=228";
			// il valore id=2 corrisponde alla richiesta relativa al pregresso
			// presente in banca dati
			// String
			// sql="Select a.id_richiesta_contributi, a.microchip, a.tipologia,a.data_approvazione, a.data_sterilizzazione,a.comune_cattura,a.comune_proprietario,p.numero_protocollo, l.description  as asl from contributi_lista_univocita  a join lookup_asl_rif l on l.code=a.asl, contributi_richieste p where  p.id=a.id_richiesta_contributi and  p.id!=228 ";
			String sql = "Select a.id_richiesta_contributi, a.microchip,a.tipo_animale, a.tipologia,a.data_approvazione, a.data_sterilizzazione,a.comune_cattura,a.comune_proprietario,a.comune_colonia,k.numero_decreto,k.data_decreto, l.description  as asl from contributi_lista_univocita a join lookup_asl_rif l on l.code=a.asl , contributi_richieste p,pratiche_contributi k where  p.id=a.id_richiesta_contributi and"
					+ " k.id=p.numero_protocollo and  p.id!=228";
			PreparedStatement stat = db.prepareStatement(sql);
			ResultSet res = stat.executeQuery();
			while (res.next()) {
				// elenco.add(res.getInt("id_richiesta_contributi"));
				elenco.add(res.getString("microchip"));
				String tipo_animale = specieList.getSelectedValue(res
						.getInt("tipo_animale"));
				elenco.add(tipo_animale);
				elenco.add(res.getString("tipologia"));
				elenco
						.add(formatData(res
								.getTimestamp("data_sterilizzazione")));
				String comune_cattura = comuniList.getSelectedValue(res
						.getInt("comune_cattura"));
				elenco.add(comune_cattura);
				String comune_proprietario = comuniList.getSelectedValue(res
						.getInt("comune_proprietario"));
				elenco.add(comune_proprietario);
				String comune_colonia = comuniList.getSelectedValue(res
						.getInt("comune_colonia"));
				elenco.add(comune_colonia);
				// elenco.add(res.getTimestamp("data"))
				elenco.add(formatData(res.getTimestamp("data_approvazione")));
				elenco.add(res.getString("numero_decreto"));
				elenco.add(formatData(res.getTimestamp("data_decreto")));
				elenco.add(res.getString("asl"));

			}

			header.add("Microchip");
			ret.add(1.5f);
			header.add("Tipo Animale");
			ret.add(0.7f);
			header.add("Tipologia");
			ret.add(1.0f);
			header.add("Data Sterilizzazione");
			ret.add(1.0f);
			header.add("Comune Cattura");
			ret.add(0.9f);
			header.add("Comune Proprietario");
			ret.add(1.0f);
			header.add("Comune Colonia");
			ret.add(0.9f);
			header.add("Data Approvazione");
			ret.add(1.0f);
			header.add("N° Progetto");
			ret.add(0.6f);
			header.add("Data Decreto");
			ret.add(1.0f);
			header.add("Asl Sterilizzante");
			ret.add(1.0f);

			fa = new float[ret.size()];

			for (int i = 0; i < ret.size(); i++) {
				fa[i] = ret.get(i).floatValue();
			}
		} catch (Exception e) {
			// logger.severe("[CANINA] - EXCEPTION nel metodo contributi_pagati della classe CntributiSterilizzazioni");
			e.printStackTrace();
		}

		return fa;

	}

	private static String formatData(Timestamp timestamp) {
		return (timestamp == null) ? ("") : (sdf.format(timestamp));
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static void setElenco(ActionContext context, PdfStamper stamper,
			ArrayList elenco, String reportName, Stats header, float[] sizes,
			ByteArrayOutputStream out, ByteArrayOutputStream out2) {
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out2);
			PdfReader reader = new PdfReader(out.toByteArray());
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			PdfImportedPage page = writer.getImportedPage(reader, 1);

			cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);

			PdfPTable table = null;

			Color blue = new Color(114, 159, 207);
			Font f = new Font(Font.HELVETICA, 8, Font.NORMAL, Color.black);
			Font fh = new Font(Font.HELVETICA, 7, Font.NORMAL, Color.white);
			BaseFont fp = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE,
					BaseFont.CP1252, BaseFont.NOT_EMBEDDED);// 8, Font.ITALIC,
			// blue);

			int numero_pagine = calcolaNumeroPagine(elenco.size());
			int curr_pag = 0;
			for (int i = 0; i < elenco.size(); i++) {
				if ((i % (page_elem)) == 0) {
					document.newPage();
					++curr_pag;

					cb.beginText();
					cb.setFontAndSize(fp, 10);
					cb.setRGBColorFill(114, 159, 207);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
							"DETTAGLIO REPORT  \"" + reportName + "\"", 10,
							PageSize.A4.height() * 0.955f, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Pagina "
							+ curr_pag + " di " + numero_pagine, PageSize.A4
							.width() - 10, PageSize.A4.height() * 0.955f, 0);
					cb.endText();

					table = new PdfPTable(sizes);
					// era 80
					table.setTotalWidth(PageSize.A4.width() - 20);
					table.getDefaultCell().setBorderWidth(0);
					// table.getDefaultCell().setPadding(2);
					table.getDefaultCell().setPaddingBottom(5);
					table.getDefaultCell().setHorizontalAlignment(
							Element.ALIGN_CENTER);
					table.getDefaultCell().setBackgroundColor(blue);
					for (int j = 0; j < header.getSize(); j++) {
						table.addCell(new Phrase(header.get(j), fh));
					}
				}

				table.getDefaultCell().setBackgroundColor(Color.white);
				Object st = elenco.get(i);

				table.addCell(new Phrase((st != null) ? st.toString() : "", f));
				if (((i + 1) % page_elem) == 0) {
					table.writeSelectedRows(0, -1, 10,
							PageSize.A4.height() * 0.94f, cb);
					table = null;
				}
			}

			if (table != null) {
				table.writeSelectedRows(0, -1, 10,
						PageSize.A4.height() * 0.94f, cb);
			}

			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static float[] richiestaContributi(ActionContext context,
			ArrayList elenco, Stats header, Connection db) {
		float[] fa = null;
		// logger.info(
		// "[CANINA] - ----------------- Contributi Approvati -----------------"
		// );
		try {
			ArrayList<Float> ret = new ArrayList<Float>();

			ArrayList temp = new ArrayList();
			int idCane;
			String microchipCane;
			String propCane;
			Timestamp approvazione;
			Timestamp data_ster;
			String tipologia;
			boolean pagamento;
			String protocollo;
			String comune_cattura = "";
			String comune_proprietario;
			int id_comune_colonia = -1;
			String asl;
			String tipo_animale = "";
			List listaCani = (List) context
					.getSession().getAttribute("listaCaniDettaglio");
			// List<Cane> listaCani = (List)context.getSession().getAttribute(
			// "listaCaniDettaglio" );

			Animale c;
			int si = 0;
			int no = 0;
			
			ComuniAnagrafica comuni = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = comuni.buildList(db,
					-1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
		//	context.getRequest().setAttribute("comuniList", comuniList);

			LookupList aslList = new LookupList(db, "lookup_asl_rif");
			aslList.addItem(-1, "--");
			//context.getRequest().setAttribute("aslRifList", aslList);
			
			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			// specieList.addItem(-1, "--Seleziona--");
		//	context.getRequest().setAttribute("specieList", specieList);
			
			
			for (int i = 0; i < listaCani.size(); i++) {

				c = (Animale) listaCani.get(i);

				// idCane=c.getId_cane();
				// elenco.add(idCane);
				microchipCane = c.getMicrochip();
				elenco.add(microchipCane);
				propCane = c.getNomeCognomeProprietario();
				elenco.add(propCane);

				switch (c.getIdSpecie()) {
				case Cane.idSpecie:
					tipo_animale = "Cane";
					break;

				case Gatto.idSpecie:
					tipo_animale = "Gatto";
//					Gatto thisGatto = new Gatto(db, c.getIdAnimale());
//					Operatore detentore = thisGatto.getDetentore();
//					Stabilimento stab = (Stabilimento) detentore
//							.getListaStabilimenti().get(0);
//					LineaProduttiva lp = (LineaProduttiva) stab
//							.getListaLineeProduttive().get(0);
//					if (lp.getIdAttivita() == 7) { // TODO CORREGGERE CON VALORE
//						// 7
					//	id_comune_colonia = stab.getSedeOperativa().getComune();
				//	}
					//in idComuneDetentore c'è il com colonia
					id_comune_colonia = c.getIdComuneDetentore();
					break;
				}
				elenco.add(tipo_animale);
				if (c.isFlagCattura())
					tipologia = "Catturato";
				else
					tipologia = "Padronale";

			

				elenco.add(tipologia);




				data_ster = c.getDataSterilizzazione();
				elenco.add(formatData(data_ster));

				if (c.getIdSpecie() == Cane.idSpecie) {
					//Cane thisCane = new Cane(db, c.getIdAnimale());
//					comune_cattura = comuniList.getSelectedValue(thisCane
//							.getIdComuneCattura());
					comune_cattura = comuniList.getSelectedValue(c.getIdComuneCattura());
					
				}
				elenco.add(comune_cattura);

				//Stabilimento stab = (Stabilimento) c.getProprietario()
					//	.getListaStabilimenti().get(0);
				int id_comune =c.getIdComuneProprietario();

				comune_proprietario = comuniList.getSelectedValue(id_comune);
				elenco.add(comune_proprietario);

				elenco.add(comuniList.getSelectedValue(id_comune_colonia));

				asl = aslList.getSelectedValue(c.getIdAslRiferimento());
				elenco.add(asl);
				//c.setContributoPagato(db, );
				if (c.isContributoPagato() == true) {
					si++;
				} else
					no++;
				elenco.add(c.isContributoPagato() ? ("SI") : ("NO"));

			}

			header.add("Microchip");
			ret.add(1.6f);
			header.add("Proprietario");
			ret.add(1.4f);
			header.add("Tipo Animale");
			ret.add(1.0f);
			header.add("Tipologia");
			ret.add(0.9f);
			header.add("Data Sterilizzazione");
			ret.add(1.4f);
			header.add("Comune Cattura");
			ret.add(1.2f);
			header.add("Comune Proprietario");
			ret.add(1.3f);
			header.add("Comune Colonia");
			ret.add(1.2f);
			header.add("Asl Sterilizzante");
			ret.add(1.3f);
			header.add("Pagato");
			ret.add(1.0f);

			fa = new float[ret.size()];
			for (int i = 0; i < ret.size(); i++) {
				fa[i] = ret.get(i).floatValue();
			}
			// return fa;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fa;

	}

	public String executeCommandprova(ActionContext context) {
		if (!hasPermission(context, "report-approvazione-contributi-view")) {
			return ("PermissionError");

		}
		Connection db = null;
		try {

			String dir = context.getServletContext().getRealPath("/")
					+ "WEB-INF" + File.separator + "nuovi_report_pdf_template"
					+ File.separator;

			PdfReader reader = new PdfReader(dir + "Richiesta Contributi.pdf");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream out2 = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, out);
			AcroFields form = stamper.getAcroFields();

			ArrayList elenco = new ArrayList();
			Stats header = new Stats();

			db = this.getConnection(context);
			float[] sizes = null;

			Integer numero_protocollo = Integer.valueOf(context.getRequest()
					.getParameter("idProtocollo"));

			Pratica p = new Pratica(db, numero_protocollo);

			sizes = richiestaContributi(context, elenco, header, db);
			List<Animale> listaCani = (List) context.getSession().getAttribute(
					"listaCaniDettaglio");
			Animale c;
			int si = 0;
			int no = 0;
			boolean pagamento;
			for (int i = 0; i < listaCani.size(); i++) {

				c = (Animale) listaCani.get(i);
			//	c.setContributoPagato(db);
				// pagamento=c.isPagato(); //controllo si /no
				if (c.isContributoPagato()) {
					si++;
				} else
					no++;
			}

			String reportName = "Dettaglio Contributi";
			form.setField("titolo", "Richiesta Contributi");
			form.setField("data", oggi());
			form.setField("approvati", Integer.toString(si));
			form.setField("respinti", Integer.toString(no));
			form.setField("totale", Integer.toString(si + no));
			form.setField("numero", "" + p.getNumeroDecretoPratica());
			form.setField("asl", p.getDescrizioneAslPratica());
			form.setField("data_decreto", p.getDataDecretoFormattata());
			form.setField("oggetto", "Oggetto: " + p.getOggettoPratica());
			form.setField("dal", "" + p.getDataInizioSterilizzazioneStringa());
			form.setField("al", "" + p.getDataFineSterilizzazioneStringa());
			form.setField("comuni", "" + p.getNomiComuni(db));

			stamper.setFormFlattening(true);
			stamper.close();

			setElenco(context, stamper, elenco, reportName, header, sizes, out,
					out2);
			write(context, out2, reportName);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "-none-";
	}

	private static void write(ActionContext context, ByteArrayOutputStream out,
			String reportName) throws IOException, EmailException {
		HttpServletResponse res = context.getResponse();
		res.setContentType("application/pdf");

		res.setHeader("Content-Disposition", "attachment; filename=\""
				+ timestamp_inverso() + "_" + reportName + ".pdf\";");
		ServletOutputStream sout = res.getOutputStream();
		sout.write(out.toByteArray());

		sout.flush();

	}

	// metodo per zippare due file
	private static void JZip(ActionContext context, String nomefilepdf,
			String nomefile2, String nomefilezip, String directory) {

		// il metodo try/catch di permette di gestire le eccezioni, se qualcosa
		// non funziona in try allora catch notifica l'errore
		String dir_tmp = null;
		try {

			dir_tmp = directory;

			// definiamo l'output previsto che sarà un file in formato zip
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(dir_tmp + nomefilezip)));

			// definiamo il buffer per lo stream di bytes
			byte[] data = new byte[1000];

			// indichiamo il nome del file che subirà la compressione
			BufferedInputStream in1 = new BufferedInputStream(
					new FileInputStream(dir_tmp + nomefilepdf));
			BufferedInputStream in2 = new BufferedInputStream(
					new FileInputStream(dir_tmp + nomefile2));
			int count;
			// processo di compressione

			// out.putNextEntry(new ZipEntry(nomefile));
			out.putNextEntry(new ZipEntry(nomefilepdf));
			while ((count = in1.read(data, 0, 1000)) != -1) {
				out.write(data, 0, count);
			}

			int count2;
			// processo di compressione

			// out.putNextEntry(new ZipEntry(nomefile));
			out.putNextEntry(new ZipEntry(nomefile2));
			while ((count = in2.read(data, 0, 1000)) != -1) {
				out.write(data, 0, count);
			}
			in1.close();
			in2.close();
			out.flush();
			out.close();

		} catch (Exception e) {
			// logger.severe("[CANINA] - EXCEPTION nel metodo JZip della classe CntributiSterilizzazioni");
			e.printStackTrace();
		}
	}

	public String executeCommandstampa_approvati(ActionContext context) {
		if (!hasPermission(context, "report-approvazione-contributi-view")) {
			return ("PermissionError");

		}
		Connection db = null;

		try {
			db = getConnection(context);
			// logger.info(
			// "[CANINA] - ----------------- Contributi Approvati -----------------"
			// );

			String dir = context.getServletContext().getRealPath("/")
					+ "WEB-INF" + File.separator + "nuovi_report_pdf_template"
					+ File.separator;

			PdfReader reader = new PdfReader(dir + "Contributi_approvati.pdf");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream out2 = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, out);
			AcroFields form = stamper.getAcroFields();
			ArrayList elenco = new ArrayList();
			Stats header = new Stats();

			float[] sizes = null;

			sizes = contributi_pagati(context, elenco, header, db);

			String reportName = "Contributi approvati";

			form.setField("titolo", "Contributi Approvati");
			form.setField("data", oggi());
			form.setField("approvati", Integer.toString(elenco.size() / 11));

			stamper.setFormFlattening(true);
			stamper.close();
			setElenco(context, stamper, elenco, reportName, header, sizes, out,
					out2);

			write(context, out2, reportName);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
			System.gc();
		}
		return "-none-";
	}
	
	
	public String executeCommandInviaPec(ActionContext context) throws SQLException{
		


//		if (!hasPermission(context, "approvazione-contributi-view")) {
//			return ("PermissionError");
//		}
		

		int idDettaglio = 151;
		int userId = this.getUserId(context);
		//int sizeCani = Integer.parseInt(context.getParameter("sizeCani"));
		int n_protocollo =1699;
		

		Connection db = null;

		try {

			//db = this.getConnection(context);
			
			String dbName = "bdu";//ApplicationProperties.getProperty("b");
			String username = "postgres";
			String pwd = "postgres";
			String host = "10.1.15.4";

			db = DbUtil.getConnection(dbName, username, pwd, host);

			//Pagamenti p = new Pagamenti();

//			boolean result = p.saveRichiestaOK(db, listaCaniOk, idDettaglio,
//					userId, n_protocollo);

			//context.getSession().setAttribute("resultPagamenti", result);
			
			ContributiAnimaleList lc = new ContributiAnimaleList();

//			if (n_protocollo > -1) {
//
//				rc.setProtocollo(n_protocollo);
//			}

			lc.setIdAsl(this.getUserAsl(context));
			lc.setIdPraticaContributi(idDettaglio);

			lc.buildListCaniPagati(db);
			
			

			String nomefile = elenco_pagamenti(db, context);
			context.getSession().setAttribute("listaCaniDettaglio",
					lc);

//			context.getSession().setAttribute("listaCaniDettaglio",
//					listaCaniDettaglio);
			// String nomefile2=pratica_approvata(context,idDettaglio);
			String nomefile2 = pratica_approvata(db, context, idDettaglio,
					n_protocollo);
			String archivio = "contributi_sterilizzazioni.zip";
			String dir_tmp = context.getServletContext().getRealPath("/")
					+ "WEB-INF" + File.separator
					+ "contributi_sterilizzazioni_tmp/";

			JZip(context, nomefile, nomefile2, archivio, dir_tmp);
			
			 GestionePEC pec=new GestionePEC(dir_tmp+archivio);
		} catch (Exception e) {
			db.rollback();
			e.printStackTrace();
			context.getRequest().setAttribute("Error", "Si è verificato un problema con la conferma del pagamento per la pratica con id " +idDettaglio+" ");
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			context.getSession().removeAttribute("listaCani");
			context.getSession().removeAttribute("richiestaContributi");
			context.getSession().removeAttribute("idDettaglio");
			context.getSession().removeAttribute("n_protocollo");

		}

		return "ResultPagamenti";

	
		
	}
	
	
	public static void updateMicrochip(Connection con,
			String microchip, String oldMicrochip) throws SQLException{
		
	PreparedStatement	pstMc = con
		.prepareStatement("update contributi_lista_animali set microchip = ?  where microchip =?  ");
		pstMc.setString(1, microchip);
		pstMc.setString(2, oldMicrochip);
		pstMc.executeUpdate();
		
	    pstMc = con.prepareStatement("update contributi_lista_univocita set microchip = ? where microchip = ?");
		pstMc.setString(1, microchip);
		pstMc.setString(2, oldMicrochip);
		pstMc.executeUpdate();
		
		pstMc.close();
		
	}

}
