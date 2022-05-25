<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.Indirizzo"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>

<%@page import="org.aspcfs.modules.sintesis.actions.StabilimentoSintesisAction"%>


<jsp:useBean id="Richiesta" class="org.aspcfs.modules.sintesis.base.StabilimentoSintesisImport" scope="request"/>
<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="lineaAttivitaMasterList" class="java.lang.String" scope="request"/>

<jsp:useBean id="msg" class="java.lang.String" scope="request"/>
<jsp:useBean id="msgScartati" class="java.lang.String" scope="request"/>
<jsp:useBean id="codiceUscita" class="java.lang.String" scope="request"/>

<jsp:useBean id="LogImport" class="org.aspcfs.modules.sintesis.base.LogImport" scope="request"/>

<jsp:useBean id="relEsistente" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="relNuova" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="StatiStabilimento" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StatiLinea" class="org.aspcfs.utils.web.LookupList" scope="request" />



<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>-->
<script f src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script type="text/javascript" src="javascript/jquery.steps_modify.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT src="javascript/sintesis.js"></SCRIPT>
<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapUtil.js"></SCRIPT>
<script src="javascript/jquery.form.js"></script>
<style>
   .td {
   border-right: 1px solid #C1DAD7;
   border-bottom: 1px solid #C1DAD7;
   background: #fff;
   padding: 6px 6px 6px 12px;
   color: #6D929B;
   }
   #progress {
   position: relative;
   width: 400px;
   border: 1px solid #ddd;
   padding: 1px;
   border-radius: 3px;
   }
   #bar {
   background-color: #B4F5B4;
   width: 0%;
   height: 20px;
   border-radius: 3px;
   }
   #percent {
   position: absolute;
   display: inline-block;
   top: 3px;
   left: 48%;
   }
   input[readonly]
   {
   background-color:grey;
   }
   table.one {border-collapse:collapse;}
   td.b {
   border-style:solid;
   border-width:1px;
   border-color:#333333;
   padding:10px;
   }
</style>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
   var cal99 = new CalendarPopup();
   cal99.showYearNavigation();
   cal99.showYearNavigationInput();
   cal99.showNavigationDropdowns();
</SCRIPT> 
<%@ page import="java.util.*"%>
<%@ include file="../initPage.jsp"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>


<script>
   
   function visualizzaOrigine(){
	   var cb = document.getElementById("cbOrigineDati");
	   var div =  document.getElementById("divOrigineDati");
	   if (cb.checked)
		   div.style.display="block";
	   else
		   div.style.display="none";
   }
   
</script>

  
  <center><h2>Dettaglio richiesta record Sintesis</h2></center>
  
   
   <input  disabled type="hidden" name="idRichiesta"	id="idRichiesta" value="<%=Richiesta.getId()%>">
   
  <% if (Richiesta.getStatoImport() !=0) { %>
  

<table style="border: 1px solid black">
<tr><td> Record processato in data <%=toDateasString(Richiesta.getDataProcess()) %> da <dhv:username id="<%= Richiesta.getIdUtenteProcess() %>"></dhv:username> </td> </tr>
</td></tr>
</table>
 <%} %>
  <br/><br/>
  

  		
  		
  	<input type="checkbox" id="cbOrigineDati" onClick="visualizzaOrigine()")> Visualizza origine dati
  <div id="divOrigineDati" style="display:none">		
  <table class="details" cellpadding="0" cellspacing="0" width="100%">
 <tr> <th>Stato sede operativa</th><th>Approval Number</th><th>Denominazione sede operativa</th><th>Ragione Sociale Impresa</th><th>Partita Iva</th><th>Codice Fiscale</th></tr>
<tr><td><%=Richiesta.getStatoSedeOperativa() %></td>
  <td><%=Richiesta.getApprovalNumber() %></td>
  <td><%=Richiesta.getDenominazioneSedeOperativa() %></td>
  <td><%=Richiesta.getRagioneSocialeImpresa() %></td>	
  <td><%=Richiesta.getPartitaIva() %></td>	
  <td><%=Richiesta.getCodiceFiscale() %></td></tr>



 <tr> <th>Indirizzo</th><th>Comune</th><th>Sigla provincia</th><th>Provincia</th><th>Regione</th><th>Cod. Ufficio Veterinario</th></tr>
 <tr><td><%=Richiesta.getIndirizzo() %></td>
  <td><%=Richiesta.getComune() %></td>	
  <td><%=Richiesta.getSiglaProvincia() %></td>		
  <td><%=Richiesta.getProvincia() %></td>	
  <td><%=Richiesta.getRegione() %></td>	
  <td><%=Richiesta.getCodUfficioVeterinario() %></td>
 </tr>
  
  
  <tr><th>Ufficio Veterinario</th> <th>Attività</th><th>Stato Attività</th><th>Descrizione Sezione</th><th>Data inizio attività</th><th>Data fine attività</th></tr>
 <tr> <td><%=Richiesta.getUfficioVeterinario() %></td>	
 <td><%=Richiesta.getAttivita() %></td>
  <td><%=Richiesta.getStatoAttivita() %></td>
  <td><%=Richiesta.getDescrizioneSezione() %></td>		
  <td><%=Richiesta.getDataInizioAttivita() %></td>																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																													
  <td><%=Richiesta.getDataFineAttivita() %></td>
 </tr>	
  
  <tr> <th>Tipo autorizzazione</th> <th>Imballaggio</th><th>Paesi abilitati export</th><th>Remark</th><th>Species</th><th>Informazioni Aggiuntive</th></tr>
  <tr> <td><%=Richiesta.getTipoAutorizzazione() %></td>
   <td><%=Richiesta.getImballaggio() %></td>
  <td><%=Richiesta.getPaesiAbilitatiExport() %></td>
  <td><%=Richiesta.getRemark() %></td>
  <td><%=Richiesta.getSpecies() %></td>
  <td><%=Richiesta.getInformazioniAggiuntive() %></td> </tr>
  </table>
  	<br/><br/>	
  </div>		
  		
  
            <%
            int tipoImpresa = -1;
             if (Richiesta.getOpuTipoImpresa()>0)
            	tipoImpresa = Richiesta.getOpuTipoImpresa();
            
            int tipoSocieta = -1;
            if (Richiesta.getOpuTipoImpresa()>0)
            	tipoSocieta = Richiesta.getOpuTipoSocieta();
            
            String domicilioDigitale = "";
             if (Richiesta.getOpuDomicilioDigitale()!=null)
            	domicilioDigitale = Richiesta.getOpuDomicilioDigitale();
            
            int nazioneSedeLegale = 106;
            if (Richiesta.getOpuNazioneSedeLegale()>0)
            	nazioneSedeLegale = Richiesta.getOpuNazioneSedeLegale();
            
            int provinciaSedeLegale = -1;
            if (Richiesta.getOpuProvinciaSedeLegale()>0)
            	provinciaSedeLegale = Richiesta.getOpuProvinciaSedeLegale(); 
            
            String descrizioneProvinciaSedeLegale = "";
             if (Richiesta.getOpuProvinciaSedeLegale()>0)
            	descrizioneProvinciaSedeLegale = Richiesta.getOpuDescrizioneProvinciaSedeLegale(); 
            
            int comuneSedeLegale = -1;
             if (Richiesta.getOpuComuneSedeLegale()>0)
            	comuneSedeLegale = Richiesta.getOpuComuneSedeLegale();
            
            String descrizioneComuneSedeLegale = "";
             if (Richiesta.getOpuComuneSedeLegale()>0)
            	descrizioneComuneSedeLegale = Richiesta.getOpuDescrizioneComuneSedeLegale();
            
            int toponimoSedeLegale = -1;
            if (Richiesta.getOpuToponimoSedeLegale()>0)
            	toponimoSedeLegale = Richiesta.getOpuToponimoSedeLegale();  
            
            String descrizioneToponimoSedeLegale = "VIA";
            if (Richiesta.getOpuToponimoSedeLegale()>0)
            	descrizioneToponimoSedeLegale = Richiesta.getOpuDescrizioneToponimoSedeLegale();  
            
            String viaSedeLegale = "";
             if (Richiesta.getOpuViaSedeLegale()!=null)
            	viaSedeLegale = Richiesta.getOpuViaSedeLegale();  
           
            String civicoSedeLegale = "";
             if (Richiesta.getOpuCivicoSedeLegale()!=null)
            	civicoSedeLegale = Richiesta.getOpuCivicoSedeLegale();  
            
            String capSedeLegale = "";
             if (Richiesta.getOpuCapSedeLegale()!=null)
            	capSedeLegale = Richiesta.getOpuCapSedeLegale();  
            
            String nomeRappresentante = "";
             if (Richiesta.getOpuNomeRappresentante()!=null)
            	nomeRappresentante = Richiesta.getOpuNomeRappresentante();  
            
            String cognomeRappresentante = "";
            if (Richiesta.getOpuNomeRappresentante()!=null)
            	cognomeRappresentante = Richiesta.getOpuCognomeRappresentante();
            
            String sessoRappresentante = "";
            if (Richiesta.getOpuSessoRappresentante()!=null)
            	sessoRappresentante = Richiesta.getOpuSessoRappresentante();  
            
            String dataNascitaRappresentante = "";
             if (Richiesta.getOpuDataNascitaRappresentante()!=null)
            	dataNascitaRappresentante = Richiesta.getOpuDataNascitaRappresentante().toString();  
            
            int nazioneNascitaRappresentante = 106;
             if (Richiesta.getOpuNazioneNascitaRappresentante()>0)
            	nazioneNascitaRappresentante = Richiesta.getOpuNazioneNascitaRappresentante();  
            
            String comuneNascitaRappresentante = "";
             if (Richiesta.getOpuComuneNascitaRappresentante()!=null)
            	comuneNascitaRappresentante = Richiesta.getOpuComuneNascitaRappresentante();  
            
            String codiceFiscaleRappresentante = "";
             if (Richiesta.getOpuCodiceFiscaleRappresentante()!=null)
            	codiceFiscaleRappresentante = Richiesta.getOpuCodiceFiscaleRappresentante();  
            
            int nazioneResidenzaRappresentante = 106;
            if (Richiesta.getOpuNazioneResidenzaRappresentante()>0)
            	nazioneResidenzaRappresentante = Richiesta.getOpuNazioneResidenzaRappresentante();  
            
            int provinciaResidenzaRappresentante = -1;
            if (Richiesta.getOpuProvinciaResidenzaRappresentante()>0)
            	provinciaResidenzaRappresentante = Richiesta.getOpuProvinciaResidenzaRappresentante();  
            
            String descrizioneProvinciaResidenzaRappresentante = "";
            if (Richiesta.getOpuProvinciaResidenzaRappresentante()>0)
            	descrizioneProvinciaResidenzaRappresentante = Richiesta.getOpuDescrizioneProvinciaResidenzaRappresentante();  
            
            int comuneResidenzaRappresentante = -1;
            if (Richiesta.getOpuComuneResidenzaRappresentante()>0)
            	comuneResidenzaRappresentante = Richiesta.getOpuComuneResidenzaRappresentante();  
        
            String descrizioneComuneResidenzaRappresentante = "";
             if (Richiesta.getOpuProvinciaResidenzaRappresentante()>0)
            	descrizioneComuneResidenzaRappresentante = Richiesta.getOpuDescrizioneComuneResidenzaRappresentante(); 
            
            int toponimoResidenzaRappresentante = -1;
             if (Richiesta.getOpuToponimoResidenzaRappresentante()>0)
            	toponimoResidenzaRappresentante = Richiesta.getOpuToponimoResidenzaRappresentante();  
            
            String descrizioneToponimoResidenzaRappresentante = "VIA";
             if (Richiesta.getOpuToponimoResidenzaRappresentante()>0)
            	descrizioneToponimoResidenzaRappresentante = Richiesta.getOpuDescrizioneToponimoResidenzaRappresentante();  
            
            String viaResidenzaRappresentante = "";
             if (Richiesta.getOpuViaResidenzaRappresentante()!=null)
            	viaResidenzaRappresentante = Richiesta.getOpuViaResidenzaRappresentante();  
           
            String civicoResidenzaRappresentante = "";
             if (Richiesta.getOpuCivicoResidenzaRappresentante()!=null)
            	civicoResidenzaRappresentante = Richiesta.getOpuCivicoResidenzaRappresentante();  
            
            String capResidenzaRappresentante = "";
            if (Richiesta.getOpuCapResidenzaRappresentante()!=null)
            	capResidenzaRappresentante = Richiesta.getOpuCapResidenzaRappresentante(); 
            
            String domicilioDigitaleRappresentante = "";
             if (Richiesta.getOpuDomicilioDigitaleRappresentante()!=null)
            	domicilioDigitaleRappresentante = Richiesta.getOpuDomicilioDigitaleRappresentante();
            %>
            

 
      <div style="display: none;"> 
         &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
      </div>
      
      
  		
  		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<tr><th colspan="2">Dati Impresa</th></tr>

<tr>
<td class="formLabel">Ragione Sociale Impresa</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getRagioneSocialeImpresa() %></span></td>
</tr>

<tr>
<td class="formLabel">Partita IVA</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getPartitaIva() %></span></td>
</tr>

<tr>
<td class="formLabel">Codice Fiscale</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getCodiceFiscale() %></span></td>
</tr>


<tr><td class="formLabel">Tipo Impresa</td>
<td> <%=TipoImpresaList.getSelectedValue(tipoImpresa) %></td></tr>
<tr><td class="formLabel" nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
<td><%=TipoSocietaList.getSelectedValue(tipoSocieta)%></td>

<tr id="codFiscaleTR">
<td class="formLabel" nowrap>DOMICILIO DIGITALE(PEC)</td>
<td><%= domicilioDigitale%></td>
</tr>

<tr><th colspan="2">Sede Legale</th></tr>

<tr>
<td class="formLabel">Nazione</td>
<td><%=NazioniList.getSelectedValue(nazioneSedeLegale)%></td>
</tr>

<tr id="searchcodeIdprovinciaTR">
<td class="formLabel">PROVINCIA</td>
<td><%=descrizioneProvinciaSedeLegale %></td>
</tr>
               <tr>
                  <td class="formLabel">COMUNE</td>
                  <td><%=descrizioneComuneSedeLegale %></td>
               </tr>
               <tr>
                  <td class="formLabel" >INDIRIZZO</td>
                  <td>
                     <table class="noborder">
                        <tr>
                           	<td>
						
							<%=ToponimiList.getSelectedValue(descrizioneToponimoSedeLegale)%>
							</td>
                           <td>
                             <%=viaSedeLegale %>
                           </td>
                           <td>
                              <%= civicoSedeLegale%> <%= capSedeLegale%>
                           </td>
                        </tr>
                     </table>
               </tr>


<tr><th colspan="2">Dati stabilimento</th></tr>

<tr>
<td class="formLabel">Stato sede operativa</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getStatoSedeOperativa() %></span></td>
</tr>

<tr>
<td class="formLabel">Approval number</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getApprovalNumber() %></span></td>
</tr>

<tr>
<td class="formLabel">Denominazione sede operativa</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getDenominazioneSedeOperativa() %></span></td>
</tr>


<tr>
<td class="formLabel">Indirizzo</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getIndirizzo() %></span></td>
</tr>

<tr>
<td class="formLabel">Comune</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getComune() %></span></td>
</tr>

<tr>
<td class="formLabel">Sigla Provincia</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getSiglaProvincia() %></span></td>
</tr>

<tr>
<td class="formLabel">Provincia</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getProvincia() %></span></td>
</tr>

<tr>
<td class="formLabel">Regione</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getRegione() %></span></td>
</tr>

<tr>
<td class="formLabel">Cod. Ufficio Veterinario</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getCodUfficioVeterinario() %></span></td>
</tr>

<tr>
<td class="formLabel">Ufficio Veterinario</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getUfficioVeterinario() %></span></td>
</tr>


<tr><th colspan="2">Rappresentante legale</th></tr>

 <tr>
                  <td class="formLabel">NOME</td>
                  <td><%=nomeRappresentante%>
                  </td>
               </tr>
               <tr>
                  <td class="formLabel"><label for="cognome-2">COGNOME </label></td>
                  <td><%=cognomeRappresentante%>
                  </td>
               </tr>
               <tr>
                  <td class="formLabel"><label for="sesso-2">SESSO </label></td>
                  <td>
                     <div class="test">
                         <%= sessoRappresentante %>
                     </div>
                  </td>
               </tr>
               <tr>
                  <td class="formLabel"><label for="dataN-2">DATA NASCITA </label></td>
                  <td><%=toDateasStringFromString(dataNascitaRappresentante)%> 
                  </td>
               </tr>
              
               <tr>
                  <td class="formLabel"><label for="nazioneN-2">NAZIONE NASCITA</label></td>
                  <td>
                   
                     <%=NazioniList.getSelectedValue(nazioneNascitaRappresentante)%>
                  </td>
               </tr>
               <tr>
                  <td class="formLabel" nowrap>COMUNE NASCITA</td>
                  <td>
                   <%=comuneNascitaRappresentante %>
                  </td>
                  
               </tr>
               <tr>
                  <td class="formLabel" nowrap >CODICE FISCALE</td>
                  <td><%=codiceFiscaleRappresentante%></td>
               </tr>
               <tr>
                  <td class="formLabel">&nbsp;</td>
                  <td></td>
               </tr>
               <tr>
                  <td class="formLabel"><label for="nazioneN-2">NAZIONE RESIDENZA</label></td>
                  <td>
                   
                     <%=NazioniList.getSelectedValue(nazioneResidenzaRappresentante)%>
                  </td>
               </tr>
               <tr id="addressLegaleCountryTR">
                  <td class="formLabel" nowrap >PROVINCIA RESIDENZA</td>
                  <td><%=descrizioneProvinciaResidenzaRappresentante %>
               </tr>
               <tr>
                  <td class="formLabel" nowrap >COMUNE RESIDENZA</td>
                  <td>
                   <%=descrizioneComuneResidenzaRappresentante %></td>
               </tr>
               <tr>
                  <td class="formLabel" >INDIRIZZO RESIDENZA</td>
                  <td>
                     <table class="noborder">
                        <tr>
                        <td>
							
							<%=ToponimiList.getSelectedValue(descrizioneToponimoResidenzaRappresentante)%>
							</td>
                           <td><%=viaResidenzaRappresentante %>
                           </td>
                           <td><%=civicoResidenzaRappresentante%></td>
                           <td><%=capResidenzaRappresentante%>
                            
                           </td>
                        </tr>
                     </table>
                 </td>
               </tr>
               <tr>
                  <td class="formLabel">DOMICILIO DIGITALE<br>(PEC)
                  </td>
                  <td><%=domicilioDigitaleRappresentante%></td>
               </tr>
               
      <tr> <th colspan="2">Linea attività</th>        </tr>
               <tr>
<td class="formLabel">Attivita'</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getAttivita() %></span></td>
</tr>


<tr>
<td class="formLabel">Stato attivita'</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getStatoAttivita() %></span></td>
</tr>

<tr>
<td class="formLabel">Descrizione Sezione</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getDescrizioneSezione() %></span></td>
</tr>

<tr>
<td class="formLabel">Data inizio attivita'</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getDataInizioAttivita() %></span></td>
</tr>

<tr>
<td class="formLabel">Data fine attivita'</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getDataFineAttivita() %></span></td>
</tr>

<tr>
<td class="formLabel">Tipo autorizzazione</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getTipoAutorizzazione() %></span></td>
</tr>

<tr>
<td class="formLabel">Imballaggio</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getImballaggio() %></span></td>
</tr>

<tr>
<td class="formLabel">Paesi abilitati export</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getPaesiAbilitatiExport() %></span></td>
</tr>

<tr>
<td class="formLabel">Remark</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getRemark() %></span></td>
</tr>

<tr>
<td class="formLabel">Species</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getSpecies() %></span></td>
</tr>

<tr>
<td class="formLabel">Informazioni aggiuntive</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getInformazioniAggiuntive() %></span></td>
</tr>


<tr><td class="formLabel">Linea Master List</td>
<td><span style="background-color:#c9f0c9;"><%=lineaAttivitaMasterList %></span>  
</td></tr>


</table>
      




