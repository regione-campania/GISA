<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>

<%@ include file="../initPage.jsp"%>

<script>
function chiamaAction(stringa1){
	var scroll = document.body.scrollTop;
	location.href=stringa1+scroll;
}
</script>
<%if(StabilimentoDettaglio.getListaLineeProduttive().size()>0 && ((LineaProduttiva)StabilimentoDettaglio.getListaLineeProduttive().get(0)).getInfoStab().getTipoAttivita()==3){ %>

<p>
			Utilizzare le caselle vuote sopra l'intestazione per filtrare per anno, richiedente, impresa, capo e/o rapporto
		</p>
	
		
		
		<%
		if(request.getAttribute("errore")!=null){
			
			%>
			<font color="red">
			<%=request.getAttribute("errore")+" Utilizzare il seguente formato (gg/mm/yyyy) <br>" %>
			
			</font>
			
			<% 
			
		} 
		
		%>
		 
		
		 
		
	<%
	String aggiunto="false";
	if(request.getAttribute("aggiunto")!=null)
	 aggiunto=(String)request.getAttribute("aggiunto");
	
	%>

		
	 <%
  Integer numeroDistributori =(Integer) request.getAttribute("numeroDistributori");
  
  %>
		<a href="javascript:chiamaAction('DistributoriListOpu.do?command=AddOpu&orgId=<%=StabilimentoDettaglio.getIdStabilimento() %>&maxRows=15&15_sw_=true&15_tr_=true&15_p_=<%=numeroDistributori %>&15_mr_=15&scroll=')">Inserisci Distributore se non importato da file</a>
		
		<form name="aiequidiForm" action="DistributoriListOpu.do?command=DetailsOpu&orgId=<%=StabilimentoDettaglio.getIdStabilimento() %>&aggiunto=<%=aggiunto %>">
		<input type="hidden" name="orgId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>">
	     
	     <%if(request.getAttribute("add")!=null) {%>
	     <input type="hidden" name="add" value="add">
	     
	     <%} %>
	     
	     
	      <jmesa:htmlColumn property="chkbox" title=" " worksheetEditor="org.jmesa.worksheet.editor.CheckboxWorksheetEditor" filterable="false" sortable="false"/>
	     
	     <%if(request.getAttribute( "tabella" )!=null) {%>
	       <%=request.getAttribute( "tabella" )%>
	       <%} %>
	    <jmesa:tableFacade editable="true" >   <jmesa:htmlRow uniqueProperty="matricola">   </jmesa:htmlRow></jmesa:tableFacade>
	    
	    <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
				
                 location.href = 'DistributoriListOpu.do?command=DetailsOpu&' + parameterString;
            }
    </script>
	    </form>
	   


<%} %>