/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.action.Index;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.UserOperation;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.bean.Bean;

public class Logout extends GenericAction
{
	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("login");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		String entrypointSinantropi = null;
		String ambiente = (String) session.getAttribute("ambienteSirv");
		
		System.out.println("********************************* Ambiente SCA: " + ambiente +  " *************************************");
		
		if(session!=null)
			entrypointSinantropi = (String)session.getAttribute("entrypointSinantropi");
		if( utente != null && ambiente==null){ //(ambiente!=null && !ambiente.equals("sirv"))){
			eliminaUtenteContext(utente.getUsername());
			String system = (String) session.getAttribute("system");
			session.setAttribute( "utente", null );
			//session.setAttribute( "funzioniConcesse", null );
			String ip=(String)req.getRemoteAddr();
			try {
				 UserOperation uo = new UserOperation();
				 uo.setUser_id(utente.getSuperutente().getId());
				 uo.setUsername(utente.getUsername());
				 uo.setIp(ip);
				 uo.setData(new Timestamp(new Date().getTime()));
				 uo.setUrl(req.getRequestURL().toString()+(req.getQueryString()!=null ? "?"+req.getQueryString() : ""));
				 uo.setParameter("");
				 uo.setUserBrowser(req.getHeader("user-agent"));
				 uo.setAction("login.Logout");
				 
				if (session.getAttribute("operazioni")!=null){
					 ArrayList<UserOperation> op = (ArrayList<UserOperation>) session.getAttribute("operazioni");
					 op.add(uo);
					 session.removeAttribute("operazioni");
					 session.removeAttribute("ip");
					 session.removeAttribute("idUser");
					 session.setAttribute("operazioni",op);
				} else {
					 ArrayList<UserOperation> op = new ArrayList<UserOperation>();
					 op.add(uo);
					 session.removeAttribute("operazioni");
					 session.removeAttribute("ip");
					 session.removeAttribute("idUser");
					 session.setAttribute("operazioni",op);
				}
				session.invalidate();
			}
			catch (Exception e) {
				e.printStackTrace();
			}			
			utente = null;
			
			/* Da qui si decide in quale index andare mediante la parametrizzazione
			 del costruttore della Index*/
			if (system.equalsIgnoreCase("vam") || (entrypointSinantropi!=null && entrypointSinantropi.equals("vam")))
				goToAction( new Index("vam") );
			else if (system.equals("sinantropi"))
				goToAction( new Index("sinantropi") );
			
		} else {
			String system = (String) session.getAttribute("system");	
			try {
					String ip=(String) session.getAttribute("ip");
					Integer id = (Integer) session.getAttribute("idUser");
					if(id!=null)
					{
					List<SuperUtente> sus = new ArrayList<SuperUtente>();
					String sql = "select  luogo,num_iscrizione_albo as numIscrizioneAlbo,sigla_provincia as siglaProvincia, id,data_scadenza as dataScadenza, enabled,enabled_date as enabledDate, entered, entered_by as enteredBy,modified, modified_by as modifiedBy, note,password, trashed_date as trashedDate, username,access_position_lat as accessPositionLat,access_position_lon as accessPositionLon,access_position_err as accessPositionErr  from utenti_super where trashed_date is null and id = ? and enabled " ;
					PreparedStatement st = connection.prepareStatement(sql);
					st.setInt(1, id);
					ResultSet rs = st.executeQuery();
					if(rs.next())
					{
						SuperUtente su = new SuperUtente();
						su = (SuperUtente)Bean.populate(su, rs);
						su.setEnabled(true);
						su.setNumIscrizioneAlbo(rs.getString("numIscrizioneAlbo"));
						su.setSiglaProvincia(rs.getString("siglaProvincia"));
						sus.add(su);
					}
					if (sus.size()>0){
						 UserOperation uo = new UserOperation();
						 uo.setUser_id(sus.get(0).getId());
						 uo.setUsername(sus.get(0).getUsername());
						 uo.setIp(ip);
						 uo.setData(new Timestamp(new Date().getTime()));
						 uo.setUrl(req.getRequestURL().toString()+(req.getQueryString()!=null ? "?"+req.getQueryString() : ""));
						 uo.setParameter("");
						 uo.setUserBrowser(req.getHeader("user-agent"));
						 uo.setAction("login.Logout");
						 if (session.getAttribute("operazioni")!=null){
							 ArrayList<UserOperation> op = (ArrayList<UserOperation>) session.getAttribute("operazioni");
							 op.add(uo);
							 session.removeAttribute("operazioni");
							 session.removeAttribute("ip");
							 session.removeAttribute("idUser");
							 session.setAttribute("operazioni",op);
						} else {
							 ArrayList<UserOperation> op = new ArrayList<UserOperation>();
							 op.add(uo);
							 session.removeAttribute("operazioni");
							 session.removeAttribute("ip");
							 session.removeAttribute("idUser");
							 session.setAttribute("operazioni",op);
						}
					}
					}
					if (ambiente != null || req.getAttribute("isFromSca") != null){// && ambiente.equalsIgnoreCase("sirv")) {	
						goToAction( new LoginSirv("vam") );
					} else {
						session.invalidate(); 
					}		

			} catch (Exception e) {
				e.printStackTrace();
			}		
						
			/* Da qui si decide in quale index andare mediante la parametrizzazione
			 del costruttore della Index*/
			if( system == null || system.equalsIgnoreCase("vam") || (entrypointSinantropi!=null && entrypointSinantropi.equals("vam")))
				goToAction( new Index("vam") );
			else if (system.equals("sinantropi"))
				goToAction( new Index("sinantropi") );
		}
			
	}
}
