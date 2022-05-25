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
function checkFormArchivio(ricercaArchivio) {
	var ricercaStringa = ricercaArchivio.ricercaStringa.value;

	var errorString='';
	
		if (ricercaStringa==''){
			errorString+='\n Campo obbligatorio!';
			alert(errorString);
			return false;
		}
	
		loadModalWindow();
		ricercaArchivio.submit();
		return true;
	
	}

</script>
	
	
	<form name="ricercaArchivio" action="GestioneBacheca.do?command=RicercaInArchivio" method="post">
	
	 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
      <th colspan="2">
        <img border="0" src="images/folder.gif" align="absmiddle"><b> Ricerca </b>
      </th>
    </tr>
    
    <tr><td class="formLabel" nowrap>Portata</td><td>
    <select name="ricerca">
 <option value="tutto">Tutto</option>
<option value="archivi">Cerca nel titolo degli archivi</option>
<option value="documenti">Cerca nell'oggetto dei documenti</option>
</select>
    
    
    </td></tr>
			<tr>
			<td class="formLabel" nowrap>
			 Ricerca
			</td>
			<td><input type="text" id="ricercaStringa" name="ricercaStringa" size="50" value=""/> <font color="red">*</font>
			</td></tr>
			<tr><td>
			<input type="button" onClick="checkFormArchivio(this.form)" value="Ricerca in archivio">
		</td>
		</tr>
			
		</table>
		
	<%--input type="button"
		value="Annulla"
		onClick="window.location.href='GestioneBacheca.do?command=ListaAllegati&storeId=<%=request.getParameter("storeId")%>&folderId=<%=request.getParameter("folderId")%>&parentId=<%=request.getParameter("parentId")%>&grandparentId=<%=request.getParameter("grandparentId")%>';this.form.dosubmit.value='false';" /--%>
	
	
	</form>
	
	
	