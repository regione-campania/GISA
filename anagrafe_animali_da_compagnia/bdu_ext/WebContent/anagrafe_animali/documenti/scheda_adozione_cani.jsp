<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@page import="org.aspcfs.modules.schedaAdozioneCani.base.Valutazione"%>
<%@page import="org.aspcfs.modules.schedaAdozioneCani.base.SchedaAdozione"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,java.io.IOException,org.aspcfs.modules.opu.base.*,java.util.Date,com.sun.org.apache.xerces.internal.impl.dv.util.Base64,java.io.ByteArrayOutputStream,javax.imageio.ImageIO"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>
<jsp:useBean id="schede" class="java.util.ArrayList" scope="request" />


<link type="text/css"
documentale_url="" href="anagrafe_animali/documenti/screen.css"
rel="stylesheet" />
	
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css"
	type="text/css" media="print" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="Cane"
	class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request" />
<jsp:useBean id="Gatto"
	class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request" />
	<jsp:useBean id="Furetto"
	class="org.aspcfs.modules.anagrafe_animali.base.Furetto" scope="request" />

<!-- LOOKUPS DECODIFICA -->
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="proprietario" class="org.aspcfs.modules.opu.base.Operatore"
	scope="request" />
<jsp:useBean id="detentore" class="org.aspcfs.modules.opu.base.Operatore"
	scope="request" />


<%
	Animale thisAnimale = null;
	if (Cane.getIdCane() > 0)
		thisAnimale = Cane;
	if (Gatto.getIdGatto() > 0)
		thisAnimale = Gatto;
	if (Furetto.getIdFuretto() > 0)
		thisAnimale = Furetto;
	LineaProduttiva linea_proprietario=null;
	LineaProduttiva linea_detentore=null;

%>

<% 
   Stabilimento temp = null;
   Indirizzo sedeOperativa = null;
   if (proprietario!=null)
	if (proprietario.getListaStabilimenti()!=null)
		if (!proprietario.getListaStabilimenti().isEmpty()){
			temp = (Stabilimento) proprietario.getListaStabilimenti().get(0); 
			sedeOperativa = temp.getSedeOperativa();
			linea_proprietario= (LineaProduttiva) temp.getListaLineeProduttive().get(0);	
		
		}%>


<% 
   Stabilimento temp2 = null;
   Indirizzo sedeOperativa2 = null;
   if (detentore!=null)
	if (detentore.getListaStabilimenti()!=null)
		if (!detentore.getListaStabilimenti().isEmpty()){
			temp2 = (Stabilimento) detentore.getListaStabilimenti().get(0); 
			sedeOperativa2 = temp2.getSedeOperativa();
			linea_detentore= (LineaProduttiva) temp2.getListaLineeProduttive().get(0);		
		}%>


<%
//CATTURO INFO MICROCHIP PER SOSTITUIRLO COL TATUAGGIO NEL CASO SIA ASSENTE
String value_microchip="";
if (thisAnimale.getMicrochip()!=null && !thisAnimale.getMicrochip().equals(""))
value_microchip = thisAnimale.getMicrochip();
else if (thisAnimale.getTatuaggio()!=null && !thisAnimale.getTatuaggio().equals(""))
	value_microchip=thisAnimale.getTatuaggio();
%>

<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>
<body>
<input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" />

  <jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="idAnimale" value="<%=thisAnimale.getIdAnimale() %>" />
     <jsp:param name="idSpecie" value="<%=thisAnimale.getIdSpecie() %>" />
      <jsp:param name="idTipo" value="PrintCertificatoIscrizione" />
       <jsp:param name="idMicrochip" value="<%=value_microchip %>" />
</jsp:include>
		
	<!-- input type="submit" name="Timbra PDF" class="buttonClass"
	onclick="window.location.href='GestioneDocumenti.do?command=ListaDocumentiByTipo&tipo=PrintCertificatoIscrizione&IdSpecie=<%=thisAnimale.getIdSpecie() %>&IdAnimale=<%=thisAnimale.getIdAnimale() %>&id_microchip=<%=value_microchip %>'" value="Gestione PDF" /-->
	
	<!-- <a href="AnimaleAction.do?command=Test&tipo=cert_prima_iscr&IdSpecie=<%=thisAnimale.getIdSpecie() %>&IdAnimale=<%=thisAnimale.getIdAnimale() %>&nomeProprietario=<%=proprietario.getRagioneSociale() %>">GENERA PDF TIMBRATO</a> -->
		 
<div class="imgRegione">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/regionecampania.jpg" />
</div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
<dhv:evaluate if="<%=(thisAnimale.getIdAslRiferimento()>0) %>"> 
<div class="imgAsl">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/<%=aslList.getSelectedValue(thisAnimale.getIdAslRiferimento()) %>.jpg" />
</div>
</dhv:evaluate>

<div class="Section1">
 </br> </br> </br> </br> </br> </br> </br>


<div class="box1">


<table width="100%" border="1" rules="all" cellpadding="4" style="border-collapse:collapse;">
<thead>
	<tr >
		<td colspan="4" style="text-align: CENTER; font-weight: bold; background-color: #BDCFFF;">DATI IDENTIFICATIVI DELL'ANIMALE</td>
	</tr>
	<tr >
		<td>Microchip</td>
		<td colspan="3"><%=(thisAnimale.getMicrochip() != null) ? thisAnimale.getMicrochip() : ""%></td>
	</tr>
	<tr >
		<td>Tatuaggio/ Secondo Microchip</td>
		<td colspan="3"><%= (thisAnimale.getTatuaggio() != null) ? thisAnimale.getTatuaggio()  : ""%></td>
	</tr>
	<tr >
		<td>Nome</td>
		<td colspan="3"><%=thisAnimale.getNome()%></td>
	</tr>
	<tr >
		<td>Specie</td>
		<td colspan="3"><%=thisAnimale.getNomeSpecieAnimale().toUpperCase()%>	</td>
	</tr>
	<tr >
		<td>Proprietario</td>
		<td colspan="3"><%=(proprietario == null) ? "" : (proprietario.getRagioneSociale() != null) ? proprietario.getRagioneSociale() : ""%></td>
	</tr>
	<tr >
		<td>Detentore</td>
		<td colspan="3"><%=(detentore.getRagioneSociale() != null) ? detentore.getRagioneSociale() : ""%>	</td>
	</tr>
	<tr >
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr >
		<td colspan="4" style="text-align: CENTER; font-weight: bold; background-color: #BDCFFF;">SCHEDA ADOZIONE</td>
	</tr>
	<tr >
		<td colspan="2" style="text-align: CENTER; font-weight: bold;background-color: #BDCFFF;">CRITERIO</td>
		<td style="text-align: CENTER; font-weight: bold;background-color: #BDCFFF;">INDICE</td>
		<td style="text-align: CENTER; font-weight: bold;background-color: #BDCFFF;">PUNTEGGIO</td>
	</tr>
<%
	int i=0;
	SchedaAdozione scheda = null;
	while(i<schede.size())
	{
		scheda = (SchedaAdozione)schede.get(i);
%>
		<tr >
			<td colspan="2"><dhv:label name=""><%=scheda.getCriterio().getNome().toUpperCase()%></dhv:label></td>
			<td><%=scheda.getIndice().getNome().toUpperCase() %></td>
			<td><%=scheda.getIndice().getPunteggio() %></td>
		</tr>
<%
		i++;
	}
%>
	<tr >
		<td colspan="3"></td>
		<td>TOTALE: <%=((Valutazione)request.getAttribute("valutazione")).getPunteggio()%></td>
	</tr>	
	<tr >
		<td colspan="3"></td>
		<td>VALUTAZIONE: <%=((Valutazione)request.getAttribute("valutazione")).getValutazione().toUpperCase()%></td>
	</tr>
	<tr >
			<td colspan="4" style="text-align: CENTER; font-weight: bold;background-color: #BDCFFF;">INFORMAZIONI INSERIMENTO</td>
		</tr>
	<tr >
		<td colspan="4">
			Inserito da <dhv:username id="<%=scheda.getEnteredBy()%>" /> il <%=toHtmlValue(toDateasString(scheda.getEntered()))%>
		</td>
	</tr>
	<tr >
		<td  colspan="4">
			Modificato da <dhv:username id="<%=scheda.getModifiedBy()%>" /> il <%=toHtmlValue(toDateasString(scheda.getModified()))%>
		</td>
	</tr>
</thead>
</table>
	<br/><br/><br/><br/>	
		
<%	
java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); %>
<div class="data">DATA RILASCIO CERTIFICATO</div>

<div class="datavalore"><%=dataToString( timeNow ) %> </div>

<div class="firma">TIMBRO E FIRMA DEL VETERINARIO</div>

<div class="firmavalore">&nbsp;</div>


</br>

</div>
</body>

