/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
$(function() {
	// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
//	$( "#dialog:ui-dialog" ).dialog( "destroy" );

	$( "#dialog-modal" ).dialog({
		height: 140,
		modal: true,
		autoOpen: false,
		closeOnEscape: false,
		show: 'blind',
		resizable: false,
		draggable: false,
		width: 250
	});
});

function attendere()
{
	$( "#dialog-modal" ).dialog( "open" );
};

function myConfirm( testo )
{
	if( confirm( testo ) )
	{
		attendere();
		return true;
	}
	else
	{
		return false;
	}
};
	