<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<jsp:useBean id="nomeCartellaOld" class="java.lang.String" scope="request"/>
<script type="text/javascript">
function checkFormRinominaCartella(formRinominaCartella) {
	if (formRinominaCartella.nomeCartella.value!=""){
		loadModalWindow();
		formRinominaCartella.submit();
		return true;
	}
	alert("Nome cartella obbligatorio.");
	return false;
	}

</script>
	
	
	<form name="rinominaCartella" action="GestioneBacheca.do?command=GestisciCartella" method="post">
	
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
      <th colspan="2">
        <img border="0" src="images/folder.gif" align="absmiddle"><b> Rinomina cartella</b>
      </th>
    </tr>
			<tr id="eventoId">
			<td class="formLabel" nowrap>
			<img src="gestione_documenti/images/new_folder_icon.png" width="30"/>  Nuovo nome cartella
			</td>
			<td><input type="text" id="nomeCartella" name="nomeCartella" size="50" value="<%=nomeCartellaOld%>"/>
		</td>
		</tr>
			
		</table>
	<input type="button" onClick="checkFormRinominaCartella(this.form)" value="Rinomina cartella">
	<input type="hidden" id="storeId" name="storeId" value="<%=request.getParameter("storeId")%>"/>
	<input type="hidden" id="folderId" name="folderId" value="<%=request.getParameter("folderId")%>"/>
	<input type="hidden" id="parentId" name="parentId" value="<%=request.getParameter("parentId")%>"/>
	<input type="hidden" id="idCartella" name="idCartella" value="<%=request.getParameter("idCartella")%>"/>
	<input type="hidden" id="operazione" name="operazione" value="rinomina"/>
	<input type="hidden" id="rinominata" name="rinominata" value="si"/>
	<input type="hidden" name="op" id="op" value="<%= (String)request.getAttribute("op") %>" />
	
	<input type="button"
		value="Annulla"
		onClick="window.location.href='GestioneBacheca.do?command=ListaAllegati&storeId=<%=request.getParameter("storeId")%>&folderId=<%=request.getParameter("folderId")%>&parentId=<%=request.getParameter("parentId")%>';" />
	
	
	</form>
	
	
	