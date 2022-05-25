<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.sql.Timestamp"%>
<%@page import="org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.GregorianCalendar, java.util.Calendar"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="dsc" class="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcolo" scope="request" />
<jsp:useBean id="dpatAtt" class="org.aspcfs.modules.dpat2019.base.Dpat" scope="request" />
<jsp:useBean id="idAsl" class="java.lang.String" scope="request" />
<jsp:useBean id="anno" class="java.lang.String" scope="request" />
<jsp:useBean id="ListaAsl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaAllegati" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList" scope="request"/>
<jsp:useBean id="ListaStruttureDirezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<dhv:container name="dpat2019" selected="details" object="" param="<%="idAsl="+dsc.getStrutturaAmbito().getId_asl()+"&combo_area="+dsc.getStrutturaAmbito().getId()+"&anno="+dsc.getAnno() %>"  hideContainer="false">


	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="dpat2019.do">DPAT</a> &gt Allegati DPAT - ASL : <%=ListaAsl.getSelectedValue(idAsl) %> > <%=dsc.getStrutturaAmbito() !=null && dsc.getStrutturaAmbito().getId()>0 ?dsc.getStrutturaAmbito().getDescrizione_lunga() :"" %>
			</td>
		</tr>
	</table>
	
<%  String edit = ""; 
	if (dsc.isCompleto()==true) {
		edit = "view";
	} else {
		edit = "edit";
	}
%>

<script>

function downloadModello(nomeClient,tipoAllegato,headerDoc,idDocumento)
{
	
	if (idDocumento!=-1)
		{
		
		location.href='GestioneAllegatiUpload.do?command=DownloadPDF&codDocumento='+headerDoc+'&idDocumento'+idDocumento+'&tipoDocumento='+tipoAllegato+'&nomeDocumento='+nomeClient;
		}
	else
		alert('Il modello selezionato non è ancora disponibile');
}
</script>


  <%! public static String fixStringa(String nome)
  {
	  String toRet = nome;
	  if (nome == null || nome.equals("null"))
		  return toRet;
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll(" ", "_");
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>
  
 
  
  <br>
  <br>
  <br>
  <center>
  
  <script>
  
  function ricaricaDpat(idAsl,anno)
  {
	  loadModalWindowCustom("Caricamento In corso");
	  var idStrutturaSelezionata = document.getElementById("combo_area").value;
	  location.href="dpat2019.do?command=Home&idAsl="+idAsl+"&anno="+anno+"&combo_area="+idStrutturaSelezionata;
  }
  
  </script>
  <%
  
  if(dsc.getStrutturaAmbito()!=null && dsc.getStrutturaAmbito().getId()>0)
  {
		  ListaStruttureDirezione.setJsEvent("onchange='ricaricaDpat("+dsc.getStrutturaAmbito().getIdAsl()+","+dsc.getStrutturaAmbito().getAnno()+ ")'");
  }
  %>
  
  
  <h3>
  <%=((dsc.getStrutturaAmbito()!=null && dsc.getStrutturaAmbito().getId()>0))? "DPAT - ANNO: "+dsc.getStrutturaAmbito().getAnno() : "DPAT - ANNO: "+dsc.getAnno()  %>
  </h3>
  <fieldset>
  <legend><h3><%=((dsc.getStrutturaAmbito()!=null && dsc.getStrutturaAmbito().getId()>0) ? "STRUTTURA SELEZIONATA :"+ dsc.getStrutturaAmbito().getDescrizione_lunga().toUpperCase() : "")  %></h3></legend>
        
        

        <dhv:permission name="modello4-view">
        <table>
                <tr>
                <td>
                <input type="button" class="greenBigButton" title="STRUMENTO DI CALCOLO (EX ALLEGATO 5 - I FOGLIO) COMPILATO DALL'ASL"   style="width: 400px !important;"  id="A5" value="ORGANIGRAMMA <%=(dsc.getStrutturaAmbito().getStato()==2) ? " (CHIUSO) " : "" %>" onClick="" style="background-color:#FF4D00; font-weight: bold;"/>

	<dhv:permission name="dpat-view">
	<script>
		document.getElementById("A5").onclick=function(){window.location='DpatSDC2019.do?command=Details&idAsl=<%=idAsl%>&anno=<%=anno%>&combo_area=<%=dsc.getIdStrutturaAreaSelezionata()%>';}
	</script>
	</dhv:permission>
	<dhv:permission name="dpat-edit">
	<script>
	<%
	Calendar calCorrente = GregorianCalendar.getInstance();
	Date dataCorrente = new Date(System.currentTimeMillis());
	int tolleranzaGiorni = Integer.parseInt(ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
	dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
	calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
	%>
		var annoCorrente = <%=calCorrente.get(Calendar.YEAR)%>;
		var annoInput = <%=anno%>;
		if (annoCorrente==annoInput || annoInput>annoCorrente){
			document.getElementById("A5").onclick=function(){window.location='DpatSDC2019.do?command=AddModify&idAsl=<%=idAsl%>&anno=<%=anno%>&combo_area=<%=dsc.getIdStrutturaAreaSelezionata()%>';}
		}
		else {
			document.getElementById("A5").onclick=function(){window.location='DpatSDC2019.do?command=Details&idAsl=<%=idAsl%>&anno=<%=anno%>&combo_area=<%=dsc.getIdStrutturaAreaSelezionata()%>';}
		}
	</script>
	</dhv:permission>
	
                </td></tr>
     </table>
     </dhv:permission>
                
                
          
           </fieldset>    
  </center>





	




	<dhv:permission name="dpat-view">
	<!-- SERVER DOCUMENTALE -->
<p align="left">
	 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
		
	  
</p>
 <!-- SERVER DOCUMENTALE -->
 </dhv:permission>
</dhv:container>
 
</body>
</html>