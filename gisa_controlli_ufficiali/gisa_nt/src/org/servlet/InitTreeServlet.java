/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.controller.actions.Tree;
import org.aspcfs.modules.controller.base.TreeDAOImpl;
import org.aspcfs.modules.util.imports.DbUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
/**
 * Servlet implementation class InitTreeServlet
 */
public class InitTreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitTreeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public void init(ServletConfig config) throws ServletException {
		
		Connection db = null;
		ConnectionPool cp = null ;
		try
		{

			ApplicationPrefs applicationPrefs = (ApplicationPrefs) config.getServletContext().getAttribute(
					"applicationPrefs");

			ApplicationPrefs prefs = (ApplicationPrefs) config.getServletContext().getAttribute("applicationPrefs");
			String ceDriver = prefs.get("GATEKEEPER.DRIVER");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

			ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
			ce.setDriver(ceDriver);

			cp = (ConnectionPool)config.getServletContext().getAttribute("ConnectionPool");
			db = cp.getConnection(ce,null);

			System.out.println("InitTreeServlet --> start");
			File file = new File(config.getServletContext().getRealPath("WEB-INF/"+config.getInitParameter("ConfigTree")));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuil = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = dbuil.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			org.w3c.dom.NodeList nodeLst = doc.getElementsByTagName("tree");

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;

					String nomeTabella = fstElmnt.getAttribute("name");
					String colonnaId = fstElmnt.getElementsByTagName("id").item(0).getTextContent();
					String colonnaIdPadre = fstElmnt.getElementsByTagName("padre").item(0).getTextContent();
					String colonnaDesc = fstElmnt.getElementsByTagName("descrizione").item(0).getTextContent();
					String colonnaLivello = fstElmnt.getElementsByTagName("livello").item(0).getTextContent();
					String colonnaEnabled = fstElmnt.getElementsByTagName("enabled").item(0).getTextContent();
					String colonnaSelezione = fstElmnt.getElementsByTagName("selezione").item(0).getTextContent();

					TreeDAOImpl treeDAO = new TreeDAOImpl();

					org.aspcfs.modules.controller.base.Tree tree = treeDAO.dettaglioTree(nomeTabella, colonnaId, colonnaIdPadre, colonnaDesc, colonnaLivello, null, colonnaEnabled,colonnaSelezione, db);

					System.out.println("caricata struttura ad albero -->"+nomeTabella);
					config.getServletContext().setAttribute(nomeTabella,tree);
				}

			}
			System.out.println("InitTreeServlet --> end");
			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> lista_comuni_in_regione = c.buildList(db,-1, ComuniAnagrafica.IN_REGIONE);
			ArrayList<ComuniAnagrafica> lista_comuni_fuori_regione = c.buildList(db,-1, ComuniAnagrafica.FUORI_REGIONE);
			config.getServletContext().setAttribute("ListaComuniInRegione",lista_comuni_in_regione);
			config.getServletContext().setAttribute("ListaComuniFuoriRegione",lista_comuni_fuori_regione);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			cp.free(db,null);
		}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
