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
<jsp:useBean id="fissa"           class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />

<%
	String comuneSuap = User.getUserRecord().getSuap().getDescrizioneComune();
	if(comuneSuap!=null && comuneSuap.equals(""))
		comuneSuap=null;
%>

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
			
			<%if(((LookupElement)TipoAttivita.get(0)).getCode()==Stabilimento.LOOKUP_ATTIVITA_APICOLTURA){ %>
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
		<td colspan="2">
			<font color="red">ATTENZIONE! Posizionarsi sul campo comune per inserire tutto l'indirizzo</font>
		</td>
	</tr>
	<tr>
		<td>COMUNE</td>
		<td>
		    
		    <input type="hidden" name="idComuneSedeOperativa" id="idComuneSedeOperativa" value="<%=StabilimentoOpu.getSedeOperativa().getComune()%>">
			
<%
				if((fissa!=null && !fissa.equals("") && fissa.equals("true") && comuneSuap!=null))
				{
%>
					<input size="50" onclick="selezionaIndirizzo('106','callBackStab',this.value)" type="text" name="searchcodeIdComuneStabinput" id="searchcodeIdComuneStabinput" placeholder="DENOMINAZIONE COMUNE" value="<%=comuneSuap%>" />
<%
				}
				else
				{
%>
					<input size="50" onclick="selezionaIndirizzo('106','callBackStab')" type="text" name="searchcodeIdComuneStabinput" id="searchcodeIdComuneStabinput" placeholder="DENOMINAZIONE COMUNE"  value="<%=StabilimentoOpu.getSedeOperativa().getDescrizioneComune()%>" />
<%
				}
%>		
		</td>
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
		<td>INDIRIZZO</td>
		<td>
			<table class="noborder">
				<tr>
					<td>
					
					<%
					String toponimoDef = "VIA";
					if (StabilimentoOpu.getIdStabilimento()>0 && !"".equals(StabilimentoOpu.getSedeOperativa().getDescrizioneToponimo()))
						toponimoDef = StabilimentoOpu.getSedeOperativa().getDescrizioneToponimo();
					%>
					<%=ToponimiList.getHtmlSelect("toponimoSedeOperativa", toponimoDef)%>
					
					<input type="hidden" name="toponimoSedeOperativaId" id="toponimoSedeOperativaId" />
					</td>
					<td>
					<select name="viaStab" id="viaStab" class="required" readonly >
					
						<%
			if(StabilimentoOpu.getIdStabilimento()>0)
			{
				%>
				<option value="<%=StabilimentoOpu.getSedeOperativa().getVia()%>"><%=StabilimentoOpu.getSedeOperativa().getVia()%></option>
				<%
			}%>
					</select>
					
					</td>

					<td><input type="text" name="civicoSedeOperativa" value="<%=toHtml(StabilimentoOpu.getSedeOperativa().getCivico()) %>"
						id="civicoSedeOperativa" required="required" placeholder="NUM."
						size="4" maxlength="7" readonly ></td>
					<td><input type="text" size="4" id="capStab" readonly name="capStab"  value="<%=StabilimentoOpu.getSedeOperativa().getCap() %>"
						maxlength="5" value="" required="required" placeholder="CAP" 
						onfocus="chkCap(document.getElementById('searchcodeIdComuneStab').value,'capStab')" title="DATI STABILIMENTO: CAP INDIRIZZO non valido. Tornare indietro e correggere il campo">
						<input type="hidden" value="Calcola CAP" id="bottoneCapStab" name="bottoneCapStab"
						onclick="calcolaCap(document.getElementById('searchcodeIdComuneStab').value, document.getElementById('toponimoSedeOperativa').value, document.getElementById('viaStabinput').value, 'capStab');" />
					</td>
					<td>
						<input id="coord1button" type="button" value="Calcola Coordinate"
						onclick="javascript:showCoordinate(getSelectedText('toponimoSedeOperativa')+' '+document.getElementById('viaStabinput').value+', '+document.getElementById('civicoSedeOperativa').value, document.getElementById('searchcodeIdComuneStabinput').value, getSelectedText('searchcodeIdprovinciaStab'), document.getElementById('capStab').value, document.getElementById('latStab'), document.getElementById('longStab'));" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>COORDINATE</td>
		<td>LAT <input type="text" name="latStab" id="latStab"  value="<%=((StabilimentoOpu.getSedeOperativa().getLatitudine()>0)?(StabilimentoOpu.getSedeOperativa().getLatitudine()):("")) %>"
			class="required" onChange="controllaCoordinate(this, 'lat')" /> LONG
			<input type="text" name="longStab" id="longStab"  value="<%=((StabilimentoOpu.getSedeOperativa().getLongitudine()>0)?(StabilimentoOpu.getSedeOperativa().getLongitudine()):("")) %>"
			class="required" onChange="controllaCoordinate(this, 'long')" />
			
			<script>
			inizializzaCoordinate('latStab', 'lat');
			inizializzaCoordinate('longStab', 'long');
			</script>
			
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
        $('#dataInizio').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 15,  showOnFocus: false, showTrigger: '#calImg',onClose: controlloDate}); 
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



<script type="text/javascript">
	document.getElementById('toponimoSedeOperativa').disabled=true;
	
	function abilitaDisabilitaIndirizzo(tipo)
	{
		if(tipo=='sedelegale')
		{
			var abilitare = document.getElementById('toponimoSedeLegale').disabled==true;
			if(abilitare && document.getElementById('nazioneSedeLegale').value!='106')
			{
				document.getElementById('toponimoSedeLegale').disabled=!abilitare;
				document.getElementById('civicoSedeLegale').readOnly=!abilitare;
				document.getElementById('presso').readOnly=!abilitare;
				document.getElementById('viainput').readOnly=!abilitare;
				document.getElementById('codeIdComune').readOnly=!abilitare;
			}
			else if(!abilitare && document.getElementById('nazioneSedeLegale').value=='106')
			{
				document.getElementById('toponimoSedeLegale').disabled=!abilitare;
				document.getElementById('civicoSedeLegale').readOnly=!abilitare;
				document.getElementById('presso').readOnly=!abilitare;
				document.getElementById('viainput').readOnly=!abilitare;
				document.getElementById('codeIdComune').readOnly=!abilitare;
			}
			document.getElementById('toponimoSedeLegale').value=-1;
			document.getElementById('civicoSedeLegale').value='';
			document.getElementById('presso').value='';
			document.getElementById('viainput').value='';
			document.getElementById('searchcodeIdprovinciaSigla').value='';
			document.getElementById('codeIdComune').value='';
		}
		else if(tipo=='residenzaRappLegale')
		{
			var abilitare = document.getElementById('toponimoResidenza').disabled==true;
			if(abilitare && document.getElementById('nazioneResidenza').value!='106')
			{
				document.getElementById('toponimoResidenza').disabled=!abilitare;
				document.getElementById('civicoResidenza').readOnly=!abilitare;
				document.getElementById('capResidenza').readOnly=!abilitare;
				document.getElementById('addressLegaleLine1input').readOnly=!abilitare;
				document.getElementById('addressLegaleCitta').readOnly=!abilitare;
			}
			else if(!abilitare && document.getElementById('nazioneResidenza').value=='106')
			{
				document.getElementById('toponimoResidenza').disabled=!abilitare;
				document.getElementById('civicoResidenza').readOnly=!abilitare;
				document.getElementById('capResidenza').readOnly=!abilitare;
				document.getElementById('addressLegaleLine1input').readOnly=!abilitare;
				document.getElementById('addressLegaleCitta').readOnly=!abilitare;
			}
			document.getElementById('toponimoResidenza').value=-1;
			document.getElementById('civicoResidenza').value='';
			document.getElementById('capResidenza').value='';
			document.getElementById('addressLegaleLine1input').value='';
			document.getElementById('addressLegaleCountrySigla').value='';
			document.getElementById('addressLegaleCitta').value='';
		}
	}
	
</script>


<!-- <%//@ include file="../opu/campi_mobile_add.jsp"%> -->