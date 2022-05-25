<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,java.io.IOException,org.aspcfs.modules.opu.base.*,java.util.Date,com.sun.org.apache.xerces.internal.impl.dv.util.Base64,java.io.ByteArrayOutputStream,javax.imageio.ImageIO,org.aspcfs.modules.registrazioniAnimali.base.EventoAggressione"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>

<jsp:useBean id="registro" class="org.aspcfs.modules.anagrafe_animali.base.RegistroUnico" scope="request" />	

<link type="text/css" href="anagrafe_animali/documenti/screen.css" rel="stylesheet" />
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css" type="text/css" media="print" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<body>
<input type="submit" name="stampa" class="buttonClass" onclick="window.print();" value="Stampa" />
	
<jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
      <jsp:param name="idAnimale" value="<%=registro.getIdAnimale() %>" />
      <jsp:param name="idSpecie" value="-1" />
      <jsp:param name="idTipo" value="PrintRegistroUnico" />
      <jsp:param name="idMicrochip" value="<%=registro.getMicrochip() %>" />
</jsp:include>
	
<div class="imgRegione">
	<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/regionecampania.jpg" />
</div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>

<dhv:evaluate if="<%=(registro.getIdAslProprietario()>0) %>"> 
<div class="imgAsl">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="anagrafe_animali/documenti/images/<%=aslList.getSelectedValue(registro.getIdAslProprietario()) %>.jpg" />
</div>
</dhv:evaluate>

<br/>
<br/>
<br/>
<div class="title1">REGISTRO UNICO CANI A RISCHIO ELEVATO DI AGGRESSIVITA' O.M. 06/08/2003 e s.m.i. art 3 comma 3</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;

<div class="clear1"></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Microchip</div>
<div class="dott_long_margin_low">&nbsp;<%=registro.getMicrochip()%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Asl Proprietario</div>
<div class="dott_long_margin_low">&nbsp;<%=registro.getAslProprietario()%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Data ingresso</div>
<div class="dott_margin_low">&nbsp; <%=toDateasString(registro.getDataRegistrazione())%></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Proprietario</div>
<div class="dott_long_margin_low" ><%=registro.getProprietario()%></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Id evento</div>
<div class="dott_long_margin_low" ><%=registro.getIdEvento()%></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Evento</div>
<div class="dott_long_margin_low" ><%=registro.getEvento()%></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Numero provvedimento</div>
<div class="dott_long_margin_low" ><%=registro.getIdCu()%></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Veterinari</div>
<div class="dott_long_margin_low" ><%=registro.getVeterinariEstesi()%></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Misure formative</div>
<div class="dott_long_margin_low" ><%=registro.getMisureFormative()%></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Misure riabilitative</div>
<div class="dott_long_margin_low" ><%=registro.getMisureRiabilitative()%></div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Misure restrittive</div>
<div class="dott_long_margin_low" ><%=registro.getMisureRestrittive()%></div>
<div class="clear1"></div>
	

<div class="nodott_margin_low" >Data rilascio certificato:</div>
<%	
java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); %>
<div class="dott_margin_low" >&nbsp;<%=dataToString( timeNow ) %> </div>



<div class="firma">TIMBRO E FIRMA DEL VETERINARIO</div>

<div class="firmavalore">&nbsp;</div>

</body>

