/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.opu.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.accounts.base.Regione;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class ServletRegioniComuniProvince
 */
public class ServletRegioniComuniProvince extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletRegioniComuniProvince() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("DOGET");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONArray json_arr = null;
		Connection db = null;
		ConnectionPool cp = null;
		try {
			ApplicationPrefs prefs = (ApplicationPrefs) getServletContext()
					.getAttribute("applicationPrefs");
			String ceDriver = prefs.get("GATEKEEPER.DRIVER");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
			ConnectionElement ce = new ConnectionElement(ceHost, ceUser,
					ceUserPw);
			ce.setDriver(ceDriver);

			cp = (ConnectionPool) getServletContext().getAttribute(
					"ConnectionPool");
			db = cp.getConnection(ce,null);
			response.setContentType("application/json");
			JSONObject json_obj = null;
			json_arr = new JSONArray();

			if ((String) request.getParameter("tipoSede") != null) {
				if (request.getParameter("tipoSede").equals("1")){
					//SEDE LEGALE
						
						if (("1").equals((String) request.getParameter("tipoRichiesta"))) { // Recupero
							// le
							// province
						if ((String) request.getParameter("idRegione") != null) {
						int idRegione = -1;
						idRegione =  new Integer( (String) request.getParameter("idRegione")).intValue() ;
						Provincia prov = new Provincia();
						ArrayList<Provincia> listaProvince = prov.getProvinceByIdRegione(db, idRegione);
						for (Provincia c : listaProvince) {
						json_obj = new JSONObject(c.getHashmap());
						json_arr.put(json_obj);
						}
						} }
						
						else if (("2").equals((String) request
								.getParameter("tipoRichiesta"))) { // recupero i
																	// comuni
							if ((String) request.getParameter("searchcodeIdprovincia") != null) {
								int idProvincia = new Integer((String) request.getParameter("searchcodeIdprovincia")).intValue();
								ComuniAnagrafica comuni = new ComuniAnagrafica();
								ArrayList<ComuniAnagrafica> listaComuni = comuni
										.getComuni(db, idProvincia);

								for (ComuniAnagrafica c : listaComuni) {
								json_obj = new JSONObject(c.getHashmap());
									json_arr.put(json_obj);
								}

							}

						}
						else if (("3").equals((String) request
								.getParameter("tipoRichiesta"))) { // recupero i
																	// comuni della stessa asl
							if ((String) request.getParameter("idAslSedePrivato") != null) {
								int idAsl = new Integer((String) request.getParameter("idAslSedePrivato")).intValue();
								ComuniAnagrafica comuni = new ComuniAnagrafica();
								ArrayList<ComuniAnagrafica> listaComuni = comuni
										.getComuniByIdAsl(db, idAsl);

								for (ComuniAnagrafica c : listaComuni) {
								json_obj = new JSONObject(c.getHashmap());
									json_arr.put(json_obj);
								}

							}

						}
					}
					else if (request.getParameter("tipoSede").equals("2")){
						//SEDE OPERATIVA
						if (("1").equals((String) request.getParameter("tipoRichiesta"))) { // Recupero
							// le
							// province
						if ((String) request.getParameter("idRegioneSedeOperativa") != null) {
						int idRegione = -1;
						idRegione =  new Integer( (String) request.getParameter("idRegioneSedeOperativa")).intValue() ;
						Provincia prov = new Provincia();
						ArrayList<Provincia> listaProvince = prov.getProvinceByIdRegione(db, idRegione);
						for (Provincia c : listaProvince) {
						json_obj = new JSONObject(c.getHashmap());
						json_arr.put(json_obj);
						}
						} }
						
						else if (("2").equals((String) request
								.getParameter("tipoRichiesta"))) { // recupero i
																	// comuni
							if ((String) request.getParameter("idAslSedeOperativa") != null) {
								int idAsl = new Integer((String) request.getParameter("idAslSedeOperativa")).intValue();
								ComuniAnagrafica comuni = new ComuniAnagrafica();
								ArrayList<ComuniAnagrafica> listaComuni = comuni
										.getComuniByIdAsl(db, idAsl);

								for (ComuniAnagrafica c : listaComuni) {
								json_obj = new JSONObject(c.getHashmap());
									json_arr.put(json_obj);
								}

							}

						}
					}
					else if (request.getParameter("tipoSede").equals("3")){
						//RESPONSABILE STABILIMENTO
						if (("1").equals((String) request.getParameter("tipoRichiesta"))) { // Recupero
							// le
							// province
						if ((String) request.getParameter("idRegioneResponsabile") != null) {
						int idRegione = -1;
						idRegione =  new Integer( (String) request.getParameter("idRegioneResponsabile")).intValue() ;
						Provincia prov = new Provincia();
						ArrayList<Provincia> listaProvince = prov.getProvinceByIdRegione(db, idRegione);
						for (Provincia c : listaProvince) {
						json_obj = new JSONObject(c.getHashmap());
						json_arr.put(json_obj);
						}
						} }
						
						else if (("2").equals((String) request
								.getParameter("tipoRichiesta"))) { // recupero i
																	// comuni
							if ((String) request.getParameter("idProvinciaResponsabile") != null) {
								int idProvincia = new Integer((String) request.getParameter("idProvinciaResponsabile")).intValue();
								ComuniAnagrafica comuni = new ComuniAnagrafica();
								ArrayList<ComuniAnagrafica> listaComuni = comuni
										.getComuni(db, idProvincia);

								for (ComuniAnagrafica c : listaComuni) {
								json_obj = new JSONObject(c.getHashmap());
									json_arr.put(json_obj);
								}

							}

						}
					}
					else if (request.getParameter("tipoSede").equals("4")){
						//RAPPRESENTANTE LEGALE
						if (("1").equals((String) request.getParameter("tipoRichiesta"))) { // Recupero
							// le
							// province
						if ((String) request.getParameter("idRegioneRappresentante") != null) {
						int idRegione = -1;
						idRegione =  new Integer( (String) request.getParameter("idRegioneRappresentante")).intValue() ;
						Provincia prov = new Provincia();
						ArrayList<Provincia> listaProvince = prov.getProvinceByIdRegione(db, idRegione);
						for (Provincia c : listaProvince) {
						json_obj = new JSONObject(c.getHashmap());
						json_arr.put(json_obj);
						}
						} }
						
						else if (("2").equals((String) request
								.getParameter("tipoRichiesta"))) { // recupero i
																	// comuni
							if ((String) request.getParameter("idProvinciaRappresentante") != null) {
								int idProvincia = new Integer((String) request.getParameter("idProvinciaRappresentante")).intValue();
								ComuniAnagrafica comuni = new ComuniAnagrafica();
								ArrayList<ComuniAnagrafica> listaComuni = comuni
										.getComuni(db, idProvincia);

								for (ComuniAnagrafica c : listaComuni) {
								json_obj = new JSONObject(c.getHashmap());
									json_arr.put(json_obj);
								}

							}

						}
					}
				}

			else if ((String) request.getParameter("tipoRichiesta") != null) {
				if (("1")
						.equals((String) request.getParameter("tipoRichiesta"))) { // Recupero
					// le
					// province
					if ((String) request.getParameter("idRegioneFr") != null
							|| (String) request.getParameter("idRegione") != null) {
						int idRegione = -1;

						idRegione = ((String) request
								.getParameter("idRegioneFr") != null) ? new Integer(
								(String) request.getParameter("idRegioneFr"))
								.intValue()
								: new Integer((String) request
										.getParameter("idRegione")).intValue();
						Provincia prov = new Provincia();
						ArrayList<Provincia> listaProvince = prov
								.getProvinceByIdRegione(db, idRegione);

						for (Provincia c : listaProvince) {

							json_obj = new JSONObject(c.getHashmap());

							json_arr.put(json_obj);
						}
					}
				} else if (("2").equals((String) request
						.getParameter("tipoRichiesta"))) { // recupero i
					// comuni
					if ((String) request.getParameter("idProvinciaFr") != null
							|| (String) request
									.getParameter("idProvinciaModificaResidenza") != null) {
						int idProvincia = ((String) request
								.getParameter("idProvinciaFr") != null) ? new Integer(
								(String) request.getParameter("idProvinciaFr"))
								.intValue()
								: new Integer(
										(String) request
												.getParameter("idProvinciaModificaResidenza"))
										.intValue();
						ComuniAnagrafica comuni = new ComuniAnagrafica();
						ArrayList<ComuniAnagrafica> listaComuni = comuni
								.getComuni(db, idProvincia);

						for (ComuniAnagrafica c : listaComuni) {

							json_obj = new JSONObject(c.getHashmap());

							json_arr.put(json_obj);
						}

					}

				} else if (("3").equals((String) request
						.getParameter("tipoRichiesta"))) { // recupero asl dal comune
					if ((String) request.getParameter("idComune") != null
							|| !("").equals((String) request
									.getParameter("idComune")) ) {
						int idComune = new Integer(
								(String) request.getParameter("idComune"))
								.intValue();
						String select 	= 	"select code, description from lookup_site_id where enabled = true and " +
								"codiceistat IN (select codiceistatasl from comuni1 where notused is null and id = ? ) "	;

						
						
						PreparedStatement pst = db.prepareStatement(select);
						
							pst.setInt(1, idComune);
						ResultSet rs = pst.executeQuery();
						int i = 0;
						HashMap<String, Object> valori = new HashMap<String, Object>();

						while ( rs.next() )
						{
							int 	code 	= rs.getInt("code")				;
							String 	value	= rs.getString("description")	;
							valori.put("codice", code);
							valori.put("description", value);

						}

						

							json_obj = new JSONObject(valori);

							json_arr.put(json_obj);
						

					}

				} else if (("1_text").equals((String) request
						.getParameter("tipoRichiesta"))) { // recupero regioni
					String incipit = (String) request.getParameter("term");
					Regione comuni = new Regione();
					ArrayList<Regione> listaRegioni = comuni.buildList(db,
							incipit);

					for (Regione c : listaRegioni) {

						json_obj = new JSONObject(c.getHashmap());

						json_arr.put(json_obj);
					}

				} else if (("2_text").equals((String) request
						.getParameter("tipoRichiesta"))) { // recupero province
															// da regione
					String incipit = (String) request.getParameter("term");
					String idRegione = (String) request
							.getParameter("idregione");
					Provincia comuni = new Provincia();
					ArrayList<Provincia> listaProvince = comuni
							.getProvincePerCampoTesto(db, incipit, new Integer(
									idRegione));

					for (Provincia c : listaProvince) {

						json_obj = new JSONObject(c.getHashmap());

						json_arr.put(json_obj);
					}

				}

				else if (("3_text").equals((String) request
						.getParameter("tipoRichiesta"))) { // recupero comuni da
															// provincia
					String incipit = (String) request.getParameter("term");
					String idProvincia = (String) request
							.getParameter("idprovincia");
					ComuniAnagrafica comuni = new ComuniAnagrafica();
					ArrayList<ComuniAnagrafica> listaComuni = comuni
							.getComuniTesto(db, new Integer(idProvincia),
									incipit);

					for (ComuniAnagrafica c : listaComuni) {

						json_obj = new JSONObject(c.getHashmap());

						json_arr.put(json_obj);
					}

				} else if (("4_text").equals((String) request
						.getParameter("tipoRichiesta"))) {

					IndirizzoList listIndirizzi = new IndirizzoList();
					listIndirizzi.setIdComune(Integer.parseInt(request
							.getParameter("idcomune")));
					listIndirizzi.setStartComune((String) request
							.getParameter("term"));

					listIndirizzi.buildList(db);
					json_arr = new JSONArray();

					for (Object c : listIndirizzi) {
						Indirizzo temp = (Indirizzo) c;

						json_obj = new JSONObject(temp.getHashmap());

						json_arr.put(json_obj);

					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cp.free(db,null);
		}

		response.getWriter().println(json_arr.toString().replaceAll(",}","}"));

	}
}
