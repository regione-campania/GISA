<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.darkhorseventures.database.ConnectionPool"%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<%@page import="org.aspcfs.controller.SubmenuItem"%>
<%@page import="java.net.URL"%>
<%@page import="org.aspcfs.controller.MainMenuItem"%>
<%@page import="com.zeroio.controller.Tracker"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.aspcfs.controller.ApplicationPrefs"%>
<%@page import="org.aspcfs.controller.SystemStatus"%>
<%@page import="java.util.Hashtable"%>
<%@page import="org.aspcfs.controller.SessionManager"%>
<%@page import="java.util.HashMap"%> 
<%@page import="org.aspcfs.controller.UserSession"%>
<%@page import="org.aspcfs.modules.admin.base.User"%>
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
<%@page import="org.aspcfs.utils.XMLUtils"%>
<%@page import="org.w3c.dom.NodeList"%>




<style type="text/css">

/*
This was made by João Sardinha
Visit me at http://johnsardine.com/

The table style doesnt contain images, it contains gradients for browsers who support them as well as round corners and drop shadows.

It has been tested in all major browsers.

This table style is based on Simple Little Table By Orman Clark,
you can download a PSD version of this at his website, PremiumPixels.
http://www.premiumpixels.com/simple-little-table-psd/

PLEASE CONTINUE READING AS THIS CONTAINS IMPORTANT NOTES

*/

/*
Im reseting this style for optimal view using Eric Meyer's CSS Reset - http://meyerweb.com/eric/tools/css/reset/
------------------------------------------------------------------ */

table {border-spacing: 0; } /* IMPORTANT, I REMOVED border-collapse: collapse; FROM THIS LINE IN ORDER TO MAKE THE OUTER BORDER RADIUS WORK */

/*------------------------------------------------------------------ */



/*
Table Style - This is what you want
------------------------------------------------------------------ */


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


<%

HashMap<String,ArrayList<UserSession>> utentiAction = new HashMap<String,ArrayList<UserSession>> ();
utentiAction.clear();
URL url = null ;
ArrayList<MainMenuItem> menuItems;
menuItems = new ArrayList<MainMenuItem>() ;
url = application.getResource("/WEB-INF/cfs-modules.xml" );

System.out.println("URL "+url.getPath());
XMLUtils xml = new XMLUtils(url);

//Process the menu tags
menuItems.clear();
NodeList menuTags = xml.getDocument().getElementsByTagName("menu");
for (int i = 0; i < menuTags.getLength(); i++) {
  Element menuTag = (Element) menuTags.item(i);
  MainMenuItem thisMenu = parseMenu(menuTag);
  menuItems.add(thisMenu);
}

%>

<%!
 public MainMenuItem parseMenu(Element e) {
    MainMenuItem mainItem = new MainMenuItem();
    //mainItem.setName(e.getAttribute("name"));
    ArrayList submenuTable = mainItem.getSubmenuItems();

    NodeList children = e.getChildNodes();
    int len = children.getLength();
    for (int i = 0; i < len; i++) {
      if (children.item(i).getNodeType() != Element.ELEMENT_NODE) {
        continue;
      }
      Element child = (Element) children.item(i);
      String childName = child.getTagName();

      if (childName.equals("action")) {
        mainItem.addActionName(child.getAttribute("name"));
      } else if (childName.equals("page")) {
        mainItem.setPageTitle(child.getAttribute("title"));
      } else if (childName.equals("permission")) {
        mainItem.setPermission(child.getAttribute("value"));
      } else if (childName.equals("long_html")) {
        mainItem.setLongHtml(child.getAttribute("value"));
        mainItem.setLongHtmlRollover(child.getAttribute("rollover"));
      } else if (childName.equals("short_html")) {
        mainItem.setShortHtml(child.getAttribute("value"));
        mainItem.setShortHtmlRollover(child.getAttribute("rollover"));
      } else if (childName.equals("link")) {
        mainItem.setLink(child.getAttribute("value"));
        mainItem.setClassNormal(child.getAttribute("classNormal"));
        mainItem.setClassSelected(child.getAttribute("classSelected"));
      } else if (childName.equals("graphic")) {
        mainItem.setGraphicWidth(child.getAttribute("width"));
        mainItem.setGraphicHeight(child.getAttribute("height"));
        mainItem.setGraphicOn(child.getAttribute("on"));
        mainItem.setGraphicOff(child.getAttribute("off"));
        mainItem.setGraphicRollover(child.getAttribute("rollover"));
      } else if (childName.equals("submenu")) {
        SubmenuItem submenuItem = new SubmenuItem();
        submenuItem.setName(child.getAttribute("name"));
        NodeList submenu = child.getChildNodes();
        int submenuLen = submenu.getLength();
        for (int j = 0; j < submenuLen; j++) {
          if (submenu.item(j).getNodeType() != Element.ELEMENT_NODE) {
            continue;
          }
          Element submenuChild = (Element) submenu.item(j);
          String tagName = submenuChild.getTagName();

          if (tagName.equals("permission")) {
            submenuItem.setPermission(submenuChild.getAttribute("value"));
          } else if (tagName.equals("long_html")) {
            submenuItem.setLongHtml(submenuChild.getAttribute("value"));
          } else if (tagName.equals("short_html")) {
            submenuItem.setShortHtml(submenuChild.getAttribute("value"));
          } else if (tagName.equals("link")) {
            submenuItem.setLink(submenuChild.getAttribute("value"));
          } else if (tagName.equals("graphic")) {
            submenuItem.setGraphicWidth(submenuChild.getAttribute("width"));
            submenuItem.setGraphicHeight(submenuChild.getAttribute("height"));
            submenuItem.setGraphicOn(submenuChild.getAttribute("on"));
            submenuItem.setGraphicOff(submenuChild.getAttribute("off"));
            submenuItem.setGraphicRollover(
                submenuChild.getAttribute("rollover"));
          }
        }
        submenuTable.add(submenuItem);
      }
    }

    return mainItem;
  }
  %>
  
  
  
<%!
 public String getLabel(String action,HashMap<String,ArrayList<UserSession>> utentiAction,ArrayList<MainMenuItem> menuItems,UserSession u) {
   
	boolean trovato = false ;
	for(MainMenuItem thisItem : menuItems)
	{
		
		trovato=false;
		for(int i = 0 ; i <thisItem.getActionNames().size();i++)
		{
			
			if(((String)thisItem.getActionNames().get(i)).equalsIgnoreCase(action))
			{
				trovato = true ;
				
				if (utentiAction.containsKey(thisItem.getShortHtml()))
				{
					ArrayList<UserSession> lista = utentiAction.get(thisItem.getShortHtml());
					
					lista.add(u);
					
					utentiAction.put(thisItem.getShortHtml(), lista);
				}
				else{
					ArrayList<UserSession> lista = new ArrayList<UserSession>();
					lista.add(u);
					utentiAction.put(thisItem.getShortHtml(), lista);
				}
				break;
			}
			
		}
		if(trovato==true)
			break;
		
	}
	
	if (trovato==false)
		if (utentiAction.containsKey("ACTION ON CONFIGURATE"))
		{
			ArrayList<UserSession> lista = utentiAction.get("ACTION ON CONFIGURATE");
			
			lista.add(u);
			
			utentiAction.put("ACTION ON CONFIGURATE", lista);
		}
		else{
			ArrayList<UserSession> lista = new ArrayList<UserSession>();
			lista.add(u);
			utentiAction.put("ACTION ON CONFIGURATE", lista);
		}
	return "";
	
  }
  %>

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



ConnectionPool cp = new ConnectionPool("java:comp/env/jdbc/bduM");
SystemStatus thisSystem = null; 
SessionManager sessionManager = null;
HashMap sessions = null;
thisSystem = (SystemStatus) ((Hashtable) application.getAttribute("SystemStatus")).get(cp.getUrl());
if(thisSystem != null){
	sessionManager = thisSystem.getSessionManager();
	
}
if(sessionManager != null){
	sessions = sessionManager.getSessions();
}


TreeMap<Integer, ArrayList<User>> listaUtentiPerAsl = new TreeMap<Integer, ArrayList<User>>();
listaUtentiPerAsl.put(-1, new ArrayList<User>());
listaUtentiPerAsl.put(201, new ArrayList<User>());
listaUtentiPerAsl.put(202, new ArrayList<User>());
listaUtentiPerAsl.put(203, new ArrayList<User>());
listaUtentiPerAsl.put(204, new ArrayList<User>());
listaUtentiPerAsl.put(205, new ArrayList<User>());
listaUtentiPerAsl.put(206, new ArrayList<User>());
listaUtentiPerAsl.put(207, new ArrayList<User>());

User u = null;
UserSession s = null;

if(sessions != null && sessions.size() > 0){

%>


<table><tr><tH colspan="3" style=""><a href="checkUtentiPo.jsp"  >VISUALIZZA MAPPA</a>
<a href="cleanUs.jsp"  >ELIMIA UTENTI US</a>
 NUMERO UTENTI: <%= sessions.keySet().size() %></tH></tr>
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
<th colspan="9">Tutti</th>
</tr>
<tr>
<th>PROG.</th>
<th>ID SESSIONE</th>
<th>ID UTENTE</th>
<th>USERNAME</th>
<th>ASL</th>
<th>IP</th>
<th>ETA' ULTIMA OP.</th>
<th>Num Connessioni Attive</th>
<th>Durata Connessione</th>
</tr>
</thead>
<% 
out.print(sessions.keySet().size());
int aslUtente = 0;
int prog = 0 ;
for(Object o : sessions.keySet()){
	s = (UserSession)sessions.get(Integer.parseInt(o.toString()));
	u = thisSystem.getUser(Integer.parseInt(o.toString()));
	if(u.getSiteId() == 0 || u.getSiteId() == -1 || u.getSiteId() == 16 ){
		aslUtente = -1;
	}
	else{
		aslUtente = u.getSiteId();
	}
	
	if ( s.getLastOperation()!=null && s.getLastOperation().indexOf("-")>0)
		getLabel(s.getLastOperation().substring(0,  s.getLastOperation().indexOf("-")),utentiAction,menuItems,s);
	else
		if ( s.getLastOperation()!=null)
		getLabel(s.getLastOperation(),utentiAction,menuItems,s);
	listaUtentiPerAsl.get(aslUtente).add(u);
	prog ++ ;
	
	
	
		

%>

<tr>
<td><%=prog %></td>
<td>


<%= (s.getSessionUser() !=null)  ? s.getSessionUser().getId() : ""%>
</td>
<td>
<%= o.toString() %>
</td>
<td>
<%= u.getUsername() %>
</td>
<td>
<%= hashAsl.get(aslUtente) %>
</td>
<td>
<%= u.getIp() %>

</td>
<% 
try{
		  long sd = session.getCreationTime();
		  UserBean userb = (UserBean)s.getSessionUser().getAttribute("User");
%>
<td <%if(s.getSessionUser()!=null &&  session != null  && ( adesso.getTime() -s.getSessionUser().getLastAccessedTime() > 1000*(session.getMaxInactiveInterval())) ){ %>bgcolor="red"<%} %> >

<%= s.getLastOperation() %>
<br><br>
<%= formattaMillisecondi(adesso.getTime() - s.getSessionUser().getLastAccessedTime()) %>
</td>

<td>

<%=userb.getNumConnessioniDb() %>
</td>
<td>

<%= (userb.getTimeConnopen()!=null) ? "<img src ='rosso.gif'/><br>"+  formattaMillisecondi(adesso.getTime() - userb.getTimeConnopen().getTime() ) : "<img src ='verde.gif'/><br>" %>

</td>
</tr> 

	<%} catch (IllegalStateException ise) {
	   Tracker tracker = thisSystem.getTracker();
       tracker.remove(s.getSessionUser().getId());
       out.println("<td>SESSIONE NON VALIDA</td>");
       out.println("<td>SESSIONE NON VALIDA</td>");
       out.println("<td>SESSIONE NON VALIDA</td>");
} %>




<%
}
%>

</table>
<br><br>

<% 
for(int asl : listaUtentiPerAsl.keySet() ){

%>


<table border="1" width="600px">
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

<% for(User utente : listaUtentiPerAsl.get(asl) ){%>
<tr>
<td>
<%= utente.getId() %>
</td>
<td>
<%= utente.getUsername() %>
</td>
<td>
<%= utente.getIp() %>
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
<th colspan="2">NUMERO UTENTI PER MODULO <font color="red">ATTENZIONE! la somma degli utenti per modulo potrebbe non coincidere con il numero totale degli utenti in quanto ci sono alcune Action non configurate nei moduli.</font></th>
</tr>
<tr>
<th>Nome Modulo</th>
<th>Numero Utenti</th>
</tr>
</thead>


<% 
int somma = 0 ;
for(String key: utentiAction.keySet() ){

	Integer val = utentiAction.get(key).size();
	somma+=val;
%>


<tr>
<td>
<%= key.toUpperCase() %>
</td>
<td>
<%= val %>
</td>

</tr>

<%} %>
<tr><td colspan="2">TOT. <%=somma %></td></tr>
</table>
<br/><br/>
<%	

%>

<%
} else{
%>
NESSUN UTENTE LOGGATO
<%
}
%>



