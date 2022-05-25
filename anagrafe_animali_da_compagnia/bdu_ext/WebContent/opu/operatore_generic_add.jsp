<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="opu.operatore"></dhv:label></strong>
    </th>
  </tr>
 
  
   
  <tr>
   <td nowrap class="formLabel">
        <dhv:label name="opu.operatore.ragione_sociale"></dhv:label>
      </td>
      <td>
		<input type="text" size="20" maxlength="200" id = "ragioneSociale" name="ragioneSociale" value="<%=(Operatore.getRagioneSociale() != null) ? Operatore.getRagioneSociale() : "" %>">
		<font color = "red">*</font>
	 </td>
 </tr>
 
    <tr>
 <td nowrap class="formLabel">
        <dhv:label name="opu.operatore.piva"></dhv:label>
      </td>
      <td>
		<input type="text" size="20" maxlength="11" id = "partitaIva" name="partitaIva" value="<%=(Operatore.getPartitaIva() != null) ? Operatore.getPartitaIva() : "" %>">
		<font color = "red">*</font>
	 </td>
 </tr>
 
   <tr>
   <td nowrap class="formLabel">
        <dhv:label name="opu.operatore.cf"></dhv:label>
      </td>
      <td>
		<input type="text" size="20" maxlength="16" id = "codFiscale" name="codFiscale" value="<%=(Operatore.getCodFiscale() != null) ? Operatore.getCodFiscale() : "" %>">
			
		
	 </td>
 </tr>
 
 

 
   <tr>
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="opu.operatore.note"></dhv:label>
    </td>
    <td><TEXTAREA NAME="note" ROWS="3" COLS="50"><%= toString(Operatore.getNote()) %></TEXTAREA></td>
  </tr>

</table>

<br/><br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="opu.sede_legale"></dhv:label></strong>
	  </th></tr>
	  
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
      <dhv:label name="opu.sede_legale.provincia"></dhv:label>
    </td>
    <td>
        	<select name = "searchcodeIdprovincia" id = "searchcodeIdprovincia">
        	<option value = "-1">Seleziona Provincia</option>
        	</select><font color = "red">(*)</font>
       		<input type = "hidden" name = "searchcodeIdprovinciaTesto" id = "searchcodeIdprovinciaTesto"/>
    </td>
  </tr>
  	<tr>
		<td nowrap class="formLabel" name="province" id="province" >
      		<dhv:label name="opu.sede_legale.comune"></dhv:label>
    	</td> 
    <td > 
   
   <select name = "searchcodeIdComune" id = "searchcodeIdComune">
        	<option value = "-1">Seleziona Comune</option>
        	</select><font color = "red">(*)</font>
     <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
	<input type = "hidden" name = "searchcodeIdComuneTesto" id = "searchcodeIdComuneTesto"/>
	
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="opu.sede_legale.indirizzo"></dhv:label>
    </td>
    <td>
<select name = "via" id = "via">
<option value = "-1" selected="selected">Seleziona Indirizzo</option>
</select><font color = "red">(*)</font>

<input type = "hidden" name = "viaTesto" id = "viaTesto"/>

    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="opu.sede_legale.co"></dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="presso" maxlength="80" value = "<%="" %>">
    </td>
  </tr>

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="opu.sede_legale.cap"></dhv:label>
    </td>
    <td>
      <input type="text" size="28" id="cap" name="cap" maxlength="5" value = "<%=toHtmlValue("") %>">
    </td>
  </tr>
  

   <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="opu.sede_legale.latitudine"></dhv:label></td>
    <td>
    	<input type="text" id="latitudine" name="latitudine" size="30" value="" >
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" ><dhv:label name="opu.sede_legale.longitudine"></dhv:label></td>
    <td><input type="text" id="longitudine" name="longitudine" size="30" value="" ></td>
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
      <strong><dhv:label name="opu.soggetto_fisico"></dhv:label></strong>
      
    </th>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.cf"></dhv:label>
    </td>
    <td>
<select name = "codFiscaleSoggetto" id = "codFiscaleSoggetto"><option value = "-1">Seleziona Codice Fiscale</option></select>
       		<input type = "hidden" name = "codFiscaleSoggettoTesto" id = "codFiscaleSoggettoTesto"/>
<font color = "red">(*)</font>
    </td>
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.sesso"></dhv:label>
    </td>
    <td>
      <input type="radio" name="sesso" id="sesso1" value="M" checked="checked">M
       <input type="radio" name="sesso" id="sesso2" value="F">F
    </td>
  </tr>
  
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.nome"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "nome" name="nome" value=""><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.cognome"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "cognome" name="cognome" value=""><font color="red">*</font>
    </td>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="opu.soggetto_fisico.data_nascita"></dhv:label>
      </td>
      <td>
      	<input readonly type="text" id="dataNascita" name="dataNascita" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      	
      
  
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.comune_nascita"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "comuneNascita" name="comuneNascita" value="">
    </td>
  </tr>
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.provincia_nascita"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "provinciaNascita" name="provinciaNascita" value="">
    </td>
  </tr>
<tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.provincia_residenza"></dhv:label>
    </td>
    <td>
    
    <select id = "addressLegaleCountry" name="addressLegaleCountry">
        	<option value = "-1">Seleziona Provincia</option>
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
        	<option value = "-1">Seleziona Comune</option>
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
<option value = "-1" selected="selected">Seleziona Indirizzo</option>
</select><font color = "red">(*)</font>

<input type = "hidden" name = "addressLegaleLine1Testo" id = "addressLegaleLine1Testo"/>
<!--      <input type="text" size="30" maxlength="50" id = "addressLegaleLine1" name="addressLegaleLine1" value="">-->
    </td>
  </tr>
  
   <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.cap_residenza"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "addressLegaleZip" name="addressLegaleZip" value="">
    </td>
  </tr>
    
  
  
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.mail"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "email" name="email" value="">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.telefono"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "telefono1" name="telefono1" value="">
    </td>
    
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.telefono2"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "telefono2" name="telefono2" value="">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.soggetto_fisico.fax"></dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "fax" name="fax" value="">
    </td>
    
  </tr>
  

</table>


