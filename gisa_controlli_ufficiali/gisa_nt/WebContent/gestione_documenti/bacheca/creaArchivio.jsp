<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<script type="text/javascript">
function checkFormArchivio(formArchivio) {
	var nomeArchivio = formArchivio.nomeArchivio.value;
	var descrizioneArchivio = formArchivio.descrizioneArchivio.value;
	var dataInizio = formArchivio.dataInizio.value;
	
	var errorString='';
	
	if (nomeArchivio=='' || descrizioneArchivio == '' || dataInizio == ''){
		if (nomeArchivio=='')
			errorString+='\n Nome obbligatorio!';
		if (descrizioneArchivio=='')
			errorString+='\n Descrizione obbligatoria!';
		if (dataInizio=='')
			errorString+='\n Data inizio obbligatoria!';
		alert(errorString);
		return false;
	}
	
		loadModalWindow();
		formArchivio.submit();
		return true;
	
	}

</script>
	
	
	<form name="newArchivio" action="GestioneBacheca.do?command=CreaNuovoArchivio" method="post">
	
	 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
      <th colspan="2">
        <img border="0" src="images/folder.gif" align="absmiddle"><b> Crea nuovo archivio</b>
      </th>
    </tr>
			<tr>
			<td class="formLabel" nowrap>
			 Nome archivio
			</td>
			<td><input type="text" id="nomeArchivio" name="nomeArchivio" size="50" value=""/> <font color="red">*</font>
			</td></tr>
			<tr>
			<td class="formLabel" nowrap>
			 Descrizione breve
			</td>
			<td><input type="text" id="descrizioneArchivio" name="descrizioneArchivio" size="50" value=""/> <font color="red">*</font>
			</td></tr>
			<tr>
			<td class="formLabel" nowrap>
			Data inizio
			</td>
			<td>

<input readonly type="text" id="dataInizio" name="dataInizio" size="10" />
		<a href="#" onClick="cal19.select(document.newArchivio.dataInizio,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
		</a>
		
<font color="red">*</font>
			</td></tr>
			<tr>
			<td class="formLabel" nowrap>
			Stato
			</td>
			<td><input type="checkbox" id="statoArchivio" name="statoArchivio" value="ok" checked="checked" onClick="return false"/> Approvato
			</td></tr>
			
			<dhv:permission name="documentale-bacheca-priorita-view">
			<tr>
			<td class="formLabel" nowrap>
			Priorit?
			</td>
			<td><select id="prioritaArchivio" name="prioritaArchivio">
			<option value="100">ALTA</option>
			<option value="50" selected>MEDIA</option>
			<option value="1">BASSA</option>
			</select>
			</td></tr>
			</dhv:permission>
					
			<tr><td>
			<input type="button" onClick="checkFormArchivio(this.form)" value="Crea archivio">
		</td>
		</tr>
			
		</table>
		
	<%--input type="button"
		value="Annulla"
		onClick="window.location.href='GestioneBacheca.do?command=ListaAllegati&storeId=<%=request.getParameter("storeId")%>&folderId=<%=request.getParameter("folderId")%>&parentId=<%=request.getParameter("parentId")%>&grandparentId=<%=request.getParameter("grandparentId")%>';this.form.dosubmit.value='false';" /--%>
	
	
	</form>
	
	
	