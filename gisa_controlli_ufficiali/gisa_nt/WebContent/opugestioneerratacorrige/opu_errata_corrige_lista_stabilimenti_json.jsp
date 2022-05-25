<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Vector"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="java.util.List"%>
<%Vector<Stabilimento> st= (Vector<Stabilimento>)request.getAttribute("ListaStabilimenti");%>
<%Stabilimento stEC= (Stabilimento)request.getAttribute("StabilimentoEC");%>
<%Stabilimento stEC2= (Stabilimento)request.getAttribute("StabilimentoEC2");%>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoImpresaListStab" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaListStab" class="org.aspcfs.utils.web.LookupList" scope="request" />
		
<%@ include file="../initPage.jsp"%>
		

<script>
$(document).ready(function () {
	
    $('#formCheckStab').validate({ // initialize the plugin
        rules: {
            'checkStab': {
                required: true
                
            }
        },
        messages: {
            'checkStab': {
                required: "Seleziona Almeno Uno Stabilimento"
                
            }
        }
    });

});

</script>

<form method="post" action="Gec.do?command=ErrataCorrigeSoggettoFisico" id = "formCheckStab" name="formCheckStab">
<h3>SELEZIONARE EVETUALI ALTRI STABILIMENTI SU CUI DEVONO ESSERE PROPAGATE LE MODIFICHE SUL SOGGETTO FISICO.</h3>
<table cellpadding="8" cellspacing="0" border="0" style="width: 100%" class="pagedList">
  <thead>
  <tr><th colspan="7">Richiesta di ER</th></tr>
  <tr>
  
   <th>
	<div align="center">
	<strong>Soggetto Fisico</strong>
          </div>
        </th>
        
          <th>
	<div align="center">
	<strong>Dati Impresa</strong>
          </div>
        </th>
           

  <th>
	<div align="center">
	<strong>Numero Registrazione</strong>
          </div>
        </th>
        
          <th>
	<div align="center">
	<strong>Asl</strong>
          </div>
        </th>
          <th>
	<div align="center">
	<strong>Sede Produttiva</strong>
          </div>
        </th> 
           <th>
	<div align="center">
	<strong>SELEZIONA</strong>
          </div>
        </th>
         </tr>    
</thead>
<%System.out.println("valore di toponimoooooooooooo : "+stEC.getOperatore().getSedeLegaleImpresa().getToponimo()); %>
<tbody id="bodytab">

<tr style="background-color: yellow;" >
	
	 <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	   <%=   toHtml2(stEC2.getOperatore().getRappLegale().getCognome()) +" "+toHtml2(stEC2.getOperatore().getRappLegale().getNome()) + " "+toHtml2(stEC2.getOperatore().getRappLegale().getCodFiscale())   %><br>
	  <%=   toHtml2(stEC2.getOperatore().getRappLegale().getComuneNascita()) +" "+toDateasString(stEC2.getOperatore().getRappLegale().getDataNascita()) + " "+toHtml2(stEC2.getOperatore().getRappLegale().getSesso())%><br>
	  <%=   toHtml2(stEC2.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()) +" "+toHtml2(stEC2.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia()) %><br>
	  <%= toHtml2(ToponimiList.getSelectedValue(stEC2.getOperatore().getRappLegale().getIndirizzo().getToponimo()))  +" "+toHtml2(stEC2.getOperatore().getRappLegale().getIndirizzo().getVia())+" "+toHtml2(stEC2.getOperatore().getRappLegale().getIndirizzo().getCivico())%><br>
	  
	  
	  </div>
	  </td>	
	  
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=  "RAGIONE SOCIALE :"+ toHtml2(stEC2.getOperatore().getRagioneSociale()) +"<br>"  %>
	   <%=  "TIPO IMPRESA : "+ TipoImpresaListStab.getSelectedValue(stEC2.getOperatore().getTipo_impresa()) +"<br>"  %>
	   <%=  (stEC2.getOperatore().getTipo_societa()>0)? "TIPO SOCIETA : "+ TipoSocietaListStab.getSelectedValue(stEC2.getOperatore().getTipo_societa()) +"<br>" :"" %>
	   <%= "PARTITA IVA : "+  toHtml2(stEC2.getOperatore().getPartitaIva())+"<br>"   %>
	   <%= "CF IMPRESA: "+  toHtml2(stEC2.getOperatore().getCodFiscale())+"<br>"   %>
	   <%= "DOMICILIO DIGITALE: "+  toHtml2(stEC2.getOperatore().getDomicilioDigitale())+"<br>"   %>
	  <%if(stEC2.getOperatore().getSedeLegaleImpresa()!=null){ 
	  
	  String comune = stEC2.getOperatore().getSedeLegaleImpresa().getDescrizioneComune();
	  if(comune.equalsIgnoreCase("n.d"))
		  comune = stEC2.getOperatore().getSedeLegaleImpresa().getComuneTesto();
	  %>
	  <%=  "SEDE LEGALE NAZIONE : "+ toHtml2(stEC2.getOperatore().getSedeLegaleImpresa().getNazione()) +", "+toHtml2(comune) +" "+toHtml2(stEC2.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())  %><br>
	  <%=   toHtml2(ToponimiList.getSelectedValue(stEC2.getOperatore().getSedeLegaleImpresa().getToponimo())) +" "+toHtml2(stEC2.getOperatore().getSedeLegaleImpresa().getVia())+" "+toHtml2(stEC2.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())  %><br>
	  <%} %>
	  </div>
	  </td>	
	  
	  
	  
	  <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=   toHtml2(stEC2.getNumero_registrazione())   %>
	  </div>
	  </td>	
	  
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=  stEC2.getIdAsl()   %>
	  </div>
	  </td>	
	  
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=   (stEC2.getSedeOperativa().getIdIndirizzo()>0) ? "SEDE OPERATIVA : "+toHtml2(stEC2.getSedeOperativa().getDescrizioneComune()) + " "+toHtml2(stEC2.getSedeOperativa().getVia())+ ","+toHtml2(stEC2.getSedeOperativa().getCivico())+"<br>" :"" %>
	  <%=(stEC2.getTipoAttivita()==1) ? "TIPO ATTIVITA : FISSA" : "TIPO ATTIVITA : MOBILE" %>
	  
	  </div>
	  </td>	
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
		 
	  </div>
	  </td>	
	
	</tr>

<tr style="background-color: red;" >
	
	 <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	   <%=   toHtml2(stEC.getOperatore().getRappLegale().getCognome()) +" "+toHtml2(stEC.getOperatore().getRappLegale().getNome()) + " "+toHtml2(stEC.getOperatore().getRappLegale().getCodFiscale())   %><br>
	  <%=   toHtml2(stEC.getOperatore().getRappLegale().getComuneNascita()) +" "+toDateasString(stEC.getOperatore().getRappLegale().getDataNascita()) + " "+toHtml2(stEC.getOperatore().getRappLegale().getSesso())%><br>
	  <%=   toHtml2(stEC.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()) +" "+toHtml2(stEC.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia()) %><br>
	  <%= toHtml2(ToponimiList.getSelectedValue(stEC.getOperatore().getRappLegale().getIndirizzo().getToponimo())) +" "+toHtml2(stEC.getOperatore().getRappLegale().getIndirizzo().getVia())+" "+toHtml2(stEC.getOperatore().getRappLegale().getIndirizzo().getCivico())%><br>
	  
	  
	  </div>
	  </td>	
	  
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=  "RAGIONE SOCIALE : "+ toHtml2(stEC.getOperatore().getRagioneSociale()) +"<br>"  %>
	   <%=  "TIPO IMPRESA : "+ TipoImpresaListStab.getSelectedValue(stEC.getOperatore().getTipo_impresa()) +"<br>"  %>
	   <%=  (stEC.getOperatore().getTipo_societa()>0)? "TIPO SOCIETA : "+ TipoSocietaListStab.getSelectedValue(stEC.getOperatore().getTipo_societa()) +"<br>" :"" %>
	   <%= "PARTITA IVA : "+  toHtml2(stEC.getOperatore().getPartitaIva())+"<br>"   %>
	   <%= "CF IMPRESA: "+  toHtml2(stEC.getOperatore().getCodFiscale())+"<br>"   %>
	   <%= "DOMICILIO DIGITALE: "+  toHtml2(stEC2.getOperatore().getDomicilioDigitale())+"<br>"   %>
	   
	  <%if(stEC.getOperatore().getSedeLegaleImpresa()!=null){ %>
	  <%=  "SEDE LEGALE "+ toHtml2(stEC.getOperatore().getSedeLegaleImpresa().getDescrizioneComune()) +" "+toHtml2(stEC.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())  %><br>
	  <%=   toHtml2(ToponimiList.getSelectedValue(stEC.getOperatore().getSedeLegaleImpresa().getToponimo())) +" "+toHtml2(stEC.getOperatore().getSedeLegaleImpresa().getVia())+" "+toHtml2(stEC.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())  %><br>
	  <%} %>
	  </div>
	  </td>	
	  
	  <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=   toHtml2(stEC.getNumero_registrazione())   %>
	  </div>
	  </td>	
	  
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=  stEC.getIdAsl()   %>
	  </div>
	  </td>	
	  
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	   <%=   (stEC.getSedeOperativa().getIdIndirizzo()>0) ? "SEDE OPERATIVA : "+toHtml2(stEC.getSedeOperativa().getDescrizioneComune()) + " "+toHtml2(stEC.getSedeOperativa().getVia())+ ","+toHtml2(stEC.getSedeOperativa().getCivico())+"<br>" :"" %>
	  <%=(stEC.getTipoAttivita()==1) ? "TIPO ATTIVITA : FISSA" : "TIPO ATTIVITA : MOBILE" %>
	  
	  </div>
	  </td>	
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
		 <input type="checkbox" name="checkStab"  checked="checked" value="<%=stEC.getIdStabilimento() %>"  onclick="controllaModificheComunePerStabilimento(<%=stEC.getIdAsl() %>,'<%=stEC.getTipoAttivita() %>',<%=stEC.getOperatore().getRappLegale().getIndirizzo().getComune() %>,<%=stEC.getOperatore().getRappLegale().getIdAsl() %>,this)" >
	  </div>
	  </td>	
	
	</tr>
	</tbody>
	</table>
	
	<br><br>
	<table cellpadding="8" cellspacing="0" border="0" style="width: 100%" class="pagedList">
  <thead>
  <tr><th colspan="7">Altre Anagrafiche Correlate</th></tr>
  <tr>
  
   <th>
	<div align="center">
	<strong>Soggetto Fiico</strong>
          </div>
        </th>
        
          
          <th>
	<div align="center">
	<strong>Dati Impresa</strong>
          </div>
        </th>      

  <th>
	<div align="center">
	<strong>Numero Registrazione</strong>
          </div>
        </th>
        
          <th>
	<div align="center">
	<strong>Asl</strong>
          </div>
        </th>
          <th>
	<div align="center">
	<strong>Sede Produttiva</strong>
          </div>
        </th> 
           <th>
	<div align="center">
	<strong>SELEZIONA</strong>
          </div>
        </th>
         </tr>    
</thead>

<tbody id="bodytab">

<%
Integer idStabEc = (Integer)request.getAttribute("idStabErrataCorrige");
for(int i = 0 ; i < st.size();i++)
{
	Stabilimento stabilimento = st.get(i);
	%>
	
	
	
	<%if(stabilimento.getIdStabilimento()!=stEC.getIdStabilimento()){ %>
	<tr >
	
	  <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	   <%=   toHtml2(stabilimento.getOperatore().getRappLegale().getCognome()) +" "+toHtml2(stabilimento.getOperatore().getRappLegale().getNome()) + " "+toHtml2(stabilimento.getOperatore().getRappLegale().getCodFiscale())   %><br>
	  <%=   toHtml2(stabilimento.getOperatore().getRappLegale().getComuneNascita()) +" "+toDateasString(stabilimento.getOperatore().getRappLegale().getDataNascita()) + " "+toHtml2(stabilimento.getOperatore().getRappLegale().getSesso())%><br>
	  <%=   toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()) +" "+toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia()) %><br>
	  <%= toHtml2(ToponimiList.getSelectedValue(stabilimento.getOperatore().getRappLegale().getIndirizzo().getToponimo())) +" "+toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getVia())+" "+toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getCivico())%><br>
	  
	  
	  </div>
	  </td>	
	  
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=  "RAGIONE SOCIALE : "+ toHtml2(stabilimento.getOperatore().getRagioneSociale()) +"<br>"  %>
	   <%=  "TIPO IMPRESA : "+ TipoImpresaListStab.getSelectedValue(stabilimento.getOperatore().getTipo_impresa()) +"<br>"  %>
	   <%=  (stabilimento.getOperatore().getTipo_societa()>0)? "TIPO SOCIETA : "+ TipoSocietaListStab.getSelectedValue(stabilimento.getOperatore().getTipo_societa()) +"<br>" :"" %>
	   
	   <%= "PARTITA IVA : "+  toHtml2(stabilimento.getOperatore().getPartitaIva())+"<br>"   %>
	  <%if(stabilimento.getOperatore().getSedeLegaleImpresa()!=null){ %>
	  <%=  "SEDE LEGALE "+ toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getDescrizioneComune()) +" "+toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())  %><br>
	  <%=  toHtml2(ToponimiList.getSelectedValue(stabilimento.getOperatore().getSedeLegaleImpresa().getToponimo()))  +" "+toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getVia())+" "+toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())  %><br>
	  <%} %>
	  </div>
	  </td>	
	  
	  <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=   toHtml2(stabilimento.getNumero_registrazione())   %>
	  </div>
	  </td>	
	  
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
	  <%=  stabilimento.getIdAsl()   %>
	  </div>
	  </td>	
	  
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
  <%=   (stabilimento.getSedeOperativa().getIdIndirizzo()>0) ? "SEDE OPERATIVA : "+toHtml2(stabilimento.getSedeOperativa().getDescrizioneComune()) + " "+toHtml2(stabilimento.getSedeOperativa().getVia())+ ","+toHtml2(stabilimento.getSedeOperativa().getCivico())+"<br>" :"" %>
	  <%=(stabilimento.getTipoAttivita()==1) ? "TIPO ATTIVITA : FISSA" : "TIPO ATTIVITA : MOBILE" %>
	 
	  </div>
	  </td>	
	   <td valign="center"  align="center"  nowrap style="width: 10%;">
	  <div >
		 <input type="checkbox" name="checkStab" value="<%=stabilimento.getIdStabilimento() %>" onclick="controllaModificheComunePerStabilimento(<%=stabilimento.getIdAsl() %>,'<%=stabilimento.getTipoAttivita() %>',<%=stabilimento.getOperatore().getRappLegale().getIndirizzo().getComune() %>,<%=stabilimento.getOperatore().getRappLegale().getIdAsl() %>,this,'<%=stabilimento.getOperatore().getTipo_impresa() %>')" >
	  </div>
	  </td>	
	
	</tr>
	 <%} %>
	
	<%
	
}
%>
</tbody>
</table>
<div id ="hiddenFieldForm" style="display: none"></div>
</form>
