<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<table style="top:0;left: 0;width:100%;height: 100%; " cellpadding="5">
<tr>
<td style="vertical-align: top; position: relative;" align="center">
<iframe 
	scrolling="no" 
	src="GestioneAnagraficaAction.do?command=TemplateDetails&altId=${altId}" 
	id="dettaglioTemplate" 
	style="width: 100%; height: 100%; border: none; " 
	onload="this.style.height=(this.contentDocument.body.scrollHeight)+'px';"></iframe>
</td>
</tr>
</table>
