<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
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
Persistence persistence = PersistenceFactory.getPersistence();

int userId = 0;
try
{
	userId = Integer.parseInt(request.getParameter("userId"));
}
catch(Exception e)
{
	e.printStackTrace();
}
if(userId>0)
{
	try
	{
		for(String username : utenti.keySet())
		{
			UtentiOperazioni uo = null;
			Date dataUltimaOp = null;
			BUtente utente = (BUtente)utenti.get(username).getAttribute("utente");
			HttpSession sessione = (HttpSession)utenti.get(username);
			ArrayList<UtentiOperazioni> uos = (ArrayList<UtentiOperazioni>)persistence.createCriteria(UtentiOperazioni.class)
																							.add(Restrictions.eq("utente",utente))
																							.addOrder(Order.desc("entered"))
																							.list();
		   if(!uos.isEmpty())
			   dataUltimaOp = uos.get(0).getEntered();
		   
			if ( utente.getId() == userId )
			{
				sessione.invalidate();
				utenti.remove(username);
				request.getSession().getServletContext().setAttribute("utenti", utenti);
				break;
			}
		}
	}
	catch(Exception e){}
	
	PersistenceFactory.closePersistence( persistence, true );

}
%>

<script>
window.location.href='checkUtenti.jsp?numeroMinuti=<%= request.getParameter("numeroMinuti") %>';
</script>

