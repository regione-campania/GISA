<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.useridentifier"%>
<%@page import="org.aspcfs.modules.vigilanza.base.Ticket,java.util.*"%>
<%@page import="org.aspcfs.modules.vigilanza.base.NucleoIspettivo"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>

<script>
function setTestoNucleo(elemid,indice)
{
	  sel = document.getElementById(elemid) ;
		for (i=0;i<sel.options.length ; i++)
		{
			if(sel.options[i].selected == true)
			{
				
				document.getElementById('componenteid_'+indice).value =sel.options[i].innerHTML ;
				break ;
			}
			
		}	
	  
}
function mostaLink(indice)
{
	document.getElementById("link_"+indice).style.display="";
}
	
	function mostraUtenti(indice)
	{
		
		 $('#dialogListaNucleoIspettivo' ).dialog('open');
		 $.fx.speeds._default = 1000;
		  var resort = true;
		  $( "#nucleoajax tr" ).remove();
          jQuery("#listanucleo").trigger('update');
         
		idAsl=document.getElementById("siteId").value;
		idQualifica=document.getElementById("nucleo_ispettivo_"+indice).value;
		
	
		
		link = "NucleoIspettivo.do?command=List&idAsl="+idAsl+"&idRuolo="+idQualifica+"&indiceComponente="+indice;
		document.getElementById("IndiceComponente").value=indice;
		/*Recupero la lista degli utenti per la qualifica selezionata tramite una chiamata ajax*/
		 //start ajax request
                $.ajax({
                    url: link,
                    
                    //force to handle it as text
                    dataType: "text",
                    success: function(data) {
                        
                    	 entrato= false ;
                    	
                        //data downloaded so we call parseJSON function 
                        //and pass downloaded data
                        var json = $.parseJSON(data);
                        
                        if (json.length>1 || (json.length==1 && json[0].id>0))
                        	{
                        $.each(json, function(i, item) {
                        	 
                        	 if(item.id>0 )
                        		 {
                        		 entrato = true
                        		 var textjson = "{_id_:_"+item.nominativo.id+"_,_nome_:_"+item.nominativo.contact.nameFirst.replaceAll("'","")+"_,_cognome_:_"+item.nominativo.contact.nameLast.replaceAll("'","")+"_,_cf_:_"+item.nominativo.contact.codiceFiscale+"_}";
                        		
                        		 var styleScaduto = "" ;
                        		
                        		 if (item.nominativo.dataScadenza!=null && item.nominativo.dataScadenza!='null')
                        			 {
                        			 styleScaduto = "background-color:red;" ;
                        			 alert(item.nominativo.dataScadenza);
                        			 
                        			 }
                        		 var newTr ='<tr style="display: table-row;'+styleScaduto+'" class="odd" role="row"><td>'+item.nominativo.strutturaAppartenenza+'</td>';
                        		 newTr += '<td>'+item.nominativo.contact.nameLast+ ' '+item.nominativo.contact.nameFirst + '</td>';
                        		 newTr+='<td><input type="checkbox" name="utente" value="'+textjson+'" ></td>';
                        		 $( "#tablelistanucleo" ).find("tbody").append(newTr);
                        		 //
                        		 
                        		 }
                        	 
                        });
                        	}
                        else
                        	{
                        	
                        	 var newTr ='<tr style="display: table-row;" class="odd" role="row"><td colspan="4">Attenzione non esiste nessun utente associato nel Dpat</tr>';
                       	 $( "#tablelistanucleo" ).find("tbody").append(newTr).trigger('update');
                        	
                        	}
                        
                        $( "#tablelistanucleo" ).find("tbody").trigger('update');
                        
                      
                        
           
                    },
                    async :true
                
                });
		
		
              NucleoIspettivo.hasVista(idQualifica,{callback:gestisciBottoneAggiuntaCallBack,async:false});
    }
	
	
	function gestisciBottoneAggiuntaCallBack(val)
	{
		 if (val==false){
	        	document.getElementById("bottoneAggiungi").style.display='block';
	        	document.getElementById("bottoneAggiungi").disabled='';
	        }
	        else{
	        	document.getElementById("bottoneAggiungi").style.display='none';
	        	document.getElementById("bottoneAggiungi").disabled='disabled';
	        }
	}
	
	function eliminaComponente(ind)
	{
		if (document.getElementById('elementi').value!='1'){
			if (ind ==1)
				{
				//document.getElementById('nucleo_ispettivo').parentNode.removeChild(document.getElementById('nucleo_ispettivo'));
				alert('Impossibile eliminare il primo componente.');
				}
			else{
				document.getElementById('nucleoispettivo_'+ind).parentNode.removeChild(document.getElementById('nucleoispettivo_'+ind));
				document.getElementById('elementi').value = parseInt(document.getElementById('elementi').value)-1;
				ricompatta(ind);
			}
		
			}
			
	}
	
	function ricompatta(ind)
	{
		
		var newIndGlobal ;
		var array = new Array();
		if (ind>1 && ind <=10)
			{
			
			newind = ind;
			ind = ind+1 ;
			while (document.getElementById('nucleoispettivo_'+ind) != null)
				{
				
				
				newIndGlobal = newind;
				array[newind] = newind;
				var element = document.getElementById('nucleo_ispettivo_'+ind) ;
				element.name = "nucleo_ispettivo_"+newind;
				element.id = "nucleo_ispettivo_"+newind;
				
				element.onchange= function(){
					var res = this.id.split("_"); 
					
					gestisciNucleo(res[2]);
					
					
				}
				element.onFocus=function(){
					var res = this.id.split("_"); 
					document.getElementById('oldValueCombo'+res[2]).value=this.value;
				}
				
				
				document.getElementById('nucleoispettivo_'+ind).id = 'nucleoispettivo_'+newind ;
				
				
				
				var  element2 = document.getElementById('Utente_'+ind);
				element2.name = "Utente_"+newind;
				element2.id = "Utente_"+newind;
				
				var element3 = document.getElementById('componenteid_'+ind);
				element3.name = "componenteid_"+newind;
				element3.id = "componenteid_"+newind;
				
				var element4=document.getElementById('risorse_'+ind);
				element4.name = "risorse_"+newind;
				element4.id = "risorse_"+newind;
				
				var element5=document.getElementById('link_'+ind);
				element5.id = "link_"+newind;
				
				element5.onclick= "" ;
				element5.href="javascript:mostraUtenti("+array[newind]+");"; //function () 
				
			
				
				
				
				
				var  element6=document.getElementById('operazione_'+ind);
				
				element6.innerHTML="<a href='javascript:eliminaComponente("+array[newind]+");'>Elimina</a>";
				element6.id = "operazione_"+newind;
				
				var element7=document.getElementById('oldValueCombo'+ind);
				element7.id = "oldValueCombo"+newind;
				
				
				
				
				
				newind ++;
				ind = ind+1 ;
				}
			
			}
		else
			{
			
			newind = ind;
			ind = ind+1 ;
			
			
			while (document.getElementById('nucleoispettivo_'+ind) != null)
			{
			
			
			newIndGlobal = newind;
			
			array[newind] = newind;
			if (newind==1)
				
				document.getElementById('nucleoispettivo_'+ind).id = 'nucleo_ispettivo' ;
			else
				document.getElementById('nucleoispettivo_'+ind).id = 'nucleoispettivo_'+newind ;
			var element = document.getElementById('nucleo_ispettivo_'+ind) ;
			element.name = "nucleo_ispettivo_"+newind;
			element.id = "nucleo_ispettivo_"+newind;
			
			element.onchange= function(){
				var res = this.id.split("_"); 
				
				gestisciNucleo(res[2]);
				
				
				
			}
			
			element.onFocus=function(){
				var res = this.id.split("_"); 
				document.getElementById('oldValueCombo'+res[2]).value=this.value;
			}
			
			
			
			var  element2 = document.getElementById('Utente_'+ind);
			element2.name = "Utente_"+newind;
			element2.id = "Utente_"+newind;
			
			var element3 = document.getElementById('componenteid_'+ind);
			element3.name = "componenteid_"+newind;
			element3.id = "componenteid_"+newind;
			
			var element4=document.getElementById('risorse_'+ind);
			element4.name = "risorse_"+newind;
			element4.id = "risorse_"+newind;
			
			var element5=document.getElementById('link_'+ind);
			element5.id = "link_"+newind;
			
			element5.onclick= "" ;
			element5.href="javascript:mostraUtenti("+newind+");"; //function () 
//				{
				
//			  		mostraUtenti(array[newind]);
//				};
			
			
			
			
			
				var  element6=document.getElementById('operazione_'+ind);
				
				
				element6.innerHTML="<a href='javascript:eliminaComponente("+array[newind]+");'>Elimina</a>";
				element6.id = "operazione_"+newind;
				
				var element7=document.getElementById('oldValueCombo'+ind);
				element7.id = "oldValueCombo"+newind;			
			
			
			
			
			
			newind ++;
			ind = ind+1 ;
			}
			
			
			
			}
			
		
	}
</script>

<input type ="hidden" name = "elementi" id = "elementi" value = "<%=(TicketDetails.getNucleoasList().size() <10)? ""+(TicketDetails.getNucleoasList().size()+1) :"10" %>">
<input type ="hidden" name = "elemento" id = "elemento" value = "1">
<input type ="hidden" name = "size" id = "size" value = "<%=TicketDetails.getNucleoasList().size()+1 %>"">

<tr class="containerBody" id="nucleoIspettivo" >

	<td valign="top" class="formLabel"><label id = "nucleo" >Nucleo Ispettivo </label></td>
	<td>
		<%=showError(request, "nuceloIspettivoError")%>
	
	
	<table class="empty">
		<%
		ArrayList<NucleoIspettivo> lista = TicketDetails.getNucleoasList();
			for (int i = 0; i < lista.size(); i++)
			{
				boolean vet_visibilita = false 	;
				boolean med_visibilita = false 	;
				boolean tpal_visibilita = false ;
				boolean altr_visibilita = false ;
				boolean ref_visibilita = false ;
				boolean amm_visibilita = false ;
				boolean criuv_visibilita = false ;
				int nucleoCurr = lista.get(i).getNucleo();
				String componente = lista.get(i).getComponente();
				int idUser = lista.get(i).getUserId();
				
				
				
				%>
		<tr id = "<%=(i>0) ? "nucleoispettivo_" +(i+1) : "nucleo_ispettivo" %>">
			<td>
				<%
				String onchange = "onChange=gestisciNucleo("+(i+1)+");onFocus=document.getElementById('oldValueCombo"+(i+1)+"').value=this.value;";
				TitoloNucleo.setJsEvent(onchange); %>
				<%= TitoloNucleo.getHtmlSelectWithDiabled("nucleo_ispettivo_"+(i+1),""+nucleoCurr,false) %>
			</td>
	
			
			<td>
				
				<a href="#" onclick="mostraUtenti(<%=(i+1) %>)" id="link_<%=(i+1) %>" style="display: none"><font  color="#006699" style="font-weight: bold;">Seleziona Componenti</font></a>
				
				<input type = "text" <%=(idUser>0) ? "style='display:;width: 200px;'" : "style='display:none;width: 200px;'" %>  readonly="readonly"   id = "componenteid_<%=(i+1) %>" name = "componenteid_<%=(i+1) %>" value="<%=(idUser>0) ? componente : "" %>">
				
				<input type="text" name="Utente_<%=i+1 %>" width="400px;" placeholder="ID COMPONENTE NUCLEO" id="Utente_<%=i+1 %>" value = "<%=(idUser<=0) ? componente : "" %>"  <%if (idUser>0) {%>style="display: none"<%} %>  onchange="clona(document)" />
				
				
				<font color = "red"> * </font> 
				
				<input type = "hidden" name="risorse_<%=i+1 %>" id="risorse_<%=i+1 %>" value = "<%=(idUser>0) ? idUser :"-1" %>"  <%if (idUser==-1) {%>style="display: none"<%} %>>
				
			</td>
			<td>
			<div id="operazione_<%=i+1%>">
				<a href='javascript:eliminaComponente(<%=i+1%>);'><font  color="#006699" style="font-weight: bold;">Elimina</font></a>
				</div>
				<input type ="hidden" id = "oldValueCombo<%=i+1%>" value = "<%=nucleoCurr%>">
			</td>
			
			
		</tr>
	
		<%} %>
	
	
	
<%if (lista.size()<10){ %>
<tr id = "<%=(lista.size()==0)? "nucleo_ispettivo" : "nucleoispettivo_"+(lista.size()+1) %>" >
			<td>
			
				<%
				String onchange = "onChange=gestisciNucleo("+(lista.size()+1)+");onFocus=document.getElementById('oldValueCombo"+(lista.size()+1)+"').value=this.value;";
				TitoloNucleo.setJsEvent(onchange); %>
				<%= TitoloNucleo.getHtmlSelectWithDiabled("nucleo_ispettivo_"+(lista.size()+1),"-1",false) %>
			</td>
			<td>
				
				<a href="#" onclick="mostraUtenti(<%=(lista.size()+1) %>)" id="link_<%=(lista.size()+1) %>" style="display: none">Seleziona Componente</a>
				
				<input type = "text" style="display:none;width: 200px;"  readonly="readonly"   id = "componenteid_<%=(lista.size()+1) %>" name = "componenteid_<%=(lista.size()+1) %>">
				<input type="text" name="Utente_<%=lista.size()+1 %>" width="400px;" placeholder="ID COMPONENTE NUCLEO" id="Utente_<%=lista.size()+1 %>"    style="display: none;width: 200px;" onchange="clona(document)" />
				<font color = "red"> * </font> 
				
				<input type = "hidden" name="risorse_<%=lista.size()+1 %>" id="risorse_<%=lista.size()+1 %>" value = "-1">
				
			</td>
			<td><div id="operazione_<%=lista.size()+1%>"><a href='javascript:eliminaComponente(<%=lista.size()+1%>);'>Elimina</a></div>
							<input type ="hidden" id = "oldValueCombo<%=lista.size()+1%>" value = "">
							</td>
		</tr>
<%} %>
	</table>
	</td>
</tr>




<tr class="containerBody" id="nucleoIspettivoSettato" style="display: none" >

	<td valign="top" class="formLabel"><label id = "nucleo" >Nucleo Ispettivo </label></td>
	<td>
		<%=showError(request, "nuceloIspettivoError")%>
	
	
	<table class="empty">
		
<tr id = "nucleo_ispettivo" >
			<td>
			
				<%=TitoloNucleo.getSelectedValue(3339) %>
				<input type = "hidden" name = "nucleo_ispettivo" value="3339">
				
			</td>
			<td>
			<input type = "text" name="Utente" id="Utente" size="1000" value = "SANZIONI POSTICIPATE UTENTE FITTIZIO" readonly="readonly">
				
				<input type = "hidden" style="idth: 200px;"  readonly="readonly"   id = "componenteid" name = "componenteid" value="6730">
				
			</td>
			<td></td>
		</tr>

	</table>
	</td>
</tr>
