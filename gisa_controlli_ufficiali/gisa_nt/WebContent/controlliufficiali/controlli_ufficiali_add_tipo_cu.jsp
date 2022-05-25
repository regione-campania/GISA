<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>

<script>

function view_uo_attivita(ind)
{
	document.getElementById('tipoIspezionePerContoDiDialog').value='attivita'
		document.getElementById("posizionePianoDialog").value=ind ;
	$('#dialogPerContoDi' ).dialog('open');
	
	}
	
function view_uo_attivita_multiplo()
{
	$('#dialogPerContoDiMultiplo' ).dialog('open');
	
	}
	
	
function checkApiariSelezionati(val){
	
	if (val=="A"){
		document.getElementById("apiariSelezionatiMotivodiv").style.display="none";
		document.getElementById("apiariSelezionatiMotivo").value="-1";
		document.getElementById("apiariSelezionatiMotivoAltrodiv").style.display="none";
		document.getElementById("apiariSelezionatiMotivoAltro").value = "";
		
		document.getElementById("apiariSelezionatiAlveariControllatidiv").style.display="";
		document.getElementById("apiariSelezionatiEsitodiv").style.display="";
		
	}
	else if (val=="B"){
		document.getElementById("apiariSelezionatiMotivodiv").style.display="";
		
		document.getElementById("apiariSelezionatiAlveariControllatidiv").style.display="";
		document.getElementById("apiariSelezionatiEsitodiv").style.display="";
	}
}	
function checkApiariSelezionatiMotivo(val){
	if (val=="4"){
		document.getElementById("apiariSelezionatiMotivoAltrodiv").style.display="";
	}
	else {
		document.getElementById("apiariSelezionatiMotivoAltrodiv").style.display="none";
	}
}
function checkApiariSelezionatiEsito(val){
	if (val=="P"){
		document.getElementById("apiariSelezionatiEsitoNotediv").style.display="";
	}
	else{
		document.getElementById("apiariSelezionatiEsitoNotediv").style.display="none";
	}	
}

</script>

<input type= "hidden" name="context" value = "gisa">
<link rel="stylesheet" href="css/jquery-ui.css" />
<%
			
			TipoCampione.setJsEvent(TipoCampione.getJsEvent()+";mostraStrutturaAsl()");
			int valTipoCampioneDefault = -1;
			int valTipoIspezioneDefault = -1;
			if (TipoCampione.size()==1)
			{
				valTipoCampioneDefault = ((LookupElement)TipoCampione.get(0)).getCode();
			}
			if (TipoIspezione.size()==1 && valTipoCampioneDefault ==4)
			{
				valTipoIspezioneDefault = ((LookupElement)TipoIspezione.get(0)).getCode();
				
				
			}
				
			%> 
			
<tr class="containerBody">
	<td nowrap class="formLabel" width="50p;"><dhv:label name="">Tecnica di controllo</dhv:label> 
	</td>
	
	
	
	<td><%=TipoCampione.getHtmlSelect("tipoCampione", valTipoCampioneDefault)%>
		
		<%
		System.out.println(TipoCampione.getHtmlSelect("tipoCampione", valTipoCampioneDefault));
		
		%>
		
		<%=showError(request, "struttureControllateAutoritaCompetentiError")%>
		<%=showError(request, "tipoControlloError")%>
	<%=showError(request, "tipoControlloUOError")%>
	<%=showError(request, "data_preavvisoError")%>
    <%=showError(request, "flag_checklistError")%>	
    <%=showError(request, "lineaAttivitaError")%>	
    
			</td>
	
</tr>
			
<tr class="containerBody"  id="tr_select_motivo" style="display: none">
	<td nowrap class="formLabel" width="50p;">&nbsp;
	</td>
	<td>
	<table id = "tipo_cu" class="noborder">
	<tr><td>
	<a href ="#" id = "link_select_motivo_href"><div id = "link_select_motivo">seleziona</div></a>
				
				<%@ include file="../controlliufficiali/dialog_motivi_ispezione.jsp" %>
				<%@ include file="../controlliufficiali/dialog_motivi_audit.jsp" %>
				<%@ include file="../controlliufficiali/dialog_strutture_per_conto_di.jsp" %>
				<%@ include file="../controlliufficiali/dialog_strutture_per_conto_di_multiplo.jsp" %>
								<%@ include file="../controlliufficiali/dialog_strutture_controllate_autorita_competenti.jsp" %>
				
				<%@ include file="../controlliufficiali/dialog_lista_componenti_nucleo_ispettivo.jsp" %>
	</td><td>
	
		
	<%=showError(request, "motivoIspezioneError")%>
	</td></tr>
	</table>
			
			
			</td>
	
</tr>
<tr id = "oggettoAudit" style="display: none" >
<td class = "formLabel" width="50p;">Oggetto Dell'Audit</td>
<td>
<%OggettoAudit.setSelectSize(9);
OggettoAudit.setMultiple(true);

%>

<%=OggettoAudit.getHtmlSelect("oggetto_audit",-1) %>
</td>
</tr>


<!-- GESTIONE PER AUTORITA COMPETENTI -->
<tr id = "auditFollowup" style="display: none" >
<td class = "formLabel" width="50p;">Audit di follow up?</td>
<td>
<input type="checkbox" id="auditFollowupCb" name="auditFollowupCb"/>
</td>
</tr>


<!-- Aggiunta della riga relativa all'allegato per controlli in supervisione -->
<tr class="containerBody" id="hiddenSuper" style="display: none">
		<td nowrap class="formLabel" width="50p;">
		<dhv:label name="sanzionia.data_richiesta"> Verbale</dhv:label>
		</td>
		<td>
		<a href = "javascript:openUploadListaPopUpDocumentale(<%=OrgDetails.getOrgId() %>,-1,'VerbaleSupervisione')">Allega Verbale</a>
		
		<input type="hidden" name="dosubmit" value="true" /> 
		<input type="hidden" id = "id" name="id" value="<%= OrgDetails.getOrgId() %>" /> 
		<input type="hidden" name="gotoPageSupervisione" value="insert" />
		<input type="hidden" id = "folderId" name="folderId" value="<%= "-1" %>" />
		<input type="hidden" name="idFileSupervisione" id = "idFileSupervisione" value="<%=fileItem.getId() %>">
		<input type="hidden" name="fileAllegareSupervisione" id = "fileAllegareSupervisione" value="<%=fileItem.getId() %>">
		<input type="hidden" name="isAllegatoSupervisione" id = "isAllegatoSupervisione" value="false">
		<input type="text" readonly style="border-style:none;" name="allegatoSupervisioneDocumentale" id = "allegatoSupervisioneDocumentale" value=""/>
		<label name="allegatoSupervisioneDocumentaleNome" id = "allegatoSupervisioneDocumentaleNome"></label>
		<label name="msg_fileSupervisione" id = "msg_fileSupervisione"><font color = "red">File non Allegato</font></label>
		
		</td>
	</tr>
	

<%
String codiceInterno  =  "" ;
if(TipoIspezione.getElementfromValue(valTipoIspezioneDefault)!=null)
 codiceInterno  = TipoIspezione.getElementfromValue(valTipoIspezioneDefault).getCodiceInterno();

%>

<%

if ( "6".equalsIgnoreCase(""+OrgDetails.getTipologia()+"") )
{
%>
<tr>
<td class = "formLabel" width="50p;">Strutture Controllate</td>
<td>

<a href="#" onclick="$('#dialogAutoritaCompetenti' ).dialog('open');" style="">
		<font  color="#006699" style="font-weight: bold;">
		Seleziona Le Strutture Controllare</font></a>
		<div id = "struttureControllateDiv"></div>
		
</td>
<td>
<%} %>
<tr id = "per_conto_di" <%if(codiceInterno.equalsIgnoreCase("2a")) {%>style="display:none"<%}%>>
<td class = "formLabel" width="50p;">Per Conto di</td>
<td>
	<%=showError(request, "tipoControlloUoMultipleError")%>
	
		<table class="noborder" id ="listaStruttureMultiple">
			<tr><td id="">
			<a href="#" onclick="view_uo_attivita_multiplo()">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
			</td></tr>
			<tr><td> <input type = "hidden" name = "uo_controllo" id = "uo_controllo" value = "-1"></td></tr>
			
			
			
			</table>
		
</td>
</tr>

<%
if(request.getAttribute("StrutturaAslSelezionata")!=null )
{
%>
<tr id = "per_conto_di_settato"  style="display:none">
<td class = "formLabel" width="50p;">Per Conto di</td>
<td>

<%
OiaNodo nn =null;
			if(request.getAttribute("StrutturaAslSelezionata")!=null)
			 nn = (OiaNodo)request.getAttribute("StrutturaAslSelezionata");
		%>
		
		
				
			<table class="noborder" id ="">
			<tr><td id="">
			<%=nn.getDescrizione_lunga() %>
			</td></tr>
			<tr><td> <input type = "hidden" name = "uo_controllo_settato" id = "uo_controllo_settato" value = "<%=nn.getId()%>"></td></tr>
			
			
			
			</table>
		
</td>
</tr><%}else
	{%>
	
	<tr id = "per_conto_di_settato"  style="display:none">
<td class = "formLabel" width="50p;">Per Conto di</td>
<td>
<%
	LookupList strutturaAslSelezionataLookup = (LookupList)request.getAttribute("StrutturaAslSelezionataLookup");
	%>
	<table class="noborder" id ="">
			<tr><td id="">
			<%String t = strutturaAslSelezionataLookup != null ? strutturaAslSelezionataLookup.getHtmlSelect("uo_controllo_settato", -1) : "no"; %>
			<!--FLUSSO NUOVO DPAT DISATTIVO QUESTO -->
			<%-- <%=strutturaAslSelezionataLookup.getHtmlSelect("uo_controllo_settato", -1) %> --%>
			</td></tr>
			<tr><td></td></tr>
			
			
			
			
			</table>
	
	<%} %>
<input type = "hidden" name = "num_piani" id = "num_piani" value = "0"/>
<%


boolean isAllerta=false;
boolean entrato = false;
Iterator<LookupElement> itTipiIspez = TipoIspezione.iterator();

while(itTipiIspez.hasNext())
{
	 isAllerta=false;
	LookupElement elTipIsp = itTipiIspez.next();
	String codeIsp =elTipIsp.getCodiceInterno();
	
	
	if(codeIsp!=null)
	if (codeIsp.equalsIgnoreCase("2a") )
	{
		%>
		
		<tr id ="clonepiano" class="containerBody" <%if(codiceInterno.equalsIgnoreCase("2a")){%> <%} else{%>style="display: none" <% }%>>
		<td nowrap class="formLabel" width="50p;"><label id = "piano"><b></b></label></td>
		<td >
		<table class="noborder">
		<tr><td colspan="2"  >
		<input type = "hidden" name = "piano_monitoraggio" id = "piano_value" value = "-1">
		 <font color = "red">*</font>
		<input type = "hidden" name = "uo" id = "uo" value="-1">
		<div id ="uodescr" ></div>
		<a href="#" onclick="" style="">
		<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
		</td></tr>
		</table>
		
		
		</td></tr>



<%
}	else if (codeIsp.equalsIgnoreCase("14a"))
	{
		%>
		<tr id="macellazione" style="display: none"  class="containerBody">
		<td  class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %>
			</td>
		<td>
		<table class = "noborder">
				<tr>
				<td >Contributi in Euro <br></td>
				<td ><input type="text" name="contributi_macellazione" value="<%=TicketDetails.getContributi_macellazione_urgenza()%>"></td>
				<td >&nbsp;</td>
				</tr>
				
				<tr>
				<td >Prelievo Campione Per Ricerca Trichine <input type ="checkbox" checked="checked" name = "flagCampione1" value="1"></td>
				<td >Prelievo Coagulo per MVS <input type ="checkbox" name = "flagCampione2" value="2"></td>
				<td>&nbsp;</td>
				</tr>
				<tr >
				<td align="left" width="30%" colspan="3" style="padding-top: 35px;">
				
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
				
				</td>
				</tr>
		</table>
</td>
</tr>


		<%
	}else if (codeIsp.equalsIgnoreCase("28a"))
	{
		%>
		<tr id="macellazione_urgenza" style="display: none"  class="containerBody">
		<td  class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %>
			</td>
		<td>
		<table class = "noborder">
				<tr>
				<td>Contributi in Euro</td>
				<td><input type="text" name="contributi_macellazione_urgenza" value="<%=TicketDetails.getContributi_macellazione()%>"></td>
				<td>&nbsp;</td>
				
				</tr>
				
				<tr >
				<td align="left" width="30%" colspan="3" style="padding-top: 35px;">
					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
				Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
				</td>
				
				</tr>
		</table>
</td>
</tr>

		<%
	}
	
	else if (codeIsp.equalsIgnoreCase("1a"))
	{
		%>
		
<tr id="seguitodicampionamento" style="display: none" class="containerBody">
		<td class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %>
		<td>
		<table class = "noborder">
				<tr>
				<td>Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td><input type="text" name="contributi" value="<%=TicketDetails.getContributi_seguito_campioni_tamponi()%>"></td>
				<td></td>
				
				</tr>
				<tr >
				<td align="left" width="30%" colspan="3" style="padding-top: 35px;">
					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
				Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
				</td>
				
				</tr>
		</table>
</td>
</tr>
		<%
	}
	else if (codeIsp.equalsIgnoreCase("20a"))
	{
		%>
		

<tr id="svincolisanitari" style="display: none" class="containerBody">
		<td class = "formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td>
		<td>
		<table class = "noborder">
				
				<tr>
						<td>Data Preavviso</td>
					<td>
					
					<input readonly type="text" id="data_preavviso" name="data_preavviso" size="10" />
		<a href="#" onClick="cal19.select(document.getElementById('data_preavviso'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
					</td></tr>
					<tr>
					<td>
					Protocollo Preavviso
					</td>
					<td >
					<input type = "text" name = "protocollo_preavviso" value = "<%=toHtml2(TicketDetails.getProtocollo_preavviso()) %>">
					</td>
					
				</tr>
				<tr>
				<td>Data Comunicazione Svincolo</td>
					<td>
					<input readonly type="text" id="data_comunicazione_svincolo" name="data_comunicazione_svincolo" size="10" />
		<a href="#" onClick="cal19.select(document.getElementById('data_comunicazione_svincolo'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
					</td></tr><tr>
					<td>
					Protocollo Svincolo
					</td>
					<td >
					<input type = "text" name = "protocollo_svincolo" value = "<%=toHtml2(TicketDetails.getProtocollo_svincolo()) %>">
					</td>
					
				</tr>
					<tr>
				<td>Tipologia Prodotto</td>
					<td>
						<textarea rows="5" cols="33" name="tipologia_sottoprodotto"></textarea>
					</td></tr><tr>
					<td>
					peso
					</td>
					<td >
					<input type = "text" name = "peso" value = "<%=TicketDetails.getPeso() %>">
					</td>
					
				</tr>
				<tr >
				<td align="left" width="30%" colspan="2" style="padding-top: 35px;">
					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
				Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
				</td>
				
				</tr>
		</table>
</td>
</tr>
		<%
	}
	else if (codeIsp.equalsIgnoreCase("10a"))
	{
		%>
		
<tr id="importazionescambio" style="display: none" class="containerBody">
		<td class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td>
		<td>
		<table class = "noborder">
				<tr>
				<td>Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td><input type="text" name="contributi_importazione_scambio" value="<%=TicketDetails.getContributi_macellazione()%>"></td>
				<td>
			&nbsp;
				</td>
				
				</tr>
				<tr >
				<td align="left" width="30%" colspan="3" style="padding-top: 35px;">
					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
				Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
				</td>
				
				</tr>
		</table>
</td>
</tr>
		<%
	}
	else if (codeIsp.equalsIgnoreCase("7a") )
	{
		isAllerta=true;
		%>
		
		
		<%
		if ( entrato==false)
		{
			entrato=true;
		%>
		<tr id="hidden1" style="display: none" class="containerBody">
		<td class="formLabel" width="50p;">Allarme Rapido</td>
		<td>
		
	<%=showError(request, "tipoControlloAllertaError")%>
	<%=showError(request, "tipoControlloAllertaFileError")%>
		<table class= "noborder">
		
		<tr>
		<td>Codice Allerta</td>
		<td>
		
			<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="id_allerta" name="idAllerta" value="<%=TicketDetails.getCodiceAllerta()%>">
			<font color="red">*</font> &nbsp;[<a href="javascript:popLookupSelectorAllerta('id_allerta','name','ticket','');">
			<dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] 
			<input type="hidden" id="ticketid" <%if (request.getAttribute("ticketidd") != null) {%> value="<%=request.getAttribute("ticketidd")%>" <%} else {%> value="0" <%}%> name="ticketidd">
			<input type="hidden" id="idLdd" name="idLdd"/>
		</td>
		</tr>
		
		<tr>
		<td>
		Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008) 
		</td>
		<td >
		<input type="text" name="contributi_allarme_rapido" value="<%=TicketDetails.getContributi_allarme_rapido()%>">
		</td>
		<td>
			&nbsp;
				</td>
				
		</tr>
		
					
	</table>
	</td>
	</tr>
	<%} %>
	
<%-- 	<tr id = "hidden1<%=elTipIsp.getCode() %>" style="display: none" > --%>
<!-- 			<td class="formLabel" width="50p;"> -->
<%-- 			<%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td> --%>
<!-- 				<td align="left"  colspan="5" style="padding-top: 35px;"> -->
<%-- 					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"> --%>
<!-- 				<font  color="#006699" style="font-weight: bold;"> -->
<!-- 				Seleziona Per Conto di</font> -->
<!-- 				</a> -->
<%-- 				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1"> --%>
<%-- 				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div> --%>
<!-- 				</td> -->
				
<!-- 				</tr> -->
		
	
				
		
	
		

				
		
		<%
		
	}
	else if (codeIsp.equalsIgnoreCase("30a"))
	{
		%>
		<tr id="rilascio_certificazione" style="display: none" class="containerBody">
		<td class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td>
		<td>
		<table class = "noborder">
				<tr>
				<td>Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td><input type="text" name="contributi_rilascio_certificazione" value="<%=TicketDetails.getContributi_rilascio_certificazione()%>"></td>
				<td>
					&nbsp;
				</td>
				
				</tr>
				<tr >
				<td align="left" width="30%" colspan="3" style="padding-top: 35px;">
					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
				Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
				</td>
				
				</tr>
		</table>
</td>
</tr>
		
		<%
	}
	//Aggiunta per sospetto
	else if (codeIsp.equalsIgnoreCase("9a"))
	{
		%>
		
	<tr id="tipoSospetto" style="display: none" class="containerBody">
		<td class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td>
		<td>
		<table class = "noborder">
			<tr>
				<td>
				<select id="tipoSosp" name="tipoSosp" onchange="if (this.value==1){document.getElementById('tipoBuffer').style.display='';} else{document.getElementById('tipoBuffer').style.display='none';}">
					<option value="-1">Seleziona voce</option>
					<option value="1">Per emergenza ambientali</option>
  					<option value="2">Per altro motivo</option>
				</select>
				<font color="red">*</font>
				</td>
				<td id="tipoBuffer" style="display:none">
				<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="idBuffer" name="idBuffer" value="<%=(TicketDetails.getCodiceBuffer()!= null && !TicketDetails.getCodiceBuffer().equals("NULL")) ? TicketDetails.getCodiceBuffer() : ""%>">
				<font color="red">*</font> &nbsp;[<a href="javascript:popLookupSelectorBuffer('idBuffer','name','buffer','');">
				<dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
				<input type="hidden" id="id_buffer" name="id_buffer" />
			
				</td>
			</tr>
		</table>
	</td>
   </tr>
   <tr class="containerBody" id ="ispezione_generica_<%=elTipIsp.getCode() %>" style="display: none">
	<td nowrap class="formLabel" width="50p;"><%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td>
		<td>
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
				</td>
				
	</tr>
   
		<%
	}
	else if (codeIsp.equalsIgnoreCase("58a"))
	{
		%>
		<tr id="nonconformitaprec" style="display: none" class="containerBody" >
		<td class = "formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %>
		<td>
		<table class = "noborder">
				<tr>
				<td>Le azioni correttive <br> risultano adeguate ed efficaci ?</td>
					<td>SI <input type="radio" name="azione" value="1"
						onclick="javascript : document.getElementById('desc1').style.display='block' ">
					</td>
					<td>NO <input type="radio"
						onclick="javascript : document.getElementById('desc1').style.display='none'"
						value="0" name="azione">
					</td>
					<td id="desc1" style="display: none">
					Descrizione<br>
					<textarea name="azione_descrizione"></textarea>
					</td>
					<td >&nbsp;&nbsp;Contributi in
					Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008) </td>
					<td >&nbsp;&nbsp;
					<input type="text" name="contributi_risol_nc" value="<%=TicketDetails.getContributi_verifica_risoluzione_nc()%>">
</td>
<td>
					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
				</td>
				
				</tr>
		</table>
</td>
</tr>
		<%
	}
	else if (codeIsp.equalsIgnoreCase("16a"))
	{
		%>
		
<tr  style="display: none" id="tossinfezione" class="containerBody"> 
		<td class="formLabel" width="50p;"> <%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td>
		<td>
		<table class = "noborder">
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>Soggetti Coinvolti</td>
					<td><input type="text" name="soggettiCoinvolti"></td>
				</tr>
				<tr>
					<td>Di cui Ricoverati</td>
					<td><input type="text" name="ricoverati"></td>
				</tr>
				<tr>
					<td>Alimenti Sospetti</td>
					<td><textarea name="alimentiSospetti" rows="4" cols="30"></textarea></td>
				</tr>
				<tr>
					<td>Data insorgenza Sintomi</td>
					<td>
					<input readonly type="text" id="dataSintomi" name="dataSintomi" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataSintomi,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td>
					
				</tr>
				<tr>
					<td>Data ingestione pasto Sospetto</td>
					<td>
					
					<input readonly type="text" id="dataPasto" name="dataPasto" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataPasto,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
					
					
				</td>
				</tr>
				<tr><td>Per condo Di</td>
				<td>
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
				</td>
				
				
				</tr>
			</table>
</td>
</tr>
		<%
	}
	else {
		
		%>	
	<tr class="containerBody" id ="ispezione_generica_<%=elTipIsp.getCode() %>" style="display: none">
			<td nowrap class="formLabel" width="50p;"><%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td>
				<td>
					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
						</td>
						
	</tr>
		<%
		
	}
	
	if (isAllerta==true)
	{
		%>
		
		<tr class="containerBody" id ="ispezione_generica_<%=elTipIsp.getCode() %>" style="display: none">
			<td nowrap class="formLabel" width="50p;"><%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td>
				<td>
					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "-1">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"></div>
						</td>
						
	</tr>
		
		
		<%
	}

}
%>

<script>
function gestisciCondizionalita(cb){
	var select = document.getElementById("condizionalita");
	var div = document.getElementById("cond");
	if (cb.checked){
		div.style="display:none";
		select.value="38";
	}
	else {
		div.style="display:block";
	}
}
</script>

<tr id="condizionalitarow" style="display: none" class="containerBody">
			<td class="formLabel" width="50p;">
				CONDIZIONALITA
			<td>
			<table class = "noborder">
					<tr>
					<td>
					<input type="checkbox" id="cond_cb" name="cond_cb" onClick="gestisciCondizionalita(this)"/>Non necessita di condizionalità
					
					<div id="cond" style="display:none">
					<%=Condizionalita.getHtmlSelect("condizionalita", 38) %>
					</div>
					
					</td>
						
					<td>
					
					&nbsp;
					</td>
			</table>
	</td>
	</tr>
	
	<tr id="condizionalitab11row" style="display: none" class="containerBody">
			<td class="formLabel" width="50p;">
				CONDIZIONALITA
			<td>
			<table class = "noborder">
					<tr>
					<td>
					<input type="checkbox" id="cond_b11_cb" name="cond_b11_cb" onClick="return false;" />ATTO B11 - rintracciabilità e sicurezza alimentare
					</td>
					<td>
					&nbsp;
					</td>
			</table>
	</td>
	</tr>
	
	<tr id="checklist_ba_tr" class="containerBody" style="display: none">
		            <td  class="formLabel" width="50p;">
					E' stata consegnata una copia della<br>
					presente checklist all'allevatore?
					</td>
					<td>
						<select id= "flag_checklist" name = "flag_checklist" >
							<option value = "-1" selected="selected" >Seleziona Voce</option>
							<option value = "N">NO</option>
							<option value = "S">SI</option>
						</select>			
				    </td>
	</tr>
	
	
	<!-- commento al 214-->
	<tr id="misureFormativetr" class="containerBody" style="display: none">
		            <td  class="formLabel" width="50p;">
					Misure formative
					</td>
					<td>
						<input type="text" value="" name="misureFormative" id="misureFormative"/>
						<font color="red">*</font>	
						<font color="red">Le misure prescrittive sono di competenza delle attività AO9_B, AO9_C e B52 che vanno inserite da macroarea iuv e sono valide solo per cani padronali e canili </font>		
				    </td>
	</tr>
	
	<tr id="misureRestrittivetr" class="containerBody" style="display: none">
		            <td  class="formLabel" width="50p;">
					Misure restrittive
					</td>
					<td>
						<input type="text" value="" name="misureRestrittive" id="misureRestrittive"/>
						<font color="red">*</font>			
				    </td>
	</tr>
	
	<tr id="misureRiabilitativetr" class="containerBody" style="display: none">
		            <td  class="formLabel" width="50p;">
					Misure riabilitative
					</td>
					<td>
						<input type="text" value="" name="misureRiabilitative" id="misureRiabilitative"/>			
						<font color="red">*</font>
				    </td>
	</tr>
	
	<!-- commento al 247-->
<tr id="apiariSelezionatitr" class="containerBody" style="display: none">
		            <td  class="formLabel" width="50p;">
					Informazioni Stato Sanitario Apiari
					</td>
					<td> <b>Apiari Selezionati: </b>
						<input type="radio" id="apiariSelezionatiA" name="apiariSelezionati" value="A" onClick="checkApiariSelezionati(this.value)">Random	
						<input type="radio" id="apiariSelezionatiB" name="apiariSelezionati" value="B" onClick="checkApiariSelezionati(this.value)">Con criteri di rischio		
				    	<font color="red">*</font>
				    	
				    	<div id="apiariSelezionatiMotivodiv" style="display:none">
				    	<b>Motivo: </b>
				    	<select id="apiariSelezionatiMotivo" name="apiariSelezionatiMotivo" onChange="checkApiariSelezionatiMotivo(this.value)">
						<option value="1">nomadismo</option>
						<option value="2">acquisto materiale biologico </option>
						<option value="3">apicoltori in possesso di diversi apiari stanziali in più regioni o province autonome</option>
						<option value="4">altro</option>
						</select>			
						</div>
						
						<div id="apiariSelezionatiMotivoAltrodiv" style="display:none">
						<b>Altro:</b> <input type="text" id="apiariSelezionatiMotivoAltro" name="apiariSelezionatiMotivoAltro" placeholder="Altro"/> <br/>
						</div>
						
						<div id="apiariSelezionatiAlveariControllatidiv" style="display:none">
						<b>Numero alveari controllati:</b> <input type="number" min="0" step="1" onkeypress="return event.charCode >= 48 && event.charCode <= 57" id="apiariSelezionatiAlveariControllati" name="apiariSelezionatiAlveariControllati"/> <br/>
				    	</div>
				    	
						<div id="apiariSelezionatiEsitodiv" style="display:none">
				    	<b>Esito:</b> 
				    	<input type="radio" id="apiariSelezionatiEsitoP" name="apiariSelezionatiEsito" value="P" onClick="checkApiariSelezionatiEsito(this.value)">Positivo	
						<input type="radio" id="apiariSelezionatiEsitoN" name="apiariSelezionatiEsito" value="N" onClick="checkApiariSelezionatiEsito(this.value)">Negativo	
						</div>
						
						<div id="apiariSelezionatiEsitoNotediv" style="display:none">
						<b>Note:</b> <input type="text" id="apiariSelezionatiEsitoNote" name="apiariSelezionatiEsitoNote" placeholder="Note"/> <br/>
						</div>
						
				    </td>
	</tr>
	
	 