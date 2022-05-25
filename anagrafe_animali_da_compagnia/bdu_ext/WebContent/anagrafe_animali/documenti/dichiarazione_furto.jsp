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



<link type="text/css"
documentale_url="" href="anagrafe_animali/documenti/screen.css"
rel="stylesheet" />
	
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css"
	type="text/css" media="print" />

<jsp:useBean id="dati_furto" class="org.aspcfs.modules.registrazioniAnimali.base.EventoFurto"
	scope="request" />	
<jsp:useBean id="thisAnimale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request" />
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
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />


<style>
</style>


<%
//CATTURO INFO MICROCHIP PER SOSTITUIRLO COL TATUAGGIO NEL CASO SIA ASSENTE
String value_microchip="";
if (thisAnimale.getMicrochip()!=null && !thisAnimale.getMicrochip().equals(""))
value_microchip = thisAnimale.getMicrochip();
else if (thisAnimale.getTatuaggio()!=null && !thisAnimale.getTatuaggio().equals(""))
	value_microchip=thisAnimale.getTatuaggio();

java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); %> 



<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>
<body>
<input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" />
	
		
  <jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="idAnimale" value="<%=thisAnimale.getIdAnimale() %>" />
     <jsp:param name="idSpecie" value="<%=thisAnimale.getIdSpecie() %>" />
      <jsp:param name="idTipo" value="PrintDichiarazioneFurto" />
       <jsp:param name="idMicrochip" value="<%=value_microchip %>" />
</jsp:include>
	
	<!-- input type="submit" name="Timbra PDF" class="buttonClass"
	onclick="window.location.href='GestioneDocumenti.do?command=ListaDocumentiByTipo&tipo=PrintDichiarazioneSmarrimento&IdSpecie=<%=thisAnimale.getIdSpecie() %>&IdAnimale=<%=thisAnimale.getIdAnimale() %>&id_microchip=<%=value_microchip %>'" value="Gestione PDF" /-->
	
	
	 
<div class="imgRegione">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/regionecampania.jpg" />
</div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>

<dhv:evaluate if="<%=(thisAnimale.getIdAslRiferimento()>0) %>"> 
<div class="imgAsl">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/<%=aslList.getSelectedValue(thisAnimale.getIdAslRiferimento()) %>.jpg" />
</div>
</dhv:evaluate>
<div class="Section1">
 </br></br>
<div class="title1">DICHIARAZIONE DI FURTO <%=specieList.getSelectedValue(thisAnimale.getIdSpecie()).toUpperCase() %></div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;



<%
LineaProduttiva linea_proprietario=null;
Operatore proprietario = thisAnimale.getProprietario();
Stabilimento temp = null;
Indirizzo sedeOperativa = null;
LineaProduttiva temp1 = null;
if (proprietario!=null)
	if (proprietario.getListaStabilimenti()!=null)
		if (!proprietario.getListaStabilimenti().isEmpty()){
			temp = (Stabilimento) proprietario.getListaStabilimenti().get(0); 
			 temp1 = (LineaProduttiva) temp.getListaLineeProduttive().get(0);
			sedeOperativa = temp.getSedeOperativa();
			linea_proprietario= (LineaProduttiva) temp.getListaLineeProduttive().get(0);	
			}
%>
<div class="nodott">Prot. &nbsp; </div> <div class="dott_solid_short" style="margin-top: 40px;">&nbsp;</div>   
<div class="nodott" style="align:right; margin-left: 380px;">Lì &nbsp; </div> <div class="dott_solid_short" style="margin-top: 40px;">&nbsp;<%=dataToString( timeNow )%></div>
<div class="clear1"></div>
<div class="nodott">Il sottoscritto</div>
<div class="dott_solid_long">&nbsp; <%=proprietario.getRagioneSociale()%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">Codice Fiscale</div>
<div class="dott_solid_long" style="margin-top: 0px;">&nbsp; <%=(proprietario.getCodFiscale() != null && !("").equals(proprietario.getCodFiscale()))? proprietario.getCodFiscale() : "" %></div>

<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">e residente a</div>
<div class="dott_solid_long" style="margin-top: 0px;">&nbsp; <%=(sedeOperativa != null) ? sedeOperativa.getDescrizioneComune() : ""%></div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">via</div>
<div class="dott_solid_long" style="margin-top: 0px;">&nbsp; <%=(sedeOperativa != null) ? sedeOperativa.getVia() : ""%></div>
<div class="nodott" style="margin-top: 0px;">cap</div>
<div class="dott_solid_short" style="margin-top: 0px;">&nbsp; <%=(sedeOperativa != null) ? (sedeOperativa.getCap()) : ""%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">tel.</div>
<div class="dott_solid" style="margin-top: 0px;">&nbsp; <%=(linea_proprietario.getTelefono1() != null) ? (linea_proprietario.getTelefono1()) : ""%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">(documento di riconoscimento)</div>
<div class="dott_solid_long" style="margin-top: 0px;">&nbsp; <%=(proprietario.getRappLegale() != null && proprietario.getRappLegale().getDocumentoIdentita() != null) ? (proprietario.getRappLegale().getDocumentoIdentita()) : ""%></div>
<div class="clear1"></div>
</br></br>
<div class="nodott" style="margin-top: 0px;">proprietario del <%=thisAnimale.getNomeSpecieAnimale() %>:</div>
<div class="clear1"></div>
</br></br>
<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() != Furetto.idSpecie) %>"> <!--  SOLO CANI E GATTI -->
<div class="nodott" style="margin-top: 0px;">razza</div>
<div class="dott_solid" style="margin-top: 0px;">&nbsp; <%=(razzaList.getSelectedValue(thisAnimale.getIdRazza()))%></div>
</dhv:evaluate>
<div class="nodott" style="margin-top: 0px;">mantello</div>
<div class="dott_solid" style="margin-top: 0px;">&nbsp; <%=mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello())%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">sesso</div>
<div class="dott_solid_short" style="margin-top: 0px;">&nbsp; <%=(thisAnimale.getSesso())%></div>

<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() == Cane.idSpecie) %>"> 
<div class="nodott" style="margin-top: 0px;">taglia</div>
<div class="dott_solid" style="margin-top: 0px;">&nbsp; <%=tagliaList.getSelectedValue(( thisAnimale).getIdTaglia())%></div>
</dhv:evaluate>

<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">data di nascita</div>
<div class="dott_solid" style="margin-top: 0px;">&nbsp; <%=toDateasString(thisAnimale.getDataNascita())%></div>


<div class="nodott" style="margin-top: 0px;">tatuaggio o microchip</div>
<div class="dott_solid" style="margin-top: 0px;">&nbsp; <%=value_microchip%></div>


<div class="clear1"></div>
</br>
<div class="nodott" style="margin-top: 0px;text-align:center;">DICHIARA:</div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">che il <%=thisAnimale.getNomeSpecieAnimale() %> di cui sopra è stato rubato in data</div>
<div class="dott_solid" style="margin-top: 0px;">&nbsp; <%=toDateasString(dati_furto.getDataFurto())%></div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">a</div>
<div class="dott_solid" style="margin-top: 0px;">&nbsp;  <%=(dati_furto.getLuogoFurto() != null) ? dati_furto.getLuogoFurto() : ""%></div>
</br>
</br>

<br>
<div class="clear1"></div>

<div class="clear1"></div>

</br>
</br>

<div class="data">IL VETERINARIO</div>
<br>
<div class="datavalore">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> 
<div class="clear1"></div>


<div class="firma">IL DICHIARANTE</div>
<br>
<div class="firmavalore">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<div class="clear1"></div>
</br></br></br></br>
</div>
<div class="nodott_margin_low" >Data rilascio certificato:</div>

<div class="dott_margin_low" >&nbsp;<%=dataToString( timeNow ) %> </div>

<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>

</div>
</body>

