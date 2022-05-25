/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apicolture.actions;

import it.izs.apicoltura.apianagraficaattivita.ws.Api;
import it.izs.apicoltura.apianagraficaattivita.ws.Apiatt;
import it.izs.apicoltura.apianagraficaattivita.ws.Apicen;
import it.izs.apicoltura.apianagraficaattivita.ws.BusinessWsException_Exception;
import it.izs.apicoltura.apianagraficaattivita.ws.FieldError;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaApi;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaAttivita;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaVariazioniCen;
import it.izs.apicoltura.apianagraficaazienda.ws.Apiazienda;
import it.izs.apicoltura.apianagraficaazienda.ws.WsApiAnagraficaAziende;
import it.izs.bdn.anagrafica.ws.Persone;
import it.izs.bdn.anagrafica.ws.WsApiAnagraficaApistoproBdn;
import it.izs.bdn.anagrafica.ws.WsApiAnagraficaPersoneBdn;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.log4j.Logger;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.aziendeagricole.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestoriacquenew.base.EccezioneDati;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.base.Mail;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DwrApicoltura;
import org.aspcfs.utils.OperatoreInsertEsception;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupListList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.utils.web.StateSelect;

import com.daffodilwoods.daffodildb.server.serversystem.dmlvalidation.constraintsystem.SetDefaultReferencedExecuter;
import com.daffodilwoods.daffodildb.server.sql99.dcl.sqlcontrolstatement.throwexception;
import com.daffodilwoods.daffodildb.server.sql99.expression.booleanvalueexpression.length;
import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.security.sasl.ServerFactoryImpl;
import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;

import ext.aspcfs.modules.apiari.base.Azienda;
import ext.aspcfs.modules.apiari.base.Delega;
import ext.aspcfs.modules.apiari.base.DelegaList;
import ext.aspcfs.modules.apiari.base.Indirizzo;
import ext.aspcfs.modules.apiari.base.MovimentazioniList;
import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.OperatoreList;
import ext.aspcfs.modules.apiari.base.SoggettoFisico;
import ext.aspcfs.modules.apiari.base.Stabilimento;
import ext.aspcfs.modules.apiari.base.StabilimentoList; 
import ext.aspcfs.modules.apiari.base.WSBdnLog;
import ext.aspcfs.modules.apicolture.util.ApiUtil;

public class OperatoreAction extends CFSModule {

	Logger logger = Logger.getLogger(OperatoreAction.class);

	
	public String executeCommandDefault(ActionContext context) {

		return executeCommandSearchForm(context);
	}

	public String executeCommandHome(ActionContext context) {

		return executeCommandList(context);

	}

	public String executeCommandRevocaDelegaApicoltore(ActionContext context) throws IndirizzoNotFoundException {

		WsApiAnagraficaAziende wsAziende = new WsApiAnagraficaAziende();
		Connection db = null;
		try {
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			db = this.getConnection(context);
			DelegaList listaDeleghe = new DelegaList();
			listaDeleghe.setEnabled(true);
			listaDeleghe.setCodice_fiscale_delegante(thisUser.getContact().getCodiceFiscale().trim());

			listaDeleghe.buildList(db, this.getSystemStatus(context), context);

			if (listaDeleghe.size() > 0) {
				Iterator<Delega> itDel = listaDeleghe.iterator();
				while (itDel.hasNext()) {
					Delega d = itDel.next();
					d.revocaDelega(db, getUserId(context));
				}
			}

		} catch (SQLException e) {
		} finally {
			this.freeConnection(context, db);
		}

		return executeCommandHome(context);
	}

	public String executeCommandToLiberaCodice(ActionContext context) {

		context.getRequest().setAttribute("Error", context.getRequest().getAttribute("Error"));
		return "ToLiberaCodiceOK";
	}

	public String executeCommandLiberaCodice(ActionContext context) {

		String codiceAzienda = context.getParameter("codice_azienda");

		WsApiAnagraficaAziende wsAziende = new WsApiAnagraficaAziende();
		Connection db = null;
		try {
			db = this.getConnection(context);
			wsAziende.liberaCodice(codiceAzienda, getUserId(context), db);

		} catch (ServerSOAPFaultException | it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception e) {
			// TODO Auto-generated catch block

			logger.error("Errore nell'invio in BDN : " + e.getMessage());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			String errore = "";
			if (e instanceof BusinessWsException_Exception) {
				BusinessWsException_Exception ee = (BusinessWsException_Exception) e;
				errore = ee.getFaultInfo().getMessage() + " : " + ee.getFaultInfo().getResult().getErrore();

				for (FieldError error : ee.getFaultInfo().getResult().getErrors()) {
					errore += "[" + error.getField() + ": " + error.getMessage() + "]";

				}
			} else {
				if (e instanceof it.izs.bdn.anagrafica.ws.BusinessWsException_Exception) {

					it.izs.bdn.anagrafica.ws.BusinessWsException_Exception ee = (it.izs.bdn.anagrafica.ws.BusinessWsException_Exception) e;
					errore = ee.getFaultInfo().getMessage() + " : " + ee.getFaultInfo().getResult().getErrore();

					for (it.izs.bdn.anagrafica.ws.FieldError error : ee.getFaultInfo().getResult().getErrors()) {
						errore += "[" + error.getField() + ": " + error.getMessage() + "]";

					}
				} else {
					if (e instanceof ServerSOAPFaultException) {

						ServerSOAPFaultException ee = (ServerSOAPFaultException) e;
						errore = ee.getFault().getFaultString();

					}

					else
						errore = e.getMessage();
				}
			}
			context.getRequest().setAttribute("Error", errore);
		} catch (SQLException e) {

		} finally {
			this.freeConnection(context, db);
		}
		return executeCommandToLiberaCodice(context);

	}

	public String executeCommandSearchAziendaEsistenteInBdn(ActionContext context) throws IllegalAccessException,
			InstantiationException, IndirizzoNotFoundException, IOException {
		ArrayList<Operatore> lista = new ArrayList<Operatore>();
		Connection db = null;
		try {
			db = this.getConnection(context);
			String codiceAziendaNazionale = context.getParameter("searchTerm");

			int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));

			Operatore op = new Operatore(db, idOperatore);

			SystemStatus system = this.getSystemStatus(context);

			UserBean user = (UserBean) context.getSession().getAttribute("User");

			WsApiAnagraficaAziende wsAziende = new WsApiAnagraficaAziende();
			List<Apiazienda> listaAziende = wsAziende.searchCodiceEsistente(op.getSedeLegale().getCodiceIstatComune(),
					op.getSedeLegale().getSiglaProvincia(), db, getUserId(context), op.getRappLegale().getCodFiscale()
							.toUpperCase());
			JsonArray array = new JsonArray();
			for (Apiazienda att : listaAziende) {
				JsonObject o = new JsonObject();
				o.addProperty("codiceAzienda", att.getCodice());
				o.addProperty("comune", op.getSedeLegale().getDescrizioneComune());
				o.addProperty("provincia", op.getSedeLegale().getSiglaProvincia());
				o.addProperty("cf", op.getRappLegale().getCodFiscale());
				array.add(o);
			}
			JsonObject obj = new JsonObject();
			obj.addProperty("page", listaAziende.size() / 5);
			obj.addProperty("total", listaAziende.size());
			obj.addProperty("records", "5");
			obj.add("rows", array);

			Gson gson = new GsonBuilder().create();

			context.getResponse().getOutputStream().print(gson.toJson(obj));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "-none-";

	}

	// public String executeCommandGetCodiceAziendaEsistente(ActionContext
	// context) throws IllegalAccessException, InstantiationException,
	// IndirizzoNotFoundException, IOException {
	// ArrayList<Operatore> lista = new ArrayList<Operatore>();
	// Connection db = null;
	// try {
	// db = this.getConnection(context);
	// int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));
	// Operatore op = new Operatore();
	// op.queryRecordOperatore(db, idOperatore);
	// SystemStatus system = this.getSystemStatus(context);
	// UserBean user = (UserBean)context.getSession().getAttribute("User");
	//
	// /**
	// *
	// * VERIFICA CHE PER LA TERNA ISTAT COMUNE SIGLA PROVINCIA CF PROPRIETARIO
	// ESISTE GIA UN CODICE AZIENDA
	// */
	// WsApiAnagraficaAziende wsAziende = new WsApiAnagraficaAziende();
	// List<Apiazienda> listaAziende =
	// wsAziende.searchCodiceEsistente(op.getSedeLegale().getCodiceIstatComune(),op.getSedeLegale().getSiglaProvincia(),
	// db, getUserId(context),op.getRappLegale().getCodFiscale().toUpperCase());
	// JsonArray array = new JsonArray();
	// if (listaAziende.size()>0)
	// {
	// for(Apiazienda att : listaAziende)
	// {
	// JsonObject o = new JsonObject();
	// o.addProperty("codiceAzienda", att.getCodice());
	// o.addProperty("comune", op.getSedeLegale().getDescrizioneComune());
	// o.addProperty("provincia", op.getSedeLegale().getSiglaProvincia());
	// o.addProperty("cf", op.getRappLegale().getCodFiscale());
	// o.addProperty("tipoGenerazione","esistente");
	// array.add(o);
	// }
	// JsonObject obj = new JsonObject();
	// obj.addProperty("page", listaAziende.size()/5);
	// obj.addProperty("total", listaAziende.size());
	// obj.addProperty("records", "5");
	// obj.add("rows", array);
	// Gson gson = new GsonBuilder().create();
	// context.getResponse().getOutputStream().print(gson.toJson(obj));
	// }
	//
	//
	//
	// }
	// catch(SQLException e)
	// {
	// e.printStackTrace();
	// }
	// finally
	// {
	// this.freeConnection(context, db);
	// }
	// return "-none-";
	//
	//
	// }

	public String executeCommandGetCodiceAziendaEsistente(ActionContext context) throws IllegalAccessException,
			InstantiationException, IndirizzoNotFoundException, IOException {
		ArrayList<Operatore> lista = new ArrayList<Operatore>();
		Connection db = null;
		try {
			db = this.getConnection(context);
			int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));
			Operatore op = new Operatore();
			op.queryRecordOperatore(db, idOperatore);
			SystemStatus system = this.getSystemStatus(context);
			UserBean user = (UserBean) context.getSession().getAttribute("User");

			String suffisso = "";

			String codiceAzienda = context.getParameter("codice");
			suffisso = codiceAzienda.substring(5, 8);
			suffisso = suffisso.substring(0, 2);

			/**
			 * 
			 * VERIFICA CHE PER LA TERNA ISTAT COMUNE SIGLA PROVINCIA CF
			 * PROPRIETARIO ESISTE GIA UN CODICE AZIENDA
			 */
			WsApiAnagraficaAziende wsAziende = new WsApiAnagraficaAziende();
			List<Apiazienda> listaAziende = wsAziende.getCodiceDisponibile(db, op.getSedeLegale()
					.getCodiceIstatComune(), op.getSedeLegale().getSiglaProvincia(), suffisso, getUserId(context));
			JsonArray array = new JsonArray();
			if (listaAziende.size() > 0) {
				for (Apiazienda att : listaAziende) {
					if (att.getCodice().equalsIgnoreCase(codiceAzienda)) {
						JsonObject o = new JsonObject();
						o.addProperty("codiceAzienda", att.getCodice());
						o.addProperty("comune", op.getSedeLegale().getDescrizioneComune());
						o.addProperty("provincia", op.getSedeLegale().getSiglaProvincia());
						o.addProperty("cf", op.getRappLegale().getCodFiscale());
						o.addProperty("tipoGenerazione", "esistente");
						array.add(o);
					}
				}
				JsonObject obj = new JsonObject();
				obj.addProperty("page", listaAziende.size() / 5);
				obj.addProperty("total", listaAziende.size());
				obj.addProperty("records", "5");
				obj.add("rows", array);
				Gson gson = new GsonBuilder().create();
				context.getResponse().getOutputStream().print(gson.toJson(obj));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "-none-";

	}

	public String executeCommandGetCodiceAziendaDisponibile(ActionContext context) throws IllegalAccessException,
			InstantiationException, IndirizzoNotFoundException, IOException {
		ArrayList<Operatore> lista = new ArrayList<Operatore>();
		Connection db = null;
		try {
			db = this.getConnection(context);
			// String codiceAziendaNazionale =
			// context.getParameter("searchTerm");
			// String suffisso = "" ;
			// if (codiceAziendaNazionale.length()>5)
			// suffisso = codiceAziendaNazionale.substring(5);

			int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));

			Operatore op = new Operatore(db, idOperatore);

			SystemStatus system = this.getSystemStatus(context);

			UserBean user = (UserBean) context.getSession().getAttribute("User");

			int itemPage = 15;
			WsApiAnagraficaAziende wsAziende = new WsApiAnagraficaAziende();
			List<Apiazienda> listaAziende = wsAziende.getCodiceDisponibile(db, op.getSedeLegale()
					.getCodiceIstatComune(), op.getSedeLegale().getSiglaProvincia(), "", getUserId(context));
			JsonArray array = new JsonArray();
			int i = 0;
			for (Apiazienda att : listaAziende) {

				if (att.verificaEsistenzaGisa(db) == false) {

					JsonObject o = new JsonObject();
					o.addProperty("codiceAzienda", att.getCodice());
					o.addProperty("comune", "");
					o.addProperty("provincia", "");
					o.addProperty("cf", "");
					array.add(o);
					i++;
				}
			}
			JsonObject obj = new JsonObject();
			obj.addProperty("page", listaAziende.size() / itemPage);
			obj.addProperty("total", listaAziende.size());
			obj.addProperty("records", i);
			obj.add("rows", array);

			Gson gson = new GsonBuilder().create();

			context.getResponse().getOutputStream().print(gson.toJson(obj));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "-none-";

	}

	public String executeCommandAssegnaNumero(ActionContext context) throws Exception {
		
		Connection db = null;
		try {
			db = this.getConnection(context);

			if (db.getAutoCommit() == true)
				db.setAutoCommit(false);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			
			String codiceAzienda = "";
			int idOperatore = Integer.parseInt(context.getParameter("opId"));

			Operatore op = new Operatore();
			op.queryRecordOperatore(db, idOperatore);

			int progressivo = 1;
			int istat = -1;
			String siglProv = "";
			String codiceAziendaNazionale = context.getParameter("searchAziendaField");

			String sel = "select max(progressivo)+1,sigla,istat from apicoltura_imprese_progressivi_comuni where id_comune =? group by sigla,istat ";
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, op.getSedeLegale().getComune());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				progressivo = rs.getInt(1);
				istat = rs.getInt("istat");
				siglProv = rs.getString("sigla");
			}

			String istatasString = org.aspcfs.utils.StringUtils.zeroPad(istat, 3);
			String progressivoPadding = org.aspcfs.utils.StringUtils.zeroPad(progressivo, 3);

			codiceAzienda = "" + siglProv + istatasString + progressivoPadding;

			op.setCodiceAzienda(codiceAziendaNazionale);

			WsApiAnagraficaPersoneBdn servicePersone = new WsApiAnagraficaPersoneBdn();
			try {

				/**
				 * INVIO IMPRESA E APIARI ALLA BDN
				 */

				/**
				 * STEP 1 VERIFICA ESISTENZA PRORPIETARIO (SE NON ESISTE LO
				 * INSERISCO)
				 */

				Persone proprietario = servicePersone.searchPersone(op.getRappLegale(), db, getUserId(context));
				if (proprietario == null)
					proprietario = servicePersone.insertAnagraficaPersona(op.getRappLegale(), db, getUserId(context));

				proprietario.getPersoneId();
				pst = db.prepareStatement("update opu_soggetto_fisico set id_bdn = ? where id = ? ");
				pst.setInt(1, proprietario.getPersoneId());
				pst.setInt(2, op.getRappLegale().getIdSoggetto());
				pst.execute();

				/**
				 * STEP 2 VERIFICA ESISTENZA AZIENDA (SE NON ESISTE LA
				 * INSERISCO)
				 */

				WsApiAnagraficaAziende serviceziende = new WsApiAnagraficaAziende();
				Apiazienda apiAzienda = serviceziende.insert(op, db, getUserId(context));

				/**
				 * STEP 3 INSERIMENTO ATTIVITA ( IMPRESA DI APICOLTURA)
				 */

				WsApiAnagraficaAttivita serviceAttivitia = new WsApiAnagraficaAttivita();
				Apiatt attivitaApi = serviceAttivitia.insertApiAnagraficaattivita(op, db, getUserId(context));

				pst = db.prepareStatement("update apicoltura_imprese set id_bdn_azienda = ?,id_bdn_attivita=? where id = ? ");
				pst.setInt(1, apiAzienda.getAziendaId());
				pst.setInt(2, attivitaApi.getApiattId());
				pst.setInt(3, op.getIdOperatore());
				pst.execute();

				op.sincronizzaBdn(db, getUserId(context));

				String update = "update apicoltura_imprese set codice_azienda_regionale = ?,stato=?,codice_azienda=?  where id = ?; ";
				pst = db.prepareStatement(update);
				pst.setString(1, codiceAzienda);
				pst.setInt(2, StabilimentoAction.API_STATO_VALIDATO);
				pst.setString(3, codiceAziendaNazionale);

				pst.setInt(4, idOperatore);

				pst.execute();

				op.setCodiceAzienda(codiceAziendaNazionale);

				if (db.getAutoCommit() == false) {
					db.commit();

				}

			} catch (Exception e) 
			{

				logger.error("Errore nell'invio in BDN : " + e.getMessage());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				String msg = ApiUtil.getInfoRichiesta(op, "KO", e.getMessage(), timestamp, thisUser, new LookupList(db, "lookup_site_id"),  new LookupList(db, "lookup_apicoltura_stati_apiario"),  new LookupList(db, "apicoltura_lookup_tipo_attivita"), new LookupList(db, "apicoltura_lookup_classificazione"), new LookupList(db, "apicoltura_lookup_sottospecie"), new LookupList(db, "apicoltura_lookup_modalita"));				
				
				if (db.getAutoCommit() == false) 
				{
					db.rollback();
					db.setAutoCommit(true);
				}
				String errore = e.getMessage();

				context.getRequest().setAttribute("ErrorValidazioneError", errore);

				try 
				{
					sendMailIzsm(context.getRequest(), msg + ". L'errore e il seguente :" + errore, "##GISA_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
				} 
				catch (Exception e2) 
				{
					logger.error("Errore nell'invio della mail");
				}
				return executeCommandSearchForm(context);
			}
			

			/**
			 * STEP 4 VERIFICA ESISTENZA DETENTORE (SE NON ESISTE LO INSERISCO)
			 */

			StabilimentoList listaApiari = op.getListaStabilimenti();
			Iterator<Stabilimento> itListaApiari = listaApiari.iterator();
			while (itListaApiari.hasNext()) 
			{

				Stabilimento thisApiario = itListaApiari.next();

				if (!thisApiario.isFlagLaboratorio()) 
				{
					try 
					{
						servicePersone.insertAnagraficaPersona(thisApiario.getDetentore(), db, getUserId(context));
						thisApiario.getOperatore().setCodiceAzienda(op.getCodiceAzienda());

						/**
						 * STEP 5 INSERIMENTO DEGLI APIARI ASSOCIATI ALL'IMPRESA
						 */

						WsApiAnagraficaApi serviceApi = new WsApiAnagraficaApi();
						Api apiarioInserito = serviceApi.insertApiAnagraficaApiario(thisApiario, db, getUserId(context),context);

						//Gestione Autocensimento
						//Ricerca censimento
						WsApiAnagraficaVariazioniCen censimento = new WsApiAnagraficaVariazioniCen();
						int progressivoApiario  = Integer.parseInt(thisApiario.getProgressivoBDA());
						
						List<Apicen> censimenti = censimento.search(op.getCodiceAzienda(), progressivoApiario, thisApiario.getDataApertura(), "S",db);
						
						//Inserimento Censimento
						if(!censimenti.isEmpty())
						{
							thisApiario.setData_assegnazione_censimento(thisApiario.getDataApertura());
							thisApiario.nuovoCensimento(db, context, censimenti.get(0).getApicenId());
						}
						//Fine Gestione Autocensimento
						
						thisApiario.sincronizzaBdn(db, getUserId(context), apiarioInserito.getApiId());

						if (db.getAutoCommit() == false) {
							db.commit();

						}

					} 
					catch (EccezioneDati e) 
					{
						logger.error("Errore nell'invio in BDN : " + e.getMessage());
						Timestamp timestamp = new Timestamp(System.currentTimeMillis());
						
						String msg = ApiUtil.getInfoRichiesta(op, "KO", e.getMessage(), timestamp, thisUser, new LookupList(db, "lookup_site_id"), new LookupList(db, "lookup_apicoltura_stati_apiario"),  new LookupList(db, "apicoltura_lookup_tipo_attivita"), new LookupList(db, "apicoltura_lookup_classificazione"), new LookupList(db, "apicoltura_lookup_sottospecie"), new LookupList(db, "apicoltura_lookup_modalita"));				

						if (db.getAutoCommit() == false) 
						{
							db.rollback();
							db.setAutoCommit(true);
						}

							if (e.getMessage().contains("RECORD ESISTENTE")) 
							{
								thisApiario.sincronizzaBdn(db, getUserId(context), null);

								if (db.getAutoCommit() == false) {
									db.commit();

								}
							} 
							else 
							{
								thisApiario.errSincronizzaBdn(db, e.getMessage());
								if (db.getAutoCommit() == false) 
								{
									db.commit();
								}

							}

						context.getRequest().setAttribute("ErrorValidazioneError", e.getMessage());

						try {
							sendMailIzsm(context.getRequest(), msg + ". L'errore e il seguente :" + e.getMessage(),
									"##GISA_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
						} catch (Exception e2) {
							logger.error("Errore nell'invio della mail");
						}

					}
					catch(Exception e)
					{
						System.out.println("SYNC- Errore nell'invio in BDN : "+e.getMessage());
						String errore = "Errore Interno GISA : "+e.getMessage() + "";
						thisApiario.errSincronizzaBdn(db, errore);
					}

				}

				if (db.getAutoCommit() == false) {
					db.setAutoCommit(true);
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					String msg = ApiUtil.getInfoRichiesta(op, "OK", null, timestamp, thisUser, new LookupList(db, "lookup_site_id"),  new LookupList(db, "lookup_apicoltura_stati_apiario"),  new LookupList(db, "apicoltura_lookup_tipo_attivita"), new LookupList(db, "apicoltura_lookup_classificazione"), new LookupList(db, "apicoltura_lookup_sottospecie"), new LookupList(db, "apicoltura_lookup_modalita"));				

					sendMailIzsm(context.getRequest(), msg, "##GISA_API_WS_BDN_OK##", "gisahelpdesk@usmail.it");

					context.getRequest().setAttribute("ErrorValidazioneError",
							"IMPRESA VALIDATA CORRETTAMENTE E INSERITA IN BDN");
				}
			}
			
			
			System.out.println("OperatoreAction: inizio trasferimento implicito in anagrafica stabilimenti");
			//inserire trasferimento in anagrafica stabilimenti se quella richiesta proviene da una nuova pratica suap
			org.aspcfs.modules.suap.base.Stabilimento richiesta = new org.aspcfs.modules.suap.base.Stabilimento();
			richiesta.queryRecordStabilimentoIdOperatore(db, op.getIdRichiestaSuap());
			String codiceRegionale = richiesta.generaNumeroRegistrazione(db, String.valueOf(op.getIdRichiestaSuap()),false);
			
			context.getRequest().setAttribute("codice_regionale",codiceRegionale);			
			context.getRequest().setAttribute("pIvaImpresa",richiesta.getOperatore().getPartitaIva());
			context.getRequest().setAttribute("codiceFiscaleImpresa",richiesta.getOperatore().getCodiceInternoImpresa());
			context.getRequest().setAttribute("idRichiesta",String.valueOf(richiesta.getIdOperatore()));
			context.getRequest().setAttribute("idTipoRichiesta",String.valueOf(richiesta.getOperatore().getIdOperazione()));			
			context.getRequest().setAttribute("statoValidazione", "1");
			context.getRequest().setAttribute("codiciNazionali", codiceAziendaNazionale);
			
			org.aspcfs.modules.suap.base.LineaProduttiva lp_richiesta = (org.aspcfs.modules.suap.base.LineaProduttiva) richiesta.getListaLineeProduttive().get(0);
			
			context.getRequest().setAttribute("idTipoLinea", String.valueOf( lp_richiesta.getIdLookupConfigurazioneValidazione()  ) );			
			context.getRequest().setAttribute("idLinea", String.valueOf( lp_richiesta.getId()  ) );
			
			return "temp_trasferisci_in_anagrafica_ok";
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}		
		
		return executeCommandSearchForm(context);
	}

	public String executeCommandAdd(ActionContext context) {

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			/*
			 * QUESTO PARAMETRO e SETTATO NELLA REQUEST DAL COMMAND INSERT DI
			 * OPERATORE ACTION
			 */
			if (context.getRequest().getAttribute("Exist") != null
					&& !("").equals(context.getRequest().getAttribute("Exist"))) {
				context.getRequest().setAttribute("Exist", (String) context.getRequest().getAttribute("Exist"));
			}

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,
					((UserBean) context.getSession().getAttribute("User")).getSiteId(), 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");

			context.getRequest().setAttribute("ComuniList", comuniList);
			Operatore newOperatore = (Operatore) context.getFormBean();
			newOperatore.setAction(context.getAction().getActionName());
			context.getRequest().setAttribute("Operatore", newOperatore);

			DelegaList listaDeleghe = new DelegaList();
			listaDeleghe.setCodice_fiscale_delegato(thisUser.getContact().getVisibilitaDelega());
			listaDeleghe.buildList(db, systemStatus, context);
			context.getRequest().setAttribute("ListaDeleghe", listaDeleghe);

			LookupList tipoAttivitaApi = new LookupList(db, "apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
//			tipoAttivitaApi.removeElementByCode(1);
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			LookupList NazioniList = new LookupList(db, "lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);

			context.getRequest().setAttribute("TipologiaSoggetto",
					(String) context.getRequest().getParameter("tipologiaSoggetto"));
			
			String cfImpresa = null;
	    	if(thisUser.getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_DELEGATO || thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("avellino") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("benevento") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("caserta") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 1 CENTRO") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 2 NORD") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 3 SUD") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("salerno"))
	    		cfImpresa = thisUser.getSoggetto().getCodFiscale();
	    	else
	    		cfImpresa = thisUser.getContact().getVisibilitaDelega();
	    	

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			OperatoreList operatoreList = new OperatoreList();
			operatoreList.setCodiceFiscale(cfImpresa);
			operatoreList.buildListApicoltori(db);

			if (operatoreList.size() > 0
					&& ((Operatore) operatoreList.get(0)).getStato() == StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE) {
				context.getRequest().setAttribute("opId", ((Operatore) operatoreList.get(0)).getIdOperatore());
				return executeCommandModifyPregresso(context);
			}
			
			System.out.println("Controllo dati registrazione per riempimento modello A apicoltura");
			if(thisUser.getSoggetto()!=null && thisUser.getSoggetto().getCodFiscale()!=null)
			{
				System.out.println("Controllo dati registrazione per riempimento modello A apicoltura: cf is not null");
				Operatore orgDetails = new Operatore();

				PreparedStatement pst = db.prepareStatement(" select *,c.nome as descrizioneComune,c.id as idComune from log_user_reg reg " + 
															" left join comuni1 c on c.istat = reg.comune_residenza where codice_fiscale ilike ? ");
				pst.setString(1,thisUser.getSoggetto().getCodFiscale());
				System.out.println("Controllo dati registrazione per riempimento modello A apicoltura: query da eseguire: " + pst.toString());
				ResultSet rs = pst.executeQuery();
				if(rs.next())
				{
					System.out.println("Controllo dati registrazione per riempimento modello A apicoltura. rs popolato");
					context.getRequest().setAttribute("comuneRichiesta", rs.getString("descrizioneComune"));
					context.getRequest().setAttribute("comuneRichiestaId", rs.getString("idComune"));
					context.getRequest().setAttribute("capRichiesta", rs.getString("cap_residenza"));
					context.getRequest().setAttribute("indirizzoRichiesta", rs.getString("indirizzo_residenza"));
					context.getRequest().setAttribute("provinciaRichiesta", rs.getString("provincia_residenza"));
					context.getRequest().setAttribute("pecRichiesta", rs.getString("pec"));
					context.getRequest().setAttribute("telefonoRichiesta", rs.getString("telefono"));
					System.out.println("Controllo dati registrazione per riempimento modello A apicoltura. setto orgdetails");
				}
			}
			
			
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));

		return getReturn(context, "Add");
	}

	public String executeCommandCambiaCodiceFiscale(ActionContext context) throws SQLException {

		Connection db = null;
		try {

			db = this.getConnection(context);
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

			SoggettoFisico sf = new SoggettoFisico(context.getRequest().getParameter("cf"), db);
			user.setSoggetto(sf);
			context.getRequest().getSession().setAttribute("User", user);

			Operatore operatore = new Operatore();
			if (context.getParameter("opId") != null && Integer.parseInt(context.getParameter("opId")) > 0) 
				operatore.queryRecordOperatore(db, Integer.parseInt(context.getParameter("opId")));
			if (context.getParameter("opId") != null && Integer.parseInt(context.getParameter("opId")) > 0 && operatore.getStato()!=9 && operatore.getStato()!=4 && operatore.getStato()!=8) 
			{
				operatore.getListaStabilimenti().setIdOperatore(operatore.getIdOperatore());
				operatore.getListaStabilimenti().buildList(db);

				context.getRequest().setAttribute("opId", context.getParameter("opId"));

				return executeCommandDetails(context);
			}
			else
			{
				ArrayList<Integer> idStatoToExclude = new ArrayList<Integer>();
				idStatoToExclude.add(4);
				idStatoToExclude.add(8);
				idStatoToExclude.add(9);
				operatore = new Operatore(db,null,context.getRequest().getParameter("cf"),null,null,idStatoToExclude);

				if(operatore.getIdOperatore()>0)
				{
					operatore.getListaStabilimenti().setIdOperatore(operatore.getIdOperatore());
					operatore.getListaStabilimenti().buildList(db);
				context.getRequest().setAttribute("opId", operatore.getIdOperatore());

				return executeCommandDetails(context);
				}
			}

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return executeCommandAdd(context);

	}

	public String executeCommandInsert(ActionContext context) throws SQLException {

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore = (Operatore) context.getFormBean();

		newOperatore.setEnteredBy(getUserId(context));
		newOperatore.setModifiedBy(getUserId(context));
		newOperatore.setTelefono1(context.getRequest().getParameter("telefono1"));
		

		try {

			db = this.getConnection(context);
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest());
			if (context.getParameter("idSoggettoFisico") != null
					&& !"".equals(context.getParameter("idSoggettoFisico")))
				soggettoAdded.setIdSoggetto(Integer.parseInt(context.getParameter("idSoggettoFisico")));
			boolean exist = false;

			/**
			 * CONTROLLO DI ESISTENZA DELL'OPERATORE PER PARTITA IVA
			 */

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			SoggettoFisico soggettoEsistente = null;
			soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			/* se il soggetto non esiste lo aggiungo */

			Indirizzo indirizzoAdded1 = null;
			indirizzoAdded1 = new Indirizzo(context.getRequest(), db, context);
			indirizzoAdded1.setTipologiaSede(1);

			newOperatore.getListaSediOperatore().add(indirizzoAdded1);

			/* se esiste */

			if (soggettoEsistente.getIdSoggetto() > 0) {
				Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();

				newOperatore.setRappLegale(soggettoEsistente);
			} else {

				/**
				 * seil soggetto non esiste e coincidde con l utente anagrafato
				 * nelsistema lo inserisco prendendo idati da concact
				 */
				if (soggettoAdded.getCodFiscale().equalsIgnoreCase(user.getContact().getCodiceFiscale())) {

					soggettoAdded.setNome(user.getContact().getNameFirst());
					soggettoAdded.setCognome(user.getContact().getNameLast());
					soggettoAdded.setDataNascita(user.getContact().getBirthDate());
					soggettoAdded.setComuneNascita(user.getContact().getCity());
					soggettoAdded.setProvinciaNascita(user.getContact().getState());
					soggettoAdded.setCodFiscale(user.getContact().getCodiceFiscale());
					Indirizzo ind = new Indirizzo();
					ind.setComune(indirizzoAdded1.getComune());
					ind.setVia(indirizzoAdded1.getVia());
					ind.setCap(indirizzoAdded1.getCap());
					ind.setProvincia(indirizzoAdded1.getProvincia());
					soggettoAdded.setIndirizzo(ind);

					soggettoAdded.insert(db, context);
					newOperatore.setRappLegale(soggettoAdded);

				}
			}

			newOperatore.setFlagProduzioneConLaboratorio(context.getParameter("produzioneConLaboratorio") != null);


			exist = newOperatore.checkEsistenzaOperatore(db);
			if (exist) {
				context.getRequest().setAttribute(
						"Exist",
						"Attenzione Attivita Esistente per Questo Codice Fiscale :"
								+ newOperatore.getRappLegale().getCodFiscale());
				context.getRequest().setAttribute("rappLegale", soggettoAdded);
				if (context.getParameter("popup") != null)
					context.getRequest().setAttribute("PopUp", context.getParameter("popup"));
				return executeCommandAdd(context);
			}

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, user.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals("")
					&& context.getRequest().getParameter("doContinue").equals("false")) {
				if (context.getParameter("popup") != null)
					context.getRequest().setAttribute("PopUp", context.getParameter("popup"));

				return executeCommandAdd(context);
			}

			isValid = this.validateObject(context, db, newOperatore);

			
			/*
			 * Controllo comune sede legale uguale a come sta in bdn
			 */
			String istatImpresa = newOperatore.getSedeLegale().getCodiceIstatComune();
			WsApiAnagraficaPersoneBdn p = new WsApiAnagraficaPersoneBdn();
			Persone  pp = p.searchPersone(newOperatore.getRappLegale().getCodFiscale());
			String istatBdn = "";
			if(pp!=null)
				istatBdn = pp.getComIstat();
			if(!istatBdn.equals("") && !istatBdn.equalsIgnoreCase(istatImpresa))
			{
				context.getRequest().setAttribute("Error", 
						                          "IL COMUNE DI RESIDENZA DEL CODICE FISCALE GIA' PRESENTE IN BDN NON COINCIDE CON QUELLO INSERITO.<br/>"
						                          + "PER INSERIRE L'ANAGRAFICA SI PREGA DI MODIFICARE PRIMA LA RESIDENZA IN BDN");
				context.getRequest().setAttribute("OrgDetails", newOperatore);
				return executeCommandAdd(context);
			}
			/*
			 * Fine Controllo comune sede legale uguale a come sta in bdn
			 */
			
			if (isValid) {
				recordInserted = newOperatore.insert(db, context);
				context.getRequest().setAttribute("opId", newOperatore.getIdOperatore());
				context.getRequest().setAttribute("OrgDetails", newOperatore);
			} else {

				context.getRequest().setAttribute("OrgDetails", newOperatore);
				return executeCommandAdd(context);
			}

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			if (context.getRequest().getParameter("popup") != null) {
				context.getRequest().setAttribute("OperatoreAdd", newOperatore);
				context.getRequest().setAttribute("TipologiaSoggetto", "1");
				return ("ClosePopupOK");
			}
		}

		return "InsertOK";

		// return ("InsertOK");

	}

	public String executeCommandUpdatePregresso(ActionContext context) throws SQLException {

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore = (Operatore) context.getFormBean();

		newOperatore.setEnteredBy(getUserId(context));
		newOperatore.setModifiedBy(getUserId(context));

		try {

			db = this.getConnection(context);
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest());
			if (context.getParameter("idSoggettoFisico") != null
					&& !"".equals(context.getParameter("idSoggettoFisico")))
				soggettoAdded.setIdSoggetto(Integer.parseInt(context.getParameter("idSoggettoFisico")));
			boolean exist = false;

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			SoggettoFisico soggettoEsistente = null;
			soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			/* se il soggetto non esiste lo aggiungo */

			Indirizzo indirizzoAdded1 = null;
			indirizzoAdded1 = new Indirizzo(context.getRequest(), db, context);
			indirizzoAdded1.setTipologiaSede(1);

			newOperatore.getListaSediOperatore().add(indirizzoAdded1);

			/* se esiste */

			if (soggettoEsistente.getIdSoggetto() > 0) {
				Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();

				newOperatore.setRappLegale(soggettoEsistente);
			} else {

				/**
				 * seil soggetto non esiste e coincidde con l utente anagrafato
				 * nelsistema lo inserisco prendendo idati da concact
				 */
				if (soggettoAdded.getCodFiscale().equalsIgnoreCase(user.getContact().getCodiceFiscale())) {

					soggettoAdded.setNome(user.getContact().getNameFirst());
					soggettoAdded.setCognome(user.getContact().getNameLast());
					soggettoAdded.setDataNascita(user.getContact().getBirthDate());
					soggettoAdded.setComuneNascita(user.getContact().getCity());
					soggettoAdded.setProvinciaNascita(user.getContact().getState());
					soggettoAdded.setCodFiscale(user.getContact().getCodiceFiscale());
					Indirizzo ind = new Indirizzo();
					ind.setComune(indirizzoAdded1.getComune());
					ind.setVia(indirizzoAdded1.getVia());
					ind.setCap(indirizzoAdded1.getCap());
					ind.setProvincia(indirizzoAdded1.getProvincia());
					soggettoAdded.setIndirizzo(ind);

					soggettoAdded.insert(db, context);
					newOperatore.setRappLegale(soggettoAdded);

				}
			}

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, ((UserBean) context.getSession()
					.getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, user.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			isValid = this.validateObject(context, db, newOperatore);

			if (isValid) {
				newOperatore.update(db, context);

				String[] idApiari = context.getRequest().getParameterValues("id_stabilimento");
				for (int i = 0; i < idApiari.length; i++) {
					Stabilimento apiario = new Stabilimento();
					apiario.setIdOperatore(newOperatore.getIdOperatore());
					apiario.setIdStabilimento(Integer.parseInt(idApiari[i]));

					apiario.setIdApicolturaClassificazione(context.getParameter("idApicolturaClassificazione_"
							+ idApiari[i]));
					apiario.setIdApicolturaSottospecie(context.getParameter("idApicolturaSottospecie_" + idApiari[i]));
					apiario.setIdApicolturaModalita(context.getParameter("idApicolturaModalita_" + idApiari[i]));
					apiario.setDataApertura(context.getParameter("dataApertura_" + idApiari[i]));
					apiario.setNumAlveari(context.getParameter("numAlveari_" + idApiari[i]));
					apiario.setNumSciami(context.getParameter("numSciami_" + idApiari[i]));
					apiario.setDetentore(new SoggettoFisico(db, Integer.parseInt(context
							.getParameter("idSoggettoFisico_" + idApiari[i]))));

					Indirizzo sedeOperativa = new Indirizzo();
					sedeOperativa.setComune(context.getParameter("comune_" + idApiari[i]));
					sedeOperativa.setProvincia(context.getParameter("provincia_" + idApiari[i]));
					sedeOperativa.setCap(context.getParameter("presso_" + idApiari[i]));
					sedeOperativa.setVia(context.getParameter("viaTesto_" + idApiari[i]));
					sedeOperativa.setLatitudine(context.getParameter("latitudine_" + idApiari[i]));
					sedeOperativa.setLongitudine(context.getParameter("longitudine_" + idApiari[i]));
					sedeOperativa.setIdIndirizzo(-1);
					apiario.setSedeOperativa(sedeOperativa);

					apiario.update(db, context);

				}

				context.getRequest().setAttribute("opId", newOperatore.getIdOperatore());
				context.getRequest().setAttribute("OrgDetails", newOperatore);
			} else {

				context.getRequest().setAttribute("OrgDetails", newOperatore);
				return executeCommandAdd(context);
			}

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			if (context.getRequest().getParameter("popup") != null) {
				context.getRequest().setAttribute("OperatoreAdd", newOperatore);
				context.getRequest().setAttribute("TipologiaSoggetto", "1");
				return ("ClosePopupOK");
			}
		}

		return "InsertOK";

		// return ("InsertOK");

	}

	public String executeCommandInsertPersona(ActionContext context) throws SQLException {

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore = (Operatore) context.getFormBean();

		newOperatore.setEnteredBy(getUserId(context));
		newOperatore.setModifiedBy(getUserId(context));

		try {

			db = this.getConnection(context);
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest());
			boolean exist = false;

			/**
			 * CONTROLLO DI ESISTENZA DELL'OPERATORE PER PARTITA IVA
			 */

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			SoggettoFisico soggettoEsistente = null;
			soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			/* se il soggetto non esiste lo aggiungo */

			isValid = this.validateObject(context, db, soggettoAdded);

			if (isValid) {
				if (soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <= 0) {

					soggettoAdded.setEnteredBy(getUserId(context));
					soggettoAdded.setModifiedBy(getUserId(context));

					soggettoAdded.insert(db, context);
					//
					context.getRequest().setAttribute("EsitoInserimentoSoggettoFisico", "OK");
					context.getRequest().setAttribute("ErroreInserimento", "");

					context.getRequest().setAttribute("idSoggettoFisico", "" + soggettoAdded.getIdSoggetto());
					context.getRequest().setAttribute("nominativoSoggettoFisico",
							"" + soggettoAdded.getCognome() + " " + soggettoAdded.getNome());
					context.getRequest().setAttribute("cfSoggettoFisico", "" + soggettoAdded.getCodFiscale());

				} else {

					soggettoEsistente.getIndirizzo().setIdProvincia(soggettoAdded.getIndirizzo().getIdProvincia());
					soggettoEsistente.getIndirizzo().setIdIndirizzo(-1);
					soggettoEsistente.getIndirizzo().setComune(soggettoAdded.getIndirizzo().getComune());
					soggettoEsistente.getIndirizzo().setVia(soggettoAdded.getIndirizzo().getVia());

					if (soggettoEsistente != null
							&& (soggettoEsistente.getComuneNascita() == null || "".equals(soggettoEsistente
									.getComuneNascita()))) {
						soggettoEsistente.setComuneNascita(soggettoAdded.getComuneNascita());
					}

					if (soggettoEsistente != null
							&& (soggettoEsistente.getDataNascita() == null || "".equals(soggettoEsistente
									.getDataNascita()))) {
						soggettoEsistente.setDataNascita(soggettoAdded.getDataNascita());
					}

					soggettoEsistente.update(db, context);

					if (context.getParameter("tipo") == null) // SOLO SE
																// RIGUARDA IL
																// PROPRIETARIO
					{
						user.setSoggetto(soggettoEsistente);
						context.getSession().setAttribute("User", user);
					}

					context.getRequest().setAttribute("EsitoInserimentoSoggettoFisico", "OK");
					context.getRequest().setAttribute("ErroreInserimento", "");

					context.getRequest().setAttribute("idSoggettoFisico", "" + soggettoEsistente.getIdSoggetto());
					context.getRequest().setAttribute("nominativoSoggettoFisico",
							"" + soggettoEsistente.getCognome() + " " + soggettoAdded.getNome());
					context.getRequest().setAttribute("cfSoggettoFisico", "" + soggettoEsistente.getCodFiscale());

				}
			} else {
				context.getRequest().setAttribute("EsitoInserimentoSoggettoFisico", "KO");
				context.getRequest().setAttribute("ErroreInserimento",
						"Errore nella validazione campi controllare i campi elencati : ");
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

		return "StampaJson";

		// return ("InsertOK");

	}

	public String executeCommandSearchAzienda(ActionContext context) throws IllegalAccessException,
			InstantiationException, IndirizzoNotFoundException, IOException {
		ArrayList<Operatore> lista = new ArrayList<Operatore>();
		Connection db = null;
		try {
			db = this.getConnection(context);
			String codiceAzienda = context.getParameter("searchTerm");

			JsonArray array = new JsonArray();
			Operatore op = new Operatore();
			op.setCodiceAzienda(codiceAzienda);
			WsApiAnagraficaAttivita wsAttivita = new WsApiAnagraficaAttivita();
			Apiatt att = wsAttivita.search(op, db, getUserId(context));
			if (att != null) {
				JsonObject o = new JsonObject();
				o.addProperty("id", att.getApiattId());
				o.addProperty("comune", att.getComSlDescrizione());
				o.addProperty("indirizzo", att.getIndirizzoSl());
				o.addProperty("codiceAzienda", att.getAziendaCodice());
				o.addProperty("proprietario", att.getPropCognNome());
				o.addProperty("ragioneSociale", att.getDenominazione());
				o.addProperty("cfProprietario", att.getPropIdFiscale());
				array.add(o);
			} else {
				JsonObject o = new JsonObject();
				o.addProperty("id", -1);
				o.addProperty("comune", "AZIENDE NON PRESENTE IN BDA");
				o.addProperty("indirizzo", "");
				o.addProperty("codiceAzienda", "");
				o.addProperty("proprietario", "");
				o.addProperty("ragioneSociale", "");
				o.addProperty("cfProprietario", "");
				array.add(o);

			}

			/*
			 * OperatoreList listaAtt = new OperatoreList();
			 * listaAtt.setCodiceAzienda(codiceAzienda);
			 * listaAtt.setStato(StabilimentoAction.API_STATO_VALIDATO);
			 * listaAtt.buildList(db);
			 * 
			 * Iterator it = listaAtt.iterator(); JsonArray array = new
			 * JsonArray();
			 * 
			 * 
			 * while (it.hasNext()) { Operatore att = (Operatore) it.next();
			 * lista.add(att);
			 * 
			 * JsonObject o = new JsonObject(); o.addProperty("id",
			 * att.getIdOperatore()); o.addProperty("comune",
			 * att.getSedeLegale().getDescrizioneComune());
			 * o.addProperty("indirizzo", att.getSedeLegale().getVia());
			 * o.addProperty("codiceAzienda", att.getCodiceAzienda());
			 * o.addProperty("proprietario",
			 * att.getRappLegale().getCognome()+" "
			 * +att.getRappLegale().getNome()); o.addProperty("ragioneSociale",
			 * att.getRagioneSociale()); o.addProperty("cfProprietario",
			 * att.getRappLegale().getCodFiscale());
			 * 
			 * 
			 * array.add(o); }
			 */

			JsonObject obj = new JsonObject();

			obj.addProperty("page", lista.size() / 5);
			obj.addProperty("total", lista.size());
			obj.addProperty("records", "5");
			obj.add("rows", array);

			Gson gson = new GsonBuilder().create();

			context.getResponse().getOutputStream().print(gson.toJson(obj));

		} catch (SQLException e) {

		} catch (BusinessWsException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "-none-";

	}
	
	
	public String executeCommandSearchAziendaCfPartitaIva(ActionContext context) throws IllegalAccessException,
	InstantiationException, IndirizzoNotFoundException, IOException 
	{
		ArrayList<Operatore> lista = new ArrayList<Operatore>();
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			String cfPartitaIva = context.getParameter("searchTerm");

		JsonArray array = new JsonArray();
		Operatore op = new Operatore(db,cfPartitaIva, cfPartitaIva);
		if (op != null && op.getIdOperatore()>0) {
			JsonObject o = new JsonObject();
			o.addProperty("cfPartitaIva", op.getCodFiscale());
			o.addProperty("ragioneSociale", op.getRagioneSociale());
			o.addProperty("indirizzoSedeLegale", op.getSedeLegale().getVia() );
			o.addProperty("comuneSedeLegale", op.getSedeLegale().getDescrizioneComune());
			Provincia p = new Provincia();
			p = p.getProvincePerCampoTesto(db, op.getSedeLegale().getDescrizione_provincia(), -1).get(0);
			
			o.addProperty("provinciaSedeLegale", p.getCodProvincia());
			o.addProperty("codiceFiscaleProprietario", op.getRappLegale().getCodFiscale());
			array.add(o);
		} else {
			JsonObject o = new JsonObject();
			o.addProperty("cfPartitaIva", "");
			o.addProperty("ragioneSociale", "AZIENDE NON PRESENTE IN BDA");
			o.addProperty("indirizzoSedeLegale", "" );
			o.addProperty("comuneSedeLegale", "");
			o.addProperty("provinciaSedeLegale", "");
			o.addProperty("codiceFiscaleProprietario", "");
			array.add(o);
		}
		
		JsonObject obj = new JsonObject();
	
		obj.addProperty("page", lista.size() / 5);
		obj.addProperty("total", lista.size());
		obj.addProperty("records", "5");
		obj.add("rows", array);
	
		Gson gson = new GsonBuilder().create();
	
		context.getResponse().getOutputStream().print(gson.toJson(obj));
	
	} catch (SQLException e) {
	
	}  finally 
	{
		this.freeConnection(context, db);
	}
	return "-none-";

}

	public String executeCommandDeleteRichiesta(ActionContext context) {

		String opId = context.getParameter("opId");

		Connection db = null;
		try {
			db = this.getConnection(context);
			Operatore op = new Operatore(db, Integer.parseInt(opId));
			op.deleteRichiesta(db, getUserId(context));

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			return executeCommandHome(context);

		} catch (SQLException e) {

		} finally {
			this.freeConnection(context, db);
		}
		return "PermissionError";
	}

	
	private HashMap<Integer,Integer> getComuniToAsl(Connection conn)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		HashMap<Integer,Integer> mappa = new HashMap<Integer,Integer>();
		try
		{
			pst = conn.prepareStatement("select b.id as idcomune, a.code as idasl from lookup_site_id a join comuni1 b on b.codiceistatasl = a.codiceistat ");
			rs = pst.executeQuery();
			while(rs.next())
			{
				mappa.put(rs.getInt("idcomune"), rs.getInt("idasl"));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();;
		}
		finally
		{
			try{} catch(Exception ex){}
			try{} catch(Exception ex){}
		}
		return mappa;
	}
	
	public String executeCommandSearch(ActionContext context) {

		String source = (String) context.getRequest().getParameter("source");
		
		
 		
		
		StabilimentoList organizationList = new StabilimentoList();

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchApiListInfo");
		searchListInfo.setLink("ApicolturaAttivita.do?command=Search");
		searchListInfo.setListView("All");
		
		
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// UserBean user = (UserBean)
			// context.getSession().getAttribute("User");
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, ComuniAnagrafica.IN_REGIONE);

			LookupList comuniList = new LookupList(listaComuni, -1);
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			
		
			
			
			

			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList lookupStati = new LookupList(db, "lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);

			// For portal usr set source as 'searchForm' explicitly since
			// the search form is bypassed.
			// temporary solution for page redirection for portal user.

			// return if no criteria is selected
			if ((searchListInfo.getListView() == null || "".equals(searchListInfo.getListView()))
					&& !"searchForm".equals(source)) {
				return "SearchListOK";
			}

			// Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context, "SearchApiListInfo");
					searchListInfo.setLink("ApicolturaAttivita.do?command=Search");
					searchListInfo.setColumnToSortBy("ragione_sociale");
				}
				// Build the organization list

				// organizationList.setStato(StabilimentoAction.API_STATO_VALIDATO);
				// // solo le validate
				// organizationList.setStatoPregresso(StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE);

				searchListInfo.setSearchCriteria(organizationList, context);
				organizationList.setPagedListInfo(searchListInfo);
				/*per qualche motivo non funziona il mapping automatico verso id asl ma verso idaslapiario */
				/*quindi lo forzo */
				
//				String  idaslS = context.getRequest().getParameter("searchcodeidAsl");
//				if(idaslS != null && idaslS.trim().length() > 0 )
//				{
//					organizationList.setIdAsl(Integer.parseInt(context.getRequest().getParameter("searchcodeidAsl")));
//				}
				
				searchListInfo.setColumnToSortBy("s.id_asl, ragione_sociale"); /*MODIFICA 17/07 PER FORZARE ORDINE, s e' 
impresa (apicoltore) */
				
				organizationList.buildList(db);

				context.getRequest().setAttribute("OrgList", organizationList);
				context.getSession().setAttribute("previousSearchType", "accounts");

				return "SearchListOK";

			}
		} catch (Exception e) {
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "SearchListOK";

	}

	public String executeCommandSearchForm(ActionContext context) {

		String id_specie = context.getRequest().getParameter("specie");
		context.getRequest().setAttribute("id_specie", id_specie);
		UserBean user = (UserBean) context.getSession().getAttribute("User");

		Operatore operatore = new Operatore();

		operatore.setAction(context.getAction().getActionName());
		context.getRequest().setAttribute("Operatore", operatore);
		// Bypass search form for portal users
		if (isPortalUser(context)) {
			// return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);

		context.getRequest()
				.setAttribute(
						"ErrorValidazione",
						"Attenzione! non e stato possibile validare l'imrpresa per problemi di interazione con la BDN. <br>Risposta del Servizio BDN : "
								+ context.getRequest().getAttribute("ErrorValidazione"));

		Connection db = null;
		try {
			db = getConnection(context);

			this.resetPagedListInfo(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(Constants.TUTTE_ASL_CAMPANIA, "Tutte ASL Campane");
			siteList.addItem(Constants.INVALID_SITE, "Tutte Asl Campane & Fuori Regione");
			context.getRequest().setAttribute("SiteList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// UserBean user = (UserBean)
			// context.getSession().getAttribute("User");
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, user.getSiteId(), ComuniAnagrafica.IN_REGIONE);

			LookupList comuniList = new LookupList(listaComuni, -1);

			comuniList.addItem(-1, "Seleziona comune");
			context.getRequest().setAttribute("ComuniList", comuniList);
			
			/*per comodita' mando al client mappa idcomune -> asl appartenenz ain modo tale che
			 * quando sceglie comune puo' essere avvisato se non e' in accordo con l'asl
			 */
			HashMap<Integer,Integer> comuniToAsl = getComuniToAsl(db);
			context.getRequest().setAttribute("ComuniToAsl", comuniToAsl);
			

			// reset the offset and current letter of the paged list in order to
			// make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);

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

		return getReturn(context, "Search");

	}

	public String executeCommandList(ActionContext context) {
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		OperatoreList operatoreList = new OperatoreList();
		String source = (String) context.getRequest().getParameter("source");

		addModuleBean(context, "View Accounts", "Search Results");

		Connection db = null;
		try {
			db = this.getConnection(context);

			// if
			// (context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI")!=null
			// &&
			// "_ext".equals(context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI")))
 
			LookupList NazioniList = new LookupList(db, "lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);

			System.out.println("------------------------------------------------------------");
			System.out.println("------------------------------------------------------------ NUOVO OPERATORE ACTION VISIBILITA DELEGA"+user.getContact().getVisibilitaDelega().trim());
			System.out.println("------------------------------------------------------------");
			
			if (context.getRequest().getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI").equals("_ext")
					&& user.getRoleId() == Role.RUOLO_APICOLTORE) {
				
				if (user.getSoggetto()==null || user.getSoggetto().getCodFiscale().equals("")){
					user.setSoggetto(new SoggettoFisico(user.getContact().getVisibilitaDelega().trim(), db));
				}
			
				if (user.getSoggetto() != null && !user.getSoggetto().getCodFiscale().equals("")) {
					operatoreList.setCodiceFiscale(user.getContact().getVisibilitaDelega().trim());
					operatoreList.buildListApicoltori(db); // nella costruzione
															// della lista ho
															// escluso il
															// pregresso
															// (g.balzano)
				}

				if (operatoreList.size() > 0
						&& ((Operatore) operatoreList.get(0)).getStato() == StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE) {
					context.getRequest().setAttribute("opId", ((Operatore) operatoreList.get(0)).getIdOperatore());
					return executeCommandModifyPregresso(context);

				} else {

					if (operatoreList.size() > 0 && ((Operatore) operatoreList.get(0)).getIdOperatore() > 0) {
						context.getRequest().setAttribute("opId", ((Operatore) operatoreList.get(0)).getIdOperatore());
						return executeCommandDetails(context);
					}
				}

			} else {
				if (user.getUserRecord().getGruppo_ruolo() == Role.GRUPPO_ALTRE_AUTORITA
						&& user.getUserRecord().getRoleId() != Role.RUOLO_DELEGATO
						&& user.getUserRecord().getRoleId() != Role.RUOLO_APICOLTORE)
					return executeCommandSearchForm(context);

				if(user.getUserRecord().getRoleId() == Role.RUOLO_DELEGATO)
				{
					operatoreList.setCodiceFiscale(user.getSoggetto().getCodFiscale());
					operatoreList.buildListApicoltori(db); // nella costruzione
				}
				else
				{
				operatoreList.setIdAsl(user.getSiteId());
				operatoreList.buildList(db);
				}
			}

			String message = context.getRequest().getParameter("Message");
			if (message != null && !"".equals(message)) {
				LoginBean login = new LoginBean();
				login.setMessage(message);
				context.getRequest().setAttribute("LoginBean", login);
			}

			context.getRequest().setAttribute("OrgList", operatoreList);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList tipoAttivitaApi = new LookupList(db, "apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			Operatore operatore = new Operatore();

			operatore.setAction(context.getAction().getActionName());
			context.getRequest().setAttribute("Operatore", operatore);

			
			//Inizio calcolo movimentazioni in ingresso
			boolean mov_in_ingresso = false;
			if(!operatoreList.isEmpty())
			{
				 Operatore op = (Operatore)operatoreList.get(0);
				 //Se non è stato ancora assegnato il codice azienda non posso fare il controllo sui moviemnti in ingresso
				if(op.getCodiceAzienda()!=null && !op.getCodiceAzienda().equals(""))
				{
				 MovimentazioniList listaMovimentazioni = new MovimentazioniList();
			     listaMovimentazioni.setCodiceAziendaDestinazione(op.getCodiceAzienda());
			     ArrayList<Integer> tipiMovimentazioni = new ArrayList<Integer>();
			     tipiMovimentazioni.add(1);
			     tipiMovimentazioni.add(4);
			     listaMovimentazioni.setTipoMovimentazione(tipiMovimentazioni);
			     listaMovimentazioni.setAccettazioneDestinatario(1);
			     listaMovimentazioni.buildList(db);
			     mov_in_ingresso = !listaMovimentazioni.isEmpty();
				}
			}
		    context.getRequest().setAttribute("mov_in_ingresso", mov_in_ingresso);
    		//Fine calcolo movimentazioni in ingresso
			
			return getReturn(context, "List");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}
	
	
	
	public String executeCommandEstrazioneAttivita(ActionContext context) 
	{

		Connection db = null;
		Operatore newOperatore = null;
		try {

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
			context.getRequest().setAttribute("dataOdierna", dataOdierna);
			
			newOperatore = new Operatore();

			if (context.getRequest().getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI").equals("_ext")
					&& user.getRoleId() == Role.RUOLO_DELEGATO) 
			{
				String cfImpresa = null;
		    	if(user.getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_DELEGATO || user.getContact().getVisibilitaDelega().equalsIgnoreCase("avellino") || 
		    			user.getContact().getVisibilitaDelega().equalsIgnoreCase("benevento") || 
		    			user.getContact().getVisibilitaDelega().equalsIgnoreCase("caserta") || 
		    			user.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 1 CENTRO") || 
		    			user.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 2 NORD") || 
		    			user.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 3 SUD") || 
		    			user.getContact().getVisibilitaDelega().equalsIgnoreCase("salerno"))
		    		cfImpresa = user.getSoggetto().getCodFiscale();
		    	else
		    		cfImpresa = user.getContact().getVisibilitaDelega();
		    	
				newOperatore.setDelegato(true);
				newOperatore.setcodice_fiscale_impresa(cfImpresa);
				newOperatore.setid_utente_access_ext_delegato(user.getUserId());
			}
			else if (context.getRequest().getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI").equals("_ext")
					&& user.getRoleId() == Role.RUOLO_APICOLTORE) 
			{
				newOperatore.setApicoltore(true);
				if(user.getSoggetto().getCodFiscale()!=null && user.getSoggetto().getCodFiscale().equals(""))
					newOperatore.setApicoltore(false);
				newOperatore.setcodice_fiscale_impresa(user.getSoggetto().getCodFiscale());
			}
			
			newOperatore.queryRecordStabilimentiEstrazione(db);
			
			
			
			
			context.getRequest().setAttribute("OperatoreDettagli", newOperatore);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList lookupStati = new LookupList(db, "lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, ((UserBean) context.getSession()
					.getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList NazioniList = new LookupList(db, "lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);

			LookupList tipoAttivitaApi = new LookupList(db, "apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db, "apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db, "apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db, "apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

			return getReturn(context, "Estrazione");
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

	public String executeCommandCessaAttivita(ActionContext context) {
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		OperatoreList operatoreList = new OperatoreList();

		addModuleBean(context, "View Accounts", "Search Results");

		Connection db = null;
		try {
			db = this.getConnection(context);

			Operatore operatore = new Operatore(db, Integer.parseInt(context.getParameter("idOperatore")));
			operatore.getListaStabilimenti().setIdOperatore(operatore.getIdOperatore());
			operatore.getListaStabilimenti().buildList(db);

			operatore.setModifiedBy(user.getUserId());
			operatore.cessaAttivita(db, context.getParameter("dataCessazione"));
			operatore.setAction(context.getAction().getActionName());
			context.getRequest().setAttribute("opId", operatore.getIdOperatore());

			return executeCommandDetails(context);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}
	
	
	public String executeCommandCessaAttivitaSincronizzata(ActionContext context) 
	{
		//Tracing XML request/responses with JAX-WS
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		addModuleBean(context, "View Accounts", "Search Results");
		Connection db = null;
		
		try 
		{
			db = this.getConnection(context);

			Operatore operatore = new Operatore(db, Integer.parseInt(context.getParameter("idOperatore")));
			operatore.getListaStabilimenti().setIdOperatore(operatore.getIdOperatore());
			operatore.getListaStabilimenti().buildList(db);

			String esito = operatore.sincronizzaCessazioneAttivita(db,user.getUserId());
			if(esito!=null && !esito.equals("OK"))
			{
				context.getRequest().setAttribute("Error", "Errore durante la cessazione: " + esito);
				return ("SystemError");
			}
			
			operatore.setModifiedBy(user.getUserId());
			operatore.setAction(context.getAction().getActionName());
			context.getRequest().setAttribute("opId", operatore.getIdOperatore());

			return executeCommandDetails(context);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e.getMessage());
			return ("SystemError");
		} 
		//Da valutare
		finally
		{
			this.freeConnection(context, db);
		}
	}

	public String executeCommandDetails(ActionContext context) {

		Connection db = null;
		Operatore newOperatore = null;
		try {

			String tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null || tempOpId.equals("-1")) {
				tempOpId = (String) ("" + context.getRequest().getAttribute("opId"));
			}

			Integer tempid = null;

			if (tempOpId != null) {
				tempid = Integer.parseInt(tempOpId);
			}
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
			context.getRequest().setAttribute("dataOdierna", dataOdierna);
			
			newOperatore = new Operatore();

			newOperatore.queryRecordOperatore(db, tempid);
			
			context.getRequest().setAttribute("OperatoreDettagli", newOperatore);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList lookupStati = new LookupList(db, "lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, ((UserBean) context.getSession()
					.getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList NazioniList = new LookupList(db, "lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);

			LookupList tipoAttivitaApi = new LookupList(db, "apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db, "apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db, "apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db, "apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

			abilitazioneTastoCensimenti(context.getRequest());
			
			//Inizio calcolo movimentazioni in ingresso
			boolean mov_in_ingresso = false;
			 //Se non è stato ancora assegnato il codice azienda non posso fare il controllo sui moviemnti in ingresso
			if(newOperatore.getCodiceAzienda()!=null && !newOperatore.getCodiceAzienda().equals(""))
			{
			 MovimentazioniList listaMovimentazioni = new MovimentazioniList();
		     listaMovimentazioni.setCodiceAziendaDestinazione(newOperatore.getCodiceAzienda());
		     ArrayList<Integer> tipiMovimentazioni = new ArrayList<Integer>();
		     tipiMovimentazioni.add(1);
		     tipiMovimentazioni.add(4);
		     listaMovimentazioni.setTipoMovimentazione(tipiMovimentazioni);
		     listaMovimentazioni.setAccettazioneDestinatario(1);
		     listaMovimentazioni.buildList(db);
		     mov_in_ingresso = !listaMovimentazioni.isEmpty();
			}
		    context.getRequest().setAttribute("mov_in_ingresso", mov_in_ingresso);
    		//Fine calcolo movimentazioni in ingresso
			
			return getReturn(context, "Details");
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

	public String executeCommandModifyPregresso(ActionContext context) {

		Connection db = null;
		Operatore newOperatore = null;
		try {

			String tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null) {
				tempOpId = (String) ("" + context.getRequest().getAttribute("opId"));
			}

			Integer tempid = null;

			if (tempOpId != null) {
				tempid = Integer.parseInt(tempOpId);
			}
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			newOperatore = new Operatore();

			newOperatore.queryRecordOperatore(db, tempid);
			context.getRequest().setAttribute("OrgDetails", newOperatore);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, ((UserBean) context.getSession()
					.getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList NazioniList = new LookupList(db, "lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);

			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList tipoAttivitaApi = new LookupList(db, "apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db, "apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db, "apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db, "apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

			return getReturn(context, "ModifyPregresso");
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

	private void resetPagedListInfo(ActionContext context) {
		this.deletePagedListInfo(context, "SearchOrgListInfo");
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

	public String executeCommandInsertSuap(ActionContext context) throws SQLException, Exception,
			OperatoreInsertEsception {

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");

		newOperatore.setEnteredBy(getUserId(context));
		newOperatore.setModifiedBy(getUserId(context));

		UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {

			db = this.getConnection(context);

			LookupList nazioniList = new LookupList(db, "lookup_nazioni");

			/* Costruzione dell'operatore da inserire */
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest(), nazioniList);

			SoggettoFisico soggettoEsistente = null;
			soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			/* se il soggetto non esiste lo aggiungo */

			if (soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <= 0) {
				soggettoAdded.setEnteredBy(getUserId(context));
				soggettoAdded.setModifiedBy(getUserId(context));
				soggettoAdded.insert(db, context);
				newOperatore.setRappLegale(soggettoAdded);

			} else {
				/* se esiste */

				if (soggettoEsistente.getIdSoggetto() > 0) {
					Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();

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
					soggettoEsistente.setEnteredBy(getUserId(context));
					soggettoEsistente.setModifiedBy(getUserId(context));
					soggettoEsistente.setIndirizzo(indirizzoAdded);

					newOperatore.setRappLegale(soggettoEsistente);
				}
			}

			Indirizzo indirizzoAdded = null;
			if (!"".equals(context.getRequest().getParameter("via"))
					&& new Integer(context.getRequest().getParameter("via")).intValue() > 0) {

				indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
				indirizzoAdded.setTipologiaSede(1); // Legale
			} else {
				indirizzoAdded = new Indirizzo(context.getRequest(), nazioniList, db, context);
				indirizzoAdded.setTipologiaSede(1);

			}
			newOperatore.getListaSediOperatore().add(indirizzoAdded);

			/**
			 * CONTROLLO DI ESISTENZA DELL'OPERATORE PER PARTITA IVA
			 */
			List<Operatore> listaOp = newOperatore.checkEsistenzaOperatoreSuap(db);

			int esitoCompare = -1;

			Operatore operatoreTrovato = null;
			for (Operatore operatoreEsistente : listaOp) {
				esitoCompare = newOperatore.compareTo(operatoreEsistente);
				if (esitoCompare == 0) {
					operatoreTrovato = operatoreEsistente;
					break;
				}

			}

			switch (esitoCompare) {
			case -1: {

				isValid = this.validateObject(context, db, newOperatore);

				if (isValid) {
					recordInserted = newOperatore.insert(db, context);
					context.getRequest().setAttribute("opId", newOperatore.getIdOperatore());
				}
				context.getRequest().setAttribute("Operatore", newOperatore);
				return "1";// Inserimento OK

			}
			case 0: {
				/* Riuso */

				newOperatore.setIdOperatore(operatoreTrovato.getIdOperatore());
				context.getRequest().setAttribute("Operatore", newOperatore);
				return "1"; // Inserimento OK
			}

			case 1: {
				String paramSovrascrivi = context.getParameter("sovrascrivi");
				if (paramSovrascrivi == null)
					paramSovrascrivi = "n.d";
				switch (paramSovrascrivi) {

				case "si": {

					soggettoEsistente.update(db, context);
					isValid = this.validateObject(context, db, newOperatore);

					if (isValid) {
						if (listaOp.size() > 0)
							newOperatore.setCodiceInternoImpresa(listaOp.get(0).getCodiceInternoImpresa());

						recordInserted = newOperatore.insert(db, context);
						context.getRequest().setAttribute("opId", newOperatore.getIdOperatore());
					}
					context.getRequest().setAttribute("Operatore", newOperatore);
					return "1";// Inserimento OK
				}
				case "no": {
					/*
					 * L'operatore esiste in banca dati e l'utente non vuole
					 * sovrascrivere i dati.
					 */
					Operatore op = new Operatore();
					op.queryRecordOperatore(db, Integer.parseInt(context.getRequest().getParameter("idOperatore")));
					context.getRequest().setAttribute("Operatore", op);

					return "1"; // Inserimento OK

				}
				case "n.d": {
					/*
					 * la partita iva che si sta inserento corrisponde a un
					 * altro operatore presente in banca dati ma con dati
					 * diversi viene sollevata una eccezione e chiesto
					 * all'utente se vuole sovrasrivere i dati presenti o
					 * mantenere quelli presenti i banca dati
					 */
					context.getRequest().setAttribute("ListaOperatori", listaOp);
					logger.error("OPERATORE CON PARTITA IVA TROVATO MA NON CIRRISPONDENTE ");
					context.getRequest().setAttribute("Operatore", newOperatore);
					return "2";// Inserimento bloccato chiedere all'utente la
								// scelta da fare
				}

				}

				break;
			}
			}

		} catch (SQLException errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return "-1";
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("idOp", newOperatore.getIdOperatore());
		return "";
	}

	public String executeCommandUpdateSoggettoFisicoImpresa(ActionContext context) throws SQLException {

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore = null;

		try {

			db = this.getConnection(context);
			boolean exist = false;

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));

			newOperatore = new Operatore();
			newOperatore.queryRecordOperatore(db, idOperatore);

			// newStabilimento.get
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest());

			soggettoAdded.setModifiedBy(user.getUserId());
			soggettoAdded.setIpModifiedBy(user.getUserRecord().getIp());
			soggettoAdded.setEnteredBy(user.getUserId());
			soggettoAdded.setIpEnteredBy(user.getUserRecord().getIp());
			SoggettoFisico soggettoEsistente = null;
			soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			/* se il soggetto non esiste lo aggiungo */

			if (soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <= 0) {
				soggettoAdded.insert(db, context);
				newOperatore.setRappLegale(soggettoAdded);

			} else {
				/* se esiste */

				if (soggettoEsistente.getIdSoggetto() > 0) {
					Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();

					if ("si".equalsIgnoreCase(context.getParameter("sovrascrivi"))) {
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
						soggettoEsistente.setIndirizzo(indirizzoAdded);
						soggettoEsistente.update(db, context);

					}
					newOperatore.setRappLegale(soggettoEsistente);

				}
			}

			newOperatore.updateSoggettoFisico(db, context);

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		return executeCommandHome(context);

		// return ("InsertOK");

	}

	public String executeCommandUpdateApicoltore(ActionContext context) throws Exception {
		ArrayList<Stabilimento> lista = new ArrayList<Stabilimento>();
		Connection db = null;
		int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
		try {
			db = this.getConnection(context);

			Stabilimento thisApiario = new Stabilimento(db, idStabilimento);

			/**
			 * RECUPERO DATI DA AGGIORNARE
			 */
			thisApiario.getOperatore().setIdTipoAttivita(Integer.parseInt(context.getParameter("idTipoAttivita")));
			thisApiario.getOperatore().setDataInizio(context.getParameter("dataInizio"));
			thisApiario.getOperatore().setTelefono1(context.getParameter("telefono1"));
			thisApiario.getOperatore().setTelefono2(context.getParameter("telefono2"));
			thisApiario.getOperatore().setDomicilioDigitale(context.getParameter("domicilioDigitale"));
			thisApiario.getOperatore().setFlagProduzioneConLaboratorio(
					context.getParameter("produzioneConLaboratorio") != null);

			boolean isValid = validateObject(context, db, thisApiario.getOperatore());
			if (isValid) {
				thisApiario.getOperatore().aggiornaDatiApicoltore(db);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("idStab", idStabilimento);
		StabilimentoAction actionStab = new StabilimentoAction();
		return actionStab.executeCommandDetails(context) + "Apiario";
	}
	
	public String executeCommandModificaTitolare(ActionContext context) throws Exception 
	{
		WsApiAnagraficaPersoneBdn servicePersone = new WsApiAnagraficaPersoneBdn();
		WsApiAnagraficaApistoproBdn serviceApistopro = new WsApiAnagraficaApistoproBdn();
		WsApiAnagraficaAttivita serviceApiattivita = new WsApiAnagraficaAttivita();
		
		int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));
		context.getRequest().setAttribute("opId", idOperatore);
		
		String dataModificaTitolareString = context.getParameter("data_modifica_titolare");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp dataModificaTitolare = new Timestamp(sdf.parse(dataModificaTitolareString).getTime());
		
		String usaProprietarioTrovato = context.getParameter("usa_questo_proprietario");
		Integer idNuovoProprietario = null;
		//Se e' stato spuntato il check per selezionare un proprietario gia' esistente
		if(usaProprietarioTrovato!=null && !usaProprietarioTrovato.equals(""))
			idNuovoProprietario = Integer.parseInt(context.getParameter("id_nuovo_proprietario"));
		
		Connection db = null;
		try
		{
			
			db = this.getConnection(context);
			 
			if(db.getAutoCommit())
				db.setAutoCommit(false);
			
			Operatore operatore = new Operatore(db, idOperatore);
			
			SoggettoFisico nuovoProprietario = null;
			
			//ASSOCIAZIONE OPERATORE CON NUOVO PROPRIETARIO
			//Se i dati del nuovo proprietario sono stati inseriti a mano
			if(idNuovoProprietario==null)
			{
				nuovoProprietario = new SoggettoFisico(context.getRequest().getParameter("codice_fiscale_nuovo_proprietario"), db);
				if(nuovoProprietario.getIdSoggetto()<=0)
				{
					nuovoProprietario = new SoggettoFisico(context.getRequest());
					nuovoProprietario.getIndirizzo().setComuneTesto(context.getRequest().getParameter("addressLegaleCityinput"));
					String comuneTesto = nuovoProprietario.getIndirizzo().getComuneTesto();
					
					if(comuneTesto!=null && !comuneTesto.equals(""))
					{
						String codComune = ext.aspcfs.modules.apiari.base.ComuniAnagrafica.getCodComune(db, comuneTesto);
						nuovoProprietario.getIndirizzo().setCodiceIstatComune(codComune);
						String siglaProvincia = ext.aspcfs.modules.apiari.base.ComuniAnagrafica.getSiglaProvincia(db, comuneTesto);
						nuovoProprietario.getIndirizzo().setSiglaProvincia(siglaProvincia);
					}
					
					nuovoProprietario.insert(db, context);
				}
			}
			//Se il proprietario e' stato selezionato da uno gia' esistente
			else
			{
				nuovoProprietario = new SoggettoFisico(db, idNuovoProprietario);
			}
			
			operatore.setRappLegale(nuovoProprietario);
			//FINE ASSOCIAZIONE OPERATORE CON NUOVO PROPRIETARIO
			
			//AGGIORNAMENTO IN GISA DEI DATI DEL TITOLARE
			/*boolean isValid = validateObject(context, db, operatore);
			if (isValid) 
			{*/
				operatore.setDataInizio(dataModificaTitolare);
				operatore.aggiornaRelazioneSoggettoFisico(db, 1, context);
			/*}*/
			//FINE AGGIORNAMENTO IN GISA DEI DATI DEL TITOLARE
			
			//AGGIORNAMENTO DEL TITOLARE IN BDA-R
			Persone proprietario = servicePersone.searchPersone(nuovoProprietario, db, getUserId(context));
			if (proprietario == null)
				proprietario = servicePersone.insertAnagraficaPersona(nuovoProprietario, db, getUserId(context));
			else
			{
				String risposta = servicePersone.updateAnagraficaPersona(nuovoProprietario, db, getUserId(context));
				if(risposta!=null)
				{
					db.rollback();
					context.getRequest().setAttribute("Error", "Errore durante la modifica dei dati del titolare in BDA-R: " + risposta);
					return new OperatoreAction().executeCommandDetails(context);
				}
			}
			String risposta = null;
			if(proprietario==null || !nuovoProprietario.getCodFiscale().equalsIgnoreCase(proprietario.getIdFiscale()))
			{
				risposta = serviceApistopro.insertPersonaStorico(nuovoProprietario, operatore, dataModificaTitolare, db, getUserId(context));
			}
			if(risposta!=null)
			{
				db.rollback();
				context.getRequest().setAttribute("Error", "Errore durante l'aggiornamento dello storico del proprietario dell'azienda in BDA-R: " + risposta);
				return new OperatoreAction().executeCommandDetails(context);
			}
			
			
			String ragioneSocialeVecchia = operatore.getRagioneSociale();
			operatore.setRagioneSociale(context.getRequest().getParameter("ragione_sociale"));
			operatore.setPartitaIva(context.getRequest().getParameter("partita_iva"));
			operatore.aggiornaRagioneSocialePiva(db, getUserId(context));
			
			if(!context.getRequest().getParameter("ragione_sociale").equals(ragioneSocialeVecchia))
			{
				String rispostaRagioneSociale = serviceApiattivita.updateRagioneSociale(operatore, db, getUserId(context));
				if(rispostaRagioneSociale!=null && !rispostaRagioneSociale.equalsIgnoreCase("OK"))
				{
					db.rollback();
					context.getRequest().setAttribute("Error", "Errore durante l'aggiornamento della ragione sociale in BDA-R: " + risposta);
					return new OperatoreAction().executeCommandDetails(context);
				}
			}
			
			//FINE AGGIORNAMENTO DEL TITOLARE IN BDA-R
			db.commit();

		} 
		catch(ServerSOAPFaultException ex)
		{
			db.rollback();
			context.getRequest().setAttribute("Error", "Errore durante l'aggiornamento dello storico del proprietario dell'azienda in BDA-R: " + ex.getMessage());
			ex.printStackTrace();
		}
		catch (SQLException e) 
		{
			db.rollback();
			context.getRequest().setAttribute("Error", "Errore: " + e.getMessage());
			e.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return new OperatoreAction().executeCommandDetails(context);
	}
	
	public String executeCommandUpdateApicoltoreRecapiti(ActionContext context) throws Exception {
		ArrayList<Stabilimento> lista = new ArrayList<Stabilimento>();
		Connection db = null;
		int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
		try {
			db = this.getConnection(context);

			Stabilimento thisApiario = new Stabilimento(db, idStabilimento);

			/**
			 * RECUPERO DATI DA AGGIORNARE
			 */
			thisApiario.getOperatore().setTelefono1(context.getParameter("telefono1"));
			thisApiario.getOperatore().setTelefono2(context.getParameter("telefono2"));
			thisApiario.getOperatore().setDomicilioDigitale(context.getParameter("domicilioDigitale"));
			thisApiario.getOperatore().setFax(context.getParameter("fax"));

				thisApiario.getOperatore().aggiornaRecapitiApicoltore(db);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("idStab", idStabilimento);
		StabilimentoAction actionStab = new StabilimentoAction();
		return actionStab.executeCommandDetails(context) + "Apiario";
	}
	
	
	public String executeCommandUpdateLaboratorio(ActionContext context) throws Exception
	{
		Connection db = null;
		String msg = "";
		int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));
		boolean laboratorio = (context.getParameter("laboratorio")!=null && context.getParameter("laboratorio").equals("on")) ? true : false;
		String note = context.getParameter("note");

		
		try {
			db = this.getConnection(context);

			Operatore operatore = new Operatore(db,idOperatore);
			operatore.setFlagProduzioneConLaboratorio(laboratorio);
			operatore.aggiornaDatiLaboratorio(db, note, getUserId(context));
			msg ="Laboratorio di smielatura aggiornato in GISA per questo apiario. ";

		}
		catch(SQLException e)
		{

		}
		finally
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("msgAggiornamento", msg);
		context.getRequest().setAttribute("opId", idOperatore);
		return executeCommandDetails(context) ;
	}
	
	
	
	public static void abilitazioneTastoCensimenti(HttpServletRequest req) throws ParseException
	{
		//Gestione Abilitazione tasto censimenti
		SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
		int tolleranzaInserimentoCensimenti = Integer.parseInt(ApplicationProperties.getProperty("tolleranza_inserimento_censimenti"));
		String suffissoLabelTastoCensimenti = "";
		
		Date dataAttuale = forma.parse(forma.format(new Date()));
		String dataCensimentoDaAssegnare = forma.format(new Date());
		
		int annoAttuale = dataAttuale.getYear()+1900;
		int annoPrecedente = annoAttuale-1;
				
		String dataFrom = "01/11/";
		String dataTo = "31/12/";
		
		Date dataFromAnnoAttuale = forma.parse(dataFrom+annoAttuale);
		Date dataToAnnoAttuale   = forma.parse(dataTo+annoAttuale);
		Date dataToTolleranzaAnnoAttuale = forma.parse(dataTo+annoAttuale);
		//Applico tolleranza
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTimeInMillis(dataToAnnoAttuale.getTime());
		gCalendar.add(Calendar.DAY_OF_MONTH, tolleranzaInserimentoCensimenti);
		dataToTolleranzaAnnoAttuale.setTime(gCalendar.getTimeInMillis());
		//Fine applicazione tolleranza
		
		Date dataFromAnnoPrecedente = forma.parse(dataFrom+annoPrecedente);
		Date dataToAnnoPrecedente   = forma.parse(dataTo+annoPrecedente);
		Date dataToTolleranzaAnnoPrecedente = forma.parse(dataTo+annoPrecedente);
		//Applico tolleranza
		gCalendar.setTimeInMillis(dataToAnnoPrecedente.getTime());
		gCalendar.add(Calendar.DAY_OF_MONTH, tolleranzaInserimentoCensimenti);
		dataToTolleranzaAnnoPrecedente.setTime(gCalendar.getTimeInMillis());
		//Fine applicazione tolleranza
		
		boolean inRangeAnnoAttuale    = dataAttuale.compareTo(dataFromAnnoAttuale)>=0    && dataAttuale.compareTo(dataToAnnoAttuale)<=0;
		boolean inRangeAnnoPrecedente = dataAttuale.compareTo(dataFromAnnoPrecedente)>=0 && dataAttuale.compareTo(dataToAnnoPrecedente)<=0;
		boolean inRangeTolleranzaAnnoAttuale    = dataAttuale.compareTo(dataFromAnnoAttuale)>=0    && dataAttuale.compareTo(dataToTolleranzaAnnoAttuale)<=0;
		boolean inRangeTolleranzaAnnoPrecedente = dataAttuale.compareTo(dataFromAnnoPrecedente)>=0 && dataAttuale.compareTo(dataToTolleranzaAnnoPrecedente)<=0;
		
		//Setto range valido per l'anno corrente da mostrare a video
		dataFrom = dataFrom+annoAttuale;
		dataTo   = forma.format(dataToTolleranzaAnnoAttuale);
		//Fine Setto range valido per l'anno corrente da mostrare a video
			
		if(!inRangeAnnoPrecedente && inRangeTolleranzaAnnoPrecedente)
		{
			dataCensimentoDaAssegnare = forma.format(dataToAnnoPrecedente);
			suffissoLabelTastoCensimenti = " " + annoPrecedente;
		}
		
		req.setAttribute("dataFrom", dataFrom);
		req.setAttribute("dataTo",   dataTo);
		req.setAttribute("inRangeAnnoAttuale",    			 inRangeAnnoAttuale);
		req.setAttribute("inRangeAnnoPrecedente", 			 inRangeAnnoPrecedente);
		req.setAttribute("inRangeTolleranzaAnnoAttuale",    inRangeTolleranzaAnnoAttuale);
		req.setAttribute("inRangeTolleranzaAnnoPrecedente", inRangeTolleranzaAnnoPrecedente);
		req.setAttribute("dataCensimentoDaAssegnare",       dataCensimentoDaAssegnare);
		req.setAttribute("suffissoLabelTastoCensimenti",    suffissoLabelTastoCensimenti);
		req.setAttribute("dataInizioAnno", 					forma.parse("01/01/"+annoAttuale));
		if(inRangeTolleranzaAnnoPrecedente)
			req.setAttribute("annoOdierno", 					new Date().getYear()+1900-1);
		else
			req.setAttribute("annoOdierno", 					new Date().getYear()+1900);
		//Fine Gestione Abilitazione tasto censimenti
	}

}
