/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/* Italian initialisation for the jQuery UI date picker plugin. */
/* Written by Antonello Pasella (antonello.pasella@gmail.com). */
( function( factory ) {
	if ( typeof define === "function" && define.amd ) {

		// AMD. Register as an anonymous module.
		define( [ "../widgets/datepicker" ], factory );
	} else {

		// Browser globals
		factory( jQuery.datepicker );
	}
}( function( datepicker ) {

datepicker.regional.it = {
	closeText: "Chiudi",
	prevText: "&#x3C;Prec",
	nextText: "Succ&#x3E;",
	currentText: "Oggi",
	monthNames: [ "Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno",
		"Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre" ],
	monthNamesShort: [ "Gen","Feb","Mar","Apr","Mag","Giu",
		"Lug","Ago","Set","Ott","Nov","Dic" ],
	dayNames: [ "Domenica","Lunedì","Martedì","Mercoledì","Giovedì","Venerdì","Sabato" ],
	dayNamesShort: [ "Dom","Lun","Mar","Mer","Gio","Ven","Sab" ],
	dayNamesMin: [ "Do","Lu","Ma","Me","Gi","Ve","Sa" ],
	weekHeader: "Sm",
	dateFormat: "dd/mm/yy",
	firstDay: 1,
	isRTL: false,
	showMonthAfterYear: false,
	yearSuffix: "" };
datepicker.setDefaults( datepicker.regional.it );

return datepicker.regional.it;

} ) );
