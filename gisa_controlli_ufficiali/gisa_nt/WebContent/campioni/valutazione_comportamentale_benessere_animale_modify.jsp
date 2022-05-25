<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.campioni.base.Ticket"%>
<%@page import="org.aspcfs.modules.campioni.base.MacroareaAnomalieValutazione"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%><jsp:useBean id="DettaglioCampione" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>
<jsp:useBean id="SchedaValutazione" class="org.aspcfs.modules.campioni.base.MacroareaValutazioneComportamentale" scope="request"/>
<jsp:useBean id="lookup_stato_generale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_anomalie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_tolleranza" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_adottabilita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="layout" class="java.lang.String" scope="request"/>

<script>
function azzeraItem()
{
	
lista = document.getElementById('listaAnomalie').options;
for (i=0;i<lista.length;i++)
{
	if (lista[i].selected == true)
	{
		lista[i].selected = false;
	}
	if (lista[i].value == '-1')
	{
		lista[i].selected = true;
	}
	}

document.getElementById('listaAnomalie').style.display='none';
	}

</script>
<%
Ticket dettCampione = (Ticket) DettaglioCampione ;
%>

<table class="trails" cellspacing="0">
<tr>
<td>
Campione > Scheda Valutazione > Modifica
</td>
</tr>
</table>

<h2>Scheda Valutazione Comportamentale Benessere Animale</h2>

<form method="post" action="IuvValutazioneComportamentale.do?command=Update&auto-populate=true">
<input type = "hidden" name = "idCampione" value = "<%=dettCampione.getId()%>"/>
<input type = "hidden" name = "id" value = "<%=SchedaValutazione.getId()%>"/>
<input type = "hidden" name = "layout" value = "<%=layout%>"/>


<!-- PULSANTI DI MODIFICA FINCHE IL CAMPIONE NON è STATO CHIUSO DOPO LA SCHEDA NON SARà MODIFICABILE -->
<%
if (dettCampione.getClosed()==null)
{
	%>
	<input type = "submit" value = "Aggiorna Scheda"/>
	<%
	
}
%>


<br><br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <%
      String enabled_1="";
      String enabled_2="";
      String enabled_3="";
      String enabled_4="";
      %>
  <tr>
    <th colspan="2">
      <strong>PARAMETRI STRUTTURALI</strong>
    </th>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAdeguatezza_box())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      adeguatezza box
    </td>
    <td>
      &nbsp;&nbsp; Adeguato<input type ="radio" id="adeguatezza_box" name ="adeguatezza_box" value = "true" <%=enabled_1 %> >
      Non Adeguato <input type ="radio" id="adeguatezza_box" name ="adeguatezza_box" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAdeguatezza_sgambamento())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      adeguatezza sgambamento
    </td>
    <td>
      &nbsp;&nbsp; Adeguato<input type ="radio" id="adeguatezza_sgambamento" name ="adeguatezza_sgambamento" value = "true" <%=enabled_1 %> >
      Non Adeguato <input type ="radio" id="adeguatezza_sgambamento" name ="adeguatezza_sgambamento" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>
      
  <tr>
    <th colspan="2">
      <strong>PARAMETRI RILEVATI SUL GRUPPO</strong>
    </th>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getSegni_di_malessere())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      segni di malessere<br>Polipnea - Tremore - Ammucchiamento<br>Abbaio continuo - dolore - diarrea
    </td>
    <td>
      &nbsp;&nbsp; Presenza<input type ="radio" id="segni_di_malessere" name ="segni_di_malessere" value = "true" <%=enabled_1 %> >
      Assenza <input type ="radio" id="segni_di_malessere" name ="segni_di_malessere" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAtti_ripetitivi())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      atti ripetitivi e/o compulsivi
    </td>
    <td>
      &nbsp;&nbsp; Presenza<input type ="radio" id="atti_ripetitivi" name ="atti_ripetitivi" value = "true" <%=enabled_1 %> >
      Assenza <input type ="radio" id="atti_ripetitivi" name ="atti_ripetitivi" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>
 
  <tr>
    <th colspan="2">
      <strong>PARAMETRI RILEVATI SUL CAMPIONE</strong>
    </th>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAtti_ripetitivi_campione())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      atti ripetitivi e/o compulsivi
    </td>
    <td>
      &nbsp;&nbsp; Presenza<input type ="radio" id="atti_ripetitivi_campione" name ="atti_ripetitivi_campione" value = "true" <%=enabled_1 %> >
      Assenza <input type ="radio" id="atti_ripetitivi_campione" name ="atti_ripetitivi_campione" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAggressivita())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      aggressivita'
    </td>
    <td>
      &nbsp;&nbsp; Presenza<input type ="radio" id="aggressivita" name ="aggressivita" value = "true" <%=enabled_1 %> >
      Assenza <input type ="radio" id="aggressivita" name ="aggressivita" value = "false" <%=enabled_2 %>  >
    </td>
  </tr> 
    <% 
  if(("molto").equals(SchedaValutazione.getPaura())){ 
	 enabled_1="checked='checked'";  enabled_2=""; enabled_3=""; enabled_4=""; 
  }else if(("abbastanza").equals(SchedaValutazione.getPaura())){
	 enabled_1=""; enabled_2=" checked='checked' "; enabled_3=""; enabled_4="";
  }else if(("poco").equals(SchedaValutazione.getPaura())){
		 enabled_1=""; enabled_2=""; enabled_3=" checked='checked' "; enabled_4="";
  
  }else{
		 enabled_1=""; enabled_2=""; enabled_3=""; enabled_4=" checked='checked' ";
  } 
  %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test paura
    </td>
    <td>
      &nbsp;&nbsp; Molto<input type ="radio" id="paura" name ="paura" value = "molto" <%=enabled_1 %>>
	  Abbastanza<input type ="radio" id="paura" name ="paura" value = "abbastanza" <%=enabled_2 %>>
	  Poco<input type ="radio" id="paura" name ="paura" value = "poco" <%=enabled_3 %>>
	  Assente<input type ="radio" id="paura" name ="paura" value = "assente" <%=enabled_4 %>>
    </td>
  </tr>  
    <% 
  if(("molto").equals(SchedaValutazione.getSocievolezza())){ 
	 enabled_1="checked='checked'";  enabled_2=""; enabled_3=""; enabled_4=""; 
  }else if(("abbastanza").equals(SchedaValutazione.getSocievolezza())){
	 enabled_1=""; enabled_2=" checked='checked' "; enabled_3=""; enabled_4="";
  }else if(("poco").equals(SchedaValutazione.getSocievolezza())){
		 enabled_1=""; enabled_2=""; enabled_3=" checked='checked' "; enabled_4="";
  
  }else{
		 enabled_1=""; enabled_2=""; enabled_3=""; enabled_4=" checked='checked' ";
  } 
  %>   
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test socievolezza
    </td>
    <td>
      &nbsp;&nbsp; Molto<input type ="radio" id="socievolezza" name ="socievolezza" value = "molto" <%=enabled_1 %>>
	  Abbastanza<input type ="radio" id="socievolezza" name ="socievolezza" value = "abbastanza" <%=enabled_2 %>>
	  Poco<input type ="radio" id="socievolezza" name ="socievolezza" value = "poco" <%=enabled_3 %>>
	  Assente<input type ="radio" id="socievolezza" name ="socievolezza" value = "assente" <%=enabled_4 %>>
    </td>
  </tr>  
  <% if(Boolean.parseBoolean(SchedaValutazione.getGuinzaglio())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test gestione guinzaglio
    </td>
    <td>
      &nbsp;&nbsp; Adeguato<input type ="radio" id="guinzaglio" name ="guinzaglio" value = "true" <%=enabled_1 %> >
      Inadeguato <input type ="radio" id="guinzaglio" name ="guinzaglio" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>  
  <% if(Boolean.parseBoolean(SchedaValutazione.getManipolazioni())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test tolleranza manipolazioni
    </td>
    <td>
      &nbsp;&nbsp; Adeguato<input type ="radio" id="manipolazioni" name ="manipolazioni" value = "true" <%=enabled_1 %> >
      Inadeguato <input type ="radio" id="manipolazioni" name ="manipolazioni" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>  
  <% if(Boolean.parseBoolean(SchedaValutazione.getPrelievo_ematico())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test tolleranza prelievo ematico
    </td>
    <td>
      &nbsp;&nbsp; Adeguato<input type ="radio" id="prelievo_ematico" name ="prelievo_ematico" value = "true" <%=enabled_1 %> >
      Inadeguato <input type ="radio" id="prelievo_ematico" name ="prelievo_ematico" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>  
  <% 
  if(("adeguato").equals(SchedaValutazione.getBcs())){ 
	 enabled_1="checked='checked'";  enabled_2=""; enabled_3=""; 
  }else if(("troppo_magro").equals(SchedaValutazione.getBcs())){
	 enabled_1=""; enabled_2=" checked='checked' "; enabled_3="";
  }else{
		 enabled_1=""; enabled_2=""; enabled_3=" checked='checked' ";	  
  } 
  %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      bcs
    </td>
    <td>
      &nbsp;&nbsp; Adeguato<input type ="radio" id="bcs" name ="bcs" value = "adeguato" <%=enabled_1 %> >
      Troppo magro <input type ="radio" id="bcs" name ="bcs" value = "troppo_magro"  <%=enabled_2 %> >
      Troppo Grasso <input type ="radio" id="bcs" name ="bcs" value = "troppo_grasso"  <%=enabled_3 %> >
    </td>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getPulizia())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      pulizia e sanita'<br>cute - mantello - unghie
    </td>
    <td>
      &nbsp;&nbsp; Adeguato<input type ="radio" id="pulizia" name ="pulizia" value = "true" <%=enabled_1 %> >
      Inadeguato <input type ="radio" id="pulizia" name ="pulizia" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>   
  <% if(Boolean.parseBoolean(SchedaValutazione.getZoppia())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      zoppia
    </td>
    <td>
      &nbsp;&nbsp; Presente<input type ="radio" id="zoppia" name ="zoppia" value = "true" <%=enabled_1 %> >
      Assente <input type ="radio" id="zoppia" name ="zoppia" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>    
  <% if(Boolean.parseBoolean(SchedaValutazione.getTosse())){ enabled_1="checked='checked'";  enabled_2=""; }else{	enabled_1=""; enabled_2=" checked='checked' "; } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      tosse
    </td>
    <td>
      &nbsp;&nbsp; Presente<input type ="radio" id="tosse" name ="tosse" value = "true" <%=enabled_1 %> >
      Assente <input type ="radio" id="tosse" name ="tosse" value = "false" <%=enabled_2 %>  >
    </td>
  </tr>    
      
      
      
      
      
      
      
      
      </table>
      <!-- PULSANTI DI MODIFICA FINCHE IL CAMPIONE NON è STATO CHIUSO DOPO LA SCHEDA NON SARà MODIFICABILE -->
<%
if (dettCampione.getClosed()==null) 
{
	%>
	<input type = "submit" value = "Aggiorna Scheda"/>
	<%
	
}
%>
</form>
<%
if (SchedaValutazione.getListaAnomalie().size()==0)
{
%>
<script>azzeraItem();</script>
<%
}
%>