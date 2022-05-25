/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.OperatoreList;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.utils.web.StateSelect;

import com.daffodilwoods.daffodildb.server.sql99.expression.booleanvalueexpression.length;
import com.darkhorseventures.framework.actions.ActionContext;

public class OperatoreAction extends CFSModule {

	public String executeCommandDefault(ActionContext context) {

		return executeCommandDashboard(context);
	}

	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			UserBean thisUser = (UserBean) context.getSession().getAttribute(
					"User");
			LineaProduttiva lp = (LineaProduttiva) context.getRequest()
					.getAttribute("LineaProduttivaScelta");
			context.getRequest().setAttribute("LineaProduttivaScelta", lp);
			
			Stabilimento stab =  (Stabilimento) context.getRequest()
			.getAttribute("stabilimento");

			if (context.getRequest().getAttribute("Exist") != null
					&& !("").equals(context.getRequest().getAttribute("Exist"))) {
				context.getRequest().setAttribute("Exist",
						(String) context.getRequest().getAttribute("Exist"));
			}

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,
					((UserBean) context.getSession().getAttribute("User"))
							.getSiteId(), 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");

			LineaProduttivaList listaLineeProduttive = new LineaProduttivaList();
			listaLineeProduttive.setIdMacrocategoria(1);
			listaLineeProduttive.buildList(db);
			context.getRequest().setAttribute("ListaLineeProduttive",
					listaLineeProduttive);

			context.getRequest().setAttribute("ComuniList", comuniList);
			Operatore newOperatore = (Operatore) context.getFormBean();
			ApplicationPrefs prefs = (ApplicationPrefs) context
					.getServletContext().getAttribute("applicationPrefs");
			context.getRequest().setAttribute("Operatore", newOperatore);

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			context.getRequest().setAttribute(
					"TipologiaSoggetto",
					(String) context.getRequest().getParameter(
							"tipologiaSoggetto"));

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus",
				this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "Add");
	}

	public String executeCommandDashboard(ActionContext context) {
		if (!hasPermission(context, "accounts-dashboard-view")) {
			if (!hasPermission(context, "accounts-accounts-view")) {
				return ("PermissionError");
			}

			// Bypass dashboard and search form for portal users
			if (isPortalUser(context)) {
				// return (executeCommandSearch(context));
			}
			return (executeCommandSearchForm(context));
		}

		addModuleBean(context, "Dashboard", "Dashboard");
		CalendarBean calendarInfo = (CalendarBean) context.getSession()
				.getAttribute("AccountsCalendarInfo");
		if (calendarInfo == null) {
			calendarInfo = new CalendarBean(this.getUser(context,
					this.getUserId(context)).getLocale());
			calendarInfo
					.addAlertType(
							"Accounts",
							"org.aspcfs.modules.accounts.base.AccountsListScheduledActions",
							"Accounts");
			calendarInfo.setCalendarDetailsView("Accounts");
			context.getSession().setAttribute("AccountsCalendarInfo",
					calendarInfo);
		}

		UserBean thisUser = (UserBean) context.getSession()
				.getAttribute("User");

		// this is how we get the multiple-level heirarchy...recursive function.
		User thisRec = thisUser.getUserRecord();

		UserList shortChildList = thisRec.getShortChildList();
		UserList newUserList = thisRec.getFullChildList(shortChildList,
				new UserList());

		newUserList.setMyId(getUserId(context));
		newUserList.setMyValue(thisUser.getUserRecord().getContact()
				.getNameLastFirst());
		newUserList.setIncludeMe(true);

		newUserList
				.setJsEvent("onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
		HtmlSelect userListSelect = newUserList.getHtmlSelectObj("userId",
				getUserId(context));
		userListSelect.addAttribute("id", "userId");
		context.getRequest().setAttribute("Return", "Accounts");
		context.getRequest().setAttribute("NewUserList", userListSelect);
		return ("DashboardOK");
	}

	public String executeCommandInsert(ActionContext context)
			throws SQLException {

		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Operatore insertedOp = null;
		// Integer orgId = null;
		Operatore newOperatore = (Operatore) context.getFormBean();
		

		if (("false").equals((String) context.getParameter("doContinueLp"))) { // Ho
			// solo
			// scelto
			// la
			// linea
			// produttiva,
			// devo
			// tornare
			// alla
			// pagina
			// jsp
			LineaProduttiva lp = new LineaProduttiva();
			lp.setIdRelazioneAttivita(Integer.parseInt(context
					.getParameter("idLineaProduttiva"))); // Setto l'id
			
			//Controllo se ho scelto un operatore fuori regione
			Stabilimento stab = new Stabilimento();
			if (context.getRequest().getParameter("inregione") != null && ("no").equals(context.getRequest().getParameter("inregione")))
				stab.setFlagFuoriRegione(true);
			// lineaproduttiva
			// scelto
			context.getRequest().setAttribute("LineaProduttivaScelta", lp);
			context.getRequest().setAttribute("stabilimento", stab);
			return executeCommandAdd(context);
		}

		newOperatore.setEnteredBy(getUserId(context));
		newOperatore.setModifiedBy(getUserId(context));
		newOperatore.setOwner(getUserId(context));

		try {

			db = this.getConnection(context);

			boolean exist = newOperatore
					.checkEsistenzaLineaProduttiva(db, Integer.parseInt(context
							.getParameter("idLineaProduttiva")));

			if (exist) {

				LineaProduttiva lp = new LineaProduttiva();
				lp.setIdRelazioneAttivita(Integer.parseInt(context
						.getParameter("idLineaProduttiva"))); // Setto l'id
				// lineaproduttiva
				// scelto

				context.getRequest().setAttribute("LineaProduttivaScelta", lp);
				context.getRequest().setAttribute(
						"Exist",
						"Esiste già un proprietario di tipo "
								+ lp.getDescrizioneAttivita(db)
								+ " con codice fiscale/p.iva = "
								+ newOperatore.getPartitaIva());
				return executeCommandAdd(context);
			}

			SoggettoFisico soggettoAdded = null;

			if (context.getRequest().getParameter("codFiscaleSoggetto") != null
					&& !"".equalsIgnoreCase(context.getRequest().getParameter(
							"codFiscaleSoggetto"))
					&& new Integer(context.getRequest().getParameter(
							"codFiscaleSoggetto")).intValue() > 0) {
				soggettoAdded = new SoggettoFisico(context.getRequest());
				UserBean user = (UserBean) context.getRequest().getSession()
						.getAttribute("User");
				soggettoAdded.setModifiedBy(user.getUserId());
				soggettoAdded.setIpModifiedBy(user.getUserRecord().getIp());
			} else {
				soggettoAdded = new SoggettoFisico(context.getRequest(), db);

			}
			newOperatore.setRappLegale(soggettoAdded);
			Indirizzo indirizzoAdded = null;
			if (new Integer(context.getRequest().getParameter("via"))
					.intValue() > 0) {
				indirizzoAdded = new Indirizzo(db, new Integer(context
						.getRequest().getParameter("via")).intValue());
				indirizzoAdded.setTipologiaSede(1); // Legale
			} else {
				indirizzoAdded = new Indirizzo(context.getRequest(), db);
				indirizzoAdded.setTipologiaSede(1);

			}

			newOperatore.getListaSediOperatore().add(indirizzoAdded);

			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals(
							"")
					&& context.getRequest().getParameter("doContinue").equals(
							"false")) {
				return executeCommandAdd(context);
			}

			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			String ip = user.getUserRecord().getIp();

			isValid = this.validateObject(context, db, newOperatore);

			if (isValid) {
				recordInserted = newOperatore.insert(db);

			}
			if (recordInserted) {
				
				String inRegione = (String) context.getRequest().getParameter("inregione");
				
				

				String nameContext = context.getParameter("context");
				if (nameContext.equalsIgnoreCase("bdu")) {
					int lineaProduttiva = Integer.parseInt(context
							.getParameter("idLineaProduttiva"));
					if (lineaProduttiva == Operatore.BDU_PRIVATO
							|| lineaProduttiva == Operatore.BDU_SINDACO
							|| lineaProduttiva == Operatore.BDU_SINDACO_FR) {
						Stabilimento newStabilimento = new Stabilimento();
						if (inRegione != null){
							newStabilimento.setFlagFuoriRegione(inRegione);
						}
						newStabilimento.setRappLegale(soggettoAdded);
						indirizzoAdded.setTipologiaSede(5); // Operativa
						newStabilimento.setSedeOperativa(indirizzoAdded);

						Object[] asl;
						if (!(lineaProduttiva == Operatore.BDU_SINDACO_FR) && !newStabilimento.isFlagFuoriRegione()) //Se nn sto considerando un sindaco fuori regione
																														//e nemmeno un proprietario di tipo fuori regione				
							asl = DwrUtil.getValoriAsl(indirizzoAdded
									.getComune());
						else
							asl = null;

						if (asl != null && asl.length > 0) {
							
							Object[] aslVal = (Object[]) asl[0];
							if (aslVal != null && aslVal.length > 0)
								newStabilimento.setIdAsl((Integer) aslVal[0]);
							
						} else if (lineaProduttiva == Operatore.BDU_SINDACO_FR) {
							newStabilimento.setIdAsl(-1);
						}else if (newStabilimento.isFlagFuoriRegione()){
							newStabilimento.setIdAsl(user.getSiteId());
						}

						LineaProduttiva lp = null;
						if (!(lineaProduttiva == Operatore.BDU_SINDACO_FR) && (!newStabilimento.isFlagFuoriRegione())) { //PRENDO L?ASL DUE VOLTE? CONTROLLARE CON GIUSEPPE
							String sql = "select id,nome,asl.description,asl.code from comuni1 "
									+ "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
									+ " where 1=1 ";
							PreparedStatement pst = db.prepareStatement(sql
									+ " and comuni1.id = ? ");
							pst.setInt(1, indirizzoAdded.getComune());
							ResultSet rs = pst.executeQuery();
							if (rs.next())
								newStabilimento.setIdAsl(rs.getInt("code"));
						}

						lp = new LineaProduttiva(db, lineaProduttiva);
						newStabilimento.setIdOperatore(newOperatore
								.getIdOperatore());

						newStabilimento.insert(db, false);
						lp = newStabilimento.aggiungiLineaProduttiva(db, lp);
						newStabilimento.getListaLineeProduttive().add(lp);
						context.getRequest().setAttribute(
								"opId",
								new Integer(((LineaProduttiva) newStabilimento
										.getListaLineeProduttive().get(0))
										.getId()).toString());
					} else {
						
						//Devo aggiungere ancora sede operativa e responsabile di stabilimento
						StabilimentoAction actionStabilimento = new StabilimentoAction();
						LineaProduttiva lp = null;
						Stabilimento newStabilimento = new Stabilimento();
						lp = new LineaProduttiva(db, lineaProduttiva);
						newStabilimento.setIdOperatore(newOperatore
								.getIdOperatore());
						newStabilimento.getListaLineeProduttive().add(lp);
						context.getRequest().setAttribute("Stabilimento",
								newStabilimento);
						context.getRequest().setAttribute(
								"tipologiaSoggetto",
								(String) context.getRequest().getParameter(
										"tipologiaSoggetto"));
						return actionStabilimento.executeCommandAdd(context);

					}
				}

			}

			context.getSession().removeAttribute("Operatore");

			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD",
					"Accounts.do?command=Details");
			context.getRequest().setAttribute("org_cod",
					"&orgId=" + newOperatore.getIdOperatore());

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null) {

				return ("ClosePopupOK");
			}
			if (target != null && "add_contact".equals(target)) {
				return ("InsertAndAddContactOK");
			} else if (context.getRequest().getParameter("iterId") != null
					&& !context.getRequest().getParameter("iterId")
							.equals("-1")) {
				// gestione iter
				return ("InsertIter");
			}
		}
	//	System.out.println("Metodo di operatore centralizzato");

		return (executeCommandDetails(context));

		// return ("InsertOK");

	}

	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "accounts-accounts-view"))) {
			return ("PermissionError");
		}

		// Bypass search form for portal users
		if (isPortalUser(context)) {
			// return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("SiteList", siteList);
			context.getRequest().setAttribute("TipologiaSoggetto",
					context.getRequest().getParameter("tipologiaSoggetto"));

			if (context.getRequest().getParameter("tipoRegistrazione") != null) {
				context.getRequest().setAttribute("tipoRegistrazione",
						context.getRequest().getParameter("tipoRegistrazione"));
			} else {
				context.getRequest().setAttribute("tipoRegistrazione", "-1");
			}

			if (context.getRequest().getParameter("idLineaProduttiva1") != null) {
				LineaProduttiva lp = new LineaProduttiva(db,
						new Integer(context.getRequest().getParameter(
								"idLineaProduttiva1")));
				context.getRequest().setAttribute("LineaProduttiva", lp);
			}

			org.aspcfs.modules.opu.base.ComuniAnagrafica c = new org.aspcfs.modules.opu.base.ComuniAnagrafica();
			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			ArrayList<org.aspcfs.modules.opu.base.ComuniAnagrafica> listaComuni = c
					.buildList(db, user.getSiteId(),
							ComuniAnagrafica.IN_REGIONE);

			LookupList comuniList = new LookupList(listaComuni, -1);

			comuniList.addItem(-1, "Seleziona comune");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList stageList = new LookupList(db, "lookup_account_stage");
			stageList.addItem(-1, systemStatus.getLabel("accounts.any"));
			context.getRequest().setAttribute("StageList", stageList);

			// reset the offset and current letter of the paged list in order to
			// make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context,
					"SearchOrgListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);
			if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null
					&& !"-1".equals(orgListInfo
							.getSearchOptionValue("searchcodeContactCountry"))) {
				StateSelect stateSelect = new StateSelect(
						systemStatus,
						orgListInfo
								.getSearchOptionValue("searchcodeContactCountry"));
				if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
					HashMap map = new HashMap();
					map
							.put(
									(String) orgListInfo
											.getSearchOptionValue("searchcodeContactCountry"),
									(String) orgListInfo
											.getSearchOptionValue("searchContactOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("ContactStateSelect",
						stateSelect);
			}
			if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null
					&& !"-1".equals(orgListInfo
							.getSearchOptionValue("searchcodeAccountCountry"))) {
				StateSelect stateSelect = new StateSelect(
						systemStatus,
						orgListInfo
								.getSearchOptionValue("searchcodeAccountCountry"));
				if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
					HashMap map = new HashMap();
					map
							.put(
									(String) orgListInfo
											.getSearchOptionValue("searchcodeAccountCountry"),
									(String) orgListInfo
											.getSearchOptionValue("searchAccountOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("AccountStateSelect",
						stateSelect);
			}

			LookupList lineeProduttive = new LookupList(db,
					"opu_lookup_attivita_linee_produttive_aggregazioni");
			context.getRequest().setAttribute("LineeProduttiveList",
					lineeProduttive);
			// buildFormElements(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");

		return getReturn(context, "Search");

	}

	public void buildFormElements(ActionContext context, Connection db)
			throws SQLException {
		String index = null;
		if (context.getRequest().getParameter("index") != null) {
			index = context.getRequest().getParameter("index");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);

		// inserito da Francesco
		CustomLookupList codiceIstatList = new CustomLookupList();
		codiceIstatList.tableName = "lookup_codistat";
		codiceIstatList.addField("*");

		// codiceIstatList.addField("description");
		// codiceIstatList.addField("short_description");
		// codiceIstatList.addField("default_item");
		// codiceIstatList.addField("level");
		// codiceIstatList.addField("enabled");
		codiceIstatList.buildList(db);
		codiceIstatList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("CodiceIstatList", codiceIstatList);

		// inserito da Francesco
		LookupList categoriaRischioList = new LookupList(db,
				"lookup_org_catrischio");
		categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("OrgCategoriaRischioList",
				categoriaRischioList);

		context.getRequest().setAttribute("TipologiaSoggetto",
				context.getRequest().getParameter("tipologiaSoggetto"));

		LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
		TipoLocale.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("TipoLocale", TipoLocale);

		LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
		TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

		LookupList salutationList = new LookupList(db, "lookup_title");
		salutationList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("SalutationList", salutationList);

		LookupList segmentList = new LookupList(db, "lookup_segments");
		segmentList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("SegmentList", segmentList);

		LookupList industrySelect = new LookupList(db, "lookup_industry");
		industrySelect.addItem(0, systemStatus
				.getLabel("calendar.none.4dashes"));
		context.getRequest().setAttribute("IndustryList", industrySelect);

		SICCodeList sicCodeList = new SICCodeList(db);
		sicCodeList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("SICCodeList", sicCodeList);

		LookupList sourceList = new LookupList(db, "lookup_contact_source");
		sourceList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("SourceList", sourceList);

		LookupList ratingList = new LookupList(db, "lookup_contact_rating");
		ratingList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("RatingList", ratingList);

		LookupList accountTypeList1 = new LookupList(db, "lookup_account_types");
		accountTypeList1.setSelectSize(4);
		accountTypeList1.setMultiple(true);
		context.getRequest().setAttribute("AccountTypeList", accountTypeList1);

		LookupList accountTypeList2 = new LookupList(db, "lookup_account_types");
		accountTypeList2
				.addItem(-1, systemStatus.getLabel("accounts.allTypes"));
		context.getRequest().setAttribute("TypeSelect", accountTypeList2);

		LookupList accountSizeList = new LookupList(db, "lookup_account_size");
		accountSizeList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("AccountSizeList", accountSizeList);

		if (index == null || (index != null && Integer.parseInt(index) == 0)) {
			LookupList phoneTypeList = new LookupList(db,
					"lookup_orgphone_types");
			context.getRequest()
					.setAttribute("OrgPhoneTypeList", phoneTypeList);

			LookupList addrTypeList = new LookupList(db,
					"lookup_orgaddress_types");
			context.getRequest().setAttribute("OrgAddressTypeList",
					addrTypeList);

			LookupList emailTypeList = new LookupList(db,
					"lookup_orgemail_types");
			context.getRequest()
					.setAttribute("OrgEmailTypeList", emailTypeList);

		} else {
			LookupList phoneTypeList = new LookupList(db,
					"lookup_contactphone_types");
			context.getRequest()
					.setAttribute("OrgPhoneTypeList", phoneTypeList);

			LookupList addrTypeList = new LookupList(db,
					"lookup_contactaddress_types");
			context.getRequest().setAttribute("OrgAddressTypeList",
					addrTypeList);

			LookupList emailTypeList = new LookupList(db,
					"lookup_contactemail_types");
			context.getRequest()
					.setAttribute("OrgEmailTypeList", emailTypeList);

		}
		// Make the StateSelect and CountrySelect drop down menus available in
		// the request.
		// This needs to be done here to provide the SystemStatus to the
		// constructors, otherwise translation is not possible
		StateSelect stateSelect = (StateSelect) context.getRequest()
				.getAttribute("StateSelect");
		if (stateSelect == null) {
			ApplicationPrefs prefs = (ApplicationPrefs) context
					.getServletContext().getAttribute("applicationPrefs");
			stateSelect = new StateSelect(systemStatus, prefs
					.get("SYSTEM.COUNTRY"));
		}
		CountrySelect countrySelect = new CountrySelect(systemStatus);

		context.getRequest().setAttribute("StateSelect", stateSelect);
		context.getRequest().setAttribute("CountrySelect", countrySelect);
	}

	/*
	 * public String executeCommandSearch(ActionContext context) { if
	 * (!hasPermission(context, "accounts-accounts-view")) { return
	 * ("PermissionError"); }
	 * 
	 * UserBean user = (UserBean) context.getSession().getAttribute("User");
	 * OperatoreList operatoreList = new OperatoreList();
	 * 
	 * String source = (String) context.getRequest().getParameter("source");
	 * 
	 * String partitaIva = (String) context.getRequest().getParameter(
	 * "PartitaIva"); String codiceF = (String)
	 * context.getRequest().getParameter( "CodiceFiscale"); String
	 * ragione_sociale = (String) context.getRequest().getParameter(
	 * "RagioneSociale");
	 * 
	 * 
	 * 
	 * //organizationList.setTipologia(1);
	 * 
	 * if (!"".equals(partitaIva) && partitaIva != null) {
	 * operatoreList.setPartIva(partitaIva); } if (!"".equals(codiceF) &&
	 * codiceF != null) { operatoreList.setCodiceFiscale(codiceF); }
	 * 
	 * if (ragione_sociale != null && !"".equals(ragione_sociale) ) {
	 * operatoreList.setRagioneSociale(ragione_sociale); }
	 * 
	 * 
	 * addModuleBean(context, "View Accounts", "Search Results");
	 * 
	 * // Prepare pagedListInfo PagedListInfo searchListInfo =
	 * this.getPagedListInfo(context, "SearchOrgListInfo");
	 * searchListInfo.setLink("OperatoreAction.do?command=Search");
	 * searchListInfo.setListView("all"); SystemStatus systemStatus =
	 * this.getSystemStatus(context); // Need to reset any sub PagedListInfos
	 * since this is a new account this.resetPagedListInfo(context); Connection
	 * db = null; try { db = this.getConnection(context);
	 * 
	 * if ((searchListInfo.getListView() == null || ""
	 * .equals(searchListInfo.getListView())) && !"searchForm".equals(source)) {
	 * return "ListOK"; }
	 * 
	 * 
	 * 
	 * 
	 * // Display list of accounts if user chooses not to list contacts if
	 * (!"true".equals(searchListInfo .getCriteriaValue("searchContacts"))) { if
	 * ("contacts".equals(context.getSession().getAttribute(
	 * "previousSearchType"))) { this.deletePagedListInfo(context,
	 * "SearchOrgListInfo"); searchListInfo = this.getPagedListInfo(context,
	 * "SearchOrgListInfo");
	 * searchListInfo.setLink("OperatoreAction.do?command=Search"); } // Build
	 * the organization list operatoreList.setPagedListInfo(searchListInfo);
	 * operatoreList.setMinerOnly(false); operatoreList.setTypeId(searchListInfo
	 * .getFilterKey("listFilter1"));
	 * 
	 * operatoreList.setStageId(searchListInfo
	 * .getCriteriaValue("searchcodeStageId"));
	 * 
	 * 
	 * 
	 * searchListInfo.setSearchCriteria(operatoreList, context);
	 * 
	 * if (context.getRequest().getParameter("doContinue") != null &&
	 * !context.getRequest().getParameter("doContinue").equals("") &&
	 * context.getRequest().getParameter("doContinue").equals("false")){ return
	 * executeCommandDashboard(context); } // fetching criterea for account
	 * source (my accounts or all // accounts) if
	 * ("my".equals(searchListInfo.getListView())) {
	 * //operatoreList.setOwnerId(this.getUserId(context)); }
	 * 
	 * // fetching criterea for account status (active, disabled or // any) int
	 * enabled = searchListInfo.getFilterKey("listFilter2"); //
	 * organizationList.setCessato(enabled); //
	 * operatoreList.setIncludeEnabled(enabled); // If the user is a portal
	 * user, fetching only the // the organization that he access to // (i.e.,
	 * the organization for which he is an account contact / if
	 * (isPortalUser(context)) {
	 * organizationList.setOrgSiteId(this.getUserSiteId(context));
	 * organizationList.setIncludeOrganizationWithoutSite(false);
	 * organizationList .setOrgId(getPortalUserPermittedOrgId(context)); }
	 */

	/*
	 * operatoreList.buildList(db);
	 * 
	 * context.getRequest().setAttribute("OrgList", operatoreList);
	 * 
	 * 
	 * LookupList siteList = new LookupList(db, "lookup_site_id");
	 * context.getRequest().setAttribute("SiteIdList", siteList);
	 * 
	 * 
	 * ComuniAnagrafica c = new ComuniAnagrafica(); ArrayList<ComuniAnagrafica>
	 * listaComuni = c.buildList_all(db,
	 * ((UserBean)context.getSession().getAttribute("User")).getSiteId());
	 * LookupList comuniList = new LookupList(listaComuni, -1);
	 * 
	 * 
	 * comuniList.addItem(-1, "");
	 * context.getRequest().setAttribute("ComuniList", comuniList);
	 * 
	 * 
	 * context.getSession().setAttribute("previousSearchType", "accounts");
	 * 
	 * return "ListOK"; } else { if
	 * ("accounts".equals(context.getSession().getAttribute(
	 * "previousSearchType"))) { this.deletePagedListInfo(context,
	 * "SearchOrgListInfo"); searchListInfo = this.getPagedListInfo(context,
	 * "SearchOrgListInfo");
	 * searchListInfo.setLink("OperatoreAction.do?command=Search"); }
	 * 
	 * 
	 * 
	 * context.getSession().setAttribute("previousSearchType", "contacts");
	 * return ("ContactListOK"); } } catch (Exception e) { e.printStackTrace();
	 * // Go through the SystemError process
	 * context.getRequest().setAttribute("Error", e); return ("SystemError"); }
	 * finally { this.freeConnection(context, db); } }
	 */

	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		OperatoreList operatoreList = new OperatoreList();

		String source = (String) context.getRequest().getParameter("source");

		String partitaIva = (String) context.getRequest().getParameter(
				"PartitaIva");
		String codiceF = (String) context.getRequest().getParameter(
				"CodiceFiscale");
		String ragione_sociale = (String) context.getRequest().getParameter(
				"RagioneSociale");
		context.getRequest().setAttribute("TipologiaSoggetto",
				context.getRequest().getParameter("tipologiaSoggetto"));

		// organizationList.setTipologia(1);

		if (!"".equals(partitaIva) && partitaIva != null) {
			operatoreList.setPartIva(partitaIva);
		}
		if (!"".equals(codiceF) && codiceF != null) {
			operatoreList.setCodiceFiscale(codiceF);
		}

		if (ragione_sociale != null && !"".equals(ragione_sociale)) {
			operatoreList.setRagioneSociale(ragione_sociale);
		}

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchOrgListInfo");
		searchListInfo
				.setLink("OperatoreAction.do?command=Search&tipologiaSoggetto="
						+ context.getRequest()
								.getParameter("tipologiaSoggetto") + RequestUtils.addLinkParams(
						                  context.getRequest(), "popup|popupType|actionId"));
		searchListInfo.setListView("all");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			if ((searchListInfo.getListView() == null || ""
					.equals(searchListInfo.getListView()))
					&& !"searchForm".equals(source)) {
				return "ListOK";
			}

			// Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo
					.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute(
						"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context,
							"SearchOrgListInfo");
					searchListInfo
							.setLink("OperatoreAction.do?command=Search&tipologiaSoggetto="
									+ context.getRequest().getParameter(
											"tipologiaSoggetto") +  RequestUtils.addLinkParams(
									                  context.getRequest(), "popup|popupType|actionId"));
				}
				// Build the organization list
				operatoreList.setPagedListInfo(searchListInfo);
				operatoreList.setMinerOnly(false);
				operatoreList.setTypeId(searchListInfo
						.getFilterKey("listFilter1"));

				operatoreList.setStageId(searchListInfo
						.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(operatoreList, context);

				if (context.getRequest().getParameter("doContinue") != null
						&& !context.getRequest().getParameter("doContinue")
								.equals("")
						&& context.getRequest().getParameter("doContinue")
								.equals("false")) {

					return executeCommandSearchForm(context);
				}
				// fetching criterea for account source (my accounts or all
				// accounts)
				if ("my".equals(searchListInfo.getListView())) {
					// operatoreList.setOwnerId(this.getUserId(context));
				}

				// fetching criterea for account status (active, disabled or
				// any)
				int enabled = searchListInfo.getFilterKey("listFilter2");
				// organizationList.setCessato(enabled);
				// operatoreList.setIncludeEnabled(enabled);
				// If the user is a portal user, fetching only the
				// the organization that he access to
				// (i.e., the organization for which he is an account contact
				/*
				 * if (isPortalUser(context)) {
				 * organizationList.setOrgSiteId(this.getUserSiteId(context));
				 * organizationList.setIncludeOrganizationWithoutSite(false);
				 * organizationList
				 * .setOrgId(getPortalUserPermittedOrgId(context)); }
				 */

				operatoreList.buildList(db);

				context.getRequest().setAttribute("OrgList", operatoreList);
				
				if (context.getRequest().getParameter("tipoRegistrazione") != null) {
					context.getRequest().setAttribute("tipoRegistrazione",
							context.getRequest().getParameter("tipoRegistrazione"));
				} else {
					context.getRequest().setAttribute("tipoRegistrazione", "-1");
				}

				LookupList siteList = new LookupList(db, "lookup_site_id");
				context.getRequest().setAttribute("SiteIdList", siteList);
				
				LookupList tipologiaList = new LookupList(db, "opu_lookup_attivita_linee_produttive_aggregazioni");
				//registrazioniList.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("tipologiaList",
						tipologiaList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,
						((UserBean) context.getSession().getAttribute("User"))
								.getSiteId());
				/*
				 * LookupList comuniList = new LookupList(listaComuni, -1);
				 * 
				 * 
				 * comuniList.addItem(-1, "");
				 * context.getRequest().setAttribute("ComuniList", comuniList);
				 */

				context.getSession().setAttribute("previousSearchType",
						"accounts");

				if (context.getRequest().getParameter("tipoRegistrazione") != null) {
					context.getRequest().setAttribute(
							"tipoRegistrazione",
							context.getRequest().getAttribute(
									"tipoRegistrazione"));
				} else {
					context.getRequest()
							.setAttribute("tipoRegistrazione", "-1");
				}

				return getReturn(context, "List");
			} else {
				if ("accounts".equals(context.getSession().getAttribute(
						"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context,
							"SearchOrgListInfo");
					searchListInfo.setLink("OperatoreAction.do?command=Search");
				}

				context.getSession().setAttribute("previousSearchType",
						"contacts");

				return getReturn(context, "ContactList");
				// return ("ContactListOK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}

	private void resetPagedListInfo(ActionContext context) {
		this.deletePagedListInfo(context, "ContactListInfo");
		this.deletePagedListInfo(context, "AccountFolderInfo");
		this.deletePagedListInfo(context, "RptListInfo");
		this.deletePagedListInfo(context, "OpportunityPagedInfo");
		this.deletePagedListInfo(context, "AccountTicketInfo");
		this.deletePagedListInfo(context, "AutoGuideAccountInfo");
		this.deletePagedListInfo(context, "RevenueListInfo");
		this.deletePagedListInfo(context, "AccountDocumentInfo");
		this.deletePagedListInfo(context, "ServiceContractListInfo");
		this.deletePagedListInfo(context, "AssetListInfo");
		this.deletePagedListInfo(context, "AccountProjectInfo");
		this.deletePagedListInfo(context, "orgHistoryListInfo");
	}

	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Operatore newOperatore = null;
		try {

			String tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null) {
				tempOpId = (String) context.getRequest().getAttribute("opId");
			}

			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;

			if (tempOpId != null) {
				tempid = Integer.parseInt(tempOpId);
			}

			UserBean user = (UserBean) context.getRequest().getSession()
					.getAttribute("User");
			String tipo = context.getRequest().getParameter("tempOpId");

			db = this.getConnection(context);

			// newOperatore = new Operatore(db, tempid);
			newOperatore = new Operatore();
			
			//System.out.println("Listaa Stabilimenti: "+newOperatore.getListaStabilimenti());
						
			// newOperatore.getListaStabilimenti().setIdAsl(user.getSiteId());
			newOperatore.queryRecordOperatorebyIdLineaProduttiva(db, tempid);

			context.getRequest()
					.setAttribute("OperatoreDettagli", newOperatore);
			
		//	System.out.println("PROBLEMA (OperatoreAction)Stabilimento stab = (Stabilimento) newOperatore.getListaStabilimenti().get(0); ");
			Stabilimento stab = (Stabilimento) newOperatore.getListaStabilimenti().get(0);
				
			LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			
			
	


			
			//NON PIU'
			if (lp.getIdRelazioneAttivita() == Operatore.BDU_COLONIA) {/*
				// Devo recuperare i dati della colonia dalla tabella
				// opu_informazioni_colonia
				String selectQuery = "Select * from opu_informazioni_colonia where id_relazione_stabilimento_linea_produttiva = ? ";

				PreparedStatement pst = db.prepareStatement(selectQuery);
				pst.setInt(1, lp.getId());
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					// Recupero informazioni DOVE LE METTO??? EH
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("Numero protocollo", rs.getString("nr_protocollo"));
					map.put("Numero totale gatti", new Integer(rs
							.getInt("nr_totale_gatti")).toString());
					map
							.put(
									"Data del censimento gatti",
									(rs.getTimestamp("data_censimento_totale") != null) ? rs
											.getTimestamp(
													"data_censimento_totale")
											.toString()
											: "--");
					map.put("Totale presunto", getStringFromBoolean(rs
							.getBoolean("totale_presunto")));
					map.put("Totale femmine", new Integer(rs
							.getInt("totale_femmine")).toString());
					map.put("Totale femmine presunto", getStringFromBoolean(rs
							.getBoolean("totale_femmine_flag_presunto")));
					map.put("Totale maschi", new Integer(rs
							.getInt("totale_maschi")).toString());
					map.put("Totale maschi presunto", getStringFromBoolean(rs
							.getBoolean("totale_maschi_flag_presunto")));

					context.getRequest().setAttribute("mapAttributi", map);
				}

			*/}

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// Provvisoriamente prendo tutti i comuni
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,
					((UserBean) context.getSession().getAttribute("User"))
							.getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);

			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			/*
			 * if (iter != null && !"".equals(iter) && "iter".equals(iter)){
			 * 
			 * return getReturn(context, "DetailsIter"); }
			 */

			return getReturn(context, "DetailsOperatore");
			// return "DetailsOperatoreOK";

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		Operatore operatoreToModify = null;
		String opId = context.getRequest().getParameter("opId");
	//	System.out.println("opId in Modify: "+opId);
		
		Connection db = null;

		try {

			db = this.getConnection(context);
			if (opId != null && !"".equals(opId)) {
				context.getRequest().setAttribute("opId", opId);
				int tempid = Integer.parseInt(opId);
				// operatoreToModify = new Operatore(db, tempid);
				operatoreToModify = new Operatore();
				operatoreToModify.getListaStabilimenti().setIdAsl(
						((UserBean) context.getSession().getAttribute("User"))
								.getSiteId());
				operatoreToModify.queryRecordOperatorebyIdLineaProduttiva(db, tempid);
			} else {
				operatoreToModify = (Operatore) context.getFormBean();
			}

			context.getRequest().setAttribute("Operatore", operatoreToModify);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI ALL'ASL
			// UTENTE
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,
					((UserBean) context.getSession().getAttribute("User"))
							.getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);

			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return "ModifyOK";

	}

	public String executeCommandUpdate(ActionContext context) throws SQLException {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		int resultCount = -1;
		// Integer orgId = null;
		Operatore newOperatore = (Operatore) context.getFormBean();
	//	System.out.println("Operatore: "+newOperatore.getIdOperatore());
		// newOperatore.setEnteredBy(getUserId(context));
		newOperatore.setModifiedBy(getUserId(context));
		// newOperatore.setOwner(getUserId(context));
		try {

			db = this.getConnection(context);

			SoggettoFisico soggettoAdded = null;

			if (new Integer(context.getRequest().getParameter(
					"codFiscaleSoggetto")).intValue() > 0) {
				// SOGGETTOADDED = NEW SOGGETTOFISICO(DB, NEW
				// INTEGER(CONTEXT.GETREQUEST().GETPARAMETER("CODFISCALESOGGETTO")).INTVALUE());
				soggettoAdded = new SoggettoFisico(context.getRequest());
			
			} else {
				soggettoAdded = new SoggettoFisico(context.getRequest(), db);

			}
			newOperatore.setRappLegale(soggettoAdded);
			Indirizzo indirizzoAdded = null;
			if (new Integer(context.getRequest().getParameter("via"))
					.intValue() > 0) {
				indirizzoAdded = new Indirizzo(db, new Integer(context
						.getRequest().getParameter("via")).intValue());
				indirizzoAdded.setTipologiaSede(1); // Legale
			} else {
				indirizzoAdded = new Indirizzo(context.getRequest(), db);
				indirizzoAdded.setTipologiaSede(1);

			}

			newOperatore.getListaSediOperatore().add(indirizzoAdded);

			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals(
							"")
					&& context.getRequest().getParameter("doContinue").equals(
							"false")) {
				return executeCommandModify(context);
			}

			// newOperatore.setTempSessionData(context);

			// newOperatore.setRequestItems(context);

			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			String ip = user.getUserRecord().getIp();
			// newOrg.setIp_entered(ip);
			// newOperatore.setIp_modified(ip);

			isValid = this.validateObject(context, db, newOperatore);

			if (isValid) {
				resultCount = newOperatore.update(db);

			}
			if (recordInserted) {

				context.getSession().removeAttribute("Operatore");
			}

			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD",
					"Accounts.do?command=Details");
			context.getRequest().setAttribute("org_cod",
					"&orgId=" + newOperatore.getIdOperatore());

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null) {
				return ("ClosePopupOK");
			}
			if (target != null && "add_contact".equals(target)) {
				return ("InsertAndAddContactOK");
			} else if (context.getRequest().getParameter("iterId") != null
					&& !context.getRequest().getParameter("iterId")
							.equals("-1")) {
				// gestione iter
				return ("InsertIter");
			}
		}
		
		//System.out.println("PROBLEMA (OperatoreAction) context.getRequest().setAttribute(opId,new Integer(newOperatore.getIdOperatore()).toString());");
		
		//l'id sulla request è in realtà l'id della linea produttiva associata al privato
		//eseguo una query per estrarre l'id dell'operatore associato alla linea produttiva
		//e lo risetto sulla request 
		String sql = "SELECT rel.id from opu_relazione_stabilimento_linee_produttive as rel inner join opu_stabilimento sta on rel.id_stabilimento = sta.id inner join opu_operatore op on sta.id_operatore=op.id where rel.trashed_date is null and op.id="+newOperatore.getIdOperatore();
		
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		String newId="";
		while (rs.next()) {
			   newId = rs.getString("id");
		//	  System.out.println("Nuovo Id: "+newId + "\n");
			}
		
		context.getRequest().setAttribute("opId", newId);
		//context.getRequest().setAttribute("opId",new Integer(newOperatore.getIdOperatore()).toString());
	//	System.out.println("opId details: "+newOperatore.getIdOperatore());
		return (executeCommandDetails(context));
		// return (executeCommandSearchForm(context));

	}

	String getStringFromBoolean(boolean boolValue) {
		String ret = "No";
		if (boolValue)
			ret = "Sì";

		return ret;
	}

}
