/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.CanileInformazioni;
import org.aspcfs.modules.opu.base.ColoniaInformazioni;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.GestoreComunicazioniGisa;
import org.aspcfs.modules.opu.base.ImportatoreInformazioni;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.RegistrazioneChiusuraLineaProduttiva;
import org.aspcfs.modules.opu.base.RegistrazioneRiaperturaLineaProduttiva;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
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
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			ComuniAnagrafica c = new ComuniAnagrafica();
			// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI ALL'ASL
			// UTENTE
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, ((UserBean) context.getSession()
					.getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			Stabilimento newStabilimento = null;

			if ((Stabilimento) context.getRequest().getAttribute("newStabilimento") != null)
				newStabilimento = (Stabilimento) context.getRequest().getAttribute("newStabilimento");
			else
				newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");

			int idOperatore = -1;
			if (context.getRequest().getParameter("idOp") != null)
				idOperatore = Integer.parseInt(context.getRequest().getParameter("idOp"));
			else if (context.getRequest().getAttribute("idOp") != null)
				idOperatore = (Integer) (context.getRequest().getAttribute("idOp"));
			else
				idOperatore = newStabilimento.getIdOperatore();

			newStabilimento.queryRecordOperatore(db, idOperatore);
			if (newStabilimento.getSedeOperativa().getComune() <= 0)
				newStabilimento.getSedeOperativa().setComune(-1);
			context.getRequest().setAttribute("newStabilimento", newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_asl_rif");
			context.getRequest().setAttribute("AslList", aslList);

			LineaProduttivaList lpList = new LineaProduttivaList();
			lpList.buildList(db);
			context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
			context.getRequest().setAttribute("tipologiaSoggetto",
					(String) context.getRequest().getParameter("tipologiaSoggetto"));

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "AddStabilimento");
	}

	public String executeCommandInsert(ActionContext context) throws SQLException {

		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Stabilimento insertedStab = null;
		int idRelazione = Integer.parseInt(context.getRequest().getParameter("idRelazione"));
		// Integer orgId = null;
		// Stabilimento newStabilimento = (Stabilimento) context.getFormBean();
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");

		if (("false").equals((String) context.getParameter("doContinueStab"))) { // Ho
			// scelto
			// in
			LineaProduttiva lp = null;
			// lineaproduttiva
			// scelto
			// LISTA LINEE PRODUTTIVE
			if (context.getRequest().getParameterValues("idLineaProduttiva") != null
					&& context.getRequest().getParameterValues("idLineaProduttiva").length > 0) {

				try {
					db = this.getConnection(context);
					LineaProduttivaList arrayListeProduttiveDaConservare = new LineaProduttivaList();

					String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
					for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
						if (!lineeProduttiveSelezionate[i].equals("")) {
							lp = new LineaProduttiva(db, Integer.parseInt(lineeProduttiveSelezionate[i]));

							/*
							 * lp = new LineaProduttiva(db, Integer
							 * .parseInt(lineeProduttiveSelezionate[i]));
							 */
							/*
							 * switch (new
							 * Integer(lineeProduttiveSelezionate[i])
							 * .intValue()) { case
							 * LineaProduttiva.idAggregazioneImportatore: lp =
							 * (ImportatoreInformazioni)
							 * context.getRequest().getAttribute
							 * ("ImportatoreInformazioni"); break;
							 * 
							 * default: lp = new LineaProduttiva(db, Integer
							 * .parseInt(lineeProduttiveSelezionate[i])); break;
							 * }
							 */
							arrayListeProduttiveDaConservare.add(lp);
						}
					}
					newStabilimento.setListaLineeProduttive(arrayListeProduttiveDaConservare);
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					this.freeConnection(context, db);

				}

				// regione
				// o
				// fuori
				//

				// Controllo se ho scelto un operatore fuori regione

				if (context.getRequest().getParameter("inregione") != null
						&& ("no").equals(context.getRequest().getParameter("inregione")))
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
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

			if (context.getRequest().getParameter("addressLegaleLine1") != null) {
				soggettoAdded = new SoggettoFisico(context.getRequest());
				SoggettoFisico soggettoEsistente = soggettoAdded.verificaSoggetto(db);

				/* se il soggetto non esiste lo aggiungo */
				if (soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <= 0) {
					if(soggettoAdded.getIndirizzo().getCap()!=null && context.getRequest().getAttribute("cap")!=null && !soggettoAdded.getIndirizzo().getCap().equals((String)context.getRequest().getAttribute("cap")))
					{
						soggettoAdded.getIndirizzo().setIdIndirizzo(-1);
						soggettoAdded.getIndirizzo().setCap((String)context.getRequest().getAttribute("cap"));
					}
					soggettoAdded.insert(db);
					newStabilimento.setRappLegale(soggettoAdded);
				} else {
					/* se esiste */

					if (soggettoEsistente.getIdSoggetto() > 0) {
						Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();
						Indirizzo indirizzoEsistente = soggettoEsistente.getIndirizzo();

						/* se l'asl di residenza non coincide */
						if (indirizzoAdded.calcolaAsl(db) != indirizzoEsistente.calcolaAsl(db)) {
							newStabilimento.setRappLegale(soggettoAdded);

							context.getRequest().setAttribute("Errore", "Asl");
							context.getRequest().setAttribute("OperatoreDettagli", newStabilimento);
							context.getRequest().setAttribute("SoggettoEsistente", soggettoEsistente);
							return executeCommandAdd(context);
						} else {
							if ("si".equalsIgnoreCase(context.getParameter("sovrascrivi"))) {
								soggettoEsistente.setNome(soggettoAdded.getNome());
								soggettoEsistente.setCognome(soggettoAdded.getCognome());
								soggettoEsistente.setDataNascita(soggettoAdded.getDataNascita());
								soggettoEsistente.setComuneNascita(soggettoAdded.getComuneNascita());

								soggettoEsistente.setDocumentoIdentita(soggettoAdded.getDocumentoIdentita());
								soggettoEsistente.setFax(soggettoAdded.getFax());
								soggettoEsistente.setIndirizzo(indirizzoAdded);
								soggettoEsistente.setProvinciaNascita(soggettoAdded.getProvinciaNascita());
								soggettoEsistente.setTelefono1(soggettoAdded.getTelefono1());
								soggettoEsistente.setTelefono2(soggettoAdded.getTelefono2());
								soggettoEsistente.setEmail(soggettoAdded.getEmail());
								soggettoEsistente.setModifiedBy(user.getUserId());
								soggettoEsistente.setIpModifiedBy(user.getUserRecord().getIp());

								soggettoEsistente.update(db);

								PreparedStatement pst = null;

								String sel = "select id_operatore from opu_stabilimento where id in "
										+ "(select id_stabilimento from opu_relazione_stabilimento_linee_produttive op where op.id_linea_produttiva in (?,?,?) and op.trashed_date is null ) "
										+ "and id_soggetto_fisico = ?; ";
								pst = db.prepareStatement(sel);
								pst.setInt(1, LineaProduttiva.idAggregazionePrivato);
								pst.setInt(2, LineaProduttiva.idAggregazioneSindaco);
								pst.setInt(3, LineaProduttiva.idAggregazioneSindacoFR);
								pst.setInt(4, soggettoEsistente.getIdSoggetto());
								java.sql.ResultSet rs1 = pst.executeQuery();

								String update = "update opu_stabilimento set id_indirizzo = ? where id in "
										+ "(select id_stabilimento from opu_relazione_stabilimento_linee_produttive op where op.id_linea_produttiva in (?,?,?) and op.trashed_date is null ) "
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

							newStabilimento.setRappLegale(soggettoEsistente);
							/* temporaneamente prendo quello che ho */
						}
					}

				}

			}

			// SOLO SU COLONIA
			if (idRelazione == LineaProduttiva.idAggregazioneColonia) {
				// Operatore newOperatore = new Operatore();
				// newOperatore.queryRecordOperatore(db,
				// newStabilimento.getIdOperatore());
				// soggettoAdded = newOperatore.getRappLegale();

				String idSoggettoRappLegale = (String) context.getRequest().getParameter("idRappLegale");

				if (idSoggettoRappLegale != null && !("-1").equals(idSoggettoRappLegale) && !("").equals(idSoggettoRappLegale))
					soggettoAdded = new SoggettoFisico(db, Integer.parseInt(idSoggettoRappLegale));
			}

			newStabilimento.setRappLegale(soggettoAdded);
			Indirizzo indirizzoAdded = null;
			if (idRelazione == LineaProduttiva.idAggregazioneCanile && context.getRequest().getParameter("via")!=null) {
				indirizzoAdded = new Indirizzo(context.getRequest(), db);
				indirizzoAdded.setTipologiaSede(5); // Operativa
				if(indirizzoAdded.getCap()!=null && context.getRequest().getParameter("cap")!=null && !indirizzoAdded.getCap().equals(context.getRequest().getParameter("cap")))
				{
					indirizzoAdded.setIdIndirizzo(-1);
					indirizzoAdded.setCap(context.getRequest().getParameter("cap"));
					indirizzoAdded.insert(db);
				}
			}
			else if (idRelazione != LineaProduttiva.idAggregazioneColonia && new Integer(context.getRequest().getParameter("via")).intValue() > 0) {
				indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
				indirizzoAdded.setTipologiaSede(5); // Operativa
				if(indirizzoAdded.getCap()!=null && context.getRequest().getParameter("cap")!=null && !indirizzoAdded.getCap().equals(context.getRequest().getParameter("cap")))
				{
					indirizzoAdded.setIdIndirizzo(-1);
					indirizzoAdded.setCap(context.getRequest().getParameter("cap"));
					indirizzoAdded.insert(db);
				}
			} else {
				indirizzoAdded = new Indirizzo(context.getRequest(), db);
				indirizzoAdded.setTipologiaSede(1);

			}

			newStabilimento.setSedeOperativa(indirizzoAdded);

			for (int i = 0; i < newStabilimento.getListaLineeProduttive().size(); i++) {
				newStabilimento.getListaLineeProduttive().remove(i);
			}
			if (context.getRequest().getParameterValues("idLineaProduttiva") != null
					&& context.getRequest().getParameterValues("idLineaProduttiva").length > 0) {
				LineaProduttiva lp = null;

				String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
				for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
					if (!lineeProduttiveSelezionate[i].equals("")) {

						switch (new Integer(lineeProduttiveSelezionate[i]).intValue()) {
						case LineaProduttiva.idAggregazioneImportatore:
							lp = (ImportatoreInformazioni) context.getRequest().getAttribute("ImportatoreInformazioni");
							break;
						case LineaProduttiva.idAggregazioneColonia:
							lp = (ColoniaInformazioni) context.getRequest().getAttribute("ColoniaInformazioni");
							break;
						case LineaProduttiva.idAggregazioneCanile:
							lp = (CanileInformazioni) context.getRequest().getAttribute("CanileInformazioni");
							break;
						default:
							lp = new LineaProduttiva(db, Integer.parseInt(lineeProduttiveSelezionate[i]));
							break;
						}
						lp.setIdRelazioneAttivita(lineeProduttiveSelezionate[i]);
						if (context.getRequest().getParameter("dataInizio" + lp.getIdRelazioneAttivita()) != null)
							lp.setDataInizio(context.getRequest().getParameter(
									"dataInizio" + lp.getIdRelazioneAttivita()));
						if (context.getRequest().getParameter("dataFine" + lp.getIdRelazioneAttivita()) != null)
							lp.setDataFine(context.getRequest().getParameter("dataFine" + lp.getIdRelazioneAttivita()));
						if (context.getRequest().getParameter("stato" + lp.getIdRelazioneAttivita()) != null)
							lp.setStato(Integer.parseInt(context.getRequest().getParameter(
									"stato" + lp.getIdRelazioneAttivita())));

						lp.setTelefono1(context.getRequest().getParameter("telefono1_lp"));
						lp.setTelefono2(context.getRequest().getParameter("telefono2_lp"));

						lp.setMail1(context.getRequest().getParameter("mail1_lp"));
						lp.setMail2(context.getRequest().getParameter("mail2_lp"));
						lp.setFax(context.getRequest().getParameter("fax_lp"));

						lp.setAutorizzazione(context.getRequest().getParameter("autorizzazione"));

						/*
						 * lp = new LineaProduttiva(db, Integer
						 * .parseInt(lineeProduttiveSelezionate[i]));
						 */

						/*
						 * lp = new LineaProduttiva(db, Integer
						 * .parseInt(lineeProduttiveSelezionate[i])); if
						 * (context.getRequest().getParameter( "dataInizio" +
						 * lp.getId()) != null)
						 * lp.setDataInizio(context.getRequest().getParameter(
						 * "dataInizio" + lp.getId())); if
						 * (context.getRequest().getParameter( "dataFine" +
						 * lp.getId()) != null)
						 * lp.setDataFine(context.getRequest().getParameter(
						 * "dataFine" + lp.getId())); if
						 * (context.getRequest().getParameter( "stato" +
						 * lp.getId()) != null)
						 * lp.setStato(Integer.parseInt(context.getRequest()
						 * .getParameter("stato" + lp.getId())));
						 */
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

			String ip = user.getUserRecord().getIp();

			String inRegione = (String) context.getRequest().getParameter("inregione");

			if (inRegione != null) {
				newStabilimento.setFlagFuoriRegione(inRegione);
			}
			Object[] asl;
			if (!newStabilimento.isFlagFuoriRegione()) // Se nn sto
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

			} else {
				newStabilimento.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
			}
			isValid = this.validateObject(context, db, newStabilimento);

			if (isValid) {
				recordInserted = newStabilimento.insert(db, true);

			}
			if (recordInserted) {

				Stabilimento stabilimentoInserito = new Stabilimento(db, newStabilimento.getIdOperatore(),
						newStabilimento.getIdStabilimento());
				context.getRequest().setAttribute("Stabilimento", stabilimentoInserito);

				/**
				 * DEVO INSERIRE OPERATORE ANCHE IN GISA SE E' operatore
				 * commeciale o importatore
				 */

				if (stabilimentoInserito.getListaLineeProduttive().get(0) != null) {
					LineaProduttiva lpInserita = ((LineaProduttiva) stabilimentoInserito.getListaLineeProduttive().get(
							0));
					ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
							"applicationPrefs");

					if (lpInserita.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale
							|| lpInserita.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore) {

						GestoreComunicazioniGisa gGisa = new GestoreComunicazioniGisa();
						gGisa.inserisciOperatoreCommercialeInGisa(db, stabilimentoInserito, applicationPrefs);

					} /*else if (lpInserita.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile) {
						GestoreComunicazioniGisa gGisa = new GestoreComunicazioniGisa();
						gGisa.inserisciCanileInGisa(db, stabilimentoInserito, applicationPrefs);

					}*/ else if (lpInserita.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia) {
						GestoreComunicazioniGisa gGisa = new GestoreComunicazioniGisa();
						gGisa.inserisciColoniaInGisa(db, stabilimentoInserito, applicationPrefs);
					}
				}

				// Informazioni aggiuntive sulla colonia
				// Lasciare così o creare un meccanismo diverso? //Meccanismo
				// diverso, bean ColoniaInformazioni!!
				if (((LineaProduttiva) stabilimentoInserito.getListaLineeProduttive().get(0)).getIdRelazioneAttivita() == Operatore.BDU_COLONIA) {
				}

				context.getRequest().setAttribute("opId", stabilimentoInserito.getIdOperatore());
				context.getRequest().setAttribute("idStab", stabilimentoInserito.getIdStabilimento());

				String contextName = context.getRequest().getParameter("context");
				if (contextName.equalsIgnoreCase("bdu_ext")) {
					context.getRequest().setAttribute("opId",
							"" + ((LineaProduttiva) stabilimentoInserito.getListaLineeProduttive().get(0)).getId());
					if (context.getRequest().getParameter("doContinue") != null
							&& !context.getRequest().getParameter("doContinue").equals("")
							&& context.getRequest().getParameter("doContinue").equals("false")) {
						context.getRequest().setAttribute(
								"opId",
								new Integer(((LineaProduttiva) newStabilimento.getListaLineeProduttive().get(0))
										.getId()).toString());
						context.getRequest().setAttribute("tipologiaSoggetto",
								(String) context.getRequest().getAttribute("tipologiaSoggetto"));
						return ("ClosePopupOK");
					}

					/**
					 * aggiornamento della tabella che materializza la vista
					 * degli operatori denormalizzati.l'aggiornamento avviene
					 * cancellando e reinserendo il record dalla tabella. il
					 * record inserito è preso dalla vista.
					 */

					Operatore opInserito = new Operatore();
					try {
						opInserito.queryRecordOperatore(db, newStabilimento.getIdOperatore());
					} catch (IndirizzoNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					opInserito.aggiornaVistaMaterializzata(db);

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

			/**
			 * aggiornamento della tabella che materializza la vista degli
			 * operatori denormalizzati.l'aggiornamento avviene cancellando e
			 * reinserendo il record dalla tabella. il record inserito è preso
			 * dalla vista.
			 */

			Operatore opInserito = new Operatore();

			opInserito.queryRecordOperatore(db, newStabilimento.getIdOperatore());

			opInserito.aggiornaVistaMaterializzata(db);

			// context.getRequest().setAttribute("opId", new
			// Integer(newOperatore.getIdOperatore()).toString());
			return (executeCommandDetails(context));

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);

		}

		//
		// /**
		// * aggiornamento della tabella che materializza la vista degli
		// operatori
		// * denormalizzati.l'aggiornamento avviene cancellando e reinserendo il
		// record dalla tabella.
		// * il record inserito è preso dalla vista.
		// */
		//
		//
		// Operatore opInserito = new Operatore();
		// try {
		// opInserito.queryRecordOperatore(db,
		// newStabilimento.getIdOperatore());
		// } catch (IndirizzoNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// opInserito.aggiornaVistaMaterializzata(db);
		//
		//
		// // context.getRequest().setAttribute("opId", new
		// // Integer(newOperatore.getIdOperatore()).toString());
		// return (executeCommandDetails(context));

		// return ("InsertOK");

	}

	public String executeCommandDetails(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {

			String tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null) {
				tempOpId = "" + (Integer) context.getRequest().getAttribute("opId");
			}

			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = (String) context.getRequest().getAttribute("idStab");
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
			context.getRequest().setAttribute("StabilimentoDettaglio", newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_asl_rif");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

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

	public String executeCommandChiudiLineaProduttiva(ActionContext context) {

		Connection db = null;
		String tempOpId = null;
		

		try {
			
			tempOpId = context.getRequest().getParameter("opId");
			
			Integer tempid = null;
	

			if (tempOpId != null && !("").equals(tempOpId)) {
				tempid = Integer.parseInt(tempOpId);
			}

//			Operatore toClose = new Operatore();
//			toClose.queryRecordOperatorebyIdLineaProduttiva(db, tempid);
			
			RegistrazioneChiusuraLineaProduttiva registrazione = (RegistrazioneChiusuraLineaProduttiva) context.getRequest().getAttribute("RegistrazioneChiusuraLineaProduttiva");
			
			
			//RegistrazioneChiusuraLineaProduttiva registrazione = new RegistrazioneChiusuraLineaProduttiva();
			registrazione.setEnteredby(this.getUserId(context));
			registrazione.setModifiedby(this.getUserId(context));
			registrazione.setIdLineaProduttiva(tempid);
			registrazione.setIdRelazioneStabilimentoLineaProduttiva(tempid);
			
			db = this.getConnection(context);
			registrazione.insert(db);
			
//			LineaProduttiva lp = new LineaProduttiva();
//			
//			lp.queryRecord(db, tempid);
//			lp.chiudi(db);




		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("opId", tempOpId);
	    return getReturn(context, "DettaglioLinea");

	}
	
	
	public String executeCommandApriLineaProduttiva(ActionContext context) {

		Connection db = null;
		String tempOpId = null;
		

		try {
			
			tempOpId = context.getRequest().getParameter("opId");
			
			Integer tempid = null;
	

			if (tempOpId != null && !("").equals(tempOpId)) {
				tempid = Integer.parseInt(tempOpId);
			}

//			Operatore toClose = new Operatore();
//			toClose.queryRecordOperatorebyIdLineaProduttiva(db, tempid);
			
			RegistrazioneRiaperturaLineaProduttiva registrazione = (RegistrazioneRiaperturaLineaProduttiva) context.getRequest().getAttribute("RegistrazioneRiaperturaLineaProduttiva");
			
			
			//RegistrazioneChiusuraLineaProduttiva registrazione = new RegistrazioneChiusuraLineaProduttiva();
			registrazione.setEnteredby(this.getUserId(context));
			registrazione.setModifiedby(this.getUserId(context));
			registrazione.setIdLineaProduttiva(tempid);
			registrazione.setIdRelazioneStabilimentoLineaProduttiva(tempid);
			
			db = this.getConnection(context);
			registrazione.insert(db);
			
//			LineaProduttiva lp = new LineaProduttiva();
//			
//			lp.queryRecord(db, tempid);
//			lp.chiudi(db);




		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("opId", tempOpId);
	    return getReturn(context, "DettaglioLinea");

	}
}
