<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo"%>
<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request" />
<%@ include file="../initPage.jsp"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>



<%
	String param1 = "altId=" + OrgDetails.getAltId()+"&stabId=" + OrgDetails.getIdStabilimento(); request.setAttribute("Operatore",OrgDetails.getOperatore());
	ConfigTipo configTipo = (ConfigTipo)request.getSession().getAttribute("configTipo");
%>
<dhv:container 
	name="sintesismacelli"
	selected="macellazioni" 
	object="Operatore"  
	param="<%=param1 %>" 
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' 
	>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-add">
	<a href="MacellazioniNewSintesis.do?command=NuovoCapo&<%=param1 %>" style="text-decoration:none"><input type="button" value="Aggiungi partita"/></a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNewSintesis.do?command=ToRegistroMacellazioni&<%=param1 %>" style="text-decoration:none"><input type="button" value="Registro macellazioni"/></a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNewSintesis.do?command=ToArt17&<%=param1 %>" style="text-decoration:none"><input type="button" value="Articolo 17"/></a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<!-- a href="MacellazioniDocumentiNewSintesis.do?command=ToMod10&<%=param1 %>" style="text-decoration:none"><input type="button" value="Modello 10"/></a -->
</dhv:permission>

<%if(ApplicationProperties.getProperty("visibilita_link_macelli").equals("si")){ %>
<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNewSintesis.do?command=ToMortiStalla&<%=param1 %>">Anim. morti in stalla/trasporto</a>
</dhv:permission>



<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNewSintesis.do?command=ToBSE&<%=param1 %>">Modulo BSE</a>
</dhv:permission>



<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNewSintesis.do?command=ToAbbattimento&<%=param1 %>">Abbattimento</a>
</dhv:permission>


<br/><br/>


<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNewSintesis.do?command=PrintBRCRilevazioneMacelli&file=BRC_rilevazione_macelli.xml&<%=param1 %>">BRC rilevazione macelli</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNewSintesis.do?command=PrintTBCRilevazioneMacelli&file=TBC_rilevazione_macelli.xml&<%=param1 %>">TBC rilevazione macelli</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNewSintesis.do?command=ToModelloIdatidosi&<%=param1 %>">Modello idatidosi</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNewSintesis.do?command=PrintModelloMarchi&file=modello_marchi.xml&<%=param1 %>">Modello marchi</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNewSintesis.do?command=PrintMacellazioneAnimaliInfetti&file=macellazione_animali_infetti.xml&<%=param1 %>">Macellazione animali infetti</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNewSintesis.do?command=PrintDisinfezioneMezziTrasporto&file=disinfezione_mezzi_di_trasporto.xml&<%=param1 %>">Disinfezione mezzi di trasporto</a>
</dhv:permission>
<%} %>


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red"><br/><%=toHtmlValue( (String)request.getAttribute( "messaggio" ) ) %></font>

<br/><br/>
 
 Attenzione! Si ricorda che per concludere definitivamente la seduta di macellazione occorre stampare l'articolo 17 e che tutti i dati saranno riportati nel registro di macellazione.
 
 
<table border="1" bordercolor="#729FCF" align="center" style="text-align: center;" width="500px">
<tr>
<th>Partite Macellate</th>
<th>Partite NON Macellate</th>
</tr>
<tr>
<td>

<%
ArrayList<String> listaDateMacellazione = (ArrayList<String>)request.getAttribute("listaDateMacellazione");
if(listaDateMacellazione!=null && listaDateMacellazione.size() > 0){
%>
<form name="macellazioniForm" action="MacellazioniNewSintesis.do?command=List" method="post">
	<input type="hidden" name="altId" value="<%=OrgDetails.getAltId() %>" />
	<p>Seleziona la data di macellazione</p>
	<select id="comboDateMacellazione" name="comboDateMacellazione">
	<%for(String dataMacellazione : listaDateMacellazione){%>		
	<option value="<%= dataMacellazione %>" <%if(dataMacellazione.equals(request.getParameter("comboDateMacellazione"))){%> selected="selected"<% } %>  ><%= dataMacellazione %></option>
	<%		
	}
	%>
<!--	<option value="-1" <%if("-1".equals(request.getParameter("comboDateMacellazione"))){%> selected="selected"<% } %>>Capi non macellati</option>-->
	</select>
	<input readonly type="text" id="campoDataMacellazione" name="campoDataMacellazione" size="10" style="display: none" />&nbsp;  
	<a href="#" onClick="cal19.select(document.forms[0].campoDataMacellazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
	<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
	<input type="submit" value="Procedi"></input>
</form>

<%}else{ %>
	Nessuna partita macellata

<%
}
%>

</td>
<td>

<form name="macellazioniForm" action="MacellazioniNewSintesis.do?command=List" method="post">
	<input type="hidden" name="altId" value="<%=OrgDetails.getAltId() %>" />
	<input type="hidden" name="comboDateMacellazione" value="-1" />
	<input type="submit" value="Procedi"></input>
</form>
</td>
</tr>
</table>

<%
if(listaDateMacellazione!=null && listaDateMacellazione.size() > 0){
%>
<input type="hidden" name="altId" value="<%=OrgDetails.getAltId() %>" />
<% } %>



<br/>
		<% String dataMacellaz = ""; %>
		<%
		if(request.getParameter("comboDateMacellazione") != null && !request.getParameter("comboDateMacellazione").equals("") ){ 
			dataMacellaz = request.getParameter("comboDateMacellazione");
		%>
			Lista partite <%= dataMacellaz.equals("-1") ? "non macellate" : "macellati il " + dataMacellaz %>
		<%}else if(listaDateMacellazione!=null && listaDateMacellazione.size() > 0){ 
			dataMacellaz = listaDateMacellazione.get(0);
		%>
			Lista partite del <%= dataMacellaz %>
		<%} %>
		
		<form name="macellazioniForm" method="post" action="MacellazioniNewSintesis.do?command=List">
			<input type="hidden" name="altId" value="<%=OrgDetails.getAltId() %>" />
			<input type = "hidden" name = "comboDateMacellazione"  value="<%=dataMacellaz %>"/>
	       <%=request.getAttribute( "tabella" )%>
	    </form>
	    
	 <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
                alert(parameterString);
                //location.href = 'MacellazioniNewSintesis.do?command=List&' + parameterString;
            }
            function toChiudiPartita(id,id_mac)
            {
            	window.open('MacellazioniNewSintesis.do?command=ToChiudiPartita&id=' + id + '&altId=' + id_mac ,'Chiudi Partita','width=1000, height=500,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
            }
            function toMostraMotivazioni(id,id_mac)
            {
            	window.open('MacellazioniNewSintesis.do?command=ToMostraMotivazioni&id=' + id + '&altId=' + id_mac ,'Motivazioni Chiusura','width=1000, height=500,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
            }
     </script>
 
</dhv:container>