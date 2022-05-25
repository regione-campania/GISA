<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.BUtenteAll"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="it.us.web.bean.vam.EsameObiettivo" %>
<%@ page import="it.us.web.bean.vam.EsameObiettivoEsito" %>
<%@ page import="it.us.web.bean.vam.CartellaClinica" %>
<%@ page import="it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito" %>
<%@ page import="java.util.Date" %>
<%@ page import="it.us.web.bean.BUtente" %>

<!-- Script per la gestione della febbre -->
<script src="js/vam/cc/esamiObiettivo/swfobject_modified.js" type="text/javascript"></script>
<script language="javascript">
	function scriviTemperatura(a){
		document.getElementById('valore_temperatura').value=a;
	}
</script>

	

<script language="JavaScript" type="text/javascript" src="js/vam/cc/esamiObiettivo/add.js"></script>

<%  
 CartellaClinica cc = (CartellaClinica)session.getAttribute("cc");
 ArrayList<String> listDescrizioni = (ArrayList<String>)  request.getAttribute("descrizioniEOTipo"); 
 ArrayList<EsameObiettivo> esameObiettivos = (ArrayList<EsameObiettivo>)request.getAttribute("esameObiettivos");
 int countDescrizioni = 0;
 Iterator<ArrayList<LookupEsameObiettivoEsito>> superList = ((ArrayList<ArrayList<LookupEsameObiettivoEsito>>)request.getAttribute("superList")).iterator(); 
%>

<form action="vam.cc.esamiObiettivo.ToEditGenerale.us" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idApparato"        value="<c:out value="${idApparato}"/>"/>
	
	<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>	
	<h4 class="titolopagina">
	   	Dettaglio esame obiettivo
	</h4>	
	
	<table class="tabella">
		
		<tr>
			<th colspan="3">
	       		Esame Obiettivo
	       	</th> 	       	     
		</tr>
		<tr>
    		<td>
    			 Data dell'esame
    		</td>
    		<td colspan="2">
    			<fmt:formatDate value="${dataEsame}" pattern="dd/MM/yyyy" var="data"/>    		
    			<c:out value="${data}"></c:out>
    		</td>
        </tr> 
        <tr class="odd">
        	<td colspan="3">
        		&nbsp;
        	</td>
        </tr>      
		
		<tr>
			<th>
	       		Tipo
	       	</th>
	       	<th>
	       		Esito
	       	</th>
	       	<th>
	       		Anormalità
	       	</th> 	       	     
		</tr>		
	
	
		<c:set var="i" value='1'/>	
		
<%
			Date data_mod=null, data_ent=null;
			BUtenteAll mod_by=null, ent_by=null;	
			
			while(superList.hasNext())
			{
				Iterator<EsameObiettivo> eoList = esameObiettivos.iterator();
				Iterator<LookupEsameObiettivoEsito> esiti = ((ArrayList<LookupEsameObiettivoEsito>)superList.next()).iterator();	
				String thereIsDescription = "NO";
				String esitoAnormale = "NO";
				String alreadyChecked = "NO";
	    	
				while(eoList.hasNext()) 
				{
					EsameObiettivo eo = eoList.next();
					ent_by = eo.getEnteredBy();
					mod_by = eo.getModifiedBy();
					data_ent = eo.getEntered();
					data_mod = eo.getModified();
					
					if (eo.getLookupEsameObiettivoTipo().getDescription().equals(listDescrizioni.get(countDescrizioni) )) 
					{
					   if (eo.getNormale() == true)  
						   thereIsDescription = "YES";
						else if (eo.getNormale() == false) 
						{ 
							esitoAnormale = "SI";
							thereIsDescription = "YES";
						}
					}
				}
				
				if(thereIsDescription.equals("YES"))
				{
%>
					<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
						<td style="width:40%">				
							<%= listDescrizioni.get(countDescrizioni) %>     
						</td>
						<input type="hidden" name="descrizione_${i}" value="<%=listDescrizioni.get(countDescrizioni)%>"/>
<%
						if(thereIsDescription.equals("YES") && esitoAnormale.equals("NO"))
						{
%>
						 	<td style="width:20%">
								Normale
							</td>
<%
						}
						if(thereIsDescription.equals("YES") && esitoAnormale.equals("SI"))
						{
%>
						 	<td style="width:20%">
								Anormale
							</td>
<%
						}
						if(thereIsDescription.equals("NO"))
						{
%>
							<td style="width:20%">
								Non esaminato
							</td>	
<%
						}
%>						
						
						<td style="width:40%">
							<div id="_${i }"   
<%
							if(esitoAnormale.equals("NO"))
							{
%>
		  		          		style="display:none;"
<%
							}
%>
		    				>
<%
		  		          	while(esiti.hasNext())
		  		          	{
		  		          		Iterator<LookupEsameObiettivoEsito> figli = ((ArrayList<LookupEsameObiettivoEsito>)request.getAttribute("listEsameObiettivoFigliList")).iterator();
		  		          		Iterator<EsameObiettivoEsito> esitiSelezionati = ((ArrayList<EsameObiettivoEsito>)request.getAttribute("superListChecked")).iterator();
								LookupEsameObiettivoEsito temp = esiti.next();	
								String superesitoDaStampato = "NO";
								String isIn = "NO";
								
								while(figli.hasNext())
								{
									LookupEsameObiettivoEsito figlio = figli.next();
									if(figlio.getLookupEsameObiettivoEsito().getId()==temp.getId())
									{
										isIn = "YES";
										break;
									}
								}
								
								if(isIn.equals("NO"))
								{
									alreadyChecked = "NO";	
									while(esitiSelezionati.hasNext())
									{
										EsameObiettivoEsito esito = esitiSelezionati.next();
										if(esito.getLookupEsameObiettivoEsito().getId()==temp.getId())
										{
											alreadyChecked = "YES";
											break;
										}
									}
									
									if(alreadyChecked.equals("YES"))
									{
										out.println(temp.getDescription() + "<br/>");
										alreadyChecked = "NO";
									}
								}
								else
								{
									figli = ((ArrayList<LookupEsameObiettivoEsito>)request.getAttribute("listEsameObiettivoFigliList")).iterator();
										
									while(figli.hasNext())
									{
										LookupEsameObiettivoEsito figlio = figli.next();
													
									    if(figlio.getLookupEsameObiettivoEsito().getId() == temp.getId())
									    {
											isIn = "YES";
									    	alreadyChecked = "NO";
												    	
									    	esitiSelezionati = ((ArrayList<EsameObiettivoEsito>)request.getAttribute("superListChecked")).iterator();
														
											while(esitiSelezionati.hasNext())
											{
												EsameObiettivoEsito esito = esitiSelezionati.next();
												    
												if(esito.getLookupEsameObiettivoEsito().getId() == figlio.getId())
											    {
											    	alreadyChecked = "YES";
											    	break;
											    }
											}
											if(alreadyChecked.equals("YES") && superesitoDaStampato.equals("NO"))
											{
												superesitoDaStampato = "YES";
%>
												<label><b><i><%=temp.getDescription() %></i></b></label><br/>&nbsp;&nbsp;&nbsp;&nbsp;<%=figlio.getDescription() %>	
												<br/>												
<%
												alreadyChecked = "NO";
										    }
													
											if(alreadyChecked.equals("YES") && superesitoDaStampato.equals("YES"))
											{
%>
												&nbsp;&nbsp;&nbsp;&nbsp;<%=figlio.getDescription() %>	
												<br/>												
<%
												alreadyChecked = "NO";
											}
										}
									}
								}
		  		          	}
%>							
						</div>
					</td>			
				</tr>
				
				<c:set var="i" value='${i+1}'/>	   
			<% 
			
				}
			
			countDescrizioni++; 
			}
			%>	
	    
	    
	    
	    
	    <tr>
	    
	    
	    
	   
	    <!-- Gestione del caso particolare per la Febbre, che fa parte dell'esame
	    obiettivo generale, e perciò apparato=0!!! -->
	    <!-- Si va a controllare se il cane, in quella cartella clinica, e quindi
		per quell'esame obiettivo ha già avuto un valore di febbre.
		Se non lo ha ancora avuto, allora viene presentato il radio button "base"
		con il valore di default "Non esaminato", altrimenti viene mostrato
		il precedente settaggio-->
		<!--  Se già l'ha avuta, si mostra il valore ultimo salvato -->
	     <c:if test="${idApparato == 0 && isFebbre==true}">	
	     <tr class="${i % 2 == 0 ? 'odd' : 'even'}">
		     <td>	    
		     Temperatura 
		     </td>						
			
			<c:forEach items="${cc.febbres}" var="febbre" >	
			<c:choose>
				<c:when test="${febbre.normale == true}">
					<td>
						Normale
					</td>	
				</c:when>	
				
				<c:when test="${febbre.normale == false}">
					<td>
						Anormale
					</td>	
				</c:when>									
			</c:choose>					
			<td>
				${febbre.temperatura}
			</td>
			</c:forEach>
		</tr>
		</c:if>	
	    
	   </tr> 
	    	          	     
	
		<input type="hidden" name="numeroElementi" value="${i}"/>
		
	</table>
	
	<br>
	<table class="tabella">
		<tr>
			<th colspan="3">
			       		Altre Informazioni
			 </th>
		 </tr> 	  
		<tr class="odd">
			<td>Inserito da</td>
			<td><%=ent_by%> il <fmt:formatDate value="<%=data_ent%>" pattern="dd/MM/yyyy"/></td>
		</tr>
		<tr class="even">
			<td>Modificato da</td><td><%=mod_by%> il <fmt:formatDate value="<%=data_mod%>" pattern="dd/MM/yyyy"/></td>
		</tr>
	</table>
	
	<input type="submit" value="Modifica" onclick="if(${cc.dataChiusura!=null}){ 
		if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere()}
		}else{attendere()}"/>
	<input type="button" value="Annulla" onclick="location.href='vam.cc.Detail.us'" />

</form>

