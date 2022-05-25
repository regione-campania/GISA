<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>


 
<script language="JavaScript" TYPE="text/javascript"SRC="javascript/gestoreCodiceFiscale.js"></script>
 
<script>
	function checkLineaProduttiva()
	{
		document.forms[0].doContinueStab.value = 'false';
		document.forms[0].submit();
	}
	
	var campoLat;
	var campoLong;
  	function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
  	{
	   campoLat = campo_lat;
	   campoLong = campo_long;
	   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
	   
	   
	}
	function setGeocodedLatLonCoordinate(value)
	{
		campoLat.value = value[1];;
		campoLong.value =value[0];
		
	}
</script>



<br/>

<%
int tipoAtt = -1 ;
if (newStabilimento.getListaLineeProduttive().size()>0 && ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getInfoStab()!= null)
{
	tipoAtt =  ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getInfoStab().getTipoAttivita();
}
%>

Lista Stabilimenti Tot <%=newStabilimento.getOperatore().getListaStabilimenti().size() %>
<table cellpadding="4"  id = "sedestabilimento" cellspacing="0" border="0" width="100%" class="details">

	<tr>
			<th colspan="5">
				<label id = "label_sede">Lista Stabilimenti</label>
			</th>
			<tr>
				<th>Numero Registrazione</th>
				<th>Indirizzo</th>
				<th>Comune</th>
				<th>Provincia</th>
				<th>Attvita Principale</th>
			</tr>
		<%
		Iterator it = newStabilimento.getOperatore().getListaStabilimenti().iterator();
		while(it.hasNext())
		{
			Stabilimento thisStab = (Stabilimento )it.next();
			%>
			<tr>
			<td><%=toHtml(thisStab.getLineaProduttivaPrimaria().getNumeroRegistrazione()) %></td>
			<td><%=thisStab.getSedeOperativa().getVia() %></td>
			<td><%=thisStab.getSedeOperativa().getDescrizioneComune() %></td>
			<td><%=thisStab.getSedeOperativa().getProvincia() %></td>
			<td><%=toHtml(thisStab.getLineaProduttivaPrimaria().getAttivita()) %></td>
			</tr>
			
			<%
		}
		%>
		
	</table>
<br><br>
<%if(newStabilimento.getListaLineeProduttive().size()==0) {%>
<input type = "button" value="Aggiungi Stabilimento" onclick="document.getElementById('infoStab').style.display=''">
<%} %>
<br><br>


<div <%if(newStabilimento.getListaLineeProduttive().size()==0){ %>style="display: none"<%} %>  id ="infoStab">
<table cellpadding="4"  id = "sedestabilimento" cellspacing="0" border="0" width="100%" class="details" <%if(tipoAtt==3){%> style="display: none" <%} %>>

	<tr>
			<th colspan="2">
				<label id = "label_sede">Dati Stabilimento</label>
			</th>
		
		
	</tr>
	

	<tr>
		<td class="formLabel">
			<dhv:label name="opu.stabilimento.asl">Asl</dhv:label>
		</td>
		<td>
			<dhv:evaluate if="<%=(User.getSiteId() <0)%>">
				<p id="descrizioneasl">
					<%=AslList.getSelectedValue(newStabilimento.getSedeOperativa().getIdAsl())%>
				</p>
			<input type="hidden" name="idAsl" id="idAsl" value="<%=newStabilimento.getSedeOperativa().getIdAsl()%>">
			</dhv:evaluate> 
			<dhv:evaluate if="<%=(User.getSiteId() >0)%>">
				<p id="descrizioneasl"><%=AslList.getSelectedValue(User.getSiteId())%></p>
				<input type="hidden" name="idAsl" id="idAsl" value="<%=User.getSiteId()%>">
			</dhv:evaluate>
		</td>
	</tr>
	
	<tr>
		<td class="formLabel">
			Denominazione
		</td>
		<td>
			<input type = "text" name = "denominazione" value="<%=toHtml(newStabilimento.getDenominazione()) %>" id="denominazione" required="required">
		</td>
	</tr>
	
	<tr style ="display:none">
		<td class="formLabel">
			<dhv:label name="opu.sede_legale.inregione"></dhv:label>
		</td>
		<td>
			<select name="inregione" onchange="checkLineaProduttiva()" id="inregione">
				<option value="si" <%=(!newStabilimento.isFlagFuoriRegione()) ? "selected" : ""%>>SI</option>
				<option value="no" <%=(newStabilimento.isFlagFuoriRegione()) ? "selected" : ""%>>NO</option>
			</select>
		</td>
		<input type="hidden" name="doContinueStab" id="doContinueStab" value="" />
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			<dhv:label	name="opu.stabilimento.provincia"></dhv:label>
		</td>
		<td>
			<dhv:evaluate if="<%=(!(User.getSiteId() > -1) || newStabilimento.isFlagFuoriRegione()) && User.getUserRecord().getSuap()==null%>">
				<select name="searchcodeIdprovincia" id="searchcodeIdprovincia">
					<option value="<%=newStabilimento.getSedeOperativa().getIdProvincia()%>"><%=(newStabilimento.getSedeOperativa().getIdProvincia()>0)? newStabilimento.getSedeOperativa().getDescrizione_provincia() : "Inserire le prime 4 lettere" %></option>
				</select>
				<font color="red">(*)</font>
				<input type="hidden" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" />
			</dhv:evaluate> 
		
			<dhv:evaluate if="<%=(User.getSiteId() > -1 && !newStabilimento.isFlagFuoriRegione()) || User.getUserRecord().getSuap()!=null%>">
				<%=provinciaAsl.getDescrizione()%>
				<input type="hidden" name="searchcodeIdprovinciaAsl" id="searchcodeIdprovinciaAsl" value="<%=provinciaAsl.getCodice()%>" />
				<input type="hidden" name="searchcodeIdprovinciainput" id="searchcodeIdprovinciainput" value="<%=provinciaAsl.getDescrizione()%>" />
				
			</dhv:evaluate>
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel" name="province" id="province">
			<dhv:label name="opu.stabilimento.comune"></dhv:label>
		</td>
		<td>
			<select name="searchcodeIdComune" id="searchcodeIdComune">
				<option value="<%=newStabilimento.getSedeOperativa().getComune()%>" selected="selected"><%=(newStabilimento.getSedeOperativa().getComune()>0)? newStabilimento.getSedeOperativa().getDescrizioneComune() : "Inserire le prime 4 lettere" %></option>
			</select> 
			<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; *</font> 
			<input	type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" />
		
			
			
		
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			<dhv:label	name="opu.stabilimento.indirizzo"></dhv:label>
		</td>
		<td>
			<select name="via" id="via">
				<option value="<%=newStabilimento.getSedeOperativa().getIdIndirizzo()%>" selected="selected"><%=newStabilimento.getSedeOperativa().getVia()%></option>
			</select> 
			<input type="hidden" name="viaTesto" id="viaTesto" />
			<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; *</font>
		</td>
	</tr>
	<tr style="display: none">
		<td nowrap class="formLabel">
			<dhv:label name="opu.stabilimento.co"></dhv:label>
		</td>
		<td>
			<input type="text" size="40" name="presso" maxlength="80" value="<%=""%>">
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			<dhv:label name="opu.stabilimento.cap"></dhv:label>
		</td>
		<td>
			<input  type="text" size="28" id="cap" name="cap" maxlength="5" value="<%=newStabilimento.getSedeOperativa().getCap()%>">
		</td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel" nowrap>
			<dhv:label name="opu.stabilimento.latitudine"></dhv:label>
		</td>
		<td>
			<input type="text" id="latitudine" name="latitudine" readonly="readonly" size="30" value="<%=((Double) newStabilimento.getSedeOperativa().getLatitudine() != null) ? newStabilimento.getSedeOperativa().getLatitudine()+ "" : ""%>">
		</td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="opu.stabilimento.longitudine">Longitude</dhv:label>
		</td>
		<td>
			<input type="text" id="longitudine" readonly="readonly" name="longitudine" size="30" value="<%=((Double) newStabilimento.getSedeOperativa().getLongitudine() != null) ? newStabilimento.getSedeOperativa().getLongitudine()+ "" : ""%>">
		</td>
	</tr>
	 <tr style="display: block">
    <td colspan="2">
    	<input id="coordbutton" type="button" value="Calcola Coordinate" 
    	onclick="javascript:showCoordinate(document.getElementById('viainput').value, document.getElementById('searchcodeIdComuneinput').value,document.getElementById('searchcodeIdprovinciainput').value, document.getElementById('cap').value, document.getElementById('latitudine'), document.getElementById('longitudine'));" /> 
    </td>
  </tr> 
</table>


<br>

<%
String nomePaginaInclusa =newStabilimento.getPageInclude() ;

if (!nomePaginaInclusa.equals(""))
{
	
%>
<%-- <jsp:include page="<%= "./"+nomePaginaInclusa+".jsp"%>" flush="true" /> --%>
<%} %>
<br/>


<jsp:include page="./opu_linee_attivita_add.jsp" flush="true" />



<br/>
<input type="button" value="Salva" name="Save" onClick="verificaSoggetto(document.getElementById('codFiscaleSoggetto'))"> 

</div>




	

