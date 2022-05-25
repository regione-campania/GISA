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

<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>

<!--<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
-->
<%@ include file="../initPage.jsp"%>

	<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/SuapDwr.js"> </script>
<SCRIPT src="javascript/suapCittadinoUtil.js"></SCRIPT>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>


<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script> function checkForm(tot, form){
	var x = document.getElementsByName("idLineaProduttiva");
	
	//alert("vvvvv "+x.length+ " - tot: "+parseInt(tot-1));
	
	var totModificati = x.length;
	if (totModificati!=parseInt(tot))
		{
		alert('Selezionare la nuova linea per ogni vecchia linea di attività!');
		}
	else{
		loadModalWindow();
		form.submit();
	}
	
}
</script>


	
	<dhv:evaluate if="<%=(StabilimentoDettaglio.getListaLineeProduttive().size() > 0)%>">
	
	
	
	<table width="100%" style="border:1px solid black">
	<tr><th></th></tr>
	<tr><td>
	
<form name="searchAccount" id = "searchAccount" action="Accounts.do?command=UpdateLineePregresse" method="post">	

<input type="hidden" id="tipo_impresa" name="tipo_impresa" value="<%=StabilimentoDettaglio.getTipoAttivita()%>"/>
<input type="hidden" id="id_impresa" name="id_impresa" value="<%=request.getParameter("orgId")%>"/>

  <%
	ArrayList<LineeAttivita> linee_attivita = (ArrayList<LineeAttivita>) request.getAttribute("linee_attivita");
    ArrayList<String> nuove_linee = (ArrayList<String>)request.getAttribute("nuove_linee");
    
    String [] arrayIDLivelli = new String[linee_attivita.size()+1];
    String [] arrayDESCLinea = new String[linee_attivita.size()+1];
     
    int indice = 1;
	//while (itLplist.hasNext()) {
	//	LineaProduttiva lp = itLplist.next();
	for (int i=0; i< linee_attivita.size();i++){
	  //LineeAttivita linea_attivita_principale = (LineeAttivita) request.getAttribute("linea_attivita_principale");
	  LineeAttivita l = linee_attivita.get(i);
  %>
  <input type="hidden" id="vecchiaLineaId<%=indice-1 %>" name ="vecchiaLineaId<%=indice-1 %>" value="<%=l.getId()%>"/> 
  <table width="100%"><col width="50%">
    <tr><td valign="top">
    <table cellpadding='4' cellspacing="0" border="0" width="100%" class="details">
	  <tr><th colspan='2'>Vecchia Linea <%=indice %></th></tr>
      <dhv:evaluate if="<%= hasText( l.getCategoria() ) && hasText( l.getCategoria() ) %>">
      <tr >
	    <td nowrap class="formLabel">
      	  <dhv:label name="">Codice Ateco/Linea di Attivita Principale</dhv:label>
		</td>
		<dhv:evaluate if="<%= hasText( l.getCategoria() ) && hasText( l.getCategoria() ) %>">
		<td>
	      <%= toHtml( l.getCodice_istat() + " " + l.getDescrizione_codice_istat()) %> <br/>
	      <%= toHtml( l.getCategoria() + " - " + l.getLinea_attivita() ) %>&nbsp;
	
		</td>
		</dhv:evaluate>
  	  </tr>
  	  </dhv:evaluate>
  	  <%
  	String nuova_linea="";
  	String desc_linea="";
    
  	
  	
	  if (!nuove_linee.get(i).equals("")){
  		out.print(nuove_linee.get(i).split("---")[0]);  
  	    desc_linea=nuove_linee.get(i).split("---")[2];
  		nuova_linea=nuove_linee.get(i).split("---")[1];
  	  }
		arrayIDLivelli[indice]=nuova_linea;
		arrayDESCLinea[indice]=desc_linea;
		
		  	  
  	  %>
    </table>
	</td><td valign="top">
     <!-- LINEA ATTIVITA -->
       <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">    
         <tr><th colspan="2"><strong><dhv:label name="">Nuova Linea <%=indice %></dhv:label></strong> </th> </tr>
         <tr>
           <td nowrap class="formLabel" >
     	     <dhv:label name="">Linea</dhv:label>
   		   </td> 
    	   <td> 
             <input type ="hidden" value = "false" id = "validatelp" value = "false">
	         <table id = "attprincipale<%=indice %>" style="width: 100%;"></table>
			</td>
  		 </tr>
       </table>
    </td>
    </tr>
	</table><br/>	
	<%	indice++;	} indice--; %>
    <%for (int j = 1; j<=indice; j++){   
    	if (arrayIDLivelli[j].equals("")){
    %>
         <script>mostraAttivitaProduttive('attprincipale<%=j %>',1,-1, false,-1);</script>
    <% }else{
    	int k=1;
    	String [] arrayID = arrayIDLivelli[j].split("&&&");
    	 // Se è nodo foglia ed è autosettato, gli creo un input hidden con il valore dell'id perchè lato java non riesce a recuperarlo
     	if (Boolean.parseBoolean(arrayID[0])){ 
     		for(k=1;k<arrayID.length;k++);	
     		%>
            <script>mostraAttivitaProduttive('attprincipale<%=j %>',1,-1, false,-1);</script>
     		<script>mostraAttivitaProduttive('attprincipale<%=j %>',<%=k %> ,<%=arrayID[k-1] %>, false,-1);</script>
     	<%   %>
     	<input type='hidden' id='idLineaProduttiva_<%=j-1 %>' name='idLineaProduttiva_<%=j-1 %>' value='<%=arrayID[k-1]%>'></input>
     	<% }else{ 
     		for(k=1;k<arrayID.length;k++);
     		%>
     		 <script>mostraAttivitaProduttive('attprincipale<%=j %>',<%=k %>,<%=arrayID[k-1] %>, false,-1);</script>
     	<%	}%>
     	
        <script>
        <%
        String att= arrayDESCLinea[j];
        %>
        var ptr = $("#attprincipale<%=j %>").find("tr:first"); ptr.find("td:first").hide();
        ptr = $("#attprincipale<%=j %>").find("tr:first"); ptr.append("<td><%=arrayDESCLinea[j]%></td>");
        </script>
        <%
      } 
   }
	%>		

  <input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>		
  <center>
  <input class="yellowBigButton" type="button" value="AGGIORNA LINEE" onClick="checkForm('<%=indice%>', this.form)"/>
  </center>	
			
</form>			
</td></tr></table>	
</dhv:evaluate>
	 
	













  	
   