<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.zeroio.taglib.PermissionHandler"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.passaporti.base.*,org.aspcfs.utils.web.*,java.util.*,java.text.DateFormat" %>
<jsp:useBean id="passaportoList" class="org.aspcfs.modules.passaporti.base.PassaportoList" scope="request"/>
<jsp:useBean id="aslRifList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupSpecie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PassaportoListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ include file="../initPage.jsp" %>
<%-- Trails --%>


<%

PermissionHandler p1 = new PermissionHandler();
  p1.setPageContext(pageContext);
  p1.setName("anagrafe_canina-anagrafe_canina-view");
  int permessoDettaglioAnimale = p1.doStartTag();
%>


<table class="trails" cellspacing="0">
<tr>
<td>
<%
if(permessoDettaglioAnimale==1)
{
%>
  <a href="Passaporto.do">Cerca passaporto</a> > 
  <dhv:label name="accounts.SearchResults">Search Results</dhv:label>
 <%
}
else
{
 %> 
  
  
  
  <a href="Passaporto.do?command=ListUOS">Distribuzione passaporti</a> > 
  <a href="Passaporto.do?command=ListUOS">Lista UOS</a> > 
Lista passaporti associabili a <%=(String)request.getAttribute("nome_uos") %>
 <%
}
 %> 
  


</td>
</tr>
</table>
<%-- End Trails --%>




<%
if(permessoDettaglioAnimale==1)
{
 if ( request.getAttribute("statistiche") != null ) { %>
<table cellpadding="4" cellspacing="0" border="0" width="50%" class="details">
	<tr>
		<th colspan="2">
			<strong>Statistiche</strong>
		</th>
	</tr>
	<tr>
		<td class="formLabel2"> Passaporti Totali (compresi quelli dichiarati fuori uso e quelli già assegnati) </td>
		<td>
			<%= request.getAttribute("totPassaporti") != null ? request.getAttribute("totPassaporti") : "" %>
		</td>
	</tr>
	<tr>
		<td class="formLabel2"> Passaporti Assegnati</td>
		<td>
			<%= request.getAttribute("totPassaportiAssegnati") != null ? request.getAttribute("totPassaportiAssegnati") : "" %>
		</td>
	</tr>
	<tr>
		<td class="formLabel2"> Passaporti Fuori Uso</td>
		<td>
			<%= request.getAttribute("totPassaportiFuoriUso") != null ? request.getAttribute("totPassaportiFuoriUso") : "" %>
		</td>
	</tr>
	<tr>
		<td class="formLabel2"> Passaporti Disponibili All'Utilizzo</td>
		<td>
			<%= request.getAttribute("totPassaportiDisponibili") != null ? request.getAttribute("totPassaportiDisponibili") : "" %>
		</td>
	</tr>
	
	
	
</table>
<br>
<% }} %> 





<%
if(permessoDettaglioAnimale!=1)
{
%>
		<form action="Passaporto.do?command=ListDistribuzionePassaporti" method="post">

			<input type="hidden" name="assegnato" id="assegnato" value="false">
			<input type="hidden" name="abilitato" id="abilitato" value="true">
			<input type="hidden" name="aslRif" id="aslRif" value="<%=(String)request.getAttribute("aslRif")%>">
			<input type="hidden" name="nome_uos" id="nome_uos" value="<%=((String)request.getAttribute("nome_uos")).replaceAll("\"","")%>">
			<input type="hidden" name="id_uos" id="id_uos" value="<%=(String)request.getAttribute("id_uos")%>">
			<input type="hidden" name="associarePassaporti" id="associarePassaporti" value="true">

        	<input type="submit" value="Associa passaporti alla UOS">
<%
}
%>


<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="PassaportoListInfo"/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th nowrap>
        <strong>Passaporto</strong>
      </th>
      <th nowrap>
        <strong>ASL</strong>
      </th>
      <th nowrap width="10%">
        <strong>Assegnato</strong>
      </th>
      <th nowrap width="10%">
        <strong>Abilitato</strong>
      </th>
      
      
      <%
if(permessoDettaglioAnimale!=1)
{
%>
	<th nowrap width="10%">
        <input type="checkbox" onclick="selezionaPassaporti(this.checked);"/> <strong id="labelSeleziona">Seleziona tutti</strong> 
      </th>
<%
}
%>
    </tr>
  <%
    Iterator itr = passaportoList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        Passaporto p = (Passaporto)itr.next();
        
        
    %>      
    <tr class="row<%= rowid %>">
      <td>
      <%
      if (p.getIdAnimale()>0 && (p.getDataCancellazione()==null) && permessoDettaglioAnimale==1){ %>
       <a href="AnimaleAction.do?command=Details&animaleId=<%=p.getIdAnimale() %>&idSpecie=<%=p.getIdSpecie()%>"> <%= toHtml(p.getNrPassaporto()) %>&nbsp;</a>
        <%}else{ %>
        <%= p.getNrPassaporto() %>&nbsp;
        <%}%>
      </td>
      <td>
        <%= aslRifList.getSelectedValue((p.getIdAslAppartenenza())) %>&nbsp;
      </td>
      <td width="10%">
        <%= (p.getIdAnimale()>0 &&  p.getDataCancellazione()==null && p.isFlagAbilitato()) ?  "SI : "+LookupSpecie.getSelectedValue(p.getIdSpecie()) :  "NO"  %>&nbsp;
      </td>
      <td width="10%">
        <%=  (p.isFlagAbilitato()) ?  "SI" : "NO"  %>&nbsp;
      </td>
      
            <%
if(permessoDettaglioAnimale!=1)
{
%>
	<td width="10%">
		<input type="checkbox" name="passaportiDaAssociare<%=p.getId()%>" id=passaportiDaAssociare<%=p.getId()%>" value="<%=p.getId()%>">
      </td>
<%
}
%>
      
    </tr>
    <%  
      }
    }else{
    %>
    <tr class="containerBody">
      <td colspan="3">
        Nessun passaporto trovato.
      </td>
    </tr>
    <%
    }
    %>
</table>
<%
if(permessoDettaglioAnimale!=1)
{
%>
		</form>
<%
}
%>
<br>
<dhv:pagedListControl object="PassaportoListInfo"/>


<script type="text/javascript">
function selezionaPassaporti(tipo)
{
	var elementi = document.getElementsByTagName("input");
	for(var i=0;i<elementi.length;i++)
	{
		var elemento = elementi[i];
		if(elemento.name.indexOf('passaportiDaAssociare')>=0)
			elemento.checked=tipo;
			
	}
	var labelSeleziona = "Deseleziona tutti";
	if(!tipo)
		labelSeleziona = "Seleziona tutti";
	document.getElementById("labelSeleziona").innerHTML=labelSeleziona;
}


</script>
