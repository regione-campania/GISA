<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@page import="org.aspcf.modules.checklist_benessere.base.Capitolo"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcf.modules.checklist_benessere.base.Domanda"%><jsp:useBean id="Allevamento" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Capitolo" class="org.aspcf.modules.checklist_benessere.base.Capitolo" scope="request"/>
<jsp:useBean id="Domanda" class="org.aspcf.modules.checklist_benessere.base.Domanda" scope="request"/>
<jsp:useBean id="CapitoliList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="idAlleg" class="java.lang.String" scope="request"/>
<jsp:useBean id="esito" class="java.lang.String" scope="request"/>
<jsp:useBean id="numAllegato" class="java.lang.String" scope="request"/>
<jsp:useBean id="AzFields" class="org.aspcf.modules.controlliufficiali.base.AziendeZootFields" scope="request"/>
<jsp:useBean id="Ticket" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="ChecklistIstanza" class="org.aspcf.modules.checklist_benessere.base.ChecklistIstanza" scope="request"/>

<jsp:useBean id="ChecklistIstanzaCGO" class="org.aspcf.modules.checklist_benessere.base.ChecklistIstanzaCGO" scope="request"/> 
<jsp:useBean id="ChecklistIstanzaCGO_2018" class="org.aspcf.modules.checklist_benessere.base.ChecklistIstanzaCGO_2018" scope="request"/> 


<%@ include file="../../../initPage.jsp" %>

<%@ include file="allegato_b11_js.jsp" %>

<% String specie = "-1";
   String codice_specie = Allevamento.getCodice_specie();
   
   codice_specie = "-1";  


%>

<input type="hidden" name="orgId" id="orgId" value="<%=Allevamento.getOrgId()%>"/>
<input type="hidden" name="stabId" id="stabId" value="<%=Allevamento.getIdStabilimento()%>"/>

<body onload="" >
<form method="post" name="myform" action="PrintModulesHTML.do?command=InsertChecklistBenessere&idControllo=<%=Allevamento.getIdControllo()%>&orgId=<%=Allevamento.getOrgId()%>&stabId=<%=Allevamento.getIdStabilimento()%>&specie=-1&auto-populate=true">


<% String versioneChecklistString = (String) request.getAttribute("versioneChecklist");
int versioneChecklist = -1;
if (versioneChecklistString!=null && !versioneChecklistString.equals(""))
	versioneChecklist = Integer.parseInt(versioneChecklistString);

%>

<% if (versioneChecklist == 3){ %>
<%@ include file="b11_frontespizio_cgo.jsp" %>
<% } else if (versioneChecklist == 4){ %>
<%@ include file="b11_frontespizio_cgo_2018.jsp" %>
<% } else {%> 
<%@ include file="b11_frontespizio_new.jsp" %>
<%} %>



 <input type="hidden" name="idAlleg" id="idAlleg" value="<%=idAlleg%>" />
  <input type="hidden" name="idCU" id="idCU" value="<%=Allevamento.getIdControllo()%>" />
  <input type="hidden" name="dimCapitoli" id="dimCapitoli" value="<%=CapitoliList.size()%>" />
<table cellpadding="9" cellspacing="0" border="1" width="100%" class="details">
   <% 
  
   	  int cap=1;
   	  Iterator<Capitolo> it = CapitoliList.iterator();
      while(it.hasNext()){
  	  		Capitolo capitolo = it.next();
  	  		ArrayList<Domanda> domande = capitolo.getDomandeList();
  	  		int numDomande = domande.size();
  	  if(numDomande>0)
  	  {
  %>
 <tr>
  			<td <%=("checkbox".equals(capitolo.getTipoCapitolo()) ||  "nocheck".equals(capitolo.getTipoCapitolo())) ? "colspan='5' " : "colspan='2'"  %> style="font-weight: bold;background-color: gray;">
  				<%=capitolo.getDescription() %>
  			</td>
  			<%
  			if (!"nocheck".equals(capitolo.getTipoCapitolo()) && !"checkbox".equals(capitolo.getTipoCapitolo()))
  			{
  			%>
  			<td style="font-weight: bold;background-color: gray;">SI</td>
  			<td style="font-weight: bold;background-color: gray;">NO</td>
  			<td style="font-weight: bold;background-color: gray;">N.A.</td> 
  			<%} %>
  		</tr>
 
  <%

  for(int i=0;i<numDomande;i++)
  { 
	%>
	
  		
	
			
			<tr class="containerBody">
			<%if ("nocheck".equals( domande.get(i).getTipoDomanda()))
			{
				%>
				
				
				<td colspan="5">
				<input type="hidden"  name="tipocapitolo_<%=cap%>_<%=(i+1)%>" id="tipocapitolo_<%=cap%>_<%=(i+1)%>" value="<%=capitolo.getTipoCapitolo()%>"/>
				<input type="hidden"  name="tipodomanda_<%=cap%>_<%=(i+1)%>" id="tipodomanda_<%=cap%>_<%=(i+1)%>" value="<%=domande.get(i).getTipoDomanda()%>"/>
				
				<%=domande.get(i).getDescription() %></td>
				<%}
			else{
				if ("checkbox".equals( domande.get(i).getTipoDomanda()))
				{
					
				
					%>
				
				<td><%=(i+1) %></td>	
				<td colspan="4">
				
				<input type="checkbox"  name="esito_<%=cap%>_<%=(i+1)%>" id="d_si_<%=cap%>_<%=(i+1)%>" value="si"/>
				<%=domande.get(i).getDescription() %>
				<input type="hidden"  name="tipocapitolo_<%=cap%>_<%=(i+1)%>" id="tipocapitolo_<%=cap%>_<%=(i+1)%>" value="<%=capitolo.getTipoCapitolo()%>"/>
				<input type="hidden"  name="tipodomanda_<%=cap%>_<%=(i+1)%>" id="tipodomanda_<%=cap%>_<%=(i+1)%>" value="<%=domande.get(i).getTipoDomanda()%>"/>
  					
				</td>	
				
				
					
					<%		
					
				}
					
				
				else
				{
				
				
			%>
  		<td>
  				<input type="hidden"  name="tipocapitolo_<%=cap%>_<%=(i+1)%>" id="tipocapitolo_<%=cap%>_<%=(i+1)%>" value="<%=capitolo.getTipoCapitolo()%>"/>
				<input type="hidden"  name="tipodomanda_<%=cap%>_<%=(i+1)%>" id="tipodomanda_<%=cap%>_<%=(i+1)%>" value="<%=domande.get(i).getTipoDomanda()%>"/>
  		<%=(i+1) %></td>
  		<td><%=domande.get(i).getDescription() %></td>
  		<td>
  		<input type="radio"  name="esito_<%=cap%>_<%=(i+1)%>" id="d_si_<%=cap%>_<%=(i+1)%>" value="si"/>
	  
	
  		</td> 
		<td>
		<input type="radio"  name="esito_<%=cap%>_<%=(i+1)%>" id="d_no_<%=cap%>_<%=(i+1)%>" value="no" />
		</td> 
		
		<td>
		<input type="radio"  name="esito_<%=cap%>_<%=(i+1)%>" id="d_na_<%=cap%>_<%=(i+1)%>" value="na" />
		</td> 
	<%}} %>
			</tr>		
  		
  		
  		
<% }
  	  }

	++cap;
  	}//fine while
     
  	%>  
<!--  <tr class="textDim"><td colspan="5"><b>NOTE:<br><textarea  name ="mod_b11_note" class="textDim" rows="6" cols="80" ></textarea></b></td></tr> -->
 <tr><td colspan="5">
 <table class = "noborder">
 <tr><td colspan="2"><b>Preavviso (max 48 ore):</b></td></tr>
  <td> NO [&nbsp;<%if (Ticket.getFlag_preavviso()==null || ( Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("n"))){%>X<%} %>&nbsp;] SI [&nbsp;<% if (Ticket.getFlag_preavviso()!=null && !Ticket.getFlag_preavviso().equalsIgnoreCase("n")){%>X<%} %>&nbsp;] </td>
 </tr>
 <tr>
 <td> Se SI in data <label class="layout"><%=toDateasString(Ticket.getData_preavviso_ba()) %></label> tramite: </td></tr>
 <tr>
 <td> [&nbsp;<% if (Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equals("P")){%>X<%} %>&nbsp;] Telefono</td></tr>
 <tr>
 <td>[&nbsp;<% if (Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equals("T")){%>X<%} %>&nbsp;] Telegramma/lettera/fax</td></tr>
 <tr>
 <td>[&nbsp;<% if (Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equals("A")){%>X<%} %>&nbsp;] Altra forma ............... </td></tr>
 </table>
 </td>
 </tr>
  
<!--   <tr class="textDim"><td colspan="5">L'esito del presente controllo sarà considerato anche per la verifica del rispetto degli impegni di -->
<!-- condizionalità</td></tr> -->
<!--  <tr class="textDim"><td colspan="5"><b>Provvedimenti adottati:<br><textarea name="mod_b11_provvedimenti" class="textDim" rows="6" cols="80" name = "" ></textarea></b></td></tr> -->
<!-- <tr><td colspan="5"><b>È stata lasciata una copia della presente check-list all'allevatore?</b><br> -->
 
<!-- <input type = "radio" name = "mod_b11_flag_rilascio_copia"  value = "No" />No<br> -->
<!--  <input type = "radio" name = "mod_b11_flag_rilascio_copia" value = "Si"/>Si<br> -->

<!-- </td></tr> -->



  
  </table>
  
  
<% if (versioneChecklist == 3){ %>
<%@ include file="b11_footer_cgo.jsp" %>
<% } else if (versioneChecklist == 4){ %>
<%@ include file="b11_footer_cgo_2018.jsp" %>
<% } %>
   
  
  
  <div id="idbtn" style="display:block;">
       	<input type="button" name="salva"  class="buttonClass" value="Salva Temporaneo" onclick="if (checkFormB11()==false){return false;}; javascript:if( confirm('La scheda sarà aggiornata come richiesto. Vuoi procedere con il salvataggio?')){document.myform.bozza.value = true; return document.myform.submit();}else return false;"/> &nbsp;
  		<input type="button" name="salva" class="buttonClass" value="Salva Definitivo" onclick="if (checkFormB11()==false){return false;}; javascript:if( confirm('La scheda sarà aggiornata come richiesto ma i dati non saranno più modificabili. Vuoi procedere con il salvataggio definitivo?')){document.myform.bozza.value = false; return document.myform.submit();}else return false;"/> &nbsp;
  	</div>
  		<input type="hidden" name="bozza" value="true" />
 
  <br>
  <br>
  <br>
  
    <div>Data del controllo Ufficiale &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Sottoscrizione del VeterinarioSottoscrizione del Veterinario che ha compiuto l'ispezione </div>


<script>

function rispondiCaso() {
	
	 var nomi = ["Rita", "Paolo", "Stefano", "Alessandro", "Uolter", "Antonio", "Carmela", "Viviana", "Valentino", "Rischio", "Impresa", "Vittoria", "Mandarino", "Ext", "US", "Caffe", "Altrove", "SPA", "Food", "Privata", "Coffee", "Angolo", "Bar"];
	 var inputs = document.getElementsByTagName('input');
	 var inputNamePrecedente="";
    for (i = 0; i < inputs.length; i++) {
    	    	
        if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
        	var random = Math.floor(Math.random() * 11);
          	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
           			inputs[i].click();
        	}
        else if (inputs[i].type == 'text') {
        	if($(inputs[i]).attr("onkeyup")=='filtraInteri(this)'){
           		inputs[i].value = Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1);
        	}
        	else
           		inputs[i].value = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
    	}
        else if (inputs[i].type == 'number') {
        	var random1 = Math.floor(Math.random() * 11);
        	var random2 = Math.floor(Math.random() * 11);

        	if($(inputs[i]).attr("step")=='.01')
	        	inputs[i].value = random1+'.'+random2;
	        else
	        	inputs[i].value = random1;
    	}
        
        else if (inputs[i].type == 'date') {
        	
        	var date = new Date();
        	var currentDate = date.toISOString().slice(0,10);
			inputs[i].value = currentDate;
    	}
        else if (inputs[i].type == 'textarea') {
        	if($(inputs[i]).attr("onkeyup")=='filtraInteri(this)'){
           		inputs[i].innerHTML = Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1);
        	}
        	else
           		inputs[i].innerHTML = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
    	}
        
        inputNamePrecedente = inputs[i].name;
          }
   		
}

function rispondiSoloDomande() {
	
	 var nomi = ["Rita", "Paolo", "Stefano", "Alessandro", "Uolter", "Antonio", "Carmela", "Viviana", "Valentino", "Rischio", "Impresa", "Vittoria", "Mandarino", "Ext", "US", "Caffe", "Altrove", "SPA", "Food", "Privata", "Coffee", "Angolo", "Bar"];
	 var inputs = document.getElementsByTagName('input');
	 var inputNamePrecedente="";
   	for (i = 0; i < inputs.length; i++) {
   		
   		if (inputs[i].name.indexOf("dom_")>=0) {
   	    	
	       if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
	       	var random = Math.floor(Math.random() * 11);
	         	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
	          			inputs[i].click();
	       	}
	       else if (inputs[i].type == 'text') {
	         inputs[i].value = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
	   	}
	       else if (inputs[i].type == 'number') {
	       	var random1 = Math.floor(Math.random() * 11);
	       	var random2 = Math.floor(Math.random() * 11);
	
	       	if($(inputs[i]).attr("step")=='.01')
		        	inputs[i].value = random1+'.'+random2;
		        else
		        	inputs[i].value = random1;
	   	}
	       
	       else if (inputs[i].type == 'date') {
	       	
	       	var date = new Date();
	       	var currentDate = date.toISOString().slice(0,10);
				inputs[i].value = currentDate;
	   	}
	       
	       inputNamePrecedente = inputs[i].name;
	         }
   	}
}


</script>

<%UserBean user = (UserBean) session.getAttribute("User");
if (user.getUserId()==5885) { %>	
<input type="button" id="caso" name="caso" style="background-color:yellow;" value="rispondi a caso a tutta la checklist (TEST)" onClick="rispondiCaso()"/>
<input type="button" id="casoD" name="casoD" style="background-color:lime" value="rispondi a caso solo alle domande (TEST)" onClick="rispondiSoloDomande()"/>
<% } %>
  
 </form> 
</body>