<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,java.io.IOException,org.aspcfs.modules.opu.base.*,java.util.Date,com.sun.org.apache.xerces.internal.impl.dv.util.Base64,java.io.ByteArrayOutputStream,javax.imageio.ImageIO"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>
<jsp:useBean id="thisAnimale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />
<jsp:useBean id="close" class="java.lang.String" scope="request"/>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/modalWindow.js"> </script>
	
<script type="text/javascript">
<%if (close != null && !("").equals(close)){%>
window.opener.location = 'AnimaleAction.do?command=SearchForm';
window.close();
<%}%>
</script>

<script>function checkForm(form){

loadModalWindow(); //ATTENDERE PREGO     
form.submit();
}
</script>


<form name="detailsAnimale" id="detailsAnimale"
	action="AnimaleAction.do?command=Delete&id=<%=thisAnimale.getIdAnimale()%><%=addLinkParams(request, "popup|popupType|actionId")%>"
	method="post"><dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="AnimaleAction.do"><dhv:label
				name="">Animale</dhv:label></a> > <dhv:label
				name="accounts.SearchResults">Cancella</dhv:label></a> > <dhv:label
				name="">Conferma</dhv:label></td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate> 
<div style="text-align:center;">
	<dhv:permission name="anagrafe_canina-note_internal_use_only-add">
		<table align="center" cellpadding="4" cellspacing="0" border="0" width="50%"
			class="details">
			<tr>
				<th colspan="2"><strong>NOTE USO INTERNO</strong></th>
			</tr>
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
				</td>
				<td><textarea name="noteInternalUseOnly" rows="3" cols="50"><%=toString(thisAnimale.getNoteInternalUseOnly())%></textarea>
				</td>
			</tr>
		</table>
	</dhv:permission>	
	</br>
<input type="hidden" name="idAnimale" id="idAnimale" value="<%=thisAnimale.getIdAnimale() %>"/>
<input type ="button" value="Conferma Cancellazione" name="save" id="save" onClick="this.disabled=1; javascript:checkForm(this.form)"/>
</div>
</form>





