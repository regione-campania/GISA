<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,org.aspcfs.modules.opu.base.*,java.util.Date"%>
	<%@ page
	import="org.aspcfs.modules.registrazioniAnimali.base.*"%>
	
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>

<link rel="stylesheet" type="text/css" media="screen"
	href="anagrafe_animali/documenti/screen.css">
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css"
	type="text/css" media="print" />
	
<jsp:useBean id="animale" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request" />
<jsp:useBean id="evento" class="org.aspcfs.modules.registrazioniAnimali.base.Evento" scope="request" />
<jsp:useBean id="dati_registrazione_adozione_canile" class="org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaCanile" scope="request" />
<jsp:useBean id="dati_registrazione_adozione" class="org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl" scope="request" />	
<!-- LOOKUPS DECODIFICA -->
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
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
<jsp:useBean id="proprietario" class="org.aspcfs.modules.opu.base.Operatore"
	scope="request" />
<jsp:useBean id="vecchioProprietario" class="org.aspcfs.modules.opu.base.Operatore"
	scope="request" />
<jsp:useBean id="vecchioDetentore" class="org.aspcfs.modules.opu.base.Operatore"
	scope="request" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request" />

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

	  <jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="idAnimale" value="<%=animale.getIdAnimale() %>" />
     <jsp:param name="idSpecie" value="<%=animale.getIdSpecie() %>" />
      <jsp:param name="idTipo" value="PrintRichiestaAdozione" />
       <jsp:param name="idMicrochip" value="<%=value_microchip %>" />
        <jsp:param name="idEvento" value="<%=request.getParameter("idEvento")%>" />
</jsp:include>



<div class="imgRegione">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/regionecampania.jpg" />
</div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
<dhv:evaluate if="<%=(evento.getIdAslRiferimento()>0) %>"> 
<div class="imgAsl">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/<%=aslList.getSelectedValue(evento.getIdAslRiferimento()) %>.jpg" />
</div>
</dhv:evaluate>
<div class="Section1">
 </br></br>
<div class="title1">RICHIESTA DI ADOZIONE DA RIFUGIO REGIONALE</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;

<!--  ADOZIONE FUORI ASL -->
<%System.out.println(evento.getIdTipologiaEvento()+" "+EventoAdozioneFuoriAsl.idTipologiaDB); %>

<%
Stabilimento stabProp = null;
Indirizzo indirizzoOperativoProp = null;
LineaProduttiva lp = null;

if (proprietario!=null)
	if (proprietario.getListaStabilimenti()!=null)
		if (!proprietario.getListaStabilimenti().isEmpty()){
			stabProp = (Stabilimento) proprietario.getListaStabilimenti().get(0); 
			indirizzoOperativoProp = stabProp.getSedeOperativa();
			lp = (LineaProduttiva) stabProp.getListaLineeProduttive().get(0);
		}



%>
<div class="nodott">L'associazione/canile</div>
<div class="dott_long"><%=proprietario.getRagioneSociale() %></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">Codice Fiscale</div>
<div class="dott" style="margin-top: 0px;"><%=proprietario.getCodFiscale() %> </div>
<div class="nodott" style="margin-top: 0px;">Partita iva</div>
<div class="dott" style="margin-top: 0px;"><%=proprietario.getPartitaIva() %></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">con sede in</div>
<div class="dott" style="margin-top: 0px;"><%=comuniList.getSelectedValue(indirizzoOperativoProp.getComune()) %></div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">alla via</div>
<div class="dott_long" style="margin-top: 0px;">&nbsp;<%=indirizzoOperativoProp.getVia() %> </div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">cap</div>
<div class="dott" style="margin-top: 0px;"><%=indirizzoOperativoProp.getCap() %> </div>
<div class="nodott" style="margin-top: 0px;">tel.</div>
<div class="dott" style="margin-top: 0px;">&nbsp;<%= lp.getTelefono1() %> </div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">chiede, giusto quanto
disposto dalla L. 281/91, l'adozione del cane randagio di proprietà del</div>
<div class="clear1"></div>

<%
  Stabilimento stab = (Stabilimento) vecchioProprietario.getListaStabilimenti().get(0);
  Indirizzo indirizzoOperativo = stab.getSedeOperativa();
%>

<div class="nodott" style="margin-top: 0px;">comune di</div>
<div class="dott" style="margin-top: 0px;"><%=comuniList.getSelectedValue(indirizzoOperativo.getComune()) %></div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">razza</div>
<div class="dott_long" style="margin-top: 0px;">&nbsp; <%=(razzaList.getSelectedValue(animale.getIdRazza())).toLowerCase() %></div>
<div class="nodott" style="margin-top: 0px;">sesso</div>
<div class="dott_short" style="margin-top: 0px;"><%=animale.getSesso() %></div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">data di nascita</div>
<div class="dott" style="margin-top: 0px;"><%=toDateasString(animale.getDataNascita()) %></div>
<div class="clear1"></div>


<div class="nodott" style="margin-top: 0px;">taglia</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=tagliaList.getSelectedValue(animale.getIdTaglia()) %></div>
<div class="nodott" style="margin-top: 0px;">mantello</div>
<div class="dott" style="margin-top: 0px;"><%=mantelloList.getSelectedValue(animale.getIdTipoMantello()).toLowerCase() %> </div>
<div class="clear1"></div>




<div class="nodott" style="margin-top: 0px;">segni particolari</div>
<div class="dott_long" style="margin-top: 0px;">&nbsp; <%=animale.getSegniParticolari() %> </div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">sterilizzato</div>
<div class="nodott" style="margin-top: 0px;"><%=(animale.isFlagSterilizzazione())? "Si" : "No" %> 
					
</div>

<dhv:evaluate if="<%=(animale.isFlagSterilizzazione()) %>">
<div class="nodott" style="margin-top: 0px;">il</div>
<div class="nodott" style="margin-top: 0px;"><%=(animale.isFlagSterilizzazione()) ? toDateasString(animale.getDataSterilizzazione()) : "" %> 
</div>
</dhv:evaluate>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">nome del cane</div>
<div class="dott" style="margin-top: 0px;">&nbsp; <%=animale.getNome() %> </div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">adottato dal rifugio</div>
<div class="dott_long" style="margin-top: 0px;"><%=vecchioDetentore.getRagioneSociale() %> </div>
<div class="clear1"></div>
<%Stabilimento stabDet = null;
Indirizzo indirizzoOperativoDet= null;

if (vecchioDetentore!=null)
	if (vecchioDetentore.getListaStabilimenti()!=null)
		if (!vecchioDetentore.getListaStabilimenti().isEmpty()){
			stabDet = (Stabilimento) vecchioDetentore.getListaStabilimenti().get(0); 
			indirizzoOperativoDet = stabDet.getSedeOperativa();
		}

%>
<div class="nodott" style="margin-top: 0px;">sito in via</div>
<div class="dott_long" style="margin-top: 0px;"> <%= (indirizzoOperativoDet!= null) ?  indirizzoOperativoDet.getVia() : "" %> </div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">Comune</div>
<div class="dott" style="margin-top: 0px;"><%=(indirizzoOperativoDet!=null) ? comuniList.getSelectedValue(indirizzoOperativoDet.getComune()) : "--" %> </div>
<div class="nodott" style="margin-top: 0px;">Prov</div>
<div class="dott" style="margin-top: 0px;"><%=(indirizzoOperativoDet!=null) ? provinceList.getSelectedValue(indirizzoOperativoDet.getIdProvincia()) : "--"%> </div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">data di adozione</div>
<div class="dott" style="margin-top: 0px;"><%= (indirizzoOperativoDet!=null) ?  toDateasString(dati_registrazione_adozione_canile.getDataAdozione()) : "" %> </div>
<div class="clear1"></div>
</br>
<div class="nodott" style="margin-top: 0px;">Dichiara di essere a 
conoscenza dei seguenti obblighi nei confronti di codesto Servizio: <br>
- denunziare, entro 5 giorni, la morte o lo smarrimento del soggetto; <br>
- denunziare, entro 15 giorni, la variazione della propria residenza o 
il trasferimento di proprietà del cane. <br>
- di sottoporre annualmente il proprio cane a visita clinica ed a 
prelievo ematico per la diagnosi di Leishmaniosi Canina</div>
<div class="clear1"></div>


<div class="nodott" style="margin-top: 0px;">Documento di riconoscimento</div>

<div class="dott" style="margin-top: 0px;">
<dhv:evaluate if="<%=(dati_registrazione_adozione.getIdProprietario() >0) %>">
<%=(proprietario.getRappLegale().getDocumentoIdentita() != null && !("").equals(proprietario.getRappLegale().getDocumentoIdentita())) ? toHtml(proprietario.getRappLegale().getDocumentoIdentita()) : "&nbsp;"%>
</dhv:evaluate>
<dhv:evaluate if="<%=(dati_registrazione_adozione.getIdProprietario() < 0) %>">
<%=(dati_registrazione_adozione.getDocIdentita() != null && !("").equals(dati_registrazione_adozione.getDocIdentita())) ? toHtml(dati_registrazione_adozione.getDocIdentita()) : "&nbsp;" %>
</dhv:evaluate>
</div>

<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">Microchip assegnato</div>
<div class="dott" style="margin-top: 0px;">&nbsp;<%=animale.getMicrochip() %> 
</div>

<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">Si autorizza al trattamento dei dati personali ai sensi del D.lgs 196 del 30 giugno 2003:</div>
<div class="clear1"></div>
<br>
<div class="firma_left">FIRMA</div>
<div class="firmavalore_left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<div class="clear1"></div>
<br/><br/>

<%	
java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); %>
<div class="data">DATA RILASCIO CERTIFICATO</div>

<div class="datavalore"><%=dataToString( timeNow ) %></div>
<br/>
<br><br/><br/>

<div class="data">FIRMA E TIMBRO DEL TITOLARE DEL RIFUGIO</div>

<div class="datavalore"> &nbsp;</div>

<div class="firma">FIRMA DEL NUOVO PROPRIETARIO</div>

<div class="firmavalore">&nbsp;</div>
<br><br><br><br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
</body>

