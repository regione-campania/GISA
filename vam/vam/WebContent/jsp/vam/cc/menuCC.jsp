<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us"%>

<fmt:formatDate pattern="dd/MM/yyyy" var="dataApertura" value="${cc.dataApertura}"/>
<fmt:formatDate pattern="dd/MM/yyyy" var="dataChiusura" value="${cc.dataChiusura}"/>

<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<link type="text/css" href="js/vam/cc/menuCC/menu.css" rel="stylesheet" />
<script type="text/javascript" src="js/vam/cc/menuCC/menu.js"></script>


<style type="text/css">
{
margin
:0;


padding
:0;


}
body {
	background: rgb(74, 81, 85);
}

div#menuCC {
	
}

div#copyright {
	font: 0px 'Trebuchet MS';
	color: #ffffff;
	text-indent: 30px;
	padding: 40px 0 0 0;
}

div#copyright a {
	color: #ffffff;
}

div#copyright a:hover {
	color: #ffffff;
}
</style>




<div id="menuCC">
<ul class="menuCC">

	<li><a href="vam.cc.Detail.us" onclick="attendere()"><span>Home</span></a>
	</li>

	<!-- Se l'animale è morto o in accettazione son state richieste solo operazioni di autopsia/smaltimento carogna, allora
    	si possono fare solo le seguenti operazioni -->
	<c:if test="${cc.ccMorto == true}">
		<li><a href="vam.cc.esamiIstopatologici.List.us"
			onclick="attendere()"><span>Istopatologico</span></a></li>
		<!--			<li>-->
		<!--				<a href="vam.cc.decessi.Detail.us?" onclick="attendere()"><span>Decesso</span></a>-->
		<!--			</li>-->
		
		<c:choose>
			<c:when test="${utente.ruolo=='IZSM' || utente.ruolo=='Universita' || utente.ruolo=='8' || utente.ruolo=='6'}">
				<li><a href="vam.izsm.autopsie.List.us?" onclick="attendere()"><span>Esame Necroscopico</span></a></li>
			</c:when>
			<c:otherwise>
				<li><a href="vam.cc.autopsie.List.us?" onclick="attendere()"><span>Esame Necroscopico</span></a></li>
			</c:otherwise>
		</c:choose>
		
		<li><a href="vam.cc.covid.List.us"><span>SARS CoV 2</span></a></li>
		
	</c:if>


	<c:if test="${cc.ccMorto != true}">

		<li><a href="#" class="parent"><span>Anamnesi</span></a>
		<ul>
			<c:if test="${cc.dataChiusura==null}">
				<li><a href="vam.cc.anamnesiRecente.ToEdit.us"
					onclick="attendere()"><span>Anamnesi Recente</span></a></li>
			</c:if>
			<c:if test="${cc.dataChiusura!=null}">
				<li><a id="mod" href="vam.cc.anamnesiRecente.ToEdit.us" 
				onclick="if(${cc.dataChiusura!=null}){ 
			    				return myConfirm('Cartella Clinica chiusa. Vuoi procedere?');
	    						}else{return true;}"><span>Anamnesi Recente</span></a></li>
			</c:if>
			<li><a href="vam.cc.anamnesiRemota.List.us"
				onclick="attendere()"><span>Anamnesi Remota</span></a></li>
		</ul>
		</li>

		<li><a href="#" class="parent"><span>Esami</span></a>
		<ul>
			<li><a href="#" class="parent"><span>Esame Obiettivo</span></a>
			<ul>
				<li><a href="vam.cc.esamiObiettivo.ToDetailGenerale.us?"
					onclick="attendere()"><span>Generale</span></a></li>
				<li><a href="vam.cc.esamiObiettivo.ToChooseApparato.us?"
					onclick="attendere()"><span>Particolare</span></a></li>
			</ul>
			</li>
			<li><a href="#" class="parent"><span>Esami Interni</span></a>
			<ul>
				<li><a href="vam.cc.diagnosticaImmagini.List.us?"
					onclick="attendere()"><span>Diagnostica per immagini</span></a></li>
				<li><a href="vam.cc.esamiSangue.List.us?" onclick="attendere()"><span>Sangue</span></a>
				</li>
				<li><a href="vam.cc.esamiUrine.List.us" onclick="attendere()"><span>Urine</span></a>
				</li>
				<li><a href="vam.cc.esamiCoprologici.List.us"
					onclick="attendere()"><span>Coprologico</span></a></li>
				<li><a href="vam.cc.ecg.List.us?" onclick="attendere()"><span>ECG</span></a>
				</li>
				<li><a href="vam.cc.esamiIstopatologici.List.us"
					onclick="attendere()"><span>Istopatologico</span></a></li>
				<li><a href="vam.cc.esamiCitologici.List.us"
					onclick="attendere()"><span>Citologico</span></a></li>
			</ul>
			</li>
			<li><a href="#" class="parent"><span>Esami Esterni</span></a>
			<ul>
				<c:if test="${cc.accettazione.animale.lookupSpecie.id == 1}">
					<li><a href="vam.cc.ehrlichiosi.List.us" onclick="attendere()"><span>Ehrlichiosi</span></a>
					</li>
				</c:if>
				<c:if test="${cc.accettazione.animale.lookupSpecie.id == 1}">
					<li><a href="vam.cc.leishmaniosi.List.us"
						onclick="attendere()"><span>Leishmaniosi</span></a></li>
				</c:if>
				<c:if test="${cc.accettazione.animale.lookupSpecie.id == 1}">
					<li><a href="vam.cc.rickettsiosi.List.us"
						onclick="attendere()"><span>Rickettsiosi</span></a></li>
				</c:if>
				<li><a href="vam.cc.toxoplasmosi.List.us" onclick="attendere()"><span>Toxoplasmosi</span></a>
				</li>
				<li><a href="vam.cc.rabbia.List.us" onclick="attendere()"><span>Rabbia</span></a>
				</li>

				<c:if test="${cc.accettazione.animale.lookupSpecie.id == 2}">
					<li><a href="vam.cc.fiv.List.us"><span>FIV</span></a></li>
				</c:if>
				<c:if test="${cc.accettazione.animale.lookupSpecie.id == 2}">
					<li><a href="vam.cc.fip.List.us"><span>FIP</span></a></li>
				</c:if>
				<c:if test="${cc.accettazione.animale.lookupSpecie.id == 2}">
					<li><a href="vam.cc.felv.List.us"><span>FELV</span></a></li>
				</c:if>
				
				<li><a href="vam.cc.covid.List.us"><span>SARS CoV 2</span></a></li>
				

			</ul>
			</li>
			<!--  <li> -->
			<!--  	<a href="vam.cc.autopsie.Detail.us?" onclick="attendere()"><span>Esame Necroscopico</span></a> -->
			<!--  </li>  -->
		</ul>
		</li>


		<li><a href="vam.cc.ricoveri.Detail.us?" onclick="attendere()"><span>Ricovero</span></a>
		</li>
		<li><a href="vam.cc.diagnosi.List.us?" onclick="attendere()"><span>Diagnosi</span></a>
		</li>
		<li><a href="vam.cc.chirurgie.List.us" onclick="attendere()"><span>Chirurgia</span></a>
		</li>
		<li><a href="vam.cc.terapieDegenza.List.us" onclick="attendere()"><span>Terapia
		in Degenza</span></a></li>
		<li><a href="vam.cc.diarioClinico.Detail.us"
			onclick="attendere()"><span>Diario clinico</span></a></li>

		<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe == false}">
			<!--				<li>-->
			<!--					<a href="vam.cc.decessi.Detail.us?" onclick="attendere()"><span>Decesso</span></a>-->
			<!--				</li>-->
		</c:if>
	</c:if>

	<c:if test="${cc.ruoloEnteredBy!='IZSM' && cc.ruoloEnteredBy!='Universita'}">
	<li><a href="vam.cc.trasferimenti.List.us?" onclick="attendere()"><span>Trasferimenti</span></a>
	</li>
	
	</c:if>

	<c:if test="${cc.ccMorto != true}">
		<c:if test="${cc.dataChiusura==null}">
			<li><a href="vam.cc.dimissioni.ToEdit.us?" onclick="attendere()"><span>Dimissioni</span></a>
			</li>
		</c:if>
		<c:if test="${cc.dataChiusura!=null}">
			<li><a href="vam.cc.dimissioni.Detail.us?" onclick="attendere()"><span>Dimissioni</span></a>
			</li>
		</c:if>
	</li>
	</c:if>
</ul>
</div>

<%
	String riepilogoCc = (String)request.getParameter("riepilogoCc");
	if(riepilogoCc==null || riepilogoCc.equals("si") || riepilogoCc.equals("ancheDatiRicovero"))
	{
%>
			<h4 id="riepilogoCc1" class="titolopagina">
    			Riepilogo Cc      
    		</h4>
    
    		<table id="riepilogoCc2" class="tabella">
        		<tr class="odd">
    				<td style="width:30%">
    					Identificativo ${cc.accettazione.animale.lookupSpecie.description}: ${cc.accettazione.animale.identificativo}
    				</td>
    				<td style="width:70%">
    					Numero Cc: ${cc.numero}
    				</td>
    			</tr>
<%
    			if(riepilogoCc!=null && riepilogoCc.equals("ancheDatiRicovero"))
    			{
%>
    				<tr class="even">
<%
    			}
    			else
    			{
%>
					<tr class="odd">
<%
    			} 
%>   			
    				<td style="width:30%">
    					Data di apertura:
    					${dataApertura}
    				</td>
    				<td style="width:70%">
	    				<c:choose>
	    					<c:when test="${cc.dataChiusura==null}">
	    						Data di chiusura
	    					</c:when>
	    					<c:otherwise>
	    						<b>Data di chiusura</b>
	    					</c:otherwise>
	    				</c:choose>: 
	    				<b>${dataChiusura}</b>
    				</td>
        		</tr>
<%
        			if(riepilogoCc!=null && riepilogoCc.equals("ancheDatiRicovero"))
					{
%>		
        			<tr class="odd">
    					<td style="width:30%">
    						Motivo ricovero:
    						
    					</td>
    					<td style="width:70%">
    						${cc.ricoveroMotivo}
    					</td>
        			</tr>
        			<tr class="even">
        				<td style="width:30%">
							Sintomatologia:
        				</td>
						<td style="width:70%">
							${cc.ricoveroSintomatologia}
						</td>
        			</tr>
        			<tr class="odd">
        				<td style="width:30%">
							Note Ricovero:
        				</td>
						<td style="width:70%">
							${cc.ricoveroNote}
						</td>
        			</tr>
<%
					}
%>
      		</table>
<%
	}
%>
