<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
<h4 class="titolopagina">
     		Dettaglio Richiesta Esame Istopatologico
</h4>		 

<c:set var="tipo" scope="request" value="stampaIstoSingolo"/>
<c:set var="idEsame" scope="request" value="${esame.id }"/>
<c:import url="../../jsp/documentale/home.jsp"/>
<!-- input type="button" value="Stampa JSP" onclick="location.href='vam.cc.esamiIstopatologici.StampaIstoSingolo.us?id=${esame.id }'"-->
<!-- input type="button" value="Stampa la richiesta" onclick="location.href='vam.cc.esamiIstopatologici.Pdf.us?id=${esame.id }'" /-->
<input type="button" value="Immagini" onclick="javascript:avviaPopup( 'vam.cc.esamiIstopatologici.GestioneImmagini.us?id=${esame.id }&cc=${cc.id}' );" />
<c:if test="${esame.outsideCC==false}">
<c:choose>
	<c:when test="${cc!=null}">
		<input type="button" value="Necroscopia" onclick="javascript:if(${cc.autopsia!=null}){avviaPopup( 'vam.cc.autopsie.DetailDaIsto.us?idCc=${cc.id}' );}else{if(${cc.accettazione.animale.necroscopiaNonEffettuabile==false}){alert('Necroscopia non inserita');}else{alert('Necroscopia dichiarata come non effettuabile');}}" />
	</c:when>
	<c:otherwise>
		<input type="button" value="Necroscopia" onclick="javascript:if(${esame.cartellaClinica.autopsia!=null}){avviaPopup( 'vam.cc.autopsie.DetailDaIsto.us?idCc=${esame.cartellaClinica.id}' );}else{if(${esame.cartellaClinica.accettazione.animale.necroscopiaNonEffettuabile==false}){alert('Necroscopia non inserita');}else{alert('Necroscopia dichiarata come non effettuabile');}}" />			
	</c:otherwise>
</c:choose>
</c:if>
<c:choose>
	<c:when test="${utente.ruolo!='IZSM' && utente.ruolo!='Universita' && utente.ruolo!='6' && utente.ruolo!='8'}">
								
		<input type="button" value="Modifica" onclick="if(${cc.dataChiusura!=null}){ 
			    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere();location.href='vam.cc.esamiIstopatologici.ToAdd.us?modify=on&idEsame=${esame.id}';}
	    						}else{attendere();location.href='vam.cc.esamiIstopatologici.ToAdd.us?modify=on&idEsame=${esame.id}';}" />
	</c:when>
	<c:otherwise>
		<input type="button" value="Inserisci Esito" onclick="if(${cc.dataChiusura!=null}){ 
			    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?'))
			    				{
			    					attendere();location.href='vam.cc.esamiIstopatologici.ToAdd.us?modify=on&idEsame=${esame.id}&editIzsm=on';
			    				}
	    						}else{attendere();location.href='vam.cc.esamiIstopatologici.ToAdd.us?modify=on&idEsame=${esame.id}&editIzsm=on';}" />
	</c:otherwise>
</c:choose>
<input type="button" value="Lista Richieste Esami Istopatologici" onclick="attendere();location.href='vam.cc.esamiIstopatologici.List.us'">


<table class="tabella">
    	
        <tr>
    		<th colspan="2">
    			Esame Istopatologico numero ${esame.numero }	
    		</th>
    	</tr>
    	
    	<tr class='odd'>
    		<td>
    			 Laboratorio di destinazione
    		</td>
    		<td>
    			 ${esame.lass.description}
    		</td>
    		<td>
    		</td>
        </tr>

		<tr class="even">
    		<td>
    			 Numero rif. Mittente
    		</td>
    		<td>
				 ${esame.numeroAccettazioneSigla}
    		</td>
    		<td>
    		</td>
        </tr>
        
		<tr class="odd">
    		<td style="width:30%">
    			 Tipo Prelievo
    		</td>
    		<td style="width:70%">  
    			${esame.tipoPrelievo.description }
    		</td>
        </tr>     

        <tr>
    		<td style="width:30%">
    			 Tumori Precedenti
    		</td>
    		<td style="width:70%"> 
    			${esame.tumoriPrecedenti.description }  
    		</td>
        </tr>    
        
    <c:if test="${esame.tumoriPrecedenti.id == 2 }">
        <tr>
    		<td style="width:30%">
    			 T
    		</td>
    		<td style="width:70%"> 
    			${esame.t }  
    		</td>
        </tr>    
        
        <tr>
    		<td style="width:30%">
    			 N
    		</td>
    		<td style="width:70%"> 
    			${esame.n }  
    		</td>
        </tr>  
        
        <tr>
    		<td style="width:30%">
    			 M
    		</td>
    		<td style="width:70%">  
    			${esame.m }  
    		</td>
        </tr> 
    </c:if>
        
        <tr class="odd">
    		<td style="width:30%" >
    			 Dimensione (centimetri)
    		</td>
    		<td style="width:70%"> 
    			${esame.dimensione }
    		</td>
        </tr> 
        
        <tr>
    		<td style="width:30%">
    			 Interessamento Linfonodale
    		</td>
    		<td style="width:70%">  
    			${esame.interessamentoLinfonodale.description }  
    		</td>
        </tr> 
        
        <tr class="odd">
    		<td style="width:30%">
    			 Sede Lesione e Sottospecifica
    		</td>
    		<td style="width:70%">  
    			${esame.sedeLesione }  
    		</td>
        </tr> 
       
       
        <tr>
        	<th colspan="2">
        		Risultato
        	</th>
        </tr>
        <tr class="odd">
    		<td style="width:30%">
    			 Data Esito
    		</td>
    		<td style="width:70%">  
    			<fmt:formatDate type="date" value="${esame.dataEsito}" pattern="dd/MM/yyyy" /> 
    		</td>
        </tr>
        <tr>
    		<td style="width:30%">
    			 Descrizione Morfologica
    		</td>
    		<td style="width:70%">  
    			${esame.descrizioneMorfologica }  
    		</td>
        </tr>
        
        <tr class="odd">
    		<td style="width:30%">
    			 Diagnosi
    		</td>
    		<td style="width:70%">  
    			<c:if test="${esame.tipoDiagnosi.description!='- Seleziona -' && esame.tipoDiagnosi.description!=''}">
    				${esame.tipoDiagnosi.description }: ${esame.whoUmana } <c:if test="${esame.tipoDiagnosi.id==3}">${esame.diagnosiNonTumorale }</c:if> 
    			</c:if>
    			
    		</td>
        </tr> 
     </table>
    	<table class="tabella">
			<tr>
				<th colspan="3">
				       		Altre Informazioni
				 </th>
			 </tr> 	  
			<tr class="odd">
				<td>Inserito da</td>
				<td>${esame.enteredBy} il <fmt:formatDate value="${esame.entered}" pattern="dd/MM/yyyy"/></td>
			</tr>
			<c:if test="${esame.modifiedBy!=null}">
			<tr class="even">
				<td>Modificato da</td><td>${esame.modifiedBy} il <fmt:formatDate value="${esame.modified}" pattern="dd/MM/yyyy"/></td>
			</tr>
			</c:if>
		</table>
 
