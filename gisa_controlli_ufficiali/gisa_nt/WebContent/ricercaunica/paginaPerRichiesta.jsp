<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.suap.base.LineaProduttivaValidazione"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<%@page import="org.aspcfs.modules.suap.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.suap.base.LineaProduttivaList"%>
<%@page import="org.aspcfs.modules.suap.base.Stabilimento"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="org.aspcfs.modules.suap.utils.CodiciRisultatiRichiesta"%>
<%@page import="org.aspcfs.modules.opu.base.OperatorePerDuplicati"%>
<%@page import="java.util.ArrayList, java.util.HashMap"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.aspcfs.modules.opu.base.LineeMobiliHtmlFields" %>
<%@page import = "org.aspcfs.utils.Jsonable" %>
<jsp:useBean id="StatiValidazioneScia" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="EsitoValidazione" class="org.aspcfs.modules.suap.base.RisultatoValidazioneRichiesta" scope="request" />
<jsp:useBean id="Richiesta" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />
<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>
<script src="javascript/jquery-ui.js"></script>

<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />

<%@ include file="../initPage.jsp"%>

<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>


<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="4"><strong><dhv:label name="">LISTA LINEE PRODUTTIVE DA VALIDARE</dhv:label></strong>
		</th>
	</tr>
	
	<tr>
		<th>MACROAREA</th>
		<th>AGGREGAZIONE</th>
		<th>LINEA DI ATTIVITA</th>
		<th>&nbsp;</th>
	</tr>
	
	<% 
	LineaProduttivaList listaLinee =  Richiesta.getListaLineeProduttive();
	if(listaLinee != null && listaLinee.size() > 0) //vuol dire che esiste almeno una linea prod con un codice nazionale da assegnare
	{
	%>
	<tr>
		<!--   <script>
		 	var campiEstesiValidazione = {};
		 </script>  -->
		<% 
		   
		   for(int indice = 0 ; indice<listaLinee.size(); indice ++) //le chiavi sono tutte le distinte linee di attivita, l'elemento è un array di stringhe, la prima è l'id tipo configurazione, la seconda è la descrizione del codice richiesto
			{
		     	LineaProduttiva lp = (LineaProduttiva) listaLinee.get(indice);
		        boolean lineaProduttivaGiaValidata = (lp.getStato() != 0 );
		        /*ArrayList<LineeMobiliHtmlFields> campiEstesiPerValidazione = lp.getHtmlFieldsValidazione();
		        
		        String campiEstesiPerValidazioneAsJsArrayString = Jsonable.getListAsJsonArrayString(campiEstesiPerValidazione);*/
		        %>
	        	<%-- <script>
	        		 
	        		campiEstesiValidazione['<%=lp.getId()%>'] = JSON.parse('<%=campiEstesiPerValidazioneAsJsArrayString %>');
	        	</script>  --%>
	        
	        	<%
		       
		     String color = "" ;
		     if(lp.getStato()==0)
		    	 color = "yellow";
		     if(lp.getStato()==1 || lp.getStato()==3 )
		    	 color = "lightgreen";
		     if(lp.getStato()==2)
		    	 color = "red";
		        
		  %>
	
	<tr>
		<td style="background-color: <%=color%> ;" width="25%"><%=lp.getMacrocategoria()%></td>
		<td style="background-color: <%=color%> ;" width="25%"><%=lp.getDescrizione_linea_attivita().split("->")[1]%></td>
		<td style="background-color: <%=color%>;" width="25%">
<!-- 		<a href="#" onclick=""> -->
		<%=lp.getDescrizione_linea_attivita().substring(lp.getDescrizione_linea_attivita().indexOf("->", lp.getDescrizione_linea_attivita().split("->")[1].length())) %>
<!-- 		</a> -->
		</td>
		
		<td width="25%">
		
		
		<%if(Richiesta.getTipoInserimentoScia()!=Richiesta.TIPO_SCIA_APICOLTURA){ %>
		
		<dhv:permission name="<%=lp.getPermesso() +"-view"%>">
					
					<%
						if(lp.getStato()==0)
						{
					%>
					<fieldset>
					<legend><%=lp.getDescr_label() %></legend>
					
					<input	<%=lineaProduttivaGiaValidata == false ?  "" : "style=\"display:none;\"" %> id="<%="idBtnValidaPerLinea"+lp.getId()%>" type="submit"
					onClick="intercettaBottoneValidaNuovo('<%=lp.getId()%>','<%=lp.getIdLookupConfigurazioneValidazione() %>','<%=lp.getDescrizione_linea_attivita().split("->")[1].replaceAll("'", "").replaceAll("\"", "") %>',null,1,<%=Richiesta.getSedeOperativa().getIdIndirizzo() %>,'<%=Richiesta.getIdOperatore() %>','<%=lp.getIdRelazioneAttivita() %>' );"
					value="TRASFERISCI IN ANAGRAFICA STABILIMENTI" /> 
					
					<input	<%=lineaProduttivaGiaValidata == false ?  "" : "style=\"display:none;\"" %> id="<%="idBtnValidaPerLinea"+lp.getId()%>" type="submit"
					onClick="intercettaBottoneValidaNuovo('<%=lp.getId()%>','<%=lp.getIdLookupConfigurazioneValidazione() %>','<%=lp.getDescrizione_linea_attivita().split("->")[1].replaceAll("'", "").replaceAll("\"", "") %>',null,2,<%=Richiesta.getSedeOperativa().getIdIndirizzo() %>,'<%=Richiesta.getIdOperatore() %>','<%=lp.getIdRelazioneAttivita() %>'  );"
					value="Respingi" />
				
					
					</fieldset>
					<%} %>
										
		</dhv:permission>
		<%}
		else{
			
			%>

			<%if(Richiesta.isValidabilitaApicoltura()==true && (Richiesta.getCodiceAziendaApicoltura()!=null && !"".equalsIgnoreCase(Richiesta.getCodiceAziendaApicoltura())))
			{ %>
			
			<%
						if(lp.getStato()==0)
						{
					%>
			<fieldset>
					<legend><%=lp.getDescr_label() %></legend>
					
					<input	<%=lineaProduttivaGiaValidata == false ?  "" : "style=\"display:none;\"" %> id="<%="idBtnValidaPerLinea"+lp.getId()%>" type="submit"
					onClick="intercettaBottoneValidaNuovo('<%=lp.getId()%>','<%=lp.getIdLookupConfigurazioneValidazione() %>','<%=lp.getDescrizione_linea_attivita().replaceAll("'", "") %>','<%=Richiesta.getCodiceAziendaApicoltura() %>',1,null,'<%=Richiesta.getIdOperatore() %>','<%=lp.getIdRelazioneAttivita() %>'  );"
					value="TRASFERISCI IN ANAGRAFICA STABILIMENTI" /> 
					
<%-- 					<input	<%=lineaProduttivaGiaValidata == false ?  "" : "style=\"display:none;\"" %> id="<%="idBtnValidaPerLinea"+lp.getId()%>" type="submit" --%>
<%-- 					onClick="intercettaBottoneValidaNuovo('<%=lp.getId()%>','<%=lp.getIdLookupConfigurazioneValidazione() %>','<%=lp.getDescrizione_linea_attivita().replaceAll("'", "") %>','<%=Richiesta.getCodiceAziendaApicoltura() %>',2);" --%>
<!-- 					value="Respingi" /> -->
					
					
					
					</fieldset>
					<%} 
			}
			else
			{
				%>
				
				<%
						if(lp.getStato()==0)
						{
					%>
					
				<dhv:permission name="<%=lp.getPermesso() +"-view"%>">
				<fieldset>
					<legend><%=lp.getDescr_label() %></legend>
				ATTENZIONE! PER VALIDARE LA SCIA DI APICOLTURA OCCORRE ESEGUIRE PRIMA LA VALIDAZIONE TRAMITE LA BDAR.<br>
				
					<input	<%=lineaProduttivaGiaValidata == false ?  "" : "style=\"display:none;\"" %> id="<%="idBtnValidaPerLinea"+lp.getId()%>" type="submit"
					onClick="intercettaBottoneValidaNuovo('<%=lp.getId()%>','<%=lp.getIdLookupConfigurazioneValidazione() %>','<%=lp.getDescrizione_linea_attivita().replaceAll("'", "") %>','<%=Richiesta.getCodiceAziendaApicoltura() %>',2);"
					value="Respingi" />

				</fieldset>
					</dhv:permission>
				<%
						}
			}
		}
		%>
		
		</td>
		</tr>
		
		

	<%  }
		}  %>
	<input type="hidden" id="tipoCodiceRichiesto" name="tipoCodiceRichiesto" value =""/>
</table>



