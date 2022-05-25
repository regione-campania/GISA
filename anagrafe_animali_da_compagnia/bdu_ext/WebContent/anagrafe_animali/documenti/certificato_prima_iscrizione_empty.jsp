<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,java.io.IOException,org.aspcfs.modules.opu.base.*, java.util.Date, com.sun.org.apache.xerces.internal.impl.dv.util.Base64,java.io.ByteArrayOutputStream,javax.imageio.ImageIO"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>

<link rel="stylesheet" type="text/css" media="screen"
	href="anagrafe_animali/documenti/screen.css">
<link rel="stylesheet" href="anagrafe_animali/documenti/print.css"
	type="text/css" media="print" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request" />



<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>
<body>
<input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" />
<div class="imgRegione">
<img style="text-decoration: none;" width="80" height="80" src="anagrafe_animali/documenti/images/regionecampania.jpg" />
</div>
<dhv:evaluate if="<%=(User.getSiteId()>0) %>"> 
<div class="imgAsl">
<img style="text-decoration: none;" width="80" height="80" src="anagrafe_animali/documenti/images/<%=aslList.getSelectedValue(User.getSiteId()) %>.jpg" />
</div>
</dhv:evaluate>
<div class="Section1">
 </br></br>
<div class="title1">CERTIFICATO DI ISCRIZIONE ALL'ANAGRAFE CANINA REGIONALE</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;

<div style="border: 1px solid black;">Dati del proprietario</div>
<div style="border: 1px solid black;">

<div class="nodott_margin_low">Cognome e nome: </div>
<div class="dott_long_margin_low">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low">Data e luogo di nascita:</div>
<div class="dott_margin_low">&nbsp; </div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-top: 10px;">Comune di residenza:</div>
<div class="dott_margin_low" style="margin-top: 10px;">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-top: 10px;">Indirizzo:</div>
<div class="dott_margin_low" style="margin-top: 10px;">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-top: 10px;">Telefono:</div>
<div class="dott_margin_low" style="margin-top: 10px;">&nbsp;</div>
<div class="nodott_margin_low" style="margin-top: 10px;">Cellulare:</div>
<div class="dott_margin_low" style="margin-top: 10px;">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-top: 10px; margin-bottom: 10px;">Codice Fiscale:</div>
<div class="dott_margin_low" style="margin-top: 10px;  margin-bottom: 10px;">&nbsp;</div>
<div class="clear1"></div>
</div>

</br></br>


<div style="border: 1px solid black;">Dati del detentore</div>
<div style="border: 1px solid black;">

<div class="nodott_margin_low" style="margin-top: 10px;">Cognome e nome: </div>
<div class="dott_long_margin_low" style="margin-top: 10px;">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-top: 10px;">Data e luogo di nascita:</div>
<div class="dott_margin_low" style="margin-top: 10px;">&nbsp; </div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-top: 10px;">Comune di residenza:</div>
<div class="dott_margin_low" style="margin-top: 10px;">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-top: 10px;">Indirizzo:</div>
<div class="dott_margin_low" style="margin-top: 10px;">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-top: 10px;">Telefono:</div>
<div class="dott_margin_low" style="margin-top: 10px;">&nbsp;</div>
<div class="nodott_margin_low" style="margin-top: 10px;">Cellulare:</div>
<div class="dott_margin_low" style="margin-top: 10px;">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" style="margin-top: 10px; margin-bottom: 10px;">Codice Fiscale:</div>
<div class="dott_margin_low" style="margin-top: 10px;  margin-bottom: 10px;">&nbsp;</div>
<div class="clear1"></div>
</div>

</br></br>

<div style="border: 1px solid black;">Dati del cane</div>
<div style="border: 1px solid black;">

<div class="nodott_margin_low" >Microchip: </div>
<div class="dott_long_margin_low" >&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data inserimento microchip:</div>
<div class="dott_margin_low" >&nbsp; </div>
<div class="barcode"></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Tatuaggio:</div>
<div class="dott_margin_low" >&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Nome:</div>
<div class="dott_margin_low" >&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Mantello:</div>
<div class="dott_margin_low" >&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Razza:</div>
<div class="dott_margin_low" >&nbsp;</div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Taglia:</div>
<div class="dott_margin_low" >&nbsp;</div>





<div class="clear1"></div>

<div class="nodott_margin_low" >Passaporto:</div>
<div class="dott_margin_low" >&nbsp;</div>
<div class="nodott_margin_low" >Data rilascio passaporto:</div>
<div class="dott_margin_low" >&nbsp;</div>
<div class="clear1"></div>
<div class="nodott_margin_low" >Data nascita:</div>
<div class="dott_margin_low" >&nbsp;</div>
<div class="nodott_margin_low" >Presunta:</div>
<div class="dott_margin_low" style="margin-top: 10px;  margin-bottom: 10px; border-bottom: 0px; width: 80px;">[ ]</div>
<div class="nodott_margin_low" >Sesso:</div>
<div class="dott_margin_low" >&nbsp;</div>
<div class="clear1"></div>
</div>

</br>
</br>

<div class="data">DATA RILASCIO CERTIFICATO</div>
<br>
<div class="datavalore">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<div class="clear1"></div>
<div class="firma">TIMBRO E FIRMA DEL VETERINARIO</div>
<br>
<div class="firmavalore">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<div class="clear1"></div>
</br>


</div>
</body>

