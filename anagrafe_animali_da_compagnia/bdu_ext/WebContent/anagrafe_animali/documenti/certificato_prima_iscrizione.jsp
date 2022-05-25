<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,java.io.IOException,org.aspcfs.modules.opu.base.*,java.util.Date,com.sun.org.apache.xerces.internal.impl.dv.util.Base64,java.io.ByteArrayOutputStream,javax.imageio.ImageIO"%>
	<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>


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
 </br>
<div class="title1">CERTIFICATO DI ISCRIZIONE ALL'ANAGRAFE CANINA REGIONALE</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;


<div style="border: 1px solid black;">Dati del proprietario</div>
<div style="border: 1px solid black;">

<div class="nodott_margin_low">Cognome e nome: </div>
<div class="dott_long_margin_low">&nbsp;<%=(proprietario == null) ? "" : (proprietario.getRagioneSociale() != null) ? proprietario.getRagioneSociale() : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low">Data e luogo di nascita:</div>
<div class="dott_long_margin_low">&nbsp;<%=(proprietario == null) ? "" : (proprietario.getRappLegale() != null) ? toDateasString(proprietario.getRappLegale().getDataNascita()) : ""%> - <%=(proprietario.getRappLegale() != null) ? proprietario.getRappLegale().getComuneNascita() : ""%> </div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Comune di residenza:</div>
<div class="dott_long_margin_low" >&nbsp;  <%=(sedeOperativa != null) ? comuniList.getSelectedValue((sedeOperativa.getComune())) : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Indirizzo:</div>
<div class="dott_long_margin_low" >&nbsp; <%=(sedeOperativa != null) ? sedeOperativa.getVia() : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Telefono:</div>
<div class="dott_margin_low" >&nbsp;  <%=(linea_proprietario == null) ? "" : (linea_proprietario.getTelefono1()!= null) ? (linea_proprietario.getTelefono1()) : ""%></div>
<div class="nodott_margin_low" >Cellulare/Tel. secondario:</div>
<div class="dott_margin_low" >&nbsp; 
<%
if(proprietario.getRappLegale() != null){
	  String telefono_sec="";
	  if(proprietario.getRappLegale().getTelefono1()!=null && !proprietario.getRappLegale().getTelefono1().equals("")) telefono_sec=proprietario.getRappLegale().getTelefono1();
 	  if(proprietario.getRappLegale().getTelefono2()!=null && !proprietario.getRappLegale().getTelefono2().equals("")) telefono_sec+="/"+proprietario.getRappLegale().getTelefono2();
	  out.print(telefono_sec);
} 
%>
</div>
<div class="clear1"></div>

<div class="nodott_margin_low" style=" margin-bottom: 10px;">Codice Fiscale:</div>
<div class="dott_margin_low" style="  margin-bottom: 10px;">&nbsp; <%=(proprietario == null) ? "" : (proprietario.getCodFiscale() != null && !("").equals(proprietario.getCodFiscale()))? proprietario.getCodFiscale() : "" %></div>
<div class="clear1"></div>
</div>

</br>

<div style="border: 1px solid black;">Dati del detentore</div>
<div style="border: 1px solid black;">

<%if (linea_detentore == null || linea_detentore.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia){ %>
<div class="nodott_margin_low" >Cognome e nome: </div>
<div class="dott_long_margin_low" >&nbsp; <%=(detentore.getRagioneSociale() != null) ? detentore.getRagioneSociale() : ""%>
</div>


<div class="clear1"></div>

<div class="nodott_margin_low" >Data e luogo di nascita:</div>
<div class="dott_long_margin_low" >&nbsp; <%=(detentore == null) ? "" : (detentore.getRappLegale() != null) ? toDateasString(detentore.getRappLegale().getDataNascita()) : ""%> - <%=(detentore.getRappLegale() != null) ? detentore.getRappLegale().getComuneNascita() : ""%> </div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Comune di residenza:</div>
<div class="dott_long_margin_low" >&nbsp;  <%=(sedeOperativa2 != null) ? comuniList.getSelectedValue((sedeOperativa2.getComune())) : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Indirizzo:</div>
<div class="dott_long_margin_low" >&nbsp; <%=(sedeOperativa2 != null) ? sedeOperativa2.getVia() : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Telefono:</div>
<div class="dott_margin_low" >&nbsp; <%=(linea_detentore == null) ? "" : (linea_detentore.getTelefono1() != null) ? (linea_detentore.getTelefono1()) : ""%></div>
<div class="nodott_margin_low" >Cellulare/Tel. secondario:</div>
<div class="dott_margin_low" >&nbsp; <%=(linea_detentore == null) ? "" : (linea_detentore.getTelefono2() != null) ? (linea_detentore.getTelefono2()) : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-bottom: 10px;">Codice Fiscale:</div>
<div class="dott_margin_low" >&nbsp; <%=(detentore == null) ? "" : (detentore.getCodFiscale() != null && !("").equals(proprietario.getCodFiscale()))? detentore.getCodFiscale() : "" %></div>
<div class="clear1"></div>
<%} else {

	Stabilimento StabilimentoDettaglio = (Stabilimento) detentore.getListaStabilimenti().get(0);
%>
<!-- COLONIA -->
<div class="nodott_margin_low" >Numero Colonia </div>
<div class="dott_long_margin_low" >&nbsp; <%= ((ColoniaInformazioni) linea_detentore)
										.getNrProtocollo()%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Cognome e nome tutore: </div>
<div class="dott_long_margin_low" >&nbsp; <%=(detentore.getRagioneSociale() != null) ? detentore.getRagioneSociale() : ""%>
</div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data e luogo di nascita tutore:</div>
<%
if(StabilimentoDettaglio!=null)
{
%>
	<div class="dott_long_margin_low" >&nbsp; <%=(StabilimentoDettaglio.getRappLegale() != null) ? toDateasString(StabilimentoDettaglio.getRappLegale().getDataNascita()) : ""%> - <%=(StabilimentoDettaglio.getRappLegale() != null) ? StabilimentoDettaglio.getRappLegale().getComuneNascita() : ""%> </div>
<%
}
else
{
%>
	<div class="dott_long_margin_low" >&nbsp; <%=(detentore.getRappLegale() != null) ? toDateasString(detentore.getRappLegale().getDataNascita()) : ""%> - <%=(detentore.getRappLegale() != null) ? detentore.getRappLegale().getComuneNascita() : ""%> </div>
<%
}
%>
<div class="clear1"></div>

<div class="nodott_margin_low" >Comune colonia:</div>
<div class="dott_long_margin_low" >&nbsp;  <%=(sedeOperativa2 != null) ? comuniList.getSelectedValue((sedeOperativa2.getComune())) : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Indirizzo colonia:</div>
<div class="dott_long_margin_low" >&nbsp; <%=(sedeOperativa2 != null) ? sedeOperativa2.getVia() : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Telefono tutore:</div>

<%
if(StabilimentoDettaglio!=null)
{
%>
	<div class="dott_margin_low" >&nbsp; <%=(StabilimentoDettaglio.getRappLegale().getTelefono1() != null) ? (StabilimentoDettaglio.getRappLegale().getTelefono1()) : ""%></div>
<%
}
else
{
%>
	<div class="dott_margin_low" >&nbsp; <%=(detentore.getRappLegale().getTelefono1() != null) ? (detentore.getRappLegale().getTelefono1()) : ""%></div>
<%
}
%>


<div class="nodott_margin_low" >Cellulare/Tel. secondario tutore:</div>

<%
if(StabilimentoDettaglio!=null)
{
%>
	<div class="dott_margin_low" >&nbsp; <%=(StabilimentoDettaglio.getRappLegale().getTelefono2() != null) ? (StabilimentoDettaglio.getRappLegale().getTelefono2()) : ""%></div><%
}
else
{
%>
	<div class="dott_margin_low" >&nbsp; <%=(detentore.getRappLegale().getTelefono2() != null) ? (detentore.getRappLegale().getTelefono2()) : ""%></div><%
}
%>


<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-bottom: 10px;">Codice Fiscale tutore:</div>
<div class="dott_margin_low" >&nbsp; <%=(detentore.getCodFiscale() != null && !("").equals(proprietario.getCodFiscale()))? detentore.getCodFiscale() : "" %></div>
<div class="clear1"></div>

<%} %>
</div>
</br>

<div style="border: 1px solid black;">Dati del <%=thisAnimale.getNomeSpecieAnimale().toLowerCase() %></div>
<div style="border: 1px solid black;">

<div class="nodott_margin_low" >Microchip: </div>
<div class="dott_margin_low" >&nbsp; <%=(thisAnimale.getMicrochip() != null) ? thisAnimale.getMicrochip() : ""%> </div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data inserimento microchip:</div>
<div class="dott_margin_low" >&nbsp;  <%= (thisAnimale.getDataMicrochip() != null) ? toDateasString(thisAnimale.getDataMicrochip()) : ""%> </div>
<div class="barcode"><img src="<%=createBarcodeImage(value_microchip)%>" /></div>
<div class="barcode"></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Tatuaggio:</div>
<div class="dott_margin_low" >&nbsp; 

<%= (thisAnimale.getTatuaggio() != null) ? thisAnimale.getTatuaggio()  : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Nome:</div>
<div class="dott_margin_low" >&nbsp; <%=thisAnimale.getNome() %></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Mantello:</div>
<div class="dott_margin_low" >&nbsp; <%=mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello()) %></div>
<div class="clear1"></div>

<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() != Furetto.idSpecie) %>"> <!--  SOLO PER CANI E GATTI -->
<div class="nodott_margin_low" >Razza:</div>
<div class="dott_long_margin_low" >&nbsp; <%=razzaList.getSelectedValue(thisAnimale.getIdRazza()) %></div>
<div class="clear1"></div></dhv:evaluate>

<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() == Cane.idSpecie) %>"> 
<div class="nodott_margin_low" >Taglia:</div>
<div class="dott_margin_low" >&nbsp; <%=tagliaList.getSelectedValue(( thisAnimale).getIdTaglia())%> </div>
<div class="clear1"></div>
</dhv:evaluate>

<div class="nodott_margin_low" >Passaporto:</div>
<div class="dott_margin_low" >&nbsp; <%=(thisAnimale.getNumeroPassaporto() != null) ? thisAnimale.getNumeroPassaporto() : "-" %></div>
<div class="nodott_margin_low" >Data rilascio passaporto:</div>
<div class="dott_margin_low" >&nbsp; <%=(thisAnimale.getDataRilascioPassaporto() != null) ? toDateasString(thisAnimale.getDataRilascioPassaporto()) : "-" %></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Data nascita:</div>
<div class="dott_margin_low" >&nbsp;<%=(thisAnimale.getDataNascita() != null) ? toDateasString(thisAnimale.getDataNascita()) : "-" %></div>
<div class="nodott_margin_low" >Presunta:</div>
<div class="dott_short_margin_low" > &nbsp;<%=(thisAnimale.getFlagDataNascitaPresunta() ) ? "SI" : "NO" %></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Sesso:</div>
<div class="dott_short_margin_low" >&nbsp; <%=(thisAnimale.getSesso() != null) ? thisAnimale.getSesso() : "-" %></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Sterilizzazione:</div>
<div class="dott_margin_low" >&nbsp; <%=(thisAnimale.getFlagSterilizzazione() == true) ? "SI "+toDateasString(thisAnimale.getDataSterilizzazione() ): "-" %></div>
<div class="clear1"></div>
</div>
<br>
<%	
java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); %>
<div class="data">DATA RILASCIO CERTIFICATO</div>

<div class="datavalore"><%=dataToString( timeNow ) %> </div>

<div class="firma">TIMBRO E FIRMA DEL VETERINARIO</div>

<div class="firmavalore">&nbsp;</div>


</div>
</body>

