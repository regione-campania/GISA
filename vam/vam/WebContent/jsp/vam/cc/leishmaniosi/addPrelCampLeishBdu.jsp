<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.action.GenericAction"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<%@page import="it.us.web.bean.vam.Animale"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/TestBdr.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script language="JavaScript" type="text/javascript" src="js/vam/bdr/registrazioniInterattive.js"></script>

<%@page import="it.us.web.util.vam.CaninaRemoteUtil"%>
<%@page import="it.us.web.bean.BUtente"%>

<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
<script language="JavaScript" type="text/javascript" src="js/vam/bdr/comunicazioniBduVamCambioDominio.js"></script>
<!--

//-->
</script>


<h4 class="titolopagina">
     		Leishmaniosi
    </h4>
  
Operazione da registrare: Prelievo Leishmania
<div id="continue_buttons" style="visibility: hidden;">
<br/><br/>
<c:if test="${cc.accettazione.animale.lookupSpecie.id==specie.cane}">
	<input type="button"  value="Inserimento Registrazioni Completato" onclick="javascript:if(registrazioneInserita('${cc.accettazione.animale.identificativo}',${cc.accettazione.animale.lookupSpecie.id},${idTipoRegBdr})){attendere(),logoutFromCanina( inserimentoRegistrazioniCompletato );}" />
	<!-- input type="button" value="Annulla"                              onclick="javascript:attendere(),logoutFromCanina( annulla );" /-->
</c:if>
<c:if test="${cc.accettazione.animale.lookupSpecie.id==specie.gatto}">
	<input type="button" value="Inserimento Registrazioni Completato" onclick="javascript:if(registrazioneInserita('${cc.accettazione.animale.identificativo}',${cc.accettazione.animale.lookupSpecie.id},${idTipoRegBdr})){attendere(),logoutFromFelina( inserimentoRegistrazioniCompletato );}" />
	<!-- input type="button" value="Annulla"                              onclick="javascript:attendere(),logoutFromFelina( annulla );" /-->
</c:if>
</br></br>
<div id="divToolTip" style="background-color:#FFFFFF; border:2px solid black; 
border-radius:3px; color:red; font-family:Tahoma; font-size:12px; padding:2px 2px 2px 2px; 
max-width:350px; display:none;"></div>  
</div>

<br/><br/>

<%
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(request);
		%>
		
<c:if test="${cc.accettazione.animale.lookupSpecie.id==specie.cane}">
	<iframe 
		id="myFrame" name="myFrame"  frameborder="0"  vspace="0"  
		hspace="0" marginwidth="0" marginheight="0"
		width="95%"  scrolling=yes  height="800"
		style="BORDER-RIGHT: black 1px solid; BORDER-TOP: black 1px solid; Z-INDEX: 999; BORDER-LEFT: black 1px solid; BORDER-BOTTOM: black 1px solid;"
		src="<%=CaninaRemoteUtil.getLoginUrl( (BUtente)session.getAttribute( "utente" ), null, (Animale)request.getAttribute("animale"), (LookupOperazioniAccettazione)request.getAttribute("operazionePrelievoLeishmania"), ((CartellaClinica)session.getAttribute("cc")).getAccettazione(), null,connection,request,"2" ) %>">
	</iframe>
</c:if>
<c:if test="${cc.accettazione.animale.lookupSpecie.id==specie.gatto}">
	<iframe 
		id="myFrame" name="myFrame"  frameborder="0"  vspace="0"  
		hspace="0" marginwidth="0" marginheight="0"
		width="95%"  scrolling=yes  height="800"
		style="BORDER-RIGHT: black 1px solid; BORDER-TOP: black 1px solid; Z-INDEX: 999; BORDER-LEFT: black 1px solid; BORDER-BOTTOM: black 1px solid;"
		src="<%=CaninaRemoteUtil.getLoginUrl( (BUtente)session.getAttribute( "utente" ), null, (Animale)request.getAttribute("animale"), (LookupOperazioniAccettazione)request.getAttribute("operazionePrelievoLeishmania"), ((CartellaClinica)session.getAttribute("cc")).getAccettazione(), null,connection,request,"2" ) %>">
	</iframe>
</c:if>

<c:if test="${cc.accettazione.animale.lookupSpecie.id==specie.cane}">
	<script type="text/javascript">
		function logoutFromCanina(nextFunction  )
		{
			document.getElementById('myFrame').src='<%=CaninaRemoteUtil.getLogoutUrl( (BUtente)session.getAttribute( "utente" ),request ) %>';
			setTimeout( nextFunction, 1000 );
		};
		function inserimentoRegistrazioniCompletato()
		{
			location.href='vam.cc.leishmaniosi.List.us?';
		}

	</script>
</c:if>
<c:if test="${cc.accettazione.animale.lookupSpecie.id==specie.gatto}">
	<script type="text/javascript">
		function logoutFromFelina(nextFunction  )
		{
			<%=CaninaRemoteUtil.getLogoutUrl( (BUtente)session.getAttribute( "utente" ),request ) %>
			//document.getElementById('myFrame').src='<%=CaninaRemoteUtil.getLogoutUrl( (BUtente)session.getAttribute( "utente" ),request ) %>';
			setTimeout( nextFunction, 1000 );
		};
		function inserimentoRegistrazioniCompletato()
		{
			location.href='vam.cc.leishmaniosi.List.us?';
		}

	</script>
</c:if>

<script type="text/javascript">
var domain = window.location.hostname;
domain = domain.substring(domain.indexOf('.')+1);
document.domain = domain;

function annulla()
{
	location.href='vam.cc.leishmaniosi.List.us?';
};
</script>


<%
connection.close();
GenericAction.aggiornaConnessioneChiusaSessione(request);
%>

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

