/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.checklist.base.Audit;
import org.aspcfs.checklist.base.Organization;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.ParameterUtils;

/**
 * Servlet implementation class ChecklistUpdateServlet
 */
public class ChecklistUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChecklistUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
		System.out.println("Inizio Salvataggio Temoraneo Checklist");
		boolean esito = true ;
		boolean 			isValid				= 	false	;
		Connection 			db 					= 	null	;
		int 				resultCount 		= 	-1		;
		int 				resultCountCk 		= 	-1		;
		int 				resultCountCkTp 	= 	-1		;
		Ticket newTic=null; 
		String 			punti 		= request.getParameter("punteggioUltimiAnni")	;
		int 			punteggio 	= 0		;
		Organization 	organ 		= null	;
		Audit 			thisAudit 		= null ;
		ArrayList<Parameter> risposte 			= ParameterUtils.list(request, "risposta")		;
		ArrayList<Parameter> valoreRange 		= ParameterUtils.list(request, "valoreRange")	;
		ArrayList<Parameter> operazione			= ParameterUtils.list(request, "operazione")	;
		ArrayList<Parameter> nota			 	= ParameterUtils.list(request, "nota")			;
		ArrayList<Parameter> paragrafiabilitati = ParameterUtils.list(request, "disabilita")	;
		String stato = request.getParameter("stato");
		
		String idC = request.getParameter("idC")	;
		try {
			db = GestoreConnessioni.getConnection();
		 			thisAudit 		= new Audit(db,request.getParameter("id"))	;
		thisAudit.setIdLastDomanda(request.getParameter("idLastDomanda"))						;
		thisAudit.setNote(request.getParameter("note"));
		thisAudit.setComponentiGruppo(request.getParameter("componentiGruppo"));
		thisAudit.setData1(request.getParameter("data1"));
		thisAudit.setLivelloRischio(Integer.parseInt(request.getParameter("livelloRischio")));
			newTic = new Ticket();
		
			newTic.setId(Integer.parseInt(idC));
			newTic.queryRecord(db, Integer.parseInt(idC));
			thisAudit.setOrgId(Integer.parseInt(request.getParameter("orgId")));
			
		} catch (SQLException e) 
		{
			esito = false ;
			  response.getWriter().write("<valid>false</valid>");
			  return ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ((punti != null)&&(!punti.equals("")))
		{
			punteggio = Integer.parseInt(punti);}
		else 
		{
			punteggio = 0;
		}
		boolean aggiornato=false;
		try 
		{
			thisAudit.setPunteggioUltimiAnni(punteggio);
			
			thisAudit.setStato(stato);
			resultCount = thisAudit.update(db, risposte, valoreRange, operazione, nota, punteggio,paragrafiabilitati);
			org.aspcfs.checklist.base.AuditList audit 			= new org.aspcfs.checklist.base.AuditList();
			int 								AuditOrgId 		= newTic.getOrgId();
			String 								idT 			= newTic.getPaddedId();
			audit.setOrgId(AuditOrgId)	;
			audit.buildListControlli(db, AuditOrgId, idT);
			Iterator<Audit> itera=audit.iterator();
			int punteggioChecklist=0;
			boolean categoriaAggiornabile = true ;

			while(itera.hasNext())
			{
				Audit temp=itera.next();
				punteggioChecklist+=temp.getLivelloRischio();
				 if (temp.getStato().equalsIgnoreCase("temporanea"))
		    	 {
		    		  categoriaAggiornabile = false ;
		    	 }
			}
		      newTic.setCategoriaAggiornabile(categoriaAggiornabile);

			request.setAttribute("PunteggioCheckList", punteggioChecklist);
			
			

		} catch (SQLException errorMessage) 
		{
				System.out.println("Errore nel salvataggio Temporaneo di una checklist Checklist");
				esito = false ;
			  response.getWriter().write("<valid>false</valid>");
			  return ;
			
		}
		finally 
		{
			GestoreConnessioni.freeConnection(db);
		}
		System.out.println("Salvataggio Temporaneo Avvenuto con successo");

		if(esito == true)
		  response.getWriter().write("<valid>true</valid>");
	}

}
