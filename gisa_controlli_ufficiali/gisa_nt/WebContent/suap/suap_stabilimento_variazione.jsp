<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.suap.base.Stabilimento"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList"     class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList"    class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoAttivita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoCarattere" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoMobili" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />
<jsp:useBean id="StabilimentoOpu" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>

<script type="text/javascript" src="suap/javascriptsuap/suap_stabilimento.js"></script>
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>

<%@ include file="../initPage.jsp" %>

<table style="width: 29%">
	<tr>
		<td>TIPO</td>
		<td>
			<%
			TipoAttivita.setRequired(true);
            TipoAttivita.setJsEvent("onchange='mostraDatiStabilimento(this.value);mostraAttivitaProduttive(\"attprincipale\",1,-1, false,"+newStabilimento.getTipoInserimentoScia()+");'");%> 
            
           <b> <%= ((LookupElement)TipoAttivita.get(0)).getDescription()%></b>
            <input type="hidden" name="tipoAttivita" id="tipoAttivita" value ="<%= ((LookupElement)TipoAttivita.get(0)).getCode()%>">
            
            
            
		</td>
	</tr>
	<tr>
		<td>CARATTERE</td>
		<td>
			<%
			
			TipoCarattere.setRequired(true);
			TipoCarattere.setJsEvent("onchange=visualizzaData(this);"); %> 
			
			<%if(((LookupElement)TipoAttivita.get(0)).getCode()==Stabilimento.TIPO_SCIA_APICOLTURA){ %>
			
			<%=TipoCarattere.getSelectedValue(1) %>
			<input type="hidden" name = "tipoCarattere" id = "tipoCarattere" value="<%=(StabilimentoOpu.getIdStabilimento()>0)? StabilimentoOpu.getTipoCarattere() : "1" %>">
			<%}else
				{%>
			<%=TipoCarattere.getHtmlSelect("tipoCarattere", StabilimentoOpu.getTipoCarattere())%>
			<%} %>
		</td>
	</tr>

	<tr style="display: none" id="trDataInizio">
		<td>DATA INIZIO</td>
		<td><input type="text" size="15" name="dataInizioAttivita"
			id="dataInizio" class="required" placeholder="dd/MM/YYYY" readonly value="<%=toDateasString(StabilimentoOpu.getDataInizioAttivita())%>">
		</td>
	</tr>
	<tr style="display: none" id="trDataFine">
		<td>DATA FINE</td>
		<td><input type="text" size="15" name="dataFineAttivita"
			id="dataFine" class="required" value="" placeholder="dd/MM/YYYY" value="<%=toDateasString(StabilimentoOpu.getDataFineAttivita())%>"
			readonly></td>
	</tr>

</table>
 <hr/>
<table id="datiIndirizzoStab" style="width: 130%">
	<tr  style="display : none">
		<td></td>
		<td><input type="checkbox" onclick="copiaDaLegale()"
			id="checkSeddeOperativa"> SPUNTARE SE LA SEDE PRODUTTIVA
			COINCIDE CON LA SEDE LEGALE</td>
	</tr>
	<tr>
		<td>PROVINCIA</td>
		<td><select name="searchcodeIdprovinciaStab"
			id="searchcodeIdprovinciaStab" class="required">
			
			<%
			if(StabilimentoOpu.getIdStabilimento()>0)
			{
				%>
				<option value="<%=StabilimentoOpu.getSedeOperativa().getIdProvincia()%>"><%=StabilimentoOpu.getSedeOperativa().getDescrizione_provincia() %></option>
				<%
			}
			%>
				<option value=""></option>
		</select> <input type="hidden" name="searchcodeIdprovinciaTestoStab"
			id="searchcodeIdprovinciaTestoStab" /></td>
	</tr>
	<tr>
		<td>COMUNE</td>
		<td><select name="searchcodeIdComuneStab" 
			id="searchcodeIdComuneStab" class="required">
				<%
			if(StabilimentoOpu.getIdStabilimento()>0)
			{
				%>
				<option value="<%=StabilimentoOpu.getSedeOperativa().getComune()%>"><%=StabilimentoOpu.getSedeOperativa().getDescrizioneComune()%></option>
				<%
			}%>
				<option value=""></option>
		</select> <input type="hidden" name="searchcodeIdComuneTestoStab"
			id="searchcodeIdComuneTestoStab" /></td>
	</tr>
	<tr>
		<td>INDIRIZZO</td>
		<td>
		
		<%=StabilimentoOpu.getSedeOperativa().getDescrizioneToponimo()%> <b><%=StabilimentoOpu.getSedeOperativa().getVia()%></b>, <%=toHtml(StabilimentoOpu.getSedeOperativa().getCivico()) %>		
		
			<table class="noborder" style="display:none">
				<tr>
					<td>
					<select name="toponimoSedeOperativa" id="toponimoSedeOperativa" class="required">
					<option value="<%=StabilimentoOpu.getSedeOperativa().getToponimo()%>"><%=StabilimentoOpu.getSedeOperativa().getDescrizioneToponimo()%></option>
					</select>
					
					</td>
					<td>
					<select name="viaStab" id="viaStab" class="required">
					<option value="<%=StabilimentoOpu.getSedeOperativa().getVia()%>"><%=StabilimentoOpu.getSedeOperativa().getVia()%></option>
					</select>
					
					</td>

					<td><input type="text" readonly name="civicoSedeOperativa" value="<%=toHtml(StabilimentoOpu.getSedeOperativa().getCivico()) %>"
						id="civicoSedeOperativa" required="required" placeholder="NUM."
						size="4" maxlength="7"></td>
					<td>
					</td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>COORDINATE</td>
		<td>LAT <input type="text" readonly name="latStab" id="latStab"  value="<%=StabilimentoOpu.getSedeOperativa().getLatitudine() %>"
			class="required" /> LONG
			<input type="text" readonly name="longStab" id="longStab"  value="<%=StabilimentoOpu.getSedeOperativa().getLongitudine() %>"
			class="required"/>
			
			<%--script>
			inizializzaCoordinate('latStab', 'lat');
			inizializzaCoordinate('longStab', 'long');
			</script--%>
			
		</td>
	</tr>
	<tr>
		<td>TELEFONO</td>
		<td><input type="text" name="telefono"></td>
	</tr>

	<tr>
		<td>NOTE</td>
		<td>
		<textarea name="noteStab" cols="50" rows="5" id="noteStab"></textarea>
		</td>
	</tr>
</table>

<script>

$(function() {
        $('#dataInizio').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg',onClose: controlloDate}); 
        $('#dataFine').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg',onClose: controlloDate}); 
});

function controlloDate(){
    if(document.getElementById(this.id.replace("Fine","Inizio")).value==""){
            alert("ATTENZIONE! Inserire prima la data inizio linea.");        
            this.value="";
    }else{
            if(!confrontoDate(document.getElementById(this.id.replace("Fine","Inizio")).value,document.getElementById(this.id).value)){
                    alert("ATTENZIONE! La data fine deve essere maggiore della data inizio.");
                    this.value="";
            }
    }
}

</script>


<!-- <%//@ include file="../opu/campi_mobile_add.jsp"%> -->