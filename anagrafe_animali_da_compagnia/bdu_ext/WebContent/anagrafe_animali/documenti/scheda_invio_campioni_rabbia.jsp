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
<jsp:useBean id="dati_registrazione" class="org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU"
	scope="request" />
<jsp:useBean id="dati_vaccinazione" class="org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni"
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
	

Operatore proprietario = thisAnimale.getProprietario();


Stabilimento stab = null;
Indirizzo sedeOperativa = null;
LineaProduttiva lp = null;
if (proprietario!=null)
	if (proprietario.getListaStabilimenti()!=null)
		if (!proprietario.getListaStabilimenti().isEmpty()){
			stab = (Stabilimento) proprietario.getListaStabilimenti().get(0);
			lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			sedeOperativa = stab.getSedeOperativa();
			linea_proprietario= (LineaProduttiva) stab.getListaLineeProduttive().get(0);	
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
		 
<div class="Section1">
<div style="clear: both;">
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
<dhv:evaluate if="<%=(thisAnimale.getIdAslRiferimento()>0) %>"> 
<dhv:evaluate if="<%=(User.getRoleId() == Integer.parseInt(ApplicationProperties.getProperty("UNINA"))) %>">
<img style="text-decoration: none;" documentale_url="" src="anagrafe_animali/documenti/images/unina3.png" />
</dhv:evaluate>
<dhv:evaluate if="<%=User.getRoleId() != Integer.parseInt(ApplicationProperties.getProperty("UNINA")) %>">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/<%=aslList.getSelectedValue(thisAnimale.getIdAslRiferimento()) %>.jpg" />
</dhv:evaluate>

</dhv:evaluate>
<div style="border: 1px solid black; padding-left: 10px; width: 20%; height: 70px; font-size: 12px; float: right;text-align: justify; ">DESTINATARIO <br> IZS ____________ <br>PER IL TRAMITE <br>IZS PORTICI</div>
<div class="titleizsm" style="font-weight: bold; border: 1px solid black;  height: 70px;"  >TITOLAZIONE ANTICORPI RABBIA<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SCHEDA INVIO CAMPIONI</div>
</div>


<div class="box1">
<table width="100%" border="1" rules="all" bordercolor="black">
<thead><tr><td colspan="2" style="text-align: CENTER; font-weight: bold;">DATI IDENTIFICATIVI DELL'ANIMALE</td></tr></thead>
<!-- <tr><td width="50%"><font style="font-weight: bold;"> DATA PRESUNTA DI PARTENZA </font></td><td width="50%">&nbsp; </td></tr>
<tr><td width="50%"> STATO DI DESTINAZIONE </td><td width="50%">&nbsp; </td></tr> -->
<tr><td width="50%"> SPECIE </td><td width="50%">&nbsp;<%=specieList.getSelectedValue(thisAnimale.getIdSpecie()) %> </td></tr>
<tr><td width="50%"> NOME </td><td width="50%">&nbsp; <%= (thisAnimale.getNome() != null) ? thisAnimale.getNome() : "" %> </td></tr>
<tr><td width="50%"> DATA DI NASCITA </td><td width="50%">&nbsp;<%=(thisAnimale.getDataNascita() != null) ? toDateasString(thisAnimale.getDataNascita())  : "" %></td></tr>
<tr><td width="50%"> SESSO </td><td width="50%">&nbsp;<%=thisAnimale.getSesso() %></td></tr>
<tr><td width="50%"> RAZZA </td><td width="50%">&nbsp;<%=razzaList.getSelectedValue(thisAnimale.getIdRazza()) %> </td></tr>
<tr><td width="50%"> TAGLIA </td><td width="50%">&nbsp;<%=(thisAnimale.getIdSpecie() == Cane.idSpecie) ? tagliaList.getSelectedValue(((Cane)thisAnimale).getIdTaglia()) : (thisAnimale.getIdSpecie() == Gatto.idSpecie) ? tagliaList.getSelectedValue(((Gatto)thisAnimale).getIdTaglia()) :  tagliaList.getSelectedValue(((Furetto)thisAnimale).getIdTaglia()) %>    </td></tr>
<tr><td width="50%"> MANTELLO (COLORE E TIPO)</td><td width="50%">&nbsp;<%=mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello()) %> </td></tr>
<tr><td width="50%"> SECONDO MICROCHIP/TATUAGGIO </td><td width="50%">&nbsp; <%=(thisAnimale.getTatuaggio() != null) ? thisAnimale.getTatuaggio() : "" %> </td></tr>
<tr><td width="50%"> MICROCHIP: <!-- NUMERO E   DATA DI APPLICAZIONE --></td><td width="50%">&nbsp; <%=thisAnimale.getMicrochip() %>  </td></tr>
<tr><td width="50%"> DATA DI APPLICAZIONE </td><td width="50%">&nbsp;  <%=(thisAnimale.getDataMicrochip() != null)?  toDateasString(thisAnimale.getDataMicrochip()) : "" %> </td></tr>
<tr><td width="50%"> DATA ULTIMA VACCINAZIONE ANTIRABBICA </td><td width="50%">&nbsp; <%=(dati_vaccinazione.getDataVaccinazione() != null) ? toDateasString(dati_vaccinazione.getDataVaccinazione()) : "--"%> </td></tr>
<tr><td width="50%"> NOME DEL VACCINO </td><td width="50%">&nbsp; <%=(dati_vaccinazione.getNomeVaccino() != null && !("").equals(dati_vaccinazione.getNomeVaccino())) ? dati_vaccinazione.getNomeVaccino() : "--"%> </td></tr>
<tr><td width="50%"> N. DI LOTTO DEL VACCINO </td><td width="50%">&nbsp; <%=(dati_vaccinazione.getNumeroLottoVaccino()!= null) ? dati_vaccinazione.getNumeroLottoVaccino() : "--" %> </td></tr>
<tr><td width="50%"> PRODUTTORE DEL VACCINO </td><td width="50%">&nbsp; <%=(dati_vaccinazione.getProduttoreVaccino()!= null) ? dati_vaccinazione.getProduttoreVaccino() : "--" %> </td></tr>
<tr><td width="50%"> DATA SCADENZA DEL VACCINO </td><td width="50%">&nbsp; <%=(dati_vaccinazione.getDataScadenzaVaccino()!= null) ? toDateasString(dati_vaccinazione.getDataScadenzaVaccino()) : "--" %> </td></tr>
<tr><td width="50%"> DATA DEL PRELIEVO </td><td width="50%">&nbsp; </td></tr>
</table>
<!-- div class="leftSquare">



</div>
<div class="rightSquare">


</div-->

</div>

<div class="box2" style="border: 0px;">
<div class="" style="text-align: center;"><font style="font-weight: bold;">Proprietario</font></div>
<br/>
<!-- div style="float: left;">
<ul id="altriesami">
<li><div>&nbsp;</div> </li>
<li><div>&nbsp;</div> </li>
<li><div>&nbsp;</div> </li>
</ul>

<div class="clear1"></div>

<ul id="altriesami">
<li><div>&nbsp;</div> </li>
<li><div>&nbsp;</div> </li>
<li><div>&nbsp;</div> </li>
</ul>
</div-->
</div>

<div class="box3" style="border: 0px;">
<dhv:evaluate if="<%=lp!=null && !(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato) %>">
<div class="nodott_margin_low"><b>NOME</b> </div>
<div class="nodott_margin_low">&nbsp;<%=proprietario.getRagioneSociale()%></div>
<div class="clear1"></div>
</dhv:evaluate>

<dhv:evaluate if="<%=lp!=null && (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato) %>">
<div class="nodott_margin_low"><b>NOME</b> </div>
<div class="nodott_margin_low">&nbsp;<%=proprietario.getRappLegale().getNome()%></div>
<div class="nodott_margin_low"><b>COGNOME</b> </div>
<div class="nodott_margin_low">&nbsp;<%=proprietario.getRappLegale().getCognome()%></div>
<div class="clear1"></div>
</dhv:evaluate>

<dhv:evaluate if="<%=lp!=null && lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato %>">
<div class="nodott_margin_low"><b>COMUNE NASCITA</b> </div>
<div class="nodott_margin_low">&nbsp;  <%=(proprietario.getRappLegale() != null) ? proprietario.getRappLegale().getComuneNascita() : ""%> </div>
<div class="clear1"></div>
</dhv:evaluate>

<div class="nodott_margin_low" ><b>VIA</b></div>
<div class="nodott_margin_low" >&nbsp; <%=(sedeOperativa != null) ? sedeOperativa.getVia() : ""%></div>
<div class="clear1"></div>
<div class="leftSquare" style="border: 0px;">
<div class="nodott_margin_low" ><b>CITTA'</b></div>
<div class="nodott_margin_low" >&nbsp;  <%=(sedeOperativa != null) ? comuniList.getSelectedValue((sedeOperativa.getComune())) : ""%></div>
<div class="clear1"></div>

<dhv:evaluate if="<%=lp!=null && lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato %>">
<div class="nodott_margin_low" ><b>CODICE FISCALE</b></div>
<div class="nodott_margin_low" >&nbsp; <%=(proprietario.getRappLegale() != null) ? (proprietario.getRappLegale().getCodFiscale()) : ""%> </div>
</dhv:evaluate>
<div class="clear1"></div>


<div class="nodott_margin_low" ><b>TELEFONO</b></div>
<div class="nodott_margin_low" >&nbsp;<%=(linea_proprietario.getTelefono1() != null) ? (linea_proprietario.getTelefono1()) : ""%>  </div>

<div class="clear1"></div>


</div>
<div class="rightSquare" style="border: 0px;">
<div class="nodott_margin_low" style=" "><b>CAP</b> </div>
<div class="nodott_margin_low">&nbsp; <%=(sedeOperativa != null) ? sedeOperativa.getCap() : ""%>  </div>
<div class="clear1"></div>


<div class="nodott_margin_low" style=""><b>P.IVA</b> </div>
<div class="nodott_margin_low">&nbsp;  </div>
<div class="clear1"></div>



<div class="nodott_margin_low" style=""><b>FAX</b> </div>
<div class="nodott_margin_low">&nbsp;  </div>
<div class="clear1"></div>

</div>



</div>



<div class="clear1"></div>
</div>

<div class="data" style="margin-top:0px;">Veterinario</div>
<div class="data" style="margin-top:15px;">_______________</div>
</br></br></br></br>
<div class="firma" style="margin-top:0px;">Timbro e firma</div>
<div class="firma" style="margin-top:15px;">_______________</div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>

</body>

