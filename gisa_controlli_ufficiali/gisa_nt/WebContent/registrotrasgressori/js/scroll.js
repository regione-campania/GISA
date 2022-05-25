/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
(function($){  
    $.fn.tScroll = function(options) {    
    return this.each(function() {
        // Recupero info tabella
        options.id_table = $(this).attr('id');
        var border = $('#'+options.id_table).attr('border');
        var cellspacing = $('#'+options.id_table).attr('cellspacing');
        var cellpadding = $('#'+options.id_table).attr('cellpadding');
        var classe = $('#'+options.id_table).attr('class');
        
        // Fisso thead
        var colonne = $('#'+options.id_table+' thead th').length;
        for(var i=0; i<colonne; i++){
            var w_th=$('#'+options.id_table+' thead th').eq(i).css('width').replace('px','');
            $('#'+options.id_table+' thead th').eq(i).css('width',w_th+'px');    
        }
        
        // Fisso tbody
        var colonne = $('#'+options.id_table+' tbody tr:eq(0) td').length;
        for(var i=0; i<colonne; i++){
            var w_th=$('#'+options.id_table+' tbody tr:eq(0) td').eq(i).width();
            $('#'+options.id_table+' tbody tr:eq(0) td').eq(i).width(w_th);    
        }
        
	
        
		var tbox = "<div id='tScroll_box_"+options.id_table+"'></div>";
        var table1 = "<table id='tScroll_t1_"+options.id_table+"' border='"+border+"' cellspacing='"+cellspacing+"' cellpadding='"+cellpadding+"' class='"+classe+"'><thead>"+$('#'+options.id_table+' thead').html()+"</thead></table>";
        var div = "<div id='tScroll_body_"+options.id_table+"'></div>";
		
		$(tbox).insertBefore('#'+options.id_table);
		
		$('#tScroll_box_'+options.id_table).append(table1);
		$('#tScroll_box_'+options.id_table).append(div);
		$('#tScroll_box_'+options.id_table).append(table2);
        
        $('#tScroll_body_'+options.id_table).append($('#'+options.id_table));
        
        $('#'+options.id_table).css('marginTop',0);
        $('#'+options.id_table).css('marginBottom',0);
        $('#'+options.id_table+" thead").remove();
        $('#'+options.id_table+" tfoot").remove();
        
        var w = $('#'+options.id_table).width() + 18;
        
        $('#tScroll_body_'+options.id_table).css('width',w)
                          .css('height',options.h_box)
                          .css('padding','0px')
                          .css('margin','0px')
                          .css('overflow-y','scroll')
                          .css('text-align','left');
		
		$('#tScroll_box_'+options.id_table).css('width',w)
										   .css('padding','0px')
										   .css('margin','0px')
										   .css('text-align','left');
    });
    
    return false;
    
    };  
})(jQuery);

