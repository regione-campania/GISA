<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="it.us.web.action.GenericAction"%>
<%@page import="it.us.web.constants.Specie"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.util.vam.CaninaRemoteUtil"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Test.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<!--  script language="JavaScript" type="text/javascript" src="js/vam/bdr/comunicazioniBduVamCambioDominio.js"></script-->

<form name="myform" id="myform" action="vam.accettazioneMultipla.ToAddAnimale.us" method="post">

<input type="hidden" name="mcAnagrafato" id="mcAnagrafato" value="${mcDaAnagrafare}"/>

<table>
<tr>
<td>
Microchip da anagrafare:
</td>
</tr>

<%
	HashMap<String,Object> datiAnimale = (HashMap<String,Object>)request.getAttribute("datiAnimale");
	LinkedHashMap<String,Boolean> mcAnagrafati = (LinkedHashMap<String,Boolean>)request.getAttribute("mcAnagrafati");
	Iterator<String> keys = mcAnagrafati. keySet() .iterator();	
	Iterator<Boolean> values = mcAnagrafati.values().iterator();
	String mcDaAnagrafare = (String)request.getAttribute("mcDaAnagrafare");
	
	boolean firstAnagrafato = false;
	
	int i=1;
	String          mc = null;
	while(keys.hasNext())
	{
		Boolean anagrafato = values.next();
		mc = keys.next();
		
%>		
	
<tr>
	<td>
<%
	String colorBackground = "red";
	String stato = "da anagrafare";
	
	if(mc.equals(mcDaAnagrafare))
	{
		colorBackground = "yellow";
		stato = "in corso";
	}
	else if(anagrafato)
	{
		colorBackground = "green";
		stato = "anagrafato";
		if(i==1)
			firstAnagrafato = true;
			
	}	
%>
		<label style="background-color: <%=colorBackground%>"><%=mc%></label> --> <%=stato%></label>
		<input type="hidden" name="microchip<%=i%>" id="microchip<%=i%>" value="<%=mc%>"/>
	</td>
</tr>

<%
	i++;
	}
%>
</table>
<br/>

</form>

<%
String label = "Prosegui con Mc Successivo";
String labelAnnulla = "Annulla";
String messaggioAnnulla = "Sicuro di voler annullare?";
if(mc.equals(mcDaAnagrafare))
{
	label = "Anagrafe Animali Completata";
	
}
%>

<div id="retry_buttons">
<input type="button" value="<%=labelAnnulla%>" onclick="javascript:if(myConfirm('<%=messaggioAnnulla%>')){logoutFromCanina( annulla );};" />
</div>

<div id="continue_buttons">
<br/><br/>
<input type="button" value="<%=label%>" onclick="javascript:attendere();if(iscrizioneInserita('<%=(String)request.getAttribute("mcDaAnagrafare")%>',${utente.id})){logoutFromCanina( anagrafeCompletata );}" />

</br></br>
<div id="divToolTip" style="background-color:#FFFFFF; border:2px solid black; 
border-radius:3px; color:red; font-family:Tahoma; font-size:12px; padding:2px 2px 2px 2px; 
max-width:350px; display:none;"></div>  
</div>

<br/>&nbsp;<br/>


<%
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(request);
		%>
		
<iframe 
	id="myFrame" name="myFrame"  frameborder="0"  vspace="0"  
	hspace="0" marginwidth="0" marginheight="0"
	width="95%"  scrolling=yes  height="800"
	style="BORDER-RIGHT: black 1px solid; BORDER-TOP: black 1px solid; Z-INDEX: 999; BORDER-LEFT: black 1px solid; BORDER-BOTTOM: black 1px solid;"
<%
	if(firstAnagrafato)
	{
%>
		src="<%=CaninaRemoteUtil.getLoginUrlAccMultipla( (BUtente)session.getAttribute( "utente" ), (String)request.getAttribute("mcDaAnagrafare"), null, (LookupOperazioniAccettazione)request.getAttribute("operazioneIscrizione"), null, null,connection,request,"2", datiAnimale ) %>"
<%
	}
	else
	{
%>		
		src="<%=CaninaRemoteUtil.getLoginUrlNuovo( (BUtente)session.getAttribute( "utente" ), (String)request.getAttribute("mcDaAnagrafare"), null, (LookupOperazioniAccettazione)request.getAttribute("operazioneIscrizione"), null, null,connection,request,"2" ) %>"
<%
	}
%>	
	>
</iframe>

		<%
		
		
			connection.close();
			GenericAction.aggiornaConnessioneChiusaSessione(request);
		%>

<script type="text/javascript">
var domain = window.location.hostname;
domain = domain.substring(domain.indexOf('.')+1);
document.domain = domain;

function logoutFromCanina( nextFunction )
{
	document.getElementById('myFrame').src='<%=CaninaRemoteUtil.getLogoutUrl( (BUtente)session.getAttribute( "utente" ),request ) %>';
	setTimeout( nextFunction, 1000 );
};

function annulla()
{
	location.href='vam.accettazioneMultipla.Home.us';
};

function anagrafeCompletata()
{
	document.getElementById("myform").submit();
};

function iscrizioneInserita(microchip, utente)
{
	var toReturn = true;
	Test.esisteMc(microchip,
											{
													callback:function(inserita) 
													{ 
														if(!inserita)
														{
															$( "#dialog-modal" ).dialog( "close" );
															alert("Inserire il mc " + microchip + " in bdu prima di proseguire col successivo.");
															toReturn = false;
															$( "#dialog-modal" ).dialog( "close" );
														}
													},
													async: false,
													timeout:20000,
   														errorHandler:function(message, exception)
   														{
   														    //Session timedout/invalidated
   														    if(exception && exception.javaClassName=='org.directwebremoting.impl.LoginRequiredException')
   														    {
   														    	$( "#dialog-modal" ).dialog( "close" );
   														        alert(message);
   														        //Reload or display an error etc.
   														        window.location.href=window.location.href;
   														    }
   														    else
   														        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
   														 }
									 			});
  return toReturn;
	
};
</script>

<script>
dwr.engine.setErrorHandler(errorHandler);
dwr.engine.setTextHtmlHandler(errorHandler);

function errorHandler(message, exception){
    //Session timedout/invalidated

    if(exception && exception.javaClassName== 'org.directwebremoting.impl.LoginRequiredException'){
        alert(message);
        //Reload or display an error etc.
        window.location.href=window.location.href;
    }
    else
        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
 }
</script>
