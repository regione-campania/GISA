<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,java.io.IOException,org.aspcfs.modules.opu.base.*,java.util.Date,com.sun.org.apache.xerces.internal.impl.dv.util.Base64,java.io.ByteArrayOutputStream,javax.imageio.ImageIO"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>

<link rel="stylesheet" type="text/css" media="screen"
	href="anagrafe_animali/documenti/screen.css">
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css"
	type="text/css" media="print" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="microchips" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="detentoreCanile" scope="request" class="org.aspcfs.modules.opu.base.Operatore"/>
<jsp:useBean id="leish" class="java.lang.String" scope="request"/>	

<!-- LOOKUPS DECODIFICA -->
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="dati_registrazione" class="org.aspcfs.modules.opu.base.Operatore"
	scope="request" />	
<script>function catturaHtml(form){
	//popolaCampi();
	h=document.getElementsByTagName('html')[0].innerHTML;
	form.html.value = h;
	}</script>

<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>
<body>
<!-- input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" / -->
		 
	<form name="generaPDF" action="GestioneDocumenti.do?command=GeneraPDF" method="POST">
<input type="button" id ="generapdf" class="buttonClass"  value="Genera PDF" 	
onClick="this.disabled=true; this.value='GENERAZIONE PDF IN CORSO'; catturaHtml(this.form); this.form.submit()" />
<input type="hidden" name="html" id="html" value=""></input>
<input type="hidden" name="tipo" id="tipo" value="PrintRichiestaCampioniCanile"></input>
<input type="hidden" name="idLinea" id="idLinea" value="<%=request.getParameter("lineaId") %>"></input>
</form>	 

<!-- script type="text/javascript">
setTimeout('Redirect()',1000);
function Redirect()
{
	catturaHtml(document.forms[0]);
generaPDF.submit();
}
</script-->
<div class="boxIdDocumento"></div>
 <div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
<br/><br/><br/><br/><br/><br/>	 

<div class="Section1">
<div class="titleizsm" style="font-weight: bold;">Scheda per l'invio di campioni all'IZS del Mezzogiorno</div>

<% if (leish!=null && leish.equals("true")){ %>
<center><b>PIANO DI SORVEGLIANZA E CONTROLLO DELLA LEISHMANIOSI IN REGIONE CAMPANIA</b></center>
<%} %>

<div class="box1">
<div class="leftSquare">
<span class="nodott_campioni">Richiedente:</span>
<span class="dott_campioni">&nbsp;</span>
<span class="dott2_campioni">&nbsp;</span>
<div class="clear1"></div>
<span class="nodott_campioni">Codice SIGLA:</span>
<span class="dott_campioni">&nbsp;</span>
<span class="dott2_campioni">&nbsp;</span>
<div class="clear1"></div>
</br></br>
<span class="nodott_campioni">Indirizzo:</span>
<span class="dott_campioni">&nbsp;</span>
<div class="clear1"></div>

<span class="nodott_campioni">Telefono:</span>
<span class="dott_campioni">&nbsp;</span>
<div class="clear1"></div>

<span class="nodott_campioni">Fax:</span>
<span class="dott_campioni">&nbsp;</span>
<div class="clear1"></div>

<span class="nodott_campioni">Email:</span>
<span class="dott_campioni">&nbsp;</span>
<div class="clear1"></div>


</div>
<div class="rightSquare">
<div class="nodott_campioni">Materiale inviato:</div>
<br>
<ul class="esami">
<li>Sangue &nbsp;</li>
<li>Siero &nbsp;</li>
<li>Biopsia:linfonodo &nbsp;</li>
<li>Midollo &nbsp;</li>
<li>Raschiato cutaneo &nbsp;</li>
</ul>
<div class="clear1"></div>
</br>
<div class="nodott_campioni">Esami richiesti per<br> Leishmania:</div>
<br>
<ul class="esami">
<li>Sierologico &nbsp;</li>
<li>Isolamento colturale &nbsp;</li>
<li>Esame citologico &nbsp;</li>
<li>PCR real-time &nbsp;</li>
</ul>
</div>

</div>

</div>

<div class="box2">
<div class="nodott_campioni">Altri esami richiesti:</div>
<br>
<div style="float: left;">
<ul class="altriesami">
<li><div>&nbsp;</div> </li>
<li><div>&nbsp;</div> </li>
<li><div>&nbsp;</div> </li>
</ul>

<div class="clear1"></div>

<ul class="altriesami">
<li><div>&nbsp;</div> </li>
<li><div>&nbsp;</div> </li>
<li><div>&nbsp;</div> </li>
</ul>
</div>
</div>

<div class="box3">
<div class="nodott_margin_low">Canile </div>
<div class="dott_long_margin_low">&nbsp;<%=detentoreCanile.getRagioneSociale()%></div>
<div class="clear1"></div>
<%Stabilimento stab = (Stabilimento)detentoreCanile.getListaStabilimenti().get(0); %>
<%--div class="nodott_margin_low">nato a </div>
<div class="dott_margin_low">&nbsp;  <%=(proprietario.getRappLegale() != null) ? proprietario.getRappLegale().getComuneNascita() : ""%> </div>
<div class="nodott_margin_low">il </div>
<div class="dott_margin_low">&nbsp; <%=(proprietario.getRappLegale() != null) ? toDateasString(proprietario.getRappLegale().getDataNascita()) : ""%> </div>
<div class="clear1"></div--%>

<div class="nodott_margin_low" >sito in via</div>
<div class="dott_margin_low" >&nbsp; <%=(stab != null) ? (stab).getSedeOperativa().getVia() : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >comune</div>
<div class="dott_margin_low" >&nbsp;  <%=(stab != null) ? comuniList.getSelectedValue(stab.getSedeOperativa().getComune()) : ""%></div>
<div class="clear1"></div>


<div class="nodott_margin_low" >ASL</div>
<div class="dott_margin_low" >&nbsp;  <%=(stab != null) ? aslList.getSelectedValue(stab.getIdAsl()) : ""%></div>
<div class="clear1"></div>
</br></br></br>

<%--div class="nodott_margin_low" >Codice identificativo</div>
<div style="border: 0px solid black; margin-left: 20px; float: left; font-size:14px;"><img src="<%=createBarcodeImage(thisAnimale.getMicrochip())%>" /></div>
<div class="clear1"></div--%>


</div>



<ul class="attitudine">
<li>&nbsp;  Primo esame</li>
<li>&nbsp;  Controllo successivo</li>
</ul>

</br>
<div class="clear1"></div>
<ul class="esitoprecedente">
<li>Esito precedente esame: &nbsp; &nbsp;</li>
<li>negativo &nbsp; &nbsp;</li>
<li>positivo &nbsp;</li>
<li>(tit............)&nbsp;&nbsp; </li>
<li>reattivo &nbsp;</li>
<li>(tit............) &nbsp; &nbsp;</li>
</ul>

<div class="clear1"></div>
<ul class="esitoprecedente" style="margin-bottom: 0px;">
<li>Somministrazione vaccino anti-Leishmania: &nbsp; &nbsp;</li>
<li>No &nbsp; &nbsp;</li>
<li>Si &nbsp;</li>
</ul>
<div class="clear1"></div>
<div class="nodott" style="font-size:14px; margin-top: 0px;padding-left: 6px;" >Data ultima somministrazione</div>
<div class="dott" style="font-size:14px; margin-top: 0px;padding-left: 6px;" >&nbsp; </div>
<div class="clear1"></div>
</div>

</br></br>
<div class="data" style="margin-top:0px;">Data del prelievo</div>
<div class="clear1"></div>
<br/><br/><br/><br/>
<div class="firma" style="margin-top:0px;">Timbro e firma</div>

<div style="page-break-before: always">&nbsp;</div>
<table align="center" cellspacing="17" cellpadding="10">

<%for (int h = 0; h < microchips.size(); h++){ %>


<%if (h == 0){ %>
<tr>
<%} else if ( h % 4 == 0){%>
</tr><tr>
<%}%>
<td><img src="<%=createBarcodeImage((String)microchips.get(h))%>" /></td>


<%} %>

</table>

</body>


<script>
window.opener.loadModalWindowUnlock();
</script>

