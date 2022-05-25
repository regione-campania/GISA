<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,java.io.IOException,org.aspcfs.modules.opu.base.*,java.util.Date,com.sun.org.apache.xerces.internal.impl.dv.util.Base64,java.io.ByteArrayOutputStream,javax.imageio.ImageIO"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>

<link rel="stylesheet" type="text/css" media="screen" href="anagrafe_animali/documenti/screen.css">
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css" type="text/css" media="print" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Cane" class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request" />
<jsp:useBean id="Gatto" class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request" />
<jsp:useBean id="animale" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="regioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="trasferimentoFuoriRegione" class="org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriRegione" scope="request" />	
<jsp:useBean id="vecchioProprietario" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />	
<jsp:useBean id="vecchioDetentore" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />	
	
<%
	Animale thisAnimale = null;

	if (Cane.getIdCane() > 0)
		thisAnimale = Cane;
	else if (Gatto.getIdGatto() > 0)
		thisAnimale = Gatto;
	else thisAnimale = animale;

Stabilimento stabVecchioProprietario = null;
Indirizzo sedeOperativaVecchioProprietario = null;
Stabilimento stabVecchioDetentore = null;
Indirizzo sedeOperativaVecchioDetentore = null;

if (vecchioProprietario.getIdOperatore()>0 && vecchioProprietario.getListaStabilimenti()!=null && !vecchioProprietario.getListaStabilimenti().isEmpty())
{
	stabVecchioProprietario = (Stabilimento) vecchioProprietario.getListaStabilimenti().get(0);
	sedeOperativaVecchioProprietario = stabVecchioProprietario.getSedeOperativa();
}
if(vecchioProprietario.getIdOperatore()==vecchioDetentore.getIdOperatore())
{
	stabVecchioDetentore = stabVecchioProprietario;
	sedeOperativaVecchioDetentore = sedeOperativaVecchioProprietario;
}
else if (vecchioDetentore.getIdOperatore()>0 && vecchioDetentore.getListaStabilimenti()!=null && !vecchioDetentore.getListaStabilimenti().isEmpty())
{
	stabVecchioDetentore = (Stabilimento) vecchioDetentore.getListaStabilimenti().get(0);
	sedeOperativaVecchioDetentore = stabVecchioDetentore.getSedeOperativa();
}
%>


<%
String value_microchip="";
if (thisAnimale.getMicrochip()!=null && !thisAnimale.getMicrochip().equals(""))
	value_microchip = thisAnimale.getMicrochip();
else if (thisAnimale.getTatuaggio()!=null && !thisAnimale.getTatuaggio().equals(""))
	value_microchip=thisAnimale.getTatuaggio();
%>
<body>
<input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" />
	
		
	  <jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="idAnimale" value="<%=thisAnimale.getIdAnimale() %>" />
     <jsp:param name="idSpecie" value="<%=thisAnimale.getIdSpecie() %>" />
      <jsp:param name="idTipo" value="PrintCertificatoTrasferimentoFuoriRegione" />
       <jsp:param name="idMicrochip" value="<%=value_microchip %>" />
</jsp:include>
	
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
<br/><br/><br/><br/><br/><br/>
<div class="Section1">
<div class="titleizsm" style="font-weight: bold;">Certificato trasferimento fuori regione</div>


<div class="nodott_margin_low"><b><u>Dati del vecchio proprietario</u></b></div>
<div class="clear1"></div>

<div class="nodott_margin_low">Cognome e nome: </div>
<div class="dott_long_margin_low">&nbsp;<%=(vecchioProprietario.getRagioneSociale() != null) ? vecchioProprietario.getRagioneSociale() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Codice Fiscale:</div>
<div class="dott_margin_low">&nbsp; <%=(vecchioProprietario.getCodFiscale() != null && !("").equals(vecchioProprietario.getCodFiscale()))? vecchioProprietario.getCodFiscale() : "" %></div>
<div class="clear1"></div>

<div class="nodott_margin_low">Data di nascita:</div>
<div class="dott_margin_low">&nbsp;<%=(vecchioProprietario.getRappLegale() != null) ? toDateasString(vecchioProprietario.getRappLegale().getDataNascita()) : ""%>  </div>

<div class="nodott_margin_low">Luogo di nascita:</div>
<div class="dott_margin_low">&nbsp;<%=(vecchioProprietario.getRappLegale() != null) ? vecchioProprietario.getRappLegale().getComuneNascita() : ""%> </div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Comune di residenza:</div>
<div class="dott_long_margin_low" >&nbsp;  <%=(sedeOperativaVecchioProprietario != null) ? comuniList.getSelectedValue((sedeOperativaVecchioProprietario.getComune())) : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Indirizzo:</div>
<div class="dott_long_margin_low" >&nbsp; <%=(sedeOperativaVecchioProprietario != null) ? sedeOperativaVecchioProprietario.getVia() : ""%></div>
<div class="clear1"></div>

</br>


<div class="nodott_margin_low" ><u><b>Dati del vecchio detentore</u></b></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Cognome e nome: </div>
<div class="dott_long_margin_low" >&nbsp; <%=(vecchioDetentore.getRagioneSociale() != null) ? vecchioDetentore.getRagioneSociale() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Codice Fiscale:</div>
<div class="dott_margin_low" >&nbsp; <%=(vecchioDetentore.getCodFiscale() != null && !("").equals(vecchioDetentore.getCodFiscale()))? vecchioDetentore.getCodFiscale() : "" %></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data di nascita:</div>
<div class="dott_margin_low" >&nbsp; <%=(vecchioDetentore.getRappLegale() != null) ? toDateasString(vecchioDetentore.getRappLegale().getDataNascita()) : ""%> </div>
<div class="nodott_margin_low" >Luogo di nascita:</div>
<div class="dott_margin_low" >&nbsp; <%=(vecchioDetentore.getRappLegale() != null) ? vecchioDetentore.getRappLegale().getComuneNascita() : ""%> </div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Comune di residenza:</div>
<div class="dott_long_margin_low" >&nbsp;  <%=(sedeOperativaVecchioDetentore != null) ? comuniList.getSelectedValue((sedeOperativaVecchioDetentore.getComune())) : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Indirizzo:</div>
<div class="dott_long_margin_low" >&nbsp; <%=(sedeOperativaVecchioDetentore != null) ? sedeOperativaVecchioDetentore.getVia() : ""%></div>
<div class="clear1"></div>

</div>

</br>

<div class="nodott_margin_low" ><b><u>Dati del <%=thisAnimale.getNomeSpecieAnimale() %></u></b></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Microchip: </div>
<div class="dott_margin_low" >&nbsp; <%=(thisAnimale.getMicrochip() != null) ? thisAnimale.getMicrochip() : ""%> </div>
<div class="nodott_margin_low" >Tatuaggio:</div>
<div class="dott_margin_low" >&nbsp; <%=(thisAnimale.getTatuaggio() != null) ? thisAnimale.getTatuaggio() : ""%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data prima iscrizione:</div>
<div class="dott_margin_low" >&nbsp; <%=toDateasString(thisAnimale.getDataRegistrazione()) %></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data chippatura:</div>
<div class="dott_margin_low" >&nbsp; <%=(thisAnimale.getDataMicrochip()!=null)?(toDateasString(thisAnimale.getDataMicrochip())):("")%></div>
<div class="clear1"></div>

<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() != Furetto.idSpecie) %>"> <!--  SOLO PER CANI E GATTI -->
<div class="nodott_margin_low" >Razza:</div>
<div class="dott_margin_low" >&nbsp; <%=razzaList.getSelectedValue(thisAnimale.getIdRazza()) %></div>
</dhv:evaluate>
<div class="clear1"></div>

<div class="nodott_margin_low" >Nome:</div>
<div class="dott_margin_low" >&nbsp; <%=thisAnimale.getNome() %></div>

<dhv:evaluate if="<%=(thisAnimale.getIdSpecie() == Cane.idSpecie) %>"> 
<div class="nodott_margin_low" >Taglia:</div>
<div class="dott_margin_low" >&nbsp; <%=tagliaList.getSelectedValue((thisAnimale).getIdTaglia())%> </div>
</dhv:evaluate>

<div class="nodott_margin_low" >Sesso:</div>
<div class="dott_short_margin_low" >&nbsp; <%=(thisAnimale.getSesso() != null) ? thisAnimale.getSesso() : "-" %></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Mantello:</div>
<div class="dott_margin_low" >&nbsp; <%=mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello()) %></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data nascita:</div>
<div class="dott_margin_low" >&nbsp;<%=(thisAnimale.getDataNascita() != null) ? toDateasString(thisAnimale.getDataNascita()) : "-" %></div>
<div class="nodott_margin_low" >Presunta:</div>
<div class="dott_short_margin_low" > &nbsp;<%=(thisAnimale.getFlagDataNascitaPresunta() ) ? "SI" : "NO" %></div>
<div class="clear1"></div>

<br>
</br>

<div class="nodott_margin_low" ><u><b>Dati registrazione</u></b></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Data trasferimento: </div>
<div class="dott_long_margin_low" >&nbsp; <%=toDateasString(trasferimentoFuoriRegione.getDataTrasferimentoFuoriRegione())%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Regione destinazione:</div>
<div class="dott_margin_low" >&nbsp; <%=regioniList.getSelectedValue(trasferimentoFuoriRegione.getIdRegioneA())%></div>
<div class="clear1"></div>

<div class="nodott_margin_low" >Dati nuovo proprietario di nascita:</div>
<div class="dott_margin_low" >&nbsp; <%=(trasferimentoFuoriRegione.getDatiProprietarioFuoriRegione() != null) ? trasferimentoFuoriRegione.getDatiProprietarioFuoriRegione() : ""%> </div>
<div class="clear1"></div>

</div>


<div class="firma">TIMBRO E FIRMA DEL VETERINARIO</div>

<div class="firmavalore">&nbsp;</div>
<br><br>
<font size="1px">
</font>
</div>
</body>

