<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--
	



	--%>

<%@page import="org.aspcfs.modules.lineeattivita.base.LineeCampiEstesi"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="AllevamentoBDN" class="org.aspcfs.modules.allevamenti.base.AllevamentoAjax" scope="request"/>
<jsp:useBean id="LineeCampiEstesi" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="DiffenzeBDN" class="java.util.ArrayList" scope="request"/>

<jsp:useBean id="idRel" class="java.lang.String" scope="request"/>


<%@page import="java.util.Map.Entry"%>

<%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popLookupSelect.js"></script>
		
<html>
<head>
<title>INTERROGAZIONE BDN</title>

</head>

<body>
		<script>
			function rosso(){					
					$('div.lampeggio').each(function(d){
					    this.style.color = 'red';
					});	
					setTimeout("bianco()",500);
				
			
			}
			function bianco(){
				$('div.lampeggio').each(function(d){
					    this.style.color = 'white';
				});      
					
				setTimeout("rosso()",500);		
			}
		</script>
		
		
		
		<dhv:evaluate if="<%= !(DiffenzeBDN.isEmpty()) %>">
		
			<h3 style="color:red;text-align:center">RISCONTRATO DISALLINEAMENTO CON BDN</h3>
			
		</dhv:evaluate>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr><th colspan="2"><strong>Dati Allevamento in BDN</strong></th></tr>
		<tr class="containerBody">
		<td nowrap class="formLabel">
		
		<div <% if (DiffenzeBDN.contains("denominazione")) {%> class="lampeggio" <%  } %> 
			<dhv:label name="">Denominazione</dhv:label></div>		
		</td>
		<td><%= AllevamentoBDN.getDenominazione() %></td></tr>
		<tr class="containerBody"><td nowrap class="formLabel"><dhv:label  name="organization.accountNumbera">Codice Azienda</dhv:label></td>
		<td><%= AllevamentoBDN.getCodice_azienda() %></td></tr>
		<tr class="containerBody"><td nowrap class="formLabel">
		<div <% if (DiffenzeBDN.contains("codice_fiscale")) {%>class="lampeggio"<%}%>
			<dhv:label  name="organization.accountNumbera">Codice Fiscale Proprietario</dhv:label>
		</div></td>
		<td><%= AllevamentoBDN.getCodice_fiscale() %></td></tr>
		<tr class="containerBody">
		<td nowrap class="formLabel">
		<div <% if ((DiffenzeBDN.contains("indirizzo"))||(DiffenzeBDN.contains("comune"))) {%>class="lampeggio"<%}%>>Indirizzo</div>
		
		</td>
		<td>
		
		<%= AllevamentoBDN.getIndirizzo()%> <%= AllevamentoBDN.getComune()%>
		</td></tr>
		<tr class="containerBody"><td nowrap class="formLabel">
		<div <% if (DiffenzeBDN.contains("data_inizio_attivita")) {%>class="lampeggio"<%}%>
			<dhv:label name="allevamenti.allevamenti_add.date1a">Data Inizio Attività</dhv:label>
		</div>	
		</td>
		<td><%= AllevamentoBDN.getData_inizio_attivita() %></td></tr>
		<%if (AllevamentoBDN.getData_fine_attivita()!= null){ %>
			<tr class="containerBody"><td nowrap class=formLabel>
				<div <% if (DiffenzeBDN.contains("data_fine_attivita")) {%>class="lampeggio"<%}%>
					<dhv:label name="allevamenti.allevamenti_add.date1a">Stato Allevamento:</dhv:label>
				</div>
			</td>
			<td><font color="red">CESSATO</font> in data <%= AllevamentoBDN.getData_fine_attivita() %></td></tr>
		<% } %>
		<tr class="containerBody">
		<td nowrap class="formLabel">
		<div <% if (DiffenzeBDN.contains("specie_allevata")) {%>class="lampeggio"<%}%>
			<dhv:label name="organization.specieAlleva">Specie Allevata</dhv:label>
		</div>	
		</td>
		<td>
		<%= AllevamentoBDN.getDescrizione_specie() %>
		</td>
		</tr>
		<tr class="containerBody">
		<td nowrap class="formLabel">
			<div <% if (DiffenzeBDN.contains("numero_capi")) {%>class="lampeggio"<%}%>><font color="red">Numero di Capi Totali</font></div>
		</td>
		<td><strong><%= AllevamentoBDN.getNumero_capi() %></strong></td></tr>
		<tr class="containerBody">
		<td nowrap class="formLabel">
			<div <% if (DiffenzeBDN.contains("codice_tipo_allevamento")) {%>class="lampeggio"<%}%>><font color="red">Codice Tipo Allevamento</font></div>
		</td>
		<td><strong><%= AllevamentoBDN.getCodiceTipoAllevamento() %></strong></td></tr>
		<tr class="containerBody">
		<td nowrap class="formLabel">
			<div <% if (DiffenzeBDN.contains("note")) {%>class="lampeggio"<%}%>>Note:</div>
		</td>
		<td>
		<%if (AllevamentoBDN.getNote() != null){ %>
			 <%= AllevamentoBDN.getNote() %>
		<% } %>
		</td></tr>	
		
		
			<tr class="containerBody">
		<td nowrap class="formLabel">
			<div <% if (DiffenzeBDN.contains("cfProprietario")) {%>class="lampeggio"<%}%>>Cf Proprietario</div>
		</td>
		<td>
		<%if (AllevamentoBDN.getCfProprietario() != null){ %>
			 <%= AllevamentoBDN.getCfProprietario() %>
		<% } %>
		</td></tr>	
		
			<tr class="containerBody">
		<td nowrap class="formLabel">
			<div <% if (DiffenzeBDN.contains("cfDetentore")) {%>class="lampeggio"<%}%>>Cf Detentore</div>
		</td>
		<td>
		<%if (AllevamentoBDN.getCfDetentore() != null){ %>
			 <%= AllevamentoBDN.getCfDetentore() %>
		<% } %>
		</td></tr>	
		
		
		
			

		</table>
		<script>
			setTimeout( "rosso()",500);
		</script>
		<form method="post" action = "OpuStab.do?command=SincronizzaConBDN">
	<input type = "hidden" name = "denominazione" value = "<%=OrgDetails.getOperatore().getRagioneSociale()%>">
		
		
		<% 
Iterator<LineaProduttiva> itLinee= OrgDetails.getListaLineeProduttive().iterator();
String codAzienda = "" ;
String codSpecie = "" ;



while(itLinee.hasNext())
{
	LineaProduttiva lp= itLinee.next();
	
	
	
	if (lp.getIdTipoGestioneScia()==LineaProduttiva.TIPO_GESTIONE_SCIA_AZIENDE_ZOOTECNICHE)
	{
		LinkedHashMap listaCampiEstesiAziendeZootecniche = (LinkedHashMap)  LineeCampiEstesi.get(lp.getId_rel_stab_lp());
		
	
		Set<Entry>  entrys2 = listaCampiEstesiAziendeZootecniche.entrySet();
		
		for(Entry el : entrys2)
		{
			
			

			LineeCampiEstesi campo = (LineeCampiEstesi) el.getValue();
			
			if (campo.getLabel_campo().equalsIgnoreCase("codice specie"))
					{
				codSpecie=campo.getValore_campo();
				codAzienda = lp.getCodice_ufficiale_esistente();
					}
		}
	}
}
		%>
		
		<input type = "hidden" name = "idFiscale" value = "<%=OrgDetails.getOperatore().getRappLegale().getCodFiscale().trim() %>">
		<input type = "hidden" name = "orgId" value = "<%=OrgDetails.getIdStabilimento() %>">
		<input type = "hidden" name = "codAzienda" value = "<%= AllevamentoBDN.getCodice_azienda() %>">
		<input type="hidden"  id="dataFineAttivita" name="dataFineAttivita" value="<%= AllevamentoBDN.getData_fine_attivita() %>"/>
		<input type="hidden"  id="specieAllevata" name="specieAllevata" value="<%="0"+ AllevamentoBDN.getSpecie_allevata() %>"/>
		<input type="hidden"  id="numeroCapi" name="numeroCapi" value="<%= AllevamentoBDN.getNumero_capi() %>"/>
		<input type="hidden"  id="idRel" name="idRel" value="<%= idRel%>"/>
		
		<%if (AllevamentoBDN.getDenominazione()!=null && !(DiffenzeBDN.isEmpty())){ %>
		<input type = "submit" value = "Sincronizza">
		<%} %>
		</form>
		
	</body>
	
</html>
		