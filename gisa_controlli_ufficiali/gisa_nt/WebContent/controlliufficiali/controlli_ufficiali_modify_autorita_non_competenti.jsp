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


        
       
        
        function controlloCuSorveglianza()
        {

      
        	
        if (document.details.assignedDate.value == '')
        	return checkForm(document.details);
        orgId = document.details.orgId.value;
        assetId = -1 ;
        if (document.details.assetId!=null)
        {
        	assetId = document.details.assetId.value;
        }




PopolaCombo.controlloInserimentoCuSorveglianza(orgId, false,false,document.details.assignedDate.value,assetId, viewMessageCallback1);
}

       
       


        function viewMessageCallback1(returnValue) {

            messaggio1 = returnValue[0];
            messaggio2 = returnValue[1];
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
            				flag = true;
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
            		
            			if (flag == true)
            				return checkForm(document.details);
            		}
            if (flag == true)
            {
                
            	
            return checkForm(document.details);
            }
            else{
            	if(messaggio2 != null && messaggio2 != "" )
            	//CASO MESSAGGIO 2 valorizzato: la data del prossimo controllo è successiva alla data in cui tenti
            	//di inserire il controllo.
            		alert('ATTENZIONE! La data del prossimo controllo in sorveglianza che è possibile inserire in G.I.S.A. per questo operatore è '+messaggio2+'.');	
            	return false;
            }
           
            }

			</script>
			
			
<input type="hidden" name="idStabilimentoopu" value="<%=OrgDetails.getIdStabilimento()%>"/>
			

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
  
  <tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Tecnica di controllo</dhv:label>
      </td><td>
       <%
    		 out.print(TipoCampione.getSelectedValue(TicketDetails.getTipoCampione()));
    		 %>&nbsp;&nbsp;
    		 <input type= "hidden" name = "tipoCampione" id ="tipoCampione" value = "<%=TicketDetails.getTipoCampione() %>"/>
    		 <%
    	 
    	 %>
    	 </td></tr>
      
   





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
		          <a id = "link_seleziona" href = "javascript:popLookupSelectorCustomStabilimentiCU('codici_selezionabili','alertText','lookup_codistat','','<%=OrgDetails.getOrgId() %>',document.getElementById('tipo_selezione').value);"><label id = "lab_linea">Seleziona Una Voce</label></a>
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
			          <a id = "link_seleziona" href = "javascript:popLookupSelectorCustomSOACU('codici_selezionabili','alertText','lookup_codistat','','<%=OrgDetails.getOrgId() %>',document.getElementById('tipo_selezione').value);"><label id = "lab_linea">Seleziona Una Voce</label></a>
	         	<font color = "red">*</font>
			     
		
	          </td>
		</tr>

			</div>
			<%
		
}
}
}
  %>
   
  <%SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy"); 
  String data = "" ;
  if (TicketDetails.getAssignedDate()!=null)
  {
	 data = sdfData.format(new Date (TicketDetails.getAssignedDate().getTime()));
  }
  
  %>
  <input type = "hidden" name = "data_iniziale" value = "<%=data %>"/>
 <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Inizio Controllo</dhv:label>
      </td>
      <td>
      
      	<input readonly type="text" id="assignedDate" name="assignedDate" size="10" 
		value="<%= (TicketDetails.getAssignedDate()==null)?(""):(getLongDate(TicketDetails.getAssignedDate()))%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      	
 
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
	<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Fine Controllo</dhv:label>
      </td>
      <td>
      	<input readonly type="text" id="dataFineControllo" name="dataFineControllo" size="10" 
		value="<%= (TicketDetails.getDataFineControllo()==null)?(""):(getLongDate(TicketDetails.getDataFineControllo()))%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataFineControllo,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
      </td>
    </tr>
 
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
	
	 <tr class="containerBody" id= "oggetto_controllo" >
    <td valign="top" class="formLabel">
      Oggetto Del Controllo
    </td>
    <td>
    <table class = "noborder">
    <tr>
    <td>
	<!-- onmouseout="abilitaNoteDescrizioni();abilitaSpecieTrasportata();" -->
	<select name = "ispezione"  size="10" multiple="multiple"  id = "ispezione" onmouseout="abilitaNoteDescrizioni();">
   <%
   

   
   Iterator<Integer> itLista = Ispezione.keySet().iterator();
   while (itLista.hasNext())
   {
	   int key = itLista.next();
	   %>
	   <optgroup label="<%=IspezioneMacrocategorie.getValueFromId(key)%>"  style="color: blue"></optgroup>
	   
	   <%
	   
	   HashMap<Integer,String> l = ( HashMap<Integer,String>) Ispezione.get(key);
	   Iterator<Integer> itL = l.keySet().iterator();
	   while (itL.hasNext())
	   {
		   int code = itL.next();
		   String desc = l.get(code);
		   boolean sel = false ;
		
			
			HashMap<Integer,String> lista = TicketDetails.getLisaElementi_Ispezioni().get(key);
			
			if (lista!=null)
			{
			Iterator<Integer> kiave1= lista.keySet().iterator();
			
			while(kiave1.hasNext()){
				
				int code2 = kiave1.next();
				if (code2 == code )
				{
					sel = true;
				}			
			}
			}
			
			
		   %>
		   
		   
		 <option value = "<%=code %>" <%if(sel==true) {%> selected="selected"<%} %>><%=desc %></option>
		   <%
		   	   }
	   
   }
   
   
   %>
	</select>
	</td>
	<td>&nbsp;</td>
	<td>
	<table>
			
			<tr id = "desc_note1" style = "display:none"><td><b>Settore Alimenti per il consumo Umano (Descrizione)</b> <br> <textarea rows = "3" cols = "20" name = "ispezioni_desc1"><%=toHtml2(TicketDetails.getIspezioni_desc1()) %></textarea></td></tr>
			<tr id = "desc_note2" style = "display:none"><td><b>Settore alimenti Zootecnici (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc2" ><%=toHtml2(TicketDetails.getIspezioni_desc2()) %></textarea></td></tr>
			<tr id = "desc_note3" style = "display:none"><td><b>Settore Benessere Animale non durante il trasporto</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc3" ><%=toHtml2(TicketDetails.getIspezioni_desc3()) %></textarea></td></tr>
			<tr id = "desc_note4" style = "display:none"><td><b>Settore Sanita animale (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc4" ><%=toHtml2(TicketDetails.getIspezioni_desc4()) %></textarea></td></tr>
			<tr id = "desc_note5" style = "display:none"><td><b>Settore S.O.A. negli Impianti di trasformazione (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc5"><%=toHtml2(TicketDetails.getIspezioni_desc5()) %></textarea></td></tr>
			<tr id = "desc_note6" style = "display:none"><td><b>Settore Rifiuti S.O.A. nelle altre imprese (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc6"><%=toHtml2(TicketDetails.getIspezioni_desc6()) %></textarea></td></tr>
			<tr id = "desc_note7" style = "display:none"><td><b>Altro (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc7"><%=toHtml2(TicketDetails.getIspezioni_desc7()) %></textarea></td></tr>
			<tr id = "desc_note8" style = "display:none"><td><b>Settore Benessere Animale durante il trasporto</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc8"><%=toHtml2(TicketDetails.getIspezioni_desc8()) %></textarea></td></tr>
			
			
			</table>
	
	</td>
	</tr></table>
	</td>
	</tr>


<input type="hidden" name="ncrilevate" value="<%=(TicketDetails.isNcrilevate()==true)?("1"):("2")%>">
   
  
        <!-- nucleo ispettivo -->
   
   <%@ include file="nucleo_ispettivo_modify.jsp" %>
  
  
   
   
   
   
 