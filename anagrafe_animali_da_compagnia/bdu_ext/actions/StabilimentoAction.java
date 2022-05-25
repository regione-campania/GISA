/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ColoniaInformazioni;
import org.aspcfs.modules.opu.base.ImportatoreInformazioni;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class StabilimentoAction extends CFSModule {

	public String executeCommandAdd(ActionContext context) {

		// if (!hasPermission(context, "stabilimento-stabilimento-add")) {
		// return ("PermissionError");
		// }
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			UserBean thisUser = (UserBean) context.getSession().getAttribute(
					"User");
			ComuniAnagrafica c = new ComuniAnagrafica();
			// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI ALL'ASL
			// UTENTE
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,
					((UserBean) context.getSession().getAttribute("User"))
							.getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			Stabilimento newStabilimento = null;

			if ((Stabilimento) context.getRequest().getAttribute(
					"newStabilimento") != null)
				newStabilimento = (Stabilimento) context.getRequest()
						.getAttribute("newStabilimento");
			else
				newStabilimento = (Stabilimento) context.getRequest()
						.getAttribute("Stabilimento");

			int idOperatore = -1;
			if (context.getRequest().getParameter("idOp") != null)
				idOperatore = Integer.parseInt(context.getRequest()
						.getParameter("idOp"));
			else if (context.getRequest().getAttribute("idOp") != null)
				idOperatore = (Integer) (context.getRequest()
						.getAttribute("idOp"));
			else
				idOperatore = newStabilimento.getIdOperatore();

			newStabilimento.queryRecordOperatore(db, idOperatore);
			if (newStabilimento.getSedeOperativa().getComune() <= 0)
				newStabilimento.getSedeOperativa().setComune(-1);
			context.getRequest().setAttribute("newStabilimento",
					newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			LineaProduttivaList lpList = new LineaProduttivaList();
			lpList.buildList(db);
			context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
			context.getRequest().setAttribute(
					"tipologiaSoggetto",
					(String) context.getRequest().getParameter(
							"tipologiaSoggetto"));

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("systemStatus",
				this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "AddStabilimento");
	}

	public String executeCommandInsert(ActionContext context)
			throws SQLException {

		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Stabilimento insertedStab = null;
		// Integer orgId = null;
		//Stabilimento newStabilimento = (Stabilimento) context.getFormBean();
		Stabilimento newStabilimento = (Stabilimento)context.getRequest().getAttribute("Stabilimento");


		if (("false").equals((String) context.getParameter("doContinueStab"))) { // Ho
																					// scelto
																					// in
			LineaProduttiva lp = null;
			// lineaproduttiva
			// scelto
			// LISTA LINEE PRODUTTIVE
			if (context.getRequest().getParameterValues("idLineaProduttiva") != null
					&& context.getRequest().getParameterValues(
							"idLineaProduttiva").length > 0) {
				
				try {
					db = this.getConnection(context);
					LineaProduttivaList arrayListeProduttiveDaConservare = new LineaProduttivaList();
					String[] lineeProduttiveSelezionate = context.getRequest()
							.getParameterValues("idLineaProduttiva");
					for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
						if (!lineeProduttiveSelezionate[i].equals("")) {
							lp = new LineaProduttiva(db, Integer
									.parseInt(lineeProduttiveSelezionate[i]));
/*							lp = new LineaProduttiva(db, Integer
									.parseInt(lineeProduttiveSelezionate[i]));*/
							/*switch (new Integer(lineeProduttiveSelezionate[i]).intValue()) {
							case LineaProduttiva.idAggregazioneImportatore:
								lp = (ImportatoreInformazioni) context.getRequest().getAttribute("ImportatoreInformazioni");
								break;

							default:
								lp = new LineaProduttiva(db, Integer
										.parseInt(lineeProduttiveSelezionate[i]));
								break;
							}*/
							arrayListeProduttiveDaConservare.add(lp);
						}
					}
					newStabilimento
							.setListaLineeProduttive(arrayListeProduttiveDaConservare);
				} catch (Exception e) {
					// TODO: handle exception
				}																		// regione
																					// o
																					// fuori
			//

			// Controllo se ho scelto un operatore fuori regione

			if (context.getRequest().getParameter("inregione") != null
					&& ("no").equals(context.getRequest().getParameter(
							"inregione")))
				newStabilimento.setFlagFuoriRegione(true);
			
			}

			context.getRequest().setAttribute("LineaProduttivaScelta", lp);
			context.getRequest().setAttribute("newStabilimento", newStabilimento);
			return executeCommandAdd(context);
		}
		newStabilimento.setEnteredBy(getUserId(context));
		newStabilimento.setModifiedBy(getUserId(context));
		try {

			db = this.getConnection(context);

			SoggettoFisico soggettoAdded = null;

			if (context.getRequest().getParameter("codFiscaleSoggetto") != null) {
				if (new Integer(context.getRequest().getParameter(
						"codFiscaleSoggetto")).intValue() > 0)

				{
					soggettoAdded = new SoggettoFisico(context.getRequest());
					UserBean user = (UserBean) context.getRequest()
							.getSession().getAttribute("User");
					soggettoAdded.setModifiedBy(user.getUserId());
					soggettoAdded.setIpModifiedBy(user.getUserRecord().getIp());
				} else {
					soggettoAdded = new SoggettoFisico(context.getRequest(), db);

				}
			} else {
				Operatore added = new Operatore();
				added
						.queryRecordOperatore(db, newStabilimento
								.getIdOperatore());
				soggettoAdded = added.getRappLegale();
			}
			newStabilimento.setRappLegale(soggettoAdded);
			Indirizzo indirizzoAdded = null;
			if (new Integer(context.getRequest().getParameter("via"))
					.intValue() > 0) {
				indirizzoAdded = new Indirizzo(db, new Integer(context
						.getRequest().getParameter("via")).intValue());
				indirizzoAdded.setTipologiaSede(5); // Operativa
			} else {
				indirizzoAdded = new Indirizzo(context.getRequest(), db);
				indirizzoAdded.setTipologiaSede(1);

			}

			newStabilimento.setSedeOperativa(indirizzoAdded);

			for (int i = 0; i < newStabilimento.getListaLineeProduttive()
					.size(); i++) {
				newStabilimento.getListaLineeProduttive().remove(i);
			}
			if (context.getRequest().getParameterValues("idLineaProduttiva") != null
					&& context.getRequest().getParameterValues(
							"idLineaProduttiva").length > 0) {
				LineaProduttiva lp = null;

				String[] lineeProduttiveSelezionate = context.getRequest()
						.getParameterValues("idLineaProduttiva");
				for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
					if (!lineeProduttiveSelezionate[i].equals("")) {
						
						
						switch (new Integer(lineeProduttiveSelezionate[i]).intValue()) {
						case LineaProduttiva.idAggregazioneImportatore:
							lp = (ImportatoreInformazioni) context.getRequest().getAttribute("ImportatoreInformazioni");
							break;
						case LineaProduttiva.idAggregazioneColonia:
							lp = (ColoniaInformazioni) context.getRequest().getAttribute("ColoniaInformazioni");
							break;
						default:
							lp = new LineaProduttiva(db, Integer
									.parseInt(lineeProduttiveSelezionate[i]));
							break;
						}
						lp.setIdRelazioneAttivita(lineeProduttiveSelezionate[i]);
						if (context.getRequest().getParameter(
								"dataInizio" + lp.getIdRelazioneAttivita()) != null)
							lp.setDataInizio(context.getRequest().getParameter(
									"dataInizio" + lp.getIdRelazioneAttivita()));
						if (context.getRequest().getParameter(
								"dataFine" + lp.getIdRelazioneAttivita()) != null)
							lp.setDataFine(context.getRequest().getParameter(
									"dataFine" + lp.getIdRelazioneAttivita()));
						if (context.getRequest().getParameter(
								"stato" + lp.getIdRelazioneAttivita()) != null)
							lp.setStato(Integer.parseInt(context.getRequest()
									.getParameter("stato" + lp.getIdRelazioneAttivita())));
				/*		lp = new LineaProduttiva(db, Integer
								.parseInt(lineeProduttiveSelezionate[i]));*/
						
/*						
				lp = new LineaProduttiva(db, Integer
								.parseInt(lineeProduttiveSelezionate[i]));
						if (context.getRequest().getParameter(
								"dataInizio" + lp.getId()) != null)
							lp.setDataInizio(context.getRequest().getParameter(
									"dataInizio" + lp.getId()));
						if (context.getRequest().getParameter(
								"dataFine" + lp.getId()) != null)
							lp.setDataFine(context.getRequest().getParameter(
									"dataFine" + lp.getId()));
						if (context.getRequest().getParameter(
								"stato" + lp.getId()) != null)
							lp.setStato(Integer.parseInt(context.getRequest()
									.getParameter("stato" + lp.getId())));*/
						newStabilimento.getListaLineeProduttive().add(lp);
					}
				}

			}
			/*
			 * if (context.getRequest().getParameter("doContinue") != null &&
			 * !context.getRequest().getParameter("doContinue").equals( "") &&
			 * context.getRequest().getParameter("doContinue").equals( "false"))
			 * { return executeCommandAdd(context); }
			 */

			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			String ip = user.getUserRecord().getIp();
			
			String inRegione = (String) context.getRequest().getParameter("inregione");
			
			if (inRegione != null){
				newStabilimento.setFlagFuoriRegione(inRegione);
			}
			Object[] asl;
			if ( !newStabilimento.isFlagFuoriRegione()) // Se nn sto
																// considerando
																// un sindaco
																// fuori regione
				// e nemmeno un proprietario di tipo fuori regione
				asl = DwrUtil.getValoriAsl(indirizzoAdded.getComune());
			else
				asl = null;

			if (asl != null && asl.length > 0) {

				Object[] aslVal = (Object[]) asl[0];
				if (aslVal != null && aslVal.length > 0)
					newStabilimento.setIdAsl((Integer) aslVal[0]);

			} else{
				newStabilimento.setIdAsl(user.getSiteId());
			}
			isValid = this.validateObject(context, db, newStabilimento);

			if (isValid) {
				recordInserted = newStabilimento.insert(db, true);

			}
			if (recordInserted) {

				Stabilimento stabilimentoInserito = new Stabilimento(db,
						newStabilimento.getIdOperatore(), newStabilimento
								.getIdStabilimento());
				context.getRequest().setAttribute("Stabilimento",
						stabilimentoInserito);

				// Informazioni aggiuntive sulla colonia
				// Lasciare così o creare un meccanismo diverso?   //Meccanismo diverso, bean ColoniaInformazioni!!
				if (((LineaProduttiva) stabilimentoInserito
						.getListaLineeProduttive().get(0))
						.getIdRelazioneAttivita() == Operatore.BDU_COLONIA) {/*
					// Dati colonia
					Timestamp data_registrazione_colonia = DateUtils
							.parseDateStringNew((String) context.getRequest()
									.getParameter("dataRegistrazioneColonia"),
									"dd/MM/yyyy");
					String nrProtocollo = (String) context.getRequest()
							.getParameter("nrProtocollo");
					int nrTotaleGatti = new Integer((String) context
							.getRequest().getParameter("nrGattiTotale"))
							.intValue();
					boolean flagTotalePresunto = DatabaseUtils
							.parseBoolean((String) context.getRequest()
									.getParameter("totalePresunto"));
					int nrTotaleFGatti = new Integer((String) context
							.getRequest().getParameter("nrGattiFTotale"))
							.intValue();
					boolean flagTotaleFPresunto = DatabaseUtils
							.parseBoolean((String) context.getRequest()
									.getParameter("totaleFPresunto"));
					int nrTotaleMGatti = new Integer((String) context
							.getRequest().getParameter("nrGattiMTotale"))
							.intValue();
					boolean flagTotaleMPresunto = DatabaseUtils
							.parseBoolean((String) context.getRequest()
									.getParameter("totaleMPresunto"));
					String nomeVeterinario = (String) context.getRequest()
							.getParameter("nomeVeterinario");

					String query = "INSERT INTO opu_informazioni_colonia(nr_totale_gatti, totale_presunto, "
							+ "data_registrazione_colonia, nr_protocollo, nominativo_veterinario,"
							+ "totale_maschi, totale_maschi_flag_presunto, totale_femmine, totale_femmine_flag_presunto, "
							+ "id_relazione_stabilimento_linea_produttiva)"
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

					PreparedStatement pst = db.prepareStatement(query
							.toString());
					int i = 0;
					pst.setInt(++i, nrTotaleGatti);
					pst.setBoolean(++i, flagTotalePresunto);
					pst.setTimestamp(++i, data_registrazione_colonia);
					pst.setString(++i, nrProtocollo);
					pst.setString(++i, nomeVeterinario);
					pst.setInt(++i, nrTotaleMGatti);
					pst.setBoolean(++i, flagTotaleMPresunto);
					pst.setInt(++i, nrTotaleFGatti);
					pst.setBoolean(++i, flagTotaleFPresunto);
					pst.setInt(++i, ((LineaProduttiva) stabilimentoInserito
							.getListaLineeProduttive().get(0)).getId());

					pst.execute();

				*/}

				context.getRequest().setAttribute("opId",
						stabilimentoInserito.getIdOperatore());
				context.getRequest().setAttribute("idStab",
						stabilimentoInserito.getIdStabilimento());

				String contextName = context.getRequest().getParameter(
						"context");
				if (contextName.equalsIgnoreCase("bdu")) {
					context.getRequest().setAttribute(
							"opId",
							""
									+ ((LineaProduttiva) stabilimentoInserito
											.getListaLineeProduttive().get(0))
											.getId());
					if (context.getRequest().getParameter("doContinue") != null
							&& !context.getRequest().getParameter("doContinue")
									.equals("")
							&& context.getRequest().getParameter("doContinue")
									.equals("false")) {
						context.getRequest().setAttribute(
								"opId",
								new Integer(((LineaProduttiva) newStabilimento
										.getListaLineeProduttive().get(0))
										.getId()).toString());
						context.getRequest().setAttribute(
								"tipologiaSoggetto",
								(String) context.getRequest().getAttribute(
										"tipologiaSoggetto"));
						return ("ClosePopupOK");
					}
					OperatoreAction opAction = new OperatoreAction();
					return opAction.executeCommandDetails(context);

				}
			}

			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			/*
			 * context.getRequest().setAttribute("commandD",
			 * "Accounts.do?command=Details");
			 * context.getRequest().setAttribute("org_cod", "&orgId=" +
			 * newOperatore.getIdOperatore());
			 */

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		// context.getRequest().setAttribute("opId", new
		// Integer(newOperatore.getIdOperatore()).toString());
		return (executeCommandDetails(context));

		// return ("InsertOK");

	}

	public String executeCommandDetails(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {

			String tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null) {
				tempOpId = ""
						+ (Integer) context.getRequest().getAttribute("opId");
			}

			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;
			Integer stabid = null;

			if (tempOpId != null) {
				tempid = Integer.parseInt(tempOpId);
			}

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);

			newStabilimento = new Stabilimento(db, tempid, stabid);
			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

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

			return getReturn(context, "Details");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}
}
