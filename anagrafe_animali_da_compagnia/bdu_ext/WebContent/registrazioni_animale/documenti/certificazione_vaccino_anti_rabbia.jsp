<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,org.aspcfs.modules.opu.base.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="request" />


<link rel="stylesheet" type="text/css" media="screen"
	documentale_url="" href="anagrafe_animali/documenti/screen.css">
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css"
	documentale_url="" type="text/css" media="print" />


<jsp:useBean id="animale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request" />
<jsp:useBean id="dati_vaccinazione" class="org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni" scope="request"/>

<!-- LOOKUPS DECODIFICA -->
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<!-- script>
$(document).ready(function(){
	 $('#timbra').submit();
	
});</script-->

<style>
</style>
<%

	LineaProduttiva linea_proprietario=null;
	Operatore prop = animale.getProprietario();
	Stabilimento stabProp = (Stabilimento)  prop.getListaStabilimenti().get(0);
	LineaProduttiva lprop = (LineaProduttiva) stabProp.getListaLineeProduttive().get(0);
	Indirizzo indProp = stabProp.getSedeOperativa();

%>



<%
//CATTURO INFO MICROCHIP PER SOSTITUIRLO COL TATUAGGIO NEL CASO SIA ASSENTE
String value_microchip="";
if (animale.getMicrochip()!=null && !animale.getMicrochip().equals(""))
value_microchip = animale.getMicrochip();
else if (animale.getTatuaggio()!=null && !animale.getTatuaggio().equals(""))
	value_microchip=animale.getTatuaggio();
%>

<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>
<body>
<input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" />
	
		<!-- input type="submit" name="Timbra PDF" class="buttonClass"
	onclick="window.location.href='GestioneDocumenti.do?command=ListaDocumentiByTipo&tipo=PrintCertificatoVaccinazioneAntiRabbia&IdSpecie=<%=animale.getIdSpecie() %>&IdAnimale=<%=animale.getIdAnimale() %>&id_microchip=<%=value_microchip %>'" value="Gestione PDF" /-->
	
		
<form name="timbra" id ="timbra" action="GestioneDocumenti.do?command=TimbraSQL" method="POST">
<input type="hidden" id ="generaPDF" value="Genera PDF" onClick="this.form.submit()" />
<input type="hidden" name="IdAnimale" id="IdAnimale" value="<%=animale.getIdAnimale() %>"></input>
<input type="hidden" name="IdSpecie" id="IdSpecie" value="<%=animale.getIdSpecie() %>"></input>
<input type="hidden" name="tipo" id="tipo" value="<%="PrintCertificatoVaccinazioneAntiRabbia"%>"></input>
<input type="hidden" name="id_microchip" id="id_microchip" value="<%=value_microchip %>"></input>
</form>
	
	
	 
<div class="imgRegione">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/regionecampania.jpg" />
</div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
<dhv:evaluate if="<%=(animale.getIdAslRiferimento()>0 && animale.getIdAslRiferimento()!=Constants.ID_ASL_FUORI_REGIONE && User.getRoleId() != Integer.parseInt(ApplicationProperties.getProperty("ID_RUOLO_LLP"))) %>"> 
<div class="imgAsl">
<dhv:evaluate if="<%=(User.getRoleId() == Integer.parseInt(ApplicationProperties.getProperty("UNINA"))) %>">
<img style="text-decoration: none;" documentale_url="" src="anagrafe_animali/documenti/images/unina3.png" />
</dhv:evaluate>
<dhv:evaluate if="<%=(User.getRoleId() != Integer.parseInt(ApplicationProperties.getProperty("UNINA"))) %>">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/<%=aslList.getSelectedValue(animale.getIdAslRiferimento()) %>.jpg" />
</dhv:evaluate>
</div>
</dhv:evaluate>
<dhv:evaluate if="<%=(User.getRoleId() == Integer.parseInt(ApplicationProperties.getProperty("ID_RUOLO_LLP"))) %>"> 
<div class="imgAsl" style="border: 1px solid black;">
Timbro ambulatorio
</div>
</dhv:evaluate>

<div class="Section1">
 </br></br>
<div class="title1">Certificazione di vaccinazione antirabbica</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;


<div class="nodott" style="margin-top: 0px;">Io sottoscritto dott. </div>
<div class="dott_long"  style="margin-top: 0px;">&nbsp; <%=User.getContact().getNameFull() %></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">iscritto all'ordine della provincia di  </div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=provinceList.getSelectedValue(User.getContact().getIdProvinciaIscrizioneOrdine()) %> </div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 6px;">nr. di iscrizione</div>
<div class="dott"  style="margin-top: 3px;">&nbsp; <%=(User.getContact().getNrIscrizioneOrdine() != null ) ? User.getContact().getNrIscrizioneOrdine() : "--" %> </div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 6px;">certifico di aver praticato la vaccinazione antirabbica precontagio in data</div>
<div class="dott"  style="margin-top: 3px;">&nbsp;<%=toDateasString(dati_vaccinazione.getDataVaccinazione())%> </div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 6px;">&nbsp;al <%=specieList.getSelectedValue(animale.getIdSpecie()).toLowerCase() %> avente identificativo</div>
<div class="dott" style="margin-top: 0px;">&nbsp;<%=(value_microchip)%></div>
<div class="nodott" style="margin-top: 0px;">sesso</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=animale.getSesso() %></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">nome</div>
<div class="dott" style="margin-top: 0px;">&nbsp;&nbsp;<%=(animale.getNome() != null && !("").equals(animale.getNome())) ? animale.getNome() : " -- "%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">razza</div>
<div class="dott_long" style="margin-top: 0px;">&nbsp;&nbsp;<%=(razzaList.getSelectedValue(animale.getIdRazza()))%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">mantello</div>
<div class="dott_long" style="margin-top: 0px;">&nbsp;&nbsp;<%=(mantelloList.getSelectedValue(animale.getIdTipoMantello()))%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">data di nascita</div>
<div class="dott" style="margin-top: 0px;">&nbsp;&nbsp;<%=toDateasString(animale.getDataNascita())%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">proprietario</div>
<div class="dott_long" style="margin-top: 0px;">&nbsp;&nbsp;<%=(animale.getProprietario().getRagioneSociale())%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">residente in via </div>
<div class="dott_long" style="margin-top: 0px;">&nbsp;&nbsp;<%=(indProp.getVia())%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">telefono </div>
<div class="dott" style="margin-top: 0px;">&nbsp;<%=(lprop.getTelefono1() != null) ? lprop.getTelefono1() : "--" %></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">comune </div>
<div class="dott_long" style="margin-top: 0px;">&nbsp;&nbsp;<%=comuniList.getSelectedValue(indProp.getComune())%></div>
<div class="nodott" style="margin-top: 0px;">provincia </div>
<div class="dott" style="margin-top: 0px;">&nbsp;&nbsp;<%=provinceList.getSelectedValue(indProp.getProvincia())%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">con vaccino lotto </div>
<div class="dott" style="margin-top: 0px;">&nbsp;&nbsp;<%=dati_vaccinazione.getNumeroLottoVaccino()%></div>
<div class="nodott" style="margin-top: 0px;">nome </div>
<div class="dott" style="margin-top: 0px;">&nbsp;&nbsp;<%=dati_vaccinazione.getNomeVaccino()%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">produttore </div>
<div class="dott" style="margin-top: 0px;">&nbsp;&nbsp;<%=(dati_vaccinazione.getProduttoreVaccino() != null) ? dati_vaccinazione.getProduttoreVaccino() : "" %></div>
<div class="nodott" style="margin-top: 0px;">data scadenza </div>
<div class="dott" style="margin-top: 0px;">&nbsp;&nbsp;<%=toDateasString(dati_vaccinazione.getDataScadenzaVaccino())%></div>

</br></br>

<div class="nodott" style="margin-top: 0px;">Si rilascia detto certificato per gli usi di legge.</div>



</br>
</br>
<%	
java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); %>
<br>
<div class="clear1"></div>

<div class="firma">Il veterinario (timbro e firma)</div>
<br>
<div class="firmavalore">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<div class="clear1"></div>
<br/><br/><br/><br/><br/><br/><br/><br/>


<div class="data">DATA</div>
<br>


<div class="datavalore"><%=dataToString( timeNow ) %> </div> <br>
<div class="clear1"></div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>


</div>
</body>

