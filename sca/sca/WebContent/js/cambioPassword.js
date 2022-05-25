/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var PWD_MIN = 10;
var PWD_MAX = 15;

  function checkForm()
  {
	  var form = document.getElementById("cambioPassword");
	 
    if(form.username.value == "") {
      alert("Errore: Username vuota.");
      form.username.focus();
      return false;
    }
    if(form.pwdOld.value == "") {
        alert("Errore: Vecchia password vuota.");
        form.pwdOld.focus();
        return false;
      }
    if(form.pwd1.value == "") {
        alert("Errore: Nuova password vuota.");
        form.pwd1.focus();
        return false;
      }
    if(form.pwd2.value == "") {
        alert("Errore: Conferma password vuota.");
        form.pwd2.focus();
        return false;
      }
    
 
    re = /^\w+$/;
    if(!re.test(form.pwd1.value)) {
      alert("Errore: Password contenente caratteri non supportati.");
      form.pwd1.focus();
      return false;
    }
    
      if(form.pwd1.value.length < PWD_MIN) {
        alert("Errore: la password deve essere almeno di " + PWD_MIN + " caratteri");
        form.pwd1.focus();
        return false;
      }
      
        if(form.pwd1.value.length > PWD_MAX) {
            alert("Errore: la password deve essere al massimo di " + PWD_MAX + " caratteri");
            form.pwd1.focus();
            return false;
          }
          
      if(form.pwd1.value == form.username.value) {
        alert("Errore: La password deve essere diversa dalla username!");
        form.pwd1.focus();
        return false;
      }
      re = /[0-9]/;
      if(!re.test(form.pwd1.value)) {
        alert("Errore: La password deve contenere almeno un numero (0-9)!");
        form.pwd1.focus();
        return false;
      }
      re = /[a-z]/;
      if(!re.test(form.pwd1.value)) {
        alert("Errore: La password deve contenere almeno una lettera minuscola (a-z)!");
        form.pwd1.focus();
        return false;
      }
      re = /[A-Z]/;
      if(!re.test(form.pwd1.value)) {
        alert("Errore: La password deve contenere almeno una lettera maiuscola (A-Z)!");
        form.pwd1.focus();
        return false;
    } 
  	if(form.pwd1.value != form.pwd2.value ) {
      alert("Errore: La password di conferma non corrisponde alla nuova password.");
      form.pwd2.focus();
      return false;
    }
 
    
  	if (confirm('ATTENZIONE. Questa operazione sta per essere loggata ed eseguita con effetto immediato. Sei sicuro di voler continuare?')){
  		document.getElementById("formCambioPassword").style.display="none";
  		document.getElementById("attenderePrego").style.display="block";
  		form.submit();
  	}
  	
  }

   
  function reset(){
	 var old = document.getElementById("pwdOld");
	 var pwd1 = document.getElementById("pwd1");
	 var pwd2 = document.getElementById("pwd2");
	
	 old.value ="";
	 pwd1.value="";
	 pwd2.value="";
  }
  
  function showPolicy(){
	  alert('Policy per nuove password: \nLunghezza: 10-15 caratteri\nContenente solo lettere, numeri e underscore (_).\nContenente almeno 1 lettera minuscola (a-z)\nContenente almeno 1 lettera maiuscola (A-Z)\nContenente almeno 1 numero (0-9)');
  }
  
  function reloadOpener(){
	    window.opener.location.reload();
	    window.close();
  }
  
  function mostraNascondi(radio){
	var old = document.getElementById("pwdOld");
	var pwd1 = document.getElementById("pwd1");
	var pwd2 = document.getElementById("pwd2");
	
	if (radio.id == 'female'){
		old.type='text';
		pwd1.type='text';
		pwd2.type='text';
	}
	else {
		old.type='password';
		pwd1.type='password';
		pwd2.type='password';
	}
  }
  
  
  function checkFormDecrypt()
  {
	  var form = document.getElementById("decriptaPassword");
	 
    if(form.pwdDaDecriptare.value == "") {
      alert("Errore: password vuota.");
      form.pwdDaDecriptare.focus();
      return false;
    }
     		form.submit();
   }
  
  function openPopup(url){
		
	  var res;
    var result;
    	  window.open(url,'popupSelect',
          'height=1280px,width=600px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
  
  function show(){
		document.getElementById("policyForm").style.display="block";
	}
	function hide(){
		document.getElementById("policyForm").style.display="none";
	}