/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

$(function(){

	  $('.tablesorter').tablesorter({
	    theme: 'green',
	    widthFixed : true,
	    showProcessing: true,
	    headerTemplate : '{content} {icon}',
	    widgets: [ 'uitheme', 'zebra', 'filter', 'scroller' ],
	    widgetOptions : {
	      scroller_height : 300,
	      scroller_upAfterSort: true,
	      scroller_jumpToHeader: true,
	      // In tablesorter v2.19.0 the scroll bar width is auto-detected
	      // add a value here to override the auto-detected setting
	      scroller_barWidth : null
	      // scroll_idPrefix was removed in v2.18.0
	      // scroller_idPrefix : 's_'
	    }
	  });

	});