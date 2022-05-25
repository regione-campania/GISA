<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<jsp:useBean id="ServizioCompetente" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupTipoAttivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Carattere" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="indirizzoAdded" class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="IterList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="Address" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinciaAsl"  class="org.aspcfs.modules.accounts.base.Provincia" scope="request" />


<%@ include file="../initPage.jsp"%>


<script>
function selCarattere()
{
carattere = document.getElementById("carattere").value;

if (carattere=='1')
	{
	document.getElementById('dal').style.display="";
	document.getElementById('al').style.display="";
	
	}
else
	{
	
	document.getElementById('dal').style.display="none";
	document.getElementById('al').style.display="none";
	document.getElementById('dateI').value = "";
	document.getElementById('dateF').value = "";
	}
	

}

function selTipoAttivita()
{
carattere = document.getElementById("tipo_attivita").value;

if (carattere=='1') // attivita FISSA
	{
	document.getElementById("label_sede").innerHTML="Sede Operativa";
	document.getElementById('sedestabilimento').style.display = "";
	document.getElementById('rappstabilimento').style.display = "";
	//document.getElementById('targa').style.display = "none";
	
	
	}
else
	{
	if (carattere=='2') // attivita MOBILE
	{
	location.href='Accounts.do?command=Add';
	
	}
	else
		{
		document.getElementById('sedestabilimento').style.display = "none";
		document.getElementById('rappstabilimento').style.display = "none";
		document.getElementById('via').value = "-1";
		document.getElementById('codFiscaleSoggetto').value = "n.d";
		//document.getElementById('targa').style.display = "none";

		}
	
	
	}
	

}
</script>


<br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
	<tr>
		<th colspan="2">
			<strong><dhv:label name="">Informazioni Stabilimento</dhv:label></strong>
		</th>
	</tr>
	
	<tr>
			<td class="formLabel" nowrap>
				Tipo Attivita
			</td>
			<td>
			<%LookupTipoAttivita.setJsEvent("onChange=\"selTipoAttivita();\""); 
			int idTipoAttivita = -1 ;
			int idCarattere= -1 ;
			int serviziocompetente = -1 ;
			String codInterno = "" ;
			String dataPResentazione = "" ;
			boolean isflagVendita=false ; 
			if ( newStabilimento.getListaLineeProduttive().size()>0 && ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getInfoStab() != null)
			{
				idTipoAttivita =   ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getInfoStab().getTipoAttivita();
				idCarattere = ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getInfoStab().getCarattere();
				serviziocompetente = ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getInfoStab().getServizioCompetente();
				codInterno = ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getInfoStab().getCodice_interno();
				dataPResentazione = ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getInfoStab().getDataPresentazioneSciaString();
				isflagVendita = ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getInfoStab().isFlag_vendita_canili();
			}
			%>
			<%= LookupTipoAttivita.getHtmlSelect("tipo_attivita",  idTipoAttivita) %>
			</td>
		</tr>
		
		<tr>
			<td class="formLabel" nowrap>
				Carattere
			</td>
			<td>
			<%Carattere.setJsEvent("onChange=\"selCarattere();\""); %>
			<%= Carattere.getHtmlSelect("carattere", idCarattere) %>
			</td>
		</tr>
		<tr id = "dal" <%if (idCarattere==1){ %><%}else{%>style="display: none"<%} %>>
			<td class="formLabel" nowrap>
				DaL
			</td>
			<td>
			
			<input readonly type="text" id="dateI" name="dateI" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dateI,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
			</td>
		</tr>
		
		<tr id = "al" <%if (idCarattere==1){ %><%}else{%>style="display: none"<%} %>>
			<td class="formLabel" nowrap>
				AL
			</td>
			<td>
			
			<input readonly type="text" id="dateF" name="dateF" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dateF,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
			</td>
		</tr>
		
		<tr>
			<td class="formLabel" nowrap>
				Servizio Competente
			</td>
			<td>
			<%= ServizioCompetente.getHtmlSelect("servizio_competente", serviziocompetente) %>
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				Codice Impresa Interno
			</td>
			<td>
				<input type="text" size="30" maxlength="50" name="codice_interno" value="<%=toHtmlValue(codInterno) %>">
			</td>
		</tr>
	    <tr>
    		<td class="formLabel" nowrap>
      			Data Presentazione Dia / Inizio Attivita
    		</td>
    		<td>
	<input readonly type="text" id="dataPresentazioneScia" name="dataPresentazioneScia" size="10" value = "<%=dataPResentazione%>" />
		<a href="#" onClick="cal19.select(document.forms[0].dataPresentazioneScia,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
      </td>
  		</tr>
  		
  		 <tr>
    		<td class="formLabel" nowrap>
      			Data Fine Attivita
    		</td>
    		<td>
	<input readonly type="text" id="dataFineAttivita" name="dataFineAttivita" size="10" value = "<%=dataPResentazione%>" />
		<a href="#" onClick="cal19.select(document.forms[0].dataFineAttivita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
      </td>
  		</tr>
  
       <tr>
    		<td class="formLabel" nowrap>
      			Vendita con canali non convenzionali
    		</td>
    		<td>
     			<input type = "checkbox" value = "1" name = "flag_vendita" <%if(isflagVendita){%>checked="checked"<%} %>>
    		</td>
  		</tr>
  		<tr>
			<td class="formLabel" nowrap>
				Domicilio Digitale
			</td>
			<td>
			<input type = "text" name = "domicilio_digitale">
			</td>
		</tr>
		
   	
  		
		
		
		

</table>