<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="it.us.web.bean.UserOperation"%>
<%@page import="it.us.web.bean.vam.OperazioneChirurgica"%>
<%@page import="org.hibernate.criterion.Order"%>
<%@page import="org.hibernate.criterion.Restrictions"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.HashMap"%> 
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Dictionary"%>
<%@page import="java.util.Map"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.NodeList"%>

<!-- Prova svn -->

<style type="text/css">

table {
	font-family:Arial, Helvetica, sans-serif;
	color:#666;
	font-size:12px;
	text-shadow: 1px 1px 0px #fff;
	background:#eaebec;
	width:100%;
	border:#ccc 1px solid;
	margin-top:20 px;
	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;

	-moz-box-shadow: 0 1px 2px #d1d1d1;
	-webkit-box-shadow: 0 1px 2px #d1d1d1;
	box-shadow: 0 1px 2px #d1d1d1;
}
table th {
	padding:21px 25px 22px 25px;
	border-top:1px solid #fafafa;
	border-bottom:1px solid #e0e0e0;
	text-align: center;
	font-size: 16px;
	background: #ededed;
	background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#ebebeb));
	background: -moz-linear-gradient(top,  #ededed,  #ebebeb);
}
table th:first-child{
	text-align: center;
	padding-left:20px;
}
table tr:first-child th:first-child{
	-moz-border-radius-topleft:3px;
	-webkit-border-top-left-radius:3px;
	border-top-left-radius:3px;
}
table tr:first-child th:last-child{
	-moz-border-radius-topright:3px;
	-webkit-border-top-right-radius:3px;
	border-top-right-radius:3px;
}
table tr{
	text-align: center;
	padding-left:20px;
}
table tr td:first-child{
	text-align: left;
	padding-left:20px;
	border-left: 0;
}
table tr td {
	padding:18px;
	border-top: 1px solid #ffffff;
	border-bottom:1px solid #e0e0e0;
	border-left: 1px solid #e0e0e0;
	font-size: 14px;
	text-align:center;
	background: #fafafa;
	background: -webkit-gradient(linear, left top, left bottom, from(#fbfbfb), to(#fafafa));
	background: -moz-linear-gradient(top,  #fbfbfb,  #fafafa);
}
table tr.even td{
	background: #f6f6f6;
	background: -webkit-gradient(linear, left top, left bottom, from(#f8f8f8), to(#f6f6f6));
	background: -moz-linear-gradient(top,  #f8f8f8,  #f6f6f6);
}
table tr:last-child td{
	border-bottom:0;
}
table tr:last-child td:first-child{
	-moz-border-radius-bottomleft:3px;
	-webkit-border-bottom-left-radius:3px;
	border-bottom-left-radius:3px;
}
table tr:last-child td:last-child{
	-moz-border-radius-bottomright:3px;
	-webkit-border-bottom-right-radius:3px;
	border-bottom-right-radius:3px;
}


</style>

</head>

<%! 
public String formattaMillisecondi(long millis){
	
	int seconds = (int)(millis / 1000) % 60 ;
	int minutes = (int)((millis / (1000*60)) % 60);
	int hours = (int)((millis / (1000*60*60)) % 24);
	int days = (int)((millis / (1000*60*60*24)));

	ArrayList<String> timeArray = new ArrayList<String>();

	if(days > 0)    
	    timeArray.add(String.valueOf(days) + "d");

	if(hours > 0 || days > 0)   
	    timeArray.add(String.valueOf(hours) + "h");

	if(minutes > 0 || hours > 0 || days > 0) 
	    timeArray.add(String.valueOf(minutes) + "min");

	if(seconds > 0 || minutes > 0 || hours > 0 || days > 0) 
	    timeArray.add(String.valueOf(seconds) + "sec");

	String time = "";
	for (int i = 0; i < timeArray.size(); i++) 
	{
	    time = time + timeArray.get(i);
	    if (i != timeArray.size() - 1)
	        time = time + " ";
	}

	if (time == "")
	  time = "0 sec";

	return time;
	
} 

%>

<% 
Date adesso = new Date();

HashMap<Integer, String> hashAsl = new HashMap<Integer, String>();
hashAsl.put(-1, "Nessuna ASL");
hashAsl.put(201, "AVELLINO");
hashAsl.put(202, "BENEVENTO");
hashAsl.put(203, "CASERTA");
hashAsl.put(204, "NAPOLI 1 CENTRO");
hashAsl.put(205, "NAPOLI 2 NORD");
hashAsl.put(206, "NAPOLI 3 SUD");
hashAsl.put(207, "SALERNO");

//Persistence persistence = PersistenceFactory.getPersistence();
Context ctx;
Connection db = null;
ctx = new InitialContext();
javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/storico");
db = ds.getConnection();

TreeMap<Integer, ArrayList<BUtente>> listaUtentiPerAsl = new TreeMap<Integer, ArrayList<BUtente>>();
listaUtentiPerAsl.put(-1, new ArrayList<BUtente>());
listaUtentiPerAsl.put(201, new ArrayList<BUtente>());
listaUtentiPerAsl.put(202, new ArrayList<BUtente>());
listaUtentiPerAsl.put(203, new ArrayList<BUtente>());
listaUtentiPerAsl.put(204, new ArrayList<BUtente>());
listaUtentiPerAsl.put(205, new ArrayList<BUtente>());
listaUtentiPerAsl.put(206, new ArrayList<BUtente>());
listaUtentiPerAsl.put(207, new ArrayList<BUtente>());

HashMap<String, HttpSession> utenti = null;

utenti = (HashMap<String, HttpSession>)request.getSession().getServletContext().getAttribute("utenti");

if(utenti != null && !utenti.isEmpty())
{

%>


<table><tr><tH colspan="3" style=""><a href="checkUtentiPo.jsp"  >VISUALIZZA MAPPA</a>
<a href="cleanUs.jsp"  >ELIMINA UTENTI US</a>
 NUMERO UTENTI: <%= utenti.keySet().size() %></tH></tr>
<form action="cleanUtenti.jsp">

<tr><td>ABATTI:</td><td>
<select name="numeroMinuti">
<option value="30" <%if(request.getParameter("numeroMinuti") != null && request.getParameter("numeroMinuti").equals("30")){ %>selected="selected" <% } %> >30 minuti</option>
<option value="60" <%if(request.getParameter("numeroMinuti") != null && request.getParameter("numeroMinuti").equals("60")){ %>selected="selected" <% } %> >1 ora</option>
<option value="90" <%if(request.getParameter("numeroMinuti") != null && request.getParameter("numeroMinuti").equals("90")){ %>selected="selected" <% } %> >1 ora e 30 minuti</option>
<option value="120" <%if(request.getParameter("numeroMinuti") == null || request.getParameter("numeroMinuti").equals("120")){ %>selected="selected" <% } %> >2 ore</option>
</select></td>
<td>
<input type="submit" value="Vai"/>
</td></tr>
</form>

<form action="cleanById.jsp">
<tr><td>ABBATTI UTENTE CON ID</td>
<td>
<input type="text" name="userId"/></td>
<td>
<input type="submit" value="Vai"/></td></tr>
</form>
</table>
<br></br>

<table>
<thead>
<tr>
<th colspan="8">Tutti</th>
</tr>
<tr>
<th>PROG.</th>
<th>ID SESSIONE</th>
<th>ID UTENTE</th>
<th>USERNAME</th>
<th>ASL</th>
<th>IP</th>
<th>ETA' ULTIMA OP.</th>
<th>Coordinate</th>
<th>Num Connessioni Attive</th>
<th>Durata Connessione</th>
</tr>
</thead>
<% 

int aslUtente = 0;
int prog = 0 ;
HashMap<String, Integer> operazioniNumUtenti = new HashMap<String, Integer>();
HashMap<Integer, String> ipUtenti = new HashMap<Integer, String>();

for(String username : utenti.keySet())
{
	HttpSession sessione = utenti.get(username);
	BUtente utenteLoggato = null;
	
	boolean controllo = false;
	try
	{
		if(sessione!=null && sessione.getAttribute("utente")!=null)
			controllo = true;
	}
	catch(IllegalStateException e)
	{
		//Gestiamo il caso in cui la sessione è invalidata
		controllo = false;
	}
	
	if(controllo)
	{
		utenteLoggato =  (BUtente)sessione.getAttribute("utente");
		int idAsl = 0;
		if(utenteLoggato.getClinica()!=null && utenteLoggato.getClinica().getLookupAsl()!=null)
		   idAsl = utenteLoggato.getClinica().getLookupAsl().getId();
		if(idAsl == 0 || idAsl == -1 )
		{
			aslUtente = -1;
		}
		else
		{
			aslUtente = idAsl;
		}
		listaUtentiPerAsl.get(aslUtente).add(utenteLoggato);
		prog ++ ;
%>

<tr>
<td><%=prog %></td>
<td>


<%=sessione.getId()%>
</td>
<td>
<%= utenteLoggato.getId() %>
</td>
<td>
<%= utenteLoggato.getUsername() %>
</td>
<td>
<%= hashAsl.get(aslUtente) %>
</td>
<td>
<% 
    UserOperation uo = null;
	Date dataUltimaOp = null;
	String ultimaOp = null;
	String ip = null;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	ArrayList<UserOperation> operazioni = (ArrayList<UserOperation>)sessione.getAttribute("operazioni");
	if(operazioni==null || operazioni.isEmpty())
	{
		uo = UserOperation.lastOperation(utenteLoggato.getSuperutente().getId(), db);
	}
	else
	{
		uo = operazioni.get(operazioni.size()-1);
	}

   if(uo!=null)
   {
	   dataUltimaOp = uo.getData();
	   ultimaOp 	= uo.getAction();
	   ip 			= uo.getIp();
	   out.println(ip);
	   ipUtenti.put(utenteLoggato.getId(), ip);
   }
   
   if(!operazioniNumUtenti.containsKey(ultimaOp))
   		operazioniNumUtenti.put(ultimaOp, 1);
   else
   {
	   Integer numUtenti = operazioniNumUtenti.get(ultimaOp);
	   int numUtenti2 = numUtenti+1;
	   operazioniNumUtenti.replace(ultimaOp, numUtenti, numUtenti2);
   }
   
	%>
</td>
<% 
try{
		  long sd = sessione.getCreationTime();
%>
<td 
<%
	if(sessione != null  && ( adesso.getTime() - dataUltimaOp.getTime() > 1000*(sessione.getMaxInactiveInterval())) )
	{ 
%>
		bgcolor="red"
<%
	} 
%> 

>

<% 
	if(uo!=null)
	{
	   out.println(sdf.format(dataUltimaOp) + " - " + ultimaOp);
	}
%>
<br><br>
<%= formattaMillisecondi(adesso.getTime() - dataUltimaOp.getTime()) %>
</td>
	<%} catch (IllegalStateException ise) {
       out.println("<td>SESSIONE NON VALIDA</td>");
} %>

<td>
<%= utenteLoggato.getAccessPositionLat() + " "+ utenteLoggato.getAccessPositionLon()  + " " + utenteLoggato.getAccessPositionErr() %>
</td>

<td>

<%=sessione.getAttribute("numConnessioniDb")%>
</td>
<td>

<%=(sessione.getAttribute("timeConnOpen")!=null) ? "<img src ='images/rosso.gif'/><br>"+  formattaMillisecondi(adesso.getTime() - ((Date)sessione.getAttribute("timeConnOpen")).getTime() ) : "<img src ='images/verde.gif'/><br>" %>

</td>

</tr> 

<%
}
%>



<%

}

%>
</table>
<br><br>

<%
for(int asl : listaUtentiPerAsl.keySet() ){

%>


<table width="600px">
<thead>
<tr>
<th colspan="3"><%= hashAsl.get(asl) %> &nbsp;NUMERO UTENTI: <%= listaUtentiPerAsl.get(asl).size() %></th>
</tr>
<tr>
<th>USER-ID</th>
<th>USERNAME</th>
<th>IP</th>
</tr>
</thead>

<% for(BUtente utente : listaUtentiPerAsl.get(asl) ){%>
<tr>
<td>
<%= utente.getId() %>
</td>
<td>
<%= utente.getUsername() %>
</td>
<td>
<% 
	UserOperation uo2 = null;
	String ip2 = null;
	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	if(ipUtenti.containsKey(utente.getId()))
		out.println(ipUtenti.get(utente.getId()));
   
   %>
</td>
</tr>

<%} %>

</table>
<br/><br/>
<%	
}
%>
<br><br>



<table border="1" width="600px">
<thead>
<tr>
<th colspan="2">NUMERO UTENTI PER MODULO</th>
</tr>
<tr>
<th>Nome Modulo</th>
<th>Numero Utenti</th>
</tr>

<%
for(String op : operazioniNumUtenti.keySet())
{
%>
<tr>
	<td>
		<%=op%>
	</td>
	<td>
		<%=operazioniNumUtenti.get(op)%>
	</td>
</tr>


<%
}
%>
</thead>


</table>
<br/><br/>
<%	
} else{
%>
NESSUN UTENTE LOGGATO
<%
}
%>


<%
	db.close();
%>
