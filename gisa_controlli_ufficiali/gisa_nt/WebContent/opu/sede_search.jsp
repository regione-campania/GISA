<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--  
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_search.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<jsp:useBean id="SearchSedeListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaProvince" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript">

  function clearForm() {
    <%-- Account Filters --%>
    //document.forms['searchSede'].searchComune.value="";
      }


  var request = null;

  function CreateXmlHttpReq(handler) {
    var xmlhttp = null;
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = handler;
    return xmlhttp;
  }

  function myHandler() {
	  
      if (request.readyState == 4 && request.status == 200) {
		  var myObject = eval('(' + request.responseText+ ')');
    	  setComuni(myObject);
      }
  }

  function myHandler2() {

      if (request.readyState == 4 && request.status == 200) {
  	  var myObject = eval('(' + request.responseText+ ')');
    	  setProvince(myObject);
      }
  }

  function getComuni2(idProvincia) {
	  var params = 'tipoRichiesta=2&idProvincia='+idProvincia ;
	  request = CreateXmlHttpReq(myHandler);
      request.open("POST", "ServletComuni", true);
      request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      request.send(params);
  }

  function setComuni(myObject)
  {

	  var select = document.forms[0].searchcodeIdComune; //Recupero la SELECT
      
	  //document.getElementById('combo1').style.display="none";
	  document.getElementById('imgload').style.display="block";
	  
      //Azzero il contenuto della seconda select
      for (var i = select.length - 1; i >= 0; i--)
    	  select.remove(i);

     
      for (i in myObject)
	  {
      var NewOpt = document.createElement('option');
      NewOpt.value = myObject[i].codice // Imposto il valore
      NewOpt.text = myObject[i].comune; // Imposto il testo
	  
      //Aggiungo l'elemento option
      try
      {
    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
      }catch(e){
    	  select.add(NewOpt); // Funziona solo con IE
      }
      }
      var NewOpt = document.createElement('option');
      NewOpt.value = '-1'; // Imposto il valore
      NewOpt.text = 'Seleziona'; // Imposto il testo
      try
      {
    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
      }catch(e){
    	  select.add(NewOpt); // Funziona solo con IE
      }
      //document.getElementById('combo1').style.display="";
	  document.getElementById('imgload').style.display="none";
		
}


  function getProvince(inRegione) {
	  
	  var params = 'inRegione='+inRegione+'&tipoRichiesta=1&combo=searchcodeIdprovincia' ;
	  request = CreateXmlHttpReq(myHandler2);
      request.open("POST", "ServletComuni", true);
      request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      request.send(params);
  }

  function setProvince(myObject)
  {

		 
	  var select = document.forms[0].searchcodeIdprovincia; //Recupero la SELECT
      
	  //document.getElementById('combo1').style.display="none";
	  
      //Azzero il contenuto della seconda select
      for (var i = select.length - 1; i >= 0; i--)
    	  select.remove(i);

     
      for (i in myObject)
	  {
      var NewOpt = document.createElement('option');
      NewOpt.value = myObject[i].codice // Imposto il valore
      NewOpt.text = myObject[i].descrizione; // Imposto il testo
	  
      //Aggiungo l'elemento option
      try
      {
    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
      }catch(e){
    	  select.add(NewOpt); // Funziona solo con IE
      }
      }
      var NewOpt = document.createElement('option');
      NewOpt.value = '-1'; // Imposto il valore
      NewOpt.text = 'Seleziona'; // Imposto il testo
      try
      {
    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
      }catch(e){
    	  select.add(NewOpt); // Funziona solo con IE
      }
      //document.getElementById('combo1').style.display="";
		
}
  </script>;
  
 

  <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
    
    <style>
    .ui-combobox {
        position: relative;
        display: inline-block;
    }
    .ui-combobox-toggle {
        position: absolute;
        top: 0;
        bottom: 0;
        margin-left: -1px;
        padding: 0;
        /* adjust styles for IE 6/7 */
        *height: 1.7em;
        *top: 0.1em;
    }
    .ui-combobox-input {
        margin: 0;
        padding: 0.3em;
    }
    </style>
    <script>


    (function( $ ) {
        $.widget( "ui.combobox", {
           
            _create: function() {
        	 var element = this.element;
                var input,  
                    that = this,
                    select = this.element.hide(),
                  
                
                    selected = select.children( ":selected" ),
                    value = selected.val() ? selected.text() : "",
                    wrapper = this.wrapper = $( "<span>" )
                        .addClass( "ui-combobox" )
                        .insertAfter( select );
 
                function removeIfInvalid(element) {
                    var value = $( element ).val(),
                        matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" ),
                        valid = false;
                    select.children( "option" ).each(function() {
                        if ( $( this ).text().match( matcher ) ) {
                            this.selected = valid = true;
                            return false;
                        }
                    });
                    if ( !valid ) {
                        // remove invalid value, as it didn't match anything
                        $( element )
                            .val( "" )
                            .attr( "title", value + " Nessun Elemento Trovato" )
                            .tooltip( "open" );
                        select.val( "" );
                        setTimeout(function() {
                            input.tooltip( "close" ).attr( "title", "" );
                        }, 2500 );
                        input.data( "autocomplete" ).term = "";
                        return false;
                    }
                }
 
                input = $( "<input>" )
                    .appendTo( wrapper )
                    .val( value )
                    .attr( "title", "" )
                    .addClass( "ui-state-default ui-combobox-input" )
                    .autocomplete({
                        delay: 0,
                        minLength: 0,
                        source: function( request, response ) {

						inregione = 'no';
						if (document.getElementById('inregione_si').checked)
							inregione = 'si' ;
                      	 
                        $.ajax({
                      	
                      		  url:  "./ServletComuni?&nome="+request.term+"&combo="+select[0].id+"&idProvincia="+document.getElementById("searchcodeIdprovincia").value+"&idComune="+document.getElementById("searchcodeIdComune").value+"&inRegione="+inregione,
                              		  
							
                           
                            dataType: "json",
                            data: {
                                style: "full",
                                maxRows: 12,
                                name_startsWith: request.term
                            },
                            success:function( data ) {
                            	arrayItem = new Array() ; 

                            	
								response( $.map( data, function( item ) {
										
									select.append('<option value='+item.codice+'>'+item.descrizione+'</option>');
                                    return {
                                        label: item.descrizione.replace(
                                                new RegExp(
                                                        "(?![^&;]+;)(?!<[^<>]*)(" +
                                                        $.ui.autocomplete.escapeRegex(request.term) +
                                                        ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                                    ),  "<strong>$1</strong>" )
                                      
                                        
                                    }
                                }));
                            }
                        });
                     
                    },
                        select: function( event, ui ) {
                            ui.item.option.selected = true;
                            that._trigger( "selected", event, {
                                item: ui.item.option
                            });
                        },
                        change: function( event, ui ) {
                         
                            if ( !ui.item )
                                return removeIfInvalid( this );
							if(ui.item)
							{
								if(select[0].id=='searchcodeIdAsl')
								{
									popolaComboComuni();

								}
								if(select[0].id=='searchcodeIdComuneStabilimento')
								{
									popolaComboAsl();

								}
								
								
							}
                            
                        }
                    })
                    .addClass( "ui-widget ui-widget-content ui-corner-left" );
 
                input.data( "autocomplete" )._renderItem = function( ul, item ) {
                    return $( "<li>" )
                        .data( "item.autocomplete", item )
                        .append( "<a>" + item.label + "</a>" )
                        .appendTo( ul );
                };
 
                $( "<a>" )
                    .attr( "tabIndex", -1 )
                   
                    .tooltip()
                    .appendTo( wrapper )
                    .button({
                        icons: {
                            primary: "ui-icon-triangle-1-s"
                        },
                        text: false
                    })
                    .removeClass( "ui-corner-all" )
                    .addClass( "ui-corner-right ui-combobox-toggle" )
                    .click(function() {
                        // close if already visible
                        if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
                            input.autocomplete( "close" );
                            removeIfInvalid( input );
                            return;
                        }
 
                        // work around a bug (likely same cause as #5265)
                        $( this ).blur();
 
                        // pass empty string as value to search for, displaying all results
                        input.autocomplete( "search", "" );
                        input.focus();
                    });
 
                    input
                        .tooltip({
                            position: {
                                of: this.button
                            },
                            tooltipClass: "ui-state-highlight"
                        });
            },
 
            destroy: function() {
                this.wrapper.remove();
                this.element.show();
                $.Widget.prototype.destroy.call( this );
            }
        });
    })( jQuery );
 
  
   
      
    $(function() {
        //$( "#searchcodeIdComune" ).combobox();

        $( "#searchcodeIdprovincia" ).combobox();
        $( "#searchcodeIdComune" ).combobox();

       
    });
    </script>	

<dhv:include name="accounts-search-name" none="true">
  <body onLoad="clearForm();getProvince('si');getComuni2(-1);">
</dhv:include>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sede.do"><dhv:label name="">Anagrafica</dhv:label></a> > 
<dhv:label name="">Ricerca rapida indirizzo</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>


<form name="searchSede" action="Sede.do?command=Search" method="post">
<input type="hidden" value="<%=request.getAttribute("tipologia") %>" name="tipologia" id="tipologia" /> 
<%= addHiddenParams(request, "actionSource|popup") %>
<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca rapida Sede</dhv:label></strong>
          </th>
  </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">In regione</dhv:label>
          </td>
          <td>
        	SI <input type ="radio" value = "si" name = "inregione" id = "inregione_si" onclick="getProvince('si')">  NO <input type ="radio" value = "no" id = "inregione_no" name = "inregione" onclick="getComuni2('no')" >
            
          </td>
        </tr>
        
        <tr >
          <td class="formLabel">
            <dhv:label name="">Provincia</dhv:label>
          </td>
          <td>
          
          <select name = "searchcodeIdprovincia" id ="searchcodeIdprovincia">
          <option value = "-1"> Seleziona Provincia</option>
          </select>
        	
            
          </td>
        </tr>
        
       <tr>
          <td class="formLabel">
            <dhv:label name="">Comune</dhv:label>
          </td>
          <td>
          <%=ComuniList.getHtmlSelect("searchcodeIdComune","-1") %>
          <br>
          <br>
          
            <img src = "images/icons/ajax-loader(1).gif" id = "imgload" title="Attendere caricamento Lista Comuni" style="display: none">
          </td>
        </tr>
        
        
           
  
  
 <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Cap</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="5" name="searchCap" value="" >
    </td>
  </tr>

       
         
       
      
      </table>
    </td>
<dhv:include name="accounts-contact-information-filters" none="true">
    <td width="30%" valign="top">
     
    </td>
</dhv:include>
  </tr>
</table>
<div id="log" style="height: 200px; width: 300px; overflow: auto;" class="ui-widget-content"></div>
<input type="submit" onclick='' value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
<input type = "button" value = "val provincia - comune" onclick="javascript:alert(document.getElementById('searchcodeIdprovincia').value+ ' '+document.getElementById('searchcodeIdComune').value)">


