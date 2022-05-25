/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.servlets.macelli;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.macellazioni.base.Art17AltreSpecie;
import org.aspcfs.modules.macellazioni.utils.Art17AltreSpecieDao;
import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class Art17Servlet
 */
public class Art17Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger("MainLogger");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Art17Servlet() {
        super();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
		String ceDriver = prefs.get("GATEKEEPER.DRIVER");
		String ceHost = prefs.get("GATEKEEPER.URL");
		String ceUser = prefs.get("GATEKEEPER.USER");
		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
		
		ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
	    ce.setDriver(ceDriver);
		
		ConnectionPool cp = (ConnectionPool) request.getSession().getServletContext().getAttribute("ConnectionPool");
		
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		Art17AltreSpecieDao dao = Art17AltreSpecieDao.getInstance();
		Art17AltreSpecie art17AltreSpecie = null;
		try{
			db = cp.getConnection(ce,null);
			
			int idMacello = Integer.parseInt(request.getParameter("idMacello"));
			int idEsercente = Integer.parseInt(request.getParameter("idEsercente"));
			String nomeEsercente = request.getParameter("nomeEsercente");
			String dataMacellazione = request.getParameter("dataMacellazione");
			
			dao.delete(idMacello, idEsercente, nomeEsercente, dataMacellazione, db);
			
			final int NUM_RIGHE = Integer.parseInt(ApplicationProperties.getProperty("NUM_RIGHE_ALTRE_SPECIE"));
			
			for(int j = 0; j < NUM_RIGHE; j++){
				art17AltreSpecie = new Art17AltreSpecie();
				art17AltreSpecie.setIdMacello(idMacello);
				art17AltreSpecie.setIdEsercente(idEsercente);
				art17AltreSpecie.setNomeEsercente(nomeEsercente);
				art17AltreSpecie.setDataMacellazione(dataMacellazione);
				art17AltreSpecie.setAuricolare(request.getParameter("auricolare_" + (j+1)));
				art17AltreSpecie.setMezzene(request.getParameter("mezzene_" + (j+1)));
				art17AltreSpecie.setDataNascita(request.getParameter("dataNascita_" + (j+1)));
				art17AltreSpecie.setSpecie(request.getParameter("specie_" + (j+1)));
				art17AltreSpecie.setCategoria(request.getParameter("categoria_" + (j+1)));
				art17AltreSpecie.setSesso(request.getParameter("sesso_" + (j+1)));
				art17AltreSpecie.setModello4(request.getParameter("modello4_" + (j+1)));
				art17AltreSpecie.setEsitoVisita(request.getParameter("esitoVisita_" + (j+1)));
				dao.insert(art17AltreSpecie, db);
			}
			
		}
		catch(SQLException sqle){
			logger.severe("Errore SQL nell'infasamento della tabella art17_altre_specie");
			request.setAttribute("problema", "["+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "] Si e' verificato un problema nel salvataggio dei dati.");
			sqle.printStackTrace();
		}
		catch(Exception e){
			logger.severe("Errore generico nell'infasamento della tabella art17_altre_specie");
			request.setAttribute("problema", "["+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "] Si e' verificato un problema nel salvataggio dei dati.");
			e.printStackTrace();
		}
		finally{
			cp.free(db,null);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("risultatoArt17.jsp");
		rd.forward(request, response);
		
	}

}
