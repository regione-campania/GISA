/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.registrotrasgressori.base.Pagamento;
import org.aspcfs.modules.registrotrasgressori.utils.PagoPaUtil;
import org.json.JSONObject;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class ReloadUtenti
 */
public class ControllaPagamentoPagoPA extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger("MainLogger");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllaPagamentoPagoPA() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ConnectionElement ce = null;
		ConnectionPool cp = null;
		Connection db = null;

		String IUV = request.getParameter("IUV");

		Pagamento p = new Pagamento();
		JSONObject jsonFinale = new JSONObject();

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

			ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
			cp = (ConnectionPool) request.getServletContext().getAttribute("ConnectionPool");
			db = cp.getConnection(ce, null);

			p = new Pagamento(db, IUV);

			PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", "[ControllaPagamentoPagoPA] ["+IUV+"] Inizio verifica stato pagamento.");

			if (p.getId()>0) {

				try {
					
					p.chiediPagati(db, -1);

					jsonFinale.put("Esito operazione", "OK");       
					jsonFinale.put("IUV", p.getIdentificativoUnivocoVersamento());
					jsonFinale.put("Stato pagamento", p.getStatoPagamento());
					jsonFinale.put("Data pagamento", p.getDataEsitoSingoloPagamento());
					jsonFinale.put("Denominazione attestante", p.getDenominazioneAttestante());
					jsonFinale.put("Denominazione beneficiario", p.getDenominazioneBeneficiario());
					jsonFinale.put("Importo pagato", p.getSingoloImportoPagato());
				
				} catch (Exception e) {
					
					jsonFinale.put("Esito operazione", "KO");       
					jsonFinale.put("IUV", IUV);
					jsonFinale.put("Errore", "Errore nella cooperazione applicativa.");
					
				}

			} else {

				jsonFinale.put("Esito operazione", "KO");       
				jsonFinale.put("IUV", IUV);
				jsonFinale.put("Errore", "IUV non presente in banca dati");

			}

			PagoPaUtil.salvaStoricoOperazioniAutomatiche(db,  p.getIdSanzione(), p.getId(), "", "", "[ControllaPagamentoPagoPA] ["+IUV+"] Fine verifica stato pagamento. Esito: "+jsonFinale.toString());

		} catch (Exception e) {
			logger.severe("[ControllaPagamentoPagoPA] Si e' verificata un'eccezione nel controllo dei pagamenti.");
			e.printStackTrace();

			jsonFinale.put("Esito operazione", "KO");       
			jsonFinale.put("Errore", e.getMessage());

		} finally {
			if (cp != null) {
				if (db != null) {
					cp.free(db, null);
				}
			}

			response.setContentType("Application/JSON");
			response.getWriter().write(jsonFinale.toString());

		}
	}
}