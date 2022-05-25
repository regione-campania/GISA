<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript">
function controlloCuSorveglianza()
{
if (document.addticket.assignedDate.value == '')
	return checkForm(document.addticket);
orgId = document.addticket.orgId.value;
tipoIspezione = document.addticket.tipoIspezione.value;
PopolaCombo.controlloInserimentoCuSorveglianza(orgId, tipoIspezione,document.addticket.assignedDate.value, viewMessageCallback1);
}

function viewMessageCallback1(returnValue) {
messaggio1 = returnValue[0];
messaggio2 = returnValue[1];
if (messaggio1 != null && messaggio1 != "") 
{
	if (document.addticket.tipoIspezione.value == 3) 
	{
		alert('ATTENZIONE : non è possibile inserire un nuovo controllo in Sorveglianza. Esistono controlli ufficiali in sorveglianza ancora aperti.Chiudere prima i seguenti controlli: \n' + messaggio1);
		return false;
	} else
		{
			alert('ATTENZIONE : non è possibile inserire un nuovo controllo ' + messaggio1);
			return false;
		}
}
else
{
	if (messaggio2 != null && messaggio2 != "") 
	{
		alert('ATTENZIONE : non è possibile inserire un nuovo controllo in sorveglianza. Controllo inseribile a partire da ' + messaggio2);
		return false;
	} 
	else
		{
		if (document.addticket != null&& document.addticket.tipoIspezione.value != 3) 
		{
			if (document.addticket.id_linea_sottoposta_a_controllo !=null)
			{
				
				alert('"ATTENZIONE! Qualora siano state controllate, nel corso dello stesso controllo, piu linee attivita occorre inserire piu controlli (uno per ogni linea attivita sottoposta a controllo)"');
			}
		}
		
			return checkForm(document.addticket);
		}
		}

	}
</script>

<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
<dhv:include name="stabilimenti-sites" none="true">
	<%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
	<tr>
		<td nowrap class="formLabel"><dhv:label name="stabilimenti.site">Site</dhv:label>
		</td>
		<td><%=SiteIdList.getSelectedValue(OrgDetails.getSiteId())%> <input
			type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">

		</td>
	</tr>
	<%--</dhv:evaluate>  --%>
	<dhv:evaluate if="<%=SiteIdList.size() <= 1%>">
		<input type="hidden" name="siteId" id="siteId" value="-1" />
	</dhv:evaluate>
</dhv:include>
<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label name="">Tipo di controllo</dhv:label>
	</td>
	<td>
	<table border="0" cellspacing="3" cellpadding="0" class="empty">
		<tr>
			<td>
			<%
				PianoMonitoraggio1.setJsEvent("onChange=javascript:piani('addticket')");
				PianoMonitoraggio2.setJsEvent("onChange=javascript:piani('addticket')");
				PianoMonitoraggio3.setJsEvent("onChange=javascript:piani('addticket')");
				TipoAudit.setSelectStyle("display: none");

				AuditTipo.setSelectStyle("display: none");
				AuditTipo.setJsEvent("onChange=javascript:mostraMenu4('addticket')");
				TipoAudit.setJsEvent("onChange=javascript:mostraMenu2('addticket')");
				TipoAudit.setMultiple(true);
				Bpi.setSelectStyle("display: none");
				Bpi.setMultiple(true);
				Bpi.setSelectSize(10);

				Haccp.setSelectStyle("display: none");
				Haccp.setMultiple(true);
				Haccp.setSelectSize(10);
				TipoIspezione.setSelectStyle("display: none");
				PianoMonitoraggio1.setSelectStyle("display: none");
				PianoMonitoraggio2.setSelectStyle("display: none");
				PianoMonitoraggio3.setSelectStyle("display: none");
				TipoCampione.setJsEvent("onChange=javascript:reloadAddCU(this.value)");
			%> 
			<%=TipoCampione.getHtmlSelect("tipoCampione", TicketDetails.getTipoCampione())%>
			</td>
			<td><%=AuditTipo.getHtmlSelect("auditTipo", TicketDetails.getAuditTipo())%></td>

			<td>
			<table class="noborder">
				<tr>
					<td><%=TipoAudit.getHtmlSelect("tipoAudit", TicketDetails.getTipoAudit())%></td>
					<td>
					<table>
						<tr>
							<td><label id="lab1" style="display: none"><b>
							Motivo dell'Ispezione :</b></label></td>
						</tr>
						<tr>
							<td><%=TipoIspezione.getHtmlSelect("tipoIspezione",TicketDetails.getTipoIspezione())%></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		<td>
		<table style="display: none" id="tossinfezione">
				<tr>
					<td></td>
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
					<td><input type="text" name="alimentiSospetti"></td>
				</tr>
				<tr>
					<td>Data insorgenza Sintomi</td>
					<td><zeroio:dateSelect form="addticket" field="dataSintomi"
						timestamp="<%=TicketDetails.getDataSintomi()%>"
						timeZone="<%=TicketDetails.getDataSintomiTimeZone()%>"
						showTimeZone="false" /></td>
				</tr>
				<tr>
					<td>Data pasto Sospetto</td>
					<td><zeroio:dateSelect form="addticket" field="dataPasto"
						timestamp="<%=TicketDetails.getDataPasto()%>"
						timeZone="<%=TicketDetails.getDataPastoTimeZone()%>"
						showTimeZone="false" /></td>
				</tr>

			</table>

			<%=PianoMonitoraggio1.getHtmlSelect("pianoMonitoraggio1",TicketDetails.getPianoMonitoraggio())%> </br>
			<%=PianoMonitoraggio2.getHtmlSelect("pianoMonitoraggio2",TicketDetails.getPianoMonitoraggio())%> </br>
			<%=PianoMonitoraggio3.getHtmlSelect("pianoMonitoraggio3",TicketDetails.getPianoMonitoraggio())%> 
			<%=Bpi.getHtmlSelect("bpi", Integer.parseInt(TicketDetails.getBpi()))%> <label name="labelname1" id="label1" style="display: none">(* In caso di selezione multipla tenere </br>
			premuto il tasto Ctrl durante la selezione)</label> <%=Haccp.getHtmlSelect("haccp", TicketDetails.getHaccp())%>
			
			<label name="labelname2" id="label2" style="display: none">(*
			In caso di selezione multipla tenere </br>
			premuto il tasto Ctrl durante la selezione)</label> <font color="red">
			* </font></td>

			<td id="notealtro" style="visibility: hidden">Descrizione : <textarea name="notealtro" rows="6" cols="30"></textarea></td>
			<td id="contributi" style="visibility: hidden">Contributi in
			Euro (nei casi in cui è previsto dal D.Lgs 194/2008) : <input type="text" name="contributi"value="<%=TicketDetails.getContributi()%>"></td>

			<td id="hidden1" style="visibility: hidden">Codice Allerta 
			<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="id_allerta" name="idAllerta" value="<%=TicketDetails.getCodiceAllerta()%>">
			<font color="red">*</font> &nbsp;[<a href="javascript:popLookupSelectorAllerta('id_allerta','name','ticket','');">
			<dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] 
			<input type="hidden" id="ticketid" <%if (request.getAttribute("ticketidd") != null) {%> value="<%=request.getAttribute("ticketidd")%>" <%} else {%> value="0" <%}%> name="ticketidd"></td>

		</tr>

		<tr>
			<td>
			<table style="display: none" id="nonconformitaprec">
				<tr>
					<td>Le azioni correttive risultano adeguate ed efficaci ?"</td>
					<td>SI <input type="radio" name="azione" value="1"
						onclick="javascript : document.getElementById('desc1').style.display='block' ">
					</td>
					<td>NO <input type="radio"
						onclick="javascript : document.getElementById('desc1').style.display='none'"
						value="0" name="azione"></td>
				</tr>
				<tr id="desc1" style="display: none">
					<td>Descrizione :</td>
					<td><textarea name="azione_descrizione"></textarea></td>
					<td>&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</td>
</tr>
<tr>
</tr>

<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label
		name="sanzionia.data_richiesta">Data Inizio Controllo</dhv:label></td>
	<td><zeroio:dateSelect form="addticket" field="assignedDate"
		timestamp="<%=TicketDetails.getAssignedDate()%>"
		timeZone="<%=TicketDetails.getAssignedDateTimeZone()%>"
		showTimeZone="false" /> <font color="red">*</font></td>
</tr>
<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label name="">Data Fine Controllo</dhv:label>
	</td>
	<td><zeroio:dateSelect form="addticket" field="dataFineControllo"
		timestamp="<%=TicketDetails.getDataFineControllo()%>"
		showTimeZone="false" /></td>
</tr>

<tr>
	<td valign="top" class="formLabel"><dhv:label name="sanzioni.note">Note</dhv:label>
	</td>
	<td>
	<table border="0" cellspacing="0" cellpadding="0" class="empty">
		<tr>
			<td><textarea name="problem" cols="55" rows="8"><%=toString(TicketDetails.getProblem())%></textarea>
			</td>
			<td valign="top"><%=showAttribute(request, "problemError")%></td>
		</tr>
	</table>
	</td>
</tr>


<tr id="oggetto_controllo" style="display: none">
	<td valign="top" class="formLabel">Oggetto del Controllo</td>
	<td>
	<table border="0" cellspacing="0" cellpadding="0" class="empty">
		<tr>
			<td><select name="ispezione" size="10" multiple="multiple" id="ispezione"
				onmouseout="abilitaNoteDescrizioni()">
				<%
					Iterator<Integer> itLista = Ispezione.keySet().iterator();
					while (itLista.hasNext()) {
						int key = itLista.next();
				%>
				<optgroup label="<%=IspezioneMacrocategorie.getValueFromId(key)%>" style="color: blue"></optgroup>

				<%
					HashMap<Integer, String> l = (HashMap<Integer, String>) Ispezione
								.get(key);
						Iterator<Integer> itL = l.keySet().iterator();
						while (itL.hasNext()) {
							int code = itL.next();
							String desc = l.get(code);
							boolean sel = false;

							if (request.getAttribute("listaControlliIspezione")!=null)
							{
								ArrayList<Integer> lista = (ArrayList<Integer>) request.getAttribute("listaControlliIspezione");
								if (lista != null) {
									for (int code2 : lista) {

										if (code2 == code) {
											sel = true;
										}
									}
								}
							}
								
				%>


				<option value="<%=code%>" <%if (sel == true) {%> selected="selected"
					<%}%>><%=desc%></option>
				<%
					}

					}
				%>
			</select><font color="red">*</font></td>
			<td>&nbsp;</td>
			<td>
			<table>
				<tr id="desc_note1" style="display: none">
					<td><b>Settore Alimenti per il consumo Umano</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc1"></textarea></td>
				</tr>
				<tr id="desc_note2" style="display: none">
					<td><b>Settore alimenti Zootecnici</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc2"></textarea></td>
				</tr>
				<tr id="desc_note3" style="display: none">
					<td><b>Settore Benessere Animale non durante il trasporto</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc3"></textarea></td>
				</tr>
				<tr id="desc_note4" style="display: none">
					<td><b>Settore Sanita animale </b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc4"></textarea></td>
				</tr>
				<tr id="desc_note5" style="display: none">
					<td><b>Settore S.O.A. negli Impianti di trasformazione </b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc5"></textarea></td>
				</tr>
				<tr id="desc_note6" style="display: none">
					<td><b>Settore Rifiuti S.O.A. nelle altre imprese</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc6"></textarea></td>
				</tr>
				<tr id="desc_note7" style="display: none">
					<td><b>Altro (Descrizione)</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc7"></textarea></td>
				</tr>
				<tr id="desc_note8" style="display: none">
					<td><b>Settore Benessere Animale durante il trasporto </b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc8"></textarea></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>
	</td>
</tr>

<input type="hidden" name="ncrilevate" value="2" />
<!-- nucleo ispettivo -->
<%@ include file="nucleo_ispettivo_modify.jsp"%>


