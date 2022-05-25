/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioni.base.Capo;
import org.aspcfs.modules.macellazioni.base.Organi;
import org.aspcfs.modules.mu.base.Articolo17;
import org.aspcfs.modules.mu.base.Articolo17List;
import org.aspcfs.modules.mu.base.CapoUnivoco;
import org.aspcfs.modules.mu.base.CapoUnivocoList;
import org.aspcfs.modules.mu.base.Organo;
import org.aspcfs.modules.mu.base.PartitaUnivoca;
import org.aspcfs.modules.mu.base.PartitaUnivocaList;
import org.aspcfs.modules.mu.base.SedutaUnivoca;
import org.aspcfs.modules.mu.operazioni.base.Comunicazioni;
import org.aspcfs.modules.mu.operazioni.base.Macellazione;
import org.aspcfs.modules.mu.operazioni.base.MacellazioneLiberoConsumo;
import org.aspcfs.modules.mu.operazioni.base.MorteANM;
import org.aspcfs.modules.mu.operazioni.base.VisitaAM;
import org.aspcfs.modules.mu.operazioni.base.VisitaAMSemplificata;
import org.aspcfs.modules.mu.operazioni.base.VisitaPM;
import org.aspcfs.modules.mu.operazioni.base.VisitaPMSemplificata;
import org.aspcfs.modules.mu_wkf.base.Path;
import org.aspcfs.modules.mu_wkf.base.PathWKF;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.ParameterUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class MacellazioneUnica extends CFSModule {

	public String executeCommandList(ActionContext context) {
		Connection db = null;

		try {
			db = this.getConnection(context);
			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);

			SedutaUnivoca seduta = new SedutaUnivoca();
			seduta.setIdMacello(org.getOrgId());
			Vector sedutaList = new Vector();
			sedutaList = seduta.ricercaSeduta(db);
			context.getRequest().setAttribute("listaSedute", sedutaList);

			caricaLookup(context, db);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "ListOK";
	}

	public String executeCommandNuovaPartita(ActionContext context) {
		Connection db = null;

		try {
			db = this.getConnection(context);

			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);
			int codiceUnivoco = PartitaUnivoca.nextId(db);
			context.getRequest().setAttribute("codiceUnivoco", codiceUnivoco + "");

			Date d = new Date();
			context.getRequest().setAttribute("anno", 1900 + d.getYear() + "");
			int asl = ((UserBean) context.getRequest().getSession().getAttribute("User")).getSiteId();
			String aslString = "";

			// Gestisco casi in cui l'utente non ha ASL
			if (asl < 0)
				aslString = "0";
			else
				aslString = String.valueOf(asl);

			// Padding a tre cifre
			while (aslString.length() < 3) {
				aslString = "0" + aslString;
			}

			context.getRequest().setAttribute("asl", aslString);

			caricaLookup(context, db);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		String popup = context.getRequest().getParameter("popup");
		if (popup != null && popup.equals("true")) {
			context.getRequest().setAttribute("popup", popup);
			return "NuovaPartitaPopupOK";
		}

		return "NuovaPartitaOK";
	}

	public String executeCommandInserisciPartita(ActionContext context) {
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		try {
			db = this.getConnection(context);

			// Gestione partita
			PartitaUnivoca partita = new PartitaUnivoca(context);
			partita.setIdUtenteInserimento(user.getUserRecord().getId());
			partita.insert(db);
			context.getRequest().setAttribute("idPartita", String.valueOf(partita.getId()));

			// Gestione capi
			gestioneInserimentoCapi(context, db, partita);

			String popup = context.getRequest().getParameter("popup");
			if (popup != null && popup.equals("true")) {
				context.getRequest().setAttribute("idPartitaDaAggiungere", String.valueOf(partita.getId()));
				context.getRequest().setAttribute("orgId", String.valueOf(partita.getIdMacello()));
				return "popupCloseOK";
			}

			Organization org = new Organization(db, partita.getIdMacello());
			context.getRequest().setAttribute("OrgDetails", org);
			
			return executeCommandDettaglioPartita(context);

		}

		catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "";
	}

	private void gestioneInserimentoCapi(ActionContext context, Connection db, PartitaUnivoca partita)
			throws SQLException {
		// Lookup delle specie macellazioni
		LookupList specieList = new LookupList(db, "lookup_specie_mu");

		Iterator iter = specieList.iterator();
		while (iter.hasNext()) {
			LookupElement thisElement = (LookupElement) iter.next();
			int specieCode = thisElement.getCode();
			String specieNome = thisElement.getDescription();
			String numeroCapi = context.getRequest().getParameter("num" + specieNome);
			String numeroCapiDeceduti = context.getRequest().getParameter("num_deceduti" + specieNome);
			CapoUnivoco capo = new CapoUnivoco();
			// INFORMAZIONI GESTIONE PARTI
			capo.setNumeroParti(thisElement.getNrParti());

			capo.setIdPartita(partita.getId());
			capo.setSpecieCapo(specieCode);
			capo.setNumeroCapi(numeroCapi);
			capo.setIdStato(CapoUnivoco.idStatoDocumentale);

			// SE non e' bovino: inserisci subito
			if (capo.getSpecieCapo() != 1) {
				for (int i = 0; i < capo.getNumeroCapi(); i++)
					capo.insert(db);
				if (numeroCapiDeceduti != null && !("").equals(numeroCapiDeceduti)) {
					for (int k = 0; k < Integer.parseInt(numeroCapiDeceduti); k++) {
						capo.setFlagArrivatoDeceduto(true);
						capo.setIdStato(CapoUnivoco.idStatoArrivatoDeceduto);
						capo.insert(db);
					}
				}
			}
			// se e' bovino: estendi i dati
			else {
				int indice = 1;
				for (int i = 0; i < capo.getNumeroCapi(); i++) {

					while (context.getRequest().getParameter("matricola_" + indice) == null)
						indice++;
					CapoUnivoco capoBovino = new CapoUnivoco(context, db, indice);
					// INFORMAZIONI GESTIONE PARTI
					capoBovino.setNumeroParti(thisElement.getNrParti());

					capoBovino.setSpecieCapo(1);
					capoBovino.setIdPartita(partita.getId());
					if (capoBovino.isFlagArrivatoDeceduto())
						capoBovino.setIdStato(CapoUnivoco.idStatoArrivatoDeceduto);
					else						
						capoBovino.setIdStato(CapoUnivoco.idStatoDocumentale);
					
					capoBovino.insert(db);
					indice++;
				}
			}

		}

	}

	public String executeCommandDettaglioPartita(ActionContext context) {
		Connection db = null;
		String idPartita = "";

		idPartita = context.getRequest().getParameter("idPartita");
		if (idPartita == null || idPartita.equals("") || idPartita.equals("null"))
			idPartita = (String) context.getRequest().getAttribute("idPartita");

		try {
			int id = Integer.parseInt(idPartita);
			db = this.getConnection(context);
			PartitaUnivoca partita = new PartitaUnivoca(db, id);
			context.getRequest().setAttribute("partita", partita);

			caricaLookup(context, db);
			
			Organization org = new Organization(db, partita.getIdMacello());
			context.getRequest().setAttribute("OrgDetails", org);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		if (context.getRequest().getParameter("popup") != null
				&& context.getRequest().getParameter("popup").equals("true"))
			return "DettaglioPartitaPopupOK";

		return "DettaglioPartitaOK";
	}

	private void caricaLookup(ActionContext context, Connection db) throws SQLException {
		// Lookup delle specie macellazioni
		LookupList specieList = new LookupList(db, "lookup_specie_mu");
		context.getRequest().setAttribute("specieList", specieList);

		// Lookup delle specie bovini
		LookupList specieBovine = new LookupList(db, "m_lookup_specie");
		specieBovine.removeElementByLevel(20);
		specieBovine.removeElementByLevel(21);
		context.getRequest().setAttribute("specieBovine", specieBovine);

		// Lookup delle categorie bovine/bufaline
		LookupList categorieBovine = new LookupList(db, "m_lookup_specie_categorie_bovine");
		categorieBovine.addItem(-1, " -- SELEZIONA -- ");
		context.getRequest().setAttribute("categorieBovine", categorieBovine);
		LookupList categorieBufaline = new LookupList(db, "m_lookup_specie_categorie_bufaline");
		categorieBufaline.addItem(-1, " -- SELEZIONA -- ");
		context.getRequest().setAttribute("categorieBufaline", categorieBufaline);

		// Lookup delle razze bovine
		LookupList razzeBovine = new LookupList(db, "razze_bovini");
		razzeBovine.addItem(-1, " -- SELEZIONA -- ");
		context.getRequest().setAttribute("razzeBovine", razzeBovine);

		// Lookup delle categorie di rischio
		LookupList catRischio = new LookupList(db, "lookup_categoria_rischio_mu");
		context.getRequest().setAttribute("catRischio", catRischio);

		// Lookup della macellazione differita
		LookupList PianiRisanamento = new LookupList(db, "m_lookup_piani_risanamento");
		PianiRisanamento.addItem(-1, " -- SELEZIONA -- ");
		context.getRequest().setAttribute("PianiRisanamento", PianiRisanamento);

		// Lookup dei veterinari
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		HashMap<String, ArrayList<Contact>> listaUtentiAttiviV = ControlliUfficialiUtil
				.getUtentiAttiviperaslVeterinari(db, user.getSiteId());
		context.getRequest().setAttribute("listaVeterinari", listaUtentiAttiviV);

		// Lookup stato art 17
		LookupList statiArticolo17 = new LookupList(db, "mu_lookup_stati_articoli_17");
		context.getRequest().setAttribute("statiArticolo17", statiArticolo17);

		// Lookup stati
		LookupList statiCapi = new LookupList(db, "mu_lookup_stati");
		context.getRequest().setAttribute("statiCapi", statiCapi);

		// Lookup stati sedute
		LookupList statoSeduta = new LookupList(db, "mu_lookup_stati_seduta");
		context.getRequest().setAttribute("statoSeduta", statoSeduta);
		
		// Lookup esito visita am
		LookupList esitoVisitaAm = new LookupList(db, "mu_lookup_esito_visita_am");
		context.getRequest().setAttribute("esitoVisitaAm", "mu_lookup_esito_visita_am");
		

		Hashtable<String, String> lu = new Hashtable<String, String>();
		lu.put("Nazioni", "m_lookup_nazioni");
		lu.put("Regioni", "m_lookup_regioni");
		lu.put("Razze", "razze_bovini");
		lu.put("Specie", "m_lookup_specie");
		lu.put("CategorieBovine", "m_lookup_specie_categorie_bovine");
		lu.put("CategorieBufaline", "m_lookup_specie_categorie_bufaline");
		lu.put("PianiRisanamento", "m_lookup_piani_risanamento");
		lu.put("TipiMacellazione", "m_lookup_tipi_macellazione");
		lu.put("Patologie", "m_lookup_patologie");
		lu.put("PatologieOrgani", "m_lookup_patologie_organi");
		lu.put("TipiEsame", "m_lookup_tipi_esame");
		lu.put("Azioni", "m_lookup_azioni");
		lu.put("Stadi", "m_lookup_stadi");
		lu.put("Organi", "m_lookup_organi");
		lu.put("TipiAnalisi", "m_lookup_tipo_analisi");
		lu.put("TipiNonConformita", "m_lookup_tipi_non_conformita");
		lu.put("Veterinari", "veterinari_view");
		lu.put("EsitiVpm", "m_lookup_esiti_vpm");
		lu.put("Matrici", "m_lookup_matrici");
		lu.put("ASL", "lookup_site_id");
		lu.put("LuoghiVerifica", "m_lookup_luoghi_verifica");
		lu.put("Molecole", "m_lookup_molecole");
		lu.put("MotiviASL", "m_lookup_motivi_comunicazioni_asl");
		lu.put("ProvvedimentiVAM", "m_lookup_provvedimenti_vam");
		lu.put("look_ProvvedimentiCASL", "m_lookup_provvedimenti_casl");
		lu.put("BseList", "m_lookup_bse");
		lu.put("EsitiCampioni", "m_lookup_campioni_esiti");
		lu.put("MotiviCampioni", "m_lookup_campioni_motivi");
		lu.put("MolecolePNR", "m_lookup_molecole_pnr");
		lu.put("MolecoleBatteriologico", "m_lookup_molecole_batteriologico");
		lu.put("MolecoleParassitologico", "m_lookup_molecole_parassitologico");

		lu.put("lookup_lesione_milza", "m_lookup_lesione_milza");
		lu.put("lookup_lesione_cuore", "m_lookup_lesione_cuore");
		lu.put("lookup_lesione_visceri", "m_lookup_lesione_visceri");
		lu.put("lookup_lesione_polmoni", "m_lookup_lesione_polmoni");
		lu.put("lookup_lesione_fegato", "m_lookup_lesione_fegato");
		lu.put("lookup_lesione_rene", "m_lookup_lesione_rene");
		lu.put("lookup_lesione_mammella", "m_lookup_lesione_mammella");
		lu.put("lookup_lesione_stomaco", "m_lookup_lesione_stomaco");
		lu.put("lookup_lesione_intestino", "m_lookup_lesione_intestino");
		lu.put("lookup_lesione_apparato_genitale", "m_lookup_lesione_apparato_genitale");
		lu.put("lookup_lesione_osteomuscolari", "m_lookup_lesione_osteomuscolari");
		lu.put("lookup_lesione_generici", "m_lookup_lesione_generici");
		lu.put("lookup_lesione_altro", "m_lookup_lesione_altro");

		lu.put("MatriciTamponi", "lookup_tamponi");
		lu.put("AnalisiTamponi", "lookup_ricerca_tamponi_macelli");
		lu.put("PianiMonitoraggio", "lookup_piano_monitoraggio");
		lu.put("statiArticolo17", "mu_lookup_stati_articoli_17");
		lu.put("statiCapi", "mu_lookup_stati");
		lu.put("statoSeduta", "mu_lookup_stati_seduta");

		// lookup istopatologico
		lu.put("listaDestinatariRichiestaIstopatologico", "lookup_istopatologico_clinica_destinataria");
		lu.put("lookup_alimentazione", "lookup_alimentazione");
		lu.put("lookup_habitat", "lookup_habitat");
		lu.put("lookup_esame_istopatologico_tipo_diagnosi", "lookup_esame_istopatologico_tipo_diagnosi");
		lu.put("lookup_associazione_classificazione_tabella_lookup",
				"lookup_associazione_classificazione_tabella_lookup");
		lu.put("lookup_who_umana", "lookup_esame_istopatologico_who_umana");
		
		lu.put("esitoVisitaAm", "mu_lookup_esito_visita_am");

		Enumeration<String> e = lu.keys();
		Organization org = null;

		try {
			// db = this.getConnection(context);
			while (e.hasMoreElements()) {
				String key = e.nextElement();
				String value = lu.get(key);
				LookupList list = new LookupList(db, value);
				list.addItem(-1, " -- SELEZIONA -- ");

				if (("lookup_associazione_classificazione_tabella_lookup").equals(value)) {
					list.setJsEvent("onChange = \"javascript:caricaClassificazioneLesione(this);\"");
				}

				if (("lookup_esame_istopatologico_tipo_diagnosi").equals(value)) {
					list.setJsEvent("onChange = \"javascript:specificaEsito(this);\"");
				}

				context.getRequest().setAttribute(key, list);
			}

			LookupList vets = (LookupList) context.getRequest().getAttribute("Veterinari");
			if (this.getUserSiteId(context) > 0) {
				vets.removeItemfromLookup(db, "veterinari_view", " asl <> " + this.getUserSiteId(context));
			}

			// Creazione Lookup per tipologia non conformita' divisa in due
			// gruppi
			SystemStatus thisSystem = this.getSystemStatus(context);
			LookupList llTipoNonConformita = new LookupList();
			llTipoNonConformita.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
			llTipoNonConformita.addAll(new LookupList(db, "m_lookup_tipi_non_conformita_normale"));
			llTipoNonConformita.addGroup("Sospetta o accertata presenza malattie infettive");
			llTipoNonConformita.addAll(new LookupList(db, "m_lookup_tipi_non_conformita_malattie_infettive"));
			context.getRequest().setAttribute("TipiNonConformita_Gruppo", llTipoNonConformita);
		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		}
	}

	public String executeCommandRicercaSeduta(ActionContext context) {
		Connection db = null;

		try {
			db = this.getConnection(context);
			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "RicercaSedutaOK";
	}

	public String executeCommandRicercaPartita(ActionContext context) {
		Connection db = null;

		try {
			db = this.getConnection(context);
			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "RicercaPartitaOK";
	}

	public String executeCommandRicercaCapo(ActionContext context) {
		Connection db = null;

		try {
			db = this.getConnection(context);
			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "RicercaCapoOK";
	}

	public String executeCommandRicerca(ActionContext context) {
		Connection db = null;

		try {
			db = this.getConnection(context);
			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "RicercaOK";
	}

	public String executeCommandCercaCapo(ActionContext context) {
		String matricola = context.getRequest().getParameter("matricola");
		CapoUnivoco capo = new CapoUnivoco();
		capo.setMatricola(matricola);
		capo.setSpecieCapo(1); // forzo solo bovini

		Vector capoList = new Vector();
		Connection db = null;
		try {
			db = this.getConnection(context);
			capoList = capo.ricercaCapo(db);
			// Collections.sort(trasgrList);
			context.getRequest().setAttribute("listaCapi", capoList);

			caricaLookup(context, db);
			
			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "RicercaListaCapiOK";
	}

	public String executeCommandCercaPartita(ActionContext context) {
//		String mod4 = context.getRequest().getParameter("mod4");
//		String numero = context.getRequest().getParameter("numero");
//		PartitaUnivoca partita = new PartitaUnivoca();
//		partita.setMod4(mod4);
//		partita.setNumeroPartita(numero);
//
//		Vector partitaList = new Vector();
		Connection db = null;
		try {
			db = this.getConnection(context);
//			partitaList = partita.ricercaPartita(db);
//			// Collections.sort(trasgrList);
//			context.getRequest().setAttribute("listaPartite", partitaList);
//
//			caricaLookup(context, db);
			
			PartitaUnivocaList listaPartite = new PartitaUnivocaList();
			
			listaPartite.setOrgId( Integer.parseInt(context.getRequest().getParameter("orgId")));
			listaPartite.buildList(db);
			context.getRequest().setAttribute("listaPartite", listaPartite);
		
			
			
			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "RicercaListaPartiteOK";
	}

	public String executeCommandRicercaGlobale(ActionContext context) {
		String matricola = context.getRequest().getParameter("matricola");
		String numeroPartita = context.getRequest().getParameter("numero");
		// CapoUnivoco capo = new CapoUnivoco();
		// capo.setMatricola(matricola);
		// capo.setSpecieCapo(1); // forzo solo bovini

		// Vector capoList = new Vector();
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);
			
			if (matricola != null && !("").equals(matricola)) {

				CapoUnivocoList capoList = new CapoUnivocoList();
				capoList.setMatricola(matricola);
				capoList.setIdSpecie(1);
				capoList.setIdMacello(Integer.parseInt(context.getRequest().getParameter("idMacello")));
				capoList.buildList(db);
				context.getRequest().setAttribute("listaCapi", capoList);

				Articolo17List listaArticoli17 = new Articolo17List();
				listaArticoli17.setMatricola(matricola);
				listaArticoli17.setIdMacello(Integer.parseInt(context.getRequest().getParameter("idMacello")));
				listaArticoli17.buildList(db);
				context.getRequest().setAttribute("listaArticoli17", listaArticoli17);

				PartitaUnivocaList listaPartite = new PartitaUnivocaList();
				listaPartite.setMatricola(matricola);
				listaPartite.buildList(db);
				context.getRequest().setAttribute("listaPartite", listaPartite);

			} else if (numeroPartita != null && !("").equals(numeroPartita)) {

				PartitaUnivocaList listaPartite = new PartitaUnivocaList();
				listaPartite.setNumeroPartita(numeroPartita);
				listaPartite.buildList(db);

			}
			// Collections.sort(trasgrList);

			caricaLookup(context, db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "RicercaGlobaleOK";
	}

	public String executeCommandNuovaSeduta(ActionContext context) {
		String orgId = context.getRequest().getParameter("orgId");
		String idPartita = context.getRequest().getParameter("idPartita");
		context.getRequest().setAttribute("idPartitaDaAggiungere", idPartita);
		
		
		String idSeduta = context.getRequest().getParameter("idSeduta");
		
		if (idSeduta == null || ("").equals(idSeduta)){
			idSeduta = (String) context.getRequest().getAttribute("idSeduta");
		}
		
		


		Connection db = null;

		try {
			db = this.getConnection(context);
			
			if (idSeduta != null && !("").equals(idSeduta)){
				SedutaUnivoca seduta = new SedutaUnivoca(db, Integer.parseInt(idSeduta));
				context.getRequest().setAttribute("seduta", seduta);
			}
			
			
			Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", org);
			
			PartitaUnivoca partita = new PartitaUnivoca();
			partita.setIdMacello(orgId);
			Vector partitaList = new Vector();
			partitaList = partita.ricercaPartita(db);
			context.getRequest().setAttribute("listaPartite", partitaList);
			caricaLookup(context, db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return getReturn(context, "NuovaSeduta");

	//	return "NuovaSedutaOK";
	}

	public String executeCommandListCapiPartita(ActionContext context) {
		Connection db = null;
		String idPartita = "";

		idPartita = context.getRequest().getParameter("idPartita");
		if (idPartita == null || idPartita.equals("") || idPartita.equals("null"))
			idPartita = (String) context.getRequest().getAttribute("idPartita");

		try {
			int id = Integer.parseInt(idPartita);
			db = this.getConnection(context);
			PartitaUnivoca partita = new PartitaUnivoca(db, id);
			context.getRequest().setAttribute("partita", partita);
			caricaLookup(context, db);
			
			Organization org = new Organization(db, partita.getIdMacello());
			context.getRequest().setAttribute("OrgDetails", org);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "ListCapiPartitaOK";
	}

	public String executeCommandInserisciSeduta(ActionContext context) {
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		int idMacello = -1;
		int numeroCapi = 0;
		boolean closePopup = false;
		SedutaUnivoca seduta = new SedutaUnivoca();
		try {
			db = this.getConnection(context);
			String idMacelloS = context.getRequest().getParameter("idMacello");
			
			if (idMacelloS == null || ("").equals(idMacelloS)){
				idMacelloS = (String)context.getRequest().getAttribute("idMacello");
			}
			
			if (idMacelloS == null || ("").equals(idMacelloS)){
				Organization org = new Organization(db, Integer.parseInt(context.getRequest().getParameter("idMacello")));
				context.getRequest().setAttribute("OrgDetails", org);
			}
			
			
			String idSeduta =  context.getRequest().getParameter("idSeduta");
			if (idSeduta != null && !("").equals(idSeduta)){
				seduta = new SedutaUnivoca(db, Integer.parseInt(idSeduta));
				
			}

			if (seduta == null || seduta.getId() < 0){
			// Gestione partita
				seduta = new SedutaUnivoca(context);
				seduta.setIdUtenteInserimento(user.getUserRecord().getId());
				seduta.setIdStato(SedutaUnivoca.idStatoDaMacellare);
				seduta.insert(db);
			}else if (seduta.getId() > 0){
				numeroCapi = seduta.getNumeroCapiTotale();
				closePopup = true;
			}
			context.getRequest().setAttribute("idSeduta", String.valueOf(seduta.getId()));

			// Gestione capi
			numeroCapi += gestioneInserimentoCapiSeduta(context, db, seduta);

			seduta.setNumeroCapiTotale(numeroCapi);
			seduta.updateNumeroCapi(db);

			// return executeCommandDettaglioSeduta(context);
			
			if (closePopup)
				return getReturn(context, "popupClose");
			return executeCommandMacellaSeduta(context);

		}

		catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "";
	}

	private int gestioneInserimentoCapiSeduta(ActionContext context, Connection db, SedutaUnivoca seduta)
			throws SQLException {

		int numCapi = 0;
		String listaCapiSeduta = context.getRequest().getParameter("listaCapiSeduta");
		String[] lista = listaCapiSeduta.split(",");

		for (int i = 0; i < lista.length; i++) {
			int idCapo = Integer.parseInt(lista[i]);
			CapoUnivoco capo = new CapoUnivoco(db, idCapo);
			capo.aggiungiSeduta(db, seduta.getId());
			numCapi++;

		}
		return numCapi;
	}

	public String executeCommandDettaglioSeduta(ActionContext context) {
		Connection db = null;
		String idSeduta = "";

		idSeduta = context.getRequest().getParameter("idSeduta");
		if (idSeduta == null || idSeduta.equals("") || idSeduta.equals("null"))
			idSeduta = (String) context.getRequest().getAttribute("idSeduta");

		try {
			int id = Integer.parseInt(idSeduta);
			db = this.getConnection(context);
			SedutaUnivoca seduta = new SedutaUnivoca(db, id);
			context.getRequest().setAttribute("seduta", seduta);

			Organization org = new Organization(db, seduta.getIdMacello());
			context.getRequest().setAttribute("OrgDetails", org);
			caricaLookup(context, db);
			
			
			
			Articolo17List listaArticoli17 = new Articolo17List();
			listaArticoli17.setIdSeduta(seduta.getId());
			
			listaArticoli17.buildList(db);
			
			
			context.getRequest().setAttribute("listaArticoli17", listaArticoli17);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "DettaglioSedutaOK";
	}

	public String executeCommandMacellaSeduta(ActionContext context) {
		Connection db = null;
		String idSeduta = "";

		idSeduta = context.getRequest().getParameter("idSeduta");
		if (idSeduta == null || idSeduta.equals("") || idSeduta.equals("null") || ("-1").equals(idSeduta))
			idSeduta = (String) context.getRequest().getAttribute("idSeduta");

		try {
			int id = Integer.parseInt(idSeduta);
			db = this.getConnection(context);
			SedutaUnivoca seduta = new SedutaUnivoca(db, id);
			context.getRequest().setAttribute("seduta", seduta);
			// TODO AGGIORNAMENTO STATO SEDUTA A STATO INIZIALE
			caricaLookup(context, db);
			
			Organization org = new Organization(db, seduta.getIdMacello());
			context.getRequest().setAttribute("OrgDetails", org);

			PathWKF wkf = new PathWKF();
			wkf.setIdStato(1); // Controllo documentale
			ArrayList<Path> pathsMacellazione = wkf.getListaPaths(db);

			context.getRequest().setAttribute("wkf", pathsMacellazione);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "MacellaSedutaOK";
	}

	public String executeCommandOperazioniSeduta(ActionContext context) {
		Connection db = null;
		Path thisPath = null;

		try {
			db = this.getConnection(context);
			String idSeduta = context.getRequest().getParameter("idSeduta");
			context.getRequest().setAttribute("idSeduta", idSeduta);
			SedutaUnivoca seduta = new SedutaUnivoca(db, Integer.parseInt(idSeduta));
			context.getRequest().setAttribute("seduta", seduta);

			
			Organization org = new Organization(db, seduta.getIdMacello());
			context.getRequest().setAttribute("OrgDetails", org);

			ArrayList<CapoUnivoco> listaCapi = seduta.getListaCapi();
			ArrayList<CapoUnivoco> listaCapiSelezionati = new ArrayList<CapoUnivoco>();

			Iterator i = listaCapi.iterator();
			while (i.hasNext()) {
				CapoUnivoco thisCapo = (CapoUnivoco) i.next();

				if (context.getRequest().getParameter("capo_" + thisCapo.getId() + "_" + thisCapo.getSpecieCapo()) != null) {
					listaCapiSelezionati.add(thisCapo);

				}

			}

			context.getSession().setAttribute("listaCapiSelezionati", listaCapiSelezionati);

			/* GESTIONE COSTRUZIONE PAGINA */

			String idPath = (String) context.getRequest().getParameter("idPath");
			if (idPath != null && !("").equals(idPath)) {
				thisPath = new Path(Integer.valueOf(idPath), db, true, false);
			}

			context.getRequest().setAttribute("path", thisPath);

			caricaLookup(context, db);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "OperazioniSedutaOK";
	}

	public String executeCommandGestisciEsercenti(ActionContext context) {

		Connection db = null;
		String idSeduta = "";
		String idPartita = "";
		String idMacello = "";
		String idStato = "";
		int idMacelloInt = -1;
		int id = -1;
		int idLivello = -1;
		SedutaUnivoca seduta = null;

		try {

			db = this.getConnection(context);

			idSeduta = context.getRequest().getParameter("idSeduta");
			if (idSeduta == null || idSeduta.equals("") || idSeduta.equals("null"))
				idSeduta = (String) context.getRequest().getAttribute("idSeduta");
			
			idStato = context.getRequest().getParameter("idStato");
			if (idStato == null || idStato.equals("") || idStato.equals("null"))
				idStato = (String) context.getRequest().getAttribute("idStato");
			

//			if (idSeduta == null || ("").equals(idSeduta)) {
//				idPartita = (context.getRequest().getParameter("idPartita") != null) ? context.getRequest()
//						.getParameter("idPartita") : (String) context.getRequest().getAttribute("idPartita");
//
//				if ((idPartita == null || ("").equals(idPartita))) {
//					idMacello = (context.getRequest().getParameter("idMacello") != null) ? context.getRequest()
//							.getParameter("idMacello") : (String) context.getRequest().getAttribute("idMacello");
//					id = Integer.parseInt(idMacello);
//					idMacelloInt = id;
//					idLivello = 1;
//
//				} else {
//					id = Integer.parseInt(idPartita);
//					PartitaUnivoca partita = new PartitaUnivoca(db, id);
//					idMacelloInt = partita.getIdMacello();
//					idLivello = 2;
//				}
//			} else {
				id = Integer.parseInt(idSeduta);
				seduta = new SedutaUnivoca(db, id);
				idMacelloInt = seduta.getIdMacello();
				context.getRequest().setAttribute("seduta", seduta);
				idLivello = 3;
		//	}

			Organization org = new Organization(db, idMacelloInt);
			context.getRequest().setAttribute("OrgDetails", org);

			Articolo17List listaArticoli17 = new Articolo17List();

			listaArticoli17.setIdStato(idStato);
			listaArticoli17.setIdSeduta(seduta.getId());
			listaArticoli17.setIdMacello(idMacelloInt);

			listaArticoli17.buildList(db);

			context.getRequest().setAttribute("listaArticoli17", listaArticoli17);
			context.getRequest().setAttribute("id", String.valueOf(id));
			context.getRequest().setAttribute("idLivello", String.valueOf(idLivello));

			caricaLookup(context, db);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "GestisciEsercentiOK";

	}

	public String executeCommandAggiungiEsercente(ActionContext context) {

		Connection db = null;
		String id = "";
		String idLivello = "";
		CapoUnivocoList listaCapi = new CapoUnivocoList();
		String idMacello = "";

		// idSeduta = context.getRequest().getParameter("idSeduta");
		// if (idSeduta == null || idSeduta.equals("") ||
		// idSeduta.equals("null"))
		// idSeduta = (String) context.getRequest().getAttribute("idSeduta");
		//
		// if (idSeduta == null || ("").equals(idSeduta)) {
		// idPartita = (context.getRequest().getParameter("idPartita") != null)
		// ? context.getRequest().getParameter(
		// "idPartita") : (String)
		// context.getRequest().getAttribute("idPartita");
		// listaCapi.setIdPartita(idPartita);
		//
		// if ((idPartita == null || ("").equals(idPartita))) {
		// idMacello = (context.getRequest().getParameter("idMacello") != null)
		// ? context.getRequest()
		// .getParameter("idMacello") : (String)
		// context.getRequest().getAttribute("idMacello");
		// listaCapi.setIdMacello(idMacello);
		// }
		// }else{
		// listaCapi.setIdSeduta(idSeduta);
		// }

		try {
			// int id = Integer.parseInt(idSeduta);
			db = this.getConnection(context);
			// SedutaUnivoca seduta = new SedutaUnivoca(db, id);
			// context.getRequest().setAttribute("seduta", seduta);

			// if (idSeduta != null) {
			// listaCapi.setIdSeduta(idSeduta);
			// }

			// *TODO Aggiungere gestione per aggiunta esercente da partita. Devo
			// recuperare tutti i capi macellati e ancora non assegnati a
			// articolo 17 della partita

			id = context.getRequest().getParameter("id");
			idLivello = context.getRequest().getParameter("idLivello");
			idMacello = context.getRequest().getParameter("idMacello");
			
			SedutaUnivoca seduta = new SedutaUnivoca(db, Integer.parseInt(id));
			context.getRequest().setAttribute("seduta", seduta);

			switch (idLivello) {
			case "1":
				listaCapi.setIdMacello(id);
				break;
			case "2":
				listaCapi.setIdPartita(id);
				break;
			case "3":
				listaCapi.setIdSeduta(id);
				break;

			default:
				break;
			}
			Organization org = new Organization(db, Integer.valueOf(idMacello));
			context.getRequest().setAttribute("OrgDetails", org);

			listaCapi.setIdStato(CapoUnivoco.idStatoMacellato);
			listaCapi.setFlagBuildDettagliPartita(true);
			listaCapi.setFlagBuildDettagliSeduta(true);
			listaCapi.buildList(db);
			context.getRequest().setAttribute("listaCapi", listaCapi);
			context.getSession().setAttribute("listaCapi", listaCapi);

			caricaLookup(context, db);

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "AggiungiEsercenteOK";

	}

	public String executeCommandSalvaArticolo17(ActionContext context) {

		Connection db = null;
		String idSeduta = "";
		PartitaUnivoca partita = null;
		ArrayList<CapoUnivoco> listaCapi = new ArrayList<CapoUnivoco>();
		int k = 0;
		HashMap<Integer, Integer> capiDaAggiornareParti = new HashMap<Integer, Integer>();

		Articolo17 newArticolo17 = new Articolo17();

		String idEsercente = context.getRequest().getParameter("destinatario_1_id");
		String nomeEsercente = context.getRequest().getParameter("destinatario_1_nome");
		newArticolo17.setIdEsercente(idEsercente);

		boolean esistenzaArticolo17 = false;

		idSeduta = context.getRequest().getParameter("idSeduta");
		if (idSeduta == null || idSeduta.equals("") || idSeduta.equals("null"))
			idSeduta = (String) context.getRequest().getAttribute("idSeduta");

		try {
			// int id = Integer.parseInt(idSeduta);
			db = this.getConnection(context);

			// SedutaUnivoca seduta = new SedutaUnivoca(db, id);

			// Iterator i = seduta.getListaCapi().iterator();
			CapoUnivocoList listaCapiSessione = (CapoUnivocoList) context.getSession().getAttribute("listaCapi");
			Iterator i = listaCapiSessione.iterator();
			while (i.hasNext()) {
				CapoUnivoco thisCapo = (CapoUnivoco) i.next();
				for (int s = 0; s < thisCapo.getNumeroParti() - thisCapo.getNumeroPartiAssegnate(); s++) {
					if (context.getRequest().getParameter("capo_" + thisCapo.getId() + "_" + s) != null) {
						if (capiDaAggiornareParti.containsKey(thisCapo.getId()))
							capiDaAggiornareParti
									.put(thisCapo.getId(), capiDaAggiornareParti.get(thisCapo.getId()) + 1);
						else
							capiDaAggiornareParti.put(thisCapo.getId(), thisCapo.getNumeroPartiAssegnate() + 1);

						listaCapi.add(thisCapo);
						if (k == 0) {
							newArticolo17.setDataCreazione(thisCapo.getDataMacellazione());
							partita = new PartitaUnivoca(db, thisCapo.getIdPartita());
						}
						k++;
					}

				}

			}

			
		//	esistenzaArticolo17 = Articolo17.checkEsistenza(Integer.parseInt(idEsercente), partita.getIdMacello());
			esistenzaArticolo17 = Articolo17.checkEsistenza(Integer.parseInt(idEsercente), partita.getIdMacello(), Integer.parseInt(idSeduta));

			if (!esistenzaArticolo17)
				newArticolo17.setDescrizione(nomeEsercente);
			else
				newArticolo17.setDescrizione(nomeEsercente + "_1");

			newArticolo17.setIdMacello(partita.getIdMacello());
			newArticolo17.setListaCapi(listaCapi);
			newArticolo17.setEnteredby(getUserId(context));
			newArticolo17.setModifiedby(getUserId(context));
			newArticolo17.setIdSeduta(idSeduta);
			newArticolo17.setIdStato(Articolo17.idStatoAperto);

			newArticolo17.store(db, context);

			Iterator it = capiDaAggiornareParti.entrySet().iterator();

			while (it.hasNext()) {

				Map.Entry pair = (Map.Entry) it.next();
				String updateCapoParti = "update mu_capi set numero_parti_assegnate = ? where id = ?";

				PreparedStatement pst = db.prepareStatement(updateCapoParti);
				pst.setInt(1, (Integer) pair.getValue());
				pst.setInt(2, (Integer) pair.getKey());

				System.out.println(pair.getKey() + " = " + pair.getValue());
				pst.execute();

			}

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return executeCommandGestisciEsercenti(context);

	}

	public String executeCommandChiudiArticolo17(ActionContext context) {

		// **PER ORA DWR

		//
		// Connection db = null;
		// String idArticolo = "";
		//
		// idArticolo = context.getRequest().getParameter("id");
		// if (idArticolo == null || idArticolo.equals("") ||
		// idArticolo.equals("null"))
		// idArticolo = (String) context.getRequest().getAttribute("id");
		//
		// try {
		// // int id = Integer.parseInt(idSeduta);
		// db = this.getConnection(context);
		// if (idArticolo == null || idArticolo.equals("") ||
		// idArticolo.equals("null")) {
		// Articolo17 articolo = new Articolo17(Integer.valueOf(idArticolo),
		// db);
		//
		// articolo.close(db);
		//
		// }
		//
		// } catch (Exception e1) {
		// context.getRequest().setAttribute("Error", e1);
		// e1.printStackTrace();
		// } finally {
		// this.freeConnection(context, db);
		// }
		//
		return executeCommandGestisciEsercenti(context);

	}

	public String executeCommandSalvaMacellazione(ActionContext context) {

		//
		Connection db = null;
		int numCapiMacellati = 0;

		try {

			db = this.getConnection(context);

			ArrayList<CapoUnivoco> lista = (ArrayList<CapoUnivoco>) context.getSession().getAttribute(
					"listaCapiSelezionati");

			Macellazione dettagliBase = (Macellazione) context.getRequest().getAttribute("Macellazione");
			ArrayList<String> nonConformita = null;
			ArrayList<String> listaProvvedimenti = null;
			ArrayList<String> listaPatologieRilevate = null;
			ArrayList<String> listaEsito = null;

			String[] nonConformitaString = (context.getRequest().getParameterValues("listaNonConformita") != null) ? context
					.getRequest().getParameterValues("listaNonConformita") : null;
			if (nonConformitaString != null)
				nonConformita = new ArrayList(Arrays.asList(nonConformitaString));
			String[] listaProvvedimentiString = (context.getRequest().getParameterValues("listaProvvedimenti") != null) ? context
					.getRequest().getParameterValues("listaProvvedimenti") : null;
			if (listaProvvedimentiString != null)
				listaProvvedimenti = new ArrayList(Arrays.asList(listaProvvedimentiString));
			
			
			String[] patologieRilevateString = (context.getRequest().getParameterValues("listaPatologieRilevate") != null) ? context
					.getRequest().getParameterValues("listaPatologieRilevate") : null;
			if (patologieRilevateString != null)
				listaPatologieRilevate = new ArrayList(Arrays.asList(patologieRilevateString));
			
			String[] listaEsitiString = (context.getRequest().getParameterValues("listaEsito") != null) ? context
					.getRequest().getParameterValues("listaEsito") : null;
			if (listaEsitiString != null)
				listaEsito = new ArrayList(Arrays.asList(listaEsitiString));
			
			if (dettagliBase.getIdPathMacellazione() > 0) {
				Macellazione macellazione = (Macellazione) context.getRequest().getAttribute(
						org.aspcfs.modules.mu.base.ApplicationProperties.getProperty(String.valueOf(dettagliBase
								.getIdPathMacellazione())));

				VisitaAMSemplificata thisVisitaAM = (VisitaAMSemplificata) context.getRequest().getAttribute(
						"VisitaAMSemplificata");
				VisitaPMSemplificata thisVisitaPM = (VisitaPMSemplificata) context.getRequest().getAttribute(
						"VisitaPMSemplificata");
				Comunicazioni comunicazioni = (Comunicazioni) context.getRequest().getAttribute("Comunicazioni");
				MorteANM morteAnm = (MorteANM) context.getRequest().getAttribute("MorteANM");
				VisitaAM thisVisitaAMCompleta = (VisitaAM) context.getRequest().getAttribute("VisitaAM");
				VisitaPM thisVisitaPMCompleta = (VisitaPM) context.getRequest().getAttribute("VisitaPM");
				
			

				if (nonConformita != null && nonConformita.size() > 0) {
					comunicazioni.setListaNonConformita(nonConformita);
				}

				if (listaProvvedimenti != null && listaProvvedimenti.size() > 0) {
					comunicazioni.setListaProvvedimenti(listaProvvedimenti);
				}
				
				
//				if (listaEsito != null && listaEsito.size() > 0) {
//					thisVisitaPMCompleta.setListaEsito(listaEsito);
//				}
				
				
				
				if (listaPatologieRilevate != null && listaPatologieRilevate.size() > 0) {
					thisVisitaPMCompleta.setListaPatologieRilevate(listaPatologieRilevate);
				}

				macellazione.setIdUtenteInserimento(getUserId(context));
				macellazione.setIdUtenteModifica(getUserId(context));
				macellazione.setDataInserimento(new Timestamp(System.currentTimeMillis()));
				macellazione.setDataModifica(macellazione.getDataInserimento());

				Iterator i = lista.iterator();
				while (i.hasNext()) {
					
					

					CapoUnivoco thisCapo = (CapoUnivoco) i.next();
					
					ArrayList <Organo> listaOrgani = loadOrgani(context, thisCapo );
					
					if (listaOrgani != null && listaOrgani.size() > 0){
						thisVisitaPMCompleta.setListaOrgani(listaOrgani);
					}
					
					macellazione.setIdCapo(thisCapo.getId());
					macellazione.store(db, context, thisVisitaAM, thisVisitaPM, comunicazioni, morteAnm, thisVisitaAMCompleta, thisVisitaPMCompleta );

					PathWKF wkf = new PathWKF();
					wkf.setIdStato(thisCapo.getIdStato());
					wkf.setIdPath(dettagliBase.getIdPathMacellazione());

					int idProssimoStato = wkf.getProssimoStato(db);

					thisCapo.setIdStato(idProssimoStato);
					thisCapo.setIdPathMacellazione(macellazione.getIdPathMacellazione());

					thisCapo.updateStato(db);
					numCapiMacellati++;
				}

				SedutaUnivoca thisSeduta = new SedutaUnivoca(db, macellazione.getIdSeduta());
				
				
//				Articolo17List listaArticoli = new Articolo17List();
//				listaArticoli.setIdSeduta(thisSeduta.getId());
//				listaArticoli.buildList(db);

				thisSeduta.setNumeroCapiMacellati(thisSeduta.getNumeroCapiMacellati() + numCapiMacellati);
				if (thisSeduta.getNumeroCapiTotale() == thisSeduta.getNumeroCapiMacellati()) {
					thisSeduta.setIdStato(SedutaUnivoca.idStatoMacellato);
				} else if (numCapiMacellati > 0 || thisSeduta.getNumeroCapiMacellati() > 0) {
					thisSeduta.setIdStato(SedutaUnivoca.idStatoMacellazioneInCorso);
				}
				thisSeduta.updateNumeroCapiMacellati(db);

			}

		} catch (Exception e1) {
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("idSeduta", context.getRequest().getAttribute("idSeduta"));
		return executeCommandDettaglioSeduta(context);

	}

	public String executeCommandDettaglioMacellazione(ActionContext context){
	
	Connection db = null;
	CapoUnivoco thisCapo = null;
	Path thisPath = null; 

	try {

		db = this.getConnection(context);
		
		
		String idCapo = context.getRequest().getParameter("idCapo");
		
		if (idCapo != null && !("").equals(idCapo)){
			thisCapo = new CapoUnivoco(db, Integer.parseInt(idCapo));
						
		}
		
		context.getRequest().setAttribute("dettaglioCapo", thisCapo);
		
		SedutaUnivoca seduta = new SedutaUnivoca(db, thisCapo.getIdSeduta());
		context.getRequest().setAttribute("seduta", seduta);
		
		/* GESTIONE COSTRUZIONE PAGINA */

		
		if (thisCapo.getIdPathMacellazione() > 0) {
			thisPath = new Path(thisCapo.getIdPathMacellazione(), db, true, false);
		}

		context.getRequest().setAttribute("path", thisPath);
		
		
		caricaLookup(context, db);

	} catch (Exception e1) {
		context.getRequest().setAttribute("Error", e1);
		e1.printStackTrace();
	} finally {
		this.freeConnection(context, db);
	}

	
	
	
	return "DettaglioMacellazioneOK";

}
	
	
	private ArrayList<Organo> loadOrgani(ActionContext context, CapoUnivoco c )
	{
		ArrayList<Organo> ret = new ArrayList<Organo>();


		ArrayList<Parameter> patologia	= ParameterUtils.list( context.getRequest(), "lcso_patologia_" );
		//ArrayList<Parameter> stadio		= ParameterUtils.list( context.getRequest(), "lcso_stadio_" );
		ArrayList<Parameter> organo		= ParameterUtils.list( context.getRequest(), "lcso_organo_" );
		ArrayList<Parameter> ids		= ParameterUtils.list( context.getRequest(), "lcso_id_" );

		ArrayList<Parameter> lesione_milza				= ParameterUtils.list( context.getRequest(), "lesione_milza_" );
		ArrayList<Parameter> lesione_cuore				= ParameterUtils.list( context.getRequest(), "lesione_cuore_" );
		ArrayList<Parameter> lesione_polmoni			= ParameterUtils.list( context.getRequest(), "lesione_polmoni_" );
		ArrayList<Parameter> lesione_visceri			= ParameterUtils.list( context.getRequest(), "lesione_visceri_" );
		ArrayList<Parameter> lesione_fegato				= ParameterUtils.list( context.getRequest(), "lesione_fegato_" );
		ArrayList<Parameter> lesione_rene				= ParameterUtils.list( context.getRequest(), "lesione_rene_" );
		ArrayList<Parameter> lesione_mammella			= ParameterUtils.list( context.getRequest(), "lesione_mammella_" );
		ArrayList<Parameter> lesione_apparato_genitale	= ParameterUtils.list( context.getRequest(), "lesione_apparato_genitale_" );
		ArrayList<Parameter> lesione_stomaco			= ParameterUtils.list( context.getRequest(), "lesione_stomaco_" );
		ArrayList<Parameter> lesione_intestino			= ParameterUtils.list( context.getRequest(), "lesione_intestino_" );
		ArrayList<Parameter> lesione_osteomuscolari		= ParameterUtils.list( context.getRequest(), "lesione_osteomuscolari_" );

		ArrayList<Parameter> lesione_generici		= ParameterUtils.list( context.getRequest(), "lesione_generici_" );
		ArrayList<Parameter> lesione_altro		= ParameterUtils.list( context.getRequest(), "lesione_altro_" );

		ArrayList<Integer> idPatologie = null;

		for(int i = 0; i < organo.size(); i++)
		{
			Parameter tipo = organo.get( i );


			if( !"-1".equalsIgnoreCase( tipo.getValore() ) )
			{
				//				int max_id = -1;
				idPatologie = new ArrayList<Integer>();

				for( String val : lesione_milza.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_cuore.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_polmoni.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}
				
				for( String val : lesione_visceri.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_fegato.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_rene.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_mammella.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_apparato_genitale.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_stomaco.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_intestino.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_osteomuscolari.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_generici.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				for( String val : lesione_altro.get( i ).getValori() ){
					if (Integer.parseInt( val )!=-1) {
						idPatologie.add( Integer.parseInt(val) );
					}
				}

				Organo temp = null;
				for(int idPatologia : idPatologie){
					temp = new Organo();

					temp.setId_capo			( c.getId() );
					//temp.setLcso_patologia	( Integer.parseInt( patologia.get( i ).getValore() ) );
					temp.setId_patologia	( idPatologia );

					String patologiaAltro = context.getRequest().getParameter("lcso_patologiaaltro_" + i);
					if(patologiaAltro != null && !patologiaAltro.equals("")){
						temp.setPatologia_altro(patologiaAltro);
					}
					//giuseppe

					//temp.setLcso_stadio		( Integer.parseInt( stadio.get( i ).getValore() ) );
					temp.setId_organo		( Integer.parseInt( organo.get( i ).getValore() ) );
					temp.setId				( Integer.parseInt( ids.get( i ).getValore() ) );

					ret.add( temp );
				}

			}

		}

		return ret;
	}
	
}