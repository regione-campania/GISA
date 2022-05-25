/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
 function inviaFileExcel(form){
		var errorString = '';
		 
		 
		var fileCaricato = form.file1;
		
		if (fileCaricato.value==''){// || (!fileCaricato.value.endsWith(".pdf") && !fileCaricato.value.endsWith(".csv"))){
			errorString+=' Errore! Selezionare un file!';
			form.file1.value='';
		}
		/*if (oggetto==''){
			errorString+='\nErrore! L\'oggetto è obbligatorio.';
			}
		if (!GetFileSize(form.file1))
			errorString+='\nErrore! Selezionare un file con dimensione inferiore a 3,00 MB'; */
		if (errorString!= '')
			alert(errorString);
		else
		{
		//form.filename.value = fileCaricato.value;	
		 
			loadModalWindow();
			form.submit();
		}
	} 

 
 
 
 
 
 
 $(function(){
	 
	 
	 $("a").filter(function(ind){
		 
		 return /^.*ImportAnagraficheGestoriMassivo.*$/i.test($(this).attr("href")+"");
	 }).css({"color" : "red", "font-weight" : "bold"});
	 
	 
 });