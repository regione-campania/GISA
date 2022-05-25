/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkformSmaltimentoCarogna() 
{
	if(document.form.dataSmaltimentoCarogna.value == '')
	{
		alert("Inserire la data");
		document.form.dataSmaltimentoCarogna.focus();
		return false;
	}
	
	if(confrontaDate(document.form.dataSmaltimentoCarogna.value,document.form.dataApertura.value)<0)
	{
		alert("La data del trasporto spoglie non può essere antecedente alla data di apertura dell'accettazione(" + document.form.dataApertura.value + ")");	
		document.form.dataSmaltimentoCarogna.focus();
		return false;
	}
	
	if(!myConfirm('Attenzione, i dati della registrazione trasporto spoglie non saranno modificabili dopo l\'inserimento, proseguire?'))
	{
		return false;
	}
	
	if(!controllaDataAnnoCorrente(document.form.dataSmaltimentoCarogna,'Data'))
	{
		return false;
	}
	
	attendere();
	return true;
}