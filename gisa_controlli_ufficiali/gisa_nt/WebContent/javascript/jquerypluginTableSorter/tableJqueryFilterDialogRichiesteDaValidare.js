/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

$(function(){
	
	 var pagerOptions = {
			    // target the pager markup - see the HTML block below
			    container: $(".pager"),
			    // output string - default is '{page}/{totalPages}'; possible variables: {page}, {totalPages}, {startRow}, {endRow} and {totalRows}
			    output: '{startRow} - {endRow} / {filteredRows} ({totalRows})',
			    // if true, the table will remain the same height no matter how many records are displayed. The space is made up by an empty
			    // table row set to a height to compensate; default is false
			    fixedHeight: true,
			    // remove rows from the table to speed up the sort of large tables.
			    // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
			    removeRows: false,
			    // go to page selector - select dropdown that sets the current page
			    cssGoto: '.gotoPage'
			  };

	 $("#tableRichiesteDaValidare")
	 .tablesorter({
			theme: 'blue',
			headerTemplate : '{content} {icon}', // new in v2.7. Needed to add the bootstrap icon!
			widthFixed: true,
			widgets: ['zebra', 'filter']
		}).tablesorterPager(pagerOptions);
	 
	
	 
	 
	 
	  
	  


	


	});