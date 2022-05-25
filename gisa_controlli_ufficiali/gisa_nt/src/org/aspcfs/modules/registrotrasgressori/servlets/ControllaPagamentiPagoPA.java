/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.registrotrasgressori.base.AnagraficaPagatore;
import org.aspcfs.modules.registrotrasgressori.base.Pagamento;
import org.aspcfs.modules.registrotrasgressori.utils.PagoPaUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class ReloadUtenti
 */
public class ControllaPagamentiPagoPA extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger("MainLogger");

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllaPagamentiPagoPA() {
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

        JSONObject jsonFinale = new JSONObject();
		JSONArray jsonAvvisi = new JSONArray();
        
        try {
            ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
            String ceHost = prefs.get("GATEKEEPER.URL");
            String ceUser = prefs.get("GATEKEEPER.USER");
            String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

            ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
            cp = (ConnectionPool) request.getServletContext().getAttribute("ConnectionPool");
            db = cp.getConnection(ce, null);

            PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, -1, -1, "", "", "[ControllaPagamentiPagoPA] Inizio.");

            int idSanzione = -1;
            int totSanzioni = 0;
			PreparedStatement pst = db.prepareStatement("select * from pagopa_get_avvisi_non_pagati();");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

				Pagamento p = new Pagamento(db, rs.getInt("id_pagamento"));

				String messaggio = "";
				PagoPaUtil.salvaStorico(db, -1, p.getIdSanzione(), p.getId(), "[ControllaPagamentiPagoPA] Tentativo di verifica stato IUV.");
				PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", "[ControllaPagamentiPagoPA] Tentativo di verifica stato IUV.");
				p.chiediPagati(db, -1);
				
				PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", "[ControllaPagamentiPagoPA] Stato pagamento: "+p.getStatoPagamento());

				JSONObject jsonAvviso = new JSONObject();
				jsonAvviso.put("Id sanzione", p.getIdSanzione());
				jsonAvviso.put("IUV", p.getIdentificativoUnivocoVersamento());
				jsonAvviso.put("Esito operazione", p.getEsitoInvio());
				jsonAvviso.put("Stato pagamento", p.getStatoPagamento());
				jsonAvvisi.put(jsonAvviso);
			}
        
			jsonFinale.put("Numero avvisi coinvolti", jsonAvvisi.length());       
           
            PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, -1, -1, "", "", "[ControllaPagamentiPagoPA] Fine.");

        } catch (Exception e) {
            logger.severe("[ControllaPagamentiPagoPA] Si e' verificata un'eccezione nel controllo dei pagamenti.");
            e.printStackTrace();
            
            jsonFinale.put("Esito operazione", "KO");       
            jsonFinale.put("Errore", e.getMessage());
            
        } finally {
            if (cp != null) {
                if (db != null) {
                    cp.free(db, null);
                }
            }
            
            jsonFinale.put("Lista avvisi", jsonAvvisi);
			response.setContentType("Application/JSON");
			response.getWriter().write(jsonFinale.toString());
            
        }
    }
}