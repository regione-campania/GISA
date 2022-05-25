/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.campioni.util.CampioniUtil;
import org.aspcfs.modules.util.imports.DbUtil;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Servlet implementation class ServletPdf
 */
public class ServletPdf extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletPdf() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		Logger logger = Logger.getLogger("MainLogger");

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		String sql_update = null;
		String sql_rapp = null;
		PreparedStatement pst_rapp = null;
		PreparedStatement pst_update = null;

		int i = 0;
		String mod = request.getParameter("mod");
		String orgId = request.getParameter("orgId");
		String id_controllo = request.getParameter("id_controllo");

		int num = 0;

		String servizio = request.getParameter("servizio");
		String uo = request.getParameter("uo");
		String via_amm = request.getParameter("via");
		String ind_legale = request
				.getParameter("indirizzo_legale_rappresentante");
		String city_legale = request.getParameter("luogo_residenza");
		String num_legale = request.getParameter("num_civico_rappresentante");
		String nome_ispezione = request.getParameter("nome_presente_ispezione");
		String luogo_nascita_ispezione = request
				.getParameter("luogo_nascita_presente_ispezione");
		String g_ispezione = request
				.getParameter("giorno_nascita_presente_ispezione");
		String m_ispezione = request
				.getParameter("mese_nascita_presente_ispezione");
		String a_ispezione = request
				.getParameter("anno_nascita_presente_ispezione");
		String luogor_ispezione = request
				.getParameter("luogo_residenza_presente_ispezione");
		String ind_ispezione = request
				.getParameter("indirizzo_presente_ispezione");
		String civ_ispezione = request
				.getParameter("num_civico_presente_ispezione");
		String doc_ispezione = request
				.getParameter("doc_identita_presente_ispezione");
		String strumento = request.getParameter("strumenti_ispezione");
		String desc_iniz = request.getParameter("descrizione_inizio");
		String desc = request.getParameter("descrizione");
		String dichiarazione = request.getParameter("dichiarazione");
		String resp = request.getParameter("responsabile_procedimento");
		String note = request.getParameter("note_inizio") + " "
				+ request.getParameter("note");
		String n_copie = request.getParameter("numero_copie");
		String luogo_trasp = request.getParameter("luogo_partenza");
		String nazione_trasp = request.getParameter("nazione_partenza");
		String data_p_trasp = request.getParameter("data_partenza");
		String ora_p_trasp = request.getParameter("ora_partenza");
		String dest_trasp = request.getParameter("destinazione");
		String nazione_dest_trasp = request.getParameter("nazione_arrivo");
		String data_a_trasp = request.getParameter("data_arrivo");
		String ora_a_trasp = request.getParameter("ora_arrivo");
		String cert_trasp = request.getParameter("certificato");
		String data_cert_trasp = request.getParameter("data_rilascio");
		String luogo_rilascio = request.getParameter("luogo_di_rilascio");
		String ore = request.getParameter("ore");
		String num_campioni = request.getParameter("numero_campione");

		try {
			int k = 0, count = 0;
			db = GestoreConnessioni.getConnection();
			sql = "select count(*) as countrecord from dati_utente_controlliufficiali where id_controllo = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(++i, Integer.parseInt(id_controllo));
			rs = pst.executeQuery();
			if (rs.next()) {
				count = rs.getInt("countrecord");
			}
			// Significa che ci sono tuple nella tabella
			if (count > 0) {
				i = 0;
				sql = "UPDATE dati_utente_controlliufficiali "
						+ "SET servizio = ?, " + " id_controllo = ?, "
						+ " uo = ?, " + " via_amm = ?, "
						+ " presente_ispezione = ?, " + " luogo_nascita = ?, "
						+ " giorno_nascita = ?, " + " mese_nascita = ?, "
						+ " anno_nascita = ?, " + " luogo_residenza = ?, "
						+ " via_ispezione = ?, " + " civico_ispezione = ?, "
						+ " doc_identita = ?, " + " strumenti_ispezione = ?, "
						+ " desc_risoluzione = ?, "
						+ " desc_risoluzione_iniz = ?, "
						+ " dichiarazione = ?, " + " responsabile = ?, "
						+ " note = ?, " + " n_copie = ?, "
						+ " luogo_partenza_trasporto = ?, "
						+ " nazione_partenza_trasporto = ?, "
						+ " data_partenza_trasporto = ?, "
						+ " ora_partenza_trasporto = ?, "
						+ " destinazione_trasporto = ?, "
						+ " nazione_destinazione_trasporto = ?, "
						+ " data_arrivo_trasporto = ?, "
						+ " ora_arrivo_trasporto = ?, "
						+ " certificato_trasporto = ?, "
						+ " data_certificato_trasporto = ?, "
						+ " luogo_rilascio_trasporto = ?, " + " ore = ?, "
						+ " num_campioni = ?, " + " flag = ? "
						+ " where id_controllo = ? ";
				pst = db.prepareStatement(sql);
				pst.setString(++i, servizio);
				pst.setInt(++i, Integer.parseInt(id_controllo));
				pst.setString(++i, uo);
				pst.setString(++i, via_amm);
				pst.setString(++i, nome_ispezione);
				pst.setString(++i, luogo_nascita_ispezione);
				pst.setString(++i, g_ispezione);
				pst.setString(++i, m_ispezione);
				pst.setString(++i, a_ispezione);
				pst.setString(++i, luogor_ispezione);
				pst.setString(++i, ind_ispezione);
				pst.setString(++i, civ_ispezione);
				pst.setString(++i, doc_ispezione);
				pst.setString(++i, strumento);
				pst.setString(++i, desc);
				pst.setString(++i, desc_iniz);
				pst.setString(++i, dichiarazione);
				pst.setString(++i, resp);
				pst.setString(++i, note);
				pst.setString(++i, n_copie);
				pst.setString(++i, luogo_trasp);
				pst.setString(++i, nazione_trasp);
				pst.setString(++i, data_p_trasp);
				pst.setString(++i, ora_p_trasp);
				pst.setString(++i, dest_trasp);
				pst.setString(++i, nazione_dest_trasp);
				pst.setString(++i, data_a_trasp);
				pst.setString(++i, ora_a_trasp);
				pst.setString(++i, cert_trasp);
				pst.setString(++i, data_cert_trasp);
				pst.setString(++i, luogo_rilascio);
				pst.setString(++i, ore);
				pst.setString(++i, num_campioni);
				pst.setString(++i, mod);
				pst.setInt(++i, Integer.parseInt(id_controllo));
				
				num = pst.executeUpdate();

			} // chiudo if
			else {
				int j = 0;
				sql = "INSERT INTO dati_utente_controlliufficiali (servizio, id_controllo, "
						+ " uo, via_amm, presente_ispezione, luogo_nascita, giorno_nascita, "
						+ " mese_nascita, anno_nascita, luogo_residenza, via_ispezione, civico_ispezione, "
						+ " doc_identita, strumenti_ispezione, desc_risoluzione, desc_risoluzione_iniz, dichiarazione, responsabile, "
						+ " note, n_copie, luogo_partenza_trasporto, nazione_partenza_trasporto, data_partenza_trasporto, ora_partenza_trasporto, "
						+ " destinazione_trasporto, nazione_destinazione_trasporto, data_arrivo_trasporto, ora_arrivo_trasporto, "
						+ " certificato_trasporto, data_certificato_trasporto, luogo_rilascio_trasporto, ore, num_campioni, flag )"
						+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pst = db.prepareStatement(sql);
				pst.setString(++j, servizio);
				pst.setInt(++j, Integer.parseInt(id_controllo));
				pst.setString(++j, uo);
				pst.setString(++j, via_amm);
				pst.setString(++j, nome_ispezione);
				pst.setString(++j, luogo_nascita_ispezione);
				pst.setString(++j, g_ispezione);
				pst.setString(++j, m_ispezione);
				pst.setString(++j, a_ispezione);
				pst.setString(++j, luogor_ispezione);
				pst.setString(++j, ind_ispezione);
				pst.setString(++j, civ_ispezione);
				pst.setString(++j, doc_ispezione);
				pst.setString(++j, strumento);
				pst.setString(++j, desc);
				pst.setString(++j, desc_iniz);
				pst.setString(++j, dichiarazione);
				pst.setString(++j, resp);
				pst.setString(++j, note);
				pst.setString(++j, n_copie);
				pst.setString(++j, luogo_trasp);
				pst.setString(++j, nazione_trasp);
				pst.setString(++j, data_p_trasp);
				pst.setString(++j, ora_p_trasp);
				pst.setString(++j, dest_trasp);
				pst.setString(++j, nazione_dest_trasp);
				pst.setString(++j, data_a_trasp);
				pst.setString(++j, ora_a_trasp);
				pst.setString(++j, cert_trasp);
				pst.setString(++j, data_cert_trasp);
				pst.setString(++j, luogo_rilascio);
				pst.setString(++j, ore);
				pst.setString(++j, num_campioni);
				pst.setString(++j, mod);
				pst.execute();

			}

			// Estrarre la tipologia
			int tipologia = 0;
			int m = 0;
			sql = "select tipologia from organization where org_id = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(++m, Integer.parseInt(orgId));
			rs = pst.executeQuery();
			if (rs.next()) {
				tipologia = rs.getInt("tipologia");
			}

			k = 0;

			if (tipologia != 3) {

				// update dati utente organization
				sql_update = "UPDATE organization "
						+ "SET city_legale_rapp = ?, "
						+ " address_legale_rapp = ? " + " where org_id = ? ";
				pst_update = db.prepareStatement(sql_update);
				pst_update.setString(++k, city_legale);
				pst_update.setString(++k, ind_legale + " " + num_legale);
				pst_update.setInt(++k, Integer.parseInt(orgId));
				
				pst_update.executeUpdate();

			} else {

				// update dati utente ticket
				sql_update = "UPDATE ticket " + " SET city_legale_rapp = ?, "
						+ " address_legale_rapp = ? "
						+ " where org_id = ? and tipologia = 4";
				pst_update = db.prepareStatement(sql_update);
				pst_update.setString(++k, city_legale);
				pst_update.setString(++k, ind_legale + " " + num_legale);
				pst_update.setInt(++k, Integer.parseInt(orgId));
			
				pst_update.executeUpdate();

			}

			if (mod.equals("false")) {

				int k2 = 0;
				int j = 0;
				sql_update = "UPDATE ticket " + "SET flag_mod5 = ? "
						+ " where ticketid = ? ";
				pst_update = db.prepareStatement(sql_update);
				pst_update.setString(++k2, mod);
				pst_update.setInt(++k2, Integer.parseInt(id_controllo));
				pst_update.executeUpdate();

			}

			String url = null;

			// Redirect switching on tipologia
			if (tipologia == 1) {
				// String url =
				// "AccountVigilanza.do?command=TicketDetails&id="+id_controllo+"&orgId="+orgId+"&bozza="+mod;
				url = "AccountVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 3) {
				url = "StabilimentiVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 151) {
				url = "FarmacieVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 800) {
				url = "OsmVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 97) {
				// Continuare con le tipologie man mano che il mod.5 viene
				// esteso
				url = "SoaVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 10) {
				// Continuare con le tipologie man mano che il mod.5 viene
				// esteso
				url = "CaniliVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 4) {
				// Continuare con le tipologie man mano che il mod.5 viene
				// esteso
				url = "AbusivismiVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 13) {
				// Continuare con le tipologie man mano che il mod.5 viene
				// esteso
				url = "OperatoriprivatiVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 22) {
				// Continuare con le tipologie man mano che il mod.5 viene
				// esteso
				url = "OperatoriFuoriRegioneVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 1) {
				// Continuare con le tipologie man mano che il mod.5 viene
				// esteso
				url = "DistributoriVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 2) {
				// Continuare con le tipologie man mano che il mod.5 viene
				// esteso
				url = "AllevamentiVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 9) {
				// Continuare con le tipologie man mano che il mod.5 viene
				// esteso
				url = "TrasportiVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			} else if (tipologia == 255) {
				// Continuare con le tipologie man mano che il mod.5 viene
				// esteso
				url = "CaniPadronaliVigilanza.do?command=TicketDetails&id="
						+ id_controllo + "&orgId=" + orgId;
			}
			else if (tipologia == 14) {
			// Continuare con le tipologie man mano che il mod.5 viene
			// esteso
			url = "AcqueReteVigilanza.do?command=TicketDetails&id="
					+ id_controllo + "&orgId=" + orgId;
			}
			else if (tipologia == 7) {
			// Continuare con le tipologie man mano che il mod.5 viene
			// esteso
			url = "AziendeAgricoleVigilanza.do?command=TicketDetails&id="
					+ id_controllo + "&orgId=" + orgId;
			}
			request.setAttribute("url", url);
			RequestDispatcher rd = request
					.getRequestDispatcher("redirect_servlet.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		// Significa che siamo in modalita' definitiva

	}

}
