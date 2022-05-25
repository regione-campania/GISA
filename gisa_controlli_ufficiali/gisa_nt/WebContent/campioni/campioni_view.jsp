<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Iterator"%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.*"%>

<%@page import="org.aspcfs.modules.campioni.base.SpecieAnimali"%>
<jsp:useBean id="Motivazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="QuesitiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaCampiAggiuntivi" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="SpecieCategoria" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<script>

function recuperaCodPreaccettazione(idCampione){
	DwrPreaccettazione.Preaccettazione_RecuperaCodPreaccettazione(idCampione, {callback:recuperaCodPreaccettazioneCallBack,async:false});
}
function recuperaCodPreaccettazioneCallBack(returnValue)
{
	var dati = returnValue;
	var obj;
	obj = JSON.parse(dati);
	
	if(obj.codice_preaccettazione != ""){        			
		document.getElementById("codpreacc").innerHTML = obj.codice_preaccettazione;
	}  else {
		document.getElementById("rigaCodPreacc").style.display = 'none';
	}  
}

function openPopUp(url){
	var res;
	var result;


		window.open(url,null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
	
		} 
	

</script>
<%    
java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>

<%

LookupList listaPiani = (LookupList)request.getAttribute("Piani2");


%>
<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.site">ASL</dhv:label></td>
					<td><%=SiteIdList.getSelectedValue(TicketDetails
										.getSiteId())%>
					<%
					%> 
					<input type="hidden"
						name="siteId" value="<%=TicketDetails.getSiteId()%>"></td>
				</tr>
			
  <input type="hidden" name="id" id="id"
			value="<%=  TicketDetails.getId() %>" />
			
		<input type="hidden" name="orgId" id="orgId"
			value="<%=  TicketDetails.getOrgId() %>" />
<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
   
     
      <td>
      <input type = "hidden" name = "idControlloUfficiale" value = "<%= toHtmlValue(TicketDetails.getIdControlloUfficiale()) %>">
      		<%= toHtmlValue(TicketDetails.getIdControlloUfficiale()) %>
      </td>.
      
  </tr>
 <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Codice Campione</dhv:label>
    </td>
      <td>
      	<%= toHtmlValue(TicketDetails.getIdentificativo()) %>
      </td>
    
  </tr>
  
   <tr id="rigaCodPreacc" class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Codice Preaccettazione</dhv:label>
    </td>
      <td>  
        <label id="codpreacc" name="codpreacc" ></label>    
        <input type="hidden" id="codpreaccId" name="codpreaccId" value="<%=TicketDetails.getId()%>"> 
      </td>
    
  </tr>
  
  
 	
 	<%if (!"2a".equalsIgnoreCase(TicketDetails.getCodiceInternoIspezione())){ 
 	if(TicketDetails.getIdControlloUfficiale()!=null && TicketDetails.getIdControlloUfficiale().equals("-1")) { %>
    	<tr class="containerBody">
	    <td valign="top" class="formLabel">
	      <dhv:label name="">Motivazione</dhv:label>
	    </td>
	    <td>
	     
	    
	     <table class="noborder">
	     <tr>
	     <% if(TicketDetails.getMotivazione_campione() > 0) {%>
	     <td>
	        <%=QuesitiList.getSelectedValue(TicketDetails.getMotivazione_campione()) %>   
	    </td>
	    <% } else { %>
	     <td>
	    	N.D   
	    </td>
	    <% } %>
	    </tr></table>
	  </tr> 	 
    <% } else { %> 	
 	
  	<%if(TicketDetails.getMotivazione_campione()>0){ %>
	  <tr class="containerBody">
	    <td valign="top" class="formLabel">
	      <dhv:label name="">Motivazione</dhv:label>
	    </td>
	    <td>
	     
	    
	     <table class="noborder">
	     <tr>
	     <td>
	    
	     
	      <%=Motivazione.getSelectedValue(TicketDetails.getMotivazione_campione()) %>
	      
	    
	    </td><td id ="noteMotivazione">
	    &nbsp; &nbsp; &nbsp; &nbsp;
	     <%
	     
	     if(TicketDetails.getMotivazione_campione()==23){ %>
	    Note <br> <%=TicketDetails.getNoteMotivazione() %>
	    <%} %>
	    
	    </td>
	    </tr></table>
	  </tr>
	  <%} 
	  } }%>
	  
   

 	<%if ("2a".equalsIgnoreCase(TicketDetails.getCodiceInternoIspezione()))
	
{%>	
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Piano di monitoraggio</dhv:label>
    </td>
    <td>
      <% if (listaPiani!=null){ %> 
      	<%=listaPiani.getSelectedValue(TicketDetails.getMotivazione_piano_campione())%>
    <%  } else { %>
    	 <%=QuesitiList.getSelectedValue(TicketDetails.getMotivazione_piano_campione())%>
    <%  }%> 
    </td>
</tr>

<%} %>

<dhv:evaluate if="<%= hasText(TicketDetails.getLocation())%>">
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Numero Verbale</dhv:label>
    </td>
    <td>
     <% 
     if(TicketDetails.getLocation() != null && !TicketDetails.getLocation().equals("null") && !TicketDetails.getLocation().equals("")) { %>
      		<%= toHtmlValue(TicketDetails.getLocation()) %>
      <% } else { %>
      <%= "N.D" %>
      <% } %>
    </td>
</tr>
</dhv:evaluate>
	   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="campioni.data_richiesta">Data Prelievo</dhv:label>
    </td>
    <td>
    	 <% if(TicketDetails.getAssignedDate()  != null && !TicketDetails.getAssignedDate().equals("null")) { 
				SimpleDateFormat dataPr = new SimpleDateFormat("dd/MM/yyyy");
    	 %>
      		 <%-- %><zeroio:tz
				timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>"
				showTimeZone="false" default="&nbsp;" /> --%>
         <%= (((TicketDetails.getAssignedDate()!=null))?(dataPr.format(TicketDetails.getAssignedDate())):(dataPr.format(d)))%> 
      <% } else { %>
      <%= "N.D" %>
      <% } %>
     
    </td>
  </tr>
  
  <%if((TicketDetails.getMicrochip()!= null && ! "".equals(TicketDetails.getMicrochip())) ||(TicketDetails.getNumBoxCanile()!=null && ! "".equals(TicketDetails.getNumBoxCanile()) ))
	  {%>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Microchip/box</dhv:label>
    </td>
    <td>
    
    <%=(TicketDetails.getMicrochip()!=null && !"".equals(TicketDetails.getMicrochip()))?TicketDetails.getMicrochip() :TicketDetails.getNumBoxCanile()%>
    
    </td>
    </tr>
    <%} %>
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Matrice</dhv:label>
    </td>
    <td>

    
    <table class="noborder">
    
						<%
						HashMap<Integer,String> matrici= TicketDetails.getMatrici();
						Iterator<Integer> itMatrici = matrici.keySet().iterator();
						int i = 0 ;
						while(itMatrici.hasNext())
						{
							i++ ;
							int chiave = itMatrici.next();
							String descrizione = TicketDetails.getMatrici().get(chiave);
							out.print("<tr><td> "+i+") "+descrizione+"</td></tr>");
						}
						%>
						<% if(i==0){%>
							N.D
						<% } %>
			<tr>
			<td>
				<% if(TicketDetails.getNoteAlimenti()!=null && ! "".equals(TicketDetails.getNoteAlimenti()) ){%>
						NOTE : <%=TicketDetails.getNoteAlimenti() %> 
				<% } else { %>
					<b>Note: </b>N.D
				<% } %>
			</td></tr>
		</table>
</td></tr>
    
 
  <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Tipo di Analisi</dhv:label>
      </td>
    <td>
    <table class="noborder">
    <% 
    String tipoEsame = "";
    
    %>
    	
    <%="<b>- Tipo di Esame:</b> "%>
					<input type="hidden" name="provvedimenti" value="<%=TicketDetails.getTipoCampione() %>">
					
				
						<%
						i=0 ;
						ArrayList<Analita> tipi= TicketDetails.getTipiCampioni();
						for(Analita a : tipi)
						{
							i++ ;
							int chiave = a.getIdAnalita();
							String descrizione = a.getDescrizione();
							out.print("<tr><td> "+i+") "+descrizione+"</td>");
						
						}
						%>
			<input type="hidden" id="numeroAnaliti" name="numeroAnaliti" value="<%=i%>">
						
			<tr><td><% if(TicketDetails.getNoteAnalisi() != null && !TicketDetails.getNoteAnalisi().equals("")) { %>
				<%="Note : "+TicketDetails.getNoteAnalisi() %>
				<% } else { %>
					<%="Note : N.D"%>
					<% } %>
			</td></tr>
		</table>
    </td>
  </tr>
</dhv:include>

<%
if (TicketDetails.getCheck_circuito_ogm() != null && ! "".equals(TicketDetails.getCheck_circuito_ogm()))
{
%>
<tr>
<td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Circuito
      </td>
    <td>
   <%=TicketDetails.getCheck_circuito_ogm() %>
    </td>
    
</tr>
<%	
}
%>
<%
ArrayList<SpecieAnimali> listaSpecieSelezionate =  TicketDetails.getListaSpecieAnimali();
ArrayList<String> listaProdottiPnaa =  TicketDetails.getListaProdottiPnaa();

if (listaSpecieSelezionate!= null && listaSpecieSelezionate.size()>0)
{
%>


<!-- GESTIONE CAMPI AGGIUNTIVI IN CASP DI PIANO PNAA MATRICE MANGIME -->
<tr>
<td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Mangime per piu specie
      </td>
    <td>
    <input type = "checkbox" disabled="disabled" <%if (TicketDetails.isCheck_specie_mangimi()){%>checked="checked"<%} %>>
    </td>
    
</tr>
<tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Specie Animali
      </td>
    <td>
    	<%
    	String categoriaSpecieBovina = "" ;
    	String categoriaSpecieBufalina= "" ;

    	for (SpecieAnimali specie : listaSpecieSelezionate)
    	{
    		out.print("- "+specie.getDescrizioneSpecie());
    		if (specie.getDescrizioneSpecie().equalsIgnoreCase("bovini"))
    		{
    			categoriaSpecieBovina = specie.getDescrizioneCategoria();
    		}
    		if (specie.getDescrizioneSpecie().equalsIgnoreCase("bufalini"))
    		{
    			categoriaSpecieBufalina = specie.getDescrizioneCategoria();
    		}
    		
    		
    	}
    	
    	%>	
    </td>
  </tr>
  <%
  if (!"".equals(categoriaSpecieBovina))
  {
	  %>
	  
<tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Categoria Specie Bovina
      </td>
    <td>
    <%=categoriaSpecieBovina %>
    </td>
    </tr>
	  <%
  }
  %>
  
    <%
  if (!"".equals(categoriaSpecieBufalina))
  {
	  %>
	  
<tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Categoria Specie Bufalina
      </td>
    <td>
    <%=categoriaSpecieBufalina %>
    </td>
    </tr>
	  <%
  }
  %>
<%	
}
%>
<!--  aggiunta prodotti -->
<%if (listaProdottiPnaa != null && listaProdottiPnaa.size() > 0)
{
%>
<tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Stato del prodotto al momento del prelievo
      </td>
    <td>
    	<%
   
    	for (String prod : listaProdottiPnaa)
    	{
    		out.print("- "+prod);
    	}
    	
    	%>	
    </td>
  </tr>
<% } %>

<dhv:include name="organization.source" none="true">
<dhv:evaluate if="<%= TicketDetails.getDestinatarioCampione()!= -1%>">
   <tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Laboratorio di Destinazione</dhv:label>
      </td>
    <td>
    	<% if(TicketDetails.getIdControlloUfficiale()!=null && TicketDetails.getIdControlloUfficiale().equals("-1")) { %>
    	 	<%= "IZSM" %>
    	<% } else { %> 	
    
      	<%=DestinatarioCampione.getSelectedValue(TicketDetails.getDestinatarioCampione())%>
		<input type="hidden" name="destinatarioCampione" value="<%=TicketDetails.getDestinatarioCampione() %>">
		<% } %>				
    </td>
  </tr>
  </dhv:evaluate>
</dhv:include>
<%-- <% if(TicketDetails.getDataAccettazione() != null){ %> --%>
<!-- <tr class="containerBody"> -->
<!--     <td nowrap class="formLabel"> -->
<!--       <dhv:label name="">Data Accettazione</dhv:label> -->
<!--     </td> -->
<!--     <td> -->
<%--     <% SimpleDateFormat dataAc = new SimpleDateFormat("dd/MM/yyyy"); %> --%>
<%--       <zeroio:tz
<%-- 				timestamp="<%= TicketDetails.getDataAccettazione() %>" dateOnly="true" --%>
<%-- 				timeZone="<%= TicketDetails.getDataAccettazioneTimeZone() %>" --%>
<%-- 				showTimeZone="false" default="&nbsp;" /> --%>
<%-- 				--%>  
<%--      <%= (((TicketDetails.getDataAccettazione() !=null))?(dataAc.format(TicketDetails.getDataAccettazione())):(dataAc.format(d)))%>  --%>
     
<!--     </td> -->
<!--   </tr> -->
<%--   <%} %> --%>
 
  
   

     
<dhv:evaluate if="<%= hasText(TicketDetails.getProblem()) %>">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>
    	<% if(TicketDetails.getProblem() != null && !TicketDetails.getProblem().equals("null") && !TicketDetails.getProblem().equals("")) { %>
	      <%= toString(TicketDetails.getProblem()) %>
	    <% } else { %>
	      <%= "N.D" %>
	     <% } %>	  
    </td>
	</tr>
</dhv:evaluate>
<%-- <dhv:evaluate if="<%= hasText(TicketDetails.getCause()) %>"> --%>
<!--   <tr class="containerBody"> -->
<!--     <td class="containerBody"> -->
<!--       <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label> -->
<!--     </td> -->
<!--     <td> -->
<%--     	<% if(TicketDetails.getCause() != null && !TicketDetails.getCause().equals("null") && !TicketDetails.getCause().equals("")) { %> --%>
<%-- 	      <%= toString(TicketDetails.getCause()) %> --%>
<%-- 	    <% } else { %> --%>
<%-- 	      <%= "N.D" %> --%>
<%--     	<% } %> --%>
<!--     </td> -->
<!--   </tr> -->
<%--   </dhv:evaluate> --%> 
  
 
  <dhv:evaluate if="<%= TicketDetails.getListaCampiAggiuntivi()!= null && TicketDetails.getListaCampiAggiuntivi().size() > 0 %>">
  <tr class="containerBody">
		<td valign="top" class="formLabel">
			Specie
		</td>
		<td> 
		<%  
    		 Iterator itMc = SpecieCategoria.iterator();
		        while (itMc.hasNext() ){
		     	   LookupElement el = (LookupElement)itMc.next();
		 %>   
    		<%=(TicketDetails.getListaCampiAggiuntivi().get("b6") != null && TicketDetails.getListaCampiAggiuntivi().get("b6").containsKey("b6."+el.getCode())) ? el.getDescription()+"<br/>" : "" %>
    		
    	<% } %> 
		</td>
	</tr>
  </dhv:evaluate>
  
  
  <script>
  var idCampione = document.getElementById("codpreaccId").value; 
	recuperaCodPreaccettazione(idCampione);
	</script>
 