<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="it.us.web.bean.vam.EsameObiettivo" %>
<%@ page import="it.us.web.bean.vam.EsameObiettivoEsito" %>
<%@ page import="it.us.web.bean.vam.CartellaClinica" %>
<%@ page import="it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito" %>

<!-- Script per la gestione della febbre -->

<%@page import="java.util.HashMap"%><script src="js/vam/cc/esamiObiettivo/swfobject_modified.js" type="text/javascript"></script>
<script language="javascript">
	function scriviTemperatura(a){
		document.getElementById('valore_temperatura').value=a;
	}
</script>

	

<script language="JavaScript" type="text/javascript" src="js/vam/cc/esamiObiettivo/add.js"></script>

<%  
 CartellaClinica cc = (CartellaClinica)session.getAttribute("cc");
 ArrayList<String> listDescrizioni = (ArrayList<String>)  request.getAttribute("descrizioniEOTipo"); 
 HashMap<Integer,Integer> padri = (HashMap<Integer,Integer>)request.getAttribute("listEsameObiettivoPadri"); 
 Iterator<ArrayList<LookupEsameObiettivoEsito>> esiti = ((ArrayList<ArrayList<LookupEsameObiettivoEsito>>)request.getAttribute("superList")).iterator(); 
 int countDescrizioni = 0;
 Iterator<EsameObiettivoEsito> esitiSelezionati = null;
 if(request.getAttribute("superListChecked")!=null)
 {
 	esitiSelezionati = ((ArrayList<EsameObiettivoEsito>)request.getAttribute("superListChecked")).iterator(); 
 }
 ArrayList<LookupEsameObiettivoEsito> figli = (ArrayList<LookupEsameObiettivoEsito>)request.getAttribute("listEsameObiettivoFigliList"); 
%>

<form action="vam.cc.esamiObiettivo.Edit.us" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idApparato" value="<c:out value="${idApparato}"/>"/>
	<input type="hidden" name="dataAccettazione" value="<fmt:formatDate value="${cc.accettazione.data }" pattern="dd/MM/yyyy" />"/>

	
	<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
	
	<h4 class="titolopagina">
	   	Esame obiettivo
	   	<c:if test="${idApparato != 0}">
	   		"${apparato.description }"
	   	</c:if>
	</h4>	
	
	<table class="tabella">
		
		<tr>
			<th colspan="3">
	       		Esame Obiettivo
			   	<c:if test="${idApparato != 0}">
			   		"${apparato.description }"
			   	</c:if>
	       	</th> 	       	     
		</tr>
		<tr>
    		<td>
    			 Data dell'esame<font color="red"> *</font>
    		</td>
    		<td colspan="2">
    		<fmt:formatDate value="${dataEsame}" pattern="dd/MM/yyyy" var="data"/>    		
    			 <input type="text" id="dataEsameObiettivo" name="dataEsameObiettivo" maxlength="32" size="50" readonly="readonly" value="<c:out value="${data}"></c:out>"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataEsameObiettivo",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_1",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
        </tr> 
        <tr class="odd">
        	<td colspan="3">
        		&nbsp;
        	</td>
        </tr>      
	</table>
	
	<c:if test="${idApparato != 0}">
		
		<div id="accordion" style="width:89%">
		
			<h3><a href="#">Sintomatologia e Terapie Precedenti</a></h3>
			<div>
				<table class="tabella">
					<tr>
						<th>Sintomi</th>
					</tr>
						<c:forEach items="${sintomi }" var="temp">
							<tr class="odd">
								<td>
									<input type="checkbox" name="sin_${temp.id }" id="sin_${temp.id }"
									<us:contain collection="${sintopatologia.esameObiettivoSintomis }" item="${temp }" >checked="checked"</us:contain> />
									<label for="sin_${temp.id }">${temp.description }</label>
								</td>
							</tr>
						</c:forEach>
					<tr>
						<th>Insorgenza</th>
					</tr>
					<tr class="odd">
						<td>
							Periodo dell'insorgenza
							<select name="periodoInsorgenzaSintomiString">
								<c:forEach items="${periodoInsorgenze }" var="temp">
									<option value="${temp.id }" <c:if test="${temp == sintopatologia.periodoInsorgenzaSintomi }" >selected="selected"</c:if> >${temp.description }</option>
								</c:forEach>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							Andamento
							<select name="insorgenzaSintomiString">
								<c:forEach items="${insorgenze }" var="temp">
									<option value="${temp.id }" <c:if test="${temp == sintopatologia.insorgenzaSintomi }" >selected="selected"</c:if> >${temp.description }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>Terapie Precedenti</th>
					</tr>
					<tr class="odd">
						<td>
							<textarea name="terapiePrecedenti" cols="100">${sintopatologia.terapiePrecedenti}</textarea>
						</td>
					</tr>
				</table>
			</div>
			
			<h3><a href="#">Patologie Congenite</a></h3>
			<div>
				<table class="tabella">
					<tr class="odd">
						<td colspan="1">
							Note
						</td>
						<td colspan="3">
							<textarea name="note" rows="4" style="width:70%">${patologieCongenite.note}</textarea>
						</td>
					</tr>
				</table>
			</div>
		
			<h3><a href="#">Esame Organi e/o Tessuti</a></h3>
			
	</c:if>
		<div>
			<table class="tabella">
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
					Iterator<LookupEsameObiettivoEsito> esitiApparato = null;	
					String esitoNormaleDescrizione = "Normale";
					while(esiti.hasNext())
					{
						esitiApparato = esiti.next().iterator();
%>		
						<c:set var="esitoAnormale" value='NO'/>
				
						<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
				
							<td style="width:40%">				
								<%= listDescrizioni.get(countDescrizioni) %> 
								<%
									if(listDescrizioni.get(countDescrizioni).equalsIgnoreCase("Polmone"))
										esitoNormaleDescrizione="Ascultazione nella norma";
								%>    
							</td>
							<input type="hidden" name="descrizione_${i}" value="<%=listDescrizioni.get(countDescrizioni)%>"/>
			    	
			    			<c:set var="thereIsDescription" value='NO'/>
<% 							
							Iterator eoList = cc.getEsameObiettivos().iterator();
							while(eoList.hasNext()) 
							{
								EsameObiettivo eo = (EsameObiettivo) eoList.next();
								if (eo.getLookupEsameObiettivoTipo().getDescription().equals(listDescrizioni.get(countDescrizioni) )) 
								{
								   if (eo.getNormale() == true) 
								   { 
%>
										<td style="width:20%">
											<input type="radio" name="_${i}" id="_N${i}" value="Normale"  checked="checked" onclick="javascript: hiddenDiv('_${i}');" > <label for="_N${i}"><%=esitoNormaleDescrizione%></label><br>
											<input type="radio" name="_${i}" id="_A${i}" value="Anormale" onclick="javascript: toggleGroup('_${i}');" > <label for="_A${i}">Anormale</label><br>
											<input type="radio" name="_${i}" id="_NE${i}" value="NE"  onclick="javascript: hiddenDiv('_${i}');" > <label for="_NE${i}">Non esaminato</label><br>
										</td>	
										<c:set var="thereIsDescription" value='YES'/>	
<%
						           }
								   else if (eo.getNormale() == false) 
								   {
%>
										<td style="width:20%">
											<input type="radio" name="_${i}" id="_N${i}" value="Normale"   onClick="javascript: hiddenDiv('_${i}');" > <label for="_N${i}"><%=esitoNormaleDescrizione%></label><br>
											<input type="radio" name="_${i}" id="_A${i}" value="Anormale" checked="checked" onClick="javascript: toggleGroup('_${i}');" > <label for="_A${i}">Anormale</label><br>
											<input type="radio" name="_${i}" id="_NE${i}" value="NE"  onClick="javascript: hiddenDiv('_${i}');" > <label for="_NE${i}">Non esaminato</label><br>
										</td>	
										<c:set var="thereIsDescription" value='YES'/>
										<c:set var="esitoAnormale" value='SI'/>
<%
									}
								}
							}
%>
							<c:if test="${thereIsDescription == 'NO'}">							
	                        <td style="width:20%">
								<input type="radio" name="_${i}" id="_N${i}" value="Normale"   onClick="javascript: hiddenDiv('_${i}');" > <label for="_N${i}"><%=esitoNormaleDescrizione%></label><br>
								<input type="radio" name="_${i}" id="_A${i}" value="Anormale"  onClick="javascript: toggleGroup('_${i}');" > <label for="_A${i}">Anormale</label><br>
								<input type="radio" name="_${i}" id="_NE${i}" value="NE" checked="checked" onClick="javascript: hiddenDiv('_${i}');" > <label for="_NE${i}">Non esaminato</label><br>
							</td>	
						</c:if>	
						<td style="width:40%">
							<div id="_${i }"   
								<c:if test="${esitoAnormale=='NO'}">
		  		          			style="display:none;"
		    					</c:if>
		    				>	
<% 
						    LookupEsameObiettivoEsito temp = null;
							while(esitiApparato.hasNext())
							{
								temp = esitiApparato.next();
								String isIn = "NO";
								if(temp.getLookupEsameObiettivoEsitos().size()>0)
									isIn = "YES";
%>		
								
								<%-- Se un esame non è padre allora checkbox normale,
								se invece lo è allora viene messo non checkabile
								in rosso e immediatamente sotto viene presentata la lista dei figli --%>
<%
								if(isIn.equals("NO"))
								{
									String alreadyChecked = "NO";
									if(request.getAttribute("superListChecked")!=null)
										esitiSelezionati = ((ArrayList<EsameObiettivoEsito>)request.getAttribute("superListChecked")).iterator();
									else
										esitiSelezionati = null;
									if(esitiSelezionati!=null)
									{
										while(esitiSelezionati.hasNext())
										{
											EsameObiettivoEsito esito = esitiSelezionati.next();
											if(esito.getLookupEsameObiettivoEsito().getId() == temp.getId())
											{
												alreadyChecked = "YES";
												break;
											}
										}
									}
									
									if(alreadyChecked.equals("YES"))
									{
%>
										<input type="checkbox" id="op_<%=temp.getId()%>" name="op_<%=temp.getId()%>" checked="checked" onclick="esitiMutuamenteEsclusivi(<%=temp.getEsitiMutuamenteEsclusiviString()%>)"/> <label for="op_<%=temp.getId()%>"><%=temp.getDescription()%></label>	
<%										
										alreadyChecked = "NO";	
									}
									else
									{
%>
										<input type="checkbox" id="op_<%=temp.getId()%>" name="op_<%=temp.getId()%>" onclick="esitiMutuamenteEsclusivi(<%=temp.getEsitiMutuamenteEsclusiviString()%>)"/> <label for="op_${temp.id }"><%=temp.getDescription()%></label>
<%		
										alreadyChecked = "NO";
									}
								}
								else
								{
%>
									<br>
									<label><b><i><%=temp.getDescription() %></i></b></label>								
									<br>
<%
								
									Iterator<LookupEsameObiettivoEsito> figliEsito = temp.getLookupEsameObiettivoEsitos().iterator();
									while(figliEsito.hasNext())
									{
										LookupEsameObiettivoEsito figlio = figliEsito.next();
										isIn = "YES";
										String alreadyChecked = "NO";
										if(request.getAttribute("superListChecked")!=null)
											esitiSelezionati = ((ArrayList<EsameObiettivoEsito>)request.getAttribute("superListChecked")).iterator();
										else
											esitiSelezionati = null;
										if(esitiSelezionati!=null)
										{
											while(esitiSelezionati.hasNext())
											{
												EsameObiettivoEsito slc = esitiSelezionati.next();		
												if(slc.getLookupEsameObiettivoEsito().getId()==figlio.getId())
												{
													alreadyChecked = "YES";
													break;
												}
											}
										}
										if(alreadyChecked.equals("YES"))
										{
%>	
											&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="op_<%=figlio.getId()%>" name="op_<%=figlio.getId()%>" checked="checked" onclick="esitiMutuamenteEsclusivi(<%=figlio.getEsitiMutuamenteEsclusiviString()%>)"/> <label for="op_<%=figlio.getId()%>"><%=figlio.getDescription()%></label>	
<%
											alreadyChecked = "NO";
										}
										else
										{
%>																												
											&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="op_<%=figlio.getId()%>" name="op_<%=figlio.getId()%>" onclick="esitiMutuamenteEsclusivi(<%=figlio.getEsitiMutuamenteEsclusiviString()%>)"/> <label for="op_<%=figlio.getId()%>"><%=figlio.getDescription()%></label>	
<%
											alreadyChecked = "NO";	
										}
%>
										<br>
<%
									}
								}
%>
								<br>
<%
							}
%>												
						</div>
					</td>			
				</tr>
				
				<c:set var="i" value='${i+1}'/>
				<% 
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
			     <c:if test="${idApparato == 0}">	
			     <tr class="even">
				     <td>	    
				     Temperatura 
				     </td>						
					
					<c:choose>
						
						
						<c:when test="${isFebbre == false}">
							
							<td>
							<input type="radio" name="_9998" id="_9998N" value="Normale"   onClick="javascript: hiddenDiv('_9998');" > <label for="_9998N">Normale</label><br>
							<input type="radio" name="_9998" id="_9998A" value="Anormale"  onClick="javascript: toggleGroup('_9998');" > <label for="_9998A">Anormale</label><br>
							<input type="radio" name="_9998" id="_9998NE" value="NE"  checked="checked" onClick="javascript: hiddenDiv('_9998');" > <label for="_9998NE">Non esaminato</label><br>
							</td>
							
							<td>
								<div id="_9998" style="display:none;">							
															
									
							    	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="160" height="280" id="FlashID" title="termometro">
								      <param name="movie" value="js/vam/cc/esamiObiettivo/termometro.swf" />
								      <param name="quality" value="high" />
								      <param name="wmode" value="opaque" />
								      <param name="swfversion" value="9.0.45.0" />
								      <!-- Questo tag param fa in modo che agli utenti con Flash Player 6.0 r65 e versioni successive venga richiesto di scaricare l'ultima versione di Flash Player. Eliminatelo se non volete visualizzare la richiesta agli utenti. -->
								      <param name="expressinstall" value="Scripts/expressInstall.swf" />
								      <param name="FlashVars" value="FLtemp=38.5" />
								      <!-- Il tag object successivo è per i browser diversi da IE. Utilizzate IECC per nasconderlo in IE. -->
								      <!--[if !IE]>-->
								      <object type="application/x-shockwave-flash" data="js/vam/cc/esamiObiettivo/termometro.swf" width="160" height="280">
								        <!--<![endif]-->
								        <param name="quality" value="high" />
								        <param name="wmode" value="opaque" />
								        <param name="swfversion" value="9.0.45.0" />
								        <param name="expressinstall" value="Scripts/expressInstall.swf" />
								        <param name="FlashVars" value="FLtemp=38.5" />
								        <!-- Il browser visualizza il seguente contenuto alternativo per gli utenti che utilizzano Flash Player 6.0 e versioni precedenti. -->
								        <div>
								          <h4>Il contenuto di questa pagina richiede una nuova versione di Adobe Flash Player.</h4>
								          <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Scarica Adobe Flash Player" width="112" height="33" /></a></p>
								        </div>
								        <!--[if !IE]>-->
								      </object>
								      <!--<![endif]-->
		    					</object>
							    <script type="text/javascript">
									swfobject.registerObject("FlashID");
							     </script>
								
								<!--Febbre-->
								<input type="hidden" id="valore_temperatura" name="valore_temperatura" maxlength="32" size="5" />					    			
							</div>
							</td>
													
						</c:when>	
						
						
						<c:otherwise>				
					
							<c:forEach items="${cc.febbres}" var="febbre" >	
							
							<c:choose>
								<c:when test="${febbre.normale == true}">
									<td>
									<input type="radio" name="_9999" id="_9999N" value="Normale"  checked="checked" onClick="javascript: hiddenDiv('_9999');" > <label for="_9999N">Normale</label><br>
									<input type="radio" name="_9999" id="_9999A" value="Anormale"  onClick="javascript: toggleGroup('_9999');" > <label for="_9999A">Anormale</label><br>
									<input type="radio" name="_9999" id="_9999NE" value="NE"  onClick="javascript: hiddenDiv('_9999');" > <label for="_9999NE">Non esaminato</label><br>
									</td>	
								</c:when>	
								
								<c:when test="${febbre.normale == false}">
									<td>
										<input type="radio" name="_9999" id="_9999N" value="Normale"   onClick="javascript: hiddenDiv('_9999');" > <label for="_9999N">Normale</label><br>
										<input type="radio" name="_9999" id="_9999A" value="Anormale"  checked="checked" onClick="javascript: toggleGroup('_9999');" > <label for="_9999A">Anormale</label><br>
										<input type="radio" name="_9999" id="_9999NE" value="NE"  onClick="javascript: hiddenDiv('_9999');" > <label for="_9999NE">Non esaminato</label><br>
									</td>	
								</c:when>									
								
							</c:choose>					
							
							<td>
								<div id="_9999" style="display:none;">							
									
									
									<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="160" height="280" id="FlashID" title="termometro">
								      <param name="movie" value="js/vam/cc/esamiObiettivo/termometro.swf" />
								      <param name="quality" value="high" />
								      <param name="wmode" value="opaque" />
								      <param name="swfversion" value="9.0.45.0" />
								      <!-- Questo tag param fa in modo che agli utenti con Flash Player 6.0 r65 e versioni successive venga richiesto di scaricare l'ultima versione di Flash Player. Eliminatelo se non volete visualizzare la richiesta agli utenti. -->
								      <param name="expressinstall" value="Scripts/expressInstall.swf" />
								      <param name="FlashVars" value="FLtemp=${febbre.temperatura}" />
								      <!-- Il tag object successivo è per i browser diversi da IE. Utilizzate IECC per nasconderlo in IE. -->
								      <!--[if !IE]>-->
								      <object type="application/x-shockwave-flash" data="js/vam/cc/esamiObiettivo/termometro.swf" width="160" height="280">
								        <!--<![endif]-->
								        <param name="quality" value="high" />
								        <param name="wmode" value="opaque" />
								        <param name="swfversion" value="9.0.45.0" />
								        <param name="expressinstall" value="Scripts/expressInstall.swf" />
								        <param name="FlashVars" value="FLtemp=${febbre.temperatura}" />
								        <!-- Il browser visualizza il seguente contenuto alternativo per gli utenti che utilizzano Flash Player 6.0 e versioni precedenti. -->
								        <div>
								          <h4>Il contenuto di questa pagina richiede una nuova versione di Adobe Flash Player.</h4>
								          <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Scarica Adobe Flash Player" width="112" height="33" /></a></p>
								        </div>
								        <!--[if !IE]>-->
								      </object>
								      <!--<![endif]-->
				    					</object>
									    <script type="text/javascript">
											swfobject.registerObject("FlashID");
									     </script>
															
									<!--Febbre-->
									<input type="hidden" id="valore_temperatura" name="valore_temperatura" maxlength="32" size="5" value="<c:out value="${febbre.temperatura}"/>"/>
							    	
							    </div>
							</td>
							</c:forEach>
					
						</c:otherwise>	
					</c:choose>		
					
					
				</tr>
				</c:if>	
			    
			   </tr> 
			    	          	     
			
				<input type="hidden" name="numeroElementi" value="${i}"/>
				
			</table>
		</div>		
		<c:if test="${idApparato != 0}">
			</div>
		</c:if>
	<br/>
	<font color="red">* </font> Campi obbligatori
	<br/>
	<input type="submit" value="Salva" />
	<c:choose>
		<c:when test="${idApparato==0}">
			<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.esamiObiettivo.ToDetailGenerale.us'">
		</c:when>
		<c:otherwise>
			<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.esamiObiettivo.ToDetailSpecifico.us?idApparato=${idApparato}'">
		</c:otherwise>
	</c:choose>
</form>

	<script>
	$(function() {
		$( "#accordion" ).accordion({
			collapsible: true,
			active: false,
			autoHeight: false
		});
	});
	</script>
	
