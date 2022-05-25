/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/




var arrayItem ;    	
var text = '' ;
var trovato = false ;
    (function( $ ) {
    	
    	$.ui.autocomplete.prototype._renderMenu = function(ul, items) {
    		  var self = this;
    		  //table definitions
    		  ul.append("<table width='40%'><thead><tr><th>ID#</th><th>Name</th><th>Cool&nbsp;Points</th></tr></thead><tbody></tbody></table>");
    		  $.each( items, function( index, item ) {
    		    self._renderItemData(ul, ul.find("table tbody"), item );
    		  });
    		};
    		$.ui.autocomplete.prototype._renderItemData = function(ul,table, item) {
    		  return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
    		};
    	
    	
        $.widget( "ui.combobox", {
            _create: function() {
                var input,
                    that = this,
                    select = this.element.hide(),
                    selected = select.children( ":selected" ),
                    value = selected.val() ? ked.text() : "",
                    wrapper = this.wrapper = $( "<span>" )
                        .addClass( "ui-combobox" )
                        .insertAfter( select );
 
                function removeIfInvalid(element) {
                    var value = $( element ).val(),
                        matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" ),
                        valid = false;
                   
                   
                    if ( valid==false ) {
                        
                     
                        
                    	
                    	
                        
                    
                    }
                }
                
                input = $( "<input type='text' size='26' id='"+select[0].id+"input' class='"+select[0].className +"' name='"+select[0].id+"input'>" )
                    .appendTo( wrapper )
                    .val( trim(value) )
                    .attr( "title", "" )
                    .autocomplete({
                        delay: 0,
                        minLength: 0,
                        
                        source:  function( request, response ) {
                       	
                   
                            $.ajax(
                                    {	
                                        url:  "testservice.jsp",
                                      	dataType: "json",
                                		data: {
                                    			style: "full",
                                    			maxRows: 12,
                                    			name_startsWith: request.term
                                			   },
                                			   error: function (textStatus, errorThrown) {
                                                   alert('errore '+errorThrown);
                                                  },
                                		success:function( data ) {
                                			
                                           		
                                						arrayItem = new Array() ; 
									
    												response( $.map( data, function( item ) {
            											
    	            									
    	                                                return {
    	                                                    label: item.codice.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" +$.ui.autocomplete.escapeRegex(request.term) +")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>" ),
    	                                                    indirizzo: item.codice ,
    	                                                    provincia:item.provincia
    	                                                    
    	                                                  
    	                                                    
    	                                                }
    	                                            }));
    	                                        	
                                            
                                }
                            });
                         
                        },
                        focus: function( event, ui ) {
                        
                        	},
                        	select: function( event, ui ) {
                        		
                        		
                        	$( "#searchAziendaFieldinput" ).val( ui.item.indirizzo );
                        	
                        	return false;
                        	},
                       
                       
                        change: function( event, ui ) {
                            if ( !ui.item )
                                return removeIfInvalid( this );
                        }
                    })
                    .addClass( "ui-widget ui-widget-content ui-corner-left" );
 
                input.data( "autocomplete" )._renderItem = function( table, item ) {
                	return $( "<tr class='ui-menu-item' role='presentation'>" )
        		    .data( "item.autocomplete", item )
        		    .append( "<td><a>"+item.label+"</a></td>"+"<td>"+item.indirizzo+"</td>"+"<td>"+item.provincia+"</td></tr>" )
        		    .appendTo( table );
                	
                };
 
                $( "<a>" ).click(function() {
                        // close if already visible
                    	alert('click me');
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
 
                   
            },
 
            destroy: function() {
                this.wrapper.remove();
                this.element.show();
                $.Widget.prototype.destroy.call( this );
            }
        });
    })( jQuery );
 
 
  
   
      
  