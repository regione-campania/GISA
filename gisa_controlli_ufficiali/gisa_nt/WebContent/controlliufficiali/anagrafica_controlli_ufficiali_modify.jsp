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
<jsp:useBean id="BufferDetails" class="org.aspcfs.modules.buffer.base.Buffer" scope="request" />

<jsp:useBean id="Condizionalita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<script type="text/javascript" src="dwr/interface/NucleoIspettivo.js"> </script>

        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript">

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
        	loadModalWindow();
        	isPianomonitoraggio = false ;
        	isSorveglianza = false ; 
        	
        if (document.details.assignedDate.value == '')
        	return checkFormModify(document.details);
        altId = document.details.altId.value;
        assetId = -1 ;
        if (document.details.assetId!=null)
        {
        	assetId = document.details.assetId.value;
        }
	  tipoIspezione = document.getElementsByName("tipoIspezione");
for (i=0 ; i<tipoIspezione.length;i++)
{
	

	getCodiceInternoTipoIspezione(tipoIspezione[i].value);
	if(codiceInternoTipoIspezione == '2a')
		{
	isPianomonitoraggio = true ;
		}



}


if (document.details.tipoCampione.value == '5')
{
	isSorveglianza = true ;
}	


PopolaCombo.controlloInserimentoCuSorveglianza(altId, isPianomonitoraggio,isSorveglianza,document.details.assignedDate.value,assetId, {callback:viewMessageCallback1,async:false});

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
            //Il messaggio2 non deve essere considerato in fase di modifica
            messaggio2 = "";
            messaggio3 = returnValue[2];
            messaggio4 = returnValue[3];
            messaggio5 = returnValue[4];
            messaggio6 = returnValue[5];

            role = document.getElementById("role_user").value;
            flag = true ;

         
            	if (messaggio4!='')
            	{
            		alert(messaggio4);
            		flag=false;
            	}
            	if(messaggio2!="" || messaggio3!="" || messaggio5!="")
            	{
            		
            		/**
            		 * SE DATA INIZIO CONTROLLO è ANTECEDENTE ALLA DATA PROSSIMO CONTROLLO - 30 GG
            		 * IL SISTEMA GENERERà UN MESSAGGIO NON BLOCCANTE.
            		 * (VALE SOLO PER I CONTROLLI IN SORVEGLIANZA)
            		 */
            	
            	/**
            	 * SE DATA INIZIO CONTROLLO è INFERIORE ALLA DATA ATTUALE -30 GIORNI IL SISTEMA SEGNALA UN
            	 * MESSAGGIO NON BLOCCANTE (IL MESSAGGIO SARA BLOCCANTE A PARTIRE DA GENNAIO 2012)
            	 * VALE PER TUTTI I TIPI DI CONTROLLO
            	 */
            	 
            	//messaggio3 è legato all'inserimento dei controlli per l'anno 2014...
            	//messaggio5 è legato all'inserimento dei controlli per l'anno 2013...
            	if ((messaggio3 != null && messaggio3 != "" ) ) 
            	{
            		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
            		if (flag == true)
            		{
            			if (isPianomonitoraggio==false)
            			{

            				alert(messaggio3 );

            			}
            			else
            			{
            				alert(messaggio3 );

            			}
            		}
            	}
            	if ((messaggio5 != null && messaggio5 != "" ) ) 
            	{
            		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
            		if (flag == true)
            		{
            			if (isPianomonitoraggio==false)
            			{

            				alert(messaggio5 );

            			}
            			else
            			{
            				alert(messaggio5 );

            			}
            		}
            	}
            	

            	if ((messaggio2 != null && messaggio2 != "" && isSorveglianza==true) ) 
            	    	{
            	    		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
            	    		if (flag == true)
            	    		{
            	    			

            	    				alert('Attenzione non è possibile inserire Controlli in Sorveglianza Ravvicinati. Controllo inseribile a partire dal'+messaggio2 );

            	    			    		}
            	    	}

            		if(document.getElementById("cu_pregresso")==null || (document.getElementById("cu_pregresso")!= null && document.getElementById("cu_pregresso").checked == false))
            			flag = false;
            		else
            			if(document.getElementById("cu_pregresso").checked==true && messaggio5 =='')
            				{
            				flag = true;
            				}
            				else
            				{
            				if(document.getElementById("cu_pregresso").checked==true && messaggio5 !=''){
            					flag = false;
            				}
            				//pregresso 2013 di nurecu non per autorità competenti
            				if(document.getElementById("cu_pregresso").checked==true && messaggio5 !='' ){
            					flag = false;
            				}
            					
            				}

            	}
            	else
            		{
            			if (flag == true){
            			  return checkFormModify(document.details);
            			}
            		}
            if (flag == true)
            {
                
            return checkFormModify(document.details);
            }
            else{
            	if(messaggio2 != null && messaggio2 != "" )
            	//CASO MESSAGGIO 2 valorizzato: la data del prossimo controllo è successiva alla data in cui tenti
            	//di inserire il controllo.
            		alert('ATTENZIONE! La data del prossimo controllo in sorveglianza che è possibile inserire in G.I.S.A. per questo operatore è '+messaggio2+'.');	
            	loadModalWindowUnlock();
            	return false;
            }
            }
        
			</script>
			
<jsp:useBean id="errors" class="java.util.HashMap" scope="request"/>



<%
if (errors.size()>0)
{%>
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color='red'>Si è Verificato un Errore : </font><br>
<hr color="#BFBFBB" noshade>

<%}
    Iterator errorList = errors.values().iterator();
    while (errorList.hasNext()) {
%>
 <font color='red'> <%= (String)errorList.next() %></font><br>
<%  
    }
%>
<div id="opendialog"></div>	
			
			<%
			if (TipoIspezione.size()>1)
			{
				TipoIspezione.setMultiple(true);
				TipoIspezione.setSelectSize(5);
			}
			TipoAudit.setMultiple(true);
			
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
      <td><%=OrgDetails.getName() %> </td>
    </tr>
	
	 <dhv:include name="stabilimenti-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
    	  <%=SiteIdList.getSelectedValue(TicketDetails.getSiteId())%>
          <input type="hidden" name="siteId" id="siteId" value="<%=TicketDetails.getSiteId()%>" >
      </td>
    </tr>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
 
 

    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
<%
		UserBean utente1 = (UserBean)session.getAttribute("User");
%>
<input type="hidden" name="role_user" id="role_user" value="<%=utente1.getUserRecord().getRoleId() %>">

 
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
         		<%= lookup_vuota_linea_attivita.getHtmlSelect("id_linea_sottoposta_a_controllo" , linee_selezionate ) %> 		
	<label id = "lab_linea"></label>
          </td>
	</tr>
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
		          <a id = "link_seleziona" href = "javascript:popLookupSelectorCustomStabilimentiCU('codici_selezionabili','alertText','lookup_codistat','','<%=OrgDetails.getAltId() %>',document.getElementById('tipo_selezione').value);"><label id = "lab_linea">Seleziona Una Voce</label></a>
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
			          <a id = "link_seleziona" href = "javascript:popLookupSelectorCustomSOACU('codici_selezionabili','alertText','lookup_codistat','','<%=OrgDetails.getAltId() %>',document.getElementById('tipo_selezione').value);"><label id = "lab_linea">Seleziona Una Voce</label></a>
	         	<font color = "red">*</font>
			     
		
	          </td>
		</tr>

			</div>
			<%
		
}
}
}
  %>
   
  

<tr id = "per_conto_di">
<td class = "formLabel" width="50p;">Per Conto di multiplo</td>
<td>
	<%=showError(request, "tipoControlloUoMultipleError")%>


		
		
				
			<table class="noborder" id ="listaStruttureMultiple">
			<tr><td>
			<a href="#" onclick="view_uo_attivita_multiplo()">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
			</td></tr>
			
			<%
		ArrayList<OiaNodo> lista_uo_sel = TicketDetails.getLista_unita_operative();
		boolean selezionato = false ;
		
		for (OiaNodo nodoSel : lista_uo_sel)
		{
			out.print(nodoSel.getDescrizione_lunga());
			%>
			<tr><td><input type="hidden" name="uo_controllo" value="<%=nodoSel.getId() %>" ><%=nodoSel.getDescrizione_lunga() %></td></tr>
			<%
		}
	%>
			
			
			
			</table>
		
</td>
</tr>
 



   <%@ include file="controlli_ufficiali_modify_info.jsp" %>

  
  <tr><td colspan="2"><div id="datiEstesi" name="datiEstesi"></div></td></tr>
   <%@ include file="../controlliufficiali/controlli_ufficiali_info_extra2.jsp" %>

   
   <%
			Iterator itKeyCodiciInterniSettati = listaCodiciInterniSettati.keySet().iterator();
			while(itKeyCodiciInterniSettati.hasNext())
			{
				String codInternoSettato = (String)itKeyCodiciInterniSettati.next();
				String descrizione = listaCodiciInterniSettati.get(codInternoSettato);
						%>
						<script>
						popolaCampi('<%=codInternoSettato%>', '<%=descrizione %>');
						</script>
						<% 
			}
			
			%>
 