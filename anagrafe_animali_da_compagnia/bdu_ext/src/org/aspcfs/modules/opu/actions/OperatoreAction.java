/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.log.base.LogBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.microchip.base.MicrochipImport;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.CanileInformazioni;
import org.aspcfs.modules.opu.base.ColoniaInformazioni;
import org.aspcfs.modules.opu.base.GestoreComunicazioniGisa;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.OperatoreList;
import org.aspcfs.modules.opu.base.RegistrazioneAccettazioneModificaResidenzaOperatore;
import org.aspcfs.modules.opu.base.RegistrazioneModificaDimensioneColonia;
import org.aspcfs.modules.opu.base.RegistrazioneModificaIndirizzoOperatore;
import org.aspcfs.modules.opu.base.RegistrazioniOperatoreList;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.StabilimentoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaCanile;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaColonia;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCambioDetentore;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoModificaResidenza;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriStato;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRitrovamento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoSindaco;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.modules.sinaaf.Sinaaf;
import org.aspcfs.modules.ws.WsPost;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.utils.web.StateSelect;

import com.daffodilwoods.daffodildb.server.sql99.expression.booleanvalueexpression.length;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;

import okhttp3.MediaType;

public class OperatoreAction extends CFSModule {
	
	
	
	public String executeCommandInsertOrigine(ActionContext context) throws SQLException {

		if (!hasPermission(context, "proprietari_detentori_modulo-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		//Operatore insertedOp = null;
		// Integer orgId = null;
		//Operatore newOperatore = (Operatore) context.getFormBean();
		
		// FLAG PER LA RICERCA PROPRIETARIO
		if (context.getRequest().getParameter("in_regione") != null)
			context.getRequest().setAttribute("in_regione",(String) context.getRequest().getParameter("in_regione"));


		if (context.getRequest().getParameter("soloPrivato") != null
				&& context.getRequest().getParameter("soloPrivato").equals("true"))
			context.getRequest().setAttribute("soloPrivato", true);

		if (("false").equals((String) context.getParameter("doContinueLp"))) { 
			// Ho solo scelto la linea produttiva, devo tornare alla pagina jsp
			int idLineaPTipologia = 1;
			if ((String) context.getParameter("idLineaProduttiva") != null) {
				idLineaPTipologia = Integer.parseInt(context.getParameter("idLineaProduttiva"));
			}
			LineaProduttiva lp = new LineaProduttiva();
			lp.setIdRelazioneAttivita(idLineaPTipologia); // Setto l'id

			// Controllo se ho scelto un operatore fuori regione
			Stabilimento stab = new Stabilimento();
			if (context.getRequest().getParameter("inregione") != null
					&& ("no").equals(context.getRequest().getParameter("inregione")))
				stab.setFlagFuoriRegione(true);
			// lineaproduttiva
			// scelto
			context.getRequest().setAttribute("LineaProduttivaScelta", lp);
			context.getRequest().setAttribute("stabilimento", stab);
			return executeCommandOrigineAdd(context);
		}

		try {

			db = this.getConnection(context);

			/*boolean exist = false;
			LineaProduttiva lpr = new LineaProduttiva();
			lpr.setIdRelazioneAttivita(Integer.parseInt(context.getParameter("idLineaProduttiva")));

			if (lpr.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia
					&& lpr.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindaco
					&& lpr.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindacoFR) {
				exist = newOperatore.checkEsistenzaLineaProduttiva(db,
						Integer.parseInt(context.getParameter("idLineaProduttiva")));

			}
			*/
			
			
			
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

			/*LineaProduttiva lp = new LineaProduttiva();
			lp.setIdRelazioneAttivita(Integer.parseInt(context.getParameter("idLineaProduttiva")));

			SoggettoFisico soggettoEsistente = null;
			if (Integer.parseInt(context.getParameter("idLineaProduttiva")) != LineaProduttiva.idAggregazioneSindacoFR
					&& Integer.parseInt(context.getParameter("idLineaProduttiva")) != LineaProduttiva.idAggregazioneSindaco) {
				//soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			}*/
			/* se il soggetto non esiste lo aggiungo */





			/*Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, user.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);
			*/

	

		
			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals("")
					&& context.getRequest().getParameter("doContinue").equals("false")) {
				return executeCommandAdd(context);
			}

			String ip = user.getUserRecord().getIp();
			
			int id_rel_stab_lp=-1;
			
			try {
				// CONTROLLO SE L'UTENTE E' GIA' PRESENTE - IL CF HA PRIORITA' ALTA, SE NON E' STATO INSERITO IL CF, CONTROLLO
				// PER NOME,COGNOME,PROV E COMNUE
				
				int idLineaPTipologia = 1;
				if ((String) context.getParameter("idLineaProduttiva") != null) 
				{
					idLineaPTipologia = Integer.parseInt(context.getParameter("idLineaProduttiva"));
				}
				
				if(idLineaPTipologia==1)
				{
					if(context.getRequest().getParameter("codFiscaleSoggetto")!=null && !((String)context.getRequest().getParameter("codFiscaleSoggetto")).equals("")){
						String select="SELECT id_rel_stab_lp from opu_operatori_denormalizzati_origine WHERE codice_fiscale ilike ? ";
						PreparedStatement pst = db.prepareStatement(select);
						pst.setString(1, (String)context.getRequest().getParameter("codFiscaleSoggetto"));
						ResultSet rs1 = pst.executeQuery();
						if(rs1.next())
							id_rel_stab_lp=rs1.getInt("id_rel_stab_lp");
					}else{
						String select="SELECT id_rel_stab_lp from opu_operatori_denormalizzati_origine WHERE "
								+ " nome ilike ? and  cognome ilike ? and comune = ? ";
						PreparedStatement pst = db.prepareStatement(select);
						pst.setString(1, (String)context.getRequest().getParameter("nome"));
						pst.setString(2, (String)context.getRequest().getParameter("cognome"));
						pst.setInt(3, Integer.parseInt(context.getRequest().getParameter("addressLegaleCity")));
						ResultSet rs1 = pst.executeQuery();
						if(rs1.next())
							id_rel_stab_lp=rs1.getInt("id_rel_stab_lp");
					
					}
				}
				
				if(id_rel_stab_lp==-1){

					String sql="INSERT INTO opu_operatori_denormalizzati_origine ("+
							"ragione_sociale, partita_iva, codice_fiscale_impresa, note, entered, modified, enteredby, "+
							"modifiedby, nome, cognome, codice_fiscale, comune, id_linea_produttiva, id_asl)"+
	                      	" VALUES (?, ?, ?, ?, now(), now(), ?, ?, ?, ?, ?, ?, ?, ?)";
	
					
					PreparedStatement pst = db.prepareStatement(sql.toString());
					int i = 0;
					pst.setString(++i, (String)context.getRequest().getParameter("ragioneSociale"));
					pst.setString(++i, ((context.getRequest().getParameter("partitaIva")!=null && !((String)context.getRequest().getParameter("partitaIva")).equals("")) 
							? (String)context.getRequest().getParameter("partitaIva") : "N.D."	));
					pst.setString(++i, ((context.getRequest().getParameter("codFiscale")!=null && !((String)context.getRequest().getParameter("codFiscale")).equals("")) 
							? (String)context.getRequest().getParameter("codFiscale") : (String)context.getRequest().getParameter("codFiscaleSoggetto")	));
					pst.setString(++i, "");
					pst.setInt(++i, getUserId(context));
					pst.setInt(++i, getUserId(context));
					pst.setString(++i, (String)context.getRequest().getParameter("nome"));
					pst.setString(++i, (String)context.getRequest().getParameter("cognome"));
					pst.setString(++i, (String)context.getRequest().getParameter("codFiscaleSoggetto"));
					pst.setInt(++i, Integer.parseInt(context.getRequest().getParameter("addressLegaleCity")));
					pst.setInt(++i, Integer.parseInt(context.getParameter("idLineaProduttiva")));
					pst.setInt(++i, Integer.parseInt(context.getRequest().getParameter("idAsl")));
					
					
					pst.execute();
					pst.close();

					id_rel_stab_lp=DatabaseUtils.getCurrVal(db, "opu_id_origine_seq",0);
				}
				/*
				 * Aggiornamento dati soggetto fisico se cambiano tutti i dati
				 * tranne quelli per il calcolo del codice fiscale
				 */

				/**
				 * Commentato da Veronica perchè dalla maschera di inserimento non
				 * si possono modificare in ogni caso i dati del soggetto
				 */
				// this.getRappLegale().update(db);
			} catch (SQLException e) {

				throw new SQLException(e.getMessage());
			} finally {

			}
			context.getSession().removeAttribute("Operatore");
			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD", "Accounts.do?command=Details");
			context.getRequest().setAttribute("org_cod", "&orgId=" + id_rel_stab_lp);
			context.getRequest().setAttribute("opId",""+id_rel_stab_lp);
			addModuleBean(context, "View Accounts", "Accounts Insert ok");
			//if (recordInserted) {

				/**
				 * aggiornamento della tabella che materializza la vista degli
				 * operatori denormalizzati.l'aggiornamento avviene cancellando
				 * e reinserendo il record dalla tabella. il record inserito è
				 * preso dalla vista.
				 */
				//newOperatore.aggiornaVistaMaterializzata(db);

				String target = context.getRequest().getParameter("target");
				if (context.getRequest().getParameter("popup") != null) {
					context.getRequest().setAttribute("TipologiaSoggetto", "1");

					return ("ClosePopupOK");
				}
			//}	
		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return (executeCommandDetails(context));
	}


	public String executeCommandDefault(ActionContext context) {

		return executeCommandDashboard(context);
	}

	public String executeCommandOrigineAdd(ActionContext context) {
		if (!hasPermission(context, "proprietari_detentori_modulo-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			LineaProduttiva lp = (LineaProduttiva) context.getRequest().getAttribute("LineaProduttivaScelta");

			if (lp == null && (String) context.getParameter("idLineaProduttiva") != null) {

				lp = new LineaProduttiva();
				lp.setIdRelazioneAttivita(Integer.parseInt(context.getParameter("idLineaProduttiva")));
			}

			if (lp != null && 1==2
					&& (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile
							|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia
							|| lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale || lp
							.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore)
					&& (ConnectionPool) context.getServletContext().getAttribute("ConnectionPoolGisa") == null) {
				// context.getRequest().setAttribute("GISAOFFLINE",
				// "Attenzione, operazione al momento non possibile per mancanza di allineamento con il sistema GISA");
				// context.getRequest().setAttribute("Error",
				// "Attenzione, operazione al momento non possibile per mancanza di allineamento con il sistema GISA");
				context.getRequest()
						.setAttribute("ErroreGisaOffline",
								"Attenzione, operazione al momento non possibile per mancanza di allineamento con il sistema GISA");
				// return ("SystemError");
				return getReturn(context, "Add");
			}
			context.getRequest().setAttribute("LineaProduttivaScelta", lp);

			Stabilimento stab = (Stabilimento) context.getRequest().getAttribute("stabilimento");

			if (context.getRequest().getAttribute("Exist") != null
					&& !("").equals(context.getRequest().getAttribute("Exist"))) {
				context.getRequest().setAttribute("Exist", (String) context.getRequest().getAttribute("Exist"));
			}

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,
					((UserBean) context.getSession().getAttribute("User")).getSiteId(), 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");

			LineaProduttivaList listaLineeProduttive = new LineaProduttivaList();
			listaLineeProduttive.setIdMacrocategoria(1);
			ArrayList<Integer> daEscludere = new ArrayList<>();
			daEscludere.add(3);
			daEscludere.add(7);
			daEscludere.add(8);
			listaLineeProduttive.setIdLineaProduttivaDaEscludere(daEscludere);
			listaLineeProduttive.buildList(db);
			context.getRequest().setAttribute("ListaLineeProduttive", listaLineeProduttive);

			context.getRequest().setAttribute("ComuniList", comuniList);
			Operatore newOperatore = (Operatore) context.getFormBean();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			context.getRequest().setAttribute("Operatore", newOperatore);

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			int tipologiaRegistrazione = -1;
			if (context.getRequest().getParameter("tipologiaRegistrazione") != null)
				tipologiaRegistrazione = Integer.parseInt(context.getRequest().getParameter("tipologiaRegistrazione"));
			Boolean soloPrivato = (Boolean) context.getRequest().getAttribute("soloPrivato");

			if (tipologiaRegistrazione == EventoAdozioneDaCanile.idTipologiaDB
					|| tipologiaRegistrazione == EventoAdozioneDaColonia.idTipologiaDB
					|| (soloPrivato != null && soloPrivato)) {
				context.getRequest().setAttribute("soloPrivato", true);
			}

			context.getRequest().setAttribute("TipologiaSoggetto",
					(String) context.getRequest().getParameter("tipologiaSoggetto"));

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// FLAG PER LA RICERCA PROPRIETARIO
		if (context.getRequest().getParameter("in_regione") != null)
			context.getRequest().setAttribute("in_regione",(String) context.getRequest().getParameter("in_regione"));
		
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "OrigineAdd");
	}

	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "proprietari_detentori_modulo-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			LineaProduttiva lp = (LineaProduttiva) context.getRequest().getAttribute("LineaProduttivaScelta");

			if (lp == null && (String) context.getParameter("idLineaProduttiva") != null) {

				lp = new LineaProduttiva();
				lp.setIdRelazioneAttivita(Integer.parseInt(context.getParameter("idLineaProduttiva")));
			}

			if (lp != null && 1==2
					&& (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile
							|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia
							|| lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale || lp
							.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore)
					&& (ConnectionPool) context.getServletContext().getAttribute("ConnectionPoolGisa") == null) {
				// context.getRequest().setAttribute("GISAOFFLINE",
				// "Attenzione, operazione al momento non possibile per mancanza di allineamento con il sistema GISA");
				// context.getRequest().setAttribute("Error",
				// "Attenzione, operazione al momento non possibile per mancanza di allineamento con il sistema GISA");
				context.getRequest()
						.setAttribute("ErroreGisaOffline",
								"Attenzione, operazione al momento non possibile per mancanza di allineamento con il sistema GISA");
				// return ("SystemError");
				return getReturn(context, "Add");
			}
			context.getRequest().setAttribute("LineaProduttivaScelta", lp);

			Stabilimento stab = (Stabilimento) context.getRequest().getAttribute("stabilimento");

			if (context.getRequest().getAttribute("Exist") != null
					&& !("").equals(context.getRequest().getAttribute("Exist"))) {
				context.getRequest().setAttribute("Exist", (String) context.getRequest().getAttribute("Exist"));
			}

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,
					((UserBean) context.getSession().getAttribute("User")).getSiteId(), 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");

			LineaProduttivaList listaLineeProduttive = new LineaProduttivaList();
			listaLineeProduttive.setIdMacrocategoria(1);
			ArrayList<Integer> daEscludere = new ArrayList<>();
			daEscludere.add(4);
			if(thisUser.getRoleId() != Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_HD1")) && thisUser.getRoleId() != Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_HD2"))) 
				daEscludere.add(5);
			daEscludere.add(6);
			listaLineeProduttive.setIdLineaProduttivaDaEscludere(daEscludere);
			listaLineeProduttive.buildList(db);
			context.getRequest().setAttribute("ListaLineeProduttive", listaLineeProduttive);

			context.getRequest().setAttribute("ComuniList", comuniList);
			Operatore newOperatore = (Operatore) context.getFormBean();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			context.getRequest().setAttribute("Operatore", newOperatore);

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			int tipologiaRegistrazione = -1;
			if (context.getRequest().getParameter("tipologiaRegistrazione") != null && !context.getRequest().getParameter("tipologiaRegistrazione").equals("") && !context.getRequest().getParameter("tipologiaRegistrazione").equals("null"))
				tipologiaRegistrazione = Integer.parseInt(context.getRequest().getParameter("tipologiaRegistrazione"));
			Boolean soloPrivato = (Boolean) context.getRequest().getAttribute("soloPrivato");

			if (tipologiaRegistrazione == EventoAdozioneDaCanile.idTipologiaDB
					|| tipologiaRegistrazione == EventoAdozioneFuoriAsl.idTipologiaDB
					|| tipologiaRegistrazione == EventoAdozioneDaColonia.idTipologiaDB
					|| (soloPrivato != null && soloPrivato)) {
				context.getRequest().setAttribute("soloPrivato", true);
			}

			context.getRequest().setAttribute("TipologiaSoggetto",
					(String) context.getRequest().getParameter("tipologiaSoggetto"));

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// FLAG PER LA RICERCA PROPRIETARIO
		if (context.getRequest().getParameter("in_regione") != null)
			context.getRequest().setAttribute("in_regione",(String) context.getRequest().getParameter("in_regione"));
		if (context.getRequest().getParameter("estero") != null)
			context.getRequest().setAttribute("estero",(String) context.getRequest().getParameter("estero"));
		
		if (context.getRequest().getParameter("tipologiaRegistrazione") != null)
			context.getRequest().setAttribute("tipologiaRegistrazione",(String) context.getRequest().getParameter("tipologiaRegistrazione"));
		
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "Add");
	}

	
	public String executeCommandDashboard(ActionContext context) {
		if (!hasPermission(context, "proprietari_detentori_modulo-view")) {
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
		CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute("AccountsCalendarInfo");
		if (calendarInfo == null) {
			calendarInfo = new CalendarBean(this.getUser(context, this.getUserId(context)).getLocale());
			calendarInfo.addAlertType("Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions",
					"Accounts");
			calendarInfo.setCalendarDetailsView("Accounts");
			context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
		}

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		// this is how we get the multiple-level heirarchy...recursive function.
		User thisRec = thisUser.getUserRecord();

		UserList shortChildList = thisRec.getShortChildList();
		UserList newUserList = thisRec.getFullChildList(shortChildList, new UserList());

		newUserList.setMyId(getUserId(context));
		newUserList.setMyValue(thisUser.getUserRecord().getContact().getNameLastFirst());
		newUserList.setIncludeMe(true);

		newUserList
				.setJsEvent("onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
		HtmlSelect userListSelect = newUserList.getHtmlSelectObj("userId", getUserId(context));
		userListSelect.addAttribute("id", "userId");
		context.getRequest().setAttribute("Return", "Accounts");
		context.getRequest().setAttribute("NewUserList", userListSelect);
		return ("DashboardOK");
	}

	public String executeCommandInsert(ActionContext context) throws SQLException {

		if (!hasPermission(context, "proprietari_detentori_modulo-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Operatore insertedOp = null;
		// Integer orgId = null;
		Operatore newOperatore = (Operatore) context.getFormBean();

		if (context.getRequest().getParameter("soloPrivato") != null
				&& context.getRequest().getParameter("soloPrivato").equals("true"))
			context.getRequest().setAttribute("soloPrivato", true);

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
			int idLineaPTipologia = 1;
			if ((String) context.getParameter("idLineaProduttiva") != null) {
				idLineaPTipologia = Integer.parseInt(context.getParameter("idLineaProduttiva"));
			}
			LineaProduttiva lp = new LineaProduttiva();
			lp.setIdRelazioneAttivita(idLineaPTipologia); // Setto l'id

			// Controllo se ho scelto un operatore fuori regione
			Stabilimento stab = new Stabilimento();
			if (context.getRequest().getParameter("inregione") != null
					&& ("no").equals(context.getRequest().getParameter("inregione")))
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
		newOperatore.setNote(context.getRequest().getParameter("noteOp"));

		try {

			db = this.getConnection(context);

			SoggettoFisico soggettoAdded = null;

			if(Integer.parseInt(context.getParameter("idLineaProduttiva")) != LineaProduttiva.idAggregazioneCanile)
				soggettoAdded = new SoggettoFisico(context.getRequest());

			boolean exist = false;
			LineaProduttiva lpr = new LineaProduttiva();
			lpr.setIdRelazioneAttivita(Integer.parseInt(context.getParameter("idLineaProduttiva")));

			if (lpr.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia
					&& lpr.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindaco
					&& lpr.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindacoFR) {
				exist = newOperatore.checkEsistenzaLineaProduttiva(db,
						Integer.parseInt(context.getParameter("idLineaProduttiva")));

			} else if (lpr.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia && lpr.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneCanile) {
				exist = newOperatore.checkEsistenzaLineaProduttivaSindaco(db, soggettoAdded.getIndirizzo().getComune(),
						Integer.parseInt(context.getParameter("idLineaProduttiva")));

			}

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

			LineaProduttiva lp = new LineaProduttiva();
			lp.setIdRelazioneAttivita(Integer.parseInt(context.getParameter("idLineaProduttiva")));

			SoggettoFisico soggettoEsistente = null;
			if (Integer.parseInt(context.getParameter("idLineaProduttiva")) != LineaProduttiva.idAggregazioneSindacoFR
					&& Integer.parseInt(context.getParameter("idLineaProduttiva")) != LineaProduttiva.idAggregazioneSindaco
					&& Integer.parseInt(context.getParameter("idLineaProduttiva")) != LineaProduttiva.idAggregazioneCanile) {
				soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			}
			/* se il soggetto non esiste lo aggiungo */

			if (Integer.parseInt(context.getParameter("idLineaProduttiva")) != LineaProduttiva.idAggregazioneCanile)
			{
				if (soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <= 0) 
				{
					Indirizzo indirizzoAdded = null;
					//Se l'indirizzo è stato selezionato tra quelli esistenti
					if(soggettoAdded.getIndirizzo().getIdIndirizzo()>0)
						indirizzoAdded = new Indirizzo(db, soggettoAdded.getIndirizzo().getIdIndirizzo());
					else
						indirizzoAdded = soggettoAdded.getIndirizzo();
					
					if(indirizzoAdded.getCap()!=null && context.getRequest().getParameter("cap")!=null && !indirizzoAdded.getCap().equals(context.getRequest().getParameter("cap")))
					{
						indirizzoAdded.setIdIndirizzo(-1);
						indirizzoAdded.setCap(context.getRequest().getParameter("cap"));
						soggettoAdded.setIndirizzo(indirizzoAdded);
					}
					
					soggettoAdded.insert(db);
					newOperatore.setRappLegale(soggettoAdded);
	
				} else {
					/* se esiste */
	
					if (soggettoEsistente.getIdSoggetto() > 0) {
						
						Indirizzo indirizzoAdded = null;
						//Se l'indirizzo è stato selezionato tra quelli esistenti
						if(soggettoAdded.getIndirizzo().getIdIndirizzo()>0)
							indirizzoAdded = new Indirizzo(db, soggettoAdded.getIndirizzo().getIdIndirizzo());
						else
							indirizzoAdded = soggettoAdded.getIndirizzo();
						Indirizzo indirizzoEsistente = soggettoEsistente.getIndirizzo();
	
						/* se l'asl di residenza non coincide */
				
						if ((indirizzoAdded.calcolaAsl(db) != indirizzoEsistente.calcolaAsl(db) ) && indirizzoEsistente.getComune()!=-1) {
							newOperatore.setRappLegale(soggettoAdded);
	
							context.getRequest()
									.setAttribute(
											"Errore",
											"Attenzione! Impossibile effettuare il cambio di residenza per il soggetto ("
													+ soggettoAdded.getCodFiscale()
													+ ") in quanto già censito per una diversa asl. Selezionare un comune appartenente all\'asl attuale o contattare l\'HelpDesk.");
							context.getRequest().setAttribute("OperatoreDettagli", newOperatore);
							context.getRequest().setAttribute("SoggettoEsistente", soggettoEsistente);
							return executeCommandAdd(context);
						} else {
							if ("si".equalsIgnoreCase(context.getParameter("sovrascrivi"))) {
								if(indirizzoEsistente.getCap()!=null && context.getRequest().getParameter("cap")!=null && !indirizzoEsistente.getCap().equals(context.getRequest().getParameter("cap")))
								{
									indirizzoAdded.setIdIndirizzo(-1);
									indirizzoAdded.setCap(context.getRequest().getParameter("cap"));
									indirizzoAdded.insert(db);
									soggettoEsistente.setIndirizzo(null);
									soggettoEsistente.setIndirizzo(indirizzoAdded);
								}
								else
									soggettoEsistente.setIndirizzo(indirizzoAdded);
								soggettoEsistente.setNome(soggettoAdded.getNome());
								soggettoEsistente.setCognome(soggettoAdded.getCognome());
								soggettoEsistente.setDataNascita(soggettoAdded.getDataNascita());
								soggettoEsistente.setComuneNascita(soggettoAdded.getComuneNascita());
	
								soggettoEsistente.setDocumentoIdentita(soggettoAdded.getDocumentoIdentita());
								soggettoEsistente.setFax(soggettoAdded.getFax());
								soggettoEsistente.setProvinciaNascita(soggettoAdded.getProvinciaNascita());
								soggettoEsistente.setTelefono1(soggettoAdded.getTelefono1());
								soggettoEsistente.setTelefono2(soggettoAdded.getTelefono2());
								soggettoEsistente.setEmail(soggettoAdded.getEmail());
								soggettoEsistente.setModifiedBy(user.getUserId());
								soggettoEsistente.setIpModifiedBy(user.getUserRecord().getIp());
	
								soggettoEsistente.update(db);
	
								PreparedStatement pst = null;
	
								String sel = "select id_operatore from opu_stabilimento where id in "
										+ "(select id_stabilimento from opu_relazione_stabilimento_linee_produttive op where op.id_linea_produttiva in (?,?,?) and op.trashed_date is null  ) "
										+ "and id_soggetto_fisico = ?; ";
								pst = db.prepareStatement(sel);
								pst.setInt(1, LineaProduttiva.idAggregazionePrivato);
								pst.setInt(2, LineaProduttiva.idAggregazioneSindaco);
								pst.setInt(3, LineaProduttiva.idAggregazioneSindacoFR);
								pst.setInt(4, soggettoEsistente.getIdSoggetto());
								ResultSet rs1 = pst.executeQuery();
	
								String update = "update opu_stabilimento set id_indirizzo = ? where id in "
										+ "(select id_stabilimento from opu_relazione_stabilimento_linee_produttive op where op.id_linea_produttiva in (?,?,?) and op.trashed_date is null  ) "
										+ "and id_soggetto_fisico = ?;"
										+ "update opu_relazione_operatore_sede set id_indirizzo = ? where id_operatore in (select id_operatore from opu_stabilimento "
										+ "where id in"
										+ "(select id_stabilimento from opu_relazione_stabilimento_linee_produttive op where op.id_linea_produttiva in (?,?,?) and op.trashed_date is null  ) "
										+ "and id_soggetto_fisico = ?)";
	
								pst = db.prepareStatement(update);
								pst.setInt(1, soggettoEsistente.getIndirizzo().getIdIndirizzo());
								pst.setInt(2, LineaProduttiva.idAggregazionePrivato);
								pst.setInt(3, LineaProduttiva.idAggregazioneSindaco);
								pst.setInt(4, LineaProduttiva.idAggregazioneSindacoFR);
								pst.setInt(5, soggettoEsistente.getIdSoggetto());
								pst.setInt(6, soggettoEsistente.getIndirizzo().getIdIndirizzo());
								pst.setInt(7, LineaProduttiva.idAggregazionePrivato);
								pst.setInt(8, LineaProduttiva.idAggregazioneSindaco);
								pst.setInt(9, LineaProduttiva.idAggregazioneSindacoFR);
								pst.setInt(10, soggettoEsistente.getIdSoggetto());
								pst.execute();
	
								while (rs1.next()) {
									db.prepareStatement(
											"select * from public_functions.update_opu_materializato(" + rs1.getInt(1)
													+ ")").execute();
								}
	
							}
	
							newOperatore.setRappLegale(soggettoEsistente);
							/* temporaneamente prendo quello che ho */
						}
	
					}
	
				}
			}

			if (exist) {

				lp = new LineaProduttiva();
				lp.setIdRelazioneAttivita(Integer.parseInt(context.getParameter("idLineaProduttiva"))); // Setto
																										// l'id
				// lineaproduttiva
				// scelto

				context.getRequest().setAttribute("LineaProduttivaScelta", lp);

				if (Integer.parseInt(context.getParameter("idLineaProduttiva")) != LineaProduttiva.idAggregazioneSindaco
						&& Integer.parseInt(context.getParameter("idLineaProduttiva")) != LineaProduttiva.idAggregazioneSindacoFR) {
					context.getRequest().setAttribute(
							"Exist",
							"Esiste già un proprietario di tipo " + lp.getDescrizioneAttivita(db)
									+ " con codice fiscale/p.iva = " + newOperatore.getPartitaIva());
				} else {
					context.getRequest().setAttribute(
							"Exist",
							"Esiste già un proprietario di tipo " + lp.getDescrizioneAttivita(db)
									+ " per Comune specificato");

				}

				context.getRequest().setAttribute("rappLegale", soggettoAdded);
				return executeCommandAdd(context);
			}

			/*
			 * soggettoAdded = new SoggettoFisico(context.getRequest());
			 * UserBean user = (UserBean) context.getRequest().getSession()
			 * .getAttribute("User");
			 * soggettoAdded.setModifiedBy(user.getUserId());
			 * soggettoAdded.setIpModifiedBy(user.getUserRecord().getIp());
			 * 
			 * System.out.println(
			 * "Esito Elaborazione sul Soggetto Fisico Operatore : "
			 * +esitoVerificaSoggetto);
			 */

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, user.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			Indirizzo indirizzoAdded = null;

			// SE E' UN PRIVATO SETTO SEDE OPERATIVA = SEDE LEGALE
			int linea = Integer.parseInt(context.getParameter("idLineaProduttiva"));

			if (linea != LineaProduttiva.idAggregazioneColonia) {
				if (context.getRequest().getParameter("via")!=null && new Integer(context.getRequest().getParameter("via")).intValue() > 0) {
					indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
					indirizzoAdded.setTipologiaSede(1); // Legale
				} else {
					indirizzoAdded = new Indirizzo(context.getRequest(), db);
					indirizzoAdded.setTipologiaSede(1);

				}
			} else { // SE E' colonia non devo inserire a qsto punto indirizzo
						// legale perchè dovrò coincidere cn ind operativo
				indirizzoAdded = new Indirizzo(db, -1);
			}

			if (linea == LineaProduttiva.idAggregazioneColonia || linea == LineaProduttiva.idAggregazionePrivato
					|| linea == LineaProduttiva.idAggregazioneSindaco
					|| linea == LineaProduttiva.idAggregazioneSindacoFR) {
				if (soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <= 0) {
					soggettoAdded.getIndirizzo().setTipologiaSede(1);
					newOperatore.getListaSediOperatore().add(soggettoAdded.getIndirizzo());

				} else {
					soggettoEsistente.getIndirizzo().setTipologiaSede(1);
					newOperatore.getListaSediOperatore().add(soggettoEsistente.getIndirizzo());

				}

			} else {
				if (context.getRequest().getParameter("via")!=null && new Integer(context.getRequest().getParameter("via")).intValue() > 0) {
					indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
					indirizzoAdded.setTipologiaSede(1); // Legale
				} else {
					indirizzoAdded = new Indirizzo(context.getRequest(), db, "sedeLegale");
					indirizzoAdded.setTipologiaSede(1);

				}
				newOperatore.getListaSediOperatore().add(indirizzoAdded);
			}

			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals("")
					&& context.getRequest().getParameter("doContinue").equals("false")) {
				return executeCommandAdd(context);
			}

			String ip = user.getUserRecord().getIp();

			isValid = this.validateObject(context, db, newOperatore);

			if (isValid) {
				if(newOperatore.getRagioneSociale()==null || newOperatore.getRagioneSociale().equals(""))
				{
					if(newOperatore.getRappLegale().getNome()!=null && !newOperatore.getRappLegale().getNome().equals("") && newOperatore.getRappLegale().getCognome()!=null && !newOperatore.getRappLegale().getCognome().equals(""))
					{
						newOperatore.setRagioneSociale(newOperatore.getRappLegale().getCognome() + ", " + newOperatore.getRappLegale().getNome());
					}
				}
				recordInserted = newOperatore.insert(db);

			}
			if (recordInserted) {

				String inRegione = (String) context.getRequest().getParameter("inregione");

				String nameContext = context.getParameter("context");
				if (nameContext.equalsIgnoreCase("bdu_ext")) {
					int lineaProduttiva = Integer.parseInt(context.getParameter("idLineaProduttiva"));
					if (lineaProduttiva == Operatore.BDU_PRIVATO || lineaProduttiva == Operatore.BDU_SINDACO
							|| lineaProduttiva == Operatore.BDU_SINDACO_FR) {
						Stabilimento newStabilimento = new Stabilimento();
						if (inRegione != null) {
							newStabilimento.setFlagFuoriRegione(inRegione);
						}

						if (soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <= 0) {
							newStabilimento.setRappLegale(soggettoAdded);
							newStabilimento.setSedeOperativa(indirizzoAdded);
						} else {
							newStabilimento.setRappLegale(soggettoEsistente);
							newStabilimento.setSedeOperativa(indirizzoAdded);
						}
						indirizzoAdded.setTipologiaSede(5); // Operativa

						Object[] asl;
						if (!(lineaProduttiva == Operatore.BDU_SINDACO_FR) && !newStabilimento.isFlagFuoriRegione()) // Se
																														// nn
																														// sto
																														// considerando
																														// un
																														// sindaco
																														// fuori
																														// regione
							// e nemmeno un proprietario di tipo fuori regione
							asl = DwrUtil.getValoriAsl(indirizzoAdded.getComune());
						else
							asl = null;

						if (asl != null && asl.length > 0) {

							Object[] aslVal = (Object[]) asl[0];
							if (aslVal != null && aslVal.length > 0)
								newStabilimento.setIdAsl((Integer) aslVal[0]);

						} else if (lineaProduttiva == Operatore.BDU_SINDACO_FR) {
							newStabilimento.setIdAsl(-1);
						} else if (newStabilimento.isFlagFuoriRegione()) {
							newStabilimento.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
						}

						if (!(lineaProduttiva == Operatore.BDU_SINDACO_FR) && (!newStabilimento.isFlagFuoriRegione())) { // PRENDO
																															// L?ASL
																															// DUE
																															// VOLTE?
																															// CONTROLLARE
																															// CON
																															// GIUSEPPE
							String sql = "select id,nome,asl.description,asl.code from comuni1 "
									+ "left join lookup_asl_rif asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
									+ " where trashed is false ";
							PreparedStatement pst = db.prepareStatement(sql + " and comuni1.id = ? ");
							pst.setInt(1, indirizzoAdded.getComune());
							ResultSet rs = pst.executeQuery();
							if (rs.next())
								newStabilimento.setIdAsl(rs.getInt("code"));
						}

						lp = new LineaProduttiva(db, lineaProduttiva);
						lp.setTelefono1(context.getRequest().getParameter("telefono1"));
						lp.setTelefono2(context.getRequest().getParameter("telefono2"));

						lp.setMail1(context.getRequest().getParameter("mail1"));
						lp.setMail2(context.getRequest().getParameter("mail2"));
						lp.setAutorizzazione(context.getRequest().getParameter("autorizzazione"));
						newStabilimento.setIdOperatore(newOperatore.getIdOperatore());

						newStabilimento.insert(db, false);
						lp = newStabilimento.aggiungiLineaProduttiva(db, lp);
						newStabilimento.getListaLineeProduttive().add(lp);
						context.getRequest().setAttribute(
								"opId",
								new Integer(((LineaProduttiva) newStabilimento.getListaLineeProduttive().get(0))
										.getId()).toString());
					} else {

						// Devo aggiungere ancora sede operativa e responsabile
						// di stabilimento
						StabilimentoAction actionStabilimento = new StabilimentoAction();

						Stabilimento newStabilimento = new Stabilimento();
						lp = new LineaProduttiva(db, lineaProduttiva);
						lp.setTelefono1(context.getRequest().getParameter("telefono1"));
						lp.setTelefono2(context.getRequest().getParameter("telefono2"));
						lp.setMail1(context.getRequest().getParameter("mail1"));
						lp.setMail2(context.getRequest().getParameter("mail2"));

						if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore
								|| lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale)
							lp.setAutorizzazione(context.getRequest().getParameter("autorizzazione"));
						newStabilimento.setIdOperatore(newOperatore.getIdOperatore());
						newStabilimento.getListaLineeProduttive().add(lp);
						context.getRequest().setAttribute("Stabilimento", newStabilimento);
						context.getRequest().setAttribute("tipologiaSoggetto",
								(String) context.getRequest().getParameter("tipologiaSoggetto"));

						if (soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <= 0)
							context.getRequest().setAttribute("rappLegale", soggettoAdded);
						else
							context.getRequest().setAttribute("rappLegale", soggettoEsistente);

						return actionStabilimento.executeCommandAdd(context);

					}
				}

			}

			if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
			{
				String[] esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),lp.getId()+"", "proprietario");
				context.getRequest().setAttribute("messaggio",  esitoInvioSinaaf[0]);
				context.getRequest().setAttribute("errore",  esitoInvioSinaaf[1]);
			}
			context.getSession().removeAttribute("Operatore");

			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD", "Accounts.do?command=Details");
			context.getRequest().setAttribute("org_cod", "&orgId=" + newOperatore.getIdOperatore());

			addModuleBean(context, "View Accounts", "Accounts Insert ok");
			if (recordInserted) {

				/**
				 * aggiornamento della tabella che materializza la vista degli
				 * operatori denormalizzati.l'aggiornamento avviene cancellando
				 * e reinserendo il record dalla tabella. il record inserito è
				 * preso dalla vista.
				 */
				newOperatore.aggiornaVistaMaterializzata(db);

				String target = context.getRequest().getParameter("target");
				if (context.getRequest().getParameter("popup") != null) {
					context.getRequest().setAttribute("TipologiaSoggetto", "1");

					return ("ClosePopupOK");
				}
				if (target != null && "add_contact".equals(target)) {
					return ("InsertAndAddContactOK");
				} else if (context.getRequest().getParameter("iterId") != null
						&& !context.getRequest().getParameter("iterId").equals("-1")) {
					// gestione iter
					return ("InsertIter");
				}

			}

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			if(errorMessage.getMessage().contains("check_capna"))
				context.getRequest().setAttribute("Error", "Il cap deve essere un valore diverso da 80100");
			else
				context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// System.out.println("Metodo di operatore centralizzato");

		return (executeCommandDetails(context));

		// return ("InsertOK");

	}

	public String executeCommandSearchForm(ActionContext context) {

		// EDIT PER DIFFERENZIARE FURETTO DA CANI E GATTI
		// IL FURETTO PUO' ESSERE DI PROPRIETA' SOLO DEL PRIVATO
		// CATTURO L'ID DELLA SPECIE DALLA MASCHERA DI INSERIMENTO
		// IN LINEAPRODUTTIVAACTION (CHE COSTRUISCE LA LOOKUP)
		// CHIAMO UN METODO BUILDLIST SE LA SPECIE E' CANE O GATTO
		// BUILDLISTSOLOPRIVATO SE E' FURETTO

		String id_specie = context.getRequest().getParameter("specie");
		context.getRequest().setAttribute("id_specie", id_specie);
		String idAnimale = context.getRequest().getParameter("idAnimale");
		Animale thisAnimale = new Animale();

		int idRegistrazione = -1;
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		if (!(hasPermission(context, "proprietari_detentori_modulo-view")
				|| user.getRoleId() == Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_LLP")) || user
					.getRoleId() == Integer.valueOf(ApplicationProperties.getProperty("UNINA")))) {
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

			if (idAnimale != null && !("").equals(idAnimale)) {
				thisAnimale = new Animale(db, Integer.parseInt(idAnimale));
			}

			context.getRequest().setAttribute("animale", thisAnimale);

			String tipologiaSoggetto = context.getRequest().getParameter("tipologiaSoggetto");
			
			LookupList siteList = null;
			//Se registrazione è Rientro fuori regione ed è un utente asl applicare il filtro sull'asl del proprietario di destinazione
			if(context.getRequest().getParameter("tipoRegistrazione")!=null && context.getRequest().getParameter("tipoRegistrazione").equals(EventoRientroFuoriRegione.idTipologiaDB+"") && user.getSiteId()>0 && (tipologiaSoggetto==null || !tipologiaSoggetto.equals("2")))
			{
				siteList = new LookupList();
				siteList.setIdAslFiltro(user.getSiteId());
				siteList.setTable("lookup_asl_rif");
				siteList.buildList(db);
			}
			else
			{
				siteList = new LookupList(db, "lookup_asl_rif");
				siteList.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			}
			
			if(context.getRequest().getParameter("tipoRegistrazione")!=null && context.getRequest().getParameter("tipoRegistrazione").equals(EventoRientroFuoriRegione.idTipologiaDB+""))
			{
				siteList.removeElementByCode(14);
				siteList.removeElementByCode(16);
			}
			
			context.getRequest().setAttribute("provenienza",context.getRequest().getParameter("provenienza"));
			context.getRequest().setAttribute("SiteList", siteList);
			context.getRequest().setAttribute("idLineaProduttiva1",context.getRequest().getParameter("idLineaProduttiva1"));
			context.getRequest().setAttribute("TipologiaSoggetto",
					context.getRequest().getParameter("tipologiaSoggetto"));

			if (context.getRequest().getParameter("tipoRegistrazione") != null) {
				context.getRequest().setAttribute("tipoRegistrazione",
						context.getRequest().getParameter("tipoRegistrazione"));
				idRegistrazione = new Integer(context.getRequest().getParameter("tipoRegistrazione"));
			} else {
				context.getRequest().setAttribute("tipoRegistrazione", "-1");
			}

			org.aspcfs.modules.opu.base.ComuniAnagrafica c = new org.aspcfs.modules.opu.base.ComuniAnagrafica();
			// UserBean user = (UserBean)
			// context.getSession().getAttribute("User");
			
			ArrayList<org.aspcfs.modules.opu.base.ComuniAnagrafica> listaComuni = null;
			if(context.getRequest().getParameter("tipologiaSoggetto")!=null && context.getRequest().getParameter("tipologiaSoggetto").equals("8")){
				if(context.getRequest().getParameter("in_regione")!=null && context.getRequest().getParameter("in_regione").equals("si"))
					listaComuni = c.buildList(db, user.getSiteId(),	ComuniAnagrafica.IN_REGIONE);
				else
					listaComuni = c.buildList(db, user.getSiteId(),	ComuniAnagrafica.FUORI_REGIONE);	
			}else{
				listaComuni = c.buildList(db, user.getSiteId(),	ComuniAnagrafica.IN_REGIONE);	
			}
			

			LookupList comuniList = new LookupList(listaComuni, -1);

			comuniList.addItem(-1, "Seleziona comune");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, "Seleziona regione");
			context.getRequest().setAttribute("regioniList", regioniList);

			LineaProduttivaList listaLineeProduttive = new LineaProduttivaList();
			listaLineeProduttive.setIdMacrocategoria(1);
			ArrayList<Integer> lineeDaEscludere = new ArrayList<Integer>();

			if (getUserRole(context) == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))) {

				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				// listaLineeProduttive
				// .setIdLineaProduttivaDaEscludere(lineeDaEscludere);

			} else if (getUserRole(context) == new Integer(ApplicationProperties.getProperty("UNINA"))) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);
				lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);

			} else if (context.getRequest().getParameter("popup") != null
					&& ("true").equals((String) context.getRequest().getParameter("popup"))
					&& context.getRequest().getParameter("idLineaProduttiva1") == null) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				// int idTipologiaSoggetto =
				// if (idRegistrazione > 0 && idRegistrazione ==
				// EventoRitrovamento.idTipologiaDB){
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				// lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);
				// }

			}

			if (idRegistrazione > 00 && 
					idRegistrazione == EventoTrasferimentoSindaco.idTipologiaDB && 
					"3".equals(context.getRequest().getParameter("tipologiaSoggetto"))) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);
				lineeDaEscludere.add(LineaProduttiva.idAggregazionePrivato);
				lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);

			}
			if (idRegistrazione > 00 && 
					idRegistrazione == EventoTrasferimentoSindaco.idTipologiaDB && 
					"4".equals(context.getRequest().getParameter("tipologiaSoggetto"))) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);
				lineeDaEscludere.add(LineaProduttiva.idAggregazionePrivato);
				lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);

			}
			if (idRegistrazione > 00
					&& (idRegistrazione == EventoTrasferimento.idTipologiaDB || idRegistrazione == EventoCessione.idTipologiaDB)) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);

			}
			if (idRegistrazione > 00
					&& (idRegistrazione == EventoRientroFuoriStato.idTipologiaDB || idRegistrazione == EventoRientroFuoriStato.idTipologiaDBRandagio)
					&& (("1").equals(context.getRequest().getParameter("tipologiaSoggetto")))) {
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);
				lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);

			}
			if (idRegistrazione > 00
					&& (idRegistrazione == EventoRientroFuoriStato.idTipologiaDB || idRegistrazione == EventoRientroFuoriStato.idTipologiaDBRandagio)
					&& (("2").equals(context.getRequest().getParameter("tipologiaSoggetto")))) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);
				lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);

			}

			if (context.getRequest().getParameter("operazioneUnificazione") != null
					&& Boolean.valueOf((String) context.getRequest().getParameter("operazioneUnificazione"))) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);
				lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);

			}
			if (context.getRequest().getParameter("operazioneMovimentazione") != null
					&& Boolean.valueOf((String) context.getRequest().getParameter("operazioneMovimentazione"))) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);
				lineeDaEscludere.add(LineaProduttiva.idAggregazionePrivato);

				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);
				// lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);

			}
			
			if (context.getRequest().getParameter("operazioneTrasferimentoMassivo") != null
					&& Boolean.valueOf((String) context.getRequest().getParameter("operazioneTrasferimentoMassivo"))) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				lineeDaEscludere.add(LineaProduttiva.idAggregazionePrivato);
				lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);

				// lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);
				// lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);

			}

			if (idRegistrazione == EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
				lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);
				lineeDaEscludere.add(LineaProduttiva.idAggregazionePrivato);
			}
			
			
			// RICERCA PROPRIETARIO ORIGINE ANIMALE
			if (("8").equals(context.getRequest().getParameter("tipologiaSoggetto"))) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);

			}

			listaLineeProduttive.setIdLineaProduttivaDaEscludere(lineeDaEscludere);

			// int idTipologiaSoggetto =
			// if (idRegistrazione > 0 && idRegistrazione ==
			// EventoRitrovamento.idTipologiaDB){
			// lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
			// lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
			// lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
			// lineeDaEscludere.add(LineaProduttiva.IdAggregazioneOperatoreCommerciale);
			// lineeDaEscludere.add(LineaProduttiva.idAggregazioneImportatore);
			// lineeDaEscludere.add(LineaProduttiva.idAggregazioneCanile);
			// }

			if (context.getRequest().getParameter("idLineaProduttiva1") != null && !context.getRequest().getParameter("idLineaProduttiva1").equals("")) {
				
				int idLinea = 0;
				String idLinee = (String)context.getRequest().getParameter("idLineaProduttiva1");
				if(idLinee.contains(";"))
				{
					idLinea = new Integer(idLinee.split(";")[0]);
				}
				else
				{
					 idLinea = new Integer(idLinee);
				}
				
				
				LineaProduttiva lp = new LineaProduttiva(db, idLinea);

				
				Integer[] array = null;
				if(
						(user.getRoleId() == Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || user.getRoleId() == Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_HD2")) )  && 
						idRegistrazione == EventoCambioDetentore.idTipologiaDB)
				{
					array = new Integer[3];
					array[0] = lp.getId();
					array[1] = LineaProduttiva.idAggregazionePrivato;
					array[2] = LineaProduttiva.idAggregazioneCanile;
				}
				else
				{
					int size = 1;
					if(idLinee.contains(";"))
						size = 2;
					array = new Integer[size];
					array[0] = lp.getId();
					if(idLinee.contains(";"))
						array[1] = new Integer(idLinee.split(";")[1]);
				}
				
				listaLineeProduttive.setIdLineaProduttiva(array);

			}

			listaLineeProduttive.buildList(db);

			context.getRequest().setAttribute("ListaLineeProduttive", listaLineeProduttive);

			// reset the offset and current letter of the paged list in order to
			// make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);
			if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null
					&& !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
				StateSelect stateSelect = new StateSelect(systemStatus,
						orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
				if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
					HashMap map = new HashMap();
					map.put((String) orgListInfo.getSearchOptionValue("searchcodeContactCountry"),
							(String) orgListInfo.getSearchOptionValue("searchContactOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("ContactStateSelect", stateSelect);
			}
			if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null
					&& !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
				StateSelect stateSelect = new StateSelect(systemStatus,
						orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
				if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
					HashMap map = new HashMap();
					map.put((String) orgListInfo.getSearchOptionValue("searchcodeAccountCountry"),
							(String) orgListInfo.getSearchOptionValue("searchAccountOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("AccountStateSelect", stateSelect);
			}

			LookupList lineeProduttive = new LookupList(db, "opu_lookup_attivita_linee_produttive_aggregazioni");
			context.getRequest().setAttribute("LineeProduttiveList", lineeProduttive);
			// buildFormElements(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");

		// FLAG PER LA RICERCA PROPRIETARIO
		if (context.getRequest().getParameter("in_regione") != null)
			context.getRequest().setAttribute("in_regione",(String) context.getRequest().getParameter("in_regione"));
		if (context.getRequest().getParameter("estero") != null)
			context.getRequest().setAttribute("estero",(String) context.getRequest().getParameter("estero"));
		
		return getReturn(context, "Search");

	}

	public void buildFormElements(ActionContext context, Connection db) throws SQLException {
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
		LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
		categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);

		context.getRequest().setAttribute("TipologiaSoggetto", context.getRequest().getParameter("tipologiaSoggetto"));

		LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
		TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		context.getRequest().setAttribute("TipoLocale", TipoLocale);

		LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
		TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

		LookupList salutationList = new LookupList(db, "lookup_title");
		salutationList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("SalutationList", salutationList);

		LookupList segmentList = new LookupList(db, "lookup_segments");
		segmentList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("SegmentList", segmentList);

		LookupList industrySelect = new LookupList(db, "lookup_industry");
		industrySelect.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
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
		accountTypeList2.addItem(-1, systemStatus.getLabel("accounts.allTypes"));
		context.getRequest().setAttribute("TypeSelect", accountTypeList2);

		LookupList accountSizeList = new LookupList(db, "lookup_account_size");
		accountSizeList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("AccountSizeList", accountSizeList);

		if (index == null || (index != null && Integer.parseInt(index) == 0)) {
			LookupList phoneTypeList = new LookupList(db, "lookup_orgphone_types");
			context.getRequest().setAttribute("OrgPhoneTypeList", phoneTypeList);

			LookupList addrTypeList = new LookupList(db, "lookup_orgaddress_types");
			context.getRequest().setAttribute("OrgAddressTypeList", addrTypeList);

			LookupList emailTypeList = new LookupList(db, "lookup_orgemail_types");
			context.getRequest().setAttribute("OrgEmailTypeList", emailTypeList);

		} else {
			LookupList phoneTypeList = new LookupList(db, "lookup_contactphone_types");
			context.getRequest().setAttribute("OrgPhoneTypeList", phoneTypeList);

			LookupList addrTypeList = new LookupList(db, "lookup_contactaddress_types");
			context.getRequest().setAttribute("OrgAddressTypeList", addrTypeList);

			LookupList emailTypeList = new LookupList(db, "lookup_contactemail_types");
			context.getRequest().setAttribute("OrgEmailTypeList", emailTypeList);

		}
		// Make the StateSelect and CountrySelect drop down menus available in
		// the request.
		// This needs to be done here to provide the SystemStatus to the
		// constructors, otherwise translation is not possible
		StateSelect stateSelect = (StateSelect) context.getRequest().getAttribute("StateSelect");
		if (stateSelect == null) {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			stateSelect = new StateSelect(systemStatus, prefs.get("SYSTEM.COUNTRY"));
		}
		CountrySelect countrySelect = new CountrySelect(systemStatus);

		context.getRequest().setAttribute("StateSelect", stateSelect);
		context.getRequest().setAttribute("CountrySelect", countrySelect);
	}

	public String executeCommandSearch(ActionContext context) {
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		if (!(hasPermission(context, "proprietari_detentori_modulo-view")
				|| user.getRoleId() == Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_LLP")) || user
					.getRoleId() == Integer.valueOf(ApplicationProperties.getProperty("UNINA")))) {
			return ("PermissionError");
		}

		OperatoreList operatoreList = new OperatoreList();

		String source = (String) context.getRequest().getParameter("source");

		String partitaIva = (String) context.getRequest().getParameter("PartitaIva");
		String codiceF = (String) context.getRequest().getParameter("CodiceFiscale");
		String ragione_sociale = (String) context.getRequest().getParameter("RagioneSociale");
		context.getRequest().setAttribute("TipologiaSoggetto", context.getRequest().getParameter("tipologiaSoggetto"));
		context.getRequest().setAttribute("idLineaProduttiva1",context.getRequest().getParameter("idLineaProduttiva1"));
		
		if(context.getRequest().getParameter("tipologiaSoggetto")!=null 
				&& ((String)context.getRequest().getParameter("tipologiaSoggetto")).equals("8")){
			operatoreList.setTipologiaSoggetto(8);
		}

		String tipologia = (String) context.getRequest().getParameter("idLineaProduttiva");
		
		
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
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
		searchListInfo.setLink("OperatoreAction.do?command=Search&tipologiaSoggetto="
				+ context.getRequest().getParameter("tipologiaSoggetto")
				+ RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
		searchListInfo.setListView("all");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			if ((searchListInfo.getListView() == null || "".equals(searchListInfo.getListView()))
					&& !"searchForm".equals(source)) {
				return "ListOK";
			}

			// Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
					searchListInfo.setLink("OperatoreAction.do?command=Search&tipologiaSoggetto="
							+ context.getRequest().getParameter("tipologiaSoggetto")
							+ RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
				}
				// Build the organization list
				operatoreList.setPagedListInfo(searchListInfo);
				operatoreList.setMinerOnly(false);
				operatoreList.setTypeId(searchListInfo.getFilterKey("listFilter1"));

				operatoreList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(operatoreList, context);

				if (context.getRequest().getParameter("doContinue") != null
						&& !context.getRequest().getParameter("doContinue").equals("")
						&& context.getRequest().getParameter("doContinue").equals("false")) {

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

				if (context.getRequest().getParameter("popup") != null
						&& DatabaseUtils.parseBoolean((String) context.getRequest().getParameter("popup")) == true) {
					operatoreList.setFlagChiuso(true);
				}

				if (context.getRequest().getParameter("tipoRegistrazione") != null) {
					context.getRequest().setAttribute("tipoRegistrazione",
							context.getRequest().getParameter("tipoRegistrazione"));

				} else {
					context.getRequest().setAttribute("tipoRegistrazione", "-1");
				}
				
				//Se sto inserendo una registrazine di f.regione non ci devono essere i proprietari fuori regione
				if (context.getRequest().getParameter("tipoRegistrazione") != null && Integer.parseInt(context.getRequest().getParameter("tipoRegistrazione")) == EventoRientroFuoriRegione.idTipologiaDB) 
				{
					operatoreList.setIdAslToExclude(14);
				}
				
				if (context.getRequest().getParameter("in_regione") != null && context.getRequest().getParameter("in_regione").equals("si"))
				{
					operatoreList.setIdAslToExclude(14);
				}

				operatoreList.buildList(db);

				context.getRequest().setAttribute("OrgList", operatoreList);

				LookupList siteList = new LookupList(db, "lookup_asl_rif");
				context.getRequest().setAttribute("SiteIdList", siteList);

				LookupList tipologiaList = new LookupList(db, "opu_lookup_attivita_linee_produttive_aggregazioni");
				// registrazioniList.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("tipologiaList", tipologiaList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, ((UserBean) context.getSession()
						.getAttribute("User")).getSiteId());
				/*
				 * LookupList comuniList = new LookupList(listaComuni, -1);
				 * 
				 * 
				 * comuniList.addItem(-1, "");
				 * context.getRequest().setAttribute("ComuniList", comuniList);
				 */

				context.getSession().setAttribute("previousSearchType", "accounts");

				if (context.getRequest().getParameter("tipoRegistrazione") != null) {
					context.getRequest().setAttribute("tipoRegistrazione",
							context.getRequest().getAttribute("tipoRegistrazione"));
				} else {
					context.getRequest().setAttribute("tipoRegistrazione", "-1");
				}
				
				// FLAG PER LA RICERCA PROPRIETARIO
				if (context.getRequest().getParameter("in_regione") != null)
					context.getRequest().setAttribute("in_regione",(String) context.getRequest().getParameter("in_regione"));


				return getReturn(context, "List");
			} else {
				if ("accounts".equals(context.getSession().getAttribute("previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
					searchListInfo.setLink("OperatoreAction.do?command=Search");
				}

				context.getSession().setAttribute("previousSearchType", "contacts");

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

	public String executeCommandSendGisa(ActionContext context) {
		int idRelStabLp = -1;

		Connection db = null;
		Operatore operatore = new Operatore();
		try {

			db = super.getConnection(context);

			idRelStabLp = Integer.parseInt(context.getParameter("idRelStabLp"));

			operatore.queryRecordOperatorebyIdLineaProduttiva(db, idRelStabLp);
			Stabilimento stabilimento = null;
			if (operatore.getListaStabilimenti().size() > 0)
				stabilimento = (Stabilimento) operatore.getListaStabilimenti().get(0);

			if (stabilimento != null && stabilimento.getListaLineeProduttive().get(0) != null) {
				LineaProduttiva lpInserita = ((LineaProduttiva) stabilimento.getListaLineeProduttive().get(0));
				ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
						"applicationPrefs");

				if (lpInserita.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale
						|| lpInserita.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore) {

					GestoreComunicazioniGisa gGisa = new GestoreComunicazioniGisa();
					gGisa.inserisciOperatoreCommercialeInGisa(db, stabilimento, applicationPrefs);

				} else if (lpInserita.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile) {
					GestoreComunicazioniGisa gGisa = new GestoreComunicazioniGisa();
					gGisa.inserisciCanileInGisa(db, stabilimento, applicationPrefs);

				} else if (lpInserita.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia) {
					GestoreComunicazioniGisa gGisa = new GestoreComunicazioniGisa();
					gGisa.inserisciColoniaInGisa(db, stabilimento, applicationPrefs);
				}
			}
		} catch (Exception e) {

		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("opId", "" + operatore.getIdOperatore());
		return executeCommandDetails(context);
	}

	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "proprietari_detentori_modulo-view")
				&& !hasPermission(context, "proprietari_detentori-view")) {
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

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			String tipo = context.getRequest().getParameter("tempOpId");

			db = this.getConnection(context);

			// newOperatore = new Operatore(db, tempid);
			newOperatore = new Operatore();

			// System.out.println("Listaa Stabilimenti: "+newOperatore.getListaStabilimenti());

			// newOperatore.getListaStabilimenti().setIdAsl(user.getSiteId());
			newOperatore.queryRecordOperatorebyIdLineaProduttiva(db, tempid);

			// System.out.println("PROBLEMA (OperatoreAction)Stabilimento stab = (Stabilimento) newOperatore.getListaStabilimenti().get(0); ");
			Stabilimento stab = (Stabilimento) newOperatore.getListaStabilimenti().get(0);

			LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

			// NON PIU'
			if (lp.getIdRelazioneAttivita() == Operatore.BDU_COLONIA) {
				
			}

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// Provvisoriamente prendo tutti i comuni
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, ((UserBean) context.getSession()
					.getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);

			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			/*
			 * if (iter != null && !"".equals(iter) && "iter".equals(iter)){
			 * 
			 * return getReturn(context, "DetailsIter"); }
			 */
			//CANILE
			//CANILE - CONTROLLO OCCUPAZIONE E BLOCCO
			if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile)
			{
				double occupazioneAttuale = Stabilimento.calcolaOccupazioneStabilimento(Integer.parseInt(tempOpId), db);
				context.getRequest().setAttribute("occupazioneAttuale", occupazioneAttuale);
				int numeroCaniVivi = Stabilimento.calcolaNumeroCaniVivi(Integer.parseInt(tempOpId), db);
				context.getRequest().setAttribute("numeroCaniVivi", numeroCaniVivi);
				newOperatore.controlloBloccoCanile(Integer.parseInt(tempOpId), db);
			}			
			context.getRequest().setAttribute("OperatoreDettagli", newOperatore);

			// SETTO PARAMETRO IN CASO DI CANILE O OP COMM PER VISUALIZZAZION COORDINATE (LAT E LON)
			if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile ||
				lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale)
			{
				context.getRequest().setAttribute("showCoordinate", "true");
			}else{
				context.getRequest().setAttribute("showCoordinate", "false");				
			}
			
			if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
			{
				context.getRequest().setAttribute("ws", new WsPost(db,tempOpId,"proprietario",ApplicationProperties.getProperty("END_POINT_SINAAF")));
			
				if(context.getRequest().getParameter("errore")!=null && !context.getRequest().getParameter("errore").equals("") && !context.getRequest().getParameter("errore").equals("null"))
					context.getRequest().setAttribute("Error", context.getRequest().getParameter("errore"));
				if(context.getRequest().getParameter("messaggio")!=null && !context.getRequest().getParameter("messaggio").equals("") && !context.getRequest().getParameter("messaggio").equals("null"))
					context.getRequest().setAttribute("messaggio", context.getRequest().getParameter("messaggio"));
			}
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
	
	
	
	
	
	
	public String executeCommandBloccaSbloccaCanile(ActionContext context) throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}
		String operazione="", id_canile="";
		Connection db = null;

		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

			db = this.getConnection(context);
			
			String inserimento = context.getRequest().getParameter("inserimento");
			if (inserimento == null) {
				inserimento = (String) context.getRequest().getAttribute("inserimento");
			}
			
			if(inserimento!=null){
				Operatore op = new Operatore();
				id_canile = (String)context.getRequest().getParameter("idCanile");
				String tipo_operazione = (String)context.getRequest().getParameter("tipo_operazione");
				String data_st = (String)context.getRequest().getParameter("dataEffettiva");
    			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			    Date parsedDate = dateFormat.parse(data_st);
			    Timestamp data = new java.sql.Timestamp(parsedDate.getTime());
				String motivo = (String)context.getRequest().getParameter("motivo");
				String note = (String)context.getRequest().getParameter("note");
				
				if(tipo_operazione.equals("blocca")){
					op.setBloccato(true);
					op.setData_sospensione_blocco(data);
				}else{
					op.setBloccato(false);
					op.setData_riattivazione_blocco(data);				
				}
				op.setMotivo_blocco(motivo);
				op.setNote_blocco(note);
				op.setUser_id_blocco(user.getUserId());
				// IN CASO DI BLOCCO, CONTROLLO SE SONO STATE EFFETTUATE OPERAZIONI IN DATA POSTERIORE ALLA
				// DATA DI BLOCCO
				if(op.isBloccato()){
					String esito="";
					esito=op.controlloRegistrazioneGiaEffettuateInCanileBloccato(Integer.parseInt(id_canile), db, data.toString());
					if (!esito.equals("")){
						String data_inizio_attivita = (String)context.getRequest().getParameter("data_inizio_attivita");
						String data_operazione = (String)context.getRequest().getParameter("data_operazione");
						context.getRequest().setAttribute("operazione", operazione);
						context.getRequest().setAttribute("data_inizio_attivita", data_inizio_attivita);
						context.getRequest().setAttribute("data_minima_operazione", data_operazione);
						context.getRequest().setAttribute("id_canile", id_canile);
						context.getRequest().setAttribute("ErrorBlocco",
								"ATTENZIONE! Non è possibile bloccare il canile con data da "+data.toString().substring(0,10)+" poiché sono state effettuate le seguenti operazioni:\\n"+esito);

						return "BloccaSbloccaOK";
					}
				}
				op.inserisciBloccoSblocco(Integer.parseInt(id_canile), db);
				context.getRequest().setAttribute("esito_inserimento", "ok");
				context.getRequest().setAttribute("id_canile", id_canile);			
			}else{
				operazione = (String)context.getRequest().getParameter("operazione");
				id_canile = (String)context.getRequest().getParameter("idCanile");

				if(operazione.equals("annulla")){
					Operatore op = new Operatore();
					op.annullaBloccoSblocco(Integer.parseInt(id_canile), db);
					
				}else{
					String data_inizio_attivita = (String)context.getRequest().getParameter("data_inizio_attivita");
					String data_operazione = (String)context.getRequest().getParameter("data_operazione");
		
					context.getRequest().setAttribute("operazione", operazione);
					context.getRequest().setAttribute("data_inizio_attivita", data_inizio_attivita);
					context.getRequest().setAttribute("data_minima_operazione", data_operazione);
					context.getRequest().setAttribute("id_canile", id_canile);
					//context.getRequest().setAttribute("coloniaDaModificare", coloniaDaModificare);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if(operazione.equals("annulla")){
			context.getRequest().setAttribute("opId", id_canile);
			return executeCommandDetails(context);
		}else{
			return "BloccaSbloccaOK";
		}

	}

	
	
	
	
	

	public String executeCommandPreparaModificaDimensioneColonia(ActionContext context) throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}
		Operatore operatoreToModify = null;
		String opId = context.getRequest().getParameter("opId");
		if (System.getProperty("DEBUG") != null)
			System.out.println("opId in Modify: " + opId);

		Connection db = null;

		try {

			db = this.getConnection(context);

			context.getRequest().setAttribute("opId", opId);
			int tempid = Integer.parseInt(opId);
			// operatoreToModify = new Operatore(db, tempid);
			operatoreToModify = new Operatore();
			operatoreToModify.queryRecordOperatorebyIdLineaProduttiva(db, tempid);

			Stabilimento stab = (Stabilimento) operatoreToModify.getListaStabilimenti().get(0);
			ColoniaInformazioni coloniaDaModificare = (ColoniaInformazioni) stab.getListaLineeProduttive().get(0);
			context.getRequest().setAttribute("operatoreColonia", operatoreToModify);
			context.getRequest().setAttribute("stabilimentoColonia", stab);
			context.getRequest().setAttribute("coloniaDaModificare", coloniaDaModificare);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("AslList", siteList);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return "ModifyColoniaOK";

	}

	public String executeCommandModificaDimensioneColonia(ActionContext context) throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}
		Operatore operatoreToModify = null;
		String opId = context.getRequest().getParameter("opId");
		if (System.getProperty("DEBUG") != null)
			System.out.println("opId in Modify: " + opId);

		Connection db = null;

		try {

			RegistrazioneModificaDimensioneColonia dettagli_registrazione = (RegistrazioneModificaDimensioneColonia) context
					.getRequest().getAttribute("RegistrazioneModificaDimensioneColonia");
			dettagli_registrazione.setEnteredby(getUserId(context));
			dettagli_registrazione.setModifiedby(getUserId(context));
			dettagli_registrazione.setIdAslInserimentoRegistrazione(getUserAsl(context));
			dettagli_registrazione
					.setIdTipologiaRegistrazioneOperatore(RegistrazioneModificaDimensioneColonia.idTipologia);
			db = this.getConnection(context);

			dettagli_registrazione.insert(db);

			int tempid = Integer.parseInt(opId);
			// operatoreToModify = new Operatore(db, tempid);
			operatoreToModify = new Operatore();
			operatoreToModify.queryRecordOperatorebyIdLineaProduttiva(db, tempid);

			Stabilimento stab = (Stabilimento) operatoreToModify.getListaStabilimenti().get(0);
			ColoniaInformazioni coloniaDaModificare = (ColoniaInformazioni) stab.getListaLineeProduttive().get(0);
			context.getRequest().setAttribute("operatoreColonia", operatoreToModify);
			context.getRequest().setAttribute("stabilimentoColonia", stab);
			context.getRequest().setAttribute("coloniaDaModificare", coloniaDaModificare);

			coloniaDaModificare.setNrGattiTotale(dettagli_registrazione.getNuovaDimensione());
			coloniaDaModificare.aggiornaDimensioneColonia(db);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("opId", opId);

		return executeCommandDetails(context);

	}

	String getStringFromBoolean(boolean boolValue) {
		String ret = "No";
		if (boolValue)
			ret = "Sì";

		return ret;
	}

	public String executeCommandModifyTotale(ActionContext context) throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}

		Operatore operatoreToModify = null;
		String opId = context.getRequest().getParameter("opId");
		if (opId != null && !"".equals(opId))
			context.getRequest().setAttribute("opId", opId);

		Connection db = null;
		try {
			db = this.getConnection(context);
			if (opId != null && !"".equals(opId)) {
				int tempid = Integer.parseInt(opId);
				operatoreToModify = new Operatore();
				operatoreToModify.getListaStabilimenti().setIdAsl(
						((UserBean) context.getSession().getAttribute("User")).getSiteId());
				operatoreToModify.queryRecordOperatorebyIdLineaProduttiva(db, tempid);
			} else {
				operatoreToModify = (Operatore) context.getFormBean();
			}
			context.getRequest().setAttribute("Operatore", operatoreToModify);
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,
					((UserBean) context.getSession().getAttribute("User")).getSiteId(), 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "Seleziona comune");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, "Seleziona regione");
			context.getRequest().setAttribute("regioniList", regioniList);

			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "Seleziona provincia");
			context.getRequest().setAttribute("provinceList", provinceList);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return "ModifyTotaleOK";

	}

	public String executeCommandUpdateTotale(ActionContext context) throws SQLException {

		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}

		int idLineaProduttiva = Integer.parseInt(context.getParameter("idLineaProduttiva")); // catturo
																								// id
																								// della
																								// linea
																								// produttiva
																								// dalla
																								// request
		int opId = Integer.parseInt(context.getParameter("opId")); // id della
																	// relazione
																	// opu_stabilimento_linee_produttive
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		int resultCount = -1;
		Operatore newOperatore = null;
		Operatore oldOperatore = null;
		// SoggettoFisico oldSoggetto=null;
		SoggettoFisico soggettoAdded = null;
		Indirizzo indirizzoAdded = null;
		Stabilimento stab = null;
		LineaProduttiva lp = null;
		int oldIndirizzoId = -1;
		int id_rapp_legale = -1; // usato per gestire casi in cui è uguale al
									// responsabile
		UserBean utente = (UserBean) context.getSession().getAttribute("User");
		int user_id = utente.getUserRecord().getId();
		String user_ip = utente.getUserRecord().getIp();

		try {
			db = this.getConnection(context);
			db.setAutoCommit(false);
			newOperatore = new Operatore();
			newOperatore.queryRecordOperatorebyIdLineaProduttiva(db, opId);
			oldOperatore = new Operatore();
			oldOperatore.queryRecordOperatorebyIdLineaProduttiva(db, opId);
			newOperatore.setModifiedBy(getUserId(context));
			stab = (Stabilimento) newOperatore.getListaStabilimenti().get(0);
			lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

			if (idLineaProduttiva == LineaProduttiva.idAggregazionePrivato
					|| idLineaProduttiva == LineaProduttiva.idAggregazioneSindaco
					|| idLineaProduttiva == LineaProduttiva.idAggregazioneSindacoFR) {

				if (context.getRequest().getParameter("note") != null)
					newOperatore.setNote(context.getRequest().getParameter("note"));
				if (context.getRequest().getParameter("nome") != null
						&& context.getRequest().getParameter("cognome") != null)
					newOperatore.setRagioneSociale(context.getRequest().getParameter("cognome") + ", "
							+ context.getRequest().getParameter("nome"));
				if (context.getRequest().getParameter("codFiscaleSoggettoTesto") != null)
					newOperatore.setCodFiscale(context.getRequest().getParameter("codFiscaleSoggettoTesto"));
				if (context.getRequest().getParameter("codFiscaleSoggettoTesto") != null)
					newOperatore.setPartitaIva(context.getRequest().getParameter("codFiscaleSoggettoTesto"));

				// gestione del soggetto fisico
				if (new Integer(context.getRequest().getParameter("idSoggetto")).intValue() > 0)
					soggettoAdded = new SoggettoFisico(context.getRequest(), 1); // 1:
																					// privato/rapp
																					// legale
				else
					soggettoAdded = new SoggettoFisico(context.getRequest(), db, 1);

				soggettoAdded.setModifiedBy(user_id);
				soggettoAdded.setIpModifiedBy(user_ip);

				if (soggettoAdded.verificaPresenzaCf(db) != -1) {
					context.getRequest()
							.setAttribute(
									"Errore",
									"Attenzione! Il codice fiscale "
											+ soggettoAdded.getCodFiscale()
											+ " selezionato risulta già presente in banca dati ed assegnato ad un altro soggetto fisico.");
					return executeCommandModifyTotale(context);
				}

				soggettoAdded.update(db,context.getRequest().getParameter("edit_legale") != null); // aggiorno soggetto fisico con i
											// campi popolati da maschera

				newOperatore.setRappLegale(soggettoAdded); // setto soggetto
															// fisico come rapp.
															// legale

				// gestione dell'indirizzo
				oldIndirizzoId = newOperatore.getSedeLegale().getIdIndirizzo();
				indirizzoAdded = null;
				if (new Integer(context.getRequest().getParameter("via")).intValue() > 0) {
					int test = new Integer(context.getRequest().getParameter("via")).intValue();
					indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
					indirizzoAdded.setTipologiaSede(1); // Legale
				} else {
					indirizzoAdded = new Indirizzo(context.getRequest(), db);
					indirizzoAdded.setTipologiaSede(1);

				}
				// Tieni traccia della modifica
				if (oldIndirizzoId != indirizzoAdded.getIdIndirizzo())
					indirizzoAdded.insertModificaIndirizzo(db, lp.getId(), oldIndirizzoId,
							indirizzoAdded.getIdIndirizzo(), user_id, 1); // 1:
																			// legale

				// Aggiorna sede operativa (=sede legale)

				stab = (Stabilimento) newOperatore.getListaStabilimenti().get(0);
				stab.setModifiedBy(user_id);
				stab.setSedeOperativa(indirizzoAdded); // SEDE OPERATIVA=SEDE
														// LEGALE

				// Aggiornamento ASL
				Object[] asl;
				if (!(idLineaProduttiva == LineaProduttiva.idAggregazioneSindacoFR) && !stab.isFlagFuoriRegione()) // Se
																													// nn
																													// sto
																													// considerando
																													// un
																													// sindaco
																													// fuori
																													// regione
																													// e
																													// nemmeno
																													// un
																													// proprietario
																													// di
																													// tipo
																													// fuori
																													// regione
					asl = DwrUtil.getValoriAsl(indirizzoAdded.getComune());
				else
					asl = null;

				if (asl != null && asl.length > 0) {

					Object[] aslVal = (Object[]) asl[0];
					if (aslVal != null && aslVal.length > 0) {
						stab.setIdAsl((Integer) aslVal[0]);
						// SETTO A MANO L'ID ASL DELLA SEDE OPERATICA
						// (CORRETTO?)
						stab.getSedeOperativa().setIdAsl((Integer) aslVal[0]);
					}

				} else if (idLineaProduttiva == LineaProduttiva.idAggregazioneSindacoFR) {
					stab.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
				} else if (stab.isFlagFuoriRegione()) {
					stab.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
					// SETTO A MANO L'ID ASL DELLA SEDE OPERATICA (CORRETTO?)
					stab.getSedeOperativa().setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
				}
				stab.updateSedeOperativa(db);
				newOperatore.update(db,context.getRequest().getParameter("edit_legale") != null);
				newOperatore.aggiornaRelazioneSede(db, newOperatore.getSedeLegale(), indirizzoAdded);
			}

			else if (idLineaProduttiva == LineaProduttiva.idAggregazioneCanile
					|| idLineaProduttiva == LineaProduttiva.IdAggregazioneOperatoreCommerciale
					|| idLineaProduttiva == LineaProduttiva.idAggregazioneImportatore
					|| idLineaProduttiva == LineaProduttiva.idAggregazioneColonia) {

				// gestione informazioni operatore
				if (context.getRequest().getParameter("ragioneSociale") != null)
					newOperatore.setRagioneSociale(context.getRequest().getParameter("ragioneSociale"));
				if (context.getRequest().getParameter("partitaIva") != null)
					newOperatore.setPartitaIva(context.getRequest().getParameter("partitaIva"));
				if (context.getRequest().getParameter("codFiscale") != null)
					newOperatore.setCodFiscale(context.getRequest().getParameter("codFiscale"));
				if (context.getRequest().getParameter("note") != null)
					newOperatore.setNote(context.getRequest().getParameter("note"));

				if (idLineaProduttiva != LineaProduttiva.idAggregazioneColonia) { // colonie:
																					// non
																					// hanno
																					// dati
																					// legali

					// gestione rappresentante legale
					if (new Integer(context.getRequest().getParameter("idSoggetto")).intValue() > 0) // recupero
																										// dati
																										// sul
																										// soggetto
																										// fisico
						soggettoAdded = new SoggettoFisico(context.getRequest(), 4);
					else
						soggettoAdded = new SoggettoFisico(context.getRequest(), db, 4);

					soggettoAdded.setModifiedBy(user_id);
					soggettoAdded.setIpModifiedBy(user_ip);

					if (soggettoAdded.verificaPresenzaCf(db) != -1) {
						context.getRequest()
								.setAttribute(
										"Errore",
										"Attenzione! Il codice fiscale "
												+ soggettoAdded.getCodFiscale()
												+ " selezionato risulta già presente in banca dati ed assegnato ad un altro soggetto fisico.");
						return executeCommandModifyTotale(context);
					}

					// gestione indirizzo rappresentante legale
					Indirizzo oldIndirizzo = new Indirizzo(db, newOperatore.getRappLegale().getIndirizzo()
							.getIdIndirizzo());
					oldIndirizzoId = oldIndirizzo.getIdIndirizzo();

					indirizzoAdded = null;
					if (new Integer(context.getRequest().getParameter("idViaRappresentante")).intValue() > 0) {
						int test = new Integer(context.getRequest().getParameter("idViaRappresentante")).intValue();
						indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter(
								"idViaRappresentante")).intValue());
						indirizzoAdded.setTipologiaSede(1); // Legale
					} else {
						indirizzoAdded = new Indirizzo(context.getRequest(), db, 4);
						indirizzoAdded.setTipologiaSede(1);
					}
					if (oldIndirizzoId != indirizzoAdded.getIdIndirizzo())
						indirizzoAdded.insertModificaIndirizzo(db, lp.getId(), oldIndirizzoId,
								indirizzoAdded.getIdIndirizzo(), user_id, 4); // 4:
																				// rappresentante
																				// legale

					soggettoAdded.setIndirizzo(indirizzoAdded);

					// Inizio controllo che il soggetto non abbia associata una
					// linea produttiva, in tal caso non posso cambiargli ASL

					// SoggettoFisico soggettoEsistente = null;
					// soggettoEsistente = soggettoAdded.verificaSoggetto(db);
					//
					// /* se il soggetto non esiste lo aggiungo */
					//
					// if (soggettoEsistente == null
					// || soggettoEsistente.getIdSoggetto() <= 0)
					// soggettoAdded.update(db);
					// else {
					// if (soggettoEsistente.getIdSoggetto() > 0) {
					// indirizzoAdded = soggettoAdded.getIndirizzo();
					// Indirizzo indirizzoEsistente = soggettoEsistente
					// .getIndirizzo();
					// /* se l'asl di residenza non coincide */
					// if (indirizzoAdded.calcolaAsl(db) != indirizzoEsistente
					// .calcolaAsl(db)) {
					// // newOperatore.setRappLegale(soggettoAdded);
					// context.getRequest().setAttribute("Errore",
					// "Attenzione! Impossibile effettuare il cambio di residenza per il soggetto ("+soggettoAdded.getCodFiscale()+") in quanto già censito per una diversa asl. Selezionare un comune appartenente all\'asl attuale o contattare l\'HelpDesk.");
					// context.getRequest().setAttribute("Operatore",
					// newOperatore);
					// context.getRequest().setAttribute(
					// "soggettoEsistente", soggettoEsistente);
					// return executeCommandModifyTotale(context);
					// }
					// }
					// }

					// FINE

					soggettoAdded.update(db); // aggiorno soggetto fisico
					newOperatore.setRappLegale(soggettoAdded); // setto soggetto
																// fisico come
																// rapp. legale
					id_rapp_legale = soggettoAdded.getIdSoggetto();

					// gestione indirizzo sede legale
					oldIndirizzoId = newOperatore.getSedeLegale().getIdIndirizzo(); // salvo
																					// il
																					// vecchio
																					// indirizzo
					indirizzoAdded = null;
					if (new Integer(context.getRequest().getParameter("via")).intValue() > 0) {
						int test = new Integer(context.getRequest().getParameter("via")).intValue();
						indirizzoAdded = new Indirizzo(db,
								new Integer(context.getRequest().getParameter("via")).intValue());
						indirizzoAdded.setTipologiaSede(1); // Legale
					} else {
						indirizzoAdded = new Indirizzo(context.getRequest(), db);
						indirizzoAdded.setTipologiaSede(1);
					}

					// Se l'indirizzo è cambiato, tieni traccia

					if (oldIndirizzoId != indirizzoAdded.getIdIndirizzo())
						indirizzoAdded.insertModificaIndirizzo(db, lp.getId(), oldIndirizzoId,
								indirizzoAdded.getIdIndirizzo(), user_id, 1); // 1:
																				// legale

					newOperatore.getListaSediOperatore().add(indirizzoAdded);

				}
				// gestione indirizzo sede operativa

				newOperatore.update(db);
				indirizzoAdded = null;
				if (new Integer(context.getRequest().getParameter("idViaSedeOperativa")).intValue() > 0) {
					int test = new Integer(context.getRequest().getParameter("idViaSedeOperativa")).intValue();
					indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter(
							"idViaSedeOperativa")).intValue());
					indirizzoAdded.setTipologiaSede(1); // Legale
				} else {
					indirizzoAdded = new Indirizzo(context.getRequest(), db, 1);

					// RECUPERO INFO ASL
					Object[] asl;
					asl = DwrUtil.getValoriAsl(indirizzoAdded.getComune());
					if (asl != null && asl.length > 0) {

						Object[] aslVal = (Object[]) asl[0];
						if (aslVal != null && aslVal.length > 0)
							indirizzoAdded.setIdAsl((Integer) aslVal[0]);
						else
							indirizzoAdded.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
					}
					indirizzoAdded.setTipologiaSede(1);

				}

				stab = (Stabilimento) newOperatore.getListaStabilimenti().get(0);
				stab.setModifiedBy(user_id);

				// SE L'INDIRIZZO E' CAMBIATO, TIENI TRACCIA DELLA MODIFICA
				oldIndirizzoId = stab.getSedeOperativa().getIdIndirizzo();

				if (oldIndirizzoId != indirizzoAdded.getIdIndirizzo())
					indirizzoAdded.insertModificaIndirizzo(db, lp.getId(), oldIndirizzoId,
							indirizzoAdded.getIdIndirizzo(), user_id, 2); // 2:
																			// operativa

				stab.setSedeOperativa(indirizzoAdded);
				int idIndirizzo = indirizzoAdded.getIdIndirizzo();

				Object[] asl;
				if (!(idLineaProduttiva == LineaProduttiva.idAggregazioneSindacoFR) && !stab.isFlagFuoriRegione()) // Se
																													// nn
																													// sto
																													// considerando
																													// un
																													// sindaco
																													// fuori
																													// regione
					// e nemmeno un proprietario di tipo fuori regione
					asl = DwrUtil.getValoriAsl(indirizzoAdded.getComune());
				else
					asl = null;

				if (asl != null && asl.length > 0) {

					Object[] aslVal = (Object[]) asl[0];
					if (aslVal != null && aslVal.length > 0)
						stab.setIdAsl((Integer) aslVal[0]);

				} else if (idLineaProduttiva == LineaProduttiva.idAggregazioneSindacoFR) {
					stab.setIdAsl(-1);
				} else if (stab.isFlagFuoriRegione()) {
					stab.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
				}

				String idRegione = context.getRequest().getParameter("idRegioneSedeOperativa");
				if (idRegione != null) {
					int regione = new Integer(idRegione).intValue();
					if (regione != 15) {
						stab.setFlagFuoriRegione(true);
						indirizzoAdded.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
						stab.setIdAsl(-1);
					} else
						stab.setFlagFuoriRegione(false);
				} else if (idRegione == null) {
					if (indirizzoAdded.getIdAsl() == 0 || indirizzoAdded.getIdAsl() == Constants.ID_ASL_FUORI_REGIONE)
						stab.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
				}
				stab.updateSedeOperativa(db);

				/*
				 * if (asl_originale != stab.getIdAsl()){ //SE E' STATA CAMBIATA
				 * L'ASL, AGGIORNA L'ASL DI TUTTI GLI ANIMALI ASSOCIATI
				 * AnimaleList listAnimali = new AnimaleList();
				 * listAnimali.setId_proprietario_o_detentore(opId);
				 * listAnimali.setFlagDecesso(false); //CERCA SOLO ANIMALI VIVI
				 * listAnimali.setBuildProprietario(false);
				 * listAnimali.setMinerOnly(false); listAnimali.buildList(db);
				 * System.out.println(listAnimali.size()); Animale
				 * thisAnimale=null; for (int i=0; i<listAnimali.size();i++){
				 * thisAnimale= (Animale)listAnimali.get(i);
				 * System.out.println(thisAnimale.getMicrochip()); }
				 * Animale.updateAslAnimaliProprietario(listAnimali,
				 * stab.getIdAsl(), db);
				 * 
				 * }
				 */

				if (idLineaProduttiva == LineaProduttiva.idAggregazioneCanile) {
					// gestione informazioni canile

					String autorizzazione = context.getRequest().getParameter("autorizzazione");
					String dataAutorizzazione = context.getRequest().getParameter("dataAutorizzazione");
					String abusivo = context.getRequest().getParameter("abusivo");
					String centroSterilizzazione = context.getRequest().getParameter("centroSterilizzazione");
					String municipale = context.getRequest().getParameter("municipale");
					String mqDisponibili = context.getRequest().getParameter("mqDisponibili");
					String flagClinicaOspedale = context.getRequest().getParameter("flagClinicaOspedale");
					CanileInformazioni canileInfo = (CanileInformazioni) lp;

					// salvo vecchi parametri
					String oldAutorizzazione = canileInfo.getAutorizzazione();
					Timestamp oldDataAutorizzazione = canileInfo.getDataAutorizzazione();
					boolean oldAbusivo = canileInfo.isAbusivo();
					boolean oldCentroSterilizzazione = canileInfo.isCentroSterilizzazione();
					boolean oldMunicipale = canileInfo.isMunicipale();

					canileInfo.setAutorizzazione(autorizzazione);
					canileInfo.setDataAutorizzazione(dataAutorizzazione);
					canileInfo.setAbusivo(abusivo);
					canileInfo.setCentroSterilizzazione(centroSterilizzazione);
					canileInfo.setMunicipale(municipale);
					canileInfo.setModifiedBy(user_id);
					canileInfo.setMqDisponibili(mqDisponibili);
					canileInfo.setFlagClinicaOspedale(flagClinicaOspedale);

					canileInfo.salvaModificheCanile(db, oldAutorizzazione, oldDataAutorizzazione, oldAbusivo,
							oldCentroSterilizzazione, oldMunicipale, canileInfo, lp.getId(), user_id);
					canileInfo.update(db);

				}

				else if (idLineaProduttiva == LineaProduttiva.idAggregazioneColonia) {
					// gestione informazioni colonia
					String nrProtocollo = context.getRequest().getParameter("nrProtocollo");
					String dataRegistrazioneColonia = context.getRequest().getParameter("dataRegistrazioneColonia");
					ColoniaInformazioni coloniaInfo = (ColoniaInformazioni) lp;

					String oldNrProtocollo = coloniaInfo.getNrProtocollo();
					Timestamp oldDataRegistrazione = coloniaInfo.getDataRegistrazioneColonia();

					coloniaInfo.setNrProtocollo(nrProtocollo);
					coloniaInfo.setDataRegistrazioneColonia(dataRegistrazioneColonia);
					coloniaInfo.setModifiedBy(user_id);
					coloniaInfo.salvaModificheColonia(db, oldNrProtocollo, oldDataRegistrazione, coloniaInfo,
							lp.getId(), user_id);
					coloniaInfo.update(db);
				}

				// gestione responsabile stabilimento

				// oldSoggetto = stab.getRappLegale();
				soggettoAdded = null;
				if (new Integer(context.getRequest().getParameter("idSoggettoResp")).intValue() > 0)
					soggettoAdded = new SoggettoFisico(context.getRequest(), 2);
				else
					soggettoAdded = new SoggettoFisico(context.getRequest(), db, 2);

				soggettoAdded.setModifiedBy(user_id);
				soggettoAdded.setIpModifiedBy(user_ip);

				if (soggettoAdded.verificaPresenzaCf(db) != -1) {
					context.getRequest()
							.setAttribute(
									"Errore",
									"Attenzione! Il codice fiscale "
											+ soggettoAdded.getCodFiscale()
											+ " selezionato risulta già presente in banca dati ed assegnato ad un altro soggetto fisico.");
					return executeCommandModifyTotale(context);
				}

				// gestione indirizzo responsabile stabilimento
				Indirizzo oldIndirizzo = new Indirizzo(db, stab.getRappLegale().getIndirizzo().getIdIndirizzo());
				oldIndirizzoId = oldIndirizzo.getIdIndirizzo();
				/*
				 * String sql =
				 * "SELECT indirizzo_id from opu_soggetto_fisico where id="
				 * +soggettoAdded.getIdSoggetto(); PreparedStatement pst =
				 * db.prepareStatement(sql); ResultSet rs = pst.executeQuery();
				 * String oldId=""; while (rs.next()) { oldId =
				 * rs.getString("indirizzo_id"); } oldIndirizzoId =
				 * Integer.parseInt(oldId);
				 */
				// non riuscivo a recuperare info
				indirizzoAdded = null;
				if (new Integer(context.getRequest().getParameter("idViaResponsabile")).intValue() > 0) {
					int test = new Integer(context.getRequest().getParameter("idViaResponsabile")).intValue();
					indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter(
							"idViaResponsabile")).intValue());
					indirizzoAdded.setTipologiaSede(1); // Legale
				} else {
					indirizzoAdded = new Indirizzo(context.getRequest(), db, 2);
					indirizzoAdded.setTipologiaSede(1);
				}

				soggettoAdded.setIndirizzo(indirizzoAdded);
				soggettoAdded.setModifiedBy(user_id);
				soggettoAdded.setIpModifiedBy(user_ip);

				// Inizio controllo che il soggetto non abbia associata una
				// linea produttiva, in tal caso non posso cambiargli ASL

				// SoggettoFisico soggettoEsistente = null;
				// soggettoEsistente = soggettoAdded.verificaSoggetto(db);
				//
				// /* se il soggetto non esiste lo aggiungo */
				//
				// if (soggettoEsistente == null
				// || soggettoEsistente.getIdSoggetto() <= 0)
				// if (soggettoAdded.getIdSoggetto() != id_rapp_legale) // SE
				// // RESP
				// // STABILIMENTO
				// // E'
				// // DIVERSO
				// // DA
				// // RAPP
				// // LEGALE,
				// // AGGIORNA
				// soggettoAdded.update(db);
				// else {
				// if (soggettoEsistente.getIdSoggetto() > 0) {
				// indirizzoAdded = soggettoAdded.getIndirizzo();
				// Indirizzo indirizzoEsistente = soggettoEsistente
				// .getIndirizzo();
				// /* se l'asl di residenza non coincide */
				// if (indirizzoAdded.calcolaAsl(db) != indirizzoEsistente
				// .calcolaAsl(db)) {
				// // newOperatore.setRappLegale(soggettoAdded);
				// context.getRequest().setAttribute("Errore",
				// "Attenzione! Impossibile effettuare il cambio di residenza per il soggetto ("+soggettoAdded.getCodFiscale()+") in quanto già censito per una diversa asl. Selezionare un comune appartenente all\'asl attuale o contattare l\'HelpDesk.");
				// context.getRequest().setAttribute("Operatore",
				// newOperatore);
				// context.getRequest().setAttribute(
				// "soggettoEsistente", soggettoEsistente);
				// return executeCommandModifyTotale(context);
				// }
				// }
				// }

				// FINE

				if (soggettoAdded.getIdSoggetto() != id_rapp_legale) // SE RESP
																		// STABILIMENTO
																		// E'
																		// DIVERSO
																		// DA
																		// RAPP
																		// LEGALE,
																		// AGGIORNA
					soggettoAdded.update(db);
				// else
				// soggettoAdded.updateSoloIndirizzo(db); //ALTRIMENTI AGGIORNA
				// SOLO L'INDIRIZZO

				// SE L'INDIRIZZO E' CAMBIATO, TIENI TRACCIA DELLA MODIFICA

				if (oldIndirizzoId != indirizzoAdded.getIdIndirizzo())
					indirizzoAdded.insertModificaIndirizzo(db, lp.getId(), oldIndirizzoId,
							indirizzoAdded.getIdIndirizzo(), user_id, 3); // 3:
																			// responsabile
																			// stabilimento

				stab.setRappLegale(soggettoAdded);
				int idSoggetto = soggettoAdded.getIdSoggetto();
				stab.updateResponsabile(db, idSoggetto);

				if (idLineaProduttiva == LineaProduttiva.idAggregazioneColonia) {
					// SE E' UNA COLONIA, AGGIORNA L'OPERATORE CON RAGIONE
					// SOCIALE E P.IVA DEL TUTORE
					newOperatore.setRagioneSociale(soggettoAdded.getCognome() + ", " + soggettoAdded.getNome());
					newOperatore.setPartitaIva(soggettoAdded.getCodFiscale());
					newOperatore.setCodFiscale(soggettoAdded.getCodFiscale());
					newOperatore.updateSoloRagioneSociale(db);

				}

			}

			// Aggiornamento recapiti telefonici su linea produttiva

			String oldTelefono = lp.getTelefono1();
			String oldAutorizzazione = lp.getAutorizzazione();
			if (context.getRequest().getParameter("autorizzazione") != null)
				lp.setAutorizzazione(context.getRequest().getParameter("autorizzazione"));
			if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindacoFR) {
				lp.setTelefono1(soggettoAdded.getTelefono1());
				lp.setTelefono2(soggettoAdded.getTelefono2());
				lp.setId(context.getRequest().getParameter("opId"));
				lp.setFax(soggettoAdded.getFax());
				lp.setMail1(soggettoAdded.getEmail());

			} else {
				lp.setTelefono1(context.getRequest().getParameter("telefono1_lp"));
				lp.setTelefono2(context.getRequest().getParameter("telefono2_lp"));
				lp.setId(context.getRequest().getParameter("opId"));
				lp.setFax(context.getRequest().getParameter("fax_lp"));
				lp.setMail1(context.getRequest().getParameter("email_lp"));
			}

			String noteInternalUseOnly = context.getRequest().getParameter("noteInternalUseOnly");
			String oldNoteInternalUseOnly = lp.getNoteInternalUseOnly();

			if (noteInternalUseOnly != null && !noteInternalUseOnly.equals(oldNoteInternalUseOnly)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				if (!noteInternalUseOnly.equals(""))
					lp.setNoteInternalUseOnly("[" + dateFormat.format(new Date()) + "] " + noteInternalUseOnly);
				else
					lp.setNoteInternalUseOnly("");
			}
			lp.salvaModificheLinea(db, oldTelefono, oldAutorizzazione, oldNoteInternalUseOnly, lp, lp.getId(), user_id);
			lp.aggiornaRecapiti(db);

			// Aggiornamento vista materializzata
			newOperatore.aggiornaVistaMaterializzata(db);

			// Salvataggio storico modifiche operatore
			newOperatore.salvaModificheOperatore(db, oldOperatore, newOperatore, lp.getId(), user_id);

			// Aggiornamento informazioni su GISA
			ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
					"applicationPrefs");

			if (lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore) {

				GestoreComunicazioniGisa gGisa = new GestoreComunicazioniGisa();
				gGisa.aggiornaOperatoreCommercialeInGisa(db, stab, applicationPrefs);

			} else if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile) {
				GestoreComunicazioniGisa gGisa = new GestoreComunicazioniGisa();
				gGisa.aggiornaCanileInGisa(db, stab, applicationPrefs);

			} else if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia) {
				GestoreComunicazioniGisa gGisa = new GestoreComunicazioniGisa();
				gGisa.aggiornaColoniaInGisa(db, stab, applicationPrefs);
			}

		} catch (Exception errorMessage) {
			db.rollback();
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			db.setAutoCommit(true);
			// db.close();
			this.freeConnection(context, db);
		}

		// //DEVO AGGIORNARE EVENTUALMENTE ASL DI TUTTI I CANI
		//
		// AnimaleList listaAnimaliPropr = new AnimaleList();
		// listaAnimaliPropr.setId_proprietario(opId);
		// try {
		// listaAnimaliPropr.buildList(db);
		// Animale.updateAslAnimaliProprietario(listaAnimaliPropr,
		// stab.getIdAsl(), db);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// newOperatore.aggiornaVistaMaterializzata(db);

		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null) {
				return ("ClosePopupOK");
			}
			if (target != null && "add_contact".equals(target)) {
				return ("InsertAndAddContactOK");
			} else if (context.getRequest().getParameter("iterId") != null
					&& !context.getRequest().getParameter("iterId").equals("-1")) {
				// gestione iter
				return ("InsertIter");
			}
		}

		return (executeCommandDetails(context));
	}

	public String executeCommandPreparaModificaIndirizzoOperatore(ActionContext context) throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}
		Operatore operatoreToModify = null;
		String opId = context.getRequest().getParameter("opId");
		// System.out.println("opId in Modify: " + opId);

		Connection db = null;

		try {

			db = this.getConnection(context);

			context.getRequest().setAttribute("opId", opId);
			int tempid = Integer.parseInt(opId);
			// operatoreToModify = new Operatore(db, tempid);
			operatoreToModify = new Operatore();
			operatoreToModify.queryRecordOperatorebyIdLineaProduttiva(db, tempid);

			context.getRequest().setAttribute("operatoreToModify", operatoreToModify);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList regioni = new LookupList(db, "lookup_regione");
			regioni.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			regioni.removeElementByCode(15);
			context.getRequest().setAttribute("regioni", regioni);

			Provincia p = new Provincia();
			ArrayList<Provincia> listaProvince = p.getProvince(db, "", null,null);
			LookupList province = new LookupList(listaProvince, -1);
			province.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("province", province);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 3);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");

			context.getRequest().setAttribute("comuni", comuniList);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return "ModifyIndirizzoOperatoreOK";

	}

	public String executeCommandModificaIndirizzoOperatore(ActionContext context) throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}
		Operatore operatoreToModify = null;
		String opId = context.getRequest().getParameter("idRelazioneStabilimentoLineaProduttiva");
		// System.out.println("opId in Modifica indirizzo: " + opId);

		Connection db = null;

		try {

			RegistrazioneModificaIndirizzoOperatore dettagli_registrazione = (RegistrazioneModificaIndirizzoOperatore) context
					.getRequest().getAttribute("RegistrazioneModificaIndirizzoOperatore");
			dettagli_registrazione.setEnteredby(getUserId(context));
			dettagli_registrazione.setModifiedby(getUserId(context));
			dettagli_registrazione.setIdAslInserimentoRegistrazione(getUserAsl(context));
			dettagli_registrazione
					.setIdTipologiaRegistrazioneOperatore(RegistrazioneModificaIndirizzoOperatore.idTipologia);
			dettagli_registrazione.setCap(context.getParameter("cap"));
			db = this.getConnection(context);
			db.setAutoCommit(false);

			// dettagli_registrazione.insert(db);

			int tempid = Integer.parseInt(opId);
			// operatoreToModify = new Operatore(db, tempid);
			operatoreToModify = new Operatore();
			operatoreToModify.queryRecordOperatorebyIdLineaProduttiva(db, tempid);

			dettagli_registrazione.ModificaOperatore(db, operatoreToModify, getUserId(context));

			// Stabilimento stab = (Stabilimento)
			// operatoreToModify.getListaStabilimenti().get(0);

			// Devo aggiornare indirizzo stabilimento ora

		} catch (Exception e) {
			e.printStackTrace();
			db.rollback();
			context.getRequest().setAttribute("Error",
					"Si è verificato un problema con l'inserimento della registrazione. Riprovare");
			return ("SystemError");
		} finally {

			db.setAutoCommit(true);
			db.close();
			this.freeConnection(context, db);

		}

		context.getRequest().setAttribute("opId",
				(String) context.getRequest().getParameter("idRelazioneStabilimentoLineaProduttiva"));

		return executeCommandDetails(context);

	}

	public String executeCommandPreparaAccettazioneModificaIndirizzoOperatore(ActionContext context)
			throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}
		Operatore operatoreToModify = null;
		String opId = context.getRequest().getParameter("opId");
		// System.out.println("opId in Modify: " + opId);

		Connection db = null;

		try {

			db = this.getConnection(context);

			context.getRequest().setAttribute("opId", opId);
			int tempid = Integer.parseInt(opId);
			// operatoreToModify = new Operatore(db, tempid);
			operatoreToModify = new Operatore();
			operatoreToModify.queryRecordOperatorebyIdLineaProduttiva(db, tempid);

			context.getRequest().setAttribute("operatoreToModify", operatoreToModify);

			RegistrazioneModificaIndirizzoOperatore regModifica = new RegistrazioneModificaIndirizzoOperatore();
			regModifica.setIdRelazioneStabilimentoLineaProduttiva(tempid);
			regModifica.getUltimaModificaResidenzaOperatore(db);
			Indirizzo nuovoIndirizzo = new Indirizzo(db, regModifica.getIdNuovoIndirizzo());
			context.getRequest().setAttribute("nuovoIndirizzo", nuovoIndirizzo);

			Indirizzo oldIndirizzo = new Indirizzo(db, regModifica.getIdVecchioIndirizzo());
			context.getRequest().setAttribute("vecchioIndirizzo", oldIndirizzo);

			context.getRequest().setAttribute("regModifica", regModifica);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList regioni = new LookupList(db, "lookup_regione");
			regioni.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("regioni", regioni);

			LookupList province = new LookupList(db, "lookup_province");
			province.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("province", province);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,
					((UserBean) context.getSession().getAttribute("User")).getSiteId(), 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");

			context.getRequest().setAttribute("comuni", comuniList);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return "AccettaModifyIndirizzoOperatoreOK";

	}

	public String executeCommandAccettaModificaIndirizzoOperatore(ActionContext context) throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}
		Operatore operatoreToModify = null;
		String opId = context.getRequest().getParameter("idRelazioneStabilimentoLineaProduttiva");
		// System.out.println("opId in Modifica indirizzo: " + opId);

		Connection db = null;

		try {

			RegistrazioneAccettazioneModificaResidenzaOperatore dettagli_registrazione = (RegistrazioneAccettazioneModificaResidenzaOperatore) context
					.getRequest().getAttribute("RegistrazioneAccettazioneModificaResidenzaOperatore");
			dettagli_registrazione.setEnteredby(getUserId(context));
			dettagli_registrazione.setModifiedby(getUserId(context));
			dettagli_registrazione.setIdAslInserimentoRegistrazione(getUserAsl(context));
			dettagli_registrazione
					.setIdTipologiaRegistrazioneOperatore(RegistrazioneAccettazioneModificaResidenzaOperatore.idTipologia);
			int tempid = Integer.parseInt(opId);

			// Riferimento registrazione modifica
			db = this.getConnection(context);

			// dettagli_registrazione.insert(db);

			// operatoreToModify = new Operatore(db, tempid);
			operatoreToModify = new Operatore();
			operatoreToModify.queryRecordOperatorebyIdLineaProduttiva(db, tempid);

			dettagli_registrazione.ModificaOperatore(db);

			// Stabilimento stab = (Stabilimento)
			// operatoreToModify.getListaStabilimenti().get(0);

			// Devo aggiornare indirizzo stabilimento ora NON PIU

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("opId",
				(String) context.getRequest().getParameter("idRelazioneStabilimentoLineaProduttiva"));

		return executeCommandDetails(context);

	}

	public String executeCommandAggiungiRegistrazioni(ActionContext context) throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		Operatore operatoreToModify = null;
		String opId = context.getRequest().getParameter("opId");
		// System.out.println("opId in Modifica indirizzo: " + opId);

		Connection db = null;
		String modulo = "";

		try {

			db = this.getConnection(context);
			if (context.getRequest().getParameter("modulo") != null
					&& !("").equals((String) context.getRequest().getParameter("modulo"))) {
				modulo = ((String) context.getRequest().getParameter("modulo"));
			}

			context.getRequest().setAttribute("modulo", modulo);
			// dettagli_registrazione.insert(db);

			int tempid = Integer.parseInt(opId);
			// operatoreToModify = new Operatore(db, tempid);
			operatoreToModify = new Operatore();
			int idAsl = -1;

			RegistrazioneModificaIndirizzoOperatore registrazione_modifica_aperta = new RegistrazioneModificaIndirizzoOperatore();
			registrazione_modifica_aperta.setIdRelazioneStabilimentoLineaProduttiva(tempid);
			registrazione_modifica_aperta.getUltimaModificaResidenzaOperatore(db);
			Indirizzo newIndirizzo = new Indirizzo(db, registrazione_modifica_aperta.getIdNuovoIndirizzo());

			Object[] asl;
			asl = DwrUtil.getValoriAsl(newIndirizzo.getComune());
			if (asl != null && asl.length > 0) {

				Object[] aslVal = (Object[]) asl[0];
				if (aslVal != null && aslVal.length > 0)
					idAsl = (Integer) aslVal[0];
				newIndirizzo.setIdAsl(idAsl);
			}

			operatoreToModify.queryRecordOperatorebyIdLineaProduttiva(db, tempid);
			Stabilimento stabOp = (Stabilimento) operatoreToModify.getListaStabilimenti().get(0);

			if (stabOp.getIdAsl() == thisUser.getSiteId() || idAsl == thisUser.getSiteId() || thisUser.getRoleId() == Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_HD1"))|| thisUser.getRoleId() == Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_HD2")) )
				context.getRequest().setAttribute("canAddModificaResidenza", "canAddModificaResidenza");
			context.getRequest().setAttribute("operatore", operatoreToModify);
			context.getRequest().setAttribute("newIndirizzo", newIndirizzo);

			// Lista registrazioni operatore

			RegistrazioniOperatoreList registrazioniList = new RegistrazioniOperatoreList();

			PagedListInfo searchListInfo = this.getPagedListInfo(context, "registrazioniListInfo");
			searchListInfo.setLink("OperatoreAction.do?command=AggiungiRegistrazioni");
			searchListInfo.setListView("all");

			registrazioniList.setIdRelazioneStabilimentoLp(tempid);
			registrazioniList.buildList(db);
			context.getRequest().setAttribute("listaRegistrazioni", registrazioniList);

			LookupList registrazioniOperatoreList = new LookupList(db, "lookup_tipologia_registrazione_operatore");
			context.getRequest().setAttribute("registrazioniOperatoreList", registrazioniOperatoreList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			context.getRequest().setAttribute("AslList", siteList);

			// Stabilimento stab = (Stabilimento)
			// operatoreToModify.getListaStabilimenti().get(0);

			// Devo aggiornare indirizzo stabilimento ora

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("opId",
				(String) context.getRequest().getParameter("idRelazioneStabilimentoLineaProduttiva"));

		return "AggiungiRegistrazioniOperatoreOK";

	}

	public String executeCommandListaModificheIndirizzoIngressoAsl(ActionContext context) throws SQLException {
		if (!hasPermission(context, "proprietari_detentori_modulo-edit")) {
			return ("PermissionError");
		}

		PagedListInfo searchListInfo = this.getPagedListInfo(context, "registrazioniListInfo");
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		// Operatore operatoreToModify = null;
		// String opId = context.getRequest().getParameter("opId");
		// System.out.println("opId in Modifica indirizzo: " + opId);

		Connection db = null;

		try {

			db = this.getConnection(context);

			// Lista registrazioni operatore

			RegistrazioniOperatoreList registrazioniList = new RegistrazioniOperatoreList();
			registrazioniList.setPagedListInfo(searchListInfo);
			registrazioniList.setIdAslDestinatariaModificaFuoriResidenza(thisUser.getSiteId());
			registrazioniList.setIdTipologiaRegistrazioneOperatore(RegistrazioneModificaIndirizzoOperatore.idTipologia);
			registrazioniList.setFlagCercaSoloSospese(true);

			searchListInfo.setLink("OperatoreAction.do?command=ListaModificheIndirizzoIngressoAsl");
			searchListInfo.setListView("all");

			searchListInfo.setSearchCriteria(registrazioniList, context);

			registrazioniList.buildList(db);
			context.getRequest().setAttribute("listaRegistrazioni", registrazioniList);

			LookupList registrazioniOperatoreList = new LookupList(db, "lookup_tipologia_registrazione_operatore");
			context.getRequest().setAttribute("registrazioniOperatoreList", registrazioniOperatoreList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- TUTTE --");
			context.getRequest().setAttribute("AslList", siteList);

			// Stabilimento stab = (Stabilimento)
			// operatoreToModify.getListaStabilimenti().get(0);

			// Devo aggiornare indirizzo stabilimento ora

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("opId",
				(String) context.getRequest().getParameter("idRelazioneStabilimentoLineaProduttiva"));

		return "RecuperaRegistrazioniModificaIndirizzoIngressoOK";

	}

	// Lista delle modifiche raggruppate per data
	public String executeCommandPrepareListaModifiche(ActionContext context) throws SQLException, UnknownHostException {
		
		Connection db = null;
		try {
			// MOSTRA LISTA MODIFICHE ESEGUITE SULL'OPERATORE
			
			db = this.getConnection(context);
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT distinct(data_modifica), id_utente_modifica FROM opu_operatore_storico where id_relazione = ? group by data_modifica, id_utente_modifica ORDER BY data_modifica DESC ");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, Integer.parseInt(context.getRequest().getParameter("opId")));
			ResultSet result = pst.executeQuery();
			ArrayList<String> listaMods = new ArrayList<String>();

			while (result.next()) { // process results one row at a time
				listaMods.add(result.getString("id_utente_modifica") + ";;" + result.getString("data_modifica") + ";;");
			}
			context.getRequest().setAttribute("listaModifiche", listaMods);

		} catch (Exception e) {

		} finally {
			this.freeConnection(context, db);
		}
		return "listaModsOK";

	}

	// Dettaglio delle modifiche
	public String executeCommandListaModifiche(ActionContext context) throws SQLException, UnknownHostException {
		// MOSTRA LISTA MODIFICHE ESEGUITE SULL'OPERATORE
		Connection db = null;
	try{	
		db = this.getConnection(context);
		StringBuffer sql = new StringBuffer();
		String dataModifica = context.getRequest().getParameter("dataModifica");
		String idUtenteModifica = context.getRequest().getParameter("idUtenteModifica");
		sql.append("SELECT * FROM opu_operatore_storico where data_modifica = ? and id_utente_modifica = ?");
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());
		// pst.setInt(++i,
		// Integer.parseInt(context.getRequest().getParameter("opId")));
		pst.setTimestamp(++i, Timestamp.valueOf(dataModifica));
		pst.setInt(++i, Integer.parseInt(idUtenteModifica));
		ResultSet result = pst.executeQuery();
		ArrayList<String> listaMods = new ArrayList<String>();

		while (result.next()) { // process results one row at a time
			listaMods.add(result.getString("id") + ";;" + result.getString("nome_campo_modificato") + ";;"
					+ result.getString("valore_precedente") + ";;" + result.getString("nuovo_valore") + ";;"
					+ result.getString("id_utente_modifica") + ";;" + result.getString("data_modifica") + ";;");
		}
		context.getRequest().setAttribute("listaModifiche", listaMods);
	} catch (Exception e) {

	} finally {
		this.freeConnection(context, db);
	}
		return "listaModsDetailOK";
	}

	public String executeCommandElencoRegistrazioniProprietario(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
			if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
				return ("PermissionError");
			}
		}
		PagedListInfo EventiListInfo = this.getPagedListInfo(context, "EventiListInfo");
		String servletPath = context.getRequest().getServletPath();
		String userAction = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
		EventiListInfo.setLink(userAction + ".do?command=Search");
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			int idLineaP = -1;
			db = this.getConnection(context);
			EventoList listaEventi = new EventoList();
			listaEventi.setPagedListInfo(EventiListInfo);
			listaEventi.setPagedListInfo(EventiListInfo);
			listaEventi.setMinerOnly(false);
			listaEventi.setTypeId(EventiListInfo.getFilterKey("listFilter1"));
			String idLineaProduttivaOperatore = (String) context.getRequest().getParameter("opId");

			if (idLineaProduttivaOperatore != null && !("").equals(idLineaProduttivaOperatore)) {
				idLineaP = new Integer(idLineaProduttivaOperatore);
			}
			Operatore newOperatore = new Operatore();
			newOperatore.queryRecordOperatorebyIdLineaProduttiva(db, idLineaP);
			context.getRequest().setAttribute("OperatoreDettagli", newOperatore);

			listaEventi.setId_proprietario_corrente(idLineaP);

			listaEventi.setStageId(EventiListInfo.getCriteriaValue("searchcodeStageId"));
			EventiListInfo.setSearchCriteria(listaEventi, context);
			listaEventi.setPagedListInfo(EventiListInfo);

			listaEventi.buildList(db);

			context.getRequest().setAttribute("listaEventi", listaEventi);

			LookupList registrazioniList = new LookupList(db, "lookup_tipologia_registrazione");
			registrazioniList.addItem(-1, "--Tutte le registrazioni--");
			context.getRequest().setAttribute("registrazioniList", registrazioniList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			// Action di provenienza
			// String servletPath = context.getRequest().getServletPath();
			String actionFrom = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
			context.getRequest().setAttribute("actionFrom", actionFrom);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("ListRegistrazioniAnimaleOK");
	}

	public String executeCommandUnificazioneProprietari(ActionContext context) throws SQLException {
		if (!hasPermission(context, "anagrafe_canina_operazioni_hd-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		Operatore soggettoAdded = new Operatore();
		Operatore soggettoAdded1 = new Operatore();

		try {

			db = this.getConnection(context);
			if (context.getRequest().getParameter("idProprietario") != null
					&& !context.getRequest().getParameter("idProprietario").equals("")
					&& !context.getRequest().getParameter("idProprietario").equals("-1")) {

				soggettoAdded = new Operatore();
				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
						.getParameter("idProprietario")).intValue());
				context.getRequest().setAttribute("Proprietario1", soggettoAdded);
			}

			if (context.getRequest().getParameter("idDetentore") != null
					&& !context.getRequest().getParameter("idDetentore").equals("")
					&& !context.getRequest().getParameter("idDetentore").equals("-1")) {

				soggettoAdded1 = new Operatore();
				soggettoAdded1.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
						.getParameter("idDetentore")).intValue());
				context.getRequest().setAttribute("Proprietario2", soggettoAdded1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		if ((context.getRequest().getParameter("doContinue") == null || context.getRequest().getParameter("doContinue")
				.equals(""))
				|| context.getRequest().getParameter("doContinue").equals("false")) {

			return getReturn(context, "Unificazione");
		}
		return executeCommandUnificazioneProprietariEsegui(context);

	}

	public String executeCommandUnificazioneProprietariEsegui(ActionContext context) throws SQLException {

		if (!hasPermission(context, "anagrafe_canina_operazioni_hd-add")) {
			return ("PermissionError");
		}

		Connection db = null;

		try {

			Operatore proprietarioDaRimuovere = (Operatore) context.getRequest().getAttribute("Proprietario1");
			Operatore proprietarioDaConservare = (Operatore) context.getRequest().getAttribute("Proprietario2");

			Stabilimento stabDaRimuovere = (Stabilimento) proprietarioDaRimuovere.getListaStabilimenti().get(0);
			LineaProduttiva lineaDaRimuovere = (LineaProduttiva) stabDaRimuovere.getListaLineeProduttive().get(0);

			Stabilimento stabDaConservare = (Stabilimento) proprietarioDaConservare.getListaStabilimenti().get(0);
			LineaProduttiva lineaDaConservare = (LineaProduttiva) stabDaConservare.getListaLineeProduttive().get(0);

			db = this.getConnection(context);
			db.setAutoCommit(false);
			Operatore.unificaProprietari(db, lineaDaRimuovere.getId(), lineaDaConservare.getId(),
					proprietarioDaRimuovere.getIdOperatore(), stabDaRimuovere.getIdAsl(), stabDaConservare.getIdAsl(),
					this.getUserId(context));

			db.commit();
			context.getRequest().setAttribute("opId", String.valueOf(lineaDaConservare.getId()));

		} catch (Exception e) {
			e.printStackTrace();
			db.rollback();
		} finally {

			this.freeConnection(context, db);
		}

		return executeCommandDetails(context);
	}

	public String executeCommandDelete(ActionContext context) throws SQLException {

		if (!hasPermission(context, "anagrafe_canina_operazioni_hd-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		int idLineaP = -1;

		try {

			db = this.getConnection(context);
			String idLineaProduttivaOperatore = (String) context.getRequest().getParameter("opId");

			if (idLineaProduttivaOperatore != null && !("").equals(idLineaProduttivaOperatore)) {
				idLineaP = new Integer(idLineaProduttivaOperatore);
			}
			Operatore newOperatore = new Operatore();
			newOperatore.queryRecordOperatorebyIdLineaProduttiva(db, idLineaP);

			EsitoControllo esito = newOperatore.verificaEliminazione(db);

			if (esito.getIdEsito() < 0) {
				// NON PUOI CANCELLARE
				context.getRequest().setAttribute("Errore", esito.getDescrizione());
				context.getRequest().setAttribute("opId", String.valueOf(idLineaP));
				return executeCommandDetails(context);
			} else {
				// PUOI CANCELLARE
				newOperatore.delete(db, (String) context.getRequest().getParameter("motivazioneModifica"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			db.rollback();
		} finally {

			this.freeConnection(context, db);
		}

		return executeCommandSearchForm(context);
	}
	
	public String executeCommandAggiornaTaglieMassivo(ActionContext context) 
	{
	    if (!(hasPermission(context, "aggiorna_taglie_massivo-view"))) 
	    {
	      return ("PermissionError");
	    }
		String filePath = this.getPath(context, "aggiorna_taglie_massivo");
		String fileName = "";
		HttpMultiPartParser multiPart = new HttpMultiPartParser();
	   	
		HashMap parts = null;
		try 
		{
			parts = multiPart.parseData(context.getRequest(), filePath);
		} 
		catch (IllegalArgumentException | IOException e2) 
		{
			e2.printStackTrace();
			context.getRequest().setAttribute("messaggio", "AGGIORNAMENTO TAGLI terminato con errore: " + e2.getMessage());
			return executeCommandDetails(context);
		}
	    
		int idCanile = Integer.parseInt((String)parts.get("opId"));
		
		Connection db = this.getConnection(context);
				
		Operatore newOperatore = new Operatore();
		
		BufferedReader br = null;
		
		HashMap<String,String> microchips = new HashMap<String,String>();
		
		try 
		{
			newOperatore.queryRecordOperatorebyIdLineaProduttiva(db, idCanile);
			context.getRequest().setAttribute("OperatoreDettagli", newOperatore);
			this.freeConnection(context, db);
			
			String motivazione = (String)parts.get("motivazione");
		     
		    if ((Object)  parts.get("file") instanceof FileInfo) 
		    {
		          //Update the database with the resulting file
		          FileInfo newFileInfo = (FileInfo) parts.get("file");
		          //Insert a file description record into the database
		          com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
		          thisItem.setLinkModuleId(Constants.ACCOUNTS);
		          thisItem.setEnteredBy(getUserId(context));
		          thisItem.setModifiedBy(getUserId(context));
		          thisItem.setClientFilename(newFileInfo.getClientFileName());
		          thisItem.setFilename(newFileInfo.getRealFilename());
		          fileName = newFileInfo.getRealFilename();
		          thisItem.setVersion(1.0);
		          thisItem.setSize(newFileInfo.getSize());
		    }
			
		    String csvFile = filePath + fileName;
		  	
			String line = "";
			String cvsSplitBy = ";";
			
			int i = 0;
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) 
			{
				
				Animale thisAnimale = new Animale();
				db =this.getConnection(context);
				String[] valori = line.split(cvsSplitBy);
				String esito = "OK-----<>-----Aggiornamento taglia avvenuto con successo.";
				boolean esitoB = true;
				
				if(valori.length<3)
				{
					esito = "KO-----<>-----Record non valido: valori mancanti."; 
					esitoB = false;
					microchips.put(valori[0] + "-----<>----------<>-----", esito);
				}
				
				String mc 		  = "";
				String tatuaggio  = "";
				String tagliaS    = "";
				int taglia = 0;
				
				if(esitoB)
				{
					mc 		 = (valori[0].equals("")?(null):(valori[0]));
					tatuaggio = (valori[1].equals("")?(null):(valori[1]));
					tagliaS    = (valori[2].equals("")?(null):(valori[2]));
					
					if(mc==null && tatuaggio==null)
					{
						esito = "KO-----<>-----Specificare microchip o tatuaggio."; 
						esitoB = false;
					}
					
					if(esitoB && tagliaS==null)
					{
						esito = "KO-----<>-----Inserire la taglia."; 
						esitoB = false;
					}
					
					try
					{
						if(esitoB)
							taglia = Integer.parseInt(tagliaS);
					}
					catch(Exception e)
					{
						esito = "KO-----<>-----Valore " + tagliaS + " non ammesso come taglia. I valori ammessi sono 1(piccola),2(media),3(grande) e 5(gigante)."; 
						esitoB=false;
					}
						
					
					if(esitoB && (taglia<0 || taglia>5 || taglia==4))
					{
						esito = "KO-----<>-----Valore " + taglia + " non ammesso come taglia. I valori ammessi sono 1,2,3 e 5."; 
						esitoB=false;
					}
					
					try
					{
						thisAnimale = new Animale(db, mc, tatuaggio);
					}
					catch(SQLException e)
					{
						esito = "KO-----<>-----Errore nel recupero dell'animale: " + e.getMessage(); 
						esitoB=false;
					}
					if(esitoB && thisAnimale.getIdAnimale()<=0)
					{
						esito = "KO-----<>-----Animale non presente in banca dati."; 
						esitoB=false;
					}
					if(esitoB && (thisAnimale.getIdProprietario()!=idCanile && thisAnimale.getIdDetentore()!=idCanile))
					{
						esito = "KO-----<>-----Proprietario e detentore non corrispondente al canile in oggetto. Il proprietario è '" + thisAnimale.getProprietario().getRagioneSociale() + "' e il detentore è '" + thisAnimale.getDetentore().getRagioneSociale() + "'."; 
						esitoB=false;
					}
					try
					{
						if(esitoB)
						{
							thisAnimale.updateTaglia(db, taglia, motivazione);
						}
					}
					catch(Exception e)
					{
						esito = "KO-----<>-----Errore durante l'aggiornamento dell'animale: " + e.getMessage(); 
						esitoB=false;
					}
					microchips.put(valori[0] + "-----<>-----" + valori[1] + "-----<>-----" + valori[2], esito);
				}
				
				
				i++;
				
				this.freeConnection(context, db);
				
			}
			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("messaggio", "AGGIORNAMENTO TAGLI terminato con errore: " + e.getMessage());
			return executeCommandDetails(context);
		} 
		finally 
		{
			if (br != null) 
			{
				try 
				{
					br.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			this.freeConnection(context, db);
		
		}
	    
		System.out.println("AGGIORNAMENTO TAGLIE Import terminato");

		context.getRequest().setAttribute("microchips", microchips);
		context.getRequest().setAttribute("orgId", idCanile);
			
		return "AggiornaTaglieMassivoOK";
		
	    } 
	
}

