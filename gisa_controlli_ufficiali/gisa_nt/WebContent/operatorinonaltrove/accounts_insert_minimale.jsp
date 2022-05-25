<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.operatorinonaltrove.base.Organization" scope="request"/>

<%! public static String fixString(String nome)
  {
	  String toRet = "";
	  if (nome == null)
		  return nome;
	  
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll("\"", "");
	  return toRet;
	  
  }%>
  
<script>

  function setDecrizioneImpresa(idimpresa,descrizioneimpresa,idTipologia)
  {
  if (window.opener!=null)
  	{
	  var orgIdOriginale = -1;
	  if (window.opener.document.getElementsByName("orgId")!=null && window.opener.document.getElementsByName("orgId")[0]!=null)
		  orgIdOriginale = window.opener.document.getElementsByName("orgId")[0].value;
	  
	  if (window.opener.document.getElementsByName("idStabilimento")!=null && window.opener.document.getElementsByName("idStabilimento")[0]!=null)
		  orgIdOriginale = window.opener.document.getElementsByName("idStabilimento")[0].value;
	  
	  
	 if (idimpresa==orgIdOriginale){
		 alert("Impossibile selezionare lo stesso operatore per la non conf. a carico di terzi!");
	 }
	 else {
  		window.opener.document.getElementById("id_impresa_sanzionata").value=idimpresa;
  		window.opener.document.getElementById("id_tipologia_operatore").value=idTipologia;

	  	window.opener.document.getElementById("descrizione_impresa_sanzionata").value=descrizioneimpresa;
  		window.opener.document.getElementById("descrizione_impresa_sanzionata").readOnly="true";
  		window.opener.document.getElementById("descrizione_impresa_sanzionata").style.display="";
  		
  		if (window.opener.document.getElementById("ncterzi_prov_gravi")!=null)
  			window.opener.document.getElementById("ncterzi_prov_gravi").style.display="block";
	  	
	  	window.close();
  	}
  	}
  	
  }
  </script>


Operatore inserito in GISA. <br/>
Operatore impostato come soggetto ispezionato della non conformità.<br/>



<script>
alert('Operatore <%=fixString(OrgDetails.getName())%> inserito in GISA ed impostato come soggetto ispezionato.');
setDecrizioneImpresa(<%=OrgDetails.getOrgId() %>,'<%=fixString(OrgDetails.getName()) %>',<%=OrgDetails.getTipologia()%>);
</script>

