/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkform(form) {
	

	if (form.tipologiaTicket.value == '0') {
		alert("Inserire una tipologia");	
		return false;
	}	
	
	if (form.description.value == '') {
		alert("Inserire una descrizione");	
		return false;
	}
	
	if (form.email.value =='') {
		alert("Inserire una mail");	
		return false;
	}
	
	if (form.email.value !='' && checkMail (form.email.value) == false) {
		alert("Inserire una mail corretta");	
		return false;
	}
		
	
	attendere();
	return true;
	
}


function checkMail(str){
	var email = str;
	
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			
	if (!filter.test(email)) {		
		return false;
	}
	
	return true;
}

	

function checkMail1(str){
    var mail=str;

    if (mail.length > 0) {     
      var i=0;
      while(mail.charAt(i)!='@')
      {
        if (i<mail.length)
        {
        	i++;
        }
        else
        {               
        	return false;
        }
      }
      
      if (mail.charAt(i++)=='.') {
		  return false;
	  }
	  
     	  
                  
      while(mail.charAt(i)!='.')
      {	         	 
    	 if (i<mail.length)
	     {
	      	i++;
	     }
	     else
	     {
	        return false;
	     }
      }
    }
    
    return true;
  }