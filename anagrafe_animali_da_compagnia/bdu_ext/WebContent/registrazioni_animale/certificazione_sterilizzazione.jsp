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
<jsp:useBean id="dati_sterilizzazione" class="org.aspcfs.modules.registrazioniAnimali.base.EventoSterilizzazione"
	scope="request" />	


<link type="text/css"
href="anagrafe_animali/documenti/screen.css"
rel="stylesheet" />
	
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css"
	type="text/css" media="print" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="thisAnimale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request" />

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
<jsp:useBean id="dati_registrazione" class="org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU"
	scope="request" />	
<jsp:useBean id="pratica" class="org.aspcfs.modules.praticacontributi.base.Pratica" scope="request" />	

<%
LineaProduttiva linea_proprietario=null;
LineaProduttiva linea_detentore=null;%>

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
	
	  <jsp:include page="../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="idAnimale" value="<%=thisAnimale.getIdAnimale() %>" />
     <jsp:param name="idSpecie" value="<%=thisAnimale.getIdSpecie() %>" />
      <jsp:param name="idTipo" value="PrintCertificatoSmarrimento" />
       <jsp:param name="idMicrochip" value="<%=value_microchip %>" />
</jsp:include>
	
<!-- input type="submit" name="Timbra PDF" class="buttonClass"
	onclick="window.location.href='GestioneDocumenti.do?command=ListaDocumentiByTipo&tipo=PrintCertificatoSmarrimento&IdSpecie=<%=thisAnimale.getIdSpecie() %>&IdAnimale=<%=thisAnimale.getIdAnimale() %>&id_microchip=<%=value_microchip %>'" value="Gestione PDF" /-->
	
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
<div class="title1">CERTIFICATO DI STERILIZZAZIONE</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;

<div class="nodott_margin_low"><b><u>Dati del proprietario</u></b></div>
<div class="clear1"></div>

<div class="nodott_margin_low">Cognome e nome: </div>
<div class="dott_long_margin_low">&nbsp;<%=(proprietario.getRagioneSociale() != null) ? proprietario.getRagioneSociale() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Codice Fiscale:</div>
<div class="dott_margin_low">&nbsp; <%=(proprietario.getCodFiscale() != null && !("").equals(proprietario.getCodFiscale()))? proprietario.getCodFiscale() : "" %></div>
<div class="clear1"></div>

<div class="nodott_margin_low">Data di nascita:</div>
<div class="dott_margin_low">&nbsp;<%=(proprietario.getRappLegale() != null) ? toDateasString(proprietario.getRappLegale().getDataNascita()) : ""%>  </div>

<div class="nodott_margin_low">Luogo di nascita:</div>
<div class="dott_margin_low">&nbsp;<%=(proprietario.getRappLegale() != null) ? proprietario.getRappLegale().getComuneNascita() : ""%> </div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Comune di residenza:</div>
<div class="dott_long_margin_low" >&nbsp;  <%=(sedeOperativa != null) ? comuniList.getSelectedValue((sedeOperativa.getComune())) : ""%></div>
<div class="clear1"></div>


<div class="nodott_margin_low" ><u><b>Dati del detentore</u></b></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Cognome e nome: </div>
<div class="dott_long_margin_low" >&nbsp; <%=(detentore.getRagioneSociale() != null) ? detentore.getRagioneSociale() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Codice Fiscale:</div>
<div class="dott_margin_low" >&nbsp; <%=(detentore.getCodFiscale() != null && !("").equals(proprietario.getCodFiscale()))? detentore.getCodFiscale() : "" %></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data di nascita:</div>
<div class="dott_margin_low" >&nbsp; <%=(detentore.getRappLegale() != null) ? toDateasString(detentore.getRappLegale().getDataNascita()) : ""%> </div>
<div class="nodott_margin_low" >Luogo di nascita:</div>
<div class="dott_margin_low" >&nbsp; <%=(detentore.getRappLegale() != null) ? detentore.getRappLegale().getComuneNascita() : ""%> </div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Comune di residenza:</div>
<div class="dott_long_margin_low" >&nbsp;  <%=(sedeOperativa2 != null) ? comuniList.getSelectedValue((sedeOperativa2.getComune())) : ""%></div>
<div class="clear1"></div>

</div>

</br>

<div class="nodott_margin_low" ><b><u>Dati del <%=thisAnimale.getNomeSpecieAnimale() %></u></b></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Microchip: </div>
<div class="dott_margin_low" >&nbsp; <%=(thisAnimale.getMicrochip() != null) ? thisAnimale.getMicrochip() : ""%> </div>

<div class="nodott_margin_low" >Tatuaggio:</div>
<div class="dott_margin_low" >&nbsp; <%=(thisAnimale.getTatuaggio() != null) ? thisAnimale.getTatuaggio() : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Nome:</div>
<div class="dott_margin_low" >&nbsp; <%=thisAnimale.getNome() %></div>

<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() != Furetto.idSpecie) %>"> <!--  SOLO PER CANI E GATTI -->
<div class="nodott_margin_low" >Razza:</div>
<div class="dott_margin_low" >&nbsp; <%=razzaList.getSelectedValue(thisAnimale.getIdRazza()) %></div>
</dhv:evaluate>
<div class="clear1"></div>


<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() == Cane.idSpecie) %>"> 
<div class="nodott_margin_low" >Taglia:</div>
<div class="dott_margin_low" >&nbsp; <%=tagliaList.getSelectedValue((thisAnimale).getIdTaglia())%> </div>
</dhv:evaluate>

<div class="nodott_margin_low" >Mantello:</div>
<div class="dott_margin_low" >&nbsp; <%=mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello()) %></div>

<div class="nodott_margin_low" >Sesso:</div>
<div class="dott_short_margin_low" >&nbsp; <%=(thisAnimale.getSesso() != null) ? thisAnimale.getSesso() : "-" %></div>
<div class="clear1"></div>


<div class="nodott_margin_low" >Data nascita:</div>
<div class="dott_margin_low" >&nbsp;<%=(thisAnimale.getDataNascita() != null) ? toDateasString(thisAnimale.getDataNascita()) : "-" %></div>
<div class="nodott_margin_low" >Presunta:</div>
<div class="dott_short_margin_low" > &nbsp;<%=(thisAnimale.getFlagDataNascitaPresunta() ) ? "SI" : "NO" %></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data sterilizzazione:</div>
<div class="dott_margin_low" >&nbsp;<%=(thisAnimale.getDataSterilizzazione() != null) ? toDateasString(thisAnimale.getDataSterilizzazione()) : "-" %></div>
<div class="nodott_margin_low" >Data rilascio certificato:</div>
<%	
java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); %>
<div class="dott_margin_low" >&nbsp;<%=dataToString( timeNow ) %> </div>

<div class="clear1"></div>
<div class="nodott_margin_low" >Progetto di sterilizzazione:</div>
<div class="dott_margin_low" >&nbsp;<%="Decreto numero "+ pratica.getNumeroDecretoPratica() + " del "+ pratica.getDataDecretoFormattata()%> </div>

<div class="clear1"></div>
<div class="nodott_margin_low" >Sterilizzazione inserita da:</div>
<div class="dott_margin_low" >&nbsp;<dhv:username id="<%=dati_sterilizzazione.getEnteredby()%>" /> </div>


<br>
</br>


<div class="firma">TIMBRO E FIRMA DEL VETERINARIO</div>

<div class="firmavalore">&nbsp;</div>

<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>

</div>
</body>

