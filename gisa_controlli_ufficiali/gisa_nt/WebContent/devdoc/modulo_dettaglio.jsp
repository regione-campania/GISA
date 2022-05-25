<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.devdoc.base.Modulo"%>
<%@page import="org.aspcfs.modules.devdoc.base.ModuloNote"%>

<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoModulo"%>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoModuloList"%> 

<jsp:useBean id="Modulo" class="org.aspcfs.modules.devdoc.base.Modulo" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="listaTipiModulo" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="listaAllegati" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoModuloList" scope="request"/>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>


<style>

.lettura {
 background-color: transparent; 
 border:none;
}

</style>






 <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null || timestring.equals("null"))
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto;
	  return toRet;
	  
  }%>
  <%! public static String fixStringa(String nome)
  {
	  String toRet = nome;
	  if (nome == null || nome.equals("null"))
		  return toRet;
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll(" ", "_");
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>



<%@ include file="../initPage.jsp" %>


<table class="trails" cellspacing="0">
<tr>
<td>
<a href="GestioneFlussoSviluppo.do?command=Dashboard">Richiesta documentale sviluppo software</a> > 
Dettaglio Modulo
</td>
</tr>
</table>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Informazioni Modulo</strong>
    </th>
  </tr>
  
 	 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Richiesta
			</td>
			<td>
         		<%= Modulo.getIdFlusso() %>&nbsp;
			</td>
		</tr>  
		

	
	 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Documenti
			</td>
			<td>
			
			<table width="100%">
					
			<tr>
			<th>Nome</th>
			<th>Progressivo</th>
			<th>Caricato da</th>
			<th>Caricato il</th>
			</tr>
			
         	<%
	
	if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleAllegatoModulo doc = (DocumentaleAllegatoModulo) listaAllegati.get(i);
			%>
				
			<tr <%=(i==0) ? "style=\"background:yellow\"" : "" %>>
			<td>
			<a href="GestioneAllegatiUpload.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>&tipoDocumento=<%=doc.getEstensione()%>&nomeDocumento=<%=fixStringa(doc.getNomeClient())%>">
			<%=doc.getNomeClient() %></a>
			</td>
			<td><%=doc.getRevisione() %></td>
			<td><dhv:username id="<%= doc.getUserId()%>" /></td>
			<td><%=fixData(doc.getDataCreazione()) %></td>
			
			<%
				
		} else {%>
					<tr>
			<td colspan="4">Non sono presenti file in questa cartella.</td> 
		</tr>
		<%}%>
		</table>
		
			</td>
		</tr>
	
					
 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Data
			</td>
			<td>
         		<%= toDateasString(Modulo.getData()) %>&nbsp;
			</td>
		</tr>



 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Utente
			</td>
			<td>
         		<dhv:username id="<%= Modulo.getIdUtente() %>" /> &nbsp;
			</td>
		</tr>
	
		</table>
		
		
		<br/><br/>       
       
       
<form id = "modificaNote" name="modificaNote" action="GestioneFlussoSviluppo.do?command=UpdateNote&auto-populate=true" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Note</strong>
    </th>
  </tr>
 <% 
boolean noteInserite = false;
for (int i = 0; i<Modulo.getNote().size(); i++){
	  ModuloNote note = (ModuloNote) Modulo.getNote().get(i);
	  %>
  
<tr class="containerBody">
<td nowrap class="formLabel">Inserite da</td>
<td><dhv:username id="<%= note.getIdUtente() %>" /> <%= toDateWithTimeasString(note.getDataInserimento()) %></td>
</tr>  

<tr class="containerBody">
<td nowrap class="formLabel">Note</td>
<td><textarea readonly rows="3" cols="100" class="lettura" id="note<%=note.getIdUtente()%>" name = "note<%=note.getIdUtente()%>"><%= note.getNote() %></textarea>

<% if (note.getIdUtente()== User.getUserRecord().getId()) {
noteInserite = true;
%>
<dhv:permission name="devdoc-edit">
<input type="button" id="modifica<%=note.getIdUtente()%>" name = "modifica<%=note.getIdUtente()%>" value="modifica" onClick="modificaCampoNote('<%=note.getIdUtente()%>')"/>
<input type="button" style="display:none" id="salva<%=note.getIdUtente()%>" name = "salva<%=note.getIdUtente()%>" value="SALVA" onClick="salvaCampoNote(this.form, '<%=note.getIdUtente()%>')"/>
</dhv:permission>
<%}%>

</td>
</tr>   
 <% } %>

<% if (!noteInserite) {%>
<tr class="containerBody" id="trAggiungiNote<%=User.getUserRecord().getId()%>" name="trAggiungiNote<%=User.getUserRecord().getId()%>" style="display:none">
<td nowrap class="formLabel">
Note</td>
<td> 
<dhv:username id="<%=  User.getUserRecord().getId() %>" /><br/><br/>
<textarea rows="3" cols="180" id="note<%= User.getUserRecord().getId()%>" name = "note<%= User.getUserRecord().getId()%>"><%= "" %></textarea><br/>
<input type="button" id="salva<%=User.getUserRecord().getId()%>" name = "salva<%=User.getUserRecord().getId()%>" value="SALVA" onClick="salvaCampoNote(this.form, '<%=User.getUserRecord().getId()%>')"/>
</td>
</tr>
<tr><td>
<dhv:permission name="devdoc-edit">
<input type="button" id="aggiungiNote<%=User.getUserRecord().getId()%>" name="aggiungiNote<%=User.getUserRecord().getId()%>" value="aggiungi" onClick="aggiungiCampoNote('<%=User.getUserRecord().getId()%>')"/>
</dhv:permission>
</td></tr>
<%}%>

</table>
<input type="hidden" id="idUtente" name="idUtente" value=""/>
<input type="hidden" id="idModulo" name="idModulo" value="<%=Modulo.getId()%>"/>
 
</form>    
	