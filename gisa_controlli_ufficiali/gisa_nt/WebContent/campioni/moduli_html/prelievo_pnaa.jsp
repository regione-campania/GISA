<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script>
function closeAndRefresh(chiudi,url) {
	if(chiudi == "si")
	{
		self.close();
		window.opener.location.href = url;
	 	
	}	
}	

function checkForm()
{
	var ret = true;
	loadModalWindow();
	//return true;
	//document.getElementById('myform').submit();
	//Stampa pdf
	document.getElementById('stampaPdfId').click();
	return ret;
	
}

function gestisciRadioButton(check){
	
	var el0 = check.name+'_testo';
	var el = check.id+'_testo';
	if (check.checked){
		var size = document.getElementsByName(el0).length;
		for (i=0;i<size;i++)
			document.getElementsByName(el0)[i].value='';
		document.getElementById(el).value='X';
} }

function gestisciCheckBox(check){
	
	var el = check.id+'_testo';
	if (check.checked)
		document.getElementById(el).value='X';
	else
		document.getElementById(el).value=''
 }

</script>

<!--  SERVER DOCUMENTALE: FUNZIONAMENTO RADIOBUTTON
AGGIUNTA CAMPO NASCOSTO DI TESTO (CLASSE EDITFIELD_FAC) CON :
NOME: NOME RADIOBUTTON _ TESTO
ID: ID RADIOBUTTON _ TESTO

ONCLICK: FUNZIONE JAVASCRIPT CHE SETTA A X IL VALORE E A NULL IL VALORE DEGLI ALTRI TESTI RELATIVI ALLO STESSO RADIOBUTTON
RADIOBUTTON: IF VALORISCELTI.GET(Z) = 'X' THEN CHECKED
IL TUTTO IN UN DIV DISPLAY NONE (PER EVITARE CHE VENGA VISUALIZZATO IN STAMPA)
 -->



<%int z = 0; %>
<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/header_pnaa.jsp" %>
<!-- FINE HEADER -->
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<dhv:permission name="server_documentale-view">
<%if (definitivoDocumentale!=null && definitivoDocumentale.equals("true")){ %>
<!--  BOX DOCUMENTALE -->
	  <jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="orgId" value="<%=request.getParameter("orgId") %>" />
     <jsp:param name="ticketId" value="<%=request.getParameter("idCampione") %>" />
      <jsp:param name="tipo" value="<%=request.getParameter("tipo") %>" />
       <jsp:param name="idCU" value="<%=request.getParameter("idCU") %>" />
        <jsp:param name="url" value="<%=request.getParameter("url") %>" />
          <jsp:param name="idCampione" value="<%=request.getParameter("idCampione") %>" />
</jsp:include>
<!--  BOX DOCUMENTALE -->
<% } else  {%>
<form method="post" name="form2" id="form2" action="CampioniReport.do?command=ViewSchedaPNAA2">

<input id="stampaPdfId" type="button" class = "buttonClass" value ="Genera e Stampa PDF" onclick="javascript:salva(this.form)"/>
<input type="hidden" id="documentale" name ="documentale" value="ok"></input>
<input type="hidden" id="listavalori" name ="listavalori" value=""></input>
 <input type="hidden" id ="orgId" name ="orgId" value="<%=request.getParameter("orgId") %>" />
  <input type="hidden" id ="ticketId" name ="ticketId" value="<%=request.getParameter("idCampione") %>" />
   <input type="hidden" id ="tipo" name ="tipo" value="<%=request.getParameter("tipo") %>" />
    <input type="hidden" id ="idCU" name ="idCU" value="<%=request.getParameter("idCU") %>" />
      <input type="hidden" id ="url" name ="url" value="<%=request.getParameter("url") %>" />
      <input type="hidden" id ="idCampione" name ="idCampione" value="<%=request.getParameter("idCampione") %>" />

</form>
<% } %>
</dhv:permission>

<!--  PER SALVA & STAMPA -->
<div style="display:none"><iframe name="response" height="0" width="0"></iframe>
</div>

<br/><br/>

<b>ENTE DI APPARTENENZA</b> <input class="layout" type="text" readonly="readonly" name="asl" id="asl" size="30" value="<%= PnaaDetails.getEnte_appartenenza()%>"/><br/>
<b>UNITA' TERRITORIALE-DISTRETTO</b> <input class="layout" type="text" readonly="readonly" size="30" name="distretto" id="distretto" value="<%=PnaaDetails.getUnita_territoriale()%>" />

<br/><br/>
L'anno <input class="layout" type="text" readonly size="4" value="<%=PnaaDetails.getAnno() %>" /> 
addi' <input class="layout" type="text" readonly size="4" value="<%=PnaaDetails.getGg() %>"/>
del mese di <input class="layout" type="text" readonly size="10" value="<%=getMeseFromData(PnaaDetails.getMese()) %>"/>
alle ore <input class="editField_fac" type="text" size="5"  name="ore" id="ore" value="<%=valoriScelti.get(z++) %>"/> <br/>
i sottoscritti dr. <input class="layout" type="text" readonly size="120" value="<%=PnaaDetails.getA4() %>"/>, <br> 
dopo essersi qualificati e aver fatto conoscere lo scopo della visita, alla presenza del Sig. 
 <input class="editField_fac" type="text" name="nomeprelevatore1" id="nomeprelevatore1" size="50" value="<%=valoriScelti.get(z++)  %>"/>, 
nella sua qualita' di titolare/rappresentante/detentore della merce, hanno proceduto al prelievo di n. <input class="editField_fac" type="text"  size="4" name="numcampioni" id="numcampioni" value="<%=valoriScelti.get(z++) %>" /><br/> 
campioni di ALIMENTO (*): <br/>
<input type="radio" disabled id="dpa" name="dpa" value="001" <%= (PnaaDetails.getListaCampiPna().get("dpa").get("dpa").equalsIgnoreCase("001")) ? ("checked=\"checked\"") : ("")  %>/> per ANIMALI NON DESTINATI alla produzione di alimenti (non DPA)<br/>
<input type="radio" disabled id="dpa" name="dpa" value="002" <%= (PnaaDetails.getListaCampiPna().get("dpa").get("dpa").equalsIgnoreCase("002")) ? ("checked=\"checked\"") : ("")  %> /> per ANIMALI DESTINATI alla produzione di alimenti (DPA)<br/>
</P>

<!-- PARTE A -->
<h2>A.PARTE GENERALE</h2>
<table width="100%" cellpadding="2" cellspacing="2">
	 <col width="50%">
	<col width="50%"> 
<tr class="colorcell">
  <td colspan="4" style="border:1px solid black;"><b>A1. Strategia di campionamento</b></td>
</tr>
<tr>


<% String disabled2 =" disabled ";
if (PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("2"))
	disabled2=" ";
	String disabled3 = " disabled ";
if (PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("3"))
	disabled3 = " ";
String disabled7 = " disabled ";
if (PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("7"))
	disabled7 = " ";
String disabled5 = " disabled ";
if (PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("5"))
	disabled5 = " ";
String disabled8 = " disabled ";
if (PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("8"))
	disabled8 = " ";

	%>


 <td><input type="radio" disabled id="a1" name="a1" value="003" <%=(PnaaDetails.getA1() != null &&  PnaaDetails.getA1().equalsIgnoreCase("003")) ? ("checked=\"checked\"") : ("") %> > Monitoraggio</td> 
  <td><input type="radio" disabled id="a1" name="a1" value="001" <%=(PnaaDetails.getA1() != null &&  PnaaDetails.getA1().equalsIgnoreCase("001")) ? ("checked=\"checked\"") : ("") %> > Sospetto </td> </tr>
  <tr> <td><input type="radio" disabled id="a1" name="a1" value="007" <%=(PnaaDetails.getA1() != null &&  PnaaDetails.getA1().equalsIgnoreCase("007")) ? ("checked=\"checked\"") : ("") %> > Sorveglianza </td>
  <td><input type="radio" disabled  id="a1" name="a1" value="008" <%=(PnaaDetails.getA1() != null &&  PnaaDetails.getA1().equalsIgnoreCase("008")) ? ("checked=\"checked\"") : ("") %> > EXTRAPIANO: Sorveglianza</td> </tr>
 <tr>
  <td><input type="radio" disabled id="a1" name="a1" value="005" <%=(PnaaDetails.getA1() != null &&  PnaaDetails.getA1().equalsIgnoreCase("005")) ? ("checked=\"checked\"") : ("") %>>EXTRAPIANO: Monitoraggio</td>     
</tr>
<tr class="colorcell">
  <td colspan="4" style="border:1px solid black;"><b>A2. Metodo di campionamento (*):</b></td>
</tr>	
<tr>
<td><input type="radio" disabled  name="a2" id="a2" value="001" <%=(PnaaDetails.getListaCampiPna().get("a2") != null && PnaaDetails.getListaCampiPna().get("a2").get("a2").equalsIgnoreCase("001")) ? ("checked=\"checked\"") : ("") %> />Individuale/singolo</td>
 <td><input type="radio" disabled  name="a2" id="a2" value="020" <%=(PnaaDetails.getListaCampiPna().get("a2") != null &&  PnaaDetails.getListaCampiPna().get("a2").get("a2").equalsIgnoreCase("020")) ? ("checked=\"checked\"") : ("") %> />Norma di riferimento (solo se trattasi di una norma UE):<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IN ACCORDO AL REG 152/2009
</tr>
<tr><td><input type="radio" disabled  name="a2" id="a2" value="003"  <%=(PnaaDetails.getListaCampiPna().get("a2") != null &&  PnaaDetails.getListaCampiPna().get("a2").get("a2").equalsIgnoreCase("003")) ? ("checked=\"checked\"") : ("") %> />Sconosciuto</td>
<td><input type="radio" disabled  name="a2" id="a2" value="011"  <%=(PnaaDetails.getListaCampiPna().get("a2") != null &&  PnaaDetails.getListaCampiPna().get("a2").get("a2").equalsIgnoreCase("011")) ? ("checked=\"checked\"") : ("") %> />Altro metodo di campionamento</td>
</tr>
<tr class="colorcell">
  <td colspan="4" style="border:1px solid black;"><b>A3. Programma di controllo	 nell'ambito del Pnaa e accertamenti richiesti (*):</b></td>
</tr>
<tr>
  <td><input type="radio" disabled  readonly="readonly"  name="a3" id="a3" value="BSE" <%=(PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("1")) ? ("checked=\"checked\"") : ("") %> ><b> Costituenti di origine animale vietati (BSE)</b></td>
  <td><input type="radio" disabled  readonly="readonly"  name="a3" id="a3" value="PCB" <%=(PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("4")) ? ("checked=\"checked\"") : ("") %> ><b>Diossine e PCB</b></td>
  </tr>

<tr> 
  <td><input type="radio" disabled readonly="readonly"  name="a3" id="a3" value="PFA" <%=(PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("2")) ? ("checked=\"checked\"") : ("") %> ><b> Principi farmacologicamente attivi e additivi</b></td>
  <td><input type="radio" disabled  readonly="readonly"  name="a3" id="a3" value="MIC" <%=(PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("5")) ? ("checked=\"checked\"") : ("") %> ><b>Micotossine <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare 
  <input class="editField_fac" type="text"  <%=disabled5 %> size="30" name="microtossine" id="microtossine" value="<%=valoriScelti.get(z++) %>"/> ) </b>  </td>
</tr>

	
  <tr>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  <%=disabled2 %>  name="a3_1_1" id="a3_1_1" value="principi_farmacologicamente_attivi" onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>> 
  <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_1_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  Principi farmacologicamente attivi<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input class="editField_fac" type="text"  <%=disabled2 %> name="principi" id="principi" value="<%=valoriScelti.get(z++) %>" size="30" />)</td>
  
  <td><input type="radio" disabled readonly="readonly"  name="a3" id="a3" value="SAL" <%=(PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("6")) ? ("checked=\"checked\"") : ("") %> ><b>Salmonella</b></td></tr>
  </tr>
  
   <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" <%=disabled2 %>  name="a3_1_2" id="a3_1_2" value="cocciodiostatici/istomonostatici"  onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>> 
     <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_2_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
    cocciodiostatici/istomonostatici<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input class="editField_fac" type="text"  <%=disabled2 %> name="cocciodiostatici" id="cocciodiostatici" value="<%=valoriScelti.get(z++)%>" size="30"/>)</td>
  
  
   <td><input type="radio" disabled readonly="readonly"  name="a3" id="a3" value="OGM" <%=(PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("7")) ? ("checked=\"checked\"") : ("") %> ><b>OGM</b></td>
   </tr>
   
    <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  <%=disabled2 %> name="a3_1_3" id="a3_1_3" value="additivi_tecnologici" onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>> 
     <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_3_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
    additivi tecnologici <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare <input class="editField_fac" type="text"  <%=disabled2 %> name="tecnologici" id="tecnologici" value="<%=valoriScelti.get(z++)%>" size="30"/>)</td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" <%=disabled7 %>   name="a3_1_10" id="a3_1_10" value="ogm_qualitativo"  onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>>
     <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_10_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
     OGM qualitativo <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare <input class="editField_fac" type="text"  <%=disabled7 %>  size="30"  name="ogmqual" id="ogmqual" value="<%=valoriScelti.get(z++)%>"/>)</td>
    </tr>
 
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  <%=disabled2 %> name="a3_1_4" id="a3_1_4" value="additivi_organolettici" onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>> 
  <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_4_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  additivi organolettici</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField_fac"  <%=disabled2 %> name="organolettici" id="organolettici" value="<%=valoriScelti.get(z++)%>" size="30"/>)</td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" <%=disabled7 %>   name="a3_1_11" id="a3_1_11" value="ogm_quantitativo"  onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>> 
  <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_11_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  OGM quantitativo<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input class="editField_fac" type="text"  <%=disabled7 %>  size="30" name="ogmquant" id="ogmquant" value="<%=valoriScelti.get(z++)%>" />)</td>
 </tr>
 
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  <%=disabled2 %>  name="a3_1_5" id="a3_1_5" value="additivi_nutrizionali" onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>> 
  <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_5_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  additivi nutrizionali</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input class="editField_fac" type="text"  <%=disabled2 %>  name="nutrizionali" id="nutrizionali"  size="30" value="<%=valoriScelti.get(z++)%>" />)</td>
	<td><input type="radio" disabled  readonly="readonly"  name="a3" id="a3" value="ALT" <%=(PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("8")) ? ("checked=\"checked\"") : ("") %> ><b>Altro <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare <input class="editField_fac" type="text" <%=disabled8 %>  size="30" name="a3_altro" id="a3_altro" value="<%=valoriScelti.get(z++)%>"/></b></td>
 </tr>
 
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  <%=disabled2 %> name="a3_1_6" id="a3_1_6" value="additivi_zootecnici" onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %> > 
  <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_6_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  additivi zootecnici</br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare <input class="editField_fac" type="text" <%=disabled2 %>  name="zootecnici" id="zootecnici"  size="30" value="<%=valoriScelti.get(z++)%>"/>)</td>
  </tr>

 <tr><td><input type="radio" disabled  readonly="readonly"  name="a3" id="a3" value="CIA" <%=(PnaaDetails.getA3() != null && PnaaDetails.getA3().equalsIgnoreCase("3")) ? ("checked=\"checked\"") : ("") %> ><b>Contaminanti inorganici e composti azotati,<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;composti organoclorurati, radionuclidi</b></td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" <%=disabled3 %>   name="a3_1_7" id="a3_1_7" value="contaminanti_inorganici_e_composti_azotati" onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %> >
  <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_7_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  contaminanti inorganici e composti azotati<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare <input class="editField_fac" type="text"  <%=disabled3 %>  name="inorganici" id="inorganici"  value="<%=valoriScelti.get(z++)%>" size="30"/>)</td></tr>
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  <%=disabled3 %>   name="a3_1_8" id="a3_1_8" value="composti_organoclorurati" onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %> >
 <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_8_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
 composti organoclorurati<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input class="editField_fac" type="text" <%=disabled3 %> name="organoclorurati" id="organoclorurati"  value="<%=valoriScelti.get(z++)%>" size="30"/>)</td></tr>
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  <%=disabled3 %>   name="a3_1_9" id="a3_1_9" value="additivi_radionuclidi" onClick="gestisciCheckBox(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %> >
 <div style="display:none">
  <input class="editField_fac" type="text" id="a3_1_9_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  radionuclidi<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input class="editField_fac" type="text" <%=disabled3 %> name="radionuclidi" id="radionuclidi" value="<%=valoriScelti.get(z++)%>"  size="30"/>)</td></tr>
</table>
<table width="100%" cellpadding="2" cellspacing="2">
	 <col width="50%">
	<col width="50%"> 
 
<tr class="colorcell">
  <td  colspan="4" style="border:1px solid black;"><b>A4. Prelevatore (Nome e Cognome)(*):</b></td>
</tr>
<tr>
  <td colspan="4"> 
    <input class="layout" type="text" readonly name="a4" id="a4" size="130" value="<%= PnaaDetails.getA4() %>" />
   </td> 
</tr>

</table>

<table width="100%" cellpadding="2" cellspacing="2"  style="page-break-inside: avoid">
	 <col width="50%">
	<col width="50%"> 
<tr class="colorcell">
  <td colspan="4" style="border:1px solid black;"><b>A5. Luogo di prelievo (*):</b></td>
</tr>
<tr>
 <tr>
 	<td><input type="radio" disabled  name="a5" id="a5" value="67" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("067")) ? ("checked=\"checked\"") : ("") %>> Stabilimenti di miscelazione grassi</td>
 	<td><input type="radio"  disabled name="a5" id="a5" value="22" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("022")) ? ("checked=\"checked\"") : ("") %>> Stabilimento di produzione oli e grassi animali </td>
 </tr>
 <tr>
 	<td><input type="radio" disabled  name="a5" id="a5" value="50" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("050")) ? ("checked=\"checked\"") : ("") %>> Stabilimento di mangimi composti</td>
 	<td><input type="radio"  disabled name="a5" id="a5" value="52" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("052")) ? ("checked=\"checked\"") : ("") %>> Stabilimento di mangimi composti per animali da compagnia</td>
 </tr>
 <tr>
 	<td><input type="radio"  disabled name="a5" id="a5" value="42" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("042")) ? ("checked=\"checked\"") : ("") %>> Mezzo di trasporto su rotaia </td>
    <td><input type="radio"  disabled name="a5" id="a5" value="44" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("044")) ? ("checked=\"checked\"") : ("") %>> Mezzo di trasporto aereo </td>
 </tr>
 <tr>
    <td><input type="radio"  disabled name="a5" id="a5" value="41" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("041")) ? ("checked=\"checked\"") : ("") %>> Mezzo di trasporto su strada </td>
    <td><input type="radio" disabled   name="a5" id="a5" value="43" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("043")) ? ("checked=\"checked\"") : ("") %>> Mezzo di trasporto su acqua </td>
 </tr>
 <tr>
  <td><input type="radio"  disabled name="a5" id="a5" value="49" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("049")) ? ("checked=\"checked\"") : ("") %>> Deposito/Magazzinaggio </td>
  <td><input type="radio" disabled  name="a5" id="a5" value="55" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("055")) ? ("checked=\"checked\"") : ("") %>> Impianto che produce grassi vegetali per l'alimentazione animale </td>
 </tr>
 <tr>
 <td><input type="radio" disabled  name="a5" id="a5" value="56" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("056")) ? ("checked=\"checked\"") : ("") %>> Impianto oleochimico che produce materie prime per l'alimentazione animale </td>
 <td><input type="radio" disabled  name="a5" id="a5" value="47" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("047")) ? ("checked=\"checked\"") : ("") %>> Vendita al dettaglio </td> 
 </tr>
 <tr>
 <td><input type="radio"  disabled name="a5" id="a5" value="51" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("051")) ? ("checked=\"checked\"") : ("") %>> Stabilimento di produzione di additivi/premiscele</td>
 <td><input type="radio" disabled  name="a5" id="a5" value="68" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("068")) ? ("checked=\"checked\"") : ("") %>> Stabilimento per la produzione di BIODIESEL</td>
 </tr>
 <tr>  
 <td><input type="radio"  disabled name="a5" id="a5" value="48" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("048")) ? ("checked=\"checked\"") : ("") %>> Mulino per la produzione di mangimi semplici</td>
 <td><input type="radio"  disabled name="a5" id="a5" value="53" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("053")) ? ("checked=\"checked\"") : ("") %>> Vendita all'ingrosso/intermediario di mangimi </td>
 </tr>
 <tr>
    <td><input type="radio"  disabled name="a5" id="a5" value="2" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("002")) ? ("checked=\"checked\"") : ("") %>> Azienda agricola</td>
    <td><input type="radio" disabled  name="a5" id="a5" value="57" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("027")) ? ("checked=\"checked\"") : ("") %>> Azienda zootecnica con ruminanti</td>
 </tr>
 <tr>
 	<td><input type="radio" disabled  name="a5" id="a5" value="58" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("058")) ? ("checked=\"checked\"") : ("") %>> Azienda zootecnica che non detiene ruminanti</td>
    <td><input type="radio"  disabled name="a5" id="a5" value="59" <%=(PnaaDetails.getListaCampiPna().get("a5") != null && PnaaDetails.getListaCampiPna().get("a5").get("a5").equalsIgnoreCase("059")) ? ("checked=\"checked\"") : ("") %>> Attivita' di importazione (Primo deposito di materie prime importate) </td>
 </tr>
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>A6. Codice identificativo luogo di prelievo (*):</b>&nbsp;&nbsp;&nbsp;</td>
  <td colspan="3" style="border:1px solid black;"><b>A7. Targa mezzo di trasporto:</b></td>
</tr>
<tr>
  <td><input class="layout" readonly="readonly"  type="text"  name="a6" id="a6" size="30" value="<%= PnaaDetails.getListaCampiPna().get("a6").get("a6")  %>"/></td>

   <td><input class="editField_fac" type="text" name="a7" id="a7" size="30" value="<%= valoriScelti.get(z++) %>" /></td>

</tr>
<tr class="colorcell">
  <td style="border:1px solid black;"><b>A8. Indirizzo del luogo di prelievo (*):</b></td>
  <td style="border:1px solid black;"><b>A9. Comune (*):</b></td>
  <td style="border:1px solid black;"><b>A10. Provincia (*):</b></td>
</tr>
<tr>
  <td>
  	<input class="layout" type="text" size="40" readonly value="<%= PnaaDetails.getA8() %>" name="a8" id="a8" /> 
  </td>
   <td>
   		<input class="layout" type="text" size="30" readonly name="a9" id="a9" value="<%= PnaaDetails.getA9() %>"/> 
   	</td>
   <td>
   		<input class="layout" type="text" size="10" readonly name="a10" id="a10" value="<%= PnaaDetails.getA10() %>"/>
   </td>
</tr>
<tr class="colorcell">
  <td colspan="4" style="border:1px solid black;"><b>A11.  Localizzazione geografica del punto di prelievo (WGS84-Formato decimale) (*):</b></td>
</tr>
<tr>
  <td>Latitudine: <input class="layout" type="text" readonly="readonly" name="a11_1" id="a11_1" size="10" value="<%= PnaaDetails.getA11_1() %>"/></td>
  <td>Longitudine: <input class="layout" type="text"  readonly="readonly" name="a11_2" id="a11_2" size="10" value="<%= PnaaDetails.getA11_2() %>"/></td> 
</tr>
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>A12. Ragione sociale/Proprietario di animali (*):</b></td>
  <td colspan="3" style="border:1px solid black;"><b>A12.b. Denominazione Allevamento (*):</b></td>
</tr>
<tr>
  <td>
  <input class="layout" type="text" readonly="readonly" size="50" name="a12" id="a12" value="<%= PnaaDetails.getA12() %>" />
  </td>
  <td>
   <input class="layout" type="text" size="30" readonly="readonly" name="a12b" id="a12b" value="<%=PnaaDetails.getA12b()%>" />
  </td> 
</tr>
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>A13. Rappresentante legale (*):</b></td>
<td colspan="3" style="border:1px solid black;"><b>A14. Codice fiscale/Proprietario animali (*):</b></td>

</tr>
<tr>
 <td> <input class="layout" type="text" readonly="readonly" size="50" name="a13" id="a13" value="<%= PnaaDetails.getA13() %>" /></td> 
  <td>
  <input class="layout" type="text" readonly="readonly" size="30" name="a14" id="a14" value="<%= (PnaaDetails.getA14() != null && !PnaaDetails.getA14().equals("null")) ? PnaaDetails.getA14() : "" %>" />
  	</td>
  
</tr>
<tr class="colorcell">
      <td style="border:1px solid black;"><b>A15. Detentore/Responsabile sede produttiva (*):</b></td>
  <td style="border:1px solid black;"><b>A15.b: C.F.Ragione Sociale/C.F.Detentore (*):</b></td>
  <td style="border:1px solid black;"><b>A16. Telefono (*):</b></td>

</tr>
<tr>

<td><input class="layout" type="text" size="30" readonly="readonly" name="a15" id="a15" value="<%= (PnaaDetails.getA15()!=null) ? PnaaDetails.getA15() : "" %>" /></td>
<td><input class="layout" type="text" size="30" readonly="readonly" name="a15b" id="a15b" value="<%= (PnaaDetails.getA15b()!=null) ? PnaaDetails.getA15b() : "" %>"/></td> 
   <td><input class="layout" type="text" size="30" readonly="readonly"  name="a16" id="a16" value="<%= (PnaaDetails.getA16()!=null) ? PnaaDetails.getA16() : "" %>"/></td> 
   
</tr>
</table>
<br/>
Note per la compilazione del verbale:<br/>
1) In caso di allevamenti zootecnici i campi A12, A13 e A14 sono relativi al proprietario degli animali<br>
2) In caso di allevamenti zootecnici i campi A12.b e A15 si riferiscono al detentore o soccidario degli animali<br> 


<% ArrayList<SpecieAnimali> listaSpeciePnaa =  PnaaDetails.getListaSpecieAnimali();
   ArrayList<String> listaProdottiPnaa =  PnaaDetails.getListaProdottiPnaa();
%>
<!-- PARTE B-->
<h2>B. INFORMAZIONI SUL CAMPIONE PRELEVATO</h2>
<table width="100%" cellpadding="2" cellspacing="2">
<col width="50%">
	<col width="50%"> 
	<tr class="colorcell"> 
		<td colspan="4" style="border:1px solid black;"><b>B1. Matrice del campione (*):</b></td> 
	</tr>
  <tr>
  
  <%String disabledm3 = " disabled ";
if (PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m3"))
	disabledm3 = " ";
String disabledm4 = " disabled ";
if (PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m4"))
	disabledm4 = " ";	
String disabledm5 = " disabled ";
if (PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m5"))
	disabledm5 = " ";	
String disabledm6 = " disabled ";
if (PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m6"))
	disabledm6 = " ";	

	%>
	

	<td><input type="radio" disabled readonly="readonly"  name="b1" id="b1"  value="m1" <%=(PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m1")) ? ("checked=\"checked\"") : ("") %> > <b>Materia prima/mangime semplice<br>(matrice da catalogo REG.68/2013)</b><br/>
	&nbsp;&nbsp;&nbsp;
	<input class="layout" type="text" readonly name="materia_prima" id="materia_prima" size="60" value="<%= (PnaaDetails.getListaCampiPna().get("materia_prima")!=null) ? PnaaDetails.getListaCampiPna().get("materia_prima").get("materia_prima") : "" %>" /></td>
	<input class="" type="hidden" name="codice_materia_prima" id="codice_materia_prima" size="60" value="<%= PnaaDetails.getCodice_materia_prima() %>" /></td>
	
	<td><input type="radio" disabled readonly="readonly"  name="b1" id="b1" value="m5" <%=(PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m5")) ? ("checked=\"checked\"") : ("") %> ><b>Additivo per mangimi</b></td>
</tr>

<tr><td><input type="radio" disabled readonly="readonly"  name="b1" id="b1"  value="m2" <%=(PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m2")) ? ("checked=\"checked\"") : ("") %>  > <b>Mangime composto</b></td>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm5 %>  name="b1_5" id="b1_5_1" value="m5_1" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> 
   <div style="display:none">
  <input class="editField_fac" type="text" name="b1_5_testo" id="b1_5_1_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  Additivo tecnologico (specificare) <input class="editField_fac" type="text" <%=disabledm5 %> name="" id="" size="30" value="<%=valoriScelti.get(z++)%>"/></td> 
</tr> 

<tr>
   <td><input type="radio" disabled readonly="readonly"  name="b1" id="b1" value="m3" <%=(PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m3")) ? ("checked=\"checked\"") : ("") %>> <b>Mangime completo</b></td>
   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm5 %>  name="b1_5" id="b1_5_2" value="m5_2" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> 
      <div style="display:none">
  <input class="editField_fac" type="text" name="b1_5_testo" id="b1_5_2_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>Additivo nutrizionale (specificare) 
<input class="editField_fac" type="text" <%=disabledm5 %> name="" size="30" value="<%=valoriScelti.get(z++) %>" id="" /></td>
  </tr>
  <tr>
   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm3 %>  name="b2_2" id="b2_2_1" value="m3_1" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> <i>d'allattamento</i>
    <div style="display:none">
  <input class="editField_fac" type="text" name="b2_2_testo" id="b2_2_1_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  </td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"   <%=disabledm5 %> name="b1_5" id="b1_5_3"  value="m5_3" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> 
       <div style="display:none">
  <input class="editField_fac" type="text" name="b1_5_testo" id="b1_5_3_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  Additivo organolettico (specificare) <input class="editField_fac" type="text" <%=disabledm5 %> name="" value="<%=valoriScelti.get(z++) %>" id="" size="30" /></td> 
  </tr>
  <tr>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm3 %> name="b2_2" id="b2_2_3" value="m3_2" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> <i> medicato </i>
    <div style="display:none">
  <input class="editField_fac" type="text" name="b2_2_testo" id="b2_2_3_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  </td>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"   <%=disabledm5 %>  name="b1_5" id="b1_5_4" value="m5_4" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/>
     <div style="display:none">
  <input class="editField_fac" type="text" name="b1_5_testo" id="b1_5_4_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
   Additivo zootecnico (specificare) <input class="editField_fac" type="text" <%=disabledm5 %> name="" value="<%=valoriScelti.get(z++) %>" id="" size="30" /></td>
  </tr>
  <tr>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm3 %>  name="b2_2" id="b2_2_4" value="m3_3" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> <i> con coccidiostatici/istomonostatici </i>
    <div style="display:none">
  <input class="editField_fac" type="text" name="b2_2_testo" id="b2_2_4_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  </td>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"   <%=disabledm5 %>  name="b1_5" id="b1_5_5" value="m5_5" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> 
        <div style="display:none">
  <input class="editField_fac" type="text" name="b1_5_testo" id="b1_5_5_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  Coccidiostatico/istomonostaico 
    <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare)<input class="editField_fac" type="text" <%=disabledm5 %> name="" value="<%=valoriScelti.get(z++) %>" id="" size="30" /></td>
  </tr>
 <tr>
 <td><input type="radio" disabled readonly="readonly"  name="b1" id="b1" value="m4" <%=(PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m4")) ? ("checked=\"checked\"") : ("") %> > <b>Mangime complementare</b></td>
 <td><input type="radio" disabled readonly="readonly"  name="b1" id="b1" value="m6" <%=(PnaaDetails.getB7() != null && PnaaDetails.getB7().equalsIgnoreCase("m6")) ? ("checked=\"checked\"") : ("") %>  ><b>Premiscela di additivi (indicare le categorie di additivi che costituiscono la premiscela):</b></td>
 </tr> 
     <tr>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm4 %>  name="b1_4_1" id="b1_4_1_1" value="m4_1"  onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> <i>d'allattamento</i>
  
  <div style="display:none">
  <input class="editField_fac" type="text" name="b1_4_1_testo" id="b1_4_1_1_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
     
    </td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm6 %>  name="b1_6" id="b1_6_1" value="m6_1" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> Additivo tecnologico
       <div style="display:none">
  <input class="editField_fac" type="text" name="b1_6_testo" id="b1_6_1_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  </td>
    </tr>
 <tr>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm4 %>  name="b1_4_1" id="b1_4_1_2" value="m4_2" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> <i> medicato </i>
 <div style="display:none">
  <input class="editField_fac" type="text" name="b1_4_1_testo" id="b1_4_1_2_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  
 </td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm6 %>  name="b1_6" id="b1_6_2" value="m6_2" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> Additivo nutrizionale
        <div style="display:none">
  <input class="editField_fac" type="text" name="b1_6_testo" id="b1_6_2_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
 </td> 
 </tr>    
   <tr>
   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" <%=disabledm4 %>  name="b1_4_1" id="b1_4_1_3" value="m4_3" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> <i> con coccidiostatici/istomonostatici </i>
   <div style="display:none">
  <input class="editField_fac" type="text" name="b1_4_1_testo" id="b1_4_1_3_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
     
   </td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm6 %>  name="b1_6" id="b1_6_3" value="m6_3" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> Additivo organolettico
           <div style="display:none">
  <input class="editField_fac" type="text" name="b1_6_testo" id="b1_6_3_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  </td> 
 </tr> 
 <tr>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm4 %>  name="b1_4_1" id="b1_4_1_4" value="m4_4" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> <i> minerale </i>
 
 <div style="display:none">
  <input class="editField_fac" type="text" name="b1_4_1_testo" id="b1_4_1_4_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
     
     </td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm6 %> name="b1_6" id="b1_6_4" value="m6_4" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> Additivo zootecnico
        <div style="display:none">
  <input class="editField_fac" type="text" name="b1_6_testo" id="b1_6_4_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  </td>
 </tr> 
 <tr>
 <td></td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  <%=disabledm6 %>  name="b1_6" id="b1_6_5" value="m6_5" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> Coccidiostatico/istomonostaico
        <div style="display:none">
  <input class="editField_fac" type="text" name="b1_6_testo" id="b1_6_5_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  </td>
 </tr>  

   <tr><td><input type="radio" disabled readonly="readonly"  name="b1" id="b1" value="m7" <%=(PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m7")) ? ("checked=\"checked\"") : ("") %> > <b>Acqua di abbeverata</b></td>
   <td><input type="radio" disabled readonly="readonly"  name="b1" id="b1" value="m8" <%=(PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m8")) ? ("checked=\"checked\"") : ("") %> > <b>Articoli da masticare</b></td>
   </tr>
   <tr><td><input type="radio" disabled readonly="readonly"  name="b1" id="b1" value="m9" <%=(PnaaDetails.getListaCampiPna().get("b1") != null && PnaaDetails.getListaCampiPna().get("b1").get("b1").equalsIgnoreCase("m9")) ? ("checked=\"checked\"") : ("") %> > <b>Prodotto intermendio</b></td>
   </tr>
  <tr>
  </tr>  
  
  </table>
    
   <table width="100%" cellpadding="2" cellspacing="2" style="collapse;page-break-inside: avoid">
<col width="50%">
	<col width="50%"> 
	
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>B2. Trattamento applicato al mangime prelevato (*):</b></td>
  <td colspan="3" style="border:1px solid black;"><b>B3. Confezionamento: </b></td>
</tr>
<tr>
      <td><input class="layout" readonly="readonly"  type="text" size="30" name="b2" id="b2" value="<%=  PnaaDetails.getListaCampiPna().get("b2").get("b2") %>" /></td>

      <td><input class="editField_fac" type="text" size="30"  name="b3" id="b3" value="<%=valoriScelti.get(z++) %>"/></td> 
	
</tr>
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>B4. Ragione sociale ditta produttrice (*):</b></td>
  <td colspan="3" style="border:1px solid black;"><b>B5. Indirizzo ditta produttrice (*):</b></td>
</tr>
<tr>
 <td><input class="layout" readonly="readonly"  type="text" size="30" name="b4" id="b4" value="<%= PnaaDetails.getListaCampiPna().get("b4").get("b4") %>"/></td>

 <td><input class="layout" readonly="readonly"  type="text" size="30" name="b5" id="b5" value="<%= PnaaDetails.getListaCampiPna().get("b5").get("b5") %>"/></td>

</tr>
</table>

 <table width="100%" cellpadding="2" cellspacing="2" style="page-break-inside:avoid">
<col width="50%">
	<col width="50%"> 
<tr class="colorcell">
  <td colspan="4" style="border:1px solid black;"><b>B6. Specie e categoria animale a cui l'alimento e' destinato (*):</b></td>
</tr>
</table></td></tr>

  <td colspan="4"><table width="100%" cellpadding="2" cellspacing="2">
<col width="25%">
	<col width="25%"><col width="25%"><col width="25%"> 
	
    <% int count = -1; %>
		  <%
 	  		 Iterator itMc = SpecieCategoria.iterator();
		        while (itMc.hasNext() ){
		     	   LookupElement el = (LookupElement)itMc.next();
		     	   count++;
		     	
		    %>
		    
		    <%if(count == 0) { %>
		    <tr>
		    <% } %>
		    <td>  
		     	  <input type="checkbox" disabled id="<%=el.getCode() %>" name="<%=el.getCode() %>" value="<%=el.getCode() %>" <%=(PnaaDetails.getListaCampiPna().get("b6").containsKey("b6."+el.getCode())) ? ("checked=\"checked\"") : "" %>  ><%=el.getDescription() %>
		    </td>
		     <%if(count == 3) { %>
		    </tr>
		    <%
		    count=-1;
		     }
		    
		    	}//chiudo while	
 	  	   
		    %>

</td></table>
 
<table width="100%" cellpadding="2" cellspacing="2">
<col width="50%">
	<col width="50%"> 
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>B7. Metodo di produzione (*):</b></td>
  <td colspan="3" style="border:1px solid black;"><b>B8. Nome commerciale del mangime (*):</b></td>
</tr>
<tr> <td><input type="radio" disabled id="b7" name="b7" value="0" <%=(PnaaDetails.getListaCampiPna().get("b7") != null && PnaaDetails.getListaCampiPna().get("b7").get("b7").equalsIgnoreCase("biologico")) ? ("checked=\"checked\"") : ("") %> >Biologico</td> 
<td><label class="layout"> <%= PnaaDetails.getListaCampiPna().get("b8").get("b8") %></label> </td></tr>

<tr>  <td><input type="radio" disabled id="b7" name="b7" value="1" <%=(PnaaDetails.getListaCampiPna().get("b7") != null && PnaaDetails.getListaCampiPna().get("b7").get("b7").equalsIgnoreCase("convenzionale")) ? ("checked=\"checked\"") : ("") %> >Convezionale</td></tr>
 <tr> <td><input type="radio" disabled id="b7" name="b7" value="2" <%=(PnaaDetails.getListaCampiPna().get("b7") != null && PnaaDetails.getListaCampiPna().get("b7").get("b7").equalsIgnoreCase("sconosciuto")) ? ("checked=\"checked\"") : ("") %> >Sconosciuto (no per OGM)</td></tr>

 <td colspan="8">
 <table>
<tr class="colorcell">
  <td style="border:1px solid black;"><b>B9. Stato del prodotto al momento del prelievo (*):</b></td>
</tr>
	
 <% int count2 = -1; %>
		 <%
		 
		 
			   
			   Iterator itP = StatoProdotti.iterator();
	   			while (itP.hasNext() ){
	     	  	 LookupElement el2 = (LookupElement)itP.next();
	     	   	count2++;
	     	 
	    %>
	    
	    <%if(count2 == 0) { %>
	    <tr>
	    <% } %>
	    <td>
	     	  <input type="checkbox" disabled id="<%=el2.getCode() %>" name="<%=el2.getCode() %>" value="<%=el2.getCode() %>" <%=(PnaaDetails.getListaCampiPna().get("b9").containsKey("b9."+el2.getCode())) ? ("checked=\"checked\"") : "" %>  ><%=el2.getDescription() %>
	     	
	    </td>
	     <%if(count2 == 2) { %>
	    </tr>
	    <%
	    count2=-1;
	     }
	    
	     	}//chiudo while
		   
		    %>
</td>
</table>

<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>B10. Ragione sociale responsabile etichettatura </b></td>
  <td colspan="3" style="border:1px solid black;"><b>B11. Indirizzo responsabile etichettatura:</b></td>
</tr>
<tr>
  <td><input class="editField_fac" type="text" size="30"  name="b10" id="b10" value="<%=  valoriScelti.get(z++)  %>"/></td>

  <td><input class="editField_fac" type="text" size="30"  name="b11" id="b11" value="<%= valoriScelti.get(z++) %>"/></td>

</tr><br/><br/><br/>
<tr class="colorcell">
  <td style="border:1px solid black;"><b>B12. Paese di produzione (*):</b></td>
  <td style="border:1px solid black;"><b>B13. Data di produzione:</b></td>
  <td style="border:1px solid black;"><b>B14. Data di scadenza (*):</b></td>
</tr>
<tr>
  <td><input class="layout" readonly="readonly"  type="text" size="30" name="b12" id="b12" value="<%=  PnaaDetails.getListaCampiPna().get("b12").get("b12") %>" /></td>
  <td><input class="editField_fac" type="text" size="30" name="b13" id="b13" value="<%= valoriScelti.get(z++) %>"/></td>
  <td><input class="layout" readonly="readonly"  type="text" size="30" name="b14" id="b14" value="<%=   PnaaDetails.getListaCampiPna().get("b14").get("b14") %>"/></td>
</tr>
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>B15. Numero di lotto:</b></td>
  <td colspan="3" style="border:1px solid black;"><b>B16. Dimensione di lotto (*):</b></td>
</tr>
<tr>
  <td><input class="editField_fac" type="text" size="30"  name="b15" id="b15" value="<%= valoriScelti.get(z++)  %>"/></td>
   <td><input class="layout" readonly="readonly"  type="text" size=30" name="b16" id="b16" value="<%=   PnaaDetails.getListaCampiPna().get("b16").get("b16") %>"/></td>
  
</tr>
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>B17. Ingredienti (*):</b></td>
  <td colspan="3" style="border:1px solid black;"><b>B18. Ulteriori commenti relativi al mangime prelevato:</b></td>
</tr>
<tr>
  <td><input class="layout" readonly="readonly"  type="text" size="50" name="b17" id="b17" value="<%=  PnaaDetails.getListaCampiPna().get("b17").get("b17") %>"/></td>

   <td><input class="editField_fac" type="text" size="30" name="b18" id="b18" value="<%=valoriScelti.get(z++) %>"/></td> 
</tr>
</table>

<br/>
<%@ include file="/campioni/moduli_html/footer.jsp" %>

<div style="page-break-before:always"> 
<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/header_pnaa.jsp" %>
<!-- FINE HEADER -->

<!-- PARTE C-->
<h2>C. LABORATORIO </h2>
<table width="100%" cellpadding="2" cellspacing="2">
	
<tr class="colorcell">
  <td style="border:1px solid black;"><b>C1. Laboratorio di destinazione del campione (*):</b>
</tr>
<tr>
  <td>Specificare <input class="editField_fac" type="text" size="30" id="lab_destinazione" name="lab_destinazione" value="<%=valoriScelti.get(z++) %>"></td>
</tr>
<tr class="colorcell">
  <td style="border:1px solid black;"><b>D. ULTERIORI INFORMAZIONI RELATIVE AL CAMPIONAMENTO: </b></td>
</tr>
<tr><td>
 Si allega il cartellino (*) o la sua fotocopia o il documento commerciale: 
 
  <input type="radio" id="allega1" name="allega" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> SI 
  <div style="display:none">
  <input class="editField_fac" type="text" name="allega_testo" id="allega1_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
 
   <input type="radio" id="allega2" name="allega" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> NO 
  <div style="display:none">
  <input class="editField_fac" type="text" name="allega_testo" id="allega2_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  
   <br>
 <p align="right">(*) sempre obbligatorio per ricerca OGM</p>
   Con le modalita' riportate nell'allegato Verbale delle Operazioni di Prelievo Campioni Effettuate (VOPE) atte a
   garantire la rappresentativita' e l'assenza di contaminazioni, utilizzando attrezzature e contenitori puliti, asciutti e <br>
   di materiale inerte sono stati prelevati a caso da n. <input class="editField_fac" type="text" size="4" name="numpunti" id="numpunti" value="<%=valoriScelti.get(z++) %>"> punti/sacchi n. <input class="editField_fac" type="text" size="4" name="mumcampioni" id="numcampioni" value="<%=valoriScelti.get(z++) %>"> campioni elementari del 
   peso/volume di <input class="editField_fac" type="text" size="4" name="peso" id="peso" value="<%=valoriScelti.get(z++) %>"> kg/lt.<br>
   Dall'unione dei campioni elementari e' stato formato il campione globale del peso/volume di <input class="editField_fac" type="text" size="4" name="pesocampione" id="pesocampione" value="<%=valoriScelti.get(z++) %>"> kg/lt.<br>
 
   <input type="radio" id="unione1" name="unione" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> Dopo opportuna miscelazione 
  <div style="display:none">
  <input class="editField_fac" type="text" name="unione_testo" id="unione1_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  <br/>
    <input type="radio" id="unione2" name="unione" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> Dopo opportuna macinazione 
  <div style="display:none">
  <input class="editField_fac" type="text" name="unione_testo" id="unione2_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  <br/>
   <br/>
   
   
    <input type="radio" id="prelievo1" name="prelievo" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> 
    <div style="display:none">
  <input class="editField_fac" type="text" name="prelievo_testo" id="prelievo1_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  e' stato ridotto a CG del peso/volume di kg/lt <input class="editField_fac" type="text" size="4" name="cgpeso" id="cgpeso" value="<%=valoriScelti.get(z++) %>"> e dal CG sono stati ottenuti n. <input class="editField_fac" type="text" size="4" name="cgcampioni" id="cgcampioni"value="<%=valoriScelti.get(z++) %>"> campioni 
   finali (campione di laboratorio) ognuno dei quali del peso/volume non inferiore a 500g/500ml, ogni
   campione finale viene sigillato e identificato con apposito cartellino.
  
    <br>
   <input type="radio" id="prelievo2" name="prelievo" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/> e' stato sigillato e identificato con apposito cartellino e inviato per la successiva macinazione.
  <div style="display:none">
  <input class="editField_fac" type="text" name="prelievo_testo" id="prelievo2_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  
  <br/>
 <input class="editField_fac" type="text" size="180" name="dich_proprietario" value="<%=valoriScelti.get(z++) %>"><br/>
 <input class="editField_fac" type="text" size="180" name="dich_proprietario2" value="<%=valoriScelti.get(z++) %>"><br/>
 <input class="editField_fac" type="text" size="180" name="dich_proprietario3" value="<%=valoriScelti.get(z++) %>"><br/>
<br/>
   N. <input class="editField_fac" type="text" size="4" name="dich_numcampioni" id="dich_numcampioni" value="<%=valoriScelti.get(z++) %>"> Campioni finali unitamente a n. <input class="editField_fac" type="text" size="4" name="dich_numcopie" id="dich_numcopie" value="<%=valoriScelti.get(z++) %>"> copie del presente verbale vengono inviate al 
  <input class="editField_fac" type="text" name="dich_invio" id="dich_invio" size="30" value="<%=valoriScelti.get(z++) %>">  in data <input class="editField_fac" type="text" size="30" name="dich_data" id="dich_data" value="<%=valoriScelti.get(z++) %>" ><br>
   Conservazione del campione <input class="editField_fac" type="text" size="100" name="dich_conservazione" id="dich_conservazione" value="<%=valoriScelti.get(z++) %>"><br>
   N. <input class="editField_fac" type="text" size="4" name="dich_numcopie" id="dich_numcopie" value="<%=valoriScelti.get(z++) %>"> copia/e del presente verbale con n. <input class="editField_fac" type="text"  size="4" name="dich_numcampionifinali" id="dich_numcampionifinali" value="<%=valoriScelti.get(z++) %>"> campioni finale/i viene/vengono consegnate al Sig. 
   <input class="editField_fac" type="text"   name="dich_consegnate" id="dich_consegnate" size="50" value="<%=valoriScelti.get(z++) %>"> il quale custodisce:<br/>
  
  <input type="radio" id="custodia1" name="custodia" onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/>  
  <div style="display:none">
  <input class="editField_fac" type="text" name="custodia_testo" id="custodia1_testo"  value="<%= valoriScelti.get(z++)  %>" />
  </div>
  un campione finale per conto del produttore<br/>
   <input type="radio" id="custodia2" name="custodia"  onClick="gestisciRadioButton(this)" <%if (valoriScelti.get(z).equals("X")){ %>checked="checked" <%} %>/>  
   <div style="display:none">
   <input class="editField_fac" type="text" size="30"  name="custodia_testo" id="custodia2_testo" value="<%= valoriScelti.get(z++)  %>"/>
   </div>
   un campione finale per conto proprio<br/>
   <br>
   <br>
   La partita/lotto relativa al campione prelevato viene/o non viene posta in sequestro fino all'esito dell'esame.
   Fatto, letto e sottoscritto.
   <br><br/>
  
  FIRMA DEL PROPRIETARIO/DETENTORE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  VERBALIZZANTI
   </td>
</tr>
</table>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>