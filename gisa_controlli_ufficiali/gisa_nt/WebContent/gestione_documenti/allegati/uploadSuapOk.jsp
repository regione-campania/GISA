<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%="File Caricato consuccess : "+request.getAttribute("titolo")+" __ "+request.getAttribute("indice")%>

<script>

window.opener.document.getElementById("fileAllegato<%=request.getAttribute("indice")%>").innerHTML="<a href=\"GestioneAllegatiUploadSuap.do?command=DownloadPDF&codDocumento=<%=request.getAttribute("codDocumento")%>&nomeDocumento=<%=request.getAttribute("titolo")%>\"><b>Scarica File</b></a>  [<a onclick=\"cancellaFile(this,'GestioneAllegatiUploadSuap.do?command=GestisciFile&orgId=-1&stabId=<%=request.getAttribute("stabId") %>&ticketId=-1&folderId=-1&parentId=-1&idHeader=<%=request.getAttribute("codDocumento")%>&operazione=cancella&op=suap',<%=request.getAttribute("indice")%>)\" href=\"#\"><img src=\"gestione_documenti/images/delete_file_icon.png\" width=\"20\"/><b>Cancella</b></a>]";

window.opener.document.getElementById("allegato<%=request.getAttribute("indice")%>").value="1";
window.opener.document.getElementById("documentazione_parziale").value="1";

window.opener.document.getElementById('linkallegato<%=request.getAttribute("indice")%>').style.display="none";



window.close();

</script>