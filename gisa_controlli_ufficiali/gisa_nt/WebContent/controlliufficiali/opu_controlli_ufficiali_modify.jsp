<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcf.modules.controlliufficiali.base.Piano"%>
<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<jsp:useBean id="OggettoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieA" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="VerificaQuantitativo" class="org.aspcfs.utils.web.LookupList"
	scope="request" />	
	<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<jsp:useBean id="Condizionalita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="BufferDetails" class="org.aspcfs.modules.buffer.base.Buffer" scope="request" />
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript" src="dwr/interface/NucleoIspettivo.js"> </script>

        <script type="text/javascript">
        
        
        function giorni_differenza(data1,data2){
        	
        	anno1 = parseInt(data1.substr(6),10);
        	mese1 = parseInt(data1.substr(3, 2),10);
        	giorno1 = parseInt(data1.substr(0, 2),10);
        	anno2 = parseInt(data2.substr(6),10);
        	mese2 = parseInt(data2.substr(3, 2),10);
        	giorno2 = parseInt(data2.substr(0, 2),10);

        	var dataok1=new Date(anno1, mese1-1, giorno1);
        	var dataok2=new Date(anno2, mese2-1, giorno2);

        	differenza = dataok2-dataok1;    
        	giorni_diff = new String(differenza/86400000);
        	//alert('diff');
        	//alert(giorni_diff);
        	return giorni_diff;
        }

        function setuo(campo){
        	if(campo.id=='uo1')
        	{
        	i = 1 ;
        	
        	while (document.getElementById('uo'+i)!=null)
        	{
        		document.getElementById('uo'+i).value = document.getElementById('uo1').value ;
        		i++;
        	}
        	}
        		
        }

        
        
        var isPianomonitoraggio = false ; 
        var isSorveglianza = false ;
        function controlloCuSorveglianza()
        {

        	entratoinpiano = false ;
        	isPianomonitoraggio = false ;
        	isSorveglianza = false ; 
        if (document.details.assignedDate.value == '')
        	return checkForm(document.details);
        
        
        var dateInizioControllo = document.details.assignedDate;
        var dateFineControllo = document.details.dataFineControllo;
        var dateInsert = '<%=TicketDetails.getEntered()%>'.substring(0, 10);
        dateInsert = dateInsert.split("-")[2]+"/"+dateInsert.split("-")[1]+"/"+dateInsert.split("-")[0];
        if(dateInizioControllo!=null && dateFineControllo!=null)
        {
        	if (dateInizioControllo.value == '')
        	{
        		alert('Valorizzare la data inizio controllo');
        		return false;
        	}
        	if (dateFineControllo.value == '')
        	{
        		alert('Valorizzare la data fine controllo');
        		return false;
        	}
        	
        	if(giorni_differenza(dateFineControllo.value,dateInizioControllo.value)>0)
        	{
        		alert('La data inizio controllo è successiva alla data di fine controllo');
        		return false;
        	}
        	
        	if(giorni_differenza(dateInsert,dateInizioControllo.value)>0)
        	{
        		alert('La data inizio controllo è successiva alla data di inserimento cu (' + dateInsert + ')');
        		return false;
        	}
        
        }
        
        loadModalWindow();
        	
        	if (document.details.orgId!=null)
        		orgId = document.details.orgId.value;
        	else
        		if (document.details.idStabilimentoopu!=null)
        			orgId = document.details.idStabilimentoopu.value; 
        		else
        			orgId = document.details.idApiario.value; 
        assetId = -1 ;
        if (document.details.assetId!=null)
        {
        	assetId = document.details.assetId.value;
        }


        tipoIspezione = document.getElementsByName('tipoIspezione');
        for (i=0 ; i<tipoIspezione.length;i++)
        {

        	getCodiceInternoTipoIspezione(tipoIspezione[i].value.split(";")[0]);
        	if (codiceInternoTipoIspezione == '2a' && entratoinpiano == false)
        		{
        			isPianomonitoraggio = true ;
        			entratoinpiano=true ;
        		
        		}


        }
        if (document.details.tipoCampione.value == '5')
        {
        	
        	isSorveglianza = true ;
        }
        PopolaCombo.controlloInserimentoCuSorveglianza(orgId, isPianomonitoraggio,isSorveglianza,document.details.assignedDate.value,assetId, {callback:viewMessageCallback1,async:false } );
        return formTest;
        }

       
        function mostraStrutturaAsl()
        {
        tipoIspezione = document.getElementById('tipoCampione').value ;
        select=document.getElementById('uo') ;

        if (tipoIspezione == '4' || tipoIspezione == '3')
        {
        	document.getElementById('per_conto_di').style.display="none";
        	
        	
        	
        	

        }
        else
        {
        	document.getElementById('per_conto_di').style.display="";

        	

        	
        }
        }

        function viewMessageCallback1(returnValue) {
        messaggio1 = returnValue[0];
        messaggio2 = returnValue[1];
        messaggio3 = returnValue[2];
        messaggio4 = returnValue[3];

        flag = true ;

        if (messaggio1 != null && messaggio1 != "") 
        {
        	if (document.details.tipoCampione.value == '5') 
        	{
        		alert('ATTENZIONE : non ï¿½ possibile inserire un nuovo controllo in Sorveglianza. Esistono controlli ufficiali in sorveglianza ancora aperti.Chiudere prima i seguenti controlli: \n' + messaggio1);
        		flag = false;
        	}
        }	/*else
        	{
        			alert('ATTENZIONE : non ï¿½ possibile inserire un nuovo controllo ' + messaggio1);
        			return false;
        	}
        }
        else
        {*/
        	
        	if (messaggio4!='')
        	{
        		
        		flag=false;
        	}
        	if(messaggio2!="" || (messaggio3!="" && document.details.data_iniziale.value!=document.details.assignedDate.value ))
        	{
        		/**
        		 * SE DATA INIZIO CONTROLLO ï¿½ ANTECEDENTE ALLA DATA PROSSIMO CONTROLLO - 30 GG
        		 * IL SISTEMA GENERERï¿½ UN MESSAGGIO NON BLOCCANTE.
        		 * (VALE SOLO PER I CONTROLLI IN SORVEGLIANZA)
        		 */
        	if (messaggio2 != null && messaggio2 != "") 
        	{
        		
        		if (flag == true)
        			alert('ATTENZIONE! Hai effettuato una ispezione in sorveglianza in una data precedente a quella stabilita dai criteri di programmazione');
        		flag= true;
        	}
        	/**
        	 * SE DATA INIZIO CONTROLLO ï¿½ INFERIORE ALLA DATA ATTUALE -30 GIORNI IL SISTEMA SEGNALA UN
        	 * MESSAGGIO NON BLOCCANTE (IL MESSAGGIO SARA BLOCCANTE A PARTIRE DA GENNAIO 2012)
        	 * VALE PER TUTTI I TIPI DI CONTROLLO
        	 */
        	if (messaggio3 != null && messaggio3 != "" ) 
        	{
            	
        		if (flag == true && (document.details.data_iniziale.value!=document.details.assignedDate.value))
        		{
        			if (isPianomonitoraggio==false)
        				alert('ATTENZIONE.Stai inserendo i dati di un controllo ufficiale effettuato oltre 30 gg fa. Il sistema impedisce questo tipo di operazioni per motivi di congruenza dei dati.' );
        			else
        			{
        				alert('ATTENZIONE.Stai inserendo i dati di un controllo ufficiale effettuato oltre 15 gg fa. Il sistema impedisce questo tipo di operazioni per motivi di congruenza dei dati.' );
        			}
        		}

        		
        		if(document.getElementById("cu_pregresso")==null || (document.getElementById("cu_pregresso")!= null && document.getElementById("cu_pregresso").checked == false))
        			flag = false;
        		else
        			if(document.getElementById("cu_pregresso").checked==true)
        				flag = true;
        	} 
        	}
        	else
        		{
        		
        			if (flag == true)
        				return checkForm(document.details);
        		}
        if (flag == true)
        {
            
        	
        return checkForm(document.details);
        }
        }


			</script>
			
			<%TipoIspezione.setMultiple(true);
			TipoAudit.setMultiple(true);
			TipoIspezione.setSelectSize(5);
			TipoAudit.setSelectSize(5); %>
	<tr>
      <th colspan="2">
        <strong><dhv:label name="Campioni.information">Informazioni Controllo Ufficiale</dhv:label></strong>
      </th>
	</tr>
	 <tr class="containerBody">
      <td nowrap class="formLabel">
       Operatore
      </td>
      <td><%=OrgDetails.getOperatore().getRagioneSociale() %> </td>
    </tr>
	
	 <dhv:include name="stabilimenti-sites" none="true">

    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
  
    	  <%=SiteIdList.getSelectedValue(TicketDetails.getSiteId())%>
          <input type="hidden" name="siteId"  id="siteId" value="<%=TicketDetails.getSiteId()%>" >
    	 
    	  
    	  
       
      
      </td>
    </tr>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>

    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="idStabilimentoopu" value="<%= TicketDetails.getIdStabilimento() %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">

  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(TicketDetails.getPaddedId()) %>
      </td>
    
  </tr>
  
  
      <%@ include file="controlli_ufficiali_modify_tipo.jsp" %>
   




<%

if (request.getAttribute("ViewLdA")!=null)
{
	if (request.getAttribute("LineaSelezionata")!=null)
	{
	LookupList linee_selezionate = (LookupList)request.getAttribute("LineaSelezionata");

	if(linee_selezionate.size()>0)
	{
          LookupList lookup_vuota_linea_attivita = new LookupList();
      	lookup_vuota_linea_attivita.addItem(-1, "" );
          
          %>
  <div style = "display:none">
		<tr  id="rigaATECO">
          <td nowrap class="formLabel">
            Linea Attivita' Sottoposta a Controllo
                        
            
          </td>
          <td>
                <%  lookup_vuota_linea_attivita.setJsEvent("onChange=\"controllaCampiAggiuntiviLinea(this.value, "+TicketDetails.getId()+")\"");%>
          		<%= lookup_vuota_linea_attivita.getHtmlSelect("id_linea_sottoposta_a_controllo" , linee_selezionate ) %> 		
	<label id = "lab_linea"></label>
          </td>
	</tr>
	
	<tr id="LuogoControllo">
	<td nowrap class="formLabel"></td>
    <td></td></tr>
    
		</div>
  <%}
	else
	{
		%>
		<input type = "hidden" name = "id_linea_sottoposta_a_controllo" value = "-1">
		<%
		
	}
	
	}}
else
{
	if (request.getAttribute("ViewLdAStab")!=null && request.getAttribute("isOperatoreIttico")!= null && ((String)request.getAttribute("isOperatoreIttico")).equals("no"))
	{
		if (request.getAttribute("linee_attivita_stabilimenti_desc")!=null)
		{
		ArrayList<String> lista_linee_desc = (ArrayList<String>)request.getAttribute("linee_attivita_stabilimenti_desc");

		ArrayList<String> lista_linee = (ArrayList<String>)request.getAttribute("linee_attivita_stabilimenti");
		%>
		 <div style = "display:none">
		<tr  id="rigaATECO">
          <td nowrap class="formLabel">
            Linea Attivita' Sottoposta a Controllo
                        
            
          </td>
          <td>
         	<input type = "hidden" name = "num_linee" id = "num_linee" value = "<%=lista_linee.size() %>"/>
          	   <input type = "hidden" name = "tipo_selezione" id = "tipo_selezione" value = "false"/>
		       <table class = "noborder">
		       <tr id = "la_stab_soa" style="display: none">
		       <td>
		       <input type = "text" readonly="readonly"  id ="codici_selezionabili" size = "80"  name = "codici_selezionabili" title="Qualora siano state controllate piï¿½ linee attivitï¿½ occorre inserire controlli ufficiali (uno per ogni linea attivitï¿½).">
		       <br>
		         <input type = "text" name = "alertText" id = "alertText"  readonly="readonly" size="80" title="Qualora siano state controllate piï¿½ linee attivitï¿½ occorre inserire controlli ufficiali (uno per ogni linea attivitï¿½).">
	
		       </td>
		       </tr>
		       <%
		       int count  ;
		       for (int i = 0 ;i < lista_linee.size();i++)
		    	   {count = i+1 ;
		    	   %>
		    	   <tr id = "la_stab_soa<%=count %>" >
		       <td>
		       <input type = "text" readonly="readonly" value = "<%=lista_linee.get(i) %>" id ="codici_selezionabili" size = "80"  name = "codici_selezionabili" title="Qualora siano state controllate piï¿½ linee attivitï¿½ occorre inserire controlli ufficiali (uno per ogni linea attivitï¿½).">
		       <br>
		         <input type = "text" name = "alertText" id = "alertText" value = "<%=lista_linee_desc.get(i)%>" readonly="readonly" size="80" title="Qualora siano state controllate piï¿½ linee attivitï¿½ occorre inserire controlli ufficiali (uno per ogni linea attivitï¿½).">
	
		       </td>
		       </tr>
		    	   <%
		    	   
		    	   }%>
		       
		       </table>
		          <a id = "link_seleziona" href = "javascript:popLookupSelectorCustomStabilimentiCU('codici_selezionabili','alertText','lookup_codistat','','<%=OrgDetails.getIdStabilimento() %>',document.getElementById('tipo_selezione').value);"><label id = "lab_linea">Seleziona Una Voce</label></a>
         	<font color = "red">*</font>
		     
	
          </td>
	</tr>

		</div>
		<%
		}
	}else
		if (request.getAttribute("ViewLdASoa")!=null)
		{
			if (request.getAttribute("linee_attivita_stabilimenti_desc")!=null)
			{
			ArrayList<String> lista_linee_desc = (ArrayList<String>)request.getAttribute("linee_attivita_stabilimenti_desc");

			ArrayList<String> lista_linee = (ArrayList<String>)request.getAttribute("linee_attivita_stabilimenti");
			%>
			 <div style = "display:none">
			<tr  id="rigaATECO">
	          <td nowrap class="formLabel">
	            Linea Attivita'' Sottoposta a Controllo
	                        <label id = "lab_linea"></label>
	            
	          </td>
	          <td>
	         	<input type = "hidden" name = "num_linee" id = "num_linee" value = "<%=lista_linee.size() %>"/>
	          	   <input type = "hidden" name = "tipo_selezione" id = "tipo_selezione" value = "false"/>
			       <table class = "noborder">
			       <tr id = "la_stab_soa" style="display: none">
			       <td>
			       <input type = "text" readonly="readonly"  id ="codici_selezionabili" size = "80"  name = "codici_selezionabili" title="Qualora siano state controllate piï¿½ linee attivitï¿½ occorre inserire controlli ufficiali (uno per ogni linea attivitï¿½).">
			       <br>
			         <input type = "text" name = "alertText" id = "alertText"  readonly="readonly" size="80" title="Qualora siano state controllate piï¿½ linee attivitï¿½ occorre inserire controlli ufficiali (uno per ogni linea attivitï¿½).">
		
			       </td>
			       </tr>
			       <%
			       int count  ;
			       for (int i = 0 ;i < lista_linee.size();i++)
			    	   {count = i+1 ;
			    	   %>
			    	   <tr id = "la_stab_soa<%=count %>" >
			       <td>
			       <input type = "text" readonly="readonly" value = "<%=lista_linee.get(i) %>" id ="codici_selezionabili" size = "80"  name = "codici_selezionabili" title="Qualora siano state controllate piï¿½ linee attivitï¿½ occorre inserire controlli ufficiali (uno per ogni linea attivitï¿½).">
			       <br>
			         <input type = "text" name = "alertText" id = "alertText" value = "<%=lista_linee_desc.get(i)%>" readonly="readonly" size="80" title="Qualora siano state controllate piï¿½ linee attivitï¿½ occorre inserire controlli ufficiali (uno per ogni linea attivitï¿½).">
		
			       </td>
			       </tr>
			    	   <%
			    	   
			    	   }%>
			       
			       </table>
			          <a id = "link_seleziona" href = "javascript:popLookupSelectorCustomSOACU('codici_selezionabili','alertText','lookup_codistat','','<%=OrgDetails.getIdStabilimento() %>',document.getElementById('tipo_selezione').value);"><label id = "lab_linea">Seleziona Una Voce</label></a>
	         	<font color = "red">*</font>
			     
		
	          </td>
		</tr>

			</div>
			<%
		
}
}
}
  %>
   
  


 
 
 
 	<tr id = "per_conto_di" >
 	<td class="formLabel">Per Conto Di</td>
 	<td>
			<table class="noborder" id ="listaStruttureMultiple">
			<tr><td>
			<a href="#" onclick="view_uo_attivita_multiplo()">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
			</td></tr>
			<tr><td> <input type = "hidden" name = "uo_controllo" id = "uo_controllo" value = "-1"></td></tr>
			<%
			for(OiaNodo uo : TicketDetails.getLista_unita_operative() )
			{
				%>
							<tr><td> <input type = "hidden" name = "uo_controllo" id = "uo_controllo" value = "<%=uo.getId()%>"><%=uo.getDescrizione_lunga() %></td></tr>
				
				<%
			}
			%>
			
			
			</table>
			</td></tr>

<!-- <tr id = "per_conto_di" > -->
<!-- <td class = "formLabel">Per Conto di</td> -->
<!-- <td> -->
<!-- <a href="#" onclick="javascript:view_uo_attivita_multiplo()">Seleziona Per conto Di</a> -->

<!-- </td> -->
<!-- </tr> -->

  
   <%@ include file="controlli_ufficiali_modify_info.jsp" %>

   
 <input type="hidden" id="tipoOperatore" name="tipoOperatore" value="<%=request.getAttribute("tipologia") %>"/>
 