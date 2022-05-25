<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcf.modules.controlliufficiali.action.Diffida"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%><jsp:useBean id="Ispezioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="ListaNorme" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<script language="JavaScript">


formTest = true;
checkNorma = true ;
message = "";
  function checkForm(form){
	  
	  var orgId = -1;
	  var idStabilimento = -1;
	  var altId = -1;
	  var idApiario = -1;
	  var idControllo = -1;
	  var idDiffida = -1;

	  if (form.orgId!=null)
		  orgId = form.orgId.value;
	  if (form.idStabilimento!=null)
		  idStabilimento = form.idStabilimento.value;
	  if (form.idApiario!=null)
		  idApiario = form.idApiario.value;
	  if (form.altId!=null)
		  altId = form.altId.value;
	  if (form.id!=null)
		  idDiffida = form.id.value;
	  if (form.idControllo!=null)
		  idControllo = form.idControllo.value;
	  
	  formTest = true;
    message = "";

arr=form.Provvedimenti;

 for(k=0 ; k<arr.length;k++){
		if(arr[k].value=="-1" && arr[k].selected==true){
			message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver selezionato una azione \r\n");
		      formTest = false;

			}
		

     }
 
if(form.Provvedimenti.value==""){
	 message += label("check.sanzioni.data_richiesta.selezionato","- Controllare che il campo \"Azione non conforme per\" sia stato popolato\r\n");
     formTest = false;

}


arr=form.listaNorme;
for(k=0 ; k<arr.length;k++){
	if(arr[k].value=="-1" && arr[k].selected==true){
		 	message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver selezionato una norma \r\n");
	      	formTest = false;

		}
	else
		{
		if( arr[k].selected==true){
	
		PopolaCombo.verificaEsistenzaDiffida( arr[k].value,idControllo,orgId,idStabilimento,idApiario, altId, idDiffida,{callback:verificaEsistenzaDiffidaCallback,async:false});
		
		
		}
		}
		}
	
	

 

if(form.listaNorme.value==-1){
	
    formTest = false;

}
    
	
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      loadModalWindow();
      return true;
    }
  }
  
  
  function verificaEsistenzaDiffidaCallback(val)
  {
	  if (val==true)
		  {
		 // formTest = false;
		 // message += label("check.sanzioni.listaNorme.selezionato","- L'operatore risulta già diffidato per questa norma.\r\n");
// 		 if (!confirm("Attenzione. Questo operatore risulta già diffidato per questa norma. E' possibile proseguire e diffidarlo nuovamente se sicuri della selezione.")){
// 			  formTest = false;
// 			  message += label("check.sanzioni.listaNorme.selezionato","- Ricontrollare la norma se non si è sicuri della selezione.\r\n");
			 
// 		 }
		  }
	  
  }
 
  var item ;
  function checkDiffida()
	{
	  form= document.forms[0];
	  arr=form.listaNorme;
	  for(k=0 ; k<arr.length;k++){
			if(arr[k].value=="-1" && arr[k].selected==true){
				 	
			      	formTest = false;

				}
			else
				{
				if( arr[k].selected==true){
					
				item = arr[k];
				PopolaCombo.verificaNormaInDiffida(arr[k].value,{callback:verificaNormaInDiffidaCallback,async:false});
				
				
				}
				}
				}

	}
	function verificaNormaInDiffidaCallback(retVal)
	{
	  if (val==true)
		  {
		 // formTest = false;
		 // message += label("check.sanzioni.listaNorme.selezionato","- L'operatore risulta già diffidato per questa norma.\r\n");
// 		 if (!confirm("Attenzione. Questo operatore risulta già diffidato per questa norma. E' possibile proseguire e diffidarlo nuovamente se sicuri della selezione.")){
// 			  formTest = false;
// 			  message += label("check.sanzioni.listaNorme.selezionato","- Ricontrollare la norma se non si è sicuri della selezione.\r\n");
			 
// 		 }
		  }
	  
	}
	
</script>

<%
ListaNorme.setJsEvent("onchange='checkDiffida()'");

%>
<%=showError(request, "listanormeError")%>
<%

String permission = TicketDetails.getPermission_ticket()+"-sanzioni-edit";

%>
<dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="<%=permission%>">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Diffida.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="button" value="Annulla" onClick="window.history.back();" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
<input type="button" value="Annulla" onClick="window.history.back();" />           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="<%=permission%>">
            <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
<input type="button" value="Annulla" onClick="window.history.back();" />           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
<input type="button" value="Annulla" onClick="window.history.back();" />           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="sanzioni.information">Scheda Diffida</dhv:label></strong>
      </th>
	</tr>
	<dhv:include name="" none="true">
	
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteIdList.getSelectedValue(TicketDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=TicketDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
 
</dhv:include>
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>
    
  </tr>
 
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
	<tr class="containerBody">
    <td valign="top" class="formLabel">
   Identificativo Non Conformità
    </td>
    <td>
     <%= TicketDetails.getIdentificativonc() %>
    </td>
  </tr>
	
	
  <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td  id="provvedimento1" nowrap class="formLabel">
       Azione non Conforme per
      </td>
    <td>
    
      <table border=0 class="noborder">
      <tr>
      <td >
     
        		<%= Provvedimenti.getHtmlSelect("Provvedimenti",TicketDetails.getProvvedimenti()) %>
        	
        
         
         <font color="red">*</font>
         <br><br>
       
    		</td>
    		   </tr>
       </table>
    </td>
  </tr>
</dhv:include>


<% if(TicketDetails.getNormaviolata()!=null && !TicketDetails.getNormaviolata().equals("")){
				//if(!TicketDetails.getNormaviolata().equals("")){ %>
				
	<tr class="containerBody">
  	<td nowrap class="formLabel">
      Norma Violata

   </td>
   <td>
      <%= TicketDetails.getNormaviolata() %>
      
   </td>
   </tr>
   <% } else { %>
	
	<tr class="containerBody"> 
    <td valign="top" class="formLabel">
     Norma Violata
    </td>
   <td>
   		  <%
       ListaNorme.setMultiple(true);
       ListaNorme.setSelectSize(9);
       
       %>
          <%
          LookupList selNorme = new LookupList();
          if(TicketDetails.getListaNorme().size()!=0) { 
       			%> 
				<%
						
						HashMap<Integer,String> listanorme =TicketDetails.getListaNorme();
						Set<Integer> setkiavi = listanorme.keySet();
						Iterator<Integer> iteraNorme=setkiavi.iterator();
						
						while(iteraNorme.hasNext()){
							int chiave = iteraNorme.next();
							String value=listanorme.get(chiave);					
						
							LookupElement elem = new LookupElement();
							elem.setCode(chiave);
							elem.setDescription(value);
							selNorme.add(elem);
							} %> 
			<%=ListaNorme.getHtmlSelect("listaNorme",selNorme) %>
			<% } else {
				%>
	   				<%=ListaNorme.getHtmlSelect("listaNorme",-1) %>
			<% } %>
			
	</td>
	
	
	
   </tr>
	   
   <% } %>
   
   





<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>
  </br>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
  
	</table>
	
	<dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="<%=permission%>">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Diffida.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
<input type="button" value="Annulla" onClick="window.history.back();" />         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
<input type="button" value="Annulla" onClick="window.history.back();" />         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="<%=permission%>">
          <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
<input type="button" value="Annulla" onClick="window.history.back();" />         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
<input type="button" value="Annulla" onClick="window.history.back();" />         </dhv:evaluate>
        <%= showAttribute(request, "closedError") %>
     </dhv:evaluate>
    </dhv:evaluate>
    <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
    <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getOrgSiteId() %>" />
    <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
    <input type="hidden" name="companyName" value="<%= toHtml(TicketDetails.getCompanyName()) %>">
    <input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
    <input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
    <input type="hidden" name="close" value="">
    <input type="hidden" name="refresh" value="-1">
    <input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
    <input type="hidden" name="altId" value="<%= TicketDetails.getAltId() %>">
    <input type="hidden" name="idControllo" value="<%= TicketDetails.getIdControlloUfficiale()%>">
    