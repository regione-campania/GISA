<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,org.aspcfs.modules.opu.base.*, java.util.Date"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>
est"/>

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
<div class="title1">RICHIESTA DI ISCRIZIONE ALL'ANAGRAFE CANINA
REGIONALE</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;


<div class="nodott">Il sottoscritto</div>
<div class="dott_long">&nbsp;</div>
<div class="clear1"></div>
<div class="nodott" style="margin-top: 0px;">Codice Fiscale</div>
<div class="dott" style="margin-top: 0px;">&nbsp; </div>
<div class="nodott" style="margin-top: 0px;">Nato a</div>
<div class="dott" style="margin-top: 0px;">&nbsp;</div>
<div class="clear1"></div>


<div class="nodott" style="margin-top: 0px;">il</div>
<div class="dott" style="margin-top: 0px;">&nbsp; </div>
<div class="nodott" style="margin-top: 0px;">e residente in</div>
<div class="dott" style="margin-top: 0px;">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">alla via</div>
<div class="dott" style="margin-top: 0px;">&nbsp; </div>
<div class="nodott" style="margin-top: 0px;">cap</div>
<div class="dott" style="margin-top: 0px;">&nbsp; </div>
<div class="nodott" style="margin-top: 0px;">tel.</div>
<div class="dott" style="margin-top: 0px;">&nbsp;</div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">chiede, giusto quanto
disposto dalla L. 281/91, l'iscrizione all'anagrafe canina del proprio <br>
Cane:</div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">razza</div>
<div class="dott" style="margin-top: 0px;">&nbsp;</div>
<div class="nodott" style="margin-top: 0px;">sesso</div>
<div class="dott" style="margin-top: 0px;">&nbsp;</div>
<div class="clear1"></div>


<div class="nodott" style="margin-top: 0px;">data di nascita</div>
<div class="dott" style="margin-top: 0px;">&nbsp;</div>
<div class="nodott" style="margin-top: 0px;">taglia</div>
<div class="dott" style="margin-top: 0px;">&nbsp; </div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">mantello</div>
<div class="dott" style="margin-top: 0px;">&nbsp; </div>
<div class="clear1"></div>


<div class="nodott" style="margin-top: 0px;">segni particolari</div>
<div class="dott" style="margin-top: 0px;">&nbsp; </div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">sterilizzato</div>
<div class="nodott" style="margin-top: 0px;">&nbsp; NO &nbsp; &nbsp;   SI 
					
</div>


<div class="nodott" style="margin-top: 0px;">il</div>
<div class="nodott" style="margin-top: 0px;">&nbsp; 
</div>

<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">nome del cane</div>
<div class="dott" style="margin-top: 0px;">&nbsp; 
</div>
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

</br>
</br>

<div class="data">DATA</div>
<br>
<div class="datavalore"></div>
<div class="clear1"></div>
<div class="firma">FIRMA</div>
<br>
<div class="firmavalore">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<div class="clear1"></div>
</br>
</br>
<div class="nodott" style="margin-top: 0px;">Documento di riconoscimento</div>
<div class="dott" style="margin-top: 0px;">&nbsp; 
</div>
<div class="clear1"></div>

<div class="nodott" style="margin-top: 0px;">Microchip</div>
<div class="dott" style="margin-top: 0px;">&nbsp; 
</div>
<div class="clear1"></div>
</br>
</br>

<div class="data">DATA</div>
<br>
<div class="datavalore"></div> <br>
<div class="clear1"></div>
<div class="firma">IL VETERINARIO</div>
<br>
<div class="firmavalore">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<div class="clear1"></div>

</div>
<br/>
</body>

