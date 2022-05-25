<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%@page import="it.us.web.util.vam.CaninaRemoteUtil"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.util.Date"%>

<form action="vam.bdr.felina.Smarrimento.us" method="post">

	<input type="submit" value="Procedi" onclick="return myConfirm('Sicuro di voler procedere col salvataggio della registrazione di smarrimento in BDR?');" />
	
	<fieldset>
		<legend>Registrazione Smarrimento ${accettazione.animale.lookupSpecie.description } ${accettazione.animale.identificativo }</legend>
		
		<input type="hidden" name="idAccettazione" value="${accettazione.id }" />
		
		<table class="tabella">
			<tr class="even" >
				<td>Data Smarrimento</td>
				<td>
					<input 
						type="text" 
						value="<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" />"
						id="dataSmarrimento" 
						name="dataSmarrimento" 
						maxlength="10" 
						size="10" 
						readonly="readonly" />
						<img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
						<script type="text/javascript">
							Calendar.setup({
								inputField  :    "dataSmarrimento",      // id of the input field
								ifFormat    :    "%d/%m/%Y",  // format of the input field
								button      :    "id_img_1",  // trigger for the calendar (button ID)
								// align    :    "rl,      // alignment (defaults to "Bl")
								singleClick :    true,
								timeFormat	:   "24",
								showsTime	:   false
							});					    
						</script>
				</td>
			</tr>
			<tr class="odd" >
				<td>Luogo Smarrimento</td>
				<td> <input type="text" size="30" maxlength="20" name="luogoSmarrimento" /> </td>
			</tr>
			<tr class="even" >
				<td>Sanzione</td>
				<td>	
					<input type="checkbox" name="sanzione" id="sanzione" onclick="toggleDiv('importoSanzioneDiv')"/>	
					<div id="importoSanzioneDiv" style="display: none"> 
						Importo Sanzione
						<input type="text" name="importoSanzione" size="10" maxlength="7" onchange="isDecimalePositivo(this.value,'Importo Sanzione',this);" />
					</div>
				</td>
			</tr>
			<tr class="even" >
				<td>Note sullo Smarrimento</td>
				<td>
					<textarea rows="3" cols="30" name="noteSmarrimento"  maxLength="300" ></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	
	<br/>
	<input type="submit" value="Procedi" onclick="return myConfirm('Sicuro di voler procedere col salvataggio della registrazione di smarrimento in BDR?');" />
	
</form>
