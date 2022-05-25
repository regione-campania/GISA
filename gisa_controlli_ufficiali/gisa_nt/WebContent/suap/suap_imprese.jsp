<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<script type="text/javascript" src="suap/javascriptsuap/suap_imprese.js"></script>
<jsp:useBean id="StabilimentoOpu" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>

<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList"     class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList"    class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />
<jsp:useBean id="fissa"           class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />

<%
	String comuneSuap = User.getUserRecord().getSuap().getDescrizioneComune();
	if(comuneSuap!=null && comuneSuap.equals(""))
		comuneSuap=null;
%>
<%@ include file="../initPage.jsp" %>
	
	<fieldset>
		<legend><b>DATI IMPRESA</b></legend>
		<table style="height: 100%; width: 70%">
			<tr id="tipo_societaTR">
				<td nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
				<td><%=TipoSocietaList.getHtmlSelect("tipo_societa", StabilimentoOpu.getOperatore().getTipo_societa())%></td>
			</tr>
			<tr>
				<td nowrap>
					<p id="labelRagSoc">
						DITTA/<br>DENOMINAZIONE/<br>RAGIONE SOCIALE
					</p>
				</td>
				<td><input type="text" size="70" id="ragioneSociale"
					class="required" name="ragioneSociale" value="<%=toHtml(StabilimentoOpu.getOperatore().getRagioneSociale())%>"></td>
			</tr>
			<tr>
				<td nowrap>PARTITA IVA</td>
				<td><input type="text" size="70" min="11" maxlength="11"
					id="partitaIva" name="partitaIva" onchange="ControllaPIVA('partitaIva');checkPartitaIva('partitaIva');" value="<%=StabilimentoOpu.getOperatore().getPartitaIva() %>" ></td>
			</tr>
			
			<tr id="codFiscaleTR">
				<td nowrap>CODICE FISCALE<br>IMPRESA
				</td>
				<td><input type="text" size="70" maxlength="16" id="codFiscale"  value="<%=StabilimentoOpu.getOperatore().getCodFiscale() %>" 
					name="codFiscale" class="required" > <input type="checkbox"
					id="codFiscaleUguale" onClick="gestisciCFUguale(this)" /> Uguale alla P.IVA
					
					</td>
			</tr>
			
			<!-- il controllo per la mail è bypassato, e non è lo stesso degli altri campi -->
			
			<script>
				window.mailValida = false;
			</script>
			
			
			<tr id="codFiscaleTR">
				<td nowrap>DOMICILIO DIGITALE(PEC)</td>
				<td><input type="text" size="100" maxlength="300"
					id="domicilioDigitale" name="domicilioDigitale" onchange="checkValiditaPec('domicilioDigitale','msgDomicilioDigitale')" value="<%=toHtml(StabilimentoOpu.getOperatore().getDomicilioDigitale()) %>" >
				</td>
				<td colspan="3"> 
					<h id="msgDomicilioDigitale" style="visibility:hidden;"></h>
				</td>
			</tr>
			<tr>
				<td>NOTE</td>
				<td><textarea name="noteImpresa" cols="50" rows="5"
						id="noteImpresa" value=""></textarea></td>
			</tr>
		</table>
	</fieldset>
	<fieldset id="fieldsRappLegale">
		<legend><b>DATI TITOLARE/LEGALE RAPPRESENTANTE</b></legend>
		<table style="height: 100%; width: 87%">
			<tr>
				<td>NOME</td>
				<td><input type="text" size="70" id="nome" name="nome" onchange="svuotaCf()" oncopy="return false" oncut="return false" onpaste="return false" class="required" value="<%=StabilimentoOpu.getOperatore().getRappLegale().getNome() %>" ></td>
			</tr>
			<tr>
				<td><label for="cognome-2">COGNOME </label></td>
				<td><input type="text" size="70" id="cognome" name="cognome" onchange="svuotaCf()" oncopy="return false" oncut="return false" onpaste="return false" class="required" value="<%=StabilimentoOpu.getOperatore().getRappLegale().getCognome() %>" ></td>
			</tr>
			<tr>
				<td><label for="sesso-2">SESSO </label></td>
				<td><div class="test">
						<input type="radio" name="sesso" id="sesso1" value="M"  onchange="svuotaCf()"
							checked="checked" class="required css-radio"> <label
							for="sesso1" class="css-radiolabel radGroup2">M</label> <input
							type="radio" name="sesso" id="sesso2" value="F"  onchange="svuotaCf()"
							class="required css-radio"> <label for="sesso2"
							class="css-radiolabel radGroup2">F</label>
					</div></td>
			</tr>
			<tr>
				<td><label for="dataN-2">DATA NASCITA </label></td>
				<td><input type="text" size="15" name="dataNascita" value="<%=toDateasString(StabilimentoOpu.getOperatore().getRappLegale().getDataNascita()) %>"
					id="dataNascita2" class="required" placeholder="dd/MM/YYYY">&nbsp;&nbsp;
				</td>
				
			</tr>
			<tr>
				<td><label for="nazioneN-2">NAZIONE NASCITA</label></td>
				<td>
					<%NazioniList.setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita');sbloccoProvincia('nazioneNascita',null,'comuneNascita',null)\"");%> 
					<%=NazioniList.getHtmlSelect("nazioneNascita", 106)%></td>
			</tr>
			<tr>
				<td nowrap>COMUNE NASCITA</td>
				<td><select name="comuneNascita" id="comuneNascita"
					class="required">
						<%
					if(StabilimentoOpu.getIdStabilimento()>0 )
					{
					%>
					<option value="<%=StabilimentoOpu.getOperatore().getRappLegale().getComuneNascita()%>"><%=StabilimentoOpu.getOperatore().getRappLegale().getComuneNascita() %></option>
					<%} %>
						
						<option value="">SELEZIONA COMUNE</option>
				</select> 
				<input type="hidden" name="comuneNascitaTesto" id="comuneNascitaTesto" value="<%=StabilimentoOpu.getOperatore().getRappLegale().getComuneNascita() %>" />
				</td>
			</tr>
			<tr>
				<td>CODICE FISCALE</td>
				<td>
					<span>
					<input type="text" name="codFiscaleSoggetto"
					readonly="readonly" id="codFiscaleSoggetto" class="required" value="<%=StabilimentoOpu.getOperatore().getRappLegale().getCodFiscale()%>"/>
					<input type="button" id="calcoloCF" class="newButtonClass"
					value="CALCOLA CODICE FISCALE"
					onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].comuneNascitainput,document.forms[0].dataNascita,'codFiscaleSoggetto')"/>
					</span>
				</td>
				<!-- <td>&nbsp;</td> -->
				<!-- <td>
					
				</td> -->
			</tr>
			<!-- <tr>
				
			</tr> -->
			<tr>
				<td><label for="nazioneN-2">NAZIONE RESIDENZA</label></td>
				<td>
					<%NazioniList.setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"");%> 
					<% NazioniList.setJsEvent("onChange=\"abilitaDisabilitaIndirizzo('residenzaRappLegale')\""); %>
					<%=NazioniList.getHtmlSelect("nazioneResidenza", 106)%></td>
			</tr>
			<tr>
				<td colspan="2">
					<font color="red">ATTENZIONE! Posizionarsi sul campo comune per inserire tutto l'indirizzo</font>
				</td>
			</tr>
			<tr>
				<td nowrap>COMUNE RESIDENZA</td>
				<td>
				<!-- Cancellato perchè la gestione deve essere tolto jquery -->
				<!-- <select name="addressLegaleCity" id="addressLegaleCity" class="required"-->
					
					<!--%
					if(StabilimentoOpu.getIdStabilimento()>0 )
					{
					%-->
					<!--option value="<%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getComune()%>" selected="selected"><%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune() %></option-->
					<!--%} %-->
					
						<!--option value="">SELEZIONA COMUNE</option>
				</select--> 
				
				
				
				<input type="hidden"  value="<%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getComune() %>" name="addressLegaleCityId" id="addressLegaleCityId" />
<%
				if(fissa!=null && !fissa.equals("") && fissa.equals("false"))
				{
%>
					<input value="<%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()%>" size="50" onclick="selezionaIndirizzo('nazioneResidenza','callBackResidenzaRappLegale',this.value)" type="text" name="addressLegaleCitta" id="addressLegaleCitta" placeholder="DENOMINAZIONE COMUNE"/>
<%
				}
				else
				{
%>	
					<input value="<%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()%>" size="50" onclick="selezionaIndirizzo('nazioneResidenza','callBackResidenzaRappLegale')" type="text" name="addressLegaleCitta" id="addressLegaleCitta" placeholder="DENOMINAZIONE COMUNE"/>
<%
				}
%>
					
					<input type="hidden" name="addressLegaleCityTesto"
					id="addressLegaleCityTesto"  value="<%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()%>"/></td>
			</tr>
			<tr id="addressLegaleCountryTR">
				<td nowrap>PROVINCIA RESIDENZA</td>
				<td>
					<input value="<%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia()%>" readonly="true" size="50" type="text" name="addressLegaleCountrySigla" id="addressLegaleCountrySigla" placeholder="DENOMINAZIONE PROVINCIA"/>
			</td>
			</tr>
			<tr>
				<td>INDIRIZZO RESIDENZA</td>
				<td>
					<table class="noborder">
						<tr>
							<td>
							<%
							String toponimoDEfault = "VIA";
							if(StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizioneToponimo()!=null && !"".equals(StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizioneToponimo()))
							{
								toponimoDEfault=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizioneToponimo();
							}
							%>
							<%=ToponimiList.getHtmlSelect("toponimoResidenza", toponimoDEfault)%>
							
							<input type="hidden" name="toponimoResidenzaId" id="toponimoResidenzaId" />
							
							</td>
							<td>
							
							<select name="addressLegaleLine1" id="addressLegaleLine1" class="required">
							<%
								if(StabilimentoOpu.getIdStabilimento()>0)
								{
									%>
									<option value="<%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getVia()%>" selected="selected"><%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getVia() %></option>
									
									<%
								}%>
							
							</select>
<!-- 							<input type="text" name="addressLegaleLine1" -->
<!-- 								id="addressLegaleLine1" class="required" > -->
								
								
								</td>
							<td><input type="text" name="civicoResidenza" readonly
								id="civicoResidenza" size="5" placeholder="NUM." maxlength="15"
								required="required" value="<%=toHtml(StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getCivico())%>" >
							</td>
							<td>
							<input type="text" name="capResidenza" id="capResidenza" readonly
							onfocus="chkCap(document.getElementById('addressLegaleCity').value,'capResidenza')"  title="DATI TITOLARE/LEGALE RAPPRESENTANTE: CAP INDIRIZZO RESIDENZA non valido. Tornare indietro e correggere il campo"
								size="4" placeholder="CAP" maxlength="5" required="required" value="<%= toHtml(StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getCap())%>">
								<input type="hidden" value="Calcola CAP" id="butCapResidenza" 
								onclick="calcolaCap(document.getElementById('addressLegaleCity').value, document.getElementById('toponimoResidenza').value, document.getElementById('addressLegaleLine1input').value, 'capResidenza');" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>DOMICILIO DIGITALE<br>(PEC)
				</td>
				<td><input type="text" size="70" name="domicilioDigitalePecSF">
				</td>
			</tr>
		</table>
	</fieldset>
	
	
	<fieldset id="setSedeLegale">
		<legend><b>DATI SEDE LEGALE</b></legend>
		<table style="height: 100%; width: 100%">
			<tr>
				<td></td>
				<td><input type="checkbox" onclick="copiaDaResidenza()"
					id="checkSeddeLegale"> SPUNTARE SE L'INDIRIZZO DI RESIDENZA
					COINCIDE CON LA SEDE LEGALE</td>
			</tr>
			<tr>
				<td><label for="nazioneN-2">NAZIONE</label></td>
				<td>
					<% NazioniList.setJsEvent("onchange=\"sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via')\""); %> 
					<% NazioniList.setJsEvent("onChange=\"abilitaDisabilitaIndirizzo('sedelegale')\""); %>
					<%=NazioniList.getHtmlSelect("nazioneSedeLegale", 106)%>
				</td>
			</tr>
			<tr>
		<td colspan="2">
			<font color="red">ATTENZIONE! Posizionarsi sul campo comune per inserire tutto l'indirizzo</font>
		</td>
	</tr>
			<tr>
				<td>COMUNE</td>
				<td>
				
				<!-- Cancellato perchè la gestione deve essere tolto jquery -->
				<!-- select name="searchcodeIdComune" id="searchcodeIdComune"
					class="required" -->
					<!-- %
								if(StabilimentoOpu.getIdStabilimento()>0)
								{
									% -->
									<!-- option value="<%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getComune()%>" selected="selected"><%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getDescrizioneComune() %></option -->
									
									<!-- %
								}% -->
						
				<!-- /select --> 
				
				<input type="hidden" name="searchcodeIdComuneId" id="searchcodeIdComuneId" value="<%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getComune()%>"/>

<%
				if((fissa!=null && !fissa.equals("") && (fissa.equals("false") || fissa.equals("api")) && comuneSuap!=null))
				{
%>
					<input size="50" onclick="pulisciCampo(this); selezionaIndirizzo('nazioneSedeLegale','callBackSedeLegale',this.value)" type="text" name="codeIdComune" id="codeIdComune" placeholder="DENOMINAZIONE COMUNE" value="<%=comuneSuap%>" />
<%
				}
				else
				{
%>
					<input size="50" onclick="pulisciCampo(this); selezionaIndirizzo('nazioneSedeLegale','callBackSedeLegale')" type="text" name="codeIdComune" id="codeIdComune" placeholder="DENOMINAZIONE COMUNE"  value="<%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getDescrizioneComune()%>" />
<%
				}
%>
				
				<input type="hidden" name="searchcodeIdComuneTesto"
					id="searchcodeIdComuneTesto"  value="<%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getDescrizioneComune()%>" /></td>
			</tr>
			<tr id="searchcodeIdprovinciaTR">
				<td>PROVINCIA</td>
				<td>
					<input value="<%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia() %>" readonly="true" size="50" type="text" name="searchcodeIdprovinciaSigla" id="searchcodeIdprovinciaSigla" placeholder="DENOMINAZIONE PROVINCIA"/>
				</td>
			</tr>
			<tr>
				<td>INDIRIZZO</td>
				<td>
					<table class="noborder">
						<tr>
							<td>
							<%
							String toponimoDEfaultSl = "VIA";
							if(StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getDescrizioneToponimo()!=null && !"".equals(StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getDescrizioneToponimo()))
							{
								toponimoDEfaultSl=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizioneToponimo();
							}
							%>
							<%=ToponimiList.getHtmlSelect("toponimoSedeLegale", toponimoDEfaultSl)%></td>
							<td>
							
							
							<select name="via" id="via" class="required">
							<%
								if(StabilimentoOpu.getIdStabilimento()>0)
								{
									%>
									<option value="<%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getVia()%>" selected="selected"><%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getVia() %></option>
									
									<%
								}%>
							
							</select>
							
							<input type="hidden" name="toponimoSedeLegaleId" id="toponimoSedeLegaleId" />
							
							</td>
							<td>
							<input type="text" name="civicoSedeLegale" readonly
								id="civicoSedeLegale" size="5" placeholder="NUM." maxlength="15"
								required="required" value="<%=toHtml(StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getCivico())%>">
								</td>
							<td><input type="text" name="presso" id="presso" size="4" value="<%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getCap()%>"
								placeholder="CAP"  maxlength="5" required="required" readonly
								onfocus="chkCap(document.getElementById('searchcodeIdComune').value,'presso')"  title="DATI SEDE LEGALE: CAP INDIRIZZO non valido. Tornare indietro e corregere il campo"> <input
								type="hidden" value="Calcola CAP" id="butCapSedeLegale"
								onclick="calcolaCap(document.getElementById('searchcodeIdComune').value, document.getElementById('toponimoSedeLegale').value, document.getElementById('viainput').value, 'presso');" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</fieldset>
	
	
	
<script type="text/javascript">
	document.getElementById('toponimoResidenza').disabled=true;
	document.getElementById('toponimoSedeLegale').disabled=true;
	document.getElementById('codeIdComune').readOnly=true;
</script>

<%
if((fissa!=null && !fissa.equals("") && (fissa.equals("false") || fissa.equals("api")) && comuneSuap!=null))
{	
	
%>
<script type="text/javascript">
	document.getElementById('nazioneSedeLegale').disabled=true;
</script>

<%
}
%>
