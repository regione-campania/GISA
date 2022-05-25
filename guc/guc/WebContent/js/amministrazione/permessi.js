/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function selezionaFunzioneInModificaRuolo( chooser, ruolo )
{
	var choice		= chooser.options[ chooser.selectedIndex ].value;
	location.href	= 'ruoli.ToPermissionEdit.us?funzione=' + choice + '&ruolo=' + ruolo;
};

function selezionaFunzioneInGestioneFunzioni( chooser )
{
	var choice		= chooser.options[ chooser.selectedIndex ].value;
	location.href	= 'funzioni.ToPermissionEdit.us?funzione=' + choice;
};

function setAllRO()
{
	var zip = document.getElementsByTagName( "input" );
	for( var i = 0; i < zip.length; i++ )
	{
		if( zip[i].id == "ogRadio" && zip[i].value == 0 )
		{
			zip[i].checked = true;
		}
	}
};

function setAllRW()
{
	var zip = document.getElementsByTagName( "input" );
	for( var i = 0; i < zip.length; i++ )
	{
		if( zip[i].id == "ogRadio" && zip[i].value == 1 )
		{
			zip[i].checked = true;
		}
	}
};

function setAllNO()
{
	var zip = document.getElementsByTagName( "input" );
	for( var i = 0; i < zip.length; i++ )
	{
		if( zip[i].id == "ogRadio" && zip[i].value == 2 )
		{
			zip[i].checked = true;
		}
	}
};
