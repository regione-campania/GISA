<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,java.io.IOException,org.aspcfs.modules.opu.base.*,java.util.Date,org.aspcfs.modules.registrazioniAnimali.base.*"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>


<jsp:useBean id="animaleList" class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList" scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"	scope="request" />

<link rel="stylesheet" type="text/css" media="screen"
	href="registrazioni_animale/documenti/modello12_screen.css">
<link rel="stylesheet"
	documentale_url="" href="registrazioni_animale/documenti/modello12_print.css"
	type="text/css" media="print" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="listaEventi"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoList"
	scope="request" />
<jsp:useBean id="registrazioniList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="da"
	class="java.lang.String"
	scope="request" />
	<jsp:useBean id="a"
	class="java.lang.String"
	scope="request" />
<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>

<script>function catturaHtml(form){
	//popolaCampi();
	h=document.getElementsByTagName('html')[0].innerHTML;
	form.html.value = h;
	}</script>
	
	<form name="generaPDF" action="GestioneDocumenti.do?command=GeneraPDF" method="POST">
<input type="button" id ="generapdf" class="buttonClass"  value="Genera PDF" 	
onClick="this.disabled=true; this.value='GENERAZIONE PDF IN CORSO'; catturaHtml(this.form); this.form.submit()" />
<input type="hidden" name="html" id="html" value=""></input>
<input type="hidden" name="tipo" id="tipo" value="Modello12"></input>
</form>	 	
	
<body>
</br>
<!-- input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" /-->
<!-- input type="submit" name="Timbra PDF" class="buttonClass"
	onclick="this.disabled=1; window.location.href='RegistrazioniAnimale.do?command=TimbraModello12&tipo=modello12&da=<%=da.replaceAll(" 00:00:00.0", "") %>&a=<%=a.replaceAll(" 00:00:00.0", "") %>'" value="Timbra PDF" /-->
		
	<div class="boxIdDocumento"></div>
	<div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
	
</br><br></br><br>
<div style="float: right; font-weight: bold; font-size: 12px;">Mod. 12 - Modello trasmissione vaccinazioni antirabbiche</div>

</br></br>
<div style="border: 0px solid black; font-weight: bold; text-decoration: underline; ">Dati del veterinario</div>
<div style="border: 0px solid black;">

<div class="nodott_margin_low">Nome e cognome:</div>
<div class="dott_long_margin_low">&nbsp;<%=User.getUserRecord().getContact().getNameFirst()
					+ "," + User.getUserRecord().getContact().getNameLast()%></div>
<div class="clear1"></div>

<div class="nodott_margin_low">Iscrizione all'ordine della provincia di :</div>
<div class="dott_long_margin_low">&nbsp;<%=provinceList.getSelectedValue(User.getContact().getIdProvinciaIscrizioneOrdine()) %></div>
<div class="clear1"></div>

<div class="nodott_margin_low">Numero iscrizione :</div>
<div class="dott_long_margin_low">&nbsp;<%=(User.getContact().getNrIscrizioneOrdine() != null ) ? User.getContact().getNrIscrizioneOrdine() : "--" %></div>
<div class="clear1"></div>

<dhv:evaluate if="<%=(User.getSiteId() > 0)%>">
	<div class="nodott_margin_low">Asl:</div>
	<div class="dott_short_margin_low">&nbsp;<%=AslList.getSelectedValue(User.getSiteId())%></div>
	<div class="clear1"></div>
</dhv:evaluate></div>
</br>
</br>

<div style="border: 0px solid black; font-weight: bold; text-decoration: underline; ">Lista delle registrazioni di vaccinazione 		
<dhv:evaluate if="<%=da!=null &&!da.equals("") && a!=null &&!a.equals("")%>">
		(<%=da.replaceAll(" 00:00:00.0", "") %> - <%=a.replaceAll(" 00:00:00.0", "") %>)
		</dhv:evaluate></div></br>
	<table cellspacing="0" cellpadding="0" border="0"  width="100%"><!--

	<tr>
	<th colspan="13">Lista delle registrazioni di vaccinazione&nbsp; 
		
		<dhv:evaluate if="<%=da!=null &&!da.equals("") && a!=null &&!a.equals("")%>">
		(<%=da.replaceAll(" 00:00:00.0", "") %> - <%=a.replaceAll(" 00:00:00.0", "") %>)
		</dhv:evaluate>
		
		</th>
	</tr>
	--><tr style="border:1px solid black;">
		<th width="7%" ><b><center><dhv:label name="">Data vaccinazione</dhv:label></center></b></th>
		<th width="7%"><b><center><dhv:label name="">Animale</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Specie</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Razza</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Sesso</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Data nascita</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Proprietario</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Indirizzo proprietario</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Nome Vaccino</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Lotto Vaccino</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Produttore Vaccino</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Scadenza Vaccino</dhv:label></b></center></th>
		<th width="7%"><b><center><dhv:label name="">Asl</dhv:label></b></center></th>
	</tr>
	<%
		Iterator j = listaEventi.iterator();
		Iterator k = animaleList.iterator();
		if (j.hasNext()) {
			// int rowid = 0;
			int i = 0;
			while (j.hasNext()) {
				i++;
				//  rowid = (rowid != 1?1:2);
				Evento thisEvento = (Evento) j.next();
				Animale thisAnimale = (Animale)k.next();
				EventoInserimentoVaccinazioni thisVaccino = (EventoInserimentoVaccinazioni)thisEvento;
	%>
	<tr style="border:1px solid black;">
		
		<td width="7%"><center><%=toDateasString(thisVaccino.getDataVaccinazione())%>&nbsp;</center></td>
		<td width="7%"><center><%=thisEvento.getMicrochip()%></center></td>
		<td width="7%"><center><%=thisAnimale.getNomeSpecieAnimale()%></center></td>
		<td width="7%"><center><%=toHtml(razzaList.getSelectedValue(thisAnimale.getIdRazza()))%></center></td>
		<td width="7%"><center><%=thisAnimale.getSesso()%></center></td>
		<td width="7%"><center><%=toDateasString(thisAnimale.getDataNascita())%></center></td>
		<% Operatore proprietario = thisAnimale.getProprietario(); %>
		<td width="7%"><center><%=(proprietario != null && proprietario.getIdOperatore()>0 ) ? proprietario.getRagioneSociale() : "" %></center></td>
		<td width="7%"><center>
<%
		if(proprietario != null && proprietario.getIdOperatore()>0 )
		{	
			Stabilimento stab = (Stabilimento) proprietario.getListaStabilimenti().get(0);
	   	 	Indirizzo ind = (Indirizzo) stab.getSedeOperativa();
		
%>
	   		<%=ind.toString() %>
<%
		} 
		else
		{
%>
			--
<%			
		}
%>
		
	   	</center></td> 
		<td width="7%"><center><%=(thisVaccino.getNomeVaccino() != null) ? thisVaccino.getNomeVaccino(): "--"%></center></td>
		<td width="7%"><center><%=(thisVaccino.getNumeroLottoVaccino() != null) ? thisVaccino.getNumeroLottoVaccino(): "--"%></center></td>
		<td width="7%"><center><%=(thisVaccino.getProduttoreVaccino() != null) ? thisVaccino.getProduttoreVaccino(): "--"%></center></td>
		<td width="7%"><center><%=(thisVaccino.getDataScadenzaVaccino() != null) ? toDateasString(thisVaccino.getDataScadenzaVaccino()) : "--"%></center></td>
		<td width="7%"><center><%=AslList.getSelectedValue(thisEvento
									.getIdAslRiferimento())%>&nbsp;</center></td>
	</tr>

	<%
		}
	%>
	<%
		} else {
	%>
	<tr>
		<td colspan="9"><dhv:label name="">Nessuna registrazione individuata.</dhv:label>
		</td>
	</tr>
	<%
		}
	%>
</table>

</br></br>



<!--  	ELIMINATA COME DA RICHIESTA VERBALE 11/12/13
<div style="border: 1px solid black; font-weight: bold;">Dati di destinazione</div>
<div style="border: 1px solid black;">

<div class="nodott_margin_low">Ambulatorio/clinica veterinaria: </div>
<div class="dott_long_margin_low">&nbsp;</div>
<div class="clear1"></div>
<div class="nodott_margin_low">Via </div>
<div class="dott_long_margin_low">&nbsp;</div>
<div class="nodott_margin_low">  n. </div>
<div class="dott_short_margin_low">&nbsp;</div>
<div class="clear1"></div>
<div class="nodott_margin_low">Comune </div>
<div class="dott_long_margin_low">&nbsp;</div>
<div class="clear1"></div>
<div class="nodott_margin_low">Provincia </div>
<div class="dott_short_margin_low">&nbsp;</div>
<div class="clear1"></div>
<div class="nodott_margin_low">Asl </div>
<div class="dott_long_margin_low">&nbsp;</div>
<div class="clear1"></div>
</div>

-->



</body>

