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

<%@page	import="java.util.concurrent.TimeUnit"%>

<link rel="stylesheet" type="text/css" media="screen"
	documentale_url="" href="anagrafe_animali/documenti/screen.css">
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css"
	type="text/css" media="print" />


<jsp:useBean id="Cane"	class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request" />
<jsp:useBean id="Gatto"	class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request" />
<jsp:useBean id="Furetto" class="org.aspcfs.modules.anagrafe_animali.base.Furetto" scope="request" />
<!-- LOOKUPS DECODIFICA -->
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="proprietario" class="org.aspcfs.modules.opu.base.Operatore"	scope="request" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="eventoF" class="org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU" scope="request" />
<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="regioniList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="nazioniList" class="org.aspcfs.utils.web.LookupList"	scope="request" />

	
<style>
</style>
<%
	Animale thisAnimale = null;
	if (Cane.getIdCane() > 0)
		thisAnimale = Cane;
	if (Gatto.getIdGatto() > 0)
		thisAnimale = Gatto;
	if (Furetto.getIdFuretto() > 0)
		thisAnimale = Furetto;
	LineaProduttiva linea_proprietario=null;

%>

<% 
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
			}%>

<%
//CATTURO INFO MICROCHIP PER SOSTITUIRLO COL TATUAGGIO NEL CASO SIA ASSENTE
String value_microchip="";
if (thisAnimale.getMicrochip()!=null && !thisAnimale.getMicrochip().equals(""))
value_microchip = thisAnimale.getMicrochip();
else if (thisAnimale.getTatuaggio()!=null && !thisAnimale.getTatuaggio().equals(""))
	value_microchip=thisAnimale.getTatuaggio();
%>

<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>
<body>
<input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" />
	
  <jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="idAnimale" value="<%=thisAnimale.getIdAnimale() %>" />
     <jsp:param name="idSpecie" value="<%=thisAnimale.getIdSpecie() %>" />
      <jsp:param name="idTipo" value="PrintRichiestaIscrizione" />
       <jsp:param name="idMicrochip" value="<%=value_microchip %>" />
</jsp:include>
			
				<!-- input type="submit" name="Timbra PDF" class="buttonClass"
	onclick="window.location.href='GestioneDocumenti.do?command=ListaDocumentiByTipo&tipo=PrintRichiestaIscrizione&IdSpecie=<%=thisAnimale.getIdSpecie() %>&IdAnimale=<%=thisAnimale.getIdAnimale() %>&id_microchip=<%=value_microchip %>'" value="Gestione PDF" /-->
	
	<!-- <a href="AnimaleAction.do?command=Test&tipo=rich_prima_iscr&IdSpecie=<%=thisAnimale.getIdSpecie() %>&IdAnimale=<%=thisAnimale.getIdAnimale() %>&nomeProprietario=<%=proprietario.getRagioneSociale() %>">GENERA PDF TIMBRATO</a>-->
	
	
<div class="imgRegione">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/regionecampania.jpg" />
</div>

	
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>

<dhv:evaluate if="<%=(thisAnimale.getIdAslRiferimento()>0 && thisAnimale.getIdAslRiferimento()!=Constants.ID_ASL_FUORI_REGIONE) %>"> 
<div class="imgAsl">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/<%=aslList.getSelectedValue(thisAnimale.getIdAslRiferimento()) %>.jpg" />
</div>
</dhv:evaluate>

<div class="Section1">
 </br></br>
<div class="title1">RICHIESTA DI ISCRIZIONE ALL'ANAGRAFE CANINA REGIONALE</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;


<div class="nodott">Il sottoscritto</div>
<div class="dott_long">&nbsp; <%=proprietario.getRagioneSociale()%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">Codice Fiscale</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=(proprietario.getCodFiscale() != null && !("").equals(proprietario.getCodFiscale()))? proprietario.getCodFiscale() : "" %></div>
<div class="nodott" style="margin-top: 0px;">Nato a</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=(proprietario.getRappLegale() != null) ? proprietario.getRappLegale().getComuneNascita() : ""%></div>
<div class="clear1"></div>


<div class="nodott" style="margin-top: 0px;">il</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=(proprietario.getRappLegale() != null) ? toDateasString(proprietario.getRappLegale().getDataNascita()) : ""%></div>
<div class="nodott" style="margin-top: 0px;">e residente in</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=(sedeOperativa != null) ? sedeOperativa.getDescrizioneComune() : ""%></div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">alla via</div>
<div class="dott_long" style="margin-top: 0px;">&nbsp; <%=(sedeOperativa != null) ? sedeOperativa.getVia() : ""%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">cap</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=(sedeOperativa != null) ? (sedeOperativa.getCap()) : ""%></div>
<div class="nodott" style="margin-top: 0px;">tel.</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=(linea_proprietario.getTelefono1() != null) ? (linea_proprietario.getTelefono1() ) : ""%></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">recapito secondario</div>
<div class="dott" style="margin-top: 0px;">&nbsp; 
<% 
  if(proprietario.getRappLegale() != null){
	  String telefono_sec="";
	  if(proprietario.getRappLegale().getTelefono1()!=null && !proprietario.getRappLegale().getTelefono1().equals("")) telefono_sec=proprietario.getRappLegale().getTelefono1();
   	  if(proprietario.getRappLegale().getTelefono2()!=null && !proprietario.getRappLegale().getTelefono2().equals("")) telefono_sec+="/"+proprietario.getRappLegale().getTelefono2();
	  out.print(telefono_sec);
  } 
%>
</div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">chiede, giusto quanto 
disposto dalla L. 281/91, l'iscrizione all'anagrafe canina del proprio <%=specieList.getSelectedValue(thisAnimale.getIdSpecie()) %>:</div>
<div class="clear1"></div>

<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() != Furetto.idSpecie) %>"> <!--  SOLO CANI E GATTI -->
<div class="nodott" style="margin-top: 0px;">razza</div>
<div class="dott_long" style="margin-top: 0px;">&nbsp; <%=(razzaList.getSelectedValue(thisAnimale.getIdRazza()))%></div>
</dhv:evaluate>
<div class="nodott" style="margin-top: 0px;">sesso</div>
<div class="dott_short" style="margin-top: 0px;">&nbsp; <%=(thisAnimale.getSesso())%></div>
<div class="clear1"></div>


<div class="nodott" style="margin-top: 0px;">data di nascita</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=toDateasString(thisAnimale.getDataNascita())%></div>
<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() == Cane.idSpecie) %>"> 
<div class="nodott" style="margin-top: 0px;">taglia</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=tagliaList.getSelectedValue(( thisAnimale).getIdTaglia())%></div>
</dhv:evaluate>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">mantello</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello())%></div>
<div class="clear1"></div>


<div class="nodott" style="margin-top: 0px;">segni particolari</div>
<div class="dott" style="margin-top: 0px;">&nbsp; 
 <%=(thisAnimale.getSegniParticolari() != null) ? (thisAnimale.getSegniParticolari() ) : ""%></div>
<div class="clear1"></div>


<div class="nodott" style="margin-top: 0px;">sterilizzato</div>
<div class="dott" style="margin-top: 0px;">&nbsp;  <%=(thisAnimale.isFlagSterilizzazione()) ? " SI il " +toDateasString(thisAnimale.getDataSterilizzazione()) : " NO"%></div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">nome del <%=specieList.getSelectedValue(thisAnimale.getIdSpecie()) %></div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=(thisAnimale.getNome())%>
</div>


<div class="clear1"></div>

<div class="Section1">
<!-- CONDIZIONI PER LA VISUALIZZAZIONE DEI DATI SULL'ORIGINE DELL'ANIMALE -->
<%	
java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); 
java.sql.Date data_reg= new java.sql.Date(thisAnimale.getDataNascita().getTime());
long diff = timeNow.getTime() - data_reg.getTime();
long giorni= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
if(giorni<365 && (linea_proprietario.getIdAttivita()==1 || linea_proprietario.getIdAttivita()==4 || linea_proprietario.getIdAttivita()==5 || linea_proprietario.getIdAttivita()==6 || linea_proprietario.getIdAttivita()==8) && thisAnimale.getIdSpecie()==1){ %>
  <%@ include file="autocertificazione_mancata_origine_animale.jsp"%> 
<% } %>


</div>


</br>
<div class="nodott" style="margin-top: 0px;">Dichiara di essere a conoscenza dei seguenti obblighi di legge (L.R. 3/2019) :: <br>
- denunziare entro 3 giorni, la morte o lo smarrimento del soggetto; <br>
- denunziare entro 5 giorni, la variazione della propria residenza o il trasferimento di proprietà del cane. <br>
</div>
<div class="clear1"></div>


<br><br>
<div class="nodott" style="margin-top: 0px;">Documento di riconoscimento</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=(proprietario.getRappLegale().getDocumentoIdentita() != null) ?  (proprietario.getRappLegale().getDocumentoIdentita()) : ""%>
</div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">Microchip</div>
<div class="dott" style="margin-top: 0px;">&nbsp;  <%=(thisAnimale.getMicrochip() != null) ? thisAnimale.getMicrochip() : ""%> 
</div>
<div class="clear1"></div>
<br></br>

<div class="data">DATA</div>
<br>
<div class="datavalore"><%=dataToString( timeNow ) %></div>  

<div class="clear1"></div>
<div class="firma">FIRMA</div>
<br>
<div class="firmavalore">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<div class="clear1"></div>
<div class="clear1"></div><div class="clear1"></div>
<div class="clear1"></div><div class="clear1"></div>
<div class="clear1"></div><div class="clear1"></div>
<div class="clear1"></div><div class="clear1"></div>
<div class="clear1"></div><div class="clear1"></div>
<div class="clear1"></div>
<div class="clear1"></div>
<div class="clear1"></div>
<div class="clear1"></div><br/><br/>


</div>
<br/>
</body>

