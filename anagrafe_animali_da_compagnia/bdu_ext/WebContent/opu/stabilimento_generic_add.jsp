<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<!-- DATI DELLO STABILIMENTO  -->
</br></br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="opu.stabilimento.sede"></dhv:label></strong>
	  </th></tr>
	  
	   <tr>
          <td class="formLabel">
            <dhv:label name="opu.stabilimento.asl">Asl</dhv:label>
          </td>
          <td>
        	<p id = "descrizioneasl"><%=newStabilimento.getSedeOperativa().getDescrizioneAsl()  %></p>
        	<input type = "hidden" name = "idAsl" id = "idAsl" value = "<%=newStabilimento.getSedeOperativa().getIdAsl() %>" >
            
          </td>
        </tr>
 <tr>
          <td class="formLabel">
            <dhv:label name="opu.sede_legale.inregione"></dhv:label>
          </td>
          <td>
        	<select name = "inregione">
        	<option value = "si"> SI</option>
        	<option value = "no"> NO</option>
        	</select>
            
          </td>
        </tr>
         	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="opu.stabilimento.provincia"></dhv:label>
    </td>
    <td>
        	<select name = "searchcodeIdprovincia" id = "searchcodeIdprovincia">
        	<option value = "<%=newStabilimento.getSedeOperativa().getIdProvincia() %>" selected="selected">Inserire le prime 4 lettere</option>
        	</select>
       		<input type = "hidden" name = "searchcodeIdprovinciaTesto" id = "searchcodeIdprovinciaTesto"/>
    </td>
  </tr>
  	<tr>
		<td nowrap class="formLabel" name="province" id="province" >
      		<dhv:label name="opu.stabilimento.comune"></dhv:label>
    	</td> 
    <td > 
   <select name = "searchcodeIdComune" id = "searchcodeIdComune">
        	<option value = "<%=newStabilimento.getSedeOperativa().getComune() %>" selected="selected">Inserire le prime 4 lettere</option>
        	</select>
     <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
	<input type = "hidden" name = "searchcodeIdComuneTesto" id = "searchcodeIdComuneTesto"/>
	
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="opu.stabilimento.indirizzo"></dhv:label>
    </td>
    <td>
<select name = "via" id = "via">
<option value = "<%=newStabilimento.getSedeOperativa().getIdIndirizzo() %>" selected="selected"><%=newStabilimento.getSedeOperativa().getVia() %></option>
</select>

<input type = "hidden" name = "viaTesto" id = "viaTesto"/>

    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="opu.stabilimento.co"></dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="presso" maxlength="80" value = "<%="" %>">
    </td>
  </tr>

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="opu.stabilimento.cap"></dhv:label>
    </td>
    <td>
      <input type="text" size="28" id="cap" name="cap" maxlength="5" value = "<%=newStabilimento.getSedeOperativa().getCap() %>">
    </td>
  </tr>
  
 

   <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="opu.stabilimento.latitudine"></dhv:label></td>
    <td>
    	<input type="text" id="latitudine" name="latitudine" size="30" value="<%=((Double)newStabilimento.getSedeOperativa().getLatitudine() != null) ? newStabilimento.getSedeOperativa().getLatitudine()+"" : "" %>" >
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" ><dhv:label name="opu.stabilimento.longitudine">Longitude</dhv:label></td>
    <td><input type="text" id="longitudine" name="longitudine" size="30" value="<%=((Double)newStabilimento.getSedeOperativa().getLongitudine() != null) ? newStabilimento.getSedeOperativa().getLongitudine()+"" : "" %>" ></td>
  </tr>
  <!-- <tr style="display: block">
    <td colspan="2">
    	<input id="coordbutton" type="button" value="Calcola Coordinate" 
    	onclick="javascript:showCoordinate(document.getElementById('via').value, document.forms['addSede'].comune.value,document.forms['addSede'].provincia.value, document.forms['addSede'].cap.value, document.forms['addSede'].latitudine, document.forms['addSede'].longitudine);" /> 
    </td>
  </tr>  -->
</table>

</br></br>


  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="opu.stabilimento.soggetto_fisico"></dhv:label></strong>
      
    </th>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.cf"></dhv:label>
    </td> 
    <td>
<select name = "codFiscaleSoggetto" id = "codFiscaleSoggetto">
<option value = "<%=newStabilimento.getRappLegale().getIdSoggetto() %>" selected="selected"><%=newStabilimento.getRappLegale().getCodFiscale() %></option>
</select>
  <input type = "hidden" name = "codFiscaleSoggettoTesto" id = "codFiscaleSoggettoTesto" value = "<%=newStabilimento.getRappLegale().getCodFiscale() %>"/>

    </td>
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.sesso"></dhv:label>
    </td>
    <td>
      <input type="radio" name="sesso" id="sesso1" value="M"  <%=(newStabilimento.getRappLegale().getSesso().equalsIgnoreCase("m"))? "checked=\"checked\"" : "" %>>M
       <input type="radio" name="sesso" id="sesso2" value="F" <%=(newStabilimento.getRappLegale().getSesso().equalsIgnoreCase("f"))? "checked=\"checked\"" : "" %>>F
    </td>
  </tr>
  
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.nome"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "nome" name="nome" value="<%=newStabilimento.getRappLegale().getNome() %>"><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.cognome"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "cognome" name="cognome" value="<%=newStabilimento.getRappLegale().getCognome() %>"><font color="red">*</font>
    </td>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="opu.stabilimento.soggetto_fisico.data_nascita"></dhv:label>
      </td>
      <td>
      	<input readonly type="text" id="dataNascita" name="dataNascita" size="10" value = "<%=newStabilimento.getRappLegale().getDataNascitaString()%>" />
		<a href="#" onClick="cal19.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      	
      
  
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.comune_nascita"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "comuneNascita" name="comuneNascita" value="<%=newStabilimento.getRappLegale().getComuneNascita() %>">
    </td>
  </tr>
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.provincia_nascita"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "provinciaNascita" name="provinciaNascita" value="<%=newStabilimento.getRappLegale().getProvinciaNascita() %>">
    </td>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.provincia_residenza"></dhv:label>
    </td>
    <td>
    
    <select id = "addressLegaleCountry" name="addressLegaleCountry">
        	<option value = "-1">Inserire le prime 4 lettere</option>
        	</select><font color = "red">(*)</font>
       		<input type = "hidden" id = "addressLegaleCountryTesto" name="addressLegaleCountryTesto"/>
       		
<!--      <input type="text" size="30" maxlength="50" id = "addressLegaleCity" name="addressLegaleCity" value="">-->
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.comune_residenza"></dhv:label>
    </td>
    <td>
    
     <select name = "addressLegaleCity" id = "addressLegaleCity">
        	<option value = "-1">Inserire le prime 4 lettere</option>
        	</select><font color = "red">(*)</font>
     <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
	<input type = "hidden" name = "addressLegaleCityTesto" id = "addressLegaleCityTesto"/>
<!--      <input type="text" size="30" maxlength="50" id = "" name="addressLegaleCountry" value="">-->
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.indirizzo"></dhv:label>
    </td>
    <td>
   
    
    
    <select name = "addressLegaleLine1" id = "addressLegaleLine1">
<option value = "-1" selected="selected">Inserire le prime 4 lettere</option>
</select><font color = "red">(*)</font>

<input type = "hidden" name = "addressLegaleLine1Testo" id = "addressLegaleLine1Testo"/>
<!--      <input type="text" size="30" maxlength="50" id = "addressLegaleLine1" name="addressLegaleLine1" value="">-->
    </td>
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.cap_residenza"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "addressLegaleZip" name="addressLegaleZip" value="">
    </td>
  </tr>
  

  
  
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.mail"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "email" name="email" value=<%=newStabilimento.getRappLegale().getEmail()%>"">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.telefono"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "telefono1" name="telefono1" value="<%=newStabilimento.getRappLegale().getTelefono1()%>">
    </td>
    
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.telefono2"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "telefono2" name="telefono2" value="<%=newStabilimento.getRappLegale().getTelefono2() %>">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.fax"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "fax" name="fax" value="<%=newStabilimento.getRappLegale().getFax() %>">
    </td>
    
  </tr>
  

</table>

<br/><br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="5"><strong><dhv:label name="opu.stabilimento.linea_produttiva"></dhv:label></strong>
		</th>
	</tr>
	
	<tr>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.categoria"></dhv:label></strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.descrizione"></dhv:label></strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.dal"></dhv:label></strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.al"></dhv:label></strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong><dhv:label name="opu.stabilimento.linea_produttiva.stato"></dhv:label></strong>
		</th>
	</tr>
	
	<dhv:evaluate if="<%= (newStabilimento.getListaLineeProduttive().size()>0) %>">
	
	<% 
		Iterator<LineaProduttiva> itLplist = newStabilimento.getListaLineeProduttive().iterator() ;
	int indice = 1 ;
		while(itLplist.hasNext())
		{
			LineaProduttiva lp = itLplist.next();
	%>
		<input type="hidden" name="idLineaProduttiva"
			id="idLineaProduttiva<%=indice %>"
			value="<%=lp.getId() %>">
	<tr>
	<td><%=lp.getCategoria() %></td>
	<td><%=lp.getAttivita() %></td>
	
	<td>
		<input readonly type="text" id="dataInizio<%=lp.getId() %>" name="dataInizio<%=lp.getId() %>" size="10" value = "<%=lp.getDataInizioasString() %>" />
		<a href="#" onClick="cal19.select(document.forms[0].dataInizio<%=lp.getId() %>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
	</td>
	<td>
	<input readonly type="text" id="dataFine<%=lp.getId() %>" name="dataFine<%=lp.getId() %>" size="10" value = "<%=lp.getDataFineasString() %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataFine<%=lp.getId()%>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
	</td>
	<td>Attivo<input type = "hidden" name = "stato<%=lp.getId() %>" value = "1"></td>
	</tr>
			<%
		indice ++ ;	
		} %>
		
	</dhv:evaluate>
	<input type = "hidden" name = "numLineeProduttive" value = "<%=newStabilimento.getListaLineeProduttive().size()%>">
		<input type="hidden" name="idLineaProduttiva" id="idLineaProduttiva" value="">
		<input type="hidden" name="dataInizio" id="dataInizio" value="">
		<input type="hidden" name="dataFine" id="dataFine" value="">
		<input type="hidden" name="stato" id="stato" value="">

	
</table>
<a href="javascript:popUp('LineaProduttivaAction.do?command=Search&popup=true&tipoSelezione=multipla');">
<dhv:label name="opu.stabilimento.linea_produttiva.selezione"></dhv:label>
</a>

<br>
<br>
<br>