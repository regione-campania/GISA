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

  function getComuni(request,combo) {
	  params ="nome="+request.term+"&combo="+combo+"&idProvincia="+document.getElementById("searchcodeIdprovincia").value+"&idComune="+document.getElementById("searchcodeIdComune").value+"&inRegione="+document.forms[0].inregione.value
	  url=  "ServletComuni" ;
	  request = CreateXmlHttpReq(myHandler);
      request.open("POST", url, true);
      request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      request.send(params);
  }

  function setComuni(myObject)
  {

	  var select = document.forms[0].searchcodeIdComune; //Recupero la SELECT
      
	  //document.getElementById('combo1').style.display="none";
	  
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
	  
	  var params = 'inRegione='+inRegione+'&tipoRichiesta=1' ;
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

var trovato = false ;
    (function( $ ) {
        $.widget( "ui.combobox", {
            _create: function() {
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
                        
                        //if ( $( this ).text().match( matcher.test(text) ) ) {
                        if ( matcher.test(text) ) {
                            alert('match si');
                            this.selected = valid = true;
                            return false;
                        }
                    });
                    if ( !valid ) {
                        // remove invalid value, as it didn't match anything
                        
                        if(confirm("ATTENZIONE ! La voce selezionata non esiste. Sicuro di inserire ?")==false)
                        {
                        $( element )
                            .val( "" )
                            .attr( "title", value + " didn't match any item" )
                            .tooltip( "open" );
                        select.val( "" );
                        setTimeout(function() {
                            input.tooltip( "close" ).attr( "title", "" );
                        }, 2500 );
                        input.data( "autocomplete" ).term = "";
                        return false;
                        }
                        else
                        {
								alert(select[0].id);
                        		document.getElementById(select[0].id+'Testo').value = value ;
                            	return true ;
                        }
                            
                        
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
                    	if(select[0].id=='searchcodeIdComune')
                        {
                        	getComuni(request,select[0].id,response);
                        }
                         
                         var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                            response( select.children( "option" ).map(function() {
                                var text = $( this ).text();
                                if ( this.value && ( !request.term || matcher.test(text) ) )
                                    return {
                                        label: text.replace(
                                            new RegExp(
                                                "(?![^&;]+;)(?!<[^<>]*)(" +
                                                $.ui.autocomplete.escapeRegex(request.term) +
                                                ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                            ), "<strong>$1</strong>" ),
                                        value: text,
                                        option: this
                                    };
                            }) );
                        },
                        /*source:  function( request, response ) {
                       	 
                            $.ajax({
                          	
                          		  url:  "http://localhost:8080/centric_osa/ServletComuni?&nome="+request.term+"&combo="+select[0].id+"&idProvincia="+document.getElementById("searchcodeIdprovincia").value+"&idComune="+document.getElementById("searchcodeIdComune").value+"&inRegione="+document.forms[0].inregione.value,
                                  		  
								
                               
                                dataType: "json",
                                data: {
                                    style: "full",
                                    maxRows: 12,
                                    name_startsWith: request.term
                                },
                                success:function( data ) {

    								response( $.map( data, function( item ) {
                                        return {
                                            label: item.descrizione.replace(
                                                    new RegExp(
                                                            "(?![^&;]+;)(?!<[^<>]*)(" +
                                                            $.ui.autocomplete.escapeRegex(request.term) +
                                                            ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                                        ), "<strong>$1</strong>" ),
                                            value: item.descrizione
                                        }
                                    }));
                                	
                                }
                            });
                         
                        },*/
                        select: function( event, ui ) {
                        	 select.children( "option" ).each(function() {
                                 
                                 if ( $( this ).text().match( ui.item.value ) ) {
                                     trovato = true ;
                                     this.selected =  true;
                                     alert(this.value);
                                     return false;
                                 }
                             }
                             

                             );
                        },
                        change: function( event, ui ) {
                            if ( !ui.item )
                                return removeIfInvalid( this );
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
                    .attr( "title", "Show All Items" )
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
        $( "#searchcodeIdAsl" ).combobox();
        $( "#searchcodeIdprovincia" ).combobox();
        $( "#searchcodeIdComune" ).combobox();
        $( "#via" ).combobox();

       
    });
    </script>	


<form id="addSede" name="addSede" action="Sede.do?command=Insert&auto-populate=true" method="post">
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="<dhv:label name="global.button.insert">Insert</dhv:label>" name="Save" onClick="">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Sede (indirizzo)</dhv:label></strong>
	  </th></tr>
	  
 <tr>
          <td class="formLabel">
            <dhv:label name="">In regione</dhv:label>
          </td>
          <td>
        	<select name = "inregione">
        	<option value = "si"> SI</option>
        	<option value = "no"> NO</option>
        	</select>
            
          </td>
        </tr>
         	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
        	<%= ListaProvince.getHtmlSelect("searchcodeIdprovincia",-1)%>
        		<input type = "hidden" name = "searchcodeIdprovinciaTesto" id = "searchcodeIdprovinciaTesto"/>
        	
   
    </td>
  </tr>
  	<tr>
		<td nowrap class="formLabel" name="province" id="province" >
      		<dhv:label name="requestor.requestor_add.City">City</dhv:label>
    	</td> 
    <td > 
   
   <select name = "searchcodeIdComune" id = "searchcodeIdComune">
        	<option value = "si"> SI</option>
        	<option value = "no"> NO</option>
        	</select>
     <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
	<input type = "hidden" name = "searchcodeIdComuneTesto" id = "searchcodeIdComuneTesto"/>
	
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
<!--      <input type="text" size="40" id="via" name="via" maxlength="80" value="<%=""%>"><font color="red">*</font>-->
<select name = "via" id = "via">
<option value = "-1" selected="selected">Seleziona Strada</option>
<option value = "2"></option>
<option value = "3"></option>
</select>

<input type = "hidden" name = "viaTesto" id = "viaTesto"/>

    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">C/O</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="presso" maxlength="80" value = "<%="" %>">
    </td>
  </tr>

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="cap" maxlength="5" value = "<%=toHtmlValue("") %>">
    </td>
  </tr>
  
 

   <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="latitudine" name="latitudine" size="30" value="" >
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" ><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="longitudine" name="longitudine" size="30" value="" ></td>
  </tr>
  <!-- <tr style="display: block">
    <td colspan="2">
    	<input id="coordbutton" type="button" value="Calcola Coordinate" 
    	onclick="javascript:showCoordinate(document.getElementById('via').value, document.forms['addSede'].comune.value,document.forms['addSede'].provincia.value, document.forms['addSede'].cap.value, document.forms['addSede'].latitudine, document.forms['addSede'].longitudine);" /> 
    </td>
  </tr>  --> 
</table>
</br></br>
<input type="submit" value="<dhv:label name="global.button.insert">Insert</dhv:label>" name="Save" onClick="">

</form>

<input type = "button" value = "val provincia - comune" onclick="javascript:alert('prov '+document.getElementById('searchcodeIdprovincia').value+ ' comune '+document.getElementById('searchcodeIdComune').value + ' via '+document.getElementById('via').value)">
