<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<%@page import="org.aspcfs.utils.web.LookupList"%><jsp:useBean id="OrgDetails" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request" />

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>

<%@ include file="../initPage.jsp"%>
	
	<head>
		<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
		
	</head>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<%
				if (request.getParameter("return") == null)
				{
			%>
					
					<a href="StabilimentoSintesisAction.do?command=DettaglioStabilimento&altId=<%=OrgDetails.getAltId() %>">Scheda Stabilimento</a> >
			<%
				}
				else if (request.getParameter("return").equals("dashboard"))
				{}
			%>
			Stampe Moduli
		</td>
	</tr>
</table>

<%
String param1 = "altId=" + OrgDetails.getAltId()+"&stabId=" + OrgDetails.getIdStabilimento(); request.setAttribute("Operatore",OrgDetails.getOperatore());
%>

<dhv:container 
	name="stabilimenti_macellazioni_ungulati"
	selected="stampe_moduli" 
	object="Operatore"  
	param="<%=param1 %>" 
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' 
	>
	
<form name="macellazioniForm" action="MacellazioniNewSintesis.do?command=StampeModuli&<%=param1%>" method="post">
	<%-- <input type="hidden" name="altId" value="<%=OrgDetails.getAltId() %>" /> --%>

<!-- GUIDA UTENTE-->
<dhv:permission name="guidautente-view">
<a href="javascript:popURL('macellazioni/guida.jsp','CRM_Help','790','500','yes','yes');">
<font size="3px" color="#006699"><b>Clicca qui per la guida utente </font><b></a>
</dhv:permission>

<br>
<br>

<%

HashMap<String,ArrayList<String>> date = (HashMap<String,ArrayList<String>> ) request.getAttribute("DateStampa");

ArrayList<String> lista1 = date.get("GET_DATE_IDATIDOSI");
ArrayList<String> lista2 = date.get("GET_DATE_MODELLO_MARCHI");
ArrayList<String> lista3 = date.get("GET_DATE_ANIMALI_INFETTI");
ArrayList<String> lista4 = date.get("GET_DATE_ANIMALI_GRAVIDI");
ArrayList<String> lista5 = date.get("GET_DATE_TBC_RILEVAZIONE_MACELLO");
ArrayList<String> lista6 = date.get("GET_DATE_BRC_RILEVAZIONE_MACELLO");
ArrayList<String> lista7 = date.get("GET_DATE_1033_TBC");
ArrayList<String> lista8 = date.get("GET_DATE_EVIDENZE_VISITA_ANTE_MORTEM");
ArrayList<String> lista9 = date.get("GET_DATE_MORTE_ANTE_MACELLAZIONE");
ArrayList<String> lista10 = date.get("GET_DATE_ANIMALI_LEB");
ArrayList<String> lista11 = date.get("GET_DATE_TRASPORTI_ANIMALI_INFETTI");
				 


 		  			
				  			
		   



	  			
 

%>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">



<table cellpadding="5" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="4">
            <strong><dhv:label name="">Lista Moduli Macelli</dhv:label></strong>
          </th>
         </tr> 
         <tr> 
          <th>
            <strong>Nome</strong>
          </th>
          <th>
            <strong><dhv:label name="">Data</dhv:label></strong>
          </th>
          <th>
            <strong><dhv:label name="">Note</dhv:label></strong>
          </th>
          <th>
            <strong><dhv:label name="">Azione</dhv:label></strong>
          </th>
        </tr>
        <!-- Modello Idatidosi -->

		<tr>
       		<td >
	       		<dhv:label name="">1. Modello Idatidosi</dhv:label>
       		</td>
       		<td >
       		<%= LookupList.stampaCombo(lista1, "data1")%>
<!--          		<input readonly type="text" name="data1" id="data1" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di macellazione</font>
       		</td>
       		<td>
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista1.isEmpty()){%>disabled="disabled"<%} %> value="Genera Modulo" onclick="this.form.tipoModulo.value='1'" />
       		</td>
  	   	</tr>
		<!-- Modello Marchi, ovvero di arrivo al macello -->
		<tr>
       		<td >
       			<dhv:label name="">2. Modello Marchi</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data2" id="data2" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data2,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
<%= LookupList.stampaCombo(lista2, "data2")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di arrivo al macello</font>
       		</td>
       		<td >
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista2.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='2'"/>
       		</td>
  	   	</tr>
		<!-- Modello Animali Infetti -->
		
		<tr>
       		<td >
       			<dhv:label name="">3. Modello Animali Infetti</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data3" id="data3" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data3,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
<%= LookupList.stampaCombo(lista3, "data3")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di macellazione</font>
       		</td>
       		<td>
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista3.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='3'" />
       		</td>
  	   	</tr>
		
		<!-- Modello Animali Gravidi-->

		<tr>
       		<td >
       			<dhv:label name="">4. Modello Animali Gravidi</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data4" id="data4" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data4,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
<%= LookupList.stampaCombo(lista4, "data4")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di macellazione</font>
       		</td>
       		<td >
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista4.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='4'"/>
       		</td>
  	   	</tr>
  	   	
  	   	<!-- Modello TBC -->
  	   	
  	   	<tr>
       		<td>
       			<dhv:label name="">5. Modello TBC Rilevazione Macello</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data5" id="data5" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data5,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
<%= LookupList.stampaCombo(lista5, "data5")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di macellazione</font>
       		</td>
       		<td>
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista5.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='5'" />
       		</td>
  	   	</tr>

		<!-- Modello BRC rilevazione macelli -->
		
		<tr>
       		<td >
       			<dhv:label name="">6. Modello BRC Rilevazione Macello</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data6" id="data6" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data6,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
       		
       		<%= LookupList.stampaCombo(lista6, "data6")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di macellazione</font>
       		</td>
       		<td >
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista6.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='6'"/>
       		</td>
  	   	</tr>
  	   	
  	   	<!-- Modello TBC 10/33 -->
  	   	
  	   	<tr>
       		<td >
       			<dhv:label name="">7. Modello TBC 10/33</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data7" id="data7" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data7,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
<%= LookupList.stampaCombo(lista7, "data7")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di macellazione</font>
       		</td>
       		<td >
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista7.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='7'" />
       		</td>
  	   	</tr>
  	   	
  	   	<!-- Modello evidenze ante mortem -->
  	   
  	   	<tr>
       		<td >
	       		<dhv:label name="">8. Modello Evidenze Ante Mortem</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data8" id="data8" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data8,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
       		<%= LookupList.stampaCombo(lista8, "data8")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di arrivo al macello</font>
       		</td>
       		<td >
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista8.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='8'" />
       		</td>
  	   	</tr>
  	   	
  	   	<!-- Modello antecedente la macellazione  -->
  	   
  	   	<tr>
       		<td >
       			<dhv:label name="">9. Modello Antecedente la Macellazione</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data9" id="data9" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data9,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
       		
       		<%= LookupList.stampaCombo(lista9, "data9")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di arrivo al macello</font>
       		</td>
       		<td >
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista9.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='9'"/>
       		</td>
  	   	</tr>
  	   	
  	   	<!-- Modello LEB -->
  	   
  	   	<tr>
       		<td >
	       		<dhv:label name="">10. Modello LEB</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data10" id="data10" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data10,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
       		<%= LookupList.stampaCombo(lista10, "data10")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di arrivo al macello</font>
       		</td>
       		<td >
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista10.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='10'"/>
       		</td>
  	   	</tr>
  	   
  	    <!-- Modello LEB -->
  	   	
  	   	<tr>
       		<td >
	       		<dhv:label name="">11. Modello Disinfezione Mezzi di Trasporto</dhv:label>
       		</td>
       		<td >
<!--          		<input readonly type="text" name="data11" id="data11" size="10" />-->
<!--		  		<a href="#" onClick="cal19.select(document.forms[0].data11,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">-->
<!--		  		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>		  	-->
       		<%= LookupList.stampaCombo(lista11, "data11")%>
       		</td>
       		<td>
       			<font color = "red"> Inserire la data di arrivo al macello</font>
       		</td>
       		<td >
       			<input type="submit" id="generaPdf" name="generaPdf" <%if(lista11.isEmpty()){%>disabled="disabled"<%} %> value = "Genera Modulo"  onclick="this.form.tipoModulo.value='11'"/>
       		</td>
  	   	</tr>
  	   	
  	   	<input type="hidden" id="tipoModulo" name="tipoModulo" value="" />
</table>

<p style="color: red;">
<%= request.getAttribute("messaggio") != null && !request.getAttribute("messaggio").equals("") ? request.getAttribute("messaggio") : "" %>
</p>
</dhv:permission>
</form>
<%--

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNewSintesis.do?command=ToRegistroMacellazioni&<%=param1 %>">Registro Macellazioni</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNewSintesis.do?command=ToArt17&<%=param1 %>">Articolo 17</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNewSintesis.do?command=ToMortiStalla&<%=param1 %>">Anim. morti in stalla/trasporto</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNewSintesis.do?command=ToBSE&<%=param1 %>">Modulo BSE</a>
</dhv:permission>\



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
	<a href="MacellazioniNewSintesis.do?command=PrintDisinfezioneMezziTrasporto&file=disinfezione_mezzi_di_trasporto.xml&<%=param1 %>">Disinfezione mezzi di trasporto</a>
</dhv:permission>
 --%> 

</dhv:container>

