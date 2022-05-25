<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="it.us.web.bean.UserOperation"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.HashMap"%> 
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeMap"%>
<%@page import="it.us.web.dao.hibernate.Persistence"%>
<%@page import="it.us.web.dao.hibernate.PersistenceFactory"%>
<%@page import="it.us.web.bean.UtentiOperazioni"%>
<%@page import="org.hibernate.criterion.Restrictions"%>
<%@page import="org.hibernate.criterion.Order"%>
<%@page import="it.us.web.action.GenericAction"%>
<%@page import="it.us.web.bean.BUtente"%>


<%
HashMap<String, HttpSession> utenti = null;
utenti = (HashMap<String, HttpSession>)request.getSession().getServletContext().getAttribute("utenti");
Date adesso = new Date();
//Persistence persistence = PersistenceFactory.getPersistence();
Context ctx;
Connection db = null;
ctx = new InitialContext();
DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/storico");
db = ds.getConnection();

int numeroMinuti = Integer.parseInt(request.getParameter("numeroMinuti"));

System.out.println("###############  RIMOZIONE UTENTI COLLEGATI DA PIU' DI  " + numeroMinuti + " MINUTI ###############");
System.out.println("DATA ATTUALE: " + adesso);
System.out.println("DURATA MINIMA DELLA SESSIONE RICHIESTA PER EFFETTUARE LOGOUT: NUMERO MINUTI: " + numeroMinuti + ", MILLISECONDI: " + 1000*60*numeroMinuti );

	try
	{
		for(String username : utenti.keySet())
		{
			UserOperation uo = null;
			Date dataUltimaOp = null;
			BUtente utente = (BUtente)utenti.get(username).getAttribute("utente");
			System.out.println("UTENTE: " + utente.getUsername());
			
			HttpSession sessione = (HttpSession)utenti.get(username);
			
			ArrayList<UserOperation> operazioni = (ArrayList<UserOperation>)sessione.getAttribute("operazioni");
			if(operazioni==null || operazioni.isEmpty())
			{
				uo = UserOperation.lastOperation(utente.getSuperutente().getId(), db);
			}
			else
			{
				uo = operazioni.get(operazioni.size()-1);
			}

		    if(uo!=null)
		    {
			   dataUltimaOp = uo.getData();
			   System.out.println("UTENTE: " + utente.getUsername() + ", DATA ULTIMA OPERAZIONE: " + dataUltimaOp);
		    }
		    else
		    {
			   System.out.println("UTENTE: " + utente.getUsername() + ", NESSUNA OPERAZIONE TROVATA: " + dataUltimaOp);
		    }
		   
		   System.out.println("UTENTE: " + utente.getUsername() + ", ETA' ULTIMA OPERAZIONE: " + (adesso.getTime() - dataUltimaOp.getTime()) );
		   
			if ( adesso.getTime() - dataUltimaOp.getTime() > 1000*60*numeroMinuti )
			{
				System.out.println("RIMOZIONE UTENTE " + utente.getUsername() + " IN CORSO..." );
				sessione.invalidate();
				utenti.remove(username);
				request.getSession().getServletContext().setAttribute("utenti", utenti);
				System.out.println("...UTENTE " + utente.getUsername() + " RIMOSSO" );
			}
		}
	}
	catch(Exception e){}
	
	//PersistenceFactory.closePersistence( persistence, true );
	db.close();

%>

<script>
window.location.href='checkUtenti.jsp?numeroMinuti=<%= request.getParameter("numeroMinuti") %>';
</script>

