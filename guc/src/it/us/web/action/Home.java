/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import it.us.web.action.endpointconnector.ConfigAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Utente;
import it.us.web.constants.ExtendedOptions;
import it.us.web.dao.AslDAO;
import it.us.web.dao.RuoloDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.GUCOperationType;
import it.us.web.util.jmesa.AslDroplistFilterEditor;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.xml.xpath.XPathConstants;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Home extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}


	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws SQLException, ServletException, IOException 
	{
		
		
//		boolean utentiAttivi = req.getParameter("utentiAttivi") != null ? booleanoFromRequest("utentiAttivi") : true;
		boolean utentiAttivi = true;
		String endpoint = req.getParameter("endpoint") != null && !req.getParameter("endpoint").trim().equals("") ? stringaFromRequest("endpoint") : "";
		int ruolo = req.getParameter("ruolo") != null && !req.getParameter("ruolo").trim().equals("") ? interoFromRequest("ruolo") : -1;
		
		List<Asl> aslList = AslDAO.getAsl(db);
		for(Asl a : aslList){
			AslDroplistFilterEditor.getMapValueLabel().put(a.getNome(), a.getNome());
		}
		AslDroplistFilterEditor.getMapValueLabel().put("TUTTE LE ASL", "");
	
		ArrayList<Ruolo> listaRuoli ;
		List<String> endpoints = new ArrayList<String>();
		
		
		EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
		EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.GETRUOLIUTENTE); 
		
		String ipBdu = null, ipVam = null, ipGisa = null, ipGisaExt = null;
		
		  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
			  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
			listaRuoli = new ArrayList<Ruolo>();
			endpoints.add(epc.getEndPoint().getNome());
			//listaRuoli = RuoloDAO.getRuoliByEndPoint(e.toString(), db);
			
			Connection conn = null;
			String dataSource = epc.getEndPoint().getDataSourceSlave();
			System.out.println("DATASOURCE: "+dataSource);

			
				try {
					conn = DbUtil.ottieniConnessioneJDBC(dataSource);
					Context ctx = new InitialContext();
					DataSource ds = (DataSource) ctx.lookup("java:comp/env/"+dataSource);
						
						
					String nome = epc.getEndPoint().getNome();
					System.out.println("NOME: "+nome);

					//String url = conn.getMetaData().getURL();
					String url = ds.getUrl();
					System.out.println("URL: "+url);

					String cleanUrl = url.substring(5);
					
					System.out.println("CLEANURL: "+cleanUrl);

					URI uri = URI.create(cleanUrl);
					System.out.println("URI GET HOST: "+uri.getHost());
					if(nome.equalsIgnoreCase("bdu"))
						 ipBdu = uri.getHost();
					else if(nome.equalsIgnoreCase("vam"))
						 ipVam = uri.getHost();
					else if(nome.equalsIgnoreCase("gisa"))
						 ipGisa = uri.getHost();
					else if(nome.equalsIgnoreCase("gisa_ext"))
						 ipGisaExt = uri.getHost();
					
					
					gestioneRuoliUtente(listaRuoli,epc, conn);
				} catch (SQLException | NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					DbUtil.chiudiConnessioneJDBC(null, conn, dataSource);
				}
		  	
		
			req.setAttribute("listaRuoli" + epc.getEndPoint().getNome(), listaRuoli);
		}
		
		ArrayList<Utente> utentiList2 = UtenteGucDAO.listaUtenti(db, endpoint, ruolo, utentiAttivi);
		ArrayList<BUtente> utentiListGuc = UtenteDAO.getUtenti(db);
		
		//List<Utente> utentiList = criteria.addOrder( Order.desc("entered") ).list();
		req.setAttribute("utentiList", utentiList2);
		req.setAttribute("utentiListGuc", utentiListGuc);

		req.setAttribute("endpoints", endpoints);
		
		req.setAttribute("utentiAttivi", utentiAttivi);
		req.setAttribute("endpoint", endpoint);
		req.setAttribute("ruolo", ruolo);
		
		// Gestione utenti presenti in GUC e non sull'ENDPOINT
		/*String queryGISA = "select count(g.id) as num from guc_utenti g join guc_ruoli gr on gr.id_utente=g.id where g.enabled and gr.endpoint ilike '%Gisa' and gr.ruolo_integer > 0 and g.username not similar to '%cni_%' and lower(trim(username)) not in (select lower(trim(username)) from dblink('host="
				+ ipGisa
				+ " dbname=gisa user=postgres password=postgres', ' SELECT username FROM access ') AS table2 (username char(150)))";
		String queryGISA_EXT = "select count(g.id) as num from guc_utenti g join guc_ruoli gr on gr.id_utente=g.id where g.enabled and gr.endpoint ilike '%Gisa_%' and gr.ruolo_integer > 0 and g.username not similar to '%cni_%'  and lower(trim(username)) not in (select lower(trim(username)) from dblink('host="
				+ ipGisaExt
				+ " dbname=gisa user=postgres password=postgres', ' SELECT username FROM access_ext ') AS table2 (username char(150)))";
		String queryBDU = "select count(g.id) as num from guc_utenti g join guc_ruoli gr on gr.id_utente=g.id where g.enabled and gr.endpoint ilike '%bdu%' and gr.ruolo_integer > 0 and lower(trim(username)) not in (select lower(trim(username)) from dblink('host="
				+ ipBdu
				+ " dbname=bdu user=postgres password=postgres', ' SELECT username FROM access ') AS table2 (username char(150)))";
		String queryVAM = "select count(g.id) as num from guc_utenti g join guc_ruoli gr on gr.id_utente=g.id where g.enabled and gr.endpoint ilike '%vam%' and gr.ruolo_integer > 0 and lower(trim(username)) not in (select lower(trim(username)) from dblink('host="
				+ ipVam
				+ " dbname=vam user=postgres password=postgres', ' SELECT username FROM utenti_super ') AS table2 (username char(150)))";
		
		System.out.println("ESEGUO GESTIONE UTENTI GUC/ENDPOINT GISA "+queryGISA);
		PreparedStatement stat = db.prepareStatement(queryGISA); ResultSet rs = stat.executeQuery();
		rs.next(); req.setAttribute("daAllineareGISA",""+ rs.getInt("num")); 

		System.out.println("ESEGUO GESTIONE UTENTI GUC/ENDPOINT GISA EXT");

		stat = db.prepareStatement(queryGISA_EXT); rs = stat.executeQuery();
		rs.next(); req.setAttribute("daAllineareGISA_EXT",""+ rs.getInt("num")); 
		System.out.println("ESEGUO GESTIONE UTENTI GUC/ENDPOINT BDU");

		stat = db.prepareStatement(queryBDU); rs = stat.executeQuery();
		rs.next(); req.setAttribute("daAllineareBDU", ""+rs.getInt("num")); 
		System.out.println("ESEGUO GESTIONE UTENTI GUC/ENDPOINT VAM");

		stat = db.prepareStatement(queryVAM); rs = stat.executeQuery();
		rs.next(); req.setAttribute("daAllineareVAM", ""+ rs.getInt("num")); */

		
		gotoPage( "/jsp/guc/homepage.jsp" );
		
	}
	
	private void gestioneRuoliUtente( ArrayList<Ruolo> ruoloUtenteList,EndPointConnector epc, Connection conn ) throws Exception{
		  
		 
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{

			String queryRuoliUtente = epc.getSql();
			ExtendedOptions e = new ExtendedOptions();
			ArrayList<String> opt = e.getListOptions(epc.getEndPoint().getNome());
			pst = conn.prepareStatement(queryRuoliUtente);
		
			DBI=pst.toString();
			System.out.println("ESEGUO QUERY SU "+epc.getEndPoint().getNome());
			rs = pst.executeQuery();
			Ruolo r = null;
			while( rs.next() ){
				r = new Ruolo();
				r.setRuoloInteger(rs.getInt("id_ruolo"));
				r.setRuoloString(rs.getString("descrizione_ruolo"));
				r.setNote(rs.getString("note_ruolo"));
				
				if (opt!=null && opt.size()>0){
					HashMap<String,String> o = new HashMap<String, String>();
					for (int i=0;i<opt.size();i++){
						o.put(epc.getEndPoint().getNome()+"_"+opt.get(i).toString(), rs.getObject(opt.get(i).toString()).toString());
					}
					r.setExtOpt(o);
				}
				ruoloUtenteList.add(r);
			}
		}
		catch (Exception e){
			throw e;
//			e.printStackTrace();
		}
}

}