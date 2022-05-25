<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*, org.aspcfs.modules.registrazioniAnimali.base.*" %>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale"%>
<jsp:useBean id="listaAnimali" class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList" scope="request" />
<jsp:useBean id="range_anni"	class="java.lang.String"	scope="request" />
<jsp:useBean id="reload"	class="java.lang.String"	scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script>
function checkAll() 
{
    //alert("Check all the checkboxes..."); 
    var allRows = document.getElementsByTagName("input");
    for (var i=0; i < allRows.length; i++) {
        if (allRows[i].type == 'checkbox') 
        {
            allRows[i].checked = true;
        }
    }

}

function uncheckAll() 
{
    //alert("Check all the checkboxes..."); 
    var allRows = document.getElementsByTagName("input");
    for (var i=0; i < allRows.length; i++) {
        if (allRows[i].type == 'checkbox') 
        {
            allRows[i].checked = false;
        }
    }

}

function controllaCheck() 
{
    //alert("Check all the checkboxes..."); 
    var allRows = document.getElementsByTagName("input");
    for (var i=0; i < allRows.length; i++) {
        if (allRows[i].type == 'checkbox') 
	        if (allRows[i].checked == true)
		        return true;
             
    }
      return false;
}
</script>

<script>
function popUp(url) {
	  title  = '_types';
	  width  =  '1000';
	  height =  '600';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	 window.open(url,'Animale',windowParams);
		  
	}

function conferma() {
	<%String username = User.getUserRecord().getUsername();
	int userid = User.getUserRecord().getId();
	int userasl = User.getUserRecord().getSiteId();%>

//alert("ATTENZIONE! Stai per generare il decesso d'ufficio degli animali selezionati.");
var answer=confirm("ATTENZIONE! Stai per generare il decesso d'ufficio degli animali selezionati. \n\nSei sicuro di voler procedere?");
if (answer==true)
  {
    return true;
  }
else
  {
    return false;
  }
}
</script>



<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="AnimaleAction.do"><dhv:label
			name="">Anagrafe animali</dhv:label></a> > <dhv:label name="">Gestisci decessi</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>



<form name="searchAnimale"
	action="AnimaleAction.do?command=GestisciDecessiUfficio" onSubmit=""
	method="post"><input type="hidden" name="doContinue"
	id="doContinue" value="" />
<input type="hidden" name="rangeAnni" id="listaSize" value="<%=range_anni%>"></input>
<input type="hidden" name="listaSize" id="listaSize" value="<%=listaAnimali.size()%>"></input>

<%if (reload.equals("")){ %>
<label>Sono stati trovati <strong><%=listaAnimali.size() %></strong> animali nati da più di <strong><%=range_anni %></strong> anni.</label>
<dhv:evaluate if="<%=(listaAnimali.size()>0) %>">
<input type="button" name="CheckAll" value="Seleziona tutti" onClick="checkAll()">
<input type="button" name="UnCheckAll" value="Deseleziona tutti" onClick="uncheckAll()">
<br></br>
<input type="button" value="PROCEDI"	onClick="if(!controllaCheck()){alert('Attenzione! Selezionare almeno un animale!')} else if (conferma()){loadModalWindow();this.form.submit()};" /><br></br>
</dhv:evaluate>
<%} %>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
				<th><strong>Specie</strong></th>
				<th><strong>Nome</strong></th>
				<th><strong>Microchip/Tatuaggio</strong></th>
				<th><strong>Data di nascita</strong></th>
				<th><strong>Stato</strong></th>
				<th><strong>Decesso d'ufficio</strong></th>
		</tr>
	
<%for (int i=0; i<listaAnimali.size();i++){%>			
			<tr>
			<%Animale an = (Animale)listaAnimali.get(i); %>
			<input type="hidden" name="id_<%=i %>" id="id_<%=i %>" value="<%=an.getIdAnimale()%>"></input> 
			<td><%=an.getNomeSpecieAnimale() %><input type="hidden" name="specie_<%=i %>" id="specie_<%=i %>" value="<%=an.getIdSpecie()%>"></input>
			<input type="hidden" name="stato_<%=i%>" id="stato_<%=i%>" value="<%=an.getStato()%>"></input>
			<input type="hidden" name="tatuaggio_<%=i%>" id="tatuaggio_<%=i%>" value="<%=an.getTatuaggio()%>"></input>
			<input type="hidden" name="nome_<%=i%>" id="nome_<%=i%>" value="<%=an.getNome()%>"></input>
			<input type="hidden" name="dataNascita_<%=i%>" id="dataNascita_<%=i%>" value="<%=toDateString(an.getDataNascita())%>"></input>
			</td> 
			<td><%=an.getNome()  %> &nbsp;</td>
			<%String identificativo="-";
			if (an.getMicrochip()!=null && !an.getMicrochip().equals(""))
			identificativo = an.getMicrochip();
			else if (an.getTatuaggio()!=null && !an.getTatuaggio().equals(""))
			identificativo=an.getTatuaggio();%>
			<td><a orderInfo="<%=toHtml(identificativo)%>"
					href="javascript:popUp('AnimaleAction.do?command=Details&animaleId=<%=an.getIdAnimale()%>&idSpecie=<%=an.getIdSpecie()%><%=addLinkParams(request,
										"popup|popupType|actionId")%>')"><%=toHtml(identificativo)%></a> <input type="hidden" name="microchip_<%=i %>" id="microchip_<%=i %>" value="<%=identificativo%>"></input></td> 
			<td><%=toDateString(an.getDataNascita()) %></td> 
			<td><%=toHtml(statoList.getSelectedValue(an.getStato()))%></td> 
			<td>
			<dhv:evaluate if="<%=(!an.getFlagDecesso()) %>">
			<input type="checkbox" name="decesso_<%=i %>" id="decesso_<%=i %>" value="OK"></dhv:evaluate>
			<dhv:evaluate if="<%=(an.getFlagDecesso()) %>">
			Deceduto d'ufficio.</dhv:evaluate>
			
			
			
			<br></td> 
			</tr>
	<%} %>
	
		</table>
<%if (reload.equals("")){ %>
<dhv:evaluate if="<%=(listaAnimali.size()>0) %>">
<label>Sono stati trovati <strong><%=listaAnimali.size() %></strong> animali nati da più di <strong><%=range_anni %></strong> anni.</label>
<input type="button" value="PROCEDI"	onClick="if(!controllaCheck()){alert('Attenzione! Selezionare almeno un animale!')} else if (conferma()){loadModalWindow();this.form.submit()};" />
<input type="button" name="CheckAll" value="Seleziona tutti" onClick="checkAll()">
<input type="button" name="UnCheckAll" value="Deseleziona tutti" onClick="uncheckAll()">
</dhv:evaluate>
<%} %>
</form>

<!-- 
<input type="submit"
	value="<dhv:label name="button.submit">Conferma</dhv:label>"> </form>-->







